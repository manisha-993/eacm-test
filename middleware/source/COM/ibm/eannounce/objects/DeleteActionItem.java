//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: DeleteActionItem.java,v $
// Revision 1.77  2013/08/06 19:14:33  wendy
// add user info to locked msg
//
// Revision 1.76  2010/05/19 14:48:17  wendy
// clean up error msg
//
// Revision 1.75  2009/11/03 18:56:09  wendy
// use EANUtility methods that do the same thing
//
// Revision 1.74  2009/05/18 23:15:58  wendy
// Support dereference for memory release
//
// Revision 1.73  2009/05/11 13:51:29  wendy
// Support turning off domain check for all actions
//
// Revision 1.72  2009/03/12 14:59:14  wendy
// Add methods for metaui access
//
// Revision 1.71  2008/06/18 16:37:07  wendy
// Check for null before rs.close()
//
// Revision 1.70  2008/02/01 22:10:08  wendy
// Cleanup RSA warnings
//
// Revision 1.69  2007/08/03 11:25:44  wendy
// RQ0713072645-1 Make actions sensitive to the PROFILE's PDHDOMAINs
//
// Revision 1.68  2007/06/08 17:55:30  wendy
// RQ1103063214 "XCC GX SR001.5.001 EACM Enabling Technology - 'No Delete FLAG' Rule"
//
// Revision 1.67  2005/08/22 18:13:46  joan
// fixes
//
// Revision 1.66  2005/08/10 16:14:23  tony
// improved catalog viewer functionality.
//
// Revision 1.65  2005/08/03 17:09:43  tony
// added datasource logic for catalog mod
//
// Revision 1.64  2005/05/10 18:26:59  joan
// fixes
//
// Revision 1.63  2005/04/21 17:05:54  joan
// add message
//
// Revision 1.62  2005/04/20 17:53:30  joan
// fixes
//
// Revision 1.61  2005/04/20 17:33:06  joan
// work on CR
//
// Revision 1.60  2005/03/28 22:02:14  joan
// input prof NLSID for sp
//
// Revision 1.59  2005/03/24 18:05:00  gregg
// just some comments
//
// Revision 1.58  2005/03/24 18:02:19  gregg
// throw exception in expireRelatorReferences
//
// Revision 1.57  2005/03/24 18:00:22  gregg
// more gbl2266 for relator T-Bone case
//
// Revision 1.56  2005/03/24 00:25:04  gregg
// returning deleted relators in 2266
//
// Revision 1.55  2005/03/11 20:41:15  roger
// Foreign ABRs
//
// Revision 1.54  2005/02/14 17:25:16  dave
// Variables and formatting
//
// Revision 1.53  2005/02/14 17:18:33  dave
// more jtest fixing
//
// Revision 1.52  2005/01/18 21:33:09  dave
// removing the debug parms from code (sp internalized them)
//
// Revision 1.51  2004/10/11 21:17:32  dave
// syntax
//
// Revision 1.50  2004/10/11 21:11:20  dave
// change to delete
//
// Revision 1.49  2004/07/15 17:28:59  joan
// add Single input
//
// Revision 1.48  2004/06/11 21:08:52  joan
// check for entities' existence in relator table before deleting them
//
// Revision 1.47  2003/10/30 00:56:16  dave
// more profile fixes
//
// Revision 1.46  2003/10/30 00:43:30  dave
// fixing all the profile references
//
// Revision 1.45  2003/08/18 21:18:52  dave
// isLocked Modifier
//
// Revision 1.44  2003/05/30 20:21:24  dave
// adding DeleteActionItem constructors
//
// Revision 1.43  2003/05/19 16:03:39  dave
// Clean up for descriptions, and exception flagging
//
// Revision 1.42  2003/04/30 21:54:35  dave
// making sure refreshClassifications is fired when the rows
// are pulled for the rowselectable table
//
// Revision 1.41  2003/04/30 20:42:21  dave
// more trace
//
// Revision 1.40  2003/04/30 20:08:06  dave
// Putting in some trace
//
// Revision 1.39  2003/04/17 18:41:34  dave
// syntax fixes
//
// Revision 1.38  2003/04/17 17:56:14  dave
// clean up link,deactivate, tagging
//
// Revision 1.37  2003/03/27 23:07:20  dave
// adding some timely commits to free up result sets
//
// Revision 1.36  2003/03/17 17:25:00  dave
// Tagging Phase II - entity prep
//
// Revision 1.35  2003/01/08 01:16:02  joan
// fix errors
//
// Revision 1.34  2003/01/07 23:20:43  joan
// fix message
//
// Revision 1.33  2003/01/07 21:45:07  joan
// fix message
//
// Revision 1.32  2003/01/07 18:23:29  joan
// throw orphan exception
//
// Revision 1.31  2003/01/07 01:34:31  joan
// fix error
//
// Revision 1.30  2003/01/07 01:04:11  joan
// add check NoOrphan
//
// Revision 1.29  2002/11/19 23:26:54  joan
// fix hasLock method
//
// Revision 1.28  2002/11/19 18:39:43  joan
// fix compile
//
// Revision 1.27  2002/11/19 18:27:41  joan
// adjust lock, unlock
//
// Revision 1.26  2002/11/19 00:06:25  joan
// adjust isLocked method
//
// Revision 1.25  2002/11/12 17:59:16  dave
// Syntax
//
// Revision 1.24  2002/11/12 17:32:28  dave
// Syntax
//
// Revision 1.23  2002/11/12 17:18:27  dave
// System.out.println clean up
//
// Revision 1.22  2002/10/29 22:50:25  dave
// trapping the LockException and rethrowing it to the client
//
// Revision 1.21  2002/10/04 14:54:28  dave
// clipping on remote send call on entityItem parm
//
// Revision 1.20  2002/09/11 17:57:34  joan
// fix rexec
//
// Revision 1.19  2002/09/10 18:28:18  gregg
// updatePdhMeta logic
//
// Revision 1.18  2002/08/23 21:59:55  gregg
// updatePdhMeta method throws MiddlewareException
//
// Revision 1.17  2002/08/23 21:29:45  gregg
// updatePdhMeta(Database,boolean) method
//
// Revision 1.16  2002/05/14 18:07:43  joan
// working on LockActionItem
//
// Revision 1.15  2002/05/10 21:06:20  joan
// compiling errors
//
// Revision 1.14  2002/05/06 23:41:24  joan
// fixing error
//
// Revision 1.13  2002/05/03 22:51:16  joan
// thow exception
//
// Revision 1.12  2002/05/03 22:40:39  joan
// fix throwing exception
//
// Revision 1.11  2002/05/03 18:02:33  joan
// fixing null pointer
//
// Revision 1.10  2002/05/03 17:42:28  joan
// fix bugs
//
// Revision 1.9  2002/05/03 17:37:26  joan
// fixing executeAction
//
// Revision 1.8  2002/05/03 16:42:54  joan
// debug
//
// Revision 1.7  2002/05/02 20:37:44  joan
// fix import
//
// Revision 1.6  2002/05/02 20:21:45  joan
// work on delete
//
// Revision 1.5  2002/05/02 20:09:15  joan
// working on DeleteActionItem
//
// Revision 1.4  2002/04/16 01:02:50  dave
// missing import
//
// Revision 1.3  2002/04/16 00:52:40  dave
// more syntax
//
// Revision 1.2  2002/04/16 00:39:43  dave
// syntax
//
// Revision 1.1  2002/04/16 00:27:12  dave
// New Method for Entity Deletion
//
//

