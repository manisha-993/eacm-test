/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: ERemotePanel.java,v $
 * Revision 1.1  2007/04/18 19:42:17  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:09  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:38  tony
 * This is the initial load of OPICM
 *
 * Revision 1.5  2005/09/08 17:58:54  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.4  2005/02/03 16:38:51  tony
 * JTest Second Pass Wrapup
 *
 * Revision 1.3  2005/02/02 21:30:08  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 23:18:17  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:25  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2003/10/29 00:22:23  tony
 * removed System.out. statements.
 *
 * Revision 1.3  2003/04/11 20:02:26  tony
 * added copyright statements.
 *
 */
package com.elogin;
import java.awt.LayoutManager;
import javax.accessibility.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public abstract class ERemotePanel extends EPanel implements Accessible {
	/**
     * eRemotePanel
     * @author Anthony C. Liberto
     */
    public ERemotePanel() {
		super();
		return;
	}

	/**
     * eRemotePanel
     * @param _b
     * @author Anthony C. Liberto
     */
    public ERemotePanel(boolean _b) {
		super(_b);
		return;
	}

	/**
     * eRemotePanel
     * @param _lm
     * @author Anthony C. Liberto
     */
    public ERemotePanel(LayoutManager _lm) {
		super(_lm);
		return;
	}

	/**
     * eRemotePanel
     * @param _lm
     * @param _b
     * @author Anthony C. Liberto
     */
    public ERemotePanel(LayoutManager _lm, boolean _b) {
		super(_lm, _b);
	}

	/**
     * actionPerformed
     * @param _action
     * @author Anthony C. Liberto
     */
    public void actionPerformed(String _action) {
		return;
	}

	/**
     * getPanelType
     * @author Anthony C. Liberto
     * @return String
     */
    public String getPanelType() {
		return TYPE_EREMOTEPANEL;
	}
}
