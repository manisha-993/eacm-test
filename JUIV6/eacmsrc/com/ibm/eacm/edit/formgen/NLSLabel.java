//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.edit.formgen;


import java.awt.*;
import java.util.HashMap;

import javax.swing.JLabel;

/**
 * this is the title that reflects nls ids
 * @author Wendy Stimpson
 */
// $Log: NLSLabel.java,v $
// Revision 1.1  2012/09/27 19:39:23  wendy
// Initial code
//
public class NLSLabel extends JLabel {
	private static final long serialVersionUID = 1L;
	private HashMap<Integer, String> titleMap = new HashMap<Integer, String>();
	private Integer defNLS = new Integer(1);

	/**
     * add Title for specified nls
     * @param s
     * @param _nls
     */
    protected void addTitle(String s, int _nls) {
		titleMap.put(new Integer(_nls),s);
	}

	/**
     * @see java.awt.Component#getPreferredSize()
     */
    public Dimension getPreferredSize() {
    	Dimension d = new Dimension();
		FontMetrics fm = getFontMetrics(getFont());
		d.setSize(getStringWidth(fm), fm.getHeight());
		return d;
	}

	private int getStringWidth(FontMetrics _fm) {
		String txt = getText();
		if (txt != null) {
			return (_fm.stringWidth(txt) + 4);
		}
		return 10;
	}

	/**
     * set title for this NLS
     * @param _nls
     */
    protected void setNLSTitle(int _nls) {
    	String title = "";
    	Integer nls = new Integer(_nls);
		if (titleMap.containsKey(nls)) {
			title = titleMap.get(nls);
		} else if (titleMap.containsKey(defNLS)) {
			title = titleMap.get(defNLS);
		}
		setText(title);
	}
    
    /**
     * release memory
     */
    protected void dereference(){
    	titleMap.clear();
    	titleMap = null;
    	defNLS = null;
    	
    	removeAll();
    	setUI(null);
    }
}
