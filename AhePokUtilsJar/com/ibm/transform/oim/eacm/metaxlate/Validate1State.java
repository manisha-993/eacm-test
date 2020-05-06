// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2002, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.metaxlate;

import java.util.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.translation.*;
import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.transactions.NLSItem;

/**
 * Implements the first step in the validation process. In which the user
 * validates all the translated values and abbreviates any values which are too
 * long to be stored in the PDH. They will see any of the english values that 
 * changed, any new values and any deleted values since the request was sent out.
 * <pre>
 * Licensed Materials -- Property of IBM
 *
 * (c) Copyright International Business Machines Corporation, 2002, 2006
 * All Rights Reserved.
 *
 * CVS Log History
 * $Log: Validate1State.java,v $
 * Revision 1.6  2008/01/22 18:33:48  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.5  2006/04/17 19:37:16  chris
 * JTest changes
 *
 * Revision 1.4  2006/03/10 21:11:31  chris
 * added serializeable and default constructors
 *
 * Revision 1.3  2006/03/10 20:00:08  chris
 * Make all versions final
 *
 * Revision 1.2  2006/01/26 20:52:52  sergio
 * AHE copyright
 *
 * Revision 1.1  2005/09/16 18:20:10  chris
 * Updates for Application Hosting Environment
 *
 * Revision 1.3  2005/02/16 17:50:21  chris
 * JTest cleanup
 *
 * Revision 1.2  2004/11/10 13:58:26  chris
 * Changes based on Daves emergency fix for CR0813042358
 *
 * Revision 1.1.1.1  2004/01/26 17:40:01  chris
 * Latest East Coast Source
 *
 * Revision 1.6  2003/09/25 18:37:47  cstolpe
 * Invoke Action instead of posting directly
 *
 * Revision 1.5  2003/09/12 16:31:17  cstolpe
 * locking stubs and block submit
 *
 * Revision 1.4  2003/09/11 21:45:29  cstolpe
 * Latest Updates
 *
 * Revision 1.3  2003/09/04 20:27:08  cstolpe
 * latest changes for 1.2H
 *
 * Revision 1.2  2003/06/20 12:50:38  cstolpe
 * Initial 1.2H port
 *
 * Revision 1.1  2002/08/07 16:06:12  cstolpe
 * CR0718026417 (CR0625023357) Multiple urls for V1.0.1
 *
 * Revision 1.0.0.1  2002/06/28 11:33:23  cstolpe
 * Initialize
 *
 * </pre>
 * @author Chris Stolpe
 * @version $Revision: 1.6 $
 */
