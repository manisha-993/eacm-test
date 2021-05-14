/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: XMLEditor.java,v $
 * Revision 1.3  2008/02/18 16:22:25  wendy
 * comment out msg
 *
 * Revision 1.2  2008/01/30 16:27:05  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:44:34  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:13  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:59  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:59:04  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/02/01 20:48:32  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 19:15:17  tony
 * JTest Format
 *
 * Revision 1.1.1.1  2004/02/10 16:59:42  tony
 * This is the initial load of OPICM
 *
 * Revision 1.11  2003/12/11 18:25:11  tony
 * 52910
 *
 * Revision 1.10  2003/12/08 20:53:59  tony
 * acl_20031208
 *
 * Revision 1.9  2003/12/08 20:45:20  tony
 * 53337
 *
 * Revision 1.8  2003/11/18 20:54:04  tony
 * added formEditorInterface to simplify formEditor modification.
 *
 * Revision 1.7  2003/08/21 15:57:35  tony
 * 51869
 *
 * Revision 1.6  2003/07/09 20:47:35  tony
 * 51422 -- blobEditor popup on double click
 *
 * Revision 1.5  2003/06/09 17:33:52  tony
 * 51248
 *
 * Revision 1.4  2003/05/30 21:09:23  tony
 * updated messaging logic to popup on top
 * of the currently active Component.
 *
 * Revision 1.3  2003/05/06 16:38:21  tony
 * acl_20030506 -- enhanced focus logic to prevent error
 * messages from firing when refocusing the component.
 *
 * Revision 1.2  2003/05/01 22:41:36  tony
 * added static borders to address border rendering
 * issues on found.
 *
 * Revision 1.1.1.1  2003/03/03 18:03:48  tony
 * This is the initial load of OPICM
 *
 * Revision 1.14  2002/11/11 22:55:39  tony
 * adjusted classification on the toggle
 *
 * Revision 1.13  2002/11/11 16:59:15  tony
 * 23032
 *
 * Revision 1.12  2002/11/07 16:58:28  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.editor;
