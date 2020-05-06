// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

/**  This CLASS is to create the Service RFA Internal Announcement Letter Report. */

package com.ibm.transform.oim.eacm.util;

import COM.ibm.eannounce.objects.*;
import java.util.*;
import java.text.*;

// 19.04
//  - PDS questions
//  - update processGeoInfo()
//  - update deriveA0110()
//  - update getA0116GeoInfo()
//  - update deriveA0116()
//  - update deriveA0119_120_126()
//  - update deriveA0157()
//  - update deriveA0158()
//  - update deriveA0155()
//  - update getDataA0208()
//  - update getSof() which causes updates in deriveA0153(), deriveA0154(), formatA0170Answer()
//  - formatA0170Answer()
//  - update deriveA0148(), formatA0148Answer()
//  - A0153 is removed, rename A0154 to A0153.
//  - A0190 is removed.
//  - P015D, P015E, P017K are removed.
//  - Add P031A

// $Log: SvcIntAnn.java,v $
// Revision 1.11  2008/01/22 17:12:15  wendy
// Cleanup RSA warnings
//
// Revision 1.10  2006/10/23 15:47:41  wendy
// Corrected TIR 6UULH9, ConcurrentModificationException
//  caused by clearing vct within an iterator
//
// Revision 1.9  2006/10/16 20:23:32  wendy
// Allow exception to flow to jsp for debug
//
// Revision 1.8  2006/04/04 01:59:52  anhtuan
// Services RFA Guide Update for version 21.04.
//
// Revision 1.7  2006/03/16 16:00:30  anhtuan
// Services RFA Guide Update for version 20.10.
//
// Revision 1.6  2006/01/26 15:36:26  anhtuan
// AHE copyright.
//
// Revision 1.5  2006/01/02 20:47:52  anhtuan
// DQA changes for table summary, id and headers attributes.
//
// Revision 1.4  2005/10/05 02:35:22  anhtuan
// Fix Jtest. Jtest does not allow multiple returns from a method.
//
// Revision 1.3  2005/09/28 14:21:59  couto
// WebKing fixes: closed <p> and <br> tags.
//
// Revision 1.2  2005/09/14 14:16:17  wendy
// Fix null ptr
//
// Revision 1.1  2005/09/14 03:55:00  anhtuan
// Init OIM3.0b
//
// Revision 1.14  2005/07/14 17:43:32  couto
// br tag closed.
//
// Revision 1.13  2005/05/05 15:03:01  anhtuan
// TIR USRO-R-JMGR-6C2T3R
// TIR USRO-R-JMGR-6C2TBN
// TIR USRO-R-JMGR-6C2TJU
// TIR USRO-R-JMGR-6C2TG3
//
// Revision 1.12  2005/04/15 19:56:30  anhtuan
// CR0310053420: Services RFA Guide Update for version 19.10 and 20.04.
//
// Revision 1.11  2005/01/31 04:48:07  anhtuan
// CR1123044433.
//
// Revision 1.10  2004/06/17 12:48:32  anhtuan
// Put in comments.
//
// Revision 1.9  2004/06/17 12:38:49  anhtuan
// Check CHANNEL's AP geo for PDS questions P014A,B, P015A,B,C,F,G and P016B,C.
//
// Revision 1.8  2004/06/10 04:56:23  anhtuan
// Don't show WW geo tag for sales contacts.
//
// Revision 1.7  2004/06/05 22:20:25  anhtuan
// Turn off VE's dump in debug information.
//
// Revision 1.6  2004/06/03 17:58:40  anhtuan
// Work on TIR USRO-R-JMGR-5ZCNYK and TIR USRO-R-JMGR-5Z4NHL.
//
// Revision 1.5  2004/05/27 18:57:54  anhtuan
// Work on TIR ID # - USRO-R-JMGR-5ZCNYK.
//
// Revision 1.4  2004/05/27 18:26:26  anhtuan
// Work on TIR ID # - USRO-R-JMGR-5ZCNYK.
//
// Revision 1.3  2004/05/25 03:39:53  anhtuan
// Added ; after &nbsp.
//
// Revision 1.2  2004/04/27 17:38:58  anhtuan
// Service Internal Announcement Letter 19.04.
//
// Revision 1.1.1.1  2004/01/26 17:40:03  chris
// Latest East Coast Source
//
// Revision 1.10  2004/01/10 01:45:42  anhtuan
// CR 123103241.
//
// Revision 1.9  2003/12/22 19:14:38  pingchen
// Add support to three new relators CMPNTRELSOF,
// FEATURERELSOF and FEATURERELCMPNT for
// A0119, A0120 and A0126.
//
// Revision 1.8  2003/12/16 15:58:37  pingchen
// Add logic to support new navigation changes for CR 4033.
//
// Revision 1.7  2003/11/21 17:56:53  pingchen
// Show Offering name when there is one and only one offer
// in the announcement.
//
// Revision 1.6  2003/11/17 15:30:53  pingchen
// Change sort logic for A0208 - Plan Availability Date first,
// then MKTGNAME.
//
// Revision 1.5  2003/11/11 17:02:54  pingchen
// Add logging timestamp in debug information
//
// Revision 1.4  2003/10/30 18:23:59  pingchen
// Change size of table for A0208.
//
// Revision 1.3  2003/10/28 16:56:26  pingchen
// Add fully-qualified offering name support
//
// Revision 1.2  2003/10/23 17:08:05  pingchen
// Add additional information to question A0200.
//
// Revision 1.1  2003/10/22 14:46:43  pingchen
// New for V1.2 IGS Seervices support
//

/**********************************************************************************
* SvcIntAnn class
*
*
*/
public class SvcIntAnn
{
    /***************
    * Version
    */
    public static final String VERSION = "$Revision: 1.11 $";

    // RFA Geo Mapping

    private static final byte RFA_AP    = 0x40;
    private static final byte RFA_CAN   = 0x20;
    private static final byte RFA_EMEA  = 0x10;
    private static final byte RFA_LA    = 0x08;
    private static final byte RFA_US    = 0x04;
    private static final byte RFA_WW    = 0x7C;
    private static final byte RFA_NO_GEO   = 0x00;
    private static final byte RFA_NOT_INIT    = 0x01;

    // Define Hashtable to hold needed PDS and Q&A answers
    private TreeMap pdsAndQ = new TreeMap();

    //Define vectors to hold information
    private Vector availVector = null;
    private Vector sofV = null;
    private Vector cmpntV = null;
    private Vector featureV = null;
    private Vector channelV1 = null;
    private Vector channelV2 = null;
    private Vector channelV3 = null;

    //Define Hashtable to hold geo information
    private Hashtable usGeoHT;
    private Hashtable apGeoHT;
    private Hashtable laGeoHT;
    private Hashtable canGeoHT;
    private Hashtable emeaGeoHT;
    private Hashtable geoHT;

    // Define RFA Guide Variables
    private StringBuffer debugBuffer = new StringBuffer();
    private StringBuffer tmpSB = new StringBuffer();

    // Constructor parameters
    private EntityList list;
    private GeneralAreaList gaList;

    // Include Header control
    private boolean includeHdr = true;

    // Announce LOB is ITS
    private boolean annLobITS = true;

    //Define Hashtable to hold diplay order of Geo
    private Hashtable geoOrderHT;

    private static final int L1 = 22;

    private static final int I_N3 = -3;

    private static final char[] FOOL_JTEST = {'\n'};
    static final String NEWLINE = new String(FOOL_JTEST);

    /***********************************************
    * Constructor
    *
    * @param aList EntityList
    * @param aGal GeneralAreaList
    */
    public SvcIntAnn(EntityList aList, GeneralAreaList aGal)
    {
        // Set up handle to extract entity list
//        if(list != null)
        if(aList != null)
        {
            list = aList;
        }

        // Set up handle to GeneralAreaList
//        if(gaList != null)
        if(aGal != null)
        {
            gaList = aGal;
        }
        usGeoHT = new Hashtable();
        apGeoHT = new Hashtable();
        laGeoHT = new Hashtable();
        canGeoHT = new Hashtable();
        emeaGeoHT = new Hashtable();
        geoHT = new Hashtable();

        geoOrderHT = new Hashtable();
        //Display order is as followed
        //US AP LA CAN EMEA
        geoOrderHT.put("No GEO Found", "31");          //00000
        geoOrderHT.put("EMEA", "30");                  //00001
        geoOrderHT.put("CAN", "28");                   //00010
        geoOrderHT.put("CAN, EMEA", "29");             //00011
        geoOrderHT.put("LA", "24");                    //00100
        geoOrderHT.put("LA, EMEA", "27");              //00101
        geoOrderHT.put("LA, CAN", "25");               //00110
        geoOrderHT.put("LA, CAN, EMEA", "26");         //00111
        geoOrderHT.put("AP", "16");                    //01000
        geoOrderHT.put("AP, EMEA", "23");              //01001
        geoOrderHT.put("AP, CAN", "21");               //01010
        geoOrderHT.put("AP, CAN, EMEA", "22");         //01011
        geoOrderHT.put("AP, LA", "17");                //01100
        geoOrderHT.put("AP, LA, EMEA", "20");          //01101
        geoOrderHT.put("AP, LA, CAN", "18");           //01110
        geoOrderHT.put("AP, LA, CAN, EMEA", "19");     //01111
        geoOrderHT.put("US", "01");                    //10000
        geoOrderHT.put("US, EMEA", "15");              //10001
        geoOrderHT.put("US, CAN", "13");               //10010
        geoOrderHT.put("US, CAN, EMEA", "14");         //10011
        geoOrderHT.put("US, LA", "09");                //10100
        geoOrderHT.put("US, LA, EMEA", "12");          //10101
        geoOrderHT.put("US, LA, CAN", "10");           //10110
        geoOrderHT.put("US, LA, CAN, EMEA", "11");     //10111
        geoOrderHT.put("US, AP", "02");                //11000
        geoOrderHT.put("US, AP, EMEA", "08");          //11001
        geoOrderHT.put("US, AP, CAN", "06");           //11010
        geoOrderHT.put("US, AP, CAN, EMEA", "07");     //11011
        geoOrderHT.put("US, AP, LA", "03");            //11100
        geoOrderHT.put("US, AP, LA, EMEA", "05");      //11101
        geoOrderHT.put("US, AP, LA, CAN", "04");       //11110
        geoOrderHT.put("WW", "32");                    //11111
    } // end of constructor

    /**
    * The method builds a string of current date/time.
    *
    * @return String
    */
    public String myDate()
    {
        Date          d       = new Date();
        String        myDate  = new String();

        StringBuffer  today   = new StringBuffer();

        SimpleDateFormat  sdf  = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        myDate = sdf.format(d);

        today.append("Created on  ");
        today.append(myDate);

        if(today.length() > L1)
        {
            today.insert(L1, " at");
        }

        return ("<p>" + today.toString() + "</p>");
    } //end of myDate method

    /**
    * Return TreeMap pdsAndQ
    *
    * @return TreeMap
    */
    public TreeMap getPdsAndQ()
    {
        return pdsAndQ;
    }

    /**
    * Debug information
    *
    * @return String
    */
    public String getDebugBuffer()
    {
        Iterator iks;
        Set ks = pdsAndQ.keySet();
        int pdsAndQSize = ks.size();
        debugBuffer.append("<!-- 11/20/2003 - pdsAndQ size = " + pdsAndQSize + " -->" + NEWLINE);
        iks = ks.iterator();
        for(int i = 0; iks.hasNext() && i<pdsAndQSize; i++)
        {
            String key = (String) iks.next();
            String value = (String) pdsAndQ.get(key);
            debugBuffer.append("<!-- key = " + key + " and value = " + value + " -->" + NEWLINE);
        }
        log("end of debug information");
        ks.clear();
        return debugBuffer.toString();
    }

    /**
    * clear buffers
    *
    */
    public void clearBuffer()
    {
        // Remove content in the debug buffer
        int length = debugBuffer.length();
        if(length > 0)
        {
            debugBuffer.delete(0,length);
        }
        // Clear the pdsAndQ TreeMap
        pdsAndQ.clear();
    }

    private boolean isYes(String key)
    {
        boolean rc = true;
        // Used to check the PDS answer is Yes
        String result = (String) pdsAndQ.get(key);
        if(result != null )
        {
            if(result.equals("Y"))
            {
                rc = true;
            }
            else
            {
                rc = false;
            }
        }
        else
        {
            debugBuffer.append("<!-- Unable to find key = " + key + " -->" + NEWLINE);
            rc = false;
        }

        return rc;
    }

    private boolean isNo(String key)
    {
        boolean rc = true;
        // Used to check the PDS answer is No
        String result = (String) pdsAndQ.get(key);
        if(result != null )
        {
            if(result.equals("N"))
            {
                rc = true;
            }
            else
            {
                rc = false;
            }
        }
        else
        {
            debugBuffer.append("<!-- Unable to find key = " + key + " -->" + NEWLINE);
            rc = false;
        }

        return rc;
    }

    private boolean isValue(String key, String value)
    {
        boolean rc;
        // Used to check the content of the answer to a specific question
        if(pdsAndQ.containsKey(key))
        {
            rc = value.equals(pdsAndQ.get(key));
        }
        else
        {
            debugBuffer.append("<!-- Unable to find key = " + key + " -->" + NEWLINE);
            rc = false;
        }

        return rc;
    }

    /**********************************************************************************
    * Initialize
    *
    */
    public void initSvcIntAnn()
    {
        // Setup all needed answer data in psdAndQ
		EntityGroup eg = list.getParentEntityGroup();
		log("starting initSvcIntAnn...");

		// Find the ANNOUNCEMENT entity.
		EntityItem ea = eg.getEntityItem(0);

		availVector = PokUtils.getAllLinkedEntities(eg, "ANNAVAILA", "AVAIL");
		sofV = getParentEntities(availVector, "SOFAVAIL", "SOF");
		debugBuffer.append("<!-- sofV.size() = " + sofV.size() + "-->" + NEWLINE);
		cmpntV = getParentEntities(availVector, "CMPNTAVAIL", "CMPNT");
		debugBuffer.append("<!-- cmpntV.size() = " + cmpntV.size() + "-->" + NEWLINE);
		featureV = getParentEntities(availVector, "FEATUREAVAIL", "FEATURE");
		debugBuffer.append("<!-- featureV.size() = " + featureV.size() + "-->" + NEWLINE);

		channelV1 = PokUtils.getAllLinkedEntities(sofV, "SOFCHANNEL", "CHANNEL");
		channelV2 = PokUtils.getAllLinkedEntities(cmpntV, "CMPNTCHANNEL", "CHANNEL");
		channelV3 = PokUtils.getAllLinkedEntities(featureV, "FEATURECHANNEL", "CHANNEL");

		derivePDSAnswers(ea);
		deriveQAnswers(ea);
		log("finishing initSvcIntAnn");
    }

    private void derivePDSAnswers(EntityItem ea)
    {
        // Derive answers to the following list of PDS questions needed for the Internal Announcement Letter:
        // P014 - A, B
        // P015 - A, B, C, D, E, F, G, H, I, J, K, L
        // P016 - A, B, C
        // P017 - A, B, C, D, E, F, G, H, I, J, K
        // P018 - A, B
        // P019 - A, B, C, D, E, F, G, H, I, J
        // P020 - A, B, C, D, E, F, G, H, I, J
        // P021 - A, B, C, D, E, F, G, H, I, J
        // P022 - A, B, C, D, E, F, G, H, I, J
        // P023 - A, B, C, D, E, F, G, H, I, J
        // P030 - A, B, C
        // P031 - A new for 19.04

        log("before processing PDS answers");
        // P014 - A, B
        deriveP014A();
        deriveP014B();
        // P015 - A, B, C, D, E, F, G, H, I, J, K, L
        deriveP015A();
        deriveP015B();
        deriveP015C();
        //P015D is removed from 19.04
        //deriveP015D();
        //P015E is removed from 19.04
        //deriveP015E();
        deriveP015F();
        deriveP015G();
        deriveP015HIJKL(ea);
        // P016 - B, C
        deriveP016B();
        deriveP016C();
        //deriveP014P015P016BC(ea);

        // P016A
        //20.10
        deriveP016A();

        //deriveP016A(ea);
        // P017 - A, B, C, D, E, F, G, H, I, J, K, L
        deriveP017(ea);
        // P018 - A, B
        deriveP018(ea);
        // P019 - A, B, C, D, E, F, G, H, I, J
        // P020 - A, B, C, D, E, F, G, H, I, J
        // P021 - A, B, C, D, E, F, G, H, I, J
        // P022 - A, B, C, D, E, F, G, H, I, J
        // P023 - A, B, C, D, E, F, G, H, I, J
        deriveConfiguratorAnswers(ea);
        // P030 - A, B, C
        deriveP030(ea);
        deriveP031A();
        log("end processing PDS answers");
    }

    //20.10
    private void deriveP016A()
    {
        pdsAndQ.put("P016A", "N");
    }

//    private void deriveP016A(EntityItem ea)
//    {
        // Derive answers to the P016A question for the Internal Announcement Letter

        // Find all ORGANUNIT entities associated with this ANNOUNCEMENT
//        Vector orgVct = PokUtils.getAllLinkedEntities(ea, "ANNORGANUNIT", "ORGANUNIT");

        // Process all ORGANUNIT entities found
//        Iterator orgItr = orgVct.iterator();
//        while(orgItr.hasNext())
//        {
//            EntityItem org = (EntityItem)orgItr.next();
//            if(PokUtils.isSelected(org, "ORGANUNITTYPE", SVConstants.XR_ORGANUNITTYPE_4156))
//            {
//                pdsAndQ.put("P016A", "Y");
//                return;
//            }
//        }
//        orgVct.clear();
//        pdsAndQ.put("P016A", "N");
//    }

    private void deriveP017(EntityItem ea)
    {
        // Derive answers to the following list of P017 questions for the Internal Announcement Letter:
        // P017 - A, B, C, D, E, F, G, H, I, J, K

        if(PokUtils.isSelected(ea, "CROSSPLATFORM", SVConstants.XR_CROSSPLATFORM_822))
        {
            pdsAndQ.put("P017A", "Y");
        }
        else
        {
            pdsAndQ.put("P017A", "N");
        }
        if(PokUtils.isSelected(ea, "PLATFORM", SVConstants.XR_PLATFORM_4767))
        {
            pdsAndQ.put("P017B", "Y");
        }
        else
        {
            pdsAndQ.put("P017B", "N");
        }
        if(PokUtils.isSelected(ea, "PLATFORM", SVConstants.XR_PLATFORM_4770))
        {
            pdsAndQ.put("P017C", "Y");
        }
        else
        {
            pdsAndQ.put("P017C", "N");
        }
        if(PokUtils.isSelected(ea, "PLATFORM", SVConstants.XR_PLATFORM_4769))
        {
            pdsAndQ.put("P017D", "Y");
        }
        else
        {
            pdsAndQ.put("P017D", "N");
        }
        if(PokUtils.isSelected(ea, "PLATFORM", SVConstants.XR_PLATFORM_4772))
        {
            pdsAndQ.put("P017E", "Y");
        }
        else
        {
            pdsAndQ.put("P017E", "N");
        }
        if(PokUtils.isSelected(ea, "PLATFORM", SVConstants.XR_PLATFORM_4764))
        {
            pdsAndQ.put("P017F", "Y");
        }
        else
        {
            pdsAndQ.put("P017F", "N");
        }
        if(PokUtils.isSelected(ea, "PLATFORM", SVConstants.XR_PLATFORM_4768))
        {
            pdsAndQ.put("P017G", "Y");
        }
        else
        {
            pdsAndQ.put("P017G", "N");
        }
        if(PokUtils.isSelected(ea, "PLATFORM", SVConstants.XR_PLATFORM_4766))
        {
            pdsAndQ.put("P017H", "Y");
        }
        else
        {
            pdsAndQ.put("P017H", "N");
        }
        if(PokUtils.isSelected(ea, "PLATFORM", SVConstants.XR_PLATFORM_4773))
        {
            pdsAndQ.put("P017I", "Y");
        }
        else
        {
            pdsAndQ.put("P017I", "N");
        }
        if(PokUtils.isSelected(ea, "OFFERINGTYPES", SVConstants.XR_OFFERINGTYPES_2907))
        {
            pdsAndQ.put("P017J", "Y");
        }
        else
        {
            pdsAndQ.put("P017J", "N");
        }
        if(PokUtils.isSelected(ea, "OFFERINGTYPES", SVConstants.XR_OFFERINGTYPES_2913))
        {
            pdsAndQ.put("P017K", "Y");
        }
        else
        {
            pdsAndQ.put("P017K", "N");
        }
    }

    private void deriveP018(EntityItem ea)
    {
        // Derive answers to the following list of P018 questions for the Internal Announcement Letter:
        // P018 - A, B

        if(PokUtils.isSelected(ea, "CONFIGSUPPORT", SVConstants.XR_CONFIGSUPPORT_677))
        {
            pdsAndQ.put("P018A", "Y");
        }
        else
        {
            pdsAndQ.put("P018A", "N");
        }
        if(PokUtils.isSelected(ea, "CONFIGSUPPORT", SVConstants.XR_CONFIGSUPPORT_675))
        {
            pdsAndQ.put("P018B", "Y");
        }
        else
        {
            pdsAndQ.put("P018B", "N");
        }
    }
    private void deriveConfiguratorAnswers(EntityItem ea)
    {
        // Derive answers to the five PDS Configurator questions for the Internal Announcement Letter
        // P19 (A-Z) - P23 (A-Z)

        String configAvail = new String();
        String configName  = new String();
        String [] nameArray = {"A", "C", "E", "G", "I"};
        String [] availArray = {"B", "D", "F", "H", "J"};
        int    indexP019   = 0;
        int    indexP020   = 0;
        int    indexP021   = 0;
        int    indexP022   = 0;
        int    indexP023   = 0;

        // Find all CONFIGURATOR entities associated with this ANNOUNCEMENT
        // Find relator ANNTOCONFIG
        for(int ui = 0; ui < ea.getDownLinkCount(); ui++)
        {
            EANEntity el = ea.getDownLink(ui);
            if(el.getEntityType().equals("ANNTOCONFIG"))
            {
                configAvail = PokUtils.getAttributeValue((EntityItem) el, "CONFIGAVAILDATE", "", "YYYY-MM-DD", false);
                // Process all CONFIGURATOR entities related to the ANNTOCONFIG
                for(int ui2 = 0; ui2 < el.getDownLinkCount(); ui2++)
                {
                    EANEntity conf = el.getDownLink(ui2);
                    if(conf.getEntityType().equals("CONFIGURATOR"))
                    {
                        configName = PokUtils.getAttributeValue((EntityItem) conf, "CONFIGNAME", "", "&nbsp;", false);
                        // Process RFA Geo questions
                        if(checkRfaGeoUS((EntityItem) conf))
                        {
                            // For P019
                            pdsAndQ.put("P019" + nameArray[indexP019], configName);
                            pdsAndQ.put("P019" + availArray[indexP019], configAvail);
                            indexP019++;
                        }
                        if(checkRfaGeoAP((EntityItem) conf))
                        {
                            // For P020
                            pdsAndQ.put("P020" + nameArray[indexP020], configName);
                            pdsAndQ.put("P020" + availArray[indexP020], configAvail);
                            indexP020++;
                        }
                        if(checkRfaGeoLA((EntityItem) conf))
                        {
                            // For P021
                            pdsAndQ.put("P021" + nameArray[indexP021], configName);
                            pdsAndQ.put("P021" + availArray[indexP021], configAvail);
                            indexP021++;
                        }
                        if(checkRfaGeoCAN((EntityItem) conf))
                        {
                            // For P022
                            pdsAndQ.put("P022" + nameArray[indexP022], configName);
                            pdsAndQ.put("P022" + availArray[indexP022], configAvail);
                            indexP022++;
                        }
                        if(checkRfaGeoEMEA((EntityItem) conf))
                        {
                            // For P023
                            pdsAndQ.put("P023" + nameArray[indexP023], configName);
                            pdsAndQ.put("P023" + availArray[indexP023], configAvail);
                            indexP023++;
                        }
                    }//end of if(conf.getEntityType().equals("CONFIGURATOR"))
                }//end of for(int ui2 = 0; ui2 < el.getDownLinkCount(); ui2++)
            }//end of if(el.getEntityType().equals("ANNTOCONFIG"))
        }//end of for(int ui = 0; ui < ea.getDownLinkCount(); ui++)
    }

    private void deriveP030(EntityItem ea)
    {
        // Derive answers to the following list of P030 questions for the Internal Announcement Letter:
        // P030 - A, B, C

        if(PokUtils.isSelected(ea, "OFFERINGACCESS", SVConstants.XR_OFFERINGACCESS_2889))
        {
            pdsAndQ.put("P030A", "Y");
        }
        else
        {
            pdsAndQ.put("P030A", "N");
        }
        if(PokUtils.isSelected(ea, "MARKETEDIBMLOGO", SVConstants.XR_MARKETEDIBMLOGO_2840))
        {
            pdsAndQ.put("P030B", "Y");
        }
        else
        {
            pdsAndQ.put("P030B", "N");
        }
        if(PokUtils.isSelected(ea, "LOGOACCESSREQTS", SVConstants.XR_LOGOACCESSREQTS_2833))
        {
            pdsAndQ.put("P030C", "Y");
        }
        else
        {
            pdsAndQ.put("P030C", "N");
        }
    }

    private void deriveQAnswers(EntityItem ea)
    {
        //A0200 is renumbered to A0202 per RFA Guide 21.04
        // Derive answers to the following list of Q&A questions for the Internal Announcement Letter:
        // A0100 to A0171
        // A0200, A0208
        // NOTE: Applicable question logic must be applied before deriving the answer.

        setFlags(ea);

        log("deriveQAnswer - before A0100");
        // Question logic is "Always Applies" and the answer is "Required".
        setQAnswer(ea, "A0100", "ANNTITLE");
        log("deriveQAnswer - before A0102");
        // Question logic is "Always Applies".
        setQAnswer(ea, "A0102", "ONESENTDESC");
        log("deriveQAnswer - before A0104");
        // Question logic is "Always Applies".
        deriveAnswer(ea, "A0104", "FEATUREBENEFIT");
        log("deriveQAnswer - before A0106");
        // Question logic is "Always Applies".
        deriveAnswer(ea, "A0106", "DIFFEATURESBENEFITS");

        //20.04
        log("deriveQAnswer - before A0105");
        if(isNo("P015H") && isNo("P015I") && isNo("P015J"))
        {
            // Put null string when true
            pdsAndQ.put("A0105", new String());
        }
        else
        {
            // A0105 - US, LA, CAN and the answer is "Required".
            deriveA0105(ea);
        }

        log("deriveQAnswer - before A0110");
        if(isNo("P015K"))
        {
            // Put null string when true
            pdsAndQ.put("A0110", new String());
        }
        else
        {
            // A0110 - EMEA only and the answer is "Required".
            deriveA0110(ea);
        }
        log("deriveQAnswer - before A0116");
        // Question logic is "Always Applies".
        deriveA0116(ea);
        log("deriveQAnswer - before A0118");
        // Question logic is "Always Applies".
        deriveAnswer(ea, "A0118", "MKTGSTRATEGY");

        log("deriveQAnswer - before A0119");
        if(isNo("P015H") && isNo("P015J"))
        {
            pdsAndQ.put("A0119", new String());
        }
        else
        {
            // A0119 - US and CAN only
            deriveA0119_120("A0119");
        }
        log("deriveQAnswer - before A0120");
        if(isNo("P015H") && isNo("P015J"))
        {
            pdsAndQ.put("A0120", new String());
        }
        else
        {
            // A0120 - US and CAN only
            deriveA0119_120("A0120");
        }
        log("deriveQAnswer - before A0122");
        // Question logic is "Always Applies".
        deriveAnswer(ea, "A0122", "CUSTWANTSNEEDS");

        //20.10
        log("deriveQAnswer - before A0123");
        // Question logic is "Always Applies".
        deriveAnswer(ea, "A0123", "CUSTPAINPT");

        log("deriveQAnswer - before A0124");
        // Question logic is "Always Applies".
        deriveAnswer(ea, "A0124", "RESOURSKILLSET");
        // Question logic is "Always Applies".
        log("deriveQAnswer - before A0126");
        //Begin of TIR ID # - USRO-R-JMGR-5ZCNYK
        deriveA0126();
        //End of TIR ID # - USRO-R-JMGR-5ZCNYK
        // Question logic is "Always Applies".
        deriveAnswer(ea, "A0128", "OTHERMKTGINFO");
        log("deriveQAnswer - before A0130");
        // Question logic is "Always Applies".
        deriveA0130();
        // Question logic is "Always Applies".
        deriveAnswer(ea, "A0132", "SALESACTREQ");
        // Question logic is "Always Applies".
        deriveAnswer(ea, "A0134", "SALESAPPROACH");
        // Question logic is "Always Applies".
        deriveAnswer(ea, "A0136", "CUSTCANDGUIDELINES");
        // Question logic is "Always Applies".
        deriveAnswer(ea, "A0138", "CUSTRESTRICTIONS");
        log("deriveQAnswer - before A0140");
        // Question logic is "Always Applies".
        deriveAnswer(ea, "A0140", "HANDOBJECTIONS");
        // Question 142 removed by RFA Guide 18.09
        // Question logic is "Always Applies".
        // deriveAnswer(ea, "A0142", "CONFPACKOPTIONS");
        // Question logic is "Always Applies".
        deriveAnswer(ea, "A0144", "COMPETITIVEOF");
        // Question logic is "Always Applies".
        deriveAnswer(ea, "A0146", "STRENGTHWEAKNESS");
        log("deriveQAnswer - before A0148");
        // Question logic is "Always Applies" and the answer is "Required".
        deriveA0148(ea);
        log("deriveQAnswer - before A0150");
        // Question logic is "Always Applies".
        deriveSATAnswer(ea, "A0150", SVConstants.XR_STANDARDAMENDTEXT_TYPE_110);

        if(isNo("P015K"))
        {
            // Put null string when true
            pdsAndQ.put("A0151", new String());
        }
        else
        {
            // A0151 - EMEA only and the answer is "Required".
            deriveA0151_152(ea, "A0151");
        }

        //A0152 is removed in 20.10
        //if(isNo("P015K"))
        //{
            // Put null string when true
        //    pdsAndQ.put("A0152", new String());
        //}
        //else
        //{
            // A0152 - EMEA only
        //    deriveA0151_152(ea, "A0152");
        //}
        // Question logic is "Always Applies" and the answer is "Required".
        // A0153 is removed from 19.04. A0154 is renamed to A0153 in 19.04
        //deriveA0153(ea);
        // A0153 is removed from 19.04. A0154 is renamed to A0153 in 19.04
        // Question logic is "Always Applies" and the answer is "Required".
        //deriveA0154(ea);
        deriveA0153(ea);
        // Question logic is "Always Applies" and the answer is "Required".
        // A0155 is Renumbered to 154 in 19.10
        deriveA0154(ea);
        // A0155 is new in 19.10
        // Question logic is "Always Applies" and the answer is "Optional".
        deriveA0155(ea);
        if(isYes("P016A") || isNo("P015K"))
        {
            pdsAndQ.put("A0156", new String());
        }
        else
        {
            // A0156 - EMEA only and the answer is "Required".
            deriveSATAnswer(ea, "A0156", SVConstants.XR_STANDARDAMENDTEXT_TYPE_120);
        }
        if(isNo("P015H") && isNo("P015J"))
        {
            pdsAndQ.put("A0157", new String());
        }
        else
        {
            // A0157 - US and CAN only and the answer is "Required".
            deriveA0157(ea);
        }
        if((isNo("P015H") && isNo("P015J")) || !isValue("A0157", "Y"))
        {
            pdsAndQ.put("A0158", new String());
        }
        else
        {
            // A0158 - US and CAN only
            deriveA0158(ea);
        }
        log("deriveQAnswer - before A0160");
        if(isNo("P030A") || isNo("P030B"))
        {
            pdsAndQ.put("A0160", new String());
        }
        else
        {
            setQAnswer(ea, "A0160", "ACCESPEOWDISABLE");
        }
        if(isNo("P030A") || isNo("P030B"))
        {
            pdsAndQ.put("A0161", new String());
        }
        else
        {
            setQAnswer(ea, "A0161", "ACCESPEOWDISABLECONSID");
        }
        if(isNo("P030A") ||
            isNo("P015H") ||
            isNo("P030B") ||
            isNo("P030C"))
        {
            pdsAndQ.put("A0162", new String());
        }
        else
        {
            // A0162 - US only and the answer is "Required".
            setQAnswer(ea, "A0162", "USSEC508");
        }
        if(isNo("P030A") ||
            isNo("P015H") ||
            isYes("P030B"))
        {
            pdsAndQ.put("A0163", new String());
        }
        else
        {
            // A0163 - US only and the answer is "Required".
            setQAnswer(ea, "A0163", "USSEC508LOGO");
        }
        log("deriveQAnswer - before A0170");
        // Question logic is "Always Applies" and the answer is "Required".
        deriveA0170(ea);
        // Question logic is "Always Applies".
        // A0171 - US only - Set to null based on Alan Crudo's RFA 18.03 Service Mapping spreadsheet
        // 6/2/2004 Even A0171 is not part of RFA Service Guide for e-announce 1.2i, 1.3i but Mike Slocum still puts
        // an empty table in the A-file for A0171 therefore ScvIntAnn.java needs to show an empty table to
        // match the A-file
        deriveA0171();
        //pdsAndQ.put("A0171", new String());
        // Question logic is "Always Applies" and the answer is "Required". - new for RFA Guide 18.09.
        deriveA0181(ea);
        // New for RFA Guide 18.09.
        if(!isValue("A0181", "Y"))
        {
            pdsAndQ.put("A0182", new String());
        }
        else
        {
            deriveIFGAnswer(ea, "A0182", "MKTGMSGINTERNAL");
        }
        // New for RFA Guide 18.09.
        if(!isValue("A0181", "Y"))
        {
            pdsAndQ.put("A0183", new String());
        }
        else
        {
            deriveIFGAnswer(ea, "A0183", "MKTGMESEXTERNAL");
        }
        // New for RFA Guide 18.09.
        if(!isValue("A0181", "Y"))
        {
            pdsAndQ.put("A0184", new String());
        }
        else
        {
            //  The answer is "Required".
            deriveA0184_A0186(ea, "A0184", "PROMOELIGIBILITYTCS");
        }
        // New for RFA Guide 18.09.
        if(!isValue("A0181", "Y") ||
            !isValue("A0184", "Y"))
        {
            pdsAndQ.put("A0185", new String());
        }
        else
        {
            deriveIFGAnswer(ea, "A0185", "PROMOELIGIBILITYTCS");
        }
        // New for RFA Guide 18.09.
        if(!isValue("A0181", "Y") || !isValue("A0184", "Y"))
        {
            pdsAndQ.put("A0186", new String());
        }
        else
        {
            //  The answer is "Required".
            deriveA0184_A0186(ea, "A0186", "MONTHLYPAYMENT");
        }
        // New for RFA Guide 18.09.
        if(!isValue("A0181", "Y") ||
            !isValue("A0184", "Y") ||
            !isValue("A0186", "Y"))
        {
            pdsAndQ.put("A0187", new String());
        }
        else
        {
            deriveIFGAnswer(ea, "A0187", "MONTHLYPAYMENT");
        }
        // New for RFA Guide 18.09.
        if(!isValue("A0181", "Y") ||
            !isValue("A0184", "Y") ||
            !isValue("A0186", "Y"))
        {
            pdsAndQ.put("A0188", new String());
        }
        else
        {
            deriveIFGAnswer(ea, "A0188", "PAYMENTTERM");
        }
        // New for RFA Guide 18.09.
        if(!isValue("A0181", "Y") ||
            !isValue("A0184", "Y") ||
            !isValue("A0186", "Y"))
        {
            pdsAndQ.put("A0189", new String());
        }
        else
        {
            deriveIFGAnswer(ea, "A0189", "ELIGIBILITYTCS");
        }
        // New for RFA Guide 18.09.
        //A0190 is removed from 19.04.
        //if (!isValue("A0181", "Y")) {
        //   pdsAndQ.put("A0190", new String());
        //}
        //else {
        //   deriveIFGAnswer(ea, "A0190", "INFOURLS");
        //}
        //log("deriveQAnswer - before A0200");
        // A0200 - the answer is "Required".
        //deriveAnswer(ea, "A0200", "OVERVIEWABSTRACT");
        //A0200 is renumbered to A0202 per RFA Guide 21.04
        log("deriveQAnswer - before A0202");
        // A0202 - the answer is "Required".
        deriveAnswer(ea, "A0202", "OVERVIEWABSTRACT");

        log("deriveQAnswer - before A0208");
        // A0208 - the answer is "Required".
        deriveA0208(ea);

        // A0195 is new in 20.04
        log("deriveQAnswer - before A0195");
        // Question logic is "Always Applies".
        deriveSATAnswer(ea, "A0195", "200");

        log("finish A0195 - exit deriveQAnswer");
    }
    private void setFlags(EntityItem ea)
    {
        Vector sofVector;
        Vector cmpntVector;
        Vector featureVector;

        // Determine if header should be included.
        int size = 0;

        // Find all AVAIL entities associated with this ANNOUNCEMENT
        Vector avVct = PokUtils.getAllLinkedEntities(ea, "ANNAVAILA", "AVAIL");

        // Process all AVAIL entities found
        Iterator avItr = avVct.iterator();
        while(avItr.hasNext())
        {
            EntityItem av = (EntityItem)avItr.next();
            // Find all SOF entities related to the AVAIL entity.
            sofVector = PokUtils.getAllLinkedEntities(av, "SOFAVAIL", "SOF");
            size += sofVector.size();
            sofVector.clear();

            // Find all CMPNT entities related to the AVAIL entity.
            cmpntVector = PokUtils.getAllLinkedEntities(av, "CMPNTAVAIL", "CMPNT");
            size += cmpntVector.size();
            cmpntVector.clear();

            // Find all FEATURE entities related to the AVAIL entity.
            featureVector = PokUtils.getAllLinkedEntities(av, "FEATUREAVAIL", "FEATURE");
            size += featureVector.size();

            featureVector.clear();
        }
        avVct.clear();

        if(size == 1)
        {
            includeHdr = false;
        }
        else
        {
            includeHdr = true;
        }
        // Set indicator for Announcement(LOB)="ITS"
        annLobITS = PokUtils.isSelected(ea, "LOB", SVConstants.XR_LOB_101);
    }

