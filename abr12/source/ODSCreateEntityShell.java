/*
*       ODSCreateEntities.java
*       --------------------
*       Licensed Materials -- Property of IBM
*
*       (c) Copyright International Business Machines Corporation, 2001
*       All Rights Reserved.
*       ----------------------------------------------------------------------
*       This will create all the Entity/Relator-Tables in the ODS if no specific entity/relator is
*       specified or a specific Entity/Relator table if one is specified
*
*       Author      :       Bala Balakrishnan
*
*       Created     :       Wednesday, January 24, 2001
*       Modified    :
*
*/

// $Log: ODSCreateEntityShell.java,v $
// Revision 1.25  2003/10/20 16:56:25  bala
// do not truncate if column width is 254, truncate otherwise
//
// Revision 1.24  2003/05/08 18:36:01  bala
// Added English only capability...this does not truncate column
// values assuming triple byte, also does not expand the column
// size from what is defined in odslength.
// The system detects this from the propertyfile attribute strNLSOds
//
// Revision 1.23  2003/03/06 22:21:19  bala
// changing odsschema property paramter to uppercase
// since SQL keeps schema in uppercase
//
// Revision 1.22  2003/02/28 19:31:36  bala
// Getting 'A'br attributes of an entity - changed the meta query
//
// Revision 1.21  2002/09/06 17:10:22  naomi
// comma is added in the flag table query
//
// Revision 1.20  2002/08/28 17:47:47  bala
// PDH_GET_ALL_ENTITY changed as Entity can have just Flag attributes
// without having Text/LongText/Blob Attributes
//
// Revision 1.19  2002/04/17 18:55:03  bala
// changed comments
//
// Revision 1.18  2002/04/17 18:53:54  bala
// changed to read the property file
// option to continue from an entity
// close statements after insert
//
// Revision 1.17  2002/04/05 18:04:09  bala
// Moved the entityresultset to returndataresult set for performance
//
// Revision 1.16  2002/04/05 18:02:05  bala
// Flag Query changes....additional 'AND' clause to f.entitytype
//
// Revision 1.15  2002/03/18 18:27:34  bala
// increased the AttributeValue column width to 24 to
// incorporate triple byte chars
//
// Revision 1.14  2002/01/15 22:36:40  bala
// changed deletelog structure, added entity1,id, entity2,id columns
//
// Revision 1.13  2002/01/02 22:55:45  bala
// Creating DeleteLog Table during initalize
//
// Revision 1.12  2001/08/31 22:34:59  bala
// removed unecessary comments....
// changed query to join on uppercase attributecode
//
// Revision 1.11  2001/08/31 21:01:33  bala
// Uppercased the attribute code returned from the queries
//
// Revision 1.10  2001/08/29 17:51:48  bala
// Changed 2.3 query which uses Metalinkattribute to Metalinkattr.
// Added one more predicate to the metalinkattribute join
// "Linkcode="EntityAttribute" and Linktype='L'"
//
// Revision 1.9  2001/08/21 19:28:33  bala
// fixed bug
//
// Revision 1.8  2001/08/21 18:44:30  bala
// Added addl command line parm to which will make program create tables only
//
// Revision 1.7  2001/06/26 18:21:44  bala
// Added checking for attribute type 'S' to entitysnapshot query
//
// Revision 1.6  2001/06/05 16:12:52  bala
// Corrected the 'getVersion' method
//
// Revision 1.5  2001/06/01 17:40:55  bala
// Changed entity/attribute build query. Added attribute class 'S' for status
//
// Revision 1.4  2001/04/19 17:31:19  bala
// Changed comments
//
// Revision 1.3  2001/04/16 23:34:25  bala
// Added the ValTo to the ODS entityColumns created and
// the GetVersion method to generate the version automatically
//
// Revision 1.2  2001/03/27 20:38:32  bala
// added log Keyword
//

import java.io.*;
import java.util.*;
import java.util.Date;
import java.lang.*;
import java.lang.String;
import java.lang.Integer;
import java.text.*;
import java.sql.*;
import java.net.*;
import COM.ibm.opicmpdh.middleware.*;

/**
 *  Description of the Class
 *
 *@author     Administrator
 *@created    April 23, 2001
 */
