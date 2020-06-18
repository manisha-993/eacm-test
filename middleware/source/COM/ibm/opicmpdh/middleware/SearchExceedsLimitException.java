// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
package COM.ibm.opicmpdh.middleware;

/**********************************************************************************
 * This class makes search limit exception unique so JUI can recognize it.
 *
 */
// $Log: SearchExceedsLimitException.java,v $
// Revision 1.1  2008/10/10 13:17:13  wendy
// Allow UI to recognize search limit failure to improve performance
//
public class SearchExceedsLimitException extends MiddlewareRequestException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * Constructs a <code>SearchExceedsLimitException</code> with the specified detail message
     */
    public SearchExceedsLimitException(String s) {
        super(s);
    }
}
