// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.toolbar;


import com.ibm.eacm.objects.Utils;

import java.io.Serializable;

/**
 * used in toolbar pref combo boxes and as a key for tab toolbar layouts
 * @author Wendy Stimpson
 */
// $Log: ComboItem.java,v $
// Revision 1.1  2012/09/27 19:39:25  wendy
// Initial code
//
public class ComboItem implements Serializable {
	static final long serialVersionUID = 19721222L;

    public static final ComboItem NORTH_ITEM = new ComboItem("North",1);
    public static final ComboItem SOUTH_ITEM = new ComboItem("South",5);
    public static final ComboItem EAST_ITEM = new ComboItem("East",3);
    public static final ComboItem WEST_ITEM = new ComboItem("West",7);

    public static final ComboItem VERTICAL_ITEM = new ComboItem("Vertical", 1);
    public static final ComboItem HORIZONTAL_ITEM = new ComboItem("Horizontal",0);


	private String sKey = null;
	private String sDisplay = null;
	private int iKey = -1;

	/**
     * comboItem
     * @param _sKey
     * @param _iKey
     */
    private ComboItem(String _sKey, int _iKey) {
		sKey = _sKey;
		sDisplay = sKey;
		iKey = _iKey;
	}

	/**
     * comboItem
     * @param _sKey
     * @param _sDisplay
     * @param _iKey
     */
    public ComboItem(String _sKey, String _sDisplay, int _iKey) {
		sKey = _sKey;
		sDisplay =Utils.getResource(_sDisplay);
		iKey = _iKey;
	}

	/**
     * getStringKey
     * @return
     */
    public String getStringKey() {
		return sKey;
	}

	/**
     * @see java.lang.Object#toString()
     */
    public String toString() {
		return sDisplay;
	}
    
    /* this is needed when a serial preference is restored
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object o) {
    	if (o instanceof ComboItem){
    		ComboItem ci = (ComboItem)o;
    		return (iKey==ci.iKey && sDisplay.equals(ci.sDisplay) && sKey.equals(ci.sKey));
    	}
    	return false;
    }
	/**
     * getIntKey
     * @return
     */
    public int getIntKey() {
		return iKey;
	}
}
