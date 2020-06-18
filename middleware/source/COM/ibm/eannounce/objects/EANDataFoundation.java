//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: EANDataFoundation.java,v $
// Revision 1.11  2005/02/14 17:57:46  dave
// more Jtest
//
// Revision 1.10  2004/10/21 16:49:53  dave
// trying to share compartor
//
// Revision 1.9  2002/03/12 20:42:55  dave
// need to abstract getLongDescription, getShortDescription at the EANFoundation
// level
//
// Revision 1.8  2002/02/04 17:07:15  dave
// new abstract class EANEntity
//
// Revision 1.7  2002/02/02 21:39:19  dave
// more  fixes to tighen up import
//
// Revision 1.6  2002/02/02 20:56:54  dave
// tightening up code
//
// Revision 1.5  2002/02/01 17:38:40  dave
// more import fixes
//
// Revision 1.4  2002/02/01 03:16:51  dave
// reworking how Things get implementd for the local text management
// and editing
//
// Revision 1.3  2002/02/01 00:42:32  dave
// more foundation fixes for passing _prof
//
// Revision 1.2  2002/01/31 21:32:22  dave
// more mass abstract changes
//
//

package COM.ibm.eannounce.objects;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;


/**
* This is the basis for all eannounce EANDataFoundation used by e-announce
*/
public abstract class EANDataFoundation extends EANFoundation implements EANData {

    /**
    * @serial
    */
    static final long serialVersionUID = 1L;

    /**
     * EANDataFoundation
     *
     * @param _f
     * @param _prof
     * @param _str
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public EANDataFoundation(EANFoundation _f, Profile _prof, String _str) throws MiddlewareRequestException {
        super(_f, _prof, _str);
    }

    /*
    * This needs to be abstracted
    */
    /**
     * (non-Javadoc)
     * getLongDescription
     *
     * @see COM.ibm.eannounce.objects.EANFoundation#getLongDescription()
     */
    public String getLongDescription() {
        return "TBD";
    }

    /**
     * (non-Javadoc)
     * getShortDescription
     *
     * @see COM.ibm.eannounce.objects.EANFoundation#getShortDescription()
     */
    public String getShortDescription() {
        return "TBD";
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public abstract String dump(boolean _brief);
}
