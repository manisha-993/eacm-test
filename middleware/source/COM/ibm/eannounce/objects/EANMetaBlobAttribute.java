//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: EANMetaBlobAttribute.java,v $
// Revision 1.5  2005/02/28 19:43:53  dave
// more Jtest fixes from over the weekend
//
// Revision 1.4  2004/10/21 16:49:53  dave
// trying to share compartor
//
// Revision 1.3  2002/09/27 20:45:24  gregg
// new constructor (EANMetaFoundation,Profile,String,String,String)
//
// Revision 1.2  2002/04/26 21:53:12  joan
// debug toString
//
// Revision 1.1  2002/02/04 22:02:32  dave
// new methods for meta objects
//
//

package COM.ibm.eannounce.objects;

import java.sql.SQLException;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;

/**
* Manages the EANMetaBlobAttribute Information
*
* @author  David Bigelow
* @version @date
*/

public abstract class EANMetaBlobAttribute extends EANMetaAttribute {

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
        return "$Id: EANMetaBlobAttribute.java,v 1.5 2005/02/28 19:43:53 dave Exp $";
    }

    /**
     * Main method which performs a simple test of this class
     *
     * @param arg 
     */
    public static void main(String arg[]) {
    }

    /**
     * Creates the EANMetaBlobAttribute
     *
     * @param _emf
     * @param _prof
     * @param _s1
     * @param _s2
     * @param _s3
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public EANMetaBlobAttribute(EANMetaFoundation _emf, Profile _prof, String _s1, String _s2, String _s3) throws MiddlewareRequestException {
        super(_emf, _prof, _s1, _s2, _s3);
    }

    /**
     * Creates the EANMetaBlobAttribute with the Default NLSID and Value
     *
     * @param _s1 is the attributecode that represents this object
     * @param _emf
     * @param _db
     * @param _prof
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public EANMetaBlobAttribute(EANMetaFoundation _emf, Database _db, Profile _prof, String _s1) throws SQLException, MiddlewareException, MiddlewareRequestException {
        super(_emf, _db, _prof, _s1);
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {
        return super.dump(_bBrief);
    }
}
