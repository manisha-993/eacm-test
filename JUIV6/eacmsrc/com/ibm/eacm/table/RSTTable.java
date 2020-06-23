//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.table;

import com.ibm.eacm.EACM;
import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.editor.*;
import com.ibm.eacm.objects.*;

import com.ibm.eacm.preference.BehaviorPref;
import com.ibm.eacm.rend.RowHeaderRenderer;
import com.ibm.eacm.ui.UI;


import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.Profile;

import java.awt.Event;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Point;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.RowSorter.SortKey;
import javax.swing.event.ChangeEvent;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;

import javax.swing.plaf.ToolTipUI;
import javax.swing.table.*;

import javax.swing.*;

/**
 *  this represents a RowSelectableTable as a Java Table
 * @author Wendy Stimpson
 */
//$Log: RSTTable.java,v $
//Revision 1.19  2013/11/07 18:09:39  wendy
//Add FillCopyEntity action
//
//Revision 1.18  2013/10/23 19:32:37  wendy
//IN4426481 - Function "Duplicate" - user wants new rows at bottom
//
//Revision 1.17  2013/10/17 15:03:24  wendy
//IN4426481 - EACM error after sorting out the fields
//
//Revision 1.16  2013/09/19 22:00:41  wendy
//control sort when a row is updated
//
//Revision 1.15  2013/09/18 18:26:59  wendy
//IN4323492 - make sure string dataflavor exists on clipboard
//
//Revision 1.14  2013/09/17 21:39:30  wendy
//check sortkeys size
//
//Revision 1.13  2013/09/17 13:16:01  wendy
//resize for sortorder after updating model
//
//Revision 1.12  2013/09/13 18:32:38  wendy
//fix null ptr after found used - IN4311520
//
//Revision 1.11  2013/08/21 15:34:10  wendy
//keep column in view after sort
//
//Revision 1.10  2013/08/14 16:55:28  wendy
//change paste to always use cell listener - needed to support typing after paste
//
//Revision 1.9  2013/07/31 17:10:29  wendy
//make sure rowsorter is not null before using it
//
//Revision 1.8  2013/07/29 18:38:57  wendy
//Turn off autosort, update classifications after edit and resize cells
//
//Revision 1.7  2013/07/26 19:46:27  wendy
//paste - split string data on clipboard into multiple cells if possible
//
//Revision 1.6  2013/07/26 17:28:02  wendy
//add method to get single row index for key
//
//Revision 1.5  2013/06/19 21:16:16  wendy
//Prevent duplicate error msg dialogs for text edits
//
//Revision 1.4  2013/05/08 18:11:05  wendy
//restore individual selected rows
//
//Revision 1.3  2013/05/08 15:40:00  wendy
//allow paste of multiple eandataflavor into single column
//
//Revision 1.2  2013/05/01 18:35:14  wendy
//perf updates for large amt of data
//
//Revision 1.1  2012/09/27 19:39:12  wendy
//Initial code
//
public abstract class RSTTable extends BaseTable implements Findable, HistoryInterface
{
	private static final long serialVersionUID = 1L;
	private static final int MAXIMUM_EDIT_ROW = 250;

	private RSTTableModel tm = null;
	private Profile profile = null;
	private int replaceRow=-1;
//	row header used in matrix and crosstables
	// used for freeze in horiz editor too
	private JList rowHeaderList = null;
	private RSTHeaderListModel headerListModel = null;
	protected RowHeaderRenderer rrend = null;
	protected RowHeaderLabel rowHeaderLabel = null;

	private Set<String> foundSet = new HashSet<String>();  // key is rowkey+columnkey

	protected static int getRecordLimit(Profile prof) {
		if (prof != null) {
//			System.out.println("profile record limit: " + prof.getRecordLimit());
			return Math.max(prof.getRecordLimit(),MAXIMUM_EDIT_ROW);
		}
		return MAXIMUM_EDIT_ROW;
	}
	/**
	 * allow derived classes to provide a different rsttablemodel
	 * @return
	 */
	protected RSTTableModel createTableModel(){
		return new RSTTableModel(this);
	}
	/**
	 * get profile for this table
	 * @return
	 */
	public Profile getProfile() {
		return profile;
	}
	/**
	 * change the tablemodel, but reuse the rst
	 * used for pivot
	 * @param dataModel
	 */
	public void setRSTModel(RSTTableModel dataModel, Profile prof) {
		setModel(dataModel);
		RowSelectableTable rst = getRSTable(); // get current RST
		tm = dataModel;
		profile = prof;
		updateModel(rst, prof);// use the new model with the current RST
	}

	/**
	 * constructor
	 * @param table
	 */
	public RSTTable(RowSelectableTable table, Profile prof) {
		tm = createTableModel();
		setModel(tm);
		profile = prof;

		//derived class must call this init();
		if (table!=null){
			updateModel(table, prof);
		}

		//    sorter.sort();
	}


	/* (non-Javadoc)
	 * @see javax.swing.JTable#sorterChanged(javax.swing.event.RowSorterEvent)
	 */
	public void sorterChanged(RowSorterEvent e) {
		super.sorterChanged(e);
		if (e.getType() == RowSorterEvent.Type.SORTED) {
			int col = 0;
			List<? extends SortKey> curSortKeys = getRowSorter().getSortKeys();
	    	if(curSortKeys!=null && curSortKeys.size()>0){
	    		SortKey sortKey = curSortKeys.get(0); // get first sort column, move to it
	    		col = this.convertColumnIndexToView(sortKey.getColumn());
	    	}
	 
			scrollToRowCol(getSelectedRow(),col);
		}
	}

	/* (non-Javadoc)
	 * @see javax.swing.JTable#createDefaultColumnsFromModel()
	 */
	public void createDefaultColumnsFromModel() {
		super.createDefaultColumnsFromModel();
		TableColumnModel tcm = getColumnModel();
		// needed for hide columns
		for (int i=0; i<tcm.getColumnCount(); i++){
			TableColumn tc = tcm.getColumn(i);
			tc.setIdentifier(tm.getColumnKey(i));
		}
	}

