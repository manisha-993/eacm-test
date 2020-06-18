//
// Copyright (c) 2003, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: FilterGroup.java,v $
// Revision 1.61  2010/08/28 01:37:26  wendy
// Support copy and refresh of columnfilter
//
// Revision 1.60  2010/02/20 00:06:30  wendy
// set casesensitive in copy
//
// Revision 1.59  2009/05/14 18:40:05  wendy
// Support dereference for memory release
//
// Revision 1.58  2005/11/04 14:52:10  tony
// VEEdit_Iteration2
// updated VEEdit logic to meet new requirements.
//
// Revision 1.57  2005/03/23 17:23:21  gregg
// removing some debugs stmts
//
// Revision 1.56  2005/03/03 21:25:17  dave
// NEW_LINE on EAN Foundation
//
// Revision 1.55  2005/03/03 18:36:52  dave
// more Jtest
//
// Revision 1.54  2005/02/04 17:21:50  gregg
// cleanup
//
// Revision 1.53  2005/02/03 22:30:57  gregg
// fix for copy()
//
// Revision 1.52  2005/02/03 21:44:27  gregg
// column filter
//
// Revision 1.51  2005/02/03 17:24:34  gregg
// debugging
//
// Revision 1.50  2005/02/01 21:28:56  gregg
// some filter for cols
//
// Revision 1.49  2005/02/01 19:34:54  gregg
// fix boolean logic
//
// Revision 1.48  2005/02/01 19:20:06  gregg
// settin up for column fitlering
//
// Revision 1.47  2005/01/10 21:47:49  joan
// work on multiple edit
//
// Revision 1.46  2005/01/05 19:24:08  joan
// add new method
//
// Revision 1.45  2004/07/29 21:51:03  gregg
// create new instances of FilterItems in copy()
//
// Revision 1.44  2004/07/29 20:16:49  gregg
// copy()
//
// Revision 1.43  2004/06/08 17:51:31  joan
// throw exception
//
// Revision 1.42  2004/06/08 17:28:34  joan
// add method
//
// Revision 1.41  2004/04/09 19:37:20  joan
// add duplicate method
//
// Revision 1.40  2003/08/28 16:28:05  joan
// adjust link method to have link option
//
// Revision 1.39  2003/08/14 18:39:30  gregg
// FilterItem.refresh()
//
// Revision 1.38  2003/08/14 17:46:51  gregg
// final refresh (key problems)
//
// Revision 1.37  2003/08/14 16:42:42  gregg
// fix
//
// Revision 1.36  2003/08/14 00:41:17  gregg
// one more..
//
// Revision 1.35  2003/08/14 00:26:24  gregg
// more refresh()
//
// Revision 1.34  2003/08/13 23:40:57  gregg
// refresh() method
//
// Revision 1.33  2003/08/13 22:36:56  gregg
// make refreshFilterKeys method public for Tony
//
// Revision 1.32  2003/06/25 18:44:00  joan
// move changes from v111
//
// Revision 1.31  2003/05/09 20:41:20  gregg
// removed some debugs
//
// Revision 1.30  2003/05/01 23:47:29  gregg
// if an item in picklist is an Implicator--> we need to display ~parent's~ description.
//
// Revision 1.29  2003/03/10 17:12:24  gregg
// implement EANSimpleTableTemplate, EANTableRowTemplate
//
// Revision 1.28  2003/03/06 20:37:58  gregg
// protected refreshFilterKeys method
//
// Revision 1.27  2003/02/27 19:15:04  gregg
// remove deprecated constructor
//
// Revision 1.26  2003/02/27 18:39:51  gregg
// putShortDescription on column headers
//
// Revision 1.25  2003/02/26 21:56:30  gregg
// New SimpleTextAttribute/SimplePicklistAttribute classes + use these in Rendering RowSelectableTable for FilterGroup/FilterItem
//
// Revision 1.24  2003/02/25 18:35:34  gregg
// remove deprecated on 1st constructor
//
// Revision 1.23  2003/02/25 18:26:30  gregg
// getNextFilterKeyID
//
// Revision 1.22  2003/02/25 18:16:08  gregg
// add in ability to externally set caseSensitivity for FilterGroup
//
// Revision 1.21  2003/02/24 23:03:29  gregg
// getting there...
//
// Revision 1.20  2003/02/24 19:46:40  gregg
// more RowSelectableTable
//
// Revision 1.19  2003/02/20 23:36:58  gregg
// put getColumn method back
//
// Revision 1.18  2003/02/20 23:25:47  gregg
// some more logic to render as EANTableWrapper
//
// Revision 1.17  2003/02/20 19:21:37  gregg
// more logic for Using this guy as a TableWrapper for a RowSelectableTable
//
// Revision 1.16  2003/02/19 22:08:41  gregg
// more tMeta to Data converisons
//
// Revision 1.15  2003/02/19 21:46:52  gregg
// getFilterItemCount should now return getDataCount
//
// Revision 1.14  2003/02/19 21:15:34  gregg
// debug for evaluate
//
// Revision 1.13  2003/02/19 20:30:41  gregg
// more for compile
//
// Revision 1.12  2003/02/19 20:12:20  gregg
// some Exception catching/throwing to enable compilation
//
// Revision 1.11  2003/02/19 19:56:41  gregg
// logic for EANTableWrapper, EANAddressable interfaces
//
// Revision 1.10  2003/02/18 19:12:38  gregg
// implement EANTableWrapper method stubs
//
// Revision 1.9  2003/02/18 19:10:51  gregg
// in applyFilter-> use getColumnKey(i) : (not getColumn(i).getKey())
//
// Revision 1.8  2003/02/18 18:46:25  gregg
// FilterItem.getKey -> getFilterKey
//
// Revision 1.7  2003/02/18 00:05:55  gregg
// first shot at applyFilter logic for RowSelectableTable
//
// Revision 1.6  2003/02/17 19:21:23  gregg
// contains methods
//
// Revision 1.5  2003/02/14 23:55:14  gregg
// compress FilterList into FilterGroup (i.e. Group now spans attributes)
//
// Revision 1.4  2003/02/13 19:15:48  gregg
// reset method
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
import java.sql.SQLException;
import java.util.Vector;
import java.rmi.RemoteException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.LockException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;

