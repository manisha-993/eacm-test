/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: SubWorkgroupMenuItem.java,v $
 * Revision 1.2  2008/01/30 16:27:01  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:42:18  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:10  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:41  tony
 * This is the initial load of OPICM
 *
 * Revision 1.5  2005/09/08 17:58:51  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.4  2005/02/04 18:16:48  tony
 * JTest Formatter Fourth Pass
 *
 * Revision 1.3  2005/02/02 17:30:22  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 23:18:16  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:27  tony
 * This is the initial load of OPICM
 *
 * Revision 1.9  2003/10/07 21:37:50  tony
 * added setlookandfeel to assist in accessibility
 * high contrast functionality.
 *
 * Revision 1.8  2003/03/25 23:29:04  tony
 * added eDisplayable to provide an
 * easier interface into components.  This will
 * assist in ease of programming in the future.
 *
 * Revision 1.7  2003/03/24 21:52:22  tony
 * added modalCursor logic.
 *
 * Revision 1.6  2003/03/21 20:54:30  tony
 * updated getBackground, getForeground, and getFont logic.
 * added logic so that each can be set for the component.
 *
 * Revision 1.5  2003/03/18 22:39:09  tony
 * more accessibility updates.
 *
 * Revision 1.4  2003/03/12 23:51:08  tony
 * accessibility and column order
 *
 * Revision 1.3  2003/03/11 00:33:22  tony
 * accessibility changes
 *
 * Revision 1.2  2003/03/07 21:40:45  tony
 * Accessibility update
 *
 * Revision 1.1.1.1  2003/03/03 18:03:41  tony
 * This is the initial load of OPICM
 *
 * Revision 1.2  2002/11/07 16:58:31  tony
 * added/adjusted copyright statement
 *
 */
package com.elogin;
import COM.ibm.opicmpdh.middleware.Profile;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.accessibility.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class SubWorkgroupMenuItem extends EMenuItem implements Accessible, EAccessConstants, EDisplayable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Profile m_prof = null;
	private static final String M_STRACTION = "SUBWORKGROUPMENUITEM";
	/**
     * bModalCursor
     */
//    private boolean bModalCursor = false;
	/**
     * bUseBack
     */
 //   private boolean bUseBack = true;
	/**
     * bUseFont
     */
//    private boolean bUseFont = true;
	/**
     * bUseFore
     */
//    private boolean bUseFore = true;

	/**
     * SubWorkgroupMenuItem
     * @param _p
     * @param _tip
     * @param _al
     * @author Anthony C. Liberto
     */
    public SubWorkgroupMenuItem(Profile _p, String _tip, ActionListener _al) {
		super();
		setWorkgroupItem(_p);
		m_prof = _p;
		if (_tip != null) {
			setToolTipText(_tip);
		}
		setActionCommand(M_STRACTION);
		if (_al != null) {
			addActionListener(_al);
		}
		return;
	}

	/**
     * eaccess
     * @author Anthony C. Liberto
     * @return EAccess
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
     * getCursor
     * @author Anthony C. Liberto
     * @return Cursor
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
     * getBackground
     * @author Anthony C. Liberto
     * @return Color
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
     * getForeground
     * @author Anthony C. Liberto
     * @return Color
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
     * getFont
     * @author Anthony C. Liberto
     * @return Font
     */
    public Font getFont() {
		if (EANNOUNCE_UPDATE_FONT && bUseFont) {
			return eaccess().getFont();
		}
		return super.getFont();
	}

	/**
     * getProfile
     * @return
     * @author Anthony C. Liberto
     */
    public Profile getProfile() {
		return m_prof;
	}

	/**
     * @see javax.swing.AbstractButton#setText(java.lang.String)
     * @author Anthony C. Liberto
     */
    public void setText(String _s) {
		super.setText(_s);
		getAccessibleContext().setAccessibleDescription(_s);
		return;
	}

	/**
     * setWorkgroupItem
     * @param _p
     * @author Anthony C. Liberto
     */
    public void setWorkgroupItem(Profile _p) {
		setText(_p.toString());
		return;
	}
}
