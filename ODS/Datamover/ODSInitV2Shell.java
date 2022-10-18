//
// Licensed Materials -- Property of IBM
//
// (C) Copyright International Business Machines Corporation. 1998,2006
//    All Rights Reserved.
//
/*
    Author              Created     LastMod     Summary
    ----------------    ---------   ----------  ------------------------------------------------------------------
    David Bigelow       02/22/99    03/01/99    Clean up and Molularization
    DWB                             05.16.99    Added Proper Keys and INDEXING to all the table Creates
    Bala                            2/14/00     Changed to V2 MetaData
    DWB                             4/01/00     Modularized so user can request DMINIT funciton from command line
    Bala                            2/26/2001   Changed MetaFlagvalue (V2) to MetaDescriptor initialize

*/
// $Log: ODSInitV2Shell.java,v $
// Revision 1.20  2015/06/10 12:38:53  jilichao
// update DB2 jdbc version to type4
//
// Revision 1.19  2007/01/30 22:25:48  bala
// close statement only after creating md
//
// Revision 1.18  2006/04/03 18:17:17  bala
// CR0403063729 - Change flag Init: Create indices after all inserts are complete for performance
//
// Revision 1.17  2006/01/27 18:10:20  bala
// Changes to copyright notice
//
// Revision 1.16  2005/02/25 18:52:35  bala
// Jtest changes
//
// Revision 1.15  2002/04/05 18:02:06  bala
// Flag Query changes....additional 'AND' clause to f.entitytype
//
// Revision 1.14  2002/01/02 23:13:25  bala
// Changed Flag scan query to include the Unique and status
// flags of linkvalue 'A' in the metalinkattr table
//
// Revision 1.13  2001/12/13 22:08:02  bala
// changed longtext selection criteria to select 'L' and 'X' type
//
// Revision 1.12  2001/12/05 19:47:12  bala
// changed flag query to move multiflags only
//
// Revision 1.11  2001/10/23 22:01:06  bala
// changed column sizes in Flag and metaflagvalue
//
// Revision 1.10  2001/09/06 21:27:02  bala
// typo
//
// Revision 1.9  2001/09/06 21:08:44  bala
// Added SHORTDESCRIPTION column
//
// Revision 1.8  2001/08/31 22:24:49  bala
// Moving all flags across...including Unique value flags
//
// Revision 1.7  2001/06/28 17:39:17  bala
// removed reference to attribute type for blob inserts
//
// Revision 1.6  2001/06/28 17:30:23  bala
// removed the column attributetype from blob table definitiion
//
// Revision 1.5  2001/06/28 17:10:26  bala
// Changed scan query of blob table to get the attributecode
//
// Revision 1.4  2001/03/29 18:14:35  bala
// Changed log comments for flag initialize
//
// Revision 1.3  2001/03/29 17:31:20  bala
// changed ODS Metadescription to Metaflagvalue. Now this will
// move data from Metadescription to Metaflagvalue
//
// Revision 1.2  2001/03/27 20:38:32  bala
// added log Keyword
//

import java.util.Date;

import com.ibm.eacm.AES256Utils;

import java.text.*;
import java.sql.*;

/** ODSInitV2Shell
 * @author Balagopal
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ODSInitV2Shell {

  /**
   * Automatically generated constructor for utility class
   */
  private ODSInitV2Shell() {
  }

  /** main
   * @param argv
   */
  public static void main(String argv[]) {

    System.out.println("Starting Tranform Prototype"); // Command
    ODSInitV2 getData = new ODSInitV2(
       argv[0], // DSN - OPICM
       argv[1], // UID - OPICM
       argv[2], // PWD - OPICM
       argv[3], // SCH - OPICM
       argv[4], // DSN - ODS
       argv[5], // UID - ODS
       argv[6], // PWD - ODS
       argv[7], // SCH - ODS
       argv[8], // Enterprise
       argv[9]);// Command


  }

} //End class ODSInitV2Shell

// -------------------------------------------------------------------------------------------------
//      This is the guts of the routine.
//      This creates all the tables and fills them with all of the latest data from OPICM.
//
//      A subsequent routine needs to be developed to just process the Mods between any two
//      Intervals.
//
//      This subsequent routine will also perform alters to existing tables to add columns
//      As new meta data is created....
//
// ------------------------------------------------------------------------------------------------------

class ODSInitV2 {
  /** Automatically generated javadoc for: COMMITCOUNT */
  private static final int COMMITCOUNT = 500;
  private final static int TWENTY = 20;
  /**
          * Automatically generated javadoc for: NINETEEN
          */
  private final static int NINETEEN = 19;
  /**
          * Automatically generated javadoc for: EIGHTEEN
          */
  private final static int EIGHTEEN = 18;
  /**
          * Automatically generated javadoc for: SEVENTEEN
          */
  private final static int SEVENTEEN = 17;
  /**
          * Automatically generated javadoc for: SIXTEEN
          */
  private final static int SIXTEEN = 16;
  /**
          * Automatically generated javadoc for: FIFTEEN
          */
  private final static int FIFTEEN = 15;
  /**
          * Automatically generated javadoc for: FOURTEEN
          */
  private final static int FOURTEEN = 14;
  /**
          * Automatically generated javadoc for: THIRTEEN
          */
  private final static int THIRTEEN = 13;
  /**
          * Automatically generated javadoc for: TWELVE
          */
  private final static int TWELVE = 12;
  /**
          * Automatically generated javadoc for: ELEVEN
          */
  private final static int ELEVEN = 11;

  /** Automatically generated javadoc for: LOOPCOUNT */
  private static final int LOOPCOUNT = 5000;

  private String uidOPICM = "";
  private String pwdOPICM = "";
  private String dsnOPICM = "";
  private String schOPICM = "";
  private String urlOPICM = "";

  private String uidODS = "";
  private String pwdODS = "";
  private String dsnODS = "";
  private String schODS = "";
  private String urlODS = "";

  private String enterprise = "";
  private String cmd = "";

  private // Here are the connection variables

  Connection conODS = null;
  private Connection conOPICM = null;

  // Here are the query selection constants

  private int OPICM_TABLE_BUILDER_QRY = 0;

  private int OPICM_SCAN_RELATOR_TABLE_QRY = 1;
  private final int ODS_DROP_RELATOR_TABLE_QRY = 2;
  private final int ODS_CREATE_RELATOR_TABLE_QRY = 3;
  private final int ODS_PQ_INSERT_RELATOR_TABLE_QRY = 4;

  // Flag query constants
  private final int OPICM_SCAN_FLAG_TABLE_QRY = 5;
  private final int ODS_DROP_FLAG_TABLE_QRY = 6;
  private final int ODS_CREATE_FLAG_TABLE_QRY = 7;
  private final int ODS_PQ_INSERT_FLAG_TABLE_QRY = 8;

  // Long text query constants
  private final int ODS_DROP_LONGTEXT_TABLE_QRY = 9;
  private final int OPICM_SCAN_LONGTEXT_TABLE_QRY = 10;
  private final int ODS_CREATE_LONGTEXT_TABLE_QRY = ELEVEN;
  private final int ODS_PQ_INSERT_LONGTEXT_TABLE_QRY = TWELVE;

  // Long text query constants
  private final int ODS_DROP_BLOB_TABLE_QRY = THIRTEEN;
  private final int OPICM_SCAN_BLOB_TABLE_QRY = FOURTEEN;
  private final int ODS_CREATE_BLOB_TABLE_QRY = FIFTEEN;
  private final int ODS_PQ_INSERT_BLOB_TABLE_QRY = SIXTEEN;

