//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.ui;


import javax.swing.*;
import javax.swing.text.*;
import java.awt.event.*;

/**
 * spinner for integers
 * @author Wendy Stimpson
 */
//$Log: IntSpinner.java,v $
//Revision 1.1  2012/09/27 19:39:11  wendy
//Initial code
//
public class IntSpinner extends JSpinner {
	private static final long serialVersionUID = 1L;
	
	public IntSpinner(int value, int minimum, int maximum, int stepSize){
		super(new SpinnerNumberModel(value, minimum, maximum, stepSize));
		
		//prevent invalid typed chars and push them into the spinnermodel immediately
		DefaultFormatter defForm = (DefaultFormatter)((JSpinner.DefaultEditor)this.getEditor()).getTextField().getFormatter();
		defForm.setAllowsInvalid(false);	
		defForm.setCommitsOnValidEdit(true);
		defForm.setOverwriteMode(true);
	}

    protected boolean processKeyBinding(KeyStroke _ks, KeyEvent _ke, int _cond, boolean _press) {
    	boolean done = super.processKeyBinding(_ks, _ke, _cond, _press);
    	if (_ke != null) {
     		if (_ke.getKeyCode() == KeyEvent.VK_ENTER) {
    			return false;
    		}
    	}
    	return done;
    }
	public int getInt(){
		Number num = (Number)getValue();
		return num.intValue();
	}
	public void dereference() {
		// initAccessibility(null);
		removeAll();
		setUI(null);
	}
}
