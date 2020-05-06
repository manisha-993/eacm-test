// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
//[]--------------------------------------------------------------------------[]
//|    Application Name: e-announce                                            |
//|           File Name: WWCustLetter_IGSSVS.java                              |
//|----------------------------------------------------------------------------|
//|          Programmer: Anhtuan Nguyen                                        |
//|        Date written: August 12, 2003                                       |
//|         Environment: Operating System: Windows 2000                        |
//|                              Compiler: IBM JDK 1.4                         |
//|----------------------------------------------------------------------------|
//|  Module Description: Worldwide Customer Letter                             |
//|----------------------------------------------------------------------------|
//|        Restrictions: None                                                  |
//|        Dependencies: None                                                  |
//|  NLS Considerations: None                                                  |
//|----------------------------------------------------------------------------|
//|      Change History:                                                       |
//|      Date            Programmer      Description/Comments                  |
//[]--------------------------------------------------------------------------[]
// $Log: WWCustLetter_IGSSVS.java,v $
// Revision 1.6  2006/04/04 01:59:52  anhtuan
// Services RFA Guide Update for version 21.04.
//
// Revision 1.5  2006/03/16 16:00:30  anhtuan
// Services RFA Guide Update for version 20.10.
//
// Revision 1.4  2006/01/26 15:36:26  anhtuan
// AHE copyright.
//
// Revision 1.3  2005/10/04 14:37:50  anhtuan
// Fix Jtest. Jtest does not allow multiple returns from a method.
//
// Revision 1.2  2005/09/29 02:53:44  anhtuan
// fixes.
//
// Revision 1.1  2005/09/14 03:54:50  anhtuan
// Init OIM3.0b
//
// Revision 1.9  2005/08/16 16:45:40  anhtuan
// Show TAIWAN as TAIWAN, Province of China.
//
// Revision 1.8  2005/04/15 19:59:49  anhtuan
// CR0310053420: Services RFA Guide Update for version 19.10 and 20.04.
//
// Revision 1.7  2005/01/31 04:51:28  anhtuan
// CR1123044433.
//
// Revision 1.6  2004/06/03 17:57:30  anhtuan
// Work on TIR USRO-R-JMGR-5ZBQ2U.
//
// Revision 1.5  2004/05/27 18:17:22  anhtuan
// Show "IBM Confidential" at the top of the report.
//
// Revision 1.4  2004/05/25 02:47:31  anhtuan
// Added ; after &nbsp. Used &gt; and &lt; instead of > and <
//
// Revision 1.3  2004/04/28 04:18:04  anhtuan
// New logic for PDS question P014B.
//
// Revision 1.2  2004/04/27 17:45:53  anhtuan
// Worldwide Customer Letter 19.04.
//
// Revision 1.11  2004/01/10 01:45:18  anhtuan
// CR 123103241.
//
// Revision 1.10  2003/11/17 12:09:11  anhtuan
// FB 53025:6DC3EE.
//
// Revision 1.9  2003/11/05 03:18:44  anhtuan
// Show (IBM GUIDE 18.09).
//
// Revision 1.8  2003/11/03 17:14:16  anhtuan
// Adjust Geo Column Dynamically.
//
// Revision 1.7  2003/10/28 20:52:50  anhtuan
// Show ANNNUMBER.
//
// Revision 1.6  2003/10/28 16:36:56  anhtuan
// Merge SOF Plan Availability table, CMPNT Plan Availability table and FEATURE Plan Availability table into one table.
//
// Revision 1.5  2003/10/26 22:48:46  anhtuan
// New specs: SVC RFA 18.09 102203.xls, V1.2 RFA SVC DI 102203.doc.
//
// Revision 1.4  2003/10/22 02:51:10  anhtuan
// Change column names "Component Name" and "Feature Name" to "Offering Name".
//
// Revision 1.3  2003/10/14 13:58:28  anhtuan
// Change P2Constants to SVConstants
//
// Revision 1.2  2003/10/01 19:09:47  anhtuan
// Fix getData(). Add cleanUp().
//
// Revision 1.1  2003/09/29 23:06:11  anhtuan
// Initial Version. RFA Services Announcement Question And Answer Guide 18.09
//
//

package com.ibm.transform.oim.eacm.util;

import COM.ibm.eannounce.objects.*;
import java.util.*;
import java.text.*;

/**********************************************************************************
* WWCustLetter_IGSSVS class
*
*
*/
public class WWCustLetter_IGSSVS
{
    private EntityList list;
    private GeneralAreaList gal;
    private EntityItem rootEntityItem;

    private StringBuffer sb;

    //Define Hashtable to hold needed PDS and Q&A answers
    private TreeMap pdsAndQ;

    private Hashtable usGeoHT;
    private Hashtable apGeoHT;
    private Hashtable laGeoHT;
    private Hashtable canGeoHT;
    private Hashtable emeaGeoHT;

    //Define Hashtable to hold geo information
    private Hashtable geoHT;

    private Vector availVector;
    private Vector sofVector;
    private Vector cmpntVector;
    private Vector featureVector;

    private Hashtable htA0208;

    private Hashtable mktgNameHT;

    private boolean showMktgName;

    private boolean lob;

    private StringBuffer debugBuffer;

    private static final int LIMIT1 = 130;
    private static final int LIMIT2 = 60;
    private static final int L1 = 22;

    private static final int I_N3 = -3;

    private static final char[] FOOL_JTEST = {'\n'};
    static final String NEWLINE = new String(FOOL_JTEST);

    /**********************************************************************************
    * Constructor
    *
    * @param    aList EntityList
    * @param    aGal GeneralAreaList
    */
    public WWCustLetter_IGSSVS(EntityList aList, GeneralAreaList aGal)
    {
        list = aList;
        gal = aGal;

        rootEntityItem = null;
        sb = new StringBuffer();
        pdsAndQ = new TreeMap();
        usGeoHT = new Hashtable();
        apGeoHT = new Hashtable();
        laGeoHT = new Hashtable();
        canGeoHT = new Hashtable();
        emeaGeoHT = new Hashtable();
        geoHT = new Hashtable();
        availVector = null;
        sofVector = null;
        cmpntVector = null;
        featureVector = null;
        htA0208 = new Hashtable();
        showMktgName = false;
        lob = false;
        mktgNameHT = new Hashtable();
        debugBuffer = new StringBuffer();
    }

    /**********************************************************************************
    * Get TreeMap pdsAndQ
    *
    * @returns TreeMap
    */
    public TreeMap getPdsAndQ()
    {
        return pdsAndQ;
    }

    private boolean isYes(String key)
    {
        return Boolean.TRUE.equals(pdsAndQ.get(key));
    }

    private boolean isNo(String key)
    {
        return Boolean.FALSE.equals(pdsAndQ.get(key));
    }

    private boolean isValue(String key, String value)
    {
        boolean rc;
        if(value == null)
        {
            rc = !pdsAndQ.containsKey(key);
        }
        else
        {
            rc = value.equals(pdsAndQ.get(key));
        }

        return rc;
    }

    /**********************************************************************************
    * Initialize
    *
    * @returns boolean
    */
    public boolean initWWCustLetter()
    {
        boolean rc = true;
        EntityGroup eg = list.getParentEntityGroup();
        list.getEntityGroup("ANNOUNCEMENT");
        if (eg != null && eg.getEntityItemCount() == 1)
        {
            rootEntityItem = eg.getEntityItem(0);
            availVector = PokUtils.getAllLinkedEntities(eg, "ANNAVAILA", "AVAIL");
            sofVector = getParentEntities(availVector, "SOFAVAIL", "SOF");
            cmpntVector = getParentEntities(availVector, "CMPNTAVAIL", "CMPNT");
            featureVector = getParentEntities(availVector, "FEATUREAVAIL", "FEATURE");

            if((sofVector.size() + cmpntVector.size() + featureVector.size()) > 1)
            {
                showMktgName = true;
            }

            lob = PokUtils.isSelected(rootEntityItem, "LOB", SVConstants.XR_LOB_101);

            populateMktgNameHT();

            derivePDSAnswers();
            deriveQAnswers();

            rc = true;
        }
        else
        {
            rc = false;
        }

        return rc;
    }

    private void populateMktgNameHT()
    {
        String mktgName;

        for(int i = 0; i < sofVector.size(); i++)
        {
            EntityItem sofItem = (EntityItem) sofVector.get(i);
            mktgNameHT.put(sofItem.getKey(), PokUtils.getAttributeValue(sofItem, "MKTGNAME", "|", "No User Input Found for Attribute MKTGNAME Of Entity SOF"));
        }

        for(int i = 0; i < cmpntVector.size(); i++)
        {
            EntityItem cmpntItem = (EntityItem) cmpntVector.get(i);
            String cat = PokUtils.getAttributeValue(cmpntItem, "ITSCMPNTCATNAME", "|", "", false);

            if((lob) && (null != cat) && (cat.length() > 0))
            {
                mktgName = cat + " " + PokUtils.getAttributeValue(cmpntItem, "MKTGNAME", "|", "No User Input Found for Attribute MKTGNAME Of Entity CMPNT");
            }
            else
            {
                //1 to 1 relationship between CMPNT and SOF
                Vector sofV = PokUtils.getAllLinkedEntities(cmpntItem, "SOFCMPNT", "SOF");

                if(sofV.size() == 0)
                {
                    mktgName = "No Parent SOF Found - " + PokUtils.getAttributeValue(cmpntItem, "MKTGNAME", "|", "No User Input Found for Attribute MKTGNAME Of Entity CMPNT");
                }
                else
                {
                    mktgName = PokUtils.getAttributeValue((EntityItem) sofV.get(0), "MKTGNAME", "|", "No User Input Found for Attribute MKTGNAME Of Entity SOF - ") + " " + PokUtils.getAttributeValue(cmpntItem, "MKTGNAME", "|", "No User Input Found for Attribute MKTGNAME Of Entity CMPNT");
                }
            }//end of else

            mktgNameHT.put(cmpntItem.getKey(), mktgName);
        }//end of for(int i = 0; i < cmpntVector.size(); i++)

        for(int i = 0; i < featureVector.size(); i++)
        {
            EntityItem featureItem = (EntityItem) featureVector.get(i);

            //1 to 1 relationship between FEATURE and CMPNT
            Vector cmpntV = PokUtils.getAllLinkedEntities(featureItem, "CMPNTFEATURE", "CMPNT");

            if(cmpntV.size() == 0)
            {
                mktgName = "No Grandparent SOF Found - No Parent CMPNT Found - " + PokUtils.getAttributeValue(featureItem, "MKTGNAME", "|", "No User Input Found for Attribute MKTGNAME Of Entity FEATURE");
            }
            else
            {
                EntityItem cmpntItem = (EntityItem) cmpntV.get(0);
                String cat = PokUtils.getAttributeValue(cmpntItem, "ITSCMPNTCATNAME", "|", "", false);

                if((lob) && (null != cat) && (cat.length() > 0))
                {
                    mktgName = cat + " " + PokUtils.getAttributeValue(cmpntItem, "MKTGNAME", "|", "No User Input Found for Attribute MKTGNAME Of Entity CMPNT") + " " + PokUtils.getAttributeValue(featureItem, "MKTGNAME", "|", "No User Input Found for Attribute MKTGNAME Of Entity FEATURE");
                }
                else
                {
                    //1 to 1 relationship between CMPNT and SOF
                    Vector sofV = PokUtils.getAllLinkedEntities(cmpntItem, "SOFCMPNT", "SOF");

                    if(sofV.size() == 0)
                    {
                        mktgName = "No Grandparent SOF Found - " + PokUtils.getAttributeValue(cmpntItem, "MKTGNAME", "|", "No User Input Found for Attribute MKTGNAME Of Entity CMPNT - ") + " " + PokUtils.getAttributeValue(featureItem, "MKTGNAME", "|", "No User Input Found for Attribute MKTGNAME Of Entity FEATURE");
                    }
                    else
                    {
                        mktgName = PokUtils.getAttributeValue((EntityItem) sofV.get(0), "MKTGNAME", "|", "No User Input Found for Attribute MKTGNAME Of Entity SOF - ") + " " + PokUtils.getAttributeValue(cmpntItem, "MKTGNAME", "|", "No User Input Found for Attribute MKTGNAME Of Entity CMPNT - ") + " " + PokUtils.getAttributeValue(featureItem, "MKTGNAME", "|", "No User Input Found for Attribute MKTGNAME Of Entity FEATURE");
                    }
                }//end of else
            }//end of else

            mktgNameHT.put(featureItem.getKey(), mktgName);
        }//end for(int i = 0; i < featureVector.size(); i++)
    }

