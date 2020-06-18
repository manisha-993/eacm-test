//
// Copyright (c) 2002, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: MetaAttributeList.java,v $
// Revision 1.16  2010/11/08 16:55:23  wendy
// check for null before rs.close
//
// Revision 1.15  2008/02/01 22:10:08  wendy
// Cleanup RSA warnings
//
// Revision 1.14  2005/03/04 22:40:10  dave
// Jtest
//
// Revision 1.13  2003/04/30 23:11:59  gregg
// remove all attribute display order references
//
// Revision 1.12  2003/04/15 22:05:40  gregg
// removing references to MetaLinkAttribute's DisplayOrder
//
// Revision 1.11  2002/11/25 19:05:13  gregg
// kompyle phicks
//
// Revision 1.10  2002/11/25 18:56:42  gregg
// isOrphan, setOrphan
//
// Revision 1.9  2002/11/20 18:32:15  gregg
// index fix for c_saSortTypes/c_saFilterTypes
//
// Revision 1.8  2002/11/19 23:56:23  gregg
// isOrphan logic introduced denoting if a given att in the list contains ~any~ valid parent
// in the database or not
//
// Revision 1.7  2002/11/18 21:20:24  gregg
// ignore case logic on performFilter
//
// Revision 1.6  2002/11/18 18:28:09  gregg
// allow use of wildcards on performFilter()
//
// Revision 1.5  2002/10/22 17:19:35  gregg
// buildCleanMetaAttribute static method
//
// Revision 1.4  2002/10/01 00:23:31  gregg
// fix in performSort method
//
// Revision 1.3  2002/09/30 23:24:32  gregg
// putMetaAttribute in Constructor -- this helps when creating a list (doh!!)
//
// Revision 1.2  2002/09/30 23:09:45  gregg
// flip-flopped params in constructor
//
// Revision 1.1  2002/09/30 22:32:38  gregg
// initial load
//
//

package COM.ibm.eannounce.objects;

import java.sql.ResultSet;
import java.sql.SQLException;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;

import java.util.Arrays;
//import java.util.Hashtable;

/**
* This Object manages A list of EANMetaAttributes - not necessarily associated w/ any entity
*/
public class MetaAttributeList extends EANMetaEntity implements EANSortableList {

    private SortFilterInfo m_SFInfo = null;

    /**
     * FIELD
     */
    public static String[] SORT_TYPES = { EANMetaAttribute.SORT_BY_LONGDESC, EANMetaAttribute.SORT_BY_SHORTDESC, EANMetaAttribute.SORT_BY_ATTCODE, EANMetaAttribute.SORT_BY_ATTTYPE, EANMetaAttribute.SORT_BY_ATTTYPEMAPPING, EANMetaAttribute.SORT_BY_IS_ORPHAN };
    /**
     * FIELD
     */
    public static final String SORT_ATTS_BY_LONGDESC = SORT_TYPES[0];
    /**
     * FIELD
     */
    public static final String SORT_ATTS_BY_SHORTDESC = SORT_TYPES[1];
    /**
     * FIELD
     */
    public static final String SORT_ATTS_BY_ATTCODE = SORT_TYPES[2];
    /**
     * FIELD
     */
    public static final String SORT_ATTS_BY_ATTTYPE = SORT_TYPES[3];
    /**
     * FIELD
     */
    public static final String SORT_ATTS_BY_ATTTYPEMAPPING = SORT_TYPES[4];
    /**
     * FIELD
     */
    public static final String SORT_ATTS_BY_IS_ORPHAN = SORT_TYPES[5];

    /**
     * FIELD
     */
    public static  String[] SA_SORT_TYPES = SORT_TYPES;
    /**
     * FIELD
     */
    public static final String FILTER_ATTS_BY_LONGDESC = SA_SORT_TYPES[0];
    /**
     * FIELD
     */
    public static final String FILTER_ATTS_BY_SHORTDESC = SA_SORT_TYPES[1];
    /**
     * FIELD
     */
    public static final String FILTER_ATTS_BY_ATTCODE = SA_SORT_TYPES[2];
    /**
     * FIELD
     */
    public static final String FILTER_ATTS_BY_ATTTYPE = SA_SORT_TYPES[3];
    /**
     * FIELD
     */
    public static final String FILTER_ATTS_BY_ATTTYPEMAPPING = SA_SORT_TYPES[4];
    /**
     * FIELD
     */
    public static final String FILTER_ATTS_BY_IS_ORPHAN = SORT_TYPES[5];