public class Validate1State extends PageableState implements Observer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The profile currently in use
	 */
	private Profile profile = null;
	/**
	 * Did we get a lock on the package
	 */
	private boolean locked = false;
	/**
	 * Who has the lock on the package
	 */
	private String lockException = null;
	/**
	 * entityType
	 */
	private String entityType = null;
	/**
	 * Who has the lock on the package
	 */
	private int entityID = -1;
	/**
	 * CVS Version 
	 */
	public static final String VERSION = "$Revision: 1.6 $";  //$NON-NLS-1$
	/**
	 * Keep track of the states
	 */
	private Stack stack = new Stack();
	/**
	 * Cache the index 
	 */
	private IndexState index = null;
	/**
	 * Keep track of the language
	 */
	private NLSItem nls = null;
	/**
	 * The translated values 
	 */
	private	TranslationPackage metaPackage = null;
	private	TranslationMetaRequest metaRequest = null;
	private MetaTranslationGroup mtg = null;
	/**
	 * Keep track if any change has occurred
	 */
	private Boolean hasChanged = Boolean.FALSE;
	/**
     * Constructor
     *
     */
    public Validate1State() {
	}
	/**
     * Based on the package info retreive the translated data. Associate
     * the TranslationMetaAttributes with the ReturnDataRows from GBL2921.
     * Instead of using the ending date from the PackageInfo use the current
     * timestamp from the PDH (GBL2028) This will show us any changes to the
     * values we sent out and any new values. We will also know if any
     * values were deleted.
     * <pre>
     * Possible Map.Entry combinations
     * ReturnDataRow            -> ReturnDataRow // New value since translation
     * TranslationMetaAttribute -> ReturnDataRow // Current value. valFrom in ReturnDataRow indicates if it changed
     * TranslationMetaAttribute -> String        // Value deleted since translation
     * </pre>
     *
     * @param aType 
     * @param entityId
     * @param aNLS
     * @throws java.rmi.RemoteException
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @param db
     * @param aProfile
     * @pre aNLS != null
     * @pre aProfile != null
     */
	public void init(Database db, Profile aProfile, String aType, int entityId, NLSItem aNLS) 
		throws 
	java.rmi.RemoteException, 
	java.sql.SQLException,
	COM.ibm.opicmpdh.middleware.MiddlewareException,
	COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException 
	{
		PackageID pId;
		profile = aProfile;
		nls = aNLS;
		setEntityType(aType);
		setEntityID(entityId);

		// Get the Translation Package for this NLS
		pId = new PackageID(aType, entityId, nls.getNLSID(), "NOQUEUE", aProfile.getNow(), "");  //$NON-NLS-1$  //$NON-NLS-2$
		metaPackage = Translation.getETSPackage(db, aProfile, pId);
		if (metaPackage != null) {
			try {
				locked = metaPackage.lockForVerification(db, aProfile);
			}
			catch (LockException le) {
				// He might provide the person who has the lock in the exception
				lockException = le.getMessage();
			}
			if (locked) {
				NLSItem[] anls;
				List sort;
				Iterator i;
				metaRequest = metaPackage.getMetaRequest();

				// Based on the NLS in the package rerun the query
				anls = new NLSItem[] { nls };
				mtg = new MetaTranslationGroup(db, aProfile, anls);

				// Start the list with the package attributes
				sort = new ArrayList(metaRequest.getAttributesAsVector());
		
				// Add any new attributes to the list
				i = mtg.getMetaTranslationItem().values().iterator();
				while (i.hasNext()) {
					MetaTranslationItem mti = (MetaTranslationItem) i.next();
					if (metaRequest.getAttributeElement(mti.getKey()) == null) {
						sort.add(mti);
					}
				}
			
				// Sort the list
				Collections.sort(sort, new ValidateComparator());
			
				// Set up page helper
				setList(sort);
			}
		}
		stack.push(this);
	}
	/**
	 * Implements the  back, continue, edit, index, and gotoPage actions.
	 * <pre>
	 * back     - Goes back to the StatusState discarding any changes.
	 * continue - Goes to the Validate2State 
	 * edit     - Goes to the EditState
	 * index    - go to IndexState (cached)
	 * gotoPage - go to the page specified by <b>parameter</b>.
	 *            if not a valid number (e.g. letter) or page do nothing.
	 * </pre>
	 * @see State#doAction(Map)
	 */
	public void doAction(Map params) {
		Object action = params.get("action");  //$NON-NLS-1$
		if ("continue".equals(action)) {  //$NON-NLS-1$
			Validate2State newState = new Validate2State();
			newState.init(this);
			stack.push(newState);
		}
		else if ("edit".equals(action)) {  //$NON-NLS-1$
			Observable newState = new EditState();
			((EditState)newState).init(this);
			newState.addObserver(this);
			stack.push(newState);
		}
		else if ("index".equals(action)) {  //$NON-NLS-1$
			if (index == null) {
                index = new IndexState();
                index.init(this);
			}
			stack.push(index);
		}
		else if ("gotoPage".equals(params.get("action"))) {  //$NON-NLS-1$  //$NON-NLS-2$
			gotoPage(params);
		}
	}
	/**
	 * @see State#getStack()
	 */
	public Stack getStack() { return stack; }
	/**
	 * @see State#getStateID()
	 */
	public Integer getStateID() { return STATE_VALIDATE1; } 
	/**
	 * NLSItem accessor method for the view.
	 * @return NLSItem of completed package
	 */
	public NLSItem getNLS() {
		return nls;
	}
	/**
	 * Accessor method for the view. To determine if we got the lock
	 * @return boolean of completed package
	 */
	public boolean isLocked() {
		return locked;
	}
	/**
	 * Accessor method for the view. To determine who has the lock
	 * @return String of person
	 */
	public String getWhoLocked() {
		return lockException;
	}
	/**
     * TranslationPackage Accessor
     *
     * @return TranslationPackage
     */
	public TranslationPackage getMetaPackage() { return metaPackage; } 
	/**
     * Compare the english value sent out with tthe current english value.
     *
     * @return Boolean true if the english value changed
     * @param tma 
     */
	public boolean hasChanged(TranslationMetaAttribute tma) {
		boolean result = false;
		MetaTranslationItem mti = mtg.getMetaTranslationItem(tma.getKey());
		if (mti != null) {
			result = !mti.getLongDescription().equals(tma.getAttributeDescription());
		} // Dont count a delete as a change
		return result;
	}
	/**
     * Get the new english value.
     *
     * @return String new english value
     * @param tma 
     */
	public String getChanged(TranslationMetaAttribute tma) {
		MetaTranslationItem mti = mtg.getMetaTranslationItem(tma.getKey());
		return mti.getLongDescription();
	}
	/**
     * Determint if this value was deleted. Not in the current set
     *
     * @return Boolean true if the TranslationMetaAttribute is no longer in query
     * @param tmi 
     */
	public boolean isDeleted(TranslationMetaAttribute tmi) {
		boolean result = false;
		MetaTranslationItem mti = mtg.getMetaTranslationItem(tmi.getKey());
		if (mti == null) {
            result = true;
		}
		return result;
	}
	/**
	 * Needed by the view. The view will confirm the back action if the data
	 * was modified and not saved.
	 * @return Boolean true if any TranslationMetaAttribute was modified
	 */
	public Boolean isModified() { return hasChanged; }
	/**
	 * Implements Observer interface
	 * @see State#getStateID()
	 */
	public void update(Observable o, Object arg) {
		hasChanged = (Boolean) arg;
	}
	/**
     * Accessor for Profile
     *
     * @return Profile
     */
	public Profile getProfile() {
		return profile;
	}
	/**
     * Accessor for Profile
     *
     * @param aProfile 
     */
	protected void setProfile(Profile aProfile) {
		profile = aProfile;
	}

	void setEntityType(String aType) {
		this.entityType = aType;
	}

	String getEntityType() {
		return entityType;
	}

	void setEntityID(int aID) {
		this.entityID = aID;
	}

	int getEntityID() {
		return entityID;
	}
}
