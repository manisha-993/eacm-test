//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: MetaFlagAttribute.java,v $
// Revision 1.8  2005/03/07 23:00:56  dave
// more Jtest
//
// Revision 1.7  2002/02/02 21:59:17  dave
// more cleanup
//

package COM.ibm.eannounce.objects;

import java.sql.SQLException;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;

/**
 * MetaFlagAttribute
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MetaFlagAttribute extends EANMetaFlagAttribute {

    // Instance variables
    /**
    * @serial
    */
    static final long serialVersionUID = 1L;

    /**
     * Main method which performs a simple test of this class
     *
     * @param arg 
     */
    public static void main(String arg[]) {
    }

    /**
     * Creates the MetaFlagAttribute
     *
     * @param _emf
     * @param _prof
     * @param _s1
     * @param _s2
     * @param _s3
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public MetaFlagAttribute(EANMetaFoundation _emf, Profile _prof, String _s1, String _s2, String _s3) throws MiddlewareRequestException {
        super(_emf, _prof, _s1, _s2, _s3);
    }

    /**
     * Creates the GenericMetaFlagAttribute with the Default US English LongDescription
     *
     * @param _db is the database
     * @param _prof is the Profile
     * @param _s is the AttributeCode
     * @param _emf
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public MetaFlagAttribute(EANMetaFoundation _emf, Database _db, Profile _prof, String _s) throws SQLException, MiddlewareException, MiddlewareRequestException {
        super(_emf, _db, _prof, _s);
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public final String dump(boolean _bBrief) {
        StringBuffer strbResult = new StringBuffer();
        if (_bBrief) {
            strbResult.append(getKey());
        } else {
            strbResult.append("*MetaFlagAttribute: " + getKey());
            strbResult.append(NEW_LINE + super.dump(_bBrief));
        }
        return new String(strbResult);
    }

    /**
    * Return the date/time this class was generated
    * @return the date/time this class was generated
    */
    public String getVersion() {
        return "$Id: MetaFlagAttribute.java,v 1.8 2005/03/07 23:00:56 dave Exp $";
    }
}
