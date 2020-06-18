//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//

//$Log: MetaActionItemList.java,v $
//Revision 1.57  2009/04/01 02:00:50  wendy
//Add query action to mdui
//
//Revision 1.56  2009/03/13 17:26:40  wendy
//Check for null before rs.close()
//
//Revision 1.55  2006/03/07 17:39:08  joan
//remove unneeded files
//
//Revision 1.54  2005/03/04 21:53:09  dave
//Jtest
//
//Revision 1.53  2005/03/04 21:35:51  dave
//Jtest work
//
//Revision 1.52  2003/12/17 17:58:27  joan
//work on SG PDG
//
//Revision 1.51  2003/12/03 18:09:06  joan
//add HWFUPPOFPDG
//
//Revision 1.50  2003/06/25 18:44:01  joan
//move changes from v111
//
//Revision 1.49.2.1  2003/06/24 20:52:33  joan
//add PCDCSOLPDG1
//
//Revision 1.49  2003/04/29 20:13:43  joan
//add HWUpgradePIPDG
//
//Revision 1.48  2003/04/29 17:05:47  dave
//clean up and removal of uneeded function
//
//Revision 1.47  2003/04/18 20:03:17  joan
//add HWUpgrade
//
//Revision 1.46  2003/04/17 17:49:08  joan
//add Hardware Upgrade PDG
//
//Revision 1.45  2003/04/14 18:52:31  joan
//add HWUpgradeModelPDG
//
//Revision 1.44  2003/04/01 18:43:30  joan
//add HWFeaturePDG
//
//Revision 1.43  2003/03/28 21:35:00  joan
//add HW PDG action items
//
//Revision 1.42  2003/03/26 20:37:29  joan
//add SW support and maintenance PDGs
//
//Revision 1.41  2003/03/20 21:33:50  joan
//add SW subscription action items
//
//Revision 1.40  2003/03/10 16:59:10  joan
//work on  PDG
//
//Revision 1.39  2003/03/04 01:12:48  joan
//fix compile
//
//Revision 1.38  2003/03/04 01:00:15  joan
//add pdg action item
//
//Revision 1.37  2003/03/03 18:36:36  joan
//add PDG action item
//
//Revision 1.36  2002/12/13 18:19:01  gregg
//in expireCategory method -> expire all 'Action/Category' records
//
//Revision 1.35  2002/12/13 00:37:50  gregg
//in expireCategory -- must also expire MetaEntityRow
//
//Revision 1.34  2002/12/12 23:56:14  gregg
//Converting to the good 'ol implicator method.
//Also removing all cache logic.
//
//Revision 1.33  2002/12/12 22:45:14  gregg
//expireCategory method
//
//Revision 1.32  2002/12/10 23:33:35  gregg
//closing off some db connections after use
//
//Revision 1.31  2002/11/18 21:20:24  gregg
//ignore case logic on performFilter
//
//Revision 1.30  2002/11/18 18:28:09  gregg
//allow use of wildcards on performFilter()
//
//Revision 1.29  2002/10/24 16:31:43  gregg
//in getAllCategories() method: check for null category b4 put
//
//Revision 1.28  2002/10/23 21:39:39  gregg
//getAllCategories method
//
//Revision 1.27  2002/10/17 18:33:01  gregg
//Category logic
//
//Revision 1.26  2002/10/14 21:43:07  joan
//add HWUPGRADEActionItem
//
//Revision 1.25  2002/10/02 15:30:08  joan
//add HWPDGActionItem
//
//Revision 1.24  2002/10/01 23:34:27  gregg
//made putCache method public
//
//Revision 1.23  2002/09/27 18:52:56  gregg
//some static sort/filter types arrays mods
//
//Revision 1.22  2002/09/23 17:30:03  gregg
//caching whole object
//
//Revision 1.20  2002/09/20 16:34:45  gregg
//some cleanup of code to only make one pass through rdrs for different nls's
//
//Revision 1.19  2002/09/19 22:59:55  gregg
//grab all nlsids
//
//Revision 1.18  2002/09/19 22:30:51  gregg
//add nlsid to gbl7524
//
//Revision 1.17  2002/09/18 22:13:53  gregg
//oops- cant close rs until rdrs is constructed
//
//Revision 1.16  2002/09/18 21:46:03  gregg
//close off rs on gbl7524
//
//Revision 1.15  2002/09/10 18:03:18  gregg
//check for null compare String in performFilter
//
//Revision 1.14  2002/09/06 17:38:47  gregg
//SortFilterInfo now uses String sort/filter key constants (were ints)
//
//Revision 1.13  2002/09/03 19:53:36  gregg
//putObject, removeObject methods
//
//Revision 1.12  2002/08/28 22:05:18  gregg
//correct long/shortdescriptions now set in constructor
//
//Revision 1.11  2002/08/28 20:58:06  gregg
//fix typo in constructor for Extracts
//
//Revision 1.10  2002/08/28 00:39:12  gregg
//removeActionItem method
//
//Revision 1.9  2002/08/08 23:37:56  gregg
//setObjectFiltered(), isObjectFiltered() methods
//
//Revision 1.8  2002/08/08 22:17:04  gregg
//getCompareField(), setCompareField(String) methods required by EANComparable inteface
//
//Revision 1.7  2002/08/08 21:56:01  gregg
//getObjectCount(), getObject(int) methods
//
//Revision 1.6  2002/08/08 20:35:45  gregg
//fix performSort() for compile
//
//Revision 1.5  2002/08/08 20:25:34  gregg
//various sort/filter related
//
//Revision 1.4  2002/08/08 19:30:55  gregg
//implement EANSortableList and added corresponding methods
//
//Revision 1.3  2002/08/08 16:19:01  gregg
//add WhereUsedActionItem, CreateActionItem
//
//Revision 1.2  2002/08/08 00:34:59  gregg
//minor adds
//
//Revision 1.1  2002/08/08 00:30:52  gregg
//intitial load

