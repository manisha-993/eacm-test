//
// Copyright (c) 2003, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: FilterItem.java,v $
// Revision 1.31  2005/03/11 22:42:53  dave
// removing some auto genned stuff
//
// Revision 1.30  2005/03/03 21:25:17  dave
// NEW_LINE on EAN Foundation
//
// Revision 1.29  2005/03/03 18:36:52  dave
// more Jtest
//
// Revision 1.28  2004/06/18 17:24:06  joan
// work on edit relator
//
// Revision 1.27  2003/08/18 21:05:09  dave
// Adding  the sequencing chain to the islocked to not
// induced each cell for being locked in the islocked of
// entityItem (kludge)
//
// Revision 1.26  2003/08/14 18:26:08  gregg
// refresh() method
//
// Revision 1.25  2003/05/27 17:20:32  gregg
// fix GTE/LTE
//
// Revision 1.24  2003/05/01 20:20:38  gregg
// fix for FB50484
//
// Revision 1.23  2003/03/10 17:12:24  gregg
// implement EANSimpleTableTemplate, EANTableRowTemplate
//
// Revision 1.22  2003/02/27 19:44:24  gregg
// multi-logic for SimplePicklistAttribute
//
// Revision 1.21  2003/02/27 19:16:08  gregg
// remove some debug stmts
//
// Revision 1.20  2003/02/26 23:32:59  gregg
// null ptr fix
//
// Revision 1.19  2003/02/26 23:12:52  gregg
// compile fix
//
// Revision 1.18  2003/02/26 23:03:18  gregg
// adjusted object to use Simple_XXX_Attributes for all properties...
//
// Revision 1.17  2003/02/26 22:06:34  gregg
// uncaught Exception for compile
//
// Revision 1.16  2003/02/26 21:56:30  gregg
// New SimpleTextAttribute/SimplePicklistAttribute classes + use these in Rendering RowSelectableTable for FilterGroup/FilterItem
//
// Revision 1.15  2003/02/24 23:03:29  gregg
// getting there...
//
// Revision 1.14  2003/02/24 19:46:40  gregg
// more RowSelectableTable
//
// Revision 1.13  2003/02/20 23:25:47  gregg
// some more logic to render as EANTableWrapper
//
// Revision 1.12  2003/02/19 20:12:20  gregg
// some Exception catching/throwing to enable compilation
//
// Revision 1.11  2003/02/19 19:56:41  gregg
// logic for EANTableWrapper, EANAddressable interfaces
//
// Revision 1.10  2003/02/18 20:24:27  gregg
// setFilterKey in constructor
//
// Revision 1.9  2003/02/18 18:45:26  gregg
// including stubs for EANAddressable methods + getKey() now getFilterKey()
//
// Revision 1.8  2003/02/18 18:33:28  gregg
// remove keyDescription -- we already have getLongDescription/getShortDescription spec. by EANMeta
//
// Revision 1.7  2003/02/18 18:29:56  gregg
// getKeyDescription, setKeyDescription methods per Tony's request
//
// Revision 1.6  2003/02/18 00:05:55  gregg
// first shot at applyFilter logic for RowSelectableTable
//
// Revision 1.5  2003/02/17 21:42:44  gregg
// toString method
//
// Revision 1.4  2003/02/14 23:55:14  gregg
// compress FilterList into FilterGroup (i.e. Group now spans attributes)
//
// Revision 1.3  2003/02/12 23:37:00  gregg
// new Class FilterList + other changes to logic for FilterGroup, FilterItem
//
// Revision 1.2  2003/02/12 19:01:42  gregg
// some cleanup/comments,  NEW_LINE for dumping html
//
// Revision 1.1  2003/02/12 01:06:35  gregg
// initial load
//
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;

/**
 * Represents one filter on a fixed Attribute. There may be 0-n of these w/in a Filter Group.
 */
public class FilterItem extends EANMetaFoundation implements EANAddressable, EANTableRowTemplate {

