// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.isgfm;

import java.util.*;
import java.io.*;
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
// Revision 1.6  2017/07/26 16:03:14  stimpsow
// Migrate to Java7 and restructure to prevent OOM
//
// Revision 1.5  2009/07/25 18:44:14  wendy
// Use current attr instead of creating one if possible
//
// Revision 1.4  2006/05/03 18:05:51  wendy
// Added support if file exceeds blob size
//
// Revision 1.3  2006/05/01 21:31:07  wendy
// Added property for max size to write to the PDH while processing.
// This was done to reduce memory usage.  If a file is too large it will
// be written to the server and then moved to the PDH after lists are freed.
//
// Revision 1.2  2006/01/25 19:26:03  wendy
// AHE copyright
//
// Revision 1.1  2006/01/24 18:39:15  wendy
// Init for AHE
//
//
class FMChgWritePDH
{
    /** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.";
    /** cvs revision number */
    public static final String VERSION = "$Revision: 1.6 $";

    private static final String SVR_PREFIX="FM_";
    private static final String PART2_SUFFIX="_2";

    private FMChgLog logGen;
    private Database dbCurrent;
    private Profile profile;
    private EntityItem[] wgArray = new EntityItem[1];
    private Hashtable<String, String> invGrpTbl = new Hashtable<String, String>(); // key is invgrpflag, value is current count
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
    * @param fm FMChgLog object driver for file generation
    * @param wg EntityItem WG to link new FMCHGLOGRPT
    */
    FMChgWritePDH(Database db, Profile prof, FMChgLog fm, EntityItem wg)
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
    * @return boolean true if file was written to the pdh
    */
    boolean createReportEntity(FMChgRpt chgRpt) throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        COM.ibm.eannounce.objects.EANBusinessRuleException,
        COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
        java.io.UnsupportedEncodingException,
        java.rmi.RemoteException
    {
        String type = chgRpt.getType();
        int interval = chgRpt.getInterval();
        String invGrpflag = chgRpt.getInvGrp();
        String brandflag = chgRpt.getBrand();
        String reportData = chgRpt.getReport();
        String reportData2 = chgRpt.getReport2();
        String fileName2;

        int maxrptlen = FMChgProperties.getMaxProcessSize();
        boolean okToWriteNow = reportData.length()<=maxrptlen; // if you can write part1, then part2 is ok too
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
        fileName2=fileName+PART2_SUFFIX+".txt";
        fileName=fileName+".txt";

//        if(FMChgProperties.getWriteToServer()) always write to server, may need to copy from server later to put into PDH
        {
            FileOutputStream fos = null;
            FileOutputStream fos2 = null;
            BufferedWriter writer = null;
            BufferedWriter writer2 = null;
            try{
                /*
                FileWriter Convenience class for writing character files. The constructors of this class assume that the default character
                encoding and the default byte-buffer size are acceptable. To specify these values yourself,
                construct an OutputStreamWriter on a FileOutputStream.

                FileOutputStream(String name)
                OutputStreamWriter(OutputStream out, "UTF8")
                */
                logGen.trace(D.EBUG_WARN,false,"FMChgWritePDH() writing file "+SVR_PREFIX+fileName);
                //java.io.FileWriter writer = new java.io.FileWriter("FM_"+fileName);
                fos = new FileOutputStream(SVR_PREFIX+fileName);
                writer = new BufferedWriter(new OutputStreamWriter(fos, "UTF8"));
                writer.write(reportData);
                if (reportData2!=null) {  // create the part2 file for this invgrp and interval
					logGen.trace(D.EBUG_WARN,false,"FMChgWritePDH() writing file "+SVR_PREFIX+fileName2);
					fos2 = new FileOutputStream(SVR_PREFIX+fileName2);
					writer2 = new BufferedWriter(new OutputStreamWriter(fos2, "UTF8"));
					writer2.write(reportData2);
				}
            }catch(Exception e) {
                System.err.println("Exception writing file to server "+e.getMessage());}
            finally {
                try {
                    if (writer!=null) {
                        writer.close(); }
                    if (fos!=null) {
                        fos.close();
                    }
                    if (writer2!=null) {
                        writer2.close(); }
                    if (fos2!=null) {
                        fos2.close();
                    }
                }catch(Exception dd) {
                    System.err.println("Exception closing files "+dd.getMessage());}
            }
        }

        if(!FMChgProperties.getWriteToPDH()) // used during debug, don't keep writing to pdh
        {
            logGen.trace(D.EBUG_WARN,false,"FMChgWritePDH() not writing to PDH");
        }else{
			if (okToWriteNow){
				writeToPDH(chgRpt, fileName, reportData);
				if (reportData2!=null) {
					writeToPDH(chgRpt, fileName2, reportData2);
				}
			}else {  // too big to write now, it was saved to file and will copy it later
				chgRpt.releaseRptBlob(); // release the blob, use the copy on the server later

				logGen.trace(D.EBUG_WARN,false,"FMChgWritePDH.createReportEntity() is NOT writing to PDH now "+
					" because blob size is too large: "+reportData.length()+" for type: *"+type+
					"* interval: "+interval+" invGrpflag["+invGrpflag+"] brflag: ["+brandflag+"] fileName: "+fileName);
			}
		}

        return okToWriteNow;
    }

