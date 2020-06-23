//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.preference;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.*;

/**
 * this is the color button on the color preference dialog
 * @author Wendy Stimpson
 */
// $Log: ColorButton.java,v $
// Revision 1.1  2012/09/27 19:39:15  wendy
// Initial code
//
class ColorButton extends JButton {
	private static final long serialVersionUID = 1L;
	private static final int lnfAdjustment = -7;
    private static final int colorAdjustment = -14;

    /**
     * @param a Action controls color settings
     * labelfor this button uses .putClientProperty(LABELED_BY_PROPERTY, this);
     */
    ColorButton(Action a) {
        super(a);
        Dimension d = new Dimension(20, 20);
        setSize(d);
        setPreferredSize(d);
        setText("");
    }

    /**
     * @see java.awt.Component#paint(java.awt.Graphics)
     */
    public void paint(Graphics _g) {
        int w = -1;
        int h = -1;
        super.paint(_g);
        w = (getWidth() + colorAdjustment);
        h = (getHeight() + colorAdjustment);
        if (isEnabled()) { 
            _g.setColor((Color)getAction().getValue("CURCOLOR"));
        } else { 
            _g.setColor(Color.gray);
        } 
        _g.drawRect(6, 6, w, h);
        _g.fillRect(6, 6, w, h);
    }

    protected void dereference(){
    	setAction(null); 
    	setUI(null);
    }
    /**
     * @see javax.swing.JComponent#updateUI()
     */
    public void updateUI() {
    	setUI(new BasicButtonUI() {
    		protected void paintFocus(Graphics _g, AbstractButton _btn, Rectangle _r0, Rectangle _r1, Rectangle _r2) {
    			int w = (_btn.getWidth() + lnfAdjustment);
    			int h = (_btn.getHeight() + lnfAdjustment);
    			_g.setColor(Color.black);
    			BasicGraphicsUtils.drawDashedRect(_g, 3, 3, w, h);
    		}
    	});
    }
    public void setEnabled(boolean b) {
    	super.setEnabled(b);
    	// get the labelFor this button
    	Object label = getClientProperty("labeledBy");
    	if (label instanceof JLabel){
    		((JLabel)label).setEnabled(b);
    	}
    }
}
