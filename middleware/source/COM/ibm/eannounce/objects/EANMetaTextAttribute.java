//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: EANMetaTextAttribute.java,v $
// Revision 1.6  2005/02/28 19:43:53  dave
// more Jtest fixes from over the weekend
//
// Revision 1.5  2004/10/21 16:49:54  dave
// trying to share compartor
//
// Revision 1.4  2002/03/13 22:51:59  gregg
// constuctor added to allow the creation of new Attributes (!in db)
//
// Revision 1.3  2002/02/12 18:30:54  dave
// more dump statements fixed up
//
// Revision 1.2  2002/02/04 22:36:44  dave
// more Meta Classes stubbed out for 1.1 arch
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
* Manages the EANMetaTextAttribute Information
* LongText and Text can be derived from this abstract class
*
* @author  David Bigelow
* @version @date
*/

public abstract class EANMetaTextAttribute extends EANMetaAttribute {

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
        return "$Id: EANMetaTextAttribute.java,v 1.6 2005/02/28 19:43:53 dave Exp $";
    }

    /**
     * Main method which performs a simple test of this class
     *
     * @param arg 
     */
    public static void main(String arg[]) {
    }

    /**
     * Creates the EANMetaTextAttribute
     *
     * @param _emf
     * @param _prof
     * @param _s1
     * @param _s2
     * @param _s3
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public EANMetaTextAttribute(EANMetaFoundation _emf, Profile _prof, String _s1, String _s2, String _s3) throws MiddlewareRequestException {
        super(_emf, _prof, _s1, _s2, _s3);
    }

    /**
     * Creates the PDHMetaFlag with the Default NLSID and Value
     *
     * @param _str1 is the attributecode that represents this object
     * @param _emf
     * @param _db
     * @param _prof
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public EANMetaTextAttribute(EANMetaFoundation _emf, Database _db, Profile _prof, String _str1) throws SQLException, MiddlewareException, MiddlewareRequestException {
        super(_emf, _db, _prof, _str1);
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
