//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.table;



import com.ibm.eacm.objects.*;
import com.ibm.eacm.objects.OutOfRangeException;
import com.ibm.eacm.objects.Routines;
import com.ibm.eacm.rend.LabelRenderer;

import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;

import java.awt.*;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.RowSorter.SortKey;
import javax.swing.table.*;

/**
 *  this represents EntityGroup as a table
 * @author Wendy Stimpson
 */
// $Log: EntityGroupTable.java,v $
// Revision 1.2  2013/07/29 18:38:58  wendy
// Turn off autosort, update classifications after edit and resize cells
//
// Revision 1.1  2012/09/27 19:39:12  wendy
// Initial code
//
public class EntityGroupTable extends RSTTable {
	private static final long serialVersionUID = 1L;

	private EntityGroup myGroup = null;
    private LabelRenderer foundRenderer = null;

	/**
	 * allow derived classes to provide a different rsttablemodel
	 * @return
	 */
	protected RSTTableModel createTableModel(){
		return new RSTTableModel(this);
	}

    /**
     * use specified rst, not table from group
     * @param eg
     * @param rst
     */
    public EntityGroupTable(EntityGroup eg, RowSelectableTable rst) {
        super(rst, eg.getProfile());
        myGroup = eg;

        init();
    }
    /**
     * @param eg
     */
    public EntityGroupTable(EntityGroup eg) {
        super(eg.getEntityGroupTable(), eg.getProfile());
        myGroup = eg;

        init();
    }

    /**
     * init
     */
    protected void init() {
    	super.init();

        setSelectionMode();
        // must set this after changing selection mode
        if (getRowCount() > 0) {
            setRowSelectionInterval(0, 0);
        }
        if (getColumnCount() > 0) {
            setColumnSelectionInterval(0, 0);
        }

        foundRenderer = new LabelRenderer();
        foundRenderer.setFocusBorderKeys(FOUND_FOCUS_BORDER_KEY, FOUND_BORDER_KEY);
    }
    /**
     * get the accessibility resource key
     * @return
     */
    protected String getAccessibilityKey() {
    	return "accessible.navTable";
    }
    /* (non-Javadoc)
     * release memory
     * @see com.ibm.eacm.table.RSTTable2#dereference()
     */
    public void dereference(){
    	super.dereference();

    	MouseListener[] ml = this.getMouseListeners();
    	for (int i=0; i<ml.length; i++){
    		removeMouseListener(ml[i]);
    		ml[i]=null;
    	}
    	ml = null;
    	myGroup = null;
    	foundRenderer.dereference();
    	foundRenderer=null;
    }

    /* (non-Javadoc)
     * @see com.ibm.eacm.table.RSTTable2#setSortKeys()
     */
    protected void setSortKeys(){
		List<? extends SortKey> origsortKeys = null;
		if(getRowSorter() != null){
			origsortKeys = ((TableRowSorter<?>)getRowSorter()).getSortKeys();
		}
    	if (origsortKeys !=null && origsortKeys.size()>0){
    		return; // keep the sort already specified
    	}
    	// must be done after the columns are setup in the model
        //The precedence of the columns in the sort is indicated by the order of the sort keys in the sort key list.
        List <RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>();
        int sortcols = Math.min(4, this.getColumnCount());
        for (int i=0;i<sortcols; i++){
        	sortKeys.add(new RowSorter.SortKey(i, SortOrder.ASCENDING));
        }
        setSortKeys(sortKeys);
    }

    private void setSelectionMode() {
        EntityList eList = myGroup.getEntityList();
        if (eList != null) {
        	EANActionItem ean = eList.getParentActionItem();
        	if (ean instanceof NavActionItem) {
        		if(( (NavActionItem) ean).isSingleSelect()) {
        			setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        		} else {
        			setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        		}
        	}
        }
    }

    /**
     * getCellRenderer
     *
     * @param r
     * @param c
     * @return
     */
    public TableCellRenderer getCellRenderer(int r, int c) {
        if (isFound(r, c)) {
            return foundRenderer;
        }
        return super.getCellRenderer(r, c);
    }

    /**
     * get EntityGroup used by this table
     *
     */
    public EntityGroup getEntityGroup() {
        return myGroup;
    }

    /**
     * refresh the model with this entitygroup
     * @param eg
     */
    public void refreshModel(EntityGroup eg){
    	myGroup = eg;
		updateModel(myGroup.getEntityGroupTable(), getProfile());
    }
    /**
     * remove selected items from the table and the group
     * @return count of items left
     */
    public int removeSelectedItems(){
    	int[] rows = getSelectedRows();
    	for (int i = rows.length-1; i >=0; --i) {
    		int row = this.convertRowIndexToModel(rows[i]);
    		EntityItem ei = (EntityItem) ((RSTTableModel)getModel()).getRowEAN(row);
    		myGroup.removeEntityItem(ei);
    	}

    	updateModel(myGroup.getEntityGroupTable(), getProfile());

    	return myGroup.getEntityItemCount();
    }

