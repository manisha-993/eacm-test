//
// Copyright (c) 2003, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: SimpleTableModel.java,v $
// Revision 1.10  2010/02/21 18:22:08  wendy
// Add dereference
//
// Revision 1.9  2008/01/31 22:56:01  wendy
// Cleanup RSA warnings
//
// Revision 1.8  2005/02/01 21:28:56  gregg
// some filter for cols
//
// Revision 1.7  2004/08/11 20:30:11  gregg
// more refresh()
//
// Revision 1.6  2004/08/11 20:17:32  gregg
// fixing refresh()
//
// Revision 1.5  2003/03/12 22:06:15  gregg
// isCellEditable method + moved all common table logic up into Abstract
//
// Revision 1.4  2003/03/10 21:00:10  gregg
// refreshTable() method --> refresh() for consistency
//
// Revision 1.3  2003/03/10 17:14:04  gregg
// changes to EANTableRowTemplate
//
// Revision 1.2  2003/03/08 00:49:41  gregg
// end-of-the-day commit (lots-o-changes)
//
// Revision 1.1  2003/03/07 22:13:19  gregg
// initial load
//
//

package COM.ibm.eannounce.objects;

import java.util.Enumeration;

/**
 * Object Adapter for the <CODE>EANSimpleTableTemplate</CODE> Interface.
 * <BR>- Simple Non-Editable Table for <CODE>EANObjects</CODE> (Based on RowSelectableTable Object).
 * <BR>- Assumes Rows of <CODE>EANTableRowTemplates</CODE> and Columns of <CODE>EANMetaFoundations</CODE>.
 */
public class SimpleTableModel extends AbstractTableModel {

  /////////////////////////////////////////////////////////////
  // Supports the following functions:
  // ----------------------------------------------------------
  // -- Filter Rows using FilterGroup (requires refresh())
  // -- Hide Columns (requires refresh())
  // -- Move Rows (does NOT require refresh())
  /////////////////////////////////////////////////////////////

  /**
  * @serial
  */
    static final long serialVersionUID = 1L;

    private String m_strTitle = null;
    private EANSimpleTableTemplate m_estt = null;
    private FilterGroup m_filterGroup = null;
    private boolean m_bUseFilter = true;
    private FilterGroup m_colFilterGroup = null;

    public void dereference(){
    	super.dereference();
    	m_colFilterGroup = null;
    	m_filterGroup = null;
    	m_estt = null;
    	m_strTitle = null;
    }

    public SimpleTableModel(EANSimpleTableTemplate _estt, String _strTitle) {
        super(_estt.getRowList(),_estt.getColumnList());
        m_estt = _estt;
        setTitle(_strTitle);
    }

    protected EANSimpleTableTemplate getSimpleTableTemplate() {
        return m_estt;
    }

/////////////////////
// Misc. ACCESSORS //
/////////////////////

	public FilterGroup getFilterGroup() {
		return m_filterGroup;
	}

	public boolean hasFilterGroup() {
	    return m_filterGroup != null;
	}

    public boolean hasFilteredRows() {
        return !(super.getRowCount() == super.getAllTableRows().size());
    }

	public FilterGroup getColFilterGroup() {
		return m_colFilterGroup;
	}

	public boolean hasColFilterGroup() {
	    return m_colFilterGroup != null;
	}

    public boolean hasFilteredCols() {
        return !(super.getColumnCount() == super.getAllTableColumns().size());
    }

	public String getTitle() {
	    return m_strTitle;
	}


///////////////////
// Misc MUTATORS //
///////////////////

	public void setFilterGroup(FilterGroup _fg) {
	    m_filterGroup = _fg;
		return;
	}

/**
 * This will null out FilterGroup.
 */
	public void removeFilterGroup() {
	    m_filterGroup = null;
	}

	public void setColFilterGroup(FilterGroup _fg) {
	    m_colFilterGroup = _fg;
		return;
	}

/**
 * This will null out FilterGroup.
 */
	public void removeColFilterGroup() {
	    m_colFilterGroup = null;
	}

/**
 * Bypass filter if set to false.
 */
    public void setUseFilter(boolean _b) {
        m_bUseFilter = _b;
    }

    public void setTitle(String _strTitle) {
        m_strTitle = _strTitle;
    }

/////////////////
// Misc public //
/////////////////
/**
 * Apply any set properties to all rows/columns
 * <BR>- such as hide, filter, order
 */
    public void refresh() {
        applyFilter();
        super.refresh();
    }

///////////////////////////////////
// protected sub-routines etc.. //
///////////////////////////////////


////////////////////////////////
// private sub-routines etc.. //
////////////////////////////////

/**
 * Apply filter to our table (we really are removing columns from our table's column list)
 * -- RE: this is really filtering out rows based on visible columns, so call this AFTER cols are sync'd
 */
	private void applyFilter() {
	    // just to protect against nulls...
		if(!hasFilterGroup() || !useFilter()) {
		    setAllRowsVisible();
		    //return;
		}
		if(!hasColFilterGroup() || !useFilter()) {
		    setAllColsVisible();
		    //return;
		}
		if(!useFilter()) {
			return;
		}
		// 1) go through each column and pull out FilterItems matching each column's key
		//    - then, we can filter out any rows for this particular FilterItem
		//    - only filter on displayable cols
	    if(hasFilterGroup()) {
	    	for(int iCol = 0; iCol < getColumnCount(); iCol++) {
        	    String strColKey = getColumnKey(iCol);
				// 2) go through ACTUAL rows and evaluate each FilterItem against the current cell's value
				Enumeration eNum = getAllTableRows().elements();
				while(eNum.hasMoreElements()) {
				    TableRow row = (TableRow)eNum.nextElement();
				    String strRowKey = row.getKey();
				    EANTableRowTemplate etrt = getRow(strRowKey);
				    String strValue = etrt.getEANObject(strColKey).toString();
					//if it doesnt pass -> not displayable
					boolean bRowVisible = getFilterGroup().evaluate(strColKey,strValue);
				    if(!bRowVisible) {
				 	    row.setVisible(bRowVisible);
				    }
				}
			}
		}
		//
	    if(hasColFilterGroup()) {
	    	for(int iRow = 0; iRow < getRowCount(); iRow++) {
        	    String strRowKey = getRowKey(iRow);
				// 2) go through ACTUAL cols and evaluate each FilterItem against the current cell's value
				Enumeration eNum = getAllTableColumns().elements();
				while(eNum.hasMoreElements()) {
				    TableColumn col = (TableColumn)eNum.nextElement();
				    String strColKey = col.getKey();
				    int iColIdx = getColumnIndex(strColKey);
				    EANFoundation ef = get(iRow,iColIdx);
				    String strValue = ef.toString();
					//if it doesnt pass -> not displayable
					boolean bRowVisible = getColFilterGroup().evaluate(strRowKey,strValue);
				    if(!bRowVisible) {
				 	    col.setVisible(false);
				    }
				}
			}
		}

	}

//are we paying attention to or by-passing filter?
    private boolean useFilter() {
	    return m_bUseFilter;
	}

}
