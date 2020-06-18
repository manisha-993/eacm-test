//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
//{{{ Log
//$Log: DataDrivenWorkflowList.java,v $
//Revision 1.11  2005/02/14 17:18:33  dave
//more jtest fixing
//
//Revision 1.10  2003/01/07 22:51:38  gregg
//return a DataDrivenWorkflowGroup in resetGroup method
//
//Revision 1.9  2003/01/07 22:48:54  gregg
//resetGroup method
//
//Revision 1.8  2003/01/02 19:51:34  gregg
//return a boolean in updatePdhMeta indicating whether any PHD updates were performed.
//
//Revision 1.7  2002/11/18 21:20:25  gregg
//ignore case logic on performFilter
//
//Revision 1.6  2002/11/18 18:28:10  gregg
//allow use of wildcards on performFilter()
//
//Revision 1.5  2002/10/14 17:33:30  gregg
//fix for compile
//
//Revision 1.4  2002/10/14 17:20:25  gregg
//updatePdhMeta/expirePdhMeta methods
//
//Revision 1.3  2002/10/11 23:09:59  gregg
// serialVersionUID
//
//Revision 1.2  2002/10/11 20:12:36  gregg
//getVersion method
//
//Revision 1.1  2002/10/11 17:30:51  gregg
//initial load
//
//}}}

package COM.ibm.eannounce.objects;

import java.util.Arrays;
import java.sql.SQLException;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;

/**
 * Manages a list of ALL Role/Action/Entity/Group relationships for a single enterprise,
 * but only for roles that this profile can 'see' -- i.e. must be a maintainer of.
 * This object can be built + stored in session, pulling out DataDrivenWorkflowGroups for a role when necessary.
 */
public class DataDrivenWorkflowList extends EANMetaEntity implements EANSortableList {

    /**
    * @serial
    */
    static final long serialVersionUID = 1L;

    /**
     * FIELD
     */
    public static String[] c_saSortTypes = new String[] { DataDrivenWorkflowGroup.SORT_BY_ROLE_DESCRIPTION, DataDrivenWorkflowGroup.SORT_BY_ROLE_CODE };
    /**
     * FIELD
     */
    public static final String SORT_ITEMS_BY_ROLE_DESCRIPTION = c_saSortTypes[0];
    /**
     * FIELD
     */
    public static final String SORT_ITEMS_BY_ROLE_CODE = c_saSortTypes[1];
    /**
     * FIELD
     */
    public static String[] c_saFilterTypes = c_saSortTypes;
    /**
     * FIELD
     */
    public static final String FILTER_ITEMS_BY_ROLE_DESCRIPTION = c_saFilterTypes[0];
    /**
     * FIELD
     */
    public static final String FILTER_ITEMS_BY_ROLE_CODE = c_saFilterTypes[1];
    private SortFilterInfo m_sfi = null;

    /**
     * FIELD
     */
    public static final String HTML_NEW_LINE = "<BR>";

    /**
     * Grab all of the records for 'Role/Action/Entity/Group' for a given Enterprise
     *
     * @param _mrl
     * @param _db
     * @param _prof
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public DataDrivenWorkflowList(MetaRoleList _mrl, Database _db, Profile _prof) throws MiddlewareRequestException {
        super(_mrl, _prof, _prof.getEnterprise() + ":" + _mrl.getKey());
        try {
            for (int i = 0; i < getMetaRoleList().getMetaRoleCount(); i++) {
                putGroup(new DataDrivenWorkflowGroup(this, _db, getProfile(), getMetaRoleList().getMetaRole(i)));
            }
        } catch (Exception exc) {
            _db.debug(D.EBUG_ERR, "Exception in DataDrivenWorkflowList Constructor:" + exc);
        } finally {
            _db.freeStatement();
            _db.isPending();
        }
    }

    /**
     * getMetaRoleList
     *
     * @return
     *  @author David Bigelow
     */
    public MetaRoleList getMetaRoleList() {
        return (MetaRoleList) getParent();
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _b) {
        return getKey();
    }
    /**
     * reload a particular DataDrivenWorkflowGroup fresh from the database
     *
     * @param _db
     * @param _strRoleCode
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @return DataDrivenWorkflowGroup
     */
    public DataDrivenWorkflowGroup resetGroup(Database _db, String _strRoleCode) throws SQLException, MiddlewareException {
        DataDrivenWorkflowGroup ddwg = null;
        removeGroup(getGroup(_strRoleCode));
        ddwg = new DataDrivenWorkflowGroup(this, _db, getProfile(), getMetaRoleList().getMetaRole(_strRoleCode));
        putGroup(ddwg);
        return ddwg;
    }

    /**
     * getGroup
     *
     * @param _strRoleCode
     * @return
     *  @author David Bigelow
     */
    public DataDrivenWorkflowGroup getGroup(String _strRoleCode) {
        return (DataDrivenWorkflowGroup) getMeta(_strRoleCode);
    }
    //use getObject	
    private DataDrivenWorkflowGroup getGroup(int _i) {
        return (DataDrivenWorkflowGroup) getMeta(_i);
    }
    //use putObject
    private void putGroup(DataDrivenWorkflowGroup _ddwg) {
        putMeta(_ddwg);
    }
    //use removeObject
    private void removeGroup(DataDrivenWorkflowGroup _ddwg) {
        removeMeta(_ddwg);
    }

