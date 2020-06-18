//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: VELockERList.java,v $
// Revision 1.11  2009/05/15 17:46:52  wendy
// Support dereference for memory release
//
// Revision 1.10  2008/01/31 22:56:01  wendy
// Cleanup RSA warnings
//
// Revision 1.9  2005/01/18 21:46:51  dave
// more parm debug cleanup
//
// Revision 1.8  2003/01/17 19:40:29  joan
// fix commit
//
// Revision 1.7  2003/01/17 00:59:00  joan
// debug
//
// Revision 1.6  2003/01/17 00:35:35  joan
// debug
//
// Revision 1.5  2003/01/17 00:17:47  joan
// fix bug
//
// Revision 1.4  2003/01/16 23:50:43  joan
// fix bugs
//
// Revision 1.3  2003/01/16 21:31:37  joan
// add code to check for VELock when link
//
// Revision 1.2  2003/01/16 00:11:52  joan
// fix throw exception
//
// Revision 1.1  2003/01/16 00:02:24  joan
// initial load
//


package COM.ibm.eannounce.objects;

import java.io.*;
import java.sql.ResultSet;
import COM.ibm.opicmpdh.middleware.*;

// Exceptions
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import java.sql.SQLException;

public class VELockERList extends EANMetaEntity {

	private class VELockERItem extends EANMetaEntity {
		private static final long serialVersionUID = 1L;
		String m_strVEName = null;
		String m_strEntityType = null;
		String m_strRelatorType = null;

		protected void dereference(){
			super.dereference();
			m_strVEName = null;
			m_strEntityType = null;
			m_strRelatorType = null;
		}
		public VELockERItem(Profile _prof, String _strVEName, String _strEntityType, String _strRelatorType)  throws MiddlewareRequestException {
			super(null, _prof, _strVEName + _strEntityType + _strRelatorType);
		}

		public String dump(boolean _bBrief) {
			return "VELockERItemList:" + getKey();
		}
	}
	/**
	 * @serial
	 */
	static final long serialVersionUID = 1L;
	
	protected void dereference(){
		for (int i=0; i < getDataCount(); i++) {
			VELockERItem ve = (VELockERItem)getData(i);
			ve.dereference();
		}
		super.dereference();
	}

	/**
	 * Main method which performs a simple test of this class
	 */
	public static void main(String arg[]) {
	}

	public VELockERList(Database _db, Profile _prof, String _strEntityType) throws  SQLException, MiddlewareException, MiddlewareRequestException {
		super(null, _prof, _strEntityType);

		String strMethod = "VELockERList constructor";
		try {
			// The stored procedure ReturnStatus
			ReturnStatus returnStatus = new ReturnStatus(-1);
			ResultSet rs = null;
			ReturnDataResultSet rdrs = null;

			// A copy of the current time
			String strNow = null;
			// String strForever = null;

			String strEnterprise = _prof.getEnterprise();
			int iOPWGID = _prof.getOPWGID();

			_db.debug(D.EBUG_DETAIL, strMethod + " transaction");
			_db.debug(D.EBUG_DETAIL, "VELockERList: Enterprise: " + strEnterprise);
			_db.debug(D.EBUG_DETAIL, "VELockERList: OPENID: " + iOPWGID);
			_db.debug(D.EBUG_DETAIL, "VELockERList:" + getKey());

			DatePackage dpNow = _db.getDates();
			strNow = dpNow.getNow();
			//strForever = dpNow.getForever();

			// getting all the lock records based on strEntityType, iEntityID
			rs = _db.callGBL8206(returnStatus, strEnterprise, _strEntityType, strNow, strNow);
			rdrs = new ReturnDataResultSet(rs);
			rs.close();
			rs = null;
			_db.freeStatement();
			_db.isPending();

			for (int j = 0 ; j < rdrs.size(); j++) {
				String strVEName = rdrs.getColumn(j,0).trim();
				String strEntityType = rdrs.getColumn(j,1).trim();
				String strRelatorType = rdrs.getColumn(j,2).trim();
				_db.debug(D.EBUG_DETAIL, "8206 answers:" + strVEName + ":" + strEntityType + ":" + strRelatorType);
				putData(new VELockERItem(_prof, strVEName, strEntityType, strRelatorType));
			}
		} catch (RuntimeException rx) {
			_db.debug(D.EBUG_ERR, "RuntimeException trapped at: " + strMethod + " " + rx);
			StringWriter writer = new StringWriter();
			rx.printStackTrace(new PrintWriter(writer));
			String x = writer.toString();
			_db.debug(D.EBUG_ERR, "" + x);
			throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
		} finally {
			// Free any statement
			_db.freeStatement();
			_db.isPending();

			// DO NOT FREE THE CONNECTION
			_db.debug(D.EBUG_DETAIL, strMethod + " complete");
		}
	}

	public boolean test(String _strLockOwner, String _strEntityType, String _strRelatorType) {
		String key = _strLockOwner + _strEntityType + _strRelatorType;
		Object o = getData(key);
		if (o != null) {
			return true;
		} else {
			return false;
		}
	}

	public String getVersion() {
		return "$Id: VELockERList.java,v 1.11 2009/05/15 17:46:52 wendy Exp $";
	}

	public String dump(boolean _bBrief) {
		StringBuffer strbResult = new StringBuffer();
		strbResult.append("VELockERList:" + getKey()) ;
		if (!_bBrief) {
			for (int i=0; i < getDataCount(); i++) {
				VELockERItem ve = (VELockERItem)getData(i);
				strbResult.append(ve.dump(false));
			}
		}
		return strbResult.toString();
	}

}
