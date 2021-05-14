/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: EDisplayable.java,v $
 * Revision 1.1  2007/04/18 19:42:17  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:37:37  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:58:52  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/02/02 21:30:07  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 23:18:16  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:24  tony
 * This is the initial load of OPICM
 *
 * Revision 1.2  2003/04/11 20:02:25  tony
 * added copyright statements.
 *
 */
package com.elogin;
import java.awt.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public interface EDisplayable {
	/**
     * getDisplayable
     * @return
     * @author Anthony C. Liberto
     */
    EDisplayable getDisplayable();
	/**
     * setUseDefined
     * @param _b
     * @author Anthony C. Liberto
     */
    void setUseDefined(boolean _b);
	/**
     * setUseBack
     * @param _b
     * @author Anthony C. Liberto
     */
    void setUseBack(boolean _b);
	/**
     * setUseFore
     * @param _b
     * @author Anthony C. Liberto
     */
    void setUseFore(boolean _b);
	/**
     * setUseFont
     * @param _b
     * @author Anthony C. Liberto
     */
    void setUseFont(boolean _b);
	/**
     * getBackground
     * @return
     * @author Anthony C. Liberto
     */
    Color getBackground();
	/**
     * getForeground
     * @return
     * @author Anthony C. Liberto
     */
    Color getForeground();
	/**
     * getFont
     * @return
     * @author Anthony C. Liberto
     */
    Font getFont();
	/**
     * getCursor
     * @return
     * @author Anthony C. Liberto
     */
    Cursor getCursor();
	/**
     * setModalCursor
     * @param _b
     * @author Anthony C. Liberto
     */
    void setModalCursor(boolean _b);
	/**
     * isModalCursor
     * @return
     * @author Anthony C. Liberto
     */
    boolean isModalCursor();
}
