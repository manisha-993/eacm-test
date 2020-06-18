//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: MetaEntityStub.java,v $
// Revision 1.5  2005/03/07 21:10:26  dave
// Jtest cleanup
//
// Revision 1.4  2002/11/21 20:41:49  gregg
// removed excess Logs left over from copy+paste
//
// Revision 1.3  2002/11/21 20:39:41  gregg
// getParent(), setParent() made public for this subclass (its protected in EANMetaFoundation parent class)
//
// Revision 1.2  2002/11/21 19:14:37  gregg
// hasParent(), isDisplayable(), setDisplayable() methods
//
// Revision 1.1  2002/11/21 17:49:39  gregg
// initial load
//
//
package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;

/**
* Serves as a placeholder for a Meta-Object in a list
*/
public class MetaEntityStub extends EANMetaFoundation {

    /**
    * @serial
    */
    static final long serialVersionUID = 1L;

    private boolean m_bDisplayable = true;

    /**
     * MetaEntityStub
     *
     * @param _mf
     * @param _prof
     * @param _s
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public MetaEntityStub(EANFoundation _mf, Profile _prof, String _s) throws MiddlewareRequestException {
        super(_mf, _prof, _s);
    }

    /**
     * (non-Javadoc)
     * toString
     *
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return getLongDescription();
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _brief) {
        if (_brief) {
            return getKey();
        }
        return "key:" + getKey() + NEW_LINE + "Long Desc:" + getLongDescription() + NEW_LINE + " Short Desc:" + getShortDescription();
    }

    /**
     * (non-Javadoc)
     * setParent
     *
     * @see COM.ibm.eannounce.objects.EANFoundation#setParent(COM.ibm.eannounce.objects.EANFoundation)
     */
    public void setParent(EANFoundation _emf) {
        super.setParent(_emf);
    }

    /**
     * (non-Javadoc)
     * getParent
     *
     * @see COM.ibm.eannounce.objects.EANFoundation#getParent()
     */
    public EANFoundation getParent() {
        return super.getParent();
    }

    /**
     * hasParent
     *
     * @return
     *  @author David Bigelow
     */
    public boolean hasParent() {
        if (getParent() == null) {
            return false;
        }
        return true;
    }

    /**
     * isDisplayable
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isDisplayable() {
        return m_bDisplayable;
    }

    /**
     * setDisplayable
     *
     * @param _b
     *  @author David Bigelow
     */
    public void setDisplayable(boolean _b) {
        m_bDisplayable = _b;
    }

}
