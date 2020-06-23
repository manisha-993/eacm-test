/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/12/05
 * @author Anthony C. Liberto
 *
 * $Log: FrameDialog.java,v $
 * Revision 1.2  2008/01/30 16:27:00  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:42:17  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:09  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:39  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:58:55  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/02/02 21:30:06  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 23:18:16  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:25  tony
 * This is the initial load of OPICM
 *
 * Revision 1.22  2003/12/19 23:14:01  tony
 * acl20031219
 * updated logic so that on 'x' close click on longeditor
 * editing is completed.
 *
 * Revision 1.21  2003/12/10 22:03:49  tony
 * 53373
 *
 * Revision 1.20  2003/12/01 19:46:27  tony
 * accessibility
 *
 * Revision 1.19  2003/10/29 20:06:49  tony
 * 52768
 *
 * Revision 1.18  2003/10/29 00:22:22  tony
 * removed System.out. statements.
 *
 * Revision 1.17  2003/09/25 20:03:11  tony
 * 52369
 *
 * Revision 1.16  2003/08/28 17:51:11  tony
 * 51969
 *
 * Revision 1.15  2003/08/21 18:10:51  tony
 * 51762
 *
 * Revision 1.14  2003/08/20 18:00:32  tony
 * 51830
 *
 * Revision 1.13  2003/07/10 20:11:29  tony
 * added shouldResetBusy Logic to assist in providing more
 * control of cursor busy.
 *
 * Revision 1.12  2003/05/28 18:15:03  tony
 * fixed null pointer on base filter, find, and sort
 *
 * Revision 1.11  2003/05/28 16:28:22  tony
 * 50924
 *
 * Revision 1.10  2003/05/15 19:33:05  tony
 * KC_20030515 added behavior fixes for KC
 *
 * Revision 1.9  2003/05/06 00:07:19  tony
 * 50468
 *
 * Revision 1.8  2003/04/11 20:47:17  tony
 * improved Windows logic.
 *
 * Revision 1.7  2003/04/10 20:06:23  tony
 * updated logic to allow for dialogs to properly
 * eminiate from the dialogs parent.
 *
 * Revision 1.6  2003/04/03 18:51:47  tony
 * adjusted logic to display individualized icon
 * for each frameDialog/tab.
 *
 * Revision 1.5  2003/04/03 16:19:06  tony
 * changed refreshLookAndFeel to refreshAppearance()
 * updated logic to take into account displayed dialogs
 * as well.
 *
 * Revision 1.4  2003/03/13 21:16:31  tony
 * adjusted disposeDialog and enhance modalDialog.
 *
 * Revision 1.3  2003/03/13 18:38:43  tony
 * accessibility and column Order.
 *
 * Revision 1.2  2003/03/07 21:40:46  tony
 * Accessibility update
 *
 * Revision 1.1.1.1  2003/03/03 18:03:40  tony
 * This is the initial load of OPICM
 *
 *
 */
