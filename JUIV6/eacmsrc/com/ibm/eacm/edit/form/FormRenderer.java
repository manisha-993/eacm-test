//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.edit.form;

import java.awt.Component;

import javax.swing.JComponent;

/*********************************************************************
 * render the form
 * @author Wendy Stimpson
 */
//$Log: FormRenderer.java,v $
//Revision 1.1  2012/09/27 19:39:16  wendy
//Initial code
//
public interface FormRenderer {
	Component getRendererComponent(JComponent comp, Object value, boolean hasFocus);
}