	/**
	 * allow for derived classes to adjust the columns if the number of columns exceeds the max
	 * gridtable needs to for editing dates
	 */
	protected void adjustColumnSize() {
	}
    /**
     * @param bnew
     * @param maxEx
     * @return
     * @throws OutOfRangeException
     */
    public EntityItem[] getSelectedEntityItems(boolean bnew, boolean maxEx) throws OutOfRangeException {
        int[] rows = getSelectedRows();
        int ii = rows.length;
        EntityItem[] out = null;
        if (ii > 0) {
        	if (maxEx && ii > getRecordLimit(getProfile())) {
        		//msg12003.0 = \n{0} selectable items exceeds the maximum number.\nPlease select {1} or less items.
        		throw new OutOfRangeException(Utils.getResource("msg12003.0",""+ii,""+getRecordLimit(getProfile())));
        	}

        	out = new EntityItem[ii];
        	for (int i = 0; i < ii; ++i) {
        		int row = this.convertRowIndexToModel(rows[i]);
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

	/* (non-Javadoc)
	 * Implement table header tool tips.
	 * @see javax.swing.JTable#createDefaultTableHeader()
	 */
	protected JTableHeader createDefaultTableHeader() {
		return new JTableHeader(getColumnModel()) {
			private static final long serialVersionUID = 1L;

			/* (non-Javadoc)
			 * get tooltip if columnname was truncated
			 * @see javax.swing.table.JTableHeader#getToolTipText(java.awt.event.MouseEvent)
			 */
			public String getToolTipText(MouseEvent e) {
				if (!BehaviorPref.showTableTooltips()) { //show tt
					return null;
				}
				java.awt.Point p = e.getPoint();
				int index = getColumnModel().getColumnIndexAtX(p.x);
				String fullColname = null;
				if(index != -1){
					fullColname = getFullColumnName(index);
					String colname = getColumnName(index);
					if (colname==null || colname.equals(fullColname)){
						fullColname = null; // no tooltip needed
					}
				}

				return fullColname;
			}
		};
	}

	/**
	 * @see javax.swing.JComponent#createToolTip()
	 */
	public JToolTip createToolTip() {
		JToolTip tip = new JToolTip(){
			private static final long serialVersionUID = 1L;

			public void updateUI() {
				setUI((ToolTipUI)UIManager.get(MULTILINETOOLTIP_UI));
			}
		};
		tip.setComponent(this);
		return tip;
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
			if (att instanceof MultiFlagAttribute) {
				return Routines.getDisplayString(getValueAt(row, col, true).toString(),true);
			}
			if (att instanceof LongTextAttribute) {
				return Routines.getDisplayString(getValueAt(row, col, true).toString(),true);
			}
			if (att instanceof XMLAttribute) {
				return Routines.getDisplayString(getValueAt(row, col, true).toString(),true);
			}
		}
		return null;
	}

	/**
	 * @see javax.swing.JComponent#getToolTipLocation(java.awt.event.MouseEvent)
	 */
	public Point getToolTipLocation(MouseEvent me) {
		Point pt = me.getPoint();
		int row = rowAtPoint(pt);
		int col = columnAtPoint(pt);
		if (isValidCell(row, col)) {
			Point out = getCellRect(row, col, true).getLocation();
			out.translate(-1, -2);
			return out;
		}
		return null;
	}

	/**
	 * must find size needed for a new column when entities are added to matrix
	 * @param fm
	 * @param tc
	 * @return
	 */
	protected int getHeaderWidth(FontMetrics fm,TableColumn tc) {
		Object o = tc.getHeaderValue();
		String str = "";
		if (o != null) {
			str = o.toString();
		}

		int strwidth = fm.stringWidth(Routines.truncate(str,164));
		return Math.max(tc.getPreferredWidth(),strwidth);
	}
	/**
	 * provide access to the RowSelectableTable from the model
	 * must go thru rst for edit on whereused
	 *
	 * @return RowSelectableTable
	 */
	public RowSelectableTable getRSTable() {
		return tm.getRSTable();
	}
	/**
	 * unlock all rows and columns in the table
	 */
	public void unlockTable(){
		for (int mdlRow = 0; mdlRow<tm.getRowCount(); mdlRow++){
			for (int mdlCol = 0; mdlCol<tm.getColumnCount(); mdlCol++){
				LockGroup lockGroup = tm.getLockGroup(mdlRow, mdlCol);
				if(lockGroup!=null){ // will be null for a new entity
					// had the lock, remove the attribute in case entity is unlocked thru lockactiontab
					EACM.getEACM().getLockMgr().removeLockGrpItem(lockGroup.getKey(), getCellEANFoundation(mdlRow,mdlCol));
				}
			}
		}

		tm.unlockTable(profile);
	}

	/**
	 * dereference
	 */
	public void dereference() {
		foundSet.clear();
		foundSet = null;

		tm.dereference();
		tm = null;

		if (rowHeaderList!=null){
			rowHeaderList.removeAll();
			rowHeaderList.setUI(null);
			rowHeaderList = null;
		}
		if (headerListModel!=null){
			headerListModel = null;
		}
		if (rrend!=null){
			rrend.dereference();
			rrend = null;
		}
		if (rowHeaderLabel!=null){
			rowHeaderLabel.dereference();
			rowHeaderLabel = null;
		}

		super.dereference();
		profile = null;
	}

	//HistoryInterface
	public EntityItem getHistoryEntityItem(){
		return null;
	}
	public EntityItem getHistoryRelatorItem(){
		return null;
	}

	public boolean enableHistory() { return getSelectedRowCount() ==1;}

	public EANAttribute getSelectedAttribute() {
		EANAttribute att = null;
		int r = getSelectedRow();
		int c = getSelectedColumn();
		if (isValidCell(r, c)) {
			att = getAttribute(r, c);
		}
		return att;
	}

	public String dump(boolean brief) {
		StringBuffer sb = new StringBuffer();
		sb.append("Table.getRowCount(): " + getRowCount() + NEWLINE);
		sb.append("Table.getColumnCount(): " + getColumnCount() + NEWLINE);
		sb.append(NEWLINE + "TableModel...." + NEWLINE);
		sb.append(tm.dump(brief));
		return sb.toString();
	}

	public EANFoundation getSelectedItem(){
		int row = getSelectedRow();
		if(row==-1){
			return null;
		}
		return getSelectedItem(row);
	}

	public EANFoundation getSelectedItem(int viewrow){
		if(viewrow==-1){
			return null;
		}

		return tm.getRowEAN(convertRowIndexToModel(viewrow));
	}

	public String getTableTitle() {
		return tm.getTableTitle();
	}
    /**
     * needed by editors
     * @return
     */
    public boolean isDynaTable() {
        return tm.isDynaTable();
    }

    /**
     * @param row
     * @return
     */
    public EANActionItem[] getActionItemsAsArray(int row) {
        return tm.getActionItemsAsArray(getProfile(), row);
    }

	/**
	 * @param prof
	 * @param rowKey
	 * @return
	 */
	public EANActionItem[] getActionItemsAsArray(String rowKey) {
		return tm.getActionItemsAsArray(profile, rowKey);
	}

	/**
	 * @param rowKey
	 * @param c
	 * @return
	 */
	public Object getValueAt(String rowKey, int c) {
		return tm.getValueAt(tm.getRowIndex(rowKey), convertColumnIndexToModel(c));
	}
	/**
	 * @param r
	 * @param c
	 * @param longDesc - if true gets newline delimited list of flag values
	 * @return
	 */
	public Object getValueAt(int r, int c, boolean longDesc) {
		return tm.getValueAt(convertRowIndexToModel(r), convertColumnIndexToModel(c), longDesc);
	}
	public EANFoundation getEANObject(String rKey, int c) {
		return tm.getEANObject(tm.getRowIndex(rKey), convertColumnIndexToModel(c));
	}

	public EANFoundation getEANObject(int r, int c) {
		return tm.getEANObject(convertRowIndexToModel(r), convertColumnIndexToModel(c));
	}
	/**
	 * get the soft lock for this cell
	 * @param viewRowid
	 * @param viewColid
	 * @return
	 */
	public boolean getCellLock(int viewRowid, int viewColid) {
		int mdlRow = convertRowIndexToModel(viewRowid);
		int mdlCol = convertColumnIndexToModel(viewColid);
		boolean gotlock = tm.getCellLock(mdlRow, mdlCol,getProfile());
		if(gotlock){
			LockGroup lockGroup = tm.getLockGroup(mdlRow, mdlCol);
			if(lockGroup!=null){ // will be null for a new entity
				// got the lock, save the attribute in case entity is unlocked thru lockactiontab
				EACM.getEACM().getLockMgr().addLockGrpKey(lockGroup.getKey(), getCellEANFoundation(mdlRow,mdlCol));

	        	// only different values will actually fire an event - make sure actions listening
	        	// reflect the correct state
	        	firePropertyChange(DATALOCKED_PROPERTY, true, false);
			}
		}

		return gotlock;
	}

	/**
	 * close any open editor, some other action was selected
	 */
	public void cancelCurrentEdit(){
		TableCellEditor tce = getCellEditor();
		if (tce != null) {
			tce.cancelCellEditing();
		}
	}

    //=================================================
    //FILLCOPYACTION
    /**
     * This copies an attribute and saves it for later fillpaste, not used by formedit
     */
    public void fillCopy(){}
    public void fillAppend(){}
    /**
     * This copies an entity and saves it for later fillpaste, not used by formedit
     */
    public void fillCopyEntity(){}

    //=================================================
    //COPYACTION
    /**
     * This copies from the table and puts EANAttrTransferable on the clipboard
     */
    public void copy(){
        Clipboard clipboard= getToolkit().getSystemClipboard();
        if (clipboard  != null) {
			AttrCellEditor tce = (AttrCellEditor)getCellEditor();
			if(tce!=null){	// a cell is currently under edit
				// is part of the text selected?
				Object selected = tce.getSelection();
				if(selected instanceof EANAttribute){
					Vector<EANAttribute> eanVct = new Vector<EANAttribute>();
					eanVct.add((EANAttribute)selected);
					// transfer of ean objects
					EANAttrTransferable eantransfer = new EANAttrTransferable(eanVct);
					eanVct.clear();
					clipboard.setContents(eantransfer, null);
				}else if(selected !=null){
	        		StringSelection contents = new StringSelection(selected.toString());
	        		clipboard.setContents(contents, null);
	        	}
			}else{
				int[] rows = getSelectedRows();
				int[] cols = getSelectedColumns();
				Vector<EANAttribute> eanVct = new Vector<EANAttribute>();

				for (int r = 0; r < rows.length; ++r) {
					for (int c = 0; c < cols.length; ++c) {
						EANFoundation eaf = getEANObject(rows[r], cols[c]);
						if(eaf instanceof EANAttribute){
							eanVct.add((EANAttribute)eaf);
						}
					}
				}

				// transfer of ean objects
				EANAttrTransferable eantransfer = new EANAttrTransferable(eanVct);
				eanVct.clear();

				clipboard.setContents(eantransfer, null);
			}
        }
    }

    /**
     * allow derived classes to define if a cell is pastable or not, like xml can not be pasted into
     * @param row
     * @param column
     * @return
     */
    protected boolean isCellPastable(int row, int column) {
    	return isCellEditable(row, column);
    }

	//=================================================
	//PASTEACTION
	public boolean canPaste() {
		boolean iseditable = false;
		int[] rows =getSelectedRows();
		int[] cols = getSelectedColumns();
		int editcnt=0;
		int selcnt=0;
		for (int r = 0; r < rows.length; ++r) {
			for (int c = 0; c < cols.length; ++c) {
				if(isCellPastable(rows[r], cols[c])){
					editcnt++;
				}
				selcnt++;
			}
		}

		iseditable = editcnt==selcnt && editcnt!=0;
		return iseditable;
	}
	
	private boolean userCanceledPaste = false;
	/**
	 * give user a way to cancel the following paste operations - if many rows are selected, the same
	 * error will occur
	 */
	public void cancelAllPaste(){
		userCanceledPaste = true;
	}
	/**
	 *
	 */
	public void paste() {
		userCanceledPaste = false;
		Clipboard clipboard= getToolkit().getSystemClipboard();
		if (clipboard  != null) {
			int[] rows = getSelectedRows();
			int[] cols = getSelectedColumns();
	
			boolean multipleSelection = rows.length>1 || cols.length>1; 
			if(multipleSelection){
				// make sure that multiple longtext are not selected
				for (int viewRowid = 0; viewRowid < rows.length; ++viewRowid) {
					int viewRow = rows[viewRowid];
					for (int viewColid = 0; viewColid < cols.length; ++viewColid) {
						int viewCol = cols[viewColid];
						EANAttribute attr = getAttribute(viewRow, viewCol);
						if(attr instanceof LongTextAttribute){
							Logger.getLogger(APP_PKG_NAME).log(Level.INFO,"unable to paste - LongText attr require single selection");
						    UIManager.getLookAndFeel().provideErrorFeedback(null);
						    //msg23048 = Long Text attributes require single selection for paste.
						    UI.showErrorMessage(null, Utils.getResource("msg23048"));
							return;
						}
					}
				}
			}
			
			//save model row selected
			int modelRows[] = new int[rows.length];
			for(int i=0;i<rows.length;i++){
				modelRows[i] = this.convertRowIndexToModel(rows[i]);
			}

			try {
				boolean pasteDone = false;
				if(clipboard.isDataFlavorAvailable(EANAttrTransferable.EANDataFlavor)){
					PasteData[] eanobj = (PasteData[])clipboard.getData(EANAttrTransferable.EANDataFlavor);
					pasteDone = pasteEANData(rows, cols,eanobj);
				}else{ // no eacm object data
					//IN4323492:UnsupportedFlavorException Java Error
					//make sure string data is available
					if(clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)){
						Transferable content = clipboard.getContents(this);
						if (content != null) {
							String data = (String)(content.getTransferData(DataFlavor.stringFlavor));
							pasteDone = pasteStringData(rows,cols,data);
						}
					}else{
						//msg9000 = Unable to paste clipboard content. Unsupported DataFlavor: {0}
						// unsupported content on clipboard
						StringBuilder sb = new StringBuilder();
						DataFlavor[] dfa = clipboard.getAvailableDataFlavors();
						//no.dataflavor = No DataFlavor
						if(dfa.length==0){
							sb.append(Utils.getResource("no.dataflavor"));
						}else{
							for (int a=0; a<dfa.length; a++){
								if(sb.length()>0){
									sb.append(NEWLINE);
								}
								if(sb.indexOf(dfa[a].getHumanPresentableName())==-1){
									sb.append(dfa[a].getHumanPresentableName());
								}
							}
						}
					    UIManager.getLookAndFeel().provideErrorFeedback(null);
						com.ibm.eacm.ui.UI.showErrorMessage(this, Utils.getResource("msg9000",sb.toString()));
						return;
					}
				}

				if(pasteDone){
					TableRowSorter<?> sorter = (TableRowSorter<?>)getRowSorter();
				  	
					this.setRowSorter(null); // turn off any sorting, table update can cancel the edit when it tries to resort
					// move selection to changed rows, order may not be the same
					//updateTable(false); // refresh any classifications
					// refresh classifications
					((RSTTableModel)getModel()).refresh(false);
				  	if(sorter!=null){
						setRowSorter(sorter);
			    	}

					this.clearSelection();

					int selectedRows[] = new int[modelRows.length];
					for(int i=0;i<modelRows.length;i++){
						selectedRows[i] = this.convertRowIndexToView(modelRows[i]);
					}

					for(int r=0;r<selectedRows.length;r++){
						addRowSelectionInterval(selectedRows[r], selectedRows[r]);
					}
					for(int c=0;c<cols.length;c++){
						addColumnSelectionInterval(cols[c], cols[c]);
					}
					
					resizeCells(); // must account for any flags.
					scrollToRowCol(selectedRows[0], cols[0]);
				}
			}catch(Exception exc){
			    UIManager.getLookAndFeel().provideErrorFeedback(null);
				com.ibm.eacm.ui.UI.showException(this, exc);
			}
		} else {
		    UIManager.getLookAndFeel().provideErrorFeedback(null);
		}
	}
	
	/**
	 * paste string data from the clipboard, if multiple cells are selected, try to parse the string into
	 * the same number of tokens and paste one per cell
	 * 
	 * if the cell is currently under edit, paste into it, paste will validate
	 * if one cell is selected and not under edit, edit will be started for that cell and paste done
	 * if multiple cells are selected - value will be checked and setValueAt() done - edit will not be left open
	 * 
	 * @param rows
	 * @param cols
	 * @param data
	 * @return
	 */
	private boolean pasteStringData(int[] rows, int[] cols, String data){
		boolean pasteDone = false;
		boolean multipleSelection = rows.length>1 || cols.length>1; 
		
		TableCellEditor editor = getCellEditor();
		if(editor!=null){	// a cell is currently under edit
			// use editor for validation
			if (editor instanceof PasteEditor){
				//paste will do the validation
				pasteDone = ((PasteEditor)editor).paste(data,true);
			}else{
				// should not be able to happen - just force it into the table
				setValueAt(data, rows[0], cols[0]);
				pasteDone = true;
			}
		}else{
			String splitData[]=null;
			if(multipleSelection){
				// try to split the data to match the rows if it has newlines
				StringTokenizer st = new StringTokenizer(data,NEWLINE+RETURN);
				if(st.countTokens()==(rows.length*cols.length)){
					splitData = new String[rows.length*cols.length];
					int cnt=0;
					while(st.hasMoreTokens()){
						splitData[cnt++] = st.nextToken();
					}
				}
			}
			for (int viewRowid = 0; viewRowid < rows.length; ++viewRowid) {
				int viewRow = rows[viewRowid];
				for (int viewColid = 0; viewColid < cols.length; ++viewColid) {
					int viewCol = cols[viewColid];	
					if(userCanceledPaste){
						return false;
					}
					editor = getCellEditor(viewRow, viewCol);
					
					if (editor != null && editor.isCellEditable(null)) {
						//this is needed to hookup cell listeners - user cant type and keep it without a cell listener
						if(!editCellAt(viewRow, viewCol)){
							Logger.getLogger(APP_PKG_NAME).log(Level.INFO,
									"unable to paste - not able to get celleditor");
							UIManager.getLookAndFeel().provideErrorFeedback(null);
							return false;
						}	

						String pasteVal = data;
						// dont split multiflag strings
						if(splitData!=null && !(editor instanceof MultiCellEditor)){
							pasteVal = splitData[viewRowid+viewColid];
						}

						// use editor for validation
						if (editor instanceof PasteEditor){
							//paste will do the validation
							if(((PasteEditor)editor).paste(pasteVal,false)){
								if(!multipleSelection && !(editor instanceof FlagCellEditor) &&
										!(editor instanceof MultiCellEditor)){
									//keep editor open
									pasteDone = true;
								}else{
									// must stop the edit so listeners get the data
									if(editor.stopCellEditing()){
										pasteDone = true;
									}else{
										editor.cancelCellEditing();
									}
								}
							}else{
								//must cancel the edit
								editor.cancelCellEditing();
							}
						}else{
							//this shouldnt really happen - just force it into the table
							setValueAt(pasteVal, viewRow, viewCol);
							pasteDone = true;
						}
					}else{
						Logger.getLogger(APP_PKG_NAME).log(Level.INFO,
								"unable to paste - not able to get celleditor or cell is not editable");
						UIManager.getLookAndFeel().provideErrorFeedback(null);
						return false;
					}
				} // end col loop
			} // end row loop
		} // end no editor open

		
		return pasteDone;
	}
	/**
	 * paste EANObject data from the clipboard
	 * 
	 * if the cell is currently under edit, paste into it, paste will validate
	 * if one cell is selected and not under edit, edit will be started for that cell and paste done
	 * if multiple cells are selected - value will be checked and setValueAt() done - edit will not be left open
	 * 
	 * @param rows
	 * @param cols
	 * @param eanobj
	 * @return
	 */
	private boolean pasteEANData(int[] rows, int[] cols,PasteData[] eanobj){
		boolean pasteDone = false;
		boolean multipleSelection = rows.length>1 || cols.length>1; 
		
		TableCellEditor editor = getCellEditor();
		if(editor!=null){	// a cell is currently under edit
			// use editor for validation
			if (editor instanceof PasteEditor){
				//paste will do the validation
				pasteDone = ((PasteEditor)editor).paste(eanobj[0],true);
			}else{
				//this shouldnt really happen - just force it into the table
				setValueAt(eanobj[0].getValue(), rows[0], cols[0]);
				pasteDone = true;
			}
		}else{
			// try to get the cell locks
			int eancnt = 0;
			for (int viewRowid = 0; viewRowid < rows.length; ++viewRowid) {
				int viewRow = rows[viewRowid];
				for (int viewColid = 0; viewColid < cols.length; ++viewColid) {
					int viewCol = cols[viewColid];
					if(eancnt==eanobj.length){
						if(eanobj.length==1 && cols.length==1){
							//allow repeat paste into same column
							eancnt = 0;
						}else{
							//1:1 mapping in copied attrs to selected attrs
							Logger.getLogger(APP_PKG_NAME).log(Level.INFO,
									"unable to paste - not 1:1 mapping between copied and selected attributes");
							UIManager.getLookAndFeel().provideErrorFeedback(null);
							return false;
						}
					}
					editor = getCellEditor(viewRow, viewCol);
					if (editor != null && editor.isCellEditable(null)) {
						//this is needed to hookup cell listeners - user cant type anything else and keep it without a listener
						if(!editCellAt(viewRow, viewCol)){
							Logger.getLogger(APP_PKG_NAME).log(Level.INFO,
									"unable to paste - not able to get celleditor");
							UIManager.getLookAndFeel().provideErrorFeedback(null);
							return false;
						}	

						PasteData ean = eanobj[eancnt++];
						//paste eanfoundation
						// use editor for validation
						if (editor instanceof PasteEditor){
							if(((PasteEditor)editor).paste(ean,false)){
								if(!multipleSelection && !(editor instanceof FlagCellEditor) &&
										!(editor instanceof MultiCellEditor)){
									//keep editor open
									pasteDone = true;
								}else{
									//	must stop the edit so listeners get the data
									if(editor.stopCellEditing()){
										pasteDone = true;
									}else{
										editor.cancelCellEditing();
									}
								}
							}else{
								//must cancel the edit
								editor.cancelCellEditing();
							}
						}else{
							//this shouldnt happen - just force it into the table
							setValueAt(ean.getValue(), viewRow, viewCol);
							pasteDone = true;
						}
					}else{
						Logger.getLogger(APP_PKG_NAME).log(Level.INFO,
								"unable to paste - not able to get celleditor or cell is not editable");
						UIManager.getLookAndFeel().provideErrorFeedback(null);
						return false;
					}
				} // end col loop
			} // end row loop
		} // end no current edit open
	
		return pasteDone;
	}

    //=================================================
    //CUTACTION
    /**
     * This cuts from the table and puts text on the clipboard
     */
    public void cut(){
    	AttrCellEditor tce = (AttrCellEditor)getCellEditor();
    	tce.cut();
    }
    /**
     * should cut action be enabled or not
     * @return
     */
    public boolean canCut(){
    	boolean editable = false;
    	TableCellEditor tce = getCellEditor();
    	if(tce instanceof TextCellEditor || tce instanceof DateCellEditor  ||
    			tce instanceof LongCellEditor || tce instanceof TimeCellEditor ){
    		editable = true;
    	}

    	return editable;
    }
    /**
     * this is needed to use EACM copy and paste actions
     * @param tce
     */
    protected void registerActionKeys(AttrCellEditor tce){
		// get actions registered with table
		//COPYACTION
    	KeyStroke keystroke = KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.CTRL_MASK, false);
    	Object actionKey = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).get(keystroke);
    	Action act = null;
    	if(actionKey!=null){
    		act = getActionMap().get(actionKey);
    		if(act instanceof EACMAction){
    			tce.registerEACMAction((EACMAction)act, keystroke);
    		}
    	}

