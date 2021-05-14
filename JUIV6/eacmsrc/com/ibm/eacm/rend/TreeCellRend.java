//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.rend;

import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.TreeCellRenderer;

/*********************************************************************
 * This is used to render tree cells
 * @author Wendy Stimpson
 */
//$Log: TreeCellRend.java,v $
//Revision 1.1  2012/09/27 19:39:24  wendy
//Initial code
//
public class TreeCellRend extends JLabel implements TreeCellRenderer, com.ibm.eacm.objects.EACMGlobals {
	private static final long serialVersionUID = 1L;

    /**
     * constructor
     */
    public TreeCellRend() {
        setOpaque(true);
    }

    /**
     * allow derived classes to provide an icon
     * @param val
     * @param expand
     * @param leaf
     * @return
     */
    protected Icon getTreeIcon(Object val, boolean expand, boolean leaf){
    	return null;
    }

    /**
     * @see javax.swing.tree.TreeCellRenderer#getTreeCellRendererComponent(javax.swing.JTree, java.lang.Object, boolean, boolean, boolean, int, boolean)
     */
    public Component getTreeCellRendererComponent(JTree tree, Object val, boolean select, boolean expand, boolean leaf, int row, boolean focus) {
        if (select) {
            setForeground(UIManager.getColor("Tree.selectionForeground"));
            setBackground(UIManager.getColor("Tree.selectionBackground"));
            setBorder(UIManager.getBorder(SELECTED_BORDER_KEY));
        } else {
            setForeground(tree.getForeground());
            setBackground(tree.getBackground());
           // setBorder(null);
        }

        setFont(tree.getFont());

        if (focus) {
            setBorder(UIManager.getBorder(FOCUS_BORDER_KEY));
        } else {
            setBorder(UIManager.getBorder(NOFOCUS_BORDER_KEY));
        }
        setText((val == null) ? "" : val.toString());
        Icon icon  = getTreeIcon(val,expand,leaf);
        setIcon(icon);

        return this;
    }
    /*
     * The following methods are overridden as a performance measure to
     * to prune code-paths are often called in the case of renders
     * but which we know are unnecessary.  Great care should be taken
     * when writing your own renderer to weigh the benefits and
     * drawbacks of overriding methods like these.
     */

    /**
     * Overridden for performance reasons.
     * See the <a href="#override">Implementation Note</a>
     * for more information.
     */
    public boolean isOpaque() {
    	Color back = getBackground();
    	Component p = getParent();
    	if (p != null) {
    		p = p.getParent();
    	}

    	// p should now be the JTree.
    	boolean colorMatch = (back != null) && (p != null) &&
    	back.equals(p.getBackground()) &&
    	p.isOpaque();
    	return !colorMatch && super.isOpaque();
    }
    /**
     * Overridden for performance reasons.
     * See the <a href="#override">Implementation Note</a>
     * for more information.
     *
     * @since 1.5
     */
    public void invalidate() {}

    /**
     * Overridden for performance reasons.
     * See the <a href="#override">Implementation Note</a>
     * for more information.
     */
    public void validate() {}

    /**
     * Overridden for performance reasons.
     * See the <a href="#override">Implementation Note</a>
     * for more information.
     */
    public void revalidate() {}

    /**
     * Overridden for performance reasons.
     * See the <a href="#override">Implementation Note</a>
     * for more information.
     */
    public void repaint(long tm, int x, int y, int width, int height) {}

    /**
     * Overridden for performance reasons.
     * See the <a href="#override">Implementation Note</a>
     * for more information.
     */
    public void repaint(Rectangle r) { }

    /**
     * Overridden for performance reasons.
     * See the <a href="#override">Implementation Note</a>
     * for more information.
     *
     * @since 1.5
     */
    public void repaint() {
    }
    /**
     * Overridden for performance reasons.
     * See the <a href="#override">Implementation Note</a>
     * for more information.
     */
    public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) { }
}
