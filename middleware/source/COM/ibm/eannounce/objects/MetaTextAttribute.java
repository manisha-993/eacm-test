//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: MetaTextAttribute.java,v $
// Revision 1.12  2005/03/08 23:15:46  dave
// Jtest checkins from today and last ngith
//
// Revision 1.11  2004/10/20 17:15:24  dave
// trying to save space here
//
// Revision 1.10  2004/10/15 17:06:04  dave
// atttempting some speed up by bypassing instance of checks
//
// Revision 1.9  2002/03/13 22:51:59  gregg
// constuctor added to allow the creation of new Attributes (!in db)
//
// Revision 1.8  2002/03/13 01:15:28  gregg
// syntax
//
// Revision 1.7  2002/03/13 01:06:37  gregg
// ods length
//
// Revision 1.6  2002/02/13 21:32:19  dave
// more table generating stuff
//
// Revision 1.5  2002/02/12 18:10:23  dave
// more changes
//
// Revision 1.4  2002/02/05 21:09:12  dave
// more abstract file jockeying
//
// Revision 1.3  2002/02/05 18:17:48  dave
// more fixes
//
// Revision 1.2  2002/02/04 23:08:47  dave
// corecting imports
//
// Revision 1.1  2002/02/04 22:36:46  dave
// more Meta Classes stubbed out for 1.1 arch
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

public class MetaTextAttribute extends EANMetaTextAttribute {

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
        return "$Id: MetaTextAttribute.java,v 1.12 2005/03/08 23:15:46 dave Exp $";
    }

    /**
     * Main method which performs a simple test of this class
     *
     * @param arg 
     */
    public static void main(String arg[]) {
    }

    /**
     * Creates the MetaTextAttribute
     *
     * @param _emf
     * @param _prof
     * @param _s1
     * @param _s2
     * @param _s3
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public MetaTextAttribute(EANMetaFoundation _emf, Profile _prof, String _s1, String _s2, String _s3) throws MiddlewareRequestException {
        super(_emf, _prof, _s1, _s2, _s3);
        setAttributeType(EANMetaAttribute.IS_TEXT);
    }

    /**
     * Creates the MetaTextAttribute with the Default NLSID and Value
     *
     * @param _str1 is the attributecode that represents this object
     * @param _emf
     * @param _db
     * @param _prof
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public MetaTextAttribute(EANMetaFoundation _emf, Database _db, Profile _prof, String _str1) throws SQLException, MiddlewareException, MiddlewareRequestException {
        super(_emf, _db, _prof, _str1);
        // setTargetClass(MetaTextAttribute.class);
        setAttributeType(EANMetaAttribute.IS_TEXT);
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
    /**
     * accessor - m_iOdsLength set in EANMetaAttribute super class
     *
     * @return int
     */
    public int getOdsLength() {
        return super.m_iOdsLength;
    }

    /**
     *  mutator - set m_iOdsLength defined in EANMetaAttribute super class
     *
     * @param _i 
     */
    public void setOdsLength(int _i) {
        super.m_iOdsLength = _i;
        return;
    }
}