		//PASTEACTION
    	keystroke = KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK, false);
    	actionKey = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).get(keystroke);
    	if(actionKey!=null){
    		act = getActionMap().get(actionKey);
    		if(act instanceof EACMAction){
    			tce.registerEACMAction((EACMAction)act, keystroke);
    		}
    	}
    }
    /**
     * this is needed to use EACM copy and paste actions, release bindings here
     * @param tce
     */
    protected void unregisterActionKeys(AttrCellEditor tce){
		// get actions registered with table
		//COPYACTION
    	KeyStroke keystroke = KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.CTRL_MASK, false);
    	Object actionKey = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).get(keystroke);
    	Action act = null;
    	if(actionKey!=null){
    		act = getActionMap().get(actionKey);
    		if(act instanceof EACMAction){
    			tce.unregisterEACMAction((EACMAction)act, keystroke);
    		}
    	}

		//PASTEACTION
    	keystroke = KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK, false);
    	actionKey = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).get(keystroke);
    	if(actionKey!=null){
    		act = getActionMap().get(actionKey);
    		if(act instanceof EACMAction){
    			tce.unregisterEACMAction((EACMAction)act, keystroke);
    		}
    	}
    }
	/**
	 * only use this on tables with attributes in the cells
	 * @param mdlRow
	 * @param mdlCol
	 * @return
	 */
	protected EANFoundation getCellEANFoundation(int mdlRow, int mdlCol){
		EANFoundation rowFoundation = ((RSTTableModel)getModel()).getCellValue(mdlRow,mdlCol);
		return rowFoundation;
	}

	/* (non-Javadoc)
	 * @see javax.swing.JTable#editCellAt(int, int, java.util.EventObject)
	 */
	public boolean editCellAt(int row, int column, EventObject e){
		boolean ok = super.editCellAt(row, column, e);
		if (ok && e instanceof KeyEvent) {
			TableCellEditor tce = getCellEditor();
			if (tce != null) {
				// use this to pass keybd event to the editor
				tce.shouldSelectCell(e);
			}
		}

		return ok;
	}

    /* (non-Javadoc)
     * @see javax.swing.JTable#editingStopped(javax.swing.event.ChangeEvent)
     */
    public void editingStopped(ChangeEvent e) {
    	int editedRow = getEditingRow();
    	int editedCol = getEditingColumn();
    	super.editingStopped(e);

    	if(editedRow!=-1 && editedCol !=-1){
    		resizeAfterEdit(editedRow, editedCol);
    		repaint(getCellRect(editedRow, editedCol, false));
    	}
    }

    /**
     * @param row
     * @param col
     */
    protected void resizeAfterEdit(int row, int col) {
    	refreshClassification();
    }

    /**
     * refreshClassification - vertical editor will override this
     */
    public void refreshClassification() {
        resizeCells();
    }

	/**
	 * horizontal tables need to resize the column after editing
	 * @param col
	 */
	protected void resizeColumnAfterEdit(int col) {
		Font fnt = getFont();
		FontMetrics fmt = getFontMetrics(fnt);
		List<? extends SortKey> curSortKeys = null;
		if(getRowSorter()!=null) { // verticaledit have column order predefined, so no RowSorter exists
			curSortKeys = getRowSorter().getSortKeys();
		}

		// get header width
    	int arrowWidth=0;
    	int minWidthAdjustment = MIN_WIDTH_ADJUSTMENT;
    	if(curSortKeys!=null){
    		for (SortKey sortKey : curSortKeys) {
       			if (sortKey.getColumn() == convertColumnIndexToModel(col)){
    				arrowWidth = UIManager.getIcon("Table.ascendingSortIcon").getIconWidth(); // allow for arrow indicator
    				break;
    			}
    		}
    	}

    	int maxwidth = 0;
    	int width = arrowWidth; // allow for sorted arrow indicator
    	if (getRowCount()>0){
    		EANFoundation ef = getEANObject(0, col);
    		if(ef instanceof EANFlagAttribute){
    			minWidthAdjustment = FLAG_WIDTH_ADJUSTMENT;
    			width += getColWidth(fmt, getColumnName(col)); // each derived class can override this if calc is diff
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
    			width += getColWidth(fmt, getColumnName(col));
    		}
    	}else{
    		width += getColWidth(fmt, getColumnName(col));
    	}

    	// find max width in all rows for this column
    	if (maxwidth==0){ // didnt calculate row max value yet
    		for (int r=0; r<getRowCount();++r) {
    			Object o = getValueAt(r,col); // base class gets value from model after converting indexes
    			width = Math.max(width,getColWidth(fmt,o));
    		}
    	}

    	width+=minWidthAdjustment;

    	TableColumn tc = getColumnModel().getColumn(col);
		tc.setWidth(width);
		tc.setPreferredWidth(width);
        tc.setMinWidth(MIN_COL_WIDTH);
	}