    //private Hashtable m_hashOrphans = null;

    /**
    * @serial
    */
    static final long serialVersionUID = 1L;

    /**
     * Main method which performs a simple test of this class
     *
     * @param arg 
     */
    public static void main(String arg[]) {
    }

    /**
     * This constructor will get ALL attributes based on role
     * warning: only for ONE nlsid!!
     *
     * @param _db
     * @param _prof
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public MetaAttributeList(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareRequestException {
        super(null, _prof, "MetaAttributeList");

        m_SFInfo = new SortFilterInfo(EANMetaAttribute.SORT_BY_LONGDESC, true, MetaAttributeList.FILTER_ATTS_BY_LONGDESC, null);

        try {
            ResultSet rs = null;
            ReturnDataResultSet rdrs = null;
            String strEnterprise = getProfile().getEnterprise();
            String strRoleCode = getProfile().getRoleCode();
            int iNlsId = getProfile().getReadLanguage().getNLSID();
            _db.debug(D.EBUG_DETAIL, "gbl7514 params are:" + strEnterprise + ":" + strRoleCode + ":" + iNlsId);
            
            try {
                rs = _db.callGBL7514(new ReturnStatus(-1), strEnterprise, strRoleCode, iNlsId);
                rdrs = new ReturnDataResultSet(rs);
            } finally {
            	if(rs!=null){
            		rs.close();
            		rs = null;
            	}
                _db.freeStatement();
                _db.isPending();
            }
            
            //m_hashOrphans = new Hashtable(rdrs.getRowCount());

            for (int row = 0; row < rdrs.getRowCount(); row++) {

                String strAttCode = rdrs.getColumn(row, 0);
                String strAttType = rdrs.getColumn(row, 1);
                String strShortDesc = rdrs.getColumn(row, 2);
                String strLongDesc = rdrs.getColumn(row, 3);
                String strCap = "N";

                EANMetaAttribute oAtt = null;

                switch (strAttType.charAt(0)) {
                case 'T' :
                case 'I' :
                    //NOTE: cpabilities are in relation to the passed RoleCode, NOT the profile
                    oAtt = new MetaTextAttribute(null, _prof, strAttCode, strAttType, strCap);
                    break;
                case 'L' :
                    oAtt = new MetaLongTextAttribute(null, _prof, strAttCode, strAttType, strCap);
                    break;
                case 'F' :
                    oAtt = new MetaMultiFlagAttribute(null, _prof, strAttCode, strAttType, strCap);
                    break;
                case 'U' :
                    oAtt = new MetaSingleFlagAttribute(null, _prof, strAttCode, strAttType, strCap);
                    break;
                case 'S' :
                    oAtt = new MetaStatusAttribute(null, _prof, strAttCode, strAttType, strCap);
                    break;
                case 'A' :
                    oAtt = new MetaTaskAttribute(null, _prof, strAttCode, strAttType, strCap);
                    break;
                case 'X' :
                    oAtt = new MetaXMLAttribute(null, _prof, strAttCode, strAttType, strCap);
                    break;
                case 'B' :
                    oAtt = new MetaBlobAttribute(null, _prof, strAttCode, strAttType, strCap);
                    break;
                default :
                    break;
                }
                oAtt.putShortDescription(strShortDesc);
                oAtt.putLongDescription(strLongDesc);
                putMetaAttribute(oAtt);
                if (rdrs.getColumn(row, 8).equals("Orphan")) {
                    oAtt.setOrphan(true);
                }
            }
        } finally {
            _db.freeStatement();
            _db.isPending();
        }

    }

    /**
     * putMetaAttribute
     *
     * @param _att
     *  @author David Bigelow
     */
    public void putMetaAttribute(EANMetaAttribute _att) {
        putMeta(_att);
    }

