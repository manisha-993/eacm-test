/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: EMToolTip.java,v $
 * Revision 1.2  2008/01/30 16:27:01  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:42:17  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:37:38  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:58:53  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/02/02 21:30:07  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 23:18:19  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:25  tony
 * This is the initial load of OPICM
 *
 * Revision 1.2  2003/04/11 20:02:25  tony
 * added copyright statements.
 *
 */
package com.elogin;
import javax.swing.JToolTip;
import javax.accessibility.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class EMToolTip extends JToolTip implements Accessible {
	private static final long serialVersionUID = 1L;
	/**
     * eMToolTip
     * @author Anthony C. Liberto
     */
    public EMToolTip() {
		setUI(new MultiLineToolTipUI());
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

	/**
     * @see javax.swing.JToolTip#setTipText(java.lang.String)
     * @author Anthony C. Liberto
     */
    public void setTipText(String _s) {
		super.setTipText(_s);
		getAccessibleContext().setAccessibleDescription(_s);
		return;
	}
}