//	====================================================================
	/**
	 * move to the entityitem row and attribute column with the error
	 * @param bre
	 */
	public void moveToError(EANBusinessRuleException bre) {
		Object o = bre.getObject(0);
		int row = -1;
		int col = -1;
		if (o instanceof EANAttribute) {
			EANAttribute att = (EANAttribute) o;
			EntityItem ei = (EntityItem) att.getParent();
			row = ((RSTTableModel)getModel()).getRowIndex(ei.getKey());
			col = ((RSTTableModel)getModel()).getColumnIndex(Utils.getAttributeKey(ei, att.getAttributeCode()));
			if (row < 0) {
				EntityItem eip = (EntityItem) ei.getUpLink(0);
				row = ((RSTTableModel)getModel()).getRowIndex(eip.getKey());
			}
		} else if (o instanceof EntityItem) {
			EntityItem ei = (EntityItem) o;
			row = ((RSTTableModel)getModel()).getRowIndex(ei.getKey());
			col = 0;
		}

		if(row>=0){
			row = convertRowIndexToView(row);
		}
		if(col>=0){
			col = convertColumnIndexToView(col);
		}
		if ((row >= getRowCount() || row < 0) ||
				(col >= getColumnCount() || col < 0)) {
			return;
		}

		// move to specified row and col
		setRowSelectionInterval(row, row);
		setColumnSelectionInterval(col, col);
		scrollRectToVisible(getCellRect(row, col, false));

		// table headers dont always repaint before dialog pops up, force repaint of entire gui now
		EACM.getEACM().repaint();
	}

	/**
	 * this is called on the bg thread
	 * @return
	 * @throws MiddlewareBusinessRuleException
	 * @throws RemoteException
	 * @throws MiddlewareRequestException
	 * @throws EANBusinessRuleException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException
	 * @throws SQLException
	 */
	public void commit() throws MiddlewareBusinessRuleException,
	RemoteException, MiddlewareRequestException, EANBusinessRuleException,
	MiddlewareException, MiddlewareShutdownInProgressException, SQLException {
		((RSTTableModel)getModel()).commit(this);
	}

	/**
	 * this is called on the event thread
	 * @return
	 */
	public boolean canStopEditing(){
		//prevent double messages from text editor
		if (isEditing() && (getCellEditor() instanceof TextCellEditor)) {
			//editor has invalid input it cant accept
			boolean ok = ((TextCellEditor)getCellEditor()).canStopCellEditing();
			if(!ok){
				return ok;
			}
		}
		return stopCellEditing();
	}

	/**
	 * this is called on the event thread
	 * @return
	 */
	public boolean stopCellEditing(){
		if (isEditing()) {
			//editor has invalid input it cant accept
			return getCellEditor().stopCellEditing();
		}
		return true;
	}

	/**
	 * this is called on the event thread after save or addnewrow
	 */
	public void refresh() {
		int cols[] = getSelectedColumns();
		int rows[] = getSelectedRows();

		((RSTTableModel)getModel()).refresh();
		// only different values will actually fire an event - save and cancel actions listen for this
		this.firePropertyChange(DATACHANGE_PROPERTY, true, false);

		// restore selection
		if (cols.length>0 && rows.length>0){
			setColumnSelectionInterval(cols[0], cols[cols.length-1]);
			setRowSelectionInterval(rows[0], rows[rows.length-1]);
			scrollToRow(rows[0]);
		}else{
			if(getRowCount()>0){
				setColumnSelectionInterval(0, 0);
				setRowSelectionInterval(0, 0);
				scrollToRow(0);
			}
		}
	}

	/**
	 * duplicate added rows, put them at the end
	 * IN4426481 - Function "Duplicate" 
	 * @param firstNewRow
	 */
	public void rowsAdded(int firstNewRow) {
		if(getRowSorter() != null){
			((TableRowSorter<?>)getRowSorter()).setSortKeys(null); // turn off any sorting
		}

		((RSTTableModel)getModel()).rowsAdded(firstNewRow);

		// only different values will actually fire an event - save and cancel actions listen for this
		this.firePropertyChange(DATACHANGE_PROPERTY, true, false);
	}
	/* (non-Javadoc)
	 * fire property chg when updated, save and cancel matrix action is a listener
	 * @see javax.swing.JTable#setValueAt(java.lang.Object, int, int)
	 */
	public void setValueAt(Object aValue, int row, int column) {
		Object oldValue = getValueAt(row, column); // the object doesnt change, only its string representation
		if (oldValue!=null){
			oldValue = oldValue.toString();
			if(aValue!=null){
				if (oldValue.equals(aValue.toString())){ // nothing really changed
					return;
				}
			}else{
				if (oldValue.toString().trim().length()==0){ // aValue is null, nothing set now so ignore it
					return;
				}
			}

			if(aValue == BlobCellEditor.NOBLOB_UPDATE){
				return;
			}
		}
		
		super.setValueAt(aValue, row, column);
		Object newValue = getValueAt(row, column);
		if (newValue!=null){
			newValue = newValue.toString();
		}

		// only different values will actually fire an event
		this.firePropertyChange(DATACHANGE_PROPERTY, oldValue, newValue);
	}
	
	/**
	 * update the model and fire an event with just the selected rows
	 */
	public void updateTableWithSelectedRows(){
		updateTable(false);
	}
	/**
	 *
	 * this is called on the event thread after removelink from wu tab
	 * or after delete key from cell editors
	 */
	public void updateTable(){
		updateTable(true);
	}
	/**
	 *
	 * this is called on the event thread after removelink from wu tab
	 * or after delete key from cell editors
	 * @param updateAll - if true fire the updateall event
	 */
	public void updateTable(boolean updateAll){
		int selectedRows[] = this.getSelectedRows();
		int selectedCols[] = this.getSelectedColumns();
		
		String eikeys[] = new String[selectedRows.length]; // keys of selected rows
		for(int r=0;r<selectedRows.length;r++){
			eikeys[r] = this.getSelectedItem(selectedRows[r]).getKey();
		}
		if(updateAll || selectedRows.length==0){
			tm.updatedModel();
		}else{
			if(selectedRows.length==1){
				int firstrow=this.convertRowIndexToModel(selectedRows[0]);
				tm.fireTableRowsUpdated(firstrow,firstrow);
			}else{
				// get all model indexes 
				Vector<Integer> mdlindexVct = new Vector<Integer>(selectedRows.length);
				int firstrow=-1;
				int lastrow=-1;
				for(int r=0; r<selectedRows.length; r++){
					mdlindexVct.add(new Integer(convertRowIndexToModel(selectedRows[r])));
				}
				Collections.sort(mdlindexVct); // sort by model index

				for(int r=0; r<mdlindexVct.size(); r++){
					if(firstrow==-1){
						lastrow = firstrow=mdlindexVct.elementAt(r);
						continue;
					}
					int nextrow = mdlindexVct.elementAt(r);
					if(lastrow !=nextrow-1){ // not consecutive
						tm.fireTableRowsUpdated(firstrow,lastrow);
						lastrow = firstrow = nextrow;
						continue;
					}
					lastrow=nextrow;
				}

				if(firstrow !=-1){
					tm.fireTableRowsUpdated(firstrow,lastrow);
				}
			}
		}

		this.clearSelection();

		//find the location of the originally selected row - it may have moved during an autosort
		for(int r=0;r<selectedRows.length;r++){
			int mdlrow = ((RSTTableModel)getModel()).getRowIndex(eikeys[r]);
			selectedRows[r] = convertRowIndexToView(mdlrow);
		}

		for(int r=0;r<selectedRows.length;r++){
			if (getRowCount()>selectedRows[r]){
				if(selectedRows[r]!=-1){
					addRowSelectionInterval(selectedRows[r], selectedRows[r]);
				}
			}else{
				break;
			}
		}
		for(int c=0;c<selectedCols.length;c++){
			if (getColumnCount()>selectedCols[c]){
				addColumnSelectionInterval(selectedCols[c], selectedCols[c]);
			}else{
				break;
			}
		}

		// move to first row
		int col=0;
		int row=0;
		if(selectedRows.length>0 && selectedRows[0]>=0){
			row = selectedRows[0];
		}
		if(selectedCols.length>0 && selectedCols[0]>=0){
			col = selectedCols[0];
		}
		this.scrollToRowCol(row, col);
		
		resizeAndRepaint();
	}

	/**
	 * get the keys for the EANFoundations for the selected rows
	 * @return
	 */
	public String[] getSelectedKeys() {
		int[] rows = getSelectedRows(); // view indexes
		String[] out = new String[rows.length];
		for (int i = 0; i < rows.length; ++i) {
			out[i] = getEANKey(rows[i]);
		}
		return out;
	}

	/**
	 * get the model row indexes for the specified keys
	 * @param keys
	 * @return
	 */
	public int[] getRowIndexes(String[] keys) {
		int[] rows = new int[keys.length];
		for (int i = 0; i < keys.length; ++i) {
			rows[i] = ((RSTTableModel)getModel()).getRowIndex(keys[i]);
		}
		return rows;
	}

	/**
	 * get the model row index for the specified key
	 * @param key
	 * @return
	 */
	public int getRowIndex(String key) {
		return ((RSTTableModel)getModel()).getRowIndex(key);
	}

	/**
	 * get the model indexes for the selected rows
	 * @return
	 */
	public int[] getSelectedModelRowIndexes() {
		int[] rows = getSelectedRows(); // view indexes
		int[] mdlrows = new int[rows.length];
		for (int i = 0; i < rows.length; ++i) {
			mdlrows[i] = convertRowIndexToModel(rows[i]);
		}
		rows = null;
		return mdlrows;
	}
	/**
	 * get the key for the EANFoundation at this view index
	 * @param viewrowid
	 * @return
	 */
	public String getEANKey(int viewrowid) {
		return tm.getRowKey(convertRowIndexToModel(viewrowid));
	}

	public void refreshTable(boolean resize) {
		//i moved below resizeAndRepaint();
		if (resize) {
			resizeCells();
			// repaint();
		}else{
			resizeAndRepaint();
		}
	}
	/**
	 * called when the pdh table is replaced, either a new RST or a refreshed RST
	 * @param table
	 */
	public void updateModel(RowSelectableTable table, Profile prof) {
		profile = prof; //must update its prof
		List<? extends SortKey> origsortkeys = null;
		if(getRowSorter() != null){
			origsortkeys = ((TableRowSorter<?>)getRowSorter()).getSortKeys();
		}

		// this will generate the default sort if any
		updateModel(table, profile, false);

		// restore any sort
		if(origsortkeys !=null && origsortkeys.size()>0){
			((TableRowSorter<?>)getRowSorter()).setSortKeys(origsortkeys);
			resizeCells(); // after restoring sort order col width may chg
		}
	}

	/**
	 * @param table
	 * @param prof
	 * @param requestFocus
	 */
	public void updateModel(RowSelectableTable table, Profile prof, boolean requestFocus) {
		if (rowHeaderLabel!=null){
			// columns may be different
			if(getRowSorter() != null){
		    	getRowSorter().removeRowSorterListener(rowHeaderLabel);
			    ((TableRowSorter<?>)getRowSorter()).setSortKeys(null);
		    }
		}
		FilterGroup fg = null;
		boolean wasFiltered = false;
		// when a matrixgroup gets replaced, the rst will be different, dont use fg
		if(getRSTable()!=null && table.getTableTitle().equals(getRSTable().getTableTitle())){
			fg = tm.getFilterGroup(prof); // copy any filtergroup to refreshed rst
			wasFiltered = isFiltered();
		}

		tm.updateModel(table);
		tm.setFilterGroup(fg);
		createDefaultColumnsFromModel();

		if (rowHeaderLabel!=null){
			// columns may be different
			if(getRowSorter() != null){
				getRowSorter().addRowSorterListener(rowHeaderLabel);
			}
		}
		setSortKeys();

		if(wasFiltered){
			filter();
		}else{
			setFilterIcon(); // update filtered icon on frame - basically reset it if it was on
		}

		EACM.getEACM().setHiddenStatus(hasHiddenCols()); // update the icon

		resizeCells();
        if (getColumnCount() > BehaviorPref.getMaxColumns()) { // resize cells did not adjust column width
        	adjustColumnSize();
        }
        
		if (getRowCount() > 0) {
			setRowSelectionInterval(0, 0);
			if (getColumnCount() > 0) {
				int col=0;
				if (getColumnCount() > 1) {
					if(getColumnModel().getColumn(0).getWidth()==0){
						col++;
					}
				}
				setColumnSelectionInterval(col, col);
			}

			scrollToRow(0);
			if(requestFocus){
				requestFocusInWindow();
			}
		}
	}

	protected void setSortKeys(){
		List<? extends SortKey> origsortKeys = null;
		if(getRowSorter() != null){
			origsortKeys = ((TableRowSorter<?>)getRowSorter()).getSortKeys();
		}
/*		if(origsortKeys!=null){//debugonly
			for (int i=0; i<origsortKeys.size(); i++){
				RowSorter.SortKey skey = origsortKeys.get(i);
				System.err.println("RSTTable.setSortKeys origsortKeys["+i+"] col "+skey.getColumn()+
						" order "+skey.getSortOrder());
			}
		}
*/
		if (origsortKeys !=null && origsortKeys.size()>0){
			return; // keep the sort already specified
		}

		// must be done after the columns are setup in the model
		//The precedence of the columns in the sort is indicated by the order of the sort keys in the sort key list.
		List <RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>();
		sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING)); // default to first column
		setSortKeys(sortKeys);
	}

	/**
	 * get help text for the selected cell
	 * @return
	 */
	public String getHelpText() {
		int r = getSelectedRow();
		int c = getSelectedColumn();
		String help = null;
		if (isValidCell(r, c)) {
			help = tm.getHelpText(tm.getRowKey(convertRowIndexToModel(r)),
					tm.getColumnKey(convertColumnIndexToModel(c)));
		}
		return help;
	}