  //MetaDescriptor query constants                            //Changed Monday, February 26, 2001
  private final int ODS_DROP_MD_TABLE_QRY = SEVENTEEN;
  private final int OPICM_SCAN_MD_TABLE_QRY = EIGHTEEN;
  private final int ODS_CREATE_MD_TABLE_QRY = NINETEEN;
  private final int ODS_PQ_INSERT_MD_TABLE_QRY = TWENTY;

  private // System control dates.

  SimpleDateFormat formatTS = new SimpleDateFormat("yyyy-MM-dd");
  //Converting todays date into string
  private String todaystr = getFormatTS().format(new Date());
  private String myunow = getTodaystr() + "-00.00.00.000000";
  private String mynow = getTodaystr() + "-23.59.59.000000";
  private String forever = "9999-12-31-00.00.00.000000";
  private String epic = "1980-01-01-00.00.00.000000";

  // Main contructor
  /**
   * @param opicmdsn
   * @param opicmuid
   * @param opicmpwd
   * @param opicmschema
   * @param odsdsn
   * @param odsuid
   * @param odspwd
   * @param odsschema
   */
  ODSInitV2(
    String opicmdsn,
    String opicmuid,
    String opicmpwd,
    String opicmschema,
    String odsdsn,
    String odsuid,
    String odspwd,
    String odsschema,
    String _enterprise,
    String _cmd) {
    //Initialize the class variables for the run

    // OPICM
    setDsnOPICM(opicmdsn);
    setPwdOPICM(opicmpwd);
    setUidOPICM(opicmuid);
    setUrlOPICM("jdbc:db2:" + opicmdsn);
    setSchOPICM(opicmschema);

    // ods
    setDsnODS(odsdsn);
    setPwdODS(odspwd);
    setUidODS(odsuid);
    setSchODS(odsschema);

    setUrlODS("jdbc:db2:" + odsdsn);

    setEnterprise(_enterprise);
    setCmd(_cmd);

    // Here is the control statement based upon what the user passed as a command

    try {

      Class.forName("COM.ibm.db2.jdbc.app.DB2Driver").newInstance();
      setConOPICM(
        DriverManager.getConnection(
          getUrlOPICM(),
          getUidOPICM(),
         AES256Utils.decrypt(getPwdOPICM())));
      setConODS(
       DriverManager.getConnection(getUrlODS(), getUidODS(), AES256Utils.decrypt( getPwdODS())));
      getConOPICM().setAutoCommit(true);
      getConODS().setAutoCommit(true);
    } catch (Exception ex) {
      System.err.println(new Date() + ":connection:err: " + ex.getMessage());
      System.exit(-1);
    }

    mainline();

  }

  /*  mainline
  **  -------------------------------------------------------------------------------------------
  **  This routine builds all the tables and fills them with all the data for the given run date.
  **  -------------------------------------------------------------------------------------------*/
  private void mainline() {
    System.out.println("Initializing for Enterprise" + getEnterprise());

    try {

      // Relators
      if (getCmd().compareTo("relator") == 0) {
        System.out.println(
          new Date() + ":dminitV2:relators were requested.  Starting...");
        genRelatorTable();
        System.out.println(
          new Date() + ":dminitV2:relators were requested.  Wrapping Up...");
      }
      // Flags
      else if (getCmd().compareTo("flag") == 0) {
        System.out.println(
          new Date() + ":dminitV2:Flags were requested.  Starting...");
        genFlagTable();
        System.out.println(
          new Date() + ":dminitV2:Flags were requested.  Wrapping Up...");
      }
      // Long Text
      else if (getCmd().compareTo("longtext") == 0) {
        System.out.println(
          new Date() + ":dminitV2:LongText was requested.  Starting...");
        genLongTextTable();
        System.out.println(
          new Date() + ":dminitV2:LongText was requested.  Wrapping Up...");
      }
      // Blob
      else if (getCmd().compareTo("blob") == 0) {
        System.out.println(
          new Date() + ":dminitV2:Blobs were requested.  Starting...");
        genBlobTable();
        System.out.println(
          new Date() + ":dminitV2:Blobs were requested.  Wrapping Up...");
      }
      // Metadescription
      else if (getCmd().compareTo("md") == 0) {
        System.out.println(
          new Date()
            + ":dminitV2:Metadescription were requested.  Starting...");
        genMDTable();
        System.out.println(
          new Date()
            + ":dminitV2:Metadescription were requested.  Wrapping Up...");
      }
    } catch (SQLException ex) {
      System.err.println(new Date() + ":general-error" + ex.getMessage());
      ex.printStackTrace();
    }
  }

  /*  genRelatorTable
  **  -------------------------------------------------------------------------------------------
  **  This routine renbuilds the ODS relator table and populates it with all active data from
  **  the target enterprise
  **  -------------------------------------------------------------------------------------------
  */
  private void genRelatorTable() throws SQLException {

    Statement ddlstmt = getConODS().createStatement();
    System.out.println(new Date() + ":genRelatorTable:Start.");

    try {

      ddlstmt.executeUpdate(getQueryStatement(ODS_DROP_RELATOR_TABLE_QRY));
      System.out.println(new Date() + ":genRelatorTable:Dropped old table.");
      getConODS().commit();

    } catch (SQLException ex) {
      ex.printStackTrace();
      System.out.println(
        new Date()
          + ":genRelatorTable:Skipping Drop, Relator Table Not Found.");
    }

    try {
      ddlstmt.executeUpdate(getQueryStatement(ODS_CREATE_RELATOR_TABLE_QRY));
      System.out.println(new Date() + ":genRelatorTable:Created new table.");
      popRelatorTable();
      ddlstmt.executeUpdate(
        "CREATE INDEX "
          + getSchODS()
          + ".R1 ON "
          + getSchODS()
          + ".RELATOR (ENTITY1ID,ENTITYTYPE) ");
      ddlstmt.executeUpdate(
        "CREATE INDEX "
          + getSchODS()
          + ".R2 ON "
          + getSchODS()
          + ".RELATOR (ENTITY2ID, ENTITYTYPE) ");

      System.out.println(
        new Date()
          + ":genRelatorTable:Created R1,R2,R3,R4 indices on the relator table.");
    } catch (SQLException ex) {
      ex.printStackTrace();
      System.out.println(
        new Date() + ":genRelatorTable:Problem in Creating relator table.");
    } finally {
      // Close everything out ...

      ddlstmt.close();

    }

    System.out.println("CREATERELATORTABLE:" + new Date() + ":FINISH");

  }

