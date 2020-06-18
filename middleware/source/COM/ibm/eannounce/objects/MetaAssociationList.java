//
// Copyright (c) 2002, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: MetaAssociationList.java,v $
// Revision 1.9  2005/03/04 22:40:10  dave
// Jtest
//
// Revision 1.8  2005/03/04 22:04:04  dave
// Jtest
//
// Revision 1.7  2003/04/24 19:18:58  gregg
// implement EANSortableList
//
// Revision 1.6  2002/12/06 01:00:59  gregg
// commented out updatePdhMeta for now
//
// Revision 1.5  2002/12/05 22:09:23  gregg
// updatePdhMeta methods
//
// Revision 1.4  2002/11/22 22:22:38  gregg
// removed getCache/putCache from constructor
//
// Revision 1.3  2002/11/07 23:08:22  gregg
// we must putCache in constructor
//
// Revision 1.2  2002/11/07 23:01:11  gregg
// caching whole object
//
// Revision 1.1  2002/11/07 22:09:32  gregg
// initial load
//
//
//

package COM.ibm.eannounce.objects;

import java.util.Arrays;
import java.sql.ResultSet;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.Stopwatch;

/**
 *  Manage a list of MetaAssociations
 */
public class MetaAssociationList extends EANMetaEntity implements EANSortableList {

    static final long serialVersionUID = 1L;

    /**
     * FIELD
     */
    protected static final String[] FILTER_TYPES = { EntityGroup.SORT_BY_ENTITYTYPE, EntityGroup.SORT_BY_LONGDESC };
    /**
     * FIELD
     */
    public static final String FILTER_BY_ENTITYTYPE = FILTER_TYPES[0];
    /**
     * FIELD
     */
    public static final String FILTER_BY_LONG_DESCRIPTION = FILTER_TYPES[1];
    /**
     * FIELD
     */
    protected static final String[] SORT_TYPES = { EntityGroup.SORT_BY_ENTITYTYPE, EntityGroup.SORT_BY_LONGDESC };
    /**
     * FIELD
     */
    public static final String SORT_BY_ENTITYTYPE = SORT_TYPES[0];
    /**
     * FIELD
     */
    public static final String SORT_BY_LONG_DESCRIPTION = SORT_TYPES[1];

    private SortFilterInfo m_SFInfo = new SortFilterInfo(EntityGroup.SORT_BY_ENTITYTYPE, true, FILTER_BY_LONG_DESCRIPTION, null);

    /**
     * d
     *
     * @param _emf
     * @param _db
     * @param _prof
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     */
    public MetaAssociationList(EANMetaFoundation _emf, Database _db, Profile _prof) throws MiddlewareRequestException {
        super(_emf, _prof, "MetaAssociationList_" + _prof.getRoleCode());
        try {

            ResultSet rs = null;
            ReturnDataResultSet rdrs = null;
            Stopwatch sw = new Stopwatch();
            String strNow = _db.getDates().getNow();

            try {

                sw.start();

                rs = _db.callGBL7501(new ReturnStatus(-1), getProfile().getEnterprise(), getProfile().getRoleCode(), strNow, strNow);
                rdrs = new ReturnDataResultSet(rs);

            } finally {
                rs.close();
                rs = null;
                _db.freeStatement();
                _db.isPending();
            }

            for (int row = 0; row < rdrs.getRowCount(); row++) {
                String strEntType = rdrs.getColumn(row, 0);
                String strEntClass = rdrs.getColumn(row, 1);
                _db.debug(D.EBUG_SPEW, "gbl7501 answers:" + strEntType + "," + strEntClass);
                if (strEntClass.equals("Assoc")) {
                    putMetaAssociation(new MetaAssociation(this, _db, getProfile(), strEntType));
                }
            }
            _db.debug(D.EBUG_SPEW, "(TESTING) build MetaAssociationList took:" + sw.finish());
        } catch (Exception exc) {
            _db.debug(D.EBUG_ERR, "MetaAssociationList: in constructor:" + exc.toString());
        } finally {
            _db.freeStatement();
            _db.isPending();
        }
        return;
    }

    /**
     * getMetaAssociationCount
     *
     * @return
     *  @author David Bigelow
     */
    public int getMetaAssociationCount() {
        return getMetaCount();
    }

    /**
     * putMetaAssociation
     *
     * @param _ma
     *  @author David Bigelow
     */
    public void putMetaAssociation(MetaAssociation _ma) {
        putMeta(_ma);
    }

    /**
     * removeMetaAssociation
     *
     * @param _ma
     *  @author David Bigelow
     */
    public void removeMetaAssociation(MetaAssociation _ma) {
        removeMeta(_ma);
    }

    /**
     * getMetaAssociation
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public MetaAssociation getMetaAssociation(int _i) {
        return (MetaAssociation) getMeta(_i);
    }

    /**
     * getMetaAssociation
     *
     * @param _strEntityType
     * @return
     *  @author David Bigelow
     */
    public MetaAssociation getMetaAssociation(String _strEntityType) {
        return (MetaAssociation) getMeta(_strEntityType);
    }

