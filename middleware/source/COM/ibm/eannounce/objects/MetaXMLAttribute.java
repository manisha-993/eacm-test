//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: MetaXMLAttribute.java,v $
// Revision 1.5  2005/03/08 23:15:46  dave
// Jtest checkins from today and last ngith
//
// Revision 1.4  2004/10/15 17:06:04  dave
// atttempting some speed up by bypassing instance of checks
//
// Revision 1.3  2002/09/27 20:45:23  gregg
// new constructor (EANMetaFoundation,Profile,String,String,String)
//
// Revision 1.2  2002/04/17 20:30:08  dave
// syntax fixes
//
// Revision 1.1  2002/04/17 20:17:06  dave
// new XMLAttribute and its MetaPartner
//
//

package COM.ibm.eannounce.objects;

import java.sql.SQLException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;

/**
* Manages the MetaTextAttribute Information
* LongText and Text can be derived from this abstract class
*
* @author  David Bigelow
* @version @date
*/

public class MetaXMLAttribute extends EANMetaTextAttribute {

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
        return "$Id: MetaXMLAttribute.java,v 1.5 2005/03/08 23:15:46 dave Exp $";
    }

    /**
     * Main method which performs a simple test of this class
     *
     * @param arg 
     */
    public static void main(String arg[]) {
    }

    /**
     * Creates the MetaXMLAttribute
     *
     * @param _emf
     * @param _prof
     * @param _s1
     * @param _s2
     * @param _s3
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public MetaXMLAttribute(EANMetaFoundation _emf, Profile _prof, String _s1, String _s2, String _s3) throws MiddlewareRequestException {
        super(_emf, _prof, _s1, _s2, _s3);
        setAttributeType(EANMetaAttribute.IS_XML);
    }

    /**
     * Creates the MetaXMLAttribute
     *
     * @param _str1 is the attributecode that represents this object
     * @param _emf
     * @param _db
     * @param _prof
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public MetaXMLAttribute(EANMetaFoundation _emf, Database _db, Profile _prof, String _str1) throws SQLException, MiddlewareException, MiddlewareRequestException {
        super(_emf, _db, _prof, _str1);
        setAttributeType(EANMetaAttribute.IS_XML);
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
