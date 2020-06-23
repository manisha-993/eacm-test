/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: VertTable.java,v $
 * Revision 1.6  2012/04/05 18:44:26  wendy
 * fillcopy/fillpaste perf updates
 *
 * Revision 1.5  2009/05/28 13:50:52  wendy
 * Performance cleanup
 *
 * Revision 1.4  2009/05/26 13:46:26  wendy
 * Performance cleanup
 *
 * Revision 1.3  2008/01/30 16:26:58  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.2  2007/05/31 19:54:21  wendy
 * Prevent hang when creating a SWFEATURE from MODEL
 *
 * Revision 1.1  2007/04/18 19:45:12  wendy
 * Reorganized JUI module
 *
 * Revision 1.6  2006/04/25 21:17:59  tony
 * fixed possible null pointer
 *
 * Revision 1.5  2005/11/04 22:58:47  tony
 * adjusted
 *
 * Revision 1.4  2005/10/27 21:17:15  tony
 * added display statements
 *
 * Revision 1.3  2005/10/13 20:11:22  joan
 * adjust for PDG
 *
 * Revision 1.2  2005/09/12 19:03:16  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:38:10  tony
 * This is the initial load of OPICM
 *
 * Revision 1.23  2005/09/08 17:59:08  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.22  2005/08/16 22:45:01  tony
 * MN_25042000
 *
 * Revision 1.21  2005/03/07 17:46:25  joan
 * add delete key in pdg dialog
 *
 * Revision 1.20  2005/03/03 21:46:41  tony
 * cr_FlagUpdate
 * Added Flag Modification Capability.
 *
 * Revision 1.19  2005/02/09 18:55:25  tony
 * Scout Accessibility
 *
 * Revision 1.18  2005/01/31 20:47:48  tony
 * JTest Second Pass
 *
 * Revision 1.17  2005/01/26 22:17:57  tony
 * JTest Modifications
 *
 * Revision 1.16  2004/11/29 18:14:37  tony
 * USRO-R-RTAR-672S9Y updated paste activation logic.
 *
 * Revision 1.15  2004/11/18 17:57:35  tony
 * fixed resize on vertical
 * adjusted column key functionality
 *
 * Revision 1.14  2004/11/09 21:26:33  tony
 * isIndicateRelations()
 *
 * Revision 1.13  2004/11/02 20:28:23  tony
 * TIR USRO-R-DWES-65ZNGE
 * Vertical Table Tab selection.
 *
 * Revision 1.12  2004/11/02 00:31:53  joan
 * fixes for PDG dialog
 *
 * Revision 1.11  2004/10/11 20:57:19  tony
 * added debug functionality.
 *
 * Revision 1.10  2004/09/10 18:00:26  tony
 * MN20687400 This may need to be modified in the future as
 * I was not able to recreate the problem.  But this should help
 *
 * Revision 1.9  2004/08/19 15:12:05  tony
 * xl8r
 *
 * Revision 1.8  2004/06/24 20:29:26  tony
 * TIR USRO-R-SWWE-629MHH
 *
 * Revision 1.7  2004/06/22 18:07:30  tony
 * EditRelator Parent/Child Indicator
 *
 * Revision 1.6  2004/06/17 14:53:36  tony
 * relator_edit
 *
 * Revision 1.5  2004/05/21 20:52:51  tony
 * cleaned-up code
 *
 * Revision 1.4  2004/04/12 17:18:42  tony
 * MN_18698482
 *
 * Revision 1.3  2004/02/27 18:53:42  tony
 * removed display statements.
 *
 * Revision 1.2  2004/02/26 21:53:18  tony
 * remoteDatabaseInterface centralization.
 *
 * Revision 1.1.1.1  2004/02/10 16:59:48  tony
 * This is the initial load of OPICM
 *
 * Revision 1.79  2004/01/08 17:40:27  tony
 * 53510
 *
 * Revision 1.78  2004/01/05 17:00:51  tony
 * acl_20040104
 * updated logic to prevent null pointer on close tab.
 *
 * Revision 1.77  2003/11/24 17:24:31  tony
 * 52887
 *
 * Revision 1.76  2003/11/14 20:45:09  tony
 * 52862
 *
 * Revision 1.75  2003/11/11 00:42:22  tony
 * accessibility update, added convenience method to table.
 *
 * Revision 1.74  2003/11/10 22:51:02  tony
 * accessibility update
 *
 * Revision 1.73  2003/11/10 15:35:01  tony
 * accessibility
 *
 * Revision 1.72  2003/10/29 16:47:40  tony
 * 52728
 *
 * Revision 1.71  2003/10/29 00:12:46  tony
 * removed System.out. statements.
 *
 * Revision 1.70  2003/10/22 17:12:03  tony
 * 52674
 *
 * Revision 1.69  2003/10/20 19:03:02  tony
 * 52624
 *
 * Revision 1.68  2003/10/20 16:37:15  tony
 * memory update.  Updated dereference logic to close memory gaps
 *
 * Revision 1.67  2003/10/14 20:41:24  tony
 * 51832
 *
 * Revision 1.66  2003/10/13 22:51:42  tony
 * 52540
 *
 * Revision 1.65  2003/10/08 22:17:41  tony
 * removed display statement
 *
 * Revision 1.64  2003/10/08 20:11:06  tony
 * 52476
 *
 * Revision 1.63  2003/10/03 16:39:12  tony
 * updated accessibility.
 *
 * Revision 1.62  2003/10/02 18:23:27  tony
 * updated for accessibility...
 * logic for screen reader updated.
 *
 * Revision 1.61  2003/09/24 15:53:04  tony
 * 52365
 *
 * Revision 1.60  2003/09/23 18:27:21  tony
 * 52354
 *
 * Revision 1.59  2003/09/22 19:37:27  tony
 * 52340
 *
 * Revision 1.58  2003/09/16 18:19:14  tony
 * 52275
 *
 * Revision 1.57  2003/09/15 20:49:26  tony
 * 52151
 *
 * Revision 1.56  2003/09/12 16:12:46  tony
 * 52196
 * 52189
 *
 * Revision 1.55  2003/09/11 18:09:25  tony
 * acl_20030911
 * updated addRow logic to sort based on a boolean.
 *
 * Revision 1.54  2003/09/11 17:24:01  tony
 * updated attribute put logic to display exception for
 * duplicate and fill paste
 *
 * Revision 1.53  2003/09/11 16:30:20  tony
 * 52148
 * update FillPaste and Duplicate functions.
 *
 * Revision 1.52  2003/09/11 00:13:25  tony
 * 52148
 *
 * Revision 1.51  2003/09/04 17:47:23  tony
 * 52045
 *
 * Revision 1.50  2003/09/03 20:14:01  tony
 * 52030
 *
 * Revision 1.49  2003/08/28 17:48:52  tony
 * 51974
 *
 * Revision 1.48  2003/08/27 16:01:37  tony
 * 51939
 *
 * Revision 1.47  2003/08/21 22:20:46  tony
 * 51732
 *
 * Revision 1.46  2003/08/21 18:06:40  tony
 * 51865
 * 51869
 *
 * Revision 1.45  2003/08/21 15:58:14  tony
 * 51869
 *
 * Revision 1.44  2003/08/20 18:00:04  tony
 * 51834
 *
 * Revision 1.43  2003/08/19 14:45:08  tony
 * 51804
 *
 * Revision 1.42  2003/07/29 22:54:32  joan
 * 51559
 *
 * Revision 1.41  2003/07/29 16:58:45  tony
 * 51553
 *
 * Revision 1.40  2003/07/22 15:47:20  tony
 * updated date editor capability to improve performance and
 * functionality.
 *
 * Revision 1.39  2003/07/10 20:38:47  tony
 * 51433 removed superEditCellAt method and calls.
 *
 * Revision 1.38  2003/07/10 18:02:20  tony
 * 51433
 *
 * Revision 1.37  2003/07/10 15:44:23  tony
 * 51317
 *
 * Revision 1.36  2003/07/09 20:47:36  tony
 * 51422 -- blobEditor popup on double click
 *
 * Revision 1.35  2003/06/27 20:09:11  tony
 * updated logic to prevent validation when in search mode
 * for certain rules.
 * updated messageing.
 *
 * Revision 1.34  2003/06/20 22:34:17  tony
 * 1.2 modification.
 *
 * Revision 1.33  2003/06/19 16:09:43  tony
 * 51298 -- limited capability when working in the past.
 * updated past date logic.
 *
 * Revision 1.32  2003/06/16 23:28:49  tony
 * 51242
 *
 * Revision 1.31  2003/06/09 19:20:12  tony
 * 51242
 *
 * Revision 1.30  2003/06/02 18:29:33  joan
 * 51068
 *
 * Revision 1.29  2003/05/30 21:09:24  tony
 * updated messaging logic to popup on top
 * of the currently active Component.
 *
 * Revision 1.28  2003/05/28 20:42:16  tony
 * 50973
 *
 * Revision 1.27  2003/05/21 15:38:11  tony
 * updated table logic to allow for the table and model
 * to always know the specific type of table.  Based on a
 * table constant.
 *
 * Revision 1.26  2003/05/20 21:35:23  tony
 * 50827
 *
 * Revision 1.25  2003/05/20 21:07:00  tony
 * updated logic on table edit to allow all editors to
 * behave similarly.
 *
 * Revision 1.24  2003/05/13 22:45:06  tony
 * 50616
 * Switched keys from a string to a pointer to the
 * EANFoundation.
 *
 * Revision 1.23  2003/05/13 16:08:37  tony
 * 50621
 *
 * Revision 1.22  2003/05/08 22:32:27  tony
 * 50550
 * 50576
 *
 * Revision 1.21  2003/05/07 18:03:46  tony
 * cleaned-up code.
 *
 * Revision 1.20  2003/05/07 17:32:34  tony
 * 50562
 *
 * Revision 1.19  2003/04/29 17:24:40  tony
 * cleaned-up longEditor to properly display to
 * enhance editing functionality.
 *
 * Revision 1.18  2003/04/25 19:21:12  tony
 * added border logic to tables.
 *
 * Revision 1.17  2003/04/24 22:23:18  tony
 * updated multi-editor renderinf logic.
 *
 * Revision 1.16  2003/04/24 21:49:10  tony
 * fixed ctrl-a multiflag issue per kc
 *
 * Revision 1.15  2003/04/23 23:35:50  tony
 * updated 50402 to integrate changes properly.
 *
 * Revision 1.13  2003/04/22 16:39:11  tony
 * per dave updated logic to improve performance while
 * toggling between vertical and horizontal editors.
 *
 * Revision 1.12  2003/04/21 20:03:13  tony
 * enhanced scroll logic to improve flag editor.
 *
 * Revision 1.11  2003/04/18 15:42:42  tony
 * enhnaced multiEditor functionality.
 *
 * Revision 1.10  2003/04/18 14:40:44  tony
 * enhanced record toggle logic per KC multiple create
 * did not toggle thru records properly.
 *
 * Revision 1.9  2003/04/16 17:20:50  tony
 * replaced has paste with can paste
 *
 * Revision 1.8  2003/04/15 17:31:34  tony
 * changed to e-announce.focusborder
 *
 * Revision 1.7  2003/04/14 21:38:25  tony
 * updated table Logic.
 *
 * Revision 1.6  2003/03/27 21:29:05  joan
 * fix bug
 *
 * Revision 1.5  2003/03/26 19:16:31  tony
 * xmlEditor, integrated XMLeditor.
 *
 * Revision 1.4  2003/03/24 19:56:15  tony
 * System enhancements to improve usability and
 * functionality of the application.
 *
 * Revision 1.3  2003/03/05 19:16:49  tony
 * AttributeChangeHistoryGroup addition.
 *
 * Revision 1.2  2003/03/05 18:54:26  tony
 * accessibility updates.
 *
 * Revision 1.1.1.1  2003/03/03 18:03:51  tony
 * This is the initial load of OPICM
 *
 * Revision 1.134  2002/12/09 19:12:44  tony
 * 23525 -- update fixed painting
 *
 * Revision 1.133  2002/12/06 18:01:07  tony
 * 23525
 *
 * Revision 1.132  2002/12/02 18:40:28  tony
 * 23421
 *
 * Revision 1.131  2002/11/26 16:37:40  tony
 * 23373
 *
 * Revision 1.130  2002/11/22 22:14:32  tony
 * acl_20021122 -- logic enhanced for painting
 * found on vertical table.
 *
 * Revision 1.129  2002/11/21 20:03:26  tony
 * added logic for trapping error on dynaTable search
 *
 * Revision 1.128  2002/11/19 19:27:23  joan
 * fix lock
 *
 * Revision 1.127  2002/11/19 18:16:48  tony
 * 23268
 *
 * Revision 1.126  2002/11/19 16:06:18  tony
 * 23263
 *
 * Revision 1.125  2002/11/19 01:27:47  joan
 * adjust lock method
 *
 * Revision 1.124  2002/11/18 18:21:05  tony
 * 23239
 *
 * Revision 1.123  2002/11/15 16:49:22  tony
 * 23187
 *
 * Revision 1.122  2002/11/11 22:55:14  tony
 * removed System.out.println() statements
 * adjusted classification on the toggle
 *
 * Revision 1.121  2002/11/07 22:21:03  tony
 * vertical was not reclassifying attributes properly.
 *
 * Revision 1.120  2002/11/07 16:58:35  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.table;