package COM.ibm.eannounce.objects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.rmi.RemoteException;

import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.ReturnID;
import COM.ibm.opicmpdh.middleware.DatePackage;
import COM.ibm.opicmpdh.middleware.LockException;

/**
 * DeleteActionItem
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DeleteActionItem extends EANActionItem {

    static final long serialVersionUID = 20011106L;

    /**
     * FIELD
     */
    protected String m_strEntityType = null; // Holds the EntityType we are removing
    /**
     * FIELD
     */
    protected EANList m_el = new EANList();
    /**
     * FIELD
     */
    protected boolean m_bOwnerCheck = false;

    public void dereference(){
    	super.dereference();
    	m_strEntityType = null;
    	if (m_el !=null){
    		for (int i=0; i<m_el.size(); i++){
    			EntityItem mt = (EntityItem) m_el.getAt(i);
    			if (mt != null){
    				mt.dereference();
    			}
    		}
    		m_el.clear();
    		m_el = null;
    	}
    }
    /**
     * Main method which performs a simple test of this class
     *
     * @param arg
     */
    public static void main(String arg[]) {
    }

    /*
    * Version info
    */
    /**
     * (non-Javadoc)
     * getVersion
     *
     * @see COM.ibm.eannounce.objects.EANFoundation#getVersion()
     */
    public String getVersion() {
        return "$Id: DeleteActionItem.java,v 1.77 2013/08/06 19:14:33 wendy Exp $";
    }

    /*
    * Instantiate a new ActionItem based upon a dereferenced version of the Existing One
    */
    /**
     * DeleteActionItem
     *
     * @param _ai
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public DeleteActionItem(DeleteActionItem _ai) throws MiddlewareRequestException {
        super(_ai);
        m_strEntityType = _ai.m_strEntityType;
        m_el = _ai.m_el;
        m_bOwnerCheck = _ai.m_bOwnerCheck;
    }

    /**
     * DeleteActionItem
     *
     * @param _mf
     * @param _ai
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public DeleteActionItem(EANMetaFoundation _mf, DeleteActionItem _ai) throws MiddlewareRequestException {
        super(_mf, _ai);
        m_strEntityType = _ai.m_strEntityType;
        m_el = _ai.m_el;
        m_bOwnerCheck = _ai.m_bOwnerCheck;
    }

    /**
     * This represents a Delete Action Item.  It can only be constructed via a database connection, a Profile, and an Action Item Key
     *
     * @param _emf
     * @param _db
     * @param _prof
     * @param _strActionItemKey
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     */
    public DeleteActionItem(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {

        super(_emf, _db, _prof, _strActionItemKey);

        // Lets go get the information pertinent to the Create Action Item

        try {
        	setDomainCheck(true);
            ReturnStatus returnStatus = new ReturnStatus(-1);
            ResultSet rs = null;
            ReturnDataResultSet rdrs;
            Profile prof = getProfile();
            try {
                rs = _db.callGBL7030(returnStatus, prof.getEnterprise(), _strActionItemKey, prof.getValOn(), prof.getEffOn());
                rdrs = new ReturnDataResultSet(rs);
            } finally {
            	if (rs != null){
            		rs.close();
                	rs = null;
            	}
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }

            // Set the class and description...
            for (int ii = 0; ii < rdrs.size(); ii++) {
                String strType = rdrs.getColumn(ii, 0);
                String strCode = rdrs.getColumn(ii, 1);
                String strValue = rdrs.getColumn(ii, 2);

                _db.debug(D.EBUG_SPEW, "gbl7030 answer is:" + strType + ":" + strCode + ":" + strValue + ":");

                // Collect the attributes
                if (strType.equals("TYPE") && strCode.equals("EntityType")) {
                    setEntityType(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("Owner")) {
                    setOwnerCheck(true);
                } else if (strType.equals("TYPE") && strCode.equals("SingleInput")) {
                    setSingleInput(true);
				} else if (strType.equals("TYPE") && strCode.equals("DataSource")) {	//catalog enhancement
					setDataSource(strValue);											//catalog enhancement
				} else if (strType.equals("TYPE") && strCode.equals("DataSourceParms")) {
					setAdditionalParms(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("DomainCheck")) { // default is on, allow off
				    setDomainCheck(!strValue.equals("N")); //RQ0713072645
                } else {
                    _db.debug(D.EBUG_ERR, "No home for this Action/Attribute" + strType + ":" + strCode + ":" + strValue);
                }

            }

        } finally {
            _db.freeStatement();
            _db.isPending();
        }
    }

    /*
    * Dumps the contents of this object to a String for review
    */
    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {
        StringBuffer strbResult = new StringBuffer();
        strbResult.append("DeleteActionItem:" + super.dump(_bBrief));
        strbResult.append("\n\tTarget EntityType is:" + getEntityType());
        return strbResult.toString();
    }

    /**
     * (non-Javadoc)
     * getPurpose
     *
     * @see COM.ibm.eannounce.objects.EANActionItem#getPurpose()
     */
    public String getPurpose() {
        return "Delete";
    }

    /**
     * getEntityType
     *
     * @return
     *  @author David Bigelow
     */
    public String getEntityType() {
        return m_strEntityType;
    }

    /**
     * setEntityType
     *
     * @param _str
     *  @author David Bigelow
     */
    protected void setEntityType(String _str) {
        m_strEntityType = _str;
    }
    /*********
     * meta ui must update action
     */
    public void setActionClass(){
    	setActionClass("Delete");
    }
    public void updateAction(String strET){
    	setEntityType(strET);
    } 
    /**
     * checkOwnership
     *
     * @return
     *  @author David Bigelow
     */
    public boolean checkOwnership() {
        return m_bOwnerCheck;
    }

    /**
     * setOwnerCheck
     *
     * @param _b
     *  @author David Bigelow
     */
    protected void setOwnerCheck(boolean _b) {
        m_bOwnerCheck = _b;
    }

    /**
     * This is the array of EntityItems that will be deactivated
     * It will search for any immediate relatives (uplink or downlink)
     * To ensure that a match is found
     *
     * @param _aei
     */
    public void setEntityItems(EntityItem[] _aei) {

        resetEntityItems();
        for (int ii = 0; ii < _aei.length; ii++) {
            EntityItem ei = _aei[ii];
            EntityItem processedEI = null;
            boolean bMatch = false;
            if (!ei.getEntityType().equals(getEntityType())) {
                //System.out.println("DeleteActionItem.setEntityItems().walking *down* Entity Chain to find match." + ei.getEntityType() + ":" + getEntityType());
                EntityGroup eg = (EntityGroup) ei.getParent();
                // It cannot be added to the list
                if (eg == null) {
                    continue;
                }
                if (eg.isRelator() || eg.isAssoc()) {
                    //check the child entity item
                    for (int i = 0; i < ei.getDownLinkCount(); i++) {
                        EntityItem eic = (EntityItem) ei.getDownLink(i);
                        if (eic != null) {
                            if (eic.getEntityType().equals(getEntityType())) {
                                processedEI = eic;
                                bMatch = true;
                                break;
                            }
                        }
                    }

                    if (!bMatch) {
                        for (int i = 0; i < ei.getUpLinkCount(); i++) {
                            EntityItem eip = (EntityItem) ei.getUpLink(i);
                            if (eip != null) {
                                if (eip.getEntityType().equals(getEntityType())) {
                                    processedEI = eip;
                                    bMatch = true;
                                    break;
                                }
                            }
                        }
                    }
                }

            } else {
                processedEI = ei;
                bMatch = true;
            }

            // If you found a match on entitytype.. please put it on the list
            if (bMatch) {
                m_el.put(processedEI);
            } else {
                System.out.println("DeleteActionItem.setEntityItems(). Could not find any matching Entity Types for:" + getEntityType());
            }
        }
    }

    /**
     * resetEntityItems
     *
     *  @author David Bigelow
     */
    public void resetEntityItems() {
        m_el = new EANList();
    }

    /**
     * exec
     *
     * @param _db
     * @param _prof
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.LockException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws COM.ibm.eannounce.objects.EANBusinessRuleException
     *  @author David Bigelow
     */
    public void exec(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareRequestException, LockException, MiddlewareShutdownInProgressException, EANBusinessRuleException {
        _db.executeAction(_prof, this);
        resetEntityItems();
    }

    /**
     * rexec
     *
     * @param _rdi
     * @param _prof
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws java.rmi.RemoteException
     * @throws COM.ibm.opicmpdh.middleware.LockException
     * @throws COM.ibm.eannounce.objects.EANBusinessRuleException
     *  @author David Bigelow
     */
    public void rexec(RemoteDatabaseInterface _rdi, Profile _prof) throws MiddlewareRequestException, MiddlewareShutdownInProgressException, MiddlewareException, RemoteException, LockException, EANBusinessRuleException {
		EntityList.checkDomain(_prof,this,m_el); //RQ0713072645

        EANList el = new EANList();
        // This guy preps the information for RDI since this is a remote call we need to strip
        // We neeed the make new entity
        for (int ii = 0; ii < m_el.size(); ii++) {
            EntityItem ei = (EntityItem) m_el.getAt(ii);
            el.put(new EntityItem(null, _prof, ei.getEntityType(), ei.getEntityID()));
        }
        m_el = el;

        // OK .. we now have modified EntityItems with no more linkage
        // Call the remote object.. then do some clean up
        _rdi.executeAction(_prof, new DeleteActionItem(this));
        resetEntityItems();
    }

    /**
     * executeAction
     *
     * @param _db
     * @param _prof
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.LockException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws COM.ibm.eannounce.objects.EANBusinessRuleException
     *  @author David Bigelow
     */
    public void executeAction(Database _db, Profile _prof) throws SQLException, MiddlewareException, LockException, MiddlewareShutdownInProgressException, EANBusinessRuleException {
        String strTraceBase = " DeleteActionItem executeAction method";
        // Initialize some SP specific objects needed in this method
        ReturnDataResultSet rdrs = null;
        ResultSet rs = null;

        EntityList.checkDomain(_prof,this,m_el); //RQ0713072645

        ReturnStatus returnStatus = new ReturnStatus(-1);

        EntityItemException eie = null;

        // Placeholders for dates
        String strNow = null;

        // Pull some profile info...
        int iOPWGID = _prof.getOPWGID();
        int iTranID = _prof.getTranID();
        int iSessionID = _prof.getSessionID();
        String strEnterprise = _prof.getEnterprise();
        int iNLSID = _prof.getReadLanguage().getNLSID();

        try {
            _db.debug(D.EBUG_DETAIL, strTraceBase);
            _db.test(m_el.size() > 0, "104035 No Items are in the EntityItems to Delete.");
            
            EntityItem lockOwnerEI = new EntityItem(null, _prof, Profile.OPWG_TYPE, _prof.getOPWGID());
            DatePackage dpNow = _db.getDates();
            LockList lockL = new LockList(_prof);

            strNow = dpNow.getNow();

            // first try to lock the entity items before deactivating them
            _db.debug(D.EBUG_DETAIL, strTraceBase + " ready to lock entity items");
            lockL = new LockList(_prof);
            for (int i = 0; i < m_el.size(); i++) {
                EntityItem ei = (EntityItem) m_el.getAt(i);
                if (!ei.isLocked(null, _db, lockL, _prof, lockOwnerEI, LockGroup.LOCK_NORMAL, "NOOP3", true)) {
                    _db.debug(D.EBUG_DETAIL, strTraceBase + " Cannot get all softlocks needed, ei "+ei.getKey());
                    _db.debug(D.EBUG_DETAIL, strTraceBase + " Backing Out SoftLocks and throwing error");
                    // We need to unlock anything we have locked!
                    for (int ii = 0; ii < m_el.size(); ii++) {
                        EntityItem lockedEI = (EntityItem) m_el.getAt(ii);
                        if (lockedEI.hasLock(lockOwnerEI, _prof)) {
                            lockedEI.unlock(null, _db, lockL, _prof, lockOwnerEI, LockGroup.LOCK_NORMAL);
                        }
                    }
                    LockGroup lock = ei.getLockGroup();
                    throw new LockException(lock+" Please try again later");
//                    throw new LockException("Some Entities are locked. Please try again later");
                }
            }

            try {
                //Returns all Relator information from a given entitytype
                rs = _db.callGBL8203(returnStatus, strEnterprise, getEntityType(), _prof.getValOn(), _prof.getEffOn());
                rdrs = new ReturnDataResultSet(rs);
            } finally {
            	if (rs != null){
            		rs.close();
                	rs = null;
            	}
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }

            for (int ii = 0; ii < rdrs.size(); ii++) {
                String strRelatorType = rdrs.getColumn(ii, 0).trim();
                String strLinkValue = rdrs.getColumn(ii, 3).trim();
                _db.debug(D.EBUG_SPEW, "gbl8203 answer is:" + strRelatorType + ":" + strLinkValue);
                if (strLinkValue.charAt(0) == 'Y') { // prevent orphans
                    setOwnerCheck(true);
                }
            }

            // OK.. we will now
            //deactivate the entity items.
            eie = new EntityItemException();
            // do it once here
    		EntityGroup eg = new EntityGroup(null, _db, _prof, ((EntityItem) m_el.getAt(0)).getEntityType(), "Edit");
    		_db.debug(D.EBUG_SPEW,"DeleteActionItem: "+eg.getEntityType()+" has dependentAttrValues? " + eg.hasDependentAttributeValue());

    		// check for any status that prevents removal
    		EANList deleteAbleList = EANUtility.canDelete(_db, _prof, m_el, eie);
    		
            for (int i = 0; i < deleteAbleList.size(); i++) {
                EntityItem ei = (EntityItem) deleteAbleList.getAt(i);

                String strEntityType = ei.getEntityType();
                int iEntityID = ei.getEntityID();

                boolean bProceed = true;
                if (checkOwnership()) {
                    int iCount = 0;
                    try {
                    	//find all relators with RelatorType, Entity2Type and Entity2ID, giving RelatorType, relatorID
                        rs = _db.callGBL8202(returnStatus, strEnterprise, _prof.getOPWGID(), strEntityType, iEntityID, _prof.getValOn(), _prof.getEffOn());
                        rdrs = new ReturnDataResultSet(rs);
                    } finally {
                    	if (rs != null){
                    		rs.close();
                        	rs = null;
                    	}
                        _db.commit();
                        _db.freeStatement();
                        _db.isPending();
                    }
                    for (int ii = 0; ii < rdrs.size(); ii++) {
                        String strRelatorType = rdrs.getColumn(ii, 0).trim();
                        int iRelatorID = rdrs.getColumnInt(ii, 1);

                        _db.debug(D.EBUG_SPEW, "gbl8202 answer is:" + strRelatorType + ":" + iRelatorID);
                        if (strRelatorType.equals(strEntityType) && iRelatorID != iEntityID) {
                            iCount++;
                        }
                    }

                    if (iCount == 0) {
                        bProceed = false;
                        eie.add(new EntityItem(ei), " Unable to remove this relationship because it creates an orphan.");
                    }
                }

				if (!canDeleteFlag(_db, eg, _prof, ei, eie)){ //RQ1103063214
					bProceed = false;
				}

                if (bProceed) {
                    _db.debug(D.EBUG_DETAIL, strTraceBase + " Deactivating Entity: " + strEntityType + ":" + iEntityID);

                    // here is where we perform some expiring of entities/relators this entity/relator points to, and things
                    // that point to this entity/relator. In the case of a relator pointing to a relator, we will recurse.
                    EANUtility.expireRelatorReferences(_db, returnStatus, strEnterprise, iOPWGID, strEntityType, iEntityID, iTranID, 0);

                    // turn off all attributes...
                    _db.callGBL2968(returnStatus, strEnterprise, strEntityType, iEntityID, iOPWGID, iTranID);
                    _db.commit();
                    _db.freeStatement();
                    _db.isPending();

                    // basically, is THIS entity a relator
                    rs = _db.callGBL2933(returnStatus, strEnterprise, strEntityType, iEntityID, iOPWGID, strNow, strNow);
                    rdrs = new ReturnDataResultSet(rs);
                    rs.close();
                    rs = null;
                    _db.commit();
                    _db.freeStatement();
                    _db.isPending();

                    // if relator -> delete relator record
                    if (rdrs.size() > 0) {
                        _db.callGBL2099(returnStatus, iOPWGID, strEnterprise, strEntityType, iEntityID, iTranID);
                        _db.commit();
                        _db.freeStatement();
                        _db.isPending();
                    }

                    // some session tagging
                    _db.callGBL2092(returnStatus, iOPWGID, iSessionID, strEnterprise, strEntityType, new ReturnID(iEntityID), iTranID, strNow, strNow, iNLSID);
                    _db.commit();
                    _db.freeStatement();
                    _db.isPending();
                }
            }
            deleteAbleList.clear();

            _db.debug(D.EBUG_DETAIL, strTraceBase + " ready to unlock entity items");

            //unlock entity items
            for (int i = 0; i < m_el.size(); i++) {
                EntityItem ei = (EntityItem) m_el.getAt(i);
                if (ei.hasLock(lockOwnerEI, _prof)) {
                    ei.unlock(null, _db, lockL, _prof, lockOwnerEI, LockGroup.LOCK_NORMAL);
                }
            }

            if (eie.getErrorCount() > 0) {
                throw eie;
            }
        } finally {
            _db.freeStatement();
            _db.isPending();
            _db.commit();
        }
    }
    /*
    public void executeAction(Database _db, Profile _prof) throws SQLException, MiddlewareException, LockException, MiddlewareShutdownInProgressException, EANBusinessRuleException {
        String strTraceBase = " DeleteActionItem executeAction method";
        // Initialize some SP specific objects needed in this method
        ReturnDataResultSet rdrs = null;
        ResultSet rs = null;

        EntityList.checkDomain(_prof,this,m_el); //RQ0713072645

        ReturnStatus returnStatus = new ReturnStatus(-1);

        EntityItemException eie = new EntityItemException();

        // Placeholders for dates
        String strNow = null;

        // Pull some profile info...
        int iOPWGID = _prof.getOPWGID();
        int iTranID = _prof.getTranID();
        int iSessionID = _prof.getSessionID();
        String strEnterprise = _prof.getEnterprise();
        int iNLSID = _prof.getReadLanguage().getNLSID();

        try {
            EntityItem lockOwnerEI = new EntityItem(null, _prof, Profile.OPWG_TYPE, _prof.getOPWGID());
            DatePackage dpNow = _db.getDates();
            LockList lockL = new LockList(_prof);

            strNow = dpNow.getNow();

            _db.debug(D.EBUG_DETAIL, strTraceBase);
            _db.test(m_el.size() > 0, "104035 No Items are in the EntityItems to Delete.");

            // first try to lock the entity items before deactivating them
            _db.debug(D.EBUG_DETAIL, strTraceBase + " ready to lock entity items");
            lockL = new LockList(_prof);
            for (int i = 0; i < m_el.size(); i++) {
                EntityItem ei = (EntityItem) m_el.getAt(i);
                if (!ei.isLocked(null, _db, lockL, _prof, lockOwnerEI, LockGroup.LOCK_NORMAL, "NOOP3", true)) {
                    _db.debug(D.EBUG_DETAIL, strTraceBase + " Cannot get all softlocked needed ");
                    _db.debug(D.EBUG_DETAIL, strTraceBase + " Backing Out SoftLocks and throwing error");
                    // We need to unlock anything we have locked!
                    for (int ii = 0; ii < m_el.size(); ii++) {
                        EntityItem lockedEI = (EntityItem) m_el.getAt(ii);
                        if (lockedEI.hasLock(lockOwnerEI, _prof)) {
                            lockedEI.unlock(null, _db, lockL, _prof, lockOwnerEI, LockGroup.LOCK_NORMAL);
                        }
                    }
                    throw new LockException(strTraceBase + "Some Entities locked.. please try again later");
                }
            }

            try {
                //Returns all Relator information from a given entitytype
                rs = _db.callGBL8203(returnStatus, strEnterprise, getEntityType(), _prof.getValOn(), _prof.getEffOn());
                rdrs = new ReturnDataResultSet(rs);
            } finally {
            	if (rs != null){
            		rs.close();
                	rs = null;
            	}
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }

            for (int ii = 0; ii < rdrs.size(); ii++) {
                String strRelatorType = rdrs.getColumn(ii, 0).trim();
                String strLinkValue = rdrs.getColumn(ii, 3).trim();
                _db.debug(D.EBUG_SPEW, "gbl8203 answer is:" + strRelatorType + ":" + strLinkValue);
                if (strLinkValue.charAt(0) == 'Y') { // prevent orphans
                    setOwnerCheck(true);
                }
            }

            // OK.. we will now
            //deactivate the entity items.
            eie = new EntityItemException();
            for (int i = 0; i < m_el.size(); i++) {
                EntityItem ei = (EntityItem) m_el.getAt(i);

                String strEntityType = ei.getEntityType();
                int iEntityID = ei.getEntityID();

                boolean bProceed = true;
                if (checkOwnership()) {
                    int iCount = 0;
                    try {
                    	//find all relators with RelatorType, Entity2Type and Entity2ID, giving RelatorType, relatorID
                        rs = _db.callGBL8202(returnStatus, strEnterprise, _prof.getOPWGID(), strEntityType, iEntityID, _prof.getValOn(), _prof.getEffOn());
                        rdrs = new ReturnDataResultSet(rs);
                    } finally {
                    	if (rs != null){
                    		rs.close();
                        	rs = null;
                    	}
                        _db.commit();
                        _db.freeStatement();
                        _db.isPending();
                    }
                    for (int ii = 0; ii < rdrs.size(); ii++) {
                        String strRelatorType = rdrs.getColumn(ii, 0).trim();
                        int iRelatorID = rdrs.getColumnInt(ii, 1);

                        _db.debug(D.EBUG_SPEW, "gbl8202 answer is:" + strRelatorType + ":" + iRelatorID);
                        if (strRelatorType.equals(strEntityType) && iRelatorID != iEntityID) {
                            iCount++;
                        }
                    }

                    if (iCount <= 0) {
                        bProceed = false;
                        eie.add(new EntityItem(ei), " Unable to remove this relationship because it creates an orphan.");
                    }
                }

				if (!EANUtility.canDelete(_db, _prof, ei)) { // use utils code
					bProceed = false;
					eie.add(new EntityItem(ei), " Unable to remove the entity because the parents are in final status.");
				}

				if (!canDeleteFlag(_db, _prof, ei, eie)){ //RQ1103063214
					bProceed = false;
				}

                if (bProceed) {
                    _db.debug(D.EBUG_DETAIL, strTraceBase + " Deactivating Entity: " + strEntityType + ":" + iEntityID);

                    // here is where we perform some expiring of entities/relators this entity/relator points to, and things
                    // that point to this entity/relator. In the case of a relator pointing to a relator, we will recurse.
                    expireRelatorReferences(_db, returnStatus, strEnterprise, iOPWGID, strEntityType, iEntityID, iTranID, 0);

                    // turn off all attributes...
                    _db.callGBL2968(returnStatus, strEnterprise, strEntityType, iEntityID, iOPWGID, iTranID);
                    _db.commit();
                    _db.freeStatement();
                    _db.isPending();

                    // basically, is THIS entity a relator
                    rs = _db.callGBL2933(returnStatus, strEnterprise, strEntityType, iEntityID, iOPWGID, strNow, strNow);
                    rdrs = new ReturnDataResultSet(rs);
                    rs.close();
                    rs = null;
                    _db.commit();
                    _db.freeStatement();
                    _db.isPending();

                    // if relator -> delete relator record
                    if (rdrs.size() > 0) {
                        _db.callGBL2099(returnStatus, iOPWGID, strEnterprise, strEntityType, iEntityID, iTranID);
                        _db.commit();
                        _db.freeStatement();
                        _db.isPending();
                    }

                    // some session tagging
                    _db.callGBL2092(returnStatus, iOPWGID, iSessionID, strEnterprise, strEntityType, new ReturnID(iEntityID), iTranID, strNow, strNow, iNLSID);
                    _db.commit();
                    _db.freeStatement();
                    _db.isPending();
                }
            }

            _db.debug(D.EBUG_DETAIL, strTraceBase + " ready to unlock entity items");

            //unlock entity items
            for (int i = 0; i < m_el.size(); i++) {
                EntityItem ei = (EntityItem) m_el.getAt(i);
                if (ei.hasLock(lockOwnerEI, _prof)) {
                    ei.unlock(null, _db, lockL, _prof, lockOwnerEI, LockGroup.LOCK_NORMAL);
                }
            }

            if (eie.getErrorCount() > 0) {
                throw eie;
            }
        } finally {
            _db.freeStatement();
            _db.isPending();
            _db.commit();
        }
    }*/

/**
 * Here is where we perform some expiring of entities/relators this entity/relator points to, and things
 * that point to this entity/relator. In the case of a relator pointing to a relator, we will recurse.
 * /
    private final void expireRelatorReferences(Database _db, ReturnStatus _returnStatus, String _strEnterprise, int _iOPWGID, String _strEntityType, int _iEntityID, int _iTranID, int _iLvl) throws SQLException, MiddlewareException {

        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;

	    // let's get back the answer of what we turned off in the form of an EntityType/EntityID...
		rs = _db.callGBL2266(_returnStatus, _strEnterprise, _iOPWGID, _strEntityType, _iEntityID, _iTranID);
		rdrs = new ReturnDataResultSet(rs);
		rs.close();
		rs = null;
		_db.commit();
		_db.freeStatement();
		_db.isPending();

		// now go through all relators we expired, and recursively whack their references IF itsa T-Bone.
		for(int iii = 0; iii < rdrs.getRowCount(); iii++) {
		    String strRelType = rdrs.getColumn(iii,0);
			int iRelID = rdrs.getColumnInt(iii,1);
			_db.debug(D.EBUG_SPEW,"GBL2266 answer:" + strRelType + ":" + iRelID + ":Lvl" + _iLvl);
			expireRelatorReferences(_db,_returnStatus,_strEnterprise,_iOPWGID, strRelType, iRelID, _iTranID, ++_iLvl);
		}
	}*/

    /**
     * (non-Javadoc)
     * updatePdhMeta
     *
     * @see COM.ibm.eannounce.objects.EANActionItem#updatePdhMeta(COM.ibm.opicmpdh.middleware.Database, boolean)
     */
    protected boolean updatePdhMeta(Database _db, boolean _bExpire) throws MiddlewareException {
        try {
            //lets get a fresh object from the database
            DeleteActionItem del_db = new DeleteActionItem(null, _db, getProfile(), getActionItemKey());
            boolean bNewAction = false;
            //check for new
            if (del_db.getActionClass() == null) {
                bNewAction = true;
            }

            //EXPIRES
            if (_bExpire && !bNewAction) {
                //EntityType
                if (del_db.getEntityType() != null) {
                    updateActionAttribute(_db, true, "TYPE", "EntityType", del_db.getEntityType());
                }

            } else {
                //EntityType
                //if new
                if (bNewAction && getEntityType() != null && !getEntityType().equals("")) {
                    updateActionAttribute(_db, false, "TYPE", "EntityType", getEntityType());
                //if used to !exist -> now exists

                } else if (!bNewAction && del_db.getEntityType() == null && this.getEntityType() != null && !this.getEntityType().equals("")) {
                    updateActionAttribute(_db, false, "TYPE", "EntityType", getEntityType());
                //if changed -- only need to update (change is on l.v.)

                } else if (!bNewAction && del_db.getEntityType() != null && this.getEntityType() != null && !del_db.getEntityType().equals(this.getEntityType())) {
                    updateActionAttribute(_db, false, "TYPE", "EntityType", getEntityType());
                //expire

                } else if (!bNewAction && del_db.getEntityType() != null && this.getEntityType() == null) {
                    updateActionAttribute(_db, true, "TYPE", "EntityType", del_db.getEntityType());
                }
            }

        } catch (SQLException sqlExc) {
            _db.debug(D.EBUG_ERR, "DeleteActionItem 361 " + sqlExc.toString());
            return false;
        } finally {
            _db.freeStatement();
            _db.isPending();
        }
        return true;
    }

    /**
     * to simplify things a bit
     */
    private void updateActionAttribute(Database _db, boolean _bExpire, String _strLinkType2, String _strLinkCode, String _strLinkValue) throws MiddlewareException {
        String strValFrom = _db.getDates().getNow();
        String strValTo = (_bExpire ? strValFrom : _db.getDates().getForever());
        new MetaLinkAttrRow(getProfile(), "Action/Attribute", getActionItemKey(), _strLinkType2, _strLinkCode, _strLinkValue, strValFrom, strValTo, strValFrom, strValTo, 2).updatePdh(_db);
    }

	/*private boolean canDelete(Database _db, Profile _prof, EntityItem _ei) throws MiddlewareException, SQLException {
        // Initialize some SP specific objects needed in this method
        ReturnDataResultSet rdrs = null;
        ReturnDataResultSet rdrs2 = null;
        ResultSet rs = null;
        ReturnStatus returnStatus = new ReturnStatus(-1);
        //EntityItemException eie = new EntityItemException();

        // Placeholders for dates
        //String strNow = null;
        //String strForever = null;

        // Pull some profile info...
        //int iOPWGID = _prof.getOPWGID();
        //int iTranID = _prof.getTranID();
        //int iSessionID = _prof.getSessionID();
        String strEnterprise = _prof.getEnterprise();
        _prof.getRoleCode();
        //int iNLSID = _prof.getReadLanguage().getNLSID();

        try {
			_db.debug(D.EBUG_SPEW, "gbl8106:parms:" + strEnterprise + ":" +  _ei.getEntityType() + ":" + _prof.getValOn() + ":" + _prof.getEffOn());
            rs = _db.callGBL8106(returnStatus, strEnterprise, _ei.getEntityType(), _prof.getValOn(), _prof.getEffOn());
            rdrs = new ReturnDataResultSet(rs);
        } finally {
        	if (rs != null){
        		rs.close();
            	rs = null;
        	}
            _db.commit();
            _db.freeStatement();
            _db.isPending();
        }

		if (rdrs.size() <= 0) {
			return true;
		} else {
			_db.debug(D.EBUG_SPEW, "gbl8106:rdrs size: " + rdrs.size());
		}

        for (int ii = 0; ii < rdrs.size(); ii++) {
            String strType = rdrs.getColumn(ii, 0).trim();
            String strCheckEntityType = rdrs.getColumn(ii, 1).trim();
            String strAttributeCode = rdrs.getColumn(ii, 2).trim();
            String strAttributeValue = rdrs.getColumn(ii, 3).trim();

            _db.debug(D.EBUG_SPEW, "gbl8106 answer is:" + strType + ":" + strCheckEntityType + ":" + strAttributeCode + ":" + strAttributeValue );
            try {
				if (strCheckEntityType.equals(_ei.getEntityType())) {
					// itself
					_db.debug(D.EBUG_SPEW, "gbl8107:parms:" + strEnterprise + ":" + _ei.getEntityType() + ":" + _ei.getEntityID() + ":" + strCheckEntityType + ":" + strAttributeCode + ":" + strAttributeValue + ":" + 3 + ":" + _prof.getValOn() + ":" + _prof.getEffOn());
					rs = _db.callGBL8107(returnStatus, strEnterprise, _ei.getEntityType(), _ei.getEntityID(), strCheckEntityType, strAttributeCode, strAttributeValue, 3, _prof.getValOn(), _prof.getEffOn());
					rdrs2 = new ReturnDataResultSet(rs);
				} else {
					if (strType.charAt(0) == 'E') {
						// children
						_db.debug(D.EBUG_SPEW, "gbl8107:parms:" + strEnterprise + ":" + _ei.getEntityType() + ":" + _ei.getEntityID() + ":" + strCheckEntityType + ":" + strAttributeCode + ":" + strAttributeValue + ":" + 1 + ":" + _prof.getValOn() + ":" + _prof.getEffOn());
						rs = _db.callGBL8107(returnStatus, strEnterprise, _ei.getEntityType(), _ei.getEntityID(), strCheckEntityType, strAttributeCode, strAttributeValue, 1, _prof.getValOn(), _prof.getEffOn());
						rdrs2 = new ReturnDataResultSet(rs);
					} else if (strType.charAt(0) == 'R') {
						// relator
						_db.debug(D.EBUG_SPEW, "gbl8107:parms:" + strEnterprise + ":" + _ei.getEntityType() + ":" + _ei.getEntityID() + ":" + strCheckEntityType + ":" + strAttributeCode + ":" + strAttributeValue + ":" + 2 + ":" + _prof.getValOn() + ":" + _prof.getEffOn());
						rs = _db.callGBL8107(returnStatus, strEnterprise, _ei.getEntityType(), _ei.getEntityID(), strCheckEntityType, strAttributeCode, strAttributeValue, 2, _prof.getValOn(), _prof.getEffOn());
						rdrs2 = new ReturnDataResultSet(rs);
					}
				}
			} finally {
				if (rs != null){
					rs.close();
					rs = null;
				}
				_db.commit();
				_db.freeStatement();
				_db.isPending();
        	}

			if (rdrs2.size() > 0) {
				return false;
			}
        }
        return true;

	}*/

	/** RQ1103063214
	* Check to see if there are any NO Delete Flag conditions for this entity
    * validate if delete entity has dependencies,
    * Deletion of the EntityType = MACHTYPE is not allowed if:
	* Search for MACHTYPE.MACHTYPEATR in MODEL.MACHTYPEATR and if found, do not allow the delete.
	*/
	private boolean canDeleteFlag(Database _db, EntityGroup eg, Profile _prof, EntityItem _ei, EntityItemException eie)
		throws MiddlewareException, SQLException
	{
		boolean isok = true;
		// DependentAttributeValue
		if (eg.hasDependentAttributeValue()) {
			java.util.Vector vctDav = eg.getDependentAttributeValueVector();
		  	_db.debug(D.EBUG_SPEW,"DeleteActionItem.canDeleteFlag: depattr vector size:" + vctDav.size());
			for (int i = 0; i < vctDav.size(); i++) {
				DependentAttributeValue dav = (DependentAttributeValue) vctDav.elementAt(i);
				dav.populateAttributeVals(_ei);

			  	_db.debug(D.EBUG_SPEW,"DeleteActionItem.canDeleteFlag: " + dav.getKey());
				if(!dav.validateDelete(_db)) {
					eie.add(new EntityItem(_ei), dav.getExceptionMessage());
					isok=false;
				}
			}
		}

        return isok;
	}
}