package COM.ibm.eannounce.objects;

import java.util.Arrays;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.lang.reflect.Constructor;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Stopwatch;

/**
 * Manage a list of ALL EANActionItems for an enterprise
 */
public class MetaActionItemList extends EANMetaEntity implements EANSortableList {

    private SortFilterInfo m_sfi = null;

    /**
     * FIELD
     */
    protected static String[] c_saFilterTypes = EANActionItem.c_saSortTypes;
    /**
     * FIELD
     */
    public static final String FILTER_BY_LONG_DESCRIPTION = c_saFilterTypes[0];
    /**
     * FIELD
     */
    public static final String FILTER_BY_SHORT_DESCRIPTION = c_saFilterTypes[1];
    /**
     * FIELD
     */
    public static final String FILTER_BY_ACTION_NAME = c_saFilterTypes[2];
    /**
     * FIELD
     */
    public static final String FILTER_BY_ACTION_CLASS = c_saFilterTypes[3];
    /**
     * FIELD
     */
    protected static String[] c_saSortTypes = EANActionItem.c_saSortTypes;
    /**
     * FIELD
     */
    public static final String SORT_BY_LONG_DESCRIPTION = EANActionItem.SORT_BY_LONG_DESCRIPTION;
    /**
     * FIELD
     */
    public static final String SORT_BY_SHORT_DESCRIPTION = EANActionItem.SORT_BY_SHORT_DESCRIPTION;
    /**
     * FIELD
     */
    public static final String SORT_BY_ACTION_NAME = EANActionItem.SORT_BY_ACTION_NAME;
    /**
     * FIELD
     */
    public static final String SORT_BY_ACTION_CLASS = EANActionItem.SORT_BY_ACTION_CLASS;