    private void setQAnswer(EntityItem ea, String aa, String attr)
    {
        //  Set answer to the specified question for the Internal Announcement Letter.

        pdsAndQ.put(aa, PokUtils.getAttributeValue(ea, attr, "", "", false));
    }

    private void deriveAnswer(EntityItem ea, String aa, String attr)
    {
        //A0200 is renumbered to A0202 per RFA Guide 21.04
        // Derive one answer to the following question for the Internal Announcement Letter:
        // A0104, A0106, A0118, A0122, A0123, A0124, A0128, A0132, A0134, A0136, A0138, A0140

        StringBuffer sb = new StringBuffer();
        TreeMap sofTm = new TreeMap();
        TreeMap cmpntTm = new TreeMap();
        TreeMap featureTm = new TreeMap();
        TreeMap sofGeoTm = new TreeMap();
        TreeMap cmpntGeoTm = new TreeMap();
        TreeMap featureGeoTm = new TreeMap();
        String mktgName = new String();
        String geoMap = new String();
        String attrStr = new String();
        Vector avVct;
        Iterator avItr;

        if(aa.equals("A0202"))
        {
            sb.append(PokUtils.getAttributeValue(ea, attr, "", "", false));
        }
        // Find all AVAIL entities associated with this ANNOUNCEMENT
        avVct = PokUtils.getAllLinkedEntities(ea, "ANNAVAILA", "AVAIL");
        // debugBuffer.append("<!-- total avail vector size = " + avVct.size() + " -->" + NEWLINE);
        // Process all AVAIL entities found
        avItr = avVct.iterator();
        while(avItr.hasNext())
        {
            Vector sofVector;
            Iterator sofItr;
            Vector cmpntVector;
            Iterator cmpntItr;
            Vector featureVector;
            Iterator featureItr;

            EntityItem av = (EntityItem) avItr.next();
            // Retrieve Geo information from the AVAIL entity
            geoMap = processGeoInfo(av);
            // Find all SOF entities related to the AVAIL entity.
            sofVector = PokUtils.getAllLinkedEntities(av, "SOFAVAIL", "SOF");

            // Process all SOF entities found
            sofItr = sofVector.iterator();
            while(sofItr.hasNext())
            {
                // get next SOF entity
                EntityItem sof = (EntityItem)sofItr.next();
                mktgName = PokUtils.getAttributeValue(sof, "MKTGNAME", "", "No user input found", false);
                attrStr = PokUtils.getAttributeValue(sof, attr, "", "", false);
                if(attrStr != null && attrStr.length() > 0)
                {
                    sofTm.put(mktgName, attrStr);
                    addGeoToTm(mktgName, geoMap, sofGeoTm);
                }
            }
            sofVector.clear();
            // Find all CMPNT entities related to the AVAIL entity.
            cmpntVector = PokUtils.getAllLinkedEntities(av, "CMPNTAVAIL", "CMPNT");

            // Process all CMPNT entities found
            cmpntItr = cmpntVector.iterator();
            while(cmpntItr.hasNext())
            {
                // get next CMPNT entity
                EntityItem cmpnt = (EntityItem)cmpntItr.next();
                mktgName = getSofMktgName(cmpnt) + " " +
                    PokUtils.getAttributeValue(cmpnt, "MKTGNAME", "", "No user input found", false);
                attrStr = PokUtils.getAttributeValue(cmpnt, attr, "", "", false);
                if(attrStr != null && attrStr.length() > 0)
                {
                    cmpntTm.put(mktgName, attrStr);
                    addGeoToTm(mktgName, geoMap, cmpntGeoTm);
                }
            }
            cmpntVector.clear();
            // Find all FEATURE entities related to the AVAIL entity.
            featureVector = PokUtils.getAllLinkedEntities(av, "FEATUREAVAIL", "FEATURE");

            // Process all FEATURE entities found
            featureItr = featureVector.iterator();
            while(featureItr.hasNext())
            {
                // get next FEATURE entity
                EntityItem feature = (EntityItem)featureItr.next();
                mktgName = getCmpntMktgName(feature) + " " +
                    PokUtils.getAttributeValue(feature, "MKTGNAME", "", "No user input found", false);
                attrStr = PokUtils.getAttributeValue(feature, attr, "", "", false);
                if(attrStr != null && attrStr.length() > 0)
                {
                    featureTm.put(mktgName, attrStr);
                    addGeoToTm(mktgName, geoMap, featureGeoTm);
                }
            }
            featureVector.clear();
        }
        avVct.clear();
        // log("deriveAnswer for " + aa + " attribute " + attr + " - before formatting answer ");

        sb.append(formatAnswer(sofTm, sofGeoTm) + formatAnswer(cmpntTm, cmpntGeoTm) + formatAnswer(featureTm, featureGeoTm));

        pdsAndQ.put(aa, sb.toString());
        // log("deriveAnswer for " + aa + " attribute " + attr + " - after formatting answer ");
        sofTm.clear();
        cmpntTm.clear();
        featureTm.clear();
        sofGeoTm.clear();
        cmpntGeoTm.clear();
        featureGeoTm.clear();
    }

    private String processGeoInfo(EntityItem availEntity)
    {
        Byte geoByte;
        // Retrieve and map the country list in the AVAIL entity to RFA geo mapping
        byte geoMap = SvcIntAnn.RFA_NO_GEO;

        // Use Middleware API here to coombine the geo info
        if(checkRfaGeoAP(availEntity))
        {
            geoMap |= SvcIntAnn.RFA_AP;
        }
        if(checkRfaGeoCAN(availEntity))
        {
            geoMap |= SvcIntAnn.RFA_CAN;
        }
        if(checkRfaGeoEMEA(availEntity))
        {
            geoMap |= SvcIntAnn.RFA_EMEA;
        }
        if(checkRfaGeoLA(availEntity))
        {
            geoMap |= SvcIntAnn.RFA_LA;
        }
        if(checkRfaGeoUS(availEntity))
        {
            geoMap |= SvcIntAnn.RFA_US;
        }

        geoByte = new Byte(geoMap);
        return geoByte.toString();
    }

    private void addGeoToTm(String key, String geoMap, TreeMap queue)
    {
        // Accumulate the Geo information for the specific key
        if( queue.size() == 0 || !(queue.containsKey(key)))
        {
            queue.put( key, geoMap);
        }
        else
        {
            Byte resultGeoByte;
            String foundGeoStr = (String) queue.get(key);
            byte foundGeoMap = (new Byte(foundGeoStr)).byteValue();
            byte currGeo = (new Byte(geoMap)).byteValue();

            currGeo |= foundGeoMap;
            resultGeoByte = new Byte(currGeo);
            queue.put( key, resultGeoByte.toString());
        }
    }

    private String formatAnswer(TreeMap tm, TreeMap geoTm)
    {
        //  Set answer to the specified question for the Internal Announcement Letter.
        StringBuffer sb = new StringBuffer();

        if(tm.size() > 0)
        {
            // Process data saved in queue
            byte prevGeo = SvcIntAnn.RFA_NOT_INIT;
            byte currGeo = SvcIntAnn.RFA_NO_GEO;
            Set keySet = tm.keySet();
            Iterator i = keySet.iterator();
            while(i.hasNext())
            {
                String keyStr = (String) i.next();
                // Get RFA Geo tag
                String geoString = (String) geoTm.get(keyStr);
                // Get the value (should be already in XML format)from the queue
                String valueStr = (String) tm.get(keyStr);
                Byte geoByte = new Byte(geoString);
                currGeo = geoByte.byteValue();
                setGeoTags(prevGeo, currGeo, sb);
                if(includeHdr)
                {
                    sb.append("<br /><ul><li><b>" + keyStr + "</b></li><br />" + valueStr + "</ul>");
                }
                else
                {
                    sb.append("<br />" + valueStr);
                }
                prevGeo = currGeo;
            }
            if(!(currGeo == SvcIntAnn.RFA_WW))
            {
                bldEndGeoTags(currGeo, sb);
            }
            keySet.clear();
        }
        return (sb.toString());
    }

    private void setGeoTags(byte prevGeo, byte currGeo, StringBuffer sb)
    {
        if(prevGeo == SvcIntAnn.RFA_NOT_INIT)
        {
            if(currGeo != SvcIntAnn.RFA_WW)
            {
                bldBgnGeoTags(currGeo, sb);
            }
        }
        else
        {
            if(prevGeo != currGeo)
            {
                if(prevGeo != SvcIntAnn.RFA_WW)
                {
                    bldEndGeoTags(prevGeo, sb);
                }
                if(currGeo != SvcIntAnn.RFA_WW)
                {
                    bldBgnGeoTags(currGeo, sb);
                }
            }
        }
    }

    private void bldBgnGeoTags(byte gm, StringBuffer sb)
    {
        sb.append("<b><br />");
        fmtGeoString(gm, sb);
        sb.append("---&gt;</b>");
    }

    private void bldEndGeoTags(byte gm, StringBuffer sb)
    {
        // Build end geo tag

        sb.append("<b><br />&lt;---");
        fmtGeoString(gm, sb);
        sb.append("</b>" + NEWLINE);
    }

    private void fmtGeoString(byte geoMap, StringBuffer sb)
    {
        boolean firstGeo = true;
        boolean rfaUS = false;
        boolean rfaAP = false;
        boolean rfaLA = false;
        boolean rfaCAN = false;
        boolean rfaEMEA = false;

        if((geoMap & SvcIntAnn.RFA_US) == SvcIntAnn.RFA_US)
        {
            sb.append("US");
            firstGeo = false;
            rfaUS = true;
        }

        if((geoMap & SvcIntAnn.RFA_AP) == SvcIntAnn.RFA_AP)
        {
            if(firstGeo)
            {
                sb.append("AP");
                firstGeo = false;
            }
            else
            {
                sb.append(", AP");
            }
            rfaAP = true;
        }

        if((geoMap & SvcIntAnn.RFA_LA) == SvcIntAnn.RFA_LA)
        {
            if(firstGeo)
            {
                sb.append("LA");
                firstGeo = false;
            }
            else
            {
                sb.append(", LA");
            }
            rfaLA = true;
        }

        if((geoMap & SvcIntAnn.RFA_CAN) == SvcIntAnn.RFA_CAN)
        {
            if(firstGeo)
            {
                sb.append("CAN");
                firstGeo = false;
            }
            else
            {
                sb.append(", CAN");
            }
            rfaCAN = true;
        }

        if((geoMap & SvcIntAnn.RFA_EMEA) == SvcIntAnn.RFA_EMEA)
        {
            if(firstGeo)
            {
                sb.append("EMEA");
            }
            else
            {
                sb.append(", EMEA");
            }
            rfaEMEA = true;
        }
        if(!(rfaUS || rfaAP || rfaLA || rfaCAN || rfaEMEA))
        {
            sb.append("*** No RFA GEO Information found ***");
        }
    }

    private String getSofMktgName(EntityItem cmpnt)
    {
        String result = "";
        boolean done = false;
        // Return the ITSCMPNTCATNAME value if Announcement(LOB) = "ITS"
        // and ITSCMPNTCATNAME value exists.  If not, then find the
        // MKTGNAME value for the parent SOF of the input CMPNT entity

        if(annLobITS)
        {
            String catname = PokUtils.getAttributeValue(cmpnt, "ITSCMPNTCATNAME", "", "", false);
            if(catname != null & catname.length() > 0)
            {
                result = catname;
            }
        }

        if(result.length() == 0)
        {
            for(int ui = 0; ui < cmpnt.getUpLinkCount(); ui++)
            {
                if(false == done)
                {
                    EANEntity rcmpnt = cmpnt.getUpLink(ui);
                    if(rcmpnt.getEntityType().equals("SOFCMPNT"))
                    {
                        for(int si = 0; si < rcmpnt.getUpLinkCount(); si++)
                        {
                            EANEntity ei = rcmpnt.getUpLink(si);
                            if(ei.getEntityType().equals("SOF"))
                            {
                                String mktgname = PokUtils.getAttributeValue((EntityItem) ei, "MKTGNAME", "", "No user input found for SOF MKTGNAME", false);
                                result =  mktgname;
                                done = true;
                                break;
                            }
                        }//end of for(int si = 0; si < rcmpnt.getUpLinkCount(); si++)
                    }//end of if(rcmpnt.getEntityType().equals("SOFCMPNT"))
                }//end of if(false == done)
                else
                {
                    break;
                }
            }//end of for(int ui = 0; ui < cmpnt.getUpLinkCount(); ui++)
        }//end of if(result.length() == 0)

        if(result.length() == 0)
        {
            result = "No parent SOF found";
        }

        return result;
    }

    private String getCmpntMktgName(EntityItem feature)
    {
        String result = "";
        boolean done = false;
        // Find the MKTGNAME value for the parent SOF of the input CMPNT entity

        for(int ui = 0; ui < feature.getUpLinkCount(); ui++)
        {
            if(false == done)
            {
                EANEntity rfeature = feature.getUpLink(ui);
                if(rfeature.getEntityType().equals("CMPNTFEATURE"))
                {
                    for(int si = 0; si < rfeature.getUpLinkCount(); si++)
                    {
                        EANEntity ei = rfeature.getUpLink(si);
                        if(ei.getEntityType().equals("CMPNT"))
                        {
                            String mktgname = getSofMktgName((EntityItem) ei) + " " +
                                PokUtils.getAttributeValue((EntityItem) ei, "MKTGNAME", "", "No user input found for CMPNT MKTGNAME", false);
                            result = mktgname;
                            done = true;
                            break;
                        }
                    }//end of for(int si = 0; si < rfeature.getUpLinkCount(); si++)
                }//end of if(rfeature.getEntityType().equals("CMPNTFEATURE"))
            }//end of if(false == done)
            else
            {
                break;
            }
        }//end of for(int ui = 0; ui < feature.getUpLinkCount(); ui++)

        if(result.length() == 0)
        {
            result = "No parent CMPNT found";
        }

        return result;
    }

    //20.04
    private void deriveA0105(EntityItem ea)
    {
        // Derive answers to the A0105 question for the Internal Announcement Letter
        String chnName  = new String();
        String mktgName  = new String();
        StringBuffer sb = new StringBuffer();
        TreeMap sofTm = new TreeMap();
        TreeMap cmpntTm = new TreeMap();
        TreeMap featureTm = new TreeMap();

        // Find all AVAIL entities associated with this ANNOUNCEMENT
        Vector avVct = PokUtils.getAllLinkedEntities(ea, "ANNAVAILA", "AVAIL");

        // Process all AVAIL entities found
        Iterator avItr = avVct.iterator();
        while(avItr.hasNext())
        {
            EntityItem av = (EntityItem)avItr.next();
            if(checkRfaGeoUS(av) || checkRfaGeoLA(av) || checkRfaGeoCAN(av))
            {
                Vector sofVector;
                Iterator sofItr;
                Vector cmpntVector;
                Iterator cmpntItr;
                Vector featureVector;
                Iterator featureItr;

                // Find all SOF entities related to the AVAIL entity.
                sofVector = PokUtils.getAllLinkedEntities(av, "SOFAVAIL", "SOF");
                // Process all SOF entities found
                sofItr = sofVector.iterator();
                while(sofItr.hasNext())
                {
                    // get next SOF entity
                    EntityItem sof = (EntityItem)sofItr.next();
                    // Find all CHANNEL entities related to the SOF entity.
                    Vector chnVector = PokUtils.getAllLinkedEntities(sof, "SOFCHANNEL", "CHANNEL");
                    Iterator chnItr = chnVector.iterator();
                    while(chnItr.hasNext())
                    {
                        // get next CHANNEL entity
                        EntityItem chn = (EntityItem) chnItr.next();
                        mktgName = PokUtils.getAttributeValue(sof, "MKTGNAME", "", "No user input found", false);
                        chnName = PokUtils.getAttributeValue(chn, "CHANNELNAME", ",", " ", false);
                        if((checkRfaGeoUS(chn) || checkRfaGeoLA(chn) || checkRfaGeoCAN(chn))  && chnName != null && chnName.length()>0 && PokUtils.isSelected(chn, "ROUTESTOMKTG", "110"))
                        {
                            if(sofTm.size() == 0)
                            {
                                HashSet sofHs1 = new HashSet();
                                sofHs1.add(chnName);
                                sofTm.put(mktgName, sofHs1);
                            }
                            else
                            {
                                HashSet sofValueSet = (HashSet) sofTm.get(mktgName);
                                if(sofValueSet != null)
                                {
                                    sofTm.remove(mktgName);
                                    sofValueSet.add(chnName);
                                    sofTm.put(mktgName, sofValueSet);
                                }
                                else
                                {
                                    HashSet sofHs2 = new HashSet();
                                    sofHs2.add(chnName);
                                    sofTm.put(mktgName, sofHs2);
                                }
                            }//end of else
                        }//end of if((checkRfaGeoUS(chn) || checkRfaGeoLA(chn) || checkRfaGeoCAN(chn))  && chnName != null && chnName.length()>0 && PokUtils.isSelected(chn, "ROUTESTOMKTG", "110"))
                    }//end of while (chnItr.hasNext())
                    chnVector.clear();
                }//end of while(sofItr.hasNext())
                sofVector.clear();

                // Find all CMPNT entities related to the AVAIL entity.
                cmpntVector = PokUtils.getAllLinkedEntities(av, "CMPNTAVAIL", "CMPNT");
                // Process all CMPNT entities found
                cmpntItr = cmpntVector.iterator();
                while(cmpntItr.hasNext())
                {
                    // get next CMPNT entity
                    EntityItem cmpnt = (EntityItem)cmpntItr.next();
                    // Find all CHANNEL entities related to the CMPNT entity.
                    Vector chnVector = PokUtils.getAllLinkedEntities(cmpnt, "CMPNTCHANNEL", "CHANNEL");
                    Iterator chnItr = chnVector.iterator();
                    while(chnItr.hasNext())
                    {
                        // get next CHANNEL entity
                        EntityItem chn = (EntityItem) chnItr.next();
                        mktgName = getSofMktgName(cmpnt) + " " + PokUtils.getAttributeValue(cmpnt, "MKTGNAME", "", "No user input found", false);
                        chnName = PokUtils.getAttributeValue(chn, "CHANNELNAME", ",", " ", false);
                        if((checkRfaGeoUS(chn) || checkRfaGeoLA(chn) || checkRfaGeoCAN(chn)) && chnName != null && chnName.length()>0 && PokUtils.isSelected(chn, "ROUTESTOMKTG", "110"))
                        {
                            if(cmpntTm.size() == 0)
                            {
                                HashSet cmpntHs1 = new HashSet();
                                cmpntHs1.add(chnName);
                                cmpntTm.put(mktgName, cmpntHs1);
                            }
                            else
                            {
                                HashSet cmpntValueSet = (HashSet) cmpntTm.get(mktgName);
                                if(cmpntValueSet != null)
                                {
                                    cmpntTm.remove(mktgName);
                                    cmpntValueSet.add(chnName);
                                    cmpntTm.put(mktgName, cmpntValueSet);
                                }
                                else
                                {
                                    HashSet cmpntHs2 = new HashSet();
                                    cmpntHs2.add(chnName);
                                    cmpntTm.put(mktgName, cmpntHs2);
                                }
                            }//end of else
                        }//end of if((checkRfaGeoUS(chn) || checkRfaGeoLA(chn) || checkRfaGeoCAN(chn))  && chnName != null && chnName.length()>0 && PokUtils.isSelected(chn, "ROUTESTOMKTG", "110"))
                    }//end of while(chnItr.hasNext())
                    chnVector.clear();
                }//end of while(cmpntItr.hasNext())
                cmpntVector.clear();

                // Find all FEATURE entities related to the AVAIL entity.
                featureVector = PokUtils.getAllLinkedEntities(av, "FEATUREAVAIL", "FEATURE");
                // Process all FEATURE entities found
                featureItr = featureVector.iterator();
                while(featureItr.hasNext())
                {
                    // get next FEATURE entity
                    EntityItem feature = (EntityItem)featureItr.next();
                    // Find all CHANNEL entities related to the FEATURE entity.
                    Vector chnVector = PokUtils.getAllLinkedEntities(feature, "FEATURECHANNEL", "CHANNEL");
                    Iterator chnItr = chnVector.iterator();
                    while(chnItr.hasNext())
                    {
                        // get next CHANNEL entity
                        EntityItem chn = (EntityItem) chnItr.next();
                        mktgName = getCmpntMktgName(feature) + " " + PokUtils.getAttributeValue(feature, "MKTGNAME", "", "No user input found", false);
                        chnName = PokUtils.getAttributeValue(chn, "CHANNELNAME", ",", " ", false);
                        if((checkRfaGeoUS(chn) || checkRfaGeoLA(chn) || checkRfaGeoCAN(chn)) && chnName != null && chnName.length()>0 && PokUtils.isSelected(chn, "ROUTESTOMKTG", "110"))
                        {
                            if(featureTm.size() == 0)
                            {
                                HashSet featureHs1 = new HashSet();
                                featureHs1.add(chnName);
                                featureTm.put(mktgName, featureHs1);
                            }
                            else
                            {
                                HashSet featureValueSet = (HashSet) featureTm.get(mktgName);
                                if(featureValueSet != null)
                                {
                                    featureTm.remove(mktgName);
                                    featureValueSet.add(chnName);
                                    featureTm.put(mktgName, featureValueSet);
                                }
                                else
                                {
                                    HashSet featureHs2 = new HashSet();
                                    featureHs2.add(chnName);
                                    featureTm.put(mktgName, featureHs2);
                                }
                            }//end of else
                        }//end of if((checkRfaGeoUS(chn) || checkRfaGeoLA(chn) || checkRfaGeoCAN(chn))  && chnName != null && chnName.length()>0 && PokUtils.isSelected(chn, "ROUTESTOMKTG", "110"))
                    }//end of while(chnItr.hasNext())
                    chnVector.clear();
                }//end of while(featureItr.hasNext())
                featureVector.clear();
            }//end of if(checkRfaGeoEMEA(av))
        }//end of while(avItr.hasNext())
        avVct.clear();
        sb.append(formatA0105Answer(sofTm) + formatA0105Answer(cmpntTm) + formatA0105Answer(featureTm));

        pdsAndQ.put("A0105", sb.toString());
        sofTm.clear();
        cmpntTm.clear();
        featureTm.clear();
    }

    private String formatA0105Answer(TreeMap tm)
    {
        // Set answer to the A0105 question for the Internal Announcement Letter.
        StringBuffer sb = new StringBuffer();

        if(tm.size() > 0)
        {
            Set keySet = tm.keySet();
            Iterator i = keySet.iterator();
            while(i.hasNext())
            {
                Iterator j;
                String keyStr = (String) i.next();
                // Get the value from the queue
                HashSet valueSet = (HashSet) tm.get(keyStr);
                //sb.append("<br /><table width=\"450\" border=\"1\" cellpadding=\"2\" cellspacing=\"1\">");
                if(includeHdr)
                {
                    //sb.append("<tr><th>" + keyStr + "</th></tr>");
                    sb.append("<p><b>" + keyStr + "</b></p>");
                }
                j = valueSet.iterator();
                while(j.hasNext())
                {
                    String chnNameStr = (String) j.next();

                    StringTokenizer stn = new StringTokenizer(chnNameStr, ",");
                    while(stn.hasMoreTokens())
                    {
                        //sb.append("<tr><td>" + stn.nextToken() + "</td></tr>");
                        sb.append("<ul><li>" + stn.nextToken() + "</li></ul>");
                    }
                }//end of while(j.hasNext())
                //sb.append("</table>");
            }//end while(i.hasNext())
            keySet.clear();
        }//end of if(tm.size() > 0)
        return (sb.toString());
    }

    private void deriveA0110(EntityItem ea)
    {
        // Derive answers to the A0110 question for the Internal Announcement Letter
        String chnName  = new String();
        String mktgName  = new String();
        StringBuffer sb = new StringBuffer();
        TreeMap sofTm = new TreeMap();
        TreeMap cmpntTm = new TreeMap();
        TreeMap featureTm = new TreeMap();

        // Find all AVAIL entities associated with this ANNOUNCEMENT
        Vector avVct = PokUtils.getAllLinkedEntities(ea, "ANNAVAILA", "AVAIL");

        // Process all AVAIL entities found
        Iterator avItr = avVct.iterator();
        while(avItr.hasNext())
        {
            EntityItem av = (EntityItem)avItr.next();
            if(checkRfaGeoEMEA(av))
            {
                Vector sofVector;
                Iterator sofItr;
                Vector cmpntVector;
                Iterator cmpntItr;
                Vector featureVector;
                Iterator featureItr;

                // Find all SOF entities related to the AVAIL entity.
                sofVector = PokUtils.getAllLinkedEntities(av, "SOFAVAIL", "SOF");
                // Process all SOF entities found
                sofItr = sofVector.iterator();
                while(sofItr.hasNext())
                {
                    // get next SOF entity
                    EntityItem sof = (EntityItem)sofItr.next();
                    // Find all CHANNEL entities related to the SOF entity.
                    Vector chnVector = PokUtils.getAllLinkedEntities(sof, "SOFCHANNEL", "CHANNEL");
                    Iterator chnItr = chnVector.iterator();
                    while(chnItr.hasNext())
                    {
                        // get next CHANNEL entity
                        EntityItem chn = (EntityItem) chnItr.next();
                        mktgName = PokUtils.getAttributeValue(sof, "MKTGNAME", "", "No user input found", false);
                        chnName = PokUtils.getAttributeValue(chn, "CHANNELNAME", ",", " ", false);
                        if(checkRfaGeoEMEA(chn) && chnName != null && chnName.length()>0 && PokUtils.isSelected(chn, "ROUTESTOMKTG", "110"))
                        {
                            if(sofTm.size() == 0)
                            {
                                HashSet sofHs1 = new HashSet();
                                sofHs1.add(chnName);
                                sofTm.put(mktgName, sofHs1);
                            }
                            else
                            {
                                HashSet sofValueSet = (HashSet) sofTm.get(mktgName);
                                if(sofValueSet != null)
                                {
                                    sofTm.remove(mktgName);
                                    sofValueSet.add(chnName);
                                    sofTm.put(mktgName, sofValueSet);
                                }
                                else
                                {
                                    HashSet sofHs2 = new HashSet();
                                    sofHs2.add(chnName);
                                    sofTm.put(mktgName, sofHs2);
                                }
                            }//end of else
                        }//end of if(checkRfaGeoEMEA(chn) && chnName != null && chnName.length()>0 && PokUtils.isSelected(chn, "ROUTESTOMKTG", "110"))
                    }//end of while (chnItr.hasNext())
                    chnVector.clear();
                }//end of while(sofItr.hasNext())
                sofVector.clear();

                // Find all CMPNT entities related to the AVAIL entity.
                cmpntVector = PokUtils.getAllLinkedEntities(av, "CMPNTAVAIL", "CMPNT");
                // Process all CMPNT entities found
                cmpntItr = cmpntVector.iterator();
                while(cmpntItr.hasNext())
                {
                    // get next CMPNT entity
                    EntityItem cmpnt = (EntityItem)cmpntItr.next();
                    // Find all CHANNEL entities related to the CMPNT entity.
                    Vector chnVector = PokUtils.getAllLinkedEntities(cmpnt, "CMPNTCHANNEL", "CHANNEL");
                    Iterator chnItr = chnVector.iterator();
                    while(chnItr.hasNext())
                    {
                        // get next CHANNEL entity
                        EntityItem chn = (EntityItem) chnItr.next();
                        mktgName = getSofMktgName(cmpnt) + " " + PokUtils.getAttributeValue(cmpnt, "MKTGNAME", "", "No user input found", false);
                        chnName = PokUtils.getAttributeValue(chn, "CHANNELNAME", ",", " ", false);
                        if(checkRfaGeoEMEA(chn) && chnName != null && chnName.length()>0 && PokUtils.isSelected(chn, "ROUTESTOMKTG", "110"))
                        {
                            if(cmpntTm.size() == 0)
                            {
                                HashSet cmpntHs1 = new HashSet();
                                cmpntHs1.add(chnName);
                                cmpntTm.put(mktgName, cmpntHs1);
                            }
                            else
                            {
                                HashSet cmpntValueSet = (HashSet) cmpntTm.get(mktgName);
                                if(cmpntValueSet != null)
                                {
                                    cmpntTm.remove(mktgName);
                                    cmpntValueSet.add(chnName);
                                    cmpntTm.put(mktgName, cmpntValueSet);
                                }
                                else
                                {
                                    HashSet cmpntHs2 = new HashSet();
                                    cmpntHs2.add(chnName);
                                    cmpntTm.put(mktgName, cmpntHs2);
                                }
                            }//end of else
                        }//end of if(checkRfaGeoEMEA(chn) && chnName != null && chnName.length()>0 && PokUtils.isSelected(chn, "ROUTESTOMKTG", "110"))
                    }//end of while(chnItr.hasNext())
                    chnVector.clear();
                }//end of while(cmpntItr.hasNext())
                cmpntVector.clear();

                // Find all FEATURE entities related to the AVAIL entity.
                featureVector = PokUtils.getAllLinkedEntities(av, "FEATUREAVAIL", "FEATURE");
                // Process all FEATURE entities found
                featureItr = featureVector.iterator();
                while(featureItr.hasNext())
                {
                    // get next FEATURE entity
                    EntityItem feature = (EntityItem)featureItr.next();
                    // Find all CHANNEL entities related to the FEATURE entity.
                    Vector chnVector = PokUtils.getAllLinkedEntities(feature, "FEATURECHANNEL", "CHANNEL");
                    Iterator chnItr = chnVector.iterator();
                    while(chnItr.hasNext())
                    {
                        // get next CHANNEL entity
                        EntityItem chn = (EntityItem) chnItr.next();
                        mktgName = getCmpntMktgName(feature) + " " + PokUtils.getAttributeValue(feature, "MKTGNAME", "", "No user input found", false);
                        chnName = PokUtils.getAttributeValue(chn, "CHANNELNAME", ",", " ", false);
                        if(checkRfaGeoEMEA(chn) && chnName != null && chnName.length()>0 && PokUtils.isSelected(chn, "ROUTESTOMKTG", "110"))
                        {
                            if(featureTm.size() == 0)
                            {
                                HashSet featureHs1 = new HashSet();
                                featureHs1.add(chnName);
                                featureTm.put(mktgName, featureHs1);
                            }
                            else
                            {
                                HashSet featureValueSet = (HashSet) featureTm.get(mktgName);
                                if(featureValueSet != null)
                                {
                                    featureTm.remove(mktgName);
                                    featureValueSet.add(chnName);
                                    featureTm.put(mktgName, featureValueSet);
                                }
                                else
                                {
                                    HashSet featureHs2 = new HashSet();
                                    featureHs2.add(chnName);
                                    featureTm.put(mktgName, featureHs2);
                                }
                            }//end of else
                        }//end of if(checkRfaGeoEMEA(chn) && chnName != null && chnName.length()>0 && PokUtils.isSelected(chn, "ROUTESTOMKTG", "110"))
                    }//end of while(chnItr.hasNext())
                    chnVector.clear();
                }//end of while(featureItr.hasNext())
                featureVector.clear();
            }//end of if(checkRfaGeoEMEA(av))
        }//end of while(avItr.hasNext())
        avVct.clear();
        sb.append(formatA0110Answer(sofTm) + formatA0110Answer(cmpntTm) + formatA0110Answer(featureTm));

        pdsAndQ.put("A0110", sb.toString());
        sofTm.clear();
        cmpntTm.clear();
        featureTm.clear();
    }