  /*  genFlagTable
  **  -------------------------------------------------------------------------------------------
  **  This routine renbuilds the ODS flag table and populates it with all active data from
  **  the target enterprise
  **  -------------------------------------------------------------------------------------------
  */
  private void genFlagTable() throws SQLException {

    Statement ddlstmt = getConODS().createStatement();

    System.out.println(new Date() + ":genFlagTable:Start.");

    try {

      ddlstmt.executeUpdate(getQueryStatement(ODS_DROP_FLAG_TABLE_QRY));
      System.out.println(new Date() + ":genFlagTable:Dropped old table.");
      getConODS().commit();

    } catch (SQLException ex) {
      ex.printStackTrace();
      System.out.println(
        new Date() + ":genFlagTable:Skipping Drop, Table Not Found.");
    }

    try {
      ddlstmt.executeUpdate(getQueryStatement(ODS_CREATE_FLAG_TABLE_QRY));
      System.out.println(new Date() + ":genFlagTable:Created new table.");
      popFlagTable();

      ddlstmt.executeUpdate(
        "ALTER TABLE "+ getSchODS()
      + ".FLAG ADD PRIMARY KEY(ENTITYID,ENTITYTYPE,ATTRIBUTECODE,ATTRIBUTEVALUE)");
      System.out.println(new Date() + ":genFlagTable:Created Primary Key Index");

      ddlstmt.executeUpdate(
        "CREATE INDEX "
          + getSchODS()
          + ".F1 ON "
          + getSchODS()
          + ".FLAG (ENTITYID,ENTITYTYPE,ATTRIBUTECODE,VALFROM)");
          System.out.println(new Date() + ":genFlagTable:Created F1 Key Index");

      ddlstmt.executeUpdate(
        "CREATE INDEX "
          + getSchODS()
          + ".F2 ON "
          + getSchODS()
          + ".FLAG (ENTITYID,ENTITYTYPE,VALFROM)");

      System.out.println(
        new Date() + ":genFlagTable:Created PK,F1,F2 indices on the Flag table.");
    } catch (SQLException ex) {
      ex.printStackTrace();
      System.out.println(
        new Date() + ":genFlagTable:Problem creating flag table.");
    } finally {
      // Close everything out ...

      ddlstmt.close();
    }

    System.out.println(new Date() + ":genFlagTable:Fini.");

  }

  /*  genBlobTable
  **  -------------------------------------------------------------------------------------------
  **  This routine renbuilds the ODS flag table and populates it with all active data from
  **  the target enterprise
  **  -------------------------------------------------------------------------------------------
  */
  private void genBlobTable() throws SQLException {

    Statement ddlstmt = getConODS().createStatement();
    System.out.println(new Date() + ":genBlobTable:Start.");

    try {

      ddlstmt.executeUpdate(getQueryStatement(ODS_DROP_BLOB_TABLE_QRY));
      System.out.println(new Date() + ":genBlobTable:Dropped old table.");
      getConODS().commit();

    } catch (SQLException ex) {
            ex.printStackTrace();
      System.out.println(
        new Date() + ":genBlobTable:Skipping Drop, Table Not Found.");
    }
    try {
      ddlstmt.executeUpdate(getQueryStatement(ODS_CREATE_BLOB_TABLE_QRY));
      System.out.println(new Date() + ":genBlobTable:Created new table.");
      popBlobTable();

      ddlstmt.executeUpdate(
        "CREATE INDEX "
          + getSchODS()
          + ".B1 ON "
          + getSchODS()
          + ".BLOB (ENTITYID, ENTITYTYPE,ATTRIBUTECODE,NLSID,VALFROM)");
      ddlstmt.executeUpdate(
        "CREATE INDEX "
          + getSchODS()
          + ".B2 ON "
          + getSchODS()
          + ".BLOB (ENTITYID, ENTITYTYPE,NLSID,VALFROM)");
      ddlstmt.executeUpdate(
        "CREATE INDEX "
          + getSchODS()
          + ".B3 ON "
          + getSchODS()
          + ".BLOB (ENTITYID, ENTITYTYPE,VALFROM)");

      System.out.println(
        new Date()
          + ":genBlobTable:Created B1,B2,B3 indices on the Blob table.");

    } catch (SQLException ex) {
      ex.printStackTrace();
      System.out.println(
        new Date() + ":genBlobTable:Problem creating blob table.");
    } finally {
      // Close everything out ...

      ddlstmt.close();
    }
    System.out.println(new Date() + ":genBlobTable:Fini.");

  }

  /*  genLongTextTable
  **  -------------------------------------------------------------------------------------------
  **  This routine renbuilds the ODS flag table and populates it with all active data from
  **  the target enterprise
  **  -------------------------------------------------------------------------------------------
  */
  private void genLongTextTable() throws SQLException {

    Statement ddlstmt = getConODS().createStatement();
    System.out.println(new Date() + ":genLongTextTable:Start.");

    try {

      ddlstmt.executeUpdate(getQueryStatement(ODS_DROP_LONGTEXT_TABLE_QRY));
      System.out.println(new Date() + ":genLongTextTable:Dropped old table.");
      getConODS().commit();

    } catch (SQLException ex) {
      ex.printStackTrace();
      System.out.println(
        new Date() + ":genLongTextTable:Skipping Drop, Table Not Found.");
    }
    try {
      ddlstmt.executeUpdate(getQueryStatement(ODS_CREATE_LONGTEXT_TABLE_QRY));
      System.out.println(new Date() + ":genLongTextTable:Created new table.");
      popLongTextTable();

      ddlstmt.executeUpdate(
        "CREATE INDEX "
          + getSchODS()
          + ".LT1 ON "
          + getSchODS()
          + ".LONGTEXT (ENTITYID,ENTITYTYPE,ATTRIBUTECODE,NLSID, VALFROM)");
      ddlstmt.executeUpdate(
        "CREATE INDEX "
          + getSchODS()
          + ".LT2 ON "
          + getSchODS()
          + ".LONGTEXT (ENTITYID,ENTITYTYPE,NLSID, VALFROM)");
      ddlstmt.executeUpdate(
        "CREATE INDEX "
          + getSchODS()
          + ".LT3 ON "
          + getSchODS()
          + ".LONGTEXT (ENTITYID,ENTITYTYPE, VALFROM)");
      System.out.println(
        new Date()
          + ":genLongTextTable:Created LT1, LT2, LT3 indices on the Long Texttable.");
    } catch (SQLException ex) {
      ex.printStackTrace();
      System.out.println(
        new Date() + ":genLongTextTable:Problem creating LongText Table.");
    } finally {

      // Close everything out ...

      ddlstmt.close();
    }

    System.out.println(new Date() + ":genLongTextTable:Fini.");

  }
  /*  genMDTable
  **  -------------------------------------------------------------------------------------------
  **  This routine renbuilds the ODS flag table and populates it with all active data from
  **  the target enterprise
  **  -------------------------------------------------------------------------------------------
  */
  private void genMDTable() throws SQLException {


    Statement ddlstmt = getConODS().createStatement();
        System.out.println(new Date() + ":genMDTable:Start.");

    try {

      ddlstmt.executeUpdate(getQueryStatement(ODS_DROP_MD_TABLE_QRY));
      System.out.println(new Date() + ":genMDTable:Dropped old table.");
      getConODS().commit();

    } catch (SQLException ex) {
            System.err.println(ex.getMessage());
      System.out.println(
        new Date() + ":genMDTable:Skipping Drop, Table Not Found.");
    }

    try {
    ddlstmt.executeUpdate(getQueryStatement(ODS_CREATE_MD_TABLE_QRY));
    System.out.println(
      new Date() + ":genMDTable:Created new MetaFlagValue table.");
    popMDTable();
  } catch (SQLException ex) {
          System.err.println(ex.getMessage());
    System.out.println(
      new Date() + ":genMDTable:Problem in creation");
  }
    finally {
                    try {
                            // Close everything out ...

                            ddlstmt.close();

                    } catch (SQLException ex1){
                            ex1.printStackTrace();
                    }
    }

    System.out.println(new Date() + ":genMDTable:Fini.");

  }

