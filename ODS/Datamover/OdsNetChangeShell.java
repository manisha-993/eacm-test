/*
 *       OdsNetChangeShell.java
 *       ----------------------
 *       Licensed Materials -- Property of IBM
 *
 *       (C) Copyright International Business Machines Corporation. 2001,2006
 *       All Rights Reserved.
 *       ----------------------------------------------------------------------

 */


//Adding more comments
// $Log: OdsNetChangeShell.java,v $
// Revision 1.113  2015/06/10 12:38:53  jilichao
// update DB2 jdbc version to type4
//
// Revision 1.100  2007/01/18 04:08:25  software
// fix typo
//
// Revision 1.99  2007/01/15 18:24:12  software
// fix typo for timestamp
//
// Revision 1.98  2007/01/12 16:49:33  software
// change delta queries to fix db2 bug for timestamp
//
// Revision 1.97  2006/09/26 18:10:26  bala
// MKTGCOLORIMG also off blob exclude list
//
// Revision 1.96  2006/09/26 17:09:10  bala
// take FMCHGREPORT off the blob exclude list.
//
// Revision 1.95  2006/06/22 19:13:25  bala
// Property file appended exclude list for Blob attributes
//
// Revision 1.94  2006/06/13 22:27:53  bala
// set Default Width of odsLength to 64 in query
//
// Revision 1.93  2006/02/20 23:48:29  bala
// fix in entityExistsinPDH...use strTimestampNow instead of machine time
//
// Revision 1.92  2006/02/18 00:17:46  bala
// get timestamp from PDH database instead of the machine the application runs on
//
// Revision 1.91  2006/02/16 22:57:49  bala
// d
//
// Revision 1.90  2006/01/27 18:15:39  bala
// more copyright changes
//
// Revision 1.89  2006/01/27 18:09:03  bala
// changes to copyright notice
//
// Revision 1.88  2006/01/11 19:50:25  bala
// moving up the pqMainQuery close statement up just after it finishes processing it
//
// Revision 1.87  2006/01/11 19:22:18  bala
// add reconnect in postRunTime and add error messages when Sql errors occurs during close()
//
// Revision 1.86  2006/01/10 23:44:35  bala
// check for nulls before close, and add reconnectODS/PDH methods to updateallkeys and processAllKeys
//
// Revision 1.85  2006/01/06 20:02:25  dave
// ok.. need more try
//
// Revision 1.84  2006/01/06 19:59:55  dave
// fix
//
// Revision 1.83  2006/01/06 18:32:07  dave
// attempting to induce reconnect Logic
//
// Revision 1.82  2006/01/04 23:04:31  yang
// fix null pointer ps1.close() only when its not null
//
// Revision 1.81  2005/12/14 01:17:53  bala
// remove extra close statements left over from the Jtest endeavour
//
// Revision 1.80  2005/12/13 22:34:27  bala
// remove unreachable return statements
//
// Revision 1.79  2005/12/13 21:48:23  bala
// removing the "NOT EFFFROM=EFFTO" clause from the LT and Blob queries
//
// Revision 1.78  2005/02/25 18:52:35  bala
// Jtest changes
//
// Revision 1.77  2004/05/14 16:48:57  dave
// added commits to PDH connection to free up resources
// on large pulls
//
// Revision 1.76  2004/03/12 19:43:34  bala
// Using returndataresult set for processing non-entity rows, additional query needed for blob, this is to reduce the impact on PDH rows getting locked
//
// Revision 1.75  2004/01/09 22:32:40  bala
// changed entitykeychanges query as it wasnt picking up flag only changes for an entity
//
// Revision 1.74  2003/11/07 18:49:54  bala
// Report when column is set to 254
//
// Revision 1.73  2003/10/20 16:55:51  bala
// do not truncate if column width is 254, truncate otherwise
//
// Revision 1.72  2003/09/27 02:08:57  bala
// trim the value of the attribute when the width of the column
// is 254
//
// Revision 1.71  2003/06/11 20:27:32  bala
// Adding 'A'br class to Flag Queries
//
// Revision 1.70  2003/05/08 18:36:01  bala
// Added English only capability...this does not truncate column
// values assuming triple byte, also does not expand the column
// size from what is defined in odslength.
// The system detects this from the propertyfile attribute strNLSOds
//
// Revision 1.69  2003/03/06 22:25:34  bala
// getting ods schema as uppercase
//
// Revision 1.68  2003/03/06 22:20:31  bala
// changed logDelete method to sql statement parameters
//
// Revision 1.67  2003/02/28 19:31:46  bala
// Getting 'A'br attributes of an entity - changed the meta query
//
// Revision 1.66  2002/10/18 17:02:13  bala
// changed updateODSColumn method to split up the update statement
// when the no. of entities exceed a number as specified by
// the property MAXROWUPDATECOUNT (1 entity = 1 row)
//
// Revision 1.65  2002/10/14 21:08:02  bala
// fix typo in update statement
//
// Revision 1.64  2002/10/14 18:13:15  bala
// updates to ods row to put Current timestamp...not last runtime
//
// Revision 1.63  2002/10/02 19:52:32  bala
// Fixed situation where multiple changes to metadescription were made in between datamover runs.
// The changes will be stepped thru for each change.
//
// Revision 1.62  2002/09/28 00:18:07  bala
// updating/refreshing at the end of result set depending on whether ods row is present
//
// Revision 1.61  2002/09/27 23:26:15  bala
// fix typo in query
//
// Revision 1.60  2002/09/27 18:21:35  bala
// added same check to the end of resultset delete
//
// Revision 1.59  2002/09/27 18:14:29  bala
// checks the pdh entity to see whether the entity is deleted before deleting the ods row
//
// Revision 1.58  2002/09/25 21:37:36  dave
// verbose print
//
// Revision 1.57  2002/09/25 20:20:19  bala
// Print vars before retiring entity
//
// Revision 1.56  2002/07/10 21:23:57  dave
// syntax fix
//
// Revision 1.55  2002/07/10 20:30:19  dave
// fix to remove the embedded single quote problem
//
// Revision 1.54  2002/05/22 18:16:56  bala
// Fix the ENTITY_CHANGES query
//
// Revision 1.53  2002/05/22 16:32:02  bala
// fixing ENTITY_KEY_CHANGES query, union to flagx
// should return entity dates...not flag dates
//
// Revision 1.52  2002/05/10 20:33:49  bala
// setting entitylist for metaflagchanges to zero if no entites found in flag
//
// Revision 1.51  2002/05/10 19:47:17  bala
// typo
//
// Revision 1.50  2002/05/10 18:21:53  bala
// fixed bug - wrong assignment to columnname during metaflagdetection
// fixed possible bug when multiple metaflags introduced - earlier logic will look to replace nulls...
// subsequent updates will be incorrect since the first null replace would have replaced all nulls
//
// Revision 1.49  2002/04/23 22:14:07  bala
// correct update sql typo for column update
//
// Revision 1.48  2002/04/23 18:57:09  bala
// changing valfrom to be set to current timestamp - it was
// last runtime in the column update
//
// Revision 1.47  2002/04/23 18:29:51  bala
// Added an end of result set break for metadescription changes
// moved the meat of the column update to a new method
//
// Revision 1.46  2002/04/19 21:31:18  bala
// changed PDH_GET_NEW_AND_OLD_MDLONGTEXT
// query....effrom of old row is not always < effrom of new row
// observed that they are equal
//
// Revision 1.45  2002/04/18 16:37:16  bala
// fixed bug in the metachange update routine
// added more debug comments...some of them have to be removed later
// after test
//
// Revision 1.44  2002/04/17 18:58:45  bala
// better error trapping
//
// Revision 1.43  2002/04/11 23:26:09  bala
// metalinkattribute changed to metalinkattr
//
// Revision 1.42  2002/04/05 18:43:20  bala
// updating VALFROM with the changed column for
// metadescription changes
//
// Revision 1.41  2002/04/05 18:06:20  bala
// This includes changes for v1.40:
// 1) Flag query changes
// 2) Property file changes (dm now will read propertyfile
// instead of command line parms
// 3) Metadescription change detection: Change to the
// way datamover updates the ods unique flag colums when metadescription changes
//
// Revision 1.40  2002/04/05 18:02:06  bala
// Flag Query changes....additional 'AND' clause to f.entitytype
//
// Revision 1.39  2002/01/24 21:11:29  bala
// changed the PDH_GET_INCLUDE_MD_UF query...
// mismatched columns, added entitydates
// and join to ENTITY  in MYFLAGS1
// subquery to keep the entity integrity
//
// Revision 1.38  2002/01/22 19:32:13  bala
// moved intializing of relator variables outside
// the hasmorelements in the commitChangesToODSTables
// method
//
// Revision 1.37  2002/01/15 22:40:35  bala
// fixed comment
//
// Revision 1.36  2002/01/15 22:39:24  bala
// fixed metadescription change detection query
//
// Revision 1.35  2002/01/15 22:36:40  bala
// changed deletelog structure, added entity1,id, entity2,id columns
//
// Revision 1.34  2002/01/05 00:42:54  bala
// check to see whether row exists in ods before deleting it
//
// Revision 1.33  2002/01/05 00:29:32  bala
// corrected bug
//
// Revision 1.32  2002/01/02 23:56:12  bala
// changed logging description from Metadescription to MetaFlagValue
//
// Revision 1.31  2002/01/02 22:45:48  bala
// Added Logging for Deletion, Change the Flag Query to
// flags which have the LinkValue of 'A'
//
// Revision 1.30  2001/12/13 22:08:02  bala
// changed longtext selection criteria to select 'L' and 'X' type
//
// Revision 1.29  2001/12/10 17:30:16  bala
// typo
//
// Revision 1.28  2001/12/10 17:29:27  bala
// changed PDH_GET_MD_ENTITY_CHANGES_PQ
// PDH_GET_INCLUDE_MD_UF_ENTITIES_PQ, added
// more join for enterprise and flag type
//
// Revision 1.27  2001/12/05 22:04:28  bala
// fixed parms for flag change prepared statement
//
// Revision 1.26  2001/12/05 21:30:29  bala
// fix for flag change query
//
// Revision 1.25  2001/12/05 17:58:55  bala
// changed insert prepared statement parms
//
// Revision 1.24  2001/11/27 00:10:06  bala
// Dave changes....
// Entitychanges resultset processed as returndata result set
// dropped the DISTINCT and UCASE functions in the entitysnapshot
// query to make it run faster
//
// Revision 1.23  2001/11/07 19:39:00  bala
// rolled over V23 changes (metadescription query)
//
// Revision 1.22  2001/10/31 18:42:43  bala
// added 1 more parm to entitychanges statement
//
// Revision 1.21  2001/10/31 18:28:36  bala
// added extra parm for timestamp, added extra flag join to entity changes query
//
// Revision 1.20  2001/10/24 17:33:20  bala
// Added more verbose error comments
//
// Revision 1.19  2001/10/23 22:02:53  bala
// added date queries to the predicate in the Text,Flag,Longtext and
// Blob subqueries in the EntitySnapshot query
//
// Revision 1.18  2001/08/31 22:30:53  bala
// changed queries to return ucase(attributecode)
// flag changes query changed to return all flags changed,
// not just unique flags...this means all the flags will be moved
// over
//
// Revision 1.17  2001/08/29 17:51:48  bala
// Changed 2.3 query which uses Metalinkattribute to Metalinkattr.
// Added one more predicate to the metalinkattribute join
// "Linkcode="EntityAttribute" and Linktype='L'"
//
// Revision 1.16  2001/06/28 17:30:53  bala
// removed the attribute type updates for blob
//
// Revision 1.15  2001/06/28 17:10:59  bala
// insert statement for blob was missing a column attribute type
//
// Revision 1.14  2001/06/26 17:43:08  bala
// Added attribute type 'S' to entity snapshot query
//
// Revision 1.13  2001/06/05 16:14:51  bala
// Check the attribute length more comprehensively
//
// Revision 1.12  2001/06/01 17:41:05  bala
// Changed entity/attribute build query. Added attribute class 'S' for status
//
// Revision 1.11  2001/05/22 16:47:43  bala
// Changed the EntitySnapshot query to query the normal tables
// - not the X tables as the x tables will only have the changed
// information and not all the current information if the
// xtable was created after the data was created in the tables
//
// Revision 1.10  2001/05/01 21:48:53  bala
// corrected MD Query
//
// Revision 1.9  2001/05/01 21:09:20  bala
// fixed date error during insert
//
// Revision 1.8  2001/05/01 17:33:17  bala
// The method for Entity net changes (scanEntityDef) was
// commented out. Uncommented it
//
// Revision 1.7  2001/04/20 18:04:48  bala
// Cleaned up useless code
//
// Revision 1.6  2001/04/19 20:55:24  bala
// Changed relator query for more efficiency,
// Changed all PDH queries which uses Relator, Entity,
// Flag, Text, Longtext and blob to use their corresponding
// 'x' tables
//
// Revision 1.5  2001/03/29 19:24:49  bala
// added getVersion method
//
// Revision 1.4  2001/03/29 19:08:07  bala
// Changed PDH Metadescription data to move to
// ODS Metaflagvalue
// Changed the updates and inserts to timestamp records to 'TimestampLastRun'
// instead of 'Timestamp Begin of day'
//
// Revision 1.3  2001/03/27 20:38:32  bala
// added log Keyword
//
//***************************************************************************************************

import java.io.*;
import java.util.*;
import java.util.Date;
import java.text.*;
import java.sql.*;
import COM.ibm.opicmpdh.middleware.*;

/**
 *  Description of the Class
 *
 *@author
 *@created    September 27, 2002
 *@Changed    September 25, 2002
 */
public class OdsNetChangeShell {

  /**
   * Automatically generated constructor for utility class
   */
  private OdsNetChangeShell() {
  }

  /**
   *  Description of the Method
   *
   *@param  argv  Description of the Parameter
   */
  public static void main(String argv[]) {
    ODSNetChangeEngine getData = null;
    System.out.println(
        "************Starting ODSNetChangeEngine.****************");
    getData = new ODSNetChangeEngine();
    //System.out.println("getData" + getData);
  }

}

/**
 *  Description of the Class
 *
 *@author     Administrator
 *@created    September 25, 2002
 */
class ODSNetChangeEngine {

  /** Automatically generated javadoc for: DEF_PDH_COL_SIZE */
  private static final int DEF_PDH_COL_SIZE = 32;
  /** Automatically generated javadoc for: MAX_COL_SIZE */
  private static final int MAX_ODS_COL_SIZE = 254;
  /** Automatically generated javadoc for: DEF_COL_SIZE */
  private static final int DEF_ODS_COL_SIZE = 64;
  private String strEnterprise;
  private String struidPDH;
  private String strpwdPDH;
  private String strSchemaPDH;
  private String struidODS;
  private String strpwdODS;
  private String strSchemaODS;
  private String strurlPDH;
  private String strurlODS;
  private String strNLSOds;

  private String strTimestampMode;
  private int iMaxRowUpdateCount;

  private String strBlobAttExcludeList ="AFPDGRESULT,AFPDGVIEW,ALCATALOGMGREDCATLGPUBC1,ALCATALOGMGRENTRYPOINT1,ALCATALOGMGRSRDCATLGPUB1,ALMANAGEREDITFCTRANSACTIONC1,ALMANAGEREDITPRODSTRUCT1,ALMANAGERENTRYPOINT1,ALMANAGERSRDFCTRANSACTION1,ALMANAGERSRDFEATURE1,ALMANAGERSRDPRODSTRUCT1,ALPOWERUSEREDITAVAILC1,ALPOWERUSEREDITFEATURE1,ALPOWERUSEREDITFEATUREC1,ALPOWERUSEREDITLSEOC1,ALPOWERUSEREDITMMODEL1,ALPOWERUSEREDITMODELC1,ALPOWERUSEREDITPRODSTRUCT1,ALPOWERUSEREDITWWSEOC1,ALPOWERUSERENTRYPOINT1,ALPOWERUSERNAVANNAVAIL1,ALPOWERUSERNAVFCVE1,ALPOWERUSERNAVMODELSOLVE1,ALPOWERUSERNAVMODELVE1,ALPOWERUSERNAVMODELVE21,ALPOWERUSERNAVMTRFC1,ALPOWERUSERNAVPRODSTRUCTVE1,ALPOWERUSERNAVPROJWWSEO1,ALPOWERUSERNAVSECMENU1,ALPOWERUSERNAVSWMODELVE31,ALPOWERUSERNAVWGANN1,ALPOWERUSERNAVWGBOM1,ALPOWERUSERNAVWGFEATURE1,ALPOWERUSERNAVWGHWMODEL1,ALPOWERUSERNAVWGMACHTYPE1,ALPOWERUSERNAVWGMODEL1,ALPOWERUSERNAVWGMODELCOFCAT1,ALPOWERUSERNAVWGPROJ1,ALPOWERUSERNAVWGSWMODEL1,ALPOWERUSERNAVWUAVAIL121,ALPOWERUSERNAVWUAVAILSWPR1,ALPOWERUSERNAVWULSEO1,ALPOWERUSERNAVWUSWPRODSTRUCT1,ALPOWERUSERNAVWWSEODETVE1,ALPOWERUSERNAVWWSEOLSEO1,ALPOWERUSERNAVWWSEOMKTGVE1,ALPOWERUSERNAVWWSEOVE1,ALPOWERUSERSRDAVAIL61,ALPOWERUSERSRDFEATURE1,ALPOWERUSERSRDLSEO111,ALPOWERUSERSRDLSEO31,ALPOWERUSERSRDMACHTYPE11,ALPOWERUSERSRDMODEL41,ALPOWERUSERSRDPRODSTRUCT031,ALPOWERUSERSRDSVC1,ALPOWERUSERSRDWWSEO51,ALPWRVIEWENTRYPOINT1,ALPWRVIEWNAVMODELSOLVE1,ALPWRVIEWSRDMODEL41,ALVIEWERENTRYPOINT1,BUIMUI,DGBLOB,EGCATALOGMGREDIT,EGCATALOGMGRNAVIGATE,EGCATALOGMGRSEARCH,EGMANAGEREDIT,EGMANAGERNAVIGATE,EGMANAGERSEARCH,EGPOWERUSEREDIT,EGPOWERUSEREXTRACT,EGPOWERUSERNAVIGATE,EGPOWERUSERSEARCH,EGPWRVIEWEDIT,EGPWRVIEWEXTRACT,EGPWRVIEWNAVIGATE,EGPWRVIEWSEARCH,EGVIEWERNAVIGATE,EGXLATEADMEDIT,ELCATALOGMGRENTRYPOINT,ELMANAGERENTRYPOINT,ELMANAGERNAVFCVE,ELPOWERUSEREPIMSLSEOBUNDLEVE,ELPOWERUSEREPIMSLSEOVE,ELPOWERUSEREXRPT3LSEO1,ELPOWERUSEREXRPT3WWSEO1,ELPOWERUSEREXTCOMPATABR01,ELPOWERUSEREXTTECHCOMPMAINT1,ELPOWERUSERENTRYPOINT,ELPOWERUSERNAVANNAVAIL,ELPOWERUSERNAVFCVE,ELPOWERUSERNAVMODELSOLVE,ELPOWERUSERNAVMODELVE,ELPOWERUSERNAVMODELVE2,ELPOWERUSERNAVMTRFC,ELPOWERUSERNAVPRODSTRUCTVE,ELPOWERUSERNAVPROJWWSEO,ELPOWERUSERNAVSECMENU,ELPOWERUSERNAVSWMODELVE3,ELPOWERUSERNAVWGANN,ELPOWERUSERNAVWGBOM,ELPOWERUSERNAVWGFEATURE,ELPOWERUSERNAVWGHWMODEL,ELPOWERUSERNAVWGMACHTYPE,ELPOWERUSERNAVWGMODEL,ELPOWERUSERNAVWGMODELCOFCAT,ELPOWERUSERNAVWGPROJ,ELPOWERUSERNAVWGSWMODEL,ELPOWERUSERNAVWUAVAIL12,ELPOWERUSERNAVWUAVAILSWPR,ELPOWERUSERNAVWULSEO,ELPOWERUSERNAVWUSWPRODSTRUCT,ELPOWERUSERNAVWWSEODETVE,ELPOWERUSERNAVWWSEOLSEO,ELPOWERUSERNAVWWSEOMKTGVE,ELPOWERUSERNAVWWSEOVE,ELPOWERUSERTMFBOMCRXREF1,ELPOWERUSERTMFBOMCRXREF2,ELPOWERUSERWWPRTLSEOVE1,ELPOWERUSERDUMMY,ELPOWERUSERNULL,ELPWRVIEWENTRYPOINT,ELPWRVIEWMODSUPPSRPT,ELPWRVIEWNAVMODELSOLVE,ELPWRVIEWNAVMODELVE2,ELPWRVIEWNAVPRODSTRUCTVE,ELPWRVIEWNAVWGMODEL,ELPWRVIEWTMFBOMCRXREF1,ELPWRVIEWTMFBOMCRXREF2,ELPWRVIEWDUMMY,ELVIEWERENTRYPOINT,JUIBUI,PRUSERPREFERENCES,XLETSPACKAGE,XLMETSPACKAGE,XLMPDHPACKAGE,XLPDHPACKAGE,";

  // Database Connection

  private Connection conPDH;
  private Connection conODS;

  private String strRunMode;

  private boolean bIncludeMDRows = false;

  // Assume a Dry Run ...
  private boolean blnLive = false;

  //Date Time Cast we use to print in the log entries
  private final SimpleDateFormat c_sdfTimestamp =
      new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS000");
  private SimpleDateFormat fmtTimeStamp = null;
  // System control dates.
  private String strTimeStampNow = "";
  private String strTimeStampBeginOfDay = "";

  // PDH symbolic link that equals FOREVER
  private final String strTimeStampForever = "9999-12-31-00.00.00.000000";
  private String strTimeStampLastRun = "";

  // Query Statement Constants.
  final static int ODS_GET_LAST_RUN_TIME = 0;
  final static int PDH_GET_COLUMN_LENGTH_PQ = 1;
  final static int PDH_GET_ENTITY_KEY_CHANGES_PQ = 2;
  final static int PDH_GET_RELATOR_KEY_CHANGES_PQ = 3;
  final static int PDH_GET_META_ENTITIES_ATTRIBUTES = 4;
  final static int PDH_GET_RELATOR_SNAPSHOT_PQ = 5;
  final static int PDH_GET_ENTITY_SNAPSHOT_PQ = 6;
  final static int PDH_GET_MD_UFTEXT_CHANGES_PQ = 7;
  final static int PDH_GET_RELATOR_CHANGES_PQ = 9;
  final static int PDH_GET_FLAG_CHANGES_PQ = 10;
  final static int PDH_GET_MD_CHANGES_PQ = 11;
  final static int PDH_GET_LONGTEXT_CHANGES_PQ = 12;
  final static int PDH_GET_BLOB_CHANGES_PQ = 13;
  final static int ODS_CHECK_MD_EXIST_PQ = 14;
  final static int ODS_CHECK_FLAG_EXIST_PQ = 15;
  final static int ODS_CHECK_LONGTEXT_EXIST_PQ = 16;
  final static int ODS_CHECK_BLOB_EXIST_PQ = 17;
  final static int ODS_CHECK_RELATOR_EXIST_PQ = 18;
  final static int PDH_GET_MD_ENTITY_CHANGES_PQ = 19;
  final static int PDH_GET_INCLUDE_MD_UF_ENTITIES_PQ = 20;
  final static int PDH_GET_NEW_AND_OLD_MDLONGTEXT = 21;
  final static int PDH_GET_FLAG_ENTITY_INSTANCE = 22;
  final static int PDH_GET_PDH_DB_TIMESTAMP = 23;

  //Table Identifier Flag constants
  final static int ISFLAG = 50;
  final static int ISBLOB = 51;
  final static int ISLONGTEXT = 52;
  final static int ISRELATOR = 53;
  final static int ISMDTABLE = 54;

  //Table Update Statements constants
  final static int ODS_UPDATE_BLOB_PQ = 30;
  final static int ODS_UPDATE_FLAG_PQ = 31;
  final static int ODS_UPDATE_LONGTEXT_PQ = 32;
  final static int ODS_UPDATE_RELATOR_PQ = 33;
  final static int ODS_UPDATE_MD_PQ = 34;

  //Table Insert Statement Constants
  final static int ODS_INSERT_BLOB_PQ = 40;
  final static int ODS_INSERT_FLAG_PQ = 41;
  final static int ODS_INSERT_LONGTEXT_PQ = 42;
  final static int ODS_INSERT_RELATOR_PQ = 43;
  final static int ODS_INSERT_MD_PQ = 44;

  //Table DELETE Statement Constants
  final static int ODS_DELETE_BLOB_PQ = 60;
  final static int ODS_DELETE_FLAG_PQ = 61;
  final static int ODS_DELETE_LONGTEXT_PQ = 62;
  final static int ODS_DELETE_RELATOR_PQ = 63;
  final static int ODS_DELETE_MD_PQ = 64;