    /**
     * Create a MetaActionItemList object from database
     *
     * @param _em
     * @param _db
     * @param _oProfile
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     */
    public MetaActionItemList(EANMetaEntity _em, Database _db, Profile _oProfile) throws SQLException, MiddlewareException, MiddlewareRequestException {

        super(_em, _oProfile, _oProfile.getEnterprise());

        try {
            Stopwatch swOuter = new Stopwatch();

            ResultSet rs = null;
            ReturnDataResultSet rdrs = null;

            swOuter.start();

            try {
                rs = _db.callGBL7524(new ReturnStatus(-1), getProfile().getEnterprise());
                rdrs = new ReturnDataResultSet(rs);
            } finally {
            	if (rs!=null){
            		rs.close();
            	}
                rs = null;
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }
            for (int row = 0; row < rdrs.getRowCount(); row++) {
                String strActionName = rdrs.getColumn(row, 0);
                String strActionClass = rdrs.getColumn(row, 1);
                String strShortDescription = rdrs.getColumn(row, 2);
                String strLongDescription = rdrs.getColumn(row, 3);
                int iNLSID = rdrs.getColumnInt(row, 4);

                String strCatCode = rdrs.getColumn(row, 5);
                String strCatShortDesc = rdrs.getColumn(row, 6);
                String strCatLongDesc = rdrs.getColumn(row, 7);

                //check if its in there for a different nls already...
                EANActionItem aItem = getActionItem(strActionName);

                _db.debug(D.EBUG_SPEW, "gbl7524 answers:" + strActionName + ":" + strActionClass + ":" + strShortDescription + ":" + strLongDescription + ":" + iNLSID + ":" + strCatCode + ":" + strCatShortDesc + ":" + strCatLongDesc);

                //if it hasnt been built yet..
                if (aItem == null) {
                    //build implicator
                    aItem = new ActionItemImplicator(this, getProfile(), strActionName, strActionClass);
                }
                //it shouldnt be null!!
                if (aItem != null) {
                    //category
                    MetaTag mtCat = aItem.getCategory();

                    aItem.putShortDescription(iNLSID, strShortDescription);
                    aItem.putLongDescription(iNLSID, strLongDescription);

                    //see if we have built this for another nls already...
                    //if it hasnt been created yet, then build it
                    if (mtCat == null && strCatCode != null) {
                        mtCat = new MetaTag(aItem, aItem.getProfile(), strCatCode);
                        aItem.setCategory(mtCat);
                    }
                    //descs for all nls's
                    if (mtCat != null) {
                        if (strCatShortDesc != null) {
                            mtCat.putShortDescription(iNLSID, strCatShortDesc);
                        }
                        if (strCatLongDesc != null) {
                            mtCat.putLongDescription(iNLSID, strCatLongDesc);
                        }
                    }

                    //now store in list
                    putActionItem(aItem);
                } else {
                    _db.debug(D.EBUG_ERR, "MetaActionItemList constuctor: ActionItem is null!! (action class = " + strActionClass + ")");
                }
            }

            _db.debug(D.EBUG_SPEW, "(TESTING) build MetaActionItemList took:" + swOuter.finish());

        } finally {
            _db.freeStatement();
            _db.isPending();
        }
    }

