/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: DateTimeEditor.java,v $
 * Revision 1.4  2009/06/12 16:06:38  wendy
 * BH SR-14 date warnings
 *
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
 * Revision 1.1.1.1  2005/09/09 20:37:56  tony
 * This is the initial load of OPICM
 *
 * Revision 1.7  2005/09/08 17:59:03  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.6  2005/02/10 19:00:45  tony
 * JTest Formatting
 *
 * Revision 1.5  2005/02/09 19:29:50  tony
 * JTest After Scout
 *
 * Revision 1.4  2005/02/09 18:55:23  tony
 * Scout Accessibility
 *
 * Revision 1.3  2005/02/01 20:48:32  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 19:15:16  tony
 * JTest Format
 *
 * Revision 1.1.1.1  2004/02/10 16:59:40  tony
 * This is the initial load of OPICM
 *
 * Revision 1.29  2003/12/11 19:44:57  tony
 * 53382
 *
 * Revision 1.28  2003/12/04 17:13:40  tony
 * 52942
 *
 * Revision 1.27  2003/12/02 21:56:51  tony
 * 52924
 *
 * Revision 1.26  2003/11/18 20:54:03  tony
 * added formEditorInterface to simplify formEditor modification.
 *
 * Revision 1.25  2003/10/20 23:23:36  tony
 * acl_20031020
 * updated logic to prevent datepanel misinterpretation
 * of popup location.
 *
 * Revision 1.24  2003/10/17 15:58:45  tony
 * 52574
 *
 * Revision 1.23  2003/10/15 19:22:53  tony
 * 52575
 *
 * Revision 1.22  2003/10/15 18:10:04  tony
 * 52488
 *
 * Revision 1.21  2003/10/14 15:37:13  tony
 * 52546
 *
 * Revision 1.20  2003/10/13 17:15:52  tony
 * kc_20031012 -- updated date editor on form so that
 * it behaves similar to the editor on the table.
 *
 * Revision 1.19  2003/08/21 15:57:34  tony
 * 51869
 *
 * Revision 1.18  2003/08/14 18:04:27  tony
 * formatted source
 *
 * Revision 1.17  2003/07/23 20:41:57  tony
 * added and enhanced restore logic
 *
 * Revision 1.16  2003/07/22 15:47:19  tony
 * updated date editor capability to improve performance and
 * functionality.
 *
 * Revision 1.15  2003/07/09 20:47:35  tony
 * 51422 -- blobEditor popup on double click
 *
 * Revision 1.14  2003/06/27 20:08:45  tony
 * updated logic to prevent validation when in search mode
 * for certain rules.
 *
 * Revision 1.13  2003/05/15 19:33:05  tony
 * KC_20030515 added behavior fixes for KC
 *
 * Revision 1.12  2003/05/06 18:56:33  tony
 * 50546
 *
 * Revision 1.11  2003/05/06 16:38:21  tony
 * acl_20030506 -- enhanced focus logic to prevent error
 * messages from firing when refocusing the component.
 *
 * Revision 1.10  2003/05/01 22:41:36  tony
 * added static borders to address border rendering
 * issues on found.
 *
 * Revision 1.9  2003/04/30 21:40:47  tony
 * updated editor rendering on form.
 *
 * Revision 1.8  2003/04/30 00:11:38  tony
 * updated form editing capability per KC.
 *
 * Revision 1.7  2003/04/29 17:21:25  tony
 * adjusted caret Position on request focus
 *
 * Revision 1.6  2003/04/18 20:09:36  tony
 * 50409
 *
 * Revision 1.5  2003/04/17 23:13:26  tony
 * played around with editing Colors
 *
 * Revision 1.4  2003/04/15 17:15:57  tony
 * 50392
 *
 * Revision 1.3  2003/03/25 17:58:11  tony
 * dateEditor modification
 *
 * Revision 1.2  2003/03/21 20:54:31  tony
 * updated getBackground, getForeground, and getFont logic.
 * added logic so that each can be set for the component.
 *
 * Revision 1.1.1.1  2003/03/03 18:03:47  tony
 * This is the initial load of OPICM
 *
 * Revision 1.23  2002/11/11 22:55:39  tony
 * adjusted classification on the toggle
 *
 * Revision 1.22  2002/11/11 16:59:15  tony
 * 23032
 *
 * Revision 1.21  2002/11/07 16:58:27  tony
 * added/adjusted copyright statement
 *
 */
