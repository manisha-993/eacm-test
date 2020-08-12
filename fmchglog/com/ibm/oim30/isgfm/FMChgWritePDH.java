// Licensed Materials -- Property of IBM
//
// (c) Copyright International Business Machines Corporation, 2004
// All Rights Reserved.
//
package com.ibm.oim30.isgfm;

import java.util.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.transactions.*;

/**********************************************************************************
 * This class writes one generated file to the PDH at a time
 *
 * A new entity will be defined in the PDH via Metadata so that DMINIT will automatically create
 * the ODS table. The entity type will be FM Change Log Report (FMCHGLOGRPT). The attributes will be:
 *      Report Type (FMCHGTTYPE) = {FM Chg Rpt | Spt Dev Chg Rpt | $$Last Run$$}
 *      Report Interval (FMCHGTINTERVAL) = {1, 7, 30}
 *      Date/Time of Report (FMCHGDTS) = yyyy-mm-dd-hh:mm.ss.uuuuuu
 *      InventoryGroup (INVENTORYGROUP)
 *      Brand (BRAND)
 *      Change Log Report (FMCHGREPORT) = File (a blob)
 *
 * These entities will be created under a Workgroup named CHGLOGEXTRACT via a relator named
 * "WG for FM Change Log Report" (WGFMCHGLOGRPT).
 *
 * The metadata will be posted once available and will supersede this document. It should be noted
 * that the DMINIT/DMNET adds a few columns to each ODS table:  entitytype,  entityid, date/time of
 * ODS insertion, and nlsid.
 *
 *@author     Wendy Stimpson
 *@created    Oct 11, 2004
 */
// $Log: FMChgWritePDH.java,v $
// Revision 1.6  2005/06/09 15:32:51  wendy
// Jtest changes
//
// Revision 1.5  2005/05/10 19:20:37  wendy
// InventoryGroup flag values changed.
//
// Revision 1.4  2005/05/05 14:01:51  wendy
// Setup for CR042605498
//
// Revision 1.3  2005/03/11 01:55:24  wendy
// CR0302055218 prefix NOCHGs if no chgs in inv grp
//
// Revision 1.2  2004/10/19 18:35:23  wendy
// Added more debug output
//
// Revision 1.1  2004/10/15 23:38:48  wendy
// Init for FM Chg Log application
//
class FMChgWritePDH
{
    /** cvs revision number */
    public static final String VERSION = "$Revision: 1.6 $";

    private FMChgLogGen logGen;
    private Database dbCurrent;
    private Profile profile;
    private EntityItem[] wgArray = new EntityItem[1];
    private Hashtable invGrpTbl = new Hashtable(); // key is invgrpflag, value is current count
    private int invGrpCount=0;
    static final String CREATEACTION_NAME = "CRFMCHGLOGRPT";
    static final String REPORT_TYPE = "FMCHGLOGRPT";
    private static final String REPORT_BLOB_ATTR = "FMCHGREPORT";

    /* FMCHGLOGRPT  entity
FMCHGTTYPE      T   ReportType  Report Type
FMCHGTINTERVAL  T   ReportInterval  Report Interval
FMCHGDTS        T   DTS of Rpt  Date/Time of Report  time it actually ran
FMCHGREPORT     B   ChgLogRpt   Change Log Report
INVENTORYGROUP  U   Inv Group   Inventory Group
    INVENTORYGROUP  0020    Squad2  Squad2
    INVENTORYGROUP  0040    Squad4  Squad4
    INVENTORYGROUP  0050    AS400   AS400
    INVENTORYGROUP  0060    super   super
    INVENTORYGROUP  0070    iSeries iSeries
    INVENTORYGROUP  0080    pSeries pSeries
    INVENTORYGROUP  0090    eServer eServer
    INVENTORYGROUP  0100    xSeries xSeries
    INVENTORYGROUP  0110    zSeries zSeries
    INVENTORYGROUP  0120    Automated Tape Library  Automated Tape Library
    INVENTORYGROUP  0130    Disk    Disk
    INVENTORYGROUP  0140    Virtualization Family   Virtualization Family
    INVENTORYGROUP  0150    SAN SAN
    INVENTORYGROUP  0160    NAS NAS
    INVENTORYGROUP  0170    Storage Media   Storage Media
    INVENTORYGROUP  0180    Tape    Tape
    INVENTORYGROUP  0190    Optical     Optical

BRAND           U   Brand   Brand
    BRAND   0010    iSeries iSeries
    BRAND   0020    pSeries pSeries
    BRAND   0030    Total Storage   Total Storage
    BRAND   0040    xSeries xSeries
    BRAND   0050    zSeries zSeries

META changed! this is it on 05/10/05
BRAND
    iSeries[0010]
    pSeries[0020]
    TotalStorage[0030]
    xSeries[0040]
    zSeries[0050]
INVENTORYGROUP
    Squad2[0020]
    Squad4[0040]
    AS400[0050]
    super[0060]
    iSeries[0070]
    pSeries[0080]
    eServer[0090]
    xSeries[0100]
    zSeries[0110]
    CCIN[0200]
    CSP[0210]
    High End Disk[0220]
    Disk Products[0230]
    Function Authorizations[0240]
    Midrange Disk[0250]
    Network Attached Storage[0260]
    Storage Area Network products[0270]
    High End Tape[0280]
    Mid Range Tape[0290]
    Complementary Storage Products[0300]
    Other Storage[0310]
    */