/**
 * Manage a collection of FilterItems
 */
public class FilterGroup extends EANMetaEntity implements EANTableWrapper, EANSimpleTableTemplate {

    /**
    * @serial
    */
    static final long serialVersionUID = 1L;

    /**
     * FIELD
     */
    protected static final String FILTERKEY_ATTCODE = "FILTERKEY";
    /**
     * FIELD
     */
    protected static final String FILTERVALUE_ATTCODE = "FILTERVALUE";
    /**
     * FIELD
     */
    protected static final String FILTERCONDITION_ATTCODE = "FILTERCONDITION";
    private int m_iNextFilterKeyID = -1;
    private boolean m_bCaseSensitive = false;
    private EANList m_filterKeyList = null;
    private boolean m_bColumnFilter = false; // row is the typical case
    
    protected void dereference(){
    	super.dereference();
    	if (m_filterKeyList !=null){
    		for (int i=0; i<m_filterKeyList.size(); i++){
    			MetaTag mt = (MetaTag) m_filterKeyList.getAt(i);
    			if (mt != null){
    				mt.dereference();
    			}
    		}
    		m_filterKeyList.clear();
    		m_filterKeyList = null;
    	}
    }

    /**
     * Constructor typeI
     * Use this constructor if you wish to use this FilterGroup on a RowSelectableTable
     *
     * @param _emf the Parent reference EANMetaFoundation (can be null in most cases)
     * @param _prof The profile reference of the creator (_prof and _emf cannot both be null)
     * @param _rstParent the RowSelectableTable we are filtering on (the one that we got this FilterGroup from)
     * @param _bColumnFilter
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     */
    public FilterGroup(EANMetaFoundation _emf, Profile _prof, RowSelectableTable _rstParent, boolean _bColumnFilter) throws MiddlewareRequestException {
        super(_emf, _prof, "FilterGroup:RST");

        try {
            EANList eListParent = new EANList();

            m_bColumnFilter = _bColumnFilter;
            if (_rstParent != null) {
                if (!m_bColumnFilter) {
                    eListParent = _rstParent.getColumnList();
                } else {
                    eListParent = _rstParent.getRowList();
                }
            }
            buildHeaderList();
            refreshFilterKeys(eListParent);
        } finally {
            //
        }
    }

