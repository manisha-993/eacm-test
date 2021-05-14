// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
package com.ibm.eacm.rend;
import java.awt.*;

import javax.swing.*;

import com.ibm.eacm.nav.NavHistBox;

/**
 * this is the renderer for history box, a picklist is displayed differently
 * @author Wendy Stimpson
 */
//$Log: HistRend.java,v $
//Revision 1.1  2012/09/27 19:39:24  wendy
//Initial code
//
public class HistRend extends JLabel implements ListCellRenderer {
	private static final long serialVersionUID = 1L;
	private NavHistBox navHist = null;
	
    public HistRend(NavHistBox nh) {
    	navHist = nh;
        setOpaque(true);
    }

    /**
     * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
     */
    public Component getListCellRendererComponent(JList jc, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if (jc == null) {
	        setText("");
	        return this;
		}

        if (value instanceof NavHistBox.HistItem) {
            Font f = getFont();
            if (navHist.isPickList(value)) {
                if (isSelected) {
                    setBackground(jc.getSelectionBackground());
                    setForeground(Color.white);
                } else {
                    setBackground(Color.white);
                    setForeground(Color.blue);
                }
                setFont(f.deriveFont(Font.BOLD + Font.ITALIC));
            } else if (isSelected) {
                setBackground(jc.getSelectionBackground());
                setForeground(jc.getSelectionForeground());
                setFont(f.deriveFont(Font.PLAIN));
            } else {
                setBackground(jc.getBackground());
                setForeground(jc.getForeground());
                setFont(f.deriveFont(Font.PLAIN));
            }
        } else if (isSelected) {
            setBackground(jc.getSelectionBackground());
            setForeground(jc.getSelectionForeground());
        } else {
            setBackground(jc.getBackground());
            setForeground(jc.getForeground());
        }
        if (value == null) {
            setText("");

        } else {
            setText(value.toString());
        }
        return this;
    }
    
    public void dereference(){
    	navHist = null;
    	removeAll();
    	setUI(null);
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