    /********************************************************************************
    * Constructor, create one and reuse it for each file
    * @param db Database object
    * @param prof Profile object for the current time
    * @param fm FMChgLogGen object driver for file generation
    * @param wg EntityItem WG to link new FMCHGLOGRPT
    */
    FMChgWritePDH(Database db, Profile prof, FMChgLogGen fm, EntityItem wg)
    {
        logGen = fm;
        dbCurrent = db;
        profile = prof;
        wgArray[0] = wg;
    }

    /********************************************************************************
    * Release memory
    */
    void dereference()
    {
        dbCurrent = null;
        profile = null;
        logGen = null;
        wgArray[0] = null;
        wgArray=null;
    }

    /********************************************************************************
    * Create report entity and set attributes
    * @param chgRpt FMChgRpt with information for this report
    */
    void createReportEntity(FMChgRpt chgRpt) throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        COM.ibm.eannounce.objects.EANBusinessRuleException,
        COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
        java.io.UnsupportedEncodingException,
        java.rmi.RemoteException
    {
        CreateActionItem cai = null;
        EntityList list = null;
        EntityGroup eGrp = null;
        String type = chgRpt.getType();
        int interval = chgRpt.getInterval();
        String invGrpflag = chgRpt.getInvGrp();
        String brandflag = chgRpt.getBrand();
        String reportData = chgRpt.getReport();
        /*The File name for each report will be: RRDDGG
        Where:
        RR is FM if FMCHGTTYPE = 'FM Chg Rpt' or else SD
        DD is {01 | 07 | 30}
        GG is a 2 character sequence # for Inventory Group, Wayne says just a count to make it unique
        Note:  GG may vary from day to day for a given Inventory Group*/
        String fileName=chgRpt.getFileNamePrefix();  // get RRDD part may have NOCHGS prepended

        String GG = (String)invGrpTbl.get(invGrpflag);  // allow diff intervals to have same GG value for invGrpflag
        if (GG==null)
        {
            GG="";
            invGrpCount++;
            if (invGrpCount<10) {
                GG=GG+"0"; }
            GG=GG+invGrpCount;
            invGrpTbl.put(invGrpflag,GG);
        }
        fileName=fileName+GG;
        fileName=fileName+".txt";

        if(FMChgProperties.getWriteToServer())
        {
            java.io.FileOutputStream fos = null;
            java.io.OutputStreamWriter writer = null;
            try{
                // write them to server as a backup for now
                /*
                FileWriter Convenience class for writing character files. The constructors of this class assume that the default character
                encoding and the default byte-buffer size are acceptable. To specify these values yourself,
                construct an OutputStreamWriter on a FileOutputStream.

                FileOutputStream(String name)
                OutputStreamWriter(OutputStream out, "UTF8")
                */
                System.out.println("Writing output FM_"+fileName);
                logGen.trace(D.EBUG_WARN,false,"FMChgWritePDH() writing file FM_"+fileName);
                //java.io.FileWriter writer = new java.io.FileWriter("FM_"+fileName);
                fos = new java.io.FileOutputStream("FM_"+fileName);
                writer = new java.io.OutputStreamWriter(fos, "UTF8");
                writer.write(reportData);
            }catch(Exception e) {
                System.err.println("Exception writing file to server "+e.getMessage());}
            finally {
                try {
                    if (writer!=null) {
                        writer.close(); }
                    if (fos!=null) {
                        fos.close();
                    }
                }catch(Exception dd) {
                    System.err.println("Exception closing files "+dd.getMessage());}
            }
        }

        if(!FMChgProperties.getWriteToPDH()) // used during debug, don't keep writing to pdh
        {
            logGen.trace(D.EBUG_WARN,false,"FMChgWritePDH() not writing to PDH");
            return;
        }

        // create the entity
        cai = new CreateActionItem(null, dbCurrent, profile, CREATEACTION_NAME);

        list = new EntityList(dbCurrent, profile, cai, wgArray);
        eGrp = list.getEntityGroup(REPORT_TYPE);
        if (eGrp.getEntityItemCount() == 1)
        {
            EntityItem relator = null;
            // write the attributes
            EntityItem reportItem = eGrp.getEntityItem(0);

            // save the Text attributes
            setText(reportItem,"FMCHGTTYPE", type); //  FMCHGTTYPE      T   ReportType  Report Type
            setText(reportItem,"FMCHGTINTERVAL", ""+interval); // FMCHGTINTERVAL    T   ReportInterval  Report Interval
            setText(reportItem,"FMCHGDTS",chgRpt.getDTS());      // FMCHGDTS        T   DTS of Rpt  Date/Time of Report

            // write the flags
            setUniqueFlag(reportItem,"INVENTORYGROUP",invGrpflag); //INVENTORYGROUP U   Inv Group   Inventory Group
            setUniqueFlag(reportItem,"BRAND",brandflag); //BRAND            U   Brand   Brand

            // write the file itself
            setReportBlobAttr(reportItem,reportData, fileName);

            // must commit new entity and the relator to the PDH
            reportItem.commit(dbCurrent, null);
            // Commit the relator too
            relator = (EntityItem) reportItem.getUpLink(0);
            relator.commit(dbCurrent, null);

            logGen.trace(D.EBUG_WARN,false,"FMChgWritePDH.createReportEntity() created Entity: "+reportItem.getKey()+
                " and Relator: "+relator.getKey()+" for type: *"+type+
                "* interval: "+interval+" invGrpflag["+invGrpflag+"] brflag: ["+brandflag+"] fileName: "+fileName);
        }
        else
        {
            logGen.trace(D.EBUG_ERR,true,"FMChgWritePDH.createReportEntity() ERROR: Can not create "+REPORT_TYPE+" entity for type: "+type+
                " interval: "+interval+" invGrpflag["+invGrpflag+"] brflag: ["+brandflag+"]");
        }
        list.dereference();
    }

