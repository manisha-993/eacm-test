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

import com.ibm.eacm.objects.Routines;
import com.ibm.eacm.table.*;

/**
 * Base class for rendering table cells using JLabels.  Derived from DefaultTableCellRenderer for
 * performance reasons.
 * @author Wendy Stimpson
 */
//$Log: LabelRenderer.java,v $
//Revision 1.1  2012/09/27 19:39:24  wendy
//Initial code
//
public class LabelRenderer extends DefaultTableCellRenderer implements com.ibm.eacm.objects.EACMGlobals {
	private static final long serialVersionUID = 1L;

	private String selectionFgKey=null;
	private String selectionBgKey=null;
	private String fgKey=null;
	private String bgKey=null;
	private String selectedBorderKey = SELECTED_BORDER_KEY;
	private String notSelectedBorderKey = UNDERLINE_BORDER_KEY;
	private String hasFocusBorderKey = FOCUS_BORDER_KEY;
	private String noFocusBorderKey = null;

    /**
     * LabelRenderer
     *
     */
    public LabelRenderer() {
    	setVerticalAlignment(SwingConstants.TOP);
    }

    /**
     * if these keys are not null, use them to find the selection foreground and background colors saved in
     * UIManager
     * @param fgKey
     * @param bgKey
     */
    public void setSelectionColorKeys(String fgKey, String bgKey){
    	selectionFgKey = fgKey;
    	selectionBgKey = bgKey;
    }
    /**
     * if these keys are not null, use them to find the foreground and background colors saved in
     * UIManager
     * @param fgKey2
     * @param bgKey2
     */
    public void setColorKeys(String fgKey2, String bgKey2){
    	this.fgKey = fgKey2;
    	this.bgKey = bgKey2;
    }
    /**
     * if these keys are not null, use them to find the selected and not selected borders saved in
     * UIManager
     * @param selKey
     * @param unselKey
     */
    public void setBorderKeys(String selKey, String unselKey){
    	selectedBorderKey = selKey;
    	notSelectedBorderKey = unselKey;
    }

    /**
     * if these keys are not null, use them to find the focus and not focused borders saved in
     * UIManager
     * @param selKey
     * @param focusKey
     * @param nofocusKey
     */
    public void setFocusBorderKeys(String focusKey, String nofocusKey){
    	hasFocusBorderKey = focusKey;
    	noFocusBorderKey = nofocusKey;
    }
	/**
	 * release memory
	 */
	public void dereference(){
		selectionFgKey=null;
		selectionBgKey=null;
		fgKey=null;
		bgKey=null;
		selectedBorderKey = null;
		notSelectedBorderKey = null;
		hasFocusBorderKey = null;
		noFocusBorderKey = null;
		removeAll();
		setUI(null);
	}
    /**
     * allow derived classes to split the text
     * @param txt
     * @return
     */
    protected String adjustText(String txt){
    	//this is a simple way to get a multiline label
    	return Routines.convertToMultilineHTML(txt);
    }

    /**
     * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
     */
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            if (selectedBorderKey!=null){
            	setBorder(UIManager.getBorder(selectedBorderKey));
            }
        } else {
            if(notSelectedBorderKey!=null){
            	setBorder(UIManager.getBorder(notSelectedBorderKey));
            }
        }

        setForeground(getFgColor(table, isSelected));
        setBackground(getBgColor(table, isSelected));

        setFont(table.getFont());

        if (hasFocus) {
        	if (hasFocusBorderKey!=null){
        		setBorder(UIManager.getBorder(hasFocusBorderKey));
        	}
        }else{
        	if(noFocusBorderKey!=null){
        		setBorder(UIManager.getBorder(noFocusBorderKey));
        	}
        }

        String txt="";
        if(value!=null){
        	txt = adjustText(value.toString());
        }

        setText(txt);

        if (table instanceof BaseTable) {
            ((BaseTable) table).updateContext(this, row, column);
        }

        return this;
    }

    /**
     * get the foreground color to use in the table cell
     * @param table
     * @param isSelected
     * @return
     */
    private Color getFgColor(JTable table, boolean isSelected){
    	Color fg = null;
    	if (isSelected) {
    		if (selectionFgKey==null){
    			fg = table.getSelectionForeground();
    		}else{
    			fg = UIManager.getColor(selectionFgKey);
    			if (fg==null){
    				fg = table.getSelectionForeground();
    			}
    		}
    	} else {
    		if (fgKey!=null){
    			fg = UIManager.getColor(fgKey);
    		}
    		if (fg==null){
				fg = table.getForeground();
			}
    	}
    	return fg;
    }
    /**
     * get the background color to use in the table cell
     * @param table
     * @param isSelected
     * @return
     */
    private Color getBgColor(JTable table, boolean isSelected){
    	Color bg = null;
    	if (isSelected) {
    		if (selectionBgKey==null){
    			bg = table.getSelectionBackground();
    		}else{
    			bg = UIManager.getColor(selectionBgKey);
    			if (bg==null){
    				bg = table.getSelectionBackground();
    			}
    		}
    	} else {
    		if (bgKey!=null){
    			bg = UIManager.getColor(bgKey);
    		}
    		if (bg==null){
				bg = table.getBackground();
			}
    	}
    	return bg;
    }
    //for form use
    /** moved to formxxrenderer classes, delete when all form is working
     * get renderer for this value
     * /
    public Component getRendererComponent(JComponent comp, Object value, boolean hasFocus) {
        setForeground(getFgColor(comp));
        setBackground(getBgColor(comp));

        setFont(comp.getFont());

        if (hasFocus) {
        	if (hasFocusBorderKey!=null){
        		setBorder(UIManager.getBorder(hasFocusBorderKey));
        	}
        }else{
        	if(noFocusBorderKey!=null){
        		setBorder(UIManager.getBorder(noFocusBorderKey));
        	}
        }

        String txt="";
        if(value!=null){
        	txt = adjustText(value.toString());
        }

        setText(txt);

        return this;
    }
    /**
     * get the foreground color to use in the form
     * @param comp
     * @return
     * /
    private Color getFgColor(JComponent comp){
    	Color fg = null;

    	if (fgKey!=null){
    		fg = UIManager.getColor(fgKey);
    	}
    	if (fg==null){
    		fg = comp.getForeground();
    	}

    	return fg;
    }
    /**
     * get the background color to use in the form
     * @param comp
     * @return
     * /
    private Color getBgColor(JComponent comp){
    	Color bg = null;

    	if (bgKey!=null){
    		bg = UIManager.getColor(bgKey);
    	}
    	if (bg==null){
    		bg = comp.getBackground();
    	}

    	return bg;
    }*/
}
