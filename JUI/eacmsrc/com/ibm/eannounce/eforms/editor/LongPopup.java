/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: LongPopup.java,v $
 * Revision 1.2  2008/01/30 16:27:04  wendy
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
 * Revision 1.5  2005/09/08 17:59:03  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.4  2005/02/01 20:48:32  tony
 * JTest Second Pass
 *
 * Revision 1.3  2005/01/27 19:15:17  tony
 * JTest Format
 *
 * Revision 1.2  2004/11/05 22:04:18  tony
 * made dialog resizable per dwb1
 *
 * Revision 1.1.1.1  2004/02/10 16:59:41  tony
 * This is the initial load of OPICM
 *
 * Revision 1.10  2003/12/22 19:03:31  tony
 * 53452
 *
 * Revision 1.9  2003/12/19 23:14:01  tony
 * acl20031219
 * updated logic so that on 'x' close click on longeditor
 * editing is completed.
 *
 * Revision 1.8  2003/08/25 14:36:20  tony
 * 51815
 *
 * Revision 1.7  2003/08/21 18:07:47  tony
 * 51869
 *
 * Revision 1.6  2003/07/10 20:11:29  tony
 * added shouldResetBusy Logic to assist in providing more
 * control of cursor busy.
 *
 * Revision 1.5  2003/07/10 15:44:23  tony
 * 51317
 *
 * Revision 1.4  2003/05/02 17:51:23  tony
 * 50497
 *
 * Revision 1.3  2003/04/29 17:23:21  tony
 * cleaned-up longEditor to properly display to
 * enhance editing functionality.
 *
 * Revision 1.2  2003/04/22 22:04:14  joan
 * 50405
 *
 * Revision 1.1.1.1  2003/03/03 18:03:47  tony
 * This is the initial load of OPICM
 *
 * Revision 1.7  2002/11/07 16:58:28  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.editor;
import com.elogin.*;
import com.ibm.eannounce.eobjects.EScrollPane;
import COM.ibm.eannounce.objects.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class LongPopup extends AccessibleDialogPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private EPanel pnlButton = new EPanel(new GridLayout(1, 2, 5, 5));
    private EButton btnOK = new EButton(eaccess().getString("ok"));
    private EButton btnCncl = new EButton(eaccess().getString("cncl"));

    private Dimension sSize = new Dimension(300, 400);

    private LongEditor editor = null;
    private EScrollPane scroll = new EScrollPane();

    /**
     * longPopup
     * @author Anthony C. Liberto
     */
    public LongPopup() {
        super(new BorderLayout());
        add("Center", scroll);
        installButtons();

        scroll.setSize(sSize);
        scroll.setPreferredSize(sSize);
        scroll.setMaximumSize(sSize);
        setResizable(true); //dwb_20041105
        return;
    }

    /**
     * setEditor
     * @param _editor
     * @author Anthony C. Liberto
     */
    public void setEditor(LongEditor _editor) {
        editor = _editor;
        scroll.setViewportView(_editor);
        scroll.validate();
        packDialog();
        return;
    }

    /**
     * setTitle
     * @param _meta
     * @author Anthony C. Liberto
     */
    public void setTitle(EANMetaAttribute _meta) {
        if (_meta != null) {
            setTitle(_meta.getLongDescription());
        }
        return;
    }

    private void installButtons() {
        pnlButton.add(btnOK);
        pnlButton.add(btnCncl);

        btnOK.addActionListener(this);
        btnOK.setMnemonic(eaccess().getChar("ok-s"));

        btnCncl.addActionListener(this);
        btnCncl.setMnemonic(eaccess().getChar("cncl-s"));

        btnOK.setActionCommand("save");
        btnCncl.setActionCommand("cancel");
        add("South", pnlButton);
        return;
    }

    private void uninstallButtons() {
        btnOK.removeActionListener(this);
        btnOK.dereference();
        btnOK = null;
        btnCncl.removeActionListener(this);
        btnCncl.dereference();
        btnCncl = null;
        return;
    }

    /**
     * setEditable
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setEditable(boolean _b) {
        btnOK.setEnabled(_b);
        editor.setEditable(_b);
        return;
    }

    /**
     * isEditable
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isEditable() {
        return editor.isEditable();
    }

    /**
     * setText
     * @param _s
     * @author Anthony C. Liberto
     */
    public void setText(String _s) {
        editor.setText(_s);
    }

    /**
     * getText
     * @return
     * @author Anthony C. Liberto
     */
    public String getText() {
        return editor.getText();
    }

    /**
     * @see java.awt.Component#requestFocus()
     * @author Anthony C. Liberto
     */
    public void requestFocus() {
        editor.requestFocus();
        return;
    }

    /**
     * @see javax.swing.JComponent#grabFocus()
     * @author Anthony C. Liberto
     */
    public void grabFocus() {
        editor.grabFocus();
        return;
    }

    /**
     * paste
     * @author Anthony C. Liberto
     */
    public void paste() {
        editor.paste();
        return;
    }

    /**
     * dereference
     *
     * @author Anthony C. Liberto
     */
    public void dereference() {
        uninstallButtons();
        pnlButton.removeAll(); //leak
        pnlButton.removeNotify(); //leak
        super.dereference();
        return;
    }

    /*
     51317
     */
    /**
     * getMenuBar
     *
     * @return
     * @author Anthony C. Liberto
     */
    public JMenuBar getMenuBar() {
        return null;
    }

    /**
     * createMenu
     *
     * @author Anthony C. Liberto
     */
    protected void createMenu() {
    }

    /**
     * removeMenu
     *
     * @author Anthony C. Liberto
     */
    protected void removeMenu() {
    }

    /*
     53452
    	public void show(Component _c) {
    		eaccess().show(_c,this,false);
    		return;
    	}
    */
    /**
     * actionPerformed
     * @author Anthony C. Liberto
     * @param _action
     */
    protected void actionPerformed(String _action) {
        if (_action.equals("save")) {
            if (!editor.stopCellEditing()) {
                return;
            }
        } else if (_action.equals("cancel")) {
            editor.cancelCellEditing();
        }
        if (isShowing()) {
            disposeDialog();
        }
        return;
    }

    /**
     * shouldResetBusy
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean shouldResetBusy() {
        return false;
    }

    /*
     51869
     */
    /**
     * show
     * @param _c
     * @param _modal
     * @author Anthony C. Liberto
     */
    public void show(Component _c, boolean _modal) {
        eaccess().show(_c, this, _modal);
        return;
    }

    /*
     51815
     */
    /**
     * getFrame
     * @return
     * @author Anthony C. Liberto
     */
    public Frame getFrame() {
        if (id != null && id instanceof Frame) {
            return (Frame) id;
        }
        return null;
    }

    /*
     acl20031219
     */
    /**
     * canWindowClose
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean canWindowClose() {
        return editor.stopCellEditing();
    }

    /*
     53452
     */
    /**
     * show
     * @param _c
     * @author Anthony C. Liberto
     */
    public void show(final Component _c) {
        final ESwingWorker myWorker = new ESwingWorker() {
            public Object construct() {
                editor.releaseFocus(false);
                show(_c, false);
                return null;
            }

            public void finished() {
                editor.requestFocus();
                editor.releaseFocus(true);
                setWorker(null);
                setBusy(false);
                return;
            }

            /*public boolean isBreakable() {
                return false;
            }*/
        };
        setWorker(myWorker);
        return;
    }
}
