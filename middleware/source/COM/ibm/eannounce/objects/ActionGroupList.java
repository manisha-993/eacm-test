//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
//{{{ Log
//$Log: ActionGroupList.java,v $
//Revision 1.23  2009/05/11 15:49:51  wendy
//Support dereference for memory release
//
//Revision 1.22  2005/02/09 22:13:43  dave
//more JTest Cleanup
//
//Revision 1.21  2005/01/18 21:33:09  dave
//removing the debug parms from code (sp internalized them)
//
//Revision 1.20  2004/01/14 18:41:23  dave
//more squeezing of the short description
//
//Revision 1.19  2003/11/05 19:24:52  gregg
//refining profile/emfParent logic for new ActionGroup() constructor
//
//Revision 1.18  2003/11/05 19:13:24  gregg
//fix null profile parent assertion
//
//Revision 1.17  2003/10/30 00:43:30  dave
//fixing all the profile references
//
//Revision 1.16  2003/10/29 22:16:18  dave
//removed getProfile from many many new.. statements
//when the parent is not null
//
//Revision 1.15  2003/03/27 23:07:19  dave
//adding some timely commits to free up result sets
//
//Revision 1.14  2003/01/13 23:14:10  gregg
//some timings of Action Groups updates
//
//Revision 1.13  2002/12/27 17:27:50  gregg
//some more ActionGroupImplicator logic
//
//Revision 1.12  2002/12/26 22:40:57  gregg
//new Constructor for ActionGroupImplicator
//
//Revision 1.11  2002/11/18 21:20:25  gregg
//ignore case logic on performFilter
//
//Revision 1.10  2002/11/18 18:28:11  gregg
//allow use of wildcards on performFilter()
//
//Revision 1.9  2002/10/16 17:02:49  gregg
//only put ActionGroup if longdescription != null
//
//Revision 1.8  2002/09/27 18:54:19  gregg
//more of the same (forgot to save all changes last time)
//
//Revision 1.7  2002/09/11 22:57:32  gregg
//some _db.freeStatements
//
//Revision 1.6  2002/09/10 23:21:12  gregg
//s'more meta updates
//
//Revision 1.5  2002/09/10 22:22:45  gregg
//updatePdhMeta logic
//
//Revision 1.4  2002/09/09 23:29:45  gregg
//fix sort types array
//
//Revision 1.3  2002/09/09 23:10:18  gregg
//setFiltered method logic
//
//Revision 1.2  2002/09/09 21:52:27  gregg
//oops! gbl7525 should be gbl7526
//
//Revision 1.1  2002/09/09 21:14:50  gregg
//initial load
//
//}}}

package COM.ibm.eannounce.objects;

import java.util.Arrays;
import java.sql.ResultSet;
import java.sql.SQLException;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.Stopwatch;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;

/**
 * Manage a list of ActionGroups for a given EntityType
 */
public class ActionGroupList extends EANMetaEntity implements EANSortableList {

    private SortFilterInfo m_sfi = null;

    /**
     * FIELD
     */
    protected static String[] c_saFilterTypes = new String[] { "Long Description", "Short Description", "Action Group Key", "Entity Type" };
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
    public static final String FILTER_BY_ACTION_GROUP_KEY = c_saFilterTypes[2];
    /**
     * FIELD
     */
    public static final String FILTER_BY_ENTITY_TYPE = c_saFilterTypes[3];

    /**
     * FIELD
     */
    protected static String[] c_saSortTypes = c_saFilterTypes;
    /**
     * FIELD
     */
    public static final String SORT_BY_LONG_DESCRIPTION = c_saSortTypes[0];
    /**
     * FIELD
     */
    public static final String SORT_BY_SHORT_DESCRIPTION = c_saSortTypes[1];
    /**
     * FIELD
     */
    public static final String SORT_BY_ACTION_GROUP_KEY = c_saSortTypes[2];
    /**
     * FIELD
     */
    public static final String SORT_BY_ENTITY_TYPE = c_saSortTypes[3];
    
    protected void dereference(){
    	if (m_sfi != null){
    		m_sfi.dereference();
    		m_sfi = null;
    	}
    	super.dereference();
    }

