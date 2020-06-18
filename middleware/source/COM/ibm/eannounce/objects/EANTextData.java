//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: EANTextData.java,v $
// Revision 1.3  2005/02/28 19:43:53  dave
// more Jtest fixes from over the weekend
//
// Revision 1.2  2004/10/21 16:49:54  dave
// trying to share compartor
//
// Revision 1.1  2002/02/11 07:23:08  dave
// new objects to commit
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
public interface EANTextData {

    /**
     * FIELD
     */
    String CLASS_BRAND = "$Id: EANTextData.java,v 1.3 2005/02/28 19:43:53 dave Exp $";

    /**
     * This is used to manage NLSSensitive Data for textlike
     *
     * @return String
     */
//    String getValue();
    /**
     * putValue
     *
     * @param _i
     * @param _str
     *  @author David Bigelow
     */
    void putValue(int _i, String _str); // Always drive to put2
    /**
     * commitValues
     *
     *  @author David Bigelow
     */
    void commitValues();
    /**
     * rollbackValues
     *
     *  @author David Bigelow
     */
    void rollbackValues();

}
