//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.rend;

import com.ibm.eacm.objects.Routines;

/**
 * class for rendering LongText table/form cells using JLabels.  
 * @author Wendy Stimpson
 */
//$Log: LongRenderer.java,v $
//Revision 1.1  2012/09/27 19:39:24  wendy
//Initial code
//
public class LongRenderer extends LabelRenderer {
	private static final long serialVersionUID = 1L;
	
    /**
     * allow derived classes to split the text
     * @param txt
     * @return
     */
    protected String adjustText(String txt){
      	//force line wrap at 80 chars
    	return Routines.convertToMultilineHTML(Routines.addLineWraps(txt, 80));
    }  
}
