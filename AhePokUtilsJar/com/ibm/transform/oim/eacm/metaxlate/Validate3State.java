// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2002, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.metaxlate;

import java.io.Serializable;
import java.util.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.translation.*;
import COM.ibm.opicmpdh.transactions.NLSItem;
import COM.ibm.opicmpdh.middleware.*;

/**
 * Implements the third step of the validation process. Here the user can save
 * the package if they made changes. Accept it if they fixed all truncations. Or
 * reject it.
 * <pre>
 * Licensed Materials -- Property of IBM
 *
 * (c) Copyright International Business Machines Corporation, 2002, 2006
 * All Rights Reserved.
 *
 * CVS Log History
 * $Log: Validate3State.java,v $
 * Revision 1.5  2006/04/17 19:37:15  chris
 * JTest changes
 *
 * Revision 1.4  2006/03/10 21:11:31  chris
 * added serializeable and default constructors
 *
 * Revision 1.3  2006/03/10 20:00:07  chris
 * Make all versions final
 *
 * Revision 1.2  2006/01/26 20:52:52  sergio
 * AHE copyright
 *
 * Revision 1.1  2005/09/16 18:20:10  chris
 * Updates for Application Hosting Environment
 *
 * Revision 1.2  2005/02/16 17:50:20  chris
 * JTest cleanup
 *
 * Revision 1.1.1.1  2004/01/26 17:40:01  chris
 * Latest East Coast Source
 *
 * Revision 1.9  2003/09/26 20:14:56  cstolpe
 * Fix or FB 52227:4C6DF4 once meta is corrected
 *
 * Revision 1.8  2003/09/25 18:37:47  cstolpe
 * Invoke Action instead of posting directly
 *
 * Revision 1.7  2003/09/12 16:31:17  cstolpe
 * locking stubs and block submit
 *
 * Revision 1.6  2003/09/11 21:45:29  cstolpe
 * Latest Updates
 *
 * Revision 1.5  2003/09/04 20:27:08  cstolpe
 * latest changes for 1.2H
 *
 * Revision 1.4  2003/06/20 12:50:38  cstolpe
 * Initial 1.2H port
 *
 * Revision 1.3  2002/08/27 16:34:10  cstolpe
 * Fix for PR21360
 *
 * Revision 1.2  2002/08/07 16:06:13  cstolpe
 * CR0718026417 (CR0625023357) Multiple urls for V1.0.1
 *
 * Revision 1.1  2002/07/18 13:24:47  cstolpe
 * Fix PR20833 Reject/Resend was doing Reject only. Missing Reject button.
 *
 * Revision 1.0.0.1  2002/06/28 11:33:23  cstolpe
 * Initialize
 *
 * </pre>
 * @author Chris Stolpe
 * @version $Revision: 1.5 $
 */
