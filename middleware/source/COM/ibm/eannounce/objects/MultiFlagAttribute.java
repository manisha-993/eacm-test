//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: MultiFlagAttribute.java,v $
// Revision 1.2  2005/03/08 23:15:47  dave
// Jtest checkins from today and last ngith
//
// Revision 1.1  2002/02/12 23:18:13  dave
// added more attribute objects
//
// Revision 1.1  2002/02/12 22:28:18  dave
// adding status attribute object
//
// Revision 1.2  2002/02/12 22:06:27  dave
// more fixes to syntax
//
// Revision 1.1  2002/02/12 21:54:45  dave
// more attribute objects
//
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;

/**
 * MultiFlagAttribute
 */
public class MultiFlagAttribute extends EANFlagAttribute {

    // Instance variables
    /**
    * @serial
    */
    static final long serialVersionUID = 1L;

    /**
     * (non-Javadoc)
     * getVersion
     *
     * @see COM.ibm.eannounce.objects.EANFoundation#getVersion()
     */
    public String getVersion() {
        return "$Id: MultiFlagAttribute.java,v 1.2 2005/03/08 23:15:47 dave Exp $";
    }

    /**
     * Main method which performs a simple test of this class
     *
     * @param arg 
     */
    public static void main(String arg[]) {
    }

    /**
     * Manages StatusAttributes in the e-announce data model
     *
     * @param _edf
     * @param _prof
     * @param _mmfa
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public MultiFlagAttribute(EANDataFoundation _edf, Profile _prof, MetaMultiFlagAttribute _mmfa) throws MiddlewareRequestException {
        super(_edf, _prof, _mmfa);
    }

}
