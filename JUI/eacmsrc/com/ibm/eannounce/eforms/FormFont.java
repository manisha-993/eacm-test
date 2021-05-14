/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: FormFont.java,v $
 * Revision 1.1  2007/04/18 19:43:40  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:37:59  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:59:01  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/02/01 22:06:31  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 19:15:14  tony
 * JTest Format
 *
 * Revision 1.1.1.1  2004/02/10 16:59:42  tony
 * This is the initial load of OPICM
 *
 * Revision 1.1.1.1  2003/03/03 18:03:48  tony
 * This is the initial load of OPICM
 *
 * Revision 1.2  2002/11/07 16:58:21  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms;
import java.awt.Font;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class FormFont {
    private String sFace = "Arial,Helvetica";
    private int iStyle = 0;
    private int iSize = 18;
    private Font font = null;

    /**
     * formFont
     * @author Anthony C. Liberto
     */
    public FormFont() {
    }

    /**
     * setFace
     * @param _s
     * @author Anthony C. Liberto
     */
    public void setFace(String _s) {
        sFace = _s;
    }

    /**
     * getFace
     * @return
     * @author Anthony C. Liberto
     */
    public String getFace() {
        return sFace;
    }

    /**
     * setStyle
     * @param _i
     * @author Anthony C. Liberto
     */
    public void setStyle(int _i) {
        iStyle = _i;
    }

    /**
     * getStyle
     * @return
     * @author Anthony C. Liberto
     */
    public int getStyle() {
        return iStyle;
    }

    /**
     * setSize
     * @param _i
     * @author Anthony C. Liberto
     */
    public void setSize(int _i) {
        iSize = _i;
    }

    /**
     * getSize
     * @return
     * @author Anthony C. Liberto
     */
    public int getSize() {
        return iSize;
    }

    /**
     * getFont
     * @return
     * @author Anthony C. Liberto
     */
    public Font getFont() {
        if (font == null) {
            font = new Font(sFace, iStyle, iSize);
        }
        return font;
    }

    /**
     * setFont
     * @param _f
     * @author Anthony C. Liberto
     */
    public void setFont(Font _f) {
        setFace(_f.getFontName());
        setStyle(_f.getStyle());
        setSize(_f.getSize());
        font = _f;
    }
}