public class Validate3State extends Observable implements State, Serializable {
    /** Automatically generated javadoc for: serialVersionUID */
    private static final long serialVersionUID = -2372705382956758560L;
	/**
	 * CVS Version 
	 */
	public static final String VERSION = "$Revision: 1.5 $";  //$NON-NLS-1$
	/**
	 * Keep track of states
	 */
	private Stack stack;
	/**
	 * Profile
	 */
	private Profile profile;
	/**
	 * Keep track of the language
	 */
	private NLSItem nls;
	/**
	 * Keep track of the number of truncations
	 */
	private int nTruncations = 0;
	/**
	 * Has there been a change to the translation package
	 */
	private Boolean isModified = Boolean.FALSE;	
	/**
     * Constructor
     *
     */
    public Validate3State() {
	}
	/**
     * Constructor initializes the state stack, the number of truncations
     * and whether the translation packages has been modified.
     *
     * @param pState 
     * @pre pState != null
     */
	public void init(Validate2State pState) {
		Validate1State v1State;
		stack = pState.getStack();
		nTruncations = pState.getList().size();
		v1State = (Validate1State) stack.firstElement();
		profile = v1State.getProfile();
		isModified = v1State.isModified();
		nls = v1State.getNLS();
	}
	/**
	 * Implements the back, accept, reject, and save actions.
	 * <pre>
	 * back   - go back to the Validate2State
	 * accept - If there are no truncations set the status to validated and
	 *          post the changes to the PDH (save package if modified). Then
	 *          go back to the StatusState and refresh the package list
	 * reject - set the status to rejected and go to the StatusState and
	 *          refresh the package list
	 * resend - set the status to update requested and go to the StatusState and
	 *          refresh the package list
	 * save   - if the package has changed save it to the PDH. stay in the
	 *          Validate3State
	 * </pre>
	 * @see State#doAction(Map)
	 */
	public void doAction(Map params) 
		throws 
	java.sql.SQLException,
	COM.ibm.eannounce.objects.WorkflowException,
	COM.ibm.opicmpdh.middleware.MiddlewareException,
	COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException 
	{
		Object action = params.get("action");  //$NON-NLS-1$
		Validate1State v1State = (Validate1State) stack.firstElement();
		Database dbCurrent = (Database) params.get("dbCurrent");  //$NON-NLS-1$
		if ("back".equals(action)) {  //$NON-NLS-1$
			stack.pop();
			deleteObservers();
		}
		else if ("accept".equals(action)) {  //$NON-NLS-1$
			TranslationPackage metaPackage = v1State.getMetaPackage();
			if (nTruncations == 0) {
				EntityGroup eg;
				EntityItem[] aItems;
				WorkflowActionItem wfai;
				StringBuffer wfName = new StringBuffer("WFMETAXLATEGRP");  //$NON-NLS-1$
				wfName.append(nls.getNLSID());
				wfName.append("V"); //$NON-NLS-1$
				if (isModified == Boolean.TRUE) {
					Translation.putETSPackage(dbCurrent, v1State.getProfile(), metaPackage);
				}
				eg = new EntityGroup(null, dbCurrent, profile, v1State.getEntityType(), "Edit");  //$NON-NLS-1$
				aItems = new EntityItem[1];
				aItems[0] = new EntityItem(eg, profile, dbCurrent, v1State.getEntityType(), v1State.getEntityID());
				wfai = new WorkflowActionItem(null, dbCurrent, profile, wfName.toString());
				wfai.setEntityItems(aItems);
				//D.ebug("Validate3State:wfai=" + wfai.dump(false));
				dbCurrent.executeAction(profile, wfai);
				metaPackage.unlock(dbCurrent, v1State.getProfile());
				stack.clear();
				deleteObservers();
			}
		}
		else if ("reject".equals(action)) {  //$NON-NLS-1$
			//EntityGroup eg = new EntityGroup(null, dbCurrent, profile, entityType, "Edit");
			//EntityItem[] aItems = new EntityItem[1];
			//aItems[0] = new EntityItem(eg, profile, dbCurrent, entityType, entityID);
			//WorkflowActionItem wfai = new WorkflowActionItem(null, dbCurrent, profile, "WFMETAXLATEGRPJR"); 
			//wfai.setEntityItems(aItems);
			//dbCurrent.executeAction(profile, wfai);

			TranslationPackage metaPackage = v1State.getMetaPackage();
			Translation.setStatus(
				dbCurrent,
				v1State.getProfile(), 
				metaPackage.getPackageID(),
				Translation.STATUS_XLATE_REJECTED);
			metaPackage.unlock(dbCurrent, v1State.getProfile());
			stack.clear();
			deleteObservers();
		}
		else if ("save".equals(action) && isModified == Boolean.TRUE) {  //$NON-NLS-1$
			TranslationPackage metaPackage = v1State.getMetaPackage();
			// return to page or status?
			Translation.putETSPackage(dbCurrent, v1State.getProfile(), metaPackage);
			isModified = Boolean.FALSE; // Added for PR21360
			setChanged();
			notifyObservers(Boolean.FALSE);
			deleteObservers();
		}
	}
	/**
	 * @see State#getStack()
	 */
	public Stack getStack() { return stack; }
	/**
	 * @see State#getStateID()
	 */
	public Integer getStateID() { return STATE_VALIDATE3; }	
	/**
	 * NLSItem accessor method for the view.
	 * @return NLSItem of completed package
	 */
	public NLSItem getNLS() {
		return nls;
	}
	/**
	 * Has the translation package been modified
	 * @return boolean true if modified
	 */
	public boolean isModified() { return Boolean.TRUE == isModified; }
	/**
	 * Are there any values that need truncation in the package
	 * @return boolean true if there are
	 */
	public boolean needsTruncation() { return nTruncations > 0; }
	/**
     * Accessor for Profile
     *
     * @return Profile
     */
	protected Profile getProfile() {
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
}
