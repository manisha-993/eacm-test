/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: EToolbar.java,v $
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
 * Revision 1.3  2002/11/07 16:58:36  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.toolbar;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public interface EToolbar {
	/**
     * getToolbarItems
     * @return
     * @author Anthony C. Liberto
     */
    ToolbarItem[] getToolbarItems();
	/**
     * isFloatable
     * @return
     * @author Anthony C. Liberto
     */
    boolean isFloatable();
	/**
     * getOrientation
     * @return
     * @author Anthony C. Liberto
     */
    ComboItem getOrientation();
	/**
     * getAlignment
     * @return
     * @author Anthony C. Liberto
     */
    ComboItem getAlignment();
	/**
     * getComboItem
     * @return
     * @author Anthony C. Liberto
     */
    ComboItem getComboItem();

	/**
     * setToolbarItems
     * @param _items
     * @author Anthony C. Liberto
     */
    void setToolbarItems(ToolbarItem[] _items);
	/**
     * setFloatable
     * @param _b
     * @author Anthony C. Liberto
     */
    void setFloatable(boolean _b);
	/**
     * setOrientation
     * @param _ci
     * @author Anthony C. Liberto
     */
    void setOrientation(ComboItem _ci);
	/**
     * setAlignment
     * @param _ci
     * @author Anthony C. Liberto
     */
    void setAlignment(ComboItem _ci);

	/**
     * getAlignmentString
     * @return
     * @author Anthony C. Liberto
     */
    String getAlignmentString();
	/**
     * getOrientationInt
     * @return
     * @author Anthony C. Liberto
     */
    int getOrientationInt();
	/**
     * getStringKey
     * @return
     * @author Anthony C. Liberto
     */
    String getStringKey();
}