public class ODSCreateEntityShell {
  /**
   *  Description of the Method
   *
   *@param  argv  Description of the Parameter
   */
  public static void main(String argv[]) {
    System.out.println("No. of parameters are :" + argv.length);
    try {
      if (argv.length != 2) {
        System.err.println("Error!! Command Line Parameters required as : \n" +
            "       1. Initialize mode - \"ALL\" for all entities, or EntityType for specific one    \n" +
            "       2. Create Empty Database (NODATA)");

      }

      System.out.println("----------------Starting ODSCreateEntities (OCE)-------------------");
      ODSCreateEntities initODS = new ODSCreateEntities(
          argv[0], // Table Command
      argv[1]//Create Empty db...dont move data
      );

    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

}//End Class ODSCreateEntities

/**
 *  Description of the Class
 *
 *@author     Administrator
 *@created    April 23, 2003
 */
class ODSCreateEntities {

  //  Class wide variables
  private String strEnterprise;
  private String struidPDH;
  private String strpwdPDH;
  private String strNLSOds;
  private String strSchemaPDH;

  private String struidODS;
  private String strpwdODS;
  private String strSchemaODS;
  private String strEntityTableName;

  private String strurlPDH;
  private String strurlODS;

  private String strRunMode;
  private String strNoData;
  private String strInitializeMode;
  // Database Connection

  private Connection conPDH;
  private Connection conODS;

  //******************************************Version No. of this program*********************************
  //Code Version No.
  final static String strODSEntityCreateVersion = "OPICM 1.0";
  //__________________Change this version no. each time you change the code____________________________________

  // Assume a Dry Run ...

  private boolean blnLive = false;

  //Date Time Cast we use to print in the log entries
  private SimpleDateFormat c_sdfTimestamp = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS000");

  // System control dates.
  private String strTimeStampNow = "";
  private String strTimeStampBeginOfDay = "";
  private String strTimeStampEndOfDay = "";

  // PDH symbolic link that equals FOREVER
  private String strTimeStampForever = "9999-12-31-00.00.00.000000";
  private String strTimeStampLastRun = "";

  // Here are the query selection constants
  final static int PDH_GET_META_ENTITIES_ATTRIBUTES = 0;
  final static int PDH_GET_ALL_ENTITY = 1;
  final static int PDH_GET_ALL_TEXT = 2;
  final static int PDH_GET_ALL_UFLAG = 3;
  final static int PDH_GET_ENTITY_SNAPSHOT = 4;
  final static int PDH_GET_COLUMN_SIZE = 5;//Dont need this as column size is part of main query
  final static int ODS_SET_TIMETABLE = 6;
  final static int ODS_CREATE_ENTITY_TABLE = 7;
  final static int ODS_INSERT_ENTITY_ROW = 8;
  final static int ODS_DROP_ENTITY_TABLE = 9;
  final static int ODS_CREATE_TIMETABLE = 10;
  final static int ODS_INSERT_TIMETABLE_ROW = 11;
  final static int ODS_DROP_TIMETABLE = 12;
  final static int ODS_CREATE_ENTITY_INDEX = 13;
  final static int ODS_DROP_DELETELOG = 14;
  final static int ODS_CREATE_DELETELOG = 15;

  //  ODS database table constants
  private int ODS_COLUMN_MULTIPLIER = 3;
  final static int ODS_COLUMN_MAXWIDTH = 254;

  final static String ODS_TEXT_WIDTH = "32";
  final static String ODS_UFLAG_WIDTH = "32";
  final static String ODS_ID_WIDTH = "64";
  final static String ODS_CAT_WIDTH = "32";
  final static String ODS_ATT_WIDTH = "32";
  final static String ODS_ET_WIDTH = "32";
  final static String ODS_PRIMARY_COLUMNS =
      " ENTITYTYPE CHAR(" + ODS_ET_WIDTH + " ) NOT NULL, " +
      " ENTITYID INTEGER NOT NULL, " +
      " NLSID INTEGER NOT NULL,  " +
      " Valfrom TIMESTAMP NOT NULL, " +
      " Valto TIMESTAMP NOT NULL";


  //
  private String strCurrentEntity = null;


  /* ***********************************************************************************
    // Main contructor
    */
  /**
   *  Constructor for the ODSCreateEntities object
   *
   *@param  _strEntityTableName  Description of the Parameter
   *@param  _strNoData           Description of the Parameter
   */
  public ODSCreateEntities(
      String _strEntityTableName
      , String _strNoData
      ) {

    readPropertyFile();
    
    ODS_COLUMN_MULTIPLIER = strNLSOds.equals("ALL") ? 3: 1 ; //Provide for English only stuff
    //Initialize the class variables for the run
    this.strEntityTableName = _strEntityTableName;
    this.strNoData = _strNoData;

    // find out if we are running live or not
    printOK("Current Run Level is :" + getVersion());

    if (strRunMode.equals("LIVE")) {
      this.blnLive = true;
      printOK("We are Live... Updating ODS" + (strNoData.equals("NODATA") ? " Creating empty tables." : " "));
    } else {
      printOK("This is a Dry Run... Check the output log");
    }

    // Set up all the date information ...
    DateFormat df = DateFormat.getDateInstance();
    SimpleDateFormat fmtTimeStamp = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS000");
    SimpleDateFormat fmtDate = new SimpleDateFormat("yyyy-MM-dd");
    String strDateToday = fmtDate.format(new Date());

    df.setCalendar(Calendar.getInstance());
    fmtTimeStamp.setCalendar(Calendar.getInstance());
    fmtDate.setCalendar(Calendar.getInstance());

    this.strTimeStampNow = fmtTimeStamp.format(new Date());
    this.strTimeStampBeginOfDay = strDateToday + "-00.00.00.000000";
    this.strTimeStampEndOfDay = strDateToday + "-23.59.59.999999";
    this.strTimeStampLastRun = "";

    //Register the Driver manager ...
    printOK("Registering the Driver Manager");

    try {

      Class.forName("COM.ibm.db2.jdbc.app.DB2Driver").newInstance();

      //get Connection to the Databases

      conPDH = DriverManager.getConnection(strurlPDH, struidPDH, strpwdPDH);
      conODS = DriverManager.getConnection(strurlODS, struidODS, strpwdODS);

      conPDH.setTransactionIsolation(4);
      conPDH.setAutoCommit(true);

    } catch (Exception ex) {

      printErr("Driver Manager Error!:" + ex.getMessage());
      System.exit(1);
    }

    if (strRunMode.equals("LIVE") &&
        strEntityTableName.equals("ALL")) {
      initializeTimeTable();
      setTimestampInTimetable();
      initializeDeleteLog();
    }


    //Proceeding to create entities and move the data
    //***********************************************
    buildODSTables(buildODSDefinition());

    //Initializing the TimeTable
    //Close everything

    printOK("Closing connections");
    try {
      conPDH.close();
      conODS.close();
    } catch (SQLException e) {
      printErr("Error while closing connections....\n" + e.getMessage());
    }
    printOK("Congratulations!! ODS Update complete!");

  }//End method ODSCreateEntities


  /*-------------------------------------------------------------------------------------
    *   function buildODSDefinition
    *   ------------------------
    *   1) Create the vector list of all the entities and its attributes
    *
    --------------------------------------------------------------------------------------*/
  /**
   *  Description of the Method
   *
   *@return    Description of the Return Value
   */
  private Vector buildODSDefinition() {
    Vector vEntityAttributes = null;
    vEntityAttributes = new Vector();
    boolean bBypassEntities = false;
    String strEntityType = null;

    printOK("Starting Entity Vector Build.");
    if (!strEntityTableName.equals("ALL") && strInitializeMode.equals("CONTINUE")) {
      bBypassEntities = true;
      printOK("buildODSDefinition: Bypassing entities before " + strEntityTableName);
    }

    try {// First ... Lets create a vector of All known Entities and Relators
      // we are suppossed to manage
      PreparedStatement pqMainQuery = conPDH.prepareStatement(getQueryStatement(PDH_GET_META_ENTITIES_ATTRIBUTES));
      ResultSet rsMainQuery = pqMainQuery.executeQuery();
      if (rsMainQuery.next()) {
        vEntityAttributes = new Vector();
        do {
          strEntityType = rsMainQuery.getString(1).trim();
          if (strEntityType.equals(strEntityTableName)) {
            bBypassEntities = false;//Read from this point onwards
          }

          if (!bBypassEntities) {
            vEntityAttributes.addElement(strEntityType);
            vEntityAttributes.addElement(rsMainQuery.getString(2).trim());
            vEntityAttributes.addElement(rsMainQuery.getString(3).trim());
            vEntityAttributes.addElement(rsMainQuery.getString(4).trim());
            vEntityAttributes.addElement(rsMainQuery.getString(5).trim());
            vEntityAttributes.addElement(rsMainQuery.getString(6).trim());
          }
        } while (rsMainQuery.next());
      }


      rsMainQuery.close();
      pqMainQuery.close();

      printOK("Finished Entity Vector Build.");
    } catch (SQLException ex) {
      printErr("buildODSDefinition:" + ex.getMessage());

    } catch (Exception ex) {
      printErr("buildODSDefinition:" + ex.getMessage());
      System.exit(1);

    }

    return vEntityAttributes;
  }//End of Method buildODSDefinition


  /*--------------------------------------------------------------------------------------
    *   buildODSTables
    *   --------------
    *   Using the vector list of entities, tablespaces and its attributes
    *       build the Entity table definition in the ods
    *       Then call the method to build the table
    */
  /**
   *  Description of the Method
   *
   *@param  vODSDefinition  Description of the Parameter
   */
  private void buildODSTables(Vector vODSDefinition) {

    String strLastTableName = null;
    String strAttributeCode = null;
    String strAttributeLen = null;
    int intAttributeLen = 0;
    String strTableSpace = null;
    String strIndexSpace = null;
    String strTableName = null;
    String strAttributeClass = null;
    String strFirstColumns = null;

    String strODSColumnDefinition = "";

    String strLastAttributeCode = "";

    try {

      Enumeration e = vODSDefinition.elements();

      while (e.hasMoreElements()) {

        strTableName = (String) e.nextElement();//Get TableName (Entity type)
        if (strLastTableName == null) {//This is when it is the 1st iteration
          strLastTableName = strTableName;
          strLastAttributeCode = null;

        }

        //Now check whether the tablename has changed.....
        if (!strTableName.equals(strLastTableName)) {
          createODSTable(strLastTableName,
              strODSColumnDefinition,
              strTableSpace,
              strIndexSpace);
          strLastTableName = strTableName;
          strODSColumnDefinition = "";
          strLastAttributeCode = "";

        }

        strAttributeCode = (String) e.nextElement();//Get Attribute Code

        if (strAttributeCode.equals(strLastAttributeCode)) {
          printErr("buildODSTables:ERROR!:DUPLICATE ATTRIBUTE DEFINED IN METAENTITY FOR ENTITY :" +
              strTableName + " ATTRIBUTE " + strLastAttributeCode + "   Exiting OCE!");
          System.exit(1);
        } else {
          strLastAttributeCode = strAttributeCode;
        }

        strAttributeLen = (String) e.nextElement();//Get Attribute Length

        if (strAttributeLen.equals("0")) {
          printErr("buildODSTables:ERROR!:ATTRIBUTE LENGTH NOT DEFINED IN METAATTRIBUTE FOR ENTITY :" +
              strTableName + "ATTRIBUTE:" + strAttributeCode + " :DEFAULTING TO 64 VARCHARS");
          strAttributeLen = "64";
        } else {//Multiply the col size for Multi byte characters

          intAttributeLen = Integer.valueOf(strAttributeLen).intValue();
          if (intAttributeLen * ODS_COLUMN_MULTIPLIER > ODS_COLUMN_MAXWIDTH) {
            intAttributeLen = ODS_COLUMN_MAXWIDTH;
          } else {
            intAttributeLen = intAttributeLen * ODS_COLUMN_MULTIPLIER;
          }

          strAttributeLen = Integer.toString(intAttributeLen);
        }
        strTableSpace = (String) e.nextElement();//Get TableSpace
        strIndexSpace = (String) e.nextElement();//Get Index Space
        strAttributeClass = (String) e.nextElement();//Get Attribute Type
        //Build the column definition here
        strODSColumnDefinition = strODSColumnDefinition + "," +
            strAttributeCode +
            " VARCHAR(" + strAttributeLen + ")";


      }//End of e.hasMoreElements()
      createODSTable(strLastTableName,
          strODSColumnDefinition,
          strTableSpace,
          strIndexSpace);
    } catch (Exception e) {
      printErr("buildODSTables:ERROR:" + e.getMessage());
      printErr(strAttributeCode + "|" + strAttributeLen);
      e.printStackTrace();
    }
  }//End of method buildODSTables


  /*---------------------------------------------------------------------------------------
    *   createODSTable
    *   --------------
    *   This Will create the Table and call the method to move the data across
    *   Parameters
    *   ----------
    *   Table name
    *   String of Columns
    *   Table Space name
    *   Index space name
    *
    */
  /**
   *  Description of the Method
   *
   *@param  strTableName  Description of the Parameter
   *@param  strColumns    Description of the Parameter
   *@param  strTspace     Description of the Parameter
   *@param  strIspace     Description of the Parameter
   */
  private void createODSTable(String strTableName,
      String strColumns,
      String strTspace,
      String strIspace) {
    String strTablestatement = "";

    try {

      printOK("createODSTable:START:TABLE:" + strTableName);
      String primarykey = "PRIMARY KEY (ENTITYID, NLSID)";// Establish the primary key

      DatabaseMetaData dbMeta = conODS.getMetaData();
      String strTableSpace = null;
      String strIndexSpace = null;

      String[] strMetaDB2Token = {"TABLE"};// Look only for tables
      boolean bTableThere = false;// flag to indicate if the table is currently present
      ResultSet rs = dbMeta.getTables(null, strSchemaODS, strTableName, strMetaDB2Token);//  Check for the table ...
      if (rs.next()) {
        bTableThere = true;// We have found it...
      }
      rs.close();

      String strDropStatement = "DROP TABLE " + strSchemaODS + "." + strTableName;

      if (bTableThere) {//Go ahead and drop the table
        try {
          printOK("createTable:DROP:EXECUTE:" + strTableName.trim());
          if (blnLive) {//But drop only if the run is LIVE!!!
            Statement stmtDroptable = conODS.createStatement();
            stmtDroptable.executeUpdate(strDropStatement);
            stmtDroptable.close();
          }
        } catch (SQLException ex) {
          printErr("createODSTable:DROP:SQL:ERROR: " + ex.getMessage());
          printErr("createODSTable:DROPPING ********EXIT OCE*********");
          printOK("createODSTable:DROPPING ********EXIT OCE*********");
          System.exit(1);
        }
      } else {
        printOK("createODSTable:DROP:PASSING" + strTableName.trim());
      }

      String strCreateTableStatement = "CREATE TABLE " + strSchemaODS + "." + strTableName + " (" +
          ODS_PRIMARY_COLUMNS + strColumns + ")" +
          " IN " + strTspace + " INDEX IN " + strIspace;
      try {
        if (blnLive) {//Create Table only if LIVE
          Statement stmtCreatetable = conODS.createStatement();
          stmtCreatetable.executeUpdate(strCreateTableStatement);
          stmtCreatetable.executeUpdate("CREATE UNIQUE INDEX " + strSchemaODS + "." + strTableName + "1 ON " +
              strSchemaODS + "." + strTableName + "(ENTITYID,NLSID )  ");
          stmtCreatetable.close();
        }
      } catch (SQLException ex) {
        printErr("createODSTable:CREATE:SQL:ERROR:: \n" + strCreateTableStatement + ":" + ex.getMessage());
        printErr("createODSTable:CREATING:********EXIT OCE*********");
        printOK("createODSTable:CREATING:********EXIT OCE*********");
        System.exit(1);
      }

    } catch (SQLException e) {
    }
    if (strNoData.equals("NODATA")) {
      printOK("Bypassing datamovement...only table will be created");
    } else {
      //Ok, Table is created. Now move the data!
      //----------------------------------------
      moveDataToODS(strTableName);
    }

  }//End of Method createODSTable


  /*--------------------------------------------------------------------------------------------------
    *   moveDataToODS
    *   -------------
    *   Parameter   : Entitytype
    *   This will run the data queries to get the data for a given entity.
    *   For a Given entity type, entity id and nls id, a row in the ODS Entity table
    *       will be updated
    */
  /**
   *  Description of the Method
   *
   *@param  strEntityType  Description of the Parameter
   */
  private void moveDataToODS(String strEntityType) {

    printOK("Starting Datamovement :" + strEntityType);

    //Get the column and definitions of
    //the ODS table
    Hashtable hTableColDefinitions = new Hashtable();
    try {
      DatabaseMetaData meta = conODS.getMetaData();
      ResultSet colrs = meta.getColumns(null, "%", strEntityType, "%");
      while (colrs.next()) {//putting ("COLUMNNAME",INTSIZE) int hashtable

        printOK("Triple Byte Column Size " + colrs.getString(4).trim() + "|" + colrs.getInt(7));
        hTableColDefinitions.put(colrs.getString(4).trim(), new Integer(colrs.getInt(7)));
      }
    } catch (SQLException e) {
      printErr("moveDataToODS:Error while scanning table " + strEntityType);
      printErr(e.getMessage());
    }

    PreparedStatement psEntityStatement = null;
    ResultSet rsEntities = null;

    //Got the list of active entities and their nls id's
    //Now go construct the attributes and values for
    // each Entityid/NLS id (Snapshot) and update a row

    int intEntityId = 0;
    int intNLSid = 0;
    int intRecProcessed = 0;
    int intCommitCount = 0;

    ReturnDataResultSet rdrs = null;
    printOK("moveDataToODS:Scanning Entity :" + strEntityType);

    //First run the entity query
    try {
      String strGetAllEntityStmt = getQueryStatement(PDH_GET_ALL_ENTITY);
      psEntityStatement = conPDH.prepareStatement(strGetAllEntityStmt);

      psEntityStatement.setString(1, strTimeStampNow);
      psEntityStatement.setString(2, strTimeStampNow);
      psEntityStatement.setString(3, strTimeStampNow);
      psEntityStatement.setString(4, strTimeStampNow);
      psEntityStatement.setString(5, strEnterprise);
      psEntityStatement.setString(6, strEntityType);
      psEntityStatement.setString(7, strTimeStampNow);
      psEntityStatement.setString(8, strTimeStampNow);
      psEntityStatement.setString(9, strTimeStampNow);
      psEntityStatement.setString(10, strTimeStampNow);

      psEntityStatement.setString(11, strTimeStampNow);
      psEntityStatement.setString(12, strTimeStampNow);
      psEntityStatement.setString(13, strTimeStampNow);
      psEntityStatement.setString(14, strTimeStampNow);
      psEntityStatement.setString(15, strEnterprise);
      psEntityStatement.setString(16, strEntityType);
      psEntityStatement.setString(17, strTimeStampNow);
      psEntityStatement.setString(18, strTimeStampNow);
      psEntityStatement.setString(19, strTimeStampNow);
      psEntityStatement.setString(20, strTimeStampNow);

      psEntityStatement.setString(21, strTimeStampNow);
      psEntityStatement.setString(22, strTimeStampNow);
      psEntityStatement.setString(23, strTimeStampNow);
      psEntityStatement.setString(24, strTimeStampNow);
      psEntityStatement.setString(25, strEnterprise);
      psEntityStatement.setString(26, strEntityType);
      psEntityStatement.setString(27, strTimeStampNow);
      psEntityStatement.setString(28, strTimeStampNow);
      psEntityStatement.setString(29, strTimeStampNow);
      psEntityStatement.setString(30, strTimeStampNow);

      psEntityStatement.setString(31, strTimeStampNow);
      psEntityStatement.setString(32, strTimeStampNow);
      psEntityStatement.setString(33, strTimeStampNow);
      psEntityStatement.setString(34, strTimeStampNow);
      psEntityStatement.setString(35, strEnterprise);
      psEntityStatement.setString(36, strEntityType);
      psEntityStatement.setString(37, strTimeStampNow);
      psEntityStatement.setString(38, strTimeStampNow);
      psEntityStatement.setString(39, strTimeStampNow);
      psEntityStatement.setString(40, strTimeStampNow);

      rsEntities = psEntityStatement.executeQuery();
      rdrs = new ReturnDataResultSet(rsEntities);
      rsEntities.close();
      rsEntities = null;
      psEntityStatement.close();
      psEntityStatement = null;
    } catch (SQLException e) {
      printErr("moveDataToODS:Error while Scanning Entity " + strEntityType);
      printErr(e.getMessage());
      e.printStackTrace();
      System.exit(1);
    }
    printOK("moveDataToODS:Scan complete for Entity :" + strEntityType + " found " + rdrs.size() + " Entities.");

    try {
      for (intRecProcessed = 0; intRecProcessed < rdrs.size(); intRecProcessed++) {
        intEntityId = rdrs.getColumnInt(intRecProcessed, 0);
        intNLSid = rdrs.getColumnInt(intRecProcessed, 1);

        PreparedStatement psSnapshotStatement = null;
        ResultSet rsEntitySnapshotRow = null;

        printOK("moveDataToODS:Extracting Snapshot for Entity :" + strEntityType + " :ID: " + intEntityId +
            " :NLS:" + intNLSid);
        //First run the entity query
        try {
          String strGetEntitySnapShotstmt = getQueryStatement(PDH_GET_ENTITY_SNAPSHOT);
          psSnapshotStatement = conPDH.prepareStatement(strGetEntitySnapShotstmt);
          psSnapshotStatement.setString(1, strEnterprise);
          psSnapshotStatement.setString(2, strTimeStampNow);
          psSnapshotStatement.setString(3, strTimeStampNow);
          psSnapshotStatement.setString(4, strTimeStampNow);
          psSnapshotStatement.setString(5, strTimeStampNow);

          psSnapshotStatement.setString(6, strEnterprise);
          psSnapshotStatement.setString(7, strTimeStampNow);
          psSnapshotStatement.setString(8, strTimeStampNow);
          psSnapshotStatement.setString(9, strTimeStampNow);
          psSnapshotStatement.setString(10, strTimeStampNow);

          psSnapshotStatement.setString(11, strEnterprise);
          psSnapshotStatement.setString(12, strEntityType);
          psSnapshotStatement.setInt(13, intEntityId);
          psSnapshotStatement.setString(14, strTimeStampNow);
          psSnapshotStatement.setString(15, strTimeStampNow);
          psSnapshotStatement.setString(16, strTimeStampNow);
          psSnapshotStatement.setString(17, strTimeStampNow);

          psSnapshotStatement.setString(18, strEnterprise);
          psSnapshotStatement.setInt(19, intNLSid);
          psSnapshotStatement.setString(20, strTimeStampNow);
          psSnapshotStatement.setString(21, strTimeStampNow);
          psSnapshotStatement.setString(22, strTimeStampNow);
          psSnapshotStatement.setString(23, strTimeStampNow);

          psSnapshotStatement.setString(24, strEnterprise);
          psSnapshotStatement.setString(25, strTimeStampNow);
          psSnapshotStatement.setString(26, strTimeStampNow);
          psSnapshotStatement.setString(27, strTimeStampNow);
          psSnapshotStatement.setString(28, strTimeStampNow);

          psSnapshotStatement.setInt(29, intNLSid);
          psSnapshotStatement.setString(30, strTimeStampNow);
          psSnapshotStatement.setString(31, strTimeStampNow);
          psSnapshotStatement.setString(32, strTimeStampNow);
          psSnapshotStatement.setString(33, strTimeStampNow);

          rsEntitySnapshotRow = psSnapshotStatement.executeQuery();
        } catch (SQLException e) {
          printErr("moveDataToODS:Error while Extracting Snapshot " + strEntityType + "ID: " + intEntityId +
              " NLS:" + intNLSid);
          printErr(e.getMessage());
          System.exit(1);
        }
        printOK("moveDataToODS:Snapshot Extraction complete :Entity :" +
            strEntityType + "ID: " + intEntityId + " NLS:" + intNLSid);

        updateODSRow(hTableColDefinitions, strEntityType, intEntityId, intNLSid,
            rsEntitySnapshotRow);//Update the row here

        rsEntitySnapshotRow.close();
        psSnapshotStatement.close();

        if (intCommitCount > 500) {
          intCommitCount = 0;
          printOK("moveDataToODS:COMMITTING at :" + intRecProcessed);
          conODS.commit();
        } else {
          intCommitCount++;
        }

      }

    } catch (SQLException e) {
      printErr("moveDataToODS: Error!" + e.getMessage());
      e.printStackTrace();
      System.exit(1);
    }

    rdrs = null;

    printOK("Datamovement Complete :" + strEntityType + " No. of Entity/NLS rows processed :" + intRecProcessed);

  }//End of Method moveDataToODS


  /*--------------------------------------------------------------------------------------------
    *   updateODSRow
    *   ------------
    *   Given a result set rows of Attribute codes and their values,
    *   method will insert a row into the given entity
    *
    */
      /**
   *  Description of the Method
   *
   *@param  hTableColDefinitions  Description of the Parameter
   *@param  strEntityType         Description of the Parameter
   *@param  intEntityId           Description of the Parameter
   *@param  intNLSid              Description of the Parameter
   *@param  rsEntityRow           Description of the Parameter
   */
  private void updateODSRow(Hashtable hTableColDefinitions,
      String strEntityType,
      int intEntityId,
      int intNLSid,
      ResultSet rsEntityRow) {

    String strColName = null;
    String strColValue = null;
    String strPrevColName = null;
    int intColWidth = 0;

    String strHeader = "INSERT INTO " + strSchemaODS + "." + strEntityType;// Build the insert header;
    String strInsertColumns = "(Valfrom,Valto,Entitytype,EntityID,NlsId";// Include the standard columns;
    String strInsertMarkers = "(?,?,?,?,?";// Put up the required markers;
    //Create the values vector
    Vector vColumnValues = new Vector();

    //Now construct the rest of the column
    //and its values
    try {
      if (rsEntityRow.next()) {
        do {
          strColName = rsEntityRow.getString(1);//AttributeCode
          strColValue = rsEntityRow.getString(2);//AttributeValue
          //Check for duplicate column names
          if (strColName.equals(strPrevColName)) {
            printErr("updateODSRow:WARNING:Duplicate column name found:NAME:" + strColName +
                "DISCARDING VALUE" + strColValue);

          } else {
            strPrevColName = strColName;
            strInsertColumns = strInsertColumns + "," + strColName.trim();
            //retrieve the Column width from the
            //Hash table
            intColWidth = ((Integer) hTableColDefinitions.get(strColName.trim())).intValue();
            //Check the width of the column
            if (intColWidth != ODS_COLUMN_MAXWIDTH) {   //Dont truncate if the width is 254
              if (strColValue.length() > intColWidth / ODS_COLUMN_MULTIPLIER) {
                printErr("updateODSRow:WARNING:" + strEntityType + ":" + strColName.trim() + " column too wide:" +
                    strColValue.trim() + " TRUNCATING TO:" +
                    strColValue.substring(0, intColWidth / ODS_COLUMN_MULTIPLIER));
  
                //Trim to correct length
  
                strColValue = strColValue.substring(0, intColWidth / ODS_COLUMN_MULTIPLIER);
  
              }
            }
            //Add to the vector after trimming to correct length
            vColumnValues.addElement(strColValue);
            //Add to the parameters of marker values
            strInsertMarkers = strInsertMarkers + ",?";

          }

        } while (rsEntityRow.next());
      } else {
        printErr("updateODSRow:WARNING:No Text/Unique flag Data found for Entity: " + strEntityType +
            "ID: " + intEntityId + "NLS: " + intNLSid);
      }
      //Close the column string and the parameter string
      strInsertColumns = strInsertColumns + ")";
      strInsertMarkers = strInsertMarkers + ")";

      //Construct the final statement
      PreparedStatement stmtInsertODS = conODS.prepareStatement(strHeader + strInsertColumns +
          " VALUES " + strInsertMarkers);

      //Now set the values to the statement
      //                      parameters

      // this is the header part
      stmtInsertODS.setString(1, strTimeStampNow);//Val from
      stmtInsertODS.setString(2, strTimeStampForever);//Val to
      stmtInsertODS.setString(3, strEntityType);//EntityType
      stmtInsertODS.setInt(4, intEntityId);//Entityid
      stmtInsertODS.setInt(5, intNLSid);//NLSid

      Enumeration e = vColumnValues.elements();
      String strSetStatementVal = null;
      int i = 6;//Valfrom,valto,entitytype,entityid,nls id added before
      // so start at parameter 6
      while (e.hasMoreElements()) {
        strSetStatementVal = (String) e.nextElement();

        if (strSetStatementVal.equals(" ")) {//This means that the field had null

          stmtInsertODS.setString(i++, null);
        } else {
          stmtInsertODS.setString(i++, strSetStatementVal);
        }
      }
      //OK Insert here!
      stmtInsertODS.execute();
      stmtInsertODS.close();
      stmtInsertODS = null;

    } catch (SQLException e) {//Dont stop processing here,
      //let it go on to the next entity
      //analyze the error and
      //initialize the problem entity later
      printErr("updateODSRow:ERROR During update:" + e.getMessage());
      e.printStackTrace();
      printErr("updateODSRow:ERROR During update:PROBLEM FOUND FOR ENTITYTYPE:" + strEntityType +
          ":ENTITYID:" + intEntityId +
          ":NLSID:" + intNLSid);
      printErr("updateODSRow:Contents of columns: " + strInsertColumns);
      printErr("updateODSRow:Parameter list: " + strInsertMarkers);
      displayVectorContents("updateODSRow:Contents of columnValues :", vColumnValues);
    } catch (Exception e) {
      printErr("updateODSRow:ERROR:Probably the has table column value is returning NULL" + e.getMessage());
      printErr("updateODSRow:ERROR During update:PROBLEM FOUND FOR ENTITYTYPE:" + strEntityType +
          ":ENTITYID:" + intEntityId +
          ":NLSID:" + intNLSid);
      printErr("updateODSRow:Contents of columns: " + strInsertColumns);
      printErr("updateODSRow:Parameter list: " + strInsertMarkers);
      displayVectorContents("updateODSRow:Contents of columnValues :", vColumnValues);

    } finally {
      vColumnValues = null;
    }

  }//End of Method updateODSRow


  /*-----------------------------------------------------------------------------------
    * initializeDeleteLog
    * -------------------
    * This method initializes the deletelog table
    */
      /**
   *  Description of the Method
   */
  private void initializeDeleteLog() {
    String strDropTimeTableStmt = getQueryStatement(ODS_DROP_DELETELOG);
    String strCreateNewTimeTableStmt = getQueryStatement(ODS_CREATE_DELETELOG);
    try {
      DatabaseMetaData dbMeta = conODS.getMetaData();
      String strTableSpace = null;
      String strIndexSpace = null;

      String[] strMetaDB2Token = {"TABLE"};// Look only for tables
      boolean bTableThere = false;// flag to indicate if the table is currently present
      ResultSet rs = dbMeta.getTables(null, strSchemaODS, "DELETELOG", strMetaDB2Token);//  Check for the table ...
      if (rs.next()) {
        bTableThere = true;// We have found it...
      }
      rs.close();

      if (bTableThere) {
        //Drop the table since we found it
        Statement stmtDropTimetable = conODS.createStatement();
        printOK("initializeDeleteLog:DROPPING DELETELOG");
        stmtDropTimetable.execute(strDropTimeTableStmt);
        stmtDropTimetable.close();

      } else {
        printOK("initializeDeleteLog:DELETELOG DROP BYPASSED");
      }
      printOK("initializeDeleteLog:CREATING NEW DELETELOG");
      Statement stmtCreateTimetable = conODS.createStatement();
      stmtCreateTimetable.execute(strCreateNewTimeTableStmt);
      stmtCreateTimetable.close();

    } catch (SQLException e) {
      printErr("initializeDeleteLog:ERROR:" + e.getMessage());
      System.exit(1);
    }
  }//End method initializeDeleteLog


  /*-----------------------------------------------------------------------------------
    *   initializeTimeTable
    *   -------------------
    *   This method drops the timetable table (if found) and creates a new one
    */
  /**
   *  Description of the Method
   */
  private void initializeTimeTable() {
    String strDropTimeTableStmt = getQueryStatement(ODS_DROP_TIMETABLE);
    String strCreateNewTimeTableStmt = getQueryStatement(ODS_CREATE_TIMETABLE);
    try {
      DatabaseMetaData dbMeta = conODS.getMetaData();
      String strTableSpace = null;
      String strIndexSpace = null;

      String[] strMetaDB2Token = {"TABLE"};// Look only for tables
      boolean bTableThere = false;// flag to indicate if the table is currently present
      ResultSet rs = dbMeta.getTables(null, strSchemaODS, "TIMETABLE", strMetaDB2Token);//  Check for the table ...
      if (rs.next()) {
        bTableThere = true;// We have found it...
      }
      rs.close();

      if (bTableThere) {
        //Drop the table since we found it
        Statement stmtDropTimetable = conODS.createStatement();
        printOK("initializeTimeTable:DROPPING TIMETABLE");
        stmtDropTimetable.execute(strDropTimeTableStmt);
        stmtDropTimetable.close();

      } else {
        printOK("initializeTimeTable:TIMETABLE DROP BYPASSED");
      }
      printOK("initializeTimeTable:CREATING NEW TIMETABLE");
      Statement stmtCreateTimetable = conODS.createStatement();
      stmtCreateTimetable.execute(strCreateNewTimeTableStmt);
      stmtCreateTimetable.close();

    } catch (SQLException e) {
      printErr("initializeTimeTable:ERROR:" + e.getMessage());
      System.exit(1);
    }

  }//End method initializeTimeTable


  /*------------------------------------------------------------------------------------
    *   setTimestampInTimetable
    *   -----------------------
    *   Inserts the current run time into the timetable with a flag of 'I'
    *           to show that the initialize inserted it
    */
      /**
   *  Sets the timestampInTimetable attribute of the ODSCreateEntities object
   */
  private void setTimestampInTimetable() {
    String strInsertTimestampStmt = getQueryStatement(ODS_INSERT_TIMETABLE_ROW);
    try {
      Statement stmtInsertTimeStamp = conODS.createStatement();
      stmtInsertTimeStamp.execute(strInsertTimestampStmt);
      stmtInsertTimeStamp.close();

    } catch (SQLException e) {
      printErr("setTimestampInTimetable:ERROR:Insert into timetable:" + e.getMessage());
      System.exit(1);
    }

  }//End method setTimestampInTimetable


  /*-----------------------------------------------------------------------------------
    * function getQueryStatement
    * -----------------------
    * This function returns query statements based upon the passed query number qn
    *
    * -------------------------------------------------------------------------------------
    */
      /**
   *  Gets the queryStatement attribute of the ODSCreateEntities object
   *
   *@param  qn  Description of the Parameter
   *@return     The queryStatement value
   */
  private String getQueryStatement(int qn) {
    switch (qn) {
        case (PDH_GET_META_ENTITIES_ATTRIBUTES):
          //This query will return the valid entities/relators, its attributes
          //The column length definition, the Table space and Index space on the
          //ODS database
          return
              "SELECT DISTINCT mea.linktype1 AS entitytype " +
              "   ,UCASE(ma.entitytype) AS attributecode  " +
              "   ,RTRIM(coalesce(ml.linkValue,'0'))  as odsLength " +
              "   ,RTRIM(coalesce(TS.LINKVALUE,'TSPACE01'))  as TABLESPACE " +
              "   ,RTRIM(coalesce(IS.linkValue,'ISPACE02'))  as INDEXSPACE " +
              "   ,ma.entityclass AS attributetype  " +
              " FROM  " + strSchemaPDH + ".metaentity  me " +
              " INNER JOIN " + strSchemaPDH + ".metalinkattr mea ON " +
              "   me.entitytype=mea.linktype1  " +
              (!strInitializeMode.equals("CONTINUE") && !strEntityTableName.equals("ALL") ? " AND mea.LinkType1 = '" + strEntityTableName + "' " : " ") + //INITIALIZE A SPECIFIC TABLE
          "   AND mea.linktype='Entity/Attribute'  " +
              "   AND mea.linkcode='EntityAttribute'  " +
              "   AND mea.linkvalue='L'  " +
              "   AND me.enterprise = mea.enterprise " +
              "   AND mea.valto > '" + strTimeStampNow + "'  " +
              "   AND mea.effto > '" + strTimeStampNow + "' " +
              "   AND mea.valFrom <= '" + strTimeStampNow + "'  " +
              "   AND mea.effFrom <= '" + strTimeStampNow + "' " +
              " LEFT join " + strSchemaPDH + ".metalinkattr ml on " +
              "   me.enterprise = ml.enterprise  " +
              "   AND mea.linktype2 = ml.linktype2  " +
              "   AND ml.linktype1='ODS'  " +
              "   AND ml.linktype = 'ODSLength' " +
              "   AND ml.valto > '" + strTimeStampNow + "'  " +
              "   AND ml.effto > '" + strTimeStampNow + "' " +
              "   AND ml.valFrom <= '" + strTimeStampNow + "'  " +
              "   AND ml.effFrom <= '" + strTimeStampNow + "' " +
              " LEFT join " + strSchemaPDH + ".metalinkattr TS on " +
              "   me.enterprise = TS.enterprise " +
              "   AND mea.linktype1 = TS.linktype2  " +
              "   AND ts.linktype1='ODS'  " +
              "   AND ts.linktype = 'ODSConfiguration'  " +
              "   AND ts.linkCode = 'TSPACE' " +
              "   AND ts.valto > '" + strTimeStampNow + "'  " +
              "   AND ts.effto > '" + strTimeStampNow + "' " +
              "   AND ts.valFrom <= '" + strTimeStampNow + "'  " +
              "   AND ts.effFrom <= '" + strTimeStampNow + "' " +
              " LEFT join " + strSchemaPDH + ".metalinkattr IS on " +
              "   me.enterprise = ml.enterprise  " +
              "   AND mea.linktype1 = IS.linktype2  " +
              "   AND Is.linktype1='ODS'  " +
              "   AND Is.linktype = 'ODSConfiguration'  " +
              "   AND Is.linkCode = 'ISPACE' " +
              "   AND is.valto > '" + strTimeStampNow + "'  " +
              "   AND is.effto > '" + strTimeStampNow + "' " +
              "   AND is.valFrom <= '" + strTimeStampNow + "'  " +
              "   AND is.effFrom <= '" + strTimeStampNow + "' " +
              " INNER JOIN " + strSchemaPDH + ".metaentity ma ON " +
              "   mea.linktype2=ma.entitytype " +
              "   AND mea.enterprise = ma.enterprise " +
              "   AND ma.entityclass IN ( 'I','T','U','S','A') " + //Id, Text,Status and Unique flags
          "   AND ma.valto > '" + strTimeStampNow + "'  " +
              "   AND ma.effto > '" + strTimeStampNow + "' " +
              "   AND ma.valFrom <= '" + strTimeStampNow + "'  " +
              "   AND ma.effFrom <= '" + strTimeStampNow + "' " +
              " WHERE  " +
              "   me.entityclass IN ('Entity','Relator') " +
              "   AND me.enterprise= '" + strEnterprise + "'  " +
              "   AND me.valto > '" + strTimeStampNow + "'  " +
              "   AND me.effto > '" + strTimeStampNow + "'  " +
              "   AND me.valFrom <= '" + strTimeStampNow + "'  " +
              "   AND me.effFrom <= '" + strTimeStampNow + "'  " +
              " ORDER BY entitytype,attributecode FOR READ ONLY";
        case (PDH_GET_ALL_ENTITY):
          return
              " SELECT  " +
              "       E.ENTITYID as ENTITYID " +
              "      ,T.NLSID    as NLSID " +
              "   FROM   " + strSchemaPDH + ".ENTITY E " +
              "   INNER JOIN " + strSchemaPDH + ".TEXT T ON " +
              "         T.Enterprise = E.Enterprise " +
              "         AND T.EntityType = E.EntityType " +
              "         AND T.EntityID = E.EntityID " +
              "         AND T.ValFrom <= ? " +
              "         AND  ? < T.ValTo " +
              "         AND  T.EffFrom <= ? " +
              "         AND  ? < T.EffTo " +
              "   WHERE " +
              "       E.EntityId > 0 " +
              "       AND E.Enterprise =   ? " +
              "       AND E.EntityType = ? " +
              "       AND E.ValFrom <= ? " +
              "       AND  ? < E.ValTo " +
              "       AND  E.EffFrom <= ? " +
              "       AND  ? < E.EffTo  " +
              " UNION " +
              " SELECT " +
              " E.ENTITYID as ENTITYID " +
              "      ,LT.NLSID    as NLSID " +
              "   FROM   " + strSchemaPDH + ".ENTITY E " +
              "   INNER JOIN " + strSchemaPDH + ".LongText LT ON " +
              "         LT.Enterprise = E.Enterprise " +
              "         AND LT.EntityType = E.EntityType " +
              "         AND LT.EntityID = E.EntityID " +
              "         AND LT.ValFrom <= ? " +
              "         AND  ? < LT.ValTo " +
              "         AND  LT.EffFrom <= ? " +
              "         AND  ? < LT.EffTo " +
              "   WHERE " +
              "         E.EntityId > 0 " +
              "         AND E.Enterprise =   ? " +
              "         AND E.EntityType = ? " +
              "         AND E.ValFrom <= ? " +
              "         AND  ? < E.ValTo " +
              "         AND  E.EffFrom <= ? " +
              "         AND  ? < E.EffTo  " +
              " UNION " +
              " SELECT " +
              "       E.ENTITYID as ENTITYID " +
              "      ,B.NLSID    as NLSID " +
              "   FROM  " + strSchemaPDH + ".ENTITY E " +
              "   INNER JOIN " + strSchemaPDH + ".BLOB B ON " +
              "         B.Enterprise = E.Enterprise " +
              "         AND B.EntityType = E.EntityType " +
              "         AND B.EntityID = E.EntityID " +
              "         AND B.ValFrom <= ? " +
              "         AND  ? < B.ValTo " +
              "         AND  B.EffFrom <= ? " +
              "         AND  ? < B.EffTo " +
              "   WHERE " +
              "         E.EntityId > 0 " +
              "         AND E.Enterprise =   ? " +
              "         AND E.EntityType = ? " +
              "         AND E.ValFrom <= ? " +
              "         AND  ? < E.ValTo " +
              "         AND  E.EffFrom <= ? " +
              "         AND  ? < E.EffTo " +
              " UNION " +
              " SELECT " +
              "       E.ENTITYID as ENTITYID " +
              "       ,1    as NLSID " +
              "   FROM  " + strSchemaPDH + ".ENTITY E " +
              "   INNER JOIN " + strSchemaPDH + ".FLAG F ON " +
              "         F.Enterprise = E.Enterprise " +
              "         AND F.EntityType = E.EntityType " +
              "         AND F.EntityID = E.EntityID " +
              "         AND F.ValFrom <= ? " +
              "         AND  ? < F.ValTo " +
              "         AND  F.EffFrom <= ? " +
              "         AND  ? < F.EffTo " +
              "   WHERE " +
              "         E.EntityId > 0 " +
              "         AND E.Enterprise =   ? " +
              "         AND E.EntityType = ? " +
              "         AND E.ValFrom <= ? " +
              "         AND  ? < E.ValTo " +
              "         AND  E.EffFrom <= ? " +
              "         AND  ? < E.EffTo " +
              " ORDER BY ENTITYID,NLSID ";
        case (PDH_GET_ENTITY_SNAPSHOT):
          return
              " WITH MYTABLE (ENTITYTYPE,ENTITYID,ATTRIBUTECODE,ATTRIBUTETYPE) AS  " +
              " ( SELECT   " +
              "      E.EntityType  " +
              "     ,E.EntityID  " +
              "     ,UCASE(MA.EntityType)  as AttributeCode " +
              "     ,MA.EntityClass as AttributeType " +
              "   FROM  " + strSchemaPDH + ".ENTITY E     " +
              "   INNER JOIN  " + strSchemaPDH + ".metalinkattr ML ON   " +
              "       ML.Enterprise = ?     " +
              "       AND ML.linktype = 'Entity/Attribute'   " +
              "       AND ML.linktype1 = E.EntityType   " +
              "       AND ML.linkcode='EntityAttribute'  " +
              "       AND ML.linkvalue='L'  " +
              "       AND ML.ValFrom <= ? AND ? <ML.ValTo  " +
              "       AND ML.EffFrom <= ? AND ? < ML.EffTo  " +
              "   INNER JOIN  " + strSchemaPDH + ".MetaEntity MA ON  " +
              "       MA.Enterprise = ?  " +
              "       AND MA.EntityType = ML.Linktype2 " +
              "       AND MA.EntityClass in ('T','I','U','S')  " +
              "       AND MA.ValFrom <= ? AND ? <MA.ValTo  " +
              "       AND MA.EffFrom <= ? AND ? < MA.EffTo  " +
              "  WHERE  " +
              "     E.Enterprise = ?  " +
              "     AND E.EntityType = ?  " +
              "     AND E.Entityid = ?  " +
              "     AND E.ValFrom <= ? AND ? < E.ValTo  " +
              "     AND E.EffFrom <= ? AND ? < E.EffTo " +
              " )  " +
              "  " +
              " SELECT DISTINCT" +
              "        MT.AttributeCode  as AttributeCode         " +
              "       ,COALESCE(RTRIM(t.AttributeValue),' ') as AttributeValue " +
              " FROM MYTABLE MT " +
              " LEFT JOIN " + strSchemaPDH + ".TEXT T ON " +
              "       T.enterprise = ? " +
              "       AND T.entitytype = MT.entitytype " +
              "       AND T.entityid = MT.entityid " +
              "       AND UCASE(T.attributecode) = MT.attributecode " +
              "       AND T.nlsid = ? " +
              "       AND T.ValFrom <= ? AND ? < T.ValTo " +
              "       AND T.EffFrom <= ? AND ? < T.EffTo " +
              " WHERE " +
              "       MT.attributeType in ('T','I') " +
              " UNION ALL " +
              " SELECT DISTINCT" +
              "         MT.AttributeCode  as AttributeCode         " +
              "         ,COALESCE(RTRIM(MD.LongDescription),' ') as AttributeValue    " +
              " FROM mytable MT " +
              " LEFT JOIN " + strSchemaPDH + ".flag F ON " +
              "             F.enterprise = ? " +
              "         AND F.entitytype = MT.entitytype " +
              "         AND F.entityid = MT.entityid " +
              "         AND UCASE(F.attributecode) = MT.attributecode " +
              "         AND F.ValFrom <= ? AND ? < F.ValTo " +
              "         AND F.EffFrom <= ? AND ? < F.EffTo " +
              " LEFT JOIN  " + strSchemaPDH + ".MetaDescription MD ON " +
              "           MD.Enterprise = F.Enterprise  " +
              "       AND MD.descriptionType = F.AttributeCode " +
              "       AND MD.DescriptionClass = F.AttributeValue " +
              "       AND MD.NLSID = ? " +
              "       AND MD.valfrom <= ? and  ? < MD.ValTo " +
              "       AND MD.efffrom <= ? and  ?  < MD.effTo " +
              " WHERE " +
              " MT.attributeType IN  ('U','S') " +
              " ORDER BY attributecode ";

        case (ODS_SET_TIMETABLE):
        case (ODS_CREATE_ENTITY_INDEX):
          return
              "CREATE UNIQUE INDEX " + strSchemaODS + "." + strCurrentEntity.trim() + "1 ON " +
              strSchemaODS + "." + strCurrentEntity.trim() + "(ENTITYID,NLSID )  ";
        case (ODS_INSERT_ENTITY_ROW):
        case (ODS_DROP_ENTITY_TABLE):
          return
              "DROP TABLE " + strSchemaODS + "." + strCurrentEntity.trim();
        case (ODS_CREATE_TIMETABLE):
          return
              "CREATE TABLE " + strSchemaODS + ".TIMETABLE (RUNTIME TIMESTAMP NOT NULL, RUNTYPE VARCHAR(1), " +
              "PRIMARY KEY (RUNTIME)) ";
        case (ODS_CREATE_DELETELOG):
          return
              "CREATE TABLE " + strSchemaODS + ".DELETELOG " +
              "( ENTITYTYPE CHAR(" + ODS_ET_WIDTH + " ) NOT NULL, " +
              " ENTITYID INTEGER NOT NULL, " +
              " NLSID INTEGER ," +
              " ENTITY1TYPE CHAR(" + ODS_ET_WIDTH + " ), " +
              " ENTITY1ID INTEGER ," +
              " ENTITY2TYPE CHAR(" + ODS_ET_WIDTH + " ), " +
              " ENTITY2ID INTEGER ," +
              " OPICMTYPE CHAR(32) NOT NULL," +
              " ATTRIBUTECODE CHAR(32) , " +
              " ATTRIBUTEVALUE CHAR(24) , " +
              " VALFROM TIMESTAMP NOT NULL, PRIMARY KEY (OPICMTYPE,ENTITYTYPE,ENTITYID,VALFROM)) ";
        case (ODS_DROP_TIMETABLE):
          return
              "DROP TABLE " + strSchemaODS + ".TIMETABLE";
        case (ODS_DROP_DELETELOG):
          return
              "DROP TABLE " + strSchemaODS + ".DELETELOG";
        case (ODS_INSERT_TIMETABLE_ROW):
          return
              "INSERT INTO " + strSchemaODS + ".TIMETABLE " +
              "(RUNTIME,RUNTYPE) VALUES('" + strTimeStampNow + "','I')";
        default:
          return "";
    }
  }//End method getQueryStatement


  /*-------------------------------------------------------------------------------
    *   Logging Methods
    *   ---------------
    *
    -----------------------------------------------------------------------*/
  /**
   *  Description of the Method
   *
   *@param  strPrintln  Description of the Parameter
   */
  private void printOK(String strPrintln) {
    String strLogDateTime = c_sdfTimestamp.format(new Date());
    System.out.println(strLogDateTime + " OCE: " + strPrintln);
  }


  /**
   *  Description of the Method
   *
   *@param  strPrintln  Description of the Parameter
   */
  private void printErr(String strPrintln) {
    String strLogDateTime = c_sdfTimestamp.format(new Date());
    System.err.println(strLogDateTime + " OCE: " + strPrintln);
  }
  //--------------------------End of logging methods


  //use only for debugging
  /**
   *  Description of the Method
   *
   *@param  displayString  Description of the Parameter
   *@param  v              Description of the Parameter
   */
  public static void displayVectorContents(String displayString, Vector v) {
    Vector tempvector;
    tempvector = v;
    System.err.println(displayString);
    Enumeration ee = tempvector.elements();
    while (ee.hasMoreElements()) {
      System.err.println("    |" + ee.nextElement() + "|");
    }
  }


  /**
   *  Gets the version attribute of the ODSCreateEntities object
   *
   *@return    The version value
   */
  private String getVersion() {
    return ("$Id: ODSCreateEntityShell.java,v 1.25 2003/10/20 16:56:25 bala Exp $");
  }


  /**
   *  readPropertyFile reads the property file reverseods.properties
   */
  private void readPropertyFile() {
    printOK("Reading Datamover.properties");
    Properties properties = new Properties();
    try {
      properties.load(new FileInputStream("datamover.properties"));
    } catch (IOException e) {
      printOK("Error reading datamover.properties");
      System.exit(1);
    }

    strurlODS = properties.getProperty("ODSURL");
    struidODS = properties.getProperty("ODSID");
    strpwdODS = properties.getProperty("ODSPWD");
    strurlPDH = properties.getProperty("PDHURL");
    struidPDH = properties.getProperty("PDHID");
    strNLSOds = properties.getProperty("ODSNLS","ALL");

    strpwdPDH = properties.getProperty("PDHPWD");
    strSchemaPDH = properties.getProperty("PDHSCHEMA");
    strSchemaODS = properties.getProperty("ODSSCHEMA", "OPICM").toUpperCase();
    strEnterprise = properties.getProperty("ENTERPRISE");
    strRunMode = properties.getProperty("RUNMODE");
    strInitializeMode = properties.getProperty("INITIALIZEMODE");//Default will be "CONTINUE"



    printOK("Read properties.");


  }//readPropertyFile

}//End Class ODSCreateEntities


