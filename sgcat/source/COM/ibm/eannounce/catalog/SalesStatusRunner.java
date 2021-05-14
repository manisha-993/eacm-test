package COM.ibm.eannounce.catalog;

import java.sql.*;

import COM.ibm.opicmpdh.middleware.*;


public class SalesStatusRunner {


	public static final void main(String[] _args) {
        try {

      		Class mclass = SalesStatusRunner.class;
      		Catalog cat = new Catalog();
      		Database db = cat.getCatalogDatabase();
      		Database pdhdb = cat.getPDHDatabase();
      		CatalogInterval cati = new CatalogInterval(mclass, cat);
            String strStartDate = cati.getStartDate();
            String strEndDate = cati.getEndDate();

            String[] saTypes = new String[] {"MODEL"
            								,"SWMODEL"
            								,"SEO"
            								,"MODEL FEAT"
            								,"MODEL RPQ"
            								,"FEATURE"
            								,"RPQ"
            								,"SWMODEL"};

            if(_args != null && _args[0].equals("2")) {
                for(int i = 0; i < saTypes.length; i++) {
                    runIDL(cat,saTypes[i],strEndDate);
				}
			} else if(_args != null && _args[0].equals("1")) {
                for(int i = 0; i < saTypes.length; i++) {
                    runNET(cat,saTypes[i],strEndDate);
				}
			} else {
				System.err.println("Please specify an argument.");
			}

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

    public static final void runIDL(Catalog _cat, String _strVarCondType, String _strEndDate) throws SQLException, MiddlewareException {
        run(_cat,_strVarCondType,"1980-01-01-00.00.00.000000",_strEndDate);
	}

	public static final void runNET(Catalog _cat, String _strVarCondType, String _strEndDate) throws SQLException, MiddlewareException {
      Database pdhdb = _cat.getPDHDatabase();
      CatalogInterval cati = new CatalogInterval(SalesStatusRunner.class, _cat);
        String strStartDate = cati.getEndDate();
        run(_cat,_strVarCondType,strStartDate,_strEndDate);
	}

	private static final void run(Catalog _cat, String _strVarCondType, String _strStartDate, String _strEndDate) throws SQLException, MiddlewareException {
        Database db = _cat.getCatalogDatabase();
        ReturnDataResultSet rdrs = null;
        ResultSet rs = null;


		String strSQL = "SELECT VARCONDTYPE,MATNR,LASTUPDATED FROM EACM.SALES_STATUS WHERE VARCONDTYPE = ? AND MATERIALSTATUS = 'ZJ' AND MARKEDFORDELETION = 'N' AND LASTUPDATED >= ? AND LASTUPDATED < ?";
		D.ebug(D.EBUG_DETAIL,"SalesStatusRunner, strSQL:" + strSQL);
		PreparedStatement ps = null;

		Connection con = db.getPDHConnection();

		ps = con.prepareStatement(strSQL);

        try {

D.ebug(D.EBUG_DETAIL,"SalesStatusRunner, setting:" + _strVarCondType + ":" + _strStartDate + ":" + _strEndDate);

            ps = con.prepareStatement(strSQL);
            ps.setString(1,_strVarCondType);
            ps.setString(2,_strStartDate);
            ps.setString(3,_strEndDate);

            rs = ps.executeQuery();



			rdrs = new ReturnDataResultSet(rs);

			rs.close();
			rs = null;

			D.ebug(D.EBUG_DETAIL,"SalesStatusRunner, record count for " + _strVarCondType + "(" + _strStartDate + "):" + rdrs.getRowCount());

			for(int i = 0; i < rdrs.getRowCount(); i++) {

				String s1 = rdrs.getColumn(i,0);
				String s2 = rdrs.getColumn(i,1);
				String s3 = rdrs.getColumn(i,2);

				SalesStatusItem ssi = new SalesStatusItem(s1,s2,s3);

				ssi.update(db, _strEndDate);

				D.ebug(D.EBUG_DETAIL,"SalesStatusRunner, one item:" + ssi.toString());
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