    /**
    * @serial
    */
    static final long serialVersionUID = 1L;

    //some condition constants
    /**
     * FIELD
     */
    public static final int LIKE_COND = 0; // Like
    /**
     * FIELD
     */
    public static final int NOT_LIKE_COND = 1; // Not Like
    /**
     * FIELD
     */
    public static final int CONTAINS_COND = 2; // Contains
    /**
     * FIELD
     */
    public static final int NOT_CONTAINS_COND = 3; // Does not Contain
    /**
     * FIELD
     */
    public static final int EQUALS_COND = 4; // Equals
    /**
     * FIELD
     */
    public static final int NOT_EQUALS_COND = 5; // Does not Equal
    /**
     * FIELD
     */
    public static final int LTE_COND = 6; // Less than or Equal to
    /**
     * FIELD
     */
    public static final int GTE_COND = 7; // Greater than or Equal to

    private static int[] c_conditionsArray = new int[] { LIKE_COND, NOT_LIKE_COND, CONTAINS_COND, NOT_CONTAINS_COND, EQUALS_COND, NOT_EQUALS_COND, LTE_COND, GTE_COND };

    private SimplePicklistAttribute m_spaFilterKey = null;
    private SimplePicklistAttribute m_spaCondition = null;
    private SimpleTextAttribute m_staValue = null;
  
    /**
     * FilterItem
     *
     * @param _fg
     * @param _prof
     * @param _strFilterKey
     * @param _iCondition
     * @param _strValue
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public FilterItem(FilterGroup _fg, Profile _prof, String _strFilterKey, int _iCondition, String _strValue) throws MiddlewareRequestException {
        super(_fg, _prof, "FilterItem:" + _fg.getNextFilterKeyID());
        //System.err.println("GB - new FilterItem(...," + _strFilterKey + "," + _iCondition + "," + _strValue + ")");
        //build attributes
        m_spaFilterKey = new SimplePicklistAttribute(getFilterGroup().getColumn(FilterGroup.FILTERKEY_ATTCODE), getProfile(), FilterGroup.FILTERKEY_ATTCODE, genFilterKeyPicklist(_strFilterKey), false);
        m_spaCondition = new SimplePicklistAttribute(getFilterGroup().getColumn(FilterGroup.FILTERCONDITION_ATTCODE), getProfile(), FilterGroup.FILTERCONDITION_ATTCODE, genConditionsPicklist(_iCondition), false);
        m_staValue = new SimpleTextAttribute(getFilterGroup().getColumn(FilterGroup.FILTERVALUE_ATTCODE), getProfile(), FilterGroup.FILTERVALUE_ATTCODE, _strValue);
        setFilterKey(_strFilterKey);
        setCondition(_iCondition);
        setValue(_strValue);
        //System.err.println("GB - exiting FilterItem Constructor: getFilterKey=" + getFilterKey() + ":getCondition()=" + getCondition() + ":getValue=" + getValue());
    }

    /**
     * main
     *
     * @param _args
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    protected static void main(String[] _args) throws MiddlewareRequestException {
    }

    /**
     * Refresh the possible Filter Keys. i.e. Sync w/ parent FilterGorup.
     *
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    protected void refresh() throws MiddlewareRequestException {
        m_spaFilterKey = new SimplePicklistAttribute(getFilterGroup().getColumn(FilterGroup.FILTERKEY_ATTCODE), getProfile(), FilterGroup.FILTERKEY_ATTCODE, genFilterKeyPicklist(getFilterKey()), false);
    }

    /**
     * All properties equals?
     *
     * @param _fi
     * @return boolean
     */
    public boolean equals(FilterItem _fi) {
        return this.getFilterKey().equals(_fi.getFilterKey()) && this.getCondition() == _fi.getCondition() && this.getValue().equals(_fi.getValue());
    }

    ///// MUTATORS ///////

