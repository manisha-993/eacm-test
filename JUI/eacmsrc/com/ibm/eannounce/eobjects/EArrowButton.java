/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: EArrowButton.java,v $
 * Revision 1.2  2008/01/30 16:26:55  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:45:47  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:16  tony
 * This is the initial load of OPICM
 *
 * Revision 1.5  2005/09/08 17:59:08  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.4  2005/02/03 21:26:14  tony
 * JTest Format Third Pass
 *
 * Revision 1.3  2005/01/28 19:36:58  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/26 18:20:08  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:49  tony
 * This is the initial load of OPICM
 *
 * Revision 1.1.1.1  2003/03/03 18:03:52  tony
 * This is the initial load of OPICM
 *
 * Revision 1.2  2002/11/07 16:58:16  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eobjects;
import javax.swing.plaf.basic.*;
import java.awt.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class EArrowButton extends BasicArrowButton {
	private static final long serialVersionUID = 1L;
	//	private boolean traversable = false;
	/**
     * eArrowButton
     * @param _direction
     * @param _traversable
     * @author Anthony C. Liberto
     */
    public EArrowButton(int _direction, boolean _traversable) {
		super(_direction);
		setFocusable(_traversable);
	}

	/**
     * @see javax.swing.plaf.basic.BasicArrowButton#paintTriangle(java.awt.Graphics, int, int, int, int, boolean)
     * @author Anthony C. Liberto
     */
    public void paintTriangle(Graphics _g, int _x, int _y, int _size, int _dir, boolean _enabled) {
		super.paintTriangle(_g,_x,_y,_size,_dir,_enabled);
		if (hasFocus()) {
			paintFocus(_g);
		}
		return;
	}

	/**
     * paintFocus
     * @param _g
     * @author Anthony C. Liberto
     */
    protected void paintFocus(Graphics _g){
		int width = getWidth();
		int height = getHeight();
		Color oldColor = _g.getColor();
		_g.setColor(Color.black);
		BasicGraphicsUtils.drawDashedRect(_g,3,3,width-6,height-6);
		_g.setColor(oldColor);
		return;
	}

}
