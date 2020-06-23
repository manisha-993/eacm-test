/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/16
 * @author Anthony C. Liberto
 *
 * $Log: ETransHandler.java,v $
 * Revision 1.2  2008/01/30 16:26:59  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:42:17  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:37:39  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:58:55  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/02/02 21:30:08  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 23:18:18  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:25  tony
 * This is the initial load of OPICM
 *
 * Revision 1.2  2003/04/16 17:40:38  tony
 * added call to super.exportToClipboard which will allow
 * backward compatibility.
 *
 * Revision 1.1  2003/04/16 17:23:56  tony
 * added eTransferHandleLogic
 *
 *
 */

package com.elogin;
import com.ibm.eannounce.eforms.table.RSTable;
import java.awt.datatransfer.*;
import javax.swing.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class ETransHandler extends TransferHandler {
	private static final long serialVersionUID = 1L;
	/**
     * eTransHandler
     * @author Anthony C. Liberto
     */
    public ETransHandler() {
		super();
		return;
	}

    /**
     * @see javax.swing.TransferHandler#exportToClipboard(javax.swing.JComponent, java.awt.datatransfer.Clipboard, int)
     * @author Anthony C. Liberto
     */
    public void exportToClipboard(JComponent _comp, Clipboard _clip, int _action) {
		if (_comp instanceof RSTable) {
			if (_action == MOVE) {
				((RSTable)_comp).cut();
			} else {
				((RSTable)_comp).copy();
			}
			return;
		}
		super.exportToClipboard(_comp,_clip,_action);
		return;
    }
}
