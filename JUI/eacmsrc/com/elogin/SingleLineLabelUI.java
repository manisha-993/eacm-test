/**
 * Copyright (c) 2001 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 * create a label that allows for the display of single lines of text
 * author unknown
 *
 * $Log: SingleLineLabelUI.java,v $
 * Revision 1.1  2007/04/18 19:42:18  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:37:41  tony
 * This is the initial load of OPICM
 *
 * Revision 1.5  2005/09/08 17:58:57  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.4  2005/02/04 16:57:41  tony
 * JTest Formatter Third Pass
 *
 * Revision 1.3  2005/02/02 17:30:22  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 23:18:18  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:26  tony
 * This is the initial load of OPICM
 *
 * Revision 1.1.1.1  2003/03/03 18:03:40  tony
 * This is the initial load of OPICM
 *
 * Revision 1.2  2002/10/23 22:47:31  tony
 * 22563
 *
 * Revision 1.1.1.1  2001/11/29 19:00:17  tony
 * This is the initial load of OPICM
 *
 * Revision 1.1.1.1  2001/08/06 21:39:20  tony
 * This is the initial load of OPICM
 *
 * Revision 1.1.1.1  2001/04/19 00:59:13  tony
 * This is the initial load of OPICM
 *
 * Revision 1.1  2001/04/09 21:39:23  tony
 * initial download
 *
 *
 */
