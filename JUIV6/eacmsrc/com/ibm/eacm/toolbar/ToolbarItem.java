// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package com.ibm.eacm.toolbar;
import com.ibm.eacm.objects.Utils;

import javax.swing.Icon;
import java.io.*;

/**
 * this class hold the information for one toolbar component
 * @author Wendy Stimpson
 */
// $Log: ToolbarItem.java,v $
// Revision 1.1  2012/09/27 19:39:25  wendy
// Initial code
//
public class ToolbarItem implements Serializable {
	static final long serialVersionUID = 19721222L;
	public static final int COMPONENT_TYPE = 3;
	public static final int ACTION_TYPE = 4;
	
	public static final ToolbarItem SEPARATOR = new ToolbarItem();
	/**
     * toolbarItem - used for a separator
     */
    private ToolbarItem() {}

    private String gif = null;
    private int typ = -1;
    private String actName = null;
    
    /**
     * @param _gif
     * @param actionName
     */
    protected ToolbarItem(String _gif,String actionName) {
    	actName = actionName; 
    	gif = _gif;
    	typ = ACTION_TYPE;
	}

	/**
     * toolbarItem this is used for record toggle
     * @param _key
     * @param _typ
     */
    protected ToolbarItem(String _key, int _typ) {
    	actName = _key;
		typ = _typ;
	}
    /* 
	 * used to check for separator and by Vector.contains()
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object o) {
    	if (o==null){
    		return false;
    	}
     	return this.toString().equals(o.toString());
    }
    
    /**
     * @return
     */
    public String getActionName(){
    	return actName;
    }

	/**
     * getGif
     * @return
     */
    public String getGif() {
		return gif;
	}

	/**
     * getType
     * @return
     */
    public int getType() {
		return typ;
	}

	/**
     * getIcon
     * @return
     */
    public Icon getIcon() {
		if (gif != null) {
			return Utils.getImageIcon(gif);
		}
		return null;
	}

	/**
	 * for debug only
     * @see java.lang.Object#toString()
     */
    public String toString() {
		return gif + ":" + actName + ":" + typ;
	}

}
