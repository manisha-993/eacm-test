// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package com.ibm.eacm.table;


import COM.ibm.eannounce.objects.*;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;

import javax.swing.RowSorter.SortKey;
import javax.swing.table.*;

import javax.accessibility.*;

import com.ibm.eacm.*;
import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.objects.*;
import com.ibm.eacm.preference.*;
import com.ibm.eacm.rend.LabelRenderer;
import com.ibm.eacm.tabs.TabPanel;
import com.ibm.eacm.ui.EACMFrame;


/**
 * use this as the base for all tbls.
 *
 * @author Wendy Stimpson
 */
//$Log: BaseTable.java,v $
//Revision 1.9  2014/10/03 11:08:08  wendy
//IN5515352 remove F8 keyboard mapping
//
//Revision 1.8  2013/10/09 16:27:47  wendy
//add room for date calendar button in grid edit
//
//Revision 1.7  2013/09/19 21:46:42  wendy
//turn off sort on model change
//
//Revision 1.6  2013/08/21 15:34:10  wendy
//keep column in view after sort
//
//Revision 1.5  2013/07/31 17:10:29  wendy
//make sure rowsorter is not null before using it
//
//Revision 1.4  2013/07/29 18:38:57  wendy
//Turn off autosort, update classifications after edit and resize cells
//
//Revision 1.3  2013/07/28 17:08:42  wendy
//expose width constants
//
//Revision 1.2  2013/07/18 18:57:40  wendy
//fix compiler warnings
//
//Revision 1.1  2012/09/27 19:39:12  wendy
//Initial code
//
public abstract class BaseTable extends JTable implements EACMGlobals,
Filterable,Sortable
{
	private static final long serialVersionUID = 1L;

	protected static final int MIN_WIDTH_ADJUSTMENT = 10;
	protected static final int CALENDAR_WIDTH_ADJUSTMENT = 20;
	protected static final int FLAG_WIDTH_ADJUSTMENT = 30;
    private static final int MAX_SORT_LEN = 100; // show at most 100 chars in the sort dialog combobox
    private List<RowFilter<Object,Object>> filters = null;

    private MouseMotionAdapter doScrollRectToVisible = new MouseMotionAdapter(){
    	   public void mouseDragged(MouseEvent e) {
    		   Rectangle r = new Rectangle(e.getX(),e.getY(),1,1);
    		   ((JTableHeader)e.getSource()).scrollRectToVisible(r);
    		   scrollRectToVisible(r);
    	   }
    };

	/**
     * BaseTable
     */
    public BaseTable() {
		//set initial RowHeight
		Font font =getFont();
		if (font != null) {
			FontMetrics fm = getFontMetrics(font);
			setRowHeight(fm.getHeight() + 7);// took 7 from getrowheight 4);
		}
		setAutoCreateRowSorter(true);  // allow all tables to be sorted, default sorter is ok

		//IN5515352 remove F8 keyboard mapping
		KeyStroke keyToRemove = KeyStroke.getKeyStroke(KeyEvent.VK_F8, 0);
		Utils.removeKeyBoardMapping(this, keyToRemove);
	}

    /**
     * finish init, derived class needs to invoke this
     */
    protected void init(){
        setRowMargin(0);
        setColumnSelectionAllowed(false);
        setRowSelectionAllowed(true);
        setAutoResizeMode(AUTO_RESIZE_OFF);
        getTableHeader().setReorderingAllowed(true);
        setDefaultRenderer(Object.class, new LabelRenderer());
        setBorder(UIManager.getBorder(FOCUS_BORDER_KEY));
    	if(getRowSorter()!=null){
    		((TableRowSorter<?>)getRowSorter()).setSortsOnUpdates(false);//true);
    	}


		getTableHeader().addMouseMotionListener(doScrollRectToVisible);

        if (getRowCount() > 0) {
            setRowSelectionInterval(0, 0);
        }
        if (getColumnCount() > 0) {
            setColumnSelectionInterval(0, 0);
        }
        // is the viewport size less than the preferred size
        Dimension vd = this.getPreferredScrollableViewportSize();
        Dimension pd = this.getPreferredSize();
        if(vd.width>pd.width){
        	setPreferredScrollableViewportSize(new Dimension(pd.width,vd.height));
        }
        initAccessibility(getAccessibilityKey());
    }

    /**
     * get the accessibility resource key
     * @return
     */
    protected abstract String getAccessibilityKey();

	/**
     * dereference
     */
    public void dereference() {
		getTableHeader().removeMouseMotionListener(doScrollRectToVisible);
		doScrollRectToVisible=null;

        initAccessibility(null);

		Object renderer = getDefaultRenderer(Object.class);
		if(renderer instanceof LabelRenderer){
			((LabelRenderer)renderer).dereference();
		}

    	MouseListener[] l = getMouseListeners();
    	for (int i = 0; i < l.length; ++i) {
    		removeMouseListener(l[i]);
    		l[i]=null;
    	}
    	FocusListener[] fl = getFocusListeners();
    	for (int i = 0; i < fl.length; ++i) {
    		removeFocusListener(fl[i]);
    		fl[i]=null;
    	}

    	if (filters!=null){
    		Iterator<RowFilter<Object, Object>> itr = filters.listIterator();
    		while (itr.hasNext()) {
    			FIFilter fgf = (FIFilter)itr.next();
    			fgf.dereference();
    		}
    		filters.clear();
    		filters = null;
    	}

    	setRowSorter(null); // let dispose run on sorter

    	removeAll();
		setUI(null);
	}

	/**
     * resizeCells
     *
     */
    public void resizeCells() {
		if (!BehaviorPref.canSize(getRowCount())) {
			initRowHeight(); //TIR7C2JUT
			//TODO resize width if possible
			Logger.getLogger(APP_PKG_NAME).log(Level.WARNING,"Cells cannot be resized. "+getRowCount()+" rows exceeds the limit.");
			return;
		}

		//MN 33121008 - if we have too much columns, then resizing cells will produce a 100% CPU usage for a long time
        int maxColumns = BehaviorPref.getMaxColumns();
        if (getColumnCount() > maxColumns) {
        	initRowHeight();//TIR7C2JUT
        	// TODO resize height
        	Logger.getLogger(APP_PKG_NAME).log(Level.WARNING,"Cells cannot be resized. "+getColumnCount()+
        			" columns exceeds the limit of "+maxColumns);
        	return;
        }

        Font fnt = getFont();
        FontMetrics fmt = getFontMetrics(fnt);
    	List<? extends SortKey> curSortKeys = null;
    	if(getRowSorter()!=null) { // verticaledit have column order predefined, so no RowSorter exists
    		curSortKeys = getRowSorter().getSortKeys();
    	}

        // find widest col width for each row and set it
        for (int c=0;c<getColumnCount();++c) {
        	// get header width
        	int arrowWidth=0;
        	int minWidthAdjustment = MIN_WIDTH_ADJUSTMENT;
        	if(curSortKeys!=null){
        		for (SortKey sortKey : curSortKeys) {
           			if (sortKey.getColumn() == convertColumnIndexToModel(c)){
        				arrowWidth = UIManager.getIcon("Table.ascendingSortIcon").getIconWidth(); // allow for arrow indicator
        				break;
        			}
        		}
        	}

			int maxwidth = 0;
        	int width = arrowWidth; // allow for sorted arrow indicator
        	if (getRowCount()>0 && this instanceof RSTTable){
        		EANFoundation ef = ((RSTTable)this).getEANObject(0, c);
        		if(ef instanceof EANFlagAttribute){
        			minWidthAdjustment = FLAG_WIDTH_ADJUSTMENT;
                  	width += getColWidth(fmt, getColumnName(c)); // each derived class can override this if calc is diff
        		}else if (ef instanceof SimplePicklistAttribute){
        			SimplePicklistAttribute pick = (SimplePicklistAttribute)ef;
        			for (int p=0; p<pick.getCount(); p++){
        				maxwidth = Math.max(maxwidth,getColWidth(fmt,pick.getAt(p)));
        			}
        			width += maxwidth;
        		}else{
        			if(this instanceof GridTable){
        				if (ef instanceof TextAttribute) {
        					TextAttribute txt = (TextAttribute) ef;
        					EANMetaAttribute meta = txt.getMetaAttribute();
        					if (meta != null && meta.isDate()) {
        						//allow for calendar button
        						minWidthAdjustment = CALENDAR_WIDTH_ADJUSTMENT;
        					}
        				}
        			}
                   	width += getColWidth(fmt, getColumnName(c));
       		}
        	}else{
            	width += getColWidth(fmt, getColumnName(c));
        	}

        	// find max width in all rows for this column
        	if (maxwidth==0){ // didnt calculate row max value yet
        		for (int r=0; r<getRowCount();++r) {
        			Object o = getValueAt(r,c); // base class gets value from model after converting indexes
        			width = Math.max(width,getColWidth(fmt,o));
        		}
        	}

        	width+=minWidthAdjustment;

        	TableColumn tc = getColumnModel().getColumn(c);
			tc.setWidth(width);
			tc.setPreferredWidth(width);
            tc.setMinWidth(MIN_COL_WIDTH);
		}

        // find biggest row height for each column and set it
		int baseHeight = getRowHeight();
		for (int r=0;r<getRowCount();++r) {
			int h = baseHeight;
			// find max height needed for any column in this row
			for (int c=0;c<getColumnCount();++c) {
				Object o = getValueAt(r,c);
				String str = "";
				if (o != null) {
					str = o.toString();
				}
				h = Math.max(h,getRowHeight(fmt, baseHeight, str));
			}
			setRowHeight(r,h);
		}

		this.getTableHeader().invalidate(); // sometimes the header columns werent matching the table cells, force it
		resizeAndRepaint();
	}

    /**
     * calculate height needed for this row
     * @param fm
     * @param baseHeight
     * @param str - could be multiline in some tables
     * @return
     */
    protected int getRowHeight(FontMetrics fm, int baseHeight, String str){
    	return baseHeight;
    }

    /**
     * get the width needed for this object
     * @param fm
     * @param o
     * @return
     */
    protected int getColWidth(FontMetrics fm, Object o) {
        int w = MIN_COL_WIDTH;
        if (o != null) {
             w = fm.stringWidth(o.toString());
        }
        return w;
    }
    /**
     * Rows need some initial height for freeze, if num rows or cols exceeds the limit for resizecells
     *
     * TIR7C2JUT-Freeze action is not working
     */
    private void initRowHeight() {
		int height = getRowHeight();
		for (int r=0;r<getRowCount();++r) {
			setRowHeight(r, height);
		}
	}

    /**
     * scrollToRow
     * @param row
     */
    public void scrollToRow(int row) {
    	int col = Math.max(0, getSelectedColumn());
    	scrollToRowCol(row, col);
    }
    /**
     * scrollToRowCol
     * @param row
     * @param col
     */
    public void scrollToRowCol(int row, int col) {
        if (isValidCell(row, col)) {
            scrollRectToVisible(getCellRect(row, col, true));
        }
    }

	/**
     * isValidCell
     * @param r
     * @param c
     * @return
     */
    protected boolean isValidCell(int r, int c) {
		if (r < 0 || r >= getRowCount()) {
			return false;
		}
		if (c < 0 || c >= getColumnCount()) {
			return false;
		}
		return true;
	}

    /**
     * must override JComponent bindings or EACM copy and paste action are not invoked
     * with standard keybindings
     * @param act
     * @param keyCode
     * @param modifiers
     */
    public void registerEACMAction(EACMAction act, int keyCode, int modifiers){
        KeyStroke keystroke = KeyStroke.getKeyStroke(keyCode, modifiers, false);
        getInputMap().put(keystroke, act.getActionKey());
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keystroke, act.getActionKey());
	    getActionMap().put(act.getActionKey(), act);
    }

    /**
     * call this when dereferencing components that registered copy or paste actions
     * @param keyCode
     * @param modifiers
     */
    public void unregisterEACMAction(EACMAction act, int keyCode, int modifiers){
        KeyStroke keystroke = KeyStroke.getKeyStroke(keyCode, modifiers, false);
        getInputMap().remove(keystroke);
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).remove(keystroke);
	    getActionMap().remove(act.getActionKey());
    }

    //=================================================
    //COPY_ACTION
    /**
     * This copies from the table and puts text on the clipboard
     */
    public void copy(){
        Clipboard clipboard= getToolkit().getSystemClipboard();

        if (clipboard  != null) {
        	StringBuffer sb = new StringBuffer();
        	int[] rows = getSelectedRows();
        	int[] cols = getSelectedColumns();

        	for (int r = 0; r < rows.length; ++r) {
        		for (int c = 0; c < cols.length; ++c) {
        			Object o = getValueAt(rows[r], cols[c]);
        			if (c > 0) {
        				sb.append(DELIMIT_CHAR); // delimit each column with a |
        			}
        			sb.append(o.toString());
        		}
        		sb.append(NEWLINE); // delimit each row with a newline - multiflag are newline delim also trim *
        	}

        	StringSelection contents = new StringSelection(sb.toString());
        	clipboard.setContents(contents, null);
        }
    }


    //==================================================
    // MOVECOL_LEFT_ACTION and MOVECOL_RIGHT_ACTION
    public void moveColumn(boolean _left) {
    	int selectedColumn = getSelectedColumn();
    	int columnCount = getColumnCount();
    	if (selectedColumn < 0 || selectedColumn >= columnCount) {
    		return;
    	}

    	int columnDir = 1;
    	int row = getSelectedRow();

    	if (_left) {
    		columnDir = -1;
    	}
    	int targetColumn = getNextColumn(selectedColumn, columnDir);
    	if (targetColumn < 0 || targetColumn >= columnCount) {
    		return;
    	}
    	moveColumn(selectedColumn, targetColumn);

    	if (row < 0 || row >= getRowCount()) {
    		return;
    	}
    	setRowSelectionInterval(row, row);
    	setColumnSelectionInterval(targetColumn, targetColumn);
    	scrollRectToVisible(getCellRect(row, targetColumn, false));
    }
    private int getNextColumn(int c, int m) {
    	int col = c + m;
    	int cc = getColumnCount();
    	if (col >= cc || col < 0) {
    		return c;
    	}
    	// get past a 0 width column, matrix or cross tables set col0=0 but it may have moved
    	if (getColumnModel().getColumn(col).getWidth()==0){
    		col+=m;
    	}
    	if (col >= cc || col < 0) {
    		return c;
    	}

    	return col;
    }
    //==================================================
    // HIDECOL_ACTION and UNHIDECOL_ACTION
    public void showHide(boolean hide) {
    	if (hide) {
    		String[] cols = getSelectedColumnKeys();
    		clearSelection();
    		if (cols != null) {
    			TableColumnModel tcm = getColumnModel();
    			if (tcm != null) {
    				for (int i = 0; i < cols.length; ++i) {
    					TableColumn tc = tcm.getColumn(tcm.getColumnIndex(cols[i]));
    					tcm.removeColumn(tc);
    				}
    			}
    			if (this.getColumnCount()>0){
    			     setColumnSelectionInterval(0, 0);
    			}
    			if (this.getRowCount()>0){
    				setRowSelectionInterval(0, 0);
    			}
    		}
    	} else {
    		createDefaultColumnsFromModel();
			if (this.getColumnCount()>0){
			     setColumnSelectionInterval(0, 0);
			}
			if (this.getRowCount()>0){
				setRowSelectionInterval(0, 0);
			}
    	}

    	EACM.getEACM().setHiddenStatus(hasHiddenCols());
    	resizeCells();
    }
    private String[] getSelectedColumnKeys() {
        int[] cols = getSelectedColumns();
        if (cols != null && cols.length>0) {
            String[] out = new String[cols.length];
            TableColumnModel tcm = getColumnModel();
            for (int i = 0; i < cols.length; ++i) {
                TableColumn tc = tcm.getColumn(cols[i]);
                out[i] = tc.getIdentifier().toString();
            }
            return out;
        }
        return null;
    }

    /**
     * check to see if the view column count matches the model column count
     * if not, a column was hidden
     * @return
     */
    public boolean hasHiddenCols(){
    	return this.getColumnCount()!=this.getModel().getColumnCount();
    }

    /**
     * get the column name without truncating
     * @param viewColumnIndex
     * @return
     */
    protected String getFullColumnName(int viewColumnIndex){
    	return getColumnName(viewColumnIndex);
    }