  /**
   *   popFlagTable
  **  -------------------------------------------------------------------------------------------
  **  This routine populates all the flag data with us-english multivalued flag data from the
  **  the source enterprise
  **  -------------------------------------------------------------------------------------------
  */
  public void popFlagTable() {

    int count = 0;
    int errcount = 0;
    int reploop = 1;
        PreparedStatement insertrecord = null;
        ResultSet rs = null;
        PreparedStatement getFlag = null;
    System.out.println(new Date() + ":popFlagTable:Start.");

    try {

      getConODS().setAutoCommit(false);

      insertrecord = getConODS().prepareStatement(
          getQueryStatement(ODS_PQ_INSERT_FLAG_TABLE_QRY));
      getFlag =
        getConOPICM().prepareStatement(
          getQueryStatement(OPICM_SCAN_FLAG_TABLE_QRY));

      // Set up the OPICM pull first

      System.out.println(new Date() + ":popFlagTable:OPICMFlagQueryBegins.");
      rs = getFlag.executeQuery();
      System.out.println(new Date() + ":popFlagTable:OPICMFlagQueryEnds.");

      while (rs.next()) {
        try {

          insertrecord.setString(1, getMyunow()); // Valfrom
          insertrecord.setString(2, rs.getString(1).trim()); // EntityType
          insertrecord.setInt(3, rs.getInt(2)); // Entityid
          insertrecord.setString(4, rs.getString(3).trim()); // AttributeCode
          insertrecord.setString(5, rs.getString(4).trim()); // AttributeValue
          insertrecord.executeUpdate();

          count++;
          reploop++;
          if (reploop == LOOPCOUNT) {
            System.out.println(new Date() + ":popFlagTable:commit on:" + count);
            getConODS().commit();
            reploop = 1;
          }
        } catch (SQLException ex) {
          errcount++;
          System.err.println(
            new Date()
              + "popFlagTable:SQL Panic, Skipping insert:ET:"
              + rs.getString(1).trim()
              + ":EID:"
              + rs.getInt(2)
              + ":COUNT:"
              + count
              + ":"
              + ex.getMessage());
        }
      }
      getConODS().commit();
      getConODS().setAutoCommit(true);

    } catch (SQLException ex) {
      System.err.println(new Date() + ":popFlagTable:" + ex.getMessage());
      System.exit(1);
    } finally {
                        try {
                                rs.close();
                                getFlag.close();
                                insertrecord.close();

                        } catch (SQLException ex1){
                                ex1.printStackTrace();
                        }
    }

    System.out.println(
      "popFlagTable:"
        + new Date()
        + ":POPULATE:FINISH:INSERT-COUNT:"
        + count
        + ":ERRORS:"
        + errcount);
  }

  /**
   *   popRelatorTable
  **  -------------------------------------------------------------------------------------------
  **  This routine renbuilds the ODS relator table and populates it with all active data from
  **  the target enterprise
  **  -------------------------------------------------------------------------------------------
  */
  public void popRelatorTable() {

    int count = 0;
    int errcount = 0;
    int reploop = 1;
                PreparedStatement insertrecord = null;
                PreparedStatement getRelator = null;
                ResultSet rs = null;
    System.out.println(new Date() + ":popRelatorTable:Start.");

    try {

      getConODS().setAutoCommit(false);

      insertrecord =
        getConODS().prepareStatement(
          getQueryStatement(ODS_PQ_INSERT_RELATOR_TABLE_QRY));
      getRelator =
        getConOPICM().prepareStatement(
          getQueryStatement(getOPICM_SCAN_RELATOR_TABLE_QRY()));

      // Set up the OPICM pull first

      System.out.println(
        new Date() + ":populateRelatorTable:OPICMRelatorQueryBegins.");
      rs = getRelator.executeQuery();
      System.out.println(
        new Date() + ":populateRelatorTable:OPICMRelatorQueryEnds.");

      while (rs.next()) {
        try {

          insertrecord.setString(1, getMyunow()); // Valfrom
          insertrecord.setString(2, rs.getString(1).trim()); // EntityType
          insertrecord.setInt(3, rs.getInt(2)); // Entityid
          insertrecord.setString(4, rs.getString(3).trim()); // Entity1Type
          insertrecord.setInt(5, rs.getInt(4)); // Entity1ID
          insertrecord.setString(6, rs.getString(5).trim()); // Entity2Type
          insertrecord.setInt(7, rs.getInt(6)); // Entity2ID
          insertrecord.executeUpdate();

          count++;
          reploop++;
          if (reploop == LOOPCOUNT) {
            System.out.println(
              new Date() + ":popRelatorTable:commit on:" + count);
            getConODS().commit();
            reploop = 1;
          }
        } catch (SQLException ex) {
          errcount++;
          System.err.println(
            new Date()
              + "popRelatorTable:SQL Panic, Skipping insert:ET:"
              + rs.getString(1).trim()
              + ":EID:"
              + rs.getInt(2)
              + ":COUNT:"
              + count
              + ":"
              + ex.getMessage());
        }
      }
      getConODS().commit();
      getConODS().setAutoCommit(true);

    } catch (SQLException ex) {
      System.err.println(new Date() + ":popRelatorTable:" + ex.getMessage());
      System.exit(1);
    }	finally {
                        try {
                                rs.close();
                                getRelator.close();
                                insertrecord.close();

                        } catch (SQLException ex1){
                                ex1.printStackTrace();
                        }

    }

    System.out.println(
      new Date()
        + ":popRelatorTable:Finished.  INSERT-COUNT:"
        + count
        + ":ERRORS:"
        + errcount);
  }

  /**  pop LongText Table
  **  -------------------------------------------------------------------------------------------
  **  This routine renbuilds the ODS Long Text table and populates it with all active data from
  **  the target enterprise
  **  -------------------------------------------------------------------------------------------
  */
  public void popLongTextTable() {

    int count = 0;
    int errcount = 0;
    int reploop = 1;
                PreparedStatement insertrecord = null;
                PreparedStatement getLongText = null;
                ResultSet rs = null;
    System.out.println(new Date() + ":popLongTextTable:Start.");

    try {

      getConODS().setAutoCommit(false);

      insertrecord =
        getConODS().prepareStatement(
          getQueryStatement(ODS_PQ_INSERT_LONGTEXT_TABLE_QRY));
      getLongText =
        getConOPICM().prepareStatement(
          getQueryStatement(OPICM_SCAN_LONGTEXT_TABLE_QRY));

      // Set up the OPICM pull first

      System.out.println(
        new Date() + ":popLongTextTable:OPICM LongText Query Begins.");
      rs = getLongText.executeQuery();
      System.out.println(
        new Date() + ":popLongTextTable::OPICM LongText Query Ends.");

      while (rs.next()) {
        try {

          insertrecord.setString(1, getMyunow()); // Valfrom
          insertrecord.setString(2, rs.getString(1).trim()); // EntityType
          insertrecord.setInt(3, rs.getInt(2)); // Entityid
          insertrecord.setString(4, rs.getString(3).trim()); // AttributeCode
          insertrecord.setString(5, rs.getString(4).trim()); // AttributeType
          insertrecord.setInt(6, rs.getInt(5)); // NLSID
          insertrecord.setString(7, rs.getString(6).trim()); // Attribute Value
          insertrecord.executeUpdate();

          count++;
          reploop++;
          if (reploop == LOOPCOUNT) {
            System.out.println(
              new Date() + ":popLongTextTable:commit on:" + count);
            getConODS().commit();
            reploop = 1;
          }
        } catch (SQLException ex) {
          errcount++;
          System.err.println(
            new Date()
              + "popLongTextTable:SQL Panic, Skipping insert.  "
              + ":ET:"
              + rs.getString(1).trim()
              + ":EID:"
              + rs.getInt(2)
              + ":AC: "
              + rs.getString(3).trim()
              + ":AT: "
              + rs.getString(4).trim()
              + ":NLS: "
              + rs.getInt(5)
              + ":COUNT:"
              + count
              + ":"
              + ex.getMessage());
        }
      }
      getConODS().commit();
      getConODS().setAutoCommit(true);

    } catch (SQLException ex) {
      System.err.println(new Date() + ":popLongTextTable:" + ex.getMessage());
      System.exit(1);
    } finally {
                        try {
                                rs.close();
                                getLongText.close();
                                insertrecord.close();

                        } catch (SQLException ex1){
                                ex1.printStackTrace();
                        }
    }

    System.out.println(
      new Date()
        + ":popLongTextTable:Finished.  # of Recs Processed:"
        + count
        + "  # number of errors:"
        + errcount);
  }

