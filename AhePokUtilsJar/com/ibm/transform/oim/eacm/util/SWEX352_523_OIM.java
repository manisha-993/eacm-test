// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
//[]--------------------------------------------------------------------------[]
//|    Application Name: e-announce                                            |
//|           File Name: SWEX352_523_OIM.java                                  |
//|----------------------------------------------------------------------------|
//|          Programmer: Anhtuan Nguyen                                        |
//|        Date written: September 18, 2004                                    |
//|         Environment: Operating System: Windows 2000                        |
//|                              Compiler: IBM JDK 1.4                         |
//|----------------------------------------------------------------------------|
//|  Module Description: SW Extract for Q352 and Q523                          |
//|----------------------------------------------------------------------------|
//|        Restrictions: None                                                  |
//|        Dependencies: None                                                  |
//|  NLS Considerations: None                                                  |
//|----------------------------------------------------------------------------|
//|      Change History:                                                       |
//|      Date            Programmer      Description/Comments                  |
//[]--------------------------------------------------------------------------[]
// $Log: SWEX352_523_OIM.java,v $
// Revision 1.9  2008/01/22 18:28:49  wendy
// Cleanup RSA warnings
//
// Revision 1.8  2007/06/27 16:46:49  frosam
// Due to the jdk update, a .toString method was needed.
//
// Revision 1.7  2006/05/12 07:57:17  anhtuan
// Jtest.
//
// Revision 1.6  2006/05/12 07:20:44  anhtuan
// Fix.
//
// Revision 1.5  2006/05/11 16:28:20  anhtuan
// New specs for CR1019052831.
//
// Revision 1.4  2006/05/04 14:08:47  anhtuan
// CR1019052831: Support ValueMetric for no charge section.
//
// Revision 1.3  2006/01/26 15:36:26  anhtuan
// AHE copyright.
//
// Revision 1.2  2005/10/04 15:50:45  anhtuan
// Fix Jtest. Jtest does not allow multiple returns from a method.
//
// Revision 1.1  2005/09/14 03:55:22  anhtuan
// Init OIM3.0b
//
// Revision 1.16  2005/07/13 14:47:10  couto
// Closed the <br> tags.
//
// Revision 1.15  2005/03/10 19:02:28  anhtuan
// CR0216056011.
//
// Revision 1.14  2004/11/23 18:14:21  anhtuan
// Don't check for SWPRODSTRUCT(s) link to Management Group with classification = SW-UpgradeFrom.
//
// Revision 1.13  2004/11/23 01:43:04  anhtuan
// Update listHeader.
//
// Revision 1.12  2004/11/15 15:38:33  anhtuan
// TIR USRO-R-GWIT-66LMUV.
//
// Revision 1.11  2004/10/24 00:23:53  anhtuan
// Fix TIR USRO-R-SDHA-65ZMVK(UVATS Server:d27dbl05/27/A/IBM, File Path: u_dir\ULC_211a.nsf)
//
// Revision 1.10  2004/10/20 19:21:15  anhtuan
// Log more messages.
//
// Revision 1.9  2004/10/12 15:27:53  anhtuan
// Remove some log.
//
// Revision 1.8  2004/10/11 16:30:18  anhtuan
// Get correct MTM of "from" model. Fix displayMaxUsers().
//
// Revision 1.7  2004/10/07 17:53:54  anhtuan
// Fix getUpLinkEntityItem(), getDownLinkEntityItem().
//
// Revision 1.6  2004/10/05 02:29:00  anhtuan
// As of 9/24/2004 attribute CHARGEFEE(SWPRODSTRUCT) is no longer exist. It is replaced by PRICEDFEATURE(SWFEATURE)
//
// Revision 1.5  2004/10/04 20:01:51  anhtuan
// displayNewUpgradeBillingCodeOther()
//
// Revision 1.4  2004/10/04 04:29:33  anhtuan
// displayNewUpgradeBillingCodePBOTC()
//
// Revision 1.3  2004/10/04 04:25:59  anhtuan
// populateQ352A2AppBaseTo()
// populateQ352A2AppOptFeaBaseTo()
//
// Revision 1.2  2004/09/30 15:02:55  anhtuan
// Put in cleanUp().
//
// Revision 1.1  2004/09/30 14:22:31  anhtuan
// Intial version. CR0623046010
//


/**********************************************************************************
 * This class is used to extract and display SW information for e-announce v1.1.1.
 *
 * 1. Q352 report - List of Order Billing Codes
 *    A. List of New Charge Features
 *       1) Initial Billing Code
 *       2) New Upgrade Billing Code
 *       3) New Group-to-Group PBOTC Billing Code
 *       4) Other New Billing Code
 *    B. Maximum users for Offering
 *    C. List of New No-Charge Features
 *
 * 2. Q523 report- List of Charge Feature Billing Codes
 *    1) Initial Billing Code
 *    2) New Upgrade Billing Code
 *    3) New Group-to-Group PBOTC Billing Code
 *    4) Other New Billing Code
 */


package com.ibm.transform.oim.eacm.util;

import COM.ibm.eannounce.objects.*;
import java.util.*;
import java.text.*;

/**********************************************************************************
* SWEX352_523_OIM class
*
*
*/
public class SWEX352_523_OIM
{
    /***************
    * Version
    */
    public static final String VERSION = "$Revision: 1.9 $";
    private EntityList list;
    private GeneralAreaList gal;

    private StringBuffer sb;
    private StringBuffer sbQ523;
    private StringBuffer debugBuffer;
    private boolean debug;

    private Vector SWProdStructVector;
    private Vector SWProdStructVectorNoChargeFee;
    private Vector SWProdStructV;

    private Hashtable usGeoHT;
    private Hashtable apGeoHT;
    private Hashtable laGeoHT;
    private Hashtable canGeoHT;
    private Hashtable emeaGeoHT;

    private Hashtable geoHT;
    private TreeMap SWAppBase;

    private Vector Q352A1AppBase;
    private Vector Q352A1AppOptFeaBase1;
    private Vector Q352A1AppOptFeaBase2;

    private Vector Q352A2AppBaseTo;
    private Vector Q352A2AppOptFeaBase1To;
    private Vector Q352A2AppOptFeaBase2To;
    private Vector Q352A2AppBaseFrom;
    private Vector Q352A2AppOptFeaBaseFrom;
    private Hashtable fromMTMHT;

    private Vector Q352A3AppBase;
    private Vector Q352A3AppOptFeaBase1;
    private Vector Q352A3AppOptFeaBase2;

    private Vector Q352A4AppBaseChargeFee;
    private Vector Q352A4AppOptFeaBase1ChargeFee;
    private Vector Q352A4AppOptFeaBase2ChargeFee;
    private Vector Q352A4AppBaseNoChargeFee;
    private Vector Q352A4AppOptFeaBase1NoChargeFee;
    private Vector Q352A4AppOptFeaBase2NoChargeFee;
    private TreeMap listHeader;

    private Vector Q352B1V1;
    private Vector Q352B1V2;
    private Vector Q352B1V3;
    private Vector Q352B1V4;

    private String machType = "";
    private String model = "";
    private String baseModelKey = "";
    private EntityItem baseModel = null;

    private String annNumber;

    private boolean initialOrder = false;
    private boolean upgradeOrder = false;
    private boolean gtgUpgrade = false;
    private boolean otherCharge = false;
    private boolean noCharge = false;

    private boolean showInitialOrderHeader = false;
    private boolean showUpgradeOrderHeader = false;
    private boolean showG2GUpgradesHeader = false;

    private static final int I_2 = 2;
    private static final int I_3 = 3;
    private static final int I_4 = 4;
    private static final int I_6 = 6;
    private static final int I_7 = 7;
    private static final int I_8 = 8;
    private static final int I_10 = 10;
    private static final int I_11 = 11;
    private static final int I_12 = 12;
    private static final int I_13 = 13;
    private static final int I_14 = 14;
    private static final int I_16 = 16;
    private static final int I_17 = 17;
    private static final int I_18 = 18;
    private static final int I_19 = 19;
    private static final int I_20 = 20;
    private static final int I_22 = 22;
    private static final int I_23 = 23;
    private static final int I_24 = 24;
    private static final int I_28 = 28;
    private static final int I_31 = 31;
    private static final int I_35 = 35;
    private static final int I_39 = 39;
    private static final int I_40 = 40;
    private static final int I_47 = 47;
    private static final int I_49 = 49;
    private static final int I_55 = 55;
    private static final int I_59 = 59;
    private static final int I_63 = 63;
    private static final int I_65 = 65;
    private static final int I_67 = 67;
    private static final int I_128 = 128;

    private StringBuffer tmpSB = new StringBuffer();
    private static final char[] FOOL_JTEST = {'\n'};
    static final String NEWLINE = new String(FOOL_JTEST);

    /********************************************************************************
    * Constructor
    *
    * @param aList Entity List
    * @param aGal GeneralAreaList
    * @param annN String
    */
    public SWEX352_523_OIM(EntityList aList, GeneralAreaList aGal, String annN)
    {
        list = aList;
        gal = aGal;
        annNumber = annN;

        sb = new StringBuffer();
        sbQ523 = new StringBuffer();
        debugBuffer = new StringBuffer();
        debugBuffer.append("<!-- " + NEWLINE);
        debug = false;

        SWProdStructVector = new Vector();
        SWProdStructVectorNoChargeFee = new Vector();
        SWProdStructV = new Vector();

        usGeoHT = new Hashtable();
        apGeoHT = new Hashtable();
        laGeoHT = new Hashtable();
        canGeoHT = new Hashtable();
        emeaGeoHT = new Hashtable();

        geoHT = new Hashtable();
        SWAppBase = new TreeMap();

        Q352A1AppBase = new Vector(1);
        Q352A1AppOptFeaBase1 = new Vector(1);
        Q352A1AppOptFeaBase2 = new Vector(1);

        Q352A2AppBaseTo = new Vector(1);
        Q352A2AppOptFeaBase1To = new Vector(1);
        Q352A2AppOptFeaBase2To = new Vector(1);
        Q352A2AppBaseFrom = new Vector(1);
        Q352A2AppOptFeaBaseFrom = new Vector(1);
        fromMTMHT = new Hashtable();

        Q352A3AppBase = new Vector(1);
        Q352A3AppOptFeaBase1 = new Vector(1);
        Q352A3AppOptFeaBase2 = new Vector(1);

        Q352A4AppBaseChargeFee = new Vector(1);
        Q352A4AppOptFeaBase1ChargeFee = new Vector(1);
        Q352A4AppOptFeaBase2ChargeFee = new Vector(1);
        Q352A4AppBaseNoChargeFee = new Vector(1);
        Q352A4AppOptFeaBase1NoChargeFee = new Vector(1);
        Q352A4AppOptFeaBase2NoChargeFee = new Vector(1);

        listHeader = new TreeMap();

        Q352B1V1 = new Vector(1);
        Q352B1V2 = new Vector(1);
        Q352B1V3 = new Vector(1);
        Q352B1V4 = new Vector(1);
    }

    /********************************************************************************
    * Initialization
    *
    */
    public void init()
    {
        EntityGroup eg;
        Vector availV;
        Hashtable tempHT;
        Vector availVector;
        log("In init()");
        //Populate hash table
        listHeader.put("Release Features", "315");
        listHeader.put("Asset Registration Features", "301");
        listHeader.put("Supply Features", "317");
        listHeader.put("Distribution Media Features", "304");
        listHeader.put("Publication Features", "313");
        listHeader.put("Optional Media Features", "309");
        listHeader.put("Special Delivery Features", "303");
        listHeader.put("Manufacturing Features", "308");
        listHeader.put("Language Features", "306");
        listHeader.put("Special Discount Features", "302");

        //For entity type = ANNOUNCEMENT we need to get parent entity group according to West Coast
        eg = list.getParentEntityGroup();

        availV = PokUtils.getAllLinkedEntities(eg, "ANNAVAILA", "AVAIL");
        tempHT = new Hashtable();
        tempHT.put("AVAILTYPE", "146"); //Planned Availability
        availVector = PokUtils.getEntitiesWithMatchedAttr(availV, tempHT);
        tempHT.clear();
        tempHT.put("AVAILTYPE", "143"); //Fisrt Order
        availVector.addAll(PokUtils.getEntitiesWithMatchedAttr(availV, tempHT));
        tempHT.clear();
        tempHT = null;
        availV.clear();
        availV = null;

        log("availVector.size() = " + availVector.size());

        getAllSWProdStructEntities(availVector, "SWPRODSTRUCTAVAIL", "SWPRODSTRUCT");

        log("SWProdStructVector.size() = " + SWProdStructVector.size());
        log("SWProdStructVectorNoChargeFee.size() = " + SWProdStructVectorNoChargeFee.size());
        log("SWProdStructV.size() = " + SWProdStructV.size());

        populateQ352A1AppBase();
        populateQ352A1AppOptFeaBase();

        //Begin new CR050504359
        populateQ352A1SubscriptionBase();
        populateQ352A1SubscrFeatureBase();
        populateQ352A1MaintenanceBase();
        populateQ352A1MaintFeatureBase();
        populateQ352A1SupportBase();
        populateQ352A1SupportFeatureBase();
        //End new CR050504359

        populateQ352A2AppBaseTo();
        populateQ352A2AppOptFeaBaseTo();

        populateQ352A3AppBase();
        populateQ352A3AppOptFeaBase();

        //Begin new CR050504359
        populateQ352A3SubscriptionBase();
        populateQ352A3SubscrFeatureBase();
        populateQ352A3MaintenanceBase();
        populateQ352A3MaintFeatureBase();
        populateQ352A3SupportBase();
        populateQ352A3SupportFeatureBase();
        //End new CR050504359

        populateQ352A4();

        populateQ352B1();
    }

    /********************************************************************************
    * init_withDebug()
    *
    */
    public void init_withDebug()
    {
        debug = true;
        init();
    }

    private void getAllSWProdStructEntities(Vector srcVct, String linkType, String destType)
    {
        Iterator srcItr = srcVct.iterator();
        while (srcItr.hasNext())
        {
            EntityItem entityItem = (EntityItem) srcItr.next();
            log("Avail = " + entityItem.toString());
            getSWProdStructEntities(entityItem, linkType, destType);
        }
    }

    private void getSWProdStructEntities(EntityItem entityItem, String linkType, String destType)
    {
        //As of 9/24/2004 attribute CHARGEFEE(SWPRODSTRUCT) is no longer exist. It is replaced by PRICEDFEATURE(SWFEATURE)
        Hashtable tempHT1;
        Hashtable tempHT2;

        //This method is based on PokUtils.getLinkedEntities()
        if (entityItem == null)
        {
            return;
        }

        tempHT1 = new Hashtable();
        tempHT1.put("PRICEDFEATURE", "100"); //Yes
        tempHT2 = new Hashtable();
        tempHT2.put("PRICEDFEATURE", "120"); //No

        for (int ui = 0; ui < entityItem.getUpLinkCount(); ui++)
        {
            EANEntity entityLink = entityItem.getUpLink(ui);
            if (entityLink.getEntityType().equals(linkType))
            {
                // check for destination entity as an uplink
                for (int i = 0; i < entityLink.getUpLinkCount(); i++)
                {
                    EANEntity entity = entityLink.getUpLink(i);
                    if (entity.getEntityType().equals(destType))
                    {
                        EntityItem swProdStructItem;
                        String key;
                        EANEntity swFeature;
                        log("entity.getEntityType() = " + entity.getEntityType());
                        log("entity = " + entity.toString());
                        //SWProdStruct entity with chargefee = yes
                        //*** As of today 9/18/04 Allowed values for CHARGEFEE are missing
                        //tempHT1.put("CHARGEFEE", "375");

                        //SWProdStruct entity with chargefee = no
                        //*** As of today 9/18/04 Allowed values for CHARGEFEE are missing
                        //tempHT2.put("CHARGEFEE", "376");

                        //String key = entity.getKey();
                        //if(isEntityWithMatchedAttr((EntityItem) entity, tempHT1))
                        //{
                        //   if(!SWProdStructVector.contains(entity))
                        //   {
                        //      SWProdStructVector.addElement(entity);
                        //   }
                        //updateGeoHT(entityItem, key);
                        //}
                        //if(isEntityWithMatchedAttr((EntityItem) entity, tempHT2))
                        //{
                        //   if(!SWProdStructVectorNoChargeFee.contains(entity))
                        //   {
                        //      SWProdStructVectorNoChargeFee.addElement(entity);
                        //   }
                        //updateGeoHT(entityItem, key);
                        //}
                        //if(!SWProdStructV.contains(entity))
                        //{
                        //   SWProdStructV.addElement(entity);
                        //}
                        //updateGeoHT(entityItem, key);

                        //tempHT1.clear();
                        //tempHT2.clear();

                        swProdStructItem = (EntityItem) entity;
                        key = swProdStructItem.getKey();
                        swFeature = getUpLinkEntityItem(swProdStructItem, "SWFEATURE");
                        if(null != swFeature)
                        {
                            //log("I am here 1");
                            if(isEntityWithMatchedAttr((EntityItem) swFeature, tempHT1))
                            {
                                //log("I am here 2");
                                if(!SWProdStructVector.contains(entity))
                                {
                                    SWProdStructVector.addElement(entity);
                                }
                                //updateGeoHT(entityItem, key);
                            }
                            if(isEntityWithMatchedAttr((EntityItem) swFeature, tempHT2))
                            {
                                //log("I am here 3");
                                if(!SWProdStructVectorNoChargeFee.contains(entity))
                                {
                                    SWProdStructVectorNoChargeFee.addElement(entity);
                                }
                                //updateGeoHT(entityItem, key);
                            }
                            if(!SWProdStructV.contains(entity))
                            {
                                SWProdStructV.addElement(entity);
                            }
                            updateGeoHT(entityItem, key);
                        }
                        else
                        {
                            //log("I am here 4");
                            if(!SWProdStructV.contains(entity))
                            {
                                SWProdStructV.addElement(entity);
                            }
                            updateGeoHT(entityItem, key);
                        }
                    }//end of if (entity.getEntityType().equals(destType))
                }//end of for (int i = 0; i < entityLink.getUpLinkCount(); i++)
            }//end of if (entityLink.getEntityType().equals(linkType))
        }//end for (int ui = 0; ui < entityItem.getUpLinkCount(); ui++)

        tempHT1.clear();
        tempHT1 = null;
        tempHT2.clear();
        tempHT2 = null;
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
    public boolean isEntityWithMatchedAttr(EntityItem ei, Hashtable attrValTbl)
    {
        //This method is derived from PokUtils.getEntitiesWithMatchedAttr()
        boolean allMatch=true;
        boolean rc;
        for(Enumeration e = attrValTbl.keys(); e.hasMoreElements();)
        {
            String attrCode = (String)e.nextElement();
            allMatch=allMatch&&
                entityMatchesAttr(ei, attrCode, attrValTbl.get(attrCode).toString());
        }
        if(allMatch)
        {
            rc = true;
        }
        else
        {
            rc = false;
        }

        return rc;
    }

    /********************************************************************************
    * Check to see if this entity's attribute has a value to match the passed in value.
    * Flags and text attributes are checked.  If it is a flag, flag codes are checked.
    * If multiple values are needed, separate them with a Delimiter ("|")
    *
    * @param entityItem EntityItem
    * @param attrCode    String Attribute code
    * @param valToMatch String Value(s) to be matched
    * @returns boolean
    */
    private boolean entityMatchesAttr(EntityItem entityItem, String attrCode, String valToMatch)
    {
        boolean found = false;
        //This method is derived from PokUtils.entityMatchesAttr()
        // Multi-flag values will be separated by |
        EANAttribute attr = entityItem.getAttribute(attrCode);
        if (attr != null)
        {
            if (attr instanceof EANFlagAttribute)
            {
                Vector vct = new Vector(1);
                String[] matchArray;
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
                matchArray = PokUtils.convertToArray(valToMatch);
                for (int i=0; i<matchArray.length; i++)
                {
                    if (vct.contains(matchArray[i]))
                    {
                        count++;
                    }
                }
                if (count==matchArray.length)
                {
                    found = true;
                }

            }
            else if (attr instanceof EANTextAttribute)
            {
                // values must match
                found = attr.get().toString().equals(valToMatch);
            }
        }

        return found;
    }

    /********************************************************************************
    * updateGeoHT
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

    private boolean checkRfaGeoUS(EntityItem ei)
    {
        //log("In checkRfaGeoUS(): " + ei.toString());
        boolean rc = true;
        String entityKey = ei.getKey();
        String value = (String) usGeoHT.get(entityKey);

        if(null == value)
        {
            if(gal.isRfaGeoUS(ei))
            {
                usGeoHT.put(entityKey, "Y");
                rc = true;
            }
            else
            {
                usGeoHT.put(entityKey, "F");
                rc = false;
            }
        }
        else if(value.equals("Y"))
        {
            rc = true;
        }
        else
        {
            rc = false;
        }

        return rc;
    }

    private boolean checkRfaGeoAP(EntityItem ei)
    {
        //log("In checkRfaGeoAP(): " + ei.toString());
        boolean rc = true;
        String entityKey = ei.getKey();
        String value = (String) apGeoHT.get(entityKey);

        if(null == value)
        {
            if(gal.isRfaGeoAP(ei))
            {
                apGeoHT.put(entityKey, "Y");
                rc = true;
            }
            else
            {
                apGeoHT.put(entityKey, "F");
                rc = false;
            }
        }
        else if(value.equals("Y"))
        {
            rc = true;
        }
        else
        {
            rc = false;
        }

        return rc;
    }

    private boolean checkRfaGeoLA(EntityItem ei)
    {
        //log("In checkRfaGeoLA(): " + ei.toString());
        boolean rc = true;
        String entityKey = ei.getKey();
        String value = (String) laGeoHT.get(entityKey);

        if(null == value)
        {
            if(gal.isRfaGeoLA(ei))
            {
                laGeoHT.put(entityKey, "Y");
                rc = true;
            }
            else
            {
                laGeoHT.put(entityKey, "F");
                rc = false;
            }
        }
        else if(value.equals("Y"))
        {
            rc = true;
        }
        else
        {
            rc = false;
        }

        return rc;
    }

    private boolean checkRfaGeoCAN(EntityItem ei)
    {
        //log("In checkRfaGeoCAN(): " + ei.toString());
        boolean rc = true;
        String entityKey = ei.getKey();
        String value = (String) canGeoHT.get(entityKey);

        if(null == value)
        {
            if(gal.isRfaGeoCAN(ei))
            {
                canGeoHT.put(entityKey, "Y");
                rc = true;
            }
            else
            {
                canGeoHT.put(entityKey, "F");
                rc = false;
            }
        }
        else if(value.equals("Y"))
        {
            rc = true;
        }
        else
        {
            rc = false;
        }

        return rc;
    }

    private boolean checkRfaGeoEMEA(EntityItem ei)
    {
        //log("In checkRfaGeoEMEA(): " + ei.toString());
        boolean rc = true;
        String entityKey = ei.getKey();
        String value = (String) emeaGeoHT.get(entityKey);

        if(null == value)
        {
            if(gal.isRfaGeoEMEA(ei))
            {
                emeaGeoHT.put(entityKey, "Y");
                rc = true;
            }
            else
            {
                emeaGeoHT.put(entityKey, "F");
                rc = false;
            }
        }
        else if(value.equals("Y"))
        {
            rc = true;
        }
        else
        {
            rc = false;
        }

        return rc;
    }

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

    private String getGeo(String key)
    {
        String geo = "";
        String result = "";

        boolean ap = false;
        boolean can = false;
        boolean emea = false;
        boolean la = false;
        boolean us = false;

        StringBuffer value = (StringBuffer) geoHT.get(key);
        tmpSB.append("<!-- " + value.toString() + " -->" + NEWLINE);
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
            result = "WW";
        }
        else
        {
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
                result = geo.substring(0, geo.length() - 2);
            }
            else
            {
                result = "No GEO Found";
            }
        }

        return result;
    }

    /********************************************************************************
    * populateQ352A1AppBase()
    *
    */
    private void populateQ352A1AppBase()
    {
        Iterator itr = SWProdStructVector.iterator();

        log("In populateQ352A1AppBase()");

        while(itr.hasNext())
        {
            Hashtable tempHT;
            Vector aVector;
            Vector bVector;
            Vector cVector;

            EntityItem swProdStructItem = (EntityItem) itr.next();
            log(swProdStructItem.toString());

            aVector = PokUtils.getAllLinkedEntities(swProdStructItem, "SWMODSTRUCT", "COFOOFMGMTGRP");
            log("aVector.size() = " + aVector.size());
            //101 = Software
            //144 = FeatureCode
            //181 = Billing
            //010 = N/A
            tempHT = new Hashtable();
            tempHT.put("COFOOFMGCAT", "101");
            tempHT.put("COFOOFMGSUBCAT", "144");
            tempHT.put("COFOOFMGGRP", "181");
            tempHT.put("COFOOFMGSUBGRP", "010");
            aVector = PokUtils.getEntitiesWithMatchedAttr(aVector, tempHT);
            log("aVector.size() = " + aVector.size());

            bVector = PokUtils.getAllLinkedEntities(aVector, "COFOWNSOOFOMG", "MODEL");
            log("bVector.size() = " + bVector.size());
            tempHT.clear();
            //101 = Software
            //127 = Application
            //151 = Initial
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "127");
            tempHT.put("COFGRP", "151");
            tempHT.put("COFSUBGRP", "010");
            bVector = PokUtils.getEntitiesWithMatchedAttr(bVector, tempHT);
            log("bVector.size() = " + bVector.size());

            cVector = PokUtils.getAllLinkedEntities(bVector, "MODELREL", "MODEL");
            log("cVector.size() = " + cVector.size());
            tempHT.clear();
            //101 = Software
            //127 = Application
            //150 = Base
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "127");
            tempHT.put("COFGRP", "150");
            tempHT.put("COFSUBGRP", "010");
            cVector = PokUtils.getEntitiesWithMatchedAttr(cVector, tempHT);
            log("cVector.size() = " + cVector.size());

            //Should be one and only one Model with classification SW-Application-Base
            //if(cVector.size() > 0)
            for(int i = 0; i < cVector.size(); i++)
            {
                String key;
                String modelStr;
                //EntityItem entityItem = (EntityItem) cVector.get(0);
                EntityItem entityItem = (EntityItem) cVector.get(i);

                Q352A1AppBase.add(new SWEntityItem_OIM(entityItem, null, swProdStructItem, null));
                key = PokUtils.getAttributeFlagValue(entityItem, "MACHTYPEATR");
                if(null == key)
                {
                    key = "";
                }

                key = key + "    ";
                key = key.substring(0, 4);
                modelStr = PokUtils.getAttributeValue(entityItem, "MODELATR", "", "000");
                modelStr = modelStr + "   ";
                modelStr = modelStr.substring(0, 3);
                key = "A" + key + modelStr + entityItem.getKey();
                if(!SWAppBase.containsKey(key))
                {
                    SWAppBase.put(key, entityItem);
                }
            }
        }//end of while(itr.hasNext())
    }

    /********************************************************************************
    * populateQ352A1AppOptFeaBase()
    *
    */
    private void populateQ352A1AppOptFeaBase()
    {
        Vector tempVector = new Vector(1);
        Iterator itr = SWProdStructVector.iterator();

        log("In populateQ352A1AppOptFeaBase()");

        while(itr.hasNext())
        {
            Hashtable tempHT;
            Vector aVector;
            Vector bVector;
            Vector cVector;

            EntityItem swProdStructItem = (EntityItem) itr.next();
            log(swProdStructItem.toString());

            aVector = PokUtils.getAllLinkedEntities(swProdStructItem, "SWMODSTRUCT", "COFOOFMGMTGRP");
            log("aVector.size() = " + aVector.size());
            //101 = Software
            //144 = FeatureCode
            //181 = Billing
            //010 = N/A
            tempHT = new Hashtable();
            tempHT.put("COFOOFMGCAT", "101");
            tempHT.put("COFOOFMGSUBCAT", "144");
            tempHT.put("COFOOFMGGRP", "181");
            tempHT.put("COFOOFMGSUBGRP", "010");
            aVector = PokUtils.getEntitiesWithMatchedAttr(aVector, tempHT);
            log("aVector.size() = " + aVector.size());

            bVector = PokUtils.getAllLinkedEntities(aVector, "COFOWNSOOFOMG", "MODEL");
            log("bVector.size() = " + bVector.size());
            tempHT.clear();
            //101 = Software
            //130 = OptionalFeature
            //151 = Initial
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "130");
            tempHT.put("COFGRP", "151");
            tempHT.put("COFSUBGRP", "010");
            bVector = PokUtils.getEntitiesWithMatchedAttr(bVector, tempHT);
            log("bVector.size() = " + bVector.size());

            cVector = PokUtils.getAllLinkedEntities(bVector, "MODELREL", "MODEL");
            log("cVector.size() = " + cVector.size());
            tempHT.clear();
            //101 = Software
            //130 = OptionalFeature
            //150 = Base
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "130");
            tempHT.put("COFGRP", "150");
            tempHT.put("COFSUBGRP", "010");
            cVector = PokUtils.getEntitiesWithMatchedAttr(cVector, tempHT);
            log("cVector.size() = " + cVector.size());

            //Should be one and only one Model with classification SW-OptionalFeature-Base
            //if(cVector.size() > 0)
            for(int i = 0; i < cVector.size(); i++)
            {
                //EntityItem entityItem = (EntityItem) cVector.get(0);
                EntityItem entityItem = (EntityItem) cVector.get(i);
                if(!tempVector.contains(entityItem))
                {
                    tempVector.add(entityItem);
                }

                Q352A1AppOptFeaBase2.add(new SWEntityItem_OIM(null, entityItem, swProdStructItem, null));
            }
        }//end of while(itr.hasNext())

        itr = tempVector.iterator();

        while(itr.hasNext())
        {
            Vector aVector;
            EntityItem modelItem = (EntityItem) itr.next();
            Hashtable tempHT = new Hashtable();;
            aVector = PokUtils.getAllLinkedEntities(modelItem, "MODELREL", "MODEL");
            log("aVector.size() = " + aVector.size());

            //101 = Software
            //127 = Application
            //150 = Base
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "127");
            tempHT.put("COFGRP", "150");
            tempHT.put("COFSUBGRP", "010");
            aVector = PokUtils.getEntitiesWithMatchedAttr(aVector, tempHT);
            log("aVector.size() = " + aVector.size());

            //Should be one and only one Model with classification SW-Application-Base
            //if(aVector.size() > 0)
            for(int i = 0; i < aVector.size(); i++)
            {
                String key;
                String modelStr;
                //EntityItem entityItem = (EntityItem) aVector.get(0);
                EntityItem entityItem = (EntityItem) aVector.get(i);

                Q352A1AppOptFeaBase1.add(new SWEntityItem_OIM(entityItem, modelItem, null, null));
                key = PokUtils.getAttributeFlagValue(entityItem, "MACHTYPEATR");
                if(null == key)
                {
                    key = "";
                }

                key = key + "    ";
                key = key.substring(0, 4);
                modelStr = PokUtils.getAttributeValue(entityItem, "MODELATR", "", "000");
                modelStr = modelStr + "   ";
                modelStr = modelStr.substring(0, 3);
                key = "A" + key + modelStr + entityItem.getKey();
                if(!SWAppBase.containsKey(key))
                {
                    SWAppBase.put(key, entityItem);
                }
            }
        }//end of while(itr.hasNext())
    }

    /********************************************************************************
    * populateQ352A1SubscriptionBase()
    *
    */
    private void populateQ352A1SubscriptionBase()
    {
        Iterator itr = SWProdStructVector.iterator();

        log("In populateQ352A1SubscriptionBase()");

        while(itr.hasNext())
        {
            Hashtable tempHT;
            Vector aVector;
            Vector bVector;
            Vector cVector;

            EntityItem swProdStructItem = (EntityItem) itr.next();
            log(swProdStructItem.toString());

            aVector = PokUtils.getAllLinkedEntities(swProdStructItem, "SWMODSTRUCT", "COFOOFMGMTGRP");
            log("aVector.size() = " + aVector.size());
            //101 = Software
            //144 = FeatureCode
            //181 = Billing
            //010 = N/A
            tempHT = new Hashtable();
            tempHT.put("COFOOFMGCAT", "101");
            tempHT.put("COFOOFMGSUBCAT", "144");
            tempHT.put("COFOOFMGGRP", "181");
            tempHT.put("COFOOFMGSUBGRP", "010");
            aVector = PokUtils.getEntitiesWithMatchedAttr(aVector, tempHT);
            log("aVector.size() = " + aVector.size());

            bVector = PokUtils.getAllLinkedEntities(aVector, "COFOWNSOOFOMG", "MODEL");
            log("bVector.size() = " + bVector.size());
            tempHT.clear();
            //101 = Software
            //133 = Subscription
            //151 = Initial
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "133");
            tempHT.put("COFGRP", "151");
            tempHT.put("COFSUBGRP", "010");
            bVector = PokUtils.getEntitiesWithMatchedAttr(bVector, tempHT);
            log("bVector.size() = " + bVector.size());

            cVector = PokUtils.getAllLinkedEntities(bVector, "MODELREL", "MODEL");
            log("cVector.size() = " + cVector.size());
            tempHT.clear();
            //101 = Software
            //133 = Subscription
            //150 = Base
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "133");
            tempHT.put("COFGRP", "150");
            tempHT.put("COFSUBGRP", "010");
            cVector = PokUtils.getEntitiesWithMatchedAttr(cVector, tempHT);
            log("cVector.size() = " + cVector.size());

            //Should be one and only one Model with classification SW-Application-Base
            //if(cVector.size() > 0)
            for(int i = 0; i < cVector.size(); i++)
            {
                String key;
                String modelStr;
                //EntityItem entityItem = (EntityItem) cVector.get(0);
                EntityItem entityItem = (EntityItem) cVector.get(i);

                Q352A1AppBase.add(new SWEntityItem_OIM(entityItem, null, swProdStructItem, null));
                key = PokUtils.getAttributeFlagValue(entityItem, "MACHTYPEATR");
                if(null == key)
                {
                    key = "";
                }

                key = key + "    ";
                key = key.substring(0, 4);
                modelStr = PokUtils.getAttributeValue(entityItem, "MODELATR", "", "000");
                modelStr = modelStr + "   ";
                modelStr = modelStr.substring(0, 3);
                key = "B" + key + modelStr + entityItem.getKey();
                if(!SWAppBase.containsKey(key))
                {
                    SWAppBase.put(key, entityItem);
                }
            }
        }//end of while(itr.hasNext())
    }

    /********************************************************************************
    * populateQ352A1SubscrFeatureBase()
    *
    */
    private void populateQ352A1SubscrFeatureBase()
    {
        Vector tempVector = new Vector(1);
        Iterator itr = SWProdStructVector.iterator();

        log("In populateQ352A1SubscrFeatureBase()");

        while(itr.hasNext())
        {
            Hashtable tempHT;
            Vector aVector;
            Vector bVector;
            Vector cVector;

            EntityItem swProdStructItem = (EntityItem) itr.next();
            log(swProdStructItem.toString());

            aVector = PokUtils.getAllLinkedEntities(swProdStructItem, "SWMODSTRUCT", "COFOOFMGMTGRP");
            log("aVector.size() = " + aVector.size());
            //101 = Software
            //144 = FeatureCode
            //181 = Billing
            //010 = N/A
            tempHT = new Hashtable();
            tempHT.put("COFOOFMGCAT", "101");
            tempHT.put("COFOOFMGSUBCAT", "144");
            tempHT.put("COFOOFMGGRP", "181");
            tempHT.put("COFOOFMGSUBGRP", "010");
            aVector = PokUtils.getEntitiesWithMatchedAttr(aVector, tempHT);
            log("aVector.size() = " + aVector.size());

            bVector = PokUtils.getAllLinkedEntities(aVector, "COFOWNSOOFOMG", "MODEL");
            log("bVector.size() = " + bVector.size());
            tempHT.clear();
            //101 = Software
            //132 = SubscrFeature
            //151 = Initial
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "132");
            tempHT.put("COFGRP", "151");
            tempHT.put("COFSUBGRP", "010");
            bVector = PokUtils.getEntitiesWithMatchedAttr(bVector, tempHT);
            log("bVector.size() = " + bVector.size());

            cVector = PokUtils.getAllLinkedEntities(bVector, "MODELREL", "MODEL");
            log("cVector.size() = " + cVector.size());
            tempHT.clear();
            //101 = Software
            //132 = SubscrFeature
            //150 = Base
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "132");
            tempHT.put("COFGRP", "150");
            tempHT.put("COFSUBGRP", "010");
            cVector = PokUtils.getEntitiesWithMatchedAttr(cVector, tempHT);
            log("cVector.size() = " + cVector.size());

            //Should be one and only one Model with classification SW-OptionalFeature-Base
            //if(cVector.size() > 0)
            for(int i = 0; i < cVector.size(); i++)
            {
                //EntityItem entityItem = (EntityItem) cVector.get(0);
                EntityItem entityItem = (EntityItem) cVector.get(i);
                if(!tempVector.contains(entityItem))
                {
                    tempVector.add(entityItem);
                }

                Q352A1AppOptFeaBase2.add(new SWEntityItem_OIM(null, entityItem, swProdStructItem, null));
            }
        }//end of while(itr.hasNext())

        itr = tempVector.iterator();

        while(itr.hasNext())
        {
            EntityItem modelItem = (EntityItem) itr.next();
            Hashtable tempHT = new Hashtable();
            Vector aVector = PokUtils.getAllLinkedEntities(modelItem, "MODELREL", "MODEL");
            log("aVector.size() = " + aVector.size());

            //101 = Software
            //133 = Subscription
            //150 = Base
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "133");
            tempHT.put("COFGRP", "150");
            tempHT.put("COFSUBGRP", "010");
            aVector = PokUtils.getEntitiesWithMatchedAttr(aVector, tempHT);
            log("aVector.size() = " + aVector.size());

            //Should be one and only one Model with classification SW-Application-Base
            //if(aVector.size() > 0)
            for(int i = 0; i < aVector.size(); i++)
            {
                String key;
                String modelStr;
                //EntityItem entityItem = (EntityItem) aVector.get(0);
                EntityItem entityItem = (EntityItem) aVector.get(i);

                Q352A1AppOptFeaBase1.add(new SWEntityItem_OIM(entityItem, modelItem, null, null));
                key = PokUtils.getAttributeFlagValue(entityItem, "MACHTYPEATR");
                if(null == key)
                {
                    key = "";
                }

                key = key + "    ";
                key = key.substring(0, 4);
                modelStr = PokUtils.getAttributeValue(entityItem, "MODELATR", "", "000");
                modelStr = modelStr + "   ";
                modelStr = modelStr.substring(0, 3);
                key = "B" + key + modelStr + entityItem.getKey();
                if(!SWAppBase.containsKey(key))
                {
                    SWAppBase.put(key, entityItem);
                }
            }
        }//end of while(itr.hasNext())
    }

    /********************************************************************************
    * populateQ352A1MaintenanceBase()
    *
    */
    private void populateQ352A1MaintenanceBase()
    {
        Iterator itr = SWProdStructVector.iterator();

        log("In populateQ352A1MaintenanceBase()");

        while(itr.hasNext())
        {
            Hashtable tempHT;
            Vector aVector;
            Vector bVector;
            Vector cVector;

            EntityItem swProdStructItem = (EntityItem) itr.next();
            log(swProdStructItem.toString());

            aVector = PokUtils.getAllLinkedEntities(swProdStructItem, "SWMODSTRUCT", "COFOOFMGMTGRP");
            log("aVector.size() = " + aVector.size());
            //101 = Software
            //144 = FeatureCode
            //181 = Billing
            //010 = N/A
            tempHT = new Hashtable();
            tempHT.put("COFOOFMGCAT", "101");
            tempHT.put("COFOOFMGSUBCAT", "144");
            tempHT.put("COFOOFMGGRP", "181");
            tempHT.put("COFOOFMGSUBGRP", "010");
            aVector = PokUtils.getEntitiesWithMatchedAttr(aVector, tempHT);
            log("aVector.size() = " + aVector.size());

            bVector = PokUtils.getAllLinkedEntities(aVector, "COFOWNSOOFOMG", "MODEL");
            log("bVector.size() = " + bVector.size());
            tempHT.clear();
            //101 = Software
            //128 = Maintenance
            //151 = Initial
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "128");
            tempHT.put("COFGRP", "151");
            tempHT.put("COFSUBGRP", "010");
            bVector = PokUtils.getEntitiesWithMatchedAttr(bVector, tempHT);
            log("bVector.size() = " + bVector.size());

            cVector = PokUtils.getAllLinkedEntities(bVector, "MODELREL", "MODEL");
            log("cVector.size() = " + cVector.size());
            tempHT.clear();
            //101 = Software
            //128 = Maintenance
            //150 = Base
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "128");
            tempHT.put("COFGRP", "150");
            tempHT.put("COFSUBGRP", "010");
            cVector = PokUtils.getEntitiesWithMatchedAttr(cVector, tempHT);
            log("cVector.size() = " + cVector.size());

            //Should be one and only one Model with classification SW-Application-Base
            //if(cVector.size() > 0)
            for(int i = 0; i < cVector.size(); i++)
            {
                String key;
                String modelStr;
                //EntityItem entityItem = (EntityItem) cVector.get(0);
                EntityItem entityItem = (EntityItem) cVector.get(i);

                Q352A1AppBase.add(new SWEntityItem_OIM(entityItem, null, swProdStructItem, null));
                key = PokUtils.getAttributeFlagValue(entityItem, "MACHTYPEATR");
                if(null == key)
                {
                    key = "";
                }

                key = key + "    ";
                key = key.substring(0, 4);
                modelStr = PokUtils.getAttributeValue(entityItem, "MODELATR", "", "000");
                modelStr = modelStr + "   ";
                modelStr = modelStr.substring(0, 3);
                key = "C" + key + modelStr + entityItem.getKey();
                if(!SWAppBase.containsKey(key))
                {
                    SWAppBase.put(key, entityItem);
                }
            }
        }//end of while(itr.hasNext())
    }

    /********************************************************************************
    * populateQ352A1MaintFeatureBase()
    *
    */
    private void populateQ352A1MaintFeatureBase()
    {
        Vector tempVector = new Vector(1);
        Iterator itr = SWProdStructVector.iterator();

        log("In populateQ352A1MaintFeatureBase()");

        while(itr.hasNext())
        {
            Hashtable tempHT;
            Vector aVector;
            Vector bVector;
            Vector cVector;

            EntityItem swProdStructItem = (EntityItem) itr.next();
            log(swProdStructItem.toString());

            aVector = PokUtils.getAllLinkedEntities(swProdStructItem, "SWMODSTRUCT", "COFOOFMGMTGRP");
            log("aVector.size() = " + aVector.size());
            //101 = Software
            //144 = FeatureCode
            //181 = Billing
            //010 = N/A
            tempHT = new Hashtable();
            tempHT.put("COFOOFMGCAT", "101");
            tempHT.put("COFOOFMGSUBCAT", "144");
            tempHT.put("COFOOFMGGRP", "181");
            tempHT.put("COFOOFMGSUBGRP", "010");
            aVector = PokUtils.getEntitiesWithMatchedAttr(aVector, tempHT);
            log("aVector.size() = " + aVector.size());

            bVector = PokUtils.getAllLinkedEntities(aVector, "COFOWNSOOFOMG", "MODEL");
            log("bVector.size() = " + bVector.size());
            tempHT.clear();
            //101 = Software
            //129 = MaintFeature
            //151 = Initial
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "129");
            tempHT.put("COFGRP", "151");
            tempHT.put("COFSUBGRP", "010");
            bVector = PokUtils.getEntitiesWithMatchedAttr(bVector, tempHT);
            log("bVector.size() = " + bVector.size());

            cVector = PokUtils.getAllLinkedEntities(bVector, "MODELREL", "MODEL");
            log("cVector.size() = " + cVector.size());
            tempHT.clear();
            //101 = Software
            //129 = MaintFeature
            //150 = Base
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "129");
            tempHT.put("COFGRP", "150");
            tempHT.put("COFSUBGRP", "010");
            cVector = PokUtils.getEntitiesWithMatchedAttr(cVector, tempHT);
            log("cVector.size() = " + cVector.size());

            //Should be one and only one Model with classification SW-OptionalFeature-Base
            //if(cVector.size() > 0)
            for(int i = 0; i < cVector.size(); i++)
            {
                //EntityItem entityItem = (EntityItem) cVector.get(0);
                EntityItem entityItem = (EntityItem) cVector.get(i);
                if(!tempVector.contains(entityItem))
                {
                    tempVector.add(entityItem);
                }

                Q352A1AppOptFeaBase2.add(new SWEntityItem_OIM(null, entityItem, swProdStructItem, null));
            }
        }//end of while(itr.hasNext())

        itr = tempVector.iterator();

        while(itr.hasNext())
        {
            Vector aVector;
            EntityItem modelItem = (EntityItem) itr.next();
            Hashtable tempHT = new Hashtable();;
            aVector = PokUtils.getAllLinkedEntities(modelItem, "MODELREL", "MODEL");
            log("aVector.size() = " + aVector.size());

            //101 = Software
            //128 = Maintenance
            //150 = Base
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "128");
            tempHT.put("COFGRP", "150");
            tempHT.put("COFSUBGRP", "010");
            aVector = PokUtils.getEntitiesWithMatchedAttr(aVector, tempHT);
            log("aVector.size() = " + aVector.size());

            //Should be one and only one Model with classification SW-Application-Base
            //if(aVector.size() > 0)
            for(int i = 0; i < aVector.size(); i++)
            {
                String key;
                String modelStr;
                //EntityItem entityItem = (EntityItem) aVector.get(0);
                EntityItem entityItem = (EntityItem) aVector.get(i);

                Q352A1AppOptFeaBase1.add(new SWEntityItem_OIM(entityItem, modelItem, null, null));
                key = PokUtils.getAttributeFlagValue(entityItem, "MACHTYPEATR");
                if(null == key)
                {
                    key = "";
                }

                key = key + "    ";
                key = key.substring(0, 4);
                modelStr = PokUtils.getAttributeValue(entityItem, "MODELATR", "", "000");
                modelStr = modelStr + "   ";
                modelStr = modelStr.substring(0, 3);
                key = "C" + key + modelStr + entityItem.getKey();
                if(!SWAppBase.containsKey(key))
                {
                    SWAppBase.put(key, entityItem);
                }
            }
        }//end of while(itr.hasNext())
    }

    /********************************************************************************
    * populateQ352A1SupportBase()
    *
    */
    private void populateQ352A1SupportBase()
    {
        Iterator itr = SWProdStructVector.iterator();

        log("In populateQ352A1SupportBase()");

        while(itr.hasNext())
        {
            Hashtable tempHT;
            Vector aVector;
            Vector bVector;
            Vector cVector;

            EntityItem swProdStructItem = (EntityItem) itr.next();
            log(swProdStructItem.toString());

            aVector = PokUtils.getAllLinkedEntities(swProdStructItem, "SWMODSTRUCT", "COFOOFMGMTGRP");
            log("aVector.size() = " + aVector.size());
            //101 = Software
            //144 = FeatureCode
            //181 = Billing
            //010 = N/A
            tempHT = new Hashtable();
            tempHT.put("COFOOFMGCAT", "101");
            tempHT.put("COFOOFMGSUBCAT", "144");
            tempHT.put("COFOOFMGGRP", "181");
            tempHT.put("COFOOFMGSUBGRP", "010");
            aVector = PokUtils.getEntitiesWithMatchedAttr(aVector, tempHT);
            log("aVector.size() = " + aVector.size());

            bVector = PokUtils.getAllLinkedEntities(aVector, "COFOWNSOOFOMG", "MODEL");
            log("bVector.size() = " + bVector.size());
            tempHT.clear();
            //101 = Software
            //134 = Support
            //151 = Initial
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "134");
            tempHT.put("COFGRP", "151");
            tempHT.put("COFSUBGRP", "010");
            bVector = PokUtils.getEntitiesWithMatchedAttr(bVector, tempHT);
            log("bVector.size() = " + bVector.size());

            cVector = PokUtils.getAllLinkedEntities(bVector, "MODELREL", "MODEL");
            log("cVector.size() = " + cVector.size());
            tempHT.clear();
            //101 = Software
            //134 = Suport
            //150 = Base
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "134");
            tempHT.put("COFGRP", "150");
            tempHT.put("COFSUBGRP", "010");
            cVector = PokUtils.getEntitiesWithMatchedAttr(cVector, tempHT);
            log("cVector.size() = " + cVector.size());

            //Should be one and only one Model with classification SW-Application-Base
            //if(cVector.size() > 0)
            for(int i = 0; i < cVector.size(); i++)
            {
                String key;
                String modelStr;
                //EntityItem entityItem = (EntityItem) cVector.get(0);
                EntityItem entityItem = (EntityItem) cVector.get(i);

                Q352A1AppBase.add(new SWEntityItem_OIM(entityItem, null, swProdStructItem, null));
                key = PokUtils.getAttributeFlagValue(entityItem, "MACHTYPEATR");
                if(null == key)
                {
                    key = "";
                }

                key = key + "    ";
                key = key.substring(0, 4);
                modelStr = PokUtils.getAttributeValue(entityItem, "MODELATR", "", "000");
                modelStr = modelStr + "   ";
                modelStr = modelStr.substring(0, 3);
                key = "D" + key + modelStr + entityItem.getKey();
                if(!SWAppBase.containsKey(key))
                {
                    SWAppBase.put(key, entityItem);
                }
            }
        }//end of while(itr.hasNext())
    }

    /********************************************************************************
    * populateQ352A1SupportFeatureBase()
    *
    */
    private void populateQ352A1SupportFeatureBase()
    {
        Vector tempVector = new Vector(1);
        Iterator itr = SWProdStructVector.iterator();

        log("In populateQ352A1SupportFeatureBase()");

        while(itr.hasNext())
        {
            Hashtable tempHT;
            Vector aVector;
            Vector bVector;
            Vector cVector;

            EntityItem swProdStructItem = (EntityItem) itr.next();
            log(swProdStructItem.toString());

            aVector = PokUtils.getAllLinkedEntities(swProdStructItem, "SWMODSTRUCT", "COFOOFMGMTGRP");
            log("aVector.size() = " + aVector.size());
            //101 = Software
            //144 = FeatureCode
            //181 = Billing
            //010 = N/A
            tempHT = new Hashtable();
            tempHT.put("COFOOFMGCAT", "101");
            tempHT.put("COFOOFMGSUBCAT", "144");
            tempHT.put("COFOOFMGGRP", "181");
            tempHT.put("COFOOFMGSUBGRP", "010");
            aVector = PokUtils.getEntitiesWithMatchedAttr(aVector, tempHT);
            log("aVector.size() = " + aVector.size());

            bVector = PokUtils.getAllLinkedEntities(aVector, "COFOWNSOOFOMG", "MODEL");
            log("bVector.size() = " + bVector.size());
            tempHT.clear();
            //101 = Software
            //135 = SupportFeature
            //151 = Initial
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "135");
            tempHT.put("COFGRP", "151");
            tempHT.put("COFSUBGRP", "010");
            bVector = PokUtils.getEntitiesWithMatchedAttr(bVector, tempHT);
            log("bVector.size() = " + bVector.size());

            cVector = PokUtils.getAllLinkedEntities(bVector, "MODELREL", "MODEL");
            log("cVector.size() = " + cVector.size());
            tempHT.clear();
            //101 = Software
            //135 = SupportFeature
            //150 = Base
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "135");
            tempHT.put("COFGRP", "150");
            tempHT.put("COFSUBGRP", "010");
            cVector = PokUtils.getEntitiesWithMatchedAttr(cVector, tempHT);
            log("cVector.size() = " + cVector.size());

            //Should be one and only one Model with classification SW-OptionalFeature-Base
            //if(cVector.size() > 0)
            for(int i = 0; i < cVector.size(); i++)
            {
                //EntityItem entityItem = (EntityItem) cVector.get(0);
                EntityItem entityItem = (EntityItem) cVector.get(i);
                if(!tempVector.contains(entityItem))
                {
                    tempVector.add(entityItem);
                }

                Q352A1AppOptFeaBase2.add(new SWEntityItem_OIM(null, entityItem, swProdStructItem, null));
            }
        }//end of while(itr.hasNext())

        itr = tempVector.iterator();

        while(itr.hasNext())
        {
            Vector aVector;
            EntityItem modelItem = (EntityItem) itr.next();
            Hashtable tempHT = new Hashtable();;
            aVector = PokUtils.getAllLinkedEntities(modelItem, "MODELREL", "MODEL");
            log("aVector.size() = " + aVector.size());

            //101 = Software
            //134 = Support
            //150 = Base
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "134");
            tempHT.put("COFGRP", "150");
            tempHT.put("COFSUBGRP", "010");
            aVector = PokUtils.getEntitiesWithMatchedAttr(aVector, tempHT);
            log("aVector.size() = " + aVector.size());

            //Should be one and only one Model with classification SW-Application-Base
            //if(aVector.size() > 0)
            for(int i = 0; i < aVector.size(); i++)
            {
                String key;
                String modelStr;
                //EntityItem entityItem = (EntityItem) aVector.get(0);
                EntityItem entityItem = (EntityItem) aVector.get(i);

                Q352A1AppOptFeaBase1.add(new SWEntityItem_OIM(entityItem, modelItem, null, null));
                key = PokUtils.getAttributeFlagValue(entityItem, "MACHTYPEATR");
                if(null == key)
                {
                    key = "";
                }

                key = key + "    ";
                key = key.substring(0, 4);
                modelStr = PokUtils.getAttributeValue(entityItem, "MODELATR", "", "000");
                modelStr = modelStr + "   ";
                modelStr = modelStr.substring(0, 3);
                key = "D" + key + modelStr + entityItem.getKey();
                if(!SWAppBase.containsKey(key))
                {
                    SWAppBase.put(key, entityItem);
                }
            }
        }//end of while(itr.hasNext())
    }

    /********************************************************************************
    *
    *
    * @returns void
    */
    private void populateQ352A2AppBaseTo()
    {
        Iterator itr = SWProdStructVector.iterator();

        log("In populateQ352A2AppBaseTo()");

        while(itr.hasNext())
        {
            Hashtable tempHT;
            Vector aVector;
            Vector bVector;
            Vector cVector;
            Vector fVector;

            EntityItem swProdStructItem = (EntityItem) itr.next();
            log(swProdStructItem.toString());

            aVector = PokUtils.getAllLinkedEntities(swProdStructItem, "SWMODSTRUCT", "COFOOFMGMTGRP");
            log("aVector.size() = " + aVector.size());
            //101 = Software
            //144 = FeatureCode
            //181 = Billing
            //010 = N/A
            tempHT = new Hashtable();
            tempHT.put("COFOOFMGCAT", "101");
            tempHT.put("COFOOFMGSUBCAT", "144");
            tempHT.put("COFOOFMGGRP", "181");
            tempHT.put("COFOOFMGSUBGRP", "010");
            aVector = PokUtils.getEntitiesWithMatchedAttr(aVector, tempHT);
            log("aVector.size() = " + aVector.size());

            bVector = PokUtils.getAllLinkedEntities(aVector, "COFOWNSOOFOMG", "MODEL");
            log("bVector.size() = " + bVector.size());
            tempHT.clear();
            //101 = Software
            //127 = Application
            //152 = Upgrade
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "127");
            tempHT.put("COFGRP", "152");
            tempHT.put("COFSUBGRP", "010");
            bVector = PokUtils.getEntitiesWithMatchedAttr(bVector, tempHT);
            log("bVector.size() = " + bVector.size());

            cVector = PokUtils.getAllLinkedEntities(bVector, "MODELREL", "MODEL");
            log("cVector.size() = " + cVector.size());
            tempHT.clear();
            //101 = Software
            //127 = Application
            //150 = Base
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "127");
            tempHT.put("COFGRP", "150");
            tempHT.put("COFSUBGRP", "010");
            cVector = PokUtils.getEntitiesWithMatchedAttr(cVector, tempHT);
            log("cVector.size() = " + cVector.size());

            //Find From SWPRODSTRUCT
            fVector = PokUtils.getAllLinkedEntities(bVector, "COFOWNSOOFOMG", "COFOOFMGMTGRP");
            log("fVector.size() = " + fVector.size());
            tempHT.clear();
            //101 = Software
            //145 = UpgradeFrom
            //010 = N/A
            //010 = N/A
            tempHT.put("COFOOFMGCAT", "101");
            tempHT.put("COFOOFMGSUBCAT", "145");
            //tempHT.put("COFOOFMGGRP", "010");
            //tempHT.put("COFOOFMGSUBGRP", "010");
            fVector = PokUtils.getEntitiesWithMatchedAttr(fVector, tempHT);
            log("fVector.size() = " + fVector.size());
            //fVector = PokUtils.getAllLinkedEntities(fVector, "SWMODSTRUCT", "SWPRODSTRUCT");
            log("fVector.size() = " + fVector.size());

            //Should be one and only one Model with classification SW-Application-Base
            //if(cVector.size() > 0)
            for(int i = 0; i < cVector.size(); i++)
            {
                String key;
                String modelStr;
                //EntityItem entityItem = (EntityItem) cVector.get(0);
                EntityItem entityItem = (EntityItem) cVector.get(i);

                Iterator aItr = fVector.iterator();
                while(aItr.hasNext())
                {
                    EntityItem ei = (EntityItem) aItr.next();
                    Q352A2AppBaseTo.add(new SWEntityItem_OIM(entityItem, null, swProdStructItem, ei));
                    if(!checkUpgradeFrom(Q352A2AppBaseFrom, entityItem, ei))
                    {
                        Q352A2AppBaseFrom.add(new SWEntityItem_OIM(entityItem, null, null, ei));
                    }
                }

                key = PokUtils.getAttributeFlagValue(entityItem, "MACHTYPEATR");
                if(null == key)
                {
                    key = "";
                }

                key = key + "    ";
                key = key.substring(0, 4);
                modelStr = PokUtils.getAttributeValue(entityItem, "MODELATR", "", "000");
                modelStr = modelStr + "   ";
                modelStr = modelStr.substring(0, 3);
                key = "A" + key + modelStr + entityItem.getKey();
                if(!SWAppBase.containsKey(key))
                {
                    SWAppBase.put(key, entityItem);
                }
            }//end of for(int i = 0; i < cVector.size(); i++)

            for(int i = 0; i < bVector.size(); i++)
            {
                Hashtable ht;
                Vector v1;
                Vector v2;
                EntityItem ei = (EntityItem) bVector.get(i);

                v1 = PokUtils.getAllLinkedEntities(ei, "MODELREL", "MODEL");
                ht = new Hashtable();
                ht.clear();
                //101 = Software
                //127 = Application
                //150 = Base
                //010 = N/A
                ht.put("COFCAT", "101");
                ht.put("COFSUBCAT", "127");
                ht.put("COFGRP", "150");
                ht.put("COFSUBGRP", "010");
                v1 = PokUtils.getEntitiesWithMatchedAttr(v1, ht);

                v2 = PokUtils.getAllLinkedEntities(ei, "COFOWNSOOFOMG", "COFOOFMGMTGRP");
                ht.clear();
                //101 = Software
                //145 = UpgradeFrom
                //010 = N/A
                //010 = N/A
                ht.put("COFOOFMGCAT", "101");
                ht.put("COFOOFMGSUBCAT", "145");
                //ht.put("COFOOFMGGRP", "010");
                //ht.put("COFOOFMGSUBGRP", "010");
                v2 = PokUtils.getEntitiesWithMatchedAttr(v2, ht);
                //v2 = PokUtils.getAllLinkedEntities(v2, "SWMODSTRUCT", "SWPRODSTRUCT");

                for(int j = 0; j < v1.size(); j++)
                {
                    String modelStr;
                    EntityItem mei = (EntityItem) v1.get(j);

                    String mt = PokUtils.getAttributeFlagValue(ei, "MACHTYPEATR");
                    if(null == mt)
                    {
                        mt = "";
                    }

                    mt = mt + "    ";
                    mt = mt.substring(0, 4);
                    modelStr = PokUtils.getAttributeValue(ei, "MODELATR", "", "000");
                    modelStr = modelStr + "   ";
                    modelStr = modelStr.substring(0, 3);

                    for(int k = 0; k < v2.size(); k++)
                    {
                        EntityItem swpsei = (EntityItem) v2.get(k);

                        String key = swpsei.getKey() + "<:>" + mt + "<:>" + modelStr + "<:>" + mei.getKey();
                        fromMTMHT.put(key, mei);
                        log("fromMTMHT: " + key +", " + mei.toString());
                    }//end of for(int k = 0; k < v2.size(); k++)
                }//end of for(int j = 0; j < v1.size(); j++)
            }//end of for(int i = 0; i < bVector.size(); i++)
        }//end of while(itr.hasNext())
    }

    /********************************************************************************
    * populateQ352A2AppOptFeaBaseTo()
    *
    */
    private void populateQ352A2AppOptFeaBaseTo()
    {
        Vector tempVector = new Vector(1);

        Iterator itr = SWProdStructVector.iterator();

        while(itr.hasNext())
        {
            Hashtable tempHT;
            Vector aVector;
            Vector bVector;
            Vector cVector;
            Vector fVector;
            EntityItem swProdStructItem = (EntityItem) itr.next();

            aVector = PokUtils.getAllLinkedEntities(swProdStructItem, "SWMODSTRUCT", "COFOOFMGMTGRP");
            //101 = Software
            //144 = FeatureCode
            //181 = Billing
            //010 = N/A
            tempHT = new Hashtable();
            tempHT.put("COFOOFMGCAT", "101");
            tempHT.put("COFOOFMGSUBCAT", "144");
            tempHT.put("COFOOFMGGRP", "181");
            tempHT.put("COFOOFMGSUBGRP", "010");
            aVector = PokUtils.getEntitiesWithMatchedAttr(aVector, tempHT);

            bVector = PokUtils.getAllLinkedEntities(aVector, "COFOWNSOOFOMG", "MODEL");
            tempHT.clear();
            //101 = Software
            //130 = OptionalFeature
            //152 = Upgrade
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "130");
            tempHT.put("COFGRP", "152");
            tempHT.put("COFSUBGRP", "010");
            bVector = PokUtils.getEntitiesWithMatchedAttr(bVector, tempHT);

            cVector = PokUtils.getAllLinkedEntities(bVector, "MODELREL", "MODEL");
            tempHT.clear();
            //101 = Software
            //130 = OptionalFeature
            //150 = Base
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "130");
            tempHT.put("COFGRP", "150");
            tempHT.put("COFSUBGRP", "010");
            cVector = PokUtils.getEntitiesWithMatchedAttr(cVector, tempHT);

            //Find From SWPRODSTRUCT
            fVector = PokUtils.getAllLinkedEntities(bVector, "COFOWNSOOFOMG", "COFOOFMGMTGRP");
            tempHT.clear();
            //101 = Software
            //145 = UpgradeFrom
            //010 = N/A
            //010 = N/A
            tempHT.put("COFOOFMGCAT", "101");
            tempHT.put("COFOOFMGSUBCAT", "145");
            //tempHT.put("COFOOFMGGRP", "010");
            //tempHT.put("COFOOFMGSUBGRP", "010");
            fVector = PokUtils.getEntitiesWithMatchedAttr(fVector, tempHT);
            //fVector = PokUtils.getAllLinkedEntities(fVector, "SWMODSTRUCT", "SWPRODSTRUCT");

            //Should be one and only one Model with classification SW-OptionalFeature-Base
            //if(cVector.size() > 0)
            for(int i = 0; i < cVector.size(); i++)
            {
                //EntityItem entityItem = (EntityItem) cVector.get(0);
                EntityItem entityItem = (EntityItem) cVector.get(i);

                Iterator aItr = fVector.iterator();
                while(aItr.hasNext())
                {
                    EntityItem ei = (EntityItem) aItr.next();

                    Q352A2AppOptFeaBase2To.add(new SWEntityItem_OIM(null, entityItem, swProdStructItem, ei));
                    if(!checkUpgradeFrom(Q352A2AppOptFeaBaseFrom, entityItem, ei))
                    {
                        Q352A2AppOptFeaBaseFrom.add(new SWEntityItem_OIM(entityItem, null, null, ei));
                    }
                }

                if(!tempVector.contains(entityItem))
                {
                    tempVector.add(entityItem);
                }
            }//end of for(int i = 0; i < cVector.size(); i++)

            for(int i = 0; i < bVector.size(); i++)
            {
                Hashtable ht;
                Vector v1;
                Vector v2;
                EntityItem ei = (EntityItem) bVector.get(i);

                v1 = PokUtils.getAllLinkedEntities(ei, "MODELREL", "MODEL");
                ht = new Hashtable();
                ht.clear();
                //101 = Software
                //130 = OptionalFeature
                //150 = Base
                //010 = N/A
                ht.put("COFCAT", "101");
                ht.put("COFSUBCAT", "130");
                ht.put("COFGRP", "150");
                ht.put("COFSUBGRP", "010");
                v1 = PokUtils.getEntitiesWithMatchedAttr(v1, ht);

                v1 = PokUtils.getAllLinkedEntities(v1, "MODELREL", "MODEL");
                ht.clear();
                //101 = Software
                //127 = Application
                //150 = Base
                //010 = N/A
                ht.put("COFCAT", "101");
                ht.put("COFSUBCAT", "127");
                ht.put("COFGRP", "150");
                ht.put("COFSUBGRP", "010");
                v1 = PokUtils.getEntitiesWithMatchedAttr(v1, ht);

                v2 = PokUtils.getAllLinkedEntities(ei, "COFOWNSOOFOMG", "COFOOFMGMTGRP");
                ht.clear();
                //101 = Software
                //145 = UpgradeFrom
                //010 = N/A
                //010 = N/A
                ht.put("COFOOFMGCAT", "101");
                ht.put("COFOOFMGSUBCAT", "145");
                //ht.put("COFOOFMGGRP", "010");
                //ht.put("COFOOFMGSUBGRP", "010");
                v2 = PokUtils.getEntitiesWithMatchedAttr(v2, ht);
                //v2 = PokUtils.getAllLinkedEntities(v2, "SWMODSTRUCT", "SWPRODSTRUCT");

                for(int j = 0; j < v1.size(); j++)
                {
                    String modelStr;
                    EntityItem mei = (EntityItem) v1.get(j);

                    String mt = PokUtils.getAttributeFlagValue(ei, "MACHTYPEATR");
                    if(null == mt)
                    {
                        mt = "";
                    }

                    mt = mt + "    ";
                    mt = mt.substring(0, 4);
                    modelStr = PokUtils.getAttributeValue(ei, "MODELATR", "", "000");
                    modelStr = modelStr + "   ";
                    modelStr = modelStr.substring(0, 3);

                    for(int k = 0; k < v2.size(); k++)
                    {
                        EntityItem swpsei = (EntityItem) v2.get(k);

                        String key = swpsei.getKey() + "<:>" + mt + "<:>" + modelStr + "<:>" + mei.getKey();
                        fromMTMHT.put(key, mei);
                    }//end of for(int k = 0; k < v2.size(); k++)
                }//end of for(int j = 0; j < v1.size(); j++)
            }//end of for(int i = 0; i < bVector.size(); i++)
        }//end of while(itr.hasNext())

        itr = tempVector.iterator();

        while(itr.hasNext())
        {
            Hashtable tempHT;
            Vector aVector;
            EntityItem modelItem = (EntityItem) itr.next();

            aVector = PokUtils.getAllLinkedEntities(modelItem, "MODELREL", "MODEL");
            tempHT = new Hashtable();
            //101 = Software
            //127 = Application
            //150 = Base
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "127");
            tempHT.put("COFGRP", "150");
            tempHT.put("COFSUBGRP", "010");
            aVector = PokUtils.getEntitiesWithMatchedAttr(aVector, tempHT);

            //Should be one and only one Model with classification SW-Application-Base
            //if(aVector.size() > 0)
            for(int i = 0; i < aVector.size(); i++)
            {
                String key;
                String modelStr;
                //EntityItem entityItem = (EntityItem) aVector.get(0);
                EntityItem entityItem = (EntityItem) aVector.get(i);

                Q352A2AppOptFeaBase1To.add(new SWEntityItem_OIM(entityItem, modelItem, null, null));
                key = PokUtils.getAttributeFlagValue(entityItem, "MACHTYPEATR");
                if(null == key)
                {
                    key = "";
                }

                key = key + "    ";
                key = key.substring(0, 4);
                modelStr = PokUtils.getAttributeValue(entityItem, "MODELATR", "", "000");
                modelStr = modelStr + "   ";
                modelStr = modelStr.substring(0, 3);
                key = "A" + key + modelStr + entityItem.getKey();
                if(!SWAppBase.containsKey(key))
                {
                    SWAppBase.put(key, entityItem);
                }
            }
        }//end of while(itr.hasNext())
    }

    /********************************************************************************
    * populateQ352A3AppBase()
    *
    */
    private void populateQ352A3AppBase()
    {
        Iterator itr = SWProdStructVector.iterator();

        while(itr.hasNext())
        {
            Hashtable tempHT;
            Vector aVector;
            Vector bVector;
            Vector cVector;

            EntityItem swProdStructItem = (EntityItem) itr.next();

            aVector = PokUtils.getAllLinkedEntities(swProdStructItem, "SWMODSTRUCT", "COFOOFMGMTGRP");
            //101 = Software
            //144 = FeatureCode
            //181 = Billing
            //010 = N/A
            tempHT = new Hashtable();
            tempHT.put("COFOOFMGCAT", "101");
            tempHT.put("COFOOFMGSUBCAT", "144");
            tempHT.put("COFOOFMGGRP", "181");
            tempHT.put("COFOOFMGSUBGRP", "010");
            aVector = PokUtils.getEntitiesWithMatchedAttr(aVector, tempHT);

            bVector = PokUtils.getAllLinkedEntities(aVector, "COFOWNSOOFOMG", "MODEL");
            tempHT.clear();
            //101 = Software
            //127 = Application
            //151 = Initial
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "127");
            tempHT.put("COFGRP", "151");
            tempHT.put("COFSUBGRP", "010");
            bVector = PokUtils.getEntitiesWithMatchedAttr(bVector, tempHT);

            cVector = PokUtils.getAllLinkedEntities(bVector, "MODELREL", "MODEL");
            tempHT.clear();
            //101 = Software
            //127 = Application
            //150 = Base
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "127");
            tempHT.put("COFGRP", "150");
            tempHT.put("COFSUBGRP", "010");
            cVector = PokUtils.getEntitiesWithMatchedAttr(cVector, tempHT);

            //Should be one and only one Model with classification SW-Application-Base
            //if(cVector.size() > 0)
            for(int i = 0; i < cVector.size(); i++)
            {
                String key;
                String modelStr;
                //EntityItem entityItem = (EntityItem) cVector.get(0);
                EntityItem entityItem = (EntityItem) cVector.get(i);

                Q352A3AppBase.add(new SWEntityItem_OIM(entityItem, null, swProdStructItem, null));
                key = PokUtils.getAttributeFlagValue(entityItem, "MACHTYPEATR");
                if(null == key)
                {
                    key = "";
                }

                key = key + "    ";
                key = key.substring(0, 4);
                modelStr = PokUtils.getAttributeValue(entityItem, "MODELATR", "", "000");
                modelStr = modelStr + "   ";
                modelStr = modelStr.substring(0, 3);
                key = "A" + key + modelStr + entityItem.getKey();
                if(!SWAppBase.containsKey(key))
                {
                    SWAppBase.put(key, entityItem);
                }
            }
        }//end of while(itr.hasNext())
    }

    /********************************************************************************
    * populateQ352A3AppOptFeaBase()
    *
    */
    private void populateQ352A3AppOptFeaBase()
    {
        Vector tempVector = new Vector(1);

        Iterator itr = SWProdStructVector.iterator();

        while(itr.hasNext())
        {
            Hashtable tempHT;
            Vector aVector;
            Vector bVector;
            Vector cVector;
            EntityItem swProdStructItem = (EntityItem) itr.next();

            aVector = PokUtils.getAllLinkedEntities(swProdStructItem, "SWMODSTRUCT", "COFOOFMGMTGRP");
            //101 = Software
            //144 = FeatureCode
            //181 = Billing
            //010 = N/A
            tempHT = new Hashtable();
            tempHT.put("COFOOFMGCAT", "101");
            tempHT.put("COFOOFMGSUBCAT", "144");
            tempHT.put("COFOOFMGGRP", "181");
            tempHT.put("COFOOFMGSUBGRP", "010");
            aVector = PokUtils.getEntitiesWithMatchedAttr(aVector, tempHT);

            bVector = PokUtils.getAllLinkedEntities(aVector, "COFOWNSOOFOMG", "MODEL");
            tempHT.clear();
            //101 = Software
            //130 = OptionalFeature
            //151 = Initial
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "130");
            tempHT.put("COFGRP", "151");
            tempHT.put("COFSUBGRP", "010");
            bVector = PokUtils.getEntitiesWithMatchedAttr(bVector, tempHT);

            cVector = PokUtils.getAllLinkedEntities(bVector, "MODELREL", "MODEL");
            tempHT.clear();
            //101 = Software
            //130 = OptionalFeature
            //150 = Base
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "130");
            tempHT.put("COFGRP", "150");
            tempHT.put("COFSUBGRP", "010");
            cVector = PokUtils.getEntitiesWithMatchedAttr(cVector, tempHT);

            //Should be one and only one Model with classification SW-OptionalFeature-Base
            //if(cVector.size() > 0)
            for(int i = 0; i < cVector.size(); i++)
            {
                //EntityItem entityItem = (EntityItem) cVector.get(0);
                EntityItem entityItem = (EntityItem) cVector.get(i);
                if(!tempVector.contains(entityItem))
                {
                    tempVector.add(entityItem);
                }

                Q352A3AppOptFeaBase2.add(new SWEntityItem_OIM(null, entityItem, swProdStructItem, null));
            }
        }//end of while(itr.hasNext())

        itr = tempVector.iterator();

        while(itr.hasNext())
        {
            Hashtable tempHT;
            Vector aVector;
            EntityItem modelItem = (EntityItem) itr.next();

            aVector = PokUtils.getAllLinkedEntities(modelItem, "MODELREL", "MODEL");
            tempHT = new Hashtable();
            //101 = Software
            //127 = Application
            //150 = Base
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "127");
            tempHT.put("COFGRP", "150");
            tempHT.put("COFSUBGRP", "010");
            aVector = PokUtils.getEntitiesWithMatchedAttr(aVector, tempHT);

            //Should be one and only one Model with classification SW-Application-Base
            //if(aVector.size() > 0)
            for(int i = 0; i < aVector.size(); i++)
            {
                String key;
                String modelStr;
                //EntityItem entityItem = (EntityItem) aVector.get(0);
                EntityItem entityItem = (EntityItem) aVector.get(i);

                Q352A3AppOptFeaBase1.add(new SWEntityItem_OIM(entityItem, modelItem, null, null));
                key = PokUtils.getAttributeFlagValue(entityItem, "MACHTYPEATR");
                if(null == key)
                {
                    key = "";
                }

                key = key + "    ";
                key = key.substring(0, 4);
                modelStr = PokUtils.getAttributeValue(entityItem, "MODELATR", "", "000");
                modelStr = modelStr + "   ";
                modelStr = modelStr.substring(0, 3);
                key = "A" + key + modelStr + entityItem.getKey();
                if(!SWAppBase.containsKey(key))
                {
                    SWAppBase.put(key, entityItem);
                }
            }
        }//end of while(itr.hasNext())
    }

    /********************************************************************************
    * populateQ352A3SubscriptionBase()
    *
    */
    private void populateQ352A3SubscriptionBase()
    {
        Iterator itr = SWProdStructVector.iterator();

        while(itr.hasNext())
        {
            Hashtable tempHT;
            Vector aVector;
            Vector bVector;
            Vector cVector;

            EntityItem swProdStructItem = (EntityItem) itr.next();

            aVector = PokUtils.getAllLinkedEntities(swProdStructItem, "SWMODSTRUCT", "COFOOFMGMTGRP");
            //101 = Software
            //144 = FeatureCode
            //181 = Billing
            //010 = N/A
            tempHT = new Hashtable();
            tempHT.put("COFOOFMGCAT", "101");
            tempHT.put("COFOOFMGSUBCAT", "144");
            tempHT.put("COFOOFMGGRP", "181");
            tempHT.put("COFOOFMGSUBGRP", "010");
            aVector = PokUtils.getEntitiesWithMatchedAttr(aVector, tempHT);

            bVector = PokUtils.getAllLinkedEntities(aVector, "COFOWNSOOFOMG", "MODEL");
            tempHT.clear();
            //101 = Software
            //133 = Subscription
            //151 = Initial
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "133");
            tempHT.put("COFGRP", "151");
            tempHT.put("COFSUBGRP", "010");
            bVector = PokUtils.getEntitiesWithMatchedAttr(bVector, tempHT);

            cVector = PokUtils.getAllLinkedEntities(bVector, "MODELREL", "MODEL");
            tempHT.clear();
            //101 = Software
            //133 = Subscription
            //150 = Base
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "133");
            tempHT.put("COFGRP", "150");
            tempHT.put("COFSUBGRP", "010");
            cVector = PokUtils.getEntitiesWithMatchedAttr(cVector, tempHT);

            //Should be one and only one Model with classification SW-Application-Base
            //if(cVector.size() > 0)
            for(int i = 0; i < cVector.size(); i++)
            {
                String key;
                String modelStr;
                //EntityItem entityItem = (EntityItem) cVector.get(0);
                EntityItem entityItem = (EntityItem) cVector.get(i);

                Q352A3AppBase.add(new SWEntityItem_OIM(entityItem, null, swProdStructItem, null));
                key = PokUtils.getAttributeFlagValue(entityItem, "MACHTYPEATR");
                if(null == key)
                {
                    key = "";
                }

                key = key + "    ";
                key = key.substring(0, 4);
                modelStr = PokUtils.getAttributeValue(entityItem, "MODELATR", "", "000");
                modelStr = modelStr + "   ";
                modelStr = modelStr.substring(0, 3);
                key = "B" + key + modelStr + entityItem.getKey();
                if(!SWAppBase.containsKey(key))
                {
                    SWAppBase.put(key, entityItem);
                }
            }
        }//end of while(itr.hasNext())
    }

    /********************************************************************************
    * populateQ352A3SubscrFeatureBase()
    *
    */
    private void populateQ352A3SubscrFeatureBase()
    {
        Vector tempVector = new Vector(1);

        Iterator itr = SWProdStructVector.iterator();

        while(itr.hasNext())
        {
            Hashtable tempHT;
            Vector aVector;
            Vector bVector;
            Vector cVector;
            EntityItem swProdStructItem = (EntityItem) itr.next();

            aVector = PokUtils.getAllLinkedEntities(swProdStructItem, "SWMODSTRUCT", "COFOOFMGMTGRP");
            //101 = Software
            //144 = FeatureCode
            //181 = Billing
            //010 = N/A
            tempHT = new Hashtable();
            tempHT.put("COFOOFMGCAT", "101");
            tempHT.put("COFOOFMGSUBCAT", "144");
            tempHT.put("COFOOFMGGRP", "181");
            tempHT.put("COFOOFMGSUBGRP", "010");
            aVector = PokUtils.getEntitiesWithMatchedAttr(aVector, tempHT);

            bVector = PokUtils.getAllLinkedEntities(aVector, "COFOWNSOOFOMG", "MODEL");
            tempHT.clear();
            //101 = Software
            //132 = SubscrFeature
            //151 = Initial
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "132");
            tempHT.put("COFGRP", "151");
            tempHT.put("COFSUBGRP", "010");
            bVector = PokUtils.getEntitiesWithMatchedAttr(bVector, tempHT);

            cVector = PokUtils.getAllLinkedEntities(bVector, "MODELREL", "MODEL");
            tempHT.clear();
            //101 = Software
            //132 = SubscrFeature
            //150 = Base
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "132");
            tempHT.put("COFGRP", "150");
            tempHT.put("COFSUBGRP", "010");
            cVector = PokUtils.getEntitiesWithMatchedAttr(cVector, tempHT);

            //Should be one and only one Model with classification SW-OptionalFeature-Base
            //if(cVector.size() > 0)
            for(int i = 0; i < cVector.size(); i++)
            {
                //EntityItem entityItem = (EntityItem) cVector.get(0);
                EntityItem entityItem = (EntityItem) cVector.get(i);
                if(!tempVector.contains(entityItem))
                {
                    tempVector.add(entityItem);
                }

                Q352A3AppOptFeaBase2.add(new SWEntityItem_OIM(null, entityItem, swProdStructItem, null));
            }
        }//end of while(itr.hasNext())

        itr = tempVector.iterator();

        while(itr.hasNext())
        {
            Hashtable tempHT;
            Vector aVector;
            EntityItem modelItem = (EntityItem) itr.next();

            aVector = PokUtils.getAllLinkedEntities(modelItem, "MODELREL", "MODEL");
            tempHT = new Hashtable();
            //101 = Software
            //133 = Subscription
            //150 = Base
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "133");
            tempHT.put("COFGRP", "150");
            tempHT.put("COFSUBGRP", "010");
            aVector = PokUtils.getEntitiesWithMatchedAttr(aVector, tempHT);

            //Should be one and only one Model with classification SW-Application-Base
            //if(aVector.size() > 0)
            for(int i = 0; i < aVector.size(); i++)
            {
                String key;
                String modelStr;
                //EntityItem entityItem = (EntityItem) aVector.get(0);
                EntityItem entityItem = (EntityItem) aVector.get(i);

                Q352A3AppOptFeaBase1.add(new SWEntityItem_OIM(entityItem, modelItem, null, null));
                key = PokUtils.getAttributeFlagValue(entityItem, "MACHTYPEATR");
                if(null == key)
                {
                    key = "";
                }

                key = key + "    ";
                key = key.substring(0, 4);
                modelStr = PokUtils.getAttributeValue(entityItem, "MODELATR", "", "000");
                modelStr = modelStr + "   ";
                modelStr = modelStr.substring(0, 3);
                key = "B" + key + modelStr + entityItem.getKey();
                if(!SWAppBase.containsKey(key))
                {
                    SWAppBase.put(key, entityItem);
                }
            }
        }//end of while(itr.hasNext())
    }

    /********************************************************************************
    * populateQ352A3MaintenanceBase()
    *
    */
    private void populateQ352A3MaintenanceBase()
    {
        Iterator itr = SWProdStructVector.iterator();

        while(itr.hasNext())
        {
            Hashtable tempHT;
            Vector aVector;
            Vector bVector;
            Vector cVector;

            EntityItem swProdStructItem = (EntityItem) itr.next();

            aVector = PokUtils.getAllLinkedEntities(swProdStructItem, "SWMODSTRUCT", "COFOOFMGMTGRP");
            //101 = Software
            //144 = FeatureCode
            //181 = Billing
            //010 = N/A
            tempHT = new Hashtable();
            tempHT.put("COFOOFMGCAT", "101");
            tempHT.put("COFOOFMGSUBCAT", "144");
            tempHT.put("COFOOFMGGRP", "181");
            tempHT.put("COFOOFMGSUBGRP", "010");
            aVector = PokUtils.getEntitiesWithMatchedAttr(aVector, tempHT);

            bVector = PokUtils.getAllLinkedEntities(aVector, "COFOWNSOOFOMG", "MODEL");
            tempHT.clear();
            //101 = Software
            //128 = Maintenance
            //151 = Initial
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "128");
            tempHT.put("COFGRP", "151");
            tempHT.put("COFSUBGRP", "010");
            bVector = PokUtils.getEntitiesWithMatchedAttr(bVector, tempHT);

            cVector = PokUtils.getAllLinkedEntities(bVector, "MODELREL", "MODEL");
            tempHT.clear();
            //101 = Software
            //128 = Maintenance
            //150 = Base
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "128");
            tempHT.put("COFGRP", "150");
            tempHT.put("COFSUBGRP", "010");
            cVector = PokUtils.getEntitiesWithMatchedAttr(cVector, tempHT);

            //Should be one and only one Model with classification SW-Application-Base
            //if(cVector.size() > 0)
            for(int i = 0; i < cVector.size(); i++)
            {
                String key;
                String modelStr;
                //EntityItem entityItem = (EntityItem) cVector.get(0);
                EntityItem entityItem = (EntityItem) cVector.get(i);

                Q352A3AppBase.add(new SWEntityItem_OIM(entityItem, null, swProdStructItem, null));
                key = PokUtils.getAttributeFlagValue(entityItem, "MACHTYPEATR");
                if(null == key)
                {
                    key = "";
                }

                key = key + "    ";
                key = key.substring(0, 4);
                modelStr = PokUtils.getAttributeValue(entityItem, "MODELATR", "", "000");
                modelStr = modelStr + "   ";
                modelStr = modelStr.substring(0, 3);
                key = "C" + key + modelStr + entityItem.getKey();
                if(!SWAppBase.containsKey(key))
                {
                    SWAppBase.put(key, entityItem);
                }
            }
        }//end of while(itr.hasNext())
    }

    /********************************************************************************
    * populateQ352A3MaintFeatureBase()
    *
    */
    private void populateQ352A3MaintFeatureBase()
    {
        Vector tempVector = new Vector(1);

        Iterator itr = SWProdStructVector.iterator();

        while(itr.hasNext())
        {
            Hashtable tempHT;
            Vector aVector;
            Vector bVector;
            Vector cVector;
            EntityItem swProdStructItem = (EntityItem) itr.next();

            aVector = PokUtils.getAllLinkedEntities(swProdStructItem, "SWMODSTRUCT", "COFOOFMGMTGRP");
            //101 = Software
            //144 = FeatureCode
            //181 = Billing
            //010 = N/A
            tempHT = new Hashtable();
            tempHT.put("COFOOFMGCAT", "101");
            tempHT.put("COFOOFMGSUBCAT", "144");
            tempHT.put("COFOOFMGGRP", "181");
            tempHT.put("COFOOFMGSUBGRP", "010");
            aVector = PokUtils.getEntitiesWithMatchedAttr(aVector, tempHT);

            bVector = PokUtils.getAllLinkedEntities(aVector, "COFOWNSOOFOMG", "MODEL");
            tempHT.clear();
            //101 = Software
            //129 = MaintFeature
            //151 = Initial
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "129");
            tempHT.put("COFGRP", "151");
            tempHT.put("COFSUBGRP", "010");
            bVector = PokUtils.getEntitiesWithMatchedAttr(bVector, tempHT);

            cVector = PokUtils.getAllLinkedEntities(bVector, "MODELREL", "MODEL");
            tempHT.clear();
            //101 = Software
            //129 = MaintFeature
            //150 = Base
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "129");
            tempHT.put("COFGRP", "150");
            tempHT.put("COFSUBGRP", "010");
            cVector = PokUtils.getEntitiesWithMatchedAttr(cVector, tempHT);

            //Should be one and only one Model with classification SW-OptionalFeature-Base
            //if(cVector.size() > 0)
            for(int i = 0; i < cVector.size(); i++)
            {
                //EntityItem entityItem = (EntityItem) cVector.get(0);
                EntityItem entityItem = (EntityItem) cVector.get(i);
                if(!tempVector.contains(entityItem))
                {
                    tempVector.add(entityItem);
                }

                Q352A3AppOptFeaBase2.add(new SWEntityItem_OIM(null, entityItem, swProdStructItem, null));
            }
        }//end of while(itr.hasNext())

        itr = tempVector.iterator();

        while(itr.hasNext())
        {
            Hashtable tempHT;
            Vector aVector;
            EntityItem modelItem = (EntityItem) itr.next();

            aVector = PokUtils.getAllLinkedEntities(modelItem, "MODELREL", "MODEL");
            tempHT = new Hashtable();
            //101 = Software
            //128 = Maintenance
            //150 = Base
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "128");
            tempHT.put("COFGRP", "150");
            tempHT.put("COFSUBGRP", "010");
            aVector = PokUtils.getEntitiesWithMatchedAttr(aVector, tempHT);

            //Should be one and only one Model with classification SW-Application-Base
            //if(aVector.size() > 0)
            for(int i = 0; i < aVector.size(); i++)
            {
                String key;
                String modelStr;
                //EntityItem entityItem = (EntityItem) aVector.get(0);
                EntityItem entityItem = (EntityItem) aVector.get(i);

                Q352A3AppOptFeaBase1.add(new SWEntityItem_OIM(entityItem, modelItem, null, null));
                key = PokUtils.getAttributeFlagValue(entityItem, "MACHTYPEATR");
                if(null == key)
                {
                    key = "";
                }

                key = key + "    ";
                key = key.substring(0, 4);
                modelStr = PokUtils.getAttributeValue(entityItem, "MODELATR", "", "000");
                modelStr = modelStr + "   ";
                modelStr = modelStr.substring(0, 3);
                key = "C" + key + modelStr + entityItem.getKey();
                if(!SWAppBase.containsKey(key))
                {
                    SWAppBase.put(key, entityItem);
                }
            }
        }//end of while(itr.hasNext())
    }

    /********************************************************************************
    * populateQ352A3SupportBase()
    *
    */
    private void populateQ352A3SupportBase()
    {
        Iterator itr = SWProdStructVector.iterator();

        while(itr.hasNext())
        {
            Hashtable tempHT;
            Vector aVector;
            Vector bVector;
            Vector cVector;

            EntityItem swProdStructItem = (EntityItem) itr.next();

            aVector = PokUtils.getAllLinkedEntities(swProdStructItem, "SWMODSTRUCT", "COFOOFMGMTGRP");
            //101 = Software
            //144 = FeatureCode
            //181 = Billing
            //010 = N/A
            tempHT = new Hashtable();
            tempHT.put("COFOOFMGCAT", "101");
            tempHT.put("COFOOFMGSUBCAT", "144");
            tempHT.put("COFOOFMGGRP", "181");
            tempHT.put("COFOOFMGSUBGRP", "010");
            aVector = PokUtils.getEntitiesWithMatchedAttr(aVector, tempHT);

            bVector = PokUtils.getAllLinkedEntities(aVector, "COFOWNSOOFOMG", "MODEL");
            tempHT.clear();
            //101 = Software
            //134 = Support
            //151 = Initial
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "134");
            tempHT.put("COFGRP", "151");
            tempHT.put("COFSUBGRP", "010");
            bVector = PokUtils.getEntitiesWithMatchedAttr(bVector, tempHT);

            cVector = PokUtils.getAllLinkedEntities(bVector, "MODELREL", "MODEL");
            tempHT.clear();
            //101 = Software
            //134 = Suport
            //150 = Base
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "134");
            tempHT.put("COFGRP", "150");
            tempHT.put("COFSUBGRP", "010");
            cVector = PokUtils.getEntitiesWithMatchedAttr(cVector, tempHT);

            //Should be one and only one Model with classification SW-Application-Base
            //if(cVector.size() > 0)
            for(int i = 0; i < cVector.size(); i++)
            {
                String key;
                String modelStr;
                //EntityItem entityItem = (EntityItem) cVector.get(0);
                EntityItem entityItem = (EntityItem) cVector.get(i);

                Q352A3AppBase.add(new SWEntityItem_OIM(entityItem, null, swProdStructItem, null));
                key = PokUtils.getAttributeFlagValue(entityItem, "MACHTYPEATR");
                if(null == key)
                {
                    key = "";
                }

                key = key + "    ";
                key = key.substring(0, 4);
                modelStr = PokUtils.getAttributeValue(entityItem, "MODELATR", "", "000");
                modelStr = modelStr + "   ";
                modelStr = modelStr.substring(0, 3);
                key = "D" + key + modelStr + entityItem.getKey();
                if(!SWAppBase.containsKey(key))
                {
                    SWAppBase.put(key, entityItem);
                }
            }
        }//end of while(itr.hasNext())
    }

    /********************************************************************************
    * populateQ352A3SupportFeatureBase()
    *
    */
    private void populateQ352A3SupportFeatureBase()
    {
        Vector tempVector = new Vector(1);

        Iterator itr = SWProdStructVector.iterator();

        while(itr.hasNext())
        {
            Hashtable tempHT;
            Vector aVector;
            Vector bVector;
            Vector cVector;
            EntityItem swProdStructItem = (EntityItem) itr.next();

            aVector = PokUtils.getAllLinkedEntities(swProdStructItem, "SWMODSTRUCT", "COFOOFMGMTGRP");
            //101 = Software
            //144 = FeatureCode
            //181 = Billing
            //010 = N/A
            tempHT = new Hashtable();
            tempHT.put("COFOOFMGCAT", "101");
            tempHT.put("COFOOFMGSUBCAT", "144");
            tempHT.put("COFOOFMGGRP", "181");
            tempHT.put("COFOOFMGSUBGRP", "010");
            aVector = PokUtils.getEntitiesWithMatchedAttr(aVector, tempHT);

            bVector = PokUtils.getAllLinkedEntities(aVector, "COFOWNSOOFOMG", "MODEL");
            tempHT.clear();
            //101 = Software
            //135 = SupportFeature
            //151 = Initial
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "135");
            tempHT.put("COFGRP", "151");
            tempHT.put("COFSUBGRP", "010");
            bVector = PokUtils.getEntitiesWithMatchedAttr(bVector, tempHT);

            cVector = PokUtils.getAllLinkedEntities(bVector, "MODELREL", "MODEL");
            tempHT.clear();
            //101 = Software
            //135 = SupportFeature
            //150 = Base
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "135");
            tempHT.put("COFGRP", "150");
            tempHT.put("COFSUBGRP", "010");
            cVector = PokUtils.getEntitiesWithMatchedAttr(cVector, tempHT);

            //Should be one and only one Model with classification SW-OptionalFeature-Base
            //if(cVector.size() > 0)
            for(int i = 0; i < cVector.size(); i++)
            {
                //EntityItem entityItem = (EntityItem) cVector.get(0);
                EntityItem entityItem = (EntityItem) cVector.get(i);
                if(!tempVector.contains(entityItem))
                {
                    tempVector.add(entityItem);
                }

                Q352A3AppOptFeaBase2.add(new SWEntityItem_OIM(null, entityItem, swProdStructItem, null));
            }
        }//end of while(itr.hasNext())

        itr = tempVector.iterator();

        while(itr.hasNext())
        {
            Hashtable tempHT;
            Vector aVector;
            EntityItem modelItem = (EntityItem) itr.next();

            aVector = PokUtils.getAllLinkedEntities(modelItem, "MODELREL", "MODEL");
            tempHT = new Hashtable();
            //101 = Software
            //134 = Support
            //150 = Base
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "134");
            tempHT.put("COFGRP", "150");
            tempHT.put("COFSUBGRP", "010");
            aVector = PokUtils.getEntitiesWithMatchedAttr(aVector, tempHT);

            //Should be one and only one Model with classification SW-Application-Base
            //if(aVector.size() > 0)
            for(int i = 0; i < aVector.size(); i++)
            {
                String key;
                String modelStr;
                //EntityItem entityItem = (EntityItem) aVector.get(0);
                EntityItem entityItem = (EntityItem) aVector.get(i);

                Q352A3AppOptFeaBase1.add(new SWEntityItem_OIM(entityItem, modelItem, null, null));
                key = PokUtils.getAttributeFlagValue(entityItem, "MACHTYPEATR");
                if(null == key)
                {
                    key = "";
                }

                key = key + "    ";
                key = key.substring(0, 4);
                modelStr = PokUtils.getAttributeValue(entityItem, "MODELATR", "", "000");
                modelStr = modelStr + "   ";
                modelStr = modelStr.substring(0, 3);
                key = "D" + key + modelStr + entityItem.getKey();
                if(!SWAppBase.containsKey(key))
                {
                    SWAppBase.put(key, entityItem);
                }
            }
        }//end of while(itr.hasNext())
    }

    /********************************************************************************
    * populateQ352A4()
    *
    */
    private void populateQ352A4()
    {
        populateQ352A4AppBase(SWProdStructVector, Q352A4AppBaseChargeFee);
        populateQ352A4AppBase(SWProdStructVectorNoChargeFee, Q352A4AppBaseNoChargeFee);
        populateQ352A4AppOptFeaBase(SWProdStructVector, Q352A4AppOptFeaBase1ChargeFee, Q352A4AppOptFeaBase2ChargeFee);
        populateQ352A4AppOptFeaBase(SWProdStructVectorNoChargeFee, Q352A4AppOptFeaBase1NoChargeFee, Q352A4AppOptFeaBase2NoChargeFee);

        //Begin new CR050504359
        populateQ352A4SubscriptionBase(SWProdStructVector, Q352A4AppBaseChargeFee);
        populateQ352A4SubscriptionBase(SWProdStructVectorNoChargeFee, Q352A4AppBaseNoChargeFee);
        populateQ352A4SubscrFeaBase(SWProdStructVector, Q352A4AppOptFeaBase1ChargeFee, Q352A4AppOptFeaBase2ChargeFee);
        populateQ352A4SubscrFeaBase(SWProdStructVectorNoChargeFee, Q352A4AppOptFeaBase1NoChargeFee, Q352A4AppOptFeaBase2NoChargeFee);
        populateQ352A4MaintenanceBase(SWProdStructVector, Q352A4AppBaseChargeFee);
        populateQ352A4MaintenanceBase(SWProdStructVectorNoChargeFee, Q352A4AppBaseNoChargeFee);
        populateQ352A4MaintFeaBase(SWProdStructVector, Q352A4AppOptFeaBase1ChargeFee, Q352A4AppOptFeaBase2ChargeFee);
        populateQ352A4MaintFeaBase(SWProdStructVectorNoChargeFee, Q352A4AppOptFeaBase1NoChargeFee, Q352A4AppOptFeaBase2NoChargeFee);
        populateQ352A4SupportBase(SWProdStructVector, Q352A4AppBaseChargeFee);
        populateQ352A4SupportBase(SWProdStructVectorNoChargeFee, Q352A4AppBaseNoChargeFee);
        populateQ352A4SupportFeaBase(SWProdStructVector, Q352A4AppOptFeaBase1ChargeFee, Q352A4AppOptFeaBase2ChargeFee);
        populateQ352A4SupportFeaBase(SWProdStructVectorNoChargeFee, Q352A4AppOptFeaBase1NoChargeFee, Q352A4AppOptFeaBase2NoChargeFee);
        //End new CR050504359

        //Begin CR1019052831
        populateQ352A4NCValueMetricAppBase(SWProdStructVectorNoChargeFee, Q352A4AppBaseNoChargeFee);
        populateQ352A4NCValueMetricAppOptFeaBase(SWProdStructVectorNoChargeFee, Q352A4AppOptFeaBase1NoChargeFee, Q352A4AppOptFeaBase2NoChargeFee);
        populateQ352A4NCValueMetricSubscriptionBase(SWProdStructVectorNoChargeFee, Q352A4AppBaseNoChargeFee);
        populateQ352A4NCValueMetricSubscrFeaBase(SWProdStructVectorNoChargeFee, Q352A4AppOptFeaBase1NoChargeFee, Q352A4AppOptFeaBase2NoChargeFee);
        populateQ352A4NCValueMetricMaintenanceBase(SWProdStructVectorNoChargeFee, Q352A4AppBaseNoChargeFee);
        populateQ352A4NCValueMetricMaintFeaBase(SWProdStructVectorNoChargeFee, Q352A4AppOptFeaBase1NoChargeFee, Q352A4AppOptFeaBase2NoChargeFee);
        populateQ352A4NCValueMetricSupportBase(SWProdStructVectorNoChargeFee, Q352A4AppBaseNoChargeFee);
        populateQ352A4NCValueMetricSupportFeaBase(SWProdStructVectorNoChargeFee, Q352A4AppOptFeaBase1NoChargeFee, Q352A4AppOptFeaBase2NoChargeFee);
        //End CR1019052831
    }

    /********************************************************************************
    * populateQ352A4AppBase()
    *
    * @param v1 Vector
    * @param v2 Vector
    */
    private void populateQ352A4AppBase(Vector v1, Vector v2)
    {
        Iterator itr = v1.iterator();

        while(itr.hasNext())
        {
            Hashtable tempHT;
            Vector aVector;
            Vector bVector;
            EntityItem swProdStructItem = (EntityItem) itr.next();

            aVector = PokUtils.getAllLinkedEntities(swProdStructItem, "SWMODSTRUCT", "COFOOFMGMTGRP");
            //101 = Software
            //144 = FeatureCode
            //010 = N/A
            //010 = N/A
            tempHT = new Hashtable();
            tempHT.put("COFOOFMGCAT", "101");
            tempHT.put("COFOOFMGSUBCAT", "144");
            //tempHT.put("COFOOFMGGRP", "181");
            //tempHT.put("COFOOFMGSUBGRP", "010");
            aVector = PokUtils.getEntitiesWithMatchedAttr(aVector, tempHT);

            bVector = PokUtils.getAllLinkedEntities(aVector, "COFOWNSOOFOMG", "MODEL");
            tempHT.clear();
            //101 = Software
            //127 = Application
            //150 = Base
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "127");
            tempHT.put("COFGRP", "150");
            //tempHT.put("COFSUBGRP", "010");
            bVector = PokUtils.getEntitiesWithMatchedAttr(bVector, tempHT);

            //Should be one and only one MODEL with classification SW-Application-Base
            //if(bVector.size() > 0)
            for(int i = 0; i < bVector.size(); i++)
            {
                String key;
                String modelStr;
                //EntityItem entityItem = (EntityItem) bVector.get(0);
                EntityItem entityItem = (EntityItem) bVector.get(i);

                v2.add(new SWEntityItem_OIM(entityItem, null, swProdStructItem, null));
                key = PokUtils.getAttributeFlagValue(entityItem, "MACHTYPEATR");
                if(null == key)
                {
                    key = "";
                }

                key = key + "    ";
                key = key.substring(0, 4);
                modelStr = PokUtils.getAttributeValue(entityItem, "MODELATR", "", "000");
                modelStr = modelStr + "   ";
                modelStr = modelStr.substring(0, 3);
                key = "A" + key + modelStr + entityItem.getKey();
                if(!SWAppBase.containsKey(key))
                {
                    SWAppBase.put(key, entityItem);
                }
            }
        }//end of while(itr.hasNext())
    }

    /********************************************************************************
    *
    *
    * @param v1 Vector
    * @param v2 Vector
    * @param v3 Vector
    */
    private void populateQ352A4AppOptFeaBase(Vector v1, Vector v2, Vector v3)
    {
        Vector tempVector = new Vector(1);

        Iterator itr = v1.iterator();

        while(itr.hasNext())
        {
            Hashtable tempHT;
            Vector aVector;
            Vector bVector;
            EntityItem swProdStructItem = (EntityItem) itr.next();

            aVector = PokUtils.getAllLinkedEntities(swProdStructItem,"SWMODSTRUCT", "COFOOFMGMTGRP");
            //101 = Software
            //144 = FeatureCode
            //010 = N/A
            //010 = N/A
            tempHT = new Hashtable();
            tempHT.put("COFOOFMGCAT", "101");
            tempHT.put("COFOOFMGSUBCAT", "144");
            //tempHT.put("COFOOFMGGRP", "181");
            //tempHT.put("COFOOFMGSUBGRP", "010");
            aVector = PokUtils.getEntitiesWithMatchedAttr(aVector, tempHT);

            bVector = PokUtils.getAllLinkedEntities(aVector, "COFOWNSOOFOMG", "MODEL");
            tempHT.clear();
            //101 = Software
            //130 = OptionalFeature
            //150 = Base
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "130");
            tempHT.put("COFGRP", "150");
            //tempHT.put("COFSUBGRP", "010");
            bVector = PokUtils.getEntitiesWithMatchedAttr(bVector, tempHT);

            //Should be one and only one MODEL with classification SW-OptionalFeature-Base
            //if(bVector.size() > 0)
            for(int i = 0; i < bVector.size(); i++)
            {
                //EntityItem entityItem = (EntityItem) bVector.get(0);
                EntityItem entityItem = (EntityItem) bVector.get(i);
                if(!tempVector.contains(entityItem))
                {
                    tempVector.add(entityItem);
                }

                v3.add(new SWEntityItem_OIM(null, entityItem, swProdStructItem, null));
            }
        }//end of while(itr.hasNext())

        itr = tempVector.iterator();

        while(itr.hasNext())
        {
            Hashtable tempHT;
            Vector aVector;
            EntityItem modelItem = (EntityItem) itr.next();

            aVector = PokUtils.getAllLinkedEntities(modelItem, "MODELREL", "MODEL");
            tempHT = new Hashtable();
            //101 = Software
            //127 = Application
            //150 = Base
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "127");
            tempHT.put("COFGRP", "150");
            //tempHT.put("COFSUBGRP", "010");
            aVector = PokUtils.getEntitiesWithMatchedAttr(aVector, tempHT);

            //Should be one and only one MODEL with classification SW-Application-Base
            //if(aVector.size() > 0)
            for(int i = 0; i < aVector.size(); i++)
            {
                String key;
                String modelStr;
                //EntityItem entityItem = (EntityItem) aVector.get(0);
                EntityItem entityItem = (EntityItem) aVector.get(i);

                v2.add(new SWEntityItem_OIM(entityItem, modelItem, null, null));
                key = PokUtils.getAttributeFlagValue(entityItem, "MACHTYPEATR");
                if(null == key)
                {
                    key = "";
                }

                key = key + "    ";
                key = key.substring(0, 4);
                modelStr = PokUtils.getAttributeValue(entityItem, "MODELATR", "", "000");
                modelStr = modelStr + "   ";
                modelStr = modelStr.substring(0, 3);
                key = "A" + key + modelStr + entityItem.getKey();
                if(!SWAppBase.containsKey(key))
                {
                    SWAppBase.put(key, entityItem);
                }
            }
        }//end of while(itr.hasNext())
    }

    /********************************************************************************
    * populateQ352A4SubscriptionBase()
    *
    * @param v1 Vector
    * @param v2 Vector
    */
    private void populateQ352A4SubscriptionBase(Vector v1, Vector v2)
    {
        Iterator itr = v1.iterator();

        while(itr.hasNext())
        {
            Hashtable tempHT;
            Vector aVector;
            Vector bVector;
            EntityItem swProdStructItem = (EntityItem) itr.next();

            aVector = PokUtils.getAllLinkedEntities(swProdStructItem, "SWMODSTRUCT", "COFOOFMGMTGRP");
            //101 = Software
            //144 = FeatureCode
            //010 = N/A
            //010 = N/A
            tempHT = new Hashtable();
            tempHT.put("COFOOFMGCAT", "101");
            tempHT.put("COFOOFMGSUBCAT", "144");
            //tempHT.put("COFOOFMGGRP", "181");
            //tempHT.put("COFOOFMGSUBGRP", "010");
            aVector = PokUtils.getEntitiesWithMatchedAttr(aVector, tempHT);

            bVector = PokUtils.getAllLinkedEntities(aVector, "COFOWNSOOFOMG", "MODEL");
            tempHT.clear();
            //101 = Software
            //133 = Subscription
            //150 = Base
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "133");
            tempHT.put("COFGRP", "150");
            //tempHT.put("COFSUBGRP", "010");
            bVector = PokUtils.getEntitiesWithMatchedAttr(bVector, tempHT);

            //Should be one and only one MODEL with classification SW-Application-Base
            //if(bVector.size() > 0)
            for(int i = 0; i < bVector.size(); i++)
            {
                String key;
                String modelStr;
                //EntityItem entityItem = (EntityItem) bVector.get(0);
                EntityItem entityItem = (EntityItem) bVector.get(i);

                v2.add(new SWEntityItem_OIM(entityItem, null, swProdStructItem, null));
                key = PokUtils.getAttributeFlagValue(entityItem, "MACHTYPEATR");
                if(null == key)
                {
                    key = "";
                }

                key = key + "    ";
                key = key.substring(0, 4);
                modelStr = PokUtils.getAttributeValue(entityItem, "MODELATR", "", "000");
                modelStr = modelStr + "   ";
                modelStr = modelStr.substring(0, 3);
                key = "B" + key + modelStr + entityItem.getKey();
                if(!SWAppBase.containsKey(key))
                {
                    SWAppBase.put(key, entityItem);
                }
            }
        }//end of while(itr.hasNext())
    }

    /********************************************************************************
    * populateQ352A4SubscrFeaBase()
    *
    * @param v1 Vector
    * @param v2 Vector
    * @param v3 Vector
    */
    private void populateQ352A4SubscrFeaBase(Vector v1, Vector v2, Vector v3)
    {
        Vector tempVector = new Vector(1);

        Iterator itr = v1.iterator();

        while(itr.hasNext())
        {
            Hashtable tempHT;
            Vector aVector;
            Vector bVector;
            EntityItem swProdStructItem = (EntityItem) itr.next();

            aVector = PokUtils.getAllLinkedEntities(swProdStructItem,"SWMODSTRUCT", "COFOOFMGMTGRP");
            //101 = Software
            //144 = FeatureCode
            //010 = N/A
            //010 = N/A
            tempHT = new Hashtable();
            tempHT.put("COFOOFMGCAT", "101");
            tempHT.put("COFOOFMGSUBCAT", "144");
            //tempHT.put("COFOOFMGGRP", "181");
            //tempHT.put("COFOOFMGSUBGRP", "010");
            aVector = PokUtils.getEntitiesWithMatchedAttr(aVector, tempHT);

            bVector = PokUtils.getAllLinkedEntities(aVector, "COFOWNSOOFOMG", "MODEL");
            tempHT.clear();
            //101 = Software
            //132 = SubscrFeature
            //150 = Base
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "132");
            tempHT.put("COFGRP", "150");
            //tempHT.put("COFSUBGRP", "010");
            bVector = PokUtils.getEntitiesWithMatchedAttr(bVector, tempHT);

            //Should be one and only one MODEL with classification SW-OptionalFeature-Base
            //if(bVector.size() > 0)
            for(int i = 0; i < bVector.size(); i++)
            {
                //EntityItem entityItem = (EntityItem) bVector.get(0);
                EntityItem entityItem = (EntityItem) bVector.get(i);
                if(!tempVector.contains(entityItem))
                {
                    tempVector.add(entityItem);
                }

                v3.add(new SWEntityItem_OIM(null, entityItem, swProdStructItem, null));
            }
        }//end of while(itr.hasNext())

        itr = tempVector.iterator();

        while(itr.hasNext())
        {
            Hashtable tempHT;
            Vector aVector;
            EntityItem modelItem = (EntityItem) itr.next();

            aVector = PokUtils.getAllLinkedEntities(modelItem, "MODELREL", "MODEL");
            tempHT = new Hashtable();
            //101 = Software
            //133 = Subscription
            //150 = Base
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "133");
            tempHT.put("COFGRP", "150");
            //tempHT.put("COFSUBGRP", "010");
            aVector = PokUtils.getEntitiesWithMatchedAttr(aVector, tempHT);

            //Should be one and only one MODEL with classification SW-Application-Base
            //if(aVector.size() > 0)
            for(int i = 0; i < aVector.size(); i++)
            {
                String key;
                String modelStr;
                //EntityItem entityItem = (EntityItem) aVector.get(0);
                EntityItem entityItem = (EntityItem) aVector.get(i);

                v2.add(new SWEntityItem_OIM(entityItem, modelItem, null, null));
                key = PokUtils.getAttributeFlagValue(entityItem, "MACHTYPEATR");
                if(null == key)
                {
                    key = "";
                }

                key = key + "    ";
                key = key.substring(0, 4);
                modelStr = PokUtils.getAttributeValue(entityItem, "MODELATR", "", "000");
                modelStr = modelStr + "   ";
                modelStr = modelStr.substring(0, 3);
                key = "B" + key + modelStr + entityItem.getKey();
                if(!SWAppBase.containsKey(key))
                {
                    SWAppBase.put(key, entityItem);
                }
            }
        }//end of while(itr.hasNext())
    }

    /********************************************************************************
    * populateQ352A4MaintenanceBase()
    *
    * @param v1 Vector
    * @param v2 Vector
    */
    private void populateQ352A4MaintenanceBase(Vector v1, Vector v2)
    {
        Iterator itr = v1.iterator();

        while(itr.hasNext())
        {
            Hashtable tempHT;
            Vector aVector;
            Vector bVector;
            EntityItem swProdStructItem = (EntityItem) itr.next();

            aVector = PokUtils.getAllLinkedEntities(swProdStructItem, "SWMODSTRUCT", "COFOOFMGMTGRP");
            //101 = Software
            //144 = FeatureCode
            //010 = N/A
            //010 = N/A
            tempHT = new Hashtable();
            tempHT.put("COFOOFMGCAT", "101");
            tempHT.put("COFOOFMGSUBCAT", "144");
            //tempHT.put("COFOOFMGGRP", "181");
            //tempHT.put("COFOOFMGSUBGRP", "010");
            aVector = PokUtils.getEntitiesWithMatchedAttr(aVector, tempHT);

            bVector = PokUtils.getAllLinkedEntities(aVector, "COFOWNSOOFOMG", "MODEL");
            tempHT.clear();
            //101 = Software
            //128 = Maintenance
            //150 = Base
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "128");
            tempHT.put("COFGRP", "150");
            //tempHT.put("COFSUBGRP", "010");
            bVector = PokUtils.getEntitiesWithMatchedAttr(bVector, tempHT);

            //Should be one and only one MODEL with classification SW-Application-Base
            //if(bVector.size() > 0)
            for(int i = 0; i < bVector.size(); i++)
            {
                String key;
                String modelStr;
                //EntityItem entityItem = (EntityItem) bVector.get(0);
                EntityItem entityItem = (EntityItem) bVector.get(i);

                v2.add(new SWEntityItem_OIM(entityItem, null, swProdStructItem, null));
                key = PokUtils.getAttributeFlagValue(entityItem, "MACHTYPEATR");
                if(null == key)
                {
                    key = "";
                }

                key = key + "    ";
                key = key.substring(0, 4);
                modelStr = PokUtils.getAttributeValue(entityItem, "MODELATR", "", "000");
                modelStr = modelStr + "   ";
                modelStr = modelStr.substring(0, 3);
                key = "C" + key + modelStr + entityItem.getKey();
                if(!SWAppBase.containsKey(key))
                {
                    SWAppBase.put(key, entityItem);
                }
            }
        }//end of while(itr.hasNext())
    }

    /********************************************************************************
    * populateQ352A4MaintFeaBase()
    *
    * @param v1 Vector
    * @param v2 Vector
    * @param v3 Vector
    */
    private void populateQ352A4MaintFeaBase(Vector v1, Vector v2, Vector v3)
    {
        Vector tempVector = new Vector(1);

        Iterator itr = v1.iterator();

        while(itr.hasNext())
        {
            Hashtable tempHT;
            Vector aVector;
            Vector bVector;
            EntityItem swProdStructItem = (EntityItem) itr.next();

            aVector = PokUtils.getAllLinkedEntities(swProdStructItem,"SWMODSTRUCT", "COFOOFMGMTGRP");
            //101 = Software
            //144 = FeatureCode
            //010 = N/A
            //010 = N/A
            tempHT = new Hashtable();
            tempHT.put("COFOOFMGCAT", "101");
            tempHT.put("COFOOFMGSUBCAT", "144");
            //tempHT.put("COFOOFMGGRP", "181");
            //tempHT.put("COFOOFMGSUBGRP", "010");
            aVector = PokUtils.getEntitiesWithMatchedAttr(aVector, tempHT);

            bVector = PokUtils.getAllLinkedEntities(aVector, "COFOWNSOOFOMG", "MODEL");
            tempHT.clear();
            //101 = Software
            //129 = MaintFeature
            //150 = Base
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "129");
            tempHT.put("COFGRP", "150");
            //tempHT.put("COFSUBGRP", "010");
            bVector = PokUtils.getEntitiesWithMatchedAttr(bVector, tempHT);

            //Should be one and only one MODEL with classification SW-OptionalFeature-Base
            //if(bVector.size() > 0)
            for(int i = 0; i < bVector.size(); i++)
            {
                //EntityItem entityItem = (EntityItem) bVector.get(0);
                EntityItem entityItem = (EntityItem) bVector.get(i);
                if(!tempVector.contains(entityItem))
                {
                    tempVector.add(entityItem);
                }

                v3.add(new SWEntityItem_OIM(null, entityItem, swProdStructItem, null));
            }
        }//end of while(itr.hasNext())

        itr = tempVector.iterator();

        while(itr.hasNext())
        {
            Hashtable tempHT;
            Vector aVector;
            EntityItem modelItem = (EntityItem) itr.next();

            aVector = PokUtils.getAllLinkedEntities(modelItem, "MODELREL", "MODEL");
            tempHT = new Hashtable();
            //101 = Software
            //128 = Maintenance
            //150 = Base
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "128");
            tempHT.put("COFGRP", "150");
            //tempHT.put("COFSUBGRP", "010");
            aVector = PokUtils.getEntitiesWithMatchedAttr(aVector, tempHT);

            //Should be one and only one MODEL with classification SW-Application-Base
            //if(aVector.size() > 0)
            for(int i = 0; i < aVector.size(); i++)
            {
                String key;
                String modelStr;
                //EntityItem entityItem = (EntityItem) aVector.get(0);
                EntityItem entityItem = (EntityItem) aVector.get(i);

                v2.add(new SWEntityItem_OIM(entityItem, modelItem, null, null));
                key = PokUtils.getAttributeFlagValue(entityItem, "MACHTYPEATR");
                if(null == key)
                {
                    key = "";
                }

                key = key + "    ";
                key = key.substring(0, 4);
                modelStr = PokUtils.getAttributeValue(entityItem, "MODELATR", "", "000");
                modelStr = modelStr + "   ";
                modelStr = modelStr.substring(0, 3);
                key = "C" + key + modelStr + entityItem.getKey();
                if(!SWAppBase.containsKey(key))
                {
                    SWAppBase.put(key, entityItem);
                }
            }
        }//end of while(itr.hasNext())
    }

    /********************************************************************************
    * populateQ352A4SupportBase()
    *
    * @param v1 Vector
    * @param v2 Vector
    */
    private void populateQ352A4SupportBase(Vector v1, Vector v2)
    {
        Iterator itr = v1.iterator();

        while(itr.hasNext())
        {
            Hashtable tempHT;
            Vector aVector;
            Vector bVector;
            EntityItem swProdStructItem = (EntityItem) itr.next();

            aVector = PokUtils.getAllLinkedEntities(swProdStructItem, "SWMODSTRUCT", "COFOOFMGMTGRP");
            //101 = Software
            //144 = FeatureCode
            //010 = N/A
            //010 = N/A
            tempHT = new Hashtable();
            tempHT.put("COFOOFMGCAT", "101");
            tempHT.put("COFOOFMGSUBCAT", "144");
            //tempHT.put("COFOOFMGGRP", "181");
            //tempHT.put("COFOOFMGSUBGRP", "010");
            aVector = PokUtils.getEntitiesWithMatchedAttr(aVector, tempHT);

            bVector = PokUtils.getAllLinkedEntities(aVector, "COFOWNSOOFOMG", "MODEL");
            tempHT.clear();
            //101 = Software
            //134 = Support
            //150 = Base
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "134");
            tempHT.put("COFGRP", "150");
            //tempHT.put("COFSUBGRP", "010");
            bVector = PokUtils.getEntitiesWithMatchedAttr(bVector, tempHT);

            //Should be one and only one MODEL with classification SW-Application-Base
            //if(bVector.size() > 0)
            for(int i = 0; i < bVector.size(); i++)
            {
                String key;
                String modelStr;
                //EntityItem entityItem = (EntityItem) bVector.get(0);
                EntityItem entityItem = (EntityItem) bVector.get(i);

                v2.add(new SWEntityItem_OIM(entityItem, null, swProdStructItem, null));
                key = PokUtils.getAttributeFlagValue(entityItem, "MACHTYPEATR");
                if(null == key)
                {
                    key = "";
                }

                key = key + "    ";
                key = key.substring(0, 4);
                modelStr = PokUtils.getAttributeValue(entityItem, "MODELATR", "", "000");
                modelStr = modelStr + "   ";
                modelStr = modelStr.substring(0, 3);
                key = "D" + key + modelStr + entityItem.getKey();
                if(!SWAppBase.containsKey(key))
                {
                    SWAppBase.put(key, entityItem);
                }
            }
        }//end of while(itr.hasNext())
    }

    /********************************************************************************
    * populateQ352A4SupportFeaBase()
    *
    * @param v1 Vector
    * @param v2 Vector
    * @param v3 Vector
    */
    private void populateQ352A4SupportFeaBase(Vector v1, Vector v2, Vector v3)
    {
        Vector tempVector = new Vector(1);

        Iterator itr = v1.iterator();

        while(itr.hasNext())
        {
            Hashtable tempHT;
            Vector aVector;
            Vector bVector;
            EntityItem swProdStructItem = (EntityItem) itr.next();

            aVector = PokUtils.getAllLinkedEntities(swProdStructItem,"SWMODSTRUCT", "COFOOFMGMTGRP");
            //101 = Software
            //144 = FeatureCode
            //010 = N/A
            //010 = N/A
            tempHT = new Hashtable();
            tempHT.put("COFOOFMGCAT", "101");
            tempHT.put("COFOOFMGSUBCAT", "144");
            //tempHT.put("COFOOFMGGRP", "181");
            //tempHT.put("COFOOFMGSUBGRP", "010");
            aVector = PokUtils.getEntitiesWithMatchedAttr(aVector, tempHT);

            bVector = PokUtils.getAllLinkedEntities(aVector, "COFOWNSOOFOMG", "MODEL");
            tempHT.clear();
            //101 = Software
            //135 = SupportFeature
            //150 = Base
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "135");
            tempHT.put("COFGRP", "150");
            //tempHT.put("COFSUBGRP", "010");
            bVector = PokUtils.getEntitiesWithMatchedAttr(bVector, tempHT);

            //Should be one and only one MODEL with classification SW-OptionalFeature-Base
            //if(bVector.size() > 0)
            for(int i = 0; i < bVector.size(); i++)
            {
                //EntityItem entityItem = (EntityItem) bVector.get(0);
                EntityItem entityItem = (EntityItem) bVector.get(i);
                if(!tempVector.contains(entityItem))
                {
                    tempVector.add(entityItem);
                }

                v3.add(new SWEntityItem_OIM(null, entityItem, swProdStructItem, null));
            }
        }//end of while(itr.hasNext())

        itr = tempVector.iterator();

        while(itr.hasNext())
        {
            Hashtable tempHT;
            Vector aVector;
            EntityItem modelItem = (EntityItem) itr.next();

            aVector = PokUtils.getAllLinkedEntities(modelItem, "MODELREL", "MODEL");
            tempHT = new Hashtable();
            //101 = Software
            //134 = Support
            //150 = Base
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "134");
            tempHT.put("COFGRP", "150");
            //tempHT.put("COFSUBGRP", "010");
            aVector = PokUtils.getEntitiesWithMatchedAttr(aVector, tempHT);

            //Should be one and only one MODEL with classification SW-Application-Base
            //if(aVector.size() > 0)
            for(int i = 0; i < aVector.size(); i++)
            {
                String key;
                String modelStr;
                //EntityItem entityItem = (EntityItem) aVector.get(0);
                EntityItem entityItem = (EntityItem) aVector.get(i);

                v2.add(new SWEntityItem_OIM(entityItem, modelItem, null, null));
                key = PokUtils.getAttributeFlagValue(entityItem, "MACHTYPEATR");
                if(null == key)
                {
                    key = "";
                }

                key = key + "    ";
                key = key.substring(0, 4);
                modelStr = PokUtils.getAttributeValue(entityItem, "MODELATR", "", "000");
                modelStr = modelStr + "   ";
                modelStr = modelStr.substring(0, 3);
                key = "D" + key + modelStr + entityItem.getKey();
                if(!SWAppBase.containsKey(key))
                {
                    SWAppBase.put(key, entityItem);
                }
            }
        }//end of while(itr.hasNext())
    }

    /********************************************************************************
    * populateQ352A4NCValueMetricAppBase()
    *
    * @param v1 Vector
    * @param v2 Vector
    */
    private void populateQ352A4NCValueMetricAppBase(Vector v1, Vector v2)
    {
        Hashtable tempHT = new Hashtable();
        Iterator itr = v1.iterator();

        log("In populateQ352A4NCValueMetricAppBase()");

        while(itr.hasNext())
        {
            Vector aVector;
            Vector bVector;
            Vector cVector;
            EntityItem swProdStructItem = (EntityItem) itr.next();
            EntityItem swFeature = (EntityItem) getUpLinkEntityItem(swProdStructItem, "SWFEATURE");
            log(swProdStructItem.toString());

            tempHT.clear();
            tempHT.put("SWFCCAT", "319"); //ValueMetric
            if(isEntityWithMatchedAttr(swFeature, tempHT))
            {
                aVector = PokUtils.getAllLinkedEntities(swProdStructItem, "SWMODSTRUCT", "COFOOFMGMTGRP");
                log("aVector.size() = " + aVector.size());
                tempHT.clear();
                //101 = Software
                //144 = FeatureCode
                //181 = Billing
                //010 = N/A
                tempHT.put("COFOOFMGCAT", "101");
                tempHT.put("COFOOFMGSUBCAT", "144");
                tempHT.put("COFOOFMGGRP", "181");
                //tempHT.put("COFOOFMGSUBGRP", "010");
                aVector = PokUtils.getEntitiesWithMatchedAttr(aVector, tempHT);
                log("aVector.size() = " + aVector.size());

                bVector = PokUtils.getAllLinkedEntities(aVector, "COFOWNSOOFOMG", "MODEL");
                log("bVector.size() = " + bVector.size());
                tempHT.clear();
                //101 = Software
                //127 = Application
                //151 = Initial
                //010 = N/A
                tempHT.put("COFCAT", "101");
                tempHT.put("COFSUBCAT", "127");
                tempHT.put("COFGRP", "151");
                //tempHT.put("COFSUBGRP", "010");
                bVector = PokUtils.getEntitiesWithMatchedAttr(bVector, tempHT);
                log("bVector.size() = " + bVector.size());

                cVector = PokUtils.getAllLinkedEntities(bVector, "MODELREL", "MODEL");
                log("cVector.size() = " + cVector.size());
                tempHT.clear();
                //101 = Software
                //127 = Application
                //150 = Base
                //010 = N/A
                tempHT.put("COFCAT", "101");
                tempHT.put("COFSUBCAT", "127");
                tempHT.put("COFGRP", "150");
                //tempHT.put("COFSUBGRP", "010");
                cVector = PokUtils.getEntitiesWithMatchedAttr(cVector, tempHT);
                log("cVector.size() = " + cVector.size());

                //Should be one and only one MODEL with classification SW-Application-Base
                //if(cVector.size() > 0)
                for(int i = 0; i < cVector.size(); i++)
                {
                    String key;
                    String modelStr;
                    //EntityItem entityItem = (EntityItem) cVector.get(0);
                    EntityItem entityItem = (EntityItem) cVector.get(i);

                    v2.add(new SWEntityItem_OIM(entityItem, null, swProdStructItem, null));
                    key = PokUtils.getAttributeFlagValue(entityItem, "MACHTYPEATR");
                    if(null == key)
                    {
                        key = "";
                    }

                    key = key + "    ";
                    key = key.substring(0, 4);
                    modelStr = PokUtils.getAttributeValue(entityItem, "MODELATR", "", "000");
                    modelStr = modelStr + "   ";
                    modelStr = modelStr.substring(0, 3);
                    key = "A" + key + modelStr + entityItem.getKey();
                    if(!SWAppBase.containsKey(key))
                    {
                        SWAppBase.put(key, entityItem);
                    }
                }//end of for(int i = 0; i < cVector.size(); i++)
            }//end of if(isEntityWithMatchedAttr(swFeature, tempHT))
        }//end of while(itr.hasNext())
    }

    /********************************************************************************
    * populateQ352A4NCValueMetricAppOptFeaBase
    *
    * @param v1 Vector
    * @param v2 Vector
    * @param v3 Vector
    */
    private void populateQ352A4NCValueMetricAppOptFeaBase(Vector v1, Vector v2, Vector v3)
    {
        Vector tempVector = new Vector(1);
        Hashtable tempHT = new Hashtable();
        Iterator itr = v1.iterator();

        log("In populateQ352A4NCValueMetricAppOptFeaBase()");

        while(itr.hasNext())
        {
            Vector aVector;
            Vector bVector;
            Vector cVector;
            EntityItem swProdStructItem = (EntityItem) itr.next();
            EntityItem swFeature = (EntityItem) getUpLinkEntityItem(swProdStructItem, "SWFEATURE");
            log(swProdStructItem.toString());

            tempHT.clear();
            tempHT.put("SWFCCAT", "319"); //ValueMetric
            if(isEntityWithMatchedAttr(swFeature, tempHT))
            {
                aVector = PokUtils.getAllLinkedEntities(swProdStructItem,"SWMODSTRUCT", "COFOOFMGMTGRP");
                log("aVector.size() = " + aVector.size());
                tempHT.clear();
                //101 = Software
                //144 = FeatureCode
                //181 = Billing
                //010 = N/A
                tempHT.put("COFOOFMGCAT", "101");
                tempHT.put("COFOOFMGSUBCAT", "144");
                tempHT.put("COFOOFMGGRP", "181");
                //tempHT.put("COFOOFMGSUBGRP", "010");
                aVector = PokUtils.getEntitiesWithMatchedAttr(aVector, tempHT);
                log("aVector.size() = " + aVector.size());

                bVector = PokUtils.getAllLinkedEntities(aVector, "COFOWNSOOFOMG", "MODEL");
                log("bVector.size() = " + bVector.size());
                tempHT.clear();
                //101 = Software
                //130 = OptionalFeature
                //151 = Initial
                //010 = N/A
                tempHT.put("COFCAT", "101");
                tempHT.put("COFSUBCAT", "130");
                tempHT.put("COFGRP", "151");
                //tempHT.put("COFSUBGRP", "010");
                bVector = PokUtils.getEntitiesWithMatchedAttr(bVector, tempHT);
                log("bVector.size() = " + bVector.size());

                cVector = PokUtils.getAllLinkedEntities(bVector, "MODELREL", "MODEL");
                log("cVector.size() = " + cVector.size());
                tempHT.clear();
                //101 = Software
                //130 = OptionalFeature
                //150 = Base
                //010 = N/A
                tempHT.put("COFCAT", "101");
                tempHT.put("COFSUBCAT", "130");
                tempHT.put("COFGRP", "150");
                //tempHT.put("COFSUBGRP", "010");
                cVector = PokUtils.getEntitiesWithMatchedAttr(cVector, tempHT);
                log("cVector.size() = " + cVector.size());

                //Should be one and only one MODEL with classification SW-OptionalFeature-Base
                //if(cVector.size() > 0)
                for(int i = 0; i < cVector.size(); i++)
                {
                    //EntityItem entityItem = (EntityItem) cVector.get(0);
                    EntityItem entityItem = (EntityItem) cVector.get(i);
                    if(!tempVector.contains(entityItem))
                    {
                        tempVector.add(entityItem);
                    }

                    v3.add(new SWEntityItem_OIM(null, entityItem, swProdStructItem, null));
                }//end of for(int i = 0; i < cVector.size(); i++)
            }//end of if(isEntityWithMatchedAttr(swFeature, tempHT))
        }//end of while(itr.hasNext())

        itr = tempVector.iterator();

        while(itr.hasNext())
        {
            Vector aVector;
            EntityItem modelItem = (EntityItem) itr.next();

            aVector = PokUtils.getAllLinkedEntities(modelItem, "MODELREL", "MODEL");
            log("aVector.size() = " + aVector.size());
            tempHT.clear();
            //101 = Software
            //127 = Application
            //150 = Base
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "127");
            tempHT.put("COFGRP", "150");
            //tempHT.put("COFSUBGRP", "010");
            aVector = PokUtils.getEntitiesWithMatchedAttr(aVector, tempHT);
            log("aVector.size() = " + aVector.size());

            //Should be one and only one MODEL with classification SW-Application-Base
            //if(aVector.size() > 0)
            for(int i = 0; i < aVector.size(); i++)
            {
                String key;
                String modelStr;
                //EntityItem entityItem = (EntityItem) aVector.get(0);
                EntityItem entityItem = (EntityItem) aVector.get(i);

                v2.add(new SWEntityItem_OIM(entityItem, modelItem, null, null));
                key = PokUtils.getAttributeFlagValue(entityItem, "MACHTYPEATR");
                if(null == key)
                {
                    key = "";
                }

                key = key + "    ";
                key = key.substring(0, 4);
                modelStr = PokUtils.getAttributeValue(entityItem, "MODELATR", "", "000");
                modelStr = modelStr + "   ";
                modelStr = modelStr.substring(0, 3);
                key = "A" + key + modelStr + entityItem.getKey();
                if(!SWAppBase.containsKey(key))
                {
                    SWAppBase.put(key, entityItem);
                }
            }
        }//end of while(itr.hasNext())
    }

    /********************************************************************************
    * populateQ352A4NCValueMetricSubscriptionBase()
    *
    * @param v1 Vector
    * @param v2 Vector
    */
    private void populateQ352A4NCValueMetricSubscriptionBase(Vector v1, Vector v2)
    {
        Hashtable tempHT = new Hashtable();
        Iterator itr = v1.iterator();

        log("In populateQ352A4NCValueMetricSubscriptionBase()");

        while(itr.hasNext())
        {
            Vector aVector;
            Vector bVector;
            Vector cVector;
            EntityItem swProdStructItem = (EntityItem) itr.next();
            EntityItem swFeature = (EntityItem) getUpLinkEntityItem(swProdStructItem, "SWFEATURE");
            log(swProdStructItem.toString());

            tempHT.clear();
            tempHT.put("SWFCCAT", "319"); //ValueMetric
            if(isEntityWithMatchedAttr(swFeature, tempHT))
            {
                aVector = PokUtils.getAllLinkedEntities(swProdStructItem, "SWMODSTRUCT", "COFOOFMGMTGRP");
                log("aVector.size() = " + aVector.size());
                tempHT.clear();
                //101 = Software
                //144 = FeatureCode
                //181 = Billing
                //010 = N/A
                tempHT.put("COFOOFMGCAT", "101");
                tempHT.put("COFOOFMGSUBCAT", "144");
                tempHT.put("COFOOFMGGRP", "181");
                //tempHT.put("COFOOFMGSUBGRP", "010");
                aVector = PokUtils.getEntitiesWithMatchedAttr(aVector, tempHT);
                log("aVector.size() = " + aVector.size());

                bVector = PokUtils.getAllLinkedEntities(aVector, "COFOWNSOOFOMG", "MODEL");
                log("bVector.size() = " + bVector.size());
                tempHT.clear();
                //101 = Software
                //133 = Subscription
                //151 = Initial
                //010 = N/A
                tempHT.put("COFCAT", "101");
                tempHT.put("COFSUBCAT", "133");
                tempHT.put("COFGRP", "151");
                //tempHT.put("COFSUBGRP", "010");
                bVector = PokUtils.getEntitiesWithMatchedAttr(bVector, tempHT);
                log("bVector.size() = " + bVector.size());

                cVector = PokUtils.getAllLinkedEntities(bVector, "MODELREL", "MODEL");
                log("cVector.size() = " + cVector.size());
                tempHT.clear();
                //101 = Software
                //133 = Subscription
                //150 = Base
                //010 = N/A
                tempHT.put("COFCAT", "101");
                tempHT.put("COFSUBCAT", "133");
                tempHT.put("COFGRP", "150");
                //tempHT.put("COFSUBGRP", "010");
                cVector = PokUtils.getEntitiesWithMatchedAttr(cVector, tempHT);
                log("cVector.size() = " + cVector.size());

                //Should be one and only one MODEL with classification SW-Application-Base
                //if(cVector.size() > 0)
                for(int i = 0; i < cVector.size(); i++)
                {
                    String key;
                    String modelStr;
                    //EntityItem entityItem = (EntityItem) cVector.get(0);
                    EntityItem entityItem = (EntityItem) cVector.get(i);

                    v2.add(new SWEntityItem_OIM(entityItem, null, swProdStructItem, null));
                    key = PokUtils.getAttributeFlagValue(entityItem, "MACHTYPEATR");
                    if(null == key)
                    {
                        key = "";
                    }

                    key = key + "    ";
                    key = key.substring(0, 4);
                    modelStr = PokUtils.getAttributeValue(entityItem, "MODELATR", "", "000");
                    modelStr = modelStr + "   ";
                    modelStr = modelStr.substring(0, 3);
                    key = "B" + key + modelStr + entityItem.getKey();
                    if(!SWAppBase.containsKey(key))
                    {
                        SWAppBase.put(key, entityItem);
                    }
                }//end of for(int i = 0; i < cVector.size(); i++)
            }//end of if(isEntityWithMatchedAttr(swFeature, tempHT))
        }//end of while(itr.hasNext())
    }

    /********************************************************************************
    * populateQ352A4NCValueMetricSubscrFeaBase
    *
    * @param v1 Vector
    * @param v2 Vector
    * @param v3 Vector
    */
    private void populateQ352A4NCValueMetricSubscrFeaBase(Vector v1, Vector v2, Vector v3)
    {
        Vector tempVector = new Vector(1);
        Hashtable tempHT = new Hashtable();
        Iterator itr = v1.iterator();

        log("In populateQ352A4NCValueMetricSubscrFeaBase()");

        while(itr.hasNext())
        {
            Vector aVector;
            Vector bVector;
            Vector cVector;
            EntityItem swProdStructItem = (EntityItem) itr.next();
            EntityItem swFeature = (EntityItem) getUpLinkEntityItem(swProdStructItem, "SWFEATURE");
            log(swProdStructItem.toString());

            tempHT.clear();
            tempHT.put("SWFCCAT", "319"); //ValueMetric
            if(isEntityWithMatchedAttr(swFeature, tempHT))
            {
                aVector = PokUtils.getAllLinkedEntities(swProdStructItem,"SWMODSTRUCT", "COFOOFMGMTGRP");
                log("aVector.size() = " + aVector.size());
                tempHT.clear();
                //101 = Software
                //144 = FeatureCode
                //181 = Billing
                //010 = N/A
                tempHT.put("COFOOFMGCAT", "101");
                tempHT.put("COFOOFMGSUBCAT", "144");
                tempHT.put("COFOOFMGGRP", "181");
                //tempHT.put("COFOOFMGSUBGRP", "010");
                aVector = PokUtils.getEntitiesWithMatchedAttr(aVector, tempHT);
                log("aVector.size() = " + aVector.size());

                bVector = PokUtils.getAllLinkedEntities(aVector, "COFOWNSOOFOMG", "MODEL");
                log("bVector.size() = " + bVector.size());
                tempHT.clear();
                //101 = Software
                //132 = SubscrFeature
                //151 = Initial
                //010 = N/A
                tempHT.put("COFCAT", "101");
                tempHT.put("COFSUBCAT", "132");
                tempHT.put("COFGRP", "151");
                //tempHT.put("COFSUBGRP", "010");
                bVector = PokUtils.getEntitiesWithMatchedAttr(bVector, tempHT);
                log("bVector.size() = " + bVector.size());

                cVector = PokUtils.getAllLinkedEntities(bVector, "MODELREL", "MODEL");
                log("cVector.size() = " + cVector.size());
                tempHT.clear();
                //101 = Software
                //132 = SubscrFeature
                //150 = Base
                //010 = N/A
                tempHT.put("COFCAT", "101");
                tempHT.put("COFSUBCAT", "132");
                tempHT.put("COFGRP", "150");
                //tempHT.put("COFSUBGRP", "010");
                cVector = PokUtils.getEntitiesWithMatchedAttr(cVector, tempHT);
                log("cVector.size() = " + cVector.size());

                //Should be one and only one MODEL with classification SW-OptionalFeature-Base
                //if(cVector.size() > 0)
                for(int i = 0; i < cVector.size(); i++)
                {
                    //EntityItem entityItem = (EntityItem) cVector.get(0);
                    EntityItem entityItem = (EntityItem) cVector.get(i);
                    if(!tempVector.contains(entityItem))
                    {
                        tempVector.add(entityItem);
                    }

                    v3.add(new SWEntityItem_OIM(null, entityItem, swProdStructItem, null));
                }//end of for(int i = 0; i < cVector.size(); i++)
            }//end of if(isEntityWithMatchedAttr(swFeature, tempHT))
        }//end of while(itr.hasNext())

        itr = tempVector.iterator();

        while(itr.hasNext())
        {
            Vector aVector;
            EntityItem modelItem = (EntityItem) itr.next();

            aVector = PokUtils.getAllLinkedEntities(modelItem, "MODELREL", "MODEL");
            log("aVector.size() = " + aVector.size());
            tempHT.clear();
            //101 = Software
            //133 = Subscription
            //150 = Base
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "133");
            tempHT.put("COFGRP", "150");
            //tempHT.put("COFSUBGRP", "010");
            aVector = PokUtils.getEntitiesWithMatchedAttr(aVector, tempHT);
            log("aVector.size() = " + aVector.size());

            //Should be one and only one MODEL with classification SW-Application-Base
            //if(aVector.size() > 0)
            for(int i = 0; i < aVector.size(); i++)
            {
                String key;
                String modelStr;
                //EntityItem entityItem = (EntityItem) aVector.get(0);
                EntityItem entityItem = (EntityItem) aVector.get(i);

                v2.add(new SWEntityItem_OIM(entityItem, modelItem, null, null));
                key = PokUtils.getAttributeFlagValue(entityItem, "MACHTYPEATR");
                if(null == key)
                {
                    key = "";
                }

                key = key + "    ";
                key = key.substring(0, 4);
                modelStr = PokUtils.getAttributeValue(entityItem, "MODELATR", "", "000");
                modelStr = modelStr + "   ";
                modelStr = modelStr.substring(0, 3);
                key = "B" + key + modelStr + entityItem.getKey();
                if(!SWAppBase.containsKey(key))
                {
                    SWAppBase.put(key, entityItem);
                }
            }
        }//end of while(itr.hasNext())
    }

    /********************************************************************************
    * populateQ352A4NCValueMetricMaintenanceBase
    *
    * @param v1 Vector
    * @param v2 Vector
    */
    private void populateQ352A4NCValueMetricMaintenanceBase(Vector v1, Vector v2)
    {
        Hashtable tempHT = new Hashtable();
        Iterator itr = v1.iterator();

        log("In populateQ352A4NCValueMetricMaintenanceBase()");

        while(itr.hasNext())
        {
            Vector aVector;
            Vector bVector;
            Vector cVector;
            EntityItem swProdStructItem = (EntityItem) itr.next();
            EntityItem swFeature = (EntityItem) getUpLinkEntityItem(swProdStructItem, "SWFEATURE");
            log(swProdStructItem.toString());

            tempHT.clear();
            tempHT.put("SWFCCAT", "319"); //ValueMetric
            if(isEntityWithMatchedAttr(swFeature, tempHT))
            {
                aVector = PokUtils.getAllLinkedEntities(swProdStructItem, "SWMODSTRUCT", "COFOOFMGMTGRP");
                log("aVector.size() = " + aVector.size());
                tempHT.clear();
                //101 = Software
                //144 = FeatureCode
                //181 = Billing
                //010 = N/A
                tempHT.put("COFOOFMGCAT", "101");
                tempHT.put("COFOOFMGSUBCAT", "144");
                tempHT.put("COFOOFMGGRP", "181");
                //tempHT.put("COFOOFMGSUBGRP", "010");
                aVector = PokUtils.getEntitiesWithMatchedAttr(aVector, tempHT);
                log("aVector.size() = " + aVector.size());

                bVector = PokUtils.getAllLinkedEntities(aVector, "COFOWNSOOFOMG", "MODEL");
                log("bVector.size() = " + bVector.size());
                tempHT.clear();
                //101 = Software
                //128 = Maintenance
                //151 = Initial
                //010 = N/A
                tempHT.put("COFCAT", "101");
                tempHT.put("COFSUBCAT", "128");
                tempHT.put("COFGRP", "151");
                //tempHT.put("COFSUBGRP", "010");
                bVector = PokUtils.getEntitiesWithMatchedAttr(bVector, tempHT);
                log("bVector.size() = " + bVector.size());

                cVector = PokUtils.getAllLinkedEntities(bVector, "MODELREL", "MODEL");
                log("cVector.size() = " + cVector.size());
                tempHT.clear();
                //101 = Software
                //128 = Maintenance
                //150 = Base
                //010 = N/A
                tempHT.put("COFCAT", "101");
                tempHT.put("COFSUBCAT", "128");
                tempHT.put("COFGRP", "150");
                //tempHT.put("COFSUBGRP", "010");
                cVector = PokUtils.getEntitiesWithMatchedAttr(cVector, tempHT);
                log("cVector.size() = " + cVector.size());

                //Should be one and only one MODEL with classification SW-Application-Base
                //if(cVector.size() > 0)
                for(int i = 0; i < cVector.size(); i++)
                {
                    String key;
                    String modelStr;
                    //EntityItem entityItem = (EntityItem) cVector.get(0);
                    EntityItem entityItem = (EntityItem) cVector.get(i);

                    v2.add(new SWEntityItem_OIM(entityItem, null, swProdStructItem, null));
                    key = PokUtils.getAttributeFlagValue(entityItem, "MACHTYPEATR");
                    if(null == key)
                    {
                        key = "";
                    }

                    key = key + "    ";
                    key = key.substring(0, 4);
                    modelStr = PokUtils.getAttributeValue(entityItem, "MODELATR", "", "000");
                    modelStr = modelStr + "   ";
                    modelStr = modelStr.substring(0, 3);
                    key = "C" + key + modelStr + entityItem.getKey();
                    if(!SWAppBase.containsKey(key))
                    {
                        SWAppBase.put(key, entityItem);
                    }
                }//end of for(int i = 0; i < cVector.size(); i++)
            }//end of if(isEntityWithMatchedAttr(swFeature, tempHT))
        }//end of while(itr.hasNext())
    }

    /********************************************************************************
    * populateQ352A4NCValueMetricMaintFeaBase
    *
    * @param v1 Vector
    * @param v2 Vector
    * @param v3 Vector
    */
    private void populateQ352A4NCValueMetricMaintFeaBase(Vector v1, Vector v2, Vector v3)
    {
        Vector tempVector = new Vector(1);
        Hashtable tempHT = new Hashtable();
        Iterator itr = v1.iterator();

        log("In populateQ352A4NCValueMetricMaintFeaBase()");

        while(itr.hasNext())
        {
            Vector aVector;
            Vector bVector;
            Vector cVector;
            EntityItem swProdStructItem = (EntityItem) itr.next();
            EntityItem swFeature = (EntityItem) getUpLinkEntityItem(swProdStructItem, "SWFEATURE");
            log(swProdStructItem.toString());

            tempHT.clear();
            tempHT.put("SWFCCAT", "319"); //ValueMetric
            if(isEntityWithMatchedAttr(swFeature, tempHT))
            {
                aVector = PokUtils.getAllLinkedEntities(swProdStructItem,"SWMODSTRUCT", "COFOOFMGMTGRP");
                log("aVector.size() = " + aVector.size());
                tempHT.clear();
                //101 = Software
                //144 = FeatureCode
                //181 = Billing
                //010 = N/A
                tempHT.put("COFOOFMGCAT", "101");
                tempHT.put("COFOOFMGSUBCAT", "144");
                tempHT.put("COFOOFMGGRP", "181");
                //tempHT.put("COFOOFMGSUBGRP", "010");
                aVector = PokUtils.getEntitiesWithMatchedAttr(aVector, tempHT);
                log("aVector.size() = " + aVector.size());

                bVector = PokUtils.getAllLinkedEntities(aVector, "COFOWNSOOFOMG", "MODEL");
                log("bVector.size() = " + bVector.size());
                tempHT.clear();
                //101 = Software
                //129 = MaintFeature
                //151 = Initial
                //010 = N/A
                tempHT.put("COFCAT", "101");
                tempHT.put("COFSUBCAT", "129");
                tempHT.put("COFGRP", "151");
                //tempHT.put("COFSUBGRP", "010");
                bVector = PokUtils.getEntitiesWithMatchedAttr(bVector, tempHT);
                log("bVector.size() = " + bVector.size());

                cVector = PokUtils.getAllLinkedEntities(bVector, "MODELREL", "MODEL");
                log("cVector.size() = " + cVector.size());
                tempHT.clear();
                //101 = Software
                //129 = MaintFeature
                //150 = Base
                //010 = N/A
                tempHT.put("COFCAT", "101");
                tempHT.put("COFSUBCAT", "129");
                tempHT.put("COFGRP", "150");
                //tempHT.put("COFSUBGRP", "010");
                cVector = PokUtils.getEntitiesWithMatchedAttr(cVector, tempHT);
                log("cVector.size() = " + cVector.size());

                //Should be one and only one MODEL with classification SW-OptionalFeature-Base
                //if(cVector.size() > 0)
                for(int i = 0; i < cVector.size(); i++)
                {
                    //EntityItem entityItem = (EntityItem) cVector.get(0);
                    EntityItem entityItem = (EntityItem) cVector.get(i);
                    if(!tempVector.contains(entityItem))
                    {
                        tempVector.add(entityItem);
                    }

                    v3.add(new SWEntityItem_OIM(null, entityItem, swProdStructItem, null));
                }//end of for(int i = 0; i < cVector.size(); i++)
            }//end of if(isEntityWithMatchedAttr(swFeature, tempHT))
        }//end of while(itr.hasNext())

        itr = tempVector.iterator();

        while(itr.hasNext())
        {
            Vector aVector;
            EntityItem modelItem = (EntityItem) itr.next();

            aVector = PokUtils.getAllLinkedEntities(modelItem, "MODELREL", "MODEL");
            log("aVector.size() = " + aVector.size());
            tempHT.clear();
            //101 = Software
            //128 = Maintenance
            //150 = Base
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "128");
            tempHT.put("COFGRP", "150");
            //tempHT.put("COFSUBGRP", "010");
            aVector = PokUtils.getEntitiesWithMatchedAttr(aVector, tempHT);
            log("aVector.size() = " + aVector.size());

            //Should be one and only one MODEL with classification SW-Application-Base
            //if(aVector.size() > 0)
            for(int i = 0; i < aVector.size(); i++)
            {
                String key;
                String modelStr;
                //EntityItem entityItem = (EntityItem) aVector.get(0);
                EntityItem entityItem = (EntityItem) aVector.get(i);

                v2.add(new SWEntityItem_OIM(entityItem, modelItem, null, null));
                key = PokUtils.getAttributeFlagValue(entityItem, "MACHTYPEATR");
                if(null == key)
                {
                    key = "";
                }

                key = key + "    ";
                key = key.substring(0, 4);
                modelStr = PokUtils.getAttributeValue(entityItem, "MODELATR", "", "000");
                modelStr = modelStr + "   ";
                modelStr = modelStr.substring(0, 3);
                key = "C" + key + modelStr + entityItem.getKey();
                if(!SWAppBase.containsKey(key))
                {
                    SWAppBase.put(key, entityItem);
                }
            }
        }//end of while(itr.hasNext())
    }

    /********************************************************************************
    * populateQ352A4NCValueMetricSupportBase
    *
    * @param v1 Vector
    * @param v2 Vector
    */
    private void populateQ352A4NCValueMetricSupportBase(Vector v1, Vector v2)
    {
        Hashtable tempHT = new Hashtable();
        Iterator itr = v1.iterator();

        log("In populateQ352A4NCValueMetricSupportBase()");

        while(itr.hasNext())
        {
            Vector aVector;
            Vector bVector;
            Vector cVector;
            EntityItem swProdStructItem = (EntityItem) itr.next();
            EntityItem swFeature = (EntityItem) getUpLinkEntityItem(swProdStructItem, "SWFEATURE");
            log(swProdStructItem.toString());

            tempHT.clear();
            tempHT.put("SWFCCAT", "319"); //ValueMetric
            if(isEntityWithMatchedAttr(swFeature, tempHT))
            {
                aVector = PokUtils.getAllLinkedEntities(swProdStructItem, "SWMODSTRUCT", "COFOOFMGMTGRP");
                log("aVector.size() = " + aVector.size());
                tempHT.clear();
                //101 = Software
                //144 = FeatureCode
                //181 = Billing
                //010 = N/A
                tempHT.put("COFOOFMGCAT", "101");
                tempHT.put("COFOOFMGSUBCAT", "144");
                tempHT.put("COFOOFMGGRP", "181");
                //tempHT.put("COFOOFMGSUBGRP", "010");
                aVector = PokUtils.getEntitiesWithMatchedAttr(aVector, tempHT);
                log("aVector.size() = " + aVector.size());

                bVector = PokUtils.getAllLinkedEntities(aVector, "COFOWNSOOFOMG", "MODEL");
                log("bVector.size() = " + bVector.size());
                tempHT.clear();
                //101 = Software
                //134 = Support
                //151 = Initial
                //010 = N/A
                tempHT.put("COFCAT", "101");
                tempHT.put("COFSUBCAT", "134");
                tempHT.put("COFGRP", "151");
                //tempHT.put("COFSUBGRP", "010");
                bVector = PokUtils.getEntitiesWithMatchedAttr(bVector, tempHT);
                log("bVector.size() = " + bVector.size());

                cVector = PokUtils.getAllLinkedEntities(bVector, "MODELREL", "MODEL");
                log("cVector.size() = " + cVector.size());
                tempHT.clear();
                //101 = Software
                //134 = Suport
                //150 = Base
                //010 = N/A
                tempHT.put("COFCAT", "101");
                tempHT.put("COFSUBCAT", "134");
                tempHT.put("COFGRP", "150");
                //tempHT.put("COFSUBGRP", "010");
                cVector = PokUtils.getEntitiesWithMatchedAttr(cVector, tempHT);
                log("cVector.size() = " + cVector.size());

                //Should be one and only one MODEL with classification SW-Application-Base
                //if(cVector.size() > 0)
                for(int i = 0; i < cVector.size(); i++)
                {
                    String key;
                    String modelStr;
                    //EntityItem entityItem = (EntityItem) cVector.get(0);
                    EntityItem entityItem = (EntityItem) cVector.get(i);

                    v2.add(new SWEntityItem_OIM(entityItem, null, swProdStructItem, null));
                    key = PokUtils.getAttributeFlagValue(entityItem, "MACHTYPEATR");
                    if(null == key)
                    {
                        key = "";
                    }

                    key = key + "    ";
                    key = key.substring(0, 4);
                    modelStr = PokUtils.getAttributeValue(entityItem, "MODELATR", "", "000");
                    modelStr = modelStr + "   ";
                    modelStr = modelStr.substring(0, 3);
                    key = "D" + key + modelStr + entityItem.getKey();
                    if(!SWAppBase.containsKey(key))
                    {
                        SWAppBase.put(key, entityItem);
                    }
                }//end of for(int i = 0; i < cVector.size(); i++)
            }//end of if(isEntityWithMatchedAttr(swFeature, tempHT))
        }//end of while(itr.hasNext())
    }

    /********************************************************************************
    * populateQ352A4NCValueMetricSupportFeaBase
    *
    * @param v1 Vector
    * @param v2 Vector
    * @param v3 Vector
    */
    private void populateQ352A4NCValueMetricSupportFeaBase(Vector v1, Vector v2, Vector v3)
    {
        Vector tempVector = new Vector(1);
        Hashtable tempHT = new Hashtable();
        Iterator itr = v1.iterator();

        log("In populateQ352A4NCValueMetricSupportFeaBase()");

        while(itr.hasNext())
        {
            Vector aVector;
            Vector bVector;
            Vector cVector;
            EntityItem swProdStructItem = (EntityItem) itr.next();
            EntityItem swFeature = (EntityItem) getUpLinkEntityItem(swProdStructItem, "SWFEATURE");
            log(swProdStructItem.toString());

            tempHT.clear();
            tempHT.put("SWFCCAT", "319"); //ValueMetric
            if(isEntityWithMatchedAttr(swFeature, tempHT))
            {
                aVector = PokUtils.getAllLinkedEntities(swProdStructItem,"SWMODSTRUCT", "COFOOFMGMTGRP");
                log("aVector.size() = " + aVector.size());
                tempHT.clear();
                //101 = Software
                //144 = FeatureCode
                //181 = Billing
                //010 = N/A
                tempHT.put("COFOOFMGCAT", "101");
                tempHT.put("COFOOFMGSUBCAT", "144");
                tempHT.put("COFOOFMGGRP", "181");
                //tempHT.put("COFOOFMGSUBGRP", "010");
                aVector = PokUtils.getEntitiesWithMatchedAttr(aVector, tempHT);
                log("aVector.size() = " + aVector.size());

                bVector = PokUtils.getAllLinkedEntities(aVector, "COFOWNSOOFOMG", "MODEL");
                log("bVector.size() = " + bVector.size());
                tempHT.clear();
                //101 = Software
                //135 = SupportFeature
                //151 = Initial
                //010 = N/A
                tempHT.put("COFCAT", "101");
                tempHT.put("COFSUBCAT", "135");
                tempHT.put("COFGRP", "151");
                //tempHT.put("COFSUBGRP", "010");
                bVector = PokUtils.getEntitiesWithMatchedAttr(bVector, tempHT);
                log("bVector.size() = " + bVector.size());

                cVector = PokUtils.getAllLinkedEntities(bVector, "MODELREL", "MODEL");
                log("cVector.size() = " + cVector.size());
                tempHT.clear();
                //101 = Software
                //135 = SupportFeature
                //150 = Base
                //010 = N/A
                tempHT.put("COFCAT", "101");
                tempHT.put("COFSUBCAT", "135");
                tempHT.put("COFGRP", "150");
                //tempHT.put("COFSUBGRP", "010");
                cVector = PokUtils.getEntitiesWithMatchedAttr(cVector, tempHT);
                log("cVector.size() = " + cVector.size());

                //Should be one and only one MODEL with classification SW-OptionalFeature-Base
                //if(cVector.size() > 0)
                for(int i = 0; i < cVector.size(); i++)
                {
                    //EntityItem entityItem = (EntityItem) cVector.get(0);
                    EntityItem entityItem = (EntityItem) cVector.get(i);
                    if(!tempVector.contains(entityItem))
                    {
                        tempVector.add(entityItem);
                    }

                    v3.add(new SWEntityItem_OIM(null, entityItem, swProdStructItem, null));
                }//end of for(int i = 0; i < cVector.size(); i++)
            }//end of if(isEntityWithMatchedAttr(swFeature, tempHT))
        }//end of while(itr.hasNext())

        itr = tempVector.iterator();

        while(itr.hasNext())
        {
            Vector aVector;
            EntityItem modelItem = (EntityItem) itr.next();

            aVector = PokUtils.getAllLinkedEntities(modelItem, "MODELREL", "MODEL");
            log("aVector.size() = " + aVector.size());
            tempHT.clear();
            //101 = Software
            //134 = Support
            //150 = Base
            //010 = N/A
            tempHT.put("COFCAT", "101");
            tempHT.put("COFSUBCAT", "134");
            tempHT.put("COFGRP", "150");
            //tempHT.put("COFSUBGRP", "010");
            aVector = PokUtils.getEntitiesWithMatchedAttr(aVector, tempHT);
            log("aVector.size() = " + aVector.size());

            //Should be one and only one MODEL with classification SW-Application-Base
            //if(aVector.size() > 0)
            for(int i = 0; i < aVector.size(); i++)
            {
                String key;
                String modelStr;
                //EntityItem entityItem = (EntityItem) aVector.get(0);
                EntityItem entityItem = (EntityItem) aVector.get(i);

                v2.add(new SWEntityItem_OIM(entityItem, modelItem, null, null));
                key = PokUtils.getAttributeFlagValue(entityItem, "MACHTYPEATR");
                if(null == key)
                {
                    key = "";
                }

                key = key + "    ";
                key = key.substring(0, 4);
                modelStr = PokUtils.getAttributeValue(entityItem, "MODELATR", "", "000");
                modelStr = modelStr + "   ";
                modelStr = modelStr.substring(0, 3);
                key = "D" + key + modelStr + entityItem.getKey();
                if(!SWAppBase.containsKey(key))
                {
                    SWAppBase.put(key, entityItem);
                }
            }
        }//end of while(itr.hasNext())
    }

    /********************************************************************************
    * populateQ352B1()
    *
    */
    private void populateQ352B1()
    {
        Hashtable tempHT = new Hashtable();
        Vector bVector;
        Vector cVector;
        Vector dVector;
        Vector eVector;
        //Constants.XR_OOFCAT_101 = Software
        //Constants.XR_OOFSUBCAT_500 = FeatureCode
        //Constants.XR_OOFGRP_405 = N/A
        //Constants.XR_OOFSUBGRP_405 = N/A
        //tempHT.put("OOFCAT", Constants.XR_OOFCAT_101);
        //tempHT.put("OOFSUBCAT", Constants.XR_OOFSUBCAT_500);
        //tempHT.put("OOFGRP", Constants.XR_OOFGRP_405);
        //tempHT.put("OOFSUBGRP", Constants.XR_OOFSUBGRP_405);
        //Vector aVector = PokUtils.getEntitiesWithMatchedAttr(oofV, tempHT);

        bVector = PokUtils.getAllLinkedEntities(SWProdStructV, "SWMODSTRUCT", "COFOOFMGMTGRP");
        tempHT.clear();
        //101 = Software
        //144 = FeatureCode
        //181 = Billing
        //010 = N/A
        tempHT.put("COFOOFMGCAT", "101");
        tempHT.put("COFOOFMGSUBCAT", "144");
        //tempHT.put("COFOOFMGGRP", "181");
        //tempHT.put("COFOOFMGSUBGRP", "010");
        bVector = PokUtils.getEntitiesWithMatchedAttr(bVector, tempHT);

        cVector = PokUtils.getAllLinkedEntities(bVector, "COFOWNSOOFOMG", "MODEL");
        tempHT.clear();
        //101 = Software
        //127 = Application
        //150 = Base
        //010 = N/A
        tempHT.put("COFCAT", "101");
        tempHT.put("COFSUBCAT", "127");
        tempHT.put("COFGRP", "150");
        tempHT.put("COFSUBGRP", "010");
        Q352B1V1 = PokUtils.getEntitiesWithMatchedAttr(cVector, tempHT);

        tempHT.clear();
        //101 = Software
        //130 = OptionalFeature
        //150 = Base
        //010 = N/A
        tempHT.put("COFCAT", "101");
        tempHT.put("COFSUBCAT", "130");
        tempHT.put("COFGRP", "150");
        tempHT.put("COFSUBGRP", "010");
        Q352B1V2 = PokUtils.getEntitiesWithMatchedAttr(cVector, tempHT);

        tempHT.clear();
        //101 = Software
        //127 = Application
        //151 = Initial
        //010 = N/A
        tempHT.put("COFCAT", "101");
        tempHT.put("COFSUBCAT", "127");
        tempHT.put("COFGRP", "151");
        tempHT.put("COFSUBGRP", "010");
        dVector = PokUtils.getEntitiesWithMatchedAttr(cVector, tempHT);

        dVector = PokUtils.getAllLinkedEntities(dVector, "MODELREL", "MODEL");
        tempHT.clear();
        //101 = Software
        //127 = Application
        //150 = Base
        //010 = N/A
        tempHT.put("COFCAT", "101");
        tempHT.put("COFSUBCAT", "127");
        tempHT.put("COFGRP", "150");
        tempHT.put("COFSUBGRP", "010");
        Q352B1V3 = PokUtils.getEntitiesWithMatchedAttr(dVector, tempHT);
        log("Q352B1V3.size() = " + Q352B1V3.size());

        tempHT.clear();
        //101 = Software
        //130 = OptionalFeature
        //151 = Initial
        //010 = N/A
        tempHT.put("COFCAT", "101");
        tempHT.put("COFSUBCAT", "130");
        tempHT.put("COFGRP", "151");
        tempHT.put("COFSUBGRP", "010");
        eVector = PokUtils.getEntitiesWithMatchedAttr(cVector, tempHT);

        eVector = PokUtils.getAllLinkedEntities(eVector, "MODELREL", "MODEL");
        tempHT.clear();
        //101 = Software
        //130 = OptionalFeature
        //150 = Base
        //010 = N/A
        tempHT.put("COFCAT", "101");
        tempHT.put("COFSUBCAT", "130");
        tempHT.put("COFGRP", "150");
        tempHT.put("COFSUBGRP", "010");
        Q352B1V4 = PokUtils.getEntitiesWithMatchedAttr(eVector, tempHT);
        log("Q352B1V4.size() = " + Q352B1V4.size());
    }

    /********************************************************************************
    * checkUpgradeFrom()
    *
    * @param aVector Vector
    * @param modelEI EntityItem
    * @param swProdStruct EntityItem
    * @returns boolean
    */
    private boolean checkUpgradeFrom(Vector aVector, EntityItem modelEI, EntityItem swProdStruct)
    {
        boolean result = false;

        Iterator itr = aVector.iterator();

        while(itr.hasNext())
        {
            SWEntityItem_OIM entityItem = (SWEntityItem_OIM)itr.next();

            result = entityItem.containModelBase(modelEI) && entityItem.containFromSWProdStruct(swProdStruct);

            if(true == result)
            {
                break;
            }
        }

        return result;
    }

    /********************************************************************************
    * Display report
    *
    *
    * @returns StringBuffer
    */
    public StringBuffer display()
    {
        Set set;
        Iterator itr;
        Date today = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");

        sb.append("<h1>SW RFA Q448 Extract - " + annNumber + "</h1>" + NEWLINE);
        sb.append("* Created on " + sdf1.format(today) + " at " + sdf2.format(today) + NEWLINE);
        sbQ523.append("<h1>SW RFA Q523 Extract - " + annNumber + "</h1>" + NEWLINE);
        sbQ523.append("* Created on " + sdf1.format(today) + " at " + sdf2.format(today) + NEWLINE);

        set = SWAppBase.keySet();

        itr = set.iterator();

        while(itr.hasNext())
        {
            String mktgName;
            String [] strLines;
            String key = (String) itr.next();

            machType = key.substring(1, 5);
            model = key.substring(5, 8);
            log("machType = " + machType);
            log("model = " + model);
            baseModelKey = key.substring(8);

            baseModel = (EntityItem) SWAppBase.get(key);

            mktgName = PokUtils.getAttributeValue(baseModel, "MKTGNAME", "|", "", false);
            if(mktgName.length() > I_128)
            {
                mktgName = mktgName.substring(0, I_128);
            }
            //mktgName = mktgName + " (" + machType + "-" + model + ")";

            strLines = extractStringLines(mktgName, I_65);

            initialOrder = containBaseModel(Q352A1AppBase) || containBaseModel(Q352A1AppOptFeaBase1);
            upgradeOrder = containBaseModel(Q352A2AppBaseTo) || containBaseModel(Q352A2AppOptFeaBase1To);
            gtgUpgrade = containBaseModel(Q352A3AppBase) ||  containBaseModel(Q352A3AppOptFeaBase1);
            otherCharge = containBaseModel(Q352A4AppBaseChargeFee) || containBaseModel( Q352A4AppOptFeaBase1ChargeFee);
            noCharge = containBaseModel(Q352A4AppBaseNoChargeFee) || containBaseModel(Q352A4AppOptFeaBase1NoChargeFee);

            showInitialOrderHeader = true;
            showUpgradeOrderHeader = true;
            showG2GUpgradesHeader = true;

            for(int i = 0; i < strLines.length; i++)
            {
                if(initialOrder || upgradeOrder || gtgUpgrade || otherCharge || noCharge)
                {
                    sb.append(":h3." + strLines[i] + NEWLINE);
                }
                if(initialOrder || upgradeOrder || gtgUpgrade || otherCharge)
                {
                    sbQ523.append(":h3." + strLines[i] + NEWLINE);
                }
            }

            if(initialOrder || upgradeOrder || gtgUpgrade)
            {
                sb.append(":p." + NEWLINE);
                sb.append(":p.:hp2.Charge Features:ehp2." + NEWLINE);
                //sb.append(":p." + NEWLINE);
                sbQ523.append(":p." + NEWLINE);
                sbQ523.append(":p.:hp2.Charge Features:ehp2." + NEWLINE);
                //sbQ523.append(":p." + NEWLINE);
            }

            if(initialOrder)
            {
                //sb.append(":hp1.Initial Order:ehp1." + NEWLINE);
                //sbQ523.append(":hp1.Initial Order:ehp1." + NEWLINE);
                displayInitialBillingCode();
            }
            displayMaxUsers();
            if(upgradeOrder)
            {
                //sb.append(":hp1.Upgrade Order:ehp1." + NEWLINE);
                //sbQ523.append(":hp1.Upgrade Order:ehp1." + NEWLINE);
                displayNewUpgradeBillingCode();
            }
            if(gtgUpgrade)
            {
                //sb.append(":hp1.Group-to-Group Upgrades:ehp1." + NEWLINE);
                //sbQ523.append(":hp1.Group-to-Group Upgrades:ehp1." + NEWLINE);
                displayGroupToGroupPBOTCBillingCode();
            }
            displayOtherChargeAndNoCharge();

        }//end of while(itr.hasNext())

        sb.append("<br /><br />" + NEWLINE);
        sb.append(sbQ523.toString());

        return sb;
    }

    /********************************************************************************
    * displayInitialBillingCode()
    *
    *
    */
    private void displayInitialBillingCode()
    {
        displayInitialBillingCodePBOTC();
        displayInitialBillingCodeOther();
    }

    /********************************************************************************
    * displayInitialBillingCodePBOTC()
    *
    *
    */
    private void displayInitialBillingCodePBOTC()
    {
        //Need to print a separate row for base application and one for each optional feature.
        TreeMap base = new TreeMap();
        String featureCode;
        String performanceGroup;
        String [] pg;

        TreeMap [] optionalFeatureBase;
        TreeSet ofb;
        Hashtable ofbHT;
        String [] arrayTechName ;
        Iterator tsIterator;
        int index;
        String baseModelTechName;
        TreeSet ts;
        Set aSet;
        Iterator itr;
        int count;
        int [] cursorPos;
        Object [] arrayObj;
        TreeMap pos;

        Hashtable ht = new Hashtable();
        //319 = ValueMetric
        //357 = PBOTC
        //010 = N/A
        ht.put("SWFCCAT", "319");
        ht.put("SWFCSUBCAT", "357");
        //ht.put("SWFCGRP", "010");

        //Get all SWPRODSTRUCT associate with base MODEL. This is for MODEL(SW-Application/Subcription/Maintenance/Support-Base)
        for(int i = 0; i < Q352A1AppBase.size(); i++)
        {
            SWEntityItem_OIM swei = (SWEntityItem_OIM) Q352A1AppBase.get(i);

            if(swei.containModelBase(baseModel))
            {
                EntityItem swProdStructItem = swei.getSWProdStruct();

                Vector upLinkEntityItemVector = getUpLinkEntityItems(swProdStructItem, "SWFEATURE");
                for(int n = 0; n < upLinkEntityItemVector.size(); n++)
                {
                    EANEntity entityUpLink  = (EANEntity) upLinkEntityItemVector.get(n);
                    if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                    {
                        EntityItem swFeatureItem = (EntityItem) entityUpLink;

                        featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                        featureCode = featureCode + "      ";
                        featureCode = featureCode.substring(0, 6);
                        performanceGroup = PokUtils.getAttributeValue(swFeatureItem, "PERFORMANCEGROUP", "|", "---");
                        pg = PokUtils.convertToArray(performanceGroup);
                        for(int j = 0; j < pg.length; j++)
                        {
                            String key;
                            String geo;
                            String perfGrp = pg[j] + "   ";
                            perfGrp = perfGrp.substring(0, 3);
                            key = machType + model + perfGrp + featureCode + swProdStructItem.getKey();
                            geo = getGeo(swProdStructItem.getKey());

                            base.put(key, geo);
                        }//end of for(int j = 0; j < pg.length; j++)
                    }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, tempHT))
                }//end of for(int n = 0; n < upLinkEntityItemVector.size(); n++)
            }//end of if(swei.containModelBase(baseModel))
        }//end of for(int i = 0; i < Q352A1AppBase.size(); i++)

        //Get all SWPRODSTRUCT associate with each optional feature which associates with base MODEL.
        ofb = new TreeSet();
        ofbHT = new Hashtable();
        for(int i = 0; i < Q352A1AppOptFeaBase1.size(); i++)
        {
            SWEntityItem_OIM swei = (SWEntityItem_OIM) Q352A1AppOptFeaBase1.get(i);

            if(swei.containModelBase(baseModel))
            {
                EntityItem OptFeaBaseModel = swei.getOptFeaBaseModel();
                String techName = PokUtils.getAttributeValue(OptFeaBaseModel, "MKTGNAME", "|", "", false);
                ofb.add(OptFeaBaseModel.getKey());
                if(techName.length() > I_128)
                {
                    techName = techName.substring(0, I_128);
                }
                ofbHT.put(OptFeaBaseModel.getKey(), techName);
            }
        }//end of for(int i = 0; i < Q352A1AppOptFeaBase1.size(); i++)

        optionalFeatureBase = new TreeMap[ofb.size()];
        for(int i = 0; i < optionalFeatureBase.length; i++)
        {
            optionalFeatureBase[i] = new TreeMap();
        }
        arrayTechName = new String[ofb.size()];

        tsIterator = ofb.iterator();
        index = 0;
        while(tsIterator.hasNext())
        {
            String optFeaBaseModel = (String) tsIterator.next();
            for(int i = 0; i < Q352A1AppOptFeaBase2.size(); i++)
            {
                SWEntityItem_OIM swei = (SWEntityItem_OIM) Q352A1AppOptFeaBase2.get(i);

                if(swei.containModelOptFeaBase(optFeaBaseModel))
                {
                    EntityItem swProdStructItem = swei.getSWProdStruct();

                    Vector upLinkEntityItemVector = getUpLinkEntityItems(swProdStructItem, "SWFEATURE");
                    for(int n = 0; n < upLinkEntityItemVector.size(); n++)
                    {
                        EANEntity entityUpLink  = (EANEntity) upLinkEntityItemVector.get(n);
                        if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                        {
                            EntityItem swFeatureItem = (EntityItem) entityUpLink;

                            featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                            featureCode = featureCode + "      ";
                            featureCode = featureCode.substring(0, 6);
                            performanceGroup = PokUtils.getAttributeValue(swFeatureItem, "PERFORMANCEGROUP", "|", "---");
                            pg = PokUtils.convertToArray(performanceGroup);
                            for(int j = 0; j < pg.length; j++)
                            {
                                String key;
                                String geo;
                                String techName;
                                String perfGrp = pg[j] + "   ";
                                perfGrp = perfGrp.substring(0, 3);
                                key = machType + model + perfGrp + featureCode + swProdStructItem.getKey();
                                geo = getGeo(swProdStructItem.getKey());

                                optionalFeatureBase[index].put(key, geo);
                                techName = (String) ofbHT.get(optFeaBaseModel);

                                arrayTechName[index] = techName;
                            }//end of for(int j = 0; j < pg.length; j++)
                        }//end if(isEntityWithMatchedAttr((EntityItem) entityUpLink, tempHT))
                    }//end of for(int n = 0; n < upLinkEntityItemVector.size(); n++)
                }//end of if(swei.containModelOptFeaBase(optFeaBaseModel))
            }//end of for(int i = 0; i < Q352A1AppOptFeaBase2.size(); i++)

            index++;
        }//end of while(tsIterator.hasNext())

        baseModelTechName = PokUtils.getAttributeValue(baseModel, "MKTGNAME", "|", "", false);
        if(baseModelTechName.length() > I_128)
        {
            baseModelTechName = baseModelTechName.substring(0, I_128);
        }

        //Count number of distinct performance group
        ts = new TreeSet();

        aSet = base.keySet();
        itr = aSet.iterator();
        while(itr.hasNext())
        {
            String str = (String) itr.next();
            String perfgrp = str.substring(7, 10);
            ts.add(perfgrp);
        }

        for(int i = 0; i < optionalFeatureBase.length; i++)
        {
            aSet = optionalFeatureBase[i].keySet();
            itr = aSet.iterator();
            while(itr.hasNext())
            {
                String str = (String) itr.next();
                String perfgrp = str.substring(7, 10);
                ts.add(perfgrp);
            }
        }

        count = 0;

        //Column positions
        cursorPos = new int[6];
        cursorPos[0] = I_23;
        cursorPos[1] = I_31;
        cursorPos[2] = I_39;
        cursorPos[3] = I_47;
        cursorPos[4] = I_55;
        cursorPos[5] = I_63;

        arrayObj = ts.toArray();

        pos = new TreeMap();

        if(arrayObj.length > 0)
        {
            if(showInitialOrderHeader)
            {
                sb.append(":hp1.Initial Order:ehp1." + NEWLINE);
                sbQ523.append(":hp1.Initial Order:ehp1." + NEWLINE);
                showInitialOrderHeader = false;
            }
            sb.append(":xmp." + NEWLINE);
            sbQ523.append(":xmp." + NEWLINE);
        }
        for(int i = 0; i < arrayObj.length; i++)
        {
            if(count < 6)
            {
                pos.put((String) arrayObj[i], new Integer(cursorPos[i % 6]));
                count++;
            }
            if((count == 6) || (i == arrayObj.length - 1))
            {
                count = 0;

                displayInitialBillingCodePBOTC(pos, base, baseModelTechName, optionalFeatureBase, arrayTechName);
                pos.clear();
            }
        }
        if(arrayObj.length > 0)
        {
            sb.append(":exmp." + NEWLINE);
            sbQ523.append(":exmp." + NEWLINE);
        }
    }

    /********************************************************************************
    * displayInitialBillingCodePBOTC()
    *
    * @param pos TreeMap
    * @param base TreeMap
    * @param baseModelTechName String
    * @param optionalFeatureBase TreeMap []
    * @param arrayTechName String []
    */
    private void displayInitialBillingCodePBOTC(TreeMap pos, TreeMap base, String baseModelTechName, TreeMap [] optionalFeatureBase, String [] arrayTechName)
    {
        Set set;
        Iterator itr;
        int count;

        int numPerformanceGroup = pos.size();
        int lastPGPosition;

        Integer aInteger = (Integer) pos.get(pos.lastKey());
        lastPGPosition = aInteger.intValue();

        printChar(sb, I_22 + 8*numPerformanceGroup - 1, "-", true);
        printChar(sbQ523, I_22 + 8*numPerformanceGroup - 1, "-", true);
        sb.append(NEWLINE);
        sbQ523.append(NEWLINE);
        sb.append("|");
        sbQ523.append("|");
        sb.append(machType + "-" + model);
        sbQ523.append(machType + "-" + model);
        printChar(sb, I_12, " ", false);
        printChar(sbQ523, I_12, " ", false);
        sb.append("|");
        sbQ523.append("|");

        set = pos.keySet();

        itr = set.iterator();

        count = 0;
        while(itr.hasNext())
        {
            String aStringKey = (String) itr.next();
            count++;
            if(count < set.size())
            {
                sb.append(aStringKey + "     ");
                sbQ523.append(aStringKey + "     ");
            }
            else
            {
                sb.append(aStringKey + "   ");
                sbQ523.append(aStringKey + "   ");
            }
        }
        sb.append("|" + NEWLINE);
        sbQ523.append("|" + NEWLINE);
        printChar(sb, I_22 + 8*numPerformanceGroup - 1, "-", true);
        printChar(sbQ523, I_22 + 8*numPerformanceGroup - 1, "-", true);
        sb.append(NEWLINE);
        sbQ523.append(NEWLINE);

        displayInitialBillingCodePBOTC(pos, base, numPerformanceGroup, lastPGPosition, baseModelTechName);
        for(int i = 0; i < optionalFeatureBase.length; i++)
        {
            displayInitialBillingCodePBOTC(pos, optionalFeatureBase[i], numPerformanceGroup, lastPGPosition, arrayTechName[i]);
        }
    }

    /********************************************************************************
    * displayInitialBillingCodePBOTC()
    *
    * @param pos TreeMap
    * @param tm TreeMap
    * @param numPerformanceGroup int
    * @param lastPGPosition int
    * @param techName String
    */
    private void displayInitialBillingCodePBOTC(TreeMap pos, TreeMap tm, int numPerformanceGroup, int lastPGPosition, String techName)
    {
        //Group entries based on Geo
        TreeSet ts = new TreeSet();
        Set tmSet = tm.keySet();
        Iterator tmItr = tmSet.iterator();
        TreeMap [] arrayTM;
        int index;
        Iterator tsItr;
        boolean printHeaderRow;

        ts = new TreeSet();
        tmSet = tm.keySet();
        tmItr = tmSet.iterator();
        while(tmItr.hasNext())
        {
            ts.add(tm.get(tmItr.next()));
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
                String aKey = (String) tmItr.next();
                String pg = aKey.substring(7, 10);
                if(geo.equals((String) tm.get(aKey)) && pos.containsKey(pg))
                {
                    arrayTM[index].put(aKey, geo);
                }
            }//end of while(tmItr.hasNext())
            index++;
        }//end of while(tsItr.hasNext())

        printHeaderRow = true;
        for(int i = 0; i < arrayTM.length; i++)
        {
            if(printHeaderRow && (arrayTM[i].size() > 0))
            {
                displayInitialBillingCodePBOTC(lastPGPosition, pos, arrayTM[i], techName, true);
                displayInitialBillingCodePBOTC_Q523(lastPGPosition, pos, arrayTM[i], techName, true);
                printHeaderRow = false;
            }
            else
            {
                //displayInitialBillingCodePBOTC(lastPGPosition, pos, arrayTM[i], techName, false);
                displayInitialBillingCodePBOTC(lastPGPosition, pos, arrayTM[i], techName, true);
                displayInitialBillingCodePBOTC_Q523(lastPGPosition, pos, arrayTM[i], techName, true);
            }
        }

        for(int i = 0; i < arrayTM.length; i++)
        {
            if(arrayTM[i].size() > 0)
            {
                printChar(sb, I_22 + 8*numPerformanceGroup - 1, "-", true);
                printChar(sbQ523, I_22 + 8*numPerformanceGroup - 1, "-", true);
                sb.append(NEWLINE);
                sbQ523.append(NEWLINE);
                break;
            }
        }
    }

    /********************************************************************************
    * displayInitialBillingCodePBOTC()
    *
    * @param lastPGPosition int
    * @param pos TreeMap
    * @param tm TreeMap
    * @param techName String
    * @param printRowHeader boolean
    * @returns void
    */
    private void displayInitialBillingCodePBOTC(int lastPGPosition, TreeMap pos, TreeMap tm, String techName, boolean printRowHeader)
    {
        String currentPG = "";
        String lastPG = "";
        String featureCode = "";
        String currentGeo = "";
        String lastGeo = "";
        Integer aInteger;

        int currentCursorPos = 0;
        int posPG = 0;

        Hashtable finishedHT = new Hashtable();

        boolean geoTag = false;
        boolean printTechName = false;

        String [] strLines = extractStringLines(techName, I_20);
        int nLines = 0;

        //TreeSet priceHolderPos = new TreeSet();

        for(int i = 0; i <= tm.size(); i++)
        {
            Set aSet;
            Iterator aItr;

            if(!lastPG.equals("") && (finishedHT.size() < tm.size()))
            {
                printChar(sb, lastPGPosition + 6 - currentCursorPos, " ", false);
                //printChar(sbQ523, lastPGPosition + 6 - currentCursorPos, " ", false);
                sb.append("|" + NEWLINE);
                //sbQ523.append("|" + NEWLINE);
                currentCursorPos = 0;
                if(printRowHeader && (nLines < strLines.length))
                {
                    sb.append("|");
                    //sbQ523.append("|");
                    sb.append(strLines[nLines]);
                    //sbQ523.append(strLines[nLines]);
                    printChar(sb, I_20 - strLines[nLines].length(), " ", false);
                    //printChar(sbQ523, 20 - strLines[nLines].length(), " ", false);
                    sb.append("|");
                    //sbQ523.append("|");
                    nLines++;
                }
                else
                {
                    printChar(sb, I_22, " ", true);
                    //printChar(sbQ523, 22, " ", true);
                }
            }//end of if(!lastPG.equals("") && (finishedHT.size() < tm.size()))
            else if(!lastPG.equals("") && (finishedHT.size() == tm.size()))
            {
                if(currentCursorPos < lastPGPosition)
                {
                    printChar(sb, lastPGPosition + 6 - currentCursorPos, " ", false);
                    //printChar(sbQ523, lastPGPosition + 6 - currentCursorPos, " ", false);
                }
                sb.append("|" + NEWLINE);
                //sbQ523.append("|" + NEWLINE);
                if(printRowHeader)
                {
                    while(nLines < strLines.length)
                    {
                        sb.append("|");
                        //sbQ523.append("|");
                        sb.append(strLines[nLines]);
                        //sbQ523.append(strLines[nLines]);
                        printChar(sb, I_20 - strLines[nLines].length(), " ", false);
                        //printChar(sbQ523, 20 - strLines[nLines].length(), " ", false);
                        sb.append("|");
                        //sbQ523.append("|");
                        //if(priceHolderPos.size() > 0)
                        //   printPriceHolderInitialBillingCodePBOTC(lastPGPosition, pos, priceHolderPos);

                        printChar(sb, lastPGPosition + 6 - I_23, " ", false);
                        //if(0 == priceHolderPos.size())
                        //printChar(sbQ523, lastPGPosition + 6 - 23, " ", false);

                        sb.append("|" + NEWLINE);
                        //if(0 == priceHolderPos.size())
                        //sbQ523.append("|" + NEWLINE);
                        //priceHolderPos.clear();
                        nLines++;
                    }
                }
                //if(priceHolderPos.size() > 0)
                //{
                //printChar(sbQ523, 22, " ", true);
                //printPriceHolderInitialBillingCodePBOTC(lastPGPosition, pos, priceHolderPos);
                //   priceHolderPos.clear();
                //}

                if(!currentGeo.equals("WW"))
                {
                    sb.append(".br;:hp2.<---" + lastGeo + ":ehp2." + NEWLINE);
                    //sbQ523.append(".br;:hp2.<---" + lastGeo + ":ehp2." + NEWLINE);
                }
            }//end of else if(!lastPG.equals("") && (finishedHT.size() == tm.size()))

            currentPG = "";
            lastPG = "";
            featureCode = "";
            currentGeo = "";
            lastGeo = "";

            currentCursorPos = I_23;
            posPG = 0;

            aSet = tm.keySet();

            aItr = aSet.iterator();

            while(aItr.hasNext())
            {
                String aStringKey = (String) aItr.next();

                if(!finishedHT.containsKey(aStringKey))
                {
                    currentPG = aStringKey.substring(I_7, I_10);
                    featureCode = aStringKey.substring(I_10, I_16);
                    currentGeo = (String) tm.get(aStringKey);
                    if(!currentPG.equals(lastPG))
                    {
                        if(currentGeo.equals("WW") && false == geoTag)
                        {
                            geoTag = true;
                            if(printRowHeader && !printTechName)
                            {
                                sb.append("|");
                                //sbQ523.append("|");
                                if(strLines.length > 0)
                                {
                                    sb.append(strLines[0]);
                                    //sbQ523.append(strLines[0]);
                                    printChar(sb, I_20 - strLines[0].length(), " ", false);
                                    //printChar(sbQ523, 20 - strLines[0].length(), " ", false);
                                    nLines++;
                                }
                                else
                                {
                                    sb.append(techName);
                                    //sbQ523.append(techName);
                                    printChar(sb, I_20 - techName.length(), " ", false);
                                    //printChar(sbQ523, 20 - techName.length(), " ", false);
                                }
                                sb.append("|");
                                //sbQ523.append("|");
                                printTechName = true;
                            }
                            else
                            {
                                printChar(sb, I_22, " ", true);
                                //printChar(sbQ523, 22, " ", true);
                            }
                            currentCursorPos = I_23;
                        }
                        else if(!currentGeo.equals("WW") && false == geoTag)
                        {
                            sb.append(":p.:hp2." + currentGeo + "--->" + ":ehp2." + NEWLINE);
                            //sbQ523.append(":p.:hp2." + currentGeo + "--->" + ":ehp2." + NEWLINE);
                            geoTag = true;
                            if(printRowHeader && !printTechName)
                            {
                                sb.append("|");
                                //sbQ523.append("|");
                                if(strLines.length > 0)
                                {
                                    sb.append(strLines[0]);
                                    //sbQ523.append(strLines[0]);
                                    printChar(sb, I_20 - strLines[0].length(), " ", false);
                                    //printChar(sbQ523, 20 - strLines[0].length(), " ", false);
                                    nLines++;
                                }
                                else
                                {
                                    sb.append(techName);
                                    //sbQ523.append(techName);
                                    printChar(sb, I_20 - techName.length(), " ", false);
                                    //printChar(sbQ523, 20 - techName.length(), " ", false);
                                }
                                sb.append("|");
                                //sbQ523.append("|");
                                printTechName = true;
                            }
                            else
                            {
                                printChar(sb, I_22, " ", true);
                                //printChar(sbQ523, 22, " ", true);
                            }
                            currentCursorPos = I_23;
                        }
                        aInteger = (Integer) pos.get(currentPG);
                        posPG = aInteger.intValue();
                        printChar(sb, posPG - currentCursorPos, " ", false);
                        //printChar(sbQ523, posPG - currentCursorPos, " ", false);
                        currentCursorPos = posPG;
                        sb.append(featureCode);
                        //sbQ523.append(featureCode);
                        currentCursorPos = currentCursorPos + I_6;
                        finishedHT.put(aStringKey, "Yes");
                        //priceHolderPos.add(currentPG);
                        lastPG = currentPG;
                        lastGeo = currentGeo;
                    }//end of if(!currentPG.equals(lastPG))
                }//end of if(!finishedHT.containsKey(aStringKey))
            }//end of while(aItr.hasNext())
        }//end of for(int i = 0; i < tm.size(); i++)
    }

    /********************************************************************************
    * displayInitialBillingCodePBOTC_Q523()
    *
    * @param lastPGPosition int
    * @param pos TreeMap
    * @param tm TreeMap
    * @param techName String
    * @param printRowHeader boolean
    */
    private void displayInitialBillingCodePBOTC_Q523(int lastPGPosition, TreeMap pos, TreeMap tm, String techName, boolean printRowHeader)
    {
        String currentPG = "";
        String lastPG = "";
        String featureCode = "";
        String currentGeo = "";
        String lastGeo = "";
        Integer aInteger;

        int currentCursorPos = 0;
        int posPG = 0;

        Hashtable finishedHT = new Hashtable();

        boolean geoTag = false;
        boolean printTechName = false;

        String [] strLines = extractStringLines(techName, I_20);
        int nLines = 0;

        TreeSet priceHolderPos = new TreeSet();

        for(int i = 0; i <= tm.size(); i++)
        {
            Set aSet;
            Iterator aItr;

            if(!lastPG.equals("") && (finishedHT.size() < tm.size()))
            {
                //printChar(sb, lastPGPosition + 6 - currentCursorPos, " ", false);
                printChar(sbQ523, lastPGPosition + 6 - currentCursorPos, " ", false);
                //sb.append("|" + NEWLINE);
                sbQ523.append("|" + NEWLINE);
                currentCursorPos = 0;
                if((printRowHeader) && (nLines < strLines.length))
                {
                    //sb.append("|");
                    sbQ523.append("|");
                    //sb.append(strLines[nLines]);
                    sbQ523.append(strLines[nLines]);
                    //printChar(sb, 20 - strLines[nLines].length(), " ", false);
                    printChar(sbQ523, I_20 - strLines[nLines].length(), " ", false);
                    //sb.append("|");
                    sbQ523.append("|");
                    nLines++;
                    printPriceHolderInitialBillingCodePBOTC(lastPGPosition, pos, priceHolderPos);
                    priceHolderPos.clear();
                    if(nLines < strLines.length)
                    {
                        sbQ523.append("|");
                        sbQ523.append(strLines[nLines]);
                        printChar(sbQ523, I_20 - strLines[nLines].length(), " ", false);
                        sbQ523.append("|");
                        nLines++;
                    }
                }
                else
                {
                    //printChar(sb, 22, " ", true);
                    printChar(sbQ523, I_22, " ", true);
                    printPriceHolderInitialBillingCodePBOTC(lastPGPosition, pos, priceHolderPos);
                    priceHolderPos.clear();
                    printChar(sbQ523, I_22, " ", true);
                }
            }//end of if(!lastPG.equals("") && (finishedHT.size() < tm.size()))
            else if(!lastPG.equals("") && (finishedHT.size() == tm.size()))
            {
                if(currentCursorPos < lastPGPosition)
                {
                    //printChar(sb, lastPGPosition + 6 - currentCursorPos, " ", false);
                    printChar(sbQ523, lastPGPosition + 6 - currentCursorPos, " ", false);
                }
                //sb.append("|" + NEWLINE);
                sbQ523.append("|" + NEWLINE);
                if(printRowHeader)
                {
                    while(nLines < strLines.length)
                    {
                        //sb.append("|");
                        sbQ523.append("|");
                        //sb.append(strLines[nLines]);
                        sbQ523.append(strLines[nLines]);
                        //printChar(sb, 20 - strLines[nLines].length(), " ", false);
                        printChar(sbQ523, I_20 - strLines[nLines].length(), " ", false);
                        //sb.append("|");
                        sbQ523.append("|");
                        if(priceHolderPos.size() > 0)
                        {
                            printPriceHolderInitialBillingCodePBOTC(lastPGPosition, pos, priceHolderPos);
                        }

                        //printChar(sb, lastPGPosition + 6 - 23, " ", false);
                        if(0 == priceHolderPos.size())
                        {
                            printChar(sbQ523, lastPGPosition + I_6 - I_23, " ", false);
                        }

                        //sb.append("|" + NEWLINE);
                        if(0 == priceHolderPos.size())
                        {
                            sbQ523.append("|" + NEWLINE);
                        }
                        priceHolderPos.clear();
                        nLines++;
                    }
                }
                if(priceHolderPos.size() > 0)
                {
                    printChar(sbQ523, I_22, " ", true);
                    printPriceHolderInitialBillingCodePBOTC(lastPGPosition, pos, priceHolderPos);
                    priceHolderPos.clear();
                }

                if(!currentGeo.equals("WW"))
                {
                    //sb.append(".br;:hp2.<---" + lastGeo + ":ehp2." + NEWLINE);
                    sbQ523.append(".br;:hp2.<---" + lastGeo + ":ehp2." + NEWLINE);
                }
            }//end of else if(!lastPG.equals("") && (finishedHT.size() == tm.size()))

            currentPG = "";
            lastPG = "";
            featureCode = "";
            currentGeo = "";
            lastGeo = "";

            currentCursorPos = I_23;
            posPG = 0;

            aSet = tm.keySet();

            aItr = aSet.iterator();

            while(aItr.hasNext())
            {
                String aStringKey = (String) aItr.next();

                if(!finishedHT.containsKey(aStringKey))
                {
                    currentPG = aStringKey.substring(I_7, I_10);
                    featureCode = aStringKey.substring(I_10, I_16);
                    currentGeo = (String) tm.get(aStringKey);
                    if(!currentPG.equals(lastPG))
                    {
                        if(currentGeo.equals("WW") && false == geoTag)
                        {
                            geoTag = true;
                            if(printRowHeader && !printTechName)
                            {
                                //sb.append("|");
                                sbQ523.append("|");
                                if(strLines.length > 0)
                                {
                                    //sb.append(strLines[0]);
                                    sbQ523.append(strLines[0]);
                                    //printChar(sb, 20 - strLines[0].length(), " ", false);
                                    printChar(sbQ523, I_20 - strLines[0].length(), " ", false);
                                    nLines++;
                                }
                                else
                                {
                                    //sb.append(techName);
                                    sbQ523.append(techName);
                                    //printChar(sb, 20 - techName.length(), " ", false);
                                    printChar(sbQ523, I_20 - techName.length(), " ", false);
                                }
                                //sb.append("|");
                                sbQ523.append("|");
                                printTechName = true;
                            }
                            else
                            {
                                //printChar(sb, 22, " ", true);
                                printChar(sbQ523, I_22, " ", true);
                            }
                            currentCursorPos = I_23;
                        }
                        else if(!currentGeo.equals("WW") && false == geoTag)
                        {
                            //sb.append(":p.:hp2." + currentGeo + "--->" + ":ehp2." + NEWLINE);
                            sbQ523.append(":p.:hp2." + currentGeo + "--->" + ":ehp2." + NEWLINE);
                            geoTag = true;
                            if(printRowHeader && !printTechName)
                            {
                                //sb.append("|");
                                sbQ523.append("|");
                                if(strLines.length > 0)
                                {
                                    //sb.append(strLines[0]);
                                    sbQ523.append(strLines[0]);
                                    //printChar(sb, 20 - strLines[0].length(), " ", false);
                                    printChar(sbQ523, I_20 - strLines[0].length(), " ", false);
                                    nLines++;
                                }
                                else
                                {
                                    //sb.append(techName);
                                    sbQ523.append(techName);
                                    //printChar(sb, 20 - techName.length(), " ", false);
                                    printChar(sbQ523, I_20 - techName.length(), " ", false);
                                }
                                //sb.append("|");
                                sbQ523.append("|");
                                printTechName = true;
                            }
                            else
                            {
                                //printChar(sb, 22, " ", true);
                                printChar(sbQ523, I_22, " ", true);
                            }
                            currentCursorPos = I_23;
                        }
                        aInteger = (Integer) pos.get(currentPG);
                        posPG = aInteger.intValue();
                        //printChar(sb, posPG - currentCursorPos, " ", false);
                        printChar(sbQ523, posPG - currentCursorPos, " ", false);
                        currentCursorPos = posPG;
                        //sb.append(featureCode);
                        sbQ523.append(featureCode);
                        currentCursorPos = currentCursorPos + I_6;
                        finishedHT.put(aStringKey, "Yes");
                        priceHolderPos.add(currentPG);
                        lastPG = currentPG;
                        lastGeo = currentGeo;
                    }//end of if(!currentPG.equals(lastPG))
                }//end of if(!finishedHT.containsKey(aStringKey))
            }//end of while(aItr.hasNext())
        }//end of for(int i = 0; i < tm.size(); i++)
    }

    /********************************************************************************
    * displayInitialBillingCodeOther()
    *
    */
    private void displayInitialBillingCodeOther()
    {
        displayInitialBillingCodeFlatBaseFlatFeeEntitleReg();
        displayInitialBillingCodeUseBlockUseEntitleBlock();
    }

    /********************************************************************************
    * displayInitialBillingCodeFlatBaseFlatFeeEntitleReg()
    *
    */
    private void displayInitialBillingCodeFlatBaseFlatFeeEntitleReg()
    {
        //Need to print a separate row for base application and one for each optional feature.
        TreeMap base = new TreeMap();
        String featureCode;

        TreeMap [] optionalFeatureBase;
        TreeSet ofb;
        Hashtable ofbHT;
        String [] arrayTechName;
        Iterator tsIterator;
        int index;
        TreeSet ts;
        Set aSet;
        Iterator itr;
        String baseModelTechName;
        int count;
        int [] cursorPos;
        Object [] arrayObj;
        TreeMap pos;

        Hashtable ht = new Hashtable();

        log("In displayInitialBillingCodeFlatBaseFlatFeeEntitleReg()");
        //Get all SWPRODSTRUCT associate with base MODEL. This is for MODEL(SW-Application/Subscription/Maintenance/Support-Base)
        for(int i = 0; i < Q352A1AppBase.size(); i++)
        {
            SWEntityItem_OIM swei = (SWEntityItem_OIM) Q352A1AppBase.get(i);

            if(swei.containModelBase(baseModel))
            {
                EntityItem swProdStructItem = swei.getSWProdStruct();

                Vector upLinkEntityItemVector = getUpLinkEntityItems(swProdStructItem, "SWFEATURE");
                log(swProdStructItem.toString());
                for(int n = 0; n < upLinkEntityItemVector.size(); n++)
                {
                    EANEntity entityUpLink  = (EANEntity) upLinkEntityItemVector.get(n);
                    log(entityUpLink.toString());
                    log("SWFCCAT = " + PokUtils.getAttributeValue((EntityItem)entityUpLink, "SWFCCAT", "" , "", false));
                    log("SWFCSUBCAT = " + PokUtils.getAttributeValue((EntityItem)entityUpLink, "SWFCSUBCAT", "" , "", false));
                    log("LICENSETYPE = " + PokUtils.getAttributeValue((EntityItem)entityUpLink, "LICENSETYPE", "" , "", false));
                    log("CHARGEOPTION = " + PokUtils.getAttributeValue((EntityItem)entityUpLink, "CHARGEOPTION", "" , "", false));

                    ht.clear();
                    //319 = ValueMetric
                    //354 = FlatBase
                    //010 = N/A
                    //2823 = BASIC
                    //380 = OTC
                    ht.put("SWFCCAT", "319");
                    ht.put("SWFCSUBCAT", "354");
                    //ht.put("SWFCGRP", "010");
                    ht.put("LICENSETYPE", "2823");
                    ht.put("CHARGEOPTION", "380");

                    if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                    {
                        String key;
                        String geo;
                        EntityItem swFeatureItem = (EntityItem) entityUpLink;

                        featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                        featureCode = featureCode + "      ";
                        featureCode = featureCode.substring(0, 6);
                        key = machType + model + "flb1" + featureCode + swProdStructItem.getKey();
                        geo = getGeo(swProdStructItem.getKey());

                        base.put(key, geo);
                    }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))

                    ht.clear();
                    //319 = ValueMetric
                    //354 = FlatBase
                    //010 = N/A
                    //2823 = BASIC
                    //378 = MLC
                    ht.put("SWFCCAT", "319");
                    ht.put("SWFCSUBCAT", "354");
                    //ht.put("SWFCGRP", "010");
                    ht.put("LICENSETYPE", "2823");
                    ht.put("CHARGEOPTION", "378");

                    if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                    {
                        String key;
                        String geo;
                        EntityItem swFeatureItem = (EntityItem) entityUpLink;

                        featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                        featureCode = featureCode + "      ";
                        featureCode = featureCode.substring(0, 6);
                        key = machType + model + "flb2" + featureCode + swProdStructItem.getKey();
                        geo = getGeo(swProdStructItem.getKey());

                        base.put(key, geo);
                    }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))

                    ht.clear();
                    //319 = ValueMetric
                    //354 = FlatBase
                    //010 = N/A
                    //2823 = BASIC
                    //379 = PLC
                    ht.put("SWFCCAT", "319");
                    ht.put("SWFCSUBCAT", "354");
                    //ht.put("SWFCGRP", "010");
                    ht.put("LICENSETYPE", "2823");
                    ht.put("CHARGEOPTION", "379");

                    if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                    {
                        String key;
                        String geo;
                        EntityItem swFeatureItem = (EntityItem) entityUpLink;

                        featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                        featureCode = featureCode + "      ";
                        featureCode = featureCode.substring(0, 6);
                        key = machType + model + "flb3" + featureCode + swProdStructItem.getKey();
                        geo = getGeo(swProdStructItem.getKey());

                        base.put(key, geo);
                    }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))

                    ht.clear();
                    //319 = ValueMetric
                    //354 = FlatBase
                    //010 = N/A
                    //2823 = BASIC
                    //377 = RLC
                    ht.put("SWFCCAT", "319");
                    ht.put("SWFCSUBCAT", "354");
                    //ht.put("SWFCGRP", "010");
                    ht.put("LICENSETYPE", "2823");
                    ht.put("CHARGEOPTION", "377");

                    if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                    {
                        String key;
                        String geo;
                        EntityItem swFeatureItem = (EntityItem) entityUpLink;

                        featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                        featureCode = featureCode + "      ";
                        featureCode = featureCode.substring(0, 6);
                        key = machType + model + "flb4" + featureCode + swProdStructItem.getKey();
                        geo = getGeo(swProdStructItem.getKey());

                        base.put(key, geo);
                    }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))

                    ht.clear();
                    //319 = ValueMetric
                    //354 = FlatBase
                    //010 = N/A
                    //2824 = DSLO
                    //380 = OTC
                    ht.put("SWFCCAT", "319");
                    ht.put("SWFCSUBCAT", "354");
                    //ht.put("SWFCGRP", "010");
                    ht.put("LICENSETYPE", "2824");
                    ht.put("CHARGEOPTION", "380");

                    if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                    {
                        String key;
                        String geo;
                        EntityItem swFeatureItem = (EntityItem) entityUpLink;

                        featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                        featureCode = featureCode + "      ";
                        featureCode = featureCode.substring(0, 6);
                        key = machType + model + "flb5" + featureCode + swProdStructItem.getKey();
                        geo = getGeo(swProdStructItem.getKey());

                        base.put(key, geo);
                    }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))

                    ht.clear();
                    //319 = ValueMetric
                    //354 = FlatBase
                    //010 = N/A
                    //2824 = DSLO
                    //378 = MLC
                    ht.put("SWFCCAT", "319");
                    ht.put("SWFCSUBCAT", "354");
                    //ht.put("SWFCGRP", "010");
                    ht.put("LICENSETYPE", "2824");
                    ht.put("CHARGEOPTION", "378");

                    if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                    {
                        String key;
                        String geo;
                        EntityItem swFeatureItem = (EntityItem) entityUpLink;

                        featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                        featureCode = featureCode + "      ";
                        featureCode = featureCode.substring(0, 6);
                        key = machType + model + "flb6" + featureCode + swProdStructItem.getKey();
                        geo = getGeo(swProdStructItem.getKey());

                        base.put(key, geo);
                    }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))

                    ht.clear();
                    //319 = ValueMetric
                    //354 = FlatBase
                    //010 = N/A
                    //2824 = DSLO
                    //379 = PLC
                    ht.put("SWFCCAT", "319");
                    ht.put("SWFCSUBCAT", "354");
                    //ht.put("SWFCGRP", "010");
                    ht.put("LICENSETYPE", "2824");
                    ht.put("CHARGEOPTION", "379");

                    if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                    {
                        String key;
                        String geo;
                        EntityItem swFeatureItem = (EntityItem) entityUpLink;

                        featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                        featureCode = featureCode + "      ";
                        featureCode = featureCode.substring(0, 6);
                        key = machType + model + "flb7" + featureCode + swProdStructItem.getKey();
                        geo = getGeo(swProdStructItem.getKey());

                        base.put(key, geo);
                    }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))

                    ht.clear();
                    //319 = ValueMetric
                    //354 = FlatBase
                    //010 = N/A
                    //2824 = DSLO
                    //377 = RLC
                    ht.put("SWFCCAT", "319");
                    ht.put("SWFCSUBCAT", "354");
                    //ht.put("SWFCGRP", "010");
                    ht.put("LICENSETYPE", "2824");
                    ht.put("CHARGEOPTION", "377");

                    if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                    {
                        String key;
                        String geo;
                        EntityItem swFeatureItem = (EntityItem) entityUpLink;

                        featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                        featureCode = featureCode + "      ";
                        featureCode = featureCode.substring(0, 6);
                        key = machType + model + "flb8" + featureCode + swProdStructItem.getKey();
                        geo = getGeo(swProdStructItem.getKey());

                        base.put(key, geo);
                    }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))

                    ht.clear();
                    //319 = ValueMetric
                    //355 = FlatFee
                    //010 = N/A
                    //2823 = BASIC
                    //380 = OTC
                    ht.put("SWFCCAT", "319");
                    ht.put("SWFCSUBCAT", "355");
                    //ht.put("SWFCGRP", "010");
                    ht.put("LICENSETYPE", "2823");
                    ht.put("CHARGEOPTION", "380");

                    if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                    {
                        String key;
                        String geo;
                        EntityItem swFeatureItem = (EntityItem) entityUpLink;

                        featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                        featureCode = featureCode + "      ";
                        featureCode = featureCode.substring(0, 6);
                        key = machType + model + "flf1" + featureCode + swProdStructItem.getKey();
                        geo = getGeo(swProdStructItem.getKey());

                        base.put(key, geo);
                    }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))

                    ht.clear();
                    //319 = ValueMetric
                    //355 = FlatFee
                    //010 = N/A
                    //2823 = BASIC
                    //378 = MLC
                    ht.put("SWFCCAT", "319");
                    ht.put("SWFCSUBCAT", "355");
                    //ht.put("SWFCGRP", "010");
                    ht.put("LICENSETYPE", "2823");
                    ht.put("CHARGEOPTION", "378");

                    if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                    {
                        String key;
                        String geo;
                        EntityItem swFeatureItem = (EntityItem) entityUpLink;

                        featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                        featureCode = featureCode + "      ";
                        featureCode = featureCode.substring(0, 6);
                        key = machType + model + "flf2" + featureCode + swProdStructItem.getKey();
                        geo = getGeo(swProdStructItem.getKey());

                        base.put(key, geo);
                    }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))

                    ht.clear();
                    //319 = ValueMetric
                    //355 = FlatFee
                    //010 = N/A
                    //2823 = BASIC
                    //379 = PLC
                    ht.put("SWFCCAT", "319");
                    ht.put("SWFCSUBCAT", "355");
                    //ht.put("SWFCGRP", "010");
                    ht.put("LICENSETYPE", "2823");
                    ht.put("CHARGEOPTION", "379");

                    if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                    {
                        String key;
                        String geo;
                        EntityItem swFeatureItem = (EntityItem) entityUpLink;

                        featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                        featureCode = featureCode + "      ";
                        featureCode = featureCode.substring(0, 6);
                        key = machType + model + "flf3" + featureCode + swProdStructItem.getKey();
                        geo = getGeo(swProdStructItem.getKey());

                        base.put(key, geo);
                    }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))

                    ht.clear();
                    //319 = ValueMetric
                    //355 = FlatFee
                    //010 = N/A
                    //2823 = BASIC
                    //377 = RLC
                    ht.put("SWFCCAT", "319");
                    ht.put("SWFCSUBCAT", "355");
                    //ht.put("SWFCGRP", "010");
                    ht.put("LICENSETYPE", "2823");
                    ht.put("CHARGEOPTION", "377");

                    if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                    {
                        String key;
                        String geo;
                        EntityItem swFeatureItem = (EntityItem) entityUpLink;

                        featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                        featureCode = featureCode + "      ";
                        featureCode = featureCode.substring(0, 6);
                        key = machType + model + "flf4" + featureCode + swProdStructItem.getKey();
                        geo = getGeo(swProdStructItem.getKey());

                        base.put(key, geo);
                    }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))

                    ht.clear();
                    //319 = ValueMetric
                    //355 = FlatFee
                    //010 = N/A
                    //2824 = DSLO
                    //380 = OTC
                    ht.put("SWFCCAT", "319");
                    ht.put("SWFCSUBCAT", "355");
                    //ht.put("SWFCGRP", "010");
                    ht.put("LICENSETYPE", "2824");
                    ht.put("CHARGEOPTION", "380");

                    if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                    {
                        String key;
                        String geo;
                        EntityItem swFeatureItem = (EntityItem) entityUpLink;

                        featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                        featureCode = featureCode + "      ";
                        featureCode = featureCode.substring(0, 6);
                        key = machType + model + "flf5" + featureCode + swProdStructItem.getKey();
                        geo = getGeo(swProdStructItem.getKey());

                        base.put(key, geo);
                    }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))

                    ht.clear();
                    //319 = ValueMetric
                    //355 = FlatFee
                    //010 = N/A
                    //2824 = DSLO
                    //378 = MLC
                    ht.put("SWFCCAT", "319");
                    ht.put("SWFCSUBCAT", "355");
                    //ht.put("SWFCGRP", "010");
                    ht.put("LICENSETYPE", "2824");
                    ht.put("CHARGEOPTION", "378");

                    if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                    {
                        String key;
                        String geo;
                        EntityItem swFeatureItem = (EntityItem) entityUpLink;

                        featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                        featureCode = featureCode + "      ";
                        featureCode = featureCode.substring(0, 6);
                        key = machType + model + "flf6" + featureCode + swProdStructItem.getKey();
                        geo = getGeo(swProdStructItem.getKey());

                        base.put(key, geo);
                    }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))

                    ht.clear();
                    //319 = ValueMetric
                    //355 = FlatFee
                    //010 = N/A
                    //2824 = DSLO
                    //379 = PLC
                    ht.put("SWFCCAT", "319");
                    ht.put("SWFCSUBCAT", "355");
                    //ht.put("SWFCGRP", "010");
                    ht.put("LICENSETYPE", "2824");
                    ht.put("CHARGEOPTION", "379");

                    if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                    {
                        String key;
                        String geo;
                        EntityItem swFeatureItem = (EntityItem) entityUpLink;

                        featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                        featureCode = featureCode + "      ";
                        featureCode = featureCode.substring(0, 6);
                        key = machType + model + "flf7" + featureCode + swProdStructItem.getKey();
                        geo = getGeo(swProdStructItem.getKey());

                        base.put(key, geo);
                    }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))

                    ht.clear();
                    //319 = ValueMetric
                    //355 = FlatFee
                    //010 = N/A
                    //2824 = DSLO
                    //377 = RLC
                    ht.put("SWFCCAT", "319");
                    ht.put("SWFCSUBCAT", "355");
                    //ht.put("SWFCGRP", "010");
                    ht.put("LICENSETYPE", "2824");
                    ht.put("CHARGEOPTION", "377");

                    if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                    {
                        String key;
                        String geo;
                        EntityItem swFeatureItem = (EntityItem) entityUpLink;

                        featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                        featureCode = featureCode + "      ";
                        featureCode = featureCode.substring(0, 6);
                        key = machType + model + "flf8" + featureCode + swProdStructItem.getKey();
                        geo = getGeo(swProdStructItem.getKey());

                        base.put(key, geo);
                    }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))

                    ht.clear();
                    //319 = ValueMetric
                    //353 = EntitleReg
                    //010 = N/A
                    //2823 = BASIC
                    ht.put("SWFCCAT", "319");
                    ht.put("SWFCSUBCAT", "353");
                    //ht.put("SWFCGRP", "010");
                    ht.put("LICENSETYPE", "2823");

                    if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                    {
                        String key;
                        String geo;
                        EntityItem swFeatureItem = (EntityItem) entityUpLink;

                        featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                        featureCode = featureCode + "      ";
                        featureCode = featureCode.substring(0, 6);
                        key = machType + model + "xer1" + featureCode + swProdStructItem.getKey();
                        geo = getGeo(swProdStructItem.getKey());

                        base.put(key, geo);
                    }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))

                    ht.clear();
                    //319 = ValueMetric
                    //353 = EntitleReg
                    //010 = N/A
                    //2824 = DSLO
                    ht.put("SWFCCAT", "319");
                    ht.put("SWFCSUBCAT", "353");
                    //ht.put("SWFCGRP", "010");
                    ht.put("LICENSETYPE", "2824");

                    if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                    {
                        String key;
                        String geo;
                        EntityItem swFeatureItem = (EntityItem) entityUpLink;

                        featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                        featureCode = featureCode + "      ";
                        featureCode = featureCode.substring(0, 6);
                        key = machType + model + "xer2" + featureCode + swProdStructItem.getKey();
                        geo = getGeo(swProdStructItem.getKey());

                        base.put(key, geo);
                    }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                }//end of for(int n = 0; n < upLinkEntityItemVector.size(); n++)
            }//end of if(swei.containModelBase(baseModel))
        }//end of for(int i = 0; i < Q352A1AppBase.size(); i++)

        //Get all SWPRODSTRUCT associate with each optional feature which associates with base MODEL.
        ofb = new TreeSet();
        ofbHT = new Hashtable();
        for(int i = 0; i < Q352A1AppOptFeaBase1.size(); i++)
        {
            SWEntityItem_OIM swei = (SWEntityItem_OIM) Q352A1AppOptFeaBase1.get(i);

            if(swei.containModelBase(baseModel))
            {
                EntityItem OptFeaBaseModel = swei.getOptFeaBaseModel();
                String techName = PokUtils.getAttributeValue(OptFeaBaseModel, "MKTGNAME", "|", "", false);
                ofb.add(OptFeaBaseModel.getKey());
                if(techName.length() > I_128)
                {
                    techName = techName.substring(0, I_128);
                }
                ofbHT.put(OptFeaBaseModel.getKey(), techName);
            }
        }//end of for(int i = 0; i < Q352A1AppOptFeaBase1.size(); i++)

        optionalFeatureBase = new TreeMap[ofb.size()];
        for(int i = 0; i < optionalFeatureBase.length; i++)
        {
            optionalFeatureBase[i] = new TreeMap();
        }
        arrayTechName = new String[ofb.size()];

        tsIterator = ofb.iterator();
        index = 0;
        while(tsIterator.hasNext())
        {
            String optFeaBaseModel = (String) tsIterator.next();
            for(int i = 0; i < Q352A1AppOptFeaBase2.size(); i++)
            {
                SWEntityItem_OIM swei = (SWEntityItem_OIM) Q352A1AppOptFeaBase2.get(i);

                if(swei.containModelOptFeaBase(optFeaBaseModel))
                {
                    EntityItem swProdStructItem = swei.getSWProdStruct();

                    Vector upLinkEntityItemVector = getUpLinkEntityItems(swProdStructItem, "SWFEATURE");
                    for(int n = 0; n < upLinkEntityItemVector.size(); n++)
                    {
                        EANEntity entityUpLink  = (EANEntity) upLinkEntityItemVector.get(n);

                        ht.clear();
                        //319 = ValueMetric
                        //354 = FlatBase
                        //010 = N/A
                        //2823 = BASIC
                        //380 = OTC
                        ht.put("SWFCCAT", "319");
                        ht.put("SWFCSUBCAT", "354");
                        //ht.put("SWFCGRP", "010");
                        ht.put("LICENSETYPE", "2823");
                        ht.put("CHARGEOPTION", "380");

                        if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                        {
                            String key;
                            String geo;
                            String techName;
                            EntityItem swFeatureItem = (EntityItem) entityUpLink;

                            featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                            featureCode = featureCode + "      ";
                            featureCode = featureCode.substring(0, 6);
                            key = machType + model + "flb1" + featureCode + swProdStructItem.getKey();
                            geo = getGeo(swProdStructItem.getKey());

                            optionalFeatureBase[index].put(key, geo);
                            techName = (String)ofbHT.get(optFeaBaseModel);

                            arrayTechName[index] = techName;
                        }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))

                        ht.clear();
                        //319 = ValueMetric
                        //354 = FlatBase
                        //010 = N/A
                        //2823 = BASIC
                        //378 = MLC
                        ht.put("SWFCCAT", "319");
                        ht.put("SWFCSUBCAT", "354");
                        //ht.put("SWFCGRP", "010");
                        ht.put("LICENSETYPE", "2823");
                        ht.put("CHARGEOPTION", "378");

                        if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                        {
                            String key;
                            String geo;
                            String techName;
                            EntityItem swFeatureItem = (EntityItem) entityUpLink;

                            featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                            featureCode = featureCode + "      ";
                            featureCode = featureCode.substring(0, 6);
                            key = machType + model + "flb2" + featureCode + swProdStructItem.getKey();
                            geo = getGeo(swProdStructItem.getKey());

                            optionalFeatureBase[index].put(key, geo);
                            techName = (String)ofbHT.get(optFeaBaseModel);

                            arrayTechName[index] = techName;
                        }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))

                        ht.clear();
                        //319 = ValueMetric
                        //354 = FlatBase
                        //010 = N/A
                        //2823 = BASIC
                        //379 = PLC
                        ht.put("SWFCCAT", "319");
                        ht.put("SWFCSUBCAT", "354");
                        //ht.put("SWFCGRP", "010");
                        ht.put("LICENSETYPE", "2823");
                        ht.put("CHARGEOPTION", "379");

                        if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                        {
                            String key;
                            String geo;
                            String techName;
                            EntityItem swFeatureItem = (EntityItem) entityUpLink;

                            featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                            featureCode = featureCode + "      ";
                            featureCode = featureCode.substring(0, 6);
                            key = machType + model + "flb3" + featureCode + swProdStructItem.getKey();
                            geo = getGeo(swProdStructItem.getKey());

                            optionalFeatureBase[index].put(key, geo);
                            techName = (String)ofbHT.get(optFeaBaseModel);

                            arrayTechName[index] = techName;
                        }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))

                        ht.clear();
                        //319 = ValueMetric
                        //354 = FlatBase
                        //010 = N/A
                        //2823 = BASIC
                        //377 = RLC
                        ht.put("SWFCCAT", "319");
                        ht.put("SWFCSUBCAT", "354");
                        //ht.put("SWFCGRP", "010");
                        ht.put("LICENSETYPE", "2823");
                        ht.put("CHARGEOPTION", "377");

                        if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                        {
                            String key;
                            String geo;
                            String techName;
                            EntityItem swFeatureItem = (EntityItem) entityUpLink;

                            featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                            featureCode = featureCode + "      ";
                            featureCode = featureCode.substring(0, 6);
                            key = machType + model + "flb4" + featureCode + swProdStructItem.getKey();
                            geo = getGeo(swProdStructItem.getKey());

                            optionalFeatureBase[index].put(key, geo);
                            techName = (String)ofbHT.get(optFeaBaseModel);

                            arrayTechName[index] = techName;
                        }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))

                        ht.clear();
                        //319 = ValueMetric
                        //354 = FlatBase
                        //010 = N/A
                        //2824 = DSLO
                        //380 = OTC
                        ht.put("SWFCCAT", "319");
                        ht.put("SWFCSUBCAT", "354");
                        //ht.put("SWFCGRP", "010");
                        ht.put("LICENSETYPE", "2824");
                        ht.put("CHARGEOPTION", "380");

                        if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                        {
                            String key;
                            String geo;
                            String techName;
                            EntityItem swFeatureItem = (EntityItem) entityUpLink;

                            featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                            featureCode = featureCode + "      ";
                            featureCode = featureCode.substring(0, 6);
                            key = machType + model + "flb5" + featureCode + swProdStructItem.getKey();
                            geo = getGeo(swProdStructItem.getKey());

                            optionalFeatureBase[index].put(key, geo);
                            techName = (String)ofbHT.get(optFeaBaseModel);

                            arrayTechName[index] = techName;
                        }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))

                        ht.clear();
                        //319 = ValueMetric
                        //354 = FlatBase
                        //010 = N/A
                        //2824 = DSLO
                        //378 = MLC
                        ht.put("SWFCCAT", "319");
                        ht.put("SWFCSUBCAT", "354");
                        //ht.put("SWFCGRP", "010");
                        ht.put("LICENSETYPE", "2824");
                        ht.put("CHARGEOPTION", "378");

                        if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                        {
                            String key;
                            String geo;
                            String techName;
                            EntityItem swFeatureItem = (EntityItem) entityUpLink;

                            featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                            featureCode = featureCode + "      ";
                            featureCode = featureCode.substring(0, 6);
                            key = machType + model + "flb6" + featureCode + swProdStructItem.getKey();
                            geo = getGeo(swProdStructItem.getKey());

                            optionalFeatureBase[index].put(key, geo);
                            techName = (String)ofbHT.get(optFeaBaseModel);

                            arrayTechName[index] = techName;
                        }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))

                        ht.clear();
                        //319 = ValueMetric
                        //354 = FlatBase
                        //010 = N/A
                        //2824 = DSLO
                        //379 = PLC
                        ht.put("SWFCCAT", "319");
                        ht.put("SWFCSUBCAT", "354");
                        //ht.put("SWFCGRP", "010");
                        ht.put("LICENSETYPE", "2824");
                        ht.put("CHARGEOPTION", "379");

                        if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                        {
                            String key;
                            String geo;
                            String techName;
                            EntityItem swFeatureItem = (EntityItem) entityUpLink;

                            featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                            featureCode = featureCode + "      ";
                            featureCode = featureCode.substring(0, 6);
                            key = machType + model + "flb7" + featureCode + swProdStructItem.getKey();
                            geo = getGeo(swProdStructItem.getKey());

                            optionalFeatureBase[index].put(key, geo);
                            techName = (String)ofbHT.get(optFeaBaseModel);

                            arrayTechName[index] = techName;
                        }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))

                        ht.clear();
                        //319 = ValueMetric
                        //354 = FlatBase
                        //010 = N/A
                        //2824 = DSLO
                        //377 = RLC
                        ht.put("SWFCCAT", "319");
                        ht.put("SWFCSUBCAT", "354");
                        //ht.put("SWFCGRP", "010");
                        ht.put("LICENSETYPE", "2824");
                        ht.put("CHARGEOPTION", "377");

                        if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                        {
                            String key;
                            String geo;
                            String techName;
                            EntityItem swFeatureItem = (EntityItem) entityUpLink;

                            featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                            featureCode = featureCode + "      ";
                            featureCode = featureCode.substring(0, 6);
                            key = machType + model + "flb8" + featureCode + swProdStructItem.getKey();
                            geo = getGeo(swProdStructItem.getKey());

                            optionalFeatureBase[index].put(key, geo);
                            techName = (String)ofbHT.get(optFeaBaseModel);

                            arrayTechName[index] = techName;
                        }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))

                        ht.clear();
                        //319 = ValueMetric
                        //355 = FlatFee
                        //010 = N/A
                        //2823 = BASIC
                        //380 = OTC
                        ht.put("SWFCCAT", "319");
                        ht.put("SWFCSUBCAT", "355");
                        //ht.put("SWFCGRP", "010");
                        ht.put("LICENSETYPE", "2823");
                        ht.put("CHARGEOPTION", "380");

                        if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                        {
                            String key;
                            String geo;
                            String techName;
                            EntityItem swFeatureItem = (EntityItem) entityUpLink;

                            featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                            featureCode = featureCode + "      ";
                            featureCode = featureCode.substring(0, 6);
                            key = machType + model + "flf1" + featureCode + swProdStructItem.getKey();
                            geo = getGeo(swProdStructItem.getKey());

                            optionalFeatureBase[index].put(key, geo);
                            techName = (String)ofbHT.get(optFeaBaseModel);

                            arrayTechName[index] = techName;
                        }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))

                        ht.clear();
                        //319 = ValueMetric
                        //355 = FlatFee
                        //010 = N/A
                        //2823 = BASIC
                        //378 = MLC
                        ht.put("SWFCCAT", "319");
                        ht.put("SWFCSUBCAT", "355");
                        //ht.put("SWFCGRP", "010");
                        ht.put("LICENSETYPE", "2823");
                        ht.put("CHARGEOPTION", "378");

                        if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                        {
                            String key;
                            String geo;
                            String techName;
                            EntityItem swFeatureItem = (EntityItem) entityUpLink;

                            featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                            featureCode = featureCode + "      ";
                            featureCode = featureCode.substring(0, 6);
                            key = machType + model + "flf2" + featureCode + swProdStructItem.getKey();
                            geo = getGeo(swProdStructItem.getKey());

                            optionalFeatureBase[index].put(key, geo);
                            techName = (String)ofbHT.get(optFeaBaseModel);

                            arrayTechName[index] = techName;
                        }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))

                        ht.clear();
                        //319 = ValueMetric
                        //355 = FlatFee
                        //010 = N/A
                        //2823 = BASIC
                        //379 = PLC
                        ht.put("SWFCCAT", "319");
                        ht.put("SWFCSUBCAT", "355");
                        //ht.put("SWFCGRP", "010");
                        ht.put("LICENSETYPE", "2823");
                        ht.put("CHARGEOPTION", "379");

                        if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                        {
                            String key;
                            String geo;
                            String techName;
                            EntityItem swFeatureItem = (EntityItem) entityUpLink;

                            featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                            featureCode = featureCode + "      ";
                            featureCode = featureCode.substring(0, 6);
                            key = machType + model + "flf3" + featureCode + swProdStructItem.getKey();
                            geo = getGeo(swProdStructItem.getKey());

                            optionalFeatureBase[index].put(key, geo);
                            techName = (String)ofbHT.get(optFeaBaseModel);

                            arrayTechName[index] = techName;
                        }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))

                        ht.clear();
                        //319 = ValueMetric
                        //355 = FlatFee
                        //010 = N/A
                        //2823 = BASIC
                        //377 = RLC
                        ht.put("SWFCCAT", "319");
                        ht.put("SWFCSUBCAT", "355");
                        //ht.put("SWFCGRP", "010");
                        ht.put("LICENSETYPE", "2823");
                        ht.put("CHARGEOPTION", "377");

                        if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                        {
                            String key;
                            String geo;
                            String techName;
                            EntityItem swFeatureItem = (EntityItem) entityUpLink;

                            featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                            featureCode = featureCode + "      ";
                            featureCode = featureCode.substring(0, 6);
                            key = machType + model + "flf4" + featureCode + swProdStructItem.getKey();
                            geo = getGeo(swProdStructItem.getKey());

                            optionalFeatureBase[index].put(key, geo);
                            techName = (String)ofbHT.get(optFeaBaseModel);

                            arrayTechName[index] = techName;
                        }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))

                        ht.clear();
                        //319 = ValueMetric
                        //355 = FlatFee
                        //010 = N/A
                        //2824 = DSLO
                        //380 = OTC
                        ht.put("SWFCCAT", "319");
                        ht.put("SWFCSUBCAT", "355");
                        //ht.put("SWFCGRP", "010");
                        ht.put("LICENSETYPE", "2824");
                        ht.put("CHARGEOPTION", "380");

                        if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                        {
                            String key;
                            String geo;
                            String techName;
                            EntityItem swFeatureItem = (EntityItem) entityUpLink;

                            featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                            featureCode = featureCode + "      ";
                            featureCode = featureCode.substring(0, 6);
                            key = machType + model + "flf5" + featureCode + swProdStructItem.getKey();
                            geo = getGeo(swProdStructItem.getKey());

                            optionalFeatureBase[index].put(key, geo);
                            techName = (String)ofbHT.get(optFeaBaseModel);

                            arrayTechName[index] = techName;
                        }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))

                        ht.clear();
                        //319 = ValueMetric
                        //355 = FlatFee
                        //010 = N/A
                        //2824 = DSLO
                        //378 = MLC
                        ht.put("SWFCCAT", "319");
                        ht.put("SWFCSUBCAT", "355");
                        //ht.put("SWFCGRP", "010");
                        ht.put("LICENSETYPE", "2824");
                        ht.put("CHARGEOPTION", "378");

                        if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                        {
                            String key;
                            String geo;
                            String techName;
                            EntityItem swFeatureItem = (EntityItem) entityUpLink;

                            featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                            featureCode = featureCode + "      ";
                            featureCode = featureCode.substring(0, 6);
                            key = machType + model + "flf6" + featureCode + swProdStructItem.getKey();
                            geo = getGeo(swProdStructItem.getKey());

                            optionalFeatureBase[index].put(key, geo);
                            techName = (String)ofbHT.get(optFeaBaseModel);

                            arrayTechName[index] = techName;
                        }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))

                        ht.clear();
                        //319 = ValueMetric
                        //355 = FlatFee
                        //010 = N/A
                        //2824 = DSLO
                        //379 = PLC
                        ht.put("SWFCCAT", "319");
                        ht.put("SWFCSUBCAT", "355");
                        //ht.put("SWFCGRP", "010");
                        ht.put("LICENSETYPE", "2824");
                        ht.put("CHARGEOPTION", "379");

                        if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                        {
                            String key;
                            String geo;
                            String techName;
                            EntityItem swFeatureItem = (EntityItem) entityUpLink;

                            featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                            featureCode = featureCode + "      ";
                            featureCode = featureCode.substring(0, 6);
                            key = machType + model + "flf7" + featureCode + swProdStructItem.getKey();
                            geo = getGeo(swProdStructItem.getKey());

                            optionalFeatureBase[index].put(key, geo);
                            techName = (String)ofbHT.get(optFeaBaseModel);

                            arrayTechName[index] = techName;
                        }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))

                        ht.clear();
                        //319 = ValueMetric
                        //355 = FlatFee
                        //010 = N/A
                        //2824 = DSLO
                        //377 = RLC
                        ht.put("SWFCCAT", "319");
                        ht.put("SWFCSUBCAT", "355");
                        //ht.put("SWFCGRP", "010");
                        ht.put("LICENSETYPE", "2824");
                        ht.put("CHARGEOPTION", "377");

                        if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                        {
                            String key;
                            String geo;
                            String techName;
                            EntityItem swFeatureItem = (EntityItem) entityUpLink;

                            featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                            featureCode = featureCode + "      ";
                            featureCode = featureCode.substring(0, 6);
                            key = machType + model + "flf8" + featureCode + swProdStructItem.getKey();
                            geo = getGeo(swProdStructItem.getKey());

                            optionalFeatureBase[index].put(key, geo);
                            techName = (String)ofbHT.get(optFeaBaseModel);

                            arrayTechName[index] = techName;
                        }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))

                        ht.clear();
                        //319 = ValueMetric
                        //353 = EntitleReg
                        //010 = N/A
                        //2823 = BASIC
                        ht.put("SWFCCAT", "319");
                        ht.put("SWFCSUBCAT", "353");
                        //ht.put("SWFCGRP", "010");
                        ht.put("LICENSETYPE", "2823");

                        if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                        {
                            String key;
                            String geo;
                            String techName;
                            EntityItem swFeatureItem = (EntityItem) entityUpLink;

                            featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                            featureCode = featureCode + "      ";
                            featureCode = featureCode.substring(0, 6);
                            key = machType + model + "xer1" + featureCode + swProdStructItem.getKey();
                            geo = getGeo(swProdStructItem.getKey());

                            optionalFeatureBase[index].put(key, geo);
                            techName = (String)ofbHT.get(optFeaBaseModel);

                            arrayTechName[index] = techName;
                        }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))

                        ht.clear();
                        //319 = ValueMetric
                        //353 = EntitleReg
                        //010 = N/A
                        //2824 = DSLO
                        ht.put("SWFCCAT", "319");
                        ht.put("SWFCSUBCAT", "353");
                        //ht.put("SWFCGRP", "010");
                        ht.put("LICENSETYPE", "2824");

                        if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                        {
                            String key;
                            String geo;
                            String techName;
                            EntityItem swFeatureItem = (EntityItem) entityUpLink;

                            featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                            featureCode = featureCode + "      ";
                            featureCode = featureCode.substring(0, 6);
                            key = machType + model + "xer2" + featureCode + swProdStructItem.getKey();
                            geo = getGeo(swProdStructItem.getKey());

                            optionalFeatureBase[index].put(key, geo);
                            techName = (String)ofbHT.get(optFeaBaseModel);

                            arrayTechName[index] = techName;
                        }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                    }//end of for(int n = 0; n < upLinkEntityItemVector.size(); n++)
                }//end of if(swei.containCOFOptFeaBase(optFeaBaseCOF))
            }//end of for(int i = 0; i < Q352A1AppOptFeaBase2.size(); i++)

            index++;
        }//end of while(tsIterator.hasNext())

        baseModelTechName = PokUtils.getAttributeValue(baseModel, "MKTGNAME", "|", "", false);
        if(baseModelTechName.length() > I_128)
        {
            baseModelTechName = baseModelTechName.substring(0, I_128);
        }

        //Count number of distinct columns
        ts = new TreeSet();

        aSet = base.keySet();
        itr = aSet.iterator();
        while(itr.hasNext())
        {
            String str = (String) itr.next();
            String perfgrp = str.substring(I_7, I_11);
            ts.add(perfgrp);
        }

        for(int i = 0; i < optionalFeatureBase.length; i++)
        {
            aSet = optionalFeatureBase[i].keySet();
            itr = aSet.iterator();
            while(itr.hasNext())
            {
                String str = (String) itr.next();
                String perfgrp = str.substring(I_7, I_11);
                ts.add(perfgrp);
            }
        }

        count = 0;

        //Column positions
        cursorPos = new int[4];
        cursorPos[0] = I_23;
        cursorPos[1] = I_35;
        cursorPos[2] = I_47;
        cursorPos[3] = I_59;

        arrayObj = ts.toArray();

        pos = new TreeMap();

        if(arrayObj.length > 0)
        {
            if(showInitialOrderHeader)
            {
                sb.append(":hp1.Initial Order:ehp1." + NEWLINE);
                sbQ523.append(":hp1.Initial Order:ehp1." + NEWLINE);
                showInitialOrderHeader = false;
            }
            sb.append(":xmp." + NEWLINE);
            sbQ523.append(":xmp." + NEWLINE);
        }
        for(int i = 0; i < arrayObj.length; i++)
        {
            if(count < 4)
            {
                pos.put((String) arrayObj[i], new Integer(cursorPos[i % 4]));
                count++;
            }
            if((count == 4) || (i == arrayObj.length - 1))
            {
                count = 0;

                displayInitialBillingCodeFlatBaseFlatFeeEntitleReg(pos, base, baseModelTechName, optionalFeatureBase, arrayTechName);
                pos.clear();
            }
        }
        if(arrayObj.length > 0)
        {
            sb.append(":exmp." + NEWLINE);
            sbQ523.append(":exmp." + NEWLINE);
        }
    }

    /********************************************************************************
    * displayInitialBillingCodeFlatBaseFlatFeeEntitleReg()
    *
    * @param pos TreeMap
    * @param base TreeMap
    * @param baseModelTechName String
    * @param optionalFeatureBase TreeMap []
    * @param arrayTechName String []
    */
    private void displayInitialBillingCodeFlatBaseFlatFeeEntitleReg(TreeMap pos, TreeMap base, String baseModelTechName, TreeMap [] optionalFeatureBase, String [] arrayTechName)
    {
        Set set;
        Iterator itr;
        int count;
        int numPerformanceGroup = pos.size();
        int lastPGPosition;

        Integer aInteger = (Integer) pos.get(pos.lastKey());
        lastPGPosition = aInteger.intValue();

        printChar(sb, I_22 + I_12*numPerformanceGroup - 1, "-", true);
        printChar(sbQ523, I_22 + I_12*numPerformanceGroup - 1, "-", true);
        sb.append(NEWLINE);
        sbQ523.append(NEWLINE);
        sb.append("|");
        sbQ523.append("|");
        sb.append(machType + "-" + model);
        sbQ523.append(machType + "-" + model);
        printChar(sb, I_12, " ", false);
        printChar(sbQ523, I_12, " ", false);
        sb.append("|");
        sbQ523.append("|");

        set = pos.keySet();

        itr = set.iterator();

        count = 0;
        while(itr.hasNext())
        {
            String aStringKey = (String) itr.next();
            count++;
            if(aStringKey.equals("flb1") || aStringKey.equals("flb2") || aStringKey.equals("flb3") || aStringKey.equals("flb4") ||
                aStringKey.equals("flb5") || aStringKey.equals("flb6") || aStringKey.equals("flb7") || aStringKey.equals("flb8"))
            {
                aStringKey = "FlatBase  ";
            }
            else if(aStringKey.equals("flf1") || aStringKey.equals("flf2") || aStringKey.equals("flf3") || aStringKey.equals("flf4") ||
                aStringKey.equals("flf5") || aStringKey.equals("flf6") || aStringKey.equals("flf7") || aStringKey.equals("flf8"))
            {
                aStringKey = "FlatFee   ";
            }
            else if(aStringKey.equals("xer1") || aStringKey.equals("xer2"))
            {
                aStringKey = "EntitleReg";
            }
            if(count < set.size())
            {
                sb.append(aStringKey + "  ");
                sbQ523.append(aStringKey + "  ");
            }
            else
            {
                sb.append(aStringKey + "");
                sbQ523.append(aStringKey + "");
            }
        }
        sb.append("|" + NEWLINE);
        sbQ523.append("|" + NEWLINE);
        printChar(sb, I_22, " ", true);
        printChar(sbQ523, I_22, " ", true);

        itr = set.iterator();

        count = 0;
        while(itr.hasNext())
        {
            String aStringKey = (String) itr.next();
            count++;
            if(aStringKey.equals("flb1") || aStringKey.equals("flb2") || aStringKey.equals("flb3") || aStringKey.equals("flb4") ||
                aStringKey.equals("flf1") || aStringKey.equals("flf2") || aStringKey.equals("flf3") || aStringKey.equals("flf4") || aStringKey.equals("xer1"))
            {
                aStringKey = "Basic     ";
            }
            else if(aStringKey.equals("flb5") || aStringKey.equals("flb6") || aStringKey.equals("flb7") || aStringKey.equals("flb8") ||
                aStringKey.equals("flf5") || aStringKey.equals("flf6") || aStringKey.equals("flf7") || aStringKey.equals("flf8") || aStringKey.equals("xer2"))
            {
                aStringKey = "DSLO      ";
            }
            if(count < set.size())
            {
                sb.append(aStringKey + "  ");
                sbQ523.append(aStringKey + "  ");
            }
            else
            {
                sb.append(aStringKey + "");
                sbQ523.append(aStringKey + "");
            }
        }
        sb.append("|" + NEWLINE);
        sbQ523.append("|" + NEWLINE);
        printChar(sb, I_22, " ", true);
        printChar(sbQ523, I_22, " ", true);

        itr = set.iterator();

        count = 0;
        while(itr.hasNext())
        {
            String aStringKey = (String) itr.next();
            count++;
            if(aStringKey.equals("flb1") || aStringKey.equals("flb5") || aStringKey.equals("flf1") || aStringKey.equals("flf5"))
            {
                aStringKey = "OTC       ";
            }
            else if(aStringKey.equals("flb2") || aStringKey.equals("flb6") || aStringKey.equals("flf2") || aStringKey.equals("flf6"))
            {
                aStringKey = "MLC       ";
            }
            else if(aStringKey.equals("flb3") || aStringKey.equals("flb7") || aStringKey.equals("flf3") || aStringKey.equals("flf7"))
            {
                aStringKey = "PLC       ";
            }
            else if(aStringKey.equals("flb4") || aStringKey.equals("flb8") || aStringKey.equals("flf4") || aStringKey.equals("flf8"))
            {
                aStringKey = "RLC       ";
            }
            else if(aStringKey.equals("xer1") || aStringKey.equals("xer2"))
            {
                aStringKey = "          ";
            }

            if(count < set.size())
            {
                sb.append(aStringKey + "  ");
                sbQ523.append(aStringKey + "  ");
            }
            else
            {
                sb.append(aStringKey + "");
                sbQ523.append(aStringKey + "");
            }
        }
        sb.append("|" + NEWLINE);
        sbQ523.append("|" + NEWLINE);

        printChar(sb, I_22 + I_12*numPerformanceGroup - 1, "-", true);
        printChar(sbQ523, I_22 + I_12*numPerformanceGroup - 1, "-", true);
        sb.append(NEWLINE);
        sbQ523.append(NEWLINE);

        displayInitialBillingCodeOther(pos, base, numPerformanceGroup, lastPGPosition, baseModelTechName);
        for(int i = 0; i < optionalFeatureBase.length; i++)
        {
            displayInitialBillingCodeOther(pos, optionalFeatureBase[i], numPerformanceGroup, lastPGPosition, arrayTechName[i]);
        }
    }

    /********************************************************************************
    * displayInitialBillingCodeUseBlockUseEntitleBlock()
    *
    */
    private void displayInitialBillingCodeUseBlockUseEntitleBlock()
    {
        //Need to print a separate row for base application and one for each optional feature.
        TreeMap base = new TreeMap();
        String featureCode;
        String blockQuantity;

        TreeMap [] optionalFeatureBase;
        TreeSet ofb;
        Hashtable ofbHT;
        String [] arrayTechName;

        Iterator tsIterator;
        int index;
        String baseModelTechName;
        TreeSet ts;
        Set aSet;
        Iterator itr;
        int count;
        int [] cursorPos;
        Object [] arrayObj;
        TreeMap pos;

        Hashtable ht = new Hashtable();

        //Get all SWPRODSTRUCT associate with base MODEL. This is for MODEL(SW-Application/Subscription/Maintenance/Support-Base)
        for(int i = 0; i < Q352A1AppBase.size(); i++)
        {
            SWEntityItem_OIM swei = (SWEntityItem_OIM) Q352A1AppBase.get(i);

            if(swei.containModelBase(baseModel))
            {
                EntityItem swProdStructItem = swei.getSWProdStruct();

                Vector upLinkEntityItemVector = getUpLinkEntityItems(swProdStructItem, "SWFEATURE");
                for(int n = 0; n < upLinkEntityItemVector.size(); n++)
                {
                    EANEntity entityUpLink  = (EANEntity) upLinkEntityItemVector.get(n);

                    ht.clear();
                    //319 = ValueMetric
                    //358 = UseBlock
                    //010 = N/A
                    ht.put("SWFCCAT", "319");
                    ht.put("SWFCSUBCAT", "358");
                    //ht.put("SWFCGRP", "010");

                    if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                    {
                        String key;
                        String geo;
                        EntityItem swFeatureItem = (EntityItem) entityUpLink;

                        featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                        featureCode = featureCode + "      ";
                        featureCode = featureCode.substring(0, 6);
                        blockQuantity = PokUtils.getAttributeValue(swFeatureItem, "BLOCKQUANTITY", "|", "0");
                        if(blockQuantity.length() == 1)
                        {
                            blockQuantity = "00" + blockQuantity;
                        }
                        else if (blockQuantity.length() == 2)
                        {
                            blockQuantity = "0" + blockQuantity;
                        }
                        blockQuantity = blockQuantity.substring(0, 3);

                        key = machType + model + "u" + blockQuantity + featureCode + swProdStructItem.getKey();
                        geo = getGeo(swProdStructItem.getKey());

                        base.put(key, geo);
                    }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))

                    ht.clear();
                    //319 = ValueMetric
                    //360 = UseEntitleBlock
                    //010 = N/A
                    ht.put("SWFCCAT", "319");
                    ht.put("SWFCSUBCAT", "360");
                    //ht.put("SWFCGRP", "010");

                    if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                    {
                        String key;
                        String geo;
                        EntityItem swFeatureItem = (EntityItem) entityUpLink;

                        featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                        featureCode = featureCode + "      ";
                        featureCode = featureCode.substring(0, 6);
                        blockQuantity = PokUtils.getAttributeValue(swFeatureItem, "BLOCKQUANTITY", "|", "0");
                        if(blockQuantity.length() == 1)
                        {
                            blockQuantity = "00" + blockQuantity;
                        }
                        else if (blockQuantity.length() == 2)
                        {
                            blockQuantity = "0" + blockQuantity;
                        }
                        blockQuantity = blockQuantity.substring(0, 3);

                        key = machType + model + "u" + blockQuantity + featureCode + swProdStructItem.getKey();
                        geo = getGeo(swProdStructItem.getKey());

                        base.put(key, geo);
                    }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                }//end of for(int n = 0; n < upLinkEntityItemVector.size(); n++)
            }//end of if(swei.containCOFBase(baseCOF))
        }//end of for(int i = 0; i < Q352A1AppBase.size(); i++)

        //Get all SWPRODSTRUCT associate with each optional feature which associates with base MODEL.
        ofb = new TreeSet();
        ofbHT = new Hashtable();
        for(int i = 0; i < Q352A1AppOptFeaBase1.size(); i++)
        {
            SWEntityItem_OIM swei = (SWEntityItem_OIM) Q352A1AppOptFeaBase1.get(i);

            if(swei.containModelBase(baseModel))
            {
                EntityItem OptFeaBaseModel = swei.getOptFeaBaseModel();
                String techName = PokUtils.getAttributeValue(OptFeaBaseModel, "MKTGNAME", "|", "", false);
                ofb.add(OptFeaBaseModel.getKey());
                if(techName.length() > I_128)
                {
                    techName = techName.substring(0, I_128);
                }
                ofbHT.put(OptFeaBaseModel.getKey(), techName);
            }
        }//end of for(int i = 0; i < Q352A1AppOptFeaBase1.size(); i++)

        optionalFeatureBase = new TreeMap[ofb.size()];
        for(int i = 0; i < optionalFeatureBase.length; i++)
        {
            optionalFeatureBase[i] = new TreeMap();
        }
        arrayTechName = new String[ofb.size()];

        tsIterator = ofb.iterator();
        index = 0;
        while(tsIterator.hasNext())
        {
            String optFeaBaseModel = (String) tsIterator.next();
            for(int i = 0; i < Q352A1AppOptFeaBase2.size(); i++)
            {
                SWEntityItem_OIM swei = (SWEntityItem_OIM) Q352A1AppOptFeaBase2.get(i);

                if(swei.containModelOptFeaBase(optFeaBaseModel))
                {
                    EntityItem swProdStructItem = swei.getSWProdStruct();

                    Vector upLinkEntityItemVector = getUpLinkEntityItems(swProdStructItem, "SWFEATURE");
                    for(int n = 0; n < upLinkEntityItemVector.size(); n++)
                    {
                        EANEntity entityUpLink  = (EANEntity) upLinkEntityItemVector.get(n);

                        ht.clear();
                        //319 = ValueMetric
                        //358 = UseBlock
                        //010 = N/A
                        ht.put("SWFCCAT", "319");
                        ht.put("SWFCSUBCAT", "358");
                        //ht.put("SWFCGRP", "010");

                        if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                        {
                            String key;
                            String geo;
                            String techName;
                            EntityItem swFeatureItem = (EntityItem) entityUpLink;

                            featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "|", "000000");
                            featureCode = featureCode + "      ";
                            featureCode = featureCode.substring(0, 6);
                            blockQuantity = PokUtils.getAttributeValue(swFeatureItem, "BLOCKQUANTITY", "|", "0");
                            if(blockQuantity.length() == 1)
                            {
                                blockQuantity = "00" + blockQuantity;
                            }
                            else if (blockQuantity.length() == 2)
                            {
                                blockQuantity = "0" + blockQuantity;
                            }
                            blockQuantity = blockQuantity.substring(0, 3);

                            key = machType + model + "u" + blockQuantity + featureCode + swProdStructItem.getKey();
                            geo = getGeo(swProdStructItem.getKey());

                            optionalFeatureBase[index].put(key, geo);
                            techName = (String) ofbHT.get(optFeaBaseModel);

                            arrayTechName[index] = techName;
                        }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))

                        ht.clear();
                        //319 = ValueMetric
                        //360 = UseEntitleBlock
                        //010 = N/A
                        ht.put("SWFCCAT", "319");
                        ht.put("SWFCSUBCAT", "360");
                        //ht.put("SWFCGRP", "010");

                        if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                        {
                            String key;
                            String geo;
                            String techName;
                            EntityItem swFeatureItem = (EntityItem) entityUpLink;

                            featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "|", "000000");
                            featureCode = featureCode + "      ";
                            featureCode = featureCode.substring(0, 6);
                            blockQuantity = PokUtils.getAttributeValue(swFeatureItem, "BLOCKQUANTITY", "|", "0");
                            if(blockQuantity.length() == 1)
                            {
                                blockQuantity = "00" + blockQuantity;
                            }
                            else if (blockQuantity.length() == 2)
                            {
                                blockQuantity = "0" + blockQuantity;
                            }
                            blockQuantity = blockQuantity.substring(0, 3);

                            key = machType + model + "u" + blockQuantity + featureCode + swProdStructItem.getKey();
                            geo = getGeo(swProdStructItem.getKey());

                            optionalFeatureBase[index].put(key, geo);
                            techName = (String) ofbHT.get(optFeaBaseModel);

                            arrayTechName[index] = techName;
                        }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                    }//end of for(int n = 0; n < upLinkEntityItemVector.size(); n++)
                }//end of if(swei.containModelOptFeaBase(optFeaBaseModel))
            }//end of for(int i = 0; i < Q352A1AppOptFeaBase2.size(); i++)

            index++;
        }//end of while(tsIterator.hasNext())

        baseModelTechName = PokUtils.getAttributeValue(baseModel, "MKTGNAME", "|", "", false);
        if(baseModelTechName.length() > I_128)
        {
            baseModelTechName = baseModelTechName.substring(0, I_128);
        }

        //Count number of distinct columns
        ts = new TreeSet();

        aSet = base.keySet();
        itr = aSet.iterator();
        while(itr.hasNext())
        {
            String str = (String) itr.next();
            String perfgrp = str.substring(I_7, I_11);
            ts.add(perfgrp);
        }

        for(int i = 0; i < optionalFeatureBase.length; i++)
        {
            aSet = optionalFeatureBase[i].keySet();
            itr = aSet.iterator();
            while(itr.hasNext())
            {
                String str = (String) itr.next();
                String perfgrp = str.substring(I_7, I_11);
                ts.add(perfgrp);
            }
        }

        count = 0;

        //Column positions
        cursorPos = new int[4];
        cursorPos[0] = I_23;
        cursorPos[1] = I_35;
        cursorPos[2] = I_47;
        cursorPos[3] = I_59;

        arrayObj = ts.toArray();

        pos = new TreeMap();

        if(arrayObj.length > 0)
        {
            if(showInitialOrderHeader)
            {
                sb.append(":hp1.Initial Order:ehp1." + NEWLINE);
                sbQ523.append(":hp1.Initial Order:ehp1." + NEWLINE);
                showInitialOrderHeader = false;
            }
            sb.append(":xmp." + NEWLINE);
            sbQ523.append(":xmp." + NEWLINE);
        }
        for(int i = 0; i < arrayObj.length; i++)
        {
            if(count < 4)
            {
                pos.put((String) arrayObj[i], new Integer(cursorPos[i % 4]));
                count++;
            }
            if((count == 4) || (i == arrayObj.length - 1))
            {
                count = 0;

                displayInitialBillingCodeUseBlockUseEntitleBlock(pos, base, baseModelTechName, optionalFeatureBase, arrayTechName);
                pos.clear();
            }
        }
        if(arrayObj.length > 0)
        {
            sb.append(":exmp." + NEWLINE);
            sbQ523.append(":exmp." + NEWLINE);
        }
    }

    /********************************************************************************
    * displayInitialBillingCodeUseBlockUseEntitleBlock()
    *
    * @param pos TreeMap
    * @param base TreeMap
    * @param baseModelTechName String
    * @param optionalFeatureBase TreeMap []
    * @param arrayTechName String []
    * @returns void
    */
    private void displayInitialBillingCodeUseBlockUseEntitleBlock(TreeMap pos, TreeMap base, String baseModelTechName, TreeMap [] optionalFeatureBase, String [] arrayTechName)
    {
        Set set;
        Iterator itr;
        int count;
        int numPerformanceGroup = pos.size();
        int lastPGPosition;

        Integer aInteger = (Integer) pos.get(pos.lastKey());
        lastPGPosition = aInteger.intValue();

        printChar(sb, I_22 + I_12*numPerformanceGroup - 1, "-", true);
        printChar(sbQ523, I_22 + I_12*numPerformanceGroup - 1, "-", true);
        sb.append(NEWLINE);
        sbQ523.append(NEWLINE);
        sb.append("|");
        sbQ523.append("|");
        sb.append(machType + "-" + model);
        sbQ523.append(machType + "-" + model);
        printChar(sb, I_12, " ", false);
        printChar(sbQ523, I_12, " ", false);
        sb.append("|");
        sbQ523.append("|");

        set = pos.keySet();

        itr = set.iterator();

        count = 0;
        while(itr.hasNext())
        {
            String aStringKey = (String) itr.next();
            count++;
            if(aStringKey.substring(0, 1).equals("u"))
            {
                aStringKey = "Per User  ";
            }
            if(count < set.size())
            {
                sb.append(aStringKey + "  ");
                sbQ523.append(aStringKey + "  ");
            }
            else
            {
                sb.append(aStringKey + "");
                sbQ523.append(aStringKey + "");
            }
        }
        sb.append("|" + NEWLINE);
        sbQ523.append("|" + NEWLINE);
        printChar(sb, I_22, " ", true);
        printChar(sbQ523, I_22, " ", true);

        itr = set.iterator();

        count = 0;
        while(itr.hasNext())
        {
            String aStringKey = (String) itr.next();
            count++;
            if(aStringKey.substring(0, 1).equals("u"))
            {
                try
                {
                    Integer aInt = new Integer(aStringKey.substring(1, 4));
                    String s = aInt.toString();
                    s = s + "   ";
                    aStringKey = "Blk of " + s.substring(0, 3);
                }
                catch(Exception e)
                {
                    sb.append("Got exception: " + e + NEWLINE);
                    sbQ523.append("Got exception: " + e + NEWLINE);
                }
            }
            if(count < set.size())
            {
                sb.append(aStringKey + "  ");
                sbQ523.append(aStringKey + "  ");
            }
            else
            {
                sb.append(aStringKey + "");
                sbQ523.append(aStringKey + "");
            }
        }
        sb.append("|" + NEWLINE);
        sbQ523.append("|" + NEWLINE);

        printChar(sb, I_22 + I_12*numPerformanceGroup - 1, "-", true);
        printChar(sbQ523, I_22 + I_12*numPerformanceGroup - 1, "-", true);
        sb.append(NEWLINE);
        sbQ523.append(NEWLINE);

        displayInitialBillingCodeOther(pos, base, numPerformanceGroup, lastPGPosition, baseModelTechName);
        for(int i = 0; i < optionalFeatureBase.length; i++)
        {
            displayInitialBillingCodeOther(pos, optionalFeatureBase[i], numPerformanceGroup, lastPGPosition, arrayTechName[i]);
        }
    }

    /********************************************************************************
    * displayInitialBillingCodeOther()
    *
    * @param pos TreeMap
    * @param tm TreeMap
    * @param numPerformanceGroup int
    * @param lastPGPosition int
    * @param techName String
    */
    private void displayInitialBillingCodeOther(TreeMap pos, TreeMap tm, int numPerformanceGroup, int lastPGPosition, String techName)
    {
        //Group entries based on Geo
        TreeMap [] arrayTM;
        int index;
        Iterator tsItr;
        boolean printHeaderRow;
        TreeSet ts = new TreeSet();
        Set tmSet = tm.keySet();
        Iterator tmItr = tmSet.iterator();
        while(tmItr.hasNext())
        {
            ts.add(tm.get(tmItr.next()));
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
                String aKey = (String) tmItr.next();
                String pg = aKey.substring(I_7, I_11);
                if(geo.equals((String) tm.get(aKey)) && pos.containsKey(pg))
                {
                    arrayTM[index].put(aKey, geo);
                }
            }//end of while(tmItr.hasNext())
            index++;
        }//end of while(tsItr.hasNext())

        printHeaderRow = true;
        for(int i = 0; i < arrayTM.length; i++)
        {
            if(printHeaderRow && (arrayTM[i].size() > 0))
            {
                displayInitialBillingCodeOther(lastPGPosition, pos, arrayTM[i], techName, true);
                displayInitialBillingCodeOther_Q523(lastPGPosition, pos, arrayTM[i], techName, true);
                printHeaderRow = false;
            }
            else
            {
                //displayInitialBillingCodeOther(lastPGPosition, pos, arrayTM[i], techName, false);
                displayInitialBillingCodeOther(lastPGPosition, pos, arrayTM[i], techName, true);
                displayInitialBillingCodeOther_Q523(lastPGPosition, pos, arrayTM[i], techName, true);
            }
        }

        for(int i = 0; i < arrayTM.length; i++)
        {
            if(arrayTM[i].size() > 0)
            {
                printChar(sb, I_22 + I_12*numPerformanceGroup - 1, "-", true);
                printChar(sbQ523, I_22 + I_12*numPerformanceGroup - 1, "-", true);
                sb.append(NEWLINE);
                sbQ523.append(NEWLINE);
                break;
            }
        }
    }

    /********************************************************************************
    * displayInitialBillingCodeOther()
    *
    * @param lastPGPosition int
    * @param pos TreeMap
    * @param tm TreeMap
    * @param techName String
    * @param printRowHeader boolean
    */
    private void displayInitialBillingCodeOther(int lastPGPosition, TreeMap pos, TreeMap tm, String techName, boolean printRowHeader)
    {
        String currentPG = "";
        String lastPG = "";
        String featureCode = "";
        String currentGeo = "";
        String lastGeo = "";
        Integer aInteger;

        int currentCursorPos = 0;
        int posPG = 0;

        Hashtable finishedHT = new Hashtable();

        boolean geoTag = false;
        boolean printTechName = false;

        String [] strLines = extractStringLines(techName, I_20);
        int nLines = 0;

        //TreeSet priceHolderPos = new TreeSet();

        for(int i = 0; i <= tm.size(); i++)
        {
            Set aSet;
            Iterator aItr;

            if(!lastPG.equals("") && (finishedHT.size() < tm.size()))
            {
                printChar(sb, lastPGPosition + I_10 - currentCursorPos, " ", false);
                //printChar(sbQ523, lastPGPosition + 10 - currentCursorPos, " ", false);
                sb.append("|" + NEWLINE);
                //sbQ523.append("|" + NEWLINE);
                currentCursorPos = 0;
                if(printRowHeader && (nLines < strLines.length))
                {
                    sb.append("|");
                    //sbQ523.append("|");
                    sb.append(strLines[nLines]);
                    //sbQ523.append(strLines[nLines]);
                    printChar(sb, I_20 - strLines[nLines].length(), " ", false);
                    //printChar(sbQ523, 20 - strLines[nLines].length(), " ", false);
                    sb.append("|");
                    //sbQ523.append("|");
                    nLines++;
                }
                else
                {
                    printChar(sb, I_22, " ", true);
                    //printChar(sbQ523, 22, " ", true);
                }
            }//end of if(!lastPG.equals("") && (finishedHT.size() < tm.size()))
            else if(!lastPG.equals("") && (finishedHT.size() == tm.size()))
            {
                //if(currentCursorPos < lastPGPosition)
                printChar(sb, lastPGPosition + I_10 - currentCursorPos, " ", false);
                //printChar(sbQ523, lastPGPosition + 10 - currentCursorPos, " ", false);
                sb.append("|" + NEWLINE);
                //sbQ523.append("|" + NEWLINE);
                if(printRowHeader)
                {
                    while(nLines < strLines.length)
                    {
                        sb.append("|");
                        //sbQ523.append("|");
                        sb.append(strLines[nLines]);
                        //sbQ523.append(strLines[nLines]);
                        printChar(sb, I_20 - strLines[nLines].length(), " ", false);
                        //printChar(sbQ523, 20 - strLines[nLines].length(), " ", false);
                        sb.append("|");
                        //sbQ523.append("|");
                        //if(priceHolderPos.size() > 0)
                        //   printPriceHolderInitialBillingCodeOther(lastPGPosition, pos, priceHolderPos);

                        printChar(sb, lastPGPosition + I_10 - I_23, " ", false);
                        //if(0 == priceHolderPos.size())
                            //printChar(sbQ523, lastPGPosition + 10 - 23, " ", false);

                        sb.append("|" + NEWLINE);
                        //if(0 == priceHolderPos.size())
                            //sbQ523.append("|" + NEWLINE);
                        //priceHolderPos.clear();
                        nLines++;
                    }
                }
                //if(priceHolderPos.size() > 0)
                //{
                    //printChar(sbQ523, 22, " ", true);
                    //printPriceHolderInitialBillingCodeOther(lastPGPosition, pos, priceHolderPos);
                    //   priceHolderPos.clear();
                //}

                if(!currentGeo.equals("WW"))
                {
                    sb.append(".br;:hp2.<---" + lastGeo + ":ehp2." + NEWLINE);
                    //sbQ523.append(".br;:hp2.<---" + lastGeo + ":ehp2." + NEWLINE);
                }
            }//end of else if(!lastPG.equals("") && (finishedHT.size() == tm.size()))

            currentPG = "";
            lastPG = "";
            featureCode = "";
            currentGeo = "";
            lastGeo = "";

            currentCursorPos = I_23;
            posPG = 0;

            aSet = tm.keySet();

            aItr = aSet.iterator();

            while(aItr.hasNext())
            {
                String aStringKey = (String) aItr.next();

                if(!finishedHT.containsKey(aStringKey))
                {
                    currentPG = aStringKey.substring(I_7, I_11);
                    featureCode = aStringKey.substring(I_11, I_17);
                    currentGeo = (String) tm.get(aStringKey);
                    if(!currentPG.equals(lastPG))
                    {
                        if(currentGeo.equals("WW") && false == geoTag)
                        {
                            geoTag = true;
                            if(printRowHeader && !printTechName)
                            {
                                sb.append("|");
                                //sbQ523.append("|");
                                if(strLines.length > 0)
                                {
                                    sb.append(strLines[0]);
                                    //sbQ523.append(strLines[0]);
                                    printChar(sb, I_20 - strLines[0].length(), " ", false);
                                    //printChar(sbQ523, 20 - strLines[0].length(), " ", false);
                                    nLines++;
                                }
                                else
                                {
                                    sb.append(techName);
                                    //sbQ523.append(techName);
                                    printChar(sb, I_20 - techName.length(), " ", false);
                                    //printChar(sbQ523, 20 - techName.length(), " ", false);
                                }
                                sb.append("|");
                                //sbQ523.append("|");
                                printTechName = true;
                            }
                            else
                            {
                                printChar(sb, I_22, " ", true);
                                //printChar(sbQ523, 22, " ", true);
                            }
                            currentCursorPos = I_23;
                        }
                        else if(!currentGeo.equals("WW") && false == geoTag)
                        {
                            sb.append(":p.:hp2." + currentGeo + "--->" + ":ehp2." + NEWLINE);
                            //sbQ523.append(":p.:hp2." + currentGeo + "--->" + ":ehp2." + NEWLINE);
                            geoTag = true;
                            if(printRowHeader && !printTechName)
                            {
                                sb.append("|");
                                //sbQ523.append("|");
                                if(strLines.length > 0)
                                {
                                    sb.append(strLines[0]);
                                    //sbQ523.append(strLines[0]);
                                    printChar(sb, I_20 - strLines[0].length(), " ", false);
                                    //printChar(sbQ523, 20 - strLines[0].length(), " ", false);
                                    nLines++;
                                }
                                else
                                {
                                    sb.append(techName);
                                    //sbQ523.append(techName);
                                    printChar(sb, I_20 - techName.length(), " ", false);
                                    //printChar(sbQ523, 20 - techName.length(), " ", false);
                                }
                                sb.append("|");
                                //sbQ523.append("|");
                                printTechName = true;
                            }
                            else
                            {
                                printChar(sb, I_22, " ", true);
                                //printChar(sbQ523, 22, " ", true);
                            }
                            currentCursorPos = I_23;
                        }
                        aInteger = (Integer) pos.get(currentPG);
                        posPG = aInteger.intValue();
                        printChar(sb, posPG - currentCursorPos, " ", false);
                        //printChar(sbQ523, posPG - currentCursorPos, " ", false);
                        currentCursorPos = posPG;
                        sb.append(featureCode);
                        //sbQ523.append(featureCode);
                        currentCursorPos = currentCursorPos + I_6;
                        finishedHT.put(aStringKey, "Yes");
                        //priceHolderPos.add(currentPG);
                        lastPG = currentPG;
                        lastGeo = currentGeo;
                    }//end of if(!currentPG.equals(lastPG))
                }//end of if(!finishedHT.containsKey(aStringKey))
            }//end of while(aItr.hasNext())
        }//end of for(int i = 0; i < tm.size(); i++)
    }

    /********************************************************************************
    * displayInitialBillingCodeOther_Q523()
    *
    * @param lastPGPosition int
    * @param pos TreeMap
    * @param tm TreeMap
    * @param techName String
    * @param printRowHeader boolean
    */
    private void displayInitialBillingCodeOther_Q523(int lastPGPosition, TreeMap pos, TreeMap tm, String techName, boolean printRowHeader)
    {
        String currentPG = "";
        String lastPG = "";
        String featureCode = "";
        String currentGeo = "";
        String lastGeo = "";
        Integer aInteger;

        int currentCursorPos = 0;
        int posPG = 0;

        Hashtable finishedHT = new Hashtable();

        boolean geoTag = false;
        boolean printTechName = false;

        String [] strLines = extractStringLines(techName, I_20);
        int nLines = 0;

        TreeSet priceHolderPos = new TreeSet();

        for(int i = 0; i <= tm.size(); i++)
        {
            Set aSet;
            Iterator aItr;

            if(!lastPG.equals("") && (finishedHT.size() < tm.size()))
            {
                //printChar(sb, lastPGPosition + 10 - currentCursorPos, " ", false);
                printChar(sbQ523, lastPGPosition + I_10 - currentCursorPos, " ", false);
                //sb.append("|" + NEWLINE);
                sbQ523.append("|" + NEWLINE);
                currentCursorPos = 0;
                if(printRowHeader && (nLines < strLines.length))
                {
                    //sb.append("|");
                    sbQ523.append("|");
                    //sb.append(strLines[nLines]);
                    sbQ523.append(strLines[nLines]);
                    //printChar(sb, 20 - strLines[nLines].length(), " ", false);
                    printChar(sbQ523, I_20 - strLines[nLines].length(), " ", false);
                    //sb.append("|");
                    sbQ523.append("|");
                    nLines++;
                    printPriceHolderInitialBillingCodeOther(lastPGPosition, pos, priceHolderPos);
                    priceHolderPos.clear();
                    if(nLines < strLines.length)
                    {
                        sbQ523.append("|");
                        sbQ523.append(strLines[nLines]);
                        printChar(sbQ523, I_20 - strLines[nLines].length(), " ", false);
                        sbQ523.append("|");
                        nLines++;
                    }
                }
                else
                {
                    //printChar(sb, 22, " ", true);
                    printChar(sbQ523, I_22, " ", true);
                    printPriceHolderInitialBillingCodeOther(lastPGPosition, pos, priceHolderPos);
                    priceHolderPos.clear();
                    printChar(sbQ523, I_22, " ", true);
                }
            }//end of if(!lastPG.equals("") && (finishedHT.size() < tm.size()))
            else if(!lastPG.equals("") && (finishedHT.size() == tm.size()))
            {
                //if(currentCursorPos < lastPGPosition)
                    //printChar(sb, lastPGPosition + 10 - currentCursorPos, " ", false);
                printChar(sbQ523, lastPGPosition + I_10 - currentCursorPos, " ", false);
                //sb.append("|" + NEWLINE);
                sbQ523.append("|" + NEWLINE);
                if(printRowHeader)
                {
                    while(nLines < strLines.length)
                    {
                        //sb.append("|");
                        sbQ523.append("|");
                        //sb.append(strLines[nLines]);
                        sbQ523.append(strLines[nLines]);
                        //printChar(sb, 20 - strLines[nLines].length(), " ", false);
                        printChar(sbQ523, I_20 - strLines[nLines].length(), " ", false);
                        //sb.append("|");
                        sbQ523.append("|");
                        if(priceHolderPos.size() > 0)
                        {
                            printPriceHolderInitialBillingCodeOther(lastPGPosition, pos, priceHolderPos);
                        }

                        //printChar(sb, lastPGPosition + 10 - 23, " ", false);
                        if(0 == priceHolderPos.size())
                        {
                            printChar(sbQ523, lastPGPosition + I_10 - I_23, " ", false);
                        }

                        //sb.append("|" + NEWLINE);
                        if(0 == priceHolderPos.size())
                        {
                            sbQ523.append("|" + NEWLINE);
                        }
                        priceHolderPos.clear();
                        nLines++;
                    }
                }
                if(priceHolderPos.size() > 0)
                {
                    printChar(sbQ523, I_22, " ", true);
                    printPriceHolderInitialBillingCodeOther(lastPGPosition, pos, priceHolderPos);
                    priceHolderPos.clear();
                }

                if(!currentGeo.equals("WW"))
                {
                    //sb.append(".br;:hp2.<---" + lastGeo + ":ehp2." + NEWLINE);
                    sbQ523.append(".br;:hp2.<---" + lastGeo + ":ehp2." + NEWLINE);
                }
            }//end of else if(!lastPG.equals("") && (finishedHT.size() == tm.size()))

            currentPG = "";
            lastPG = "";
            featureCode = "";
            currentGeo = "";
            lastGeo = "";

            currentCursorPos = I_23;
            posPG = 0;

            aSet = tm.keySet();

            aItr = aSet.iterator();

            while(aItr.hasNext())
            {
                String aStringKey = (String) aItr.next();

                if(!finishedHT.containsKey(aStringKey))
                {
                    currentPG = aStringKey.substring(I_7, I_11);
                    featureCode = aStringKey.substring(I_11, I_17);
                    currentGeo = (String) tm.get(aStringKey);
                    if(!currentPG.equals(lastPG))
                    {
                        if(currentGeo.equals("WW") && false == geoTag)
                        {
                            geoTag = true;
                            if(printRowHeader && !printTechName)
                            {
                                //sb.append("|");
                                sbQ523.append("|");
                                if(strLines.length > 0)
                                {
                                    //sb.append(strLines[0]);
                                    sbQ523.append(strLines[0]);
                                    //printChar(sb, 20 - strLines[0].length(), " ", false);
                                    printChar(sbQ523, I_20 - strLines[0].length(), " ", false);
                                    nLines++;
                                }
                                else
                                {
                                    //sb.append(techName);
                                    sbQ523.append(techName);
                                    //printChar(sb, 20 - techName.length(), " ", false);
                                    printChar(sbQ523, I_20 - techName.length(), " ", false);
                                }
                                //sb.append("|");
                                sbQ523.append("|");
                                printTechName = true;
                            }
                            else
                            {
                                //printChar(sb, 22, " ", true);
                                printChar(sbQ523, I_22, " ", true);
                            }
                            currentCursorPos = I_23;
                        }
                        else if(!currentGeo.equals("WW") && false == geoTag)
                        {
                            //sb.append(":p.:hp2." + currentGeo + "--->" + ":ehp2." + NEWLINE);
                            sbQ523.append(":p.:hp2." + currentGeo + "--->" + ":ehp2." + NEWLINE);
                            geoTag = true;
                            if(printRowHeader && !printTechName)
                            {
                                //sb.append("|");
                                sbQ523.append("|");
                                if(strLines.length > 0)
                                {
                                    //sb.append(strLines[0]);
                                    sbQ523.append(strLines[0]);
                                    //printChar(sb, 20 - strLines[0].length(), " ", false);
                                    printChar(sbQ523, I_20 - strLines[0].length(), " ", false);
                                    nLines++;
                                }
                                else
                                {
                                    //sb.append(techName);
                                    sbQ523.append(techName);
                                    //printChar(sb, 20 - techName.length(), " ", false);
                                    printChar(sbQ523, I_20 - techName.length(), " ", false);
                                }
                                //sb.append("|");
                                sbQ523.append("|");
                                printTechName = true;
                            }
                            else
                            {
                                //printChar(sb, 22, " ", true);
                                printChar(sbQ523, I_22, " ", true);
                            }
                            currentCursorPos = I_23;
                        }
                        aInteger = (Integer) pos.get(currentPG);
                        posPG = aInteger.intValue();
                        //printChar(sb, posPG - currentCursorPos, " ", false);
                        printChar(sbQ523, posPG - currentCursorPos, " ", false);
                        currentCursorPos = posPG;
                        //sb.append(featureCode);
                        sbQ523.append(featureCode);
                        currentCursorPos = currentCursorPos + I_6;
                        finishedHT.put(aStringKey, "Yes");
                        priceHolderPos.add(currentPG);
                        lastPG = currentPG;
                        lastGeo = currentGeo;
                    }//end of if(!currentPG.equals(lastPG))
                }//end of if(!finishedHT.containsKey(aStringKey))
            }//end of while(aItr.hasNext())
        }//end of for(int i = 0; i < tm.size(); i++)
    }

    /********************************************************************************
    * displayNewUpgradeBillingCode()
    *
    */
    private void displayNewUpgradeBillingCode()
    {
        displayNewUpgradeBillingCodePBOTC();
        displayNewUpgradeBillingCodeOther();
    }

    /********************************************************************************
    * displayNewUpgradeBillingCodePBOTC()
    *
    */
    private void displayNewUpgradeBillingCodePBOTC()
    {
        TreeMap base = new TreeMap();
        String featureCode;
        String performanceGroup;
        String [] pg;

        TreeMap [] optionalFeatureBase;
        TreeSet ofb;
        Hashtable ofbHT;
        String [] arrayTechName;
        Iterator tsIterator;
        int index;
        String baseModelTechName;

        Hashtable ht = new Hashtable();
        //319 = ValueMetric
        //357 = PBOTC
        //010 = N/A
        ht.put("SWFCCAT", "319");
        ht.put("SWFCSUBCAT", "357");
        //ht.put("SWFCGRP", "010");

        //Get all SWPRODSTRUCT associate with base MODEL and SWPRODSTRUCT(Upgrade)
        for(int i = 0; i < Q352A2AppBaseFrom.size(); i++)
        {
            SWEntityItem_OIM swei = (SWEntityItem_OIM) Q352A2AppBaseFrom.get(i);

            if(swei.containModelBase(baseModel))
            {
                EntityItem fromSWProdStructItem = swei.getFromSWProdStruct();
                Vector fromMTM = getFromMTM(fromSWProdStructItem);

                for(int j = 0; j < Q352A2AppBaseTo.size(); j++)
                {
                    SWEntityItem_OIM swEntityItem = (SWEntityItem_OIM) Q352A2AppBaseTo.get(j);

                    if(swEntityItem.containModelBase(baseModel) && swEntityItem.containFromSWProdStruct(fromSWProdStructItem))
                    {
                        EntityItem swProdStructItem = swEntityItem.getSWProdStruct();

                        Vector upLinkEntityItemVector = getUpLinkEntityItems(swProdStructItem, "SWFEATURE");
                        for(int n = 0; n < upLinkEntityItemVector.size(); n++)
                        {
                            EANEntity entityUpLink  = (EANEntity) upLinkEntityItemVector.get(n);
                            if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                            {
                                EntityItem swFeatureItem = (EntityItem) entityUpLink;

                                featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                                featureCode = featureCode + "      ";
                                featureCode = featureCode.substring(0, 6);
                                performanceGroup = PokUtils.getAttributeValue(swFeatureItem, "PERFORMANCEGROUP", "|", "---");
                                pg = PokUtils.convertToArray(performanceGroup);
                                for(int k = 0; k < pg.length; k++)
                                {
                                    String perfGrp = pg[k] + "   ";
                                    perfGrp = perfGrp.substring(0, 3);

                                    for(int m = 0; m < fromMTM.size(); m++)
                                    {
                                        String aStr = (String) fromMTM.get(m);
                                        String fromMachType = parseString(aStr, 1);
                                        String fromModel = parseString(aStr, 2);

                                        String key = machType + model + fromMachType + fromModel + perfGrp + featureCode + swProdStructItem.getKey();
                                        String geo = getGeo(swProdStructItem.getKey());
                                        log("key = " + key);

                                        base.put(key, geo);
                                    }//end of for (int m = 0; m < fromMTM.size(); m++)
                                }//end of for(int k = 0; k < pg.length; k++)
                            }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                        }//end of for(int n = 0; n < upLinkEntityItemVector.size(); n++)
                    }//if(swEntityItem.containModelBase(baseModel) && swEntityItem.containFromSWProdStruct(fromSWProdStructItem))
                }//end of for(int j = 0; j < Q352A2AppBaseTo.size(); j++)
            }//end of if(swei.containModelBase(baseModel))
        }//end of for(int i = 0; i < Q352A2AppBaseFrom.size(); i++)

        //Get all SWPRODSTRUCT associate with each optional feature which associates with base MODEL and SWPRODSTRUCT(Upgrade).
        ofb = new TreeSet();
        ofbHT = new Hashtable();
        for(int i = 0; i < Q352A2AppOptFeaBase1To.size(); i++)
        {
            SWEntityItem_OIM swei = (SWEntityItem_OIM) Q352A2AppOptFeaBase1To.get(i);

            if(swei.containModelBase(baseModel))
            {
                EntityItem OptFeaBaseModel = swei.getOptFeaBaseModel();
                String techName = PokUtils.getAttributeValue(OptFeaBaseModel, "MKTGNAME", "|", "", false);
                ofb.add(OptFeaBaseModel.getKey());
                if(techName.length() > I_128)
                {
                    techName = techName.substring(0, I_128);
                }
                ofbHT.put(OptFeaBaseModel.getKey(), techName);
            }
        }//end of for(int i = 0; i < Q352A2AppOptFeaBase1To.size(); i++)

        optionalFeatureBase = new TreeMap[ofb.size()];
        for(int i = 0; i < optionalFeatureBase.length; i++)
        {
            optionalFeatureBase[i] = new TreeMap();
        }
        arrayTechName = new String[ofb.size()];

        tsIterator = ofb.iterator();
        index = 0;
        while(tsIterator.hasNext())
        {
            String optFeaBaseModel = (String) tsIterator.next();
            for(int i = 0; i < Q352A2AppOptFeaBaseFrom.size(); i++)
            {
                SWEntityItem_OIM swei = (SWEntityItem_OIM) Q352A2AppOptFeaBaseFrom.get(i);

                if(swei.containModelBase(optFeaBaseModel))
                {
                    EntityItem fromSWProdStructItem = swei.getFromSWProdStruct();
                    Vector fromMTM = getFromMTM(fromSWProdStructItem);

                    for(int j = 0; j < Q352A2AppOptFeaBase2To.size(); j++)
                    {
                        SWEntityItem_OIM swEntityItem = (SWEntityItem_OIM) Q352A2AppOptFeaBase2To.get(j);

                        if(swEntityItem.containModelOptFeaBase(optFeaBaseModel) && swEntityItem.containFromSWProdStruct(fromSWProdStructItem))
                        {
                            EntityItem swProdStructItem = swEntityItem.getSWProdStruct();

                            Vector upLinkEntityItemVector = getUpLinkEntityItems(swProdStructItem, "SWFEATURE");
                            for(int n = 0; n < upLinkEntityItemVector.size(); n++)
                            {
                                EANEntity entityUpLink  = (EANEntity) upLinkEntityItemVector.get(n);
                                if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                                {
                                    EntityItem swFeatureItem = (EntityItem) entityUpLink;

                                    featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                                    featureCode = featureCode + "      ";
                                    featureCode = featureCode.substring(0, 6);
                                    performanceGroup = PokUtils.getAttributeValue(swFeatureItem, "PERFORMANCEGROUP", "|", "---");
                                    pg = PokUtils.convertToArray(performanceGroup);
                                    for(int k = 0; k < pg.length; k++)
                                    {
                                        String perfGrp = pg[k] + "   ";
                                        perfGrp = perfGrp.substring(0, 3);

                                        for(int m = 0; m < fromMTM.size(); m++)
                                        {
                                            String aStr = (String) fromMTM.get(m);
                                            String fromMachType = parseString(aStr, 1);
                                            String fromModel = parseString(aStr, 2);

                                            String key = machType + model + fromMachType + fromModel + perfGrp + featureCode + swProdStructItem. getKey();
                                            String geo = getGeo(swProdStructItem.getKey());

                                            String techName = (String) ofbHT.get(optFeaBaseModel);
                                            optionalFeatureBase[index].put(key, geo);

                                            arrayTechName[index] = techName;
                                        }//end of for (int m = 0; m < fromMTM.size(); m++)
                                    }//end of for(int k = 0; k < pg.length; k++)
                                }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                            }//end of for(int n = 0; n < upLinkEntityItemVector.size(); n++)
                        }//end of if(swEntityItem.containModelOptFeaBase(optFeaBaseModel) && swEntityItem.containFromSWProdStruct(fromSWProdStructItem))
                    }//end of for(int i = 0; i < Q352A2AppOptFeaBase2To.size(); i++)
                }//end of if(swei.containModelBase(optFeaBaseModel))
            }//end of for(int i = 0; i < Q352A2AppOptFeaBaseFrom.size(); i++)

            index++;
        }//end of while(tsIterator.hasNext())

        baseModelTechName = PokUtils.getAttributeValue(baseModel, "MKTGNAME", "|", "", false);
        if(baseModelTechName.length() > I_128)
        {
            baseModelTechName = baseModelTechName.substring(0, I_128);
        }

        //sb.append(":xmp." + NEWLINE);
        //sbQ523.append(":xmp." + NEWLINE);
        displayNewUpgradeBillingCodePBOTC(base, baseModelTechName);
        for(int i = 0; i < optionalFeatureBase.length; i++)
        {
            displayNewUpgradeBillingCodePBOTC(optionalFeatureBase[i], arrayTechName[i]);
        }
        //sb.append(":exmp." + NEWLINE);
        //sbQ523.append(":exmp." + NEWLINE);
    }

    /********************************************************************************
    * displayNewUpgradeBillingCodePBOTC()
    *
    * @param tm TreeMap
    * @param techName String
    */
    private void displayNewUpgradeBillingCodePBOTC(TreeMap tm, String techName)
    {
        //Group entries based on machine type and model of "upgrade from" OOF
        int index;
        Iterator tsItr;
        TreeSet ts = new TreeSet();
        TreeMap [] arrayTM;
        Set tmSet = tm.keySet();
        Set aSet;
        Iterator itr;
        int count;
        int [] cursorPos;
        Object [] arrayObj;
        TreeMap pos;

        Iterator tmItr = tmSet.iterator();
        while(tmItr.hasNext())
        {
            String aKey = (String) tmItr.next();
            aKey = aKey.substring(I_7, I_14);
            ts.add(aKey);
        }

        arrayTM = new TreeMap[ts.size()];

        index = 0;
        tsItr = ts.iterator();
        while(tsItr.hasNext())
        {
            String upgradeFromMTM = (String) tsItr.next();
            arrayTM[index] = new TreeMap();

            tmItr = tmSet.iterator();
            while(tmItr.hasNext())
            {
                String aKey = (String) tmItr.next();
                String upgrdFrmMTM = aKey.substring(I_7, I_14);
                if(upgrdFrmMTM.equals(upgradeFromMTM))
                {
                    arrayTM[index].put(aKey, tm.get(aKey));
                }
            }//end of while(tmItr.hasNext())

            index++;
        }//end of while(tsItr.hasNext())

        //Count number of distinct performance group
        //ts = new TreeSet();
        ts.clear();

        aSet = tm.keySet();
        itr = aSet.iterator();
        while(itr.hasNext())
        {
            String str = (String) itr.next();
            String pg = str.substring(I_14, I_17);
            ts.add(pg);
        }

        count = 0;

        cursorPos = new int[6];
        cursorPos[0] = I_23;
        cursorPos[1] = I_31;
        cursorPos[2] = I_39;
        cursorPos[3] = I_47;
        cursorPos[4] = I_55;
        cursorPos[5] = I_63;

        arrayObj = ts.toArray();

        pos = new TreeMap();

        if(arrayObj.length > 0)
        {
            if(showUpgradeOrderHeader)
            {
                sb.append(":hp1.Upgrade Order:ehp1." + NEWLINE);
                sbQ523.append(":hp1.Upgrade Order:ehp1." + NEWLINE);
                showUpgradeOrderHeader = false;
            }
            sb.append(":xmp." + NEWLINE);
            sbQ523.append(":xmp." + NEWLINE);
        }
        for(int i = 0; i < arrayObj.length; i++)
        {
            if(count < 6)
            {
                pos.put((String) arrayObj[i], new Integer(cursorPos[i % 6]));
                count++;
            }
            if((count == 6) || (i == arrayObj.length - 1))
            {
                count = 0;
                displayNewUpgradeBillingCodePBOTC(pos, arrayTM, techName);
                pos.clear();
            }
        }
        if(arrayObj.length > 0)
        {
            sb.append(":exmp." + NEWLINE);
            sbQ523.append(":exmp." + NEWLINE);
        }
    }

    /********************************************************************************
    * displayNewUpgradeBillingCodePBOTC()
    *
    * @param pos TreeMap
    * @param arrayTM TreeMap []
    * @param techName String
    */
    private void displayNewUpgradeBillingCodePBOTC(TreeMap pos, TreeMap [] arrayTM, String techName)
    {
        int numPerformanceGroup = pos.size();
        int lastPGPosition;
        int lastCharPosition;
        String [] strLines;
        Set set;
        Iterator itr;
        int count;

        Integer aInteger = (Integer) pos.get(pos.lastKey());
        lastPGPosition = aInteger.intValue();

        strLines = extractStringLines(techName, I_40);

        if(lastPGPosition >= (I_22 + techName.length() + I_2))
        {
            lastCharPosition = lastPGPosition + I_4;
            printChar(sb, I_22 + I_8*numPerformanceGroup - 1, "-", true);
            printChar(sbQ523, I_22 + I_8*numPerformanceGroup - 1, "-", true);
            sb.append(NEWLINE);
            sbQ523.append(NEWLINE);
        }
        else
        {
            if(techName.length() < I_40)
            {
                lastCharPosition = I_22 + techName.length() + 4 + 1;
            }
            else
            {
                lastCharPosition = I_67;
            }

            printChar(sb, lastCharPosition + 2, "-", true);
            printChar(sbQ523, lastCharPosition + 2, "-", true);
            sb.append(NEWLINE);
            sbQ523.append(NEWLINE);
        }

        for(int i = 0; i < strLines.length; i++)
        {
            printChar(sb, I_22, " ", true);
            printChar(sbQ523, I_22, " ", true);
            if(0 == i)
            {
                sb.append("To: " + strLines[i]);
                sbQ523.append("To: " + strLines[i]);
            }
            else
            {
                sb.append("    " + strLines[i]);
                sbQ523.append("    " + strLines[i]);
            }
            printChar(sb, lastCharPosition - I_22 - strLines[i].length() - 3, " ", false);
            printChar(sbQ523, lastCharPosition - I_22 - strLines[i].length() - 3, " ", false);
            sb.append("|");
            sbQ523.append("|");
            sb.append(NEWLINE);
            sbQ523.append(NEWLINE);
        }

        sb.append("|");
        sbQ523.append("|");
        sb.append("Upgrade From:");
        sbQ523.append("Upgrade From:");
        printChar(sb, 7, " ", false);
        printChar(sbQ523, 7, " ", false);
        sb.append("|");
        sbQ523.append("|");

        set = pos.keySet();

        itr = set.iterator();

        count = 0;
        while(itr.hasNext())
        {
            String aStringKey = (String) itr.next();
            count++;
            if(count < set.size())
            {
                sb.append(aStringKey + "     ");
                sbQ523.append(aStringKey + "     ");
            }
            else if(lastPGPosition >= (I_22 + techName.length() + 4))
            {
                sb.append(aStringKey + "   ");
                sbQ523.append(aStringKey + "   ");
            }
            else
            {
                sb.append(aStringKey + "   ");
                sbQ523.append(aStringKey + "   ");
                printChar(sb, lastCharPosition - (I_22 + I_8*numPerformanceGroup - 3), " ", false);
                printChar(sbQ523, lastCharPosition - (I_22 + I_8*numPerformanceGroup - 3), " ", false);
            }
        }

        sb.append("|" + NEWLINE);
        sbQ523.append("|" + NEWLINE);
        printChar(sb, lastCharPosition + 2, "-", true);
        printChar(sbQ523, lastCharPosition + 2, "-", true);
        sb.append(NEWLINE);
        sbQ523.append(NEWLINE);

        for(int i = 0; i < arrayTM.length; i++)
        {
            displayNewUpgradeBillingCodePBOTC(pos, arrayTM[i], numPerformanceGroup, lastPGPosition, lastCharPosition);
        }
    }

    /********************************************************************************
    * displayNewUpgradeBillingCodePBOTC()
    *
    * @param pos TreeMap
    * @param tm TreeMap
    * @param numPerformanceGroup int
    * @param lastPGPosition int
    * @param lastCharPosition int
    */
    private void displayNewUpgradeBillingCodePBOTC(TreeMap pos, TreeMap tm, int numPerformanceGroup, int lastPGPosition, int lastCharPosition)
    {
        //Group entries based on Geo
        TreeMap [] arrayTM;
        int index;
        boolean printHeaderRow;
        Iterator tsItr;
        TreeSet ts = new TreeSet();
        Set tmSet = tm.keySet();
        Iterator tmItr = tmSet.iterator();
        while(tmItr.hasNext())
        {
            ts.add(tm.get(tmItr.next()));
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
                String aKey = (String) tmItr.next();
                String pg = aKey.substring(I_14, I_17);
                if(geo.equals((String) tm.get(aKey)) && pos.containsKey(pg))
                {
                    arrayTM[index].put(aKey, geo);
                }
            }//end of while(tmItr.hasNext())
            index++;
        }//end of while(tsItr.hasNext())

        printHeaderRow = true;
        for(int i = 0; i < arrayTM.length; i++)
        {
            if(printHeaderRow && (arrayTM[i].size() > 0))
            {
                displayNewUpgradeBillingCodePBOTC(pos, arrayTM[i], lastPGPosition, lastCharPosition, true);
                displayNewUpgradeBillingCodePBOTC_Q523(pos, arrayTM[i], lastPGPosition, lastCharPosition, true);
                printHeaderRow = false;
            }
            else
            {
                displayNewUpgradeBillingCodePBOTC(pos, arrayTM[i], lastPGPosition, lastCharPosition, true);
                displayNewUpgradeBillingCodePBOTC_Q523(pos, arrayTM[i], lastPGPosition, lastCharPosition,true);
            }
        }

        for(int i = 0; i < arrayTM.length; i++)
        {
            if(arrayTM[i].size() > 0)
            {
                if(lastCharPosition > (lastPGPosition + 4))
                {
                    printChar(sb, lastCharPosition + 2, "-", true);
                    printChar(sbQ523, lastCharPosition + 2, "-", true);
                }
                else
                {
                    printChar(sb, I_22 + I_8*numPerformanceGroup - 1, "-", true);
                    printChar(sbQ523, I_22 + I_8*numPerformanceGroup - 1, "-", true);
                }
                sb.append(NEWLINE);
                sbQ523.append(NEWLINE);
                break;
            }
        }
    }

    /********************************************************************************
    *  displayNewUpgradeBillingCodePBOTC()
    *
    * @param pos TreeMap
    * @param tm TreeMap
    * @param lastPGPosition int
    * @param lastCharPosition int
    * @param printRowHeader boolean
    */
    private void displayNewUpgradeBillingCodePBOTC(TreeMap pos, TreeMap tm, int lastPGPosition, int lastCharPosition, boolean printRowHeader)
    {
        String currentPG = "";
        String lastPG = "";
        String featureCode = "";
        String currentGeo = "";
        String lastGeo = "";
        Integer aInteger;

        int currentCursorPos = 0;
        int posPG = 0;

        Hashtable finishedHT = new Hashtable();

        boolean geoTag = false;
        boolean printTechName = false;

        //TreeSet priceHolderPos = new TreeSet();

        for(int i = 0; i <= tm.size(); i++)
        {
            Set aSet;
            Iterator aItr;

            if(!lastPG.equals("") && (finishedHT.size() < tm.size()))
            {
                //printChar(sb, lastPGPosition + 7 - currentCursorPos, " ", false);

                if(lastCharPosition > (lastPGPosition + 6))
                {
                    printChar(sb, lastCharPosition + 2 - currentCursorPos, " ", false);
                    //printChar(sbQ523, lastCharPosition + 2 - currentCursorPos, " ", false);
                }
                else
                {
                    printChar(sb, lastPGPosition + 6 - currentCursorPos, " ", false);
                    //printChar(sbQ523, lastPGPosition + 6 - currentCursorPos, " ", false);
                }

                sb.append("|" + NEWLINE);
                //sbQ523.append("|" + NEWLINE);
                currentCursorPos = 0;
                printChar(sb, I_22, " ", true);
                //printChar(sbQ523, 22, " ", true);
            }//end of if(!lastPG.equals("") && (finishedHT.size() < tm.size()))
            else if(!lastPG.equals("") && (finishedHT.size() == tm.size()))
            {
                //printChar(sb, lastPGPosition + 7 - currentCursorPos, " ", false);
                //printChar(sb, lastPGPosition + 3 - currentCursorPos, " ", false);

                if(lastCharPosition > (lastPGPosition + 6))
                {
                    printChar(sb, lastCharPosition + 2 - currentCursorPos, " ", false);
                    //printChar(sbQ523, lastCharPosition + 2 - currentCursorPos, " ", false);
                }
                else
                {
                    printChar(sb, lastPGPosition + 6 - currentCursorPos, " ", false);
                    //printChar(sbQ523, lastPGPosition + 6 - currentCursorPos, " ", false);
                }

                sb.append("|" + NEWLINE);
                //sbQ523.append("|" + NEWLINE);

                //if(priceHolderPos.size() > 0)
                //{
                    //printChar(sbQ523, 22, " ", true);
                    //   printPriceHolderNewUpgradeBillingCodePBOTC(lastPGPosition, lastCharPosition, pos, priceHolderPos);
                    //   priceHolderPos.clear();
                //}

                if(!currentGeo.equals("WW"))
                {
                    sb.append(".br;:hp2.<---" + lastGeo + ":ehp2." + NEWLINE);
                    //sbQ523.append(".br;:hp2.<---" + lastGeo + ":ehp2." + NEWLINE);
                }
            }//end of else if(!lastPG.equals("") && (finishedHT.size() == tm.size()))

            currentPG = "";
            lastPG = "";
            featureCode = "";
            currentGeo = "";
            lastGeo = "";

            currentCursorPos = I_23;
            posPG = 0;

            aSet = tm.keySet();

            aItr = aSet.iterator();

            while(aItr.hasNext())
            {
                String aStringKey = (String) aItr.next();

                String upgradeFromMTM = aStringKey.substring(I_7, I_11);
                upgradeFromMTM = upgradeFromMTM + "-" + aStringKey.substring(I_11, I_14);

                if(!finishedHT.containsKey(aStringKey))
                {
                    currentPG = aStringKey.substring(I_14, I_17);
                    featureCode = aStringKey.substring(I_17, I_23);
                    currentGeo = (String) tm.get(aStringKey);
                    if(!currentPG.equals(lastPG))
                    {
                        if(currentGeo.equals("WW") && false == geoTag)
                        {
                            geoTag = true;
                            if(printRowHeader && !printTechName)
                            {
                                sb.append("|");
                                //sbQ523.append("|");
                                sb.append(upgradeFromMTM);
                                //sbQ523.append(upgradeFromMTM);
                                printChar(sb, I_20 - upgradeFromMTM.length(), " ", false);
                                //printChar(sbQ523, 20 - upgradeFromMTM.length(), " ", false);
                                sb.append("|");
                                //sbQ523.append("|");
                                printTechName = true;
                            }
                            else
                            {
                                printChar(sb, I_22, " ", true);
                                //printChar(sbQ523, 22, " ", true);
                            }
                            currentCursorPos = I_23;
                        }
                        else if(!currentGeo.equals("WW") && false == geoTag)
                        {
                            sb.append(":p.:hp2." + currentGeo + "--->" + ":ehp2." + NEWLINE);
                            //sbQ523.append(":p.:hp2." + currentGeo + "--->" + ":ehp2." + NEWLINE);
                            geoTag = true;
                            if(printRowHeader && !printTechName)
                            {
                                sb.append("|");
                                //sbQ523.append("|");
                                sb.append(upgradeFromMTM);
                                //sbQ523.append(upgradeFromMTM);
                                printChar(sb, I_20 - upgradeFromMTM.length(), " ", false);
                                //printChar(sbQ523, 20 - upgradeFromMTM.length(), " ", false);
                                sb.append("|");
                                //sbQ523.append("|");
                                printTechName = true;
                            }
                            else
                            {
                                printChar(sb, I_22, " ", true);
                                //printChar(sbQ523, 22, " ", true);
                            }
                            currentCursorPos = I_23;
                        }
                        aInteger = (Integer) pos.get(currentPG);
                        posPG = aInteger.intValue();
                        printChar(sb, posPG - currentCursorPos, " ", false);
                        //printChar(sbQ523, posPG - currentCursorPos, " ", false);
                        currentCursorPos = posPG;
                        sb.append(featureCode);
                        //sbQ523.append(featureCode);
                        currentCursorPos = currentCursorPos + 6;
                        finishedHT.put(aStringKey, "Yes");
                        //priceHolderPos.add(currentPG);
                        lastPG = currentPG;
                        lastGeo = currentGeo;
                    }//end of if(!currentPG.equals(lastPG))
                }//end of if(!finishedHT.containsKey(aStringKey))
            }//end of while(aItr.hasNext())
        }//end of for(int i = 0; i < tm.size(); i++)
    }

    /********************************************************************************
    * displayNewUpgradeBillingCodePBOTC_Q523()
    *
    * @param pos TreeMap
    * @param tm TreeMap
    * @param lastPGPosition int
    * @param lastCharPosition int
    * @param printRowHeader boolean
    */
    private void displayNewUpgradeBillingCodePBOTC_Q523(TreeMap pos, TreeMap tm, int lastPGPosition, int lastCharPosition, boolean printRowHeader)
    {
        String currentPG = "";
        String lastPG = "";
        String featureCode = "";
        String currentGeo = "";
        String lastGeo = "";
        Integer aInteger;

        int currentCursorPos = 0;
        int posPG = 0;

        Hashtable finishedHT = new Hashtable();

        boolean geoTag = false;
        boolean printTechName = false;

        TreeSet priceHolderPos = new TreeSet();

        for(int i = 0; i <= tm.size(); i++)
        {
            Set aSet;
            Iterator aItr;

            if(!lastPG.equals("") && (finishedHT.size() < tm.size()))
            {
                //printChar(sb, lastPGPosition + 7 - currentCursorPos, " ", false);

                if(lastCharPosition > (lastPGPosition + 6))
                {
                    //printChar(sb, lastCharPosition + 2 - currentCursorPos, " ", false);
                    printChar(sbQ523, lastCharPosition + 2 - currentCursorPos, " ", false);
                }
                else
                {
                    //printChar(sb, lastPGPosition + 6 - currentCursorPos, " ", false);
                    printChar(sbQ523, lastPGPosition + 6 - currentCursorPos, " ", false);
                }

                //sb.append("|" + NEWLINE);
                sbQ523.append("|" + NEWLINE);
                currentCursorPos = 0;
                printChar(sbQ523, I_22, " ", true);
                printPriceHolderNewUpgradeBillingCodePBOTC(lastPGPosition, lastCharPosition, pos, priceHolderPos);
                priceHolderPos.clear();
                printChar(sbQ523, I_22, " ", true);
                //printChar(sb, 22, " ", true);
            }//end of if(!lastPG.equals("") && (finishedHT.size() < tm.size()))
            else if(!lastPG.equals("") && (finishedHT.size() == tm.size()))
            {
                //printChar(sb, lastPGPosition + 7 - currentCursorPos, " ", false);
                //printChar(sb, lastPGPosition + 3 - currentCursorPos, " ", false);

                if(lastCharPosition > (lastPGPosition + 6))
                {
                    //printChar(sb, lastCharPosition + 2 - currentCursorPos, " ", false);
                    printChar(sbQ523, lastCharPosition + 2 - currentCursorPos, " ", false);
                }
                else
                {
                    //printChar(sb, lastPGPosition + 6 - currentCursorPos, " ", false);
                    printChar(sbQ523, lastPGPosition + 6 - currentCursorPos, " ", false);
                }

                //sb.append("|" + NEWLINE);
                sbQ523.append("|" + NEWLINE);

                if(priceHolderPos.size() > 0)
                {
                    printChar(sbQ523, I_22, " ", true);
                    printPriceHolderNewUpgradeBillingCodePBOTC(lastPGPosition, lastCharPosition, pos, priceHolderPos);
                    priceHolderPos.clear();
                }

                if(!currentGeo.equals("WW"))
                {
                    //sb.append(".br;:hp2.<---" + lastGeo + ":ehp2." + NEWLINE);
                    sbQ523.append(".br;:hp2.<---" + lastGeo + ":ehp2." + NEWLINE);
                }
            }//end of else if(!lastPG.equals("") && (finishedHT.size() == tm.size()))

            currentPG = "";
            lastPG = "";
            featureCode = "";
            currentGeo = "";
            lastGeo = "";

            currentCursorPos = I_23;
            posPG = 0;

            aSet = tm.keySet();

            aItr = aSet.iterator();

            while(aItr.hasNext())
            {
                String aStringKey = (String) aItr.next();

                String upgradeFromMTM = aStringKey.substring(I_7, I_11);
                upgradeFromMTM = upgradeFromMTM + "-" + aStringKey.substring(I_11, I_14);

                if(!finishedHT.containsKey(aStringKey))
                {
                    currentPG = aStringKey.substring(I_14, I_17);
                    featureCode = aStringKey.substring(I_17, I_23);
                    currentGeo = (String) tm.get(aStringKey);
                    if(!currentPG.equals(lastPG))
                    {
                        if(currentGeo.equals("WW") && false == geoTag)
                        {
                            geoTag = true;
                            if(printRowHeader && !printTechName)
                            {
                                //sb.append("|");
                                sbQ523.append("|");
                                //sb.append(upgradeFromMTM);
                                sbQ523.append(upgradeFromMTM);
                                //printChar(sb, 20 - upgradeFromMTM.length(), " ", false);
                                printChar(sbQ523, I_20 - upgradeFromMTM.length(), " ", false);
                                //sb.append("|");
                                sbQ523.append("|");
                                printTechName = true;
                            }
                            else
                            {
                                //printChar(sb, 22, " ", true);
                                printChar(sbQ523, I_22, " ", true);
                            }
                            currentCursorPos = I_23;
                        }
                        else if(!currentGeo.equals("WW") && false == geoTag)
                        {
                            //sb.append(":p.:hp2." + currentGeo + "--->" + ":ehp2." + NEWLINE);
                            sbQ523.append(":p.:hp2." + currentGeo + "--->" + ":ehp2." + NEWLINE);
                            geoTag = true;
                            if(printRowHeader && !printTechName)
                            {
                                //sb.append("|");
                                sbQ523.append("|");
                                //sb.append(upgradeFromMTM);
                                sbQ523.append(upgradeFromMTM);
                                //printChar(sb, 20 - upgradeFromMTM.length(), " ", false);
                                printChar(sbQ523, I_20 - upgradeFromMTM.length(), " ", false);
                                //sb.append("|");
                                sbQ523.append("|");
                                printTechName = true;
                            }
                            else
                            {
                                //printChar(sb, 22, " ", true);
                                printChar(sbQ523, I_22, " ", true);
                            }
                            currentCursorPos = I_23;
                        }
                        aInteger = (Integer) pos.get(currentPG);
                        posPG = aInteger.intValue();
                        //printChar(sb, posPG - currentCursorPos, " ", false);
                        printChar(sbQ523, posPG - currentCursorPos, " ", false);
                        currentCursorPos = posPG;
                        //sb.append(featureCode);
                        sbQ523.append(featureCode);
                        currentCursorPos = currentCursorPos + 6;
                        finishedHT.put(aStringKey, "Yes");
                        priceHolderPos.add(currentPG);
                        lastPG = currentPG;
                        lastGeo = currentGeo;
                    }//end of if(!currentPG.equals(lastPG))
                }//end of if(!finishedHT.containsKey(aStringKey))
            }//end of while(aItr.hasNext())
        }//end of for(int i = 0; i < tm.size(); i++)
    }

    /********************************************************************************
    * displayNewUpgradeBillingCodeOther()
    *
    */
    private void displayNewUpgradeBillingCodeOther()
    {
        displayNewUpgradeBillingCodeFlatFee();
        displayNewUpgradeBillingCodeUseDiscBlock();
    }

    /********************************************************************************
    * displayNewUpgradeBillingCodeFlatFee()
    *
    */
    private void displayNewUpgradeBillingCodeFlatFee()
    {
        TreeMap base = new TreeMap();
        String featureCode;

        TreeMap [] optionalFeatureBase;
        TreeSet ofb;
        Hashtable ofbHT;
        String [] arrayTechName;
        Iterator tsIterator;
        int index;
        String baseModelTechName;

        Hashtable ht = new Hashtable();

        //Get all SWPRODSTRUCT associate with base MODEL and SWPRODSTRUCT(Upgrade)
        for(int i = 0; i < Q352A2AppBaseFrom.size(); i++)
        {
            SWEntityItem_OIM swei = (SWEntityItem_OIM) Q352A2AppBaseFrom.get(i);

            if(swei.containModelBase(baseModel))
            {
                EntityItem fromSWProdStructItem = swei.getFromSWProdStruct();
                Vector fromMTM = getFromMTM(fromSWProdStructItem);

                for(int j = 0; j < Q352A2AppBaseTo.size(); j++)
                {
                    SWEntityItem_OIM swEntityItem = (SWEntityItem_OIM) Q352A2AppBaseTo.get(j);

                    if(swEntityItem.containModelBase(baseModel) && swEntityItem.containFromSWProdStruct(fromSWProdStructItem))
                    {
                        EntityItem swProdStructItem = swEntityItem.getSWProdStruct();

                        Vector upLinkEntityItemVector = getUpLinkEntityItems(swProdStructItem, "SWFEATURE");
                        for(int n = 0; n < upLinkEntityItemVector.size(); n++)
                        {
                            EANEntity entityUpLink  = (EANEntity) upLinkEntityItemVector.get(n);

                            ht.clear();
                            //319 = ValueMetric
                            //355 = FlatFee
                            //010 = N/A
                            //2823 = BASIC
                            //380 = OTC
                            ht.put("SWFCCAT", "319");
                            ht.put("SWFCSUBCAT", "355");
                            //ht.put("SWFCGRP", "010");
                            ht.put("LICENSETYPE", "2823");
                            ht.put("CHARGEOPTION", "380");

                            if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                            {
                                EntityItem swFeatureItem = (EntityItem) entityUpLink;

                                featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                                featureCode = featureCode + "      ";
                                featureCode = featureCode.substring(0, 6);

                                for(int m = 0; m < fromMTM.size(); m++)
                                {
                                    String aStr = (String) fromMTM.get(m);
                                    String fromMachType = parseString(aStr, 1);
                                    String fromModel = parseString(aStr, 2);

                                    String key = machType + model + fromMachType + fromModel + "flf1" + featureCode + swProdStructItem.getKey();
                                    String geo = getGeo(swProdStructItem.getKey());

                                    base.put(key, geo);
                                }
                            }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))

                            ht.clear();
                            //319 = ValueMetric
                            //355 = FlatFee
                            //010 = N/A
                            //2823 = BASIC
                            //378 = MLC
                            ht.put("SWFCCAT", "319");
                            ht.put("SWFCSUBCAT", "355");
                            //ht.put("SWFCGRP", "010");
                            ht.put("LICENSETYPE", "2823");
                            ht.put("CHARGEOPTION", "378");

                            if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                            {
                                EntityItem swFeatureItem = (EntityItem) entityUpLink;

                                featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                                featureCode = featureCode + "      ";
                                featureCode = featureCode.substring(0, 6);

                                for(int m = 0; m < fromMTM.size(); m++)
                                {
                                    String aStr = (String) fromMTM.get(m);
                                    String fromMachType = parseString(aStr, 1);
                                    String fromModel = parseString(aStr, 2);

                                    String key = machType + model + fromMachType + fromModel + "flf2" + featureCode + swProdStructItem.getKey();
                                    String geo = getGeo(swProdStructItem.getKey());

                                    base.put(key, geo);
                                }
                            }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))

                            ht.clear();
                            //319 = ValueMetric
                            //355 = FlatFee
                            //010 = N/A
                            //2823 = BASIC
                            //379 = PLC
                            ht.put("SWFCCAT", "319");
                            ht.put("SWFCSUBCAT", "355");
                            //ht.put("SWFCGRP", "010");
                            ht.put("LICENSETYPE", "2823");
                            ht.put("CHARGEOPTION", "379");

                            if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                            {
                                EntityItem swFeatureItem = (EntityItem) entityUpLink;

                                featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                                featureCode = featureCode + "      ";
                                featureCode = featureCode.substring(0, 6);

                                for(int m = 0; m < fromMTM.size(); m++)
                                {
                                    String aStr = (String) fromMTM.get(m);
                                    String fromMachType = parseString(aStr, 1);
                                    String fromModel = parseString(aStr, 2);

                                    String key = machType + model + fromMachType + fromModel + "flf3" + featureCode + swProdStructItem.getKey();
                                    String geo = getGeo(swProdStructItem.getKey());

                                    base.put(key, geo);
                                }
                            }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))

                            ht.clear();
                            //319 = ValueMetric
                            //355 = FlatFee
                            //010 = N/A
                            //2823 = BASIC
                            //377 = RLC
                            ht.put("SWFCCAT", "319");
                            ht.put("SWFCSUBCAT", "355");
                            //ht.put("SWFCGRP", "010");
                            ht.put("LICENSETYPE", "2823");
                            ht.put("CHARGEOPTION", "377");

                            if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                            {
                                EntityItem swFeatureItem = (EntityItem) entityUpLink;

                                featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                                featureCode = featureCode + "      ";
                                featureCode = featureCode.substring(0, 6);

                                for(int m = 0; m < fromMTM.size(); m++)
                                {
                                    String aStr = (String) fromMTM.get(m);
                                    String fromMachType = parseString(aStr, 1);
                                    String fromModel = parseString(aStr, 2);

                                    String key = machType + model + fromMachType + fromModel + "flf4" + featureCode + swProdStructItem.getKey();
                                    String geo = getGeo(swProdStructItem.getKey());

                                    base.put(key, geo);
                                }
                            }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))

                            ht.clear();
                            //319 = ValueMetric
                            //355 = FlatFee
                            //010 = N/A
                            //2824 = DSLO
                            //380 = OTC
                            ht.put("SWFCCAT", "319");
                            ht.put("SWFCSUBCAT", "355");
                            //ht.put("SWFCGRP", "010");
                            ht.put("LICENSETYPE", "2824");
                            ht.put("CHARGEOPTION", "380");

                            if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                            {
                                EntityItem swFeatureItem = (EntityItem) entityUpLink;

                                featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                                featureCode = featureCode + "      ";
                                featureCode = featureCode.substring(0, 6);

                                for(int m = 0; m < fromMTM.size(); m++)
                                {
                                    String aStr = (String) fromMTM.get(m);
                                    String fromMachType = parseString(aStr, 1);
                                    String fromModel = parseString(aStr, 2);

                                    String key = machType + model + fromMachType + fromModel + "flf5" + featureCode + swProdStructItem.getKey();
                                    String geo = getGeo(swProdStructItem.getKey());

                                    base.put(key, geo);
                                }
                            }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))

                            ht.clear();
                            //319 = ValueMetric
                            //355 = FlatFee
                            //010 = N/A
                            //2824 = DSLO
                            //378 = MLC
                            ht.put("SWFCCAT", "319");
                            ht.put("SWFCSUBCAT", "355");
                            //ht.put("SWFCGRP", "010");
                            ht.put("LICENSETYPE", "2824");
                            ht.put("CHARGEOPTION", "378");

                            if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                            {
                                EntityItem swFeatureItem = (EntityItem) entityUpLink;

                                featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                                featureCode = featureCode + "      ";
                                featureCode = featureCode.substring(0, 6);

                                for(int m = 0; m < fromMTM.size(); m++)
                                {
                                    String aStr = (String) fromMTM.get(m);
                                    String fromMachType = parseString(aStr, 1);
                                    String fromModel = parseString(aStr, 2);

                                    String key = machType + model + fromMachType + fromModel + "flf6" + featureCode + swProdStructItem.getKey();
                                    String geo = getGeo(swProdStructItem.getKey());

                                    base.put(key, geo);
                                }
                            }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))

                            ht.clear();
                            //319 = ValueMetric
                            //355 = FlatFee
                            //010 = N/A
                            //2824 = DSLO
                            //379 = PLC
                            ht.put("SWFCCAT", "319");
                            ht.put("SWFCSUBCAT", "355");
                            //ht.put("SWFCGRP", "010");
                            ht.put("LICENSETYPE", "2824");
                            ht.put("CHARGEOPTION", "379");

                            if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                            {
                                EntityItem swFeatureItem = (EntityItem) entityUpLink;

                                featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                                featureCode = featureCode + "      ";
                                featureCode = featureCode.substring(0, 6);

                                for(int m = 0; m < fromMTM.size(); m++)
                                {
                                    String aStr = (String) fromMTM.get(m);
                                    String fromMachType = parseString(aStr, 1);
                                    String fromModel = parseString(aStr, 2);

                                    String key = machType + model + fromMachType + fromModel + "flf7" + featureCode + swProdStructItem.getKey();
                                    String geo = getGeo(swProdStructItem.getKey());

                                    base.put(key, geo);
                                }
                            }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))

                            ht.clear();
                            //319 = ValueMetric
                            //355 = FlatFee
                            //010 = N/A
                            //2824 = DSLO
                            //377 = RLC
                            ht.put("SWFCCAT", "319");
                            ht.put("SWFCSUBCAT", "355");
                            //ht.put("SWFCGRP", "010");
                            ht.put("LICENSETYPE", "2824");
                            ht.put("CHARGEOPTION", "377");

                            if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                            {
                                EntityItem swFeatureItem = (EntityItem) entityUpLink;

                                featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                                featureCode = featureCode + "      ";
                                featureCode = featureCode.substring(0, 6);

                                for(int m = 0; m < fromMTM.size(); m++)
                                {
                                    String aStr = (String) fromMTM.get(m);
                                    String fromMachType = parseString(aStr, 1);
                                    String fromModel = parseString(aStr, 2);

                                    String key = machType + model + fromMachType + fromModel + "flf8" + featureCode + swProdStructItem.getKey();
                                    String geo = getGeo(swProdStructItem.getKey());

                                    base.put(key, geo);
                                }
                            }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                        }//end of for(int n = 0; n < upLinkEntityItemVector.size(); n++)
                    }//end of if(swEntityItem.containModelBase(baseModel) && swEntityItem.containFromSWProdStruct(fromSWProdStructItem))
                }//end of for(int j = 0; j < Q352A2AppBaseTo.size(); j++)
            }//end of if(swei.containCOFBase(baseCOF))
        }//end of for(int i = 0; i < Q352A2AppBaseFrom.size(); i++)

        //Get all SWPRODSTRUCT associate with each optional feature which associates with base MODEL and SWPRODSTRUCT(Upgrade).
        ofb = new TreeSet();
        ofbHT = new Hashtable();
        for(int i = 0; i < Q352A2AppOptFeaBase1To.size(); i++)
        {
            SWEntityItem_OIM swei = (SWEntityItem_OIM) Q352A2AppOptFeaBase1To.get(i);

            if(swei.containModelBase(baseModel))
            {
                EntityItem OptFeaBaseModel = swei.getOptFeaBaseModel();
                String techName = PokUtils.getAttributeValue(OptFeaBaseModel, "MKTGNAME", "|", "", false);
                ofb.add(OptFeaBaseModel.getKey());
                if(techName.length() > I_128)
                {
                    techName = techName.substring(0, I_128);
                }
                ofbHT.put(OptFeaBaseModel.getKey(), techName);
            }
        }//end of for(int i = 0; i < Q352A2AppOptFeaBase1To.size(); i++)

        optionalFeatureBase = new TreeMap[ofb.size()];
        for(int i = 0; i < optionalFeatureBase.length; i++)
        {
            optionalFeatureBase[i] = new TreeMap();
        }
        arrayTechName = new String[ofb.size()];

        tsIterator = ofb.iterator();
        index = 0;
        while(tsIterator.hasNext())
        {
            String optFeaBaseModel = (String) tsIterator.next();
            for(int i = 0; i < Q352A2AppOptFeaBaseFrom.size(); i++)
            {
                SWEntityItem_OIM swei = (SWEntityItem_OIM) Q352A2AppOptFeaBaseFrom.get(i);

                if(swei.containModelBase(optFeaBaseModel))
                {
                    EntityItem fromSWProdStructItem = swei.getFromSWProdStruct();
                    Vector fromMTM = getFromMTM(fromSWProdStructItem);

                    for(int j = 0; j < Q352A2AppOptFeaBase2To.size(); j++)
                    {
                        SWEntityItem_OIM swEntityItem = (SWEntityItem_OIM) Q352A2AppOptFeaBase2To.get(j);

                        if(swEntityItem.containModelOptFeaBase(optFeaBaseModel) && swEntityItem.containFromSWProdStruct(fromSWProdStructItem))
                        {
                            EntityItem swProdStructItem = swEntityItem.getSWProdStruct();

                            Vector upLinkEntityItemVector = getUpLinkEntityItems(swProdStructItem, "SWFEATURE");
                            for(int n = 0; n < upLinkEntityItemVector.size(); n++)
                            {
                                EANEntity entityUpLink  = (EANEntity) upLinkEntityItemVector.get(n);

                                ht.clear();
                                //319 = ValueMetric
                                //355 = FlatFee
                                //010 = N/A
                                //2823 = BASIC
                                //380 = OTC
                                ht.put("SWFCCAT", "319");
                                ht.put("SWFCSUBCAT", "355");
                                //ht.put("SWFCGRP", "010");
                                ht.put("LICENSETYPE", "2823");
                                ht.put("CHARGEOPTION", "380");

                                if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                                {
                                    EntityItem swFeatureItem = (EntityItem) entityUpLink;

                                    featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                                    featureCode = featureCode + "      ";
                                    featureCode = featureCode.substring(0, 6);

                                    for(int m = 0; m < fromMTM.size(); m++)
                                    {
                                        String aStr = (String) fromMTM.get(m);
                                        String fromMachType = parseString(aStr, 1);
                                        String fromModel = parseString(aStr, 2);

                                        String key = machType + model + fromMachType + fromModel + "flf1" + featureCode + swProdStructItem.getKey();
                                        String geo = getGeo(swProdStructItem.getKey());

                                        String techName = (String)ofbHT.get(optFeaBaseModel);
                                        optionalFeatureBase[index].put(key, geo);

                                        arrayTechName[index] = techName;
                                    }
                                }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))

                                ht.clear();
                                //319 = ValueMetric
                                //355 = FlatFee
                                //010 = N/A
                                //2823 = BASIC
                                //378 = MLC
                                ht.put("SWFCCAT", "319");
                                ht.put("SWFCSUBCAT", "355");
                                //ht.put("SWFCGRP", "010");
                                ht.put("LICENSETYPE", "2823");
                                ht.put("CHARGEOPTION", "378");

                                if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                                {
                                    EntityItem swFeatureItem = (EntityItem) entityUpLink;

                                    featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                                    featureCode = featureCode + "      ";
                                    featureCode = featureCode.substring(0, 6);

                                    for(int m = 0; m < fromMTM.size(); m++)
                                    {
                                        String aStr = (String) fromMTM.get(m);
                                        String fromMachType = parseString(aStr, 1);
                                        String fromModel = parseString(aStr, 2);

                                        String key = machType + model + fromMachType + fromModel + "flf2" + featureCode + swProdStructItem.getKey();
                                        String geo = getGeo(swProdStructItem.getKey());

                                        String techName = (String)ofbHT.get(optFeaBaseModel);
                                        optionalFeatureBase[index].put(key, geo);

                                        arrayTechName[index] = techName;
                                    }
                                }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))

                                ht.clear();
                                //319 = ValueMetric
                                //355 = FlatFee
                                //010 = N/A
                                //2823 = BASIC
                                //379 = PLC
                                ht.put("SWFCCAT", "319");
                                ht.put("SWFCSUBCAT", "355");
                                //ht.put("SWFCGRP", "010");
                                ht.put("LICENSETYPE", "2823");
                                ht.put("CHARGEOPTION", "379");

                                if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                                {
                                    EntityItem swFeatureItem = (EntityItem) entityUpLink;

                                    featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                                    featureCode = featureCode + "      ";
                                    featureCode = featureCode.substring(0, 6);

                                    for(int m = 0; m < fromMTM.size(); m++)
                                    {
                                        String aStr = (String) fromMTM.get(m);
                                        String fromMachType = parseString(aStr, 1);
                                        String fromModel = parseString(aStr, 2);

                                        String key = machType + model + fromMachType + fromModel + "flf3" + featureCode + swProdStructItem.getKey();
                                        String geo = getGeo(swProdStructItem.getKey());

                                        String techName = (String)ofbHT.get(optFeaBaseModel);
                                        optionalFeatureBase[index].put(key, geo);

                                        arrayTechName[index] = techName;
                                    }
                                }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))

                                ht.clear();
                                //319 = ValueMetric
                                //355 = FlatFee
                                //010 = N/A
                                //2823 = BASIC
                                //377 = RLC
                                ht.put("SWFCCAT", "319");
                                ht.put("SWFCSUBCAT", "355");
                                //ht.put("SWFCGRP", "010");
                                ht.put("LICENSETYPE", "2823");
                                ht.put("CHARGEOPTION", "377");

                                if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                                {
                                    EntityItem swFeatureItem = (EntityItem) entityUpLink;

                                    featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                                    featureCode = featureCode + "      ";
                                    featureCode = featureCode.substring(0, 6);

                                    for(int m = 0; m < fromMTM.size(); m++)
                                    {
                                        String aStr = (String) fromMTM.get(m);
                                        String fromMachType = parseString(aStr, 1);
                                        String fromModel = parseString(aStr, 2);

                                        String key = machType + model + fromMachType + fromModel + "flf4" + featureCode + swProdStructItem.getKey();
                                        String geo = getGeo(swProdStructItem.getKey());

                                        String techName = (String)ofbHT.get(optFeaBaseModel);
                                        optionalFeatureBase[index].put(key, geo);

                                        arrayTechName[index] = techName;
                                    }
                                }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))

                                ht.clear();
                                //319 = ValueMetric
                                //355 = FlatFee
                                //010 = N/A
                                //2824 = DSLO
                                //380 = OTC
                                ht.put("SWFCCAT", "319");
                                ht.put("SWFCSUBCAT", "355");
                                //ht.put("SWFCGRP", "010");
                                ht.put("LICENSETYPE", "2824");
                                ht.put("CHARGEOPTION", "380");

                                if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                                {
                                    EntityItem swFeatureItem = (EntityItem) entityUpLink;

                                    featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                                    featureCode = featureCode + "      ";
                                    featureCode = featureCode.substring(0, 6);

                                    for(int m = 0; m < fromMTM.size(); m++)
                                    {
                                        String aStr = (String) fromMTM.get(m);
                                        String fromMachType = parseString(aStr, 1);
                                        String fromModel = parseString(aStr, 2);

                                        String key = machType + model + fromMachType + fromModel + "flf5" + featureCode + swProdStructItem.getKey();
                                        String geo = getGeo(swProdStructItem.getKey());

                                        String techName = (String)ofbHT.get(optFeaBaseModel);
                                        optionalFeatureBase[index].put(key, geo);

                                        arrayTechName[index] = techName;
                                    }
                                }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))

                                ht.clear();
                                //319 = ValueMetric
                                //355 = FlatFee
                                //010 = N/A
                                //2824 = DSLO
                                //378 = MLC
                                ht.put("SWFCCAT", "319");
                                ht.put("SWFCSUBCAT", "355");
                                //ht.put("SWFCGRP", "010");
                                ht.put("LICENSETYPE", "2824");
                                ht.put("CHARGEOPTION", "378");

                                if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                                {
                                    EntityItem swFeatureItem = (EntityItem) entityUpLink;

                                    featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                                    featureCode = featureCode + "      ";
                                    featureCode = featureCode.substring(0, 6);

                                    for(int m = 0; m < fromMTM.size(); m++)
                                    {
                                        String aStr = (String) fromMTM.get(m);
                                        String fromMachType = parseString(aStr, 1);
                                        String fromModel = parseString(aStr, 2);

                                        String key = machType + model + fromMachType + fromModel + "flf6" + featureCode + swProdStructItem.getKey();
                                        String geo = getGeo(swProdStructItem.getKey());

                                        String techName = (String)ofbHT.get(optFeaBaseModel);
                                        optionalFeatureBase[index].put(key, geo);

                                        arrayTechName[index] = techName;
                                    }
                                }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))

                                ht.clear();
                                //319 = ValueMetric
                                //355 = FlatFee
                                //010 = N/A
                                //2824 = DSLO
                                //379 = PLC
                                ht.put("SWFCCAT", "319");
                                ht.put("SWFCSUBCAT", "355");
                                //ht.put("SWFCGRP", "010");
                                ht.put("LICENSETYPE", "2824");
                                ht.put("CHARGEOPTION", "379");

                                if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                                {
                                    EntityItem swFeatureItem = (EntityItem) entityUpLink;

                                    featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                                    featureCode = featureCode + "      ";
                                    featureCode = featureCode.substring(0, 6);

                                    for(int m = 0; m < fromMTM.size(); m++)
                                    {
                                        String aStr = (String) fromMTM.get(m);
                                        String fromMachType = parseString(aStr, 1);
                                        String fromModel = parseString(aStr, 2);

                                        String key = machType + model + fromMachType + fromModel + "flf7" + featureCode + swProdStructItem.getKey();
                                        String geo = getGeo(swProdStructItem.getKey());

                                        String techName = (String)ofbHT.get(optFeaBaseModel);
                                        optionalFeatureBase[index].put(key, geo);

                                        arrayTechName[index] = techName;
                                    }
                                }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))

                                ht.clear();
                                //319 = ValueMetric
                                //355 = FlatFee
                                //010 = N/A
                                //2824 = DSLO
                                //377 = RLC
                                ht.put("SWFCCAT", "319");
                                ht.put("SWFCSUBCAT", "355");
                                //ht.put("SWFCGRP", "010");
                                ht.put("LICENSETYPE", "2824");
                                ht.put("CHARGEOPTION", "377");

                                if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                                {
                                    EntityItem swFeatureItem = (EntityItem) entityUpLink;

                                    featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                                    featureCode = featureCode + "      ";
                                    featureCode = featureCode.substring(0, 6);

                                    for(int m = 0; m < fromMTM.size(); m++)
                                    {
                                        String aStr = (String) fromMTM.get(m);
                                        String fromMachType = parseString(aStr, 1);
                                        String fromModel = parseString(aStr, 2);

                                        String key = machType + model + fromMachType + fromModel + "flf8" + featureCode + swProdStructItem.getKey();
                                        String geo = getGeo(swProdStructItem.getKey());

                                        String techName = (String)ofbHT.get(optFeaBaseModel);
                                        optionalFeatureBase[index].put(key, geo);

                                        arrayTechName[index] = techName;
                                    }
                                }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                            }//end of for(int n = 0; n < upLinkEntityItemVector.size(); n++)
                        }//end of if(swEntityItem.containModelOptFeaBase(optFeaBaseModel) && swEntityItem.containFromSWProdStruct(fromSWProdStructItem))
                    }//end of for(int i = 0; i < Q352A2AppOptFeaBase2To.size(); i++)
                }//end of if(swei.containCOFBase(baseCOF))
            }//end of for(int i = 0; i < Q352A2AppOptFeaBaseFrom.size(); i++)

            index++;
        }//end of while(tsIterator.hasNext())

        baseModelTechName = PokUtils.getAttributeValue(baseModel, "MKTGNAME", "|", "", false);
        if(baseModelTechName.length() > I_128)
        {
            baseModelTechName = baseModelTechName.substring(0, I_128);
        }

        //sb.append(":xmp." + NEWLINE);
        //sbQ523.append(":xmp." + NEWLINE);
        displayNewUpgradeBillingCodeFlatFee(base, baseModelTechName);
        for(int i = 0; i < optionalFeatureBase.length; i++)
        {
            displayNewUpgradeBillingCodeFlatFee(optionalFeatureBase[i], arrayTechName[i]);
        }
        //sb.append(":exmp." + NEWLINE);
        //sbQ523.append(":exmp." + NEWLINE);
    }

    /********************************************************************************
    * displayNewUpgradeBillingCodeFlatFee()
    *
    * @param tm TreeMap
    * @param techName String
    */
    private void displayNewUpgradeBillingCodeFlatFee(TreeMap tm, String techName)
    {
        //Group entries based on machine type and model of "upgrade from" OOF
        int index;
        Iterator tsItr;
        Set aSet;
        Iterator itr;
        int count;
        int [] cursorPos;
        Object [] arrayObj;
        TreeMap pos;
        TreeSet ts = new TreeSet();
        TreeMap [] arrayTM;
        Set tmSet = tm.keySet();
        Iterator tmItr = tmSet.iterator();
        while(tmItr.hasNext())
        {
            String aKey = (String) tmItr.next();
            aKey = aKey.substring(I_7, I_14);
            ts.add(aKey);
        }

        arrayTM = new TreeMap[ts.size()];

        index = 0;
        tsItr = ts.iterator();
        while(tsItr.hasNext())
        {
            String upgradeFromMTM = (String) tsItr.next();
            arrayTM[index] = new TreeMap();

            tmItr = tmSet.iterator();
            while(tmItr.hasNext())
            {
                String aKey = (String) tmItr.next();
                String upgrdFrmMTM = aKey.substring(I_7, I_14);
                if(upgrdFrmMTM.equals(upgradeFromMTM))
                {
                    arrayTM[index].put(aKey, tm.get(aKey));
                }
            }//end of while(tmItr.hasNext())

            index++;
        }//end of while(tsItr.hasNext())

        //Count number of distinct performance group
        //ts = new TreeSet();
        ts.clear();

        aSet = tm.keySet();
        itr = aSet.iterator();
        while(itr.hasNext())
        {
            String str = (String) itr.next();
            String pg = str.substring(I_14, I_18);
            ts.add(pg);
        }

        count = 0;

        cursorPos = new int[4];
        cursorPos[0] = I_23;
        cursorPos[1] = I_35;
        cursorPos[2] = I_47;
        cursorPos[3] = I_59;

        arrayObj = ts.toArray();

        pos = new TreeMap();

        if(arrayObj.length > 0)
        {
            if(showUpgradeOrderHeader)
            {
                sb.append(":hp1.Upgrade Order:ehp1." + NEWLINE);
                sbQ523.append(":hp1.Upgrade Order:ehp1." + NEWLINE);
                showUpgradeOrderHeader = false;
            }
            sb.append(":xmp." + NEWLINE);
            sbQ523.append(":xmp." + NEWLINE);
        }
        for(int i = 0; i < arrayObj.length; i++)
        {
            if(count < 4)
            {
                pos.put((String) arrayObj[i], new Integer(cursorPos[i % 4]));
                count++;
            }
            if((count == 4) || (i == arrayObj.length - 1))
            {
                count = 0;
                displayNewUpgradeBillingCodeFlatFee(pos, arrayTM, techName);
                pos.clear();
            }
        }
        if(arrayObj.length > 0)
        {
            sb.append(":exmp." + NEWLINE);
            sbQ523.append(":exmp." + NEWLINE);
        }
    }

    /********************************************************************************
    * displayNewUpgradeBillingCodeFlatFee()
    *
    * @param pos TreeMap
    * @param arrayTM TreeMap []
    * @param techName String
    */
    private void displayNewUpgradeBillingCodeFlatFee(TreeMap pos, TreeMap [] arrayTM, String techName)
    {
        int numPerformanceGroup = pos.size();
        int lastPGPosition;
        int lastCharPosition;
        Set set;
        Iterator itr;
        int count;
        String [] strLines;

        Integer aInteger = (Integer) pos.get(pos.lastKey());
        lastPGPosition = aInteger.intValue();
        strLines = extractStringLines(techName, I_40);

        if(lastPGPosition >= (I_22 + techName.length() + 2))
        {
            lastCharPosition = lastPGPosition + I_8;
            printChar(sb, I_22 + I_12*numPerformanceGroup - 1, "-", true);
            printChar(sbQ523, I_22 + I_12*numPerformanceGroup - 1, "-", true);
            sb.append(NEWLINE);
            sbQ523.append(NEWLINE);
        }
        else
        {
            if(techName.length() < I_40)
            {
                lastCharPosition = I_22 + techName.length() + 4 + 1;
            }
            else
            {
                lastCharPosition = I_67;
            }

            printChar(sb, lastCharPosition + 2, "-", true);
            printChar(sbQ523, lastCharPosition + 2, "-", true);
            sb.append(NEWLINE);
            sbQ523.append(NEWLINE);
        }

        for(int i = 0; i < strLines.length; i++)
        {
            printChar(sb, I_22, " ", true);
            printChar(sbQ523, I_22, " ", true);
            if(0 == i)
            {
                sb.append("To: " + strLines[i]);
                sbQ523.append("To: " + strLines[i]);
            }
            else
            {
                sb.append("    " + strLines[i]);
                sbQ523.append("    " + strLines[i]);
            }
            printChar(sb, lastCharPosition - I_22 - strLines[i].length() - 3, " ", false);
            printChar(sbQ523, lastCharPosition - I_22 - strLines[i].length() - 3, " ", false);
            sb.append("|");
            sbQ523.append("|");
            sb.append(NEWLINE);
            sbQ523.append(NEWLINE);
        }

        printChar(sb, I_22, " ", true);
        printChar(sbQ523, I_22, " ", true);

        set = pos.keySet();

        itr = set.iterator();

        count = 0;
        while(itr.hasNext())
        {
            String aStringKey = (String) itr.next();
            count++;
            if(aStringKey.equals("flf1") || aStringKey.equals("flf2") || aStringKey.equals("flf3") || aStringKey.equals("flf4") ||
                aStringKey.equals("flf5") || aStringKey.equals("flf6") || aStringKey.equals("flf7") || aStringKey.equals("flf8"))
            {
                aStringKey = "FlatFee ";
            }

            if(count < set.size())
            {
                sb.append(aStringKey + "    ");
                sbQ523.append(aStringKey + "    ");
            }
            else if(lastPGPosition >= (I_22 + techName.length() + I_4))
            {
                sb.append(aStringKey + "  ");
                sbQ523.append(aStringKey + "  ");
            }
            else
            {
                sb.append(aStringKey + "  ");
                sbQ523.append(aStringKey + "  ");
                printChar(sb, lastCharPosition - (I_22 + I_12*numPerformanceGroup - 3), " ", false);
                printChar(sbQ523, lastCharPosition - (I_22 + I_12*numPerformanceGroup - 3), " ", false);
            }
        }//end of while(itr.hasNext())

        sb.append("|" + NEWLINE);
        sbQ523.append("|" + NEWLINE);
        printChar(sb, I_22, " ", true);
        printChar(sbQ523, I_22, " ", true);

        itr = set.iterator();

        count = 0;
        while(itr.hasNext())
        {
            String aStringKey = (String) itr.next();
            count++;
            if(aStringKey.equals("flf1") || aStringKey.equals("flf2") || aStringKey.equals("flf3") || aStringKey.equals("flf4"))
            {
                aStringKey = "Basic   ";
            }
            else if(aStringKey.equals("flf5") || aStringKey.equals("flf6") || aStringKey.equals("flf7") || aStringKey.equals("flf8"))
            {
                aStringKey = "DSLO    ";
            }

            if(count < set.size())
            {
                sb.append(aStringKey + "    ");
                sbQ523.append(aStringKey + "    ");
            }
            else if(lastPGPosition >= (I_22 + techName.length() + I_4))
            {
                sb.append(aStringKey + "  ");
                sbQ523.append(aStringKey + "  ");
            }
            else
            {
                sb.append(aStringKey + "  ");
                sbQ523.append(aStringKey + "  ");
                printChar(sb, lastCharPosition - (I_22 + I_12*numPerformanceGroup - 3), " ", false);
                printChar(sbQ523, lastCharPosition - (I_22 + I_12*numPerformanceGroup - 3), " ", false);
            }
        }//end of while(itr.hasNext())

        sb.append("|" + NEWLINE);
        sbQ523.append("|" + NEWLINE);

        itr = set.iterator();

        sb.append("|");
        sbQ523.append("|");
        sb.append("Upgrade From:");
        sbQ523.append("Upgrade From:");
        printChar(sb, I_7, " ", false);
        printChar(sbQ523, I_7, " ", false);
        sb.append("|");
        sbQ523.append("|");
        count = 0;
        while(itr.hasNext())
        {
            String aStringKey = (String) itr.next();
            count++;
            if(aStringKey.equals("flf1") || aStringKey.equals("flf5"))
            {
                aStringKey = "OTC     ";
            }
            else if(aStringKey.equals("flf2") || aStringKey.equals("flf6"))
            {
                aStringKey = "MLC     ";
            }
            else if(aStringKey.equals("flf3") || aStringKey.equals("flf7"))
            {
                aStringKey = "PLC     ";
            }
            else if(aStringKey.equals("flf4") || aStringKey.equals("flf8"))
            {
                aStringKey = "RLC     ";
            }

            if(count < set.size())
            {
                sb.append(aStringKey + "    ");
                sbQ523.append(aStringKey + "    ");
            }
            else if(lastPGPosition >= (I_22 + techName.length() + I_4))
            {
                sb.append(aStringKey + "  ");
                sbQ523.append(aStringKey + "  ");
            }
            else
            {
                sb.append(aStringKey + "  ");
                sbQ523.append(aStringKey + "  ");
                printChar(sb, lastCharPosition - (I_22 + I_12*numPerformanceGroup - 3), " ", false);
                printChar(sbQ523, lastCharPosition - (I_22 + I_12*numPerformanceGroup - 3), " ", false);
            }
        }//end of while(itr.hasNext())

        sb.append("|" + NEWLINE);
        sbQ523.append("|" + NEWLINE);
        printChar(sb, lastCharPosition + 2, "-", true);
        printChar(sbQ523, lastCharPosition + 2, "-", true);
        sb.append(NEWLINE);
        sbQ523.append(NEWLINE);

        for(int i = 0; i < arrayTM.length; i++)
        {
            displayNewUpgradeBillingCodeOther(pos, arrayTM[i], numPerformanceGroup, lastPGPosition, lastCharPosition);
        }
    }

    /********************************************************************************
    * displayNewUpgradeBillingCodeUseDiscBlock()
    *
    */
    private void displayNewUpgradeBillingCodeUseDiscBlock()
    {
        TreeMap base = new TreeMap();
        String featureCode;
        String blockQuantity;

        TreeMap [] optionalFeatureBase;
        TreeSet ofb;
        Hashtable ofbHT;
        String [] arrayTechName;
        Iterator tsIterator;
        int index;
        String baseModelTechName;

        Hashtable ht = new Hashtable();

        //Get all SWPRODSTRUCT associate with base MODEL and SWPRODSTRUCT(Upgrade)
        for(int i = 0; i < Q352A2AppBaseFrom.size(); i++)
        {
            SWEntityItem_OIM swei = (SWEntityItem_OIM) Q352A2AppBaseFrom.get(i);

            if(swei.containModelBase(baseModel))
            {
                EntityItem fromSWProdStructItem = swei.getFromSWProdStruct();
                Vector fromMTM = getFromMTM(fromSWProdStructItem);

                for(int j = 0; j < Q352A2AppBaseTo.size(); j++)
                {
                    SWEntityItem_OIM swEntityItem = (SWEntityItem_OIM) Q352A2AppBaseTo.get(j);

                    if(swEntityItem.containModelBase(baseModel) && swEntityItem.containFromSWProdStruct(fromSWProdStructItem))
                    {
                        EntityItem swProdStructItem = swEntityItem.getSWProdStruct();

                        Vector upLinkEntityItemVector = getUpLinkEntityItems(swProdStructItem, "SWFEATURE");
                        for(int n = 0; n < upLinkEntityItemVector.size(); n++)
                        {
                            EANEntity entityUpLink  = (EANEntity) upLinkEntityItemVector.get(n);

                            ht.clear();
                            //319 = ValueMetric
                            //359 = UseDiscBlock
                            //010 = N/A
                            ht.put("SWFCCAT", "319");
                            ht.put("SWFCSUBCAT", "359");
                            //ht.put("SWFCGRP", "010");

                            if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                            {
                                EntityItem swFeatureItem = (EntityItem) entityUpLink;

                                featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                                featureCode = featureCode + "      ";
                                featureCode = featureCode.substring(0, 6);
                                blockQuantity = PokUtils.getAttributeValue(swFeatureItem, "BLOCKQUANTITY", "|", "0");
                                if(blockQuantity.length() == 1)
                                {
                                    blockQuantity = "00" + blockQuantity;
                                }
                                else if (blockQuantity.length() == 2)
                                {
                                    blockQuantity = "0" + blockQuantity;
                                }
                                blockQuantity = blockQuantity.substring(0, 3);

                                for(int m = 0; m < fromMTM.size(); m++)
                                {
                                    String aStr = (String) fromMTM.get(m);
                                    String fromMachType = parseString(aStr, 1);
                                    String fromModel = parseString(aStr, 2);

                                    String key = machType + model + fromMachType + fromModel + "u" + blockQuantity + featureCode + swProdStructItem.getKey();
                                    String geo = getGeo(swProdStructItem.getKey());

                                    base.put(key, geo);
                                }
                            }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                        }//end of for(int n = 0; n < upLinkEntityItemVector.size(); n++)
                    }//end of if(swEntityItem.containCOFBase(baseCOF) && swEntityItem.containFromOOF(fromOOF))
                }//end of for(int j = 0; j < Q352A2AppBaseTo.size(); j++)
            }//end of if(swei.containCOFBase(baseCOF))
        }//end of for(int i = 0; i < Q352A2AppBaseFrom.size(); i++)

        //Get all SWPRODSTRUCT associate with each optional feature which associates with base MODEL and SWPRODSTRUCT(Upgrade).
        ofb = new TreeSet();
        ofbHT = new Hashtable();
        for(int i = 0; i < Q352A2AppOptFeaBase1To.size(); i++)
        {
            SWEntityItem_OIM swei = (SWEntityItem_OIM) Q352A2AppOptFeaBase1To.get(i);

            if(swei.containModelBase(baseModel))
            {
                EntityItem OptFeaBaseModel = swei.getOptFeaBaseModel();
                String techName = PokUtils.getAttributeValue(OptFeaBaseModel, "MKTGNAME", "|", "", false);
                ofb.add(OptFeaBaseModel.getKey());
                if(techName.length() > I_128)
                {
                    techName = techName.substring(0, I_128);
                }
                ofbHT.put(OptFeaBaseModel.getKey(), techName);
            }
        }//end of for(int i = 0; i < Q352A2AppOptFeaBase1To.size(); i++)

        optionalFeatureBase = new TreeMap[ofb.size()];
        for(int i = 0; i < optionalFeatureBase.length; i++)
        {
            optionalFeatureBase[i] = new TreeMap();
        }
        arrayTechName = new String[ofb.size()];

        tsIterator = ofb.iterator();
        index = 0;
        while(tsIterator.hasNext())
        {
            String optFeaBaseModel = (String) tsIterator.next();
            for(int i = 0; i < Q352A2AppOptFeaBaseFrom.size(); i++)
            {
                SWEntityItem_OIM swei = (SWEntityItem_OIM) Q352A2AppOptFeaBaseFrom.get(i);

                if(swei.containModelBase(optFeaBaseModel))
                {
                    EntityItem fromSWProdStructItem = swei.getFromSWProdStruct();
                    Vector fromMTM = getFromMTM(fromSWProdStructItem);

                    for(int j = 0; j < Q352A2AppOptFeaBase2To.size(); j++)
                    {
                        SWEntityItem_OIM swEntityItem = (SWEntityItem_OIM) Q352A2AppOptFeaBase2To.get(j);

                        if(swEntityItem.containModelOptFeaBase(optFeaBaseModel) && swEntityItem.containFromSWProdStruct(fromSWProdStructItem))
                        {
                            EntityItem swProdStructItem = swEntityItem.getSWProdStruct();

                            Vector upLinkEntityItemVector = getUpLinkEntityItems(swProdStructItem, "SWFEATURE");
                            for(int n = 0; n < upLinkEntityItemVector.size(); n++)
                            {
                                EANEntity entityUpLink  = (EANEntity) upLinkEntityItemVector.get(n);

                                ht.clear();
                                //319 = ValueMetric
                                //359 = UseDiscBlock
                                //010 = N/A
                                ht.put("SWFCCAT", "319");
                                ht.put("SWFCSUBCAT", "359");
                                //ht.put("SWFCGRP", "010");

                                if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                                {
                                    EntityItem swFeatureItem = (EntityItem) entityUpLink;

                                    featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                                    featureCode = featureCode + "      ";
                                    featureCode = featureCode.substring(0, 6);
                                    blockQuantity = PokUtils.getAttributeValue(swFeatureItem, "BLOCKQUANTITY", "|", "0");
                                    if(blockQuantity.length() == 1)
                                    {
                                        blockQuantity = "00" + blockQuantity;
                                    }
                                    else if (blockQuantity.length() == 2)
                                    {
                                        blockQuantity = "0" + blockQuantity;
                                    }
                                    blockQuantity = blockQuantity.substring(0, 3);

                                    for(int m = 0; m < fromMTM.size(); m++)
                                    {
                                        String aStr = (String) fromMTM.get(m);
                                        String fromMachType = parseString(aStr, 1);
                                        String fromModel = parseString(aStr, 2);

                                        String key = machType + model + fromMachType + fromModel + "u" + blockQuantity + featureCode + swProdStructItem.getKey();
                                        String geo = getGeo(swProdStructItem.getKey());

                                        String techName = (String)ofbHT.get(optFeaBaseModel);
                                        optionalFeatureBase[index].put(key, geo);

                                        arrayTechName[index] = techName;
                                    }
                                }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                            }//end of for(int n = 0; n < upLinkEntityItemVector.size(); n++)
                        }//end of if(swei.containCOFOptFeaBase(optFeaBaseCOF))
                    }//end of for(int i = 0; i < Q352A2AppOptFeaBase2To.size(); i++)
                }//end of if(swei.containCOFBase(baseCOF))
            }//end of for(int i = 0; i < Q352A2AppOptFeaBaseFrom.size(); i++)

            index++;
        }//end of while(tsIterator.hasNext())

        baseModelTechName = PokUtils.getAttributeValue(baseModel, "MKTGNAME", "|", "", false);
        if(baseModelTechName.length() > I_128)
        {
            baseModelTechName = baseModelTechName.substring(0, I_128);
        }

        //sb.append(":xmp." + NEWLINE);
        //sbQ523.append(":xmp." + NEWLINE);
        displayNewUpgradeBillingCodeUseDiscBlock(base, baseModelTechName);
        for(int i = 0; i < optionalFeatureBase.length; i++)
        {
            displayNewUpgradeBillingCodeUseDiscBlock(optionalFeatureBase[i], arrayTechName[i]);
        }
        //sb.append(":exmp." + NEWLINE);
        //sbQ523.append(":exmp." + NEWLINE);
    }

    /********************************************************************************
    * displayNewUpgradeBillingCodeUseDiscBlock()
    *
    * @param tm TreeMap
    * @param techName String
    */
    private void displayNewUpgradeBillingCodeUseDiscBlock(TreeMap tm, String techName)
    {
        //Group entries based on machine type and model of "upgrade from" OOF
        int index;
        Iterator tsItr;
        Set aSet;
        Iterator itr;
        int count;
        int [] cursorPos;
        Object [] arrayObj;
        TreeMap pos;
        TreeSet ts = new TreeSet();
        TreeMap [] arrayTM;
        Set tmSet = tm.keySet();
        Iterator tmItr = tmSet.iterator();
        while(tmItr.hasNext())
        {
            String aKey = (String) tmItr.next();
            aKey = aKey.substring(I_7, I_14);
            ts.add(aKey);
        }

        arrayTM = new TreeMap[ts.size()];

        index = 0;
        tsItr = ts.iterator();
        while(tsItr.hasNext())
        {
            String upgradeFromMTM = (String) tsItr.next();
            arrayTM[index] = new TreeMap();

            tmItr = tmSet.iterator();
            while(tmItr.hasNext())
            {
                String aKey = (String) tmItr.next();
                String upgrdFrmMTM = aKey.substring(I_7, I_14);
                if(upgrdFrmMTM.equals(upgradeFromMTM))
                {
                    arrayTM[index].put(aKey, tm.get(aKey));
                }
            }//end of while(tmItr.hasNext())

            index++;
        }//end of while(tsItr.hasNext())

        //Count number of distinct performance group
        //ts = new TreeSet();
        ts.clear();

        aSet = tm.keySet();
        itr = aSet.iterator();
        while(itr.hasNext())
        {
            String str = (String) itr.next();
            String pg = str.substring(I_14, I_18);
            ts.add(pg);
        }

        count = 0;

        cursorPos = new int[4];
        cursorPos[0] = I_23;
        cursorPos[1] = I_35;
        cursorPos[2] = I_47;
        cursorPos[3] = I_59;

        arrayObj = ts.toArray();

        pos = new TreeMap();

        if(arrayObj.length > 0)
        {
            if(showUpgradeOrderHeader)
            {
                sb.append(":hp1.Upgrade Order:ehp1." + NEWLINE);
                sbQ523.append(":hp1.Upgrade Order:ehp1." + NEWLINE);
                showUpgradeOrderHeader = false;
            }
            sb.append(":xmp." + NEWLINE);
            sbQ523.append(":xmp." + NEWLINE);
        }
        for(int i = 0; i < arrayObj.length; i++)
        {
            if(count < 4)
            {
                pos.put((String) arrayObj[i], new Integer(cursorPos[i % 4]));
                count++;
            }
            if((count == 4) || (i == arrayObj.length - 1))
            {
                count = 0;
                displayNewUpgradeBillingCodeUseDiscBlock(pos, arrayTM, techName);
                pos.clear();
            }
        }
        if(arrayObj.length > 0)
        {
            sb.append(":exmp." + NEWLINE);
            sbQ523.append(":exmp." + NEWLINE);
        }
    }

    /********************************************************************************
    * displayNewUpgradeBillingCodeUseDiscBlock()
    *
    * @param pos TreeMap
    * @param arrayTM TreeMap []
    * @param techName String
    */
    private void displayNewUpgradeBillingCodeUseDiscBlock(TreeMap pos, TreeMap [] arrayTM, String techName)
    {
        int numPerformanceGroup = pos.size();
        int lastPGPosition;
        int lastCharPosition;
        String [] strLines;
        Set set;
        Iterator itr;
        int count;

        Integer aInteger = (Integer) pos.get(pos.lastKey());
        lastPGPosition = aInteger.intValue();
        strLines = extractStringLines(techName, I_40);

        if(lastPGPosition >= (I_22 + techName.length() + I_2))
        {
            lastCharPosition = lastPGPosition + I_8;
            printChar(sb, I_22 + I_12*numPerformanceGroup - 1, "-", true);
            printChar(sbQ523, I_22 + I_12*numPerformanceGroup - 1, "-", true);
            sb.append(NEWLINE);
            sbQ523.append(NEWLINE);
        }
        else
        {
            if(techName.length() < I_40)
            {
                lastCharPosition = I_22 + techName.length() + I_4 + 1;
            }
            else
            {
                lastCharPosition = I_67;
            }

            printChar(sb, lastCharPosition + 2, "-", true);
            printChar(sbQ523, lastCharPosition + 2, "-", true);
            sb.append(NEWLINE);
            sbQ523.append(NEWLINE);
        }

        for(int i = 0; i < strLines.length; i++)
        {
            printChar(sb, I_22, " ", true);
            printChar(sbQ523, I_22, " ", true);
            if(0 == i)
            {
                sb.append("To: " + strLines[i]);
                sbQ523.append("To: " + strLines[i]);
            }
            else
            {
                sb.append("    " + strLines[i]);
                sbQ523.append("    " + strLines[i]);
            }
            printChar(sb, lastCharPosition - I_22 - strLines[i].length() - 3, " ", false);
            printChar(sbQ523, lastCharPosition - I_22 - strLines[i].length() - 3, " ", false);
            sb.append("|");
            sbQ523.append("|");
            sb.append(NEWLINE);
            sbQ523.append(NEWLINE);
        }

        printChar(sb, I_22, " ", true);
        printChar(sbQ523, I_22, " ", true);

        set = pos.keySet();

        itr = set.iterator();

        count = 0;
        while(itr.hasNext())
        {
            String aStringKey = (String) itr.next();
            count++;
            if(aStringKey.substring(0, 1).equals("u"))
            {
                aStringKey = "Per User";
            }

            if(count < set.size())
            {
                sb.append(aStringKey + "    ");
                sbQ523.append(aStringKey + "    ");
            }
            else if(lastPGPosition >= (I_22 + techName.length() + I_4))
            {
                sb.append(aStringKey + "  ");
                sbQ523.append(aStringKey + "  ");
            }
            else
            {
                sb.append(aStringKey + "  ");
                sbQ523.append(aStringKey + "  ");
                printChar(sb, lastCharPosition - (I_22 + I_12*numPerformanceGroup - 3), " ", false);
                printChar(sbQ523, lastCharPosition - (I_22 + I_12*numPerformanceGroup - 3), " ", false);
            }
        }//end of while(itr.hasNext())

        sb.append("|" + NEWLINE);
        sbQ523.append("|" + NEWLINE);

        itr = set.iterator();

        sb.append("|");
        sbQ523.append("|");
        sb.append("Upgrade From:");
        sbQ523.append("Upgrade From:");
        printChar(sb, I_7, " ", false);
        printChar(sbQ523, I_7, " ", false);
        sb.append("|");
        sbQ523.append("|");
        count = 0;
        while(itr.hasNext())
        {
            String aStringKey = (String) itr.next();
            count++;
            if(aStringKey.substring(0, 1).equals("u"))
            {
                try
                {
                    Integer aInt = new Integer(aStringKey.substring(1, I_4));
                    String s = aInt.toString();
                    s = s + "   ";
                    aStringKey = "Blk of " + s.substring(0, I_3);
                }
                catch(Exception e)
                {
                    sb.append("Got exception: " + e + NEWLINE);
                    sbQ523.append("Got exception: " + e + NEWLINE);
                }
            }

            if(count < set.size())
            {
                sb.append(aStringKey + "  ");
                sbQ523.append(aStringKey + "  ");
            }
            else if(lastPGPosition >= (I_22 + techName.length() + I_4))
            {
                sb.append(aStringKey + "");
                sbQ523.append(aStringKey + "");
            }
            else
            {
                sb.append(aStringKey + "");
                sbQ523.append(aStringKey + "");
                printChar(sb, lastCharPosition - (I_22 + I_12*numPerformanceGroup - 3), " ", false);
                printChar(sbQ523, lastCharPosition - (I_22 + I_12*numPerformanceGroup - 3), " ", false);
            }
        }//end of while(itr.hasNext())

        sb.append("|" + NEWLINE);
        sbQ523.append("|" + NEWLINE);
        printChar(sb, lastCharPosition + 2, "-", true);
        printChar(sbQ523, lastCharPosition + 2, "-", true);
        sb.append(NEWLINE);
        sbQ523.append(NEWLINE);

        for(int i = 0; i < arrayTM.length; i++)
        {
            displayNewUpgradeBillingCodeOther(pos, arrayTM[i], numPerformanceGroup, lastPGPosition, lastCharPosition);
        }
    }

    /********************************************************************************
    * displayNewUpgradeBillingCodeOther()
    *
    * @param pos TreeMap
    * @param tm TreeMap
    * @param numPerformanceGroup int
    * @param lastPGPosition int
    * @param lastCharPosition int
    */
    private void displayNewUpgradeBillingCodeOther(TreeMap pos, TreeMap tm, int numPerformanceGroup, int lastPGPosition, int lastCharPosition)
    {
        //Group entries based on Geo
        TreeMap [] arrayTM;
        int index;
        Iterator tsItr;
        boolean printHeaderRow;
        TreeSet ts = new TreeSet();
        Set tmSet = tm.keySet();
        Iterator tmItr = tmSet.iterator();
        while(tmItr.hasNext())
        {
            ts.add(tm.get(tmItr.next()));
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
                String aKey = (String) tmItr.next();
                String pg = aKey.substring(I_14, I_18);
                if(geo.equals((String) tm.get(aKey)) && pos.containsKey(pg))
                {
                    arrayTM[index].put(aKey, geo);
                }
            }//end of while(tmItr.hasNext())
            index++;
        }//end of while(tsItr.hasNext())

        printHeaderRow = true;
        for(int i = 0; i < arrayTM.length; i++)
        {
            if(printHeaderRow && (arrayTM[i].size() > 0))
            {
                displayNewUpgradeBillingCodeOther(pos, arrayTM[i], lastPGPosition, lastCharPosition, true);
                displayNewUpgradeBillingCodeOther_Q523(pos, arrayTM[i], lastPGPosition, lastCharPosition, true);
                printHeaderRow = false;
            }
            else
            {
                displayNewUpgradeBillingCodeOther(pos, arrayTM[i], lastPGPosition, lastCharPosition, true);
                displayNewUpgradeBillingCodeOther_Q523(pos, arrayTM[i], lastPGPosition, lastCharPosition, true);
            }
        }

        for(int i = 0; i < arrayTM.length; i++)
        {
            if(arrayTM[i].size() > 0)
            {
                if(lastCharPosition > (lastPGPosition + 4))
                {
                    printChar(sb, lastCharPosition + 2, "-", true);
                    printChar(sbQ523, lastCharPosition + 2, "-", true);
                }
                else
                {
                    printChar(sb, I_22 + I_12*numPerformanceGroup - 1, "-", true);
                    printChar(sbQ523, I_22 + I_12*numPerformanceGroup - 1, "-", true);
                }
                sb.append(NEWLINE);
                sbQ523.append(NEWLINE);
                break;
            }
        }
    }

    /********************************************************************************
    * displayNewUpgradeBillingCodeOther()
    *
    * @param pos TreeMap
    * @param tm TreeMap
    * @param lastPGPosition int
    * @param lastCharPosition int
    * @param printRowHeader boolean
    */
    private void displayNewUpgradeBillingCodeOther(TreeMap pos, TreeMap tm, int lastPGPosition, int lastCharPosition, boolean printRowHeader)
    {
        String currentPG = "";
        String lastPG = "";
        String featureCode = "";
        String currentGeo = "";
        String lastGeo = "";
        Integer aInteger;

        int currentCursorPos = 0;
        int posPG = 0;

        Hashtable finishedHT = new Hashtable();

        boolean geoTag = false;
        boolean printTechName = false;

        //TreeSet priceHolderPos = new TreeSet();

        for(int i = 0; i <= tm.size(); i++)
        {
            Set aSet;
            Iterator aItr;

            if(!lastPG.equals("") && (finishedHT.size() < tm.size()))
            {
                //printChar(sb, lastPGPosition + 10 - currentCursorPos, " ", false);

                if(lastCharPosition > (lastPGPosition + 6))
                {
                    printChar(sb, lastCharPosition + 2 - currentCursorPos, " ", false);
                    //printChar(sbQ523, lastCharPosition + 2 - currentCursorPos, " ", false);
                }
                else
                {
                    printChar(sb, lastPGPosition + 9 - currentCursorPos, " ", false);
                    //printChar(sbQ523, lastPGPosition + 9 - currentCursorPos, " ", false);
                }

                sb.append("|" + NEWLINE);
                //sbQ523.append("|" + NEWLINE);
                currentCursorPos = 0;
                printChar(sb, I_22, " ", true);
                //printChar(sbQ523, 22, " ", true);
            }//end of if(!lastPG.equals("") && (finishedHT.size() < tm.size()))
            else if(!lastPG.equals("") && (finishedHT.size() == tm.size()))
            {
                //if(currentCursorPos < lastPGPosition)
                //   printChar(sb, lastPGPosition + 10 - currentCursorPos, " ", false);
                if(lastCharPosition > (lastPGPosition + 6))
                {
                    printChar(sb, lastCharPosition + 2 - currentCursorPos, " ", false);
                    //printChar(sbQ523, lastCharPosition + 2 - currentCursorPos, " ", false);
                }
                else
                {
                    printChar(sb, lastPGPosition + 9 - currentCursorPos, " ", false);
                    //printChar(sbQ523, lastPGPosition + 9 - currentCursorPos, " ", false);
                }

                sb.append("|" + NEWLINE);
                //sbQ523.append("|" + NEWLINE);

                ///if(priceHolderPos.size() > 0)
                //{
                //printChar(sbQ523, 22, " ", true);
                //   printPriceHolderNewUpgradeBillingCodeOther(lastPGPosition, lastCharPosition, pos, priceHolderPos);
                //   priceHolderPos.clear();
                //}

                if(!currentGeo.equals("WW"))
                {
                    sb.append(".br;:hp2.<---" + lastGeo + ":ehp2." + NEWLINE);
                    //sbQ523.append(".br;:hp2.<---" + lastGeo + ":ehp2." + NEWLINE);
                }
            }//end of else if(!lastPG.equals("") && (finishedHT.size() == tm.size()))

            currentPG = "";
            lastPG = "";
            featureCode = "";
            currentGeo = "";
            lastGeo = "";

            currentCursorPos = I_23;
            posPG = 0;

            aSet = tm.keySet();

            aItr = aSet.iterator();

            while(aItr.hasNext())
            {
                String aStringKey = (String) aItr.next();

                String upgradeFromMTM = aStringKey.substring(I_7, I_11);
                upgradeFromMTM = upgradeFromMTM + "-" + aStringKey.substring(I_11, I_14);

                if(!finishedHT.containsKey(aStringKey))
                {
                    currentPG = aStringKey.substring(I_14, I_18);
                    featureCode = aStringKey.substring(I_18, I_24);
                    currentGeo = (String) tm.get(aStringKey);
                    if(!currentPG.equals(lastPG))
                    {
                        if(currentGeo.equals("WW") && false == geoTag)
                        {
                            geoTag = true;
                            if(printRowHeader && !printTechName)
                            {
                                sb.append("|");
                                //sbQ523.append("|");
                                sb.append(upgradeFromMTM);
                                //sbQ523.append(upgradeFromMTM);
                                printChar(sb, I_20 - upgradeFromMTM.length(), " ", false);
                                //printChar(sbQ523, 20 - upgradeFromMTM.length(), " ", false);
                                sb.append("|");
                                //sbQ523.append("|");
                                printTechName = true;
                            }
                            else
                            {
                                printChar(sb, I_22, " ", true);
                                //printChar(sbQ523, 22, " ", true);
                            }
                            currentCursorPos = I_23;
                        }
                        else if(!currentGeo.equals("WW") && false == geoTag)
                        {
                            sb.append(":p.:hp2." + currentGeo + "--->" + ":ehp2." + NEWLINE);
                            //sbQ523.append(":p.:hp2." + currentGeo + "--->" + ":ehp2." + NEWLINE);
                            geoTag = true;
                            if(printRowHeader && !printTechName)
                            {
                                sb.append("|");
                                //sbQ523.append("|");
                                sb.append(upgradeFromMTM);
                                //sbQ523.append(upgradeFromMTM);
                                printChar(sb, I_20 - upgradeFromMTM.length(), " ", false);
                                //printChar(sbQ523, 20 - upgradeFromMTM.length(), " ", false);
                                sb.append("|");
                                //sbQ523.append("|");
                                printTechName = true;
                            }
                            else
                            {
                                printChar(sb, I_22, " ", true);
                                //printChar(sbQ523, 22, " ", true);
                            }
                            currentCursorPos = I_23;
                        }
                        aInteger = (Integer) pos.get(currentPG);
                        posPG = aInteger.intValue();
                        printChar(sb, posPG - currentCursorPos, " ", false);
                        //printChar(sbQ523, posPG - currentCursorPos, " ", false);
                        currentCursorPos = posPG;
                        sb.append(featureCode);
                        //sbQ523.append(featureCode);
                        currentCursorPos = currentCursorPos + 6;
                        finishedHT.put(aStringKey, "Yes");
                        //priceHolderPos.add(currentPG);
                        lastPG = currentPG;
                        lastGeo = currentGeo;
                    }//end of if(!currentPG.equals(lastPG))
                }//end of if(!finishedHT.containsKey(aStringKey))
            }//end of while(aItr.hasNext())
        }//end of for(int i = 0; i < tm.size(); i++)
    }

    /********************************************************************************
    * displayNewUpgradeBillingCodeOther_Q523()
    *
    * @param pos TreeMap
    * @param tm TreeMap
    * @param lastPGPosition int
    * @param lastCharPosition int
    * @param printRowHeader boolean
    */
    private void displayNewUpgradeBillingCodeOther_Q523(TreeMap pos, TreeMap tm, int lastPGPosition, int lastCharPosition, boolean printRowHeader)
    {
        String currentPG = "";
        String lastPG = "";
        String featureCode = "";
        String currentGeo = "";
        String lastGeo = "";
        Integer aInteger;

        int currentCursorPos = 0;
        int posPG = 0;

        Hashtable finishedHT = new Hashtable();

        boolean geoTag = false;
        boolean printTechName = false;

        TreeSet priceHolderPos = new TreeSet();

        for(int i = 0; i <= tm.size(); i++)
        {
            Set aSet;
            Iterator aItr;

            if(!lastPG.equals("") && (finishedHT.size() < tm.size()))
            {
                //printChar(sb, lastPGPosition + 10 - currentCursorPos, " ", false);

                if(lastCharPosition > (lastPGPosition + 6))
                {
                    //printChar(sb, lastCharPosition + 2 - currentCursorPos, " ", false);
                    printChar(sbQ523, lastCharPosition + 2 - currentCursorPos, " ", false);
                }
                else
                {
                    //printChar(sb, lastPGPosition + 9 - currentCursorPos, " ", false);
                    printChar(sbQ523, lastPGPosition + 9 - currentCursorPos, " ", false);
                }

                //sb.append("|" + NEWLINE);
                sbQ523.append("|" + NEWLINE);
                currentCursorPos = 0;
                printChar(sbQ523, I_22, " ", true);
                printPriceHolderNewUpgradeBillingCodeOther(lastPGPosition, lastCharPosition, pos, priceHolderPos);
                priceHolderPos.clear();
                printChar(sbQ523, I_22, " ", true);
                //printChar(sb, 22, " ", true);
            }//end of if(!lastPG.equals("") && (finishedHT.size() < tm.size()))
            else if(!lastPG.equals("") && (finishedHT.size() == tm.size()))
            {
                //if(currentCursorPos < lastPGPosition)
                //   printChar(sb, lastPGPosition + 10 - currentCursorPos, " ", false);
                if(lastCharPosition > (lastPGPosition + 6))
                {
                    //printChar(sb, lastCharPosition + 2 - currentCursorPos, " ", false);
                    printChar(sbQ523, lastCharPosition + 2 - currentCursorPos, " ", false);
                }
                else
                {
                    //printChar(sb, lastPGPosition + 9 - currentCursorPos, " ", false);
                    printChar(sbQ523, lastPGPosition + 9 - currentCursorPos, " ", false);
                }

                //sb.append("|" + NEWLINE);
                sbQ523.append("|" + NEWLINE);

                if(priceHolderPos.size() > 0)
                {
                    printChar(sbQ523, I_22, " ", true);
                    printPriceHolderNewUpgradeBillingCodeOther(lastPGPosition, lastCharPosition, pos, priceHolderPos);
                    priceHolderPos.clear();
                }

                if(!currentGeo.equals("WW"))
                {
                    //sb.append(".br;:hp2.<---" + lastGeo + ":ehp2." + NEWLINE);
                    sbQ523.append(".br;:hp2.<---" + lastGeo + ":ehp2." + NEWLINE);
                }
            }//end of else if(!lastPG.equals("") && (finishedHT.size() == tm.size()))

            currentPG = "";
            lastPG = "";
            featureCode = "";
            currentGeo = "";
            lastGeo = "";

            currentCursorPos = I_23;
            posPG = 0;

            aSet = tm.keySet();

            aItr = aSet.iterator();

            while(aItr.hasNext())
            {
                String aStringKey = (String) aItr.next();

                String upgradeFromMTM = aStringKey.substring(I_7, I_11);
                upgradeFromMTM = upgradeFromMTM + "-" + aStringKey.substring(I_11, I_14);

                if(!finishedHT.containsKey(aStringKey))
                {
                    currentPG = aStringKey.substring(I_14, I_18);
                    featureCode = aStringKey.substring(I_18, I_24);
                    currentGeo = (String) tm.get(aStringKey);
                    if(!currentPG.equals(lastPG))
                    {
                        if(currentGeo.equals("WW") && false == geoTag)
                        {
                            geoTag = true;
                            if(printRowHeader && !printTechName)
                            {
                                //sb.append("|");
                                sbQ523.append("|");
                                //sb.append(upgradeFromMTM);
                                sbQ523.append(upgradeFromMTM);
                                //printChar(sb, 20 - upgradeFromMTM.length(), " ", false);
                                printChar(sbQ523, I_20 - upgradeFromMTM.length(), " ", false);
                                //sb.append("|");
                                sbQ523.append("|");
                                printTechName = true;
                            }
                            else
                            {
                                //printChar(sb, 22, " ", true);
                                printChar(sbQ523, I_22, " ", true);
                            }
                            currentCursorPos = I_23;
                        }
                        else if(!currentGeo.equals("WW") && false == geoTag)
                        {
                            //sb.append(":p.:hp2." + currentGeo + "--->" + ":ehp2." + NEWLINE);
                            sbQ523.append(":p.:hp2." + currentGeo + "--->" + ":ehp2." + NEWLINE);
                            geoTag = true;
                            if(printRowHeader && !printTechName)
                            {
                                //sb.append("|");
                                sbQ523.append("|");
                                //sb.append(upgradeFromMTM);
                                sbQ523.append(upgradeFromMTM);
                                //printChar(sb, 20 - upgradeFromMTM.length(), " ", false);
                                printChar(sbQ523, I_20 - upgradeFromMTM.length(), " ", false);
                                //sb.append("|");
                                sbQ523.append("|");
                                printTechName = true;
                            }
                            else
                            {
                                //printChar(sb, 22, " ", true);
                                printChar(sbQ523, I_22, " ", true);
                            }
                            currentCursorPos = I_23;
                        }
                        aInteger = (Integer) pos.get(currentPG);
                        posPG = aInteger.intValue();
                        //printChar(sb, posPG - currentCursorPos, " ", false);
                        printChar(sbQ523, posPG - currentCursorPos, " ", false);
                        currentCursorPos = posPG;
                        //sb.append(featureCode);
                        sbQ523.append(featureCode);
                        currentCursorPos = currentCursorPos + 6;
                        finishedHT.put(aStringKey, "Yes");
                        priceHolderPos.add(currentPG);
                        lastPG = currentPG;
                        lastGeo = currentGeo;
                    }//end of if(!currentPG.equals(lastPG))
                }//end of if(!finishedHT.containsKey(aStringKey))
            }//end of while(aItr.hasNext())
        }//end of for(int i = 0; i < tm.size(); i++)
    }

    /********************************************************************************
    * displayGroupToGroupPBOTCBillingCode()
    *
    */
    private void displayGroupToGroupPBOTCBillingCode()
    {
        TreeMap base = new TreeMap();
        String featureCode;
        String fromPerformanceGroup;
        String toPerformanceGroup;

        TreeMap [] optionalFeatureBase;
        TreeSet ofb;
        Hashtable ofbHT;
        String [] arrayTechName;
        Iterator tsIterator;
        int index;
        String baseModelTechName;

        Hashtable ht = new Hashtable();
        //319 = ValueMetric
        //356 = Grp2GrpPBOTC
        //010 = N/A
        ht.put("SWFCCAT", "319");
        ht.put("SWFCSUBCAT", "356");
        //ht.put("SWFCGRP", "010");

        //Get all SWPRODSTRUCT associate with base MODEL. This is for COF(SW-Application/Subcription/Maintenance/Support-Base)
        for(int i = 0; i < Q352A3AppBase.size(); i++)
        {
            SWEntityItem_OIM swei = (SWEntityItem_OIM) Q352A3AppBase.get(i);

            if(swei.containModelBase(baseModel))
            {
                EntityItem swProdStructItem = swei.getSWProdStruct();

                Vector upLinkEntityItemVector = getUpLinkEntityItems(swProdStructItem, "SWFEATURE");
                for(int n = 0; n < upLinkEntityItemVector.size(); n++)
                {
                    EANEntity entityUpLink  = (EANEntity) upLinkEntityItemVector.get(n);

                    if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                    {
                        String key;
                        String geo;
                        EntityItem swFeatureItem = (EntityItem) entityUpLink;

                        featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                        featureCode = featureCode + "      ";
                        featureCode = featureCode.substring(0, 6);

                        fromPerformanceGroup = PokUtils.getAttributeValue(swFeatureItem, "FROMPERFORMANCEGROUP", "|", "---");
                        fromPerformanceGroup = fromPerformanceGroup + "   ";
                        fromPerformanceGroup = fromPerformanceGroup.substring(0, 3);
                        toPerformanceGroup = PokUtils.getAttributeValue(swFeatureItem, "TOPERFORMANCEGROUP", "|", "---");
                        toPerformanceGroup = toPerformanceGroup + "   ";
                        toPerformanceGroup = toPerformanceGroup.substring(0, 3);

                        key = machType + model + fromPerformanceGroup + toPerformanceGroup + featureCode + swProdStructItem.getKey();
                        geo = getGeo(swProdStructItem.getKey());

                        base.put(key, geo);
                    }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                }//end of for(int n = 0; n < upLinkEntityItemVector.size(); n++)
            }//end of if(swei.containCOFBase(baseCOF))
        }//end of for(int i = 0; i < Q352A3AppBase.size(); i++)

        //Get all SWPRODSTRUCT associate with each optional feature which associates with base MODEL.
        ofb = new TreeSet();
        ofbHT = new Hashtable();
        for(int i = 0; i < Q352A3AppOptFeaBase1.size(); i++)
        {
            SWEntityItem_OIM swei = (SWEntityItem_OIM) Q352A3AppOptFeaBase1.get(i);

            if(swei.containModelBase(baseModel))
            {
                EntityItem OptFeaBaseModel = swei.getOptFeaBaseModel();
                String techName = PokUtils.getAttributeValue(OptFeaBaseModel, "MKTGNAME", "|", "", false);
                ofb.add(OptFeaBaseModel.getKey());
                if(techName.length() > I_128)
                {
                    techName = techName.substring(0, I_128);
                }
                ofbHT.put(OptFeaBaseModel.getKey(), techName);
            }
        }//end of for(int i = 0; i < Q352A3AppOptFeaBase1.size(); i++)

        optionalFeatureBase = new TreeMap[ofb.size()];
        for(int i = 0; i < optionalFeatureBase.length; i++)
        {
            optionalFeatureBase[i] = new TreeMap();
        }
        arrayTechName = new String[ofb.size()];

        tsIterator = ofb.iterator();
        index = 0;
        while(tsIterator.hasNext())
        {
            String optFeaBaseModel = (String) tsIterator.next();
            for(int i = 0; i < Q352A3AppOptFeaBase2.size(); i++)
            {
                SWEntityItem_OIM swei = (SWEntityItem_OIM) Q352A3AppOptFeaBase2.get(i);

                if(swei.containModelOptFeaBase(optFeaBaseModel))
                {
                    EntityItem swProdStructItem = swei.getSWProdStruct();

                    Vector upLinkEntityItemVector = getUpLinkEntityItems(swProdStructItem, "SWFEATURE");
                    for(int n = 0; n < upLinkEntityItemVector.size(); n++)
                    {
                        EANEntity entityUpLink  = (EANEntity) upLinkEntityItemVector.get(n);

                        if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                        {
                            String key;
                            String geo;
                            String techName;
                            EntityItem swFeatureItem = (EntityItem) entityUpLink;
                            featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                            featureCode = featureCode + "      ";
                            featureCode = featureCode.substring(0, 6);

                            fromPerformanceGroup = PokUtils.getAttributeValue(swFeatureItem, "FROMPERFORMANCEGROUP", "|", "---");
                            fromPerformanceGroup = fromPerformanceGroup + "   ";
                            fromPerformanceGroup = fromPerformanceGroup.substring(0, 3);
                            toPerformanceGroup = PokUtils.getAttributeValue(swFeatureItem, "TOPERFORMANCEGROUP", "|", "---");
                            toPerformanceGroup = toPerformanceGroup + "   ";
                            toPerformanceGroup = toPerformanceGroup.substring(0, 3);

                            key = machType + model + fromPerformanceGroup + toPerformanceGroup + featureCode + swProdStructItem.getKey();
                            geo = getGeo(swProdStructItem.getKey());

                            optionalFeatureBase[index].put(key, geo);
                            techName = (String) ofbHT.get(optFeaBaseModel);

                            arrayTechName[index] = techName;
                        }//end of if(isEntityWithMatchedAttr((EntityItem) entityUpLink, ht))
                    }//end of for(int n = 0; n < upLinkEntityItemVector.size(); n++)
                }//end of if(swei.containCOFOptFeaBase(optFeaBaseCOF))
            }//end of for(int i = 0; i < Q352A3AppOptFeaBase2.size(); i++)

            index++;
        }//end of while(tsIterator.hasNext())

        baseModelTechName = PokUtils.getAttributeValue(baseModel, "MKTGNAME", "|", "", false);
        if(baseModelTechName.length() > I_128)
        {
            baseModelTechName = baseModelTechName.substring(0, I_128);
        }

        //sb.append(":xmp." + NEWLINE);
        //sbQ523.append(":xmp." + NEWLINE);
        displayGroupToGroupPBOTCBillingCode(base, baseModelTechName);
        for(int i = 0; i < optionalFeatureBase.length; i++)
        {
            displayGroupToGroupPBOTCBillingCode(optionalFeatureBase[i], arrayTechName[i]);
        }
        //sb.append(":exmp." + NEWLINE);
        //sbQ523.append(":exmp." + NEWLINE);
    }

    /********************************************************************************
    * displayGroupToGroupPBOTCBillingCode()
    *
    * @param tm TreeMap
    * @param techName String
    */
    private void displayGroupToGroupPBOTCBillingCode(TreeMap tm, String techName)
    {
        //Group entries based on Geo
        TreeMap [] arrayTM;
        int index;
        Iterator tsItr;
        TreeSet ts = new TreeSet();
        Set tmSet = tm.keySet();
        Iterator tmItr = tmSet.iterator();
        while(tmItr.hasNext())
        {
            ts.add(tm.get(tmItr.next()));
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
                String aKey = (String) tmItr.next();
                if(geo.equals((String) tm.get(aKey)))
                {
                    arrayTM[index].put(aKey, geo);
                }
            }
            index++;
        }//end of while(tsItr.hasNext())

        if(arrayTM.length > 0)
        {
            if(showG2GUpgradesHeader)
            {
                sb.append(":hp1.Group-to-Group Upgrades:ehp1." + NEWLINE);
                sbQ523.append(":hp1.Group-to-Group Upgrades:ehp1." + NEWLINE);
                showUpgradeOrderHeader = false;
            }
        }
        for(int i = 0; i < arrayTM.length; i++)
        {
            String aKey = (String) arrayTM[i].firstKey();
            String geo = (String) arrayTM[i].get(aKey);
            displayGroupToGroupPBOTCBillingCode(arrayTM[i], techName, geo);
        }
    }

    /********************************************************************************
    * displayGroupToGroupPBOTCBillingCode()
    *
    * @param tm TreeMap
    * @param techName String
    * @param geo String
    */
    private void displayGroupToGroupPBOTCBillingCode(TreeMap tm, String techName, String geo)
    {
        //Group entries based on "from" performance group
        int index;
        Iterator tsItr;
        Set aSet;
        Iterator itr;
        int count;
        int [] cursorPos;
        Object [] arrayObj;
        TreeMap pos;

        TreeSet ts = new TreeSet();
        TreeMap [] arrayTM;
        Set tmSet = tm.keySet();
        Iterator tmItr = tmSet.iterator();
        while(tmItr.hasNext())
        {
            String aKey = (String) tmItr.next();
            aKey = aKey.substring(I_7, I_10);
            ts.add(aKey);
        }

        arrayTM = new TreeMap[ts.size()];

        index = 0;
        tsItr = ts.iterator();
        while(tsItr.hasNext())
        {
            String fromPerformanceGroup = (String) tsItr.next();
            arrayTM[index] = new TreeMap();

            tmItr = tmSet.iterator();
            while(tmItr.hasNext())
            {
                String aKey = (String) tmItr.next();
                String fromPG = aKey.substring(I_7, I_10);
                if(fromPG.equals(fromPerformanceGroup))
                {
                    arrayTM[index].put(aKey, tm.get(aKey));
                }
            }//end of while(tmItr.hasNext())

            index++;
        }//end of while(tsItr.hasNext())

        //Count number of distinct performance group
        //ts = new TreeSet();
        ts.clear();

        aSet = tm.keySet();
        itr = aSet.iterator();
        while(itr.hasNext())
        {
            String str = (String) itr.next();
            String pg = str.substring(I_10, I_13);
            ts.add(pg);
        }

        count = 0;

        cursorPos = new int[6];
        cursorPos[0] = I_23;
        cursorPos[1] = I_31;
        cursorPos[2] = I_39;
        cursorPos[3] = I_47;
        cursorPos[4] = I_55;
        cursorPos[5] = I_63;

        arrayObj = ts.toArray();

        pos = new TreeMap();

        if(!geo.equals("WW"))
        {
            sb.append(":p.:hp2." + geo + "--->:ehp2." + NEWLINE);
            sbQ523.append(":p.:hp2." + geo + "--->:ehp2." + NEWLINE);
        }
        sb.append(":xmp." + NEWLINE);
        sbQ523.append(":xmp." + NEWLINE);
        for(int i = 0; i < arrayObj.length; i++)
        {
            if(count < 6)
            {
                pos.put((String) arrayObj[i], new Integer(cursorPos[i % 6]));
                count++;
            }
            if((count == 6) || (i == arrayObj.length - 1))
            {
                count = 0;
                displayGroupToGroupPBOTCBillingCode(pos, arrayTM, techName);
                pos.clear();
            }
        }//end of for(int i = 0; i < arrayObj.length; i++)
        sb.append(":exmp." + NEWLINE);
        sbQ523.append(":exmp." + NEWLINE);
        if(!geo.equals("WW"))
        {
            sb.append(".br;:hp2.<---" + geo + ":ehp2." + NEWLINE);
            sbQ523.append(".br;:hp2.<---" + geo + ":ehp2." + NEWLINE);
        }
    }

    /********************************************************************************
    * displayGroupToGroupPBOTCBillingCode()
    *
    * @param pos TreeMap
    * @param arrayTM TreeMap []
    * @param techName String
    */
    private void displayGroupToGroupPBOTCBillingCode(TreeMap pos, TreeMap [] arrayTM, String techName)
    {
        int numPerformanceGroup = pos.size();
        int lastPGPosition;
        int lastCharPosition;
        String [] strLines;
        Set set;
        Iterator itr;
        int count;

        Integer aInteger = (Integer) pos.get(pos.lastKey());
        lastPGPosition = aInteger.intValue();

        strLines = extractStringLines(techName, I_40);

        if(lastPGPosition >= (I_22 + techName.length() + 2))
        {
            lastCharPosition = lastPGPosition + 4;
            printChar(sb, I_22 + I_8*numPerformanceGroup - 1, "-", true);
            printChar(sbQ523, I_22 + I_8*numPerformanceGroup - 1, "-", true);
            sb.append(NEWLINE);
            sbQ523.append(NEWLINE);
        }
        else
        {
            if(techName.length() < I_40)
            {
                lastCharPosition = I_22 + techName.length() + 4 + 1;
            }
            else
            {
                lastCharPosition = I_67;
            }

            printChar(sb, lastCharPosition + 2, "-", true);
            printChar(sbQ523, lastCharPosition + 2, "-", true);
            sb.append(NEWLINE);
            sbQ523.append(NEWLINE);
        }

        for(int i = 0; i < strLines.length; i++)
        {
            printChar(sb, I_22, " ", true);
            printChar(sbQ523, I_22, " ", true);
            if(0 == i)
            {
                sb.append("To: " + strLines[i]);
                sbQ523.append("To: " + strLines[i]);
            }
            else
            {
                sb.append("    " + strLines[i]);
                sbQ523.append("    " + strLines[i]);
            }
            printChar(sb, lastCharPosition - I_22 - strLines[i].length() - I_3, " ", false);
            printChar(sbQ523, lastCharPosition - I_22 - strLines[i].length() - I_3, " ", false);
            sb.append("|");
            sbQ523.append("|");
            sb.append(NEWLINE);
            sbQ523.append(NEWLINE);
        }


        sb.append("|");
        sbQ523.append("|");
        sb.append("From: " + machType + "-" + model);
        sbQ523.append("From: " + machType + "-" + model);
        printChar(sb, 6, " ", false);
        printChar(sbQ523, 6, " ", false);
        sb.append("|");
        sbQ523.append("|");

        set = pos.keySet();

        itr = set.iterator();

        count = 0;
        while(itr.hasNext())
        {
            String aStringKey = (String) itr.next();
            count++;
            if(count < set.size())
            {
                sb.append(aStringKey + "     ");
                sbQ523.append(aStringKey + "     ");
            }
            else if(lastPGPosition >= (I_22 + techName.length() + I_4))
            {
                sb.append(aStringKey + "   ");
                sbQ523.append(aStringKey + "   ");
            }
            else
            {
                sb.append(aStringKey + "   ");
                sbQ523.append(aStringKey + "   ");
                printChar(sb, lastCharPosition - (I_22 + I_8*numPerformanceGroup - I_3), " ", false);
                printChar(sbQ523, lastCharPosition - (I_22 + I_8*numPerformanceGroup - I_3), " ", false);
            }
        }

        sb.append("|" + NEWLINE);
        sbQ523.append("|" + NEWLINE);
        printChar(sb, lastCharPosition + 2, "-", true);
        printChar(sbQ523, lastCharPosition + 2, "-", true);
        sb.append(NEWLINE);
        sbQ523.append(NEWLINE);

        for(int i = 0; i < arrayTM.length; i++)
        {
            //displayGroupToGroupPBOTCBillingCode(pos, arrayTM[i], numPerformanceGroup, lastPGPosition, lastCharPosition, techName);
            displayGroupToGroupPBOTCBillingCode(pos, arrayTM[i], lastPGPosition, lastCharPosition, true);
            displayGroupToGroupPBOTCBillingCode_Q523(pos, arrayTM[i], lastPGPosition, lastCharPosition, true);
        }

        printChar(sb, lastCharPosition + 2, "-", true);
        printChar(sbQ523, lastCharPosition + 2, "-", true);
        sb.append(NEWLINE);
        sbQ523.append(NEWLINE);
    }

    /********************************************************************************
    * displayGroupToGroupPBOTCBillingCode()
    *
    * @param pos TreeMap
    * @param aTM TreeMap
    * @param lastPGPosition int
    * @param lastCharPosition int
    * @param printRowHeader boolean
    */
    private void displayGroupToGroupPBOTCBillingCode(TreeMap pos, TreeMap aTM, int lastPGPosition, int lastCharPosition, boolean printRowHeader)
    {
        String currentPG = "";
        String lastPG = "";
        String featureCode = "";
        String currentGeo = "";
        //String lastGeo = "";
        Integer aInteger;

        int currentCursorPos = 0;
        int posPG = 0;

        Hashtable finishedHT = new Hashtable();

        boolean geoTag = false;
        boolean printTechName = false;

        //Begin TIR USRO-R-SDHA-65ZMVK
        TreeMap tm = new TreeMap();
        Set tmSet = aTM.keySet();
        Iterator tmItr = tmSet.iterator();
        while(tmItr.hasNext())
        {
            String aKey = (String) tmItr.next();
            String geo = (String) aTM.get(aKey);
            String pg = aKey.substring(I_10, I_13);

            if(pos.containsKey(pg))
            {
                tm.put(aKey, geo);
            }
        }//end of while(tmItr.hasNext())
        //End TIR USRO-R-SDHA-65ZMVK

        //TreeSet priceHolderPos = new TreeSet();

        for(int i = 0; i <= tm.size(); i++)
        {
            Set aSet;
            Iterator aItr;

            if(!lastPG.equals("") && (finishedHT.size() < tm.size()))
            {
                //printChar(sb, lastPGPosition + 7 - currentCursorPos, " ", false);

                if(lastCharPosition > (lastPGPosition + 6))
                {
                    printChar(sb, lastCharPosition + 2 - currentCursorPos, " ", false);
                    //printChar(sbQ523, lastCharPosition + 2 - currentCursorPos, " ", false);
                }
                else
                {
                    printChar(sb, lastPGPosition + 6 - currentCursorPos, " ", false);
                    //printChar(sbQ523, lastPGPosition + 6 - currentCursorPos, " ", false);
                }

                sb.append("|" + NEWLINE);
                //sbQ523.append("|" + NEWLINE);
                currentCursorPos = 0;
                printChar(sb, I_22, " ", true);
                //printChar(sbQ523, 22, " ", true);
            }//end of if(!lastPG.equals("") && (finishedHT.size() < tm.size()))
            else if(!lastPG.equals("") && (finishedHT.size() == tm.size()))
            {
                //printChar(sb, lastPGPosition + 7 - currentCursorPos, " ", false);
                //printChar(sb, lastPGPosition + 3 - currentCursorPos, " ", false);

                if(lastCharPosition > (lastPGPosition + 6))
                {
                    printChar(sb, lastCharPosition + 2 - currentCursorPos, " ", false);
                    //printChar(sbQ523, lastCharPosition + 2 - currentCursorPos, " ", false);
                }
                else
                {
                    printChar(sb, lastPGPosition + 6 - currentCursorPos, " ", false);
                    //printChar(sbQ523, lastPGPosition + 6 - currentCursorPos, " ", false);
                }

                sb.append("|" + NEWLINE);
                //sbQ523.append("|" + NEWLINE);

                //if(priceHolderPos.size() > 0)
                //{
                    //printChar(sbQ523, 22, " ", true);
                    //   printPriceHolderGroupToGroupPBOTCBillingCode(lastPGPosition, lastCharPosition, pos, priceHolderPos);
                    //   priceHolderPos.clear();
                //}

                if(!currentGeo.equals("WW"))
                {
                    //               sb.append(".br;:hp2.<---" + lastGeo + ":ehp2." + NEWLINE);
                    //sbQ523.append(".br;:hp2.<---" + lastGeo + ":ehp2." + NEWLINE);
                }
            }//end of else if(!lastPG.equals("") && (finishedHT.size() == tm.size()))

            currentPG = "";
            lastPG = "";
            featureCode = "";
            currentGeo = "";
            //lastGeo = "";

            currentCursorPos = I_23;
            posPG = 0;

            aSet = tm.keySet();

            aItr = aSet.iterator();

            while(aItr.hasNext())
            {
                String aStringKey = (String) aItr.next();

                String fromPerformanceGroup = aStringKey.substring(I_7, I_10);

                if(!finishedHT.containsKey(aStringKey))
                {
                    currentPG = aStringKey.substring(I_10, I_13);
                    featureCode = aStringKey.substring(I_13, I_19);
                    currentGeo = (String) tm.get(aStringKey);
                    if(!currentPG.equals(lastPG))
                    {
                        if(currentGeo.equals("WW") && false == geoTag)
                        {
                            geoTag = true;
                            if(printRowHeader && !printTechName)
                            {
                                sb.append("|");
                                //sbQ523.append("|");
                                sb.append(fromPerformanceGroup);
                                //sbQ523.append(fromPerformanceGroup);
                                printChar(sb, I_20 - fromPerformanceGroup.length(), " ", false);
                                //printChar(sbQ523, 20 - fromPerformanceGroup.length(), " ", false);
                                sb.append("|");
                                //sbQ523.append("|");
                                printTechName = true;
                            }
                            else
                            {
                                printChar(sb, I_22, " ", true);
                                //printChar(sbQ523, 22, " ", true);
                            }
                            currentCursorPos = I_23;
                        }
                        else if(!currentGeo.equals("WW") && false == geoTag)
                        {
                            //                     sb.append(":p.:hp2." + currentGeo + "--->" + ":ehp2." + NEWLINE);
                            //sbQ523.append(":p.:hp2." + currentGeo + "--->" + ":ehp2." + NEWLINE);
                            geoTag = true;
                            if(printRowHeader && !printTechName)
                            {
                                sb.append("|");
                                //sbQ523.append("|");
                                sb.append(fromPerformanceGroup);
                                //sbQ523.append(fromPerformanceGroup);
                                printChar(sb, I_20 - fromPerformanceGroup.length(), " ", false);
                                //printChar(sbQ523, 20 - fromPerformanceGroup.length(), " ", false);
                                sb.append("|");
                                //sbQ523.append("|");
                                printTechName = true;
                            }
                            else
                            {
                                printChar(sb, I_22, " ", true);
                                //printChar(sbQ523, 22, " ", true);
                            }
                            currentCursorPos = I_23;
                        }
                        aInteger = (Integer) pos.get(currentPG);
                        posPG = aInteger.intValue();
                        printChar(sb, posPG - currentCursorPos, " ", false);
                        //printChar(sbQ523, posPG - currentCursorPos, " ", false);
                        currentCursorPos = posPG;
                        sb.append(featureCode);
                        //sbQ523.append(featureCode);
                        currentCursorPos = currentCursorPos + 6;
                        finishedHT.put(aStringKey, "Yes");
                        //priceHolderPos.add(currentPG);
                        lastPG = currentPG;
                        //lastGeo = currentGeo;
                    }//end of if(!currentPG.equals(lastPG))
                }//end of if(!finishedHT.containsKey(aStringKey))
            }//end of while(aItr.hasNext())
        }//end of for(int i = 0; i < tm.size(); i++)
    }

    /********************************************************************************
    * displayGroupToGroupPBOTCBillingCode_Q523()
    *
    * @param pos TreeMap
    * @param aTM TreeMap
    * @param lastPGPosition int
    * @param lastCharPosition int
    * @param printRowHeader boolean
    */
    private void displayGroupToGroupPBOTCBillingCode_Q523(TreeMap pos, TreeMap aTM, int lastPGPosition, int lastCharPosition, boolean printRowHeader)
    {
        String currentPG = "";
        String lastPG = "";
        String featureCode = "";
        String currentGeo = "";
        //String lastGeo = "";
        Integer aInteger;

        int currentCursorPos = 0;
        int posPG = 0;

        Hashtable finishedHT = new Hashtable();

        boolean geoTag = false;
        boolean printTechName = false;
        TreeSet priceHolderPos = new TreeSet();

        //Begin TIR USRO-R-SDHA-65ZMVK
        TreeMap tm = new TreeMap();
        Set tmSet = aTM.keySet();
        Iterator tmItr = tmSet.iterator();
        while(tmItr.hasNext())
        {
            String aKey = (String) tmItr.next();
            String geo = (String) aTM.get(aKey);
            String pg = aKey.substring(I_10, I_13);

            if(pos.containsKey(pg))
            {
                tm.put(aKey, geo);
            }
        }//end of while(tmItr.hasNext())
        //End TIR USRO-R-SDHA-65ZMVK

        for(int i = 0; i <= tm.size(); i++)
        {
            Set aSet;

            Iterator aItr;

            if(!lastPG.equals("") && (finishedHT.size() < tm.size()))
            {
                //printChar(sb, lastPGPosition + 7 - currentCursorPos, " ", false);

                if(lastCharPosition > (lastPGPosition + 6))
                {
                    //printChar(sb, lastCharPosition + 2 - currentCursorPos, " ", false);
                    printChar(sbQ523, lastCharPosition + 2 - currentCursorPos, " ", false);
                }
                else
                {
                    //printChar(sb, lastPGPosition + 6 - currentCursorPos, " ", false);
                    printChar(sbQ523, lastPGPosition + 6 - currentCursorPos, " ", false);
                }

                //sb.append("|" + NEWLINE);
                sbQ523.append("|" + NEWLINE);
                currentCursorPos = 0;
                printChar(sbQ523, I_22, " ", true);
                printPriceHolderGroupToGroupPBOTCBillingCode(lastPGPosition, lastCharPosition, pos, priceHolderPos);
                priceHolderPos.clear();
                printChar(sbQ523, I_22, " ", true);
                //printChar(sb, 22, " ", true);
            }//end of if(!lastPG.equals("") && (finishedHT.size() < tm.size()))
            else if(!lastPG.equals("") && (finishedHT.size() == tm.size()))
            {
                //printChar(sb, lastPGPosition + 7 - currentCursorPos, " ", false);
                //printChar(sb, lastPGPosition + 3 - currentCursorPos, " ", false);

                if(lastCharPosition > (lastPGPosition + 6))
                {
                    //printChar(sb, lastCharPosition + 2 - currentCursorPos, " ", false);
                    printChar(sbQ523, lastCharPosition + 2 - currentCursorPos, " ", false);
                }
                else
                {
                    //printChar(sb, lastPGPosition + 6 - currentCursorPos, " ", false);
                    printChar(sbQ523, lastPGPosition + 6 - currentCursorPos, " ", false);
                }

                //sb.append("|" + NEWLINE);
                sbQ523.append("|" + NEWLINE);

                if(priceHolderPos.size() > 0)
                {
                    printChar(sbQ523, I_22, " ", true);
                    printPriceHolderGroupToGroupPBOTCBillingCode(lastPGPosition, lastCharPosition, pos, priceHolderPos);
                    priceHolderPos.clear();
                }

                if(!currentGeo.equals("WW"))
                {
                    //sb.append(".br;:hp2.<---" + lastGeo + ":ehp2." + NEWLINE);
                    //               sbQ523.append(".br;:hp2.<---" + lastGeo + ":ehp2." + NEWLINE);
                }
            }//end of else if(!lastPG.equals("") && (finishedHT.size() == tm.size()))

            currentPG = "";
            lastPG = "";
            featureCode = "";
            currentGeo = "";
            //lastGeo = "";

            currentCursorPos = I_23;
            posPG = 0;

            aSet = tm.keySet();

            aItr = aSet.iterator();

            while(aItr.hasNext())
            {
                String aStringKey = (String) aItr.next();

                String fromPerformanceGroup = aStringKey.substring(I_7, I_10);

                if(!finishedHT.containsKey(aStringKey))
                {
                    currentPG = aStringKey.substring(I_10, I_13);
                    featureCode = aStringKey.substring(I_13, I_19);
                    currentGeo = (String) tm.get(aStringKey);
                    if(!currentPG.equals(lastPG))
                    {
                        if(currentGeo.equals("WW") && false == geoTag)
                        {
                            geoTag = true;
                            if(printRowHeader && !printTechName)
                            {
                                //sb.append("|");
                                sbQ523.append("|");
                                //sb.append(fromPerformanceGroup);
                                sbQ523.append(fromPerformanceGroup);
                                //printChar(sb, 20 - fromPerformanceGroup.length(), " ", false);
                                printChar(sbQ523, I_20 - fromPerformanceGroup.length(), " ", false);
                                //sb.append("|");
                                sbQ523.append("|");
                                printTechName = true;
                            }
                            else
                            {
                                //printChar(sb, 22, " ", true);
                                printChar(sbQ523, I_22, " ", true);
                            }
                            currentCursorPos = I_23;
                        }
                        else if(!currentGeo.equals("WW") && false == geoTag)
                        {
                            //sb.append(":p.:hp2." + currentGeo + "--->" + ":ehp2." + NEWLINE);
                            //                     sbQ523.append(":p.:hp2." + currentGeo + "--->" + ":ehp2." + NEWLINE);
                            geoTag = true;
                            if(printRowHeader && !printTechName)
                            {
                                //sb.append("|");
                                sbQ523.append("|");
                                //sb.append(fromPerformanceGroup);
                                sbQ523.append(fromPerformanceGroup);
                                //printChar(sb, 20 - fromPerformanceGroup.length(), " ", false);
                                printChar(sbQ523, I_20 - fromPerformanceGroup.length(), " ", false);
                                //sb.append("|");
                                sbQ523.append("|");
                                printTechName = true;
                            }
                            else
                            {
                                //printChar(sb, 22, " ", true);
                                printChar(sbQ523, I_22, " ", true);
                            }
                            currentCursorPos = I_23;
                        }
                        aInteger = (Integer) pos.get(currentPG);
                        posPG = aInteger.intValue();
                        //printChar(sb, posPG - currentCursorPos, " ", false);
                        printChar(sbQ523, posPG - currentCursorPos, " ", false);
                        currentCursorPos = posPG;
                        //sb.append(featureCode);
                        sbQ523.append(featureCode);
                        currentCursorPos = currentCursorPos + 6;
                        finishedHT.put(aStringKey, "Yes");
                        priceHolderPos.add(currentPG);
                        lastPG = currentPG;
                        //lastGeo = currentGeo;
                    }//end of if(!currentPG.equals(lastPG))
                }//end of if(!finishedHT.containsKey(aStringKey))
            }//end of while(aItr.hasNext())
        }//end of for(int i = 0; i < tm.size(); i++)
    }

    /********************************************************************************
    * displayOtherChargeAndNoCharge()
    *
    */
    private void displayOtherChargeAndNoCharge()
    {
        if(otherCharge)
        {
            sb.append(":p.:hp2.Other Charge Features:ehp2." + NEWLINE);
            sbQ523.append(":p.:hp2.Other Charge Features:ehp2." + NEWLINE);
            displayOtherChargeAndNoCharge(Q352A4AppBaseChargeFee, Q352A4AppOptFeaBase1ChargeFee, Q352A4AppOptFeaBase2ChargeFee, true);
        }
        //displayMaxUsers();
        if(noCharge)
        {
            sb.append(":p.:hp2.No-Charge Features:ehp2." + NEWLINE);
            displayOtherChargeAndNoCharge(Q352A4AppBaseNoChargeFee, Q352A4AppOptFeaBase1NoChargeFee, Q352A4AppOptFeaBase2NoChargeFee, false);
        }
    }

    /********************************************************************************
    * displayOtherChargeAndNoCharge()
    *
    * @param base Vector
    * @param optFeaBase1 Vector
    * @param optFeaBase2 Vector
    * @param q523 boolean
    */
    private void displayOtherChargeAndNoCharge(Vector base, Vector optFeaBase1, Vector optFeaBase2, boolean q523)
    {
        TreeMap tm = new TreeMap();
        String featureCode;
        String swFCCat;
        TreeSet ofb = new TreeSet();
        Hashtable invoiceNameHT = new Hashtable();
        Iterator tsIterator;

        //Get all SWPRODSTRUCT associate with base MODEL. This is for MODEL(SW-Application/Subscription/Maintenance/Support-Base)
        for(int i = 0; i < base.size(); i++)
        {
            SWEntityItem_OIM swei = (SWEntityItem_OIM) base.get(i);

            if(swei.containModelBase(baseModel))
            {
                EntityItem swProdStructItem = swei.getSWProdStruct();

                Vector upLinkEntityItemVector = getUpLinkEntityItems(swProdStructItem, "SWFEATURE");
                for(int n = 0; n < upLinkEntityItemVector.size(); n++)
                {
                    String key;
                    String geo;
                    EntityItem swFeatureItem = (EntityItem) upLinkEntityItemVector.get(n);

                    String invoiceName = PokUtils.getAttributeValue(swProdStructItem, "INVNAME", "|", "", false);
                    featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                    featureCode = featureCode + "      ";
                    featureCode = featureCode.substring(0, 6);
                    if(invoiceName.length() > I_28)
                    {
                        invoiceName = invoiceName.substring(0, I_28);
                    }
                    invoiceNameHT.put(swProdStructItem.getKey(), invoiceName);

                    swFCCat = PokUtils.getAttributeFlagValue(swFeatureItem, "SWFCCAT");

                    key = featureCode + machType + model + swFCCat + swProdStructItem.getKey();
                    geo = getGeo(swProdStructItem.getKey());

                    tm.put(key, geo);
                }//end of for(int n = 0; n < upLinkEntityItemVector.size(); n++)
            }//end of if(swei.containModelBase(baseModel))
        }//end of for(int i = 0; i < base.size(); i++)

        //Get all SWPRODSTRUCT associate with each optional feature which associates with base MODEL.
        ofb = new TreeSet();

        for(int i = 0; i < optFeaBase1.size(); i++)
        {
            SWEntityItem_OIM swei = (SWEntityItem_OIM) optFeaBase1.get(i);

            if(swei.containModelBase(baseModel))
            {
                EntityItem OptFeaBaseModel = swei.getOptFeaBaseModel();
                ofb.add(OptFeaBaseModel.getKey());
            }
        }//end of for(int i = 0; i < optFeaBase1.size(); i++)

        tsIterator = ofb.iterator();
        while(tsIterator.hasNext())
        {
            String optFeaBaseModel = (String) tsIterator.next();
            for(int i = 0; i < optFeaBase2.size(); i++)
            {
                SWEntityItem_OIM swei = (SWEntityItem_OIM) optFeaBase2.get(i);

                if(swei.containModelOptFeaBase(optFeaBaseModel))
                {
                    EntityItem swProdStructItem = swei.getSWProdStruct();

                    Vector upLinkEntityItemVector = getUpLinkEntityItems(swProdStructItem, "SWFEATURE");
                    for(int n = 0; n < upLinkEntityItemVector.size(); n++)
                    {
                        String key;
                        String geo;
                        EntityItem swFeatureItem = (EntityItem) upLinkEntityItemVector.get(n);

                        String invoiceName = PokUtils.getAttributeValue(swProdStructItem, "INVNAME", "|", "", false);
                        featureCode = PokUtils.getAttributeValue(swFeatureItem, "FEATURECODE", "", "000000", false);
                        featureCode = featureCode + "      ";
                        featureCode = featureCode.substring(0, 6);
                        if(invoiceName.length() > I_28)
                        {
                            invoiceName = invoiceName.substring(0, I_28);
                        }
                        invoiceNameHT.put(swProdStructItem.getKey(), invoiceName);

                        swFCCat = PokUtils.getAttributeFlagValue(swFeatureItem, "SWFCCAT");

                        key = featureCode + machType + model + swFCCat + swProdStructItem.getKey();
                        geo = getGeo(swProdStructItem.getKey());

                        tm.put(key, geo);
                    }//end of if(null != entityUpLink)
                }//end of if(swei.containModelOptFeaBase(optFeaBaseModel))
            }//end of for(int i = 0; i < Q352A1AppOptFeaBase2.size(); i++)
        }//end of while(tsIterator.hasNext())

        displayOtherChargeAndNoCharge(tm, invoiceNameHT, q523);
    }

    /********************************************************************************
    * displayOtherChargeAndNoCharge()
    *
    * @param tm TreeMap
    * @param invoiceNameHT Hashtable
    * @param q523 boolean
    */
    private void displayOtherChargeAndNoCharge(TreeMap tm, Hashtable invoiceNameHT, boolean q523)
    {
        TreeMap aTreeMap;
        Set set1;
        Iterator itr1;
        Set set2;
        Iterator itr2;

        if(tm.size() > 0)
        {
            sb.append(":xmp." + NEWLINE);
            printChar(sb, I_49, " ", false);
            sb.append("Feature" + NEWLINE);
            printChar(sb, I_49, " ", false);
            sb.append("Number" + NEWLINE);
            printChar(sb, I_49, " ", false);
            sb.append("-------" + NEWLINE);
            sb.append(":exmp." + NEWLINE);
        }

        if((tm.size() > 0) && q523)
        {
            sbQ523.append(":xmp." + NEWLINE);
            printChar(sbQ523, I_49, " ", false);
            sbQ523.append("Feature" + NEWLINE);
            printChar(sbQ523, I_49, " ", false);
            sbQ523.append("Number");
            printChar(sbQ523, I_7, " ", false);
            sbQ523.append("Charge" + NEWLINE);
            printChar(sbQ523, I_49, " ", false);
            sbQ523.append("-------");
            printChar(sbQ523, I_6, " ", false);
            sbQ523.append("-------" + NEWLINE);
            sbQ523.append(":exmp." + NEWLINE);
        }

        aTreeMap = new TreeMap();

        set1 = listHeader.keySet();

        itr1 = set1.iterator();
        while(itr1.hasNext())
        {
            String aKey1 = (String) itr1.next();
            String value = (String) listHeader.get(aKey1);

            Set aset2 = tm.keySet();

            Iterator aitr2 = aset2.iterator();
            while(aitr2.hasNext())
            {
                String aKey2 = (String) aitr2.next();
                String swFCCat = aKey2.substring(I_13, I_16);
                if(value.equals(swFCCat))
                {
                    aTreeMap.put(aKey2, tm.get(aKey2));
                }
            }//end of while(itr2.hasNext())

            displayOtherChargeAndNoCharge(aTreeMap, aKey1, invoiceNameHT, q523);

            aTreeMap.clear();
        }//end of while(itr1.hasNext())

        aTreeMap.clear();

        set2 = tm.keySet();

        itr2 = set2.iterator();
        while(itr2.hasNext())
        {
            String aKey2 = (String) itr2.next();
            String swFCCat = aKey2.substring(I_13, I_16);
            //if(!listHeader.containsValue(swFCCat) && !swFCCat.equals("319"))
            //CR1019052831: Support ValueMetric for no charge section
            if(!listHeader.containsValue(swFCCat))
            {
                aTreeMap.put(aKey2, tm.get(aKey2));
            }
        }//end of while(itr2.hasNext())

        displayOtherChargeAndNoCharge(aTreeMap, "Other Features", invoiceNameHT, q523);
    }

    /********************************************************************************
    * displayOtherChargeAndNoCharge()
    *
    * @param tm TreeMap
    * @param header String
    * @param invoiceNameHT Hashtable
    * @param q523 boolean
    */
    private void displayOtherChargeAndNoCharge(TreeMap tm, String header, Hashtable invoiceNameHT, boolean q523)
    {
        if(tm.size() > 0)
        {
            sb.append(":h5." + header + NEWLINE);
            sb.append(":xmp." + NEWLINE);

            if(q523)
            {
                sbQ523.append(":h5." + header + NEWLINE);
                sbQ523.append(":xmp." + NEWLINE);
            }

            //Group based on geo
            //TreeSet ts = new TreeSet();
            //Set tmSet = tm.keySet();
            //Iterator tmItr = tmSet.iterator();
            //while(tmItr.hasNext())
            //{
            //   ts.add(tm.get(tmItr.next()));
            //}

            //TreeMap [] arrayTM = new TreeMap[ts.size()];
            //for(int i = 0; i < ts.size(); i++)
            //{
            //   arrayTM[i] = new TreeMap();
            //}

            //int index = 0;
            //Iterator tsItr = ts.iterator();
            //while(tsItr.hasNext())
            //{
            //   String geo = (String) tsItr.next();

            //   tmItr = tmSet.iterator();
            //   while(tmItr.hasNext())
            //   {
            //      String aKey = (String) tmItr.next();

            //      if(geo.equals((String) tm.get(aKey)))
            //         arrayTM[index].put(aKey, geo);
            //   }//end of while(tmItr.hasNext())

            //   displayOtherChargeAndNoCharge(invoiceNameHT, arrayTM[index], q523);

            //   index++;
            //}//end of while(tsItr.hasNext())

            displayOtherChargeAndNoCharge(invoiceNameHT, tm, q523);

            sb.append(":exmp." + NEWLINE);

            if(q523)
            {
                sbQ523.append(":exmp." + NEWLINE);
            }
        }//end of if(tm.size() > 0)
    }

    /********************************************************************************
    * displayOtherChargeAndNoCharge()
    *
    * @param invoiceNameHT Hashtable
    * @param tm TreeMap
    * @param q523 boolean
    */
    private void displayOtherChargeAndNoCharge(Hashtable invoiceNameHT, TreeMap tm, boolean q523)
    {
        String lastGeo = "";
        String currentGeo = "";
        Set tmSet = tm.keySet();
        Iterator tmItr = tmSet.iterator();
        while(tmItr.hasNext())
        {
            String aKey = (String) tmItr.next();
            String oofKey = aKey.substring(I_16);

            String invoiceName = (String) invoiceNameHT.get(oofKey);
            String featureCode = aKey.substring(0, I_6);
            currentGeo = (String) tm.get(aKey);

            if(!currentGeo.equals(lastGeo))
            {
                if(!lastGeo.equals("WW") && !lastGeo.equals(""))
                {
                    sb.append(".br;:hp2." + "<---" + lastGeo  + ":ehp2." + NEWLINE);
                    if(q523)
                    {
                        sbQ523.append(".br;:hp2." + "<---" + lastGeo  + ":ehp2." + NEWLINE);
                    }
                }

                if(!currentGeo.equals("WW"))
                {
                    sb.append(":p.:hp2." + currentGeo + "--->" + ":ehp2." + NEWLINE);
                    if(q523)
                    {
                        sbQ523.append(":p.:hp2." + currentGeo + "--->" + ":ehp2." + NEWLINE);
                    }
                }
            }//end of if(!currentGeo.equals(lastGeo))

            lastGeo = currentGeo;

            printChar(sb, 2, " ", false);
            sb.append(invoiceName);
            printChar(sb, I_49 - invoiceName.length(), " ", false);
            sb.append(featureCode);
            sb.append(NEWLINE);

            if(q523)
            {
                printChar(sbQ523, 2, " ", false);
                sbQ523.append(invoiceName);
                printChar(sbQ523, I_49 - invoiceName.length(), " ", false);
                sbQ523.append(featureCode);
                printChar(sbQ523, 7, " ", false);
                sbQ523.append("xxxxx");
                sbQ523.append(NEWLINE);
            }
        }//end of while(tmItr.hasNext()
        if(!lastGeo.equals("WW"))
        {
            sb.append(".br;:hp2." + "<---" + lastGeo  + ":ehp2." + NEWLINE);
            if(q523)
            {
                sbQ523.append(".br;:hp2." + "<---" + lastGeo  + ":ehp2." + NEWLINE);
            }
        }
    }

    /********************************************************************************
    * displayMaxUsers()
    *
    */
    private void displayMaxUsers()
    {
        //displayMaxUsers(Q352B1V1);
        //displayMaxUsers(Q352B1V2);
        displayMaxUsers(Q352B1V3);
        displayMaxUsers(Q352B1V4);
    }

    /********************************************************************************
    * displayMaxUsers()
    *
    * @param v1 Vector
    */
    private void displayMaxUsers(Vector v1)
    {
        log("In displayMaxUsers()");
        for(int i = 0; i < v1.size(); i++)
        {
            String m;
            EntityItem ei = (EntityItem) v1.get(i);
            String mt = PokUtils.getAttributeFlagValue(ei, "MACHTYPEATR");
            if(null == mt)
            {
                mt = "";
            }

            mt = mt + "    ";
            mt = mt.substring(0, 4);
            m = PokUtils.getAttributeValue(ei, "MODELATR", "|", "000");
            m = m + "   ";
            m = m.substring(0, 3);
            log("mt = " + mt + ", m = " + m);

            if(mt.equals(machType) && m.equals(model) && ei.getKey().equals(baseModelKey))
            {
                String maxUsers = PokUtils.getAttributeValue(ei, "MAXUSERS", "|", "");
                if(maxUsers.length() > 0)
                {
                    try
                    {
                        int n = Integer.parseInt(maxUsers);
                        if(n > 0)
                        {
                            sb.append(":p.:hp2.Maximum users for Offering:ehp2.  " + n + NEWLINE);
                        }
                    }
                    catch(Exception e)
                    {
                        sb.append("Got exception: " + e + NEWLINE);
                    }

                    break;
                }
            }//end of if(mt.equals(machineType) && m.equals(model))
        }//end of for(int i = 0; i < v1.size(); i++)
    }

    /********************************************************************************
    * printPriceHolderInitialBillingCodePBOTC()
    *
    * @param lastPGPosition int
    * @param pos TreeMap
    * @param priceHolderPos TreeSet
    */
    private void printPriceHolderInitialBillingCodePBOTC(int lastPGPosition, TreeMap pos, TreeSet priceHolderPos)
    {
        boolean dollarSign = false;
        int currentCursorPos = 0;

        Iterator priceHolderPosItr = priceHolderPos.iterator();
        while(priceHolderPosItr.hasNext())
        {
            String aKey = (String) priceHolderPosItr.next();
            Integer aInteger = (Integer) pos.get(aKey);
            int priceHolderPosition = aInteger.intValue();

            if(!dollarSign)
            {
                printChar(sbQ523, priceHolderPosition - I_23, " ", false);
                sbQ523.append("$");
                dollarSign = true;
                currentCursorPos = priceHolderPosition + 1;
            }
            else
            {
                printChar(sbQ523, priceHolderPosition - currentCursorPos, " ", false);
            }

            sbQ523.append("XXXX");
            if(priceHolderPosition > currentCursorPos)
            {
                currentCursorPos = priceHolderPosition + 4;
            }
            else
            {
                currentCursorPos = currentCursorPos + 4;
            }
        }//end of while(priceHolderPosItr.hasNext())

        if(priceHolderPos.size() > 0)
        {
            printChar(sbQ523, lastPGPosition + 6 - currentCursorPos, " ", false);
            sbQ523.append("|");
            sbQ523.append(NEWLINE);
        }
    }

    /********************************************************************************
    * printPriceHolderInitialBillingCodeOther()
    *
    * @param lastPGPosition int
    * @param pos TreeMap
    * @param priceHolderPos TreeSet
    */
    private void printPriceHolderInitialBillingCodeOther(int lastPGPosition, TreeMap pos, TreeSet priceHolderPos)
    {
        boolean dollarSign = false;
        int currentCursorPos = 0;

        Iterator priceHolderPosItr = priceHolderPos.iterator();
        while(priceHolderPosItr.hasNext())
        {
            String aKey = (String) priceHolderPosItr.next();
            Integer aInteger = (Integer) pos.get(aKey);
            int priceHolderPosition = aInteger.intValue();

            if(!dollarSign)
            {
                printChar(sbQ523, priceHolderPosition - I_23, " ", false);
                sbQ523.append("$");
                dollarSign = true;
                currentCursorPos = priceHolderPosition + 1;
            }
            else
            {
                printChar(sbQ523, priceHolderPosition - currentCursorPos, " ", false);
            }

            sbQ523.append("XXXX");
            if(priceHolderPosition > currentCursorPos)
            {
                currentCursorPos = priceHolderPosition + 4;
            }
            else
            {
                currentCursorPos = currentCursorPos + 4;
            }
        }//end of while(priceHolderPosItr.hasNext())

        if(priceHolderPos.size() > 0)
        {
            printChar(sbQ523, lastPGPosition + I_10 - currentCursorPos, " ", false);
            sbQ523.append("|");
            sbQ523.append(NEWLINE);
        }
    }

    /********************************************************************************
    * printPriceHolderNewUpgradeBillingCodePBOTC()
    *
    * @param lastPGPosition int
    * @param lastCharPosition int
    * @param pos TreeMap
    * @param priceHolderPos TreeSet
    */
    private void printPriceHolderNewUpgradeBillingCodePBOTC(int lastPGPosition, int lastCharPosition, TreeMap pos, TreeSet priceHolderPos)
    {
        boolean dollarSign = false;
        int currentCursorPos = 0;

        Iterator priceHolderPosItr = priceHolderPos.iterator();
        while(priceHolderPosItr.hasNext())
        {
            String aKey = (String) priceHolderPosItr.next();
            Integer aInteger = (Integer) pos.get(aKey);
            int priceHolderPosition = aInteger.intValue();

            if(!dollarSign)
            {
                printChar(sbQ523, priceHolderPosition - I_23, " ", false);
                sbQ523.append("$");
                dollarSign = true;
                currentCursorPos = priceHolderPosition + 1;
            }
            else
            {
                printChar(sbQ523, priceHolderPosition - currentCursorPos, " ", false);
            }

            sbQ523.append("XXXX");
            if(priceHolderPosition > currentCursorPos)
            {
                currentCursorPos = priceHolderPosition + 4;
            }
            else
            {
                currentCursorPos = currentCursorPos + 4;
            }
        }//end of while(priceHolderPosItr.hasNext())

        if(priceHolderPos.size() > 0)
        {
            if(lastCharPosition > (lastPGPosition + 6))
            {
                printChar(sbQ523, lastCharPosition + 2 - currentCursorPos, " ", false);
            }
            else
            {
                printChar(sbQ523, lastPGPosition + 6 - currentCursorPos, " ", false);
            }

            sbQ523.append("|");
            sbQ523.append(NEWLINE);
        }
    }

    /********************************************************************************
    * printPriceHolderNewUpgradeBillingCodeOther()
    *
    * @param lastPGPosition int
    * @param lastCharPosition int
    * @param pos TreeMap
    * @param priceHolderPos TreeSet
    */
    private void printPriceHolderNewUpgradeBillingCodeOther(int lastPGPosition, int lastCharPosition, TreeMap pos, TreeSet priceHolderPos)
    {
        boolean dollarSign = false;
        int currentCursorPos = 0;

        Iterator priceHolderPosItr = priceHolderPos.iterator();
        while(priceHolderPosItr.hasNext())
        {
            String aKey = (String) priceHolderPosItr.next();
            Integer aInteger = (Integer) pos.get(aKey);
            int priceHolderPosition = aInteger.intValue();

            if(!dollarSign)
            {
                printChar(sbQ523, priceHolderPosition - I_23, " ", false);
                sbQ523.append("$");
                dollarSign = true;
                currentCursorPos = priceHolderPosition + 1;
            }
            else
            {
                printChar(sbQ523, priceHolderPosition - currentCursorPos, " ", false);
            }

            sbQ523.append("XXXX");
            if(priceHolderPosition > currentCursorPos)
            {
                currentCursorPos = priceHolderPosition + I_4;
            }
            else
            {
                currentCursorPos = currentCursorPos + I_4;
            }
        }//end of while(priceHolderPosItr.hasNext())

        if(priceHolderPos.size() > 0)
        {
            if(lastCharPosition > (lastPGPosition + I_6))
            {
                printChar(sbQ523, lastCharPosition + I_2 - currentCursorPos, " ", false);
            }
            else
            {
                printChar(sbQ523, lastPGPosition + I_12 - currentCursorPos, " ", false);
            }

            sbQ523.append("|");
            sbQ523.append(NEWLINE);
        }
    }

    /********************************************************************************
    * printPriceHolderGroupToGroupPBOTCBillingCode()
    *
    * @param lastPGPosition int
    * @param lastCharPosition int
    * @param pos TreeMap
    * @param priceHolderPos TreeSet
    */
    private void printPriceHolderGroupToGroupPBOTCBillingCode(int lastPGPosition, int lastCharPosition, TreeMap pos, TreeSet priceHolderPos)
    {
        boolean dollarSign = false;
        int currentCursorPos = 0;

        Iterator priceHolderPosItr = priceHolderPos.iterator();
        while(priceHolderPosItr.hasNext())
        {
            String aKey = (String) priceHolderPosItr.next();
            Integer aInteger = (Integer) pos.get(aKey);
            int priceHolderPosition = aInteger.intValue();

            if(!dollarSign)
            {
                printChar(sbQ523, priceHolderPosition - I_23, " ", false);
                sbQ523.append("$");
                dollarSign = true;
                currentCursorPos = priceHolderPosition + 1;
            }
            else
            {
                printChar(sbQ523, priceHolderPosition - currentCursorPos, " ", false);
            }

            sbQ523.append("XXXX");
            if(priceHolderPosition > currentCursorPos)
            {
                currentCursorPos = priceHolderPosition + 4;
            }
            else
            {
                currentCursorPos = currentCursorPos + 4;
            }
        }//end of while(priceHolderPosItr.hasNext())

        if(priceHolderPos.size() > 0)
        {
            if(lastCharPosition > (lastPGPosition + 6))
            {
                printChar(sbQ523, lastCharPosition + 2 - currentCursorPos, " ", false);
            }
            else
            {
                printChar(sbQ523, lastPGPosition + 6 - currentCursorPos, " ", false);
            }

            sbQ523.append("|");
            sbQ523.append(NEWLINE);
        }
    }

    /********************************************************************************
    * getUpLinkEntityItem()
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
            log("eai.getEntityType() = " + eai.getEntityType());
            if(eai.getEntityType().equals(destType))
            {
                entity = eai;
                break;
            }
        }

        return entity;
    }

    /********************************************************************************
    * getUpLinkEntityItems()
    *
    * @param r EntityItem
    * @param destType String
    * @returns Vector
    */
    private Vector getUpLinkEntityItems(EntityItem r, String destType)
    {
        Vector destVct = new Vector(1);

        for(int i = 0; i < r.getUpLinkCount(); i++)
        {
            EANEntity entity = r.getUpLink(i);
            if(entity.getEntityType().equals(destType))
            {
                destVct.addElement(entity);
            }
        }

        return destVct;
    }

    /********************************************************************************
    * printChar()
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
    * extractStringLines()
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
        int intMaxLine = I_20;
        int n = 0;
        String[] strWrappedText;
        int num = 0;
        String[] returnedStrWrappedText;
        int j = 0;

        strTextToBeWrapped = strTextToBeWrapped.trim();
        if(0 == strTextToBeWrapped.length())
        {
            returnedStrWrappedText = new String[1];
            returnedStrWrappedText[0] = " ";
        }
        else
        {
            if(strTextToBeWrapped.length() > I_128)
            {
                strTextToBeWrapped = strTextToBeWrapped.substring(0, I_128);
            }

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

            for(int i = 0; i < I_20; i++)
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
        }//end of else

        return returnedStrWrappedText;
    }

//    /********************************************************************************
//    * parseString()
//    *
//    * @param str String
//    * @param n int
//    * @returns String
//    */
//    private String parseString(String str, int n)
//    {
//        if((0 == n) || (n < 0))
//        {
//            return str;
//        }

//        int i = n - 1;
//        String result = "";
//        int index = -3;
//        Vector pos = new Vector(1);

//        if(0 == i)
//        {
//            return str.substring(0, str.indexOf("<:>"));
//        }

//        while(true)
//        {
//            index = str.indexOf("<:>", index + 3);

//            if(index < 0)
//            {
//                break;
//            }

//            pos.addElement(new Integer(index));
//        }//end of while(true)

//        if(i > pos.size())
//        {
//            return str;
//        }

//        if(i == pos.size())
//        {
//            result = str.substring(((Integer) pos.get(i - 1)).intValue() + 3);
//        }
//        else
//        {
//            result = str.substring(((Integer) pos.get(i - 1)).intValue() + 3, ((Integer) Pos.get(i)).intValue());
//        }

//        return result;
//    }

    /********************************************************************************
    *
    *
    * @param str String
    * @param n int
    * @returns String
    */
    private String parseString(String str, int n)
    {
        //Java StringTokenizer works only when there is no empty string before delimiter and after delimiter
        StringTokenizer st;
        String result = "";
        int i = 0;

        if((0 == n) || (n < 0))
        {
            result = str;
        }
        else
        {
            st = new StringTokenizer(str, "<:>");

            while(st.hasMoreTokens())
            {
                result = st.nextToken();
                i++;
                if(i == n)
                {
                    break;
                }
            }

            if(n > i)
            {
                result = str;
            }
        }

        return result;
    }

    /********************************************************************************
    * containBaseModel()
    *
    * @param aVector Vector
    * @returns boolean
    */
    private boolean containBaseModel(Vector aVector)
    {
        boolean rc = false;

        for(int i = 0; i < aVector.size(); i++)
        {
            SWEntityItem_OIM swei = (SWEntityItem_OIM) aVector.get(i);
            if(swei.containModelBase(baseModel))
            {
                rc = true;
                break;
            }
        }

        return rc;
    }

    /********************************************************************************
    * getFromMTM()
    *
    * @param fromSWProdStructItem EntityItem
    * @returns Vector
    */
    Vector getFromMTM(EntityItem fromSWProdStructItem)
    {
        Vector v = new Vector(1);

        Set aSet = fromMTMHT.keySet();

        Iterator itr = aSet.iterator();

        while(itr.hasNext())
        {
            String key = (String) itr.next();

            String eiKey = parseString(key, 1);
            EntityItem ei = (EntityItem) fromMTMHT.get(key);
            String modelKey = ei.getKey();

            if(eiKey.equals(fromSWProdStructItem.getKey()) && modelKey.equals(baseModelKey))
            {
                String mt = parseString(key, 2);
                String m = parseString(key, 3);
                v.addElement(mt + "<:>" + m);
            }
        }//end of while(itr.hasNext())

        return v;
    }

    private void log(String str)
    {
        if(debug)
        {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            String dt = sdf.format(new Date());
            debugBuffer.append("<br />" + dt + "  " + str + NEWLINE);
        }
    }

    /********************************************************************************
    * showDebugInfo()
    *
    * @returns StringBuffer
    */
    public StringBuffer showDebugInfo()
    {
        debugBuffer.append("-->");
        return debugBuffer;
    }

    /********************************************************************************
    * cleanUp()
    *
    */
    public void cleanUp()
    {
        sb.delete(0, sb.length());
        sb = null;

        sbQ523.delete(0, sbQ523.length());
        sbQ523 = null;

        debugBuffer.delete(0, debugBuffer.length());
        debugBuffer = null;

        SWProdStructV.clear();
        SWProdStructV = null;

        SWProdStructVector.clear();
        SWProdStructVector = null;

        SWProdStructVectorNoChargeFee.clear();
        SWProdStructVectorNoChargeFee = null;

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

        SWAppBase.clear();
        SWAppBase = null;

        Q352A1AppBase.clear();
        Q352A1AppBase = null;

        Q352A1AppOptFeaBase1.clear();
        Q352A1AppOptFeaBase1 = null;

        Q352A1AppOptFeaBase2.clear();
        Q352A1AppOptFeaBase1 = null;

        Q352A2AppBaseTo.clear();
        Q352A2AppBaseTo = null;

        Q352A2AppOptFeaBase1To.clear();
        Q352A2AppOptFeaBase1To = null;

        Q352A2AppOptFeaBase2To.clear();
        Q352A2AppOptFeaBase2To = null;

        Q352A2AppBaseFrom.clear();
        Q352A2AppBaseFrom = null;

        fromMTMHT.clear();
        fromMTMHT = null;

        Q352A2AppOptFeaBaseFrom.clear();
        Q352A2AppOptFeaBaseFrom = null;

        Q352A3AppBase.clear();
        Q352A3AppBase = null;

        Q352A3AppOptFeaBase1.clear();
        Q352A3AppOptFeaBase1 = null;

        Q352A3AppOptFeaBase2.clear();
        Q352A3AppOptFeaBase2 = null;

        Q352A4AppBaseChargeFee.clear();
        Q352A4AppBaseChargeFee = null;

        Q352A4AppOptFeaBase1ChargeFee.clear();
        Q352A4AppOptFeaBase1ChargeFee = null;

        Q352A4AppOptFeaBase2ChargeFee.clear();
        Q352A4AppOptFeaBase2ChargeFee = null;

        Q352A4AppBaseNoChargeFee.clear();
        Q352A4AppBaseNoChargeFee = null;

        Q352A4AppOptFeaBase1NoChargeFee.clear();
        Q352A4AppOptFeaBase1NoChargeFee = null;

        Q352A4AppOptFeaBase2NoChargeFee.clear();
        Q352A4AppOptFeaBase2NoChargeFee = null;

        listHeader.clear();
        listHeader = null;

        Q352B1V1.clear();
        Q352B1V1 = null;

        Q352B1V2.clear();
        Q352B1V2 = null;

        Q352B1V3.clear();
        Q352B1V3 = null;

        Q352B1V4.clear();
        Q352B1V4 = null;
    }
}
