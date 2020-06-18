// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2002, 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
// $Log: ActionList.java,v $
// Revision 1.121  2013/11/01 15:59:35  wendy
// PR20627 - set EOD in parent profile for loading cache
//
// Revision 1.120  2013/09/11 20:23:07  wendy
// cachekey must indicate readonly role function
//
// Revision 1.119  2012/11/08 21:28:59  wendy
// RCQ00210066-WI Ref CQ00014746 use EOD to build cache, rollback if error instead of commit
//
// Revision 1.118  2009/05/19 13:45:30  wendy
// check for rs!=null before close()
//
// Revision 1.117  2008/07/31 18:59:06  wendy
// CQ00006067-WI : LA CTO - Added support for QueryAction
//
// Revision 1.116  2007/08/28 18:48:13  wendy
// Print stacktrace on exceptions, check for ba.length to prevent an exc
//
// Revision 1.115  2006/10/05 19:27:56  roger
// Fix
//
// Revision 1.114  2006/10/05 16:00:29  roger
// Fix
//
// Revision 1.113  2006/10/05 15:44:43  roger
// Log putBlob calls for caching into TCOUNT table
//
// Revision 1.112  2006/03/07 17:39:08  joan
// remove unneeded files
//
// Revision 1.111  2005/11/04 14:52:09  tony
// VEEdit_Iteration2
// updated VEEdit logic to meet new requirements.
//
// Revision 1.110  2005/03/11 22:42:52  dave
// removing some auto genned stuff
//
// Revision 1.109  2005/03/03 21:25:16  dave
// NEW_LINE on EAN Foundation
//
// Revision 1.108  2005/03/01 23:39:20  joan
// work on Meta Maintenance
//
// Revision 1.107  2005/02/09 23:46:46  dave
// more Jtest Cleanup
//
// Revision 1.106  2005/01/18 21:33:09  dave
// removing the debug parms from code (sp internalized them)
//
// Revision 1.105  2005/01/10 21:47:48  joan
// work on multiple edit
//
// Revision 1.104  2005/01/05 19:24:07  joan
// add new method
//
// Revision 1.103  2004/08/02 19:34:01  joan
// add CGActionItem
//
// Revision 1.102  2004/06/21 15:02:35  joan
// add ABRStatus action
//
// Revision 1.101  2004/06/15 20:36:41  joan
// add CopyActionItem
//
// Revision 1.100  2004/06/08 17:51:30  joan
// throw exception
//
// Revision 1.99  2004/06/08 17:28:33  joan
// add method
//
// Revision 1.98  2004/04/09 19:37:17  joan
// add duplicate method
//
// Revision 1.97  2004/01/08 21:33:34  dave
// syntax
//
// Revision 1.96  2004/01/08 21:22:26  dave
// cleaning up actionList.. does it need a parent?
//
// Revision 1.95  2003/12/17 17:58:27  joan
// work on SG PDG
//
// Revision 1.94  2003/12/03 18:09:06  joan
// add HWFUPPOFPDG
//
// Revision 1.93  2003/10/29 23:56:20  dave
// fixing null pointers
//
// Revision 1.92  2003/10/29 22:16:19  dave
// removed getProfile from many many new.. statements
// when the parent is not null
//
// Revision 1.91  2003/09/04 19:58:33  joan
// fix null pointer
//
// Revision 1.90  2003/08/28 16:28:02  joan
// adjust link method to have link option
//
// Revision 1.89  2003/08/27 22:09:48  dave
// Testing cache action items
//
// Revision 1.88  2003/07/01 00:19:53  dave
// final syntax
//
// Revision 1.87  2003/07/01 00:05:08  dave
// more syntax
//
// Revision 1.86  2003/07/01 00:04:07  dave
// more syntax
//
// Revision 1.85  2003/06/30 23:52:10  dave
// more import statements
//
// Revision 1.84  2003/06/30 23:50:27  dave
// cache attempt
//
// Revision 1.83  2003/06/30 23:41:37  dave
// more syntax
//
// Revision 1.82  2003/06/30 23:34:27  dave
// minor syntax
//
// Revision 1.81  2003/06/30 23:25:06  dave
// first attempt at caching action lists
//
// Revision 1.80  2003/06/25 18:43:58  joan
// move changes from v111
//
// Revision 1.79.2.4  2003/06/25 00:01:43  joan
// fix compile
//
// Revision 1.79.2.3  2003/06/24 23:37:23  joan
// fix WhereUsedActionItem constructor
//
// Revision 1.79.2.2  2003/06/24 20:52:34  joan
// add PCDCSOLPDG1
//
// Revision 1.79.2.1  2003/06/13 22:32:44  joan
// add SPDG
//
// Revision 1.79  2003/05/11 02:04:39  dave
// making EANlists bigger
//
// Revision 1.78  2003/04/29 20:13:43  joan
// add HWUpgradePIPDG
//
// Revision 1.77  2003/04/29 17:05:47  dave
// clean up and removal of uneeded function
//
// Revision 1.76  2003/04/18 20:03:16  joan
// add HWUpgrade
//
// Revision 1.75  2003/04/17 17:49:08  joan
// add Hardware Upgrade PDG
//
// Revision 1.74  2003/04/14 18:52:31  joan
// add HWUpgradeModelPDG
//
// Revision 1.73  2003/04/01 18:43:29  joan
// add HWFeaturePDG
//
// Revision 1.72  2003/03/28 21:35:00  joan
// add HW PDG action items
//
// Revision 1.71  2003/03/27 23:07:19  dave
// adding some timely commits to free up result sets
//
// Revision 1.70  2003/03/26 20:37:28  joan
// add SW support and maintenance PDGs
//
// Revision 1.69  2003/03/20 21:46:01  joan
// fix compile
//
// Revision 1.68  2003/03/20 21:33:50  joan
// add SW subscription action items
//
// Revision 1.67  2003/03/10 17:30:57  joan
// fix compile
//
// Revision 1.66  2003/03/10 16:59:09  joan
// work on  PDG
//
// Revision 1.65  2003/03/04 01:00:15  joan
// add pdg action item
//
// Revision 1.64  2003/03/03 18:36:35  joan
// add PDG action item
//
// Revision 1.63  2003/01/21 00:20:34  joan
// adjust link method to test VE lock
//
// Revision 1.62  2003/01/14 22:05:07  joan
// adjust removeLink method
//
// Revision 1.61  2003/01/08 21:44:03  joan
// add getWhereUsedList
//
// Revision 1.60  2002/10/30 22:39:12  dave
// more cleanup
//
// Revision 1.59  2002/10/30 22:36:18  dave
// clean up
//
// Revision 1.58  2002/10/30 22:02:31  dave
// added exception throwing to commit
//
// Revision 1.57  2002/10/29 00:02:54  dave
// backing out row commit for 1.1
//
// Revision 1.56  2002/10/28 23:49:13  dave
// attempting the first commit with a row index
//
// Revision 1.55  2002/10/18 20:18:51  joan
// add isMatrixEditable method
//
// Revision 1.54  2002/10/14 21:58:31  joan
// fix error
//
// Revision 1.53  2002/10/14 21:43:06  joan
// add HWUPGRADEActionItem
//
// Revision 1.52  2002/10/09 21:32:55  dave
// added isDynaTable to EANTableWrapper interface
//
// Revision 1.51  2002/10/02 15:47:11  joan
// fix error
//
// Revision 1.50  2002/10/02 15:30:08  joan
// add HWPDGActionItem
//
// Revision 1.49  2002/09/27 17:10:58  dave
// made addRow a boolean
//
// Revision 1.48  2002/07/18 21:51:49  joan
// add SBRActionItem
//
// Revision 1.47  2002/07/16 15:38:19  joan
// working on method to return the array of actionitems
//
// Revision 1.46  2002/07/08 17:53:41  joan
// fix link method
//
// Revision 1.45  2002/07/08 16:05:28  joan
// fix link method
//
// Revision 1.44  2002/06/25 20:36:07  joan
// add create method for whereused
//
// Revision 1.43  2002/06/25 17:49:36  joan
// add link and removeLink methods
//
// Revision 1.42  2002/06/21 16:58:23  joan
// add WhereUsedActionItem
//
// Revision 1.41  2002/06/19 15:52:18  joan
// work on add column in matrix
//
// Revision 1.40  2002/06/17 23:53:46  joan
// add addColumn method
//
// Revision 1.39  2002/06/05 22:18:19  joan
// work on put and rollback
//
// Revision 1.38  2002/06/05 17:37:45  joan
// add MatrixActionItem
//
// Revision 1.37  2002/06/05 16:28:48  joan
// add getMatrixValue method
//
// Revision 1.36  2002/05/30 22:49:52  joan
// throw MiddlewareBusinessRuleException when committing
//
// Revision 1.35  2002/05/08 19:56:40  dave
// attempting to throw the BusinessRuleException on Commit
//
// Revision 1.34  2002/04/24 18:04:37  joan
// add removeRow method
//
// Revision 1.33  2002/04/22 19:43:53  dave
// adding Workflow action item stub
//
// Revision 1.32  2002/04/19 20:53:29  dave
// attempting to encorporate ExtractActionItem and ReportActionItem
//
// Revision 1.31  2002/04/02 21:25:49  dave
// added hasChanges()
//
// Revision 1.30  2002/03/27 22:34:20  dave
// Row Selectable Table row Add logic. First attempt
//
// Revision 1.29  2002/03/22 21:26:54  dave
// more syntax cleanup
//
// Revision 1.28  2002/03/07 20:12:04  dave
// more null pointer for action item parent in actionlist
//
// Revision 1.27  2002/02/26 21:43:59  dave
// ensuring I am setting the rs = null after a close
//
// Revision 1.26  2002/02/20 00:11:06  dave
// more fixes
//
// Revision 1.25  2002/02/14 01:44:25  dave
// more syntax fixes
//
// Revision 1.24  2002/02/14 01:21:17  dave
// created a binder for action groups so relator/entity can be shown
//
// Revision 1.23  2002/02/13 23:29:23  dave
// more table model for ActionGroups from a list
//
// Revision 1.22  2002/02/12 21:01:58  dave
// added toString methods for diplay help
//
// Revision 1.21  2002/02/12 18:51:44  dave
// more changes to dump
//
// Revision 1.20  2002/02/12 18:10:22  dave
// more changes
//
// Revision 1.19  2002/02/11 19:19:40  dave
// more dump rearranging
//
// Revision 1.18  2002/02/09 22:09:23  dave
// Abstracted the Action Item in the Action List
//
// Revision 1.17  2002/02/09 21:50:41  dave
// more syntax
//
// Revision 1.16  2002/02/09 21:39:34  dave
// syntax fixes
//
// Revision 1.15  2002/02/09 21:14:37  dave
// more arranging  of Abstract Classing
//
// Revision 1.14  2002/02/06 00:42:39  dave
// more fixes to base code
//
// Revision 1.13  2002/02/02 21:39:19  dave
// more  fixes to tighen up import
//
// Revision 1.12  2002/02/02 21:11:07  dave
// fixing more import statements
//
// Revision 1.11  2002/02/02 20:56:53  dave
// tightening up code
//
// Revision 1.10  2002/02/01 00:42:32  dave
// more foundation fixes for passing _prof
//
// Revision 1.9  2002/01/31 22:57:18  dave
// more Foundation Cleanup
//
// Revision 1.8  2002/01/21 20:59:50  dave
// bit the bullet and use profile to imply NLSItem
//
// Revision 1.7  2002/01/18 22:17:48  dave
// fixing a deadly null pointer
//
// Revision 1.6  2002/01/18 17:48:24  dave
// syntax
//
// Revision 1.5  2002/01/18 17:31:12  dave
// multiple lookup capability on ActionItem group from
// within an Action List
//
// Revision 1.4  2002/01/17 18:33:53  dave
// misc adjustments to help cloning and copying of structures
// in e-announce
//
// Revision 1.3  2002/01/15 00:29:22  dave
// minor syntax changes
//
// Revision 1.2  2002/01/15 00:15:51  dave
// missing bracket in for loop
//
// Revision 1.1  2002/01/15 00:06:43  dave
// initial imports for ActionList
//
//

