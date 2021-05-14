//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.rend;

import java.util.StringTokenizer;


/**
 * class for rendering gridtable cells using JLabels.  
 * @author Wendy Stimpson
 */
//$Log: GridRenderer.java,v $
//Revision 1.1  2012/09/27 19:39:24  wendy
//Initial code
//
public class GridRenderer extends LabelRenderer {
	private static final long serialVersionUID = 1L;
	
    /**
     * grid only shows a single line of text
     * @param txt
     * @return
     */
    protected String adjustText(String txt){
      	//only return first line
    	StringTokenizer st = new StringTokenizer(txt,NEWLINE);
    	while(st.hasMoreTokens()){
    		txt = st.nextToken();
    		break;
    	}
    	return txt;
    }  
}