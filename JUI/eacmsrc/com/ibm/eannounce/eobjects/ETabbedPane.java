/**
 * Copyright (c) 2001 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 * @author Anthony C. Liberto
 * @version 2.3
 *
 * $Log: ETabbedPane.java,v $
 * Revision 1.3  2012/04/05 17:43:37  wendy
 * jre142 and win7 changes
 *
 * Revision 1.2  2008/01/30 16:26:56  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:45:47  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:17  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:38:18  tony
 * This is the initial load of OPICM
 *
 * Revision 1.7  2005/09/08 17:59:10  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.6  2005/02/03 21:26:14  tony
 * JTest Format Third Pass
 *
 * Revision 1.5  2005/01/28 19:36:59  tony
 * JTest Second Pass
 *
 * Revision 1.4  2005/01/26 18:20:09  tony
 * JTest Formatting
 *
 * Revision 1.3  2004/11/19 18:01:51  tony
 * imporoved accessibilitiy and replayable log file functionatlity.
 *
 * Revision 1.2  2004/11/16 17:28:35  tony
 * USRO-R-CRES-66ERD3
 *
 * Revision 1.1.1.1  2004/02/10 16:59:50  tony
 * This is the initial load of OPICM
 *
 * Revision 1.16  2004/01/16 18:40:09  tony
 * 53516
 * updated tab logic to alter tab color when necessary.
 *
 * Revision 1.15  2004/01/08 17:40:55  tony
 * 53516
 *
 * Revision 1.14  2003/10/29 19:10:43  tony
 * acl_20031029
 *
 * Revision 1.13  2003/10/27 22:18:21  tony
 * updated logic to make ACCESSIBLE_ENABLED a boolean
 * that was not computed each time but instead a constant...
 * That will allow for the app to run a bit faster.
 * Parameterized accessibility on the table with the new
 * Parm.
 *
 * Revision 1.12  2003/10/17 22:47:00  tony
 * parameterized setLookAndFeel to prevent memory leak for testing.
 *
 * Revision 1.11  2003/10/07 21:33:14  tony
 * added setlookandfeel to assist in accessibility
 * high contrast functionality.
 *
 * Revision 1.10  2003/04/18 20:10:29  tony
 * added tab placement to preferences.
 *
 * Revision 1.9  2003/04/11 20:47:18  tony
 * improved Windows logic.
 *
 * Revision 1.8  2003/03/25 23:29:06  tony
 * added eDisplayable to provide an
 * easier interface into components.  This will
 * assist in ease of programming in the future.
 *
 * Revision 1.7  2003/03/24 21:52:24  tony
 * added modalCursor logic.
 *
 * Revision 1.6  2003/03/21 20:54:34  tony
 * updated getBackground, getForeground, and getFont logic.
 * added logic so that each can be set for the component.
 *
 * Revision 1.5  2003/03/18 22:39:12  tony
 * more accessibility updates.
 *
 * Revision 1.4  2003/03/12 23:51:20  tony
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
 * Revision 1.2  2002/02/13 15:55:35  tony
 * adjusted trace statements.
 *
 * Revision 1.1.1.1  2001/11/29 19:00:19  tony
 * This is the initial load of OPICM
 *
 * Revision 1.1.1.1  2001/08/06 21:39:18  tony
 * This is the initial load of OPICM
 *
 * Revision 1.3  2001/06/05 21:25:46  tony
 * added back in a commented out section
 *
 * Revision 1.2  2001/06/05 17:35:00  tony
 * adjusted tab appearance
 *
 * Revision 1.1.1.1  2001/04/19 00:58:44  tony
 * This is the initial load of OPICM
 *
 * Revision 1.5  2001/03/30 00:36:07  tony
 * refined logic for Macintosh implementation.
 *
 * Revision 1.4  2001/03/22 22:17:58  tony
 * adjusted log information to remove duplicates
 *
 * Revision 1.3  2001/03/22 21:09:55  tony
 * Added standard copyright to all
 * modules
 *
 * Revision 1.2  2001/03/22 18:54:32  tony
 * added log keyword
 *
 */
package com.ibm.eannounce.eobjects;
import com.elogin.*;
import java.awt.*;
import javax.swing.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class ETabbedPane extends JTabbedPane implements EAccessConstants, EDisplayable {
	private static final long serialVersionUID = 1L;
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
     * eTabbedPane
     * @author Anthony C. Liberto
     */
    public ETabbedPane() {
		super();
		 /*breaks win7	setLookAndFeel();*/
		updateTabPlacement(false);
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
     * updateTabPlacement
     * @param _revalidate
     * @author Anthony C. Liberto
     */
    public void updateTabPlacement(boolean _revalidate) {
		setTabPlacement(eaccess().getPrefInt(PREF_TAB_LAYOUT, SwingConstants.TOP));
		if (_revalidate) {
			revalidate();
		}
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
		if (EANNOUNCE_UPDATE_FONT && bUseFont) {
			return eaccess().getFont();
		}
		return super.getFont();
	}

	/**
     * @see javax.swing.JTabbedPane#setSelectedIndex(int)
     * @author Anthony C. Liberto
     */
    public void setSelectedIndex(int i) {
		int max = getTabCount() - 1;
		if (i > max) {
			i = max;
		}
		super.setSelectedIndex(i);
		return;
	}

	/**
     * isWindows
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean isWindows() {
		return eaccess().isWindows();
	}

/*
 acl_20031007
 */
	/**
     * setLookAndFeel
     * @author Anthony C. Liberto
     */
    /*breaks win7
    public void setLookAndFeel() {
		if (EAccess.isArmed(ACCESSIBLE_ARM_FILE)) {
			LookAndFeel lnf = UIManager.getLookAndFeel();
			try {
				UIManager.setLookAndFeel(WINDOWS_LNF);
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			SwingUtilities.updateComponentTreeUI(this);

			try {
				UIManager.setLookAndFeel(lnf);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return;
	}*/

/*
 53516
 ********
 * NOTE *
 *      ***********************************************************************
 * comment in when 53516 is approved                                          *
 * code in eLogin.java tagged with comment out for 53516 needs to be removed. *
 ******************************************************************************

	public Color getBackgroundAt(int _index) {
		Color out = super.getBackgroundAt(_index);
		if (eaccess().canOverrideColor() && !isSelectedIndex(_index)) {
			out = out.darker();
		}
		return out;
	}

	public boolean isSelectedIndex(int _i) {
		return getSelectedIndex() == _i;
	}
 */

/*
 USRO-R-CRES-66ERD3
 */
	/**
     * @see javax.swing.JTabbedPane#getBackgroundAt(int)
     * @author Anthony C. Liberto
     */
    public Color getBackgroundAt(int _index) {
		if (_index >= 0 && _index < getTabCount()) {
			Color out = super.getBackgroundAt(_index);
			if (out != null) {
				if (!isSelectedIndex(_index)) {
					out = out.darker();
				}
				return out;
			}
		}
		return DEFAULT_COLOR_ENABLED_BACKGROUND;
	}

	/**
     * isSelectedIndex
     * @param _i
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isSelectedIndex(int _i) {
		return getSelectedIndex() == _i;
	}
}

