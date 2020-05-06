// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2002, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.metaxlate;

import java.io.Serializable;
import java.util.*;
import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.transactions.NLSItem;
import COM.ibm.opicmpdh.translation.*;

/**
 * Retrieves the GBL2921 ReturnDataRows based on the PackageInfo
 * <pre>
 * Licensed Materials -- Property of IBM
 *
 * (c) Copyright International Business Machines Corporation, 2002, 2006
 * All Rights Reserved.
 *
 * CVS Log History
 * $Log: OriginalState.java,v $
 * Revision 1.5  2006/04/17 19:37:15  chris
 * JTest changes
 *
 * Revision 1.4  2006/03/10 21:11:30  chris
 * added serializeable and default constructors
 *
 * Revision 1.3  2006/03/10 20:00:07  chris
 * Make all versions final
 *
 * Revision 1.2  2006/01/26 20:52:51  sergio
 * AHE copyright
 *
 * Revision 1.1  2005/09/16 18:20:10  chris
 * Updates for Application Hosting Environment
 *
 * Revision 1.3  2005/02/16 17:50:20  chris
 * JTest cleanup
 *
 * Revision 1.2  2004/11/10 13:58:26  chris
 * Changes based on Daves emergency fix for CR0813042358
 *
 * Revision 1.1.1.1  2004/01/26 17:40:01  chris
 * Latest East Coast Source
 *
 * Revision 1.4  2003/09/11 21:45:29  cstolpe
 * Latest Updates
 *
 * Revision 1.3  2003/09/04 20:27:08  cstolpe
 * latest changes for 1.2H
 *
 * Revision 1.2  2003/06/20 12:50:37  cstolpe
 * Initial 1.2H port
 *
 * Revision 1.1  2002/08/07 16:06:12  cstolpe
 * CR0718026417 (CR0625023357) Multiple urls for V1.0.1
 *
 * Revision 1.0.0.1  2002/06/28 11:33:22  cstolpe
 * Initialize
 *
 * </pre>
 * @author Chris Stolpe
 * @version $Revision: 1.5 $
 */
public class OriginalState extends PageableState implements Serializable {
    /** Automatically generated javadoc for: serialVersionUID */
    private static final long serialVersionUID = 5073709158514760953L;
	/**
	 * CVS Version 
	 */
	public static final String VERSION = "$Revision: 1.5 $";  //$NON-NLS-1$
	/**
	 * Keep track of the states 
	 */
	private Stack stack = new Stack();
	/**
	 * Cache the index 
	 */
	private IndexState index = null;
	/**
	 * Profile
	 */
	private Profile profile = null;;
	/**
	 * Keep track of language
	 */
	private NLSItem nls;
	/**
     * Constructor
     *
     */
    public OriginalState() {
	}
	/**
     * Constructor retrievies the ReturnDataRows from the GBL2921 middleware call
     *
     * @param db
     * @param aProfile
     * @param entityType
     * @param entityId
     * @param aNLS
     * @throws java.rmi.RemoteException
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException 
     * @pre aNLS != null
     * @pre aProfile != null
     */
	public void init(Database db, Profile aProfile, String entityType, int entityId, NLSItem aNLS)
		throws 
	java.rmi.RemoteException, 
	java.sql.SQLException,
	COM.ibm.opicmpdh.middleware.MiddlewareException,
	COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException 
	{
		PackageID pId = new PackageID(entityType, entityId, aNLS.getNLSID(), "NOQUEUE", aProfile.getNow(), "");  //$NON-NLS-1$  //$NON-NLS-2$
		TranslationPackage tp = Translation.getPDHPackage(db, aProfile, pId);
		nls = aNLS;
		if (tp != null) {
			TranslationMetaRequest req = tp.getMetaRequest();
			List sort = new ArrayList(req.getAttributesAsVector());
			Collections.sort(sort, new TranslationMetaAttributeComparator());
			// Set up page helper
			setList(sort);
		}
		stack.push(this);
	}
	/**
	 * Implements the back, index, and gotoPage actions
	 * <pre>
	 * back     - go back to StatusState
	 * index    - go to IndexState (cached)
	 * gotoPage - go to the page specified by <b>parameter</b>.
	 *            if not a valid number (e.g. letter) or page do nothing.
	 * </pre>
	 * @see State#doAction(Map)
	 */
	public void doAction(java.util.Map params) {
		Object action = params.get("action");  //$NON-NLS-1$
		if ("index".equals(action)) {  //$NON-NLS-1$
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
	 * NLSItem accessor method for the view.
	 * @return NLSItem of completed package
	 */
	public NLSItem getNLS() {
		return nls;
	}
	/**
	 * @see State#getStack()
	 */
	public Stack getStack() { return stack; }
	/**
	 * @see State#getStateID()
	 */
	public Integer getStateID() { return STATE_ORIGINAL; } 
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
}
