// Licensed Materials -- Property of IBM
//
// (c) Copyright International Business Machines Corporation, 2001
// All Rights Reserved.
//
// $Log: SynchronizeClassifications.java,v $
// Revision 1.18  2004/09/28 20:42:34  roger
// Cast interferes
//
// Revision 1.17  2004/09/28 20:23:29  roger
// NULLs are bad - don't output them
//
// Revision 1.16  2003/05/29 18:53:35  dave
// clean up and removed the null from the table def
//
// Revision 1.15  2003/01/14 21:02:42  dave
// adding str_valOn to dump
//
// Revision 1.14  2003/01/14 20:04:49  dave
// changing date package creation
//
// Revision 1.13  2003/01/14 18:53:55  dave
// changing the dbPDH, c_dbPDH
//
// Revision 1.12  2003/01/14 18:09:54  dave
// database name typo
//
// Revision 1.11  2003/01/14 17:30:50  dave
// Added EndOfDay to DatePackage and used it somewhere
//
// Revision 1.10  2003/01/13 23:42:36  dave
// dumping profile info to log file
//
// Revision 1.9  2003/01/13 20:01:37  dave
// move schema setting to ods.middleware.server.properties
//
// Revision 1.8  2003/01/08 01:43:13  dave
// removed some commits
//
// Revision 1.7  2003/01/08 00:36:16  dave
// more Classification Stuff
//
// Revision 1.6  2003/01/08 00:13:24  dave
// syntax
//
// Revision 1.5  2003/01/08 00:05:06  dave
// fixing missing ;
//
// Revision 1.4  2003/01/07 23:57:09  dave
// adding multiple property files for EntityType, Attribute
// Code levels C1, C4
//
// Revision 1.3  2003/01/07 22:48:03  dave
// syntax for ODSServerProperties
//
// Revision 1.2  2003/01/07 22:34:42  dave
// more ODS server stuff
//
// Revision 1.1  2003/01/07 21:12:01  dave
// first time add of the module
//
//


package COM.ibm.eannounce.ods;

import java.util.*;
import java.io.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.objects.*;

/**
* This is a simple Thead manager that creates a ReportStub Thread for each ABR that it comes across
*/
public class SynchronizeClassifications {

  // Class initialization code
  static {
    D.isplay("SynchornizeClassifications:" + getVersion());
  }

  // Class variables
  private static Database c_dbPDH = new Database();
  private static Connection c_conODS = null;
  private static String c_strNow = null;
  private static String c_strForever = null;
  private static String c_strEpoch = null;
   private static String c_strurlODS = ODSServerProperties.getDatabaseURL();
   private static String c_struidODS = ODSServerProperties.getDatabaseUser();
   private static String c_strpwdODS = ODSServerProperties.getDatabasePassword();

   //**********************************
   // ODS Query Literals
   //**********************************
   private static String c_strODSSchema = ODSServerProperties.getDatabaseSchema();

   private static String ODS_DROP_CLASSIFICATIONS_TABLE =
   " DROP TABLE " + c_strODSSchema + ".CLASSIFICATIONS";

  private static String ODS_CREATE_CLASSIFICATIONS_TABLE =
  " CREATE TABLE " + c_strODSSchema + ".CLASSIFICATIONS"  +
  " (" +
  "ENTITYTYPE VARCHAR(32) NOT NULL, " +
  "C1 VARCHAR(32)," +
  "C2 VARCHAR(32)," +
  "C3 VARCHAR(32)," +
  "C4 VARCHAR(32)," +
  "ATTRIBUTECODE VARCHAR(32) NOT NULL," +
   "ATTRIBUTETYPE VARCHAR(1) NOT NULL" +
   ")";

   private static String ODS_CREATE_CLASSIFICATIONS_INDEX =
  " CREATE INDEX " + c_strODSSchema + ".CLASSIFICATIONS_PK ON "  + c_strODSSchema + ".CLASSIFICATIONS " +
  " (" +
  "ENTITYTYPE, C1, C2, C3, C4" +
   ")";

   private static String ODS_INSERT_CLASSIFICATIONS_ROW =
   " INSERT INTO " + c_strODSSchema + ".CLASSIFICATIONS " +
  " ( " +
  "ENTITYTYPE, " +
  "C1, " +
  "C2, " +
  "C3, " +
  "C4, "+
   "ATTRIBUTECODE," +
   "ATTRIBUTETYPE " +
   "  ) " +
  "VALUES (?,?,?,?,?,?,?)";

