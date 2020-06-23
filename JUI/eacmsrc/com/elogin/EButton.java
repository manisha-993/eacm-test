/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: EButton.java,v $
 * Revision 1.3  2012/04/05 17:28:41  wendy
 * jre142 and win7 changes
 *
 * Revision 1.2  2008/01/30 16:27:00  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:42:17  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:09  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:37  tony
 * This is the initial load of OPICM
 *
 * Revision 1.7  2005/09/08 17:58:52  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.6  2005/02/09 18:55:22  tony
 * Scout Accessibility
 *
 * Revision 1.5  2005/02/04 16:57:40  tony
 * JTest Formatter Third Pass
 *
 * Revision 1.4  2005/02/02 21:30:08  tony
 * JTest Second Pass
 *
 * Revision 1.3  2005/01/27 23:18:17  tony
 * JTest Formatting
 *
 * Revision 1.2  2004/11/19 18:01:50  tony
 * imporoved accessibilitiy and replayable log file functionatlity.
 *
 * Revision 1.1.1.1  2004/02/10 16:59:24  tony
 * This is the initial load of OPICM
 *
 * Revision 1.14  2003/10/29 19:10:40  tony
 * acl_20031029
 *
 * Revision 1.13  2003/10/27 22:18:19  tony
 * updated logic to make ACCESSIBLE_ENABLED a boolean
 * that was not computed each time but instead a constant...
 * That will allow for the app to run a bit faster.
 * Parameterized accessibility on the table with the new
 * Parm.
 *
 * Revision 1.12  2003/10/17 22:46:58  tony
 * parameterized setLookAndFeel to prevent memory leak for testing.
 *
 * Revision 1.11  2003/10/07 21:37:50  tony
 * added setlookandfeel to assist in accessibility
 * high contrast functionality.
 *
 * Revision 1.10  2003/04/14 21:37:23  tony
 * improved lookAndFeel handling.
 *
 * Revision 1.9  2003/04/11 20:02:24  tony
 * added copyright statements.
 *
 */
package com.elogin;
import java.awt.*;
import javax.accessibility.*;
import javax.swing.*;
import javax.swing.plaf.basic.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class EButton extends JButton implements EAccessConstants, Accessible, EDisplayable {
	private static final long serialVersionUID = 1L;
	private final int lnfAdjustment = -7;
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
     * construct a new GButton that has a
     * text value of the given string
     *
     * @param _s
     */
	public EButton (String _s) {
		super(_s);
		init();
		return;
	}

	/**
     * eButton
     * @param _s
     * @param _b
     * @author Anthony C. Liberto
     */
    public EButton (String _s, boolean _b) {
		super(_s);
		setEnabled(_b);
		init();
		return;
	}
	/**
	 * create a new default GButton
	 */
	public EButton () {
		super();
		init();
		return;
	}

	/**
     * create a new default GButton
     *
     * @param _img
     */
	public EButton (ImageIcon _img) {
		super(_img);
		init();
		return;
	}

	/**
     * eButton
     * @param _icon
     * @author Anthony C. Liberto
     */
    public EButton(Icon _icon) {
		super(_icon);
		init();
		return;
	}

	/**
     * eButton
     * @param _s
     * @param _img
     * @author Anthony C. Liberto
     */
    public EButton (String _s, ImageIcon _img) {
		super(_s,_img);
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

	private void init() {
//		setFocusPainted(true);
		 /*breaks win7
		if (eaccess().isWindows()) {
			setLookAndFeel();
		}*/
//		setBorder(UIManager.getBorder("eannounce.raisedBorder"));
		return;
	}

	/**
     * @see javax.swing.AbstractButton#setText(java.lang.String)
     * @author Anthony C. Liberto
     */
    public void setText(String _txt) {
		super.setText(_txt);
		getAccessibleContext().setAccessibleDescription(_txt);
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
     * @see javax.swing.JComponent#updateUI()
     * @author Anthony C. Liberto
     */
    public void updateUI() {
        setUI(new BasicButtonUI() {
                protected void paintFocus(Graphics _graphics, AbstractButton _button, Rectangle _r0, Rectangle _r1, Rectangle _r2) {
                    int w = (_button.getWidth() + lnfAdjustment);
                    int h = (_button.getHeight() + lnfAdjustment);
                    BasicGraphicsUtils.drawDashedRect(_graphics,3,3,w,h);
                    return;
                }
		});
    }

	/**
     * @see javax.swing.JComponent#grabFocus()
     * @author Anthony C. Liberto
     */
    public void grabFocus() {
		if (isFocusable()) {
			super.grabFocus();
		}
		return;
	}

	/**
     * @see java.awt.Component#requestFocus()
     * @author Anthony C. Liberto
     */
    public void requestFocus() {
		if (isFocusable()) {
			super.requestFocus();
		}
		return;
	}

	/**
     * setLookAndFeel
     * @author Anthony C. Liberto
     */
    /*breaks win7
    public void setLookAndFeel() {
		if (EAccess.isAccessible()) {
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

	/**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
		getUI().uninstallUI(this);
		removeAll();
		removeNotify();
		return;
	}
}
