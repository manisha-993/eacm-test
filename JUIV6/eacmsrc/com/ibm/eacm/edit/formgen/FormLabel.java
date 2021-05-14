//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.edit.formgen;

import com.ibm.eacm.objects.EACMGlobals;
import com.ibm.eacm.objects.Routines;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.border.Border;

/**
 * label used for each cell in the form table
 * @author Wendy Stimpson
 */
// $Log: FormLabel.java,v $
// Revision 1.1  2012/09/27 19:39:23  wendy
// Initial code
//
public class FormLabel extends JLabel implements EACMGlobals
{
	private static final long serialVersionUID = 1L;

    private String key = null;
    private boolean found = false;

    /**
     * called by derived classes
     * @param key2
     */
    protected FormLabel(String key2) {
        key = key2;
    }
    
    /**
     * formLabel - used for constants 
     * @param s
     * @param key2
     */
    protected FormLabel(String s, String key2) {
        super(s);
        key = key2;
    }

    /**
     * release memory
     */
    protected void dereference(){
        key = null;
    	removeAll();
    	setUI(null);
    }

    /* (non-Javadoc)
     * convert to multiline label if needed
     * @see javax.swing.JLabel#setText(java.lang.String)
     */
    public void setText(String text) {
    	super.setText(Routines.convertToMultilineHTML(text));
    }

    /**
     * getKey
     * @return
     */
    protected String getKey() {
        return key;
    }


    /**
     * find
     * @param strFind
     * @param bCase
     * @return
     */
    public boolean find(String strFind, boolean bCase) {
        boolean bFound = false;
        if (!isShowing()) {
            return false;
        }

        bFound = Routines.find(getText(), strFind, bCase);
        if (bFound) { 
            found = true; 
            setBorder(getCurrentBorder());
            requestFocusInWindow();
        }
        return found;
    }

    /**
     * isFound
     * @return
     */
    protected boolean isFound() {
        return found;
    }

    /**
     */
    public void resetFound() {
        if (!found) {
            return;
        }
        found = false;
        setBorder(getCurrentBorder());
    }

    private Border getCurrentBorder() {
        if (isFound()) {
            return com.ibm.eacm.ui.FoundLineBorder.FOUND_BORDER;
        }
        return UIManager.getBorder(EMPTY_BORDER_KEY);
    }

}
