/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: ELenField.java,v $
 * Revision 1.2  2008/01/30 16:27:00  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:42:17  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:37:37  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:58:53  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/02/02 21:30:07  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 23:18:16  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:24  tony
 * This is the initial load of OPICM
 *
 * Revision 1.10  2003/04/30 21:42:28  tony
 * removed custom calls and changed to extends
 * eTextField.  code simplification.
 *
 * Revision 1.9  2003/04/11 20:02:25  tony
 * added copyright statements.
 *
 */
package com.elogin;
import com.ibm.eannounce.eobjects.ETextField;
import java.awt.*;
import javax.swing.text.*;
import javax.accessibility.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class ELenField extends ETextField implements Accessible, EAccessConstants, EDisplayable {
	private static final long serialVersionUID = 1L;
	private int maxL;

	/**
     * eLenField
     * @param _col
     * @param _max
     * @author Anthony C. Liberto
     */
    public ELenField(int _col, int _max) {
		super(_col);
		maxL = _max;
		return;
	}

	/**
     * @see javax.swing.JTextField#createDefaultModel()
     * @author Anthony C. Liberto
     */
    protected Document createDefaultModel() {
		return new MaxDoc();
	}

	private class MaxDoc extends PlainDocument {
		private static final long serialVersionUID = 1L;
		public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
			if (str == null) {
                return;
			}
			if ((getLength() + str.length()) <= maxL) {
				super.insertString(offs, str, a);
			} else {
				Toolkit.getDefaultToolkit().beep();
			}
			return;
		}
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
}

