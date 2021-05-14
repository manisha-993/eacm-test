/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: LongEditor.java,v $
 * Revision 1.3  2009/05/28 13:08:28  wendy
 * Performance cleanup
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
 * Revision 1.1.1.1  2005/09/09 20:37:57  tony
 * This is the initial load of OPICM
 *
 * Revision 1.6  2005/09/09 16:17:43  tony
 * removed println statement
 *
 * Revision 1.5  2005/09/08 17:59:03  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.4  2005/08/16 22:45:01  tony
 * MN_25042000
 *
 * Revision 1.3  2005/02/01 20:48:32  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 19:15:16  tony
 * JTest Format
 *
 * Revision 1.1.1.1  2004/02/10 16:59:41  tony
 * This is the initial load of OPICM
 *
 * Revision 1.23  2003/12/22 19:03:31  tony
 * 53452
 *
 * Revision 1.22  2003/11/18 20:54:04  tony
 * added formEditorInterface to simplify formEditor modification.
 *
 * Revision 1.21  2003/10/29 00:18:35  tony
 * removed System.out. statements.
 *
 * Revision 1.20  2003/08/25 14:36:20  tony
 * 51815
 *
 * Revision 1.19  2003/08/21 18:08:13  tony
 * 51869
 *
 * Revision 1.18  2003/08/21 15:57:34  tony
 * 51869
 *
 * Revision 1.17  2003/07/10 20:17:25  joan
 * 51317
 *
 * Revision 1.16  2003/07/10 15:44:23  tony
 * 51317
 *
 * Revision 1.15  2003/07/09 20:47:35  tony
 * 51422 -- blobEditor popup on double click
 *
 * Revision 1.14  2003/07/07 15:26:21  tony
 * updated logic per Wayne to only report 0 errors for
 * spell check when manually invoked.
 *
 * Revision 1.13  2003/06/27 20:08:45  tony
 * updated logic to prevent validation when in search mode
 * for certain rules.
 *
 * Revision 1.12  2003/05/21 15:54:55  tony
 * 24209
 *
 * Revision 1.11  2003/05/20 20:31:35  tony
 * updated cancel logic.
 *
 * Revision 1.10  2003/05/06 16:38:21  tony
 * acl_20030506 -- enhanced focus logic to prevent error
 * messages from firing when refocusing the component.
 *
 * Revision 1.9  2003/05/02 17:51:23  tony
 * 50497
 *
 * Revision 1.8  2003/05/01 22:41:36  tony
 * added static borders to address border rendering
 * issues on found.
 *
 * Revision 1.7  2003/04/30 21:40:47  tony
 * updated editor rendering on form.
 *
 * Revision 1.6  2003/04/30 20:32:06  joan
 * 04/29/2003
 *
 * Revision 1.5  2003/04/29 17:23:21  tony
 * cleaned-up longEditor to properly display to
 * enhance editing functionality.
 *
 * Revision 1.4  2003/04/24 19:40:09  tony
 * fixed null pointer presented by dwb.
 *
 * Revision 1.3  2003/04/17 23:13:26  tony
 * played around with editing Colors
 *
 * Revision 1.2  2003/03/21 20:54:31  tony
 * updated getBackground, getForeground, and getFont logic.
 * added logic so that each can be set for the component.
 *
 * Revision 1.1.1.1  2003/03/03 18:03:47  tony
 * This is the initial load of OPICM
 *
 * Revision 1.27  2002/12/05 21:23:34  tony
 * acl_20021205 -- longTextEditor updated to use a trueType font.
 *
 * Revision 1.26  2002/11/11 22:55:39  tony
 * adjusted classification on the toggle
 *
 * Revision 1.25  2002/11/11 16:59:16  tony
 * 23032
 *
 * Revision 1.24  2002/11/07 16:58:28  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.editor;
import com.elogin.*;
import com.ibm.eannounce.eobjects.*;
import com.ibm.eannounce.eforms.editform.FormEditorInterface; //acl_20030506
//acl_20030506 import com.ibm.eannounce.eForms.editor.Verifier.Verifier;
import java.awt.*;
import java.awt.event.KeyEvent;
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
public class LongEditor extends ETextArea implements EditorInterface {
	private static final long serialVersionUID = 1L;
	private MetaValidator validator = new MetaValidator();

    private boolean byPassValidation = false;
    private boolean found = false;

    /**
     * changeevent
     */
    transient protected ChangeEvent changeEvent = null;
    /**
     * listenerlist
     */
    protected EventListenerList listenerList = new EventListenerList();

    private String key = null;
    //acl_20030506	private RowSelectableTable table = null;
    private FormEditorInterface ef = null; //acl_20030506
    private boolean form = false;

    private Font defFont = new Font("Courier New", 0, 11); //acl_20021205
    private LongPopup popup = new LongPopup();
    private Component comp = null; //51317

    private boolean bReleaseFocus = true; //53452

    /**
     * longEditor
     * @author Anthony C. Liberto
     */
    public LongEditor() {
        super(0, 80);
        popup.setEditor(this);
        super.setFont(defFont); //acl_20021205
        validator.setEditor(this);
        form = false;
        setLineWrap(true); //21803
        setWrapStyleWord(true); //21803
    }

    /**
     * @see java.awt.Component#setFont(java.awt.Font)
     * @author Anthony C. Liberto
     */
    public void setFont(Font _fnt) {
    } //acl_20021205

    //acl_20030506	public longEditor(EANAttribute _att, String _key, Verifier _verify) {
    /**
     * longEditor
     * @param _att
     * @param _key
     * @param _ef
     * @author Anthony C. Liberto
     */
    public LongEditor(EANAttribute _att, String _key, FormEditorInterface _ef) { //acl_20030506
        this();
        ef = _ef;
        setAttribute(_att);
        setKey(_key);
        //acl_20030506		setInputVerifier(_verify);
        form = true;
    }

