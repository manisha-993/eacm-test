/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: SerialToolbar.java,v $
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
import java.io.Serializable;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class SerialToolbar implements Serializable, EToolbar {
	static final long serialVersionUID = 19721222L;
	private ToolbarItem[] items = null;
	private boolean bFloat = false;
	private ComboItem ciOrient = null;
	private ComboItem ciAlign = null;
	private ComboItem ci = null;

	/**
     * serialToolbar
     * @param _ci
     * @author Anthony C. Liberto
     */
    public SerialToolbar(ComboItem _ci) {
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
     * setToolbarItems
     * @author Anthony C. Liberto
     * @param _items
     */
    public void setToolbarItems(ToolbarItem[] _items) {
		items = _items;
		return;
	}

    /**
     * getToolbarItems
     *
     * @return
     * @author Anthony C. Liberto
     */
    public ToolbarItem[] getToolbarItems() {
		return items;
	}

    /**
     * setFloatable
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setFloatable(boolean _b) {
		bFloat = _b;
		return;
	}

    /**
     * isFloatable
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isFloatable() {
		return bFloat;
	}

	/**
     * setOrientation
     * @author Anthony C. Liberto
     * @param _ci
     */
    public void setOrientation(ComboItem _ci) {
		ciOrient = _ci;
		return;
	}

    /**
     * getOrientationInt
     *
     * @return
     * @author Anthony C. Liberto
     */
    public int getOrientationInt() {
		return ciOrient.getIntKey();
	}

    /**
     * getOrientation
     *
     * @return
     * @author Anthony C. Liberto
     */
    public ComboItem getOrientation() {
		return ComboItem.getStaticComboItem(ciOrient);
	}

	/**
     * setAlignment
     * @author Anthony C. Liberto
     * @param _ci
     */
    public void setAlignment(ComboItem _ci) {
		ciAlign = _ci;
		return;
	}

    /**
     * getAlignmentString
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getAlignmentString() {
		return ciAlign.getStringKey();
	}

    /**
     * getAlignment
     *
     * @return
     * @author Anthony C. Liberto
     */
    public ComboItem getAlignment() {
		return ComboItem.getStaticComboItem(ciAlign);
	}
}
