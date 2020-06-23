/**
 * Copyright (c) 2004-2005 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 * cr_6542
 *
 * @version 3.0a 2005/02/16
 * @author Anthony C. Liberto
 *
 * $Log: NLSNode.java,v $
 * Revision 1.2  2008/01/30 16:27:07  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:44:06  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:12  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:54  tony
 * This is the initial load of OPICM
 *
 * Revision 1.5  2005/09/08 17:59:01  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.4  2005/02/18 17:06:53  tony
 * removed testing logic.
 *
 * Revision 1.3  2005/02/18 16:53:24  tony
 * cr_6542
 *
 * Revision 1.2  2005/02/17 22:45:42  tony
 * cr_5254
 *
 * Revision 1.1  2005/02/17 18:57:09  tony
 * cr_6542
 *
 */
package com.ibm.eannounce.eforms.edit;
import com.elogin.*;
import COM.ibm.opicmpdh.transactions.NLSItem;
import javax.swing.tree.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class NLSNode extends DefaultMutableTreeNode implements EAccessConstants {
	private static final long serialVersionUID = 1L;
	private int type = -1;

    /**
     * nlsNode
     *
     * @author Anthony C. Liberto
     * @param _o
     * @param _type
     */
    public NLSNode(Object _o, int _type) {
		super(_o);
		type = _type;
		return;
	}

    /**
     * @see java.lang.Object#toString()
     * @author Anthony C. Liberto
     */
    public String toString() {
		Object o = getUserObject();
		if (o != null) {
			if (isTestMode()) {
				return o.toString() + getTypeString();
			}
			return o.toString();
		}
		return "NLS Language Tree";
	}

    /**
     * equals
     *
     * @author Anthony C. Liberto
     * @param _s
     * @return
     */
    public boolean equals(String _s) {
		Object o = getUserObject();
		if (o instanceof String) {
			return _s.equals((String)o);
		} else if (o instanceof  NLSItem) {
			return _s.equals(((NLSItem)o).toString());
		}
		return false;
	}

    /**
     * getNLSItem
     *
     * @author Anthony C. Liberto
     * @return
     */
    public NLSItem getNLSItem() {
		Object o = getUserObject();
		if (o != null && o instanceof NLSItem) {
			return (NLSItem)o;
		}
		return null;
	}

    /**
     * eaccess
     *
     * @author Anthony C. Liberto
     * @return
     */
    public static EAccess eaccess() {
		return EAccess.eaccess();
	}

    /**
     * getString
     *
     * @author Anthony C. Liberto
     * @param _code
     * @return
     */
    public String getString(String _code) {
		return eaccess().getString(_code);
	}

    /**
     * isArmed
     *
     * @author Anthony C. Liberto
     * @param _code
     * @return
     */
    public boolean isArmed(String _code) {
		return EAccess.isArmed(_code);
	}

    /**
     * isTestMode
     *
     * @author Anthony C. Liberto
     * @return
     */
    public boolean isTestMode() {
		return EAccess.isTestMode();
	}

	/**
     * isType
     *
     * @author Anthony C. Liberto
     * @param _i
     * @return
     */
    public boolean isType(int _i) {
		return type == _i;
	}

	/**
     * getType
     *
     * @author Anthony C. Liberto
     * @return
     */
	public int getType() {
		return type;
	}

	private String getTypeString() {
		if (type == NLS_READ_ONLY) {
			return " (Read Only)";
		} else if (type == NLS_WRITE) {
			return " (Update)";
		} else if (type == NLS_CREATE) {
			return " (Create)";
		}
		return "";
	}
}
