/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: EViewport.java,v $
 * Revision 1.2  2008/01/30 16:26:57  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:45:48  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:17  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:38:18  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:59:10  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/01/28 19:37:00  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/26 18:20:09  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:50  tony
 * This is the initial load of OPICM
 *
 * Revision 1.6  2003/10/07 21:31:35  tony
 * added setlookandfeel to assist in accessibility
 * high contrast functionality.
 *
 * Revision 1.5  2003/09/04 20:30:09  tony
 * 52009
 *
 * Revision 1.4  2003/03/25 23:29:07  tony
 * added eDisplayable to provide an
 * easier interface into components.  This will
 * assist in ease of programming in the future.
 *
 * Revision 1.3  2003/03/24 21:52:25  tony
 * added modalCursor logic.
 *
 * Revision 1.2  2003/03/21 22:38:33  tony
 * form accessibilty update.
 *
 * Revision 1.1.1.1  2003/03/03 18:03:52  tony
 * This is the initial load of OPICM
 *
 * Revision 1.2  2002/11/07 16:58:17  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eobjects;
import com.elogin.*;
import java.awt.*;
import javax.swing.JViewport;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class EViewport extends JViewport implements EAccessConstants, EDisplayable {
	private static final long serialVersionUID = 1L;
	private boolean staticView = false;
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

	/**
     * eViewport
     * @author Anthony C. Liberto
     */
    public EViewport() {
		this(false);
	}

	/**
     * eViewport
     * @param _staticView
     * @author Anthony C. Liberto
     */
    public EViewport(boolean _staticView) {
		super();
		setStaticView(_staticView);
		setScrollMode(BACKINGSTORE_SCROLL_MODE);		//52009
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
		if (EANNOUNCE_UPDATE_FONT && bUseFont && isEnabled()) {
			return eaccess().getFont();
		}
		return super.getFont();
	}

	/**
     * setStaticView
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setStaticView(boolean _b) {
		staticView = _b;
		return;
	}

	/**
     * isStaticView
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isStaticView() {
		return staticView;
	}

	/**
     * @see javax.swing.JViewport#setViewPosition(java.awt.Point)
     * @author Anthony C. Liberto
     */
    public void setViewPosition(Point _pt) {
		if (isStaticView()) {
            return;
		}
		super.setViewPosition(_pt);
		return;
	}

	/**
     * @see javax.swing.JComponent#scrollRectToVisible(java.awt.Rectangle)
     * @author Anthony C. Liberto
     */
    public void scrollRectToVisible(Rectangle _rect) {
		if (isStaticView()) {
            return;
		}
		super.scrollRectToVisible(_rect);
		return;
	}
}
