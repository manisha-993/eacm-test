/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: VertEditor.java,v $
 * Revision 1.4  2009/05/22 14:18:52  wendy
 * Performance cleanup
 *
 * Revision 1.3  2009/03/10 21:21:20  wendy
 * MN38666284 - CQ00022911:Creating elements from search not linking back to Workgroup
 *
 * Revision 1.2  2008/01/30 16:27:07  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:44:06  wendy
 * Reorganized JUI module
 *
 * Revision 1.5  2005/11/04 22:59:28  tony
 * adjusted
 *
 * Revision 1.4  2005/10/31 17:22:00  tony
 * VEEdit_Iteration2
 * Added create logic.
 *
 * Revision 1.3  2005/10/13 20:11:22  joan
 * adjust for PDG
 *
 * Revision 1.2  2005/09/12 19:03:12  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:54  tony
 * This is the initial load of OPICM
 *
 * Revision 1.20  2005/09/08 17:59:02  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.19  2005/06/30 20:59:44  tony
 * MN24151359
 *
 * Revision 1.18  2005/03/29 21:54:15  tony
 * rework of display workaround
 *
 * Revision 1.17  2005/03/04 19:48:31  tony
 * hide maintenance function when not available and
 * properly enable maintenance function
 *
 * Revision 1.16  2005/03/04 19:08:24  tony
 * improved cr_FlagUpdate
 *
 * Revision 1.15  2005/03/03 21:46:40  tony
 * cr_FlagUpdate
 * Added Flag Modification Capability.
 *
 * Revision 1.14  2005/03/01 19:30:11  tony
 * 6245 enahnced logic to query about current edit
 * before NLS is toggled.
 *
 * Revision 1.13  2005/02/23 20:22:01  tony
 * 6542
 *
 * Revision 1.12  2005/02/18 18:01:14  tony
 * cr_6542
 *
 * Revision 1.11  2005/02/03 16:38:53  tony
 * JTest Second Pass Wrapup
 *
 * Revision 1.10  2005/02/01 20:48:33  tony
 * JTest Second Pass
 *
 * Revision 1.9  2005/01/27 19:15:15  tony
 * JTest Format
 *
 * Revision 1.8  2004/11/29 18:14:37  tony
 * USRO-R-RTAR-672S9Y updated paste activation logic.
 *
 * Revision 1.7  2004/10/13 22:01:32  tony
 * TIR USRO-R-JSTT-65QSAL
 *
 * Revision 1.6  2004/08/19 15:12:05  tony
 * xl8r
 *
 * Revision 1.5  2004/06/14 16:37:08  tony
 * MN19599530
 *
 * Revision 1.4  2004/04/12 17:18:42  tony
 * MN_18698482
 *
 * Revision 1.3  2004/03/30 21:34:51  tony
 * CR_0813025214
 *
 * Revision 1.2  2004/02/27 18:54:22  tony
 * display statements
 *
 * Revision 1.1.1.1  2004/02/10 16:59:38  tony
 * This is the initial load of OPICM
 *
 * Revision 1.42  2004/01/05 17:00:51  tony
 * acl_20040104
 * updated logic to prevent null pointer on close tab.
 *
 * Revision 1.41  2003/10/31 17:30:49  tony
 * 52783
 *
 * Revision 1.40  2003/10/29 03:50:27  tony
 * acl_20031028
 * found nullpointer on new record logic.
 *
 * Revision 1.39  2003/10/29 00:19:19  tony
 * removed System.out. statements.
 *
 * Revision 1.38  2003/10/20 16:37:14  tony
 * memory update.  Updated dereference logic to close memory gaps
 *
 * Revision 1.37  2003/10/14 15:26:42  tony
 * 51832
 *
 * Revision 1.36  2003/10/08 20:12:57  tony
 * 52476
 *
 * Revision 1.35  2003/09/23 23:20:07  joan
 * fixes for upgrade
 *
 * Revision 1.34  2003/09/23 18:27:21  tony
 * 52354
 *
 * Revision 1.33  2003/09/12 16:14:27  tony
 * 52189
 *
 * Revision 1.32  2003/09/11 18:09:24  tony
 * acl_20030911
 * updated addRow logic to sort based on a boolean.
 *
 * Revision 1.31  2003/08/27 16:20:32  tony
 * 51945
 *
 * Revision 1.30  2003/08/27 16:02:23  tony
 * acl_20030827
 * updated toggling logic.
 *
 * Revision 1.29  2003/08/18 21:46:44  tony
 * 51803
 *
 * Revision 1.28  2003/08/18 15:38:48  tony
 * cr_TBD
 *
 * Revision 1.27  2003/08/13 23:05:01  tony
 * 51762
 *
 * Revision 1.26  2003/07/18 18:59:09  tony
 * acl_20030718 added selectKeys logic as guest
 * Entity  = current entity
 * Entity1 = parent Entity
 * Entity2 = Child Entity
 * then ',' then keys separated by ','.
 *
 * Revision 1.25  2003/07/15 18:24:14  joan
 * 51336
 *
 * Revision 1.24  2003/07/03 17:26:56  tony
 * updated logic to improve scripting
 *
 * Revision 1.23  2003/06/27 20:08:45  tony
 * updated logic to prevent validation when in search mode
 * for certain rules.
 *
 * Revision 1.22  2003/06/26 15:41:00  tony
 * MCI -- LAX
 * Updated logic for matrix, based on issues discovered by KC To La
 * Fixed filtering, editing, and matrix from edit for form and vert.
 *
 * Revision 1.21  2003/06/16 23:29:15  tony
 * 51257
 *
 * Revision 1.20  2003/06/05 17:07:38  tony
 * added refreshAppearance logic to the toolbar for the container.
 * This will flow thru to the scrollable popup.
 * Adjusted the sizing logic on the scrollable popup.
 *
 * Revision 1.19  2003/06/02 18:29:33  joan
 * 51068
 *
 * Revision 1.18  2003/05/30 22:47:26  tony
 * 51017
 *
 * Revision 1.17  2003/05/13 22:45:05  tony
 * 50616
 * Switched keys from a string to a pointer to the
 * EANFoundation.
 *
 * Revision 1.16  2003/05/13 16:07:43  tony
 * 50621
 *
 * Revision 1.15  2003/05/09 16:51:27  tony
 * updated filter icon enabling for filter.
 *
 * Revision 1.14  2003/05/02 20:05:55  tony
 * 50504 -- adjusted messaging and this seems to have
 * resolved the problem in local test.
 *
 * Revision 1.13  2003/04/22 16:39:11  tony
 * per dave updated logic to improve performance while
 * toggling between vertical and horizontal editors.
 *
 * Revision 1.12  2003/04/18 14:40:44  tony
 * enhanced record toggle logic per KC multiple create
 * did not toggle thru records properly.
 *
 * Revision 1.11  2003/04/15 22:01:33  tony
 * 50395 -- copy was looping.
 *
 * Revision 1.10  2003/04/03 16:19:08  tony
 * changed refreshLookAndFeel to refreshAppearance()
 * updated logic to take into account displayed dialogs
 * as well.
 *
 * Revision 1.9  2003/04/02 17:55:19  tony
 * added dump logic.
 *
 * Revision 1.8  2003/03/26 17:08:22  tony
 * removed system.out statement.
 *
 * Revision 1.7  2003/03/26 17:06:30  tony
 * per Joan adjusted logic on PDG so
 * that table is properly displayed.  Adjusted logic
 * by trapping possible null pointers.
 *
 * Revision 1.6  2003/03/24 19:56:14  tony
 * System enhancements to improve usability and
 * functionality of the application.
 *
 * Revision 1.5  2003/03/20 01:03:52  joan
 * add PDG Action item
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
 * Revision 1.106  2002/12/09 19:18:10  tony
 * acl_20021209 -- added resize cells to improve rollback logic.
 * 22051
 *
 * Revision 1.105  2002/12/03 21:54:19  tony
 * 23462
 *
 * Revision 1.104  2002/11/25 18:32:15  tony
 * adjusted logic so that search highlights (0,1) when
 * appropriate.
 *
 * Revision 1.103  2002/11/22 00:11:26  tony
 * 23337
 *
 * Revision 1.102  2002/11/20 18:03:57  tony
 * 23265b - enhancement to adjust menu enablement
 * base on the number of records displayed and the
 * number of records available.
 *
 * Revision 1.101  2002/11/20 17:10:00  tony
 * 23265
 *
 * Revision 1.100  2002/11/14 22:04:48  tony
 * 23173
 *
 * Revision 1.99  2002/11/11 22:54:21  tony
 * adjusted classification on the toggle
 *
 * Revision 1.98  2002/11/07 16:58:26  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.edit;