    public EntityItem[] getAllEntityItems(boolean bnew, boolean maxEx) throws OutOfRangeException {
        int ii = this.getRowCount();
        EntityItem[] out = null;
        if (ii > 0) {
        	if (maxEx && ii > getRecordLimit(getProfile())) {
        		//msg12003.0 = \n{0} selectable items exceeds the maximum number.\nPlease select {1} or less items.
        		throw new OutOfRangeException(Utils.getResource("msg12003.0",""+ii,""+getRecordLimit(getProfile())));
        	}

        	out = new EntityItem[ii];
        	for (int i = 0; i < ii; ++i) {
        		int row = this.convertRowIndexToModel(i);
        		EntityItem ei = (EntityItem) ((RSTTableModel)getModel()).getRowEAN(row);
        		if (bnew) {
        			try {
        				out[i] = new EntityItem(ei);
        			} catch (MiddlewareRequestException mre) {
        				mre.printStackTrace();
        			}
        		} else {
        			out[i] = ei;
        		}
        	}
        }
        return out;
    }
    /**
     * getParentAction
     * @return
     */
    public EANActionItem getParentAction() {
        EntityGroup eg = getEntityGroup();
        if (eg != null) {
            EntityList list = eg.getEntityList();
            if (list != null) {
                return list.getParentActionItem();
            }
        }
        return null;
    }
    /**
     * getCurrentEntityItem - based on row and column
     * used to support getting entityhistory when row has entity - relator - entity
     */
    public EntityItem getCurrentEntityItem() {
        int row = getSelectedRow();
        EntityItem ei = null;

        int col = getSelectedColumn();
        if (isValidCell(row, col)) {
            EANAttribute att = getAttribute(row, col);
            ei = att.getEntityItem();
        }
        return ei;
    }

    /**
     * getUIPrefKey
     *
     * @return
     */
    public String getUIPrefKey() {
        EntityGroup eg = getEntityGroup();
        return "EGT" + eg.getKey();
    }

    /**
     * @see javax.swing.JComponent#getToolTipText(java.awt.event.MouseEvent)
     */
    public String getToolTipText(MouseEvent me) {
        Point pt = me.getPoint();
        int row = rowAtPoint(pt);
        int col = columnAtPoint(pt);
        if (isValidCell(row, col)) {
            EANAttribute att = getAttribute(row, col);
            if (att instanceof MultiFlagAttribute || att instanceof LongTextAttribute ||
            		att instanceof XMLAttribute) {
                return Routines.getDisplayString(getValueAt(row, col, true).toString(), true);
            }
        }
        return null;
    }

    /**
     * select rows for specified keys
     * @param keys
     */
    public void highlight(String[] keys) {
    	boolean bClear = true;
    	if (keys != null) {
    		int rows[] = getRowIndexes(keys);
    		for (int i = 0; i < rows.length; ++i) {
    			if (rows[i] >= 0) {
    				int row = this.convertRowIndexToView(rows[i]);
    				if (bClear) {
    					bClear = false;
    					clearSelection();
    					scrollToRow(row);
    				}
    				addRowSelectionInterval(row, row);
    			}
    		}
    	}
    }

    public String export() {
    	EntityGroup eg = getEntityGroup();
    	EntityItem[] ei = null;
    	int ii = -1;
    	if (eg == null || eg.getEntityItemCount() == 0) {
    		return null;
    	}
    	ii = eg.getEntityItemCount();
    	ei = new EntityItem[ii];
    	for (int i = 0; i < ii; ++i) {
    		ei[i] = eg.getEntityItem(i);
    	}
    	return export(ei, true);
    }
    /**
     * export
     * @param ei
     * @param includeHeaders
     * @return
     */
    protected String export(EntityItem[] ei, boolean includeHeaders) {
    	int rr = ei.length;
    	int cc = getColumnCount();
    	StringBuffer out = new StringBuffer();
    	if (includeHeaders) {
    		out.append('"');
    		out.append("EntityType");
    		out.append('"');
    		out.append(",");
    		out.append('"');
    		out.append("EntityID");
    		out.append('"');
    		out.append(",");
    		for (int c = 0; c < cc; ++c) {
    			out.append('"');
    			out.append(getColumnName(c));
    			out.append('"');
    			if (c < (cc - 1)) {
    				out.append(",");

    			} else {
    				out.append(RETURN);
    			}
    		}
    	}
    	for (int r = 0; r < rr; ++r) {
    		if (includeHeaders) {
    			out.append('"');
    			out.append(ei[r].getEntityType());
    			out.append('"');
    			out.append(",");
    			out.append('"');
    			out.append(ei[r].getEntityID());
    			out.append('"');
    			out.append(",");
    		}
    		for (int c = 0; c < cc; ++c) {
    			out.append('"');
    			out.append(getValueAt(ei[r].getKey(), c));
    			out.append('"');
    			if (c < (cc - 1)) {
    				out.append(",");

    			} else {
    				out.append(RETURN);
    			}
    		}
    	}
    	return out.toString();
    }
 }
