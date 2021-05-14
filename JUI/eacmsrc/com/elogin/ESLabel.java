/**
 * Copyright (c) 2001 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 * @author Anthony C. Liberto
 * @version 2.3
 *
 * $Log: ESLabel.java,v $
 * Revision 1.2  2008/01/30 16:26:59  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:42:17  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:37:38  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:58:54  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/02/02 21:30:06  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 23:18:19  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:25  tony
 * This is the initial load of OPICM
 *
 * Revision 1.1.1.1  2003/03/03 18:03:40  tony
 * This is the initial load of OPICM
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
 * Revision 1.1  2001/04/09 21:39:24  tony
 * initial download
 *
 *
 */
package com.elogin;
import javax.swing.*;
import javax.accessibility.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class ESLabel extends ELabel implements Accessible {
	private static final long serialVersionUID = 1L;
	/**
     * eSLabel
     * @param s
     * @author Anthony C. Liberto
     */
    public ESLabel(String s) {
		super(s);
		setUI(new SingleLineLabelUI());
		return;
	}

	/**
     * eSLabel
     * @param i
     * @author Anthony C. Liberto
     */
    public ESLabel(ImageIcon i) {
		super(i);
		setUI(new SingleLineLabelUI());
		return;
	}

	/**
     * eSLabel
     * @author Anthony C. Liberto
     */
    public ESLabel() {
		super();
		setUI(new SingleLineLabelUI());
		return;
	}

	/**
     * @see javax.swing.JLabel#setText(java.lang.String)
     * @author Anthony C. Liberto
     */
    public void setText(String _s) {
		super.setText(_s);
		getAccessibleContext().setAccessibleDescription(_s);
	}
}