    /**
     * getMetaAttribute
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public EANMetaAttribute getMetaAttribute(int _i) {
        return (EANMetaAttribute) getMeta(_i);
    }

    /**
     * getMetaAttribute
     *
     * @param _s
     * @return
     *  @author David Bigelow
     */
    public EANMetaAttribute getMetaAttribute(String _s) {
        return (EANMetaAttribute) getMeta(_s);
    }

    /**
     * getMetaAttributeCount
     *
     * @return
     *  @author David Bigelow
     */
    public int getMetaAttributeCount() {
        return getMetaCount();
    }

    /**
     * removeMetaAttribute
     *
     * @param _att
     *  @author David Bigelow
     */
    public void removeMetaAttribute(EANMetaAttribute _att) {
        removeMeta(_att);
    }

    /**
     * refreshOrphansList
     *
     * @param _db
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public void refreshOrphansList(Database _db) throws SQLException, MiddlewareException, MiddlewareRequestException {
        try {
            ResultSet rs = null;
            ReturnDataResultSet rdrs = null;
            String strEnterprise = getProfile().getEnterprise();
            String strRoleCode = getProfile().getRoleCode();
            int iNlsId = getProfile().getReadLanguage().getNLSID();
            try {
                rs = _db.callGBL7514(new ReturnStatus(-1), strEnterprise, strRoleCode, iNlsId);
                rdrs = new ReturnDataResultSet(rs);
            } finally {
              	if(rs!=null){
            		rs.close();
            		rs = null;
            	}
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }

            for (int row = 0; row < rdrs.getRowCount(); row++) {

                String strAttCode = rdrs.getColumn(row, 0);
                EANMetaAttribute att = getMetaAttribute(strAttCode);
                if (att != null) {
                    att.setOrphan((rdrs.getColumn(row, 8).equals("Orphan")));
                }

            }
        } finally {
            _db.freeStatement();
            _db.isPending();
        }
    }

    /**
     * (non-Javadoc)
     * getFilterTypesArray
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#getFilterTypesArray()
     */
    public String[] getFilterTypesArray() {
        return SA_SORT_TYPES;
    }

    /**
     * (non-Javadoc)
     * getObject
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#getObject(int)
     */
    public EANComparable getObject(int _i) {
        return getMetaAttribute(_i);
    }

    /**
     * (non-Javadoc)
     * getObjectCount
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#getObjectCount()
     */
    public int getObjectCount() {
        return getMetaAttributeCount();
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
        return !getMetaAttribute(_i).isDisplayableForFilter();
    }

    /**
     * (non-Javadoc)
     * setObjectFiltered
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#setObjectFiltered(int, boolean)
     */
    public void setObjectFiltered(int _i, boolean _b) {
        getMetaAttribute(_i).setDisplayableForFilter(!_b);
    }

