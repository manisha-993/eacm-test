/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: SubActionMenuItem.java,v $
 * Revision 1.2  2008/01/30 16:27:00  wendy
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
 * Revision 1.5  2005/09/08 17:58:57  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.4  2005/02/02 17:30:21  tony
 * JTest Second Pass
 *
 * Revision 1.3  2005/01/27 23:18:18  tony
 * JTest Formatting
 *
 * Revision 1.2  2004/08/04 17:49:14  tony
 * adjusted logic to break function parameterization into
 * arm files and a new function directory.  This way
 * we will eliminate the possibility of translation to
 * accidentally change functionality.
 *
 * Revision 1.1.1.1  2004/02/10 16:59:27  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2003/09/10 00:07:56  tony
 * cleaned-up code.
 *
 * Revision 1.3  2003/05/21 17:05:00  tony
 * 50827 -- separated out picklist from navigate
 *
 * Revision 1.2  2003/03/07 21:40:47  tony
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
import COM.ibm.eannounce.objects.EANActionItem;
import java.awt.event.ActionListener;
import javax.accessibility.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class SubActionMenuItem extends EMenuItem implements Accessible, SubAction {
	private static final long serialVersionUID = 1L;
	private String key = null;
	private boolean bTestMode = isTestMode();

	/**
     * subActionMenuItem
     * @param _eai
     * @param _actionCommand
     * @param _al
     * @author Anthony C. Liberto
     */
    public SubActionMenuItem(EANActionItem _eai, String _actionCommand, ActionListener _al) {
		super();
		setActionItem(_eai);
		setActionCommand(_actionCommand);
		if (_al != null) {
			addActionListener(_al);
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
     * setKey
     * @author Anthony C. Liberto
     * @param _s
     */
    public void setKey(String _s) {
		key = new String(_s);
		return;
	}

	/**
     * getKey
     *
     * @author Anthony C. Liberto
     * @return String
     */
    public String getKey() {
		return key;
	}

	/**
     * setActionItem
     * @author Anthony C. Liberto
     * @param _eai
     */
    public void setActionItem(EANActionItem _eai) {
		if (bTestMode) {
			setText(_eai.toString() + " (" + _eai.getKey() + ")");
		} else {
			setText(_eai.toString());
		}
		setKey(_eai.getKey());
		return;
	}
}
