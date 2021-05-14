/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/12/05
 * @author Anthony C. Liberto
 *
 * $Log: NumberPanel.java,v $
 * Revision 1.2  2008/01/30 16:26:54  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:43:24  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:10  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:43  tony
 * This is the initial load of OPICM
 *
 * Revision 1.5  2005/09/08 17:59:00  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.4  2005/02/04 15:22:08  tony
 * JTest Format Third Pass
 *
 * Revision 1.3  2005/02/02 17:30:21  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 23:18:20  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:28  tony
 * This is the initial load of OPICM
 *
 * Revision 1.7  2003/12/01 17:45:12  tony
 * accessibility
 *
 * Revision 1.6  2003/06/12 22:22:30  tony
 * 51278
 *
 * Revision 1.5  2003/05/30 21:09:19  tony
 * updated messaging logic to popup on top
 * of the currently active Component.
 *
 * Revision 1.4  2003/05/16 18:23:08  tony
 * 50713
 *
 * Revision 1.3  2003/05/01 17:30:16  tony
 * kc_20030501 - adjusted modal cursor and removed
 * menu from numberPanel.
 *
 * Revision 1.2  2003/03/13 18:38:44  tony
 * accessibility and column Order.
 *
 * Revision 1.1.1.1  2003/03/03 18:03:43  tony
 * This is the initial load of OPICM
 *
 *
 */
package com.ibm.eannounce.dialogpanels;
import com.elogin.*;
import com.ibm.eannounce.eforms.editor.EIntField;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JMenuBar;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class NumberPanel extends AccessibleDialogPanel implements KeyListener {
	private static final long serialVersionUID = 1L;
	private EButton btnOK = new EButton(getString("ok"));
    private EButton btnCancel = new EButton(getString("cncl"));
    private ELabel lbl = new ELabel();

    private EIntField intIn = new EIntField(2);
    private int rtn = -1;

    private EPanel pNorth = new EPanel();
    private GridLayout ga = new GridLayout(1, 2, 10, 10);
    private EPanel pSouth = new EPanel(ga);

//    private boolean block = true;

    /**
     * numberPanel
     * @author Anthony C. Liberto
     */
    public NumberPanel() {
        super(new BorderLayout());
        init();
        return;
    }

    /**
     * init
     * @author Anthony C. Liberto
     */
    public void init() {
        setModalCursor(true); //kc_20030501
        setFocusable(false); //kc_20030501

        pSouth.setLayout(ga);

        btnOK.setToolTipText(getString("help009"));
        btnOK.setActionCommand("ok");
        btnCancel.setToolTipText(getString("cncl-t"));
        btnOK.addActionListener(this);
        btnCancel.addActionListener(this);

        pSouth.add(btnOK);
        pSouth.add(btnCancel);

        pNorth.add(lbl);
        pNorth.add(intIn);
        lbl.setLabelFor(intIn); //access

        add("North", pNorth);
        add("South", pSouth);

        intIn.setMinValue(1);
        intIn.addKeyListener(this); //010477

        return;
    }

    /**
     * setText
     * @param _s
     * @author Anthony C. Liberto
     */
    public void setText(String _s) {
        lbl.setText(_s);
        return;
    }

    /**
     * actionPerformed
     *
     * @author Anthony C. Liberto
     * @param _action
     */
    public void actionPerformed(String _action) {
        if (_action.equals("ok")) {
            try {
                if (!intIn.isValid()) {
                    showError("msg12002.0");
                    intIn.grabFocus();
                    return;
                }
                rtn = Integer.valueOf(intIn.getText()).intValue();
            } catch (NumberFormatException nfe) {
                EAccess.report(nfe,false);
                rtn = -1;
            }
        }
        intIn.grabFocus();
        hideDialog();
        return;
    }

    /**
     * getSelected
     * @return
     * @author Anthony C. Liberto
     */
    public int getSelected() {
        return rtn;
    }

    /**
     * reset
     * @author Anthony C. Liberto
     */
    public void reset() {
        rtn = -1;
        return;
    }

    /**
     * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
     * @author Anthony C. Liberto
     */
    public void keyTyped(KeyEvent kea) {
    }
    /**
     * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
     * @author Anthony C. Liberto
     */
    public void keyReleased(KeyEvent kea) {
    }

    /**
     * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
     * @author Anthony C. Liberto
     */
    public void keyPressed(KeyEvent kea) {
        if (kea.getKeyCode() == KeyEvent.VK_ENTER) {
            if (btnOK.isDefaultButton()) {
                try {
                    if (!intIn.isValid()) {
                        showError("msg12002.0");
                        intIn.grabFocus();
                        return;
                    }
                    rtn = Integer.valueOf(intIn.getText()).intValue();
                } catch (NumberFormatException nfe) {
                    EAccess.report(nfe,false);
                    rtn = -1;
                }
            } else if (btnCancel.isDefaultButton()) {
                rtn = -1;
            }
            hideDialog();
        }
        return;
    }

    /**
     * showMe
     * @author Anthony C. Liberto
     */
    public void showMe() {
        intIn.setValue(1); //50713
        getRootPane().setDefaultButton(btnOK); //51278
        packDialog();
        return;
    }

    /**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
        pNorth.removeAll();
        pNorth = null;
        pSouth.removeAll();
        pSouth = null;

        btnOK.removeActionListener(this);
        btnCancel.removeActionListener(this);
        intIn.removeKeyListener(this); //010477

        btnOK = null;
        btnCancel = null;
        intIn = null;

        ga = null;

        removeAll();
        removeNotify();
        return;
    }

    /**
     * getMenuBar
     * @author Anthony C. Liberto
     * @return JMenuBar
     */
    public JMenuBar getMenuBar() { //kc_20030501
        return null; //kc_20030501
    } //kc_20030501

    /**
     * createMenu
     * @author Anthony C. Liberto
     */
    protected void createMenu() {
    } //kc_20030501
    /**
     * removeMenu
     * @author Anthony C. Liberto
     */
    protected void removeMenu() {
    } //kc_20030501

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
        return getString("numb.panel");
    }
}
