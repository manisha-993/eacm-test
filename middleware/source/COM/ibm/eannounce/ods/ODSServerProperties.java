//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: ODSServerProperties.java,v $
// Revision 1.54  2005/08/22 14:03:12  gregg
// sql order by fix
//
// Revision 1.53  2005/08/20 01:25:53  gregg
// trimming!!!!
//
// Revision 1.52  2005/08/18 17:39:43  gregg
// multiattribute att mappings
//
// Revision 1.51  2005/08/17 17:49:52  gregg
// Ok, first pass at unlink/link fix for multiattributes
//
// Revision 1.50  2005/08/12 22:23:36  gregg
// fix for deletes for multiattribute
//
// Revision 1.49  2005/08/02 20:12:53  gregg
// propertyize nls defs for multiattribute
//
// Revision 1.48  2005/08/01 19:24:29  gregg
// some adjustments for multiattr net logic + property-ize multiatrribute table name
//
// Revision 1.47  2005/06/21 17:10:27  gregg
// fix in isWithdrawCheckAttribute
//
// Revision 1.46  2005/06/15 22:21:28  gregg
// withdraw check
//
// Revision 1.45  2005/05/13 17:30:34  gregg
// fixes
//
// Revision 1.44  2005/05/13 17:00:44  gregg
// more prepping rollup logic for init
//
// Revision 1.43  2005/04/26 20:33:19  gregg
// fix
//
// Revision 1.42  2005/04/26 18:00:49  gregg
// performRollupAttributes method name change for clarity
//
// Revision 1.41  2005/04/26 17:59:46  gregg
// processRollupAttribute
//
// Revision 1.40  2005/04/26 17:27:13  gregg
// start w/ rollup attribtues
//
// Revision 1.39  2004/03/03 18:48:37  gregg
// attribute subset logic
//
// Revision 1.38  2004/03/02 22:10:53  gregg
// some empty string checking for props vals
//
// Revision 1.37  2004/03/02 21:56:16  gregg
// excludeEntityFromUpdate: use ',' as delimiter
//
// Revision 1.36  2004/03/02 20:49:38  gregg
// excludeEntityFromUpdate
//
// Revision 1.35  2004/02/23 21:35:21  dave
// update for fkey safety net
//
// Revision 1.34  2004/02/05 19:27:59  dave
// more minor changes
//
// Revision 1.33  2004/01/28 21:36:58  dave
// more script changes
//
// Revision 1.32  2004/01/19 03:47:01  dave
// added crosspostII configs
//
// Revision 1.31  2004/01/19 03:02:44  dave
// working in the crosspostII logic
//
// Revision 1.30  2004/01/19 02:35:19  dave
// syntax
//
// Revision 1.29  2004/01/19 02:24:22  dave
// adding new properties
//
// Revision 1.28  2003/11/13 19:24:44  dave
// added properties file to control what gets put into
// the attrgroup table
//
// Revision 1.27  2003/09/29 01:52:07  dave
// moding flag table to house descriptions in
// nls for speeds and feeds attributes
//
// Revision 1.26  2003/09/22 02:21:50  dave
// more refinement
//
// Revision 1.25  2003/09/21 23:10:46  dave
// whoops extra stuff at end
//
// Revision 1.24  2003/09/21 23:04:45  dave
// more function
//
// Revision 1.23  2003/09/21 21:58:59  dave
// need include table fix
//
// Revision 1.22  2003/09/21 21:14:28  dave
// implementing foriegn keys
//
// Revision 1.21  2003/09/21 19:56:24  dave
// more changes to properties files
//
// Revision 1.20  2003/09/21 19:18:15  dave
// starting ODSInitII
//
// Revision 1.19  2003/09/14 02:24:34  dave
// Translation fixes so no length attributes will also not be
// considered overrides
//
// Revision 1.18  2003/09/14 00:58:22  dave
// more misc changes
//
// Revision 1.17  2003/09/14 00:35:41  dave
// more refining
//
// Revision 1.16  2003/09/11 22:44:28  dave
// syntax
//
// Revision 1.15  2003/09/11 22:38:05  dave
// adding AllNLSid
//
// Revision 1.14  2003/09/11 22:27:45  dave
// adding allNLS stuff and single flag table
//
// Revision 1.13  2003/09/10 21:00:21  dave
// fixing FlagFilter
//
// Revision 1.12  2003/02/19 23:54:28  bala
// Added getDatabaseNLS method
//
// Revision 1.11  2003/01/29 22:52:48  dave
// adding more properties to the ODS properties file
//
// Revision 1.10  2003/01/23 17:07:42  dave
// adding new properties to the ODS server properties file
//
// Revision 1.9  2003/01/22 23:53:20  dave
// added more ODSServerProperties
//
// Revision 1.8  2003/01/14 00:34:22  dave
// added upper case forcing to schema property
//
// Revision 1.7  2003/01/13 23:09:28  dave
// adding chunck size to the ods properties file
//
// Revision 1.6  2003/01/13 20:01:37  dave
// move schema setting to ods.middleware.server.properties
//
// Revision 1.5  2003/01/08 00:47:48  dave
// type in server properties file for ods
//
// Revision 1.4  2003/01/08 00:36:16  dave
// more Classification Stuff
//
// Revision 1.3  2003/01/07 23:57:09  dave
// adding multiple property files for EntityType, Attribute
// Code levels C1, C4
//
// Revision 1.2  2003/01/07 22:48:03  dave
// syntax for ODSServerProperties
//
// Revision 1.1  2003/01/07 22:34:42  dave
// more ODS server stuff
//
//

