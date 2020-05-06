/* Licensed Materials -- Property of IBM
 * (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
 * The source code for this program is not published or otherwise divested of
 * its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
 *
 * Created on Feb 28, 2005
 *
 */
package com.ibm.transform.oim.eacm.xalan;

/**
 * Implement this interface to alter the return code of XSLReportABR
 * <pre>
 * $Log: ReturnCode.java,v $
 * Revision 1.3  2008/08/26 21:10:46  wendy
 * Updated ReturnCode interface with message
 *
 * Revision 1.2  2006/01/26 15:28:14  wendy
 * AHE copyright
 *
 * Revision 1.1  2005/09/08 19:09:29  wendy
 * New pkg
 *
 * Revision 1.1  2005/03/02 18:19:23  chris
 * Classes implement this interface to change the return code of XSLReportABR
 *
 * </pre>
 * @author cstolpe
 */
public interface ReturnCode {
    /**
     * Return true if passed
     * @return
     */
    boolean hasPassed();
    /**
     * Get cause of error or information if possible
     * @return
     */
    String getMessage(); 
}
