/**
 * $Log: PubToValidator.java,v $
 * Revision 1.7  2011/05/05 11:21:32  wendy
 * src from IBMCHINA
 *
 * Revision 1.1.1.1  2007/06/05 02:09:28  jingb
 * no message
 *
 * Revision 1.6  2006/05/01 19:48:47  gregg
 * fix
 *
 * Revision 1.5  2006/05/01 19:48:10  gregg
 * fixes
 *
 * Revision 1.4  2006/05/01 19:46:05  gregg
 * pub to validation item
 *
 * Revision 1.3  2006/04/27 20:11:02  gregg
 * fix
 *
 * Revision 1.2  2006/04/27 20:02:20  gregg
 * allow end date arg
 *
 * Revision 1.1  2006/04/27 19:56:19  gregg
 * initial load
 *
 */

package COM.ibm.eannounce.catalog;

import java.sql.*;

import COM.ibm.opicmpdh.middleware.*;


public class PubToValidator {


	public static final void main(String[] _args) {
        try {

      		Class mclass = PubToValidator.class;
      		Catalog cat = new Catalog();
      		Database db = cat.getCatalogDatabase();
      		Database pdhdb = cat.getPDHDatabase();
      		CatalogInterval cati = new CatalogInterval(mclass, cat);
            //String strStartDate = cati.getStartDate();
            String strEndDate = cati.getEndDate();

            // allow an override for testing...
            if(_args.length > 0) {
				strEndDate = _args[0];
				db.debug(D.EBUG_DETAIL,"End Date found in args:" + strEndDate);
			}

            run(cat,strEndDate);

            //
            // make sure we close this
            //
            cat.close();

        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        catch (MiddlewareException ex) {
            ex.printStackTrace();
        }
	}


	private static final void run(Catalog _cat, String _strEndDate) throws SQLException, MiddlewareException {
        Database db = _cat.getCatalogDatabase();
        ReturnDataResultSet rdrs = null;
        ResultSet rs = null;

		String strSQL = "select " +
  						"  prodentitytype " +
 						" ,prodentityid " +
 						" ,structentitytype " +
						" ,structentityid " +
						" ,featentitytype " +
						" ,featentityid " +
						" ,pubtodate " +
						" ,withdrawdate " +
						" ,nlsid " +
						"   from gbli.prodstruct " +
						" where  " +
						"    valfrom > ? " +
						" and pubtodate < withdrawdate " +
						" and prodentitytype in ('MODEL','LCTO') " +
						" and featentitytype in ('FEATURE','SWFEATURE') ";

		D.ebug(D.EBUG_DETAIL,"PubToValidator, strSQL:" + strSQL);
		PreparedStatement ps = null;

		Connection con = db.getPDHConnection();

		ps = con.prepareStatement(strSQL);

        try {
            ps = con.prepareStatement(strSQL);
            ps.setString(1,_strEndDate);
            rs = ps.executeQuery();

            D.ebug(D.EBUG_DETAIL,"PubToValidator, running with endDate:" + _strEndDate);

			rdrs = new ReturnDataResultSet(rs);

			rs.close();
			rs = null;

			D.ebug(D.EBUG_DETAIL,"PubToValidator, record count:" + rdrs.getRowCount());

			for(int i = 0; i < rdrs.getRowCount(); i++) {

				String strProdEntityType = rdrs.getColumn(i,0);
				int iProdEntityID = rdrs.getColumnInt(i,1);
				String strStructEntityType = rdrs.getColumn(i,2);
				int iStructEntityID = rdrs.getColumnInt(i,3);
				String strFeatEntityType = rdrs.getColumn(i,4);
				int iFeatEntityID = rdrs.getColumnInt(i,5);
				String strPubToDate = rdrs.getColumn(i,6);
				String strWithdrawDate = rdrs.getColumn(i,7);
				int iNLSID = rdrs.getColumnInt(i,8);

				PubToValidationItem ptvi = new PubToValidationItem(strProdEntityType
				                                                  ,iProdEntityID
				                                                  ,strStructEntityType
				                                                  ,iStructEntityID
				                                                  ,strFeatEntityType
				                                                  ,iFeatEntityID
				                                                  ,strPubToDate
				                                                  ,strWithdrawDate
				                                                  ,iNLSID);

				if(!ptvi.validate()) {
					ptvi.notify(db);
				}
				ptvi = null;

				D.ebug(D.EBUG_DETAIL,"PubToValidator, one item:" + ptvi.toString());
			}
		} finally {
			if(rs != null) {
				rs.close();
				rs = null;
			}
			ps.close();
			ps = null;
		}

	}


}
