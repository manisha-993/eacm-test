// Licensed Materials -- Property of IBM

//

// (c) Copyright International Business Machines Corporation, 2001

// All Rights Reserved.

//

//package COM.ibm.eannounce.util;

package COM.ibm.eannounce.abr.util;

// Licensed Materials -- Property of IBM

//

// (c) Copyright International Business Machines Corporation, 2001

// All Rights Reserved.

//

/*****************************************************************************

 * ---------------------------------------------------------------------------

 * Module Name: UpdatePDHEntityException.java

 *

 * Descriptive Name: Thrown if errors are encountered when trying to update

 *	the entity through the state machine.

 * ---------------------------------------------------------------------------

 * @author  Wendy Stimpson

 * @version 1.0

 * ---------------------------------------------------------------------------

 * Dependencies:

 * ---------------------------------------------------------------------------

 * Change History:   (Most Current at TOP)

 *   Date            Programmer      Description/Comments

 * 10-09-01          WSS			Initial creation of the file

 * ---------------------------------------------------------------------------

 */



public class UpdatePDHEntityException extends Exception

{
	private static final long serialVersionUID = 1L;

	public UpdatePDHEntityException(String msg)
   {

	  super(msg);

   }

  public static String getVersion() {

	return "1.0";

  }

}

