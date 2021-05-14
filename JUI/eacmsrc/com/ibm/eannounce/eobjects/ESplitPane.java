/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: ESplitPane.java,v $
 * Revision 1.2  2008/01/30 16:26:56  wendy
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
 * Revision 1.9  2005/09/08 17:59:10  tony
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
 * Revision 1.3  2004/03/30 16:30:54  tony
 * 6951 refined.
 *
 * Revision 1.2  2004/03/26 21:21:54  tony
 * cr_916036951
 *
 * Revision 1.1.1.1  2004/02/10 16:59:50  tony
 * This is the initial load of OPICM
 *
 * Revision 1.16  2003/10/29 00:12:28  tony
 * removed System.out. statements.
 *
 * Revision 1.15  2003/10/07 21:33:41  tony
 * added setlookandfeel to assist in accessibility
 * high contrast functionality.
 *
 * Revision 1.14  2003/10/06 20:06:47  tony
 * 52474
 *
 * Revision 1.13  2003/09/18 17:28:13  tony
 * 52312
 *
 * Revision 1.12  2003/08/29 16:15:48  tony
 * 51994
 *
 * Revision 1.11  2003/07/07 18:56:08  tony
 * 51399
 *
 * Revision 1.10  2003/06/16 17:22:54  tony
 * updated logic for 1.2h to allow for the application to
 * automatically select the visible navigate as current when
 * the navigate splitpane is adjusted to its minimum or
 * its maximum.
 *
 * Revision 1.9  2003/04/11 20:02:32  tony
 * added copyright statements.
 *
 */
package com.ibm.eannounce.eobjects;
import com.elogin.*;
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
public class ESplitPane extends JSplitPane implements EAccessConstants, EDisplayable {
	private static final long serialVersionUID = 1L;
	//	private boolean bSetDivLoc = true;
	/**
     * bModalCursor
     */
    private boolean bModalCursor = false;
	/**
     * bUseBack
     */
    private boolean bUseBack = true;
	/**
     * bUseFont
     */
    private boolean bUseFont = true;
	/**
     * bUseFore
     */
    private boolean bUseFore = true;
	private int FUDGE_FACTOR = 10;			//52312

	/**
     * eSplitPane
     * @author Anthony C. Liberto
     */
    public ESplitPane() {
		super();
		initAccessibility("accessible.splitPane");
		return;
	}

	/**
     * eSplitPane
     * @param _orient
     * @author Anthony C. Liberto
     */
    public ESplitPane(int _orient) {
		super(_orient);
		initAccessibility("accessible.splitPane");
		return;
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
     * getDisplayable
     *
     * @return
     * @author Anthony C. Liberto
     */
    public EDisplayable getDisplayable() {
		Container par = getParent();
		if (par instanceof EDisplayable) {
			return (EDisplayable)par;
		}
		return null;
	}

    /**
     * setModalCursor
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setModalCursor(boolean _b) {
		bModalCursor = _b;
		return;
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
		return;
	}

    /**
     * setUseBack
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setUseBack(boolean _b) {
		bUseBack = _b;
		return;
	}

    /**
     * setUseFore
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setUseFore(boolean _b) {
		bUseFore = _b;
		return;
	}

    /**
     * setUseFont
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setUseFont(boolean _b) {
		bUseFont = _b;
		return;
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

	/**
     * isMaximized
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isMaximized() {
//51399		if (getDividerLocation() == getMaximumDividerLocation())
//52312		if (getDividerLocation() >= getMaximumDividerLocation()) {	//51399
		if (getDividerLocation() >= (getMaximumDividerLocation() - FUDGE_FACTOR)) {	//52312
			return true;
		}
		return false;
	}

	/**
     * isMinimized
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isMinimized() {
//51399		if (getDividerLocation() == getMinimumDividerLocation())
//52312		if (getDividerLocation() <= getMinimumDividerLocation()) {	//51399
		if (getDividerLocation() <= (getMinimumDividerLocation() + FUDGE_FACTOR)) {	//52312
			return true;
		}
		return false;
	}

	/**
     * minimize
     * @author Anthony C. Liberto
     */
    public void minimize() {
//51399		setDividerLocation(getMinimumDividerLocation());
		setDividerLocation(0D);			//51399
		return;
	}

	/**
     * maximize
     * @author Anthony C. Liberto
     */
    public void maximize() {
//51399		setDividerLocation(getMaximumDividerLocation());
		setDividerLocation(1D);			//51399
		return;
	}

	/**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
		initAccessibility(null);
		removeAll();
		removeNotify();
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
     * getString
     * @param _code
     * @return
     * @author Anthony C. Liberto
     */
    public String getString(String _code) {
		return eaccess().getString(_code);
	}

	/**
     * appendLog
     * @param _message
     * @author Anthony C. Liberto
     */
    public void appendLog(String _message) {
		EAccess.appendLog(_message);
		return;
	}

/*
 1.2h
*/
	/**
     * @see javax.swing.JSplitPane#setDividerLocation(int)
     * @author Anthony C. Liberto
     */
    public void setDividerLocation(int _loc) {
		super.setDividerLocation(_loc);
		if (isMinimized()) {
			dividerToggle(NAVIGATE_DIVIDER_MINIMIZED);
		} else if (isMaximized()) {
			dividerToggle(NAVIGATE_DIVIDER_MAXIMIZED);
		} else {
			dividerToggle(NAVIGATE_DIVIDER_OTHER);
		}
		return;
	}

	/**
     * dividerToggle
     * @param _i
     * @author Anthony C. Liberto
     */
    public void dividerToggle(int _i) {}

/*
 51994
 */
	/**
     * @see javax.swing.JComponent#processKeyBinding(javax.swing.KeyStroke, java.awt.event.KeyEvent, int, boolean)
     * @author Anthony C. Liberto
     */
    protected boolean processKeyBinding(KeyStroke _ks, KeyEvent _ke,int _cond, boolean _press) {
		if (_ke != null) {
			int iKeyCode = _ke.getKeyCode();
			if (_ke.isControlDown()) {
			} else if (_ke.isShiftDown()) {					//52474
				if (iKeyCode == KeyEvent.VK_LEFT) {			//52474
					return false;							//52474
				}											//52474
			} else {
				if (iKeyCode == KeyEvent.VK_F8) {
					return false;
				}
			}
		}
		return super.processKeyBinding(_ks,_ke,_cond,_press);
	}

/*
 cr_916036951
 */
	/**
     * getProportionalPosition
     * @return
     * @author Anthony C. Liberto
     */
    public double getProportionalPosition() {
		int loc = getDividerLocation();
		if (loc == 0) {
			return 0D;
		}
		return (double)(loc / getMaximumDividerLocation()) ;
	}


	/**
     * @see javax.swing.JSplitPane#setDividerLocation(double)
     * @author Anthony C. Liberto
     */
    public void setDividerLocation(double _dbl) {
		int iProp = 0;
        if (_dbl >= 0.0 && _dbl <= 1.0) {
			int iAvail = 0;
			Container cont = eaccess().getTabbedPane();
			if (getOrientation() == VERTICAL_SPLIT) {
				if (cont != null) {
					iAvail = cont.getHeight() - getDividerSize();
				} else {
					iAvail = getHeight() - getDividerSize();
				}
			} else {
				if (cont != null) {
					iAvail = cont.getWidth() - getDividerSize();
				} else {
					iAvail = getWidth() - getDividerSize();
				}
			}
			iProp = (int)((iAvail * _dbl));
			setDividerLocation(iProp);
		} else {
			throw new IllegalArgumentException("location must be between 0 and 1.");
		}
		return;
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
		return;
	}
}