package com.elogin;
import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class SingleLineLabelUI extends BasicLabelUI {
    static {
        labelUI = new SingleLineLabelUI();
    }

    /**
     * layout the componet information
     *
     * @return String
     * @param fontMetrics
     * @param icon
     * @param iconR
     * @param label
     * @param text
     * @param textR
     * @param viewR
     */
    protected String layoutCL(JLabel label, FontMetrics fontMetrics, String text, Icon icon, Rectangle viewR, Rectangle iconR, Rectangle textR) {
        String s = layoutCompoundLabel(label, fontMetrics, text, icon, label.getVerticalAlignment(), label.getHorizontalAlignment(), label.getVerticalTextPosition(), label.getHorizontalTextPosition(), viewR, iconR, textR, label.getIconTextGap());
        if (s.equals("")) {
            return text;
        }
        return s;
    }

    static final int LEADING = SwingConstants.LEADING;
    static final int TRAILING = SwingConstants.TRAILING;
    static final int LEFT = SwingConstants.LEFT;
    static final int RIGHT = SwingConstants.RIGHT;
    static final int TOP = SwingConstants.TOP;
    static final int CENTER = SwingConstants.CENTER;

    /**
     * Compute and return the location of the icons origin, the
     * location of origin of the text baseline, and a possibly clipped
     * version of the compound labels string.  Locations are computed
     * relative to the viewR rectangle.
     * The JComponents orientation (LEADING/TRAILING) will also be taken
     * into account and translated into LEFT/RIGHT values accordingly.
     *
     * @return String
     * @param c
     * @param fm
     * @param horizontalAlignment
     * @param horizontalTextPosition
     * @param icon
     * @param iconR
     * @param text
     * @param textIconGap
     * @param textR
     * @param verticalAlignment
     * @param verticalTextPosition
     * @param viewR
     */
    public static String layoutCompoundLabel(
        JComponent c,
        FontMetrics fm,
        String text,
        Icon icon,
        int verticalAlignment,
        int horizontalAlignment,
        int verticalTextPosition,
        int horizontalTextPosition,
        Rectangle viewR,
        Rectangle iconR,
        Rectangle textR,
        int textIconGap) {
        boolean orientationIsLeftToRight = true;
        int hAlign = horizontalAlignment;
        int hTextPos = horizontalTextPosition;

        if (c != null) {
            if (!(c.getComponentOrientation().isLeftToRight())) {
                orientationIsLeftToRight = false;
            }
        }

        // Translate LEADING/TRAILING values in horizontalAlignment
        // to LEFT/RIGHT values depending on the components orientation
        switch (horizontalAlignment) {
        case LEADING :
            hAlign = (orientationIsLeftToRight) ? LEFT : RIGHT;
            break;
        case TRAILING :
            hAlign = (orientationIsLeftToRight) ? RIGHT : LEFT;
            break;
        default:
            break;
        }

        // Translate LEADING/TRAILING values in horizontalTextPosition
        // to LEFT/RIGHT values depending on the components orientation
        switch (horizontalTextPosition) {
        case LEADING :
            hTextPos = (orientationIsLeftToRight) ? LEFT : RIGHT;
            break;
        case TRAILING :
            hTextPos = (orientationIsLeftToRight) ? RIGHT : LEFT;
            break;
        default:
            break;
        }

        return layoutCompoundLabel(fm, text, icon, verticalAlignment, hAlign, verticalTextPosition, hTextPos, viewR, iconR, textR, textIconGap);
    }

    /**
     * Compute and return the location of the icons origin, the
     * location of origin of the text baseline, and a possibly clipped
     * version of the compound labels string.  Locations are computed
     * relative to the viewR rectangle.
     * This layoutCompoundLabel() does not know how to handle LEADING/TRAILING
     * values in horizontalTextPosition (they will default to RIGHT) and in
     * horizontalAlignment (they will default to CENTER).
     * Use the other version of layoutCompoundLabel() instead.
     *
     * @return String
     * @param fm
     * @param horizontalAlignment
     * @param horizontalTextPosition
     * @param icon
     * @param iconR
     * @param text
     * @param textIconGap
     * @param textR
     * @param verticalAlignment
     * @param verticalTextPosition
     * @param viewR
     */
    public static String layoutCompoundLabel(
        FontMetrics fm,
        String text,
        Icon icon,
        int verticalAlignment,
        int horizontalAlignment,
        int verticalTextPosition,
        int horizontalTextPosition,
        Rectangle viewR,
        Rectangle iconR,
        Rectangle textR,
        int textIconGap) {
        /* Initialize the icon bounds rectangle iconR.
         */
        int labelR_x = -1;
        int labelR_width = -1;
        int labelR_y = -1;
        int labelR_height = -1;
        int dx = -1;
        int dy = -1;
        String rettext = "";
        boolean textIsEmpty = false;
        int gap = -1;
        if (icon != null) {
            iconR.width = icon.getIconWidth();
            iconR.height = icon.getIconHeight();
        } else {
            iconR.width = iconR.height = 0;
        }

        /* Initialize the text bounds rectangle textR.  If a null
         * or and empty String was specified we substitute "" here
         * and use 0,0,0,0 for textR.
         */

        // Fix for textIsEmpty sent by Paulo Santos
        textIsEmpty = (text == null);

        if (textIsEmpty) {
            textR.width = textR.height = 0;
        } else {
            Dimension dim = computeMultiLineDimension(fm, text);
            textR.width = dim.width;
            textR.height = dim.height;
        }

        /* Unless both text and icon are non-null, we effectively ignore
         * the value of textIconGap.  The code that follows uses the
         * value of gap instead of textIconGap.
         */

        gap = (textIsEmpty || (icon == null)) ? 0 : textIconGap;

        if (!textIsEmpty) {

            /* If the label text string is too wide to fit within the available
             * space "..." and as many characters as will fit will be
             * displayed instead.
             */

            int availTextWidth;

            if (horizontalTextPosition == CENTER) {
                availTextWidth = viewR.width;
            } else {
                availTextWidth = viewR.width - (iconR.width + gap);
            }

            if (textR.width > availTextWidth) {
                String clipString = "...";
                int totalWidth = SwingUtilities.computeStringWidth(fm, clipString);
                int nChars;
                for (nChars = 0; nChars < text.length(); nChars++) {
                    totalWidth += fm.charWidth(text.charAt(nChars));
                    if (totalWidth > availTextWidth) {
                        break;
                    }
                }
                rettext = text.substring(0, nChars) + clipString;
                textR.width = SwingUtilities.computeStringWidth(fm, rettext);
            }
        }

        /* Compute textR.x,y given the verticalTextPosition and
         * horizontalTextPosition properties
         */

        if (verticalTextPosition == TOP) {
            if (horizontalTextPosition != CENTER) {
                textR.y = 0;
            } else {
                textR.y = - (textR.height + gap);
            }
        } else if (verticalTextPosition == CENTER) {
            textR.y = (iconR.height / 2) - (textR.height / 2);
        } else { // (verticalTextPosition == BOTTOM)
            if (horizontalTextPosition != CENTER) {
                textR.y = iconR.height - textR.height;
            } else {
                textR.y = (iconR.height + gap);
            }
        }

        if (horizontalTextPosition == LEFT) {
            textR.x = - (textR.width + gap);
        } else if (horizontalTextPosition == CENTER) {
            textR.x = (iconR.width / 2) - (textR.width / 2);
        } else { // (horizontalTextPosition == RIGHT)
            textR.x = (iconR.width + gap);
        }

        /* labelR is the rectangle that contains iconR and textR.
         * Move it to its proper position given the labelAlignment
         * properties.
         *
         * To avoid actually allocating a Rectangle, Rectangle.union
         * has been inlined below.
         */
        labelR_x = Math.min(iconR.x, textR.x);
        labelR_width = Math.max(iconR.x + iconR.width, textR.x + textR.width) - labelR_x;
        labelR_y = Math.min(iconR.y, textR.y);
        labelR_height = Math.max(iconR.y + iconR.height, textR.y + textR.height) - labelR_y;

        if (verticalAlignment == TOP) {
            dy = viewR.y - labelR_y;
        } else if (verticalAlignment == CENTER) {
            dy = (viewR.y + (viewR.height / 2)) - (labelR_y + (labelR_height / 2));
        } else { // (verticalAlignment == BOTTOM)
            dy = (viewR.y + viewR.height) - (labelR_y + labelR_height);
        }

        if (horizontalAlignment == LEFT) {
            dx = viewR.x - labelR_x;
        } else if (horizontalAlignment == RIGHT) {
            dx = (viewR.x + viewR.width) - (labelR_x + labelR_width);
        } else { // (horizontalAlignment == CENTER)
            dx = (viewR.x + (viewR.width / 2)) - (labelR_x + (labelR_width / 2));
        }

        /* Translate textR and glypyR by dx,dy.
         */

        textR.x += dx;
        textR.y += dy;

        iconR.x += dx;
        iconR.y += dy;

        return rettext;
    }

    /**
     * @see javax.swing.plaf.basic.BasicLabelUI#paintEnabledText(javax.swing.JLabel, java.awt.Graphics, java.lang.String, int, int)
     * @author Anthony C. Liberto
     */
    protected void paintEnabledText(JLabel l, Graphics g, String s, int textX, int textY) {
        int accChar = l.getDisplayedMnemonic();
        g.setColor(l.getForeground());
        drawString(g, s, accChar, textX, textY);
    }

    /**
     * @see javax.swing.plaf.basic.BasicLabelUI#paintDisabledText(javax.swing.JLabel, java.awt.Graphics, java.lang.String, int, int)
     * @author Anthony C. Liberto
     */
    protected void paintDisabledText(JLabel l, Graphics g, String s, int textX, int textY) {
        int accChar = l.getDisplayedMnemonic();
        g.setColor(l.getBackground());
        drawString(g, s, accChar, textX, textY);
    }

    /**
     * drawString
     * @param g
     * @param s
     * @param accChar
     * @param textX
     * @param textY
     * @author Anthony C. Liberto
     */
    protected void drawString(Graphics g, String s, int accChar, int textX, int textY) {
        if (s.indexOf('\n') == -1) {
            //22563			BasicGraphicsUtils.drawString(g, s, accChar, textX, textY);
            BasicGraphicsUtils.drawString(g, getString(s), accChar, textX, textY); //22563
        } else {
            String[] strs = Routines.splitStringByLines(s);
            int height = g.getFontMetrics().getHeight();
            // Only the first line can have the accel char
            int l = strs.length;
            BasicGraphicsUtils.drawString(g, getString(strs[0]), accChar, textX, textY);
            for (int i = 1; i < l; i++) {
                g.drawString(getString(strs[i]), textX, textY + (height * i));
            }
        }
    }

    /**
     * computeMultiLineDimension
     * @param fm
     * @param strs
     * @return
     * @author Anthony C. Liberto
     */
    public static Dimension computeMultiLineDimension(FontMetrics fm, String strs) {
        int width = 0;
        try {
            width = Math.max(width, SwingUtilities.computeStringWidth(fm, strs));
        } catch (Exception e) {
            EAccess.report(e,false);
        }
        return new Dimension(width, fm.getHeight());
    }

    /**
     * getString
     * @param s
     * @return
     * @author Anthony C. Liberto
     */
    public static String getString(String s) {
        if (s == null) {
            return "";
        }
        //22563		return s;
        return Routines.getDisplayString(s, false); //22563
    }

}