    /**
     * (non-Javadoc)
     * getSFInfo
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#getSFInfo()
     */
    public SortFilterInfo getSFInfo() {
        if (m_sfi == null) {
            m_sfi = new SortFilterInfo(SORT_ITEMS_BY_ROLE_DESCRIPTION, true, DataDrivenWorkflowList.FILTER_ITEMS_BY_ROLE_DESCRIPTION, null);
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
        resetDisplayableGroups();
        if (getSFInfo().getFilter() != null) {
            String strFilter = getSFInfo().getFilter();
            for (int i = 0; i < getObjectCount(); i++) {
                String strToCompare = "";
                if (getSFInfo().getFilterType().equals(FILTER_ITEMS_BY_ROLE_DESCRIPTION)) {
                    strToCompare = getGroup(i).getMetaRole().getLongDescription();
                
                } else if (getSFInfo().getFilterType().equals(FILTER_ITEMS_BY_ROLE_CODE)) {
                    strToCompare = getGroup(i).getMetaRole().getRoleCode();
                }
                if (!_bUseWildcards) {
                    if (strToCompare != null && strToCompare.length() < strFilter.length()) {
                        getGroup(i).setFiltered(true);
                    } else if (strToCompare != null && !strToCompare.substring(0, strFilter.length()).equalsIgnoreCase(strFilter)) {
                        getGroup(i).setFiltered(true);
                    }
                } else {
                    if (!SortFilterInfo.equalsWithWildcards(strFilter, strToCompare, new char[] { '*', '%' }, true)) {
                        getGroup(i).setFiltered(true);
                    }
                }
            }
        }
    }

    /**
     * rearrange the list so that it is sorted alphabetically by the specified type
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
  
    /**
     * (non-Javadoc)
     * getObjectCount
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#getObjectCount()
     */
    public int getObjectCount() {
        return getMetaCount();
    }
    /**
     * (non-Javadoc)
     * getObject
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#getObject(int)
     */
    public EANComparable getObject(int _i) {
        return getGroup(_i);
    }
  
    /**
     * (non-Javadoc)
     * isObjectFiltered
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#isObjectFiltered(int)
     */
    public boolean isObjectFiltered(int _i) {
        return getGroup(_i).isFiltered();
    }
    /**
     * (non-Javadoc)
     * setObjectFiltered
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#setObjectFiltered(int, boolean)
     */
    public void setObjectFiltered(int _i, boolean _b) {
        getGroup(_i).setFiltered(_b);
    }
  
    private void resetDisplayableGroups() {
        for (int i = 0; i < getObjectCount(); i++) {
            getGroup(i).setFiltered(false);
        }
    }
  
    /**
     * (non-Javadoc)
     * putObject
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#putObject(COM.ibm.eannounce.objects.EANComparable)
     */
    public void putObject(EANComparable _ec) {
        putGroup((DataDrivenWorkflowGroup) _ec);
    }

    /**
     * (non-Javadoc)
     * removeObject
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#removeObject(COM.ibm.eannounce.objects.EANComparable)
     */
    public void removeObject(EANComparable _ec) {
        removeGroup((DataDrivenWorkflowGroup) _ec);
    }

    //}}}

    ////////////////////
    // update methods //
    ////////////////////	

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
        DataDrivenWorkflowList ddwl_db = new DataDrivenWorkflowList(getMetaRoleList(), _db, getProfile());
        // 1) Expire
        if (_bExpire) {
            for (int i = 0; i < ddwl_db.getObjectCount(); i++) {
                bUpdatePerformed = (ddwl_db.getGroup(i).expirePdhMeta(_db) ? true : bUpdatePerformed);
            }
        }
        // 2) Update
        else {
            // a) in db, !in current: expire these
            for (int i = 0; i < ddwl_db.getObjectCount(); i++) {
                String strKey_db = ddwl_db.getGroup(i).getKey();
                if (this.getGroup(strKey_db) == null) {
                    bUpdatePerformed = (ddwl_db.getGroup(i).expirePdhMeta(_db) ? true : bUpdatePerformed);
                }
            }
            // b) let DataDrivenWorkflowGroup take care of any existing groups' updates
            for (int i = 0; i < this.getObjectCount(); i++) {
                bUpdatePerformed = (this.getGroup(i).updatePdhMeta(_db) ? true : bUpdatePerformed);
            }
        }
        return bUpdatePerformed;
    }

    ////////////////////

    /**
    * Return the date/time this class was generated
    * @return the date/time this class was generated
    */
    public String getVersion() {
        return "$Id: DataDrivenWorkflowList.java,v 1.11 2005/02/14 17:18:33 dave Exp $";
    }

}