    /**
     * Returns a subset of this list composed of all MetaAssociations that have the given entity1type.
     *
     * @return EANList
     * @param _strEntity1Type 
     */
    public EANList getMetaAssociationsWithEntity1Type(String _strEntity1Type) {
        EANList eList = new EANList();
        for (int i = 0; i < getMetaAssociationCount(); i++) {
            MetaAssociation ma = getMetaAssociation(i);
            if (ma.getEntityGroup1() != null) {
                if (ma.getEntityGroup1().getEntityType().equals(_strEntity1Type)) {
                    eList.put(ma);
                }
            }
        }
        return eList;
    }

    /**
     * Returns a subset of this list composed of all MetaAssociations that have the given entity2type.
     *
     * @return EANList
     * @param _strEntity2Type 
     */
    public EANList getMetaAssociationsWithEntity2Type(String _strEntity2Type) {
        EANList eList = new EANList();
        for (int i = 0; i < getMetaAssociationCount(); i++) {
            MetaAssociation ma = getMetaAssociation(i);
            if (ma.getEntityGroup2() != null) {
                if (ma.getEntityGroup2().getEntityType().equals(_strEntity2Type)) {
                    eList.put(ma);
                }
            }
        }
        return eList;
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {
        StringBuffer sb = new StringBuffer("MetaAssociationList" + NEW_LINE + "======================================================");
        sb.append(NEW_LINE + "MetaAssociationCount:" + getMetaAssociationCount());
        for (int i = 0; i < getMetaAssociationCount(); i++) {
            sb.append(NEW_LINE + getMetaAssociation(i).getEntityType());
        }
        return sb.toString();
    }

    /**
     * Return the date/time this class was generated
     * @return the date/time this class was generated
     */
    public String getVersion() {
        return "$Id: MetaAssociationList.java,v 1.9 2005/03/04 22:40:10 dave Exp $";
    }

    ////////
    // EANSortable Interface methods
    ////////

    /**
     * (non-Javadoc)
     * getFilterTypesArray
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#getFilterTypesArray()
     */
    public String[] getFilterTypesArray() {
        return FILTER_TYPES;
    }

    /**
     * (non-Javadoc)
     * getObject
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#getObject(int)
     */
    public EANComparable getObject(int _i) {
        return getMetaAssociation(_i);
    }

    /**
     * (non-Javadoc)
     * getObjectCount
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#getObjectCount()
     */
    public int getObjectCount() {
        return getMetaAssociationCount();
    }

    /**
     * (non-Javadoc)
     * getSFInfo
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#getSFInfo()
     */
    public SortFilterInfo getSFInfo() {
        return m_SFInfo;
    }

    /**
     * (non-Javadoc)
     * getSortTypesArray
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#getSortTypesArray()
     */
    public String[] getSortTypesArray() {
        return SORT_TYPES;
    }

    /**
     * (non-Javadoc)
     * isObjectFiltered
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#isObjectFiltered(int)
     */
    public boolean isObjectFiltered(int _i) {
        return (!isMetaAssociationDisplayableForFilter(_i));
    }

    /**
     * (non-Javadoc)
     * performFilter
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#performFilter(boolean)
     */
    public void performFilter(boolean _bUseWildcards) {
        resetDisplayableObjects();
        if (getSFInfo().getFilter() != null) {
            String strFilter = getSFInfo().getFilter();
            for (int i = 0; i < getObjectCount(); i++) {
                String strToCompare = null;
                if (getSFInfo().getFilterType().equals(FILTER_BY_ENTITYTYPE)) {
                    strToCompare = getMetaAssociation(i).getEntityType();
                
                } else if (getSFInfo().getFilterType().equals(FILTER_BY_LONG_DESCRIPTION)) {
                    strToCompare = getMetaAssociation(i).getLongDescription();
                
                } else {
                    strToCompare = "";
                }
                //
                if (!_bUseWildcards) {
                    if (strToCompare.length() < strFilter.length()) {
                        setObjectFiltered(i, true);
                    } else if (!strToCompare.substring(0, strFilter.length()).equalsIgnoreCase(strFilter)) {
                        setObjectFiltered(i, true);
                    }
                } else {
                    if (!SortFilterInfo.equalsWithWildcards(strFilter, strToCompare, new char[] { '*', '%' }, true)) {
                        setObjectFiltered(i, true);
                    }
                }
                //

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

    /**
     * (non-Javadoc)
     * putObject
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#putObject(COM.ibm.eannounce.objects.EANComparable)
     */
    public void putObject(EANComparable _ec) {
        putMetaAssociation((MetaAssociation) _ec);
    }

    /**
     * (non-Javadoc)
     * removeObject
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#removeObject(COM.ibm.eannounce.objects.EANComparable)
     */
    public void removeObject(EANComparable _ec) {
        removeMetaAssociation((MetaAssociation) _ec);
    }

    /**
     * (non-Javadoc)
     * setObjectFiltered
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#setObjectFiltered(int, boolean)
     */
    public void setObjectFiltered(int _i, boolean _b) {
        setDisplayableForFilter(_i, !_b);
    }

    //non-required for EANSortableList interface
    private void resetDisplayableObjects() {
        for (int i = 0; i < getMetaAssociationCount(); i++) {
            setObjectFiltered(i, false);
        }
    }
    private boolean isMetaAssociationDisplayableForFilter(int _i) {
        MetaAssociation ma = getMetaAssociation(_i);
        return ma.isDisplayableForFilter();
    }
    private void setDisplayableForFilter(int _i, boolean _b) {
        MetaAssociation ma = getMetaAssociation(_i);
        ma.setDisplayableForFilter(_b);
        return;
    }
    //

}
