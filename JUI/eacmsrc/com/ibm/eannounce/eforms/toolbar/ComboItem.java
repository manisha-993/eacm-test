/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: ComboItem.java,v $
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
 * Revision 1.3  2005/01/31 20:47:48  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/26 22:17:57  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2004/02/10 16:59:48  tony
 * This is the initial load of OPICM
 *
 * Revision 1.2  2003/04/11 20:02:31  tony
 * added copyright statements.
 *
 */
package com.ibm.eannounce.eforms.toolbar;
import com.ibm.eannounce.eobjects.*;
import java.io.Serializable;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class ComboItem extends EObject implements Serializable {
	static final long serialVersionUID = 19721222L;
	/**
     * BOTTOM_ITEM
     */
    public static final ComboItem BOTTOM_ITEM = new ComboItem("Bottom",3);
	/**
     * CENTER_ITEM
     */
    public static final ComboItem CENTER_ITEM = new ComboItem("Center",0);
	/**
     * EAST_ITEM
     */
    public static final ComboItem EAST_ITEM = new ComboItem("East",3);
	/**
     * HORIZONTAL_ITEM
     */
    public static final ComboItem HORIZONTAL_ITEM = new ComboItem("Horizontal",0);
	/**
     * LEADING_ITEM
     */
    public static final ComboItem LEADING_ITEM = new ComboItem("Leading",10);
	/**
     * LEFT_ITEM
     */
    public static final ComboItem LEFT_ITEM = new ComboItem("Left",2);
	/**
     * NORTH_ITEM
     */
    public static final ComboItem NORTH_ITEM = new ComboItem("North",1);
	/**
     * NORTHEAST_ITEM
     */
    public static final ComboItem NORTHEAST_ITEM = new ComboItem("Northeast",2);
	/**
     * NORTHWEST_ITEM
     */
    public static final ComboItem NORTHWEST_ITEM = new ComboItem("Northwest",8);
	/**
     * RIGHT_ITEM
     */
    public static final ComboItem RIGHT_ITEM = new ComboItem("Right",4);
	/**
     * SOUTH_ITEM
     */
    public static final ComboItem SOUTH_ITEM = new ComboItem("South",5);
	/**
     * SOUTHEAST_ITEM
     */
    public static final ComboItem SOUTHEAST_ITEM = new ComboItem("Southeast",4);
	/**
     * SOUTHWEST_ITEM
     */
    public static final ComboItem SOUTHWEST_ITEM = new ComboItem("Southwest",6);
	/**
     * TOP_ITEM
     */
    public static final ComboItem TOP_ITEM = new ComboItem("Top",1);
	/**
     * TRAILING_ITEM
     */
    public static final ComboItem TRAILING_ITEM = new ComboItem("Trailing",11);
	/**
     * VERTICAL_ITEM
     */
    public static final ComboItem VERTICAL_ITEM = new ComboItem("Vertical", 1);
	/**
     * WEST_ITEM
     */
    public static final ComboItem WEST_ITEM = new ComboItem("West",7);

	private String sKey = null;
	private String sDisplay = null;
	private int iKey = -1;

	/**
     * comboItem
     * @param _sKey
     * @param _iKey
     * @author Anthony C. Liberto
     */
    public ComboItem(String _sKey, int _iKey) {
		sKey = new String(_sKey);
		sDisplay = sKey;
		iKey = _iKey;
		return;
	}

	/**
     * comboItem
     * @param _sKey
     * @param _sDisplay
     * @param _iKey
     * @author Anthony C. Liberto
     */
    public ComboItem(String _sKey, String _sDisplay, int _iKey) {
		sKey = new String(_sKey);
		sDisplay = new String(getString(_sDisplay));
		iKey = _iKey;
		return;
	}

	/**
     * getStaticComboItem
     * @param _item
     * @return
     * @author Anthony C. Liberto
     */
    public static ComboItem getStaticComboItem(ComboItem _item) {
		String key = _item.getStringKey();
		if (BOTTOM_ITEM.getStringKey().equals(key)) {
			return BOTTOM_ITEM;
		} else if (CENTER_ITEM.getStringKey().equals(key)) {
			return CENTER_ITEM;
		} else if (EAST_ITEM.getStringKey().equals(key)) {
			return EAST_ITEM;
		} else if (HORIZONTAL_ITEM.getStringKey().equals(key)) {
			return HORIZONTAL_ITEM;
		} else if (LEADING_ITEM.getStringKey().equals(key)) {
			return LEADING_ITEM;
		} else if (NORTH_ITEM.getStringKey().equals(key)) {
			return NORTH_ITEM;
		} else if (NORTHEAST_ITEM.getStringKey().equals(key)) {
			return NORTHEAST_ITEM;
		} else if (RIGHT_ITEM.getStringKey().equals(key)) {
			return RIGHT_ITEM;
		} else if (SOUTH_ITEM.getStringKey().equals(key)) {
			return SOUTH_ITEM;
		} else if (SOUTHEAST_ITEM.getStringKey().equals(key)) {
			return SOUTHEAST_ITEM;
		} else if (SOUTHWEST_ITEM.getStringKey().equals(key)) {
			return SOUTHWEST_ITEM;
		} else if (TOP_ITEM.getStringKey().equals(key)) {
			return TOP_ITEM;
		} else if (TRAILING_ITEM.getStringKey().equals(key)) {
			return TRAILING_ITEM;
		} else if (VERTICAL_ITEM.getStringKey().equals(key)) {
			return VERTICAL_ITEM;
		} else if (WEST_ITEM.getStringKey().equals(key)) {
			return WEST_ITEM;
		}
		return _item;
	}

	/**
     * getStringKey
     * @return
     * @author Anthony C. Liberto
     */
    public String getStringKey() {
		return sKey;
	}

	/**
     * @see java.lang.Object#toString()
     * @author Anthony C. Liberto
     */
    public String toString() {
		return sDisplay;
	}

	/**
     * getIntKey
     * @return
     * @author Anthony C. Liberto
     */
    public int getIntKey() {
		return iKey;
	}
}
