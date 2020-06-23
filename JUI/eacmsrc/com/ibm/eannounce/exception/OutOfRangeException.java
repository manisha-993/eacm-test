/**
 * Copyright (c) 2003-2004 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2003/05/30
 * @author Anthony C. Liberto
 *
 * $Log: OutOfRangeException.java,v $
 * Revision 1.2  2008/01/30 16:27:11  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:46:56  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:55  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:59:12  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/01/28 17:54:21  tony
 * JTest Fromat step 2
 *
 * Revision 1.2  2005/01/26 17:18:51  tony
 * JTest Formatting modifications
 *
 * Revision 1.1.1.1  2004/02/10 17:00:28  tony
 * This is the initial load of OPICM
 *
 * Revision 1.1  2003/06/02 16:46:04  tony
 * 51024 added outOfRangeException, thus preventing multiple
 * messages.
 *
 *
 */
package com.ibm.eannounce.exception;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class OutOfRangeException extends Exception {
	private static final long serialVersionUID = 1L;
	/**
     * outOfRangeException
     * @author Anthony C. Liberto
     */
    public OutOfRangeException() {
        super();
    }
    /**
     * outOfRangeException
     * @param _s
     * @author Anthony C. Liberto
     */
    public OutOfRangeException(String _s) {
        super(_s);
    }
}
