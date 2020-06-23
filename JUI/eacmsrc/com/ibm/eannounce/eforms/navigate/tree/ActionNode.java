/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: ActionNode.java,v $
 * Revision 1.2  2008/01/30 16:27:06  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:44:58  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:03  tony
 * This is the initial load of OPICM
 *
 * Revision 1.6  2005/09/08 17:59:05  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.5  2005/02/03 21:26:13  tony
 * JTest Format Third Pass
 *
 * Revision 1.4  2005/01/31 20:47:46  tony
 * JTest Second Pass
 *
 * Revision 1.3  2005/01/26 23:42:28  tony
 * JTest Formatting
 *
 * Revision 1.2  2004/08/04 17:49:15  tony
 * adjusted logic to break function parameterization into
 * arm files and a new function directory.  This way
 * we will eliminate the possibility of translation to
 * accidentally change functionality.
 *
 * Revision 1.1.1.1  2004/02/10 16:59:44  tony
 * This is the initial load of OPICM
 *
 * Revision 1.2  2003/05/14 20:26:03  tony
 * updated logic to display key when in test mode.
 *
 * Revision 1.1.1.1  2003/03/03 18:03:49  tony
 * This is the initial load of OPICM
 *
 * Revision 1.3  2002/11/07 16:58:32  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.navigate.tree;
import com.elogin.*;
import COM.ibm.eannounce.objects.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class ActionNode extends AbstractNode {
	private static final long serialVersionUID = 1L;
	private boolean bTestMode = isTestMode();

	/**
     * ActionNode
     * @param _o
     * @author Anthony C. Liberto
     */
    public ActionNode(Object _o) {
		super(_o);
		return;
	}

	/**
     * @see java.lang.Object#toString()
     * @author Anthony C. Liberto
     */
    public String toString() {
		Object o = null;
        if (bTestMode) {
			return getTip();
		}
		o = getUserObject();
		if (o != null) {
			return o.toString();
		}
		return "ActionNode is Null";
	}

	/**
     * getTip
     * @return
     * @author Anthony C. Liberto
     */
    public String getTip() {
		Object o = getUserObject();
		if (o != null) {
			if (o instanceof EANActionItem) {
				EANActionItem ean = (EANActionItem)o;
				return ean.toString() + " (" + ean.getActionItemKey() + ")";
			}
			return o.toString();
		}
		return "ActionNode is null";
	}

	/**
     * equals
     *
     * @author Anthony C. Liberto
     * @return boolean
     * @param _s
     */
    public boolean equals(String _s) {
		Object o = getUserObject();
		if (o instanceof String) {
			return _s.equals((String)o);

		} else if (o instanceof EANActionItem) {
			return _s.equals(((EANActionItem)o).getKey());
		}
		return false;
	}

	/**
     * @see com.ibm.eannounce.eForms.navigate.tree.AbstractNode#getActionItem()
     * @author Anthony C. Liberto
     */
    public EANActionItem getActionItem() {
		Object o = getUserObject();
		if (o != null && o instanceof EANActionItem) {
			return (EANActionItem)o;
		}
		return null;
	}

	/**
     * eaccess
     * @return
     * @author Anthony C. Liberto
     */
    public static EAccess eaccess() {
		return EAccess.eaccess();
	}

	/**
     * getString
     * @param _code
     * @return
     * @author Anthony C. Liberto
     */
    public String getString(String _code) {
		return eaccess().getString(_code);
	}

	/**
     * isArmed
     * @param _code
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isArmed(String _code) {
		return EAccess.isArmed(_code);
	}

	/**
     * isTestMode
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isTestMode() {
		return EAccess.isTestMode();
	}

	/**
	 * Automatically generated method: hashCode
	 *
	 * @return int
	 */
	public int hashCode() {
	    return 0;
	}
}
