//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: EANObject.java,v $
// Revision 1.17  2005/08/25 21:27:33  tony
// removed getHeritageKey
//
// Revision 1.16  2005/08/23 18:14:24  tony
// adjusted Heritage Key
//
// Revision 1.15  2005/08/22 16:24:03  tony
// Test_acl20050822
//
// Revision 1.14  2005/02/28 19:43:53  dave
// more Jtest fixes from over the weekend
//
// Revision 1.13  2005/02/15 18:42:24  dave
// More JTest cleanup
//
// Revision 1.12  2004/10/21 16:49:54  dave
// trying to share compartor
//
// Revision 1.11  2002/02/02 19:48:59  dave
// more foundation work
//
// Revision 1.10  2002/01/31 19:33:54  dave
// remove the set kind of stuff from the interface def that needs to be protected
// embodied it in the EANFoundation
//
// Revision 1.9  2002/01/31 19:01:49  dave
// clean up and syntax fix
//
// Revision 1.8  2002/01/31 17:58:54  dave
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

import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.transactions.NLSItem;

/**
* Ensures that a getKey method is used
* for the eannounce objects
*/
public interface EANObject {

    /**
     * FIELD
     */
    String CLASS_BRAND = "$Id: EANObject.java,v 1.17 2005/08/25 21:27:33 tony Exp $";

    /**
     * This is used to pull out the key identifier of the object.
     *
     * @return String
     */
    String getKey();

    /**
     * getProfile
     *
     * @return
     *  @author David Bigelow
     */
    Profile getProfile();
    /**
     * getNLSItem
     *
     * @return
     *  @author David Bigelow
     */
    NLSItem getNLSItem();
    /**
     * getNLSID
     *
     * @return
     *  @author David Bigelow
     */
    int getNLSID();
    /**
     * isSelected
     *
     * @return
     *  @author David Bigelow
     */
    boolean isSelected();
    /**
     * setSelected
     *
     * @param _b
     *  @author David Bigelow
     */
    void setSelected(boolean _b);
    /**
     * dump
     *
     * @param _b
     * @return
     *  @author David Bigelow
     */
    String dump(boolean _b);
}
