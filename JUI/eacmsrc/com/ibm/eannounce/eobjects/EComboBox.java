/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: EComboBox.java,v $
 * Revision 1.3  2012/04/05 17:40:01  wendy
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
 * Revision 1.1.1.1  2005/09/09 20:38:16  tony
 * This is the initial load of OPICM
 *
 * Revision 1.8  2005/09/08 17:59:09  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.7  2005/02/09 19:29:51  tony
 * JTest After Scout
 *
 * Revision 1.6  2005/02/09 18:55:25  tony
 * Scout Accessibility
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
 * Revision 1.23  2003/10/29 19:10:43  tony
 * acl_20031029
 *
 * Revision 1.22  2003/10/27 22:18:21  tony
 * updated logic to make ACCESSIBLE_ENABLED a boolean
 * that was not computed each time but instead a constant...
 * That will allow for the app to run a bit faster.
 * Parameterized accessibility on the table with the new
 * Parm.
 *
 * Revision 1.21  2003/10/17 22:46:59  tony
 * parameterized setLookAndFeel to prevent memory leak for testing.
 *
 * Revision 1.20  2003/10/07 21:34:33  tony
 * added setlookandfeel to assist in accessibility
 * high contrast functionality.
 *
 * Revision 1.19  2003/08/14 15:35:31  tony
 * cleaned-up code.
 *
 * Revision 1.18  2003/06/20 22:33:56  tony
 * 1.2 modification.
 *
 * Revision 1.17  2003/05/07 22:13:15  tony
 * 50567
 *
 * Revision 1.16  2003/04/21 17:30:19  tony
 * updated Color Logic by adding edit and found color
 * preferences to appearance.
 *
 * Revision 1.15  2003/04/14 21:37:23  tony
 * improved lookAndFeel handling.
 *
 * Revision 1.14  2003/04/11 20:02:32  tony
 * added copyright statements.
 *
 */
package com.ibm.eannounce.eobjects;
import com.elogin.*;
import com.ibm.eannounce.ui.EComboBoxUI;
import java.awt.*;
import java.util.Vector;
import javax.swing.*;

import com.ibm.eannounce.erend.ComboBoxRenderer;
import javax.accessibility.AccessibleContext;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class EComboBox extends JComboBox implements EAccessConstants, EDisplayable {
	private static final long serialVersionUID = 1L;
	/**
     * maximumWidth
     */
    public static final int MAXIMUM_WIDTH = 500;
//	private int maxWidth = -1;
	/**
     * d
     */
    protected Dimension d =  new Dimension();
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
     * eComboBox
	 * construct a new GComboBox that has a
	 * text value of the given string
	 */
	public EComboBox () {
		super();
		init();
		return;
	}

	/**
     * eComboBox
     * @param o
     * @author Anthony C. Liberto
     */
    public EComboBox (Object[] o) {
		super(o);
		init();
		return;
	}

	/**
     * eComboBox
     * @param v
     * @author Anthony C. Liberto
     */
    public EComboBox (Vector v) {
		super(v);
		init();
		return;
	}

	/**
     * eComboBox
     * @param cbm
     * @author Anthony C. Liberto
     */
    public EComboBox (ComboBoxModel cbm) {
		super(cbm);
		init();
		return;
	}

    protected void init() {
	//not needed with metal LNF	setBorder(UIManager.getBorder("eannounce.loweredBorder"));
		initAccessibility("accessible.combo");
		setBackground(Color.white);
		 /*breaks win7
		if (eaccess().isWindows()) {
			setLookAndFeel();
		}*/
		setUI(new EComboBoxUI(this));
		createRenderer();
		return;
	}

	/**
     * refreshAppearance
     * @author Anthony C. Liberto
     */
    public void refreshAppearance() {
		FontMetrics fm = getFontMetrics(getFont());
		d.setSize(getPreferredWidth(fm),Math.max(fm.getHeight(),20));
		setSize(d);
		setPreferredSize(d);
		((EComboBoxUI)getUI()).refreshAppearance();
		validate();
		if (isShowing()) {
			getParent().validate();
		}
		return;
	}

	/**
     * getPreferredWidth
     * @param _fm
     * @return
     * @author Anthony C. Liberto
     */
    protected int getPreferredWidth(FontMetrics _fm) {
		int width = 10;
		for (int i=0;i<getItemCount();++i) {
			Object o = getItemAt(i);
			if (o instanceof String) {
				width = Math.max(width, _fm.stringWidth((String)o));
			}
		}
		return width;
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
     * getPrefColor
     * @param _code
     * @param _def
     * @return
     * @author Anthony C. Liberto
     */
    public Color getPrefColor(String _code, Color _def) {
		return eaccess().getPrefColor(_code,_def);
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
     * getString
     * @param _code
     * @return
     * @author Anthony C. Liberto
     */
    public String getString(String _code) {
		return eaccess().getString(_code);
	}

	/**
	 * set the look and feel for the GComboBox
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

/*
 50567
 */
	/**
     * getPopupWidth
     * @return
     * @author Anthony C. Liberto
     */
    public int getPopupWidth() {
		return Math.min(MAXIMUM_WIDTH,getPreferredSize().width);
	}

	/**
     * createRenderer
     * @author Anthony C. Liberto
     */
    protected void createRenderer() {
		setRenderer(new ComboBoxRenderer());
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

