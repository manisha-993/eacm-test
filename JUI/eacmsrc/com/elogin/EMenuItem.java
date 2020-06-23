/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: EMenuItem.java,v $
 * Revision 1.3  2012/04/05 17:31:50  wendy
 * jre142 and win7 changes
 *
 * Revision 1.2  2008/01/30 16:26:59  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:42:17  wendy
 * Reorganized JUI module
 *
 * Revision 1.3  2005/09/20 16:01:59  tony
 * CR092005410
 * Ability to add middleware location on the fly.
 *
 * Revision 1.2  2005/09/12 19:03:09  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:38  tony
 * This is the initial load of OPICM
 *
 * Revision 1.8  2005/09/08 17:58:53  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.7  2005/02/09 18:55:23  tony
 * Scout Accessibility
 *
 * Revision 1.6  2005/02/04 16:57:41  tony
 * JTest Formatter Third Pass
 *
 * Revision 1.5  2005/02/02 21:30:07  tony
 * JTest Second Pass
 *
 * Revision 1.4  2005/01/27 23:18:18  tony
 * JTest Formatting
 *
 * Revision 1.3  2004/11/19 18:01:50  tony
 * imporoved accessibilitiy and replayable log file functionatlity.
 *
 * Revision 1.2  2004/08/04 17:49:14  tony
 * adjusted logic to break function parameterization into
 * arm files and a new function directory.  This way
 * we will eliminate the possibility of translation to
 * accidentally change functionality.
 *
 * Revision 1.1.1.1  2004/02/10 16:59:25  tony
 * This is the initial load of OPICM
 *
 * Revision 1.15  2003/10/29 19:10:41  tony
 * acl_20031029
 *
 * Revision 1.14  2003/10/27 22:18:20  tony
 * updated logic to make ACCESSIBLE_ENABLED a boolean
 * that was not computed each time but instead a constant...
 * That will allow for the app to run a bit faster.
 * Parameterized accessibility on the table with the new
 * Parm.
 *
 * Revision 1.13  2003/10/17 22:46:58  tony
 * parameterized setLookAndFeel to prevent memory leak for testing.
 *
 * Revision 1.12  2003/10/07 21:36:59  tony
 * added setlookandfeel to assist in accessibility
 * high contrast functionality.
 *
 * Revision 1.11  2003/09/10 00:07:35  tony
 * updated 2003-09-09 to eliminate excess call
 *
 * Revision 1.10  2003/05/21 17:05:00  tony
 * 50827 -- separated out picklist from navigate
 *
 * Revision 1.9  2003/04/24 16:46:33  tony
 * updated menu logic so that it uses the preference defaults.
 *
 * Revision 1.8  2003/04/11 20:02:26  tony
 * added copyright statements.
 *
 */
package com.elogin;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicMenuItemUI;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class EMenuItem extends JMenuItem implements EAccessConstants, EDisplayable {
	private static final long serialVersionUID = 1L;
	/**
     * bModalCursor
     */
    protected boolean bModalCursor = false;
	/**
     * bUseBack
     */
    protected boolean bUseBack = true;
	/**
     * bUseFont
     */
    protected boolean bUseFont = true;
	/**
     * bUseFore
     */
    protected boolean bUseFore = true;

	/**
     * eMenuItem
     * @author Anthony C. Liberto
     */
    public EMenuItem() {
		super();
		setLookAndFeel();
		return;
	}

	/**
     * eMenuItem
     * @param _s
     * @author Anthony C. Liberto
     */
    public EMenuItem(String _s) {
		super(_s);
		setLookAndFeel();
		return;
	}

	/**
     * eaccess
     * @return
     * @author Anthony C. Liberto
     */
    public EAccess eaccess() {
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
     * @author Anthony C. Liberto
     * @param _b 
     */
    public void setModalCursor(boolean _b) {
		bModalCursor = _b;
		return;
	}

	/**
     * isModalCursor
     * @author Anthony C. Liberto
     * @return boolean
     */
    public boolean isModalCursor() {
		return bModalCursor;
	}

	/**
     * getDisplayable
     * @author Anthony C. Liberto
     * @return EDisplayable
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
     * @author Anthony C. Liberto
     * @param _b 
     */
    public void setUseDefined(boolean _b) {
		setUseBack(_b);
		setUseFore(_b);
		setUseFont(_b);
		return;
	}

	/**
     * setUseBack
     * @author Anthony C. Liberto
     * @param _b 
     */
    public void setUseBack(boolean _b) {
		bUseBack = _b;
		return;
	}

	/**
     * setUseFore
     * @author Anthony C. Liberto
     * @param _b 
     */
    public void setUseFore(boolean _b) {
		bUseFore = _b;
		return;
	}

	/**
     * setUseFont
     * @author Anthony C. Liberto
     * @param _b 
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
     * getSelectionBackground
     * @return
     * @author Anthony C. Liberto
     */
    public Color getSelectionBackground() {
		return eaccess().getPrefColor(PREF_COLOR_SELECTION_BACKGROUND,DEFAULT_COLOR_SELECTION_BACKGROUND);
	}

	/**
     * getSelectionForeground
     * @return
     * @author Anthony C. Liberto
     */
    public Color getSelectionForeground() {
		return eaccess().getPrefColor(PREF_COLOR_SELECTION_FOREGROUND,DEFAULT_COLOR_SELECTION_FOREGROUND);
	}

	/**
     * @see javax.swing.JComponent#updateUI()
     * @author Anthony C. Liberto
     */
    public void updateUI() {
//mem		super.updateUI();
		setUI(new BasicMenuItemUI() {
                public void paint(Graphics _g, JComponent _c) {
				    selectionBackground = getSelectionBackground();
				    selectionForeground = getSelectionForeground();
				    acceleratorForeground = getForeground();
				    acceleratorSelectionForeground = getSelectionForeground();
//  				disabledForeground;
                    super.paint(_g,_c);
                    return;
                }
		});
		return;
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

/*
 acl_20031007
 */
	/**
     * setLookAndFeel
     * @author Anthony C. Liberto
     */
    public void setLookAndFeel() {
    	 /*breaks win7if (EAccess.isAccessible()) {
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
		}*/
		return;
	}
}
