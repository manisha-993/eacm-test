/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: EIScroll.java,v $
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
 * Revision 1.3  2005/02/01 20:48:32  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 19:15:16  tony
 * JTest Format
 *
 * Revision 1.1.1.1  2004/02/10 16:59:41  tony
 * This is the initial load of OPICM
 *
 * Revision 1.8  2003/12/08 20:44:52  tony
 * cleaned-up code
 *
 * Revision 1.7  2003/11/18 20:54:04  tony
 * added formEditorInterface to simplify formEditor modification.
 *
 * Revision 1.6  2003/08/21 15:57:34  tony
 * 51869
 *
 * Revision 1.5  2003/07/09 20:47:35  tony
 * 51422 -- blobEditor popup on double click
 *
 * Revision 1.4  2003/05/06 16:38:21  tony
 * acl_20030506 -- enhanced focus logic to prevent error
 * messages from firing when refocusing the component.
 *
 * Revision 1.3  2003/05/01 22:41:36  tony
 * added static borders to address border rendering
 * issues on found.
 *
 * Revision 1.2  2003/04/30 21:40:47  tony
 * updated editor rendering on form.
 *
 * Revision 1.1.1.1  2003/03/03 18:03:47  tony
 * This is the initial load of OPICM
 *
 * Revision 1.15  2002/11/07 16:58:28  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.editor;
import com.elogin.*;
import com.ibm.eannounce.eforms.editform.*;
import com.ibm.eannounce.eobjects.*;
import COM.ibm.eannounce.objects.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.*;
import javax.swing.text.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class EIScroll extends EScrollPane implements EditorInterface {
	private static final long serialVersionUID = 1L;
	private Dimension size = new Dimension(300, 105);
    private boolean form = false;
    private EditorInterface edit = null;
    private FormLabel lbl = null;

    private boolean displayScrollBar = false;

    /**
     * eiScroll
     * @param _edit
     * @param _form
     * @author Anthony C. Liberto
     */
    public EIScroll(EditorInterface _edit, boolean _form) {
        super();
        edit = _edit;
        setSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
        setViewportView((Component) edit);
        form = _form;
        if (displayScrollBar) {
            setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_AS_NEEDED);
            setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
        }
        return;
    }

    /**
     * eiScroll
     * @param _lbl
     * @param _form
     * @author Anthony C. Liberto
     */
    public EIScroll(FormLabel _lbl, boolean _form) {
        super();
        lbl = _lbl;
        setSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
        setViewportView(_lbl);
        form = _form;
        if (displayScrollBar) {
            setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_AS_NEEDED);
            setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
        }
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
     * getKey
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getKey() {
        if (edit != null) {
            return edit.getKey();

        } else if (lbl != null) {
            return lbl.getKey();
        }
        return null;
    }

    /**
     * setKey
     * @author Anthony C. Liberto
     * @param _key
     */
    public void setKey(String _key) {
        if (edit != null) {
            edit.setKey(_key);
        }
        return;
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
     * @see javax.swing.JComponent#setOpaque(boolean)
     * @author Anthony C. Liberto
     */
    public void setOpaque(boolean _b) {
        super.setOpaque(_b);
        getViewport().setOpaque(_b);
        if (edit != null) {
            edit.setOpaque(_b);
        } else if (lbl != null) {
            lbl.setOpaque(_b);
        }
        return;
    }

    /**
     * setDisplayOnly
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setDisplayOnly(boolean _b) {
        if (edit != null) {
            edit.setDisplayOnly(_b);
        }
        if (!displayScrollBar) {
            if (_b) {
                setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
                setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_NEVER);
            } else {
                setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_AS_NEEDED);
                setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
            }
        } else {
            if (_b) {
                getHorizontalScrollBar().setOpaque(false);
                getVerticalScrollBar().setOpaque(false);
            } else {
                getHorizontalScrollBar().setOpaque(true);
                getVerticalScrollBar().setOpaque(true);
            }
        }
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
        return true;
    }

    //51422	public void prepareToEdit() {
    //51422		edit.prepareToEdit();
    //51869	public void prepareToEdit(EventObject _eo) {	//51422
    /**
     * prepareToEdit
     * @author Anthony C. Liberto
     * @param _c
     * @param _eo
     */
    public void prepareToEdit(EventObject _eo, Component _c) { //51869
        edit.prepareToEdit(_eo, _c);
        return;
    }

    /**
     * refreshDisplay
     * @author Anthony C. Liberto
     * @param _att
     */
    public void refreshDisplay(EANAttribute _att) {
        if (edit != null) {
            edit.refreshDisplay(_att);
        } else if (lbl != null) {
            lbl.refresh(_att);
        }
        return;
    }

    /**
     * copy
     *
     * @author Anthony C. Liberto
     */
    public void copy() {
        if (edit != null) {
            edit.copy();
        }
    }

    /**
     * cut
     *
     * @author Anthony C. Liberto
     */
    public void cut() {
        if (edit != null) {
            edit.cut();
        }
    }

    /**
     * paste
     *
     * @author Anthony C. Liberto
     */
    public void paste() {
        if (edit != null) {
            edit.paste();
        }
    }

    /**
     * isRequired
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isRequired() {
        if (edit != null) {
            return edit.isRequired();
        }
        return false;
    }

    /**
     * cancel
     *
     * @author Anthony C. Liberto
     */
    public void cancel() {
        if (edit != null) {
            edit.cancel();
        }
    }

    /**
     * deactivate
     *
     * @author Anthony C. Liberto
     */
    public void deactivate() {
        if (edit != null) {
            edit.deactivate();
        }
    }

    /**
     * setEffectivity
     * @author Anthony C. Liberto
     * @param _from
     * @param _to
     */
    public void setEffectivity(String _from, String _to) {
        if (edit != null) {
            edit.setEffectivity(_from, _to);
        }
    }

    /**
     * dereference
     *
     * @author Anthony C. Liberto
     */
    public void dereference() {
        if (edit != null) {
            edit.dereference();
        }
        removeAll();
    }

    /*
    acl_20030506
    	public void setTable(RowSelectableTable _table) {
    		if (edit != null)
    			edit.setTable(_table);
    	}

    	public RowSelectableTable getTable() {
    		if (edit != null)
    			return edit.getTable();
    		return null;
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
        if (edit != null) {
            return edit.find(_strFind, _bCase);

        } else if (lbl != null) {
            return lbl.find(_strFind, _bCase);
        }
        return false;
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
        if (edit != null) {
            return edit.replace(_old, _new, _bCase);
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
        if (edit != null) {
            return edit.isFound();

        } else if (lbl != null) {
            return lbl.isFound();
        }
        return false;
    }

    /**
     * resetFound
     *
     * @author Anthony C. Liberto
     */
    public void resetFound() {
        if (edit != null) {
            edit.resetFound();

        } else if (lbl != null) {
            lbl.resetFound();
        }
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
        if (edit != null) {
            edit.addKeyListener(_kl);
        }
        return;
    }

    /**
     * @see java.awt.Component#removeKeyListener(java.awt.event.KeyListener)
     * @author Anthony C. Liberto
     */
    public void removeKeyListener(KeyListener _kl) {
        if (edit != null) {
            edit.removeKeyListener(_kl);
        }
        return;
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
     * removeEditor
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean removeEditor() {
        return false;
    }

    private Border getCurrentBorder() {
        if (isFound()) {
            return EAccess.FOUND_BORDER;
        } else if (isDisplayOnly() && !displayScrollBar) {
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
        return edit.isReplaceable(); //22632
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
}
