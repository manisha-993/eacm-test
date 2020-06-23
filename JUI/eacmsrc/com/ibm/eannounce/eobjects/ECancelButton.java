/**
 * Copyright (c) 2001 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 * @version 2.4  2001/08/09
 * @author Anthony C. Liberto
 * date 2001-08-16
 *
 * $Log: ECancelButton.java,v $
 * Revision 1.2  2008/01/30 16:26:57  wendy
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
 * Revision 1.4  2005/09/08 17:59:08  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/01/28 19:36:58  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/26 18:20:08  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:49  tony
 * This is the initial load of OPICM
 *
 * Revision 1.9  2003/10/07 21:34:33  tony
 * added setlookandfeel to assist in accessibility
 * high contrast functionality.
 *
 * Revision 1.8  2003/03/25 23:29:06  tony
 * added eDisplayable to provide an
 * easier interface into components.  This will
 * assist in ease of programming in the future.
 *
 * Revision 1.7  2003/03/24 21:52:24  tony
 * added modalCursor logic.
 *
 * Revision 1.6  2003/03/21 20:54:33  tony
 * updated getBackground, getForeground, and getFont logic.
 * added logic so that each can be set for the component.
 *
 * Revision 1.5  2003/03/18 22:39:12  tony
 * more accessibility updates.
 *
 * Revision 1.4  2003/03/12 23:51:17  tony
 * accessibility and column order
 *
 * Revision 1.3  2003/03/11 00:33:26  tony
 * accessibility changes
 *
 * Revision 1.2  2003/03/07 21:40:49  tony
 * Accessibility update
 *
 * Revision 1.1.1.1  2003/03/03 18:03:51  tony
 * This is the initial load of OPICM
 *
 * Revision 1.1.1.1  2001/11/29 19:00:19  tony
 * This is the initial load of OPICM
 *
 * Revision 1.1  2001/10/10 17:16:16  tony
 * enhanced introduction.
 *
 *
 */
package com.ibm.eannounce.eobjects;
import com.elogin.*;
import java.awt.*;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.UIManager;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class ECancelButton extends BasicArrowButton implements EAccessConstants, EDisplayable {
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
     * cancelButton
     * @author Anthony C. Liberto
     */
    public ECancelButton() {
		super(WEST);
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
     * @see javax.swing.plaf.basic.BasicArrowButton#paintTriangle(java.awt.Graphics, int, int, int, int, boolean)
     * @author Anthony C. Liberto
     */
    public void paintTriangle(Graphics g, int x, int y, int size, int direction, boolean isEnabled) {
		Color oColor = g.getColor();
		Font oFont = g.getFont();
		FontMetrics fm = null;
        int adjY = -1;
        size = Math.max(size, 2);

		if(isEnabled) {
			g.setColor(UIManager.getColor("controlDkShadow"));

		} else {
			g.setColor(UIManager.getColor("controlShadow"));
		}

		//might be able to further refine by drawing the cancel image.

		g.setFont(oFont.deriveFont(Font.BOLD));				//adjust X Bold
		fm = g.getFontMetrics();				//getMetrics for X
		adjY = fm.getHeight() / 2;						//adjustment to center X horz
		g.drawString("X",x,y+adjY);							//draw X with centering

//		g.drawLine(x,y,x+size,y+size);						//draw \
//		g.drawLine(x+size,y,x,y+size);						//draw /

		g.setColor(oColor);
		g.setFont(oFont);									//draw X
		return;
	}
}