    /**
     * FilterGroup
     *
     * @param _emf
     * @param _prof
     * @param _rstParent
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public FilterGroup(EANMetaFoundation _emf, Profile _prof, RowSelectableTable _rstParent) throws MiddlewareRequestException {
        this(_emf, _prof, _rstParent, false);
    }

    /**
     * for copy().
     */
    private FilterGroup(EANMetaFoundation _emf, Profile _prof, boolean _bColFilter) throws MiddlewareException {
        super(_emf, _prof, "FilterGroup:RST");
        setColumnFilter(_bColFilter);
        buildHeaderList();
    }

    /**
     * JUI needs a new instance of this FilterGroup which is identical to the existing structure. orig will be derefd
     *
     * @return FilterGroup
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     */
    public FilterGroup copy() throws MiddlewareException {
        FilterGroup fg_new = new FilterGroup((EANMetaFoundation) this.getParent(), this.getProfile(), isColumnFilter());
        fg_new.setColumnFilter(this.isColumnFilter());
        fg_new.m_filterKeyList = null;//this.getFilterKeyList();
        fg_new.m_bCaseSensitive = this.m_bCaseSensitive;

        if(getFilterKeyList()!=null){ // make a copy, orig will be dereferenced
        	fg_new.m_filterKeyList = new EANList();
        	for (int i = 0; i < getFilterKeyList().size(); i++) {
        		MetaTag mt = (MetaTag) getFilterKeyList().getAt(i);
        		MetaTag mt_new = new MetaTag(null, mt.getProfile(), mt.getKey());

        		mt_new.putLongDescription(mt.toString());
        		mt_new.putShortDescription(mt.toString());

        		fg_new.m_filterKeyList.put(mt_new);
        	}
        }
        
        for (int i = 0; i < this.getFilterItemCount(); i++) {
            FilterItem fi_this = this.getFilterItem(i);
            FilterItem fi_new = new FilterItem(fg_new, fi_this.getProfile(), fi_this.getFilterKey(), fi_this.getCondition(), fi_this.getValue());
            fg_new.putFilterItem(fi_new);
        }
        return fg_new;
    }

    /**
     * Constructor typeII
     * Use this constructor if you wish to use this FilterGroup on a SimpleTableModel
     *
     * @param _emf the Parent reference EANMetaFoundation (can be null in most cases)
     * @param _prof The profile reference of the creator (_prof and _emf cannot both be null)
     * @param _stmParent
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     */
    public FilterGroup(EANMetaFoundation _emf, Profile _prof, SimpleTableModel _stmParent) throws MiddlewareRequestException {
        this(_emf, _prof, _stmParent, false);
    }

    /**
     * FilterGroup
     *
     * @param _emf
     * @param _prof
     * @param _stmParent
     * @param _bColumnFilter
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public FilterGroup(EANMetaFoundation _emf, Profile _prof, SimpleTableModel _stmParent, boolean _bColumnFilter) throws MiddlewareRequestException {
        super(_emf, _prof, "FilterGroup:STM");
        try {
            EANList eListParent = new EANList();
            if (_stmParent != null) {
                if (!m_bColumnFilter) {
                    eListParent = _stmParent.getColumnList();
                } else {
                    eListParent = _stmParent.getRowList();
                }
            }
            buildHeaderList();
            refreshFilterKeys(eListParent);
        } finally {
        }
    }

    /**
     * Build column list of MetaLabels + ALSO store parent columns as EANList of MetaTags:
     *   1) Col 1 is Filter Key
     *   2) Col 2 is Condition
     *   3) Col 3 is Value
     */
    private void buildHeaderList() {

        MetaLabel mlFilterKey = null;
        MetaLabel mlCondition = null;
        MetaLabel mlVal = null;

        try {
            // storing RST's columns as labels - remember desc's are ONLY USEnglish right now (NLS=1)!

            mlFilterKey = new MetaLabel(null, getProfile(), FILTERKEY_ATTCODE, "Filter Key", SimplePicklistAttribute.class); // desc not really used ..?
            mlFilterKey.putShortDescription("Filter Key");
            putMeta(mlFilterKey);
            mlCondition = new MetaLabel(null, getProfile(), FILTERCONDITION_ATTCODE, "Condition", SimplePicklistAttribute.class); // desc not really used ..?
            mlCondition.putShortDescription("Condition");
            putMeta(mlCondition);
            mlVal = new MetaLabel(null, getProfile(), FILTERVALUE_ATTCODE, "Filter Value", SimpleTextAttribute.class); // desc not really used ..?
            mlVal.putShortDescription("Filter Value");
            putMeta(mlVal);

        } catch (Exception exc) {
            exc.printStackTrace(System.out);
            System.out.println("End of buildHeaderList:Exception:" + exc.toString());
        }

    }