  /**
   *  popBlobTable
  **  -------------------------------------------------------------------------------------------
  **  This routine renbuilds the ODS Long Text table and populates it with all active data from
  **  the target enterprise
  **  -------------------------------------------------------------------------------------------
  */

  public void popBlobTable() {

    int count = 0;
    int errcount = 0;
    int reploop = 0;
    int blobSize = 0;
        PreparedStatement insertrecord = null;
        PreparedStatement getBlob = null;
        ResultSet rs = null;
        byte[] mybytes = null;
        System.out.println(new Date() + ":popBlobTable:Start.");

    try {

      getConODS().setAutoCommit(false);

      insertrecord =
        getConODS().prepareStatement(
          getQueryStatement(ODS_PQ_INSERT_BLOB_TABLE_QRY));
      getBlob =
        getConOPICM().prepareStatement(
          getQueryStatement(OPICM_SCAN_BLOB_TABLE_QRY));

      // Set up the OPICM pull first

      System.out.println(new Date() + ":popBlobTable:OPICM Blob Query Begins.");
      rs = getBlob.executeQuery();
      System.out.println(new Date() + ":popBlobTable::OPICM Blob Query Ends");

      while (rs.next()) {
        try {
          insertrecord.clearParameters();
          insertrecord.setString(1, getMyunow()); // Valfrom
          insertrecord.setString(2, rs.getString(1).trim()); // EntityType
          insertrecord.setInt(3, rs.getInt(2)); // Entityid
          insertrecord.setString(4, rs.getString(3).trim()); // AttributeCode
          insertrecord.setInt(5, rs.getInt(5)); // NLSID
          insertrecord.setString(6, rs.getString(6).trim()); // Blob extension
          mybytes = rs.getBytes(7); // Blob Value
          blobSize = mybytes.length; //Size of the blob
          insertrecord.setBytes(7, mybytes); //
          insertrecord.executeUpdate();

          count++;
          reploop++;
          if (reploop == COMMITCOUNT) {
            System.out.println(new Date() + ":popBlobTable:commit on:" + count);
            getConODS().commit();
            reploop = 1;
          }
        } catch (SQLException ex) {
          errcount++;
          System.err.println(
            new Date()
              + "popBlobTable:SQL Panic, Skipping insert.  "
              + ":ET:"
              + rs.getString(1).trim()
              + ":EID:"
              + rs.getInt(2)
              + ":AC:"
              + rs.getString(3).trim()
              + ":AT:"
              + rs.getString(4).trim()
              + ":NLS:"
              + rs.getInt(5)
              + ":BEX:"
              + rs.getString(6).trim()
              + ":COUNT:"
              + count
              + ":"
              + ex.getMessage());
        }
      }
      getConODS().commit();
      getConODS().setAutoCommit(true);

    } catch (SQLException ex) {
      System.err.println(new Date() + ":popBlobTable:" + ex.getMessage());
      System.err.print(
        new Date()
          + ":popBlobTable:Error Occurred for :");
      System.err.println("     :BLOBSIZE:"
          + blobSize);
          System.err.println("     :COMMITTED: "
          + count
          + " RECORDS");

      System.exit(1);
    } finally {
                        try {
                                rs.close();
                                getBlob.close();
                                insertrecord.close();

                        } catch (SQLException ex1){
                                ex1.printStackTrace();
                        }

    }

    System.out.println(
      new Date()
        + ":popBlobTable:Finished.  # of Recs Processed:"
        + count
        + "  # number of errors:"
        + errcount);
  }

  /**
   *
      **  -------------------------------------------------------------------------------------------
      **  This routine renbuilds the ODS Long Text table and populates it with all active data from
      **  the target enterprise
      **  -------------------------------------------------------------------------------------------
      */
  public void popMDTable() {

    int count = 0;
    int errcount = 0;
    int reploop = 1;
        PreparedStatement insertrecord = null;
        PreparedStatement getMDvalues = null;
        ResultSet rs = null;
    System.out.println(new Date() + ":popMDTable:Start.");

    try {

      getConODS().setAutoCommit(false);

      insertrecord =
        getConODS().prepareStatement(
          getQueryStatement(ODS_PQ_INSERT_MD_TABLE_QRY));
      getMDvalues =
        getConOPICM().prepareStatement(
          getQueryStatement(OPICM_SCAN_MD_TABLE_QRY));

      // Set up the OPICM pull first

      System.out.println(new Date() + ":popMDTable:OPICM  MD Query Begins.");
      rs = getMDvalues.executeQuery();
      System.out.println(new Date() + ":popMDTable::OPICM MD Query Ends.");

      while (rs.next()) {
        try {

          insertrecord.setString(1, getMyunow()); // Valfrom
          insertrecord.setString(2, rs.getString(2).trim());
          // Description Type
          insertrecord.setInt(3, rs.getInt(5)); // NLSID
          insertrecord.setString(4, rs.getString(3).trim());
          // Descriptionclass
          insertrecord.setString(5, rs.getString(4).trim());
          // Long Description
          insertrecord.setString(6, rs.getString(6).trim());
          // Short Description

          insertrecord.executeUpdate();

          count++;
          reploop++;
          if (reploop == LOOPCOUNT) {
            System.out.println(new Date() + ":popMDTable:commit on:" + count);
            getConODS().commit();
            reploop = 1;
          }
        } catch (SQLException ex) {
          errcount++;
          System.err.println(
            new Date()
              + "popMDTable:SQL Panic, Skipping insert.  "
              + ":DT: "
              + rs.getString(2).trim()
              + ":DC: "
              + rs.getString(3).trim()
              + ":LD: "
              + rs.getString(4).trim()
              + ":SD: "
              + rs.getString(6).trim()
              + ":NLS:"
              + rs.getInt(5)
              + ":COUNT:"
              + count
              + ":"
              + ex.getMessage());
        }
      }
      getConODS().commit();
      getConODS().setAutoCommit(true);

    } catch (SQLException ex) {
      System.err.println(new Date() + ":popMDTable:" + ex.getMessage());
      System.exit(1);
    } finally {
                        try {
                                rs.close();
                                getMDvalues.close();
                                insertrecord.close();

                        } catch (SQLException ex1){
                                ex1.printStackTrace();
                        }

    }

    System.out.println(
      new Date()
        + ":popMDTable:Finished.  # of Recs Processed:"
        + count
        + "  # number of errors:"
        + errcount);
  }

