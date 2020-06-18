//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: MetaTaskAttribute.java,v $
// Revision 1.7  2013/11/11 18:30:33  wendy
// show meta information in dump()
//
// Revision 1.6  2010/11/08 18:46:07  wendy
// Reuse string objects to reduce memory requirements
//
// Revision 1.5  2005/03/08 23:15:46  dave
// Jtest checkins from today and last ngith
//
// Revision 1.4  2004/10/15 17:06:04  dave
// atttempting some speed up by bypassing instance of checks
//
// Revision 1.3  2002/05/02 17:34:46  gregg
// for extract, build EANMetaFlagAttributes w/ all NLS flag values
//
// Revision 1.2  2002/02/05 23:11:30  dave
// more baseline fixes
//
// Revision 1.1  2002/02/05 21:09:12  dave
// more abstract file jockeying
//
// Revision 1.1  2002/02/04 21:13:57  dave
// file management
//
// Revision 1.1  2002/02/04 20:51:40  dave
// new classes
//
//

package COM.ibm.eannounce.objects;

import java.sql.SQLException;
import java.util.Hashtable;

import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;

/**
 * MetaTaskAttribute
*/
public class MetaTaskAttribute extends EANMetaFlagAttribute {

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
        return "$Id: MetaTaskAttribute.java,v 1.7 2013/11/11 18:30:33 wendy Exp $";
    }

    /**
     * Main method which performs a simple test of this class
     *
     * @param arg 
     */
    public static void main(String arg[]) {
    }

    /**
     * Creates the MetaTaskAttribute
     *
     * @param _emf
     * @param _prof
     * @param _s1
     * @param _s2
     * @param _s3
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public MetaTaskAttribute(EANMetaFoundation _emf, Profile _prof, String _s1, String _s2, String _s3) throws MiddlewareRequestException {
        super(_emf, _prof, _s1, _s2, _s3);
        setAttributeType(EANMetaAttribute.IS_ABR);
    }

    /**
     * Creates the MetaMultiFlagAttribute with the Default US English LongDescription
     *
     * @param _db is the database
     * @param _prof is the Profile
     * @param _emf
     * @param _str
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public MetaTaskAttribute(EANMetaFoundation _emf, Database _db, Profile _prof, String _str) throws SQLException, MiddlewareException, MiddlewareRequestException {
        super(_emf, _db, _prof, _str);
        setAttributeType(EANMetaAttribute.IS_ABR);
    }

    /**
     * Creates the MetaMultiFlagAttribute with the Default US English LongDescription - all NLS flag values
     *
     * @param _db is the database
     * @param _prof is the Profile
     * @param _bAllNls is whether we will retreive all NLS flag values
     * @param _emf
     * @param _str
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public MetaTaskAttribute(EANMetaFoundation _emf, Database _db, Profile _prof, String _str, boolean _bAllNls) throws SQLException, MiddlewareException, MiddlewareRequestException {
    	this(_emf, _db, _prof, _str, _bAllNls,null);
    }
    public MetaTaskAttribute(EANMetaFoundation _emf, Database _db, Profile _prof, String _str, boolean _bAllNls,Hashtable memTbl) throws SQLException, MiddlewareException, MiddlewareRequestException {
        super(_emf, _db, _prof, _str, _bAllNls,memTbl);
        setAttributeType(EANMetaAttribute.IS_ABR);
    }

    /**
     * (non-Javadoc) 
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public final String dump(boolean _bBrief) {
        StringBuffer strbResult = new StringBuffer();
        strbResult.append("*MetaTaskAttribute: " + getKey());

        if (!_bBrief) {
        	strbResult.append(":" + super.dump(_bBrief));
        }
        return strbResult.toString();
    }

}
