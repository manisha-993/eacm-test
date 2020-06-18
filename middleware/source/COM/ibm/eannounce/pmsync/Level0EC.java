//
//$Log
//
package COM.ibm.eannounce.pmsync;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Vector;

import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;

/**
 * The Level0EC class represents EC numbers from ProductManager (PM)
 * that affect Level 0 part numbers in OPICM. This class contains
 * the attributes EC number, Engineering location, the timestamp
 * of when the data was fetched from PM, and a list of Level 0
 * part numbers affected by the EC.
 *
 * @author Tom Nelson
 * @version 1.0
 */

public class Level0EC {
   public String m_strEcNumber;
   public String m_strEngLoc;
   public Timestamp m_tsFetchTime;
   public Vector m_vctBomList = new Vector();


   /**
    * This constructor sets the EC number to the value passed
    * in and then retrieves the remaining attribute values
    * using the database connection parameter. A Level0BOM object
    * is created for each row fetched from the database.
    *
    * @param con	PM database connection
    * @param m_strEcNumber	Engineering Change (EC) number
    */
   public Level0EC(Connection _con, String _strEcNumber) {

     m_strEcNumber = _strEcNumber;

     try {
       // Create and execute the SQL statement to fetch the EC attributes
       // and Level 0 part numbers affected.

       System.out.println("Querying for Level 0 items on EC " + m_strEcNumber);

       StringBuffer query = new StringBuffer ("select rtrim(substr(d.itm_id,1,12)),rtrim(substr(d.itm_sh_des,1,25)),b.des_seqnum,substr(c.locid,1,4),CURRENT TIMESTAMP from pm.avntec a, pm.avntecai b, pm.avntlocatn c, pm.avntitem d where a.ec_mc_id='" + m_strEcNumber + "' and a.int_ec_mfiaff = 1 and b.oid_ec=a.objid and c.objid=a.oid_engloc and d.oid_mitem=b.oid_mitem and (d.itm_id like '20000____CTO%' or d.itm_id like '20000____REV%') and d.insertseq= (select max(insertseq) from pm.avntitem where itm_id = d.itm_id and insertseq between 300000000 and b.des_seqnum) order by 1");
       Statement stmt = _con.createStatement();
       ResultSet rs = stmt.executeQuery(query.toString());
       ReturnDataResultSet rdrs = new ReturnDataResultSet(rs);

       rs.close();
       stmt.close();
       rs = null;
       stmt = null;

       // Fetch results
       for(int iRow = 0; iRow < rdrs.getRowCount(); iRow++) {
           if (iRow == 0) {
               m_strEngLoc = rdrs.getColumn(iRow,3);
               String strFetchTime = rdrs.getColumn(iRow,4);
               m_tsFetchTime = Timestamp.valueOf(strFetchTime);
           }
           m_vctBomList.addElement(new Level0BOM(_con,rdrs.getColumn(iRow,0),rdrs.getColumn(iRow,1),rdrs.getColumnInt(iRow,2)));
       }
       m_vctBomList.trimToSize();
     }
     catch (SQLException se) {
       // Display any SQL errors
       System.out.println("SQL Exception: " + se.getMessage());
       se.printStackTrace(System.out);
     }
   } // Level0EC constructor


/**
 * Dummy Constructor Level0EC.
 * @param string
 */
public Level0EC(String string) {
	// Do nothing
}


   /**
    * This method is used to check whether any Level 0 BOMs
    * exist.
    *
    * @return true if at least 1 Level 0 BOM exists, false otherwise
    */
   public boolean level0BOMExists() {
     if (m_vctBomList.size() == 0) {
       return false;
     }
     else {
       return true;
     }
   } // level0BOMExists method


   /**
    * This method is used to destroy the Level0BOM objects.
    */
   public void destroy() {
     int index;

     for (index=0; index < m_vctBomList.size(); index++) {
       ((Level0BOM)m_vctBomList.elementAt(index)).destroy();
     }

     m_vctBomList = null;
   } // destroy method


   /**
    * This method is used for debugging purposes and when called
    * will print the contents of the Level0EC object.
    */
   public void print() {
     int index;

     System.out.println("Level0 EC: m_strEcNumber=" + m_strEcNumber + " m_strEngLoc=" + m_strEngLoc + " m_tsFetchTime=" + m_tsFetchTime.toString());

     for (index=0; index < m_vctBomList.size(); index++) {
       ((Level0BOM)m_vctBomList.elementAt(index)).print();
     }
   } // print method


} // Level0EC class