  //ODS column conversion factor for MultipleLang Support
  private int ODS_COL_CONV_FACTOR = 3;

  private // Hashtable to store the column name and size of ods tables
      Hashtable hODSTableDef = new Hashtable();

  /**
   * Automatically generated javadoc for: TWENTYEIGHT
   */
  private final static int TWENTYEIGHT = 28;
  /**
   * Automatically generated javadoc for: TWENTYSEVEN
   */
  private final static int TWENTYSEVEN = 27;
  /**
   * Automatically generated javadoc for: TWENTYSIX
   */
  private final static int TWENTYSIX = 26;
  /**
   * Automatically generated javadoc for: TWENTYFIVE
   */
  private final static int TWENTYFIVE = 25;
  /**
   * Automatically generated javadoc for: TWENTYFOUR
   */
  private final static int TWENTYFOUR = 24;
  /**
   * Automatically generated javadoc for: TWENTYTHREE
   */
  private final static int TWENTYTHREE = 23;
  /**
   * Automatically generated javadoc for: TWENTYTWO
   */
  private final static int TWENTYTWO = 22;
  /**
   * Automatically generated javadoc for: TWENTYONE
   */
  private final static int TWENTYONE = 21;
  /**
   * Automatically generated javadoc for: TWENTY
   */
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

  /*
   *  ***********************************************************************************
   *  / Main contructor
   */
  /**
   *  Constructor for the ODSNetChangeEngine object
   */
  ODSNetChangeEngine() {

    //Initialize the class variables for the run
    DateFormat df = null;
    SimpleDateFormat fmtDate = null;
    String strDayToday = null;
    readPropertyFile();

    ODS_COL_CONV_FACTOR = strNLSOds.equals("ALL") ? 3 : 1;
    //Provide for English only stuff

    this.strEnterprise = strEnterprise;
    this.struidPDH = struidPDH;
    this.strpwdPDH = strpwdPDH;
    this.struidODS = struidODS;
    this.strpwdODS = strpwdODS;
    this.strurlPDH = strurlPDH;
    this.strurlODS = strurlODS;
    this.strSchemaPDH = strSchemaPDH;
    this.strSchemaODS = strSchemaODS;
    this.strTimestampMode = strTimestampMode;
    this.iMaxRowUpdateCount = iMaxRowUpdateCount;
    this.conODS = null;
    this.conPDH = null;

    // find out if we are running live or not
    printOK("ODSNetChangeEngine: Current Run Level is :" + this.getVersion());

    if (strRunMode.equals("LIVE")) {
      this.blnLive = true;
      printOK("ODSNetChangeEngine:We are Live... Anything Goes!");
    }
    else {
      printOK("ODSNetChangeEngine:This is a Dry Run... Lets Have Some Fun.");
    }


    // Execute the Update ...

    try {

      Class.forName("COM.ibm.db2.jdbc.app.DB2Driver").newInstance();

      updateODS();

    }
    catch (Exception ex) {

      printErr("VeryBadERROR:" + ex.getMessage());
      ex.printStackTrace();

      StringWriter writer = new StringWriter();
      ex.printStackTrace(new PrintWriter(writer));
      String x = writer.toString();
      System.out.println("x: " + x);
      System.exit(1);
    }
  }

  private void reconnectPDH() {
    if (conPDH != null) {
      try {
        conPDH.commit();
        conPDH.close();
        conPDH = null;
      }
      catch (SQLException ex) {
        ex.printStackTrace();
      }
    }
    try {

      conPDH = DriverManager.getConnection(strurlPDH, struidPDH, strpwdPDH);
    }
    catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  private void reconnectODS() {
    if (conODS != null) {
      try {
        conODS.commit();
        conODS.close();
        conODS = null;
      }
      catch (SQLException ex) {
        ex.printStackTrace();
      }
    }

    try {

      conODS = DriverManager.getConnection(strurlODS, struidODS, strpwdODS);
    }
    catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  // --------------------------------------------
  // This routine does everything....
  // --------------------------------------------
  /**
   *  Description of the Method
   *
   *@exception  SQLException  Description of the Exception
   */
  private void updateODS() throws SQLException {

    Vector vEntities = new Vector();
    PreparedStatement pqMainQuery = null;
    ResultSet rsMainQuery = null;

    conPDH = DriverManager.getConnection(strurlPDH, struidPDH, strpwdPDH);
    conODS = DriverManager.getConnection(strurlODS, struidODS, strpwdODS);

    conPDH.setTransactionIsolation(4);
    conPDH.setAutoCommit(true);


    // Set up all the date information ...

    strTimeStampLastRun = "";
    strTimeStampNow = getCurrentPDHTime();
    strTimeStampBeginOfDay= strTimeStampNow.substring(0,10) +  "-00.00.00.000000";
    // get the last run time .. this is hokey.. but it works for now

    strTimeStampLastRun = getLastRunTime();

    printOK("updateODS:Current PDH time is: \t" + strTimeStampNow);
    printOK("updateODS:Current PDH time BOF is: \t" + strTimeStampBeginOfDay);
    printOK("updateODS:Last Run Time Is: \t" + strTimeStampLastRun);

    printOK("updateODS:Starting Entity Vector Build.");

    try { // First ... Lets create a vector of All known Entities and Relators
      // we are suppossed to manage

      pqMainQuery =
          conPDH.prepareStatement(
              getQueryStatement(PDH_GET_META_ENTITIES_ATTRIBUTES));
      rsMainQuery = pqMainQuery.executeQuery();

      while (rsMainQuery.next()) {
        vEntities.addElement(rsMainQuery.getString(1).trim()); //EntityType
        vEntities.addElement(rsMainQuery.getString(2).trim()); //AttributeCode
        vEntities.addElement(rsMainQuery.getString(3).trim()); //ODSLength
        vEntities.addElement(rsMainQuery.getString(4).trim()); //TableSpace
        vEntities.addElement(rsMainQuery.getString(5).trim()); //IndexSpace
      }
      printOK("updateODS:Finished Entity Vector Build." + vEntities);

      if (rsMainQuery != null) {
        rsMainQuery.close();
        rsMainQuery = null;
      }
      if (pqMainQuery != null) {
        pqMainQuery.close();
        pqMainQuery = null;

      }

      scanEntityDef(vEntities);

      //Entities are processed, now scan the rest
      commitChangesToODSTables(ISFLAG);
      commitChangesToODSTables(ISBLOB);
      commitChangesToODSTables(ISLONGTEXT);
      commitChangesToODSTables(ISRELATOR);
      commitChangesToODSTables(ISMDTABLE);

      //And at the end set the new time to the timetable for the next run
      putODSRunTime();

    }
    catch (SQLException ex) {

      printErr("updateODS:*Error*" + ex.getMessage());
      System.exit(1);

    }

    // Dont forget to log the run time

  }

  /*
   *  -----------------------------------------------------------------------------------
   *  function scanEntityDef
   *  -----------------------
   *  This function will loop through the vector and build a vector of columns.. then
   *  it will call the alter TableDef method to check out the table
   *  -----------------------------------------------------------------------------------
   */
  /**
   *  Description of the Method
   *
   *@param  vEntityDetails    Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  private void scanEntityDef(Vector vEntityDetails) throws SQLException {

    String strTableName = "";
    String strAttributeName = "";
    String strAttributeLen = "";
    String strLastTableName = "";
    String strTableSpace = "";
    String strIndexSpace = "";

    Vector vTableColumns = new Vector();

    Enumeration e = vEntityDetails.elements();

    while (e.hasMoreElements()) {

      strTableName = (String) e.nextElement();
      strAttributeName = (String) e.nextElement();
      strAttributeLen = (String) e.nextElement();
      strTableSpace = (String) e.nextElement();
      strIndexSpace = (String) e.nextElement();

      if (strLastTableName.equals("")) {
        strLastTableName = strTableName;
      }
      // Process table on Table Name Change
      if (!strTableName.equals(strLastTableName)) {
        checkODSTableDef(strLastTableName, vTableColumns); // Process It
        strLastTableName = strTableName; // Reset TableName
        vTableColumns.removeAllElements(); // Remove all Column Defs
      }

      vTableColumns.addElement(strAttributeName);
      vTableColumns.addElement(strAttributeLen);
      vTableColumns.addElement(strTableSpace);
      vTableColumns.addElement(strIndexSpace);

    }

    // Don't forget the last one here ...

    checkODSTableDef(strLastTableName, vTableColumns);

  } //End of function scanEntityDef

  /*
   *  ------------------------------------------------------------------------------------------------
   *  checkODSTableDef
   *  -----------------------
   *  This will check the ODS table definition against the list of vector columns passed as parameter
   *  If there is a difference in definition, it will apply the change (ie add a column).
   *  Then it will call another method (updateAllKeys) to apply the changes made for the entity in the PDH against the
   *  ODS table
   *  -------------------------------------------------------------------------------------------------
   */
  /**
   *  Description of the Method
   *
   *@param  strTableName      Description of the Parameter
   *@param  vPDHColumns       Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  private void checkODSTableDef(String strTableName, Vector vPDHColumns) throws
      SQLException {

    String strAlterStatement =
        "ALTER TABLE " + strSchemaODS + "." + strTableName;
    String strAttributeName = "";
    String strAttributeLen = "";
    String strColumns = "";
    String strTableSpace = "";
    String strIndexSpace = "";
    String[] strAFilter = {
        "TABLE"};
    Vector vODSColumns = new Vector();
    ResultSet rs = null;
    ResultSet rscols = null;
    boolean blnfound = false;
    Statement stmtAlterTable = null;

    // Get some meta data for the ODS
    DatabaseMetaData dbMetaODS = conODS.getMetaData();
    printOK("checkODSTableDef:" + blnLive + ":Entering:ET:" + strTableName);

    // Perform a table presence check in the ODS

    printOK("checkODSTableDef:CheckingODSExistance:ET:" + strTableName);

    try {
      rs = dbMetaODS.getTables(null, strSchemaODS, strTableName, strAFilter);

      if (rs.next()) {
        blnfound = true;
      }
    }
    catch (SQLException rse) {
      throw rse;
    }
    finally {
      try {
        rs.close();
        rs = null;

      }
      catch (SQLException ex1) {
        ex1.printStackTrace();

        throw ex1;
      }
    }

    if (blnfound) {

      printOK("checkODSTableDef:Found Table:ET:" + strTableName);

      // Lets get the columns
      try {
        printOK("checkODSTableDef:Cross Checking Columns:ET:" + strTableName);
        rscols = dbMetaODS.getColumns(null, "%", strTableName, "%");
        while (rscols.next()) {
          vODSColumns.addElement(rscols.getString(4).trim());
        }
      }
      catch (SQLException rse) {
        throw rse;
      }
      finally {
        try {
          if (rscols != null) {
            rscols.close();
            rscols = null;

          }

        }
        catch (SQLException ex1) {
          ex1.printStackTrace();

          throw ex1;
        }
      }

      for (int myi = 0; myi <= vPDHColumns.size() - 1; ) {

        strAttributeName = (String) vPDHColumns.elementAt(myi);
        myi++;
        strAttributeLen = (String) vPDHColumns.elementAt(myi);
        myi++;
        strTableSpace = (String) vPDHColumns.elementAt(myi);
        myi++;
        strIndexSpace = (String) vPDHColumns.elementAt(myi);
        myi++;

        if (!vODSColumns.contains(strAttributeName)) {

          int iColSize = DEF_ODS_COL_SIZE;
          try {
            iColSize = Integer.valueOf(strAttributeLen).intValue();
          }
          catch (java.lang.NumberFormatException n) {
            printErr(n.getMessage());
            printErr(
                "checkODSTableDef:ERROR:Expected number got character!:Column: "
                + strAttributeName
                + " :Length: "
                + strAttributeLen
                + ". Defaulting to 64 characters.");
          }

          printOK(
              "checkODSTableDef:New column found:ET:"
              + strTableName
              + ":ATTNAME:"
              + strAttributeName);

          if (iColSize == 0) {
            printErr(
                "checkODSTableDef:ERROR!:ATTRIBUTE LENGTH NOT DEFINED IN METAATTRIBUTE FOR ENTITY :"
                + strTableName
                + "ATTRIBUTE:"
                + strAttributeName
                + " :DEFAULTING TO 64 CHARACTERS");
            iColSize = DEF_ODS_COL_SIZE;
          }

          strColumns =
              strColumns
              + " ADD COLUMN "
              + strAttributeName
              + " VARCHAR("
              + Integer.toString(
                  (iColSize * ODS_COL_CONV_FACTOR < MAX_ODS_COL_SIZE
                   ? iColSize * ODS_COL_CONV_FACTOR
                   : MAX_ODS_COL_SIZE))
              + ")";
        }
      }

      if (strColumns.equals("")) {
        printOK("checkODSTableDef:Cross Check Comes Clean:ET:" + strTableName);
      }
      else {
        printOK("checkODSTableDef:Altering:ET:" + strTableName);
        try {
          stmtAlterTable = conODS.createStatement();
          strAlterStatement = strAlterStatement + " " + strColumns;
          if (blnLive) {
            stmtAlterTable.executeUpdate(strAlterStatement);
          }
        }
        catch (SQLException ex) {
          printErr(
              "checkODSTableDef:*error* with strAlterStatement:"
              + ex.getMessage());
          System.exit(0);

        }
        finally {
          try {
            if (stmtAlterTable != null) {
              stmtAlterTable.close();
              stmtAlterTable = null;
            }
          }
          catch (SQLException ex1) {
            ex1.printStackTrace();
          }
        }
      }

      // Check for any updates to this table
      try {
        processAllKeys(strTableName);
      }
      catch (SQLException e) {
        e.printStackTrace();
        System.exit( -1);
      }

    }
    else {

      printOK("checkODSTableDef:NewEntityFound:ET:" + strTableName);
      createTable(strTableName, vPDHColumns);

    }

    dbMetaODS = null;
    printOK("checkODSTableDef:Leaving:ET:" + strTableName);

  }

  /**
   *  Run the query to find out changes in the PDH
   *
   *@param  strTableName      Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  private void processAllKeys(String strTableName) throws SQLException {

    String strSQL = "";
    String strCGOSSEO = "SEOCGOSSEO";
    String strDGENTITY = "DGENTITY";
    String strPDHUPDATE = "PDHUPDATE";
    String strPDHUPDATEACT = "PDHUPDATEACT";
    
    ResultSet rs = null;
    PreparedStatement ps = null;
    ReturnDataResultSet rdrs1 = null;
    ReturnDataResultSet rdrs2 = null;
    bIncludeMDRows = false;

    try {
      strSQL = getQueryStatement(PDH_GET_MD_ENTITY_CHANGES_PQ);
      ps = conPDH.prepareStatement(strSQL);
      ps.setString(1, strTableName);
      printOK(
          "processAllKeys:Started Scan of MetaDescription for :ET:"
          + strTableName);
      rs = ps.executeQuery();
      printOK(
          "processAllKeys:Finished Scan of MetaDescription for :ET:"
          + strTableName);
      if (rs.next()) {
        bIncludeMDRows = true;
        printOK(
            "processAllKeys:Changes Detected in MetaDescription!...This will take a while!");
      }
      conPDH.commit();

    }
    catch (SQLException rse) {
      throw rse;
    }
    finally {
      try {
        if (rs != null) {
          rs.close();
          rs = null;

        }
        if (ps != null) {
          ps.close();
          ps = null;

        }

      }
      catch (SQLException ex1) {
        ex1.printStackTrace();
        throw ex1;
      }

    }
    reconnectODS();
    reconnectPDH();

    try {
     if (strTableName.equals(strCGOSSEO) || strTableName.equals(strDGENTITY) || strTableName.equals(strPDHUPDATE) || strTableName.equals(strPDHUPDATEACT))
     { 
      printOK("processAllKeys:Skipping Scan:ET:" + strTableName);
     } else {
      strSQL = getQueryStatement(PDH_GET_ENTITY_KEY_CHANGES_PQ);
      ps = conPDH.prepareStatement(strSQL);
      ps.setString(1, strTableName);
      ps.setString(2, strTableName);
      ps.setString(3, strTableName);
      ps.setString(4, strTableName);
      ps.setString(5, strTableName);
      ps.setString(6, strTableName);
      ps.setString(7, strTableName);
      ps.setString(8, strTableName);
      printOK("processAllKeys:Started Scan:ET:" + strTableName);
      rs = ps.executeQuery();
      rdrs1 = new ReturnDataResultSet(rs);
      conPDH.commit();
     } 
    }
    catch (SQLException rse1) {
      rse1.printStackTrace();
      throw rse1;
    }
    finally {
      try {
        if (rs != null) {
          rs.close();
          rs = null;

        }
        if (ps != null) {
          ps.close();
          ps = null;

        }

      }
      catch (SQLException ex1) {
        ex1.printStackTrace();
        throw ex1;
      }

    }

    if (strTableName.equals(strCGOSSEO) || strTableName.equals(strDGENTITY) || strTableName.equals(strPDHUPDATE) || strTableName.equals(strPDHUPDATEACT))
    {
    	printOK("processAllKeys:Exiting:ET:" + strTableName);
    } else {
    	printOK("processAllKeys:Finished Scan:ET:" + strTableName);
    	updateAllKeys(rdrs1, strTableName);
    }

    rdrs1 = null;

    if (bIncludeMDRows) {
      printOK("Including PDH_GET_NEW_AND_OLD_MDLONGTEXT");
      strSQL = getQueryStatement(PDH_GET_NEW_AND_OLD_MDLONGTEXT);
      try {
        ps = conPDH.prepareStatement(strSQL);
        ps.setString(1, strTableName);
      }
      catch (SQLException e) {
        e.printStackTrace();
      }
      printOK("processAllKeys:Started Special Scan:ET:" + strTableName);
      rs = ps.executeQuery();
      rdrs2 = new ReturnDataResultSet(rs);
      conPDH.commit();
      try {
        if (rs != null) {
          rs.close();
          rs = null;

        }
        if (ps != null) {
          ps.close();
          ps = null;

        }
      }
      catch (SQLException e1) {
        e1.printStackTrace();
        throw e1;
      }
      printOK("processAllKeys:Finished Special Scan:ET:" + strTableName);
      updateUFColumnsinODSTable(rdrs2, strTableName);
      rdrs2 = null;
    }
  } //End processAllKeys

  /*
   *  Begin Method updateAllKeys
   *  ---------------------------------------------------------------------------------------------------------------
   *
   *  This method will update all the MIRROR Table Keys (Perform adds and Deletes)
   *  Also, for each entity type it processes, it will get the definition of the entity
   *  (read table) in the ODS and get its column definition for comparison later on
   *  ---------------------------------------------------------------------------------------------------------------
   */
  /**
   *  Description of the Method
   *
   *@param  rdrs              Description of the Parameter
   *@param  strTableName      Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  private void updateAllKeys(ReturnDataResultSet rdrs, String strTableName) throws
      SQLException {

    String strValFrom = "";
    String strValTo = "";
    String strEffFrom = "";
    String strEffTo = "";
    int iEID = 0;
    int iNLSID = 0;

    String strLastValFrom = "";
    String strLastValTo = "";
    String strLastEffFrom = "";
    String strLastEffTo = "";
    int iLastEID = 0;
    int iLastNLSID = 0;
    boolean blnFirstPass = true;

    reconnectODS();
    reconnectPDH();

    //now get the meta definition of the entitytype-table in the ods
    DatabaseMetaData meta = conODS.getMetaData();

    ResultSet rsODSTableDef = meta.getColumns(null, "%", strTableName, "%");
    printOK("updateAllKeys:" + blnLive + ":Entering:ET:" + strTableName);

    //But first clear all of the definitions stuff out
    getHODSTableDef().clear();

    try {

      while (rsODSTableDef.next()) {
        //Populate the hash table with the Column name and size pairs
        getHODSTableDef().put(
            (rsODSTableDef.getString(4)).trim(),
            new Integer(rsODSTableDef.getInt(7)));
        //printOK("Putting Column "+(rsODSTableDef.getString(4)).trim()+" with Size:"+rsODSTableDef.getInt(7));

      }

      for (int x = 0; x < rdrs.size(); x++) {

        iEID = rdrs.getColumnInt(x, 0);
        iNLSID = rdrs.getColumnInt(x, 1);
        strValFrom = rdrs.getColumn(x, 2).trim();
        strValTo = rdrs.getColumn(x, 3).trim();
        strEffFrom = rdrs.getColumn(x, 4).trim();
        strEffTo = rdrs.getColumn(x, 5).trim();

        if (blnFirstPass) {
          iLastEID = iEID;
          iLastNLSID = iNLSID;
          strLastValFrom = strValFrom;
          strLastValTo = strValTo;
          strLastEffFrom = strEffFrom;
          strLastEffTo = strEffTo;
          blnFirstPass = false;
        }

        // Check for an EntityType/NLSID Break in the stream
        if (iEID != iLastEID || iNLSID != iLastNLSID) {

          printOK(
              "updateAllKeys:Entity Churn Found:ET:"
              + strTableName
              + ":EID:"
              + iLastEID
              + ":NLSID:"
              + iLastNLSID
              + ":ValFrom:"
              + strLastValFrom
              + ":ValTo:"
              + strLastValTo
              + ":EffFrom:"
              + strLastEffFrom
              + ":EffTo:"
              + strLastEffTo);

          if (strLastValTo.compareTo(strTimeStampNow) <= 0
              || strLastEffTo.compareTo(strTimeStampNow) <= 0) {
            /*
             *  Is this really to be deleted? Lets check the entity again
             */
            if (!entityExistsInPDH(strTableName, iLastEID)) {
              printOK(
                  "DELETINGENTITY:ET:#1"
                  + strTableName
                  + ":EID:"
                  + iLastEID
                  + ":strLastValFrom:"
                  + strLastValFrom
                  + ":strLastValTo:"
                  + strLastValTo
                  + ":strEffFrom:"
                  + strLastEffFrom
                  + ":strLastEffTo:"
                  + strLastEffTo
                  + ":strTimeStampNow:"
                  + strTimeStampNow
                  + ":strLastValTo.compareTo(strTimeStampNow)<=0:"
                  + (strLastValTo.compareTo(strTimeStampNow) <= 0)
                  + ":strLastEffTo.compareTo(strTimeStampNow)<=0:"
                  + (strLastEffTo.compareTo(strTimeStampNow) <= 0));
              retireEntity(strTableName, iLastEID, iLastNLSID);
              logDelete(
                  strTableName,
                  iLastEID,
                  iLastNLSID,
                  "ENTITY",
                  "",
                  "",
                  null,
                  0,
                  null,
                  0);
              //Append to log table
            }
            else {
              //This is not a delete...so lets refresh the row
              if (!entityExistsInODS(strTableName, iLastEID, iLastNLSID)) {
                insertNewEntity(strTableName, iLastEID, iLastNLSID);
                refreshODSEntity(strTableName, iLastEID, iLastNLSID);
              }
              else {
                updateEntity(strTableName, iLastEID, iLastNLSID);
              }
            }
          }
          else if (!entityExistsInODS(strTableName, iLastEID, iLastNLSID)) {
            insertNewEntity(strTableName, iLastEID, iLastNLSID);
            //        printOK("Calling refresh Entity after insert");
            refreshODSEntity(strTableName, iLastEID, iLastNLSID);
          }
          else {
            updateEntity(strTableName, iLastEID, iLastNLSID);
          }
        }
        // Re-assign these