//	====================================================================
//	Filterable support
	/* (non-Javadoc) derived class must support this
	 * @see com.ibm.eacm.objects.Filterable#getFilterableTable()
	 */
	public Object getFilterableTable(){
		return tm.getRSTable();
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.objects.Filterable#setFilterGroup(COM.ibm.eannounce.objects.FilterGroup)
	 */
	public void setFilterGroup(FilterGroup group) {
		tm.setFilterGroup(group);
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.objects.Filterable#getFilterGroup()
	 */
	public FilterGroup getFilterGroup() {
		return tm.getFilterGroup(profile);
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.objects.Filterable#getColFilterGroup()
	 */
	public FilterGroup getColFilterGroup() {
		return tm.getColFilterGroup(profile);
	}
	/* (non-Javadoc)
	 * @see com.ibm.eacm.objects.Filterable#setColFilterGroup(COM.ibm.eannounce.objects.FilterGroup)
	 */
	public void setColFilterGroup(FilterGroup group) {
		tm.setColFilterGroup(group);
	}
	/* (non-Javadoc)derived class must support this
	 * this is used for saving and restoring FilterGroup from serial preferences
	 * @see com.ibm.eacm.objects.Filterable#getUIPrefKey()
	 */
	public String getUIPrefKey() {
		return "RSTTable";
	}

//	====================================================================
	// used for find/replace
	/**
	 * @param viewRow
	 * @param viewCol
	 */
	private void setFound(int viewRow, int viewCol) {
		foundSet.add(generateFoundKey(viewRow,viewCol,false));

		setRowSelectionInterval(viewRow, viewRow);
		setColumnSelectionInterval(viewCol, viewCol);

		scrollRectToVisible(getCellRect(viewRow, viewCol, true));
	}

	/**
	 * needed when found is done and then the row is deleted
	 * @param viewRow
	 */
	protected void removeFound(int viewRow){
		String key = generateFoundKey(viewRow,0,true);
		removeFound(new String[]{key});
	}

	/**
	 * needed when found is done and then the row is deleted
	 * @param key
	 */
	protected void removeFound(String[] keys){
		Iterator<String> itr = foundSet.iterator();
		while(itr.hasNext()){
			String fndkey = itr.next();
			for(int i=0;i<keys.length; i++){
				// remove all columns for this row
				if(fndkey.startsWith(keys[i]+":")){
					itr.remove();
					break; //IN4311520
				}
			}
		}
	}
	/**
	 * was this cell marked as found
	 * @param viewRow
	 * @param viewCol
	 * @return
	 */
	protected boolean isFound(int viewRow, int viewCol) {
		if (foundSet.isEmpty()){
			return false;
		}
		String key = generateFoundKey(viewRow, viewCol,false);

		return foundSet.contains(key);
	}

	/**
	 * get the key to use for the found set
	 * @param viewRow
	 * @param viewCol
	 * @return
	 */
	protected String generateFoundKey(int viewRow, int viewCol, boolean rowOnly) {
		String key = tm.getRowKey(convertRowIndexToModel(viewRow));
		if(!rowOnly){
			int colkey = convertColumnIndexToModel(viewCol);
			key = key+":"+colkey;
		}

		return key;
	}
	/**
	 * do any uncommitted changes exist
	 * @return
	 */
	public boolean hasChanges() {
		// look for chgs 'put' into the rst or chgs made as user types
		return tm.hasChanges() || hasChangedAttrSelected();
	}

	/**
	 * @param rViewId
	 * @param cViewId
	 * @return
	 */
	public boolean isCellLocked(int rViewId, int cViewId) {
		return tm.isCellLocked(convertRowIndexToModel(rViewId),
				convertColumnIndexToModel(cViewId), profile);
	}

	/**
	 * reset Found keys
	 *
	 */
	public void resetFound() {
		foundSet.clear();
		repaint();
		requestFocusInWindow();
	}

	/**
	 * does this cell have the specified value?
	 * @param findValue
	 * @param useCase
	 * @param r
	 * @param c
	 * @return
	 */
	private boolean searchCell(String findValue, boolean useCase, int r, int c) {
		boolean found = false;

		Object o = getValueAt(r, c);
		if (o != null) {
			found = Routines.isFound(o.toString(),findValue,useCase);
		}

		return found;
	}

	/*
	 * (non-Javadoc)
	 * @see com.ibm.eacm.objects.Findable#findValue(java.lang.String, boolean, boolean, boolean, boolean)
	 */
	public boolean findValue(String findValue, boolean isMulti, boolean useCase, boolean isDown, boolean isWrap) {
		int row = getSelectedRow();  // indexes are from table view
		int col = getSelectedColumn(); // indexes are from table view

		if(this.getRowCount()==0 || this.getColumnCount()==0){
			return false;
		}

		if (row<0){ // nothing selected
			row=0;
		}
		if (col<0){
			col=0;
		}
		return findValue(findValue, isMulti, useCase, isDown, isWrap, row, col,true);
	}
	/**
	 * @param findValue
	 * @param isMulti
	 * @param useCase
	 * @param isDown
	 * @param isWrap
	 * @param row
	 * @param col
	 * @param beep
	 * @return
	 */
	private boolean findValue(String findValue, boolean isMulti, boolean useCase, boolean isDown,
			boolean isWrap, int row, int col, boolean beep) {
		boolean found = false;


		if (!isFound(row, col)){
			// look in current cell
			found = searchCell(findValue, useCase, row, col);
		}

		if (!found){
			int startCol = col;
			int startRow = row;
			if(isDown){
				if(isMulti){
					// look in rest of current row
					for (int c=startCol+1; c<getColumnCount()&& !found; c++){
						found = searchCell(findValue, useCase, row, c);
						col = c;
					}

					// look at all following rows and all columns
					for (int r=startRow+1;r<getRowCount() && !found;r++) {
						for (int c=0; c<getColumnCount()&& !found; c++){
							found = searchCell(findValue, useCase, r, c);
							col = c;
							row = r;
						}
					}
				}else{ // just this column
					for (int r=startRow+1;r<getRowCount() && !found;++r) {
						found = searchCell(findValue, useCase, r, col);
						row = r;
					}
				}
				if (!found && isWrap){
					if(beep){
						UIManager.getLookAndFeel().provideErrorFeedback(this);
					}
					// go from last row,col to startRow, startCol
					if(isMulti){
						// look at all previous rows and all columns
						colloop:for (int r=0;r<getRowCount() && !found;r++) {
							for (int c=0; c<getColumnCount() && !found; c++){
								found = searchCell(findValue, useCase, r, c);
								col = c;
								row = r;
								if (r==startRow && c == startCol){
									break colloop;
								}
							}
						}
					}else{ // just this column
						for (int r=0;r<startRow && !found;++r) {
							found = searchCell(findValue, useCase, r, col);
							row = r;
						}
					}
				}// end iswrap
			}// end is down
			else{
				if(isMulti){
					// look in rest of current row
					for (int c = startCol-1; c>=0 && !found; --c){
						found = searchCell(findValue, useCase, row, c);
						col = c;
					}
					// look at all preceding rows
					for (int r = startRow-1;r>=0 && !found;--r) {
						for (int c = getColumnCount()-1; c>=0 && !found; --c){
							found = searchCell(findValue, useCase, r, c);
							col = c;
							row = r;
						}
					}
				}else{ // just this column
					for (int r = startRow-1;r>=0 && !found;--r) {
						found = searchCell(findValue, useCase, r, col);
						row = r;
					}
				}
				if (!found && isWrap){
					if(beep){
						UIManager.getLookAndFeel().provideErrorFeedback(this);
					}
					// go from last row,col to startRow, startCol
					if(isMulti){
						// look at all preceeding rows and all columns
						colloop:for (int r=getRowCount()-1;r>=0 && !found;r--) {
							for (int c=getColumnCount()-1; c>=0 && !found; c--){
								found = searchCell(findValue, useCase, r, c);
								col = c;
								row = r;
								if (r==startRow && c == startCol){
									break colloop;
								}
							}
						}
					}else{ // just this column
						for (int r=getRowCount()-1;r>startRow && !found;--r) {
							found = searchCell(findValue, useCase, r, col);
							row = r;
						}
					}
				}// end iswrap
			}// end is up
		}

		if (found){
			setFound(row,col);
			repaint();
		}
		return found;
	}

	/*
	 * (non-Javadoc)
	 * @see com.ibm.eacm.objects.Findable#hasFound()
	 */
	public boolean hasFound() {
		if(foundSet==null){
			return false;
		}
		return !foundSet.isEmpty();
	}

	/* used to enable the check multiple columns checkbox
	 * (non-Javadoc)
	 * @see com.ibm.eacm.objects.Findable#isMultiColumn()
	 */
	public boolean isMultiColumn() {
		return true;
	}

	/* do not enable any replace function
	 * (non-Javadoc)
	 * @see com.ibm.eacm.objects.Findable#isReplaceable()
	 */
	public boolean isReplaceable() {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.objects.Findable#replaceValue(java.lang.String, java.lang.String, boolean)
	 */
	public int replaceValue(String findValue, String replace, boolean useCase) {
		int r = getSelectedRow();
		int c = getSelectedColumn();
		if(this.getRowCount()==0 || this.getColumnCount()==0){
			return NOT_FOUND;
		}
		if (r < 0) {
			r = 0;
		}
		if (c < 0) {
			c = 0;
		}
		return replaceValue(findValue, replace, useCase, r, c) ;
	}

	private int replaceValue(String findValue, String replace, boolean useCase, int r, int c) {
		int results=REPLACED;

		// look in current cell
		boolean found = searchCell(findValue, useCase, r, c);

		if (found) {
			if (getCellLock(r, c)) {
				if (!isCellEditable(r, c)) {
					results = CELL_UNEDITABLE;
				} else {
					if (!isReplaceableAttribute(r, c)) {
						results = ATTR_NOTREPLACEABLE;
					}else{
						replaceString(findValue,replace, r, c);
						updateTable(); // refresh any classifications
						resizeCells(); // must account for any flags
					}
				}
			} else {
				results = CELL_LOCKED;
				replaceRow = r;
			}
		} else {
			results = NOT_FOUND;
		}
		return results;
	}

	/**
	 * @param newValue
	 * @param viewRow
	 * @param viewCol
	 */
	protected void replaceString(String oldValue,String newValue, int viewRow, int viewCol){
		TableCellEditor editor = getCellEditor(viewRow, viewCol);
		if (editor != null && editor.isCellEditable(null)) {
			prepareEditor(editor, viewRow, viewCol);

			//EANAttribute att = getAttribute(viewRow, viewCol);
			EANFoundation att = getEANObject(viewRow, viewCol);
			String newStr = newValue;
			if (att instanceof LongTextAttribute) {
				newStr = Routines.replace((String) ((LongTextAttribute) att).get(), oldValue, newValue);
			} else if (att instanceof TextAttribute) {
				newStr = Routines.replace((String) ((TextAttribute) att).get(), oldValue, newValue);
			} else if (att instanceof SimpleTextAttribute) {
				newStr = Routines.replace((String) ((SimpleTextAttribute) att).toString(), oldValue, newValue);
			}

			// use editor for validation
			if (editor instanceof PasteEditor){
				if(((PasteEditor)editor).paste(newStr,false)){
					if(editor.stopCellEditing()){
						Object value = editor.getCellEditorValue();
						setValueAt(value, viewRow, viewCol);
					}else{
						editor.cancelCellEditing();
					}
				}
			}else{
				setValueAt(newStr, viewRow, viewCol);
			}
		}
	}
    /**
     * isReplaceableAttribute
     *
     * @param r
     * @param c
     * @return
     */
	protected boolean isReplaceableAttribute(int r, int c) {
		EANFoundation o = getEANObject(r, c);
		if (o instanceof TextAttribute) {
			return true;
		}
		if (o instanceof LongTextAttribute) {
			return true;
		}
		if(o instanceof SimpleTextAttribute){
			return true;
		}

		return false;
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.objects.Findable#getLockInformation()
	 */
	public String getLockInformation() {
		Profile prof = getProfile();
		EntityItem lockOwnerEI = EACM.getEACM().getLockMgr().getLockOwner(prof); // use hashtable if possible

		int row = replaceRow;
		if(row==-1){
			row = this.getSelectedRow();
		}
		if(row==-1){
			return null;
		}

		EntityItem ei = (EntityItem) getSelectedItem(replaceRow);
		if(ei instanceof VEEditItem){
			EntityItem[] eai = ((VEEditItem)ei).getEditableItems();
			for(int a=0; a<eai.length; a++){
				EntityItem relitem = eai[a];
				if (!relitem.hasLock(lockOwnerEI, prof)) {
					LockGroup lock = relitem.getLockGroup();
					if (lock != null) {
						return lock.toString();
					}
				}
			}
		}else{
			if (!ei.hasLock(lockOwnerEI, prof)) {
				LockGroup lock = ei.getLockGroup();
				if (lock != null) {
					return lock.toString();
				}
			}
			EntityGroup eg = ei.getEntityGroup();
			if (eg.isAssoc() || eg.isRelator()) {
				EntityItem downEI = (EntityItem) ei.getDownLink(0);
				if (downEI != null) {
					if (!downEI.hasLock(lockOwnerEI, prof)) {
						LockGroup lg = downEI.getLockGroup();
						if (lg != null) {
							return lg.toString();
						}
					}
				}
			}
		}

		return null;
	}
	/**
	 * @param findValue
	 * @param replace
	 * @param isMulti
	 * @param useCase
	 * @param isDown
	 * @param isWrap
	 * @return
	 */
	public int replaceNextValue(String findValue, String replace, boolean isMulti, boolean useCase, boolean isDown, boolean isWrap) {
		// same crappy behavior as before
		int results = replaceValue(findValue, replace, useCase);
		if(results == REPLACED){
			findValue(findValue, isMulti, useCase, isDown, isWrap);
		}

		return results;
	}

	/**
	 * @param findValue
	 * @param replace
	 * @param isMulti
	 * @param useCase
	 * @param isDown
	 */
	public void replaceAllValue(String findValue, String replace, boolean isMulti, boolean useCase, boolean isDown) {
		for (int r = 0; r < getRowCount(); ++r) {
			for (int c = 0; c <  getColumnCount(); ++c) {
				if (findValue(findValue, isMulti, useCase, isDown, true, r, c,false)){
					replaceValue(findValue, replace, useCase, r, c);
				}
			}
		}
	}


//	=============================================================================

	/**
	 * get the model column id used for the row header if the row header exists
	 * @return
	 */
	protected int getRowHeaderColumnId(){
		int id = -1;
		if(headerListModel != null){
			id = headerListModel.getModelColumnId();
		}
		return id;
	}
	/**
	 * get the row header list
	 * used with matrix, crosstable and horiz ed for freeze
	 * @param viewColId
	 * @return
	 */
	public JList getRowHeader(int viewColId){
		if(rowHeaderList==null){
			headerListModel = new RSTHeaderListModel(viewColId);
			rrend = new RowHeaderRenderer(this);
			String title = getModel().getColumnName(headerListModel.getModelColumnId());
			rrend.setMinWidth(title);
			rowHeaderList = new JList(headerListModel);
			rowHeaderList.setCellRenderer(rrend);
			//white below list if this isnt done
			rowHeaderList.setBackground(getTableHeader().getBackground());
		}else{
			if(headerListModel.getModelColumnId()!=convertColumnIndexToModel(viewColId)){
				headerListModel.setModelColumnId(viewColId);
				headerListModel.refresh(); // render the list again, using new column values
				String title = getModel().getColumnName(headerListModel.getModelColumnId());
				rowHeaderLabel.reset(title);
				rrend.setMinWidth(title);
			}
		}

		return rowHeaderList;
	}
    /**
     * calculate height needed for this row
     * @param fm
     * @param baseHeight
     * @param str - could be multiline in some tables
     * @return
     */
    protected int getRowHeight(FontMetrics fm, int baseHeight, String str){
    	return fm.getHeight() * Routines.getCharacterCount(str,NEWLINE);
    }
	/**
	 * get the column header for the row header list
	 * @return
	 */
	public JLabel getHeader(){
		if (rowHeaderLabel==null){
			//allow for freeze support
			rowHeaderLabel = new RowHeaderLabel(getModel().getColumnName(headerListModel.getModelColumnId()));
		}
		return rowHeaderLabel;
	}

	/**
	 * this is the column label for the row header list
	 * it displays an up/down arrow and listens for sort changes, it will update the listmodel when a sort was done
	 */
	protected class RowHeaderLabel extends JLabel implements RowSorterListener {
		private static final long serialVersionUID = 1L;
		private Icon ascIcon, descIcon;

		private MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				boolean isAscending = getIcon()==ascIcon;

				//The precedence of the columns in the sort is indicated by the order of the sort keys in the sort key list.
				List <RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>();
				sortKeys.add(new RowSorter.SortKey(headerListModel.getModelColumnId(),
						(isAscending?SortOrder.DESCENDING:SortOrder.ASCENDING)));
				sort(sortKeys);
			}
		};

		RowHeaderLabel(String title) {
			addMouseListener(mouseListener);
			ascIcon = UIManager.getIcon("Table.ascendingSortIcon");
			descIcon = UIManager.getIcon("Table.descendingSortIcon");

			reset(title);

			if(RSTTable.this.getRowSorter() != null){
				RSTTable.this.getRowSorter().addRowSorterListener(this);
			}
			setHorizontalTextPosition(JLabel.LEFT);
		}

		/**
		 * @param title
		 */
		void reset(String title) {
			setIcon(ascIcon);
			setText(title);
		}

		/**
		 * release memory
		 */
		void dereference() {
			if(RSTTable.this.getRowSorter() != null){
				RSTTable.this.getRowSorter().removeRowSorterListener(this);
			}
			removeMouseListener(mouseListener);
			mouseListener = null;
			ascIcon=null;
			descIcon=null;
			setIcon(null);
			removeAll();
			setUI(null);
		}

		/* (non-Javadoc)
		 * one of the columns was sorted, update the list
		 * @see javax.swing.event.RowSorterListener#sorterChanged(javax.swing.event.RowSorterEvent)
		 */
		public void sorterChanged(RowSorterEvent e) {
			headerListModel.refresh(); // render the list again, using new column values

			// look to see if the column used for this row header was used for sort
			//if so, then update the icon direction
			List<? extends SortKey> sortKeys = RSTTable.this.getRowSorter().getSortKeys();
			setIcon(null);
			for (SortKey sortKey : sortKeys) {
				if (sortKey.getColumn() == RSTTable.this.convertColumnIndexToModel(headerListModel.getModelColumnId())){
					SortOrder o = sortKey.getSortOrder();
					setIcon(o == SortOrder.ASCENDING ? ascIcon : descIcon);
					break;
				}
			}
		}
	}
	/**
	 * this is called when the table model is completely changed and the list and column header must be updated
	 * like pivot or new matrixgroup in a crosstable
	 * @param title
	 */
	protected void refreshHeaderModel(String title){
		if(rrend!=null){
			rrend.setMinWidth(title);
		}
		if (headerListModel!=null){
			headerListModel.refresh(); // render the list again, using new column values
		}
		if (rowHeaderLabel!=null){
			rowHeaderLabel.reset(title);
		}
	}
	/**
	 * used as listmodel for row header list
	 * a jtable column is used for populating the list
	 */
	private class RSTHeaderListModel extends AbstractListModel {
		private static final long serialVersionUID = 1L;
		private int columnId = 0;
		/**
		 * @param viewcolid
		 */
		RSTHeaderListModel(int viewcolid){ // idea is that horz table can use this for freeze
			setModelColumnId(viewcolid);
		}
		void setModelColumnId(int viewcolid){
			columnId = convertColumnIndexToModel(viewcolid);
		}
		int getModelColumnId(){
			return columnId;
		}
		public int getSize() {
			return getRowCount();
		}
		public void refresh(){
			fireContentsChanged(this, 0, getSize());
		}
		public Object getElementAt(int index) {
			return getValueAt(index, convertColumnIndexToView(columnId)); // allow for moving column 0
		}
	}
//	=============================================================================
	/**
	 * get entity data information for the selected attribute
	 * called by the EntityDataAction - ENTITYDATAACTION
	 * @return
	 */
	public String getInformation() {
		int r = getSelectedRow();
		int c = getSelectedColumn();
		if (isValidCell(r, c)) {
			EANAttribute att = getAttribute(r, c);
			return Routines.getInformation(att);
		}
		return Utils.getResource("nia");
	}

    /**
     * return a single selected attribute's meta
     * @return
     */
    public EANMetaAttribute getSelectedEANMetaAttribute() {
		int rows[] = getSelectedRows();
		int cols[] = getSelectedColumns();
		if(rows.length==1 && cols.length==1){
			if (isValidCell(rows[0], cols[0])) {
				EANAttribute ean= getAttribute(rows[0],cols[0]);
				if (ean != null) {
		            return ean.getMetaAttribute();
		        }
			}
		}
		return null;
	}
	/**
	 * get the attribute at this row and column
	 * @param r
	 * @param c
	 * @return
	 */
	public EANAttribute getAttribute(int r, int c) {
		EANFoundation o = getEANObject(r, c);
		if (o instanceof EANAttribute) {
			return (EANAttribute) o;
		}
		return null;
	}
	/**
	 * can spell check be done on the selected attributes
	 * @return
	 */
	public boolean isSpellCheckable(){
		boolean chgd = false;

    	int rows[] = getSelectedRows();
    	int cols[] = getSelectedColumns();
    	outerloop:for(int r=0; r<rows.length; r++){
    		for(int c=0; c<cols.length; c++){
    			if (isValidCell(rows[r], cols[c])) {
    				EANAttribute attr = getAttribute(rows[r],cols[c]);
    				if(attr==null){
    					// must be bad meta, the attr should exist, bail out
    					break outerloop;
    				}
    				EANMetaAttribute meta = attr.getMetaAttribute();
    				if(meta.isSpellCheckable()){
    					if(attr instanceof LongTextAttribute){
    						chgd = attr.hasChanges() || hasNewChanges(rows[r],cols[c]);
    					}else if(attr instanceof TextAttribute){
    						if (meta.isDate() || meta.isTime()) {
    						}else{
    							chgd = attr.hasChanges() || hasNewChanges(rows[r],cols[c]);
    						}
    					}
    				}
    				if(chgd){
    					break outerloop;
    				}
    			}
    		}
    	}

		return chgd;
	}

	/**
	 * were any changes just made in this editor
	 * @return
	 */
	public boolean hasNewChanges(int row, int col){
		if(getEditingRow() !=row || this.getEditingColumn()!=col){
			return false; // cell not under edit
		}
		TableCellEditor editor = getCellEditor();//row,col); //this updates the rst, row and col in the editor, bad if pasting
		//if(getCellEditor()!=editor || editor ==null){
		if(editor ==null){
			return false;
		}
		Object obj = editor.getCellEditorValue(); // if this isnt the current editor, it wont have a value
		Object orig = getAttribute(row,col).get();

		String curvalue="";
		String origvalue="";
		if(obj !=null){
			if(obj instanceof MetaFlag[]){
				MetaFlag[] mf = (MetaFlag[])obj;
				StringBuilder sb = new StringBuilder();
				for (int i=0; i<mf.length; i++){
					if(mf[i].isSelected()){
						sb.append(mf[i].getFlagCode());
					}
				}
				curvalue = sb.toString();
				if(orig !=null){
					//get original value
					if(orig instanceof MetaFlag[]){
						mf = (MetaFlag[])orig;
						sb = new StringBuilder();
						for (int i=0; i<mf.length; i++){
							if(mf[i].isSelected()){
								sb.append(mf[i].getFlagCode());
							}
						}
						origvalue = sb.toString();
					}else{
						origvalue = orig.toString();
					}
				}
			}else{
				curvalue = obj.toString();
				origvalue=orig.toString();
			}
		}

		return !origvalue.equals(curvalue);
	}
    /**
     * does the current editor have a change
     * @return
     */
	public boolean hasChangedAttrSelected() {
    	int rows[] = getSelectedRows();
    	int cols[] = getSelectedColumns();
    	for(int r=0; r<rows.length; r++){
    		for(int c=0; c<cols.length; c++){
    			if (isValidCell(rows[r], cols[c])) {
    				EANAttribute attr = getAttribute(rows[r],cols[c]);
    			   	if(hasNewChanges(rows[r],cols[c]) ||
    	        			(attr!=null && attr.hasChanges())){
    	        		return true;
    	        	}
    			}
    		}
    	}
    	return false;
    }

	public boolean requestFocusInWindow() {
		boolean b = super.requestFocusInWindow();
		if(b){
			// select the first row
			int row = getSelectedRow();
			int col = getSelectedColumn();
			if(row==-1 && this.getRowCount()>0){
				this.setRowSelectionInterval(0, 0);
			}
			if(col==-1 && this.getColumnCount()>0){
				this.setColumnSelectionInterval(0, getColumnCount()-1);
			}
		}
		return b;
	}


    /**
     * called to roll back an attribute
     */
    public void rollbackSingle() {
    	int rows[] = getSelectedRows();
    	int cols[] = getSelectedColumns();
    	for(int r=0; r<rows.length; r++){
    		for(int c=0; c<cols.length; c++){
    			if (isValidCell(rows[r], cols[c])) {
    				EANAttribute att = getAttribute(rows[r],cols[c]);
    				if (att != null && att.hasChanges()) {
    					att.rollback();
    					resizeAfterEdit(rows[r],cols[c]);
    				}
    			}
    		}
    	}
    	
    	//tm.updatedModel(); this resorts
    	for(int r=0; r<rows.length; r++){
			tm.fireTableRowsUpdated(rows[r],rows[r]);
		}
        // restore selection
		setRowSelectionInterval(rows[0], rows[0]);
		setColumnSelectionInterval(cols[0], cols[0]);

		scrollToRowCol(rows[0], cols[0]);

		resizeCells(); // cell dimensions may have changed
    }

    /* (non-Javadoc)
     * @see com.ibm.eacm.table.BaseTable#getAccessibleMetaAttribute(int, int)
     */
    protected EANMetaAttribute getAccessibleMetaAttribute(int r, int c) {
    	Object o = getEANObject(r, c);
    	if (o instanceof EANAttribute) {
    		return ((EANAttribute) o).getMetaAttribute();
    	}

    	return null;
    }
    /* (non-Javadoc)
     * @see com.ibm.eacm.table.BaseTable#isAccessibleCellEditable(int, int)
     */
    protected boolean isAccessibleCellEditable(int row, int col) {
        return isCellEditable(row, col);
    }

    /* (non-Javadoc)
     * @see com.ibm.eacm.table.BaseTable#isAccessibleCellLocked(int, int)
     */
    protected boolean isAccessibleCellLocked(int row, int col) {
        return isCellLocked(row, col);
    }
}

