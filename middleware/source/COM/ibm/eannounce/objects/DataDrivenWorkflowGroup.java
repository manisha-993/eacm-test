//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
//{{{ Log
//$Log: DataDrivenWorkflowGroup.java,v $
//Revision 1.17  2005/02/14 17:18:33  dave
//more jtest fixing
//
//Revision 1.16  2003/03/27 23:07:20  dave
//adding some timely commits to free up result sets
//
//Revision 1.15  2003/01/02 19:51:33  gregg
//return a boolean in updatePdhMeta indicating whether any PHD updates were performed.
//
//Revision 1.14  2002/12/17 00:42:27  gregg
//getItem methods made public (were protected)
//
//Revision 1.13  2002/12/17 00:13:10  gregg
//remove System.outs
//
//Revision 1.12  2002/12/16 23:50:52  gregg
//some debug stmts
//
//Revision 1.11  2002/12/16 22:51:08  gregg
//removeItemAndAllChildren method
//
//Revision 1.10  2002/11/27 20:25:36  gregg
//in constructor: only put items that are not already in group
//
//Revision 1.9  2002/11/27 20:16:30  gregg
//parent/child/level/tree logic + methods
//
//Revision 1.8  2002/11/18 21:20:25  gregg
//ignore case logic on performFilter
//
//Revision 1.7  2002/11/18 18:28:10  gregg
//allow use of wildcards on performFilter()
//
//Revision 1.6  2002/10/14 17:20:25  gregg
//updatePdhMeta/expirePdhMeta methods
//
//Revision 1.5  2002/10/11 23:09:59  gregg
// serialVersionUID
//
//Revision 1.4  2002/10/11 20:12:36  gregg
//getVersion method
//
//Revision 1.3  2002/10/11 18:59:55  gregg
//trying to change indexes again...??
//
//Revision 1.2  2002/10/11 18:18:01  gregg
//fix to sort types array indexes
//
//Revision 1.1  2002/10/11 17:30:51  gregg
//initial load
//
//}}}

package COM.ibm.eannounce.objects;

import java.util.Arrays;
import java.sql.ResultSet;
import java.sql.SQLException;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.ReturnStatus;

/**
 * Manages a Group of Role/Action/Entity/Group relationships sharing a common >>Role<<
 */
public class DataDrivenWorkflowGroup extends EANMetaEntity implements EANSortableList, EANComparable {

    /**
    * @serial
    */
    static final long serialVersionUID = 1L;

    /**
     * FIELD
     */
    public static String[] c_saSortTypes = new String[] { DataDrivenWorkflowItem.SORT_BY_ROLE, DataDrivenWorkflowItem.SORT_BY_ACTION, DataDrivenWorkflowItem.SORT_BY_ENTITY, DataDrivenWorkflowItem.SORT_BY_GROUP };
    /**
     * FIELD
     */
    public static final String SORT_ITEMS_BY_ROLE = c_saSortTypes[0];
    /**
     * FIELD
     */
    public static final String SORT_ITEMS_BY_ACTION = c_saSortTypes[1];
    /**
     * FIELD
     */
    public static final String SORT_ITEMS_BY_ENTITY = c_saSortTypes[2];
    /**
     * FIELD
     */
    public static final String SORT_ITEMS_BY_GROUP = c_saSortTypes[3];
    /**
     * FIELD
     */
    public static String[] c_saFilterTypes = c_saSortTypes;
    /**
     * FIELD
     */
    public static final String FILTER_ITEMS_BY_ROLE = c_saFilterTypes[0];
    /**
     * FIELD
     */
    public static final String FILTER_ITEMS_BY_ACTION = c_saFilterTypes[1];
    /**
     * FIELD
     */
    public static final String FILTER_ITEMS_BY_ENTITY = c_saFilterTypes[2];
    /**
     * FIELD
     */
    public static final String FILTER_ITEMS_BY_GROUP = c_saFilterTypes[3];
    private SortFilterInfo m_sfi = null;