    /**
     * getBackground
     *
     * @return
     * @author Anthony C. Liberto
     */
    public Color getBackground() {
        if (isEditable() && validator != null) {
            return validator.getEditorColor(getText());
        }
        return super.getBackground();
    }

    /**
     * @see java.awt.Component#isOpaque()
     * @author Anthony C. Liberto
     */
    public boolean isOpaque() {
        if (isEditable() && form) {
            return true;
        }
        return super.isOpaque();
    }

    /**
     * @see java.awt.Component#setBounds(java.awt.Rectangle)
     * @author Anthony C. Liberto
     */
    public void setBounds(Rectangle _rect) {
        if (form) {
            super.setBounds(_rect);
        }
    }

    /**
     * @see javax.swing.JTextArea#createDefaultModel()
     * @author Anthony C. Liberto
     */
    public Document createDefaultModel() {
        return new MaxDoc();
    }

    /**
     * character validation
     */
    private class MaxDoc extends PlainDocument {
    	private static final long serialVersionUID = 1L;
    	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            if (str == null) {
                return;
            }
            if (byPassValidation) {
                super.insertString(offs, str, a);
            } else if (charValidation(offs, str)) {
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
        if (v instanceof LongTextAttribute) {
            LongTextAttribute att = (LongTextAttribute) v;
            setDisplay(att.get().toString());
        }
        return this;
    }

    /**
     * @see javax.swing.CellEditor#isCellEditable(java.util.EventObject)
     * @author Anthony C. Liberto
     */
    public boolean isCellEditable(EventObject e) {
        return true;
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
        if (popup.isShowing()) {
            //51317			popup.dispose();
            popup.disposeDialog(); //51317
        }
        editingStopped();
        return true;
    }

    private void spellCheck() {
        if (validator != null && validator.isSpellCheckable() && isEditable()) {
            eaccess().initSpellCheck();
            //51815			eaccess().spellCheck(null,this);
            eaccess().spellCheck(popup.getFrame(), null, this); //51815
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
        setDisplay(_att.get().toString());
        popup.setTitle(getMetaAttribute());
    }

    /**
     * getAttribute
     *
     * @return
     * @author Anthony C. Liberto
     */
    public EANAttribute getAttribute() {
        return validator.getAttribute();
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

    /*
     53452
    	public boolean canLeave() {
    		return validator.canLeave(getText());
    	}
    */
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
        setEditable(!_b);
        setBorder(getCurrentBorder());
    }

    /**
     * isDisplayOnly
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isDisplayOnly() {
        return !isEditable();
    }

    /**
     * refreshDisplay
     * @author Anthony C. Liberto
     * @param _att
     */
    public void refreshDisplay(EANAttribute _att) {
        setDisplay(_att.get().toString());
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

    /**
     * dereference
     *
     * @author Anthony C. Liberto
     */
    public void dereference() {
        //acl_20030506		table = null;
        ef = null; //acl_20030506
        comp = null; //51317
        removeAll();
        removeNotify();
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
        if (!find(_old, _bCase)) {
            return false;
        }
        newStr = Routines.replace(getText(), _old, _new);
        setDisplay(newStr);
        //acl_20030506		InputVerifier ver = getInputVerifier();
        //acl_20030506		if (ver instanceof Verifier)
        //acl_20030506			((Verifier)ver).commit(this);
        if (ef != null) { //acl_20030506
            try { //acl_20030506
                ef.commit(this); //acl_20030506
            } catch (Exception _ex) { //acl_20030506
                _ex.printStackTrace(); //acl_20030506
            } //acl_20030506
        } //acl_20030506
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
        if (popup.isShowing()) {
            //51317			popup.dispose();
            popup.disposeDialog(); //51317
        }
        return false;
    }

    private Border getCurrentBorder() {
        if (isFound()) {
            return EAccess.FOUND_BORDER;
        } else if (isDisplayOnly()) {
            return UIManager.getBorder("eannounce.emptyBorder");
        } else if (form) {
            return null;
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
     51317
     */
    /**
     * setComponent
     * @param _comp
     * @author Anthony C. Liberto
     */
    public void setComponent(Component _comp) {
        comp = _comp;
    }
    /*
     51869
     */
    /**
     * showDialog
     * @param _comp
     * @author Anthony C. Liberto
     */
    private void showDialog(Component _comp) {
        popup.setModalCursor(true);
        popup.show(_comp);
    }

    /**
     * showDialog
     * @param _comp
     * @param _modal
     * @author Anthony C. Liberto
     */
    public void showDialog(Component _comp, boolean _modal) {
        popup.setModalCursor(true);
        popup.show(_comp, _modal);
    }

    /**
     * prepareToEdit
     * @author Anthony C. Liberto
     * @param _comp
     * @param _eo
     */
    public void prepareToEdit(EventObject _eo, Component _comp) {
        showDialog(_comp);
        requestFocus();
    }

    /*
     53452
     */
    /**
     * releaseFocus
     * @param _b
     * @author Anthony C. Liberto
     */
    public void releaseFocus(boolean _b) {
        bReleaseFocus = _b;
    }

    /**
     * canLeave
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean canLeave() {
        if (!bReleaseFocus) {
            return false;
        }
        return validator.canLeave(getText());
    }

    /**
     * preloadKeyEvent
     * MN_25042000
     * @param _ke
     * @author Anthony C. Liberto
     */
    public void preloadKeyEvent(KeyEvent _ke) {
		if (_ke.getKeyCode() == KeyEvent.VK_V) {
			if (_ke.isControlDown()) {
				paste();
			}
		}
        processKeyEvent(_ke);
    }
}