   //Instance variables
  public static void main(String[] arg) {

    D.ebug(MiddlewareServerProperties.getTrace());
    D.ebug(D.EBUG_INFO, "Tester... tracing enabled");
    D.ebug(D.EBUG_INFO, "Tester... debug trace level " + MiddlewareServerProperties.getDebugTraceLevel());
    D.ebugLevel(MiddlewareServerProperties.getDebugTraceLevel());

    // Here is the control statement based upon what the user passed as a command

    try {
      Class.forName("COM.ibm.db2.jdbc.app.DB2Driver").newInstance();
      c_conODS = DriverManager.getConnection(c_strurlODS,c_struidODS,c_strpwdODS);
      c_conODS.setAutoCommit(false);

      rebuildODSClassificationTable();

    }catch (Exception ex) {
      D.ebug(D.EBUG_ERR,"odsConnectionError" + ex.getMessage());
      System.exit(-1);
    }

    try {

         Profile prof = new Profile(c_dbPDH, ODSServerProperties.getProfileEnterprise(), ODSServerProperties.getProfileOPWGID());

         DatePackage dpNow = new DatePackage(c_dbPDH);
         prof.setValOn(dpNow.getEndOfDay());
         prof.setEffOn(dpNow.getEndOfDay());

         D.ebug(D.EBUG_INFO,"===========================");
         D.ebug(D.EBUG_INFO,"Profile:" + prof.dump(false));
         D.ebug(D.EBUG_INFO,"===========================");

         // Get Date package
         String strEntityType = "NOTHING";
         String strAC1 = "NOTHING";
         String strAC2 = "NOTHING";
         String strAC3 = "NOTHING";
         String strAC4 = "NOTHING";

         // Lets loop through and process what is called out in the properties file

         StringTokenizer stMain = new StringTokenizer(ODSServerProperties.getClassifyEntityList());
         while (stMain.hasMoreTokens()) {

            strEntityType = stMain.nextToken().trim();
            strAC1 = ODSServerProperties.getClassifyC1(strEntityType);
            strAC2 = ODSServerProperties.getClassifyC2(strEntityType);
            strAC3 = ODSServerProperties.getClassifyC3(strEntityType);
            strAC4 = ODSServerProperties.getClassifyC4(strEntityType);

            EntityGroup eg = new EntityGroup(null,c_dbPDH,prof,strEntityType,"Edit");
            ClassificationGroup cgGlobal = eg.getGlobalClassificationGroup();

            // O.K.  Lets loop though the classification guys here and work on adding
            for (int ii = 0;ii < eg.getClassificationGroupCount();ii++) {
               ClassificationGroup cg = eg.getClassificationGroup(ii);
               System.out.println("========= " + cg.getKey() + "========");
               if (!cg.isGlobalClassify()) {
                  String strEval = cg.getEvaluationString();
                  StringTokenizer st = new StringTokenizer(strEval,"&&");
                  Hashtable hsh1 = new Hashtable();
                  while (st.hasMoreTokens()) {
                     String strFragment = st.nextToken().trim();
                     StringTokenizer st2 = new StringTokenizer(strFragment,"==");
                     while (st2.hasMoreTokens()) {
                        String strAttributeCode = st2.nextToken().trim().substring(1);
                        String strFlagCode = st2.nextToken().trim();
                        String strFlagDescription = "NOMATCH";
                        // O.K.  now lets fish this one out of the structure
                        EANMetaFlagAttribute mfa = (EANMetaFlagAttribute)eg.getMetaAttribute(strAttributeCode);
                        if (mfa == null) {
                           System.out.println("Error cannot find strAttributeCode:" + strAttributeCode);
                        } else {
                           MetaFlag mf = mfa.getMetaFlag(strFlagCode);
                           strFlagDescription  = (mf == null ? "NOMATCH" : mf.getLongDescription());
                        }
                        hsh1.put(strAttributeCode,strFlagDescription);
                     }
                  }

                  // OK.. lets print them
                  String strC1 = (hsh1.get(strAC1) == null ? "" : (String)hsh1.get(strAC1));
                  String strC2 = (hsh1.get(strAC2) == null ? "" : (String)hsh1.get(strAC2));
                  String strC3 = (hsh1.get(strAC3) == null ? "" : (String)hsh1.get(strAC3));
                  String strC4 = (hsh1.get(strAC4) == null ? "" : (String)hsh1.get(strAC4));

                  // Now lets loop through all the attributes and build the answer we need here...

                  for (int iy = 0;iy<cgGlobal.getAttributeCodeCount();iy++) {
                     String strAttributeCode = cgGlobal.getAttributeCode(iy);
                     EANMetaAttribute ma = (EANMetaAttribute)eg.getMetaAttribute(strAttributeCode);
                     String strAttributeType = (ma == null ? "Q" : ma.getAttributeType());
                     System.out.println(strEntityType + ":" + strC1 + ":" + strC2 + ":" + strC3 + ":" + strC4 + ":" + strAttributeCode +  ":" + strAttributeType);
               PreparedStatement insertrecord = c_conODS.prepareStatement(ODS_INSERT_CLASSIFICATIONS_ROW);
                     insertrecord.setString(1,strEntityType);
                     insertrecord.setString(2,strC1);
                     insertrecord.setString(3,strC2);
                     insertrecord.setString(4,strC3);
                     insertrecord.setString(5,strC4);
                     insertrecord.setString(6,strAttributeCode);
                     insertrecord.setString(7,strAttributeType);
                     insertrecord.executeUpdate();
                     insertrecord.close();
                  }

                  for (int iy = 0;iy<cg.getAttributeCodeCount();iy++) {
                     String strAttributeCode = cg.getAttributeCode(iy);
                     EANMetaAttribute ma = (EANMetaAttribute)eg.getMetaAttribute(strAttributeCode);
                     String strAttributeType = (ma == null ? "Q" : ma.getAttributeType());
                     System.out.println(strEntityType + ":" + strC1 + ":" + strC2 + ":" + strC3 + ":" + strC4 + ":" + strAttributeCode +  ":" + strAttributeType);
               PreparedStatement insertrecord = c_conODS.prepareStatement(ODS_INSERT_CLASSIFICATIONS_ROW);
                     insertrecord.setString(1,strEntityType);
                     insertrecord.setString(2,strC1);
                     insertrecord.setString(3,strC2);
                     insertrecord.setString(4,strC3);
                     insertrecord.setString(5,strC4);
                     insertrecord.setString(6,strAttributeCode);
                     insertrecord.setString(7,strAttributeType);
                     insertrecord.executeUpdate();
                     insertrecord.close();
                  }

                  // Now commit the records
                 c_conODS.commit();

               }
            }
         }
    } catch (Exception x) {

      D.ebug(D.EBUG_ERR, "Test:main" + x);
      x.printStackTrace();
      //If test fails on any connection, then fail hard
      System.exit(-1);
    }


  }

