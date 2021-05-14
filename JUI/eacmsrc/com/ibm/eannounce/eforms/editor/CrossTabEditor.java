/**
 * Copyright (c) 2001 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 * the cross tab editor
 *
 * @author Anthony C. Liberto
 * @version 2.3
 *
 * $Log: CrossTabEditor.java,v $
 * Revision 1.2  2008/01/30 16:27:05  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:44:33  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:12  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:56  tony
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
 * Revision 1.1.1.1  2004/02/10 16:59:40  tony
 * This is the initial load of OPICM
 *
 * Revision 1.6  2003/06/26 15:41:00  tony
 * MCI -- LAX
 * Updated logic for matrix, based on issues discovered by KC To La
 * Fixed filtering, editing, and matrix from edit for form and vert.
 *
 * Revision 1.3  2003/04/23 17:17:09  tony
 * updated CrossTabEditor made into text based editor.
 *
 * Revision 1.2  2003/03/04 22:34:51  tony
 * *** empty log message ***
 *
 * Revision 1.1.1.1  2003/03/03 18:03:47  tony
 * This is the initial load of OPICM
 *
 * Revision 1.3  2002/06/28 19:51:05  tony
 * memory update matrix.
 *
 * Revision 1.2  2002/02/13 15:55:34  tony
 * adjusted trace statements.
 *
 * Revision 1.1.1.1  2001/11/29 19:00:18  tony
 * This is the initial load of OPICM
 *
 * Revision 1.1.1.1  2001/08/06 21:39:19  tony
 * This is the initial load of OPICM
 *
 * Revision 1.2  2001/05/23 15:50:58  tony
 * wayback processing.
 *
 * Revision 1.1.1.1  2001/04/19 00:58:43  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2001/03/22 22:17:56  tony
 * adjusted log information to remove duplicates
 *
 * Revision 1.3  2001/03/22 21:09:54  tony
 * Added standard copyright to all
 * modules
 *
 * Revision 1.2  2001/03/22 18:54:30  tony
 * added log keyword
 *
 */
package com.ibm.eannounce.eforms.editor;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.plaf.basic.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class CrossTabEditor extends EIntField implements TableCellEditor, ActionListener {
	private static final long serialVersionUID = 1L;
	/**
     * max
     */
    public static final int MAX = 99;
    /**
     * min
     */
    public static final int MIN = 0;

    private JButton bIncrement = new BasicArrowButton(BasicArrowButton.NORTH) {
    	private static final long serialVersionUID = 1L;
    	public Cursor getCursor() {
            return Cursor.getDefaultCursor();
        }
    };
    private JButton bDecrement = new BasicArrowButton(BasicArrowButton.SOUTH) {
    	private static final long serialVersionUID = 1L;
    	public Cursor getCursor() {
            return Cursor.getDefaultCursor();
        }
    };
    private Dimension dButton = new Dimension(10, 10);

    /**
     * changeEvent
     */
    transient protected ChangeEvent changeEvent = null;

    /**
     * CrossTabEditor
     * @author Anthony C. Liberto
     */
    public CrossTabEditor() {
        super(2, 99, false);
        init();
        return;
    }

    private void init() {
        add(bIncrement);
        bIncrement.setActionCommand("increment");
        bIncrement.addActionListener(this);
        bIncrement.setSize(dButton);

        add(bDecrement);
        bDecrement.setActionCommand("decrement");
        bDecrement.addActionListener(this);
        bDecrement.setSize(dButton);
        return;
    }

    /**
     * @see java.awt.Component#setBounds(java.awt.Rectangle)
     * @author Anthony C. Liberto
     */
    public void setBounds(Rectangle _rec) {
        bIncrement.setBounds(_rec.width - dButton.width, 0, dButton.width, dButton.height);
        bDecrement.setBounds(_rec.width - dButton.width, dButton.height, dButton.width, dButton.height);
        super.setBounds(_rec);
        return;
    }

    /**
     * getHour
     * @return
     * @author Anthony C. Liberto
     */
    public String getHour() {
        return getText().trim();
    }

    /**
     * getInt
     * @return
     * @author Anthony C. Liberto
     */
    public int getInt() {
        return Integer.parseInt(getText());
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @author Anthony C. Liberto
     */
    public void actionPerformed(ActionEvent _ae) {
        String action = _ae.getActionCommand();
        if (action.equals("decrement")) {
            setText(decrement(getInt()));
        } else if (action.equals("increment")) {
            setText(increment(getInt()));
        }
        return;
    }

    private String increment(int _i) {
        String str = null;
        if (_i < MAX) {
            _i++;
        } else if (_i == MAX) {
            eaccess().showError(this, "msg23020");
        }
        str = String.valueOf(_i);
        if (str.length() == 1) {
            str = "0" + str;
        }
        return str;
    }

    private String decrement(int _i) {
        String str = null;
        if (_i > MIN) {
            _i--;
        } else if (_i == MIN) {
            eaccess().showError(this, "msg23021");
        }
        str = String.valueOf(_i);
        if (str.length() == 1) {
            str = "0" + str;
        }
        return str;
    }

    /**
     * dereference
     *
     * @author Anthony C. Liberto
     */
    public void dereference() {
        bIncrement.removeActionListener(this);
        bDecrement.removeActionListener(this);
        removeAll();
        removeNotify();
        return;
    }

    /**
     * TableCellEditor
     *
     * @param c
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
        if (v instanceof String) {
            setText(v.toString());
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
        setText(getText());
        editingStopped();
        return true;
    }

    /**
     * editingStopped
     * @author Anthony C. Liberto
     */
    protected void editingStopped() {
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
}
