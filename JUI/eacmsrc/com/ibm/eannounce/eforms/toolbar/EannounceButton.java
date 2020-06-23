/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: EannounceButton.java,v $
 * Revision 1.1  2007/04/18 19:45:24  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:15  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:59:08  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/01/31 20:47:49  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/26 22:17:58  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2004/02/10 16:59:48  tony
 * This is the initial load of OPICM
 *
 * Revision 1.1.1.1  2003/03/03 18:03:51  tony
 * This is the initial load of OPICM
 *
 * Revision 1.2  2002/11/07 16:58:36  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.toolbar;
import java.awt.event.*;
import javax.swing.plaf.ButtonUI;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public interface EannounceButton {
	/**
     * isVisible
     * @return
     * @author Anthony C. Liberto
     */
    boolean isVisible();
	/**
     * setVisible
     * @param _b
     * @author Anthony C. Liberto
     */
    void setVisible(boolean _b);
	/**
     * isShowing
     * @return
     * @author Anthony C. Liberto
     */
    boolean isShowing();
	/**
     * isEnabled
     * @return
     * @author Anthony C. Liberto
     */
    boolean isEnabled();
	/**
     * setEnabled
     * @param _b
     * @author Anthony C. Liberto
     */
    void setEnabled(boolean _b);
	/**
     * getName
     * @return
     * @author Anthony C. Liberto
     */
    String getName();

	/**
     * removeActionListener
     * @param _al
     * @author Anthony C. Liberto
     */
    void removeActionListener(ActionListener _al);
	/**
     * getUI
     * @return
     * @author Anthony C. Liberto
     */
    ButtonUI getUI();
	/**
     * dereference
     * @author Anthony C. Liberto
     */
    void dereference();
	/**
     * removeAll
     * @author Anthony C. Liberto
     */
    void removeAll();
	/**
     * removeNotify
     * @author Anthony C. Liberto
     */
    void removeNotify();
}