    /**
     * An ActionGroupList for one EntityGroup
     *
     * @param _eg
     * @param _db
     * @param _oProfile
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public ActionGroupList(EntityGroup _eg, Database _db, Profile _oProfile) throws SQLException, MiddlewareException, MiddlewareRequestException {

        super(_eg, _oProfile, _eg.getEntityType() + ":ActionGroupList");

        try {
            
            ResultSet rs = null;
            ReturnDataResultSet rdrs = null;
            
            String strNow = _db.getDates().getNow();
            String strLinkType = "Role/Action/Entity/Group";
            String strLinkCode = getEntityType();
            
            try {
                _db.debug(D.EBUG_DETAIL, "callgbl7526(" + _oProfile.getEnterprise() + "," + strLinkType + "," + strLinkCode + "," + strNow + "," + strNow + ")");
                rs = _db.callGBL7526(new ReturnStatus(-1), _oProfile.getEnterprise(), strLinkType, strLinkCode, strNow, strNow);
                rdrs = new ReturnDataResultSet(rs);
            } finally {
                rs.close();
                rs = null;
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }
            for (int row = 0; row < rdrs.getRowCount(); row++) {
                String strRole = rdrs.getColumn(row, 0);
                String strAction = rdrs.getColumn(row, 1);
                String strGroup = rdrs.getColumn(row, 2);
                ActionGroup ag = new ActionGroup(this, _db, null, strGroup, getEntityType());
                _db.debug(D.EBUG_SPEW, "gbl7526 answers:" + strRole + ":" + strAction + ":" + strGroup);
                if (ag.getLongDescription() != null) {
                    putActionGroup(ag);
                }
            }

        } finally {
            _db.commit();
            _db.freeStatement();
            _db.isPending();
        }
        return;
    }

    /**
     * an ActionGroupList of ALL ActionGroups for an enterprise
     *
     * @param _db
     * @param _oProfile
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public ActionGroupList(Database _db, Profile _oProfile) throws SQLException, MiddlewareException, MiddlewareRequestException {

        super(null, _oProfile, "ActionGroupList");

        try {
            String strNow = _db.getDates().getNow();
            ResultSet rs = null;
            ReturnDataResultSet rdrs = null;
            
            _db.debug(D.EBUG_DETAIL, "callgbl7529(" + _oProfile.getEnterprise() + ",Group," + strNow + "," + strNow + ")");
            try {
                rs = _db.callGBL7529(new ReturnStatus(-1), _oProfile.getEnterprise(), "Group", strNow, strNow);
                rdrs = new ReturnDataResultSet(rs);
            } finally {
                rs.close();
                rs = null;
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }
            for (int row = 0; row < rdrs.getRowCount(); row++) {
                String strGroup = rdrs.getColumn(row, 0);
                ActionGroupImplicator agi = new ActionGroupImplicator(this, _db, _oProfile, strGroup, "NONE");
                _db.debug(D.EBUG_SPEW, "gbl7529 answers:" + strGroup);
                if (agi.getLongDescription() != null) {
                    putActionGroup(agi);
                }
            }

        } finally {
            _db.freeStatement();
            _db.isPending();
        }
        return;
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
            m_sfi = new SortFilterInfo(SORT_BY_LONG_DESCRIPTION, true, FILTER_BY_LONG_DESCRIPTION, null);
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
        resetDisplayableActionGroups();
        if (getSFInfo().getFilter() != null) {
            String strFilter = getSFInfo().getFilter();
            for (int i = 0; i < getObjectCount(); i++) {
                String strToCompare = "";
                if (getSFInfo().getFilterType().equals(FILTER_BY_LONG_DESCRIPTION)) {
                    strToCompare = getActionGroup(i).getLongDescription();
                
                } else if (getSFInfo().getFilterType().equals(FILTER_BY_SHORT_DESCRIPTION)) {
                    strToCompare = getActionGroup(i).getShortDescription();
                
                } else if (getSFInfo().getFilterType().equals(FILTER_BY_ACTION_GROUP_KEY)) {
                    strToCompare = getActionGroup(i).getActionGroupKey();
                
                } else if (getSFInfo().getFilterType().equals(FILTER_BY_ENTITY_TYPE)) {
                    strToCompare = getActionGroup(i).getEntityType();
                }
                if (!_bUseWildcards) {
                    if (strToCompare != null && strToCompare.length() < strFilter.length()) {
                        getActionGroup(i).setDisplayFiltered(true);
                    } else if (!strToCompare.substring(0, strFilter.length()).equalsIgnoreCase(strFilter)) {
                        getActionGroup(i).setDisplayFiltered(true);
                    }
                } else {
                    if (!SortFilterInfo.equalsWithWildcards(strFilter, strToCompare, new char[] { '*', '%' }, true)) {
                        getActionGroup(i).setDisplayFiltered(true);
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

    //get the number of items in the list
    /**
     * (non-Javadoc)
     * getObjectCount
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#getObjectCount()
     */
    public int getObjectCount() {
        return getActionGroupCount();
    }

