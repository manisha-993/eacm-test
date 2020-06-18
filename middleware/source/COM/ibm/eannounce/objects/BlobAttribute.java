//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: BlobAttribute.java,v $
// Revision 1.3  2005/02/09 23:46:46  dave
// more Jtest Cleanup
//
// Revision 1.2  2002/04/26 17:08:24  joan
// working on blob attribute
//
// Revision 1.1  2002/04/25 18:02:27  joan
// initial load for blob attribute
//
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;


/**
 * BlobAttribute
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class BlobAttribute extends EANBlobAttribute {

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
        return "$Id: BlobAttribute.java,v 1.3 2005/02/09 23:46:46 dave Exp $";
    }

    /**
     * Main method which performs a simple test of this class
     *
     * @param arg 
     */
    public static void main(String arg[]) {
    }

    /**
     * Manages EANBlobAttributes in the e-announce data model
     *
     * @param _edf
     * @param _prof
     * @param _mta
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public BlobAttribute(EANDataFoundation _edf, Profile _prof, MetaBlobAttribute _mta) throws MiddlewareRequestException {
        super(_edf, _prof, _mta);
    }

}