    private String formatA0110Answer(TreeMap tm)
    {
        // Set answer to the A0110 question for the Internal Announcement Letter.
        StringBuffer sb = new StringBuffer();

        if(tm.size() > 0)
        {
            Set keySet = tm.keySet();
            Iterator i = keySet.iterator();
            while(i.hasNext())
            {
                Iterator j;
                String keyStr = (String) i.next();
                // Get the value from the queue
                HashSet valueSet = (HashSet) tm.get(keyStr);
                //sb.append("<br /><table width=\"450\" border=\"1\" cellpadding=\"2\" cellspacing=\"1\">");
                if(includeHdr)
                {
                    //sb.append("<tr><th>" + keyStr + "</th></tr>");
                    sb.append("<p><b>" + keyStr + "</b></p>");
                }
                j = valueSet.iterator();
                while(j.hasNext())
                {
                    String chnNameStr = (String) j.next();

                    StringTokenizer stn = new StringTokenizer(chnNameStr, ",");
                    while(stn.hasMoreTokens())
                    {
                        //sb.append("<tr><td>" + stn.nextToken() + "</td></tr>");
                        sb.append("<ul><li>" + stn.nextToken() + "</li></ul>");
                    }
                }//end of while(j.hasNext())
                //sb.append("</table>");

            }//end while(i.hasNext())
            keySet.clear();
        }//end of if(tm.size() > 0)
        return (sb.toString());
    }

    private void deriveA0116(EntityItem ea)
    {
        // Derive answers to the A0116 question for the Internal Announcement Letter

        String chnName  = new String();
        String mktgName  = new String();
        StringBuffer sb = new StringBuffer();
        TreeMap sofTm = new TreeMap();
        TreeMap cmpntTm = new TreeMap();
        TreeMap featureTm = new TreeMap();

        // Find all AVAIL entities associated with this ANNOUNCEMENT
        Vector avVct = PokUtils.getAllLinkedEntities(ea, "ANNAVAILA", "AVAIL");

        // Process all AVAIL entities found
        Iterator avItr = avVct.iterator();
        while(avItr.hasNext())
        {
            Vector sofVector;
            Iterator sofItr;
            Vector cmpntVector;
            Iterator cmpntItr;
            Vector featureVector;
            Iterator featureItr;

            EntityItem av = (EntityItem)avItr.next();
            // Find all SOF entities related to the AVAIL entity.
            sofVector = PokUtils.getAllLinkedEntities(av, "SOFAVAIL", "SOF");

            // Process all SOF entities found
            sofItr = sofVector.iterator();
            while(sofItr.hasNext())
            {
                // get next SOF entity
                EntityItem sof = (EntityItem)sofItr.next();
                // Find all CHANNEL entities related to the SOF entity.
                Vector chnVector = PokUtils.getAllLinkedEntities(sof, "SOFCHANNEL", "CHANNEL");

                Iterator chnItr = chnVector.iterator();
                while(chnItr.hasNext())
                {
                    // get next CHANNEL entity
                    EntityItem chn = (EntityItem) chnItr.next();
                    mktgName = PokUtils.getAttributeValue(sof, "MKTGNAME", "", "No user input found", false);
                    chnName = PokUtils.getAttributeValue(chn, "CHANNELNAME", ",", " ", false);
                    if(chnName != null && chnName.length() > 0 && PokUtils.isSelected(chn, "ROUTESTOMKTG", "100"))
                    {
                        if(sofTm.size() == 0)
                        {
                            TreeMap sofTm1 = new TreeMap();
                            sofTm1.put(chnName,chn);
                            sofTm.put(mktgName, sofTm1);
                        }
                        else
                        {
                            TreeMap sofValueTm = (TreeMap) sofTm.get(mktgName);
                            if(sofValueTm != null)
                            {
                                sofTm.remove(mktgName);
                                sofValueTm.put(chnName,chn);
                                sofTm.put(mktgName, sofValueTm);
                            }
                            else
                            {
                                TreeMap sofTm2 = new TreeMap();
                                sofTm2.put(chnName,chn);
                                sofTm.put(mktgName, sofTm2);
                            }
                        }//end of else
                    }//end of if(chnName != null && chnName.length() > 0 && PokUtils.isSelected(chn, "ROUTESTOMKTG", "100"))
                }//end of while(chnItr.hasNext())
                chnVector.clear();
            }//end of while(sofItr.hasNext())
            sofVector.clear();

            // Find all CMPNT entities related to the AVAIL entity.
            cmpntVector = PokUtils.getAllLinkedEntities(av, "CMPNTAVAIL", "CMPNT");

            // Process all CMPNT entities found
            cmpntItr = cmpntVector.iterator();
            while(cmpntItr.hasNext())
            {
                // get next CMPNT entity
                EntityItem cmpnt = (EntityItem)cmpntItr.next();
                // Find all CHANNEL entities related to the CMPNT entity.
                Vector chnVector = PokUtils.getAllLinkedEntities(cmpnt, "CMPNTCHANNEL", "CHANNEL");

                Iterator chnItr = chnVector.iterator();
                while(chnItr.hasNext())
                {
                    // get next CHANNEL entity
                    EntityItem chn = (EntityItem) chnItr.next();
                    mktgName = getSofMktgName(cmpnt) + " " + PokUtils.getAttributeValue(cmpnt, "MKTGNAME", "", "No user input found", false);
                    chnName = PokUtils.getAttributeValue(chn, "CHANNELNAME", ",", " ", false);
                    if(chnName != null && chnName.length() > 0 && PokUtils.isSelected(chn, "ROUTESTOMKTG", "100"))
                    {
                        if(cmpntTm.size() == 0)
                        {
                            TreeMap cmpntTm1 = new TreeMap();
                            cmpntTm1.put(chnName,chn);
                            cmpntTm.put(mktgName, cmpntTm1);
                        }
                        else
                        {
                            TreeMap cmpntValueTm = (TreeMap) cmpntTm.get(mktgName);
                            if(cmpntValueTm != null)
                            {
                                cmpntTm.remove(mktgName);
                                cmpntValueTm.put(chnName,chn);
                                cmpntTm.put(mktgName, cmpntValueTm);
                            }
                            else
                            {
                                TreeMap cmpntTm2 = new TreeMap();
                                cmpntTm2.put(chnName,chn);
                                cmpntTm.put(mktgName, cmpntTm2);
                            }
                        }//end of else
                    }//end of if(chnName != null && chnName.length() > 0 && PokUtils.isSelected(chn, "ROUTESTOMKTG", "100"))
                }//end of while(chnItr.hasNext())
                chnVector.clear();
            }//end of while (cmpntItr.hasNext())
            cmpntVector.clear();

            // Find all FEATURE entities related to the AVAIL entity.
            featureVector = PokUtils.getAllLinkedEntities(av, "FEATUREAVAIL", "FEATURE");

            // Process all FEATURE entities found
            featureItr = featureVector.iterator();
            while(featureItr.hasNext())
            {
                // get next FEATURE entity
                EntityItem feature = (EntityItem)featureItr.next();
                // Find all CHANNEL entities related to the FEATURE entity.
                Vector chnVector = PokUtils.getAllLinkedEntities(feature, "FEATURECHANNEL", "CHANNEL");

                Iterator chnItr = chnVector.iterator();
                while(chnItr.hasNext())
                {
                    // get next CHANNEL entity
                    EntityItem chn = (EntityItem) chnItr.next();
                    mktgName = getCmpntMktgName(feature) + " " + PokUtils.getAttributeValue(feature, "MKTGNAME", "", "No user input found", false);
                    chnName = PokUtils.getAttributeValue(chn, "CHANNELNAME", ",", " ", false);
                    if(chnName != null && chnName.length() > 0 && PokUtils.isSelected(chn, "ROUTESTOMKTG", "100"))
                    {
                        if(featureTm.size() == 0)
                        {
                            TreeMap featureTm1 = new TreeMap();
                            featureTm1.put(chnName,chn);
                            featureTm.put(mktgName, featureTm1);
                        }
                        else
                        {
                            TreeMap featureValueTm = (TreeMap) featureTm.get(mktgName);
                            if(featureValueTm != null)
                            {
                                featureTm.remove(mktgName);
                                featureValueTm.put(chnName,chn);
                                featureTm.put(mktgName, featureValueTm);
                            }
                            else
                            {
                                TreeMap featureTm2 = new TreeMap();
                                featureTm2.put(chnName,chn);
                                featureTm.put(mktgName, featureTm2);
                            }
                        }//end of else
                    }//end of if(chnName != null && chnName.length() > 0 && PokUtils.isSelected(chn, "ROUTESTOMKTG", "100"))
                }//end of while(chnItr.hasNext())
                chnVector.clear();
            }//end of while(featureItr.hasNext())
            featureVector.clear();
        }//end of while(avItr.hasNext())
        // log("A0116 - before formatting answer");
        avVct.clear();
        sb.append(formatA0116Answer(sofTm) + formatA0116Answer(cmpntTm) + formatA0116Answer(featureTm));

        // log("A0116 - after formatting answer");
        pdsAndQ.put("A0116", sb.toString());
        sofTm.clear();
        cmpntTm.clear();
        featureTm.clear();
    }

    private String formatA0116Answer(TreeMap tm)
    {
        // Set answer to the A0116 question for the Internal Announcement Letter.
        StringBuffer sb = new StringBuffer();

        if(tm.size() > 0)
        {
            Set keySet = tm.keySet();
            Iterator i = keySet.iterator();
            while(i.hasNext())
            {
                Set chnSet;
                Iterator j;
                String keyStr = (String) i.next();
                // Get the value from the queue
                TreeMap valueTm = (TreeMap) tm.get(keyStr);
                if(includeHdr)
                {
                    sb.append("<ul><li><b>" + keyStr + "</b></li></ul>");
                }
                sb.append("<br /><table width=\"600\" border=\"1\" cellpadding=\"2\" cellspacing=\"1\" summary=\"Routes to Market\">" +
                    "<tr><th id=\"route\">Route Description</th><th id=\"ww\">WW</th>" +
                    "<th id=\"ap\">AP</th><th id=\"can\">CAN</th><th id=\"us\">US</th><th id=\"emea\">EMEA</th><th id=\"la\">LA</th></tr>");

                chnSet = valueTm.keySet();
                j = chnSet.iterator();
                while(j.hasNext())
                {
                    String chnNameStr = (String) j.next();
                    EntityItem chn = (EntityItem) valueTm.get(chnNameStr);

                    // debugBuffer.append("<!-- formatA0116Answer - chnNameStr = " + chnNameStr + " -->" + NEWLINE);
                    StringTokenizer stn = new StringTokenizer(chnNameStr, ",");
                    while(stn.hasMoreTokens())
                    {
                        sb.append("<tr><td headers=\"route " + chn.getKey() + "\">" + stn.nextToken() + "</td>");
                        sb.append(getA0116GeoInfo(chn) + "</tr>");
                    }
                }//end of while(j.hasNext())
                sb.append("</table>");
                chnSet.clear();
            }//end of while(i.hasNext())
            keySet.clear();
        }//end of if(tm.size() > 0)
        return (sb.toString());
    }

    private String getA0116GeoInfo(EntityItem chn)
    {
        StringBuffer sb = new StringBuffer();
        String key = chn.getKey();
        log("entering getA0116GeoInfo");
        if(checkRfaGeoAP(chn) && checkRfaGeoCAN(chn) && checkRfaGeoUS(chn) && checkRfaGeoEMEA(chn) && checkRfaGeoLA(chn))
        {
            sb.append("<td align=\"center\" headers=\"ww " + key + "\">X</td>");
            sb.append("<td headers=\"ap " + key + "\">&nbsp;</td>");
            sb.append("<td headers=\"can " + key + "\">&nbsp;</td>");
            sb.append("<td headers=\"us " + key + "\">&nbsp;</td>");
            sb.append("<td headers=\"emea " + key + "\">&nbsp;</td>");
            sb.append("<td headers=\"la " + key + "\">&nbsp;</td></tr>");
        }
        else
        {
            sb.append("<td headers=\"ww " + key + "\">&nbsp;</td>");
            if(checkRfaGeoAP(chn))
            {
                sb.append("<td align=\"center\" headers=\"ap " + key + "\">X</td>");
            }
            else
            {
                sb.append("<td headers=\"ap " + key + "\">&nbsp;</td>");
            }
            if(checkRfaGeoCAN(chn))
            {
                sb.append("<td align=\"center\" headers=\"can " + key + "\">X</td>");
            }
            else
            {
                sb.append("<td headers=\"can " + key + "\">&nbsp;</td>");
            }
            if(checkRfaGeoUS(chn))
            {
                sb.append("<td align=\"center\" headers=\"us " + key + "\">X</td>");
            }
            else
            {
                sb.append("<td headers=\"us " + key + "\">&nbsp;</td>");
            }
            if(checkRfaGeoEMEA(chn))
            {
                sb.append("<td align=\"center\" headers=\"emea " + key + "\">X</td>");
            }
            else
            {
                sb.append("<td headers=\"emea " + key + "\">&nbsp;</td>");
            }
            if(checkRfaGeoLA(chn))
            {
                sb.append("<td align=\"center\" headers=\"la " + key + "\">X</td></tr>");
            }
            else
            {
                sb.append("<td headers=\"la " + key + "\">&nbsp;</td></tr>");
            }
        }//end of else
        log("exiting getA0116GeoInfo");
        return (sb.toString());
    }

    private void deriveA0119_120(String aa)
    {
        // Derive answers to the A0119, A0120 questions for the Internal Announcement Letter

        StringBuffer sb = new StringBuffer();
        String mktgName  = new String();
        String mktgName2  = new String();
        TreeMap sofTm = new TreeMap();
        TreeMap cmpntTm = new TreeMap();
        TreeMap featureTm = new TreeMap();
        String geo = "";
        String crosssellUpsellType = "";
        String crosssellUpsell = "";

        if(aa.equals("A0119"))
        {
            crosssellUpsellType = "CROSSSELL";
        }
        else if(aa.equals("A0120"))
        {
            crosssellUpsellType = "UPSELL";
        }

        for(int i = 0; i < sofV.size(); i++)
        {
            EntityItem sof = (EntityItem) sofV.get(i);

            if(checkRfaGeoUS(sof) || checkRfaGeoCAN(sof))
            {
                mktgName = PokUtils.getAttributeValue(sof, "MKTGNAME", "", "No user input found for SOF MKTGNAME", false);
                geo = getGeo(sof.getKey(), geoHT);
                if(!crosssellUpsellType.equals(""))
                {
                    crosssellUpsell = PokUtils.getAttributeValue(sof, crosssellUpsellType, "", "", false);
                }
                // Find relator SOFRELSOF, SOFRELCMPNT and SOFRELFEATURE
                for(int ui = 0; ui < sof.getDownLinkCount(); ui++)
                {
                    EANEntity rsof = sof.getDownLink(ui);
                    if(rsof.getEntityType().equals("SOFRELSOF") ||
                        rsof.getEntityType().equals("SOFRELCMPNT") ||
                        rsof.getEntityType().equals("SOFRELFEATURE"))
                    {
                        for(int si = 0; si < rsof.getDownLinkCount(); si++)
                        {
                            EANEntity ei = rsof.getDownLink(si);
                            if(ei.getEntityType().equals("SOF") ||
                                ei.getEntityType().equals("CMPNT") ||
                                ei.getEntityType().equals("FEATURE"))
                            {
                                if(ei.getEntityType().equals("SOF"))
                                {
                                    mktgName2 = "1" + "<:>" + PokUtils.getAttributeValue((EntityItem)ei, "MKTGNAME", "", "No user input found for SOF MKTGNAME", false);
                                }
                                else
                                {
                                    if(ei.getEntityType().equals("CMPNT"))
                                    {
                                        mktgName2 = "2" + "<:>" + getSofMktgName((EntityItem)ei) + " " + PokUtils.getAttributeValue((EntityItem)ei, "MKTGNAME", "", "No user input found for CMPNT MKTGNAME", false);
                                    }
                                    else
                                    {
                                        mktgName2 = "3" + "<:>" + getCmpntMktgName((EntityItem)ei) + " " + PokUtils.getAttributeValue((EntityItem)ei, "MKTGNAME", "", "No user input found for FEATURE MKTGNAME", false);
                                    }
                                }//end of else
                                processRelator((EntityItem)rsof, sofTm, aa, mktgName + "<:>" + geo + "<:>" + crosssellUpsell + "<:>" + mktgName2);
                            }//end of if(ei.getEntityType().equals("SOF") || ei.getEntityType().equals("CMPNT") || ei.getEntityType().equals("FEATURE"))
                        }//end of for(int si = 0; si < rsof.getDownLinkCount(); si++)
                    }//end of if(rsof.getEntityType().equals("SOFRELSOF") || rsof.getEntityType().equals("SOFRELCMPNT") || rsof.getEntityType().equals("SOFRELFEATURE"))
                }//end of for(int ui = 0; ui < sof.getDownLinkCount(); ui++)
            }//end of if(checkRfaGeoUS(sof) || checkRfaGeoCAN(sof))
        }//end of for(int i = 0; i < sofV.size(); i++)

        for(int i = 0; i < cmpntV.size(); i++)
        {
            EntityItem cmpnt = (EntityItem) cmpntV.get(i);

            if(checkRfaGeoUS(cmpnt) || checkRfaGeoCAN(cmpnt))
            {
                mktgName = getSofMktgName(cmpnt) + " " + PokUtils.getAttributeValue(cmpnt, "MKTGNAME", "", "No user input found for CMPNT MKTGNAME", false);
                geo = getGeo(cmpnt.getKey(), geoHT);
                if(!crosssellUpsellType.equals(""))
                {
                    crosssellUpsell = PokUtils.getAttributeValue(cmpnt, crosssellUpsellType, "", "", false);
                }
                // Find relator CMPNTRELSOF, CMPNTRELCMPNT and CMPNTRELFEATURE
                for(int ui = 0; ui < cmpnt.getDownLinkCount(); ui++)
                {
                    EANEntity rcmpnt = cmpnt.getDownLink(ui);
                    if(rcmpnt.getEntityType().equals("CMPNTRELSOF") ||
                        rcmpnt.getEntityType().equals("CMPNTRELCMPNT") ||
                        rcmpnt.getEntityType().equals("CMPNTRELFEATURE"))
                    {
                        for(int si = 0; si < rcmpnt.getDownLinkCount(); si++)
                        {
                            EANEntity ei = rcmpnt.getDownLink(si);
                            if(ei.getEntityType().equals("SOF") ||
                                ei.getEntityType().equals("CMPNT") ||
                                ei.getEntityType().equals("FEATURE"))
                            {
                                if(ei.getEntityType().equals("SOF"))
                                {
                                    mktgName2 = "1" + "<:>" + PokUtils.getAttributeValue((EntityItem)ei, "MKTGNAME", "", "No user input found for SOF MKTGNAME", false);
                                }
                                else
                                {
                                    if(ei.getEntityType().equals("CMPNT"))
                                    {
                                        mktgName2 = "2" + "<:>" + getSofMktgName((EntityItem)ei) + " " + PokUtils.getAttributeValue((EntityItem)ei, "MKTGNAME", "", "No user input found for CMPNT MKTGNAME", false);
                                    }
                                    else
                                    {
                                        mktgName2 = "3" + "<:>" + getCmpntMktgName((EntityItem)ei) + " " + PokUtils.getAttributeValue((EntityItem)ei, "MKTGNAME", "", "No user input found for FEATURE MKTGNAME", false);
                                    }
                                }//end of else
                                processRelator((EntityItem)rcmpnt, cmpntTm, aa, mktgName + "<:>" + geo + "<:>" + crosssellUpsell + "<:>" + mktgName2);
                            }//end of if(ei.getEntityType().equals("SOF") || ei.getEntityType().equals("CMPNT") || ei.getEntityType().equals("FEATURE"))
                        }//end of for(int si = 0; si < rcmpnt.getDownLinkCount(); si++)
                    }//end of if(rcmpnt.getEntityType().equals("CMPNTRELSOF") || rcmpnt.getEntityType().equals("CMPNTRELCMPNT") || rcmpnt.getEntityType().equals("CMPNTRELFEATURE"))
                }//end of for(int ui = 0; ui < cmpnt.getDownLinkCount(); ui++)
            }//end of if(checkRfaGeoUS(cmpnt) || checkRfaGeoCAN(cmpnt))
        }//end of for(int i = 0; i < cmpntV.size(); i++)

        for(int i = 0; i < featureV.size(); i++)
        {
            EntityItem feature = (EntityItem) featureV.get(i);

            if(checkRfaGeoUS(feature) || checkRfaGeoCAN(feature))
            {
                mktgName = getCmpntMktgName(feature) + " " + PokUtils.getAttributeValue(feature, "MKTGNAME", "", "No user input found for FEATURE MKTGNAME", false);
                geo = getGeo(feature.getKey(), geoHT);
                if(!crosssellUpsellType.equals(""))
                {
                    crosssellUpsell = PokUtils.getAttributeValue(feature, crosssellUpsellType, "", "", false);
                }
                // Find relator FEATURERELSOF, FEATURERELCMPNT and FEATURERELFEATURE
                for(int ui = 0; ui < feature.getDownLinkCount(); ui++)
                {
                    EANEntity rfeature = feature.getDownLink(ui);
                    if(rfeature.getEntityType().equals("FEATURERELSOF") ||
                        rfeature.getEntityType().equals("FEATURERELCMPNT") ||
                        rfeature.getEntityType().equals("FEATURERELFEATURE"))
                    {
                        for(int si = 0; si < rfeature.getDownLinkCount(); si++)
                        {
                            EANEntity ei = rfeature.getDownLink(si);
                            if(ei.getEntityType().equals("SOF") ||
                                ei.getEntityType().equals("CMPNT") ||
                                ei.getEntityType().equals("FEATURE"))
                            {
                                if(ei.getEntityType().equals("SOF"))
                                {
                                    mktgName2 = "1" + "<:>" + PokUtils.getAttributeValue((EntityItem)ei, "MKTGNAME", "", "No user input found for SOF MKTGNAME", false);
                                }
                                else
                                {
                                    if(ei.getEntityType().equals("CMPNT"))
                                    {
                                        mktgName2 = "2" + "<:>" + getSofMktgName((EntityItem)ei) + " " + PokUtils.getAttributeValue((EntityItem)ei, "MKTGNAME", "", "No user input found for CMPNT MKTGNAME", false);
                                    }
                                    else
                                    {
                                        mktgName2 = "3" + "<:>" + getCmpntMktgName((EntityItem)ei) + " " + PokUtils.getAttributeValue((EntityItem)ei, "MKTGNAME", "", "No user input found for FEATURE MKTGNAME", false);
                                    }
                                }//end of else
                                processRelator((EntityItem)rfeature, featureTm, aa, mktgName + "<:>" + geo + "<:>" + crosssellUpsell + "<:>" + mktgName2);
                            }//end of if(ei.getEntityType().equals("SOF") || ei.getEntityType().equals("CMPNT") || ei.getEntityType().equals("FEATURE"))
                        }//end of for(int si = 0; si < rfeature.getDownLinkCount(); si++)
                    }//end of if(rfeature.getEntityType().equals("FEATURERELSOF") || rfeature.getEntityType().equals("FEATURERELCMPNT") || rfeature.getEntityType().equals("FEATURERELFEATURE"))
                }//end of for(int ui = 0; ui < feature.getDownLinkCount(); ui++)
            }//end of if(checkRfaGeoUS(feature) || checkRfaGeoCAN(feature))
        }//end of for(int i = 0; i < featureV.size(); i++)

        // log(aa + " - before formatting answer");
        sb.append(formatA0119_120Answer(sofTm) + formatA0119_120Answer(cmpntTm) + formatA0119_120Answer(featureTm));

        pdsAndQ.put(aa, sb.toString());
        // log(aa + " - after formatting");
    }

    private void processRelator(EntityItem ei, TreeMap tm, String aa, String keyStr)
    {
        // To process relator for A0119, A0120
        // The EntityItem is the relator

        if((aa.equals("A0119") &&
                PokUtils.isSelected(ei, "TYPE", SVConstants.XR_TYPE_110)) ||
            (aa.equals("A0120") &&
                PokUtils.isSelected(ei, "TYPE", SVConstants.XR_TYPE_100)))
        {
            String benefit = PokUtils.getAttributeValue(ei, "BENEFIT", "", "", false);
            if(benefit == null)
            {
                benefit = "";
            }
            if(tm.size() == 0)
            {
                HashSet hs1 = new HashSet();
                hs1.add(benefit);
                tm.put(keyStr, hs1);
            }
            else
            {
                HashSet valueHs = (HashSet) tm.get(keyStr);
                if(valueHs != null)
                {
                    tm.remove(keyStr);
                    valueHs.add(benefit);
                    tm.put(keyStr, valueHs);
                }
                else
                {
                    HashSet hs2 = new HashSet();
                    hs2.add(benefit);
                    tm.put(keyStr, hs2);
                }
            }//end of else
        }//end of if
    }

    private String formatA0119_120Answer(TreeMap tm)
    {
        //  Set answer to the A0119 or A0120 question for the Internal Announcement Letter.
        StringBuffer sb = new StringBuffer();
        String prevStr = new String();
        String currStr = new String();
        String currentGeo = "";
        String lastGeo = "";
        String prevCrosssellUpsell = "";
        String currCrosssellUpsell = "";

        if(tm.size() > 0)
        {
            Set keySet = tm.keySet();
            Iterator i = keySet.iterator();
            while(i.hasNext())
            {
                HashSet valueHs;
                Iterator j;
                String keyStr = (String) i.next();
                currentGeo = parseString(keyStr, 2);

                if(!currentGeo.equals(lastGeo) && !lastGeo.equals("WW") && !lastGeo.equals(""))
                {
                    sb.append("<p><b>&lt;---" + lastGeo + "</b></p>" + NEWLINE);
                }

                if(!currentGeo.equals(lastGeo) && !currentGeo.equals("WW"))
                {
                    sb.append("<p><b>" + currentGeo + "---&gt;</b></p>" + NEWLINE);
                }
                else
                {
                    sb.append("<p></p>" + NEWLINE);
                }

                if(includeHdr)
                {
                    // Set Offering name
                    currStr = parseString(keyStr, 1);
                    if(!currStr.equals(prevStr))
                    {
                        sb.append("<br /><p><b>" + currStr + "</b></p>");
                        prevStr = currStr;
                    }
                }
                currCrosssellUpsell = parseString(keyStr, 3);
                if(!currCrosssellUpsell.equals(prevCrosssellUpsell))
                {
                    sb.append(currCrosssellUpsell);
                    prevCrosssellUpsell = currCrosssellUpsell;
                }

                sb.append("<br /><ul><li><b>" + parseString(keyStr, 5) + "</b></li>");
                // Get the Benefit value from the queue
                valueHs = (HashSet) tm.get(keyStr);
                j = valueHs.iterator();
                while(j.hasNext())
                {
                    String benefit = (String) j.next();
                    if(benefit.length() > 0)
                    {
                        sb.append("<ul><li>Benefit: " + benefit + "</li></ul>");
                    }
                }
                sb.append("</ul>");

                lastGeo = currentGeo;
            }//end of while (i.hasNext())
            keySet.clear();
            if(!lastGeo.equals("WW") && !lastGeo.equals(""))
            {
                sb.append("<p><b>&lt;---" + lastGeo + "</b></p>" + NEWLINE);
            }
        }//end of if (tm.size() > 0)

        return (sb.toString());
    }

    private void deriveA0126()
    {
        //Derive answer A0126 questions for the Internal Announcement Letter

        StringBuffer sb = new StringBuffer();
        String mktgName  = new String();
        String mktgName2  = new String();
        TreeMap sofTm = new TreeMap();
        TreeMap cmpntTm = new TreeMap();
        TreeMap featureTm = new TreeMap();
        String geo = "";

        for(int i = 0; i < sofV.size(); i++)
        {
            EntityItem sof = (EntityItem) sofV.get(i);

            if(checkRfaGeoUS(sof) || checkRfaGeoCAN(sof))
            {
                mktgName = PokUtils.getAttributeValue(sof, "MKTGNAME", "", "No user input found for SOF MKTGNAME", false);
                geo = getGeo(sof.getKey(), geoHT);

                // Find relator SOFRELSOF, SOFRELCMPNT and SOFRELFEATURE
                for(int ui = 0; ui < sof.getDownLinkCount(); ui++)
                {
                    EANEntity rsof = sof.getDownLink(ui);
                    if(rsof.getEntityType().equals("SOFRELSOF") ||
                        rsof.getEntityType().equals("SOFRELCMPNT") ||
                        rsof.getEntityType().equals("SOFRELFEATURE"))
                    {
                        for(int si = 0; si < rsof.getDownLinkCount(); si++)
                        {
                            EANEntity ei = rsof.getDownLink(si);
                            if(ei.getEntityType().equals("SOF") ||
                                ei.getEntityType().equals("CMPNT") ||
                                ei.getEntityType().equals("FEATURE"))
                            {
                                if(ei.getEntityType().equals("SOF"))
                                {
                                    mktgName2 = "1" + "<:>" + PokUtils.getAttributeValue((EntityItem)ei, "MKTGNAME", "", "No user input found for SOF MKTGNAME", false);
                                }
                                else
                                {
                                    if(ei.getEntityType().equals("CMPNT"))
                                    {
                                        mktgName2 = "2" + "<:>" + getSofMktgName((EntityItem)ei) + " " + PokUtils.getAttributeValue((EntityItem)ei, "MKTGNAME", "", "No user input found for CMPNT MKTGNAME", false);
                                    }
                                    else
                                    {
                                        mktgName2 = "3" + "<:>" + getCmpntMktgName((EntityItem)ei) + " " + PokUtils.getAttributeValue((EntityItem)ei, "MKTGNAME", "", "No user input found for FEATURE MKTGNAME", false);
                                    }
                                }//end of else
                                processRelatorForA0126((EntityItem)rsof, sofTm, mktgName + "<:>" + geo + "<:>" + mktgName2);
                            }//end of if(ei.getEntityType().equals("SOF") || ei.getEntityType().equals("CMPNT") || ei.getEntityType().equals("FEATURE"))
                        }//end of for(int si = 0; si < rsof.getDownLinkCount(); si++)
                    }//end of if(rsof.getEntityType().equals("SOFRELSOF") || rsof.getEntityType().equals("SOFRELCMPNT") || rsof.getEntityType().equals("SOFRELFEATURE"))
                }//end of for(int ui = 0; ui < sof.getDownLinkCount(); ui++)
            }//end of if(checkRfaGeoUS(sof) || checkRfaGeoCAN(sof))
        }//end of for(int i = 0; i < sofV.size(); i++)

        for(int i = 0; i < cmpntV.size(); i++)
        {
            EntityItem cmpnt = (EntityItem) cmpntV.get(i);

            if(checkRfaGeoUS(cmpnt) || checkRfaGeoCAN(cmpnt))
            {
                mktgName = getSofMktgName(cmpnt) + " " + PokUtils.getAttributeValue(cmpnt, "MKTGNAME", "", "No user input found for CMPNT MKTGNAME", false);
                geo = getGeo(cmpnt.getKey(), geoHT);

                // Find relator CMPNTRELSOF, CMPNTRELCMPNT and CMPNTRELFEATURE
                for(int ui = 0; ui < cmpnt.getDownLinkCount(); ui++)
                {
                    EANEntity rcmpnt = cmpnt.getDownLink(ui);
                    if(rcmpnt.getEntityType().equals("CMPNTRELSOF") ||
                        rcmpnt.getEntityType().equals("CMPNTRELCMPNT") ||
                        rcmpnt.getEntityType().equals("CMPNTRELFEATURE"))
                    {
                        for(int si = 0; si < rcmpnt.getDownLinkCount(); si++)
                        {
                            EANEntity ei = rcmpnt.getDownLink(si);
                            if(ei.getEntityType().equals("SOF") ||
                                ei.getEntityType().equals("CMPNT") ||
                                ei.getEntityType().equals("FEATURE"))
                            {
                                if(ei.getEntityType().equals("SOF"))
                                {
                                    mktgName2 = "1" + "<:>" + PokUtils.getAttributeValue((EntityItem)ei, "MKTGNAME", "", "No user input found for SOF MKTGNAME", false);
                                }
                                else
                                {
                                    if(ei.getEntityType().equals("CMPNT"))
                                    {
                                        mktgName2 = "2" + "<:>" + getSofMktgName((EntityItem)ei) + " " + PokUtils.getAttributeValue((EntityItem)ei, "MKTGNAME", "", "No user input found for CMPNT MKTGNAME", false);
                                    }
                                    else
                                    {
                                        mktgName2 = "3" + "<:>" + getCmpntMktgName((EntityItem)ei) + " " + PokUtils.getAttributeValue((EntityItem)ei, "MKTGNAME", "", "No user input found for FEATURE MKTGNAME", false);
                                    }
                                }//end of else
                                processRelatorForA0126((EntityItem)rcmpnt, cmpntTm, mktgName + "<:>" + geo + "<:>" + mktgName2);
                            }//end of if(ei.getEntityType().equals("SOF") || ei.getEntityType().equals("CMPNT") || ei.getEntityType().equals("FEATURE"))
                        }//end of for(int si = 0; si < rcmpnt.getDownLinkCount(); si++)
                    }//end of if(rcmpnt.getEntityType().equals("CMPNTRELSOF") || rcmpnt.getEntityType().equals("CMPNTRELCMPNT") || rcmpnt.getEntityType().equals("CMPNTRELFEATURE"))
                }//end of for(int ui = 0; ui < cmpnt.getDownLinkCount(); ui++)
            }//end of if(checkRfaGeoUS(cmpnt) || checkRfaGeoCAN(cmpnt))
        }//end of for(int i = 0; i < cmpntV.size(); i++)

        for(int i = 0; i < featureV.size(); i++)
        {
            EntityItem feature = (EntityItem) featureV.get(i);

            if(checkRfaGeoUS(feature) || checkRfaGeoCAN(feature))
            {
                mktgName = getCmpntMktgName(feature) + " " + PokUtils.getAttributeValue(feature, "MKTGNAME", "", "No user input found for FEATURE MKTGNAME", false);
                geo = getGeo(feature.getKey(), geoHT);

                // Find relator FEATURERELSOF, FEATURERELCMPNT and FEATURERELFEATURE
                for(int ui = 0; ui < feature.getDownLinkCount(); ui++)
                {
                    EANEntity rfeature = feature.getDownLink(ui);
                    if(rfeature.getEntityType().equals("FEATURERELSOF") ||
                        rfeature.getEntityType().equals("FEATURERELCMPNT") ||
                        rfeature.getEntityType().equals("FEATURERELFEATURE"))
                    {
                        for(int si = 0; si < rfeature.getDownLinkCount(); si++)
                        {
                            EANEntity ei = rfeature.getDownLink(si);
                            if(ei.getEntityType().equals("SOF") ||
                                ei.getEntityType().equals("CMPNT") ||
                                ei.getEntityType().equals("FEATURE"))
                            {
                                if(ei.getEntityType().equals("SOF"))
                                {
                                    mktgName2 = "1" + "<:>" + PokUtils.getAttributeValue((EntityItem)ei, "MKTGNAME", "", "No user input found for SOF MKTGNAME", false);
                                }
                                else
                                {
                                    if(ei.getEntityType().equals("CMPNT"))
                                    {
                                        mktgName2 = "2" + "<:>" + getSofMktgName((EntityItem)ei) + " " + PokUtils.getAttributeValue((EntityItem)ei, "MKTGNAME", "", "No user input found for CMPNT MKTGNAME", false);
                                    }
                                    else
                                    {
                                        mktgName2 = "3" + "<:>" + getCmpntMktgName((EntityItem)ei) + " " + PokUtils.getAttributeValue((EntityItem)ei, "MKTGNAME", "", "No user input found for FEATURE MKTGNAME", false);
                                    }
                                }//end of else
                                processRelatorForA0126((EntityItem)rfeature, featureTm, mktgName + "<:>" + geo + "<:>" + mktgName2);
                            }//end of if(ei.getEntityType().equals("SOF") || ei.getEntityType().equals("CMPNT") || ei.getEntityType().equals("FEATURE"))
                        }//end of for(int si = 0; si < rfeature.getDownLinkCount(); si++)
                    }//end of if(rfeature.getEntityType().equals("FEATURERELSOF") || rfeature.getEntityType().equals("FEATURERELCMPNT") || rfeature.getEntityType().equals("FEATURERELFEATURE"))
                }//end of for(int ui = 0; ui < feature.getDownLinkCount(); ui++)
            }//end of if(checkRfaGeoUS(feature) || checkRfaGeoCAN(feature))
        }//end of for(int i = 0; i < featureV.size(); i++)

        sb.append(formatA0126Answer(sofTm) + formatA0126Answer(cmpntTm) + formatA0126Answer(featureTm));

        pdsAndQ.put("A0126", sb.toString());
    }

