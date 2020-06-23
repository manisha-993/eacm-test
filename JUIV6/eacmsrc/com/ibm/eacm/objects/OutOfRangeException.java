//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.objects;

/**

 */
// $Log: OutOfRangeException.java,v $
// Revision 1.1  2012/09/27 19:39:13  wendy
// Initial code
//
public class OutOfRangeException extends Exception {
	private static final long serialVersionUID = 1L;

    /**
     * outOfRangeException
     * @param _s
     */
    public OutOfRangeException(String _s) {
        super(_s);
    }
}