  /*  getQueryStatement
  **  -------------------------------------------------------------------------------------------
  **  This routine passes back SQL statments back to the caller based upon the qn
  **  the target enterprise
  **  -------------------------------------------------------------------------------------------
  */
  private String getQueryStatement(int qn) {

    // DROP THE RELATOR TABLE IN THE OSD
    // ---------------------------------
    if (qn == ODS_DROP_RELATOR_TABLE_QRY) {

      return "DROP TABLE " + getSchODS() + ".RELATOR";

    }

    // DROP FLAG TABLE IN THE ODS
    // ---------------------------------
    else if (qn == ODS_DROP_FLAG_TABLE_QRY) {

      return "DROP TABLE " + getSchODS() + ".FLAG";

    }

    // DROP LONG TEXT TABLE IN THE ODS
    // ---------------------------------
    else if (qn == ODS_DROP_LONGTEXT_TABLE_QRY) {

      return "DROP TABLE " + getSchODS() + ".LONGTEXT";

    }

    // DROP BLOB TABLE IN THE ODS
    // ---------------------------------
    else if (qn == ODS_DROP_BLOB_TABLE_QRY) {

      return "DROP TABLE " + getSchODS() + ".BLOB";

    }

    // DROP METADESCRIPTION TABLE IN THE ODS
    // ---------------------------------
    else if (qn == ODS_DROP_MD_TABLE_QRY) {

      return "DROP TABLE " + getSchODS() + ".METAFLAGVALUE";

    }

    // CREATE RELATOR TABLE IN ODS
    // ---------------------------------
    else if (qn == ODS_CREATE_RELATOR_TABLE_QRY) {

      return "CREATE TABLE "
        + getSchODS()
        + ".RELATOR"
        + " ("
        + "ENTITYTYPE CHAR(32) NOT NULL, "
        + "ENTITYID INTEGER NOT NULL, "
        + "ENTITY1TYPE CHAR (32) NOT NULL, "
        + "ENTITY1ID INTEGER NOT NULL, "
        + "ENTITY2TYPE CHAR (32) NOT NULL, "
        + "ENTITY2ID INTEGER NOT NULL, "
        + "VALFROM TIMESTAMP NOT NULL, "
        + "PRIMARY KEY (ENTITYID,ENTITYTYPE)) ";
      //+
      //"IN TSPACE03 LONG IN LSPACE06 INDEX IN ISPACE04";
    }

    // CREATE FLAG TABLE IN ODS
    // ---------------------------------
    else if (qn == ODS_CREATE_FLAG_TABLE_QRY) {

      return "CREATE TABLE "
        + getSchODS()
        + ".FLAG"
        + " ("
        + "VALFROM TIMESTAMP NOT NULL, "
        + "ENTITYTYPE CHAR(32) NOT NULL, "
        + "ENTITYID INTEGER NOT NULL, "
        + "ATTRIBUTECODE CHAR(32) NOT NULL, "
        + "ATTRIBUTEVALUE CHAR(32) NOT NULL )";
//        + "PRIMARY KEY (ENTITYID,ENTITYTYPE,ATTRIBUTECODE,ATTRIBUTEVALUE)) ";
      // +
      //"IN TSPACE04  INDEX IN ISPACE03";
    }

    // CREATE LONGTEXT TABLE IN ODS
    // ---------------------------------
    else if (qn == ODS_CREATE_LONGTEXT_TABLE_QRY) {

      return "CREATE TABLE "
        + getSchODS()
        + ".LONGTEXT"
        + " ("
        + "VALFROM TIMESTAMP NOT NULL, "
        + "ENTITYTYPE CHAR(32) NOT NULL, "
        + "ENTITYID INTEGER NOT NULL, "
        + "ATTRIBUTECODE CHAR(32) NOT NULL, "
        + "ATTRIBUTETYPE CHAR(8) NOT NULL, "
        + "NLSID INT NOT NULL, "
        + "ATTRIBUTEVALUE LONG VARCHAR, "
        + "PRIMARY KEY (ENTITYID,ENTITYTYPE,ATTRIBUTECODE,NLSID)) ";
      // +
      //"IN TSPACE05 LONG IN LSPACE06 INDEX IN ISPACE06";
    }

    // CREATE METADESCRIPTION TABLE IN ODS
    // ---------------------------------
    else if (qn == ODS_CREATE_MD_TABLE_QRY) {

      return "CREATE TABLE "
        + getSchODS()
        + ".METAFLAGVALUE"
        + " ("
        + "VALFROM TIMESTAMP NOT NULL, "
        + "ATTRIBUTECODE CHAR(32) NOT NULL, "
        + "NLSID INT NOT NULL, "
        + "ATTRIBUTEVALUE CHAR(32) NOT NULL, "
        + "SHORTDESCRIPTION CHAR(64), "
        + "LONGDESCRIPTION CHAR(128), "
        + "PRIMARY KEY (ATTRIBUTECODE,ATTRIBUTEVALUE,NLSID)) ";
      // +
      //"IN TSPACE04  INDEX IN ISPACE06";

    }

    // CREATE BLOB TABLE IN ODS
    // ---------------------------------
    else if (qn == ODS_CREATE_BLOB_TABLE_QRY) {

      return "CREATE TABLE "
        + getSchODS()
        + ".BLOB "
        + " ("
        + "VALFROM TIMESTAMP NOT NULL, "
        + "ENTITYTYPE CHAR(32) NOT NULL, "
        + "ENTITYID INTEGER NOT NULL, "
        + "ATTRIBUTECODE CHAR(32) NOT NULL, "
        + "NLSID INT NOT NULL, "
        + "BLOBEXTENSION CHAR(32) NOT NULL,"
        + "ATTRIBUTEVALUE BLOB(10M) NOT NULL, "
        + "PRIMARY KEY (ENTITYTYPE,ENTITYID,ATTRIBUTECODE,NLSID)) ";
      // +
      //"IN TSPACE02 LONG IN LSPACE06 INDEX IN ISPACE01";
    }

    // RELATOR INSERT INTO THE ODS
    // ---------------------------------
    else if (qn == ODS_PQ_INSERT_RELATOR_TABLE_QRY) {

      return "INSERT INTO "
        + getSchODS()
        + ".RELATOR "
        + " ( "
        + "VALFROM, "
        + "ENTITYTYPE, "
        + "ENTITYID, "
        + "ENTITY1TYPE, "
        + "ENTITY1ID, "
        + "ENTITY2TYPE, "
        + "ENTITY2ID "
        + "  ) "
        + "VALUES (?,?,?,?,?,?,?)";
    }

    // FLAG INSERT INTO THE ODS                             //Changed 7/5/00
    // ---------------------------------
    else if (qn == ODS_PQ_INSERT_FLAG_TABLE_QRY) {

      return "INSERT INTO "
        + getSchODS()
        + ".FLAG "
        + " ( "
        + "VALFROM, "
        + "ENTITYTYPE, "
        + "ENTITYID, "
        + "ATTRIBUTECODE, "
        + "ATTRIBUTEVALUE "
        + "  ) "
        + "VALUES (?,?,?,?,?)";
    }

    // LONG TEXT  INSERT INTO THE ODS
    // ---------------------------------
    else if (qn == ODS_PQ_INSERT_LONGTEXT_TABLE_QRY) {

      return "INSERT INTO "
        + getSchODS()
        + ".LONGTEXT "
        + " ( "
        + "VALFROM, "
        + "ENTITYTYPE, "
        + "ENTITYID, "
        + "ATTRIBUTECODE, "
        + "ATTRIBUTETYPE, "
        + "NLSID, "
        + "ATTRIBUTEVALUE "
        + "  ) "
        + "VALUES (?,?,?,?,?,?,?)";
    }

    // BLOB INSERT INTO THE ODS
    // ---------------------------------
    else if (qn == ODS_PQ_INSERT_BLOB_TABLE_QRY) {

      return "INSERT INTO "
        + getSchODS()
        + ".BLOB "
        + " ( "
        + "VALFROM, "
        + "ENTITYTYPE, "
        + "ENTITYID, "
        + "ATTRIBUTECODE, "
        + "NLSID, "
        + "BLOBEXTENSION, "
        + "ATTRIBUTEVALUE "
        + "  ) "
        + "VALUES (?,?,?,?,?,?,?)";
    }
    // METADESCRIPTION INSERT INTO THE ODS
    // ---------------------------------
    else if (qn == ODS_PQ_INSERT_MD_TABLE_QRY) {
      return "INSERT INTO "
        + getSchODS()
        + ".METAFLAGVALUE "
        + " ( "
        + "VALFROM, "
        + "ATTRIBUTECODE, "
        + "NLSID, "
        + "ATTRIBUTEVALUE, "
        + "LONGDESCRIPTION, "
        + "SHORTDESCRIPTION "
        + "  ) "
        + "VALUES (?,?,?,?,?,?)";

    }

    //  RELATOR QUERY OUT OF OPICM.
    // ---------------------------------
    else if (qn == getOPICM_SCAN_RELATOR_TABLE_QRY()) {

      return "SELECT  "
        + "EntityType,    "
        + "EntityID,      "
        + "Entity1Type,   "
        + "Entity1ID,     "
        + "Entity2Type,   "
        + "Entity2ID      "
        + "FROM "
        + getSchOPICM()
        + ".RELATOR "
        + "WHERE "
        + "Enterprise = '"
        + getEnterprise()
        + "' AND "
        + " NOT (EffFrom = EffTo)  AND "
        + "ValFrom <= '"
        + getMynow()
        + "' AND '"
        + getMynow()
        + "' < ValTo AND "
        + "EffFrom <= '"
        + getMynow()
        + "' AND '"
        + getMynow()
        + "' < EffTo "
        + " FOR READ ONLY";
    }

    //  FLAG QUERY OUT OF OPICM.                        //Changed 7/5/00
    // ---------------------------------
    else if (qn == OPICM_SCAN_FLAG_TABLE_QRY) {

      return "SELECT "
        + "F.ENTITYTYPE, "
        + "F.ENTITYID, "
        + "F.ATTRIBUTECODE,"
        + "F.ATTRIBUTEVALUE "
        + "FROM "
        + getSchOPICM()
        + ".FLAG F "
        + "INNER JOIN "
        + getSchOPICM()
        + ".metaentity MA ON "
        + "MA.ENTERPRISE = F.ENTERPRISE AND "
        + "MA.ENTITYTYPE = F.ATTRIBUTECODE AND "
        + "MA.ENTITYCLASS = 'F' AND "
        + "MA.ValFrom <= '"
        + getMynow()
        + "' AND '"
        + getMynow()
        + "' < MA.ValTo AND "
        + "MA.EffFrom <= '"
        + getMynow()
        + "' AND '"
        + getMynow()
        + "' < MA.EffTo "
        + " WHERE "
        + "F.ENTERPRISE = '"
        + getEnterprise()
        + "' AND "
        + " NOT (F.EffFrom = F.EffTo)  AND "
        + "F.ValFrom <= '"
        + getMynow()
        + "' AND '"
        + getMynow()
        + "' < F.ValTo AND "
        + "F.EffFrom <= '"
        + getMynow()
        + "' AND '"
        + getMynow()
        + "' < F.EffTo  "
        + " UNION "
        + "SELECT "
        + "F.ENTITYTYPE, "
        + "F.ENTITYID, "
        + "F.ATTRIBUTECODE,"
        + "F.ATTRIBUTEVALUE "
        + "FROM "
        + getSchOPICM()
        + ".FLAG F "
        + "INNER JOIN "
        + getSchOPICM()
        + ".metaentity MA ON "
        + "MA.ENTERPRISE = F.ENTERPRISE AND "
        + "MA.ENTITYTYPE = F.ATTRIBUTECODE AND "
        + "MA.ENTITYCLASS IN ('U','S') AND "
        + "MA.ValFrom <= '"
        + getMynow()
        + "' AND '"
        + getMynow()
        + "' < MA.ValTo AND "
        + "MA.EffFrom <= '"
        + getMynow()
        + "' AND '"
        + getMynow()
        + "' < MA.EffTo "
        + "INNER JOIN "
        + getSchOPICM()
        + ".METALINKATTR MLA   ON  "
        + " MLA.ENTERPRISE =  F.ENTERPRISE "
        + " AND MA.ENTITYTYPE=MLA.LINKTYPE2 "
        + " AND MLA.LINKCODE='EntityAttribute' "
        + " AND MLA.LINKTYPE='Entity/Attribute'  "
        + " AND F.ENTITYTYPE=MLA.LINKTYPE1 "
        + " AND MLA.LINKVALUE='A'                "
        + " AND MLA.ValFrom <= '"
        + getMynow()
        + "' AND '"
        + getMynow()
        + "' < MLA.ValTo AND "
        + "MLA.EffFrom <= '"
        + getMynow()
        + "' AND '"
        + getMynow()
        + "' < MLA.EffTo "
        + " WHERE "
        + "F.ENTERPRISE = '"
        + getEnterprise()
        + "' AND "
        + " NOT (F.EffFrom = F.EffTo)  AND "
        + "F.ValFrom <= '"
        + getMynow()
        + "' AND '"
        + getMynow()
        + "' < F.ValTo AND "
        + "F.EffFrom <= '"
        + getMynow()
        + "' AND '"
        + getMynow()
        + "' < F.EffTo  "
        + " FOR READ ONLY";

    }

    //  LONG TEXT QUERY OUT OF OPICM.
    // ---------------------------------
    else if (qn == OPICM_SCAN_LONGTEXT_TABLE_QRY) {

      return "SELECT "
        + "LT.ENTITYTYPE, "
        + "LT.ENTITYID, "
        + "LT.ATTRIBUTECODE,"
        + "MA.ENTITYCLASS AS ATTRIBUTETYPE,"
        + "LT.NLSID, "
        + "LT.ATTRIBUTEVALUE "
        + "FROM "
        + getSchOPICM()
        + ".LONGTEXT LT "
        + "INNER JOIN "
        + getSchOPICM()
        + ".metaentity MA ON "
        + "MA.ENTERPRISE = LT.ENTERPRISE AND "
        + "MA.ENTITYTYPE = LT.ATTRIBUTECODE AND "
        + "MA.ENTITYCLASS in ('L','X') AND "
        + "MA.ValFrom <= '"
        + getMynow()
        + "' AND '"
        + getMynow()
        + "' < MA.ValTo AND "
        + " NOT (MA.EffFrom = MA.EffTo)  AND "
        + "MA.EffFrom <= '"
        + getMynow()
        + "' AND '"
        + getMynow()
        + "' < MA.EffTo "
        + " WHERE "
        + "LT.ENTERPRISE = '"
        + getEnterprise()
        + "' AND "
        + "LT.ValFrom <= '"
        + getMynow()
        + "' AND '"
        + getMynow()
        + "' < LT.ValTo AND "
        + " NOT (LT.EffFrom = LT.EffTo)  AND "
        + "LT.EffFrom <= '"
        + getMynow()
        + "' AND '"
        + getMynow()
        + "' < LT.EffTo  "
        + " FOR READ ONLY";

    }

    //  BLOB  QUERY OUT OF OPICM.
    // ---------------------------------

    else if (qn == OPICM_SCAN_BLOB_TABLE_QRY) {

      return "SELECT "
        + "B.ENTITYTYPE, "
        + "B.ENTITYID, "
        + "B.ATTRIBUTECODE AS ATTRIBUTECODE,"
        + "MA.ENTITYCLASS AS ATTRIBUTETYPE,"
        + "B.NLSID, "
        + "B.BLOBEXTENSION, "
        + "B.ATTRIBUTEVALUE "
        + "FROM "
        + getSchOPICM()
        + ".BLOB B "
        + "INNER JOIN "
        + getSchOPICM()
        + ".metaentity MA ON "
        + "MA.ENTERPRISE = B.ENTERPRISE AND "
        + "MA.ENTITYTYPE = B.ATTRIBUTECODE AND "
        + "MA.ENTITYCLASS IN ('P','D','B') AND "
        + "MA.ValFrom <= '"
        + getMynow()
        + "' AND '"
        + getMynow()
        + "' < MA.ValTo AND "
        + " NOT (MA.EffFrom = MA.EffTo)  AND "
        + "MA.EffFrom <= '"
        + getMynow()
        + "' AND '"
        + getMynow()
        + "' < MA.EffTo "
        + " WHERE "
        + "B.ENTERPRISE = '"
        + getEnterprise()
        + "' AND "
        + "B.ValFrom <= '"
        + getMynow()
        + "' AND '"
        + getMynow()
        + "' < B.ValTo AND "
        + " NOT (B.EffFrom = B.EffTo)  AND "
        + "B.EffFrom <= '"
        + getMynow()
        + "' AND '"
        + getMynow()
        + "' < B.EffTo  "
        + " FOR READ ONLY";

    }
    //  METADESCRIPTION  QUERY OUT OF OPICM.
    // ---------------------------------

    else if (qn == OPICM_SCAN_MD_TABLE_QRY) {

      return "SELECT "
        + "   VALFROM, "
        + "   DESCRIPTIONTYPE, "
        + "   DESCRIPTIONCLASS, "
        + "   LONGDESCRIPTION,  "
        + "   NLSID, "
        + "   SHORTDESCRIPTION "
        + "FROM "
        + "   "
        + getSchOPICM()
        + ".METADESCRIPTION  "
        + "WHERE "
        + "   ENTERPRISE = '"
        + getEnterprise()
        + "' AND "
        + "   ValFrom <= '"
        + getMynow()
        + "'AND  "
        + "   '"
        + getMynow()
        + "' < VALTO     AND "
        + "   EffFrom <= '"
        + getMynow()
        + "'  AND  "
        + "   '"
        + getMynow()
        + "' < EFFTO  "
        + "    FOR READ ONLY ";

    }

    // GIVE UP - YOU HAVE AN ERROR
    // ---------------------------------------------------------
    else {
      return "ERROR";
    }

  } // End of getQueryStatement