    private void derivePDSAnswers()
    {
        //P014B
        //P015H
        //P030A, P030B, P030C
        //IDKEYC
        //P015J
        //P017L
        //P017M
        //P015K
        //P015M

        deriveP014B();
        deriveP015H();
        deriveP030A();
        deriveP030B();
        deriveP030C();
        deriveIDKEYC();

        //Begin of TIR ID # - USRO-R-JMGR-5ZBQ2U
        deriveP015J();
        deriveP017L();
        deriveP017M();
        deriveP015K();
        deriveP015M();
        //End of TIR ID # - USRO-R-JMGR-5ZBQ2U

        //19.10
        deriveP015L();

        //20.10
        deriveP016A();
    }

    private void deriveP014B()
    {
        boolean y = false;
        Vector channelVector = PokUtils.getAllLinkedEntities(sofVector, "SOFCHANNEL", "CHANNEL");

        for(int i = 0; i < channelVector.size(); i++)
        {
            EntityItem channelItem = (EntityItem) channelVector.get(i);
            if(PokUtils.isSelected(channelItem, "CHANNELNAME", "374") && PokUtils.isSelected(channelItem, "ROUTESTOMKTG", "110"))
            {
                if(checkRfaGeoUS(channelItem) || checkRfaGeoAP(channelItem) || checkRfaGeoLA(channelItem) || checkRfaGeoCAN(channelItem))
                {
                    pdsAndQ.put("P014B", new Boolean(true));
                    y = true;
                    break;
                }
            }
        }

        if(!y)
        {
            channelVector = PokUtils.getAllLinkedEntities(cmpntVector, "CMPNTCHANNEL", "CHANNEL");

            for(int i = 0; i < channelVector.size(); i++)
            {
                EntityItem channelItem = (EntityItem) channelVector.get(i);
                if(PokUtils.isSelected(channelItem, "CHANNELNAME", "374") && PokUtils.isSelected(channelItem, "ROUTESTOMKTG", "110"))
                {
                    if(checkRfaGeoUS(channelItem) || checkRfaGeoAP(channelItem) || checkRfaGeoLA(channelItem) || checkRfaGeoCAN(channelItem))
                    {
                        pdsAndQ.put("P014B", new Boolean(true));
                        y = true;
                        break;
                    }
                }
            }
        }

        if(!y)
        {
            channelVector = PokUtils.getAllLinkedEntities(featureVector, "FEATURECHANNEL", "CHANNEL");

            for(int i = 0; i < channelVector.size(); i++)
            {
                EntityItem channelItem = (EntityItem) channelVector.get(i);
                if(PokUtils.isSelected(channelItem, "CHANNELNAME", "374") && PokUtils.isSelected(channelItem, "ROUTESTOMKTG", "110"))
                {
                    if(checkRfaGeoUS(channelItem) || checkRfaGeoAP(channelItem) || checkRfaGeoLA(channelItem) || checkRfaGeoCAN(channelItem))
                    {
                        pdsAndQ.put("P014B", new Boolean(true));
                        y = true;
                        break;
                    }
                }
            }
        }

        if(!y)
        {
            pdsAndQ.put("P014B", new Boolean(false));
        }
    }

    private void deriveP015H()
    {
        if (gal.isRfaGeoUS(rootEntityItem))
        {
            pdsAndQ.put("P015H", new Boolean(true));
        }
        else
        {
            pdsAndQ.put("P015H", new Boolean(false));
        }
    }

    private void deriveP030A()
    {
        if(PokUtils.isSelected(rootEntityItem, "OFFERINGACCESS", SVConstants.XR_OFFERINGACCESS_2889))
        {
            pdsAndQ.put("P030A", new Boolean(true));
        }
        else
        {
            pdsAndQ.put("P030A", new Boolean(false));
        }
    }

    private void deriveP030B()
    {
        if(PokUtils.isSelected(rootEntityItem, "MARKETEDIBMLOGO", SVConstants.XR_MARKETEDIBMLOGO_2840))
        {
            pdsAndQ.put("P030B", new Boolean(true));
        }
        else
        {
            pdsAndQ.put("P030B", new Boolean(false));
        }
    }

    private void deriveP030C()
    {
        if(PokUtils.isSelected(rootEntityItem, "LOGOACCESSREQTS", SVConstants.XR_LOGOACCESSREQTS_2833))
        {
            pdsAndQ.put("P030C", new Boolean(true));
        }
        else
        {
            pdsAndQ.put("P030C", new Boolean(false));
        }
    }

    private void deriveIDKEYC()
    {
        boolean b17A, b17B, b17C, b17D, b17E, b17F, b17G, b17H, b17I, b17J;
        String IDKEYC = "";
        int count = 0;
        b17A = PokUtils.isSelected(rootEntityItem, "CROSSPLATFORM", SVConstants.XR_CROSSPLATFORM_822); //  Yes
        if(b17A)
        {
            IDKEYC = "YE001";
            count++;
        }

        b17B = PokUtils.isSelected(rootEntityItem, "PLATFORM", SVConstants.XR_PLATFORM_4767); // Windows or Windows NT
        if(b17B)
        {
            IDKEYC = "SE001";
            count++;
        }

        b17C = PokUtils.isSelected(rootEntityItem, "PLATFORM", SVConstants.XR_PLATFORM_4770); // AS/400 (iSeries)
        if(b17C)
        {
            IDKEYC = "AE001";
            count++;
        }

        b17D = PokUtils.isSelected(rootEntityItem, "PLATFORM", SVConstants.XR_PLATFORM_4769); // RS/6000 (pSeries)
        if(b17D)
        {
            IDKEYC = "RE001";
            count++;
        }

        b17E = PokUtils.isSelected(rootEntityItem, "PLATFORM", SVConstants.XR_PLATFORM_4772); // OS/2 Software
        if(b17E)
        {
            IDKEYC = "SE001";
            count++;
        }

        b17F = PokUtils.isSelected(rootEntityItem, "PLATFORM", SVConstants.XR_PLATFORM_4764); // Hardware for, or connected to, Main Frame systems
        if(b17F)
        {
            IDKEYC = "ME001";
            count++;
        }

        b17G = PokUtils.isSelected(rootEntityItem, "PLATFORM", SVConstants.XR_PLATFORM_4768); // Main Frame software
        if(b17G)
        {
            IDKEYC = "LE001";
            count++;
        }

        b17H = PokUtils.isSelected(rootEntityItem, "PLATFORM", SVConstants.XR_PLATFORM_4766); // PC Server or RISC S/390
        if(b17H)
        {
            IDKEYC = "LE001";
            count++;
        }

        b17I = PokUtils.isSelected(rootEntityItem, "PLATFORM", SVConstants.XR_PLATFORM_4773); // Network
        if(b17I)
        {
            IDKEYC = "NE001";
            count++;
        }

        b17J = PokUtils.isSelected(rootEntityItem, "OFFERINGTYPES", SVConstants.XR_OFFERINGTYPES_2907); // OFFERINGTYPES = '2907' (IBM Service)
        if(b17J)
        {
            IDKEYC = "VE001";
            count++;
        }

        //P017K is removed from 19.04
        //boolean b17K = PokUtils.isSelected(rootEntityItem, "OFFERINGTYPES", SVConstants.XR_OFFERINGTYPES_2913);  // OFFERINGTYPES = '2913' (IBM Global Financing)
        //if(b17K)
        //{
        //   IDKEYC = "VE001";
        //   count++;
        //}

        if(count > 1)
        {
            if(count == 2 && b17B && b17E)
            {
                IDKEYC = "SE001";
            }
            else
            {
                IDKEYC = "YE001";
            }
        }

        pdsAndQ.put("IDKEYC" , IDKEYC);
    }

    private void deriveP015J()
    {
        if(gal.isRfaGeoCAN(rootEntityItem))
        {
            pdsAndQ.put("P015J", new Boolean(true));
        }
        else
        {
            pdsAndQ.put("P015J", new Boolean(false));
        }
    }

    private void deriveP017L()
    {
        if(PokUtils.isSelected(rootEntityItem, "ELECTRONICSERVICE", SVConstants.XR_ELECTRONICSERVICE_010))
        {
            pdsAndQ.put("P017L", new Boolean(true));
        }
        else
        {
            pdsAndQ.put("P017L", new Boolean(false));
        }
    }

    private void deriveP017M()
    {
        if(PokUtils.isSelected(rootEntityItem, "ELECTRONICSERVICE", SVConstants.XR_ELECTRONICSERVICE_011))
        {
            pdsAndQ.put("P017M", new Boolean(true));
        }
        else
        {
            pdsAndQ.put("P017M", new Boolean(false));
        }
    }

    private void deriveP015K()
    {
        if(gal.isRfaGeoEMEA(rootEntityItem))
        {
            pdsAndQ.put("P015K", new Boolean(true));
        }
        else
        {
            pdsAndQ.put("P015K", new Boolean(false));
        }
    }

    private void deriveP015M()
    {
        if(gal.isRfaGeoEMEA(rootEntityItem))
        {
            //Get General Area Group for EMEA
            GeneralAreaGroup gaGrpEmea = gal.getRfaGeoEMEAInclusion(rootEntityItem);
            int j = gaGrpEmea.getGeneralAreaItemCount();
            if(j >= LIMIT1)
            {
                pdsAndQ.put("P015M", new Boolean(true));
            }
            else
            {
                pdsAndQ.put("P015M", new Boolean(false));
            }
        }
        else
        {
            pdsAndQ.put("P015M", new Boolean(false));
        }
    }

    private void deriveP015L()
    {
        if(gal.isRfaGeoAP(rootEntityItem))
        {
            pdsAndQ.put("P015L", new Boolean(true));
        }
        else
        {
            pdsAndQ.put("P015L", new Boolean(false));
        }
    }

    //20.10
    private void deriveP016A()
    {
        pdsAndQ.put("P016A", new Boolean(false));
    }

