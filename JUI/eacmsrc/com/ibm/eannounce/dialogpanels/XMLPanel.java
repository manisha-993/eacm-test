/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/12/05
 * @author Anthony C. Liberto
 *
 * $Log: XMLPanel.java,v $
 * Revision 1.3  2008/01/30 16:26:54  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.2  2007/05/21 19:59:29  wendy
 * Prevent NPE in setAttribute()
 *
 * Revision 1.1  2007/04/18 19:43:25  wendy
 * Reorganized JUI module
 *
 * Revision 1.3  2006/11/09 00:31:43  tony
 * added monitor functionality
 *
 * Revision 1.2  2005/09/12 19:03:11  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:45  tony
 * This is the initial load of OPICM
 *
 * Revision 1.5  2005/09/08 17:59:00  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.4  2005/02/03 16:38:52  tony
 * JTest Second Pass Wrapup
 *
 * Revision 1.3  2005/02/02 17:30:21  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 23:18:20  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:29  tony
 * This is the initial load of OPICM
 *
 * Revision 1.14  2004/01/08 17:39:40  tony
 * 53510
 *
 * Revision 1.13  2003/12/08 20:44:24  tony
 * 53337
 *
 * Revision 1.12  2003/11/14 21:50:44  tony
 * cr_dtd
 *
 * Revision 1.11  2003/09/25 20:03:31  tony
 * 52398
 *
 * Revision 1.10  2003/06/02 19:46:40  tony
 * 51055
 * 51047
 *
 * Revision 1.9  2003/05/30 21:09:21  tony
 * updated messaging logic to popup on top
 * of the currently active Component.
 *
 * Revision 1.8  2003/05/20 21:07:00  tony
 * updated logic on table edit to allow all editors to
 * behave similarly.
 *
 * Revision 1.7  2003/04/09 17:39:21  tony
 * repaired logic to allow for menu to be shown on
 * xmlEditor
 *
 * Revision 1.6  2003/04/07 17:55:51  tony
 * added DEFAULT_DTD parameter to loadXML
 *
 * Revision 1.5  2003/03/29 00:06:27  tony
 * added remove Menu Logic
 *
 * Revision 1.4  2003/03/26 19:16:30  tony
 * xmlEditor, integrated XMLeditor.
 *
 * Revision 1.3  2003/03/25 21:44:48  tony
 * adjusted logic to integrate in the xmlEditor.
 *
 * Revision 1.2  2003/03/05 18:53:46  tony
 * adjusted logic so that XMLEditor is shown on the XMLPanel.
 *
 * Revision 1.1.1.1  2003/03/03 18:03:44  tony
 * This is the initial load of OPICM
 *
 *
 */
