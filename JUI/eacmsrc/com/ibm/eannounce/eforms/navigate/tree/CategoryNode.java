/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: CategoryNode.java,v $
 * Revision 1.2  2008/01/30 16:27:06  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:44:58  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:03  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:59:05  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/01/31 20:47:46  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/26 23:42:28  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:44  tony
 * This is the initial load of OPICM
 *
 * Revision 1.1.1.1  2003/03/03 18:03:49  tony
 * This is the initial load of OPICM
 *
 * Revision 1.2  2002/11/07 16:58:32  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.navigate.tree;
import COM.ibm.eannounce.objects.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class CategoryNode extends AbstractNode {
	private static final long serialVersionUID = 1L;
	private String code = null;

	/**
     * CategoryNode
     * @param _code
     * @param _val
     * @author Anthony C. Liberto
     */
    public CategoryNode(String _code, String _val) {
		super(_val);
		setCode(_code);
		return;
	}

	/**
     * @see java.lang.Object#toString()
     * @author Anthony C. Liberto
     */
    public String toString() {
		Object o = getUserObject();
		if (o != null) {
			return o.toString();
		}
		return "CategoryNode is Null";
	}

	private void setCode(String _s) {
		code = new String(_s);
	}

	/**
     * getCode
     * @return
     * @author Anthony C. Liberto
     */
    public String getCode() {
		return code;
	}

	/**
     * equals
     * @author Anthony C. Liberto
     * @return boolean
     * @param _s
     */
    public boolean equals(String _s) {
		return code.equals(_s);
	}

	/**
     * @see com.ibm.eannounce.eForms.navigate.tree.AbstractNode#getActionItem()
     * @author Anthony C. Liberto
     */
    public EANActionItem getActionItem() {
		return null;
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
