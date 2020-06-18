//
// Copyright (c) 2003, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: EntityChangeHistoryItem.java,v $
// Revision 1.10  2005/02/28 23:31:01  dave
// more Jtest
//
// Revision 1.9  2004/09/28 21:16:35  dave
// more change history stuff
//
// Revision 1.8  2004/09/16 00:24:47  dave
// one more field swap
//
// Revision 1.7  2004/09/14 22:31:40  dave
// new change history stuff.. to include Change group in
// addition to role information
//
// Revision 1.6  2003/06/19 20:32:00  dave
// changes to ChangeHistoryItem
//
// Revision 1.5  2003/03/11 21:16:49  gregg
// added extra RoleDescription column to ChangeHistoryGroup
//
// Revision 1.4  2003/03/04 19:05:08  gregg
// ChangeHistoryGroup ->EntityChangeHistoryGroup type in constructor
//
// Revision 1.3  2003/02/28 00:42:11  gregg
// moving everything we can up into abstract ChangeHistory objects
//
// Revision 1.2  2003/02/25 23:58:07  gregg
// removed duplicate member variables
//
// Revision 1.1  2003/02/25 23:54:53  gregg
// initial load - no EANTableWrapper/Addressable yet..
//
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;

/**
 * Represents one Change History record in the PDH for one EntityItem
 * i.e. who changed the record and when was it changed
 */
public class EntityChangeHistoryItem extends ChangeHistoryItem {

    /**
    * @serial
    */
    static final long serialVersionUID = 1L;

    /**
     * Think about this ... why would anybody need to create one of these?? (so protected for now)
     *
     * @param _strChangeDate
     * @param _bValid
     * @param _bActive
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @param _echg
     * @param _prof
     * @param _strUser
     * @param _strRole
     * @param _strChgDesc 
     */
    protected EntityChangeHistoryItem(EntityChangeHistoryGroup _echg, Profile _prof, String _strUser, String _strRole, String _strChgDesc, String _strChangeDate, boolean _bValid, boolean _bActive) throws MiddlewareRequestException {
        super(_echg, _prof, _strUser, _strRole, _strChgDesc, _strChangeDate, _bValid, _bActive);
    }

    /**
     * (non-Javadoc)
     * getFlagCode
     *
     * @see COM.ibm.eannounce.objects.ChangeHistoryItem#getFlagCode()
     */
    public String getFlagCode() {
        return null;
    }
}
