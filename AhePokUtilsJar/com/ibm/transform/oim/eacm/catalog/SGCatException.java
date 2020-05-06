// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.catalog;

import java.io.IOException;

/**********************************************************************************
 * Exception for missing partnumber
 *@author     Wendy Stimpson
 *@created    Sept 18, 2006
 */
// $Log: SGCatException.java,v $
// Revision 1.2  2008/01/22 16:52:19  wendy
// Added serialversionuid to remove RSA warnings
//
// Revision 1.1  2006/09/22 14:52:03  wendy
// Init for XCC Catalog reports
//
//
public class SGCatException extends IOException
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2006  All Rights Reserved.";
    /** cvs revision number */
    public static final String VERSION = "$Revision: 1.2 $";

    /********************************************************************************
    * constructor
    *@param msg
    */
    public SGCatException(String msg)	{
		super(msg);
   	}
}