package COM.ibm.eannounce.ods;

import java.io.*;
import java.util.*;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EANAttribute;
import COM.ibm.eannounce.objects.EANFlagAttribute;

/**
 * Retrieve configuration properties for <code>ODS Related Functions</code>
 * @version @date
 */
public final class ODSServerProperties extends Properties {

  // Class constants
  private static final String PROPERTIES_FILENAME = new String("ods.server.properties");

  // Class variables
  private static Properties c_propODS = null;

/**
 * Main method which performs a simple test of this class
 */
  public static void main(String arg[]) {
    System.out.println(ODSServerProperties.getVersion());
    System.out.println(ODSServerProperties.getDatabaseURL());
    System.out.println(ODSServerProperties.getDatabaseUser());
    System.out.println(ODSServerProperties.getDatabasePassword());
  }

/**
 * Some class level initialization
 */
  static {
    // Load the properties from file so they are available for each accessor method
    reloadProperties();
  }

/**
 * Don't let anyone instantiate this class.
 */
  private ODSServerProperties() {
  }

/**
 * Load the Middleware properties from the middleware.server.properties file
 */
  private static final void loadProperties() {
    try {
      if (c_propODS == null) {
        c_propODS = new Properties();
        FileInputStream inProperties = new FileInputStream("./" + PROPERTIES_FILENAME);
        c_propODS.load(inProperties);
        inProperties.close();
      }
    } catch (Exception x) {
      D.ebug("Unable to loadProperties for " + PROPERTIES_FILENAME + " " + x);
    }
  }

/**
 * Reload the properties from the middleware.server.properties file
 */
  public static final void reloadProperties() {
    loadProperties();
  }

/**
 * Return the Database User property from the ods.server.properties file
 */
  public static final String getDatabaseUser() {
    return c_propODS.getProperty("ods_database_user", "");
  }

/**
 * Return the Database password property from the ods.server.properties file
 */
  public static final String getDatabasePassword() {
    return c_propODS.getProperty("ods_database_password", "");
  }

/**
 * Return the Database URL property from from the ods.server.properties file
 */
  public static final String getDatabaseURL() {
    return c_propODS.getProperty("ods_database_url", "");
  }

/**
 * Return the Database Schema property from from the ods.server.properties file
 */
  public static final String getDatabaseSchema() {
    return c_propODS.getProperty("ods_database_schema", getProfileEnterprise()).toUpperCase();
  }