    /********************************************************************************
    * Create report entity and set attributes
    * @param chgRpt FMChgRpt with information for this report
    * @param fileName String generated filename
    * @param reportData String report data
    */
    private void writeToPDH(FMChgRpt chgRpt, String fileName, String reportData) throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        COM.ibm.eannounce.objects.EANBusinessRuleException,
        COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
        java.io.UnsupportedEncodingException,
        java.rmi.RemoteException
    {
        String type = chgRpt.getType();
        int interval = chgRpt.getInterval();
        String invGrpflag = chgRpt.getInvGrp();
        String brandflag = chgRpt.getBrand();

		// create the entity
		CreateActionItem cai = new CreateActionItem(null, dbCurrent, profile, CREATEACTION_NAME);
		EntityList list = new EntityList(dbCurrent, profile, cai, wgArray);
		EntityGroup eGrp = list.getEntityGroup(REPORT_TYPE);
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

			logGen.trace(D.EBUG_WARN,false,"FMChgWritePDH.writeToPDH() created Entity: "+reportItem.getKey()+
				" and Relator: "+relator.getKey()+" for type: *"+type+
				"* interval: "+interval+" invGrpflag["+invGrpflag+"] brflag: ["+brandflag+"] fileName: "+fileName);
		}
		else
		{
			logGen.trace(D.EBUG_ERR,true,"FMChgWritePDH.writeToPDH() ERROR: Can not create "+REPORT_TYPE+" entity for type: "+type+
				" interval: "+interval+" invGrpflag["+invGrpflag+"] brflag: ["+brandflag+"]");
		}
		list.dereference();