/*
 * Once get to jdk1.4 change to extend JSpinner
 */
package com.ibm.eannounce.eforms.editor;
import com.elogin.*;
import com.ibm.eannounce.eobjects.ETextField;
import com.ibm.eannounce.eforms.editform.FormEditorInterface; //acl_20030506
//acl_20030506 import com.ibm.eannounce.eForms.editor.Verifier.Verifier;
import COM.ibm.eannounce.objects.*;
import java.awt.*;
import java.awt.event.*;
import java.util.EventObject;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.accessibility.AccessibleContext;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class DateTimeEditor extends ETextField implements EditorInterface, ActionListener {
	private static final long serialVersionUID = 1L;
	/**
     * DEFAULT
     */
	//private static final int DEFAULT = -1;
    /**
     * DATE_VALIDATOR
     */
    public static final int DATE_VALIDATOR = 0;
    /**
     * FUTURE_DATE_VALIDATOR
     */
    //private static final int FUTURE_DATE_VALIDATOR = 1;
    /**
     * PAST_DATE_VALIDATOR
     */
    //private static final int PAST_DATE_VALIDATOR = 2;
    /**
     * TIME_VALIDATOR
     */
    public static final int TIME_VALIDATOR = 3;

    /**
     * separator
     */
    private static final String[] SEPARATOR = { "-", "-", "-", ":" }; //52575
    /**
     * sepChar
     */
    private static final char[] SEPCHAR = { '-', '-', '-', ':' }; //52575

    private int type = 0;

    private boolean found = false;
    private MetaValidator validator = new MetaValidator();
    private DatePopup popup = null;
    private boolean bypass = false;
    private boolean displayOnly = false;

    /**
     * changeEvent
     */
    transient protected ChangeEvent changeEvent = null;

    private final String[] defString = { "YYYY-MM-DD", "YYYY-MM-DD", "YYYY-MM-DD", "HH:MM" };

    private EButton button = new EButton(eaccess().getImageIcon("cal.gif")) {
    	private static final long serialVersionUID = 1L;
    	public Cursor getCursor() {
                //acl_20031015			return eaccess().getCursor();
            return getParentCursor(); //acl_20031015
        }
    };
    private String key = null;
    private FormEditorInterface ef = null;
    private Dimension dButton = new Dimension(16, 16);
    private DateCaret dCaret = new DateCaret(); //52574

    /**
     * dateTimeEditor
     * @param _type
     * @author Anthony C. Liberto
     */
    public DateTimeEditor(int _type) {
        setUseDefined(true);
        setType(_type);
        setDisplay(null);
        init();
        validator.setEditor(this);
        popup = new DatePopup(this);
        setOpaque(false);
        //		setBorder(getCurrentBorder());
        setCaret(dCaret); //52574
        button.setFocusable(false);
        initAccessibility("accessible.datetimeEditor");
    }

    /**
     * dateTimeEditor
     * @param _att
     * @param _key
     * @param _ef
     * @param _type
     * @author Anthony C. Liberto
     */
    public DateTimeEditor(EANAttribute _att, String _key, FormEditorInterface _ef, int _type) {
        this(_type);
        ef = _ef;
        refreshDisplay(_att);
        setKey(_key);
        setOpaque(false);
        button.setFocusable(false);
        initAccessibility("accessible.datetimeEditor");
    }

    private Cursor getParentCursor() { //acl_20031015
        return getCursor(); //acl_20031015
    } //acl_20031015

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
     * setValidatorType
     * @param _i
     * @author Anthony C. Liberto
     */
    public void setValidatorType(int _i) {
        validator.setType(_i);
    }

    /**
     * getValidatorType
     * @return
     * @author Anthony C. Liberto
     */
    public int getValidatorType() {
        return validator.getType();
    }

    private void init() {
        if (isDateValidator()) {
            if (!EAccess.isAccessible()) {
	            add(button);
	            button.addActionListener(this);
	            button.setOpaque(false);
			}
            //			button.setBorder(null);
        }
    }

    /**
     * @see java.awt.Component#setBounds(java.awt.Rectangle)
     * @author Anthony C. Liberto
     */
    public void setBounds(Rectangle _rec) {
        if (isDateValidator()) {
            button.setBounds(_rec.width - dButton.width, (_rec.height - dButton.height) / 2, dButton.width, dButton.height);
        }
        super.setBounds(_rec);
    }

    /**
     * resetCalendar
     * @author Anthony C. Liberto
     */
    public void resetCalendar() {
        popup.resetCalendar();
    }

    /**
     * setType
     * @param _i
     * @author Anthony C. Liberto
     */
    private void setType(int _i) {
        if (_i == DATE_VALIDATOR) {
            type = DATE_VALIDATOR;
        } else if (_i == TIME_VALIDATOR) {
            type = TIME_VALIDATOR;
        }
    }

    /**
     * isDateValidator
     * @return
     * @author Anthony C. Liberto
     */
    private boolean isDateValidator() {
        return type == DATE_VALIDATOR;
    }

    /**
     * isTimeValidtor
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean isTimeValidtor() {
        return type == TIME_VALIDATOR;
    }*/

    /**
     * @see javax.swing.JTextField#createDefaultModel()
     * @author Anthony C. Liberto
     */
    protected Document createDefaultModel() {
        return new MaxDoc();
    }

    /**
     * getButton
     * @return
     * @author Anthony C. Liberto
     */
    public JButton getButton() {
        return button;
    }

    private boolean checkOffset(int _i) {
        if (type == DATE_VALIDATOR) {
            if (_i == 4) {
                return true;
            } else if (_i == 7) {
                return true;
            }
        } else if (type == TIME_VALIDATOR) {
            if (_i == 2) {
                return true;
            }
        }
        return false;
    }

    /**
     * passMouseEvent
     * @param _me
     * @author Anthony C. Liberto
     * /
    public void passMouseEvent(MouseEvent _me) { //22191
        processMouseEvent(_me); //22191
    } //22191
    */

    /**
     * displayPopup
     * @author Anthony C. Liberto
     * /
    public void displayPopup() {
        if (!bypass && !popup.isShowing()) {
            showPopup();
        }
    }*/

    /**
     * showPopup
     * @author Anthony C. Liberto
     */
    private void showPopup() {
        getLocationOnScreen(); //22290
        popup.show(this, 0, computeY(0));
    }

    private int computeY(int _y) { //22290
        Dimension pSize = popup.getPreferredSize(); //22290
        int popupHeight = pSize.height; //22290
        //52488		Dimension oSize = eaccess().getLogin().getSize();			//22290
        Window win = eaccess().getParentWindow(this); //52488
        if (win != null) { //52488
            Dimension oSize = win.getSize(); //52488
            if ((popupHeight + _y) > oSize.height) { //22290
                return Math.max(0, (_y - popupHeight)); //22290
            } //22290
        } //52488
        return _y + getHeight() - 1; //22290
    } //22290

    /**
     * hidePopup
     * @author Anthony C. Liberto
     */
    public void hidePopup() {
        popup.setVisible(false);
    }

    /**
     * setSelectedDate - this is only called by the calendar control
     * @param _date
     * @author Anthony C. Liberto
     */
    protected void setSelectedDate(String _date) {
        //setDisplay(_date);
        setText(_date); //BH SR-14 use document checks, dont bypass 
        
        super.requestFocus();
        if (ef != null) { 
            try { 
                ef.commit(this); 
            } catch (Exception _x) { 
                _x.printStackTrace(); 
            } 
        } else { 
        	//BH SR-14 checks already done because bypass is off
            hidePopup();//BH SR-14
            editingStopped();//BH SR-14
            //BH SR-14 stopCellEditing();
        } 
    }

    /**
     * getStrippedText
     * @return
     * @author Anthony C. Liberto
     */
    public String getStrippedText() {
        return getStrippedText(getText().toCharArray());
    }

    private String getStrippedText(char[] _c) {
        StringBuffer sb = new StringBuffer();
        int ii = _c.length;
        for (int i = 0; i < ii; ++i) {
            if (Character.isDigit(_c[i]) || _c[i] == SEPCHAR[type]) {
                sb.append(_c[i]);
            } else {
                break;
            }
        }
        return sb.toString();
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @author Anthony C. Liberto
     */
    public void actionPerformed(ActionEvent _ae) {
        togglePopup();
    }

    private void togglePopup() {
        if (popup.isShowing()) {
            hidePopup();
        } else {
            showPopup();
        }
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
        return getStrippedText();
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
        hidePopup();
        editingStopped();
        return true;
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
        if (_att == null) {
            return;
        }
        validator.setAttribute(_att);
        setFuture(validator.isFutureDate(), false);
        setPast(validator.isPastDate(), false);
        popup.setWarning(validator.isWarningDate()); // BH SR-14
        popup.resetCalendar();
        warningChecked = false;
    }

    /**
     * setPast
     * @param _b
     * @param _reset
     * @author Anthony C. Liberto
     */
    public void setPast(boolean _b, boolean _reset) {
        popup.setPast(_b);
        if (_reset) {
            popup.resetCalendar();
        }
    }

    /**
     * setFuture
     * @param _b
     * @param _reset
     * @author Anthony C. Liberto
     */
    public void setFuture(boolean _b, boolean _reset) {
        popup.setFuture(_b);
        if (_reset) {
            popup.resetCalendar();
        }
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
        if (_key == null) {
            return;
        }
        key = _key;
    }

    /**
     * setDisplay
     * @author Anthony C. Liberto
     * @param _s
     */
    public void setDisplay(String _s) {
        bypass = true; 
        if (Routines.have(_s)) {
        	setText(_s);
        } else {
            setText(defString[type]);
        }
        bypass = false;
    }

    /**
     * canLeave
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean canLeave() {
    	if (warningChecked){
    		return true;  //BH SR-14
    	}
        return validator.canLeave(getStrippedText());
    }

    /**
     * @see javax.swing.text.JTextComponent#setEditable(boolean)
     * @author Anthony C. Liberto
     */
    public void setEditable(boolean _b) {
        super.setEditable(_b);
        if (button == null) {
            return;
        }
        if (_b) {
            button.setVisible(true);
        } else {
            button.setVisible(false);
        }
    }

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
        setBorder(getCurrentBorder());
        if (displayOnly) {
            setEditable(false);
        } else {
            setEditable(true);
        }
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
            setCaretPosition(0); //52546
        }
    }

    /**
     * isRequired
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isRequired() {
        EANAttribute att = getAttribute();
        if (att != null) {
            return att.isRequired();
        }
        return false;
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
        initAccessibility(null);
        ef = null; //acl_20030506
        button.removeActionListener(this);
        button.removeAll();
        popup.dereference();
        removeAll();
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
            if (type == TIME_VALIDATOR) {
                setBorder(getCurrentBorder());
            }
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
        if (!find(_old, _bCase)) {
            return false;
        }
        setText(Routines.replace(getStrippedText(), _old, _new));
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
        if (type == TIME_VALIDATOR) {
            setBorder(getCurrentBorder());
        }
    }

    /**
     * removeEditor
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean removeEditor() {
        if (popup.isShowing()) {
            hidePopup();
        }
        return true;
    }

    private Border getCurrentBorder() {
        if (isFound()) {
            return EAccess.FOUND_BORDER;
        } else if (isDisplayOnly()) {
            return UIManager.getBorder("eannounce.emptyBorder");
        } else if (type == DATE_VALIDATOR) {
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
            boolean b = super.requestFocus(_temp);
            setCaretPosition(0);
            return b;
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
     52574
     */
    private class DateCaret extends DefaultCaret {
    	private static final long serialVersionUID = 1L;
    	private DateCaret() {
            setBlinkRate(500);
        }

        public int getDot() {
            int iOut = super.getDot();
            int iMax = getMaximumCaretPosition();
            return Math.min(iOut, iMax);
        }

        public int getMark() {
            int iOut = super.getMark();
            int iMax = getMaximumCaretPosition();
            return Math.min(iOut, iMax);
        }

        /**
         * @see javax.swing.text.DefaultCaret#moveCaret(java.awt.event.MouseEvent)
         * @author Anthony C. Liberto
         */
        protected void moveCaret(MouseEvent _me) {
            Point pt = new Point(_me.getX(), _me.getY());
            Position.Bias[] biasRet = new Position.Bias[1];
            JTextComponent component = getComponent();
            int pos = component.getUI().viewToModel(component, pt, biasRet);
            moveDot(Math.min(pos, getMaximumCaretPosition()));
        }
        /**
         * @see javax.swing.text.DefaultCaret#positionCaret(java.awt.event.MouseEvent)
         * @author Anthony C. Liberto
         */
        protected void positionCaret(MouseEvent _me) {
            Point pt = new Point(_me.getX(), _me.getY());
            Position.Bias[] biasRet = new Position.Bias[1];
            JTextComponent component = getComponent();
            int pos = component.getUI().viewToModel(component, pt, biasRet);
            setDot(Math.min(pos, getMaximumCaretPosition()));
        }
    }

    private int getMaximumCaretPosition() {
        String str = getStrippedText();
        if (str != null) {
            return str.length();
        }
        return 0;
    }

    /**
     * getDatePopupPanel
     * @return
     * @author Anthony C. Liberto
     */
    public DatePopupPanel getDatePopupPanel() {
        if (popup != null) {
            return popup.getDatePopupPanel();
        }
        return null;
    }

    /*
     52924
     */
    /**
     * @see javax.swing.text.JTextComponent#paste()
     * @author Anthony C. Liberto
     */
    public void paste() {
        String strTxt = null;
        String strSel = null;
        String out = null;
        if (isEditable() && isEnabled()) {
            String strClp = eaccess().getClipboardString();
            if (strClp != null) {
                Document doc = getDocument();
                if (doc != null) {
                    //boolean bValid = false;
                    int pos = getCaretPosition();
                    if (pos == 10) {
                        pos = 0;
                    }
                    strTxt = getStrippedText();
                    strSel = getSelectedText();
                    if (strSel != null && strSel.length() > 0) {
                        out = Routines.replace(strTxt, strSel, strClp.trim()); //53382
                    } else if (strTxt == null || strTxt.length() == 0) {
                        out = strClp.trim(); //53382
                    } else {
                        StringBuffer sb = new StringBuffer(strTxt);
                        sb.insert(pos, strClp.trim()); //53382
                        out = sb.toString();
                    }
                    out = formatDate(out);
                    setText(out);// BH SR-14 allow doc to do the checking
                   /* bValid = validator.validateDate(out, false);
                    if (bValid) {
                        setDisplay(out);
                    }*/
                }
            }
        }
    }

    private String formatDate(String _date) {
        if (_date != null) {
            char[] c = _date.toCharArray();
            int ii = c.length;
            StringBuffer sb = new StringBuffer();
            String out = null;
            for (int i = 0; i < ii; ++i) {
                if (i == 4 || i == 7) {
                    sb.append('-');
                }
                if (c[i] != '-') {
                    sb.append(c[i]);
                }
            }
            out = sb.toString();
            if (out.length() > 10) {
                return out.substring(0, 10);
            }
            return out;
        }
        return "";
    }
