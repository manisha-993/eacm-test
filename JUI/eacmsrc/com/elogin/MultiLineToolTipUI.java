/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: MultiLineToolTipUI.java,v $
 * Revision 1.1  2007/04/18 19:42:18  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:37:40  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:58:50  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/02/02 17:30:22  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 23:18:17  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:26  tony
 * This is the initial load of OPICM
 *
 * Revision 1.2  2003/04/11 20:02:24  tony
 * added copyright statements.
 *
 */
package com.elogin;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JToolTip;
import javax.swing.plaf.ToolTipUI;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class MultiLineToolTipUI extends ToolTipUI {
	private String[] tip = null;
//	private int maxWidth = 0;
	private FontMetrics fm = null;
	private int h;

    /**
     * paint the tooltip to the screen
     *
     * @param c
     * @param g
     */
	public void paint(Graphics g, JComponent c) {
		Dimension d = null;
        int ii = -1;
        if (tip == null) {
			return;
		}
		d = c.getSize();
		g.setColor(c.getBackground());
		g.fillRect(0, 0, d.width, d.height);
		g.setColor(c.getForeground());
		ii = tip.length;
		for (int i=0;i<ii;i++) {
			if (tip[i] != null) {
				g.drawString(tip[i],3,(h*(i+1)));
			}
		}
		return;
	}

    /**
     * get the preferred size of the tooltip
     *
     * @return Dimension
     * @param c
     */
	public Dimension getPreferredSize(JComponent c) {
		String txt = null;
        Object[] o = null;
        int height = -1;
        if (fm == null) {
			fm = c.getFontMetrics(c.getFont());
			h = fm.getHeight();
		}
		txt = ((JToolTip)c).getTipText();
		if (!Routines.have(txt)) {
			return new Dimension();
		}
		o = Routines.splitString(txt);
		tip = (String[])o[1];
		height = h * tip.length;

		return new Dimension(fm.stringWidth((String)o[0])+30, height+5);	//15106
	}
}