    private void processRelatorForA0126(EntityItem ei, TreeMap tm, String keyStr)
    {
        // To process relator for A0126
        // The EntityItem is the relator

        if(PokUtils.isSelected(ei, "TYPE", SVConstants.XR_TYPE_120))
        {
            String benefit = PokUtils.getAttributeValue(ei, "BENEFIT", "", "", false);
            if(benefit == null)
            {
                benefit = "";
            }
            if(tm.size() == 0)
            {
                HashSet hs1 = new HashSet();
                hs1.add(benefit);
                tm.put(keyStr, hs1);
            }
            else
            {
                HashSet valueHs = (HashSet) tm.get(keyStr);
                if(valueHs != null)
                {
                    tm.remove(keyStr);
                    valueHs.add(benefit);
                    tm.put(keyStr, valueHs);
                }
                else
                {
                    HashSet hs2 = new HashSet();
                    hs2.add(benefit);
                    tm.put(keyStr, hs2);
                }
            }//end of else
        }//end of if
    }

    private String formatA0126Answer(TreeMap tm)
    {
        //Set answer to the A0126 question for the Internal Announcement Letter.
        StringBuffer sb = new StringBuffer();
        String prevStr = new String();
        String currStr = new String();
        String currentGeo = "";
        String lastGeo = "";

        if(tm.size() > 0)
        {
            Set keySet = tm.keySet();
            Iterator i = keySet.iterator();
            while(i.hasNext())
            {
                HashSet valueHs;
                Iterator itr;
                String keyStr = (String) i.next();
                currStr = parseString(keyStr, 1);
                currentGeo = parseString(keyStr, 2);

                if(!currentGeo.equals(lastGeo) && !lastGeo.equals("WW") && !lastGeo.equals(""))
                {
                    sb.append("<p><b>&lt;---" + lastGeo + "</b></p>" + NEWLINE);
                }

                if(!currentGeo.equals(lastGeo) && !currentGeo.equals("WW"))
                {
                    sb.append("<p><b>" + currentGeo + "---&gt;</b></p>" + NEWLINE);
                }
                else
                {
                    sb.append("<p></p>" + NEWLINE);
                }

                if(includeHdr)
                {
                    // Set Offering name
                    if(!currStr.equals(prevStr))
                    {
                        sb.append("<p><b>" + currStr + "</b></p>");
                        prevStr = currStr;
                    }
                }

                sb.append("<br /><ul><li>" + parseString(keyStr, 4) + "</li>");
                // Get the Benefit value from the queue
                valueHs = (HashSet) tm.get(keyStr);
                itr = valueHs.iterator();
                while(itr.hasNext())
                {
                    String benefit = (String) itr.next();
                    if(benefit.length() > 0)
                    {
                        //sb.append("<ul><li>Benefit: " + benefit + "</li></ul>");
                    }
                }
                sb.append("</ul>");

                lastGeo = currentGeo;
            }//end of while(i.hasNext())
            keySet.clear();
            if(!lastGeo.equals("WW") && !lastGeo.equals(""))
            {
                sb.append("<p><b>&lt;---" + lastGeo + "</b></p>" + NEWLINE);
            }
        }//end of if(tm.size() > 0)

        return (sb.toString());
    }

    private void deriveA0130()
    {
        // Derive answers to the A0130 question for the Internal Announcement Letter
        String lastName  = new String();
        String firstName = new String();
        String mktgName  = new String();
        String geo = "";
        String displayOrder = "";

        StringBuffer sb = new StringBuffer();
        TreeMap sofTm = new TreeMap();
        TreeMap cmpntTm = new TreeMap();
        TreeMap featureTm = new TreeMap();

        for(int i = 0; i < sofV.size(); i++)
        {
            TreeMap tm = new TreeMap();
            EntityItem sof = (EntityItem) sofV.get(i);

            mktgName = PokUtils.getAttributeValue(sof, "MKTGNAME", "", "No user input found", false);
            debugBuffer.append("<!-- mktgName in sofVector loop = " + mktgName + "-->" + NEWLINE);

            for(int ui = 0; ui < sof.getDownLinkCount(); ui++)
            {
                EANEntity entityLink = sof.getDownLink(ui);
                if(entityLink.getEntityType().equals("SOFSALESCNTCTOP"))
                {
                    // check for destination entity as an downlink
                    for(int uj = 0; uj < entityLink.getDownLinkCount(); uj++)
                    {
                        EANEntity entity = entityLink.getDownLink(uj);
                        if(entity.getEntityType().equals("OP"))
                        {
                            EntityItem op = (EntityItem) entity;

                            geo = getGeoInfoForA0130((EntityItem) entityLink);
                            displayOrder = getDisplayOrder(geo);

                            lastName = PokUtils.getAttributeValue(op, "LASTNAME", "", " ", false);
                            firstName = PokUtils.getAttributeValue(op, "FIRSTNAME", "", " ", false);
                            //tm.put(geo + " " + "<:>" + lastName + "<:>" + firstName, op);
                            tm.put(displayOrder + "<:>" + geo + " " + "<:>" + firstName + "<:>" + lastName, op);

                            sofTm.put(mktgName , tm);
                        }//end of if (entity.getEntityType().equals("OP"))
                    }//end of for (int uj = 0; uj < entityLink.getDownLinkCount(); uj++)
                }//end of if (entityLink.getEntityType().equals("SOFSALESCNTCTOP"))
            }//end of for(int ui = 0; ui < sof.getDownLinkCount(); ui++)
        }//end of for(int i = 0; i < sofV.size(); i++)

        for(int i = 0; i < cmpntV.size(); i++)
        {
            TreeMap tm = new TreeMap();
            EntityItem cmpnt = (EntityItem) cmpntV.get(i);

            mktgName = getSofMktgName(cmpnt) + " " + PokUtils.getAttributeValue(cmpnt, "MKTGNAME", "", "No user input found", false);
            debugBuffer.append("<!-- mktgName in cmpntVector loop = " + mktgName + "-->" + NEWLINE);

            for(int ui = 0; ui < cmpnt.getDownLinkCount(); ui++)
            {
                EANEntity entityLink = cmpnt.getDownLink(ui);
                if(entityLink.getEntityType().equals("CMPNTSALESCNTCTOP"))
                {
                    // check for destination entity as an downlink
                    for(int uj = 0; uj < entityLink.getDownLinkCount(); uj++)
                    {
                        EANEntity entity = entityLink.getDownLink(uj);
                        if(entity.getEntityType().equals("OP"))
                        {
                            EntityItem op = (EntityItem) entity;

                            geo = getGeoInfoForA0130((EntityItem) entityLink);
                            displayOrder = getDisplayOrder(geo);

                            lastName = PokUtils.getAttributeValue(op, "LASTNAME", "", " ", false);
                            firstName = PokUtils.getAttributeValue(op, "FIRSTNAME", "", " ", false);
                            //tm.put(geo + " " + "<:>" + lastName + "<:>" + firstName, op);
                            tm.put(displayOrder + "<:>" + geo + " " + "<:>" + firstName + "<:>" + lastName, op);

                            cmpntTm.put(mktgName , tm);
                        }//end of if (entity.getEntityType().equals("OP"))
                    }//end of for (int uj = 0; uj < entityLink.getDownLinkCount(); uj++)
                }//end of if (entityLink.getEntityType().equals("CMPNTSALESCNTCTOP"))
            }//end of for(int ui = 0; ui < cmpnt.getDownLinkCount(); ui++)
        }//end of for(int i = 0; i < cmpntV.size(); i++)

        for(int i = 0; i < featureV.size(); i++)
        {
            TreeMap tm = new TreeMap();
            EntityItem feature = (EntityItem) featureV.get(i);

            mktgName = getCmpntMktgName(feature) + " " + PokUtils.getAttributeValue(feature, "MKTGNAME", "", "No user input found", false);
            debugBuffer.append("<!-- mktgName in featureVector loop = " + mktgName + "-->" + NEWLINE);

            for(int ui = 0; ui < feature.getDownLinkCount(); ui++)
            {
                EANEntity entityLink = feature.getDownLink(ui);
                if(entityLink.getEntityType().equals("FEATRSALESCNTCTOP"))
                {
                    // check for destination entity as an downlink
                    for(int uj = 0; uj < entityLink.getDownLinkCount(); uj++)
                    {
                        EANEntity entity = entityLink.getDownLink(uj);
                        if(entity.getEntityType().equals("OP"))
                        {
                            EntityItem op = (EntityItem) entity;

                            geo = getGeoInfoForA0130((EntityItem) entityLink);
                            displayOrder = getDisplayOrder(geo);

                            lastName = PokUtils.getAttributeValue(op, "LASTNAME", "", " ", false);
                            firstName = PokUtils.getAttributeValue(op, "FIRSTNAME", "", " ", false);
                            //tm.put(geo + " " + "<:>" + lastName + "<:>" + firstName, op);
                            tm.put(displayOrder + "<:>" + geo + " " + "<:>" + firstName + "<:>" + lastName, op);

                            featureTm.put(mktgName , tm);
                        }//end of if (entity.getEntityType().equals("OP"))
                    }//end of for (int uj = 0; uj < entityLink.getDownLinkCount(); uj++)
                }//end of if (entityLink.getEntityType().equals("FEATRSALESCNTCTOP"))
            }//end of for(int ui = 0; ui < feature.getDownLinkCount(); ui++)
        }//end of for(int i = 0; i < featureVector.size(); i++)

        sb.append(formatA0130Answer(sofTm) + formatA0130Answer(cmpntTm) + formatA0130Answer(featureTm));

        pdsAndQ.put("A0130", sb.toString());
        sofTm.clear();
        cmpntTm.clear();
        featureTm.clear();
    }

    //Begin of TIR ID # - USRO-R-JMGR-5ZCNYK
    private String getGeoInfoForA0130(EntityItem ei)
    {
        String result = "";
        String key = ei.getKey();

        if(null != geoHT.get(key))
        {
            result = getGeo(key, geoHT);
        }
        else
        {
            geoHT.put(key, new StringBuffer("NNNNN"));

            if(checkRfaGeoUS(ei))
            {
                updateGeo(key, "US", geoHT);
            }
            if(checkRfaGeoAP(ei))
            {
                updateGeo(key, "AP", geoHT);
            }
            if(checkRfaGeoLA(ei))
            {
                updateGeo(key, "LA", geoHT);
            }
            if(checkRfaGeoCAN(ei))
            {
                updateGeo(key, "CAN", geoHT);
            }
            if(checkRfaGeoEMEA(ei))
            {
                updateGeo(key, "EMEA", geoHT);
            }

            result = getGeo(key, geoHT);
        }

        return result;
    }
    //End of TIR ID # - USRO-R-JMGR-5ZCNYK

    private String getDisplayOrder(String geo)
    {
        String displayOrder = "";

        displayOrder = (String) geoOrderHT.get(geo);
        if(null == displayOrder)
        {
            displayOrder = "32";
        }

        return displayOrder;
    }

    private String formatA0130Answer(TreeMap tm)
    {
        //  Set answer to the A0130 question for the Internal Announcement Letter.
        StringBuffer sb = new StringBuffer();
        String firstName  = new String();
        String lastName  = new String();

        String currentGeo = "";
        String lastGeo = "";

        if(tm.size() > 0)
        {
            Set keySet = tm.keySet();
            Iterator i = keySet.iterator();
            while(i.hasNext())
            {
                TreeMap aTM;
                Set aKeySet;
                Iterator itr;
                String keyStr = (String) i.next();
                currentGeo = "";
                lastGeo = "";

                if(includeHdr)
                {
                    sb.append("<p><b>" + keyStr + "</b></p>" + NEWLINE);
                }
                sb.append("<table width=\"700\" border=\"1\" cellpadding=\"2\" cellspacing=\"1\" summary=\"Sales Contacts\">" +
                    "<tr><th id=\"name\">Contact Name</th><th id=\"telephone\">Telephone</th><th id=\"email\">E-mail</th></tr>" + NEWLINE);

                aTM = (TreeMap) tm.get(keyStr);
                aKeySet = aTM.keySet();
                itr = aKeySet.iterator();
                while(itr.hasNext())
                {
                    String key = (String) itr.next();
                    EntityItem op = (EntityItem) aTM.get(key);
                    currentGeo = parseString(key, 2);
                    currentGeo = currentGeo.trim();
                    firstName = parseString(key, 3);
                    lastName = parseString(key, 4);

                    if(!currentGeo.equals(lastGeo) && !lastGeo.equals("WW") && !lastGeo.equals(""))
                    {
                        sb.append("<tr>" + NEWLINE);
                        sb.append("   <td headers=\"name " + lastGeo + "\"><b>&lt;---" + lastGeo + "</b></td>" + NEWLINE);
                        sb.append("   <td align=\"center\" headers=\"telephone " + lastGeo + "\">&nbsp;</td>" + NEWLINE);
                        sb.append("   <td headers=\"email " + lastGeo + "\">&nbsp;</td>" + NEWLINE);
                        sb.append("</tr>" + NEWLINE);
                    }

                    if(!currentGeo.equals(lastGeo) && !currentGeo.equals("WW"))
                    {
                        sb.append("<tr>" + NEWLINE);
                        sb.append("   <td headers=\"name " + currentGeo + "\"><b>" + currentGeo + "---&gt;</b></td>" + NEWLINE);
                        sb.append("   <td align=\"center\" headers=\"telephone " + currentGeo + "\">&nbsp;</td>" + NEWLINE);
                        sb.append("   <td headers=\"email " + currentGeo + "\">&nbsp;</td>" + NEWLINE);
                        sb.append("</tr>" + NEWLINE);
                    }

                    sb.append("<tr>" + NEWLINE);
                    sb.append("   <td headers=\"name " + op.getKey() + "\">" + firstName + " " + lastName + "</td>" + NEWLINE);
                    sb.append("   <td align=\"center\" headers=\"telephone " + op.getKey() + "\">" + PokUtils.getAttributeValue(op, "TELEPHONE", "", "&nbsp;", false)+ "</td>" + NEWLINE);
                    sb.append("   <td  headers=\"email " + op.getKey() + "\">" + PokUtils.getAttributeValue(op, "EMAIL", "", "&nbsp;", false) + "</td>" + NEWLINE);
                    sb.append("</tr>" + NEWLINE);

                    lastGeo = currentGeo;
                }//end of while(itr.hasNext())
                if(!lastGeo.equals("WW") && !lastGeo.equals(""))
                {
                    sb.append("<tr>" + NEWLINE);
                    sb.append("   <td headers=\"name " + lastGeo + "\"><b>&lt;---" + lastGeo + "</b></td>" + NEWLINE);
                    sb.append("   <td align=\"center\" headers=\"telephone " + lastGeo + "\">&nbsp;</td>" + NEWLINE);
                    sb.append("   <td headers=\"email " + lastGeo + "\">&nbsp;</td>" + NEWLINE);
                    sb.append("</tr>" + NEWLINE);
                }
                sb.append("</table>" + NEWLINE);
            }//end of while(i.hasNext())
        }//end of if(tm.size() > 0)

        //This code works but does not pass Jtest because of using String.indexOf()
        //if(tm.size() > 0)
        //{
        //    Set keySet = tm.keySet();
        //    Iterator i = keySet.iterator();
        //    while(i.hasNext())
        //    {
        //        TreeMap aTM;
        //        Set aKeySet;
        //        Iterator itr;
        //        String keyStr = (String) i.next();
        //        currentGeo = "";
        //        lastGeo = "";

        //        if(includeHdr)
        //        {
        //            sb.append("<p><b>" + keyStr + "</b></p>" + NEWLINE);
        //        }
        //        sb.append("<table width=\"700\" border=\"1\" cellpadding=\"2\" cellspacing=\"1\">" +
        //            "<tr><th>Contact Name</th><th>Telephone</th><th>E-mail</th></tr>" + NEWLINE);

        //        aTM = (TreeMap) tm.get(keyStr);
        //        aKeySet = aTM.keySet();
        //        itr = aKeySet.iterator();
        //        while(itr.hasNext())
        //        {
        //            String key = (String) itr.next();
        //            EntityItem op = (EntityItem) aTM.get(key);
        //            int jj = key.indexOf("<:>");
        //            int j = key.indexOf("<:>", jj + 3);
        //            currentGeo = key.substring(jj + 3, j);
        //            currentGeo = currentGeo.trim();
        //            int k = key.indexOf("<:>", j + 3);
        //            firstName = key.substring(j + 3, k);
        //            lastName = key.substring(k + 3);

        //            if(!currentGeo.equals(lastGeo) && !lastGeo.equals("WW") && !lastGeo.equals(""))
        //            {
        //                sb.append("<tr>" + NEWLINE);
        //                sb.append("   <td><b>&lt;---" + lastGeo + "</b></td>" + NEWLINE);
        //                sb.append("   <td align=\"center\">&nbsp;</td>" + NEWLINE);
        //                sb.append("   <td>&nbsp;</td>" + NEWLINE);
        //                sb.append("</tr>" + NEWLINE);
        //            }

        //            if(!currentGeo.equals(lastGeo) && !currentGeo.equals("WW"))
        //            {
        //                sb.append("<tr>" + NEWLINE);
        //                sb.append("   <td><b>" + currentGeo + "---&gt;</b></td>" + NEWLINE);
        //                sb.append("   <td align=\"center\">&nbsp;</td>" + NEWLINE);
        //                sb.append("   <td>&nbsp;</td>" + NEWLINE);
        //                sb.append("</tr>" + NEWLINE);
        //            }

        //            sb.append("<tr>" + NEWLINE);
        //            sb.append("   <td>" + firstName + " " + lastName + "</td>" + NEWLINE);
        //            sb.append("   <td align=\"center\">" + PokUtils.getAttributeValue(op, "TELEPHONE", "", "&nbsp;", false)+ "</td>" + NEWLINE);
        //            sb.append("   <td>" + PokUtils.getAttributeValue(op, "EMAIL", "", "&nbsp;", false) + "</td>" + NEWLINE);
        //            sb.append("</tr>" + NEWLINE);

        //            lastGeo = currentGeo;
        //        }//end of while(itr.hasNext())
        //        if(!lastGeo.equals("WW") && !lastGeo.equals(""))
        //        {
        //            sb.append("<tr>" + NEWLINE);
        //            sb.append("   <td><b>&lt;---" + lastGeo + "</b></td>" + NEWLINE);
        //            sb.append("   <td align=\"center\">&nbsp;</td>" + NEWLINE);
        //            sb.append("   <td>&nbsp;</td>" + NEWLINE);
        //            sb.append("</tr>" + NEWLINE);
        //        }
        //        sb.append("</table>" + NEWLINE);
        //    }//end of while(i.hasNext())
        //}//end of if(tm.size() > 0)

        return (sb.toString());
    }

    private void deriveA0148(EntityItem ea)
    {
        // Derive answers to the A0148 question for the Internal Announcement Letter

        StringBuffer sb = new StringBuffer();
        String mktgName  = new String();
        String offeringId  = new String();
        String billingApp  = new String();
        TreeMap sofTm = new TreeMap();
        TreeMap cmpntTm = new TreeMap();
        TreeMap featureTm = new TreeMap();

        TreeSet ts = new TreeSet();
        Iterator aItr;

        // Find all AVAIL entities associated with this ANNOUNCEMENT
        Vector avVct = PokUtils.getAllLinkedEntities(ea, "ANNAVAILA", "AVAIL");

        // Process all AVAIL entities found
        Iterator avItr = avVct.iterator();
        while(avItr.hasNext())
        {
            Vector sofVector;
            Iterator sofItr;
            Vector cmpntVector;
            Iterator cmpntItr;
            Vector featureVector;
            Iterator featureItr;

            EntityItem av = (EntityItem) avItr.next();
            // Find all SOF entities related to the AVAIL entity.
            sofVector = PokUtils.getAllLinkedEntities(av, "SOFAVAIL", "SOF");
            // Process all SOF entities found
            sofItr = sofVector.iterator();
            while(sofItr.hasNext())
            {
                // get next SOF entity
                EntityItem sof = (EntityItem)sofItr.next();
                // Find all PRICEFININFO entities related to the SOF entity.
                Vector sofPrVector = PokUtils.getAllLinkedEntities(sof, "SOFPRICE", "PRICEFININFO");
                Iterator sofPrItr = sofPrVector.iterator();
                while(sofPrItr.hasNext())
                {
                    // get next PRICEFININFO entity
                    EntityItem sofPr = (EntityItem) sofPrItr.next();
                    mktgName = PokUtils.getAttributeValue(sof, "MKTGNAME", "", "No user input found", false);
                    offeringId = PokUtils.getAttributeValue(sof, "OFIDNUMBER", "", "No user input found", false);
                    billingApp = PokUtils.getAttributeValue(sofPr, "BILLINGAPP", "|", "&nbsp;", false);
                    ts.add(billingApp);
                    if(billingApp != null && billingApp.length()>0)
                    {
                        if(sofTm.size() == 0)
                        {
                            TreeMap sofTm1 = new TreeMap();
                            //sofTm1.put(offeringId + "<:>" + sofPr.getKey(), billingApp);
                            sofTm1.put(offeringId + "<:>" + billingApp, billingApp);
                            sofTm.put(mktgName, sofTm1);
                        }
                        else
                        {
                            TreeMap sofValueTm = (TreeMap) sofTm.get(mktgName);
                            if(sofValueTm != null)
                            {
                                sofTm.remove(mktgName);
                                //sofValueTm.put(offeringId + "<:>" + sofPr.getKey(), billingApp);
                                sofValueTm.put(offeringId + "<:>" + billingApp, billingApp);
                                sofTm.put(mktgName, sofValueTm);
                            }
                            else
                            {
                                TreeMap sofTm2 = new TreeMap();
                                //sofTm2.put(offeringId + "<:>" + sofPr.getKey(), billingApp);
                                sofTm2.put(offeringId + "<:>" + billingApp, billingApp);
                                sofTm.put(mktgName, sofTm2);
                            }
                        }//end of else
                    }//end of if(billingApp != null && billingApp.length()>0)
                }//end of while(sofPrItr.hasNext())
                sofPrVector.clear();
            }//end of while(sofItr.hasNext())
            sofVector.clear();
            // Find all CMPNT entities related to the AVAIL entity.
            cmpntVector = PokUtils.getAllLinkedEntities(av, "CMPNTAVAIL", "CMPNT");
            // Process all CMPNT entities found
            cmpntItr = cmpntVector.iterator();
            while(cmpntItr.hasNext())
            {
                // get next CMPNT entity
                EntityItem cmpnt = (EntityItem)cmpntItr.next();
                // Find all PRICEFININFO entities related to the CMPNT entity.
                Vector cmpntPrVector = PokUtils.getAllLinkedEntities(cmpnt, "CMPNTPRICE", "PRICEFININFO");
                Iterator cmpntPrItr = cmpntPrVector.iterator();
                while(cmpntPrItr.hasNext())
                {
                    // get next PRICEFININFO entity
                    EntityItem cmpntPr = (EntityItem) cmpntPrItr.next();
                    mktgName = getSofMktgName(cmpnt) + " " +
                        PokUtils.getAttributeValue(cmpnt, "MKTGNAME", "", "No user input found", false);
                    offeringId = PokUtils.getAttributeValue(cmpnt, "COMPONENTID", "", "No user input found", false);
                    billingApp = PokUtils.getAttributeValue(cmpntPr, "BILLINGAPP", "|", "&nbsp;", false);
                    ts.add(billingApp);
                    if(billingApp != null && billingApp.length()>0)
                    {
                        if(cmpntTm.size() == 0)
                        {
                            TreeMap cmpntTm1 = new TreeMap();
                            //cmpntTm1.put(offeringId + "<:>" + cmpntPr.getKey(), billingApp);
                            cmpntTm1.put(offeringId + "<:>" + billingApp, billingApp);
                            cmpntTm.put(mktgName, cmpntTm1);
                        }
                        else
                        {
                            TreeMap cmpntValueTm = (TreeMap) cmpntTm.get(mktgName);
                            if(cmpntValueTm != null)
                            {
                                cmpntTm.remove(mktgName);
                                //cmpntValueTm.put(offeringId + "<:>" + cmpntPr.getKey(), billingApp);
                                cmpntValueTm.put(offeringId + "<:>" + billingApp, billingApp);
                                cmpntTm.put(mktgName, cmpntValueTm);
                            }
                            else
                            {
                                TreeMap cmpntTm2 = new TreeMap();
                                //cmpntTm2.put(offeringId + "<:>" + cmpntPr.getKey(), billingApp);
                                cmpntTm2.put(offeringId + "<:>" + billingApp, billingApp);
                                cmpntTm.put(mktgName, cmpntTm2);
                            }
                        }//end of else
                    }//end of if(billingApp != null && billingApp.length()>0)
                }//end of while(cmpntPrItr.hasNext())
                cmpntPrVector.clear();
            }//end of while (cmpntItr.hasNext())
            cmpntVector.clear();
            // Find all FEATURE entities related to the AVAIL entity.
            featureVector = PokUtils.getAllLinkedEntities(av, "FEATUREAVAIL", "FEATURE");
            // Process all FEATURE entities found
            featureItr = featureVector.iterator();
            while(featureItr.hasNext())
            {
                // get next FEATURE entity
                EntityItem feature = (EntityItem)featureItr.next();
                // Find all PRICEFININFO entities related to the FEATURE entity.
                Vector featurePrVector = PokUtils.getAllLinkedEntities(feature, "FEATUREPRICE", "PRICEFININFO");
                Iterator featurePrItr = featurePrVector.iterator();
                while(featurePrItr.hasNext())
                {
                    // get next PRICEFININFO entity
                    EntityItem featurePr = (EntityItem) featurePrItr.next();
                    mktgName = getCmpntMktgName(feature) + " " +
                        PokUtils.getAttributeValue(feature, "MKTGNAME", "", "No user input found", false);
                    offeringId = PokUtils.getAttributeValue(feature, "FEATURENUMBER", "", "No user input found", false);
                    billingApp = PokUtils.getAttributeValue(featurePr, "BILLINGAPP", "|", "&nbsp;", false);
                    ts.add(billingApp);
                    if(billingApp != null && billingApp.length()>0)
                    {
                        if(featureTm.size() == 0)
                        {
                            TreeMap featureTm1 = new TreeMap();
                            //featureTm1.put(offeringId + "<:>" + featurePr.getKey(), billingApp);
                            featureTm1.put(offeringId + "<:>" + billingApp, billingApp);
                            featureTm.put(mktgName, featureTm1);
                        }
                        else
                        {
                            TreeMap featureValueTm = (TreeMap) featureTm.get(mktgName);
                            if(featureValueTm != null)
                            {
                                featureTm.remove(mktgName);
                                //featureValueTm.put(offeringId + "<:>" + featurePr.getKey(), billingApp);
                                featureValueTm.put(offeringId + "<:>" + billingApp, billingApp);
                                featureTm.put(mktgName, featureValueTm);
                            }
                            else
                            {
                                TreeMap featureTm2 = new TreeMap();
                                //featureTm2.put(offeringId + "<:>" + featurePr.getKey(), billingApp);
                                featureTm2.put(offeringId + "<:>" + billingApp, billingApp);
                                featureTm.put(mktgName, featureTm2);
                            }
                        }//end of else
                    }//if(billingApp != null && billingApp.length()>0)
                }//end of while(featurePrItr.hasNext())
                featurePrVector.clear();
            }//end of while(featureItr.hasNext())
            featureVector.clear();
        }//end of while(avItr.hasNext())
        avVct.clear();

        aItr = ts.iterator();
        while(aItr.hasNext())
        {
            int i = 0;
            String [] billingAppStrArr;
            billingApp = (String) aItr.next();
            //sb.append("<p><b>Billing Application: " + billingApp + "</b></p>");
            billingAppStrArr = PokUtils.convertToArray(billingApp);

            do
            {
                if(0 == i)
                {
                    sb.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"2\" summary=\"layout\">" +
                        "<tr><td align=\"left\">&nbsp</td><td align=\"left\">&nbsp</td></tr>" +
                        "<tr><td align=\"left\"><b>Billing Application:</b></td>");
                    if(billingAppStrArr.length > 0)
                    {
                        sb.append("<td align=\"left\">" + billingAppStrArr[i] + "</td></tr>");
                    }
                    else
                    {
                        sb.append("<td align=\"left\">&nbsp</td></tr>");
                    }
                }
                else
                {
                    sb.append("<tr><td align=\"left\">&nbsp</td><td align=\"left\">" + billingAppStrArr[i] + "</td></tr>");
                }
                i++;
            }while(i < billingAppStrArr.length);
            sb.append("</table>");

            sb.append("<table width=\"550\" border=\"0\" summary=\"layout\">" +
                   //"<table width=\"550\" border=\"1\" cellpadding=\"2\" cellspacing=\"1\">" +
                   //"<tr><th>Offering Name</th><th>Offering ID</th></tr>");
                   //"<tr><th>&nbsp;</th></tr>" +
                "<tr><td align=\"left\"><b>Offering Name</b></td></tr>");
            sb.append(formatA0148Answer(sofTm, billingApp));
            sb.append(formatA0148Answer(cmpntTm, billingApp));
            sb.append(formatA0148Answer(featureTm, billingApp));
            sb.append("</table>");
        }//end of while(aItr.hasNext())