  /**
 * Return the Classify Entity List from the ods.server.properties file
 */
  public static final String getClassifyEntityList() {
    return c_propODS.getProperty("ods_classify_entities", "NOTHING");
  }

 /**
 * Return the number of openid number for the profile
 */
  public static final int getProfileOPWGID() {
    return Integer.parseInt(c_propODS.getProperty("pdh_profile_opwgid", "1").trim());
    }

 /**
 * Return the number of openid number for the profile
 */
    public static final int getVarCharColumnLength() {
    return Integer.parseInt(c_propODS.getProperty("ods_varchar_column_length", "25").trim());
  }

 /**
 * Return the number of openid number for the profile
 */
  public static final int getChunkSize() {
    return Integer.parseInt(c_propODS.getProperty("ods_chunk_size", "5000"));
  }

 /**
 * Return the Targeted Enterprise for the profile
 */
  public static final String getProfileEnterprise() {
    return c_propODS.getProperty("pdh_profile_enterprise", "NONE");
  }

 /**
 * Return the Classify Entity List from the ods.server.properties file
 */
  public static final String getClassifyC1(String _strPrefix) {
    return c_propODS.getProperty(_strPrefix + "_C1", "NOTHING");
  }

 /**
 * Return the Classify Entity List from the ods.server.properties file
 */
  public static final String getClassifyC2(String _strPrefix) {
    return c_propODS.getProperty(_strPrefix + "_C2", "NOTHING");
  }

 /**
 * Return the Classify Entity List from the ods.server.properties file
 */
  public static final String getClassifyC3(String _strPrefix) {
    return c_propODS.getProperty(_strPrefix + "_C3", "NOTHING");
  }

 /**
 * Return the Classify Entity List from the ods.server.properties file
 */
  public static final String getClassifyC4(String _strPrefix) {
    return c_propODS.getProperty(_strPrefix + "_C4", "NOTHING");
  }

    public static final String getTableSpace(String _strSchema, String _strTableName) {
       return c_propODS.getProperty(_strSchema + "_" + _strTableName + "_tablespace","DEFAULT");
    }

    public static final String getIndexSpace(String _strSchema, String _strTableName) {
       return c_propODS.getProperty(_strSchema + "_" + _strTableName + "_indexspace","DEFAULT");
    }

    public static final String getDefaultTableSpace(String _strSchema) {
       return c_propODS.getProperty(_strSchema +  "_default_tablespace","DEFAULT");
    }

    public static final String getDefaultIndexSpace(String _strSchema) {
        return c_propODS.getProperty(_strSchema +  "_default_indexspace","DEFAULT");
    }

    public static final boolean isProdAttribute(String _strSchema, String _strAttributeCode) {
        return (c_propODS.getProperty(_strSchema + "_prodattr_" + _strAttributeCode,"n").equals("n") ? false : true);
    }

    public static final boolean isProdAttrGroup(String _strSchema, String _strEntityType) {
        return (c_propODS.getProperty(_strSchema + "_prodattrgrp_" + _strEntityType,"n").equals("n") ? false : true);
    }

    public static final String getProdAttributeMap(String _strSchema, String _strAttributeCode) {
        return c_propODS.getProperty(_strSchema + "_prodattr_" + _strAttributeCode,"n");
    }

    public static final boolean isEnglishOnly(String _strSchema, String _strAttributeCode) {
        return (c_propODS.getProperty(_strSchema + "_english_only_" + _strAttributeCode,"n").equals("y") ? true : false);
    }

    public static final boolean isMultiFlag(String _strSchema) {
        return (c_propODS.getProperty(_strSchema + "_multiflag","n").equals("y") ? true : false);
    }

