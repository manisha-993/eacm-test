//
// Copyright (c) 2001-2004, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: EANActionItemEntitiesList.java,v $
// Revision 1.6  2009/03/12 14:59:14  wendy
// Add methods for metaui access
//
// Revision 1.5  2005/03/11 22:21:40  dave
// removing those nasty auto method generations
//
// Revision 1.4  2005/03/03 21:25:16  dave
// NEW_LINE on EAN Foundation
//
// Revision 1.3  2005/02/14 17:18:34  dave
// more jtest fixing
//
// Revision 1.2  2004/10/21 16:49:53  dave
// trying to share compartor
//
// Revision 1.1  2004/01/13 22:49:11  gregg
// moving EANActionItem.EANActionItemEntitiesList class into it's own class because it's rarely used and taking up space
//
//

package COM.ibm.eannounce.objects;

import java.sql.ResultSet;
import java.sql.SQLException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import java.util.Arrays;


/**
* manages the Action/Entity Definition for relevant ActionItems (Extract, Navigate)
*/
public class EANActionItemEntitiesList extends EANMetaEntity implements EANSortableList {

    static final long serialVersionUID = 1L;
      
    private SortFilterInfo m_sfi = null;

    /**
     * FIELD
     */
    protected static String[] c_saFilterTypes = { "Entity", "Link Code", "Link Value" };
    /**
     * FIELD
     */
    public static final String FILTER_BY_ENTITY = c_saFilterTypes[0];
    /**
     * FIELD
     */
    public static final String FILTER_BY_LINKCODE = c_saFilterTypes[1];
    /**
     * FIELD
     */
    public static final String FILTER_BY_LINKVALUE = c_saFilterTypes[2];
    /**
     * FIELD
     */
    protected static String[] c_saSortTypes = c_saFilterTypes;
    /**
     * FIELD
     */
    public static final String SORT_BY_ENTITY = FILTER_BY_ENTITY;
    /**
     * FIELD
     */
    public static final String SORT_BY_LINKCODE = FILTER_BY_LINKCODE;
    /**
     * FIELD
     */
    public static final String SORT_BY_LINKVALUE = FILTER_BY_LINKVALUE;

    /**
     * database constructor
     *
     * @param _profile
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @param _parent
     * @param _db 
     */
    protected EANActionItemEntitiesList(EANActionItem _parent, Database _db, Profile _profile) throws MiddlewareRequestException {
        super(_parent, _profile, _parent.getActionItemKey() + ":EntitiesList");
        try {
            String strNow = _db.getDates().getNow();
            ResultSet rs = null;
            ReturnDataResultSet rdrs = null;
            _db.getDates().getForever();
            try {
                rs = _db.callGBL7509(new ReturnStatus(-1), getProfile().getEnterprise(), "Action/Entity", getActionItemKey(), strNow, strNow);
                rdrs = new ReturnDataResultSet(rs);
            } finally {
                rs.close();
                rs = null;
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }
            for (int row = 0; row < rdrs.getRowCount(); row++) {
                String strLinkType2 = rdrs.getColumn(row, 0);
                String strLinkCode = rdrs.getColumn(row, 1);
                String strLinkValue = rdrs.getColumn(row, 2);
                putObject(new EANActionItemEntity(this, _db, getProfile(), strLinkType2, strLinkCode, strLinkValue));
                //_db.debug(D.EBUG_SPEW,"gbl7509 answers:" + strLinkType2 + ":" + strLinkCode + ":" + strLinkValue);
            }

        } catch (MiddlewareException mExc) {
            _db.debug(D.EBUG_ERR, "EANActionItemEntitiesList constructor:" + mExc.toString());
        } catch (SQLException sqlExc) {
            _db.debug(D.EBUG_ERR, "EANActionItemEntitiesList constructor:" + sqlExc.toString());
        } finally {
            _db.freeStatement();
            _db.isPending();
        }
    }

