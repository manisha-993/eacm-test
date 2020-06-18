//
// Copyright (c) 2003, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: AttributeChangeHistoryGroup.java,v $
// Revision 1.15  2005/02/09 23:46:46  dave
// more Jtest Cleanup
//
// Revision 1.14  2004/09/28 21:16:35  dave
// more change history stuff
//
// Revision 1.13  2004/09/14 23:27:27  dave
// syntax fixes
//
// Revision 1.12  2004/09/14 22:31:40  dave
// new change history stuff.. to include Change group in
// addition to role information
//
// Revision 1.11  2003/12/23 20:59:09  gregg
// use GBL7522
//
// Revision 1.10  2003/06/19 20:31:59  dave
// changes to ChangeHistoryItem
//
// Revision 1.9  2003/06/02 19:20:18  gregg
// sync w/ 1.1.1
//
// Revision 1.8  2003/05/09 22:06:28  gregg
// compile fix
//
// Revision 1.7  2003/05/09 21:58:27  gregg
// remove emf parent param from ChangeHistoryGroup constructor
//
// Revision 1.6  2003/03/27 23:07:19  dave
// adding some timely commits to free up result sets
//
// Revision 1.5  2003/03/11 21:16:48  gregg
// added extra RoleDescription column to ChangeHistoryGroup
//
// Revision 1.4  2003/03/05 18:28:41  gregg
// more comments
//
// Revision 1.3  2003/03/05 18:25:12  gregg
// comments indicating blob now included
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

import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.T;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import java.sql.ResultSet;

/**
 * Fetch Attribute changes for AttType == 'T',L',X','F','U','S','A','B'
 */
public class AttributeChangeHistoryGroup extends ChangeHistoryGroup {

    /**
     * FIELD
     */
    protected static final String ATTVAL_KEY = AttributeChangeHistoryItem.ATTVAL_KEY;
    /**
     * FIELD
     */
    protected static final String ATTVAL_DESC = AttributeChangeHistoryItem.ATTVAL_DESC;

    /**
     * AttributeChangeHistoryGroup
     *
     * @param _db
     * @param _prof
     * @param _ea
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public AttributeChangeHistoryGroup(Database _db, Profile _prof, EANAttribute _ea) throws MiddlewareRequestException {
        super(_db, _prof, _ea);

        T.est(_ea != null, "AttributeChangeHistoryGroup Constructor:Passed EANAttribute cannot be null!");
        try {
            
            String strEntityType = _ea.getEntityItem().getEntityType();
            int iEntityID = _ea.getEntityItem().getEntityID();
            String strAttributeCode = _ea.getAttributeCode();
            String strEnterprise = getProfile().getEnterprise();
            int iNLSID = getProfile().getReadLanguage().getNLSID();
            
            ResultSet rs = null;
            ReturnDataResultSet rdrsOuter = null;
            ReturnDataResultSet rdrsInner = null;
            try {
    
                try {
                    rs = _db.callGBL7540(new ReturnStatus(-1), strEnterprise, strEntityType, iEntityID, strAttributeCode, iNLSID);
                    rdrsOuter = new ReturnDataResultSet(rs);
                } finally {
                    rs.close();
                    rs = null;
                    _db.commit();
                    _db.freeStatement();
                    _db.isPending();
                }
                for (int row = 0; row < rdrsOuter.getRowCount(); row++) {

                    String strUserTok = null;
                    String strRoleDesc = null;
                    String strChgDesc = null;

                    String strValFrom = rdrsOuter.getColumnDate(row, 0);
                    int iOPWGID = rdrsOuter.getColumnInt(row, 4);
                    int iTRANID = rdrsOuter.getColumnInt(row, 5);
                    boolean bCurrent = (rdrsOuter.getColumn(row, 6).equalsIgnoreCase(ChangeHistoryItem.CURRENT) ? true : false);
                    boolean bActive = (rdrsOuter.getColumn(row, 7).equalsIgnoreCase(ChangeHistoryItem.ACTIVE) ? true : false);
                    String strAttributeValue = rdrsOuter.getColumn(row, 8);
                    String strFlagCode = rdrsOuter.getColumn(row, 9);

                    try {
                        _db.debug(D.EBUG_SPEW, "callGBL7552(" + getProfile().getEnterprise() + ":" + iOPWGID + ":" + iOPWGID + ":" + getProfile().getReadLanguage().getNLSID() + ")");
                        rs = _db.callGBL7552(new ReturnStatus(-1), getProfile().getEnterprise(), iOPWGID, iTRANID, getProfile().getReadLanguage().getNLSID());
                        rdrsInner = new ReturnDataResultSet(rs);
                    } finally {
                        rs.close();
                        rs = null;
                        _db.commit();
                        _db.freeStatement();
                        _db.isPending();
                    }
                    strUserTok = rdrsInner.getColumn(0, 0);
                    strRoleDesc = rdrsInner.getColumn(0, 1);
                    strChgDesc = rdrsInner.getColumn(0, 2);
                    putChangeHistoryItem(new AttributeChangeHistoryItem(this, getProfile(), strUserTok, strRoleDesc, strChgDesc, strValFrom, bCurrent, bActive, strAttributeValue, strFlagCode));
                }
            } catch (Exception exc) {
                _db.debug(D.EBUG_ERR, "AttributeChangeHistoryGroup constructor:" + exc.toString());
            } finally {
                _db.freeStatement();
                _db.isPending();
            }
        } finally {
        }
    }

    /**
     * One more column for AttributeValue
     *
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    protected void putColumns() throws MiddlewareRequestException {
        MetaLabel mlValid = new MetaLabel(null, getProfile(), ATTVAL_KEY, ATTVAL_DESC, SimplePicklistAttribute.class);
        mlValid.putShortDescription(ATTVAL_DESC);
        putMeta(mlValid);
    }
}