//  ====================================================================
//  Sort support
     /* (non-Javadoc)
      * used in sortdialog for sort choice
      * @see com.ibm.eacm.objects.Sortable#getVisibleColumnNames()
      */
     public SortColumn[] getVisibleColumnNames() {
         int cc = getColumnCount();
         SortColumn[] out = new SortColumn[cc];
         for (int viewColumnIndex = 0; viewColumnIndex < cc; ++viewColumnIndex) {
        	 out[viewColumnIndex]=new SortColumn(
        			 Routines.truncate(getFullColumnName(viewColumnIndex),MAX_SORT_LEN),
        			 convertColumnIndexToModel(viewColumnIndex));
         }

         return out;
     }

     /* (non-Javadoc)
      * called by sortdialog when user has specified desired sort
      * and when mouse is clicked on the rowheaderlabel
      * @see com.ibm.eacm.objects.Sortable#sort(List <RowSorter.SortKey> list);
      */
     public void sort(List <RowSorter.SortKey> list){
    	 if(getRowSorter() != null){
    		 setSortKeys(list);
    		 ((TableRowSorter<?>)getRowSorter()).sort();
    	 }
     }

     /**
      * set the sort criteria, derived classes have defaults that can be overridden with the sortdialog
      * @param sortKeys
      */
     protected void setSortKeys(List <? extends SortKey> sortKeys){
    	 if(getRowSorter() != null){
    		 ((TableRowSorter<?>)getRowSorter()).setSortKeys(sortKeys);
    	 }
     }

     /**
      * get current set of sortkeys
     * @return
     */
    public List<? extends SortKey> getSortKeys(){
    	 List<? extends SortKey> origsortKeys = null;
    	 if(getRowSorter() !=null){
    		 origsortKeys = ((TableRowSorter<?>)getRowSorter()).getSortKeys();
    	 }
    	 return origsortKeys;
     }
