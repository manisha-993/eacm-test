//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: EANData.java,v $
// Revision 1.5  2005/02/14 17:57:46  dave
// more Jtest
//
// Revision 1.4  2004/10/21 16:49:53  dave
// trying to share compartor
//
// Revision 1.3  2002/02/01 00:19:42  dave
// more low level foundation fixes
//
// Revision 1.2  2002/02/01 00:08:11  dave
// another wave of foundation changes
//
// Revision 1.1  2002/01/31 17:58:53  dave
// Rearranging Abrstract levels for more consistiency
//
// Revision 1.7  2002/01/21 22:24:12  dave
// adding Interface definitions for EANObject for more consistiency
// \
//
// Revision 1.6  2001/08/08 17:41:45  dave
// more generalization is prep for table model on Search API
//
// Revision 1.5  2001/08/01 23:32:27  dave
// correct interface EANObject to properly define setKey
//
// Revision 1.4  2001/08/01 23:02:43  dave
// generic object to house meta entity definition
//
// Revision 1.3  2001/08/01 21:13:27  dave
// more foundation building for the search API
//
// Revision 1.2  2001/08/01 17:44:59  dave
// big syntax fixes
//

package COM.ibm.eannounce.objects;


/**
* Ensures that a getKey method is used
* for the eannounce objects
*/
public interface EANData {

    /**
     * FIELD
     */
    String CLASS_BRAND = "$Id: EANData.java,v 1.5 2005/02/14 17:57:46 dave Exp $";

    /**
    * This is used to pull out the key identifier of the object.
    */
}
