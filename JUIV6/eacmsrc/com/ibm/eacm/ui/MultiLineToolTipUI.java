//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2010  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.ui;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JToolTip;
import javax.swing.plaf.ToolTipUI;

import com.ibm.eacm.objects.Routines;

/**
 * render tooltip as multiline
 * @author Wendy Stimpson
 */
//$Log: MultiLineToolTipUI.java,v $
//Revision 1.1  2012/09/27 19:39:11  wendy
//Initial code
//
public class MultiLineToolTipUI extends ToolTipUI {
	private String[] tip = null;
	private FontMetrics fm = null;
	private int h;

    /**
     * paint the tooltip to the screen
     *
     * @param c
     * @param g
     */
	public void paint(Graphics g, JComponent c) {
		if (tip != null) {
			Dimension d = c.getSize();
			g.setColor(c.getBackground());
			g.fillRect(0, 0, d.width, d.height);
			g.setColor(c.getForeground());
			for (int i=0;i<tip.length;i++) {
				if (tip[i] != null) {
					g.drawString(tip[i],3,(h*(i+1)));
				}
			}
		}
	}

    /**
     * get the preferred size of the tooltip
     *
     * @return Dimension
     * @param c
     */
	public Dimension getPreferredSize(JComponent c) {
		if(c==null || c.getFont()==null){
			return super.getPreferredSize(c);
		}
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

		return new Dimension(fm.stringWidth((String)o[0])+30, height+5);
	}
}

