/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2003/06/20
 * @author Anthony C. Liberto
 *
 * $Log: ComboBoxRenderer.java,v $
 * Revision 1.2  2008/01/30 16:27:02  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:46:19  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:19  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:59:11  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/01/28 17:54:20  tony
 * JTest Fromat step 2
 *
 * Revision 1.2  2005/01/26 17:43:26  tony
 * JTest Format Mods
 *
 * Revision 1.1.1.1  2004/02/10 16:59:51  tony
 * This is the initial load of OPICM
 *
 * Revision 1.1  2003/06/20 22:37:56  tony
 * 1.2h mods.
 **
 */
package com.ibm.eannounce.erend;
import com.elogin.*;
import java.awt.*;
import javax.swing.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public	class ComboBoxRenderer extends ELabel implements ListCellRenderer {
	private static final long serialVersionUID = 1L;
	/**
     * comboBoxRenderer
     * @author Anthony C. Liberto
     */
    public ComboBoxRenderer() {
		super();
		setOpaque(true);
		setUseDefined(false);
		return;
	}

	/**
     * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
     * @author Anthony C. Liberto
     */
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,	boolean cellHasFocus) {								//15573
		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}

		if (value == null) {
			setText("");
		} else {
			if (value instanceof Font) {
				setFont((Font)value);
				setText(((Font)value).getFontName());
			} else {
				setText(value.toString());
			}
		}
		return this;
	}
}
