// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
//[]--------------------------------------------------------------------------[]
//|    Application Name: e-announce                                            |
//|           File Name: AUTOGENRpt.java                                       |
//|----------------------------------------------------------------------------|
//|          Programmer: Anhtuan Nguyen                                        |
//|        Date written: August 19, 2004                                       |
//|         Environment: Operating System: Windows 2000                        |
//|                              Compiler: IBM JDK 1.4                         |
//|----------------------------------------------------------------------------|
//|  Module Description: OIM 3.0b AutoGen(HW RFA) Extract                      |
//|----------------------------------------------------------------------------|
//|        Restrictions: None                                                  |
//|        Dependencies: None                                                  |
//|  NLS Considerations: None                                                  |
//|----------------------------------------------------------------------------|
//|      Change History:                                                       |
//|      Date            Programmer      Description/Comments                  |
//[]--------------------------------------------------------------------------[]
// $Log: AUTOGENRpt.java,v $
// Revision 1.21  2007/03/22 16:06:38  bala
// fix for parseString when string to parse does not have a delimiter in it
//
// Revision 1.20  2006/08/12 20:08:08  chris
// revert to last version of anhtuans
//
// Revision 1.9  2006/06/19 16:07:10  anhtuan
// Don't show same features in q779 new features table.
//
// Revision 1.8  2006/06/12 03:32:26  anhtuan
// Put in more dashes in the headers for q779.
//
// Revision 1.7  2006/06/03 01:51:40  anhtuan
// Updated specs for new section XCHARGES.txt(Q779) and new FORMAT3 for MTM announcement in CHARGES section.
//
// Revision 1.6  2006/05/04 16:46:50  anhtuan
// Updated specs 05/03/2006:
// 1) Fix gml tags in PRODNUM and CHARGES reports for Format 1 and Format 3
// 2) Removed hardcoding of model descriptions from PRODNUM and CHARGES reports for Format 1 and Format 3
// 3) Replaced hardcoded model descriptions per spec for PRODNUM and CHARGES for Format 1 and Format 3
// 4 )Replace model description mapping from MKTGNAME to PRCFILENAME for PRODNUM for Format 3
//
// Revision 1.5  2006/05/02 03:58:22  anhtuan
// New way of showing SEO Description. Use merge() instead of combination() for SEO Table.
//
// Revision 1.4  2006/04/24 15:10:19  anhtuan
// Fix populateSEOTable().
//
// Revision 1.3  2006/04/21 12:36:38  anhtuan
// CR041906713: Hardware-%-Base
//
// Revision 1.2  2006/02/09 23:50:22  yang
// change class from sg to sgv30b
//
// Revision 1.1  2006/02/09 23:36:18  yang
// adding AUTOGENRpt
//
// Revision 1.61  2006/01/26 14:36:11  anhtuan
// AHE copyright.
//
// Revision 1.60  2006/01/25 16:46:18  anhtuan
// New specs for SEO Description.
//
// Revision 1.59  2006/01/24 17:02:18  yang
// Jtest Changes
//
// Revision 1.58  2006/01/17 17:37:22  anhtuan
// New specs for SEO Table.
//
// Revision 1.57  2006/01/10 20:28:34  anhtuan
// Rename AUTOGENRpt30b.java to AUTOGENRpt.java.
//
// Revision 1.2  2005/12/08 17:40:40  anhtuan
// CR0801054544: Show "S = SUPPORTED" for Feature Matrix section.
// Don't show comment text , ".*", of FCMKTGDESC(FEATURE) in Sales Manual section.
//
// Revision 1.1  2005/11/17 21:33:33  anhtuan
// Initial version
//
// Revision 1.53  2005/09/15 18:16:17  anhtuan
// MN25063112.
//
// Revision 1.52  2005/08/25 13:58:45  anhtuan
// MN25078328.
//
// Revision 1.51  2005/08/18 18:41:06  anhtuan
// MN25058185. Show the following tags after :h3.Features and Specify Codes
// :xmp.
// .kp off
//
// Revision 1.50  2005/08/02 17:47:15  anhtuan
// MN24858606.Per Mark Gennrich:
// 1. Following the Feature Conversion section is a note, "PARTS RETURN: Parts removed or .....etc."  Eliminate all caps.  Should be "Parts Return".  Also, should be two spaces after the colon.  (eg. Parts Return:  Parts removed or ... etc.")
// 2. Remove the note (including the geo arrows), "Feature Removal Prices:  Feature removals ... etc."  We have been informed by the Price File that this no longer applies, and is causing confusion in the field.
//
// Revision 1.49  2005/07/21 16:13:36  anhtuan
// MN24702340. If description field is empty then don't show :li.Attributes provided: and :li.Attributes required: in the report for iSeries.
//
// Revision 1.48  2005/06/07 17:44:22  anhtuan
// MN ticket 24200422. Show effective dates for feature withdrawals of iSeries.
//
// Revision 1.47  2005/05/17 20:34:36  anhtuan
// Fix heading text.
//
// Revision 1.46  2005/05/17 16:07:19  anhtuan
// CR042805419
// CR0509056439
//
// Revision 1.45  2005/03/18 17:41:31  anhtuan
// Per Alan Crudo for Feature Matrix section of iSeries use COMNAME if INVNAME is empty.
//
// Revision 1.44  2005/03/16 19:31:54  anhtuan
// If DESCRIPTION field of entity FEATURE is empty then the following boilerplate is substituted in the autogen output:
// .*   DESCRIPTION FILE NOT FOUND
// .*
// .* BEGIN FEATURE TEMPLATE
// .sk1
// :ul c.
// .kp on
// :li.Attributes provided:
// :li.Attributes required:
//
// Revision 1.43  2005/03/16 18:53:00  anhtuan
// According to SG folks, the user will not enter :eul. tag in DESCRIPTION field of entity FEATURE.
//
// Revision 1.42  2005/03/13 22:46:13  anhtuan
// Sales Manual: put .kp on and .kp off inside :ul c.and :eul.
//
// Revision 1.41  2005/03/13 20:16:32  anhtuan
// Fixes.
//
// Revision 1.40  2005/03/13 02:53:00  anhtuan
// Remove one extra *. Maximum is 69 * within :xmp. and :exmp.
//
// Revision 1.39  2005/03/12 21:32:27  anhtuan
// CR0307055513, CR0307056511, latest Specs.
//
// Revision 1.38  2005/03/08 18:03:54  anhtuan
// Fixes.
//
// Revision 1.37  2005/03/03 17:25:41  anhtuan
// Fix text between :xmp. and :exmp. tags that > 69 characters.
//
// Revision 1.36  2005/03/03 03:36:30  anhtuan
// Fixes.
//
// Revision 1.35  2005/03/02 21:40:28  anhtuan
// TIR USRO-R-ACRO-6A4NXB
//
// Revision 1.34  2005/03/01 20:23:43  anhtuan
// TIR USRO-R-ACRO-6A3QD5
// TIR USRO-R-TRAA-69NKGZ
//
// Revision 1.33  2005/02/28 17:01:47  anhtuan
// TIR USRO-R-TRAA-69NLHT
//
// Revision 1.32  2005/02/22 19:40:22  anhtuan
// TIR USRO-R-ACRO-69NUPK
//
// Revision 1.31  2005/02/22 01:52:32  anhtuan
// TIR USRO-R-TRAA-69NKGZ
// TIR USRO-R-ACRO-69P3MK
//
// Revision 1.30  2005/02/20 20:08:40  anhtuan
// TIR USRO-R-ACRO-69P3BW
//
// Revision 1.29  2005/02/19 04:41:08  anhtuan
// Repeat header after every 50 rows for supported devices section
// and feature matrix section.
//
// Revision 1.28  2005/02/19 00:03:34  bala
// modify filebreak tag as gml comment
//
// Revision 1.27  2005/02/17 21:22:07  anhtuan
// Repeat header after every 50 rows for supported devices section.
//
// Revision 1.26  2005/02/17 15:43:06  anhtuan
// TIR USRO-R-TRAA-69NK44
// TIR USRO-R-ACRO-69NPYT
// TIR USRO-R-ACRO-69MTX9
// TIR USRO-R-ACRO-69NNVG
// TIR USRO-R-TRAA-69NL4B
//
// Revision 1.25  2005/02/15 16:53:32  anhtuan
// Use INVNAME(PRODSTRUCT)/MKTGNAME(PRODSTRUCT). If the value is blank then use INVNAME(FEATURE)/MKTGNAME(FEATURE).
// Show timestamp in each report.
//
// Revision 1.24  2005/02/10 19:22:16  anhtuan
// Append \n at the end of HTML comment for file break.
//
// Revision 1.23  2005/02/09 22:54:18  anhtuan
// Fix.
//
// Revision 1.22  2005/02/09 21:49:50  anhtuan
// Fix.
//
// Revision 1.21  2005/02/09 20:32:40  anhtuan
// CR1207043428, CR1207044247, CR112304474.
//
// Revision 1.19  2005/01/20 21:08:27  anhtuan
// Add support for xSeries, zSeries and TS. Add report section for Feature Matrix Error.
//
// Revision 1.18  2004/12/03 17:14:31  anhtuan
// New logic to determine if a feature is a new feature or an existing feature.
//
// Revision 1.17  2004/12/02 16:07:13  anhtuan
// Remove the word "priced" in the header of iSeries.
//
// Revision 1.16  2004/12/02 00:55:17  anhtuan
// Per Jim Bishop and Alan Crudo. Don't check for PRICEDFEATURE = Yes in Features section of Product Number section.
//
// Revision 1.15  2004/12/01 16:51:48  anhtuan
// Remove the word "priced" in the header of pSeries.
//
// Revision 1.14  2004/11/30 19:14:57  anhtuan
// Updated Sales Manual Section Specs.
//
// Revision 1.13  2004/11/24 21:53:40  anhtuan
// If PricedFeature = Y && ZeroPrice = N then output XXXX.XX.
// If PricedFeature = Y && ZeroPrice = Y then output NC.
//
// Revision 1.12  2004/11/18 02:18:03  anhtuan
// - Add method retrievePNFeaturesWithdraw().
//
// Revision 1.11  2004/11/17 19:47:49  joan
// change to getRowIndex
//
// Revision 1.10  2004/11/17 17:04:24  joan
// changes due to rowselectabletable key
//
// Revision 1.9  2004/11/16 22:49:03  anhtuan
// Fixes.
//
// Revision 1.8  2004/11/16 16:13:45  anhtuan
// Fixes.
//
// Revision 1.7  2004/11/15 02:53:32  anhtuan
// Handle Announcement type = Withdraw.
// Use OSLEVELCOMPLEMENT instead of OSLEVEL.
//
// Revision 1.6  2004/11/13 13:31:10  anhtuan
// Per Wayne Kehrli:
// if PRICEDFEATURE = YES then feature is a charged feature.
// if PRICEDFEATURE is empty then its default value is YES then feature is a charged feature.
// if ZEROPRICE is empty then its default value is No.
// Fix productNumber_FeatureCodes():Move INVNAME(PRODSTRUCT) or MKTGNAME(PRODSTRUCT) after FEATURECODE(FEATURE).
// retrievePNFeatures(): Group entries based on MACHTYPEATR(MODEL)
// Fix charges_FeatureCodes():Move INVNAME(PRODSTRUCT) or MKTGNAME(PRODSTRUCT) after FEATURECODE(FEATURE).
// retrieveChargesFeatures(): Group entries based on MACHTYPEATR(MODEL).
//
// Revision 1.5  2004/11/11 14:37:41  anhtuan
// Fix retrieveSalesManual().
// Remove "NOTE TO EDITORS -".
//
// Revision 1.4  2004/11/09 20:02:59  anhtuan
// Include EDITORNOTE(FEATURE) for featurematrix.
// PRICEDFEATURE(FEATURE) = Y || ZEROPRICE(FEATURE) = Y
// Fix layout.
//
// Revision 1.3  2004/11/05 22:13:53  anhtuan
// Fix retrieveSupportedDevices() to match layout of External Machine Type.
//
// Revision 1.2  2004/10/07 17:59:19  anhtuan
// Fix getUpLinkEntityItem(), getDownLinkEntityItem().
//
// Revision 1.1  2004/10/05 17:25:23  anhtuan
// Initial version. CR0629043214.

//3/6/2005
//Modify retrieveSalesManualFeatures(TreeMap tm, String hwfccat, boolean fmtType, StringBuffer outSB)
//to handle text > 79 characters
//
//3/5/2005
//Create 2 methods:
//retrieveChargesFeaturesFormat2_MTM()
//retrieveChargesFeaturesFormat2_Feature()
//
//3/4/2005
//Use another version of retrievePNFeaturesFormat2()

package COM.ibm.eannounce.abr.sgv30b;

import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;
import java.util.*;
import java.text.*;

/**********************************************************************************
* AUTOGENRpt class
*
*
*/
public class AUTOGENRpt
{
    /***************
    * Version
    */
    public final static  String VERSION = "$Revision: 1.21 $";

    private static final String DELIMITER = "|";
    private static final int MW_VENTITY_LIMIT = 50;
    private static final int ROW_LIMIT = 50;

    private static final String ANNOUNCEMENT_TYPE_NEW = "new";
    private static final String ANNOUNCEMENT_TYPE_WITHDRAW = "withdraw";

    private static final String ISERIES = "iSeries";
    private static final String PSERIES = "pSeries";
    private static final String TS = "totalStorage";
    private static final String XSERIES = "xSeries";
    private static final String ZSERIES = "zSeries";

    private static final int FORMAT1 = 1;
    private static final int FORMAT2 = 2;
    private static final int FORMAT3 = 3;

    /***************
    * GMLFORMAT
    */
    public static final boolean GMLFORMAT = true;
    /***************
    * XMLFORMAT
    */
    public static final boolean XMLFORMAT = false;

    private static final int NEWMODELS = 1;
    private static final int EXISTINGMODELS = 2;
    private static final int NEWFC = 1;
    private static final int EXISTINGFC = 2;

    private EntityList list;
    private GeneralAreaList gal;
    private Database dbCurrent;
    private EntityItem rootEntity = null;
    private String annDate = "";
    private String annCodeName = "";
    private String annType = "";
    private String annTypeDesc = "";

    private Vector availV = null;
    private Vector availVector = null;

    private Hashtable usGeoHT;
    private Hashtable apGeoHT;
    private Hashtable laGeoHT;
    private Hashtable canGeoHT;
    private Hashtable emeaGeoHT;

    private Hashtable geoHT;

    private String brand = "";
    private int format = 0;
    private String inventoryGroup = "";
    private String inventoryGroupFlag = "";

    private TreeSet machineTypeTS;
    private Hashtable featureHT;

    private TreeMap productNumber_NewModels_TM;
    private Hashtable productNumber_NewModels_HT;
    private TreeMap productNumber_NewFC_TM;
    private TreeMap productNumber_ExistingFC_TM;
    private TreeMap productNumber_NewModels_NewFC_TM;
    private TreeMap productNumber_NewModels_ExistingFC_TM;
    private TreeMap productNumber_ExistingModels_NewFC_TM;
    private TreeMap productNumber_ExistingModels_ExistingFC_TM;
    private TreeMap productNumber_MTM_Conversions_TM;
    private TreeMap productNumber_Model_Conversions_TM;
    private TreeMap productNumber_Feature_Conversions_TM;
    private Vector featureVector;

    private TreeMap charges_NewModels_TM;
    private TreeMap charges_NewFC_TM;
    private TreeMap charges_ExistingFC_TM;
    private TreeMap charges_NewModels_NewFC_TM;
    private TreeMap charges_NewModels_ExistingFC_TM;
    private TreeMap charges_ExistingModels_NewFC_TM;
    private TreeMap charges_ExistingModels_ExistingFC_TM;
    private TreeMap charges_Feature_Conversions_TM;

    private TreeMap q779_NewModels_TM;
    private TreeMap q779_NewFeatures_TM;

    private TreeMap salesManual_TM;
    private TreeMap salesManualSpecifyFeatures_TM;
    private TreeMap salesManualSpecialFeaturesInitialOrder_TM;
    private TreeMap salesManualSpecialFeaturesOther_TM;

    private TreeMap supportedDevices_TM;

    private TreeMap featureMatrix_TM;

    private StringBuffer headerSB;

    private Vector lseoVector;
    private TreeSet mtmTS;
    private TreeMap seoTable_TM;
    private TreeMap seoDescription_TM;

    private TreeSet featureMatrixError;

    //Debug information
    //private StringBuffer sb;
    private boolean debug;
    private StringBuffer debugBuff;

    private static final int I_10 = 10;
    private static final int I_11 = 11;
    private static final int I_12 = 12;
    private static final int I_13 = 13;
    private static final int I_14 = 14;
    private static final int I_15 = 15;
    private static final int I_16 = 16;
    private static final int I_17 = 17;
    private static final int I_18 = 18;
    private static final int I_19 = 19;
    private static final int I_20 = 20;
    private static final int I_21 = 21;
    private static final int I_22 = 22;
    private static final int I_23 = 23;
    private static final int I_24 = 24;
    private static final int I_25 = 25;
    private static final int I_26 = 26;
    private static final int I_27 = 27;
    private static final int I_28 = 28;
    private static final int I_29 = 29;
    private static final int I_30 = 30;
    //private static final int I_32 = 32;
    //private static final int I_35 = 35;
    private static final int I_37 = 37;
    private static final int I_38 = 38;
    private static final int I_41 = 41;
    private static final int I_50 = 50;
    private static final int I_51 = 51;
    private static final int I_53 = 53;
    private static final int I_58 = 58;
    private static final int I_68 = 68;
    private static final int I_69 = 69;
    private static final int I_70 = 70;
    //private static final int I_75 = 75;
    private static final int I_79 = 79;
    //private static final int I_N3 = -3;

    private static final char[] FOOL_JTEST = {'\n'};
    static final String NEWLINE = new String(FOOL_JTEST);

    /***********************************************
    * Constructor
    *
    * @param aList EntityList
    * @param aGal GeneralAreaList
    * @param aDBCurrent Database
    */
    public AUTOGENRpt(EntityList aList, GeneralAreaList aGal, Database aDBCurrent)
    {
        list = aList;
        gal = aGal;
        dbCurrent = aDBCurrent;

        //sb = new StringBuffer();

        usGeoHT = new Hashtable();
        apGeoHT = new Hashtable();
        laGeoHT = new Hashtable();
        canGeoHT = new Hashtable();
        emeaGeoHT = new Hashtable();

        geoHT = new Hashtable();

        machineTypeTS = new TreeSet();
        featureHT = new Hashtable();

        productNumber_NewModels_TM = new TreeMap();
        productNumber_NewModels_HT = new Hashtable();
        productNumber_NewFC_TM = new TreeMap();
        productNumber_ExistingFC_TM = new TreeMap();
        productNumber_NewModels_NewFC_TM = new TreeMap();
        productNumber_NewModels_ExistingFC_TM = new TreeMap();
        productNumber_ExistingModels_NewFC_TM = new TreeMap();
        productNumber_ExistingModels_ExistingFC_TM = new TreeMap();
        productNumber_MTM_Conversions_TM = new TreeMap();
        productNumber_Model_Conversions_TM = new TreeMap();
        productNumber_Feature_Conversions_TM = new TreeMap();
        featureVector = new Vector();

        charges_NewModels_TM = new TreeMap();
        charges_NewFC_TM = new TreeMap();
        charges_ExistingFC_TM = new TreeMap();
        charges_NewModels_NewFC_TM = new TreeMap();
        charges_NewModels_ExistingFC_TM = new TreeMap();
        charges_ExistingModels_NewFC_TM = new TreeMap();
        charges_ExistingModels_ExistingFC_TM = new TreeMap();
        charges_Feature_Conversions_TM = new TreeMap();

        q779_NewModels_TM = new TreeMap();
        q779_NewFeatures_TM = new TreeMap();

        salesManual_TM = new TreeMap();
        salesManualSpecifyFeatures_TM = new TreeMap();
        salesManualSpecialFeaturesInitialOrder_TM = new TreeMap();
        salesManualSpecialFeaturesOther_TM = new TreeMap();

        supportedDevices_TM  = new TreeMap();

        featureMatrix_TM = new TreeMap();

        headerSB = new StringBuffer();

        lseoVector = new Vector();
        mtmTS = new TreeSet();
        seoTable_TM = new TreeMap();
        seoDescription_TM = new TreeMap();

        featureMatrixError = new TreeSet();

        //Initialize to no debug
        debug = false;
        debugBuff = new StringBuffer();
    }

    /***********************************************
    * init_withDebug()
    *
    * @param sb StringBuffer
    * @return boolean
    */
    public boolean init_withDebug(StringBuffer sb)
    {
        debug = true;
        return init(sb);
    }

    /***********************************************
    * init()
    *
    * @param sb StringBuffer
    * @return boolean
    */
    public boolean init(StringBuffer sb)
    {
        Hashtable tempHT;
        EntityGroup eg = list.getParentEntityGroup();

        if(null == eg || eg.getEntityItemCount() > 1)
        {
            return false;
        }

        rootEntity = eg.getEntityItem(0);
        if(isSelected(rootEntity, "BRAND", "0010"))
        {
            brand = ISERIES;
            format = FORMAT1;
        }
        else if(isSelected(rootEntity, "BRAND", "0020"))
        {
            brand = PSERIES;
            format = FORMAT2;
        }
        else if(isSelected(rootEntity, "BRAND", "0030"))
        {
            brand = TS;
            format = FORMAT2;
        }
        else if(isSelected(rootEntity, "BRAND", "0040"))
        {
            brand = XSERIES;
            format = FORMAT3;
        }
        else if(isSelected(rootEntity, "BRAND", "0050"))
        {
            brand = ZSERIES;
            format = FORMAT1;
        }
        else
        {
            sb.append("<p><b>Unsupported brand.</b></p>" + NEWLINE);
            log("Unsupported brand");
            return false;
        }

        if(isSelected(rootEntity, "ANNTYPE", "19"))
        {
            annType = ANNOUNCEMENT_TYPE_NEW;
            annTypeDesc = "New";
        }
        else if(isSelected(rootEntity, "ANNTYPE", "14"))
        {
            annType = ANNOUNCEMENT_TYPE_WITHDRAW;
            annTypeDesc = "End Of Life - Withdrawal from mktg";
        }
        else if(isSelected(rootEntity, "ANNTYPE", "12"))
        {
            annType = ANNOUNCEMENT_TYPE_WITHDRAW;
            annTypeDesc = "End Of Life - Change to End Of Service Date";
        }
        else if(isSelected(rootEntity, "ANNTYPE", "13"))
        {
            annType = ANNOUNCEMENT_TYPE_WITHDRAW;
            annTypeDesc = "End Of Life - Discontinuance of service";
        }
        else if(isSelected(rootEntity, "ANNTYPE", "16"))
        {
            annType = ANNOUNCEMENT_TYPE_WITHDRAW;
            annTypeDesc = "End Of Life - Both";
        }
        else
        {
            sb.append("<p><b>Unsupported Annoucement Type.</b><br />" + NEWLINE);
            sb.append("<b>Supported Announcement Types Are: New, End Of Life.</b></p>" + NEWLINE);
            return false;
        }

        annDate = getAttributeValue(rootEntity, "ANNDATE", "", "", false);
        annCodeName = getAttributeValue(rootEntity, "ANNCODENAME", "", "", false);
        inventoryGroup = getAttributeValue(rootEntity, "INVENTORYGROUP", "", "", false);
        inventoryGroupFlag = getAttributeFlagValue(rootEntity, "INVENTORYGROUP");

        log("Announcement EID = " + (new Integer(rootEntity.getEntityID())).toString());
        log("ANNDATE is " + annDate + " and ANNCODENAME is " + annCodeName);
        log("Brand = " + getAttributeValue(rootEntity, "BRAND", "", "", false));

        availV = getAllLinkedEntities(eg, "ANNAVAILA", "AVAIL");
        tempHT = new Hashtable();
        if(annType.equals(ANNOUNCEMENT_TYPE_NEW))
        {
            tempHT.put("AVAILTYPE", "146"); //Planned Availability
            availVector = getEntitiesWithMatchedAttr(availV, tempHT);
        }
        else if(annType.equals(ANNOUNCEMENT_TYPE_WITHDRAW))
        {
            tempHT.put("AVAILTYPE", "149"); //Last Order
            availVector = getEntitiesWithMatchedAttr(availV, tempHT);
            tempHT.clear();
            tempHT.put("AVAILTYPE", "151"); //End of Service
            availVector.addAll(getEntitiesWithMatchedAttr(availV, tempHT));
            tempHT.clear();
            tempHT.put("AVAILTYPE", "152"); //End of Dev Support
            availVector.addAll(getEntitiesWithMatchedAttr(availV, tempHT));
            tempHT.clear();
            tempHT.put("AVAILTYPE", "153"); //Last Initial Order
            availVector.addAll(getEntitiesWithMatchedAttr(availV, tempHT));
        }

        if(availVector.size() == 0)
        {
            if(annType.equals(ANNOUNCEMENT_TYPE_NEW))
            {
                sb.append("<p><b>Announcement Type = " + annTypeDesc + " but no AVAIL Entities with Avail Type = Planned Availability are found.</b></p>");
            }
            else if(annType.equals(ANNOUNCEMENT_TYPE_WITHDRAW))
            {
                sb.append("<p><b>Announcement Type = " + annTypeDesc + " but no AVAIL Entities with Avail Type = Last Order or End of Service or End of Dev Support or Last Initial Order are found.</b></p>");
            }
            return false;
        }

        tempHT.clear();
        tempHT = null;
        availV.clear();
        availV = null;

        getListOfMTs();

        productNumber_NewModels();
        if(FORMAT1 == format)
        {
            productNumber_FeatureCodes1();
        }
        else if(FORMAT2 == format || FORMAT3 == format)
        {
            if(annType.equals(ANNOUNCEMENT_TYPE_NEW))
            {
                productNumber_FeatureCodes2();
            }
            else if(annType.equals(ANNOUNCEMENT_TYPE_WITHDRAW))
            {
                productNumber_FeatureCodes1();
            }
        }
        productNumber_MTM_Model_Conversions();
        productNumber_Feature_Conversions();

        if(annType.equals(ANNOUNCEMENT_TYPE_NEW))
        {
            charges_NewModels();
            if(FORMAT1 == format)
            {
                charges_FeatureCodes1();
            }
            else if(FORMAT2 == format || FORMAT3 == format)
            {
                charges_FeatureCodes2();
            }

            if(brand.equals(XSERIES))
            {
                q779_NewModels();
                q779_NewFeatures();
            }

            salesManual();
            supportedDevices();
            featureMatrix();

            if(brand.equals(XSERIES))
            {
                getGEOInfoForLSEO();
                getMTM();
                populateSEOTable();
                populateSEODescription();
            }
        }//end of if(annType.equals(ANNOUNCEMENT_TYPE_NEW))

        return true;
    }

    /********************************************************************************
    *
    *
    */
    private void getListOfMTs()
    {
        String mt = "";
        Iterator srcItr;
        Hashtable tempHT = new Hashtable();
        /*** Need to get flag codes for COFCAT, COFSUBCAT, COFGRP. As of today 8/17/04 flag codes are not defined yet ***/
        tempHT.clear();
        tempHT.put("COFCAT", "100");    //Hardware
        //if(!brand.equals(XSERIES))
        //{
        //    tempHT.put("COFSUBCAT", "126"); //System
        //}
        tempHT.put("COFGRP", "150");    //Base

        srcItr = availVector.iterator();
        while(srcItr.hasNext())
        {
            EntityItem availItem = (EntityItem) srcItr.next();
            Vector prodstructVector = getAllLinkedEntities(availItem, "OOFAVAIL", "PRODSTRUCT");
            for(int i = 0; i < prodstructVector.size(); i++)
            {
                EntityItem prodstructItem = (EntityItem) prodstructVector.get(i);

                EANEntity entityDownLink = getDownLinkEntityItem(prodstructItem, "MODEL");
                if(null != entityDownLink)
                {
                    if(isEntityWithMatchedAttr((EntityItem) entityDownLink, tempHT))
                    {
                        EntityItem modelItem = (EntityItem) entityDownLink;
                        EANEntity entityUpLink = getUpLinkEntityItem(prodstructItem, "FEATURE");
                        if(null != entityUpLink)
                        {
                            mt = getAttributeFlagValue(modelItem, "MACHTYPEATR");
                            if(null == mt)
                            {
                                mt = " ";
                            }
                            mt = mt.trim();
                            mt = setString(mt, 4);

                            machineTypeTS.add(mt);
                        }//end of if(null != entityUpLink)
                    }//end of if(isEntityWithMatchedAttr((EntityItem) entityDownLink, tempHT))
                }//end of if(null != entityDownLink)
            }//end of for(int i = 0; i < prodstructVector.size(); i++)
        }//end of while(srcItr.hasNext())
        tempHT.clear();
        tempHT = null;
    }

    /********************************************************************************
    *
    *
    */
    private void productNumber_NewModels()
    {
        Iterator srcItr;
        Hashtable tempHT = new Hashtable();
        /*** Need to get flag codes for COFCAT, COFSUBCAT, COFGRP. As of today 8/17/04 flag codes are not defined yet ***/
        tempHT.put("COFCAT", "100");    //Hardware
        //if(!brand.equals(XSERIES))
        //{
        //    tempHT.put("COFSUBCAT", "126"); //System
        //}
        tempHT.put("COFGRP", "150");    //Base

        //Path 1: ANNOUNCEMENT --> ANNAVAILA --> AVAIL --> MODELAVAIL --> MODEL
        srcItr = availVector.iterator();
        while(srcItr.hasNext())
        {
            EntityItem availItem = (EntityItem) srcItr.next();
            Vector modelVector = getAllLinkedEntities(availItem, "MODELAVAIL", "MODEL");
            modelVector = getEntitiesWithMatchedAttr(modelVector, tempHT);
            for(int i = 0; i < modelVector.size(); i++)
            {
                EntityItem modelItem = (EntityItem) modelVector.get(i);

                populate_PN_NewModels_TM(availItem, modelItem);
            }//end of for(int i = 0; i < modelVector.size(); i++)
        }//end of while(srcItr.hasNext())

      //Per Alan 11/29/2004, no path 2
      //Path 2: ANNOUNCEMENT --> ANNAVAILA --> AVAIL --> OOFAVAIL --> PRODSTRUCT --> MODEL
//      srcItr = availVector.iterator();
//      while(srcItr.hasNext())
//      {
//         EntityItem availItem = (EntityItem) srcItr.next();
//         Vector prodstructVector = getAllLinkedEntities(availItem, "OOFAVAIL", "PRODSTRUCT");
//         for(int i = 0; i < prodstructVector.size(); i++)
//         {
//            EntityItem prodstructItem = (EntityItem) prodstructVector.get(i);

//            for(int j = 0; j < prodstructItem.getDownLinkCount(); j++)
//            {
//               EANEntity entity = prodstructItem.getDownLink(j);
//               if(entity.getEntityType().equals("MODEL"))
//               {
//                  if(isEntityWithMatchedAttr((EntityItem) entity, tempHT))
//                  {
//                     EntityItem modelItem = (EntityItem) entity;
//log("modelItem.toString() = " + modelItem.toString());
//log("availItem.toString() = " + availItem.toString());
//                     populate_PN_NewModels_TM(availItem, modelItem);
//                  }
//               }//end of if(entity.getEntityType().equals("MODEL"))
//            }//end of for(int j = 0; j < prodstructItem.getDownLinkCount(); i++)
//         }//end of for(int i = 0; i < prodstructVector.size(); i++)
//      }//end of while(srcItr.hasNext())
    }

    /********************************************************************************
    *
    *
    * @param availItem EntityItem
    * @param modelItem EntityItem
    */
    private void populate_PN_NewModels_TM(EntityItem availItem, EntityItem modelItem)
    {
        /*
           Key = MACHTYPEATR(MODEL) +
                 MODELATR(MODEL) +
                 INVNAME(MODEL) or MKTGNAME(MODEL) +
                 Y or N/A or N from INSTALL(MODEL)
        */
        String key;
        String mt;
        String model;
        String geo;
        String modelItemKey = modelItem.getKey();
        updateGeoHT(availItem, modelItemKey);

        key = getAttributeFlagValue(modelItem, "MACHTYPEATR");
        if(null == key)
        {
            key = " ";
        }
        key = key.trim();
        key = setString(key, 4);
        mt = key;
        model = getAttributeValue(modelItem, "MODELATR", "", "000", false);
        model = model.trim();
        model = setString(model, 3);
        key = key + "<:>" + model;

        if(FORMAT1 == format)
        {
            String invName = getAttributeValue(modelItem, "INVNAME", "", "", false);
            invName = invName.trim();
            if(invName.equals(""))
            {
                invName =getAttributeValue(modelItem, "INTERNALNAME", "", "", false);
                if(invName.length() > I_28)
                {
                    invName = invName.substring(0, I_28);
                }
                invName = invName.trim();
                invName = invName.toUpperCase();
                //if(invName.equals(""))
                //   featureMatrixError.add("10<:>" + "Product Number<:>" + mt + "<:>" + model + "<:>MODEL<:>Price File Name");
            }

            invName = setString(invName, I_28);
            key = key + "<:>" + invName;
        }
        else if(FORMAT2 == format)
        {
            String mktgName = getAttributeValue(modelItem, "MKTGNAME", "", "", false);
            mktgName = mktgName.trim();
            if(mktgName.equals(""))
            {
                featureMatrixError.add("10<:>" + "Product Number<:>" + mt + "<:>" + model + "<:>MODEL<:>Marketing Name");
            }
            key = key + "<:>" + mktgName;
        }
        else if(FORMAT3 == format)
        {
            String invName = getAttributeValue(modelItem, "INVNAME", "", "", false);
            invName = invName.trim();
            if(invName.equals(""))
            {
                featureMatrixError.add("10<:>" + "Product Number<:>" + mt + "<:>" + model + "<:>MODEL<:>Price File Name");
            }

            invName = setString(invName, I_28);
            key = key + "<:>" + invName;
        }

        //Check for INSTALL = CIF(5671), INSTALL = CE(5672), INSTALL = 5673(N/A)
        if(isSelected(modelItem, "INSTALL", "5671"))
        {
            key = key + "<:>" + "Yes";
            productNumber_NewModels_HT.put(model, "Yes");
        }
        else if(isSelected(modelItem, "INSTALL", "5672"))
        {
            key = key + "<:>" + "No ";
            productNumber_NewModels_HT.put(model, "No ");
        }
        else if(isSelected(modelItem, "INSTALL", "5673"))
        {
            key = key + "<:>" + "N/A";
            productNumber_NewModels_HT.put(model, "N/A");
        }
        else
        {
            key = key + "<:>" + "   ";
            productNumber_NewModels_HT.put(model, "   ");
        }

        if(getAttributeValue(modelItem, "INSTALL", "", "", false).equals(""))
        {
            if(FORMAT1 == format)
            {
                featureMatrixError.add("10<:>" + "Product Number<:>" + mt + "<:>" + model + "<:>MODEL<:>Customer Install");
            }
        }

        //key = key + "<:>" + modelItemKey;
        geo = getGeo(modelItemKey);

        addToTreeMap(key, geo, productNumber_NewModels_TM);
    }

    /********************************************************************************
    *
    *
    */
    private void productNumber_FeatureCodes1()
    {
        /*
           Key = MACHTYPEATR(MODEL) +
                 FEATURECODE(FEATURE) +
                 MODELATR(MODEL) +
                 //INVNAME(PRODSTRUCT) or MKTGNAME(PRODSTRUCT) +
                 INVNAME(PRODSTRUCT) or INVNAME(FEATURE) or MKTGNAME(PRODSTRUCT) or MKTGNAME(FEATURE) +
                 Both or MES or Initial or Support from ORDERCODE(PRODSTRUCT)+
                 Y or N/A or N from INSTALL(PRODSTRUCT) +
                 Y or N from RETURNEDPARTS(PRODSTRUCT) +
                 EDITORNOTE(FEATURE)
                 EFFECTIVEDATE(AVAIL)
        */
        Hashtable tempHT = new Hashtable();
        String mt = "";
        boolean newFC = true;

        Iterator srcItr = availVector.iterator();
        while(srcItr.hasNext())
        {
            EntityItem availItem = (EntityItem) srcItr.next();
            Vector prodstructVector = getAllLinkedEntities(availItem, "OOFAVAIL", "PRODSTRUCT");
            for(int i = 0; i < prodstructVector.size(); i++)
            {
                EANEntity entityDownLink;
                EntityItem prodstructItem = (EntityItem) prodstructVector.get(i);
                String prodstructItemKey = prodstructItem.getKey();
                String effectiveDate = getAttributeValue(availItem, "EFFECTIVEDATE", "", "", false);
                updateGeoHT(availItem, prodstructItemKey);

                entityDownLink = getDownLinkEntityItem(prodstructItem, "MODEL");
                if(null != entityDownLink)
                {
                    /*** Need to get flag codes for COFCAT, COFSUBCAT, COFGRP. As of today 8/17/04 flag codes are not defined yet ***/
                    tempHT.clear();
                    tempHT.put("COFCAT", "100");    //Hardware
                    //if(!brand.equals(XSERIES))
                    //{
                    //    tempHT.put("COFSUBCAT", "126"); //System
                    //}
                    tempHT.put("COFGRP", "150");    //Base
                    if(isEntityWithMatchedAttr((EntityItem) entityDownLink, tempHT))
                    {
                        EntityItem modelItem = (EntityItem) entityDownLink;
                        EANEntity entityUpLink = getUpLinkEntityItem(prodstructItem, "FEATURE");
                        if(null != entityUpLink)
                        {
                            EntityItem featureItem;
                            String key;
                            String fc;
                            String model;
                            String featureFirstAnnDate;
                            String geo;
                            //Per Wayne Kehrli:
                            //if PRICEDFEATURE = YES then feature is a charged feature
                            //if PRICEDFEATURE is empty then its default value is YES then feature is a charged feature
                            tempHT.clear();
                            tempHT.put("PRICEDFEATURE", "120");  //No
                            //12/01/2004 Per Jim Bishop and Alan Crudo. Don't check for PRICEDFEATURE = Yes and PRICEDFEATURE is empty
                            //if(!isEntityWithMatchedAttr((EntityItem) entityUpLink, tempHT))
                            //{
                            featureItem = (EntityItem) entityUpLink;
                            key = getAttributeFlagValue(modelItem, "MACHTYPEATR");
                            if(null == key)
                            {
                                key = " ";
                            }
                            key = key.trim();
                            key = setString(key, 4);
                            mt = key;
                            fc = getAttributeValue(featureItem, "FEATURECODE", "", "", false);
                            fc = fc.trim();
                            fc = setString(fc, 4);
                            key = key + "<:>" + fc;
                            model = getAttributeValue(modelItem, "MODELATR", "", "000", false);
                            model = model.trim();
                            model = setString(model, 3);
                            key = key + "<:>" + model;

                            featureFirstAnnDate = getAttributeValue(featureItem, "FIRSTANNDATE", "", "", false);
                            if(annDate.equals(featureFirstAnnDate))
                            {
                                newFC = true;
                            }
                            else
                            {
                                if(isNewFeature(featureItem))
                                {
                                    newFC = true;
                                }
                                else
                                {
                                    newFC = false;
                                }
                            }

                            if(fc.equals("    "))
                            {
                                featureMatrixError.add("10<:>" + "Product Number<:>" +  mt + "<:>" + model + "<:>FEATURE<:>Feature Code");
                            }

                            if(FORMAT1 == format)
                            {
                                String invName = getAttributeValue(prodstructItem, "INVNAME", "", "", false);
                                invName = invName.trim();
                                if(invName.equals(""))
                                {
                                    invName = getAttributeValue(featureItem, "INVNAME", "", "", false);
                                    invName = invName.trim();
                                    if(invName.equals(""))
                                    {
                                        invName = getAttributeValue(featureItem, "COMNAME", "", "", false);
                                        if(invName.length() > I_28)
                                        {
                                            invName = invName.substring(0, I_28);
                                        }
                                        invName = invName.trim();
                                        invName = invName.toUpperCase();
                                        //if(invName.equals(""))
                                        //{
                                        //   featureMatrixError.add("10<:>" + "Product Number<:>" + mt + "<:>" + model + "<:>PRODSTRUCT " +  getAttributeValue(prodstructItem, "COMNAME", "", "", false) +  "<:>Price File Name");
                                        //   featureMatrixError.add("10<:>" + "Product Number<:>" + mt + "<:>" + model + "<:>FEATURE " + fc +  "<:>Price File Name");
                                        //}
                                    }
                                }
                                invName = setString(invName, I_28);
                                key = key + "<:>" + invName;
                            }
                            else if(FORMAT2 == format || FORMAT3 == format)
                            {
                                String mktgName = getAttributeValue(prodstructItem, "MKTGNAME", "", "", false);
                                mktgName = mktgName.trim();
                                if(mktgName.equals(""))
                                {
                                    mktgName = getAttributeValue(featureItem, "MKTGNAME", "", "", false);
                                    mktgName = mktgName.trim();
                                    if(mktgName.equals(""))
                                    {
                                        featureMatrixError.add("10<:>" + "Product Number<:>" + mt + "<:>" + model + "<:>PRODSTRUCT " + getAttributeValue(prodstructItem, "COMNAME", "", "", false) + "<:>Marketing Name");
                                        featureMatrixError.add("10<:>" + "Product Number<:>" + mt + "<:>" + model + "<:>FEATURE " + fc + "<:>Marketing Name");
                                    }
                                }
                                key = key + "<:>" + mktgName;
                            }

                            //Check for ORDERCODE = B, M, P, S
                            if(isSelected(prodstructItem, "ORDERCODE", "5955"))
                            {
                                key = key + "<:>" + "Both   ";
                            }
                            else if(isSelected(prodstructItem, "ORDERCODE", "5956"))
                            {
                                key = key + "<:>" + "MES    ";
                            }
                            else if(isSelected(prodstructItem, "ORDERCODE", "5957"))
                            {
                                key = key + "<:>" + "Initial";
                            }
                            else if(isSelected(prodstructItem, "ORDERCODE", "5958"))
                            {
                                key = key + "<:>" + "Support";
                            }
                            else
                            {
                                key = key + "<:>" + "       ";
                            }

                            if(getAttributeValue(prodstructItem, "ORDERCODE", "", "", false).equals(""))
                            {
                                if(FORMAT1 == format)
                                {
                                    featureMatrixError.add("10<:>" + "Product Number<:>" + mt + "<:>" + model + "<:>PRODSTRUCT " + getAttributeValue(prodstructItem, "COMNAME", "", "", false) + "<:>Original Order Code");
                                }
                            }

                            //Check for INSTALL = CIF(5671), INSTALL = CE(5672), INSTALL = 5673(N/A)
                            if(isSelected(prodstructItem, "INSTALL", "5671"))
                            {
                                key = key + "<:>" + "Yes";
                            }
                            else if(isSelected(prodstructItem, "INSTALL", "5672"))
                            {
                                key = key + "<:>" + "No ";
                            }
                            else if(isSelected(prodstructItem, "INSTALL", "5673"))
                            {
                                key = key + "<:>" + "N/A";
                            }
                            else
                            {
                                key = key + "<:>" + "   ";
                            }

                            if(getAttributeValue(prodstructItem, "INSTALL", "", "", false).equals(""))
                            {
                                if(FORMAT1 == format)
                                {
                                    featureMatrixError.add("10<:>" + "Product Number<:>" + mt + "<:>" + model + "<:>PRODSTRUCT " + getAttributeValue(prodstructItem, "COMNAME", "", "", false) + "<:>Customer Install");
                                }
                            }

                            if(isSelected(prodstructItem, "RETURNEDPARTS", "5100"))
                            {
                                key = key + "<:>" + "Yes";
                            }
                            else if(isSelected(prodstructItem, "RETURNEDPARTS", "5101"))
                            {
                                key = key + "<:>" + "No ";
                            }
                            else
                            {
                                key = key + "<:>" + "   ";
                            }

                            key = key + "<:>" + getAttributeValue(featureItem, "EDITORNOTE", "", "", false).trim();
                            key = key + "<:>" + effectiveDate;

                            geo = getGeo(prodstructItemKey);

                        //Per Alan Crudo if Announcement Date of the root ANNOUNCEMENT entity is the same as
                        //First Announcement Date of FEATURE entity then it is a new fc else existing fc.
//                        if(annDate.compareTo(featureFirstAnnDate) > 0)
//                        {
//                           addToTreeMap(key, geo, productNumber_ExistingFC_TM);
//                           log(featureItem.getKey() + ": " + fc + " is an existing fc");
//                        }
//                        else
//                        {
//                           addToTreeMap(key, geo, productNumber_NewFC_TM);
//                           log(featureItem.getKey() + ": " + fc + " is a new fc");
//                        }

                            if(newFC)
                            {
                                addToTreeMap(key, geo, productNumber_NewFC_TM);
                            }
                            else
                            {
                                addToTreeMap(key, geo, productNumber_ExistingFC_TM);
                            }
                     //}//end of if(!isEntityWithMatchedAttr((EntityItem) entityUpLink, tempHT))
                        }//end of if(null != entityUpLink)
                    }//end of if(isEntityWithMatchedAttr((EntityItem) entityDownLink, tempHT))
                }//end of if(null != entityDownLink)
            }//end of for(int i = 0; i < prodstructVector.size(); i++)
        }//end of while(srcItr.hasNext())
        tempHT.clear();
        tempHT = null;
    }

    /********************************************************************************
    *
    *
    */
    private void productNumber_FeatureCodes2()
    {
        /*
           Key = MACHTYPEATR(MODEL) +
                 FEATURECODE(FEATURE) +
                 MODELATR(MODEL) +
                 //INVNAME(PRODSTRUCT) or MKTGNAME(PRODSTRUCT) +
                 INVNAME(PRODSTRUCT) or INVNAME(FEATURE) or MKTGNAME(PRODSTRUCT) or MKTGNAME(FEATURE) +
                 Both or MES or Initial or Support from ORDERCODE(PRODSTRUCT)+
                 Y or N/A or N from INSTALL(PRODSTRUCT) +
                 Y or N from RETURNEDPARTS(PRODSTRUCT) +
                 EDITORNOTE(FEATURE)
                 MKTGNAME(MODEL) or INVNAME(MODEL)
        */
        Hashtable tempHT = new Hashtable();
        String mt = "";
        boolean newFC = true;
        boolean newModel = true;

        Iterator srcItr = availVector.iterator();
        while(srcItr.hasNext())
        {
            EntityItem availItem = (EntityItem) srcItr.next();
            Vector prodstructVector = getAllLinkedEntities(availItem, "OOFAVAIL", "PRODSTRUCT");
            for(int i = 0; i < prodstructVector.size(); i++)
            {
                EANEntity entityDownLink;
                EntityItem prodstructItem = (EntityItem) prodstructVector.get(i);
                String prodstructItemKey = prodstructItem.getKey();
                updateGeoHT(availItem, prodstructItemKey);

                entityDownLink = getDownLinkEntityItem(prodstructItem, "MODEL");
                if(null != entityDownLink)
                {
                    /*** Need to get flag codes for COFCAT, COFSUBCAT, COFGRP. As of today 8/17/04 flag codes are not defined yet ***/
                    tempHT.clear();
                    tempHT.put("COFCAT", "100");    //Hardware
                    //if(!brand.equals(XSERIES))
                    //{
                    //    tempHT.put("COFSUBCAT", "126"); //System
                    //}
                    tempHT.put("COFGRP", "150");    //Base
                    if(isEntityWithMatchedAttr((EntityItem) entityDownLink, tempHT))
                    {
                        EntityItem modelItem = (EntityItem) entityDownLink;
                        EANEntity entityUpLink = getUpLinkEntityItem(prodstructItem, "FEATURE");
                        if(null != entityUpLink)
                        {
                            EntityItem featureItem;
                            String key;
                            String fc;
                            String model;
                            String featureFirstAnnDate;
                            String mktgName;
                            String invName;
                            String geo;
                            //Per Wayne Kehrli:
                            //if PRICEDFEATURE = YES then feature is a charged feature
                            //if PRICEDFEATURE is empty then its default value is YES then feature is a charged feature
                            tempHT.clear();
                            tempHT.put("PRICEDFEATURE", "120");  //No
                            //12/01/2004 Per Jim Bishop and Alan Crudo. Don't check for PRICEDFEATURE = Yes and PRICEDFEATURE is empty
                            //if(!isEntityWithMatchedAttr((EntityItem) entityUpLink, tempHT))
                            //{
                            featureItem = (EntityItem) entityUpLink;
                            key = getAttributeFlagValue(modelItem, "MACHTYPEATR");
                            if(null == key)
                            {
                                key = " ";
                            }
                            key = key.trim();
                            key = setString(key, 4);
                            mt = key;
                            fc = getAttributeValue(featureItem, "FEATURECODE", "", "", false);
                            fc = fc.trim();
                            fc = setString(fc, 4);
                            key = key + "<:>" + fc;
                            model = getAttributeValue(modelItem, "MODELATR", "", "000", false);
                            model = model.trim();
                            model = setString(model, 3);
                            key = key + "<:>" + model;

                            newModel =  productNumber_NewModels_HT.containsKey(model);

                            featureFirstAnnDate = getAttributeValue(featureItem, "FIRSTANNDATE", "", "", false);
                            if(annDate.equals(featureFirstAnnDate))
                            {
                                newFC = true;
                            }
                            else
                            {
                                if(isNewFeature(featureItem))
                                {
                                    newFC = true;
                                }
                                else
                                {
                                    newFC = false;
                                }
                            }

                            if(fc.equals("    "))
                            {
                                featureMatrixError.add("10<:>" + "Product Number<:>" +  mt + "<:>" + model +"<:>FEATURE<:>Feature Code");
                            }

                            mktgName = getAttributeValue(prodstructItem, "MKTGNAME", "", "", false);
                            mktgName = mktgName.trim();
                            if(mktgName.equals(""))
                            {
                                mktgName = getAttributeValue(featureItem, "MKTGNAME", "", "", false);
                                mktgName = mktgName.trim();
                                if(mktgName.equals(""))
                                {
                                    featureMatrixError.add("10<:>" + "Product Number<:>" + mt + "<:>" + model + "<:>PRODSTRUCT " + getAttributeValue(prodstructItem, "COMNAME", "", "", false) + "<:>Marketing Name");
                                    featureMatrixError.add("10<:>" + "Product Number<:>" + mt + "<:>" + model + "<:>FEATURE " + fc + "<:>Marketing Name");
                                }
                            }
                            key = key + "<:>" + mktgName;

                            //Check for ORDERCODE = B, M, P, S
                            if(isSelected(prodstructItem, "ORDERCODE", "5955"))
                            {
                                key = key + "<:>" + "Both   ";
                            }
                            else if(isSelected(prodstructItem, "ORDERCODE", "5956"))
                            {
                                key = key + "<:>" + "MES    ";
                            }
                            else if(isSelected(prodstructItem, "ORDERCODE", "5957"))
                            {
                                key = key + "<:>" + "Initial";
                            }
                            else if(isSelected(prodstructItem, "ORDERCODE", "5958"))
                            {
                                key = key + "<:>" + "Support";
                            }
                            else
                            {
                                key = key + "<:>" + "       ";
                            }

                            //Check for INSTALL = CIF(5671), INSTALL = CE(5672), INSTALL = 5673(N/A)
                            if(isSelected(prodstructItem, "INSTALL", "5671"))
                            {
                                key = key + "<:>" + "Yes";
                            }
                            else if(isSelected(prodstructItem, "INSTALL", "5672"))
                            {
                                key = key + "<:>" + "No ";
                            }
                            else if(isSelected(prodstructItem, "INSTALL", "5673"))
                            {
                                key = key + "<:>" + "N/A";
                            }
                            else
                            {
                                key = key + "<:>" + "   ";
                            }

                            if(isSelected(prodstructItem, "RETURNEDPARTS", "5100"))
                            {
                                key = key + "<:>" + "Yes";
                            }
                            else if(isSelected(prodstructItem, "RETURNEDPARTS", "5101"))
                            {
                                key = key + "<:>" + "No ";
                            }
                            else
                            {
                                key = key + "<:>" + "   ";
                            }

                            key = key + "<:>" + getAttributeValue(featureItem, "EDITORNOTE", "", "", false).trim();

                            if(FORMAT2 == format)
                            {
                                mktgName = getAttributeValue(modelItem, "MKTGNAME", "", "", false);
                                mktgName = mktgName.trim();
                                if(mktgName.equals(""))
                                {
                                    featureMatrixError.add("10<:>" + "Product Number<:>" + mt + "<:>" + model + "<:>MODEL<:>Marketing Name");
                                }
                                key = key + "<:>" + mktgName;
                            }
                            else if(FORMAT3 == format)
                            {
                                invName = getAttributeValue(modelItem, "INVNAME", "", "", false);
                                invName = invName.trim();
                                if(invName.equals(""))
                                {
                                    featureMatrixError.add("10<:>" + "Product Number<:>" + mt + "<:>" + model + "<:>MODEL<:>Price File Name");
                                }

                                invName = setString(invName, I_28);
                                key = key + "<:>" + invName;
                            }

                            geo = getGeo(prodstructItemKey);

                        //Per Alan Crudo if Announcement Date of the root ANNOUNCEMENT entity is the same as
                        //First Announcement Date of FEATURE entity then it is a new fc else existing fc.
//                        if(annDate.compareTo(featureFirstAnnDate) > 0)
//                        {
//                           addToTreeMap(key, geo, productNumber_ExistingFC_TM);
//                           log(featureItem.getKey() + ": " + fc + " is an existing fc");
//                        }
//                        else
//                        {
//                           addToTreeMap(key, geo, productNumber_NewFC_TM);
//                           log(featureItem.getKey() + ": " + fc + " is a new fc");
//                        }

                            if(newModel && newFC)
                            {
                                addToTreeMap(key, geo, productNumber_NewModels_NewFC_TM);
                            }
                            else if(newModel && !newFC)
                            {
                                addToTreeMap(key, geo, productNumber_NewModels_ExistingFC_TM);
                            }
                            else if(!newModel && newFC)
                            {
                                addToTreeMap(key, geo, productNumber_ExistingModels_NewFC_TM);
                            }
                            else if(!newModel && !newFC)
                            {
                                addToTreeMap(key, geo, productNumber_ExistingModels_ExistingFC_TM);
                            }
                     //}//end of if(!isEntityWithMatchedAttr((EntityItem) entityUpLink, tempHT))
                        }//end of if(null != entityUpLink)
                    }//end of if(isEntityWithMatchedAttr((EntityItem) entityDownLink, tempHT))
                }//end of if(null != entityDownLink)
            }//end of for(int i = 0; i < prodstructVector.size(); i++)
        }//end of while(srcItr.hasNext())
        tempHT.clear();
        tempHT = null;
    }

    /********************************************************************************
    *
    *
    */
    private void productNumber_MTM_Model_Conversions()
    {
        Iterator srcItr = availVector.iterator();
        while(srcItr.hasNext())
        {
            EntityItem availItem = (EntityItem) srcItr.next();
            Vector modelconvertVector = getAllLinkedEntities(availItem, "MODELCONVERTAVAIL", "MODELCONVERT");
            for(int i = 0; i < modelconvertVector.size(); i++)
            {
                String tmpStr;
                String toMachType;
                String fromMachType;
                String returnedParts;
                EntityItem modelconvertItem = (EntityItem) modelconvertVector.get(i);
                String modelconvertItemKey = modelconvertItem.getKey();
                updateGeoHT(availItem, modelconvertItemKey);

                tmpStr = getAttributeValue(modelconvertItem, "TOMACHTYPE", "", "", false);
                tmpStr = tmpStr.trim();
                toMachType = setString(tmpStr, 4);
                tmpStr = getAttributeValue(modelconvertItem, "FROMMACHTYPE", "", "", false);
                tmpStr = tmpStr.trim();
                fromMachType = setString(tmpStr, 4);
                returnedParts = "";
                if(isSelected(modelconvertItem, "RETURNEDPARTS", "5100"))
                {
                    returnedParts = "Yes";
                }
                else if(isSelected(modelconvertItem, "RETURNEDPARTS", "5101"))
                {
                    returnedParts = "No ";
                }
                else
                {
                    returnedParts = "   ";
                }

                if(fromMachType.equals(toMachType))
                {
                    //Model conversions
                    /*
                       Key = FROMMACHTYPE(MODELCONVERT) +
                             TOMODEL(MODELCONVERT) +
                             FROMMODEL(MODELCONVERT) +
                             Y or N from RETURNEDPARTS(MODELCONVERT)
                    */
                    String geo;
                    String key = fromMachType;
                    tmpStr = getAttributeValue(modelconvertItem, "TOMODEL", "", "", false);
                    tmpStr = tmpStr.trim();
                    key = key + "<:>" + setString(tmpStr, 3);
                    tmpStr = getAttributeValue(modelconvertItem, "FROMMODEL", "", "", false);
                    tmpStr = tmpStr.trim();
                    key = key + "<:>" + setString(tmpStr, 3);
                    key = key + "<:>" + returnedParts;

                    geo = getGeo(modelconvertItemKey);
                    addToTreeMap(key, geo, productNumber_Model_Conversions_TM);
                }
                else
                {
                    //!fromMachType.equals(toMachType)
                    //MTM conversions
                    /*
                       Key = TOMACHTYPE(MODELCONVERT) +
                             TOMODEL(MODELCONVERT) +
                             FROMMACHTYPE(MODELCONVERT)
                             FROMMODEL(MODELCONVERT) +
                             Y or N from RETURNEDPARTS(MODELCONVERT)
                    */
                    String geo;
                    String key = toMachType;
                    tmpStr = getAttributeValue(modelconvertItem, "TOMODEL", "", "", false);
                    tmpStr = tmpStr.trim();
                    key = key + "<:>" + setString(tmpStr, 3);
                    key = key + "<:>" + fromMachType;
                    tmpStr = getAttributeValue(modelconvertItem, "FROMMODEL", "", "", false);
                    tmpStr = tmpStr.trim();
                    key = key + "<:>" + setString(tmpStr, 3);
                    key = key + "<:>" + returnedParts;

                    geo = getGeo(modelconvertItemKey);
                    addToTreeMap(key, geo, productNumber_MTM_Conversions_TM);
                }
            }//end of for(int i = 0; i < modelconvertVector.size(); i++)
        }//end of while(srcItr.hasNext())
    }

    /********************************************************************************
    *
    *
    */
    private void productNumber_Feature_Conversions()
    {
        Iterator srcItr;
        Hashtable tempHT = new Hashtable();

        //Get all features belong to this Announcement
        Vector prodstructVector = getAllLinkedEntities(availVector, "OOFAVAIL", "PRODSTRUCT");
        for(int i = 0; i < prodstructVector.size(); i++)
        {
            EntityItem prodstructItem = (EntityItem) prodstructVector.get(i);
            EANEntity entityUpLink = getUpLinkEntityItem(prodstructItem, "FEATURE");

            if(null != entityUpLink)
            {
                featureVector.add(entityUpLink.getKey());
            }
        }//end of for(int i = 0; i < prodstructVector.size(); i++)

        //Path 1:  ANNOUNCEMENT --> ANNAVAILA --> AVAIL --> FCTRANSACTION
        srcItr = availVector.iterator();
        while(srcItr.hasNext())
        {
            Vector fctransactionVector;
            Vector fctV;
            EntityItem availItem = (EntityItem) srcItr.next();
            Vector fctransactionV = getAllLinkedEntities(availItem, "FEATURETRNAVAIL", "FCTRANSACTION");
            tempHT.clear();
            tempHT.put("FTCAT", "404"); //Add and Delete
            fctransactionVector = getEntitiesWithMatchedAttr(fctransactionV, tempHT);
            tempHT.clear();
            tempHT.put("FTCAT", "406"); //Feature Conversion
            fctV = getEntitiesWithMatchedAttr(fctransactionV, tempHT);
            fctransactionVector.addAll(fctV);
            for(int i = 0; i < fctransactionVector.size(); i++)
            {
                EntityItem fctransactionItem = (EntityItem) fctransactionVector.get(i);
                populate_PN_Feature_Conversions_TM(availItem, fctransactionItem);
            }//end of for(int i = 0; i < fctransactionVector.size(); i++)
        }//end of while(srcItr.hasNext())

        //Path 2:  ANNOUNCEMENT --> ANNAVAILA --> AVAIL --> MODELCONVERT --> FCTRANSACTION
        srcItr = availVector.iterator();
        while(srcItr.hasNext())
        {
            Vector fctransactionVector;
            Vector fctV;
            EntityItem availItem = (EntityItem) srcItr.next();
            Vector modelconvertVector = getAllLinkedEntities(availItem, "MODELCONVERTAVAIL", "MODELCONVERT");
            Vector fctransactionV = getAllLinkedEntities(modelconvertVector, "MODELCONVFCTRANSAC", "FCTRANSACTION");
            tempHT.clear();
            tempHT.put("FTCAT", "404"); //Add and Delete
            fctransactionVector = getEntitiesWithMatchedAttr(fctransactionV, tempHT);
            tempHT.clear();
            tempHT.put("FTCAT", "406"); //Feature Conversion
            fctV = getEntitiesWithMatchedAttr(fctransactionV, tempHT);
            fctransactionVector.addAll(fctV);
            for(int i = 0; i < fctransactionVector.size(); i++)
            {
                EntityItem fctransactionItem = (EntityItem) fctransactionVector.get(i);
                populate_PN_Feature_Conversions_TM(availItem, fctransactionItem);
            }//end of for(int i = 0; i < fctransactionVector.size(); i++)
        }//end of while(srcItr.hasNext())
    }

    /********************************************************************************
    *
    *
    * @param availItem EntityItem
    * @param fctransactionItem EntityItem
    */
    private void populate_PN_Feature_Conversions_TM(EntityItem availItem, EntityItem fctransactionItem)
    {
        /*
           Key = HWFCCAT(TO FEATURE) +
                 TOMODEL(FCTRANSACTION) +
                 TOMACHTYPE(FCTRANSACTION) +
                 TOFEATURECODE(FCTRANSACTION) +
                 FROMMODEL(FCTRANSACTION) +
                 FROMMACHTYPE(FCTRANSACTION) +
                 FROMFEATURECODE(FCTRANSACTION) +
                 RETURNEDPARTS(FCTRANSACTION) +
                 EDITORNOTE(TO FEATURE)
        */
        /*
           Key = HWFCCAT(TO FEATURE) +
                 TOMACHTYPE(FCTRANSACTION) +
                 TOMODEL(FCTRANSACTION) +
                 TOFEATURECODE(FCTRANSACTION) +
                 FROMMACHTYPE(FCTRANSACTION) +
                 FROMMODEL(FCTRANSACTION) +
                 FROMFEATURECODE(FCTRANSACTION) +
                 MKTGNAME(TO FEATURE) +
                 MKTGNAME(FROM FEATURE) +
                 RETURNEDPARTS(FCTRANSACTION)+
                 EDITORNOTE(TO FEATURE)
        */
        String toModel;
        String key1;
        String toMachType;
        String toFeatureCode;
        String fromModel;
        String fromMachType;
        String fromFeatureCode;
        String effectiveDate;
        String key2;
        Vector toFeatureVector;
        Vector fromFeatureVector;
        String returnedParts;
        String geo;
        String hwfccat;
        String fctransactionItemKey = fctransactionItem.getKey();
        updateGeoHT(availItem, fctransactionItemKey);

        toModel = getAttributeValue(fctransactionItem, "TOMODEL", "", "", false);
        toModel = toModel.trim();
        toModel = setString(toModel, 3);
        key1 = toModel;
        toMachType = getAttributeValue(fctransactionItem, "TOMACHTYPE", "", "", false);
        toMachType = toMachType.trim();
        toMachType = setString(toMachType, 4);
        key1 = key1 + "<:>" + toMachType;
        toFeatureCode = getAttributeValue(fctransactionItem, "TOFEATURECODE", "", "", false);
        toFeatureCode = toFeatureCode.trim();
        toFeatureCode = setString(toFeatureCode, 4);
        key1 = key1 + "<:>" + toFeatureCode;
        fromModel = getAttributeValue(fctransactionItem, "FROMMODEL", "", "", false);
        fromModel = fromModel.trim();
        fromModel = setString(fromModel, 3);
        //key1 = key1 + "<:>" + fromModel;
        key1 = key1 + "<:>" + " ";
        fromMachType = getAttributeValue(fctransactionItem, "FROMMACHTYPE", "", "", false);
        fromMachType = fromMachType.trim();
        fromMachType = setString(fromMachType, 4);
        //key1 = key1 + "<:>" + fromMachType;
        key1 = key1 + "<:>" + " ";
        fromFeatureCode = getAttributeValue(fctransactionItem, "FROMFEATURECODE", "", "", false);
        fromFeatureCode = fromFeatureCode.trim();
        fromFeatureCode = setString(fromFeatureCode, 4);
        key1 = key1 + "<:>" + fromFeatureCode;
        effectiveDate = getAttributeValue(availItem, "EFFECTIVEDATE", "", "", false);

        key2 = toMachType;
        key2 = key2 + "<:>" + toModel;
        key2 = key2 + "<:>" + toFeatureCode;
        key2 = key2 + "<:>" + fromMachType;
        key2 = key2 + "<:>" + fromModel;
        key2 = key2 + "<:>" + fromFeatureCode;

        toFeatureVector = getFeatureEntities(toMachType, toModel, toFeatureCode);
        fromFeatureVector = getFeatureEntities(fromMachType, fromModel, fromFeatureCode);

        returnedParts = "";
        if(isSelected(fctransactionItem, "RETURNEDPARTS", "5100"))
        {
            returnedParts = "Yes";
        }
        else if(isSelected(fctransactionItem, "RETURNEDPARTS", "5101"))
        {
            returnedParts = "No ";
        }
        else
        {
            returnedParts = "   ";
        }

        geo = getGeo(fctransactionItemKey);

        hwfccat = "";

        if(toFeatureVector.size() == 0)
        {
            hwfccat = "UNKNOWN HWFCCAT(FEATURE)";
            //key1 = hwfccat + "<:>" + key1;
            key1 = " " + "<:>" + key1;
            key1 = key1 + "<:>" + returnedParts;
            key1 = key1 + "<:>" + "";
            key1 = key1 + "<:>" + effectiveDate;
            addToTreeMap(key1, geo, productNumber_Feature_Conversions_TM);
        }
        else
        {
            for(int i = 0; i < toFeatureVector.size(); i++)
            {
                EntityItem ei = (EntityItem) toFeatureVector.get(i);
                hwfccat = getAttributeValue(ei, "HWFCCAT", "", "UNKNOWN HWFCCAT(FEATURE)", false);
                //key1 = hwfccat + "<:>" + key1;
                key1 = " " + "<:>" + key1;
                key1 = key1 + "<:>" + returnedParts;
                key1 = key1 + "<:>" + getAttributeValue(ei, "EDITORNOTE", "", "", false).trim();
                key1 = key1 + "<:>" + effectiveDate;
                addToTreeMap(key1, geo, productNumber_Feature_Conversions_TM);
            }
        }

        if((fromFeatureVector.size() == 0) && (toFeatureVector.size() == 0))
        {
            hwfccat = "UNKNOWN HWFCCAT(FEATURE)";
            key2 = hwfccat + "<:>" + key2;
            key2 = key2 + "<:>" + "" + "<:>" + "";
            key2 = key2 + "<:>" + returnedParts;
            key2 = key2 + "<:>" + "";
            addToTreeMap(key2, geo, charges_Feature_Conversions_TM);
            log("In 1");
        }

        if((fromFeatureVector.size() == 0) && (toFeatureVector.size() > 0))
        {
            for(int i = 0; i < toFeatureVector.size(); i++)
            {
                EntityItem ei = (EntityItem) toFeatureVector.get(i);
                hwfccat = getAttributeValue(ei, "HWFCCAT", "", "UNKNOWN HWFCCAT(FEATURE)", false);
                key2 = hwfccat + "<:>" + key2;
                key2 = key2 + "<:>" + getAttributeValue(ei, "MKTGNAME", "", "", false);
                key2 = key2 + "<:>" + "";
                key2 = key2 + "<:>" + returnedParts;
                key2 = key2 + "<:>" + getAttributeValue(ei, "EDITORNOTE", "", "", false).trim();
                addToTreeMap(key2, geo, charges_Feature_Conversions_TM);
            }
            log("In 2");
        }

        if((fromFeatureVector.size() > 0) && (toFeatureVector.size() == 0))
        {
            for(int i = 0; i < fromFeatureVector.size(); i++)
            {
                EntityItem ei = (EntityItem) fromFeatureVector.get(i);
                hwfccat = "UNKNOWN HWFCCAT(FEATURE)";
                key2 = hwfccat + "<:>" + key2;
                key2 = key2 + "<:>" + "";
                key2 = key2 + "<:>" + getAttributeValue(ei, "MKTGNAME", "", "", false);
                key2 = key2 + "<:>" + returnedParts;
                key2 = key2 + "<:>" + "";
                addToTreeMap(key2, geo, charges_Feature_Conversions_TM);
            }
            log("In 3");
        }

        if((fromFeatureVector.size() > 0) && (toFeatureVector.size() > 0))
        {
            for(int i = 0; i < fromFeatureVector.size(); i++)
            {
                EntityItem fei = (EntityItem) fromFeatureVector.get(i);
                String fromMktgname = getAttributeValue(fei, "MKTGNAME", "", "", false);

                for(int j = 0; j < toFeatureVector.size(); j++)
                {
                    EntityItem tei = (EntityItem) toFeatureVector.get(j);
                    hwfccat = getAttributeValue(tei, "HWFCCAT", "", "UNKNOWN HWFCCAT(FEATURE)", false);
                    key2 = hwfccat + "<:>" + key2;
                    key2 = key2 + "<:>" + getAttributeValue(tei, "MKTGNAME", "", "", false);
                    key2 = key2 + "<:>" + fromMktgname;
                    key2 = key2 + "<:>" + returnedParts;
                    key2 = key2 + "<:>" + getAttributeValue(tei, "EDITORNOTE", "", "", false).trim();
                    addToTreeMap(key2, geo, charges_Feature_Conversions_TM);
                }//end of for(int j = 0; i < toFeatureVector.size(); j++)
            }//end of for(int i = 0; i < fromFeatureVector.size(); i++)
            log("In 4");
        }//end of if((fromFeatureVector.size() > 0) && (toFeatureVector.size() > 0))
    }

    /********************************************************************************
    *
    *
    * @param machType String
    * @param model String
    * @param featureCode String
    * @returns Vector
    */
    private Vector getFeatureEntities(String machType, String model, String featureCode)
    {
        Vector featureVct = new Vector();
        Profile profile = list.getProfile();

        try
        {
            EntityList eList;
            EntityGroup feg;
            String actionName="SRDFEATURE";
            RowSelectableTable searchTable;
            int row;

            SearchActionItem sai = new SearchActionItem(null, dbCurrent, profile, actionName);
            sai.setCheckLimit(false); //allow more than 2500
            searchTable = sai.getDynaSearchTable(dbCurrent);

            row = searchTable.getRowIndex("FEATURE:FEATURECODE");
            if(row < 0)
            {
                row = searchTable.getRowIndex("FEATURE:FEATURECODE:C");
            }
            if(row < 0)
            {
                row = searchTable.getRowIndex("FEATURE:FEATURECODE:R");
            }
            if(row != -1 && featureCode.length()>0)
            {
                searchTable.put(row, 1, featureCode);
            }

            searchTable.commit(dbCurrent);

            eList = sai.executeAction(dbCurrent, profile);

            feg = eList.getEntityGroup("FEATURE");
            //EntityGroup peg = eList.getParentEntityGroup();

            if(null == feg)
            {
                return featureVct;
            }

            if(feg.getEntityItemCount() > 0)
            {
                Vector tmpVct = new Vector();
                //mw has a limit of 50 for entity ids.. must break into groups of 50 or less
                if(feg.getEntityItemCount() > MW_VENTITY_LIMIT)
                {
                    int numGrps = feg.getEntityItemCount() / MW_VENTITY_LIMIT;
                    int numUsed = 0;
                    for(int i = 0; i <= numGrps; i++)
                    {
                        tmpVct.clear();
                        for(int x = 0; x <MW_VENTITY_LIMIT; x++)
                        {
                            EntityItem entityItem;
                            if(numUsed == feg.getEntityItemCount())
                            {
                                break;
                            }

                            entityItem = feg.getEntityItem(numUsed++);
                            //if(featureVector.contains(entityItem.getKey()))
                            tmpVct.addElement(entityItem);
                        }
                        if(tmpVct.size() > 0) // could be 0 if num entities is multiple of limit
                        {
                            getFeatureEntities(tmpVct, featureVct, machType, model);
                        }
                    }//end of for (int i = 0; i <= numGrps; i++)
                }//end of if(feg.getEntityItemCount()> MW_VENTITY_LIMIT)
                else
                {
                    // build vector of EntityItems
                    for(int ie = 0; ie < feg.getEntityItemCount(); ie++)
                    {
                        EntityItem entityItem = feg.getEntityItem(ie);
                        //if(featureVector.contains(entityItem.getKey()))
                        tmpVct.addElement(entityItem);
                    }

                    getFeatureEntities(tmpVct, featureVct, machType, model);
                }//end of else

                tmpVct.clear();
                tmpVct= null;
            }//end of if(feg.getEntityItemCount() >0)
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log(e.toString());
        }

        return featureVct;
    }

    /********************************************************************************
    *
    *
    * @param tmpVct Vector
    * @param featureVct Vector
    * @param machType String
    * @param model String
    */
    private void getFeatureEntities(Vector tmpVct, Vector featureVct, String machType, String model)
        throws Exception
    {
        EntityList aList;
        String extractName = "EXRPT3FM";
        // build array of EntityItems
        EntityItem[] eiArray = new EntityItem[tmpVct.size()];

        log("In getFeatureEntities(Vector tmpVct, Vector featureVct, String machType, String model)");
        for(int ie = 0; ie < tmpVct.size(); ie++)
        {
            eiArray[ie] = (EntityItem)tmpVct.elementAt(ie);
        }

        aList = null;
        if(tmpVct.size() > 0)
        {
            // pull extracts
            EntityGroup peg;
            Profile profile = list.getProfile();
            aList = dbCurrent.getEntityList(profile,
                new ExtractActionItem(null, dbCurrent, profile, extractName),
                eiArray);

            log("I am here 1");
            if(aList.getEntityGroupCount() == 0) // ERROR meta not found for extract
            {
                log("Extract was not found for " + extractName);
            }

            peg = aList.getParentEntityGroup();

            for(int e = 0; e < peg.getEntityItemCount(); e++)
            {
                EntityItem entityItem = peg.getEntityItem(e);
                log("I am here 2");
                if(checkModel(entityItem, model, machType))
                {
                    featureVct.addElement(entityItem);
                }
            }//end of for(int e = 0; e <peg.getEntityItemCount(); e++)
        }//end of if(tmpVct.size() > 0)

        //release memory
        for (int ie = 0; ie < eiArray.length; ie++)
        {
            eiArray[ie] = null;
        }
        //aList.dereference();
    }

    /********************************************************************************
    *
    *
    * @param ei EntityItem
    * @param model String
    * @param machType String
    */
    private boolean checkModel(EntityItem ei, String model, String machType)
    {
        boolean match = false;

        Vector modelV = getAllLinkedEntities(ei, "PRODSTRUCT", "MODEL");

        for(int i = 0; i < modelV.size(); i++)
        {
            String m;
            EntityItem modelItem = (EntityItem) modelV.get(i);
            String mt = getAttributeFlagValue(modelItem, "MACHTYPEATR");
            if(null == mt)
            {
                mt = " ";
            }

            m = getAttributeValue(modelItem, "MODELATR", "", "", false);

            if(machType.equals(mt) && model.equals(m))
            {
                match = true;
                break;
            }
        }

        return match;
    }

    /********************************************************************************
    *
    *
    */
    private void charges_NewModels()
    {
        //This is for pSeries only
        Iterator srcItr;
        Hashtable tempHT = new Hashtable();
        /*** Need to get flag codes for COFCAT, COFSUBCAT, COFGRP. As of today 8/17/04 flag codes are not defined yet ***/
        tempHT.put("COFCAT", "100");    //Hardware
        //if(!brand.equals(XSERIES))
        //{
        //    tempHT.put("COFSUBCAT", "126"); //System
        //}
        tempHT.put("COFGRP", "150");    //Base

        //Path 1: ANNOUNCEMENT --> ANNAVAILA --> AVAIL --> MODELAVAIL --> MODEL
        srcItr = availVector.iterator();
        while(srcItr.hasNext())
        {
            EntityItem availItem = (EntityItem) srcItr.next();
            Vector modelVector = getAllLinkedEntities(availItem, "MODELAVAIL", "MODEL");
            modelVector = getEntitiesWithMatchedAttr(modelVector, tempHT);
            for(int i = 0; i < modelVector.size(); i++)
            {
                EntityItem modelItem = (EntityItem) modelVector.get(i);

                populate_Charges_NewModels_TM(availItem, modelItem);
            }//end of for(int i = 0; i < modelVector.size(); i++)
        }//end of while(srcItr.hasNext())

      //Per Alan 11/29/2004, no path 2
      //Path 2: ANNOUNCEMENT --> ANNAVAILA --> AVAIL --> OOFAVAIL --> PRODSTRUCT --> MODEL
//      srcItr = availVector.iterator();
//      while(srcItr.hasNext())
//      {
//         EntityItem availItem = (EntityItem) srcItr.next();
//         Vector prodstructVector = getAllLinkedEntities(availItem, "OOFAVAIL", "PRODSTRUCT");
//         for(int i = 0; i < prodstructVector.size(); i++)
//         {
//            EntityItem prodstructItem = (EntityItem) prodstructVector.get(i);

//            for(int j = 0; j < prodstructItem.getDownLinkCount(); j++)
//            {
//               EANEntity entity = prodstructItem.getDownLink(j);
//               if(entity.getEntityType().equals("MODEL"))
//               {
//                  if(isEntityWithMatchedAttr((EntityItem) entity, tempHT))
//                  {
//                     EntityItem modelItem = (EntityItem) entity;

//                     populate_Charges_NewModels_TM(availItem, modelItem);
//                  }
//               }//end of if(entity.getEntityType().equals("MODEL"))
//            }//end of for(int j = 0; j < prodstructItem.getDownLinkCount(); i++)
//         }//end of for(int i = 0; i < prodstructVector.size(); i++)
//      }//end of while(srcItr.hasNext())
        tempHT.clear();
        tempHT = null;
    }

    /********************************************************************************
    *
    *
    * @param availItem EntityItem
    * @param modelItem EntityItem
    */
    private void populate_Charges_NewModels_TM(EntityItem availItem, EntityItem modelItem)
    {
        /*
           Key = MACHTYPEATR(MODEL) +
                 MODELATR(MODEL) +
                 MKTGNAME(MODEL) +
                 Initial or MES or Both from MODELORDERCODE(MODEL) +
                 Y or N/A or N from INSTALL(MODEL)
        */
        String key;
        String mt;
        String model;
        String geo;
        String modelItemKey = modelItem.getKey();
        updateGeoHT(availItem, modelItemKey);
        key = getAttributeFlagValue(modelItem, "MACHTYPEATR");
        if(null == key)
        {
            key = " ";
        }
        key = key.trim();
        key = setString(key, 4);
        mt = key;
        model = getAttributeValue(modelItem, "MODELATR", "", "000", false);
        model = model.trim();
        model = setString(model, 3);
        key = key + "<:>" + model;

        if(FORMAT1 == format)
        {
            String invName = getAttributeValue(modelItem, "INVNAME", "", "", false);
            invName = invName.trim();
            if(invName.equals(""))
            {
                invName =getAttributeValue(modelItem, "INTERNALNAME", "", "", false);
                if(invName.length() > I_28)
                {
                    invName = invName.substring(0, I_28);
                }
                invName = invName.trim();
                invName = invName.toUpperCase();
                //if(invName.equals(""))
                //   featureMatrixError.add("20<:>" + "Charges<:>" + mt + "<:>" + model + "<:>MODEL<:>Price File Name");
            }

            invName = setString(invName, I_28);
            key = key + "<:>" + invName;
        }
        else if(FORMAT2 == format || FORMAT3 == format)
        {
            String mktgName = getAttributeValue(modelItem, "MKTGNAME", "", "", false);
            mktgName = mktgName.trim();
            if(mktgName.equals(""))
            {
                featureMatrixError.add("20<:>" + "Charges<:>" + mt + "<:>" + model + "<:>MODEL<:>Marketing Name");
            }
            key = key + "<:>" + mktgName;
        }

        //Check for MODELORDERCODE = Initial, MES or Both
        if(isSelected(modelItem, "MODELORDERCODE", "100"))
        {
            key = key + "<:>" + "Initial";
        }
        else if(isSelected(modelItem, "ORDERCODE", "110"))
        {
            key = key + "<:>" + "MES    ";
        }
        else if(isSelected(modelItem, "ORDERCODE", "120"))
        {
            key = key + "<:>" + "Both   ";
        }
        else
        {
            key = key + "<:>" + "       ";
        }

        if(getAttributeValue(modelItem, "MODELORDERCODE", "", "", false).equals(""))
        {
            featureMatrixError.add("20<:>" + "Charges<:>" + mt + "<:>" + model + "<:>MODEL<:>Model Order Code");
        }

        //Check for INSTALL = CIF(5671), INSTALL = CE(5672), INSTALL = 5673(N/A)
        if(isSelected(modelItem, "INSTALL", "5671"))
        {
            key = key + "<:>" + "Yes";
            productNumber_NewModels_HT.put(model, "Yes");
        }
        else if(isSelected(modelItem, "INSTALL", "5672"))
        {
            key = key + "<:>" + "No ";
            productNumber_NewModels_HT.put(model, "No ");
        }
        else if(isSelected(modelItem, "INSTALL", "5673"))
        {
            key = key + "<:>" + "N/A";
            productNumber_NewModels_HT.put(model, "N/A");
        }
        else
        {
            key = key + "<:>" + "   ";
            productNumber_NewModels_HT.put(model, "   ");
        }

        if(getAttributeValue(modelItem, "INSTALL", "", "", false).equals(""))
        {
            featureMatrixError.add("20<:>" + "Charges<:>" + mt + "<:>" + model + "<:>MODEL<:>Customer Install");
        }

        //key = key + "<:>" + modelItemKey;
        geo = getGeo(modelItemKey);

        addToTreeMap(key, geo, charges_NewModels_TM);
    }

    /********************************************************************************
    *
    *
    */
    private void charges_FeatureCodes1()
    {
        /*
           Key = FEATURECODE(FEATURE) +
                 MACHTYPEATR(MODEL) +
                 MODELATR(MODEL) +
                 //INVNAME(PRODSTRUCT) or MKTGNAME(PRODSTRUCT) +
                 INVNAME(PRODSTRUCT) or INVNAME(FEATURE) or MKTGNAME(PRODSTRUCT) or MKTGNAME(FEATURE) +
                 Both or MES or Initial or Support from ORDERCODE(PRODSTRUCT)+
                 Y or N/A or N from INSTALL(PRODSTRUCT) +
                 Y or N from RETURNEDPARTS(PRODSTRUCT) +
                 EDITORNOTE(FEATURE) +
                 $XXXX or NC +
                 $XXX or blank +
                 $XXX or blank +
                 $XXX or blank
        */
        Hashtable tempHT = new Hashtable();

        String mt = "";

        Iterator srcItr = availVector.iterator();
        while(srcItr.hasNext())
        {
            EntityItem availItem = (EntityItem) srcItr.next();
            Vector prodstructVector = getAllLinkedEntities(availItem, "OOFAVAIL", "PRODSTRUCT");
            for(int i = 0; i < prodstructVector.size(); i++)
            {
                EANEntity entityDownLink;
                EntityItem prodstructItem = (EntityItem) prodstructVector.get(i);
                String prodstructItemKey = prodstructItem.getKey();
                updateGeoHT(availItem, prodstructItemKey);

                entityDownLink = getDownLinkEntityItem(prodstructItem, "MODEL");
                if(null != entityDownLink)
                {
                    /*** Need to get flag codes for COFCAT, COFSUBCAT, COFGRP. As of today 8/17/04 flag codes are not defined yet ***/
                    tempHT.clear();
                    tempHT.put("COFCAT", "100");    //Hardware
                    //if(!brand.equals(XSERIES))
                    //{
                    //    tempHT.put("COFSUBCAT", "126"); //System
                    //}
                    tempHT.put("COFGRP", "150");    //Base
                    if(isEntityWithMatchedAttr((EntityItem) entityDownLink, tempHT))
                    {
                        EntityItem modelItem = (EntityItem) entityDownLink;
                        EANEntity entityUpLink = getUpLinkEntityItem(prodstructItem, "FEATURE");
                        if(null != entityUpLink)
                        {
                            String key;
                            String model;
                            String invName;
                            String geo;
                            String featureFirstAnnDate;
                            EntityItem featureItem = (EntityItem) entityUpLink;
                            String fc = getAttributeValue(featureItem, "FEATURECODE", "", "", false);
                            fc = fc.trim();
                            fc = setString(fc, 4);

                            key = fc;

                            mt = getAttributeFlagValue(modelItem, "MACHTYPEATR");
                            if(null == mt)
                            {
                                mt = " ";
                            }
                            mt = mt.trim();
                            mt = setString(mt, 4);

                            key = key + "<:>" + mt;
                            model = getAttributeValue(modelItem, "MODELATR", "", "000", false);
                            model = model.trim();
                            if(model.equals(""))
                            {
                                featureMatrixError.add("20<:>" + "Charges<:>" + mt + "<:>" + model + "<:>MODEL" + "<:>Machine Model");
                            }

                            model = setString(model, 3);
                            key = key + "<:>" + model;
                            if(fc.equals("    "))
                            {
                                featureMatrixError.add("20<:>" + "Charges<:>" + mt + "<:>" + model + "<:>FEATURE" + "<:>Feature Code");
                            }

                            invName = getAttributeValue(prodstructItem, "INVNAME", "", "", false);
                            invName = invName.trim();
                            if(invName.equals(""))
                            {
                                invName = getAttributeValue(featureItem, "INVNAME", "", "", false);
                                invName = invName.trim();
                                if(invName.equals(""))
                                {
                                    invName = getAttributeValue(featureItem, "COMNAME", "", "", false);
                                    if(invName.length() > I_28)
                                    {
                                        invName = invName.substring(0, I_28);
                                    }
                                    invName = invName.trim();
                                    invName = invName.toUpperCase();
                                    //if(invName.equals(""))
                                    //{
                                    //   featureMatrixError.add("20<:>" + "Charges<:>" + mt + "<:>" + model + "<:>PRODSTRUCT " + getAttributeValue( prodstructItem, "COMNAME", "", "", false) +  "<:>Price File Name");
                                    //   featureMatrixError.add("20<:>" + "Charges<:>" + mt + "<:>" + model + "<:>FEATURE " + fc +  "<:>Price File Name");
                                    //}
                                }
                            }
                            invName = setString(invName, I_28);
                            key = key + "<:>" + invName;

                            //Check for ORDERCODE = B, M, P, S
                            if(isSelected(prodstructItem, "ORDERCODE", "5955"))
                            {
                                key = key + "<:>" + "Both   ";
                            }
                            else if(isSelected(prodstructItem, "ORDERCODE", "5956"))
                            {
                                key = key + "<:>" + "MES    ";
                            }
                            else if(isSelected(prodstructItem, "ORDERCODE", "5957"))
                            {
                                key = key + "<:>" + "Initial";
                            }
                            else if(isSelected(prodstructItem, "ORDERCODE", "5958"))
                            {
                                key = key + "<:>" + "Support";
                            }
                            else
                            {
                                key = key + "<:>" + "       ";
                            }

                            if(getAttributeValue(prodstructItem, "ORDERCODE", "", "", false).equals(""))
                            {
                                featureMatrixError.add("20<:>" + "Charges<:>" + mt + "<:>" + model + "<:>PRODSTRUCT " + getAttributeValue(prodstructItem, "COMNAME", "", "", false) + "<:>Original Order Code");
                            }

                            //Check for INSTALL = CIF(5671), INSTALL = CE(5672), INSTALL = 5673(N/A)
                            if(isSelected(prodstructItem, "INSTALL", "5671"))
                            {
                                key = key + "<:>" + "Yes";
                            }
                            else if(isSelected(prodstructItem, "INSTALL", "5672"))
                            {
                                key = key + "<:>" + "No ";
                            }
                            else if(isSelected(prodstructItem, "INSTALL", "5673"))
                            {
                                key = key + "<:>" + "N/A";
                            }
                            else
                            {
                                key = key + "<:>" + "   ";
                            }

                            if(getAttributeValue(prodstructItem, "INSTALL", "", "", false).equals(""))
                            {
                                featureMatrixError.add("20<:>" + "Charges<:>" + mt + "<:>" + model + "<:>PRODSTRUCT " + getAttributeValue(prodstructItem, "COMNAME", "", "", false) + "<:>Customer Install");
                            }

                            if(isSelected(prodstructItem, "RETURNEDPARTS", "5100"))
                            {
                                key = key + "<:>" + "Yes";
                            }
                            else if(isSelected(prodstructItem, "RETURNEDPARTS", "5101"))
                            {
                                key = key + "<:>" + "No ";
                            }
                            else
                            {
                                key = key + "<:>" + "   ";
                            }

                            if(getAttributeValue(prodstructItem, "RETURNEDPARTS", "", "", false).equals(""))
                            {
                                featureMatrixError.add("20<:>" + "Charges<:>" + mt + "<:>" + model + "<:>PRODSTRUCT " + getAttributeValue(prodstructItem, "COMNAME", "", "", false) + "<:>Returned Parts MES");
                            }

                            key = key + "<:>" + getAttributeValue(featureItem, "EDITORNOTE", "", "", false).trim();

                            geo = getGeo(prodstructItemKey);
                            featureFirstAnnDate = getAttributeValue(featureItem, "FIRSTANNDATE", "", "", false);

                            if(getAttributeValue(featureItem, "PRICEDFEATURE", "", "", false).equals(""))
                            {
                                featureMatrixError.add("20<:>" + "Charges<:>" + mt + "<:>" + model + "<:>FEATURE " + fc +  "<:>Priced Feature Indicator");
                            }
                            if(getAttributeValue(featureItem, "ZEROPRICE", "", "", false).equals(""))
                            {
                                featureMatrixError.add("20<:>" + "Charges<:>" + mt + "<:>" + model + "<:>FEATURE " + fc +  "<:>Zero Priced Indicator");
                            }

                            if(getAttributeValue(featureItem, "PRICEDFEATURE", "", "", false).equals(""))
                            {
                                //Set blank
                                key = key + "<:>        " + "<:>    " + "<:>    " + "<:>    ";
                            }
                            else
                            {
                                //if(getAttributeValue(featureItem, "ZEROPRICE", "", "", false).equals(""))
                                //{
                                //Set blank
                                //   key = key + "<:>        ";
                                //}
                                //else
                                //{
                                //Per Alan Crudo 2/15/2005
                                //if PRICEDFEATURE = YES and ZEROPRICE = NO then show XXXX.XX
                                //if PRICEDFEATURE = YES and ZEROPRICE = YES then show XXXX.XX
                                //if PRICEDFEATURE = NO and ZEROPRICE = NO then show NC
                                //if PRICEDFEATURE = NO and ZEROPRICE = YES then show NC
                                //if PRICEDFEATURE = NO and ZEROPRICE = Blank then show NC
                                //Per Alan Crudo
                                //if PRICEDFEATURE = YES and ZEROPRICE = NO then show $XXXX
                                //if PRICEDFEATURE = YES and ZEROPRICE = YES then show $0
                                //if PRICEDFEATURE = NO and ZEROPRICE = NO then show NC
                                //if PRICEDFEATURE = NO and ZEROPRICE = YES then show $0
                                tempHT.clear();
                                tempHT.put("PRICEDFEATURE", "100");  //YES
                                //tempHT.put("ZEROPRICE", "120");      //NO
                                if(isEntityWithMatchedAttr(featureItem, tempHT))
                                {
                                    //According to Alan Crudo 5/4/2005
                                    //Eliminate the split between "New Features" and "Existing Features".  Put all features in sequence under the heading
                                    //Features and Specify Codes" (eliminate the "No-Charge" in headings, and the separate "New Features" and "Existing  Features" headings.
                                    //One suggested improvement. Blank out the $XXXX under the headings, "MMMC", "ESA 24X7", and "ESA 9X5".
                                    //There are only a few features to which maintenance applies, and we can more easily add them in.
                                    //key = key + "<:> $XXXX  " + "<:>$XXX" + "<:>$XXX" + "<:>$XXX";
                                    key = key + "<:> $XXXX  " + "<:>    " + "<:>    " + "<:>    ";
                                }
                                tempHT.clear();
                                tempHT.put("PRICEDFEATURE", "120");  //NO
                                //tempHT.put("ZEROPRICE", "120");      //NO
                                if(isEntityWithMatchedAttr(featureItem, tempHT))
                                {
                                    key = key + "<:>    NC  " + "<:>    " + "<:>    " + "<:>    ";
                                }
                                //}//end of else
                            }//end of else

                            if(annDate.equals(featureFirstAnnDate))
                            {
                                addToTreeMap(key, geo, charges_NewFC_TM);
                            }
                            else
                            {
                                if(isNewFeature(featureItem))
                                {
                                    addToTreeMap(key, geo, charges_NewFC_TM);
                                }//end of if(isNewFeature(featureItem))
                                else
                                {
                                    addToTreeMap(key, geo, charges_ExistingFC_TM);
                                }//end of else
                            }//end of else
                        }//end of if(null != entityUpLink)
                    }//end of if(isEntityWithMatchedAttr((EntityItem) entityDownLink, tempHT))
                }//end of if(null != entityDownLink)
            }//end of for(int i = 0; i < prodstructVector.size(); i++)
        }//end of while(srcItr.hasNext())
    }

    /********************************************************************************
    *
    *
    */
    private void charges_FeatureCodes2()
    {
        /*
           Key = MACHTYPEATR(MODEL) +
                 FEATURECODE(FEATURE) +
                 MODELATR(MODEL) +
                 //INVNAME(PRODSTRUCT) or MKTGNAME(PRODSTRUCT) +
                 INVNAME(PRODSTRUCT) or INVNAME(FEATURE) or MKTGNAME(PRODSTRUCT) or MKTGNAME(FEATURE) +
                 Both or MES or Initial or Support from ORDERCODE(PRODSTRUCT)+
                 Y or N/A or N from INSTALL(PRODSTRUCT) +
                 Y or N from RETURNEDPARTS(PRODSTRUCT) +
                 EDITORNOTE(FEATURE) +
                 XXXX.XX or NC
                 MKTGNAME(MODEL)
        */
        Hashtable tempHT = new Hashtable();
        String mt = "";
        boolean newFC = true;
        boolean newModel = true;

        Iterator srcItr = availVector.iterator();
        while(srcItr.hasNext())
        {
            EntityItem availItem = (EntityItem) srcItr.next();
            Vector prodstructVector = getAllLinkedEntities(availItem, "OOFAVAIL", "PRODSTRUCT");
            for(int i = 0; i < prodstructVector.size(); i++)
            {
                EANEntity entityDownLink;
                EntityItem prodstructItem = (EntityItem) prodstructVector.get(i);
                String prodstructItemKey = prodstructItem.getKey();
                updateGeoHT(availItem, prodstructItemKey);

                entityDownLink = getDownLinkEntityItem(prodstructItem, "MODEL");
                if(null != entityDownLink)
                {
                    /*** Need to get flag codes for COFCAT, COFSUBCAT, COFGRP. As of today 8/17/04 flag codes are not defined yet ***/
                    tempHT.clear();
                    tempHT.put("COFCAT", "100");    //Hardware
                    //if(!brand.equals(XSERIES))
                    //{
                    //    tempHT.put("COFSUBCAT", "126"); //System
                    //}
                    tempHT.put("COFGRP", "150");    //Base
                    if(isEntityWithMatchedAttr((EntityItem) entityDownLink, tempHT))
                    {
                        EntityItem modelItem = (EntityItem) entityDownLink;
                        EANEntity entityUpLink = getUpLinkEntityItem(prodstructItem, "FEATURE");
                        if(null != entityUpLink)
                        {
                            String fc;
                            String model;
                            String featureFirstAnnDate;
                            String mktgName;
                            String geo;
                            EntityItem featureItem = (EntityItem) entityUpLink;
                            String key = getAttributeFlagValue(modelItem, "MACHTYPEATR");
                            if(null == key)
                            {
                                key = " ";
                            }
                            key = key.trim();
                            key = setString(key, 4);
                            mt = key;
                            fc = getAttributeValue(featureItem, "FEATURECODE", "", "", false);
                            fc = fc.trim();

                            fc = setString(fc, 4);
                            key = key + "<:>" + fc;
                            model = getAttributeValue(modelItem, "MODELATR", "", "000", false);
                            model = model.trim();
                            if(model.equals(""))
                            {
                                featureMatrixError.add("20<:>" + "Charges<:>" + mt + "<:>" + model + "<:>MODEL" + "<:>Machine Model");
                            }

                            model = setString(model, 3);
                            key = key + "<:>" + model;
                            if(fc.equals("    "))
                            {
                                featureMatrixError.add("20<:>" + "Charges<:>" + mt + "<:>" + model + "<:>FEATURE" + "<:>Feature Code");
                            }

                            newModel =  productNumber_NewModels_HT.containsKey(model);

                            featureFirstAnnDate = getAttributeValue(featureItem, "FIRSTANNDATE", "", "", false);
                            if(annDate.equals(featureFirstAnnDate))
                            {
                                newFC = true;
                            }
                            else
                            {
                                if(isNewFeature(featureItem))
                                {
                                    newFC = true;
                                }
                                else
                                {
                                    newFC = false;
                                }
                            }

                            mktgName = getAttributeValue(prodstructItem, "MKTGNAME", "", "", false);
                            mktgName = mktgName.trim();
                            if(mktgName.equals(""))
                            {
                                mktgName = getAttributeValue(featureItem, "MKTGNAME", "", "", false);
                                mktgName = mktgName.trim();
                                if(mktgName.equals(""))
                                {
                                    featureMatrixError.add("20<:>" + "Charges<:>" + mt + "<:>" + model + "<:>PRODSTRUCT " + getAttributeValue(prodstructItem, "COMNAME", "", "", false) +  "<:>Marketing Name");
                                    featureMatrixError.add("20<:>" + "Charges<:>" + mt + "<:>" + model + "<:>FEATURE " + fc +  "<:>Marketing Name");
                                }
                            }
                            key = key + "<:>" + mktgName;

                            //Check for ORDERCODE = B, M, P, S
                            if(isSelected(prodstructItem, "ORDERCODE", "5955"))
                            {
                                key = key + "<:>" + "Both   ";
                            }
                            else if(isSelected(prodstructItem, "ORDERCODE", "5956"))
                            {
                                key = key + "<:>" + "MES    ";
                            }
                            else if(isSelected(prodstructItem, "ORDERCODE", "5957"))
                            {
                                key = key + "<:>" + "Initial";
                            }
                            else if(isSelected(prodstructItem, "ORDERCODE", "5958"))
                            {
                                key = key + "<:>" + "Support";
                            }
                            else
                            {
                                key = key + "<:>" + "       ";
                            }

                            if(getAttributeValue(prodstructItem, "ORDERCODE", "", "", false).equals(""))
                            {
                                featureMatrixError.add("20<:>" + "Charges<:>" + mt + "<:>" + model + "<:>PRODSTRUCT " + getAttributeValue(prodstructItem, "COMNAME", "", "", false) + "<:>Original Order Code");
                            }

                            //Check for INSTALL = CIF(5671), INSTALL = CE(5672), INSTALL = 5673(N/A)
                            if(isSelected(prodstructItem, "INSTALL", "5671"))
                            {
                                key = key + "<:>" + "Yes";
                            }
                            else if(isSelected(prodstructItem, "INSTALL", "5672"))
                            {
                                key = key + "<:>" + "No ";
                            }
                            else if(isSelected(prodstructItem, "INSTALL", "5673"))
                            {
                                key = key + "<:>" + "N/A";
                            }
                            else
                            {
                                key = key + "<:>" + "   ";
                            }

                            if(getAttributeValue(prodstructItem, "INSTALL", "", "", false).equals(""))
                            {
                                featureMatrixError.add("20<:>" + "Charges<:>" + mt + "<:>" + model + "<:>PRODSTRUCT " + getAttributeValue(prodstructItem, "COMNAME", "", "", false) + "<:>Customer Install");
                            }

                            if(isSelected(prodstructItem, "RETURNEDPARTS", "5100"))
                            {
                                key = key + "<:>" + "Yes";
                            }
                            else if(isSelected(prodstructItem, "RETURNEDPARTS", "5101"))
                            {
                                key = key + "<:>" + "No ";
                            }
                            else
                            {
                                key = key + "<:>" + "   ";
                            }

                            if(getAttributeValue(prodstructItem, "RETURNEDPARTS", "", "", false).equals(""))
                            {
                                featureMatrixError.add("20<:>" + "Charges<:>" + mt + "<:>" + model + "<:>PRODSTRUCT " + getAttributeValue(prodstructItem, "COMNAME", "", "", false) + "<:>Returned Parts MES");
                            }

                            key = key + "<:>" + getAttributeValue(featureItem, "EDITORNOTE", "", "", false).trim();

                            geo = getGeo(prodstructItemKey);

                            if(getAttributeValue(featureItem, "PRICEDFEATURE", "", "", false).equals(""))
                            {
                                featureMatrixError.add("20<:>" + "Charges<:>" + mt + "<:>" + model + "<:>FEATURE " + fc +  "<:>Priced Feature Indicator");
                            }
                            if(getAttributeValue(featureItem, "ZEROPRICE", "", "", false).equals(""))
                            {
                                featureMatrixError.add("20<:>" + "Charges<:>" + mt + "<:>" + model + "<:>FEATURE " + fc +  "<:>Zero Priced Indicator");
                            }

                            if(getAttributeValue(featureItem, "PRICEDFEATURE", "", "", false).equals(""))
                            {
                                //Set blank
                                key = key + "<:>       ";
                            }
                            else
                            {
                                //if(getAttributeValue(featureItem, "ZEROPRICE", "", "", false).equals(""))
                                //{
                                //Set blank
                                //   key = key + "<:>       ";
                                //}
                                //else
                                //{
                                //Per Alan Crudo 2/15/2005
                                //if PRICEDFEATURE = YES and ZEROPRICE = NO then show XXXX.XX
                                //if PRICEDFEATURE = YES and ZEROPRICE = YES then show XXXX.XX
                                //if PRICEDFEATURE = NO and ZEROPRICE = NO then show NC
                                //if PRICEDFEATURE = NO and ZEROPRICE = YES then show NC
                                //if PRICEDFEATURE = NO and ZEROPRICE = Blank then show NC
                                //Per Alan Crudo
                                //if PRICEDFEATURE = YES and ZEROPRICE = NO then show XXXX.XX
                                //if PRICEDFEATURE = YES and ZEROPRICE = YES then show 0
                                //if PRICEDFEATURE = NO and ZEROPRICE = NO then show NC
                                //if PRICEDFEATURE = NO and ZEROPRICE = YES then show 0
                                tempHT.clear();
                                tempHT.put("PRICEDFEATURE", "100");  //YES
                                //tempHT.put("ZEROPRICE", "120");      //NO
                                if(isEntityWithMatchedAttr(featureItem, tempHT))
                                {
                                    key = key + "<:>XXXX.XX";
                                }
                                tempHT.clear();
                                tempHT.put("PRICEDFEATURE", "120");  //NO
                                //tempHT.put("ZEROPRICE", "120");      //NO
                                if(isEntityWithMatchedAttr(featureItem, tempHT))
                                {
                                    key = key + "<:>     NC";
                                }
                                //}//end of else
                            }//end of else

                            mktgName = getAttributeValue(modelItem, "MKTGNAME", "", "", false);
                            mktgName = mktgName.trim();
                            if(mktgName.equals(""))
                            {
                                featureMatrixError.add("20<:>" + "Charges<:>" + mt + "<:>" + model + "<:>MODEL<:>Marketing Name");
                            }
                            key = key + "<:>" + mktgName;

                            if(newModel && newFC)
                            {
                                addToTreeMap(key, geo, charges_NewModels_NewFC_TM);
                            }
                            else if(newModel && !newFC)
                            {
                                addToTreeMap(key, geo, charges_NewModels_ExistingFC_TM);
                            }
                            else if(!newModel && newFC)
                            {
                                addToTreeMap(key, geo, charges_ExistingModels_NewFC_TM);
                            }
                            else if(!newModel && !newFC)
                            {
                                addToTreeMap(key, geo, charges_ExistingModels_ExistingFC_TM);
                            }
                        }//end of if(null != entityUpLink)
                    }//end of if(isEntityWithMatchedAttr((EntityItem) entityDownLink, tempHT))
                }//end of if(null != entityDownLink)
            }//end of for(int i = 0; i < prodstructVector.size(); i++)
        }//end of while(srcItr.hasNext())
    }

    /********************************************************************************
    *
    *
    */
    private void salesManual()
    {
        Iterator srcItr;
        Hashtable tempHT = new Hashtable();
        /*** Need to get flag codes for COFCAT, COFSUBCAT, COFGRP. As of today 8/17/04 flag codes are not defined yet ***/
        tempHT.put("COFCAT", "100");    //Hardware
        //if(!brand.equals(XSERIES))
        //{
        //    tempHT.put("COFSUBCAT", "126"); //System
        //}
        tempHT.put("COFGRP", "150");    //Base

        //Path: ANNOUNCEMENT --> ANNAVAILA --> AVAIL --> OOFAVAIL --> PRODSTRUCT --> MODEL/FEATURE
        srcItr = availVector.iterator();
        while(srcItr.hasNext())
        {
            EntityItem availItem = (EntityItem) srcItr.next();
            Vector prodstructVector = getAllLinkedEntities(availItem, "OOFAVAIL", "PRODSTRUCT");
            for(int i = 0; i < prodstructVector.size(); i++)
            {
                EntityItem prodstructItem = (EntityItem) prodstructVector.get(i);

                for(int j = 0; j < prodstructItem.getDownLinkCount(); j++)
                {
                    EANEntity entityDownLink = prodstructItem.getDownLink(j);
                    if(entityDownLink.getEntityType().equals("MODEL"))
                    {
                        if(isEntityWithMatchedAttr((EntityItem) entityDownLink, tempHT))
                        {
                            EntityItem modelItem = (EntityItem) entityDownLink;
                            for(int k = 0; k < prodstructItem.getUpLinkCount(); k++)
                            {
                                EANEntity entityUpLink = prodstructItem.getUpLink(k);
                                if(entityUpLink.getEntityType().equals("FEATURE"))
                                {
                                    EntityItem featureItem = (EntityItem) entityUpLink;
//                        log("prodstructItem = " + prodstructItem.getKey());
//                        log("entityDownLink = " + entityDownLink.getKey());
//                        log("entityUpLink = " + entityUpLink.getKey());

                                    populate_SalesManual_TM(availItem, prodstructItem, modelItem, featureItem);
                                }
                            }//end of for(int k = 0; prodstructItem.getUpLinkCount(); k++)
                        }
                    }//end of if(entity.getEntityType().equals("MODEL"))
                }//end of for(int j = 0; j < prodstructItem.getDownLinkCount(); i++)
            }//end of for(int i = 0; i < prodstructVector.size(); i++)
        }//end of while(srcItr.hasNext())
    }

    /********************************************************************************
    *
    *
    * @param availItem EntityItem
    * @param prodstructItem EntityItem
    * @param modelItem EntityItem
    * @param featureItem EntityItem
    */
    private void populate_SalesManual_TM(EntityItem availItem, EntityItem prodstructItem, EntityItem modelItem, EntityItem featureItem)
    {
        /*
           Key = MACHTYPEATR(MODEL) +
                 MODELATR(MODEL) +
                 FEATURECODE(FEATURE) +
                 INVNAME(FEATURE) or MKTGNAME(FEATURE) +
                 DESCRIPTION(FEATURE) is for 3.0a, FCMKTGDESC(FEATURE) is for 3.0b +
                 SYSTEMMIN(PRODSTRUCT) +
                 SYSTEMMAX(PRODSTRUCT) +
                 INITORDERMAX(PRODSTRUCT) +
                 OSLEVELCOMPLEMENT(PRODSTRUCT) +
                 Both or MES or Initial or Support from ORDERCODE(PRODSTRUCT) +
                 Y or N/A or N from INSTALL(PRODSTRUCT) +
                 Y or N from RETURNEDPARTS(PRODSTRUCT) +
                 EDITORNOTE(PRODSTRUCT)
                 EDITORNOTE(FEATURE)
                 ATTPROVIDED(FEATURE)  new for OIM3.0b
                 ATTREQUIRED(FEATURE)  new for OIM3.0b
        */
        String key;
        String mt;
        String model;
        String fc;
        String invName;
        String mktgName;
        String geo;
        String hwfccat;
        Hashtable tempHT = new Hashtable();
        String featureItemKey = featureItem.getKey();
        updateGeoHT(availItem, featureItemKey);

        key = getAttributeFlagValue(modelItem, "MACHTYPEATR");
        if(null == key)
        {
            key = " ";
        }
        key = key.trim();
        key = setString(key, 4);
        mt = key;
        model = getAttributeValue(modelItem, "MODELATR", "", "000", false);
        model = model.trim();
        model = setString(model, 3);
        key = key + "<:>" + model;
        fc = getAttributeValue(featureItem, "FEATURECODE", "", "", false);
        fc = fc.trim();
        if(fc.equals(""))
        {
            featureMatrixError.add("30<:>" + "Sales Manual<:>" + mt + "<:>" + model + "<:>FEATURE<:>Feature Code");
        }
        fc = setString(fc, 4);
        key = key + "<:>" + fc;

        invName = "";
        mktgName = "";
        if(FORMAT1 == format)
        {
            invName = getAttributeValue(featureItem, "INVNAME", "", "", false).trim();
            if(invName.equals(""))
            {
                invName = getAttributeValue(featureItem, "COMNAME", "", "", false);
                if(invName.length() > I_28)
                {
                    invName = invName.substring(0, I_28);
                }
                invName = invName.trim();
                invName = invName.toUpperCase();
                //if(invName.equals(""));
                //   featureMatrixError.add("30<:>" + "Sales Manual<:>" + mt + "<:>" + model + "<:>FEATURE " + fc + "<:>Price File Name");
            }
            key = key + "<:>" + invName;
        }
        else if(FORMAT2 == format || FORMAT3 == format)
        {
            mktgName = getAttributeValue(featureItem, "MKTGNAME", "", "", false).trim();
            if(mktgName.equals(""))
            {
                featureMatrixError.add("30<:>" + "Sales Manual<:>" + mt + "<:>" + model + "<:>FEATURE " + fc + "<:>Marketing Name");
            }
            key = key + "<:>" + mktgName;
        }

        key = key + "<:>" + getAttributeValue(featureItem, "FCMKTGDESC", "", "", false).trim();

        if(getAttributeValue(featureItem, "FCMKTGDESC", "", "", false).trim().equals(""))
        {
            if(FORMAT2 == format || FORMAT3 == format)
            {
                featureMatrixError.add("30<:>" + "Sales Manual<:>" + mt + "<:>" + model + "<:>FEATURE " + fc + "<:>FC Marketing Description");
            }
        }

        key = key + "<:>" + getAttributeValue(prodstructItem, "SYSTEMMIN", "", "", false).trim();

        if(getAttributeValue(prodstructItem, "SYSTEMMIN", "", "", false).trim().equals(""))
        {
            if(FORMAT2 == format || FORMAT3 == format)
            {
                featureMatrixError.add("30<:>" + "Sales Manual<:>" + mt + "<:>" + model + "<:>PRODSTRUCT " + getAttributeValue(prodstructItem, "COMNAME", "", "", false) + "<:>Minimum Required");
            }
        }

        key = key + "<:>" + getAttributeValue(prodstructItem, "SYSTEMMAX", "", "", false).trim();

        if(getAttributeValue(prodstructItem, "SYSTEMMAX", "", "", false).trim().equals(""))
        {
            if(FORMAT2 == format || FORMAT3 == format)
            {
                featureMatrixError.add("30<:>" + "Sales Manual<:>" + mt + "<:>" + model + "<:>PRODSTRUCT " + getAttributeValue(prodstructItem, "COMNAME", "", "", false) + "<:>Maximum Allowed");
            }
        }

        key = key + "<:>" + getAttributeValue(prodstructItem, "INITORDERMAX", "", "", false).trim();

        if(getAttributeValue(prodstructItem, "INITORDERMAX", "", "", false).trim().equals(""))
        {
            if(FORMAT2 == format || FORMAT3 == format)
            {
                featureMatrixError.add("30<:>" + "Sales Manual<:>" + mt + "<:>" + model + "<:>PRODSTRUCT " + getAttributeValue(prodstructItem, "COMNAME", "", "", false) + "<:>Initial Order Maximum");
            }
        }

        key = key + "<:>" + getAttributeValue(prodstructItem, "OSLEVELCOMPLEMENT", ", ", "", false).trim();

        if(getAttributeValue(prodstructItem, "OSLEVELCOMPLEMENT", "", "", false).trim().equals(""))
        {
            featureMatrixError.add("30<:>" + "Sales Manual<:>" + mt + "<:>" + model + "<:>PRODSTRUCT " + getAttributeValue(prodstructItem, "COMNAME", "", "", false) + "<:>OS Level Complement");
        }

        //Check for ORDERCODE = B, M, P, S
        if(isSelected(prodstructItem, "ORDERCODE", "5955"))
        {
            key = key + "<:>" + "Both";
        }
        else if(isSelected(prodstructItem, "ORDERCODE", "5956"))
        {
            key = key + "<:>" + "MES";
        }
        else if(isSelected(prodstructItem, "ORDERCODE", "5957"))
        {
            key = key + "<:>" + "Initial";
        }
        else if(isSelected(prodstructItem, "ORDERCODE", "5958"))
        {
            key = key + "<:>" + "Support";
        }
        else
        {
            key = key + "<:>" + "";
        }

        if(getAttributeValue(prodstructItem, "ORDERCODE", "", "", false).equals(""))
        {
            featureMatrixError.add("30<:>" + "Sales Manual<:>" + mt + "<:>" + model + "<:>PRODSTRUCT " + getAttributeValue(prodstructItem, "COMNAME", "", "", false) + "<:>Original Order Code");
        }

        //Check for INSTALL = CIF(5671), INSTALL = CE(5672), INSTALL = 5673(N/A)
        if(isSelected(prodstructItem, "INSTALL", "5671"))
        {
            key = key + "<:>" + "Yes";
        }
        else if(isSelected(prodstructItem, "INSTALL", "5672"))
        {
            key = key + "<:>" + "No ";
        }
        else if(isSelected(prodstructItem, "INSTALL", "5673"))
        {
            key = key + "<:>" + "N/A";
        }
        else
        {
            key = key + "<:>" + "Does not apply";
        }

        if(getAttributeValue(prodstructItem, "INSTALL", "", "", false).equals(""))
        {
            featureMatrixError.add("30<:>" + "Sales Manual<:>" + mt + "<:>" + model + "<:>PRODSTRUCT " + getAttributeValue(prodstructItem, "COMNAME", "", "", false) + "<:>Customer Install");
        }


        if(isSelected(prodstructItem, "RETURNEDPARTS", "5100"))
        {
            key = key + "<:>" + "Yes";
        }
        else if(isSelected(prodstructItem, "RETURNEDPARTS", "5101"))
        {
            key = key + "<:>" + "No";
        }
        else if(isSelected(prodstructItem, "RETURNEDPARTS", "5102"))
        {
            key = key + "<:>" + "Does not apply";
        }
        else if(isSelected(prodstructItem, "RETURNEDPARTS", "5103"))
        {
            key = key + "<:>" + "Feature conversion only";
        }
        else
        {
            key = key + "<:>" + "";
        }

        if(getAttributeValue(prodstructItem, "RETURNEDPARTS", "", "", false).equals(""))
        {
            if(FORMAT2 == format || FORMAT3 == format)
            {
                featureMatrixError.add("30<:>" + "Sales Manual<:>" + mt + "<:>" + model + "<:>PRODSTRUCT " + getAttributeValue(prodstructItem, "COMNAME", "", "", false) + "<:>Returned Parts MES");
            }
        }

        //key = key + "<:>" + getAttributeValue(featureItem, "EDITORNOTE", "", "", false).trim();
        //Per Alan 3/1/2005. Get EDITORNOTE from PRODSTRUCT
        key = key + "<:>" + getAttributeValue(prodstructItem, "EDITORNOTE", "", "", false).trim();
        key = key + "<:>" + getAttributeValue(featureItem, "EDITORNOTE", "", "", false).trim();
        key = key + "<:>" + getAttributeValue(featureItem, "ATTPROVIDED", "", "", false).trim();
        key = key + "<:>" + getAttributeValue(featureItem, "ATTREQUIRED", "", "", false).trim();

        geo = getGeo(featureItemKey);

        addToTreeMap(key, geo, salesManual_TM);

        key = getAttributeFlagValue(modelItem, "MACHTYPEATR");
        if(null == key)
        {
            key = " ";
        }
        key = key.trim();
        key = setString(key, 4);
        hwfccat = getAttributeValue(featureItem, "HWFCCAT", "", "UNKNOWN HWFCCAT(FEATURE)", false);
        if(hwfccat.equals("UNKNOWN HWFCCAT(FEATURE)"))
        {
            featureMatrixError.add("30<:>" + "Sales Manual<:>" + mt + "<:>" + model + "<:>FEATURE " + fc + "<:>HW Feature Category");
        }
        key = key + "<:>" + hwfccat;
        key = key + "<:>" + fc;
        if(FORMAT1 == format)
        {
            key = key + "<:>" + invName;
        }
        else if(FORMAT2 == format || FORMAT3 == format)
        {
            key = key + "<:>" + mktgName;
        }
        key = key + "<:>" + getAttributeValue(featureItem, "EDITORNOTE", "", "", false).trim();

        //Per Wayne Kehrli:
        //if PRICEDFEATURE is empty then its default value is YES
        tempHT.clear();
        tempHT.put("PRICEDFEATURE", "120");  //No
        tempHT.put("ZEROPRICE", "120");      //NO
        if(isEntityWithMatchedAttr(featureItem, tempHT))
        {
            addToTreeMap(key, geo, salesManualSpecifyFeatures_TM);
        }

        tempHT.clear();
        tempHT.put("PRICEDFEATURE", "120");  //No
        if(!isEntityWithMatchedAttr(featureItem, tempHT))
        {
            //Check for ORDERCODE = B, M, P, S
            if(isSelected(prodstructItem, "ORDERCODE", "5957"))
            {
                addToTreeMap(key, geo, salesManualSpecialFeaturesInitialOrder_TM);
            }
            else if(isSelected(prodstructItem, "ORDERCODE", "5955") ||
                isSelected(prodstructItem, "ORDERCODE", "5956") ||
                isSelected(prodstructItem, "ORDERCODE", "5958"))
            {
                addToTreeMap(key, geo, salesManualSpecialFeaturesOther_TM);
            }
        }
        tempHT.clear();
        tempHT = null;
    }

    /********************************************************************************
    *
    *
    */
    private void supportedDevices()
    {
        Iterator srcItr;
        Hashtable tempHT = new Hashtable();
        /*** Need to get flag codes for COFCAT, COFSUBCAT, COFGRP. As of today 8/17/04 flag codes are not defined yet ***/
        tempHT.put("COFCAT", "100");    //Hardware
        //if(!brand.equals(XSERIES))
        //{
        //    tempHT.put("COFSUBCAT", "126"); //System
        //}
        tempHT.put("COFGRP", "150");    //Base
        //Path 1: ANNOUNCEMENT --> ANNAVAILA --> AVAIL --> MODELAVAIL --> MODEL
        srcItr = availVector.iterator();
        while(srcItr.hasNext())
        {
            EntityItem availItem = (EntityItem) srcItr.next();
            Vector modelVector = getAllLinkedEntities(availItem, "MODELAVAIL", "MODEL");
            modelVector = getEntitiesWithMatchedAttr(modelVector, tempHT);
            for(int i = 0; i < modelVector.size(); i++)
            {
                EntityItem modelItem = (EntityItem) modelVector.get(i);
                Vector modelCGVector = getAllLinkedEntities(modelItem, "MDLCGMDL", "MODELCG");
                Vector modelCGOSVector = getAllLinkedEntities(modelCGVector, "MDLCGMDLCGOS", "MODELCGOS");
                Vector supportedDevicesVector = getAllLinkedEntities(modelCGOSVector, "MDLCGOSMDL", "MODEL");
                populate_SupportedDevices_TM(availItem, modelItem, supportedDevicesVector);
            }//end of for(int i = 0; i < modelVector.size(); i++)
        }//end of while(srcItr.hasNext())

        //Path 2: ANNOUNCEMENT --> ANNAVAILA --> AVAIL --> OOFAVAIL --> PRODSTRUCT --> MODEL
        srcItr = availVector.iterator();
        while(srcItr.hasNext())
        {
            EntityItem availItem = (EntityItem) srcItr.next();
            Vector prodstructVector = getAllLinkedEntities(availItem, "OOFAVAIL", "PRODSTRUCT");
            for(int i = 0; i < prodstructVector.size(); i++)
            {
                EntityItem prodstructItem = (EntityItem) prodstructVector.get(i);

                for(int j = 0; j < prodstructItem.getDownLinkCount(); j++)
                {
                    EANEntity entity = prodstructItem.getDownLink(j);
                    if(entity.getEntityType().equals("MODEL"))
                    {
                        if(isEntityWithMatchedAttr((EntityItem) entity, tempHT))
                        {
                            EntityItem modelItem = (EntityItem) entity;
                            Vector modelCGVector = getAllLinkedEntities(modelItem, "MDLCGMDL", "MODELCG");
                            Vector modelCGOSVector = getAllLinkedEntities(modelCGVector, "MDLCGMDLCGOS", "MODELCGOS");
                            Vector supportedDevicesVector = getAllLinkedEntities(modelCGOSVector, "MDLCGOSMDL", "MODEL");
                            populate_SupportedDevices_TM(availItem, modelItem, supportedDevicesVector);
                        }//end of if(isEntityWithMatchedAttr((EntityItem) entity, tempHT))
                    }//end of if(entity.getEntityType().equals("MODEL"))
                }//end of for(int j = 0; j < prodstructItem.getDownLinkCount(); i++)
            }//end of for(int i = 0; i < prodstructVector.size(); i++)
        }//end of while(srcItr.hasNext())
    }

    /********************************************************************************
    *
    *
    * @param availItem EntityItem
    * @param modelItem EntityItem
    * @param supportedDevicesVector Vector
    */
    private void populate_SupportedDevices_TM(EntityItem availItem, EntityItem modelItem, Vector supportedDevicesVector )
    {
        /*
           OIM3.0b
           Key = MACHTYPEATR(MODEL) +
                 COMPATDVCCAT(PARENT MODEL) +
                 MACHTYPEATR(PARENT MODEL) +
                 MODELATR(PARENT MODEL) +
                 MKTGNAME(PARENT MODEL) +
                 MODELATR(MODEL)

                 For OIM3.0b we don't use SUPPDEVICE anymore instead we use parent MODEL(child of MODELCGOS) and also FMGROUP(SUPPDEVICE) is replaced with COMPATDVCCAT(MODEL)

           OIM3.0a
           Key = MACHTYPEATR(MODEL) +
                 FMGROUP(SUPPDEVICE) +
                 MACHTYPESD(SUPPDEVICE) +
                 MODELATR(SUPPDEVICE) +
                 MKTGNAME(SUPPDEVICE) +
                 MODELATR(MODEL)
        */

        for(int i = 0; i < supportedDevicesVector.size(); i++)
        {
            EntityItem suppDeviceItem;
            String key;
            String mt;
            String machType;
            String suppDeviceModel;
            String model;
            String geo;
            String modelItemKey = modelItem.getKey();
            updateGeoHT(availItem, modelItemKey);

            suppDeviceItem = (EntityItem) supportedDevicesVector.get(i);

            key = getAttributeFlagValue(modelItem, "MACHTYPEATR");
            if(null == key)
            {
                key = " ";
            }
            key = key.trim();
            key = setString(key, 4);
            mt = key;

            key = key + "<:>" + getAttributeValue(suppDeviceItem, "COMPATDVCCAT", "", "", false).trim();
            machType = getAttributeValue(suppDeviceItem, "MACHTYPEATR", "", "0000", false);
            machType = machType.trim();
            machType = setString(machType, 4);
            key = key + "<:>" + machType;
            suppDeviceModel = getAttributeValue(suppDeviceItem, "MODELATR", "", "000", false);
            suppDeviceModel = suppDeviceModel.trim();
            suppDeviceModel = setString(suppDeviceModel, 3);
            key = key + "<:>" + suppDeviceModel;
            key = key + "<:>" + getAttributeValue(suppDeviceItem, "MKTGNAME", "", "", false).trim();
            model = getAttributeValue(modelItem, "MODELATR", "", "000", false);
            model = model.trim();
            model = setString(model, 3);
            key = key + "<:>" + model;
            geo = getGeo(modelItemKey);

            if(getAttributeValue(suppDeviceItem, "COMPATDVCCAT", "", "", false).trim().equals(""))
            {
                featureMatrixError.add("40<:>" + "External Machine Type (Support Devices)<:>" + mt + "<:>" + model + "<:>SUPPDEVICE: " + machType + "-" + suppDeviceModel + "<:>COMPATDVCCAT");
            }

            if(getAttributeValue(suppDeviceItem, "MKTGNAME", "", "", false).trim().equals(""))
            {
                featureMatrixError.add("40<:>" + "External Machine Type (Support Devices)<:>" + mt + "<:>" + model + "<:>SUPPDEVICE: " + machType + "-" + suppDeviceModel + "<:>Marketing Name");
            }

            addToTreeMap(key, geo, supportedDevices_TM);
        }//end of for(int n = 0; n < supportedDevicesVector.size(); n++)
    }

    /********************************************************************************
    *
    *
    */
    private void featureMatrix()
    {
        /*
           Key = MACHTYPEATR(MODEL) +
                 MODELATR(MODEL) +
                 FEATURECODE(FEATURE) +
                 ORDERCODE(PRODSTRUCT) +
                 INVNAME(FEATURE) or MKTGNAME(FEATURE) +
                 EDITORNOTE(FEATURE)
        */

        String invName;
        String mktgName;
        Iterator srcItr;
        Hashtable tempHT = new Hashtable();
        /*** Need to get flag codes for COFCAT, COFSUBCAT, COFGRP. As of today 8/17/04 flag codes are not defined yet ***/
        tempHT.put("COFCAT", "100");    //Hardware
        //if(!brand.equals(XSERIES))
        //{
        //    tempHT.put("COFSUBCAT", "126"); //System
        //}
        tempHT.put("COFGRP", "150");    //Base

        invName = "";
        mktgName = "";

        //Path 1:
        srcItr = availVector.iterator();
        while(srcItr.hasNext())
        {
            EntityItem availItem = (EntityItem) srcItr.next();
            Vector modelVector = getAllLinkedEntities(availItem, "MODELAVAIL", "MODEL");
            modelVector = getEntitiesWithMatchedAttr(modelVector, tempHT);
            for(int i = 0; i < modelVector.size(); i++)
            {
                EANEntity entityUpLink1;
                EntityItem modelItem = (EntityItem) modelVector.get(i);
                String modelItemKey = modelItem.getKey();
                updateGeoHT(availItem, modelItemKey);

                entityUpLink1 = getUpLinkEntityItem(modelItem, "PRODSTRUCT");
                if(null != entityUpLink1)
                {
                    EntityItem prodstructItem = (EntityItem) entityUpLink1;
                    EANEntity entityUpLink2 = getUpLinkEntityItem(prodstructItem, "FEATURE");
                    if(null != entityUpLink2)
                    {
                        String mt;
                        String model;
                        String fc;
                        String geo;
                        EntityItem featureItem = (EntityItem) entityUpLink2;

                        String key = getAttributeFlagValue(modelItem, "MACHTYPEATR");
                        if(null == key)
                        {
                            key = " ";
                        }
                        key = key.trim();
                        key = setString(key, 4);
                        mt = key;
                        model = getAttributeValue(modelItem, "MODELATR", "", "000", false);
                        model = model.trim();
                        model = setString(model, 3);

                        key = key + "<:>" + model;
                        fc = getAttributeValue(featureItem, "FEATURECODE", "", "", false);
                        fc = fc.trim();
                        if(fc.equals(""))
                        {
                            featureMatrixError.add("50<:>" + "Feature Matrix<:>" + mt + "<:>" + model + "<:>FEATURE<:>Feature Code");
                        }
                        fc = setString(fc, 4);
                        key = key + "<:>" + fc;

                        //Check for ORDERCODE = B, M, P, S
                        //If ORDERCODE = B, M, P then set to A
                        //If ORDERCODE = S then set to S
                        //If ORDERCODE = Must Remove then set to N
                        if(isSelected(prodstructItem, "ORDERCODE", "5955"))
                        {
                            if(FORMAT2 == format)
                            {
                                key = key + "<:>" + "A";
                            }
                            else
                            {
                                key = key + "<:>" + "B";
                            }
                        }
                        else if(isSelected(prodstructItem, "ORDERCODE", "5956"))
                        {
                            if(FORMAT2 == format)
                            {
                                key = key + "<:>" + "A";
                            }
                            else
                            {
                                key = key + "<:>" + "M";
                            }
                        }
                        else if(isSelected(prodstructItem, "ORDERCODE", "5957"))
                        {
                            if(FORMAT2 == format)
                            {
                                key = key + "<:>" + "A";
                            }
                            else
                            {
                                key = key + "<:>" + "I";
                            }
                        }
                        else if(isSelected(prodstructItem, "ORDERCODE", "5958"))
                        {
                            key = key + "<:>" + "S";
                        }
                        else if(isSelected(prodstructItem, "ORDERCODE", "5959"))
                        {
                            if(FORMAT2 == format)
                            {
                                key = key + "<:>" + "N";
                            }
                            else
                            {
                                key = key + "<:>" + " ";
                            }
                        }
                        else
                        {
                            key = key + "<:>" + " ";
                        }

                        if(getAttributeValue(prodstructItem, "ORDERCODE", "", "", false).equals(""))
                        {
                            featureMatrixError.add("50<:>" + "Feature Matrix<:>" + mt + "<:>" + model + "<:>PRODSTRUCT " + getAttributeValue(prodstructItem, "COMNAME", "", "", false) + "<:>Original Order Code");
                        }

                        if(FORMAT1 == format)
                        {
                            invName = getAttributeValue(featureItem, "INVNAME", "", "", false).trim();
                            if(invName.equals(""))
                            {
                                invName = getAttributeValue(featureItem, "COMNAME", "", "", false);
                                if(invName.length() > I_28)
                                {
                                    invName = invName.substring(0, I_28);
                                }
                                invName = invName.trim();
                                invName = invName.toUpperCase();
                                //if(invName.equals(""));
                                //   featureMatrixError.add("50<:>" + "Feature Matrix<:>" + mt + "<:>" + model + "<:>FEATURE " + fc + "<:>Price File Name");
                            }
                            key = key + "<:>" + invName;
                        }
                        else if(FORMAT2 == format || FORMAT3 == format)
                        {
                            mktgName = getAttributeValue(featureItem, "MKTGNAME", "", "", false).trim();
                            if(mktgName.equals(""))
                            {
                                featureMatrixError.add("50<:>" + "Feature Matrix<:>" + mt + "<:>" + model + "<:>FEATURE " + fc + "<:>Marketing Name");
                            }
                            key = key + "<:>" + mktgName;
                        }

                        key = key + "<:>" + getAttributeValue(featureItem, "EDITORNOTE", "", "", false).trim();

                        geo = getGeo(modelItemKey);

                        addToTreeMap(key, geo, featureMatrix_TM);
                    }//end of if(null != entityUpLink2)
                }//end of if(null != entityUpLink)
            }//end of for(int i = 0; i < modelVector.size(); i++)
        }//end of while(srcItr.hasNext())

        //Path 2:
        srcItr = availVector.iterator();
        while(srcItr.hasNext())
        {
            EntityItem availItem = (EntityItem) srcItr.next();
            Vector prodstructVector = getAllLinkedEntities(availItem, "OOFAVAIL", "PRODSTRUCT");
            for(int i = 0; i < prodstructVector.size(); i++)
            {
                EANEntity entityDownLink;
                EntityItem prodstructItem = (EntityItem) prodstructVector.get(i);
                String prodstructItemKey = prodstructItem.getKey();
                updateGeoHT(availItem, prodstructItemKey);

                entityDownLink = getDownLinkEntityItem(prodstructItem, "MODEL");
                if(null != entityDownLink)
                {
                    if(isEntityWithMatchedAttr((EntityItem) entityDownLink, tempHT))
                    {
                        EntityItem modelItem = (EntityItem) entityDownLink;
                        EANEntity entityUpLink = getUpLinkEntityItem(prodstructItem, "FEATURE");
                        if(null != entityUpLink)
                        {
                            String mt;
                            String model;
                            String fc;
                            String geo;
                            EntityItem featureItem = (EntityItem) entityUpLink;

                            String key = getAttributeFlagValue(modelItem, "MACHTYPEATR");
                            if(null == key)
                            {
                                key = " ";
                            }
                            key = key.trim();
                            key = setString(key, 4);
                            mt = key;
                            model = getAttributeValue(modelItem, "MODELATR", "", "000", false);
                            model = model.trim();
                            model = setString(model, 3);

                            key = key + "<:>" + model;
                            fc = getAttributeValue(featureItem, "FEATURECODE", "", "", false);
                            fc = fc.trim();
                            if(fc.equals(""))
                            {
                                featureMatrixError.add("50<:>" + "Feature Matrix<:>" + mt + "<:>" + model + "<:>FEATURE<:>Feature Code");
                            }
                            fc = setString(fc, 4);
                            key = key + "<:>" + fc;

                            //Check for ORDERCODE = B, M, P, S
                            //If ORDERCODE = B, M, P then set to A
                            //If ORDERCODE = S then set to S
                            //If ORDERCODE = Must Remove then set to N
                            if(isSelected(prodstructItem, "ORDERCODE", "5955"))
                            {
                                if(FORMAT2 == format)
                                {
                                    key = key + "<:>" + "A";
                                }
                                else
                                {
                                    key = key + "<:>" + "B";
                                }
                            }
                            else if(isSelected(prodstructItem, "ORDERCODE", "5956"))
                            {
                                if(FORMAT2 == format)
                                {
                                    key = key + "<:>" + "A";
                                }
                                else
                                {
                                    key = key + "<:>" + "M";
                                }
                            }
                            else if(isSelected(prodstructItem, "ORDERCODE", "5957"))
                            {
                                if(FORMAT2 == format)
                                {
                                    key = key + "<:>" + "A";
                                }
                                else
                                {
                                    key = key + "<:>" + "I";
                                }
                            }
                            else if(isSelected(prodstructItem, "ORDERCODE", "5958"))
                            {
                                key = key + "<:>" + "S";
                            }
                            else if(isSelected(prodstructItem, "ORDERCODE", "5959"))
                            {
                                if(FORMAT2 == format)
                                {
                                    key = key + "<:>" + "N";
                                }
                                else
                                {
                                    key = key + "<:>" + " ";
                                }
                            }
                            else
                            {
                                key = key + "<:>" + " ";
                            }

                            if(getAttributeValue(prodstructItem, "ORDERCODE", "", "", false).equals(""))
                            {
                                featureMatrixError.add("50<:>" + "Feature Matrix<:>" + mt + "<:>" + model + "<:>PRODSTRUCT " + getAttributeValue(prodstructItem, "COMNAME", "", "", false) + "<:>Original Order Code");
                            }

                            if(FORMAT1 == format)
                            {
                                invName = getAttributeValue(featureItem, "INVNAME", "", "", false).trim();
                                if(invName.equals(""))
                                {
                                    invName = getAttributeValue(featureItem, "COMNAME", "", "", false);
                                    if(invName.length() > I_28)
                                    {
                                        invName = invName.substring(0, I_28);
                                    }
                                    invName = invName.trim();
                                    invName = invName.toUpperCase();
                                    //if(invName.equals(""));
                                    //   featureMatrixError.add("50<:>" + "Feature Matrix<:>" + mt + "<:>" + model + "<:>FEATURE " + fc + "<:>Price File Name");
                                }
                                key = key + "<:>" + invName;
                            }
                            else if(FORMAT2 == format || FORMAT3 == format)
                            {
                                mktgName = getAttributeValue(featureItem, "MKTGNAME", "", "", false).trim();
                                if(mktgName.equals(""))
                                {
                                    featureMatrixError.add("50<:>" + "Feature Matrix<:>" + mt + "<:>" + model + "<:>FEATURE " + fc + "<:>Marketing Name");
                                }
                                key = key + "<:>" + mktgName;
                            }

                            key = key + "<:>" + getAttributeValue(featureItem, "EDITORNOTE", "", "", false).trim();

                            geo = getGeo(prodstructItemKey);

                            addToTreeMap(key, geo, featureMatrix_TM);
                        }//end of if(null != entityUpLink)
                    }//end of if(isEntityWithMatchedAttr((EntityItem) entityDownLink, tempHT))
                }//end of if(null != entityDownLink)
            }//end of for(int i = 0; i < prodstructVector.size(); i++)
        }//end of while(srcItr.hasNext())
    }

    /********************************************************************************
    *
    *
    */
    private void getGEOInfoForLSEO()
    {
        Iterator srcItr = availVector.iterator();
        while(srcItr.hasNext())
        {
            EntityItem availItem = (EntityItem) srcItr.next();
            Vector lseoV = getAllLinkedEntities(availItem, "LSEOAVAIL", "LSEO");
            for(int i = 0; i < lseoV.size(); i++)
            {
                EntityItem lseoItem = (EntityItem) lseoV.get(i);

                String lseoItemKey = lseoItem.getKey();
                updateGeoHT(availItem, lseoItemKey);
            }//end of for(int i = 0; i < lseoV.size(); i++)
        }//end of while(srcItr.hasNext())
    }

    /********************************************************************************
    *
    *
    */
    private void getMTM()
    {
        String machineType = "";
        String model = "";
        Vector wwseoVector;
        Vector prodstructVector;

        lseoVector = getAllLinkedEntities(availVector, "LSEOAVAIL", "LSEO");
        wwseoVector = getAllLinkedEntities(lseoVector, "WWSEOLSEO", "WWSEO");
        prodstructVector = getAllLinkedEntities(lseoVector, "LSEOPRODSTRUCT", "PRODSTRUCT");
        if(wwseoVector.size() > 0)
        {
            prodstructVector.addAll(getAllLinkedEntities(wwseoVector, "WWSEOPRODSTRUCT", "PRODSTRUCT"));
        }

        for(int i = 0; i < prodstructVector.size(); i++)
        {
            EntityItem prodstructItem = (EntityItem) prodstructVector.get(i);
            EANEntity entityDownLink = getDownLinkEntityItem(prodstructItem, "MODEL");
            EntityItem modelItem = (EntityItem) entityDownLink;
            EANEntity entityUpLink = getUpLinkEntityItem(prodstructItem, "FEATURE");
            if((null != entityUpLink) && (null != modelItem))
            {
                machineType = getAttributeFlagValue(modelItem, "MACHTYPEATR");
                if(null == machineType)
                {
                    machineType = " ";
                }
                machineType = machineType.trim();
                machineType = setString(machineType, 4);
                model = getAttributeValue(modelItem, "MODELATR", "", "000", false);
                model = model.trim();
                model = setString(model, 3);

                mtmTS.add(machineType + "-" + model);

            }//end of if(null != entityUpLink)
        }//end of for(int i = 0; i < prodstructVector.size(); i++)

        wwseoVector.clear();
        wwseoVector = null;
        prodstructVector.clear();
        prodstructVector = null;
    }

    /********************************************************************************
    *
    *
    */
    private void populateSEOTable()
    {
        /*
           Key = Geo +
                 MTM +
                 CLOCKRATE(PROC) +
                 CLOCKRATEUNIT(PROC) +
                 PROCL2CACHE(PROC) +
                 PROCL2CACHEUNIT(PROC) +
                 MEMRYRAMSTD(DERIVEDDATA) +
                 MEMRYRAMSTDUNIT(DERIVEDDATA) +
                 INTRFC(HDD) +
                 HDDSZE(HDD) +
                 HDDSZEUNIT(HDD) +
                 OPTCALDRIVETYPE(OPTCALDVC) +
        */

        Iterator aItr = mtmTS.iterator();
        while(aItr.hasNext())
        {
            String mtm = (String) aItr.next();
            for(int i = 0; i < lseoVector.size(); i++)
            {
                EntityItem lseoItem = (EntityItem) lseoVector.get(i);
                populateSEOTable(mtm, lseoItem);
            }//end of for(int i = 0; i < lseoVector.size(); i++)
        }//end of while(aItr.hasNext())
    }

    /********************************************************************************
    *
    * @param mtm String
    * @param lseoItem EntityItem
    */
    private void populateSEOTable(String mtm, EntityItem lseoItem)
    {
        String machineType = "";
        String model = "";
        String machineTypeModel = "";
        TreeSet ts;
        TreeSet [] arrayTS;
        Iterator tsItr;
        Vector prodstructVector;
        Vector wwseoVector;
        boolean aFlag = true;
        String geo = getGeo(lseoItem.getKey());

        ts = new TreeSet();

        arrayTS = new TreeSet[4];
        for(int i = 0; i < 4; i++)
        {
            arrayTS[i] = new TreeSet();
        }

        //Relationship between WWSEO and LSEO is 1 to n.
        wwseoVector = getAllLinkedEntities(lseoItem, "WWSEOLSEO", "WWSEO");
        prodstructVector = getAllLinkedEntities(lseoItem, "LSEOPRODSTRUCT", "PRODSTRUCT");
        if(wwseoVector.size() > 0)
        {
            prodstructVector.addAll(getAllLinkedEntities((EntityItem)wwseoVector.get(0), "WWSEOPRODSTRUCT", "PRODSTRUCT"));
        }

        for(int i = 0; i < prodstructVector.size(); i++)
        {
            EntityItem prodstructItem = (EntityItem) prodstructVector.get(i);
            EANEntity entityDownLink = getDownLinkEntityItem(prodstructItem, "MODEL");
            EntityItem modelItem = (EntityItem) entityDownLink;
            EANEntity entityUpLink = getUpLinkEntityItem(prodstructItem, "FEATURE");
            if((null != entityUpLink) && (null != modelItem))
            {
                machineType = getAttributeFlagValue(modelItem, "MACHTYPEATR");
                if(null == machineType)
                {
                    machineType = " ";
                }
                machineType = machineType.trim();
                machineType = setString(machineType, 4);
                model = getAttributeValue(modelItem, "MODELATR", "", "000", false);
                model = model.trim();
                model = setString(model, 3);
                machineTypeModel = machineType + "-" + model;

                if(machineTypeModel.equals(mtm))
                {
                    getPROCForSEOTable((EntityItem) entityUpLink, arrayTS[0]);
                    if(aFlag)
                    {
                        getDDForSEOTable(wwseoVector, arrayTS[1]);
                    }
                    getHDDForSEOTable((EntityItem) entityUpLink, arrayTS[2]);
                    getOptcalDvcForSEOTable((EntityItem) entityUpLink, arrayTS[3]);
                    aFlag = false;
                }//end of if(machineTypeModel.equals(mtm))
            }//end of if(null != entityUpLink)
        }//end of for(int i = 0; i < prodstructVector.size(); i++)

        if(arrayTS[0].size() > 1)
        {
            arrayTS[0].remove("   <:>   <:>  <:>  ");
        }
        if(arrayTS[1].size() > 1)
        {
            arrayTS[1].remove("   <:>  ");
        }
        if(arrayTS[2].size() > 1)
        {
            arrayTS[2].remove(setString(" ", I_12));
        }
        if(arrayTS[3].size() > 1)
        {
            arrayTS[3].remove(setString(" ", I_21));
        }

        merge(geo, mtm, ts, arrayTS);

        tsItr = ts.iterator();
        while(tsItr.hasNext())
        {
            String key = (String) tsItr.next();
            seoTable_TM.put(key, geo);
        }//end of while(tsItr.hasNext())
    }

    /********************************************************************************
    *
    * @param featureItem EntityItem
    * @param aTS TreeSet
    */
    private void getPROCForSEOTable(EntityItem featureItem, TreeSet aTS)
    {
        EntityItem procItem;
        String clockRate;
        String clockRateUnit;
        String l2Cache;
        String l2CacheUnit;

        Vector procVector = getAllLinkedEntities(featureItem, "FEATUREPROC", "PROC");

        if(procVector.size() > 0)
        {
            for(int i = 0; i < procVector.size(); i++)
            {
                procItem = (EntityItem) procVector.get(i);
                clockRate = getAttributeValue(procItem, "CLOCKRATE", "", "", false);
                clockRate = clockRate.trim();
                clockRate = setString2(clockRate, 3);
                clockRateUnit = getAttributeValue(procItem, "CLOCKRATEUNIT", "", "", false);
                clockRateUnit = clockRateUnit.trim();
                clockRateUnit = setString(clockRateUnit, 3);
                l2Cache = getAttributeValue(procItem, "PROCL2CACHE", "", "", false);
                l2Cache = l2Cache.trim();
                l2Cache = setString2(l2Cache, 2);
                l2CacheUnit = getAttributeValue(procItem, "PROCL2CACHEUNIT", "", "", false);
                l2CacheUnit = l2CacheUnit.trim();
                l2CacheUnit = setString(l2CacheUnit, 2);

                aTS.add(clockRate + "<:>" + clockRateUnit + "<:>" + l2Cache + "<:>" + l2CacheUnit);
            }//end of for(int i = 0; i < procVector.size(); i++)
        }//end of if(procVector.size() > 0)
        else
        {
            aTS.add("   " + "<:>" + "   " + "<:>" + "  " + "<:>" + "  ");
        }

        procVector.clear();
        procVector = null;
    }

    /********************************************************************************
    *
    * @param wwseoVector Vector
    * @param aTS TreeSet
    */
    private void getDDForSEOTable(Vector wwseoVector, TreeSet aTS)
    {
        EntityItem wwseoItem;
        Vector ddVector;
        EntityItem ddItem;
        String ramStd;
        String ramStdUnit;

        //Relationship between WWSEO and LSEO is 1 to n.
        if(wwseoVector.size() > 0)
        {
            wwseoItem = (EntityItem) wwseoVector.get(0);
            //Relationship between WWSEO and DERIVEDDATA is 1 to 1.
            ddVector = getAllLinkedEntities(wwseoItem, "WWSEODERIVEDDATA", "DERIVEDDATA");
            if(ddVector.size() > 0)
            {
                ddItem = (EntityItem) ddVector.get(0);

                ramStd = getAttributeValue(ddItem, "MEMRYRAMSTD", "", "", false);
                ramStd = ramStd.trim();
                ramStd = setString2(ramStd, 3);
                ramStdUnit = getAttributeValue(ddItem, "MEMRYRAMSTDUNIT", "", "", false);
                ramStdUnit = ramStdUnit.trim();
                ramStdUnit = setString(ramStdUnit, 2);

                aTS.add(ramStd + "<:>" + ramStdUnit);
            }//end of if(ddVector.size() > 0)
            else
            {
                aTS.add("   " + "<:>" + "  ");
            }
        }//end of if(wwseoVector.size() > 0)
        else
        {
            aTS.add("   " + "<:>" + "  ");
        }
    }

    /********************************************************************************
    *
    * @param featureItem EntityItem
    * @param aTS TreeSet
    */
    private void getHDDForSEOTable(EntityItem featureItem, TreeSet aTS)
    {
        EntityItem hddItem;
        String intrfc;
        String hddSize;
        String hddSizeUnit;
        String hddInfo;

        Vector hddVector = getAllLinkedEntities(featureItem, "FEATUREHDD", "HDD");

        if(hddVector.size() > 0)
        {
            for(int i = 0; i < hddVector.size(); i++)
            {
                hddItem = (EntityItem) hddVector.get(i);
                intrfc = getAttributeValue(hddItem, "INTRFC", "", "", false);
                intrfc = intrfc.trim();
                hddSize = getAttributeValue(hddItem, "HDDSZE", "", "", false);
                hddSize = hddSize.trim();
                hddSizeUnit = getAttributeValue(hddItem, "HDDSZEUNIT", "", "", false);
                hddSizeUnit = hddSizeUnit.trim();
                hddInfo = intrfc + " " + hddSize + " " + hddSizeUnit;
                hddInfo = hddInfo.trim();

                if(hddInfo.length() < I_12)
                {
                    hddInfo = setString(hddInfo, I_12);
                }

                aTS.add(hddInfo);
            }//end of for(int i = 0; i < hddVector.size(); i++)
        }//end of if(hddVector.size() > 0)
        else
        {
            aTS.add(setString(" ", I_12));
        }

        hddVector.clear();
        hddVector = null;
    }

    /********************************************************************************
    *
    * @param featureItem EntityItem
    * @param aTS TreeSet
    */
    private void getOptcalDvcForSEOTable(EntityItem featureItem, TreeSet aTS)
    {
        EntityItem optcalDvcItem;
        String optcalDriveType;

        Vector optcalDvcVector = getAllLinkedEntities(featureItem, "FEATUREOPTCALDVC", "OPTCALDVC");

        if(optcalDvcVector.size() > 0)
        {
            for(int i = 0; i < optcalDvcVector.size(); i++)
            {
                optcalDvcItem = (EntityItem) optcalDvcVector.get(i);
                optcalDriveType = getAttributeValue(optcalDvcItem, "OPTCALDRIVETYPE", "", "", false);
                optcalDriveType = optcalDriveType.trim();

                if(optcalDriveType.length() < I_21)
                {
                    optcalDriveType = setString(optcalDriveType, I_21);
                }

                aTS.add(optcalDriveType);
            }//end of for(int i = 0; i < optcalDvcVector.size(); i++)
        }//end of if(optcalDvcVector.size() > 0)
        else
        {
            aTS.add(setString(" ", I_21));
        }

        optcalDvcVector.clear();
        optcalDvcVector = null;
    }

    /********************************************************************************
    *
    *
    */
    private void populateSEODescription()
    {
        /*
           Key = Geo +
                 MTM +
                 CLOCKRATE(PROC) +
                 CLOCKRATEUNIT(PROC) +
                 PROCL2CACHE(PROC) +
                 PROCL2CACHEUNIT(PROC) +
                 MEMRYRAMSTD(DERIVEDDATA) +
                 MEMRYRAMSTDUNIT(DERIVEDDATA) +
                 INTRFC(HDD) +
                 HDDSZE(HDD) +
                 HDDSZEUNIT(HDD) +
                 OPTCALDRIVETYPE(OPTCALDVC) +
        */

        Iterator aItr = mtmTS.iterator();
        while(aItr.hasNext())
        {
            String mtm = (String) aItr.next();
            for(int i = 0; i < lseoVector.size(); i++)
            {
                EntityItem lseoItem = (EntityItem) lseoVector.get(i);
                populateSEODescription(mtm, lseoItem);
            }//end of for(int i = 0; i < lseoVector.size(); i++)
        }//end of while(aItr.hasNext())
    }

    /********************************************************************************
    *
    *
    */
    private void populateSEODescription(String mtm, EntityItem lseoItem)
    {
        String machineType = "";
        String model = "";
        String machineTypeModel = "";
        //TreeSet ts;
        TreeSet [] arrayTS;
        Vector prodstructVector;
        Vector wwseoVector;
        boolean aFlag = true;
        Vector optcalDriveTypeVector = new Vector();

        //ts = new TreeSet();
        //ts.add(mtm);

        arrayTS = new TreeSet[I_13];
        for(int i = 0; i < I_13; i++)
        {
            arrayTS[i] = new TreeSet();
        }

        //Relationship between WWSEO and LSEO is 1 to n.
        wwseoVector = getAllLinkedEntities(lseoItem, "WWSEOLSEO", "WWSEO");
        prodstructVector = getAllLinkedEntities(lseoItem, "LSEOPRODSTRUCT", "PRODSTRUCT");
        if(wwseoVector.size() > 0)
        {
            prodstructVector.addAll(getAllLinkedEntities((EntityItem)wwseoVector.get(0), "WWSEOPRODSTRUCT", "PRODSTRUCT"));
        }

        for(int i = 0; i < prodstructVector.size(); i++)
        {
            EntityItem prodstructItem = (EntityItem) prodstructVector.get(i);
            EANEntity entityDownLink = getDownLinkEntityItem(prodstructItem, "MODEL");
            EntityItem modelItem = (EntityItem) entityDownLink;
            EANEntity entityUpLink = getUpLinkEntityItem(prodstructItem, "FEATURE");
            if((null != entityUpLink) && (null != modelItem))
            {
                EntityItem featureItem = (EntityItem) entityUpLink;
                machineType = getAttributeFlagValue(modelItem, "MACHTYPEATR");
                if(null == machineType)
                {
                    machineType = " ";
                }
                machineType = machineType.trim();
                machineType = setString(machineType, 4);
                model = getAttributeValue(modelItem, "MODELATR", "", "000", false);
                model = model.trim();
                model = setString(model, 3);
                machineTypeModel = machineType + "-" + model;

                if(machineTypeModel.equals(mtm))
                {
                    getPROCForSEODescription(featureItem, arrayTS[0]);
                    if(aFlag)
                    {
                        getDDForSEODescription(wwseoVector, arrayTS[1]);
                    }
                    getMemoryForSEODescription(featureItem, arrayTS[2]);
                    getPlanarForSEODescription(featureItem, arrayTS[3]);
                    getGrphAdapterForSEODescription(featureItem, arrayTS[4]);
                    getHDCForSEODescription(featureItem, arrayTS[5]);
                    getHDDForSEODescription(featureItem, arrayTS[6]);
                    getMECHPKGForSEODescription(featureItem, arrayTS[7]);
                    if(aFlag)
                    {
                        getBaysAvailForSEODescription(wwseoVector, arrayTS[8]);
                    }
                    getSysMgAdapterForSEODescription(featureItem, arrayTS[9]);
                    getNICForSEODescription(featureItem, arrayTS[10]);
                    getOptcalDvcForSEODescription(featureItem, optcalDriveTypeVector);
                    getPwrSupplyForSEODescription(featureItem, arrayTS[I_12]);
                    aFlag = false;
                }//end of if(machineTypeModel.equals(mtm))
            }//end of if(null != entityUpLink)
        }//end of for(int i = 0; i < prodstructVector.size(); i++)

        getOptcalDvcForSEODescription(optcalDriveTypeVector, arrayTS[I_11]);
        if(arrayTS[0].size() > 1)
        {
            arrayTS[0].remove(setString(" ", I_17) + "<:>" + setString(" ", I_17) + "<:>" + setString(" ", I_17) + "<:>" + setString(" ", I_17));
        }
        if(arrayTS[1].size() > 1)
        {
            arrayTS[1].remove(setString(" ", I_17) + "<:>" + setString(" ", I_17) + "<:>" + setString(" ", I_17) + "<:>" + setString(" ", I_17));
        }
        if(arrayTS[2].size() > 1)
        {
            arrayTS[2].remove(setString(" ", I_17));
        }
        if(arrayTS[3].size() > 1)
        {
            arrayTS[3].remove(setString(" ", I_17) + "<:>" + setString(" ", I_17) + "<:>" + setString(" ", I_17) + "<:>" + "<::>");
        }
        if(arrayTS[3].size() > 1)
        {
            arrayTS[3] = combineInfo1(arrayTS[3]);
        }
        if(arrayTS[4].size() > 1)
        {
            arrayTS[4].remove(setString(" ", I_17) + "<:>" + setString(" ", I_17));
        }
        if(arrayTS[5].size() > 1)
        {
            arrayTS[5].remove(setString(" ", I_17) + "<:>" + setString(" ", I_17));
        }
        if(arrayTS[6].size() > 1)
        {
            arrayTS[6].remove(setString(" ", I_17));
        }
        if(arrayTS[7].size() > 1)
        {
            arrayTS[7].remove(setString(" ", I_17) + "<:>" + "<::>");
        }
        if(arrayTS[7].size() > 1)
        {
            arrayTS[7] = combineInfo2(arrayTS[7]);
        }
        if(arrayTS[8].size() > 1)
        {
            arrayTS[8].remove("<::>");
        }
        if(arrayTS[9].size() > 1)
        {
            arrayTS[9].remove(setString(" ", I_17));
        }
        if(arrayTS[10].size() > 1)
        {
            arrayTS[10].remove(setString(" ", I_17));
        }
        if(arrayTS[I_11].size() > 1)
        {
            arrayTS[I_11].remove(setString(" ", I_22) + "<::>" + setString(" ", I_17));
        }
        if(arrayTS[I_12].size() > 1)
        {
            arrayTS[I_12].remove(setString(" ", I_17) + "<:>" + setString(" ", I_17));
        }

        seoDescription_TM.put(mtm, arrayTS);
    }

    /********************************************************************************
    *
    * @param featureItem EntityItem
    * @param aTS TreeSet
    */
    private void getPROCForSEODescription(EntityItem featureItem, TreeSet aTS)
    {
        EntityItem procItem;
        String procSpeedMktgName;
        String clockRate;
        String clockRateUnit;
        String clockRateInfo;
        String procBusSpeed;
        String procBusSpeedUnit;
        String procBusSpeedInfo;
        String procConfig;

        Vector procVector = getAllLinkedEntities(featureItem, "FEATUREPROC", "PROC");

        if(procVector.size() > 0)
        {
            for(int i = 0; i < procVector.size(); i++)
            {
                procItem = (EntityItem) procVector.get(i);
                procSpeedMktgName = getAttributeValue(procItem, "PROCSPEDMKTGNAM", "", "", false);
                procSpeedMktgName = procSpeedMktgName.trim();
                if(procSpeedMktgName.length() < I_17)
                {
                    procSpeedMktgName = setString(procSpeedMktgName, I_17);
                }
                clockRate = getAttributeValue(procItem, "CLOCKRATE", "", "", false);
                clockRate = clockRate.trim();
                clockRateUnit = getAttributeValue(procItem, "CLOCKRATEUNIT", "", "", false);
                clockRateUnit = clockRateUnit.trim();
                clockRateInfo = clockRate + " " + clockRateUnit;
                clockRateInfo = setString(clockRateInfo, I_17);
                procBusSpeed = getAttributeValue(procItem, "PROCBUSSPED", "", "", false);
                procBusSpeed = procBusSpeed .trim();
                procBusSpeedUnit = getAttributeValue(procItem, "PROCBUSSPEDUNIT", "", "", false);
                procBusSpeedUnit = procBusSpeedUnit.trim();
                procBusSpeedInfo = procBusSpeed + " " + procBusSpeedUnit;
                procBusSpeedInfo = setString(procBusSpeedInfo, I_17);
                procConfig = getAttributeValue(procItem, "PROCCONFIG", "", "", false);
                procConfig = procConfig.trim();
                if(procConfig.length() < I_17)
                {
                    procConfig = setString(procConfig, I_17);
                }

                aTS.add(procSpeedMktgName + "<:>" + clockRateInfo + "<:>" + procBusSpeedInfo + "<:>" + procConfig);
            }//end of for(int i = 0; i < procVector.size(); i++)
        }//end of if(procVector.size() > 0)
        else
        {
            aTS.add(setString(" ", I_17) + "<:>" + setString(" ", I_17) + "<:>" + setString(" ", I_17) + "<:>" + setString(" ", I_17));
        }

        procVector.clear();
        procVector = null;
    }

    /********************************************************************************
    *
    * @param wwseoVector Vector
    * @param aTS TreeSet
    */
    private void getDDForSEODescription(Vector wwseoVector, TreeSet aTS)
    {
        EntityItem wwseoItem;
        Vector ddVector;
        EntityItem ddItem;
        String noOfProcStd;
        String totL2CacheStd;
        String totL2CacheStdUnit;
        String totL2CacheStdInfo;
        String totAvailBay;
        String slotAvail;

        //Relationship between WWSEO and LSEO is 1 to n.
        if(wwseoVector.size() > 0)
        {
            wwseoItem = (EntityItem) wwseoVector.get(0);
            //Relationship between WWSEO and DERIVEDDATA is 1 to 1.
            ddVector = getAllLinkedEntities(wwseoItem, "WWSEODERIVEDDATA", "DERIVEDDATA");
            if(ddVector.size() > 0)
            {
                ddItem = (EntityItem) ddVector.get(0);

                noOfProcStd = getAttributeValue(ddItem, "NOOFPROCSTD", "", "", false);
                noOfProcStd = noOfProcStd.trim();
                noOfProcStd = setString(noOfProcStd, I_17);
                totL2CacheStd = getAttributeValue(ddItem, "TOTL2CACHESTD", "", "", false);
                totL2CacheStd = totL2CacheStd.trim();
                totL2CacheStdUnit = getAttributeValue(ddItem, "TOTL2CACHESTDUNIT", "", "", false);
                totL2CacheStdUnit = totL2CacheStdUnit.trim();
                totL2CacheStdInfo = totL2CacheStd + " " + totL2CacheStdUnit;
                totL2CacheStdInfo = setString(totL2CacheStdInfo, I_17);
                totAvailBay = getAttributeValue(ddItem, "TOTAVAILBAY", "", "", false);
                totAvailBay = totAvailBay.trim();
                totAvailBay = setString(totAvailBay, I_17);
                slotAvail = getAttributeValue(ddItem, "TOTAVAILCARDSLOT", "", "", false);
                slotAvail = slotAvail.trim();
                slotAvail = setString(slotAvail, I_17);

                aTS.add(noOfProcStd + "<:>" + totL2CacheStdInfo + "<:>" + totAvailBay + "<:>" + slotAvail);
            }//end of if(ddVector.size() > 0)
            else
            {
                aTS.add(setString(" ", I_17) + "<:>" + setString(" ", I_17) + "<:>" + setString(" ", I_17) + "<:>" + setString(" ", I_17));
            }
        }//end of if(wwseoVector.size() > 0)
        else
        {
            aTS.add(setString(" ", I_17) + "<:>" + setString(" ", I_17) + "<:>" + setString(" ", I_17) + "<:>" + setString(" ", I_17));
        }
    }

    /********************************************************************************
    *
    * @param featureItem EntityItem
    * @param aTS TreeSet
    */
    private void getMemoryForSEODescription(EntityItem featureItem, TreeSet aTS)
    {
        EntityItem memoryItem;
        String memoryCap;
        String capUnit;
        String memoryInfo;

        Vector memoryVector = getAllLinkedEntities(featureItem, "FEATUREMEMORY", "MEMORY");

        if(memoryVector.size() > 0)
        {
            for(int i = 0; i < memoryVector.size(); i++)
            {
                memoryItem = (EntityItem) memoryVector.get(i);
                memoryCap = getAttributeValue(memoryItem, "MEMRYCAP", "", "", false);
                memoryCap = memoryCap.trim();
                capUnit = getAttributeValue(memoryItem, "CAPUNIT", "", "", false);
                capUnit = capUnit.trim();
                memoryInfo = memoryCap + " " + capUnit;
                memoryInfo = setString(memoryInfo, I_17);

                aTS.add(memoryInfo);
            }//end of for(int i = 0; i < memoryVector.size(); i++)
        }//end of if(memoryVector.size() > 0)
        else
        {
            aTS.add(setString(" ", I_17));
        }

        memoryVector.clear();
        memoryVector = null;
    }

    /********************************************************************************
    *
    * @param featureItem EntityItem
    * @param aTS TreeSet
    */
    private void getPlanarForSEODescription(EntityItem featureItem, TreeSet aTS)
    {
        EntityItem planarItem;
        String ramSocketTot;
        String memoryRAMMax;
        String memoryRAMMaxUnit;
        String memoryRAMMaxInfo;
        String totCardSlot;
        String slotInfo;

        Vector planarVector = getAllLinkedEntities(featureItem, "FEATUREPLANAR", "PLANAR");

        if(planarVector.size() > 0)
        {
            for(int i = 0; i < planarVector.size(); i++)
            {
                planarItem = (EntityItem) planarVector.get(i);
                ramSocketTot = getAttributeValue(planarItem, "RAMSOCKETTOT", "", "", false);
                ramSocketTot = ramSocketTot.trim();
                if(ramSocketTot.length() < I_17)
                {
                    ramSocketTot = setString(ramSocketTot, I_17);
                }
                memoryRAMMax = getAttributeValue(planarItem, "MEMRYRAMMAX", "", "", false);
                memoryRAMMax = memoryRAMMax.trim();
                memoryRAMMaxUnit = getAttributeValue(planarItem, "MEMRYRAMMAXUNIT", "", "", false);
                memoryRAMMaxUnit = memoryRAMMaxUnit.trim();
                memoryRAMMaxInfo = memoryRAMMax + " " + memoryRAMMaxUnit;
                memoryRAMMaxInfo = setString(memoryRAMMaxInfo, I_17);
                totCardSlot = getAttributeValue(planarItem, "TOTCARDSLOT", "", "", false);
                totCardSlot = totCardSlot.trim();
                totCardSlot = setString(totCardSlot, I_17);
                slotInfo = getPlanarSlotInfo(planarItem);

                aTS.add(ramSocketTot + "<:>" + memoryRAMMaxInfo + "<:>" + totCardSlot + "<:>" + slotInfo);
            }//end of for(int i = 0; i < memoryVector.size(); i++)
        }//end of if(planarVector.size() > 0)
        else
        {
            aTS.add(setString(" ", I_17) + "<:>" + setString(" ", I_17) + "<:>" + setString(" ", I_17) + "<:>" + "<::>");
        }

        planarVector.clear();
        planarVector = null;
    }

    /********************************************************************************
    *
    * @param planarItem EntityItem
    */
    private String getPlanarSlotInfo(EntityItem planarItem)
    {
        EntityItem slotItem;
        String slotType;
        String slotTot;
        String slotInfo = "";

        Vector slotVector = getAllLinkedEntities(planarItem, "PLANARSLOT", "SLOT");

        if(slotVector.size() > 0)
        {
            boolean firstElement = true;

            for(int i = 0; i < slotVector.size(); i++)
            {
                slotItem = (EntityItem) slotVector.get(i);
                slotType = getAttributeValue(slotItem, "SLOTTYPE", "", "", false);
                slotType = slotType.trim();
                if(slotType.length() < I_21)
                {
                    slotType = setString(slotType, I_21);
                }
                slotTot = getAttributeValue(slotItem, "SLOTTOT", "", "", false);
                slotTot = slotTot.trim();
                slotTot = setString(slotTot, I_17);
                if(firstElement)
                {
                    slotInfo = slotType + "<::>" + slotTot;
                    firstElement = false;
                }
                else
                {
                    slotInfo = slotInfo + "<::>" + slotType + "<::>" + slotTot;
                }
            }//end of for(int i = 0; i < slotVector.size(); i++)
        }//end of if(slotVector.size() > 0)
        else
        {
            slotInfo = "<::>";
        }

        return slotInfo;
    }

    /********************************************************************************
    *
    * @param featureItem EntityItem
    * @param aTS TreeSet
    */
    private void getGrphAdapterForSEODescription(EntityItem featureItem, TreeSet aTS)
    {
        EntityItem grphAdapterItem;
        String grphAdapterType;
        String ramMax;
        String ramMaxUnit;
        String ramMaxInfo;

        Vector grphAdapterVector = getAllLinkedEntities(featureItem, "FEATUREGRPHADAPTER", "GRPHADAPTER");

        if(grphAdapterVector.size() > 0)
        {
            for(int i = 0; i < grphAdapterVector.size(); i++)
            {
                grphAdapterItem = (EntityItem) grphAdapterVector.get(i);
                grphAdapterType = getAttributeValue(grphAdapterItem, "GRPHADPTRTYPE", "", "", false);
                grphAdapterType = grphAdapterType.trim();
                if(grphAdapterType.length() < I_17)
                {
                    grphAdapterType = setString(grphAdapterType, I_17);
                }
                ramMax = getAttributeValue(grphAdapterItem, "RAMMAX", "", "", false);
                ramMax = ramMax.trim();
                ramMaxUnit = getAttributeValue(grphAdapterItem, "RAMMAXUNIT", "", "", false);
                ramMaxUnit = ramMaxUnit.trim();
                ramMaxInfo = ramMax + " " + ramMaxUnit;
                ramMaxInfo = setString(ramMaxInfo, I_17);

                aTS.add(grphAdapterType + "<:>" + ramMaxInfo);
            }//end of for(int i = 0; i < grphAdapterVector.size(); i++)
        }//end of if(grphAdapterVector.size() > 0)
        else
        {
            aTS.add(setString(" ", I_17) + "<:>" + setString(" ", I_17));
        }

        grphAdapterVector.clear();
        grphAdapterVector = null;
    }

    /********************************************************************************
    *
    * @param featureItem EntityItem
    * @param aTS TreeSet
    */
    private void getHDCForSEODescription(EntityItem featureItem, TreeSet aTS)
    {
        EntityItem hdcItem;
        String controllerCode;
        String ideController = setString(" ", I_17);
        String nonIDEController = setString(" ", I_17);

        Vector hdcVector = getAllLinkedEntities(featureItem, "FEATUREHDC", "HDC");

        if(hdcVector.size() > 0)
        {
            for(int i = 0; i < hdcVector.size(); i++)
            {
                hdcItem = (EntityItem) hdcVector.get(i);
                controllerCode = getAttributeFlagValue(hdcItem, "CNTRLR");
                if(null == controllerCode)
                {
                    aTS.add(setString(" ", I_17) + "<:>" + setString(" ", I_17));
                }
                else
                {
                    controllerCode = controllerCode.trim();
                    if(controllerCode.equals("0020"))
                    {
                        ideController = getAttributeValue(hdcItem, "CNTRLR", "", "", false);
                        ideController = ideController.trim();
                        if(ideController.length() < I_17)
                        {
                            ideController = setString(ideController, I_17);
                        }
                    }//end of if(controllerCode.equals("0020"))
                    else
                    {
                        nonIDEController = getAttributeValue(hdcItem, "CNTRLR", "", "", false);
                        nonIDEController = nonIDEController.trim();
                        if(nonIDEController.length() < I_17)
                        {
                            nonIDEController = setString(nonIDEController, I_17);
                        }
                    }//end of else
                }//end of else

                aTS.add(nonIDEController + "<:>" + ideController);
            }//end of for(int i = 0; i < hdcVector.size(); i++)
        }//end of if(hdcVector.size() > 0)
        else
        {
            aTS.add(setString(" ", I_17) + "<:>" + setString(" ", I_17));
        }

        hdcVector.clear();
        hdcVector = null;
    }

    /********************************************************************************
    *
    * @param featureItem EntityItem
    * @param aTS TreeSet
    */
    private void getHDDForSEODescription(EntityItem featureItem, TreeSet aTS)
    {
        EntityItem hddItem;
        String intrfc;
        String hddSize;
        String hddSizeUnit;
        String hddInfo;

        Vector hddVector = getAllLinkedEntities(featureItem, "FEATUREHDD", "HDD");

        if(hddVector.size() > 0)
        {
            for(int i = 0; i < hddVector.size(); i++)
            {
                hddItem = (EntityItem) hddVector.get(i);
                intrfc = getAttributeValue(hddItem, "INTRFC", "", "", false);
                intrfc = intrfc.trim();
                hddSize = getAttributeValue(hddItem, "HDDSZE", "", "", false);
                hddSize = hddSize.trim();
                hddSizeUnit = getAttributeValue(hddItem, "HDDSZEUNIT", "", "", false);
                hddSizeUnit = hddSizeUnit.trim();
                hddInfo = intrfc + " " + hddSize + " " + hddSizeUnit;
                hddInfo = hddInfo.trim();

                if(hddInfo.length() < I_17)
                {
                    hddInfo = setString(hddInfo, I_17);
                }

                aTS.add(hddInfo);
            }//end of for(int i = 0; i < hddVector.size(); i++)
        }//end of if(hddVector.size() > 0)
        else
        {
            aTS.add(setString(" ", I_17));
        }

        hddVector.clear();
        hddVector = null;
    }

    /********************************************************************************
    *
    * @param featureItem EntityItem
    * @param aTS TreeSet
    */
    private void getMECHPKGForSEODescription(EntityItem featureItem, TreeSet aTS)
    {
        EntityItem mechpkgItem;
        String totBay;
        String bayInfo;

        Vector mechpkgVector = getAllLinkedEntities(featureItem, "FEATUREMECHPKG", "MECHPKG");

        if(mechpkgVector.size() > 0)
        {
            for(int i = 0; i < mechpkgVector.size(); i++)
            {
                mechpkgItem = (EntityItem) mechpkgVector.get(i);
                totBay = getAttributeValue(mechpkgItem, "TOTBAY", "", "", false);
                totBay = totBay.trim();
                totBay = setString(totBay, I_17);
                bayInfo = getMechpkgBayInfo(mechpkgItem);

                aTS.add(totBay + "<:>" + bayInfo);
            }//end of for(int i = 0; i < mechpkgVector.size(); i++)
        }//end of if(mechpkgVector.size() > 0)
        else
        {
            aTS.add(setString(" ", I_17) + "<:>" + "<::>");
        }

        mechpkgVector.clear();
        mechpkgVector = null;
    }

    /********************************************************************************
    *
    * @param mechpkgItem EntityItem
    */
    private String getMechpkgBayInfo(EntityItem mechpkgItem)
    {
        EntityItem bayItem;
        String bayType;
        String bayTot;
        String bayInfo = "";

        Vector bayVector = getAllLinkedEntities(mechpkgItem, "MECHPKGBAY", "BAY");

        if(bayVector.size() > 0)
        {
            boolean firstElement = true;

            for(int i = 0; i < bayVector.size(); i++)
            {
                bayItem = (EntityItem) bayVector.get(i);
                bayType = getAttributeValue(bayItem, "BAYTYPE", "", "", false);
                bayType = bayType.trim();
                if(bayType.length() < I_21)
                {
                    bayType = setString(bayType, I_21);
                }
                bayTot = getAttributeValue(bayItem, "BAYTOT", "", "", false);
                bayTot = bayTot.trim();
                bayTot = setString(bayTot, I_17);
                if(firstElement)
                {
                    bayInfo = bayType + "<::>" + bayTot;
                    firstElement = false;
                }
                else
                {
                    bayInfo = bayInfo + "<::>" + bayType + "<::>" + bayTot;
                }
            }//end of for(int i = 0; i < bayVector.size(); i++)
        }//end of if(bayVector.size() > 0)
        else
        {
            bayInfo = "<::>";
        }

        return bayInfo;
    }

    /********************************************************************************
    *
    * @param wwseoVector Vector
    * @param aTS TreeSet
    */
    private void getBaysAvailForSEODescription(Vector wwseoVector, TreeSet aTS)
    {
        EntityItem wwseoItem;
        Vector baysAvailVector;
        EntityItem baysAvailItem;
        String bayType;
        String bayAvail;

        //Relationship between WWSEO and LSEO is 1 to n.
        if(wwseoVector.size() > 0)
        {
            wwseoItem = (EntityItem) wwseoVector.get(0);
            baysAvailVector = getAllLinkedEntities(wwseoItem, "WWSEOBAYSAVAIL", "BAYSAVAIL");
            if(baysAvailVector.size() > 0)
            {
                for(int i = 0; i < baysAvailVector.size(); i++)
                {
                    baysAvailItem = (EntityItem) baysAvailVector.get(i);
                    bayType = getAttributeValue(baysAvailItem, "BAYTYPE", "", "", false);
                    bayType = bayType.trim();
                    if(bayType.length() < I_21)
                    {
                        bayType = setString(bayType, I_21);
                    }
                    bayAvail = getAttributeValue(baysAvailItem, "BAYAVAIL", "", "", false);
                    bayAvail = bayAvail.trim();
                    bayAvail = setString(bayAvail, I_17);
                    aTS.add(bayType + "<::>" + bayAvail);
                }
            }//end of if(baysAvailVector.size() > 0)
            else
            {
                aTS.add("<::>");
            }
        }//end of if(wwseoVector.size() > 0)
        else
        {
            aTS.add("<::>");
        }
    }

    /********************************************************************************
    *
    * @param featureItem EntityItem
    * @param aTS TreeSet
    */
    private void getSysMgAdapterForSEODescription(EntityItem featureItem, TreeSet aTS)
    {
        EntityItem sysMgAdptrItem;
        String sysMgFeat;

        Vector sysMgAdptrVector = getAllLinkedEntities(featureItem, "FEATURESYSMGADAPTR", "SYSMGADPTR");

        if(sysMgAdptrVector.size() > 0)
        {
            for(int i = 0; i < sysMgAdptrVector.size(); i++)
            {
                sysMgAdptrItem = (EntityItem) sysMgAdptrVector.get(i);
                sysMgFeat = getAttributeValue(sysMgAdptrItem, "SYSMGFEAT", ", ", "", false);
                sysMgFeat = sysMgFeat.trim();
                if(sysMgFeat.length() < I_17)
                {
                    sysMgFeat = setString(sysMgFeat, I_17);
                }

                aTS.add(sysMgFeat);
            }//end of for(int i = 0; i < sysMgAdptrVector.size(); i++)
        }//end of if(sysMgAdptrVector.size() > 0)
        else
        {
            aTS.add(setString(" ", I_17));
        }

        sysMgAdptrVector.clear();
        sysMgAdptrVector = null;
    }

    /********************************************************************************
    *
    * @param featureItem EntityItem
    * @param aTS TreeSet
    */
    private void getNICForSEODescription(EntityItem featureItem, TreeSet aTS)
    {
        EntityItem nicItem;
        String nicDesc;

        Vector nicVector = getAllLinkedEntities(featureItem, "FEATURENIC", "NIC");

        if(nicVector.size() > 0)
        {
            for(int i = 0; i < nicVector.size(); i++)
            {
                nicItem = (EntityItem) nicVector.get(i);
                nicDesc = getAttributeValue(nicItem, "NICDESC", ", ", "", false);
                nicDesc = nicDesc.trim();
                if(nicDesc.length() < I_17)
                {
                    nicDesc = setString(nicDesc, I_17);
                }

                aTS.add(nicDesc);
            }//end of for(int i = 0; i < nicVector.size(); i++)
        }//end of if(nicVector.size() > 0)
        else
        {
            aTS.add(setString(" ", I_17));
        }

        nicVector.clear();
        nicVector = null;
    }

    /********************************************************************************
    *
    * @param featureItem EntityItem
    * @param v Vector
    */
    private void getOptcalDvcForSEODescription(EntityItem featureItem, Vector v)
    {
        EntityItem optcalDvcItem;
        String optcalDriveType;

        Vector optcalDvcVector = getAllLinkedEntities(featureItem, "FEATUREOPTCALDVC", "OPTCALDVC");

        for(int i = 0; i < optcalDvcVector.size(); i++)
        {
            optcalDvcItem = (EntityItem) optcalDvcVector.get(i);
            optcalDriveType = getAttributeValue(optcalDvcItem, "OPTCALDRIVETYPE", "", "", false);
            optcalDriveType = optcalDriveType.trim();

            if(optcalDriveType.length() < I_22)
            {
                optcalDriveType = setString(optcalDriveType, I_22);
            }

            v.add(optcalDriveType);
        }//end of for(int i = 0; i < optcalDvcVector.size(); i++)
    }

    /********************************************************************************
    *
    * @param v Vector
    * @param aTS TreeSet
    */
    private void getOptcalDvcForSEODescription(Vector v, TreeSet aTS)
    {
        Iterator itr;
        TreeSet ts = new TreeSet();

        if(v.size() > 0)
        {
            for(int i = 0; i < v.size(); i++)
            {
                ts.add(v.get(i));
            }

            itr = ts.iterator();
            while(itr.hasNext())
            {
                String optcalDriveType = (String) itr.next();
                int n = 0;
                for(int j = 0; j < v.size(); j++)
                {
                    String str = (String) v.get(j);
                    if(optcalDriveType.equals(str))
                    {
                        n++;
                    }
                }

                aTS.add(optcalDriveType + "<::>" + setString(Integer.toString(n), I_17));
            }//end of while(itr.hasNext())
        }//end of if(v.size() > 0)
        else
        {
            aTS.add(setString(" ", I_22) + "<::>" + setString(" ", I_17));
        }
    }

    /********************************************************************************
    *
    * @param featureItem EntityItem
    * @param aTS TreeSet
    */
    private void getPwrSupplyForSEODescription(EntityItem featureItem, TreeSet aTS)
    {
        EntityItem powerSupplyItem;
        String power;
        String powerUnit;
        String powerInfo;
        String powerSupplyType;

        Vector powerSupplyVector = getAllLinkedEntities(featureItem, "FEATUREPWRSUPPLY", "PWRSUPPLY");

        if(powerSupplyVector.size() > 0)
        {
            for(int i = 0; i < powerSupplyVector.size(); i++)
            {
                powerSupplyItem = (EntityItem) powerSupplyVector.get(i);
                power = getAttributeValue(powerSupplyItem, "POWR", "", "", false);
                power = power.trim();
                powerUnit = getAttributeValue(powerSupplyItem, "POWRUNIT", "", "", false);
                powerUnit = powerUnit.trim();
                powerInfo = power + " " + powerUnit;
                powerInfo = setString(powerInfo, I_17);
                powerSupplyType = getAttributeValue(powerSupplyItem, "POWRSUPLYTYPE", "", "", false);
                powerSupplyType = powerSupplyType.trim();
                if(powerSupplyType.length() < I_17)
                {
                    powerSupplyType = setString(powerSupplyType, I_17);
                }

                aTS.add(powerInfo + "<:>" + powerSupplyType);
            }//end of for(int i = 0; i < powerSupplyVector.size(); i++)
        }//end of if(prowerSupplyVector.size() > 0)
        else
        {
            aTS.add(setString(" ", I_17) + "<:>" + setString(" ", I_17));
        }

        powerSupplyVector.clear();
        powerSupplyVector = null;
    }

    /********************************************************************************
    *
    * @param ts TreeSet
    */
    private TreeSet combineInfo1(TreeSet ts)
    {
        TreeSet aTS1 = new TreeSet();
        TreeSet aTS2 = new TreeSet();

        Iterator tsItr = ts.iterator();
        while(tsItr.hasNext())
        {
            String str = (String) tsItr.next();
            String key = parseString(str, 1, "<:>") + "<:>" + parseString(str, 2, "<:>")+ "<:>" + parseString(str, 3, "<:>");
            aTS1.add(key);
        }//end of while(tsItr.hasNext())

        tsItr = aTS1.iterator();
        while(tsItr.hasNext())
        {
            String str = (String) tsItr.next();

            String info = combineInfo1(str, ts);
            aTS2.add(info);

        }//end of while(tsItr.hasNext())

        ts.clear();
        ts = null;

        return aTS2;
    }

    /********************************************************************************
    *
    * @param str String
    * @param ts TreeSet
    */
    private String combineInfo1(String str, TreeSet ts)
    {
        String info = "";

        Iterator itr = ts.iterator();
        while(itr.hasNext())
        {
            String aStr1 = (String) itr.next();
            String aStr2 = parseString(aStr1, 1, "<:>") + "<:>" + parseString(aStr1, 2, "<:>") + "<:>" + parseString(aStr1, 3, "<:>");
            if(str.equals(aStr2))
            {
                String aStr3 = parseString(aStr1, 4, "<:>");
                int n = countDelims(aStr3, "<::>");
                String type = "";
                String tot = "";

                n = n + 1;

                if(n > 1)
                {
                    for(int i = 1; i <= n; i++)
                    {
                        if(i%2 != 0)
                        {
                            type = parseString(aStr3, i, "<::>");
                        }
                        else
                        {
                            tot = parseString(aStr3, i, "<::>");
                            if((!type.equals("")) && (!tot.equals("")))
                            {
                                if(info.equals(""))
                                {
                                    info = type + "<::>" + tot;
                                }
                                else
                                {
                                    info = info + "<::>" + type + "<::>" + tot;
                                }
                            }
                        }
                    }//end of for(int i = 1; i <= n; i++)
                }//end of if(n > 1)
            }//end of if(str.equals(aStr2))
        }//end of while(itr.hasNext())

        info = str + "<:>" + info;

        return info;
    }

    /********************************************************************************
    *
    * @param ts TreeSet
    */
    private TreeSet combineInfo2(TreeSet ts)
    {
        TreeSet aTS1 = new TreeSet();
        TreeSet aTS2 = new TreeSet();

        Iterator tsItr = ts.iterator();
        while(tsItr.hasNext())
        {
            String str = (String) tsItr.next();
            String key = parseString(str, 1, "<:>");
            aTS1.add(key);
        }//end of while(tsItr.hasNext())

        tsItr = aTS1.iterator();
        while(tsItr.hasNext())
        {
            String str = (String) tsItr.next();

            String info = combineInfo2(str, ts);
            aTS2.add(info);

        }//end of while(tsItr.hasNext())

        ts.clear();
        ts = null;

        return aTS2;
    }

    /********************************************************************************
    *
    * @param str String
    * @param ts TreeSet
    */
    private String combineInfo2(String str, TreeSet ts)
    {
        String info = "";

        Iterator itr = ts.iterator();
        while(itr.hasNext())
        {
            String aStr1 = (String) itr.next();
            String aStr2 = parseString(aStr1, 1, "<:>");
            if(str.equals(aStr2))
            {
                String aStr3 = parseString(aStr1, 2, "<:>");
                int n = countDelims(aStr3, "<::>");
                String type = "";
                String tot = "";

                n = n + 1;

                if(n > 1)
                {
                    for(int i = 1; i <= n; i++)
                    {
                        if(i%2 != 0)
                        {
                            type = parseString(aStr3, i, "<::>");
                        }
                        else
                        {
                            tot = parseString(aStr3, i, "<::>");
                            if((!type.equals("")) && (!tot.equals("")))
                            {
                                if(info.equals(""))
                                {
                                    info = type + "<::>" + tot;
                                }
                                else
                                {
                                    info = info + "<::>" + type + "<::>" + tot;
                                }
                            }
                        }
                    }//end of for(int i = 1; i <= n; i++)
                }//end of if(n > 1)
            }//end of if(str.equals(aStr2))
        }//end of while(itr.hasNext())

        info = str + "<:>" + info;

        return info;
    }

    /********************************************************************************
    *
    * @param geo String
    * @param mtm String
    * @param ts TreeSet
    * @param arrayTS TreeSet []
    */
    private void merge(String geo, String mtm, TreeSet ts, TreeSet [] arrayTS)
    {
        int n1 = arrayTS[0].size();
        int n2 = arrayTS[1].size();
        int n3 = arrayTS[2].size();
        int n4 = arrayTS[3].size();

        Object [] arrayObj1 = arrayTS[0].toArray();
        Object [] arrayObj2 = arrayTS[1].toArray();
        Object [] arrayObj3 = arrayTS[2].toArray();
        Object [] arrayObj4 = arrayTS[3].toArray();

        String ddMemory = "";

        int max = n1;

        if(n2 > max)
        {
            max = n2;
        }
        if(n3 > max)
        {
            max = n3;
        }
        if(n4 > max)
        {
            max = n4;
        }

        if(n2 > 0)
        {
            ddMemory = (String) arrayObj2[0];
        }
        else
        {
            ddMemory = "   <:>  ";
        }

        for(int i = 0; i < max; i++)
        {
            String str = geo + "<:>" + mtm;
            if(i < n1)
            {
                str = str + "<:>" + (String) arrayObj1[i];
            }
            else
            {
                str = str + "<:>" + "   <:>   <:>  <:>  ";
            }

            if(i < n2)
            {
                str = str + "<:>" + (String) arrayObj2[i];
            }
            else
            {
                str = str + "<:>" + "   <:>  ";
            }

            if(i < n3)
            {
                str = str + "<:>" + (String) arrayObj3[i];
            }
            else
            {
                str = str + "<:>" + setString(" ", I_12);
            }

            if(i < n4)
            {
                str = str + "<:>" + (String) arrayObj4[i];
            }
            else
            {
                str = str + "<:>" + setString(" ", I_21);
            }

            ts.add(str);
        }//end of for(int i = 0; i < max; i++)
    }

    /********************************************************************************
    * retrieveAnswer()
    *
    * @param fmtType boolean
    * @param outSB StringBuffer
    */
    public void retrieveAnswer(boolean fmtType, StringBuffer outSB)
    {
//      setFileBreakTag("Product Number", "PRODNUM.txt");
        outSB.append(".* <!--STARTFILEBREAKFORMAIL:PRODNUM.txt: FOR :Product Number-->" + NEWLINE);
        retrieveProductNumber(fmtType, outSB);
        outSB.append(".* <!--STARTFILEBREAKFORMAIL:FEATCONV.txt: FOR :Conversions-->" + NEWLINE);
        outSB.append(".* <pre>" + NEWLINE);
        retrievePNMTMConversions(fmtType, outSB);
        retrievePNModelConversions(fmtType, outSB);
        if(FORMAT1 == format)
        {
            if(annType.equals(ANNOUNCEMENT_TYPE_NEW))
            {
                retrievePNFeatureConversionsFormat1(fmtType, outSB);
            }
            else if(annType.equals(ANNOUNCEMENT_TYPE_WITHDRAW))
            {
                retrievePNFeatureConversionsForWithdrawFormat1(fmtType, outSB);
            }
        }
        else if(FORMAT2 == format || FORMAT3 == format)
        {
            if(annType.equals(ANNOUNCEMENT_TYPE_NEW))
            {
                retrievePNFeatureConversionsFormat2(fmtType, outSB);
            }
            else if(annType.equals(ANNOUNCEMENT_TYPE_WITHDRAW))
            {
                retrievePNFeatureConversionsForWithdrawFormat2(fmtType, outSB);
            }
        }
        outSB.append(".* </pre>" + NEWLINE);

        if(annType.equals(ANNOUNCEMENT_TYPE_NEW))
        {
//         setFileBreakTag("Charges", "CHARGES.txt");
            outSB.append(".* <!--STARTFILEBREAKFORMAIL:CHARGES.txt: FOR :Charges-->" + NEWLINE);
            retrieveCharges(fmtType, outSB);
            outSB.append(".* <!--STARTFILEBREAKFORMAIL:XCHARGES.txt: FOR :XCharges-->" + NEWLINE);
            retrieveXCharges(fmtType, outSB);
//         setFileBreakTag("Sales Manual", "SALESMAN.txt");
            outSB.append(".* <!--STARTFILEBREAKFORMAIL:SALESMAN.txt: FOR :Sales Manual-->" + NEWLINE);
            retrieveSalesManual(fmtType, outSB);
//         setFileBreakTag("Supported Devices", "EXTDEVICE.txt");
            outSB.append(".* <!--STARTFILEBREAKFORMAIL:EXTDEVICE.txt: FOR :Supported Devices-->" + NEWLINE);
            retrieveSupportedDevices(fmtType, outSB);
//         setFileBreakTag("Feature Matrix", "MATRIX.txt");
            outSB.append(".* <!--STARTFILEBREAKFORMAIL:MATRIX.txt: FOR :Feature Matrix-->" + NEWLINE);
            retrieveFeatureMatrix(fmtType, outSB);
            if(brand.equals(XSERIES))
            {
                outSB.append(".* <!--STARTFILEBREAKFORMAIL:SEOTABLE.txt: FOR :SEO Table-->" + NEWLINE);
                retrieveSEOTable(outSB);
                outSB.append(".* <!--STARTFILEBREAKFORMAIL:SEODESC.txt: FOR :SEO Description-->" + NEWLINE);
                retrieveSEODescription(outSB);
            }
        }
//      setFileBreakTag("Matrix RFA Error", "RFAERROR.txt");
        outSB.append(".* <!--STARTFILEBREAKFORMAIL:RFAERROR.txt: FOR :Matrix RFA Error-->" + NEWLINE);
        retrieveFeatureMatrixError(outSB);
    }

    /********************************************************************************
    *
    *
    * @param fmtType boolean
    * @param outSB StringBuffer
    */
    private void retrieveProductNumber(boolean fmtType, StringBuffer outSB)
    {
        outSB.append(".* <pre>" + NEWLINE);
        outSB.append(".* " + myDate() + NEWLINE);
        outSB.append(".* " + inventoryGroup + NEWLINE);
        //if(FORMAT1 == format)
        //   outSB.append(":h2.Product Number\n");

        if((productNumber_NewModels_TM.size()  + productNumber_NewFC_TM.size() +
                productNumber_ExistingFC_TM.size() + productNumber_MTM_Conversions_TM.size() +
                productNumber_Model_Conversions_TM.size() + productNumber_Feature_Conversions_TM.size() +
                productNumber_NewModels_NewFC_TM.size() + productNumber_NewModels_ExistingFC_TM.size() +
                productNumber_ExistingModels_NewFC_TM.size() + productNumber_ExistingModels_ExistingFC_TM.size())  > 0)
        {
            log("annType = " + annType);
            log("productNumber_NewModels_TM.size() = " + productNumber_NewModels_TM.size());
            log("productNumber_NewFC_TM.size() = " + productNumber_NewFC_TM.size());
            log("productNumber_ExistingFC_TM.size() = " + productNumber_ExistingFC_TM.size());
            log("productNumber_NewModels_NewFC_TM.size() = " + productNumber_NewModels_NewFC_TM.size());
            log("productNumber_NewModels_ExistingFC_TM.size() = " + productNumber_NewModels_ExistingFC_TM.size());
            log("productNumber_ExistingModels_NewFC_TM.size() = " + productNumber_ExistingModels_NewFC_TM.size());
            log("productNumber_ExistingModels_ExistingFC_TM.size() = " + productNumber_ExistingModels_ExistingFC_TM.size());

            if(FORMAT1 == format)
            {
                retrievePNNewModelsFormat1(fmtType, outSB);
            }
            else if(FORMAT2 == format || FORMAT3 == format)
            {
                retrievePNNewModelsFormat2(fmtType, outSB);
            }

         //if((productNumber_NewFC_TM.size() + productNumber_ExistingFC_TM.size())  > 0)
         //{
         //   if(FORMAT1 == format)
         //      outSB.append(":h3.Features\n");
         //}

            if(annType.equals(ANNOUNCEMENT_TYPE_WITHDRAW))
            {
                if(FORMAT1 == format)
                {
                    retrievePNFeaturesWithdrawFormat1(fmtType, outSB);
                }
                else if(FORMAT2 == format || FORMAT3 == format)
                {
                    retrievePNFeaturesWithdrawFormat2(fmtType, outSB);
                }
            }
            else
            {
                if(FORMAT1 == format)
                {
                    retrievePNFeaturesFormat1(NEWFC, productNumber_NewFC_TM, fmtType, outSB);
                    retrievePNFeaturesFormat1(EXISTINGFC, productNumber_ExistingFC_TM, fmtType, outSB);
                }
                else if(FORMAT2 == format || FORMAT3 == format)
                {
                    retrievePNFeaturesFormat2(NEWMODELS, NEWFC, productNumber_NewModels_NewFC_TM, fmtType, outSB);
                    retrievePNFeaturesFormat2(NEWMODELS, EXISTINGFC, productNumber_NewModels_ExistingFC_TM, fmtType, outSB);
                    retrievePNFeaturesFormat2(EXISTINGMODELS, NEWFC, productNumber_ExistingModels_NewFC_TM, fmtType, outSB);
                    retrievePNFeaturesFormat2(EXISTINGMODELS, EXISTINGFC, productNumber_ExistingModels_ExistingFC_TM, fmtType, outSB);
                }
            }
        }
        else
        {
            if(fmtType == GMLFORMAT)
            {
                outSB.append(":p.No answer data found for Product Number section." + NEWLINE);
            }
            else
            {
                outSB.append("<p>No answer data found for Product Number section.</p>" + NEWLINE);
            }
        }
        outSB.append(".* </pre>" + NEWLINE);
    }

    /********************************************************************************
    *
    *
    * @param fmtType boolean
    * @param outSB StringBuffer
    */
    private void retrievePNNewModelsFormat1(boolean fmtType, StringBuffer outSB)
    {
        if(productNumber_NewModels_TM.size() > 0)
        {
            String prevGeo = "";
            String currentGeo = "";

            if(FORMAT1 == format)
            {
                Set tmSet;
                Iterator tmItr;
                if(annType.equals(ANNOUNCEMENT_TYPE_NEW))
                {
                    outSB.append(":h3.Models" + NEWLINE);
                    outSB.append(":xmp." + NEWLINE);
                    outSB.append(".kp off" + NEWLINE  + NEWLINE);
                    outSB.append("                              Type         Model" + NEWLINE);
                    outSB.append("Description                   Number       Number       CSU" + NEWLINE);
                    outSB.append("----------------------------  ------       ------       ---" + NEWLINE);
                }
                else if(annType.equals(ANNOUNCEMENT_TYPE_WITHDRAW))
                {
                    outSB.append(":h3.Model Withdrawals" + NEWLINE);
                    outSB.append(":p.The following Machine Type Models are being withdrawn:" + NEWLINE);
                    outSB.append(":xmp." + NEWLINE);
                    outSB.append(".kp off" + NEWLINE + NEWLINE);
                    outSB.append("                              Type         Model" + NEWLINE);
                    outSB.append("Description                   Number       Number" + NEWLINE);
                    outSB.append("----------------------------  ------       ------" + NEWLINE);
                }

                tmSet = productNumber_NewModels_TM.keySet();
                tmItr = tmSet.iterator();
                while(tmItr.hasNext())
                {
                    String key = (String) tmItr.next();
                    currentGeo = (String) productNumber_NewModels_TM.get(key);
                    setGeoTags(prevGeo, currentGeo, fmtType, outSB);
                    //outSB.append(" ");
                    outSB.append(parseString(key, 3, "<:>"));
                    outSB.append("   ");
                    outSB.append(parseString(key, 1, "<:>"));
                    outSB.append("         ");
                    outSB.append(parseString(key, 2, "<:>"));
                    if(annType.equals(ANNOUNCEMENT_TYPE_NEW))
                    {
                        outSB.append("         ");
                        outSB.append(parseString(key, 4, "<:>"));
                    }
                    outSB.append(NEWLINE);

                    prevGeo = currentGeo;
                }//end of while(tmItr.hasNext())
                if(!currentGeo.equals("WW"))
                {
                    bldEndGeoTags(currentGeo, fmtType, outSB);
                }
                if(fmtType == GMLFORMAT)
                {
                    outSB.append(":exmp." + NEWLINE + NEWLINE);
                }
            }//end of if(FORMAT1 == format)
        }//end of if(productNumber_NewModels_TM.size() > 0)
    }

    /********************************************************************************
    *
    *
    * @param fmtType boolean
    * @param outSB StringBuffer
    */
    private void retrievePNNewModelsFormat2(boolean fmtType, StringBuffer outSB)
    {
        String prevGeo = "";
        String currentGeo = "";

        if(productNumber_NewModels_TM.size() > 0)
        {
            if((FORMAT2 == format || FORMAT3 == format) && (annType.equals(ANNOUNCEMENT_TYPE_WITHDRAW)))
            {
                int nLines;
                Set tmSet;
                Iterator tmItr;

                if(brand.equals(PSERIES))
                {
                    outSB.append(":p.The following RS/6000 or pSeries Machine Type Models are being withdrawn:" + NEWLINE);
                }
                else if(brand.equals(XSERIES))
                {
                    outSB.append(":p.The following xSeries Machine Type Models are being withdrawn:" + NEWLINE);
                }
                //else if(brand.equals(ZSERIES))
                //   outSB.append(":p.The following S/390 or zSeries Machine Type Models are being withdrawn:\n");
                else if(brand.equals(TS))
                {
                    outSB.append(":p.The following Total Storage Machine Type Models are being withdrawn:" + NEWLINE);
                }

                //outSB.append(":xmp." + NEWLINE);
                outSB.append(".RH ON" + NEWLINE);
                outSB.append(".fo off" + NEWLINE);
                outSB.append(".in 2" + NEWLINE);
                //outSB.append(".kp off\n");
                outSB.append("Description                                            MT  Model" + NEWLINE);
                outSB.append("----------------------------------------------------- ---- -----" + NEWLINE);
                outSB.append(".fo on" + NEWLINE);
                outSB.append(".RH OFF" + NEWLINE);
                outSB.append(":xmp." + NEWLINE);
                outSB.append(".kp off" + NEWLINE);
                //outSB.append(":exmp." + NEWLINE); According to OIM3.0b specs: remove :xmp. and :exmp. pair
                nLines = 0;
                tmSet = productNumber_NewModels_TM.keySet();
                tmItr = tmSet.iterator();
                while(tmItr.hasNext())
                {
                    String key = (String) tmItr.next();
                    String [] stringLines = extractStringLines(parseString(key, 3, "<:>"), I_53);
                    currentGeo = (String) productNumber_NewModels_TM.get(key);
                    nLines = 0;
                    setGeoTags2(prevGeo, currentGeo, fmtType, outSB);
                    //if(brand.equals(PSERIES))
                    //{
                    //    outSB.append("RS/6000 or pSeries");
                    //    outSB.append(setString("", I_35));
                    //    outSB.append(" ");
                    //    outSB.append(parseString(key, 1, "<:>"));
                    //    outSB.append("-");
                    //    outSB.append(parseString(key, 2, "<:>"));
                    //    outSB.append(NEWLINE);
                    //}
                    //else if(brand.equals(TS) || (brand.equals(XSERIES)))
                    //{
                    //    do
                    //    {
                    //        if(0 == nLines)
                    //        {
                    //            outSB.append(setString(stringLines[nLines], I_53));
                    //            outSB.append(" ");
                    //            outSB.append(parseString(key, 1, "<:>"));
                    //            outSB.append("-");
                    //            outSB.append(parseString(key, 2, "<:>"));
                    //            outSB.append(NEWLINE);
                    //            nLines++;
                    //        }//end of if(false == firstLine)
                    //        else
                    //        {
                    //            outSB.append(setString(stringLines[nLines], I_53));
                    //            outSB.append(NEWLINE);
                    //            nLines++;
                    //        }
                    //    }while(nLines < stringLines.length);
                    //}//end of else if(brand.equals(TS) || (brand.equals(XSERIES)))

                    do
                    {
                        if(0 == nLines)
                        {
                            outSB.append(setString(stringLines[nLines], I_53));
                            outSB.append(" ");
                            outSB.append(parseString(key, 1, "<:>"));
                            outSB.append("-");
                            outSB.append(parseString(key, 2, "<:>"));
                            outSB.append(NEWLINE);
                            nLines++;
                        }//end of if(false == firstLine)
                        else
                        {
                            outSB.append(setString(stringLines[nLines], I_53));
                            outSB.append(NEWLINE);
                            nLines++;
                        }
                    }while(nLines < stringLines.length);

                    prevGeo = currentGeo;
                }//end of while(tmItr.hasNext())
                if(!currentGeo.equals("WW"))
                {
                    bldEndGeoTags(currentGeo, fmtType, outSB);
                }
                if(fmtType == GMLFORMAT)
                {
                    outSB.append(":exmp." + NEWLINE);
                    outSB.append(".RH CANCEL" + NEWLINE + NEWLINE);
                }
            }//end of else if(FORMAT2 == format)
        }//end of if(productNumber_NewModels_TM.size() > 0)
    }

    /********************************************************************************
    *
    *
    * @param fType int
    * @param tm TreeMap
    * @param fmtType boolean
    * @param outSB StringBuffer
    */
    private void retrievePNFeaturesFormat1(int fType, TreeMap tm, boolean fmtType, StringBuffer outSB)
    {
        TreeMap productNumber_Features_TM = new TreeMap();

        //Sort by feature code first
        Set tmSet = tm.keySet();
        Iterator tmItr = tmSet.iterator();
        while(tmItr.hasNext())
        {
            String key1 = (String) tmItr.next();
            String key2 = parseString(key1, 2, "<:>") + "<:>" + parseString(key1, 1, "<:>") + "<:>" + parseString(key1, 3, "<:>") + "<:>" +
                parseString(key1, 4, "<:>") + "<:>" + parseString(key1, 5, "<:>") + "<:>" + parseString(key1, 6, "<:>") + "<:>" +
                parseString(key1, 7, "<:>") + "<:>" + parseString(key1, 8, "<:>");
            productNumber_Features_TM.put(key2, tm.get(key1));
        }//end of while(tmItr.hasNext())

        if(productNumber_Features_TM.size() > 0)
        {
            String prevGeo = "";
            String currentGeo = "";
            String prevFeature = "";
            String currentFeature = "";
            String prevMT = "";
            String currentMT ="";

            switch(fType)
            {
            case NEWFC:
                outSB.append(":h3.New Features" + NEWLINE);
                //outSB.append(":p.The following are newly announced features on the specified\n");
                //outSB.append("models.\n");
                outSB.append(":xmp." + NEWLINE);
                outSB.append(".kp off" + NEWLINE + NEWLINE);
                break;

            case EXISTINGFC:
                outSB.append(":h3.Existing Features" + NEWLINE);
                //outSB.append(":p.The following features are already announced, for the specified\n");
                //outSB.append("machine type, and are now orderable on additional models.\n");
                outSB.append(":xmp." + NEWLINE);
                outSB.append(".kp off" + NEWLINE + NEWLINE);
                break;

            default:
                break;
            }//end of switch(fType)

            if(FORMAT1 == format)
            {
                outSB.append("                                                    Initial/" + NEWLINE);
                outSB.append("                                                    MES/" + NEWLINE);
                outSB.append("                                                    Both/" + NEWLINE);
                outSB.append("Description                   Type  Model  Feature  Support  CSU" + NEWLINE);
                outSB.append("----------------------------  ----  -----  -------  -------  ---" + NEWLINE);
                tmSet = productNumber_Features_TM.keySet();
                tmItr = tmSet.iterator();
                while(tmItr.hasNext())
                {
                    String key = (String) tmItr.next();
                    currentGeo = (String) productNumber_Features_TM.get(key);
                    currentFeature = parseString(key, 1, "<:>");
                    currentMT = parseString(key, 2, "<:>");
                    setGeoTags(prevGeo, currentGeo, fmtType, outSB);
                    if(parseString(key, 8, "<:>").length() > 0)
                    {
                        if(!currentFeature.equals(prevFeature))
                        {
                            String [] arrayStr1 = getStringTokens(parseString(key, 8, "<:>"), NEWLINE);
                            for(int i = 0; i < arrayStr1.length; i++)
                            {
                                if(arrayStr1[i].length() > I_58)
                                {
                                    String [] arrayStr2 = extractStringLines(arrayStr1[i], I_58);
                                    for(int j = 0; j < arrayStr2.length; j++)
                                    {
                                        outSB.append(":hp2." + arrayStr2[j] + ":ehp2." + NEWLINE);
                                    }
                                }
                                else
                                {
                                    outSB.append(":hp2." + arrayStr1[i] + ":ehp2." + NEWLINE);
                                }
                            }
                        }//end of if(!currentFeature.equals(prevFeature)
                    }
                    //if(!currentFeature.equals(prevFeature))
                    //   outSB.append(parseString(key, 4));
                    //else
                    //   outSB.append(setString("", 28));

                    //If the same feature code is used on more than one machine type
                    //and model then print the feature code description, machine type,
                    //feature code, order code and customer setup each time it is used
                    outSB.append(parseString(key, 4, "<:>"));

                    outSB.append("  ");

                    //if(!currentMT.equals(prevMT) || !currentFeature.equals(prevFeature))
                    //   outSB.append(parseString(key, 2));
                    //else
                    //   outSB.append(setString("", 4));

                    //If the same feature code is used on more than one machine type
                    //and model then print the feature code description, machine type,
                    //feature code, order code and customer setup each time it is used
                    outSB.append(parseString(key, 2, "<:>"));

                    outSB.append("   ");
                    outSB.append(parseString(key, 3, "<:>"));
                    outSB.append("     ");

                    //if(!currentFeature.equals(prevFeature))
                    //   outSB.append(parseString(key, 1));
                    //else
                    //   outSB.append(setString("", 4));

                    //If the same feature code is used on more than one machine type
                    //and model then print the feature code description, machine type,
                    //feature code, order code and customer setup each time it is used
                    outSB.append(parseString(key, 1, "<:>"));

                    outSB.append("   ");
                    outSB.append(parseString(key, 5, "<:>"));
                    outSB.append("  ");
                    outSB.append(parseString(key, 6, "<:>"));
                    outSB.append(NEWLINE);

                    prevGeo = currentGeo;
                    prevFeature = currentFeature;
                    prevMT = currentMT;
                }//end of while(tmItr.hasNext())
                if(!currentGeo.equals("WW"))
                {
                    bldEndGeoTags(currentGeo, fmtType, outSB);
                }

                outSB.append(":exmp." + NEWLINE + NEWLINE);
                //if(fmtType == GMLFORMAT)
                //{
                //   outSB.append(":p.:hp2.US, LA, CAN--->:ehp2.\n");
                //   outSB.append(":p.:hp2.Feature Removal Prices::ehp2. Feature removals not associated\n");
                //   outSB.append("with MES upgrades are available for a charge.\n");
                //   outSB.append(".br;:hp2.<---US, LA, CAN:ehp2.\n\n");
                //}
                //else
                //{
                //   outSB.append("<i>US, LA, CAN---></i>");
                //   outSB.append("<p><i>Feature Removal Prices</i> Feature removals not associated ");
                //   outSB.append("with MES upgrades are available for a charge.</p>");
                //   outSB.append("<i><---US, LA, CAN</i>");
                //}
            }//end of if(FORMAT1 == format))
            productNumber_Features_TM.clear();
            productNumber_Features_TM = null;
        }//end of if(productNumber_Features_TM.size() > 0)
    }

   /********************************************************************************
   *
   *
   * @param mType int
   * @param fType int
   * @param tm TreeMap
   * @param fmtType boolean
   * @param outSB StringBuffer
   */
//   private void retrievePNFeaturesFormat2(int mType, int fType, TreeMap tm, boolean fmtType, StringBuffer outSB)
//   {
//      TreeMap productNumber_Features_TM = new TreeMap();

      //Sort by feature code first
//      Set tmSet = tm.keySet();
//      Iterator tmItr = tmSet.iterator();
//      while(tmItr.hasNext())
//      {
//         String key1 = (String) tmItr.next();
//         String key2 = parseString(key1, 2) + "<:>" + parseString(key1, 1) + "<:>" + parseString(key1, 3) + "<:>" +
//                       parseString(key1, 4) + "<:>" + parseString(key1, 5) + "<:>" + parseString(key1, 6) + "<:>" +
//                       parseString(key1, 7) + "<:>" + parseString(key1, 8);
//         productNumber_Features_TM.put(key2, tm.get(key1));
//      }//end of while(tmItr.hasNext())

//      if(productNumber_Features_TM.size() > 0)
//      {
//         String prevGeo = "";
//         String currentGeo = "";
//         String prevFeature = "";
//         String currentFeature = "";
//         String prevMT = "";
//         String currentMT ="";

//         TreeSet aTS1 = new TreeSet();
//         TreeSet aTS2 = new TreeSet();
//         Set aSet = productNumber_Features_TM.keySet();
//         Iterator aItr = aSet.iterator();
//         while(aItr.hasNext())
//         {
//            String key = (String) aItr.next();
//            String machineType = parseString(key, 2);
//            String model = parseString(key, 3);
//            aTS1.add(machineType);
//            aTS2.add(machineType + "<:>" + model);
//         }//end of while(aItr.hasNext())

//         String mtList = "";
//         aItr = aTS1.iterator();
//         while(aItr.hasNext())
//         {
//            mtList = mtList + (String) aItr.next() + ", ";
//         }
//         if(mtList.length() > 1)
//            mtList = mtList.substring(0, mtList.length() - 2);

//         String headerString = "";
//         if(brand.equals(PSERIES))
//         {
//            if(NEWFC == fType)
//               headerString = "of the IBM RS/6000 or pSeries " + mtList + " machine type:";
//            else if(EXISTINGFC == fType)
//               headerString = "the " + mtList + " machine type:";
//         }
//         else if(brand.equals(XSERIES))
//         {
//            if(NEWFC == fType)
//               headerString = "of the IBM xSeries " + mtList + " machine type:";
//            else if(EXISTINGFC == fType)
//               headerString = "the " + mtList + " machine type:";
//         }
//         else if(brand.equals(ZSERIES))
//         {
//            if(NEWFC == fType)
//               headerString = "of the IBM S/390 or zSeries " + mtList + " machine type:";
//            else if(EXISTINGFC == fType)
//               headerString = "the " + mtList + " machine type:";
//         }
//         else if(brand.equals(TS))
//         {
//            if(NEWFC == fType)
//               headerString = "of the Total Storage " + mtList + " machine type:";
//            else if(EXISTINGFC == fType)
//               headerString = "the " + mtList + " machine type:";
//         }
//         String[] arrayStr = extractStringLines(headerString, 70);
//         switch(fType)
//         {
//            case NEWFC:
//               outSB.append(":p.The following are newly announced features on the specified models\n");
//               for(int i = 0; i < arrayStr.length; i++)
//                  outSB.append(arrayStr[i] + "\n");
//            break;

//            case EXISTINGFC:
//               outSB.append(":p.The following are features already announced for\n");
//               for(int i = 0; i < arrayStr.length; i++)
//                  outSB.append(arrayStr[i] + "\n");
//            break;
//         }//end of switch(fType)

//         outSB.append(".RH ON\n");
//         outSB.append(":xmp.\n");
//         outSB.append("Description                                         MT  Model Feature\n");
//         outSB.append("-------------------------------------------------- ---- ----- -------\n");
//         outSB.append(":exmp.\n");
//         outSB.append(".RH OFF\n");

//         if(NEWMODELS == mType)
//         {
//            if(aTS2.size() > 0)
//            {
//               Iterator tsItr = aTS2.iterator();

//               outSB.append(".pa\n");

//               while(tsItr.hasNext())
//               {
//                  String key = (String) tsItr.next();

//                  if(brand.equals(PSERIES))
//                  {
//                     outSB.append("RS/6000 or pSeries");
//                     outSB.append(setString("", 32));
//                  }
//                  else if(brand.equals(XSERIES))
//                  {
//                     outSB.append("xSeries           ");
//                     outSB.append(setString("", 32));
//                  }
//                  else if(brand.equals(ZSERIES))
//                  {
//                     outSB.append("S/390 or zSeries  ");
//                     outSB.append(setString("", 32));
//                  }
//                  else if(brand.equals(TS))
//                  {
//                     outSB.append("Total Storage     ");
//                     outSB.append(setString("", 32));
//                  }

//                  outSB.append(" ");
//                  outSB.append(parseString(key, 1));
//                  outSB.append("  ");
//                  outSB.append(parseString(key, 2));
//                  outSB.append("\n");
//               }
               //outSB.append(".pa\n");
//            }//end of if(aTS.size() > 0)
//         }//end of if(NEWMODELS == mType)

//         String [] stringLines = null;
//         int nLines = 0;
//         boolean firstLine = false;
//         tmSet = productNumber_Features_TM.keySet();
//         tmItr = tmSet.iterator();
//         while(tmItr.hasNext())
//         {
//            String key = (String) tmItr.next();
//            currentGeo = (String) productNumber_Features_TM.get(key);
//            currentFeature = parseString(key, 1);
//            currentMT = parseString(key, 2);
//            if(!currentFeature.equals(prevFeature) || !currentMT.equals(prevMT))
//            {
//               if(currentFeature.equals(""))
//               {
//                  stringLines = extractStringLines(parseString(key, 4), 50);
//                  nLines = 0;
//               }
//               else
//               {
//                  if(stringLines != null)
//                  {
//                     while(nLines < stringLines.length)
//                     {
//                        outSB.append(setString(stringLines[nLines], 50));
//                        outSB.append("\n");
//                        nLines++;
//                     }
//                  }
//                  if(!currentFeature.equals(prevFeature))
//                  {
//                     stringLines = extractStringLines(parseString(key, 4), 50);
//                     nLines = 0;
//                  }
//               }
//            }
//            setGeoTags3(prevGeo, currentGeo, prevMT, currentMT, prevFeature, currentFeature, fmtType, outSB);

//            if(parseString(key, 8).length() > 0)
//            {
//               if(!currentFeature.equals(prevFeature))
//               {
//                  String [] arrayStr1 = getStringTokens(parseString(key, 8), "\n");
//                  for(int i = 0; i < arrayStr1.length; i++)
//                  {
//                     if(arrayStr1[i].length() > 58)
//                     {
//                        String [] arrayStr2 = extractStringLines(arrayStr1[i], 58);
//                        for(int j = 0; j < arrayStr2.length; j++)
//                           outSB.append(":hp2." + arrayStr2[j] + ":ehp2.\n");
//                     }
//                     else
//                        outSB.append(":hp2." + arrayStr1[i] + ":ehp2.\n");
//                  }
//               }//end of if(!currentFeature.equals(prevFeature))
//            }

//            if(nLines < stringLines.length)
//            {
//               outSB.append(setString(stringLines[nLines], 50));
//               outSB.append(" ");
//            }
//            else
//               outSB.append(setString("", 51));
//            if(!currentMT.equals(prevMT) || !currentFeature.equals(prevFeature))
//            {
//               outSB.append(parseString(key, 2));
//               outSB.append("  ");
//            }
//            else
//               outSB.append(setString("", 6));
//            outSB.append(parseString(key, 3));
//            outSB.append("     ");
//            if(!currentFeature.equals(prevFeature))
//            {
//               outSB.append(parseString(key, 1));
//               outSB.append("\n");
//            }
//            else
//               outSB.append("\n");
//            nLines++;

//            prevGeo = currentGeo;
//            prevFeature = currentFeature;
//            prevMT = currentMT;
//         }//end of while(tmItr.hasNext())
//         while(nLines < stringLines.length)
//         {
//            outSB.append(setString(stringLines[nLines], 50));
//            outSB.append("\n");
//            nLines++;
//         }
//         if(!currentGeo.equals("WW"))
//         {
//            bldEndGeoTags(currentGeo, fmtType, outSB);
//         }

//         outSB.append(":exmp.\n");
//         outSB.append(".RH CANCEL\n\n");

//         productNumber_Features_TM.clear();
//         productNumber_Features_TM = null;
//      }//end of if(productNumber_Features_TM.size() > 0)
//   }

    /********************************************************************************
    *
    *
    * @param mType int
    * @param fType int
    * @param tm TreeMap
    * @param fmtType boolean
    * @param outSB StringBuffer
    */
    private void retrievePNFeaturesFormat2(int mType, int fType, TreeMap tm, boolean fmtType, StringBuffer outSB)
    {
        TreeMap productNumber_Features_TM = new TreeMap();

        //Sort by feature code first
        Set tmSet = tm.keySet();
        Iterator tmItr = tmSet.iterator();
        while(tmItr.hasNext())
        {
            String key1 = (String) tmItr.next();
            String key2 = parseString(key1, 2, "<:>") + "<:>" + parseString(key1, 1, "<:>") + "<:>" + parseString(key1, 3, "<:>") + "<:>" +
                parseString(key1, 4, "<:>") + "<:>" + parseString(key1, 5, "<:>") + "<:>" + parseString(key1, 6, "<:>") + "<:>" +
                parseString(key1, 7, "<:>") + "<:>" + parseString(key1, 8, "<:>") + "<:>" + parseString(key1, 9, "<:>");
            productNumber_Features_TM.put(key2, tm.get(key1));
        }//end of while(tmItr.hasNext())

        if(productNumber_Features_TM.size() > 0)
        {
            String prevGeo = "";
            String currentGeo = "";
            String prevFeature = "";
            String currentFeature = "";
            String prevMT = "";
            String currentMT ="";
            String mtList;
            String headerString;
            String[] arrayStr;
            String [] stringLines;
            int nLines = 0;
            boolean printHeader;

            TreeSet aTS1 = new TreeSet();
            TreeSet aTS2 = new TreeSet();
            Set aSet = productNumber_Features_TM.keySet();
            Iterator aItr = aSet.iterator();
            while(aItr.hasNext())
            {
                String key = (String) aItr.next();
                String machineType = parseString(key, 2, "<:>");
                String model = parseString(key, 3, "<:>");
                String mktgName = parseString(key, 9, "<:>");
                aTS1.add(machineType);
                aTS2.add(machineType + "<:>" + model + "<:>" + mktgName);
            }//end of while(aItr.hasNext())

            mtList = "";
            aItr = aTS1.iterator();
            while(aItr.hasNext())
            {
                mtList = mtList + (String) aItr.next() + ", ";
            }
            if(mtList.length() > 1)
            {
                mtList = mtList.substring(0, mtList.length() - 2);
            }

            headerString = "";
            if(brand.equals(PSERIES))
            {
                if(NEWFC == fType)
                {
                    headerString = "of the IBM RS/6000 or pSeries " + mtList + " machine type:";
                }
                else if(EXISTINGFC == fType)
                {
                    headerString = "the " + mtList + " machine type:";
                }
            }
            else if(brand.equals(XSERIES))
            {
                if(NEWFC == fType)
                {
                    headerString = "of the IBM xSeries " + mtList + " machine type:";
                }
                else if(EXISTINGFC == fType)
                {
                    headerString = "the " + mtList + " machine type:";
                }
            }
            //else if(brand.equals(ZSERIES))
            //{
            //   if(NEWFC == fType)
            //      headerString = "of the IBM S/390 or zSeries " + mtList + " machine type:";
            //   else if(EXISTINGFC == fType)
            //      headerString = "the " + mtList + " machine type:";
            //}
            else if(brand.equals(TS))
            {
                if(NEWFC == fType)
                {
                    headerString = "of the Total Storage " + mtList + " machine type:";
                }
                else if(EXISTINGFC == fType)
                {
                    headerString = "the " + mtList + " machine type:";
                }
            }

            switch(fType)
            {
            case NEWFC:
                headerString = ":p.The following are newly announced features on the specified models " + headerString;
                arrayStr = extractStringLines(headerString, I_70);
                for(int i = 0; i < arrayStr.length; i++)
                {
                    outSB.append(arrayStr[i]  + NEWLINE);
                }
                break;

            case EXISTINGFC:
                headerString = ":p.The following are features already announced for " + headerString;
                arrayStr = extractStringLines(headerString, I_70);
                for(int i = 0; i < arrayStr.length; i++)
                {
                    outSB.append(arrayStr[i] + NEWLINE);
                }
                break;

            default:
                break;
            }//end of switch(fType)

            outSB.append(".RH ON" + NEWLINE);
            //outSB.append(":xmp." + NEWLINE);
            outSB.append(".fo off" + NEWLINE);
            outSB.append(".in 2" + NEWLINE);
            //outSB.append(".kp off\n");
            outSB.append("Description                                         MT  Model Feature" + NEWLINE);
            outSB.append("-------------------------------------------------- ---- ----- -------" + NEWLINE);
            //outSB.append(":exmp." + NEWLINE);
            outSB.append(".fo on" + NEWLINE);
            outSB.append(".RH OFF" + NEWLINE);

            if(NEWMODELS == mType)
            {
                if(aTS2.size() > 0)
                {
                    Iterator tsItr = aTS2.iterator();
                    outSB.append(".pa" + NEWLINE);
                    outSB.append(":xmp." + NEWLINE);
                    outSB.append(".kp off" + NEWLINE);

                    while(tsItr.hasNext())
                    {
                        String key = (String) tsItr.next();
                        stringLines = extractStringLines(parseString(key, 3, "<:>"), I_50);
                        nLines = 0;
                        //if(brand.equals(PSERIES))
                        //{
                        //    outSB.append("RS/6000 or pSeries");
                        //    outSB.append(setString("", I_32));
                        //    outSB.append(" ");
                        //    outSB.append(parseString(key, 1, "<:>"));
                        //    outSB.append("  ");
                        //    outSB.append(parseString(key, 2, "<:>"));
                        //    outSB.append(NEWLINE);
                        //}
                        //else if(brand.equals(TS) || (brand.equals(XSERIES)))
                        //{
                        //    do
                        //    {
                        //        if(0 == nLines)
                        //        {
                        //            outSB.append(setString(stringLines[nLines], I_50));
                        //            outSB.append(" ");
                        //            outSB.append(parseString(key, 1, "<:>"));
                        //            outSB.append("  ");
                        //            outSB.append(parseString(key, 2, "<:>"));
                        //            outSB.append(NEWLINE);
                        //            nLines++;
                        //        }//end of if(false == firstLine)
                        //        else
                        //        {
                        //            outSB.append(setString(stringLines[nLines], I_50));
                        //            outSB.append(NEWLINE);
                        //            nLines++;
                        //        }
                        //    }while(nLines < stringLines.length);
                        //}//end of else if(brand.equals(TS) || (brand.equals(XSERIES)))

                        do
                        {
                            if(0 == nLines)
                            {
                                outSB.append(setString(stringLines[nLines], I_50));
                                outSB.append(" ");
                                outSB.append(parseString(key, 1, "<:>"));
                                outSB.append("  ");
                                outSB.append(parseString(key, 2, "<:>"));
                                outSB.append(NEWLINE);
                                nLines++;
                            }//end of if(false == firstLine)
                            else
                            {
                                outSB.append(setString(stringLines[nLines], I_50));
                                outSB.append(NEWLINE);
                                nLines++;
                            }
                        }while(nLines < stringLines.length);
                    }//end of while(tsItr.hasNext())
                    //outSB.append(".pa\n");
                }//end of if(aTS2.size() > 0)
            }//end of if(NEWMODELS == mType)

            stringLines = null;
            nLines = 0;
            printHeader = false;
            tmSet = productNumber_Features_TM.keySet();
            tmItr = tmSet.iterator();
            if(NEWMODELS != mType)
            {
                outSB.append(":xmp." + NEWLINE); //According to OIM3.0b specs: remove :xmp. and :exmp. pair appeared around each feature
                outSB.append(".kp off" + NEWLINE);
            }
            while(tmItr.hasNext())
            {
                String key = (String) tmItr.next();
                currentGeo = (String) productNumber_Features_TM.get(key);
                currentFeature = parseString(key, 1, "<:>");
                currentMT = parseString(key, 2, "<:>");
                if(!currentFeature.equals(prevFeature) || !currentMT.equals(prevMT))
                {
                    stringLines = extractStringLines(parseString(key, 4, "<:>"), I_50);
                    printHeader = true;
                }
                setGeoTags3(prevGeo, currentGeo, prevMT, currentMT, prevFeature, currentFeature, fmtType, outSB);

                if(parseString(key, 8, "<:>").length() > 0)
                {
                    if(!currentFeature.equals(prevFeature))
                    {
                        String [] arrayStr1 = getStringTokens(parseString(key, 8, "<:>"), NEWLINE);
                        for(int i = 0; i < arrayStr1.length; i++)
                        {
                            if(arrayStr1[i].length() > I_58)
                            {
                                String [] arrayStr2 = extractStringLines(arrayStr1[i], I_58);
                                for(int j = 0; j < arrayStr2.length; j++)
                                {
                                    outSB.append(":hp2." + arrayStr2[j] + ":ehp2." + NEWLINE);
                                }
                            }
                            else
                            {
                                outSB.append(":hp2." + arrayStr1[i] + ":ehp2." + NEWLINE);
                            }
                        }
                    }//end of if(!currentFeature.equals(prevFeature))
                }

                if(printHeader)
                {
                    if(1 == stringLines.length)
                    {
                        outSB.append(setString(stringLines[0], I_50));
                        outSB.append(" ");
                    }
                    else
                    {
                        for(int i = 0; i < stringLines.length; i++)
                        {
                            outSB.append(setString(stringLines[i], I_50));
                            if(i < stringLines.length - 1)
                            {
                                outSB.append(NEWLINE);
                            }
                        }
                        outSB.append(" ");
                    }
                    printHeader = false;
                }
                else
                {
                    outSB.append(setString("", I_51));
                }

                if(!currentMT.equals(prevMT) || !currentFeature.equals(prevFeature))
                {
                    outSB.append(parseString(key, 2, "<:>"));
                    outSB.append("  ");
                }
                else
                {
                    outSB.append(setString("", 6));
                }
                outSB.append(parseString(key, 3, "<:>"));
                outSB.append("     ");
                if(!currentFeature.equals(prevFeature))
                {
                    outSB.append(parseString(key, 1, "<:>"));
                    outSB.append(NEWLINE);
                }
                else
                {
                    outSB.append(NEWLINE);
                }

                prevGeo = currentGeo;
                prevFeature = currentFeature;
                prevMT = currentMT;
            }//end of while(tmItr.hasNext())
            if(!currentGeo.equals("WW"))
            {
                bldEndGeoTags(currentGeo, fmtType, outSB);
            }

            outSB.append(":exmp." + NEWLINE);
            outSB.append(".RH CANCEL" + NEWLINE + NEWLINE);

            productNumber_Features_TM.clear();
            productNumber_Features_TM = null;
        }//end of if(productNumber_Features_TM.size() > 0)
    }

   /********************************************************************************
   *
   *
   * @param fmtType boolean
   * @param outSB StringBuffer
   */
//   private void retrievePNFeaturesWithdrawFormat1(boolean fmtType, StringBuffer outSB)
//   {
//      TreeMap productNumber_FeaturesWithdraw_TM = new TreeMap();

      //Sort by feature code first
//      Set tmSet = productNumber_NewFC_TM.keySet();
//      Iterator tmItr = tmSet.iterator();
//      while(tmItr.hasNext())
//      {
//         String key1 = (String) tmItr.next();
//         String key2 = parseString(key1, 2) + "<:>" + parseString(key1, 1) + "<:>" + parseString(key1, 3) + "<:>" +
//                       parseString(key1, 4) + "<:>" + parseString(key1, 8);
//         productNumber_FeaturesWithdraw_TM.put(key2, productNumber_NewFC_TM.get(key1));
//      }//end of while(tmItr.hasNext())

//      tmSet = productNumber_ExistingFC_TM.keySet();
//      tmItr = tmSet.iterator();
//      while(tmItr.hasNext())
//      {
//         String key1 = (String) tmItr.next();
//         String key2 = parseString(key1, 2) + "<:>" + parseString(key1, 1) + "<:>" + parseString(key1, 3) + "<:>" +
//                       parseString(key1, 4) + "<:>" + parseString(key1, 8);
//         productNumber_FeaturesWithdraw_TM.put(key2, productNumber_ExistingFC_TM.get(key1));
//      }//end of while(tmItr.hasNext())

//      if(productNumber_FeaturesWithdraw_TM.size() > 0)
//      {
//         String prevGeo = "";
//         String currentGeo = "";
//         String prevFeature = "";
//         String currentFeature = "";
//         String prevMT = "";
//         String currentMT ="";

//         if(FORMAT1 == format)
//         {
//            outSB.append(":h3.Feature Withdrawals\n");
//            outSB.append(":p.The following features are being withdrawn:\n");
//            outSB.append(":xmp.\n");
//            outSB.append(".kp off\n");
//            outSB.append("Description                   Type  Model  Feature\n");
//            outSB.append("----------------------------  ----  -----  -------\n");
//            tmSet = productNumber_FeaturesWithdraw_TM.keySet();
//            tmItr = tmSet.iterator();
//            while(tmItr.hasNext())
//            {
//               String key = (String) tmItr.next();
//               currentGeo = (String) productNumber_FeaturesWithdraw_TM.get(key);
//               currentFeature = parseString(key, 1);
//               currentMT = parseString(key, 2);
//               setGeoTags(prevGeo, currentGeo, fmtType, outSB);
//               if(parseString(key, 5).length() > 0)
//               {
//                  if(!currentFeature.equals(prevFeature))
//                  {
//                     String [] arrayStr1 = getStringTokens(parseString(key, 5), "\n");
//                     for(int i = 0; i < arrayStr1.length; i++)
//                     {
//                        if(arrayStr1[i].length() > 58)
//                        {
//                           String [] arrayStr2 = extractStringLines(arrayStr1[i], 58);
//                           for(int j = 0; j < arrayStr2.length; j++)
//                              outSB.append(":hp2." + arrayStr2[j] + ":ehp2.\n");
//                        }
//                        else
//                           outSB.append(":hp2." + arrayStr1[i] + ":ehp2.\n");
//                     }
//                  }//end of if(!currentFeature.equals(prevFeature)
//               }
               //if(!currentFeature.equals(prevFeature))
               //   outSB.append(parseString(key, 4));
               //else
               //   outSB.append(setString("", 28));

               //If the same feature code is used on more than one machine type
               //and model then print the feature code description, machine type,
               //feature code, model each time it is used
//               outSB.append(parseString(key, 4));

//               outSB.append("  ");

               //if(!currentMT.equals(prevMT) || !currentFeature.equals(prevFeature))
               //   outSB.append(parseString(key, 2));
               //else
               //   outSB.append(setString("", 4));

               //If the same feature code is used on more than one machine type
               //and model then print the feature code description, machine type,
               //feature code, model each time it is used
//               outSB.append(parseString(key, 2));

//               outSB.append("   ");
//               outSB.append(parseString(key, 3));
//               outSB.append("     ");

               //if(!currentFeature.equals(prevFeature))
               //   outSB.append(parseString(key, 1));
               //else
               //   outSB.append(setString("", 4));

               //If the same feature code is used on more than one machine type
               //and model then print the feature code description, machine type,
               //feature code, model each time it is used
//               outSB.append(parseString(key, 1));

//               outSB.append("\n");

//               prevGeo = currentGeo;
//               prevFeature = currentFeature;
//               prevMT = currentMT;
//            }//end of while(tmItr.hasNext())
//            if(!currentGeo.equals("WW"))
//            {
//               bldEndGeoTags(currentGeo, fmtType, outSB);
//            }

//            outSB.append(":exmp.\n");
//         }//end of if(FORMAT1 == format))
//         productNumber_FeaturesWithdraw_TM.clear();
//         productNumber_FeaturesWithdraw_TM = null;
//      }//end of if(productNumber_FeaturesWithdraw_TM.size() > 0)
//   }

    /********************************************************************************
    *
    *
    * @param fmtType boolean
    * @param outSB StringBuffer
    */
    private void retrievePNFeaturesWithdrawFormat1(boolean fmtType, StringBuffer outSB)
    {
        TreeMap productNumber_FeaturesWithdraw_TM = new TreeMap();

        //Sort by feature code first
        Set tmSet = productNumber_NewFC_TM.keySet();
        Iterator tmItr = tmSet.iterator();
        while(tmItr.hasNext())
        {
            String key1 = (String) tmItr.next();
            String key2 = parseString(key1, 2, "<:>") + "<:>" + parseString(key1, 1, "<:>") + "<:>" + parseString(key1, 3, "<:>") + "<:>" +
                parseString(key1, 4, "<:>") + "<:>" + parseString(key1, 8, "<:>") + "<:>" + parseString(key1, 9, "<:>");
            productNumber_FeaturesWithdraw_TM.put(key2, productNumber_NewFC_TM.get(key1));
        }//end of while(tmItr.hasNext())

        tmSet = productNumber_ExistingFC_TM.keySet();
        tmItr = tmSet.iterator();
        while(tmItr.hasNext())
        {
            String key1 = (String) tmItr.next();
            String key2 = parseString(key1, 2, "<:>") + "<:>" + parseString(key1, 1, "<:>") + "<:>" + parseString(key1, 3, "<:>") + "<:>" +
                parseString(key1, 4, "<:>") + "<:>" + parseString(key1, 8, "<:>") + "<:>" + parseString(key1, 9, "<:>");
            productNumber_FeaturesWithdraw_TM.put(key2, productNumber_ExistingFC_TM.get(key1));
        }//end of while(tmItr.hasNext())

        if(productNumber_FeaturesWithdraw_TM.size() > 0)
        {
            //Group entries based on EFFECTIVEDATE(AVAIL)
            TreeMap [] arrayTM;
            int index;
            Iterator tsItr;
            TreeSet ts = new TreeSet();
            tmSet = productNumber_FeaturesWithdraw_TM.keySet();
            tmItr = tmSet.iterator();
            while(tmItr.hasNext())
            {
                String key = (String) tmItr.next();
                ts.add(parseString(key, 6, "<:>"));
            }

            arrayTM = new TreeMap[ts.size()];
            for(int i = 0; i < ts.size(); i++)
            {
                arrayTM[i] = new TreeMap();
            }

            index = 0;
            tsItr = ts.iterator();
            while(tsItr.hasNext())
            {
                String effectiveDate = (String) tsItr.next();

                tmItr = tmSet.iterator();
                while(tmItr.hasNext())
                {
                    String key = (String) tmItr.next();
                    String ed = parseString(key, 6, "<:>");

                    if(effectiveDate.equals(ed))
                    {
                        arrayTM[index].put(key, productNumber_FeaturesWithdraw_TM.get(key));
                    }
                }//end of while(tmItr.hasNext())
                index++;
            }//end while(tsItr.hasNext())

            tsItr = ts.iterator();
            for(int i = 0; i < ts.size(); i++)
            {
                retrievePNFeaturesWithdrawFormat1(fmtType, outSB, arrayTM[i]);
            }

            for(int i = 0; i < ts.size(); i++)
            {
                arrayTM[i].clear();
                arrayTM[i] = null;
            }
            ts.clear();
            ts = null;
        }//end of if(productNumber_FeaturesWithdraw_TM.size() > 0)
    }

    /********************************************************************************
    *
    *
    * @param fmtType boolean
    * @param outSB StringBuffer
    * @param tm TreeMap
    */
    private void retrievePNFeaturesWithdrawFormat1(boolean fmtType, StringBuffer outSB, TreeMap tm)
    {
        if(tm.size() > 0)
        {
            String prevGeo = "";
            String currentGeo = "";
            String prevFeature = "";
            String currentFeature = "";
            String prevMT = "";
            String currentMT ="";
            String effectiveDate = parseString((String) tm.firstKey(), 6, "<:>");

            if(FORMAT1 == format)
            {
                Set tmSet;
                Iterator tmItr;
                outSB.append(":h3.Effective " + formatDate(effectiveDate) + NEWLINE);
                outSB.append(":h5.Feature Withdrawals" + NEWLINE);
                outSB.append(":p.The following features are being withdrawn:" + NEWLINE);
                outSB.append(":xmp." + NEWLINE);
                outSB.append(".kp off" + NEWLINE);
                outSB.append("Description                   Type  Model  Feature" + NEWLINE);
                outSB.append("----------------------------  ----  -----  -------" + NEWLINE);
                tmSet = tm.keySet();
                tmItr = tmSet.iterator();
                while(tmItr.hasNext())
                {
                    String key = (String) tmItr.next();
                    currentGeo = (String) tm.get(key);
                    currentFeature = parseString(key, 1, "<:>");
                    currentMT = parseString(key, 2, "<:>");
                    setGeoTags(prevGeo, currentGeo, fmtType, outSB);
                    if(parseString(key, 5, "<:>").length() > 0)
                    {
                        if(!currentFeature.equals(prevFeature))
                        {
                            String [] arrayStr1 = getStringTokens(parseString(key, 5, "<:>"), NEWLINE);
                            for(int i = 0; i < arrayStr1.length; i++)
                            {
                                if(arrayStr1[i].length() > I_58)
                                {
                                    String [] arrayStr2 = extractStringLines(arrayStr1[i], I_58);
                                    for(int j = 0; j < arrayStr2.length; j++)
                                    {
                                        outSB.append(":hp2." + arrayStr2[j] + ":ehp2." + NEWLINE);
                                    }
                                }
                                else
                                {
                                    outSB.append(":hp2." + arrayStr1[i] + ":ehp2." + NEWLINE);
                                }
                            }
                        }//end of if(!currentFeature.equals(prevFeature)
                    }
                    //if(!currentFeature.equals(prevFeature))
                    //   outSB.append(parseString(key, 4));
                    //else
                    //   outSB.append(setString("", 28));

                    //If the same feature code is used on more than one machine type
                    //and model then print the feature code description, machine type,
                    //feature code, model each time it is used
                    outSB.append(parseString(key, 4, "<:>"));

                    outSB.append("  ");

                    //if(!currentMT.equals(prevMT) || !currentFeature.equals(prevFeature))
                    //   outSB.append(parseString(key, 2));
                    //else
                    //   outSB.append(setString("", 4));

                    //If the same feature code is used on more than one machine type
                    //and model then print the feature code description, machine type,
                    //feature code, model each time it is used
                    outSB.append(parseString(key, 2, "<:>"));

                    outSB.append("   ");
                    outSB.append(parseString(key, 3, "<:>"));
                    outSB.append("     ");

                    //if(!currentFeature.equals(prevFeature))
                    //   outSB.append(parseString(key, 1));
                    //else
                    //   outSB.append(setString("", 4));

                    //If the same feature code is used on more than one machine type
                    //and model then print the feature code description, machine type,
                    //feature code, model each time it is used
                    outSB.append(parseString(key, 1, "<:>"));

                    outSB.append(NEWLINE);

                    prevGeo = currentGeo;
                    prevFeature = currentFeature;
                    prevMT = currentMT;
                }//end of while(tmItr.hasNext())
                if(!currentGeo.equals("WW"))
                {
                    bldEndGeoTags(currentGeo, fmtType, outSB);
                }

                outSB.append(":exmp." + NEWLINE);
            }//end of if(FORMAT1 == format))
        }//end of if(tm.size() > 0)
    }

    /********************************************************************************
    *
    *
    * @param fmtType boolean
    * @param outSB StringBuffer
    */
    private void retrievePNFeaturesWithdrawFormat2(boolean fmtType, StringBuffer outSB)
    {
        TreeMap productNumber_FeaturesWithdraw_TM = new TreeMap();

        //Sort by feature code first
        Set tmSet = productNumber_NewFC_TM.keySet();
        Iterator tmItr = tmSet.iterator();
        while(tmItr.hasNext())
        {
            String key1 = (String) tmItr.next();
            String key2 = parseString(key1, 2, "<:>") + "<:>" + parseString(key1, 1, "<:>") + "<:>" + parseString(key1, 3, "<:>") + "<:>" +
                parseString(key1, 4, "<:>") + "<:>" + parseString(key1, 5, "<:>") + "<:>" + parseString(key1, 6, "<:>") + "<:>" +
                parseString(key1, 7, "<:>") + "<:>" + parseString(key1, 8, "<:>");
            productNumber_FeaturesWithdraw_TM.put(key2, productNumber_NewFC_TM.get(key1));
        }//end of while(tmItr.hasNext())

        tmSet = productNumber_ExistingFC_TM.keySet();
        tmItr = tmSet.iterator();
        while(tmItr.hasNext())
        {
            String key1 = (String) tmItr.next();
            String key2 = parseString(key1, 2, "<:>") + "<:>" + parseString(key1, 1, "<:>") + "<:>" + parseString(key1, 3, "<:>") + "<:>" +
                parseString(key1, 4, "<:>") + "<:>" + parseString(key1, 5, "<:>") + "<:>" + parseString(key1, 6, "<:>") + "<:>" +
                parseString(key1, 7, "<:>") + "<:>" + parseString(key1, 8, "<:>");
            productNumber_FeaturesWithdraw_TM.put(key2, productNumber_ExistingFC_TM.get(key1));
        }//end of while(tmItr.hasNext())

        if(productNumber_FeaturesWithdraw_TM.size() > 0)
        {
            String prevGeo = "";
            String currentGeo = "";
            String prevFeature = "";
            String currentFeature = "";
            String prevMT = "";
            String currentMT ="";

            outSB.append(":p.The following features are being withdrawn on the specified models" + NEWLINE);
            if(brand.equals(PSERIES))
            {
                outSB.append("of the IBM RS/6000 or pSeries machine types:" + NEWLINE + NEWLINE);
            }
            else if(brand.equals(XSERIES))
            {
                outSB.append("of the IBM xSeries machine types:" + NEWLINE + NEWLINE);
            }
            //else if(brand.equals(ZSERIES))
            //   outSB.append("of the IBM S/390 or es machine types:\n\n");
            else if(brand.equals(TS))
            {
                outSB.append("of the IBM Total Storage machine types:" + NEWLINE + NEWLINE);
            }

            if(FORMAT2 == format || FORMAT3 == format)
            {
                String [] stringLines = null;
                int nLines = 0;
                outSB.append(".RH ON" + NEWLINE);
                //outSB.append(":xmp." + NEWLINE);
                outSB.append(".fo off" + NEWLINE);
                outSB.append(".in 2" + NEWLINE);
                //outSB.append(".kp off\n");
                outSB.append("Description                                         MT  Model Feature" + NEWLINE);
                outSB.append("-------------------------------------------------- ---- ----- -------" + NEWLINE);
                //outSB.append(":exmp." + NEWLINE);
                outSB.append(".fo on" + NEWLINE);
                outSB.append(".RH OFF" + NEWLINE);
                //boolean firstLine = false;
                tmSet = productNumber_FeaturesWithdraw_TM.keySet();
                tmItr = tmSet.iterator();
                outSB.append(":xmp." + NEWLINE); //According to OIM3.0b specs: remove :xmp. and :exmp. pair appeared around each feature
                outSB.append(".kp off" + NEWLINE);
                while(tmItr.hasNext())
                {
                    String key = (String) tmItr.next();
                    currentGeo = (String) productNumber_FeaturesWithdraw_TM.get(key);
                    currentFeature = parseString(key, 1, "<:>");
                    currentMT = parseString(key, 2, "<:>");
                    if(!currentFeature.equals(prevFeature))
                    {
                        stringLines = extractStringLines(parseString(key, 4, "<:>"), I_50);
                        nLines = 0;
                    }//end of if(!currentFeature.equals(prevFeature) || !currentMT.equals(prevMT))
                    setGeoTags3(prevGeo, currentGeo, prevMT, currentMT, prevFeature, currentFeature, fmtType, outSB);

                    if(parseString(key, 8, "<:>").length() > 0)
                    {
                        if(!currentFeature.equals(prevFeature))
                        {
                            String [] arrayStr1 = getStringTokens(parseString(key, 8, "<:>"), NEWLINE);
                            for(int i = 0; i < arrayStr1.length; i++)
                            {
                                if(arrayStr1[i].length() > I_58)
                                {
                                    String [] arrayStr2 = extractStringLines(arrayStr1[i], I_58);
                                    for(int j = 0; j < arrayStr2.length; j++)
                                    {
                                        outSB.append(":hp2." + arrayStr2[j] + ":ehp2." + NEWLINE);
                                    }
                                }
                                else
                                {
                                    outSB.append(":hp2." + arrayStr1[i] + ":ehp2." + NEWLINE);
                                }
                            }
                        }//end of if(!currentFeature.equals(prevFeature))
                    }

                    if(nLines < stringLines.length)
                    {
                        outSB.append(setString(stringLines[nLines], I_50));
                        outSB.append(" ");
                        nLines++;
                    }
                    else
                    {
                        outSB.append(setString("", I_51));
                    }
                    if(!currentMT.equals(prevMT) || !currentFeature.equals(prevFeature))
                    {
                        outSB.append(parseString(key, 2, "<:>"));
                        outSB.append("  ");
                    }
                    else
                    {
                        outSB.append(setString("", 6));
                    }
                    outSB.append(parseString(key, 3, "<:>"));
                    outSB.append("     ");
                    if(!currentFeature.equals(prevFeature))
                    {
                        outSB.append(parseString(key, 1, "<:>"));
                        outSB.append(NEWLINE);
                    }
                    else
                    {
                        outSB.append(NEWLINE);
                    }
                    while(nLines < stringLines.length)
                    {
                        outSB.append(setString(stringLines[nLines], I_50));
                        outSB.append(NEWLINE);
                        nLines++;
                    }

                    prevGeo = currentGeo;
                    prevFeature = currentFeature;
                    prevMT = currentMT;
                }//end of while(tmItr.hasNext())
                if(!currentGeo.equals("WW"))
                {
                    bldEndGeoTags(currentGeo, fmtType, outSB);
                }

                outSB.append(":exmp." + NEWLINE);
                outSB.append(".RH CANCEL" + NEWLINE + NEWLINE);
            }//end of else if(FORMAT2 == format)
            productNumber_FeaturesWithdraw_TM.clear();
            productNumber_FeaturesWithdraw_TM = null;
        }//end of if(productNumber_FeaturesWithdraw_TM.size() > 0)
    }

    /********************************************************************************
    *
    *
    * @param fmtType boolean
    * @param outSB StringBuffer
    */
    private void retrievePNMTMConversions(boolean fmtType, StringBuffer outSB)
    {
        if(productNumber_MTM_Conversions_TM.size() > 0)
        {
            String prevGeo = "";
            String currentGeo = "";
            if(fmtType == GMLFORMAT)
            {
                if(annType.equals(ANNOUNCEMENT_TYPE_NEW))
                {
                    outSB.append(":h3.Type/Model Conversions" + NEWLINE);
                }
                else if(annType.equals(ANNOUNCEMENT_TYPE_WITHDRAW))
                {
                    outSB.append(":h3.Type/Model Conversion Withdrawals" + NEWLINE);
                    outSB.append(":p.The following Type/Model Conversions are being withdrawn:" + NEWLINE);
                }
                outSB.append(":xmp." + NEWLINE);
                outSB.append(".kp off" + NEWLINE + NEWLINE);
            }
            else
            {
                if(annType.equals(ANNOUNCEMENT_TYPE_NEW))
                {
                    outSB.append("<h3>Type/Model Conversions</h3>" + NEWLINE);
                }
                else if(annType.equals(ANNOUNCEMENT_TYPE_WITHDRAW))
                {
                    outSB.append("<h3>Type/Model Conversion Withdrawals</h3>" + NEWLINE);
                    outSB.append("<p>The following Type/Model Conversions are being withdrawn:</p>" + NEWLINE);
                }
            }

            if(FORMAT1 == format)
            {
                Set tmSet;
                Iterator tmItr;

                if(annType.equals(ANNOUNCEMENT_TYPE_NEW))
                {
                    outSB.append("   From       To      Parts" + NEWLINE);
                    outSB.append("Type Model Type Model Returned" + NEWLINE);
                    outSB.append("---- ----- ---- ----- --------" + NEWLINE);
                }
                else if(annType.equals(ANNOUNCEMENT_TYPE_WITHDRAW))
                {
                    outSB.append("   From       To" + NEWLINE);
                    outSB.append("Type Model Type Model" + NEWLINE);
                    outSB.append("---- ----- ---- -----" + NEWLINE);
                }
                tmSet = productNumber_MTM_Conversions_TM.keySet();
                tmItr = tmSet.iterator();
                while(tmItr.hasNext())
                {
                    String key = (String) tmItr.next();
                    currentGeo = (String) productNumber_MTM_Conversions_TM.get(key);
                    setGeoTags(prevGeo, currentGeo, fmtType, outSB);
                    //outSB.append(" ");
                    outSB.append(parseString(key, 3, "<:>"));
                    outSB.append("  ");
                    outSB.append(parseString(key, 4, "<:>"));
                    outSB.append("  ");
                    outSB.append(parseString(key, 1, "<:>"));
                    outSB.append("  ");
                    outSB.append(parseString(key, 2, "<:>"));
                    if(annType.equals(ANNOUNCEMENT_TYPE_NEW))
                    {
                        outSB.append("    ");
                        outSB.append(parseString(key, 5, "<:>"));
                    }
                    outSB.append(NEWLINE);

                    prevGeo = currentGeo;
                }//end of while(tmItr.hasNext())
                if(!currentGeo.equals("WW"))
                {
//               if(fmtType == XMLFORMAT)
//               {
//                  outSB.append("</pre>\n");
//               }
                    bldEndGeoTags(currentGeo, fmtType, outSB);
//               if(fmtType == XMLFORMAT)
//               {
//                  outSB.append("<pre>");
//               }
                }
                if(fmtType == GMLFORMAT)
                {
                    outSB.append(":exmp." + NEWLINE + NEWLINE);
                }
//            else
//            {
//               outSB.append("</pre><p> </p>");
//            }
            }//end of if(brand.equals("iSeries"))
            else if(FORMAT2 == format || FORMAT3 == format)
            {
                Set tmSet;
                Iterator tmItr;
                outSB.append("   From       To" + NEWLINE);
                outSB.append("Type Model Type Model" + NEWLINE);
                outSB.append("---- ----- ---- -----" + NEWLINE);
                tmSet = productNumber_MTM_Conversions_TM.keySet();
                tmItr = tmSet.iterator();
                while(tmItr.hasNext())
                {
                    String key = (String) tmItr.next();
                    currentGeo = (String) productNumber_MTM_Conversions_TM.get(key);
                    setGeoTags(prevGeo, currentGeo, fmtType, outSB);
                    //outSB.append(" ");
                    outSB.append(parseString(key, 3, "<:>"));
                    outSB.append("  ");
                    outSB.append(parseString(key, 4, "<:>"));
                    outSB.append("  ");
                    outSB.append(parseString(key, 1, "<:>"));
                    outSB.append("  ");
                    outSB.append(parseString(key, 2, "<:>"));
                    outSB.append(NEWLINE);

                    prevGeo = currentGeo;
                }//end of while(tmItr.hasNext())
                if(!currentGeo.equals("WW"))
                {
//               if(fmtType == XMLFORMAT)
//               {
//                  outSB.append("</pre>\n");
//               }
                    bldEndGeoTags(currentGeo, fmtType, outSB);
//               if(fmtType == XMLFORMAT)
//               {
//                  outSB.append("<pre>");
//               }
                }
                if(fmtType == GMLFORMAT)
                {
                    outSB.append(":exmp." + NEWLINE + NEWLINE);
                }
//            else
//            {
//               outSB.append("</pre><p> </p>");
//            }
            }//end of else if(FORMAT2 == format)
        }//end of if(productNumber_MTM_Conversions_TM.size() > 0)
    }

    /********************************************************************************
    *
    *
    * @param fmtType boolean
    * @param outSB StringBuffer
    */
    private void retrievePNModelConversions(boolean fmtType, StringBuffer outSB)
    {
        if(productNumber_Model_Conversions_TM.size() > 0)
        {
            String prevGeo = "";
            String currentGeo = "";
            if(fmtType == GMLFORMAT)
            {
                if(annType.equals(ANNOUNCEMENT_TYPE_NEW))
                {
                    outSB.append(":h3.Model Conversions" + NEWLINE);
                }
                else if(annType.equals(ANNOUNCEMENT_TYPE_WITHDRAW))
                {
                    outSB.append(":h3.Model Conversion Withdrawals" + NEWLINE);
                    outSB.append(":p.The following Model Converions on the specified machine type are being withdrawn:" + NEWLINE);
                }
                outSB.append(":xmp." + NEWLINE);
                outSB.append(".kp off" + NEWLINE + NEWLINE);
            }
            else
            {
                if(annType.equals(ANNOUNCEMENT_TYPE_NEW))
                {
                    outSB.append("<h3>Model Conversions</h3>" + NEWLINE);
                }
                else if(annType.equals(ANNOUNCEMENT_TYPE_WITHDRAW))
                {
                    outSB.append("<h3>Model Conversion Withdrawals</h3>" + NEWLINE);
                    outSB.append("<p>The following Model Converions on the specified machine type are being withdrawn:</p>" + NEWLINE);
                }
            }

            if(FORMAT1 == format)
            {
                Set tmSet;
                Iterator tmItr;
                if(annType.equals(ANNOUNCEMENT_TYPE_NEW))
                {
                    outSB.append("      From   To      Parts" + NEWLINE);
                    outSB.append("Type  Model  Model   Returned" + NEWLINE);
                    outSB.append("----  -----  -----   --------" + NEWLINE);
                }
                else if(annType.equals(ANNOUNCEMENT_TYPE_WITHDRAW))
                {
                    outSB.append("      From   To" + NEWLINE);
                    outSB.append("Type  Model  Model" + NEWLINE);
                    outSB.append("----  -----  -----" + NEWLINE);
                }
                tmSet = productNumber_Model_Conversions_TM.keySet();
                tmItr = tmSet.iterator();
                while(tmItr.hasNext())
                {
                    String key = (String) tmItr.next();
                    currentGeo = (String) productNumber_Model_Conversions_TM.get(key);
                    setGeoTags(prevGeo, currentGeo, fmtType, outSB);
                    //outSB.append(" ");
                    outSB.append(parseString(key, 1, "<:>"));
                    outSB.append("   ");
                    outSB.append(parseString(key, 3, "<:>"));
                    outSB.append("    ");
                    outSB.append(parseString(key, 2, "<:>"));
                    if(annType.equals(ANNOUNCEMENT_TYPE_NEW))
                    {
                        outSB.append("      ");
                        outSB.append(parseString(key, 4, "<:>"));
                    }
                    outSB.append(NEWLINE);

                    prevGeo = currentGeo;
                }//end of while(tmItr.hasNext())
                if(!currentGeo.equals("WW"))
                {
//               if(fmtType == XMLFORMAT)
//               {
//                  outSB.append("</pre>\n");
//               }
                    bldEndGeoTags(currentGeo, fmtType, outSB);
//               if(fmtType == XMLFORMAT)
//               {
//                  outSB.append("<pre>");
//               }
                }
                if(fmtType == GMLFORMAT)
                {
                    outSB.append(":exmp." + NEWLINE + NEWLINE);
                }
//            else
//            {
//               outSB.append("</pre><p> </p>");
//            }
            }//end of if(brand.equals("iSeries"))
            else if(FORMAT2 == format || FORMAT3 == format)
            {
                Set tmSet;
                Iterator tmItr;
                if(annType.equals(ANNOUNCEMENT_TYPE_NEW))
                {
                    outSB.append("      From   To      Parts" + NEWLINE);
                    outSB.append("Type  Model  Model   Returned" + NEWLINE);
                    outSB.append("----  -----  -----   --------" + NEWLINE);
                }
                else if(annType.equals(ANNOUNCEMENT_TYPE_WITHDRAW))
                {
                    outSB.append("      From   To" + NEWLINE);
                    outSB.append("Type  Model  Model" + NEWLINE);
                    outSB.append("----  -----  -----" + NEWLINE);
                }
                tmSet = productNumber_Model_Conversions_TM.keySet();
                tmItr = tmSet.iterator();
                while(tmItr.hasNext())
                {
                    String key = (String) tmItr.next();
                    currentGeo = (String) productNumber_Model_Conversions_TM.get(key);
                    setGeoTags(prevGeo, currentGeo, fmtType, outSB);
                    //outSB.append(" ");
                    outSB.append(parseString(key, 1, "<:>"));
                    outSB.append("   ");
                    outSB.append(parseString(key, 3, "<:>"));
                    outSB.append("    ");
                    outSB.append(parseString(key, 2, "<:>"));
                    if(annType.equals(ANNOUNCEMENT_TYPE_NEW))
                    {
                        outSB.append("      ");
                        outSB.append(parseString(key, 4, "<:>"));
                    }
                    outSB.append(NEWLINE);

                    prevGeo = currentGeo;
                }//end of while(tmItr.hasNext())
                if(!currentGeo.equals("WW"))
                {
//               if(fmtType == XMLFORMAT)
//               {
//                  outSB.append("</pre>\n");
//               }
                    bldEndGeoTags(currentGeo, fmtType, outSB);
//               if(fmtType == XMLFORMAT)
//               {
//                  outSB.append("<pre>");
//               }
                }
                if(fmtType == GMLFORMAT)
                {
                    outSB.append(":exmp." + NEWLINE + NEWLINE);
                }
//            else
//            {
//               outSB.append("</pre><p> </p>");
//            }
            }//end of else if(FORMAT2 == format)
        }//end of if(productNumber_Model_Conversions_TM.size() > 0)
    }

    /********************************************************************************
    *
    *
    * @param fmtType boolean
    * @param outSB StringBuffer
    */
    private void retrievePNFeatureConversionsFormat1(boolean fmtType, StringBuffer outSB)
    {
        if(productNumber_Feature_Conversions_TM.size() > 0)
        {
            String prevGeo = "";
            String currentGeo = "";
            //String hwfccat = parseString((String)productNumber_Feature_Conversions_TM.firstKey(), 1);
            Set tmSet;
            Iterator tmItr;

            outSB.append(":h3.Conversions" + NEWLINE);
            outSB.append(":h5.Feature Conversions" + NEWLINE);
            outSB.append(":xmp." + NEWLINE);
            outSB.append(".kp off" + NEWLINE);
            outSB.append("               Parts       Continuous    Machine" + NEWLINE);
            outSB.append("From:   To:    Returned    Maintenance   Type     Model" + NEWLINE);
            outSB.append("----    ---    --------    -----------   -------  -----" + NEWLINE);
            tmSet = productNumber_Feature_Conversions_TM.keySet();
            tmItr = tmSet.iterator();
            while(tmItr.hasNext())
            {
                String key = (String) tmItr.next();
                currentGeo = (String) productNumber_Feature_Conversions_TM.get(key);
                setGeoTags(prevGeo, currentGeo, fmtType, outSB);
                if(parseString(key, 9, "<:>").length() > 0)
                {
                    if(fmtType == XMLFORMAT)
                    {
//               outSB.append("</pre>\n");
                        String [] arrayStr1 = getStringTokens(parseString(key, 9, "<:>"), NEWLINE);
                        for(int i = 0; i < arrayStr1.length; i++)
                        {
                            outSB.append(arrayStr1[i] + NEWLINE);
                        }
//                     outSB.append("<pre>\n");
                    }
                    if(fmtType == GMLFORMAT)
                    {
                        String [] arrayStr1 = getStringTokens(parseString(key, 9, "<:>"), NEWLINE);
                        for(int i = 0; i < arrayStr1.length; i++)
                        {
                            if(arrayStr1[i].length() > I_58)
                            {
                                String [] arrayStr2 = extractStringLines(arrayStr1[i], I_58);
                                for(int j = 0; j < arrayStr2.length; j++)
                                {
                                    outSB.append(":hp2." + arrayStr2[j] + ":ehp2." + NEWLINE);
                                }
                            }
                            else
                            {
                                outSB.append(":hp2." + arrayStr1[i] + ":ehp2." + NEWLINE);
                            }
                        }
                    }
                }
                //outSB.append(" ");
                outSB.append(parseString(key, 7, "<:>"));
                outSB.append("    ");
                outSB.append(parseString(key, 4, "<:>"));
                outSB.append("     ");
                outSB.append(parseString(key, 8, "<:>"));
                outSB.append("                      ");
                outSB.append(parseString(key, 3, "<:>"));
                outSB.append("     ");
                outSB.append(parseString(key, 2, "<:>"));
                outSB.append(NEWLINE);

                prevGeo = currentGeo;
            }//end of while(tmItr.hasNext())
            if(!currentGeo.equals("WW"))
            {
//         if(fmtType == XMLFORMAT)
//         {
//            outSB.append("</pre>\n");
//         }
                bldEndGeoTags(currentGeo, fmtType, outSB);
//         if(fmtType == XMLFORMAT)
//         {
//            outSB.append("<pre>");
//         }
            }
            if(fmtType == GMLFORMAT)
            {
                outSB.append(":exmp." + NEWLINE + NEWLINE);
            }
//      else
//      {
//         outSB.append("</pre><p> </p>");
//      }
        }//end of if(productNumber_Feature_Conversions_TM.size() > 0)
    }

    /********************************************************************************
    *
    *
    * @param fmtType boolean
    * @param outSB StringBuffer
    */
    private void retrievePNFeatureConversionsFormat2(boolean fmtType, StringBuffer outSB)
    {
        if(charges_Feature_Conversions_TM.size() > 0)
        {
            String fromMachType;
            String fromModel;
            TreeSet ts;
            Set tmSet;
            Iterator tmItr;
            TreeMap [] arrayTM;
            int index;
            Iterator tsItr;

            outSB.append(":h3.Feature Conversions" + NEWLINE);
            outSB.append(":p." + NEWLINE);
            outSB.append("The existing components being replaced during a model or feature" + NEWLINE);
            outSB.append("conversion become the property of IBM and must be returned." + NEWLINE);
            outSB.append(":p." + NEWLINE);
            outSB.append("Feature conversions are always implemented on a \"quantity of one for" + NEWLINE);
            outSB.append("quantity of one\" basis. Multiple existing features may not be converted" + NEWLINE);
            outSB.append("to a single new feature. Single existing features may not be converted" + NEWLINE);
            outSB.append("to multiple new features." + NEWLINE);
            outSB.append(":p." + NEWLINE);
            outSB.append("The following conversions are available to customers:" + NEWLINE);
            outSB.append(":p." + NEWLINE);

            //Group entries based FROMMACHTYPE-FROMMODEL
            fromMachType = "";
            fromModel = "";
            ts = new TreeSet();
            tmSet = charges_Feature_Conversions_TM.keySet();
            tmItr = tmSet.iterator();
            while(tmItr.hasNext())
            {
                String key = (String) tmItr.next();
                fromMachType = parseString(key, 5, "<:>");
                fromModel = parseString(key, 6, "<:>");
                ts.add(fromMachType + "-" + fromModel);
            }

            arrayTM = new TreeMap[ts.size()];
            for(int i = 0; i < ts.size(); i++)
            {
                arrayTM[i] = new TreeMap();
            }

            index = 0;
            tsItr = ts.iterator();
            while(tsItr.hasNext())
            {
                String fromMTM = (String) tsItr.next();

                tmItr = tmSet.iterator();
                while(tmItr.hasNext())
                {
                    String fMTM;
                    String key = (String) tmItr.next();
                    fromMachType = parseString(key, 5, "<:>");
                    fromModel = parseString(key, 6, "<:>");
                    fMTM = fromMachType + "-" + fromModel;

                    if(fromMTM.equals(fMTM))
                    {
                        arrayTM[index].put(key, charges_Feature_Conversions_TM.get(key));
                    }
                }//end of while(tmItr.hasNext())
                index++;
            }//end of while(tsItr.hasNext())

            index = 0;
            tsItr = ts.iterator();
            while(tsItr.hasNext())
            {
                String fromMTM = (String) tsItr.next();
                retrievePNFeatureConversionsFormat2(fmtType, outSB, fromMTM, arrayTM[index]);
                index++;
            }
        }//end of if(charges_Feature_Conversions_TM.size() > 0)
    }

    /********************************************************************************
    *
    *
    * @param fmtType boolean
    * @param outSB StringBuffer
    * @param mtm String
    * @param tm TreeMap
    */
    private void retrievePNFeatureConversionsFormat2(boolean fmtType, StringBuffer outSB, String mtm, TreeMap tm)
    {
        TreeSet ts;
        Set tmSet;
        Iterator tmItr;
        TreeMap [] arrayTM;
        int index;
        Iterator tsItr;

        outSB.append(":h4.Feature conversions for " + mtm + NEWLINE);
        outSB.append(".sk 1" + NEWLINE);
        outSB.append(":p." + NEWLINE);

        //Group entries based on HWFCCAT(FEATURE)
        ts = new TreeSet();
        tmSet = tm.keySet();
        tmItr = tmSet.iterator();
        while(tmItr.hasNext())
        {
            String key = (String) tmItr.next();
            ts.add(parseString(key, 1, "<:>"));
        }

        arrayTM = new TreeMap[ts.size()];
        for(int i = 0; i < ts.size(); i++)
        {
            arrayTM[i] = new TreeMap();
        }

        index = 0;
        tsItr = ts.iterator();
        while(tsItr.hasNext())
        {
            String hwfccat = (String) tsItr.next();

            tmItr = tmSet.iterator();
            while(tmItr.hasNext())
            {
                String key = (String) tmItr.next();

                if(hwfccat.equals(parseString(key, 1, "<:>")))
                {
                    arrayTM[index].put(key, tm.get(key));
                }
            }//end of while(tmItr.hasNext())
            index++;
        }//end of while(tsItr.hasNext())

        for(int i = 0; i < arrayTM.length; i++)
        {
            retrievePNFeatureConversionsFormat2(fmtType, outSB, arrayTM[i]);
        }
    }

    /********************************************************************************
    *
    *
    * @param fmtType boolean
    * @param outSB StringBuffer
    * @param tm TreeMap
    */
    private void retrievePNFeatureConversionsFormat2(boolean fmtType, StringBuffer outSB, TreeMap tm)
    {
        String prevGeo = "";
        String currentGeo = "";
        String hwfccat = parseString((String) tm.firstKey(), 1, "<:>");
        int totalLines;
        int nLines;
        boolean firstLine;
        Set tmSet;
        Iterator tmItr;
        outSB.append(":h5.Feature conversions for " + hwfccat + " features:" + NEWLINE);
        outSB.append(":xmp." + NEWLINE);
        //outSB.append(".kp off\n");
        outSB.append("                                                            RETURN" + NEWLINE);
        outSB.append("From FC:                      To FC:                        PARTS" + NEWLINE);
        outSB.append("---------------------------   ---------------------------   ------" + NEWLINE);
        outSB.append(":exmp." + NEWLINE);
        totalLines = 0;
        nLines = 0;
        firstLine = false;
        tmSet = tm.keySet();
        tmItr = tmSet.iterator();
        while(tmItr.hasNext())
        {
            String fromFCDescription;
            String toFCDescription;
            String [] fromFCStringLines;
            String [] toFCStringLines ;
            String key = (String) tmItr.next();
            currentGeo = (String) tm.get(key);
            setGeoTagsFeatConv(prevGeo, currentGeo, fmtType, outSB);
            if(parseString(key, I_11, "<:>").length() > 0)
            {
                if(fmtType == XMLFORMAT)
                {
//                  outSB.append("</pre>\n");
                    String [] arrayStr1 = getStringTokens(parseString(key, I_11, "<:>"), NEWLINE);
                    for(int i = 0; i < arrayStr1.length; i++)
                    {
                        outSB.append(arrayStr1[i] + NEWLINE);
                    }
//                  outSB.append("<pre>\n");
                }
                if(fmtType == GMLFORMAT)
                {
                    String [] arrayStr1 = getStringTokens(parseString(key, I_11, "<:>"), NEWLINE);
                    for(int i = 0; i < arrayStr1.length; i++)
                    {
                        if(arrayStr1[i].length() > I_58)
                        {
                            String [] arrayStr2 = extractStringLines(arrayStr1[i], I_58);
                            for(int j = 0; j < arrayStr2.length; j++)
                            {
                                outSB.append(":hp2." + arrayStr2[j] + ":ehp2." + NEWLINE);
                            }
                        }
                        else
                        {
                            outSB.append(":hp2." + arrayStr1[i] + ":ehp2." + NEWLINE);
                        }
                    }
                }
            }

            fromFCDescription = parseString(key, 7, "<:>") + " - " + parseString(key, 9, "<:>");
            toFCDescription = parseString(key, 4, "<:>") + " - " + parseString(key, 8, "<:>");
            fromFCStringLines = extractStringLines(fromFCDescription, I_27);
            toFCStringLines = extractStringLines(toFCDescription, I_27);
            totalLines = 0;
            if(fromFCStringLines.length > toFCStringLines.length)
            {
                totalLines = fromFCStringLines.length;
            }
            else
            {
                totalLines = toFCStringLines.length;
            }
            nLines = 0;
            firstLine = false;
            do
            {
                if(false == firstLine)
                {
                    //outSB.append(" ");
                    outSB.append(setString(fromFCStringLines[nLines], I_27));
                    outSB.append("   ");
                    outSB.append(setString(toFCStringLines[nLines], I_27));
                    outSB.append("    ");
                    outSB.append(parseString(key, 10, "<:>"));
                    outSB.append(NEWLINE);
                    firstLine = true;
                    nLines++;
                }
                else
                {
                    if(nLines < fromFCStringLines.length)
                    {
                        outSB.append(setString(fromFCStringLines[nLines], I_27));
                    }
                    else
                    {
                        outSB.append(setString(" ", I_27));
                    }
                    outSB.append("   ");
                    if(nLines < toFCStringLines.length)
                    {
                        outSB.append(setString(toFCStringLines[nLines], I_27));
                    }
                    else
                    {
                        outSB.append(setString(" ", I_27));
                    }
                    outSB.append(NEWLINE);
                    nLines++;
                }
            }while(nLines < totalLines);
            prevGeo = currentGeo;
        }//end of while(tmItr.hasNext())
        if(!currentGeo.equals("WW"))
        {
//            if(fmtType == XMLFORMAT)
//            {
//               outSB.append("</pre>\n");
//            }
            bldEndGeoTags(currentGeo, fmtType, outSB);
//            if(fmtType == XMLFORMAT)
//            {
//               outSB.append("<pre>");
//            }
        }
        if(fmtType == GMLFORMAT)
        {
            outSB.append(":exmp." + NEWLINE + NEWLINE);
        }
//         else
//         {
//            outSB.append("</pre><p> </p>");
//         }
    }

    /********************************************************************************
    *
    *
    * @param fmtType boolean
    * @param outSB StringBuffer
    */
    private void retrievePNFeatureConversionsForWithdrawFormat1(boolean fmtType, StringBuffer outSB)
    {
        if(productNumber_Feature_Conversions_TM.size() > 0)
        {
            //Group entries based on EFFECTIVEDATE(AVAIL)
            TreeMap [] arrayTM;
            int index;
            Iterator tsItr;
            TreeSet ts = new TreeSet();
            Set tmSet = productNumber_Feature_Conversions_TM.keySet();
            Iterator tmItr = tmSet.iterator();
            while(tmItr.hasNext())
            {
                String key = (String) tmItr.next();
                ts.add(parseString(key, 10, "<:>"));
            }

            arrayTM = new TreeMap[ts.size()];
            for(int i = 0; i < ts.size(); i++)
            {
                arrayTM[i] = new TreeMap();
            }

            index = 0;
            tsItr = ts.iterator();
            while(tsItr.hasNext())
            {
                String effectiveDate = (String) tsItr.next();

                tmItr = tmSet.iterator();
                while(tmItr.hasNext())
                {
                    String key = (String) tmItr.next();
                    String ed = parseString(key, 10, "<:>");

                    if(effectiveDate.equals(ed))
                    {
                        String aKey = parseString(key, 1, "<:>") + "<:>" + parseString(key, 2, "<:>") + "<:>" + parseString(key, 3, "<:>") + "<:>"  +
                            parseString(key, 4, "<:>") + "<:>" + parseString(key, 5, "<:>") + "<:>" + parseString(key, 6, "<:>") + "<:>"  +
                            parseString(key, 7, "<:>") + "<:>" + parseString(key, 9, "<:>") + "<:>" + parseString(key, 10, "<:>");
                        arrayTM[index].put(aKey, productNumber_Feature_Conversions_TM.get(key));
                    }
                }//end of while(tmItr.hasNext())
                index++;
            }//end of while(tsItr.hasNext())

            tsItr = ts.iterator();
            for(int i = 0; i < ts.size(); i++)
            {
                retrievePNFeatureConversionsForWithdrawFormat1(fmtType, outSB, arrayTM[i]);
            }

            for(int i = 0; i < ts.size(); i++)
            {
                arrayTM[i].clear();
                arrayTM[i] = null;
            }
            ts.clear();
            ts = null;
        }//end of if(productNumber_Feature_Conversions_TM.size() > 0)
    }

    /********************************************************************************
    *
    *
    * @param fmtType boolean
    * @param outSB StringBuffer
    * @param tm TreeMap
    */
    private void retrievePNFeatureConversionsForWithdrawFormat1(boolean fmtType, StringBuffer outSB, TreeMap tm)
    {
        Set tmSet;
        Iterator tmItr;
        String prevGeo = "";
        String currentGeo = "";
        String effectiveDate = parseString((String)tm.firstKey(), 9, "<:>");

        outSB.append(":h3.Effective " + formatDate(effectiveDate) + NEWLINE);
        outSB.append(":h5.Feature Conversion Withdrawals" + NEWLINE);
        outSB.append(":xmp." + NEWLINE);
        outSB.append(".kp off" + NEWLINE);
        outSB.append("              Machine" + NEWLINE);
        outSB.append("From:   To:   Type     Model" + NEWLINE);
        outSB.append("----    ---   -------  -----" + NEWLINE);
        tmSet = tm.keySet();
        tmItr = tmSet.iterator();
        while(tmItr.hasNext())
        {
            String key = (String) tmItr.next();
            currentGeo = (String) tm.get(key);
            setGeoTags(prevGeo, currentGeo, fmtType, outSB);
            if(parseString(key, 8, "<:>").length() > 0)
            {
                if(fmtType == XMLFORMAT)
                {
//               outSB.append("</pre>\n");
                    String [] arrayStr1 = getStringTokens(parseString(key, 8, "<:>"), NEWLINE);
                    for(int i = 0; i < arrayStr1.length; i++)
                    {
                        outSB.append(arrayStr1[i] + NEWLINE);
                    }
//                     outSB.append("<pre>\n");
                }
                if(fmtType == GMLFORMAT)
                {
                    String [] arrayStr1 = getStringTokens(parseString(key, 9, "<:>"), NEWLINE);
                    for(int i = 0; i < arrayStr1.length; i++)
                    {
                        if(arrayStr1[i].length() > I_58)
                        {
                            String [] arrayStr2 = extractStringLines(arrayStr1[i], I_58);
                            for(int j = 0; j < arrayStr2.length; j++)
                            {
                                outSB.append(":hp2." + arrayStr2[j] + ":ehp2." + NEWLINE);
                            }
                        }
                        else
                        {
                            outSB.append(":hp2." + arrayStr1[i] + ":ehp2." + NEWLINE);
                        }
                    }
                }
            }
            //outSB.append(" ");
            outSB.append(parseString(key, 7, "<:>"));
            outSB.append("    ");
            outSB.append(parseString(key, 4, "<:>"));
            outSB.append("   ");
            outSB.append(parseString(key, 3, "<:>"));
            outSB.append("     ");
            outSB.append(parseString(key, 2, "<:>"));
            outSB.append(NEWLINE);

            prevGeo = currentGeo;
        }//end of while(tmItr.hasNext())
        if(!currentGeo.equals("WW"))
        {
//         if(fmtType == XMLFORMAT)
//         {
//            outSB.append("</pre>\n");
//         }
            bldEndGeoTags(currentGeo, fmtType, outSB);
//         if(fmtType == XMLFORMAT)
//         {
//            outSB.append("<pre>");
//         }
        }
        if(fmtType == GMLFORMAT)
        {
            outSB.append(":exmp." + NEWLINE + NEWLINE);
        }
//      else
//      {
//         outSB.append("</pre><p> </p>");
//      }
    }

    /********************************************************************************
    *
    *
    * @param fmtType boolean
    * @param outSB StringBuffer
    */
    private void retrievePNFeatureConversionsForWithdrawFormat2(boolean fmtType, StringBuffer outSB)
    {
        if(charges_Feature_Conversions_TM.size() > 0)
        {
            String fromMachType;
            String fromModel;
            TreeSet ts;
            Set tmSet;
            Iterator tmItr;
            TreeMap [] arrayTM;
            int index;
            Iterator tsItr;

            outSB.append(":h3.Feature Conversion Withdrawals" + NEWLINE);
            outSB.append(":p." + NEWLINE);

            //Group entries based on FROMMACHTYPE-FROMMODEL
            fromMachType = "";
            fromModel = "";
            ts = new TreeSet();
            tmSet = charges_Feature_Conversions_TM.keySet();
            tmItr = tmSet.iterator();
            while(tmItr.hasNext())
            {
                String key = (String) tmItr.next();
                fromMachType = parseString(key, 5, "<:>");
                fromModel = parseString(key, 6, "<:>");
                ts.add(fromMachType + "-" + fromModel);
            }

            arrayTM = new TreeMap[ts.size()];
            for(int i = 0; i < ts.size(); i++)
            {
                arrayTM[i] = new TreeMap();
            }

            index = 0;
            tsItr = ts.iterator();
            while(tsItr.hasNext())
            {
                String fromMTM = (String) tsItr.next();

                tmItr = tmSet.iterator();
                while(tmItr.hasNext())
                {
                    String fMTM;
                    String key = (String) tmItr.next();
                    fromMachType = parseString(key, 5, "<:>");
                    fromModel = parseString(key, 6, "<:>");
                    fMTM = fromMachType + "-" + fromModel;

                    if(fromMTM.equals(fMTM))
                    {
                        arrayTM[index].put(key, charges_Feature_Conversions_TM.get(key));
                    }
                }//end of while(tmItr.hasNext())
                index++;
            }//end of while(tsItr.hasNext())

            index = 0;
            tsItr = ts.iterator();
            while(tsItr.hasNext())
            {
                String fromMTM = (String) tsItr.next();
                retrievePNFeatureConversionsForWithdrawFormat2(fmtType, outSB, fromMTM, arrayTM[index]);
                index++;
            }

            for(int i = 0; i < ts.size(); i++)
            {
                arrayTM[i].clear();
                arrayTM[i] = null;
            }
            ts.clear();
            ts = null;
        }//end of if(charges_Feature_Conversions_TM.size() > 0)
    }

    /********************************************************************************
    *
    *
    * @param fmtType boolean
    * @param outSB StringBuffer
    * @param mtm String
    * @param tm TreeMap
    */
    private void retrievePNFeatureConversionsForWithdrawFormat2(boolean fmtType, StringBuffer outSB, String mtm, TreeMap tm)
    {
        TreeSet ts;
        Set tmSet;
        Iterator tmItr;
        TreeMap [] arrayTM;
        int index;
        Iterator tsItr;

        outSB.append("The following feature conversions for the " + mtm + " are being withdrawn:" + NEWLINE);
        outSB.append(".sk 1" + NEWLINE);

        //Group entries based on HWFCCAT(FEATURE)
        ts = new TreeSet();
        tmSet = tm.keySet();
        tmItr = tmSet.iterator();
        while(tmItr.hasNext())
        {
            String key = (String) tmItr.next();
            ts.add(parseString(key, 1, "<:>"));
        }

        arrayTM = new TreeMap[ts.size()];
        for(int i = 0; i < ts.size(); i++)
        {
            arrayTM[i] = new TreeMap();
        }

        index = 0;
        tsItr = ts.iterator();
        while(tsItr.hasNext())
        {
            String hwfccat = (String) tsItr.next();

            tmItr = tmSet.iterator();
            while(tmItr.hasNext())
            {
                String key = (String) tmItr.next();

                if(hwfccat.equals(parseString(key, 1, "<:>")))
                {
                    arrayTM[index].put(key, tm.get(key));
                }
            }//end of while(tmItr.hasNext())
            index++;
        }//end of while(tsItr.hasNext())

        for(int i = 0; i < arrayTM.length; i++)
        {
            retrievePNFeatureConversionsForWithdrawFormat2(fmtType, outSB, arrayTM[i]);
        }
    }

    /********************************************************************************
    *
    *
    * @param fmtType boolean
    * @param outSB StringBuffer
    * @param tm TreeMap
    */
    private void retrievePNFeatureConversionsForWithdrawFormat2(boolean fmtType, StringBuffer outSB, TreeMap tm)
    {
        String prevGeo = "";
        String currentGeo = "";
        String hwfccat = parseString((String) tm.firstKey(), 1, "<:>");
        int totalLines;
        int nLines;
        boolean firstLine;
        Set tmSet;
        Iterator tmItr;
        outSB.append(":h5.Feature conversions for " + hwfccat + " features:" + NEWLINE);
        outSB.append(":xmp." + NEWLINE);
        //outSB.append(".kp off\n");
        outSB.append("                                                            RETURN" + NEWLINE);
        outSB.append("From FC:                      To FC:                        PARTS" + NEWLINE);
        outSB.append("---------------------------   ---------------------------   ------" + NEWLINE);
        outSB.append(":exmp." + NEWLINE);
        totalLines = 0;
        nLines = 0;
        firstLine = false;
        tmSet = tm.keySet();
        tmItr = tmSet.iterator();
        while(tmItr.hasNext())
        {
            String fromFCDescription;
            String toFCDescription;
            String [] fromFCStringLines;
            String [] toFCStringLines;
            String key = (String) tmItr.next();
            currentGeo = (String) tm.get(key);
            setGeoTagsFeatConv(prevGeo, currentGeo, fmtType, outSB);
            if(parseString(key, I_11, "<:>").length() > 0)
            {
                if(fmtType == XMLFORMAT)
                {
//                  outSB.append("</pre>\n");
                    String [] arrayStr1 = getStringTokens(parseString(key, I_11, "<:>"), NEWLINE);
                    for(int i = 0; i < arrayStr1.length; i++)
                    {
                        outSB.append(arrayStr1[i] + NEWLINE);
                    }
//                  outSB.append("<pre>\n");
                }
                if(fmtType == GMLFORMAT)
                {
                    String [] arrayStr1 = getStringTokens(parseString(key, I_11, "<:>"), NEWLINE);
                    for(int i = 0; i < arrayStr1.length; i++)
                    {
                        if(arrayStr1[i].length() > I_58)
                        {
                            String [] arrayStr2 = extractStringLines(arrayStr1[i], I_58);
                            for(int j = 0; j < arrayStr2.length; j++)
                            {
                                outSB.append(":hp2." + arrayStr2[j] + ":ehp2." + NEWLINE);
                            }
                        }
                        else
                        {
                            outSB.append(":hp2." + arrayStr1[i] + ":ehp2." + NEWLINE);
                        }
                    }
                }
            }

            fromFCDescription = parseString(key, 7, "<:>") + " - " + parseString(key, 9, "<:>");
            toFCDescription = parseString(key, 4, "<:>") + " - " + parseString(key, 8, "<:>");
            fromFCStringLines = extractStringLines(fromFCDescription, I_27);
            toFCStringLines = extractStringLines(toFCDescription, I_27);
            totalLines = 0;
            if(fromFCStringLines.length > toFCStringLines.length)
            {
                totalLines = fromFCStringLines.length;
            }
            else
            {
                totalLines = toFCStringLines.length;
            }
            nLines = 0;
            firstLine = false;
            do
            {
                if(false == firstLine)
                {
                    //outSB.append(" ");
                    outSB.append(setString(fromFCStringLines[nLines], I_27));
                    outSB.append("   ");
                    outSB.append(setString(toFCStringLines[nLines], I_27));
                    outSB.append("    ");
                    outSB.append(parseString(key, 10, "<:>"));
                    outSB.append(NEWLINE);
                    firstLine = true;
                    nLines++;
                }
                else
                {
                    if(nLines < fromFCStringLines.length)
                    {
                        outSB.append(setString(fromFCStringLines[nLines], I_27));
                    }
                    else
                    {
                        outSB.append(setString(" ", I_27));
                    }
                    outSB.append("   ");
                    if(nLines < toFCStringLines.length)
                    {
                        outSB.append(setString(toFCStringLines[nLines], I_27));
                    }
                    else
                    {
                        outSB.append(setString(" ", I_27));
                    }
                    outSB.append(NEWLINE);
                    nLines++;
                }
            }while(nLines < totalLines);
            prevGeo = currentGeo;
        }//end of while(tmItr.hasNext())
        if(!currentGeo.equals("WW"))
        {
//            if(fmtType == XMLFORMAT)
//            {
//               outSB.append("</pre>\n");
//            }
            bldEndGeoTags(currentGeo, fmtType, outSB);
//            if(fmtType == XMLFORMAT)
//            {
//               outSB.append("<pre>");
//            }
        }
        if(fmtType == GMLFORMAT)
        {
            outSB.append(":exmp." + NEWLINE + NEWLINE);
        }
//         else
//         {
//            outSB.append("</pre><p> </p>");
//         }
    }

    /********************************************************************************
    *
    *
    * @param fmtType boolean
    * @param outSB StringBuffer
    */
    private void retrieveCharges(boolean fmtType, StringBuffer outSB)
    {
        outSB.append(".* <pre>" + NEWLINE);
        outSB.append(".* " + myDate() + NEWLINE);
        outSB.append(".* " + inventoryGroup + NEWLINE);
        //if(FORMAT1 == format)
        //   outSB.append(":h2.Charges\n");

        if((charges_NewModels_TM.size()  + charges_NewFC_TM.size() +
                charges_ExistingFC_TM.size() + productNumber_MTM_Conversions_TM.size() +
                productNumber_Model_Conversions_TM.size() + charges_Feature_Conversions_TM.size() +
                charges_NewModels_NewFC_TM.size() + charges_NewModels_ExistingFC_TM.size() +
                charges_ExistingModels_NewFC_TM.size() + charges_ExistingModels_ExistingFC_TM.size())  > 0)
        {
            log("annType = " + annType);
            log("charges_NewModels_TM.size() = " + charges_NewModels_TM.size());
            log("charges_NewFC_TM.size() = " + charges_NewFC_TM.size());
            log("charges_ExistingFC_TM.size() = " + charges_ExistingFC_TM.size());
            log("charges_NewModels_NewFC_TM.size() = " + charges_NewModels_NewFC_TM.size());
            log("charges_NewModels_ExistingFC_TM.size() = " + charges_NewModels_ExistingFC_TM.size());
            log("charges_ExistingModels_NewFC_TM.size() = " + charges_ExistingModels_NewFC_TM.size());
            log("charges_ExistingModels_ExistingFC_TM.size() = " + charges_ExistingModels_ExistingFC_TM.size());

            if(FORMAT1 == format)
            {
                retrieveChargesNewModelsFormat1(fmtType, outSB);
            }

            if((charges_NewFC_TM.size() + charges_ExistingFC_TM.size())  > 0)
            {
                if(FORMAT1 == format)
                {
                    //According to Alan Crudo 5/4/2005
                    //Eliminate the split between "New Features" and "Existing Features".  Put all features in sequence under the heading
                    //Features and Specify Codes" (eliminate the "No-Charge" in headings, and the separate "New Features" and "Existing  Features" headings.
                    //One suggested improvement. Blank out the $XXXX under the headings, "MMMC", "ESA 24X7", and "ESA 9X5".
                    //There are only a few features to which maintenance applies, and we can more easily add them in.
                    //outSB.append(":h3.Features and No-Charge Specify Codes\n");
                    outSB.append(":h3.Features and Specify Codes" + NEWLINE);
                }
            }

            if(FORMAT1 == format)
            {
                //According to Alan Crudo 5/4/2005
                //Eliminate the split between "New Features" and "Existing Features".  Put all features in sequence under the heading
                //Features and Specify Codes" (eliminate the "No-Charge" in headings, and the separate "New Features" and "Existing Features" headings.
                //One suggested improvement. Blank out the $XXXX under the headings, "MMMC", "ESA 24X7", and "ESA 9X5".
                //There are only a few features to which maintenance applies, and we can more easily add them in.

                //retrieveChargesFeaturesFormat1(NEWFC, charges_NewFC_TM, fmtType, outSB);
                //retrieveChargesFeaturesFormat1(EXISTINGFC, charges_ExistingFC_TM, fmtType, outSB);

                TreeMap aTM = new TreeMap();
                aTM.putAll(charges_NewFC_TM);
                aTM.putAll(charges_ExistingFC_TM);
                retrieveChargesFeaturesFormat1(aTM, fmtType, outSB);
                aTM.clear();
                aTM = null;
            }
            else if(FORMAT2 ==format || FORMAT3 == format)
            {
                retrieveChargesFeaturesFormat2(fmtType, outSB);
            }

            retrieveChargesMTMConversions(fmtType, outSB);
            retrieveChargesModelConversions(fmtType, outSB);
            if(FORMAT1 == format)
            {
                retrieveChargesFeatureConverstionsFormat1(fmtType, outSB);
            }
            else if(FORMAT2 == format || FORMAT3 == format)
            {
                retrieveChargesFeatureConverstionsFormat2(fmtType, outSB);
            }
        }
        else
        {
            if(fmtType == GMLFORMAT)
            {
                outSB.append(":p.No answer data found for Charges section." + NEWLINE);
            }
            else
            {
                outSB.append("<p>No answer data found for Charges section.</p>" + NEWLINE);
            }
        }
        outSB.append(".* </pre>" + NEWLINE);
    }

    /********************************************************************************
    *
    *
    * @param fmtType boolean
    * @param outSB StringBuffer
    */
    private void retrieveChargesNewModelsFormat1(boolean fmtType, StringBuffer outSB)
    {
        if(charges_NewModels_TM.size() > 0)
        {
            String prevGeo = "";
            String currentGeo = "";

            outSB.append(":h3.Models" + NEWLINE);

            if(FORMAT1 == format)
            {
                Set tmSet;
                Iterator tmItr;
                outSB.append(":xmp." + NEWLINE);
                outSB.append(".kp off" + NEWLINE + NEWLINE);
                outSB.append("                                           Purchase        ESA" + NEWLINE);
                outSB.append("Description                                Price     MMMC  24X7  CSU" + NEWLINE);
                outSB.append("-----------------------------------------  --------  ----  ----  ---" + NEWLINE);
                tmSet = charges_NewModels_TM.keySet();
                tmItr = tmSet.iterator();
                while(tmItr.hasNext())
                {
                    String key = (String) tmItr.next();
                    currentGeo = (String) charges_NewModels_TM.get(key);
                    setGeoTags(prevGeo, currentGeo, fmtType, outSB);
                    //outSB.append(" ");
                    outSB.append(setString(parseString(key, 3, "<:>"), I_41));
                    outSB.append("  ");
                    outSB.append("   $XXXX");
                    outSB.append("  ");
                    outSB.append("$XXX");
                    outSB.append("  ");
                    outSB.append("$XXX");
                    outSB.append("  ");
                    outSB.append(parseString(key, 5, "<:>"));
                    outSB.append(NEWLINE);

                    prevGeo = currentGeo;
                }//end of while(tmItr.hasNext())
                if(!currentGeo.equals("WW"))
                {
                    bldEndGeoTags(currentGeo, fmtType, outSB);
                }
                if(fmtType == GMLFORMAT)
                {
                    outSB.append(":exmp." + NEWLINE + NEWLINE);
                }
            }//end of if(FORMAT1 == format)
        }//end of if(charges_NewModels_TM.size() > 0)
    }

    /********************************************************************************
    *
    *
    * @param fType boolean
    * @param tm TreeMap
    * @param fmtType boolean
    * @param outSB StringBuffer
    */
//    private void retrieveChargesFeaturesFormat1(int fType, TreeMap tm, boolean fmtType, StringBuffer outSB)
//    {
//        if(tm.size() > 0)
//        {
//            switch(fType)
//            {
//            case NEWFC:
//                outSB.append(":h5.New Features" + NEWLINE);
                //outSB.append(":p.The following are newly announced features on the specified\n");
                //outSB.append("models.\n");
//                outSB.append(":xmp." + NEWLINE);
//                outSB.append(".kp off" + NEWLINE + NEWLINE);
//                break;

//            case EXISTINGFC:
//                outSB.append(":h5.Existing Features" + NEWLINE);
                //outSB.append(":p.The following features are already announced, for the specified\n");
                //outSB.append("machine type, and are now orderable on additional models.\n");
//                outSB.append(":xmp." + NEWLINE);
//                outSB.append(".kp off" + NEWLINE + NEWLINE);
//                break;
//            }//end of switch(fType)

//            String prevGeo = "";
//            String currentGeo = "";
//            String prevFeature = "";
//            String currentFeature = "";
//            String prevMT = "";
//            String currentMT ="";

//            if(FORMAT1 == format)
//            {
//                outSB.append("                             Feature  Purchase        ESA   ESA" + NEWLINE);
//                outSB.append("Description                  Number   Price     MMMC  24X7  9X5   CSU" + NEWLINE);
//                outSB.append("---------------------------- -------  --------  ----  ----  ----  ---" + NEWLINE);
//                Set tmSet = tm.keySet();
//                Iterator tmItr = tmSet.iterator();
//                while(tmItr.hasNext())
//                {
//                    String key = (String) tmItr.next();
//                    currentGeo = (String) tm.get(key);
//                    currentFeature = parseString(key, 1);
//                    currentMT = parseString(key, 2);
//                    if(!currentFeature.equals(prevFeature))
//                    {
//                        setGeoTags(prevGeo, currentGeo, fmtType, outSB);
//                    }
//                    if(parseString(key, 8).length() > 0)
//                    {
//                        if(!currentFeature.equals(prevFeature))
//                        {
//                            String [] arrayStr1 = getStringTokens(parseString(key, 8), NEWLINE);
//                            for(int i = 0; i < arrayStr1.length; i++)
//                            {
//                                if(arrayStr1[i].length() > 58)
//                                {
//                                    String [] arrayStr2 = extractStringLines(arrayStr1[i], 58);
//                                    for(int j = 0; j < arrayStr2.length; j++)
//                                    {
//                                        outSB.append(":hp2." + arrayStr2[j] + ":ehp2." + NEWLINE);
//                                    }
//                                }
//                                else
//                                {
//                                    outSB.append(":hp2." + arrayStr1[i] + ":ehp2." + NEWLINE);
//                                }
//                            }
//                        }//end of if(!currentFeature.equals(prevFeature)
//                    }
                    //Print feature code only once regardless of the number of machine type/model refers to it
//                    if(!currentFeature.equals(prevFeature))
//                    {
//                        outSB.append(parseString(key, 4));
//                        outSB.append("  ");
//                        outSB.append(parseString(key, 1));
//                        outSB.append("    ");
//                        outSB.append(parseString(key, 9));
//                        outSB.append("  ");
//                        outSB.append(parseString(key, 10));
//                        outSB.append("  ");
//                        outSB.append(parseString(key, 11));
//                        outSB.append("  ");
//                        outSB.append(parseString(key, 12));
//                        outSB.append("  ");
//                        outSB.append(parseString(key, 6));
//                        outSB.append(NEWLINE);
//                    }

//                    prevGeo = currentGeo;
//                    prevFeature = currentFeature;
//                    prevMT = currentMT;
//                }//end of while(tmItr.hasNext())
//                if(!currentGeo.equals("WW"))
//                {
//                    bldEndGeoTags(currentGeo, fmtType, outSB);
//                }

//                outSB.append(":exmp." + NEWLINE + NEWLINE);
                //if(fmtType == GMLFORMAT)
                //{
                //   outSB.append(":p.:hp2.US, LA, CAN--->:ehp2.\n");
                //   outSB.append(":p.:hp2.Feature Removal Prices::ehp2. Feature removals not associated\n");
                //   outSB.append("with MES upgrades are available for a charge.\n");
                //   outSB.append(".br;:hp2.<---US, LA, CAN:ehp2.\n\n");
                //}
                //else
                //{
                //   outSB.append("<i>US, LA, CAN---></i>");
                //   outSB.append("<p><i>Feature Removal Prices</i> Feature removals not associated ");
                //   outSB.append("with MES upgrades are available for a charge.</p>");
                //   outSB.append("<i><---US, LA, CAN</i>");
                //}
//            }//end of if(FORMAT1 == format))
//        }//end of if(tm.size() > 0)
//    }

    /********************************************************************************
    *
    *
    * @param tm TreeMap
    * @param fmtType boolean
    * @param outSB StringBuffer
    */
    private void retrieveChargesFeaturesFormat1(TreeMap tm, boolean fmtType, StringBuffer outSB)
    {
        if(tm.size() > 0)
        {
            String prevGeo = "";
            String currentGeo = "";
            String prevFeature = "";
            String currentFeature = "";
            String prevMT = "";
            String currentMT ="";

            if(FORMAT1 == format)
            {
                Set tmSet;
                Iterator tmItr;
                outSB.append(":xmp." + NEWLINE);
                outSB.append(".kp off" + NEWLINE + NEWLINE);
                outSB.append("                             Feature  Purchase        ESA   ESA" + NEWLINE);
                outSB.append("Description                  Number   Price     MMMC  24X7  9X5   CSU" + NEWLINE);
                outSB.append("---------------------------- -------  --------  ----  ----  ----  ---" + NEWLINE);
                tmSet = tm.keySet();
                tmItr = tmSet.iterator();
                while(tmItr.hasNext())
                {
                    String key = (String) tmItr.next();
                    currentGeo = (String) tm.get(key);
                    currentFeature = parseString(key, 1, "<:>");
                    currentMT = parseString(key, 2, "<:>");
                    if(!currentFeature.equals(prevFeature))
                    {
                        setGeoTags(prevGeo, currentGeo, fmtType, outSB);
                    }
                    if(parseString(key, 8, "<:>").length() > 0)
                    {
                        if(!currentFeature.equals(prevFeature))
                        {
                            String [] arrayStr1 = getStringTokens(parseString(key, 8, "<:>"), NEWLINE);
                            for(int i = 0; i < arrayStr1.length; i++)
                            {
                                if(arrayStr1[i].length() > I_58)
                                {
                                    String [] arrayStr2 = extractStringLines(arrayStr1[i], I_58);
                                    for(int j = 0; j < arrayStr2.length; j++)
                                    {
                                        outSB.append(":hp2." + arrayStr2[j] + ":ehp2." + NEWLINE);
                                    }
                                }
                                else
                                {
                                    outSB.append(":hp2." + arrayStr1[i] + ":ehp2." + NEWLINE);
                                }
                            }
                        }//end of if(!currentFeature.equals(prevFeature)
                    }
                    //Print feature code only once regardless of the number of machine type/model refers to it
                    if(!currentFeature.equals(prevFeature))
                    {
                        outSB.append(parseString(key, 4, "<:>"));
                        outSB.append("  ");
                        outSB.append(parseString(key, 1, "<:>"));
                        outSB.append("    ");
                        outSB.append(parseString(key, 9, "<:>"));
                        outSB.append("  ");
                        outSB.append(parseString(key, 10, "<:>"));
                        outSB.append("  ");
                        outSB.append(parseString(key, I_11, "<:>"));
                        outSB.append("  ");
                        outSB.append(parseString(key, I_12, "<:>"));
                        outSB.append("  ");
                        outSB.append(parseString(key, 6, "<:>"));
                        outSB.append(NEWLINE);
                    }

                    prevGeo = currentGeo;
                    prevFeature = currentFeature;
                    prevMT = currentMT;
                }//end of while(tmItr.hasNext())
                if(!currentGeo.equals("WW"))
                {
                    bldEndGeoTags(currentGeo, fmtType, outSB);
                }

                outSB.append(":exmp." + NEWLINE + NEWLINE);
                //if(fmtType == GMLFORMAT)
                //{
                //   outSB.append(":p.:hp2.US, LA, CAN--->:ehp2.\n");
                //   outSB.append(":p.:hp2.Feature Removal Prices::ehp2. Feature removals not associated\n");
                //   outSB.append("with MES upgrades are available for a charge.\n");
                //   outSB.append(".br;:hp2.<---US, LA, CAN:ehp2.\n\n");
                //}
                //else
                //{
                //   outSB.append("<i>US, LA, CAN---></i>");
                //   outSB.append("<p><i>Feature Removal Prices</i> Feature removals not associated ");
                //   outSB.append("with MES upgrades are available for a charge.</p>");
                //   outSB.append("<i><---US, LA, CAN</i>");
                //}
            }//end of if(FORMAT1 == format))
        }//end of if(productNumber_Features_TM.size() > 0)
    }

    /********************************************************************************
    *
    * @param fmtType boolean
    * @param outSB StringBuffer
    */
    private void retrieveChargesFeaturesFormat2(boolean fmtType, StringBuffer outSB)
    {
        Iterator aItr = machineTypeTS.iterator();

        while(aItr.hasNext())
        {
            String machineType = (String) aItr.next();

            if(FORMAT2 == format)
            {
                retrieveChargesFeaturesFormat2_MTM(machineType, fmtType, outSB);
            }
            else if(FORMAT3 == format)
            {
                retrieveChargesFeaturesFormat3_MTM(machineType, fmtType, outSB);
            }
            retrieveChargesFeaturesFormat2_Feature(machineType, fmtType, outSB);
        }//end of while(aItr.hasNext())
    }

    /********************************************************************************
    *
    *
    * @param mt String
    * @param fmtType boolean
    * @param outSB StringBuffer
    */
    private void retrieveChargesFeaturesFormat2_MTM(String mt, boolean fmtType, StringBuffer outSB)
    {
        TreeMap newFC_TM = new TreeMap();
        TreeMap existingFC_TM = new TreeMap();
        TreeSet modelTS = new TreeSet();

        if(charges_NewModels_NewFC_TM.size() > 0)
        {
            Set tmSet = charges_NewModels_NewFC_TM.keySet();
            Iterator tmItr = tmSet.iterator();
            while(tmItr.hasNext())
            {
                String key = (String) tmItr.next();

                if(mt.equals(parseString(key, 1, "<:>")))
                {
                    String model = parseString(key, 3, "<:>");
                    String mktgName = parseString(key, 10, "<:>");
                    modelTS.add(model + "<:>" + mktgName);
                    newFC_TM.put(key, charges_NewModels_NewFC_TM.get(key));
                }
            }//end of while(tmItr.hasNext())
        }//end of if(charges_NewModels_NewFC_TM.size() > 0)
        retrieveChargesFeaturesFormat2_MTM(mt, modelTS, NEWFC, newFC_TM, fmtType, outSB);
        modelTS.clear();

        if(charges_NewModels_ExistingFC_TM.size() > 0)
        {
            Set tmSet = charges_NewModels_ExistingFC_TM.keySet();
            Iterator tmItr = tmSet.iterator();
            while(tmItr.hasNext())
            {
                String key = (String) tmItr.next();

                if(mt.equals(parseString(key, 1, "<:>")))
                {
                    String model = parseString(key, 3, "<:>");
                    modelTS.add(model);
                    existingFC_TM.put(key, charges_NewModels_ExistingFC_TM.get(key));
                }
            }//end of while(tmItr.hasNext())
        }//end of if(charges_NewModels_ExistingFC_TM.size() > 0)

        retrieveChargesFeaturesFormat2_MTM(mt, modelTS, EXISTINGFC, existingFC_TM, fmtType, outSB);

        modelTS.clear();
        modelTS = null;
        newFC_TM.clear();
        newFC_TM = null;
        existingFC_TM.clear();
        existingFC_TM = null;
    }

    /********************************************************************************
    *
    *
    * @param machineType String
    * @param mTS TreeSet
    * @param fType int
    * @param tm TreeMap
    * @param fmtType boolean
    * @param outSB StringBuffer
    */
    private void retrieveChargesFeaturesFormat2_MTM(String machineType, TreeSet mTS, int fType, TreeMap tm, boolean fmtType, StringBuffer outSB)
    {
        if(tm.size() > 0)
        {
            String[] arrayStr;
            String headerString = "";

            switch(fType)
            {
            case NEWFC:
                if(brand.equals(PSERIES))
                {
                    headerString = "of the IBM RS/6000 or pSeries " + machineType + " machine type:";
                }
                else if(brand.equals(XSERIES))
                {
                    headerString = "of the IBM xSeries " + machineType + " machine type:";
                }
                //else if(brand.equals(ZSERIES))
                //{
                //   headerString = "of the IBM S/390 or zSeries " + machineType + " machine type:";
                //}
                else if(brand.equals(TS))
                {
                    headerString = "of the Total Storage " + machineType + " machine type:";
                }

                headerString = ":p.The following are newly announced features on the specified models " + headerString;
                arrayStr = extractStringLines(headerString, I_70);
                for(int i = 0; i < arrayStr.length; i++)
                {
                    outSB.append(arrayStr[i] + NEWLINE);
                }
                break;

            case EXISTINGFC:
                headerString = ":p.The following are features already announced for the " + machineType + " machine type:";
                arrayStr = extractStringLines(headerString, I_70);
                for(int i = 0; i < arrayStr.length; i++)
                {
                    outSB.append(arrayStr[i] + NEWLINE);
                }
                break;

            default:
                break;
            }//end of switch(fType)

            outSB.append(".RH ON" + NEWLINE);
            //outSB.append(":xmp." + NEWLINE);
            outSB.append(".fo off" + NEWLINE);
            outSB.append(".in 2" + NEWLINE);
            //outSB.append(".kp off\n");
            outSB.append("                                             Minimum" + NEWLINE);
            outSB.append("                                             Maint.  Initial/" + NEWLINE);
            outSB.append("                     Model  Feature Purchase Charge  MES/         RP" + NEWLINE);
            outSB.append("Description          Number Numbers Price    Monthly Both     CSU MES" + NEWLINE);
            outSB.append("-------------------- ------ ------- -------- ------- -------- --- ---" + NEWLINE);
            //outSB.append(":exmp." + NEWLINE);
            outSB.append(".fo on" + NEWLINE);
            outSB.append(".RH OFF" + NEWLINE);

            if(mTS.size() > 0)
            {
                Iterator tsItr = mTS.iterator();

                outSB.append(".pa" + NEWLINE);
                outSB.append(":xmp." + NEWLINE);
                outSB.append(".kp off" + NEWLINE);

                while(tsItr.hasNext())
                {
                    String key = (String) tsItr.next();
                    String model = parseString(key, 1, "<:>");
                    String [] stringLines = extractStringLines(parseString(key, 2, "<:>"), I_51);
                    for(int i = 0; i < stringLines.length; i++)
                    {
                        outSB.append(stringLines[i]);
                        outSB.append(NEWLINE);
                    }
                    outSB.append(setString("", I_22));
                    outSB.append(model);
                    outSB.append(setString("", I_12));
                    outSB.append("XXXX.XX");
                    outSB.append(" ");
                    outSB.append("XXXX.XX");
                    outSB.append(setString("", 10));
                    outSB.append(productNumber_NewModels_HT.get(model));
                    outSB.append(NEWLINE);
                }
                //outSB.append(":exmp." + NEWLINE);
                //outSB.append(".pa\n");
            }//end of if(aTS1.size() > 0)

            retrieveChargesFeaturesFormat2_MTM(machineType, tm, fmtType, outSB);
        }//end of if(tm.size() > 0))
    }

    /********************************************************************************
    *
    *
    * @param mt String
    * @param tm TreeMap
    * @param fmtType boolean
    * @param outSB StringBuffer
    */
    private void retrieveChargesFeaturesFormat2_MTM(String mt, TreeMap tm, boolean fmtType, StringBuffer outSB)
    {
        if(tm.size() > 0)
        {
            String prevGeo = "";
            String currentGeo = "";
            String prevFeature = "";
            String currentFeature = "";
            String prevMT = "";
            String currentMT ="";

            String [] stringLines = null;
            boolean printHeader = false;
            Set tmSet = tm.keySet();
            Iterator tmItr = tmSet.iterator();
            //outSB.append(":xmp." + NEWLINE); //According to OIM3.0b specs: remove :xmp. and :exmp. pair appeared around each feature
            while(tmItr.hasNext())
            {
                String key = (String) tmItr.next();
                currentGeo = (String) tm.get(key);
                currentFeature = parseString(key, 2, "<:>");
                currentMT = mt;
                if(!currentFeature.equals(prevFeature) || !currentMT.equals(prevMT))
                {
                    stringLines = extractStringLines(parseString(key, 4, "<:>"), I_51);
                    printHeader = true;
                }
                setGeoTags3(prevGeo, currentGeo, prevMT, currentMT, prevFeature, currentFeature, fmtType, outSB);

                if(parseString(key, 8, "<:>").length() > 0)
                {
                    if(!currentFeature.equals(prevFeature))
                    {
                        String [] arrayStr1 = getStringTokens(parseString(key, 8, "<:>"), NEWLINE);
                        for(int i = 0; i < arrayStr1.length; i++)
                        {
                            if(arrayStr1[i].length() > I_58)
                            {
                                String [] arrayStr2 = extractStringLines(arrayStr1[i], I_58);
                                for(int j = 0; j < arrayStr2.length; j++)
                                {
                                    outSB.append(":hp2." + arrayStr2[j] + ":ehp2." + NEWLINE);
                                }
                            }
                            else
                            {
                                outSB.append(":hp2." + arrayStr1[i] + ":ehp2." + NEWLINE);
                            }
                        }
                    }//end of if(!currentFeature.equals(prevFeature))
                }

                if(printHeader)
                {
                    for(int i = 0; i < stringLines.length; i++)
                    {
                        outSB.append(stringLines[i] + NEWLINE);
                    }
                    printHeader = false;
                }

                outSB.append(setString("", I_22));
                outSB.append(parseString(key, 3, "<:>"));
                outSB.append("      ");

                if(!currentFeature.equals(prevFeature))
                {
                    outSB.append(parseString(key, 2, "<:>"));
                }
                else
                {
                    outSB.append("    ");
                }
                outSB.append("  ");

                if(!currentFeature.equals(prevFeature))
                {
                    outSB.append(parseString(key, 9, "<:>"));
                }
                else
                {
                    outSB.append("       ");
                }
                outSB.append("         ");
                outSB.append(parseString(key, 5, "<:>"));
                outSB.append("  ");
                outSB.append(parseString(key, 6, "<:>"));
                outSB.append(" ");
                outSB.append(parseString(key, 7, "<:>"));
                outSB.append(NEWLINE);

                prevGeo = currentGeo;
                prevFeature = currentFeature;
                prevMT = currentMT;
            }//end of while(tmItr.hasNext())
            if(!currentGeo.equals("WW"))
            {
                bldEndGeoTags(currentGeo, fmtType, outSB);
            }

            outSB.append(":exmp." + NEWLINE);
            outSB.append(".RH CANCEL" + NEWLINE + NEWLINE);
        }//end of if(productNumber_Features_TM.size() > 0)
    }

    /********************************************************************************
    *
    *
    * @param mt String
    * @param fmtType boolean
    * @param outSB StringBuffer
    */
    private void retrieveChargesFeaturesFormat2_Feature(String mt, boolean fmtType, StringBuffer outSB)
    {
        TreeMap newFC_TM = new TreeMap();
        TreeMap existingFC_TM = new TreeMap();

        if(charges_ExistingModels_NewFC_TM.size() > 0)
        {
            Set tmSet = charges_ExistingModels_NewFC_TM.keySet();
            Iterator tmItr = tmSet.iterator();
            while(tmItr.hasNext())
            {
                String key = (String) tmItr.next();

                if(mt.equals(parseString(key, 1, "<:>")))
                {
                    newFC_TM.put(key, charges_ExistingModels_NewFC_TM.get(key));
                }
            }//end of while(tmItr.hasNext())
        }//end of if(charges_ExistingModels_NewFC_TM.size() > 0)

        if(charges_ExistingModels_ExistingFC_TM.size() > 0)
        {
            Set tmSet = charges_ExistingModels_ExistingFC_TM.keySet();
            Iterator tmItr = tmSet.iterator();
            while(tmItr.hasNext())
            {
                String key = (String) tmItr.next();

                if(mt.equals(parseString(key, 1, "<:>")))
                {
                    existingFC_TM.put(key, charges_ExistingModels_ExistingFC_TM.get(key));
                }
            }//end of while(tmItr.hasNext())
        }//end of if(charges_ExistingModels_ExistingFC_TM.size() > 0)

        retrieveChargesFeaturesFormat2_Feature(NEWFC, newFC_TM, mt, fmtType, outSB);
        retrieveChargesFeaturesFormat2_Feature(EXISTINGFC, existingFC_TM, mt, fmtType, outSB);

        newFC_TM.clear();
        newFC_TM = null;
        existingFC_TM.clear();
        existingFC_TM = null;
    }

    /********************************************************************************
    *
    *
    * @param fType int
    * @param tm TreeMap
    * @param mt String
    * @param fmtType boolean
    * @param outSB StringBuffer
    */
    private void retrieveChargesFeaturesFormat2_Feature(int fType, TreeMap tm, String mt, boolean fmtType, StringBuffer outSB)
    {
        if(tm.size() > 0)
        {
            String prevGeo = "";
            String currentGeo = "";
            String prevFeature = "";
            String currentFeature = "";
            String prevMT = "";
            String currentMT ="";
            String headerString = "";
            String[] arrayStr;
            String [] stringLines;
            int nLines;
            boolean printHeader;
            Set tmSet;
            Iterator tmItr;

            TreeSet aTS1 = new TreeSet();
            Set aSet = tm.keySet();
            Iterator aItr = aSet.iterator();
            while(aItr.hasNext())
            {
                String key = (String) aItr.next();
                String model = parseString(key, 3, "<:>");
                aTS1.add(model);
            }//end of while(aItr.hasNext())


            if(brand.equals(PSERIES))
            {
                headerString = "of the IBM RS/6000 or pSeries " + mt + " machine type:";
            }
            else if(brand.equals(XSERIES))
            {
                headerString = "of the IBM xSeries " + mt + " machine type:";
            }
            //else if(brand.equals(ZSERIES))
            //{
            //   headerString = "of the IBM S/390 or zSeries " + mt + " machine type:";
            //}
            else if(brand.equals(TS))
            {
                headerString = "of the Total Storage " + mt + " machine type:";
            }

            switch(fType)
            {
            case NEWFC:
                headerString = ":p.The following are newly announced features on the specified models " + headerString;
                arrayStr = extractStringLines(headerString, I_70);
                for(int i = 0; i < arrayStr.length; i++)
                {
                    outSB.append(arrayStr[i] + NEWLINE);
                }
                break;

            case EXISTINGFC:
                headerString = ":p.The following are features already announced for the " + mt + " machine type:";
                arrayStr = extractStringLines(headerString, I_70);
                for(int i = 0; i < arrayStr.length; i++)
                {
                    outSB.append(arrayStr[i] + NEWLINE);
                }
                break;

            default:
                break;
            }//end of switch(fType)

            outSB.append(".RH ON" + NEWLINE);
            //outSB.append(":xmp." + NEWLINE);
            outSB.append(".fo off" + NEWLINE);
            outSB.append(".in 2" + NEWLINE);
            //outSB.append(".kp off\n");
            outSB.append("                                                     Initial/" + NEWLINE);
            outSB.append("                                                     MES/" + NEWLINE);
            outSB.append("Description                  Model  Feature Purchase Both/        RP" + NEWLINE);
            //outSB.append("Machine Type ");
            //outSB.append(setString(mt, 4));
            //outSB.append("            Number Numbers Price    Support  CSU MES" + NEWLINE);
            outSB.append("                             Number Numbers Price    Support  CSU MES" + NEWLINE);
            outSB.append("---------------------------- ------ ------- -------- -------- --- ---" + NEWLINE);
            //outSB.append(":exmp." + NEWLINE);
            outSB.append(".fo on" + NEWLINE);
            outSB.append(".RH OFF" + NEWLINE);

            stringLines = null;
            nLines = 0;
            printHeader = false;
            tmSet = tm.keySet();
            tmItr = tmSet.iterator();
            outSB.append(":xmp." + NEWLINE); //According to OIM3.0b specs: remove :xmp. and :exmp. pair appeared around each feature
            outSB.append(".kp off" + NEWLINE);
            while(tmItr.hasNext())
            {
                String key = (String) tmItr.next();
                currentGeo = (String) tm.get(key);
                currentFeature = parseString(key, 2, "<:>");
                currentMT = mt;
                if(!currentFeature.equals(prevFeature) || !currentMT.equals(prevMT))
                {
                    stringLines = extractStringLines(parseString(key, 4, "<:>"), I_51);
                    printHeader = true;
                }
                setGeoTags3(prevGeo, currentGeo, prevMT, currentMT, prevFeature, currentFeature, fmtType, outSB);

                if(parseString(key, 8, "<:>").length() > 0)
                {
                    if(!currentFeature.equals(prevFeature))
                    {
                        String [] arrayStr1 = getStringTokens(parseString(key, 8, "<:>"), NEWLINE);
                        for(int i = 0; i < arrayStr1.length; i++)
                        {
                            if(arrayStr1[i].length() > I_58)
                            {
                                String [] arrayStr2 = extractStringLines(arrayStr1[i], I_58);
                                for(int j = 0; j < arrayStr2.length; j++)
                                {
                                    outSB.append(":hp2." + arrayStr2[j] + ":ehp2." + NEWLINE);
                                }
                            }
                            else
                            {
                                outSB.append(":hp2." + arrayStr1[i] + ":ehp2." + NEWLINE);
                            }
                        }
                    }//end of if(!currentFeature.equals(prevFeature))
                }

                if(printHeader)
                {
                    for(int i = 0; i < stringLines.length; i++)
                    {
                        outSB.append(stringLines[i] + NEWLINE);
                    }
                    printHeader = false;
                }

                outSB.append(setString("", I_30));
                outSB.append(parseString(key, 3, "<:>"));
                outSB.append("      ");

                if(!currentFeature.equals(prevFeature))
                {
                    outSB.append(parseString(key, 2, "<:>"));
                }
                else
                {
                    outSB.append("    ");
                }
                outSB.append("  ");

                if(!currentFeature.equals(prevFeature))
                {
                    outSB.append(parseString(key, 9, "<:>"));
                }
                else
                {
                    outSB.append("       ");
                }
                outSB.append(" ");
                outSB.append(parseString(key, 5, "<:>"));
                outSB.append("  ");
                outSB.append(parseString(key, 6, "<:>"));
                outSB.append(" ");
                outSB.append(parseString(key, 7, "<:>"));
                outSB.append(NEWLINE);

                prevGeo = currentGeo;
                prevFeature = currentFeature;
                prevMT = currentMT;
            }//end of while(tmItr.hasNext())
            if(!currentGeo.equals("WW"))
            {
                bldEndGeoTags(currentGeo, fmtType, outSB);
            }

            outSB.append(":exmp." + NEWLINE);
            outSB.append(".RH CANCEL" + NEWLINE + NEWLINE);
        }//end of if(productNumber_Features_TM.size() > 0)
    }

    /********************************************************************************
    *
    *
    * @param fmtType boolean
    * @param outSB StringBuffer
    */
    private void retrieveChargesMTMConversions(boolean fmtType, StringBuffer outSB)
    {
        if(productNumber_MTM_Conversions_TM.size() > 0)
        {
            String prevGeo = "";
            String currentGeo = "";
            Set tmSet;
            Iterator tmItr;
            if(fmtType == GMLFORMAT)
            {
                outSB.append(":h3.Type/Model Conversions" + NEWLINE);
                outSB.append(":xmp." + NEWLINE);
                outSB.append(".kp off" + NEWLINE + NEWLINE);
            }
            else
            {
                outSB.append("<h3>Type/Model Conversions</h3>" + NEWLINE);
            }

            outSB.append("   From       To      Parts     Purchase" + NEWLINE);
            outSB.append("Type Model Type Model Returned  Price" + NEWLINE);
            outSB.append("---- ----- ---- ----- --------  --------" + NEWLINE);
            tmSet = productNumber_MTM_Conversions_TM.keySet();
            tmItr = tmSet.iterator();
            while(tmItr.hasNext())
            {
                String key = (String) tmItr.next();
                currentGeo = (String) productNumber_MTM_Conversions_TM.get(key);
                setGeoTags(prevGeo, currentGeo, fmtType, outSB);
                //outSB.append(" ");
                outSB.append(parseString(key, 3, "<:>"));
                outSB.append("  ");
                outSB.append(parseString(key, 4, "<:>"));
                outSB.append("  ");
                outSB.append(parseString(key, 1, "<:>"));
                outSB.append("  ");
                outSB.append(parseString(key, 2, "<:>"));
                outSB.append("     ");
                outSB.append(parseString(key, 5, "<:>"));
                outSB.append("       ");
                outSB.append("$XXXX");
                outSB.append(NEWLINE);

                prevGeo = currentGeo;
            }//end of while(tmItr.hasNext())
            if(!currentGeo.equals("WW"))
            {
//            if(fmtType == XMLFORMAT)
//            {
//               outSB.append("</pre>\n");
//            }
                bldEndGeoTags(currentGeo, fmtType, outSB);
//            if(fmtType == XMLFORMAT)
//            {
//               outSB.append("<pre>");
//            }
            }
            if(fmtType == GMLFORMAT)
            {
                outSB.append(":exmp." + NEWLINE + NEWLINE);
            }
//         else
//         {
//            outSB.append("</pre><p> </p>");
//         }
        }//end of if(productNumber_MTM_Conversions_TM.size() > 0)
    }

    /********************************************************************************
    *
    *
    * @param fmtType boolean
    * @param outSB StringBuffer
    */
    private void retrieveChargesModelConversions(boolean fmtType, StringBuffer outSB)
    {
        if(productNumber_Model_Conversions_TM.size() > 0)
        {
            String prevGeo = "";
            String currentGeo = "";
            Set tmSet;
            Iterator tmItr;
            if(fmtType == GMLFORMAT)
            {
                outSB.append(":h3.Model Conversions" + NEWLINE);
                outSB.append(":xmp." + NEWLINE);
                outSB.append(".kp off" + NEWLINE + NEWLINE);
            }
            else
            {
                outSB.append("<h3>Model Conversions</h3>" + NEWLINE);
            }

            outSB.append("      From   To      Parts     Purchase" + NEWLINE);
            outSB.append("Type  Model  Model   Returned  Price" + NEWLINE);
            outSB.append("----  -----  -----   --------  --------" + NEWLINE);
            tmSet = productNumber_Model_Conversions_TM.keySet();
            tmItr = tmSet.iterator();
            while(tmItr.hasNext())
            {
                String key = (String) tmItr.next();
                currentGeo = (String) productNumber_Model_Conversions_TM.get(key);
                setGeoTags(prevGeo, currentGeo, fmtType, outSB);
                //outSB.append(" ");
                outSB.append(parseString(key, 1, "<:>"));
                outSB.append("   ");
                outSB.append(parseString(key, 3, "<:>"));
                outSB.append("    ");
                outSB.append(parseString(key, 2, "<:>"));
                outSB.append("       ");
                outSB.append(parseString(key, 4, "<:>"));
                outSB.append("       ");
                outSB.append("$XXXX");
                outSB.append(NEWLINE);

                prevGeo = currentGeo;
            }//end of while(tmItr.hasNext())
            if(!currentGeo.equals("WW"))
            {
//            if(fmtType == XMLFORMAT)
//            {
//               outSB.append("</pre>\n");
//            }
                bldEndGeoTags(currentGeo, fmtType, outSB);
//            if(fmtType == XMLFORMAT)
//            {
//               outSB.append("<pre>");
//            }
            }
            if(fmtType == GMLFORMAT)
            {
                outSB.append(":exmp." + NEWLINE + NEWLINE);
            }
//         else
//         {
//            outSB.append("</pre><p> </p>");
//         }
        }//end of if(productNumber_Model_Conversions_TM.size() > 0)
    }

    /********************************************************************************
    *
    *
    * @param fmtType boolean
    * @param outSB StringBuffer
    */
    private void retrieveChargesFeatureConverstionsFormat1(boolean fmtType, StringBuffer outSB)
    {
        if(charges_Feature_Conversions_TM.size() > 0)
        {
            TreeMap tm = new TreeMap();
            String key = "";
            String toFeatureCode = "";
            String fromFeatureCode = "";
            String aKey = "";

            Set tmSet = charges_Feature_Conversions_TM.keySet();
            Iterator tmItr = tmSet.iterator();
            while(tmItr.hasNext())
            {
                key = (String) tmItr.next();
                toFeatureCode = parseString(key, 4, "<:>");
                fromFeatureCode = parseString(key, 7, "<:>");
                aKey = toFeatureCode + "<:>" + fromFeatureCode + "<:>" + parseString(key, 10, "<:>") + "<:>" + parseString(key, I_11, "<:>");
                tm.put(aKey, charges_Feature_Conversions_TM.get(key));
            }

            retrieveChargesFeatureConverstionsFormat1(fmtType, outSB, tm);
        }//end of if(charges_Feature_Conversions_TM.size() > 0)
    }

    /********************************************************************************
    *
    *
    * @param fmtType boolean
    * @param outSB StringBuffer
    * @param tm TreeMap
    */
    private void retrieveChargesFeatureConverstionsFormat1(boolean fmtType, StringBuffer outSB, TreeMap tm)
    {
        String prevGeo = "";
        String currentGeo = "";

        if(FORMAT1 == format)
        {
            Set tmSet;
            Iterator tmItr;
            outSB.append(":h3.Conversions" + NEWLINE);
            outSB.append(":p." + NEWLINE);
            outSB.append(":h5.Feature Conversions" + NEWLINE);
            outSB.append(":xmp." + NEWLINE);
            outSB.append(".kp off" + NEWLINE);
            outSB.append("               Parts       Purchase" + NEWLINE);
            outSB.append("From:   To:    Returned    Price" + NEWLINE);
            outSB.append("----    ---    --------    --------" + NEWLINE);
            tmSet = tm.keySet();
            tmItr = tmSet.iterator();
            while(tmItr.hasNext())
            {
                String key = (String) tmItr.next();
                currentGeo = (String) tm.get(key);
                setGeoTags(prevGeo, currentGeo, fmtType, outSB);
                if(parseString(key, 4, "<:>").length() > 0)
                {
                    if(fmtType == XMLFORMAT)
                    {
//                  outSB.append("</pre>\n");
                        String [] arrayStr1 = getStringTokens(parseString(key, 4, "<:>"), NEWLINE);
                        for(int i = 0; i < arrayStr1.length; i++)
                        {
                            outSB.append(arrayStr1[i] + NEWLINE);
                        }
//                  outSB.append("<pre>\n");
                    }
                    if(fmtType == GMLFORMAT)
                    {
                        String [] arrayStr1 = getStringTokens(parseString(key, 4, "<:>"), NEWLINE);
                        for(int i = 0; i < arrayStr1.length; i++)
                        {
                            if(arrayStr1[i].length() > I_58)
                            {
                                String [] arrayStr2 = extractStringLines(arrayStr1[i], I_58);
                                for(int j = 0; j < arrayStr2.length; j++)
                                {
                                    outSB.append(":hp2." + arrayStr2[j] + ":ehp2." + NEWLINE);
                                }
                            }
                            else
                            {
                                outSB.append(":hp2." + arrayStr1[i] + ":ehp2." + NEWLINE);
                            }
                        }
                    }
                }
                //outSB.append(" ");
                outSB.append(parseString(key, 2, "<:>"));
                outSB.append("    ");
                outSB.append(parseString(key, 1, "<:>"));
                outSB.append("      ");
                outSB.append(parseString(key, 3, "<:>"));
                outSB.append("         $XXXX");
                outSB.append(NEWLINE);

                prevGeo = currentGeo;
            }//end of while(tmItr.hasNext())
            if(!currentGeo.equals("WW"))
            {
//            if(fmtType == XMLFORMAT)
//            {
//               outSB.append("</pre>\n");
//            }
                bldEndGeoTags(currentGeo, fmtType, outSB);
//            if(fmtType == XMLFORMAT)
//            {
//               outSB.append("<pre>");
//            }
            }
            if(fmtType == GMLFORMAT)
            {
                outSB.append(":exmp." + NEWLINE + NEWLINE);
            }
//         else
//         {
//            outSB.append("</pre><p> </p>");
//         }
            outSB.append(":p.:hp2.US, LA, CAN--->:ehp2." + NEWLINE);
            outSB.append(":p.:hp2.Parts Return::ehp2.  Parts removed or replaced as part of MES upgrades" + NEWLINE);
            outSB.append("or conversions become the property of IBM and must be returned." + NEWLINE);
            outSB.append(".br;:hp2.<---US, LA, CAN:ehp2." + NEWLINE + NEWLINE);
        }//end of if(brand.equals("iSeries"))
    }

    /********************************************************************************
    *
    *
    * @param fmtType boolean
    * @param outSB StringBuffer
    */
    private void retrieveChargesFeatureConverstionsFormat2(boolean fmtType, StringBuffer outSB)
    {
        if(charges_Feature_Conversions_TM.size() > 0)
        {
            TreeSet ts;
            Set tmSet;
            Iterator tmItr;
            TreeMap [] arrayTM;
            int index;
            Iterator tsItr;

            if(fmtType == GMLFORMAT)
            {
                outSB.append(":h3.Feature Conversions" + NEWLINE);
            }
            else
            {
                outSB.append("<h3>Feature Conversions</h3>" + NEWLINE);
            }

            //Group entries based on HWFCCAT(FEATURE)
            ts = new TreeSet();
            tmSet = charges_Feature_Conversions_TM.keySet();
            tmItr = tmSet.iterator();
            while(tmItr.hasNext())
            {
                String key = (String) tmItr.next();
                ts.add(parseString(key, 1, "<:>"));
            }

            arrayTM = new TreeMap[ts.size()];
            for(int i = 0; i < ts.size(); i++)
            {
                arrayTM[i] = new TreeMap();
            }

            index = 0;
            tsItr = ts.iterator();
            while(tsItr.hasNext())
            {
                String hwfccat = (String) tsItr.next();

                tmItr = tmSet.iterator();
                while(tmItr.hasNext())
                {
                    String key = (String) tmItr.next();

                    if(hwfccat.equals(parseString(key, 1, "<:>")))
                    {
                        arrayTM[index].put(key, charges_Feature_Conversions_TM.get(key));
                    }
                }//end of while(tmItr.hasNext())
                index++;
            }//end of while(tsItr.hasNext())

            for(int i = 0; i < arrayTM.length; i++)
            {
                retrieveChargesFeatureConversionsFormat2(outSB, fmtType, arrayTM[i]);
            }
        }//end of if(charges_Feature_Conversions_TM.size() > 0)
    }

    /********************************************************************************
    *
    *
    * @param outSB StringBuffer
    * @param fmtType boolean
    * @param tm TreeMap
    */
    private void retrieveChargesFeatureConversionsFormat2(StringBuffer outSB, boolean fmtType, TreeMap tm)
    {
        String prevGeo = "";
        String currentGeo = "";
        String hwfccat = parseString((String) tm.firstKey(), 1, "<:>");
        if(fmtType == GMLFORMAT)
        {
            outSB.append(":p." + NEWLINE);
            outSB.append(":h4." + hwfccat + NEWLINE);
            outSB.append(":xmp." + NEWLINE);
            outSB.append(".kp off" + NEWLINE + NEWLINE);
        }
        else
        {
            outSB.append("<h4>" + hwfccat + "</h4>" + NEWLINE);
        }

        if(FORMAT2 == format || FORMAT3 == format)
        {
            int totalLines;
            int nLines;
            boolean firstLine;
            Set tmSet;
            Iterator tmItr;

            outSB.append("                                                  Parts      Purchase" + NEWLINE);
            outSB.append("From FC                  To FC                    Returned   Price" + NEWLINE);
            outSB.append("------------------------ ------------------------ ---------  --------" + NEWLINE);
            totalLines = 0;
            nLines = 0;
            firstLine = false;
            tmSet = tm.keySet();
            tmItr = tmSet.iterator();
            while(tmItr.hasNext())
            {
                String fromFCDescription;
                String toFCDescription;
                String [] fromFCStringLines;
                String [] toFCStringLines;
                String key = (String) tmItr.next();
                currentGeo = (String) tm.get(key);
                setGeoTags(prevGeo, currentGeo, fmtType, outSB);
                if(parseString(key, I_11, "<:>").length() > 0)
                {
                    if(fmtType == XMLFORMAT)
                    {
//                  outSB.append("</pre>\n");
                        String [] arrayStr1 = getStringTokens(parseString(key, I_11, "<:>"), NEWLINE);
                        for(int i = 0; i < arrayStr1.length; i++)
                        {
                            outSB.append(arrayStr1[i] + NEWLINE);
                        }
//                  outSB.append("<pre>\n");
                    }
                    if(fmtType == GMLFORMAT)
                    {
                        String [] arrayStr1 = getStringTokens(parseString(key, I_11, "<:>"), NEWLINE);
                        for(int i = 0; i < arrayStr1.length; i++)
                        {
                            if(arrayStr1[i].length() > I_58)
                            {
                                String [] arrayStr2 = extractStringLines(arrayStr1[i], I_58);
                                for(int j = 0; j < arrayStr2.length; j++)
                                {
                                    outSB.append(":hp2." + arrayStr2[j] + ":ehp2." + NEWLINE);
                                }
                            }
                            else
                            {
                                outSB.append(":hp2." + arrayStr1[i] + ":ehp2." + NEWLINE);
                            }
                        }
                    }
                }

                fromFCDescription = parseString(key, 7, "<:>") + " - " + parseString(key, 9, "<:>");
                toFCDescription = parseString(key, 4, "<:>") + " - " + parseString(key, 8, "<:>");
                fromFCStringLines = extractStringLines(fromFCDescription, I_24);
                toFCStringLines = extractStringLines(toFCDescription, I_24);
                totalLines = 0;
                if(fromFCStringLines.length > toFCStringLines.length)
                {
                    totalLines = fromFCStringLines.length;
                }
                else
                {
                    totalLines = toFCStringLines.length;
                }
                nLines = 0;
                firstLine = false;
                do
                {
                    if(false == firstLine)
                    {
                        //outSB.append(" ");
                        outSB.append(setString(fromFCStringLines[nLines], I_24));
                        outSB.append(" ");
                        outSB.append(setString(toFCStringLines[nLines], I_24));
                        outSB.append("    ");
                        outSB.append(parseString(key, 10, "<:>"));
                        outSB.append("        $XXXX");
                        outSB.append(NEWLINE);
                        firstLine = true;
                        nLines++;
                    }
                    else
                    {
                        if(nLines < fromFCStringLines.length)
                        {
                            outSB.append(setString(fromFCStringLines[nLines], I_24));
                        }
                        else
                        {
                            outSB.append(setString(" ", I_24));
                        }
                        outSB.append(" ");
                        if(nLines < toFCStringLines.length)
                        {
                            outSB.append(setString(toFCStringLines[nLines], I_24));
                        }
                        else
                        {
                            outSB.append(setString(" ", I_24));
                        }
                        outSB.append(NEWLINE);
                        nLines++;
                    }
                }while(nLines < totalLines);
                prevGeo = currentGeo;
            }//end of while(tmItr.hasNext())
            if(!currentGeo.equals("WW"))
            {
//            if(fmtType == XMLFORMAT)
//            {
//               outSB.append("</pre>\n");
//            }
                bldEndGeoTags(currentGeo, fmtType, outSB);
//            if(fmtType == XMLFORMAT)
//            {
//               outSB.append("<pre>");
//            }
            }
            if(fmtType == GMLFORMAT)
            {
                outSB.append(":exmp." + NEWLINE + NEWLINE);
            }
//         else
//         {
//            outSB.append("</pre><p> </p>");
//         }
        }//end of else if(FORMAT2 == format)
    }

    /********************************************************************************
    *
    *
    * @param fmtType boolean
    * @param outSB StringBuffer
    */
    private void retrieveSalesManual(boolean fmtType, StringBuffer outSB)
    {
        outSB.append(".* <pre>" + NEWLINE);
        outSB.append(".* " + myDate() + NEWLINE);
        outSB.append(".* " + inventoryGroup + NEWLINE);
        //if(FORMAT1 == format)
        //{
        //   outSB.append(":h2.Sales Manual\n");
        //   outSB.append(".sk 2\n");
        //}

        if(salesManual_TM.size() > 0)
        {
            //Group entries based on MACHTYPEATR(MODEL)
            TreeMap [] arrayTM;
            int index;
            Iterator tsItr;
            TreeSet ts = new TreeSet();
            Set tmSet = salesManual_TM.keySet();
            Iterator tmItr = tmSet.iterator();
            while(tmItr.hasNext())
            {
                String key = (String) tmItr.next();
                ts.add(parseString(key, 1, "<:>"));
            }

            arrayTM = new TreeMap[ts.size()];
            for(int i = 0; i < ts.size(); i++)
            {
                arrayTM[i] = new TreeMap();
            }

            index = 0;
            tsItr = ts.iterator();
            while(tsItr.hasNext())
            {
                String mt = (String) tsItr.next();

                tmItr = tmSet.iterator();
                while(tmItr.hasNext())
                {
                    String key = (String) tmItr.next();

                    if(mt.equals(parseString(key, 1, "<:>")))
                    {
                        arrayTM[index].put(key, salesManual_TM.get(key));
                    }
                }//end of while(tmItr.hasNext())

                retrieveSalesManual(arrayTM[index], mt, fmtType, outSB);
                index++;
            }//end of while(tsItr.hasNext())
        }//end of salesManual_TM.size() > 0
        outSB.append(".* </pre>" + NEWLINE);
    }

    /********************************************************************************
    *
    *
    * @param tm TreeMap
    * @param mt String
    * @param fmtType boolean
    * @param outSB StringBuffer
    */
    private void retrieveSalesManual(TreeMap tm, String mt, boolean fmtType, StringBuffer outSB)
    {
        outSB.append(NEWLINE + ":xmp." + NEWLINE);
        outSB.append(".kp off" + NEWLINE);
        outSB.append("*********************************************************************" + NEWLINE);
        outSB.append("NOTE TO THE EDITOR" + NEWLINE);
        outSB.append("THE FEATURE SECTION OF THE SALES MANUAL IS AUTOMATICALLY GENERATED" + NEWLINE);
        outSB.append("FROM THE PLAN OF RECORD. PLEASE DO THE FOLLOWING:" + NEWLINE);
        outSB.append("1) ADD THE FEATURE IN NUMERIC ORDER UNDER THE HEADING GIVEN. IF THE" + NEWLINE);
        outSB.append("WORD \"NONE\" APPEARS DO NOT ADD THE WORD \"NONE.\"" + NEWLINE);
        outSB.append("2) IF DESCRIPTIVE INFORMATION IS PROVIDED SUCH AS:" + NEWLINE);
        outSB.append("\"The following is a list of all feature codes.................\"" + NEWLINE);
        outSB.append("REPLACE THE EXISTING DESCRIPTIVE INFORMATION." + NEWLINE);
        outSB.append("IF SUCH DESCRIPTIVE INFORMATION IS NOT PROVIDED DO NOT ADD THESE" + NEWLINE);
        outSB.append("WORDS." + NEWLINE);
        outSB.append("*********************************************************************" + NEWLINE);
        outSB.append(":exmp." + NEWLINE + NEWLINE);
        retrieveSalesManualSpecifyFeatures(mt, fmtType, outSB);
        retrieveSalesManualSpecialFeatures(mt, fmtType, outSB);
        retrieveSalesManualFeatureDescriptions(tm, mt, fmtType, outSB);
    }

    /********************************************************************************
    *
    *
    * @param mt String
    * @param fmtType boolean
    * @param outSB StringBuffer
    */
    private void retrieveSalesManualSpecifyFeatures(String mt, boolean fmtType, StringBuffer outSB)
    {
        if(fmtType == GMLFORMAT)
        {
            outSB.append(":h2.Specify Features for Machine Type " + mt + NEWLINE);
            outSB.append(":ul c." + NEWLINE);
        }
        else
        {
            outSB.append("<h2>Specify Features for Machine Type " + mt + "</h2>" + NEWLINE);
            outSB.append(":ul c." + NEWLINE);
        }

        retrieveSalesManualFeatures(mt, salesManualSpecifyFeatures_TM, fmtType, outSB);
        outSB.append(":eul." + NEWLINE);
        outSB.append(".sk 1" + NEWLINE + NEWLINE);
    }

    /********************************************************************************
    *
    *
    * @param mt String
    * @param fmtType boolean
    * @param outSB StringBuffer
    */
    private void retrieveSalesManualSpecialFeatures(String mt, boolean fmtType, StringBuffer outSB)
    {
        if(fmtType == GMLFORMAT)
        {
            outSB.append(":h2.Special Features for Machine Type " + mt + NEWLINE);
            outSB.append(":h3.Special Features - Initial Orders" + NEWLINE);
            outSB.append(":ul c." + NEWLINE);
        }
        else
        {
            outSB.append("<h2>Special Features for Machine Type " + mt + "</h2>" + NEWLINE);
            outSB.append("<h3>Special Features - Initial Orders</h3>" + NEWLINE);
            outSB.append(":ul c." + NEWLINE);
        }

        retrieveSalesManualFeatures(mt, salesManualSpecialFeaturesInitialOrder_TM, fmtType, outSB);
        outSB.append(":eul." + NEWLINE);
        outSB.append(".sk 1" + NEWLINE + NEWLINE);

        if(fmtType == GMLFORMAT)
        {
            outSB.append(":h3.Special Features - Plant and/or Field Installable" + NEWLINE);
            outSB.append(":ul c." + NEWLINE);
        }
        else
        {
            outSB.append("<h3>Special Features - Plant and/or Field Installable</h3>" + NEWLINE);
            outSB.append(":ul c." + NEWLINE);
        }

        retrieveSalesManualFeatures(mt, salesManualSpecialFeaturesOther_TM, fmtType, outSB);
        outSB.append(":eul." + NEWLINE);
        outSB.append(".************************************************************" + NEWLINE + NEWLINE);
    }

    /********************************************************************************
    *
    *
    * @param mt String
    * @param tm TreeMap
    * @param fmtType boolean
    * @param outSB StringBuffer
    */
    private void retrieveSalesManualFeatures(String mt, TreeMap tm, boolean fmtType, StringBuffer outSB)
    {
        if(tm.size() > 0)
        {
            //Get all entries that match machine type
            TreeMap aTM = new TreeMap();
            Set tmSet = tm.keySet();
            Iterator tmItr = tmSet.iterator();
            while(tmItr.hasNext())
            {
                String key = (String) tmItr.next();
                String geo = (String) tm.get(key);

                if(mt.equals(parseString(key, 1, "<:>")))
                {
                    aTM.put(key, geo);
                }
            }//end of while(tmItr.hasNext())

            if(aTM.size() > 0)
            {
                //Group entries based on HWFCCAT
                TreeMap [] arrayTM;
                int index;
                Iterator tsItr;
                TreeSet ts = new TreeSet();
                tmSet = aTM.keySet();
                tmItr = tmSet.iterator();
                while(tmItr.hasNext())
                {
                    String key = (String) tmItr.next();
                    ts.add(parseString(key, 2, "<:>"));
                }//end of while(tmItr.hasNext())

                arrayTM = new TreeMap[ts.size()];
                for(int i = 0; i < ts.size(); i++)
                {
                    arrayTM[i] = new TreeMap();
                }

                index = 0;
                tsItr = ts.iterator();
                while(tsItr.hasNext())
                {
                    String hwfccat = (String) tsItr.next();

                    tmItr = tmSet.iterator();
                    while(tmItr.hasNext())
                    {
                        String key = (String) tmItr.next();

                        if(hwfccat.equals(parseString(key, 2, "<:>")))
                        {
                            arrayTM[index].put(key, aTM.get(key));
                        }
                    }

                    retrieveSalesManualFeatures(arrayTM[index], hwfccat, fmtType, outSB);
                    index++;
                }//end while(tsItr.hasNext())
            }//end of if(aTM.size() > 0)
            else
            {
                outSB.append(":li.NONE" + NEWLINE);
                //outSB.append(":ul c.\n");
                //outSB.append(":eul.\n");
            }
        }//end of if(tm.size() > 0)
        else
        {
            outSB.append(":li.NONE" + NEWLINE);
            //outSB.append(":ul c.\n");
            //outSB.append(":eul.\n");
        }
    }

    /********************************************************************************
    *
    *
    * @param tm TreeMap
    * @param hwfccat String
    * @param fmtType boolean
    * @param outSB StringBuffer
    */
    private void retrieveSalesManualFeatures(TreeMap tm, String hwfccat, boolean fmtType, StringBuffer outSB)
    {
        if(tm.size() > 0)
        {
            Set tmSet;
            Iterator tmItr;
            String prevGeo = "";
            String currentGeo = "";

            outSB.append(":li." + hwfccat + NEWLINE);
            outSB.append(":ul c." + NEWLINE);

            tmSet = tm.keySet();
            tmItr = tmSet.iterator();
            while(tmItr.hasNext())
            {
                String liStr;
                String [] stringLines;
                String key = (String) tmItr.next();
                currentGeo = (String) tm.get(key);
                setGeoTags(prevGeo, currentGeo, fmtType, outSB);
                if(parseString(key, 5, "<:>").length() > 0)
                {
                    if(fmtType == XMLFORMAT)
                    {
//                  outSB.append("</pre>\n");
                        String [] arrayStr1 = getStringTokens(parseString(key, 5, "<:>"), NEWLINE);
                        for(int i = 0; i < arrayStr1.length; i++)
                        {
                            outSB.append(arrayStr1[i] + NEWLINE);
                        }
//                  outSB.append("<pre>\n");
                    }
                    if(fmtType == GMLFORMAT)
                    {
                        String [] arrayStr1 = getStringTokens(parseString(key, 5, "<:>"), NEWLINE);
                        for(int i = 0; i < arrayStr1.length; i++)
                        {
                            if(arrayStr1[i].length() > I_68)
                            {
                                String [] arrayStr2 = extractStringLines(arrayStr1[i], I_68);
                                for(int j = 0; j < arrayStr2.length; j++)
                                {
                                    outSB.append(":hp2." + arrayStr2[j] + ":ehp2." + NEWLINE);
                                }
                            }
                            else
                            {
                                outSB.append(":hp2." + arrayStr1[i] + ":ehp2." + NEWLINE);
                            }
                        }
                    }
                }
                //outSB.append(":li.(#" + parseString(key, 3) + ") - " + parseString(key, 4) + "\n");
                liStr = ":li.(#" + parseString(key, 3, "<:>") + ") - " + parseString(key, 4, "<:>");
                stringLines = extractStringLines(liStr, I_79);
                for(int i = 0; i < stringLines.length; i++)
                {
                    outSB.append(stringLines[i]);
                    outSB.append(NEWLINE);
                }
                prevGeo = currentGeo;
            }//end of while(tmItr.hasNext())
            if(!currentGeo.equals("WW"))
            {
//            if(fmtType == XMLFORMAT)
//            {
//               outSB.append("</pre>\n");
//            }
                bldEndGeoTags(currentGeo, fmtType, outSB);
//            if(fmtType == XMLFORMAT)
//            {
//               outSB.append("<pre>");
//            }
            }
            outSB.append(":eul." + NEWLINE);
        }//end of if(tm.size() > 0)
        else
        {
            outSB.append(":li.NONE" + NEWLINE);
         //outSB.append(":ul c.\n");
         //outSB.append(":eul.\n");
        }
    }

    /********************************************************************************
    *
    *
    * @param tm TreeMap
    * @param mt String
    * @param fmtType boolean
    * @param outSB StringBuffer
    */
    private void retrieveSalesManualFeatureDescriptions(TreeMap tm, String mt, boolean fmtType, StringBuffer outSB)
    {
        if(fmtType == GMLFORMAT)
        {
            outSB.append(":h2.Feature Descriptions" + NEWLINE);
            outSB.append(":p.The following is a list of all feature codes in numeric order for" + NEWLINE);
            if(brand.equals(PSERIES))
            {
                outSB.append("the IBM RS/6000 or pSeries " + mt + " machine type." + NEWLINE);
            }
            else if(brand.equals(ISERIES) || brand.equals(TS) || brand.equals(XSERIES) || brand.equals(ZSERIES))
            {
                outSB.append(mt + " machine type." + NEWLINE);
            }
            outSB.append(NEWLINE);
            outSB.append(":p.Attributes, as defined in the following feature descriptions," + NEWLINE);
            outSB.append("state the interaction of requirements among features." + NEWLINE);
            outSB.append(NEWLINE);
            outSB.append(":p.Minimums and maximums are the absolute limits for a single" + NEWLINE);
            outSB.append("feature without regard to interaction with other features.  The" + NEWLINE);
            outSB.append("maximum valid quantity for MES orders may be different than for" + NEWLINE);
            outSB.append("initial orders.  The maximums listed below refer to the largest" + NEWLINE);
            outSB.append("quantity of these two possibilities." + NEWLINE);
            outSB.append(NEWLINE);
            outSB.append(":p.The order type defines if a feature is orderable only on" + NEWLINE);
            outSB.append("initial orders, only on MES orders, on both initial and MES" + NEWLINE);
            outSB.append("orders, or if a feature is supported on a model due to a model" + NEWLINE);
            outSB.append("conversion.  Supported features cannot be ordered on the" + NEWLINE);
            outSB.append("converted model, only left on or removed from the converted" + NEWLINE);
            outSB.append("model." + NEWLINE + NEWLINE);
        }
        else
        {
            outSB.append("<h2>Sales Manual</h2>" + NEWLINE);
            outSB.append("<h2>Feature Descriptions</h2>" + NEWLINE);
            outSB.append("The following is a list of all feature codes in numeric order for" + NEWLINE);
            if(brand.equals(PSERIES))
            {
                outSB.append("the IBM RS/6000 or pSeries " + mt + "machine type." + NEWLINE);
            }
            else if(brand.equals(ISERIES) || brand.equals(TS) || brand.equals(XSERIES) || brand.equals(ZSERIES))
            {
                outSB.append(mt + " machine type." + NEWLINE);
            }
            outSB.append(NEWLINE);
            outSB.append("Attributes, as defined in the following feature descriptions," + NEWLINE);
            outSB.append("state the interaction of requirements among features." + NEWLINE);
            outSB.append(NEWLINE);
            outSB.append("Minimums and maximums are the absolute limits for a single" + NEWLINE);
            outSB.append("feature without regard to interaction with other features.  The" + NEWLINE);
            outSB.append("maximum valid quantity for MES orders may be different than for" + NEWLINE);
            outSB.append("initial orders.  The maximums listed below refer to the largest" + NEWLINE);
            outSB.append("quantity of these two possibilities." + NEWLINE);
            outSB.append(NEWLINE);
            outSB.append("The order type defines if a feature is orderable only on" + NEWLINE);
            outSB.append("initial orders, only on MES orders, on both initial and MES" + NEWLINE);
            outSB.append("orders, or if a feature is supported on a model due to a model" + NEWLINE);
            outSB.append("conversion.  Supported features cannot be ordered on the" + NEWLINE);
            outSB.append("converted model, only left on or removed from the converted" + NEWLINE);
            outSB.append("model." + NEWLINE + NEWLINE);
        }
        if(tm.size() > 0)
        {
            //Group entries based on feature
            TreeMap [] arrayTM;
            int index;
            Iterator tsItr;
            TreeSet ts = new TreeSet();
            Set tmSet = tm.keySet();
            Iterator tmItr = tmSet.iterator();
            while(tmItr.hasNext())
            {
                String key = (String) tmItr.next();
                ts.add(parseString(key, 3, "<:>"));
            }//end while(tmItr.hasNext())

            arrayTM = new TreeMap[ts.size()];
            for(int i = 0; i < ts.size(); i++)
            {
                arrayTM[i] = new TreeMap();
            }

            index = 0;
            tsItr = ts.iterator();
            while(tsItr.hasNext())
            {
                String feature = (String) tsItr.next();

                tmItr = tmSet.iterator();
                while(tmItr.hasNext())
                {
                    String key = (String) tmItr.next();

                    if(feature.equals(parseString(key, 3, "<:>")))
                    {
                        arrayTM[index].put(key, tm.get(key));
                    }
                }//end of while(tmItr.hasNext())

                retrieveSalesManualFeatureDescriptions(arrayTM[index], outSB);
                index++;
            }//end of while(tsItr.hasNext())
        }//end of if(tm.size() > 0)
    }

    /********************************************************************************
    *
    *
    * @param tm TreeMap
    * @param outSB StringBuffer
    */
    private void retrieveSalesManualFeatureDescriptions(TreeMap tm, StringBuffer outSB)
    {
        boolean printHeader = true;
        String geo = "";

        if(tm.size() > 0)
        {
            Set tmSet = tm.keySet();
            Iterator tmItr = tmSet.iterator();
            while(tmItr.hasNext())
            {
                String osLevelReq;
                String [] osLevelReqArrayStr;
                String editorNote;
                String returnPartsMES;
                String key = (String) tmItr.next();
                geo = (String) tm.get(key);

                if(printHeader)
                {
                    String h5Str;
                    String [] stringLines;
                    String description;
                    String attProvided;
                    String attRequired;
                    if(!geo.equals("WW"))
                    {
                        outSB.append(":p.:hp2.");
                        outSB.append(geo);
                        outSB.append("--->:ehp2." + NEWLINE);
                        outSB.append(".br" + NEWLINE);
                    }

                    if(parseString(key, I_14, "<:>").length() > 0)
                    {
                        String [] arrayStr1 = getStringTokens(parseString(key, I_14, "<:>"), NEWLINE);
                        for(int i = 0; i < arrayStr1.length; i++)
                        {
                            if(arrayStr1[i].length() > I_68)
                            {
                                String [] arrayStr2 = extractStringLines(arrayStr1[i], I_68);
                                for(int j = 0; j < arrayStr2.length; j++)
                                {
                                    outSB.append(":hp2." + arrayStr2[j] + ":ehp2." + NEWLINE);
                                }
                            }
                            else
                            {
                                outSB.append(":hp2." + arrayStr1[i] + ":ehp2." + NEWLINE);
                            }
                        }
                    }

                    //outSB.append(":h5.(#");
                    //outSB.append(parseString(key, 3) + ") - ");
                    //String [] stringLines = extractStringLines(parseString(key, 4), 54);
                    h5Str = ":h5.(#" + parseString(key, 3, "<:>") + ") - " + parseString(key, 4, "<:>");
                    stringLines = extractStringLines(h5Str, I_79);
                    for(int i = 0; i < stringLines.length; i++)
                    {
                        outSB.append(stringLines[i]);
                        outSB.append(NEWLINE);
                    }
                    //outSB.append(".*\n");
                    //outSB.append(":p.");
                    //stringLines = extractStringLines(parseString(key, 5), 63);
                    //for(int i = 0; i < stringLines.length; i++)
                    //{
                    //   outSB.append(stringLines[i]);
                    //   outSB.append("\n");
                    //}
                    //outSB.append(".sk 1\n");
                    //outSB.append(":ul c.\n");
                    //outSB.append(".kp on\n");
                    //outSB.append(":li.Attributes provided:\n");
                    //outSB.append(":li.Attributes required:\n");
                    //outSB.append(".kp off\n");
                    description = parseString(key, 5, "<:>");
                    if(description.equals(""))
                    {
                        //outSB.append(".*   DESCRIPTION FILE NOT FOUND" + NEWLINE);
                        //outSB.append(".*" + NEWLINE);
                        //outSB.append(".* BEGIN FEATURE TEMPLATE" + NEWLINE);
                        outSB.append(".sk1" + NEWLINE);
                        outSB.append(":ul c." + NEWLINE);
                        outSB.append(".kp on" + NEWLINE);
                        //if(FORMAT2 == format || FORMAT3 == format)
                        //{
                        //    outSB.append(":li.Attributes provided:" + NEWLINE);
                        //    outSB.append(":li.Attributes required:" + NEWLINE);
                        //}
                    }
                    else
                    {
                        boolean sk1 = false;
                        boolean ul = false;
                        boolean kp = false;
                        String [] arrayStr1 = getStringTokens(parseString(key, 5, "<:>"), NEWLINE);
                        for(int i = 0; i < arrayStr1.length; i++)
                        {
                            if(arrayStr1[i].length() > I_79)
                            {
                                //if(arrayStr1[i].substring(0, 2).equals(".*"))
                                //{
                                //    String [] arrayStr2 = extractStringLines(arrayStr1[i], I_75);
                                //    for(int j = 0; j < arrayStr2.length; j++)
                                //    {
                                //        if(0 == j)
                                //        {
                                //            outSB.append(arrayStr2[j] + NEWLINE);
                                //        }
                                //        else
                                //        {
                                //            outSB.append(".* " + arrayStr2[j] + NEWLINE);
                                //        }
                                //    }
                                //}
                                //else
                                if(!(arrayStr1[i].substring(0, 2).equals(".*")))
                                {
                                    String [] arrayStr2 = extractStringLines(arrayStr1[i], I_79);
                                    for(int j = 0; j < arrayStr2.length; j++)
                                    {
                                        outSB.append(arrayStr2[j] + NEWLINE);
                                    }
                                }
                            }//end of if(arrayStr1[i].length() > I_79)
                            else
                            {
                                if(!(arrayStr1[i].substring(0, 2).equals(".*")))
                                {
                                    outSB.append(arrayStr1[i] + NEWLINE);
                                }
                            }
                        }//end of for(int i = 0; i < arrayStr1.length; i++)

                        sk1 = textContainsGMLTag(parseString(key, 5, "<:>"), ".sk1");
                        ul = textContainsGMLTag(parseString(key, 5, "<:>"), ":ul");
                        kp = textContainsGMLTag(parseString(key, 5, "<:>"), ".kp");
                        if(!sk1 && !ul && !kp)
                        {
                            outSB.append(".sk1" + NEWLINE);
                            outSB.append(":ul c." + NEWLINE);
                            outSB.append(".kp on" + NEWLINE);
                        }
                    }//end of else

                    attProvided = parseString(key, I_15, "<:>");
                    attRequired = parseString(key, I_16, "<:>");

                    if(!attProvided.equals(""))
                    {
                        if(!textContainsGMLTag(attProvided, ":li"))
                        {
                            attProvided = ":li.Attributes provided: " + attProvided;
                        }

                        stringLines = extractStringLines(attProvided, I_79);
                        for(int i = 0; i < stringLines.length; i++)
                        {
                            outSB.append(stringLines[i]);
                            outSB.append(NEWLINE);
                        }
                    }//end of if(!attProvided.equals(""))

                    if(!attRequired.equals(""))
                    {
                        if(!textContainsGMLTag(attRequired, ":li"))
                        {
                            attRequired = ":li.Attributes required: " + attRequired;
                        }

                        stringLines = extractStringLines(attRequired, I_79);
                        for(int i = 0; i < stringLines.length; i++)
                        {
                            outSB.append(stringLines[i]);
                            outSB.append(NEWLINE);
                        }
                    }//end of if(!attRequired.equals(""))

                    outSB.append(".kp off" + NEWLINE);
                    outSB.append(".* MODEL SPECIFIC INFO SHOULD BE ADDED BELOW THIS LINE" + NEWLINE);

                    printHeader = false;
                }//end of if(printHeader)

                //outSB.append(":ul c.\n");
                outSB.append(".kp on" + NEWLINE);
                outSB.append(":li.For ");
                outSB.append(parseString(key, 1, "<:>") + "-");
                outSB.append(parseString(key, 2, "<:>") + ": ");
                outSB.append("(#");
                outSB.append(parseString(key, 3, "<:>") + ")" + NEWLINE);
                outSB.append(":ul c." + NEWLINE);
                if(FORMAT1 == format)
                {
                    String min = parseString(key, 6, "<:>");
                    String max = parseString(key, 7, "<:>");
                    String initOrder = parseString(key, 8, "<:>");

                    if(!min.equals(""))
                    {
                        outSB.append(":li.Minimum required: " + min + NEWLINE);
                    }

                    if(!max.equals(""))
                    {
                        outSB.append(":li.Maximum allowed: " + max + "   ");
                        if(!initOrder.equals(""))
                        {
                            outSB.append("(Initial order maximum: " + initOrder + " )" + NEWLINE);
                        }
                        else
                        {
                            outSB.append(NEWLINE);
                        }
                    }
                    else
                    {
                        if(!initOrder.equals(""))
                        {
                            outSB.append(":li.(Initial order maximum: " + initOrder + " )" + NEWLINE);
                        }
                    }
                }
                else if(FORMAT2 == format || FORMAT3 == format)
                {
                    outSB.append(":li.Minimum required: " + parseString(key, 6, "<:>") + NEWLINE);
                    outSB.append(":li.Maximum allowed: " + parseString(key, 7, "<:>") + "   ");
                    outSB.append("(Initial order maximum: " + parseString(key, 8, "<:>") + " )" + NEWLINE);
                }
                //outSB.append(":li.OS level required: " + parseString(key, 9) + "\n");
                osLevelReq = ":li.OS level required: " + parseString(key, 9, "<:>");
                osLevelReqArrayStr = getStringTokens(osLevelReq, NEWLINE);
                for(int i = 0; i < osLevelReqArrayStr.length; i++)
                {
                    if(osLevelReqArrayStr[i].length() > I_79)
                    {
                        String [] arrayStr = extractStringLines(osLevelReqArrayStr[i], I_79);
                        for(int j = 0; j < arrayStr.length; j++)
                        {
                            outSB.append(arrayStr[j] + NEWLINE);
                        }
                    }
                    else
                    {
                        outSB.append(osLevelReqArrayStr[i] + NEWLINE);
                    }
                }
                outSB.append(":li.Initial Order/MES/Both/Supported: " + parseString(key, 10, "<:>") + NEWLINE);
                outSB.append(":li.CSU: " + parseString(key, I_11, "<:>") + NEWLINE);
                returnPartsMES = parseString(key, I_12, "<:>");
                if(FORMAT1 == format)
                {
                    if(!returnPartsMES.equals(""))
                    {
                        outSB.append(":li.Return parts MES: " + parseString(key, I_12, "<:>") + NEWLINE);
                    }
                }
                else if(FORMAT2 == format || FORMAT3 == format)
                {
                    outSB.append(":li.Return parts MES: " + parseString(key, I_12, "<:>") + NEWLINE);
                }
                outSB.append(":eul." + NEWLINE);
                //outSB.append(".kp off\n");
                //outSB.append(":eul.\n");
                editorNote = parseString(key, I_13, "<:>");
                if(editorNote.length() > 0)
                {
                    String [] noteArrayStr;
                    editorNote = ":NOTE." + editorNote;
                    noteArrayStr = getStringTokens(editorNote, NEWLINE);
                    for(int i = 0; i < noteArrayStr.length; i++)
                    {
                        if(noteArrayStr[i].length() > I_79)
                        {
                            String [] arrayStr = extractStringLines(noteArrayStr[i], I_79);
                            for(int j = 0; j < arrayStr.length; j++)
                            {
                                outSB.append(arrayStr[j] + NEWLINE);
                            }
                        }
                        else
                        {
                            outSB.append(noteArrayStr[i] + NEWLINE);
                        }
                    }
                }
                outSB.append(".kp off" + NEWLINE);
                //outSB.append(":eul.\n"); //Don't print this
            }//end while(tmItr.hasNext())
            outSB.append(":eul." + NEWLINE);
            if(!geo.equals("WW"))
            {
                outSB.append(".br;:hp2.<---");
                outSB.append(geo);
                outSB.append(":ehp2." + NEWLINE);
            }//end of if(!geo.equals("WW"))
            outSB.append(".*" + NEWLINE);
            outSB.append(".******** END OF MODEL SPECIFIC INFO ************************" + NEWLINE);
        }//end of if(tm.size() > 0)
    }

    /********************************************************************************
    *
    *
    * @param fmtType boolean
    * @param outSB StringBuffer
    */
    private void retrieveSupportedDevices(boolean fmtType, StringBuffer outSB)
    {
        TreeSet ts;
        Set tmSet;
        Iterator tmItr;
        TreeMap [] arrayTM;
        int index;
        Iterator tsItr;

        outSB.append(".* <pre>" + NEWLINE);
        outSB.append(".* " + myDate() + NEWLINE);
        outSB.append(".* " + inventoryGroup + NEWLINE);
        //Group entries based on MACHTYPEATR(MODEL)
        ts = new TreeSet();
        tmSet = supportedDevices_TM.keySet();
        tmItr = tmSet.iterator();
        while(tmItr.hasNext())
        {
            String key = (String) tmItr.next();

            ts.add(parseString(key, 1, "<:>"));
        }

        arrayTM = new TreeMap[ts.size()];
        for(int i = 0; i < ts.size(); i++)
        {
            arrayTM[i] = new TreeMap();
        }

        index = 0;
        tsItr = ts.iterator();
        while(tsItr.hasNext())
        {
            String machType = (String) tsItr.next();

            tmItr = tmSet.iterator();
            while(tmItr.hasNext())
            {
                String key = (String) tmItr.next();

                if(machType.equals(parseString(key, 1, "<:>")))
                {
                    String aKey = parseString(key, 2, "<:>") + "<:>" + parseString(key, 3, "<:>") + "<:>" + parseString(key, 4, "<:>") + "<:>" + parseString(key, 5, "<:>") + "<:>" + parseString(key, 6, "<:>");
                    arrayTM[index].put(aKey, supportedDevices_TM.get(key));
                }
            }//end of while(tmItr.hasNext())
            index++;
        }//end of while(tsItr.hasNext())

        if(FORMAT1 == format)
        {
            outSB.append(":h2.External Machine Type (Support Devices)" + NEWLINE);
        }

        if(0 == arrayTM.length)
        {
            if(GMLFORMAT == fmtType)
            {
                outSB.append(":p.No answer data found for External Machine Type (Support Devices) section." + NEWLINE);
            }
            else
            {
                outSB.append("<p>No answer data found for External Machine Type (Support Devices) section.</p>" + NEWLINE);
            }
        }

        tsItr = ts.iterator();
        index = 0;
        while(tsItr.hasNext())
        {
            String machType = (String) tsItr.next();
            if(GMLFORMAT == fmtType)
            {
                outSB.append(NEWLINE + ":p.The following external machine types are supported on the" + NEWLINE);
                outSB.append("indicated models for MT " + machType + "." + NEWLINE);
                outSB.append(":p.This list is not all inclusive.  Many devices are supported through" + NEWLINE);
                outSB.append("standard ports.  Please refer to the sales manual" + NEWLINE);
                outSB.append("of the external machine type and the list of supported devices in the" + NEWLINE);
                if(brand.equals("pSeries"))
                {
                    outSB.append("appropriate section of the AIX sales manual" + NEWLINE);
                }
                else
                {
                    outSB.append("appropriate section of the sales manual" + NEWLINE);
                }
                outSB.append("for further attach support information." + NEWLINE + NEWLINE);
            }
            else
            {
                outSB.append("<p>The following external machine types are supported on the indicated models for MT " + machType + ".</p>" + NEWLINE);
                outSB.append("<p>This list is not all inclusive.  Many devices are supported through" + NEWLINE);
                outSB.append("standard ports.  Please refer to the sales manual" + NEWLINE);
                outSB.append("of the external machine type and the list of supported devices in the" + NEWLINE);
                if(brand.equals("pSeries"))
                {
                    outSB.append("appropriate section of the AIX sales manual" + NEWLINE);
                }
                else
                {
                    outSB.append("appropriate section of the sales manual" + NEWLINE);
                }
                outSB.append("for further attach support information.</p>" + NEWLINE);
            }
            retrieveSupportedDevices(outSB, fmtType, arrayTM[index]);
            index++;
        }//end of while(tsItr.hasNext())
        outSB.append(".* </pre>" + NEWLINE);
    }

    /********************************************************************************
    *
    *
    * @param outSB StringBuffer
    * @param fmtType boolean
    * @param tm TreeMap
    */
    private void retrieveSupportedDevices(StringBuffer outSB, boolean fmtType, TreeMap tm)
    {
        int [] cursorPos;
        Object [] arrayObj;
        TreeMap pos;
        int count;
        TreeSet ts = new TreeSet();
        Set tmSet = tm.keySet();
        Iterator tmItr = tmSet.iterator();
        while(tmItr.hasNext())
        {
            String aKey = (String) tmItr.next();
            aKey = parseString(aKey, 5, "<:>");
            ts.add(aKey);
        }

        //Column positions
        cursorPos = new int[10];
        cursorPos[0] = I_11;
        cursorPos[1] = I_13;
        cursorPos[2] = I_15;
        cursorPos[3] = I_17;
        cursorPos[4] = I_19;
        cursorPos[5] = I_21;
        cursorPos[6] = I_23;
        cursorPos[7] = I_25;
        cursorPos[8] = I_27;
        cursorPos[9] = I_29;

        arrayObj = ts.toArray();

        pos = new TreeMap();

        count = 0;

        //if(GMLFORMAT == fmtType)
        //   outSB.append(":xmp.\n");

        for(int i = 0; i < arrayObj.length; i++)
        {
            if(count < 10)
            {
                pos.put((String) arrayObj[i], new Integer(cursorPos[i % 10]));
                count++;
            }
            if((count == 10) || (i == arrayObj.length - 1))
            {
                count = 0;
                retrieveSupportedDevices(outSB, pos, tm, fmtType);
                pos.clear();
            }
        }//end of for(int i = 0; i < arrayObj.length; i++)

        //if(arrayObj.length > 0)
        //{
        //   if(GMLFORMAT == fmtType)
        //      outSB.append(":exmp.\n");
        //}
    }

    /********************************************************************************
    *
    *
    * @param outSB StringBuffer
    * @param pos TreeMap
    * @param tm TreeMap
    * @param fmtType boolean
    */
    private void retrieveSupportedDevices(StringBuffer outSB, TreeMap pos, TreeMap tm, boolean fmtType)
    {
        Set aSet;
        Object [ ] arrayObj;
        int descriptionLength;

        outSB.append(":xmp." + NEWLINE);
        outSB.append(".kp off" + NEWLINE);

//      if(XMLFORMAT == fmtType)
//         outSB.append("<pre>\n");

        headerSB.delete(0, headerSB.length());

        aSet = pos.keySet();
        arrayObj = aSet.toArray();
        descriptionLength = I_69 - (9 + (2 * pos.size() + 1));

        printChar(outSB, 9, " ", false);
        printChar(headerSB, 9, " ", false);
        for(int i = 0; i < pos.size(); i++)
        {
            String aStr = (String) arrayObj[i];
            outSB.append("|");
            headerSB.append("|");
            //String aStr = (String) arrayObj[i];
            outSB.append(aStr.charAt(0));
            headerSB.append(aStr.charAt(0));
        }//end of for(int i = 0; i < pos.size(); i++)
        outSB.append("|");
        headerSB.append("|");
        outSB.append("  X = SUPPORTED DEVICE" + NEWLINE);
        headerSB.append("  X = SUPPORTED DEVICE" + NEWLINE);

        printChar(outSB, 9, " ", false);
        printChar(headerSB, 9, " ", false);
        for(int i = 0; i < pos.size(); i++)
        {
            String aStr = (String) arrayObj[i];
            outSB.append("|");
            headerSB.append("|");
            //String aStr = (String) arrayObj[i];
            outSB.append(aStr.charAt(1));
            headerSB.append(aStr.charAt(1));
        }//end of for(int i = 0; i < pos.size(); i++)
        outSB.append("|" + NEWLINE);
        headerSB.append("|" + NEWLINE);

        printChar(outSB, 9, " ", false);
        printChar(headerSB, 9, " ", false);
        for(int i = 0; i < pos.size(); i++)
        {
            String aStr = (String) arrayObj[i];
            outSB.append("|");
            headerSB.append("|");
            //String aStr = (String) arrayObj[i];
            outSB.append(aStr.charAt(2));
            headerSB.append(aStr.charAt(2));
        }//end of for(int i = 0; i < pos.size(); i++)
        outSB.append("|" + NEWLINE);
        headerSB.append("|" + NEWLINE);

        printChar(outSB, 9, " ", false);
        printChar(headerSB, 9, " ", false);
        printChar(outSB, pos.size() * 2 + 1, "|", " ", false);
        printChar(headerSB, pos.size() * 2 + 1, "|", " ", false);
        outSB.append(NEWLINE);
        headerSB.append(NEWLINE);

        outSB.append("  MT-MOD ");
        headerSB.append("  MT-MOD ");
        printChar(outSB, pos.size() * 2 + 1, "|", " ", false);
        printChar(headerSB, pos.size() * 2 + 1, "|", " ", false);
        outSB.append("        DESCRIPTION" + NEWLINE);
        headerSB.append("        DESCRIPTION" + NEWLINE);

        printChar(outSB, 9, "-", false);
        printChar(headerSB, 9, "-", false);
        printChar(outSB, pos.size() * 2 + 1, "|", "-", false);
        printChar(headerSB, pos.size() * 2 + 1, "|", "-", false);
        printChar(outSB, descriptionLength, "-", false);
        printChar(headerSB, descriptionLength, "-", false);
        outSB.append(NEWLINE);
        headerSB.append(NEWLINE);

        retrieveSupportedDevices(pos, tm, descriptionLength, outSB, fmtType);

        outSB.append(":exmp." + NEWLINE);
    }

    /********************************************************************************
    *
    *
    * @param pos TreeMap
    * @param tm TreeMap
    * @param descriptionLength int
    * @param outSB StringBuffer
    * @param fmtType boolean
    */
    private void retrieveSupportedDevices(TreeMap pos, TreeMap tm, int descriptionLength, StringBuffer outSB, boolean fmtType)
    {
        int currentCursorPos = 0;
        Integer aInteger;
        int posModel = 0;
        boolean printNewRow = true;
        int nRow = 0;

        String prevGeo = "";
        String currentGeo = "";
        String prevFmGroup = "";
        String currentFmGroup = "";

        TreeSet ts = new TreeSet();

        Set aSet = tm.keySet();

        Iterator itr1 = aSet.iterator();

        while(itr1.hasNext())
        {
            String aStr = (String) itr1.next();
            String model = parseString(aStr, 5, "<:>");
            if(pos.containsKey(model))
            {
                String aKey = parseString(aStr, 1, "<:>") + "<:>" + parseString(aStr, 2, "<:>") + "<:>" + parseString(aStr, 3, "<:>") + "<:>" + parseString(aStr, 4, "<:>") + "<:>" + tm.get(aStr);
                ts.add(aKey);
            }
        }//end of while(itr.hasNext())

        itr1 = ts.iterator();

        while(itr1.hasNext())
        {
            Set tmSet;
            Iterator itr2;
            String [] strLines;
            String aStr = (String) itr1.next();

            currentCursorPos = 10;

            tmSet = tm.keySet();
            itr2 = tmSet.iterator();
            while(itr2.hasNext())
            {
                String aKey = (String) itr2.next();
                String aStrKey = parseString(aKey, 1, "<:>") + "<:>" + parseString(aKey, 2, "<:>") + "<:>" + parseString(aKey, 3, "<:>") + "<:>" + parseString(aKey, 4, "<:>") + "<:>" + tm.get(aKey);
                String modelAtr = parseString(aKey, 5, "<:>");
                if(aStr.equals(aStrKey) && pos.containsKey(modelAtr))
                {
                    currentGeo = parseString(aStrKey, 5, "<:>");
                    //setGeoTags(prevGeo, currentGeo, fmtType, outSB);
                    currentFmGroup = parseString(aStr, 1, "<:>");

                    if(printNewRow)
                    {
                        if(!currentFmGroup.equals(prevFmGroup))
                        {
                            if(ROW_LIMIT == nRow)
                            {
                                printEndGeoTags(prevGeo, fmtType, outSB);
                                outSB.append(":exmp." + NEWLINE + NEWLINE);
                                nRow = 0;
                                outSB.append(":xmp." + NEWLINE);
                                outSB.append(".kp off" + NEWLINE);
                                outSB.append(headerSB.toString());
                            }
                            else
                            {
                                printEndGeoTags(prevGeo, fmtType, outSB);
                            }
                            printChar(outSB, 9 ," ", false);
                            printChar(outSB, 2 * pos.size() + 1, "|", " ", false);
                            outSB.append(NEWLINE);

                            printChar(outSB, 9, "-", false);
                            printChar(outSB, pos.size() * 2 + 1, "|", "-", false);
                            printChar(outSB, 8, "-", false);

                            outSB.append(currentFmGroup);
                            printChar(outSB, descriptionLength - (8 + currentFmGroup.length()), "-", false);
                            outSB.append(NEWLINE);
                            printChar(outSB, 9, "-", " ", false);
                            printChar(outSB, pos.size() * 2 + 1, "|", "-", false);
                            printChar(outSB, descriptionLength, "-", " ", false);
                            outSB.append(NEWLINE);
                            printBeginGeoTags(currentGeo, fmtType, outSB);
                        }
                        else
                        {
                            if(ROW_LIMIT == nRow)
                            {
                                setGeoTags(prevGeo, currentGeo, fmtType, outSB, headerSB);
                                nRow = 0;
                            }
                            else
                            {
                                setGeoTags(prevGeo, currentGeo, fmtType, outSB);
                            }
                        }

                        outSB.append(parseString(aStr, 2, "<:>") + "-" + parseString(aStr, 3, "<:>") + " ");

                        printNewRow = false;
                        nRow++;
                    }//end of if(printNewRow)

                    prevGeo = currentGeo;
                    prevFmGroup = currentFmGroup;

                    aInteger = (Integer) pos.get(modelAtr);
                    posModel = aInteger.intValue();
                    printChar(outSB, posModel - currentCursorPos, "|", " ", false);
                    currentCursorPos = posModel;
                    outSB.append("X");
                    currentCursorPos = currentCursorPos + 1;
                }//end of if(aStr.equals(aStrKey) && pos.containsKey(modelAtr))
            }//end of while(itr2.hasNext())
            printChar(outSB, 9 + (2 * pos.size() + 2)  - currentCursorPos, "|", " ", false);

            strLines = extractStringLines(parseString(aStr, 4, "<:>"), descriptionLength - 1);
            for(int i = 0; i < strLines.length; i++)
            {
                if(0 == i)
                {
                    outSB.append(" " + strLines[i] + NEWLINE);
                }
                else
                {
                    printChar(outSB, 9, " ", false);
                    printChar(outSB, (2 * pos.size() + 1), "|", " ", false);
                    outSB.append(" " + strLines[i] + NEWLINE);
                }
            }

            printNewRow = true;
        }//end of while(itr1.hasNext())
        if(!currentGeo.equals("WW"))
        {
//         if(XMLFORMAT == fmtType)
//         {
//            outSB.append("</pre>\n");
//         }
            bldEndGeoTags(currentGeo, fmtType, outSB);
//         if(XMLFORMAT == fmtType)
//         {
//            outSB.append("<pre></pre>\n");
//         }
        }
        outSB.append(NEWLINE);
    }

    /********************************************************************************
    *
    *
    * @param fmtType boolean
    * @param outSB StringBuffer
    */
    private void retrieveFeatureMatrix(boolean fmtType, StringBuffer outSB)
    {
        TreeSet ts;
        Set tmSet;
        Iterator tmItr;
        TreeMap [] arrayTM;
        int index;
        Iterator tsItr;

        outSB.append(".* <pre>" + NEWLINE);
        outSB.append(".* " + myDate() + NEWLINE);
        outSB.append(".* " + inventoryGroup + NEWLINE);
        //Group entries based on MACHTYPEATR(MODEL)
        ts = new TreeSet();
        tmSet = featureMatrix_TM.keySet();
        tmItr = tmSet.iterator();
        while(tmItr.hasNext())
        {
            String key = (String) tmItr.next();

            ts.add(parseString(key, 1, "<:>"));
        }

        arrayTM = new TreeMap[ts.size()];
        for(int i = 0; i < ts.size(); i++)
        {
            arrayTM[i] = new TreeMap();
        }

        index = 0;
        tsItr = ts.iterator();
        while(tsItr.hasNext())
        {
            String machType = (String) tsItr.next();

            tmItr = tmSet.iterator();
            while(tmItr.hasNext())
            {
                String key = (String) tmItr.next();

                if(machType.equals(parseString(key, 1, "<:>")))
                {
                    String aKey = parseString(key, 2, "<:>") + "<:>" + parseString(key, 3, "<:>") + "<:>" + parseString(key, 4, "<:>") + "<:>" + parseString(key, 5, "<:>") + "<:>" + parseString(key, 6, "<:>");
                    arrayTM[index].put(aKey, featureMatrix_TM.get(key));
                }
            }//end of while(tmItr.hasNext())
            index++;
        }//end of while(tsItr.hasNext())

        if(FORMAT1 == format)
        {
            outSB.append(":h2.Feature Matrix" + NEWLINE);
        }

        if(0 == arrayTM.length)
        {
            if(GMLFORMAT == fmtType)
            {
                outSB.append(":p.No answer data found for Feature Matrix section." + NEWLINE);
            }
            else
            {
                outSB.append("<p>No answer data found for Feature Matrix section.</p>" + NEWLINE);
            }
        }

        tsItr = ts.iterator();
        index = 0;
        while(tsItr.hasNext())
        {
            String machType = (String) tsItr.next();
            if(GMLFORMAT == fmtType)
            {
                outSB.append(NEWLINE + ":xmp." + NEWLINE);
                outSB.append(".kp off" + NEWLINE);
                outSB.append("*********************************************************************" + NEWLINE);
                outSB.append("NOTE TO THE EDITOR" + NEWLINE);
                outSB.append("THE FEATURE AVAILABILITY MATRIX SECTION OF THE SALES MANUAL" + NEWLINE);
                outSB.append("IS AUTOMATICALLY GENERATED FROM THE PLAN OF RECORD." + NEWLINE);
                outSB.append("PLEASE DO THE FOLLOWING:" + NEWLINE);
                outSB.append("1) ADD THE FEATURE IN NUMERIC ORDER INTO THE FEATURE AVAILABILITY" + NEWLINE);
                outSB.append("MATRIX." + NEWLINE);
                outSB.append("2) IF DESCRIPTIVE INFORMATION IS PROVIDED SUCH AS:" + NEWLINE);
                outSB.append("\"The following feature availability matrix for................\"" + NEWLINE);
                outSB.append("REPLACE THE EXISTING DESCRIPTIVE INFORMATION." + NEWLINE);
                outSB.append("IF SUCH DESCRIPTIVE INFORMATION IS NOT PROVIDED DO NOT ADD THESE" + NEWLINE);
                outSB.append("WORDS." + NEWLINE);
                outSB.append("3) IF THERE IS NO FEATURE AVAILABILITY MATRIX DO NOT INCLUDE THIS" + NEWLINE);
                outSB.append("SECTION." + NEWLINE);
                outSB.append("*********************************************************************" + NEWLINE);
                outSB.append(":exmp." + NEWLINE);
                if(FORMAT2 == format)
                {
                    outSB.append(NEWLINE+ ":p.The following feature availability matrix for MT " + machType + NEWLINE);
                    outSB.append("uses the letter \"A\"" + NEWLINE);
                    outSB.append("to indicate features that are available and orderable on the specified" + NEWLINE);
                    outSB.append("models.  \"S\" indicates a feature that is supported on the new model" + NEWLINE);
                    outSB.append("during a model conversion; these features will" + NEWLINE);
                    outSB.append("work on the new model, but additional quantities of these" + NEWLINE);
                    outSB.append("features cannot be ordered on the new model; they can only be removed." + NEWLINE);
                    outSB.append("\"N\" indicates that the feature is not supported" + NEWLINE);
                    outSB.append("on the new model and must be removed during the model conversion." + NEWLINE);
                    outSB.append("As additional features are announced, supported, or withdrawn," + NEWLINE);
                    outSB.append("this list will be updated.  Please check with your Marketing" + NEWLINE);
                    outSB.append("Representative for additional information." + NEWLINE + NEWLINE);
                }
                else
                {
                    outSB.append(NEWLINE+ ":p.The following feature availability matrix for MT " + machType + NEWLINE + NEWLINE);
                }
            }
            else
            {
                if(FORMAT2 == format)
                {
                    outSB.append("<p>The following feature availability matrix for MT " + machType + NEWLINE);
                    outSB.append("uses the letter &quot;A&quot;" + NEWLINE);
                    outSB.append("to indicate features that are available and orderable on the specified" + NEWLINE);
                    outSB.append("models. &quot;S&quot; indicates a feature that is supported on the new model" + NEWLINE);
                    outSB.append("during a model conversion; these features will" + NEWLINE);
                    outSB.append("work on the new model, but additional quantities of these" + NEWLINE);
                    outSB.append("features cannot be ordered on the new model; they can only be removed." + NEWLINE);
                    outSB.append("&quot;N&quot; indicates that the feature is not supported" + NEWLINE);
                    outSB.append("on the new model and must be removed during the model conversion." + NEWLINE);
                    outSB.append("As additional features are announced, supported, or withdrawn," + NEWLINE);
                    outSB.append("this list will be updated.  Please check with your Marketing" + NEWLINE);
                    outSB.append("Representative for additional information.</p>" + NEWLINE);
                }
                else
                {
                    outSB.append("<p>The following feature availability matrix for MT " + machType + "</p>" + NEWLINE);
                }
            }
            retrieveFeatureMatrix(outSB, fmtType, arrayTM[index]);
            index++;
        }//end of while(tsItr.hasNext())
        outSB.append(".* </pre>" + NEWLINE);
    }

    /********************************************************************************
    *
    *
    * @param outSB StringBuffer
    * @param fmtType boolean
    * @param tm TreeMap
    */
    private void retrieveFeatureMatrix(StringBuffer outSB, boolean fmtType, TreeMap tm)
    {
        int [] cursorPos;
        Object [] arrayObj;
        TreeMap pos;
        int count;
        TreeSet ts = new TreeSet();
        Set tmSet = tm.keySet();
        Iterator tmItr = tmSet.iterator();
        while(tmItr.hasNext())
        {
            String aKey = (String) tmItr.next();
            aKey = parseString(aKey, 1, "<:>");
            ts.add(aKey);
        }

        //Column positions
        cursorPos = new int[10];
        cursorPos[0] = I_10;
        cursorPos[1] = I_12;
        cursorPos[2] = I_14;
        cursorPos[3] = I_16;
        cursorPos[4] = I_18;
        cursorPos[5] = I_20;
        cursorPos[6] = I_22;
        cursorPos[7] = I_24;
        cursorPos[8] = I_26;
        cursorPos[9] = I_28;

        arrayObj = ts.toArray();

        pos = new TreeMap();

        count = 0;

        //if(GMLFORMAT == fmtType)
        //   outSB.append(":xmp.\n");

        for(int i = 0; i < arrayObj.length; i++)
        {
            if(count < 10)
            {
                pos.put((String) arrayObj[i], new Integer(cursorPos[i % 10]));
                count++;
            }
            if((count == 10) || (i == arrayObj.length - 1))
            {
                count = 0;
                retrieveFeatureMatrix(outSB, pos, tm, fmtType);
                pos.clear();
            }
        }//end of for(int i = 0; i < arrayObj.length; i++)

        //if(arrayObj.length > 0)
        //{
        //   if(GMLFORMAT == fmtType)
        //      outSB.append(":exmp.\n");
        //}
    }

    /********************************************************************************
    *
    *
    * @param outSB StringBuffer
    * @param pos TreeMap
    * @param tm TreeMap
    * @param fmtType boolean
    */
    private void retrieveFeatureMatrix(StringBuffer outSB, TreeMap pos, TreeMap tm, boolean fmtType)
    {
        Set aSet = pos.keySet();
        Object [ ]  arrayObj = aSet.toArray();
        int descriptionLength = I_69 - (8 + (2 * pos.size() + 1));

        outSB.append(":xmp." + NEWLINE);
        outSB.append(".kp off" + NEWLINE);

//      if(XMLFORMAT == fmtType)
//         outSB.append("<pre>\n");

        headerSB.delete(0, headerSB.length());

        printChar(outSB, 8, " ", false);
        printChar(headerSB, 8, " ", false);
        for(int i = 0; i < pos.size(); i++)
        {
            String aStr = (String) arrayObj[i];
            outSB.append("|");
            headerSB.append("|");
            //String aStr = (String) arrayObj[i];
            outSB.append(aStr.charAt(0));
            headerSB.append(aStr.charAt(0));
        }//end of for(int i = 0; i < pos.size(); i++)
        outSB.append("|");
        headerSB.append("|");
        if(FORMAT2 == format)
        {
            outSB.append(" A = AVAILABLE  S = SUPPORTED" + NEWLINE);
            headerSB.append(" A = AVAILABLE  S = SUPPORTED" + NEWLINE);
        }
        else
        {
            outSB.append(" I = Initial" + NEWLINE);
            headerSB.append(" I = Initial" + NEWLINE);
        }

        printChar(outSB, 8, " ", false);
        printChar(headerSB, 8, " ", false);
        for(int i = 0; i < pos.size(); i++)
        {
            String aStr = (String) arrayObj[i];
            outSB.append("|");
            headerSB.append("|");
            //String aStr = (String) arrayObj[i];
            outSB.append(aStr.charAt(1));
            headerSB.append(aStr.charAt(1));
        }//end of for(int i = 0; i < pos.size(); i++)
        outSB.append("|");
        headerSB.append("|");
        if(FORMAT2 == format)
        {
            outSB.append(" N = NOT SUPPORTED, MUST BE REMOVED" + NEWLINE);
            headerSB.append(" N = NOT SUPPORTED, MUST BE REMOVED" + NEWLINE);
        }
        else
        {
            outSB.append(" M = MES" + NEWLINE);
            headerSB.append(" M = MES" + NEWLINE);
        }

        printChar(outSB, 8, " ", false);
        printChar(headerSB, 8, " ", false);
        for(int i = 0; i < pos.size(); i++)
        {
            String aStr = (String) arrayObj[i];
            outSB.append("|");
            headerSB.append("|");
            //String aStr = (String) arrayObj[i];
            outSB.append(aStr.charAt(2));
            headerSB.append(aStr.charAt(2));
        }//end of for(int i = 0; i < pos.size(); i++)
        outSB.append("|");
        headerSB.append("|");
        if(FORMAT2 == format)
        {
            outSB.append(NEWLINE);
            headerSB.append(NEWLINE);
        }
        else
        {
            outSB.append(" B = Both (Initial and MES)" + NEWLINE);
            headerSB.append(" B = Both (Initial and MES)" + NEWLINE);
        }

        printChar(outSB, 8, " ", false);
        printChar(headerSB, 8, " ", false);
        printChar(outSB, pos.size() * 2 + 1, "|", " ", false);
        printChar(headerSB, pos.size() * 2 + 1, "|", " ", false);
        if(FORMAT2 == format)
        {
            outSB.append(NEWLINE);
            headerSB.append(NEWLINE);
        }
        else
        {
            outSB.append(" S = SUPPORTED" + NEWLINE);
            headerSB.append(" S = SUPPORTED" + NEWLINE);
        }

        outSB.append("FEAT/PN ");
        headerSB.append("FEAT/PN ");
        printChar(outSB, pos.size() * 2 + 1, "|", " ", false);
        printChar(headerSB, pos.size() * 2 + 1, "|", " ", false);
        outSB.append("        DESCRIPTION" + NEWLINE);
        headerSB.append("        DESCRIPTION" + NEWLINE);

        printChar(outSB, 8, "-", false);
        printChar(headerSB, 8, "-", false);
        printChar(outSB, pos.size() * 2 + 1, "|", "-", false);
        printChar(headerSB, pos.size() * 2 + 1, "|", "-", false);
        printChar(outSB, descriptionLength, "-", false);
        printChar(headerSB, descriptionLength, "-", false);
        outSB.append(NEWLINE);
        headerSB.append(NEWLINE);

        retrieveFeatureMatrix(pos, tm, descriptionLength, outSB, fmtType);

        outSB.append(":exmp." + NEWLINE);
    }

    /********************************************************************************
    *
    *
    * @param pos TreeMap
    * @param tm TreeMap
    * @param descriptionLength int
    * @param outSB StringBuffer
    * @param fmtType boolean
    */
    private void retrieveFeatureMatrix(TreeMap pos, TreeMap tm, int descriptionLength, StringBuffer outSB, boolean fmtType)
    {
        int currentCursorPos = 0;
        Integer aInteger;
        int posModel = 0;
        boolean printHeader = true;
        int nRow = 0;

        String prevGeo = "";
        String currentGeo = "";

        TreeSet ts = new TreeSet();

        Set aSet = tm.keySet();

        Iterator itr1 = aSet.iterator();

        while(itr1.hasNext())
        {
            String aStr = (String) itr1.next();
            String model = parseString(aStr, 1, "<:>");
            if(pos.containsKey(model))
            {
                //String aKey = parseString(aStr, 2) + "<:>" + parseString(aStr, 3) + "<:>" + parseString(aStr, 4) + "<:>" + tm.get(aStr);
                String aKey = parseString(aStr, 2, "<:>") + "<:>" + parseString(aStr, 4, "<:>") + "<:>" + tm.get(aStr);
                ts.add(aKey);
            }
        }//end of while(itr.hasNext())

        itr1 = ts.iterator();

        while(itr1.hasNext())
        {
            String aStr = (String) itr1.next();

            String [] strLines;
            Set tmSet = tm.keySet();
            Iterator itr2 = tmSet.iterator();
            currentCursorPos = 9;
            while(itr2.hasNext())
            {
                String aKey = (String) itr2.next();
                //String aStrKey = parseString(aKey, 2) + "<:>" + parseString(aKey, 3) + "<:>" + parseString(aKey, 4) + "<:>" + tm.get(aKey);
                String aStrKey = parseString(aKey, 2, "<:>") + "<:>" + parseString(aKey, 4, "<:>") + "<:>" + tm.get(aKey);
                String modelAtr = parseString(aKey, 1, "<:>");
                String orderCode = parseString(aKey, 3, "<:>");
                String editorNote = parseString(aKey, 5, "<:>");
                if(aStr.equals(aStrKey) && pos.containsKey(modelAtr))
                {
                    currentGeo = parseString(aStrKey, 3, "<:>");

                    if(printHeader)
                    {
                        if(ROW_LIMIT == nRow)
                        {
                            setGeoTags(prevGeo, currentGeo, fmtType, outSB, headerSB);
                            nRow = 0;
                        }
                        else
                        {
                            setGeoTags(prevGeo, currentGeo, fmtType, outSB);
                        }

                        if(editorNote.length() > 0)
                        {
                            if(fmtType == XMLFORMAT)
                            {
//                        outSB.append("</pre>\n");
                                String [] arrayStr1 = getStringTokens(editorNote, NEWLINE);
                                for(int i = 0; i < arrayStr1.length; i++)
                                {
                                    outSB.append(arrayStr1[i] + NEWLINE);
                                }
//                        outSB.append("<pre>\n");
                            }
                            if(fmtType == GMLFORMAT)
                            {
                                String [] arrayStr1 = getStringTokens(editorNote, NEWLINE);
                                for(int i = 0; i < arrayStr1.length; i++)
                                {
                                    if(arrayStr1[i].length() > I_58)
                                    {
                                        String [] arrayStr2 = extractStringLines(arrayStr1[i], I_58);
                                        for(int j = 0; j < arrayStr2.length; j++)
                                        {
                                            outSB.append(":hp2." + arrayStr2[j] + ":ehp2." + NEWLINE);
                                        }
                                    }
                                    else
                                    {
                                        outSB.append(":hp2." + arrayStr1[i] + ":ehp2." + NEWLINE);
                                    }
                                }
                            }
                        }
                        outSB.append(parseString(aStr, 1, "<:>") + "    ");
                        printHeader = false;
                        nRow++;
                    }

                    prevGeo = currentGeo;

                    aInteger = (Integer) pos.get(modelAtr);
                    posModel = aInteger.intValue();
                    printChar(outSB, posModel - currentCursorPos, "|", " ", false);
                    currentCursorPos = posModel;
                    outSB.append(orderCode);
                    currentCursorPos = currentCursorPos + 1;
                }//end of if(aStr.equals(aStrKey) && pos.containsKey(modelAtr))
            }//end of while(itr2.hasNext())
            printChar(outSB, 8 + (2 * pos.size() + 2)  - currentCursorPos, "|", " ", false);

            strLines = extractStringLines(parseString(aStr, 2, "<:>"), descriptionLength - 1);
            for(int i = 0; i < strLines.length; i++)
            {
                if(0 == i)
                {
                    outSB.append(" " + strLines[i] + NEWLINE);
                }
                else
                {
                    printChar(outSB, 8, " ", false);
                    printChar(outSB, (2 * pos.size() + 1), "|", " ", false);
                    outSB.append(" " + strLines[i] + NEWLINE);
                }
            }

            printHeader = true;
        }//end of while(itr1.hasNext())
        if(!currentGeo.equals("WW"))
        {
//         if(XMLFORMAT == fmtType)
//         {
//            outSB.append("</pre>\n");
//         }
            bldEndGeoTags(currentGeo, fmtType, outSB);
//         if(XMLFORMAT == fmtType)
//         {
//            outSB.append("<pre></pre>\n");
//         }
        }
        outSB.append(NEWLINE);
    }

    /********************************************************************************
    *
    *
    * @param outSB StringBuffer
    */
    private void retrieveSEOTable(StringBuffer outSB)
    {
        TreeMap [] arrayTM;
        int index;
        Iterator tsItr;
        TreeSet ts;
        Set tmSet;
        Iterator tmItr;

        outSB.append(".* <pre>" + NEWLINE);
        outSB.append(".* " + myDate() + NEWLINE);
        outSB.append(".* " + inventoryGroup + NEWLINE);
        //Group entries based on geo
        ts = new TreeSet();
        tmSet = seoTable_TM.keySet();
        tmItr = tmSet.iterator();
        while(tmItr.hasNext())
        {
            String key = (String) tmItr.next();
            ts.add(parseString(key, 1, "<:>"));
        }

        arrayTM = new TreeMap[ts.size()];
        for(int i = 0; i < ts.size(); i++)
        {
            arrayTM[i] = new TreeMap();
        }

        index = 0;
        tsItr = ts.iterator();
        while(tsItr.hasNext())
        {
            String geo = (String) tsItr.next();

            tmItr = tmSet.iterator();
            while(tmItr.hasNext())
            {
                String key = (String) tmItr.next();
                String geoInfo = parseString(key, 1, "<:>");

                if(geo.equals(geoInfo))
                {
                    arrayTM[index].put(key, seoTable_TM.get(key));
                }
            }//end of while(tmItr.hasNext())
            index++;
        }

        for(int i = 0; i < ts.size(); i++)
        {
            retrieveSEOTable(outSB, arrayTM[i]);
        }

        outSB.append(".* </pre>" + NEWLINE);

        for(int i = 0; i < ts.size(); i++)
        {
            arrayTM[i].clear();
            arrayTM[i] = null;
        }
        ts.clear();
        ts = null;
    }

    /********************************************************************************
    *
    *
    * @param outSB StringBuffer
    * @param tm TreeMap
    */
    private void retrieveSEOTable(StringBuffer outSB, TreeMap tm)
    {
        String geo;
        Set tmSet;
        Iterator tmItr;

        String firstKey = (String) tm.firstKey();
        geo = (String) tm.get(firstKey);

        outSB.append(":xmp." + NEWLINE + NEWLINE);
        if(!geo.equals("WW"))
        {
            outSB.append(geo + "--->" + NEWLINE + NEWLINE);
        }
        showSEOTableHeader(outSB);

        tmSet = tm.keySet();
        tmItr = tmSet.iterator();

        while(tmItr.hasNext())
        {
            String[] hddInfoAS = new String[0];
            String[] odInfoAS = new String [0];
            int nLine = 1;
            String odInfo;
            String l2Info;

            String key = (String) tmItr.next();
            String mtm = parseString(key, 2, "<:>");
            String procInfo = parseString(key, 3, "<:>") + " " + parseString(key, 4, "<:>");
            String ramInfo = parseString(key, 7, "<:>") + " " + parseString(key, 8, "<:>");
            String hddInfo = parseString(key, 9, "<:>");
            if(hddInfo.length() > I_12)
            {
                hddInfoAS = extractStringLines(hddInfo , I_12);
                nLine = hddInfoAS.length;
            }

            odInfo = parseString(key, 10, "<:>");
            if(odInfo.length() > I_21)
            {
                odInfoAS = extractStringLines(odInfo , I_21);
                if(odInfoAS.length > nLine)
                {
                    nLine = odInfoAS.length;
                }
            }
            l2Info = parseString(key, 5, "<:>") + " " + parseString(key, 6, "<:>");

            for(int i = 0; i < nLine; i++)
            {
                if(i == 0)
                {
                    outSB.append(" " + mtm + "  " + procInfo + "   " + ramInfo);
                    if(nLine == 1)
                    {
                        outSB.append(" " + hddInfo + " " + odInfo + " " + l2Info);
                        outSB.append(NEWLINE);
                    }
                    else
                    {
                        if(i < hddInfoAS.length)
                        {
                            outSB.append(" " + hddInfoAS[i]);
                            printChar(outSB, I_12 - hddInfoAS[i].length(), " ", false);
                        }
                        else
                        {
                            outSB.append(" " + hddInfo);
                        }

                        if(i < odInfoAS.length)
                        {
                            outSB.append(" " + odInfoAS[i]);
                            printChar(outSB, I_21 - odInfoAS[i].length(), " ", false);
                        }
                        else
                        {
                            outSB.append(" " + odInfo);
                        }
                        outSB.append(" " + l2Info + NEWLINE);
                    }//end of else
                }//end of if(i == 0)
                else
                {
                    printChar(outSB, I_27, " ", false);
                    if(i < hddInfoAS.length)
                    {
                        outSB.append(" " + hddInfoAS[i]);
                        printChar(outSB, I_12 - hddInfoAS[i].length(), " ", false);
                    }
                    else
                    {
                        outSB.append(" ");
                        printChar(outSB, I_12, " ", false);
                    }
                    if(i < odInfoAS.length)
                    {
                        outSB.append(" " + odInfoAS[i]);
                        printChar(outSB, I_21 - odInfoAS[i].length(), " ", false);
                    }
                    else
                    {
                        printChar(outSB, I_21, " ", false);
                    }
                    printChar(outSB, 6, " ", false);
                    outSB.append(NEWLINE);
                }//end of else
            }//end of for(int i = 0; i < nLine; i++)

        }//end of while(tmItr.hasNext())

        outSB.append(NEWLINE);
        if(!geo.equals("WW"))
        {
            outSB.append("<---" + geo + NEWLINE + NEWLINE);
        }
        outSB.append(":exmp." + NEWLINE + NEWLINE);
    }

    /********************************************************************************
    *
    *
    * @param outSB StringBuffer
    */
    private void showSEOTableHeader(StringBuffer outSB)
    {
        outSB.append("                            HDD          CD-                   L2" + NEWLINE);
        outSB.append("  Model    Processor Memory Interface    ROM HDD       Other   Cache" + NEWLINE);
        outSB.append("--------------------------------------------------------------------" + NEWLINE);
    }

    /********************************************************************************
    *
    *
    * @param outSB StringBuffer
    */
    private void retrieveSEODescription(StringBuffer outSB)
    {
        Set tmSet;
        Iterator tmItr;
        int size;
        int n;
        String mtm1 = "";
        String mtm2 = "";
        TreeSet [] arrayTS1 = null;
        TreeSet [] arrayTS2 = null;

        outSB.append(".* <pre>" + NEWLINE);
        outSB.append(".* " + myDate() + NEWLINE);
        outSB.append(".* " + inventoryGroup + NEWLINE);
        size = seoDescription_TM.size();
        n = 1;
        tmSet = seoDescription_TM.keySet();
        tmItr = tmSet.iterator();
        while(tmItr.hasNext())
        {
            if(n%2 != 0)
            {
                mtm1 = (String) tmItr.next();
                arrayTS1 = (TreeSet []) seoDescription_TM.get(mtm1);
                if(n == size)
                {
                    retrieveSEODescription(outSB, mtm1, arrayTS1, "", null);
                }

            }//end of if(n%2 == 0)
            else
            {
                mtm2 = (String) tmItr.next();
                arrayTS2 = (TreeSet []) seoDescription_TM.get(mtm2);
                retrieveSEODescription(outSB, mtm1, arrayTS1, mtm2, arrayTS2);
            }//end of else

            n++;
        }//end of while(tmItr.hasNext())
    }

    /********************************************************************************
    *
    *
    * @param outSB StringBuffer
    * @param mtm1 String
    * @param arrayTS1 TreeSet[]
    * @param mtm2 String
    * @param arrayTS2 TreeSet[]
    */
    private void retrieveSEODescription(StringBuffer outSB, String mtm1, TreeSet [] arrayTS1, String mtm2, TreeSet [] arrayTS2)
    {
        outSB.append(":xmp." + NEWLINE + NEWLINE);
        showColumnHeaderForSEODescription(outSB, mtm1, mtm2);

        if(arrayTS2 != null)
        {
            showProcForSEODescription1(outSB, arrayTS1[0], arrayTS2[0]);
            showInfoForSEODescription1(outSB, 1, " Number standard -", arrayTS1[1], arrayTS2[1]);
            showInfoForSEODescription1(outSB, 4, " Maximum -", arrayTS1[0], arrayTS2[0]);
            showInfoForSEODescription1(outSB, 2, "L2 cache (full speed)", arrayTS1[1], arrayTS2[1]);
            showInfoForSEODescription1(outSB, 0, "Memory (400 MHz SDRAM)", arrayTS1[2], arrayTS2[2]);
            showRowForSEODescription1(outSB, " RDIMMs -", "", "");
            showInfoForSEODescription1(outSB, 1, " DIMM sockets -", arrayTS1[3], arrayTS2[3]);
            showInfoForSEODescription1(outSB, 2, " Address capability", arrayTS1[3], arrayTS2[3]);
            showInfoForSEODescription1(outSB, 1, "Video -", arrayTS1[4], arrayTS2[4]);
            showInfoForSEODescription1(outSB, 2, " Memory -", arrayTS1[4], arrayTS2[4]);
            showInfoForSEODescription1(outSB, 1, "HDD Controller", arrayTS1[5], arrayTS2[5]);
            showRowForSEODescription1(outSB, " Channels -", "", "");
            showRowForSEODescription1(outSB, " Connector internal -", "", "");
            showRowForSEODescription1(outSB, " Connector external -", "", "");
            showInfoForSEODescription1(outSB, 2, "IDE Controller", arrayTS1[5], arrayTS2[5]);
            showRowForSEODescription1(outSB, " Channels -", "", "");
            showRowForSEODescription1(outSB, " Connector internal -", "", "");
            showRowForSEODescription1(outSB, " Connector external -", "", "");
            showInfoForSEODescription1(outSB, 0, "HDD -", arrayTS1[6], arrayTS2[6]);
            showInfoForSEODescription1(outSB, 1, "Total bays -", arrayTS1[7], arrayTS2[7]);
            showInfoForSEODescription2(outSB, 2, arrayTS1[7], arrayTS2[7], true);
            showInfoForSEODescription1(outSB, 3, "Bays available", arrayTS1[1], arrayTS2[1]);
            showInfoForSEODescription2(outSB, 0, arrayTS1[8], arrayTS2[8], true);
            showInfoForSEODescription1(outSB, 3, "Total slots -", arrayTS1[3], arrayTS2[3]);
            showInfoForSEODescription2(outSB, 4, arrayTS1[3], arrayTS2[3], true);
            showInfoForSEODescription1(outSB, 4, "Slots available -", arrayTS1[1], arrayTS2[1]);
            showInfoForSEODescription1(outSB, 0, "Management proc. -", arrayTS1[9], arrayTS2[9]);
            showInfoForSEODescription1(outSB, 0, "Ethernet controller -", arrayTS1[10], arrayTS2[10]);
            showInfoForSEODescription2(outSB, 0, arrayTS1[I_11], arrayTS2[I_11], false);
            showRowForSEODescription1(outSB, "Diskette drive -", "", "");
            showInfoForSEODescription1(outSB, 1, "Power supply -", arrayTS1[I_12], arrayTS2[I_12]);
            showRowForSEODescription1(outSB, " Number standard -", "", "");
            showInfoForSEODescription1(outSB, 2, " Power Supply Type -", arrayTS1[I_12], arrayTS2[I_12]);
            showRowForSEODescription1(outSB, " Auto restart -", "", "");
        }
        else
        {
            showProcForSEODescription1(outSB, arrayTS1[0], null);
            showInfoForSEODescription1(outSB, 1, " Number standard -", arrayTS1[1], null);
            showInfoForSEODescription1(outSB, 4, " Maximum -", arrayTS1[0], null);
            showInfoForSEODescription1(outSB, 2, "L2 cache (full speed)", arrayTS1[1], null);
            showInfoForSEODescription1(outSB, 0, "Memory (400 MHz SDRAM)", arrayTS1[2], null);
            showRowForSEODescription1(outSB, " RDIMMs -", "", "");
            showInfoForSEODescription1(outSB, 1, " DIMM sockets -", arrayTS1[3], null);
            showInfoForSEODescription1(outSB, 2, " Address capability", arrayTS1[3], null);
            showInfoForSEODescription1(outSB, 1, "Video -", arrayTS1[4], null);
            showInfoForSEODescription1(outSB, 2, " Memory -", arrayTS1[4], null);
            showInfoForSEODescription1(outSB, 1, "HDD Controller", arrayTS1[5], null);
            showRowForSEODescription1(outSB, " Channels -", "", "");
            showRowForSEODescription1(outSB, " Connector internal -", "", "");
            showRowForSEODescription1(outSB, " Connector external -", "", "");
            showInfoForSEODescription1(outSB, 2, "IDE Controller", arrayTS1[5], null);
            showRowForSEODescription1(outSB, " Channels -", "", "");
            showRowForSEODescription1(outSB, " Connector internal -", "", "");
            showRowForSEODescription1(outSB, " Connector external -", "", "");
            showInfoForSEODescription1(outSB, 0, "HDD -", arrayTS1[6], null);
            showInfoForSEODescription1(outSB, 1, "Total bays -", arrayTS1[7], null);
            showInfoForSEODescription2(outSB, 2, arrayTS1[7], null, true);
            showInfoForSEODescription1(outSB, 3, "Bays available", arrayTS1[1], null);
            showInfoForSEODescription2(outSB, 0, arrayTS1[8], null, true);
            showInfoForSEODescription1(outSB, 3, "Total slots -", arrayTS1[3], null);
            showInfoForSEODescription2(outSB, 4, arrayTS1[3], null, true);
            showInfoForSEODescription1(outSB, 4, "Slots available -", arrayTS1[1], null);
            showInfoForSEODescription1(outSB, 0, "Management proc. -", arrayTS1[9], null);
            showInfoForSEODescription1(outSB, 0, "Ethernet controller -", arrayTS1[10], null);
            showInfoForSEODescription2(outSB, 0, arrayTS1[I_11], null, false);
            showRowForSEODescription1(outSB, "Diskette drive -", "", "");
            showInfoForSEODescription1(outSB, 1, "Power supply -", arrayTS1[I_12], null);
            showRowForSEODescription1(outSB, " Number standard -", "", "");
            showInfoForSEODescription1(outSB, 2, " Power Supply Type -", arrayTS1[I_12], null);
            showRowForSEODescription1(outSB, " Auto restart -", "", "");
        }

        outSB.append(NEWLINE);
        outSB.append(":exmp." + NEWLINE + NEWLINE);
    }

    /********************************************************************************
    *
    *
    * @param outSB StringBuffer
    * @param str1 String
    * @param str2 String
    */
    private void showColumnHeaderForSEODescription(StringBuffer outSB, String str1, String str2)
    {
        if(str1.length() > 0)
        {
            outSB.append(setString(" ", I_23));
            outSB.append(str1);
        }
        if(str2.length() > 0)
        {
            outSB.append(setString(" ", I_10));
            outSB.append(str2);
        }
        outSB.append(NEWLINE);

        if(str1.length() > 0)
        {
            outSB.append(setString(" ", I_23));
            printChar(outSB, I_13, "-", false);
        }
        if(str2.length() > 0)
        {
            outSB.append(setString(" ", 5));
            printChar(outSB, I_13, "-", false);
        }
        outSB.append(NEWLINE);
    }

    /********************************************************************************
    *
    *
    * @param outSB StringBuffer
    * @param ts1 TreeSet
    * @param ts2 TreeSet
    */
    private void showProcForSEODescription1(StringBuffer outSB, TreeSet ts1, TreeSet ts2)
    {
        int n1 = 0;
        int n2 = 0;
        int max = 0;
        String str = "";
        Object [] arrayObj1 = null;
        Object [] arrayObj2 = null;

        String procSpeedMktgName1 = "";
        String procInternalSpeed1 = "";
        String procExternalSpeed1 = "";
        String procSpeedMktgName2 = "";
        String procInternalSpeed2 = "";
        String procExternalSpeed2 = "";

        if(ts1 != null)
        {
            n1 = ts1.size();
            arrayObj1 = ts1.toArray();
        }

        if(ts2 != null)
        {
            n2 = ts2.size();
            arrayObj2 = ts2.toArray();
        }

        if(n1 > n2)
        {
            max = n1;
        }
        else
        {
            max = n2;
        }

        for(int i = 0; i < max; i++)
        {
            if(i < n1)
            {
                str = (String) arrayObj1[i];
                procSpeedMktgName1 = parseString(str, 1, "<:>");
                procInternalSpeed1 = parseString(str, 2, "<:>");
                procExternalSpeed1 = parseString(str, 3, "<:>");
            }
            else
            {
                procSpeedMktgName1 = setString(" ", I_17);
                procInternalSpeed1 = setString(" ", I_17);
                procExternalSpeed1 = setString(" ", I_17);
            }

            if(i < n2)
            {
                str = (String) arrayObj2[i];;
                procSpeedMktgName2 = parseString(str, 1, "<:>");
                procInternalSpeed2 = parseString(str, 2, "<:>");
                procExternalSpeed2 = parseString(str, 3, "<:>");
            }
            else
            {
                procSpeedMktgName2 = setString(" ", I_17);
                procInternalSpeed2 = setString(" ", I_17);
                procExternalSpeed2 = setString(" ", I_17);
            }

            showRowForSEODescription1(outSB, "Processor -", procSpeedMktgName1, procSpeedMktgName2);
            showRowForSEODescription1(outSB, " Internal speed -", procInternalSpeed1, procInternalSpeed2);
            showRowForSEODescription1(outSB, " External speed -", procExternalSpeed1, procExternalSpeed2);
        }//end of for(int i = 0; i < max; i++)
    }

    /********************************************************************************
    *
    *
    * @param outSB StringBuffer
    * @param n int
    * @param rowHeader String
    * @param ts1 TreeSet
    * @param ts2 TreeSet
    */
    private void showInfoForSEODescription1(StringBuffer outSB, int n, String rowHeader, TreeSet ts1, TreeSet ts2)
    {
        int n1 = 0;
        int n2 = 0;
        int max = 0;
        String str = "";
        Object [] arrayObj1 = null;
        Object [] arrayObj2 = null;

        String str1 = "";
        String str2 = "";

        if(ts1 != null)
        {
            n1 = ts1.size();
            arrayObj1 = ts1.toArray();
        }

        if(ts2 != null)
        {
            n2 = ts2.size();
            arrayObj2 = ts2.toArray();
        }

        if(n1 > n2)
        {
            max = n1;
        }
        else
        {
            max = n2;
        }

        for(int i = 0; i < max; i++)
        {
            if(i < n1)
            {
                str = (String) arrayObj1[i];
                str1 = parseString(str, n, "<:>");
            }
            else
            {
                str1 = setString(" ", I_17);
            }

            if(i < n2)
            {
                str = (String) arrayObj2[i];;
                str2 = parseString(str, n, "<:>");
            }
            else
            {
                str2 = setString(" ", I_17);
            }

            showRowForSEODescription1(outSB, rowHeader, str1, str2);
        }//end of for(int i = 0; i < max; i++)
    }

    /********************************************************************************
    *
    *
    * @param outSB StringBuffer
    * @param n int
    * @param ts1 TreeSet
    * @param ts2 TreeSet
    * @param padding boolean
    */
    private void showInfoForSEODescription2(StringBuffer outSB, int n, TreeSet ts1, TreeSet ts2, boolean padding)
    {
        int n1 = 0;
        int n2 = 0;
        int max = 0;
        String str = "";
        Object [] arrayObj1 = null;
        Object [] arrayObj2 = null;

        String str1 = "";
        String str2 = "";

        if(ts1 != null)
        {
            n1 = ts1.size();
            arrayObj1 = ts1.toArray();
        }

        if(ts2 != null)
        {
            n2 = ts2.size();
            arrayObj2 = ts2.toArray();
        }

        if(n1 > n2)
        {
            max = n1;
        }
        else
        {
            max = n2;
        }

        for(int i = 0; i < max; i++)
        {
            if(i < n1)
            {
                str = (String) arrayObj1[i];
                str1 = parseString(str, n, "<:>");
            }
            else
            {
                str1 = "<:><::>";
            }

            if(i < n2)
            {
                str = (String) arrayObj2[i];;
                str2 = parseString(str, n, "<:>");
            }
            else
            {
                str2 = "<:><::>";
            }

            showRowForSEODescription2(outSB, str1, str2, n, padding);
        }//end of for(int i = 0; i < max; i++)
    }

    /********************************************************************************
    *
    *
    * @param outSB StringBuffer
    * @param rowHeader String
    * @param value1 String
    * @param value2 String
    */
    private void showRowForSEODescription1(StringBuffer outSB, String rowHeader, String value1, String value2)
    {
        String[] value1AS = new String[0];
        String[] value2AS = new String[0];
        int nLine = 1;

        if(value1.length() > I_17)
        {
            value1AS = extractStringLines(value1 , I_17);
            nLine = value1AS.length;
        }

        if(value2.length() > I_17)
        {
            value2AS = extractStringLines(value2 , I_17);
            if(value2AS.length > nLine)
            {
                nLine = value2AS.length;
            }
        }

        for(int i = 0; i < nLine; i++)
        {
            if(i == 0)
            {
                outSB.append(rowHeader);
                printChar(outSB, I_22 - rowHeader.length(), " ", false);
                if(nLine == 1)
                {
                    outSB.append(" " + value1 + " " + value2);
                    outSB.append(NEWLINE);
                }
                else
                {
                    if(i < value1AS.length)
                    {
                        outSB.append(" " + value1AS[i]);
                        printChar(outSB, I_17 - value1AS[i].length(), " ", false);
                    }
                    else
                    {
                        outSB.append(" " + value1);
                    }

                    if(i < value2AS.length)
                    {
                        outSB.append(" " + value2AS[i]);
                        printChar(outSB, I_17 - value2AS[i].length(), " ", false);
                    }
                    else
                    {
                        outSB.append(" " + value2);
                    }
                    outSB.append(NEWLINE);
                }//end of else
            }//end of if(i == 0)
            else
            {
                printChar(outSB, I_22, " ", false);
                if(i < value1AS.length)
                {
                    outSB.append(" " + value1AS[i]);
                    printChar(outSB, I_17 - value1AS[i].length(), " ", false);
                }
                else
                {
                    outSB.append(" ");
                    printChar(outSB, I_17, " ", false);
                }
                if(i < value2AS.length)
                {
                    outSB.append(" " + value2AS[i]);
                    printChar(outSB, I_17 - value2AS[i].length(), " ", false);
                }
                else
                {
                    printChar(outSB, I_17, " ", false);
                }
                outSB.append(NEWLINE);
            }//end of else
        }//end of for(int i = 0; i < nLine; i++)
    }

    private void showRowForSEODescription2(StringBuffer outSB, String str1, String str2, int m, boolean padding)
    {
        TreeSet ts;
        String value1 = "";
        String value2 = "";
        int n = 0;
        Iterator itr;
        Vector v1 = new Vector();
        Vector v2 = new Vector();

        if(str1.length() > 0)
        {
            value1 = parseString(str1, m, "<:>");
        }
        if(str2.length() > 0)
        {
            value2 = parseString(str2, m, "<:>");
        }

        ts = new TreeSet();
        n = countDelims(value1, "<::>") + 1;
        if(n > 1)
        {
            for(int i = 1; i <= n; i++)
            {
                if(i%2 != 0)
                {
                    if((!parseString(value1, i, "<::>").equals("")) && (!parseString(value1, i + 1, "<::>").equals("")))
                    {
                        ts.add(parseString(value1, i, "<::>"));
                    }
                }
            }
        }
        n = countDelims(value2, "<::>") + 1;
        if(n > 1)
        {
            for(int i = 1; i <= n; i++)
            {
                if(i%2 != 0)
                {
                    if((!parseString(value2, i, "<::>").equals("")) && (!parseString(value2, i + 1, "<::>").equals("")))
                    {
                        ts.add(parseString(value2, i, "<::>"));
                    }
                }
            }
        }

        itr = ts.iterator();
        while(itr.hasNext())
        {
            int maxLines = 0;
            String type = (String) itr.next();
            n = 0;

            maxLines = countTypes(value1, type, "<::>", v1);
            n = countTypes(value2, type, "<::>", v2);
            if(n > maxLines)
            {
                maxLines = n;
            }

            showRowForSEODescription2(outSB, type, maxLines, v1, v2, padding);

            v1.clear();
            v2.clear();
        }//end of while(itr.hasNext())
    }

    /********************************************************************************
    *
    *
    * @param str String
    * @param type String
    * @param delim String
    * @param v Vector
    * @returns int
    */
    private int countTypes(String str, String type, String delim, Vector v)
    {
        int n = 0;
        int tot = 0;

        n = countDelims(str, delim) + 1;

        if(n > 1)
        {
            String value = "";
            for(int i = 1; i <= n; i++)
            {
                if(i%2 != 0)
                {
                    value = parseString(str, i, "<::>");

                    if(value.equals(type))
                    {
                        if((!value.equals("")) && (!parseString(str, i + 1, "<::>").equals("")))
                        {
                            tot++;
                            v.add(parseString(str, i + 1, "<::>"));
                        }
                    }
                }
            }
        }//end of if(n > 1)

        return tot;
    }

    /********************************************************************************
    *
    *
    * @param outSB StringBuffer
    * @param type String
    * @param maxLines int
    * @param v1 Vector
    * @param v2 Vector
    * @param padding boolean
    */
    private void showRowForSEODescription2(StringBuffer outSB, String type, int maxLines, Vector v1, Vector v2, boolean padding)
    {
        String tot1 = "";
        String tot2 = "";

        for(int i = 0; i < maxLines; i++)
        {
            if(i  < v1.size())
            {
                tot1 = (String) v1.get(i);
            }
            else
            {
                tot1 = " ";
            }
            if(i  < v2.size())
            {
                tot2 = (String) v2.get(i);
            }
            else
            {
                tot2 = " ";
            }
            showRowForSEODescription2(outSB, type, tot1, tot2, padding);
        }//end of for(int i = 0; i < maxLines; i++)
    }

    /********************************************************************************
    *
    *
    * @param outSB StringBuffer
    * @param rowHeader String
    * @param v1 String
    * @param v2 String
    * @param padding boolean
    */
    private void showRowForSEODescription2(StringBuffer outSB, String rowHeader,  String v1, String v2, boolean padding)
    {
        String[] rowHeaderAS;
        String paddingStr = "";

        if(padding)
        {
            rowHeaderAS = extractStringLines(rowHeader, I_21);
            paddingStr = " ";
        }
        else
        {
            rowHeaderAS = extractStringLines(rowHeader, I_22);
        }
        v1 = setString(v1, I_17);
        v2 = setString(v2, I_17);
        for(int i = 0; i < rowHeaderAS.length; i++)
        {
            if(i == 0)
            {
                outSB.append(paddingStr + rowHeaderAS[i]);
                if(padding)
                {
                    printChar(outSB, I_21 - rowHeaderAS[i].length(), " ", false);
                }
                else
                {
                    printChar(outSB, I_22 - rowHeaderAS[i].length(), " ", false);
                }
                outSB.append(" " + v1);
                outSB.append(" " + v2 + NEWLINE);
            }
            else
            {
                outSB.append(paddingStr + rowHeaderAS[i] + NEWLINE);
            }
        }//end of for(int i = 0; i < rowHeader.length; i++)
    }

    /********************************************************************************
    *
    *
    * @param outSB StringBuffer
    */
    private void retrieveFeatureMatrixError(StringBuffer outSB)
    {
        String prevReportSection = "";
        String currentReportSection = "";
        String prevMTM = "";
        String currentMTM = "";
        String mt = "";
        String model = "";
        String entity = "";
        String attribute = "";
        Iterator tsItr;

        outSB.append(".* <pre>" + NEWLINE);
        outSB.append(".* " + myDate() + NEWLINE);
        outSB.append(".* " + inventoryGroup + NEWLINE);

        if((featureMatrixError.size() > 0) && (FORMAT1 == format))
        {
            outSB.append(":h2.Feature Matrix Error" + NEWLINE);
        }

        tsItr = featureMatrixError.iterator();
        while(tsItr.hasNext())
        {
            String key = (String) tsItr.next();
            currentReportSection = parseString(key, 2, "<:>");
            mt = parseString(key, 3, "<:>");
            model = parseString(key, 4, "<:>");
            currentMTM = mt + "-" + model;
            entity = parseString(key, 5, "<:>");
            attribute = parseString(key, 6, "<:>");

            if(!currentReportSection.equals(prevReportSection) || !currentMTM.equals(prevMTM))
            {
                outSB.append(NEWLINE + "Report Name: " + currentReportSection + NEWLINE);
                outSB.append("Machine Type:        " + mt + NEWLINE);
                outSB.append("Model:               " + model + NEWLINE);
                outSB.append("Missing Attributes:" + NEWLINE);
            }
            //outSB.append("Entity: " + entity + ", " + "Attribute: " + attribute + "\n");
            outSB.append("   Entity: " + entity + ", Attribute: " + attribute + NEWLINE);

            prevReportSection = currentReportSection;
            prevMTM = currentMTM;
        }//end of while(tsItr.hasNext())
        outSB.append(".* </pre>" + NEWLINE);
    }

    /********************************************************************************
    *
    *
    * @param ei EntityItem
    * @param key String
    */
    private void updateGeoHT(EntityItem ei, String key)
    {
        if(null == geoHT.get(key))
        {
            geoHT.put(key, new StringBuffer("NNNNN"));
        }
        if(checkRfaGeoUS(ei))
        {
            updateGeo(key, "US");
        }
        if(checkRfaGeoAP(ei))
        {
            updateGeo(key, "AP");
        }
        if(checkRfaGeoLA(ei))
        {
            updateGeo(key, "LA");
        }
        if(checkRfaGeoCAN(ei))
        {
            updateGeo(key, "CAN");
        }
        if(checkRfaGeoEMEA(ei))
        {
            updateGeo(key, "EMEA");
        }
    }

    /********************************************************************************
    *
    *
    * @param ei EntityItem
    * @returns boolean
    */
    private boolean checkRfaGeoUS(EntityItem ei)
    {
        String entityKey = ei.getKey();
        String value = (String) usGeoHT.get(entityKey);

        if(null == value)
        {
            if(gal.isRfaGeoUS(ei))
            {
                usGeoHT.put(entityKey, "Y");
                return true;
            }
            else
            {
                usGeoHT.put(entityKey, "F");
                return false;
            }
        }
        else if(value.equals("Y"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /********************************************************************************
    *
    *
    * @param ei EntityItem
    * @returns boolean
    */
    private boolean checkRfaGeoAP(EntityItem ei)
    {
        String entityKey = ei.getKey();
        String value = (String) apGeoHT.get(entityKey);

        if(null == value)
        {
            if(gal.isRfaGeoAP(ei))
            {
                apGeoHT.put(entityKey, "Y");
                return true;
            }
            else
            {
                apGeoHT.put(entityKey, "F");
                return false;
            }
        }
        else if(value.equals("Y"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /********************************************************************************
    *
    *
    * @param ei EntityItem
    * @returns boolean
    */
    private boolean checkRfaGeoLA(EntityItem ei)
    {
        String entityKey = ei.getKey();
        String value = (String) laGeoHT.get(entityKey);

        if(null == value)
        {
            if(gal.isRfaGeoLA(ei))
            {
                laGeoHT.put(entityKey, "Y");
                return true;
            }
            else
            {
                laGeoHT.put(entityKey, "F");
                return false;
            }
        }
        else if(value.equals("Y"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /********************************************************************************
    *
    *
    * @param ei EntityItem
    * @returns boolean
    */
    private boolean checkRfaGeoCAN(EntityItem ei)
    {
        String entityKey = ei.getKey();
        String value = (String) canGeoHT.get(entityKey);

        if(null == value)
        {
            if(gal.isRfaGeoCAN(ei))
            {
                canGeoHT.put(entityKey, "Y");
                return true;
            }
            else
            {
                canGeoHT.put(entityKey, "F");
                return false;
            }
        }
        else if(value.equals("Y"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /********************************************************************************
    *
    *
    * @param ei EntityItem
    * @returns boolean
    */
    private boolean checkRfaGeoEMEA(EntityItem ei)
    {
        String entityKey = ei.getKey();
        String value = (String) emeaGeoHT.get(entityKey);

        if(null == value)
        {
            if(gal.isRfaGeoEMEA(ei))
            {
                emeaGeoHT.put(entityKey, "Y");
                return true;
            }
            else
            {
                emeaGeoHT.put(entityKey, "F");
                return false;
            }
        }
        else if(value.equals("Y"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /********************************************************************************
    *
    *
    * @param key String
    * @param geo String
    */
    private void updateGeo(String key, String geo)
    {
        StringBuffer value = (StringBuffer) geoHT.get(key);

        if(geo.equalsIgnoreCase("US"))
        {
            value.setCharAt(0, 'Y');
        }
        if(geo.equalsIgnoreCase("AP"))
        {
            value.setCharAt(1, 'Y');
        }
        if(geo.equalsIgnoreCase("LA"))
        {
            value.setCharAt(2, 'Y');
        }
        if(geo.equalsIgnoreCase("CAN"))
        {
            value.setCharAt(3, 'Y');
        }
        if(geo.equalsIgnoreCase("EMEA"))
        {
            value.setCharAt(4, 'Y');
        }
    }

    /********************************************************************************
    *
    *
    * @param key String
    * @returns String
    */
    private String getGeo(String key)
    {
        String geo = "";

        boolean ap = false;
        boolean can = false;
        boolean emea = false;
        boolean la = false;
        boolean us = false;

        StringBuffer value = (StringBuffer) geoHT.get(key);
        log(value.toString());
        if(value.charAt(0) == 'Y')
        {
            us = true;
        }
        if(value.charAt(1) == 'Y')
        {
            ap = true;
        }
        if(value.charAt(2) == 'Y')
        {
            la = true;
        }
        if(value.charAt(3) == 'Y')
        {
            can = true;
        }
        if(value.charAt(4) == 'Y')
        {
            emea = true;
        }

        if(ap && can && emea && la && us)
        {
            return "WW";
        }

        if(us)
        {
            geo = "US, ";
        }
        if(ap)
        {
            geo = geo + "AP, ";
        }
        if(la)
        {
            geo = geo + "LA, ";
        }
        if(can)
        {
            geo = geo + "CAN, ";
        }
        if(emea)
        {
            geo = geo + "EMEA, ";
        }

        if(geo.length() > 0)
        {
            return geo.substring(0, geo.length() - 2);
        }
        else
        {
            return "No GEO Found";
        }
    }

    /********************************************************************************
    * Find entities in the specified vector that have attribute values that
    * match the passed in values.  All values must match.
    * Flags and text attributes are checked.  If it is a flag, flag codes are checked.
    * If multiple values are needed, separate them with a Delimiter ("|")
    *
    * @param ei EntityItem
    * @param attrValTbl  Hashtable with Attribute code as key and String to match as value
    * @returns boolean
    */
    private boolean isEntityWithMatchedAttr(EntityItem ei, Hashtable attrValTbl)
    {
        //This method is derived from PokUtils.getEntitiesWithMatchedAttr()
        boolean allMatch=true;
        for(Enumeration e = attrValTbl.keys(); e.hasMoreElements();)
        {
            String attrCode = (String)e.nextElement();
            allMatch=allMatch&&
                entityMatchesAttr(ei, attrCode, attrValTbl.get(attrCode).toString());
        }
        if(allMatch)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private void addToTreeMap(String key, String geo, TreeMap tm)
    {
//      log("In addToTreeMap(): key = " + key + ", geo = " + geo);

        tm.put(key, geo);
    }

    /********************************************************************************
    *
    *
    * @param r EntityItem
    * @param destType String
    * @returns EANEntity
    */
    private EANEntity getUpLinkEntityItem(EntityItem r, String destType)
    {
        EANEntity entity = null;

        for(int i = 0; i < r.getUpLinkCount(); i++)
        {
            EANEntity eai = r.getUpLink(i);
            if(eai.getEntityType().equals(destType))
            {
                entity = eai;
                break;
            }
        }

        return entity;
    }

    /********************************************************************************
    *
    *
    * @param r EntityItem
    * @param destType String
    * @returns EANEntity
    */
    private EANEntity getDownLinkEntityItem(EntityItem r, String destType)
    {
        EANEntity entity = null;

        for(int i = 0; i < r.getDownLinkCount(); i++)
        {
            EANEntity eai = r.getDownLink(i);
            if(eai.getEntityType().equals(destType))
            {
                entity = eai;
                break;
            }
        }

        return entity;
    }

    /********************************************************************************
    *
    *
    * @param str String
    * @param n int
    * @returns String
    */
    //private String parseString(String str, int n)
    //{
    //    int i = n - 1;
    //    String result = "";
    //    int index = I_N3;
    //    Vector pos = new Vector(1);

    //    if((0 == n) || (n < 0))
    //    {
    //        return str;
    //    }

    //    if(0 == i)
    //    {
    //        return str.substring(0, str.indexOf("<:>"));
    //    }

    //    while(true)
    //    {
    //        index = str.indexOf("<:>", index + 3);

    //        if(index < 0)
    //        {
    //            break;
    //        }

    //        pos.addElement(new Integer(index));
    //    }//end of while(true)

    //    if(i > pos.size())
    //    {
    //        return str;
    //    }

    //    if(i == pos.size())
    //    {
    //        result = str.substring(((Integer) pos.get(i - 1)).intValue() + 3);
    //    }
    //    else
    //    {
    //        result = str.substring(((Integer) pos.get(i - 1)).intValue() + 3, ((Integer) pos.get(i)).intValue());
    //    }

    //    return result;
    //}

    /********************************************************************************
    *
    *
    * @param str String
    * @param n int
    * @param delim String
    * @returns String
    */
    private String parseString(String str, int n, String delim)
    {
        if (str.indexOf(delim)==-1) {                   //Return when no delimiter found in string -Bala
          return str;
        }
        int i = n - 1;
        String result = "";
        int index = - delim.length();
        int l = delim.length();
        Vector pos = new Vector(1);
        if((0 == n) || (n < 0))
        {
            return str;
        }

        if(0 == i)
        {
            return str.substring(0, str.indexOf(delim));
        }

        while(true)
        {
            index = str.indexOf(delim, index + l);

            if(index < 0)
            {
                break;
            }

            pos.addElement(new Integer(index));
        }//end of while(true)

        if(i > pos.size())
        {
            return str;
        }

        if(i == pos.size())
        {
            result = str.substring(((Integer) pos.get(i - 1)).intValue() + l);
        }
        else
        {
            result = str.substring(((Integer) pos.get(i - 1)).intValue() + l, ((Integer) pos.get(i)).intValue());
        }

        return result;
    }

    /********************************************************************************
    *
    *
    * @param str String
    * @param delim String
    * @returns int
    */
    private int countDelims(String str, String delim)
    {
        int n = 0;
        int index = - delim.length();

        while(true)
        {
            index = str.indexOf(delim, index + 3);

            if(index < 0)
            {
                break;
            }

            n++;
        }//end of while(true)

        return n;
    }

    /********************************************************************************
    *
    *
    * @param strTextToBeWrapped String
    * @param intMaxLineLength int
    * @returns String[]
    */
    private String[] extractStringLines(String strTextToBeWrapped, int intMaxLineLength)
    {
        int intLengthOfInput;
        int intCurrentPosition = 0;
        int intCurrentLineStart = 0;
        int intPositionOfLastSpace = 0;
        //int intMaxLine = I_20;
        int intMaxLine = strTextToBeWrapped.length();
        int n = 0;
        String[] strWrappedText;
        int num = 0;
        String[] returnedStrWrappedText;
        int j = 0;

        strTextToBeWrapped = strTextToBeWrapped.trim();
        if(0 == strTextToBeWrapped.length())
        {
            String [] strArray = new String[1];
            strArray[0] = " ";
            return strArray;
        }

        //if(strTextToBeWrapped.length() > 128)
        //{
        //   strTextToBeWrapped = strTextToBeWrapped.substring(0, 128);
        //}

        strWrappedText = new String[intMaxLine];
        for(int i = 0; i < intMaxLine; i++)
        {
            strWrappedText[i] = new String();
        }

        intLengthOfInput = strTextToBeWrapped.length();

        while(intCurrentPosition < intLengthOfInput)
        {
            if(strTextToBeWrapped.charAt(intCurrentPosition) == ' ')
            {
                intPositionOfLastSpace = intCurrentPosition;
            }

            if(intCurrentPosition == intCurrentLineStart + intMaxLineLength)
            {
                if(0 == intPositionOfLastSpace)
                {
                    strWrappedText[n] = strTextToBeWrapped.substring(intCurrentLineStart, intCurrentPosition).trim();
                    //intCurrentLineStart = intCurrentPosition + 1;
                    intCurrentLineStart = intCurrentPosition;
                    n++;
                }
                else
                {
                    if(intCurrentLineStart == (intPositionOfLastSpace + 1))
                    {
                        strWrappedText[n] = strTextToBeWrapped.substring(intCurrentLineStart, intCurrentPosition).trim();
                        intCurrentLineStart = intCurrentPosition;
                        n++;
                    }
                    else
                    {
                        strWrappedText[n] = strTextToBeWrapped.substring(intCurrentLineStart, intPositionOfLastSpace + 1).trim();
                        intCurrentLineStart = intPositionOfLastSpace + 1;
                        n++;
                    }
                }
            }
            intCurrentPosition = intCurrentPosition + 1;
        }//end of while(intCurrentPostion < intLengthOfInput)

        strWrappedText[n] = strTextToBeWrapped.substring(intCurrentLineStart).trim();

        num = 0;

        for(int i = 0; i < intMaxLine; i++)
        {
            if(strWrappedText[i].length() > 0)
            {
                num++;
            }
        }

        returnedStrWrappedText = new String[num];
        for(int i = 0; i < num; i++)
        {
            returnedStrWrappedText[i] = new String();
        }

        j = 0;
        for(int i = 0; i < intMaxLine; i++)
        {
            if(strWrappedText[i].length() > 0)
            {
                returnedStrWrappedText[j++] = strWrappedText[i];
            }
        }

        return returnedStrWrappedText;
    }

    /********************************************************************************
    *
    *
    * @param aStr String
    * @param delim String
    * @returns String[]
    */
    private String[] getStringTokens(String aStr, String delim)
    {
        StringTokenizer stn1 = new StringTokenizer(aStr, delim);
        StringTokenizer stn2 = new StringTokenizer(aStr, delim);
        int n = 0;
        String [] arrayStr;

        while(stn1.hasMoreTokens())
        {
            n++;
            stn1.nextToken();
        }

        arrayStr = new String[n];

        n = 0;
        while(stn2.hasMoreTokens())
        {
            arrayStr[n] = stn2.nextToken();
            n++;
        }

        return arrayStr;
    }

    /********************************************************************************
    *
    *
    * @param aSB StringBuffer
    * @param n int
    * @param s String
    * @param vertical boolean
    */
    private void printChar(StringBuffer aSB, int n, String s, boolean vertical)
    {
        if(!vertical)
        {
            for(int i = 0; i < n; i++)
            {
                aSB.append(s);
            }
        }
        else
        {
            aSB.append("|");
            for(int i = 0; i < n - 2; i++)
            {
                aSB.append(s);
            }
            aSB.append("|");
        }
    }

    /********************************************************************************
    *
    *
    * @param aSB StringBuffer
    * @param n int
    * @param s1 String
    * @param s2 String
    * @param vertical boolean
    */
    private void printChar(StringBuffer aSB, int n, String s1, String s2, boolean vertical)
    {
        boolean printS1 = true;

        if(!vertical)
        {
            for(int i = 0; i < n; i++)
            {
                if(printS1)
                {
                    aSB.append(s1);
                    printS1 = false;
                }
                else
                {
                    aSB.append(s2);
                    printS1 = true;
                }
            }//end of for(int i = 0; i < n; i++)
        }//end of if(!vertical)
        else
        {
            aSB.append("|");
            for(int i = 0; i < n - 2; i++)
            {
                if(printS1)
                {
                    aSB.append(s1);
                    printS1 = false;
                }
                else
                {
                    aSB.append(s2);
                    printS1 = true;
                }
            }//end of for(int i = 0; i < n - 2; i++)
            aSB.append("|");
        }//end of else
    }

    /**
    * The method builds a string of current date/time.
    *
    * @return String
    */
    public String myDate()
    {
        Date d = new Date();
        String myDate = new String();

        StringBuffer today   = new StringBuffer();

        SimpleDateFormat sdf  = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        myDate = sdf.format(d);

        today.append("Created on ");
        today.append(myDate);

        if(today.length() > I_22)
        {
            today.insert(I_22, "at ");
        }

        return today.toString();
    }

    /**
    *  Return a string with the specified size - truncate or padd with blanks.
    *
    *
    * @param inString String
    * @param setSize int
    * @return String
    */
    private String setString(String inString, int setSize)
    {
        int len = inString.length();

        if(setSize == 0)
        {
            return new String();
        }

        if(len > setSize)
        {
            return (inString.substring(0, setSize));
        }
        else
        {
            StringBuffer sb   = new StringBuffer();
            sb.append(inString);
            for(int i = len; i < setSize; i++)
            {
                sb.append(" ");
            }
            return sb.toString();
        }
    }

    /**
    *  Return a string with the specified size - truncate or pre padd with blanks.
    *
    *
    * @param inString String
    * @param setSize int
    * @return String
    */
    private String setString2(String inString, int setSize)
    {
        int len = inString.length();

        if(setSize == 0)
        {
            return new String();
        }

        if(len > setSize)
        {
            return (inString.substring(0, setSize));
        }
        else
        {
            StringBuffer sb   = new StringBuffer();
            for(int i = len; i < setSize; i++)
            {
                sb.append(" ");
            }
            sb.append(inString);
            return sb.toString();
        }
    }

    private void setGeoTags(String prevGeo, String currentGeo, boolean fmtType, StringBuffer sb)
    {
        if(prevGeo.equals(""))
        {
            if(!currentGeo.equals("WW"))
            {
//            if(fmtType == XMLFORMAT)
//            {
//               sb.append("</pre>\n");
//            }
                bldBgnGeoTags(currentGeo, fmtType, sb);
//            if(fmtType == XMLFORMAT)
//            {
//               sb.append("<pre>\n");
//            }
            }
        }
        else
        {
            if(!prevGeo.equals(currentGeo))
            {
//            if(fmtType == XMLFORMAT)
//            {
//               sb.append("</pre>\n");
//            }
                if(!prevGeo.equals("WW"))
                {
                    bldEndGeoTags(prevGeo, fmtType, sb);
                }
                if(!currentGeo.equals("WW"))
                {
                    bldBgnGeoTags(currentGeo, fmtType, sb);
                }
//            if(fmtType == XMLFORMAT)
//            {
//               sb.append("<pre>\n");
//            }
            }
        }
    }

    private void setGeoTags(String prevGeo, String currentGeo, boolean fmtType, StringBuffer sb, StringBuffer hsb)
    {
        if(prevGeo.equals(""))
        {
            if(!currentGeo.equals("WW"))
            {
//            if(fmtType == XMLFORMAT)
//            {
//               sb.append("</pre>\n");
//            }
                sb.append(":exmp." + NEWLINE + NEWLINE);
                sb.append(":xmp." + NEWLINE);
                sb.append(".kp off" + NEWLINE);
                sb.append(hsb.toString());
                bldBgnGeoTags(currentGeo, fmtType, sb);
//            if(fmtType == XMLFORMAT)
//            {
//               sb.append("<pre>\n");
//            }
            }
            else
            {
                sb.append(":exmp." + NEWLINE + NEWLINE);
                sb.append(":xmp." + NEWLINE);
                sb.append(".kp off" + NEWLINE);
                sb.append(hsb.toString());
            }
        }
        else
        {
            if(!prevGeo.equals(currentGeo))
            {
//            if(fmtType == XMLFORMAT)
//            {
//               sb.append("</pre>\n");
//            }
                if(!prevGeo.equals("WW"))
                {
                    bldEndGeoTags(prevGeo, fmtType, sb);
                    sb.append(":exmp." + NEWLINE + NEWLINE);
                    sb.append(":xmp." + NEWLINE);
                    sb.append(".kp off" + NEWLINE);
                    sb.append(hsb.toString());
                }
                else
                {
                    sb.append(":exmp." + NEWLINE + NEWLINE);
                    sb.append(":xmp." + NEWLINE);
                    sb.append(".kp off" + NEWLINE);
                    sb.append(hsb.toString());
                }
                if(!currentGeo.equals("WW"))
                {
                    bldBgnGeoTags(currentGeo, fmtType, sb);
                }
//            if(fmtType == XMLFORMAT)
//            {
//               sb.append("<pre>\n");
//            }
            }
            else
            {
                sb.append(":exmp." + NEWLINE + NEWLINE);
                sb.append(":xmp." + NEWLINE);
                sb.append(".kp off" + NEWLINE);
                sb.append(hsb.toString());
            }
        }
    }

    private void setGeoTags2(String prevGeo, String currentGeo, boolean fmtType, StringBuffer sb)
    {
        if(prevGeo.equals(""))
        {
            if(!currentGeo.equals("WW"))
            {
//            if(fmtType == XMLFORMAT)
//            {
//               sb.append("</pre>\n");
//            }
                //sb.append(":xmp." + NEWLINE); According to OIM3.0b specs: remove :xmp. and :exmp. pair
            //sb.append(".kp off\n");
                bldBgnGeoTags(currentGeo, fmtType, sb);
//            if(fmtType == XMLFORMAT)
//            {
//               sb.append("<pre>\n");
//            }
            }
            //else According to OIM3.0b specs: remove :xmp. and :exmp. pair
            //{
                //sb.append(":xmp." + NEWLINE);
            //sb.append(".kp off\n");
            //}
        }
        else
        {
            if(!prevGeo.equals(currentGeo))
            {
//            if(fmtType == XMLFORMAT)
//            {
//               sb.append("</pre>\n");
//            }
                if(!prevGeo.equals("WW"))
                {
                    bldEndGeoTags(prevGeo, fmtType, sb);
                    //sb.append(":exmp." + NEWLINE); According to OIM3.0b specs: remove :xmp. and :exmp. pair
                }
                //else According to OIM3.0b specs: remove :xmp. and :exmp. pair
                //{
                //    sb.append(":exmp." + NEWLINE);
                //}
                if(!currentGeo.equals("WW"))
                {
                    //sb.append(":xmp." + NEWLINE); According to OIM3.0b specs: remove :xmp. and :exmp. pair
                    //sb.append(".kp off\n");
                    bldBgnGeoTags(currentGeo, fmtType, sb);
                }
                //else According to OIM3.0b specs: remove :xmp. and :exmp. pair
                //{
                //    sb.append(":xmp." + NEWLINE);
                    //sb.append(".kp off\n");
                //}
//            if(fmtType == XMLFORMAT)
//            {
//               sb.append("<pre>\n");
//            }
            }//end of if(!prevGeo.equals(currentGeo))
            //else According to OIM3.0b specs: remove :xmp. and :exmp. pair
            //{
            //    sb.append(":exmp." + NEWLINE);
            //    sb.append(":xmp." + NEWLINE);
                //sb.append(".kp off\n");
            //}
        }
    }

    private void setGeoTags3(String prevGeo, String currentGeo, String prevMT, String currentMT,
        String prevFeature, String currentFeature, boolean fmtType, StringBuffer sb)
    {
        if(prevGeo.equals(""))
        {
            if(!currentGeo.equals("WW"))
            {
//            if(fmtType == XMLFORMAT)
//            {
//               sb.append("</pre>\n");
//            }
                //sb.append(":xmp." + NEWLINE); According to OIM3.0b specs: remove :xmp. and :exmp. pair appeared around each feature
            //sb.append(".kp off\n");
                bldBgnGeoTags(currentGeo, fmtType, sb);
//            if(fmtType == XMLFORMAT)
//            {
//               sb.append("<pre>\n");
//            }
            }
            //else According to OIM3.0b specs: remove :xmp. and :exmp. pair appeared around each feature
            //{
                //sb.append(":xmp." + NEWLINE);
            //sb.append(".kp off\n");
            //}
        }
        else
        {
            if(!prevGeo.equals(currentGeo))
            {
//            if(fmtType == XMLFORMAT)
//            {
//               sb.append("</pre>\n");
//            }
                if(!prevGeo.equals("WW"))
                {
                    bldEndGeoTags(prevGeo, fmtType, sb);
                    //sb.append(":exmp." + NEWLINE); According to OIM3.0b specs: remove :xmp. and :exmp. pair appeared around each feature
                }
                //else According to OIM3.0b specs: remove :xmp. and :exmp. pair appeared around each feature
                //{
                //    sb.append(":exmp." + NEWLINE);
                //}
                if(!currentGeo.equals("WW"))
                {
                    //sb.append(":xmp." + NEWLINE); According to OIM3.0b specs: remove :xmp. and :exmp. pair appeared around each feature
                    //sb.append(".kp off\n");
                    bldBgnGeoTags(currentGeo, fmtType, sb);
                }
                //else According to OIM3.0b specs: remove :xmp. and :exmp. pair appeared around each feature
                //{
                    //sb.append(":xmp." + NEWLINE);
                    //sb.append(".kp off\n");
                //}
//            if(fmtType == XMLFORMAT)
//            {
//               sb.append("<pre>\n");
//            }
            }//end of if(!prevGeo.equals(currentGeo))
            else
            {
                if(!currentMT.equals(prevMT) || !currentFeature.equals(prevFeature))
                {
                    //sb.append(":exmp." + NEWLINE); According to OIM3.0b specs: remove :xmp. and :exmp. pair appeared around each feature
                    //sb.append(":xmp." + NEWLINE);
                    //sb.append(".kp off\n");
                }
            }
        }
    }

    private void setGeoTagsFeatConv(String prevGeo, String currentGeo, boolean fmtType, StringBuffer sb)
    {
        if(prevGeo.equals(""))
        {
            if(!currentGeo.equals("WW"))
            {
//            if(fmtType == XMLFORMAT)
//            {
//               sb.append("</pre>\n");
//            }
                sb.append(".sk 1" + NEWLINE);
                sb.append(":xmp." + NEWLINE);
                //sb.append(".kp off\n");
                bldBgnGeoTags(currentGeo, fmtType, sb);
//            if(fmtType == XMLFORMAT)
//            {
//               sb.append("<pre>\n");
//            }
            }
            else
            {
                sb.append(".sk 1" + NEWLINE);
                sb.append(":xmp." + NEWLINE);
                //sb.append(".kp off\n");
            }
        }
        else
        {
            if(!prevGeo.equals(currentGeo))
            {
//            if(fmtType == XMLFORMAT)
//            {
//               sb.append("</pre>\n");
//            }
                if(!prevGeo.equals("WW"))
                {
                    bldEndGeoTags(prevGeo, fmtType, sb);
                    sb.append(":exmp." + NEWLINE);
                }
                else
                {
                    sb.append(":exmp." + NEWLINE);
                }
                if(!currentGeo.equals("WW"))
                {
                    sb.append(".sk 1" + NEWLINE);
                    sb.append(":xmp." + NEWLINE);
                    //sb.append(".kp off\n");
                    bldBgnGeoTags(currentGeo, fmtType, sb);
                }
                else
                {
                    sb.append(".sk 1" + NEWLINE);
                    sb.append(":xmp." + NEWLINE);
                    //sb.append(".kp off\n");
                }
//            if(fmtType == XMLFORMAT)
//            {
//               sb.append("<pre>\n");
//            }
            }//end of if(!prevGeo.equals(currentGeo))
            else
            {
                sb.append(":exmp." + NEWLINE);
                sb.append(".sk 1" + NEWLINE);
                sb.append(":xmp." + NEWLINE);
                //sb.append(".kp off\n");
            }
        }
    }

    private void bldBgnGeoTags(String geo, boolean fmtType, StringBuffer sb)
    {
        if(fmtType == GMLFORMAT)
        {
            sb.append(":p.:hp2.");
        }
        else
        {
            sb.append("<b>");
        }

        sb.append(geo);

        if(fmtType == GMLFORMAT)
        {
            sb.append("--->:ehp2." + NEWLINE);
            //if(brand.equals(PSERIES))
            if(FORMAT2 == format || FORMAT3 == format)
            {
                sb.append(".br" + NEWLINE);
            }
        }
        else
        {
            sb.append("---&gt</b>" + NEWLINE);
        }
    }

    private void bldEndGeoTags(String geo, boolean fmtType, StringBuffer sb)
    {
        if(fmtType == GMLFORMAT)
        {
            sb.append(".br;:hp2.<---");
        }
        else
        {
            sb.append("<b>&lt---");
        }

        sb.append(geo);

        if(fmtType == GMLFORMAT)
        {
            sb.append(":ehp2." + NEWLINE);
        }
        else
        {
            //sb.append("</b></br>\n");
            sb.append("</b>" + NEWLINE);
        }
    }

    private void printBeginGeoTags(String geo, boolean fmtType, StringBuffer sb)
    {
        if(!geo.equals("WW") && !geo.equals(""))
        {
            if(fmtType == GMLFORMAT)
            {
                sb.append(":p.:hp2.");
            }
            else
            {
//            sb.append("</pre>\n");
                sb.append("<b>");
            }

            sb.append(geo);

            if(fmtType == GMLFORMAT)
            {
                sb.append("--->:ehp2." + NEWLINE);
                //if(brand.equals(PSERIES))
                if(FORMAT2 == format || FORMAT3 == format)
                {
                    sb.append(".br" + NEWLINE);
                }
            }
            else
            {
                sb.append("---&gt</b>" + NEWLINE);
//            sb.append("<pre>\n");
            }
        }
    }

    private void printEndGeoTags(String geo, boolean fmtType, StringBuffer sb)
    {
        if(!geo.equals("WW") && !geo.equals(""))
        {
            if(fmtType == GMLFORMAT)
            {
                sb.append(".br;:hp2.<---");
            }
            else
            {
//            sb.append("</pre>\n");
                sb.append("<b>&lt---");
            }

            sb.append(geo);

            if(fmtType == GMLFORMAT)
            {
                sb.append(":ehp2." + NEWLINE);
            }
            else
            {
//            sb.append("</b></br>\n");
//            sb.append("<pre>\n");
                sb.append("</b>" + NEWLINE);
            }
        }
    }

    private void log(String str)
    {
        if(debug)
        {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            String dt = sdf.format(new Date());
            debugBuff.append("</br>" + dt + "  " + str + NEWLINE);
        }
    }

    /**
    * Debug information
    *
    * @return String
    */
    public StringBuffer getDebugBuffer()
    {
        return debugBuff;
    }

    /**
    * Clean up
    *
    */
    public void cleanUp()
    {
        Iterator tsItr = machineTypeTS.iterator();
        //Iterator tsItr = featureMatrixError.iterator();
        while(tsItr.hasNext())
        {
            String machineType = (String) tsItr.next();
            log("machinetype = " + machineType);
        }

        usGeoHT.clear();
        usGeoHT = null;
        apGeoHT.clear();
        apGeoHT = null;
        laGeoHT.clear();
        laGeoHT = null;
        canGeoHT.clear();
        canGeoHT = null;
        emeaGeoHT.clear();
        emeaGeoHT = null;

        geoHT.clear();
        geoHT = null;

        machineTypeTS.clear();
        machineTypeTS = null;
        featureHT.clear();
        featureHT = null;

        productNumber_NewModels_TM.clear();
        productNumber_NewModels_TM = null;
        productNumber_NewModels_HT.clear();
        productNumber_NewModels_HT = null;
        productNumber_NewFC_TM.clear();
        productNumber_NewFC_TM = null;
        productNumber_ExistingFC_TM.clear();
        productNumber_ExistingFC_TM = null;
        productNumber_NewModels_NewFC_TM.clear();
        productNumber_NewModels_NewFC_TM = null;
        productNumber_NewModels_ExistingFC_TM.clear();
        productNumber_NewModels_ExistingFC_TM = null;
        productNumber_ExistingModels_NewFC_TM.clear();
        productNumber_ExistingModels_NewFC_TM = null;
        productNumber_ExistingModels_ExistingFC_TM.clear();
        productNumber_ExistingModels_ExistingFC_TM = null;

        productNumber_MTM_Conversions_TM.clear();
        productNumber_MTM_Conversions_TM = null;
        productNumber_Model_Conversions_TM.clear();
        productNumber_Model_Conversions_TM = null;
        productNumber_Feature_Conversions_TM.clear();
        productNumber_Feature_Conversions_TM = null;
        featureVector.clear();
        featureVector = null;

        charges_NewModels_TM.clear();
        charges_NewModels_TM = null;
        charges_NewFC_TM.clear();
        charges_NewFC_TM = null;
        charges_ExistingFC_TM.clear();
        charges_ExistingFC_TM = null;
        charges_NewModels_NewFC_TM.clear();
        charges_NewModels_NewFC_TM = null;
        charges_NewModels_ExistingFC_TM.clear();
        charges_NewModels_ExistingFC_TM = null;
        charges_ExistingModels_NewFC_TM.clear();
        charges_ExistingModels_NewFC_TM = null;
        charges_ExistingModels_ExistingFC_TM.clear();
        charges_ExistingModels_ExistingFC_TM = null;
        charges_Feature_Conversions_TM.clear();
        charges_Feature_Conversions_TM = null;

        q779_NewModels_TM.clear();
        q779_NewModels_TM = null;
        q779_NewFeatures_TM.clear();
        q779_NewFeatures_TM = null;

        salesManual_TM.clear();
        salesManual_TM = null;
        salesManualSpecifyFeatures_TM.clear();
        salesManualSpecifyFeatures_TM = null;
        salesManualSpecialFeaturesInitialOrder_TM.clear();
        salesManualSpecialFeaturesInitialOrder_TM = null;
        salesManualSpecialFeaturesOther_TM.clear();
        salesManualSpecialFeaturesOther_TM = null;

        supportedDevices_TM.clear();
        supportedDevices_TM = null;

        featureMatrix_TM.clear();
        featureMatrix_TM = null;

        headerSB.delete(0, headerSB.length());
        headerSB = null;

        lseoVector.clear();
        lseoVector = null;
        mtmTS.clear();
        mtmTS = null;
        seoTable_TM.clear();
        seoTable_TM = null;
        seoDescription_TM.clear();
        seoDescription_TM = null;

        featureMatrixError.clear();
        featureMatrixError = null;
    }

    /********************************************************************************
    * Get the display value for the Attribute.
    *
    * @param item EntityItem
    * @param attCode    String Attribute code
    * @param delim      String delimiter used to separate flag values (descriptions)
    * @param defValue   String used if attribute does not have a value
    * @return String
    */
    public static String getAttributeValue(EntityItem item, String attCode, String delim, String defValue)
    {
        return getAttributeValue(item, attCode, delim, defValue, true, "eannounce");
    }

    /********************************************************************************
    * Get the display value for the Attribute.
    *
    * @param item EntityItem
    * @param attCode    String Attribute code
    * @param delim      String delimiter used to separate flag values (descriptions)
    * @param defValue   String used if attribute does not have a value
    * @param convert    boolean if true, value will be converted to valid html
    * @return String
    */
    public static String getAttributeValue(EntityItem item, String attCode, String delim, String defValue,
        boolean convert)
    {
        return getAttributeValue(item, attCode, delim, defValue, convert, "eannounce");
    }

    /********************************************************************************
    * Get the display value for the Attribute.
    *
    * @param item EntityItem
    * @param attCode    String Attribute code
    * @param delim      String delimiter used to separate flag values (descriptions)
    * @param defValue   String used if attribute does not have a value
    * @param convert    boolean if true, value will be converted to valid html
    * @param applicationName String with application name for getblob
    * @return String
    */
    public static String getAttributeValue(EntityItem item, String attCode, String delim, String defValue,
        boolean convert, String applicationName)
    {
        EANMetaAttribute metaAttr = item.getEntityGroup().getMetaAttribute(attCode);
        StringBuffer sb = new StringBuffer();
        EANAttribute attr = null;
        if (metaAttr==null) {
            return ("<font color=\"red\">Attribute &quot;"+attCode+"&quot; NOT found in &quot;"+
                item.getEntityType()+"&quot; META data.</font>"); }

        attr = item.getAttribute(attCode);
        if (attr == null) {
            return defValue; }

        if (attr instanceof EANFlagAttribute)
        {
            // Get all the Flag values.
            MetaFlag[] mfArray = (MetaFlag[]) attr.get();
            for (int i = 0; i < mfArray.length; i++)
            {
                // get selection
                if (mfArray[i].isSelected())
                {
                    if (sb.length()>0) {
                        sb.append(delim); }
                    // convert all flag descriptions too
                    if (convert){
                        sb.append(convertToHTML(mfArray[i].toString()));}
                    else {
                        sb.append(mfArray[i].toString());}
                    if (metaAttr.getAttributeType().equals("U")) {
                        break; }
                }
            }
        }
        else if (attr instanceof EANTextAttribute)
        {
            // L and T and I text attributes must be converted to prevent invalid html
            if (metaAttr.getAttributeType().equals("T") || metaAttr.getAttributeType().equals("L")
                || metaAttr.getAttributeType().equals("I")) // FB52179
            {
                // convert the html special chars
                if (convert) {
                    sb.append(convertToHTML(attr.get().toString()));}
                else {
                    sb.append(attr.get().toString());}
            }
            else {
                sb.append(attr.get().toString());}
        }
        else if (attr instanceof EANBlobAttribute)
        {
            // only 'B' binary now
            if (metaAttr.getAttributeType().equals("B"))
            {
                EANBlobAttribute blobAtt = (EANBlobAttribute) attr;
                // sometimes the entire file name, rather than just
                // the extension, is stored in the m_strBlobExtension
                // variable.
                if (blobAtt.getBlobExtension().toUpperCase().endsWith(".GIF") ||
                    blobAtt.getBlobExtension().toUpperCase().endsWith(".JPG"))
                {
                    sb.append("<img src='/"+applicationName+"/GetBlobAttribute?entityID=" + item.getEntityID() +
                        "&entityType=" + item.getEntityType() +
                        "&attributeCode=" + attCode +
                        "' alt='image' />");  // close tag needed for XML
                }
                else
                {
                    // the HTML field is a link to a temp file generated by the
                    // GetBlobAttribute Servlet. note, we use ouputMode=F to indicate we
                    // want to generate a temp file and
                    // execute a browser-redirect to the temp file.
                    /*sb.append("<a href='/"+applicationName+"/GetBlobAttribute?outputMode=F"+
                        "&entityID=" + item.getEntityID()+
                        "&entityType=" + item.getEntityType()+
                        "&attributeCode=" +attCode);
                    sb.append("' />");
                    sb.append("Download this file for viewing.</a>");*/

                    // add support for other binary types FB53628:6FF425
                    sb.append("<form action=\"/"+applicationName+"/PokXMLDownload\" name=\""+item.getEntityType()
                            +item.getEntityID()+attCode+"\" method=\"post\"> "+NEWLINE);
                    sb.append("<input type=\"hidden\" name=\"entityType\" value=\"" + item.getEntityType() +"\" />"+NEWLINE);
                    sb.append("<input type=\"hidden\" name=\"entityID\" value=\"" + item.getEntityID() +"\" />"+NEWLINE);
                    sb.append("<input type=\"hidden\" name=\"downloadType\" value=\"blob\" />"+NEWLINE);
                    sb.append("<input type=\"hidden\" name=\"attributeCode\" value=\""+attCode+"\" />"+NEWLINE);
                    sb.append("<input type=\"submit\" value=\"Download\" />"+NEWLINE);
                    sb.append("</form>"+NEWLINE);

                    //sb.append("<font color=\"red\">Blob Attribute for "+attCode+", extension: "+
                    //  blobAtt.getBlobExtension()+" is NOT yet supported</font>");
                }
            }
            else {
                sb.append("<font color=\"red\">Blob Attribute type &quot;"+metaAttr.getAttributeType()+
                    "&quot; for "+attCode+" NOT yet supported</font>"); }
        }

        if (sb.length()==0) {
            return defValue; }

        return sb.toString();
    }

    /********************************************************************************
    * Convert string into valid html.  Special HTML characters are converted.
    *
    * @param txt    String to convert
    * @return String
    */
    public static String convertToHTML(String txt)
    {
        StringBuffer htmlSB = new StringBuffer();
        StringCharacterIterator sci = null;
        char ch = ' ';
        if (txt == null) {
            return null;}

        sci = new StringCharacterIterator(txt);
        ch = sci.first();
        while(ch != CharacterIterator.DONE)
        {
            switch(ch)
            {
            case '<': // could be saved as &lt; also. this will be &#60;
            case '>': // could be saved as &gt; also. this will be &#62;
            case '"': // could be saved as &quot; also. this will be &#34;
            // this should be included too, but left out to be consistent with west coast
            //case '&': // ignore entity references such as &lt; if user typed it, user will see it
                      // could be saved as &amp; also. this will be &#38;
                htmlSB.append("&#"+((int)ch)+";");
                break;
            case '\n':  // maintain new lines
                htmlSB.append("<br />");
                break;
            default:
                if (Character.isSpaceChar(ch))// check for unicode space character
                {
                    htmlSB.append("&#32;"); // this fails because extra whitespace, even &#32;, is discarded
                    // but left to be consistent with WestCoast code
//                      htmlSB.append("&nbsp;"); // this will correctly maintain spaces
                }
                else {
                    htmlSB.append(ch);}
                break;
            }
            ch = sci.next();
        }

        return htmlSB.toString();
    }

    /*****************************************************************************
    * Get the current Flag Value for the specified attribute, null if not set
    *
    * @param entityItem EntityItem
    * @param attrCode String attribute code to get value for
    * @return String attribute flag code
    */
    public static String getAttributeFlagValue(EntityItem entityItem, String attrCode)
    {
        EANMetaAttribute metaAttr = entityItem.getEntityGroup().getMetaAttribute(attrCode);
        // Multi-flag values will be separated by |
        EANAttribute attr = entityItem.getAttribute(attrCode);
        if (attr == null) {
            return null; }

        if (attr instanceof EANFlagAttribute)
        {
            StringBuffer sb = new StringBuffer();

            // Get the selected Flag codes.
            MetaFlag[] mfArray = (MetaFlag[]) attr.get();
            for (int i = 0; i < mfArray.length; i++)
            {
                // get selection
                if (mfArray[i].isSelected())
                {
                    if (sb.length()>0) {
                        sb.append(DELIMITER); }
                    sb.append(mfArray[i].getFlagCode());
                    if (metaAttr.getAttributeType().equals("U")) {
                        break; }
                }
            }
            return sb.toString();
        }

        return null;
    }

    /********************************************************************************
    * Find entities of the destination type linked to the EntityItems in the EntityGroup
    * through the specified link type.  Both uplinks and downlinks are checked
    * though only one will contain a match.
    * All objects in the source Vector must be EntityItems of the same entity type
    *
    * @param srcGrp     EntityGroup
    * @param linkType   String Association or Relator type linking the entities
    * @param destType   String EntityType to match
    * @return Vector of EntityItems
    */
    public static Vector getAllLinkedEntities(EntityGroup srcGrp, String linkType, String destType)
    {
        // find entities thru 'linkType' relators
        Vector destVct = new Vector(1);
        if (srcGrp == null) {
            return destVct; }

        for(int ie=0; ie<srcGrp.getEntityItemCount();ie++)
        {
            EntityItem entityItem = srcGrp.getEntityItem(ie);
            getLinkedEntities(entityItem, linkType, destType, destVct);
        }

        return destVct;
    }

    /********************************************************************************
    * Find entities of the destination type linked to the EntityItems in the source
    * vector through the specified link type.  Both uplinks and downlinks are checked
    * though only one will contain a match.
    * All objects in the source Vector must be EntityItems of the same entity type
    *
    * @param srcVct     Vector of EntityItems
    * @param linkType   String Association or Relator type linking the entities
    * @param destType   String EntityType to match
    * @returns Vector of EntityItems
    */
    public static Vector getAllLinkedEntities(Vector srcVct, String linkType, String destType)
    {
        // find entities thru 'linkType' relators
        Vector destVct = new Vector(1);

        Iterator srcItr = srcVct.iterator();
        while (srcItr.hasNext())
        {
            EntityItem entityItem = (EntityItem)srcItr.next();
            getLinkedEntities(entityItem, linkType, destType, destVct);
        }

        return destVct;
    }

    /********************************************************************************
    * Find entities of the destination type linked to the EntityItems in the source
    * vector through the specified link type.  Both uplinks and downlinks are checked
    * though only one will contain a match.
    * All objects in the source Vector must be EntityItems of the same entity type
    *
    * @param entityItem EntityItem
    * @param linkType   String Association or Relator type linking the entities
    * @param destType   String EntityType to match
    * @returns Vector of EntityItems
    */
    public static Vector getAllLinkedEntities(EntityItem entityItem, String linkType, String destType)
    {
        // find entities thru 'linkType' relators
        Vector destVct = new Vector(1);

        getLinkedEntities(entityItem, linkType, destType, destVct);

        return destVct;
    }

    /********************************************************************************
    * Find entities of the destination type linked to the specified EntityItem through
    * the specified link type.  Both uplinks and downlinks are checked though only
    * one will contain a match.
    *
    * @param entityItem EntityItem
    * @param linkType   String Association or Relator type linking the entities
    * @param destType   String EntityType to match
    * @param destVct    Vector EntityItems found are returned in this vector
    */
    private static void getLinkedEntities(EntityItem entityItem, String linkType, String destType,
        Vector destVct)
    {
        if (entityItem==null) {
            return; }

        // see if this relator is used as an uplink
        for (int ui=0; ui<entityItem.getUpLinkCount(); ui++)
        {
            EANEntity entityLink = entityItem.getUpLink(ui);
            if (entityLink.getEntityType().equals(linkType))
            {
                // check for destination entity as an uplink
                for (int i=0; i<entityLink.getUpLinkCount(); i++)
                {
                    EANEntity entity = entityLink.getUpLink(i);
                    if (entity.getEntityType().equals(destType) && !destVct.contains(entity)) {
                        destVct.addElement(entity); }
                }
                // check for destination entity as a downlink
                /*for (int i=0; i<entityLink.getDownLinkCount(); i++)
                {
                    EANEntity entity = entityLink.getDownLink(i);
                    if (entity.getEntityType().equals(destType) && !destVct.contains(entity))
                        destVct.addElement(entity);
                }*/
            }
        }

        // see if this relator is used as a downlink
        for (int ui=0; ui<entityItem.getDownLinkCount(); ui++)
        {
            EANEntity entityLink = entityItem.getDownLink(ui);
            if (entityLink.getEntityType().equals(linkType))
            {
                // check for destination entity as an uplink
                /*for (int i=0; i<entityLink.getUpLinkCount(); i++)
                {
                    EANEntity entity = entityLink.getUpLink(i);
                    if (entity.getEntityType().equals(destType) && !destVct.contains(entity))
                        destVct.addElement(entity);
                }*/
                // check for destination entity as a downlink
                for (int i=0; i<entityLink.getDownLinkCount(); i++)
                {
                    EANEntity entity = entityLink.getDownLink(i);
                    if (entity.getEntityType().equals(destType) && !destVct.contains(entity)) {
                        destVct.addElement(entity); }
                }
            }
        }
    }

    /********************************************************************************
    * Find entities in the specified vector that have an attribute value that
    * matches the passed in value.
    * Flags and text attributes are checked.  If it is a flag, flag codes are checked.
    * If multiple values are needed, separate them with a Delimiter ("|")
    *
    * @param srcVct Vector of EntityItems
    * @param attrCode   String Attribute code
    * @param valToMatch String Value(s) to be matched
    * @return Vector of EntityItems
    */
    public static Vector getEntitiesWithMatchedAttr(Vector srcVct, String attrCode, String valToMatch)
    {
        Vector matchVct = new Vector(1);
        // get list of entities with attr = valToMatch
        Iterator srcItr = srcVct.iterator();
        while (srcItr.hasNext())
        {
            EntityItem entityItem = (EntityItem)srcItr.next();
            if (entityMatchesAttr(entityItem, attrCode, valToMatch)) {
                matchVct.addElement(entityItem); }
        }

        return matchVct;
    }

    /********************************************************************************
    * Find entities in the specified group that have an attribute value that
    * matches the passed in value.
    * Flags and text attributes are checked.  If it is a flag, flag codes are checked.
    * If multiple values are needed, separate them with a Delimiter ("|")
    *
    * @param srcGrp EntityGroup
    * @param attrCode   String Attribute code
    * @param valToMatch String Value(s) to be matched
    * @return Vector of EntityItems
    */
    public static Vector getEntitiesWithMatchedAttr(EntityGroup srcGrp, String attrCode, String valToMatch)
    {
        Vector matchVct = new Vector(1);
        if (srcGrp == null) {
            return matchVct; }

        // get list of entities with attr = valToMatch
        for(int ie=0; ie<srcGrp.getEntityItemCount();ie++)
        {
            EntityItem entityItem = srcGrp.getEntityItem(ie);
            if (entityMatchesAttr(entityItem, attrCode, valToMatch)) {
                matchVct.addElement(entityItem);}
        }

        return matchVct;
    }

    /********************************************************************************
    * Find entities in the specified vector that have attribute values that
    * match the passed in values.  All values must match.
    * Flags and text attributes are checked.  If it is a flag, flag codes are checked.
    * If multiple values are needed, separate them with a Delimiter ("|")
    *
    * @param srcVct Vector of EntityItems
    * @param attrValTbl Hashtable with Attribute code as key and String to match as value
    * @returns Vector of EntityItems
    */
    public static Vector getEntitiesWithMatchedAttr(Vector srcVct, Hashtable attrValTbl)
    {
        Vector matchVct = new Vector(1);
        // get list of entities with attr = valToMatch
        Iterator srcItr = srcVct.iterator();
        while (srcItr.hasNext())
        {
            EntityItem entityItem = (EntityItem)srcItr.next();
            boolean allMatch=true;
            for (Enumeration e = attrValTbl.keys(); e.hasMoreElements();)
            {
                String attrCode = (String)e.nextElement();
                allMatch=allMatch&&
                    entityMatchesAttr(entityItem, attrCode, attrValTbl.get(attrCode).toString());
            }
            if (allMatch) {
                matchVct.addElement(entityItem);}
        }

        return matchVct;
    }

    /********************************************************************************
    * Find entities in the specified group that have attribute values that
    * match the passed in values.  All values must match.
    * Flags and text attributes are checked.  If it is a flag, flag codes are checked.
    * If multiple values are needed, separate them with a Delimiter ("|")
    *
    * @param srcGrp EntityGroup
    * @param attrValTbl Hashtable with Attribute code as key and String to match as value
    * @returns Vector of EntityItems
    */
    public static Vector getEntitiesWithMatchedAttr(EntityGroup srcGrp, Hashtable attrValTbl)
    {
        Vector matchVct = new Vector(1);
        if (srcGrp == null) {
            return matchVct;}

        // get list of entities with attr = valToMatch
        for(int ie=0; ie<srcGrp.getEntityItemCount();ie++)
        {
            EntityItem entityItem = srcGrp.getEntityItem(ie);
            boolean allMatch=true;
            for (Enumeration e = attrValTbl.keys(); e.hasMoreElements();)
            {
                String attrCode = (String)e.nextElement();
                allMatch=allMatch&&
                    entityMatchesAttr(entityItem, attrCode, attrValTbl.get(attrCode).toString());
            }
            if (allMatch) {
                matchVct.addElement(entityItem);}
        }

        return matchVct;
    }

    /********************************************************************************
    * Check to see if this entity's attribute has a value to match the passed in value.
    * Flags and text attributes are checked.  If it is a flag, flag codes are checked.
    * If multiple values are needed, separate them with a Delimiter ("|")
    *
    * @param entityItem EntityItem
    * @param attrCode   String Attribute code
    * @param valToMatch String Value(s) to be matched
    * @return boolean
    */
    private static boolean entityMatchesAttr(EntityItem entityItem, String attrCode, String valToMatch)
    {
        // Multi-flag values will be separated by |
        EANAttribute attr = entityItem.getAttribute(attrCode);
        if (attr == null) {
            return false; }

        if (attr instanceof EANFlagAttribute)
        {
            Vector vct = new Vector(1);
            String[] matchArray = null;
            int count=0;
            // Get all the Flag values.
            MetaFlag[] mfArray = (MetaFlag[]) attr.get();
            for (int i = 0; i < mfArray.length; i++)
            {
                // get selection
                if (mfArray[i].isSelected())
                {
                    vct.addElement(mfArray[i].getFlagCode());
                }
            }

            // convert valToMatch into an array of strings separated by Delimiter ("|")
            matchArray = convertToArray(valToMatch);
            for (int i=0; i<matchArray.length; i++)
            {
                if (vct.contains(matchArray[i])) {
                    count++; }
            }
            if (count==matchArray.length) {
                return true; }

        }
        else if (attr instanceof EANTextAttribute)
        {
            // values must match
            return attr.get().toString().equals(valToMatch);
        }

        return false;
    }

    /**
     * Returns true if the flag is selected. Returns false if any parameter is null,
     * the attribute code is not a flag attribute, or the flag code is not valid for the attribute code.
     *@param item EntityItem to check
     *@param attCode String the attribute code to check
     *@param flagCode String the flagCode to check
     *@return boolean true if it is selected
     */
    public static boolean isSelected(EntityItem item, String attCode, String flagCode) {
        EANAttribute att = null;
        if (item == null) {
            return false;}
        if (attCode == null) {
            return false;}
        if (flagCode == null) {
            return false;}

        // Get the attribute
        att = item.getAttribute(attCode);
        // If it is a Flag
        if (att instanceof EANFlagAttribute) {
            EANFlagAttribute fAtt = (EANFlagAttribute) att;
            EANMetaFlagAttribute mfa = (EANMetaFlagAttribute) item.getEntityGroup().getMetaAttribute(attCode);
            MetaFlag mf = mfa.getMetaFlag(flagCode);
            if (mf == null) {
                return false;}
            return fAtt.isSelected(mf);
        }
        return false;
    }

    /********************************************************************************
    * Convert the string into an array.  The string is a list of values delimited by
    * Delimiter
    *
    * @param data String
    * @returns String[] one delimited string per element
    */
    public static String[] convertToArray(String data)
    {
        Vector vct = new Vector();
        String array[] = null;
        // parse the string into substrings
        if (data!=null)
        {
            StringTokenizer st = new StringTokenizer(data,DELIMITER);
            while(st.hasMoreTokens())
            {
                vct.addElement(st.nextToken());
            }
        }
        array= new String[vct.size()];
        vct.copyInto(array);
        return array;
    }

    /********************************************************************************
    * Debug purpose
    *
    *
    * @param aList EntityList
    * @returns String
    */
    public static String outputList(EntityList aList) // debug
    {
        StringBuffer sb = new StringBuffer();
        EntityGroup peg = aList.getParentEntityGroup();
        if (peg!=null)
        {
            sb.append(peg.getEntityType()+" : "+peg.getEntityItemCount()+" Parent entity items. ");
            if (peg.getEntityItemCount()>0)
            {
                sb.append("IDs(");
                for (int e=0; e<peg.getEntityItemCount(); e++)
                {
                    sb.append(" "+peg.getEntityItem(e).getEntityID());
                }
                sb.append(")");
            }
            sb.append(NEWLINE);
        }

        for (int i=0; i<aList.getEntityGroupCount(); i++)
        {
            EntityGroup eg =aList.getEntityGroup(i);
            sb.append(eg.getEntityType()+" : "+eg.getEntityItemCount()+" entity items. ");
            if (eg.getEntityItemCount()>0)
            {
                sb.append("IDs(");
                for (int e=0; e<eg.getEntityItemCount(); e++)
                {
                    sb.append(" "+eg.getEntityItem(e).getEntityID());
                }
                sb.append(")");
            }
            sb.append(NEWLINE);
        }
        return sb.toString();
    }

    /********************************************************************************
    *
    *
    * @param featureItem EntityItem
    * @returns boolean
    */
    private boolean isNewFeature(EntityItem featureItem)
    {
        String extractName;
        EntityItem[] eiArray;
        EntityList aList;
        String key;
        String featureCode;
        boolean rc = true;
        String mt;
        TreeSet mtTS;
        Iterator srcItr;
        Hashtable tempHT = new Hashtable();

        /*** Need to get flag codes for COFCAT, COFSUBCAT, COFGRP. As of today 8/17/04 flag codes are not defined yet ***/
        tempHT.clear();
        tempHT.put("COFCAT", "100");    //Hardware
        //if(!brand.equals(XSERIES))
        //{
        //    tempHT.put("COFSUBCAT", "126"); //System
        //}
        tempHT.put("COFGRP", "150");    //Base

        log(featureItem.toString());

        extractName = "EXRPT3FM";
        eiArray = new EntityItem[1];
        eiArray[0] = featureItem;
        aList = null;

        key = featureItem.getKey();
        featureCode = getAttributeValue(featureItem, "FEATURECODE", "", "", false);
        featureCode = featureCode.trim();
        featureCode = setString(featureCode, 4);

        if(featureHT.containsKey(key))
        {
            String value = (String) featureHT.get(key);
            log("---> found it " + featureItem.toString());
            if(value.equals("New"))
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        //Get all machine types that use this feature in this announcement
        mt = "";
        mtTS = new TreeSet();

        srcItr = availVector.iterator();
        while(srcItr.hasNext())
        {
            EntityItem availItem = (EntityItem) srcItr.next();
            Vector prodstructVector = getAllLinkedEntities(availItem, "OOFAVAIL", "PRODSTRUCT");
            for(int i = 0; i < prodstructVector.size(); i++)
            {
                EntityItem prodstructItem = (EntityItem) prodstructVector.get(i);

                EANEntity entityDownLink = getDownLinkEntityItem(prodstructItem, "MODEL");
                if(null != entityDownLink)
                {
                    if(isEntityWithMatchedAttr((EntityItem) entityDownLink, tempHT))
                    {
                        EntityItem modelItem = (EntityItem) entityDownLink;
                        EANEntity entityUpLink = getUpLinkEntityItem(prodstructItem, "FEATURE");
                        if(null != entityUpLink)
                        {
                            EntityItem featureEI = (EntityItem) entityUpLink;
                            String fc = getAttributeValue(featureEI, "FEATURECODE", "", "", false);
                            fc = fc.trim();
                            fc = setString(fc, 4);

                            if(fc.equals(featureCode))
                            {
                                mt = getAttributeFlagValue(modelItem, "MACHTYPEATR");
                                if(null == mt)
                                {
                                    mt = " ";
                                }
                                mt = mt.trim();
                                mt = setString(mt, 4);
                                mtTS.add(mt);
                            }
                        }//end of if(null != entityUpLink)
                    }//end of if(isEntityWithMatchedAttr((EntityItem) entityDownLink, tempHT))
                }//end of if(null != entityDownLink)
            }//end of for(int i = 0; i < prodstructVector.size(); i++)
        }//end of while(srcItr.hasNext())

        try
        {
            EntityGroup peg;
            Vector prodstructVector = new Vector();
            Profile profile = list.getProfile();
            aList = dbCurrent.getEntityList(profile,
                new ExtractActionItem(null, dbCurrent, profile, extractName),
                eiArray);

            if(aList.getEntityGroupCount() == 0) // ERROR meta not found for extract
            {
                log("Extract was not found for " + extractName);
            }

            peg = aList.getParentEntityGroup();

            featureItem = peg.getEntityItem(0);

            for(int i = 0; i < featureItem.getDownLinkCount(); i++)
            {
                EANEntity entity = featureItem.getDownLink(i);

                if(entity.getEntityType().equals("PRODSTRUCT"))
                {
                    EntityItem prodstructItem = (EntityItem) entity;
                    EANEntity entityDownLink = getDownLinkEntityItem(prodstructItem, "MODEL");
                    log(entity.toString());
                    if(null != entityDownLink)
                    {
                        if(isEntityWithMatchedAttr((EntityItem) entityDownLink, tempHT))
                        {
                            EntityItem modelItem = (EntityItem) entityDownLink;
                            String machineType = getAttributeFlagValue(modelItem, "MACHTYPEATR");
                            if(null == machineType)
                            {
                                machineType = " ";
                            }
                            machineType = machineType.trim();
                            machineType = setString(machineType, 4);

                            if(mtTS.contains(machineType))
                            {
                                prodstructVector.add(prodstructItem);
                            }
                        }
                    }
                }//end of if(entity.getEntityType().equals("PRODSTRUCT"))
            }//end of for(int i = 0; i < featureItem.getDownLinkCount(); i++)

            if(prodstructVector.size() == 0)
            {
                featureHT.put(key, "New");
                return true;
            }

            for(int i = 0; i < prodstructVector.size(); i++)
            {
                EntityItem prodstructItem = (EntityItem) prodstructVector.get(i);
                String aDate = getAttributeValue(prodstructItem, "ANNDATE", "", "", false);
                if(aDate.equals(""))
                {
                    EANEntity entityDownLink = getDownLinkEntityItem(prodstructItem, "MODEL");
                    if(null != entityDownLink)
                    {
                        EntityItem modelItem = (EntityItem) entityDownLink;
                        aDate = getAttributeValue(modelItem, "ANNDATE", "", "", false);
                    }
                }

                if(annDate.compareTo(aDate) > 0)
                {
                    featureHT.put(key, "Old");
                    return false;
                }
            }//end of for(int i = 0; i < prodstructVector.size(); i++)

            featureHT.put(key, "New");
            rc = true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            log(e.toString());
        }

        tempHT.clear();
        tempHT = null;
        mtTS.clear();
        mtTS = null;

        return rc;
    }

    /********************************************************************************
    *
    *
    * @param text String
    * @param gmlTag String
    * @returns boolean
    */
    private boolean textContainsGMLTag(String text, String gmlTag)
    {
        boolean rc = false;
        String [] arrayStr = getStringTokens(text, NEWLINE);
        for(int i = 0; i < arrayStr.length; i++)
        {
            String aStr = arrayStr[i].toLowerCase();
            if(-1 != aStr.indexOf(gmlTag))
            {
                rc = true;
                break;
            }
        }//end of for(int i = 0; i < arrayStr1.length; i++)

        return rc;
    }

    /********************************************************************************
    *
    *
    * @param aStr String
    * @returns String
    */
    private String formatDate(String aStr)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try
        {
            Date aDate = sdf.parse(aStr);
            //sdf = new SimpleDateFormat("MMMM dd, yyyy 'for:'");
            sdf = new SimpleDateFormat("MMMM dd, yyyy");
            return (sdf.format(aDate).toString());
        }
        catch(Exception e)
        {
            return ("Exception in formatDate(): " + e);
        }
    }

    /********************************************************************************
    *
    *
    */
    private void q779_NewModels()
    {
        Iterator srcItr;
        Hashtable tempHT = new Hashtable();
        /*** Need to get flag codes for COFCAT, COFSUBCAT, COFGRP. As of today 8/17/04 flag codes are not defined yet ***/
        tempHT.put("COFCAT", "100");    //Hardware
        //if(!brand.equals(XSERIES))
        //{
        //    tempHT.put("COFSUBCAT", "126"); //System
        //}
        tempHT.put("COFGRP", "150");    //Base

        //Path 1: ANNOUNCEMENT --> ANNAVAILA --> AVAIL --> MODELAVAIL --> MODEL
        srcItr = availVector.iterator();
        while(srcItr.hasNext())
        {
            EntityItem availItem = (EntityItem) srcItr.next();
            Vector modelVector = getAllLinkedEntities(availItem, "MODELAVAIL", "MODEL");
            modelVector = getEntitiesWithMatchedAttr(modelVector, tempHT);
            for(int i = 0; i < modelVector.size(); i++)
            {
                EntityItem modelItem = (EntityItem) modelVector.get(i);

                populate_Q779_NewModels_TM(availItem, modelItem);
            }//end of for(int i = 0; i < modelVector.size(); i++)
        }//end of while(srcItr.hasNext())
    }

    /********************************************************************************
    *
    *
    * @param availItem EntityItem
    * @param modelItem EntityItem
    */
    private void populate_Q779_NewModels_TM(EntityItem availItem, EntityItem modelItem)
    {
        /*
           Key = MACHTYPEATR(MODEL) +
                 MODELATR(MODEL) +
                 IOR +
                 MKTGNAME(MODEL) +
        */
        String key;
        String mt;
        String model;
        String geo;
        String mktgName;
        String modelItemKey = modelItem.getKey();
        updateGeoHT(availItem, modelItemKey);

        key = getAttributeFlagValue(modelItem, "MACHTYPEATR");
        if(null == key)
        {
            key = " ";
        }
        key = key.trim();
        key = setString(key, 4);
        mt = key;
        model = getAttributeValue(modelItem, "MODELATR", "", "000", false);
        model = model.trim();
        model = setString(model, 3);
        key = key + "<:>" + model;
        key = key + "<:>" + "IOR";

        mktgName = getAttributeValue(modelItem, "MKTGNAME", "", "", false);
        mktgName = mktgName.trim();
        if(mktgName.equals(""))
        {
            featureMatrixError.add("21<:>" + "XCHARGES<:>" + mt + "<:>" + model + "<:>MODEL<:>Marketing Name");
        }
        key = key + "<:>" + mktgName;

        geo = getGeo(modelItemKey);

        addToTreeMap(key, geo, q779_NewModels_TM);
    }

    /********************************************************************************
    *
    *
    */
    private void q779_NewFeatures()
    {
        /*
           Key = FEATURECODE(FEATURE) +
                 MACHTYPEATR(MODEL) +
                 MODELATR(MODEL) +
                 CCE/CCR +
                 MKTGNAME(PRODSTRUCT) or MKTGNAME(FEATURE) +
                 XXXX.XX or NC
        */
        Hashtable tempHT = new Hashtable();
        String mt = "";
        boolean newFC = true;

        Iterator srcItr = availVector.iterator();
        while(srcItr.hasNext())
        {
            EntityItem availItem = (EntityItem) srcItr.next();
            Vector prodstructVector = getAllLinkedEntities(availItem, "OOFAVAIL", "PRODSTRUCT");
            for(int i = 0; i < prodstructVector.size(); i++)
            {
                EANEntity entityDownLink;
                EntityItem prodstructItem = (EntityItem) prodstructVector.get(i);
                String prodstructItemKey = prodstructItem.getKey();
                updateGeoHT(availItem, prodstructItemKey);

                entityDownLink = getDownLinkEntityItem(prodstructItem, "MODEL");
                if(null != entityDownLink)
                {
                    /*** Need to get flag codes for COFCAT, COFSUBCAT, COFGRP. As of today 8/17/04 flag codes are not defined yet ***/
                    tempHT.clear();
                    tempHT.put("COFCAT", "100");    //Hardware
                    //if(!brand.equals(XSERIES))
                    //{
                    //    tempHT.put("COFSUBCAT", "126"); //System
                    //}
                    tempHT.put("COFGRP", "150");    //Base
                    if(isEntityWithMatchedAttr((EntityItem) entityDownLink, tempHT))
                    {
                        EntityItem modelItem = (EntityItem) entityDownLink;
                        EANEntity entityUpLink = getUpLinkEntityItem(prodstructItem, "FEATURE");
                        if(null != entityUpLink)
                        {
                            EntityItem featureItem;
                            String key;
                            String fc;
                            String model;
                            String featureFirstAnnDate;
                            String mktgName;
                            String geo;

                            featureItem = (EntityItem) entityUpLink;
                            key = getAttributeValue(featureItem, "FEATURECODE", "", "", false);
                            key = key.trim();
                            key = setString(key, 4);
                            fc = key;

                            mt = getAttributeFlagValue(modelItem, "MACHTYPEATR");
                            if(null == mt)
                            {
                                mt = " ";
                            }
                            mt = mt.trim();
                            mt = setString(mt, 4);
                            key = key + "<:>" + mt;
                            model = getAttributeValue(modelItem, "MODELATR", "", "000", false);
                            model = model.trim();
                            model = setString(model, 3);
                            key = key + "<:>" + model;

                            featureFirstAnnDate = getAttributeValue(featureItem, "FIRSTANNDATE", "", "", false);
                            if(annDate.equals(featureFirstAnnDate))
                            {
                                newFC = true;
                            }
                            else
                            {
                                if(isNewFeature(featureItem))
                                {
                                    newFC = true;
                                }
                                else
                                {
                                    newFC = false;
                                }
                            }

                            if(fc.equals("    "))
                            {
                                featureMatrixError.add("21<:>" + "XCHARGES<:>" +  mt + "<:>" + model + "<:>FEATURE<:>Feature Code");
                            }

                            key = key + "<:>" + "CCE/CCR";

                            mktgName = getAttributeValue(prodstructItem, "MKTGNAME", "", "", false);
                            mktgName = mktgName.trim();
                            if(mktgName.equals(""))
                            {
                                mktgName = getAttributeValue(featureItem, "MKTGNAME", "", "", false);
                                mktgName = mktgName.trim();
                                if(mktgName.equals(""))
                                {
                                    featureMatrixError.add("21<:>" + "XCHARGES<:>" + mt + "<:>" + model + "<:>PRODSTRUCT " + getAttributeValue(prodstructItem, "COMNAME", "", "", false) + "<:>Marketing Name");
                                    featureMatrixError.add("21<:>" + "XCHARGES<:>" + mt + "<:>" + model + "<:>FEATURE " + fc + "<:>Marketing Name");
                                }
                            }
                            key = key + "<:>" + mktgName;

                            geo = getGeo(prodstructItemKey);

                            if(getAttributeValue(featureItem, "PRICEDFEATURE", "", "", false).equals(""))
                            {
                                featureMatrixError.add("21<:>" + "XCHARGES<:>" + mt + "<:>" + model + "<:>FEATURE " + fc +  "<:>Priced Feature Indicator");
                            }

                            if(getAttributeValue(featureItem, "PRICEDFEATURE", "", "", false).equals(""))
                            {
                                //Set blank
                                key = key + "<:>       ";
                            }
                            else
                            {
                                //if(getAttributeValue(featureItem, "ZEROPRICE", "", "", false).equals(""))
                                //{
                                //Set blank
                                //   key = key + "<:>       ";
                                //}
                                //else
                                //{
                                //Per Alan Crudo 2/15/2005
                                //if PRICEDFEATURE = YES and ZEROPRICE = NO then show XXXX.XX
                                //if PRICEDFEATURE = YES and ZEROPRICE = YES then show XXXX.XX
                                //if PRICEDFEATURE = NO and ZEROPRICE = NO then show NC
                                //if PRICEDFEATURE = NO and ZEROPRICE = YES then show NC
                                //if PRICEDFEATURE = NO and ZEROPRICE = Blank then show NC
                                //Per Alan Crudo
                                //if PRICEDFEATURE = YES and ZEROPRICE = NO then show XXXX.XX
                                //if PRICEDFEATURE = YES and ZEROPRICE = YES then show 0
                                //if PRICEDFEATURE = NO and ZEROPRICE = NO then show NC
                                //if PRICEDFEATURE = NO and ZEROPRICE = YES then show 0
                                tempHT.clear();
                                tempHT.put("PRICEDFEATURE", "100");  //YES
                                //tempHT.put("ZEROPRICE", "120");      //NO
                                if(isEntityWithMatchedAttr(featureItem, tempHT))
                                {
                                    key = key + "<:>XXXX.XX";
                                }
                                tempHT.clear();
                                tempHT.put("PRICEDFEATURE", "120");  //NO
                                //tempHT.put("ZEROPRICE", "120");      //NO
                                if(isEntityWithMatchedAttr(featureItem, tempHT))
                                {
                                    key = key + "<:>     NC";
                                }
                                //}//end of else
                            }//end of else

                        //Per Alan Crudo if Announcement Date of the root ANNOUNCEMENT entity is the same as
                        //First Announcement Date of FEATURE entity then it is a new fc else existing fc.
//                        if(annDate.compareTo(featureFirstAnnDate) > 0)
//                        {
//                           addToTreeMap(key, geo, productNumber_ExistingFC_TM);
//                           log(featureItem.getKey() + ": " + fc + " is an existing fc");
//                        }
//                        else
//                        {
//                           addToTreeMap(key, geo, productNumber_NewFC_TM);
//                           log(featureItem.getKey() + ": " + fc + " is a new fc");
//                        }

                            if(newFC)
                            {
                                addToTreeMap(key, geo, q779_NewFeatures_TM);
                            }
                     //}//end of if(!isEntityWithMatchedAttr((EntityItem) entityUpLink, tempHT))
                        }//end of if(null != entityUpLink)
                    }//end of if(isEntityWithMatchedAttr((EntityItem) entityDownLink, tempHT))
                }//end of if(null != entityDownLink)
            }//end of for(int i = 0; i < prodstructVector.size(); i++)
        }//end of while(srcItr.hasNext())
        tempHT.clear();
        tempHT = null;
    }

    /********************************************************************************
    *
    *
    * @param fmtType boolean
    * @param outSB StringBuffer
    */
    private void retrieveXCharges(boolean fmtType, StringBuffer outSB)
    {
        outSB.append(".* <pre>" + NEWLINE);
        outSB.append(".* " + myDate() + NEWLINE);
        outSB.append(".* " + inventoryGroup + NEWLINE);

        retrieveQ799NewModels(fmtType, outSB);
        retrieveQ799NewFeatures(fmtType, outSB);

        outSB.append(".* </pre>" + NEWLINE);
    }

    /********************************************************************************
    *
    *
    * @param fmtType boolean
    * @param outSB StringBuffer
    */
    private void retrieveQ799NewModels(boolean fmtType, StringBuffer outSB)
    {
        if(q779_NewModels_TM.size () > 0)
        {
            String prevGeo = "";
            String currentGeo = "";
            int nLines;
            Set tmSet;
            Iterator tmItr;

            outSB.append(".RH ON" + NEWLINE);
            outSB.append(".fo off" + NEWLINE);
            outSB.append(".in 2" + NEWLINE);
            outSB.append("Machine/                                                 IBM" + NEWLINE);
            outSB.append("Model     SUPP.                                          List   " + NEWLINE);
            outSB.append("NUMBER    CAT.   DESCRIPTION                             Price* " + NEWLINE);
            outSB.append("--------  -----  --------------------------------------  ------" + NEWLINE);
            outSB.append(".fo on" + NEWLINE);
            outSB.append(".RH OFF" + NEWLINE);
            outSB.append(".pa" + NEWLINE);
            outSB.append(":xmp." + NEWLINE);
            outSB.append(".kp off" + NEWLINE);

            tmSet = q779_NewModels_TM.keySet();
            tmItr = tmSet.iterator();
            while(tmItr.hasNext())
            {
                String [] stringLines;
                String key = (String) tmItr.next();
                currentGeo = (String) q779_NewModels_TM.get(key);
                stringLines = extractStringLines(parseString(key, 4, "<:>"), I_38);
                nLines = 0;

                setGeoTags2(prevGeo, currentGeo, fmtType, outSB);

                do
                {
                    if(0 == nLines)
                    {
                        outSB.append(parseString(key, 1, "<:>"));
                        outSB.append("-");
                        outSB.append(parseString(key, 2, "<:>"));
                        outSB.append("  ");
                        outSB.append(parseString(key, 3, "<:>"));
                        outSB.append("    ");
                        outSB.append(setString(stringLines[nLines], I_38));
                        outSB.append("  ");
                        outSB.append("xxxx.xx");
                        outSB.append(NEWLINE);
                        nLines++;
                    }//end of if(false == firstLine)
                    else
                    {
                        outSB.append(setString(" ", I_17));
                        outSB.append(setString(stringLines[nLines], I_38));
                        outSB.append(NEWLINE);
                        nLines++;
                    }
                }while(nLines < stringLines.length);

                prevGeo = currentGeo;
            }//end of while(tmItr.hasNext())
            if(!currentGeo.equals("WW"))
            {
                bldEndGeoTags(currentGeo, fmtType, outSB);
            }
            if(fmtType == GMLFORMAT)
            {
                outSB.append(":exmp." + NEWLINE);
                outSB.append(".RH CANCEL" + NEWLINE + NEWLINE);
            }
        }//end of if(Q779_NewModels_TM.size () > 0)
    }

    /********************************************************************************
    *
    *
    * @param fmtType boolean
    * @param outSB StringBuffer
    */
    private void retrieveQ799NewFeatures(boolean fmtType, StringBuffer outSB)
    {
        if(q779_NewFeatures_TM.size () > 0)
        {
            //Group by machine type
            Set tmSet;
            Iterator tmItr;
            TreeMap [] arrayTM;
            int index;
            Iterator tsItr;
            TreeSet ts = new TreeSet();
            tmSet = q779_NewFeatures_TM.keySet();
            tmItr = tmSet.iterator();
            while(tmItr.hasNext())
            {
                String key = (String) tmItr.next();
                ts.add(parseString(key, 2, "<:>"));
            }

            arrayTM = new TreeMap[ts.size()];
            for(int i = 0; i < ts.size(); i++)
            {
                arrayTM[i] = new TreeMap();
            }

            index = 0;
            tsItr = ts.iterator();
            while(tsItr.hasNext())
            {
                String machineType = (String) tsItr.next();

                tmItr = tmSet.iterator();
                while(tmItr.hasNext())
                {
                    String key = (String) tmItr.next();
                    String mt = parseString(key, 2, "<:>");

                    if(machineType.equals(mt))
                    {
                        arrayTM[index].put(key, q779_NewFeatures_TM.get(key));
                    }
                }//end of while(tmItr.hasNext())
                index++;
            }//end while(tsItr.hasNext())

            for(int i = 0; i < ts.size(); i++)
            {
                retrieveQ799NewFeatures(fmtType, outSB, arrayTM[i]);

            }

            for(int i = 0; i < ts.size(); i++)
            {
                arrayTM[i].clear();
                arrayTM[i] = null;
            }
            ts.clear();
            ts = null;
        }//end of if(Q779_NewFeatures_TM.size () > 0)
    }

    /********************************************************************************
    *
    *
    * @param fmtType boolean
    * @param outSB StringBuffer
    * @param tm TreeMap
    */
    private void retrieveQ799NewFeatures(boolean fmtType, StringBuffer outSB, TreeMap tm)
    {
        if(tm.size () > 0)
        {
            String machineType;
            String modelList = "";
            String headerString = "";

            String prevGeo = "";
            String currentGeo = "";
            int nLines;
            Set tmSet;
            Iterator tmItr;
            TreeSet ts;
            Iterator tsItr;
            String [] headerStringLines;
            TreeMap aTM = new TreeMap();

            machineType = parseString((String) tm.firstKey(), 2, "<:>");
            ts = new TreeSet();
            tmSet = tm.keySet();
            tmItr = tmSet.iterator();
            while(tmItr.hasNext())
            {
                String key = (String) tmItr.next();
                ts.add(parseString(key, 3, "<:>"));
            }
            tsItr = ts.iterator();
            while(tsItr.hasNext())
            {
                modelList = modelList + (String) tsItr.next() + ", ";
            }
            if(modelList.length() > 1)
            {
                modelList = modelList.substring(0, modelList.length() - 2);
            }

            headerString = "Machine Type: " + machineType + "  Models: " + modelList;

            outSB.append(".RH ON" + NEWLINE);
            outSB.append(".fo off" + NEWLINE);
            outSB.append(".in 2" + NEWLINE);
            outSB.append("                                                         IBM" + NEWLINE);
            outSB.append("         SUPP.                                           List    PKG." + NEWLINE);
            outSB.append("Feature  CAT.     Description                            Price   QTY." + NEWLINE);
            outSB.append("-------  -------  -------------------------------------  -----   ----" + NEWLINE);
            outSB.append(".fo on" + NEWLINE);
            outSB.append(".RH OFF" + NEWLINE);
            outSB.append(".pa" + NEWLINE);
            outSB.append(":xmp." + NEWLINE);
            outSB.append(".kp off" + NEWLINE);

            headerStringLines = extractStringLines(headerString, I_69);
            for(int i = 0; i < headerStringLines.length; i++)
            {
                outSB.append(headerStringLines[i] + NEWLINE);
            }

            tmSet = tm.keySet();
            tmItr = tmSet.iterator();

            while(tmItr.hasNext())
            {
                String key1 = (String) tmItr.next();
                String key2 = parseString(key1, 1, "<:>") + "<:>" + parseString(key1, 4, "<:>") + "<:>" + parseString(key1, 5, "<:>") + "<:>" + parseString(key1, 6, "<:>");

                aTM.put(key2, tm.get(key1));
            }

            tmSet = aTM.keySet();
            tmItr = tmSet.iterator();

            while(tmItr.hasNext())
            {
                String [] stringLines;
                String key = (String) tmItr.next();
                currentGeo = (String) aTM.get(key);
                stringLines = extractStringLines(parseString(key, 3, "<:>"), I_37);
                nLines = 0;

                setGeoTags2(prevGeo, currentGeo, fmtType, outSB);

                do
                {
                    if(0 == nLines)
                    {
                        outSB.append(parseString(key, 1, "<:>"));
                        outSB.append("     ");
                        outSB.append(parseString(key, 2, "<:>"));
                        outSB.append("  ");
                        outSB.append(setString(stringLines[nLines], I_37));
                        outSB.append("  ");
                        outSB.append(parseString(key, 4, "<:>"));
                        outSB.append("  ");
                        outSB.append("1");
                        outSB.append(NEWLINE);
                        nLines++;
                    }//end of if(0 == nLines)
                    else
                    {
                        outSB.append(setString(" ", I_18));
                        outSB.append(setString(stringLines[nLines], I_37));
                        outSB.append(NEWLINE);
                        nLines++;
                    }
                }while(nLines < stringLines.length);

                prevGeo = currentGeo;
            }//end of while(tmItr.hasNext())
            if(!currentGeo.equals("WW"))
            {
                bldEndGeoTags(currentGeo, fmtType, outSB);
            }
            if(fmtType == GMLFORMAT)
            {
                outSB.append(":exmp." + NEWLINE);
                outSB.append(".RH CANCEL" + NEWLINE + NEWLINE);
            }

            aTM.clear();
            aTM = null;
        }//end of if(tm.size () > 0)
    }

    /********************************************************************************
    *
    *
    * @param mt String
    * @param fmtType boolean
    * @param outSB StringBuffer
    */
    private void retrieveChargesFeaturesFormat3_MTM(String mt, boolean fmtType, StringBuffer outSB)
    {
        TreeMap newFC_TM = new TreeMap();
        TreeMap existingFC_TM = new TreeMap();
        TreeSet modelTS = new TreeSet();

        if(charges_NewModels_NewFC_TM.size() > 0)
        {
            Set tmSet = charges_NewModels_NewFC_TM.keySet();
            Iterator tmItr = tmSet.iterator();
            while(tmItr.hasNext())
            {
                String key = (String) tmItr.next();
                if(mt.equals(parseString(key, 1, "<:>")))
                {
                    String model = parseString(key, 3, "<:>");
                    String mktgName = parseString(key, 10, "<:>");
                    modelTS.add(model + "<:>" + mktgName);
                    newFC_TM.put(key, charges_NewModels_NewFC_TM.get(key));
                }
            }//end of while(tmItr.hasNext())
        }//end of if(charges_NewModels_NewFC_TM.size() > 0)
        retrieveChargesFeaturesFormat3_MTM(mt, modelTS, NEWFC, newFC_TM, fmtType, outSB);
        modelTS.clear();

        if(charges_NewModels_ExistingFC_TM.size() > 0)
        {
            Set tmSet = charges_NewModels_ExistingFC_TM.keySet();
            Iterator tmItr = tmSet.iterator();
            while(tmItr.hasNext())
            {
                String key = (String) tmItr.next();

                if(mt.equals(parseString(key, 1, "<:>")))
                {
                    String model = parseString(key, 3, "<:>");
                    modelTS.add(model);
                    existingFC_TM.put(key, charges_NewModels_ExistingFC_TM.get(key));
                }
            }//end of while(tmItr.hasNext())
        }//end of if(charges_NewModels_ExistingFC_TM.size() > 0)

        retrieveChargesFeaturesFormat3_MTM(mt, modelTS, EXISTINGFC, existingFC_TM, fmtType, outSB);

        modelTS.clear();
        modelTS = null;
        newFC_TM.clear();
        newFC_TM = null;
        existingFC_TM.clear();
        existingFC_TM = null;
    }

    /********************************************************************************
    *
    *
    * @param machineType String
    * @param mTS TreeSet
    * @param fType int
    * @param tm TreeMap
    * @param fmtType boolean
    * @param outSB StringBuffer
    */
    private void retrieveChargesFeaturesFormat3_MTM(String machineType, TreeSet mTS, int fType, TreeMap tm, boolean fmtType, StringBuffer outSB)
    {
        if(tm.size() > 0)
        {
            String[] arrayStr;
            String headerString = "";

            switch(fType)
            {
            case NEWFC:
                if(brand.equals(PSERIES))
                {
                    headerString = "of the IBM RS/6000 or pSeries " + machineType + " machine type:";
                }
                else if(brand.equals(XSERIES))
                {
                    headerString = "of the IBM xSeries " + machineType + " machine type:";
                }
                //else if(brand.equals(ZSERIES))
                //{
                //   headerString = "of the IBM S/390 or zSeries " + machineType + " machine type:";
                //}
                else if(brand.equals(TS))
                {
                    headerString = "of the Total Storage " + machineType + " machine type:";
                }

                headerString = ":p.The following are newly announced features on the specified models " + headerString;
                arrayStr = extractStringLines(headerString, I_70);
                for(int i = 0; i < arrayStr.length; i++)
                {
                    outSB.append(arrayStr[i] + NEWLINE);
                }
                break;

            case EXISTINGFC:
                headerString = ":p.The following are features already announced for the " + machineType + " machine type:";
                arrayStr = extractStringLines(headerString, I_70);
                for(int i = 0; i < arrayStr.length; i++)
                {
                    outSB.append(arrayStr[i] + NEWLINE);
                }
                break;

            default:
                break;
            }//end of switch(fType)

            outSB.append(".RH ON" + NEWLINE);
            //outSB.append(":xmp." + NEWLINE);
            outSB.append(".fo off" + NEWLINE);
            outSB.append(".in 2" + NEWLINE);
            //outSB.append(".kp off\n");
            outSB.append("                                                     Initial/" + NEWLINE);
            outSB.append("                                                     MES/" + NEWLINE);
            outSB.append("Description                  Model  Feature Purchase Both/        RP" + NEWLINE);
            outSB.append("                             Number Numbers Price    Support  CSU MES" + NEWLINE);
            outSB.append("---------------------------- ------ ------- -------- -------- --- ---" + NEWLINE);
            //outSB.append(":exmp." + NEWLINE);
            outSB.append(".fo on" + NEWLINE);
            outSB.append(".RH OFF" + NEWLINE);

            if(mTS.size() > 0)
            {
                Iterator tsItr = mTS.iterator();

                outSB.append(".pa" + NEWLINE);
                outSB.append(":xmp." + NEWLINE);
                outSB.append(".kp off" + NEWLINE);

                while(tsItr.hasNext())
                {
                    String key = (String) tsItr.next();
                    String model = parseString(key, 1, "<:>");
                    String [] stringLines = extractStringLines(parseString(key, 2, "<:>"), I_51);
                    for(int i = 0; i < stringLines.length; i++)
                    {
                        outSB.append(stringLines[i]);
                        outSB.append(NEWLINE);
                    }
                    outSB.append(setString("", I_30));
                    outSB.append(model);
                    outSB.append(setString("", I_12));
                    outSB.append("XXXX.XX");
                    outSB.append(setString("", I_10));
                    outSB.append(productNumber_NewModels_HT.get(model));
                    outSB.append(NEWLINE);
                }
                //outSB.append(":exmp." + NEWLINE);
                //outSB.append(".pa\n");
            }//end of if(aTS1.size() > 0)

            retrieveChargesFeaturesFormat3_MTM(machineType, tm, fmtType, outSB);
        }//end of if(tm.size() > 0))
    }

    /********************************************************************************
    *
    *
    * @param mt String
    * @param tm TreeMap
    * @param fmtType boolean
    * @param outSB StringBuffer
    */
    private void retrieveChargesFeaturesFormat3_MTM(String mt, TreeMap tm, boolean fmtType, StringBuffer outSB)
    {
        if(tm.size() > 0)
        {
            String prevGeo = "";
            String currentGeo = "";
            String prevFeature = "";
            String currentFeature = "";
            String prevMT = "";
            String currentMT ="";

            String [] stringLines = null;
            boolean printHeader = false;
            Set tmSet = tm.keySet();
            Iterator tmItr = tmSet.iterator();
            //outSB.append(":xmp." + NEWLINE); //According to OIM3.0b specs: remove :xmp. and :exmp. pair appeared around each feature
            while(tmItr.hasNext())
            {
                String key = (String) tmItr.next();
                currentGeo = (String) tm.get(key);
                currentFeature = parseString(key, 2, "<:>");
                currentMT = mt;
                if(!currentFeature.equals(prevFeature) || !currentMT.equals(prevMT))
                {
                    stringLines = extractStringLines(parseString(key, 4, "<:>"), I_51);
                    printHeader = true;
                }
                setGeoTags3(prevGeo, currentGeo, prevMT, currentMT, prevFeature, currentFeature, fmtType, outSB);

                if(parseString(key, 8, "<:>").length() > 0)
                {
                    if(!currentFeature.equals(prevFeature))
                    {
                        String [] arrayStr1 = getStringTokens(parseString(key, 8, "<:>"), NEWLINE);
                        for(int i = 0; i < arrayStr1.length; i++)
                        {
                            if(arrayStr1[i].length() > I_58)
                            {
                                String [] arrayStr2 = extractStringLines(arrayStr1[i], I_58);
                                for(int j = 0; j < arrayStr2.length; j++)
                                {
                                    outSB.append(":hp2." + arrayStr2[j] + ":ehp2." + NEWLINE);
                                }
                            }
                            else
                            {
                                outSB.append(":hp2." + arrayStr1[i] + ":ehp2." + NEWLINE);
                            }
                        }
                    }//end of if(!currentFeature.equals(prevFeature))
                }

                if(printHeader)
                {
                    for(int i = 0; i < stringLines.length; i++)
                    {
                        outSB.append(stringLines[i] + NEWLINE);
                    }
                    printHeader = false;
                }

                outSB.append(setString("", I_30));
                outSB.append(parseString(key, 3, "<:>"));
                outSB.append("      ");

                if(!currentFeature.equals(prevFeature))
                {
                    outSB.append(parseString(key, 2, "<:>"));
                }
                else
                {
                    outSB.append("    ");
                }
                outSB.append("  ");

                if(!currentFeature.equals(prevFeature))
                {
                    outSB.append(parseString(key, 9, "<:>"));
                }
                else
                {
                    outSB.append("       ");
                }
                outSB.append(" ");
                outSB.append(parseString(key, 5, "<:>"));
                outSB.append("  ");
                outSB.append(parseString(key, 6, "<:>"));
                outSB.append(" ");
                outSB.append(parseString(key, 7, "<:>"));
                outSB.append(NEWLINE);

                prevGeo = currentGeo;
                prevFeature = currentFeature;
                prevMT = currentMT;
            }//end of while(tmItr.hasNext())
            if(!currentGeo.equals("WW"))
            {
                bldEndGeoTags(currentGeo, fmtType, outSB);
            }

            outSB.append(":exmp." + NEWLINE);
            outSB.append(".RH CANCEL" + NEWLINE + NEWLINE);
        }//end of if(productNumber_Features_TM.size() > 0)
    }
}
