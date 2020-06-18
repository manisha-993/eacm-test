//
// Copyright (c) 2003, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: AttributeChangeHistoryItem.java,v $
// Revision 1.12  2006/03/22 21:05:19  joan
// adjusting
//
// Revision 1.11  2005/02/09 23:46:46  dave
// more Jtest Cleanup
//
// Revision 1.10  2004/09/28 22:14:12  dave
// final fix
//
// Revision 1.9  2004/09/28 21:16:35  dave
// more change history stuff
//
// Revision 1.8  2004/09/14 22:31:40  dave
// new change history stuff.. to include Change group in
// addition to role information
//
// Revision 1.7  2003/06/19 21:14:03  dave
// checking in GBL8122
//
// Revision 1.6  2003/06/19 20:31:59  dave
// changes to ChangeHistoryItem
//
// Revision 1.5  2003/03/11 21:16:48  gregg
// added extra RoleDescription column to ChangeHistoryGroup
//
// Revision 1.4  2003/03/05 18:27:14  gregg
// now includes blobs (blobExtension)
//
// Revision 1.3  2003/03/05 01:39:05  gregg
// oopps
//
// Revision 1.2  2003/03/05 01:38:34  gregg
// compile fix
//
// Revision 1.1  2003/03/05 01:25:15  gregg
// moved name from TextChangeHistoryXXX to AttributeXXX.
// Now covers any EANAttribute minus blob
//
// Revision 1.1  2003/03/05 00:14:50  gregg
// initial load
//
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;

/**
 * Represents one Change History record in the PDH for one Attribute - i.e. who changed the record and when was it changed.
 * Note that AttributeValue for BlobAttribute is BlobExtension NOT actual blob att value.
 */
public class AttributeChangeHistoryItem extends ChangeHistoryItem {

    /**
    * @serial
    */
    static final long serialVersionUID = 1L;

    /**
     * FIELD
     */
    protected static final String ATTVAL_KEY = "ATTVAL";
    /**
     * FIELD
     */
    protected static final String ATTVAL_DESC = "Attribute Value";

    /**
     * FIELD
     */
    protected String m_strFlagCode = "";

    /**
     * Think about this ... why would anybody need to create one of these?? (so protected for 	now)
     *
     * @param _achg
     * @param _prof
     * @param _strUser
     * @param _strRoleDesc
     * @param _strChgDesc
     * @param _strChangeDate
     * @param _bValid
     * @param _bActive
     * @param _strAttributeValue
     * @param _strFlagCode
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     */
    protected AttributeChangeHistoryItem(AttributeChangeHistoryGroup _achg, Profile _prof, String _strUser, String _strRoleDesc, String _strChgDesc, String _strChangeDate, boolean _bValid, boolean _bActive, String _strAttributeValue, String _strFlagCode) throws MiddlewareRequestException {
        super(_achg, _prof, _strUser, _strRoleDesc, _strChgDesc, _strChangeDate, _bValid, _bActive);
        // Store Attribute Value as column
        putSimpleTextAttribute(ATTVAL_KEY, _strAttributeValue);
        m_strFlagCode = _strFlagCode;
    }

    /**
     * (non-Javadoc)
     * getFlagCode
     *
     * @see COM.ibm.eannounce.objects.ChangeHistoryItem#getFlagCode()
     */
    public String getFlagCode() {
        return m_strFlagCode;
    }

    /**
     * (non-Javadoc)
     * getFlagCode
     *
     * @see COM.ibm.eannounce.objects.ChangeHistoryItem#getFlagCode()
     */
    public String getAttributeValue() {
        return getSimpleTextAttribute(ATTVAL_KEY).toString();
    }
}
