//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.toolbar;
import java.io.Serializable;

import com.ibm.eacm.objects.SerialPref;


/**
 * this class holds the information for laying out a toolbar based on user preferences
 * @author Wendy Stimpson
 */
// $Log: ToolbarLayout.java,v $
// Revision 1.1  2012/09/27 19:39:25  wendy
// Initial code
//
public class ToolbarLayout implements Serializable {
	static final long serialVersionUID = 19721222L;
	private ComboItem ci = null;
	private ToolbarItem[] items = null;
	private boolean bFloat = true;
	private ComboItem ciOrient = null;
	private ComboItem ciAlign = null;

    /**
     * get toolbarlayout for specified item, look for saved preference first
     * @param _item
     * @return
     */
    public static ToolbarLayout getToolbarLayout(ComboItem _item) {
		Object o = SerialPref.getPref(_item.getStringKey());
		if (o instanceof ToolbarLayout) {
			return (ToolbarLayout)o;
		}
		return new ToolbarLayout(_item);
	}
	/**
     * default Toolbar
     * @param _ci
     */
    private ToolbarLayout(ComboItem _ci) {
		ci = _ci;
	}

    /**
     * used by toolbar preferences
     * @param _ci
     * @param orient
     * @param alignment
     * @param isfloat
     * @param it
     */
    public ToolbarLayout(ComboItem _ci, ComboItem orient, ComboItem alignment, boolean isfloat, ToolbarItem[] it) {
		ci = _ci;
		ciOrient = orient;
		ciAlign = alignment;
		bFloat = isfloat;
		items = it;
	}
    /**
     * getStringKey - used when instantiating a toolbar, it is the 'name' of the toolbar
     *
     * @return
     */
    public String getStringKey() {
		return ci.getStringKey();
	}

    /**
     * getToolbarItems - used when instantiating a toolbar and in toolbar pref
     *
     * @return
     */
    public ToolbarItem[] getToolbarItems() {
    	if(items!=null){
    		return items;
    	}
		return DefaultToolbarLayout.getDefaultLayout(ci);
	}

    /**
     * isFloatable  - used when instantiating a toolbar and in toolbar pref
     *
     * @return
     */
    public boolean isFloatable() {
		return bFloat;
	}

    /**
     * getOrientation - used by toolbar pref
     *
     * @return
     */
    public ComboItem getOrientation() {
    	if(ciOrient!=null){
    		return ciOrient;
    	}
		return ComboItem.HORIZONTAL_ITEM;
	}

    /**
     * getAlignment - used by toolbar pref
     *
     * @return
     */
    public ComboItem getAlignment() {
    	if (ciAlign!=null){
    		return ciAlign;
    	}
		return ComboItem.NORTH_ITEM;
	}

    /**
     * getAlignmentString - used when instantiating a toolbar
     *
     * @return
     */
    public String getAlignmentString() {
    	if (ciAlign!= null){
    		return ciAlign.getStringKey();
    	}
		return ComboItem.NORTH_ITEM.getStringKey();
	}

    /**
     * getOrientationInt - used when instantiating a toolbar
     *
     * @return
     */
    public int getOrientationInt() {
    	if (ciOrient!=null){
    		return ciOrient.getIntKey();
    	}
		return ComboItem.HORIZONTAL_ITEM.getIntKey();
	}
}
