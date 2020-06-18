//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: Implicator.java,v $
// Revision 1.7  2005/03/03 22:39:32  dave
// JTest working and cleanup
//
// Revision 1.6  2002/03/19 00:23:15  dave
// syntax fixes
//
// Revision 1.5  2002/03/12 19:32:12  dave
// demp display "imp" for implicator
//
// Revision 1.4  2002/03/12 19:31:07  dave
// adding stuff for proper column header display
//
// Revision 1.3  2002/02/02 20:56:54  dave
// tightening up code
//
// Revision 1.2  2002/02/01 00:08:11  dave
// another wave of foundation changes
//
// Revision 1.1  2002/01/15 00:06:43  dave
// initial imports for ActionList
//
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;

/**
* This is used to make bridge two EAN Objects with equivelent keys
*/
public class Implicator extends EANMetaFoundation {

    /**
    * @serial
    */
    static final long serialVersionUID = 1L;

    /**
     * Implicator
     *
     * @param _mf
     * @param _prof
     * @param _s1
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public Implicator(EANFoundation _mf, Profile _prof, String _s1) throws MiddlewareRequestException {
        super(_mf, _prof, _s1);
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {

        StringBuffer strbResult = new StringBuffer();

        if (_bBrief) {
            strbResult.append(getKey());
        } else {
            strbResult.append("Implicator: " + getKey());
        }

        return new String(strbResult);

    }

    /**
     * (non-Javadoc)
     * getLongDescription
     *
     * @see COM.ibm.eannounce.objects.EANMeta#getLongDescription()
     */
    public String getLongDescription() {
        return "imp:" + getParent().getLongDescription();
    }

    /**
     * (non-Javadoc)
     * getShortDescription
     *
     * @see COM.ibm.eannounce.objects.EANMeta#getShortDescription()
     */
    public String getShortDescription() {
        return "imp:" + getParent().getShortDescription();
    }

}
