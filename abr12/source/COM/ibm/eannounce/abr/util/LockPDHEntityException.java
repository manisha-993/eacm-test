// Licensed Materials -- Property of IBM

//

// (c) Copyright International Business Machines Corporation, 2001

// All Rights Reserved.

// $Log: LockPDHEntityException.java,v $
// Revision 1.2  2008/02/19 17:18:25  wendy
// Cleanup RSA warnings
//
// Revision 1.1.1.1  2003/06/03 19:02:25  dave
// new 1.1.1 abr 
//
// Revision 1.2  2002/09/24 21:19:40  bala
// make getVersion method static to match AbstractTask
//
// Revision 1.1.1.1  2002/06/20 16:16:59  bala
// Creating ODS module
//

// Revision 1.1.1.1  2002/02/07 23:29:24  cstolpe

// Initialize

//

// Revision 1.3  2002/01/15 00:26:59  dave

// branding interface files

// and added logging

//



//package COM.ibm.eannounce.util;

package COM.ibm.eannounce.abr.util;

public class LockPDHEntityException extends Exception

{
	private static final long serialVersionUID = 1L;
  public LockPDHEntityException(String msg)

   {

	  super(msg);

   }

  public static String getVersion() {

	  return new String ("$Id: LockPDHEntityException.java,v 1.2 2008/02/19 17:18:25 wendy Exp $");

  }

}

