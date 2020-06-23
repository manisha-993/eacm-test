//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.rend;

/**
 * class for rendering locktable cells using JLabels, new lines are removed and then truncated 
 * @author Wendy Stimpson
 */
//$Log: LineRenderer.java,v $
//Revision 1.1  2012/09/27 19:39:24  wendy
//Initial code
//
public class LineRenderer extends LabelRenderer {
	private static final long serialVersionUID = 1L;
	
    /**
     * grid only shows a single line of text
     * @param txt
     * @return
     */
    protected String adjustText(String txt){
    	String txt2 = txt.replaceAll(NEWLINE, "");
       	txt2 = txt2.replaceAll(RETURN, "");

    	return txt2;
    }  
}