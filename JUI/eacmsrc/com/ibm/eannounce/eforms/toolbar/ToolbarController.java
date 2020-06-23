/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: ToolbarController.java,v $
 * Revision 1.2  2008/01/30 16:27:03  wendy
 * Cleanup RSA warnings
 *
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
 * Revision 1.4  2002/11/07 16:58:37  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.toolbar;
import com.elogin.*;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class ToolbarController extends EVector {
	private static final long serialVersionUID = 1L;
	/**
     * toolbarController
     * @author Anthony C. Liberto
     */
    public ToolbarController() {
		super();
		return;
	}

	/**
     * generateToolbar
     * @param _bar
     * @param _al
     * @param _obj
     * @return
     * @author Anthony C. Liberto
     */
    public static EannounceToolbar generateToolbar(EToolbar _bar, ActionListener _al, Object _obj) {
		EannounceToolbar bar = new EannounceToolbar(_bar, _al, _obj);
		return bar;
	}

	/**
     * generateToolbar
     * @param _item
     * @param _al
     * @param _obj
     * @return
     * @author Anthony C. Liberto
     */
    public static EannounceToolbar generateToolbar(ComboItem _item, ActionListener _al, Object _obj) {
		return generateToolbar(getEToolbar(_item), _al, _obj);
	}

	/**
     * getEToolbar
     * @param _item
     * @return
     * @author Anthony C. Liberto
     */
    public static EToolbar getEToolbar(ComboItem _item) {
		Object o = getSerialPref(_item.getStringKey());
		if (o != null && o instanceof SerialToolbar) {
			return (SerialToolbar)o;
		}
		return new DefaultToolbar(_item);
	}

	private Vector getVector(String _key, boolean _create) {
		Vector v = null;
        if (containsKey(_key)) {
			return (Vector)get(_key);
		}
		if (!_create) {
            return null;
		}
		v = new Vector();
		put(_key, v);
		return v;
	}

	/**
     * dereference
     * @param _bar
     * @author Anthony C. Liberto
     */
    public void dereference(EannounceToolbar _bar) {
		Vector v = getVector(_bar.getStringKey(),false);
		EannounceToolbar bar = null;
        if (v == null) {
            return;
		}
		bar = (EannounceToolbar)v.get(v.indexOf(_bar));
		if (bar == null) {
			v.remove(bar);
			bar.dereference();
		}
		return;
	}

	private void dereference(Vector _v) {
		while (!_v.isEmpty()) {
			Object o = _v.get(0);
			if (o instanceof EannounceToolbar) {
				((EannounceToolbar)o).dereference();
			}
			_v.remove(0);
		}
		return;
	}

	/**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
		int ii = size();
		for (int i=0;i<ii;++i) {
			Object o = get(i);
			if (o instanceof Vector) {
				dereference((Vector)o);
			}
		}
		removeAll();
		return;
	}

	/**
     * getSerialPref
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    public static Object getSerialPref(String _s) {
		return eaccess().getPrefObject(_s);
	}
}
