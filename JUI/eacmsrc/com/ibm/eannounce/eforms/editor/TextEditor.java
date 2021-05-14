/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: TextEditor.java,v $
 * Revision 1.3  2009/05/28 13:08:28  wendy
 * Performance cleanup
 *
 * Revision 1.2  2008/01/30 16:27:04  wendy
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
 * Revision 1.7  2005/09/08 17:59:04  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.6  2005/02/03 21:26:12  tony
 * JTest Format Third Pass
 *
 * Revision 1.5  2005/02/01 20:48:32  tony
 * JTest Second Pass
 *
 * Revision 1.4  2005/01/27 19:15:17  tony
 * JTest Format
 *
 * Revision 1.3  2004/08/25 16:24:04  tony
 * fixed cursor positioning logic.
 *
 * Revision 1.2  2004/07/12 21:17:40  tony
 * fixed null pointer for 7/12/04
 *
 * Revision 1.1.1.1  2004/02/10 16:59:42  tony
 * This is the initial load of OPICM
 *
 * Revision 1.19  2004/01/20 21:30:44  tony
 * 53569
 *
 * Revision 1.18  2003/11/18 20:54:04  tony
 * added formEditorInterface to simplify formEditor modification.
 *
 * Revision 1.17  2003/09/26 17:52:14  tony
 * 52430
 *
 * Revision 1.16  2003/09/24 21:48:09  tony
 * 52389
 *
 * Revision 1.15  2003/09/11 14:53:31  tony
 * 52051
 *
 * Revision 1.14  2003/09/04 17:10:13  tony
 * 52051
 *
 * Revision 1.13  2003/08/21 15:57:35  tony
 * 51869
 *
 * Revision 1.12  2003/07/09 20:47:35  tony
 * 51422 -- blobEditor popup on double click
 *
 * Revision 1.11  2003/07/07 15:26:21  tony
 * updated logic per Wayne to only report 0 errors for
 * spell check when manually invoked.
 *
 * Revision 1.10  2003/06/27 20:08:45  tony
 * updated logic to prevent validation when in search mode
 * for certain rules.
 *
 * Revision 1.9  2003/05/21 15:54:55  tony
 * 24209
 *
 * Revision 1.8  2003/05/06 18:56:33  tony
 * 50546
 *
 * Revision 1.7  2003/05/06 16:38:21  tony
 * acl_20030506 -- enhanced focus logic to prevent error
 * messages from firing when refocusing the component.
 *
 * Revision 1.6  2003/05/01 22:41:36  tony
 * added static borders to address border rendering
 * issues on found.
 *
 * Revision 1.5  2003/04/30 21:40:47  tony
 * updated editor rendering on form.
 *
 * Revision 1.4  2003/04/24 19:40:09  tony
 * fixed null pointer presented by dwb.
 *
 * Revision 1.3  2003/04/17 23:13:25  tony
 * played around with editing Colors
 *
 * Revision 1.2  2003/03/21 20:54:32  tony
 * updated getBackground, getForeground, and getFont logic.
 * added logic so that each can be set for the component.
 *
 * Revision 1.1.1.1  2003/03/03 18:03:48  tony
 * This is the initial load of OPICM
 *
 * Revision 1.21  2002/11/14 17:27:04  tony
 * 23157
 *
 * Revision 1.20  2002/11/11 16:59:15  tony
 * 23032
 *
 * Revision 1.19  2002/11/07 16:58:28  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.editor;
