/**
 * Copyright (c) 2001 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 * @author Anthony C. Liberto
 * @version 1.2
 *
 * $Log: ELineBorder.java,v $
 * Revision 1.2  2008/01/30 16:26:56  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:45:47  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:17  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:59:09  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/01/28 19:36:59  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/26 18:20:08  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:49  tony
 * This is the initial load of OPICM
 *
 * Revision 1.1  2003/05/01 22:43:39  tony
 * added more passthru components
 *
 */
package com.ibm.eannounce.eobjects;
import com.elogin.*;
import java.awt.*;
import javax.swing.border.LineBorder;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class ELineBorder extends LineBorder {
	private static final long serialVersionUID = 1L;
	private String prefKey = null;
	private Color defColor = null;

	/**
     * eLineBorder
     * @param _key
     * @param _defColor
     * @param _thick
     * @author Anthony C. Liberto
     */
    public ELineBorder(String _key, Color _defColor, int _thick) {
		super(_defColor,_thick);
		if (_defColor != null) {
			defColor = new Color(_defColor.getRGB());
		}
		if (_key != null) {
			prefKey = new String(_key);
		}
		refreshAppearance();
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
     * refreshAppearance
     * @author Anthony C. Liberto
     */
    public void refreshAppearance() {
		lineColor = eaccess().getPrefColor(prefKey,defColor);
		return;
	}

	/**
     * resetAppearance
     * @author Anthony C. Liberto
     */
    public void resetAppearance() {
		lineColor = defColor;
		return;
	}

	/**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
		prefKey = null;
		defColor = null;
		lineColor = null;
		return;
	}
}