import com.elogin.*;
import com.ibm.eannounce.dialogpanels.XMLPanel;
import com.ibm.eannounce.eforms.edit.EditController; //51317
import com.ibm.eannounce.eforms.edit.Editable;
import com.ibm.eannounce.eforms.edit.VertEditor; //51317
import com.ibm.eannounce.eforms.editor.*;
import com.ibm.eannounce.erend.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;
import java.awt.*; //copy
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class VertTable extends RSTable implements ETable, ActionListener,Editable  { //20020107
	private static final long serialVersionUID = 1L;
    private LongRend lr = new LongRend();
    private FlagRend fr = new FlagRend();
    private ERend dr = new ERend();
    private FoundRend foundRenderer = new FoundRend();
    private LockedFoundRend lockedFoundRenderer = new LockedFoundRend();
    private LockedDefaultRend lockedDefaultRenderer = new LockedDefaultRend();
    private LockedFoundFlagRend lockedFoundFlagRenderer = new LockedFoundFlagRend();
    private FoundFlagRend foundFlagRenderer = new FoundFlagRend();
    private LockedFlagRend lockedFlagRenderer = new LockedFlagRend();
    private LockedFoundLongRend lockedFoundLongRenderer = new LockedFoundLongRend();
    private FoundLongRend foundLongRenderer = new FoundLongRend();
    private LockedLongRend lockedLongRenderer = new LockedLongRend();
    private ERowRend rowHeaderRenderer = new ERowRend();
    private ERowFoundRend rowHeaderFoundRenderer = new ERowFoundRend();

    private TextEditor edit = new TextEditor();
    private BlobEditor blobEdit = new BlobEditor();
    private FlagEditor flagEdit = new FlagEditor();
    private LongEditor longEdit = new LongEditor();
    private DateTimeEditor dateEdit = new DateTimeEditor(DateTimeEditor.DATE_VALIDATOR);
    //	private xmlDialog  xmlEdit  = Opicm.getOpicm().getXMLDialog();
    private XMLPanel xmlEdit = eaccess().getXMLPanel();
    private DateTimeEditor timeEdit = new DateTimeEditor(DateTimeEditor.TIME_VALIDATOR);
    private MultiEditor multiEdit = new MultiEditor(true);

    //23421	private boolean filterOn = false;

    private MetaValidator validator = new MetaValidator(); //20020207
    private RowSelectableTable controllingTable = null;
    private String curKey = null;
    //22713	private String[] fillKeys = null;
    private Fillvector vFill = new Fillvector(); //22713
    private boolean bSearch = false; //52365

	private VertEditor m_vertEditor = null;
    /**
     * vertTable
     * @param _o
     * @param _table
     * @author Anthony C. Liberto
     */
    public VertTable(Object _o, RowSelectableTable _table) {
        super(_o, _table, TABLE_VERTICAL);
        init();
        setReplaceable(eaccess().isEditable(_table)); //51298
    }

    /**
     * createDefaultColumns
     * @author Anthony C. Liberto
     * /
    protected void createDefaultColumns() {
        if (cgtm != null) {
            int ii = cgtm.getColumnCount();
            int rows = cgtm.getRowCount();
            TableColumnModel cm = getColumnModel();
            while (cm.getColumnCount() > 0) {
                cm.removeColumn(cm.getColumn(0));
            }
            for (int i = 0; i < ii; i++) {
                RSTableColumn newColumn = new RSTableColumn(i);
                if (i == 1) {
                    newColumn.setMinimumWidth(RSTableColumn.MIN_BLOB_WIDTH);
                }
                newColumn.setIdentifier(cgtm.getColumnKey(i));
                if (rows > 0) {
                    newColumn.setMeta(cgtm.getEANObject(0, i));
                }
                //52728				newColumn.setMinWidth(newColumn.getMinimumPreferredWidth());
                newColumn.setMinWidth(newColumn.getMinimumAllowableWidth()); //52728
                addColumn(newColumn);
            }
        }
    }*/

    /**
     * updateModel
     *
     * @author Anthony C. Liberto
     * @param _table
     */
    public void updateModel(RowSelectableTable _table) { //21722
        if (getKey().equals("PDG")) {
            RowSelectableTable rst = getEditController().getTable();
            rst.refresh();
            cgtm.updateModel(rst);
        }

        setControllingTable(_table); //21722
        toggleRecord(getKey()); //21722
        resizeCells(); //21722
    } //21722

    /**
     * setControllingTable
     * @param _table
     * @author Anthony C. Liberto
     */
    public void setControllingTable(RowSelectableTable _table) {
        controllingTable = _table;
    }

    /**
     * getTableHeight
     *
     * @return
     * @author Anthony C. Liberto
     */
    public int getTableHeight() {
        int rr = getRowCount();
        Dimension d = getIntercellSpacing();
        int height = 0;
        for (int r = 0; r < rr; ++r) {
            height += getRowHeight(r) + d.height;
        }
        return height;
    }

    /**
     * @see javax.swing.JTable#prepareEditor(javax.swing.table.TableCellEditor, int, int)
     * @author Anthony C. Liberto
     */
    public Component prepareEditor(TableCellEditor _editor, int _row, int _col) {
        Object o = getEANObject(_row, _col);
        boolean isSelected = isCellSelected(_row, _col);
        Component comp = _editor.getTableCellEditorComponent(this, o, isSelected, _row, _col);
        if (comp instanceof MultiEditor) {
            Rectangle rect = getCellRect(_row, _col, false);
            rect.setSize(rect.width, 16);
            scrollRectToVisible(rect);
        }
        return comp;
    }

    /**
     * @see javax.swing.JTable#editCellAt(int, int, java.util.EventObject)
     * @author Anthony C. Liberto
     */
    public boolean editCellAt(int _r, int _c, EventObject _e) {
//		System.out.println("VERTTABLE editCellAt 00 ");

        boolean displayOnly = false;
        KeyEvent ke = null;
        int iClick = 0;
        boolean b = false;
        Object o = null;
        int code = -1;
        LockGroup lockGroup = null;
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
//System.out.println("VERTTABLE editCellAt 01 ");
        if (isEditing() && editingCanStop()) {
            return false;
        } else if (_e == null) {
            return false;
        } else if (iClick == 1) {
            return false;
        } else if (_e instanceof KeyEvent) {
            ke = (KeyEvent) _e;
            code = ke.getKeyCode();
            if (code == KeyEvent.VK_F2 || ke.isControlDown() || ke.isMetaDown() || ke.isAltDown() || ke.isActionKey() || !Character.isDefined(ke.getKeyChar())) {
                return false;
            } else if (code == KeyEvent.VK_DELETE) {
                if (bSearch) { //52365
                    deactivateAttribute(_r); //52365
                } //52365
                repaint();
                return false;
            }
        }
//System.out.println("VERTTABLE editCellAt 02 ");
        if (bSearch) { //52624
            if (_c != 1) { //52624
                return false; //52624
            } //52624
        } else if (!isCellLocked(_r, _c)) {
            displayOnly = true; //blobEditor
            if (!isCellLocked(_r, _c, true)) {
                displayOnly = true; //blobEditor
                lockGroup = getLockGroup(_r, _c); //lock_update
                if (lockGroup != null) { //lock_update
                    setMessage(lockGroup.toString());
                    showError();
                } //lock_update
                getAttribute(_r, _c);
                viewCellAt(_r, _c, _e);
                return false;
            } else {
                displayOnly = false; //blobEditor
                resizeCells(); //22428
                repaint();
            }
        }
//System.out.println("VERTTABLE editCellAt 03 ");
        b = super.editCellAt(_r, _c, _e); //20011227
        scrollRectToVisible(getCellRect(_r, _c, false)); //22428
//System.out.println("VERTTABLE editCellAt 04 ");

		EANAttribute att = getAttribute(_r, _c);
		EANMetaAttribute meta = att.getMetaAttribute();

//		System.out.println("VERTTABLE editCellAt attisPDGINFO( : " + meta.isPDGINFO());
		if (getKey().equals("PDG") && meta.isPDGINFO()) {
			if (m_vertEditor != null) {
				if (isModalBusy()) {
					return false;
				}

				m_vertEditor.getPDGInfo(meta);

			}
		}

        if (!b) { //22248
            if (xmlEdit.isShowing()) { //53510
                xmlEdit.processEvent(_e); //53510
            } //53510
            return false; //22248
        } //22248
        o = getCellEditor(); //20011227
//System.out.println("VERTTABLE editCellAt 05 ");
        if (o instanceof EditorInterface) {
            //51422			((editorInterface)o).prepareToEdit();
            //51869			((editorInterface)o).prepareToEdit(_e);				//51422
            ((EditorInterface) o).prepareToEdit(_e, this); //51869
        }
//System.out.println("VERTTABLE editCellAt 06 ");
        if (o instanceof MultiEditor) {
            if (_e instanceof KeyEvent) {
                //52674				((multiEditor)o).showPopup();
                ((MultiEditor) o).showPopup((KeyEvent) _e); //52674
            }
            ((MultiEditor) o).requestFocus();
        } else if (o instanceof BlobEditor) { //blobEditor
            ((BlobEditor) o).setDisplayOnly(displayOnly); //blobEditor
            if (isModalCursor()) { //51559
                ((BlobEditor) o).setModalCursor(true); //51559
            } else { //51559
                ((BlobEditor) o).setModalCursor(false); //51559
            }
        } else if (o instanceof FlagEditor && ke != null) { //51732
            ((FlagEditor) o).preloadKeyEvent(ke); //52887
		} else if (o instanceof LongEditor && ke != null) {	//MN_25042000
			((LongEditor)o).preloadKeyEvent(ke);			//MN_25042000
        } //51732

        if (b) {
            repaint();
        }
        return b; //20011227
    }

    /**
     * init
     *
     * @author Anthony C. Liberto
     */
    public void init() {
        setRowMargin(0);
        setBorder(UIManager.getBorder("eannounce.focusborder"));
        setColumnSelectionAllowed(false);
        setAutoResizeMode(AUTO_RESIZE_OFF);
        getTableHeader().setReorderingAllowed(false);
        resizeCells();
        initAccessibility("accessible.vertTable");
        //		sort();
        setBorder(UIManager.getBorder("eannounce.focusBorder"));
        setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        if (getRowCount() > 0) {
            setRowSelectionInterval(0, 0);
        }
        //		setDefaultRenderer(MultiFlagAttribute.class, fr);
        //		setDefaultRenderer(LongTextAttribute.class, lr);
        //		setDefaultRenderer(XMLAttribute.class, lr);				//22612
        setDefaultRenderer(Object.class, dr); //accessibility
        //		addAncestorListener(this);						//20020107
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

        Class cls = getRowClass(_r);

        TableCellRenderer rend = getCellRenderer(_r, _c, cls);
        boolean isFlag = (rend == fr);
        boolean isLong = (rend == lr);

        if (_c == 0) {
            if (isFound) {
                return rowHeaderFoundRenderer;
            }
            return rowHeaderRenderer;
        }

        if (isFlag) {
            if (isLocked) {
                if (isFound) {
                    return lockedFoundFlagRenderer;
                }
                return lockedFlagRenderer;
            }
            if (isFound) {
                return foundFlagRenderer;
            }
            return rend;
        } else if (isLong) {
            if (isLocked) {
                if (isFound) {
                    return lockedFoundLongRenderer;
                }
                return lockedLongRenderer;
            }
            if (isFound) {
                return foundLongRenderer;
            }
            return rend;
        } else {
            if (isLocked) {
                if (isFound) {
                    return lockedFoundRenderer;
                }
                return lockedDefaultRenderer;
            }
            if (isFound) {
                return foundRenderer;
            }
        }
        return rend;
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
            multiEdit.setKey(cgtm.getRowKey(_r));
            multiEdit.setTable(cgtm.getTable());
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
            show(this, xmlEdit, false);
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
     * replaceString
     * @author Anthony C. Liberto
     * @return boolean
     * @param _c
     * @param _new
     * @param _old
     * @param _r
     */
    public boolean replaceString(String _old, String _new, int _r, int _c) {
        if (_c > 0) { //21645
            EANAttribute att = getAttribute(_r, _c);
            String newStr = null;
            if (att instanceof LongTextAttribute) {
                newStr = Routines.replaceString((String) ((LongTextAttribute) att).get(), _old, _new);
            } else if (att instanceof XMLAttribute) {
                newStr = Routines.replaceString((String) ((XMLAttribute) att).get(), _old, _new);
            } else if (att instanceof TextAttribute) {
                newStr = Routines.replaceString((String) ((TextAttribute) att).get(), _old, _new);
            }
            validator.setAttribute(att);
            if (newStr == null) {
                return false;
            }
            if (!validator.validate(newStr, newStr, 0)) {
                return false;
            }
            //23373			setValueAt(newStr,_r,_c);
            //50973			putValueAt(newStr,_r,_c);													//23373
            putValueAt(newStr, _r, convertColumnIndexToModel(_c)); //50973
            if (ec != null) { //23268
                ec.refreshUpdate(); //23268
            } else if (ac != null) { //23268
                ac.refreshUpdate(); //23268
            } //23268
            resizeCells(); //52045
            repaint();
            return true;
        } else { //21645
            return false; //21645
        } //21645
    }

    /**
     * toggleRecord
     * @param _key
     * @param _ean
     * @author Anthony C. Liberto
     */
    public void toggleRecord(String _key, EANFoundation _ean) {
        stopEditing();
        if (!isDynaTable()) {
            if (controllingTable == null) {
                return;
            }
            if (_ean != null && _ean instanceof EntityItem) {
                EntityItem ei = (EntityItem) _ean;
//                System.out.println("VertTable(0) toggling to: " + ei.getKey());
//                System.out.println("    ei.class: " + ei.getClass().getName());
                RowSelectableTable table = ei.getEntityItemTable();
                table.setLongDescription(true);
                cgtm.updateModel(table);
                setKey(_key);
            }
        }
    }

    private void toggleRecord(String _key) {
        stopEditing();
        if (!isDynaTable()) { //dyna
            EANFoundation ean = null;
            if (controllingTable == null) {
                return;
            }
            ean = controllingTable.getRow(_key);
            if (ean != null && ean instanceof EntityItem) {
                EntityItem ei = (EntityItem) ean;
//                System.out.println("VertTable(1) toggling to: " + ei.getKey());
//                System.out.println("    ei.class: " + ei.getClass().getName());
                RowSelectableTable table = ei.getEntityItemTable();
                table.setLongDescription(true);

                cgtm.updateModel(table); //classification
                setKey(_key);
            }
        } //dyna
    }

    /**
     * setKey
     * @param _s
     * @author Anthony C. Liberto
     */
    public void setKey(String _s) {
		System.out.println("VertTable setKey: " + _s);
        curKey = _s;
    }

    /**
     * updateKey
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    public boolean updateKey(String _s) { //22098
        if (!curKey.equals(_s)) { //22098
            setKey(_s); //22098
            return true; //22098
        } //22098
        return false; //22098
    } //22098

    /**
     * getKey
     * @return
     * @author Anthony C. Liberto
     */
    public String getKey() {
        return curKey;
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @author Anthony C. Liberto
     */
    public void actionPerformed(ActionEvent _ae) {
    }

    /**
     * moveColumn
     *
     * @param _left
     * @author Anthony C. Liberto
     */
    public void moveColumn(boolean _left) {
    }

    /**
     * showEffectivity
     * @author Anthony C. Liberto
     */
    public void showEffectivity() {
        //		if (dateD == null) createDateDialog();
        //		int r = getSelectedRow();
        //		if (isValidCell(r,1)) {
        //			EANAttribute att = getAttribute(r,1);
        //			if (att != null) {
        //				dateD.setDates(att);
        //				dateD.show();
        //			}
        //		}
    }

    /**
     * isColumnOrderable
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean isColumnOrderable() {
        return false;
    }*/

    /**
     * getCurrentEntityItem
     *
     * @param _new
     * @return
     * @author Anthony C. Liberto
     */
    public EntityItem getCurrentEntityItem(boolean _new) { //21753
        EntityItem ei = getCurrentEntityItem(); //21753
        if (ei == null) { //21753
            return null; //21753

        } else if (_new) { //21753
            try { //21753
                return new EntityItem(ei); //21753
            } catch (MiddlewareRequestException _mre) { //21753
                _mre.printStackTrace(); //21753
                return ei; //21753
            } //21753
        } else { //21753
            return ei;
        } //21753
    } //21753

    /**
     * dereference
     *
     * @author Anthony C. Liberto
     */
    public void dereference() {
        clearSelection(); //52030
        vFill.clear(); //22713
        if (cellEditor != null) { //21864
            cellEditor.cancelCellEditing();
        } //21864
        controllingTable = null;

        if (blobEdit != null) {
            blobEdit.dereference();
            blobEdit = null;
        }

        xmlEdit = null;

        if (edit != null) {
            edit.dereference();
            edit = null;
        }

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

        if (lockedFoundFlagRenderer != null) {
            lockedFoundFlagRenderer.dereference();
            lockedFoundFlagRenderer = null;
        }

        if (foundFlagRenderer != null) {
            foundFlagRenderer.dereference();
            foundFlagRenderer = null;
        }

        if (lockedFlagRenderer != null) {
            lockedFlagRenderer.dereference();
            lockedFlagRenderer = null;
        }

        if (lockedFoundLongRenderer != null) {
            lockedFoundLongRenderer.dereference();
            lockedFoundLongRenderer = null;
        }

        if (foundLongRenderer != null) {
            foundLongRenderer.dereference();
            foundLongRenderer = null;
        }

        if (lockedLongRenderer != null) {
            lockedLongRenderer.dereference();
            lockedLongRenderer = null;
        }

        if (lr != null) {
            lr.dereference();
            lr = null;
        }

        if (fr != null) {
            fr.dereference();
            fr = null;
        }
        super.dereference();
    }

    private void deactivateAttribute(int _row) {
        if (isValidCell(_row, 1)) {
            if (isCellLocked(_row, 1)) { //23187
                if (isCellEditable(_row, 1)) { //51865
                    putValueAt(null, _row, 1);
                    refreshClassification(); //50576
                } //51865
            } //23187
        }
    }

    private void deactivateAttribute(int[] _rows) {
        int ii = _rows.length;
        for (int i = 0; i < ii; ++i) {
            deactivateAttribute(_rows[i]);
        } //23187
    }

    /**
     * deactivateAttribute
     * @author Anthony C. Liberto
     */
    public void deactivateAttribute() {
        lockRows(); //23187
        deactivateAttribute(getSelectedRows());
    }
    //20011228
    //	public void setValueAt(Object o, int r, int c) {
    //		super.setValueAt(o,r,c);
    //		resizeCells();
    //		return;
    //	}

    /**
     * @see javax.swing.JTable#removeEditor()
     * @author Anthony C. Liberto
     */
    public void removeEditor() {
        /*
        		TableCellEditor edit = cellEditor;
        		if (edit instanceof DateBox) {
        			if (dateEdit.isPopupShowing())
        				dateEdit.hidePopup();
        		} else if (edit instanceof lTextBox) {
        			if (longEdit.isPopupShowing())
        				longEdit.hidePopup();
        		} else if (edit instanceof mFlagBox) {
        			if (mFlagEdit != null)
        				if (mFlagEdit.isPopupShowing())
        					mFlagEdit.hidePopup();
        		}
        */
        super.removeEditor();
    }

    /**
     * getCellRenderer
     * @param r
     * @param c
     * @param _cl
     * @return
     * @author Anthony C. Liberto
     */
    private TableCellRenderer getCellRenderer(int r, int c, Class _cl) { //013176
        if (_cl == MultiFlagAttribute.class) {
            return fr;
        } else if (_cl == LongTextAttribute.class) {
            return lr;
        } else if (_cl == XMLAttribute.class) {
            return lr;
        }
        return dr;
    }

    /**
     * ancestorAdded
     * @param event
     * @author Anthony C. Liberto
     */
    public void ancestorAdded(AncestorEvent event) {
    }
    /**
     * ancestorRemoved
     * @param event
     * @author Anthony C. Liberto
     */
    public void ancestorRemoved(AncestorEvent event) {
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
     * moveToErrorPDG
     * @param bre
     * @author Anthony C. Liberto
     */
    public void moveToErrorPDG(EANBusinessRuleException bre) { //51068
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
        if (pt.x >= controllingTable.getRowCount() || pt.x < 0) {
            return;
        }
        if (pt.y >= controllingTable.getColumnCount() || pt.y < 0) {
            return;
        }

        setRowSelectionInterval(pt.x, pt.x);
        setColumnSelectionInterval(1, 1);

        scrollRectToVisible(getCellRect(pt.y, 1, false));
        grabFocus();
    }

    /**
     * moveToError
     * @author Anthony C. Liberto
     * @param bre
     */
    public void moveToError(EANBusinessRuleException bre) { //20069
        Object o = bre.getObject(0);
        Point pt = new Point(0, 0);
        EANFoundation ean = null;
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
        if (pt.x >= controllingTable.getRowCount() || pt.x < 0) {
            return;
        }
        if (pt.y >= controllingTable.getColumnCount() || pt.y < 0) {
            return;
        }
        ean = controllingTable.getRow(pt.x);

        if (ean != null && ean instanceof EntityItem) { //52354
            EntityItem ei = (EntityItem) ean; //52354
            RowSelectableTable table = ei.getEntityItemTable(); //52354
            table.setLongDescription(true); //52354
            cgtm.updateModel(table); //52354
            setKey(ean.getKey()); //52354
            if (ec != null) { //52354
                ec.updateRecordLabel(pt.x); //52354
            } //52354
        } //52354

        setRowSelectionInterval(pt.y, pt.y);
        setColumnSelectionInterval(1, 1);

        scrollRectToVisible(getCellRect(pt.y, 1, false));
        //52354		setKey(ean.getKey());
        grabFocus();
    }

    /**
     * getMasterRowCount
     * @return
     * @author Anthony C. Liberto
     */
    public int getMasterRowCount() { //22150
        if (controllingTable != null) { //22150
            return controllingTable.getRowCount();
        } //22150
        return -1; //22150
    } //22150

    /**
     * getMasterColumnCount
     * @return
     * @author Anthony C. Liberto
     * /
    public int getMasterColumnCount() { //22150
        if (controllingTable != null) { //22150
            return controllingTable.getColumnCount();
        } //22150
        return -1; //22150
    } //22150
    */

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
     * @param _code
     * @return
     * @author Anthony C. Liberto
     */
    private Point selectCell(EntityItem _ei, String _code) {
        if (getKey().equals("PDG")) {
            //dwb_20041117			int r = controllingTable.getRowIndex(_ei.getEntityType() + ":" + _code);
            String sKey = eaccess().getKey(_ei, _code); //dwb_20041117
            int r = controllingTable.getRowIndex(sKey); //dwb_20041117
            int c = 1;
            return new Point(r, c);
        } else {
            EntityItem eip = (EntityItem) _ei.getUpLink(0);

			if (eip==null){  // try downlink if trying to create a SWFEATURE at MODEL uplink will be null
				eip = (EntityItem) _ei.getDownLink(0);
			}

            EANFoundation ean = controllingTable.getRow(_ei.getKey());
            int c = -1;
            int r = -1;
            if (ean == null && eip!=null) {
                ean = controllingTable.getRow(eip.getKey());
            }

            c = -1;
            if (ean != null) {
                //50562			RowSelectableTable table = ((EntityItem)ean).getEntityItemTable();
                //21998			c = table.getRowIndex(_ei.getEntityType() + ":" + code);
                //50562			c = cgtm.getRowIndex(_ei.getEntityType() + ":" + code);			//21998
                //dwb_20041117				c = cgtm.getViewRowIndex(_ei.getEntityType() + ":" + _code);		//50562
                c = cgtm.getViewRowIndex(eaccess().getKey(_ei, _code)); //dwb_20041117
            }

            r = controllingTable.getRowIndex(_ei.getKey());
            if (r < 0 && eip!=null) {
                r = controllingTable.getRowIndex(eip.getKey());
            }
            return new Point(r, c);
        }
    }

    /**
     * getCurrentEntityItem
     * @return
     * @author Anthony C. Liberto
     */
    public EntityItem getCurrentEntityItem() {
		if (controllingTable != null) {
	        EANFoundation ean = controllingTable.getRow(curKey);
	        if (ean != null) {
	            return (EntityItem) ean;
	        }
		}
        return null;
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
        boolean bMsg = true; //22829
        if (_row) {
            EntityItem ei = null;
            int aa = 0;
            fillClear();

            ei = getCurrentEntityItem(false); //52148
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
        } else {
            int[] rows = getSelectedRows();
            int rr = rows.length;
            for (int r = 0; r < rr; ++r) {
                bMsg = fillAdd(getAttribute(rows[r], 1), bMsg,1); //52196
            }
        }
    }

    /**
     * fillPaste
     * @author Anthony C. Liberto
     */
    public void fillPaste() {
        boolean bFill = false; //22711
        int ii = 0;
        setUpdateArmed(false);
        ii = vFill.size();
        lockRows(); //22833
        for (int i = 0; i < ii; ++i) {
            putFillValue(vFill.getAttribute(i));
            bFill = true;
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
        resizeCells(); //52340
        repaint(); //51804
        return;
    }

    private boolean fillAdd(EANAttribute _att, boolean _msg, int colid) { //22829
        if (_att != null) {
            if (_att instanceof BlobAttribute) { //22829
                if (_msg) { //22829
                    showError("msg23047"); //22829
                    return false; //22829
                } //22829
            } else { //22829
                vFill.add(_att, colid);
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
     * getSelectionKey
     * @return
     * @author Anthony C. Liberto
     */
    public String getSelectionKey() {
        int row = getSelectedRow();
        return cgtm.getRowKey(row);
    }

    /**
     * setSelection
     * @param _recKey
     * @param _selKey
     * @author Anthony C. Liberto
     */
    public void setSelection(String _recKey, String _selKey) {
        int row = cgtm.getViewRowIndex(_selKey);
        if (isValidCell(row, 1)) {
            setRowSelectionInterval(row, row);
            setColumnSelectionInterval(1, 1);
            toggleEnabled(row, 1); //23263
            scrollRectToVisible(getCellRect(row, 1, false));
        } else if (isValidCell(0, 1)) {
            setRowSelectionInterval(0, 0);
            setColumnSelectionInterval(1, 1);
            toggleEnabled(0, 1); //23263
            scrollRectToVisible(getCellRect(0, 1, false));
        }
    }

    /**
     * @see javax.swing.JComponent#scrollRectToVisible(java.awt.Rectangle)
     * @author Anthony C. Liberto
     */
    public void scrollRectToVisible(Rectangle _rect) { //22073
        _rect.setSize(10, _rect.height); //22073
        super.scrollRectToVisible(_rect); //22073
    } //22073

    /**
     * lockRows
     *
     * @author Anthony C. Liberto
     */
    public void lockRows() {
        String key = getKey();
        if (key != null) {
            Profile prof = getActiveProfile(); //22411
            EntityItem ean = (EntityItem) controllingTable.getRow(key);
            if (ean != null || key.equals("PDG")) {
                   // EntityItem lockOwnerEI = new EntityItem(null, prof, Profile.OPWG_TYPE, getActiveProfile().getOPWGID());
                    EntityItem lockOwnerEI = eaccess().getLockOwner(); // use hashtable if possible
                    eaccess().dBase().lock(controllingTable, getLockList(), prof, lockOwnerEI, LockGroup.LOCK_NORMAL);
           //     } catch (MiddlewareRequestException _mre) {
             //       _mre.printStackTrace();
               // }
                //22411				if (!hasLock(key,1)) {
                if (!hasLock(0, 1)) {
                    LockGroup lockGroup = getLockGroup(0, 1); //22283
                    if (lockGroup != null && lockGroup.getProfile() != prof) {
                        setMessage(lockGroup.toString());
                        showError();
                    }
                } else { //22428
                    resizeCells(); //22428
                } //22428
            }
        }
    }

    /**
     * unlockRows
     *
     * @author Anthony C. Liberto
     */
    public void unlockRows() {
        String key = getKey();
        EANFoundation ean = controllingTable.getRow(key);
        if (ean != null) {
            RowSelectableTable table = ((EntityItem) ean).getEntityItemTable();
            //Profile prof = getActiveProfile();
            //try {
                //EntityItem lockOwnerEI = new EntityItem(null, prof, Profile.OPWG_TYPE, prof.getOPWGID());
                EntityItem lockOwnerEI = eaccess().getLockOwner(); // use hashtable if possible
                eaccess().dBase().unlock(table, getLockList(), getActiveProfile(), lockOwnerEI, LockGroup.LOCK_NORMAL);
            //} catch (MiddlewareRequestException _mre) {
              //  _mre.printStackTrace();
            //}
        }
    }

    /**
     * prepareToEdit
     *
     * @author Anthony C. Liberto
     */
    public void prepareToEdit() {
        int col = 0;
        requestFocus();
        col = getSelectedColumn();
        if (col <= 0 && getColumnCount() > 1) {
            setColumnSelectionInterval(1, 1);
        }
        repaint();
    }

    private boolean isRequiredField(int _r, int _c) {
        Object o = getEANObject(_r, _c);
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
    public void changeSelection(int _row, int _col, boolean _toggle, boolean _extend) { //20072
        toggleEnabled(_row, _col); //23262
        // TIR USRO-R-DWES-65ZNGE		super.changeSelection(_row,_col,_toggle,_extend);
        super.changeSelection(_row, convertColumnIndexToModel(1), _toggle, _extend); // TIR USRO-R-DWES-65ZNGE
    }

    /**
     * toggleEnabled
     *
     * @param _row
     * @param _col
     * @author Anthony C. Liberto
     */
    protected void toggleEnabled(int _row, int _col) { //21737
        if (ec != null) { //21737
            boolean bReq = false;
            boolean bEdit = false;
            boolean bPaste = false;
            EANMetaAttribute meta = null;
            if (_col == 1) {
                bEdit = isEditable(_row, _col); //23263
                if (bEdit) {
                    bReq = isRequiredField(_row, _col); //23263
                    bPaste = isPasteable(_row, _col); //23263
                }
            }

			meta = getEANMetaAttribute(_row,_col);	//cr_FlagUpdate
			ec.toggleMaint(meta);									//cr_FlagUpdate

            ec.setEnabled("cut", !bReq && bEdit); //23263
            ec.setEnabled("dAct", bEdit); //23263
            ec.setEnabled("pste", bPaste && ec.canPaste()); //23263
        } //21737
        return; //21737
    } //21737

    /**
     * getInformation
     *
     * @return
     * @author Anthony C. Liberto
     */
    protected String getInformation() { //20187
        int r = getSelectedRow(); //20187
        int c = 1; //20187
        if (isValidCell(r, c)) { //20187
//			System.out.println("record key: " + curKey);
//			System.out.println("curEI: " + getCurrentEntityItem().getKey());
            EANAttribute att = getAttribute(r, c); //20187
            return Routines.getInformation(att); //20187
        } //20187
        return getString("nia"); //20187
    }

    /**
     * historyInfo
     *
     * @author Anthony C. Liberto
     */
    public void historyInfo() {
        int r = getSelectedRow();
        int c = 1;
        if (isValidCell(r, c)) {
            EANAttribute att = getAttribute(r, c);
            eaccess().getChangeHistory(att);
        }
    }

    /**
     * getUIPrefKey
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getUIPrefKey() { //21643
        String key = getKey();
        EntityItem ei = (EntityItem) controllingTable.getRow(key);
        if (ei != null) {
            return "VERT" + ei.getEntityType();
        } else {
            return "VERT";
        }
    }

    /**
     * refreshClassification
     *
     * @author Anthony C. Liberto
     */
    public void refreshClassification() { //22098
        //dwb_20030421		toggleRecord(getKey());									//22098
        if (getKey().equals("PDG")) { //50402
            RowSelectableTable rst = getEditController().getTable(); //50402
            rst.refresh(); //50402
            cgtm.updateModel(rst); //50402
        }
        cgtm.refresh(); //keeps calling new rowSelectableTable
        //		cgtm.reclassify(cgtm.getRowKeyArray());					//kc
        resizeCells(); //22098
    } //22098

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

    /**
     * resizeAfterEdit
     *
     * @param _row
     * @param _col
     * @author Anthony C. Liberto
     */
    protected void resizeAfterEdit(int _row, int _col) { //23239
        //51939		resizeEditColumn(_col);										//23239
        //51939		resizeEditRow(_row);										//23525
        resizeCells(); //51939
        resizeAndPaint(false); //23525
    } //23239

    /**
     * getKey
     *
     * @param i
     * @return
     * @author Anthony C. Liberto
     */
    public String getKey(int i) { //acl_20021122
        return curKey + cgtm.getRowKey(i); //acl_20021122
    } //acl_20021122
    /*
     50621
    */
    /**
     * getValidator
     * @return
     * @author Anthony C. Liberto
     * /
    public MetaValidator getValidator() {
        return validator;
    }*/

    /**
     * processFlags
     * @author Anthony C. Liberto
     * @param _flags
     */
    protected void processFlags(MetaFlag[] _flags) {
        resizeCells();
    }

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
     51242
     */
    /**
     * getHeaderWidth
     * @author Anthony C. Liberto
     * @return int
     * @param _tc
     */
    protected int getHeaderWidth(RSTableColumn _tc) {
        Object o = _tc.getHeaderValue();
        String str = "";
        if (o != null) {
            str = o.toString();
        }
        return Math.max(RSTableColumn.MIN_BLOB_WIDTH, getWidth(str));
    }
    /*
     dwb_20030527
     */
    /**
     * setSearch
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setSearch(boolean _b) {
        edit.setSearch(_b);
        longEdit.setSearch(_b);
        dateEdit.setSearch(_b);
        validator.setSearch(_b);
        cgtm.setSearch(_b); //52624
        bSearch = _b; //52365
    }

    /*
     51317
     */
    /**
     * setEditController
     * @author Anthony C. Liberto
     * @param _ec
     */
    public void setEditController(EditController _ec) {
        super.setEditController(_ec);
        if (longEdit != null) {
            longEdit.setComponent(_ec);
        }
    }

    /*
     52148
    */
    private void putFillValue(EANAttribute _fillAtt) {
        boolean bProcess = false; //MN20687400
        EANAttribute localAtt = null;
        if (_fillAtt != null) {
            EntityItem ei = getCurrentEntityItem();
            if (ei != null) {
                clearTransitionGroup(); //52151
                localAtt = ei.getAttribute(_fillAtt.getKey());
                if (localAtt != null) { //MN20687400
                    processAttribute(_fillAtt, localAtt); //MN20687400
                    bProcess = true; //MN20687400
                } else{
                    if (ei.hasDownLinks()) {
//                        System.out.println("local Att was null...  Check DownLinks for: " + _fillAtt.getKey());
                        int dd = ei.getDownLinkCount();
                        for (int d = 0; d < dd && localAtt == null; ++d) {
                            localAtt = ei.getDownLink(d).getAttribute(_fillAtt.getKey());
                            if (localAtt != null) { //MN20687400
                                processAttribute(_fillAtt, localAtt); //MN20687400
                                bProcess = true; //MN20687400
                            } //MN20687400
                        }
                    }
                }

                if (localAtt == null && !bProcess) { //MN20687400
                    if (ei.hasUpLinks()) { //MN20687400
//                        System.out.println("local Att was null...  Check UpLinks for: " + _fillAtt.getKey()); //MN20687400
                        int dd = ei.getUpLinkCount(); //MN20687400
                        for (int d = 0; d < dd && localAtt == null; ++d) { //MN20687400
                            localAtt = ei.getUpLink(d).getAttribute(_fillAtt.getKey()); //MN20687400
                            if (localAtt != null) { //MN20687400
                                processAttribute(_fillAtt, localAtt); //MN20687400
                            } //MN20687400
                        } //MN20687400
                    } //MN20687400
                } //MN20687400

                processTransitionGroup(); //52151
            }
        }
    }

    /*
     MN20687400
     */
    private void processAttribute(EANAttribute _fill, EANAttribute _local) {
        if (eaccess().isDebug()) {
            try {
                if (_local != null) {
                    appendLog("localAttribute.isEditable: " + _local.isEditable());
                    appendLog("localAttribute.isLocked  : " + _local.hasLock(_local.getKey(), eaccess().getLockOwner(), getActiveProfile()));
                } else {
                    appendLog("local is null");
                }
                if (_fill != null) {
                    appendLog("fillAttribute.value      : " + _fill.get().toString());
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
                    _local.put(_fill.get());
                } catch (StateTransitionException _ste) {
                    _ste.printStackTrace();
                    addTransitionItem(_fill, _local);
                } catch (EANBusinessRuleException _bre) {
                    _bre.printStackTrace();
                    eaccess().showException(_bre, this, ERROR_MESSAGE, OK);
                }
            }
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
        return ".vert";
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
        return getValueAt(_row, 1);
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
        Object o = getValueAt(_row, 0);
        if (o != null) {
            return o.toString();
        }
        return "";
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
            //52540			EntityGroup eg = ec.getEntityGroup();
            //52540			if (eg != null) {
            //52540				int iDispRows = getRowCount();
            //52540				int iTotalRows = eg.getActualColumnListCount();
            //52540				return !(iDispRows == iTotalRows);
            //52540			}
            EntityItem ei = getCurrentEntityItem(); //52540
            if (ei != null) { //52540
                int iDispRows = getRowCount(); //52540
                int iTotalRows = ei.getActualRowListCount(); //52540
                return iDispRows != iTotalRows; //52540
            } //52540
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
     */
    public void showHide(boolean _b) {
        if (_b) {
            String[] rows = getSelectedRowKeys();
            clearSelection();
            if (rows != null) {
                cgtm.removeRows(rows);
                resizeAndRepaint();
            }
        } else {
            cgtm.removeRows((String[]) null);
            resizeAndRepaint();
        }
        if (getRowCount() > 0) {
            if (getColumnCount() > 1) {
                setRowSelectionInterval(0, 0);
                setColumnSelectionInterval(1, 1);
                scrollToRow(0);
            }
        }
    }
    /*
     52624
     */
    /**
     * @see com.ibm.eannounce.eForms.table.GTable#isEditable()
     * @author Anthony C. Liberto
     */
    public boolean isEditable() {
        if (bSearch) {
            return true;
        }
        return super.isEditable();
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
        int iRow = controllingTable.getRowIndex(curKey);
        controllingTable.duplicateRow(iRow, _copies);
    }
    /*
     relator_edit
     */
    /**
     * @see javax.swing.JTable#getValueAt(int, int)
     * @author Anthony C. Liberto
     */
    public Object getValueAt(int _r, int _c) {
        if (cgtm != null) {
            int row = cgtm.getRowIndex(cgtm.getRowKey(_r));
            int col = convertColumnIndexToModel(_c);
            if (cgtm.isIndicateRelations()) { //TIR USRO-R-SWWE-629MHH
                if (isChild(row, col)) {
                    return INDICATE_CHILD + cgtm.getValueAt(row, col, true);
                } else if (isParent(row, col)) {
                    return INDICATE_PARENT + cgtm.getValueAt(row, col, true);
                }
            } //TIR USRO-R-SWWE-629MHH
            return cgtm.getValueAt(row, col, true);
        }
        return null;
    }

    /**
     * isChild
     * @param _r
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    private boolean isChild(int _r, int _c) {
        if (cgtm != null) {
            if (_c == 0) {
                return cgtm.isChild(_r, _c);
            }
        }
        return false;
    }

    /**
     * isParent
     * @param _r
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    private boolean isParent(int _r, int _c) {
        if (cgtm != null) {
            if (_c == 0) {
                return cgtm.isParent(_r, _c);
            }
        }
        return false;
    }
    /*
     TIR USRO-R-SWWE-629MHH
     */
    /**
     * createTableModel
     * @author Anthony C. Liberto
     * @return rsTableModel
     * @param _o
     * @param _table
     * @param _type
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

    /*
     xl8r
     */
    /**
     * getEntityItem
     *
     * @param _row
     * @param _new
     * @return
     * @author Anthony C. Liberto
     */
    public EntityItem getEntityItem(int _row, boolean _new) {
        return getCurrentEntityItem();
    }

    /*
     must resize veritcal the old way.
     */
    /**
     * resizeCells
     *
     * @author Anthony C. Liberto
     */
    public void resizeCells() {
        int rr = getRowCount();
        int cc = 0;
        int[] height = null;
        int[] width = null;
        if (!eaccess().canSize(rr)) {
            return;
        }
        cc = getColumnCount();
        height = new int[rr];
        width = new int[cc];
        for (int r = 0; r < rr; ++r) {
            boolean bMultiLine = isMultiLineClass(r);
            for (int c = 0; c < cc; ++c) {
                String str = getString(r, c, false);
                if (isHidden(r)) {
                    height[r] = -1;
                } else if (bMultiLine) {
                    if (isVerticalTableFormat()) {
                        height[r] = Math.max(height[r], getMultiLineHeight(str));
                    } else if (isMatrixTableFormat()) {
                        height[r] = Math.max(height[r], getMatrixLineHeight(str));
                    } else if (isHorizontalTableFormat()) {
                        height[r] = Math.max(height[r], 1);
                    }
                } else {
                    if (isHorizontalTableFormat()) {
                        height[r] = 1;
                    } else {
                        height[r] = Math.max(height[r], getHeight(str));
                    }
                }
                if (bMultiLine) {
                    if (isMatrixTableFormat()) {
                        width[c] = Math.max(width[c], getMatrixLineWidth(str));
                    } else {
                        width[c] = Math.max(width[c], getMultiLineWidth(str));
                    }
                } else {
                    width[c] = Math.max(width[c], getWidth(str));
                }
            }
        }
        processWidth(width);
        processHeight(height);
        resizeAndRepaint();
    }

    public void setVertEditor(VertEditor _ve) {
		m_vertEditor = _ve;
	}
}
