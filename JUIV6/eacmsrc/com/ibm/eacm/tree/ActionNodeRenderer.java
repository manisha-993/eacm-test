//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.tree;

import javax.swing.Icon;
import javax.swing.UIManager;

import com.ibm.eacm.rend.TreeCellRend;


/*********************************************************************
 * This is used to render nav category and action nodes
 */
//$Log: ActionNodeRenderer.java,v $
//Revision 1.1  2012/09/27 19:39:18  wendy
//Initial code
//
public class ActionNodeRenderer extends TreeCellRend {
	private static final long serialVersionUID = 1L;
	
	
    /* (non-Javadoc)
     * @see com.ibm.eacm.rend.TreeCellRend#getTreeIcon(java.lang.Object, boolean, boolean)
     */
    protected Icon getTreeIcon(Object val, boolean expand, boolean leaf){
    	Icon icon = null;
        if (leaf) {
            icon = UIManager.getIcon("eannounce.navtree.leafIcon");
        } else if (expand) {
            icon = UIManager.getIcon("eannounce.navtree.openIcon");
        } else {
            icon = UIManager.getIcon("eannounce.navtree.closedIcon");
        }
    	return icon;
    }
}