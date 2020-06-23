/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: EMLabel.java,v $
 * Revision 1.2  2008/01/30 16:27:00  wendy
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
 * Revision 1.6  2005/09/08 17:58:53  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.5  2005/05/25 18:15:42  tony
 * silverBulletReload
 *
 * Revision 1.4  2005/02/04 18:16:48  tony
 * JTest Formatter Fourth Pass
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
 * Revision 1.3  2003/10/07 21:37:50  tony
 * added setlookandfeel to assist in accessibility
 * high contrast functionality.
 *
 * Revision 1.2  2003/04/11 20:02:25  tony
 * added copyright statements.
 *
 */
package com.elogin;
import java.awt.Font;
import javax.swing.*;
import javax.accessibility.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class EMLabel extends ELabel implements Accessible {
	private static final long serialVersionUID = 1L;
	private int iHeight = 0;
	private boolean bUseTrueType = false;
	private Font fTrueType =  new Font("Courier New", 0, 12);

	/**
     * eMLabel
     * @param _s
     * @author Anthony C. Liberto
     */
    public EMLabel(String _s) {
		super(_s);
//		setUI(new multiLineLabelUI());
		return;
	}

	/**
     * eMLabel
     * @param _img
     * @author Anthony C. Liberto
     */
    public EMLabel(ImageIcon _img) {
		super(_img);
//		setUI(new multiLineLabelUI());
		return;
	}

	/**
     * eMLabel
     * @author Anthony C. Liberto
     */
    public EMLabel() {
		super();
//		setUI(new multiLineLabelUI());
		return;
	}

    /**
     * @see javax.swing.JComponent#updateUI()
     * @author Anthony C. Liberto
     */
    public void updateUI() {
        setUI(new MultiLineLabelUI());
        return;
    }


	/**
     * @see javax.swing.JLabel#setText(java.lang.String)
     * @author Anthony C. Liberto
     */
    public void setText(String _s) {
		super.setText(_s);
		getAccessibleContext().setAccessibleDescription(_s);
		return;
	}

	/**
     * getLabelHeight
     * @return
     * @author Anthony C. Liberto
     */
    public int getLabelHeight() {
		return iHeight;
	}

	/**
     * setLabelHeight
     * @param _i
     * @author Anthony C. Liberto
     */
    public void setLabelHeight(int _i) {
		iHeight = _i;
	}

    /**
     * @see javax.swing.JComponent#createToolTip()
     * @author Anthony C. Liberto
     */
    public JToolTip createToolTip() {
		EMToolTip tip = new EMToolTip();
		tip.setComponent(this);
		return tip;
	}

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

	/**
     * use when desire a true type font display
     *
     * @author tony
     * @param _b 
     */
	public void setUseTrueTypeFont(boolean _b) {
		bUseTrueType = _b;
		return;
	}
	/**
     * over ride for true type font
     *
     * @return Font
     */
	public Font getFont() {
		if (bUseTrueType) {
			return fTrueType;
		}
		return super.getFont();
	}
}