    private void deriveQAnswers()
    {
        //A0100
        //A0160
        //A0162
        //A0163
        //A0200 is renumbered to A0202 per RFA Guide 21.04
        //A0204
        //A0206 is renumbered to A0200 per RFA Guide 21.04
        //A0208
        //A0210
        //A0212
        //A0300
        //A0214

        deriveA0100();
        deriveA0160();
        deriveA0162();
        deriveA0163();

        //deriveA0200();
        //A0200 is renumbered to A0202 per RFA Guide 21.04
        deriveA0202();

        deriveA0204();

        //deriveA0206();
        //A0206 is renumbered to A0200 per RFA Guide 21.04
        deriveA0200();

        deriveA0208();
        deriveA0210();
        deriveA0212();
        deriveA0300();

        //Begin of TIR ID # - USRO-R-JMGR-5ZBQ2U
        deriveA0214();
        deriveA0181();
        if(!isYes("A0181"))
        {
            pdsAndQ.put("A0183", new String());
        }
        else
        {
            deriveIFGAnswer("A0183", "MKTGMESEXTERNAL");
        }

        if(!isYes("A0181"))
        {
            pdsAndQ.put("A0184", new String());
        }
        else
        {
            //  The answer is "Required".
            deriveA0184_A0186("A0184", "PROMOELIGIBILITYTCS");
        }

        if(!isYes("A0181") ||
            !isYes("A0184"))
        {
            pdsAndQ.put("A0185", new String());
            pdsAndQ.put("A0186", new String());
        }
        else
        {
            deriveIFGAnswer("A0185", "PROMOELIGIBILITYTCS");
            //  The answer is "Required".
            deriveA0184_A0186("A0186", "MONTHLYPAYMENT");
        }

        if(!isYes("A0181") ||
            !isYes("A0184") ||
            !isYes("A0186"))
        {
            pdsAndQ.put("A0187", new String());
            pdsAndQ.put("A0188", new String());
            pdsAndQ.put("A0189", new String());
        }
        else
        {
            deriveIFGAnswer("A0187", "MONTHLYPAYMENT");
            deriveIFGAnswer("A0188", "PAYMENTTERM");
            deriveIFGAnswer("A0189", "ELIGIBILITYTCS");
        }

        if(isNo("P015K") || isYes("P015M"))
        {
            pdsAndQ.put("A0219", new String());
        }
        else
        {
            deriveA0219();
        }
        //End of TIR ID # - USRO-R-JMGR-5ZBQ2U

        //19.10
        deriveA0220();
    }

    private void deriveA0100()
    {
        pdsAndQ.put("A0100", "<p>" + PokUtils.getAttributeValue(rootEntityItem, "ANNTITLE", "|", "No User Input Found." + "</p>"));
    }

    private void deriveA0160()
    {
        if(!(isNo("P030A") || isNo("P030B")))
        {
            pdsAndQ.put("A0160", PokUtils.getAttributeValue(rootEntityItem, "ACCESPEOWDISABLE", "|", ""));
        }
        else
        {
            pdsAndQ.put("A0160" , "");
        }
    }

    private void deriveA0162()
    {
        if(!(isNo("P030A") || isNo("P015H") || isNo("P030B") || isNo("P030C")))
        {
            pdsAndQ.put("A0162", PokUtils.getAttributeValue(rootEntityItem, "USSEC508", "|", "<p>No User Input Found.</p>"));
        }
        else
        {
            pdsAndQ.put("A0162", "");
        }
    }

    private void deriveA0163()
    {
        if(!(isNo("P030A") || isNo("P015H") || isYes("P030B")))
        {
            pdsAndQ.put("A0163", PokUtils.getAttributeValue(rootEntityItem, "USSEC508LOGO", "|", "<p>No User Input Found.</p>"));
        }
        else
        {
            pdsAndQ.put("A0163", "");
        }
    }

    //private void deriveA0200()
    //A0200 is renumbered to A0202 per RFA Guide 21.04
    private void deriveA0202()
    {
        if(showMktgName)
        {
            getDataWithMktgName("A0202", "OVERVIEWABSTRACT", true, "No User Input Found For Attribute OVERVIEWABSTRACT");
        }
        else
        {
            getDataWithoutMktgName("A0202", "OVERVIEWABSTRACT", true, "No User Input Found For Attribute OVERVIEWABSTRACT");
        }
    }

    private void deriveA0204()
    {
        //If atrribute is empty don't show it.
        if(showMktgName)
        {
            getDataWithMktgName("A0204", "PREREQCOREQ", false);
        }
        else
        {
            getDataWithoutMktgName("A0204", "PREREQCOREQ", false);
        }
    }

    private void getDataWithMktgName(String answerNumber, String attrName, boolean bShow)
    {
        StringBuffer value = new StringBuffer();

        if(bShow)
        {
            value.append("<p>" + PokUtils.getAttributeValue(rootEntityItem, attrName, "|", "") + "</p>");
        }

        value.append(getData(sofVector, attrName).toString());
        value.append(getData(cmpntVector, attrName).toString());
        value.append(getData(featureVector, attrName).toString());

        pdsAndQ.put(answerNumber, value.toString());
    }

    private StringBuffer getData(Vector srcVct, String attrName)
    {
        TreeMap tm = new TreeMap();
        Hashtable ht = new Hashtable();

        StringBuffer value = new StringBuffer();

        String lastGeo = "";
        String currentGeo = "";
        Set keySet;
        Iterator itr;

        for(int i = 0; i < srcVct.size(); i++)
        {
            EntityItem entityItem = (EntityItem) srcVct.get(i);
            String attributeValue = PokUtils.getAttributeValue(entityItem, attrName, "|", "");
            if(!attributeValue.equals("") && !attributeValue.equals("<p></p>"))
            {
                StringBuffer keySB = new StringBuffer((String) mktgNameHT.get(entityItem.getKey()));
                keySB.append(value);
                keySB.append(getGeo(entityItem.getKey(), geoHT));

                tm.put(keySB.toString(), entityItem.getKey());
                ht.put(entityItem.getKey(), entityItem);
            }
        }//end of for(int i = 0; i < sofVector.size(); i++)

        keySet = tm.keySet();
        itr = keySet.iterator();
        while(itr.hasNext())
        {
            String key = (String) itr.next();
            EntityItem entityItem = (EntityItem) ht.get((String) tm.get(key));

            currentGeo = getGeo((String)tm.get(key), geoHT);

            if(!currentGeo.equals(lastGeo))
            {
                if(!lastGeo.equals("WW") && !lastGeo.equals(""))
                {
                    value.append("<p>&lt;---" + lastGeo + "</p>");
                }

                if(!currentGeo.equals("WW"))
                {
                    value.append("<p>" + currentGeo + "---&gt;</p>");
                }

                lastGeo = currentGeo;
            }

            value.append("<p><b>" + (String) mktgNameHT.get(entityItem.getKey()) + "</b><br />");
            value.append(PokUtils.getAttributeValue(entityItem, attrName, "|", "") + "</p>");
        }//end of while(itr.hasNext())

        if(!lastGeo.equals("WW") && !lastGeo.equals(""))
        {
            value.append("<p>&lt;---" + lastGeo + "</p>");
        }

        return value;
    }

    private void getDataWithoutMktgName(String answerNumber, String attrName, boolean bShow)
    {
        StringBuffer valueSB = new StringBuffer();

        if(bShow)
        {
            valueSB.append("<p>" + PokUtils.getAttributeValue(rootEntityItem, attrName, "|", "") + "</p>");
        }

        for(int i = 0; i < sofVector.size(); i++)
        {
            EntityItem entityItem = (EntityItem) sofVector.get(i);
            String geo = getGeo(entityItem.getKey(), geoHT);
            String attributeValue = PokUtils.getAttributeValue(entityItem, attrName, "|", "");

            if(!attributeValue.equals("") && !attributeValue.equals("<p></p>"))
            {
                if(!geo.equals("WW"))
                {
                    valueSB.append("<p>" + geo + "---&gt;</p>");
                }
                valueSB.append("<p>" + attributeValue + "</p>");
                if(!geo.equals("WW"))
                {
                    valueSB.append("<p>&lt;---" + geo + "</p>");
                }
            }
        }

        for(int i = 0; i < cmpntVector.size(); i++)
        {
            EntityItem entityItem = (EntityItem) cmpntVector.get(i);
            String geo = getGeo(entityItem.getKey(), geoHT);

            String attributeValue = PokUtils.getAttributeValue(entityItem, attrName, "|", "");

            if(!attributeValue.equals("") && !attributeValue.equals("<p></p>"))
            {
                if(!geo.equals("WW"))
                {
                    valueSB.append("<p>" + geo + "---&gt;</p>");
                }
                valueSB.append("<p>" + attributeValue + "</p>");
                if(!geo.equals("WW"))
                {
                    valueSB.append("<p>&lt;---" + geo + "</p>");
                }
            }
        }

        for(int i = 0; i < featureVector.size(); i++)
        {
            EntityItem entityItem = (EntityItem) featureVector.get(i);
            String geo = getGeo(entityItem.getKey(), geoHT);

            String attributeValue = PokUtils.getAttributeValue(entityItem, attrName, "|", "");

            if(!attributeValue.equals("") && !attributeValue.equals("<p></p>"))
            {
                if(!geo.equals("WW"))
                {
                    valueSB.append("<p>" + geo + "---&gt;</p>");
                }
                valueSB.append("<p>" + attributeValue + "</p>");
                if(!geo.equals("WW"))
                {
                    valueSB.append("<p>&lt;---" + geo + "</p>");
                }
            }
        }

        pdsAndQ.put(answerNumber, valueSB.toString());
    }

    //private void deriveA0206()
    //A0206 is renumbered to A0200 per RFA Guide 21.04
    private void deriveA0200()
    {
        pdsAndQ.put("A0200", PokUtils.getAttributeValue(rootEntityItem, "ATAGLANCE", "|", "<p>No User Input Found.</p>"));
    }

    private void deriveA0208()
    {
        //Find only AVAIL entities with AVAILTYPE = Planned Availibility
        Vector availV;
        StringBuffer value = new StringBuffer();
        //String geoColumnSize = "1%";
        Hashtable tempHT = new Hashtable();
        tempHT.put("AVAILTYPE", SVConstants.XR_AVAILTYPE_146);
        availV = PokUtils.getEntitiesWithMatchedAttr(availVector, tempHT);

        getHTA0208(availV, "SOFAVAIL", "SOF");
        getHTA0208(availV, "CMPNTAVAIL", "CMPNT");
        getHTA0208(availV, "FEATUREAVAIL", "FEATURE");

        value = new StringBuffer();

        if(htA0208.size() > 0)
        {
            //if(requiredColumnForGeo())
            //   geoColumnSize = "19%";

            //value.append("<table border=\"0\" width=\"100%\">\n");
            //value.append("   <tr>\n");
            //value.append("      <td width=\"" + geoColumnSize + "\">\n");
            //value.append("      </td>\n");
            //value.append("      <td width=\"14%\" valign=\"bottom\">\n");
            //value.append("         <span style=\"text-decoration:underline\"><b>Plan Availability</b></span>\n");
            //value.append("      </td>\n");
            //value.append("      <td width=\"1%\">\n");
            //value.append("      </td>\n");
            //value.append("      <td width=\"66%\" valign=\"bottom\">\n");
            //value.append("         <span style=\"text-decoration:underline\"><b>Offering Name</b></span>\n");
            //value.append("      </td>\n");
            //value.append("   </tr>\n");

            value.append(getDataA0208_method2());

            //value.append("</table>\n");

            pdsAndQ.put("A0208", value.toString());
        }
    }

