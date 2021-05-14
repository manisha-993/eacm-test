/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: HorzEditor.java,v $
 * Revision 1.4  2009/05/22 14:18:52  wendy
 * Performance cleanup
 *
 * Revision 1.3  2009/04/16 17:52:58  wendy
 * Cleanup button enablement
 *
 * Revision 1.2  2008/01/30 16:27:07  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:44:06  wendy
 * Reorganized JUI module
 *
 * Revision 1.6  2005/11/15 17:07:42  tony
 * fixed null pointer
 *
 * Revision 1.5  2005/11/10 21:37:18  tony
 * Fixed null pointer
 *
 * Revision 1.4  2005/11/04 22:59:28  tony
 * adjusted
 *
 * Revision 1.3  2005/10/31 17:22:00  tony
 * VEEdit_Iteration2
 * Added create logic.
 *
 * Revision 1.2  2005/09/12 19:03:12  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:54  tony
 * This is the initial load of OPICM
 *
 * Revision 1.18  2005/09/08 17:59:02  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.17  2005/03/29 21:54:15  tony
 * rework of display workaround
 *
 * Revision 1.16  2005/03/04 19:48:31  tony
 * hide maintenance function when not available and
 * properly enable maintenance function
 *
 * Revision 1.15  2005/03/04 19:08:24  tony
 * improved cr_FlagUpdate
 *
 * Revision 1.14  2005/03/03 21:46:40  tony
 * cr_FlagUpdate
 * Added Flag Modification Capability.
 *
 * Revision 1.13  2005/03/01 19:30:11  tony
 * 6245 enahnced logic to query about current edit
 * before NLS is toggled.
 *
 * Revision 1.12  2005/02/25 16:54:48  tony
 * 6542 change request wrap-up
 *
 * Revision 1.11  2005/02/23 20:22:01  tony
 * 6542
 *
 * Revision 1.10  2005/02/03 16:38:53  tony
 * JTest Second Pass Wrapup
 *
 * Revision 1.9  2005/02/01 20:48:33  tony
 * JTest Second Pass
 *
 * Revision 1.8  2005/01/27 19:15:15  tony
 * JTest Format
 *
 * Revision 1.7  2004/11/29 18:14:37  tony
 * USRO-R-RTAR-672S9Y updated paste activation logic.
 *
 * Revision 1.6  2004/08/19 15:12:05  tony
 * xl8r
 *
 * Revision 1.5  2004/06/24 20:29:26  tony
 * TIR USRO-R-SWWE-629MHH
 *
 * Revision 1.4  2004/04/26 15:29:15  tony
 * added yield on dereference.
 *
 * Revision 1.3  2004/03/30 21:34:52  tony
 * CR_0813025214
 *
 * Revision 1.2  2004/03/25 23:37:20  tony
 * cr_216041310
 *
 * Revision 1.1.1.1  2004/02/10 16:59:38  tony
 * This is the initial load of OPICM
 *
 * Revision 1.29  2003/10/20 16:37:14  tony
 * memory update.  Updated dereference logic to close memory gaps
 *
 * Revision 1.28  2003/10/14 15:26:42  tony
 * 51832
 *
 * Revision 1.27  2003/10/08 20:12:57  tony
 * 52476
 *
 * Revision 1.26  2003/09/25 22:49:14  tony
 * 52287 -- explanation of why vertical works and
 * horizontal does NOT.
 *
 * Revision 1.25  2003/09/23 18:27:21  tony
 * 52354
 *
 * Revision 1.24  2003/09/19 18:11:07  tony
 * 52323
 *
 * Revision 1.23  2003/09/12 16:14:27  tony
 * 52189
 *
 * Revision 1.22  2003/09/11 18:09:24  tony
 * acl_20030911
 * updated addRow logic to sort based on a boolean.
 *
 * Revision 1.21  2003/09/04 20:29:20  tony
 * added return at eol.
 *
 * Revision 1.20  2003/09/02 21:29:13  tony
 * 52020
 *
 * Revision 1.19  2003/08/27 16:20:32  tony
 * 51945
 *
 * Revision 1.18  2003/08/18 15:38:48  tony
 * cr_TBD
 *
 * Revision 1.17  2003/08/13 23:05:01  tony
 * 51762
 *
 * Revision 1.16  2003/07/18 18:59:09  tony
 * acl_20030718 added selectKeys logic as guest
 * Entity  = current entity
 * Entity1 = parent Entity
 * Entity2 = Child Entity
 * then ',' then keys separated by ','.
 *
 * Revision 1.15  2003/07/03 17:26:56  tony
 * updated logic to improve scripting
 *
 * Revision 1.14  2003/06/10 16:46:47  tony
 * 51260
 *
 * Revision 1.13  2003/06/05 17:07:38  tony
 * added refreshAppearance logic to the toolbar for the container.
 * This will flow thru to the scrollable popup.
 * Adjusted the sizing logic on the scrollable popup.
 *
 * Revision 1.12  2003/06/02 16:45:29  tony
 * 51024 added outOfRangeException, thus preventing multiple
 * messages.
 *
 * Revision 1.11  2003/05/13 22:45:05  tony
 * 50616
 * Switched keys from a string to a pointer to the
 * EANFoundation.
 *
 * Revision 1.10  2003/05/09 16:51:27  tony
 * updated filter icon enabling for filter.
 *
 * Revision 1.9  2003/05/02 20:05:55  tony
 * 50504 -- adjusted messaging and this seems to have
 * resolved the problem in local test.
 *
 * Revision 1.8  2003/05/01 18:01:58  tony
 * horzEditor was requiring multiple closes when single record
 * was edited.
 *
 * Revision 1.7  2003/05/01 17:14:15  tony
 * cleaned-up code
 *
 * Revision 1.6  2003/04/03 16:19:08  tony
 * changed refreshLookAndFeel to refreshAppearance()
 * updated logic to take into account displayed dialogs
 * as well.
 *
 * Revision 1.5  2003/04/02 17:55:19  tony
 * added dump logic.
 *
 * Revision 1.4  2003/03/11 00:33:24  tony
 * accessibility changes
 *
 * Revision 1.3  2003/03/04 22:34:50  tony
 * *** empty log message ***
 *
 * Revision 1.2  2003/03/04 16:52:55  tony
 * added logic for EntityHistoryGroup
 *
 * Revision 1.1.1.1  2003/03/03 18:03:46  tony
 * This is the initial load of OPICM
 *
 * Revision 1.87  2002/12/06 17:22:06  tony
 * acl_20021206 -- added synchronize logic to the tables.
 *
 * Revision 1.86  2002/11/20 18:03:57  tony
 * 23265b - enhancement to adjust menu enablement
 * base on the number of records displayed and the
 * number of records available.
 *
 * Revision 1.85  2002/11/20 17:10:00  tony
 * 23265
 *
 * Revision 1.84  2002/11/14 22:04:47  tony
 * 23173
 *
 * Revision 1.83  2002/11/11 22:55:38  tony
 * adjusted classification on the toggle
 *
 * Revision 1.82  2002/11/07 16:58:26  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.edit;