    /**
     * FIELD
     */
    public static final String SORT_BY_ROLE_DESCRIPTION = "Role Description";
    /**
     * FIELD
     */
    public static final String SORT_BY_ROLE_CODE = "Role Code";
    private String m_strCompareField = SORT_BY_ROLE_DESCRIPTION;

    private boolean m_bFiltered = false;

    private MetaRole m_oMetaRole = null;

    /**
     * FIELD
     */
    public static final String HTML_NEW_LINE = "<BR>";

    /**
     * Grab all of the records for 'Role/Action/Entity/Group' for a given Role
     *
     * @param _emf
     * @param _db
     * @param _prof
     * @param _mr
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public DataDrivenWorkflowGroup(EANMetaFoundation _emf, Database _db, Profile _prof, MetaRole _mr) throws SQLException, MiddlewareException, MiddlewareRequestException {
        super(_emf, _prof, _mr.getRoleCode());
        setMetaRole(_mr);
        try {
            
            ResultSet rs = null;
            ReturnDataResultSet rdrs = null;
            
            String strNow = _db.getDates().getNow();
            _db.debug(D.EBUG_DETAIL, "calling gbl7533:" + getProfile().getEnterprise() + ":" + getMetaRole().getRoleCode() + ":10:" + strNow + ":" + strNow);
            try {
                rs = _db.callGBL7533(new ReturnStatus(-1), getProfile().getEnterprise(), getMetaRole().getRoleCode(), 10, strNow, strNow);
                rdrs = new ReturnDataResultSet(rs);
            } finally {
                rs.close();
                rs = null;
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }
            
            for (int row = 0; row < rdrs.getRowCount(); row++) {
                int iLevel = rdrs.getColumnInt(row, 0);
                String strAction = rdrs.getColumn(row, 1);
                String strEntity = rdrs.getColumn(row, 2);
                String strGroup = rdrs.getColumn(row, 3);
                String strParentGroup = rdrs.getColumn(row, 4);
                DataDrivenWorkflowItem ddwi = new DataDrivenWorkflowItem(this, getProfile(), getMetaRole().getRoleCode(), strAction, strEntity, strGroup);
                if (getItem(ddwi.getKey()) == null) {
                    ddwi.setLevel(iLevel);
                    ddwi.setParentActionGroup(strParentGroup);
                    putItem(ddwi);
                }
            }
        } catch (Exception exc) {
            _db.debug(D.EBUG_ERR, "Exception in DataDrivenWorkflowGroup Constructor:" + exc);
        } finally {
            _db.freeStatement();
            _db.isPending();
        }
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _b) {
        StringBuffer sb = new StringBuffer("" + HTML_NEW_LINE + "------- DataDrivenWorkflowGroup --------" + HTML_NEW_LINE + "Role:" + getKey());
        if (!_b) {
            sb.append(HTML_NEW_LINE + "** " + getObjectCount() + " items **");
            for (int i = 0; i < getObjectCount(); i++) {
                sb.append(getItem(i).dump(_b));
            }
        }
        return sb.toString();
    }

    /**
     * getItem
     *
     * @param _strKey
     * @return
     *  @author David Bigelow
     */
    public DataDrivenWorkflowItem getItem(String _strKey) {
        return (DataDrivenWorkflowItem) getMeta(_strKey);
    }

    //use getObject
    /**
     * getItem
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public DataDrivenWorkflowItem getItem(int _i) {
        return (DataDrivenWorkflowItem) getMeta(_i);
    }

    //use putObject
    private void putItem(DataDrivenWorkflowItem _ddwi) {
        putMeta(_ddwi);
    }

    //use removeObject
    private void removeItem(DataDrivenWorkflowItem _ddwi) {
        removeMeta(_ddwi);
    }

    //{{{ === EANSortableList methods
    //{{{ getSFInfo() method
    //Accessor to sort & filter info
    /**
     * (non-Javadoc)
     * getSFInfo
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#getSFInfo()
     */
    public SortFilterInfo getSFInfo() {
        if (m_sfi == null) {
            m_sfi = new SortFilterInfo(SORT_ITEMS_BY_ACTION, true, DataDrivenWorkflowGroup.FILTER_ITEMS_BY_ACTION, null);
        }
        return m_sfi;
    }
    //}}}

