/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: EScrollPane.java,v $
 * Revision 1.4  2012/04/05 17:42:10  wendy
 * jre142 and win7 changes
 *
 * Revision 1.3  2008/02/20 15:46:25  wendy
 * Prevent nullptr if deref called more than once
 *
 * Revision 1.2  2008/01/30 16:26:57  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:45:47  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:17  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:38:17  tony
 * This is the initial load of OPICM
 *
 * Revision 1.9  2005/09/08 17:59:09  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.8  2005/02/09 19:29:51  tony
 * JTest After Scout
 *
 * Revision 1.7  2005/02/09 18:55:25  tony
 * Scout Accessibility
 *
 * Revision 1.6  2005/02/03 21:26:14  tony
 * JTest Format Third Pass
 *
 * Revision 1.5  2005/01/28 19:36:59  tony
 * JTest Second Pass
 *
 * Revision 1.4  2005/01/26 18:20:08  tony
 * JTest Formatting
 *
 * Revision 1.3  2004/11/19 18:01:51  tony
 * imporoved accessibilitiy and replayable log file functionatlity.
 *
 * Revision 1.2  2004/08/04 17:49:15  tony
 * adjusted logic to break function parameterization into
 * arm files and a new function directory.  This way
 * we will eliminate the possibility of translation to
 * accidentally change functionality.
 *
 * Revision 1.1.1.1  2004/02/10 16:59:49  tony
 * This is the initial load of OPICM
 *
 * Revision 1.27  2003/12/31 16:57:13  tony
 * cr_3312
 *
 * Revision 1.26  2003/10/29 19:10:43  tony
 * acl_20031029
 *
 * Revision 1.25  2003/10/27 22:18:21  tony
 * updated logic to make ACCESSIBLE_ENABLED a boolean
 * that was not computed each time but instead a constant...
 * That will allow for the app to run a bit faster.
 * Parameterized accessibility on the table with the new
 * Parm.
 *
 * Revision 1.24  2003/10/17 22:47:00  tony
 * parameterized setLookAndFeel to prevent memory leak for testing.
 *
 * Revision 1.23  2003/10/07 21:33:41  tony
 * added setlookandfeel to assist in accessibility
 * high contrast functionality.
 *
 * Revision 1.22  2003/10/01 16:07:38  tony
 * 52194_rework
 *
 * Revision 1.21  2003/09/19 18:09:05  tony
 * cleaned-up code.
 *
 * Revision 1.20  2003/09/12 20:15:45  tony
 * 52194
 *
 * Revision 1.19  2003/06/10 16:46:49  tony
 * 51260
 *
 * Revision 1.18  2003/05/30 21:09:24  tony
 * updated messaging logic to popup on top
 * of the currently active Component.
 *
 * Revision 1.17  2003/05/29 21:20:44  tony
 * adjusted setParm logic for single parm string.
 *
 * Revision 1.16  2003/05/29 19:05:40  tony
 * updated report launching.
 *
 * Revision 1.15  2003/05/27 21:23:31  tony
 * updated url logic
 *
 * Revision 1.14  2003/05/20 21:35:44  tony
 * 50827
 *
 * Revision 1.13  2003/05/02 20:05:56  tony
 * 50504 -- adjusted messaging and this seems to have
 * resolved the problem in local test.
 *
 * Revision 1.12  2003/04/21 20:03:11  tony
 * enhanced scroll logic to improve flag editor.
 *
 * Revision 1.11  2003/04/14 21:37:24  tony
 * improved lookAndFeel handling.
 *
 * Revision 1.10  2003/04/11 20:02:32  tony
 * added copyright statements.
 *
 */
