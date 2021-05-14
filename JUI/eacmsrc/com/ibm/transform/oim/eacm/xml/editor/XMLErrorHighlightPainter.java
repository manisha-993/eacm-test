// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.xml.editor;

import javax.swing.text.*;
import java.awt.*;

/******************************************************************************
* This is used to highlight xml parsing errors in the error dialog.
* Painter for underlined highlights
*
* @author Wendy Stimpson
* @version 1.0
*
* Change History:
*/
// $Log: XMLErrorHighlightPainter.java,v $
// Revision 1.1  2007/04/18 19:47:48  wendy
// Reorganized JUI module
//
// Revision 1.3  2006/01/25 18:59:04  wendy
// AHE copyright
//
// Revision 1.2  2005/10/12 12:48:57  wendy
// Conform to new jtest configuration
//
// Revision 1.1.1.1  2005/09/09 20:39:19  tony
// This is the initial load of OPICM
//
//
class XMLErrorHighlightPainter extends LayeredHighlighter.LayerPainter
{
    /** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.";
    /** cvs version */
    public static final String VERSION = "$Revision: 1.1 $";
    private Color color;    // The color for the underline

    XMLErrorHighlightPainter(Color c) { color = c; }
    /************************************
     * paint
     * @param g
     * @param offs0
     * @param offs1
     * @param bounds
     * @param c
     */
    public void paint(Graphics g, int offs0, int offs1,
        Shape bounds, JTextComponent c)
    {
        // Do nothing: this method will never be called
    }
    /**************************************
     * paint
     * @param g
     * @param offs0
     * @param offs1
     * @param bounds
     * @param c
     * @param view
     * @return Shape
     */
    public Shape paintLayer(Graphics g, int offs0, int offs1,
            Shape bounds, JTextComponent c, View view)
    {
        boolean isok=true;
        int baseline;
        FontMetrics fm;
        Rectangle alloc = null;
        g.setColor(color == null ? c.getSelectionColor() : color);

        if (offs0 == view.getStartOffset() &&
            offs1 == view.getEndOffset()) {
            if (bounds instanceof Rectangle) {
                alloc = (Rectangle)bounds;
            } else {
                alloc = bounds.getBounds();
            }
        } else {
            try {
                Shape shape = view.modelToView(
                    offs0, Position.Bias.Forward,
                    offs1, Position.Bias.Backward,
                    bounds);
                alloc = (shape instanceof Rectangle) ? (Rectangle)shape : shape.getBounds();
            } catch (BadLocationException e) {
                System.out.println(e.getMessage());  // jtest req
                //return null;
                isok=false;
            }
        }

        if (isok) {
            fm = c.getFontMetrics(c.getFont());
            baseline = alloc.y + alloc.height - fm.getDescent() + 1;
            g.drawLine(alloc.x, baseline, alloc.x + alloc.width, baseline);
            g.drawLine(alloc.x, baseline + 1, alloc.x + alloc.width, baseline + 1);
        }

        return alloc;
    }
}