        pdsAndQ.put("A0148", sb.toString());
        sofTm.clear();
        cmpntTm.clear();
        featureTm.clear();
    }

    private String formatA0148Answer(TreeMap tm, String aStr)
    {
        //  Set answer to the A0148 question for the Internal Announcement Letter.
        StringBuffer sb = new StringBuffer();

        if(tm.size() > 0)
        {
            Set keySet = tm.keySet();
            Iterator i = keySet.iterator();
            while(i.hasNext())
            {
                String mktgName = (String) i.next();
                // Get the value from the queue
                TreeMap valueTm = (TreeMap) tm.get(mktgName);
                Set key2Set = valueTm.keySet();
                Iterator j = key2Set.iterator();
                while(j.hasNext())
                {
                    String offeringId = (String) j.next();
                    String billingApp = (String) valueTm.get(offeringId);
                    if(billingApp.equals(aStr))
                    {
                        //sb.append("<tr><td>" + mktgName + "</td><td align=\"center\">" + getOfferingId(offeringId) + "</td></tr>");
                        sb.append("<tr><td>" + mktgName + "</td></tr>");
                    }
                }//end of while(j.hasNext())
            }//end of while(i.hasNext())
        }//end of if(tm.size() > 0)
        return (sb.toString());
    }

    //private String getOfferingId(String str)
    //{
    //    int i = str.indexOf("<:>");

    //    return str.substring(0, i);
    //}

    private void deriveA0151_152(EntityItem ea, String aa)
    {
        // Derive answers to the A0151 question for the Internal Announcement Letter

        TreeMap langTm = new TreeMap();
        HashSet propHs = new HashSet();

        // Find all ANNDELIVERABLE entities associated with this ANNOUNCEMENT
        Vector adVct = PokUtils.getAllLinkedEntities(ea, "ANNTOANNDELIVER", "ANNDELIVERABLE");

        // Process all ANNDELIVERABLE entities found
        Iterator adItr = adVct.iterator();
        while(adItr.hasNext())
        {
            EntityItem ad = (EntityItem)adItr.next();
            // Check for Process Proposal Insert
            if(PokUtils.isSelected(ad, "DELIVERABLETYPE", SVConstants.XR_DELIVERABLETYPE_856))
            {
                // Find all EMEATRANSLATION entities related to the ANNDELIVERABLE entity.
                Vector emeaVector = PokUtils.getAllLinkedEntities(ad, "ANNDELREQTRANS", "EMEATRANSLATION");
                // Process all EMEATRANSLATION entities found
                Iterator emeaItr = emeaVector.iterator();
                while(emeaItr.hasNext())
                {
                    // get next EMEATRANSLATION entity
                    EntityItem emea = (EntityItem)emeaItr.next();
                    if(PokUtils.isSelected(emea, "LANGUAGES", SVConstants.XR_LANGUAGES_2802) ||
                        PokUtils.isSelected(emea, "LANGUAGES", SVConstants.XR_LANGUAGES_2803) ||
                        PokUtils.isSelected(emea, "LANGUAGES", SVConstants.XR_LANGUAGES_2796) ||
                        PokUtils.isSelected(emea, "LANGUAGES", SVConstants.XR_LANGUAGES_2797))
                    {
                        String languages = PokUtils.getAttributeValue(emea, "LANGUAGES", "", "", false);
                        // Find all OP entities related to the EMEATRANSLATION entity.
                        Vector opVector = PokUtils.getAllLinkedEntities(emea, "TRANSDELREVIEW", "OP");
                        Iterator opItr = opVector.iterator();
                        while(opItr.hasNext())
                        {
                            // get next OP entity
                            EntityItem op = (EntityItem) opItr.next();
                            String lastName = PokUtils.getAttributeValue(op, "LASTNAME", "", "", false);
                            if(lastName != null && lastName.length() > 0)
                            {
                                if(langTm.size() == 0)
                                {
                                    HashSet langHs1 = new HashSet();
                                    langHs1.add(op);
                                    langTm.put(languages, langHs1);
                                }
                                else
                                {
                                    HashSet langValueSet = (HashSet) langTm.get(languages);
                                    if(langValueSet != null)
                                    {
                                        langTm.remove(languages);
                                        langValueSet.add(op);
                                        langTm.put(languages, langValueSet);
                                    }
                                    else
                                    {
                                        HashSet langHs2 = new HashSet();
                                        langHs2.add(op);
                                        langTm.put(languages, langHs2);
                                    }
                                }
                            }//end of if(lastName != null && lastName.length() > 0)
                        }//end of while(opItr.hasNext())
                        opVector.clear();
                    }
                    if(aa.equals("A0152"))
                    {
                        // Process Proposal Insert Id
                        String propInsId = PokUtils.getAttributeValue(emea, "PROPOSALINSERTID", "", "", false);
                        if(propInsId != null && propInsId.length()>0)
                        {
                            propHs.add(propInsId);
                        }
                    }
                }//end of while(emeaItr.hasNext())
                emeaVector.clear();
            }//end of if(PokUtils.isSelected(ad, "DELIVERABLETYPE", SVConstants.XR_DELIVERABLETYPE_856))
        }//end of while(adItr.hasNext())
        adVct.clear();
        pdsAndQ.put(aa, formatA0151_152Answer(aa, langTm, propHs));
        langTm.clear();
        propHs.clear();
    }

    private String formatA0151_152Answer(String aa, TreeMap tm, HashSet hs)
    {
        //  Set answer to the A0151 or A0152 question for the Internal Announcement Letter.

        StringBuffer sb = new StringBuffer();
        String firstName  = new String();
        String firstInit = new String();

        if(tm.size() > 0)
        {
            Set keySet = tm.keySet();
            Iterator i = keySet.iterator();
            while(i.hasNext())
            {
                Iterator j;
                String language = (String) i.next();
                // Get the value from the queue
                HashSet valueSet = (HashSet) tm.get(language);
                sb.append("<br /><table width=\"650\" border=\"1\" cellpadding=\"2\" cellspacing=\"1\" summary=\"Translation Information\">" +
                    "<tr><th id=\"language\">Language</th><th id=\"name\">Brand Reviewer Name</th><th id=\"userid\">Node/Userid</th></tr>");

                j = valueSet.iterator();
                while(j.hasNext())
                {
                    EntityItem op = (EntityItem) j.next();
                    String key = op.getKey();
                    //String nodeName = PokUtils.getAttributeValue(op, "VNETNODE", "", "XXXXXXXX", false);
                    firstName = PokUtils.getAttributeValue(op, "FIRSTNAME", "", "", false);
                    if(firstName != null && firstName.length() > 0)
                    {
                        firstInit = firstName.substring(0,1) + ". ";
                    }
                    else
                    {
                        firstInit = " ";
                    }
                    sb.append("<tr><td align=\"center\" headers=\"language " + key + "\">" + language + "</td>" +
                        "<td headers=\"name " + key + "\">" + firstInit +
                        PokUtils.getAttributeValue(op, "LASTNAME", "", "", false) + "</td>" +
                        "<td align=\"center\" headers=\"userid " + key + "\">" +
                        PokUtils.getAttributeValue(op, "VNETNODE", "", "XXXXXXXX", false) + "/" +
                        PokUtils.getAttributeValue(op, "VNETUID", "", "XXXXXXXX", false) + "</td></tr>");
                }//end of while(j.hasNext())
                valueSet.clear();
                sb.append("</table>");
            }//end of while(i.hasNext())
            keySet.clear();
            // debugBuffer.append("<!-- propHs size = " + hs.size() + " and question number = " + aa + " -->" + NEWLINE);
            if(aa.equals("A0152"))
            {
                Iterator j;
                int count = 0;
                sb.append("<br /><h5>PROPOSAL INSERT DOCUMENT IDS</h5>" +
                    "<table width=\"450\" border=\"1\" cellpadding=\"2\" cellspacing=\"1\" summary=\"layout\">" +
                    "<tr><td><b>PINo:</b></td><td><b>PINo:</b></td><td><b>PINo:</b></td><td><b>PINo:</b></td></tr>");
                j = hs.iterator();
                while(j.hasNext())
                {
                    String propInsId = (String) j.next();
                    if(count%4 == 0)
                    {
                        sb.append("<tr>");
                    }
                    sb.append("<td>" + propInsId + "</td>");
                    if(count%4 == 3)
                    {
                        sb.append("</tr>");
                    }
                    count++;
                }//while(j.hasNext())

                while(count%4 != 0)
                {
                    sb.append("<td>&nbsp;</td>");
                    if(count%4 == 3)
                    {
                        sb.append("</tr>");
                    }
                    count++;
                }
                sb.append("</table>");
            }//end of if(aa.equals("A0152"))
        }//end of if(tm.size() > 0)
        //if (sb.length() > 0) {
        sb.append("<p>Note: This section is deleted at PLET generation time</p>");
        //}
        return (sb.toString());
    }

// A0153 is removed from 19.04. A0154 is renamed to A0153 in 19.04
//   private void deriveA0153(EntityItem ea) {
      // Derive answers to the A0153 question for the Internal Announcement Letter

//      StringBuffer sb = new StringBuffer();
//      TreeMap tm = new TreeMap();

      // Find all AVAIL entities associated with this ANNOUNCEMENT
//      Vector avVct = PokUtils.getAllLinkedEntities(ea, "ANNAVAILA", "AVAIL");

      // Process all AVAIL entities found
//      Iterator avItr = avVct.iterator();
//      while (avItr.hasNext()) {
//          EntityItem av = (EntityItem) avItr.next();
          // Find all SOF entities related to the AVAIL entity.
//          Vector sofVector = PokUtils.getAllLinkedEntities(av, "SOFAVAIL", "SOF");
          // Process all SOF entities found
//          Iterator sofItr = sofVector.iterator();
//          while (sofItr.hasNext()) {
         // get next SOF entity
//         EntityItem sof = (EntityItem)sofItr.next();
//         getSof(sof, tm, "A0153");
//          }
//          sofVector.clear();

          // Find all CMPNT entities related to the AVAIL entity.
//          Vector cmpntVector = PokUtils.getAllLinkedEntities(av, "CMPNTAVAIL", "CMPNT");
          // Process all CMPNT entities found
//          Iterator cmpntItr = cmpntVector.iterator();
//          while (cmpntItr.hasNext()) {
         // get next CMPNT entity
//         EntityItem cmpnt = (EntityItem)cmpntItr.next();
//         getParentSOF(cmpnt, tm, "A0153");
//          }
//          cmpntVector.clear();

          // Find all FEATURE entities related to the AVAIL entity.
//          Vector featureVector = PokUtils.getAllLinkedEntities(av, "FEATUREAVAIL", "FEATURE");
          // Process all FEATURE entities found
//          Iterator featureItr = featureVector.iterator();
//          while (featureItr.hasNext()) {
         // get next FEATURE entity
//         EntityItem feature = (EntityItem)featureItr.next();
//         getGParentSOF(feature, tm, "A0153");
//          }
//          featureVector.clear();
//      }
//      avVct.clear();
//      Set keySet = tm.keySet();
//      Iterator i = keySet.iterator();
//      while (i.hasNext()) {
          // key = mktgname
//          sb.append("<ul><li>" + getMktgName((String)i.next()) + "</li></ul>" + NEWLINE);
//      }
//      tm.clear();