    /**
     * getNewActionItemEntity
     *
     * @param _eList
     * @param _db
     * @param _strEntity
     * @param _strLinkCode
     * @param _strLinkValue
     * @return
     *  @author David Bigelow
     */
    protected EANActionItemEntity getNewActionItemEntity(EANActionItemEntitiesList _eList, Database _db, String _strEntity, String _strLinkCode, String _strLinkValue) {
        try {
            return new EANActionItemEntitiesList.EANActionItemEntity(_eList, _db, _eList.getProfile(), _strEntity, _strLinkCode, _strLinkValue);
        } catch (MiddlewareRequestException mrExc) {
            System.err.println("EANActionItem.getNewActionItemEntity():" + mrExc.toString());
        }
        return null;
    }

    /**
     * get a new copy w/ new ActionItem's actionItem key
     *
     * @param _db
     * @param _actionItemNew
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @return EANActionItemEntitiesList
     */
    public EANActionItemEntitiesList duplicateForNewActionItem(Database _db, EANActionItem _actionItemNew) throws MiddlewareRequestException {
        //_db.debug(D.EBUG_SPEW,"duplicateForNewActionItem start");
        EANActionItemEntitiesList aieListNew = _actionItemNew.getActionEntitiesList(_db);
        //the list should be empty, but just to be safe...
        aieListNew.resetMeta();
        //_db.debug(D.EBUG_SPEW,"duplicateForNewActionItem object count:" + this.getObjectCount());
        for (int i = 0; i < this.getObjectCount(); i++) {
            EANActionItemEntity aieOld = this.getActionItemEntity(i);
            EANActionItemEntity aieNew = new EANActionItemEntity(aieListNew, _db, _actionItemNew.getProfile(), aieOld.getEntity(), aieOld.getLinkCode(), aieOld.getLinkValue());
            //_db.debug(D.EBUG_SPEW,"duplicateForNewActionItem putting aieNew:" + aieNew.dump(false));
            aieListNew.putObject(aieNew);
        }
        //_db.debug(D.EBUG_SPEW,"duplicateForNewActionItem aieListNew:"  + aieListNew.dump(false));
        return aieListNew;
    }

    //{{{ EANSortableList required methods

    /**
     * (non-Javadoc)
     * performFilter
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#performFilter(boolean)
     */
    public void performFilter(boolean _bUseWildcards) {
        //first go through and set the displayable
        //  - note that it is up to the calling party to check for isDisplayable on MetaRoles
        resetDisplayableActionItemEntities();
        if (getSFInfo().getFilter() != null) {
            String strFilter = getSFInfo().getFilter();
            for (int i = 0; i < getActionItemEntityCount(); i++) {
                String strToCompare = "";
                if (getSFInfo().getFilterType().equals(FILTER_BY_ENTITY)) {
                    strToCompare = getActionItemEntity(i).getEntity();
                
                } else if (getSFInfo().getFilterType().equals(FILTER_BY_LINKCODE)) {
                    strToCompare = getActionItemEntity(i).getLinkCode();
                
                } else if (getSFInfo().getFilterType().equals(FILTER_BY_LINKVALUE)) {
                    strToCompare = getActionItemEntity(i).getLinkValue();
                }
                if (!_bUseWildcards) {
                    if (strToCompare.length() < strFilter.length()) {
                        getActionItemEntity(i).setFiltered(true);
                    } else if (!strToCompare.substring(0, strFilter.length()).equalsIgnoreCase(strFilter)) {
                        getActionItemEntity(i).setFiltered(true);
                    }
                } else {
                    if (!SortFilterInfo.equalsWithWildcards(strFilter, strToCompare, new char[] { '*', '%' }, true)) {
                        getActionItemEntity(i).setFiltered(true);
                    }
                }
            }
        }
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
        return getActionItemEntityCount();
    }

    /**
     * (non-Javadoc)
     * getObject
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#getObject(int)
     */
    public EANComparable getObject(int _i) {
        return getActionItemEntity(_i);
    }

    /**
     * (non-Javadoc)
     * isObjectFiltered
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#isObjectFiltered(int)
     */
    public boolean isObjectFiltered(int _i) {
        return getActionItemEntity(_i).isFiltered();
    }

    /**
     * (non-Javadoc)
     * setObjectFiltered
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#setObjectFiltered(int, boolean)
     */
    public void setObjectFiltered(int _i, boolean _b) {
        getActionItemEntity(_i).setFiltered(_b);
    }