        iLastEID = iEID;
        iLastNLSID = iNLSID;
        strLastValFrom = strValFrom;
        strLastValTo = strValTo;
        strLastEffFrom = strEffFrom;
        strLastEffTo = strEffTo;
      }
      if (iLastEID != 0) {

        printOK(
            "updateAllKeys:Entity Churn Found:ET:"
            + strTableName
            + ":EID:"
            + iLastEID
            + ":NLSID:"
            + iLastNLSID
            + ":ValFrom:"
            + strLastValFrom
            + ":ValTo:"
            + strLastValTo
            + ":EffFrom:"
            + strLastEffFrom
            + ":EffTo:"
            + strLastEffTo);

        if (strLastValTo.compareTo(strTimeStampNow) <= 0
            || strLastEffTo.compareTo(strTimeStampNow) <= 0) {
          if (!entityExistsInPDH(strTableName, iLastEID)) {
            printOK(
                "DELETINGENTITY:ET:#2"
                + strTableName
                + ":EID:"
                + iLastEID
                + ":strLastValFrom:"
                + strLastValFrom
                + ":strLastValTo:"
                + strLastValTo
                + ":strEffFrom:"
                + strLastEffFrom
                + ":strLastEffTo:"
                + strLastEffTo
                + ":strTimeStampNow:"
                + strTimeStampNow
                + ":strLastValTo.compareTo(strTimeStampNow)<=0:"
                + (strLastValTo.compareTo(strTimeStampNow) <= 0)
                + ":strLastEffTo.compareTo(strTimeStampNow)<=0:"
                + (strLastEffTo.compareTo(strTimeStampNow) <= 0));
            retireEntity(strTableName, iLastEID, iLastNLSID);
            logDelete(
                strTableName,
                iLastEID,
                iLastNLSID,
                "ENTITY",
                "",
                "",
                null,
                0,
                null,
                0);
            //Append to log table
          }
          else {
            //This is not a delete...so lets refresh the row
            if (!entityExistsInODS(strTableName, iLastEID, iLastNLSID)) {
              insertNewEntity(strTableName, iLastEID, iLastNLSID);
              refreshODSEntity(strTableName, iLastEID, iLastNLSID);
            }
            else {
              updateEntity(strTableName, iLastEID, iLastNLSID);
            }
          }
        }
        else if (!entityExistsInODS(strTableName, iLastEID, iLastNLSID)) {
          insertNewEntity(strTableName, iLastEID, iLastNLSID);
          //        printOK("Calling refresh Entity after insert");
          refreshODSEntity(strTableName, iLastEID, iLastNLSID);
        }
        else {
          updateEntity(strTableName, iLastEID, iLastNLSID);
        }
      }
    }
    catch (SQLException se) {
      throw se;
    }
    finally {
      try {
        if (rsODSTableDef != null) {
          rsODSTableDef.close();
          rsODSTableDef = null;
        }
      }
      catch (SQLException ex1) {
        ex1.printStackTrace();
        throw ex1;
      }
    }

    printOK("updateAllKeys:Exiting:ET:" + strTableName);
  }

  /**
   *  updateUFColumnsinODSTable --------------------------- Given a
   *  returndataresult set of metadescription changes
   *
   *@param  _rdrs             Description of the Parameter
   *@param  _strTableName     Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */

  private void updateUFColumnsinODSTable(
      ReturnDataResultSet _rdrs,
      String _strTableName) throws SQLException {
    String strColName = null;
    String strLastColName = null;
    int iNLSid = 0;
    int ilastNLSid = 0;
    String strNewText = null;
    String strOldText = null;
    String strFlagCode = null;
    String strLastFlagCode = null;
    String strLastNewText = null;
    String strLastOldText = null;
    //ENTITYTYPE
    //ATTRIBUTECODE
    //NLSID
    //FLAGVALUE
    //FLAGDESC
    //OLDDESC
    //VALFROM
    //VALTO
    for (int i = 0; i < _rdrs.size(); i++) {

      //read the columns into vars using the column width obtained from the table meta
      strColName = _rdrs.getColumn(i, 1).trim();
      iNLSid = _rdrs.getColumnInt(i, 2);
      strFlagCode = _rdrs.getColumn(i, 3).trim();
      strNewText = _rdrs.getColumn(i, 4).trim();
      strOldText = _rdrs.getColumn(i, 5).trim();

      printOK(
          "strColName:"
          + strColName
          + "iNLSid:"
          + iNLSid
          + "strFlagCode:"
          + strFlagCode
          + "strNewText:"
          + strNewText
          + "strOldText:"
          + strOldText);
      if (i == 0) {
        //Means that this is the first time in, set the break variablesmaybe
        printOK("Setting the break vars");
        strLastFlagCode = strFlagCode;
        ilastNLSid = iNLSid;
        strLastColName = strColName;
        strLastNewText = strNewText;
        strLastOldText = strOldText;
      }

      //look for a change in key (attributecode/nlsid/flagcode/value)
      if ( (!strLastColName.equals(strColName)
            || ilastNLSid != iNLSid
            || !strLastFlagCode.equals(strFlagCode))) {
        updateODSColumn(
            _strTableName,
            strLastColName,
            strLastFlagCode,
            strLastOldText,
            strLastNewText,
            ilastNLSid);
        printOK("Key has Changed");
        //Setting the break vars back
        strLastFlagCode = strFlagCode;
        ilastNLSid = iNLSid;
        strLastColName = strColName;
        strLastNewText = strNewText;
        strLastOldText = strOldText;
      }
      else {
        //Step thru every change (check if values have changed)
        //this prevents the situation where the update was partially succesful
        // and the subsequent changes were made to metadescription text
        if (!strOldText.equals(strLastOldText)
            || !strNewText.equals(strLastNewText)) {
          updateODSColumn(
              _strTableName,
              strLastColName,
              strLastFlagCode,
              strLastOldText,
              strLastNewText,
              ilastNLSid);
          strLastNewText = strNewText;
          strLastOldText = strOldText;
        }

      }
    } //End For
    if (_rdrs.size() > 0) {
      printOK("End of Result set update");
      updateODSColumn(
          _strTableName,
          strColName,
          strFlagCode,
          strLastOldText,
          strLastNewText,
          iNLSid);
      //End of result set break
    }
  } //End of method updateUFColumnsinODSTable

  /*
   *
   */
  /**
   *  Description of the Method
   *
   *@param  _strTableName     Description of the Parameter
   *@param  _strColName       Description of the Parameter
   *@param  _strFlagCode      Description of the Parameter
   *@param  _strOldText       Description of the Parameter
   *@param  _strNewText       Description of the Parameter
   *@param  _iNLSid           Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  private void updateODSColumn(
      String _strTableName,
      String _strColName,
      String _strFlagCode,
      String _strOldText,
      String _strNewText,
      int _iNLSid) throws SQLException {

    int intColSize = 0;
    String strUpdateSQL = null;
    int iRowsUpdated = 0;
    String strGetFlags = null;
    String strEntityList = "0";
    int itype = 0;
    PreparedStatement ps = null;
    PreparedStatement ps1 = null;
    ResultSet rs = null;
    int i = 0;
    int iExtractCount = 0;

    // set up column size and truncate if neccescary
    if (getHODSTableDef().get(_strColName) != null) {
      intColSize = ( (Integer) (getHODSTableDef().get(_strColName))).intValue();
    }

    if (intColSize < MAX_ODS_COL_SIZE) {
      //If the colsize = 254, let all the text move without truncation
      intColSize = intColSize / ODS_COL_CONV_FACTOR;
    }
    else {
      intColSize = MAX_ODS_COL_SIZE;
    }

    if (!_strOldText.equals("QUERYNULL")) {
      if (_strOldText.length() > intColSize) {
        printOK(
            "updateODSColumn:"
            + blnLive
            + ":TRUNCATE:OLD:"
            + intColSize
            + ":"
            + _strOldText);
        _strOldText = _strOldText.substring(0, intColSize);
      }
    }

    if (!_strNewText.equals("QUERYNULL")) {
      if (_strNewText.length() > intColSize) {
        printOK(
            "updateODSColumn:"
            + blnLive
            + ":TRUNCATE:NEW:"
            + intColSize
            + ":"
            + _strNewText);
        _strNewText = _strNewText.substring(0, intColSize);
      }
    }

    //Leave if they are the same
    if (_strOldText.equals(_strNewText)) {
      printOK(
          "updateODSColumn: Bypassing column update since truncated values of previous and current are same :OLD:"
          + _strOldText
          + ":NEW:"
          + _strNewText);
      return;
    }

    // OK .. something is differnt lets proceed

    if (_strOldText.equals("QUERYNULL")) {
      // This means the old value was not provided .. but the new one exists
      itype = 1;
    }
    else if (_strNewText.equals("QUERYNULL")) {
      // This means the old value was provided .. and the new one is null
      itype = 2;
    }
    else {
      // This means the old value was provided .. and the new one is provided
      itype = 3;
    }

    // Load and update based upon itype

    /*
     *  This means that we have to get the actual entity ID's from the
     *  flag table since we dont have previous value
     */
    printOK("updateODSColumn:Getting the list of flag instances");

    try {
      strGetFlags = getQueryStatement(PDH_GET_FLAG_ENTITY_INSTANCE);
      ps = conPDH.prepareStatement(strGetFlags);
      //System.out.println("strGetFlags: " + strGetFlags);
      ps.setString(1, strEnterprise);
      //System.out.println("strEnterprise: " + strEnterprise);
      ps.setString(2, _strTableName);
      //System.out.println("_strTableName: " + _strTableName);
      ps.setString(3, _strColName);
      //System.out.println("_strColName: " + _strColName);
      ps.setString(4, _strFlagCode);
      //System.out.println("_strFlagCode: " + _strFlagCode);
      rs = ps.executeQuery();
      //System.out.println("rs: " + rs);
      if (rs.next()) {
        strEntityList = rs.getString(1).trim();
        ; //set up the first one
      }
      i = 0;
      //System.out.println("processing loop" + i + "rs.getString(1).trim(): " + rs.getString(1).trim());
      while (rs.next()) {
        //System.out.println("strEntityList and i: " + strEntityList + "i: " + i + "iMaxRowUpdateCount: " + iMaxRowUpdateCount + "rs.getString(1).trim(): " + rs.getString(1).trim());
        strEntityList = strEntityList + "," + rs.getString(1).trim();
        //and then the rest
        i++;
        iExtractCount++;
        if (i > iMaxRowUpdateCount) {
          //Update every 1000 entities to prevent Db2 statement stack overflow
          i = 0;
          printOK(
              "updateODSColumn:Entity-Flag List extracted " + iExtractCount);

          if (itype == 2) {

            strUpdateSQL =
                "UPDATE "
                + strSchemaODS
                + "."
                + _strTableName
                + " SET "
                + _strColName
                + "= NULL, "
                + " VALFROM = "
                + (strTimestampMode.equals("TIMESTAMP")
                   ? "current timestamp "
                   : "'" + strTimeStampBeginOfDay + "'")
                + " WHERE "
                + _strColName
                + " = ? "
                + " AND ENTITYID IN ("
                + strEntityList
                + ") AND NLSID= "
                + Integer.toString(_iNLSid);

            ps = conODS.prepareStatement(strUpdateSQL);
            ps.setString(1, _strOldText);

            printOK(
                "updateODSColumn:Updating "
                + _strOldText
                + " :with: "
                + _strNewText
                + ":COLUMN:"
                + _strColName
                + ":NLS:"
                + _iNLSid);
            printOK(strUpdateSQL);

            if (blnLive) {
              iRowsUpdated = ps.executeUpdate();
            }

            printOK("updateODSColumn: " + iRowsUpdated + " rows updated");
            if (ps != null) {
              ps.close();
              ps = null;

            }
          } else {
            strUpdateSQL =
                "UPDATE "
                + strSchemaODS
                + "."
                + _strTableName
                + " SET "
                + _strColName
                + " = ?, "
                + " VALFROM = "
                + (strTimestampMode.equals("TIMESTAMP")
                   ? "current timestamp "
                   : "'" + strTimeStampBeginOfDay + "'")
                + " WHERE ENTITYID IN ("
                + strEntityList
                + ") AND NLSID = "
                + Integer.toString(_iNLSid);
            ps1 = conODS.prepareStatement(strUpdateSQL);
            ps1.setString(1, _strNewText);
            printOK(
                "updateODSColumn:Updating "
                + _strOldText
                + " :with: "
                + _strNewText
                + ":COLUMN:"
                + _strColName
                + ":NLS:"
                + _iNLSid);
            printOK(strUpdateSQL);

            if (blnLive) {
              iRowsUpdated = ps1.executeUpdate();
            }
            if (ps1 != null) {
              ps1.close();
              ps1 = null;

            }

            printOK("updateODSColumn: " + iRowsUpdated + " rows updated");

          }
          strEntityList = rs.getString(1).trim(); //begin the next one
        }

      } //end while
      //System.out.println("rs next complete");
    }
    catch (SQLException se) {
      printErr("updateODSColumn:" + se.getMessage());
      throw se;
    }
    finally {
      try {
        if (rs != null) {
          rs.close();
          rs = null;

        }
        if (ps != null) {
          ps.close();
          ps = null;

        }

        if (ps1 != null) {
          ps1.close();
          ps1 = null;
        }
      }
      catch (SQLException ex1) {
        ex1.printStackTrace();
        throw ex1;
      }
    }
    //System.out.println("updateODSColumn:Entity-Flag Total extracted" + iExtractCount);
    printOK("updateODSColumn:Entity-Flag Total extracted " + iExtractCount);
    try {
      if (itype == 2) {
        strUpdateSQL =
            "UPDATE "
            + strSchemaODS
            + "."
            + _strTableName
            + " SET "
            + _strColName
            + "= NULL, "
            + " VALFROM = "
            + (strTimestampMode.equals("TIMESTAMP")
               ? "current timestamp "
               : "'" + strTimeStampBeginOfDay + "'")
            + " WHERE ENTITYID IN ("
            + strEntityList
            + ") AND NLSID = "
            + Integer.toString(_iNLSid);

        ps = conODS.prepareStatement(strUpdateSQL);
        ps.setString(1, _strOldText);

        printOK(
            "updateODSColumn:Updating "
            + _strOldText
            + " :with: "
            + _strNewText
            + ":COLUMN:"
            + _strColName
            + ":NLS:"
            + _iNLSid);
        printOK(strUpdateSQL);

      } else {
        strUpdateSQL =
            "UPDATE "
            + strSchemaODS
            + "."
            + _strTableName
            + " SET "
            + _strColName
            + " = ?, "
            + " VALFROM = "
            + (strTimestampMode.equals("TIMESTAMP")
               ? "current timestamp "
               : "'" + strTimeStampBeginOfDay + "'")
            + " WHERE ENTITYID IN ("
            + strEntityList
            + ") AND NLSID = "
            + Integer.toString(_iNLSid);
        ps = conODS.prepareStatement(strUpdateSQL);
        ps.setString(1, _strNewText);
        printOK(
            "updateODSColumn:Updating "
            + _strOldText
            + " :with: "
            + _strNewText
            + ":COLUMN:"
            + _strColName
            + ":NLS:"
            + _iNLSid);
        printOK(strUpdateSQL);

      }
      if (blnLive) {
        iRowsUpdated = ps.executeUpdate();
      }

      printOK("updateODSColumn: " + iRowsUpdated + " rows updated");
    }
    catch (SQLException se) {
      printErr("updateODSColumn:" + se.getMessage());
      throw se;
    }
    finally {
      try {
        if (ps != null) {
          ps.close();
          ps = null;

        }

      }
      catch (SQLException ex1) {
        ex1.printStackTrace();
        throw ex1;
      }
    }

  }

  /**
   *  Description of the Method
   *
   *@param  _strEType         Description of the Parameter
   *@param  _iEid             Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  private boolean entityExistsInPDH(String _strEType, int _iEid) throws
      SQLException {
    String strNow = strTimeStampNow;
    String strSQL =
        " SELECT ENTITYID  "
        + " FROM "
        + strSchemaPDH
        + "."
        + "ENTITY "
        + " WHERE "
        + "   ENTERPRISE = '"
        + strEnterprise
        + "'"
        + "   AND ENTITYTYPE = '"
        + _strEType
        + "' "
        + "   AND ENTITYID = "
        + _iEid
        + "   AND VALTO >= '"
        + strNow
        + "'"
        + "   AND EFFTO >= '"
        + strNow
        + "'"
        + " FOR READ ONLY";

    boolean blnFound = false;

    Statement stmt = conPDH.createStatement();
    ResultSet myrs = stmt.executeQuery(strSQL);
    printOK("entityExistsInPDH: " + strSQL);
    try {
      if (myrs.next()) {
        blnFound = true;
      }
    }
    catch (SQLException se) {
      se.printStackTrace();
      throw se;
    }
    finally {
      try {
        if (myrs != null) {
          myrs.close();
          myrs = null;
        }
        if (stmt != null) {
          stmt.close();
          stmt = null;
        }
      }
      catch (SQLException ex1) {
        ex1.printStackTrace();
      }
    }

    return blnFound;
  }

  /*
   *  BEGIN ROUTINE entityExistsInODS
   *  --------------------------------------------------------------------------------------------------------------
   *
   *  This routine will check to see if a record for the given tableName already exists in the database.
   *  If so .. it will return true ... else false.
   *
   *  --------------------------------------------------------------------------------------------------------------
   */
  /**
   *  Description of the Method
   *
   *@param  strEntityType     Description of the Parameter
   *@param  iEID              Description of the Parameter
   *@param  iNLSID            Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  private boolean entityExistsInODS(String strEntityType, int iEID, int iNLSID) throws
      SQLException {

    String strSQL =
        " SELECT ENTITYID  "
        + " FROM "
        + strSchemaODS
        + "."
        + strEntityType
        + " WHERE "
        + "   ENTITYTYPE = '"
        + strEntityType
        + "' "
        + "   AND ENTITYID = "
        + iEID
        + "  "
        + "   AND NLSID  = "
        + iNLSID
        + "  "
        + " FOR READ ONLY";

    boolean blnFound = false;

    Statement stmt = conODS.createStatement();
    ResultSet myrs = stmt.executeQuery(strSQL);

    try {
      if (myrs.next()) {
        blnFound = true;
      }
    }
    catch (SQLException se) {
      se.printStackTrace();
      throw se;
    }
    finally {
      try {
        if (myrs != null) {
          myrs.close();
          myrs = null;
        }
        if (stmt != null) {
          stmt.close();
          stmt = null;
        }
      }
      catch (SQLException ex1) {
        ex1.printStackTrace();
      }
    }

    return blnFound;
  }

  /*
   *  BEGIN ROUTINE insertNewEntiy
   *  -----------------------------------------------------------------------------------------------------------
   *
   *  This routine will insert a new entity record into the appropriate table
   *  in the MIRRORDB.
   *
   *  -----------------------------------------------------------------------------------------------------------
   */
  /**
   *  Description of the Method
   *
   *@param  strEntityType  Description of the Parameter
   *@param  iEID           Description of the Parameter
   *@param  iNLSID         Description of the Parameter
   */
  private void insertNewEntity(String strEntityType, int iEID, int iNLSID) {
    Statement stmt = null;

    String strSQL =
        "INSERT INTO "
        + strSchemaODS
        + "."
        + strEntityType
        + "(ENTITYTYPE, ENTITYID, NLSID,VALFROM, VALTO) "
        + " VALUES ( "
        + "'"
        + strEntityType
        + "',"
        + " "
        + iEID
        + " ,"
        + " "
        + iNLSID
        + " ,"
        + " "
        + (strTimestampMode.equals("TIMESTAMP")
           ? "current timestamp,"
           : "'" + strTimeStampBeginOfDay + "',")
        + "'"
        + strTimeStampForever
        + "' "
        + ")";

    printOK(
        "insertNewEntity:"
        + blnLive
        + ":ET:"
        + strEntityType
        + ":EID:"
        + iEID
        + ":NLSID:"
        + iNLSID);

    try {
      stmt = conODS.createStatement();
      if (blnLive) {
        stmt.execute(strSQL);
      }
    }
    catch (SQLException ex) {
      printErr(
          "ERROR: insertNewEntity:"
          + blnLive
          + ":ET:"
          + strEntityType
          + ":EID:"
          + iEID
          + ":NLSID:"
          + iNLSID);
      printErr(ex.getMessage());
    }
    finally {
      try {
        if (stmt != null) {
          stmt.close();
          stmt = null;
        }

      }
      catch (SQLException ex1) {
        ex1.printStackTrace();
      }

    }

  }

  /*
   *  BEGIN METHOD updateEntity
   *  --------------------------------------------------------------------------------------------------------
   *  This routine will update the ValFrom for the given record.. in the given table in the ODS
   *  to what ever it was passed
   *  --------------------------------------------------------------------------------------------------------
   */
  /**
   *  Description of the Method
   *
   *@param  strEntityType     Description of the Parameter
   *@param  iEID              Description of the Parameter
   *@param  iNLSID            Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  private void updateEntity(String strEntityType, int iEID, int iNLSID) throws
      SQLException {
    String strSQL = null;
    PreparedStatement ps = null;
    printOK(
        "updateEntity:"
        + blnLive
        + ":ET:"
        + strEntityType
        + ":EID:"
        + iEID
        + ":NLSID:"
        + iNLSID);

    // This SQL updates the valfrom to that of Begining of the Day
    strSQL =
        " UPDATE "
        + strSchemaODS
        + "."
        + strEntityType
        + "   SET VALFROM = "
        + (strTimestampMode.equals("TIMESTAMP")
           ? "current timestamp"
           : "'" + strTimeStampBeginOfDay + "'")
        + " WHERE   "
        + "     ENTITYTYPE    = '"
        + strEntityType
        + "' "
        + "     AND ENTITYID  =  "
        + iEID
        + "     AND NLSID     =  "
        + iNLSID;
    ps = conODS.prepareStatement(strSQL);
    try {
      if (blnLive) {
        ps.executeUpdate();
      }
    }
    catch (SQLException se) {
      se.printStackTrace();
      throw se;
    }
    finally {
      try {
        if (ps != null) {
          ps.close();
          ps = null;
        }
      }
      catch (SQLException ex1) {
        ex1.printStackTrace();
        throw ex1;
      }
    }

    //Now refresh all the fields  for this record
    refreshODSEntity(strEntityType, iEID, iNLSID);

  }

  /*
   *  BEGIN METHOD retireEntity
   *  --------------------------------------------------------------------------------------------------------
   *
   *  This routine will remove an entity record from the ODS
   *  --------------------------------------------------------------------------------------------------------
   */
  /**
   *  Description of the Method
   *
   *@param  strEntityType     Description of the Parameter
   *@param  iEID              Description of the Parameter
   *@param  iNLSID            Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void retireEntity(String strEntityType, int iEID, int iNLSID) throws
      SQLException {

    String strSQL =
        " DELETE FROM "
        + strSchemaODS
        + "."
        + strEntityType
        + " WHERE     "
        + "   ENTITYTYPE = '"
        + strEntityType
        + "' "
        + "     AND ENTITYID  =  "
        + iEID
        + "     AND NLSID     =  "
        + iNLSID;

    PreparedStatement ps = conODS.prepareStatement(strSQL);
    printOK(
        "retireEntity:"
        + blnLive
        + ":ET:"
        + strEntityType
        + ":EID:"
        + iEID
        + ":NLSID:"
        + iNLSID);
    try {
      if (blnLive) {
        ps.executeUpdate();
      }
    }
    catch (SQLException se) {
      se.printStackTrace();
      throw se;
    }
    finally {
      try {
        if (ps != null) {
          ps.close();
          ps = null;
        }
      }
      catch (SQLException ex1) {
        ex1.printStackTrace();
        throw ex1;
      }
    }

  }

  /*
   *  BEGIN METHOD logDelete
   *  -----------------------------------------------------------------------------------------
   */
  /**
   *  Description of the Method
   *
   *@param  strEntityType     Description of the Parameter
   *@param  iEID              Description of the Parameter
   *@param  iNLSID            Description of the Parameter
   *@param  strLogType        Description of the Parameter
   *@param  strAttrCode       Description of the Parameter
   *@param  strAttrVal        Description of the Parameter
   *@param  strE1Type         Description of the Parameter
   *@param  iE1Id             Description of the Parameter
   *@param  strE2Type         Description of the Parameter
   *@param  iE2Id             Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void logDelete(
      String strEntityType,
      int iEID,
      int iNLSID,
      String strLogType,
      String strAttrCode,
      String strAttrVal,
      String strE1Type,
      int iE1Id,
      String strE2Type,
      int iE2Id) throws SQLException {
    String strSQL = null;
    PreparedStatement ps = null;
    if (strAttrCode == null) {
      strAttrCode = "";
    }
    if (strAttrVal == null) {
      strAttrVal = "";
    }
    if (strE1Type == null) {
      strE1Type = "";
    }
    if (strE2Type == null) {
      strE2Type = "";
    }
    strAttrVal =
        (strAttrVal.length() > 8) ? strAttrVal.substring(0, 7) : strAttrVal;

    strAttrVal =
        (strAttrVal.length() > 8) ? strAttrVal.substring(0, 7) : strAttrVal;

    strSQL =
        " INSERT INTO "
        + strSchemaODS
        + ".DELETELOG"
        + "(ENTITYTYPE, ENTITYID, NLSID,VALFROM,OPICMTYPE,ATTRIBUTECODE,ATTRIBUTEVALUE,ENTITY1TYPE,ENTITY2TYPE,ENTITY1ID,ENTITY2ID) "
        + " VALUES (?,?,?,current timestamp,?,?,?,?,?,?,?)";

    try {
      ps = conODS.prepareStatement(strSQL);

      ps.setString(1, strEntityType == null ? "METAFLAG" : strEntityType);
      ps.setInt(2, iEID);
      ps.setInt(3, iNLSID);
      ps.setString(4, strLogType);
      ps.setString(5, strAttrCode.equals("") ? null : strAttrCode);
      ps.setString(6, strAttrVal.equals("") ? null : strAttrVal);
      ps.setString(7, strE1Type.equals("") ? null : strE1Type);
      ps.setString(8, strE2Type.equals("") ? null : strE2Type);
      ps.setInt(9, iE1Id);
      ps.setInt(10, iE2Id);
      if (blnLive) {
        ps.execute();
      }
    }
    catch (SQLException se) {
      se.printStackTrace();
      throw se;
    }
    finally {
      try {
        if (ps != null) {
          ps.close();
          ps = null;
        }
      }
      catch (SQLException ex1) {
        ex1.printStackTrace();
        throw ex1;
      }
    }

  }

  /*
   *  ------------------------------------------------------------------------------------------------
   *  function createTable
   *  -----------------------
   *  creates a table (after dropping the same one if exists) in the connection provided with
   *  the string of columns provided
   *  --------------------------------------------------------------------------------------------------
   */
  /**
   *  Description of the Method
   *
   *@param  strTableName      Description of the Parameter
   *@param  vPDHColumns       Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void createTable(String strTableName, Vector vPDHColumns) throws
      SQLException {

    String strBaseColumns =
        " ENTITYTYPE CHAR(32) NOT NULL, "
        + " ENTITYID INTEGER NOT NULL, "
        + " NLSID INTEGER NOT NULL,  "
        + " Valfrom TIMESTAMP NOT NULL, "
        + " Valto TIMESTAMP NOT NULL";
    String strCurrentColumn = "";
    String strColLength = "";
    String strTableSpace = "";
    String strIndexSpace = "";
    int iColSize = 0;
    String strSQL = null;
    Statement stmt = null;
    printOK("createTable:Entering:" + blnLive + ":ET:" + strTableName);

    for (int myi = 0; myi <= vPDHColumns.size() - 1; ) {

      strCurrentColumn = (String) vPDHColumns.elementAt(myi);
      myi++;
      strColLength = (String) vPDHColumns.elementAt(myi);
      myi++;
      strTableSpace = (String) vPDHColumns.elementAt(myi);
      myi++;
      strIndexSpace = (String) vPDHColumns.elementAt(myi);
      myi++;

      iColSize = Integer.valueOf(strColLength).intValue();

      //int iColSize = getColumnSizeFromPDH(strCurrentColumn);
      strBaseColumns =
          strBaseColumns
          + ","
          + strCurrentColumn
          + " VARCHAR ("
          + Integer.toString(
              (iColSize * ODS_COL_CONV_FACTOR < MAX_ODS_COL_SIZE
               ? iColSize * ODS_COL_CONV_FACTOR
               : MAX_ODS_COL_SIZE))
          + ")";
    }

    strSQL =
        "CREATE TABLE "
        + strSchemaODS
        + "."
        + strTableName
        + " ("
        + strBaseColumns
        + ") "
        + " IN "
        + strTableSpace
        + " INDEX IN "
        + strIndexSpace;

    stmt = conODS.createStatement();
    printOK("createTable:" + ":SQL is:" + strSQL);
    try {
      stmt.executeUpdate(strSQL);
      stmt.executeUpdate(
          "CREATE UNIQUE INDEX "
          + strSchemaODS
          + "."
          + strTableName
          + "1 ON "
          + strSchemaODS
          + "."
          + strTableName
          + "(ENTITYID, ENTITYTYPE,NLSID )  ");
    }
    catch (SQLException se) {
      se.printStackTrace();
      throw se;
    }
    finally {
      try {
        if (stmt != null) {
          stmt.close();
          stmt = null;
        }
      }
      catch (SQLException ex1) {
        ex1.printStackTrace();
        throw ex1;
      }
    }

    printOK("createTable:Exiting:" + blnLive + ":ET:" + strTableName);

  }

  /*
   *  BEGIN Routine getLastRunTime
   *  ----------------------------------------------------------------------------------------------------------
   *
   *  This routine will insert a new time record into the TIMETABLE TABLE .. Subsequent routines will
   *  Grab the first record in this series (SORTED DECENDING). to know when the beginning time boundry
   *  Will work
   *
   *  ----------------------------------------------------------------------------------------------------------
   */
  /**
   *  Gets the lastRunTime attribute of the ODSNetChangeEngine object
   *
   *@return    The lastRunTime value
   */
  private String getLastRunTime() {

    String strAnswer = "ERROR";
    Statement stmt = null;
    ResultSet rs = null;
    try {
      stmt = conODS.createStatement();
      rs = stmt.executeQuery(getQueryStatement(ODS_GET_LAST_RUN_TIME));
      if (rs.next()) {
        strAnswer = rs.getString(1).trim();
      }

    }
    catch (SQLException ex) {
      printErr("getLastRunTime *Error*:\t" + ex.getMessage());
      System.exit( -1);
    }
    finally {
      try {
        if (rs != null) {
          rs.close();
          rs = null;
        }
        if (stmt != null) {
          stmt.close();
          stmt = null;
        }

      }
      catch (SQLException ex1) {
        ex1.printStackTrace();
        printErr("getLastRunTime *Error*:" + ex1.getMessage());
        System.exit( -1);
      }

    }

    return strAnswer;
  }

  /*
   *  ------------------------------------------------------------------------------------------------
   *  function putODSRunTime
   *  -----------------------
   *  creates a table (after dropping the same one if exists) in the connection provided with
   *  the string of columns provided
   *  --------------------------------------------------------------------------------------------------
   */
  /**
   *  Description of the Method
   *
   *@exception  SQLException  Description of the Exception
   */
  private void putODSRunTime() throws SQLException {

    String strSQL =
        "INSERT INTO "
        + strSchemaODS
        + ".TIMETABLE (RUNTIME,RUNTYPE) "
        + " VALUES ( "
        + " '"
        + strTimeStampNow
        + "', "
        + " 'N')";

    reconnectPDH();
    reconnectODS();

    Statement stmt = conODS.createStatement();
    printOK("putODSRunTime:" + blnLive + ":Posting run time...");
    try {
      if (blnLive) {
        stmt.execute(strSQL);
      }
    }
    catch (SQLException se) {
      se.printStackTrace();
      throw se;
    }
    finally {
      try {
        if (stmt != null) {
          stmt.close();
          stmt = null;
        }
      }
      catch (SQLException ex1) {
        ex1.printStackTrace();
        throw ex1;
      }
    }

  }

  /*
   *  start Method updateAllData
   *  ------------------------------------------------------------------------------------------------------------------
   *
   *  For the given run interval.
   *
   *  ------------------------------------------------------------------------------------------------------------------
   */
  /**
   *  Description of the Method
   *
   *@param  strEntityType     Description of the Parameter
   *@param  iEID              Description of the Parameter
   *@param  iNLSID            Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  private void refreshODSEntity(String strEntityType, int iEID, int iNLSID) throws
      SQLException {

    String strSQL = null;
    ResultSet rsSnapshot = null;
    PreparedStatement pqSnapshot = null;
    printOK(
        "refreshODSEntity: Refreshing Entity "
        + strEntityType
        + ":Mode:"
        + blnLive);
    strSQL = getQueryStatement(PDH_GET_ENTITY_SNAPSHOT_PQ);

    try {
      pqSnapshot = conPDH.prepareStatement(strSQL);

      pqSnapshot.setString(1, strTimeStampNow);
      pqSnapshot.setString(2, strTimeStampNow);
      pqSnapshot.setString(3, strTimeStampNow);
      pqSnapshot.setString(4, strTimeStampNow);
      pqSnapshot.setString(5, strTimeStampNow);
      pqSnapshot.setString(6, strTimeStampNow);
      pqSnapshot.setString(7, strTimeStampNow);
      pqSnapshot.setString(8, strTimeStampNow);

      pqSnapshot.setString(9, strEntityType);
      pqSnapshot.setInt(10, iEID);
      pqSnapshot.setString(ELEVEN, strTimeStampNow);
      pqSnapshot.setString(TWELVE, strTimeStampNow);
      pqSnapshot.setString(THIRTEEN, strTimeStampNow);
      pqSnapshot.setString(FOURTEEN, strTimeStampNow);

      pqSnapshot.setInt(FIFTEEN, iNLSID);
      pqSnapshot.setString(SIXTEEN, strTimeStampNow);
      pqSnapshot.setString(SEVENTEEN, strTimeStampNow);
      pqSnapshot.setString(EIGHTEEN, strTimeStampNow);
      pqSnapshot.setString(NINETEEN, strTimeStampNow);

      pqSnapshot.setString(TWENTY, strTimeStampNow);
      pqSnapshot.setString(TWENTYONE, strTimeStampNow);
      pqSnapshot.setString(TWENTYTWO, strTimeStampNow);
      pqSnapshot.setString(TWENTYTHREE, strTimeStampNow);

      pqSnapshot.setInt(TWENTYFOUR, iNLSID);
      pqSnapshot.setString(TWENTYFIVE, strTimeStampNow);
      pqSnapshot.setString(TWENTYSIX, strTimeStampNow);
      pqSnapshot.setString(TWENTYSEVEN, strTimeStampNow);
      pqSnapshot.setString(TWENTYEIGHT, strTimeStampNow);

      rsSnapshot = pqSnapshot.executeQuery();

      // printOK ("refreshODSEntity:" + blnLive + ":Scanned...");

      //  printOK ("refreshODSEntity:" + blnLive + ":Start Processing...");
      processAttribute(strEntityType, iEID, iNLSID, rsSnapshot);
      //  printOK ("refreshODSEntity:" + blnLive + ":End Processing...");

    }
    catch (SQLException ex) {
      printErr("refreshODSEntity:" + blnLive + ":ERROR..");
      printErr(ex.getMessage());
    }
    finally {
      try {
        if (rsSnapshot != null) {
          rsSnapshot.close();
          rsSnapshot = null;
        }
        if (pqSnapshot != null) {
          pqSnapshot.close();
          pqSnapshot = null;
        }

      }
      catch (SQLException ex1) {
        printErr("refreshODSEntity:" + blnLive + ":ERROR..");
        printErr(ex1.getMessage());
        ex1.printStackTrace();
        System.exit(1);
      }
    }

  }

  //System.out.println("updateAllData:" + new Date() + ":FINISHED");

  /*
   *  -----------------------------------------------------------------------------------
   *  function getColumnSizeFromPDH
   *  -----------------------
   *  This function returns query statements based upon the passed query number qn
   *
   *  -------------------------------------------------------------------------------------
   */
  /**
   *  Gets the columnSizeFromPDH attribute of the ODSNetChangeEngine object
   *
   *@param  strAttributeCode  Description of the Parameter
   *@return                   The columnSizeFromPDH value
   *@exception  SQLException  Description of the Exception
   */
  int getColumnSizeFromPDH(String strAttributeCode) throws SQLException {

    int iSize = DEF_PDH_COL_SIZE;

    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = conPDH.prepareStatement(getQueryStatement(PDH_GET_COLUMN_LENGTH_PQ));
      ps.setString(1, strAttributeCode);
      rs = ps.executeQuery();
      printOK(getQueryStatement(PDH_GET_COLUMN_LENGTH_PQ));

      printOK(
          "getColumnSizeFromPDH:Checking Column Size for:ATTNAME:"
          + strAttributeCode);
      if (rs.next()) {
        iSize = rs.getInt(1);
        if (iSize >= MAX_ODS_COL_SIZE) {
          printOK(
              "getColumnSizeFromPDH:CollTooLong:Setting to 254:ATTNAME:"
              + strAttributeCode
              + ":"
              + iSize);
          iSize = MAX_ODS_COL_SIZE;
        }
        else {
          printOK(
              "getColumnSizeFromPDH:Reported Size:ATTNAME:"
              + strAttributeCode
              + ":"
              + iSize);
        }

      }
      else {
        iSize = DEF_PDH_COL_SIZE;
        printOK(
            "getColumnSizeFromPDH:CollNotFound:Setting to 32:ATTNAME:"
            + strAttributeCode);
      }
    }
    catch (SQLException se) {
      se.printStackTrace();
      throw se;
    }
    finally {
      try {
        if (rs != null) {
          rs.close();
          rs = null;

        }
        if (ps != null) {
          ps.close();
          ps = null;

        }

      }
      catch (SQLException ex1) {
        ex1.printStackTrace();
        throw ex1;
      }
    }

    return iSize;
  } //END METHOD getColumnSizeFromPDH

  /*
   *  BEGIN ROUTINE processAttribute
   *  -------------------------------------------------------------------------------------------------------------------
   *
   *  This routine loops through a given result set and creates vector column and its value pairs to update
   *  in the target table.
   *
   *
   *  -------------------------------------------------------------------------------------------------------------------
   */
  /**
   *  Description of the Method
   *
   *@param  strEntityType     Description of the Parameter
   *@param  iEID              Description of the Parameter
   *@param  iNLSID            Description of the Parameter
   *@param  myrs              Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  private void processAttribute(
      String strEntityType,
      int iEID,
      int iNLSID,
      ResultSet myrs) throws SQLException {

    String strColName = "";
    String strColValue = "";
    Vector vColNames = new Vector();
    Vector vColVals = new Vector();
    int intColSize = 0;

    //Get the current definition of the columns in the ODS and
    //prune the values returned to fit the columns
    printOK("processAttribute:" + blnLive + ":Start Building Vector...");

    while (myrs.next()) {
      strColName = myrs.getString(1).trim();
      strColValue = myrs.getString(2);

      if (blnLive) {
        //  printOK ("processAttribute:" + blnLive +"NAME:"+strColName+":VALUE:"+strColValue+":");

        intColSize = 0;
      }
      if (getHODSTableDef().isEmpty()) {
        printOK("HEY HASH TABLE IS EMPTY!!!");
      }

      //Check for the length of the column in the hash table
      if (getHODSTableDef().get(strColName) != null) {
        //only if the column was defined in the first place!
        intColSize = ( (Integer) (getHODSTableDef().get(strColName))).intValue();
      }

      if (intColSize > 0) {
        //If the size is 0 it means that the column was not found in the hash table

        //Add to column name vector
        //  printOK("Adding "+strColName+" to vector");
        vColNames.addElement(strColName);

        //Get the correct length of value using the conversion factor
        if (intColSize < MAX_ODS_COL_SIZE) {
          intColSize = intColSize / ODS_COL_CONV_FACTOR;
        }
        else {
          intColSize = MAX_ODS_COL_SIZE;
          //No truncation of text when colsize = 254
          printOK("processAttribute:Setting to 254:ATTNAME:" + strColName);
        }

        //Trim the length of the value returned from the PDH to the
        //correct length
        if (strColValue.length() > intColSize) {
          printOK(
              "processAttribute:"
              + blnLive
              + ":TRUNCATE:"
              + intColSize
              + ":"
              + strColValue);
          strColValue = strColValue.substring(0, intColSize);
        }

        //Append this value to the column value vector
        vColVals.addElement(strColValue);
      }
      else {
        printErr(
            "processAttribute:WARNING:COLNOTFOUND:CHECK:ENTITY"
            + strEntityType
            + ":COLUMN"
            + strColName);
      }

    }
    if (!vColNames.isEmpty()) {
      printOK(
          "processAttribute:"
          + blnLive
          + ":End  Building Vector...Calling update");
      updateAttribute(conODS, strEntityType, iEID, iNLSID, vColNames, vColVals);
    }
    else {
      printOK(
          "processAttribute:"
          + blnLive
          + ":End  Building Vector...Nothing to process..not calling Update");
    }

  }

  /*
   *  BEGIN METHOD updateAttribute
   *  --------------------------------------------------------------------------------------------------------
   *
   *  This routine will take the given attribute and update the corresponding table/column with the passed
   *  effFrom and actual value.
   *
   *  --------------------------------------------------------------------------------------------------------
   */
  /**
   *  Description of the Method
   *
   *@param  strEntityType  Description of the Parameter
   *@param  intEntityID    Description of the Parameter
   *@param  intNLSID       Description of the Parameter
   *@param  vColNames      Description of the Parameter
   *@param  vColVals       Description of the Parameter
   */
  private void updateAttribute(
      Connection _conODS,
      String strEntityType,
      int intEntityID,
      int intNLSID,
      Vector vColNames,
      Vector vColVals) {

    String strHeader =
        "UPDATE  " + strSchemaODS + "." + strEntityType + " SET ";
    // Build the  header
    String strColDetails = "*";
    String strFooter = " WHERE ENTITYID= ? AND NLSID = ? ";
    int i = 1;
    String strUpdateValue = null;

    Enumeration eColumns = vColNames.elements();
    Enumeration eValues = vColVals.elements();
    PreparedStatement psUpdateStatement = null;

    while (eColumns.hasMoreElements()) {
      if (strColDetails.compareTo("*") == 0) {
        strColDetails = " "; //This
      }
      else {
        strColDetails = strColDetails + ",";
      }

      strColDetails = strColDetails + (String) eColumns.nextElement() + " = ? ";

    }

    //Update now
    try {
      psUpdateStatement =
          _conODS.prepareStatement(strHeader + strColDetails + strFooter);
      i = 1;
      strUpdateValue = null;
      while (eValues.hasMoreElements()) {
        strUpdateValue = (String) eValues.nextElement();
        if (strUpdateValue.equals(" ")) { //This means that the field had null

          psUpdateStatement.setString(i++, null); //Then set the value to Null
        }
        else {
          psUpdateStatement.setString(i++, strUpdateValue);
        }

      }
      //And finally, the parms in the WHERE
      psUpdateStatement.setInt(i++, intEntityID);
      psUpdateStatement.setInt(i++, intNLSID);

      //Update only if live
      if (blnLive) {
        int intRowCount = psUpdateStatement.executeUpdate();
        printOK("updateAttribute:" + intRowCount + " rows updated");
      }
    }
    catch (SQLException ex) {
      printErr("updateAttribute:SQLError:updateAttribute:" + ex.getMessage());
      printErr("updateAttribute:" + strHeader + strColDetails + strFooter);
      printOK(
          "Bad Update:ET:"
          + strEntityType
          + ":EID:"
          + intEntityID
          + ":NLSID:"
          + intNLSID);
      System.exit( -1);
    }
    finally {
      try {
        if (psUpdateStatement != null) {
          psUpdateStatement.close();
          psUpdateStatement = null;
        }
      }
      catch (SQLException ex1) {
        ex1.printStackTrace();
        printErr(
            "updateAttribute:SQLError:updateAttribute:" + ex1.getMessage());
        System.exit( -1);
      }
    }

  }

  /*
   *  commitChangesToODSTables
   *  ------------------------
   *  This will update all the non entity related tables.
   *  This will query the table being processed using the table parameter. The result set generated from the query is
   *  processed and the column names and its types derived from it. The values are derived according to the column types
   *  This is used to call the insert and update methods
   */
  /**
   *  Description of the Method
   *
   *@param  intTableFlag  Description of the Parameter
   */
  private void commitChangesToODSTables(int intTableFlag) {

    //
    // Lets reconnect for fun
    // just incase something took too long!
    // and a firewall killed the connection between blocks
    //
    reconnectPDH();
    reconnectODS();

    String strEtype = null;
    int iEID = 0;
    int iNLSID = 0;
    String strAttrCode = null;
    String strAttrType = null;
    String strAttrVal = null;
    byte[] byteBlobValue = null;
    String strValTo = null; //We will use this var to compare to today
    String strEffTo = null; // as above
    String strValFrom = null; //Used for Blob retrieval
    String strEffFrom = null; // as above
    //used to see whether the row has empty key field
    String strLongDescription = null;
    String strShortDescription = null;
    String strBlobExtension = null;
    String strE1Type = null;
    String strE2Type = null;
    int intE1Id = 0;
    int intE2Id = 0;

    String strInsertStmt = null; //Assign the appropriate values depending upon
    String strUpdateStmt = null; //the table being processed
    String strTableQueryStmt = null;
    String strDeleteStmt = null;
    String strDeleteLogType = null;

    PreparedStatement psFindPDHChange = null;
    ResultSet rsPDHChanges = null;
    ResultSetMetaData rsmdColInfo = null;
    int intRSColCount = 0;
    int intProgColCount = 1;
    Vector vColName = new Vector();
    Vector vColType = new Vector();
    ReturnDataResultSet rdrsPDH = null;
    Enumeration eColName = null;
    String strTempColName = null;
    int intRSColNo = 0; //Set the column Number to 0

    //the insert

    switch (intTableFlag) {
      case (ISFLAG):
        printOK(
            "commitChangesToODSTables:" + blnLive + "Start Processing Flag");
        strInsertStmt = getQueryStatement(ODS_INSERT_FLAG_PQ);
        strUpdateStmt = getQueryStatement(ODS_UPDATE_FLAG_PQ);
        strTableQueryStmt = getQueryStatement(PDH_GET_FLAG_CHANGES_PQ);
        strDeleteStmt = getQueryStatement(ODS_DELETE_FLAG_PQ);
        strDeleteLogType = "FLAG";
        break;
      case (ISBLOB):
        printOK(
            "commitChangesToODSTables:" + blnLive + "Start Processing Blob");
        strInsertStmt = getQueryStatement(ODS_INSERT_BLOB_PQ);
        strUpdateStmt = getQueryStatement(ODS_UPDATE_BLOB_PQ);
        strTableQueryStmt = getQueryStatement(PDH_GET_BLOB_CHANGES_PQ);
        strDeleteStmt = getQueryStatement(ODS_DELETE_BLOB_PQ);
        strDeleteLogType = "BLOB";
        break;
      case (ISLONGTEXT):
        printOK(
            "commitChangesToODSTables:" + blnLive + "Start Processing LongText");
        strInsertStmt = getQueryStatement(ODS_INSERT_LONGTEXT_PQ);
        strUpdateStmt = getQueryStatement(ODS_UPDATE_LONGTEXT_PQ);
        strTableQueryStmt = getQueryStatement(PDH_GET_LONGTEXT_CHANGES_PQ);
        strDeleteStmt = getQueryStatement(ODS_DELETE_LONGTEXT_PQ);
        strDeleteLogType = "LONGTEXT";
        break;
      case (ISRELATOR):
        printOK(
            "commitChangesToODSTables:" + blnLive + "Start Processing Relator");
        strInsertStmt = getQueryStatement(ODS_INSERT_RELATOR_PQ);
        strUpdateStmt = getQueryStatement(ODS_UPDATE_RELATOR_PQ);
        strTableQueryStmt = getQueryStatement(PDH_GET_RELATOR_CHANGES_PQ);
        strDeleteStmt = getQueryStatement(ODS_DELETE_RELATOR_PQ);
        strDeleteLogType = "RELATOR";
        break;
      case (ISMDTABLE):
        printOK(
            "commitChangesToODSTables:"
            + blnLive
            + "Start Processing MetaFlagValue");
        strInsertStmt = getQueryStatement(ODS_INSERT_MD_PQ);
        strUpdateStmt = getQueryStatement(ODS_UPDATE_MD_PQ);
        strTableQueryStmt = getQueryStatement(PDH_GET_MD_CHANGES_PQ);
        strDeleteStmt = getQueryStatement(ODS_DELETE_MD_PQ);
        strDeleteLogType = "METAFLAGVALUE";
        break;
      default:
        printErr(
            "commitChangesToODSTables:"
            + blnLive
            + "Invalid Table parameter received....ABORTING");
        System.exit( -1);
    }

    //Lets get down to business!, lets run the query
    try {
      psFindPDHChange = conPDH.prepareStatement(strTableQueryStmt);
      rsPDHChanges = null;
      printOK("commitChangesToODSTables:" + blnLive + " Starting Query");
      //Assign the parameters
      switch (intTableFlag) {
        case (ISFLAG):

          psFindPDHChange.setString(1, strTimeStampNow);
          psFindPDHChange.setString(2, strTimeStampNow);
          psFindPDHChange.setString(3, strTimeStampNow);
          psFindPDHChange.setString(4, strTimeStampNow);

          psFindPDHChange.setString(5, strTimeStampLastRun);
          psFindPDHChange.setString(6, strTimeStampNow);
          psFindPDHChange.setString(7, strTimeStampLastRun);
          psFindPDHChange.setString(8, strTimeStampNow);
          psFindPDHChange.setString(9, strTimeStampLastRun);
          psFindPDHChange.setString(10, strTimeStampNow);
          psFindPDHChange.setString(ELEVEN, strTimeStampLastRun);
          psFindPDHChange.setString(TWELVE, strTimeStampNow);

          psFindPDHChange.setString(THIRTEEN, strTimeStampNow);
          psFindPDHChange.setString(FOURTEEN, strTimeStampNow);
          psFindPDHChange.setString(FIFTEEN, strTimeStampNow);
          psFindPDHChange.setString(SIXTEEN, strTimeStampNow);

          psFindPDHChange.setString(SEVENTEEN, strTimeStampNow);
          psFindPDHChange.setString(EIGHTEEN, strTimeStampNow);
          psFindPDHChange.setString(NINETEEN, strTimeStampNow);
          psFindPDHChange.setString(TWENTY, strTimeStampNow);

          psFindPDHChange.setString(TWENTYONE, strTimeStampLastRun);
          psFindPDHChange.setString(TWENTYTWO, strTimeStampNow);
          psFindPDHChange.setString(TWENTYTHREE, strTimeStampLastRun);
          psFindPDHChange.setString(TWENTYFOUR, strTimeStampNow);
          psFindPDHChange.setString(TWENTYFIVE, strTimeStampLastRun);
          psFindPDHChange.setString(TWENTYSIX, strTimeStampNow);
          psFindPDHChange.setString(TWENTYSEVEN, strTimeStampLastRun);
          psFindPDHChange.setString(TWENTYEIGHT, strTimeStampNow);

          break;
        case (ISBLOB):
          psFindPDHChange.setString(1, strTimeStampNow);
          psFindPDHChange.setString(2, strTimeStampNow);
          psFindPDHChange.setString(3, strTimeStampNow);
          psFindPDHChange.setString(4, strTimeStampNow);
          psFindPDHChange.setString(5, strEnterprise);
          psFindPDHChange.setString(6, strTimeStampLastRun);
          psFindPDHChange.setString(7, strTimeStampNow);
          psFindPDHChange.setString(8, strTimeStampLastRun);
          psFindPDHChange.setString(9, strTimeStampNow);
          psFindPDHChange.setString(10, strTimeStampLastRun);
          psFindPDHChange.setString(ELEVEN, strTimeStampNow);
          psFindPDHChange.setString(TWELVE, strTimeStampLastRun);
          psFindPDHChange.setString(THIRTEEN, strTimeStampNow);

          break;
        case (ISLONGTEXT):
          psFindPDHChange.setString(1, strTimeStampNow);
          psFindPDHChange.setString(2, strTimeStampNow);
          psFindPDHChange.setString(3, strTimeStampNow);
          psFindPDHChange.setString(4, strTimeStampNow);
          psFindPDHChange.setString(5, strEnterprise);
          psFindPDHChange.setString(6, strTimeStampLastRun);
          psFindPDHChange.setString(7, strTimeStampNow);
          psFindPDHChange.setString(8, strTimeStampLastRun);
          psFindPDHChange.setString(9, strTimeStampNow);
          psFindPDHChange.setString(10, strTimeStampLastRun);
          psFindPDHChange.setString(ELEVEN, strTimeStampNow);
          psFindPDHChange.setString(TWELVE, strTimeStampLastRun);
          psFindPDHChange.setString(THIRTEEN, strTimeStampNow);

          break;
        case (ISRELATOR):
          psFindPDHChange.setString(1, strTimeStampNow);
          psFindPDHChange.setString(2, strTimeStampNow);
          psFindPDHChange.setString(3, strTimeStampNow);
          psFindPDHChange.setString(4, strTimeStampNow);
          psFindPDHChange.setString(5, strEnterprise);
          psFindPDHChange.setString(6, strTimeStampLastRun);

          psFindPDHChange.setString(7, strTimeStampNow);
          psFindPDHChange.setString(8, strTimeStampNow);
          psFindPDHChange.setString(9, strTimeStampNow);
          psFindPDHChange.setString(10, strTimeStampNow);
          psFindPDHChange.setString(ELEVEN, strEnterprise);
          psFindPDHChange.setString(TWELVE, strTimeStampLastRun);
          psFindPDHChange.setString(THIRTEEN, strTimeStampNow);

          psFindPDHChange.setString(FOURTEEN, strTimeStampNow);
          psFindPDHChange.setString(FIFTEEN, strTimeStampNow);
          psFindPDHChange.setString(SIXTEEN, strTimeStampNow);
          psFindPDHChange.setString(SEVENTEEN, strTimeStampNow);
          psFindPDHChange.setString(EIGHTEEN, strEnterprise);
          psFindPDHChange.setString(NINETEEN, strTimeStampLastRun);
          psFindPDHChange.setString(TWENTY, strTimeStampNow);

          break;
        case (ISMDTABLE):
          psFindPDHChange.setString(1, strEnterprise);
          psFindPDHChange.setString(2, strTimeStampLastRun);
          psFindPDHChange.setString(3, strTimeStampNow);
          psFindPDHChange.setString(4, strTimeStampLastRun);
          psFindPDHChange.setString(5, strTimeStampNow);
          psFindPDHChange.setString(6, strTimeStampLastRun);
          psFindPDHChange.setString(7, strTimeStampNow);
          psFindPDHChange.setString(8, strTimeStampLastRun);
          psFindPDHChange.setString(9, strTimeStampNow);

          break;
        default:
          break;
      }

      printOK("commitChangesToODSTables:" + blnLive + "Query Start");
      printOK(strTableQueryStmt);
      rsPDHChanges = psFindPDHChange.executeQuery(); //Execute the query
      printOK("commitChangesToODSTables:" + blnLive + "Query complete");

      /*
       *  Here's the tricky part.....from the Result set,
       *  get the column names and type of columns...
       *  put them into a vector...
       */
      rsmdColInfo = rsPDHChanges.getMetaData();
      intRSColCount = 0;
      intProgColCount = 1;
      intRSColCount = rsmdColInfo.getColumnCount(); //Get the number of columns
      vColName = new Vector();
      vColType = new Vector();

      for (intProgColCount = 1;
           intProgColCount <= intRSColCount;
           intProgColCount++) {
        vColName.addElement(rsmdColInfo.getColumnName(intProgColCount).trim());
        vColType.addElement(
            new Integer(rsmdColInfo.getColumnType(intProgColCount)));
      }
      printOK("vColName:"+vColName);
      /*
       * Move the resultset into a returndata result set
       * so that we can close the result set and reduce
       * the window of time where the rows are locked in the pdh
       */
      rdrsPDH = new ReturnDataResultSet(rsPDHChanges);

      conPDH.commit();
      if (rsPDHChanges != null) {
        rsPDHChanges.close();
      }
      printOK(
          "commitChangesToODSTables:"
          + blnLive
          + "Found "
          + rdrsPDH.size()
          + " rows to process");

      //Now compare and process the result set

      eColName = vColName.elements();
      vColType.elements();
      strTempColName = null;
      intRSColNo = 0; //Set the column Number to 0

      for (int x = 0; x < rdrsPDH.size(); x++) {
        printOK(
            "commitChangesToODSTables:"
            + blnLive
            + " "
            + x
            + ":of:"
            + rdrsPDH.size());
        try {
          //Get each column
          while (eColName.hasMoreElements()) {
            strTempColName = (String) eColName.nextElement();
            //Get the name of the column

            if (strTempColName.equals("ENTITYTYPE")) {
              strEtype = rdrsPDH.getColumn(x, intRSColNo).trim();
            }
            else if (strTempColName.equals("ENTITYID")) {
              iEID = rdrsPDH.getColumnInt(x, intRSColNo);
            }
            else if (strTempColName.equals("ENTITY1TYPE")) {
              strE1Type = rdrsPDH.getColumn(x, intRSColNo).trim();
            }
            else if (strTempColName.equals("ENTITY1ID")) {
              intE1Id = rdrsPDH.getColumnInt(x, intRSColNo);
            }
            else if (strTempColName.equals("ENTITY2TYPE")) {
              strE2Type = rdrsPDH.getColumn(x, intRSColNo).trim();
            }
            else if (strTempColName.equals("ENTITY2ID")) {
              intE2Id = rdrsPDH.getColumnInt(x, intRSColNo);
            }
            else if (strTempColName.equals("NLSID")) {
              iNLSID = rdrsPDH.getColumnInt(x, intRSColNo);
            }
            else if (strTempColName.equals("ATTRIBUTECODE")) {
              strAttrCode = rdrsPDH.getColumn(x, intRSColNo).trim();
            }
            else if (strTempColName.equals("ATTRIBUTETYPE")) {
              strAttrType = rdrsPDH.getColumn(x, intRSColNo).trim();
             
            }
            else if (
                strTempColName.equals(
                    "ATTRIBUTEVALUE")) { //Gotta check when its a blob then value will be bin

              if (intTableFlag == ISBLOB) {

              } //Attr Value
              else {
                strAttrVal = rdrsPDH.getColumn(x, intRSColNo);
              }

            }
            else if (strTempColName.equals("BLOBEXTENSION")) {
              strBlobExtension = rdrsPDH.getColumn(x, intRSColNo);
            }
            else if (strTempColName.equals("VALFROM")) {
              strValFrom = rdrsPDH.getColumn(x, intRSColNo).trim();
            }
            else if (strTempColName.equals("EFFFROM")) {
              strEffFrom = rdrsPDH.getColumn(x, intRSColNo).trim();
            }
            else if (strTempColName.equals("VALTO")) {
              strValTo = rdrsPDH.getColumn(x, intRSColNo).trim();
            } //Get Valto Effto
            else if (strTempColName.equals("EFFTO")) {
              strEffTo = rdrsPDH.getColumn(x, intRSColNo).trim();
            }
            else if (strTempColName.equals("LONGDESCRIPTION")) {
              strLongDescription = rdrsPDH.getColumn(x, intRSColNo).trim();
            }
            else if (strTempColName.equals("SHORTDESCRIPTION")) {
              strShortDescription = rdrsPDH.getColumn(x, intRSColNo).trim();
            }
            else if (strTempColName.equals("DESCRIPTIONTYPE")) {
              strAttrCode = rdrsPDH.getColumn(x, intRSColNo).trim();
            }
            else if (strTempColName.equals("DESCRIPTIONCLASS")) {
              strAttrVal = rdrsPDH.getColumn(x, intRSColNo).trim();
            }

            intRSColNo++;
          }

          eColName = vColName.elements();
          //Reset enumerator for the next iteration
          intRSColNo = 0; //ReSet the column Number to 0

          if (strValTo.compareTo(strTimeStampNow) > 0
              && strEffTo.compareTo(strTimeStampNow) > 0) {
            //Could be an insert or update

            if (intTableFlag == ISBLOB) {
              //Skip Blob attributes found in this list
              if (strBlobAttExcludeList.indexOf(strAttrCode)>0 ) {
                printOK("Skipping this BLOB Row as Attribute in excludelist");
                printOK(" ET:" + strEtype);
                printOK(" ID:" + iEID);
                printOK(" :ACODE:" + strAttrCode);
                continue;
              }


              byteBlobValue =
                  getBlobValue(
                      strEtype,
                      iEID,
                      iNLSID,
                      strAttrCode,
                      strValTo,
                      strEffTo,
                      strValFrom,
                      strEffFrom);
              if (byteBlobValue == null) {
                printOK("Blob Returned NULL..");
              }
            }
            //Check if this exists
            if (!checkIfRowExistsInODS(intTableFlag,
                                       conODS,
                                       strEtype,
                                       iEID,
                                       iNLSID,
                                       strAttrCode,
                                       //used only for flag queries
                                       strAttrVal)) { // and metaflagvalue queries

              insertIntoODS(
                  intTableFlag,
                  strInsertStmt,
                  conODS,
                  strEtype,
                  strE1Type,
                  strE2Type,
                  iEID,
                  intE1Id,
                  intE2Id,
                  iNLSID,
                  byteBlobValue,
                  strLongDescription,
                  strShortDescription,
                  strBlobExtension,
                  strAttrType,
                  strAttrCode,
                  strAttrVal);

              //if no exist, then insert
            }
            else { //Update it

              updateODS(
                  intTableFlag,
                  strUpdateStmt,
                  conODS,
                  strEtype,
                  iEID,
                  iNLSID,
                  byteBlobValue,
                  strLongDescription,
                  strShortDescription,
                  strBlobExtension,
                  strAttrType,
                  strAttrCode,
                  strAttrVal);

            }
          }
          else {
            if (checkIfRowExistsInODS(intTableFlag,
                                      conODS,
                                      strEtype,
                                      iEID,
                                      iNLSID,
                                      strAttrCode,
                                      //used only for flag queries
                                      strAttrVal)) { // and metaflagvalue queries

              //Retire it
              retireODSrow(
                  intTableFlag,
                  strDeleteStmt,
                  conODS,
                  strEtype,
                  iEID,
                  iNLSID,
                  strAttrCode,
                  strAttrVal);
              logDelete(
                  strEtype,
                  iEID,
                  iNLSID,
                  strDeleteLogType,
                  strAttrCode,
                  strAttrVal,
                  strE1Type,
                  intE1Id,
                  strE2Type,
                  intE2Id);
              //Append to log table
            }
            else {
              printOK(
                  "WARNING:Row Not found in the ODS table, expecting to delete ");
              printOK(" ET:" + strEtype);
              printOK(" ID:" + iEID);
              printOK(" :ACODE:" + strAttrCode);
              printOK(" :NOW:" + strTimeStampNow);
              printOK(" :VT" + strValTo);
              printOK(" ET:" + strEffTo);
            }
          }

          strE1Type = null;
          strE2Type = null;
          intE1Id = 0;
          intE2Id = 0;

        }
        catch (SQLException e) {
          printErr("SQLE:commitChangesToODSTables ERROR IN WHILE LOOP! ");
          printErr(e.getMessage());
          System.exit( -1);
        }
      }

    }
    catch (SQLException ex) {
      printErr("SQLE:commitChangesToODSTables ERROR!");
      printErr(ex.getMessage());
      System.exit( -1);
    }
    finally {
      try {
        if (rsPDHChanges != null) {
          rsPDHChanges.close();
          rsPDHChanges = null;

        }
        if (psFindPDHChange != null) {
          psFindPDHChange.close();
          psFindPDHChange = null;

        }
      }
      catch (SQLException ex1) {
        ex1.printStackTrace();
        printErr("SQLE:commitChangesToODSTables ERROR!");
        printErr(ex1.getMessage());

      }
    }

  } //commitChangesToODSTables

  /*
   *  -----------------------------------------------------------------------------------
   *  function getQueryStatement
   *  -----------------------
   *  This function returns query statements based upon the passed query number qn
   *
   *  -------------------------------------------------------------------------------------
   */
  /**
   *  Gets the queryStatement attribute of the ODSNetChangeEngine object
   *
   *@param  qn  Description of the Parameter
   *@return     The queryStatement value
   */
  private String getQueryStatement(int qn) {

    switch (qn) {
      case (PDH_GET_PDH_DB_TIMESTAMP):
        return "SELECT current timestamp AS now FROM opicm.nextid FETCH FIRST 1 ROWS ONLY";

      case (PDH_GET_FLAG_ENTITY_INSTANCE):
        return "SELECT entityid FROM opicm.flag F"
            + " WHERE enterprise= ?"
            + " AND entitytype= ? "
            + " AND attributecode= ? "
            + " AND attributevalue=  ? "
            + "  AND F.ValFrom <= '"
            + strTimeStampNow
            + "'"
            + "  AND F.ValTo > '"
            + strTimeStampNow
            + "'"
            + "  AND F.EffFrom <= '"
            + strTimeStampNow
            + "'"
            + "  AND F.EffTo > '"
            + strTimeStampNow
            + "'";
      case (PDH_GET_NEW_AND_OLD_MDLONGTEXT):
        return "SELECT ML.LINKTYPE1 as ENTITYTYPE,  "
            + "      MD.DESCRIPTIONTYPE AS ATTRIBUTECODE,  "
            + "      MD.NLSID,MD.DESCRIPTIONCLASS AS FLAGVALUE  "
            + "      ,COALESCE(MD.LONGDESCRIPTION,'QUERYNULL') AS FLAGDESC "
            + "      ,COALESCE(MD1.LONGDESCRIPTION,'QUERYNULL') AS OLDDESC "
            + "      ,MD.VALFROM "
            + "      ,MD.VALTO "
            + "from "
            + strSchemaPDH
            + ".MetaLinkAttr ML   "
            + "INNER JOIN "
            + strSchemaPDH
            + ".metaentity ma ON  "
            + "  ML.linktype2=ma.entitytype   "
            + "  AND ML.enterprise = ma.enterprise   "
            + "  AND ma.entityclass IN ('U','S','A')   "
            + "  AND MA.ValFrom <= '"
            + strTimeStampNow
            + "'"
            + "  AND MA.ValTo > '"
            + strTimeStampNow
            + "'"
            + "  AND MA.EffFrom <= '"
            + strTimeStampNow
            + "'"
            + "  AND MA.EffTo > '"
            + strTimeStampNow
            + "'"
            + "INNER JOIN "
            + strSchemaPDH
            + ".METADESCRIPTION MD ON  "
            + "  ML.LINKTYPE2=MD.DESCRIPTIONTYPE  "
            + "  AND MD.Enterprise = ML.enterprise "
            + "  AND  "
            + "(TIMESTAMP(MD.VALFROM) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "'"
            + "OR TIMESTAMP(MD.VALTO) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "'"
            + "OR TIMESTAMP(MD.EFFFROM) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "'"
            + "OR TIMESTAMP(MD.EFFTO) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "') "
            +
            //"--Get the old metadescription here "+
            "LEFT JOIN "
            + strSchemaPDH
            + ".METADESCRIPTION MD1 ON  "
            + "  MD.DESCRIPTIONTYPE =MD1.DESCRIPTIONTYPE  "
            + "  AND MD1.Enterprise = md.enterprise "
            + "  and md1.nlsid = md.nlsid "
            + "  and md1.descriptionclass = md.descriptionclass "
            + "  AND "
            + "   md.valfrom > MD1.ValFrom  AND "
            + "   md.efffrom >= MD1.EffFrom  AND "
            + "   md.valfrom = MD1.ValTo     "
            + " WHERE "
            + "    ML.Enterprise = '"
            + strEnterprise
            + "'  "
            + "    AND ML.linktype = 'Entity/Attribute'"
            + "    AND ML.linkcode='EntityAttribute'   "
            + "    AND ML.linkvalue='L'   "
            + "    AND ML.ValFrom <= '"
            + strTimeStampNow
            + "'"
            + "    AND ML.ValTo > '"
            + strTimeStampNow
            + "'"
            + "    AND ML.EffFrom <= '"
            + strTimeStampNow
            + "'"
            + "    AND ML.EffTo > '"
            + strTimeStampNow
            + "'"
            + "    AND ML.Linktype1 = ?"
            + "ORDER BY "
            + "ATTRIBUTECODE,FLAGVALUE,NLSID,VALTO                    ";
      case (PDH_GET_MD_ENTITY_CHANGES_PQ):
        return "SELECT ML.LINKTYPE1 as ENTITYTYPE, "
            + "      MD.DESCRIPTIONTYPE AS ATTRIBUTECODE, "
            + "      MD.NLSID,MD.DESCRIPTIONCLASS AS FLAGVALUE, "
            + "      MD.LONGDESCRIPTION "
            + "from "
            + strSchemaPDH
            + ".MetaLinkAttr ML  "
            + "INNER JOIN "
            + strSchemaPDH
            + ".metaentity ma ON "
            + "  ML.linktype2=ma.entitytype  "
            + "  AND ML.enterprise = ma.enterprise  "
            + "  AND ma.entityclass IN ('U','S','A')  "
            + "  AND MA.ValFrom <= '"
            + strTimeStampNow
            + "' "
            + "  AND MA.ValTo > '"
            + strTimeStampNow
            + "' "
            + "  AND MA.EffFrom <= '"
            + strTimeStampNow
            + "' "
            + "  AND MA.EffTo > '"
            + strTimeStampNow
            + "' "
            + "INNER JOIN "
            + strSchemaPDH
            + ".METADESCRIPTION MD ON "
            + "  ML.LINKTYPE2=MD.DESCRIPTIONTYPE "
            + "  AND MD.Enterprise = ML.enterprise"
            +
            //"  AND MD.Descriptionclass in ('U','S') "+
            "  AND "
            + "   (('"
            + strTimeStampLastRun
            + "' <= MD.ValFrom  AND MD.ValFrom <= '"
            + strTimeStampNow
            + "')  OR     "
            + "   ('"
            + strTimeStampLastRun
            + "'<= MD.EffFrom  AND MD.EffFrom <= '"
            + strTimeStampNow
            + "')  OR     "
            + "   ('"
            + strTimeStampLastRun
            + "' <= MD.ValTo    AND MD.ValTo   <= '"
            + strTimeStampNow
            + "')  OR     "
            + "   ('"
            + strTimeStampLastRun
            + "' <= MD.EffTo    AND MD.EffTo   <= '"
            + strTimeStampNow
            + "'))        "
            + " WHERE  "
            + "    ML.Enterprise = '"
            + strEnterprise
            + "'  "
            + "    AND ML.linktype = 'Entity/Attribute'    "
            + "    AND ML.linkcode='EntityAttribute'   "
            + "    AND ML.linkvalue='L'   "
            + "    AND ML.ValFrom <= '"
            + strTimeStampNow
            + "' "
            + "    AND ML.ValTo > '"
            + strTimeStampNow
            + "' "
            + "    AND ML.EffFrom <= '"
            + strTimeStampNow
            + "' "
            + "    AND ML.EffTo > '"
            + strTimeStampNow
            + "' "
            + "    AND ML.Linktype1 = ? "
            + "FETCH FIRST 1 ROWS ONLY    ";
      case (PDH_GET_INCLUDE_MD_UF_ENTITIES_PQ):
        return "WITH MYFLAGS (ENTITYTYPE,ATTRIBUTECODE,NLSID,FLAGVALUE) AS "
            + "( "
            + "SELECT ML.LINKTYPE1 as ENTITYTYPE, "
            + "      MD.DESCRIPTIONTYPE AS ATTRIBUTECODE, "
            + "      MD.NLSID,MD.DESCRIPTIONCLASS AS FLAGVALUE "
            + "from "
            + strSchemaPDH
            + ".MetaLinkAttr ML  "
            + "INNER JOIN "
            + strSchemaPDH
            + ".metaentity ma ON "
            + "  ML.linktype2=ma.entitytype  "
            + "  AND ML.enterprise = ma.enterprise  "
            + "  AND ma.entityclass IN ('U','S','A')  "
            + "  AND MA.ValFrom <= '"
            + strTimeStampNow
            + "' "
            + "  AND MA.ValTo > '"
            + strTimeStampNow
            + "' "
            + "  AND MA.EffFrom <= '"
            + strTimeStampNow
            + "' "
            + "  AND MA.EffTo > '"
            + strTimeStampNow
            + "' "
            + "INNER JOIN "
            + strSchemaPDH
            + ".METADESCRIPTION MD ON "
            + "  ML.LINKTYPE2=MD.DESCRIPTIONTYPE "
            + "  AND MD.Enterprise = ML.enterprise"
            +
            //"  AND MD.Descriptionclass IN ('U','S') "+
            "  AND "
            + "   (('"
            + strTimeStampLastRun
            + "' <= MD.ValFrom  AND MD.ValFrom <= '"
            + strTimeStampNow
            + "')  OR     "
            + "   ('"
            + strTimeStampLastRun
            + "'<= MD.EffFrom  AND MD.EffFrom <= '"
            + strTimeStampNow
            + "')  OR     "
            + "   ('"
            + strTimeStampLastRun
            + "' <= MD.ValTo    AND MD.ValTo   <= '"
            + strTimeStampNow
            + "')  OR     "
            + "   ('"
            + strTimeStampLastRun
            + "' <= MD.EffTo    AND MD.EffTo   <= '"
            + strTimeStampNow
            + "'))        "
            + " WHERE  "
            + "    ML.Enterprise = '"
            + strEnterprise
            + "'  "
            + "    AND ML.linktype = 'Entity/Attribute'    "
            + "    AND ML.linkcode='EntityAttribute'   "
            + "    AND ML.linkvalue='L'   "
            + "    AND ML.ValFrom <= '"
            + strTimeStampNow
            + "' "
            + "    AND ML.ValTo > '"
            + strTimeStampNow
            + "' "
            + "    AND ML.EffFrom <= '"
            + strTimeStampNow
            + "' "
            + "    AND ML.EffTo > '"
            + strTimeStampNow
            + "' "
            + "    AND ML.Linktype1 = ? "
            + ")    "
            + ",MYFLAGS1 (ENTITYTYPE,ENTITYID,ATTRIBUTECODE, ATTRIBUTEVALUE,NLSID,VALFROM,VALTO,EFFFROM,EFFTO) AS "
            + "( "
            + "  SELECT F.ENTITYTYPE "
            + "        ,F.ENTITYID "
            + "        ,F.ATTRIBUTECODE "
            + "        ,F.ATTRIBUTEVALUE "
            + "        ,MF.NLSID "
            + "        ,E.VALFROM "
            + "        ,E.VALTO "
            + "        ,E.EFFFROM "
            + "        ,E.EFFTO "
            + "  FROM MYFLAGS MF "
            + "  INNER JOIN "
            + strSchemaPDH
            + ".FLAG F ON "
            + "      F.ENTITYTYPE=? "
            + "  AND F.ATTRIBUTECODE = MF.ATTRIBUTECODE "
            + "  AND F.ATTRIBUTEVALUE = MF.FLAGVALUE "
            + "  AND F.ENTERPRISE='"
            + strEnterprise
            + "' "
            + "  AND F.ValFrom <= '"
            + strTimeStampNow
            + "' "
            + "  AND F.ValTo > '"
            + strTimeStampNow
            + "' "
            + "  AND F.EffFrom <= '"
            + strTimeStampNow
            + "' "
            + "  AND F.EffTo > '"
            + strTimeStampNow
            + "' "
            + " INNER JOIN "
            + strSchemaPDH
            + ".ENTITY E ON "
            + "       F.ENTITYTYPE=E.ENTITYTYPE "
            + "  AND  F.ENTITYID = E.ENTITYID  "
            + "  AND  E.ENTERPRISE='"
            + strEnterprise
            + "' "
            + "  AND E.ValFrom <= '"
            + strTimeStampNow
            + "' "
            + "  AND E.ValTo > '"
            + strTimeStampNow
            + "' "
            + "  AND E.EffFrom <= '"
            + strTimeStampNow
            + "' "
            + "  AND E.EffTo > '"
            + strTimeStampNow
            + "' "
            + "   "
            + ")         "
            + "SELECT  "
            + "       T.ENTITYID "
            + "       ,T.NLSID "
            + "       ,MF.VALFROM "
            + "       ,MF.VALTO "
            + "       ,MF.EFFFROM "
            + "       ,MF.EFFTO "
            + "FROM MYFLAGS1 MF        "
            + "INNER JOIN "
            + strSchemaPDH
            + ".TEXT T ON "
            + "      T.ENTITYTYPE=MF.ENTITYTYPE "
            + "  AND T.ENTITYID=MF.ENTITYID       "
            + "  AND T.ENTERPRISE='"
            + strEnterprise
            + "' "
            + "  AND T.NLSID = MF.NLSID "
            + "  AND T.ValFrom <= '"
            + strTimeStampNow
            + "' "
            + "  AND T.ValTo > '"
            + strTimeStampNow
            + "' "
            + "  AND T.EffFrom <= '"
            + strTimeStampNow
            + "' "
            + "  AND T.EffTo > '"
            + strTimeStampNow
            + "' "
            + "UNION "
            + "SELECT  "
            + "       LT.ENTITYID "
            + "       ,LT.NLSID "
            + "       ,MF.VALFROM "
            + "       ,MF.VALTO "
            + "       ,MF.EFFFROM "
            + "       ,MF.EFFTO "
            + "FROM MYFLAGS1 MF        "
            + "INNER JOIN "
            + strSchemaPDH
            + ".LONGTEXT LT ON "
            + "      LT.ENTITYTYPE=MF.ENTITYTYPE "
            + "  AND LT.ENTITYID=MF.ENTITYID       "
            + "  AND LT.ENTERPRISE='"
            + strEnterprise
            + "' "
            + "  AND LT.NLSID = MF.NLSID "
            + "  AND LT.ValFrom <= '"
            + strTimeStampNow
            + "' "
            + "  AND LT.ValTo > '"
            + strTimeStampNow
            + "' "
            + "  AND LT.EffFrom <= '"
            + strTimeStampNow
            + "' "
            + "  AND LT.EffTo > '"
            + strTimeStampNow
            + "' "
            + "UNION "
            + "SELECT  "
            + "       B.ENTITYID "
            + "       ,B.NLSID "
            + "       ,MF.VALFROM "
            + "       ,MF.VALTO "
            + "       ,MF.EFFFROM "
            + "       ,MF.EFFTO "
            + "FROM MYFLAGS1 MF        "
            + "INNER JOIN "
            + strSchemaPDH
            + ".BLOB B ON "
            + "      B.ENTITYTYPE=MF.ENTITYTYPE "
            + "  AND B.ENTERPRISE='"
            + strEnterprise
            + "' "
            + "  AND B.NLSID = MF.NLSID "
            + "  AND B.ValFrom <= '"
            + strTimeStampNow
            + "' "
            + "  AND B.ValTo > '"
            + strTimeStampNow
            + "' "
            + "  AND B.EffFrom <= '"
            + strTimeStampNow
            + "' "
            + "  AND B.EffTo > '"
            + strTimeStampNow
            + "' "
            + "  ORDER BY ENTITYID,NLSID,VALFROM FOR READ ONLY";
      case (ODS_GET_LAST_RUN_TIME):
        return "SELECT RUNTIME FROM "
            + strSchemaODS
            + ".TIMETABLE ORDER BY RUNTIME DESC";
      case (PDH_GET_COLUMN_LENGTH_PQ):
        return "SELECT LinkValue "
            + "    FROM "
            + strSchemaPDH
            + ".metalinkattr "
            + "    WHERE   "
            + "        LinkType='ODSLength' "
            + "        AND Enterprise  = '"
            + strEnterprise
            + "' "
            + "        AND LINKTYPE2   = ? "
            + "        AND ValFrom    <= '"
            + strTimeStampNow
            + "' "
            + "        AND '"
            + strTimeStampNow
            + "' < ValTo "
            + "        AND EffFrom <= '"
            + strTimeStampNow
            + "' "
            + "        AND '"
            + strTimeStampNow
            + "' < EffTo FOR READ ONLY";
      case (PDH_GET_ENTITY_KEY_CHANGES_PQ):
        return
            " WITH MYENTITY (ENTITYID,NLSID,VALFROM,VALTO,EFFFROM,EFFTO) AS "
            + " ( "
            + " SELECT E.ENTITYID "
            + " ,T.NLSID  "
            + " ,E.VALFROM  as VALFROM   "
            + " ,E.VALTO    as VALTO  "
            + " ,E.EFFFROM  as EFFFROM   "
            + " ,E.EFFTO    as EFFTO   "
            + " FROM "
            + " "
            + strSchemaPDH
            + ".ENTITYX E "
            + " INNER JOIN "
            + strSchemaPDH
            + ".TEXT T ON "
            + "     E.ENTERPRISE=T.ENTERPRISE "
            + " AND E.ENTITYTYPE=T.ENTITYTYPE "
            + " AND E.ENTITYID=T.ENTITYID "
            + " AND T.ValFrom <= '"
            + strTimeStampNow
            + "'"
            + " AND T.ValTo > '"
            + strTimeStampNow
            + "'"
            + " AND T.EffFrom <= '"
            + strTimeStampNow
            + "'"
            + " AND T.EffTo > '"
            + strTimeStampNow
            + "'"
            + " WHERE "
            + "  E.Enterprise =   '"
            + strEnterprise
            + "' "
            + "  AND E.EntityType = ? "
            + "      AND ("

            + "TIMESTAMP(E.VALFROM) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "' "
            + "         OR "
            + "TIMESTAMP(E.EffFrom) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "' "
            + "         OR "
            + "TIMESTAMP(E.ValTo) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "' "
            + "         OR "
            + "TIMESTAMP(E.EffTo) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "')"

            + " UNION "
            + " SELECT E.ENTITYID "
            + " ,LT.NLSID  "
            + " ,E.VALFROM  as VALFROM   "
            + " ,E.VALTO    as VALTO  "
            + " ,E.EFFFROM  as EFFFROM   "
            + " ,E.EFFTO    as EFFTO   "
            + " FROM "
            + " "
            + strSchemaPDH
            + ".ENTITYX E "
            + " INNER JOIN "
            + strSchemaPDH
            + ".LONGTEXT LT ON "
            + "     E.ENTERPRISE=LT.ENTERPRISE "
            + " AND E.ENTITYTYPE=LT.ENTITYTYPE "
            + " AND E.ENTITYID=LT.ENTITYID "
            + " AND LT.ValFrom <= '"
            + strTimeStampNow
            + "'"
            + " AND LT.ValTo > '"
            + strTimeStampNow
            + "'"
            + " AND LT.EffFrom <= '"
            + strTimeStampNow
            + "'"
            + " AND LT.EffTo > '"
            + strTimeStampNow
            + "'"
            + " WHERE "
            + "  E.Enterprise =   '"
            + strEnterprise
            + "' "
            + "  AND E.EntityType = ? "
            + "      AND ("

            + "TIMESTAMP(E.VALFROM) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "' "
            + "         OR "
            + "TIMESTAMP(E.EffFrom) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "' "
            + "         OR "
            + "TIMESTAMP(E.ValTo) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "' "
            + "         OR "
            + "TIMESTAMP(E.EffTo) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "')"
            + " UNION "
            + " SELECT E.ENTITYID "
            + " ,B.NLSID  "
            + " ,E.VALFROM  as VALFROM   "
            + " ,E.VALTO    as VALTO  "
            + " ,E.EFFFROM  as EFFFROM   "
            + " ,E.EFFTO    as EFFTO   "
            + " FROM "
            + " "
            + strSchemaPDH
            + ".ENTITYX E "
            + " INNER JOIN "
            + strSchemaPDH
            + ".blob b ON "
            + "     E.ENTERPRISE=b.ENTERPRISE "
            + " AND E.ENTITYTYPE=b.ENTITYTYPE "
            + " AND E.ENTITYID=b.ENTITYID "
            + " AND b.ValFrom <= '"
            + strTimeStampNow
            + "'"
            + " AND b.ValTo > '"
            + strTimeStampNow
            + "'"
            + " AND b.EffFrom <= '"
            + strTimeStampNow
            + "'"
            + " AND b.EffTo > '"
            + strTimeStampNow
            + "'"
            + " WHERE "
            + "  E.Enterprise =   '"
            + strEnterprise
            + "' "
            + "  AND E.EntityType = ? "
            + "      AND ("

            + "TIMESTAMP(E.VALFROM) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "' "
            + "         OR "
            + "TIMESTAMP(E.EffFrom) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "' "
            + "         OR "
            + "TIMESTAMP(E.ValTo) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "' "
            + "         OR "
            + "TIMESTAMP(E.EffTo) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "')"
            + " UNION "
            + "  SELECT E.ENTITYID   "
            + "  ,1 as NLSID    "
            + "  ,E.VALFROM  as VALFROM     "
            + "  ,E.VALTO    as VALTO    "
            + "  ,E.EFFFROM  as EFFFROM     "
            + "  ,E.EFFTO    as EFFTO     "
            + "  FROM   "
            + " "
            + strSchemaPDH
            + ".ENTITYX E "
            + " INNER JOIN "
            + strSchemaPDH
            + ".flag f ON   "
            + "      E.ENTERPRISE=f.ENTERPRISE   "
            + "  AND E.ENTITYTYPE=f.ENTITYTYPE   "
            + "  AND E.ENTITYID=f.ENTITYID   "
            + " AND f.ValFrom <= '"
            + strTimeStampNow
            + "'"
            + " AND f.ValTo > '"
            + strTimeStampNow
            + "'"
            + " AND f.EffFrom <= '"
            + strTimeStampNow
            + "'"
            + " AND f.EffTo > '"
            + strTimeStampNow
            + "'"
            + "  WHERE   "
            + "  E.Enterprise =   '"
            + strEnterprise
            + "' "
            + "  AND E.EntityType = ? "
            + "      AND ("

            + "TIMESTAMP(E.VALFROM) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "' "
            + "         OR "
            + "TIMESTAMP(E.EffFrom) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "' "
            + "         OR "
            + "TIMESTAMP(E.ValTo) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "' "
            + "         OR "
            + "TIMESTAMP(E.EffTo) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "')"
            + " )  "
            + " SELECT DISTINCT  "
            + "       E.ENTITYID as ENTITYID   "
            + "      ,COALESCE(E.NLSID,1)    as NLSID   "
            + "      ,E.VALFROM  as VALFROM   "
            + "      ,E.VALTO    as VALTO  "
            + "      ,E.EFFFROM  as EFFFROM   "
            + "      ,E.EFFTO    as EFFTO   "
            + "  FROM MYENTITY E  "
            + "  INNER JOIN "
            + strSchemaPDH
            + ".FLAGX F ON "
            + "      F.Enterprise  = '"
            + strEnterprise
            + "' "
            + "      AND F.EntityType = ? "
            + "      AND F.EntityID= E.EntityID  "
            + "      AND F.ATTRIBUTECODE <> 'XMLIDLABRSTATUS'   "
            + "      AND ("

            + "TIMESTAMP(f.VALFROM) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "' "
            + "         OR "
            + "TIMESTAMP(f.EffFrom) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "' "
            + "         OR "
            + "TIMESTAMP(f.ValTo) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "' "
            + "         OR "
            + "TIMESTAMP(f.EffTo) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "')"
            + " UNION "
            + " SELECT DISTINCT  "
            + "       E.ENTITYID as ENTITYID  "
            + "      ,T.NLSID    as NLSID  "
            + "      ,E.VALFROM  as VALFROM  "
            + "      ,E.VALTO    as VALTO  "
            + "      ,E.EFFFROM  as EFFFROM  "
            + "      ,E.EFFTO    as EFFTO  "
            + "   FROM   "
            + strSchemaPDH
            + ".ENTITYX E  "
            + "   INNER JOIN "
            + strSchemaPDH
            + ".TEXTx T ON  "
            + "     T.Enterprise = E.Enterprise  "
            + "     AND T.EntityType = E.EntityType  "
            + "     AND T.EntityID = E.EntityID   "


            + "      AND ("

            + "TIMESTAMP(T.VALFROM) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "' "
            + "         OR "
            + "TIMESTAMP(T.EffFrom) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "' "
            + "         OR "
            + "TIMESTAMP(T.ValTo) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "' "
            + "         OR "
            + "TIMESTAMP(T.EffTo) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "')"

            + "   WHERE     "
            + "       E.Enterprise =   '"
            + strEnterprise
            + "' "
            + "       AND E.EntityType = ? "
            + "      AND ("

            + "TIMESTAMP(E.VALFROM) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "' "
            + "         OR "
            + "TIMESTAMP(E.EffFrom) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "' "
            + "         OR "
            + "TIMESTAMP(E.ValTo) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "' "
            + "         OR "
            + "TIMESTAMP(E.EffTo) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "')"
            + " UNION "
            + " SELECT DISTINCT  "
            + "       E.ENTITYID as ENTITYID  "
            + "      ,LT.NLSID    as NLSID "
            + "      ,E.VALFROM  as VALFROM "
            + "      ,E.VALTO    as VALTO "
            + "      ,E.EFFFROM  as EFFFROM "
            + "      ,E.EFFTO    as EFFTO "
            + "   FROM   "
            + strSchemaPDH
            + ".ENTITYX E "
            + "   INNER JOIN "
            + strSchemaPDH
            + ".LongTextx LT ON "
            + "      LT.Enterprise = E.Enterprise "
            + "      AND LT.EntityType = E.EntityType "
            + "      AND LT.EntityID = E.EntityID "

            + "      AND ("

            + "TIMESTAMP(LT.VALFROM) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "' "
            + "         OR "
            + "TIMESTAMP(LT.EffFrom) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "' "
            + "         OR "
            + "TIMESTAMP(LT.ValTo) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "' "
            + "         OR "
            + "TIMESTAMP(LT.EffTo) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "')"

            + "   WHERE   "
            + "      E.Enterprise =   '"
            + strEnterprise
            + "' "
            + "      AND E.EntityType = ? "
            + "      AND ("

            + "TIMESTAMP(E.VALFROM) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "' "
            + "         OR "
            + "TIMESTAMP(E.EffFrom) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "' "
            + "         OR "
            + "TIMESTAMP(E.ValTo) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "' "
            + "         OR "
            + "TIMESTAMP(E.EffTo) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "')"

            + " UNION  "
            + " SELECT DISTINCT "
            + "       E.ENTITYID as ENTITYID "
            + "      ,B.NLSID    as NLSID "
            + "      ,E.VALFROM  as VALFROM "
            + "      ,E.VALTO    as VALTO "
            + "      ,E.EFFFROM  as EFFFROM "
            + "      ,E.EFFTO    as EFFTO "
            + "   FROM  "
            + strSchemaPDH
            + ".ENTITYX E "
            + "   INNER JOIN "
            + strSchemaPDH
            + ".BLOBx B ON   "
            + "       B.Enterprise = E.Enterprise "
            + "        AND B.EntityType = E.EntityType "
            + "        AND B.EntityID = E.EntityID  "
            + "      AND ("

            + "TIMESTAMP(B.VALFROM) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "' "
            + "         OR "
            + "TIMESTAMP(B.EffFrom) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "' "
            + "         OR "
            + "TIMESTAMP(B.ValTo) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "' "
            + "         OR "
            + "TIMESTAMP(B.EffTo) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "')"
            + "   WHERE   "
            + "      E.Enterprise =   '"
            + strEnterprise
            + "' "
            + "      AND E.EntityType = ? "
            + "      AND ("
            + "TIMESTAMP(E.VALFROM) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "' "
            + "         OR "
            + "TIMESTAMP(E.EffFrom) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "' "
            + "         OR "
            + "TIMESTAMP(E.ValTo) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "' "
            + "         OR "
            + "TIMESTAMP(E.EffTo) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "')"
            + "  ORDER BY ENTITYID,NLSID,VALFROM FOR READ ONLY";
      case (PDH_GET_RELATOR_KEY_CHANGES_PQ):
        return " SELECT DISTINCT  "
            + "       R.ENTITYID as ENTITYID  "
            + "      ,T.NLSID    as NLSID  "
            + "      ,R.VALFROM  as VALFROM  "
            + "      ,R.VALTO    as VALTO  "
            + "      ,R.EFFFROM  as EFFFROM  "
            + "      ,R.EFFTO    as EFFTO  "
            + "   FROM   opicm.RELATORx R  "
            + "   INNER JOIN opicm.TEXTx T ON  "
            + "     T.Enterprise = R.Enterprise  "
            + "     AND T.EntityType = R.EntityType  "
            + "     AND T.EntityID = R.EntityID   "
            + "   WHERE     "
            + "       R.Enterprise =   '"
            + strEnterprise
            + "' "
            + "       AND R.EntityType = ? "

            + "      AND ("
            + "TIMESTAMP(r.VALFROM) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "' "
            + "         OR "
            + "TIMESTAMP(r.EffFrom) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "' "
            + "         OR "
            + "TIMESTAMP(r.ValTo) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "' "
            + "         OR "
            + "TIMESTAMP(r.EffTo) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "')"

            + " UNION "
            + " SELECT DISTINCT  "
            + "       R.ENTITYID as ENTITYID  "
            + "      ,LT.NLSID    as NLSID "
            + "      ,R.VALFROM  as VALFROM "
            + "      ,R.VALTO    as VALTO "
            + "      ,R.EFFFROM  as EFFFROM "
            + "      ,R.EFFTO    as EFFTO "
            + "   FROM   opicm.RELATORx R "
            + "   INNER JOIN opicm.LongTextx LT ON "
            + "      LT.Enterprise = R.Enterprise "
            + "      AND LT.EntityType = R.EntityType "
            + "      AND LT.EntityID = R.EntityID "
            + "   WHERE   "
            + "      R.Enterprise =   '"
            + strEnterprise
            + "' "
            + "      AND R.EntityType = ? "
            + "      AND ("
            + "TIMESTAMP(r.VALFROM) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "' "
            + "         OR "
            + "TIMESTAMP(r.EffFrom) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "' "
            + "         OR "
            + "TIMESTAMP(r.ValTo) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "' "
            + "         OR "
            + "TIMESTAMP(r.EffTo) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "')"
            + " UNION  "
            + " SELECT DISTINCT "
            + "       R.ENTITYID as ENTITYID "
            + "      ,B.NLSID    as NLSID "
            + "      ,R.VALFROM  as VALFROM "
            + "      ,R.VALTO    as VALTO "
            + "      ,R.EFFFROM  as EFFFROM "
            + "      ,R.EFFTO    as EFFTO "
            + "   FROM  opicm.Relatorx R "
            + "   INNER JOIN opicm.BLOBx B ON   "
            + "       B.Enterprise = R.Enterprise "
            + "        AND B.EntityType = R.EntityType "
            + "        AND B.EntityID = R.EntityID  "
            + "   WHERE   "
            + "      R.Enterprise =   '"
            + strEnterprise
            + "' "
            + "      AND R.EntityType = ? "
            + "      AND ("
            + "TIMESTAMP(r.VALFROM) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "' "
            + "         OR "
            + "TIMESTAMP(r.EffFrom) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "' "
            + "         OR "
            + "TIMESTAMP(r.ValTo) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "' "
            + "         OR "
            + "TIMESTAMP(r.EffTo) BETWEEN '"
            + strTimeStampLastRun
            + "'  AND '"
            + strTimeStampNow
            + "')"
            + "  ORDER BY ENTITYID,NLSID,VALFROM FOR READ ONLY";
      case (PDH_GET_META_ENTITIES_ATTRIBUTES):
        return "SELECT DISTINCT mea.linktype1 AS entitytype "
            + "   ,UCASE(ma.entitytype) AS attributecode  "
            + "   ,RTRIM(coalesce(ml.linkValue,'64'))  as odsLength "
            + "   ,RTRIM(coalesce(TS.LINKVALUE,'TSPACE01'))  as TABLESPACE "
            + "   ,RTRIM(coalesce(IS.linkValue,'ISPACE02'))  as INDEXSPACE "
            + " FROM  "
            + strSchemaPDH
            + ".metaentity  me "
            + " INNER JOIN "
            + strSchemaPDH
            + ".metalinkattr mea ON "
            + "   me.entitytype=mea.linktype1  "
            + "   AND mea.linktype='Entity/Attribute'  "
            + "   AND mea.linkcode='EntityAttribute'  "
            + "   AND mea.linkvalue='L'  "
            + "   AND me.enterprise = mea.enterprise "
            + "   AND mea.valto > '"
            + strTimeStampNow
            + "'  "
            + "   AND mea.effto > '"
            + strTimeStampNow
            + "' "
            + "   AND mea.valFrom <= '"
            + strTimeStampNow
            + "'  "
            + "   AND mea.effFrom <= '"
            + strTimeStampNow
            + "' "
            + " LEFT join "
            + strSchemaPDH
            + ".metalinkattr ml on "
            + "   me.enterprise = ml.enterprise  "
            + "   AND mea.linktype2 = ml.linktype2  "
            + "   AND ml.linktype1='ODS'  "
            + "   AND ml.linktype = 'ODSLength' "
            + "   AND ml.valto > '"
            + strTimeStampNow
            + "'  "
            + "   AND ml.effto > '"
            + strTimeStampNow
            + "' "
            + "   AND ml.valFrom <= '"
            + strTimeStampNow
            + "'  "
            + "   AND ml.effFrom <= '"
            + strTimeStampNow
            + "' "
            + " LEFT join "
            + strSchemaPDH
            + ".metalinkattr TS on "
            + "   me.enterprise = TS.enterprise "
            + "   AND mea.linktype1 = TS.linktype2  "
            + "   AND ts.linktype1='ODS'  "
            + "   AND ts.linktype = 'ODSConfiguration'  "
            + "   AND ts.linkCode = 'TSPACE' "
            + "   AND ts.valto > '"
            + strTimeStampNow
            + "'  "
            + "   AND ts.effto > '"
            + strTimeStampNow
            + "' "
            + "   AND ts.valFrom <= '"
            + strTimeStampNow
            + "'  "
            + "   AND ts.effFrom <= '"
            + strTimeStampNow
            + "' "
            + " LEFT join "
            + strSchemaPDH
            + ".metalinkattr IS on "
            + "   me.enterprise = ml.enterprise  "
            + "   AND mea.linktype1 = IS.linktype2  "
            + "   AND Is.linktype1='ODS'  "
            + "   AND Is.linktype = 'ODSConfiguration'  "
            + "   AND Is.linkCode = 'ISPACE' "
            + "   AND is.valto > '"
            + strTimeStampNow
            + "'  "
            + "   AND is.effto > '"
            + strTimeStampNow
            + "' "
            + "   AND is.valFrom <= '"
            + strTimeStampNow
            + "'  "
            + "   AND is.effFrom <= '"
            + strTimeStampNow
            + "' "
            + " INNER JOIN "
            + strSchemaPDH
            + ".metaentity ma ON "
            + "   mea.linktype2=ma.entitytype "
            + "   AND mea.enterprise = ma.enterprise "
            + "   AND ma.entityclass IN ( 'I','T','U','S','A') "
            + //Id, Text,Status and Unique flags
            "   AND ma.valto > '"
            + strTimeStampNow
            + "'  "
            + "   AND ma.effto > '"
            + strTimeStampNow
            + "' "
            + "   AND ma.valFrom <= '"
            + strTimeStampNow
            + "'  "
            + "   AND ma.effFrom <= '"
            + strTimeStampNow
            + "' "
            + " WHERE  "
            + "   me.entityclass IN ('Entity','Relator') "
            + "   AND me.enterprise= '"
            + strEnterprise
            + "'  "
            + "   AND me.valto > '"
            + strTimeStampNow
            + "'  "
            + "   AND me.effto > '"
            + strTimeStampNow
            + "'  "
            + "   AND me.valFrom <= '"
            + strTimeStampNow
            + "'  "
            + "   AND me.effFrom <= '"
            + strTimeStampNow
            + "'  "
            + " ORDER BY entitytype,attributecode FOR READ ONLY";
      case (PDH_GET_ENTITY_SNAPSHOT_PQ):
        return
            " WITH MYTABLE (ENTITYTYPE,ENTITYID,ATTRIBUTECODE,ATTRIBUTETYPE) AS  "
            + " ( SELECT  DISTINCT "
            + "      E.EntityType  "
            + "     ,E.EntityID  "
            + "     ,MA.EntityType  as AttributeCode "
            + "     ,MA.EntityClass as AttributeType "
            + "   FROM  "
            + strSchemaPDH
            + ".ENTITY E     "
            + "   INNER JOIN  "
            + strSchemaPDH
            + ".metalinkattr ML ON   "
            + "       ML.Enterprise = '"
            + strEnterprise
            + "'"
            + "       AND ML.linktype = 'Entity/Attribute'   "
            + "       AND ML.linkcode='EntityAttribute'  "
            + "       AND ML.linkvalue='L'  "
            + "       AND ML.linktype1 = E.EntityType   "
            + "       AND ML.ValFrom <= ? AND ? <ML.ValTo  "
            + "       AND ML.EffFrom <= ? AND ? < ML.EffTo  "
            + "   INNER JOIN  "
            + strSchemaPDH
            + ".MetaEntity MA ON  "
            + "       MA.Enterprise = '"
            + strEnterprise
            + "'"
            + "       AND MA.EntityType = ML.Linktype2 "
            + "       AND MA.EntityClass in ('T','I','U','S','A')  "
            + "       AND MA.ValFrom <= ? AND ? < MA.ValTo  "
            + "       AND MA.EffFrom <= ? AND ? < MA.EffTo  "
            + "  WHERE  "
            + "     E.Enterprise ='"
            + strEnterprise
            + "'"
            + "     AND E.EntityType = ?  "
            + "     AND E.Entityid = ?  "
            + "       AND E.ValFrom <= ? AND ? < E.ValTo  "
            + "       AND E.EffFrom <= ? AND ? < E.EffTo  "
            + " )  "
            + "  "
            + " SELECT DISTINCT"
            + "        MT.AttributeCode  as AttributeCode         "
            +
            "       ,COALESCE(RTRIM(t.AttributeValue),' ') as AttributeValue "
            + " FROM MYTABLE MT "
            + " LEFT JOIN "
            + strSchemaPDH
            + ".TEXT T ON "
            + "       T.enterprise = '"
            + strEnterprise
            + "'"
            + "       AND T.entitytype = MT.entitytype "
            + "       AND T.entityid = MT.entityid "
            + "       AND T.attributecode = MT.attributecode "
            + "       AND T.nlsid = ? "
            + "       AND T.ValFrom <= ? AND ? < T.ValTo "
            + "       AND T.EffFrom <= ? AND ? < T.EffTo "
            + " WHERE "
            + "       MT.attributeType in ('T','I') "
            + " UNION ALL "
            + " SELECT DISTINCT"
            + "         MT.AttributeCode  as AttributeCode         "
            +
            "         ,COALESCE(RTRIM(MD.LongDescription),' ') as AttributeValue    "
            + " FROM mytable MT "
            + " LEFT JOIN "
            + strSchemaPDH
            + ".flag F ON "
            + "             F.enterprise = '"
            + strEnterprise
            + "'"
            + "         AND F.entitytype = MT.entitytype "
            + "         AND F.entityid = MT.entityid "
            + "         AND F.attributecode = MT.attributecode "
            + "         AND F.ValFrom <= ? AND ? < F.ValTo "
            + "         AND F.EffFrom <= ? AND ? < F.EffTo "
            + " LEFT JOIN  "
            + strSchemaPDH
            + ".MetaDescription MD ON "
            + "           MD.Enterprise = F.Enterprise  "
            + "       AND MD.descriptionType = F.AttributeCode "
            + "       AND MD.DescriptionClass = F.AttributeValue "
            + "       AND MD.NLSID = ? "
            + "       AND MD.valfrom <= ? and  ? < MD.ValTo "
            + "       AND MD.efffrom <= ? and  ?  < MD.effTo "
            + " WHERE "
            + " MT.attributeType IN ('U','S','A') "
            + " ORDER BY attributecode ";

      case (PDH_GET_RELATOR_CHANGES_PQ):
        return "  SELECT   "
            + "  R.ENTITYTYPE as ENTITYTYPE,  "
            + "  R.ENTITYID as ENTITYID,  "
            + "  R.Entity1Type as ENTITY1TYPE,  "
            + "  R.Entity1ID as ENTITY1ID,  "
            + "  R.Entity2Type as ENTITY2TYPE,  "
            + "  R.Entity2ID as ENTITY2ID ,  "
            + "  R.valto As VALTO,  "
            + "  R.effto AS EFFTO,  "
            + "  R.valfrom AS VALFROM  "
            + "  FROM  OPICM.Relatorx R   "
            + "  INNER JOIN  OPICM.METAENTITY ME ON   "
            + "  ME.ENTERPRISE = R.ENTERPRISE AND   "
            + "  ME.ENTITYTYPE = R.ENTITYTYPE AND   "
            + "  ME.ENTITYCLASS = 'Relator' AND   "
            + "  ME.ValFrom <= ? AND ? < ME.ValTo AND   "
            + "  ME.EffFrom <= ? AND ? < ME.EffTo   "
            + "  WHERE "
            + "  R.Enterprise = ?  AND    "
            + "  R.valto = '9999-12-31-00.00.00.000000' AND  "
            + "  R.ENTITYTYPE NOT IN ('PDHUPDATEREL')   AND    "
            + "  R.valfrom >=  ?  "
            + "  UNION  "
            + "  SELECT   "
            + "  R.ENTITYTYPE as ENTITYTYPE,  "
            + "  R.ENTITYID as ENTITYID,  "
            + "  R.Entity1Type as ENTITY1TYPE,  "
            + "  R.Entity1ID as ENTITY1ID,  "
            + "  R.Entity2Type as ENTITY2TYPE,  "
            + "  R.Entity2ID as ENTITY2ID ,  "
            + "  R.valto As VALTO,  "
            + "  R.effto AS EFFTO,  "
            + "  R.valfrom AS VALFROM  "
            + "  FROM  OPICM.Relatorx R   "
            + "  INNER JOIN  OPICM.METAENTITY ME ON   "
            + "  ME.ENTERPRISE = R.ENTERPRISE AND   "
            + "  ME.ENTITYTYPE = R.ENTITYTYPE AND   "
            + "  ME.ENTITYCLASS = 'Relator' AND   "
            + "  ME.ValFrom <= ? AND  ? < ME.ValTo AND   "
            + "  ME.EffFrom <= ? AND ? < ME.EffTo   "
            + "  WHERE  "
            + "    R.Enterprise = ?  AND    "
            + "    R.ENTITYTYPE NOT IN ('PDHUPDATEREL')   AND    "
            + "    R.Valto = '9999-12-31-00.00.00.000000' AND   "
            + "    TIMESTAMP(R.Efffrom) BETWEEN ?  AND  ?  "
            + "  UNION  "
            + "  SELECT   "
            + "  R.ENTITYTYPE as ENTITYTYPE,  "
            + "  R.ENTITYID as ENTITYID,  "
            + "  R.Entity1Type as ENTITY1TYPE,  "
            + "  R.Entity1ID as ENTITY1ID,  "
            + "  R.Entity2Type as ENTITY2TYPE, "
            + "  R.Entity2ID as ENTITY2ID ,  "
            + "  R.valto As VALTO,  "
            + "  R.effto AS EFFTO,  "
            + "  R.valfrom AS VALFROM  "
            + "  FROM  OPICM.Relatorx R   "
            + "  INNER JOIN  OPICM.METAENTITY ME ON   "
            + "  ME.ENTERPRISE = R.ENTERPRISE AND   "
            + "  ME.ENTITYTYPE = R.ENTITYTYPE AND   "
            + "  ME.ENTITYCLASS = 'Relator' AND   "
            + "  ME.ValFrom <= ? AND  ? < ME.ValTo AND   "
            + "  ME.EffFrom <= ? AND ? < ME.EffTo   "
            + "  WHERE  "
            + "    R.Enterprise = ?  AND    "
            + "    R.ENTITYTYPE NOT IN ('PDHUPDATEREL')   AND    "
            + "    R.Valto = '9999-12-31-00.00.00.000000' AND   "
            + "    TIMESTAMP(R.EffTo) BETWEEN ?     AND    ?   "
            + "  ORDER BY  EntityType, EntityID, Valfrom  "
            + "  FOR READ ONLY  ";
      case (PDH_GET_FLAG_CHANGES_PQ): //Gets the 'F' type and also the 'A' type
        return " SELECT  "
            + "          F.ENTITYTYPE,  "
            + "          F.ENTITYID,  "
            + "          F.ATTRIBUTECODE,  "
            + "          F.ATTRIBUTEVALUE,  "
            + "          F.VALTO,  "
            + "          F.EFFTO,  "
            + "          F.ValFrom  "
            + " FROM  "
            + strSchemaPDH
            + ".FLAGx F  "
            + " INNER JOIN    "
            + strSchemaPDH
            + ".METAENTITY MA   ON  "
            + "        MA.ENTERPRISE = '"
            + strEnterprise
            + "'"
            + "        AND        MA.ENTITYCLASS = 'F'        "
            + "        AND MA.ValFrom <= ? "
            + "        AND  ?< MA.ValTo  "
            + "        AND MA.EffFrom <= ? "
            + "        AND ?< MA.EffTo  "
            + "  WHERE  "
            + "  F.ENTERPRISE='"
            + strEnterprise
            + "'"
            + "  and F.ENTITYTYPE NOT IN ('PDHUPDATE','PDHUPDATEACT','LEADSXML','MIWXML','DGENTITY') "
            + "  and F.ATTRIBUTECODE=MA.ENTITYTYPE  "
            + "  AND  "
            + "       (TIMESTAMP(F.ValFrom) BETWEEN ? and ? "
            + "   "
            + "   OR "
            + "        TIMESTAMP(F.VALTO) BETWEEN ? and ? "
            + "   OR        "
            + "       TIMESTAMP(F.EFFFrom) BETWEEN ? and ? "
            + "   OR "
            + "       TIMESTAMP(F.EFFTO) BETWEEN ? and ?)"
            + "UNION "
            + " SELECT  "
            + "          F.ENTITYTYPE,  "
            + "          F.ENTITYID,  "
            + "          F.ATTRIBUTECODE,  "
            + "          F.ATTRIBUTEVALUE,  "
            + "          F.VALTO,  "
            + "          F.EFFTO,  "
            + "          F.ValFrom  "
            + " FROM  "
            + strSchemaPDH
            + ".FLAGx F  "
            + " INNER JOIN    "
            + strSchemaPDH
            + ".METAENTITY MA   ON  "
            + "        MA.ENTERPRISE = '"
            + strEnterprise
            + "'"
            + "        AND        MA.ENTITYCLASS IN ('U','S','A')        "
            + "        AND MA.ValFrom <= ? "
            + "        AND  ?< MA.ValTo  "
            + "        AND MA.EffFrom <= ? "
            + "        AND ?< MA.EffTo  "
            + "  INNER JOIN "
            + strSchemaPDH
            + ".METALINKATTR MLA   ON  "
            + "        MLA.ENTERPRISE = '"
            + strEnterprise
            + "'"
            + "        AND MA.ENTITYTYPE=MLA.LINKTYPE2 "
            + "        AND MLA.LINKCODE='EntityAttribute' "
            + "        AND MLA.LINKTYPE='Entity/Attribute'  "
            + "        AND MLA.LINKVALUE='A'                "
            + "        AND F.ENTITYTYPE=MLA.LINKTYPE1 "
            + //Fix for flag changes 4/4/2002
            "        AND MLA.ValFrom <= ? "
            + "        AND  ?< MLA.ValTo  "
            + "        AND MLA.EffFrom <= ? "
            + "        AND ?< MLA.EffTo  "
            + "  WHERE  "
            + "  F.ENTERPRISE='"
            + strEnterprise
            + "'"
            + "  and F.ENTITYTYPE NOT IN ('PDHUPDATE','PDHUPDATEACT') "
            + "  and F.ATTRIBUTECODE=MA.ENTITYTYPE  "
            + "  AND  "
            + "       (TIMESTAMP(F.ValFrom) BETWEEN ? and ? "
            + "   "
            + "   OR "
            + "        TIMESTAMP(F.VALTO) BETWEEN ? and ? "
            + "   OR        "
            + "       TIMESTAMP(F.EFFFrom) BETWEEN ? and ? "
            + "   OR "
            + "       TIMESTAMP(F.EFFTO) BETWEEN ? and ?)";
      case (PDH_GET_MD_CHANGES_PQ):
        return "SELECT "
            + "   VALFROM, "
            + "   DESCRIPTIONTYPE, "
            + "   DESCRIPTIONCLASS, "
            + "   SHORTDESCRIPTION, "
            + "   LONGDESCRIPTION,  "
            + "   VALTO,            "
            + "   EFFTO,            "
            + "   NLSID "
            + "FROM "
            + strSchemaPDH
            + ".METADESCRIPTION MD "
            + " WHERE MD.ENTERPRISE= ?  AND    "
            + "       (TIMESTAMP(MD.ValFrom) BETWEEN ? and ? "
            + "   "
            + "   OR "
            + "        TIMESTAMP(MD.VALTO) BETWEEN ? and ? "
            + "   OR        "
            + "       TIMESTAMP(MD.EFFFrom) BETWEEN ? and ? "
            + "   OR "
            + "       TIMESTAMP(MD.EFFTO) BETWEEN ? and ?)";
      case (PDH_GET_LONGTEXT_CHANGES_PQ):
        return "SELECT "
            + "LT.ENTITYTYPE, "
            + "LT.ENTITYID, "
            + "LT.NLSID, "
            + "UCASE(LT.ATTRIBUTECODE)  as ATTRIBUTECODE,"
            + "LT.ATTRIBUTEVALUE, "
            + "MA.ENTITYCLASS  as ATTRIBUTETYPE,"
            + "LT.VALTO, "
            + "LT.EFFTO "
            + "FROM "
            + strSchemaPDH
            + ".LONGTEXTx LT "
            + "INNER JOIN "
            + strSchemaPDH
            + ".metaentity MA ON "
            + "MA.ENTERPRISE = LT.ENTERPRISE AND "
            + "UCASE(MA.ENTITYTYPE) = UCASE(LT.ATTRIBUTECODE) AND "
            + "MA.ENTITYCLASS in ('L','X') AND "
            + "MA.ValFrom <= ? AND ? < MA.ValTo AND "
            + "MA.EffFrom <= ? AND ? < MA.EffTo "
            + " WHERE "
            + "LT.Enterprise = ?  AND  "
            + "LT.ATTRIBUTECODE NOT IN ('MXMSG','LXMSG') AND "
            + "       (TIMESTAMP(LT.ValFrom) BETWEEN ? and ? "
            + "   "
            + "   OR "
            + "        TIMESTAMP(LT.VALTO) BETWEEN ? and ? "
            + "   OR        "
            + "       TIMESTAMP(LT.EFFFrom) BETWEEN ? and ? "
            + "   OR "
            + "       TIMESTAMP(LT.EFFTO) BETWEEN ? and ?) "
            + "ORDER BY "
            + "LT.EntityID, LT.ATTRIBUTECODE, LT.valfrom for READ ONLY";
      case (PDH_GET_BLOB_CHANGES_PQ):
        return "SELECT "
            + "B.ENTITYTYPE, "
            + "B.ENTITYID, "
            + "B.NLSID, "
            + "UCASE(B.ATTRIBUTECODE)  as ATTRIBUTECODE,"
            + "B.BLOBEXTENSION, "
            +
            //              "B.ATTRIBUTEVALUE, " +          //Getting this in  a separate query
            "MA.EntityType  as ATTRIBUTETYPE, "
            + "B.VALTO, "
            + "B.EFFTO, "
            + "B.VALFROM, "
            + "B.EFFFROM "
            + "FROM "
            + strSchemaPDH
            + ".BLOBx B "
            + "INNER JOIN "
            + strSchemaPDH
            + ".metaentity MA ON "
            + "MA.ENTERPRISE = B.ENTERPRISE AND "
            + "UCASE(MA.ENTITYTYPE) = UCASE(B.ATTRIBUTECODE) AND "
            + "MA.ENTITYCLASS IN ('P','D','B') AND "
            + "MA.ValFrom <= ? AND ? < MA.ValTo AND "
            + "MA.EffFrom <= ? AND ? < MA.EffTo "
            + " WHERE "
            + "B.Enterprise = ?  AND  "
            + "       (TIMESTAMP(B.ValFrom) BETWEEN ? and ? "
            + "   "
            + "   OR "
            + "        TIMESTAMP(B.VALTO) BETWEEN ? and ? "
            + "   OR        "
            + "       TIMESTAMP(B.EFFFrom) BETWEEN ? and ? "
            + "   OR "
            + "       TIMESTAMP(B.EFFTO) BETWEEN ? and ?) "

            + "ORDER BY "
            + "B.EntityID, B.NLSID, B.valto FOR READ ONLY";
      case (ODS_CHECK_MD_EXIST_PQ):

        return "SELECT ATTRIBUTECODE FROM "
            + strSchemaODS
            + ".METAFLAGVALUE "
            + " WHERE "
            + " ATTRIBUTECODE = ? AND "
            + " NLSID = ? AND "
            + " ATTRIBUTEVALUE  = ? FOR READ ONLY";
      case (ODS_CHECK_FLAG_EXIST_PQ):
        return "SELECT ENTITYID FROM "
            + strSchemaODS
            + ".FLAG "
            + " WHERE "
            + " ENTITYTYPE = ? AND "
            + " ENTITYID = ? AND "
            + " ATTRIBUTECODE  = ? AND "
            + " ATTRIBUTEVALUE  = ? FOR READ ONLY";
      case (ODS_CHECK_LONGTEXT_EXIST_PQ):
        return "SELECT ENTITYID FROM "
            + strSchemaODS
            + ".LONGTEXT "
            + " WHERE "
            + " ENTITYTYPE = ? AND "
            + " ENTITYID = ? AND "
            + " NLSID = ? AND "
            + " ATTRIBUTECODE =  ? FOR READ ONLY";
      case (ODS_CHECK_BLOB_EXIST_PQ):
        return "SELECT ENTITYTYPE FROM "
            + strSchemaODS
            + ".BLOB "
            + " WHERE "
            + " ENTITYTYPE = ? AND "
            + " ENTITYID = ? AND "
            + " NLSID = ? AND "
            + " ATTRIBUTECODE  = ? FOR READ ONLY";
      case (ODS_CHECK_RELATOR_EXIST_PQ):
        return "SELECT ENTITYID  FROM "
            + strSchemaODS
            + ".RELATOR "
            + "WHERE "
            + "ENTITYTYPE = ? AND "
            + " ENTITYID = ? FOR READ ONLY";
      case (ODS_UPDATE_BLOB_PQ):
        return "UPDATE "
            + strSchemaODS
            + ".BLOB "
            + " SET valFrom = "
            + (strTimestampMode.equals("TIMESTAMP")
               ? "current timestamp"
               : "'" + strTimeStampBeginOfDay + "'")
            + ", BLOBEXTENSION = ? , ATTRIBUTEVALUE = ?"
            +
            " WHERE ENTITYTYPE = ? AND ENTITYID = ? AND NLSID = ? AND ATTRIBUTECODE = ? ";
      case (ODS_UPDATE_FLAG_PQ):
        return "UPDATE "
            + strSchemaODS
            + ".FLAG "
            + " SET valFrom = "
            + (strTimestampMode.equals("TIMESTAMP")
               ? "current timestamp"
               : "'" + strTimeStampBeginOfDay + "'")
            + " WHERE ENTITYTYPE = ? AND ENTITYID = ? AND ATTRIBUTECODE = ? AND  ATTRIBUTEVALUE = ?";
      case (ODS_UPDATE_LONGTEXT_PQ):
        return "UPDATE "
            + strSchemaODS
            + ".LONGTEXT "
            + " SET valFrom = "
            + (strTimestampMode.equals("TIMESTAMP")
               ? "current timestamp"
               : "'" + strTimeStampBeginOfDay + "'")
            + " ,ATTRIBUTEVALUE = ?"
            +
            " WHERE ENTITYTYPE = ? AND ENTITYID = ? AND NLSID = ? AND ATTRIBUTECODE = ?";
      case (ODS_UPDATE_RELATOR_PQ):
        return "UPDATE "
            + strSchemaODS
            + ".RELATOR "
            + " SET valFrom = "
            + (strTimestampMode.equals("TIMESTAMP")
               ? "current timestamp"
               : "'" + strTimeStampBeginOfDay + "'")
            + " WHERE ENTITYTYPE = ? AND ENTITYID = ?";
      case (ODS_UPDATE_MD_PQ):
        return "UPDATE "
            + strSchemaODS
            + ".METAFLAGVALUE "
            + " SET valFrom = "
            + (strTimestampMode.equals("TIMESTAMP")
               ? "current timestamp"
               : "'" + strTimeStampBeginOfDay + "'")
            + ",  LONGDESCRIPTION  = ?"
            + " WHERE ATTRIBUTECODE = ? AND ATTRIBUTEVALUE = ? AND NLSID  = ? ";
      case (ODS_INSERT_BLOB_PQ):
        return "INSERT INTO "
            + strSchemaODS
            + ".BLOB "
            + "(ValFrom, ENTITYTYPE, ENTITYID, NLSID,ATTRIBUTECODE, BLOBEXTENSION,ATTRIBUTEVALUE) VALUES "
            + "("
            + (strTimestampMode.equals("TIMESTAMP")
               ? "current timestamp"
               : "'" + strTimeStampBeginOfDay + "'")
            + ",?,?,?,?,?,?)";
      case (ODS_INSERT_FLAG_PQ):
        return "INSERT INTO "
            + strSchemaODS
            + ".FLAG "
            +
            "(ValFrom,  ENTITYTYPE, ENTITYID, ATTRIBUTECODE, ATTRIBUTEVALUE) VALUES "
            + "("
            + (strTimestampMode.equals("TIMESTAMP")
               ? "current timestamp"
               : "'" + strTimeStampBeginOfDay + "'")
            + ",?,?,?,?)";
      case (ODS_INSERT_LONGTEXT_PQ):
        return "INSERT INTO "
            + strSchemaODS
            + ".LONGTEXT "
            + "(ValFrom, ENTITYTYPE, ENTITYID,NLSID, ATTRIBUTECODE,ATTRIBUTEVALUE,ATTRIBUTETYPE) VALUES "
            + "("
            + (strTimestampMode.equals("TIMESTAMP")
               ? "current timestamp"
               : "'" + strTimeStampBeginOfDay + "'")
            + ",?,?,?,?,?,?)";
      case (ODS_INSERT_RELATOR_PQ):
        return "INSERT INTO "
            + strSchemaODS
            + ".RELATOR"
            + "(ValFrom,  ENTITYTYPE, ENTITYID, ENTITY1TYPE, ENTITY1ID, ENTITY2TYPE, ENTITY2ID) VALUES "
            + "("
            + (strTimestampMode.equals("TIMESTAMP")
               ? "current timestamp"
               : "'" + strTimeStampBeginOfDay + "'")
            + ",?,?,?,?,?,?)";
      case (ODS_INSERT_MD_PQ):

        return "INSERT INTO "
            + strSchemaODS
            + ".METAFLAGVALUE"
            +
            "(ValFrom,  ATTRIBUTECODE,ATTRIBUTEVALUE, NLSID,LONGDESCRIPTION) VALUES "
            + "("
            + (strTimestampMode.equals("TIMESTAMP")
               ? "current timestamp"
               : "'" + strTimeStampBeginOfDay + "'")
            + ",?,?,?,?)";
      case (ODS_DELETE_BLOB_PQ):
        return "DELETE FROM "
            + strSchemaODS
            + ".BLOB "
            +
            " WHERE ENTITYTYPE = ? AND ENTITYID = ? AND NLSID = ? AND ATTRIBUTECODE = ? ";
      case (ODS_DELETE_FLAG_PQ):
        return "DELETE FROM "
            + strSchemaODS
            + ".FLAG "
            + " WHERE ENTITYTYPE = ? AND ENTITYID = ? AND ATTRIBUTECODE = ?  AND ATTRIBUTEVALUE = ?";
      case (ODS_DELETE_LONGTEXT_PQ):
        return "DELETE FROM "
            + strSchemaODS
            + ".LONGTEXT "
            +
            " WHERE ENTITYTYPE = ? AND ENTITYID = ? AND NLSID= ? AND ATTRIBUTECODE = ? ";
      case (ODS_DELETE_RELATOR_PQ):
        return "DELETE FROM "
            + strSchemaODS
            + ".RELATOR "
            + " WHERE ENTITYTYPE = ? AND ENTITYID = ?";
      case (ODS_DELETE_MD_PQ):

        return "DELETE FROM "
            + strSchemaODS
            + ".METAFLAGVALUE "
            + " WHERE ATTRIBUTECODE = ? AND ATTRIBUTEVALUE = ? AND NLSID = ?";
      default:
        return "";
    }
  }

  /**
   *  Description of the Method
   *
   *@param  strPrintln  Description of the Parameter
   */
  private void printOK(String strPrintln) {
    String strLogDateTime = c_sdfTimestamp.format(new Date());
    System.out.println(strLogDateTime + " " + strPrintln);
  }

  /**
   *  Description of the Method
   *
   *@param  strPrintln  Description of the Parameter
   */
  private void printErr(String strPrintln) {
    String strLogDateTime = c_sdfTimestamp.format(new Date());
    System.err.println("Eannounce: " + getVersion());
    System.err.println(strLogDateTime + " " + strPrintln);
  }

  /*
   *  checkIfRowExistsInODS
   *  ---------------------
   *  Given the integer constant which determines the table to search, will search the table
   *  for a row matching the parameters passed.
   */
  /**
   *  Description of the Method
   *
   *@param  tableFlagConst    Description of the Parameter
   *@param  strEtype          Description of the Parameter
   *@param  intEid            Description of the Parameter
   *@param  intNlsId          Description of the Parameter
   *@param  strattributecode  Description of the Parameter
   *@param  strAttributeVal   Description of the Parameter
   *@return                   Description of the Return Value
   */
  private boolean checkIfRowExistsInODS(
      int tableFlagConst,
      Connection _conODS,
      String strEtype,
      int intEid,
      int intNlsId,
      String strattributecode,
      //used only for flag queries
      String strAttributeVal // and metaflagvalue queries
      ) {
    boolean foundIt = false;
    String strFindQuery = "";
    PreparedStatement psFindODSRow = null;
    ResultSet rsFindODSRow = null;

    try {

      switch (tableFlagConst) {
        case (ISFLAG):

          strFindQuery = getQueryStatement(ODS_CHECK_FLAG_EXIST_PQ);
          psFindODSRow = _conODS.prepareStatement(strFindQuery);

          psFindODSRow.setString(1, strEtype);
          psFindODSRow.setInt(2, intEid);
          psFindODSRow.setString(3, strattributecode);
          psFindODSRow.setString(4, strAttributeVal);
          break;
        case (ISBLOB):
          strFindQuery = getQueryStatement(ODS_CHECK_BLOB_EXIST_PQ);
          psFindODSRow = _conODS.prepareStatement(strFindQuery);

          psFindODSRow.setString(1, strEtype);
          psFindODSRow.setInt(2, intEid);
          psFindODSRow.setInt(3, intNlsId);
          psFindODSRow.setString(4, strattributecode);
          break;
        case (ISLONGTEXT):
          strFindQuery = getQueryStatement(ODS_CHECK_LONGTEXT_EXIST_PQ);
          psFindODSRow = _conODS.prepareStatement(strFindQuery);

          psFindODSRow.setString(1, strEtype);
          psFindODSRow.setInt(2, intEid);
          psFindODSRow.setInt(3, intNlsId);
          psFindODSRow.setString(4, strattributecode);
          break;
        case (ISRELATOR):
          strFindQuery = getQueryStatement(ODS_CHECK_RELATOR_EXIST_PQ);
          psFindODSRow = _conODS.prepareStatement(strFindQuery);

          psFindODSRow.setString(1, strEtype);
          psFindODSRow.setInt(2, intEid);
          break;
        case (ISMDTABLE):
          strFindQuery = getQueryStatement(ODS_CHECK_MD_EXIST_PQ);

          psFindODSRow = _conODS.prepareStatement(strFindQuery);

          psFindODSRow.setString(1, strattributecode);
          psFindODSRow.setInt(2, intNlsId);
          psFindODSRow.setString(3, strAttributeVal);
          break;
        default:
          printErr("ERROR! checkIfValueExistsInODS: ILLEGAL PARM VALUE");
          System.exit( -1);

      }

      rsFindODSRow = psFindODSRow.executeQuery();
      foundIt = rsFindODSRow.next();

    }
    catch (SQLException ex) {
      printErr("ERROR! checkIfValueExistsInODS: TableConst:" + tableFlagConst);
      printErr(ex.getMessage());
      ex.printStackTrace();
      System.exit( -1);
    }
    finally {
      try {
        if (rsFindODSRow != null) {
          rsFindODSRow.close();
        }
        if (psFindODSRow != null) {
          psFindODSRow.close();
        }
      }
      catch (SQLException ex1) {
        ex1.printStackTrace();
        printErr(
            "ERROR! checkIfValueExistsInODS: TableConst:" + tableFlagConst);
        printErr(ex1.getMessage());
        ex1.printStackTrace();
        System.exit( -1);
      }
    }

    return foundIt;
  }

  /*
   *  insertIntoODS
   *  -------------
   *  This method will insert new records into the ods non entity tables
   */
  /**
   *  Description of the Method
   *
   *@param  intTableFlag         Description of the Parameter
   *@param  strInsertQuery       Description of the Parameter
   *@param  strEtype             Description of the Parameter
   *@param  strE1Type            Description of the Parameter
   *@param  strE2Type            Description of the Parameter
   *@param  intEid               Description of the Parameter
   *@param  intE1Id              Description of the Parameter
   *@param  intE2Id              Description of the Parameter
   *@param  iNLSID               Description of the Parameter
   *@param  byteBlobValue        Description of the Parameter
   *@param  strLongDescription   Description of the Parameter
   *@param  strShortDescription  Description of the Parameter
   *@param  strBlobExtension     Description of the Parameter
   *@param  strAttrType          Description of the Parameter
   *@param  strAttrCode          Description of the Parameter
   *@param  strAttrVal           Description of the Parameter
   */
  private void insertIntoODS(
      int intTableFlag,
      String strInsertQuery,
      Connection _conODS,
      String strEtype,
      String strE1Type,
      String strE2Type,
      int intEid,
      int intE1Id,
      int intE2Id,
      int iNLSID,
      byte[] byteBlobValue,
      String strLongDescription,
      String strShortDescription,
      String strBlobExtension,
      String strAttrType,
      String strAttrCode,
      String strAttrVal) {
    PreparedStatement psInsertODSRow = null;

    try {
      psInsertODSRow = _conODS.prepareStatement(strInsertQuery);

      switch (intTableFlag) {
        case (ISFLAG):

          printOK(
              "insertIntoODS:INSERT: FLAG:ETYPE:"
              + strEtype.trim()
              + ":EID:"
              + intEid
              + ":ATTRIBUTECODE:"
              + strAttrCode.trim()
              + ":ATTRIBUTEVALUE:"
              + strAttrVal.trim());

          psInsertODSRow.setString(1, strEtype);
          psInsertODSRow.setInt(2, intEid);
          psInsertODSRow.setString(3, strAttrCode);
          psInsertODSRow.setString(4, strAttrVal);
          break;
        case (ISBLOB):
        	  printOK(
                      "insertIntoODS:INSERT: BLOB:ETYPE:"
                      + strEtype
                      + ":EID:"
                      + intEid
                      + ":NLSID"
                      + iNLSID
                      + ":ATTRIBUTETYPE:"
                      + strAttrType
                      + ":ATTRIBUTECODE:"
                      + strAttrCode
                      + ":BLOBEXTENSION:"
                      + strBlobExtension);
          printOK(
              "insertIntoODS:INSERT: BLOB:ETYPE:"
              + strEtype.trim()
              + ":EID:"
              + intEid
              + ":NLSID"
              + iNLSID
              + ":ATTRIBUTETYPE:"
              + strAttrType.trim()
              + ":ATTRIBUTECODE:"
              + strAttrCode.trim()
              + ":BLOBEXTENSION:"
              + strBlobExtension.trim());

          psInsertODSRow.setString(1, strEtype);
          psInsertODSRow.setInt(2, intEid);
          psInsertODSRow.setInt(3, iNLSID);
          psInsertODSRow.setString(4, strAttrCode);
          psInsertODSRow.setString(5, strBlobExtension);
          psInsertODSRow.setBytes(6, byteBlobValue);

          break;
        case (ISLONGTEXT):

          printOK(
              "insertIntoODS:INSERT: LONGTEXT:ETYPE:"
              + strEtype
              + ":EID:"
              + intEid
              + ":NLSID"
              + iNLSID
              + ":ATTRIBUTETYPE:"
              + strAttrType
              + ":ATTRIBUTECODE:"
              + strAttrCode
              + ":ATTRIBUTEVALUE:"
              + strAttrVal.trim());
          psInsertODSRow.setString(1, strEtype);
          psInsertODSRow.setInt(2, intEid);
          psInsertODSRow.setInt(3, iNLSID);
          psInsertODSRow.setString(4, strAttrCode);
          psInsertODSRow.setString(5, strAttrVal.trim());
          psInsertODSRow.setString(6, strAttrType);
          break;
        case (ISRELATOR):

          printOK(
              "insertIntoODS:INSERT: RELATOR:ETYPE:"
              + strEtype.trim()
              + ":EID:"
              + intEid
              + ":E1TYPE:"
              + strE1Type
              + ":E1ID:"
              + intE1Id
              + ":E2TYPE:"
              + strE2Type
              + ":E2ID:"
              + intE2Id);

          psInsertODSRow.setString(1, strEtype);
          psInsertODSRow.setInt(2, intEid);
          psInsertODSRow.setString(3, strE1Type);
          psInsertODSRow.setInt(4, intE1Id);
          psInsertODSRow.setString(5, strE2Type);
          psInsertODSRow.setInt(6, intE2Id);
          break;
        case (ISMDTABLE):
          printOK(
              "insertIntoODS:INSERT: METAFLAGVALUE:"
              + ":NLSID"
              + iNLSID
              + ":LONGDESCRIPTION:"
              + strLongDescription.trim()
              + ":SHORTDESCRIPTION:"
              + strShortDescription
              + ":DESCRIPTIONTYPE:"
              + strAttrCode.trim()
              + ":DESCRIPTIONCLASS:"
              + strAttrVal.trim());

          psInsertODSRow.setString(1, strAttrCode);
          psInsertODSRow.setString(2, strAttrVal);
          psInsertODSRow.setInt(3, iNLSID);
          psInsertODSRow.setString(4, strLongDescription.trim());
          break;
        default:
          printErr("ERROR! insertIntoODS: ILLEGAL PARM VALUE");
          System.exit( -1);

      }
      //Update only if live
      if (blnLive) {
        psInsertODSRow.executeUpdate();
      }

    }
    catch (SQLException ex) {
      printErr("ERROR! insertIntoODS: TableConst:" + intTableFlag);
      printErr(ex.getMessage());
      System.exit( -1);
    }
    finally {
      try {
        if (psInsertODSRow != null) {
          psInsertODSRow.close();
          psInsertODSRow = null;
        }
      }
      catch (SQLException ex1) {
        ex1.printStackTrace();
        printErr("ERROR! insertIntoODS: TableConst:" + intTableFlag);
        printErr(ex1.getMessage());
        System.exit( -1);
      }
    }

  }

  /*
   *  updateODS
   *  ---------
   *  This method will update ods non entity tables
   */
  /**
   *  Description of the Method
   *
   *@param  intTableFlag         Description of the Parameter
   *@param  strUpdateQuery       Description of the Parameter
   *@param  strEtype             Description of the Parameter
   *@param  intEid               Description of the Parameter
   *@param  iNLSID               Description of the Parameter
   *@param  byteBlobValue        Description of the Parameter
   *@param  strLongDescription   Description of the Parameter
   *@param  strShortDescription  Description of the Parameter
   *@param  strBlobExtension     Description of the Parameter
   *@param  strAttrType          Description of the Parameter
   *@param  strAttrCode          Description of the Parameter
   *@param  strAttrVal           Description of the Parameter
   */
  private void updateODS(
      int intTableFlag,
      String strUpdateQuery,
      Connection _conODS,
      String strEtype,
      int intEid,
      int iNLSID,
      byte[] byteBlobValue,
      String strLongDescription,
      String strShortDescription,
      String strBlobExtension,
      String strAttrType,
      String strAttrCode,
      String strAttrVal) {

    PreparedStatement psUpdateODSRow = null;
    try {
      psUpdateODSRow = _conODS.prepareStatement(strUpdateQuery);

      switch (intTableFlag) {
        case (ISFLAG):
          printOK(
              "updateODS:UPDATING FLAG:ETYPE:"
              + strEtype
              + ":EID:"
              + intEid
              + ":ATTRIBUTECODE:"
              + strAttrCode
              + ":ATTRIBUTEVALUE:"
              + strAttrVal);

          psUpdateODSRow.setString(1, strEtype);
          psUpdateODSRow.setInt(2, intEid);
          psUpdateODSRow.setString(3, strAttrCode);
          psUpdateODSRow.setString(4, strAttrVal);
          break;
        case (ISBLOB):
          printOK(
              "updateODS:UPDATING BLOB:ETYPE:"
              + strEtype
              + ":EID:"
              + intEid
              + ":NLSID"
              + iNLSID
              + ":ATTRIBUTETYPE:"
              + strAttrType
              + ":ATTRIBUTECODE:"
              + strAttrCode);

          psUpdateODSRow.setString(1, strBlobExtension);
          psUpdateODSRow.setBytes(2, byteBlobValue);
          psUpdateODSRow.setString(3, strEtype);
          psUpdateODSRow.setInt(4, intEid);
          psUpdateODSRow.setInt(5, iNLSID);
          psUpdateODSRow.setString(6, strAttrCode);
          break;
        case (ISLONGTEXT):

          printOK(
              "updateODS:UPDATING LONGTEXT:ETYPE:"
              + strEtype
              + ":EID:"
              + intEid
              + ":NLSID"
              + iNLSID
              + ":ATTRIBUTETYPE:"
              + strAttrType
              + ":ATTRIBUTECODE:"
              + strAttrCode
              + ":ATTRIBUTEVALUE:"
              + strAttrVal);
          psUpdateODSRow.setString(1, strAttrVal);
          psUpdateODSRow.setString(2, strEtype);
          psUpdateODSRow.setInt(3, intEid);
          psUpdateODSRow.setInt(4, iNLSID);
          psUpdateODSRow.setString(5, strAttrCode);
          break;
        case (ISRELATOR):

          printOK(
              "updateODS:UPDATING RELATOR:ETYPE:" + strEtype + ":EID:" + intEid);
          psUpdateODSRow.setString(1, strEtype);
          psUpdateODSRow.setInt(2, intEid);

          break;
        case (ISMDTABLE):
          printOK(
              "updateODS:UPDATING METADESCRIPTION:"
              + ":NLSID"
              + iNLSID
              + ":SHORTDESCRIPTION:"
              + strShortDescription.trim()
              + ":LONGDESCRIPTION:"
              + strLongDescription.trim()
              + ":DESCRIPTIONTYPE:"
              + strAttrCode
              + ":DESCRIPTIONCLASS:"
              + strAttrVal);

          psUpdateODSRow.setString(1, strLongDescription.trim());
          psUpdateODSRow.setString(2, strAttrCode);
          psUpdateODSRow.setString(3, strAttrVal);
          psUpdateODSRow.setInt(4, iNLSID);
          break;
        default:
          printErr("ERROR! updateODS: ILLEGAL PARM VALUE");
          System.exit( -1);

      }

      //Update only if live
      if (blnLive) {
        psUpdateODSRow.executeUpdate();
      }

    }
    catch (SQLException ex) {
      printErr("ERROR! updateODS: TableConst:" + intTableFlag);
      printErr(ex.getMessage());
      System.exit( -1);
    }
    finally {
      try {
        if (psUpdateODSRow != null) {
          psUpdateODSRow.close();
          psUpdateODSRow = null;
        }
      }
      catch (SQLException ex1) {
        printErr("ERROR! updateODS: TableConst:" + intTableFlag);
        printErr(ex1.getMessage());
        ex1.printStackTrace();
        System.exit( -1);
      }
    }

  }

  /*
   *  retireODSrow
   *  ---------
   *  This method will retire ods non entity tables
   */
  /**
   *  Description of the Method
   *
   *@param  intTableFlag    Description of the Parameter
   *@param  strDeleteQuery  Description of the Parameter
   *@param  strEtype        Description of the Parameter
   *@param  intEid          Description of the Parameter
   *@param  iNLSID          Description of the Parameter
   *@param  strAttrCode     Description of the Parameter
   *@param  strAttrVal      Description of the Parameter
   */
  private void retireODSrow(
      int intTableFlag,
      String strDeleteQuery,
      Connection _conODS,
      String strEtype,
      int intEid,
      int iNLSID,
      String strAttrCode,
      String strAttrVal) {
    PreparedStatement psDeleteODSRow = null;

    try {
      psDeleteODSRow = _conODS.prepareStatement(strDeleteQuery);

      switch (intTableFlag) {
        case (ISFLAG):
          printOK(
              "retireODSrow:DELETING FLAG:ETYPE:"
              + strEtype
              + ":EID:"
              + intEid
              + ":ATTRIBUTECODE:"
              + strAttrCode
              + ":ATTRIBUTEVALUE:"
              + strAttrVal);
          psDeleteODSRow.setString(1, strEtype);
          psDeleteODSRow.setInt(2, intEid);
          psDeleteODSRow.setString(3, strAttrCode);
          psDeleteODSRow.setString(4, strAttrVal);
          break;
        case (ISBLOB):
          printOK(
              "retireODSrow:DELETING BLOB:ETYPE:"
              + strEtype
              + ":EID:"
              + intEid
              + ":NLSID:"
              + iNLSID
              + ":ATTRIBUTECODE:"
              + strAttrCode);
          psDeleteODSRow.setString(1, strEtype);
          psDeleteODSRow.setInt(2, intEid);
          psDeleteODSRow.setInt(3, iNLSID);
          psDeleteODSRow.setString(4, strAttrCode);
          break;
        case (ISLONGTEXT):

          printOK(
              "retireODSrow:DELETING LONGTEXT:ETYPE:"
              + strEtype
              + ":EID:"
              + intEid
              + ":ATTRIBUTECODE:"
              + strAttrCode);
          psDeleteODSRow.setString(1, strEtype);
          psDeleteODSRow.setInt(2, intEid);
          psDeleteODSRow.setInt(3, iNLSID);
          psDeleteODSRow.setString(4, strAttrCode);
          break;
        case (ISRELATOR):
          printOK(
              "retireODSrow:DELETING RELATOR:ETYPE:"
              + strEtype
              + ":EID:"
              + intEid);

          psDeleteODSRow.setString(1, strEtype);
          psDeleteODSRow.setInt(2, intEid);
          break;
        case (ISMDTABLE):
          printOK(
              "retireODSrow:DELETING METAFLAGVALUE:"
              + ":ATTRIBUTECODE:"
              + strAttrCode
              + ":ATTRIBUTEVALUE:"
              + strAttrVal
              + ":NLSID:"
              + iNLSID);

          psDeleteODSRow.setString(1, strAttrCode);
          psDeleteODSRow.setString(2, strAttrVal);
          psDeleteODSRow.setInt(3, iNLSID);
          break;
        default:
          printErr("ERROR! retireODSrow: ILLEGAL PARM VALUE");
          System.exit( -1);

      }

      if (blnLive) {
        psDeleteODSRow.executeUpdate();
      }

    }
    catch (SQLException ex) {
      printErr("ERROR! retireODSrow: TableConst:" + intTableFlag);
      printErr(ex.getMessage());
      System.exit( -1);
    }
    finally {
      try {
        if (psDeleteODSRow != null) {
          psDeleteODSRow.close();
          psDeleteODSRow = null;
        }
      }
      catch (SQLException ex1) {
        printErr("ERROR! retireODSrow: TableConst:" + intTableFlag);
        printErr(ex1.getMessage());
        ex1.printStackTrace();
        System.exit( -1);
      }
    }

  }

  //use only for debugging
  /**
   *  Description of the Method
   *
   *@param  displayString  Description of the Parameter
   *@param  v              Description of the Parameter
   */
  public static void displayVectorContents(String displayString, Vector v) {
    Vector tempvector = null;
    Enumeration ee = null;
    tempvector = v;
    ee = tempvector.elements();
    System.err.println(displayString);
    while (ee.hasMoreElements()) {
      System.err.println("    |" + ee.nextElement() + "|");
    }
  }

  private byte[] getBlobValue(
      String _strEtype,
      int _iEID,
      int _iNLSID,
      String _strAttrCode,
      String _strValTo,
      String _strEffTo,
      String _strValfrom,
      String _strEffFrom) {
    byte[] byteBlob = null;
    int iblobsize = 0;
    String strBlobSQL =
        "SELECT "
        + "B.ATTRIBUTEVALUE "
        + "FROM "
        + strSchemaPDH
        + ".BLOBx B "
        + "WHERE "
        + "B.ENTERPRISE = ? AND "
        + "B.EntityType = ? AND B.EntityID = ? AND B.ATTRIBUTECODE = ? AND B.NLSID = ? AND "
        + "B.ValFrom = ? AND  B.ValTo = ? AND "
        + "B.EffFrom = ? AND  B.EffTo = ?";
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = conPDH.prepareStatement(strBlobSQL);

      printOK("getBlobValue:" + blnLive + " Starting Query");
      ps.setString(1, strEnterprise);
      ps.setString(2, _strEtype);
      ps.setInt(3, _iEID);
      ps.setString(4, _strAttrCode);
      ps.setInt(5, _iNLSID);
      ps.setString(6, _strValfrom);
      ps.setString(7, _strValTo);
      ps.setString(8, _strEffFrom);
      ps.setString(9, _strEffTo);
      rs = ps.executeQuery();
      if (rs.next()) {

        byteBlob = rs.getBytes(1);
        iblobsize = byteBlob.length;

        printOK("getBlobValue:Retrieved Blob of size:  " + iblobsize);
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
      printOK("getBlobValue:problem during blob retrieval" + e.getMessage());
      System.exit( -1);
    }
    finally {
      try {
        if (rs != null) {
          rs.close();
          rs = null;

        }
        if (ps != null) {
          ps.close();
          ps = null;

        }

      }
      catch (SQLException ex1) {
        ex1.printStackTrace();
        printOK(
            "getBlobValue:problem during blob retrieval" + ex1.getMessage());
        System.exit( -1);

      }
    }
    return byteBlob;
  }

  /**
   *  readPropertyFile reads the property file reverseods.properties
   */
  private void readPropertyFile() {
    Properties properties = new Properties();
    printOK("Reading Datamover.properties");
    try {
      properties.load(new FileInputStream("datamover.properties"));
    }
    catch (IOException e) {
      printOK("Error reading datamover.properties" + e.getMessage());
      System.exit( -1);
    }

    strurlODS = properties.getProperty("ODSURL");
    struidODS = properties.getProperty("ODSID");
    strpwdODS = properties.getProperty("ODSPWD");
    strurlPDH = properties.getProperty("PDHURL");
    struidPDH = properties.getProperty("PDHID");
    strNLSOds = properties.getProperty("ODSNLS", "ALL");

    strpwdPDH = properties.getProperty("PDHPWD");
    strSchemaPDH = properties.getProperty("PDHSCHEMA");
    strSchemaODS = properties.getProperty("ODSSCHEMA", "OPICM").toUpperCase();
    strEnterprise = properties.getProperty("ENTERPRISE");
    strRunMode = properties.getProperty("RUNMODE");
    strTimestampMode = properties.getProperty("TIMESTAMPMODE");
    iMaxRowUpdateCount =
        Integer.parseInt(properties.getProperty("MAXROWUPDATECOUNT", "10000"));
    strBlobAttExcludeList += properties.getProperty("EXCLUDE_BLOB_ATTRIBUTES","");

    printOK("Read properties.");

  } //readPropertyFile

  /**
   *  Return the date/time this class was generated
   *
   *@return    the date/time this class was generated
   */
  public final String getVersion() {
    return "$Id: OdsNetChangeShell.java,v 1.113 2015/06/10 12:38:53 jilichao Exp $";
  }

  void setFmtTimeStamp(SimpleDateFormat _fmtTimeStamp) {
    this.fmtTimeStamp = _fmtTimeStamp;
  }

  SimpleDateFormat getFmtTimeStamp() {
    return fmtTimeStamp;
  }

  void setHODSTableDef(Hashtable _hODSTableDef) {
    this.hODSTableDef = _hODSTableDef;
  }

  Hashtable getHODSTableDef() {
    return hODSTableDef;
  }

  String getCurrentPDHTime() {
    String strAnswer = "ERROR";
    Statement stmt = null;
    ResultSet rs = null;
    try {
      stmt = conPDH.createStatement();
      rs = stmt.executeQuery(getQueryStatement(PDH_GET_PDH_DB_TIMESTAMP));
      if (rs.next()) {
        strAnswer = rs.getString(1).trim();
      }

    }
    catch (SQLException ex) {
      printErr("getCurrentPDHTime *Error*:\t" + ex.getMessage());
      System.exit( -1);
    }
    finally {
      try {
        if (rs != null) {
          rs.close();
          rs = null;
        }
        if (stmt != null) {
          stmt.close();
          stmt = null;
        }

      }
      catch (SQLException ex1) {
        ex1.printStackTrace();
        printErr("getCurrentPDHTime *Error*:" + ex1.getMessage());
        System.exit( -1);
      }

    }

    return strAnswer;

  }
}