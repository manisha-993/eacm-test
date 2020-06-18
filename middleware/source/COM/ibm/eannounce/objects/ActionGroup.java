//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: ActionGroup.java,v $
// Revision 1.123  2014/04/17 20:49:00  wendy
// RCQ00302088 - readonly workflow action
//
// Revision 1.122  2008/08/01 12:28:19  wendy
// Change action type to View
//
// Revision 1.121  2008/07/31 18:59:06  wendy
// CQ00006067-WI : LA CTO - Added support for QueryAction
//
// Revision 1.120  2006/03/07 17:39:08  joan
// remove unneeded files
//
// Revision 1.119  2005/08/30 21:00:54  joan
// remove comments made by cvs to compile
//
// Revision 1.118  2005/08/30 17:39:14  dave
// new cat comments
//
// Revision 1.116  2005/07/22 19:48:11  joan
// fixes
//
// Revision 1.117  2005/08/16 16:20:29  joan
// fix for order
//
// Revision 1.116  2005/07/22 19:48:11  joan
// fixes
//
// Revision 1.115  2005/06/23 17:48:51  joan
// add debug
//
// Revision 1.114  2005/06/23 17:07:57  joan
// add Category order
//
// Revision 1.113  2005/03/03 21:25:15  dave
// NEW_LINE on EAN Foundation
//
// Revision 1.112  2005/03/01 23:39:20  joan
// work on Meta Maintenance
//
// Revision 1.111  2005/02/09 22:13:43  dave
// more JTest Cleanup
//
// Revision 1.110  2005/01/18 21:33:09  dave
// removing the debug parms from code (sp internalized them)
//
// Revision 1.109  2004/08/02 19:34:01  joan
// add CGActionItem
//
// Revision 1.108  2004/06/21 15:02:35  joan
// add ABRStatus action
//
// Revision 1.107  2004/06/18 17:11:16  joan
// work on edit relator
//
// Revision 1.106  2004/06/15 20:36:41  joan
// add CopyActionItem
//
// Revision 1.105  2004/01/14 18:41:23  dave
// more squeezing of the short description
//
// Revision 1.104  2003/12/17 17:58:27  joan
// work on SG PDG
//
// Revision 1.103  2003/12/03 18:09:06  joan
// add HWFUPPOFPDG
//
// Revision 1.102  2003/10/29 23:56:20  dave
// fixing null pointers
//
// Revision 1.101  2003/10/29 22:25:41  dave
// action group cons parm mismatch
//
// Revision 1.100  2003/10/29 22:16:18  dave
// removed getProfile from many many new.. statements
// when the parent is not null
//
// Revision 1.99  2003/08/18 21:05:06  dave
// Adding  the sequencing chain to the islocked to not
// induced each cell for being locked in the islocked of
// entityItem (kludge)
//
// Revision 1.98  2003/06/25 18:43:57  joan
// move changes from v111
//
// Revision 1.97.2.2  2003/06/24 20:52:34  joan
// add PCDCSOLPDG1
//
// Revision 1.97.2.1  2003/06/13 22:32:44  joan
// add SPDG
//
// Revision 1.97  2003/05/15 19:27:24  dave
// implementing isReadOnly I
//
// Revision 1.96  2003/04/29 20:13:43  joan
// add HWUpgradePIPDG
//
// Revision 1.95  2003/04/29 17:05:46  dave
// clean up and removal of uneeded function
//
// Revision 1.94  2003/04/18 20:03:16  joan
// add HWUpgrade
//
// Revision 1.93  2003/04/17 17:49:07  joan
// add Hardware Upgrade PDG
//
// Revision 1.92  2003/04/14 18:52:31  joan
// add HWUpgradeModelPDG
//
// Revision 1.91  2003/04/01 18:43:29  joan
// add HWFeaturePDG
//
// Revision 1.90  2003/03/28 21:35:00  joan
// add HW PDG action items
//
// Revision 1.89  2003/03/27 23:43:33  dave
// removed some  commits
//
// Revision 1.88  2003/03/27 23:07:19  dave
// adding some timely commits to free up result sets
//
// Revision 1.87  2003/03/27 16:24:37  joan
// fix null pointer
//
// Revision 1.86  2003/03/26 20:37:28  joan
// add SW support and maintenance PDGs
//
// Revision 1.85  2003/03/25 21:33:35  joan
// debug
//
// Revision 1.84  2003/03/20 21:33:50  joan
// add SW subscription action items
//
// Revision 1.83  2003/03/10 16:59:09  joan
// work on  PDG
//
// Revision 1.82  2003/03/04 01:12:48  joan
// fix compile
//
// Revision 1.81  2003/03/04 01:00:15  joan
// add pdg action item
//
// Revision 1.80  2003/03/03 18:36:35  joan
// add PDG action item
//
// Revision 1.79  2003/02/11 18:24:14  gregg
// return correct boolean in updatePdhMeta method indicating whether any changes were indeed posted to pdh.
//
// Revision 1.78  2003/01/14 00:23:02  gregg
// removed entityclass param in gbl7506 -- we want to ensure entitytype is unique across ALL entityclasses
//
// Revision 1.77  2003/01/13 22:28:19  gregg
// extra EntityClass param passed into GBL7506
//
// Revision 1.76  2002/12/26 22:40:57  gregg
// new Constructor for ActionGroupImplicator
//
// Revision 1.75  2002/12/10 19:17:48  gregg
// changes to dummy insert in updatePDhMeta
//
// Revision 1.74  2002/11/19 23:26:55  joan
// fix hasLock method
//
// Revision 1.73  2002/11/19 18:27:41  joan
// adjust lock, unlock
//
// Revision 1.72  2002/11/19 00:06:25  joan
// adjust isLocked method
//
// Revision 1.71  2002/11/18 21:20:25  gregg
// ignore case logic on performFilter
//
// Revision 1.70  2002/11/18 18:28:11  gregg
// allow use of wildcards on performFilter()
//
// Revision 1.69  2002/10/14 21:43:06  joan
// add HWUPGRADEActionItem
//
// Revision 1.68  2002/10/07 17:41:37  joan
// add getLockGroup method
//
// Revision 1.67  2002/10/02 15:30:08  joan
// add HWPDGActionItem
//
// Revision 1.66  2002/09/27 18:52:57  gregg
// some static sort/filter types arrays mods
//
// Revision 1.65  2002/09/11 22:50:02  gregg
// throw some Exceptions in putNewActionItem() method
//
// Revision 1.64  2002/09/11 22:21:00  gregg
// somne _db.freeStatements
//
// Revision 1.63  2002/09/11 22:09:15  gregg
// getAllActionItemsForEntity method
//
// Revision 1.62  2002/09/11 16:58:35  gregg
// updatePdhMeta - correctly expire metadescription,metaentity rows on expire of ActionGroup
//
// Revision 1.61  2002/09/11 16:38:57  gregg
// updatePdhMeta -insert dummy record if no Role/Action/Entity/Group record exists
//
// Revision 1.60  2002/09/10 23:30:21  gregg
// updatePdhMeta - Role/Action/Entity/Group stuff
//
// Revision 1.59  2002/09/10 23:05:01  gregg
// updatePdhMeta - MetaDescription/MetaEntity updates
//
// Revision 1.58  2002/09/10 22:22:45  gregg
// updatePdhMeta logic
//
// Revision 1.57  2002/09/09 23:15:00  gregg
// implement EANSortableList + removeActionItem, putActionItem methods
//
// Revision 1.56  2002/09/09 20:26:00  gregg
// isDisplayFiltered, setDisplayFiltered now protected
//
// Revision 1.55  2002/09/09 20:19:53  gregg
// displayableForFilter methods
//
// Revision 1.54  2002/09/09 20:14:54  gregg
// implement EANComparable
//
// Revision 1.53  2002/08/08 20:51:48  joan
// fix setParentEntityItem
//
// Revision 1.52  2002/08/08 20:07:25  joan
// fix setParentEntityItem
//
// Revision 1.51  2002/07/30 01:41:30  dave
// more clean up
//
// Revision 1.50  2002/07/18 21:51:48  joan
// add SBRActionItem
//
// Revision 1.49  2002/06/21 16:58:23  joan
// add WhereUsedActionItem
//
// Revision 1.48  2002/06/05 17:37:45  joan
// add MatrixActionItem
//
// Revision 1.47  2002/05/20 21:31:11  joan
// add setParentEntityItem
//
// Revision 1.46  2002/05/14 17:47:05  joan
// working on LockActionItem
//
// Revision 1.45  2002/05/14 16:07:42  joan
// add code for LockActionItem
//
// Revision 1.44  2002/05/13 23:10:14  joan
// fix error
//
// Revision 1.43  2002/05/13 22:59:17  joan
// add code for new entityitem in hasLock method
//
// Revision 1.42  2002/05/13 21:04:17  gregg
// included WatchdogActionItem
//
// Revision 1.41  2002/05/13 20:40:32  joan
// add resetLockGroup method
//
// Revision 1.40  2002/05/10 22:04:55  joan
// add hasLock method
//
// Revision 1.39  2002/05/10 20:45:54  joan
// fixing lock
//
// Revision 1.38  2002/05/02 21:31:47  joan
// add code for DeleteActionItem
//
// Revision 1.37  2002/04/22 22:18:23  joan
// working on unlock
//
// Revision 1.36  2002/04/22 19:43:53  dave
// adding Workflow action item stub
//
// Revision 1.35  2002/04/22 18:20:12  joan
// fixing compile errors
//
// Revision 1.34  2002/04/19 22:34:05  joan
// change isLocked interface to include profile as parameter
//
// Revision 1.33  2002/04/19 20:53:29  dave
// attempting to encorporate ExtractActionItem and ReportActionItem
//
// Revision 1.32  2002/04/19 18:08:07  joan
// fixing compiling error
//
// Revision 1.31  2002/04/19 17:17:02  joan
// change isLocked  interface
//
// Revision 1.30  2002/04/12 22:44:17  joan
// throws exception
//
// Revision 1.29  2002/04/12 16:42:04  dave
// added isLocked to the tableDef
//
// Revision 1.28  2002/03/21 18:38:56  dave
// added getHelp to the EANTableModel
//
// Revision 1.27  2002/03/21 00:22:56  dave
// adding rollback logic to the rowSelectable table
//
// Revision 1.26  2002/03/20 18:33:55  dave
// expanding the get for the EANAddressable to
// include a verbose boolean to dictate what gets sent back
//
// Revision 1.25  2002/03/11 21:22:39  dave
// syntax for first stage of edit
//
// Revision 1.24  2002/03/06 23:53:09  dave
// introduction of the edit action item
//
// Revision 1.23  2002/03/06 22:46:24  dave
// added the logic to create the SearchActionItem in ActionGroup
//
// Revision 1.22  2002/03/04 19:42:29  dave
// adding CreateActionItem to eannounce
//
// Revision 1.21  2002/02/26 21:43:59  dave
// ensuring I am setting the rs = null after a close
//
// Revision 1.20  2002/02/20 18:07:06  dave
// adding new Link Action Item
//
// Revision 1.19  2002/02/15 18:06:52  dave
// expanded EAN Table structures
//
// Revision 1.18  2002/02/13 23:29:23  dave
// more table model for ActionGroups from a list
//
// Revision 1.17  2002/02/12 21:01:58  dave
// added toString methods for diplay help
//
// Revision 1.16  2002/02/12 18:51:44  dave
// more changes to dump
//
// Revision 1.15  2002/02/11 19:19:40  dave
// more dump rearranging
//
// Revision 1.14  2002/02/11 18:55:04  dave
// more dump rearranges
//
// Revision 1.13  2002/02/09 21:50:41  dave
// more syntax
//
// Revision 1.12  2002/02/09 21:39:34  dave
// syntax fixes
//
// Revision 1.11  2002/02/09 21:14:36  dave
// more arranging  of Abstract Classing
//
// Revision 1.10  2002/01/31 22:57:18  dave
// more Foundation Cleanup
//
// Revision 1.9  2002/01/18 21:51:27  dave
// fix to constuctor and rdrs
//
// Revision 1.8  2002/01/18 17:48:24  dave
// syntax
//
// Revision 1.7  2002/01/18 17:31:12  dave
// multiple lookup capability on ActionItem group from
// within an Action List
//
// Revision 1.6  2002/01/17 18:33:53  dave
// misc adjustments to help cloning and copying of structures
// in e-announce
//
// Revision 1.5  2002/01/14 22:07:54  dave
// minor abract fixes
//
// Revision 1.4  2002/01/14 21:36:58  dave
// removed astract for now.. and fixed constructor info
//
// Revision 1.3  2002/01/14 21:25:45  dave
// minor syntax fix
//
// Revision 1.2  2002/01/14 21:19:10  dave
// minor syntax
//
// Revision 1.1  2002/01/14 21:01:46  dave
// new Object to manage ActionGroups
//

