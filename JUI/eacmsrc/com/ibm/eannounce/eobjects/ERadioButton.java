/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: ERadioButton.java,v $
 * Revision 1.3  2012/04/05 17:41:20  wendy
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
 * Revision 1.1.1.1  2005/09/09 20:38:17  tony
 * This is the initial load of OPICM
 *
 * Revision 1.6  2005/09/08 17:59:09  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.5  2005/02/03 21:26:14  tony
 * JTest Format Third Pass
 *
 * Revision 1.4  2005/01/28 19:36:59  tony
 * JTest Second Pass
 *
 * Revision 1.3  2005/01/26 18:20:08  tony
 * JTest Formatting
 *
 * Revision 1.2  2004/11/19 18:01:51  tony
 * imporoved accessibilitiy and replayable log file functionatlity.
 *
 * Revision 1.1.1.1  2004/02/10 16:59:49  tony
 * This is the initial load of OPICM
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
 * Revision 1.12  2003/10/17 22:46:59  tony
 * parameterized setLookAndFeel to prevent memory leak for testing.
 *
 * Revision 1.11  2003/10/07 21:34:32  tony
 * added setlookandfeel to assist in accessibility
 * high contrast functionality.
 *
 * Revision 1.10  2003/04/14 21:37:23  tony
 * improved lookAndFeel handling.
 *
 * Revision 1.9  2003/04/11 20:02:32  tony
 * added copyright statements.
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
public class ERadioButton extends JRadioButton implements EAccessConstants, EDisplayable {
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
//
// Constructors
//
	/**
     * eRadioButton
     * @author Anthony C. Liberto
     */
    public ERadioButton () {
		super();
		init();
		return;
	}

	/**
     * eRadioButton
     * @param s
     * @author Anthony C. Liberto
     */
    public ERadioButton (String s) {
		super(s);
		init();
		return;
	}

	/**
     * eRadioButton
     * @param a
     * @author Anthony C. Liberto
     */
    public ERadioButton (Action a) {
		super(a);
		init();
		return;
	}

	/**
     * eRadioButton
     * @param i
     * @author Anthony C. Liberto
     */
    public ERadioButton (Icon i) {
		super(i);
		init();
		return;
	}

	/**
     * eRadioButton
     * @param s
     * @param i
     * @author Anthony C. Liberto
     */
    public ERadioButton (String s,Icon i) {
		super(s,i);
		init();
		return;
	}
	/**
     * eRadioButton
     * @param s
     * @param b
     * @author Anthony C. Liberto
     */
    public ERadioButton (String s, boolean b) {
		super(s,b);
		init();
		return;
	}
	/**
     * eRadioButton
     * @param i
     * @param b
     * @author Anthony C. Liberto
     */
    public ERadioButton (Icon i, boolean b) {
		super(i,b);
		init();
		return;
	}
	/**
     * eRadioButton
     * @param s
     * @param i
     * @param b
     * @author Anthony C. Liberto
     */
    public ERadioButton (String s, Icon i, boolean b) {
		super(s,i,b);
		init();
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
     * init
     * @author Anthony C. Liberto
     */
    public void init() {
		setOpaque(false);
//		if (eaccess().isWindows()) {
//			setLookAndFeel();
//		}
		return;
	}
	/**
	 * set the look and feel for the GRadioButton
	 * to the windows look and feel
	 * /
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
}

