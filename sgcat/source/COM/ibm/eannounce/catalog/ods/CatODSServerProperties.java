package COM.ibm.eannounce.catalog.ods;

import COM.ibm.eannounce.ods.ODSServerProperties;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * Licensed Materials -- Property of IBM

 * (c) Copyright International Business Machines Corporation, 2003
 *  All Rights Reserved.
 *
 * @author not attributable
 * @version 1.0
 */

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
public final class CatODSServerProperties extends Properties {
    /**
    * Don't let anyone instantiate this class.
    */

    private CatODSServerProperties() {
    }

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

   public static final int getCatDBODSProfileOPWGID() {
     return Integer.parseInt(c_propODS.getProperty("catDBODS_profile_opwgid", "1").trim());
     }



/**
* Return the number of openid number for the profile
*/
   public static final int getVarCharColumnLength() {
   return Integer.parseInt(c_propODS.getProperty("ods_varchar_column_length", "25").trim());
 }


 public static final int getAttrColLength(String _strAttrCode) {
   return Integer.parseInt(c_propODS.getProperty("ods_varchar_column_length_"+_strAttrCode, "0").trim());
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

 public static final String getCountryFilterAttribute(String _strSchema, String _strTableName) {
   String strVal = c_propODS.getProperty(_strSchema+"_"+_strTableName+"_CountryFilter","");
   return strVal;
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
   return new String("$Id: CatODSServerProperties.java,v 1.5 2011/05/05 11:22:00 wendy Exp $");
 }
 
 /**
  * Return final data tables list.
  * @return
  */
 public static String[] getFinalTableNames() {
 	String[] finalTables = null;
 	String finalTableStr = c_propODS.getProperty(CatODSServerProperties
 			.getDatabaseSchema()
 			+ "_final_tables");
 	if (finalTableStr != null) {		
 		StringTokenizer st = new StringTokenizer(finalTableStr, ",");
 		finalTables = new String[st.countTokens()];
 		int i = 0;
 		while (st.hasMoreTokens()) {
 			finalTables[i] = st.nextToken();
 			i++;
 		}
 	}
 	return finalTables;
 }  
 
 /**
  * Return approved tables status condition.
  * @return
  */
 public static String getFinalTablesStatus() {
	 String aggrTablesStr = c_propODS.getProperty("FINALTABLES_STATUS");
	 return aggrTablesStr;
 }  
 
 /**
  * Return final tables list.
  * @return
  */
 public static String getAggrTableNames() {
 	String aggrTablesStr = c_propODS.getProperty(CatODSServerProperties
 			.getDatabaseSchema()
 			+ "_aggr_flagtables");
 	return aggrTablesStr;
 } 
}


/*
$Log: CatODSServerProperties.java,v $
Revision 1.5  2011/05/05 11:22:00  wendy
src from IBMCHINA

Revision 1.2  2007/10/09 08:11:49  sulin
no message

Revision 1.1.1.1  2007/06/05 02:09:45  jingb
no message

Revision 1.3  2006/06/03 16:58:51  bala
get Attributewidth from prop file using getAttrColLength overriding default

Revision 1.2  2006/04/06 23:20:38  bala
add CountryFilter property and associated methods

Revision 1.1.1.1  2006/03/30 17:36:30  gregg
Moving catalog module from middleware to
its own module.

Revision 1.2  2005/10/20 17:49:48  bala
changes to get new custom profile for cat ods datamover

Revision 1.1  2005/09/26 17:30:41  bala
<No Comment Entered>

*/