    /**
     * This is needed because a FilterGroup could be saved and later replayed. At this later date, we
     * need to revalidate the group/items b/c possible columns for the item we are filtering *on* could
     * have changed.
     * 1) Refresh the possible filter key column values
     *  AND
     * 2) Remove any obsolete FilterItems
     *  AND
     * 3) Refresh all obsolete filter keys in valid children.
     */
    private void refresh(EANList _eListParent) {

        Vector vctRemoveKeys = new Vector();
        refreshFilterKeys(_eListParent);

        for (int i = 0; i < getFilterItemCount(); i++) {
            String strFilterItemKey = getFilterItem(i).getFilterKey();
            boolean bFound = false;
            for (int j = 0; j < getFilterKeyList().size(); j++) {
                String strFilterKeyList = ((MetaTag) getFilterKeyList().getAt(j)).getKey();
                if (strFilterItemKey.equals(strFilterKeyList)) {
                    bFound = true;
                }
            }
            if (!bFound) {
                vctRemoveKeys.addElement(strFilterItemKey);
            } else {
                // 3)
                try {
                    getFilterItem(i).refresh();
                } catch (MiddlewareRequestException mre) {
                    System.err.println("FilterGroup Exception 244:"); // this should really never occur
                    mre.printStackTrace(System.err);
                }
            }
        }
        for (int i = 0; i < vctRemoveKeys.size(); i++) {
            String strRemoveKey = (String) vctRemoveKeys.elementAt(i);
            for (int j = 0; j < getFilterItemCount(); j++) {
                if (getFilterItem(j).getFilterKey().equals(strRemoveKey)) {
                    removeFilterItem(getFilterItem(j));
                    break;
                }
            }
        }
    }

    /**
     * refresh
     *
     * @param _rst
     *  @author David Bigelow
     */
    public void refresh(RowSelectableTable _rst) {
        //refresh(_rst.getColumnList()); must look if this is done by columns
        if (!m_bColumnFilter) {
        	refresh(_rst.getColumnList());
        } else {
        	refresh(_rst.getRowList());
        }
    }

    /**
     * refresh
     *
     * @param _stm
     *  @author David Bigelow
     */
    public void refresh(SimpleTableModel _stm) {
        refresh(_stm.getColumnList());
    }

    /**
     * This should be called any time a parent table's columns are added/removed so that selectable filterKey's
     * are valid columns
     *
     * @param _eListParent
     */
    protected void refreshFilterKeys(EANList _eListParent) {
        try {
            m_filterKeyList = new EANList();
            for (int i = 0; i < _eListParent.size(); i++) {
                EANFoundation ef = (EANFoundation) _eListParent.getAt(i);
                //now put the MetaFlag -- these are options in the RowSelectableTable!!
                // Key of parent col is really the attcode
                MetaTag mt = new MetaTag(null, getProfile(), ef.getKey());

                // cols are EANMetaFoundation, rows are EANFoundation

                if (!isColumnFilter()) {
                    EANMetaFoundation emf = (EANMetaFoundation) ef;
                    mt.putLongDescription((emf instanceof Implicator ? emf.getParent().getLongDescription() : emf.getLongDescription()));
                    mt.putShortDescription((emf instanceof Implicator ? emf.getParent().getShortDescription() : emf.getShortDescription()));
                } else {
                    mt.putLongDescription(ef.toString());
                    mt.putShortDescription(ef.toString());
                }
                m_filterKeyList.put(mt);
            }
        } catch (Exception exc) {
            exc.printStackTrace(System.out);
            System.err.println("end of buildColumnList:Exception:" + exc.toString());
        }
    }