    private void deriveA0210()
    {
        if(showMktgName)
        {
            getDataWithMktgName("A0210", "DESCRIPTION", true, "No User Input Found For Attribute DESCRIPTION");
        }
        else
        {
            getDataWithoutMktgName("A0210", "DESCRIPTION", true, "No User Input Found For Attribute DESCRIPTION");
        }
    }

    private void deriveA0212()
    {
        Vector satVector = PokUtils.getAllLinkedEntities(rootEntityItem, "ANNST", "STANDAMENDTEXT");

        Iterator satItr = satVector.iterator();
        while(satItr.hasNext())
        {
            EntityItem ei = (EntityItem)satItr.next();
            if(PokUtils.isSelected(ei, "STANDARDAMENDTEXT_TYPE", SVConstants.XR_STANDARDAMENDTEXT_TYPE_130))
            {
                pdsAndQ.put("A0212" , PokUtils.getAttributeValue(ei, "STANDARDAMENDTEXT", "", "", false));
                satVector.clear();
                return;
            }
        }

        satVector.clear();
        pdsAndQ.put("A0212" , "");
    }

    private void deriveA0300()
    {
        if(!(isNo("P014B") || isValue("IDKEYC", "") || isNo("P015H")))
        {
            if(PokUtils.isSelected(rootEntityItem, "AMCALLCENTER", SVConstants.XR_AMCALLCENTER_1138))
            {
                pdsAndQ.put("A0300", "A");
            }
            else if(PokUtils.isSelected(rootEntityItem, "AMCALLCENTER", SVConstants.XR_AMCALLCENTER_1139))
            {
                pdsAndQ.put("A0300", "B");
            }
            else
            {
                pdsAndQ.put("A0300", "");
            }
        }
    }

    private void deriveA0214()
    {
        if(showMktgName)
        {
            getDataA0214();
        }
        else
        {
            pdsAndQ.put("A0214", "<p>For pricing information, contact your IBM representative.</p>");
        }
    }

    private void getDataA0214()
    {
        StringBuffer value = new StringBuffer();

        value.append(getDataA0214(sofVector).toString());
        value.append(getDataA0214(cmpntVector).toString());
        value.append(getDataA0214(featureVector).toString());

        pdsAndQ.put("A0214", value.toString());
    }

    private StringBuffer getDataA0214(Vector srcVct)
    {
        TreeMap tm = new TreeMap();
        Hashtable ht = new Hashtable();

        StringBuffer value = new StringBuffer();

        String lastGeo = "";
        String currentGeo = "";
        Set keySet;
        Iterator itr;

        for(int i = 0; i < srcVct.size(); i++)
        {
            EntityItem entityItem = (EntityItem) srcVct.get(i);
            StringBuffer keySB = new StringBuffer((String) mktgNameHT.get(entityItem.getKey()));
            keySB.append(getGeo(entityItem.getKey(), geoHT));

            tm.put(keySB.toString(), entityItem.getKey());
            ht.put(entityItem.getKey(), entityItem);
        }//end of for(int i = 0; i < sofVector.size(); i++)

        keySet = tm.keySet();
        itr = keySet.iterator();
        while(itr.hasNext())
        {
            String key = (String) itr.next();
            EntityItem entityItem = (EntityItem) ht.get((String) tm.get(key));

            currentGeo = getGeo((String)tm.get(key), geoHT);

            if(!currentGeo.equals(lastGeo))
            {
                if(!lastGeo.equals("WW") && !lastGeo.equals(""))
                {
                    value.append("<p>&lt;---" + lastGeo + "</p>");
                }

                if(!currentGeo.equals("WW"))
                {
                    value.append("<p>" + currentGeo + "---&gt;</p>");
                }

                lastGeo = currentGeo;
            }

            value.append("<p><b>" + (String) mktgNameHT.get(entityItem.getKey()) + "</b><br />");
            value.append("For pricing information, contact your IBM representative.</p>");
        }//end of while(itr.hasNext())

        if(!lastGeo.equals("WW") && !lastGeo.equals(""))
        {
            value.append("<p>&lt;---" + lastGeo + "</p>");
        }

        return value;
    }

    private void deriveA0181()
    {
        boolean y = false;
        Vector finof = PokUtils.getAllLinkedEntities(cmpntVector, "CMPNTFINOF", "FINOF");
        if(finof.size() > 0)
        {
            pdsAndQ.put("A0181", new Boolean(true));
            y = true;
        }

        if(!y)
        {
            finof = PokUtils.getAllLinkedEntities(featureVector, "FEATUREFINOF", "FINOF");
            if(finof.size() > 0)
            {
                pdsAndQ.put("A0181", new Boolean(true));
                y = true;
            }
        }

        if(!y)
        {
            pdsAndQ.put("A0181", new Boolean(false));
        }
    }

    private void deriveIFGAnswer(String aa, String attr)
    {
        // Derive one answer to the following question for the Internal Announcement Letter:
        // A0183, A0185, A0187, A0188, A0189, A0190

        StringBuffer sbf = new StringBuffer();
        TreeMap cmpntTm = new TreeMap();
        TreeMap featureTm = new TreeMap();
        boolean includeHdr = true;
        String instStr = new String();
        String mktgname = new String();

        for(int i = 0; i < cmpntVector.size(); i++)
        {
            EntityItem cmpntEI = (EntityItem) cmpntVector.get(i);

            Vector finofVector = PokUtils.getAllLinkedEntities(cmpntEI, "CMPNTFINOF", "FINOF");
            mktgname = (String) mktgNameHT.get(cmpntEI.getKey());

            for(int j = 0; j < finofVector.size(); j++)
            {
                EntityItem finof = (EntityItem) finofVector.get(j);

                instStr = PokUtils.getAttributeValue(finof, attr, "", "", false);
                if (instStr != null && instStr.length() > 0)
                {
                    cmpntTm.put(mktgname, instStr);
                }
            }
        }

        for(int i = 0; i < featureVector.size(); i++)
        {
            EntityItem featureEI = (EntityItem) featureVector.get(i);

            Vector finofVector = PokUtils.getAllLinkedEntities(featureEI, "FEATUREFINOF", "FINOF");
            mktgname = (String) mktgNameHT.get(featureEI.getKey());

            for(int j = 0; j < finofVector.size(); j++)
            {
                EntityItem finof = (EntityItem) finofVector.get(j);

                instStr = PokUtils.getAttributeValue(finof, attr, "", "", false);
                if (instStr != null && instStr.length() > 0)
                {
                    featureTm.put(mktgname, instStr);
                }
            }
        }

        if (cmpntVector.size() + featureVector.size() > 0)
        {
            if (cmpntVector.size() + featureVector.size() == 1)
            {
                includeHdr = false;
            }
            if (cmpntTm.size() > 0)
            {
                sbf.append("<p><b>Component Offerings</b></p>" + formatIGFAnswer(includeHdr, cmpntTm, aa));
            }
            if (featureTm.size() > 0)
            {
                sbf.append("<p><b>Features</b></p>" + formatIGFAnswer(includeHdr, featureTm, aa));
            }
        }

        pdsAndQ.put(aa, sbf.toString());
        cmpntTm.clear();
        featureTm.clear();
    }

    private String formatIGFAnswer(boolean ih, TreeMap tm, String aa)
    {
        //  Set answer to the specified question for the Internal Announcement Letter.
        StringBuffer sbf = new StringBuffer();

        Set keySet = tm.keySet();
        Iterator i = keySet.iterator();
        while (i.hasNext())
        {
            String keyStr = (String) i.next();
            // Get the value (should be already in XML format) from the TreeMap
            String valueStr = (String) tm.get(keyStr);
            if (ih)
            {
                sbf.append("<br><ul><li><b>" + keyStr + "</b></li>");
            }
            if (aa.equals("A0188"))
            {
                sbf.append("<p>");
            }
            sbf.append(valueStr);
            if (aa.equals("A0188"))
            {
                sbf.append(" Months</p>");
            }
            if (ih)
            {
                sbf.append("</ul>");
            }
        }
        keySet.clear();
        return (sbf.toString());
    }

    private void deriveA0184_A0186(String aa, String attr)
    {
        boolean y = false;
        // Derive answers to the A0184 and A0186 questions for the Internal Announcement Letter
        String attrValue = new String();

        Vector finofVector = PokUtils.getAllLinkedEntities(cmpntVector, "CMPNTFINOF", "FINOF");

        for(int j = 0; j < finofVector.size(); j++)
        {
            EntityItem finof = (EntityItem) finofVector.get(j);

            attrValue = PokUtils.getAttributeValue(finof, attr, "", "", false);
            if (attrValue != null && attrValue.length() > 0)
            {
                pdsAndQ.put(aa, new Boolean(true));
                y = true;
                break;
            }
        }

        if(!y)
        {
            finofVector = PokUtils.getAllLinkedEntities(featureVector, "FEATUREFINOF", "FINOF");

            for(int j = 0; j < finofVector.size(); j++)
            {
                EntityItem finof = (EntityItem) finofVector.get(j);

                attrValue = PokUtils.getAttributeValue(finof, attr, "", "", false);
                if (attrValue != null && attrValue.length() > 0)
                {
                    pdsAndQ.put(aa, new Boolean(true));
                    y = true;
                    break;
                }
            }
        }

        if(!y)
        {
            pdsAndQ.put(aa, new Boolean(false));
        }
    }

    private void deriveA0219()
    {
        StringBuffer sbf = new StringBuffer();

        // Get General Area Group for EMEA
        GeneralAreaGroup gaGrpEmea = gal.getRfaGeoEMEAInclusion(rootEntityItem);
        // Make sure announcement including EMEA
        if(gal.isRfaGeoEMEA(rootEntityItem))
        {
            int j = gaGrpEmea.getGeneralAreaItemCount();
            if(j >= LIMIT1)
            {
                sbf.append("<p>All European, Middle Eastern and African Countries</p>");
            }
            else
            {
                if(j < LIMIT2)
                {
                    StringBuffer strCondition1SB = new StringBuffer();
                    sbf.append("<p>Only in the following  European, Middle Eastern and African Countries:<br>");
                    for (int i=0; i < gaGrpEmea.getGeneralAreaItemCount(); i++)
                    {
                        GeneralAreaItem gai = gaGrpEmea.getGeneralAreaItem(i);
                        strCondition1SB.append((strCondition1SB.length() >0 ? " ," : "") + gai.getName());
                    }
                    sbf.append(strCondition1SB.toString() + "</p>");
                }//end of if(j < 60)
                else
                {
                    if(j > LIMIT2 && j < LIMIT1)
                    {
                        //Get the exclusion list here
                        StringBuffer strCondition2SB = new StringBuffer();
                        gaGrpEmea = gal.getRfaGeoEMEAExclusion(rootEntityItem);
                        sbf.append("<p>All European, Middle Eastern and African Countries except:<br>");
                        for(int i=0; i < gaGrpEmea.getGeneralAreaItemCount(); i++)
                        {
                            GeneralAreaItem gai = gaGrpEmea.getGeneralAreaItem(i);
                            strCondition2SB.append((strCondition2SB.length() >0 ? " ," : "") + gai.getName());
                        }
                        sbf.append(strCondition2SB.toString() + "</p>");
                    }//end of if(j > 60 && j < 130)
                }//end of else
            }//end of else
            pdsAndQ.put("A0219", sbf.toString());
        }//end of if(gaList.isRfaGeoEMEA(rootEntityItem))
        else
        {
            pdsAndQ.put("A0219", new String());
        }
    }