package com.ibm.eannounce.eobjects;
import com.elogin.*;
import com.ibm.eannounce.eforms.table.ETable;
import COM.ibm.eannounce.objects.LockList;
import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.transactions.NLSItem;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.accessibility.AccessibleContext;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class EScrollPane extends JScrollPane implements EAccessConstants, EDisplayable, MouseListener {
	private static final long serialVersionUID = 1L;
    private boolean bModalCursor = false;
    private boolean bUseBack = true;
    private boolean bUseFont = true;
    private boolean bUseFore = true;
    private boolean derefDone = false;
    
    /**
     * eScrollPane
     * @author Anthony C. Liberto
     */
    public EScrollPane() {
        super();
        init();
    }

    /**
     * eScrollPane
     * @param _c
     * @author Anthony C. Liberto
     */
    public EScrollPane(Component _c) {
        super(_c);
        init();
    }

    /**
     * eScrollPane
     * @param _a
     * @param _b
     * @author Anthony C. Liberto
     */
    public EScrollPane(int _a, int _b) {
        super(_a, _b);
        init();
    }

    /**
     * @see javax.swing.JScrollPane#isWheelScrollingEnabled()
     * @author Anthony C. Liberto
     */
    public boolean isWheelScrollingEnabled() {
        return true;
    }

    /**
     * eScrollPane
     * @param _c
     * @param _a
     * @param _b
     * @author Anthony C. Liberto
     */
    public EScrollPane(Component _c, int _a, int _b) {
        super(_c, _a, _b);
        init();
    }

    /**
     * getDisplayable
     *
     * @return
     * @author Anthony C. Liberto
     */
    public EDisplayable getDisplayable() {
        Container par = getParent();
        if (par instanceof EDisplayable) {
            return (EDisplayable) par;
        }
        return null;
    }

    /**
     * @see java.awt.Component#getCursor()
     * @author Anthony C. Liberto
     */
    public Cursor getCursor() {
        if (isModalCursor()) {
            return eaccess().getModalCursor();
        } else {
            EDisplayable disp = getDisplayable();
            if (disp != null) {
                return disp.getCursor();
            }
        }
        return eaccess().getCursor();
    }

    /**
     * setModalCursor
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setModalCursor(boolean _b) {
        bModalCursor = _b;
    }

    /**
     * isModalCursor
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isModalCursor() {
        return bModalCursor;
    }

    /**
     * setUseDefined
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setUseDefined(boolean _b) {
        setUseBack(_b);
        setUseFore(_b);
        setUseFont(_b);
    }

    /**
     * setUseBack
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setUseBack(boolean _b) {
        bUseBack = _b;
    }

    /**
     * setUseFore
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setUseFore(boolean _b) {
        bUseFore = _b;
    }

    /**
     * setUseFont
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setUseFont(boolean _b) {
        bUseFont = _b;
    }

    /**
     * @see java.awt.Component#getBackground()
     * @author Anthony C. Liberto
     */
    public Color getBackground() {
        if (eaccess().canOverrideColor() && bUseBack) {
            if (isEnabled()) {
                return eaccess().getBackground();
            } else {
                return eaccess().getDisabledBackground();
            }
        }
        return super.getBackground();
    }

    /**
     * @see java.awt.Component#getForeground()
     * @author Anthony C. Liberto
     */
    public Color getForeground() {
        if (eaccess().canOverrideColor() && bUseFore) {
            if (isEnabled()) {
                return eaccess().getForeground();
            } else {
                return eaccess().getDisabledForeground();
            }
        }
        return super.getForeground();
    }

    /**
     * @see java.awt.MenuContainer#getFont()
     * @author Anthony C. Liberto
     */
    public Font getFont() {
        if (EANNOUNCE_UPDATE_FONT && bUseFont) {
            return eaccess().getFont();
        }
        return super.getFont();
    }

    private void init() {
    	 /*breaks win7
        if (eaccess().isWindows()) {
            setLookAndFeel();
        }*/
        addMouseListener(this);
    }

    /**
     * @see javax.swing.JScrollPane#createViewport()
     * @author Anthony C. Liberto
     */
    protected JViewport createViewport() {
        EViewport view = new EViewport() {
        	private static final long serialVersionUID = 1L;
        	public void setViewPosition(Point _p) {
                super.setViewPosition(_p);
                repaint();
                return;
            }
        };
        view.setOpaque(false);
        view.addMouseListener(this);
        return view;
    }

    /**
     * isEditing
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isEditing() {
        Component c = getView();
        if (c instanceof ETable) {
            return ((ETable) c).isEditing();
        }
        return false;
    }

    /**
     * cancelEdit
     * @author Anthony C. Liberto
     */
    public void cancelEdit() {
        Component c = getView();
        if (c instanceof ETable) {
            ((ETable) c).cancelEdit();
        }
    }

    /**
     * getView
     * @return
     * @author Anthony C. Liberto
     */
    public Component getView() {
        EViewport view = getMyViewport();
        if (view != null) {
            return view.getView();
        }
        return null;
    }

    /*
    	public JScrollBar createHorizontalScrollBar() {
    		return new eScrollBar(JScrollBar.HORIZONTAL);
    	}

    	public JScrollBar createVerticalScrollBar() {
    		return new eScrollBar(JScrollBar.VERTICAL);
    	}
    */
    /**
     * setViewPosition
     * @param _x
     * @param _y
     * @author Anthony C. Liberto
     */
    public void setViewPosition(int _x, int _y) {
        setViewPosition(new Point(_x, _y));
    }

    /**
     * setViewPosition
     * @param _pt
     * @author Anthony C. Liberto
     */
    public void setViewPosition(Point _pt) {
        getViewport().setViewPosition(_pt);
    }

    /**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
    	if (derefDone){
    		return;
    	}
    	derefDone = true;
    	//getUI().uninstallUI(this) is getting nullptr if called a second time
        EViewport view = null;
        initAccessibility(null);
        view = getMyViewport();
        if (view != null) {
            view.removeMouseListener(this);
        }
        removeAll();
        removeNotify();
       	getUI().uninstallUI(this);
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
     * getString
     * @param _code
     * @return
     * @author Anthony C. Liberto
     */
    public String getString(String _code) {
        return eaccess().getString(_code);
    }

    /**
     * setMessage
     * @param _message
     * @author Anthony C. Liberto
     */
    public void setMessage(String _message) {
        eaccess().setMessage(_message);
    }

    /**
     * setCode
     * @param _code
     * @author Anthony C. Liberto
     */
    public void setCode(String _code) {
        eaccess().setCode(_code);
    }

    /**
     * setParm
     * @param _parm
     * @author Anthony C. Liberto
     */
    public void setParm(String _parm) {
        eaccess().setParm(_parm);
    }

    /**
     * showError
     * @author Anthony C. Liberto
     */
    public void showError() {
        eaccess().showError(this);
    }

    /**
     * isArmed
     * @param _code
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isArmed(String _code) {
        return EAccess.isArmed(_code);
    }

    /**
     * isTestMode
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isTestMode() {
        return EAccess.isTestMode();
    }

    /**
     * showError
     * @param _code
     * @author Anthony C. Liberto
     */
    public void showError(String _code) {
        eaccess().showError(this, _code);
    }

    /**
     * showFYI
     * @author Anthony C. Liberto
     */
    public void showFYI() {
        eaccess().showFYI(this);
    }

    /**
     * showConfirm
     * @param _buttons
     * @param _clear
     * @return
     * @author Anthony C. Liberto
     */
    public int showConfirm(int _buttons, boolean _clear) {
        return eaccess().showConfirm(this, _buttons, _clear);
    }

    /**
     * appendLog
     * @param _message
     * @author Anthony C. Liberto
     */
    public void appendLog(String _message) {
        EAccess.appendLog(_message);
    }

    /**
     * getRemoteDatabaseInterface
     * @return
     * @author Anthony C. Liberto
     */
    public RemoteDatabaseInterface getRemoteDatabaseInterface() {
        return eaccess().getRemoteDatabaseInterface();
    }

    /**
     * getOPWGID
     * @return
     * @author Anthony C. Liberto
     */
    public int getOPWGID() {
        return eaccess().getOPWGID();
    }

    /**
     * getActiveProfile
     * @return
     * @author Anthony C. Liberto
     */
    public Profile getActiveProfile() {
        return eaccess().getActiveProfile();
    }

    /**
     * getActiveNLSItem
     * @return
     * @author Anthony C. Liberto
     */
    public NLSItem getActiveNLSItem() {
        return eaccess().getActiveNLSItem();
    }

    /**
     * getLockList
     * @return
     * @author Anthony C. Liberto
     */
    public LockList getLockList() {
        return eaccess().getLockList();
    }

    /**
     * launchURL
     * @param _url
     * @author Anthony C. Liberto
     */
    public void launchURL(String _url) {
        eaccess().launchURL(_url);
    }

    /**
     * gc
     * @author Anthony C. Liberto
     */
    public void gc() {
        eaccess().gc();
    }

    /**
     * gio
     * @return
     * @author Anthony C. Liberto
     */
    public Gio gio() {
        return eaccess().gio();
    }

    /**
     * getHTML
     * @param _code
     * @return
     * @author Anthony C. Liberto
     */
    public String getHTML(String _code) {
        return eaccess().getHTML(_code);
    }

    /**
     * setEMenuBar
     * @param _bar
     * @author Anthony C. Liberto
     */
    public void setEMenuBar(EMenubar _bar) {
        eaccess().setEMenuBar(_bar);
    }

    /**
     * dBase
     * @return
     * @author Anthony C. Liberto
     */
    public DataBase dBase() {
        return eaccess().dBase();
    }

    /**
     * print
     * @param _c
     * @author Anthony C. Liberto
     */
    public void print(Component _c) {
        eaccess().print(_c);
    }

    /**
     * getPrefBoolean
     * @param _code
     * @param _def
     * @return
     * @author Anthony C. Liberto
     */
    public boolean getPrefBoolean(String _code, boolean _def) { //cr_3312
        return eaccess().getPrefBoolean(_code, _def); //cr_3312
    } //cr_3312

    /**
     * setLookAndFeel
     * @author Anthony C. Liberto
     */
    public void setLookAndFeel() {
        if (EAccess.isArmed(ACCESSIBLE_ARM_FILE)) {
            LookAndFeel lnf = UIManager.getLookAndFeel();
            try {
                UIManager.setLookAndFeel(WINDOWS_LNF);
            } catch (Exception _ex) {
                _ex.printStackTrace();
            }
            SwingUtilities.updateComponentTreeUI(this);
            try {
                UIManager.setLookAndFeel(lnf);
            } catch (Exception _ex) {
                _ex.printStackTrace();
            }
        }
    }

    /**
     * getPrefColor
     * @param _code
     * @param _def
     * @return
     * @author Anthony C. Liberto
     */
    public Color getPrefColor(String _code, Color _def) {
        return eaccess().getPrefColor(_code, _def);
    }

    /**
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mousePressed(MouseEvent _me) {
        if (isEditing()) {
            //50827			cancelEdit();
            finishEdit();
        }
    }
    /**
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseClicked(MouseEvent _me) {
    }
    /**
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseEntered(MouseEvent _me) {
    }
    /**
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseExited(MouseEvent _me) {
    }
    /**
     * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseReleased(MouseEvent _me) {
    }

    /*
     50827
    */
    /**
     * finishEdit
     * @author Anthony C. Liberto
     */
    public void finishEdit() {
        Component c = getView();
        if (c instanceof ETable) {
            ((ETable) c).finishEditing();
        }
    }

    /*
     51260
     */
    /**
     * showException
     * @param _x
     * @param _icon
     * @param _buttons
     * @return
     * @author Anthony C. Liberto
     */
    public int showException(Exception _x, int _icon, int _buttons) {
        return eaccess().showException(_x, this, _icon, _buttons);
    }
    /*
     52194
     */
    /**
     * getMyViewport
     * @return
     * @author Anthony C. Liberto
     */
    public EViewport getMyViewport() {
        return (EViewport) getViewport();
    }

    /**
     * setScrollMode
     * @param _mode
     * @author Anthony C. Liberto
     */
    public void setScrollMode(int _mode) {
        EViewport view = getMyViewport();
        if (view != null) {
            view.setScrollMode(_mode);
        }
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
    public void initAccessibility(String _s) {
		if (EAccess.isAccessible()) {
			AccessibleContext ac = getAccessibleContext();
			String strAccessible = null;
			if (ac != null) {
				if (_s == null) {
					ac.setAccessibleName(null);
					ac.setAccessibleDescription(null);
				} else {
					strAccessible = getString(_s);
					ac.setAccessibleName(strAccessible);
					ac.setAccessibleDescription(strAccessible);
				}
			}
		}
	}
}
