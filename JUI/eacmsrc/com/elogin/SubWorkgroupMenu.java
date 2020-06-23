/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: SubWorkgroupMenu.java,v $
 * Revision 1.2  2008/01/30 16:26:59  wendy
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
 * Revision 1.6  2005/09/08 17:58:50  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.5  2005/02/02 17:30:22  tony
 * JTest Second Pass
 *
 * Revision 1.4  2005/01/27 23:18:18  tony
 * JTest Formatting
 *
 * Revision 1.3  2004/10/28 17:35:56  tony
 * removed the tooltip to prevent a hang when selecting menu item
 * and tooltip is being displayed.
 *
 * Revision 1.2  2004/03/24 16:15:14  tony
 * accessibility.
 *
 * Revision 1.1.1.1  2004/02/10 16:59:27  tony
 * This is the initial load of OPICM
 *
 * Revision 1.10  2003/10/07 21:37:51  tony
 * added setlookandfeel to assist in accessibility
 * high contrast functionality.
 *
 * Revision 1.9  2003/04/11 20:02:24  tony
 * added copyright statements.
 *
 */
package com.elogin;
import COM.ibm.opicmpdh.middleware.Profile;
import java.awt.event.ActionListener;
import javax.accessibility.*;
import javax.swing.*;
import java.awt.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class SubWorkgroupMenu extends EMenu2 implements Accessible, EAccessConstants, EDisplayable {
	private ActionListener m_al = null;
	private static final long serialVersionUID = 1L;
	/**
     * bModalCursor
     */
//    private boolean bModalCursor = false;
	/**
     * bUseBack
     */
//    private boolean bUseBack = true;
	/**
     * bUseFont
     */
//    private boolean bUseFont = true;
	/**
     * bUseFore
     */
//    private boolean bUseFore = true;

	/**
     * SubWorkgroupMenu
     * @param _s
     * @author Anthony C. Liberto
     */
    public SubWorkgroupMenu(String _s) {
		super(_s);
		setName(_s);					//accessibility
		return;
	}

	/**
     * setActionListener
     * @param _al
     * @author Anthony C. Liberto
     */
    public void setActionListener(ActionListener _al) {
		m_al = _al;
	}

	/**
     * adjustSubItems
     * @param _pArray
     * @author Anthony C. Liberto
     */
    public void adjustSubItems(Profile[] _pArray) {
		int ii = -1;
        if (isPopupMenuVisible()) {
			setPopupMenuVisible(false);
		}
		clearSubItems();
		if (_pArray == null) {
			setEnabled(false);
			return;
		}
		ii = _pArray.length;
		if (ii == 0) {
			setEnabled(false);
		} else {
			for (int i=0;i<ii;++i) {
				addSubItem(_pArray[i]);
			}
			setEnabled(true);
		}
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
     * addSubItem
     * @param _p
     * @author Anthony C. Liberto
     */
    public void addSubItem(Profile _p) {
		add(new SubWorkgroupMenuItem(_p,getProfileInfo(_p),m_al));
		return;
	}

	private String getProfileInfo(Profile _p) {
//wk_20041028		return _p.getEnterprise() + ":" + _p.getOPWGID();
		return null;		//wk_20041028
	}

	/**
     * clearSubItems
     * @author Anthony C. Liberto
     */
    public void clearSubItems() {
		while (getItemCount() > 0) {
			JMenuItem item = getItem(0);
			item.removeActionListener(m_al);
			item.removeAll();
			remove(item);
		}
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
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
		clearSubItems();
		m_al = null;
		removeAll();
	}
}