    /********************************************************************************
    * Create unique flag attribute and set it
    * @param reportItem EntityItem to add attribute to
    * @param attrCode String with attribute code
    * @param flagCode String with value
    */
    private void setUniqueFlag(EntityItem reportItem,String attrCode, String flagCode) throws
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        COM.ibm.eannounce.objects.EANBusinessRuleException
    {
        EANFlagAttribute flagAttr = (EANFlagAttribute)createAttr(reportItem,attrCode);

        if (flagAttr !=null)
        {
            MetaFlag[] mfa = (MetaFlag[]) flagAttr.get();
            for (int i = 0; i < mfa.length; i++)
            {
                if (mfa[i].getFlagCode().equals(flagCode))
                {
                    mfa[i].setSelected(true);
                    flagAttr.put(mfa);
                    break;
                }
            }
        }
    }

    /********************************************************************************
    * Create text attribute and set it
    * @param reportItem EntityItem to add attribute to
    * @param attrCode String with attribute code
    * @param value String with value
    */
    private void setText(EntityItem reportItem,String attrCode, String value)   throws
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        COM.ibm.eannounce.objects.EANBusinessRuleException
    {
        EANAttribute textAttr = createAttr(reportItem,attrCode);
        if (textAttr != null)
        {
            textAttr.put(value);
        }
    }
    /********************************************************************************
    * Create report blob data attribute and set it
    * @param reportItem EntityItem to add attribute to
    * @param reportData String with file contents
    * @param fileName String with filename
    */
    private void setReportBlobAttr(EntityItem reportItem,String reportData, String fileName) throws
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        COM.ibm.eannounce.objects.EANBusinessRuleException,
        java.io.UnsupportedEncodingException
    {
        EANAttribute blobAttr = createAttr(reportItem,REPORT_BLOB_ATTR);
        if (blobAttr != null)
        {
            int NLSID = profile.getReadLanguage().getNLSID();
            // create an OPICMBlobValue to put into the new attr
            byte[] contentBytes = reportData.getBytes("UTF8");
            OPICMBlobValue blobValue = new OPICMBlobValue(NLSID, fileName, contentBytes);
            blobAttr.put(blobValue);
        }
    }

    /********************************************************************************
    * Create specified attribute
    * @param reportItem EntityItem to add attribute to
    * @param attrCode String with attribute code
    */
    private EANAttribute createAttr(EntityItem reportItem, String attrCode) throws
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    {
        EntityGroup eGrp = reportItem.getEntityGroup();
        EANMetaAttribute ma = eGrp.getMetaAttribute(attrCode);
        if (ma==null)
        {
            logGen.trace(D.EBUG_ERR,true,"FMChgWritePDH.createAttr() MetaAttribute cannot be found to Create "+REPORT_TYPE+"."+attrCode);
            return null;
        }
        switch (ma.getAttributeType().charAt(0))
        {
        case 'T':
            {
                TextAttribute ta = new TextAttribute(reportItem, null, (MetaTextAttribute) ma);
                reportItem.putAttribute(ta);
                return ta;
            }
        case 'U':
            {
                SingleFlagAttribute sfa = new SingleFlagAttribute(reportItem, null, (MetaSingleFlagAttribute) ma);
                reportItem.putAttribute(sfa);
                return sfa;
            }
        case 'B':
            {
                BlobAttribute ba = new BlobAttribute(reportItem, null, (MetaBlobAttribute) ma);
                reportItem.putAttribute(ba);
                return ba;
            }
        default:
            break;
        }

        logGen.trace(D.EBUG_ERR,false,"FMChgWritePDH.createAttr() MetaAttribute Type="+ma.getAttributeType()+" is not supported yet "+REPORT_TYPE+"."+attrCode);
        // could not get anything
        return null;
    }
}