    ///// MUTATORS ///////
    /**
     * setCaseSensitive
     *
     * @param _b
     *  @author David Bigelow
     */
    public void setCaseSensitive(boolean _b) {
        m_bCaseSensitive = _b;
    }

    /**
     * build+put a FilterItem w/ the given properties
     *
     * @return the newly created FilterItem
     * @param _strKey
     * @param _iCondition
     * @param _strValue
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     */
    public FilterItem putNewFilterItem(String _strKey, int _iCondition, String _strValue) throws MiddlewareRequestException {
        FilterItem fi = new FilterItem(this, this.getProfile(), _strKey, _iCondition, _strValue);
        putFilterItem(fi);
        return fi;
    }

    /**
     * putFilterItem
     *
     * @param _fi
     *  @author David Bigelow
     */
    public void putFilterItem(FilterItem _fi) {
        putData(_fi);
    }

    /**
     * removeFilterItem
     *
     * @param _fi
     *  @author David Bigelow
     */
    public void removeFilterItem(FilterItem _fi) {
        removeData(_fi);
    }

    /**
     * removeFilterItem
     *
     * @param _strKey
     *  @author David Bigelow
     */
    public void removeFilterItem(String _strKey) {
        if (getFilterItem(_strKey) != null) {
            removeFilterItem(getFilterItem(_strKey));
        }
    }

    ///// ACCESSORS

    // return an EANList of MetaTags containing possible Filter Keys
    /**
     * getFilterKeyList
     *
     * @return
     *  @author David Bigelow
     */
    protected EANList getFilterKeyList() {
        return m_filterKeyList;
    }

    /**
     * isCaseSensitive
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isCaseSensitive() {
        return m_bCaseSensitive;
    }

    //attempt to keep FilterItem keys unique within this FilterGroup...
    /**
     * getNextFilterKeyID
     *
     * @return
     *  @author David Bigelow
     */
    protected int getNextFilterKeyID() {
        return ++m_iNextFilterKeyID;
    }

    /**
     * Render this FilterGroup as a RowSelectableTable
     * @return a new instance of RowSelectableTable for this Group
     */
    public RowSelectableTable getFilterGroupTable() {
        return new RowSelectableTable(this, getKey());
    }

    /**
     * getFilterItem
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public FilterItem getFilterItem(int _i) {
        return (FilterItem) getData(_i);
    }

    /**
     * getFilterItem
     *
     * @param _strKey
     * @return
     *  @author David Bigelow
     */
    public FilterItem getFilterItem(String _strKey) {
        return (FilterItem) getData(_strKey);
    }

    /**
     * EANSimpleTableTemplate
     *
     * @return EANTableRowTemplate
     * @param _strKey
     */
    public EANTableRowTemplate getRow(String _strKey) {
        return getFilterItem(_strKey);
    }

    /**
     * getFilterItem
     *
     * @param _strFilterKey
     * @param _iCondition
     * @param _strValue
     * @return
     *  @author David Bigelow
     */
    public FilterItem getFilterItem(String _strFilterKey, int _iCondition, String _strValue) {
        return getFilterItem(_strFilterKey + ":" + _iCondition + ":" + _strValue);
    }

    /**
     * getFilterItemCount
     *
     * @return
     *  @author David Bigelow
     */
    public int getFilterItemCount() {
        return getDataCount();
    }

    /**
     * get an array of FilterItems common to a single key
     *
     * @return FilterItem[]
     * @param _strKey
     */
    public FilterItem[] getFilterItemsForKey(String _strKey) {
        FilterItem[] fiArray = null;
        Vector vctFi = new Vector();
        for (int i = 0; i < getFilterItemCount(); i++) {
            if (getFilterItem(i).getFilterKey().equals(_strKey)) {
                vctFi.addElement(getFilterItem(i));
            }
        }
        fiArray = new FilterItem[vctFi.size()];
        vctFi.copyInto(fiArray);
        return fiArray;
    }

    ////////////////////////////////////////////////////
    /// UTILITIES, ETC..
    ////////////////////////////////////////////////////

