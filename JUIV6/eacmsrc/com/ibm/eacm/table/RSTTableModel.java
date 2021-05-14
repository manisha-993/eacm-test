
//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.table;

import java.awt.Component;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Date;

import com.ibm.eacm.*;
import com.ibm.eacm.mw.RMIMgr;
import com.ibm.eacm.objects.*;

import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;

import javax.swing.event.*;

/**
 *  this represents a RowSelectableTable as a java tablemodel
 * @author Wendy Stimpson
 */
public class RSTTableModel extends javax.swing.table.AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private RowSelectableTable rst = null;
	private int maxColWidth = 0;
	private FilterGroup filterGroup, colFilterGroup;
	private EANFoundation[] eanWithImplicators = null;
	protected RSTTable parentTable = null; // used to get at profile

	/**
	 * @param table
	 */
	public RSTTableModel(RSTTable table){
		parentTable = table;
	}
	/**
	 * create a matrix tablemodel with a max column name length
	 * only used by crosstable
	 * @param maxlen
	 */
	public RSTTableModel(RSTTable table, int maxlen){
		parentTable = table;
		maxColWidth = maxlen;
	}

	/**
     * release memory
     */
    public void dereference() {
    	if (rst!=null){
    		rst.dereference();
    		rst = null;
		}
    	filterGroup=null;
    	colFilterGroup=null;
		eanWithImplicators = null;
    	parentTable = null;
    	derefListeners();
	}

    /**
     * remove listeners, need way to do this without deref the rst
     */
    protected void derefListeners(){
    	TableModelListener[] tml = getTableModelListeners();
    	if(tml !=null){
    		for (int i=0; i<tml.length; i++){
    			removeTableModelListener(tml[i]);
    		}
    	}
    }

	/**
	 * truncate name if needed
	 * @param name
	 * @return
	 */
	public String getTruncName(String name){
		if (name != null && maxColWidth !=0){
			name = Routines.truncate(name, maxColWidth);
		}

		return name;
	}
	/**
	 * used by derived classes and must go thru rst for edit on whereused
     * get RowSelectableTable
     * @return
     */
    public RowSelectableTable getRSTable() {
		return rst;
	}

	/**
     * updateModel with new RowSelectableTable
     * @param _ctm
     */
    protected void updateModel(RowSelectableTable _ctm) {
     	if (rst!=null){
     		rst.dereference();
    		eanWithImplicators = null;
    	}

		rst = _ctm;
		if(rst!=null){
			eanWithImplicators = rst.getTableRowsAsArray();
		}

	    // All row data in the table has changed, listeners should discard any state
        //TableModelEvent(this, 0, Integer.MAX_VALUE, TableModelEvent.ALL_COLUMNS, TableModelEvent.UPDATE)
        fireTableChanged(new TableModelEvent(this,TableModelEvent.HEADER_ROW));
	}
	/**
     * get the key used by the EANFoundation with implicators at that row
     * @param modelRowIndex - must be model index
     * @return
     */
    protected String getRowKey(int modelRowIndex) {
    	return eanWithImplicators[modelRowIndex].getKey();
    	//return rst.getRow(modelRowIndex).getKey(); this gets item without implicator
    	// GENAREASELECTION instead of FEATURE:GENAREASELECTION:C
	}

    /**
     * @param _r
     * @param _c
     * @param _longDesc
     * @return
     */
    public Object getValueAt(int _r, int _c, boolean _longDesc) {
		if (rst != null) {
			return rst.get(_r, _c, _longDesc);
		}
		return null;
    }
    /**
     * find row with this key
     * @param _s
     * @return
     */
    public int getModelRowIndex(String _s) {
    	 for (int i = 0; i < eanWithImplicators.length; ++i) {
            if (eanWithImplicators[i].getKey().equals(_s)) {
                return i;
            }
        }
        return -1;
    }
	/**
	 * get row count from pdh model
     * @see javax.swing.table.TableModel#getRowCount()
     */
    public int getRowCount() {
		if (rst != null) {
			return rst.getRowCount();
		}
		return 0;
	}

	/**
	 * get column count from pdh model
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    public int getColumnCount() {
		if (rst != null) {
			return rst.getColumnCount();
		}
		return 0;
	}

	/**
	 * get column index for this string
	 * @param _s
	 * @return
	 */
	public int getColumnIndex(String _s) {
		return rst.getColumnIndex(_s);
	}
	/**
	 * get column name from pdh model
     * @see javax.swing.table.TableModel#getColumnName(int)
     */
    public String getColumnName(int _i) {
		if (rst != null) {
			return rst.getColumnHeader(_i);
		}
		return null;
	}

	/**
	 * get class from pdh model
     * @see javax.swing.table.TableModel#getColumnClass(int)
     *
     */
    public Class<?> getColumnClass(int _i) {
		if (rst != null && rst.getColumnCount()>_i) {
			return rst.getColumnClass(_i);
		}
		return String.class;
	}

	/**
	 * get the value from the pdh model
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    public Object getValueAt(int _r, int _c) {
		if (rst != null) {
			return rst.get(_r,_c);
		}
		return null;
	}

    /**
     * dump
     * @param _sb
     * @param _brief
     * @return
     */
    protected String dump(boolean _brief) {
    	StringBuffer sb = new StringBuffer();
		sb.append("TableModel.getRowCount(): " + getRowCount() + Utils.NEWLINE);
		sb.append("TableModel.getColumnCount(): " + getColumnCount() + Utils.NEWLINE);
		if (!_brief) {
			for (int r=0;r<getRowCount();++r) {
				sb.append("Row(" + r + "): ");
				for (int c=0;c<getColumnCount();++c) {
					if(sb.length()>0){
						sb.append(", ");
					}
					Object o = getValueAt(r,c);
					if (o != null) {
						sb.append(o.toString());
					}
				}
				sb.append(Utils.NEWLINE);
			}
		}
		return sb.toString();
	}

    /**
     * get the EANFoundation for this row - no implicator
     * @param _row
     * @return
     */
    protected EANFoundation getRowEAN(int _row) {
        return rst.getRow(_row);
    }

    /**
     * get the EANFoundation for this key - no implicator
     * @param _key
     * @return
     */
    public EANFoundation getEANFoundationByKey(String _key) {
    	if (_key!=null){
    		return rst.getRow(_key);
    	}else{
    		System.err.println("##RSTableModel2.getRowByKey _key is null!!");
    	}
        return null;
    }

    /**
     * @param _rowKey
     * @param _colKey
     * @return
     */
    public String getHelpText(String _rowKey, String _colKey) {
        return rst.getHelp(rst.getRowIndex(_rowKey), rst.getColumnIndex(_colKey));
    }

    /**
     * get the key for this column from the RowSelectableTabl
     * @param _i
     * @return
     */
    public String getColumnKey(int _i) {
    	return rst.getColumnKey(_i);
    }

    /**
     * get the EANFoundation in this cell
     * @param _r
     * @param _c
     * @return
     */
    protected EANFoundation getEANObject(int _r, int _c) {
    	return rst.getEANObject(_r, _c);
    }

    /**
     * isLongDescriptionEnabled
     * @return
     */
    protected boolean isLongDescriptionEnabled() {
        return rst.isLongDescriptionEnabled();
    }
    /**
     * setLongDescription
     * @param _b
     */
    protected void setLongDescription(boolean _b) {
        rst.setLongDescription(_b);
    }
    /**
     * get the row index for this key
     * @param key
     * @return
     */
    public int getRowIndex(String key) {
    	return rst.getRowIndex(key);
    }

    /**
     * get the remote connection
     * @return
     * @throws RemoteException 
     */
    protected RemoteDatabaseInterface ro() throws RemoteException  {
    	return RMIMgr.getRmiMgr().getRemoteDatabaseInterface();
    }

    /**
     *
     * this is called on the event thread after rst has been modified for things like link or removelink
     */
    protected void updatedModel() {
    	// All row data in the table has changed, listeners should discard any state
    	//TableModelEvent(this, 0, Integer.MAX_VALUE, TableModelEvent.ALL_COLUMNS, TableModelEvent.UPDATE)
    	fireTableChanged(new TableModelEvent(this));
    }

    /**
     * get the title for the data in this table
     * @return
     */
    public String getTableTitle() {
        return rst.getTableTitle();
    }

    /**
     * needed by editors
     * @return
     */
    public boolean isDynaTable() {
        return rst.isDynaTable();
    }

    /**
     * get the action items for this key
     * @param _key
     * @return
     */
    protected EANActionItem[] getActionItemsAsArray(Profile prof,String _key) {
        return getActionItemsAsArray(prof,rst.getRowIndex(_key));
    }

    /**
     * get ActionItems for this row
     * @param _row
     * @return
     */
    protected EANActionItem[] getActionItemsAsArray(Profile prof, int _row) {
        Object[] ean = null;
        try {
            ean = rst.getActionItemsAsArray(_row, null, ro(), prof);
        } catch (Exception exc) {
        	if(RMIMgr.shouldTryReconnect(exc) &&	// try to reconnect
        			RMIMgr.getRmiMgr().reconnectMain()) {
        		try{
        			 ean = rst.getActionItemsAsArray(_row, null, ro(), prof);
        		}catch(Exception e){
        			if(RMIMgr.shouldPromptUser(exc)){
        				if (com.ibm.eacm.ui.UI.showMWExcPrompt(null, exc) == Utils.RETRY) {
        				    ean = getActionItemsAsArray(prof,_row);
        				}// else user decide to ignore or exit
        			}else{
        				com.ibm.eacm.ui.UI.showException(null,exc, "mw.err-title");
        			}
        		}
        	} else{ // show user msg and ask what to do
    			if(RMIMgr.shouldPromptUser(exc)){
    				if (com.ibm.eacm.ui.UI.showMWExcPrompt(null, exc) == Utils.RETRY) {
    				    ean = getActionItemsAsArray(prof,_row);
    				}// else user decide to ignore or exit
    			}else{
    				com.ibm.eacm.ui.UI.showException(null,exc, "mw.err-title");
    			}
    		}
        }

        if (ean != null) {
            int ii = ean.length;
            EANActionItem[] out = new EANActionItem[ii];
            for (int i = 0; i < ii; ++i) {
                out[i] = (EANActionItem) ean[i];
            }
            return out;
        }
        return null;
    }

