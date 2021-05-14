/**
 * Copyright (c) 2001 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 * @version 2.3
 * @date 2001-06-08
 * @author Anthony C. Liberto
 *
 * $Log: RowHeaderIcon.java,v $
 * Revision 1.2  2008/01/30 16:26:56  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:45:48  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:19  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:59:10  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/01/28 19:37:00  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/26 18:20:09  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:50  tony
 * This is the initial load of OPICM
 *
 * Revision 1.1.1.1  2003/03/03 18:03:52  tony
 * This is the initial load of OPICM
 *
 * Revision 1.2  2002/06/28 19:51:05  tony
 * memory update matrix.
 *
 * Revision 1.1.1.1  2001/11/29 19:00:19  tony
 * This is the initial load of OPICM
 *
 * Revision 1.1.1.1  2001/08/06 21:39:18  tony
 * This is the initial load of OPICM
 *
 * Revision 1.1  2001/06/08 17:07:06  tony
 * initial load
 *
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
public class RowHeaderIcon extends ImageIcon {
	private static final long serialVersionUID = 1L;
	private Image asc = eaccess().getImage("asc.gif");
	private Image des = eaccess().getImage("des.gif");

	/**
     * rowHeaderIcon
     * @author Anthony C. Liberto
     */
    public RowHeaderIcon() {
		super();
		setImage("asc");
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
     * getImageIcon
     * @return
     * @author Anthony C. Liberto
     */
    public ImageIcon getImageIcon() {
		return this;
	}

	/**
     * setImage
     * @param s
     * @author Anthony C. Liberto
     */
    public void setImage(String s) {
		if (s.equals("asc")) {
			super.setImage(asc);

		} else if (s.equals("des")) {
			super.setImage(des);
		}
		return;
	}

	/**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
		asc.flush();
		asc = null;
		des.flush();
		des = null;
		return;
	}

}