    public static final boolean allNLSMode(String _strSchema, String _strTableName) {
     return (c_propODS.getProperty(_strSchema + "_" + _strTableName + "_nlsmode","def").equals("all") ? true : false);
  }

  public static final boolean geoNLSMode(String _strSchema, String _strTableName) {
     return (c_propODS.getProperty(_strSchema + "_" + _strTableName + "_nlsmode","def").equals("geo") ? true : false);
  }

  public static final boolean defNLSMode(String _strSchema, String _strTableName) {
     return (c_propODS.getProperty(_strSchema + "_" + _strTableName + "_nlsmode","def").equals("def") ? true : false);
  }

  public static final boolean includeTable(String _strSchema, String _strTableName) {
    if (c_propODS.getProperty(_strSchema + "_alltables","n").equals("y")) {
        return true;
    }
    return (c_propODS.getProperty(_strSchema + "_" + _strTableName + "_include","n").equals("n") ? false : true);
  }

  public static final boolean hasAttributeSubset(String _strSchema, String _strEntityType) {
      String strVal = c_propODS.getProperty(_strSchema + "_" + _strEntityType + "_attsubset");
      if(strVal == null || strVal.equals("")) {
          return false;
      }
      return true;
  }

  public static final boolean isAttributeSubset(String _strSchema, String _strEntityType, String _strAttributeCode) {
      String strVal = c_propODS.getProperty(_strSchema + "_" + _strEntityType + "_attsubset");
      // if no value, then it is in the subset of attributes (i.e. all atts for entity) by default
      if(strVal == null || strVal.equals("")) {
          return true;
      }
      StringTokenizer stList = new StringTokenizer(strVal,",");
      while (stList.hasMoreTokens()) {
          String strAtt = stList.nextToken();
          if(strAtt.equals(_strAttributeCode)) {
              return true;
          }
      }
      return false;
  }

  public static final boolean excludeEntityFromUpdate(String _strSchema, EntityItem _ei) {
      String strVal = c_propODS.getProperty(_strSchema + "_" + _ei.getEntityType() + "_exclude");
      if(strVal == null || strVal.equals("")) {
          return false;
      }
      StringTokenizer stList = new StringTokenizer(strVal,",");
    ATT_LIST_LOOP:
      while (stList.hasMoreTokens()) {
          String strAtt = stList.nextToken();
          StringTokenizer stAtt = new StringTokenizer(strAtt,":");
          String strAttCode = stAtt.nextToken();
          String strAttVal = stAtt.nextToken();
          EANAttribute oAtt = _ei.getAttribute(strAttCode);
          if(oAtt == null) {
              continue ATT_LIST_LOOP;
          }
          if(oAtt instanceof EANFlagAttribute) {
              EANFlagAttribute oFAtt = (EANFlagAttribute)oAtt;
              String strFlagCodes = oFAtt.getFlagCodes();
              if(strFlagCodes == null || strFlagCodes.equals("")) {
                  continue ATT_LIST_LOOP;
              }
              StringTokenizer stFlagCodes = new StringTokenizer(strFlagCodes,":");
              while(stFlagCodes.hasMoreTokens()) {
                  if(stFlagCodes.nextToken().equals(strAttVal)) {
                     return true;
                  }
              }
          } else {
             continue ATT_LIST_LOOP; // we're only dealing w/ Flag Attributes for now...
          }
      }
      return false;
  }

  public static final boolean hasFKeyMap(String _strSchema, String _strEntityType) {
    return (c_propODS.getProperty(_strSchema + "_fkey_" + _strEntityType,"n").equals("n") ? false : true);
  }

  public static final String getFKeyMap(String _strSchema, String _strEntityType) {
    return c_propODS.getProperty(_strSchema + "_fkey_" + _strEntityType,"n");
  }