//      pdsAndQ.put("A0153", sb.toString());
//   }

   // A0153 is removed from 19.04. A0154 is renamed to A0153 in 19.04
   //private void deriveA0154(EntityItem ea) {
    private void deriveA0153(EntityItem ea)
    {
        // Derive answers to the A0154 question for the Internal Announcement Letter

        StringBuffer sb = new StringBuffer();
        boolean thDefined = false;
        TreeMap tm = new TreeMap();

        Set keySet;
        Iterator i;
        int n = 1;

        // Find all AVAIL entities associated with this ANNOUNCEMENT
        Vector avVct = PokUtils.getAllLinkedEntities(ea, "ANNAVAILA", "AVAIL");

        // Process all AVAIL entities found
        Iterator avItr = avVct.iterator();
        while(avItr.hasNext())
        {
            Vector sofVector;
            Iterator sofItr;
            Vector cmpntVector;
            Iterator cmpntItr;
            Vector featureVector;
            Iterator featureItr;
            EntityItem av = (EntityItem) avItr.next();
            // Find all SOF entities related to the AVAIL entity.
            sofVector = PokUtils.getAllLinkedEntities(av, "SOFAVAIL", "SOF");
            // Process all SOF entities found
            sofItr = sofVector.iterator();
            while(sofItr.hasNext())
            {
                // get next SOF entity
                EntityItem sof = (EntityItem)sofItr.next();
                //getSof(sof, tm, "A0154");
                getSof(sof, tm, "A0153");
            }
            sofVector.clear();
            // Find all CMPNT entities related to the AVAIL entity.
            cmpntVector = PokUtils.getAllLinkedEntities(av, "CMPNTAVAIL", "CMPNT");
            // Process all CMPNT entities found
            cmpntItr = cmpntVector.iterator();
            while(cmpntItr.hasNext())
            {
                // get next CMPNT entity
                EntityItem cmpnt = (EntityItem)cmpntItr.next();
                //getParentSOF(cmpnt, tm, "A0154");
                getParentSOF(cmpnt, tm, "A0153");
            }
            cmpntVector.clear();
            // Find all FEATURE entities related to the AVAIL entity.
            featureVector = PokUtils.getAllLinkedEntities(av, "FEATUREAVAIL", "FEATURE");
            // Process all FEATURE entities found
            featureItr = featureVector.iterator();
            while(featureItr.hasNext())
            {
                // get next FEATURE entity
                EntityItem feature = (EntityItem)featureItr.next();
                //getGParentSOF(feature, tm, "A0154");
                getGParentSOF(feature, tm, "A0153");
            }
            featureVector.clear();
        }//end of while(avItr.hasNext())
        avVct.clear();
        keySet = tm.keySet();
        i = keySet.iterator();
        while(i.hasNext())
        {
            // key = mktgname
            String keyStr = (String) i.next();
            // value = Offering ID
            String valStr = (String) tm.get(keyStr);
            if(!thDefined)
            {
                //sb.append("<table width=\"550\" border=\"1\" cellpadding=\"2\" cellspacing=\"1\">" +
                //              "<tr><th>Offering ID</th><th>Offering Name</th></tr>");

                //20.10 has new column "Autobahn Project Number"
                //19.04
                sb.append("<table width=\"800\" border=\"1\" cellpadding=\"2\" cellspacing=\"1\" summary=\"offering\">" +
                    "<tr><th width=\"500\" id=\"name\">Offering Name</th><th width=\"100\" id=\"id\">Offering ID</th><th width=\"200\" id=\"autobahn\">Autobahn Project Number</th></tr>");
                thDefined = true;
            }
            //sb.append("<tr><td align=\"center\">" + valStr +
            //    "</td><td>" + getMktgName(keyStr) +
            //    "</td></tr>");

            //20.10 has new column "Autobahn Project Number"
            //19.04
            sb.append("<tr><td valign=\"top\" headers=\"name SOF" + n + "\">" + getMktgName(keyStr) +
                "</td><td align=\"center\" valign=\"top\" headers=\"id SOF" + n + "\">" + parseString(valStr, 1) +
                "</td><td align=\"center\" valign=\"top\" headers=\"autobahn SOF" + n + "\">" + parseString(valStr, 2) +
                "</td></tr>");
            n++;
        }
        tm.clear();
        if(thDefined)
        {
            sb.append("</table>");
        }
        //pdsAndQ.put("A0154", sb.toString());
        pdsAndQ.put("A0153", sb.toString());
    }

    private void getSof(EntityItem sof, TreeMap tm, String aa)
    {
        // Find the MKTGNAME value for the SOF entity and stored in the TreeMap
        // key = MKTGNAME
        // value = OFIDNUMBER
        String mktgName;
        String projNumber;
        Vector ofdevlprojV;

        if(!sof.getEntityType().equals("SOF"))
        {
            return;
        }

        mktgName = PokUtils.getAttributeValue(sof, "MKTGNAME", "", "", false);

        //1 to 1 relationship between SOF and OFDEVLPROJ
        ofdevlprojV = PokUtils.getAllLinkedEntities(sof, "SOFOFDEVLPROJA", "OFDEVLPROJ");
        if(ofdevlprojV.size() > 0)
        {
            projNumber = PokUtils.getAttributeValue((EntityItem) ofdevlprojV.get(0), "PROJNUMBER", "|", "&nbsp;");
        }
        else
        {
            projNumber = "&nbsp;";
        }

        //20.10 has new column "Autobahn Project Number"
        // A0153 is removed from 19.04. A0154 is renamed to A0153 in 19.04
        //if (aa.equals("A0154")) {
        if(aa.equals("A0153"))
        {
            String ofID = PokUtils.getAttributeValue(sof, "OFIDNUMBER", "", "", false);
            if((mktgName != null && mktgName.length() > 0) ||
                (ofID != null && ofID.length() > 0))
            {
                if(mktgName == null || mktgName.length() == 0)
                {
                    mktgName = "&nbsp;";
                }
                if(ofID != null && ofID.length() == 0)
                {
                    ofID = "&nbsp;";
                }
                //tm.put(mktgName + "<:>" + sof.getKey(), ofID);
                tm.put(mktgName + "<:>" + ofID, ofID + "<:>" + projNumber);
            }
        }
        else
        {
            // must be A0170
            if(mktgName != null && mktgName.length() > 0)
            {
                tm.put(mktgName + "<:>" + sof.getKey(), sof);
            }
        }
    }

    private void getParentSOF(EntityItem cmpnt, TreeMap tm, String aa)
    {
        // Find the MKTGNAME of the parent SOF for the input CMPNT entity

        if(!cmpnt.getEntityType().equals("CMPNT"))
        {
            return;
        }

        for(int ui = 0; ui < cmpnt.getUpLinkCount(); ui++)
        {
            EANEntity rcmpnt = cmpnt.getUpLink(ui);
            if(rcmpnt.getEntityType().equals("SOFCMPNT"))
            {
                for(int si = 0; si < rcmpnt.getUpLinkCount(); si++)
                {
                    EANEntity ei = rcmpnt.getUpLink(si);
                    if(ei.getEntityType().equals("SOF"))
                    {
                        getSof((EntityItem) ei, tm, aa);
                    }
                }
            }
        }
    }

    private void getGParentSOF(EntityItem feature, TreeMap tm, String aa)
    {
        // Find the MKTGNAME value for the grandparent SOF of the input feature entity

        if(!feature.getEntityType().equals("FEATURE"))
        {
            return;
        }

        for(int ui = 0; ui < feature.getUpLinkCount(); ui++)
        {
            EANEntity rfeature = feature.getUpLink(ui);
            if(rfeature.getEntityType().equals("CMPNTFEATURE"))
            {
                for(int si = 0; si < rfeature.getUpLinkCount(); si++)
                {
                    EANEntity ei = rfeature.getUpLink(si);
                    if(ei.getEntityType().equals("CMPNT"))
                    {
                        getParentSOF((EntityItem) ei, tm, aa);
                    }
                }
            }
        }
    }

    private String getMktgName(String str)
    {
        //int i = str.indexOf("<:>");

        //return str.substring(0, i);

        return parseString(str, 1);
    }

    //A0155 is renumbered to A0154 in 19.10
    private void deriveA0154(EntityItem ea)
    {
        // Derive answers to the A0154 question for the Internal Announcement Letter

        StringBuffer sb = new StringBuffer();
        boolean thDefined = false;
        TreeMap tm = new TreeMap();
        TreeSet ts = new TreeSet();
        boolean showSofMktgName = false;

        Iterator aItr1;
        int n = 1;

        // Find all AVAIL entities associated with this ANNOUNCEMENT
        Vector avVct = PokUtils.getAllLinkedEntities(ea, "ANNAVAILA", "AVAIL");

        // Process all AVAIL entities found
        Iterator avItr = avVct.iterator();
        while(avItr.hasNext())
        {
            //Vector sofVector;
            //Iterator sofItr;
            Vector cmpVector;
            Iterator cmpItr;
            Vector featureVector;
            Iterator featureItr;

            EntityItem av = (EntityItem) avItr.next();
            // Find all SOF entities related to the AVAIL entity.
            //sofVector = PokUtils.getAllLinkedEntities(av, "SOFAVAIL", "SOF");
            // Process all SOF entities found
            //sofItr = sofVector.iterator();
            //while(sofItr.hasNext())
            //{
                // get next SOF entity
            //    EntityItem sof = (EntityItem)sofItr.next();
            //    getChildCMPNT(sof, tm, ts);
            //}
            //sofVector.clear();
            // Find all CMPNT entities related to the AVAIL entity.
            cmpVector = PokUtils.getAllLinkedEntities(av, "CMPNTAVAIL", "CMPNT");
            // Process all CMPNT entities found
            cmpItr = cmpVector.iterator();
            while(cmpItr.hasNext())
            {
                // get next CMPNT entity
                EntityItem cmp = (EntityItem)cmpItr.next();
                getCmpntInfo(cmp, tm, ts);
            }
            cmpVector.clear();
            // Find all FEATURE entities related to the AVAIL entity.
            featureVector = PokUtils.getAllLinkedEntities(av, "FEATUREAVAIL", "FEATURE");
            // Process all FEATURE entities found
            featureItr = featureVector.iterator();
            while(featureItr.hasNext())
            {
                // get next CMPNT entity
                EntityItem feature = (EntityItem)featureItr.next();
                getParentCMPNT(feature, tm, ts);
            }
            featureVector.clear();
        }//end of while (avItr.hasNext())
        avVct.clear();

        if(tm.size() > 1)
        {
            showSofMktgName = true;
        }

        aItr1 = ts.iterator();
        while(aItr1.hasNext())
        {
            Set keySet;
            Iterator aItr2;
            String sofMktgName = (String) aItr1.next();
            if(showSofMktgName)
            {
                sb.append("<p><b>" + sofMktgName + "</b></p>");
            }

            keySet = tm.keySet();
            aItr2 = keySet.iterator();

            while(aItr2.hasNext())
            {
                // key = mktgname
                String keyStr = (String) aItr2.next();
                String str1 = getFirstStringA0154(keyStr);
                String str2 = getSecondStringA0154(keyStr);
                // value = Offering ID
                String valStr = (String) tm.get(keyStr);
                if(!thDefined)
                {
                    //sb.append("<table width=\"550\" border=\"1\" cellpadding=\"2\" cellspacing=\"1\">" +
                    //          "<tr><th>Component Name</th><th>Component ID</th></tr>");

                    //19.10
                    sb.append("<table width=\"800\" border=\"1\" cellpadding=\"2\" cellspacing=\"1\" summary=\"component\">" +
                        "<tr><th width=\"500\" id=\"name\">Component Name</th><th width=\"100\" id=\"id\">Component ID</th><th width=\"200\" id=\"autobahn\">Autobahn Project Number</th></tr>");

                    thDefined = true;
                }
                if(sofMktgName.equals(str1))
                {
                    sb.append("<tr><td valign=\"top\" headers=\"name cmpnt" + n + "\">" + str2 +
                        "</td><td align=\"center\" valign=\"top\" headers=\"id cmpnt" + n + "\">" + getFirstStringA0154(valStr) +
                        "</td><td align=\"center\" valign=\"top\" headers=\"autobahn cmpnt" + n + "\">" + getSecondStringA0154(valStr) +
                        "</td></tr>");
                    n++;
                }
            }//end of while(aItr2.hasNext())
            if(thDefined)
            {
                sb.append("</table>");
            }
            thDefined = false;
        }//end of while(aItr1.hasNext())

        tm.clear();
        ts.clear();
        pdsAndQ.put("A0154", sb.toString());
    }

    //private void getChildCMPNT(EntityItem sof, TreeMap tm, TreeSet ts)
    //{
        // Find the MKTGNAME value of the child CMPNT for the input SOF entity

    //    if(!sof.getEntityType().equals("SOF"))
    //    {
    //        return;
    //    }

    //    for(int ui = 0; ui < sof.getDownLinkCount(); ui++)
    //    {
    //        EANEntity rsof = sof.getDownLink(ui);
    //        if(rsof.getEntityType().equals("SOFCMPNT"))
    //        {
    //            for(int si = 0; si < rsof.getDownLinkCount(); si++)
    //            {
    //                EANEntity ei = rsof.getDownLink(si);
    //                if(ei.getEntityType().equals("CMPNT"))
    //                {
    //                    getCmpntInfo((EntityItem) ei, tm, ts);
    //                }
    //            }//end of for(int si = 0; si < rsof.getDownLinkCount(); si++)
    //        }//end of if(rsof.getEntityType().equals("SOFCMPNT"))
    //    }//end of for(int ui = 0; ui < sof.getDownLinkCount(); ui++)
    //}

    private void getParentCMPNT(EntityItem feature, TreeMap tm, TreeSet ts)
    {
        // Find the MKTGNAME value for the grandparent SOF of the input feature entity

        if(!feature.getEntityType().equals("FEATURE"))
        {
            return;
        }

        for(int ui = 0; ui < feature.getUpLinkCount(); ui++)
        {
            EANEntity rfeature = feature.getUpLink(ui);
            if(rfeature.getEntityType().equals("CMPNTFEATURE"))
            {
                for(int si = 0; si < rfeature.getUpLinkCount(); si++)
                {
                    EANEntity ei = rfeature.getUpLink(si);
                    if(ei.getEntityType().equals("CMPNT"))
                    {
                        getCmpntInfo((EntityItem) ei, tm, ts);
                    }
                }//end of for(int si = 0; si < rfeature.getUpLinkCount(); si++)
            }//end of if(rfeature.getEntityType().equals("CMPNTFEATURE"))
        }//end of for(int ui = 0; ui < feature.getUpLinkCount(); ui++)
    }

    private void getCmpntInfo(EntityItem cmp, TreeMap tm, TreeSet ts)
    {
        // Find the MKTGNAME and Offering ID value of the input CMPNT entity
        String mktgName;
        String cmpID;
        String cat;
        String sofMktgName;
        String projNumber;
        Vector ofdevlprojV;
        Vector sofVector;

        if(!cmp.getEntityType().equals("CMPNT"))
        {
            return;
        }

        mktgName = PokUtils.getAttributeValue(cmp, "MKTGNAME", "", "", false);
        cmpID = PokUtils.getAttributeValue(cmp, "COMPONENTID", "", "", false);
        cat = PokUtils.getAttributeValue(cmp, "ITSCMPNTCATNAME", "|", "", false);
        sofMktgName = "";
        projNumber = "";

        if((annLobITS) && (null != cat) && (cat.length() > 0))
        {
            mktgName = cat + " " + mktgName;
        }

        //1 to 1 relationship between CMPNT and OFDEVLPROJ
        ofdevlprojV = PokUtils.getAllLinkedEntities(cmp, "CMPNTPRA", "OFDEVLPROJ");
        if(ofdevlprojV.size() > 0)
        {
            projNumber = PokUtils.getAttributeValue((EntityItem) ofdevlprojV.get(0), "PROJNUMBER", "|", "&nbsp;");
        }
        else
        {
            projNumber = "&nbsp;";
        }

        //1 to 1 relationship between CMPNT and SOF
        sofVector = PokUtils.getAllLinkedEntities(cmp, "SOFCMPNT", "SOF");
        if(sofVector.size() > 0)
        {
            sofMktgName = PokUtils.getAttributeValue((EntityItem) sofVector.get(0), "MKTGNAME", "|", "No User Input Found for Attribute MKTGNAME Of Entity SOF");
            ts.add(sofMktgName);

            if((mktgName != null && mktgName.length() > 0) ||
                (cmpID != null && cmpID.length() > 0))
            {
                if(mktgName != null && mktgName.length() == 0)
                {
                    mktgName = "&nbsp;";
                }
                if(cmpID != null && cmpID.length() == 0)
                {
                    cmpID = "&nbsp;";
                }
                mktgName = sofMktgName + "<:>" + mktgName;
                tm.put(mktgName, cmpID + "<:>" + projNumber);
            }
        }//end of if(sofVector.size() > 0)
    }

    private String getFirstStringA0154(String str)
    {
        //int i = str.indexOf("<:>");

        //return str.substring(0, i);

        return parseString(str, 1);
    }

    private String getSecondStringA0154(String str)
    {
        //int i = str.indexOf("<:>");

        //return str.substring(i + 3);

        return parseString(str, 2);
    }

    //A0155 is new 19.10
    private void deriveA0155(EntityItem ea)
    {
        // Derive answers to the A0155 question for the Internal Announcement Letter

        StringBuffer sb = new StringBuffer();
        boolean thDefined = false;
        TreeMap tm = new TreeMap();
        String mktgName = new String();
        String featureID = new String();
        String sofCmpntMktgName = new String();
        TreeSet ts = new TreeSet();
        boolean showSofCmpntMktgName = false;
        String projNumber;
        Vector ofdevlprojV;

        Iterator aItr1;
        int n = 1;

        // Find all AVAIL entities associated with this ANNOUNCEMENT
        Vector avVct = PokUtils.getAllLinkedEntities(ea, "ANNAVAILA", "AVAIL");

        // Process all AVAIL entities found
        Iterator avItr = avVct.iterator();
        while(avItr.hasNext())
        {
            EntityItem av = (EntityItem) avItr.next();
            // Find all FEATURE entities related to the AVAIL entity.
            Vector featureVector = PokUtils.getAllLinkedEntities(av, "FEATUREAVAIL", "FEATURE");

            // Process all FEATURE entities found
            Iterator featureItr = featureVector.iterator();
            while(featureItr.hasNext())
            {
                // get next FEATURE entity
                EntityItem feature = (EntityItem)featureItr.next();
                mktgName = PokUtils.getAttributeValue(feature, "MKTGNAME", "", "&nbsp;", false);
                featureID = PokUtils.getAttributeValue(feature, "FEATURENUMBER", "", "&nbsp;", false);
                sofCmpntMktgName = getCmpntMktgName(feature);
                ts.add(sofCmpntMktgName);
                mktgName = sofCmpntMktgName + "<:>" + mktgName;

                //1 to 1 relationship between FEATURE and OFDEVLPROJ
                ofdevlprojV = PokUtils.getAllLinkedEntities(feature, "FEATUREPRA", "OFDEVLPROJ");
                if(ofdevlprojV.size() > 0)
                {
                    projNumber = PokUtils.getAttributeValue((EntityItem) ofdevlprojV.get(0), "PROJNUMBER", "|", "&nbsp;");
                }
                else
                {
                    projNumber = "&nbsp;";
                }

                tm.put(mktgName, featureID + "<:>" + projNumber);
            }//end of while(featureItr.hasNext())
            featureVector.clear();
        }//end of while(avItr.hasNext())
        avVct.clear();

        if(tm.size() > 1)
        {
            showSofCmpntMktgName = true;
        }

        aItr1 = ts.iterator();
        while(aItr1.hasNext())
        {
            Set keySet;
            Iterator aItr2;

            sofCmpntMktgName = (String) aItr1.next();
            if(showSofCmpntMktgName)
            {
                sb.append("<p><b>" + sofCmpntMktgName + "</b></p>");
            }

            keySet = tm.keySet();
            aItr2 = keySet.iterator();

            while(aItr2.hasNext())
            {
                // key = mktgname
                String keyStr = (String) aItr2.next();
                String str1 = getFirstStringA0154(keyStr);
                String str2 = getSecondStringA0154(keyStr);
                // value = Feature ID
                String valStr = (String) tm.get(keyStr);
                if(!thDefined)
                {
                    sb.append("<table width=\"800\" border=\"1\" cellpadding=\"2\" cellspacing=\"1\" summary=\"feature\">" +
                        "<tr><th width=\"500\" id=\"name\">Feature Name</th><th width=\"100\" id=\"id\">Feature ID</th><th width=\"200\" id=\"autobahn\">Autobahn Project Number</th></tr>");
                    thDefined = true;
                }
                if(sofCmpntMktgName.equals(str1))
                {
                    sb.append("<tr><td valign=\"top\" headers=\"name feature" + n + "\">" + str2 +
                        "</td><td align=\"center\" valign=\"top\" headers=\"id feature" + n + "\">" + parseString(valStr, 1) +
                        "</td><td align=\"center\" valign=\"top\" headers=\"autobahn feature" + n + "\">" + parseString(valStr, 2) +
                        "</td></tr>");
                    n++;
                }
            }//end of while(aItr2.hasNext())
            if(thDefined)
            {
                sb.append("</table>");
            }
            thDefined = false;
        }//end of while(aItr1.hasNext())

        tm.clear();
        ts.clear();
        pdsAndQ.put("A0155", sb.toString());
    }

    private void deriveA0157(EntityItem ea)
    {
        // Derive answers to the A0157 question for the Internal Announcement Letter
        boolean y = false;

        // Find all AVAIL entities associated with this ANNOUNCEMENT
        Vector avVct = PokUtils.getAllLinkedEntities(ea, "ANNAVAILA", "AVAIL");

        // Process all AVAIL entities found
        Iterator avItr = avVct.iterator();
        while(avItr.hasNext())
        {
            EntityItem av = (EntityItem)avItr.next();
            // A0157 - US and CAN only
            if(checkRfaGeoUS(av) || checkRfaGeoCAN(av))
            {
                Vector sofVector;
                Iterator sofItr;
                Vector cmpntVector;
                Iterator cmpntItr;
                Vector featureVector;
                Iterator featureItr;
                // Find all SOF entities related to the AVAIL entity.
                sofVector = PokUtils.getAllLinkedEntities(av, "SOFAVAIL", "SOF");
                // Process all SOF entities found
                sofItr = sofVector.iterator();
                while(sofItr.hasNext())
                {
                    // get next SOF entity
                    EntityItem sof = (EntityItem)sofItr.next();
                    // Find all CATINCL entities related to the SOF entity.
                    Vector catVector = PokUtils.getAllLinkedEntities(sof, "SOFCATINCL", "CATINCL");
                    Iterator catItr = catVector.iterator();
                    while(catItr.hasNext())
                    {
                        // get next CATINCL entity
                        EntityItem cat = (EntityItem) catItr.next();
                        if(PokUtils.isSelected(cat, "CATALOGNAME", SVConstants.XR_CATALOGNAME_321))
                        {
                            pdsAndQ.put("A0157", "Y");
                            y = true;
                        }
                    }
                    catVector.clear();
                }//end of while(sofItr.hasNext())
                sofVector.clear();

                if(!y)
                {
                    // Find all CMPNT entities related to the AVAIL entity.
                    cmpntVector = PokUtils.getAllLinkedEntities(av, "CMPNTAVAIL", "CMPNT");
                    // Process all CMPNT entities found
                    cmpntItr = cmpntVector.iterator();
                    while(cmpntItr.hasNext())
                    {
                        // get next CMPNT entity
                        EntityItem cmpnt = (EntityItem)cmpntItr.next();
                        // Find all CATINCL entities related to the CMPNT entity.
                        Vector catVector = PokUtils.getAllLinkedEntities(cmpnt, "CMPNTCATINCL", "CATINCL");
                        Iterator catItr = catVector.iterator();
                        while(catItr.hasNext())
                        {
                            // get next CATINCL entity
                            EntityItem cat = (EntityItem) catItr.next();
                            if(PokUtils.isSelected(cat, "CATALOGNAME", SVConstants.XR_CATALOGNAME_321))
                            {
                                pdsAndQ.put("A0157", "Y");
                                y = true;
                            }
                        }
                        catVector.clear();
                    }//end of while(cmpntItr.hasNext())
                    cmpntVector.clear();
                }//end of if(!y)

                if(!y)
                {
                    // Find all FEATURE entities related to the AVAIL entity.
                    featureVector = PokUtils.getAllLinkedEntities(av, "FEATUREAVAIL", "FEATURE");
                    // Process all FEATURE entities found
                    featureItr = featureVector.iterator();
                    while(featureItr.hasNext())
                    {
                        // get next FEATURE entity
                        EntityItem feature = (EntityItem)featureItr.next();
                        // Find all CATINCL entities related to the FEATURE entity.
                        Vector catVector = PokUtils.getAllLinkedEntities(feature, "FEATURECATINCL", "CATINCL");
                        Iterator catItr = catVector.iterator();
                        while(catItr.hasNext())
                        {
                            // get next CATINCL entity
                            EntityItem cat = (EntityItem) catItr.next();
                            if(PokUtils.isSelected(cat, "CATALOGNAME", SVConstants.XR_CATALOGNAME_321))
                            {
                                pdsAndQ.put("A0157", "Y");
                                y = true;
                            }
                        }
                        catVector.clear();
                    }//end of while(featureItr.hasNext())
                    featureVector.clear();
                }//end of if(!y)
            }//end of if(checkRfaGeoUS(av) || checkRfaGeoCAN(av))
        }//end of while(avItr.hasNext())
        avVct.clear();

        if(!y)
        {
            pdsAndQ.put("A0157", "N");
        }
    }

    private void deriveA0158(EntityItem ea)
    {
        // Derive answers to the A0158 question for the Internal Announcement Letter
        String mktgName = new String();
        String taxonomy = new String();
        StringBuffer sb = new StringBuffer();
        TreeMap sofTm = new TreeMap();
        TreeMap cmpntTm = new TreeMap();
        TreeMap featureTm = new TreeMap();

        // Find all AVAIL entities associated with this ANNOUNCEMENT
        Vector avVct = PokUtils.getAllLinkedEntities(ea, "ANNAVAILA", "AVAIL");

        // Process all AVAIL entities found
        Iterator avItr = avVct.iterator();
        while(avItr.hasNext())
        {
            EntityItem av = (EntityItem)avItr.next();
            // A0158 - US and CAN only
            if(checkRfaGeoUS(av) || checkRfaGeoCAN(av))
            {
                Vector sofVector;
                Iterator sofItr;
                Vector cmpntVector;
                Iterator cmpntItr;
                Vector featureVector;
                Iterator featureItr;
                // Find all SOF entities related to the AVAIL entity.
                sofVector = PokUtils.getAllLinkedEntities(av, "SOFAVAIL", "SOF");
                // Process all SOF entities found
                sofItr = sofVector.iterator();
                while(sofItr.hasNext())
                {
                    Vector catVector;
                    Iterator catItr;
                    // get next SOF entity
                    EntityItem sof = (EntityItem)sofItr.next();
                    mktgName = PokUtils.getAttributeValue(sof, "MKTGNAME", "", "No user input found", false);
                    // Find all CATINCL entities related to the SOF entity.
                    catVector = PokUtils.getAllLinkedEntities(sof, "SOFCATINCL", "CATINCL");
                    catItr = catVector.iterator();
                    while(catItr.hasNext())
                    {
                        // get next CATINCL entity
                        EntityItem cat = (EntityItem) catItr.next();
                        if(PokUtils.isSelected(cat, "CATALOGNAME", SVConstants.XR_CATALOGNAME_321))
                        {
                            taxonomy = PokUtils.getAttributeValue(cat, "CATALOGTAXONOMY", "", "No user input found", false);
                            if(sofTm.size() == 0)
                            {
                                HashSet sofHs1 = new HashSet();
                                sofHs1.add(taxonomy);
                                sofTm.put(mktgName, sofHs1);
                            }
                            else
                            {
                                HashSet sofValueSet = (HashSet) sofTm.get(mktgName);
                                if(sofValueSet != null)
                                {
                                    sofTm.remove(mktgName);
                                    sofValueSet.add(taxonomy);
                                    sofTm.put(mktgName, sofValueSet);
                                }
                                else
                                {
                                    HashSet sofHs2 = new HashSet();
                                    sofHs2.add(taxonomy);
                                    sofTm.put(mktgName, sofHs2);
                                }
                            }
                        }//end of if(PokUtils.isSelected(cat, "CATALOGNAME", SVConstants.XR_CATALOGNAME_321))
                    }//end of while(catItr.hasNext())
                    catVector.clear();
                }//end of while(sofItr.hasNext())
                sofVector.clear();
                // Find all CMPNT entities related to the AVAIL entity.
                cmpntVector = PokUtils.getAllLinkedEntities(av, "CMPNTAVAIL", "CMPNT");
                // Process all CMPNT entities found
                cmpntItr = cmpntVector.iterator();
                while(cmpntItr.hasNext())
                {
                    Vector catVector;
                    Iterator catItr;
                    // get next CMPNT entity
                    EntityItem cmpnt = (EntityItem)cmpntItr.next();
                    mktgName = getSofMktgName(cmpnt) + " " +
                        PokUtils.getAttributeValue(cmpnt, "MKTGNAME", "", "No user input found", false);
                    // Find all CATINCL entities related to the CMPNT entity.
                    catVector = PokUtils.getAllLinkedEntities(cmpnt, "CMPNTCATINCL", "CATINCL");
                    catItr = catVector.iterator();
                    while(catItr.hasNext())
                    {
                        // get next CATINCL entity
                        EntityItem cat = (EntityItem) catItr.next();
                        if(PokUtils.isSelected(cat, "CATALOGNAME", SVConstants.XR_CATALOGNAME_321))
                        {
                            taxonomy = PokUtils.getAttributeValue(cat, "CATALOGTAXONOMY", "", "No user input found", false);
                            if(cmpntTm.size() == 0)
                            {
                                HashSet cmpntHs1 = new HashSet();
                                cmpntHs1.add(taxonomy);
                                cmpntTm.put(mktgName, cmpntHs1);
                            }
                            else
                            {
                                HashSet cmpntValueSet = (HashSet) cmpntTm.get(mktgName);
                                if(cmpntValueSet != null)
                                {
                                    cmpntTm.remove(mktgName);
                                    cmpntValueSet.add(taxonomy);
                                    cmpntTm.put(mktgName, cmpntValueSet);
                                }
                                else
                                {
                                    HashSet cmpntHs2 = new HashSet();
                                    cmpntHs2.add(taxonomy);
                                    cmpntTm.put(mktgName, cmpntHs2);
                                }
                            }
                        }//end of if(PokUtils.isSelected(cat, "CATALOGNAME", SVConstants.XR_CATALOGNAME_321))
                    }//end of while(catItr.hasNext())
                    catVector.clear();
                }//end of while(cmpntItr.hasNext())
                cmpntVector.clear();
                // Find all FEATURE entities related to the AVAIL entity.
                featureVector = PokUtils.getAllLinkedEntities(av, "FEATUREAVAIL", "FEATURE");
                // Process all FEATURE entities found
                featureItr = featureVector.iterator();
                while(featureItr.hasNext())
                {
                    Vector catVector;
                    Iterator catItr;
                    // get next FEATURE entity
                    EntityItem feature = (EntityItem)featureItr.next();
                    mktgName = getCmpntMktgName(feature) + " " +
                        PokUtils.getAttributeValue(feature, "MKTGNAME", "", "No user input found", false);
                    // Find all CATINCL entities related to the FEATURE entity.
                    catVector = PokUtils.getAllLinkedEntities(feature, "FEATURECATINCL", "CATINCL");
                    catItr = catVector.iterator();
                    while(catItr.hasNext())
                    {
                        // get next CATINCL entity
                        EntityItem cat = (EntityItem) catItr.next();
                        if(PokUtils.isSelected(cat, "CATALOGNAME", SVConstants.XR_CATALOGNAME_321))
                        {
                            taxonomy = PokUtils.getAttributeValue(cat, "CATALOGTAXONOMY", "", "No user input found", false);
                            if(featureTm.size() == 0)
                            {
                                HashSet featureHs1 = new HashSet();
                                featureHs1.add(taxonomy);
                                featureTm.put(mktgName, featureHs1);
                            }
                            else
                            {
                                HashSet featureValueSet = (HashSet) featureTm.get(mktgName);
                                if(featureValueSet != null)
                                {
                                    featureTm.remove(mktgName);
                                    featureValueSet.add(taxonomy);
                                    featureTm.put(mktgName, featureValueSet);
                                }
                                else
                                {
                                    HashSet featureHs2 = new HashSet();
                                    featureHs2.add(taxonomy);
                                    featureTm.put(mktgName, featureHs2);
                                }
                            }
                        }//end of if(PokUtils.isSelected(cat, "CATALOGNAME", SVConstants.XR_CATALOGNAME_321))
                    }//end of while(catItr.hasNext())
                    catVector.clear();
                }//end of while(featureItr.hasNext())
                featureVector.clear();
            }//end of if(checkRfaGeoUS(av) || checkRfaGeoCAN(av))
        }//end of while(avItr.hasNext())
        avVct.clear();
        sb.append(formatA0158Answer(sofTm) + formatA0158Answer(cmpntTm) + formatA0158Answer(featureTm));

        pdsAndQ.put("A0158", sb.toString());
        sofTm.clear();
        cmpntTm.clear();
        featureTm.clear();
    }

    private String formatA0158Answer(TreeMap tm)
    {
        //  Set answer to the A0158 question for the Internal Announcement Letter.
        StringBuffer sb = new StringBuffer();

        if(tm.size() > 0)
        {
            Set keySet = tm.keySet();
            Iterator i = keySet.iterator();
            while(i.hasNext())
            {
                Iterator j;
                String keyStr = (String) i.next();
                // Get the value from the queue
                HashSet valueSet = (HashSet) tm.get(keyStr);
                if(includeHdr)
                {
                    sb.append("<br /><p><b>" + keyStr + "</b></p>");
                }
                sb.append("<ul>");
                j = valueSet.iterator();
                while(j.hasNext())
                {
                    String taxonomy = (String) j.next();
                    sb.append("<li>" + taxonomy + "</li>");
                }
                sb.append("</ul>");
                valueSet.clear();
            }
            keySet.clear();
        }
        return (sb.toString());
    }

    private void deriveA0170(EntityItem ea)
    {
        // Derive answers to the A0170 question for the Internal Announcement Letter

        TreeMap sofTm = new TreeMap();

        // Find all AVAIL entities associated with this ANNOUNCEMENT
        Vector avVct = PokUtils.getAllLinkedEntities(ea, "ANNAVAILA", "AVAIL");

        // Process all AVAIL entities found
        Iterator avItr = avVct.iterator();
        while(avItr.hasNext())
        {
            Vector sofVector;
            Iterator sofItr;
            Vector cmpntVector;
            Iterator cmpntItr;
            EntityItem av = (EntityItem)avItr.next();
            // Find all SOF entities related to the AVAIL entity.
            sofVector = PokUtils.getAllLinkedEntities(av, "SOFAVAIL", "SOF");
            // Process all SOF entities found
            sofItr = sofVector.iterator();
            while(sofItr.hasNext())
            {
                // get next SOF entity
                EntityItem sof = (EntityItem)sofItr.next();
                getSof(sof, sofTm, "A0170");
            }
            sofVector.clear();
            // Find all CMPNT entities related to the AVAIL entity.
            cmpntVector = PokUtils.getAllLinkedEntities(av, "CMPNTAVAIL", "CMPNT");
            // Process all CMPNT entities found
            cmpntItr = cmpntVector.iterator();
            while(cmpntItr.hasNext())
            {
                // get next CMPNT entity
                EntityItem cmpnt = (EntityItem)cmpntItr.next();
                getParentSOF(cmpnt, sofTm, "A0170");
            }
            cmpntVector.clear();

            //CR1123044433
            //According to James Gardner: Don't need to do this for FEATURE
            // Find all FETURE entities related to the AVAIL entity.
            //Vector featureVector = PokUtils.getAllLinkedEntities(av, "FEATUREAVAIL", "FEATURE");
            // Process all FEATURE entities found
            //Iterator featureItr = featureVector.iterator();
            //while(featureItr.hasNext())
            //{
            // get next FEATURE entity
            //   EntityItem feature = (EntityItem)featureItr.next();
            //   getGParentSOF(feature, sofTm, "A0170");
            //}
            //featureVector.clear();
        }
        avVct.clear();
        pdsAndQ.put("A0170", formatA0170Answer(sofTm));
    }

    private String formatA0170Answer(TreeMap tm)
    {
        //  Set answer to the A0170 question for the Internal Announcement Letter.
        StringBuffer sb1 = new StringBuffer();
        StringBuffer sb2 = new StringBuffer();
        StringBuffer sb3 = new StringBuffer();
        StringBuffer sb4 = new StringBuffer();

        if(tm.size() > 0)
        {
            Set keySet;
            Iterator i;

            sb1.append("<br /><table width=\"750\" border=\"1\" cellpadding=\"2\" cellspacing=\"1\" summary=\"layout\">" +
                "<tr><td><b>Offering ID</b></td><td><b>Description</b></td><td><b>Primary Brand Code</b></td></tr>");
            sb2.append("<br /><table width=\"750\" border=\"1\" cellpadding=\"2\" cellspacing=\"1\" summary=\"layout\">" +
                "<tr><td><b>Product Family Code</b></td><td><b>OM Brand Code</b></td>" +
                "<td><b>OM Product Family Code</b></td><td><b>BPDB Brand Code</b></td></tr>");
            sb3.append("<br /><table width=\"750\" border=\"1\" cellpadding=\"2\" cellspacing=\"1\" summary=\"layout\">" +
                "<tr><td><b>Offering ID</b></td><td><b>Material Account Group</b></td>" +
                "<td><b>Assortment Module</b></td>" + "<td><b>Development Division</b></td></tr>");
            sb4.append("<br /><table width=\"750\" border=\"1\" cellpadding=\"2\" cellspacing=\"1\" summary=\"layout\">" +
                "<tr><td><b>Amortization Start</b></td><td><b>Amortization Length</b></td></tr>");

            keySet = tm.keySet();
            i = keySet.iterator();
            while(i.hasNext())
            {
                Vector ofdevlprojVector;
                Vector pricefininfoVector;
                String key = (String) i.next();
                // Get the value from the queue
                EntityItem sof = (EntityItem) tm.get(key);
                // Find all GBT entities associated with the SOF entity.
                Vector gbtVector = PokUtils.getAllLinkedEntities(sof, "SOFGBTA", "GBT");

                debugBuffer.append("<!-- sof.getUpLinkCount() = " + sof.getUpLinkCount() + "-->" + NEWLINE);
                debugBuffer.append("<!-- sof.getDownLinkCount() = " + sof.getDownLinkCount() + "-->" + NEWLINE);
                for (int ui=0; ui<sof.getDownLinkCount(); ui++)
                {
                    EANEntity entityLink = sof.getDownLink(ui);
                    debugBuffer.append("<!-- entityLink.getEntityType() = " + entityLink.getEntityType() + "-->" + NEWLINE);
                }

                debugBuffer.append("<!-- gbtVector's size = " + gbtVector.size() + "-->" + NEWLINE);

                if(gbtVector.isEmpty())
                {
                    sb1.append("<tr><td align=\"center\">" + PokUtils.getAttributeValue(sof, "OFIDNUMBER", "", "&nbsp;", false) +
                        "</td><td>" + PokUtils.getAttributeValue(sof, "GBNAME", "", "&nbsp;", false) +
                        "</td><td>&nbsp;</td></tr>");
                    sb2.append("<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>");
                }
                else
                {
                    for (int c=0; c<gbtVector.size();c++)
                    {
                        // get next GBT entity
                        EntityItem gbt = (EntityItem) gbtVector.elementAt(c);
                        sb1.append("<tr><td align=\"center\">" + PokUtils.getAttributeValue(sof, "OFIDNUMBER", "", "&nbsp;", false) +
                            "</td><td>" + PokUtils.getAttributeValue(sof, "GBNAME", "", "&nbsp;", false) +
                            "</td><td>" + PokUtils.getAttributeValue(gbt, "SAPPRIMBRANDCODE", "", "&nbsp;", false) +
                            "</td></tr>");

                        sb2.append("<tr><td>" + PokUtils.getAttributeValue(gbt, "SAPPRODFAMCODE", "", "&nbsp;", false) +
                            "</td><td>" + PokUtils.getAttributeValue(gbt, "OMBRANDCODE", "", "&nbsp;", false) +
                            "</td><td>" + PokUtils.getAttributeValue(gbt, "OMPRODFAMCODE", "", "&nbsp;", false) +
                            "</td><td>" + PokUtils.getAttributeValue(gbt, "BPDBBRANDCODE", "", "&nbsp;", false) +
                            "</td></tr>");
                    }
                }//end of else
                ofdevlprojVector = PokUtils.getAllLinkedEntities(sof, "SOFOFDEVLPROJA", "OFDEVLPROJ");
                if(ofdevlprojVector.size() > 0)
                {
                    for(int j = 0; j < ofdevlprojVector.size(); j++)
                    {
                        EntityItem ei = (EntityItem) ofdevlprojVector.get(j);
                        sb3.append("<tr><td align=\"center\">" + PokUtils.getAttributeValue(sof, "OFIDNUMBER", "", "&nbsp;", false) +
                            "</td><td>" + PokUtils.getAttributeValue(sof, "MATACCGRP", "", "&nbsp;", false) +
                            "</td><td>" + PokUtils.getAttributeValue(sof, "ASSORTMODULE", "", "&nbsp;", false) +
                            "</td><td>" + PokUtils.getAttributeValue(ei, "DEVLDIV", "", "&nbsp;", false) + "</td></tr>");
                    }
                }
                else
                {
                    sb3.append("<tr><td align=\"center\">" + PokUtils.getAttributeValue(sof, "OFIDNUMBER", "", "&nbsp;", false) +
                        "</td><td>" + PokUtils.getAttributeValue(sof, "MATACCGRP", "", "&nbsp;", false) +
                        "</td><td>" + PokUtils.getAttributeValue(sof, "ASSORTMODULE", "", "&nbsp;", false) +
                        "</td><td>&nbsp;</td></tr>");
                }
                pricefininfoVector = PokUtils.getAllLinkedEntities(sof, "SOFPRICE", "PRICEFININFO");
                if(pricefininfoVector.size () > 0)
                {
                    for(int j = 0; j < pricefininfoVector.size(); j++)
                    {
                        EntityItem ei = (EntityItem) pricefininfoVector.get(j);
                        sb4.append("<tr><td align=\"center\">" + PokUtils.getAttributeValue(ei, "AMORTIZATIONSTART", "", "&nbsp;", false) +
                            "</td><td>" + PokUtils.getAttributeValue(ei, "AMORTIZATIONLENGTH", "", "&nbsp;", false) + "</td></tr>");
                    }
                }
                else
                {
                    sb4.append("<tr><td>&nbsp;</td><td>&nbsp;</td></tr>");
                }
                gbtVector.clear();
                ofdevlprojVector.clear();
                pricefininfoVector.clear();
            }//end of while (i.hasNext())
            sb1.append("</table>");
            sb2.append("</table>");
            sb3.append("</table>");
            sb4.append("</table>");
            keySet.clear();
        }//end of if (tm.size() > 0)
        return (sb1.toString() + sb2.toString() + sb3.toString() + sb4.toString());
    }

    private void deriveA0181(EntityItem ea)
    {
        // Derive answers to the A0181 question for the Internal Announcement Letter
        boolean y = false;

        // Find all AVAIL entities associated with this ANNOUNCEMENT
        Vector avVct = PokUtils.getAllLinkedEntities(ea, "ANNAVAILA", "AVAIL");

        // Process all AVAIL entities found
        Iterator avItr = avVct.iterator();
        while(avItr.hasNext())
        {
            Vector cmpVector;
            Iterator cmpItr;
            Vector featureVector;
            Iterator featureItr;
            EntityItem av = (EntityItem) avItr.next();

            // Find all CMPNT entities related to the AVAIL entity.
            cmpVector = PokUtils.getAllLinkedEntities(av, "CMPNTAVAIL", "CMPNT");
            // Process all CMPNT entities found
            cmpItr = cmpVector.iterator();
            while(cmpItr.hasNext())
            {
                // get next CMPNT entity
                EntityItem cmp = (EntityItem)cmpItr.next();
                Vector finofVector = PokUtils.getAllLinkedEntities(cmp, "CMPNTFINOF", "FINOF");
                if(finofVector.size() > 0)
                {
                    pdsAndQ.put("A0181", "Y");
                    finofVector.clear();
                    y = true;
                }
                //cmpVector.clear(); this causes java.util.ConcurrentModificationException
            }
            cmpVector.clear();

            if(!y)
            {
                // Find all FEATURE entities related to the AVAIL entity.
                featureVector = PokUtils.getAllLinkedEntities(av, "FEATUREAVAIL", "FEATURE");
                // Process all FEATURE entities found
                featureItr = featureVector.iterator();
                while(featureItr.hasNext())
                {
                    // get next FEATURE entity
                    EntityItem feature = (EntityItem)featureItr.next();
                    Vector finofVector = PokUtils.getAllLinkedEntities(feature, "FEATUREFINOF", "FINOF");
                    if(finofVector.size() > 0)
                    {
                        pdsAndQ.put("A0181", "Y");
                        finofVector.clear();
                        y = true;
                    }
                }
                featureVector.clear();
            }//end of if(!y)
        }//end of while(avItr.hasNext())
        avVct.clear();

        if(!y)
        {
            pdsAndQ.put("A0181", "N");
        }
    }

    private void deriveA0184_A0186(EntityItem ea, String aa, String attr)
    {
        // Derive answers to the A0184 and A0186 questions for the Internal Announcement Letter
        boolean y = false;
        String attrValue = new String();

        // Find all AVAIL entities associated with this ANNOUNCEMENT
        Vector avVct = PokUtils.getAllLinkedEntities(ea, "ANNAVAILA", "AVAIL");

        // Process all AVAIL entities found
        Iterator avItr = avVct.iterator();
        while(avItr.hasNext())
        {
            Vector cmpVector;
            Iterator cmpItr;
            Vector featureVector;
            Iterator featureItr;

            EntityItem av = (EntityItem) avItr.next();

            // Find all CMPNT entities related to the AVAIL entity.
            cmpVector = PokUtils.getAllLinkedEntities(av, "CMPNTAVAIL", "CMPNT");
            // Process all CMPNT entities found
            cmpItr = cmpVector.iterator();
            while(cmpItr.hasNext())
            {
                // get next CMPNT entity
                EntityItem cmp = (EntityItem)cmpItr.next();
                Vector finofVector = PokUtils.getAllLinkedEntities(cmp, "CMPNTFINOF", "FINOF");
                // Process all FINOF entities found
                Iterator finofItr = finofVector.iterator();
                while(finofItr.hasNext())
                {
                    // get next FINOF entity
                    EntityItem finof = (EntityItem) finofItr.next();
                    attrValue = PokUtils.getAttributeValue(finof, attr, "", "", false);
                    if(attrValue != null && attrValue.length() > 0)
                    {
                        pdsAndQ.put(aa, "Y");
                        y = true;
                    }
                }
                finofVector.clear();
            }
            cmpVector.clear();

            if(!y)
            {
                // Find all FEATURE entities related to the AVAIL entity.
                featureVector = PokUtils.getAllLinkedEntities(av, "FEATUREAVAIL", "FEATURE");
                // Process all FEATURE entities found
                featureItr = featureVector.iterator();
                while(featureItr.hasNext())
                {
                    // get next FEATURE entity
                    EntityItem feature = (EntityItem)featureItr.next();
                    Vector finofVector = PokUtils.getAllLinkedEntities(feature, "FEATUREFINOF", "FINOF");
                    // Process all FINOF entities found
                    Iterator finofItr = finofVector.iterator();
                    while(finofItr.hasNext())
                    {
                        // get next FINOF entity
                        EntityItem finof = (EntityItem) finofItr.next();
                        attrValue = PokUtils.getAttributeValue(finof, attr, "", "", false);
                        if(attrValue != null && attrValue.length() > 0)
                        {
                            pdsAndQ.put(aa, "Y");
                            y = true;
                        }
                    }
                    finofVector.clear();
                }
                featureVector.clear();
            }//end of if(!y)
        }
        avVct.clear();

        if(!y)
        {
            pdsAndQ.put(aa, "N");
        }
    }

    private void deriveIFGAnswer(EntityItem ea, String aa, String attr)
    {
        // Derive one answer to the following question for the Internal Announcement Letter:
        // A0182, A0183, A0185, A0187, A0188, A0189, A0190

        StringBuffer sb = new StringBuffer();
        TreeMap cmpntTm = new TreeMap();
        TreeMap featureTm = new TreeMap();
        int cmpntSize = 0;
        int featureSize = 0;
        boolean hdr = true;
        String instStr = new String();
        String mktgname = new String();

        // Find all AVAIL entities associated with this ANNOUNCEMENT
        Vector avVct = PokUtils.getAllLinkedEntities(ea, "ANNAVAILA", "AVAIL");

        // Process all AVAIL entities found
        Iterator avItr = avVct.iterator();
        while(avItr.hasNext())
        {
            Vector cmpntVector;
            Iterator cmpntItr;
            Vector featureVector;
            Iterator featureItr;
            EntityItem av = (EntityItem) avItr.next();

            // Find all CMPNT entities related to the AVAIL entity.
            cmpntVector = PokUtils.getAllLinkedEntities(av, "CMPNTAVAIL", "CMPNT");
            cmpntSize += cmpntVector.size();
            // Process all CMPNT entities found
            cmpntItr = cmpntVector.iterator();
            while(cmpntItr.hasNext())
            {
                Vector finofVector;
                Iterator finofItr;
                // get next CMPNT entity
                EntityItem cmpnt = (EntityItem)cmpntItr.next();
                mktgname = getSofMktgName(cmpnt) + " " +
                    PokUtils.getAttributeValue(cmpnt, "MKTGNAME", "", "No user input found", false);
                finofVector = PokUtils.getAllLinkedEntities(cmpnt, "CMPNTFINOF", "FINOF");
                // Process all FINOF entities found
                finofItr = finofVector.iterator();
                while(finofItr.hasNext())
                {
                    // get next FINOF entity
                    EntityItem finof = (EntityItem) finofItr.next();
                    instStr = PokUtils.getAttributeValue(finof, attr, "", "", false);
                    if(instStr != null && instStr.length() > 0)
                    {
                        cmpntTm.put(mktgname, instStr);
                    }
                }
                finofVector.clear();
            }//end of while(cmpntItr.hasNext())
            cmpntVector.clear();
            // Find all FEATURE entities related to the AVAIL entity.
            featureVector = PokUtils.getAllLinkedEntities(av, "FEATUREAVAIL", "FEATURE");
            featureSize += featureVector.size();
            // Process all FEATURE entities found
            featureItr = featureVector.iterator();
            while(featureItr.hasNext())
            {
                Vector finofVector;
                Iterator finofItr;
                // get next FEATURE entity
                EntityItem feature = (EntityItem)featureItr.next();
                mktgname = getCmpntMktgName(feature) + " " +
                    PokUtils.getAttributeValue(feature, "MKTGNAME", "", "", false);
                finofVector = PokUtils.getAllLinkedEntities(feature, "FEATUREFINOF", "FINOF");
                // Process all FINOF entities found
                finofItr = finofVector.iterator();
                while(finofItr.hasNext())
                {
                    // get next FINOF entity
                    EntityItem finof = (EntityItem) finofItr.next();
                    instStr = PokUtils.getAttributeValue(finof, attr, "", "", false);
                    if(instStr != null && instStr.length() > 0)
                    {
                        featureTm.put(mktgname, instStr);
                    }
                }
                finofVector.clear();
            }//end of while(featureItr.hasNext())
            featureVector.clear();
        }//end of while(avItr.hasNext())
        avVct.clear();
        if(cmpntSize + featureSize > 0)
        {
            if(cmpntSize + featureSize == 1)
            {
                hdr = false;
            }
            if(cmpntTm.size() > 0)
            {
                sb.append("<p><b>Component Offerings</b></p>" + formatIGFAnswer(hdr, cmpntTm, aa));
            }
            if(featureTm.size() > 0)
            {
                sb.append("<p><b>Features</b></p>" + formatIGFAnswer(hdr, featureTm, aa));
            }
        }

        pdsAndQ.put(aa, sb.toString());
        cmpntTm.clear();
        featureTm.clear();
    }

    private String formatIGFAnswer(boolean ih, TreeMap tm, String aa)
    {
        //  Set answer to the specified question for the Internal Announcement Letter.
        StringBuffer sb = new StringBuffer();

        Set keySet = tm.keySet();
        Iterator i = keySet.iterator();
        while(i.hasNext())
        {
            String keyStr = (String) i.next();
            // Get the value (should be already in XML format) from the TreeMap
            String valueStr = (String) tm.get(keyStr);
            if(ih)
            {
                sb.append("<br /><ul><li><b>" + keyStr + "</b></li>");
            }
            if(aa.equals("A0188"))
            {
                sb.append("<p>");
            }
            sb.append(valueStr);
            if (aa.equals("A0188"))
            {
                sb.append(" Months</p>");
            }
            if(ih)
            {
                sb.append("</ul>");
            }
        }
        keySet.clear();
        return (sb.toString());
    }

    private void deriveA0208(EntityItem ea)
    {
        // Derive answers to question 208 for the Internal Announcement Letter.
        // NOTE - The logic is copied from the WWCustLetter_IGSSVS.java file

        Vector availV;
        StringBuffer value;
        Hashtable htA0208 = new Hashtable();

        // Find all AVAIL entities associated with this ANNOUNCEMENT
        Vector availVtr = PokUtils.getAllLinkedEntities(ea, "ANNAVAILA", "AVAIL");

        //Find only AVAIL entities with AVAILTYPE = Planned Availibility
        Hashtable tempHT = new Hashtable();
        tempHT.put("AVAILTYPE", SVConstants.XR_AVAILTYPE_146);
        availV = PokUtils.getEntitiesWithMatchedAttr(availVtr, tempHT);

        getHTA0208(availV, "SOFAVAIL", "SOF", htA0208);
        getHTA0208(availV, "CMPNTAVAIL", "CMPNT", htA0208);
        getHTA0208(availV, "FEATUREAVAIL", "FEATURE", htA0208);
        availV.clear();
        availVtr.clear();

        value = new StringBuffer();
        // log("A0208 - before formatting answer");
        if((htA0208.size()) > 0)
        {
            value.append(getDataA0208_method2(htA0208).toString());
        }
        pdsAndQ.put("A0208", value.toString());
        htA0208.clear();
    }

    private void getHTA0208(Vector srcVct, String linkType, String destType, Hashtable ht)
    {
        Iterator srcItr = srcVct.iterator();
        while(srcItr.hasNext())
        {
            EntityItem entityItem = (EntityItem) srcItr.next();
            getHTA0208(entityItem, linkType, destType, ht);
        }
    }

    private void getHTA0208(EntityItem entityItem, String linkType, String destType, Hashtable ht)
    {
        //This method is based on PokUtils.getLinkedEntities()

        String mktgname = new String();
        if(entityItem == null)
        {
            return;
        }

        for(int ui = 0; ui < entityItem.getUpLinkCount(); ui++)
        {
            EANEntity entityLink = entityItem.getUpLink(ui);
            if(entityLink.getEntityType().equals(linkType))
            {
                // check for destination entity as an uplink
                for(int i = 0; i < entityLink.getUpLinkCount(); i++)
                {
                    EANEntity entity = entityLink.getUpLink(i);
                    if(entity.getEntityType().equals(destType))
                    {
                        String key;
                        if(entity.getEntityType().equals("SOF"))
                        {
                            mktgname = PokUtils.getAttributeValue((EntityItem)entity, "MKTGNAME", "|", " ", false);
                        }
                        else
                        {
                            if(entity.getEntityType().equals("CMPNT"))
                            {
                                mktgname = getSofMktgName((EntityItem)entity) + " " +
                                    PokUtils.getAttributeValue((EntityItem)entity, "MKTGNAME", "|", " ", false);
                            }
                            else
                            {
                                mktgname = getCmpntMktgName((EntityItem)entity) + " " +
                                    PokUtils.getAttributeValue((EntityItem)entity, "MKTGNAME", "|", " ", false);
                            }
                        }
                        key = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", "|", " ", false) +
                            "<:>" + entity.getKey() +
                            "<:>" + mktgname;

                        if(null == ht.get(key))
                        {
                            ht.put(key, new StringBuffer("NNNNN"));
                        }
                        if(checkRfaGeoUS(entityItem))
                        {
                            updateGeo(key, "US", ht);
                        }
                        if(checkRfaGeoAP(entityItem))
                        {
                            updateGeo(key, "AP", ht);
                        }
                        if(checkRfaGeoLA(entityItem))
                        {
                            updateGeo(key, "LA", ht);
                        }
                        if(checkRfaGeoCAN(entityItem))
                        {
                            updateGeo(key, "CAN", ht);
                        }
                        if(checkRfaGeoEMEA(entityItem))
                        {
                            updateGeo(key, "EMEA", ht);
                        }
                    }//end of if(entity.getEntityType().equals(destType))
                }//end of for(int i = 0; i < entityLink.getUpLinkCount(); i++)
            }//end of if(entityLink.getEntityType().equals(linkType))
        }//end for(int ui = 0; ui < entityItem.getUpLinkCount(); ui++)
    }

    private void updateGeo(String key, String geo, Hashtable ht)
    {
        StringBuffer value = (StringBuffer) ht.get(key);

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

//    private StringBuffer getDataA0208_method1(Hashtable ht)
//    {
//        TreeMap tm = new TreeMap();

//        StringBuffer sBuffer = new StringBuffer();

//        String lastGeo = "";
//        String currentGeo = "";

//        TreeSet aTreeSet = new TreeSet();

//        if(ht.size() > 0)
//        {
//            Set keySet = ht.keySet();
//            Iterator itr = keySet.iterator();
//            while(itr.hasNext())
//            {
//                String key = (String) itr.next();
//                String date = getDateA0208(key);
//                String entityKey = getEntityKeyA0208(key);
//                String mktgName = getMktgNameA0208(key);
//                String geo = getGeo(key, ht);
//                tm.put(date + "<:>" + geo + " " + "<:>" + mktgName, geo);
                //tm.put(date + "<:>" + geo + "<:>" + mktgName + "<:>" + entityKey, geo);

//                aTreeSet.add(date + "<:>" + geo);
//            }//end of while(itr.hasNext())

//            Iterator aItr1 = aTreeSet.iterator();
//            while(aItr1.hasNext())
//            {
//                String str1 = (String) aItr1.next();
//                int i = str1.indexOf("<:>");
//                currentGeo = str1.substring(i + 3);
//                if(!currentGeo.equals("WW"))
//                {
//                    sBuffer.append("<p><b>" + currentGeo + "---&gt;</b><br />" + NEWLINE);
//                }
//                else
//                {
//                    sBuffer.append("<p>" + NEWLINE);
//                }
//                sBuffer.append(formatDate(getDateA0208(str1)) + "<br />" + NEWLINE);

//                keySet = tm.keySet();
//                Iterator aItr2 = keySet.iterator();
//                while (aItr2.hasNext())
//                {
//                    String key = (String) aItr2.next();
//                    String date = getDateA0208(key);
//                    String geo = (String) tm.get(key);
//                    geo = geo.trim();
//                    String mktgName = getMktgNameA0208(key);
//                    String str2 = date + "<:>" + geo;
//                    if(str1.equals(str2))
//                    {
//                        sBuffer.append(mktgName + "<br />" + NEWLINE);
//                    }
//                }//end of while (aItr2.hasNext())
//                if(!currentGeo.equals("WW"))
//                {
//                    sBuffer.append("<b>&lt;---" + currentGeo + "</b></p>" + NEWLINE);
//                }
//                else
//                {
//                    sBuffer.append("</p>" + NEWLINE);
//                }
//            }//end of while(aItr1.hasNext())
//        }//end of if(ht.size() > 0)

//        return sBuffer;
//    }

    private StringBuffer getDataA0208_method2(Hashtable ht)
    {
        TreeMap tm = new TreeMap();

        StringBuffer sBuffer = new StringBuffer();

        String lastGeo = "";
        String currentGeo = "";

        TreeSet aTreeSet = new TreeSet();

        if(ht.size() > 0)
        {
            Iterator aItr1;
            Set keySet = ht.keySet();
            Iterator itr = keySet.iterator();
            while(itr.hasNext())
            {
                String key = (String) itr.next();
                String date = getDateA0208(key);
                String mktgName = getMktgNameA0208(key);
                String geo = getGeo(key, ht);
                tm.put(date + "<:>" + geo + " " + "<:>" + mktgName, geo);
                //tm.put(date + "<:>" + geo + "<:>" + mktgName + "<:>" + entityKey, geo);

                aTreeSet.add(date);
            }//end of while(itr.hasNext())

            aItr1 = aTreeSet.iterator();
            while(aItr1.hasNext())
            {
                Iterator aItr2;
                String str1 = (String) aItr1.next();

                sBuffer.append("<p>" + formatDate(str1) + "<br />" + NEWLINE);

                keySet = tm.keySet();
                aItr2 = keySet.iterator();
                while (aItr2.hasNext())
                {
                    String key = (String) aItr2.next();
                    String date = getDateA0208(key);
                    String mktgName = getMktgNameA0208(key);
                    currentGeo = (String) tm.get(key);
                    currentGeo = currentGeo.trim();

                    if(str1.equals(date))
                    {
                        if(!currentGeo.equals(lastGeo) && !lastGeo.equals("WW") && !lastGeo.equals(""))
                        {
                            sBuffer.append("<b>&lt;---" + lastGeo + "</b><br />" + NEWLINE);
                        }

                        if(!currentGeo.equals(lastGeo) && !currentGeo.equals("WW"))
                        {
                            sBuffer.append("<b>" + currentGeo + "---&gt;</b><br />" + NEWLINE);
                        }

                        sBuffer.append(mktgName + "<br />" + NEWLINE);
                        lastGeo = currentGeo;
                    }
                }//end of while (aItr2.hasNext())
                if(!lastGeo.equals("WW") && !lastGeo.equals(""))
                {
                    sBuffer.append("<b>&lt;---" + lastGeo + "</b></p>" + NEWLINE);
                }
                else
                {
                    sBuffer.append("</p>" + NEWLINE);
                }

                currentGeo = "";
                lastGeo = "";
            }//end of while(aItr1.hasNext())
        }//end of if(ht.size() > 0)

        return sBuffer;
    }

    private String getDateA0208(String str)
    {
        //int i = str.indexOf("<:>");

        //return str.substring(0, i);

        return parseString(str, 1);
    }

//    private String getEntityKeyA0208(String str)
//    {
//        int i = str.indexOf("<:>");
//        int j = str.indexOf("<:>", i + 3);

//        return str.substring(i + 3, j);
//    }

    private String getMktgNameA0208(String str)
    {
        //int i = str.indexOf("<:>");
        //i = str.indexOf("<:>", i + 3);

        //return str.substring(i + 3);

        return parseString(str, 3);
    }

    /**********************************************************************************
    * Format the date
    *
    * @param    aStr String
    * @return   String
    */
    public String formatDate(String aStr)
    {
        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try
        {
            Date aDate = sdf.parse(aStr);
            //sdf = new SimpleDateFormat("MMMM dd, yyyy 'for:'");
            sdf = new SimpleDateFormat("MMMM dd, yyyy");
            result = (sdf.format(aDate).toString());
        }
        catch(Exception e)
        {
            result = "Exception in formatDate(): " + e;
        }

        return result;
    }

    private String getGeo(String key, Hashtable ht)
    {
        String geo = "";
        String result = "";

        boolean ap = false;
        boolean can = false;
        boolean emea = false;
        boolean la = false;
        boolean us = false;

        StringBuffer value = (StringBuffer) ht.get(key);
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

    private void deriveSATAnswer(EntityItem ea, String aa, String tt)
    {
        // Derive answers to standard amendable text questions for the Internal Announcement Letter.

        // Find all STANDAMENDTEXT entities associated with this ANNOUNCEMENT
        Vector satVct = PokUtils.getAllLinkedEntities(ea, "ANNST", "STANDAMENDTEXT");

        // Process only first STANDAMENDTEXT entities found
        Iterator satItr = satVct.iterator();
        while(satItr.hasNext())
        {
            EntityItem sat = (EntityItem)satItr.next();
            if(PokUtils.isSelected(sat, "STANDARDAMENDTEXT_TYPE", tt))
            {
                pdsAndQ.put(aa, PokUtils.getAttributeValue(sat, "STANDARDAMENDTEXT", "", "", false));
                satVct.clear();
                return;
            }
        }
        satVct.clear();
        pdsAndQ.put(aa, new String());
    }

    private void deriveA0171()
    {
        StringBuffer sb = new StringBuffer();

        sb.append("<table  border=\"1\" cellpadding=\"2\" cellspacing=\"1\" summary=\"layout\">" + NEWLINE);
        sb.append("<tr>" + NEWLINE);
        sb.append("   <td valign=\"bottom\"><b>SAP Part Number</b></td>" + NEWLINE);
        sb.append("   <td valign=\"bottom\"><b>Product Type</b></td>" + NEWLINE);
        sb.append("   <td valign=\"bottom\"><b>Brand ID</b></td>" + NEWLINE);
        sb.append("   <td valign=\"bottom\"><b>Serialized</b></td>" + NEWLINE);
        sb.append("   <td valign=\"bottom\"><b>Pick Pack</b></td>" + NEWLINE);
        sb.append("   <td valign=\"bottom\"><b>Shippable</b></td>" + NEWLINE);
        sb.append("   <td valign=\"bottom\"><b>SW Royalty Part No</b></td>" + NEWLINE);
        sb.append("   <td valign=\"bottom\"><b>Sofware Description</b></td>" + NEWLINE);
        sb.append("</tr>" + NEWLINE);
        sb.append("</table>" + NEWLINE);

        sb.append("<br />");

        sb.append("<table  border=\"1\" cellpadding=\"2\" cellspacing=\"1\" summary=\"layout\">" + NEWLINE);
        sb.append("<tr>" + NEWLINE);
        sb.append("   <td valign=\"bottom\"><b>Unit of Measure</b></td>" + NEWLINE);
        sb.append("   <td valign=\"bottom\"><b>Minimum Order Quantity</b></td>" + NEWLINE);
        sb.append("   <td valign=\"bottom\"><b>Distribution System</b></td>" + NEWLINE);
        sb.append("   <td valign=\"bottom\"><b>Regulated Supply</b></td>" + NEWLINE);
        sb.append("</tr>" + NEWLINE);
        sb.append("</table>" + NEWLINE);
        pdsAndQ.put("A0171", sb.toString());
    }

    /**********************************************************************************
    * Show Internal Announcement Letter
    *
    * @return   String
    */
    public String getIntAnnLetter()
    {
        StringBuffer sb = new StringBuffer();
        String cfgStr  = new String();

        sb.append("<!-- B0052 -->");
        sb.append("<h2>TITLE</h2>");

        sb.append("<!-- A0100 -->");
        sb.append(pdsAndQ.get("A0100"));

        //A0102 is removed in 20.04
        //sb.append("<!-- B0053 -->");
        //if(isValue("A0102", ""))
        //{
              // Skip when true
        //   sb.append("<!-- skipped -->");
        //}
        //else
        //{
        //   sb.append("<h2>ONE SENTENCE DESCRIPTION</h2>");
        //}

        //sb.append("<!-- A0102 -->");
        //sb.append(pdsAndQ.get("A0102"));

        //A0104 is used in 20.10
        //A0104 is removed in 20.04
        //sb.append("<!-- B0054 -->");
        if(isValue("A0104", ""))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            sb.append("<h3>MANDATORY FEATURES AND BENEFITS</h3>");
        }

        sb.append("<!-- A0104 -->");
        sb.append(pdsAndQ.get("A0104"));

        //A0106 is used in 20.10
        //A0106 is removed in 20.04
        sb.append("<!-- B0055 -->");
        if(isValue("A0106", ""))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            sb.append("<h3>DIFFERENTIATING FEATURES AND BENEFITS</h3>");
        }

        sb.append("<!-- A0106 -->");
        sb.append(pdsAndQ.get("A0106"));

        sb.append("<!-- B0056 -->");
        sb.append("<h2>OVERVIEW</h2>");

        sb.append("<!-- B0057 -->");
        //sb.append(pdsAndQ.get("A0200"));
        //A0200 is renumbered to A0202 per RFA Guide 21.04
        sb.append(pdsAndQ.get("A0202"));

        sb.append("<!-- B0058 -->");
        sb.append("<h2>PLANNED AVAILABILITY DATE</h2>");

        sb.append("<!-- B0059 -->");
        sb.append(pdsAndQ.get("A0208"));

        sb.append("<!-- B0060 -->");
        sb.append("<h2>MARKETING INFORMATION</h2>");
        sb.append("<h3>MARKETING CHANNELS</h3>");

        sb.append("<!-- B0061 -->");
        if(isNo("P015H") && isNo("P015I") && isNo("P015J"))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            sb.append("<b><br />US, LA, CAN---&gt;</b>");
        }

      //Remove in 20.04
      //sb.append("<!-- B0062 -->");
      //if((isNo("P015H") && isNo("P015I") && isNo("P015J")) ||
      //    isNo("P015A"))
      //{
         // Skip when true
      //   sb.append("<!-- skipped -->");
      //}
      //else
      //{
      //   sb.append("<ul><li>IBM Business Partner - Solution Providers</li></ul>");
      //}

      //sb.append("<!-- B0063 -->");
      //if((isNo("P015H") && isNo("P015I") && isNo("P015J")) ||
      //    isNo("P015B"))
      //{
         // Skip when true
      //   sb.append("<!-- skipped -->");
      //}
      //else
      //{
      //   sb.append("<ul><li>IBM Business Partner - Distributors</li></ul>");
      //}

      //sb.append("<!-- B0064 -->");
      //if((isNo("P015H") && isNo("P015I") && isNo("P015J")) ||
      //    isNo("P015C"))
      //{
         // Skip when true
      //   sb.append("<!-- skipped -->");
      //}
      //else
      //{
      //   sb.append("<ul><li>IBM Business Partner - Systems Integrators</li></ul>");
      //}

      //P015D is removed from 19.04
      //sb.append("<!-- B0065 -->");
      //if ((isNo("P015H") && isNo("P015I") && isNo("P015J")) ||
      //    isNo("P015D")) {
         // Skip when true
      //   sb.append("<!-- skipped -->");
      //}
      //else {
      //   sb.append("<ul><li>IBM Printer Resellers</li></ul>");
      //}

      //P015E is removed from 19.04
      //sb.append("<!-- B0066 -->");
      //if ((isNo("P015H") && isNo("P015I") && isNo("P015J")) ||
      //    isNo("P015E")) {
         // Skip when true
      //   sb.append("<!-- skipped -->");
      //}
      //else {
      //   sb.append("<ul><li>IBM Printer Distributors</li></ul>");
      //}

      //sb.append("<!-- B0067 -->");
      //if((isNo("P015H") && isNo("P015I") && isNo("P015J")) ||
      //    isNo("P015F"))
      //{
         // Skip when true
      //   sb.append("<!-- skipped -->");
      //}
      //else
      //{
      //   sb.append("<ul><li>IBM Printer Remarketer Affiliates</li></ul>");
      //}

      //sb.append("<!-- B0068 -->");
      //if((isNo("P015H") && isNo("P015I") && isNo("P015J")) ||
      //    isNo("P014A"))
      //{
         // Skip when true
      //   sb.append("<!-- skipped -->");
      //}
      //else
      //{
      //   sb.append("<ul><li>Sales Specialists</li></ul>");
      //}

      //P017K is removed from 19.04
      //sb.append("<!-- B0069 -->");
      //if((isNo("P015H") && isNo("P015I") && isNo("P015J")) ||
      //    isNo("P014B") ||
          // IDKEYC = ''
      //   (isNo("P017A") && isNo("P017B") && isNo("P017C") && isNo("P017D") && isNo("P017E") && isNo("P017F") &&
          // isNo("P017G") && isNo("P017H") && isNo("P017I") && isNo("P017J") && isNo("P017K"))) {
      //    isNo("P017G") && isNo("P017H") && isNo("P017I") && isNo("P017J"))) {
         // Skip when true
      //   sb.append("<!-- skipped -->");
      //}
      //else
      //{
      //   sb.append("<ul><li>IBM Americas Call Centers</li></ul>");
      //}

      //sb.append("<!-- B0070 -->");
      //if((isNo("P015H") && isNo("P015I") && isNo("P015J")) ||
      //    isNo("P015G"))
      //{
         // Skip when true
      //   sb.append("<!-- skipped -->");
      //}
      //else
      //{
      //   sb.append("<ul><li>IBM Business Partner - Distributor for Workstation Software</li></ul>");
      //   sb.append("<ul><li>IBM Business Partner - Reseller for Workstation Software</li></ul>");
      //}

      //sb.append("<!-- B0071 -->");
      //if((isNo("P015H") && isNo("P015I") && isNo("P015J")) ||
      //    isNo("P016A") ||
      //    isNo("P016B"))
      //{
         // Skip when true
      //   sb.append("<!-- skipped -->");
      //}
      //else
      //{
      //   sb.append("<ul><li>IBM Business Partner - Personal Computer Resellers</li></ul>");
      //}

      //sb.append("<!-- B0072 -->");
      //if((isNo("P015H") && isNo("P015I") && isNo("P015J")) ||
      //    isNo("P016A") ||
      //    isNo("P016C"))
      //{
         // Skip when true
      //   sb.append("<!-- skipped -->");
      //}
      //else
      //{
      //   sb.append("<ul><li>IBM Business Partner - Personal Computer Distributors</li></ul>");
      //}

        sb.append("<!-- B0073 -->");
        if(isNo("P015H") && isNo("P015I") && isNo("P015J"))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            //20.04
            sb.append("<!-- A0105 -->");
            sb.append(pdsAndQ.get("A0105"));

            sb.append("<b><br />&lt;---US, LA, CAN</b>");
        }

        sb.append("<!-- B0074 -->");
        if(isNo("P015K"))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            // Output Content
            sb.append("<b><br />EMEA---&gt;</b>");
        }

        sb.append("<!-- A0110 -->");
        sb.append(pdsAndQ.get("A0110"));

        sb.append("<!-- B0075 -->");
        if(isNo("P015K"))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            // Output Content
            sb.append("<b><br />&lt;---EMEA</b>");
        }

        if(isNo("P015L"))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            // Output Content
            sb.append("<br /><b>AP---&gt;</b>");
            sb.append("<p>The announced product is released to authorized ");
        }
        if(isNo("P015L") || isYes("P016A"))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            sb.append("IBM ");
        }
        if(isNo("P015L"))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            // Output Content
            sb.append("channels at the announcing country's delegation.</p>");
            sb.append("<br /><b>&lt;---AP</b>");
        }

        sb.append("<!-- B0077 -->");
      //if(isValue("A0116", ""))
      //{
         // Skip when true
      //   sb.append("<!-- skipped -->");
      //}
      //else
      //{
      //   sb.append("<h3>ROUTES TO MARKET BY GEOGRAPHY</h3>");
      //}

        //A0116 is removed in 20.10
        //CR1123044433
        //According to John Gardner: show <h3>ROUTES TO MARKET BY GEOGRAPHY</h3> even though A0116 is empty
        //sb.append("<h3>ROUTES TO MARKET BY GEOGRAPHY</h3>");

        //sb.append("<!-- A0116 -->");
        //sb.append(pdsAndQ.get("A0116"));

        //sb.append("<!-- B0078-->");
        //if(isValue("A0116", "") ||
        //   isNo("P015K"))
        //{
            // Skip when true
        //   sb.append("<!-- skipped -->");
        //}
        //else
        //{
            // Output Content
        //   sb.append("<b><br />EMEA---&gt;</b>");
        //   sb.append("<p>Note  to the EMEA PM: This section will be automatically deleted at PLET " +
        //      "Generation time.</p>");
        //   sb.append("<b><br />&lt;---EMEA</b>");
        //}

        //The following is removed in 20.10
        //CR1123044433
        //According to John Gardner: show the following text when PO15K = yes
        //if(isNo("P015K"))
        //{
            // Skip when true
        //    sb.append("<!-- skipped -->");
        //}
        //else
        //{
            // Output Content
        //    sb.append("<b><br />EMEA---&gt;</b>");
        //    sb.append("<p>Note  to the EMEA PM: This section will be automatically deleted at PLET " +
        //        "Generation time.</p>");
        //    sb.append("<b><br />&lt;---EMEA</b>");
        //}

        sb.append("<!-- B0079 -->");
        if(isValue("A0118", ""))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            sb.append("<h3>MARKETING STRATEGY</h3>");
        }

        sb.append("<!-- A0118 -->");
        sb.append(pdsAndQ.get("A0118"));

        sb.append("<!-- B0080 -->");
        if((isNo("P015H") && isNo("P015J")) || (isValue("A0119", "") && isValue("A0120", "")))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            // Output Content
            sb.append("<b><br />US, CAN---&gt;</b>");
            sb.append("<h3>CROSS-SELL, UP-SELL INFORMATION</h3>");
        }

        sb.append("<!-- A0119 -->");
        sb.append(pdsAndQ.get("A0119"));

        sb.append("<!-- A0120 -->");
        sb.append(pdsAndQ.get("A0120"));

        sb.append("<!-- B0081 -->");
        if((isNo("P015H") && isNo("P015J")) || (isValue("A0119", "") && isValue("A0120", "")))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            // Output Content
            sb.append("<b><br />&lt;---US, CAN</b>");
        }

        sb.append("<!-- B0082 -->");
        if(isValue("A0122", ""))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            sb.append("<h3>CUSTOMER WANTS AND NEEDS</h3>");
        }

        sb.append("<!-- A0122 -->");
        sb.append(pdsAndQ.get("A0122"));

        //20.10
        if(isValue("A0123", ""))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            sb.append("<h3>CUSTOMER PAIN POINTS</h3>");
        }

        sb.append("<!-- A0123 -->");
        sb.append(pdsAndQ.get("A0123"));

        sb.append("<!-- B0083 -->");
        if(isValue("A0124", ""))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            sb.append("<h3>RESOURCE AND SKILL REQUIREMENTS</h3>");
        }

        sb.append("<!-- A0124 -->");
        sb.append(pdsAndQ.get("A0124"));

        sb.append("<!-- B0084 -->");
        if(isValue("A0126", ""))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            sb.append("<h3>RELATED SERVICES OFFERING POTENTIAL</h3>");
        }

        sb.append("<!-- A0126 -->");
        sb.append(pdsAndQ.get("A0126"));

        sb.append("<!-- B0085 -->");
        if(isValue("A0128", ""))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            sb.append("<h3>OTHER MARKETING INFORMATION</h3>");
        }

        sb.append("<!-- A0128 -->");
        sb.append(pdsAndQ.get("A0128"));

        sb.append("<!-- B0086 -->");
        sb.append("<h2>SELLING INFORMATION</h2>");

        sb.append("<!-- B0087 -->");
        if(isValue("A0130", ""))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            sb.append("<h3>SALES CONTACTS</h3>");
        }

        sb.append("<!-- A0130 -->");
        sb.append(pdsAndQ.get("A0130"));

        sb.append("<!-- B0088 -->");
        if(isValue("A0132", ""))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            sb.append("<h3>SALES ACTION REQUIRED</h3>");
        }

        sb.append("<!-- A0132 -->");
        sb.append(pdsAndQ.get("A0132"));

        sb.append("<!-- B0089 -->");
        if(isValue("A0134", ""))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            sb.append("<h3>SALES APPROACH</h3>");
        }

        sb.append("<!-- A0134 -->");
        sb.append(pdsAndQ.get("A0134"));

        sb.append("<!-- B0090 -->");
        if(isValue("A0136", ""))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            sb.append("<h3>CUSTOMER CANDIDATE GUIDELINES</h3>");
        }

        sb.append("<!-- A0136 -->");
        sb.append(pdsAndQ.get("A0136"));

        sb.append("<!-- B0091 -->");
        if(isValue("A0138", ""))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            sb.append("<h3>CUSTOMER RESTRICTIONS</h3>");
        }

        sb.append("<!-- A0138 -->");
        sb.append(pdsAndQ.get("A0138"));

        sb.append("<!-- B0092 -->");
        if(isValue("A0140", ""))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            sb.append("<h3>HANDLING OBJECTIONS</h3>");
        }

        sb.append("<!-- A0140 -->");
        sb.append(pdsAndQ.get("A0140"));

        sb.append("<!-- B0093 -->");
        sb.append("<h2>COMPETITION & COMPETITIVE OFFERINGS</h2>");

        sb.append("<!-- B0094 -->");
        if(isValue("A0144", ""))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            sb.append("<h3>COMPETITIVE OFFERINGS</h3>");
        }

        sb.append("<!-- A0144 -->");
        sb.append(pdsAndQ.get("A0144"));

        sb.append("<!-- B0095 -->");
        if(isValue("A0146", ""))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            sb.append("<h3>STRENGTHS AND WEAKNESSES SUMMARY</h3>");
        }

        sb.append("<!-- A0146 -->");
        sb.append(pdsAndQ.get("A0146"));

        sb.append("<!-- B0096 -->");
        if(!isValue("A0181", "Y"))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            sb.append("<h3>GLOBAL FINANCING</h3>" +
                "<p>IBM  Global  Financing  offers competitive financing to credit-qualified " +
                "customers and ");
        }
        if(!isValue("A0181", "Y") || isYes("P016A"))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            sb.append("IBM ");
        }
        if(!isValue("A0181", "Y"))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            sb.append("Business Partners to assist them in acquiring IT solutions. Our offerrings " +
                "include financing for IT acquisition, including hardware, software, and  services, both " +
                "from IBM and other manufacturers or vendors, as well as commercial financing (revolving lines of credit, term loans, acquisition facilities, and inventory financing credit  lines) for Business " +
                "Partners. Offerings (for all customer segments: small, medium, and large enterprise), " +
                "rates, terms, and availability can vary  by  country. Contact your local IBM Global Financing organization or visit the Web at</p>" +
                "<ul><li>http://www.ibm.com/financing</li></ul>");
            sb.append("<p>IBM  Global  Financing  offerings are provided through IBM Credit LLC in the United States and other IBM subsidiaries and divisions worldwide  to qualified  commercial  and  government customers.   Rates are based on a customer's credit rating, financing terms, offering type, equipment type and options, and may vary by country.   Other  restrictions  may  apply. Rates and offerings are subject to change, extension or withdrawal without notice.</p>" + NEWLINE);
        }
      //sb.append("<!-- B0097 -->");
      //if (!isValue("A0181", "Y")) {
         // Skip when true
      //   sb.append("<!-- skipped -->");
      //}
      //else {
      //   sb.append(pdsAndQ.get("A0134"));
      //}

        sb.append("<!-- B0098 -->");
        if(!isValue("A0181", "Y"))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            sb.append(pdsAndQ.get("A0182"));
        }

        sb.append("<!-- B0099 -->");
        if(!isValue("A0181", "Y"))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            //sb.append(pdsAndQ.get("A0184"));
            sb.append(pdsAndQ.get("A0185"));
            //sb.append(pdsAndQ.get("A0186"));
            sb.append(pdsAndQ.get("A0187"));
            sb.append(pdsAndQ.get("A0188"));
            sb.append(pdsAndQ.get("A0189"));
            //A0190 is removed from 19.04.
            //sb.append(pdsAndQ.get("A0190"));
            sb.append("<p>For more financing information, please visit</p>");
            sb.append("<ul><li>www.ibm.com/financing</li></ul>");
        }

        sb.append("<!-- B0100 -->");
        sb.append("<h2>ADMINISTRATIVE INFORMATION</h2>");
        if(isNo("P015K"))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            // Output Content
            sb.append("<b><br />EMEA---&gt;</b>");
            if(isYes("P031A"))
            {
                sb.append("<p>Part number included: Yes and available through IBM Business Partner Resellers/Distributors for WorkStation Software</p>");
            }
            else
            {
                sb.append("<p>No part number included</p>");
            }
        }
        if(isNo("P015K"))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            // Output Content
            sb.append("<b><br />&lt;---EMEA</b>");
        }

        sb.append("<h3>BILLING</h3>");

        sb.append("<!-- A0148 -->");
        sb.append(pdsAndQ.get("A0148"));

        sb.append("<!-- B0101 -->");
        if(isValue("A0150", ""))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            sb.append("<h3>CONTRACT INFORMATION</h3>");
        }

        sb.append("<!-- A0150 -->");
        sb.append(pdsAndQ.get("A0150"));

        sb.append("<!-- B0102 -->");
      //if ((isNo("P015H")) ||
      //     (isValue("A0119", "") && isValue("A0120", ""))) {
         // Skip when true
      //   sb.append("<!-- skipped -->");
      //}
        if(isYes("P016A") || isNo("P015H"))
        {
            // Skip when true
            // sb.append("<!-- skipped -->");
        }
        else
        {
            // Output Content
            sb.append("<b><br />US---&gt;</b>" +
                "<h3>GOVERNMENT CUSTOMERS</h3>" +
                "<h5>FEDERAL GOVERNMENT CUSTOMERS</h5>" +
                "<p>For assistance with marketing, proposing, or administering this offering " +
                "to  a  federal  government  account,  contact  IBM Government Systems at " +
                "800-333-6705. For additional information, please note the following:</p>" +
                "<ul><li>http://www.ibm.com/easyaccess/gsa   (The IBM/GSA Schedule for the US " +
                "Federal Government Customers)</li>" +
                "<li>http://www.ibm.com/easyaccess/federal   (Portal for US Federal  Gov" +
                "ernment Customers)</li></ul>" +
                "<h5>STATE AND LOCAL GOVERNMENT CUSTOMERS</h5>" +
                "<p>For  assistance marketing, proposing or administering this offering to a " +
                "state or local account, find the contact information for the appropriate " +
                "IBM Team, by utilizing the following Web:</p>" +
                "<ul><li>http://www.ibm.com/easyaccess/slg" + "</li></ul>" +
                "<p>If you have a Web site question contact:   " +
                "Kathy Davenport tie line 930-0143.   If you have  a  Sales  question, " +
                "contact  the IBM Sales Center at 800-426-1751 and ask to be connected to " +
                "someone who handles Government accounts.</p>" +
                "<b><br />&lt;---US</b>");
        }

        sb.append("<!-- B0103 -->");
        if(isNo("P015K"))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            // Output Content
            sb.append("<b><br />EMEA---&gt;</b>");
        }

        if(isNo("P015K"))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            // Output Content
            sb.append("<h5>WORLDWIDE CUSTOMER LETTER TRANSLATION</h5>");
        }

        sb.append("<!-- A0151 -->");
        sb.append(pdsAndQ.get("A0151"));

        //A0152 is removed in 20.10
        //if(isNo("P015K"))
        //{
            // Skip when true
        //    sb.append("<!-- skipped -->");
        //}
        //else
        //{
            // Output Content
        //    sb.append("<h5>PROPOSAL INSERT TRANSLATION</h5>");
        //}

        //sb.append("<!-- A0152 -->");
        //sb.append(pdsAndQ.get("A0152"));

        sb.append("<!-- B0104 -->");
        if(isNo("P015K"))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            // Output Content
            sb.append("<b><br />&lt;---EMEA</b>");
        }

        sb.append("<!-- B0105 -->");
        if(isNo("P015L"))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            // Output Content
            sb.append("<b><br />AP---&gt;</b>" +
                "<h3>ADMINISTRATIVE FACT SHEET:</h3>" +
                "<p>Not applicable.</p>" +
                "<b><br />&lt;---AP</b>");
        }

        //sb.append("<!-- B0106 -->");
        //sb.append("<h2>OFFERING NAME</h2>");

        // A0153 is removed from 19.04. A0154 is renamed to A0153 in 19.04
        //sb.append("<!-- A0153 -->");
        //sb.append(pdsAndQ.get("A0153"));

        sb.append("<!-- B0107 -->");
        sb.append("<h2>OFFERING NAME AND ID</h2>");

        // A0153 is removed from 19.04. A0154 is renamed to A0153 in 19.04
        //sb.append("<!-- A0154 -->");
        //sb.append(pdsAndQ.get("A0154"));
        sb.append("<!-- A0153 -->");
        sb.append(pdsAndQ.get("A0153"));

        sb.append("<!-- B0108 -->");
        sb.append("<h2>COMPONENT NAME AND ID</h2>");

        // A0155 is renumbered to A0154 in 19.10
        sb.append("<!-- A0154 -->");
        sb.append(pdsAndQ.get("A0154"));

        // 19.10
        sb.append("<h2>FEATURE NAME AND ID</h2>");
        sb.append("<!-- A0155 -->");
        sb.append(pdsAndQ.get("A0155"));

        sb.append("<!-- B0109 -->");
        sb.append("<h3>CONFIGURATOR INFORMATION</h3>" +
            "<p>The following configurator aids will support this announcement:</p>");

        sb.append("<!-- B0110 -->");
        if(isNo("P018A") || isNo("P015H"))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            // Output Content
            cfgStr = getConfigRow("P019A", "P019B") +
                getConfigRow("P019C", "P019D") +
                getConfigRow("P019E", "P019F") +
                getConfigRow("P019G", "P019H") +
                getConfigRow("P019I", "P019J");
            if(cfgStr.length() > 0)
            {
                sb.append("<b><br />US---&gt;</b>" +
                    "<table width=\"750\" border=\"1\" cellpadding=\"2\" cellspacing=\"1\" summary=\"layout\">" +
                    "<tr><td><b>Configurator Aid</b></td><td><b>Date Available</b></td></tr>" +
                    cfgStr +
                    "</table><b><br />&lt;---US</b>");
            }
        }

        // Need to work on B0111 with B0112 -> B0115
        sb.append("<!-- B0111 -->");
        if(isNo("P018B") || (isNo("P015L") && isNo("P015I") && isNo("P015J") && isNo("P015K")))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            // Output Content - moved to individual geo in B0112, B0113, B0114, and B0115
            // sb.append("<b><br />AP, LA, CAN, EMEA---&gt;</b>" +
            //   "<table width=\"750\" border=\"1\" cellpadding=\"2\" cellspacing=\"1\">" +
            //   "<tr><th>Configurator Aid</th><th>Date Available</th></tr>" +
            //   "</table><b><br />&lt;---AP, LA, CAN, EMEA</b>");
        }

        sb.append("<!-- B0112 -->");
        if(isNo("P018B") || isNo("P015L"))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            // Output Content
            cfgStr = getConfigRow("P020A", "P020B") +
                getConfigRow("P020C", "P020D") +
                getConfigRow("P020E", "P020F") +
                getConfigRow("P020G", "P020H") +
                getConfigRow("P020I", "P020J");
            if(cfgStr.length() > 0)
            {
                sb.append("<b><br />AP---&gt;</b>" +
                    "<table width=\"600\" border=\"1\" cellpadding=\"2\" cellspacing=\"1\" summary=\"layout\">" +
                    "<tr><td><b>Configurator Aid</b></td><td><b>Date Available</b></td></tr>" +
                    cfgStr +
                    "</table><b><br />&lt;---AP</b>");
            }
        }

        sb.append("<!-- B0113 -->");
        if(isNo("P018B") || isNo("P015I"))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            // Output Content
            cfgStr = getConfigRow("P021A", "P021B") +
                getConfigRow("P021C", "P021D") +
                getConfigRow("P021E", "P021F") +
                getConfigRow("P021G", "P021H") +
                getConfigRow("P021I", "P021J");
            if(cfgStr.length() > 0)
            {
                sb.append("<b><br />LA---&gt;</b>" +
                    "<table width=\"600\" border=\"1\" cellpadding=\"2\" cellspacing=\"1\" summary=\"layout\">" +
                    "<tr><td><b>Configurator Aid</b></td><td><b>Date Available</b></td></tr>" +
                    cfgStr +
                    "</table><b><br />&lt;---LA</b>");
            }
        }

        sb.append("<!-- B0114 -->");
        if(isNo("P018B") || isNo("P015J"))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            // Output Content
            cfgStr = getConfigRow("P022A", "P022B") +
                getConfigRow("P022C", "P022D") +
                getConfigRow("P022E", "P022F") +
                getConfigRow("P022G", "P022H") +
                getConfigRow("P022I", "P022J");
            if(cfgStr.length() > 0)
            {
                sb.append("<b><br />CAN---&gt;</b>" +
                    "<table width=\"600\" border=\"1\" cellpadding=\"2\" cellspacing=\"1\" summary=\"layout\">" +
                    "<tr><td><b>Configurator Aid</b></td><td><b>Date Available</b></td></tr>" +
                    cfgStr +
                    "</table><b><br />&lt;---CAN</b>");
            }
        }

        sb.append("<!-- B0115 -->");
        if(isNo("P018B") || isNo("P015K"))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            // Output Content
            cfgStr = getConfigRow("P023A", "P023B") +
                getConfigRow("P023C", "P023D") +
                getConfigRow("P023E", "P023F") +
                getConfigRow("P023G", "P023H") +
                getConfigRow("P023I", "P023J");
            if(cfgStr.length() > 0)
            {
                sb.append("<b><br />EMEA---&gt;</b>" +
                    "<table width=\"600\" border=\"1\" cellpadding=\"2\" cellspacing=\"1\" summary=\"layout\">" +
                    "<tr><td><b>Configurator Aid</b></td><td><b>Date Available</b></td></tr>" +
                    cfgStr +
                    "</table><b><br />&lt;---EMEA</b>");
            }
        }

        sb.append("<!-- B0116 -->");
        if(isYes("P018A") || isYes("P018B"))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            // Output Content
            sb.append("<table width=\"450\" border=\"1\" cellpadding=\"2\" cellspacing=\"1\" summary=\"layout\">" +
                "<tr><td><b>Configurator Aid</b></td><td><b>Date Available</b></td></tr>" +
                "<tr><td align=\"center\">None</td><td align=\"center\">N//A</td></tr></table>");
        }

        sb.append("<!-- B0117 -->");
        sb.append("<h2>SAP INFORMATION SECTION</h2>");

        sb.append("<!-- B0118 -->");
        sb.append(pdsAndQ.get("A0170"));

        sb.append("<!-- B0119 -->");
        sb.append("<h2>ORDERING INFORMATION FOR SAP PARTNUMBERS</h2>");

        sb.append("<!-- B0120 -->");
        sb.append(pdsAndQ.get("A0171"));

        sb.append("<!-- B0121 -->");
        if(isNo("P015J"))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            // Output Content
            sb.append("<b><br />CAN---&gt;</b>" +
                "<h2>CARIBBEAN NORTH RELEASE INFORMATION</h2>" +
                "<h3>AFFECTED COUNTRIES/CHANNELS</h3>" +
                "<p>This announcement is applicable in Caribbean North Countries.</p>" +
                "<b><br />&lt;---CAN</b>");
        }

        sb.append("<!-- B0122 -->");
        if(isYes("P016A") || isNo("P015K"))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            // Output Content
            sb.append("<b><br />EMEA---&gt;</b>" +
                "<h3>BUSINESS PARTNERS TERMS AND CONDITIONS</h3>");
        }

        sb.append("<!-- A0156 -->");
        sb.append(pdsAndQ.get("A0156"));

        sb.append("<!-- B0123 -->");
        if(isYes("P016A") || isNo("P015K"))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            // Output Content
            sb.append("<b><br />&lt;---EMEA</b>");
        }

        sb.append("<!-- B0124 -->");
        if(isNo("P015H") && isNo("P015J"))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            // Output Content
            sb.append("<b><br />US, CAN---&gt;</b>" +
                "<h3>IBM.COM (FORMERLY KNOWN AS SHOPIBM)</h3>");
        }


        // sb.append(pdsAndQ.get("A0157"));

        sb.append("<!-- B0125 -->");
        if(isNo("P015H") && isNo("P015J"))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            // Output Content
            sb.append("<p>Is  this  product(s)  to  be  sold  via  the  ibm.com (formerly known as " +
                "ShopIBM) Web site?</p>");
        }

        sb.append("<!-- B0126 -->");
        if((isNo("P015H") && isNo("P015J")) ||
            !isValue("A0157", "N"))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            // Output Content
            sb.append("<p>No</p>");
        }

        sb.append("<!-- B0127 -->");
        if((isNo("P015H") && isNo("P015J")) ||
            !isValue("A0157", "Y"))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            // Output Content
            sb.append("<p>Yes</p>" +
                "<p>If yes, select the ibm.com (formerly known as ShopIBM) sections for the " +
                "product from the list below:</p>");
        }

        sb.append("<!-- A0158 -->");
        sb.append(pdsAndQ.get("A0158"));

        sb.append("<!-- B0128 -->");
        if(isNo("P015H") && isNo("P015J"))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            // Output Content
            sb.append("<h5>NOTE  TO EDITOR:</h5>" +
                "<p>Do not publish ibm.com (formerly known as ShopIBM) information in announcement. " +
                "Remove this section before  announcing.</p>" +
                "<b><br />&lt;---US, CAN</b>");
        }

        sb.append("<!-- B0129 -->");
        if(isNo("P030A") ||
            isNo("P030B") ||
            (isValue("A0160", "") && isValue("A0161", "")))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            // Output Content
            sb.append("<h2>ACCESSIBILITY BY PEOPLE WITH DISABILITIES</h2>");
        }

        sb.append("<!-- A0160 -->");
        sb.append(pdsAndQ.get("A0160"));

        sb.append("<!-- B0130 -->");
        sb.append("<!-- skipped - blank Boilerplate text -->");

        sb.append("<!-- A0161 -->");
        sb.append(pdsAndQ.get("A0161"));

        sb.append("<!-- B0131 -->");
        if(isNo("P030A") || isNo("P015H"))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            // Output Content
            sb.append("<b><br />US---&gt;</b>" +
                "<h2>SECTION 508 OF THE U.S. REHABILITATION ACT</h2>");
        }

        sb.append("<!-- A0162 -->");
        sb.append(pdsAndQ.get("A0162"));

        sb.append("<!-- A0163 -->");
        sb.append(pdsAndQ.get("A0163"));

        sb.append("<!-- B0132 -->");
        if(isNo("P030A") ||
            isNo("P015H"))
        {
            // Skip when true
            sb.append("<!-- skipped -->");
        }
        else
        {
            // Output Content
            sb.append("<b><br />&lt;---US</b>");
        }

      //sb.append("<!-- A0170 -->");
      //sb.append(pdsAndQ.get("A0170"));

      //sb.append("<!-- A0171 -->");
      //sb.append(pdsAndQ.get("A0171"));

      //sb.append("<!-- A0181 -->");
      // sb.append(pdsAndQ.get("A0181"));

      //sb.append("<!-- B0133 -->");
      //if (!isValue("A0181", "Y")) {
         // Skip when true
      //   sb.append("<!-- skipped -->");
      //}
      //else {
      //   sb.append("<p>IBM  Global  Financing  offerings are provided through IBM Credit LLC in " +
      //        "the United States and other IBM subsidiaries and divisions worldwide  to " +
      //        "qualified  commercial  and  government customers.   Rates are based on a " +
      //        "customer's credit rating, financing terms, offering type, equipment type " +
      //        "and options, and may vary by country.   Other  restrictions  may  apply.  " +
      //        "Rates and offerings are subject to change, extension or withdrawal with" +
      //        "out notice.</p>");
      //}

      //sb.append("<!-- A0182 -->");
      //sb.append(pdsAndQ.get("A0182"));

      //sb.append("<!-- A0183 -->");
      //sb.append(pdsAndQ.get("A0183"));

      //sb.append("<!-- A0184 -->");
      // sb.append(pdsAndQ.get("A0184"));

      //sb.append("<!-- A0185 -->");
      //sb.append(pdsAndQ.get("A0185"));

      //sb.append("<!-- A0186 -->");
      // sb.append(pdsAndQ.get("A0186"));

      //sb.append("<!-- A0187 -->");
      //sb.append(pdsAndQ.get("A0187"));

      //sb.append("<!-- A0188 -->");
      //sb.append(pdsAndQ.get("A0188"));

      //sb.append("<!-- A0189 -->");
      //sb.append(pdsAndQ.get("A0189"));

      //A0190 is removed from 19.04.
      //sb.append("<!-- A0190 -->");
      //sb.append(pdsAndQ.get("A0190"));

      // A0195 is new in 20.04
        sb.append("<!-- A0195 -->");
        sb.append(pdsAndQ.get("A0195"));

        return (sb.toString());
    }
    private String getConfigRow(String pdsCfg, String pdsDate)
    {
        // Build HTML data for the configurator table
        StringBuffer sb = new StringBuffer();
        String cfgAid   = new String();
        String cfgDate  = new String();

        cfgAid = (String) pdsAndQ.get(pdsCfg);
        if(cfgAid != null && cfgAid.length() > 0)
        {
            sb.append("<tr><td>cfgAid</td>");
            cfgDate = (String) pdsAndQ.get(pdsDate);
            if(cfgDate != null && cfgDate.length() > 0)
            {
                sb.append("<td align=\"center\">cfgDate</td></tr>");
            }
            else
            {
                sb.append("<td>&nbsp;</td></tr>");
            }
        }
        return sb.toString();
    }
    private void log(String str)
    {
        // Get current data and time to be included in the debug log
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String dt = sdf.format(new Date());
        debugBuffer.append("<!-- "+ dt + "  " + str + " -->" + NEWLINE);
    }

    private Vector getParentEntities(Vector srcVct, String linkType, String destType)
    {
        // find entities thru 'linkType' relators
        Vector destVct = new Vector(1);

        Iterator srcItr = srcVct.iterator();
        while (srcItr.hasNext())
        {
            EntityItem entityItem = (EntityItem) srcItr.next();
            getParentEntities(entityItem, linkType, destType, destVct);
        }

        return destVct;
    }

    private void getParentEntities(EntityItem entityItem, String linkType, String destType, Vector destVct)
    {
        //This method is based on PokUtils.getLinkedEntities()
        if (entityItem == null)
        {
            return;
        }

        for(int ui = 0; ui < entityItem.getUpLinkCount(); ui++)
        {
            EANEntity entityLink = entityItem.getUpLink(ui);
            if(entityLink.getEntityType().equals(linkType))
            {
                // check for destination entity as an uplink
                for(int i = 0; i < entityLink.getUpLinkCount(); i++)
                {
                    EANEntity entity = entityLink.getUpLink(i);
                    if(entity.getEntityType().equals(destType))
                    {
                        String key;
                        if(!destVct.contains(entity))
                        {
                            destVct.addElement(entity);
                        }

                        key = entity.getKey();

                        if(null == geoHT.get(key))
                        {
                            geoHT.put(key, new StringBuffer("NNNNN"));
                        }
                        if(checkRfaGeoUS(entityItem))
                        {
                            updateGeo(key, "US", geoHT);
                        }
                        if(checkRfaGeoAP(entityItem))
                        {
                            updateGeo(key, "AP", geoHT);
                        }
                        if(checkRfaGeoLA(entityItem))
                        {
                            updateGeo(key, "LA", geoHT);
                        }
                        if(checkRfaGeoCAN(entityItem))
                        {
                            updateGeo(key, "CAN", geoHT);
                        }
                        if(checkRfaGeoEMEA(entityItem))
                        {
                            updateGeo(key, "EMEA", geoHT);
                        }
                    }//end of if
                }//end of for (int i = 0; i < entityLink.getUpLinkCount(); i++)
            }//end of if (entityLink.getEntityType().equals(linkType))
        }//end for (int ui = 0; ui < entityItem.getUpLinkCount(); ui++)
    }

    private boolean checkRfaGeoUS(EntityItem ei)
    {
        boolean rc = true;
        String entityKey = ei.getKey();
        String value = (String) usGeoHT.get(entityKey);

        if(null == value)
        {
            if(gaList.isRfaGeoUS(ei))
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
        boolean rc = true;
        String entityKey = ei.getKey();
        String value = (String) apGeoHT.get(entityKey);

        if(null == value)
        {
            if(gaList.isRfaGeoAP(ei))
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
        boolean rc = true;
        String entityKey = ei.getKey();
        String value = (String) laGeoHT.get(entityKey);

        if(null == value)
        {
            if(gaList.isRfaGeoLA(ei))
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
        boolean rc = true;
        String entityKey = ei.getKey();
        String value = (String) canGeoHT.get(entityKey);

        if(null == value)
        {
            if(gaList.isRfaGeoCAN(ei))
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
        boolean rc = true;
        String entityKey = ei.getKey();
        String value = (String) emeaGeoHT.get(entityKey);

        if(null == value)
        {
            if(gaList.isRfaGeoEMEA(ei))
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

    private void deriveP014A()
    {
        boolean y = false;

        for(int i = 0; i < channelV1.size(); i++)
        {
            EntityItem channelItem = (EntityItem) channelV1.get(i);
            if(PokUtils.isSelected(channelItem, "CHANNELNAME", "373") && PokUtils.isSelected(channelItem, "ROUTESTOMKTG", "110"))
            {
                if(checkRfaGeoUS(channelItem) || checkRfaGeoAP(channelItem) || checkRfaGeoLA(channelItem) || checkRfaGeoCAN(channelItem))
                {
                    pdsAndQ.put("P014A", "Y");
                    y = true;
                    break;
                }
            }
        }

        if(!y)
        {
            for(int i = 0; i < channelV2.size(); i++)
            {
                EntityItem channelItem = (EntityItem) channelV2.get(i);
                if(PokUtils.isSelected(channelItem, "CHANNELNAME", "373") && PokUtils.isSelected(channelItem, "ROUTESTOMKTG", "110"))
                {
                    if(checkRfaGeoUS(channelItem) || checkRfaGeoAP(channelItem) || checkRfaGeoLA(channelItem) || checkRfaGeoCAN(channelItem))
                    {
                        pdsAndQ.put("P014A", "Y");
                        y = true;
                        break;
                    }
                }
            }
        }//end of if(!y)

        if(!y)
        {
            for(int i = 0; i < channelV3.size(); i++)
            {
                EntityItem channelItem = (EntityItem) channelV3.get(i);
                if(PokUtils.isSelected(channelItem, "CHANNELNAME", "373") && PokUtils.isSelected(channelItem, "ROUTESTOMKTG",   "110"))
                {
                    if(checkRfaGeoUS(channelItem) || checkRfaGeoAP(channelItem) || checkRfaGeoLA(channelItem) || checkRfaGeoCAN(channelItem))
                    {
                        pdsAndQ.put("P014A", "Y");
                        y = true;
                        break;
                    }
                }
            }
        }//enf of if(!y)

        if(!y)
        {
            pdsAndQ.put("P014A", "N");
        }
    }

    private void deriveP014B()
    {
        boolean y = false;

        for(int i = 0; i < channelV1.size(); i++)
        {
            EntityItem channelItem = (EntityItem) channelV1.get(i);
            if(PokUtils.isSelected(channelItem, "CHANNELNAME", "374") && PokUtils.isSelected(channelItem, "ROUTESTOMKTG", "110"))
            {
                if(checkRfaGeoUS(channelItem) || checkRfaGeoAP(channelItem) || checkRfaGeoLA(channelItem) || checkRfaGeoCAN(channelItem))
                {
                    pdsAndQ.put("P014B", "Y");
                    y = true;
                    break;
                }
            }
        }

        if(!y)
        {
            for(int i = 0; i < channelV2.size(); i++)
            {
                EntityItem channelItem = (EntityItem) channelV2.get(i);
                if(PokUtils.isSelected(channelItem, "CHANNELNAME", "374") && PokUtils.isSelected(channelItem, "ROUTESTOMKTG", "110"))
                {
                    if(checkRfaGeoUS(channelItem) || checkRfaGeoAP(channelItem) || checkRfaGeoLA(channelItem) || checkRfaGeoCAN(channelItem))
                    {
                        pdsAndQ.put("P014B", "Y");
                        y = true;
                        break;
                    }
                }
            }
        }//end of if(!y)

        if(!y)
        {
            for(int i = 0; i < channelV3.size(); i++)
            {
                EntityItem channelItem = (EntityItem) channelV3.get(i);
                if(PokUtils.isSelected(channelItem, "CHANNELNAME", "374") && PokUtils.isSelected(channelItem, "ROUTESTOMKTG", "110"))
                {
                    if(checkRfaGeoUS(channelItem) || checkRfaGeoAP(channelItem) || checkRfaGeoLA(channelItem) || checkRfaGeoCAN(channelItem))
                    {
                        pdsAndQ.put("P014B", "Y");
                        y = true;
                        break;
                    }
                }
            }
        }//end of if(!y)

        if(!y)
        {
            pdsAndQ.put("P014B", "N");
        }
    }

    private void deriveP015A()
    {
        boolean y = false;

        for(int i = 0; i < channelV1.size(); i++)
        {
            EntityItem channelItem = (EntityItem) channelV1.get(i);
            if(PokUtils.isSelected(channelItem, "CHANNELNAME", "386") && PokUtils.isSelected(channelItem, "ROUTESTOMKTG", "110"))
            {
                if(checkRfaGeoUS(channelItem) || checkRfaGeoAP(channelItem) || checkRfaGeoLA(channelItem) || checkRfaGeoCAN(channelItem))
                {
                    pdsAndQ.put("P015A", "Y");
                    y = true;
                    break;
                }
            }
        }

        if(!y)
        {
            for(int i = 0; i < channelV2.size(); i++)
            {
                EntityItem channelItem = (EntityItem) channelV2.get(i);
                if(PokUtils.isSelected(channelItem, "CHANNELNAME", "386") && PokUtils.isSelected(channelItem, "ROUTESTOMKTG", "110"))
                {
                    if(checkRfaGeoUS(channelItem) || checkRfaGeoAP(channelItem) || checkRfaGeoLA(channelItem) || checkRfaGeoCAN(channelItem))
                    {
                        pdsAndQ.put("P015A", "Y");
                        y = true;
                        break;
                    }
                }
            }
        }//end of if(!y)

        if(!y)
        {
            for(int i = 0; i < channelV3.size(); i++)
            {
                EntityItem channelItem = (EntityItem) channelV3.get(i);
                if(PokUtils.isSelected(channelItem, "CHANNELNAME", "386") && PokUtils.isSelected(channelItem, "ROUTESTOMKTG", "110"))
                {
                    if(checkRfaGeoUS(channelItem) || checkRfaGeoAP(channelItem) || checkRfaGeoLA(channelItem) || checkRfaGeoCAN(channelItem))
                    {
                        pdsAndQ.put("P015A", "Y");
                        y = true;
                        break;
                    }
                }
            }
        }//end of if(!y)

        if(!y)
        {
            pdsAndQ.put("P015A", "N");
        }
    }

    private void deriveP015B()
    {
        boolean y = false;

        for(int i = 0; i < channelV1.size(); i++)
        {
            EntityItem channelItem = (EntityItem) channelV1.get(i);
            if(PokUtils.isSelected(channelItem, "CHANNELNAME", "375") && PokUtils.isSelected(channelItem, "ROUTESTOMKTG", "110"))
            {
                if(checkRfaGeoUS(channelItem) || checkRfaGeoAP(channelItem) || checkRfaGeoLA(channelItem) || checkRfaGeoCAN(channelItem))
                {
                    pdsAndQ.put("P015B", "Y");
                    y = true;
                    break;
                }
            }
        }

        if(!y)
        {
            for(int i = 0; i < channelV2.size(); i++)
            {
                EntityItem channelItem = (EntityItem) channelV2.get(i);
                if(PokUtils.isSelected(channelItem, "CHANNELNAME", "375") && PokUtils.isSelected(channelItem, "ROUTESTOMKTG", "110"))
                {
                    if(checkRfaGeoUS(channelItem) || checkRfaGeoAP(channelItem) || checkRfaGeoLA(channelItem) || checkRfaGeoCAN(channelItem))
                    {
                        pdsAndQ.put("P015B", "Y");
                        y = true;
                        break;
                    }
                }
            }
        }//end of if(!y)

        if(!y)
        {
            for(int i = 0; i < channelV3.size(); i++)
            {
                EntityItem channelItem = (EntityItem) channelV3.get(i);
                if(PokUtils.isSelected(channelItem, "CHANNELNAME", "375") && PokUtils.isSelected(channelItem, "ROUTESTOMKTG", "110"))
                {
                    if(checkRfaGeoUS(channelItem) || checkRfaGeoAP(channelItem) || checkRfaGeoLA(channelItem) || checkRfaGeoCAN(channelItem))
                    {
                        pdsAndQ.put("P015B", "Y");
                        y = true;
                        break;
                    }
                }
            }
        }//end of if(!y)

        if(!y)
        {
            pdsAndQ.put("P015B", "N");
        }
    }

    private void deriveP015C()
    {
        boolean y = false;

        for(int i = 0; i < channelV1.size(); i++)
        {
            EntityItem channelItem = (EntityItem) channelV1.get(i);
            if(PokUtils.isSelected(channelItem, "CHANNELNAME", "387") && PokUtils.isSelected(channelItem, "ROUTESTOMKTG", "110"))
            {
                if(checkRfaGeoUS(channelItem) || checkRfaGeoAP(channelItem) || checkRfaGeoLA(channelItem) || checkRfaGeoCAN(channelItem))
                {
                    pdsAndQ.put("P015C", "Y");
                    y = true;
                    break;
                }
            }
        }

        if(!y)
        {
            for(int i = 0; i < channelV2.size(); i++)
            {
                EntityItem channelItem = (EntityItem) channelV2.get(i);
                if(PokUtils.isSelected(channelItem, "CHANNELNAME", "387") && PokUtils.isSelected(channelItem, "ROUTESTOMKTG", "110"))
                {
                    if(checkRfaGeoUS(channelItem) || checkRfaGeoAP(channelItem) || checkRfaGeoLA(channelItem) || checkRfaGeoCAN(channelItem))
                    {
                        pdsAndQ.put("P015C", "Y");
                        y = true;
                        break;
                    }
                }
            }
        }//end of if(!y)

        if(!y)
        {
            for(int i = 0; i < channelV3.size(); i++)
            {
                EntityItem channelItem = (EntityItem) channelV3.get(i);
                if(PokUtils.isSelected(channelItem, "CHANNELNAME", "387") && PokUtils.isSelected(channelItem, "ROUTESTOMKTG", "110"))
                {
                    if(checkRfaGeoUS(channelItem) || checkRfaGeoAP(channelItem) || checkRfaGeoLA(channelItem) || checkRfaGeoCAN(channelItem))
                    {
                        pdsAndQ.put("P015C", "Y");
                        y = true;
                        break;
                    }
                }
            }
        }//end of if(!y)

        if(!y)
        {
            pdsAndQ.put("P015C", "N");
        }
    }

    //Need to ask Alan about P015D if it needs ROUTESTOMKTG and also XR_CHANNELNAME_376 is already used in P015B ???
//    private void deriveP015D()
//    {
//        for(int i = 0; i < channelV1.size(); i++)
//        {
//            EntityItem channelItem = (EntityItem) channelV1.get(i);
//            if(PokUtils.isSelected(channelItem, "CHANNELNAME", SVConstants.XR_CHANNELNAME_376) && PokUtils.isSelected(channelItem, "ROUTESTOMKTG", "110"))
//            {
//                if(checkRfaGeoUS(channelItem) || checkRfaGeoAP(channelItem) || checkRfaGeoLA(channelItem) || checkRfaGeoCAN(channelItem))
//                {
//                    pdsAndQ.put("P015D", "Y");
//                    return;
//                }
//            }
//        }

//        for(int i = 0; i < channelV2.size(); i++)
//        {
//            EntityItem channelItem = (EntityItem) channelV2.get(i);
//            if(PokUtils.isSelected(channelItem, "CHANNELNAME", SVConstants.XR_CHANNELNAME_376) && PokUtils.isSelected(channelItem, "ROUTESTOMKTG", "110"))
//            {
//                if(checkRfaGeoUS(channelItem) || checkRfaGeoAP(channelItem) || checkRfaGeoLA(channelItem) || checkRfaGeoCAN(channelItem))
//                {
//                    pdsAndQ.put("P015D", "Y");
//                    return;
//                }
//            }
//        }

//        for(int i = 0; i < channelV3.size(); i++)
//        {
//            EntityItem channelItem = (EntityItem) channelV3.get(i);
//            if(PokUtils.isSelected(channelItem, "CHANNELNAME", SVConstants.XR_CHANNELNAME_376) && PokUtils.isSelected(channelItem, "ROUTESTOMKTG", "110"))
//            {
//                if(checkRfaGeoUS(channelItem) || checkRfaGeoAP(channelItem) || checkRfaGeoLA(channelItem) || checkRfaGeoCAN(channelItem))
//                {
//                    pdsAndQ.put("P015D", "Y");
//                    return;
//                }
//            }
//        }

//        pdsAndQ.put("P015D", "N");
//    }

    //Need to ask Alan about P015E if it needs ROUTESTOMKTG and also XR_CHANNELNAME_377 is already used in P015C ???
//    private void deriveP015E()
//    {
//        for(int i = 0; i < channelV1.size(); i++)
//        {
//            EntityItem channelItem = (EntityItem) channelV1.get(i);
//            if(PokUtils.isSelected(channelItem, "CHANNELNAME", SVConstants.XR_CHANNELNAME_377) && PokUtils.isSelected(channelItem, "ROUTESTOMKTG", "110"))
//            {
//                if(checkRfaGeoUS(channelItem) || checkRfaGeoAP(channelItem) || checkRfaGeoLA(channelItem) || checkRfaGeoCAN(channelItem))
//                {
//                    pdsAndQ.put("P015E", "Y");
//                    return;
//                }
//            }
//        }

//        for(int i = 0; i < channelV2.size(); i++)
//        {
//            EntityItem channelItem = (EntityItem) channelV2.get(i);
//            if(PokUtils.isSelected(channelItem, "CHANNELNAME", SVConstants.XR_CHANNELNAME_377) && PokUtils.isSelected(channelItem, "ROUTESTOMKTG", "110"))
//            {
//                if(checkRfaGeoUS(channelItem) || checkRfaGeoAP(channelItem) || checkRfaGeoLA(channelItem) || checkRfaGeoCAN(channelItem))
//                {
//                    pdsAndQ.put("P015E", "Y");
//                    return;
//                }
//            }
//        }

//        for(int i = 0; i < channelV3.size(); i++)
//        {
//            EntityItem channelItem = (EntityItem) channelV3.get(i);
//            if(PokUtils.isSelected(channelItem, "CHANNELNAME", SVConstants.XR_CHANNELNAME_377) && PokUtils.isSelected(channelItem, "ROUTESTOMKTG", "110"))
//            {
//                if(checkRfaGeoUS(channelItem) || checkRfaGeoAP(channelItem) || checkRfaGeoLA(channelItem) || checkRfaGeoCAN(channelItem))
//                {
//                    pdsAndQ.put("P015E", "Y");
//                    return;
//                }
//            }
//        }

//        pdsAndQ.put("P015E", "N");
//    }

    private void deriveP015F()
    {
        boolean y = false;

        for(int i = 0; i < channelV1.size(); i++)
        {
            EntityItem channelItem = (EntityItem) channelV1.get(i);
            if(PokUtils.isSelected(channelItem, "CHANNELNAME", SVConstants.XR_CHANNELNAME_378) && PokUtils.isSelected(channelItem, "ROUTESTOMKTG", "110"))
            {
                if(checkRfaGeoUS(channelItem) || checkRfaGeoAP(channelItem) || checkRfaGeoLA(channelItem) || checkRfaGeoCAN(channelItem))
                {
                    pdsAndQ.put("P015F", "Y");
                    y = true;
                    break;
                }
            }
        }

        if(!y)
        {
            for(int i = 0; i < channelV2.size(); i++)
            {
                EntityItem channelItem = (EntityItem) channelV2.get(i);
                if(PokUtils.isSelected(channelItem, "CHANNELNAME", SVConstants.XR_CHANNELNAME_378) && PokUtils.isSelected(channelItem, "ROUTESTOMKTG", "110"))
                {
                    if(checkRfaGeoUS(channelItem) || checkRfaGeoAP(channelItem) || checkRfaGeoLA(channelItem) || checkRfaGeoCAN(channelItem))
                    {
                        pdsAndQ.put("P015F", "Y");
                        y = true;
                        break;
                    }
                }
            }
        }//end of if(!y)

        if(!y)
        {
            for(int i = 0; i < channelV3.size(); i++)
            {
                EntityItem channelItem = (EntityItem) channelV3.get(i);
                if(PokUtils.isSelected(channelItem, "CHANNELNAME", SVConstants.XR_CHANNELNAME_378) && PokUtils.isSelected(channelItem, "ROUTESTOMKTG", "110"))
                {
                    if(checkRfaGeoUS(channelItem) || checkRfaGeoAP(channelItem) || checkRfaGeoLA(channelItem) || checkRfaGeoCAN(channelItem))
                    {
                        pdsAndQ.put("P015F", "Y");
                        y = true;
                        break;
                    }
                }
            }
        }//end of if(!y)

        if(!y)
        {
            pdsAndQ.put("P015F", "N");
        }
    }

    private void deriveP015G()
    {
        boolean y = false;

        for(int i = 0; i < channelV1.size(); i++)
        {
            EntityItem channelItem = (EntityItem) channelV1.get(i);
            if(PokUtils.isSelected(channelItem, "CHANNELNAME", SVConstants.XR_CHANNELNAME_381) && PokUtils.isSelected(channelItem, "ROUTESTOMKTG", "110"))
            {
                if(checkRfaGeoUS(channelItem) || checkRfaGeoAP(channelItem) || checkRfaGeoLA(channelItem) || checkRfaGeoCAN(channelItem))
                {
                    pdsAndQ.put("P015G", "Y");
                    y = true;
                    break;
                }
            }
        }

        if(!y)
        {
            for(int i = 0; i < channelV2.size(); i++)
            {
                EntityItem channelItem = (EntityItem) channelV2.get(i);
                if(PokUtils.isSelected(channelItem, "CHANNELNAME", SVConstants.XR_CHANNELNAME_381) && PokUtils.isSelected(channelItem, "ROUTESTOMKTG", "110"))
                {
                    if(checkRfaGeoUS(channelItem) || checkRfaGeoAP(channelItem) || checkRfaGeoLA(channelItem) || checkRfaGeoCAN(channelItem))
                    {
                        pdsAndQ.put("P015G", "Y");
                        y = true;
                        break;
                    }
                }
            }
        }//end of if(!y)

        if(!y)
        {
            for(int i = 0; i < channelV3.size(); i++)
            {
                EntityItem channelItem = (EntityItem) channelV3.get(i);
                if(PokUtils.isSelected(channelItem, "CHANNELNAME", SVConstants.XR_CHANNELNAME_381) && PokUtils.isSelected(channelItem,  "ROUTESTOMKTG", "110"))
                {
                    if(checkRfaGeoUS(channelItem) || checkRfaGeoAP(channelItem) || checkRfaGeoLA(channelItem) || checkRfaGeoCAN(channelItem))
                    {
                        pdsAndQ.put("P015G", "Y");
                        y = true;
                        break;
                    }
                }
            }
        }//end of if(!y)

        if(!y)
        {
            pdsAndQ.put("P015G", "N");
        }
    }

    private void deriveP015HIJKL(EntityItem ea)
    {
        // Process RFA Geo questions
        log("before invoking isRfaGeoUS API");
        if(checkRfaGeoUS(ea))
        {
            pdsAndQ.put("P015H", "Y");
        }
        else
        {
            pdsAndQ.put("P015H", "N");
        }

        log("after invoking isRfaGeoUS API and before invoking isRfaGeoLA API");
        if(checkRfaGeoLA(ea))
        {
            pdsAndQ.put("P015I", "Y");
        }
        else
        {
            pdsAndQ.put("P015I", "N");
        }
        log("after invoking isRfaGeoLA API and before invoking isRfaGeoCAN API");
        if(checkRfaGeoCAN(ea))
        {
            pdsAndQ.put("P015J", "Y");
        }
        else
        {
            pdsAndQ.put("P015J", "N");
        }
        log("after invoking isRfaGeoLA API and before invoking isRfaGeoEMEA API");
        if(checkRfaGeoEMEA(ea))
        {
            pdsAndQ.put("P015K", "Y");
        }
        else
        {
            pdsAndQ.put("P015K", "N");
        }
        log("after invoking isRfaGeoEMEA API and before invoking isRfaGeoAP API");
        if(checkRfaGeoAP(ea))
        {
            pdsAndQ.put("P015L", "Y");
        }
        else
        {
            pdsAndQ.put("P015L", "N");
        }
    }

    private void deriveP016B()
    {
        boolean y = false;

        for(int i = 0; i < channelV1.size(); i++)
        {
            EntityItem channelItem = (EntityItem) channelV1.get(i);
            if(PokUtils.isSelected(channelItem, "CHANNELNAME", "379") && PokUtils.isSelected(channelItem, "ROUTESTOMKTG", "110"))
            {
                if(checkRfaGeoUS(channelItem) || checkRfaGeoAP(channelItem) || checkRfaGeoLA(channelItem) || checkRfaGeoCAN(channelItem))
                {
                    pdsAndQ.put("P016B", "Y");
                    y = true;
                    break;
                }
            }
        }

        if(!y)
        {
            for(int i = 0; i < channelV2.size(); i++)
            {
                EntityItem channelItem = (EntityItem) channelV2.get(i);
                if(PokUtils.isSelected(channelItem, "CHANNELNAME", "379") && PokUtils.isSelected(channelItem, "ROUTESTOMKTG", "110"))
                {
                    if(checkRfaGeoUS(channelItem) || checkRfaGeoAP(channelItem) || checkRfaGeoLA(channelItem) || checkRfaGeoCAN(channelItem))
                    {
                        pdsAndQ.put("P016B", "Y");
                        y = true;
                        break;
                    }
                }
            }
        }//end of if(!y)

        if(!y)
        {
            for(int i = 0; i < channelV3.size(); i++)
            {
                EntityItem channelItem = (EntityItem) channelV3.get(i);
                if(PokUtils.isSelected(channelItem, "CHANNELNAME", "379") && PokUtils.isSelected(channelItem, "ROUTESTOMKTG", "110"))
                {
                    if(checkRfaGeoUS(channelItem) || checkRfaGeoAP(channelItem) || checkRfaGeoLA(channelItem) || checkRfaGeoCAN(channelItem))
                    {
                        pdsAndQ.put("P016B", "Y");
                        y = true;
                        break;
                    }
                }
            }
        }//end of if(!y)

        if(!y)
        {
            pdsAndQ.put("P016B", "N");
        }
    }

    private void deriveP016C()
    {
        boolean y = false;

        for(int i = 0; i < channelV1.size(); i++)
        {
            EntityItem channelItem = (EntityItem) channelV1.get(i);
            if(PokUtils.isSelected(channelItem, "CHANNELNAME", "380") && PokUtils.isSelected(channelItem, "ROUTESTOMKTG", "110"))
            {
                if(checkRfaGeoUS(channelItem) || checkRfaGeoAP(channelItem) || checkRfaGeoLA(channelItem) || checkRfaGeoCAN(channelItem))
                {
                    pdsAndQ.put("P016C", "Y");
                    y = true;
                    break;
                }
            }
        }

        if(!y)
        {
            for(int i = 0; i < channelV2.size(); i++)
            {
                EntityItem channelItem = (EntityItem) channelV2.get(i);
                if(PokUtils.isSelected(channelItem, "CHANNELNAME", "380") && PokUtils.isSelected(channelItem, "ROUTESTOMKTG", "110"))
                {
                    if(checkRfaGeoUS(channelItem) || checkRfaGeoAP(channelItem) || checkRfaGeoLA(channelItem) || checkRfaGeoCAN(channelItem))
                    {
                        pdsAndQ.put("P016C", "Y");
                        y = true;
                        break;
                    }
                }
            }
        }//end of if(!y)

        if(!y)
        {
            for(int i = 0; i < channelV3.size(); i++)
            {
                EntityItem channelItem = (EntityItem) channelV3.get(i);
                if(PokUtils.isSelected(channelItem, "CHANNELNAME", "380") && PokUtils.isSelected(channelItem, "ROUTESTOMKTG", "110"))
                {
                    if(checkRfaGeoUS(channelItem) || checkRfaGeoAP(channelItem) || checkRfaGeoLA(channelItem) || checkRfaGeoCAN(channelItem))
                    {
                        pdsAndQ.put("P016C", "Y");
                        y = true;
                        break;
                    }
                }
            }
        }//end of if(!y)

        if(!y)
        {
            pdsAndQ.put("P016C", "N");
        }
    }

    private void deriveP031A()
    {
        boolean y = false;

        for(int i = 0; i < channelV1.size(); i++)
        {
            EntityItem channelItem = (EntityItem) channelV1.get(i);
            if(PokUtils.isSelected(channelItem, "CHANNELNAME", SVConstants.XR_CHANNELNAME_381) && PokUtils.isSelected(channelItem, "ROUTESTOMKTG", "110"))
            {
                pdsAndQ.put("P031A", "Y");
                y = true;
                break;
            }
        }

        if(!y)
        {
            for(int i = 0; i < channelV2.size(); i++)
            {
                EntityItem channelItem = (EntityItem) channelV2.get(i);
                if(PokUtils.isSelected(channelItem, "CHANNELNAME", SVConstants.XR_CHANNELNAME_381) && PokUtils.isSelected(channelItem,  "ROUTESTOMKTG", "110"))
                {
                    pdsAndQ.put("P031A", "Y");
                    y = true;
                    break;
                }
            }
        }//end of if(!y)

        if(!y)
        {
            for(int i = 0; i < channelV3.size(); i++)
            {
                EntityItem channelItem = (EntityItem) channelV3.get(i);
                if(PokUtils.isSelected(channelItem, "CHANNELNAME", SVConstants.XR_CHANNELNAME_381) && PokUtils.isSelected(channelItem,  "ROUTESTOMKTG", "110"))
                {
                    pdsAndQ.put("P031A", "Y");
                    y = true;
                    break;
                }
            }
        }//end of if(!y)

        if(!y)
        {
            pdsAndQ.put("P031A", "N");
        }
    }

    /********************************************************************************
    *
    *
    * @param str String
    * @param n int
    * @returns String
    */
    private String parseString(String str, int n)
    {
        int i = n - 1;
        String result = "";
        int index = I_N3;
        Vector pos = new Vector(1);

        if((0 == n) || (n < 0))
        {
            result = str;
        }
        else if(0 == i)
        {
            result = str.substring(0, str.indexOf("<:>"));
        }
        else
        {
            while(true)
            {
                index = str.indexOf("<:>", index + 3);

                if(index < 0)
                {
                    break;
                }

                pos.addElement(new Integer(index));
            }//end of while(true)

            if(i > pos.size())
            {
                result = str;
            }
            else if(i == pos.size())
            {
                result = str.substring(((Integer) pos.get(i - 1)).intValue() + 3);
            }
            else
            {
                result = str.substring(((Integer) pos.get(i - 1)).intValue() + 3, ((Integer) pos.get(i)).intValue());
            }
        }//end of else

        return result;
    }

//    /********************************************************************************
//    *
//    *
//    * @param str String
//    * @param n int
//    * @returns String
//    */
//    private String parseString(String str, int n)
//    {
        //Java StringTokenizer works only when there is no empty string before delimiter and after delimiter
//        StringTokenizer st;
//        String result = "";
//        int i = 0;

//        if((0 == n) || (n < 0))
//        {
//            result = str;
//        }
//        else
//        {
//            st = new StringTokenizer(str, "<:>");

//            while(st.hasMoreTokens())
//            {
//                result = st.nextToken();
//                i++;
//                if(i == n)
//                {
//                    break;
//                }
//            }

//            if(n > i)
//            {
//                result = str;
//            }
//        }
//
//        return result;
//    }
}
