//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: ActionItemImplicator.java,v $
// Revision 1.3  2005/02/09 23:46:46  dave
// more Jtest Cleanup
//
// Revision 1.2  2002/12/13 00:09:00  gregg
// return false in updatePdhMeta
//
// Revision 1.1  2002/12/12 23:31:27  gregg
// initial load
//
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;

/**
* Used as a placeholder for an ActionItem in MetaActionItemList
*/
public class ActionItemImplicator extends EANActionItem {

    /**
    * @serial
    */
    static final long serialVersionUID = 1L;


    /**
     * ActionItemImplicator
     *
     * @param _emf
     * @param _prof
     * @param _strActionItemKey
     * @param _strActionItemClass
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public ActionItemImplicator(EANMetaFoundation _emf, Profile _prof, String _strActionItemKey, String _strActionItemClass) throws MiddlewareRequestException {
        super(_emf, _prof, _strActionItemKey);
        setActionClass(_strActionItemClass);
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
            strbResult.append("ActionItemImplicator: " + getKey());
        }

        return new String(strbResult);

    }

    /**
     * (non-Javadoc)
     * updatePdhMeta
     *
     * @see COM.ibm.eannounce.objects.EANActionItem#updatePdhMeta(COM.ibm.opicmpdh.middleware.Database, boolean)
     */
    public boolean updatePdhMeta(Database _db, boolean _b) throws MiddlewareException {
        return false;
    }

    /**
     * (non-Javadoc)
     * getPurpose
     *
     * @see COM.ibm.eannounce.objects.EANActionItem#getPurpose()
     */
    public String getPurpose() {
        return getActionClass();
    }

}