    private void deriveA0220()
    {
        StringBuffer sbf = new StringBuffer();

        if(!isNo("P015L"))
        {
            sbf.append("<pre>" + NEWLINE);
            sbf.append("AP DISTRIBUTION:  TO ALL ASIA PACIFIC COUNTRIES FOR RELEASE." + NEWLINE + NEWLINE);
            sbf.append(" CTRY/Region                               ANNOUNCED" + NEWLINE);
            sbf.append(" -----------                               ---------" + NEWLINE);
            sbf.append(" ASEAN *                          -          ");
            if(PokUtils.isSelected(rootEntityItem, "COUNTRYLIST", SVConstants.XR_COUNTRYLIST_1444) ||
                PokUtils.isSelected(rootEntityItem, "COUNTRYLIST", SVConstants.XR_COUNTRYLIST_1458) ||
                PokUtils.isSelected(rootEntityItem, "COUNTRYLIST", SVConstants.XR_COUNTRYLIST_1574) ||
                PokUtils.isSelected(rootEntityItem, "COUNTRYLIST", SVConstants.XR_COUNTRYLIST_1627) ||
                PokUtils.isSelected(rootEntityItem, "COUNTRYLIST", SVConstants.XR_COUNTRYLIST_1527) ||
                PokUtils.isSelected(rootEntityItem, "COUNTRYLIST", SVConstants.XR_COUNTRYLIST_1528) ||
                PokUtils.isSelected(rootEntityItem, "COUNTRYLIST", SVConstants.XR_COUNTRYLIST_1557) ||
                PokUtils.isSelected(rootEntityItem, "COUNTRYLIST", SVConstants.XR_COUNTRYLIST_1597) ||
                PokUtils.isSelected(rootEntityItem, "COUNTRYLIST", SVConstants.XR_COUNTRYLIST_1619) ||
                PokUtils.isSelected(rootEntityItem, "COUNTRYLIST", SVConstants.XR_COUNTRYLIST_1638) ||
                PokUtils.isSelected(rootEntityItem, "COUNTRYLIST", SVConstants.XR_COUNTRYLIST_1658) ||
                PokUtils.isSelected(rootEntityItem, "COUNTRYLIST", SVConstants.XR_COUNTRYLIST_1577) ||
                PokUtils.isSelected(rootEntityItem, "COUNTRYLIST", SVConstants.XR_COUNTRYLIST_1544) ||
                PokUtils.isSelected(rootEntityItem, "COUNTRYLIST", SVConstants.XR_COUNTRYLIST_1462))
            {
                sbf.append("Yes" + NEWLINE);
            }
            else
            {
                sbf.append("No" + NEWLINE);
            }

            sbf.append(" AUSTRALIA                        -          ");
            if(PokUtils.isSelected(rootEntityItem, "COUNTRYLIST", SVConstants.XR_COUNTRYLIST_1439))
            {
                sbf.append("Yes" + NEWLINE);
            }
            else
            {
                sbf.append("No" + NEWLINE);
            }

            sbf.append(" HONG KONG                        -          ");
            if(PokUtils.isSelected(rootEntityItem, "COUNTRYLIST", SVConstants.XR_COUNTRYLIST_1524))
            {
                sbf.append("Yes" + NEWLINE);
            }
            else
            {
                sbf.append("No" + NEWLINE);
            }

            sbf.append(" PRC (Peoples Republic of China)  -          ");
            if(PokUtils.isSelected(rootEntityItem, "COUNTRYLIST", SVConstants.XR_COUNTRYLIST_1470))
            {
                sbf.append("Yes" + NEWLINE);
            }
            else
            {
                sbf.append("No" + NEWLINE);
            }

            sbf.append(" MACAO                            -          ");
            if(PokUtils.isSelected(rootEntityItem, "COUNTRYLIST", SVConstants.XR_COUNTRYLIST_1553))
            {
                sbf.append("Yes" + NEWLINE);
            }
            else
            {
                sbf.append("No" + NEWLINE);
            }

            sbf.append(" TAIWAN, Province of China        -          ");
            if(PokUtils.isSelected(rootEntityItem, "COUNTRYLIST", SVConstants.XR_COUNTRYLIST_1635))
            {
                sbf.append("Yes" + NEWLINE);
            }
            else
            {
                sbf.append("No" + NEWLINE);
            }

            sbf.append(" KOREA                            -          ");
            if(PokUtils.isSelected(rootEntityItem, "COUNTRYLIST", SVConstants.XR_COUNTRYLIST_1540) ||
                PokUtils.isSelected(rootEntityItem, "COUNTRYLIST", SVConstants.XR_COUNTRYLIST_1541))
            {
                sbf.append("Yes" + NEWLINE);
            }
            else
            {
                sbf.append("No" + NEWLINE);
            }

            sbf.append(" JAPAN                            -          ");
            if(PokUtils.isSelected(rootEntityItem, "COUNTRYLIST", SVConstants.XR_COUNTRYLIST_1535))
            {
                sbf.append("Yes" + NEWLINE);
            }
            else
            {
                sbf.append("No" + NEWLINE);
            }

            sbf.append(" NEW ZEALAND                      -          ");
            if(PokUtils.isSelected(rootEntityItem, "COUNTRYLIST", SVConstants.XR_COUNTRYLIST_1581))
            {
                sbf.append("Yes" + NEWLINE);
            }
            else
            {
                sbf.append("No" + NEWLINE);
            }

            sbf.append(NEWLINE);
            sbf.append(" * Bangladesh, Brunei, Myanmar, Sri Lanka, India, Nepal, Indonesia," + NEWLINE);
            sbf.append("   Malaysia, Philippines, Singapore, Thailand, Laos, Cambodia, Vietnam" + NEWLINE);
            sbf.append("</pre>");
            pdsAndQ.put("A0220", sbf.toString());
        }//end of if(!isNo("P015L"))
        else
        {
            pdsAndQ.put("A0220", new String());
        }
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
                        String key = entity.getKey();

                        if(!destVct.contains(entity))
                        {
                            destVct.addElement(entity);
                        }

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

    private void getHTA0208(Vector srcVct, String linkType, String destType)
    {
        Iterator srcItr = srcVct.iterator();
        while (srcItr.hasNext())
        {
            EntityItem entityItem = (EntityItem) srcItr.next();
            getHTA0208(entityItem, linkType, destType);
        }
    }

    private void getHTA0208(EntityItem entityItem, String linkType, String destType)
    {
        //This method is based on PokUtils.getLinkedEntities()
        if (entityItem == null)
        {
            return;
        }

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
                        String key = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", "|", " ", false) + "<:>" + entity.getKey() + "<:>" + (String) mktgNameHT.get(entity.getKey());

                        if(null == htA0208.get(key))
                        {
                            htA0208.put(key, new StringBuffer("NNNNN"));
                        }
                        if(checkRfaGeoUS(entityItem))
                        {
                            updateGeo(key, "US", htA0208);
                        }
                        if(checkRfaGeoAP(entityItem))
                        {
                            updateGeo(key, "AP", htA0208);
                        }
                        if(checkRfaGeoLA(entityItem))
                        {
                            updateGeo(key, "LA", htA0208);
                        }
                        if(checkRfaGeoCAN(entityItem))
                        {
                            updateGeo(key, "CAN", htA0208);
                        }
                        if(checkRfaGeoEMEA(entityItem))
                        {
                            updateGeo(key, "EMEA", htA0208);
                        }
                    }//end of if
                }//end of for (int i = 0; i < entityLink.getUpLinkCount(); i++)
            }//end of if (entityLink.getEntityType().equals(linkType))
        }//end for (int ui = 0; ui < entityItem.getUpLinkCount(); ui++)
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

    private void getDataWithoutMktgName(String answerNumber, String attrName, boolean bShow, String defaultString)
    {
        StringBuffer valueSB = new StringBuffer();

        if(bShow)
        {
            valueSB.append("<p>" + PokUtils.getAttributeValue(rootEntityItem, attrName, "|", "") + "</p>");
        }

        for(int i = 0; i < sofVector.size(); i++)
        {
            EntityItem entityItem = (EntityItem) sofVector.get(i);
            String geo = getGeo(entityItem.getKey(), geoHT);
            String attributeValue = PokUtils.getAttributeValue(entityItem, attrName, "|", defaultString + " Of Entity SOF.");

            if(!attributeValue.equals(""))
            {
                if(!geo.equals("WW"))
                {
                    valueSB.append("<p>" + geo + "---&gt;</p>");
                }
                valueSB.append("<p>" + attributeValue + "</p>");
                if(!geo.equals("WW"))
                {
                    valueSB.append("<p>&lt;---" + geo + "</p>");
                }
            }
        }

        for(int i = 0; i < cmpntVector.size(); i++)
        {
            EntityItem entityItem = (EntityItem) cmpntVector.get(i);
            String geo = getGeo(entityItem.getKey(), geoHT);

            String attributeValue = PokUtils.getAttributeValue(entityItem, attrName, "|", defaultString + " Of Entity CMPNT.");

            if(!attributeValue.equals(""))
            {
                if(!geo.equals("WW"))
                {
                    valueSB.append("<p>" + geo + "---&gt;</p>");
                }
                valueSB.append("<p>" + attributeValue + "</p>");
                if(!geo.equals("WW"))
                {
                    valueSB.append("<p>&lt;---" + geo + "</p>");
                }
            }
        }

        for(int i = 0; i < featureVector.size(); i++)
        {
            EntityItem entityItem = (EntityItem) featureVector.get(i);
            String geo = getGeo(entityItem.getKey(), geoHT);

            String attributeValue = PokUtils.getAttributeValue(entityItem, attrName, "|", defaultString + " Of Entity FEATURE.");

            if(!attributeValue.equals(""))
            {
                if(!geo.equals("WW"))
                {
                    valueSB.append("<p>" + geo + "---&gt;</p>");
                }
                valueSB.append("<p>" + attributeValue + "</p>");
                if(!geo.equals("WW"))
                {
                    valueSB.append("<p>&lt;---" + geo + "</p>");
                }
            }
        }

        pdsAndQ.put(answerNumber, valueSB.toString());
    }

    private void getDataWithMktgName(String answerNumber, String attrName, boolean bShow, String defaultString)
    {
        StringBuffer value = new StringBuffer();

        if(bShow)
        {
            value.append("<p>" + PokUtils.getAttributeValue(rootEntityItem, attrName, "|", "") + "</p>");
        }

        value.append(getData(sofVector, attrName, defaultString + " Of Entity SOF.").toString());
        value.append(getData(cmpntVector, attrName, defaultString + " Of Entity CMPNT.").toString());
        value.append(getData(featureVector, attrName, defaultString + " Of Entity FEATURE.").toString());

        pdsAndQ.put(answerNumber, value.toString());
    }

    private StringBuffer getData(Vector srcVct, String attrName, String defaultString)
    {
        TreeMap tm = new TreeMap();
        Hashtable ht = new Hashtable();

        StringBuffer value = new StringBuffer();

        String lastGeo = "";
        String currentGeo = "";
        Set keySet;
        Iterator itr;

        for(int i = 0; i < srcVct.size(); i++)
        {
            EntityItem entityItem = (EntityItem) srcVct.get(i);
            StringBuffer keySB = new StringBuffer((String) mktgNameHT.get(entityItem.getKey()));
            keySB.append(PokUtils.getAttributeValue(entityItem, attrName, "|", "&nbsp;"));
            keySB.append(getGeo(entityItem.getKey(), geoHT));

            tm.put(keySB.toString(), entityItem.getKey());
            ht.put(entityItem.getKey(), entityItem);
        }//end of for(int i = 0; i < sofVector.size(); i++)

        keySet = tm.keySet();
        itr = keySet.iterator();
        while(itr.hasNext())
        {
            String key = (String) itr.next();
            EntityItem entityItem = (EntityItem) ht.get((String) tm.get(key));

            currentGeo = getGeo((String)tm.get(key), geoHT);

            if(!currentGeo.equals(lastGeo))
            {
                if(!lastGeo.equals("WW") && !lastGeo.equals(""))
                {
                    value.append("<p>&lt;---" + lastGeo + "</p>");
                }

                if(!currentGeo.equals("WW"))
                {
                    value.append("<p>" + currentGeo + "---&gt;</p>");
                }

                lastGeo = currentGeo;
            }

            value.append("<p><b>" + (String) mktgNameHT.get(entityItem.getKey()) + "</b><br />");
            value.append(PokUtils.getAttributeValue(entityItem, attrName, "|", defaultString) + "</p>");
        }//end of while(itr.hasNext())

        if(!lastGeo.equals("WW") && !lastGeo.equals(""))
        {
            value.append("<p>&lt;---" + lastGeo + "</p>");
        }

        return value;
    }