  public static final String getFKeyMapPair(String _strSchema, String _strEntityType) {
    return c_propODS.getProperty(_strSchema + "_fkeypair_" + _strEntityType,"n");
  }


  public static final boolean isProdAttributeRelator(String _strSchema, String _strTableName) {
     return (c_propODS.getProperty(_strSchema + "_prodattr_" + _strTableName,"n").equals("y") ? true : false);
  }

  /**
  * if a cross post reletor.. the software will update the parent's val from
  */
  public static final boolean isCrossPostRelator(String _strSchema, String _strEntityType) {
     return (c_propODS.getProperty(_strSchema + "_crosspostrelator_" + _strEntityType,"n").equals("y") ? true : false);
  }

  /**
  * is it a cross post entity
  */
  public static final boolean isCrossPostEntity(String _strSchema, String _strEntityType) {
     return (c_propODS.getProperty(_strSchema + "_crosspostentity_" + _strEntityType,"n").equals("n") ? false : true);
  }

  /**
  * If it is a cross post entity, return a string of relator,tables pairs to process
  */
  public static String getCrossPostEntities(String _strSchema, String _strEntityType) {
    return c_propODS.getProperty(_strSchema + "_crosspostentity_" + _strEntityType,"");
  }

  public static final boolean isFKeyMapRelator(String _strSchema, String _strEntityType) {
    return (c_propODS.getProperty(_strSchema + "_fkeyrelator_" + _strEntityType,"n").equals("y") ? true :  false);
  }

  public static final boolean hasPublishFlag(String _strSchema, String _strEntityType) {
      return (c_propODS.getProperty(_strSchema + "_haspublishflag_" + _strEntityType,"n").equals("y") ? true :  false);
  }

/**
 * Rollup attributes
 */
  public static final boolean isRollupAttribute(String _strSchema, String _strEntityType, String _strAttributeCode) {
      String strVal = c_propODS.getProperty(_strSchema + "_rollupattr_" + _strEntityType + "." + _strAttributeCode);
      if(strVal == null || strVal.trim().equals("")) {
	      return false;
      }
      return true;
  }

/**
 * Will return an array of strings in the form of etype.attcode; representing the root to place the value on.
 */
  public static final String[] getRollupAttributeMappings(String _strSchema, String _strEntityType, String _strAttributeCode) {
      String strVal = c_propODS.getProperty(_strSchema + "_rollupattr_" + _strEntityType + "." + _strAttributeCode);
      if(strVal == null || strVal.equals("")) {
		  return new String[]{};
	  }
      Vector v = new Vector();
      // first parse the comma seperated list
      StringTokenizer stList = new StringTokenizer(strVal,",");
      while (stList.hasMoreTokens()) {
          String strPair = stList.nextToken().trim();
          v.addElement(strPair);
      }
      String[] sa = new String[v.size()];
      v.copyInto(sa);
      return sa;
  }

/**
 * Check for toggle on processing of rollup attribtues
 * value must be "y" or "Y"
 */
  public static final boolean performRollupMultiAttributes(String _strSchema) {
	  String strVal = c_propODS.getProperty(_strSchema + "_processrollupattr");
	  if(strVal == null || !strVal.trim().equalsIgnoreCase("y")) {
		  return false;
	  }
	  return true;
  }
/**
 * Allow us to refresh the MULTIATTRIBUTE table in a net load
 */
  public static final boolean initMultiAttributeInNet(String _strSchema) {
	  String strVal = c_propODS.getProperty(_strSchema + "_initRollupInNet");
	  if(strVal == null || !strVal.equalsIgnoreCase("y")) {
		  return false;
	  }
	  return true;
  }
/**
 * We are now skipping entities which are withdrawn
 */
  public static final boolean isWithdrawCheckEntity(String _strSchema, String _strEntityType) {
	  String strVal = c_propODS.getProperty(_strSchema + "_withdrawcheck_" + _strEntityType);
	  if(strVal == null || strVal.equals("")) {
		  return false;
	  }
	  return true;
  }
/**
 * We are now skipping entities which are withdrawn
 */
  public static final boolean isWithdrawCheckAttribute(String _strSchema, String _strEntityType, String _strAttributeCode) {
	  String strVal = c_propODS.getProperty(_strSchema + "_withdrawcheck_" + _strEntityType);
	  if(strVal == null || strVal.equals("")) {
		  return false;
	  } else if (strVal.equalsIgnoreCase(_strAttributeCode)) {
		  return true;
	  }
	  return false;
  }