  void setUidOPICM(String _uidOPICM) {
    this.uidOPICM = _uidOPICM;
  }

  String getUidOPICM() {
    return uidOPICM;
  }

  void setPwdOPICM(String _pwdOPICM) {
    this.pwdOPICM = _pwdOPICM;
  }

  String getPwdOPICM() {
    return pwdOPICM;
  }

  void setDsnOPICM(String _dsnOPICM) {
    this.dsnOPICM = _dsnOPICM;
  }

  String getDsnOPICM() {
    return dsnOPICM;
  }

  void setSchOPICM(String _schOPICM) {
    this.schOPICM = _schOPICM;
  }

  String getSchOPICM() {
    return schOPICM;
  }

  void setUrlOPICM(String _urlOPICM) {
    this.urlOPICM = _urlOPICM;
  }

  String getUrlOPICM() {
    return urlOPICM;
  }

  void setUidODS(String _uidODS) {
    this.uidODS = _uidODS;
  }

  String getUidODS() {
    return uidODS;
  }

  void setPwdODS(String _pwdODS) {
    this.pwdODS = _pwdODS;
  }

  String getPwdODS() {
    return pwdODS;
  }

  void setDsnODS(String _dsnODS) {
    this.dsnODS = _dsnODS;
  }

  String getDsnODS() {
    return dsnODS;
  }

