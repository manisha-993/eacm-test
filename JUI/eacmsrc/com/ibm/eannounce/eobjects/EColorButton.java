/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: EColorButton.java,v $
 * Revision 1.2  2008/01/30 16:26:57  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:45:47  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:16  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:59:09  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/01/28 19:36:59  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/26 18:20:08  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:49  tony
 * This is the initial load of OPICM
 *
 * Revision 1.6  2003/10/08 23:11:22  tony
 * disabled color changed to gray from darkgray.
 *
 * Revision 1.5  2003/10/07 21:40:24  tony
 * added setlookandfeel to assist in accessibility
 * high contrast functionality.
 *
 * Revision 1.4  2003/04/25 22:44:08  tony
 * improved by drawing focus
 *
 * Revision 1.3  2003/04/11 20:02:32  tony
 * added copyright statements.
 *
 */
package com.ibm.eannounce.eobjects;
import com.elogin.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class EColorButton extends EButton implements EAccessConstants {
	private static final long serialVersionUID = 1L;
	private final int lnfAdjustment = -7;
    private final int colorAdjustment = -14;
    private Color selColor = null;
    private Dimension d = new Dimension(20, 20);

    /**
     * eColorButton
     * @param _s
     * @param _c
     * @author Anthony C. Liberto
     */
    public EColorButton(String _s, Color _c) {
        super();
        selColor = getPrefColor(_s, _c);
        setSize(d);
        setPreferredSize(d);
        return;
    }

    private Color getPrefColor(String _code, Color _color) {
        return eaccess().getPrefColor(_code, _color);
    }

    /**
     * @see java.awt.Component#paint(java.awt.Graphics)
     * @author Anthony C. Liberto
     */
    public void paint(Graphics _g) {
        int w = -1;
        int h = -1;
        super.paint(_g);
        w = (getWidth() + colorAdjustment);
        h = (getHeight() + colorAdjustment);
        if (isEnabled()) { //acl_20031007
            _g.setColor(selColor);
        } else { //acl_20031007
            _g.setColor(Color.gray);
        } //acl_20031007
        _g.drawRect(6, 6, w, h);
        _g.fillRect(6, 6, w, h);
        return;
    }

    /**
     * @see javax.swing.JComponent#updateUI()
     * @author Anthony C. Liberto
     */
    public void updateUI() {
        setUI(new BasicButtonUI() {
                protected void paintFocus(Graphics _g, AbstractButton _btn, Rectangle _r0, Rectangle _r1, Rectangle _r2) {
                    int w = (_btn.getWidth() + lnfAdjustment);
                    int h = (_btn.getHeight() + lnfAdjustment);
                    _g.setColor(Color.black);
                    BasicGraphicsUtils.drawDashedRect(_g, 3, 3, w, h);
                    return;
                }
        });
    }

    /**
     * getSelectedColor
     * @param _choose
     * @return
     * @author Anthony C. Liberto
     */
    public Color getSelectedColor(boolean _choose) {
        if (_choose) {
            setSelectedColor(eaccess().getColor(selColor));
        }
        return selColor;
    }

    /**
     * setSelectedColor
     * @param _c
     * @author Anthony C. Liberto
     */
    public void setSelectedColor(Color _c) {
        selColor = _c;
        return;
    }

}
