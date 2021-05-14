/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: EmptySeparator.java,v $
 * Revision 1.2  2008/01/30 16:27:03  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:45:24  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:15  tony
 * This is the initial load of OPICM
 *
 * Revision 1.5  2005/09/08 17:59:08  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.4  2005/02/04 18:16:49  tony
 * JTest Formatter Fourth Pass
 *
 * Revision 1.3  2005/01/31 20:47:49  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/26 22:17:58  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2004/02/10 16:59:48  tony
 * This is the initial load of OPICM
 *
 * Revision 1.1.1.1  2003/03/03 18:03:51  tony
 * This is the initial load of OPICM
 *
 * Revision 1.2  2002/11/07 16:58:36  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.toolbar;
import javax.swing.JSeparator;
import javax.swing.JComponent;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.plaf.basic.BasicSeparatorUI;
import javax.swing.plaf.ComponentUI;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class EmptySeparator extends JSeparator implements SwingConstants {
	private static final long serialVersionUID = 1L;
	private Dimension size = new Dimension(6,6);

	/**
     * emptySeparator
     * @author Anthony C. Liberto
     */
    public EmptySeparator(){
		super();
		setSize(size);
		setMinimumSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
		return;
	}

    /**
     * @see javax.swing.JComponent#updateUI()
     * @author Anthony C. Liberto
     */
    public void updateUI() {
        setUI(new MySepUI());
        invalidate();
    }

	private static class MySepUI extends BasicSeparatorUI {
		/**
         * createUI
         * @param _c
         * @return
         * @author Anthony C. Liberto
         */
        public static ComponentUI createUI(JComponent _c) {
			return new MySepUI();
		}

		/**
         * @see javax.swing.plaf.ComponentUI#paint(java.awt.Graphics, javax.swing.JComponent)
         * @author Anthony C. Liberto
         */
        public void paint(Graphics _g, JComponent _c) {
			return;
		}
	}
}
