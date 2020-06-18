//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: MetaSingleFlagAttribute.java,v $
// Revision 1.9  2010/11/08 18:45:23  wendy
// Reuse string objects to reduce memory requirements
//
// Revision 1.8  2005/03/08 23:15:46  dave
// Jtest checkins from today and last ngith
//
// Revision 1.7  2004/10/15 17:13:53  dave
// o.k. some syntax
//
// Revision 1.6  2004/10/15 17:06:04  dave
// atttempting some speed up by bypassing instance of checks
//
// Revision 1.5  2002/05/02 17:34:46  gregg
// for extract, build EANMetaFlagAttributes w/ all NLS flag values
//
// Revision 1.4  2002/03/13 01:15:28  gregg
// syntax
//
// Revision 1.3  2002/03/13 01:06:37  gregg
// ods length
//
// Revision 1.2  2002/03/05 05:18:46  dave
// more dump stuff
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
 * MetaSingleFlagAttribute
*/
public class MetaSingleFlagAttribute extends EANMetaFlagAttribute {

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
        return "$Id: MetaSingleFlagAttribute.java,v 1.9 2010/11/08 18:45:23 wendy Exp $";
    }

    /**
     * Main method which performs a simple test of this class
     *
     * @param arg 
     */
    public static void main(String arg[]) {
    }

    /**
     * Creates the MetaStatusFlagAttribute
     *
     * @param emf is the Assosicated Creator tied to this Object
     * @param s1 is the AttributeCode
     * @param s2 is the AttributeType
     * @param s3 is the Capability
     * This is where the caller is responsible for building the object
     * @param _s3
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @param _emf
     * @param _prof
     * @param _s1
     * @param _s2 
     */
    public MetaSingleFlagAttribute(EANMetaFoundation _emf, Profile _prof, String _s1, String _s2, String _s3) throws MiddlewareRequestException {
        super(_emf, _prof, _s1, _s2, _s3);
        setAttributeType(EANMetaAttribute.IS_SINGLE);
    }

    /**
     * Creates the MetaStatusFlagAttribute with the Default US English LongDescription
     * used in MDUI
     *
     * @param emf is the Assosicated Creator tied to this Object
     * @param _db is the database
     * @param _prof is the Profile
     * @param _s is the AttributeCode
     * @param _emf
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public MetaSingleFlagAttribute(EANMetaFoundation _emf, Database _db, Profile _prof, String _s) throws SQLException, MiddlewareException, MiddlewareRequestException {
        super(_emf, _db, _prof, _s);
        setAttributeType(EANMetaAttribute.IS_SINGLE);
    }

    /**
     * Creates the MetaStatusFlagAttribute with the Default US English LongDescription - all NLS flag values
     *
     * @param emf is the Assosicated Creator tied to this Object
     * @param _db is the database
     * @param _prof is the Profile
     * @param _s is the AttributeCode
     * @param _bAllNls is whether we retreive all nls flag values
     * @param _emf
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public MetaSingleFlagAttribute(EANMetaFoundation _emf, Database _db, Profile _prof, String _s, boolean _bAllNls) throws SQLException, MiddlewareException, MiddlewareRequestException {
    	this(_emf, _db, _prof, _s, _bAllNls,null);
    }
    public MetaSingleFlagAttribute(EANMetaFoundation _emf, Database _db, Profile _prof, String _s, boolean _bAllNls,Hashtable memTbl) throws SQLException, MiddlewareException, MiddlewareRequestException {
        super(_emf, _db, _prof, _s, _bAllNls,memTbl);
        setAttributeType(EANMetaAttribute.IS_SINGLE);
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public final String dump(boolean _bBrief) {
        StringBuffer strbResult = new StringBuffer();
        strbResult.append("MetaSingleFlagAttribute: " + getKey());
        if (!_bBrief) {
            strbResult.append(":" + super.dump(_bBrief));
        }
        return strbResult.toString();
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
    }

}