    //{{{ performFilter() method
    /**
     * perform filter on the list
     *
     * @param _bUseWildcards 
     */
    public void performFilter(boolean _bUseWildcards) {
        //first go through and set the displayable
        //  - note that it is up to the calling party to check for isDisplayable on MetaRoles
        resetDisplayableItems();
        if (getSFInfo().getFilter() != null) {
            String strFilter = getSFInfo().getFilter();
            for (int i = 0; i < getObjectCount(); i++) {
                String strToCompare = "";
                if (getSFInfo().getFilterType().equals(FILTER_ITEMS_BY_ROLE)) {
                    strToCompare = getItem(i).getRole();
                
                } else if (getSFInfo().getFilterType().equals(FILTER_ITEMS_BY_ACTION)) {
                    strToCompare = getItem(i).getAction();
                
                } else if (getSFInfo().getFilterType().equals(FILTER_ITEMS_BY_ENTITY)) {
                    strToCompare = getItem(i).getEntity();
                
                } else if (getSFInfo().getFilterType().equals(FILTER_ITEMS_BY_GROUP)) {
                    strToCompare = getItem(i).getGroup();
                }
                if (!_bUseWildcards) {
                    if (strToCompare != null && strToCompare.length() < strFilter.length()) {
                        getItem(i).setFiltered(true);
                    } else if (strToCompare != null && !strToCompare.substring(0, strFilter.length()).equalsIgnoreCase(strFilter)) {
                        getItem(i).setFiltered(true);
                    }
                } else {
                    if (!SortFilterInfo.equalsWithWildcards(strFilter, strToCompare, new char[] { '*', '%' }, true)) {
                        getItem(i).setFiltered(true);
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
        resetMeta();
        for (int i = 0; i < aC.length; i++) {
            EANComparable c = aC[i];
            putObject(c);
        }
        return;
    }
    //}}}

    //{{{ getFilterTypesArray() method
    /**
     * (non-Javadoc)
     * getFilterTypesArray
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#getFilterTypesArray()
     */
    public String[] getFilterTypesArray() {
        return c_saFilterTypes;
    }
    //}}}

    //{{{ getSortTypesArray() method
    /**
     * (non-Javadoc)
     * getSortTypesArray
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#getSortTypesArray()
     */
    public String[] getSortTypesArray() {
        return c_saSortTypes;
    }
    //}}}

    //{{{ getObjectCount() method
    //get the number of items in the list
    /**
     * (non-Javadoc)
     * getObjectCount
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#getObjectCount()
     */
    public int getObjectCount() {
        return getMetaCount();
    }
    //}}}

    //{{{ getObject(int) method
    //get the EANComparable object at (i);
    /**
     * (non-Javadoc)
     * getObject
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#getObject(int)
     */
    public EANComparable getObject(int _i) {
        return getItem(_i);
    }
    //}}}

    //{{{ isObjectFiltered(int) method
    // is the object filtered out?
    /**
     * (non-Javadoc)
     * isObjectFiltered
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#isObjectFiltered(int)
     */
    public boolean isObjectFiltered(int _i) {
        return getItem(_i).isFiltered();
    }
    //}}}

    //{{{ setObjectFiltered(int) method
    // set filter on an indexed item
    /**
     * (non-Javadoc)
     * setObjectFiltered
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#setObjectFiltered(int, boolean)
     */
    public void setObjectFiltered(int _i, boolean _b) {
        getItem(_i).setFiltered(_b);
    }
    //}}}

    //{{{ resetDisplayableActionItems() method
    private void resetDisplayableItems() {
        for (int i = 0; i < getObjectCount(); i++) {
            getItem(i).setFiltered(false);
        }
    }
    //}}}

    /**
     * (non-Javadoc)
     * putObject
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#putObject(COM.ibm.eannounce.objects.EANComparable)
     */
    public void putObject(EANComparable _ec) {
        putItem((DataDrivenWorkflowItem) _ec);
    }

    /**
     * (non-Javadoc)
     * removeObject
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#removeObject(COM.ibm.eannounce.objects.EANComparable)
     */
    public void removeObject(EANComparable _ec) {
        removeItem((DataDrivenWorkflowItem) _ec);
    }

    //}}}

    //{{{ EANComparable methods
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
        if (getCompareField().equals(SORT_BY_ROLE_CODE)) {
            return getMetaRole().getRoleCode();
        }
        return getMetaRole().getLongDescription();
    }
    //}}}

    /////
    /**
     * setMetaRole
     *
     * @param _mr
     *  @author David Bigelow
     */
    protected void setMetaRole(MetaRole _mr) {
        m_oMetaRole = _mr;
    }

    /**
     * getMetaRole
     *
     * @return
     *  @author David Bigelow
     */
    public MetaRole getMetaRole() {
        return m_oMetaRole;
    }

    /**
     * isFiltered
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isFiltered() {
        return m_bFiltered;
    }

    /**
     * setFiltered
     *
     * @param _b
     *  @author David Bigelow
     */
    public void setFiltered(boolean _b) {
        m_bFiltered = _b;
    }

    /**
     * Remove this item AND all of its children
     *
     * @param _ddwi 
     */
    public void removeItemAndAllChildren(DataDrivenWorkflowItem _ddwi) {
        EANList eListChildren = null;
        if (_ddwi == null) {
            return;
        }
        eListChildren = _ddwi.getChildItems();
        for (int i = 0; i < eListChildren.size(); i++) {
            DataDrivenWorkflowItem ddwi = (DataDrivenWorkflowItem) eListChildren.getAt(i);
            removeItemAndAllChildren(ddwi);
        }
        //only remove once we have traversed ALL children
        this.removeItem(_ddwi);
        return;
    }

    //////////////////
    //update methods//
    //////////////////

    /**
     * updatePdhMeta
     *
     * @param _db
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws java.sql.SQLException
     * @return
     *  @author David Bigelow
     */
    public boolean updatePdhMeta(Database _db) throws MiddlewareException, SQLException {
        return updatePdhMeta(_db, false);
    }

    /**
     * expirePdhMeta
     *
     * @param _db
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws java.sql.SQLException
     * @return
     *  @author David Bigelow
     */
    public boolean expirePdhMeta(Database _db) throws MiddlewareException, SQLException {
        return updatePdhMeta(_db, true);
    }

    private boolean updatePdhMeta(Database _db, boolean _bExpire) throws MiddlewareException, SQLException {
        boolean bUpdatePerformed = false;
        // get a "clean" copy from db to compare against
        DataDrivenWorkflowGroup ddwg_db = new DataDrivenWorkflowGroup((EANMetaFoundation) getParent(), _db, getProfile(), getMetaRole());
        // 1) Expire
        if (_bExpire) {
            for (int i = 0; i < ddwg_db.getObjectCount(); i++) {
                bUpdatePerformed = (ddwg_db.getItem(i).expirePdhMeta(_db) ? true : bUpdatePerformed);
            }
        }
        // 2) Update - find diff's
        else {
            // a) in db, !in current: expire these
            for (int i = 0; i < ddwg_db.getObjectCount(); i++) {
                String strKey_db = ddwg_db.getItem(i).getKey();
                if (this.getItem(strKey_db) == null) {
                    bUpdatePerformed = (ddwg_db.getItem(i).expirePdhMeta(_db) ? true : bUpdatePerformed);
                }
            }
            // b) in current, !in db: insert these
            for (int i = 0; i < this.getObjectCount(); i++) {
                String strKey_this = this.getItem(i).getKey();
                if (ddwg_db.getItem(strKey_this) == null) {
                    bUpdatePerformed = (this.getItem(i).insertPdhMeta(_db) ? true : bUpdatePerformed);
                }
            }
            // c) in both: do nothing!!
        }
        return bUpdatePerformed;
    }

    //////////////////

    /**
    * Return the date/time this class was generated
    * @return the date/time this class was generated
    */
    public String getVersion() {
        return "$Id: DataDrivenWorkflowGroup.java,v 1.17 2005/02/14 17:18:33 dave Exp $";
    }
}
