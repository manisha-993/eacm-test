/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: DefaultToolbar.java,v $
 * Revision 1.1  2007/04/18 19:45:24  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:16  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:38:15  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:59:08  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/01/31 20:47:48  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/26 22:17:57  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2004/02/10 16:59:48  tony
 * This is the initial load of OPICM
 *
 * Revision 1.1.1.1  2003/03/03 18:03:51  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2002/11/07 16:58:36  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.toolbar;
import java.io.Serializable;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class DefaultToolbar implements EToolbar, Serializable {
	static final long serialVersionUID = 19721222L;
	private ComboItem ci = null;

	/**
     * defaultToolbar
     * @param _ci
     * @author Anthony C. Liberto
     */
    public DefaultToolbar(ComboItem _ci) {
		ci = _ci;
		return;
	}

    /**
     * getStringKey
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getStringKey() {
		return ci.getStringKey();
	}

    /**
     * getComboItem
     *
     * @return
     * @author Anthony C. Liberto
     */
    public ComboItem getComboItem() {
		return ci;
	}

    /**
     * getToolbarItems
     *
     * @return
     * @author Anthony C. Liberto
     */
    public ToolbarItem[] getToolbarItems() {
		if (ci != null) {
			return DefaultToolbarLayout.getDefaultLayout(ci);
		}
		return null;
	}

    /**
     * isFloatable
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isFloatable() {
		return true;
	}

    /**
     * getOrientation
     *
     * @return
     * @author Anthony C. Liberto
     */
    public ComboItem getOrientation() {
		return ComboItem.HORIZONTAL_ITEM;
	}

    /**
     * getAlignment
     *
     * @return
     * @author Anthony C. Liberto
     */
    public ComboItem getAlignment() {
		return ComboItem.NORTH_ITEM;
	}

    /**
     * getAlignmentString
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getAlignmentString() {
		return ComboItem.NORTH_ITEM.getStringKey();
	}

    /**
     * getOrientationInt
     *
     * @return
     * @author Anthony C. Liberto
     */
    public int getOrientationInt() {
		return ComboItem.HORIZONTAL_ITEM.getIntKey();
	}

	/**
     * setToolbarItems
     * @author Anthony C. Liberto
     * @param _items
     */
    public void setToolbarItems(ToolbarItem[] _items) {}

    /**
     * setFloatable
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setFloatable(boolean _b) {}

	/**
     * setOrientation
     * @author Anthony C. Liberto
     * @param _ci
     */
    public void setOrientation(ComboItem _ci) {}

	/**
     * setAlignment
     * @author Anthony C. Liberto
     * @param _ci
     */
    public void setAlignment(ComboItem _ci) {}
}
