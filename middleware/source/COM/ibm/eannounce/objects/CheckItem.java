//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: CheckItem.java,v $
// Revision 1.4  2005/02/10 01:22:25  dave
// JTest fixes
//
// Revision 1.3  2002/03/12 20:53:42  dave
// fixing Set Check to conform to abstract super class
//
// Revision 1.2  2002/02/27 20:06:26  dave
// syntax
//
// Revision 1.1  2002/02/27 19:54:23  dave
// new objects to manage state transition
//
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;


/**
 * CheckItem
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CheckItem extends EANFoundation {

    /**
    * @serial
    */
    static final long serialVersionUID = 1L;
    private String m_strValue = null;

    /**
     * (non-Javadoc)
     * getVersion
     *
     * @see COM.ibm.eannounce.objects.EANFoundation#getVersion()
     */
    public String getVersion() {
        return "$Id: CheckItem.java,v 1.4 2005/02/10 01:22:25 dave Exp $";
    }

    /**
     * Main method which performs a simple test of this class
     *
     * @param arg 
     */
    public static void main(String arg[]) {
    }

    /**
     * Creates the Check Item, based upon the value of _s1 and its associated MetaAttribute definition
     *
     * @param _ema
     * @param _strValue
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public CheckItem(EANMetaAttribute _ema, String _strValue) throws MiddlewareRequestException {
        super(_ema, null, _ema.getKey() + _strValue);
        m_strValue = _strValue;
    }

    /**
     * getMetaAttribute
     *
     * @return
     *  @author David Bigelow
     */
    public EANMetaAttribute getMetaAttribute() {
        return (EANMetaAttribute) getParent();
    }

    /**
     * getValue
     *
     * @return
     *  @author David Bigelow
     */
    public String getValue() {
        return m_strValue;
    }

    /**
     * Display the object and show values
     *
     * @return String
     * @param _bBrief 
     */
    public String dump(boolean _bBrief) {
        return "CheckItem:" + getKey() + getMetaAttribute().dump(_bBrief);
    }

    /**
     * (non-Javadoc)
     * getLongDescription
     *
     * @see COM.ibm.eannounce.objects.EANFoundation#getLongDescription()
     */
    public String getLongDescription() {
        return dump(false);
    }

    /**
     * (non-Javadoc)
     * getShortDescription
     *
     * @see COM.ibm.eannounce.objects.EANFoundation#getShortDescription()
     */
    public String getShortDescription() {
        return dump(true);
    }

    /**
     * (non-Javadoc)
     * toString
     *
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return dump(false);
    }
}