    private void resetDisplayableActionItemEntities() {
        for (int i = 0; i < getActionItemEntityCount(); i++) {
            getActionItemEntity(i).setFiltered(false);
        }
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
            m_sfi = new SortFilterInfo(EANActionItemEntitiesList.SORT_BY_LINKVALUE, true, EANActionItemEntitiesList.FILTER_BY_LINKVALUE, null);
        }
        return m_sfi;
    }
    /**
    * rearrange the list so that it is sorted alphabetically by the specified type
    */
    public void performSort() {
        EANActionItemEntity[] aA = new EANActionItemEntity[getActionItemEntityCount()];
        EANComparator ec = new EANComparator(getSFInfo().isAscending());
        for (int i = 0; i < getActionItemEntityCount(); i++) {
            EANActionItemEntity a = getActionItemEntity(i);
            a.setCompareField(getSFInfo().getSortType());
            aA[i] = a;
        }
        Arrays.sort(aA, ec);
        resetMeta();
        for (int i = 0; i < aA.length; i++) {
            EANActionItemEntity a = aA[i];
            putMeta(a);
        }
        return;
    }

    //}}}

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _b) {
        StringBuffer sb = new StringBuffer();
        sb.append(NEW_LINE  + "getKey():" + getKey());
        sb.append(NEW_LINE  + "getActionItemKey():" + getActionItemKey());
        sb.append(NEW_LINE  + "#of items:" + getObjectCount());
        if (!_b) {
            for (int i = 0; i < getObjectCount(); i++) {
                sb.append(getActionItemEntity(i).dump(_b));
            }
        }
        return sb.toString();
    }

    // === Accessor methods ===
    /**
     * getActionItemEntity
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public EANActionItemEntity getActionItemEntity(int _i) {
        return (EANActionItemEntity) getMeta(_i);
    }

    /**
     * getActionItemEntity
     *
     * @param _s
     * @return
     *  @author David Bigelow
     */
    public EANActionItemEntity getActionItemEntity(String _s) {
        return (EANActionItemEntity) getMeta(_s);
    }

    /**
     * getActionItemEntityCount
     *
     * @return
     *  @author David Bigelow
     */
    public int getActionItemEntityCount() {
        return getMetaCount();
    }

    // === Mutator methods ===

    /**
     * (non-Javadoc)
     * putObject
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#putObject(COM.ibm.eannounce.objects.EANComparable)
     */
    public void putObject(EANComparable _ec) {
        putMeta((EANActionItemEntity) _ec);
    }

    /**
     * (non-Javadoc)
     * removeObject
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#removeObject(COM.ibm.eannounce.objects.EANComparable)
     */
    public void removeObject(EANComparable _ec) {
        removeMeta((EANActionItemEntity) _ec);
    }

    /**
     * getActionItemKey
     *
     * @return
     *  @author David Bigelow
     */
    public String getActionItemKey() {
        EANActionItem actionItem = (EANActionItem) getParent();
        return actionItem.getActionItemKey();
    }

    /**
     * updatePdhMeta
     *
     * @param _db
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @return
     *  @author David Bigelow
     */
    public boolean updatePdhMeta(Database _db) throws MiddlewareException {
        return updatePdhMeta(_db, false);
    }

    /**
     * expirePdhMeta
     *
     * @param _db
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @return
     *  @author David Bigelow
     */
    public boolean expirePdhMeta(Database _db) throws MiddlewareException {
        return updatePdhMeta(_db, true);
    }

    /**
     * updatePdhMeta
     *
     * @param _db
     * @param _bExpire
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @return
     *  @author David Bigelow
     */
    public boolean updatePdhMeta(Database _db, boolean _bExpire) throws MiddlewareException {
        try {
            EANActionItem actionItemParent = (EANActionItem) getParent();
            boolean bIsInDatabase = actionItemParent.isMetaDefinedInPdh(_db);
            //grab the existing data IN THE PDH
            EANActionItemEntitiesList eList_db = new EANActionItemEntitiesList((EANActionItem) getParent(), _db, getProfile());
            //expire - go through all meta in db and expire
            if (_bExpire && bIsInDatabase) {
                for (int i = 0; i < eList_db.getActionItemEntityCount(); i++) {
                    eList_db.getActionItemEntity(i).expirePdhMeta(_db);
                }
            } else if (!bIsInDatabase) { //insert all new
                for (int i = 0; i < getActionItemEntityCount(); i++) {
                    getActionItemEntity(i).updatePdhMeta(_db);
                }
            } else { //compare to database and expire/update accordingly

                // 1) go through and expire any records that are in db, !in current object
                for (int i = 0; i < eList_db.getActionItemEntityCount(); i++) {
                    EANActionItemEntity aiEnt_db = eList_db.getActionItemEntity(i);
                    if (this.getActionItemEntity(aiEnt_db.getKey()) == null) {
                        aiEnt_db.expirePdhMeta(_db);
                    }
                }
                // 2) go through and update any records that are !in db, in current object
                for (int i = 0; i < this.getActionItemEntityCount(); i++) {
                    EANActionItemEntity aiEnt_this = this.getActionItemEntity(i);
                    if (eList_db.getActionItemEntity(aiEnt_this.getKey()) == null) {
                        aiEnt_this.updatePdhMeta(_db);
                    }
                }
            }

        } finally {
            _db.freeStatement();
            _db.isPending();
        }
        return false;
    }

    /**
    * manage one Action/Entity record for an EANActionItem and enable sort/filter on these guys
    * (i.e. one row in the MetaLinkAttr table where l.t. == "Action/Entity" && l.t.1 == actionItemKey)
    */
    public class EANActionItemEntity extends EANMetaFoundation implements EANComparable {

        /**
         * FIELD
         */
        protected final String[] c_saSortTypes = { "Entity", "Link Code", "Link Value" };

        /**
         * FIELD
         */
        public final String SORT_BY_ENTITY = c_saSortTypes[0];
        /**
         * FIELD
         */
        public final String SORT_BY_LINKCODE = c_saSortTypes[1];
        /**
         * FIELD
         */
        public final String SORT_BY_LINKVALUE = c_saSortTypes[2];
        private String m_strSortType = SORT_BY_ENTITY; //default

        private String m_strEntity = null;
        private String m_strLinkCode = null;
        private String m_strLinkValue = null;
        private boolean m_bFiltered = false;
        private final String LINKTYPE = "Action/Entity";

        /**
         * EANActionItemEntity
         *
         * @param _parent
         * @param _db
         * @param _profile
         * @param _strEntity
         * @param _strLinkCode
         * @param _strLinkValue
         * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
         *  @author David Bigelow
         */
        protected EANActionItemEntity(EANActionItemEntitiesList _parent, Database _db, Profile _profile, String _strEntity, String _strLinkCode, String _strLinkValue) throws MiddlewareRequestException {
            super(_parent, _profile, _parent.getActionItemKey() + ":" + _strEntity + ":" + _strLinkCode + ":" + _strLinkValue);
            //_db.debug(D.EBUG_SPEW,"EANActionItemEntity constructor: _strEntity=\"" + _strEntity + "\", _strLinkCode=\"" + _strLinkCode + "\", _strLinkValue=\"" + _strLinkValue + "\"");
            setEntity(_strEntity);
            setLinkCode(_strLinkCode);
            setLinkValue(_strLinkValue);
            //_db.debug(D.EBUG_SPEW,"EANActionItemEntity constructor: getEntity()=\"" + getEntity() + "\", getLinkCode()=\"" + getLinkCode() + "\", getLinkValue()=\"" + getLinkValue() + "\"");
        }

        /**
         * setEntity
         *
         * @param _s
         *  @author David Bigelow
         */
        public void setEntity(String _s) {
            m_strEntity = _s;
        }

        /**
         * setLinkCode
         *
         * @param _s
         *  @author David Bigelow
         */
        public void setLinkCode(String _s) {
            m_strLinkCode = _s;
        }

        /**
         * setLinkValue
         *
         * @param _s
         *  @author David Bigelow
         */
        public void setLinkValue(String _s) {
            m_strLinkValue = _s;
        }

        /**
         * getEntity
         *
         * @return
         *  @author David Bigelow
         */
        public String getEntity() {
            return m_strEntity;
        }

        /**
         * getLinkCode
         *
         * @return
         *  @author David Bigelow
         */
        public String getLinkCode() {
            return m_strLinkCode;
        }

        /**
         * getLinkValue
         *
         * @return
         *  @author David Bigelow
         */
        public String getLinkValue() {
            return m_strLinkValue;
        }

        /**
         * (non-Javadoc)
         * setCompareField
         *
         * @see COM.ibm.eannounce.objects.EANComparable#setCompareField(java.lang.String)
         */
        public void setCompareField(String _s) {
            m_strSortType = _s;
        }

        /**
         * (non-Javadoc)
         * getCompareField
         *
         * @see COM.ibm.eannounce.objects.EANComparable#getCompareField()
         */
        public String getCompareField() {
            return m_strSortType;
        }

        /**
         * getActionItemKey
         *
         * @return
         *  @author David Bigelow
         */
        public String getActionItemKey() {
            EANActionItemEntitiesList eList = (EANActionItemEntitiesList) getParent();
            return eList.getActionItemKey();
        }

        /**
         * set whether or not this ActionItem will be displayed in an EANActionItemEntitiesList (true means not displayed)
         *
         * @param _b 
         */
        public void setFiltered(boolean _b) {
            m_bFiltered = _b;
        }

        /**
         * get whether or not this ActionItem will be displayed in an EANActionItemEntitiesList (true means not displayed)
         *
         * @return boolean
         */
        public boolean isFiltered() {
            return m_bFiltered;
        }

        /**
         * get the sort key required by EANComparable interface
         *
         * @return String
         */
        public String toCompareString() {
            if (getCompareField().equals(SORT_BY_ENTITY)) {
                return getEntity();
            
            } else if (getCompareField().equals(SORT_BY_LINKCODE)) {
                return getLinkCode();
            
            } else if (getCompareField().equals(SORT_BY_LINKVALUE)) {
                return getLinkValue();
            }
            //this shouldn't occur
            return getKey();
        }

        /**
         * (non-Javadoc)
         * dump
         *
         * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
         */
        public String dump(boolean _b) {
            StringBuffer sb = new StringBuffer();
            sb.append(NEW_LINE  + "getKey():" + getKey());
            sb.append(NEW_LINE  + "getActionItemKey():" + getActionItemKey());
            sb.append(NEW_LINE  + "getEntity():" + getEntity());
            sb.append(NEW_LINE  + "getLinkCode():" + getLinkCode());
            sb.append(NEW_LINE  + "getLinkValue():" + getLinkValue());
            sb.append(NEW_LINE  + "isFiltered():" + isFiltered());
            sb.append(NEW_LINE  + "toCompareString():" + toCompareString());
            return sb.toString();
        }

        /**
         * These are equal if lt1,lt2,lc,lv are ALL equal.
         *
         * @return boolean
         * @param _e 
         */
        public boolean equals(EANActionItemEntity _e) {
            return getKey().equals(_e.getKey());
        }

        /**
         * expirePdhMeta
         *
         * @param _db
         * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
         * @return
         *  @author David Bigelow
         */
        public boolean expirePdhMeta(Database _db) throws MiddlewareException {
            return updatePdhMeta(_db, true);
        }

        /**
         * updatePdhMeta
         *
         * @param _db
         * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
         * @return
         *  @author David Bigelow
         */
        public boolean updatePdhMeta(Database _db) throws MiddlewareException {
            return updatePdhMeta(_db, false);
        }

        
        /*********
         * meta ui must update action
         */
        public void updateAction(String strEntity, String strLinkCode, String strLinkValue, String actionkey){
        	setEntity(strEntity);
			setLinkCode(strLinkCode);
			setLinkValue(strLinkValue);
			setKey(actionkey + ":" + strEntity + ":" + strLinkCode + ":" + strLinkValue);        	
        }            
        private boolean updatePdhMeta(Database _db, boolean _bExpire) throws MiddlewareException {
            String strNow = _db.getDates().getNow();
            String strForever = _db.getDates().getForever();
            String strValFrom = strNow;
            String strValTo = (_bExpire ? strNow : strForever);
            new MetaLinkAttrRow(getProfile(), LINKTYPE, getActionItemKey(), getEntity(), getLinkCode(), getLinkValue(), strValFrom, strValTo, strValFrom, strValTo, 2).updatePdh(_db);
            return true;
        }

    } //end EANActionItemEntity class
    //}}}
} //end EANActionItemEntitiesList class
//}}}