import com.elogin.*;
import com.ibm.eannounce.dialogpanels.XMLPanel;
import com.ibm.eannounce.eforms.editform.FormEditorInterface; //acl_20030506
//acl_20030506 import com.ibm.eannounce.eForms.editor.Verifier.*;
import COM.ibm.eannounce.objects.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.text.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class XMLEditor extends JTextArea implements EAccessConstants, EditorInterface, KeyListener, MouseListener {
	private static final long serialVersionUID = 1L;
	/**
     * changeEvent
     */
    transient protected ChangeEvent changeEvent = null;
    private XMLPanel dialog = (XMLPanel) eaccess().getPanel(XML_PANEL);

    private boolean found = false;
    private boolean displayOnly = false;
    private boolean active = true;
    private boolean canUpdate = false;

    private EANAttribute att = null;
    //acl_20030506	private RowSelectableTable table = null;
    private FormEditorInterface ef = null; //acl_20030506
    private String key = null;

    //acl_20030506	public xmlEditor(EANAttribute _att, String _key, RowSelectableTable _table) {
    /**
     * xmlEditor
     * @param _att
     * @param _key
     * @param _ef
     * @author Anthony C. Liberto
     */
    public XMLEditor(EANAttribute _att, String _key, FormEditorInterface _ef) { //acl_20030506
        super();
        ef = _ef; //acl_20030506
        setAttribute(_att);
        setKey(_key);
        //acl_20030506		setTable(_table);
        refreshDisplay(_att); //acl_20031208
        setEditable(true);
        addKeyListener(this);
        addMouseListener(this);
        return;
    }

    /**
     * eaccess
     * @return
     * @author Anthony C. Liberto
     */
    public static EAccess eaccess() {
        return EAccess.eaccess();
    }

    /**
     * @see javax.swing.JTextArea#createDefaultModel()
     * @author Anthony C. Liberto
     */
    protected Document createDefaultModel() {
        return new MaxDoc();
    }

    private class MaxDoc extends PlainDocument {
    	private static final long serialVersionUID = 1L;
    	/**
         * @see javax.swing.text.Document#insertString(int, java.lang.String, javax.swing.text.AttributeSet)
         * @author Anthony C. Liberto
         */
        public void insertString(int _offs, String _s, AttributeSet _a) throws BadLocationException {
            if (canUpdate) {
                super.insertString(_offs, _s, _a);
            }
        }

        /**
         * @see javax.swing.text.Document#remove(int, int)
         * @author Anthony C. Liberto
         */
        public void remove(int _offs, int _len) throws BadLocationException {
            if (canUpdate) {
                super.remove(_offs, _len);
            }
        }
    }

    /**
     * dereference
     *
     * @author Anthony C. Liberto
     */
    public void dereference() {
        removeKeyListener(this);
        removeMouseListener(this);
        //acl_20030506		table = null;
        ef = null; //acl_20030506
        changeEvent = null;
        return;
    }

    /**
     * @see javax.swing.text.JTextComponent#isEditable()
     * @author Anthony C. Liberto
     */
    public boolean isEditable() {
        if (att != null) {
            return att.isEditable();
        }
        return false;
    }

    /**
     * setActive
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setActive(boolean _b) {
        active = _b;
    }

    /**
     * isActive
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isActive() {
        return active;
    }

    private void launchXMLEditor(KeyEvent _ke) {
        System.out.println("xmlEditor.launchXMLEditor()");
        dialog.setRowKey(key);
        dialog.setTable(ef.getTable()); //acl_20030506
        dialog.setAttribute(getXMLAttribute());
        dialog.setEditor(this); //53337
        dialog.processEvent(_ke);
        dialogShow(); //53337
        //53337		setDisplay((String)getAttribute().get());
        return;
    }

    /**
     * dialogShow
     * @author Anthony C. Liberto
     */
    public void dialogShow() { //22248
        eaccess().show(dialog, dialog, false);
        return; //22248
    } //22248

    /**
     * getXML
     * @return
     * @author Anthony C. Liberto
     */
    public String getXML() {
        return dialog.getXML();
    }

    /**
     * getXMLAttribute
     * @return
     * @author Anthony C. Liberto
     */
    public XMLAttribute getXMLAttribute() {
        return (XMLAttribute) att;
    }

    /**
     * Listener
     *
     * @param me
     */
    public void mouseClicked(MouseEvent me) {
        System.out.println("xmlEditor.mouseClicked()");
        if (me.getClickCount() == 2 && isActive()) {
            launchXMLEditor(null);
        }
        return;
    }
    /**
     * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
     * @author Anthony C. Liberto
     */
    public void keyTyped(KeyEvent ke) {
 //       System.out.println("xmlEditor.keyTyped()");
        char c = ke.getKeyChar();
        if ((Character.isLetterOrDigit(c) || Character.isSpaceChar(c)) && isActive()) {
            launchXMLEditor(ke);
        }
        return;
    }
    /**
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseEntered(MouseEvent me) {
    }
    /**
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseExited(MouseEvent me) {
    }
    /**
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mousePressed(MouseEvent me) {
    }
    /**
     * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseReleased(MouseEvent me) {
    }
    /**
     * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
     * @author Anthony C. Liberto
     */
    public void keyPressed(KeyEvent ke) {
        if (ke.getKeyCode() == KeyEvent.VK_TAB) {
            FocusManager fm = FocusManager.getCurrentManager();
            if (ke.isShiftDown()) {
                fm.focusPreviousComponent(this);
            } else {
                fm.focusNextComponent(this);
            }
            ke.consume();
            return;
        }
    }
    /**
     * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
     * @author Anthony C. Liberto
     */
    public void keyReleased(KeyEvent ke) {
    }

    /*
     *editorInterface
     */

    /**
     * @see javax.swing.CellEditor#addCellEditorListener(javax.swing.event.CellEditorListener)
     * @author Anthony C. Liberto
     */
    public void addCellEditorListener(CellEditorListener c) {
        listenerList.add(CellEditorListener.class, c);
    }

    /**
     * @see javax.swing.CellEditor#cancelCellEditing()
     * @author Anthony C. Liberto
     */
    public void cancelCellEditing() {
        editingCanceled();
    }

    /**
     * @see javax.swing.CellEditor#getCellEditorValue()
     * @author Anthony C. Liberto
     */
    public Object getCellEditorValue() {
        return getText();
    }

    /**
     * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
     * @author Anthony C. Liberto
     */
    public Component getTableCellEditorComponent(JTable t, Object v, boolean sel, int r, int c) {
        return this;
    }

    /**
     * @see javax.swing.CellEditor#isCellEditable(java.util.EventObject)
     * @author Anthony C. Liberto
     */
    public boolean isCellEditable(EventObject e) {
        return isEditable();
    }

    /**
     * @see javax.swing.CellEditor#removeCellEditorListener(javax.swing.event.CellEditorListener)
     * @author Anthony C. Liberto
     */
    public void removeCellEditorListener(CellEditorListener c) {
        listenerList.remove(CellEditorListener.class, c);
    }

    /**
     * @see javax.swing.CellEditor#shouldSelectCell(java.util.EventObject)
     * @author Anthony C. Liberto
     */
    public boolean shouldSelectCell(EventObject e) {
        return true;
    }

    /**
     * @see javax.swing.CellEditor#stopCellEditing()
     * @author Anthony C. Liberto
     */
    public boolean stopCellEditing() {
        if (!canLeave()) {
            return false;
        }
        editingStopped();
        return true;
    }

    /**
     * editingStopped
     * @author Anthony C. Liberto
     */
    public void editingStopped() {
        Object[] listen = listenerList.getListenerList();
        for (int i = listen.length - 2; i >= 0; i -= 2) {
            if (listen[i] == CellEditorListener.class) {
                if (changeEvent == null) {
                    changeEvent = new ChangeEvent(this);
                }
                ((CellEditorListener) listen[i + 1]).editingStopped(changeEvent);
            }
        }
    }

    /**
     * editingCanceled
     * @author Anthony C. Liberto
     */
    protected void editingCanceled() {
        Object[] listen = listenerList.getListenerList();
        for (int i = listen.length - 2; i >= 0; i -= 2) {
            if (listen[i] == CellEditorListener.class) {
                if (changeEvent == null) {
                    changeEvent = new ChangeEvent(this);
                }
                ((CellEditorListener) listen[i + 1]).editingCanceled(changeEvent);
            }
        }
    }

    /**
     * setAttribute
     * @author Anthony C. Liberto
     * @param _att
     */
    public void setAttribute(EANAttribute _att) {
        att = _att;
        return;
    }

    /**
     * getAttribute
     *
     * @return
     * @author Anthony C. Liberto
     */
    public EANAttribute getAttribute() {
        return att;
    }

    /**
     * getMetaAttribute
     *
     * @return
     * @author Anthony C. Liberto
     */
    public EANMetaAttribute getMetaAttribute() { //22098
        if (att != null) { //22098
            return att.getMetaAttribute();
        } //22098
        return null; //22098
    } //22098

    /**
     * getKey
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getKey() {
        return key;
    }

    /**
     * setKey
     * @author Anthony C. Liberto
     * @param _key
     */
    public void setKey(String _key) {
        key = new String(_key);
    }

    /**
     * setDisplay
     * @author Anthony C. Liberto
     * @param _s
     */
    public void setDisplay(String _s) {
        canUpdate = true;
        setText(_s);
        canUpdate = false;
        return;
    }

    /**
     * canLeave
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean canLeave() {
        return true;
    }

    /**
     * hasChanged
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean hasChanged() { //acl_20021023
        return true; //acl_20021023
    } //acl_20021023

    /**
     * setDisplayOnly
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setDisplayOnly(boolean _b) {
        //System.out.println("xmlEditor.setDisplayOnly(" + _b + ")");
        if (!getAttribute().isEditable()) {
            _b = true;
        }
        displayOnly = _b;
        if (displayOnly) {
            setActive(false);
        } else {
            setActive(true);
        }
        setFocusable(!_b);
        setBorder(getCurrentBorder());
        return;
    }

    /**
     * isDisplayOnly
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isDisplayOnly() {
        return displayOnly;
    }

    //51422	public void prepareToEdit() {
    //51869	public void prepareToEdit(EventObject _eo) {	//51422
    /**
     * prepareToEdit
     * @author Anthony C. Liberto
     * @param _c
     * @param _eo
     */
    public void prepareToEdit(EventObject _eo, Component _c) { //51869
        requestFocus();
        return;
    }

    /**
     * refreshDisplay
     * @author Anthony C. Liberto
     * @param _att
     */
    public void refreshDisplay(EANAttribute _att) {
        setAttribute(_att);
        if (_att != null) {
            setDisplay(_att.toString());
        }
        return;
    }

    /**
     * isRequired
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isRequired() {
        return getAttribute().isRequired();
    }

    /**
     * cancel
     *
     * @author Anthony C. Liberto
     */
    public void cancel() {
        refreshDisplay(getAttribute());
        return;
    }

    /**
     * deactivate
     *
     * @author Anthony C. Liberto
     */
    public void deactivate() {
    }
    /**
     * setEffectivity
     * @author Anthony C. Liberto
     * @param _from
     * @param _to
     */
    public void setEffectivity(String _from, String _to) {
    }

    /*
     acl_20030506
    	public void setTable(RowSelectableTable _table) {
    		table = _table;
    	}

    	public RowSelectableTable getTable() {
    		return table;
    	}
    */
    /*
     * find replace
     */
    /**
     * find
     * @author Anthony C. Liberto
     * @return boolean
     * @param _bCase
     * @param _strFind
     */
    public boolean find(String _strFind, boolean _bCase) {
        boolean bFound = false;
        if (!isShowing()) {
            return false;
        }
        //23032		found = routines.find(getText(),_strFind,_bCase);
        //23032		if (found) {
        bFound = Routines.find(getText(), _strFind, _bCase); //23032
        if (bFound) { //23032
            found = true; //23032
            setBorder(getCurrentBorder());
            requestFocus();
        }
        return found;
    }

    /**
     * replace
     * @author Anthony C. Liberto
     * @return boolean
     * @param _bCase
     * @param _new
     * @param _old
     */
    public boolean replace(String _old, String _new, boolean _bCase) {
        String newStr = null;
        RowSelectableTable table = null;
        if (!find(_old, _bCase)) {
            return false;
        }

        newStr = Routines.replace(getText(), _old, _new);
        setDisplay(newStr);
        table = ef.getTable(); //acl_20030506
        if (dialog.updateRequested(table, table.getRowIndex(key), 1, newStr)) {
            return true;
        }
        return false;
    }

    /**
     * isFound
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isFound() {
        return found;
    }

    /**
     * resetFound
     *
     * @author Anthony C. Liberto
     */
    public void resetFound() {
        if (!found) {
            return;
        }
        found = false;
        setBorder(getCurrentBorder());
        return;
    }

    /**
     * removeEditor
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean removeEditor() {
        return true;
    }

    private Border getCurrentBorder() {
        if (isFound()) {
            return EAccess.FOUND_BORDER;
        } else if (isDisplayOnly()) {
            return UIManager.getBorder("eannounce.emptyBorder");
        }
        return null;
    }
    /*
     * Information
     */
    /**
     * getInformation
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getInformation() {
        return Routines.getInformation(getAttribute());
    }

    /**
     * isReplaceable
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isReplaceable() { //22632
        return false; //51248
        //51248		return true;								//22632
    } //22632

    /*
    acl_20030506
    */
    /**
     * getEditForm
     *
     * @return
     * @author Anthony C. Liberto
     */
    public FormEditorInterface getEditForm() {
        return ef;
    }

    /**
     * canReceiveFocus
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean canReceiveFocus() {
        FormEditorInterface tmpEf = getEditForm();
        if (tmpEf != null) {
            return tmpEf.verifyNewFocus(this);
        }
        return true;
    }

    /**
     * @see java.awt.Component#requestFocus()
     * @author Anthony C. Liberto
     */
    public void requestFocus() {
        if (canReceiveFocus()) {
            super.requestFocus();
        }
        return;
    }

    /**
     * @see java.awt.Component#requestFocus(boolean)
     * @author Anthony C. Liberto
     */
    public boolean requestFocus(boolean _temp) {
        if (canReceiveFocus()) {
            return super.requestFocus(_temp);
        }
        return false;
    }

    /**
     * @see javax.swing.JComponent#grabFocus()
     * @author Anthony C. Liberto
     */
    public void grabFocus() {
        if (canReceiveFocus()) {
            super.grabFocus();
        }
        return;
    }

    /**
     * equals
     * @author Anthony C. Liberto
     * @return boolean
     * @param _c
     */
    public boolean equals(Component _c) {
        if (_c != null) {
            if (_c instanceof EditorInterface) {
                return (this == (EditorInterface) _c);
            }
        }
        return true;
    }
}