package com.elogin;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.accessibility.*;
import com.ibm.eannounce.dialogpanels.PrefPanel; //50468

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class FrameDialog extends JFrame implements Accessible, InterfaceDialog, WindowListener {
	private static final long serialVersionUID = 1L;
	private JFrame pFrame = null;
    private DisplayableComponent dc = null;
    private Window ownWin = null;
    private boolean bListen = false;

    /**
     * frameDialog
     * @param _parent
     * @author Anthony C. Liberto
     */
    public FrameDialog(JFrame _parent) {
        super();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        pFrame = _parent;
        //		setIconImage(_parent.getIconImage());
        init();
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
     * init
     * @author Anthony C. Liberto
     */
    public void init() {
        if (eaccess().isWindows()) {
            setResizable(false);
        }
        //		addWindowListener(this);
        return;
    }

    /**
     * @see java.awt.Window#addWindowListener(java.awt.event.WindowListener)
     * @author Anthony C. Liberto
     */
    public void addWindowListener(WindowListener _wl) {
        if (!bListen) {
            super.addWindowListener(_wl);
            bListen = true;
        }
        return;
    }

    /**
     * @see java.awt.Window#removeWindowListener(java.awt.event.WindowListener)
     * @author Anthony C. Liberto
     */
    public void removeWindowListener(WindowListener _wl) {
        if (bListen) {
            super.removeWindowListener(_wl);
            bListen = false;
        }
        return;
    }

    /**
     * repaintImmediately
     * @author Anthony C. Liberto
     */
    public void repaintImmediately() {
        update(getGraphics());
        return;
    }

    /**
     * @see java.awt.Component#hide()
     * @author Anthony C. Liberto
     */
    public void hide() {
        boolean bPop = dc.shouldResetBusy();
        dc.hideMe();
        dc = null;
        super.hide();
        moveToFront();
        if (bPop) {
            eaccess().setBusy(false);
        }
        return;
    }

    /**
     * construct
     * @author Anthony C. Liberto
     * @param _dc
     */
    public void construct(DisplayableComponent _dc) {
        setContentPane((JComponent) _dc);
        setJMenuBar(_dc.getMenuBar());
        if (!bListen) {
            addWindowListener(this);
        }
        dc = _dc;
        pack();
        return;
    }

    /**
     * show
     * @author Anthony C. Liberto
     * @param _dc
     */
    public void show(DisplayableComponent _dc) {
        construct(_dc);
        if (dc instanceof PrefPanel) { //50468
            positionPreferences(); //50468
        } else { //50468
            position();
        } //50468
        moveToFront();
        super.show();
        _dc.showMe();
        return;
    }

    private void moveToFront() {
        if (ownWin != null) {
            ownWin.toFront();
            ownWin.requestFocus(); //52768
        } else if (pFrame != null) {
            pFrame.toFront();
            pFrame.requestFocus(); //52768
        }
        return;
    }

    /**
     * position
     * @author Anthony C. Liberto
     */
    public void position() {
//        Dimension d = getPreferredSize();
//        int w = d.width;
//        int h = d.height;
        //kc_20030515		if (pFrame != null && pFrame.isShowing()) {
        //kc_20030515			Point pnt = pFrame.getLocationOnScreen();
        //kc_20030515			int x = (pnt.x + ((pFrame.getWidth() -w) / 2));
        //kc_20030515			int y = (pnt.y + ((pFrame.getHeight() -h)/ 2));
        //kc_20030515			setLocation(x,y);
        //kc_20030515		} else {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(((screenSize.width - getWidth()) / 2), ((screenSize.height - getHeight()) / 2));
        //kc_20030515		}
        return;
    }

    /**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
        ownWin = null;
        if (dc != null) {
            dc.setParentDialog(null);
            dc = null;
        }
        removeWindowListener(this);
        return;
    }

    /**
     * dereferenceFull
     * @author Anthony C. Liberto
     */
    public void dereferenceFull() {
        pFrame = null;
        removeAll();
        removeNotify();
        return;
    }

    /**
     * @see java.awt.Window#dispose()
     * @author Anthony C. Liberto
     */
    public void dispose() {
        if (isShowing()) { //51969
            super.dispose();
            dereference();
        } //51969
        return;
    }

    /**
     * setOwner
     * @param _win
     * @author Anthony C. Liberto
     */
    public void setOwner(Window _win) {
        ownWin = _win;
        return;
    }

    /**
     * @see java.awt.Frame#isResizable()
     * @author Anthony C. Liberto
     */
    public boolean isResizable() {
        if (dc != null) {
            return dc.isResizable();
        }
        return super.isResizable();
    }

    /**
     * isShowing
     * @param _dc
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isShowing(DisplayableComponent _dc) {
        boolean bShow = isShowing();
        if (dc == _dc && bShow) {
            return false;
        }
        return bShow;
    }

    /**
     * @see java.awt.Component#getMinimumSize()
     * @author Anthony C. Liberto
     */
    public Dimension getMinimumSize() {
        if (dc != null) {
            return dc.getMinimumSize();
        }
        return super.getMinimumSize();
    }

    /**
     * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
     * @author Anthony C. Liberto
     */
    public void windowActivated(WindowEvent _we) {
        if (dc != null) {
            dc.activateMe();
        }
        return;
    }

    /**
     * @see java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
     * @author Anthony C. Liberto
     */
    public void windowClosed(WindowEvent _we) {
    }
    /**
     * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
     * @author Anthony C. Liberto
     */
    public void windowClosing(WindowEvent _we) {
        if (ownWin != null) { //53373
            ownWin.validate(); //53373
        } //53373
        if (dc != null) {
            if (dc.canWindowClose()) {
                if (dc != null && dc.isShowing()) {
                    dc.disposeDialog();
                    eaccess().dereferenceDialog(this);
                }
            }
        }
        return;
    }
    /**
     * @see java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent)
     * @author Anthony C. Liberto
     */
    public void windowDeactivated(WindowEvent _we) {
    }
    /**
     * @see java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent)
     * @author Anthony C. Liberto
     */
    public void windowDeiconified(WindowEvent _we) {
    }
    /**
     * @see java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
     * @author Anthony C. Liberto
     */
    public void windowIconified(WindowEvent _we) {
    }
    /**
     * @see java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
     * @author Anthony C. Liberto
     */
    public void windowOpened(WindowEvent _we) {
    }

    /**
     * refreshAppearance
     * @author Anthony C. Liberto
     */
    public void refreshAppearance() {
        validate();
        repaint();
        return;
    }

    /*
     50468
    */
    /**
     * positionPreferences
     * @author Anthony C. Liberto
     */
    public void positionPreferences() {
        Dimension d = getPreferredSize();
        int h = d.height;
        if (pFrame != null && pFrame.isShowing()) {
            Point pnt = pFrame.getLocationOnScreen();
            int x = pnt.x + 50;
            int y = (pnt.y + ((pFrame.getHeight() - h) / 2));
            setLocation(x, y);
        } else {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            setLocation(50, ((screenSize.height - getHeight()) / 2));
        }
        return;
    }
    /*
     50924
     */
    /**
     * getSearchObject
     * @author Anthony C. Liberto
     * @return Object
     */
    public Object getSearchObject() {
        if (ownWin instanceof InterfaceDialog) {
            if (ownWin.isShowing()) { //51762
                return ((InterfaceDialog) ownWin).getSearchableObject();
            } else { //52369
                if (dc != null && dc.isShowing()) { //52369
                    dc.disposeDialog(); //52369
                    eaccess().dereferenceDialog(this); //52369
                } //52369
            } //51762
        }
        return eaccess().getSearchObject();
    }

    /**
     * getSearchableObject
     * @author Anthony C. Liberto
     * @return Object
     */
    public Object getSearchableObject() {
        if (dc != null) {
            return dc.getSearchableObject();
        }
        return null;
    }

    /*
     51830
     */
    /**
     * @see java.awt.Window#toFront()
     * @author Anthony C. Liberto
     */
    public void toFront() {
        if (getExtendedState() == ICONIFIED) {
            setExtendedState(NORMAL);
        }
        super.toFront();
        return;
    }

    /*
     accessibility
     */
    /**
     * @see java.awt.Container#getFocusTraversalPolicy()
     * @author Anthony C. Liberto
     */
    public FocusTraversalPolicy getFocusTraversalPolicy() {
        if (dc != null) {
            if (dc.hasCustomFocusPolicy()) {
                return dc.getFocusTraversalPolicy();
            }
        }
        return super.getFocusTraversalPolicy();
    }
}
