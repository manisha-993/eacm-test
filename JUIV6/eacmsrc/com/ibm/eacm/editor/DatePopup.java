//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.editor;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

/**
 * a popup is used to get that behavior for the DateSelector panel
 * @author Wendy Stimpson
 */
//$Log: DatePopup.java,v $
//Revision 1.1  2012/09/27 19:39:21  wendy
//Initial code
//
public class DatePopup extends JPopupMenu {
	private static final long serialVersionUID = 1L;
	
	private DateSelector dateSelector = null;

    /**
     * @param kl - needed when dateselector is used in a popup, when used in a dialog enter keys go 
     * directly to the date buttons 
     */
    public DatePopup(KeyListener kl) {
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		setBorderPainted(true);
		setBorder(BorderFactory.createLineBorder(Color.black));
		setOpaque(false);
		dateSelector = new DateSelector(DateEditor.DateType.ANY_DATE,kl);
		add(dateSelector);
		setDoubleBuffered(true);

		pack();
		
	}
    
    public void setVisible(boolean b) {
    	super.setVisible(b);
    	if(b){
    		dateSelector.requestFocus(); // this allows focus to go to date selector so keystrokes go there
    	}
    }

	/**
     * dereference
     */
    public void dereference() {
    	dateSelector.dereference();
    	dateSelector = null;
		removeAll();
		setUI(null);
	}

	/**
     * get date selector
     * @return
     */
	public DateSelector getDateSelector() {
		return dateSelector;
	}
}
