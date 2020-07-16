/*
 * Created on May 26, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package COM.ibm.eannounce.catalog;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Vector;
import java.util.Hashtable;

import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.DatePackage;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.ReturnStatus;

/**
 * @author David Bigelow
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SyncMapCollection extends CatObject {

	private Vector m_vct = null;
//	private Hashtable m_hash = null;

	/**
	 * Simple constructor
	 *
	 */
	protected SyncMapCollection() {
		super();
	}

	public SyncMapCollection(
		Catalog _cat,
		String _strVE,
		String _strEntityType,
		int _istartEntityID,
		int _iendEntityID
		) {

		super();

		try {

			ReturnDataResultSet rdrs = null;
			ResultSet rs = null;

			Database db = _cat.getCatalogDatabase();
			DatePackage dp = db.getDates();
			Profile prof = _cat.getCatalogProfile();
			String strEnterprise = _cat.getEnterprise();
			String strRoleCode = prof.getRoleCode();
			int iSessionID = db.getNewSessionID();

			//
			// ok.. lets load up the request
			//
			db.callGBL8180(new ReturnStatus(-1), iSessionID, strEnterprise, _strVE,_strEntityType, _istartEntityID, _iendEntityID);
			db.commit();
			db.freeStatement();
			db.isPending();

			try {
				rs =
					db.callGBL8184(
						new ReturnStatus(-1),
						iSessionID,
						strEnterprise,
						_strEntityType,
						_strVE,
						strRoleCode,
						this.EPOCH,
						// see if this turns them back on
						dp.getNow(),
						1, "", -1,1);

				// Lets load them into a return data result set that contains all our transactions
				rdrs = new ReturnDataResultSet(rs);
			} finally {
				rs.close();
				rs = null;
				db.commit();
				db.freeStatement();
				db.isPending();
			}
			db.debug(D.EBUG_DETAIL, "GBL8184:recordcount:" + rdrs.size());

			for (int i = rdrs.size() - 1; i >= 0; i--) {
				int y = 0;
				String strGenArea = rdrs.getColumn(i, y++);
				String strRootType = rdrs.getColumn(i, y++);
				int iRootID = rdrs.getColumnInt(i, y++);
				String strRootTran = rdrs.getColumn(i, y++);
				String strChildType = rdrs.getColumn(i, y++);
				int iChildID = rdrs.getColumnInt(i, y++);
				String strChildTran = rdrs.getColumn(i, y++);
				int iChildLevel = rdrs.getColumnInt(i, y++);
				String strChildClass = rdrs.getColumn(i, y++);
				String strChildPath = rdrs.getColumn(i, y++);
				String strRelChildType = rdrs.getColumn(i, y++);
				int iRelChildID = rdrs.getColumnInt(i, y++);
				String strRelParentType = rdrs.getColumn(i, y++);
				int iRelParentID = rdrs.getColumnInt(i, y++);
				String strChildGenArea = rdrs.getColumn(i, y++);

				db.debug(
					D.EBUG_SPEW,
					"GBL8184:answer:"
						+ strGenArea
						+ ":"
						+ strRootType
						+ ":"
						+ iRootID
						+ ":"
						+ strRootTran
						+ ":"
						+ strChildGenArea
						+ ":"
						+ strChildType
						+ ":"
						+ iChildID
						+ ":"
						+ strChildTran
						+ ":"
						+ iChildLevel
						+ ":"
						+ strChildClass
						+ ":"
						+ strChildPath
						+ ":"
						+ strRelChildType
						+ ":"
						+ iRelChildID
						+ ":"
						+ strRelParentType
						+ ":"
						+ iRelParentID
						+ ":"
						+ strChildGenArea);

				SyncMapItem smi = new SyncMapItem();
				smi.setGeneralAreaName(strGenArea);
				smi.setRootType(strRootType);
				smi.setRootID(iRootID);
				smi.setRootTran(strRootTran);
				smi.setChildType(strChildType);
				smi.setChildID(iChildID);
				smi.setChildTran(strChildTran);
				smi.setChildLevel(iChildLevel);
				smi.setChildClass(strChildClass);
				smi.setChildPath(strChildPath);
				smi.setEntity2Type(strRelChildType);
				smi.setEntity2ID(iRelChildID);
				smi.setEntity1Type(strRelParentType);
				smi.setEntity1ID(iRelParentID);
				smi.setChildGeneralAreaName(strChildGenArea);

				this.add(smi);
                //
                //
                // Lets work on memory here..
                //
                rdrs.remove(i);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MiddlewareException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// TO DO
		}

	}

	public SyncMapCollection(
		Catalog _cat,
		String _strVE,
		String _strEntityType,
		int _istartEntityID,
		int _iendEntityID,
		int _iSessionID_prior, List _sessionIdList
		) {

		super();

		try {

			ReturnDataResultSet rdrs = null;
			ResultSet rs = null;

			Database db = _cat.getCatalogDatabase();
			DatePackage dp = db.getDates();
			Profile prof = _cat.getCatalogProfile();
			String strEnterprise = _cat.getEnterprise();
			String strRoleCode = prof.getRoleCode();
			int iSessionID_curr = db.getNewSessionID();
            _sessionIdList.add(new Integer(iSessionID_curr));

            D.ebug(D.EBUG_SPEW,"Start of SyncMapCollection");

			//
			// ok.. lets load up the request
			//
			db.callGBL8181(new ReturnStatus(-1), _iSessionID_prior, iSessionID_curr, strEnterprise, _strVE,_strEntityType, _istartEntityID, _iendEntityID);
			db.commit();
			db.freeStatement();
			db.isPending();

			try {
				rs =
					db.callGBL8184(
						new ReturnStatus(-1),
						iSessionID_curr,
						strEnterprise,
						_strEntityType,
						_strVE,
						strRoleCode,
						this.EPOCH,
						// see if this turns them back on
						dp.getNow(),
						1, "", -1,1);

				// Lets load them into a return data result set that contains all our transactions
				rdrs = new ReturnDataResultSet(rs);
			} finally {
				rs.close();
				rs = null;
				db.commit();
				db.freeStatement();
				db.isPending();
			}
			db.debug(D.EBUG_DETAIL, "GBL8184:recordcount:" + rdrs.size());

			for (int i = rdrs.size() - 1; i >= 0; i--) {
				int y = 0;
				String strGenArea = rdrs.getColumn(i, y++);
				String strRootType = rdrs.getColumn(i, y++);
				int iRootID = rdrs.getColumnInt(i, y++);
				String strRootTran = rdrs.getColumn(i, y++);
				String strChildType = rdrs.getColumn(i, y++);
				int iChildID = rdrs.getColumnInt(i, y++);
				String strChildTran = rdrs.getColumn(i, y++);
				int iChildLevel = rdrs.getColumnInt(i, y++);
				String strChildClass = rdrs.getColumn(i, y++);
				String strChildPath = rdrs.getColumn(i, y++);
				String strRelChildType = rdrs.getColumn(i, y++);
				int iRelChildID = rdrs.getColumnInt(i, y++);
				String strRelParentType = rdrs.getColumn(i, y++);
				int iRelParentID = rdrs.getColumnInt(i, y++);
				String strChildGenArea = rdrs.getColumn(i, y++);

				db.debug(
					D.EBUG_SPEW,
					"GBL8184:answer:"
						+ strGenArea
						+ ":"
						+ strRootType
						+ ":"
						+ iRootID
						+ ":"
						+ strRootTran
						+ ":"
						+ strChildGenArea
						+ ":"
						+ strChildType
						+ ":"
						+ iChildID
						+ ":"
						+ strChildTran
						+ ":"
						+ iChildLevel
						+ ":"
						+ strChildClass
						+ ":"
						+ strChildPath
						+ ":"
						+ strRelChildType
						+ ":"
						+ iRelChildID
						+ ":"
						+ strRelParentType
						+ ":"
						+ iRelParentID
						+ ":"
						+ strChildGenArea);

				SyncMapItem smi = new SyncMapItem();
				smi.setGeneralAreaName(strGenArea);
				smi.setRootType(strRootType);
				smi.setRootID(iRootID);
				smi.setRootTran(strRootTran);
				smi.setChildType(strChildType);
				smi.setChildID(iChildID);
				smi.setChildTran(strChildTran);
				smi.setChildLevel(iChildLevel);
				smi.setChildClass(strChildClass);
				smi.setChildPath(strChildPath);
				smi.setEntity2Type(strRelChildType);
				smi.setEntity2ID(iRelChildID);
				smi.setEntity1Type(strRelParentType);
				smi.setEntity1ID(iRelParentID);
				smi.setChildGeneralAreaName(strChildGenArea);

				this.add(smi);
                //
                //
                // Lets work on memory here..
                //
                rdrs.remove(i);
			}
			D.ebug(D.EBUG_SPEW,"End of SyncMapCollection");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MiddlewareException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// TO DO
		}

	}
	
	/**
	 * Added by Jicky Zhao, 2007-07-24
	 * for break-up stored procedures based on VEs and corresponding EntityTypes
	 * 
	 * @param _cat
	 * @param _strVE
	 * @param _strEntityType
	 * @param _istartEntityID
	 * @param _iendEntityID
	 * @param _iSessionID_prior
	 * @param _suffix
	 */
	public SyncMapCollection(
			Catalog _cat,
			String _strVE,
			String _strEntityType,
			int _istartEntityID,
			int _iendEntityID,
			int _iSessionID_prior,
			String _suffix
			) {

			super();

			try {

				ReturnDataResultSet rdrs = null;
				ResultSet rs = null;

				Database db = _cat.getCatalogDatabase();
				db.setAutoCommit(false);
				DatabaseExt dbe = new DatabaseExt(db);
				DatePackage dp = db.getDates();
				Profile prof = _cat.getCatalogProfile();
				String strEnterprise = _cat.getEnterprise();
				String strRoleCode = prof.getRoleCode();
				int iSessionID_curr = db.getNewSessionID();

	            D.ebug(D.EBUG_SPEW,"Start of SyncMapCollection");

				//
				// ok.. lets load up the request
				//
				dbe.callGBL8181(new ReturnStatus(-1),_iSessionID_prior, iSessionID_curr, strEnterprise, _strVE, _strEntityType, _istartEntityID, _iendEntityID, _suffix);
	            //db.commit();
				db.freeStatement();
				db.isPending();

				try {
					//add by yangxiaojiang
					if(_strVE.equals(WorldWideProductCollection.SEO_VE)&&_suffix.equals("F")){
						rs =
						    db.callGBL8184W(
								new ReturnStatus(-1),
								iSessionID_curr,
								strEnterprise,
								_strEntityType,
								_strVE,
								strRoleCode,
								this.EPOCH,
								// see if this turns them back on
								dp.getNow(),
								1, "", -1,1);
					}
					else{
						
					rs =
					    dbe.callGBL8184(
							new ReturnStatus(-1),
							iSessionID_curr,
							strEnterprise,
							_strEntityType,
							_strVE,
							strRoleCode,
							this.EPOCH,
							// see if this turns them back on
							dp.getNow(),
							1, "", -1,1, _suffix);
					}
					
					// Lets load them into a return data result set that contains all our transactions
					rdrs = new ReturnDataResultSet(rs);
					db.debug(D.EBUG_DETAIL, "ReturnDataResultSet finish" );
					rs.close();
					rs=null;
					
				} finally {
					if(rs!=null){
						rs.close();
						rs=null;
					}
					db.commit();
					db.freeStatement();
					db.isPending();
				}
				db.debug(D.EBUG_DETAIL, "GBL8184:recordcount:" + rdrs.size());

				for (int i = rdrs.size() - 1; i >= 0; i--) {
					int y = 0;
					String strGenArea = rdrs.getColumn(i, y++);
					String strRootType = rdrs.getColumn(i, y++);
					int iRootID = rdrs.getColumnInt(i, y++);
					String strRootTran = rdrs.getColumn(i, y++);
					String strChildType = rdrs.getColumn(i, y++);
					int iChildID = rdrs.getColumnInt(i, y++);
					String strChildTran = rdrs.getColumn(i, y++);
					int iChildLevel = rdrs.getColumnInt(i, y++);
					String strChildClass = rdrs.getColumn(i, y++);
					String strChildPath = rdrs.getColumn(i, y++);
					String strRelChildType = rdrs.getColumn(i, y++);
					int iRelChildID = rdrs.getColumnInt(i, y++);
					String strRelParentType = rdrs.getColumn(i, y++);
					int iRelParentID = rdrs.getColumnInt(i, y++);
					String strChildGenArea = rdrs.getColumn(i, y++);

					db.debug(
						D.EBUG_SPEW,
						"GBL8184:answer:"
							+ strGenArea
							+ ":"
							+ strRootType
							+ ":"
							+ iRootID
							+ ":"
							+ strRootTran
							+ ":"
							+ strChildGenArea
							+ ":"
							+ strChildType
							+ ":"
							+ iChildID
							+ ":"
							+ strChildTran
							+ ":"
							+ iChildLevel
							+ ":"
							+ strChildClass
							+ ":"
							+ strChildPath
							+ ":"
							+ strRelChildType
							+ ":"
							+ iRelChildID
							+ ":"
							+ strRelParentType
							+ ":"
							+ iRelParentID
							+ ":"
							+ strChildGenArea);

					SyncMapItem smi = new SyncMapItem();
					smi.setGeneralAreaName(strGenArea);
					smi.setRootType(strRootType);
					smi.setRootID(iRootID);
					smi.setRootTran(strRootTran);
					smi.setChildType(strChildType);
					smi.setChildID(iChildID);
					smi.setChildTran(strChildTran);
					smi.setChildLevel(iChildLevel);
					smi.setChildClass(strChildClass);
					smi.setChildPath(strChildPath);
					smi.setEntity2Type(strRelChildType);
					smi.setEntity2ID(iRelChildID);
					smi.setEntity1Type(strRelParentType);
					smi.setEntity1ID(iRelParentID);
					smi.setChildGeneralAreaName(strChildGenArea);

					this.add(smi);
	                //
	                //
	                // Lets work on memory here..
	                //
	                rdrs.remove(i);
				}
				D.ebug(D.EBUG_SPEW,"End of SyncMapCollection");

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MiddlewareException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				// TO DO
			}

		}


	/**
	 * @param _cat
	 * @param _strEntityType
	 * @param _strVE
	 * @param _cati
	 * @param _bAllIn
	 */
	public SyncMapCollection(
		Catalog _cat,
		String _strEntityType,
		String _strVE,
		CatalogInterval _cati,
		int _iType) {

		super();

		// Here we insert record into the trsNetterPass2 table
		// _db.callGBL8101(returnStatus, iSessionID, strEnterprise, strEntityType, iEntityID, strActionItemKey, m_strEndDate);

		try {

			ReturnDataResultSet rdrs = null;
			ResultSet rs = null;

			Database db = _cat.getCatalogDatabase();
			Profile prof = _cat.getCatalogProfile();
			String strEnterprise = _cat.getEnterprise();
			String strRoleCode = prof.getRoleCode();
			int iSessionID = db.getNewSessionID();

			// Now.. pass bypass 1

			try {
				rs =
					db.callGBL8184(
						new ReturnStatus(-1),
						iSessionID,
						strEnterprise,
						_strEntityType,
						_strVE,
						strRoleCode,
						_cati.getStartDate(),
						_cati.getEndDate(),
						_iType, "", -1,1);
				// Lets load them into a return data result set that contains all our transactions
				rdrs = new ReturnDataResultSet(rs);
			} finally {
				rs.close();
				rs = null;
				db.commit();
				db.freeStatement();
				db.isPending();
			}
			db.debug(D.EBUG_DETAIL, "GBL8184:recordcount:" + rdrs.size());

			for (int i = rdrs.size() - 1; i >= 0; i--) {
				int y = 0;
				String strGenArea = rdrs.getColumn(i, y++);
				String strRootType = rdrs.getColumn(i, y++);
				int iRootID = rdrs.getColumnInt(i, y++);
				String strRootTran = rdrs.getColumn(i, y++);
				String strChildType = rdrs.getColumn(i, y++);
				int iChildID = rdrs.getColumnInt(i, y++);
				String strChildTran = rdrs.getColumn(i, y++);
				int iChildLevel = rdrs.getColumnInt(i, y++);
				String strChildClass = rdrs.getColumn(i, y++);
				String strChildPath = rdrs.getColumn(i, y++);
				String strRelChildType = rdrs.getColumn(i, y++);
				int iRelChildID = rdrs.getColumnInt(i, y++);
				String strRelParentType = rdrs.getColumn(i, y++);
				int iRelParentID = rdrs.getColumnInt(i, y++);
				String strChildGenArea = rdrs.getColumn(i, y++);

				db.debug(
					D.EBUG_SPEW,
					"GBL8184:answer:"
						+ strGenArea
						+ ":"
						+ strRootType
						+ ":"
						+ iRootID
						+ ":"
						+ strRootTran
						+ ":"
						+ strChildGenArea
						+ ":"
						+ strChildType
						+ ":"
						+ iChildID
						+ ":"
						+ strChildTran
						+ ":"
						+ iChildLevel
						+ ":"
						+ strChildClass
						+ ":"
						+ strChildPath
						+ ":"
						+ strRelChildType
						+ ":"
						+ iRelChildID
						+ ":"
						+ strRelParentType
						+ ":"
						+ iRelParentID
						+ ":"
						+ strChildGenArea);

				SyncMapItem smi = new SyncMapItem();
				smi.setGeneralAreaName(strGenArea);
				smi.setRootType(strRootType);
				smi.setRootID(iRootID);
				smi.setRootTran(strRootTran);
				smi.setChildType(strChildType);
				smi.setChildID(iChildID);
				smi.setChildTran(strChildTran);
				smi.setChildLevel(iChildLevel);
				smi.setChildClass(strChildClass);
				smi.setChildPath(strChildPath);
				smi.setEntity2Type(strRelChildType);
				smi.setEntity2ID(iRelChildID);
				smi.setEntity1Type(strRelParentType);
				smi.setEntity1ID(iRelParentID);
				smi.setChildGeneralAreaName(strChildGenArea);

				this.add(smi);
                //
                //
                // Lets work on memory here..
                //
                rdrs.remove(i);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MiddlewareException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// TO DO
		}

	}

	/**
	 * @param _cat
	 * @param _strEntityType
	 * @param _strVE
	 * @param _cati
	 * @param _bAllIn
	 */
	public SyncMapCollection(
		Catalog _cat,
		String _strEntityType,
		String _strVE,
		CatalogInterval _cati,
		int _iType,
		String _strChildEntityType,
		int _iChildEntityID) {

		super();

		// Here we insert record into the trsNetterPass2 table
		// _db.callGBL8101(returnStatus, iSessionID, strEnterprise, strEntityType, iEntityID, strActionItemKey, m_strEndDate);

		try {

			ReturnDataResultSet rdrs = null;
			ResultSet rs = null;

			Database db = _cat.getCatalogDatabase();
			Profile prof = _cat.getCatalogProfile();
			String strEnterprise = _cat.getEnterprise();
			String strRoleCode = prof.getRoleCode();
			int iSessionID = db.getNewSessionID();

			// Now.. pass bypass 1

			try {
				rs =
					db.callGBL8184(
						new ReturnStatus(-1),
						iSessionID,
						strEnterprise,
						_strEntityType,
						_strVE,
						strRoleCode,
						_cati.getStartDate(),
						_cati.getEndDate(),
						_iType,
						_strChildEntityType,
						_iChildEntityID,0);
				// Lets load them into a return data result set that contains all our transactions
				rdrs = new ReturnDataResultSet(rs);
			} finally {
				rs.close();
				rs = null;
				db.commit();
				db.freeStatement();
				db.isPending();
			}
			db.debug(D.EBUG_DETAIL, "GBL8184:recordcount:" + rdrs.size());

			for (int i = rdrs.size() - 1; i >= 0; i--) {
				int y = 0;
				String strGenArea = rdrs.getColumn(i, y++);
				String strRootType = rdrs.getColumn(i, y++);
				int iRootID = rdrs.getColumnInt(i, y++);
				String strRootTran = rdrs.getColumn(i, y++);
				String strChildType = rdrs.getColumn(i, y++);
				int iChildID = rdrs.getColumnInt(i, y++);
				String strChildTran = rdrs.getColumn(i, y++);
				int iChildLevel = rdrs.getColumnInt(i, y++);
				String strChildClass = rdrs.getColumn(i, y++);
				String strChildPath = rdrs.getColumn(i, y++);
				String strRelChildType = rdrs.getColumn(i, y++);
				int iRelChildID = rdrs.getColumnInt(i, y++);
				String strRelParentType = rdrs.getColumn(i, y++);
				int iRelParentID = rdrs.getColumnInt(i, y++);
				String strChildGenArea = rdrs.getColumn(i, y++);

				db.debug(
					D.EBUG_SPEW,
					"GBL8184:answer:"
						+ strGenArea
						+ ":"
						+ strRootType
						+ ":"
						+ iRootID
						+ ":"
						+ strRootTran
						+ ":"
						+ strChildGenArea
						+ ":"
						+ strChildType
						+ ":"
						+ iChildID
						+ ":"
						+ strChildTran
						+ ":"
						+ iChildLevel
						+ ":"
						+ strChildClass
						+ ":"
						+ strChildPath
						+ ":"
						+ strRelChildType
						+ ":"
						+ iRelChildID
						+ ":"
						+ strRelParentType
						+ ":"
						+ iRelParentID
						+ ":"
						+ strChildGenArea);

				SyncMapItem smi = new SyncMapItem();
				smi.setGeneralAreaName(strGenArea);
				smi.setRootType(strRootType);
				smi.setRootID(iRootID);
				smi.setRootTran(strRootTran);
				smi.setChildType(strChildType);
				smi.setChildID(iChildID);
				smi.setChildTran(strChildTran);
				smi.setChildLevel(iChildLevel);
				smi.setChildClass(strChildClass);
				smi.setChildPath(strChildPath);
				smi.setEntity2Type(strRelChildType);
				smi.setEntity2ID(iRelChildID);
				smi.setEntity1Type(strRelParentType);
				smi.setEntity1ID(iRelParentID);
				smi.setChildGeneralAreaName(strChildGenArea);

				this.add(smi);
                //
                //
                // Lets work on memory here..
                //
                rdrs.remove(i);
			}

            // Now remove all the records to clean up after yourself
            db.callGBL8109(new ReturnStatus(-1), iSessionID, strEnterprise);
            db.commit();
            db.freeStatement();
            db.isPending();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MiddlewareException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// TO DO
		}

	}

	/**
	 * @param _cat
	 * @param _strEntityType
	 * @param _strVE
	 * @param _cati
	 * @param _bAllIn
	 */
	public SyncMapCollection(
		Catalog _cat,
		String _strEntityType,
		int _iEntityID,
		String _strVE,
		CatalogInterval _cati,
		int _iType) {

		super();

		try {

			ReturnDataResultSet rdrs = null;
			ResultSet rs = null;

			Database db = _cat.getCatalogDatabase();
			Profile prof = _cat.getCatalogProfile();
			String strEnterprise = _cat.getEnterprise();
			String strRoleCode = prof.getRoleCode();
			int iSessionID = db.getNewSessionID();

			// Now.. pass bypass 1
			// Here we insert record into the trsNetterPass2 table
			db.callGBL8101(new ReturnStatus(-1), iSessionID, strEnterprise, _strEntityType, _iEntityID, _strVE, _cati.getEndDate());


			try {
				rs =
					db.callGBL8184(
						new ReturnStatus(-1),
						iSessionID,
						strEnterprise,
						_strEntityType,
						_strVE,
						strRoleCode,
						_cati.getStartDate(),
						_cati.getEndDate(),
						_iType, "", -1,1);
				// Lets load them into a return data result set that contains all our transactions
				rdrs = new ReturnDataResultSet(rs);
			} finally {
				rs.close();
				rs = null;
				db.commit();
				db.freeStatement();
				db.isPending();
			}
			db.debug(D.EBUG_DETAIL, "GBL8184:recordcount:" + rdrs.size());

			for (int i = rdrs.size() - 1; i >= 0; i--) {
				int y = 0;
				String strGenArea = rdrs.getColumn(i, y++);
				String strRootType = rdrs.getColumn(i, y++);
				int iRootID = rdrs.getColumnInt(i, y++);
				String strRootTran = rdrs.getColumn(i, y++);
				String strChildType = rdrs.getColumn(i, y++);
				int iChildID = rdrs.getColumnInt(i, y++);
				String strChildTran = rdrs.getColumn(i, y++);
				int iChildLevel = rdrs.getColumnInt(i, y++);
				String strChildClass = rdrs.getColumn(i, y++);
				String strChildPath = rdrs.getColumn(i, y++);
				String strRelChildType = rdrs.getColumn(i, y++);
				int iRelChildID = rdrs.getColumnInt(i, y++);
				String strRelParentType = rdrs.getColumn(i, y++);
				int iRelParentID = rdrs.getColumnInt(i, y++);
				String strChildGenArea = rdrs.getColumn(i, y++);

				db.debug(
					D.EBUG_SPEW,
					"GBL8184:answer:"
						+ strGenArea
						+ ":"
						+ strRootType
						+ ":"
						+ iRootID
						+ ":"
						+ strRootTran
						+ ":"
						+ strChildGenArea
						+ ":"
						+ strChildType
						+ ":"
						+ iChildID
						+ ":"
						+ strChildTran
						+ ":"
						+ iChildLevel
						+ ":"
						+ strChildClass
						+ ":"
						+ strChildPath
						+ ":"
						+ strRelChildType
						+ ":"
						+ iRelChildID
						+ ":"
						+ strRelParentType
						+ ":"
						+ iRelParentID
						+ ":"
						+ strChildGenArea);

				SyncMapItem smi = new SyncMapItem();
				smi.setGeneralAreaName(strGenArea);
				smi.setRootType(strRootType);
				smi.setRootID(iRootID);
				smi.setRootTran(strRootTran);
				smi.setChildType(strChildType);
				smi.setChildID(iChildID);
				smi.setChildTran(strChildTran);
				smi.setChildLevel(iChildLevel);
				smi.setChildClass(strChildClass);
				smi.setChildPath(strChildPath);
				smi.setEntity2Type(strRelChildType);
				smi.setEntity2ID(iRelChildID);
				smi.setEntity1Type(strRelParentType);
				smi.setEntity1ID(iRelParentID);
				smi.setChildGeneralAreaName(strChildGenArea);

				this.add(smi);
                //
                //
                // Lets work on memory here..
                //
                rdrs.remove(i);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MiddlewareException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// TO DO
		}

	}

	// TODO Auto-generated constructor stub

	public void add(SyncMapItem _ci) {
		if (m_vct == null) {
			m_vct = new Vector();
		}
		m_vct.add(_ci);
//		if (m_hash == null) {
//			m_hash = new Hashtable();
//		}
//		m_hash.put(_ci.toString(),_ci);
	}

	public SyncMapItem get(int _i) {
		// TODO Auto-generated method stub
		return (SyncMapItem) m_vct.elementAt(_i);
	}

//	public SyncMapItem get(String _s) {
//		// TODO Auto-generated method stub
//		return (SyncMapItem) m_hash.get(_s);
//	}

	public final int getCount() {
		if (m_vct == null) {
			return 0;
		}
		return m_vct.size();
	}

	public static void main(String[] args) {
	}

	public String toString() {

		StringBuffer sb = new StringBuffer();
		sb.append("SYNC.MAP.COLLECTION:");
		for (int x = 0; x < getCount(); x++) {
			SyncMapItem smi = get(x);
			sb.append("\n" + smi);
		}
		sb.append("\nEND OF INFORMATION:\n");
		return sb.toString();
	}

	public String dump(boolean _breif) {
		// TODO Auto-generated method stub
		return toString();
	}

	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}

	public final Collection getCollection() {
		return m_vct;
	}

//	private final Hashtable getHashtable() {
//		return m_hash;
//	}

	/**
	 * merge - we take one SyncMapCollection and
	 * we add it to the end of this one
	 * This will only do something if the passes Collection
	 * has values.  Otherwise it leaves this object untouched
	 *
	 * @param _smc
	 */
	public final void merge(SyncMapCollection _smc) {
		if (m_vct == null && _smc.getCount() > 0) {
			m_vct = new Vector();
		}
		if (_smc.getCount() > 0) {
			m_vct.addAll(_smc.getCollection());
		}
		//
//		if (m_hash == null && _smc.getCount() > 0) {
//			m_hash = new Hashtable();
//		}
//		if (_smc.getCount() > 0) {
//			m_hash.putAll(_smc.getHashtable());
//		}
	}

        /**
         * clear and distroy SMI along with it
         */
        public final void clearAll() {
            D.ebug("clearAll SMC...");
            for (int i = this.getCount() - 1; i >= 0; i--) {
                SyncMapItem smi = this.get(i);
                smi.clear();
                this.remove(i);
            }
            this.m_vct = null;
        }

        public final void remove(int _i) {
            SyncMapItem smi = (SyncMapItem) m_vct.elementAt(_i);
            m_vct.remove(_i);
         }


	/**
	 * conatins - check if our collection contains the specified item.
	 *
	 * @param _smc
	 */
//	 public final boolean contains(SyncMapItem _smi) {
//		 if(m_hash == null || getCount() == 0) {
//			 return false;
//		 }
//		 return (m_hash.get(_smi.toString()) != null);
//	 }
}
/**
* $Log: SyncMapCollection.java,v $
* Revision 1.13  2011/05/05 11:21:33  wendy
* src from IBMCHINA
*
* Revision 1.6  2008/11/19 01:18:12  yang
* add by guobin for gbl8184w
*
* Revision 1.12  2008/05/23 07:22:59  yang
* no message
*
* Revision 1.5  2007/10/29 09:40:46  jingb
* Change method:
* SyncMapCollection(
* 		Catalog _cat,
* 		String _strVE,
* 		String _strEntityType,
* 		int _istartEntityID,
* 		int _iendEntityID,
* 		int _iSessionID_prior, List _sessionIdList
* 		)
*
* Revision 1.4  2007/10/09 06:47:18  jingb
* *** empty log message ***
*
* Revision 1.3  2007/09/26 08:52:39  jingb
* *** empty log message ***
*
* Revision 1.2  2007/09/13 05:54:53  sulin
* no message
*
* Revision 1.1.1.1  2007/06/05 02:09:30  jingb
* no message
*
* Revision 1.11  2006/09/01 20:53:51  gregg
* debugs
*
* Revision 1.10  2006/08/28 20:07:26  dave
* ok.. minor memory fixes
*
* Revision 1.9  2006/08/08 22:57:26  gregg
* compile
*
* Revision 1.8  2006/08/07 21:36:10  gregg
* prepping for chunky munky delta
*
* Revision 1.7  2006/07/04 18:59:48  dave
* changed the sp
*
* Revision 1.6  2006/06/30 21:24:36  gregg
* now 8184
*
* Revision 1.5  2006/06/22 22:45:18  gregg
* temporarily setting back to 8104 for prod
*
* Revision 1.4  2006/06/20 02:42:26  dave
* memory cleanup
*
* Revision 1.3  2006/06/19 22:36:22  gregg
* GBL8184-->GBL8184
*
* Revision 1.2  2006/06/12 17:30:46  gregg
* memory management for processing rdrs's
*
* Revision 1.1.1.1  2006/03/30 17:36:31  gregg
* Moving catalog module from middleware to
* its own module.
*
* Revision 1.21  2006/02/20 17:41:09  joan
* change System.out.println to D.ebug statements
*
* Revision 1.20  2006/02/01 22:27:18  joan
* add condition to see whether filter is needed in sp
*
* Revision 1.19  2006/01/30 22:33:06  joan
* try to add Pass=4 to GBL8184
*
* Revision 1.18  2005/09/12 01:33:15  dave
* log level changes
*
* Revision 1.17  2005/06/24 00:18:52  gregg
* contains
*
* Revision 1.16  2005/06/13 05:18:06  dave
* found a good bug
*
* Revision 1.15  2005/06/13 04:35:34  dave
* ! needs to be not !
*
* Revision 1.14  2005/06/07 03:54:23  dave
* not callable fix
*
* Revision 1.13  2005/06/07 03:32:13  dave
* syntax
*
* Revision 1.12  2005/06/07 03:25:29  dave
* new IDL sp and approach
*
* Revision 1.11  2005/06/05 20:33:55  dave
* going for the Feature Update to the CatDB
*
* Revision 1.10  2005/06/05 19:35:06  dave
* more logging
*
* Revision 1.9  2005/06/01 23:10:46  joan
* getting stuff for feature from pdh
*
* Revision 1.8  2005/06/01 02:50:20  dave
* net change to full image pull
*
* Revision 1.7  2005/06/01 02:20:11  dave
* ok.. more cleanup and ve work
*
* Revision 1.6  2005/05/28 19:17:53  dave
* ok.. lets go for a robust dump statement
*
* Revision 1.5  2005/05/27 21:46:59  dave
* trying to split it up
*
* Revision 1.4  2005/05/27 05:41:34  dave
* trapping null pointer
*
* Revision 1.3  2005/05/27 02:46:02  dave
* ok.. lets hook up a test case here
*
* Revision 1.2  2005/05/27 02:09:29  dave
* introducing ChildGeneralArea
*
* Revision 1.1  2005/05/27 02:03:09  dave
* ok.. first attempt at focusing on isolating GBL8184 from
* the world
*
*/
