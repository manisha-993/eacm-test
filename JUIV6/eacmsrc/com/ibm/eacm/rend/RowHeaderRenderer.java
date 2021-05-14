//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
package com.ibm.eacm.rend;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.JList;

import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.table.JTableHeader;

import com.ibm.eacm.objects.EACMGlobals;
import com.ibm.eacm.table.*;

/*************
* 
* this renders the JList used for the rowheader - used for freeze action and also matrixaction
* @author Wendy Stimpson		
*/
//$Log: RowHeaderRenderer.java,v $
//Revision 1.1  2012/09/27 19:39:24  wendy
//Initial code
//
public class RowHeaderRenderer extends JLabel implements ListCellRenderer, EACMGlobals {
	private static final long serialVersionUID = 1L;
	private RSTTable table = null;

	private int minWidth = 0;
	/**
	 * must allow for column header width
	 * @param title
	 */
	public void setMinWidth(String title){
		minWidth = widthOf(((RSTTableModel)table.getModel()).getTruncName(title))+
			UIManager.getIcon("Table.ascendingSortIcon").getIconWidth(); 
	}
	public void setMinWidth(int width){
		minWidth = width+
			UIManager.getIcon("Table.ascendingSortIcon").getIconWidth(); 
	}
	public void dereference(){
		table = null;
		removeAll();
		setUI(null);
	}
	public RowHeaderRenderer(RSTTable tbl) {
		table = tbl;
		JTableHeader header = table.getTableHeader();
		//setBorder(UIManager.getBorder("TableHeader.cellBorder"));
		setOpaque(true);
		setBorder(UIManager.getBorder(RAISED_BORDER_KEY));
		//setHorizontalAlignment(CENTER);
		setForeground(header.getForeground());
		setBackground(header.getBackground());
		setFont(header.getFont());
	}

	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		setText((value == null) ? "" : value.toString());

		// this is used to determine the width of the list
		setPreferredSize(new Dimension(
				Math.max(minWidth, widthOf(((RSTTableModel)table.getModel()).getTruncName(getText()))),
				table.getRowHeight(index)));
		return this;
	}
	
	private int widthOf(Object _o) {
		if (_o != null) {
			FontMetrics fm = getFontMetrics(getFont());
			return fm.stringWidth(_o.toString()) + 7;
		}
		return 27;
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