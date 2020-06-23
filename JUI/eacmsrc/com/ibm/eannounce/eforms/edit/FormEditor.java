/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: FormEditor.java,v $
 * Revision 1.5  2009/05/22 14:18:52  wendy
 * Performance cleanup
 *
 * Revision 1.4  2008/02/20 15:45:56  wendy
 * Prevent nullptr if deref called more than once
 *
 * Revision 1.3  2008/02/13 19:34:26  wendy
 * Display wait cursor when loading togglerecord
 *
 * Revision 1.2  2008/01/30 16:27:07  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:44:06  wendy
 * Reorganized JUI module
 *
 * Revision 1.4  2005/11/04 22:59:28  tony
 * adjusted
 *
 * Revision 1.3  2005/10/31 17:21:59  tony
 * VEEdit_Iteration2
 * Added create logic.
 *
 * Revision 1.2  2005/09/12 19:03:12  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:54  tony
 * This is the initial load of OPICM
 *
 * Revision 1.16  2005/09/08 17:59:02  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.15  2005/03/29 21:54:15  tony
 * rework of display workaround
 *
 * Revision 1.14  2005/03/04 19:48:31  tony
 * hide maintenance function when not available and
 * properly enable maintenance function
 *
 * Revision 1.13  2005/03/04 19:08:24  tony
 * improved cr_FlagUpdate
 *
 * Revision 1.12  2005/03/03 21:46:40  tony
 * cr_FlagUpdate
 * Added Flag Modification Capability.
 *
 * Revision 1.11  2005/03/01 19:30:11  tony
 * 6245 enahnced logic to query about current edit
 * before NLS is toggled.
 *
 * Revision 1.10  2005/02/18 18:01:14  tony
 * cr_6542
 *
 * Revision 1.9  2005/02/03 16:38:53  tony
 * JTest Second Pass Wrapup
 *
 * Revision 1.8  2005/02/01 20:48:33  tony
 * JTest Second Pass
 *
 * Revision 1.7  2005/01/27 19:15:15  tony
 * JTest Format
 *
 * Revision 1.6  2004/11/29 18:14:36  tony
 * USRO-R-RTAR-672S9Y updated paste activation logic.
 *
 * Revision 1.5  2004/10/13 22:01:32  tony
 * TIR USRO-R-JSTT-65QSAL
 *
 * Revision 1.4  2004/08/19 15:31:37  tony
 * xl8r
 *
 * Revision 1.3  2004/03/30 21:34:52  tony
 * CR_0813025214
 *
 * Revision 1.2  2004/02/27 18:54:22  tony
 * display statements
 *
 * Revision 1.1.1.1  2004/02/10 16:59:38  tony
 * This is the initial load of OPICM
 *
 * Revision 1.37  2004/01/05 17:00:51  tony
 * acl_20040104
 * updated logic to prevent null pointer on close tab.
 *
 * Revision 1.36  2003/10/29 03:50:27  tony
 * acl_20031028
 * found nullpointer on new record logic.
 *
 * Revision 1.35  2003/10/29 00:19:19  tony
 * removed System.out. statements.
 *
 * Revision 1.34  2003/10/22 17:15:46  tony
 * 52650
 * DEREFERENCE MODS FOR MEMORY
 *
 * Revision 1.33  2003/10/20 16:37:14  tony
 * memory update.  Updated dereference logic to close memory gaps
 *
 * Revision 1.32  2003/10/16 20:39:21  tony
 * 52494
 *
 * Revision 1.31  2003/10/14 15:26:41  tony
 * 51832
 *
 * Revision 1.30  2003/10/08 20:11:56  tony
 * 52476
 *
 * Revision 1.29  2003/09/23 18:27:21  tony
 * 52354
 *
 * Revision 1.28  2003/09/15 20:51:04  tony
 * 52189
 *
 * Revision 1.27  2003/09/12 16:14:27  tony
 * 52189
 *
 * Revision 1.26  2003/09/11 18:09:24  tony
 * acl_20030911
 * updated addRow logic to sort based on a boolean.
 *
 * Revision 1.25  2003/08/21 15:57:33  tony
 * 51869
 *
 * Revision 1.24  2003/08/18 21:46:44  tony
 * 51803
 *
 * Revision 1.23  2003/08/18 15:38:47  tony
 * cr_TBD
 *
 * Revision 1.22  2003/08/13 23:05:01  tony
 * 51762
 *
 * Revision 1.21  2003/07/18 18:59:09  tony
 * acl_20030718 added selectKeys logic as guest
 * Entity  = current entity
 * Entity1 = parent Entity
 * Entity2 = Child Entity
 * then ',' then keys separated by ','.
 *
 * Revision 1.20  2003/07/09 20:47:34  tony
 * 51422 -- blobEditor popup on double click
 *
 * Revision 1.19  2003/07/03 17:26:56  tony
 * updated logic to improve scripting
 *
 * Revision 1.18  2003/06/26 15:41:00  tony
 * MCI -- LAX
 * Updated logic for matrix, based on issues discovered by KC To La
 * Fixed filtering, editing, and matrix from edit for form and vert.
 *
 * Revision 1.17  2003/06/05 17:07:37  tony
 * added refreshAppearance logic to the toolbar for the container.
 * This will flow thru to the scrollable popup.
 * Adjusted the sizing logic on the scrollable popup.
 *
 * Revision 1.16  2003/06/03 17:15:00  tony
 * 51083
 *
 * Revision 1.15  2003/05/30 22:47:26  tony
 * 51017
 *
 * Revision 1.14  2003/05/22 16:23:13  tony
 * 50874 -- filter, find, and sort adjust the object they
 * function on dynamically.
 *
 * Revision 1.13  2003/05/12 22:19:04  tony
 * 50611
 *
 * Revision 1.12  2003/05/07 18:03:23  tony
 * 50560
 *
 * Revision 1.11  2003/05/02 20:05:54  tony
 * 50504 -- adjusted messaging and this seems to have
 * resolved the problem in local test.
 *
 * Revision 1.10  2003/05/01 17:14:15  tony
 * cleaned-up code
 *
 * Revision 1.9  2003/04/29 19:53:14  tony
 * fixed looping issue on doubleClick lock()
 *
 * Revision 1.8  2003/04/22 16:39:10  tony
 * per dave updated logic to improve performance while
 * toggling between vertical and horizontal editors.
 *
 * Revision 1.7  2003/04/18 14:40:44  tony
 * enhanced record toggle logic per KC multiple create
 * did not toggle thru records properly.
 *
 * Revision 1.6  2003/04/03 16:19:08  tony
 * changed refreshLookAndFeel to refreshAppearance()
 * updated logic to take into account displayed dialogs
 * as well.
 *
 * Revision 1.5  2003/03/21 22:38:33  tony
 * form accessibilty update.
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
 * Revision 1.110  2002/12/03 21:54:19  tony
 * 23462
 *
 * Revision 1.109  2002/11/22 00:11:26  tony
 * 23337
 *
 * Revision 1.108  2002/11/20 18:03:57  tony
 * 23265b - enhancement to adjust menu enablement
 * base on the number of records displayed and the
 * number of records available.
 *
 * Revision 1.107  2002/11/20 17:10:00  tony
 * 23265
 *
 * Revision 1.106  2002/11/12 23:33:36  tony
 * removed dumpStack()
 *
 * Revision 1.105  2002/11/12 19:15:21  tony
 * modification to allow for editable to be updated on the
 * fly.  Meaning if refreshEnabled then represent the
 * editorInterface to allow for editing toggles.
 *
 * Revision 1.104  2002/11/11 23:28:52  tony
 * rollback fix on formEditor
 *
 * Revision 1.103  2002/11/11 22:54:21  tony
 * adjusted classification on the toggle
 *
 * Revision 1.102  2002/11/09 00:01:58  tony
 * acl_20021108 changed JSplitPane to GSplitPane to
 * simplify controls and add functionality.  This should
 * assist in accessibility concerns.
 *
 * Revision 1.101  2002/11/07 18:59:16  tony
 * 22979
 *
 * Revision 1.100  2002/11/07 16:58:26  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.edit;
