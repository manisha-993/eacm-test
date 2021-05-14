/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: EIPanel.java,v $
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
 * Revision 1.4  2005/09/08 17:59:03  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/02/03 16:38:53  tony
 * JTest Second Pass Wrapup
 *
 * Revision 1.2  2005/02/01 20:48:33  tony
 * JTest Second Pass
 *
 * Revision 1.1.1.1  2004/02/10 16:59:41  tony
 * This is the initial load of OPICM
 *
 * Revision 1.5  2003/11/18 20:54:04  tony
 * added formEditorInterface to simplify formEditor modification.
 *
 * Revision 1.4  2003/10/13 17:15:52  tony
 * kc_20031012 -- updated date editor on form so that
 * it behaves similar to the editor on the table.
 *
 * Revision 1.3  2003/05/06 16:38:21  tony
 * acl_20030506 -- enhanced focus logic to prevent error
 * messages from firing when refocusing the component.
 *
 * Revision 1.2  2003/03/04 22:34:51  tony
 * *** empty log message ***
 *
 * Revision 1.1.1.1  2003/03/03 18:03:47  tony
 * This is the initial load of OPICM
 *
 * Revision 1.7  2002/11/11 22:55:39  tony
 * adjusted classification on the toggle
 *
 * Revision 1.6  2002/11/07 16:58:27  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.editor;
import com.elogin.*;
import com.ibm.eannounce.eforms.editform.FormEditorInterface;
import java.awt.*;
import java.awt.event.*;
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
public class EIPanel extends EPanel implements EditorInterface {
	private static final long serialVersionUID = 1L;
	//kc_20031012	private Dimension size = new Dimension(300,100);

    private boolean form = false;

    private EditorInterface edit = null;

    /**
     * eiPanel
     * @param _edit
     * @param _form
     * @author Anthony C. Liberto
     */
    public EIPanel(EditorInterface _edit, boolean _form) {
        super(new BorderLayout());
        edit = _edit;
        //kc_20031012		setSize(size);
        //kc_20031012		setPreferredSize(size);
        //kc_20031012		setMaximumSize(size);
        setBorder(getCurrentBorder());
        add("Center", (Component) edit);
        form = _form;
        return;
    }

    /**
     * @see java.awt.Component#setBounds(java.awt.Rectangle)
     * @author Anthony C. Liberto
     */
    public void setBounds(Rectangle _rect) {
        if (!form) {
            return;
        }
        super.setBounds(_rect);
    }

    /**
     * setEditable
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setEditable(boolean _b) {
        edit.setEditable(_b);
    }

    /**
     * isEditable
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isEditable() {
        return edit.isEditable();
    }

    /**
     * setText
     * @author Anthony C. Liberto
     * @param _s
     */
    public void setText(String _s) {
        edit.setText(_s);
    }

    /**
     * getText
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getText() {
        return edit.getText();
    }

    /*
     *editorInterface
     */
    /**
     * @see javax.swing.CellEditor#addCellEditorListener(javax.swing.event.CellEditorListener)
     * @author Anthony C. Liberto
     */
    public void addCellEditorListener(CellEditorListener c) {
        edit.addCellEditorListener(c);
    }

    /**
     * @see javax.swing.CellEditor#cancelCellEditing()
     * @author Anthony C. Liberto
     */
    public void cancelCellEditing() {
        edit.cancelCellEditing();
    }

    /**
     * @see javax.swing.CellEditor#getCellEditorValue()
     * @author Anthony C. Liberto
     */
    public Object getCellEditorValue() {
        return edit.getCellEditorValue();
    }

    /**
     * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
     * @author Anthony C. Liberto
     */
    public Component getTableCellEditorComponent(JTable t, Object v, boolean sel, int r, int c) {
        if (v instanceof EANAttribute) {
            EANAttribute att = (EANAttribute) v;
            setDisplay(att.get().toString());
        }
        return this;
    }

    /**
     * @see javax.swing.CellEditor#isCellEditable(java.util.EventObject)
     * @author Anthony C. Liberto
     */
    public boolean isCellEditable(EventObject e) {
        return edit.isCellEditable(e);
    }

