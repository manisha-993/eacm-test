/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: ToolbarItem.java,v $
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
 * Revision 1.3  2003/06/19 16:06:29  tony
 * toolbar functionality update 1.2h
 *
 * Revision 1.2  2003/04/11 20:02:31  tony
 * added copyright statements.
 *
 */
package com.ibm.eannounce.eforms.toolbar;
import com.ibm.eannounce.eobjects.EObject;
import javax.swing.Icon;
import java.io.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class ToolbarItem extends EObject implements Serializable {
	static final long serialVersionUID = 19721222L;
	/**
     * BUTTON_TYPE
     */
    public static final int BUTTON_TYPE = 0;
	/**
     * CONTAINER_TYPE
     */
    public static final int CONTAINER_TYPE = 1;
	/**
     * SEPARATOR_TYPE
     */
    public static final int SEPARATOR_TYPE = 2;
	/**
     * COMPONENT_TYPE
     */
    public static final int COMPONENT_TYPE = 3;

	/**
     * gif
     */
    private String gif = null;
	/**
     * key
     */
    private String key = null;
	/**
     * tip
     */
    private String tip = null;
	/**
     * typ
     */
    private int typ = SEPARATOR_TYPE;
	/**
     * enabled
     */
    private boolean enabled = true;

	/**
     * toolbarItem
     * @param _item
     * @author Anthony C. Liberto
     */
    public ToolbarItem(ToolbarItem _item) {
		if (_item.gif != null) {
            gif = new String(_item.gif);
		}
		if (_item.key != null) {
            key = new String(_item.key);
		}
		if (_item.tip != null) {
            tip = new String(_item.tip);
		}
		typ = _item.typ;
		enabled = _item.enabled;
		return;
	}

	/**
     * toolbarItem
     * @param _gif
     * @param _key
     * @param _tip
     * @param _typ
     * @param _enabled
     * @author Anthony C. Liberto
     */
    public ToolbarItem(String _gif, String _key, String _tip, int _typ, boolean _enabled) {
		gif = new String(_gif);
		key = new String(_key);
		tip = new String(_tip);
		typ = _typ;
		enabled = _enabled;
		return;
	}

	/**
     * toolbarItem
     * @param _gif
     * @param _key
     * @param _tip
     * @param _typ
     * @author Anthony C. Liberto
     */
    public ToolbarItem(String _gif, String _key, String _tip, int _typ) {
		this(_gif,_key,_tip,_typ,true);
		return;
	}

	/**
     * toolbarItem
     * @author Anthony C. Liberto
     */
    public ToolbarItem() {
		return;
	}

	/**
     * toolbarItem
     * @param _key
     * @param _tip
     * @param _typ
     * @author Anthony C. Liberto
     */
    public ToolbarItem(String _key, String _tip, int _typ) {
		key = new String(_key);
		tip = new String(_tip);
		typ = _typ;
		return;
	}

	/**
     * isSeparator
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isSeparator() {
		return typ == SEPARATOR_TYPE;
	}

	/**
     * getGif
     * @return
     * @author Anthony C. Liberto
     */
    public String getGif() {
		return gif;
	}

	/**
     * getKey
     * @return
     * @author Anthony C. Liberto
     */
    public String getKey() {
		return key;
	}

	/**
     * getTip
     * @return
     * @author Anthony C. Liberto
     */
    public String getTip() {
		return tip;
	}

	/**
     * getType
     * @return
     * @author Anthony C. Liberto
     */
    public int getType() {
		return typ;
	}

	/**
     * getEnabled
     * @return
     * @author Anthony C. Liberto
     */
    public boolean getEnabled() {
		return enabled;
	}

	/**
     * getClone
     * @return
     * @author Anthony C. Liberto
     */
    public ToolbarItem getClone() {
		return new ToolbarItem(this);
	}

	/**
     * getIcon
     * @return
     * @author Anthony C. Liberto
     */
    public Icon getIcon() {
		if (gif != null) {
			return getImageIcon(gif);
		}
		return null;
	}

	/**
     * getTipDisplay
     * @return
     * @author Anthony C. Liberto
     */
    public String getTipDisplay() {
		if (tip != null) {
			return tip;
		}
//			return getString(tip);
		return null;
	}

	/**
     * @see java.lang.Object#toString()
     * @author Anthony C. Liberto
     */
    public String toString() {
		return gif + ":" + key + ":" + tip + ":" + typ;
	}

}
