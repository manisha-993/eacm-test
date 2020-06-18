//
//$Log
//
package COM.ibm.eannounce.pmsync;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;

/**
 * The Level0BOM class represents a Bill of Material (BOM)
 * from ProductManager (PM) that is a Level 0 part number
 * in OPICM. This class contains the attributes item number,
 * m_strDescription, PM design sequence number, and a list of
 * Level 0 components.
 *
 * @author Tom Nelson
 * @version 1.0
 */

public class Level0BOM {

   public String m_strItemNumber;
   public String m_strDescription;
   public int m_strDesignSeqnum;
   public Vector m_vctCompList = new Vector();


   /**
    * This constructor sets the Item number and m_strDescription
    * to the values passed in and then retrieves the remaining
    * attribute values using the database connection parameter.
    *
    * @param con        	PM database connection
    * @param m_strItemNumber 	Item number
    * @param m_strDescription	Item m_strDescription
    * @param m_strDesignSeqnum	PM internal sequence number for versioning
    */
   public Level0BOM(Connection con, String _strItemNumber, String _strDescription, int _strDesignSeqnum) {
     int maxSeq;  // Sequence number upper limit for fetching Item header data

     m_strItemNumber = _strItemNumber;
     m_strDescription = _strDescription;
     m_strDesignSeqnum = _strDesignSeqnum;

     // If the design sequence number is in the Prerelease status
     // range (500000000 - 59999999), then set the upper limit
     // to the max Prerelease value, otherwise set the upper limit
     // to the max Release status value.
     maxSeq = (m_strDesignSeqnum > 499999999) ? 599999999:499999999;

     try {
       // Create and execute the SQL statement to fetch the Level 0 components
       System.out.println("Fetch Level 0 components for " + m_strItemNumber);

       StringBuffer strQuery = new StringBuffer ("select substr(a.cmp_itm_id,1,12),substr(e.itm_name,1,10),substr(a.cmp_desc,1,14) from pm.avntcompnt a, pm.avntecai c, pm.avntitem e where a.insertseq between 300000000 and " + m_strDesignSeqnum + " and a.extractseq > " + m_strDesignSeqnum + " and partitm_id = '" + m_strItemNumber + "' and a.view_orig = '1' and c.oid_mitem = a.oid_parmit and c.des_seqnum = a.insertseq and e.itm_id = a.cmp_itm_id and e.insertseq = (select max(insertseq) from pm.avntitem where itm_id = a.cmp_itm_id and insertseq between 300000000 and " + maxSeq + ") order by 1");

       Statement stmt = con.createStatement();
       ResultSet rs = stmt.executeQuery(strQuery.toString());
       ReturnDataResultSet rdrs = new ReturnDataResultSet(rs);

       rs.close();
       stmt.close();
       rs = null;
       stmt = null;

       for(int iRow = 0; iRow < rdrs.getRowCount(); iRow++) {
         m_vctCompList.addElement(new Level0Comp(rdrs.getColumn(iRow,0),rdrs.getColumn(iRow,1),rdrs.getColumn(iRow,2)));
       }
       m_vctCompList.trimToSize();
       System.out.println("  " + m_vctCompList.size() + " components fetched");

     }
     catch (SQLException se) {
       // Display any SQL errors
       System.out.println("SQL Exception: " + se.getMessage());
       se.printStackTrace(System.out);
     }
   } // Level0BOM constructor


   /**
    * This method is used to check whether any Level 0 Components
    * exist.
    *
    * @return true if at least 1 Level 0 Component exists, false otherwise
    */
   public boolean level0CompExists() {
     if (m_vctCompList.size() == 0) {
       return false;
     }
     else {
       return true;
     }
   } // level0CompExists method


   /**
    * This method is used to destroy the Level0Comp objects
    */
   public void destroy() {
     m_vctCompList = null;
   } // destroy method


   /**
    * This method is used for debugging purposes and when called
    * will print the contents of the Level0BOM object.
    */
   public void print() {
     int index;

     System.out.println("Level0 BOM: m_strItemNumber=" + m_strItemNumber + " m_strDescription=" +
                                     m_strDescription + " m_strDesignSeqnum=" + m_strDesignSeqnum);

     for (index=0; index < m_vctCompList.size(); index++) {
       ((Level0Comp)m_vctCompList.elementAt(index)).print();
     }
   } // print method


} // Level0BOM class