    /**
     * @see javax.swing.CellEditor#removeCellEditorListener(javax.swing.event.CellEditorListener)
     * @author Anthony C. Liberto
     */
    public void removeCellEditorListener(CellEditorListener c) {
        edit.removeCellEditorListener(c);
    }

    /**
     * @see javax.swing.CellEditor#shouldSelectCell(java.util.EventObject)
     * @author Anthony C. Liberto
     */
    public boolean shouldSelectCell(EventObject e) {
        return edit.shouldSelectCell(e);
    }

    /**
     * @see javax.swing.CellEditor#stopCellEditing()
     * @author Anthony C. Liberto
     */
    public boolean stopCellEditing() {
        return edit.stopCellEditing();
    }

    /**
     * setAttribute
     * @author Anthony C. Liberto
     * @param _att
     */
    public void setAttribute(EANAttribute _att) {
        edit.setAttribute(_att);
    }

    /**
     * getAttribute
     *
     * @return
     * @author Anthony C. Liberto
     */
    public EANAttribute getAttribute() {
        return edit.getAttribute();
    }

    /**
     * getKey
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getKey() {
        return edit.getKey();
    }

    /**
     * setKey
     * @author Anthony C. Liberto
     * @param _key
     */
    public void setKey(String _key) {
        edit.setKey(_key);
    }

    /**
     * setDisplay
     * @author Anthony C. Liberto
     * @param _s
     */
    public void setDisplay(String _s) {
        edit.setDisplay(_s);
        return;
    }

    /**
     * canLeave
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean canLeave() {
        return edit.canLeave();
    }

    /**
     * setDisplayOnly
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setDisplayOnly(boolean _b) {
        edit.setDisplayOnly(_b);
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
        if (edit != null) {
            return edit.isDisplayOnly();
        }
        return false;
    }

    /**
     * prepareToEdit
     * @author Anthony C. Liberto
     * @param _c
     * @param _eo
     */
    public void prepareToEdit(EventObject _eo, Component _c) {
        edit.prepareToEdit(_eo, _c);
        return;
    }

    /**
     * refreshDisplay
     * @author Anthony C. Liberto
     * @param _att
     */
    public void refreshDisplay(EANAttribute _att) {
        edit.refreshDisplay(_att);
    }

    /**
     * copy
     *
     * @author Anthony C. Liberto
     */
    public void copy() {
        edit.copy();
    }

    /**
     * cut
     *
     * @author Anthony C. Liberto
     */
    public void cut() {
        edit.cut();
    }

    /**
     * paste
     *
     * @author Anthony C. Liberto
     */
    public void paste() {
        edit.paste();
    }

    /**
     * isRequired
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isRequired() {
        return edit.isRequired();
    }

    /**
     * cancel
     *
     * @author Anthony C. Liberto
     */
    public void cancel() {
        edit.cancel();
    }

    /**
     * deactivate
     *
     * @author Anthony C. Liberto
     */
    public void deactivate() {
        edit.deactivate();
    }

    /**
     * setEffectivity
     * @author Anthony C. Liberto
     * @param _from
     * @param _to
     */
    public void setEffectivity(String _from, String _to) {
        edit.setEffectivity(_from, _to);
    }

    /**
     * dereference
     *
     * @author Anthony C. Liberto
     */
    public void dereference() {
        edit.dereference();
        removeAll();
    }

    /*
    acl_20030506
    	public void setTable(RowSelectableTable _table) {
    		edit.setTable(_table);
    	}

    	public RowSelectableTable getTable() {
    		return edit.getTable();
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
        boolean b = edit.find(_strFind, _bCase);
        setBorder(getCurrentBorder());
        return b;
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
        return edit.replace(_old, _new, _bCase);
    }

    /**
     * isFound
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isFound() {
        if (edit != null) {
            return edit.isFound();
        }
        return false;
    }

    /**
     * resetFound
     *
     * @author Anthony C. Liberto
     */
    public void resetFound() {
        edit.resetFound();
        setBorder(getCurrentBorder());
        return;
    }