//   ====================================================================
//   Filterable support
// use the FilterGroup object but do not apply it to the model, allow the view to do the filtering
 	/* (non-Javadoc) derived class must support this
 	 * @see com.ibm.eacm.objects.Filterable#getFilterableTable()
 	 */
 	public Object getFilterableTable(){
 		return null;
 	}
    /* (non-Javadoc)
     * @see com.ibm.eacm.objects.Filterable#setFilterGroup(COM.ibm.eannounce.objects.FilterGroup)
     */
    public void setFilterGroup(FilterGroup group) {}

    /* (non-Javadoc)
     * @see com.ibm.eacm.objects.Filterable#getColFilterGroup()
     */
    public FilterGroup getColFilterGroup() {
        return null;
    }
    /* (non-Javadoc)
     * @see com.ibm.eacm.objects.Filterable#setColFilterGroup(COM.ibm.eannounce.objects.FilterGroup)
     */
    public void setColFilterGroup(FilterGroup group) {}

    /* (non-Javadoc)
     * @see com.ibm.eacm.objects.Filterable#getFilterGroup()
     */
    public FilterGroup getFilterGroup() {
        return null;
    }
    /* (non-Javadoc)
     * @see com.ibm.eacm.objects.Filterable#isPivoted()
     */
    public boolean isPivoted(){ return false; }
     /* (non-Javadoc)derived class must support this
      * this is used for saving and restoring FilterGroup from serial preferences
      * @see com.ibm.eacm.objects.Filterable#getUIPrefKey()
      */
     public String getUIPrefKey() {
         return "BaseTable";
     }
     /* (non-Javadoc)
      * @see com.ibm.eacm.objects.Filterable#isFiltered()
      */
     public boolean isFiltered() {
    	 // check RowSorter for filters
    	 boolean filtered = false;
    	 if(getRowSorter() !=null){
    		 filtered = (((TableRowSorter<?>)getRowSorter()).getRowFilter() != null);
    	 }
         return filtered;
     }
     /* (non-Javadoc)
      * @see com.ibm.eacm.objects.Filterable#resetFilter()
      */
     public void resetFilter() {
    	 if(getRowSorter() !=null){
    		 ((TableRowSorter<?>)getRowSorter()).setRowFilter(null);
    	 }

    	 setFilterIcon();
     }

     /* (non-Javadoc)
      * @see com.ibm.eacm.objects.Filterable#filter()
      */
     public void filter() {
    	 // get filter group from the table, convert to RowFilters and apply
    	 FilterGroup fg = null;
    	 if (isPivoted()){
    		 fg = getColFilterGroup();
    	 }else{
    		 fg = getFilterGroup();
    	 }

    	 if (fg!=null && fg.getFilterItemCount()>0){
    		 int curCol = this.getSelectedColumn();
    		 if(curCol==-1){
    			 curCol=0;
    			 if(this.getColumnModel().getColumn(0).getWidth()==0){
    				 curCol++;
    			 }
    		 }

    		 clearSelection();
    		 TableColumnModel tcm = getColumnModel();

    		 if (filters!=null){
    			 Iterator<RowFilter<Object, Object>> itr = filters.listIterator();
    			 while (itr.hasNext()) {
    				 FIFilter fif = (FIFilter)itr.next();
    				 fif.dereference();
    			 }
    			 filters.clear(); // release original filter
    		 }
    		 // build RowFilters
    		 filters = new ArrayList<RowFilter<Object,Object>>(fg.getFilterItemCount());
    		 for (int i=0; i< fg.getFilterItemCount(); i++){
    			 FilterItem fi = fg.getFilterItem(i);
    			 String colkey = fi.getFilterKey();
    			 int colid = tcm.getColumnIndex(colkey);
    			 filters.add(new FIFilter(fi, convertColumnIndexToModel(colid)));//colid from the view
    		 }

    		 RowFilter<Object,Object> rf = RowFilter.andFilter(filters);

    		 if(getRowSorter() ==null){
    			 // it is needed for filter
		 		setRowSorter(new TableRowSorter<TableModel>(getModel()));
		 	 }
    		 
    		 ((TableRowSorter<?>)getRowSorter()).setRowFilter(rf);

    		 setFilterIcon();

    		 if (getRowCount() > 0) {
    			 if (getColumnCount() > curCol) {
    				 setRowSelectionInterval(0, 0);
    				 setColumnSelectionInterval(curCol, curCol);
    				 scrollToRow(0);
    			 }
    		 }
    	 }
     }

     /**
      * enable filter icon label in frame
      */
     public void setFilterIcon() {
    	 Component comp = this.getParent();
    	 while (comp != null){
    		 if (comp instanceof TabPanel || comp instanceof EACMFrame){
    			 break;
    		 }
    		 comp = comp.getParent();
    	 }
    	 if (comp instanceof EACMFrame){ // used for bookmark frame or navcart frame
    		 ((EACMFrame)comp).setFilter(isFiltered());
    	 }
    	 if (comp instanceof TabPanel){
    		 EACM.getEACM().setFilterStatus(isFiltered());
    	 }
     }

     private static class FIFilter extends RowFilter<Object,Object> {
         private int[] columns;
         private FilterItem filterItem;

         void dereference(){
        	 filterItem = null;
        	 columns = null;
         }
         FIFilter(FilterItem fi, int... columns) {
             this.columns = columns;
             filterItem = fi;
         }

         public boolean include(Entry<? extends Object,? extends Object> value){
             int count = value.getValueCount();
             if (columns.length > 0) {
                 for (int i = columns.length - 1; i >= 0; i--) {
                     int index = columns[i];
                     if (index < count) {
                         if (include(value, index)) {
                             return true;
                         }
                     }
                 }
             }
             else {
                 while (--count >= 0) {
                     if (include(value, count)) {
                         return true;
                     }
                 }
             }
             return false;
         }

         protected boolean include(
        		 Entry<? extends Object,? extends Object> value, int index) {
        	 boolean match = false;
        	 Object eanObj = value.getValue(index);
        	 //a new entityitem must not be filtered out
        	 if (eanObj instanceof EANEntity){
         		 EANEntity ent = (EANEntity) eanObj;
        		 if (ent.getEntityID() < 0) {
        			 return match;
        		 }
        	 }

        	 String strValue = value.getStringValue(index);
        	 String fivalue = filterItem.getValue();

        	 if(!((FilterGroup)filterItem.getParent()).isCaseSensitive()){
        		 strValue = strValue.toLowerCase();
        		 fivalue = fivalue.toLowerCase();
        	 }

        	 switch (filterItem.getCondition()) {
        	 case FilterItem.LIKE_COND :
        		 match = strValue.startsWith(fivalue);
        		 break;
        	 case FilterItem.NOT_LIKE_COND :
        		 match = !strValue.startsWith(fivalue);
        		 break;
        	 case FilterItem.CONTAINS_COND :
        		 match = strValue.indexOf(fivalue) != -1;
        		 break;
        	 case FilterItem.NOT_CONTAINS_COND :
        		 match = strValue.indexOf(fivalue) == -1;
        		 break;
        	 case FilterItem.EQUALS_COND :
        		 match = strValue.equals(fivalue);
        		 break;
        	 case FilterItem.NOT_EQUALS_COND :
        		 match = !strValue.equals(fivalue);
        		 break;
        	 case FilterItem.LTE_COND :
        		 match = strValue.compareTo(fivalue) <= 0;
        		 break;
        	 case FilterItem.GTE_COND :
        		 match = strValue.compareTo(fivalue) >= 0;
        		 break;
        	 default :
        		 break;
        	 }

        	 return match;
         }
     }