package com.ibm.eannounce.dialogpanels;
import com.elogin.*;
//import com.ibm.eannounce.editor.*;
import com.ibm.transform.oim.eacm.xml.editor.*;
import com.ibm.eannounce.eforms.editor.EditorInterface; //53337
import COM.ibm.eannounce.objects.*;
import com.ibm.eannounce.einterface.XMLInterface; //22429
import com.wallstreetwise.app.jspell.domain.*; //spelling
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;
import java.util.EventObject; //53510

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class XMLPanel extends AccessibleDialogPanel implements EAccessConstants, XMLEditorListener {
	private static final long serialVersionUID = 1L;
	private XMLEditor xeditor = null;
    private XMLAttribute att = null;
    private RowSelectableTable table = null;
    private String rowKey = null;
    private String colKey = null;
    private boolean editable = true;
    private XMLInterface xml = null; //22429
    private EditorInterface ei = null; //53337

    /**
     * xmlPanel
     * @param _root
     * @param _local
     * @author Anthony C. Liberto
     */
    public XMLPanel(JRootPane _root, JSpellDictionary _local) {
        super(new BorderLayout());
        xeditor = new XMLEditor(_root, _local);
        init();
        return;
    }

    /**
     * init
     * @author Anthony C. Liberto
     */
    public void init() {
        setModal(true);
        setTitle("xmlEditor");
        add("Center", xeditor);
        xeditor.addXMLEditorListener(this);
        return;
    }

    /**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
        xeditor.removeXMLEditorListener(this);
        ei = null;
        xml = null;
        super.dereference();
        return;
    }

    /**
     * setTable
     * @param _table
     * @author Anthony C. Liberto
     */
    public void setTable(RowSelectableTable _table) {
        table = _table;
        return;
    }

    /**
     * setTable
     * @param _xml
     * @author Anthony C. Liberto
     */
    public void setTable(XMLInterface _xml) {
        xml = _xml;
        return;
    }

    /**
     * getTable
     * @return
     * @author Anthony C. Liberto
     */
    public RowSelectableTable getTable() {
        return table;
    }

    /**
     * setRowKey
     * @param _s
     * @author Anthony C. Liberto
     */
    public void setRowKey(String _s) {
        rowKey = new String(_s);
        return;
    }

    /**
     * getRowKey
     * @return
     * @author Anthony C. Liberto
     */
    public String getRowKey() {
        return rowKey;
    }

    /**
     * setColumnKey
     * @param _s
     * @author Anthony C. Liberto
     */
    public void setColumnKey(String _s) {
        colKey = new String(_s);
        return;
    }

    /**
     * getColumnKey
     * @return
     * @author Anthony C. Liberto
     */
    public String getColumnKey() {
        return colKey;
    }

    /**
     * @see com.ibm.eannounce.editor.XMLEditorListener#attributeHelpTextRequested()
     * @author Anthony C. Liberto
     */
    public String attributeHelpTextRequested() {
        if (att != null) {
            return att.getMetaAttribute().getHelpValueText();
        }
        return null;
    }

    /**
     * showDialog
     * @author Anthony C. Liberto
     */
    public void showDialog() {
        showInterface(null); //22429
        return; //22429
    } //22429

    /**
     * showInterface
     * @param _xml
     * @author Anthony C. Liberto
     */
    public void showInterface(XMLInterface _xml) { //22429
        xml = _xml; //22429
        showDialog(this, this);
        return;
    }

    private void setEditable(boolean b) {
        editable = b;
        return;
    }

    private boolean isEditable() {
        return editable;
    }

    /**
     * getEditor
     * @return
     * @author Anthony C. Liberto
     */
    public XMLEditor getEditor() {
        return xeditor;
    }

    //cr_dtd	private void loadXML(String _s, boolean _editable) {
    //cr_dtd		xeditor.loadXML(_s,_editable,DEFAULT_DTD);
    private void loadXML(String _s, boolean _editable, String _dtd, boolean _spellCheck) { //cr_dtd
        xeditor.loadXML(_s, _editable, _dtd,_spellCheck); //cr_dtd
        setEditable(_editable);
        return;
    }

    /**
     * setAttribute
     * @param _att
     * @author Anthony C. Liberto
     */
    public void setAttribute(XMLAttribute _att) {
        att = _att;
        boolean bSpellCheck = false;																		//xmlSpellCheck
        /*this gets null ptr exception if _att=null
        EANMetaAttribute ma = _att.getMetaAttribute();														//xmlSpellCheck
        if (ma != null) {																					//xmlSpellCheck
			bSpellCheck = ma.isSpellCheckable();															//xmlSpellCheck
		}	*/																								//xmlSpellCheck
        if (att == null) {
            loadXML("", false, "default.dtd", bSpellCheck); //cr_dtd
            //cr_dtt			loadXML("", false);
        } else {
            EANMetaAttribute meta = att.getMetaAttribute(); //cr_dtd
            if (meta != null) { //cr_dtd
				bSpellCheck = meta.isSpellCheckable();
                loadXML((String) att.get(), att.isEditable(), meta.getXMLDTD(),bSpellCheck); //cr_dtd
            } else { //cr_dtd
                loadXML((String) att.get(), att.isEditable(),"default.dtd",bSpellCheck); //cr_dtd
            } //cr_dtd
            //cr_dtd			loadXML((String)att.get(),att.isEditable());
        }
    }

    /**
     * getXML
     * @return
     * @author Anthony C. Liberto
     */
    public String getXML() {
        String s = null;
        try {
            s = xeditor.getCompletedXML();
        } catch (IOException ioe) {
            eaccess().showError(this, "msg25007");
            ioe.printStackTrace();
        }
        return s;
    }

    /**
     * getDisplayString
     * @return
     * @author Anthony C. Liberto
     */
    public String getDisplayString() {
        return getXML();
    }

    /**
     * reset
     * @author Anthony C. Liberto
     */
    public void reset() {
        if (isShowing()) { //51055
            disposeDialog();
        } //51055
        if (ei != null && att != null) { //53337
            ei.refreshDisplay(att); //53337
            ei = null; //53337
        } //53337
        setAttribute(null);
        return;
    }

    /**
     * @see com.ibm.eannounce.editor.XMLEditorListener#editorClosing()
     * @author Anthony C. Liberto
     */
    public void editorClosing() {
    }

    private int getColumnIndex(String _s) {
        if (_s == null) {
            return 1;
        }
        return table.getColumnIndex(_s);
    }

    /**
     * @see com.ibm.eannounce.editor.XMLEditorListener#updateRequested()
     * @author Anthony C. Liberto
     */
    public boolean updateRequested() { //22429
        if (xml != null) { //22429
            return updateTableRequested(); //22429
        }
        return updateFormRequested(); //22429
    } //22429

    /**
     * updateFormRequested
     * @return
     * @author Anthony C. Liberto
     */
    public boolean updateFormRequested() {
        int row = -1;
        int col = -1;
        if (!isEditable()) { //52398
            return false; //52398
        } //52398
        if (att == null) {
            eaccess().showError(this, "msg25006");
            return false;
        }
        row = table.getRowIndex(getRowKey());
        col = getColumnIndex(getColumnKey());
        return updateRequested(table, row, col, getXML());
    }

    /**
     * updateRequested
     * @param _table
     * @param _row
     * @param _col
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    public boolean updateRequested(RowSelectableTable _table, int _row, int _col, String _s) {
        if (_s == null) {
            return false;
        }
        try {
            _table.put(_row, _col, _s);
            reset();
        } catch (EANBusinessRuleException _bre) {
            _bre.printStackTrace();
        }
        return true;
    }

    /**
     * setXMLInterface
     * @param _xml
     * @author Anthony C. Liberto
     */
    public void setXMLInterface(XMLInterface _xml) { //22429
        xml = _xml; //22429
        return; //22429
    } //22429

    /**
     * updateTableRequested
     * @return
     * @author Anthony C. Liberto
     */
    public boolean updateTableRequested() { //22429
        boolean b = xml.updateXML(getXML()); //22429
        reset(); //22429
        return b; //22429
    } //22429

    /*
    53510
    	public void processEvent(KeyEvent _ke) {
    		return;
    	}
    */
    /**
     * showMe
     * @author Anthony C. Liberto
     */
    public void showMe() {
        packDialog();
        return;
    }

    /**
     * getMenuBar
     * @author Anthony C. Liberto
     * @return JMenuBar
     */
    public JMenuBar getMenuBar() {
        return getParentMenuBar();
    }

    /**
     * createMenu
     * @author Anthony C. Liberto
     */
    protected void createMenu() {
    }
    /**
     * removeMenu
     * @author Anthony C. Liberto
     */
    protected void removeMenu() {
    }

    /**
     * setTitle
     *
     * @author Anthony C. Liberto
     * @param _title
     */
    public void setTitle(String _title) {
    }

    /**
     * getTitle
     * @author Anthony C. Liberto
     * @return String
     */
    public String getTitle() {
        if (att != null) { //51047
            return getString("xml.panel") + " -- " + att.getLongDescription(); //51047
        } //51047
        return getString("xml.panel");
    }

    /**
     * getPanelType
     * @author Anthony C. Liberto
     * @return String
     */
    public String getPanelType() {
        return TYPE_XMLPANEL;
    }
    /*
     53337
     */
    /**
     * setEditor
     * @param _ei
     * @author Anthony C. Liberto
     */
    public void setEditor(EditorInterface _ei) {
        ei = _ei;
        return;
    }
    /*
     53510
     */
    /**
     * processEvent
     * @param _eo
     * @author Anthony C. Liberto
     */
    public void processEvent(EventObject _eo) {
        System.out.println("processing the event on the xmlPanel");
        if (_eo != null && _eo instanceof KeyEvent) {
            //			xeditor.processKeyEvent((KeyEvent)_eo);
        }
    }
}