    /**
     * setFilterKey
     *
     * @param _s
     *  @author David Bigelow
     */
    public void setFilterKey(String _s) {
        if (getFilterKeyAtt().getFirstSelectedKey().equals(_s)) {
            return;
        }
        getFilterKeyAtt().setSelectedKey(_s);
    }

    /**
     * setCondition
     *
     * @param _i
     *  @author David Bigelow
     */
    public void setCondition(int _i) {
        if (getConditionAtt().getFirstSelectedKey().equals(String.valueOf(_i))) {
            return;
        }
        getConditionAtt().setSelectedKey(String.valueOf(_i));
    }

    /**
     * setValue
     *
     * @param _s
     *  @author David Bigelow
     */
    public void setValue(String _s) {
        if (getValueAtt().getValue().equals(_s)) {
            return;
        }
        getValueAtt().putValue(_s);
    }

    ///// ACCESSORS
    /**
     * getFilterKey
     *
     * @return
     *  @author David Bigelow
     */
    public String getFilterKey() {
        return getFilterKeyAtt().getFirstSelectedKey();
    }

    /**
     * getCondition
     *
     * @return
     *  @author David Bigelow
     */
    public int getCondition() {
        try {
            return Integer.parseInt(getConditionAtt().getFirstSelectedKey());
        } catch (NumberFormatException exc) {
            exc.getMessage();
        }
        return -1;
    }

    /**
     * getValue
     *
     * @return
     *  @author David Bigelow
     */
    public String getValue() {
        return getValueAtt().getValue();
    }

    private SimplePicklistAttribute getFilterKeyAtt() {
        return m_spaFilterKey;
    }

    private SimplePicklistAttribute getConditionAtt() {
        return m_spaCondition;
    }

    private SimpleTextAttribute getValueAtt() {
        return m_staValue;
    }

    ///////////////
    /**
     * Evaluate the indicated String using this items filter criteria
     * @param _s the String to evaluate
     * @param _bCaseSensitive do we care about case?
     * @return true if it passes (i.e. should be displayed - NOT filtered), false if this String should be filtered
     */
    public boolean evaluate(String _s, boolean _bCaseSensitive) {
        String strValue = null;

        if (!_bCaseSensitive) {
            _s = _s.toUpperCase();
        }
        strValue = (_bCaseSensitive ? getValue() : getValue().toUpperCase());
        switch (getCondition()) {
        case LIKE_COND :
            return _s.startsWith(strValue);
        case NOT_LIKE_COND :
            return !_s.startsWith(strValue);
        case CONTAINS_COND :
            return _s.indexOf(strValue) != -1;
        case NOT_CONTAINS_COND :
            return _s.indexOf(strValue) == -1;
        case EQUALS_COND :
            return _s.equals(strValue);
        case NOT_EQUALS_COND :
            return !_s.equals(strValue);
        case LTE_COND :
            return _s.compareTo(strValue) <= 0;
        case GTE_COND :
            return _s.compareTo(strValue) >= 0;
        default :
            break;
        }
        return false;
    }

    /**
     * getConditionsArray
     *
     * @return
     *  @author David Bigelow
     */
    protected static final int[] getConditionsArray() {
        return c_conditionsArray;
    }

    /**
     * We can retain NLS Sensitivity for Condition Descriptions if we want to later 'coz we have the profile
     */
    private String getConditionDescription(int _iCondition) {
        switch (_iCondition) {
        case LIKE_COND :
            return "Like";
        case NOT_LIKE_COND :
            return "Not Like";
        case CONTAINS_COND :
            return "Contains";
        case NOT_CONTAINS_COND :
            return "Does not contain";
        case EQUALS_COND :
            return "Equals";
        case NOT_EQUALS_COND :
            return "Does not equal";
        case LTE_COND :
            return "Less than or equal to";
        case GTE_COND :
            return "Greater than or equal to";
        default :
            break;
        }
        return _iCondition + " is invalid!!";
    }

