//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.rend;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.*;

/**
 * Used to render locked boolean cells
 * @author Wendy Stimpson
 */
//$Log: LockedBooleanRend.java,v $
//Revision 1.1  2012/09/27 19:39:24  wendy
//Initial code
//
public class LockedBooleanRend extends JCheckBox implements TableCellRenderer, com.ibm.eacm.objects.EACMGlobals {
	private static final long serialVersionUID = 1L;
	/**
     * lockedBooleanRend
     */
    public LockedBooleanRend() {
    	setOpaque(true);
        setHorizontalAlignment(JLabel.CENTER);
    }
    
    public void dereference(){
    	removeAll();
    	setUI(null);
    }

    /**
     * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
     */
    public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, int row, int column) {
 
    	setForeground(UIManager.getColor(PREF_COLOR_LOCK));//ColorPref.getLockColor());
    	setBackground(UIManager.getColor(PREF_COLOR_LOCK));//ColorPref.getLockColor());

        if (hasFocus) {
			setBorder(com.ibm.eacm.ui.FoundLineBorder.FOUND_FOCUS_BORDER);
		} else {
			setBorder(com.ibm.eacm.ui.FoundLineBorder.FOUND_BORDER);
		}
  
        setSelected((value != null && ((Boolean)value).booleanValue()));
		if (table instanceof com.ibm.eacm.table.BaseTable) {
			((com.ibm.eacm.table.BaseTable) table).updateContext(this, row, column);
		}
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

    	// p should now be the JTable. 
    	boolean colorMatch = (back != null) && (p != null) && 
    	back.equals(p.getBackground()) && 
    	p.isOpaque();
    	return !colorMatch && super.isOpaque(); 
    }
    /**
     * Overridden for performance reasons.
     * See the <a href="#override">Implementation Note</a> 
     * for more information.
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
     */
    public void repaint() {}
    /**
     * Overridden for performance reasons.
     * See the <a href="#override">Implementation Note</a> 
     * for more information.
     */
    public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) { }    
}

