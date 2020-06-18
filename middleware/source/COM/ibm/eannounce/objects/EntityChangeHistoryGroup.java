//
// Copyright (c) 2003, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: EntityChangeHistoryGroup.java,v $
// Revision 1.22  2008/02/25 15:37:01  wendy
// check for rs!=null before close
//
// Revision 1.21  2005/02/28 19:43:53  dave
// more Jtest fixes from over the weekend
//
// Revision 1.20  2004/09/28 21:16:35  dave
// more change history stuff
//
// Revision 1.19  2004/09/14 23:27:27  dave
// syntax fixes
//
// Revision 1.18  2004/09/14 22:52:11  dave
// syntax
//
// Revision 1.17  2004/09/14 22:31:40  dave
// new change history stuff.. to include Change group in
// addition to role information
//
// Revision 1.16  2003/12/23 20:58:26  gregg
// debug trace
//
// Revision 1.15  2003/12/23 20:57:38  gregg
// rdrs fix
//
// Revision 1.14  2003/12/23 20:22:07  gregg
// new SP GBL7552
//
// Revision 1.13  2003/06/02 19:20:18  gregg
// sync w/ 1.1.1
//
// Revision 1.12  2003/05/09 21:58:27  gregg
// remove emf parent param from ChangeHistoryGroup constructor
//
// Revision 1.11  2003/03/28 19:34:32  gregg
// _db.commit();
//
// Revision 1.10  2003/03/20 20:09:48  gregg
// debug on gbl7539
//
// Revision 1.9  2003/03/11 21:31:06  gregg
// compile fix (xtra param for GBL7539)
//
// Revision 1.8  2003/03/11 21:16:48  gregg
// added extra RoleDescription column to ChangeHistoryGroup
//
// Revision 1.7  2003/03/05 00:05:01  gregg
// some cleanup
//
// Revision 1.6  2003/03/05 00:03:51  gregg
// ok, remove getClassSpecificInfo altogether
//
// Revision 1.5  2003/03/04 23:58:14  gregg
// abstract cloneFoundation method replaced by getClassSpecific info method
//
// Revision 1.4  2003/02/28 20:46:09  gregg
// use getColumnDate to get changeDate from rdrs
//
// Revision 1.3  2003/02/28 00:42:11  gregg
// moving everything we can up into abstract ChangeHistory objects
//
// Revision 1.2  2003/02/26 00:06:36  gregg
// compile fix
//
// Revision 1.1  2003/02/25 23:54:53  gregg
// initial load - no EANTableWrapper/Addressable yet..
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
 * EntityChangeHistoryGroup
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EntityChangeHistoryGroup extends ChangeHistoryGroup {

    /**
     * EntityChangeHistoryGroup
     *
     * @param _db
     * @param _prof
     * @param _ei
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public EntityChangeHistoryGroup(Database _db, Profile _prof, EntityItem _ei) throws MiddlewareRequestException {
        // for now, downlink to et2 if itsa relator - this assumes a lot....
        super(_db, _prof, _ei);

        T.est(_ei != null, "EntityChangeHistoryGroup Constructor:Passed EntityItem cannot be null!");

		String strEntityType = _ei.getEntityType();
		int iEntityID = _ei.getEntityID();
		ResultSet rs = null;
		ReturnDataResultSet rdrsOuter = null;
		ReturnDataResultSet rdrsInner = null;

		try {

			try {
				rs = _db.callGBL7539(new ReturnStatus(-1), getProfile().getEnterprise(), strEntityType, iEntityID, getProfile().getReadLanguage().getNLSID());
				rdrsOuter = new ReturnDataResultSet(rs);
			} finally {
				if (rs != null){
					rs.close();
					rs = null;
				}
				_db.commit();
				_db.freeStatement();
				_db.isPending();
			}
			for (int row = 0; row < rdrsOuter.getRowCount(); row++) {
				String strValFrom = rdrsOuter.getColumnDate(row, 0);
				int iOPWGID = rdrsOuter.getColumnInt(row, 4);
				int iTRANID = rdrsOuter.getColumnInt(row, 5);
				boolean bCurrent = (rdrsOuter.getColumn(row, 6).equalsIgnoreCase(ChangeHistoryItem.CURRENT) ? true : false);
				boolean bActive = (rdrsOuter.getColumn(row, 7).equalsIgnoreCase(ChangeHistoryItem.ACTIVE) ? true : false);
				try {
					rs = _db.callGBL7552(new ReturnStatus(-1), getProfile().getEnterprise(), iOPWGID, iTRANID, getProfile().getReadLanguage().getNLSID());
					rdrsInner = new ReturnDataResultSet(rs);
				} finally {
					if (rs != null){
						rs.close();
						rs = null;
					}
					_db.commit();
					_db.freeStatement();
					_db.isPending();
				}
				//try {
					String strUserTok = rdrsInner.getColumn(0, 0);
					String strRoleDesc = rdrsInner.getColumn(0, 1);
					String strChgDesc = rdrsInner.getColumn(0, 2);
					putChangeHistoryItem(new EntityChangeHistoryItem(this, getProfile(), strUserTok, strRoleDesc, strChgDesc, strValFrom, bCurrent, bActive));
				//} finally {
				//}
			}
		} catch (Exception exc) {
			_db.debug(D.EBUG_ERR, "EntityChangeHistoryGroup constructor:" + exc.toString());
			exc.printStackTrace();
		} finally {

			_db.freeStatement();
			_db.isPending();

		}
    }

    /**
     * We dont need any extra columns for EntityItems
     *
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     */
    protected void putColumns() throws MiddlewareRequestException {
        return;
    }

    /////////////////
    /// ACCESSORS ///
    /////////////////

}
