/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: EList2.java,v $
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
 * Revision 1.5  2005/09/08 17:59:09  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.4  2005/05/25 20:59:57  tony
 * multiple flag enhancement
 *
 * Revision 1.3  2005/01/28 19:36:59  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/26 18:20:08  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:49  tony
 * This is the initial load of OPICM
 *
 * Revision 1.8  2003/10/07 21:34:33  tony
 * added setlookandfeel to assist in accessibility
 * high contrast functionality.
 *
 * Revision 1.7  2003/09/08 20:44:54  tony
 * 51732
 *
 * Revision 1.6  2003/05/07 23:32:04  tony
 * 50522
 *
 * Revision 1.5  2003/04/24 15:33:12  tony
 * updated logic to include preference for selection fore and
 * back ground.
 *
 * Revision 1.4  2003/03/25 23:29:06  tony
 * added eDisplayable to provide an
 * easier interface into components.  This will
 * assist in ease of programming in the future.
 *
 * Revision 1.3  2003/03/24 21:52:24  tony
 * added modalCursor logic.
 *
 * Revision 1.2  2003/03/21 20:54:33  tony
 * updated getBackground, getForeground, and getFont logic.
 * added logic so that each can be set for the component.
 *
 * Revision 1.1  2003/03/19 20:39:51  tony
 * *** empty log message ***
 *
 * Revision 1.4  2003/03/12 23:51:18  tony
 * accessibility and column order
 *
 * Revision 1.3  2003/03/11 00:33:27  tony
 * accessibility changes
 *
 * Revision 1.2  2003/03/07 21:40:49  tony
 * Accessibility update
 *
 * Revision 1.1.1.1  2003/03/03 18:03:52  tony
 * This is the initial load of OPICM
 *
 * Revision 1.12  2002/11/20 21:24:43  tony
 * fixed index out of range
 *
 * Revision 1.11  2002/11/07 16:58:16  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eobjects;
import com.elogin.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class EList2 extends JList implements EAccessConstants, EDisplayable {
	private static final long serialVersionUID = 1L;
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
     * bModalCursor
     */
    private boolean bModalCursor = false;

	/**
     * eList2
     * @author Anthony C. Liberto
     */
    public EList2() {
		super();
		return;
	}

	/**
     * eList2
     * @param _o
     * @author Anthony C. Liberto
     */
    public EList2(Object[] _o) {
		super(_o);
		return;
	}

	/**
     * eList2
     * @param _v
     * @author Anthony C. Liberto
     */
    public EList2(Vector _v) {
		super(_v);
		return;
	}

	/**
     * eList2
     * @param _lm
     * @author Anthony C. Liberto
     */
    public EList2(ListModel _lm) {
		super(_lm);
		return;
	}


	/**
     * @see java.awt.Component#processMouseEvent(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void processMouseEvent(MouseEvent _me) {
		super.processMouseEvent(_me);
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
     * @see javax.swing.JList#getSelectionBackground()
     * @author Anthony C. Liberto
     */
    public Color getSelectionBackground() {
		return eaccess().getPrefColor(PREF_COLOR_SELECTION_BACKGROUND,DEFAULT_COLOR_SELECTION_BACKGROUND);
	}

	/**
     * @see javax.swing.JList#getSelectionForeground()
     * @author Anthony C. Liberto
     */
    public Color getSelectionForeground() {
		return eaccess().getPrefColor(PREF_COLOR_SELECTION_FOREGROUND,DEFAULT_COLOR_SELECTION_FOREGROUND);
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
     * setFixedCellHeight
     * @author Anthony C. Liberto
     */
    public void setFixedCellHeight() {
		FontMetrics fm = getFontMetrics(getFont());
//50522		setFixedCellHeight(fm.getHeight() + 4);
		Insets insets = getInsets();										//50522
		setFixedCellHeight(fm.getHeight() + insets.top + insets.bottom);	//50522
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
}