//   private StringBuffer getDataA0208_method1(String geoColumnSize)
//   {
//      TreeMap tm = new TreeMap();

//      StringBuffer sBuffer = new StringBuffer();

//      String lastGeo = "";
//      String currentGeo = "";

//      TreeSet aTreeSet = new TreeSet();

//      if(htA0208.size() > 0)
//      {
//         Set keySet = htA0208.keySet();
//         Iterator itr = keySet.iterator();
//         while(itr.hasNext())
//         {
//            String key = (String) itr.next();
//            String date = getDateA0208(key);
//            String entityKey = getEntityKeyA0208(key);
//            String mktgName = getMktgNameA0208(key);
//            String geo = getGeo(key, htA0208);
//            tm.put(date + "<:>" + geo + " " + "<:>" + mktgName, geo);
            //tm.put(date + "<:>" + geo + "<:>" + mktgName + "<:>" + entityKey, geo);

//            aTreeSet.add(date + "<:>" + geo);
//         }//end of while(itr.hasNext())

//         Iterator aItr1 = aTreeSet.iterator();
//         while(aItr1.hasNext())
//         {
//            String str1 = (String) aItr1.next();
//            int i = str1.indexOf("<:>");
//            currentGeo = str1.substring(i + 3);
//            if(!currentGeo.equals("WW"))
//               sBuffer.append("<p>" + currentGeo + "---&gt;<br>" + NEWLINE);
//            else
//               sBuffer.append("<p>" + NEWLINE);
//            sBuffer.append(formatDate(getDateA0208(str1)) + "<br>" + NEWLINE);

//            keySet = tm.keySet();
//            Iterator aItr2 = keySet.iterator();
//            while (aItr2.hasNext())
//            {
//               String key = (String) aItr2.next();
//               String date = getDateA0208(key);
//               String geo = (String) tm.get(key);
//               geo = geo.trim();
//               String mktgName = getMktgNameA0208(key);
//               String str2 = date + "<:>" + geo;
//               if(str1.equals(str2))
//               {
//                  sBuffer.append(mktgName + "<br>" + NEWLINE);
//               }
//            }//end of while (aItr2.hasNext())
//            if(!currentGeo.equals("WW"))
//               sBuffer.append("&lt;---" + currentGeo + "</p>" + NEWLINE);
//            else
//               sBuffer.append("</p>" + NEWLINE);
//         }//end of while(aItr1.hasNext())

         //keySet = tm.keySet();
         //itr = keySet.iterator();
         //while(itr.hasNext())
         //{
         //   String key = (String) itr.next();
         //   String date = getDateA0208(key);
         //   currentGeo = (String) tm.get(key);
         //   String mktgName = getMktgNameA0208(key);

         //   if(!currentGeo.equals(lastGeo))
         //   {
         //      if(!lastGeo.equals("WW") && !lastGeo.equals(""))
         //      {
         //         sBuffer.append("   <tr>\n");
         //         sBuffer.append("      <td width=\"" + geoColumnSize + "\">\n");
         //         sBuffer.append("         <---" + lastGeo + "\n");
         //         sBuffer.append("      </td>\n");
         //         sBuffer.append("      <td width=\"14%\">\n");
         //         sBuffer.append("      </td>\n");
         //         sBuffer.append("      <td width=\"1%\">\n");
         //         sBuffer.append("      </td>\n");
         //         sBuffer.append("      <td width=\"66%\">\n");
         //         sBuffer.append("      </td>\n");
         //         sBuffer.append("   </tr>\n");
         //      }

         //      if(!currentGeo.equals("WW"))
         //      {
         //         sBuffer.append("   <tr>\n");
         //         sBuffer.append("      <td width=\"" + geoColumnSize + "\">\n");
         //         sBuffer.append("         " + currentGeo + "--->\n");
         //         sBuffer.append("      </td>\n");
         //         sBuffer.append("      <td width=\"14%\">\n");
         //         sBuffer.append("      </td>\n");
         //         sBuffer.append("      <td width=\"1%\">\n");
         //         sBuffer.append("      </td>\n");
         //         sBuffer.append("      <td width=\"66%\">\n");
         //         sBuffer.append("      </td>\n");
         //         sBuffer.append("   </tr>\n");
         //      }

         //      lastGeo = currentGeo;
         //   }//end of if(!currentGeo.equals(lastGeo))

         //   sBuffer.append("   <tr>\n");
         //   sBuffer.append("      <td width=\"" + geoColumnSize + "\">\n");
         //   sBuffer.append("      </td>\n");
         //   sBuffer.append("      <td width=\"14%\" valign=\"top\">\n");
         //   sBuffer.append("         " + date + "\n");
         //   sBuffer.append("      </td>\n");
         //   sBuffer.append("      <td width=\"1%\">\n");
         //   sBuffer.append("      </td>\n");
         //   sBuffer.append("      <td width=\"66%\">\n");
         //   sBuffer.append("         " + mktgName + "\n");
         //   sBuffer.append("      </td>\n");
         //   sBuffer.append("   </tr>\n");
         //}//end of while(itr.hasNext())

         //if(!lastGeo.equals("WW") && !lastGeo.equals(""))
         //{
         //   sBuffer.append("   <tr>\n");
         //   sBuffer.append("      <td width=\"" + geoColumnSize + "\">\n");
         //   sBuffer.append("         <---" + lastGeo + "\n");
         //   sBuffer.append("      </td>\n");
         //   sBuffer.append("      <td width=\"14%\">\n");
         //   sBuffer.append("      </td>\n");
         //   sBuffer.append("      <td width=\"1%\">\n");
         //   sBuffer.append("      </td>\n");
         //   sBuffer.append("      <td width=\"66%\">\n");
         //   sBuffer.append("      </td>\n");
         //   sBuffer.append("   </tr>\n");
         //}
//      }//end of htA0208.size() > 0)

//      return sBuffer;
//   }

    private StringBuffer getDataA0208_method2()
    {
        TreeMap tm = new TreeMap();

        StringBuffer sBuffer = new StringBuffer();

        String lastGeo = "";
        String currentGeo = "";

        TreeSet aTreeSet = new TreeSet();

        if(htA0208.size() > 0)
        {
            Set keySet = htA0208.keySet();
            Iterator itr = keySet.iterator();
            Iterator aItr1;
            while(itr.hasNext())
            {
                String key = (String) itr.next();
                //String date = getDateA0208(key);
                String date = parseString(key, 1);
                //String entityKey = getEntityKeyA0208(key);
                //String mktgName = getMktgNameA0208(key);
                String mktgName = parseString(key, 3);
                String geo = getGeo(key, htA0208);
                tm.put(date + "<:>" + geo + " " + "<:>" + mktgName, geo);
                //tm.put(date + "<:>" + geo + "<:>" + mktgName + "<:>" + entityKey, geo);

                aTreeSet.add(date);
            }//end of while(itr.hasNext())

            aItr1 = aTreeSet.iterator();
            while(aItr1.hasNext())
            {
                String str1 = (String) aItr1.next();
                Iterator aItr2;

                sBuffer.append("<p>" + formatDate(str1) + "<br>" + NEWLINE);

                keySet = tm.keySet();
                aItr2 = keySet.iterator();
                while (aItr2.hasNext())
                {
                    String mktgName;
                    String key = (String) aItr2.next();
                    //String date = getDateA0208(key);
                    String date = parseString(key, 1);
                    currentGeo = (String) tm.get(key);
                    currentGeo.trim();
                    //mktgName = getMktgNameA0208(key);
                    mktgName = parseString(key, 3);

                    if(str1.equals(date))
                    {
                        if(!currentGeo.equals(lastGeo) && !lastGeo.equals("WW") && !lastGeo.equals(""))
                        {
                            sBuffer.append("&lt;---" + lastGeo + "<br>" + NEWLINE);
                        }

                        if(!currentGeo.equals(lastGeo) && !currentGeo.equals("WW"))
                        {
                            sBuffer.append(currentGeo + "---&gt;<br>" + NEWLINE);
                        }

                        sBuffer.append(mktgName + "<br>" + NEWLINE);
                        lastGeo = currentGeo;
                    }
                }//end of while (aItr2.hasNext())
                if(!lastGeo.equals("WW") && !lastGeo.equals(""))
                {
                    sBuffer.append("&lt;---" + lastGeo + "</p>" + NEWLINE);
                }
                else
                {
                    sBuffer.append("</p>" + NEWLINE);
                }

                currentGeo = "";
                lastGeo = "";
            }//end of while(aItr1.hasNext())
        }//end of if(htA0208.size() > 0)

        return sBuffer;
    }

//    private String getDateA0208(String str)
//    {
//        int i = str.indexOf("<:>");
//
//        return str.substring(0, i);
//    }

//    private String getEntityKeyA0208(String str)
//    {
//        int i = str.indexOf("<:>");
//        int j = str.indexOf("<:>", i + 3);

//        return str.substring(i + 3, j);
//    }

//    private String getMktgNameA0208(String str)
//    {
//        int i = str.indexOf("<:>");
//        i = str.indexOf("<:>", i + 3);
//
//        return str.substring(i + 3);
//    }

//    private boolean requiredColumnForGeo()
//    {
//        Set keySet = htA0208.keySet();
//        Iterator itr = keySet.iterator();
//        while(itr.hasNext())
//        {
//            String key = (String) itr.next();
//            String geo = getGeo(key, htA0208);
//            if(!geo.equals("WW"))
//            {
//                return true;
//            }
//        }

