/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: HorzTable.java,v $
 * Revision 1.10  2012/04/10 21:11:24  wendy
 * fillcopy/fillpaste perf updates
 *
 * Revision 1.9  2009/10/01 19:50:52  wendy
 * SR 14 date warning
 *
 * Revision 1.8  2009/05/28 13:50:52  wendy
 * Performance cleanup
 *
 * Revision 1.7  2009/05/26 13:46:26  wendy
 * Performance cleanup
 *
 * Revision 1.6  2009/04/16 17:53:55  wendy
 * Cleanup button enablement
 *
 * Revision 1.5  2009/02/18 17:26:04  wendy
 * MN38316169 : was picking wrong entity
 *
 * Revision 1.4  2008/04/29 19:18:22  wendy
 * MN35270066 VEEdit rewrite
 *
 * Revision 1.3  2008/01/30 16:26:58  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.2  2007/05/31 19:53:11  wendy
 * Prevent hang when creating a SWFEATURE from MODEL
 *
 * Revision 1.1  2007/04/18 19:45:12  wendy
 * Reorganized JUI module
 *
 * Revision 1.8  2007/04/12 19:34:53  wendy
 * TIR6ZNSHV continued, Fill copy entity updates
 *
 * Revision 1.7  2007/04/12 14:30:16  couto
 * TIR6ZNSHV - per Wendy: recursive check for attributes on VE Edit.
 *
 * Revision 1.6  2007/04/12 13:33:33  couto
 * TIR6ZNSHV - Fill past was not working for VE Edits.
 *
 * Revision 1.5  2005/12/15 19:07:36  tony
 * added comments for testing purposes.
 *
 * Revision 1.4  2005/11/15 16:55:12  tony
 * adjusted dup logic for VEEdit
 *
 * Revision 1.3  2005/10/31 17:22:00  tony
 * VEEdit_Iteration2
 * Added create logic.
 *
 * Revision 1.2  2005/09/12 19:03:15  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:38:04  tony
 * This is the initial load of OPICM
 *
 * Revision 1.17  2005/09/08 17:59:07  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.16  2005/08/16 22:45:01  tony
 * MN_25042000
 *
 * Revision 1.15  2005/03/03 21:46:40  tony
 * cr_FlagUpdate
 * Added Flag Modification Capability.
 *
 * Revision 1.14  2005/02/09 18:55:24  tony
 * Scout Accessibility
 *
 * Revision 1.13  2005/01/31 20:47:47  tony
 * JTest Second Pass
 *
 * Revision 1.12  2005/01/26 22:17:56  tony
 * JTest Modifications
 *
 * Revision 1.11  2005/01/25 22:56:05  tony
 * MN TBD-DK
 *
 * Revision 1.10  2004/11/29 18:14:37  tony
 * USRO-R-RTAR-672S9Y updated paste activation logic.
 *
 * Revision 1.9  2004/11/18 18:02:37  tony
 * adjusted keying logic based on middleware changes.
 *
 * Revision 1.8  2004/11/09 21:26:33  tony
 * isIndicateRelations()
 *
 * Revision 1.7  2004/10/11 20:57:53  tony
 * added debug functionality.
 *
 * Revision 1.6  2004/10/05 19:45:09  tony
 * TIR USRO-R-JSTT-65GLT2
 *
 * Revision 1.5  2004/09/10 18:00:26  tony
 * MN20687400 This may need to be modified in the future as
 * I was not able to recreate the problem.  But this should help
 *
 * Revision 1.4  2004/06/24 20:29:26  tony
 * TIR USRO-R-SWWE-629MHH
 *
 * Revision 1.3  2004/04/12 17:18:42  tony
 * MN_18698482
 *
 * Revision 1.2  2004/02/26 21:53:17  tony
 * remoteDatabaseInterface centralization.
 *
 * Revision 1.1.1.1  2004/02/10 16:59:45  tony
 * This is the initial load of OPICM
 *
 * Revision 1.83  2004/01/08 17:40:27  tony
 * 53510
 *
 * Revision 1.82  2004/01/05 17:00:51  tony
 * acl_20040104
 * updated logic to prevent null pointer on close tab.
 *
 * Revision 1.81  2003/11/24 17:24:31  tony
 * 52887
 *
 * Revision 1.80  2003/11/14 20:45:09  tony
 * 52862
 *
 * Revision 1.79  2003/11/12 18:23:04  tony
 * updated logic to read row header if visible
 *
 * Revision 1.78  2003/11/11 00:42:21  tony
 * accessibility update, added convenience method to table.
 *
 * Revision 1.77  2003/11/10 22:09:07  tony
 * accessibility
 *
 * Revision 1.76  2003/10/29 16:47:39  tony
 * 52728
 *
 * Revision 1.75  2003/10/29 00:14:03  tony
 * removed System.out. statements.
 *
 * Revision 1.74  2003/10/24 21:11:28  tony
 * 52719
 *
 * Revision 1.73  2003/10/22 17:12:03  tony
 * 52674
 *
 * Revision 1.72  2003/10/20 16:37:15  tony
 * memory update.  Updated dereference logic to close memory gaps
 *
 * Revision 1.71  2003/10/10 23:47:14  tony
 * 52521
 *
 * Revision 1.70  2003/10/08 22:17:41  tony
 * removed display statement
 *
 * Revision 1.69  2003/10/08 20:11:06  tony
 * 52476
 *
 * Revision 1.68  2003/10/03 16:39:12  tony
 * updated accessibility.
 *
 * Revision 1.67  2003/10/02 18:23:26  tony
 * updated for accessibility...
 * logic for screen reader updated.
 *
 * Revision 1.66  2003/09/24 19:34:22  tony
 * 52380
 *
 * Revision 1.65  2003/09/19 18:10:05  tony
 * 52113
 * 52323
 *
 * Revision 1.64  2003/09/16 18:19:13  tony
 * 52275
 *
 * Revision 1.63  2003/09/15 20:49:26  tony
 * 52151
 *
 * Revision 1.62  2003/09/12 16:13:31  tony
 * 52189
 *
 * Revision 1.61  2003/09/11 18:09:24  tony
 * acl_20030911
 * updated addRow logic to sort based on a boolean.
 *
 * Revision 1.60  2003/09/11 17:23:52  tony
 * 52167
 * updated attribute put logic to display exception for
 * duplicate and fill paste
 *
 * Revision 1.59  2003/09/11 16:30:20  tony
 * 52148
 * update FillPaste and Duplicate functions.
 *
 * Revision 1.58  2003/09/11 00:13:25  tony
 * 52148
 *
 * Revision 1.57  2003/09/10 00:09:11  tony
 * 52113
 * 52115
 *
 * Revision 1.56  2003/09/08 20:44:54  tony
 * 51732
 *
 * Revision 1.55  2003/09/04 21:13:52  tony
 * 52009
 *
 * Revision 1.54  2003/09/04 18:25:40  tony
 * 52052
 *
 * Revision 1.53  2003/09/04 17:47:23  tony
 * 52045
 *
 * Revision 1.52  2003/09/04 14:56:30  tony
 * 52026
 *
 * Revision 1.51  2003/09/03 20:14:00  tony
 * 52030
 *
 * Revision 1.50  2003/09/02 16:18:37  tony
 * 52017
 *
 * Revision 1.49  2003/08/28 17:48:53  tony
 * 51974
 *
 * Revision 1.48  2003/08/27 16:02:23  tony
 * acl_20030827
 * updated toggling logic.
 *
 * Revision 1.47  2003/08/21 22:20:25  tony
 * 51732
 *
 * Revision 1.46  2003/08/21 18:07:12  tony
 * 51865
 * 51869
 *
 * Revision 1.45  2003/08/21 15:58:13  tony
 * 51869
 *
 * Revision 1.44  2003/08/20 17:59:48  tony
 * 51834
 *
 * Revision 1.43  2003/08/19 14:45:08  tony
 * 51804
 *
 * Revision 1.42  2003/08/18 21:45:57  tony
 * 51798
 *
 * Revision 1.41  2003/08/18 17:28:06  tony
 * 51780
 *
 * Revision 1.40  2003/07/28 16:12:36  tony
 * 51522
 *
 * Revision 1.39  2003/07/22 15:47:19  tony
 * updated date editor capability to improve performance and
 * functionality.
 *
 * Revision 1.38  2003/07/10 20:38:47  tony
 * 51433 removed superEditCellAt method and calls.
 *
 * Revision 1.37  2003/07/10 18:02:20  tony
 * 51433
 *
 * Revision 1.36  2003/07/09 20:47:35  tony
 * 51422 -- blobEditor popup on double click
 *
 * Revision 1.35  2003/07/08 18:42:08  tony
 * 51414
 *
 * Revision 1.34  2003/06/27 20:09:11  tony
 * updated logic to prevent validation when in search mode
 * for certain rules.
 * updated messageing.
 *
 * Revision 1.33  2003/06/20 22:34:17  tony
 * 1.2 modification.
 *
 * Revision 1.32  2003/06/19 16:09:43  tony
 * 51298 -- limited capability when working in the past.
 * updated past date logic.
 *
 * Revision 1.31  2003/06/16 23:29:14  tony
 * 51257
 *
 * Revision 1.30  2003/06/10 16:46:49  tony
 * 51260
 *
 * Revision 1.29  2003/06/09 19:20:12  tony
 * 51242
 *
 * Revision 1.28  2003/05/30 21:09:23  tony
 * updated messaging logic to popup on top
 * of the currently active Component.
 *
 * Revision 1.27  2003/05/29 15:46:42  tony
 * 50985
 *
 * Revision 1.26  2003/05/28 22:48:05  tony
 * 50957
 *
 * Revision 1.25  2003/05/28 20:42:16  tony
 * 50973
 *
 * Revision 1.24  2003/05/28 19:35:19  tony
 * 50952
 *
 * Revision 1.23  2003/05/28 17:37:35  tony
 * 50960
 *
 * Revision 1.22  2003/05/21 15:38:12  tony
 * updated table logic to allow for the table and model
 * to always know the specific type of table.  Based on a
 * table constant.
 *
 * Revision 1.21  2003/05/20 21:35:22  tony
 * 50827
 *
 * Revision 1.20  2003/05/20 21:06:59  tony
 * updated logic on table edit to allow all editors to
 * behave similarly.
 *
 * Revision 1.19  2003/05/13 22:45:05  tony
 * 50616
 * Switched keys from a string to a pointer to the
 * EANFoundation.
 *
 * Revision 1.18  2003/05/08 22:31:53  tony
 * 50550
 *
 * Revision 1.17  2003/04/29 17:24:40  tony
 * cleaned-up longEditor to properly display to
 * enhance editing functionality.
 *
 * Revision 1.16  2003/04/25 19:20:08  tony
 * added border to tables
 *
 * Revision 1.15  2003/04/24 22:23:17  tony
 * updated multi-editor renderinf logic.
 *
 * Revision 1.14  2003/04/24 21:49:09  tony
 * fixed ctrl-a multiflag issue per kc
 *
 * Revision 1.13  2003/04/23 22:05:19  tony
 * improved readibility of code.
 *
 * Revision 1.12  2003/04/21 20:03:13  tony
 * enhanced scroll logic to improve flag editor.
 *
 * Revision 1.11  2003/04/18 15:42:42  tony
 * enhnaced multiEditor functionality.
 *
 * Revision 1.10  2003/04/16 17:20:50  tony
 * replaced has paste with can paste
 *
 * Revision 1.9  2003/04/15 17:31:34  tony
 * changed to e-announce.focusborder
 *
 * Revision 1.8  2003/04/14 21:38:25  tony
 * updated table Logic.
 *
 * Revision 1.7  2003/03/27 22:50:47  tony
 * adjusted vectorMap logic
 *
 * Revision 1.6  2003/03/26 19:16:31  tony
 * xmlEditor, integrated XMLeditor.
 *
 * Revision 1.5  2003/03/24 19:56:14  tony
 * System enhancements to improve usability and
 * functionality of the application.
 *
 * Revision 1.4  2003/03/05 19:16:49  tony
 * AttributeChangeHistoryGroup addition.
 *
 * Revision 1.3  2003/03/05 18:54:26  tony
 * accessibility updates.
 *
 * Revision 1.2  2003/03/04 16:52:55  tony
 * added logic for EntityHistoryGroup
 *
 * Revision 1.1.1.1  2003/03/03 18:03:50  tony
 * This is the initial load of OPICM
 *
 * Revision 1.135  2002/12/09 19:14:31  tony
 * 23522 removed auto sort
 *
 * Revision 1.134  2002/12/06 17:22:28  tony
 * acl_20021206 -- added synchronize logic to the tables.
 *
 * Revision 1.133  2002/12/02 18:39:11  tony
 * 23421
 *
 * Revision 1.132  2002/11/26 16:37:39  tony
 * 23373
 *
 * Revision 1.131  2002/11/22 22:13:36  tony
 * 23355
 *
 * Revision 1.130  2002/11/22 21:30:36  tony
 * 23351
 *
 * Revision 1.129  2002/11/19 23:58:08  tony
 * 23269
 *
 * Revision 1.128  2002/11/19 18:16:48  tony
 * 23268
 *
 * Revision 1.127  2002/11/19 16:06:18  tony
 * 23263
 *
 * Revision 1.126  2002/11/18 18:21:05  tony
 * 23239
 *
 * Revision 1.125  2002/11/15 16:48:02  tony
 * 23187
 *
 * Revision 1.124  2002/11/12 22:18:59  tony
 * 22833b
 *
 * Revision 1.123  2002/11/11 22:55:41  tony
 * adjusted classification on the toggle
 *
 * Revision 1.122  2002/11/07 16:58:33  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.table;