//=========================================================================
/*
 accessiblity
 the screen reader gets the accessibility from the renderer....
 for full integration we will need to update the renderers as well.
 */
	/**
     * updateContext
     *
     * @return AccessibleContext
     * @param _ac
     * @param _col
     * @param _row
     */
    public void updateContext(Accessible ac, int viewRowIndex, int viewColumnIndex) {
    	if (ac==null ||viewRowIndex < 0 || viewColumnIndex < 0) {
    		return;
    	}

    	// rst may have been updated, but the column model has not been updated yet
    	if(convertColumnIndexToModel(viewColumnIndex)>=getModel().getColumnCount()){
    		return;
    	}

     	if(convertRowIndexToModel(viewRowIndex)>=getModel().getRowCount()){
    		return;
    	}

    	String sAccessibleContext = getAccessibleCell(viewRowIndex,viewColumnIndex);
    	if (sAccessibleContext != null) {
  //		System.err.println("=*=*=*=*=*     screenReader.output for (" + viewRowIndex + ", " + viewColumnIndex + "):  " + sAccessibleContext + "     =*=*=*=*=*");
    		ac.getAccessibleContext().setAccessibleDescription(sAccessibleContext);
    	}
	}

//	 ""		 --> default
//	 ".ctab" --> cross table
//	 ".horz" --> horizontal
//	 ".mtrx" --> matrix
//	 ".vert" --> vertical
	/**
     * getContext
     * @return
     */
    protected String getContext() {
		return "";
	}

    /**
     * isAccessibleCellEditable
     *
     * @param _row
     * @param _col
     * @return
     */
    protected boolean isAccessibleCellEditable(int _row, int _col) {
		return false;
	}

    /**
     * isAccessibleCellLocked
     *
     * @param _row
     * @param _col
     * @return
     */
    protected boolean isAccessibleCellLocked(int _row, int _col) {
		return false;
	}

    /**
     * getAccessibleValueAt
     *
     * @param _row
     * @param _col
     * @return
     */
    protected  Object getAccessibleValueAt(int _row, int _col) {
		return getValueAt(_row,_col);
	}

    /**
     * getAccessibleColumnNameAt
     *
     * @param _col
     * @return
     */
    protected String getAccessibleColumnNameAt(int _col) {
		return getColumnName(_col);
	}

    /**
     * getAccessibleRowNameAt
     *
     * @param _row
     * @return
     */
    protected String getAccessibleRowNameAt(int _row) {
		return " " + _row;
	}

    /**
     * getAccessibleCell
     *
     * @param _row
     * @param _col
     * @return
     */
    private String getAccessibleCell(int viewrow, int viewcol) {
		boolean bEdit = false;
        boolean bLock = false;
        Object[] parms = null;
		bEdit = isAccessibleCellEditable(viewrow,viewcol);
		bLock = isAccessibleCellLocked(viewrow,viewcol);
		parms = new String[ACCESSIBLE_MAX];
		parms[ACCESSIBLE_EDIT_STATUS] = getAccessibleEditStatus(bEdit);
		parms[ACCESSIBLE_LOCK_STATUS] = bEdit ? getAccessibleLockStatus(bLock) : "";
		parms[ACCESSIBLE_ATTRIBUTE_TYPE] = getAccessibleAttributeType(viewrow,viewcol);
		parms[ACCESSIBLE_COLUMN_NAME] = getAccessibleColumnName(viewcol);
		parms[ACCESSIBLE_ROW_NAME] = getAccessibleRowName(viewrow);

		parms[ACCESSIBLE_VALUE] = getAccessibleValue(viewrow,viewcol);
		String cellkey = "accessible.cell" + getContext();
		return Utils.getResource(cellkey,parms);
	}

	/**
     * getAccessibleEditStatus
     * @param _bEdit
     * @return
     */
    private String getAccessibleEditStatus(boolean _bEdit) {
    	String key = "accessible.";
		if (_bEdit) {
			key +="editable";
		}else{
			key +="uneditable";
		}
		return Utils.getResource(key);
	}

	/**
     * getAccessibleLockStatus
     * @param _bLock
     * @return
     */
    private String getAccessibleLockStatus(boolean _bLock) {
       	String key = "accessible.";
		if (_bLock) {
			key +="locked";
		}else{
			key +="unlocked";
		}

		return	Utils.getResource(key);
	}

	/**
     * getAccessibleColumnName
     * @param _col
     * @return
     */
    private String getAccessibleColumnName(int _col) {
    	String namekey = "accessible.column.name" + getContext();
		return Utils.getResource(namekey) + " " + getAccessibleColumnNameAt(_col);
	}

	/**
     * getAccessibleRowName
     * @param viewrow
     * @return
     */
    private String getAccessibleRowName(int viewrow) {
    	String rowkey="accessible.row.name" + getContext();
		return Utils.getResource(rowkey) + " " + getAccessibleRowNameAt(convertRowIndexToModel(viewrow));
	}

	/**
     * getAccessibleValue
     * @param _row
     * @param _col
     * @return
     */
    private String getAccessibleValue(int _row, int _col) {
		Object o = getAccessibleValueAt(_row,_col);
		if (Routines.have(o)) {
			String namekey = "accessible.value.name" + getContext();
			return Utils.getResource(namekey) + " " + o.toString();
		}
		return Utils.getResource("accessible.value.null");
	}

	/**
     * getAccessibleAttributeType
     * @param _row
     * @param _col
     * @return
     */
    protected String getAccessibleAttributeType(int _row, int _col) {
		String sConstant = "accessible.editor.type.";
		EANMetaAttribute meta = getAccessibleMetaAttribute(_row,_col);
		if (meta != null) {
			String attType = meta.getAttributeType();
			if (attType != null) {
				if (attType.equalsIgnoreCase("T")) {
					if (meta.isDate()) {
						return Utils.getResource(sConstant+"DATE");
					} else if (meta.isTime()) {
						return Utils.getResource(sConstant+"TIME");
					} else {
						return Utils.getResource(sConstant+attType);
					}
				}
				return Utils.getResource(sConstant+attType);
			}
		}
		return "";
	}

	/**
     * getAccessibleMetaAttribute
     * @param _row
     * @param _col
     * @return
     */
    protected EANMetaAttribute getAccessibleMetaAttribute(int _row, int _col) {
		return null;
	}

	/**
     * initAccessibility
     * @param _s
     */
    private void initAccessibility(String _s) {
			AccessibleContext ac = getAccessibleContext();
			if (ac != null) {
				if (_s == null) {
					ac.setAccessibleName(null);
					ac.setAccessibleDescription(null);
				} else {
					String strAccessible = Utils.getResource(_s);
					ac.setAccessibleName(strAccessible);
					ac.setAccessibleDescription(strAccessible);
				}
			}
	}
}