package COM.ibm.eannounce.objects;

import java.sql.ResultSet;
import java.lang.reflect.Constructor;
import COM.ibm.opicmpdh.objects.Blob;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.DatePackage;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

// Exceptions
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
import java.sql.SQLException;
import java.rmi.RemoteException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.LockException;
//
// This guy is defined via a Role/PreviousAction/Target EntityType
// This makes up the key
//
// Note: This guys uses an implicator which allows ActionGroup (which are key by an Action Group Key)
// to be referenced by an EntityType.  Since There is one Action Group per EntityType in an Action List
//  
/**  
 * ActionList
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ActionList extends EANMetaEntity implements EANTableWrapper {

    static final long serialVersionUID = 20011106L;
    /**
     * FIELD
     */
    public static final int KEY_BY_ENTITYTYPE = 0;

    /**
     * FIELD
     */
    protected EANList m_elImp = new EANList(EANMetaEntity.LIST_SIZE);

    /**
     * FIELD
     */
    protected EANActionItem m_aiParent = null;
    /**
     * FIELD
     */
    protected String m_strRoleCodeParent = null;

    /**
     * Main method which performs a simple test of this class
     *
     * @param arg
     */
    public static void main(String arg[]) {
    }

    /**
     * (non-Javadoc)
     * getVersion
     *
     * @see COM.ibm.eannounce.objects.EANFoundation#getVersion()
     */
    public String getVersion() {
        return "$Id: ActionList.java,v 1.121 2013/11/01 15:59:35 wendy Exp $";
    }

    /**
     * ActionList
     *
     * @param _mf
     * @param _db
     * @param _prof
     * @param _ai
     * @param _bscratch
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public ActionList(EANMetaFoundation _mf, Database _db, Profile _prof, EANActionItem _ai, boolean _bscratch) throws MiddlewareException, MiddlewareRequestException {
        super(_mf, _prof, _prof.getRoleCode() + _ai.getActionItemKey() + _prof.getReadLanguage().getNLSID());

        try {
            ActionList altmp = getCachedActionList(_db);
            if (altmp != null) {
                ownIt(altmp);
            } else {
             	DatePackage dp = _db.getDates();
                //RCQ00210066-WI Ref CQ00014746 - BH W1 R1 - EACM support history of Metadata in PDH
                //Enabling Technology to change how CACHE is built
                Profile eodProfile=getProfile();
				//try {
					//eodProfile = getProfile().getNewInstance(_db);
				    eodProfile.setValOnEffOn(dp.getEndOfDay(), dp.getEndOfDay()); // change it in this.profile PR20627
				//} catch (SQLException e) {
					//_db.debug(D.EBUG_DETAIL, "ActionList: "+getKey()+" ERROR getting EOD profile "+e);
					//e.printStackTrace();
				//}
                
                build(_db, eodProfile, _ai);
                       
                putCachedActionList(_db);
            }
        } finally {
        }

    }

    /**
     * This represents an Action List.  It can only be constructed via a database connection, a Profile, and an ActionItemKey
     * This is the highest point in the higherarchy...
     * The way to Change NLSIDs here is to simply use the getProfile method.. then manipulate the ReadLanguage of that Profile.
     *
     * @param _mf
     * @param _db
     * @param _prof
     * @param _ai
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     */
    public ActionList(EANMetaFoundation _mf, Database _db, Profile _prof, EANActionItem _ai) throws MiddlewareException, MiddlewareRequestException {

        super(_mf, _prof, _prof.getRoleCode() + _ai.getActionItemKey() + _prof.getReadLanguage().getNLSID());
     	DatePackage dp = _db.getDates();
        //RCQ00210066-WI Ref CQ00014746 - BH W1 R1 - EACM support history of Metadata in PDH
        //Enabling Technology to change how CACHE is built
        Profile eodProfile=getProfile();
		//try {
		//	eodProfile = _prof.getNewInstance(_db);
		    eodProfile.setValOnEffOn(dp.getEndOfDay(), dp.getEndOfDay()); // use this.profile PR20627
		//} catch (SQLException e) {
			//_db.debug(D.EBUG_DETAIL, "ActionList: "+getKey()+" ERROR getting EOD profile "+e);
			//e.printStackTrace();
		//}
        
        build(_db, eodProfile, _ai);
    }

    private void build(Database _db, Profile _prof, EANActionItem _ai) throws MiddlewareException, MiddlewareRequestException {

        // Preserve the rolecode out of the Profile that was used to create this object.
        setParentRoleCode(_prof.getRoleCode());
        setParentActionItem(_ai);

        try {

            ReturnStatus returnStatus = new ReturnStatus(-1);
            ResultSet rs = null;
            ReturnDataResultSet rdrs = null;
            boolean success = false;
            try {
                rs = _db.callGBL7000(returnStatus, _prof.getEnterprise(), getParentRoleCode(), getParentActionItem().getActionItemKey(), _prof.getValOn(), _prof.getEffOn());
                rdrs = new ReturnDataResultSet(rs);
                success = true;
            } finally {
            	if (rs!=null){
                	try{
            			rs.close();
            		} catch (SQLException x) {
            			_db.debug(D.EBUG_DETAIL, "build: "+getKey()+" ERROR failure closing ResultSet "+x);
            		} 
            		rs = null;
            	}

            	if(success){
            		_db.commit();
            	}else{
            		_db.rollback();
            	}
                _db.freeStatement();
                _db.isPending();
            }

            // For Each actionGroup/EntityType Combination in the list.. we need to generate the ActionGroup.. and
            // wrap it in an Implicator based upon entitytype so Action Groups can be addressable via the EntityType
            for (int ii = 0; ii < rdrs.size(); ii++) {
                ActionGroup ag = null;
                String strEntityType = rdrs.getColumn(ii, 0);
                String strGroupKey = rdrs.getColumn(ii, 1);
                _db.debug(D.EBUG_SPEW, "gbl7000 answer is:" + strEntityType + ":" + strGroupKey + ":");
                ag = new ActionGroup(this, _db, null, strGroupKey, strEntityType);
                // Store it so it is accessable by entitytype...
                putImpliedData(new Implicator(ag, _prof, strEntityType));
                // Store it Normally.. accessable by group key
                putData(ag);
            }

        } catch (Exception x) {
        	_db.debug(D.EBUG_ERR, "ActionGroup exception: " + x);
        } finally {
            _db.freeStatement();
            _db.isPending();
        }
    }

    //
    // ACCESSORS
    //

    /**
     * getParentActionItem
     *
     * @return
     *  @author David Bigelow
     */
    public EANActionItem getParentActionItem() {
        return m_aiParent;
    }

    // Implied data manipulation stuff
    /**
     * putImpliedData
     *
     * @param _eanm
     *  @author David Bigelow
     */
    protected void putImpliedData(EANMetaFoundation _eanm) {
        m_elImp.put(_eanm);
    }

    /**
     * getImpliedDataCount
     *
     * @return
     *  @author David Bigelow
     */
    protected int getImpliedDataCount() {
        return m_elImp.size();
    }

    /**
     * removeImpliedData
     *
     * @param _eanm
     *  @author David Bigelow
     */
    protected void removeImpliedData(EANMetaFoundation _eanm) {
        m_elImp.remove(_eanm);
    }

    /**
     * getImpliedData
     *
     * @param _s
     * @return
     *  @author David Bigelow
     */
    protected EANMetaFoundation getImpliedData(String _s) {
        return (EANMetaFoundation) m_elImp.get(_s);
    }

    /**
     * getImpliedData
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    protected EANMetaFoundation getImpliedData(int _i) {
        return (EANMetaFoundation) m_elImp.getAt(_i);
    }

    /**
     * getImpliedData
     *
     * @return
     *  @author David Bigelow
     */
    protected EANList getImpliedData() {
        return m_elImp;
    }

    /*
    * Get the action group by Key Index and by Key Type
    */
    /**
     * getActionGroup
     *
     * @param _i
     * @param _y
     * @return
     *  @author David Bigelow
     */
    public ActionGroup getActionGroup(int _i, int _y) {

        Implicator imp = null;

        switch (_y) {

        case KEY_BY_ENTITYTYPE :

            imp = (Implicator) getImpliedData(_i);
            if (imp != null) {
                return (ActionGroup) imp.getParent();
            }

            return null;
        default :
            break;
        }

        return null;
    }

    /*
    * Get the action group by Key String and by Key Type
    */
    /**
     * getActionGroup
     *
     * @param _s
     * @param _y
     * @return
     *  @author David Bigelow
     */
    public ActionGroup getActionGroup(String _s, int _y) {

        Implicator imp = null;

        switch (_y) {

        case KEY_BY_ENTITYTYPE :

            imp = (Implicator) getImpliedData(_s);
            if (imp != null) {
                return (ActionGroup) imp.getParent();
            }
            return null;
        default :
            break;

        }

        return null;

    }

    /**
     * getActionGroup
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public ActionGroup getActionGroup(int _i) {
        return (ActionGroup) getData(_i);
    }

    /**
     * getActionGroup
     *
     * @param _s
     * @return
     *  @author David Bigelow
     */
    public ActionGroup getActionGroup(String _s) {
        return (ActionGroup) getData(_s);
    }

    /**
     * getActionGroup
     *
     * @return
     *  @author David Bigelow
     */
    public EANList getActionGroup() {
        return getData();
    }

    /**
     * setActionGroup
     *
     * @param _el
     *  @author David Bigelow
     */
    protected void setActionGroup(EANList _el) {
        setData(_el);
    }

    /**
     * getActionGroupCount
     *
     * @return
     *  @author David Bigelow
     */
    public int getActionGroupCount() {
        return getDataCount();
    }

    /**
     * getParentRoleCode
     *
     * @return
     *  @author David Bigelow
     */
    public String getParentRoleCode() {
        return m_strRoleCodeParent;
    }

    //
    // MUTATORS
    //

    /*
    * Set the Parent Action Item of this object.
    */
    /**
     * setParentActionItem
     *
     * @param _ai
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public void setParentActionItem(EANActionItem _ai) throws MiddlewareRequestException {
        if (_ai == null) {
            m_aiParent = null;
        } else if (_ai instanceof NavActionItem) {
            m_aiParent = new NavActionItem(this, (NavActionItem) _ai);
        } else if (_ai instanceof CreateActionItem) {
            m_aiParent = new CreateActionItem(this, (CreateActionItem) _ai);
        } else if (_ai instanceof SearchActionItem) {
            m_aiParent = new SearchActionItem(this, (SearchActionItem) _ai);
        } else if (_ai instanceof EditActionItem) {
            m_aiParent = new EditActionItem(this, (EditActionItem) _ai);
        } else if (_ai instanceof CopyActionItem) {
            m_aiParent = new CopyActionItem(this, (CopyActionItem) _ai);
        } else if (_ai instanceof ABRStatusActionItem) {
            m_aiParent = new ABRStatusActionItem(this, (ABRStatusActionItem) _ai);
        } else if (_ai instanceof ExtractActionItem) {
            m_aiParent = new ExtractActionItem(this, (ExtractActionItem) _ai);
        } else if (_ai instanceof ReportActionItem) {
            m_aiParent = new ReportActionItem(this, (ReportActionItem) _ai);
        } else if (_ai instanceof WorkflowActionItem) {
            m_aiParent = new WorkflowActionItem(this, (WorkflowActionItem) _ai);
        } else if (_ai instanceof MatrixActionItem) {
            m_aiParent = new MatrixActionItem(this, (MatrixActionItem) _ai);
        } else if (_ai instanceof WhereUsedActionItem) {
            m_aiParent = new WhereUsedActionItem(this, (WhereUsedActionItem) _ai);
//        } else if (_ai instanceof HWPDGActionItem) {
//            m_aiParent = new HWPDGActionItem(this, (HWPDGActionItem) _ai);
//        } else if (_ai instanceof HWUPGRADEActionItem) {
//            m_aiParent = new HWUPGRADEActionItem(this, (HWUPGRADEActionItem) _ai);
        } else if (_ai instanceof QueryActionItem) {
            m_aiParent = new QueryActionItem(this,(QueryActionItem) _ai);
        } else if (_ai instanceof CGActionItem) {
            m_aiParent = new CGActionItem(this, (CGActionItem) _ai);
        } else if (_ai instanceof MetaMaintActionItem) {
            m_aiParent = new MetaMaintActionItem(this, (MetaMaintActionItem) _ai);
        } else if (_ai instanceof PDGActionItem) {
            String strKey = _ai.getKey();
            if (strKey.equals("SWSUBSPRODPDG")) {
                m_aiParent = new SWSubsProdPDG(this, (SWSubsProdPDG) _ai);
            } else if (strKey.equals("SWSUBSPRODINITIALPDG")) {
                m_aiParent = new SWSubsProdInitialPDG(this, (SWSubsProdInitialPDG) _ai);
            } else if (strKey.equals("SWSUBSPRODSUPPPDG")) {
                m_aiParent = new SWSubsProdSuppPDG(this, (SWSubsProdSuppPDG) _ai);
            } else if (strKey.equals("SWSUBSFEATPDG")) {
                m_aiParent = new SWSubsFeatPDG(this, (SWSubsFeatPDG) _ai);
            } else if (strKey.equals("SWSUBSFEATINITIALPDG")) {
                m_aiParent = new SWSubsFeatInitialPDG(this, (SWSubsFeatInitialPDG) _ai);
            } else if (strKey.equals("SWSUBSFEATSUPPPDG")) {
                m_aiParent = new SWSubsFeatSuppPDG(this, (SWSubsFeatSuppPDG) _ai);
            } else if (strKey.equals("SWMAINTPRODPDG")) {
                m_aiParent = new SWMaintProdPDG(this, (SWMaintProdPDG) _ai);
            } else if (strKey.equals("SWMAINTPRODINITIALPDG")) {
                m_aiParent = new SWMaintProdInitialPDG(this, (SWMaintProdInitialPDG) _ai);
            } else if (strKey.equals("SWMAINTPRODSUPPPDG")) {
                m_aiParent = new SWMaintProdSuppPDG(this, (SWMaintProdSuppPDG) _ai);
            } else if (strKey.equals("SWMAINTFEATPDG")) {
                m_aiParent = new SWMaintFeatPDG(this, (SWMaintFeatPDG) _ai);
            } else if (strKey.equals("SWMAINTFEATINITIALPDG")) {
                m_aiParent = new SWMaintFeatInitialPDG(this, (SWMaintFeatInitialPDG) _ai);
            } else if (strKey.equals("SWMAINTFEATSUPPPDG")) {
                m_aiParent = new SWMaintFeatSuppPDG(this, (SWMaintFeatSuppPDG) _ai);
            } else if (strKey.equals("SWSUPPPRODPDG")) {
                m_aiParent = new SWSuppProdPDG(this, (SWSuppProdPDG) _ai);
            } else if (strKey.equals("SWSUPPPRODINITIALPDG")) {
                m_aiParent = new SWSuppProdInitialPDG(this, (SWSuppProdInitialPDG) _ai);
            } else if (strKey.equals("SWSUPPPRODSUPPPDG")) {
                m_aiParent = new SWSuppProdSuppPDG(this, (SWSuppProdSuppPDG) _ai);
            } else if (strKey.equals("SWSUPPFEATPDG")) {
                m_aiParent = new SWSuppFeatPDG(this, (SWSuppFeatPDG) _ai);
            } else if (strKey.equals("SWSUPPFEATINITIALPDG")) {
                m_aiParent = new SWSuppFeatInitialPDG(this, (SWSuppFeatInitialPDG) _ai);
            } else if (strKey.equals("SWSUPPFEATSUPPPDG")) {
                m_aiParent = new SWSuppFeatSuppPDG(this, (SWSuppFeatSuppPDG) _ai);
            } else if (strKey.equals("HWPRODBASEPDG")) {
                m_aiParent = new HWProdBasePDG(this, (HWProdBasePDG) _ai);
            } else if (strKey.equals("HWPRODINITIALPDG")) {
                m_aiParent = new HWProdInitialPDG(this, (HWProdInitialPDG) _ai);
            } else if (strKey.equals("HWFEATUREPDG")) {
                m_aiParent = new HWFeaturePDG(this, (HWFeaturePDG) _ai);
            } else if (strKey.equals("HWUPMODELPDG")) {
                m_aiParent = new HWUpgradeModelPDG(this, (HWUpgradeModelPDG) _ai);
            } else if (strKey.equals("HWFEATCONVPDG")) {
                m_aiParent = new HWFeatureConvertPDG(this, (HWFeatureConvertPDG) _ai);
            } else if (strKey.equals("HWUPPTOPIPDG")) {
                m_aiParent = new HWUpgradePToPIPDG(this, (HWUpgradePToPIPDG) _ai);
            } else if (strKey.equals("HWUPPIPDG")) {
                m_aiParent = new HWUpgradePIPDG(this, (HWUpgradePIPDG) _ai);
            } else if (strKey.equals("PCDCSOLPDG1")) {
                m_aiParent = new CSOLRegionPCDPDG(this, (CSOLRegionPCDPDG) _ai);
            } else {
                try {
                    Class c = Class.forName("COM.ibm.eannounce.objects." + strKey);
                    Class[] ac = { Class.forName("COM.ibm.eannounce.objects.EANMetaFoundation"), Class.forName("COM.ibm.eannounce.objects.PDGActionItem")};
                    Constructor con = c.getConstructor(ac);
                    Object[] ao = { this, (PDGActionItem) _ai };
                    m_aiParent = (PDGActionItem) con.newInstance(ao);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } else if (_ai instanceof SPDGActionItem) {
            m_aiParent = new SPDGActionItem(this, (SPDGActionItem) _ai);
        }
    }

    /**
     * setParentRoleCode
     *
     * @param _str
     *  @author David Bigelow
     */
    protected void setParentRoleCode(String _str) {
        m_strRoleCodeParent = _str;
    }

    private String getCacheKey(){
    	// if profile has ROLE_FUNCTION_READONLY - the actiongroups are limited and cache needs to recognize this
    	String strKey = "AL" + getKey();
    	if(this.getProfile().isReadOnly()){
    		strKey+="RO";
    	}
    	return strKey;
    }
    private ActionList getCachedActionList(Database _db) {

        String strKey = getCacheKey();//"AL" + getKey();
        byte[] ba = null;
        ByteArrayInputStream baisObject = null;
        BufferedInputStream bis = null;
        ObjectInputStream oisObject = null;
        ActionList al = null;

        _db.debug(D.EBUG_SPEW, "getCachedActionList starting the cache search "+strKey);

        try {
            // go get the blob.. It is NLS Independent right now
            Blob b = _db.getBlobNow(getProfile(), strKey, 0, strKey, 1);
            _db.commit();

            if (b!=null){
            	ba = b.getBAAttributeValue();
			}
            if (ba == null|| ba.length==0) {
                _db.debug(D.EBUG_SPEW, "getCachedActionList complete "+strKey+" - nothing found");
                return null;
            }

            baisObject = new ByteArrayInputStream(ba);
            bis = new BufferedInputStream(baisObject, 1000000);
            oisObject = new ObjectInputStream(bis);
            al = (ActionList) oisObject.readObject();

            _db.debug(D.EBUG_SPEW, "getCachedActionList complete "+strKey+" - found one");
            return al;
        } catch (Exception x) {
            _db.debug(D.EBUG_ERR, "getCachedActionList: "+strKey+" ERROR "+x.getMessage());
            x.printStackTrace();
        } finally {
        	if(oisObject!=null){
        		try{
        			oisObject.close();
        		} catch (java.io.IOException x) {
        			_db.debug(D.EBUG_DETAIL, "getCachedActionList: "+strKey+" ERROR failure closing ObjectInputStream "+x);
        		} 
        		oisObject=null;
        	}
           	if(bis!=null){
        		try{
        			bis.close();
        		} catch (java.io.IOException x) {
        			_db.debug(D.EBUG_DETAIL, "getCachedActionList: "+strKey+" ERROR failure closing BufferedInputStream "+x);
        		} 
        		bis=null;
        	}
        	if(baisObject!=null){
        		try{
        			baisObject.close();
        		} catch (java.io.IOException x) {
        			_db.debug(D.EBUG_DETAIL, "getCachedActionList: "+strKey+" ERROR failure closing ByteArrayInputStream "+x);
        		} 
        		baisObject=null;
        	}
            ba = null;
            
            _db.freeStatement();
            _db.isPending();
        }
        
        _db.debug(D.EBUG_SPEW, "getCachedActionList complete "+strKey+" - nothing found - because of issues");
        return null;
    }

    private void putCachedActionList(Database _db) {

        byte[] ba = null;
        String strKey = getCacheKey(); //"AL" + getKey();

        ByteArrayOutputStream baosObject = null;
        ObjectOutputStream oosObject = null;

        // Hide the parent for a bit!
        EANFoundation ef = getParent();
        setParent(null);
        _db.debug(D.EBUG_SPEW, "putCachedActionList started "+strKey);

        boolean success = false;
        try {
        	DatePackage dp = _db.getDates();

        	String strNow = dp.getNow();
        	String strForever = dp.getForever();

        	baosObject = new ByteArrayOutputStream();
        	oosObject = new ObjectOutputStream(baosObject);
        	oosObject.writeObject(this);
        	oosObject.flush();
        	oosObject.reset();

        	ba = new byte[baosObject.size()];
        	ba = baosObject.toByteArray();
        	_db.callGBL9974(new ReturnStatus(-1), _db.getInstanceName(), "pcal");
        	_db.freeStatement();
        	_db.isPending();
        	_db.putBlob(getProfile(), strKey, 0, strKey, "CACHE", strNow, strForever, ba, 1);
        	_db.debug(D.EBUG_SPEW, "putCachedActionList success "+strKey);
        	success = true;

        } catch (Exception x) {
        	_db.debug(D.EBUG_ERR, "putCachedActionList error "+strKey+" "+ x.getMessage());
        	x.printStackTrace();
        } finally {
        	try{
        		if(success){
        			_db.commit();
        		}else{
        			_db.rollback();
        		}
        	}catch(Exception e){}
          	if(oosObject!=null){
        		try{
        			oosObject.close();
        		} catch (java.io.IOException x) {
        			_db.debug(D.EBUG_DETAIL, "putCachedActionList: "+strKey+" ERROR failure closing ObjectOutputStream "+x);
        		} 
        		oosObject=null;
        	}
        	if(baosObject!=null){
        		try{
        			baosObject.close();
        		} catch (java.io.IOException x) {
        			_db.debug(D.EBUG_DETAIL, "putCachedActionList: "+strKey+" ERROR failure closing ByteArrayOutputStream "+x);
        		} 
        		baosObject=null;
        	}
        	setParent(ef);

        	_db.freeStatement();
        	_db.isPending();
        }
    }

    private void ownIt(ActionList _al) {

        // First.. assign these things
        setActionGroup(_al.getActionGroup());
        this.m_elImp = _al.m_elImp;
        this.m_strRoleCodeParent = _al.m_strRoleCodeParent;
        this.setProfile(getProfile());
        // Reset Group's
        for (int ii = 0; ii < getActionGroupCount(); ii++) {
            ActionGroup ag = getActionGroup(ii);
            ag.setParent(this);
        }

    }

    /**
     * (non-Javadoc)
     * toString
     *
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "ACTIONLIST:" + getKey() + getShortDescription() + " - " + getLongDescription();
    }

    //
    //  Debug methods
    //

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {

        StringBuffer strbResult = new StringBuffer();

        if (_bBrief) {
            strbResult.append(getKey());
        } else {
            strbResult.append(NEW_LINE + "ActionList:" + getKey());
            strbResult.append(":ShortDesc:" + getShortDescription());
            strbResult.append(":LongDesc:" + getLongDescription());
            strbResult.append(":ParentRoleCode:" + getParentRoleCode());
            strbResult.append(NEW_LINE + "ParentActionItem:" + (getParentActionItem() != null ? getParentActionItem().dump(_bBrief) : ""));
            strbResult.append(NEW_LINE + "Implicators for the EntityList:");
            for (int i = 0; i < getImpliedDataCount(); i++) {
                Implicator imp = (Implicator) getImpliedData(i);
                strbResult.append(NEW_LINE + "" + i + ":" + imp.dump(_bBrief));
            }
            strbResult.append(NEW_LINE + "ActionGroups for the EntityList:");
            for (int i = 0; i < getActionGroupCount(); i++) {
                ActionGroup ag = getActionGroup(i);
                strbResult.append(NEW_LINE + "" + i + ":" + ag.dump(_bBrief));
            }
        }

        return strbResult.toString();
    }

    //
    // This is the table wrapper stuff to Render its Entity Groups
    //

    /*
    * return the column information here
    */
    /**
     * (non-Javadoc)
     * getColumnList
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#getColumnList()
     */
    public EANList getColumnList() {

        try {

            // Meta for a simple table display using description
            putMeta(new MetaLabel(this, getProfile(), ActionGroup.DESCRIPTION, "Description", String.class));

        } catch (Exception x) {
            x.printStackTrace();
        }

        // Now return it

        return getMeta();
    }

    /*
    *   Return only visible rows
    */
    /**
     * (non-Javadoc)
     * getRowList
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#getRowList()
     */
    public EANList getRowList() {
        return getData();
    }

    /*
    * Returns a basic table adaptor for client rendering of EntityLists
    */
    /**
     * getTable
     *
     * @return
     *  @author David Bigelow
     */
    public RowSelectableTable getTable() {
        return new RowSelectableTable(this, getLongDescription());
    }

    /**
     * (non-Javadoc)
     * commit
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#commit(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface)
     */
    public void commit(Database _db, RemoteDatabaseInterface _rdi) throws EANBusinessRuleException, MiddlewareBusinessRuleException, RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, SQLException {
    }

    /**
     * (non-Javadoc)
     * canEdit
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#canEdit()
     */
    public boolean canEdit() {
        return false;
    }

    /**
     * (non-Javadoc)
     * canCreate
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#canCreate()
     */
    public boolean canCreate() {
        return false;
    }

    /**
     * (non-Javadoc)
     * addRow
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#addRow()
     */
    public boolean addRow() {
        return false;
    }

    /**
     * (non-Javadoc)
     * addRow
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#addRow(String)
     */
    public boolean addRow(String _sKey) {
        return false;
    }

    /**
     * (non-Javadoc)
     * removeRow
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#removeRow(java.lang.String)
     */
    public void removeRow(String _strKey) {
    }

    /**
     * (non-Javadoc)
     * hasChanges
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#hasChanges()
     */
    public boolean hasChanges() {
        return false;
    }

    /**
     * (non-Javadoc)
     * getMatrixValue
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#getMatrixValue(java.lang.String)
     */
    public Object getMatrixValue(String _str) {
        return null;
    }

    /**
     * (non-Javadoc)
     * putMatrixValue
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#putMatrixValue(java.lang.String, java.lang.Object)
     */
    public void putMatrixValue(String _str, Object _o) {
    }
    /**
     * (non-Javadoc)
     * isMatrixEditable
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#isMatrixEditable(java.lang.String)
     */
    public boolean isMatrixEditable(String _str) {
        return false;
    }

    /**
     * (non-Javadoc)
     * rollbackMatrix
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#rollbackMatrix()
     */
    public void rollbackMatrix() {
    }
    /**
     * (non-Javadoc)
     * addColumn
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#addColumn(COM.ibm.eannounce.objects.EANFoundation)
     */
    public void addColumn(EANFoundation _ean) {
    }
    /**
     * (non-Javadoc)
     * generatePickList
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#generatePickList(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Profile, java.lang.String)
     */
    public EntityList generatePickList(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRelatorType) {
        return null;
    }

    /**
     * (non-Javadoc)
     * removeLink
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#removeLink(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Profile, java.lang.String)
     */
    public boolean removeLink(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRowKey) throws EANBusinessRuleException {
        return false;
    }

    /**
     * (non-Javadoc)
     * link
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#link(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Profile, java.lang.String, COM.ibm.eannounce.objects.EntityItem[], java.lang.String)
     */
    public EANFoundation[] link(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRowKey, EntityItem[] _aeiChild, String _strLinkOption) throws EANBusinessRuleException {
        return null;
    }

    /**
     * (non-Javadoc)
     * create
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#create(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Profile, java.lang.String)
     */
    public EntityList create(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRelatorType) {
        return null;
    }

    /**
     * (non-Javadoc)
     * edit
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#edit(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Profile, java.lang.String[])
     */
    public EntityList edit(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String[] _astrKey) throws SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, RemoteException {
        return null;
    }

    /**
     * (non-Javadoc)
     * getWhereUsedList
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#getWhereUsedList(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Profile, java.lang.String)
     */
    public WhereUsedList getWhereUsedList(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRelatorType) {
        return null;
    }

    /**
     * (non-Javadoc)
     * getActionItemsAsArray
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#getActionItemsAsArray(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Profile, java.lang.String)
     */
    public Object[] getActionItemsAsArray(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strKey) {
        return null;
    }

    /**
     * (non-Javadoc)
     * isDynaTable
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#isDynaTable()
     */
    public boolean isDynaTable() {
        return false;
    }

    /**
     * (non-Javadoc)
     * duplicate
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#duplicate(java.lang.String, int)
     */
    public boolean duplicate(String _strKey, int _iDup) {
        return false;
    }
    /**
     * (non-Javadoc)
     * linkAndRefresh
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#linkAndRefresh(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Profile, COM.ibm.eannounce.objects.LinkActionItem)
     */
    public Object linkAndRefresh(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, LinkActionItem _lai) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, EANBusinessRuleException, LockException, WorkflowException, RemoteException, RemoteException {
        return null;
    }
}
