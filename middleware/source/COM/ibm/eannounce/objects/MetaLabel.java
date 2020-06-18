//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: MetaLabel.java,v $
// Revision 1.6  2005/03/08 23:15:46  dave
// Jtest checkins from today and last ngith
//
// Revision 1.5  2002/03/19 00:39:07  dave
// more syntax fixes
//
// Revision 1.4  2002/02/02 20:20:14  dave
// more Cleanup on ensuring Profile or Parent is always present
//
// Revision 1.3  2002/02/01 00:42:32  dave
// more foundation fixes for passing _prof
//
// Revision 1.2  2002/01/31 21:32:22  dave
// more mass abstract changes
//
// Revision 1.1  2001/10/04 20:29:41  dave
// serveral new adds and renames
//
// Revision 1.5  2001/08/19 21:58:31  dave
// sytax fixes on RestrictionGroup logic
//
// Revision 1.4  2001/08/15 18:25:05  dave
// misc fixes on NLS and ReportSet..and RPTID bucketing
//
// Revision 1.3  2001/08/09 18:04:30  dave
// first attempt at a table model
//
// Revision 1.2  2001/08/08 21:38:45  dave
// display fixes for debuging aid
//
// Revision 1.1  2001/08/08 17:42:57  dave
// first attempt at Search API standard table interface
//
// Revision 1.5  2001/08/02 19:13:59  dave
// display fixes
//
// Revision 1.4  2001/08/02 19:08:13  dave
// adjustments for compile
//
// Revision 1.3  2001/08/02 18:37:12  dave
// final syntax
//
// Revision 1.2  2001/08/02 18:16:32  dave
// syntax
//
// Revision 1.1  2001/08/02 18:10:32  dave
// more conversion.. getting close
//
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;

/**
*/
public class MetaLabel extends EANMetaFoundation {

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
     * Creates the MetaLabel with the Default US English LongDescription
     *
     * @param emf is the Assosicated Creator tied to this Object
     * @param s1 is the Tag of the MetaLabel
     * @param s2 is the LongDescription
     * @param _emf
     * @param _prof
     * @param _s1
     * @param _s2
     * @param _cl
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public MetaLabel(EANFoundation _emf, Profile _prof, String _s1, String _s2, Class _cl) throws MiddlewareRequestException {
        super(_emf, _prof, _s1);
        putLongDescription(1, _s2);
        setTargetClass(_cl);
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _brief) {
        return "TBD";
    }

    /**
    * Return the date/time this class was generated
    * @return the date/time this class was generated
    */
    public String getVersion() {
        return "$Id: MetaLabel.java,v 1.6 2005/03/08 23:15:46 dave Exp $";
    }

}
