/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: EPasswordField.java,v $
 * Revision 1.2  2008/01/30 16:26:59  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:42:17  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:09  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:38  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:58:54  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/02/02 21:30:07  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 23:18:17  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:25  tony
 * This is the initial load of OPICM
 *
 * Revision 1.11  2003/10/07 21:36:58  tony
 * added setlookandfeel to assist in accessibility
 * high contrast functionality.
 *
 * Revision 1.10  2003/04/11 20:02:26  tony
 * added copyright statements.
 *
 */
package com.elogin;
import java.awt.*;
import javax.swing.JPasswordField;
import javax.swing.text.*;
import javax.accessibility.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class EPasswordField extends JPasswordField implements Accessible, EAccessConstants, EDisplayable {
	private static final long serialVersionUID = 1L;
	private int maxL;
	/**
     * bModalCursor
     */
    private boolean bModalCursor = false;
	/**
     * bUseBack
     */
    private boolean bUseBack = false;
	/**
     * bUseFont
     */
    private boolean bUseFont = true;
	/**
     * bUseFore
     */
    private boolean bUseFore = true;

	/**
     * ePasswordField
     * @param _col
     * @param _max
     * @author Anthony C. Liberto
     */
    public EPasswordField(int _col, int _max) {
		super(_col);
		maxL = _max;
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
     * @see java.awt.Component#getCursor()
     * @author Anthony C. Liberto
     */
    public Cursor getCursor() {
		if (isModalCursor() && eaccess().isModalBusy()) {
			return eaccess().getModalCursor();
		} else if (!eaccess().isBusy()) {
			return Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR);
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
     * @see javax.swing.JTextField#createDefaultModel()
     * @author Anthony C. Liberto
     */
    protected Document createDefaultModel() {
		return new MaxDoc();
	}

	private class MaxDoc extends PlainDocument {
		private static final long serialVersionUID = 1L;
		public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
			if (str == null) {
                return;
			}
			if ((getLength() + str.length()) <= maxL) {
				super.insertString(offs, str, a);
			} else {
				Toolkit.getDefaultToolkit().beep();
			}
			return;
		}
	}

	/**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
		removeAll();
		removeNotify();
		return;
	}

}

