/**
 * Copyright (c) 2001 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 * @author Anthony C. Liberto
 * @version 2.3
 *
 * $Log: ETitledBorder.java,v $
 * Revision 1.2  2008/01/30 16:26:56  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:45:48  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:18  tony
 * This is the initial load of OPICM
 *
 * Revision 1.6  2005/09/08 17:59:10  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.5  2005/02/08 16:33:58  tony
 * JTest Formatting Fourth pass
 *
 * Revision 1.4  2005/02/03 21:26:14  tony
 * JTest Format Third Pass
 *
 * Revision 1.3  2005/01/28 19:36:59  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/26 18:20:09  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:50  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2003/10/07 21:32:24  tony
 * added setlookandfeel to assist in accessibility
 * high contrast functionality.
 *
 * Revision 1.3  2003/04/02 00:52:57  tony
 * 50325
 *
 * Revision 1.2  2003/03/18 22:39:12  tony
 * more accessibility updates.
 *
 * Revision 1.1.1.1  2003/03/03 18:03:52  tony
 * This is the initial load of OPICM
 *
 * Revision 1.2  2002/01/22 01:21:13  tony
 * e-announce1.1 development, added TitledBorder and
 * fixed unlock logic to inquire about save.
 *
 * Revision 1.1.1.1  2001/11/29 19:00:19  tony
 * This is the initial load of OPICM
 *
 * Revision 1.1.1.1  2001/08/06 21:39:18  tony
 * This is the initial load of OPICM
 *
 * Revision 1.1.1.1  2001/04/19 00:58:44  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2001/03/22 22:17:58  tony
 * adjusted log information to remove duplicates
 *
 * Revision 1.3  2001/03/22 21:09:56  tony
 * Added standard copyright to all
 * modules
 *
 * Revision 1.2  2001/03/22 18:54:32  tony
 * added log keyword
 *
 */
package com.ibm.eannounce.eobjects;
import com.elogin.*;
import javax.swing.border.*;
import java.awt.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class ETitledBorder extends TitledBorder implements EAccessConstants {
	private static final long serialVersionUID = 1L;
	/**
     * b
     */
    private ELineBorder b = null;
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
     * eTitledBorder
     * @param _s
     * @param _titleColor
     * @param _borderColor
     * @param _width
     * @author Anthony C. Liberto
     */
    public ETitledBorder(String _s, Color _titleColor, Color _borderColor, int _width) {
		super(_s);
		setTitleColor(_titleColor);
		b = new ELineBorder(_borderColor, _width);
		setBorder(b);
		return;
	}

	/**
     * eTitledBorder
     * @param _s
     * @param _titleColor
     * @param _borderColor
     * @author Anthony C. Liberto
     */
    public ETitledBorder(String _s, Color _titleColor, Color _borderColor) {
		this(_s, _titleColor, _borderColor, 1);
	}

	/**
     * eTitledBorder
     * @param _s
     * @param _titleColor
     * @author Anthony C. Liberto
     */
    public ETitledBorder(String _s, Color _titleColor) {
		this(_s,_titleColor,Color.black);
		return;
	}

	/**
     * eTitledBorder
     * @param _s
     * @author Anthony C. Liberto
     */
    public ETitledBorder(String _s) {
		this(_s,Color.black);
	}

	/**
     * eTitledBorder
     * @author Anthony C. Liberto
     */
    public ETitledBorder() {
		this("");
	}

	/**
     * @see javax.swing.border.TitledBorder#setTitleFont(java.awt.Font)
     * @author Anthony C. Liberto
     */
    public void setTitleFont(Font _f) {
		super.setTitleFont(_f);
		return;
	}

	/**
     * @see javax.swing.border.TitledBorder#setTitleColor(java.awt.Color)
     * @author Anthony C. Liberto
     */
    public void setTitleColor(Color _c) {
		super.setTitleColor(_c);
		return;
	}

	/**
     * setBorderColor
     * @param _c
     * @author Anthony C. Liberto
     */
    public void setBorderColor(Color _c) {
		b.setLineColor(_c);
		return;
	}

	private class ELineBorder extends LineBorder {
		private static final long serialVersionUID = 1L;
		/**
         * eLineBorder
         * @param _c
         * @param _thick
         * @author Anthony C. Liberto
         */
        private ELineBorder(Color _c, int _thick) {
			super(_c,_thick);
			return;
		}

		/**
         * @see javax.swing.border.LineBorder#getLineColor()
         * @author Anthony C. Liberto
         */
        public Color getLineColor() {
			return lineColor;
		}

		/**
         * @see javax.swing.border.Border#paintBorder(java.awt.Component, java.awt.Graphics, int, int, int, int)
         * @author Anthony C. Liberto
         */
        public void paintBorder(Component _c, Graphics _g, int _x, int _y, int _width, int _height) {	//50325
			lineColor = getTitleColor();																//50325
			super.paintBorder(_c,_g,_x,_y,_width,_height);												//50325
			return;																						//50325
		}																								//50325

		/**
         * setLineColor
         * @param _c
         * @author Anthony C. Liberto
         */
        public void setLineColor(Color _c) {
			lineColor = _c;
			return;
		}
	}

/*
added for 50325
*/
	/**
     * setModalCursor
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setModalCursor(boolean _b) {
		bModalCursor = _b;
		return;
	}

	/**
     * isModalCursor
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isModalCursor() {
		return bModalCursor;
	}

	/**
     * getDisplayable
     * @return
     * @author Anthony C. Liberto
     */
    public EDisplayable getDisplayable() {
		return null;
	}

	/**
     * setUseDefined
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
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setUseBack(boolean _b) {
		bUseBack = _b;
		return;
	}

	/**
     * setUseFore
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setUseFore(boolean _b) {
		bUseFore = _b;
		return;
	}

	/**
     * setUseFont
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setUseFont(boolean _b) {
		bUseFont = _b;
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
     * @see javax.swing.border.TitledBorder#getTitleColor()
     * @author Anthony C. Liberto
     */
    public Color getTitleColor() {
		if (eaccess().canOverrideColor() && bUseFore) {
			return eaccess().getForeground();
		}
		return super.getTitleColor();
	}

	/**
     * @see javax.swing.border.TitledBorder#getTitleFont()
     * @author Anthony C. Liberto
     */
    public Font getTitleFont() {
		if (EANNOUNCE_UPDATE_FONT && bUseFont) {
			return eaccess().getFont();
		}
		return super.getTitleFont();
	}
}
