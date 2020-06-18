//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: MetaTag.java,v $
// Revision 1.8  2005/03/08 23:15:46  dave
// Jtest checkins from today and last ngith
//
// Revision 1.7  2004/08/18 17:15:51  dave
// refining ECCM group / item descriptions
//
// Revision 1.6  2002/03/19 00:39:07  dave
// more syntax fixes
//
// Revision 1.5  2002/02/27 20:06:26  dave
// syntax
//
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;

/**
 * MetaTag
*/
public class MetaTag extends EANMetaFoundation {

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
     * Creates the MetaTag with the Default US English LongDescription
     *
     * @param _emf
     * @param _prof
     * @param _s1
     * @param _s2
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public MetaTag(EANFoundation _emf, Profile _prof, String _s1, String _s2) throws MiddlewareRequestException {
        super(_emf, _prof, _s1);
        putLongDescription(1, _s2);
    }

    /**
     * Creates the MetaTag with the Default US English LongDescription
     *
     * @param _emf
     * @param _prof
     * @param _s1
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public MetaTag(EANFoundation _emf, Profile _prof, String _s1) throws MiddlewareRequestException {
        super(_emf, _prof, _s1);
    }

    /**
     * Display the object and show values
     *
     * @return String
     * @param _brief 
     */
    public String dump(boolean _brief) {
        return "MetaTag:key:" + getKey() + ":ld:" + getLongDescription();
    }

    /**
    * Return the date/time this class was generated
    * @return the date/time this class was generated
    */
    public String getVersion() {
        return "$Id: MetaTag.java,v 1.8 2005/03/08 23:15:46 dave Exp $";
    }
}
