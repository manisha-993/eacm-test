//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: MetaBlobAttribute.java,v $
// Revision 1.7  2005/03/04 22:40:10  dave
// Jtest
//
// Revision 1.6  2004/10/20 17:15:24  dave
// trying to save space here
//
// Revision 1.5  2004/10/15 17:06:03  dave
// atttempting some speed up by bypassing instance of checks
//
// Revision 1.4  2002/09/27 20:45:24  gregg
// new constructor (EANMetaFoundation,Profile,String,String,String)
//
// Revision 1.3  2002/04/26 21:53:12  joan
// debug toString
//
// Revision 1.2  2002/04/25 18:21:59  joan
// fix compiling errors
//
// Revision 1.1  2002/04/25 18:02:27  joan
// initial load for blob attribute
//
//

package COM.ibm.eannounce.objects;

import java.sql.SQLException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;

/**
* Manages the MetaBlobAttribute Information
*
* @author  Joan Tran
* @version @date
*/
public class MetaBlobAttribute extends EANMetaBlobAttribute {

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
        return "$Id: MetaBlobAttribute.java,v 1.7 2005/03/04 22:40:10 dave Exp $";
    }

    /**
     * Main method which performs a simple test of this class
     *
     * @param arg 
     */
    public static void main(String arg[]) {
    }

    /**
     * Creates the MetaBlobAttribute
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
    public MetaBlobAttribute(EANMetaFoundation _emf, Profile _prof, String _s1, String _s2, String _s3) throws MiddlewareRequestException {
        super(_emf, _prof, _s1, _s2, _s3);
        setAttributeType(EANMetaAttribute.IS_BLOB);
    }

    /**
     * Creates the MetaBlobAttribute with the Default NLSID and Value
     *
     * @param emf is the Assosicated Object that created this instance
     * @param db is the Database
     * @param prof is the Profile
     * @param _str1 is the attributecode that represents this object
     * @param _emf
     * @param _db
     * @param _prof
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public MetaBlobAttribute(EANMetaFoundation _emf, Database _db, Profile _prof, String _str1) throws SQLException, MiddlewareException, MiddlewareRequestException {
        super(_emf, _db, _prof, _str1);
        //setTargetClass(MetaBlobAttribute.class);
        setAttributeType(EANMetaAttribute.IS_BLOB);
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