  public static String getMultiAttributeTableName(String _strSchema) {
    return c_propODS.getProperty(_strSchema + "_multiattrtabname");
  }

  public static Integer[] getMultiAttributeNLSs(String _strSchema) {
      String strVal = c_propODS.getProperty(_strSchema + "_multiattr_nls_list");
      if(strVal == null || strVal.equals("")) {
		  return new Integer[]{};
	  }
      Vector v = new Vector();
      // first parse the comma seperated list
      StringTokenizer stList = new StringTokenizer(strVal,",");
      while (stList.hasMoreTokens()) {
          String strNLS = stList.nextToken().trim();
          try {
			  Integer oNLS = new Integer(Integer.parseInt(strNLS));
			  v.addElement(oNLS);
		  } catch(NumberFormatException nfe) {
			  // blah
			  System.err.println("OdsServerProperties.getMultiAttributeNLSs NFE:" + nfe.toString());
		  }
      }
      Integer[] ia = new Integer[v.size()];
      v.copyInto(ia);
      return ia;
  }

  public static boolean isMultiAttributeEntity(String _strSchema, String _strEntityType) {
	  String strVal = c_propODS.getProperty(_strSchema + "_rollup_" + _strEntityType);
	  // only 'y' or 'Y'!!
	  if(strVal == null || !strVal.trim().equalsIgnoreCase("y")) {
		  return false;
	  }
	  return true;
  }

  public static boolean isMultiAttributeRelator(String _strSchema, String _strRelType) {
	  String strVal = c_propODS.getProperty(_strSchema + "_rollup_relator_" + _strRelType);
	  if(strVal == null || strVal.equalsIgnoreCase("")) {
		  return false;
	  }
	  return true;
  }


  public static final String[] getMultiAttributeRelatorPath(String _strSchema, String _strRelType) {
	  String strVal = c_propODS.getProperty(_strSchema + "_rollup_relator_" + _strRelType);
        if(strVal == null || strVal.equals("")) {
  		  return new String[]{};
  	  }
      Vector v = new Vector();
      // first parse the comma seperated list
      StringTokenizer stList = new StringTokenizer(strVal,",");
      while (stList.hasMoreTokens()) {
          String strPair = stList.nextToken().trim();
          v.addElement(strPair);
      }
      String[] sa = new String[v.size()];
      v.copyInto(sa);
      return sa;
  }

/**
 * Go from PDH attcode->multiattribute table attcode
 */
  public static String[] getMultiAttributeMappings(String _strSchema, String _strPDHAttCode) {
      String strVal = c_propODS.getProperty(_strSchema + "_rollupattr_mapping_" + _strPDHAttCode);
      if(strVal == null || strVal.equals("")) {
          return new String[]{};
  	  }
      Vector v = new Vector();
      // first parse the comma seperated list
      StringTokenizer stList = new StringTokenizer(strVal,",");
      while (stList.hasMoreTokens()) {
          String strPair = stList.nextToken().trim();
          v.addElement(strPair);
      }
      String[] sa = new String[v.size()];
      v.copyInto(sa);
      return sa;
  }

/**
 * Return the date/time this class was generated
 * @return the date/time this class was generated
 */
  public static final String getVersion() {
    return new String("$Id: ODSServerProperties.java,v 1.54 2005/08/22 14:03:12 gregg Exp $");
  }
}