    //get the EANComparable object at (i);
    /**
     * (non-Javadoc)
     * getObject
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#getObject(int)
     */
    public EANComparable getObject(int _i) {
        return getActionGroup(_i);
    }

    // is the object filtered out?
    /**
     * (non-Javadoc)
     * isObjectFiltered
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#isObjectFiltered(int)
     */
    public boolean isObjectFiltered(int _i) {
        return getActionGroup(_i).isDisplayFiltered();
    }

    // set filter on an indexed item
    /**
     * (non-Javadoc)
     * setObjectFiltered
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#setObjectFiltered(int, boolean)
     */
    public void setObjectFiltered(int _i, boolean _b) {
        getActionGroup(_i).setDisplayFiltered(_b);
    }

    private void resetDisplayableActionGroups() {
        for (int i = 0; i < getObjectCount(); i++) {
            getActionGroup(i).setDisplayFiltered(false);
        }
    }

    /**
     * (non-Javadoc)
     * putObject
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#putObject(COM.ibm.eannounce.objects.EANComparable)
     */
    public void putObject(EANComparable _ec) {
        putActionGroup((ActionGroup) _ec);
    }

    /**
     * (non-Javadoc)
     * removeObject
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#removeObject(COM.ibm.eannounce.objects.EANComparable)
     */
    public void removeObject(EANComparable _ec) {
        removeActionGroup((ActionGroup) _ec);
    }

    //}}}

    //{{{ === Accessor methods ===

    /**
     * getEntityType
     *
     * @return
     *  @author David Bigelow
     */
    public String getEntityType() {
        return getParentEntityGroup().getEntityType();
    }

    /**
     * getParentEntityGroup
     *
     * @return
     *  @author David Bigelow
     */
    public EntityGroup getParentEntityGroup() {
        return (EntityGroup) getParent();
    }

    /**
     * getActionGroup
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public ActionGroup getActionGroup(int _i) {
        return (ActionGroup) getMeta(_i);
    }

    /**
     * getActionGroup
     *
     * @param _s
     * @return
     *  @author David Bigelow
     */
    public ActionGroup getActionGroup(String _s) {
        return (ActionGroup) getMeta(_s);
    }

    /**
     * getActionGroup
     *
     * @param _db
     * @param _s
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @return
     *  @author David Bigelow
     */
    public ActionGroup getActionGroup(Database _db, String _s) throws MiddlewareException {
        if (getActionGroup(_s) instanceof ActionGroupImplicator) {
            EANMetaFoundation emfParent = (EANMetaFoundation) getParent();
            Profile prof = (emfParent == null ? getProfile() : null);
            return putActionGroup(new ActionGroup(emfParent, _db, prof, getActionGroup(_s).getActionGroupKey(), getActionGroup(_s).getEntityType()));
        }
        return getActionGroup(_s);
    }

    /**
     * getActionGroup
     *
     * @param _db
     * @param _i
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @return
     *  @author David Bigelow
     */
    public ActionGroup getActionGroup(Database _db, int _i) throws MiddlewareException {
        if (getActionGroup(_i) instanceof ActionGroupImplicator) {
            EANMetaFoundation emfParent = (EANMetaFoundation) getParent();
            Profile prof = (emfParent == null ? getProfile() : null);
            return putActionGroup(new ActionGroup(emfParent, _db, prof, getActionGroup(_i).getActionGroupKey(), getActionGroup(_i).getEntityType()));
        }
        return getActionGroup(_i);
    }