		// if properties file says, not to server.. then delete the file on the server!
		if(!FMChgProperties.getWriteToServer()) {
			//always write to server, may need to copy from server later to put into PDH
			File thefile = new File("./"+SVR_PREFIX+fileName);
			thefile.delete();
			logGen.trace(D.EBUG_WARN,false,"FMChgWritePDH.writeToPDH() deleted on server fileName: "+fileName);
		}
	}

    /********************************************************************************
    * Create report entity and set attributes using blob file from server
    * @param chgRpt FMChgRpt with information for this report
    */
    void createReportEntityFromFile(FMChgRpt chgRpt) throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        COM.ibm.eannounce.objects.EANBusinessRuleException,
        COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
        java.io.UnsupportedEncodingException,
        java.rmi.RemoteException
    {
        BufferedReader rdr = null;
		FileInputStream fis = null;
        BufferedReader rdr2 = null;
		FileInputStream fis2 = null;
        String type = chgRpt.getType();
        int interval = chgRpt.getInterval();
        String invGrpflag = chgRpt.getInvGrp();
        String brandflag = chgRpt.getBrand();
        StringBuffer reportData = new StringBuffer();
        /*The File name for each report will be: RRDDGG
        Where:
        RR is FM if FMCHGTTYPE = 'FM Chg Rpt' or else SD
        DD is {01 | 07 | 30}
        GG is a 2 character sequence # for Inventory Group, Wayne says just a count to make it unique
        Note:  GG may vary from day to day for a given Inventory Group*/
        String fileName=chgRpt.getFileNamePrefix();  // get RRDD part may have NOCHGS prepended
        String fileName2;

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
        fileName2 =fileName+PART2_SUFFIX+".txt";
        fileName=fileName+".txt";

		// get the report data from the file on the server
		try{
			String s=null;
			logGen.trace(D.EBUG_WARN,false,"FMChgWritePDH() reading file "+SVR_PREFIX+fileName);
			fis = new FileInputStream(SVR_PREFIX+fileName);
			// use buffered reader for efficiency
			rdr = new BufferedReader(new InputStreamReader(fis,"UTF8"));
			// Note: character encoding used with inputstreams can not
			// be determined by the inputstream alone.  If the encoding is
			// not specified, it assumes the input is in the default encoding
			// of the platform.
			// append lines until done
			while((s=rdr.readLine()) !=null){
				reportData.append(s+FMChgLog.NEWLINE);  // duplicate the original string
			}

			writeToPDH(chgRpt, fileName, reportData.toString());

			if (chgRpt.getReport2()!=null)  {  // it had a part 2 but will be empty here
				reportData = new StringBuffer();
				logGen.trace(D.EBUG_WARN,false,"FMChgWritePDH() reading file "+SVR_PREFIX+fileName2);
				fis2 = new FileInputStream(SVR_PREFIX+fileName2);
				// use buffered reader for efficiency
				rdr2 = new BufferedReader(new InputStreamReader(fis2,"UTF8"));
				// Note: character encoding used with inputstreams can not
				// be determined by the inputstream alone.  If the encoding is
				// not specified, it assumes the input is in the default encoding
				// of the platform.
				// append lines until done
				while((s=rdr2.readLine()) !=null){
					reportData.append(s+FMChgLog.NEWLINE);  // duplicate the original string
				}
				writeToPDH(chgRpt, fileName2, reportData.toString());

			}
		}catch(Exception e) {
			System.err.println("Exception reading file: "+fileName+" from server "+e.getMessage());
			logGen.trace(D.EBUG_ERR,true,"FMChgWritePDH.createReportEntityFromFile() ERROR: Can not read file: "+fileName+
				" for "+REPORT_TYPE+" entity for type: "+type+
				" interval: "+interval+" invGrpflag["+invGrpflag+"] brflag: ["+brandflag+"] "+e);
		}
		finally {
			try {
				if (rdr!=null) {
					rdr.close(); }
				if (fis!=null) {
					fis.close();
				}
				if (rdr2!=null) {
					rdr2.close(); }
				if (fis2!=null) {
					fis2.close();
				}
			}catch(Exception dd) {
				System.err.println("Exception closing files "+dd.getMessage());}
		}

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
        EANAttribute attr =null;
        EntityGroup eGrp = reportItem.getEntityGroup();
        EANMetaAttribute ma = eGrp.getMetaAttribute(attrCode);
        if (ma==null)
        {
            logGen.trace(D.EBUG_ERR,true,"FMChgWritePDH.createAttr() MetaAttribute cannot be found to Create "+REPORT_TYPE+"."+attrCode);
        }
        else {
        	attr = reportItem.getAttribute(attrCode); // use the one that exists if available
        	if (attr == null){
        		switch (ma.getAttributeType().charAt(0))
        		{
        		case 'T':
        		{
        			TextAttribute ta = new TextAttribute(reportItem, null, (MetaTextAttribute) ma);
        			reportItem.putAttribute(ta);
        			attr=ta;
        			break;
        		}
        		case 'U':
        		{
        			SingleFlagAttribute sfa = new SingleFlagAttribute(reportItem, null, (MetaSingleFlagAttribute) ma);
        			reportItem.putAttribute(sfa);
        			attr=sfa;
        			break;
        		}
        		case 'B':
        		{
        			BlobAttribute ba = new BlobAttribute(reportItem, null, (MetaBlobAttribute) ma);
        			reportItem.putAttribute(ba);
        			attr=ba;
        			break;
        		}
        		default:
        			logGen.trace(D.EBUG_ERR,false,"FMChgWritePDH.createAttr() MetaAttribute Type="+ma.getAttributeType()+" is not supported yet "+REPORT_TYPE+"."+attrCode);
        		// could not get anything
        		break;
        		}
        	}
        }
        return attr;
    }
}