    ///////////////
    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {
        StringBuffer sb = new StringBuffer("FilterItem:" + getKey() + NEW_LINE);
        sb.append("getFilterKey():" + getFilterKey() + NEW_LINE);
        sb.append("getCondition():" + getCondition() + NEW_LINE);
        sb.append("getValue():" + getValue() + NEW_LINE);
        return sb.toString();
    }

    /**
     * Report all fields filled out correctly
     *
     * @return boolean
     */
    public boolean isComplete() {
        if (getFilterKey() == null || getFilterKey().equals("")) {
            return false;
        }
        if (getValue() == null || getValue().equals("")) {
            return false;
        }
        if (!isValidCondition(getCondition())) {
            return false;
        }
        return true;
    }

    /**
     * is the Condition valid?
     */
    private boolean isValidCondition(int _i) {
        return ((_i >= 0) && (_i <= 7));
    }

    private FilterGroup getFilterGroup() {
        return (FilterGroup) getParent();
    }

    // generate picklist list representing possible filter keys -- used to generate SimplePicklistAttribute picklist
    private EANList genFilterKeyPicklist(String _strFilterKey) throws MiddlewareRequestException {
        EANList elOGKeys = getFilterGroup().getFilterKeyList();
        EANList elPicklist = new EANList();
        //dont get burned -- we must copy off all of these things in list + list!!!
        for (int i = 0; i < elOGKeys.size(); i++) {
            MetaTag mtOG = (MetaTag) elOGKeys.getAt(i);
            MetaTag mtKey = new MetaTag(null, getProfile(), mtOG.getKey());
            mtKey.putLongDescription(mtOG.getLongDescription());
            mtKey.putShortDescription(mtOG.getShortDescription());
            if (mtKey.getKey().equals(_strFilterKey)) {
                mtKey.setSelected(true);
            }
            elPicklist.put(mtKey);
        }
        return elPicklist;
    }

    // generate picklist list representing possible filter conditions -- used to generate SimplePicklistAttribute picklist
    private EANList genConditionsPicklist(int _iCondition) throws MiddlewareRequestException {
        EANList elPicklist = new EANList();
        for (int i = 0; i < getConditionsArray().length; i++) {
            MetaTag mtKey = new MetaTag(null, getProfile(), String.valueOf(getConditionsArray()[i]));
            mtKey.putLongDescription(getConditionDescription(getConditionsArray()[i]));
            mtKey.putShortDescription(getConditionDescription(getConditionsArray()[i]));
            if (getConditionsArray()[i] == _iCondition) {
                mtKey.setSelected(true);
            }
            elPicklist.put(mtKey);
        }
        return elPicklist;
    }

    ////////////////////////////////////////
    /// EANAddressable interface methods ///
    ////////////////////////////////////////

    // get Value of cell w/ key _s -- e.g. value @ this row w/ given columnKey
    /**
     * (non-Javadoc)
     * get
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#get(java.lang.String, boolean)
     */
    public Object get(String _s, boolean _b) {
        if (_s.equals(FilterGroup.FILTERKEY_ATTCODE)) {
            MetaTag mt = getFilterKeyAtt().getFirstSelected();
            if (mt != null) {
                return mt.getLongDescription();
            }
        }
        if (_s.equals(FilterGroup.FILTERCONDITION_ATTCODE)) {
            MetaTag mt = getConditionAtt().getFirstSelected();
            if (mt != null) {
                return mt.getLongDescription();
            }
        }
        if (_s.equals(FilterGroup.FILTERVALUE_ATTCODE)) {
            return getValue();
        }
        return ("");
    }

    // get our Simple_XXX_Attribute
    /**
     * (non-Javadoc)
     * getEANObject
     *
     * @see COM.ibm.eannounce.objects.EANTableRowTemplate#getEANObject(java.lang.String)
     */
    public EANFoundation getEANObject(String _strKey) {
        if (_strKey.equals(FilterGroup.FILTERKEY_ATTCODE)) {
            return getFilterKeyAtt();
        }
        if (_strKey.equals(FilterGroup.FILTERCONDITION_ATTCODE)) {
            return getConditionAtt();
        }
        if (_strKey.equals(FilterGroup.FILTERVALUE_ATTCODE)) {
            return getValueAtt();
        }
        return null;
    }