import com.elogin.*;
import com.ibm.eannounce.eobjects.*;
import com.ibm.eannounce.eforms.editform.FormEditorInterface; //acl_20030506
//acl_20030506 import com.ibm.eannounce.eForms.editor.Verifier.Verifier;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.*;
import javax.swing.text.*;
import COM.ibm.eannounce.objects.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class TextEditor extends ETextField implements EditorInterface {
	private static final long serialVersionUID = 1L;
	private MetaValidator validator = new MetaValidator();
    private boolean byPassValidation = false;
    private boolean displayOnly = false;
    private boolean found = false;

    /**
     * changeEvent
     */
    transient protected ChangeEvent changeEvent = null;

    private String key = null;
    //acl_20030506	private RowSelectableTable table = null;
    private FormEditorInterface ef = null; //acl_20030506

    /**
     * textEditor
     * @author Anthony C. Liberto
     */
    public TextEditor() {
        setUseDefined(true);
        validator.setEditor(this);
        setAutoscrolls(true);
    }

    //acl_20030506	public textEditor(EANAttribute _att, String _key, Verifier _verify) {
    /**
     * textEditor
     * @param _att
     * @param _key
     * @param _ef
     * @author Anthony C. Liberto
     */
    public TextEditor(EANAttribute _att, String _key, FormEditorInterface _ef) { //acl_20030506
        this();
        ef = _ef;
        refreshDisplay(_att);
        setKey(_key);
        //acl_20030506		setInputVerifier(_verify);
    }

    /**
     * @see java.awt.Component#getPreferredSize()
     * @author Anthony C. Liberto
     */
    public Dimension getPreferredSize() {
        Dimension d = super.getPreferredSize();
        int minWidth = 4 * getColumnWidth();
        if (d.width < minWidth) {
            d.width = minWidth;
        }
        return d;
    }

    /**
     * @see javax.swing.JTextField#getColumnWidth()
     * @author Anthony C. Liberto
     */
    protected int getColumnWidth() {
        FontMetrics metrics = getFontMetrics(getFont());
        return metrics.charWidth('W');
    }

    /**
     * dereference
     *
     * @author Anthony C. Liberto
     */
    public void dereference() {
        //acl_20030506		table = null;
        ef = null; //acl_20030506
        changeEvent = null;
        validator.dereference();
        validator = null;
        removeAll();
        removeNotify();
    }

    /**
     * getBackground
     *
     * @return
     * @author Anthony C. Liberto
     */
    public Color getBackground() {
        if (!isDisplayOnly() && validator != null) {
            return validator.getEditorColor(getText());
        }
        return super.getBackground();
    }

    /**
     * character validation
     *
     * @return Document
     */
    protected Document createDefaultModel() {
        return new MaxDoc();
    }

    private class MaxDoc extends PlainDocument {
    	private static final long serialVersionUID = 1L;
    	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            if (str == null) {
                return;
            }
            if (byPassValidation) {
                super.insertString(offs, str, a);
            } else if (isEditable() && charValidation(offs, str)) {
                super.insertString(offs, str, a);
            }
            repaint();
        }

        public void remove(int offs, int len) throws BadLocationException {
            if (!isEditable() && !byPassValidation) {
                return;
            }
            super.remove(offs, len);
            checkLength();
            repaint();
        }
    }

    /**
     * charValidation
     * @param _offs
     * @param _str
     * @return
     * @author Anthony C. Liberto
     */
    private boolean charValidation(int _offs, String _str) {
        return validator.validate(_str, getText() + _str, _offs);
    }

    /**
     * checkLength
     * @author Anthony C. Liberto
     */
    private void checkLength() {
        String str = getText();
        validator.checkLength(str, str.length(), true);
    }

    /**
     * replaceValidation
     * @param _str
     * @return
     * @author Anthony C. Liberto
     */
    private boolean replaceValidation(String _str) { //23157
        return validator.validate(_str, _str, 0); //23157
    } //23157

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
        if (v instanceof TextAttribute) {
            TextAttribute att = (TextAttribute) v;
            setDisplay(att.get().toString());
        }
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
        spellCheck();
        editingStopped();
        return true;
    }

    private void spellCheck() {
        if (validator != null && validator.isSpellCheckable() && isEditable()) {
            eaccess().initSpellCheck();
            eaccess().spellCheck(null, this);
            eaccess().completeSpellCheck(false); //24209
        }
    }

    /**
     * editingStopped
     * @author Anthony C. Liberto
     */
    private void editingStopped() {
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
    private void editingCanceled() {
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
        validator.setAttribute(_att);
    }

    /**
     * getAttribute
     *
     * @return
     * @author Anthony C. Liberto
     */
    public EANAttribute getAttribute() {
        if (validator != null) { //52389
            return validator.getAttribute();
        } //52389
        return null; //52389
    }

    /**
     * getMetaAttribute
     *
     * @return
     * @author Anthony C. Liberto
     */
    public EANMetaAttribute getMetaAttribute() { //22098
        EANAttribute att = getAttribute(); //22098
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
        byPassValidation = true;
        setText(_s);
        byPassValidation = false;
    }

    /**
     * canLeave
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean canLeave() {
        if (hasChanged()) { //acl_20021023
            return validator.canLeave(getText());
        } //acl_20021023
        return true; //acl_20021023
        //acl_20021023		return validator.canLeave(getText());
    }

    /**
     * hasChanged
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean hasChanged() { //acl_20021023
        EANAttribute att = getAttribute(); //acl_20021023
        if (att != null) { //acl_20021023
            return !att.toString().equals(getText()); //acl_20021023
        } //acl_20021023
        return false; //acl_20021023
    } //acl_20021023

    /**
     * setDisplayOnly
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setDisplayOnly(boolean _b) {
        if (!getAttribute().isEditable()) {
            _b = true;
        }
        displayOnly = _b;
        if (displayOnly) {
            setEditable(false);
        } else {
            setEditable(true);
        }
        //50546		setFocusable(!_b);
        setBorder(getCurrentBorder());
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
        } else {
            setDisplay("");
        }
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
        String str = null;
        if (!find(_old, _bCase)) {
            return false;
        }
        str = Routines.replace(getText(), _old, _new); //23157
        if (replaceValidation(str)) { //23157
            setDisplay(str); //23157
        } //23157
        return true;
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
        return UIManager.getBorder("TextField.border");
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
        return true; //22632
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

    /*
     dwb_20030527
     */
    /**
     * setSearch
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setSearch(boolean _b) {
        validator.setSearch(_b);
    }

    /*
     52051
     */
    /**
     * @see javax.swing.text.JTextComponent#paste()
     * @author Anthony C. Liberto
     */
    public void paste() {
        if (isEditable() && isEnabled()) {
            String clipString = eaccess().getClipboardString();
            int pos = -1;
            if (clipString != null) {
                Document doc = getDocument();
                if (doc != null) {
                    try {
                        replaceSelection(""); //52430
                        //53569						int pos = getCaretPosition();
                        pos = Math.min(getCaretPosition(), doc.getLength()); //53569
                        doc.insertString(pos, clipString, null);
                        setCaretPosition((clipString.length() + pos));
                    } catch (BadLocationException _ble) {
                        _ble.printStackTrace();
                    } catch (IllegalArgumentException _iae) {
                        _iae.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * caretToEnd
     * @author Anthony C. Liberto
     */
    public void caretToEnd() {
        Document doc = getDocument();
        if (doc != null) {
            setCaretPosition(doc.getLength());
        }
    }

    /*
     fix null pointer

     July 12, 2004
     */
    /**
     * @see javax.swing.text.JTextComponent#setCaretPosition(int)
     * @author Anthony C. Liberto
     */
    public void setCaretPosition(int _pos) {
        /*
        was one position off.
        		int iMaxLen = Math.max(0,getDocument().getLength() -1);
        */
        int iMaxLen = Math.max(0, getDocument().getLength());
        if (_pos < 0) {
            super.setCaretPosition(0);
        } else if (_pos > iMaxLen) {
            super.setCaretPosition(iMaxLen);
        } else {
            super.setCaretPosition(_pos);
        }
    }
}
