//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.ui;

import com.ibm.eacm.preference.ColorPref;

import java.awt.*;
import javax.swing.border.LineBorder;

/**
 * Border used for renderers
 * @author Wendy Stimpson
 */
// $Log: FoundLineBorder.java,v $
// Revision 1.1  2012/09/27 19:39:11  wendy
// Initial code
//
public class FoundLineBorder extends LineBorder {
	private static final long serialVersionUID = 1L;
	private String prefKey = null; // key to color in preferences
	private Color defColor = null; // default color


    /**
     * FOUND_FOCUS_BORDER
     */
    public static final FoundLineBorder FOUND_FOCUS_BORDER = new FoundLineBorder(ColorPref.PREF_COLOR_FOUND_FOCUS, ColorPref.DEFAULT_COLOR_FOUND_FOCUS, 2);
    /**
     * FOUND_BORDER
     */
    public static final FoundLineBorder FOUND_BORDER = new FoundLineBorder(ColorPref.PREF_COLOR_FOUND, ColorPref.DEFAULT_COLOR_FOUND, 2);
    
	/**
     * @param _key
     * @param _defColor
     * @param _thick
     */
    public FoundLineBorder(String _key, Color _defColor, int _thick) {
		super(_defColor,_thick);
		defColor = _defColor;
		prefKey = _key;
	}

	/**
     * change line color
     */
    public void refreshAppearance() {
		lineColor = ColorPref.getColorForRGB(prefKey,defColor);
	}

}