    /**
     * Perform a simple test of this class
     *
     * @param args
     */
    public static void main(String[] args) {
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {
        return toString();
    }

    //Accessor to sort & filter info
    /**
     * (non-Javadoc)
     * getSFInfo
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#getSFInfo()
     */
    public SortFilterInfo getSFInfo() {
        if (m_sfi == null) {
            m_sfi = new SortFilterInfo(EANActionItem.SORT_BY_LONG_DESCRIPTION, true, MetaActionItemList.FILTER_BY_LONG_DESCRIPTION, null);
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
            for (int i = 0; i < getActionItemCount(); i++) {
                String strToCompare = "";
                if (getSFInfo().getFilterType().equals(FILTER_BY_LONG_DESCRIPTION)) {
                    strToCompare = getActionItem(i).getLongDescription();

                } else if (getSFInfo().getFilterType().equals(FILTER_BY_SHORT_DESCRIPTION)) {
                    strToCompare = getActionItem(i).getShortDescription();

                } else if (getSFInfo().getFilterType().equals(FILTER_BY_ACTION_NAME)) {
                    strToCompare = getActionItem(i).getActionItemKey();

                } else if (getSFInfo().getFilterType().equals(FILTER_BY_ACTION_CLASS)) {
                    strToCompare = getActionItem(i).getActionClass();
                }
                if (!_bUseWildcards) {
                    if (strToCompare != null && strToCompare.length() < strFilter.length()) {
                        getActionItem(i).setFiltered(true);
                    } else if (strToCompare != null && !strToCompare.substring(0, strFilter.length()).equalsIgnoreCase(strFilter)) {
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
     * rearrange the list so that it is sorted alphabetically by the specified type
     */
    public void performSort() {
        EANActionItem[] aA = new EANActionItem[getActionItemCount()];
        EANComparator ec = new EANComparator(getSFInfo().isAscending());
        for (int i = 0; i < getActionItemCount(); i++) {
            EANActionItem a = getActionItem(i);
            a.setCompareField(getSFInfo().getSortType());
            aA[i] = a;
        }
        Arrays.sort(aA, ec);
        resetMeta();
        for (int i = 0; i < aA.length; i++) {
            EANActionItem a = aA[i];
            putMeta(a);
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
        for (int i = 0; i < getActionItemCount(); i++) {
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

    /**
     * getActionItem
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public EANActionItem getActionItem(int _i) {
        return (EANActionItem) getMeta(_i);
    }

    /**
     * getActionItem
     *
     * @param _s
     * @return
     *  @author David Bigelow
     */
    public EANActionItem getActionItem(String _s) {
        return (EANActionItem) getMeta(_s);
    }

    //ANY CALLS FROM THE OUTSIDE WORLD SHOULD USE THESE TO ENSURE FULL OBJECTS ARE RETRIEVED
    /**
     * getActionItem
     *
     * @param _db
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public EANActionItem getActionItem(Database _db, int _i) {
        EANActionItem aItem = (EANActionItem) getMeta(_i);
        if (aItem == null) {
            return null;
        }
        return getRealActionItem(_db, aItem.getKey());
    }
    /**
     * getActionItem
     *
     * @param _db
     * @param _s
     * @return
     *  @author David Bigelow
     */
    public EANActionItem getActionItem(Database _db, String _s) {
        EANActionItem aItem = (EANActionItem) getMeta(_s);
        if (aItem == null) {
            return null;
        }
        return getRealActionItem(_db, aItem.getKey());
    }
    private EANActionItem getRealActionItem(Database _db, String _strKey) {
        if (getActionItem(_strKey) == null) {
            return null;
        }
        if (getActionItem(_strKey) instanceof ActionItemImplicator) {
            try {
                return putRealActionItem(_db, _strKey, getActionItem(_strKey).getActionClass());
            } catch (Exception exc) {
                _db.debug(D.EBUG_ERR, "Exception in MetaActionItemList.getRealActionItem():" + exc.toString());
            }
        }
        return getActionItem(_strKey);
    }
    ///////

    /**
     * getActionItemCount
     *
     * @return
     *  @author David Bigelow
     */
    public int getActionItemCount() {
        return getMetaCount();
    }

    /**
     * Get all possible categories currently existing in the list
     *
     * @return EANList
     */
    public EANList getAllCategories() {
        EANList eListCats = new EANList();
        for (int i = 0; i < getActionItemCount(); i++) {
            if (getActionItem(i).getCategory() != null) {
                eListCats.put(getActionItem(i).getCategory());
            }
        }
        return eListCats;
    }

    /**
     * expireCategory
     *
     * @param _db
     * @param _strCatCode
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     *  @author David Bigelow
     */
    public void expireCategory(Database _db, String _strCatCode) throws SQLException, MiddlewareRequestException, MiddlewareException {


        try {

            String strNow = _db.getDates().getNow();
            ResultSet rs = null;
            ReturnDataResultSet rdrs = null;

            try {
                rs = _db.callGBL7507(new ReturnStatus(-1), getProfile().getEnterprise(), "Action/Category", _strCatCode, "Link", strNow, strNow);
                rdrs = new ReturnDataResultSet(rs);
            } finally {
            	if (rs!=null){
            		rs.close();
            	}
                rs = null;
                _db.freeStatement();
                _db.isPending();
            }

            for (int row = 0; row < rdrs.getRowCount(); row++) {
                String strAction = rdrs.getColumn(row, 0);
                String strLinkValue = rdrs.getColumn(row, 1);
                new MetaLinkAttrRow(getProfile(), "Action/Category", strAction, _strCatCode, "Link", strLinkValue, strNow, strNow, strNow, strNow, 2).updatePdh(_db);
            }

            //now remove all instances of this category
            for (int i = 0; i < getActionItemCount(); i++) {
                if (getActionItem(i).getCategory() != null) {
                    if (getActionItem(i).getCategory().getKey().equals(_strCatCode)) {
                        getActionItem(i).setCategory(null);
                    }
                }
            }
        } finally {
        }
    }

    /**
     * putActionItem
     *
     * @param _eanActionItem
     *  @author David Bigelow
     */
    public void putActionItem(EANActionItem _eanActionItem) {
        putMeta(_eanActionItem);
    }

    /**
     * removeActionItem
     *
     * @param _eanActionItem
     *  @author David Bigelow
     */
    public void removeActionItem(EANActionItem _eanActionItem) {
        removeMeta(_eanActionItem);
    }

    /**
     * build a new EANActionItem (proper subclass of) and store in this Action Group
     */
    private EANActionItem putRealActionItem(Database _db, String _strKey, String _strClass) throws SQLException, MiddlewareException, MiddlewareRequestException {
        if (_strClass.equals("Navigate")) {
            putActionItem(new NavActionItem(this, _db, getProfile(), _strKey));
        } else if (_strClass.equals("Link")) {
            putActionItem(new LinkActionItem(this, _db, getProfile(), _strKey));
        } else if (_strClass.equals("Create")) {
            putActionItem(new CreateActionItem(this, _db, getProfile(), _strKey));
        } else if (_strClass.equals("Edit")) {
            putActionItem(new EditActionItem(this, _db, getProfile(), _strKey));
        } else if (_strClass.equals("Search")) {
            putActionItem(new SearchActionItem(this, _db, getProfile(), _strKey));
        } else if (_strClass.equals("Extract")) {
            putActionItem(new ExtractActionItem(this, _db, getProfile(), _strKey));
        } else if (_strClass.equals("Report")) {
            putActionItem(new ReportActionItem(this, _db, getProfile(), _strKey));
        } else if (_strClass.equals("Workflow")) {
            putActionItem(new WorkflowActionItem(this, _db, getProfile(), _strKey));
        } else if (_strClass.equals("Delete")) {
            putActionItem(new DeleteActionItem(this, _db, getProfile(), _strKey));
        } else if (_strClass.equals("Watchdog")) {
            putActionItem(new WatchdogActionItem(this, _db, getProfile(), _strKey));
        } else if (_strClass.equals("Lock")) {
            putActionItem(new LockActionItem(this, _db, getProfile(), _strKey));
        } else if (_strClass.equals("Matrix")) {
            putActionItem(new MatrixActionItem(this, _db, getProfile(), _strKey));
        } else if (_strClass.equals("WhereUsed")) {
            putActionItem(new WhereUsedActionItem(this, _db, getProfile(), _strKey));
//        } else if (_strClass.equals("HWPDG")) {
//            putActionItem(new HWPDGActionItem(this, _db, getProfile(), _strKey));
//        } else if (_strClass.equals("HWUPGRADE")) {
//            putActionItem(new HWUPGRADEActionItem(this, _db, getProfile(), _strKey));
        } else if (_strClass.equals("View")) {
            putActionItem(new QueryActionItem(this, _db, getProfile(), _strKey));
        }else if (_strClass.equals("PDG")) {
            if (_strKey.equals("SWSUBSPRODPDG")) {
                putData(new SWSubsProdPDG(this, _db, getProfile(), _strKey));
            } else if (_strKey.equals("SWSUBSPRODINITIALPDG")) {
                putData(new SWSubsProdInitialPDG(this, _db, getProfile(), _strKey));
            } else if (_strKey.equals("SWSUBSPRODSUPPPDG")) {
                putData(new SWSubsProdSuppPDG(this, _db, getProfile(), _strKey));
            } else if (_strKey.equals("SWSUBSFEATPDG")) {
                putData(new SWSubsFeatPDG(this, _db, getProfile(), _strKey));
            } else if (_strKey.equals("SWSUBSFEATINITIALPDG")) {
                putData(new SWSubsFeatInitialPDG(this, _db, getProfile(), _strKey));
            } else if (_strKey.equals("SWSUBSFEATSUPPPDG")) {
                putData(new SWSubsFeatSuppPDG(this, _db, getProfile(), _strKey));
            } else if (_strKey.equals("SWMAINTPRODPDG")) {
                putData(new SWMaintProdPDG(this, _db, getProfile(), _strKey));
            } else if (_strKey.equals("SWMAINTPRODINITIALPDG")) {
                putData(new SWMaintProdInitialPDG(this, _db, getProfile(), _strKey));
            } else if (_strKey.equals("SWMAINTPRODSUPPPDG")) {
                putData(new SWMaintProdSuppPDG(this, _db, getProfile(), _strKey));
            } else if (_strKey.equals("SWMAINTFEATPDG")) {
                putData(new SWMaintFeatPDG(this, _db, getProfile(), _strKey));
            } else if (_strKey.equals("SWMAINTFEATINITIALPDG")) {
                putData(new SWMaintFeatInitialPDG(this, _db, getProfile(), _strKey));
            } else if (_strKey.equals("SWMAINTFEATSUPPPDG")) {
                putData(new SWMaintFeatSuppPDG(this, _db, getProfile(), _strKey));
            } else if (_strKey.equals("SWSUPPPRODPDG")) {
                putData(new SWSuppProdPDG(this, _db, getProfile(), _strKey));
            } else if (_strKey.equals("SWSUPPPRODINITIALPDG")) {
                putData(new SWSuppProdInitialPDG(this, _db, getProfile(), _strKey));
            } else if (_strKey.equals("SWSUPPPRODSUPPPDG")) {
                putData(new SWSuppProdSuppPDG(this, _db, getProfile(), _strKey));
            } else if (_strKey.equals("SWSUPPFEATPDG")) {
                putData(new SWSuppFeatPDG(this, _db, getProfile(), _strKey));
            } else if (_strKey.equals("SWSUPPFEATINITIALPDG")) {
                putData(new SWSuppFeatInitialPDG(this, _db, getProfile(), _strKey));
            } else if (_strKey.equals("SWSUPPFEATSUPPPDG")) {
                putData(new SWSuppFeatSuppPDG(this, _db, getProfile(), _strKey));
            } else if (_strKey.equals("HWPRODBASEPDG")) {
                putData(new HWProdBasePDG(this, _db, getProfile(), _strKey));
            } else if (_strKey.equals("HWPRODINITIALPDG")) {
                putData(new HWProdInitialPDG(this, _db, getProfile(), _strKey));
            } else if (_strKey.equals("HWFEATUREPDG")) {
                putData(new HWFeaturePDG(this, _db, getProfile(), _strKey));
            } else if (_strKey.equals("HWUPMODELPDG")) {
                putData(new HWUpgradeModelPDG(this, _db, getProfile(), _strKey));
            } else if (_strKey.equals("HWFEATCONVPDG")) {
                putData(new HWFeatureConvertPDG(this, _db, getProfile(), _strKey));
            } else if (_strKey.equals("HWUPPTOPIPDG")) {
                putData(new HWUpgradePToPIPDG(this, _db, getProfile(), _strKey));
            } else if (_strKey.equals("HWUPPIPDG")) {
                putData(new HWUpgradePIPDG(this, _db, getProfile(), _strKey));
            } else if (_strKey.equals("PCDCSOLPDG1")) {
                putData(new CSOLRegionPCDPDG(this, _db, getProfile(), _strKey));
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
        } else {
            _db.debug(D.EBUG_ERR, "Unknown Action Item");
        }
        return getActionItem(_strKey);
    }

}
