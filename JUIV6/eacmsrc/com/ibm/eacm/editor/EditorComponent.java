//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.editor;

import com.ibm.eacm.edit.form.FormCellPanel;

/**
 *used to identify the editing component in a form
 *@author Wendy Stimpson
 *
 */
//$Log: EditorComponent.java,v $
//Revision 1.1  2012/09/27 19:39:21  wendy
//Initial code
//
public interface EditorComponent {
	FormCellPanel getFormCellPanel();
}