/*
 accessibility
 */
	/**
     * initAccessibility
     *
     * @author Anthony C. Liberto
     * @param _s
     */
    private void initAccessibility(String _s) {
		if (EAccess.isAccessible()) {
			AccessibleContext ac = getAccessibleContext();
			String strAccessible = null;
			if (ac != null) {
				if (_s == null) {
					ac.setAccessibleName(null);
					ac.setAccessibleDescription(null);
				} else {
					strAccessible = eaccess().getString(_s);
					ac.setAccessibleName(strAccessible);
					ac.setAccessibleDescription(strAccessible);
				}
			}
		}
	}
    
    private class MaxDoc extends PlainDocument {
    	private static final long serialVersionUID = 1L;
    	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            boolean adjust = false;
            if (bypass) {
                super.insertString(offs, str, a);
                return;
            }
            if (checkOffset(offs)) {
                if (!str.equals(SEPARATOR[type])) {
                    offs += 1;
                    adjust = true;
                }
            }
            if (charValidation(offs, str)) {
                super.remove(offs, str.length());
                super.insertString(offs, str, a);
                if (adjust) {
                    setCaretPosition(offs + 1);
                }
            }
        }

        public void remove(int offs, int len) throws BadLocationException {
            if (bypass) {
                super.remove(offs, len);
                return;
            }
 
            setCaretPosition(offs);
        }
    }

    private boolean warningChecked = false; // BH SR-14 
    private boolean charValidation(int _offs, String _str) {
    	String txt = getText().substring(0, _offs);
    	boolean isok = validator.validate(_str, txt + _str, _offs);
    	if (isok && validator.isWarningDate()){
    		warningChecked = true; // prevent checks when leaving the control if checks are already done
    	}
        return isok;
    }    
}
