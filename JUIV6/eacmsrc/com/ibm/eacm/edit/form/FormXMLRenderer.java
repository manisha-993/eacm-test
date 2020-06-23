//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.edit.form;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.UIManager;

/**
 * Base class for rendering form xml cells using JTextArea.   This allows user to select the text 
 * @author Wendy Stimpson
 */
//$Log: FormXMLRenderer.java,v $
//Revision 1.1  2012/09/27 19:39:16  wendy
//Initial code
//
public class FormXMLRenderer extends JEditorPane implements com.ibm.eacm.objects.EACMGlobals,FormRenderer {
	private static final long serialVersionUID = 1L;
	
	private String fgKey=null;
	private String bgKey=null;
	private String hasFocusBorderKey = FOCUS_BORDER_KEY;
	private String noFocusBorderKey = null;
	
	/**
	 * constructor
	 */
	public FormXMLRenderer(){
		setEditable(false);
		setBorder(null);
		setForeground(UIManager.getColor("Label.foreground"));
		setBackground(UIManager.getColor("Label.background"));
		setFont(UIManager.getFont("Label.font"));
		setContentType("text/html");
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
     * get renderer for this value
     */
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
        
        String txt=null;
        if(value!=null){
        	txt = value.toString();
        }

        setText(txt);
        
        setCaretPosition(0); // scroll to the top
        
        return this;
    }
    /**
     * get the foreground color to use in the form
     * @param comp
     * @return
     */
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
     */
    private Color getBgColor(JComponent comp){
    	Color bg = null;

    	if (bgKey!=null){
    		bg = UIManager.getColor(bgKey);
    	}  
    	if (bg==null){
    		bg = comp.getBackground();
    	}

    	return bg;
    }
    
	/**
	 * release memory
	 */
	public void dereference(){
		fgKey=null;
		bgKey=null;
		hasFocusBorderKey = null;
		noFocusBorderKey = null;
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

		// p should now be the FormCellPanel. 
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
    public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {}
}
