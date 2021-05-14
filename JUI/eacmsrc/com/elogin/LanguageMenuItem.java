/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: LanguageMenuItem.java,v $
 * Revision 1.2  2008/01/30 16:27:00  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:42:17  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:37:39  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:58:50  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/02/02 17:30:22  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 23:18:16  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:25  tony
 * This is the initial load of OPICM
 *
 * Revision 1.1.1.1  2003/03/03 18:03:40  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2002/11/07 16:58:31  tony
 * added/adjusted copyright statement
 *
 */
package com.elogin;
import COM.ibm.opicmpdh.transactions.NLSItem;
import java.awt.event.ActionListener;
import javax.accessibility.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class LanguageMenuItem extends ERadioButtonMenuItem implements Accessible {
	private static final long serialVersionUID = 1L;
	private NLSItem m_nls = null;
	private static final String M_STRACTION = "LANGUAGEMENUITEM";

	/**
     * LanguageMenuItem
     * @param _nls
     * @param _al
     * @author Anthony C. Liberto
     */
    public LanguageMenuItem(NLSItem _nls, ActionListener _al) {
		super();
		setLanguageItem(_nls);
		m_nls = _nls;
		setActionCommand(M_STRACTION);
		if (_al != null) {
			addActionListener(_al);
		}
	}

	/**
     * getNLSItem
     * @return
     * @author Anthony C. Liberto
     */
    public NLSItem getNLSItem() {
		return m_nls;
	}

	/**
     * setLanguageItem
     * @param _nls
     * @author Anthony C. Liberto
     */
    public void setLanguageItem(NLSItem _nls) {
		setText(_nls.toString());
		return;
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
}