package COM.ibm.eannounce.objects;

import java.util.Arrays;
import java.util.Hashtable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.lang.reflect.Constructor;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;
//
// This guy is defined via a Role/PreviousAction/Target EntityType
// This makes up the key
/**
 * ActionGroup
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ActionGroup extends EANMetaEntity implements EANAddressable, EANComparable, EANSortableList {

    static final long serialVersionUID = 20011106L;
    /**
     * FIELD
     */
    public static final String DESCRIPTION = "0";

    private String m_strEntityType = null;

    private Hashtable m_hashOrders = null;

    /**
     * FIELD
     */
    public static final String SORT_BY_LONG_DESCRIPTION = "Long Description";
    /**
     * FIELD
     */
    public static final String SORT_BY_SHORT_DESCRIPTION = "Short Description";
    /**
     * FIELD
     */
    public static final String SORT_BY_ACTION_GROUP_KEY = "Action Group Key";
    /**
     * FIELD
     */
    public static final String SORT_BY_ENTITY_TYPE = "Entity Type";

    private String m_strCompareField = SORT_BY_LONG_DESCRIPTION;
    private boolean m_bDisplayFiltered = false;
    private SortFilterInfo m_sfi = null;

    /**
     * FIELD
     */
    protected static String[] c_saFilterTypes = EANActionItem.c_saSortTypes;
    /**
     * FIELD
     */
    public static final String FILTER_ITEMS_BY_LONG_DESCRIPTION = c_saFilterTypes[0];
    /**
     * FIELD
     */
    public static final String FILTER_ITEMS_BY_SHORT_DESCRIPTION = c_saFilterTypes[1];
    /**
     * FIELD
     */
    public static final String FILTER_ITEMS_BY_ACTION_NAME = c_saFilterTypes[2];
    /**
     * FIELD
     */
    public static final String FILTER_ITEMS_BY_ACTION_CLASS = c_saFilterTypes[3];

    /**
     * FIELD
     */
    protected static String[] c_saSortTypes = c_saFilterTypes;
    /**
     * FIELD
     */
    public static final String SORT_ITEMS_BY_LONG_DESCRIPTION = c_saSortTypes[0];
    /**
     * FIELD
     */
    public static final String SORT_ITEMS_BY_SHORT_DESCRIPTION = c_saSortTypes[1];
    /**
     * FIELD
     */
    public static final String SORT_ITEMS_BY_ACTION_NAME = c_saSortTypes[2];
    /**
     * FIELD
     */
    public static final String SORT_ITEMS_BY_ACTION_CLASS = c_saSortTypes[3];

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
        return "$Id: ActionGroup.java,v 1.123 2014/04/17 20:49:00 wendy Exp $";
    }

    /**
     * This represents an Action Group.  It can only be constructed via a database connection, a Profile, and an Action Item Key*
     *
     * @param _emf
     * @param _db
     * @param _prof
     * @param _strActionGroupKey
     * @param _strEntityType
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     */
    public ActionGroup(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionGroupKey, String _strEntityType) throws MiddlewareException {

        super(_emf, _prof, _strActionGroupKey);

        setEntityType(_strEntityType);
        m_hashOrders = new Hashtable();

        try {

            Profile prof = getProfile();

            // Future.. pull structure out of cache if already pulled for this group.
            // Lets get a list of Action Items from the database here and put them into a re
            ReturnStatus returnStatus = new ReturnStatus(-1);
            ResultSet rs = null;
            ReturnDataResultSet rdrs = null;

            // Retrieve the Action Class and the Action Description.
            try {
                rs = _db.callGBL7002(returnStatus, prof.getOPWGID(), prof.getEnterprise(), _strActionGroupKey, "Group", prof.getReadLanguage().getNLSID(), prof.getValOn(), prof.getEffOn());
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
            for (int i = 0; i < rdrs.size(); i++) {
                _db.debug(D.EBUG_SPEW, "gbl7002 answer is:" + rdrs.getColumn(i, 0) + ":" + rdrs.getColumn(i, 1) + ":" + rdrs.getColumnInt(i, 2) + ":");
                //putShortDescription(rdrs.getColumnInt(i,2),rdrs.getColumn(i,0));
                putLongDescription(rdrs.getColumnInt(i, 2), rdrs.getColumn(i, 1));
            }
            // Retrieve the Action Class and the Action Description.
            try {
                rs = _db.callGBL7001(returnStatus, prof.getOPWGID(), prof.getEnterprise(), _strActionGroupKey, prof.getValOn(), prof.getEffOn());
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
            // Generate and place all the ActionItems in this list for this group.
            for (int i = 0; i < rdrs.size(); i++) {
                String strKey = rdrs.getColumn(i, 0);
                String strClass = rdrs.getColumn(i, 1);
                String strOrder = rdrs.getColumn(i, 2);
                String strCategory = rdrs.getColumn(i, 3);
                String strCategoryOrder = rdrs.getColumn(i, 4);
				_db.debug(D.EBUG_SPEW, "gbl7001 answer is:" + strKey + ":" + strClass + ":" + strOrder + ":" + strCategory + ":" + strCategoryOrder);
                putNewActionItem(_db, strKey, strClass, strCategoryOrder, prof.isReadOnly());
            }

            // We now have everything...

        } catch (Exception x) {
            System.out.println("ActionGroup exception: " + x);
            x.printStackTrace();
        } finally {
            _db.freeStatement();
            _db.isPending();
        }
    }

    /**
     * Used for an ActionGroupImplicator
     *
     * @param _emf
     * @param _prof
     * @param _strActionGroupKey
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     */
    protected ActionGroup(EANMetaFoundation _emf, Profile _prof, String _strActionGroupKey) throws MiddlewareException {
        super(_emf, _prof, _strActionGroupKey);
    }

    /**
     * build a new EANActionItem (proper subclass of) and store in this Action Group
     */
    private void putNewActionItem(Database _db, String _strKey, String _strClass, String _strOrder, boolean _bSkipUpdate) throws SQLException, MiddlewareException, MiddlewareRequestException {
        //store the orders for Action/Group by actionItemeKey
        m_hashOrders.put(_strKey, _strOrder);
        _db.debug(D.EBUG_SPEW, "gbl7001 answer is:" + _strKey + ":" + _strClass + ":" + _strOrder);
        if (_strClass.equals("Navigate")) {
            putData(new NavActionItem(this, _db, null, _strKey));
        } else if (_strClass.equals("Link") && !_bSkipUpdate) {
            putData(new LinkActionItem(this, _db, null, _strKey));
        } else if (_strClass.equals("Create") && !_bSkipUpdate) {
            putData(new CreateActionItem(this, _db, null, _strKey));
        } else if (_strClass.equals("Edit")) {
            putData(new EditActionItem(this, _db, null, _strKey));
        } else if (_strClass.equals("Copy")) {
            putData(new CopyActionItem(this, _db, null, _strKey));
        } else if (_strClass.equals("ABRStatus")) {
            putData(new ABRStatusActionItem(this, _db, null, _strKey));
        } else if (_strClass.equals("Search")) {
            putData(new SearchActionItem(this, _db, null, _strKey));
        } else if (_strClass.equals("Extract")) {
            putData(new ExtractActionItem(this, _db, null, _strKey));
        } else if (_strClass.equals("Report")) {
            putData(new ReportActionItem(this, _db, null, _strKey));
        //} else if (_strClass.equals("Workflow") && !_bSkipUpdate) { 
        } else if (_strClass.equals("Workflow")) {
        	//RCQ00302088
        	WorkflowActionItem wfa = new WorkflowActionItem(this, _db, null, _strKey);
            if(!_bSkipUpdate){
                putData(wfa);
            }else{
            	if(wfa.isReadOnly()){
            		 putData(wfa);
            	}else{
            		wfa.dereference();
            	}
            }
        } else if (_strClass.equals("Delete") && !_bSkipUpdate) {
            putData(new DeleteActionItem(this, _db, null, _strKey));
        } else if (_strClass.equals("Watchdog") && !_bSkipUpdate) {
            putData(new WatchdogActionItem(this, _db, null, _strKey));
        } else if (_strClass.equals("Lock") && !_bSkipUpdate) {
            putData(new LockActionItem(this, _db, null, _strKey));
        } else if (_strClass.equals("Matrix")) {
            putData(new MatrixActionItem(this, _db, null, _strKey));
        } else if (_strClass.equals("WhereUsed")) {
            putData(new WhereUsedActionItem(this, _db, null, _strKey));
//        } else if (_strClass.equals("HWPDG") && !_bSkipUpdate) {
//            putData(new HWPDGActionItem(this, _db, null, _strKey));
//        } else if (_strClass.equals("HWUPGRADE") && !_bSkipUpdate) {
//            putData(new HWUPGRADEActionItem(this, _db, null, _strKey));
        } else if (_strClass.equals("View")) {
            putData(new QueryActionItem(this, _db, null, _strKey));
        } else if (_strClass.equals("CG")) {
            putData(new CGActionItem(this, _db, null, _strKey));
        } else if (_strClass.equals("MetaMaint")) {
            putData(new MetaMaintActionItem(this, _db, null, _strKey));
        } else if (_strClass.equals("PDG") && !_bSkipUpdate) {
            if (_strKey.equals("SWSUBSPRODPDG")) {
                putData(new SWSubsProdPDG(this, _db, null, _strKey));
            } else if (_strKey.equals("SWSUBSPRODINITIALPDG")) {
                putData(new SWSubsProdInitialPDG(this, _db, null, _strKey));
            } else if (_strKey.equals("SWSUBSPRODSUPPPDG")) {
                putData(new SWSubsProdSuppPDG(this, _db, null, _strKey));
            } else if (_strKey.equals("SWSUBSFEATPDG")) {
                putData(new SWSubsFeatPDG(this, _db, null, _strKey));
            } else if (_strKey.equals("SWSUBSFEATINITIALPDG")) {
                putData(new SWSubsFeatInitialPDG(this, _db, null, _strKey));
            } else if (_strKey.equals("SWSUBSFEATSUPPPDG")) {
                putData(new SWSubsFeatSuppPDG(this, _db, null, _strKey));
            } else if (_strKey.equals("SWMAINTPRODPDG")) {
                putData(new SWMaintProdPDG(this, _db, null, _strKey));
            } else if (_strKey.equals("SWMAINTPRODINITIALPDG")) {
                putData(new SWMaintProdInitialPDG(this, _db, null, _strKey));
            } else if (_strKey.equals("SWMAINTPRODSUPPPDG")) {
                putData(new SWMaintProdSuppPDG(this, _db, null, _strKey));
            } else if (_strKey.equals("SWMAINTFEATPDG")) {
                putData(new SWMaintFeatPDG(this, _db, null, _strKey));
            } else if (_strKey.equals("SWMAINTFEATINITIALPDG")) {
                putData(new SWMaintFeatInitialPDG(this, _db, null, _strKey));
            } else if (_strKey.equals("SWMAINTFEATSUPPPDG")) {
                putData(new SWMaintFeatSuppPDG(this, _db, null, _strKey));
            } else if (_strKey.equals("SWSUPPPRODPDG")) {
                putData(new SWSuppProdPDG(this, _db, getProfile(), _strKey));
            } else if (_strKey.equals("SWSUPPPRODINITIALPDG")) {
                putData(new SWSuppProdInitialPDG(this, _db, null, _strKey));
            } else if (_strKey.equals("SWSUPPPRODSUPPPDG")) {
                putData(new SWSuppProdSuppPDG(this, _db, null, _strKey));
            } else if (_strKey.equals("SWSUPPFEATPDG")) {
                putData(new SWSuppFeatPDG(this, _db, null, _strKey));
            } else if (_strKey.equals("SWSUPPFEATINITIALPDG")) {
                putData(new SWSuppFeatInitialPDG(this, _db, null, _strKey));
            } else if (_strKey.equals("SWSUPPFEATSUPPPDG")) {
                putData(new SWSuppFeatSuppPDG(this, _db, null, _strKey));
            } else if (_strKey.equals("HWPRODBASEPDG")) {
                putData(new HWProdBasePDG(this, _db, null, _strKey));
            } else if (_strKey.equals("HWPRODINITIALPDG")) {
                putData(new HWProdInitialPDG(this, _db, null, _strKey));
            } else if (_strKey.equals("HWFEATUREPDG")) {
                putData(new HWFeaturePDG(this, _db, null, _strKey));
            } else if (_strKey.equals("HWUPMODELPDG")) {
                putData(new HWUpgradeModelPDG(this, _db, null, _strKey));
            } else if (_strKey.equals("HWFEATCONVPDG")) {
                putData(new HWFeatureConvertPDG(this, _db, null, _strKey));
            } else if (_strKey.equals("HWUPPTOPIPDG")) {
                putData(new HWUpgradePToPIPDG(this, _db, null, _strKey));
            } else if (_strKey.equals("HWUPPIPDG")) {
                putData(new HWUpgradePIPDG(this, _db, null, _strKey));
            } else if (_strKey.equals("PCDCSOLPDG1")) {
                putData(new CSOLRegionPCDPDG(this, _db, null, _strKey));
            } else {
                try {
                    Class c = Class.forName("COM.ibm.eannounce.objects." + _strKey);
                    Class[] ac = { Class.forName("COM.ibm.eannounce.objects.EANMetaFoundation"), Class.forName("COM.ibm.opicmpdh.middleware.Database"), Class.forName("COM.ibm.opicmpdh.middleware.Profile"), Class.forName("java.lang.String")};
                    Constructor con = c.getConstructor(ac);
                    Object[] ao = { this, _db, null, _strKey };
                    putData((PDGActionItem) con.newInstance(ao));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } else if (_strClass.equals("SPDG") && !_bSkipUpdate) {
            putData(new SPDGActionItem(this, _db, getProfile(), _strKey));
        } else {
            _db.debug(D.EBUG_ERR, "Unknown Action Item");
        }
        return;
    }

    //
    // ACCESSORS
    //

    //get the order for the given ActionItem in relation to this ActionGroup
    /**
     * getOrder
     *
     * @param _actionItem
     * @return
     *  @author David Bigelow
     */
    public String getOrder(EANActionItem _actionItem) {
        return (String) m_hashOrders.get(_actionItem.getActionItemKey());
    }

    /**
     * setOrder
     *
     * @param _actionItem
     * @param _strOrder
     *  @author David Bigelow
     */
    public void setOrder(EANActionItem _actionItem, String _strOrder) {
        m_hashOrders.remove(_actionItem.getActionItemKey());
        m_hashOrders.put(_actionItem.getActionItemKey(), _strOrder);
    }

    /**
     * This will return an ActionGroup w/ ALL available actions for the Entity Type that this group lives in
     *
     * @param _db
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @return ActionGroup
     */
    public ActionGroup getAllActionItemsForEntity(Database _db) throws MiddlewareException {

        ActionGroup agAll = new ActionGroup(this, _db, null, this.getActionGroupKey(), this.getEntityType());

        String strNow = null;
        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;

        // 1) get all actionItemKeys associated w/ this EntityType
        try {
            strNow = _db.getDates().getNow();
            try {
                rs = _db.callGBL7528(new ReturnStatus(-1), getProfile().getEnterprise(), getEntityType(), strNow, strNow);
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
            for (int row = 0; row < rdrs.getRowCount(); row++) {
                String strKey = rdrs.getColumn(row, 0);
                String strClass = rdrs.getColumn(row, 1);
                String strOrder = "Z";
                agAll.putNewActionItem(_db, strKey, strClass, strOrder, false);
            }
        } catch (Exception exc) {
            throw new MiddlewareException(exc.toString());
        } finally {
            _db.freeStatement();
            _db.isPending();
        }
        return agAll;
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
     * getActionGroupKey
     *
     * @return
     *  @author David Bigelow
     */
    public String getActionGroupKey() {
        return getKey();
    }

    /**
     * getActionItem
     *
     * @return
     *  @author David Bigelow
     */
    public EANList getActionItem() {
        return getData();
    }

    /**
     * getActionItem
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public EANActionItem getActionItem(int _i) {
        return (EANActionItem) getData(_i);
    }

    /**
     * getActionItem
     *
     * @param _s
     * @return
     *  @author David Bigelow
     */
    public EANActionItem getActionItem(String _s) {
        return (EANActionItem) getData(_s);
    }

    /**
     * getActionItemCount
     *
     * @return
     *  @author David Bigelow
     */
    public int getActionItemCount() {
        return getDataCount();
    }

    //
    // MUTATORS
    //

    /**
     * putActionItem
     *
     * @param _item
     *  @author David Bigelow
     */
    public void putActionItem(EANActionItem _item) {
        putData(_item);
    }

    /**
     * removeActionItem
     *
     * @param _item
     *  @author David Bigelow
     */
    public void removeActionItem(EANActionItem _item) {
        removeData(_item);
    }

    /**
     * setEntityType
     *
     * @param _s
     *  @author David Bigelow
     */
    protected void setEntityType(String _s) {
        m_strEntityType = _s;
    }

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
            strbResult.append("ActionGroup:" + getKey());
            strbResult.append(":ShortDesc:" + getShortDescription());
            strbResult.append(":LongDesc:" + getLongDescription());
            strbResult.append(NEW_LINE + ":ActionItems:");
            for (int i = 0; i < getActionItemCount(); i++) {
                EANActionItem ai = getActionItem(i);
                strbResult.append(NEW_LINE + i + ":" + ai.dump(_bBrief));
            }
        }

        return new String(strbResult);

    }

    /**
     * (non-Javadoc)
     * toString
     *
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return getLongDescription();
    }

    /*
    * This returns the object that we are interested in based on the _str key
    */
    /**
     * (non-Javadoc)
     * get
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#get(java.lang.String, boolean)
     */
    public Object get(String _str, boolean _b) {
        if (_str.equals(DESCRIPTION)) {
            return getLongDescription();
        }
        return null;
    }

    /**
     * (non-Javadoc)
     * getHelp
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#getHelp(java.lang.String)
     */
    public String getHelp(String _str) {
        return getLongDescription();
    }

    /**
     * (non-Javadoc)
     * getEANObject
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#getEANObject(java.lang.String)
     */
    public EANFoundation getEANObject(String _str) {
        return this;
    }

    /*
    * Nothing is editable .. so it is always false
    */
    /**
     * (non-Javadoc)
     * put
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#put(java.lang.String, java.lang.Object)
     */
    public boolean put(String _s, Object _o) throws EANBusinessRuleException {
        return false;
    }

    /*
    * Nothing is Editable .. so it is always false
    */
    /**
     * (non-Javadoc)
     * isEditable
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#isEditable(java.lang.String)
     */
    public boolean isEditable(String _s) {
        return false;
    }

    /*
    * Nothing is Editable .. so it is always false
    */
    /**
     * (non-Javadoc)
     * isLocked
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#isLocked(java.lang.String, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Database, COM.ibm.eannounce.objects.LockList, COM.ibm.opicmpdh.middleware.Profile, COM.ibm.eannounce.objects.EntityItem, int, java.lang.String, boolean)
     */
    public boolean isLocked(String _s, RemoteDatabaseInterface _rdi, Database _db, LockList _ll, Profile _prof, EntityItem _lockOwnerEI, int _iLockType, String _strTime, boolean _bCreateLock) {
        return false;
    }

    /*
    * No LockGroup to return
    */
    /**
     * (non-Javadoc)
     * getLockGroup
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#getLockGroup(java.lang.String)
     */
    public LockGroup getLockGroup(String _s) {
        return null;
    }

    /**
     * (non-Javadoc)
     * hasLock
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#hasLock(java.lang.String, COM.ibm.eannounce.objects.EntityItem, COM.ibm.opicmpdh.middleware.Profile)
     */
    public boolean hasLock(String _str, EntityItem _lockOwnerEI, Profile _prof) {
        return false;
    }

    /*
    * This rollback an attribute for the given string index into its structure
    */
    /**
     * (non-Javadoc)
     * rollback
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#rollback(java.lang.String)
     */
    public void rollback(String _str) {

    }

    /*
    * This does nothing
    */
    /**
     * (non-Javadoc)
     * unlock
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#unlock(java.lang.String, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Database, COM.ibm.eannounce.objects.LockList, COM.ibm.opicmpdh.middleware.Profile, COM.ibm.eannounce.objects.EntityItem, int)
     */
    public void unlock(String _s, RemoteDatabaseInterface _rdi, Database _db, LockList _ll, Profile _prof, EntityItem _lockOwnerEI, int _iLockType) {
    }

    /*
    * This does nothing
    */
    /**
     * (non-Javadoc)
     * resetLockGroup
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#resetLockGroup(java.lang.String, COM.ibm.eannounce.objects.LockList)
     */
    public void resetLockGroup(String _s, LockList _ll) {
    }

    /*
    * This does nothing
    */
    /**
     * (non-Javadoc)
     * setParentEntityItem
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#setParentEntityItem(COM.ibm.eannounce.objects.EntityItem)
     */
    public void setParentEntityItem(EntityItem _ei) {
    }

    // methods for EANComparable interface
    /**
     * (non-Javadoc)
     * getCompareField
     *
     * @see COM.ibm.eannounce.objects.EANComparable#getCompareField()
     */
    public String getCompareField() {
        return m_strCompareField;
    }

    /**
     * (non-Javadoc)
     * setCompareField
     *
     * @see COM.ibm.eannounce.objects.EANComparable#setCompareField(java.lang.String)
     */
    public void setCompareField(String _s) {
        m_strCompareField = _s;
    }

    /**
     * (non-Javadoc)
     * toCompareString
     *
     * @see COM.ibm.eannounce.objects.EANComparable#toCompareString()
     */
    public String toCompareString() {
        if (getCompareField().equals(SORT_BY_SHORT_DESCRIPTION)) {
            return getShortDescription();

        } else if (getCompareField().equals(SORT_BY_ACTION_GROUP_KEY)) {
            return getActionGroupKey();

        } else if (getCompareField().equals(SORT_BY_ENTITY_TYPE)) {
            return getEntityType();
        }
        return getLongDescription(); //default
    }

    /**
     * isDisplayFiltered
     *
     * @return
     *  @author David Bigelow
     */
    protected boolean isDisplayFiltered() {
        return m_bDisplayFiltered;
    }

    /**
     * setDisplayFiltered
     *
     * @param _b
     *  @author David Bigelow
     */
    protected void setDisplayFiltered(boolean _b) {
        m_bDisplayFiltered = _b;
    }
    //end EANComparable methods

    //{{{ === EANSortableList methods
    //Accessor to sort & filter info
    /**
     * (non-Javadoc)
     * getSFInfo
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#getSFInfo()
     */
    public SortFilterInfo getSFInfo() {
        if (m_sfi == null) {
            m_sfi = new SortFilterInfo(SORT_ITEMS_BY_LONG_DESCRIPTION, true, FILTER_ITEMS_BY_LONG_DESCRIPTION, null);
        }
        return m_sfi;
    }

    /**
     * perform filter on the list
     *
     * @param _bUseWildcards
     */
    public void performFilter(boolean _bUseWildcards) {
        //first go through and set the displayable
        //  - note that it is up to the calling party to check for isDisplayable on MetaRoles
        resetDisplayableActionItems();
        if (getSFInfo().getFilter() != null) {
            String strFilter = getSFInfo().getFilter();
            for (int i = 0; i < getObjectCount(); i++) {
                String strToCompare = "";
                if (getSFInfo().getFilterType().equals(FILTER_ITEMS_BY_LONG_DESCRIPTION)) {
                    strToCompare = getActionItem(i).getLongDescription();

                } else if (getSFInfo().getFilterType().equals(FILTER_ITEMS_BY_SHORT_DESCRIPTION)) {
                    strToCompare = getActionItem(i).getShortDescription();

                } else if (getSFInfo().getFilterType().equals(FILTER_ITEMS_BY_ACTION_NAME)) {
                    strToCompare = getActionItem(i).getActionItemKey();

                } else if (getSFInfo().getFilterType().equals(FILTER_ITEMS_BY_ACTION_CLASS)) {
                    strToCompare = getActionItem(i).getActionClass();
                }
                if (!_bUseWildcards) {
                    if (strToCompare != null && strToCompare.length() < strFilter.length()) {
                        getActionItem(i).setFiltered(true);
                    } else if (!strToCompare.substring(0, strFilter.length()).equalsIgnoreCase(strFilter)) {
                        getActionItem(i).setFiltered(true);
                    }
                } else {
                    if (!SortFilterInfo.equalsWithWildcards(strFilter, strToCompare, new char[] { '*', '%' }, true)) {
                        getActionItem(i).setFiltered(true);
                    }
                }
            }
        }
    }

    /**
     * (non-Javadoc)
     * performSort
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#performSort()
     */
    public void performSort() {
        EANComparable[] aC = new EANComparable[getObjectCount()];
        EANComparator ec = new EANComparator(getSFInfo().isAscending());

        for (int i = 0; i < getObjectCount(); i++) {
            EANComparable c = getObject(i);
            c.setCompareField(getSFInfo().getSortType());
            aC[i] = c;
        }
        Arrays.sort(aC, ec);
        resetData();
        for (int i = 0; i < aC.length; i++) {
            EANComparable c = aC[i];
            putObject(c);
        }
        return;
    }

    /**
     * (non-Javadoc)
     * getFilterTypesArray
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#getFilterTypesArray()
     */
    public String[] getFilterTypesArray() {
        return c_saFilterTypes;
    }

    /**
     * (non-Javadoc)
     * getSortTypesArray
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#getSortTypesArray()
     */
    public String[] getSortTypesArray() {
        return c_saSortTypes;
    }

    //get the number of items in the list
    /**
     * (non-Javadoc)
     * getObjectCount
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#getObjectCount()
     */
    public int getObjectCount() {
        return getActionItemCount();
    }

    //get the EANComparable object at (i);
    /**
     * (non-Javadoc)
     * getObject
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#getObject(int)
     */
    public EANComparable getObject(int _i) {
        return getActionItem(_i);
    }

    // is the object filtered out?
    /**
     * (non-Javadoc)
     * isObjectFiltered
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#isObjectFiltered(int)
     */
    public boolean isObjectFiltered(int _i) {
        return getActionItem(_i).isFiltered();
    }

    // set filter on an indexed item
    /**
     * (non-Javadoc)
     * setObjectFiltered
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#setObjectFiltered(int, boolean)
     */
    public void setObjectFiltered(int _i, boolean _b) {
        getActionItem(_i).setFiltered(_b);
    }

    private void resetDisplayableActionItems() {
        for (int i = 0; i < getObjectCount(); i++) {
            getActionItem(i).setFiltered(false);
        }
    }

    /**
     * (non-Javadoc)
     * putObject
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#putObject(COM.ibm.eannounce.objects.EANComparable)
     */
    public void putObject(EANComparable _ec) {
        putActionItem((EANActionItem) _ec);
    }

    /**
     * (non-Javadoc)
     * removeObject
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#removeObject(COM.ibm.eannounce.objects.EANComparable)
     */
    public void removeObject(EANComparable _ec) {
        removeActionItem((EANActionItem) _ec);
    }

    //}}}

    /**
     * updatePdhMeta
     *
     * @param _db
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @return
     *  @author David Bigelow
     */
    public boolean updatePdhMeta(Database _db) throws SQLException, MiddlewareException {
        return updatePdhMeta(_db, false);
    }

    /**
     * expirePdhMeta
     *
     * @param _db
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @return
     *  @author David Bigelow
     */
    public boolean expirePdhMeta(Database _db) throws SQLException, MiddlewareException {
        return updatePdhMeta(_db, true);
    }

    /**
     * updatePdhMeta
     *
     * @param _db
     * @param _bExpire
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @return
     *  @author David Bigelow
     */
    public boolean updatePdhMeta(Database _db, boolean _bExpire) throws SQLException, MiddlewareException {
        //do the whole Role/Action/Entity/Group thing
        //do the Role/Action/Entity/Group thing
        //  -- expire ALL records for this ActionGroup
        //  -- OR
        //  -- insert a dummy record if there are no current Role/Action/Entity/Group records

        boolean bChangesMade = false;
        boolean bNewActionGroup = false;

        ActionGroup ag_db = null;
        String strLinkType = null;
        String strLinkCode = null;
        String strNow = null;
        String strLinkValue = null;
        String strEnterprise = null;

        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;

        try {
            strNow = _db.getDates().getNow();
            strLinkValue = getActionGroupKey();
            strLinkType = "Role/Action/Entity/Group";
            _db.debug(D.EBUG_DETAIL, "callgbl7527(" + getProfile().getEnterprise() + "," + strLinkType + "," + strLinkValue + "," + strNow + "," + strNow + ")");
            try {
                rs = _db.callGBL7527(new ReturnStatus(-1), getProfile().getEnterprise(), strLinkType, strLinkValue, strNow, strNow);
                rdrs = new ReturnDataResultSet(rs);
            } finally {
				if (rs != null){
                	rs.close();
                	rs = null;
				}
                _db.freeStatement();
                _db.isPending();
            }
            //expire
            if (_bExpire) {
                for (int row = 0; row < rdrs.getRowCount(); row++) {
                    String strRole = rdrs.getColumn(row, 0);
                    String strAction = rdrs.getColumn(row, 1);
                    String strEntity = rdrs.getColumn(row, 2);
                    _db.debug(D.EBUG_SPEW, "gbl7527 answers:" + strRole + ":" + strAction + ":" + strEntity);
                    updateMetaLinkAttrRow(_db, true, strLinkType, strRole, strAction, strEntity, getActionGroupKey());
                    bChangesMade = true;
                }
                //add dummy row
            } else if (rdrs.getRowCount() == 0) {
                updateMetaLinkAttrRow(_db, false, strLinkType, "N/A", "N/A", getEntityType(), getActionGroupKey());
                bChangesMade = true;
            }

        } finally {
            _db.freeStatement();
            _db.isPending();
        }

        //update all "Group/Action" records in the MLA table
        ag_db = new ActionGroup((EANMetaFoundation) getParent(), _db, null, getActionGroupKey(), getEntityType());

        try {
            strLinkType = "Group/Action";
            bNewActionGroup = false;
            strLinkCode = "Link";
            //1)check existence of group in db
            strNow = _db.getDates().getNow();
            strEnterprise = getProfile().getEnterprise();

            try {
                rs = _db.callGBL7506(new ReturnStatus(-1), strEnterprise, getActionGroupKey(), strNow, strNow);
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
            if (rdrs.getRowCount() == 0) {
                bNewActionGroup = true;
            }
        } finally {
            _db.commit();
            _db.freeStatement();
            _db.isPending();
        }
        //1) expire
        if (_bExpire && !bNewActionGroup) {
            for (int i = 0; i < ag_db.getActionItemCount(); i++) {
                EANActionItem actionItem_db = getActionItem(i);
                String strLinkType2 = actionItem_db.getActionItemKey();
                strLinkValue = ag_db.getOrder(actionItem_db);
                updateMetaLinkAttrRow(_db, true, strLinkType, ag_db.getActionGroupKey(), strLinkType2, strLinkCode, strLinkValue);
            }
            updateMetaEntityRow(_db, true, ag_db.getActionGroupKey(), "Group");
            updateMetaDescriptionRow(_db, true, ag_db.getActionGroupKey(), "Group", ag_db.getShortDescription(), ag_db.getLongDescription());
            bChangesMade = true;
        } else if (!_bExpire) {
            //2) lets pick out changes by taking 2 passes :
            //   -- once through database records, once through current object
            //    a) go through db records -- expire any that do not apply to current object
            //       AND change any records that have changed
            for (int i = 0; i < ag_db.getActionItemCount(); i++) {
                EANActionItem actionItem_db = ag_db.getActionItem(i);
                String strActionItemKey_db = actionItem_db.getActionItemKey();
                String strOrder_db = ag_db.getOrder(actionItem_db);
                //see if this ActionItemKey currently exists..
                EANActionItem actionItem_this = this.getActionItem(strActionItemKey_db);
                //expire if not in current object
                if (actionItem_this == null) {
                    updateMetaLinkAttrRow(_db, true, strLinkType, ag_db.getActionGroupKey(), strActionItemKey_db, strLinkCode, strOrder_db);
                    bChangesMade = true;
                    //else if changed (the only thing that can vary here is linkValue -- so this only needs an update (yehaw)!
                } else if (!ag_db.getOrder(actionItem_db).equals(this.getOrder(actionItem_this))) {
                    updateMetaLinkAttrRow(_db, false, strLinkType, this.getActionGroupKey(), actionItem_this.getActionItemKey(), strLinkCode, this.getOrder(actionItem_this));
                    bChangesMade = true;
                }
            }
            //     b) go through current object and ADD any that aren't in database
            //        (changes should already have been carried out in step (a) above)
            for (int i = 0; i < this.getActionItemCount(); i++) {
                EANActionItem actionItem_this = this.getActionItem(i);
                String strActionItemKey_this = actionItem_this.getActionItemKey();
                String strOrder_this = this.getOrder(actionItem_this);
                EANActionItem actionItem_db = ag_db.getActionItem(strActionItemKey_this);
                if (actionItem_db == null) {
                    updateMetaLinkAttrRow(_db, false, strLinkType, this.getActionGroupKey(), strActionItemKey_this, strLinkCode, strOrder_this);
                    bChangesMade = true;
                }
            }
            //Now MetaEntity, MetaDescription tables if new add
            if (bNewActionGroup) {
                updateMetaEntityRow(_db, false, this.getActionGroupKey(), "Group");
                updateMetaDescriptionRow(_db, false, this.getActionGroupKey(), "Group", this.getShortDescription(), this.getLongDescription());
                bChangesMade = true;
            } else { //update metaDescription if changed
                if (!this.getShortDescription().equals(ag_db.getShortDescription()) || !this.getLongDescription().equals(ag_db.getLongDescription())) {
                    updateMetaDescriptionRow(_db, false, this.getActionGroupKey(), "Group", this.getShortDescription(), this.getLongDescription());
                    bChangesMade = true;
                }
            }
        }
        return bChangesMade;
    }

    private void updateMetaLinkAttrRow(Database _db, boolean _bExpire, String _strLinkType, String _strLinkType1, String _strLinkType2, String _strLinkCode, String _strLinkValue) throws MiddlewareException {
        String strValFrom = _db.getDates().getNow();
        String strValTo = (_bExpire ? strValFrom : _db.getDates().getForever());
        new MetaLinkAttrRow(getProfile(), _strLinkType, _strLinkType1, _strLinkType2, _strLinkCode, _strLinkValue, strValFrom, strValTo, strValFrom, strValTo, 2).updatePdh(_db);
    }

    private void updateMetaEntityRow(Database _db, boolean _bExpire, String _strEntType, String _strEntClass) throws MiddlewareException {
        String strValFrom = _db.getDates().getNow();
        String strValTo = (_bExpire ? strValFrom : _db.getDates().getForever());
        new MetaEntityRow(getProfile(), _strEntType, _strEntClass, strValFrom, strValTo, strValFrom, strValTo, 2).updatePdh(_db);
    }

    private void updateMetaDescriptionRow(Database _db, boolean _bExpire, String _strDescType, String _strDescClass, String _strShortDesc, String _strLongDesc) throws MiddlewareException {
        String strValFrom = _db.getDates().getNow();
        String strNow = strValFrom;
        String strValTo = (_bExpire ? strValFrom : _db.getDates().getForever());
        if (!_bExpire) {
            new MetaDescriptionRow(getProfile(), _strDescType, _strDescClass, _strShortDesc, _strLongDesc, getProfile().getReadLanguage().getNLSID(), strValFrom, strValTo, strValFrom, strValTo, 2).updatePdh(_db);

        } else { //we have to expire ALL nlsid's
            ResultSet rs = null;
            ReturnDataResultSet rdrs = null;
            try {
                try {
                    rs = _db.callGBL7511(new ReturnStatus(-1), getProfile().getEnterprise(), _strDescType, _strDescClass);
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
                for (int row = 0; row < rdrs.getRowCount(); row++) {
                    int iNLSID = rdrs.getColumnInt(row, 0);
                    String strShortDesc_nls = rdrs.getColumn(row, 1);
                    String strLongDesc_nls = rdrs.getColumn(row, 2);
                    new MetaDescriptionRow(getProfile(), _strDescType, _strDescClass, strShortDesc_nls, strLongDesc_nls, iNLSID, strNow, strNow, strNow, strNow, 2).updatePdh(_db);
                }
            } catch (SQLException exc) {
                throw new MiddlewareException(exc.toString());
            }
        }
    }
    /**
     * (non-Javadoc)
     * isParentAttribute
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#isParentAttribute(java.lang.String)
     */
    public boolean isParentAttribute(String _str) {
        return false;
    }
    /**
     * (non-Javadoc)
     * isChildAttribute
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#isChildAttribute(java.lang.String)
     */
    public boolean isChildAttribute(String _str) {
        return false;
    }

}