import com.ibm.eannounce.eforms.table.VertTable;
//import com.ibm.eannounce.eforms.editor.MetaValidator;
import com.ibm.eannounce.eforms.toolbar.*;
import com.ibm.eannounce.dialogpanels.EditPDGPanel;
import com.ibm.eannounce.eobjects.*;
import java.awt.*;
import java.beans.*;
import COM.ibm.eannounce.objects.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class VertEditor extends EannounceEditor implements PropertyChangeListener {
	private static final long serialVersionUID = 1L;
	private VertTable table = null;
    private RecordToggle recToggle = null;
    private EditPDGPanel m_pdgPanel = null;

    protected Editable getEditable() { return table;}
    /**
     * vertEditor
     * @param _eList
     * @param _key
     * @param _ec
     * @param _rec
     * @author Anthony C. Liberto
     */
    protected VertEditor(EntityList _eList, String _key, EditController _ec, RecordToggle _rec) {
        super( _ec, DefaultToolbarLayout.EDIT_VERT_BAR, _rec);
        recToggle = _rec;
        buildTable(_eList, _key);
        createScrollPane(table);
        createMenus("Vertical Toolbar", true);
        table.addMouseListener(popup);
        setTitle(ec.getTable().getTableTitle()); //22165
        init();
    }

    /**
     * vertEditor
     * @param _ec
     * @param _sai
     * @author Anthony C. Liberto
     */
    protected VertEditor(EditController _ec, SearchActionItem _sai) { //dyna
        super(_ec); //dyna
        table = new VertTable(null, _ec.getTable()) {
        	private static final long serialVersionUID = 1L;
        	public void addRowImport() {//xl8r
                addImportRow(); //xl8r
            } //xl8r
        }; //dyna
        table.setEditController(_ec); //dyna
        table.setKey("DYNA"); //dyna
        table.setSearch(true); //dwb_20030527
        if (!table.isEmpty()) { //acl_20021125
            table.setRowSelectionInterval(0, 0); //acl_20021125
            table.setColumnSelectionInterval(1, 1); //acl_20021125
        } //acl_20021125

        createScrollPane(table, new Dimension(500, 500)); //dyna
        init();
    } //dyna

    /**
     * vertEditor
     * @param _ec
     * @param _pdgai
     * @author Anthony C. Liberto
     */
    public VertEditor(EditController _ec, PDGActionItem _pdgai, EditPDGPanel _pdgPanel) { //pdg
        super(_ec);
        m_pdgPanel = _pdgPanel;
        table = new VertTable(null, _ec.getTable()) {
        	private static final long serialVersionUID = 1L;
        	public void addRowImport() {//xl8r
                addImportRow(); //xl8r
            } //xl8r
        };
        table.setEditController(_ec);
        table.setVertEditor(this);
        table.setControllingTable(_ec.getTable()); //51068
        table.setKey("PDG");
        table.setModalCursor(true); //51317

        if (!table.isEmpty()) {
            table.setRowSelectionInterval(0, 0);
            table.setColumnSelectionInterval(1, 1);
        }

        createScrollPane(table, new Dimension(500, 500));
        init();
        //		setTitle(ec.getTable().getTableTitle());
    }

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
    private void buildTable(EntityList _eList, String _key) {
        EANFoundation ean = null;
        //51803		recToggle.setTable(ec.getTable());
        recToggle.setTable(ec.getTable(), ec.getTable().getRowIndex(_key)); //51803
        recToggle.addPropertyChangeListener(this);
        //		EANFoundation ean = ec.getTable().getRow(0);
        if (_key != null) {
            ean = ec.getTable().getRow(_key);
        } else {
            ean = ec.getTable().getRow(0);
        }
        if (ean != null) {
            if (table != null) {
                table.dereference();
            }
            ec.loadNLSTree((EntityItem)ean);							//cr_6245
            table = new VertTable(_eList, ((EntityItem) ean).getEntityItemTable()) {
            	private static final long serialVersionUID = 1L;
            	public void addRowImport() {//xl8r
                    addImportRow(); //xl8r
                } //xl8r
            };
            table.setControllingTable(ec.getTable());
            table.setEditController(ec);
            table.setKey(ean.getKey()); //19924
            table.hasHiddenAttributes(); //52476
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
     * @author Anthony C. Liberto
     */
    private void toggleRecord(int _i) {
        String key = ec.getTable().getRowKey(_i);
        toggleRecord(key, ec.getTable().getRow(key));
        if (recToggle != null) { //MN19599530
            ec.setEnabled("next", recToggle.isNextEnabled()); //51017
            ec.setEnabled("prev", recToggle.isPrevEnabled()); //51017
        } //MN19599530
    }

    /**
     * toggleRecord
     * @param _key
     * @author Anthony C. Liberto
     */
    private void toggleRecord(String _key) {
        if (!table.canContinue()) { //22455
            return;
        } //22455
        if (_key == null) {
            toggleRecord(0);
        } else {
            toggleRecord(_key, ec.getTable().getRow(_key));
        }
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
        boolean bDefault = false;
        if (_key.equals(table.getKey())) {
            ec.setEnabled("next", recToggle.isNextEnabled()); //51017
            ec.setEnabled("prev", recToggle.isPrevEnabled()); //51017
            table.refreshClassification();
            table.resizeCells();
            return;
        }
        table.toggleRecord(_key, _ean);
        i = ec.getTable().getRowIndex(_key);
        if (recToggle != null) { //MN19599530
            recToggle.setCurrent(i, false);
        } //MN19599530
        //dwb_20030421		table.resizeCells();
        bNew = isNew();
        setEnabled("remove", bNew);
        ec.setEnabled("remove", bNew);

        bDefault = ec.canEditDefault() && bNew; //51257
        ec.setEnabled("dsave", bDefault); //51257
        ec.setEnabled("dcncl", bDefault); //51257

        ec.setSelectorEnabled(bNew);
        ec.setSelectedParent(table.getCurrentEntityItem());
        if (recToggle != null) { //MN19599530
            ec.setEnabled("next", recToggle.isNextEnabled());
            ec.setEnabled("prev", recToggle.isPrevEnabled());
        } //MN19599530
        table.refreshClassification();
        table.resizeCells(); //dwb_20030421 row not found issue
        ec.refreshUpdate();
    }

    /**
     * isNew
     *
     * @return
     */
    protected boolean isNew() { //19937
        return isNew(table.getKey()); //19937
    } //19937

    /**
     * isNew
     * @param _key
     * @return
     * @author Anthony C. Liberto
     */
    private boolean isNew(String _key) { //19937
        if (ec != null) { //TIR USRO-R-JSTT-65QSAL
            Object o = ec.getTable().getRow(_key); //19937
            if (o != null && o instanceof EntityItem) { //19937
                return (((EntityItem) o).isNew() && table.getMasterRowCount() >= 1); //22252
                //22252			return (((EntityItem)o).isNew() && table.getMasterRowCount() > 1);		//22150
                //22150			return ((EntityItem)o).isNew();					//19937
            }
        } //TIR USRO-R-JSTT-65QSAL
        return false; //19937
    } //19937
    
    /**
     * refresh
     */
    protected void refresh() {
        table.resizeAndPaint(false);
        table.resizeCells();
        toggleRecord(0); //20020110
    }

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
        } //22825
        table.unlockRows();
        repaint();
    }

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
        RowSelectableTable rst = null;
        int row = -1;
        if (!table.canContinue()) { //22455
            return;
        } //22455
        copies = getNumberCopies();
        if (copies < 0) {
            return;
        }
        rst = ec.getTable();
        table.duplicate(copies);
        row = rst.getRowCount() - 1;

        recToggle.setCurrent(row, true);
    }

    /**
     * print
     */
    protected void print() {
        table.print();
    }

    /**
     * showInformation
     */
    protected void showInformation() {
        table.showInformation();
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
     * find
     */
    protected void find() {
        table.showFind();
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
        if (table.isDynaTable()) { //dyna
            return true;
        } //dyna

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
                action = showConfirm(YES_NO_CANCEL, true); //j20020402
            }

            //acl_20040104			repaintImmediately();
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
                    //51762					ec.commit();			//23337
                    //23337					commit();
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
     * @author Anthony C. Liberto
     */
    public void dereference() {
        if (recToggle != null) { //dyna
            recToggle.removePropertyChangeListener(this);

            closeLocalMenus(); //acl_Mem_20020130
            recToggle.dereference();
            recToggle = null;
        } //dyna

        if (table != null) {
            table.removeMouseListener(popup);
            table.dereference();
            //table.removeAll();
            //table.removeNotify();
            table = null;
        }

        super.dereference();
    }

    /**
     * removeExtra
     *
     */
    protected void removeExtra() {
        RowSelectableTable rst = ec.getTable();

        int r = rst.getRowIndex(table.getKey());
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
        int r = rst.getRowIndex(table.getKey());
        rst.setParentEntityItem(r, _parent);
    }

    /**
     * getRecordKey
     *
     * @return
     */
    protected String getRecordKey() {
        return table.getKey();
    }

    /**
     * setSelection
     * @param _recKey
     * @param _selKey
     */
    protected void setSelection(String _recKey, String _selKey) {
        if (_recKey == null && _selKey == null) {
            table.resetSelection(0, 1);
            return;
        }
        if (_recKey != null) {
            toggleRecord(_recKey);
        }
        table.setSelection(_recKey, _selKey);
    }

    /**
     * rollback
     */
    protected void rollback() {
        //		table.rollback();
        RowSelectableTable rst = null;
        if (table.isEditing()) { //22552
            table.cancelEdit(); //22552
        } //22552
        rst = ec.getTable();
        if (rst != null) {
            rst.rollback();
            table.resizeCells(); //acl_20021209
            repaint();
        }
    }

    /**
     * rollbackRow
     *
     */
    protected void rollbackRow() {
        if (table.isEditing()) { //23173
            table.cancelEdit(); //23173
        } //23173
        //22842		table.rollbackRow();
        table.rollback(); //22842
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
        boolean b = table.commit(); //21954
        if (b) { //21954
            //			table.setKey(recToggle.getCurrentKey());	//21954
            if (recToggle != null) {
                if (table.updateKey(recToggle.getCurrentKey())) { //22098
                    lock(); //22098
                } //22098
            }
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

    //acl_20030911	public void addRow() {
    private void addImportRow() { //xl8r
        addRow(false); //xl8r
    } //xl8r

    /**
     * addRow
     *
     * @param _sort
     */
    protected void addRow(boolean _sort) {
        RowSelectableTable rst = null;
        int row = -1;
        //System.out.println("**** verTable.addRow ****");
        if (table.isEditing() && !table.canStopEditing()) {
            return;
        }
        boolean success=false; //MN38666284 - CQ00022911
        rst = ec.getTable();
		if (ec != null && ec.isVEEdit()) {
			success = rst.addRow(getRecordKey());
		} else {
			success = rst.addRow();
		}
		if (success){
			row = rst.getRowCount() - 1;
			recToggle.setCurrent(row, true);
		}else{
			setMessage("Error adding row, see log for details");
			showError();
		}
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
            if (isNew(rst.getRowKey(i))) { //19920
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
     * moveToErrorPDG
     * @param _ebre
     * @author Anthony C. Liberto
     */
    public void moveToErrorPDG(EANBusinessRuleException _ebre) { //51068
        table.moveToErrorPDG(_ebre);
    }

    /**
     * showSort
     */
    protected void showSort() {
        table.showSort();
    }

    /**
     * canContinue
     *
     * @return
     * @author Anthony C. Liberto
     */
    protected boolean canContinue() { //22624
        return table.canContinue(); //22624
    } //22624

    /**
     * cancel
     *
     * @author Anthony C. Liberto
     */
    protected void cancel() { //22624
        table.cancelEdit(); //22624
    } //22624

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
        return TYPE_VERTEDITOR;
    }

    /**
     * refreshAppearance
     *
     * @author Anthony C. Liberto
     */
    public void refreshAppearance() {
        super.refreshAppearance();
        table.refreshAppearance();
    }

    /*
     acl_20030827
     */
    /**
     * synchronize
     *
     * @author Anthony C. Liberto
     */
    protected void synchronize() {
        if (table != null) {
            table.resizeCells();
        }
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
        if (table != null) {
            String[] out = new String[1];
            out[0] = table.getKey();
            return out;
        }
        return null;
    }

    /**
     * nlsRefresh
     *
     */
    protected void nlsRefresh() {
		table.resizeCells();
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

	public void getPDGInfo(EANMetaAttribute _meta) {
		//System.out.println("VertEditor getPDGInfo");
		if (m_pdgPanel != null) {
			m_pdgPanel.getPDGInfo(_meta);
		}
	}
	// old or unused
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
     * /
    public boolean isPrevEnabled() { //23265
        if (recToggle != null) { //23265
            return recToggle.isPrevEnabled(); //23265
        } //23265
        return false; //23265
    } //23265
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
    /**
     * closeLocalMenus
     *
     * @author Anthony C. Liberto
     * 
    protected void closeLocalMenus() { //acl_Mem_20020130
        super.closeLocalMenus(); //acl_Mem_20020130
    } //acl_Mem_20020130
    */
    /*
	private void toggleRecord(int _i, String _key) {
		table.toggleRecord(_key);
		recToggle.setCurrent(_i,false); 						//20020110
//22165		setTitle(ec.getTable().getTableTitle());
		table.resizeCells();
		boolean bNew = isNew();
		setEnabled("remove", bNew);						//19937
		ec.setEnabled("remove", bNew);
		ec.setSelectorEnabled(bNew);
		ec.setSelectedParent(table.getCurrentEntityItem());			//22542
		ec.setEnabled("next", recToggle.isNextEnabled());		//23265
		ec.setEnabled("prev", recToggle.isPrevEnabled());		//23265
		table.refreshClassification();
		ec.refreshUpdate();							//22780
//22542		ec.setSelectedParent(getSelectedObject());
		return;
	}
*/
/**
 * getSelectedObject
 *
 * @return
 * @author Anthony C. Liberto
 * /
public Object getSelectedObject() { 		//21765
	if (table != null) {					//MN24151359
        return table.getSelectedObject(); 	//21765
	}										//MN24151359
	return null;							//MN24151359
} 											//21765
*/
    /**
     * spellCheck
     *
     * @author Anthony C. Liberto
     * /
    public void spellCheck() {
        table.spellCheck();
    }   
    /**
     * moveToError
     * @author Anthony C. Liberto
     * @param _mbre
     * /
    public void moveToError(MiddlewareBusinessRuleException _mbre) {
    }*/
    /**
     * exportString
     *
     * @return
     * @author Anthony C. Liberto
     * /
    public String exportString() {
        return table.export();
    }*/
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
        table.sort(_ascending);
    }
*/
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
    }
/*
    private String orderKey() {
        int ii = eList.getEntityGroupCount();
        for (int i = 0; i < ii; ++i) {
            if (eList.getEntityGroup(i).isDisplayable()) {
                return "hor" + eList.getEntityGroup(i).getEntityType();
            }
        }
        return null;
    }
*/
    /**
     * deactivateAttribute
     *
     * @author Anthony C. Liberto
     * /
    public void deactivateAttribute() {
        if (table != null) {
            table.deactivateAttribute();
        }
        return;
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
        return;
    }

    /**
     * getAttributeCode
     *
     * @return
     * @author Anthony C. Liberto
     * /
    public String getAttributeCode() {
        //nfw		return table.getAttributeCode();
        return "";
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
    }*/
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
     *
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
     * getSearchableObject
     *
     * @return
     * @author Anthony C. Liberto
     * /
    public Object getSearchableObject() { //22377
        return table; //22377
    } //22377
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
            sb = ec.dump(sb, _brief);
        }
        return sb.toString();
    }
    /*
     50621
    */
    /**
     * getValidator
     *
     * @return
     * @author Anthony C. Liberto
     * /
    public MetaValidator getValidator() {
        if (table != null) {
            return table.getValidator(); //50621
        }
        return null;
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
        return;
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
        if (table != null) {
            table.selectKeys(_s);
        }
        return;
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
    }*/
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
   }*/
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
     * getSelectedEANMetaAttribute
     *
     * @return
     * @author Anthony C. Liberto
     * /
    public EANMetaAttribute getSelectedEANMetaAttribute() {
		return table.getSelectedEANMetaAttribute();
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
}