//  ====================================================================
//  Filterable support - filter in the view.. do not set it in rst
	/**
     * getFilterGroup
     * @return
     */
    protected FilterGroup getFilterGroup(Profile prof) {
    	FilterGroup fg = null;
		if (rst != null) {
			fg = filterGroup;//rst.getFilterGroup();
			if (fg == null) {
				try {
					fg = new FilterGroup(null,prof,rst);
					setFilterGroup(fg);
				} catch (MiddlewareRequestException _mre) {
					_mre.printStackTrace();
				}
			}
		}
		return fg;
	}

	/**
     * setFilterGroup but do not run it
     * @param _group
     */
    protected void setFilterGroup(FilterGroup _group) {
		if (rst != null) {
			//rst.setFilterGroup(_group);
			filterGroup = _group;
		}
	}
    protected FilterGroup getColFilterGroup(Profile prof) {
    	FilterGroup fg = null;
        if (rst != null) {
            fg = colFilterGroup;
            if (fg == null) {
                try {
                    fg = new FilterGroup(null, prof, rst,true);
                    setColFilterGroup(fg);
                } catch (MiddlewareRequestException _mre) {
                    _mre.printStackTrace();
                }
            }
        }
        return fg;
    }

    /**
     * setColFilterGroup
     * @param _group
     */
    protected void setColFilterGroup(FilterGroup _group) {
        if (rst != null) {
        	colFilterGroup = _group;
        }
    }
	/**
     * refresh
     */
    protected void refresh() {
    	refresh(true);
    }
    /**
     * @param updateAllRows
     */
    protected void refresh(boolean updateAllRows) {
    	FilterGroup fg = rst.getFilterGroup();
    	FilterGroup colfg = rst.getColFilterGroup();
    	rst.setFilterGroup(null);
    	rst.setColFilterGroup(null);
		rst.refresh(); // this does any filtering too if filtering was previously done in the rst
		eanWithImplicators = rst.getTableRowsAsArray();
		if(updateAllRows){
			updatedModel(); // updating all rows will force a sort if any sortkeys exist
		}else{
			//vertical edit must not sort all rows, so do update one row at a time
			for(int r=0; r<getRowCount(); r++){
				fireTableRowsUpdated(r,r);
			}
		}
		rst.setFilterGroup(fg);
    	rst.setColFilterGroup(colfg);
	}
    
    /**
     * rows were added, duplicate uses this
     * IN4426481 - Function "Duplicate" 
     * @param firstNewRow
     */
    protected void rowsAdded(int firstNewRow) {
    	FilterGroup fg = rst.getFilterGroup();
    	FilterGroup colfg = rst.getColFilterGroup();
    	rst.setFilterGroup(null);
    	rst.setColFilterGroup(null);
		rst.refresh(); // this does any filtering too if filtering was previously done in the rst
		eanWithImplicators = rst.getTableRowsAsArray();

		fireTableRowsInserted(firstNewRow, getRowCount()-1); 

		rst.setFilterGroup(fg);
    	rst.setColFilterGroup(colfg);
	}

    /**
     * do any uncommitted changes exist
     * @return
     */
    public boolean hasChanges() {
    	if (rst!=null){
    		return rst.hasChanges();
    	}
    	return false;
    }

    /* (non-Javadoc)
     * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
     *
     */
    public boolean isCellEditable(int _r, int _c) {
		// look at entity and attribute information to determine if this can be edited
    	return rst.isEditable(_r, _c);
    }
    /**
     * does user have a lock on this entity?
     * @param _r
     * @param _c
     * @return
     */
    public boolean isCellLocked(int _r, int _c, Profile prof) {
        return hasLock( _r, _c, prof);
    }

    /**
     * this must be separate because 1 is subtracted from col in matrix
     * @param _r
     * @param _c
     * @return
     */
    private boolean hasLock(int _r, int _c, Profile prof) {
        return rst.hasLock(_r, _c, EACM.getEACM().getLockMgr().getLockOwner(prof), prof);
    }

    /**
     * get the lock on this entity if not already held
     * @param _r
     * @param _c
     * @return
     */
    public boolean getCellLock(int _r, int _c, Profile prof) {
    	if (!hasLock(_r,_c, prof)){ // dont use overridden method
    		boolean ok = getCellSoftLock(_r, _c, prof);
    		if(!ok){
        		LockGroup lockGroup = rst.getLockGroup(_r, _c);  // dont use overridden method
    			if (lockGroup != null) {
    				com.ibm.eacm.ui.UI.showErrorMessage(null,lockGroup.toString());
    			} else{
    				// if this is a VEEdit, some of the entities may not be editable
    				EANFoundation ean = rst.getEANObject(_r, _c);
    				if(ean instanceof EANAttribute){
    					ean = ean.getParent();
    				}
    				if(ean instanceof EntityItem){
    					if(((EntityItem)ean).isVEEdit() && !((EntityItem)ean).isEntityGroupEditable()){
    						return ok;
    					}
    				}

    				//lock.errmsg = Error getting lock for {0}, contact support.
    				// must have been an exception
    				com.ibm.eacm.ui.UI.showErrorMessage(null,
    						Utils.getResource("lock.errmsg",getValueAt(_r,_c)));
    			}
    		}

			return ok;
    	}else{ // already have the lock
    		return true;
    	}
    }
    /**
     * get the lock group
     * @param r
     * @param c
     * @return
     */
    public LockGroup getLockGroup(int r, int c){
    	return rst.getLockGroup(r, c);
    }

	/**
	 * get around col0 adjust done in derived mtrxtablemodel
	 * @param r
	 * @param c
	 * @return
	 */
	protected EANFoundation getCellValue(int r, int c){
		return getEANObject(r, c);
	}

    /**
     * get the softlock for this cell in the pdh
     * @param _r
     * @param _c
     * @return
     */
	private boolean getCellSoftLock(int _r, int _c, Profile prof) {
		String _time = new Date().toString();
		LockList ll =  EACM.getEACM().getLockMgr().getLockList(prof,true);
		EntityItem lockOwnerEI = EACM.getEACM().getLockMgr().getLockOwner(prof); // use hashtable if possible
		boolean out = false;
		try {
			out = rst.isLocked(_r, _c, ro(), null, ll, prof, lockOwnerEI, LockGroup.LOCK_NORMAL, _time, true);
			Utils.monitor("lock", ll);
		} catch (Exception e) {
			e.printStackTrace();
		} 
    	return out;
    }

    /**
     * Unlock all rows and columns in the rst
     *
     */
    public void unlockTable(Profile prof){
        LockList ll =EACM.getEACM().getLockMgr().getExistingLockList(prof);
        if (ll != null) {
        	EntityItem lockOwnerEI = EACM.getEACM().getLockMgr().getLockOwner(prof);
        	if (lockOwnerEI!=null){
        		try{
        			rst.unlock(ro(), null, ll, prof, lockOwnerEI, LockGroup.LOCK_NORMAL);
        			Utils.monitor("unlock", ll);
        		} catch (Exception e) {
        			e.printStackTrace();
        		} 
        	}
        }
    }

    /**
     * - no implicator
     * @param _key
     * @return
     */
    public EANFoundation getRowByKey(String _key) {
    	if (_key!=null){
    		return rst.getRow(_key);
    	}else{
    		System.err.println("##RSTableModel.getRowByKey _key is null!!");
    		Thread.dumpStack();
    	}
        return null;
    }

    protected void commit(Component comp)
    throws EANBusinessRuleException, MiddlewareBusinessRuleException,
    RemoteException, MiddlewareException, MiddlewareRequestException,
    MiddlewareShutdownInProgressException, SQLException
    {
    	try {
    		rst.commit(ro());
    	} catch (MiddlewareSFRuleException _msfre) {
    		_msfre.printStackTrace();
    		throw _msfre;
    	} catch (MiddlewareBusinessRuleException _mbre) {
    		_mbre.printStackTrace();
    		throw _mbre;
    	} catch (EANBusinessRuleException _bre) {
    		_bre.printStackTrace();
    		throw _bre;
    	} catch(Exception exc){
    		if(RMIMgr.shouldTryReconnect(exc)){	// try to reconnect
    			if (RMIMgr.getRmiMgr().reconnectMain()) {
    				try{
    					rst.commit(ro());
    				}catch (MiddlewareSFRuleException _msfre) {
    					_msfre.printStackTrace();
    					throw _msfre;
    				} catch (MiddlewareBusinessRuleException _mbre) {
    					_mbre.printStackTrace();
    					throw _mbre;
    				} catch (EANBusinessRuleException _bre) {
    					_bre.printStackTrace();
    					throw _bre;
    				} catch(Exception e){
    					if(RMIMgr.shouldPromptUser(e)){// show user msg and ask what to do
    						if (com.ibm.eacm.ui.UI.showMWExcPrompt(comp, e) == Utils.RETRY) {
    							commit(comp);
    						}
    					}else{
    						com.ibm.eacm.ui.UI.showException(comp,e, "mw.err-title");
    					}
    				}
    			}else{	// reconnect failed
    				com.ibm.eacm.ui.UI.showException(comp,exc, "mw.err-title");
    			}
    		}else{ // show user msg and ask what to do
    			if(RMIMgr.shouldPromptUser(exc)){
    				if (com.ibm.eacm.ui.UI.showMWExcPrompt(comp, exc) == Utils.RETRY) {
    					commit(comp);
    				}// else user decide to ignore or exit
    			}else{
    				com.ibm.eacm.ui.UI.showException(comp,exc, "mw.err-title");
    			}
    		}
    	}
    }

    // not tested yet
    protected void addColumn(EntityItem[] _ei) {
        rst.addColumn(_ei);
// do this after adding columns to tablecolmodel?        updatedModel(); //23551
    }

    protected void addRow(){
    	rst.addRow();
    	this.updatedModel();
    }
    protected Class<?> getRowClass(int _row) {
        return getRSTable().getRowClass(_row);
    }
    /**
     * called by rollbacksingle - roll back a single attribute
     * @param row
     * @param col
     */
    protected void rollback(int row, int col) {
    	getRSTable().rollback(row, col);
    }
    protected void rollback() {
        getRSTable().rollback();
        updatedModel();
    }
    /**
     * rollback an entire row, usually an entity
     * @param row
     */
    protected void rollbackRow(int row) {
        getRSTable().rollback(row);
        //do this in caller, after all rollbacks are done updatedModel();
    }
    /**
     * remove this row from the rst
     * @param mdlrow
     */
    protected void removeRow(int mdlrow){
    	rst.removeRow(mdlrow);
//    	do this in caller, after all are done updatedModel();
    }

    protected boolean isChild(int r, int c) {
    	if (c == 0) {
    		return getRSTable().isChildAttribute(r, c);
    	}
    	return false;
    }
    protected boolean isParent(int r, int c) {
    	if (c == 0) {
    		return getRSTable().isParentAttribute(r, c);
    	}
    	return false;
    }
	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object, int, int)
	 */
	public void setValueAt(Object aValue, int r, int c) {
		try {
			getRSTable().put(r, c, aValue);
			// notify listeners that table has changed
			fireTableCellUpdated(r, c);
		} catch (final Exception bre) {
			// allow editor to close and then display the error
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					com.ibm.eacm.ui.UI.showException(null,bre);
				}
			});
		}
	}
}