import com.elogin.*;
import com.ibm.eannounce.eforms.editform.*;
import com.ibm.eannounce.eforms.editor.*;
import com.ibm.eannounce.eforms.toolbar.*;
import com.ibm.eannounce.eobjects.*;
import COM.ibm.eannounce.objects.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class FormEditor extends EannounceEditor implements PropertyChangeListener, MouseListener, EAccessConstants {
	private static final long serialVersionUID = 1L;
	private RecordToggle recToggle = null;

    private EditForm table = null;
    private String curKey = null;
    
    protected Editable getEditable() { return table;}

    /**
     * formEditor
     * @param _eList
     * @param _key
     * @param _ec
     * @param _rec
     * @author Anthony C. Liberto
     */
    protected FormEditor(String _key, EditController _ec, RecordToggle _rec) {
        super(_ec, DefaultToolbarLayout.EDIT_FORM_BAR, _rec);
        recToggle = _rec;
        buildTable( _key);
        createScrollPane(table);
        createMenus("Form Toolbar", false);
        table.addMouseListener(this);
        table.addMouseListener(popup);
        setTitle(ec.getTable().getTableTitle()); //22165
        init(); //TIR USRO-R-JSTT-65QSAL
    }

    /**
     * formEditor
     * @param _ec
     * @param _sai
     * @author Anthony C. Liberto
     */
    protected FormEditor(EditController _ec, SearchActionItem _sai) { //dyna
        super(_ec); //dyna
        table = new EditForm(_ec, _sai) {
        	private static final long serialVersionUID = 1L;
        	public void addRowImport() {//xl8r
                addImportRow(); //xl8r
                return; //xl8r
            } //xl8r
        }; //dyna
        createScrollPane(table); //dyna
        init(); //TIR USRO-R-JSTT-65QSAL
    } //dyna

    private void init() { //TIR USRO-R-JSTT-65QSAL
        boolean bNew = isNew(); //TIR USRO-R-JSTT-65QSAL
        setEnabled("remove", bNew); //TIR USRO-R-JSTT-65QSAL
        if (ec != null) { //TIR USRO-R-JSTT-65QSAL
            ec.setEnabled("remove", bNew); //TIR USRO-R-JSTT-65QSAL
        } //TIR USRO-R-JSTT-65QSAL
    } //TIR USRO-R-JSTT-65QSAL

    /**
     * buildTable
     * @param _eList
     * @param _key
     * @author Anthony C. Liberto
     */
    private void buildTable( String _key) {
        EANFoundation ean = null;
        String formName = ec.getFormName();
        //51803		recToggle.setTable(ec.getTable());
        recToggle.setTable(ec.getTable(), ec.getTable().getRowIndex(_key)); //51803
        recToggle.addPropertyChangeListener(this);
        if (_key != null) {
            ean = ec.getTable().getRow(_key);
        } else {
            ean = ec.getTable().getRow(0);
        }
        if (ean != null) {
			ec.loadNLSTree((EntityItem)ean);							//cr_6245
            table = new EditForm(formName, ec) {
            	private static final long serialVersionUID = 1L;
            	public void addRowImport() {//xl8r
                    addImportRow(); //xl8r
                } //xl8r
            };
            //52494			table.setControllingTable(ec.getTable(), 0);
            table.setControllingTable(ec.getTable(), 0, false);
            curKey = ean.getKey();
        }
    }

    //21923	public void revalidateForm() {
    /**
     * revalidateForm
     * @param _att
     * @author Anthony C. Liberto
     */
    protected void revalidateForm(EANAttribute _att) { //21923
        if (table != null) {
            if (table.isAutoRefresh(_att)) { //21923
                table.refreshAll(); //21923
                //				formRefresh();									//21923
            } //21923
            table.revalidate();
        }
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
     * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
     * @author Anthony C. Liberto
     */
    public void propertyChange(PropertyChangeEvent _pce) {
        String property = _pce.getPropertyName();
        if (property.equals(RECORD_TOGGLE)) {
            toggleRecord((String) _pce.getOldValue(), (EANFoundation) _pce.getNewValue());
        }
    }

    /**
     * toggleRecord
     * @param _i
     */
    private void toggleRecord(int _i) {
        String key = ec.getTable().getRowKey(_i);
        toggleRecord(key, ec.getTable().getRow(key));
        ec.setEnabled("next", recToggle.isNextEnabled()); //51017
        ec.setEnabled("prev", recToggle.isPrevEnabled()); //51017
    }

    /**
     * toggleRecord
     * @param _key
     */
    private void toggleRecord(String _key) {
        if (_key == null) {
            toggleRecord(0);
        } else {
            toggleRecord(_key, ec.getTable().getRow(_key));
        }
    }
    /**
     * allow wait cursor to be shown when moving from record to record (togglerecord)
     */
    public Cursor getCursor() {
    	return eaccess().getCursor();
    }
    /**
     * toggleRecord
     * @param _key
     * @param _ean
     * @author Anthony C. Liberto
     */
    private void toggleRecord(String _key, EANFoundation _ean) {
        int i = -1;
        boolean bNew = false;
        eaccess().setBusy(true);   //allow wait cursor to be shown when moving from record to record     
        
        if (_key.equals(table.getKey())) {
            ec.setEnabled("next", recToggle.isNextEnabled()); //51017
            ec.setEnabled("prev", recToggle.isPrevEnabled()); //51017
            table.partialRefresh();
            eaccess().setBusy(false);  //allow wait cursor to be shown when moving from record to record
            return;
        }
       
        table.toggleRecord(_key, _ean, true);
        curKey = _key;
        i = ec.getTable().getRowIndex(_key);
        recToggle.setCurrent(i, false);
        bNew = isNew();
        setEnabled("remove", bNew);
        ec.setEnabled("remove", bNew);
        ec.setSelectorEnabled(bNew);
        ec.setSelectedParent(table.getSelectedObject());
        ec.setEnabled("next", recToggle.isNextEnabled());
        ec.setEnabled("prev", recToggle.isPrevEnabled());
        table.partialRefresh();
        ec.refreshUpdate();
        eaccess().setBusy(false);  //allow wait cursor to be shown when moving from record to record
    }

    /**
     * isNew
     *
     * @return
     */
    protected boolean isNew() { //21765
        return table.isNew(); //21765
    } //21765
    /**
     * refresh
     */
    protected void refresh() {
        toggleRecord(0); //20020111
    }

    /*
     * lock and unlock
     */
    /**
     * lock
     *
     */
    protected void lock() {
        table.lock();
    }

    /**
     * unlock
     *
     */
    protected void unlock() {
        if (!okToClose(true)) {
            return;
        } //22825
        table.unlockRows();
        table.regenerateForm();
    }

    /**
     * cut
     *
     */
    protected void cut() {
        Component c = eaccess().getFocusOwner();
        if (c != null && c instanceof EditorInterface) {
            ((EditorInterface) c).cut();
        }
    }

    /**
     * copy
     *
     */
    protected void copy() {
        Component c = eaccess().getFocusOwner();
        if (c != null && c instanceof EditorInterface) {
            ((EditorInterface) c).copy();
        }
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
     * paste
     */
    protected void paste() {
        Component c = eaccess().getFocusOwner();
        if (c != null && c instanceof EditorInterface) {
            ((EditorInterface) c).paste();
        }
    }

    /*
     * print
     */
    /**
     * print
     *
     */
    protected void print() {
    	eaccess().print(table);
    }


    /**
     * find
     */
    protected void find() {
    	eaccess().show(FIND_PANEL, false);;
    }

    /**
     * showInformation
     *
     */
    protected void showInformation() {
        String info = table.getInformation();
        eaccess().showScrollDialog(info);
    }

    /**
     * getHelpText
     *
     * @return
     */
    protected String getHelpText() {
        EANMetaAttribute meta = table.getCurrentEANMetaAttribute();
        if (meta != null) {
            return meta.getHelpValueText();
        }
        return getString("nia");
    }

    /**
     * okToClose
     *
     * @param _reset
     * @return
     */
    protected boolean okToClose(boolean _reset) {
        int action = -1;
        if (hasMasterChanges()) { //j20020402
            if (getEntityItemCount() > 1) { //j20020402
                setCode("updtMsg4");
                action = showConfirm(ALL_NONE_CHOOSE_CANCEL, true);
                if (action == 0) { //19967
                    return ec.commitAll(); //19967
                } else if (action == 2) { //j20020402
                    return chooseToSave(); //j20020402
                } //j20020402
            } else { //j20020402
                setCode("updtMsg2");
                action = showConfirm(YES_NO_CANCEL, true);
            }
            //acl_20040104			eaccess().repaintImmediately();
            if (action == 0) {
                return ec.commit(); //22219
                //22219				return commit();
            } else if (action == 1) {
                if (_reset) {
                    rollback();
                }
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    private boolean chooseToSave() { //j20020402
        RowSelectableTable rst = ec.getTable();
        EANFoundation[] ean = rst.getTableRowsAsArray();
        int action = -1;
        for (int i = 0; i < ean.length; i++) {
            RowSelectableTable t = ((EntityItem) ean[i]).getEntityItemTable();
            if (t.hasChanges()) {
                toggleRecord(i);
                setCode("updtMsg2");
                action = showConfirm(YES_NO_CANCEL, true);
                if (action == 0) {
                    if (!ec.commit()) { //51762
                        return false; //51762
                    } //51762
                } else if (action == 1) {
                    //23462					rollback();
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
     */
    public void dereference() {
        if (table != null) {
            table.removeMouseListener(this);
            table.removeMouseListener(popup);
            table.dereference();
            table = null;
        }

        if (recToggle != null) {
        	recToggle.removePropertyChangeListener(this);        	
            recToggle.dereference();
            recToggle = null;
        }

        super.dereference();
    }

    /**
     * removeExtra
     */
    protected void removeExtra() {
        RowSelectableTable rst = ec.getTable();
        int r = rst.getRowIndex(curKey);
        int row = -1;
        rst.removeRow(r);
        row = rst.getRowCount() - 1;

        if (row < 0) { //21810
            rst.addRow(); //21810
            row = 0; //21810
        } //21810

        recToggle.setCurrent(row, true);
    }

    /**
     * setParentItem
     * @param _parent
     */
    protected void setParentItem(EntityItem _parent) {
        RowSelectableTable rst = ec.getTable();
        int r = rst.getRowIndex(curKey);
        rst.setParentEntityItem(r, _parent);
    }

    /**
     * formRefresh
     */
    protected void formRefresh() {
        table.partialRefresh(); //classification
    }

    /**
     * duplicate
     *
     * @author Anthony C. Liberto
     */
    protected void duplicate() {
        int copies = -1;
        RowSelectableTable rst = null;
        int row = -1;
        if (!saveCurrentEdit()) { //23017
            return;
        } //23017
        copies = getNumberCopies();
        if (copies < 0) {
            return;
        }
        table.duplicate(copies);
        rst = ec.getTable();
        row = rst.getRowCount() - 1;
        recToggle.setCurrent(row, true);
    }

    /**
     * getRecordKey
     *
     * @return
     */
    protected String getRecordKey() {
        return curKey;
    }
    /**
     * setSelection
     * @param _colKey
     * @param _recKey
     */
    protected void setSelection(String _recKey, String _colKey) {
        EditorInterface ei = null;
        toggleRecord(_recKey);
        ei = table.getEditorInterface(_colKey);
        if (ei != null) {
            ei.requestFocus();
        }
    }

    /**
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mousePressed(MouseEvent _me) {
    }
    /**
     * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseReleased(MouseEvent _me) {
    }
    /**
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseEntered(MouseEvent _me) {
    }
    /**
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseExited(MouseEvent _me) {
    }
    /**
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseClicked(MouseEvent _me) {
        Component c = null;
        if (SwingUtilities.isLeftMouseButton(_me)) {
            if (_me.getClickCount() == 2) {
                if (!isBusy()) {
                    setBusy(true);
                    if (_me.isShiftDown()) {
                        unlock();
                    } else {
                        lock();
                        c = _me.getComponent(); //22979
                        if (c != null && c instanceof EditorInterface) { //22979
                            EditorInterface ei = (EditorInterface) c; //22979
                            if (!ei.isDisplayOnly()) { //22979
                                requestFocus(); //22979
                                ei.prepareToEdit(_me, this); //51869

                            } //22979
                        } //22979
                    }
                    setBusy(false);
                }
            }
        }
    }

    /**
     * rollback
     *
     */
    protected void rollback() {
        RowSelectableTable rst = ec.getTable(); //22842
        if (rst != null) { //22842
            rst.rollback();
        } //22842
        table.refreshAll();
    }

    /**
     * rollbackRow
     */
    protected void rollbackRow() {
        table.rollbackRow();
    }

    /**
     * rollbackSingle
     *
     */
    protected void rollbackSingle() {
        table.rollbackSingle();
    }

    /**
     * commit
     *
     * @return
     */
    protected boolean commit() {
        boolean b = table.commit(); //21954
        if (b) { //21954
            curKey = new String(recToggle.getCurrentKey()); //21954
            //22098			table.setKey(curKey);							//21954
            if (table.updateKey(curKey)) { //22098
                lock(); //22098
            } //22098
        } //21954
        return b; //21954
        //21954		return table.commit();
    }

    /**
     * hasChanges
     *
     * @return
     */
    protected boolean hasChanges() {
        return table.hasChanges();
    }

    private void addImportRow() { //xl8r
        addRow(false); //xl8r
    } //xl8r

    //acl_20030911	public void addRow() {
    /**
     * addRow
     *
     * @param _sort
     */
    protected void addRow(boolean _sort) { //acl_20030911
        RowSelectableTable rst = ec.getTable();
        int row = -1;
        //String key = null;
        if (ec != null && ec.isVEEdit()) {
			rst.addRow(getRecordKey());
		} else {
        	rst.addRow();
		}
        row = rst.getRowCount() - 1;
        //key = rst.getRowKey(row);
        recToggle.setCurrent(row, true);
    }

    /**
     * getEntityItems
     *
     * @return
     */
    protected EntityItem[] getEntityItems() {
        Object o = recToggle.getCurrentTable(getRecordKey()); //acl_20031028

        if (o instanceof EntityItem) {
            EntityItem[] out = new EntityItem[1];
            out[0] = (EntityItem) o;
            return out;
        }
        return null;
    }

    /**
     * removeNewRows
     *
     */
    protected void removeNewRows() { //19920
        RowSelectableTable rst = ec.getTable(); //19920
        boolean bToggle = false; //22051
        int rr = rst.getRowCount(); //19920
        for (int i = (rr - 1); i >= 0; --i) { //19920
            if (table.isNew(rst.getRowKey(i))) { //19920
                rst.removeRow(i); //19920
                bToggle = true; //22051
            } //19920
        } //19920
        if (rst.getRowCount() == 0) { //19920
            rst.addRow(); //19920
        } //19920
        if (bToggle) { //22051
            toggleRecord(0);
        } //19920
    } //19920

    /**
     * moveToError
     * @param _ebre
     */
    protected void moveToError(EANBusinessRuleException _ebre) { //20069
        table.moveToError(_ebre);
        toggleRecord(table.getKey());
    }

    /**
     * increment
     *
     * @param _i
     */
    protected void increment(int _i) { //23265
        if (recToggle != null) { //23265
            recToggle.increment(_i); //23265
        } //23265
    } //23265

    /**
     * getPanelType
     *
     * @return
     */
    public String getPanelType() {
        return TYPE_FORMEDITOR;
    }

    /**
     * refreshAppearance
     *
     * @author Anthony C. Liberto
     */
    public void refreshAppearance() {
        super.refreshAppearance();
    }

    /*
     51083
     */
    /**
     * synchronize
     *
     * @author Anthony C. Liberto
     */
    protected void synchronize() {
        refreshAppearance();
    }

    /*
     52354
     */
    /**
     * updateRecordLabel
     *
     * @param _i
     */
    protected void updateRecordLabel(int _i) {
        if (recToggle != null) {
            recToggle.updateRecordLabel(_i);
        }
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
        String[] out = new String[1];
        out[0] = curKey;
        return out;
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
			processMaintAction(table.getCurrentEANMetaAttribute());
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
			return table.getCurrentEANMetaAttribute();
		}
		return null;
	}
    // old stuff or unused

    /**
     * isNextEnabled
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean isNextEnabled() { //23265
        if (recToggle != null) { //23265
            return recToggle.isNextEnabled(); //23265
        } //23265
        return false; //23265
    } //23265

    /**
     * isPrevEnabled
     * @return
     * @author Anthony C. Liberto
     *  /
    public boolean isPrevEnabled() { //23265
        if (recToggle != null) { //23265
            return recToggle.isPrevEnabled(); //23265
        } //23265
        return false; //23265
    } //23265
*/
    /**
     * deactivateAttribute
     *
     * @author Anthony C. Liberto
     * /
    public void deactivateAttribute() {
        table.deactivate(); //22819
    }

    /**
     * stopEditing
     *
     * @author Anthony C. Liberto
     * /
    public void stopEditing() { //20020107
    }

    /**
     * selectAll
     *
     * @author Anthony C. Liberto
     * /
    public void selectAll() {
    } //20020118

    /**
     * invertSelection
     *
     * @author Anthony C. Liberto
     * /
    public void invertSelection() {
    } //20020118
    */
    /**
     * showLayout
     *
     * @author Anthony C. Liberto
     * /
    public void showLayout() {
        table.showLayout();
    }*/
    /*
    private void paste(int _i) {
    }
*/
    /**
     * hasFiltered
     *
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean hasFiltered() {
        return false;
    }
    /**
     * getSelectionKey
     *
     * @return
     * @author Anthony C. Liberto
     * /
    public String getSelectionKey() {
        return null;
    }*/
    /**
     * historyInfo
     *
     * @author Anthony C. Liberto
     * /
    public void historyInfo() {
        table.historyInfo();
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
     * showSort
     *
     * @author Anthony C. Liberto
     * /
    public void showSort() {
    }

    /**
     * hasRows
     *
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean hasRows() {
        RowSelectableTable rst = ec.getTable();
        if (rst != null) {
            if (rst.getRowCount() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * hasColumns
     *
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean hasColumns() {
        RowSelectableTable rst = ec.getTable();
        if (rst != null) {
            if (rst.getColumnCount() > 0) {
                return true;
            }
        }
        return false;

    }

    /**
     * getSearchableObject
     *
     * @return
     * @author Anthony C. Liberto
     * /
    public Object getSearchableObject() { //22377
        return table; //22377
    } //22377

    /**
     * saveCurrentEdit
     *
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean saveCurrentEdit() { //22794
        return table.saveCurrentEdit(); //22794
    } //22794
*/
    /**
     * highlight
     * @author Anthony C. Liberto
     * @param _s
     * /
    public void highlight(String[] _s) {
    }

    /*
     acl_20030718
     */
    /**
     * selectKeys
     * @author Anthony C. Liberto
     * @param _s
     * /
    public void selectKeys(String[] _s) {
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
    }

    /*
     52189
     */
    /**
     * deselect
     *
     * @author Anthony C. Liberto
     * /
    public void deselect() {
        if (table != null) {
            table.deselect();
        }
    }

    /**
     * select
     *
     * @author Anthony C. Liberto
     * /
    public void select() {
        if (table != null) {
            table.select();
        }
    }*/

    /**
     * hasHiddenAttributes
     *
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean hasHiddenAttributes() {
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
    }*/
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
    }

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

    /**
     * hasFocus
     *
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean hasFocus() {
        if (table != null) { //20020118
            if (table.hasFocus()) {
                return true;
            }
        }
        return false;
    }

    /**
     * filter
     *
     * @author Anthony C. Liberto
     * /
    public void filter() {
    }

    /**
     * launchMatrix
     *
     * @author Anthony C. Liberto
     * /
    public void launchMatrix() {
    }

    /**
     * freeze
     *
     * @author Anthony C. Liberto
     * /
    public void freeze() {
    }

    /**
     * thaw
     *
     * @author Anthony C. Liberto
     * /
    public void thaw() {
    }*/

    /**
     * showEffectivity
     *
     * @author Anthony C. Liberto
     * /
    public void showEffectivity() {
    }*/

    /**
     * exportString
     *
     * @return
     * @author Anthony C. Liberto
     * /
    public String exportString() {
        return null;
    }
    
    /**
     * fillCopy
     *
     * @param _row
     * @author Anthony C. Liberto
     * /
    public void fillCopy(boolean _row) {
    }

    /**
     * fillPaste
     *
     * @author Anthony C. Liberto
     * /
    public void fillPaste() {
    }

    /**
     * fillClear
     *
     * @author Anthony C. Liberto
     * /
    public void fillClear() {
    }

    /**
     * moveColumn
     *
     * @param _left
     * @author Anthony C. Liberto
     * /
    public void moveColumn(boolean _left) {
    }
    /**
     * sort
     *
     * @param _ascending
     * @author Anthony C. Liberto
     * /
    public void sort(boolean _ascending) {
    }

    /**
     * getAttributeCode
     *
     * @return
     * @author Anthony C. Liberto
     * /
    public String getAttributeCode() {
        return "nfw";
        //nfw		return table.getCurrentAttributeCode();
    }*/

    /**
     * spellCheck
     *
     * @author Anthony C. Liberto
     * /
    public void spellCheck() {
        table.spellCheck();
    }
        /*
    	private void toggleRecord(int _i, String _key) {
    		table.toggleRecord(_key,true);
    		curKey = _key;
    		recToggle.setCurrent(_i,false); 						//20020110
    //22165		setTitle(ec.getTable().getTableTitle());
    		boolean bNew = isNew();
    		setEnabled("remove", bNew);					//19937
    		ec.setEnabled("remove", bNew);
    		ec.setSelectorEnabled(bNew);
    		ec.setSelectedParent(table.getSelectedObject());
    		ec.setEnabled("next", recToggle.isNextEnabled());		//23265
    		ec.setEnabled("prev", recToggle.isPrevEnabled());		//23265
    		table.partialRefresh();
    		ec.refreshUpdate();							//22780
    		return;
    	}
    */
    /**
     * getSelectedObject
     *
     * @return
     * @author Anthony C. Liberto
     * /
    public Object getSelectedObject() { //21765
        return table.getSelectedObject(); //21765
    } //21765

    //	public void moveToError(OPICMBusinessRuleException _opbre) {}			//20020116
    /**
     * moveToError
     * @author Anthony C. Liberto
     * @param _mbre
     * /
    public void moveToError(MiddlewareBusinessRuleException _mbre) {
    } //20020116
    */

    /**
     * updateModel
     * @author Anthony C. Liberto
     * @param _table
     * /
    public void updateModel(RowSelectableTable _table) { //21722
        table.updateModel(_table); //21722
    } //21722
    */
}
