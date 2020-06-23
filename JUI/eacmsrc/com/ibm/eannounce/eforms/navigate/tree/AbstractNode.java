/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: AbstractNode.java,v $
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
import javax.swing.tree.*;
import COM.ibm.eannounce.objects.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public abstract class AbstractNode extends DefaultMutableTreeNode {
	/**
     * AbstractNode
     * @param _o
     * @author Anthony C. Liberto
     */
    public AbstractNode(Object _o) {
		super(_o);
	}
	/**
     * @see java.lang.Object#toString()
     * @author Anthony C. Liberto
     */
    public abstract String toString();
	/**
     * equals
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    public abstract boolean equals(String _s);
	/**
     * getActionItem
     * @return
     * @author Anthony C. Liberto
     */
    public abstract EANActionItem getActionItem();
}
