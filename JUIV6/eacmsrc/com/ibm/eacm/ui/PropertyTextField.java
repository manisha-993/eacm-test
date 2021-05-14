//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.ui;

import java.awt.event.KeyEvent;
import javax.swing.text.*;
import javax.swing.*;

/**
 * This is a text field that fires property change events when contents are updated
 *
 *
 * Note that text is not a bound property, so no <code>PropertyChangeEvent
 * </code> is fired when it changes. To listen for changes to the text,
 * use <code>DocumentListener</code>.
 * @author Wendy Stimpson
 */
//$Log: PropertyTextField.java,v $
//Revision 1.1  2012/09/27 19:39:11  wendy
//Initial code
//
public class PropertyTextField extends JTextField
{
	private static final long serialVersionUID = 1L;
	/**
	 * min
	 */
    public static final int MINIMUM_TYPE = 0;
	/**
	 * min =
	 */
    public static final int MINIMUM_EQUAL_TYPE = 2;

    public static final String ACTIVATE = "ACTIVATE";
    public static final String DEACTIVATE = "DEACTIVATE";

	private String status = DEACTIVATE;
	private int type = -1;
	private int maxL = -1;
	private int minL = -1;

    /**
     * @param col
     * @param pcl
     * @param type2
     * @param parm
     */
    public PropertyTextField(int col,int type2, int parm) {
		super(col);
		this.type =type2;
		minL = parm;

		// allow esc to get to the container
		getInputMap().put(KeyStroke.getKeyStroke((char)KeyEvent.VK_ESCAPE), "none");
	}

	/**
     * isActive
     * @return
     *
     */
    public boolean isActive() {
		return status.equals(ACTIVATE);
	}

	/**
     * setMaximumLength
     * @param i
     *
     */
    public void setMaximumLength(int i) {
		maxL = i;
	}

    /**
     * release memory
     */
    public void dereference(){
    	removeAll();
    	setUI(null);
    	status = null;
    }

	/**
     * @see javax.swing.JTextField#createDefaultModel()
     *
     */
    protected Document createDefaultModel() {
		return new MaxDoc();
	}

	private class MaxDoc extends PlainDocument {
		private static final long serialVersionUID = 1L;
		public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
			if (str == null) {
                return;
			}
			if (maxL < 0 || (getLength() + str.length()) <= maxL) {
				super.insertString(offs, str, a);
				evaluate();
			} else {
			    UIManager.getLookAndFeel().provideErrorFeedback(null);
			}
		}

		public void remove(int offs, int len) throws BadLocationException {
			super.remove(offs,len);
			evaluate();
		}
	}
	private void evaluate() {
		if (minL >= 0) {
			int length = getText().length();
			if (type == MINIMUM_TYPE) {
				if (length <= minL) {
					setStatus(DEACTIVATE);
				} else if (length > minL ) {
					setStatus(ACTIVATE);
				}
			} else if (type == MINIMUM_EQUAL_TYPE) {
				if (length < minL) {
					setStatus(DEACTIVATE);
				} else if (length >= minL ) {
					setStatus(ACTIVATE);
				}
			}
		}
	}

	private void setStatus(String s) {
		if (!status.equals(s)) {
			String origstatus = status;
			status = s;
			firePropertyChange(s,origstatus,s);
		}
	}
}