import com.elogin.*;
import com.ibm.eannounce.dialogpanels.XMLPanel;
import com.ibm.eannounce.eforms.edit.EditController;
import com.ibm.eannounce.eforms.edit.Editable;
import com.ibm.eannounce.eforms.editor.*;
import com.ibm.eannounce.eobjects.*;
import com.ibm.eannounce.erend.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import javax.swing.event.*; //copy

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class HorzTable extends RSTable implements ETable, ActionListener, AncestorListener,Editable  { //20020107
	private static final long serialVersionUID = 1L;
	private HeaderTable header = new HeaderTable();

    private LockedFoundSingleRenderer lockedFoundRend = new LockedFoundSingleRenderer();
    private LockedSingleRenderer lockedRend = new LockedSingleRenderer();
    private FoundSingleRenderer foundRend = new FoundSingleRenderer();

    private TextEditor edit = null;
    private BlobEditor blobEdit = null;
    private FlagEditor flagEdit = null;
    private LongEditor longEdit = null;
    private DateTimeEditor dateEdit = null;
    private XMLPanel xmlEdit = null;
    private DateTimeEditor timeEdit = null;
    private MultiEditor multiEdit = null;

    //23421	private boolean filterOn = false;
    private MetaValidator validator = new MetaValidator(); //20020207

    private ERowList rList = new ERowList();
    //22713	private String[] fillKeys = null;

    private boolean rowHeaderVisible = false;
    private Fillvector vFill = new Fillvector(); //22713

    /**
     * horzTable
     * @param _o
     * @param _table
     * @author Anthony C. Liberto
     */
    public HorzTable(Object _o, RowSelectableTable _table) {
        super(_o, _table, TABLE_HORIZONTAL);
        init();
        if (_table != null) {
            //51298			setReplaceable(_table.canEdit());
            setReplaceable(eaccess().isEditable(_table)); //51298
        }
        //51780		rList.setFixedCellHeight(getRowHeight());
        rList.setTable(this); //51780
    }
    /**
     * horzTable
     * @param _o
     * @param _table
     * @param _ec
     * @author Anthony C. Liberto
     */
    public HorzTable(EntityList _o, RowSelectableTable _table, EditController _ec) { //TIR USRO-R-SWWE-629MHH
        super(_o, _table, _ec, TABLE_HORIZONTAL); //TIR USRO-R-SWWE-629MHH
        init(); //TIR USRO-R-SWWE-629MHH
        if (_table != null) { //TIR USRO-R-SWWE-629MHH
            setReplaceable(eaccess().isEditable(_table)); //TIR USRO-R-SWWE-629MHH
        } //TIR USRO-R-SWWE-629MHH
        rList.setTable(this); //TIR USRO-R-SWWE-629MHH
    } //TIR USRO-R-SWWE-629MHH

    /**
     * generateEditor
     * @author Anthony C. Liberto
     */
    private void generateEditor() {
        edit = new TextEditor();
        blobEdit = new BlobEditor();
        flagEdit = new FlagEditor();
        longEdit = new LongEditor();
        dateEdit = new DateTimeEditor(DateTimeEditor.DATE_VALIDATOR);
        xmlEdit = eaccess().getXMLPanel();
        timeEdit = new DateTimeEditor(DateTimeEditor.TIME_VALIDATOR);
        multiEdit = new MultiEditor(false);
    }

    /*
     * eliminates sizing problem
     */
    /**
     * @see javax.swing.JTable#getValueAt(int, int)
     * @author Anthony C. Liberto
     */
    public Object getValueAt(int _r, int _c) {
        return getValueAt(_r, _c, false);
    }

    /**
     * @see javax.swing.JTable#getRowMargin()
     * @author Anthony C. Liberto
     */
    public int getRowMargin() {
        return rowMargin;
    }

    /**
     * getColumnByKey
     * @param _col
     * @return
     * @author Anthony C. Liberto
     * /
    public EANFoundation getColumnByKey(int _col) {
        int col = convertColumnIndexToModel(_col);
        return cgtm.getColumn(col);
    }*/

    /**
     * isHidden
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean isHidden() {
        int cc = getColumnCount();
        TableColumn tc = null;
        for (int c = 0; c < cc; ++c) {
            tc = getColumnModel().getColumn(c);
            if (tc.getWidth() == 0) {
                return true;
            }
        }
        return false;
    }*/
/*
    private void setTableColumnWidth(RSTableColumn _tc, int _width) {
        boolean hidden = (_width == 0);
        if (_width == Integer.MAX_VALUE) {
            _width = 15;
        }
        if (hidden) {
            _tc.setMinWidth(_width);
            _tc.setMaxWidth(_tc.getWidth());
        } else {
            //52728			_tc.setMinWidth(_tc.getMinimumPreferredWidth());
            _tc.setMinWidth(_tc.getMinimumAllowableWidth()); //52728
        }
        _tc.setWidth(_width);
        _tc.setPreferredWidth(_width);
        _tc.setResizable(!hidden); //19950
    }*/
  
    /**
     * @see javax.swing.JTable#prepareEditor(javax.swing.table.TableCellEditor, int, int)
     * @author Anthony C. Liberto
     */
    public Component prepareEditor(TableCellEditor _editor, int _row, int _col) {
        Object o = getEANObject(_row, _col);
        boolean isSelected = isCellSelected(_row, _col);
        Component comp = _editor.getTableCellEditorComponent(this, o, isSelected, _row, _col);
        return comp;
    }

    /**
     * @see javax.swing.JTable#editCellAt(int, int, java.util.EventObject)
     * @author Anthony C. Liberto
     */
    public boolean editCellAt(int _r, int _c, EventObject _e) {
        boolean displayOnly = false;
        KeyEvent ke = null;
        int iClick = 0;
        int code = -1;
        boolean b = false;
        EANAttribute ean = null;
        LockGroup lockGroup = null;
        Object o = null;
        if (isViewing()) {
            removeViewer();
        }
        if (!isEditable()) {
            viewCellAt(_r, _c, _e); //22248
            return false;
        }

        if (_e != null && _e instanceof MouseEvent) {
            iClick = ((MouseEvent) _e).getClickCount();
            if (iClick > 2) {
                return false;
            }
        }

        if (isEditing() && editingCanStop()) {
            return false;
        } else if (_e == null) {
            return false;
        } else if (iClick == 1) {
            return false;
        } else if (_e instanceof KeyEvent) {
            ke = (KeyEvent) _e;
            code = ke.getKeyCode();
            if (code == KeyEvent.VK_F2 || ke.isControlDown() || ke.isMetaDown() || ke.isActionKey() || ke.isAltDown() || !Character.isDefined(ke.getKeyChar())) {
                return false;
            } else if (code == KeyEvent.VK_DELETE) {
                repaint();
                return false;
            }
        }

        if (!isCellLocked(_r, _c)) {
            displayOnly = true;
            if (!isCellLocked(_r, _c, true)) {
                displayOnly = true;
                ean = getAttribute(_r, _c);
                lockGroup = getLockGroup(_r, _c); //lock_update
                if (lockGroup != null) { //lock_update
                    setMessage(lockGroup.toString());
                    showError();
                } //lock_update
                if (!(ean instanceof BlobAttribute)) {
                    return false;
                }
            } else {
                displayOnly = false;
                repaint();
            }
        }

        b = super.editCellAt(_r, _c, _e); //20011227

        if (!b) { //22248
            if (xmlEdit.isShowing()) { //53510
                xmlEdit.processEvent(_e); //53510
            } //53510
            return false; //22248
        } //22248

        o = getCellEditor();

        if (o instanceof EditorInterface) {
            //51422			((editorInterface)o).prepareToEdit();
            //51869			((editorInterface)o).prepareToEdit(_e);				//51422
            ((EditorInterface) o).prepareToEdit(_e, this); //51869
        }

        if (o instanceof MultiEditor) {
            if (_e instanceof KeyEvent) {
                //52674				((multiEditor)o).showPopup();
                ((MultiEditor) o).showPopup((KeyEvent) _e); //52674
            }
            ((MultiEditor) o).requestFocus();
        } else if (o instanceof BlobEditor) {
            ((BlobEditor) o).setDisplayOnly(displayOnly);
        } else if (o instanceof FlagEditor && ke != null) { //52380
            ((FlagEditor) o).preloadKeyEvent(ke); //52887
		} else if (o instanceof LongEditor && ke != null) {	//MN_25042000
			((LongEditor)o).preloadKeyEvent(ke);			//MN_25042000
        } //52380

        return b; //20011227
    }

    /**
     * setPopupDisplayable
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setPopupDisplayable(boolean _b) {
        popupDisplayable = _b;
    }

    /**
     * init
     *
     * @author Anthony C. Liberto
     */
    public void init() {
        generateEditor();
        initAccessibility("accessible.horzTable");
        setRowMargin(0);
        setBorder(UIManager.getBorder("eannounce.focusborder"));
        setColumnSelectionAllowed(false);
        setAutoResizeMode(AUTO_RESIZE_OFF);
        resizeCells();
        //23522		sort();
        setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        setCellSelectionEnabled(true); //20020116
        setBorder(UIManager.getBorder("eannounce.focusBorder"));
        if (getRowCount() > 0) {
            setRowSelectionInterval(0, 0);
        }

        //21805		LongRenderer lr = new LongRenderer();
        //		setDefaultRenderer(PDHMetaMultiFlag.class, new FlagRenderer());
        //		setDefaultRenderer(PDHMetaLongText.class, lr);
        //		setDefaultRenderer(PDHMetaXMLText.class, lr);
        setDefaultRenderer(Object.class, new SingleRenderer());
        addAncestorListener(this); //20020107
    }

    /**
     * getCellRenderer
     *
     * @param _r
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public TableCellRenderer getCellRenderer(int _r, int _c) {
        boolean isFound = isFound(_r, _c);
        boolean isLocked = isCellEditable(_r, _c);
        TableCellRenderer rend = null;
        getRowClass(_r);
        rend = super.getCellRenderer(_r, _c);

        if (isLocked) {
            if (isFound) {
                return lockedFoundRend;
            }
            return lockedRend;
        }
        if (isFound) {
            return foundRend;
        }
        return rend;
    }

    /**
     * getTableHeight
     *
     * @return
     * @author Anthony C. Liberto
     */
    public int getTableHeight() {
        int height = getRowHeight();
        int rows = getRowCount();
        return (height * rows);
    }

    /**
     * getCellEditor
     *
     * @param _r
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public TableCellEditor getCellEditor(int _r, int _c) {
        TableCellEditor tce = null;
        Object o = getAttribute(_r, _c);
        if (o instanceof SingleFlagAttribute) {
            flagEdit.setAttribute((SingleFlagAttribute) o);
            tce = flagEdit;
        } else if (o instanceof StatusAttribute) {
            flagEdit.setAttribute((StatusAttribute) o);
            tce = flagEdit;
        } else if (o instanceof TaskAttribute) { //22509
            flagEdit.setAttribute((TaskAttribute) o); //22509
            tce = flagEdit; //22509
        } else if (o instanceof MultiFlagAttribute) {
            multiEdit.setAttribute((MultiFlagAttribute) o);
            multiEdit.loadPopup();
            tce = multiEdit;
        } else if (o instanceof LongTextAttribute) {
            longEdit.setAttribute((LongTextAttribute) o);
            longEdit.setDisplayOnly(false); //51242
            tce = longEdit;
        } else if (o instanceof XMLAttribute) {
            xmlEdit.setAttribute((XMLAttribute) o);
            xmlEdit.setTable(this);
            //22429			xmlEdit.show();
            setEditingRowKey(_r); //22429
            setEditingColumnKey(_c); //22429
            show(this, xmlEdit, false); //22429
            cellEditor = null;
        } else if (o instanceof BlobAttribute) {
            blobEdit.setAttribute((BlobAttribute) o);
            blobEdit.setDisplayOnly(false); //51242
            tce = blobEdit;
        } else if (o instanceof TextAttribute) {
            TextAttribute txt = (TextAttribute) o;
            EANMetaAttribute meta = txt.getMetaAttribute();
            if (meta.isDate()) {
                dateEdit.setAttribute(txt);
                tce = dateEdit;
            } else if (meta.isTime()) {
                timeEdit.setAttribute(txt);
                tce = timeEdit;
            } else {
                edit.setAttribute(txt);
                tce = edit;
            }
        }
        return tce;
    }
 
    /**
     * canTouch
     *
     * @param _r
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public boolean canTouch(int _r, int _c) { //21825
        EANAttribute att = getAttribute(_r, _c); //21825
        String key = getKey(att); //21825
        if (touch.containsKey(key)) { //21825
            return false; //21825
        } //21825
        touch.put(key, null); //21825
        return true; //21825
    } //21825

    /**
     * replaceString
     * @author Anthony C. Liberto
     * @return boolean
     * @param _old
     * @param _new
     * @param _r
     * @param _c
     */
    public boolean replaceString(String _old, String _new, int _r, int _c) {
        EANAttribute att = getAttribute(_r, _c);
        String newStr = null;
        if (att instanceof LongTextAttribute) {
            newStr = Routines.replaceString(((LongTextAttribute) att).get().toString(), _old, _new);
        } else if (att instanceof XMLAttribute) {
            newStr = Routines.replaceString(((XMLAttribute) att).get().toString(), _old, _new);
        } else if (att instanceof TextAttribute) {
            newStr = Routines.replaceString(((TextAttribute) att).get().toString(), _old, _new);
        }
        if (newStr == null) {
            return false;
        }
        validator.setAttribute(att);
        if (!validator.validate(newStr, newStr, 0)) {
            return false;
        }
        if (isCellLocked(_r, _c, true) && isEditable(_r, _c)) {
            //23373			setValueAt(newStr,_r,_c);
            //50973			putValueAt(newStr,_r,_c);													//23373
            putValueAt(newStr, _r, convertColumnIndexToModel(_c)); //50973
            if (ec != null) { //23268
                ec.refreshUpdate(); //23268
            } else if (ac != null) { //23268
                ac.refreshUpdate(); //23268
            } //23268
        }
        resizeCells(); //52045
        repaint();
        return true;
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @author Anthony C. Liberto
     */
    public void actionPerformed(ActionEvent _ae) {
    }

    /**
     * setFillable
     * @param _b
     * @author Anthony C. Liberto
     * /
    public void setFillable(boolean _b) {
        fillable = _b;
    }*/

    /**
     * isFillable
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean isFillable() {
        return fillable;
    }*/

    /**
     * showEffectivity
     * @author Anthony C. Liberto
     */
    public void showEffectivity() {
    }

    /**
     * freeze
     * @return
     * @author Anthony C. Liberto
     */
    public ERowList freeze() {
        int col = getSelectedColumn();
        if (col < 0 || col >= getColumnCount()) {
            return null;
        }
        cgtm.setRowHeaderIndex(convertColumnIndexToModel(col));
        rowHeaderVisible = true;
        return refreshList();
    }

    /**
     * thaw
     * @author Anthony C. Liberto
     */
    public void thaw() {
        rowHeaderVisible = false;
    }

    //acl_20021015	public void setValueAt(Object _o, int _r, int _c) {
    //acl_20021015		super.setValueAt(_o,_r,_c);
    /**
     * putValueAt
     * @author Anthony C. Liberto
     * @return boolean
     * @param _o
     * @param _r
     * @param _c
     */
    public boolean putValueAt(Object _o, int _r, int _c) { //acl_20021015
        boolean b = super.putValueAt(_o, _r, _c); //acl_20021015
        if (b) { //acl_20021015
            int c = convertColumnIndexToModel(_c);
            if (c == cgtm.getRowHeaderIndex()) {
                refreshList();
            }
        } //acl_20021015
        return b; //acl_20021015
    }

    //acl_20021015	public void setValueAt(Object _o, String _rKey, String _colKey) {
    //acl_20021015		super.setValueAt(_o,_rKey,_colKey);
    /**
     * putValueAt
     * @author Anthony C. Liberto
     * @return boolean
     * @param _o
     * @param _rKey
     * @param _colKey
     */
    public boolean putValueAt(Object _o, String _rKey, String _colKey) { //acl_20021015
        boolean b = super.putValueAt(_o, _rKey, _colKey); //acl_20021015
        if (b) { //acl_20021015
            int c = cgtm.getColumnIndex(_colKey);
            if (c == cgtm.getRowHeaderIndex()) {
                refreshList();
            }
        } //acl_20021015
        return b; //acl_20021015
    }

    /**
     * refreshList
     *
     * @return
     * @author Anthony C. Liberto
     */
    public ERowList refreshList() {
        if (!rowHeaderVisible) {
            return null;
        }
        rList.setHeaderValue(cgtm.getColumnName(cgtm.getRowHeaderIndex())); //21773
        rList.refresh(cgtm.getRowHeader());
        //21773		rList.setHeaderValue(cgtm.getColumnName(cgtm.getRowHeaderIndex()));
        rList.revalidate(); //52719
        return rList;
    }

    /*
     * filter
     */
    /**
     * filter
     *
     * @author Anthony C. Liberto
     */
    public void filter() { //19938
        super.filter(); //19938
        if (ec != null) { //52323
            ec.freezeRefresh(); //52323
        } //52323
    } //19938

    /**
     * moveColumn
     * @param _attCode
     * @param _vis
     * @param _pos
     * @return
     * @author Anthony C. Liberto
     * /
    public int moveColumn(String _attCode, boolean _vis, int _pos) {
        TableColumnModel tcm = getColumnModel();
        int indx = tcm.getColumnIndex(_attCode);
        RSTableColumn tc = null;
        if (indx < 0 || indx >= getColumnCount()) {
            return _pos;
        }
        moveColumn(indx, _pos);
        //20110		TableColumn tc = getColumn(_attCode);
        tc = (RSTableColumn) getColumn(_attCode); //20110
        if (tc != null && !_vis) {
            int width = tc.getWidth();
            if (width > 0) {
                //20110				tc.setResizable(true);
                //20110				tc.setMaxWidth(width);
                //20110				tc.setWidth(0);
                //20110				tc.setResizable(false);
                setTableColumnWidth(tc, 0); //20110
            }
        }
        return ++_pos;
    }*/

    /**
     * dereference
     *
     * @author Anthony C. Liberto
     */
    public void dereference() {
        validator = null; //20020207
        clearSelection(); //52030
        if (cellEditor != null) { //21864
            cellEditor.cancelCellEditing();
        } //21864
        removeAncestorListener(this); //acl_Mem_20020131
        //		getUI().uninstallUI(this);			//acl_Mem_20020130

        if (header != null) {
            header.dereference();
            header.removeAll();
            header.removeNotify();
            header = null;
        }

        if (lockedFoundRend != null) {
            lockedFoundRend.removeAll();
            lockedFoundRend.removeNotify();
            lockedFoundRend = null;
        }
        if (lockedRend != null) {
            lockedRend.removeAll();
            lockedRend.removeNotify();
            lockedRend = null;
        }
        if (foundRend != null) {
            foundRend.removeAll();
            foundRend.removeNotify();
            foundRend = null;
        }
        if (edit != null) {
            edit.dereference();
            edit = null;
        }

        if (blobEdit != null) {
            blobEdit.dereference();
            blobEdit = null;
        }

        xmlEdit = null;

        if (longEdit != null) {
            longEdit.dereference();
            longEdit = null;
        }
        if (flagEdit != null) {
            flagEdit.dereference();
            flagEdit = null;
        }

        if (dateEdit != null) {
            dateEdit.dereference();
            dateEdit = null;
        }

        if (multiEdit != null) {
            multiEdit.dereference();
            multiEdit = null;
        }

        if (timeEdit != null) {
            timeEdit.dereference();
            timeEdit = null;
        }

        if (foundItem != null) {
            foundItem.clear();
            foundItem = null;
        }
        if (hiddenRows != null) {
            hiddenRows.clear();
            hiddenRows = null;
        }
        if (vFill != null) {
            vFill.clear();
            vFill = null;
        }
        super.dereference();
    }

    private void deactivateAttribute(int _row, int _col) {
        int col = convertColumnIndexToModel(_col); //23187
        if (isValidCell(_row, col)) {
            if (isCellLocked(_row, _col)) { //23187
                if (isCellEditable(_row, _col)) { //51865
                    putValueAt(null, _row, col); //acl_20021016
                } //51865
            } //23187
            //acl_20021016			setValueAt(null,_row, _col);
        }
    }

    private void deactivateAttribute(int[] _rows, int[] _cols) {
        int rr = _rows.length;
        int cc = _cols.length;
        lockRows(_rows); //23187
        for (int r = 0; r < rr; ++r) {
            for (int c = 0; c < cc; ++c) {
                deactivateAttribute(_rows[r], _cols[c]);
            }
        }
        repaint(); //52017
    }

    /**
     * deactivateAttribute
     * @author Anthony C. Liberto
     */
    public void deactivateAttribute() {
        deactivateAttribute(getSelectedRows(), getSelectedColumns());
    }


    /**
     * @see javax.swing.event.AncestorListener#ancestorAdded(javax.swing.event.AncestorEvent)
     * @author Anthony C. Liberto
     */
    public void ancestorAdded(AncestorEvent event) {
    }
    /**
     * @see javax.swing.event.AncestorListener#ancestorRemoved(javax.swing.event.AncestorEvent)
     * @author Anthony C. Liberto
     */
    public void ancestorRemoved(AncestorEvent event) {
    }


    /**
     * @see javax.swing.event.AncestorListener#ancestorMoved(javax.swing.event.AncestorEvent)
     * @author Anthony C. Liberto
     */
    public void ancestorMoved(AncestorEvent event) {
    }

    /**
     * stopEditing
     * @author Anthony C. Liberto
     */
    public void stopEditing() {
        if (isEditing()) {
            editingCanStop();
        }
    }

    /**
     * moveToError
     * @author Anthony C. Liberto
     * @param bre
     */
    public void moveToError(EANBusinessRuleException bre) { //20069
        Object o = bre.getObject(0);
        Point pt = new Point(0, 0);
        if (o instanceof EANAttribute) {
            EANAttribute att = (EANAttribute) o;
            EntityItem ei = (EntityItem) att.getParent();
            pt = selectCell(ei, att.getAttributeCode());
        } else if (o instanceof EntityItem) {
            EntityItem ei = (EntityItem) o;
            pt = selectCellPoint(ei, 0);
        }

        if (pt == null) {
            return;
        }
        if (pt.x >= getRowCount() || pt.x < 0) {
            return;
        }
        if (pt.y >= getColumnCount() || pt.y < 0) {
            return;
        }
        setRowSelectionInterval(pt.x, pt.x);
        setColumnSelectionInterval(pt.y, pt.y);
        scrollRectToVisible(getCellRect(pt.x, pt.y, false));
        grabFocus();
    }

    /**
     * selectCellPoint
     * @param _ei
     * @param c
     * @return
     * @author Anthony C. Liberto
     */
    private Point selectCellPoint(EntityItem _ei, int c) {
        int r = cgtm.getRowIndex(_ei.getKey());
        return new Point(r, c);
    }

    /**
     * selectCell
     * @param _ei
     * @param code
     * @return
     * @author Anthony C. Liberto
     */
    private Point selectCell(EntityItem _ei, String code) {
        //21998		int r = cgtm.getRowIndex(_ei.getKey());
        int r = cgtm.getViewRowIndex(_ei.getKey()); //21998
        //dwb_20041117		int c = convertColumnIndexToView(cgtm.getColumnIndex(_ei.getEntityType() + ":" + code));
        String sKey = eaccess().getKey(_ei, code); //dwb_20041117
        int c = convertColumnIndexToView(cgtm.getColumnIndex(sKey)); //dwb_20041117
        if (r < 0) {
            EntityItem eip = (EntityItem) _ei.getUpLink(0);
			if (eip==null){  // try downlink if trying to create a SWFEATURE at MODEL uplink will be null
				eip = (EntityItem) _ei.getDownLink(0);
			}
            if (eip!=null){
            //21998			r = cgtm.getRowIndex(eip.getKey());
            	r = cgtm.getViewRowIndex(eip.getKey()); //21998
			}
        }

        return new Point(r, c);
    }

    /**
     * selectRow
     * @param _ei
     * @author Anthony C. Liberto
     */
    public void selectRow(EntityItem _ei) {
        int r = getRow(_ei);
        int cc = -1;
        if (r >= 0) {
            setRowSelectionInterval(r, r);
            cc = getColumnCount();
            if (cc > 0) {
                setColumnSelectionInterval(0, 0);
                for (int c = 1; c < cc; ++c) {
                    addColumnSelectionInterval(c, c);
                }
            }
            if (isValidCell(r, 0)) { //52026
                scrollRectToVisible(getCellRect(r, 0, false)); //52026
            } //52026
            repaint();
        }
    }

    /**
     * getRow
     * @param _ei
     * @return
     * @author Anthony C. Liberto
     */
    private int getRow(EntityItem _ei) {
        if (_ei == null) {
            return -1;
        }
        //52167		return cgtm.getRowIndex(_ei.getKey());
        return cgtm.getViewRowIndex(_ei.getKey()); //52167
    }
    /*
    added for 22713
    start
    */
    /**
     * fillCopy
     * @param _row
     * @author Anthony C. Liberto
     */
    public void fillCopy(boolean _row) {
        int row = getSelectedRow();
        int aa = -1;
        EntityItem ei = null;
        boolean bMsg = true; //22829
        if (row < 0 || row >= getRowCount()) {
            return;
        }
        if (_row) {
            fillClear();

            ei = getCurrentEntityItem(false); //52148
            if (!ei.isVEEdit()){//TIR6ZNSHV
				aa = ei.getAttributeCount(); //52148
				for (int a = 0; a < aa; ++a) { //52148
					bMsg = fillAdd(ei.getAttribute(a), bMsg,-1); //52148
				} //52148
				if (ei.hasDownLinks()) { //52148
					int dd = ei.getDownLinkCount(); //52148
					for (int d = 0; d < dd; ++d) { //52148
						EANEntity entity = ei.getDownLink(d); //52148
						int ii = entity.getAttributeCount(); //52148
						for (int i = 0; i < ii; ++i) { //52148
							bMsg = fillAdd(entity.getAttribute(i), bMsg,-1); //52148
						} //52148
					} //52148
				} //52148
			}else{//TIR6ZNSHV
				// use the entity items for this path
				if (ei instanceof VEEditItem){
					EntityItem[] eiRelated = ((VEEditItem)ei).getDisplayableItems();
					for (int i = 0; i < eiRelated.length; ++i) {
						EntityItem editItem = eiRelated[i];
						aa = editItem.getAttributeCount();
						for (int a = 0; a < aa; ++a) {
							bMsg = fillAdd(editItem.getAttribute(a), bMsg,-1);
						}
					}
				}
			}
        } else {
            int[] cols = getSelectedColumns();
            int ii = cols.length;
            for (int i = 0; i < ii; ++i) {
                bMsg = fillAdd(getAttribute(row, cols[i]), bMsg,convertColumnIndexToModel(cols[i])); //22829
            }
        }
    }

    /**
     * fillPaste
     * @author Anthony C. Liberto
     */
    public void fillPaste() {
        int[] rows = getSelectedRows();
        boolean bFill = false; //22711
        setUpdateArmed(false);
        lockRows(rows); //22833
        
        for (int r = 0; r < rows.length; ++r) {
            putFillValue(rows[r]);
            if (!vFill.isEmpty()) {
                bFill = true;
            }
        }
        
        if (bFill) { //22711
            if (ec != null) {
                ec.refreshUpdate();
            }
            setUpdateArmed(true);
            cgtm.modelChanged();
        } else { //22711
            setUpdateArmed(true); //22711
            showError("msg23031"); //22711
        } //22711
        repaint(); //51804
    }

    private boolean fillAdd(EANAttribute _att, boolean _msg, int colid) { //22829
        if (_att != null) {
            if (_att instanceof BlobAttribute) { //22829
                if (_msg) { //22829
                    showError("msg23047"); //22829
                    return false; //22829
                } //22829
            } else { //22829
                vFill.add(_att,colid);
            }
        }
        return _msg; //22829
    }

    /**
     * fillClear
     * @author Anthony C. Liberto
     */
    public void fillClear() {
        vFill.clear();
    }

    /**
     * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
     * @author Anthony C. Liberto
     */
    public void valueChanged(ListSelectionEvent _lse) {
        superValueChanged(_lse); //TIR USRO-R-JSTT-65GLT2
    }

    /**
     * getRecordKey
     * @return
     * @author Anthony C. Liberto
     */
    public String getRecordKey() {
        int row = getSelectedRow();
        if (row < 0 || row > getRowCount()) {
            return "";

        } else {
            return cgtm.getRowKey(row);
        }
    }

    /**
     * getSelectionKey
     * @return
     * @author Anthony C. Liberto
     */
    public String getSelectionKey() {
        int col = convertColumnIndexToModel(getSelectedColumn());
        return cgtm.getColumnKey(col);
    }

    /**
     * setSelection
     * @param _recKey
     * @param _selKey
     * @author Anthony C. Liberto
     */
    public void setSelection(String _recKey, String _selKey) {
        int row = cgtm.getViewRowIndex(_recKey);
        //22286		int col = cgtm.getColumnIndex(_selKey);
        int col = -1; //22286
        if (_selKey != null) { //22286
            try { //22858
                col = getColumnModel().getColumnIndex(_selKey); //22286
            } catch (IllegalArgumentException _iae) { //22858
                _iae.printStackTrace(); //22858
                col = -1; //22858
            } //22858
        }
        if (isValidCell(row, col)) {
            setRowSelectionInterval(row, row);
            setColumnSelectionInterval(col, col);
            scrollRectToVisible(getCellRect(row, col, false));
            toggleEnabled(row, col); //22149
        } else if (isValidCell(row, 0)) {
            setRowSelectionInterval(row, row);
            setColumnSelectionInterval(0, 0);
            scrollRectToVisible(getCellRect(row, 0, false));
            toggleEnabled(row, 0); //22149
        } else if (isValidCell(0, 0)) {
            setRowSelectionInterval(0, 0);
            setColumnSelectionInterval(0, 0);
            scrollRectToVisible(getCellRect(0, 0, false));
            toggleEnabled(0, 0); //22149
        }
    }

    //52009	public void addRow() {											//19983
    //acl_20030911	public synchronized void addRow() {								//52009
    /**
     * addRow
     *
     * @param _sort
     * @author Anthony C. Liberto
     */
    public synchronized void addRow(boolean _sort) { //acl_20030911
        int iRow = getRowCount(); //51522
        super.addRow(_sort); //acl_20030911
        //acl_20030911		super.addRow();												//19983
        refreshList(); //19983
        if (iRow > 0 && iRow < getRowCount()) { //51522
            setRowSelectionInterval(iRow, iRow); //51522
            if (getColumnCount() > 0) { //51522
                setColumnSelectionInterval(0, 0); //51522
                Routines.yield(); //52009
                scrollRectToVisible(getCellRect(iRow, 0, false)); //51798
                repaint(); //52009
            } //51522
        } //51522
    } //19983

    /**
     * addRow
     * VEEdit_Iteration2
     * @param _key
     * @param _sort
     * @author Anthony C. Liberto
     */
    public synchronized void addRow(String _key, boolean _sort) {
        int iRow = getRowCount();
        super.addRow(_key,_sort);
        refreshList();
        if (iRow > 0 && iRow < getRowCount()) {
            setRowSelectionInterval(iRow, iRow);
            if (getColumnCount() > 0) {
                setColumnSelectionInterval(0, 0);
                Routines.yield();
                scrollRectToVisible(getCellRect(iRow, 0, false));
                repaint();
            }
        }
    }

    /**
     * synchronize
     * @author Anthony C. Liberto
     */
    public void synchronize() { //acl_20021206
        //51414		if (cgtm.synchronize()) {									//acl_20021206
        //acl_20030911			cgtm.synchronize(TableModelEvent.UPDATE);					//51414
        cgtm.synchronize(TableModelEvent.UPDATE, true); //acl_20030911
        resizeCells(); //acl_20030827
        resizeAndPaint(false); //acl_20021206
        //51414		}															//acl_20021206
    } //acl_20021206

    /**
     * removeRows
     *
     * @author Anthony C. Liberto
     */
    public void removeRows() { //19983
        super.removeRows(); //19983
        refreshList(); //19983
    } //19983

    /**
     * rollback
     *
     * @author Anthony C. Liberto
     */
    public void rollback() { //19983
        super.rollback(); //19983
        refreshList(); //19983
    } //19983

    /**
     * rollbackRow
     *
     * @author Anthony C. Liberto
     */
    public void rollbackRow() { //19983
        super.rollbackRow(); //19983
        refreshList(); //19983
    } //19983

    /**
     * rollbackSingle
     *
     * @author Anthony C. Liberto
     */
    public void rollbackSingle() { //19983
        int c = -1;
        super.rollbackSingle(); //19983
        c = convertColumnIndexToModel(getSelectedColumn()); //19983
        if (c == cgtm.getRowHeaderIndex()) { //19983
            refreshList();
        } //19983
    } //19983

    /*
     * tooltip
     */
    /**
     * @see javax.swing.JComponent#createToolTip()
     * @author Anthony C. Liberto
     */
    public JToolTip createToolTip() {
        EMToolTip tip = new EMToolTip();
        tip.setComponent(this);
        return tip;
    }

    /**
     * @see javax.swing.JComponent#getToolTipText(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public String getToolTipText(MouseEvent _me) {
        Point pt = _me.getPoint();
        int row = rowAtPoint(pt);
        int col = columnAtPoint(pt);
        if (isValidCell(row, col)) {
            EANAttribute att = getAttribute(row, col);
            if (att instanceof MultiFlagAttribute) {
                return Routines.getDisplayString(getValueAt(row, col, true).toString(), true); //22733
                //22733				return routines.getDisplayString(getValueAt(row,col,true).toString(),false);		//22563
                //22563				return getValueAt(row,col,true).toString();
            } else if (att instanceof LongTextAttribute) { //22223
                return Routines.getDisplayString(getValueAt(row, col, true).toString(), true); //22733
                //22733				return routines.getDisplayString(getValueAt(row,col,true).toString(),false);		//22563
                //22563				return getValueAt(row,col,true).toString();			//22223
            } else if (att instanceof XMLAttribute) { //22223
                return Routines.getDisplayString(getValueAt(row, col, true).toString(), true); //22612
                //22612				return routines.getDisplayString(getValueAt(row,col,true).toString(),false);		//22563
                //22563				return getValueAt(row,col,true).toString();			//22223
            }
        }
        return null;
    }

    /**
     * @see javax.swing.JComponent#getToolTipLocation(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public Point getToolTipLocation(MouseEvent _me) {
        Point pt = _me.getPoint();
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
     * removeNewRows
     * @author Anthony C. Liberto
     */
    public void removeNewRows() { //19920
        int rr = getRowCount(); //19920
        for (int i = (rr - 1); i >= 0; --i) { //19920
            int mRow = cgtm.getModelRowIndex(i); //19920
            if (cgtm.isNewModelRow(mRow)) { //19920
                cgtm.removeRow(mRow);
            } //19920
        } //19920
        if (getRowCount() == 0) { //19920
            //acl_20030911			cgtm.addRow(true);						//19920
            cgtm.addRow(true, false); //acl_20030911
        } //19920
        setRowSelectionInterval(0, 0); //19920
        setColumnSelectionInterval(0, 0); //19920
        refreshList(); //20041
    } //19920

    private boolean isRequiredField(int _r, int _c) {
        Object o = cgtm.getEANObject(_r, convertColumnIndexToModel(_c));
        if (o instanceof EANAttribute) {
            EANAttribute att = (EANAttribute) o;
            EANMetaAttribute meta = att.getMetaAttribute();
            if (meta != null && meta.isRequired()) {
                return true;
            } else {
                return att.isRequired();
            }
        }
        return false;
    }

    /**
     * @see javax.swing.JTable#changeSelection(int, int, boolean, boolean)
     * @author Anthony C. Liberto
     */
    public void changeSelection(int _row, int _col, boolean _toggle, boolean _extend) { //19937
        toggleEnabled(_row, _col); //21737
        super.changeSelection(_row, _col, _toggle, _extend); //19937
    } //19937

    /**
     * toggleEnabled
     *
     * @param _row
     * @param _col
     * @author Anthony C. Liberto
     */
    protected void toggleEnabled(int _row, int _col) { //21737
        if (ec != null) { //21737
            boolean bNew = isNew(_row); //21737
            boolean bReq = isRequiredField(_row, _col); //23263
            boolean bEdit = isEditable(_row, _col); //23263
            boolean bPaste = isPasteable(_row, _col); //23263
            boolean bDefault = ec.canEditDefault() && bNew; //51257

            EANMetaAttribute meta = getEANMetaAttribute(_row,_col);	//cr_FlagUpdate
            ec.toggleMaint(meta);									//cr_FlagUpdate

            ec.setEnabled("remove", bNew); //21737
            ec.setEnabled("dsave", bDefault); //51257
            ec.setEnabled("dcncl", bDefault); //51257

            ec.setSelectorEnabled(bNew); //21737
            ec.setEnabled("cut", !bReq && bEdit); //23263
            ec.setEnabled("dAct", bEdit); //23263

            //USRO-R-RTAR-672S9Y			ec.setEnabled("pste", bEdit && ec.canPaste() && bPaste);					//23263
            ec.setEnabled("pste", ec.canPaste() && bPaste); //USRO-R-RTAR-672S9Y
            //23263		ec.setEnabled("cut", !isRequiredField(_row, _col));							//21737
            ec.setSelectedParent(cgtm.getRowByKey(cgtm.getRowKey(_row))); //21737
            boolean itemchgd = hasEntityItemChanged(cgtm.getRowKey(_row));
            ec.setEnabled("saveR", itemchgd); //22780
            // also modify 'reset attributes'
            ec.setEnabled("rstA", itemchgd);
            ec.setEnabled("rstR", itemchgd);
            ec.setEnabled("rstS", itemchgd);
           
        } //21737
    } //21737

    /**
     * getUIPrefKey
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getUIPrefKey() { //21643
        if (getRowCount() > 0) {
            EntityItem ei = (EntityItem) cgtm.getModelRow(0);
            return "HORZ" + ei.getEntityType();
        }
        return "HORZ";
    }

    /**
     * getCellViewer
     *
     * @param _r
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public TableCellEditor getCellViewer(int _r, int _c) { //22248
        Object o = getAttribute(_r, _c); //22248
        if (o instanceof LongTextAttribute) { //22248
            longEdit.setAttribute((LongTextAttribute) o); //22248
            longEdit.setDisplayOnly(true); //22248
            //			lPopup.setEditor(longEdit);															//22248
            //			lPopup.show();																		//22248
            //51869			longEdit.showDialog();
            longEdit.showDialog(this, true); //51869
            return longEdit; //22248
        } else if (o instanceof XMLAttribute) { //22248
            xmlEdit.setAttribute((XMLAttribute) o); //22248
            show(this, xmlEdit, false);
            return null; //22248
        } else if (o instanceof BlobAttribute) { //22248
            blobEdit.setAttribute((BlobAttribute) o); //22248
            blobEdit.setDisplayOnly(true); //22248
            return blobEdit; //22248
        } //22248
        return null; //22248
    } //22248

    /*
     * 21745
     */
    /**
     * getSelectedEntityItem
     * @return
     * @author Anthony C. Liberto
     */
    private EntityItem getSelectedEntityItem() {
        Object o = getSelectedObject();
        if (o != null && o instanceof EntityItem) {
            return (EntityItem) o;
        }
        return null;
    }

    /**
     * getSelectedVisibleEntityItems
     * @return
     * @author Anthony C. Liberto
     * /
    public EntityItem[] getSelectedVisibleEntityItems() {
        int[] rows = getSelectedVisibleRows();
        int ii = rows.length;
        String[] sKey = new String[ii];
        for (int i = 0; i < ii; ++i) {
            sKey[i] = getKey(i);
        }
        return getEntityItems(sKey);
    }*/

    /**
     * commit
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean commit() {
        int[] rr = getSelectedRows();
        EntityItem ei = null;
        Exception e = null;
        if (rr.length != 1) {
            showError("msg11022.0");
            return false;
        }
        if (isEditing()) {
            if (!canStopEditing()) {
                return false;
            }
        }

        ei = getSelectedEntityItem();
        e = dBase().commit(ei, this);
        if (e != null) {
            if (e instanceof MiddlewareBusinessRuleException) {
                //				moveToError((MiddlewareBusinessRuleException)e);
            } else if (e instanceof EANBusinessRuleException) {
                moveToError((EANBusinessRuleException) e);
            }
            //51260			setMessage(e.toString());
            //51260			showError();
            showException(e, ERROR_MESSAGE, OK); //51260
        } else {
            refresh();
            return true;
        }
        return false;
    }

    /**
     * hasEntityItemChanged
     * @return
     * @author Anthony C. Liberto
     */
    public boolean hasEntityItemChanged() { //22780
        EntityItem ei = getSelectedEntityItem(); //22780
        if (ei != null) { //22780
            return ei.hasChanges();
        } //22780
        return false; //22780
    } //22780

    /**
     * hasEntityItemChanged
     * @param _key
     * @return
     * @author Anthony C. Liberto
     */
    private boolean hasEntityItemChanged(String _key) { //22780
        EANFoundation ean = (EANFoundation) cgtm.getRowByKey(_key); //22780
        if (ean instanceof EntityItem) { //22780
            return ((EntityItem) ean).hasChanges();
        } //22780
       
        return false; //22780
    } //22780

    /**
     * refreshRemove
     * @author Anthony C. Liberto
     */
    public void refreshRemove() {
        //		cgtm.rekey(cgtm.getKeys(),cgtm.getRowKeyArray(), PROCESS_EXIST,false);
        if (getRowCount() == 0) {
            //acl_20030911			cgtm.addRow(true);
            cgtm.addRow(true, false); //acl_20030911
        }
        refreshList();
    }

    /**
     * resizeAfterEdit
     *
     * @param _row
     * @param _col
     * @author Anthony C. Liberto
     */
    protected void resizeAfterEdit(int _row, int _col) { //23239
        resizeEditColumn(_col); //23239
    } //23239

    /*
     20030520
    */
    /**
     * isEditing
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isEditing() {
        if (cellEditor == null) {
            if (xmlEdit != null) {
                return xmlEdit.isShowing();
            }
            return false;
        }
        return true;
    }

    /**
     * cancelEdit
     *
     * @author Anthony C. Liberto
     */
    public void cancelEdit() {
        TableCellEditor editor = getCellEditor();
        if (editor != null) {
            removeEditor(editor);
        } else {
            if (xmlEdit.isShowing()) {
                xmlEdit.reset();
            } else {
                removeEditor();
            }
        }
        editingStopped(null);
    }

    /**
     * finishEditing
     *
     * @author Anthony C. Liberto
     */
    public void finishEditing() {
        if (isViewing()) {
            removeViewer();
        } else if (isEditing()) {
            TableCellEditor tce = getCellEditor();
            if (tce instanceof FlagEditor) { //50827
                ((FlagEditor) tce).removeEditor(); //50827
            } else if (tce instanceof MultiEditor) { //51974
                ((MultiEditor) tce).removeEditor(); //51974
            } //50827
            if (tce != null) {
                tce.stopCellEditing();
            } else if (xmlEdit.isShowing()) {
                xmlEdit.updateRequested();
            }
        }
    }

    /**
     * editingCanStop
     * @author Anthony C. Liberto
     * @return boolean
     * @param tce
     */
    public boolean editingCanStop(TableCellEditor tce) {
        if (tce != null) {
            return tce.stopCellEditing();
        } else if (xmlEdit.isShowing()) {
            xmlEdit.updateRequested();
        }
        return true;
    }

    /*
     50985
     */
    /**
     * replaceAllValue
     * @author Anthony C. Liberto
     * @param _multi
     * @param _case
     * @param _increment
     * @param _find
     * @param _replace
     */
    public void replaceAllValue(String _find, String _replace, boolean _multi, boolean _case, int _increment) {
        int rr = getRowCount();
        int cc = getColumnCount();
        if (!isSearchable() || !isReplaceable()) {
            return;
        }
        if (_multi) {
            for (int c = 0; c < cc; ++c) {
                for (int r = 0; r < rr; ++r) {
                    String str = getString(r, c, false);
                    if (isFound(str, _find, _case)) {
                        if (isCellLocked(r, c, true)) {
                            if (isCellEditable(r, c)) {
                                if (isReplaceableAttribute(r, c)) {
                                    if (canTouch(r, c)) {
                                        setFound(r, c);
                                        replaceString(_find, _replace, r, c);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            int c = getSelectedColumn();
            if (c < 0) {
                c = 0;
            }
            for (int r = 0; r < rr; ++r) {
                String str = getString(r, c, false);
                if (isFound(str, _find, _case)) {
                    if (isCellLocked(r, c, true)) {
                        if (isCellEditable(r, c)) {
                            if (isReplaceableAttribute(r, c)) {
                                if (canTouch(r, c)) {
                                    setFound(r, c);
                                    replaceString(_find, _replace, r, c);
                                }
                            }
                        }
                    }
                }
            }
        }
        resizeCells(); //52045
        setCode("msg11024.0");
        setParmCount(2);
        setParm(0, _find);
        setParm(1, _replace);
        showFYI();
    }

    /*
     52115
     */
    /**
     * resetFilter
     *
     * @author Anthony C. Liberto
     */
    public void resetFilter() {
        super.resetFilter();
        refreshList();
    }

    /*
     52113
     */
    /**
     * sortHeader
     *
     * @param _asc
     * @author Anthony C. Liberto
     */
    public void sortHeader(boolean _asc) {
        if (isSortable()) {
            cgtm.sortHeader(_asc);
            refreshList();
        }
    }

    /*
    TIR6ZNSHV  need to look at grandchildren as well as children to find attribute on veedit
    */
	private EANAttribute findLocalAttribute(EntityItem ei, String attrKey, boolean lookdown){
		EANAttribute localAtt = null;
		
		if (lookdown){
			if (ei.hasDownLinks()) {
				for (int d = 0; d < ei.getDownLinkCount() && localAtt == null; ++d) {
					EntityItem dnlnk = (EntityItem)ei.getDownLink(d);
					localAtt = dnlnk.getAttribute(attrKey);
					if (localAtt == null) { // check children
						localAtt = findLocalAttribute(dnlnk,attrKey, lookdown);
					}
				}
			}
		}else{
			if (ei.hasUpLinks()) {
				for (int d = 0; d < ei.getUpLinkCount() && localAtt == null; ++d) {
					EntityItem uplnk = (EntityItem)ei.getUpLink(d);
					localAtt = uplnk.getAttribute(attrKey);
					if (localAtt == null) { // check parents
						localAtt = findLocalAttribute(uplnk,attrKey, lookdown);
					}
				}
			}
		}

		return localAtt;
	}

    /*
     52148
     * /
	   private void putFillValue(int row, Fillvector _v) {
//      	System.out.println("putFillValue");
       boolean bRefresh = false;
       Object _o = cgtm.getRow(row);
       if (_o instanceof EntityItem) {
           EntityItem ei = (EntityItem) _o;
           int ii = _v.size();
           clearTransitionGroup(); //52151
           for (int i = 0; i < ii; ++i) {
               EANAttribute fillAtt = _v.getAttribute(i);
               EANAttribute localAtt = ei.getAttribute(fillAtt.getKey());
               if (localAtt != null) { //MN20687400
                   processAttribute(fillAtt, localAtt); //MN20687400
                   bRefresh = true; //MN20687400
               } //MN20687400
               else{//MN38316169
               	boolean lookdown = true;
               	EntityGroup eg = ei.getEntityGroup();
               	if (eg!= null){
               		EntityList list = eg.getEntityList();
               		if (list!= null){
               			//may need to look up first, SWFEATURE->SWPRODSTRUCT->MODEL
               			//MODEL and SWFEATURE share some attributes
               			lookdown = !list.isCreateParent(); // if true, look up first
               		}
               	}
               	localAtt = findLocalAttribute(ei, fillAtt.getKey(), lookdown);
               	if (localAtt == null) {
               		localAtt = findLocalAttribute(ei, fillAtt.getKey(), !lookdown);
               	}
               	if (localAtt != null) { 
               		processAttribute(fillAtt, localAtt); 
               		bRefresh = true; 
               	}
               }
           }
           processTransitionGroup(); //52151
       }
       if (bRefresh) {
           refreshList();
       }
  }*/
	
   private void putFillValue(int row) {
	   boolean bRefresh = false;
   
	   clearTransitionGroup(); //52151
	
	   if(!vFill.isFillCopyEntity()){
		   for (int i = 0; i < vFill.size(); ++i) {
			   EANAttribute fillAtt = vFill.getAttribute(i);
			   EANAttribute localAtt = getAttribute(row, this.convertColumnIndexToView(vFill.getModelColumnId(i)));
			   processAttribute(fillAtt, localAtt); 
			   bRefresh = true; 
		   }
	   }else{
		   Object _o = cgtm.getRow(row);
		   if (_o instanceof EntityItem) {
			   EntityItem ei = (EntityItem) _o;
			   // for fill copy entity, must look for attrs.. this function is no longer used
			   for (int i = 0; i < vFill.size(); ++i) {
				   EANAttribute fillAtt = vFill.getAttribute(i);
				   EANAttribute localAtt = ei.getAttribute(fillAtt.getKey());
				   if (localAtt != null) { //MN20687400
					   processAttribute(fillAtt, localAtt); //MN20687400
					   bRefresh = true; //MN20687400
				   } //MN20687400
				   else{//MN38316169
					   boolean lookdown = true;
					   EntityGroup eg = ei.getEntityGroup();
					   if (eg!= null){
						   EntityList list = eg.getEntityList();
						   if (list!= null){
							   //may need to look up first, SWFEATURE->SWPRODSTRUCT->MODEL
							   //MODEL and SWFEATURE share some attributes
							   lookdown = !list.isCreateParent(); // if true, look up first
						   }
					   }
					   localAtt = findLocalAttribute(ei, fillAtt.getKey(), lookdown);
					   if (localAtt == null) {
						   localAtt = findLocalAttribute(ei, fillAtt.getKey(), !lookdown);
					   }
					   if (localAtt != null) { 
						   processAttribute(fillAtt, localAtt); 
						   bRefresh = true; 
					   }
				   }
			   }
		   }
	   }
	   
	   processTransitionGroup(); //52151

	   if (bRefresh) {
		   refreshList();
	   }
   }

    /*
     MN20687400
     */
    private void processAttribute(EANAttribute _fill, EANAttribute _local) {
        if (eaccess().isDebug()) {
            try {
                if (_local != null) {
                    appendLog("localAttribute "+_local.getAttributeCode()+" isEditable: " + _local.isEditable());
                    appendLog("localAttribute parent "+_local.getParent().getKey());
                    appendLog("localAttribute.isLocked  : " + _local.hasLock(_local.getKey(), eaccess().getLockOwner(), getActiveProfile()));
                } else {
                    appendLog("local is null");
                }
                if (_fill != null) {
                    appendLog("fillAttribute "+_fill.getAttributeCode()+" value : " + _fill.toString());
                } else {
                    appendLog("fill is null");
                }
            } catch (Exception _ex) {
                _ex.printStackTrace();
            }
        }
  
        if (_local != null && _local.isEditable()) {
            if (_local.hasLock(_local.getKey(), eaccess().getLockOwner(), getActiveProfile())) {
                try {
//					System.out.println("    _local 1: " + _local.getAttributeCode());
//					System.out.println("    _fill  1: " + _fill.getAttributeCode());
//                    System.out.println("    _local == _fill: " + (_local == _fill));
//                    System.out.println("    _local.class: " + _local.getClass().getName());
                	
                	boolean isok=true; // SR14 hack to get to warning msg in editor
                	if (_fill.getMetaAttribute().isDate()){
                		// editors are bypassed in fillcopy and fillpaste
                		MetaValidator mv = new MetaValidator();
                		mv.setAttribute(_fill);
                		isok =mv.canLeave(_fill.toString());
                	}
                	if (isok){
                		_local.put(_fill.get());
                	}
                } catch (StateTransitionException _ste) {
                    _ste.printStackTrace();
                    addTransitionItem(_fill, _local);
                } catch (EANBusinessRuleException _bre) {
                    _bre.printStackTrace();
                    eaccess().showException(_bre, this, ERROR_MESSAGE, OK);
                }
//			} else {
//				System.out.println("  I have NO lock");
//				System.out.println("    _local 2: " + _local.getAttributeCode());
//				System.out.println("    _fill  2: " + _fill.getAttributeCode());
            }
//        } else {
//			System.out.println("  I am NOT editable");
//			System.out.println("    _local 3: " + _local.getAttributeCode());
//			System.out.println("    _fill  3: " + _fill.getAttributeCode());
		}
    }

    /*
     52189
     */
    /**
     * select
     * @author Anthony C. Liberto
     */
    public void select() {
        if (cgtm != null && isEditing()) {
            TableCellEditor tce = getCellEditor();
            if (tce instanceof MultiEditor) {
                ((MultiEditor) tce).reshowPopup();
            }
        }
    }

    /**
     * deselect
     * @author Anthony C. Liberto
     */
    public void deselect() {
        if (isViewing()) {
            removeViewer();
        } else if (isEditing()) {
            TableCellEditor tce = getCellEditor();
            if (tce instanceof MultiEditor) {
                ((MultiEditor) tce).hidePopup();
            }
        }
    }

    /*
     52113
     */
    /**
     * sort
     *
     * @param _i
     * @param _d
     * @author Anthony C. Liberto
     */
    public void sort(int[] _i, boolean[] _d) {
        super.sort(_i, _d);
        refreshList();
    }
    /*
     accessibility
     */
    //	 ""		 --> default
    //	 ".ctab" --> cross table
    //	 ".horz" --> horizontal
    //	 ".mtrx" --> matrix
    //	 ".vert" --> vertical
    /**
     * @see com.ibm.eannounce.eForms.table.GTable#getContext()
     * @author Anthony C. Liberto
     */
    protected String getContext() {
        return ".horz";
    }

    /**
     * getAccessibleValueAt
     *
     * @param _row
     * @param _col
     * @return
     * @author Anthony C. Liberto
     */
    public Object getAccessibleValueAt(int _row, int _col) {
        return getValueAt(_row, _col);
    }

    /**
     * getAccessibleColumnNameAt
     *
     * @param _col
     * @return
     * @author Anthony C. Liberto
     */
    public String getAccessibleColumnNameAt(int _col) {
        return getColumnName(_col);
    }

    /**
     * getAccessibleRowNameAt
     *
     * @param _row
     * @return
     * @author Anthony C. Liberto
     */
    public String getAccessibleRowNameAt(int _row) {
        if (rowHeaderVisible) {
            int col = cgtm.getRowHeaderIndex();
            if (col >= 0 && col < getColumnCount()) {
                Object o = cgtm.getValueAt(_row, col);
                if (o != null) {
                    return o.toString();
                }
            }
        } else {
            Object o = cgtm.getRow(_row); //52521
            if (o != null && o instanceof EANFoundation) { //52521
                return ((EANFoundation) o).getKey(); //52521
            }
        }
        return super.getAccessibleRowNameAt(_row);
    }

    /*
     52476
     */
    /**
     * hasHiddenAttributes
     * @return
     * @author Anthony C. Liberto
     */
    public boolean hasHiddenAttributes() {
        if (cgtm != null && ec != null) {
            EntityGroup eg = ec.getEntityGroup();
            if (eg != null) {
                int iDispCols = getColumnCount();
                int iTotalCols = eg.getActualColumnListCount();
                return !(iDispCols == iTotalCols);
            }
        }
        return false;
    }
    /*
     MN_18698482
     */
    /**
     * duplicate
     * @param _copies
     * @author Anthony C. Liberto
     */
    public void duplicate(int _copies) {
        int[] rows = getSelectedRows();
        int rr = rows.length;
        String[] keys = new String[rr];
        int iLastRow = -1;
        for (int r=0;r<rr;++r) {
			keys[r] = cgtm.getRowKey(rows[r]);
        }
		for (int r=0;r<rr;++r) {
			if (keys[r] != null) {
				cgtm.duplicateRow(cgtm.getRowIndex(keys[r]), _copies);
			}
		}
        cgtm.synchronize(TableModelEvent.INSERT, true);
        iLastRow = getRowCount() - 1;
        setRowSelectionInterval(iLastRow, iLastRow);
        scrollRectToVisible(getCellRect(iLastRow, getSelectedColumn(), false));
        if (ec != null) { //MN TBD-DK
            ec.freezeRefresh(); //MN TBD-DK
        } //MN TBD-DK
        repaint();
    }
    /*
     TIR USRO-R-SWWE-629MHH
     */
    /**
     * createTableModel
     * @author Anthony C. Liberto
     * @param _o
     * @param _table
     * @param _type
     * @return rsTableModel
     */
    protected RSTableModel createTableModel(Object _o, RowSelectableTable _table, int _type) {
        return new RSTableModel(_o, _table, _type) {
            public boolean isIndicateRelations() {
                if (ec != null) {
                    return ec.isIndicateRelations();
                }
                return false;
            }
        };
    }
}