//        return false;
//    }

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

    private boolean checkRfaGeoUS(EntityItem ei)
    {
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

    /**********************************************************************************
    * Show Worldwide Customer Letter
    *
    * @return   String
    */
    public String getWWCustLetter()
    {
        Date          d       = new Date();
        String        myDate  = new String();
        SimpleDateFormat  sdf  = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        StringBuffer  today   = new StringBuffer();

        //sb.append("<p class=\"confidential\">IBM Confidential</p>");

        sb.append("<!-- B0135 -->" + NEWLINE);
        sb.append("<!-- <h1>WORLDWIDE CUSTOMER LETTER --/--/-- </h1> -->" + NEWLINE);
        sb.append("<!-- Always Applies -->" + NEWLINE);

        sb.append("<h1>WORLDWIDE CUSTOMER LETTER - " + PokUtils.getAttributeValue(rootEntityItem, "ANNNUMBER", " ", "&nbsp;") + " - " + PokUtils.getAttributeValue(rootEntityItem, "ANNDATE", " ", "&nbsp;") + " (IBM GUIDE 21.04)" + "</h1>" + NEWLINE);

        /* From http://w3.ibm.com/standards/intranet/design/v8/103_cnt_area_elements/02_pg_titles.html
        If a Web page contains IBM confidential information, include an "IBM Confidential" classification
        marker below the page title. If the page already contains a subtitle, place the "IBM Confidential"
        marker on the line below the existing subtitle.
        */
        sb.append("<p class=\"confidential\">IBM Confidential</p>");

        myDate = sdf.format(d);

        today.append("Created on  ");
        today.append(myDate);

        if(today.length() > L1)
        {
            today.insert(L1, " at");
        }

        sb.append("<p>" + today.toString() + "</p>");

        sb.append("<!-- B0136 -->" + NEWLINE);
        sb.append("<!-- <h2>TITLE</h2> -->" + NEWLINE);
        sb.append("<!-- Always Applies -->" + NEWLINE);

        sb.append("<h2>TITLE</h2>" + NEWLINE);

        sb.append("<!-- B0137 -->" + NEWLINE);
        sb.append("<!-- Information copied from elsewhere:  Title A0100 -->" + NEWLINE);
        sb.append("<!-- Always Applies -->" + NEWLINE);

        sb.append(pdsAndQ.get("A0100"));

        sb.append("<!-- B0140 -->" + NEWLINE);
        sb.append("<!-- <h2>AT A GLANCE</h2> -->" + NEWLINE);
        sb.append("<!-- Always Applies -->" + NEWLINE);

        sb.append("<h2>AT A GLANCE</h2>" + NEWLINE);

        sb.append("<!-- A0200 -->" + NEWLINE);
        sb.append("<!-- ANNOUNCEMENT(ATAGLANCE) -->" + NEWLINE);
        sb.append("<!-- Always Applies -->" + NEWLINE);

        sb.append(pdsAndQ.get("A0200"));

        sb.append("<!-- B0138 -->" + NEWLINE);
        sb.append("<!-- <h2>OVERVIEW</h2> -->" + NEWLINE);
        sb.append("<!-- Always Applies -->" + NEWLINE);

        sb.append("<h2>OVERVIEW</h2>" + NEWLINE);

        sb.append("<!-- A0202 -->" + NEWLINE);
        sb.append("<!-- ANNOUNCEMENT(OVERVIEWABSTRACT) -->" + NEWLINE);
        sb.append("<!-- SOF(OVERVIEWABSTRACT) -->" + NEWLINE);
        sb.append("<!-- CMPNT(OVERVIEWABSTRACT) -->" + NEWLINE);
        sb.append("<!-- FEATURE(OVERVIEWABSTRACT) -->" + NEWLINE);
        sb.append("<!-- Always Applies -->" + NEWLINE);

        sb.append(pdsAndQ.get("A0202"));

        sb.append("<!-- B0139 -->" + NEWLINE);
        sb.append("<!-- <h2>KEY PREREQUISITES</h2> -->" + NEWLINE);
        sb.append("<!-- !(A0204 = '') -->" + NEWLINE);

        sb.append("<!-- A0204 -->" + NEWLINE);
        sb.append("<!-- SOF(PREREQCOREQ) -->" + NEWLINE);
        sb.append("<!-- CMPNT(PREREQCOREQ) -->" + NEWLINE);
        sb.append("<!-- FEATURE(PREREQCOREQ) -->" + NEWLINE);
        sb.append("<!-- Always Applies -->" + NEWLINE);

        if(!isValue("A0204", ""))
        {
            sb.append("<h2>KEY PREREQUISITES</h2>" + NEWLINE);
            sb.append(pdsAndQ.get("A0204"));
        }

        if(!(isNo("P014B") || isValue("IDKEYC", "") || isNo("P015H")))
        {
            sb.append("US---&gt;<br />" + NEWLINE);
            sb.append("To order, contact:<br />" + NEWLINE);
        }
        if(!(isNo("P014B") || isValue("IDKEYC", "") || isNo("P015H") || (!isValue("A0300" , "A")) || isYes("P016A")))
        {
            sb.append("Your IBM representative or the Americas Call Centers at " + NEWLINE);
        }
        if(!(isNo("P014B") || isValue("IDKEYC", "") || isNo("P015H") || (!isValue("A0300" , "A")) || isNo("P016A")))
        {
            sb.append("Your Sales representative or the Americas Call Centers at " + NEWLINE);
        }
        if(!(isNo("P014B") || isValue("IDKEYC", "") || isNo("P015H") || (!isValue("A0300" , "B")) || isYes("P016A")))
        {
            sb.append("Your IBM representative, an IBM Business Partner, or the Americas Call Centers at " + NEWLINE);
        }
        if(!(isNo("P014B") || isValue("IDKEYC", "") || isNo("P015H") || (!isValue("A0300" , "B")) || isNo("P016A")))
        {
            sb.append("Your  Sales  representative,  a Business Partner, or the Americas Call Centers at " + NEWLINE);
        }
        if(!(isNo("P014B") || isValue("IDKEYC", "") || isNo("P015H") || isYes("P016A")))
        {
            sb.append("800-IBM-CALL (426-2255)<br />" + NEWLINE);
        }
        if(!(isNo("P014B") || isValue("IDKEYC", "") || isNo("P015H") || isNo("P016A")))
        {
            sb.append("1-877-THINK-72 (844-6572)<br />" + NEWLINE);
        }
        if(!(isNo("P014B") || isValue("IDKEYC", "") || isNo("P015H")))
        {
            sb.append("Reference:<br />" + NEWLINE);
            sb.append("&lt;---US<br />" + NEWLINE);
        }

        //Remove in 20.04
        //if(isYes("P015H") && isYes("P014B") && !isValue("IDKEYC", ""))
        //   sb.append("<br />\n");

        sb.append("<!-- B0147 -->" + NEWLINE);
        sb.append("<!-- <h2>PLANNED AVAILABILITY DATE</h2> -->" + NEWLINE);
        sb.append("<!-- Always Applies -->" + NEWLINE);

        sb.append("<h2>PLANNED AVAILABILITY DATE</h2>" + NEWLINE);

        sb.append("<!-- A0208 -->" + NEWLINE);
        sb.append("<!-- Always Applies -->" + NEWLINE);

        sb.append(pdsAndQ.get("A0208"));

        sb.append("<!-- B0148 -->" + NEWLINE);
        sb.append("<!-- <h2>DESCRIPTION</h2> -->" + NEWLINE);
        sb.append("<!-- Always Applies -->" + NEWLINE);

        sb.append("<h2>DESCRIPTION</h2>" + NEWLINE);

        sb.append("<!-- A0210 -->" + NEWLINE);
        sb.append("<!-- ANNOUNCEMENT(DESCRIPTION) -->" + NEWLINE);
        sb.append("<!-- SOF(DESCRIPTION) -->" + NEWLINE);
        sb.append("<!-- CMPNT(DESCRIPTION) -->" + NEWLINE);
        sb.append("<!-- FEATURE(DESCRIPTION) -->" + NEWLINE);
        sb.append("<!-- Always Applies -->" + NEWLINE);

        sb.append(pdsAndQ.get("A0210"));

        sb.append("<!-- B0149 -->" + NEWLINE);
        sb.append("<!-- <h3>ACCESSIBILITY BY PEOPLE WITH DISABILITIES</h3> -->" + NEWLINE);
        sb.append("<!-- !(P030A = NO || P030B = NO || A0160 = '') -->" + NEWLINE);

        if(isYes("P030A") && isYes("P030B") && !isValue("A0160", ""))
        {
            sb.append("<h3>ACCESSIBILITY BY PEOPLE WITH DISABILITIES</h3>" + NEWLINE);
        }

        sb.append("<!-- B0150 -->" + NEWLINE);
        sb.append("<!-- Information copied from elsewhere:  AccessibilityText A0160 -->" + NEWLINE);
        sb.append("<!-- !(P030A = NO || P030B = NO) -->" + NEWLINE);

        if(isYes("P030A") && isYes("P030B"))
        {
            sb.append(pdsAndQ.get("A0160"));
        }

        sb.append("<!-- B0151 -->" + NEWLINE);
        sb.append("<!-- US--->" + NEWLINE);
        sb.append("<!-- <h3>SECTION 508 OF THE U.S. REHABILITATION ACT</h3> -->" + NEWLINE);
        sb.append("<!-- !(P030A = NO || P015H = NO || P030C = NO) -->" + NEWLINE);
        if(!(isNo("P030A") || isNo("P015H") || isNo("P030C")))
        {
            sb.append("<br />US---&gt;" + NEWLINE);
            sb.append("<h3>SECTION 508 OF THE U.S. REHABILITATION ACT</h3>" + NEWLINE);
        }

        sb.append("<!-- B0152 -->" + NEWLINE);
        sb.append("<!-- Information copied from elsewhere:  Section508StatusText A0162 A0163 -->" + NEWLINE);
        sb.append("<!-- !(P030A = NO || P015H = NO) -->" + NEWLINE);
        if(!(isNo("P030A") || isNo("P015H")))
        {
            sb.append(pdsAndQ.get("A0162"));
            sb.append("<br />");
            sb.append(pdsAndQ.get("A0163"));
            sb.append("<br />");
        }

        sb.append("<!-- B0153 -->" + NEWLINE);
        sb.append("<!-- <---US -->" + NEWLINE);
        sb.append("<!-- !(P030A = NO || P015H = NO || P030C = NO) -->" + NEWLINE);

        if(!(isNo("P030A") || isNo("P015H") || isNo("P030C")))
        {
            sb.append("&lt;---US<br>" + NEWLINE);
        }

        //Per Mike Slocum if A0212 is left blank then the heading Supplemetal Information is deleted.  As such, everything below that point is under the WWCL heading
        if(isValue("A0212", ""))
        {
            if(!(isYes("P016A") || (isNo("P017L") && isNo("P017M"))))
            {
                sb.append("<h2>IBM ELECTRONIC SERVICES</h2>" + NEWLINE);
                sb.append("<p>IBM Global Services has transformed its delivery of hardware and software");
                sb.append("support services  to put you on the road to higher systems availability. IBM Electronic Services is a Web-enabled solution that provides you with an exclusive, no-additional-charge enhancement to the service and support on the IBM eServer. You should benefit from greater system availability due to faster problem resolution and preemptive mon itoring. IBM Electronic Services is comprised of two separate but complementary elements:   IBM Electronic Services news page and IBM Electronic Service Agent.</p>" + NEWLINE);
                sb.append("<p>IBM  Electronic  Services  news page provides you with a single Internet entry point that replaces the multiple entry points  traditionally  used by  customers  to access IBM Internet services and support. The news page enables you to gain easier access to IBM resources for assistance in resolving technical problems.</p>" + NEWLINE);
                sb.append("<p>The IBM Electronic Service Agent is no-additional-charge  software  that resides on your IBM eServer system. It is designed to proactively monitor  events  and transmit system inventory information to IBM on a periodic customer-defined timetable. The IBM Electronic Service Agent tracks system inventory, hardware error logs, and performance  information.  If the  server  is  under  a  current  IBM maintenance service agreement or  within the IBM warranty period, the Service Agent automatically  reports hardware  problems to IBM. Early knowledge about potential problems enables IBM to provide  proactive  service  that  maintains  higher  system availability and performance. In addition, information collected through the  Service  Agent will be made available to IBM service support representatives when they are helping answer  your  questions  or  diagnosing problems.</p>" + NEWLINE);
                sb.append("<p>To learn how IBM Electronic Services can work for you, visit</p>" + NEWLINE);
                sb.append("<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;http://www.ibm.com/support/electronic</p>" + NEWLINE);
            }//end of if(!(isNo("P017L") && isNo("P017M")))

            sb.append("<h2>PRICES</h2>" + NEWLINE);
            sb.append(pdsAndQ.get("A0214"));

            if(!isNo("A0181"))
            {
                sb.append("<h3>IBM GLOBAL FINANCING</h3>" + NEWLINE);
                sb.append("<p>IBM  Global  Financing  offers competitive financing to credit-qualified customers to assist them in acquiring IT solutions. Our offerings  include  financing  for  IT acquisition, including hardware, software, and services, both from IBM and other manufacturers  or  vendors.  Offerings (for  all  customer  segments:    small,  medium, and large enterprise), rates, terms, and availability can vary by country.  Contact your  local IBM Global Financing organization or visit the Web at</p>" + NEWLINE);
                sb.append("<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;http://www.ibm.com/financing</p>" + NEWLINE);
                sb.append("<p>IBM  Global  Financing  offerings are provided through IBM Credit LLC in the United States and other IBM subsidiaries and divisions worldwide  to qualified  commercial  and  government customers.   Rates are based on a customer's credit rating, financing terms, offering type, equipment type and options, and may vary by country.   Other  restrictions  may  apply. Rates and offerings are subject to change, extension or withdrawal without notice.</p>" + NEWLINE);
                sb.append(pdsAndQ.get("A0183"));
                sb.append(pdsAndQ.get("A0185"));
                sb.append(pdsAndQ.get("A0187"));
                sb.append(pdsAndQ.get("A0188"));
                sb.append(pdsAndQ.get("A0189"));
                sb.append("<p>For more financing information, please visit</p>" + NEWLINE);
                sb.append("<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;www.ibm.com/financing</p>" + NEWLINE);
            }//end of if(!isNo("A0181"))

            if(!(isNo("P014B") || isValue("IDKEYC", "") || isNo("P015H")))
            {
                sb.append("<p>US---&gt;</p>" + NEWLINE);
                sb.append("<h2>ORDER NOW</h2>" + NEWLINE);
            }

            if(!(isNo("P014B") || isValue("IDKEYC", "") || isNo("P015H") || !isValue("A0300", "A")))
            {
                sb.append("<p>To order, contact the Americas Call Centers or your local ");
            }
            if(!(isNo("P014B") || isValue("IDKEYC", "") || isNo("P015H") || !isValue("A0300", "A") || isNo("P016A")))
            {
                sb.append("Sales ");
            }
            if(!(isNo("P014B") || isValue("IDKEYC", "") || isNo("P015H") || !isValue("A0300", "A") || isYes("P016A")))
            {
                sb.append("IBM ");
            }
            if(!(isNo("P014B") || isValue("IDKEYC", "") || isNo("P015H") || !isValue("A0300", "A")))
            {
                sb.append("representative.</p>");
                sb.append("<p>To indentify your local ");
            }
            if(!(isNo("P014B") || isValue("IDKEYC", "") || isNo("P015H") || !isValue("A0300", "A") || isNo("P016A")))
            {
                sb.append("Sales ");
            }
            if(!(isNo("P014B") || isValue("IDKEYC", "") || isNo("P015H") || !isValue("A0300", "A") || isYes("P016A")))
            {
                sb.append("IBM ");
            }
            if(!(isNo("P014B") || isValue("IDKEYC", "") || isNo("P015H") || !isValue("A0300", "A")))
            {
                sb.append("representative, call ");
            }
            if(!(isNo("P014B") || isValue("IDKEYC", "") || isNo("P015H") || !isValue("A0300", "A") || isNo("P016A")))
            {
                sb.append("1-866-45-THINK (458-4465).</p>");
            }
            if(!(isNo("P014B") || isValue("IDKEYC", "") || isNo("P015H") || !isValue("A0300", "A") || isYes("P016A")))
            {
                sb.append("1 800-IBM-4YOU (426-4968).</p>");
            }

            if(!(isNo("P014B") || isValue("IDKEYC", "") || isNo("P015H") || !isValue("A0300", "B")))
            {
                sb.append("<p>To order, contact the Americas Call Centers, your local ");
            }
            if(!(isNo("P014B") || isValue("IDKEYC", "") || isNo("P015H") || !isValue("A0300", "B") || isNo("P016A")))
            {
                sb.append("Sales ");
            }
            if(!(isNo("P014B") || isValue("IDKEYC", "") || isNo("P015H") || !isValue("A0300", "B") || isYes("P016A")))
            {
                sb.append("IBM ");
            }
            if(!(isNo("P014B") || isValue("IDKEYC", "") || isNo("P015H") || !isValue("A0300", "B")))
            {
                sb.append("representative, or your ");
            }
            if(!(isNo("P014B") || isValue("IDKEYC", "") || isNo("P015H") || !isValue("A0300", "B") || isYes("P016A")))
            {
                sb.append("IBM ");
            }
            if(!(isNo("P014B") || isValue("IDKEYC", "") || isNo("P015H") || !isValue("A0300", "B")))
            {
                sb.append("Business Partner.</p>");
                sb.append("<p>To indentify your local ");
            }
            if(!(isNo("P014B") || isValue("IDKEYC", "") || isNo("P015H") || !isValue("A0300", "B") || isYes("P016A")))
            {
                sb.append("IBM ");
            }
            if(!(isNo("P014B") || isValue("IDKEYC", "") || isNo("P015H") || !isValue("A0300", "B")))
            {
                sb.append("Business Partner or ");
            }
            if(!(isNo("P014B") || isValue("IDKEYC", "") || isNo("P015H") || !isValue("A0300", "B") || isYes("P016A")))
            {
                sb.append("IBM ");
            }
            if(!(isNo("P014B") || isValue("IDKEYC", "") || isNo("P015H") || !isValue("A0300", "B") || isNo("P016A")))
            {
                sb.append("Sales ");
            }
            if(!(isNo("P014B") || isValue("IDKEYC", "") || isNo("P015H") || !isValue("A0300", "B")))
            {
                sb.append("representative, call ");
            }
            if(!(isNo("P014B") || isValue("IDKEYC", "") || isNo("P015H") || !isValue("A0300", "B") || isNo("P016A")))
            {
                sb.append("1-866-45-THINK (458-4465).</p>");
            }
            if(!(isNo("P014B") || isValue("IDKEYC", "") || isNo("P015H") || !isValue("A0300", "B") || isYes("P016A")))
            {
                sb.append("1 800-IBM-4YOU (426-4968).</p>");
            }
            if(!(isNo("P014B") || isValue("IDKEYC", "") || isNo("P015H") || isYes("P016A")))
            {
                sb.append("<pre>" + NEWLINE);
                sb.append("     Phone:     800-IBM-CALL (426-2255)" + NEWLINE);
                sb.append("     Fax:       800-2IBM-FAX (242-6329)" + NEWLINE);
                sb.append("     Internet:  ibm_direct@vnet.ibm.com" + NEWLINE);
                sb.append("     Mail:      IBM Americas Call Centers" + NEWLINE);
                sb.append("                Dept: IBM CALL, 6th Floor" + NEWLINE);
                sb.append("                105 Moatfield Drive" + NEWLINE);
                sb.append("                North York, Ontario" + NEWLINE);
                sb.append("                Canada  M3B 3R1" + NEWLINE);
                sb.append(NEWLINE);
                sb.append(NEWLINE);
                sb.append("     Reference:" + NEWLINE);
                sb.append("</pre>" + NEWLINE);
                sb.append("<p>The Americas Call Centers, our national direct marketing organization, can add your name to the mailing list for catalogs of IBM products.</p>");
            }//end of if(!(isNo("P014B") || isValue("IDKEYC", "") || isNo("P015H") || isYes("P016A")))
            if(!(isNo("P014B") || isValue("IDKEYC", "") || isNo("P015H")))
            {
                sb.append("<p>NOTE:Shipments will begin after the planned availability date.</p>" + NEWLINE);
                sb.append("<p>&lt;---US</p>" + NEWLINE);
            }

            if(!isNo("P015K"))
            {
                sb.append("<p>EMEA---&gt;</p>" + NEWLINE);
                sb.append("<h2>ANNOUNCEMENT COUNTRIES</h2>" + NEWLINE);
            }

            sb.append(pdsAndQ.get("A0219"));

            if(!(isNo("P015K") || isNo("P015M")))
            {
                sb.append("<p>All European, Middle Eastern and African Countries.</p>" + NEWLINE);
            }

            if(!isNo("P015K"))
            {
                sb.append("<p>&lt;---EMEA</p>" + NEWLINE);
            }

            //19.10
            if(!isNo("P015L"))
            {
                sb.append("<p>AP---&gt;</p>" + NEWLINE);
                sb.append(pdsAndQ.get("A0220"));
                sb.append("<p>&lt;---AP</p>" + NEWLINE);
            }
        }//end of if(isValue("A0212", ""))

        return sb.toString();
    }

    /**********************************************************************************
    * Clean up
    */
    public void cleanUp()
    {
        int length;

        sb.delete(0, sb.length());
        sb = null;

        pdsAndQ.clear();
        pdsAndQ = null;

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

        availVector.clear();
        availVector = null;

        sofVector.clear();
        sofVector = null;

        cmpntVector.clear();
        cmpntVector = null;

        featureVector.clear();
        featureVector = null;

        htA0208.clear();
        htA0208 = null;

        // Remove content in the debug buffer
        length = debugBuffer.length();
        if (length > 0)
        {
            debugBuffer.delete(0,length);
        }
    }

    private void log(String str)
    {
        // Get current data and time to be included in the debug log
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String dt = sdf.format(new Date());
        debugBuffer.append("<!-- "+ dt + "  " + str + " -->" + NEWLINE);
    }

    /**********************************************************************************
    * Get debug information
    *
    * @return   String
    */
    public String getDebugBuffer()
    {
        Iterator iks;
        Set ks = pdsAndQ.keySet();
        int pdsAndQSize = ks.size();
        debugBuffer.append("<!-- 11/20/2003 - pdsAndQ size = " + pdsAndQSize + " -->" + NEWLINE);
        iks = ks.iterator();
        for (int i = 0; iks.hasNext() && i<pdsAndQSize; i++)
        {
            String key = (String) iks.next();

            debugBuffer.append("<!-- key = " + key + " and value = " + pdsAndQ.get(key) + " -->" + NEWLINE);
        }
        log("end of debug information");
        ks.clear();
        return debugBuffer.toString();
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
        //also StringTokenizer only uses single character delimeters.
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

//        return result;
//    }
}
