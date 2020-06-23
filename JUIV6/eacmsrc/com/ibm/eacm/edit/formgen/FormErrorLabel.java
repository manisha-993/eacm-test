//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.edit.formgen;

import java.awt.Color;

/**
 * label used for each cell in the form table
 * @author Wendy Stimpson
 */
// $Log: FormErrorLabel.java,v $
// Revision 1.1  2012/09/27 19:39:23  wendy
// Initial code
//
public class FormErrorLabel extends FormLabel 
{
	private static final long serialVersionUID = 1L;
    
    /**
     * formLabel - used for errors
     * @param _s
     * @param _key
     */
    protected FormErrorLabel(String _s, String _key) {
        super(_key);
        super.setForeground(Color.red);
        setText(_s);
    }

    /**
     * @see java.awt.Component#setBackground(java.awt.Color)
     */
    public void setBackground(Color _c) {}

    /**
     * @see java.awt.Component#setForeground(java.awt.Color)
     */
    public void setForeground(Color _c) {}
}