  void setSchODS(String _schODS) {
    this.schODS = _schODS;
  }

  String getSchODS() {
    return schODS;
  }

  void setUrlODS(String _urlODS) {
    this.urlODS = _urlODS;
  }

  String getUrlODS() {
    return urlODS;
  }

  void setEnterprise(String _enterprise) {
    this.enterprise = _enterprise;
  }

  String getEnterprise() {
    return enterprise;
  }

  void setCmd(String _cmd) {
    this.cmd = _cmd;
  }

  String getCmd() {
    return cmd;
  }

  void setConODS(Connection _conODS) {
    this.conODS = _conODS;
  }

  Connection getConODS() {
    return conODS;
  }

  void setConOPICM(Connection _conOPICM) {
    this.conOPICM = _conOPICM;
  }

  Connection getConOPICM() {
    return conOPICM;
  }

  void setOPICM_TABLE_BUILDER_QRY(int oPICM_TABLE_BUILDER_QRY) {
    OPICM_TABLE_BUILDER_QRY = oPICM_TABLE_BUILDER_QRY;
  }

  int getOPICM_TABLE_BUILDER_QRY() {
    return OPICM_TABLE_BUILDER_QRY;
  }

  void setOPICM_SCAN_RELATOR_TABLE_QRY(int oPICM_SCAN_RELATOR_TABLE_QRY) {
    OPICM_SCAN_RELATOR_TABLE_QRY = oPICM_SCAN_RELATOR_TABLE_QRY;
  }

  int getOPICM_SCAN_RELATOR_TABLE_QRY() {
    return OPICM_SCAN_RELATOR_TABLE_QRY;
  }

  void setFormatTS(SimpleDateFormat _formatTS) {
    this.formatTS = _formatTS;
  }

  SimpleDateFormat getFormatTS() {
    return formatTS;
  }

  void setTodaystr(String _todaystr) {
    this.todaystr = _todaystr;
  }

  String getTodaystr() {
    return todaystr;
  }

  void setMyunow(String _myunow) {
    this.myunow = _myunow;
  }

  String getMyunow() {
    return myunow;
  }

  void setMynow(String _mynow) {
    this.mynow = _mynow;
  }

  String getMynow() {
    return mynow;
  }

  void setForever(String _forever) {
    this.forever = _forever;
  }

  String getForever() {
    return forever;
  }

  void setEpic(String _epic) {
    this.epic = _epic;
  }

  String getEpic() {
    return epic;
  }
}