    /**
     * Effectively remove all FilterItems form this FilterGroup
     */
    public void reset() {
        resetData();
    }

    /**
     * Evaluate the given String against ALL items w/in this FilterGroup for a given key.
     * @param _strKey the key to evaluate for
     * @param _strValue the String to evaluate
     * @return true ONLY if the String passes ALL FilterItem's evaluate(_s) method.
     */
    public boolean evaluate(String _strKey, String _strValue) {
        FilterItem[] arrayFilterItems = getFilterItemsForKey(_strKey);
        for (int i = 0; i < arrayFilterItems.length; i++) {
            //System.out.println(
            //    "evaluate filter item for getKey():"
            //        + arrayFilterItems[i].getKey()
            //        + ":getFilterKey():"
            //        + arrayFilterItems[i].getFilterKey()
            //        + ":getCondition():"
            //        + arrayFilterItems[i].getCondition()
            //        + ":getValue():"
            //        + arrayFilterItems[i].getValue()
            //        + " = "
            //        + arrayFilterItems[i].evaluate(_strValue, isCaseSensitive()));
            if (!arrayFilterItems[i].evaluate(_strValue, isCaseSensitive())) {
                return false;
            }
        }
        //if we made it this far -> it passed!
        return true;
    }
    ///////////////
    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {
        StringBuffer sb = new StringBuffer("FilterGroup:" + getKey() + NEW_LINE);
        sb.append("getFilterItemCount():" + getFilterItemCount() + NEW_LINE);
        if (!_bBrief) {
            sb.append("--->Displaying Children:" + NEW_LINE);
            for (int i = 0; i < getFilterItemCount(); i++) {
                sb.append(getFilterItem(i).dump(_bBrief));
            }
        }
        return sb.toString();
    }

    /**
     * Does the list contain the FilterItem w/ the indicated properties?
     *
     * @return boolean
     * @param _strKey
     * @param _iCondition
     * @param _strValue
     */
    public boolean contains(String _strKey, int _iCondition, String _strValue) {
        return (getFilterItem(_strKey, _iCondition, _strValue) != null);
    }

    /**
     * Does the list contian the indicated FilterItem?
     *
     * @return boolean
     * @param _fi
     */
    public boolean contains(FilterItem _fi) {
        return contains(_fi.getFilterKey(), _fi.getCondition(), _fi.getValue());
    }

    private boolean allRowsValid() {
        //System.out.println("GB - checking allRowsValid()");
        for (int i = 0; i < getFilterItemCount(); i++) {
            if (!getFilterItem(i).isComplete()) {
                //System.out.println("GB - allRowsValid() == false for FilterItem:" + getFilterItem(i).toString());
                return false;
            }
        }
        return true;
    }

    /**
     * getColumn
     *
     * @param _strKey
     * @return
     *  @author David Bigelow
     */
    protected MetaLabel getColumn(String _strKey) {
        return (MetaLabel) getMeta(_strKey);
    }

    ///////////////////////////////////////////
    ///  EANTableWrapper interface methods  ///
    ///////////////////////////////////////////

    /**
     * Dont allow to add if one is open
     *
     * @return boolean
     */
    public boolean addRow() {
        if (!allRowsValid()) {
            return false;
        }
        try {
            String strDefaultFilterKey = "";
            //set the first value in the list
            if (getFilterKeyList().size() > 0) {
                MetaTag mtColZero = (MetaTag) getFilterKeyList().getAt(0);
                strDefaultFilterKey = mtColZero.getKey();
            }
            //System.out.println("GB - addRow w/ filter key = " + strDefaultFilterKey);
            putNewFilterItem(strDefaultFilterKey, FilterItem.CONTAINS_COND, "");
            return true;
        } catch (MiddlewareRequestException exc) {
            System.err.println("Exception in add row:" + exc.toString());
        }
        return false;
    }

	/**
	 * pass thru to the used method
	 * VEEdit_Iteration2
	 *
	 * @param String
	 * @return boolean
	 * @author tony
	 */
	public boolean addRow(String _sKey) {
		return addRow();
	}

    /**
     * Fixed Number of Columns for a FilterGroup:
     *  Col 1) U Flag Att - cols are parent TableWrapper's cols
     *  Col 2) U Flag Att - cols are Conditions
     *  Col 3) Text Att - value can be whatever!
     *
     * @return EANList
     */
    public EANList getColumnList() {
        return getMeta();
    }