    /**
     * (non-Javadoc)
     * getKey
     *
     * @see COM.ibm.eannounce.objects.EANObject#getKey()
     */
    public String getKey() {
        return super.getKey();
    }

    // put value in "attribute"
    /**
     * (non-Javadoc)
     * put
     *
     * @see COM.ibm.eannounce.objects.EANTableRowTemplate#put(java.lang.String, java.lang.Object)
     */
    public boolean put(String _s, Object _o) throws EANBusinessRuleException {
        if (_s.equals(FilterGroup.FILTERKEY_ATTCODE)) {
            getFilterKeyAtt().setPicklist(((SimplePicklistAttribute) _o).getPicklist());
            return true;
        }
        if (_s.equals(FilterGroup.FILTERVALUE_ATTCODE)) {
            setValue(_o.toString());
            return true;
        }
        if (_s.equals(FilterGroup.FILTERCONDITION_ATTCODE)) {
            getConditionAtt().setPicklist(((SimplePicklistAttribute) _o).getPicklist());
            return true;
        }
        return false;
    }

    //*** irrelevant ones ***///

    // we're no help here
    /**
     * (non-Javadoc)
     * getHelp
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#getHelp(java.lang.String)
     */
    public String getHelp(String _str) {
        return null;
    }
    // dont need
    /**
     * (non-Javadoc)
     * getLockGroup
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#getLockGroup(java.lang.String)
     */
    public LockGroup getLockGroup(String _s) {
        return null;
    }
    // nein
    /**
     * (non-Javadoc)
     * hasLock
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#hasLock(java.lang.String, COM.ibm.eannounce.objects.EntityItem, COM.ibm.opicmpdh.middleware.Profile)
     */
    public boolean hasLock(String _str, EntityItem _lockOwnerEI, Profile _prof) {
        return true;
    }
    // uh-uh
    /**
     * (non-Javadoc)
     * isEditable
     *
     * @see COM.ibm.eannounce.objects.EANTableRowTemplate#isEditable(java.lang.String)
     */
    public boolean isEditable(String _s) {
        return true;
    }
    //no
    /**
     * (non-Javadoc)
     * isLocked
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#isLocked(java.lang.String, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Database, COM.ibm.eannounce.objects.LockList, COM.ibm.opicmpdh.middleware.Profile, COM.ibm.eannounce.objects.EntityItem, int, java.lang.String, boolean)
     */
    public boolean isLocked(String _s, RemoteDatabaseInterface _rdi, Database _db, LockList _ll, Profile _prof, EntityItem _lockOwnerEI, int _iLockType, String _strTime, boolean _bCreateLock) {
        return true;
    }

    // nothing
    /**
     * (non-Javadoc)
     * resetLockGroup
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#resetLockGroup(java.lang.String, COM.ibm.eannounce.objects.LockList)
     */
    public void resetLockGroup(String _s, LockList _ll) {
        return;
    }

    // nathan
    /**
     * (non-Javadoc)
     * rollback
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#rollback(java.lang.String)
     */
    public void rollback(String _str) {
        return;
    }

    // nada
    /**
     * (non-Javadoc)
     * setParentEntityItem
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#setParentEntityItem(COM.ibm.eannounce.objects.EntityItem)
     */
    public void setParentEntityItem(EntityItem _ei) {
        return;
    }

    /// non
    /**
     * (non-Javadoc)
     * unlock
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#unlock(java.lang.String, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Database, COM.ibm.eannounce.objects.LockList, COM.ibm.opicmpdh.middleware.Profile, COM.ibm.eannounce.objects.EntityItem, int)
     */
    public void unlock(String _s, RemoteDatabaseInterface _rdi, Database _db, LockList _ll, Profile _prof, EntityItem _lockOwnerEI, int _iLockType) {
        return;
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
