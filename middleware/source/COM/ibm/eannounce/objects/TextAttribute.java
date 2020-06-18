//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: TextAttribute.java,v $
// Revision 1.2  2008/01/31 22:56:01  wendy
// Cleanup RSA warnings
//
// Revision 1.1  2002/02/11 07:23:09  dave
// new objects to commit
//
// Revision 1.2  2002/02/05 16:39:13  dave
// more expansion of abstract model
//
// Revision 1.1  2002/02/04 18:02:46  dave
// added new Abstract EANAttribute
//
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;

/**
*/
public class TextAttribute extends EANTextAttribute{

  // Instance variables
  /**
  * @serial
  */
  static final long serialVersionUID = 1L;

  public String getVersion() {
    return new String("$Id: TextAttribute.java,v 1.2 2008/01/31 22:56:01 wendy Exp $");
  }
	
  /**
  * Main method which performs a simple test of this class
  */
  public static void main(String arg[]) {
  }

	/**
	* Manages EANTextAttributes in the e-announce data model
	*/
	public TextAttribute(EANDataFoundation _edf, Profile _prof, MetaTextAttribute _mta) throws MiddlewareRequestException {
		super(_edf, _prof, _mta);
  }
  
}