    /**
     * getActionGroupCount
     *
     * @return
     *  @author David Bigelow
     */
    public int getActionGroupCount() {
        return getMetaCount();
    }
    //}}}

    //{{{ === Mutator methods ===

    /**
     * putActionGroup
     *
     * @param _actionGroup
     * @return
     *  @author David Bigelow
     */
    public ActionGroup putActionGroup(ActionGroup _actionGroup) {
        putMeta(_actionGroup);
        return _actionGroup;
    }

    /**
     * removeActionGroup
     *
     * @param _actionGroup
     * @return
     *  @author David Bigelow
     */
    public ActionGroup removeActionGroup(ActionGroup _actionGroup) {
        removeMeta(_actionGroup);
        return _actionGroup;
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

        ActionGroupList agl_db = new ActionGroupList(this.getParentEntityGroup(), _db, this.getProfile());
        // 1) expire all in db
        if (_bExpire) {
            for (int i = 0; i < agl_db.getActionGroupCount(); i++) {
                agl_db.getActionGroup(i).expirePdhMeta(_db);
            }
        } else {
            // A) go through db and expire all actionGroups that are in db, not in current list
            for (int i = 0; i < agl_db.getActionGroupCount(); i++) {
                if (this.getActionGroup(agl_db.getActionGroup(i).getActionGroupKey()) == null) {
                    agl_db.getActionGroup(i).expirePdhMeta(_db);
                }
            }
            // B) go through current list and update all ActionGroups (these will take care of themselves)
            for (int i = 0; i < this.getActionGroupCount(); i++) {
                Stopwatch sw = new Stopwatch();
                sw.start();
                this.getActionGroup(i).updatePdhMeta(_db);
                _db.debug(D.EBUG_SPEW, "ActionGroupList: update of one ActionGroup (" + this.getActionGroup(i).getKey() + ") took:" + sw.finish());
            }
        }
        return true;
    }


    /////////////
    /**
     * Build an ActionGroup minus the Action Items inside it
     */
    private class ActionGroupImplicator extends ActionGroup {
        /**
         * ActionGroupImplicator
         *
         * @param _emf
         * @param _db
         * @param _prof
         * @param _strActionGroupKey
         * @param _strEntityType
         * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
         *  @author David Bigelow
         */
        ActionGroupImplicator(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionGroupKey, String _strEntityType) throws MiddlewareException {
            super(_emf, _prof, _strActionGroupKey);
            setEntityType(_strEntityType);
            try {

                Profile prof = getProfile();

                // Future.. pull structure out of cache if already pulled for this group.
                // Lets get a list of Action Items from the database here and put them into a re
                ReturnStatus returnStatus = new ReturnStatus(-1);
                ResultSet rs = null;
                ReturnDataResultSet rdrs = null;
                
                try {
                    // Retrieve the Action Class and the Action Description.
                    rs = _db.callGBL7002(returnStatus, prof.getOPWGID(), prof.getEnterprise(), _strActionGroupKey, "Group", prof.getReadLanguage().getNLSID(), prof.getValOn(), prof.getEffOn());
                    rdrs = new ReturnDataResultSet(rs);
                } finally {
                    rs.close();
                    rs = null;
                    _db.commit();
                    _db.freeStatement();
                    _db.isPending();
                }
                for (int i = 0; i < rdrs.size(); i++) {
                    _db.debug(D.EBUG_SPEW, "gbl7002 answer is:" + rdrs.getColumn(i, 0) + ":" + rdrs.getColumn(i, 1) + ":" + rdrs.getColumnInt(i, 2) + ":");
                    //putShortDescription(rdrs.getColumnInt(i,2),rdrs.getColumn(i,0));
                    putLongDescription(rdrs.getColumnInt(i, 2), rdrs.getColumn(i, 1));
                }

            } catch (Exception x) {
                System.out.println("ActionGroupImplicator exception: " + x);
            } finally {
                _db.freeStatement();
                _db.isPending();
            }
        }
    }

}