    /**
     * (non-Javadoc)
     * performFilter
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#performFilter(boolean)
     */
    public void performFilter(boolean _bUseWildcards) {
        //first go through and set the displayable
        //  - note that it is up to the calling party to check for isDisplayable on MetaRoles
        resetDisplayableObjects();
        if (getSFInfo().getFilter() != null) {
            String strFilter = getSFInfo().getFilter();
            for (int i = 0; i < getMetaAttributeCount(); i++) {
                String strToCompare = null;
                if (getSFInfo().getFilterType().equals(FILTER_ATTS_BY_ATTCODE)) {
                    strToCompare = getMetaAttribute(i).getAttributeCode();
                
                } else if (getSFInfo().getFilterType().equals(FILTER_ATTS_BY_ATTTYPE)) {
                    strToCompare = getMetaAttribute(i).getAttributeType();
                
                } else if (getSFInfo().getFilterType().equals(FILTER_ATTS_BY_ATTTYPEMAPPING)) {
                    strToCompare = getMetaAttribute(i).getAttributeTypeMapping();
                
                } else if (getSFInfo().getFilterType().equals(FILTER_ATTS_BY_SHORTDESC)) {
                    strToCompare = getMetaAttribute(i).getShortDescription();
                
                } else if (getSFInfo().getFilterType().equals(FILTER_ATTS_BY_IS_ORPHAN)) {
                    strToCompare = (getMetaAttribute(i).isOrphan() ? "Yes" : "No");
                
                } else { //default - by long description
                    strToCompare = getMetaAttribute(i).getLongDescription();
                }
                if (!_bUseWildcards) {
                    if (strToCompare.length() < strFilter.length()) {
                        getMetaAttribute(i).setDisplayableForFilter(false);
                    } else if (!strToCompare.substring(0, strFilter.length()).equalsIgnoreCase(strFilter)) {
                        getMetaAttribute(i).setDisplayableForFilter(false);
                    }
                } else {
                    if (!SortFilterInfo.equalsWithWildcards(strFilter, strToCompare, new char[] { '*', '%' }, true)) {
                        getMetaAttribute(i).setDisplayableForFilter(false);
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

    /**
     * (non-Javadoc)
     * putObject
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#putObject(COM.ibm.eannounce.objects.EANComparable)
     */
    public void putObject(EANComparable _ec) {
        putMetaAttribute((EANMetaAttribute) _ec);
    }

    /**
     * (non-Javadoc)
     * removeObject
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#removeObject(COM.ibm.eannounce.objects.EANComparable)
     */
    public void removeObject(EANComparable _ec) {
        removeMetaAttribute((EANMetaAttribute) _ec);
    }

    //non-required for EANSortableList interface
    private void resetDisplayableObjects() {
        for (int i = 0; i < getObjectCount(); i++) {
            setObjectFiltered(i, false);
        }
    }
    //

    /**
     * dump it for now..
     *
     * @return String
     * @param _bBrief 
     */
    public String dump(boolean _bBrief) {
        return toString();
    }

    /**
    * Return the date/time this class was generated
    * @return the date/time this class was generated
    */
    public String getVersion() {
        return "$Id: MetaAttributeList.java,v 1.16 2010/11/08 16:55:23 wendy Exp $";
    }

    ////////////////
    // STATIC METHODS
    ////////////////

    /**
     * This will build the EANMetaAttribute (proper subclass) from database COMPLETELY, finally
     * display order, setCapability, setNavigate, ExcludeFromCopy, etc, make sure to cover everything in here!!!
     *
     * @return EANMetaAttribute
     * @param _db
     * @param _prof
     * @param _egParent
     * @param _strAttCode
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public static final EANMetaAttribute buildCleanMetaAttribute(Database _db, Profile _prof, EntityGroup _egParent, String _strAttCode) throws SQLException, MiddlewareException, MiddlewareRequestException {
        EANMetaAttribute oAtt = null;
        try {
            ResultSet rs = null;
            ReturnDataResultSet rdrs = null;
            String strEnterprise = _prof.getEnterprise();
            String strRoleCode = _prof.getRoleCode();
            int iNlsId = _prof.getReadLanguage().getNLSID();
            
            String strAttType = null;
            String strCap = null;
            String strNow = _db.getDates().getNow();
            
            try {
                rs = _db.callGBL7514(new ReturnStatus(-1), strEnterprise, strRoleCode, iNlsId);
                rdrs = new ReturnDataResultSet(rs);
            } finally {
              	if(rs!=null){
            		rs.close();
            		rs = null;
            	}
                _db.freeStatement();
                _db.isPending();
            }
            
            // 1) get the attType, capability - this is all we need from call to gbl7514
            for (int row = 0; row < rdrs.getRowCount(); row++) {
                String strAttCode = rdrs.getColumn(row, 0);
                _db.debug(D.EBUG_SPEW, "gbl7514 attCode:" + strAttCode);
                if (strAttCode.equalsIgnoreCase(_strAttCode)) {
                    strAttType = rdrs.getColumn(row, 1);
                    strCap = rdrs.getColumn(row, 5);
                    break;
                }
            }

            if (strAttType == null) {
                _db.debug(D.EBUG_WARN, "MetaAttributeList.buildMetaAttribute: attType for " + _strAttCode + " is null!!");
                return null;
            }
            //ok, we can build att...
            switch (strAttType.charAt(0)) {
            case 'T' :
            case 'I' :
                oAtt = new MetaTextAttribute(_egParent, _db, _prof, _strAttCode);
                break;
            case 'L' :
                oAtt = new MetaLongTextAttribute(_egParent, _db, _prof, _strAttCode);
                break;
            case 'F' :
                oAtt = new MetaMultiFlagAttribute(_egParent, _db, _prof, _strAttCode);
                break;
            case 'U' :
                oAtt = new MetaSingleFlagAttribute(_egParent, _db, _prof, _strAttCode);
                break;
            case 'S' :
                oAtt = new MetaStatusAttribute(_egParent, _db, _prof, _strAttCode);
                break;
            case 'A' :
                oAtt = new MetaTaskAttribute(_egParent, _db, _prof, _strAttCode);
                break;
            case 'X' :
                oAtt = new MetaXMLAttribute(_egParent, _db, _prof, _strAttCode);
                break;
            case 'B' :
                oAtt = new MetaBlobAttribute(_egParent, _db, _prof, _strAttCode);
                break;
            default :
                _db.debug(D.EBUG_WARN, "MetaAttributeList.buildCleanMetaAttribute: unknown attribute type - " + strAttType);
                return null;
            }
            if (oAtt != null) {
                oAtt.setCapability(strCap);
            
            } else {
                _db.debug(D.EBUG_WARN, "MetaAttributeList.buildCleanMetaAttribute: oAtt is null!!");
                return null;
            }

            // 2) now get ALL descs for ALL NLSID's...
            _db.debug(D.EBUG_DETAIL, "gbl7511 params are:" + strEnterprise + ":" + _strAttCode + ":" + strAttType);
            rs = _db.callGBL7511(new ReturnStatus(-1), strEnterprise, _strAttCode, strAttType);
            rdrs = new ReturnDataResultSet(rs);
            rs.close();
            rs = null;
            _db.freeStatement();
            _db.isPending();
            for (int row = 0; row < rdrs.getRowCount(); row++) {
                int iNLSID = rdrs.getColumnInt(row, 0);
                String strShortDesc = rdrs.getColumn(row, 1);
                String strLongDesc = rdrs.getColumn(row, 2);
                _db.debug(D.EBUG_SPEW, "gbl7511 answers: nls=" + iNLSID + ", shortDesc=" + strShortDesc + ", longDesc=" + strLongDesc);
                oAtt.putShortDescription(iNLSID, strShortDesc);
                oAtt.putLongDescription(iNLSID, strLongDesc);
            }

            if (_egParent != null) {
                // 3) now get display order
                /*
                rs = _db.callGBL7503(new ReturnStatus(-1)
                	                ,strEnterprise
                					,"Entity/Attribute"
                					,_egParent.getEntityType()
                                    ,_strAttCode
                					,"DisplayOrder"
                					,strNow
                					,strNow);
                rdrs = new ReturnDataResultSet(rs);
                rs.close();rs = null;_db.freeStatement();_db.isPending();
                if(rdrs.getRowCount() > 0) {
                    String strOrder = rdrs.getColumn(0,0);
                	try {
                	    oAtt.setOrder(Integer.parseInt(strOrder));
                	} catch (NumberFormatException nfe) {
                	    _db.debug(D.EBUG_WARN,"MetaAttributeList.getCleanMetaAttribute - display order is not an int - " + strOrder + "!!");   
                	} 
                }
                */
                // 3) now get exclude from copy
                rs = _db.callGBL7503(new ReturnStatus(-1), strEnterprise, "Entity/Attribute", _egParent.getEntityType(), _strAttCode, "Copy", strNow, strNow);
                rdrs = new ReturnDataResultSet(rs);
                rs.close();
                rs = null;
                _db.freeStatement();
                _db.isPending();
                if (rdrs.getRowCount() > 0) {
                    String strExcludeFromCopy = rdrs.getColumn(0, 0);
                    if (strExcludeFromCopy.equalsIgnoreCase("N")) {
                        oAtt.setExcludeFromCopy(true);
                    }
                }
            }
        } finally {
            _db.freeStatement();
            _db.isPending();
        }
        return oAtt;
    }

}
