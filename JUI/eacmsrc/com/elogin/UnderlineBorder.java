/*
 * Created on Jan 27, 2005
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 */
package com.elogin;
import javax.swing.border.*;
import java.awt.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class UnderlineBorder extends LineBorder {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * UnderlineBorder
     * @param _c
     * @author Anthony C. Liberto
     */
    public UnderlineBorder(Color _c) {
        super(_c);
        return;
    }

    /**
     * @see javax.swing.border.Border#paintBorder(java.awt.Component, java.awt.Graphics, int, int, int, int)
     * @author Anthony C. Liberto
     */
    public void paintBorder(Component _c, Graphics _g, int _x, int _y, int _width, int _height) {
        Color oldColor = _g.getColor();
        _g.translate(_x,_y);

        _g.setColor(lineColor);
        _g.drawLine(0,0,_width-1,0);

        _g.translate(-_x,-_y);
        _g.setColor(oldColor);
        return;
    }

	/**
     * @see javax.swing.border.Border#getBorderInsets(java.awt.Component)
     * @author Anthony C. Liberto
     */
    public Insets getBorderInsets(Component _c) {
//51942		return new Insets(0, 0, thickness, 0);
		return new Insets(1,1,thickness,1);									//51942
	}

    /**
     * @see javax.swing.border.AbstractBorder#getBorderInsets(java.awt.Component, java.awt.Insets)
     * @author Anthony C. Liberto
     */
    public Insets getBorderInsets(Component _c, Insets _insets) {
//51942        _insets.left = _insets.top = _insets.right = 0;
        _insets.right = 1;
        _insets.top = _insets.right;
        _insets.left = _insets.top;						//51942
        _insets.bottom = thickness;
        return _insets;
    }
}