    /**
     * getTextComponent
     * @return
     * @author Anthony C. Liberto
     */
    public JTextComponent getTextComponent() {
        return (JTextComponent) edit;
    }

    /**
     * @see java.awt.Component#addKeyListener(java.awt.event.KeyListener)
     * @author Anthony C. Liberto
     */
    public void addKeyListener(KeyListener _kl) {
        edit.addKeyListener(_kl);
    }

    /**
     * @see java.awt.Component#removeKeyListener(java.awt.event.KeyListener)
     * @author Anthony C. Liberto
     */
    public void removeKeyListener(KeyListener _kl) {
        edit.removeKeyListener(_kl);
    }

    /**
     * @see java.awt.Component#addFocusListener(java.awt.event.FocusListener)
     * @author Anthony C. Liberto
     */
    public void addFocusListener(FocusListener _fl) {
        if (edit != null) {
            edit.addFocusListener(_fl);
        }
        return;
    }

    /**
     * @see java.awt.Component#removeFocusListener(java.awt.event.FocusListener)
     * @author Anthony C. Liberto
     */
    public void removeFocusListener(FocusListener _fl) {
        if (edit != null) {
            edit.removeFocusListener(_fl);
        }
        return;
    }

    /**
     * @see java.awt.Component#addMouseListener(java.awt.event.MouseListener)
     * @author Anthony C. Liberto
     */
    public void addMouseListener(MouseListener _ml) {
        if (edit != null) {
            edit.addMouseListener(_ml);
        }
        return;
    }

    /**
     * @see java.awt.Component#removeMouseListener(java.awt.event.MouseListener)
     * @author Anthony C. Liberto
     */
    public void removeMouseListener(MouseListener _ml) {
        if (edit != null) {
            edit.removeMouseListener(_ml);
        }
        return;
    }

    /**
     * getMetaAttribute
     *
     * @return
     * @author Anthony C. Liberto
     */
    public EANMetaAttribute getMetaAttribute() { //22098
        return edit.getMetaAttribute(); //22098
    } //22098

    /**
     * hasChanged
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean hasChanged() { //acl_20021023
        return edit.hasChanged(); //acl_20021023
    } //acl_20021023

    /**
     * removeEditor
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean removeEditor() {
        return false;
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
        return edit.isReplaceable(); //22632
    } //22632

    /**
     * getPanelType
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getPanelType() {
        return TYPE_EIPANEL;
    }

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
        return edit.getEditForm();
    }

    /**
     * canReceiveFocus
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean canReceiveFocus() {
        return edit.canReceiveFocus();
    }

    /**
     * @see java.awt.Component#requestFocus()
     * @author Anthony C. Liberto
     */
    public void requestFocus() {
        edit.requestFocus();
    }

    /**
     * @see java.awt.Component#requestFocus(boolean)
     * @author Anthony C. Liberto
     */
    public boolean requestFocus(boolean _temp) {
        return edit.requestFocus(_temp);
    }

    /**
     * @see javax.swing.JComponent#grabFocus()
     * @author Anthony C. Liberto
     */
    public void grabFocus() {
        edit.grabFocus();
    }

    /**
     * equals
     * @author Anthony C. Liberto
     * @return boolean
     * @param _c
     */
    public boolean equals(Component _c) {
        return edit.equals(_c);
    }
    /*
     kc_20031012
     */
    /**
     * pack
     * @author Anthony C. Liberto
     */
    public void pack() {
        setSize(getPreferredSize());
        return;
    }

    private Border getCurrentBorder() {
        if (isFound()) {
            return EAccess.FOUND_BORDER;
        } else if (isDisplayOnly()) {
            return UIManager.getBorder("eannounce.emptyBorder");
        }
        return UIManager.getBorder("TextField.border");
    }

    /**
     * Automatically generated method: hashCode
     *
     * @return int
     */
    public int hashCode() {
        return 0;
    }
}