import com.elogin.Routines;
import com.ibm.eannounce.eforms.table.HorzTable;
import com.ibm.eannounce.eforms.toolbar.*;
import com.ibm.eannounce.eobjects.*;
import com.ibm.eannounce.exception.*;
import javax.swing.*;
import java.awt.*;
import COM.ibm.eannounce.objects.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class HorzEditor extends EannounceEditor {
	private static final long serialVersionUID = 1L;
	private HorzTable table = null;
	protected Editable getEditable() { return table;}

    /**
     * horzEditor
     * @param _eList
     * @param _ec
     * @author Anthony C. Liberto
     */
    protected HorzEditor(EntityList _eList, EditController _ec) {
        super(_ec, DefaultToolbarLayout.EDIT_HORZ_BAR, null);
        buildTable(_eList);
        createScrollPane(table);
        setTitle(ec.getTable().getTableTitle());
        createMenus("Horizontal Toolbar", true);
        table.addMouseListener(popup);
        table.setEditController(ec);
        // this just returns a boolean.. hasHiddenAttributes(); //52476
    }

    /**
     * buildTable
     * @param _eList
     * @author Anthony C. Liberto
     */
    private void buildTable(EntityList _eList) {
        //TIR USRO-R-SWWE-629MHH		table = new horzTable(_eList,ec.getTable());
        table = new HorzTable(_eList, ec.getTable(), ec); //TIR USRO-R-SWWE-629MHH
        if (_eList.isVEEdit()) {							//VEEdit_Iteration2Sort
			table.sort();									//VEEdit_Iteration2Sort
		}													//VEEdit_Iteration2Sort
    }

    /**
     * requestFocus
     *
     * @author Anthony C. Liberto
     */
    public void requestFocus() {
        if (table != null) {
            table.requestFocus();
        } else {
            super.requestFocus();
        }
    }

    /**
     * refresh
     *
     */
    protected void refresh() {
        table.resizeAndPaint(false);
    }

    /**
     * synchronize
     *
     * @author Anthony C. Liberto
     */
    protected void synchronize() { //acl_20021206
        table.synchronize(); //acl_20021206
    } //acl_20021206

    /*
     * lock and unlock
     */
    /**
     * lock
     */
    protected void lock() {
        table.lockRows();
        repaint();
    }

    /**
     * unlock
     *
     */
    protected void unlock() {
        if (!okToClose(true)) {
            return;
        }
        //locklist		lockAll(false);
        table.unlockRows();
        repaint();
    }

    /*
     * copy, paste, and export
     */
    /**
     * copy
     */
    protected void copy() {
        table.copy();
    }

    /**
     * importTable
     *
     * @author Anthony C. Liberto
     */
    protected void importTable() { //xl8r
        table.importTable(); //xl8r
    } //xl8r

    /**
     * cut
     *
     */
    protected void cut() {
        table.cut();
    }

    /**
     * paste
     */
    protected void paste() {
        table.paste();
    }

    /**
     * duplicate
     *
     */
    protected void duplicate() {
        int copies = -1;
        if (!table.canContinue()) { //22455
            return;
        } //22455
        copies = getNumberCopies();
        if (copies < 0) {
            return;
        }
        table.duplicate(copies);
    }

    /**
     * print
     */
    protected void print() {
        table.print();
    }

    /**
     * moveColumn
     *
     * @param _left
     * @author Anthony C. Liberto
     */
    protected void moveColumn(boolean _left) {
        table.moveColumn(_left);
    }

    /**
     * getHelpText
     *
     * @return
     */
    protected String getHelpText() {
        return table.getHelpText();
    }

    /**
     * showInformation
     *
     */
    protected void showInformation() {
        table.showInformation();
    }

    /**
     * find
     */
    protected void find() {
        table.showFind();
    }

    /**
     * freeze
     *
     */
    protected void freeze() {
        ERowList list = table.freeze();
        JViewport view = null;
        if (list == null) {
            return;
        }
        scroll.setRowHeaderView(list);
        view = scroll.getRowHeader();
        view.setViewSize(list.getListSize());
        view.setSize(list.getListSize());
        view.setPreferredSize(list.getListSize());
        scroll.setCorner(JScrollPane.UPPER_LEFT_CORNER, list.getHeader());
    }

    /**
     * freezeRefresh
     *
     */
    protected void freezeRefresh() {
        ERowList list = table.refreshList();
        if (list != null) { //52323
            JViewport view = scroll.getRowHeader();
            view.setViewSize(list.getListSize());
            view.setSize(list.getListSize());
            view.setPreferredSize(list.getListSize());
            scroll.setCorner(JScrollPane.UPPER_LEFT_CORNER, list.getHeader());
        } //52323
    }

    /**
     * thaw
     *
     */
    protected void thaw() {
        JViewport view = null;
        table.thaw();
        scroll.setRowHeaderView(null);
        view = scroll.getRowHeader();
        if (view != null) { //52020
            Dimension d = UIManager.getDimension("eannounce.minimum");
            view.setViewSize(d);
            view.setSize(d);
            view.setPreferredSize(d);
            view.revalidate(); //52020
        } //52020
    }

    /**
     * okToClose
     *
     * @param _reset
     * @return
     */
    protected boolean okToClose(boolean _reset) {
        int action = -1;
        if (table.isEditing()) {
            //51945			table.finishEditing();
            if (!table.editingCanStop()) { //51945
                return false; //51945
            } //51945
        }
        // check for any change
        boolean chgsMade = false;
        if (ec != null){
        	chgsMade = ec.hasMasterChanges();
        }else{
        	chgsMade = hasChanges(); // look at selected row
        }
        if (chgsMade) {
            if (getEntityItemCount() > 1) {
                action = showConfirm(ALL_NONE_CHOOSE_CANCEL, "updtMsg4", true);
                if (action == 0) {
                    return ec.commitAll();
                } else if (action == 2) {
                    return chooseToSave();
                }
            } else {
                action = showConfirm(YES_NO_CANCEL, ("updtMsg2"), true);
            }
            if (action == 0) {
                return ec.commit();
            } else if (action == 1) {
                if (_reset) {
                    rollback();
                }
                return true;
            }
            return false;
        }
        return true;
    }

    private boolean chooseToSave() {
        RowSelectableTable rst = ec.getTable();
        EANFoundation[] ean = rst.getTableRowsAsArray();
        int action = -1;
        for (int i = 0; i < ean.length; i++) {
            EntityItem ei = ((EntityItem) ean[i]);
            if (ei.hasChanges()) {
                table.selectRow(ei);
                action = showConfirm(YES_NO_CANCEL, ("updtMsg1"), true);
                if (action == 0) {
                    //51762					ec.commit();
                    if (!ec.commit()) { //51762
                        return false; //51762
                    } //51762
                } else if (action == 1) {
                    continue;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * dereference
     *
     * @author Anthony C. Liberto
     */
    public void dereference() {
        Routines.yield();
        closeLocalMenus(); //acl_Mem_20020130
        if (table != null) {
            table.removeMouseListener(popup);
            table.dereference();
            //table.removeAll(); done in gtable.deref
            //table.removeNotify();
            table = null;
        }
        super.dereference();
    }

    /**
     * removeExtra
     */
    protected void removeExtra() {
        table.removeRows();
    }

    /**
     * setParentItem
     * @param _parent
     */
    protected void setParentItem(EntityItem _parent) {
        table.setParentItem(_parent);
    }

    /**
     * getRecordKey
     *
     * @return
     */
    protected String getRecordKey() {
        return table.getRecordKey();
    }

    /**
     * setSelection
     * @param _recKey
     * @param _selKey
     */
    protected void setSelection(String _recKey, String _selKey) {
        table.setSelection(_recKey, _selKey);
    }

    /**
     * rollback
     *
     */
    protected void rollback() {
        if (table.isEditing()) { //23173
            table.cancelEdit(); //23173
        } //23173
        table.rollback();
    }

    /**
     * rollbackRow
     *
     */
    protected void rollbackRow() {
        if (table.isEditing()) { //23173
            table.cancelEdit(); //23173
        } //23173
        table.rollbackRow();
    }

    /**
     * rollbackSingle
     *
     */
    protected void rollbackSingle() {
        if (table.isEditing()) { //23173
            table.cancelEdit(); //23173
        } //23173
        table.rollbackSingle();
    }

    /**
     * commit
     *
     * @return
     */
    protected boolean commit() {
        return table.commit();
    }

    /**
     * hasChanges
     *
     * @return
     */
    protected boolean hasChanges() {
		if (table != null) {
			//look at the row, dont enable save row if row didnt have the change
	        return table.hasEntityItemChanged();
	        //return table.hasChanges();
		}
		return false;
    }

    /**
     * addRow
     *
     * @param _sort
     */
    protected void addRow(boolean _sort) { //acl_20030911
        if (ec != null && ec.isVEEdit()) {
			table.addRow(getRecordKey(),_sort);
		} else {
	        table.addRow(_sort); //acl_20030911
		}
    }

    /**
     * getEntityItems
     *
     * @return
     */
    protected EntityItem[] getEntityItems() {
        try {
            return (EntityItem[]) table.getSelectedObjects(false, true);
        } catch (OutOfRangeException _range) {
            _range.printStackTrace();
            showException(_range, FYI_MESSAGE, OK); //51260
        }
        return null;
    }

    /**
     * removeNewRows
     *
     */
    protected void removeNewRows() { //19920
        table.removeNewRows(); //19920
    } //19920

    /**
     * moveToError
     * @param _ebre
     */
    protected void moveToError(EANBusinessRuleException _ebre) { //20069
        table.moveToError(_ebre);
    }

    /**
     * showSort
     */
    protected void showSort() {
        table.showSort();
    }

    /**
     * isNew
     *
     * @return
     */
    protected boolean isNew() { //21765
	    if (table != null) {
	        return table.isNew(); //22252
		}
		return false;
        //22252		return false;							//21765
    } //21765

    /**
     * refreshRemove
     * @author Anthony C. Liberto
     */
    protected void refreshRemove() {
        table.refreshRemove();
    }

    /**
     * getPanelType
     *
     * @return
     */
    public String getPanelType() {
        return TYPE_HORZEDITOR;
    }

    /**
     * refreshAppearance
     *
     * @author Anthony C. Liberto
     */
    public void refreshAppearance() {
        super.refreshAppearance();
        table.refreshAppearance();
        revalidate();
        repaint();
    }

    /*
     cr_0813025224
     */
    /**
     * getSelectedKeys
     *
     * @return
     */
    protected String[] getSelectedKeys() {
        if (table != null) {
            return table.getSelectedKeys();
        }
        return null;
    }

    /**
     * nlsRefresh
     *
     * @author Anthony C. Liberto
     */
    protected void nlsRefresh() {
        table.resizeAndPaint(false);
        table.resizeCells();
        freezeRefresh();
    }

/*
 cr_FlagUpdate
 */
    /**
     * processMaintAction
     *
     */
    protected void processMaintAction() {
		if (table != null) {
			processMaintAction(table.getSelectedEANMetaAttribute());
		}
	}

    /**
     * getEANMetaAttribute
     *
     * @param _r
     * @param _c
     * @return
     */
    protected EANMetaAttribute getEANMetaAttribute(int _r, int _c) {
		if (table != null) {
			return table.getEANMetaAttribute(_r,_c);
		}
		return null;
	}

    // old or unused methods

    /**
     * getColumnKeyArray
     * @return
     * @author Anthony C. Liberto
     * /
    public String[] getColumnKeyArray() { //22286
        return table.getColumnKeyArray(); //22286
    } //22286
*/

    /**
     * updateModel
     * @author Anthony C. Liberto
     * @param _table
     * /
    public void updateModel(RowSelectableTable _table) { //21722
        table.updateModel(_table); //21722
        return; //21722
    } //21722
    */
    /**
     * closeLocalMenus
     *
     * @author Anthony C. Liberto
     * /
    protected void closeLocalMenus() { //acl_Mem_20020130
        super.closeLocalMenus(); //acl_Mem_20020130
    } //acl_Mem_20020130

    /**
     * moveToError
     * @author Anthony C. Liberto
     * @param _mbre
     * /
    public void moveToError(MiddlewareBusinessRuleException _mbre) {
    }
    /*deprecated
    	public void moveToError(OPICMBusinessRuleException _opbre) {			//20020116
    		Point pt = table.moveToError(_opbre);
    		if (pt == null)
    			return;
    		if (pt.x >= table.getRowCount() || pt.x < 0) return;
    		if (pt.y >= table.getColumnCount() || pt.y < 0) return;
    		table.setRowSelectionInterval(pt.x,pt.x);
    		table.setColumnSelectionInterval(pt.y,pt.y);
    		table.scrollRectToVisible(table.getCellRect(pt.x,pt.y,false));
    		table.grabFocus();
    	}
    */

    /**
     * spellCheck
     *
     * @author Anthony C. Liberto
     * /
    public void spellCheck() {
        table.spellCheck();
    }*/
    /**
     * exportString
     *
     * @return
     * @author Anthony C. Liberto
     * /
    public String exportString() {
        return table.export();
    }

    /**
     * fillCopy
     *
     * @param _row
     * @author Anthony C. Liberto
     * /
    public void fillCopy(boolean _row) {
        table.fillCopy(_row);
    }

    /**
     * fillPaste
     *
     * @author Anthony C. Liberto
     * /
    public void fillPaste() {
        table.fillPaste();
    }

    /**
     * fillClear
     *
     * @author Anthony C. Liberto
     * /
    public void fillClear() {
        table.fillClear();
    }
    /**
     * sort
     *
     * @param _ascending
     * @author Anthony C. Liberto
     * /
    public void sort(boolean _ascending) {
        table.sort(_ascending);
    }

    /**
     * showEffectivity
     *
     * @author Anthony C. Liberto
     * /
    public void showEffectivity() {
        table.showEffectivity();
    }*/
    /**
     * filter
     *
     * @author Anthony C. Liberto
     * /
    public void filter() {
        table.showFilter();
    }

    /**
     * launchMatrix
     *
     * @author Anthony C. Liberto
     * /
    public void launchMatrix() {
    }

    /**
     * hasFocus
     *
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean hasFocus() {
        if (table != null && table.hasFocus()) {
            return true;
        }
        return false;
    }*/
    /**
     * deactivateAttribute
     *
     * @author Anthony C. Liberto
     * /
    public void deactivateAttribute() {
        if (table != null) {
            table.deactivateAttribute();
        }
    }

    /**
     * stopEditing
     *
     * @author Anthony C. Liberto
     * /
    public void stopEditing() { //20020107
        if (table != null) {
            table.stopEditing();
        }
    }

    /**
     * getAttributeCode
     *
     * @return
     * @author Anthony C. Liberto
     * /
    public String getAttributeCode() {
        //nfw		return table.getAttributeCode();
        return ""; //nfw
    }

    /**
     * selectAll
     *
     * @author Anthony C. Liberto
     * /
    public void selectAll() { //20020118
        table.selectAll();
    }

    /**
     * invertSelection
     *
     * @author Anthony C. Liberto
     * /
    public void invertSelection() { //20020118
        table.invertSelection();
    }*/
    /**
     * hasFiltered
     *
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean hasFiltered() {
        return table.isFiltered();
    }

    /**
     * getSelectionKey
     *
     * @return
     * @author Anthony C. Liberto
     * /
    public String getSelectionKey() {
        return table.getSelectionKey();
    }*/
    /**
     * historyInfo
     *
     * @author Anthony C. Liberto
     * /
    public void historyInfo() {
        table.historyInfo();
        return;
    }

    /**
     * commitDefault
     *
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean commitDefault() {
        return table.commitDefault();
    }*/

    /**
     * cancelDefault
     *
     * @author Anthony C. Liberto
     * /
    public void cancelDefault() {
        table.cancelDefault();
        return;
    }*/
    /**
     * prepareToEdit
     *
     * @author Anthony C. Liberto
     * /
    public void prepareToEdit() {
        table.prepareToEdit();
    }*/
    /**
     * hasRows
     *
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean hasRows() {
        return table.hasRows();
    }

    /**
     * hasColumns
     *
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean hasColumns() {
        return table.hasColumns();
    }

    /**
     * getSelectedObject
     *
     * @return
     * @author Anthony C. Liberto
     * /
    public Object getSelectedObject() { //21765
		if (table != null) {
	        return table.getSelectedObject(); //21765
		}
		return null;
    } //21765
*/
    /**
     * getSearchableObject
     *
     * @return
     * @author Anthony C. Liberto
     * /
    public Object getSearchableObject() { //22377
        return table; //22377
    } //22377
    /*
    	public void rekey(String _old, String _new) {			//21954
    		table.rekey(_old,_new);								//21954
    		return;												//21954
    	}														//21954

    	public void rekey(String[] _old, String[] _new ) {		//21954
    		table.rekey(_old,_new);								//21954
    		return;												//21954
    	}														//21954

    	public String[] getRowKeyArray() {						//21954
    		return table.getRowKeyArray();						//21954
    	}														//21954
    */
    /**
     * saveCurrentEdit
     *
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean saveCurrentEdit() { //22920
        return table.saveCurrentEdit(); //22920
    } //22920

    /**
     * increment
     *
     * @param _i
     * @author Anthony C. Liberto
     * /
    public void increment(int _i) {
    } //23265
    */
    /**
     * dump
     *
     * @param _brief
     * @return
     * @author Anthony C. Liberto
     * /
    public String dump(boolean _brief) {
        StringBuffer sb = new StringBuffer();
        sb.append("table.title   : " + table.getTableTitle() + RETURN);
        sb.append("table.rowCount: " + table.getRowCount() + RETURN);
        sb.append("table.colCount: " + table.getColumnCount() + RETURN);
        if (!_brief) {
            ///			sb = routines.appendArray(sb,"rowKey",table.getKeys());
            //			sb = routines.appendArray(sb,"rowKeyArray",table.getRowKeyArray());
            sb = ec.dump(sb, _brief);
        }
        return sb.toString();
    }

    /*
     logging
     */
    /**
     * highlight
     * @author Anthony C. Liberto
     * @param _s
     * /
    public void highlight(String[] _s) {
        if (table != null) {
            table.highlight(_s);
        }
    }

    /*
     acl_20030718
     */
    /**
     * \selectKeys
     * @author Anthony C. Liberto
     * @param _s
     * /
    public void selectKeys(String[] _s) {
        if (table != null) {
            table.selectKeys(_s);
        }
    }

    /*
     cr_TBD
     */
    /**
     * refreshTable
     *
     * @param _b
     * @author Anthony C. Liberto
     * /
    public void refreshTable(boolean _b) {
        if (table != null) {
            table.refreshTable(_b);
        }
        return;
    }

    /*
     52189
     */
    /**
     * select
     *
     * @author Anthony C. Liberto
     * /
    public void select() {
        if (table != null) {
            table.select();
        }
        return;
    }

    /**
     * deselect
     *
     * @author Anthony C. Liberto
     * /
    public void deselect() {
        if (table != null) {
            table.deselect();
        }
        return;
    }

    /*
     52354
     */
    /**
     * updateRecordLabel
     *
     * @param _i
     * @author Anthony C. Liberto
     * /
    public void updateRecordLabel(int _i) {
    }

    /*
     52476
     */

    /**
     * hasHiddenAttributes
     *
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean hasHiddenAttributes() {
        if (table != null) {
            return table.hasHiddenAttributes();
        }
        return false;
    }

    /*
     51832
     */
    /**
     * showHide
     *
     * @param _b
     * @author Anthony C. Liberto
     * /
    public void showHide(boolean _b) {
        if (table != null) {
            table.showHide(_b);
        }
        return;
    }*/

    /*
     USRO-R-RTAR-672S9Y
     */
    /**
     * isPasteable
     *
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean isPasteable() {
        if (table != null) {
            return table.isPasteable();
        }
        return false;
    }*/
    /**
     * isEditing
     *
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean isEditing() {
		if (table != null) {
			return table.isEditing();
		}
		return false;
	}

    /**
     * cancelEdit
     *
     * @author Anthony C. Liberto
     * /
    public void cancelEdit() {
		if (table != null) {
			table.cancelEdit();
		}
		return;
	}

    /**
     * commitEdit
     *
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean commitEdit() {
		if (table != null) {
			return table.canStopEditing();
		}
		return false;
	}*/
	/**
     * getSelectedEANMetaAttribute
     *
     * @return
     * @author Anthony C. Liberto
     * /
    public EANMetaAttribute getSelectedEANMetaAttribute() {
		return table.getSelectedEANMetaAttribute();
	}*/
}