  /*
  * Not much happening here
  */
  public SynchronizeClassifications()  {
      System.out.println("HI");
  }

  /**
  * Return the versoin of this class
  */
  public final static String getVersion() {
    return new String("$Id: SynchronizeClassifications.java,v 1.18 2004/09/28 20:42:34 roger Exp $");
  }



   public static void rebuildODSClassificationTable() throws SQLException {

    Statement ddlstmt = c_conODS.createStatement();

      // Let us drop the table first
    try {
         D.ebug(D.EBUG_DETAIL,"rebuildODSTables.. dropping CLASSIFICATIONS Table");
       ddlstmt.executeUpdate(ODS_DROP_CLASSIFICATIONS_TABLE);
      c_conODS.commit();
         D.ebug(D.EBUG_DETAIL,"rebuildODSTables.. drop complete");
    } catch (SQLException ex)   {
         D.ebug(D.EBUG_DETAIL,"rebuildODSTables.. Skipping Drop, CLASSIFICTATIONS Table Not Found.");
    }

      // O.K.  Creating a new table.

      ddlstmt.executeUpdate(ODS_CREATE_CLASSIFICATIONS_TABLE);
      D.ebug(D.EBUG_DETAIL,"rebuildODSTables.. creating CLASSIFICATIONS Table complete");
    ddlstmt.executeUpdate(ODS_CREATE_CLASSIFICATIONS_INDEX);
      D.ebug(D.EBUG_DETAIL,"rebuildODSTables.. creating CLASSIFICATIONS Index complete");
    c_conODS.commit();
      ddlstmt.close();
  }
}
