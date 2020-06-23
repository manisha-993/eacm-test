//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.ui;
import javax.swing.border.*;
import java.awt.*;

/**
 * selection border
 */
public class UnderlineBorder extends LineBorder {
	private static final long serialVersionUID = 1L;

	/**
     * UnderlineBorder
     * @param _c
     */
    public UnderlineBorder(Color _c) {
        super(_c);
    }

    /**
     * @see javax.swing.border.Border#paintBorder(java.awt.Component, java.awt.Graphics, int, int, int, int)
     */
    public void paintBorder(Component _c, Graphics _g, int _x, int _y, int _width, int _height) {
        Color oldColor = _g.getColor();
        _g.translate(_x,_y);

        _g.setColor(lineColor);
        _g.drawLine(0,0,_width-1,0);

        _g.translate(-_x,-_y);
        _g.setColor(oldColor);
    }

	/**
     * @see javax.swing.border.Border#getBorderInsets(java.awt.Component)
     */
    public Insets getBorderInsets(Component _c) {
		return new Insets(1,1,thickness,1);				
	}

    /**
     * @see javax.swing.border.AbstractBorder#getBorderInsets(java.awt.Component, java.awt.Insets)
     */
    public Insets getBorderInsets(Component _c, Insets _insets) {
        _insets.right = 1;
        _insets.top = _insets.right;
        _insets.left = _insets.top;					
        _insets.bottom = thickness;
        return _insets;
    }
}