    /**
     * (non-Javadoc)
     * getRowList
     *
     * @see COM.ibm.eannounce.objects.EANSimpleTableTemplate#getRowList()
     */
    public EANList getRowList() {
        return getData();
    }

    /**
     * (non-Javadoc)
     * removeRow
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#removeRow(java.lang.String)
     */
    public void removeRow(String _strKey) {
        removeFilterItem(_strKey);
    }

    /**
     * Do nothing - Columns are constant for FilterItems
     *
     * @param _ean
     */
    public void addColumn(EANFoundation _ean) {
        //do nothing
        return;
    }
    /**
     * Returns false
     * @return false
     */
    public boolean hasChanges() {
        return false;
    }
    /**
     * Returns true
     * @return true
     */
    public boolean canCreate() {
        return true;
    }
    /**
     * Returns true
     * @return true
     */
    public boolean canEdit() {
        return true;
    }
    /**
     * Returns false
     *
     * @return false
     * @param _str
     */
    public boolean isMatrixEditable(String _str) {
        return false;
    }
    /**
     * doesnt apply to filter
     *
     * @param _str
     * @param _o
     */
    public void putMatrixValue(String _str, Object _o) {
        return;
    }
    /**
     * doesnt apply to filter
     *
     * @return Object
     * @param _str
     */
    public Object getMatrixValue(String _str) {
        return null;
    }
    /**
     * doesnt apply to filter
     */
    public void rollbackMatrix() {
        return;
    }
    /**
     * doesnt apply to filter
     *
     * @param _db
     * @param _rdi
     */
    public void commit(Database _db, RemoteDatabaseInterface _rdi) {
        return;
    }
    /**
     * doesnt apply to filter
     *
     * @return EntityList
     * @param _db
     * @param _rdi
     * @param _prof
     * @param _strRelatorType
     */
    public EntityList create(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRelatorType) {
        return null;
    }
    /**
     * doesnt apply to filter
     *
     * @return EntityList
     * @param _db
     * @param _rdi
     * @param _prof
     * @param _strRelatorType
     */
    public EntityList generatePickList(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRelatorType) {
        return null;
    }
    /**
     * doesnt apply to filter
     *
     * @return Object[]
     * @param _db
     * @param _rdi
     * @param _prof
     * @param _strKey
     */
    public Object[] getActionItemsAsArray(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strKey) {
        return null;
    }
    /**
     * doesnt apply to filter
     *
     * @return boolean
     * @param _db
     * @param _rdi
     * @param _prof
     * @param _strRowKey
     */
    public boolean removeLink(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRowKey) {
        return false;
    }
    /**
     * doesnt apply to filter
     *
     * @return EANFoundation[]
     * @param _db
     * @param _rdi
     * @param _prof
     * @param _strRowKey
     * @param _aeiChild
     * @param _strLinkOption
     */
    public EANFoundation[] link(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRowKey, EntityItem[] _aeiChild, String _strLinkOption) {
        return null;
    }
    /**
     * doesnt apply to filter
     *
     * @return WhereUsedList
     * @param _db
     * @param _rdi
     * @param _prof
     * @param _strRelatorType
     */
    public WhereUsedList getWhereUsedList(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRelatorType) {
        return null;
    }
    /**
     * doesnt apply to filter
     *
     * @return EntityList
     * @param _db
     * @param _rdi
     * @param _prof
     * @param _astrKey
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws java.rmi.RemoteException
     */
    public EntityList edit(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String[] _astrKey) throws SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, RemoteException {
        return null;
    }

    /**
     * doesnt apply to filter
     *
     * @return boolean
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
    public Object linkAndRefresh(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, LinkActionItem _lai) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, EANBusinessRuleException, LockException, WorkflowException, RemoteException {
        return null;
    }

    /**
     * isColumnFilter
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isColumnFilter() {
        return m_bColumnFilter;
    }

    /**
     * setColumnFilter
     *
     * @param _b
     *  @author David Bigelow
     */
    public void setColumnFilter(boolean _b) {
        m_bColumnFilter = _b;
    }
}
