//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2008  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package COM.ibm.eannounce.abr.sg;

import java.sql.SQLException;
import java.util.*;

import org.apache.poi.hssf.usermodel.HSSFCell;
import com.ibm.transform.oim.eacm.diff.DiffEntity;
import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.MiddlewareException;

import com.ibm.transform.oim.eacm.util.*;
/**********************************************************************************
 * QSM ANN ABR - for ANNTYPE="New" and "End Of Life - Withdrawal from mktg"
 * 
 * QSMRPTABRSTATUS will launch the correct class
 * From "SG FS SysFeed QSM Load  20090709.doc" 
 *
 *E.	Change Reports - Announcements
 *
 *If GENAREASELECTION  Not In ('6204'| '1999' | '6197') then (no report now)
 *	Set QSMFEEDRESEND = "No" (No)
 *	Set Return Code = Success
 *	Exit
 *End if
 *
 *If ANNTYPE Not In ('19','14') then
 *	Do not send Spreadsheet; instead send HTML Report
 *	Set QSMFEEDRESEND = "No" (No)
 *	Set Return Code = Success
 *	Exit
 *End if
 *
 *Select Case QSMFEEDRESEND
 *Case "Yes" (Yes)
 *	GenerateOutputAll
 *Case "No" (No)
 *	If (Now() + 30 days >= ANNDATE >= Now()) then
 *		GenerateOutputDelta
 *	Else
 *		Do not send Spreadsheet; instead send HTML Report
 *		Set QSMFEEDRESEND = “No” (No)
 *		Set Return Code = “0030” (Passed)
 *		Exit
 *	End if
 *Case Else
 *	ErrorMessage LD(ANNOUNCEMENT) NDN(ANNOUNCEMENT) "Invalid data condition; the report was not generated."
 *End Select Case
 *
 *Set QSMFEEDRESEND = "No" (No)
 *Exit
 *
 */
//QSMANNABR.java,v
//Revision 1.9  2013/05/22 14:35:30  wendy
//Updates for RCQ00132040-WI
//
//Revision 1.8  2009/07/13 15:28:44  wendy
//RCQ00055678 PRODID is now found on the parent WWSEO instead of the grandparent MODEL. PRODID changed from type "T" to type "U"
//
//Revision 1.7  2009/06/24 13:00:47  wendy
//Make 'IBMPartno' Left justified instead of Right CQ00016165
//
//Revision 1.6  2009/05/29 02:01:07  wendy
//A LA Ann must not use WW Avails
//
//Revision 1.5  2009/03/13 14:32:45  wendy
//CQ22294-LA CTO (XCC) - Update QSM Reports to Accept WW GenArea (WI CQ22265)
//
//Revision 1.4  2009/02/19 14:46:18  wendy
//Add SGA output for LSEOBUNDLE
//
//Revision 1.3  2009/02/04 21:26:44  wendy
//CQ00016165 - Automated QSM feed from ePIMS HW to support the late change request from BIDS
//
//Revision 1.2  2008/12/10 16:39:39  wendy
//Spec change to reduce number of error rpts
//
//Revision 1.1  2008/09/30 12:50:12  wendy
//CQ00006066-WI LA CTO - EACM - Support GFS data Load of QSM (report)
//

public class QSMANNABR implements QSMABRInterface
{
    // ANNTYPE	14	End Of Life - Withdrawal from mktg
    // ANNTYPE	19	New
    private static final String ANNTYPE_EOL ="14";
    private static final String ANNTYPE_NEW ="19";
    
    private static final String PLANNEDAVAIL = "146";
	private static final String LASTORDERAVAIL = "149";
	
	//COFCAT	100	Hardware
	//COFCAT	101	Software
	//COFCAT	102	Service
	private static final String COFCAT_HW="100";
	private static final String COFCAT_SW="101";
    
	private static final Vector NEW_LSEO_COLUMN_VCT;
	private static final Vector NEW_LSEOBDL_COLUMN_VCT;
	private static final Vector EOL_LSEO_COLUMN_VCT;
	private static final Vector EOL_LSEOBDL_COLUMN_VCT;

	static {	
/*
The following path is used to identify a row for a LSEO in the ChangeAnnouncement report. If ANNTYPE = 19, then use all of the following. If ANNTYPE = 14, then use rows 1 thru 5:
1.	Announcement is the root
2.	Association ANNAVAILA
3.	Include row AVAIL where GENAREASELECTION  In ('6204'| '1999' | '6197') and 
If (ANNTYPE = 19 and AVAILTYPE = '146' (Planned Availability)) or  (ANNTYPE = 14 and AVAILTYPE='149' (Last Order))
 if there is more than one applicable AVAIL, use the first one.
4.	LSEOAVAIL-u
5.	WWSEOLSEO-u
6.	MODELWWSEO-u
7.	Include row MODEL where COFCAT in ('100', '101') 
8.	MODELGEOMOD-d
9.	Include data GEOMOD where GENAREASELECTION  In ('6204'| '1999' | '6197') since GEOMOD may not exist
10.	Association WWSEOPROJA

The following path is used to identify a row for LSEOBUNDLEs in the ChangeAnnouncement report. If ANNTYPE = 19, then use all of the following. If ANNTYPE = 14, then use rows 1 thru 6:
1.	Announcement is the root
2.	Association ANNAVAILA
3.	Include row AVAIL where GENAREASELECTION  In ('6204'| '1999' | '6197') ) and 
If (ANNTYPE = 19 and AVAILTYPE = '146' (Planned Availability)) or  (ANNTYPE = 14 and AVAILTYPE='149' (Last Order))
 if there is more than one applicable AVAIL, use the first one.
4.	LSEOBUNDLEAVAIL-u
5.	LSEOBUNDLEGEOMOD-d 
6.	Include data GEOMOD where GENAREASELECTION  In ('6204'| '1999' | '6197') since GEOMOD may not exist
7.	Association LSEOBUNDLEPROJA -d

*/
	
/*
 * New ANN
	 SS 		SS		Flat File											GA		GA	
	 Column		Report	Column							Col		Col		LSEO	LSEO	LSEOBUNDLE	LSEOBUNDLE
Col# Heading	Passed	Heading							Width	Justify	EACM Entity	EACM Attribute	EACM Entity	EACM Attribute
1						"2"								1		Left		"5"		"5"
2						Date______						10		Left				
3						Time___________					15		Left				
4	RFAnumber	Always	RFANUM							6		Right	ANNOUNCEMENT	ANNNUMBER	ANNOUNCEMENT	ANNNUMBER
5	AnnDate				ANNDATE___						10		Left	ANNOUNCEMENT	ANNDATE	ANNOUNCEMENT	ANNDATE
6	GADate				GADATE____						10		Left	AVAIL	EFFECTIVEDATE	AVAIL	EFFECTIVEDATE
7	WDDate				WDDATE____						10					
8	IBMPartno	Always	IBMPART_____					12		Left	LSEO	SEOID	LSEOBUNDLE	SEOID
9	Description			DESCRIPTION___________________	30		Left	WWSEO	PRCFILENAM	LSEOBUNDLE	PRCFILENAM
10	Div					DV								2		Left	PROJ	DIV	PROJ	DIV
11	SegA				SGA								3		Left	SGMNTACRNYM	ACRNYM	SGMNTACRNYM	ACRNYM
12	ProdID				P								1		Left	WWSEO RCQ00055678	PRODID	LSEOBUNDLE	PRODID
13	SysOrOpt			I								1		Left	WWSEO	SEOORDERCODE		If LSEOBUNDLE.BUNDLETYPE = Hardware then "Initial" else "Both"
14	PlntOfMfg			PLT								3		Left	GEOMOD	PLNTOFMFR	GEOMOD	PLNTOFMFR
15	IndDefCat			IDC								3		Left	ANNOUNCEMENT	INDDEFNCATG	ANNOUNCEMENT	INDDEFNCATG
16	FtnClass			CLAS							4		Left	MODEL	FUNCCLS	LSEOBUNDLE	FUNCCLS
17	Brand				B								1		Left	GEOMOD	EMEABRANDCD	GEOMOD	EMEABRANDCD
18	USPartNo			USPN___							7		Left	LSEO	SEOID		Null
19	SPECBID				S								1		Left	WWSEO	SPECBID	LSEOBUNDLE	SPECBID
20	SEOType		Always	SEOTYPE___						10		Left		"LSEO"		"LSEOBUNDLE"
21	EntityId	Always	ENTITYID__						10		Right	ANNOUNCEMENT	Entityid	ANNOUNCEMENT	Entityid

 */		
		NEW_LSEO_COLUMN_VCT = new Vector();

		//4	RFAnumber	Always	RFANUM							6		Right	ANNOUNCEMENT	ANNNUMBER	ANNOUNCEMENT	ANNNUMBER
		XLColumn xlcAnnNum = new XLColumn("RFAnumber","ANNOUNCEMENT","ANNNUMBER");
		xlcAnnNum.setAlwaysShow();
		xlcAnnNum.setFFColumnLabel("RFANUM");
		xlcAnnNum.setColumnWidth(6);
		xlcAnnNum.setJustified(XLColumn.RIGHT);
		NEW_LSEO_COLUMN_VCT.addElement(xlcAnnNum);
		//5	AnnDate				ANNDATE___						10		Left	ANNOUNCEMENT	ANNDATE	ANNOUNCEMENT	ANNDATE
		XLColumn xlcAnnDate = new XLColumn("AnnDate","ANNOUNCEMENT","ANNDATE");
		xlcAnnDate.setFFColumnLabel("ANNDATE___");
		xlcAnnDate.setColumnWidth(10);
		NEW_LSEO_COLUMN_VCT.addElement(xlcAnnDate);
		//6	GADate				GADATE____						10		Left	AVAIL	EFFECTIVEDATE	AVAIL	EFFECTIVEDATE
		XLColumn gaDate = new XLColumn("GADate","AVAIL","EFFECTIVEDATE");
		gaDate.setFFColumnLabel("GADATE____");
		gaDate.setColumnWidth(10);
		NEW_LSEO_COLUMN_VCT.addElement(gaDate);
		//7	WDDate				WDDATE____						10					
		XLColumn wdDate = new XLFixedColumn("WDDate","");
		wdDate.setFFColumnLabel("WDDATE____");
		wdDate.setColumnWidth(10);
		NEW_LSEO_COLUMN_VCT.addElement(wdDate);
		//8	IBMPartno	Always	IBMPART_____					12		Left	LSEO	SEOID	LSEOBUNDLE	SEOID
		XLColumn xlcLseoSeoid = new XLColumn("IBMPartno","LSEO","SEOID");
		xlcLseoSeoid.setFFColumnLabel("IBMPART_____");
		xlcLseoSeoid.setColumnWidth(12);
		xlcLseoSeoid.setAlwaysShow();
		xlcLseoSeoid.setJustified(XLColumn.LEFT);
		NEW_LSEO_COLUMN_VCT.addElement(xlcLseoSeoid);
		//9	Description			DESCRIPTION___________________	30		Left	WWSEO	PRCFILENAM	LSEOBUNDLE	PRCFILENAM
		XLColumn xlcLseoPrcfn = new XLColumn("Description","WWSEO","PRCFILENAM");
		xlcLseoPrcfn.setFFColumnLabel("DESCRIPTION___________________");
		xlcLseoPrcfn.setColumnWidth(30);
		NEW_LSEO_COLUMN_VCT.addElement(xlcLseoPrcfn);		
		//10	Div					DV								2		Left	PROJ	DIV	PROJ	DIV		
		XLColumn xlcDiv = new XLColumn("Div","PROJ","DIV");
		xlcDiv.setFFColumnLabel("DV");
		xlcDiv.setColumnWidth(2);
		NEW_LSEO_COLUMN_VCT.addElement(xlcDiv);
		//11	SegA				SGA								3		Left	SGMNTACRNYM	ACRNYM	SGMNTACRNYM	ACRNYM
		XLColumn xlcSga = new XLColumn("SegA","SGMNTACRNYM","ACRNYM");
		xlcSga.setFFColumnLabel("SGA");
		xlcSga.setColumnWidth(3);
		NEW_LSEO_COLUMN_VCT.addElement(xlcSga);
		//12	ProdID				P								1		Left	WWSEO	PRODID	LSEOBUNDLE	PRODID
		XLColumn xlcCol = new XLColumn("ProdID","WWSEO","PRODID"); //RCQ00055678
		xlcCol.setFFColumnLabel("P");
		xlcCol.setColumnWidth(1);
		NEW_LSEO_COLUMN_VCT.addElement(xlcCol);	
		//13	SysOrOpt			I								1		Left	WWSEO	SEOORDERCODE		If LSEOBUNDLE.BUNDLETYPE = Hardware then "Initial" else "Both"
		xlcCol = new XLColumn("SysOrOpt","WWSEO","SEOORDERCODE");
		xlcCol.setFFColumnLabel("I");
		xlcCol.setColumnWidth(1);
		NEW_LSEO_COLUMN_VCT.addElement(xlcCol);	
		//14	PlntOfMfg			PLT								3		Left	GEOMOD	PLNTOFMFR	GEOMOD	PLNTOFMFR
		XLColumn xlcPlt = new XLColumn("PlntOfMfg","GEOMOD","PLNTOFMFR");
		xlcPlt.setFFColumnLabel("PLT");
		xlcPlt.setColumnWidth(3);
		NEW_LSEO_COLUMN_VCT.addElement(xlcPlt);	
		//15	IndDefCat			IDC								3		Left	ANNOUNCEMENT	INDDEFNCATG	ANNOUNCEMENT	INDDEFNCATG
		XLColumn xlcIdc = new XLColumn("IndDefCat","ANNOUNCEMENT","INDDEFNCATG");
		xlcIdc.setFFColumnLabel("IDC");
		xlcIdc.setColumnWidth(3);
		NEW_LSEO_COLUMN_VCT.addElement(xlcIdc);
		//16	FtnClass			CLAS							4		Left	MODEL	FUNCCLS	LSEOBUNDLE	FUNCCLS
		xlcCol = new XLColumn("FtnClass","MODEL","FUNCCLS");
		xlcCol.setFFColumnLabel("CLAS");
		xlcCol.setColumnWidth(4);
		NEW_LSEO_COLUMN_VCT.addElement(xlcCol);	
		//17	Brand				B								1		Left	GEOMOD	EMEABRANDCD	GEOMOD	EMEABRANDCD
		XLColumn xlcBr = new XLColumn("Brand","GEOMOD","EMEABRANDCD");
		xlcBr.setFFColumnLabel("B");
		xlcBr.setColumnWidth(1);
		NEW_LSEO_COLUMN_VCT.addElement(xlcBr);	
		//18	USPartNo			USPN___							7		Left	LSEO	SEOID		Null
		xlcCol = new XLColumn("USPartNo","LSEO","SEOID");
		xlcCol.setFFColumnLabel("USPN___");
		xlcCol.setColumnWidth(7);
		NEW_LSEO_COLUMN_VCT.addElement(xlcCol);	
		//19	SPECBID				S								1		Left	WWSEO	SPECBID	LSEOBUNDLE	SPECBID
		xlcCol = new XLColumn("SPECBID","WWSEO","SPECBID");
		xlcCol.setFFColumnLabel("S");
		xlcCol.setColumnWidth(1);
		NEW_LSEO_COLUMN_VCT.addElement(xlcCol);	
		//20	SEOType		Always	SEOTYPE___						10		Left		"LSEO"		"LSEOBUNDLE"
		XLColumn xlcLseoSeotype = new XLFixedColumn("SEOType","LSEO");
		xlcLseoSeotype.setFFColumnLabel("SEOTYPE___");
		xlcLseoSeotype.setColumnWidth(10);
		xlcLseoSeotype.setAlwaysShow();
		NEW_LSEO_COLUMN_VCT.addElement(xlcLseoSeotype);	
		//21	EntityId	Always	ENTITYID__						10		Right	ANNOUNCEMENT	Entityid	ANNOUNCEMENT	Entityid
		XLColumn xlcAnnId = new XLIdColumn("EntityId","ANNOUNCEMENT");
		xlcAnnId.setFFColumnLabel("ENTITYID__");
		xlcAnnId.setAlwaysShow();
		xlcAnnId.setJustified(XLColumn.RIGHT);
		xlcAnnId.setColumnWidth(10);
		NEW_LSEO_COLUMN_VCT.addElement(xlcAnnId);					
		
		
		NEW_LSEOBDL_COLUMN_VCT = new Vector();

		//4	RFAnumber	Always	RFANUM							6		Right	ANNOUNCEMENT	ANNNUMBER	ANNOUNCEMENT	ANNNUMBER
		NEW_LSEOBDL_COLUMN_VCT.addElement(xlcAnnNum);
		//5	AnnDate				ANNDATE___						10		Left	ANNOUNCEMENT	ANNDATE	ANNOUNCEMENT	ANNDATE
		NEW_LSEOBDL_COLUMN_VCT.addElement(xlcAnnDate);
		//6	GADate				GADATE____						10		Left	AVAIL	EFFECTIVEDATE	AVAIL	EFFECTIVEDATE
		NEW_LSEOBDL_COLUMN_VCT.addElement(gaDate);
		//7	WDDate				WDDATE____						10
		NEW_LSEOBDL_COLUMN_VCT.addElement(wdDate);
		//8	IBMPartno	Always	IBMPART_____					12		Left	LSEO	SEOID	LSEOBUNDLE	SEOID
		XLColumn xlcLseobdlSeoid = new XLColumn("IBMPartno","LSEOBUNDLE","SEOID");
		xlcLseobdlSeoid.setAlwaysShow();
		xlcLseobdlSeoid.setFFColumnLabel("IBMPART_____");
		xlcLseobdlSeoid.setColumnWidth(12);
		xlcLseobdlSeoid.setJustified(XLColumn.LEFT);
		NEW_LSEOBDL_COLUMN_VCT.addElement(xlcLseobdlSeoid);
		//9	Description			DESCRIPTION___________________	30		Left	WWSEO	PRCFILENAM	LSEOBUNDLE	PRCFILENAM
		XLColumn xlcLseobdlPrcfn = new XLColumn("Description","LSEOBUNDLE","PRCFILENAM");
		xlcLseobdlPrcfn.setFFColumnLabel("DESCRIPTION___________________");
		xlcLseobdlPrcfn.setColumnWidth(30);
		NEW_LSEOBDL_COLUMN_VCT.addElement(xlcLseobdlPrcfn);
		//10	Div					DV								2		Left	PROJ	DIV	PROJ	DIV
		NEW_LSEOBDL_COLUMN_VCT.addElement(xlcDiv);
		//11	SegA				SGA								3		Left	SGMNTACRNYM	ACRNYM	SGMNTACRNYM	ACRNYM
		NEW_LSEOBDL_COLUMN_VCT.addElement(xlcSga);
		//12	ProdID				P								1		Left	MODEL	PRODID	LSEOBUNDLE	PRODID
		XLColumn xlCol = new XLColumn("ProdID","LSEOBUNDLE","PRODID");
		xlCol.setFFColumnLabel("P");
		xlCol.setColumnWidth(1);
		NEW_LSEOBDL_COLUMN_VCT.addElement(xlCol);
		//13	SysOrOpt			I								1		Left	WWSEO	SEOORDERCODE		If LSEOBUNDLE.BUNDLETYPE = Hardware then "Initial" else "Both"
		xlCol = new XLSysOrOptColumn();
		xlCol.setFFColumnLabel("I");
		xlCol.setColumnWidth(1);
		NEW_LSEOBDL_COLUMN_VCT.addElement(xlCol);
		//14	PlntOfMfg			PLT								3		Left	GEOMOD	PLNTOFMFR	GEOMOD	PLNTOFMFR
		NEW_LSEOBDL_COLUMN_VCT.addElement(xlcPlt);
		//15	IndDefCat			IDC								3		Left	ANNOUNCEMENT	INDDEFNCATG	ANNOUNCEMENT	INDDEFNCATG
		NEW_LSEOBDL_COLUMN_VCT.addElement(xlcIdc);
		//16	FtnClass			CLAS							4		Left	MODEL	FUNCCLS	LSEOBUNDLE	FUNCCLS
		xlCol = new XLColumn("FtnClass","LSEOBUNDLE","FUNCCLS");
		xlCol.setFFColumnLabel("CLAS");
		xlCol.setColumnWidth(4);
		NEW_LSEOBDL_COLUMN_VCT.addElement(xlCol);
		//17	Brand				B								1		Left	GEOMOD	EMEABRANDCD	GEOMOD	EMEABRANDCD
		NEW_LSEOBDL_COLUMN_VCT.addElement(xlcBr);
		//18	USPartNo			USPN___							7		Left	LSEO	SEOID		Null
		xlCol = new XLFixedColumn("USPartNo","");
		xlCol.setFFColumnLabel("USPN___");
		xlCol.setColumnWidth(7);
		NEW_LSEOBDL_COLUMN_VCT.addElement(xlCol);
		//19	SPECBID				S								1		Left	WWSEO	SPECBID	LSEOBUNDLE	SPECBID
		xlCol = new XLColumn("SPECBID","LSEOBUNDLE","SPECBID");
		xlCol.setFFColumnLabel("S");
		xlCol.setColumnWidth(1);
		NEW_LSEOBDL_COLUMN_VCT.addElement(xlCol);
		//20	SEOType		Always	SEOTYPE___						10		Left		"LSEO"		"LSEOBUNDLE"
		XLColumn xlcLseobdlSeotype = new XLFixedColumn("SEOType","LSEOBUNDLE");
		xlcLseobdlSeotype.setAlwaysShow();
		xlcLseobdlSeotype.setFFColumnLabel("SEOTYPE___");
		xlcLseobdlSeotype.setColumnWidth(10);
		NEW_LSEOBDL_COLUMN_VCT.addElement(xlcLseobdlSeotype);	
		//21	EntityId	Always	ENTITYID__						10		Right	ANNOUNCEMENT	Entityid	ANNOUNCEMENT	Entityid
		NEW_LSEOBDL_COLUMN_VCT.addElement(xlcAnnId);
		

/*
For EOL announcements

	 SS 				Flat File													GA		GA	
	 Column				Column							Col		Col			LSEO	LSEO	LSEOBUNDLE	LSEOBUNDLE
Col# Heading			Heading							Width	Justify	EACM Entity	EACM Attribute	EACM Entity	EACM Attribute
1						"2"								1		Left		"5"		"5"
2						IFCDate___						10		Left				
3						IFCTime________					15		Left				
4	RFAnumber	Always	RFANUM							6		Right	ANNOUNCEMENT	ANNNUMBER	ANNOUNCEMENT	ANNNUMBER
5	AnnDate				ANNDATE___						10		Left	ANNOUNCEMENT	ANNDATE	ANNOUNCEMENT	ANNDATE
6	WDDate				WDDATE____						10		Left	AVAIL	EFFECTIVEDATE	AVAIL	EFFECTIVEDATE
7	IBMPartno	Always	IBMPART_____					12		Left	LSEO	SEOID	LSEOBUNDLE	SEOID
8	Description			DESCRIPTION___________________	30		Left	WWSEO	PRCFILENAM	LSEOBUNDLE	PRCFILENAM
9	SEOType		Always	SEOTYPE___						10		Left		"LSEO"		"LSEOBUNDLE"
10	EntityId	Always	ENTITYID__						10		Right	ANNOUNCEMENT	Entityid	ANNOUNCEMENT	Entityid

 */		
		EOL_LSEO_COLUMN_VCT = new Vector();
		EOL_LSEO_COLUMN_VCT.addElement(xlcAnnNum);
		EOL_LSEO_COLUMN_VCT.addElement(xlcAnnDate);

		//6	WDDate				WDDATE____		10		Left	AVAIL	EFFECTIVEDATE	AVAIL	EFFECTIVEDATE
		wdDate = new XLColumn("WDDate","AVAIL","EFFECTIVEDATE");
		wdDate.setFFColumnLabel("WDDATE____");
		wdDate.setColumnWidth(10);
	
		EOL_LSEO_COLUMN_VCT.addElement(wdDate);
		EOL_LSEO_COLUMN_VCT.addElement(xlcLseoSeoid);
		EOL_LSEO_COLUMN_VCT.addElement(xlcLseoPrcfn);
		EOL_LSEO_COLUMN_VCT.addElement(xlcLseoSeotype);
		EOL_LSEO_COLUMN_VCT.addElement(xlcAnnId);

		EOL_LSEOBDL_COLUMN_VCT = new Vector();
		EOL_LSEOBDL_COLUMN_VCT.addElement(xlcAnnNum);
		EOL_LSEOBDL_COLUMN_VCT.addElement(xlcAnnDate);
		EOL_LSEOBDL_COLUMN_VCT.addElement(wdDate);
		EOL_LSEOBDL_COLUMN_VCT.addElement(xlcLseobdlSeoid);
		EOL_LSEOBDL_COLUMN_VCT.addElement(xlcLseobdlPrcfn);
		EOL_LSEOBDL_COLUMN_VCT.addElement(xlcLseobdlSeotype);	
		EOL_LSEOBDL_COLUMN_VCT.addElement(xlcAnnId);
	}
	
	private String annType=ANNTYPE_NEW;
	private boolean isWW = false;

	/***********************************************
	 * Should the report be generated for this root
	 * Check for specified attributes on root
	 * 
	 * If RootEntity.STATUS <> "Final" (0020) then
	 * 		ErrorMessage LD(ANNOUNCEMENT) NDN(ANNOUNCEMENT) "was queued to send data; however, it is not Final"
	 * 		Set QSMFEEDRESEND = "No" (No)
	 * 		Set Return Code = "0040" (Failed)
	 * 		Exit
	 * End if
	 * If ANNTYPE Not In ('19','14') then 
	 * 		Do not send Spreadsheet; instead send HTML Report
	 * 		Set QSMFEEDRESEND = "No" (No)
	 * 		Set Return Code = "0030" (Passed)
	 * 		Exit
	 * End if
	 * If GENAREASELECTION  Not In ('6204'| '1999' | '6197') then (no report now)
	 * 		Set QSMFEEDRESEND = "No" (No)
	 * 		Set Return Code = "0030" (Passed)
	 * 		Exit
	 * End if
	 * 
	 * @return boolean
	 * @throws MiddlewareException 
	 * @throws SQLException 
	 */
	public boolean canGenerateReport(EntityItem rootItem, QSMRPTABRSTATUS abr) {
		boolean isOk = true;
		String statusFlag = PokUtils.getAttributeFlagValue(rootItem, "ANNSTATUS");
		abr.addDebug("QSMANNABR.generateReport: "+rootItem.getKey()+" STATUS: "+
    			PokUtils.getAttributeValue(rootItem, "ANNSTATUS",", ", "", false)+" ["+statusFlag+"]");
		if (!STATUS_FINAL.equals(statusFlag)){
			// NOT_FINAL_ERR = was queued to send data; however, it is not Final
			abr.addError("NOT_FINAL_ERR",null);
			isOk = false;
		}
		
		annType = PokUtils.getAttributeFlagValue(rootItem, "ANNTYPE");
		abr.addDebug("QSMANNABR.generateReport: "+rootItem.getKey()+" ANNTYPE: "+
				PokUtils.getAttributeValue(rootItem, "ANNTYPE",", ", "", false)+" ["+annType+"]");
		if (!ANNTYPE_EOL.equals(annType) && !ANNTYPE_NEW.equals(annType)){
			isOk = false;
			abr.addOutput("Announcement is not 'New' or 'End of Life - Withdrawal from mktg'");
		}

		Set testSet = new HashSet();
		testSet.add(QSMRPTABRSTATUS.GENAREA_WW); // allow WW now CQ22265
		isWW = PokUtils.contains(rootItem, "GENAREASELECTION", testSet);
		abr.addDebug(rootItem.getKey()+" GENAREASELECTION isWW: "+isWW);
		
		for (int i=0; i<QSMRPTABRSTATUS.GEOS.length; i++){
			testSet.add(QSMRPTABRSTATUS.GEOS[i]);
		}
		
		if (!PokUtils.contains(rootItem, "GENAREASELECTION", testSet)){ 
			isOk = false;
			abr.addOutput("Announcement does not have required 'General Area Selection' value.");
			abr.addDebug("GENAREASELECTION did not include "+testSet+", was "+
					PokUtils.getAttributeValue(rootItem, "GENAREASELECTION",", ", "", false)+
					"["+PokUtils.getAttributeFlagValue(rootItem, "GENAREASELECTION")+"]");
			abr.setNoReport();
		}
		testSet.clear();
		
		return isOk;
	}
	/***********************************************
	 * Should the report be generated for this extract
	 * Check for specified structure and attributes
	 * The following path is used to identify a row for a LSEO in the ChangeAnnouncement report. 
	 * If ANNTYPE = 19, then use all of the following. If ANNTYPE = 14, then use rows 1 thru 5:
	 * 1.	Announcement is the root
	 * 2.	Association ANNAVAILA
	 * 3.	Include row AVAIL where GENAREASELECTION  In ('6204'| '1999' | '6197') and
	 * 		If (ANNTYPE = 19 and AVAILTYPE = '146' (Planned Availability)) or  (ANNTYPE = 14 and AVAILTYPE='149' (Last Order))
	 * 		if there is more than one applicable AVAIL, use the first one.
	 * 4.	LSEOAVAIL-u
	 * 5.	WWSEOLSEO-u
	 * 6.	MODELWWSEO-u
	 * 7.	Include row MODEL where COFCAT in ('100', '101')
	 * 8.	MODELGEOMOD-d
	 * 9.	Include data GEOMOD where GENAREASELECTION  In ('6204'| '1999' | '6197') since GEOMOD may not exist
	 * 10.	Association WWSEOPROJA
	 * 
	 * The following path is used to identify a row for LSEOBUNDLEs in the ChangeAnnouncement report.
	 * If ANNTYPE = 19, then use all of the following. If ANNTYPE = 14, then use rows 1 thru 6:
	 * 1.	Announcement is the root
	 * 2.	Association ANNAVAILA
	 * 3.	Include row AVAIL where GENAREASELECTION  In ('6204'| '1999' | '6197') ) and
	 * 		If (ANNTYPE = 19 and AVAILTYPE = '146' (Planned Availability)) or  (ANNTYPE = 14 and AVAILTYPE='149' (Last Order))
	 * 		if there is more than one applicable AVAIL, use the first one.
	 * 4.	LSEOBUNDLEAVAIL-u
	 * 5.	LSEOBUNDLEGEOMOD-d
	 * 6.	Include data GEOMOD where GENAREASELECTION  In ('6204'| '1999' | '6197') since GEOMOD may not exist
	 * 7.	Association LSEOBUNDLEPROJA -d
	 * 
	 * The following is an example of the filtering of Announcement and its offering data based on GENARESELECTION:
	 * 
	 * EG	ANNOUNCEMENT	LSEO AVAIL		GEOMOD
	 * 1	World Wide		US				US
	 * 2	World Wide		World Wide		Latin America
	 * 3	World Wide		Latin America	World Wide
	 * 4	Latin America	Latin America	World Wide
	 * 5	Latin America	World Wide		World Wide
	 * 
	 * EG 1: do not include
	 * EG 2: Include
	 * EG 3: Include
	 * EG 4: Include
	 * EG 5: Announcement is not valid since AVAIL is World Wide
	 * 
	 * Note: the Data Quality ABR prevents the Announcement moving to Final in EG 5 .
	 * 
	 *@return boolean
	 */
	public boolean canGenerateReport(EntityList list, QSMRPTABRSTATUS abr){
		boolean isok = false;
		String availtype=LASTORDERAVAIL;
		if(isNewAnn()){
			availtype=PLANNEDAVAIL;
		}
 
		// get the proper AVAILs for this ANN
		Vector availVct = PokUtils.getEntitiesWithMatchedAttr(list.getEntityGroup("AVAIL"), "AVAILTYPE",availtype); 
		abr.addDebug("QSMANNABR.canGenerateReport availtype:"+availtype+" has availvct.size: "+availVct.size());
		if (availVct.size()==0){
			if(isNewAnn()){
				abr.addOutput("No Planned Avails found for this Announcement");
			}else{
				abr.addOutput("No Last Order Avails found for this Announcement");
			}
		}else{
			// get AVAILs matching the specified geo, look for WW first now 
			outerloop:for (int g=0; g<QSMRPTABRSTATUS.GEOS.length; g++){
				String geo = QSMRPTABRSTATUS.GEOS[g];

				// this needs LSEO and LSEOBUNDLE thru the AVAIL where GENAREASELECTION  In ('6204'| '1999' | '6197')			
				Vector geoavailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "GENAREASELECTION",geo);
				abr.addDebug("QSMANNABR.canGenerateReport GENAREASELECTION:"+geo+" has geoavailvct.size: "+
						geoavailVct.size());
				
				if (isWW){
					// first look for WW CQ22265
					Vector wwgeoavailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "GENAREASELECTION",QSMRPTABRSTATUS.GENAREA_WW);
					abr.addDebug("QSMANNABR.canGenerateReport GENAREASELECTION:"+QSMRPTABRSTATUS.GENAREA_WW+" has wwgeoavailVct.size: "+
							wwgeoavailVct.size());
					if (wwgeoavailVct.size()>0){// add any WW avails to the geo avails if the root was WW
						for (int w=0; w<wwgeoavailVct.size(); w++){
							EntityItem avail = (EntityItem)wwgeoavailVct.elementAt(w);
							if (geoavailVct.contains(avail)){
								abr.addDebug("QSMANNABR.canGenerateReport WW "+avail.getKey()+" was also found for "+geo);
							}else{
								abr.addDebug("QSMANNABR.canGenerateReport adding WW "+avail.getKey()+" to avails found for "+geo);
								geoavailVct.addElement(avail);
							}
						}
						wwgeoavailVct.clear();
					}
				}

				if (geoavailVct.size()==0){
					abr.addOutput("No Avails found for this Announcement with 'General Area Selection':"+
							geo+(isWW?"|"+QSMRPTABRSTATUS.GENAREA_WW:""));
					continue;
				}
				// get LSEOs
				Vector lseoVct = PokUtils.getAllLinkedEntities(geoavailVct, "LSEOAVAIL", "LSEO");
				abr.addDebug("QSMANNABR.canGenerateReport lseoVct.size: "+lseoVct.size()+" for 'General Area Selection':"+geo);

				// NEW announcements;the LSEO must have a MODEL where COFCAT in ('100', '101') 
				for (int i=0; i< lseoVct.size(); i++){
					EntityItem lseoitem = (EntityItem)lseoVct.elementAt(i);
					if(isNewAnn()){
						// get its model
						Vector wwseoVct = PokUtils.getAllLinkedEntities(lseoitem, "WWSEOLSEO", "WWSEO");
						Vector mdlVct = PokUtils.getAllLinkedEntities(wwseoVct, "MODELWWSEO", "MODEL");
						for (int m=0; m<mdlVct.size(); m++){
							EntityItem mdlitem = (EntityItem)mdlVct.elementAt(m);
							String cofcat = PokUtils.getAttributeFlagValue(mdlitem, "COFCAT");
							abr.addDebug("QSMANNABR.canGenerateReport "+lseoitem.getKey()+
									" "+mdlitem.getKey()+" cofcat:"+cofcat);
							if (COFCAT_HW.equals(cofcat)||COFCAT_SW.equals(cofcat)){
								isok = true;
								break outerloop;
							}
						}
						wwseoVct.clear();
						mdlVct.clear();
					}else{
						isok = true;
						break outerloop;
					}
				}
				if (isNewAnn()){
					abr.addOutput("No LSEOs found for this Announcement for 'General Area Selection':"+geo+
							" with Hardware or Software Models");
				}else{
					abr.addOutput("No LSEOs found for this Announcement for 'General Area Selection':"+geo);
				}
				
				lseoVct.clear();
				// check lseobundles
				Vector lseobdlVct = PokUtils.getAllLinkedEntities(geoavailVct, "LSEOBUNDLEAVAIL", "LSEOBUNDLE");
				if (lseobdlVct.size()==0){
					abr.addOutput("No LSEOBUNDLEs found for this Announcement for 'General Area Selection':"+geo);	
				}else{
					isok = true;
					break outerloop;
				}
				geoavailVct.clear();
			}

			availVct.clear();
		}
		return isok;
	}
	
	/**********************************
	 * get the name of the VE to use - this must filter AVAIL for AVAILTYPE
	 * @see COM.ibm.eannounce.abr.sg.QSMABRInterface#getVeName()
	 */
	public String getVeName() { 
		if (this.isNewAnn()){ // filtered on AVAILTYPE=PLANNEDAVAIL = "146"; 
			return "QSMANNNEW";
		}else{
			return "QSMANNEOL"; // filtered on AVAILTYPE=LASTORDERAVAIL = "149";
		}
	}

	/***********************************************
	 *  Get the version
	 *
	 *@return java.lang.String
	 */
	public String getVersion(){
		return "1.9";
	}

	/*********************************************************************************
	 * @see COM.ibm.eannounce.abr.sg.QSMABRInterface#getColumnCount()
	 */
	public int getColumnCount() {
		if (ANNTYPE_NEW.equals(annType)){
			return NEW_LSEO_COLUMN_VCT.size();
		}else{
			return EOL_LSEO_COLUMN_VCT.size();
		}
	}

	/*********************************************************************************
	 * @see COM.ibm.eannounce.abr.sg.QSMABRInterface#getColumnLabel(int)
	 */
	public String getColumnLabel(int i) {
		if (ANNTYPE_NEW.equals(annType)){
			return ((XLColumn)NEW_LSEO_COLUMN_VCT.elementAt(i)).getColumnLabel();
		}else{
			return ((XLColumn)EOL_LSEO_COLUMN_VCT.elementAt(i)).getColumnLabel();
		}
	}
	/***********************************************
	 *  Get the label for the specified column index for the flatfile
	 *
	 *@return String
	 */
	public String getFFColumnLabel(int i){
		if (ANNTYPE_NEW.equals(annType)){
			return ((XLColumn)NEW_LSEO_COLUMN_VCT.elementAt(i)).getFFColumnLabel();
		}else{
			return ((XLColumn)EOL_LSEO_COLUMN_VCT.elementAt(i)).getFFColumnLabel();
		}
	}
	/***********************************************
	 *  Get the width for the specified column (used for flat files)  CQ00016165 
	 *
	 *@return int
	 */
	public int getColumnWidth(int i){
		if (ANNTYPE_NEW.equals(annType)){
			return ((XLColumn)NEW_LSEO_COLUMN_VCT.elementAt(i)).getColumnWidth();
		}else{
			return ((XLColumn)EOL_LSEO_COLUMN_VCT.elementAt(i)).getColumnWidth();
		}
	}

	/* (non-Javadoc)
	 * @see COM.ibm.eannounce.abr.sg.QSMABRInterface#setColumnValue(org.apache.poi.hssf.usermodel.HSSFCell, java.lang.String, java.util.Hashtable, int)
	 */
	public void setColumnValue(HSSFCell cell,String rowtype, Hashtable itemTbl, int i) {
		if (ANNTYPE_NEW.equals(annType)){
			if (rowtype.equals("LSEO")){
				((XLColumn)NEW_LSEO_COLUMN_VCT.elementAt(i)).setColumnValue(cell,itemTbl);
			}else{
				((XLColumn)NEW_LSEOBDL_COLUMN_VCT.elementAt(i)).setColumnValue(cell,itemTbl);
			}
		}else{
			if (rowtype.equals("LSEO")){
				((XLColumn)EOL_LSEO_COLUMN_VCT.elementAt(i)).setColumnValue(cell,itemTbl);
			}else{
				((XLColumn)EOL_LSEOBDL_COLUMN_VCT.elementAt(i)).setColumnValue(cell,itemTbl);
			}
		}
	}

	/***********************************************
	 *  get the value for the specified column index - flat file only
	 * CQ00016165 
	 */
	public String getColumnValue(String rowtype, Hashtable itemTbl, int i){
		String value = "";
		if (ANNTYPE_NEW.equals(annType)){
			if (rowtype.equals("LSEO")){
				value = ((XLColumn)NEW_LSEO_COLUMN_VCT.elementAt(i)).getColumnValue(itemTbl);
			}else{
				value = ((XLColumn)NEW_LSEOBDL_COLUMN_VCT.elementAt(i)).getColumnValue(itemTbl);
			}
		}else{
			if (rowtype.equals("LSEO")){
				value = ((XLColumn)EOL_LSEO_COLUMN_VCT.elementAt(i)).getColumnValue(itemTbl);
			}else{
				value = ((XLColumn)EOL_LSEOBDL_COLUMN_VCT.elementAt(i)).getColumnValue(itemTbl);
			}
		}
		return value;
	}
	
	/*********************************************************************************
	 * @see COM.ibm.eannounce.abr.sg.QSMABRInterface#isChanged(com.ibm.transform.oim.eacm.diff.DiffEntity, int)
	 */
	public boolean isChanged(String rowtype, Hashtable itemTbl, int i)
	{
		if (ANNTYPE_NEW.equals(annType)){
		if (rowtype.equals("LSEO")){
			return ((XLColumn)NEW_LSEO_COLUMN_VCT.elementAt(i)).isChanged(itemTbl);
		}else{
			return ((XLColumn)NEW_LSEOBDL_COLUMN_VCT.elementAt(i)).isChanged(itemTbl);
		}	
		}else{
			if (rowtype.equals("LSEO")){
				return ((XLColumn)EOL_LSEO_COLUMN_VCT.elementAt(i)).isChanged(itemTbl);
			}else{
				return ((XLColumn)EOL_LSEOBDL_COLUMN_VCT.elementAt(i)).isChanged(itemTbl);
			}	
		}
	}
	private boolean isNewAnn() { return ANNTYPE_NEW.equals(annType);}

	/***********************************************
	 *  Get the entity items to put in the ss for this geo
	 * The following path is used to identify a row for a LSEO in the ChangeAnnouncement report. 
	 * If ANNTYPE = 19, then use all of the following. If ANNTYPE = 14, then use rows 1 thru 5:
	 * 1.	Announcement is the root
	 * 2.	Association ANNAVAILA
	 * 3.	Include row AVAIL where GENAREASELECTION  In ('6204'| '1999' | '6197') and
	 * 		If (ANNTYPE = 19 and AVAILTYPE = '146' (Planned Availability)) or  (ANNTYPE = 14 and AVAILTYPE='149' (Last Order))
	 * 		if there is more than one applicable AVAIL, use the first one.
	 * 4.	LSEOAVAIL-u
	 * 5.	WWSEOLSEO-u
	 * 6.	MODELWWSEO-u
	 * 7.	Include row MODEL where COFCAT in ('100', '101')
	 * 8.	MODELGEOMOD-d
	 * 9.	Include data GEOMOD where GENAREASELECTION  In ('6204'| '1999' | '6197') since GEOMOD may not exist
	 * 10.	Association WWSEOPROJA
	 * 
	 * The following path is used to identify a row for LSEOBUNDLEs in the ChangeAnnouncement report.
	 * If ANNTYPE = 19, then use all of the following. If ANNTYPE = 14, then use rows 1 thru 6:
	 * 1.	Announcement is the root
	 * 2.	Association ANNAVAILA
	 * 3.	Include row AVAIL where GENAREASELECTION  In ('6204'| '1999' | '6197') ) and
	 * 		If (ANNTYPE = 19 and AVAILTYPE = '146' (Planned Availability)) or  (ANNTYPE = 14 and AVAILTYPE='149' (Last Order))
	 * 		if there is more than one applicable AVAIL, use the first one.
	 * 4.	LSEOBUNDLEAVAIL-u
	 * 5.	LSEOBUNDLEGEOMOD-d
	 * 6.	Include data GEOMOD where GENAREASELECTION  In ('6204'| '1999' | '6197') since GEOMOD may not exist
	 * 7.	Association LSEOBUNDLEPROJA -d
	 *@return Vector
	 */
	public Vector getRowItems(EntityList list,Hashtable diffTbl, String geo, QSMRPTABRSTATUS abr){
		Vector rowVct = new Vector();
		String availtype=LASTORDERAVAIL;
		if(isNewAnn()){
			availtype=PLANNEDAVAIL;
		}
		Vector availVct=null;
		EntityItem annItem=null;
		if (list!= null){ // get EntityItems to be output - one per row
			annItem = list.getParentEntityGroup().getEntityItem(0);
			// get the proper AVAILs for this ANN
			availVct = PokUtils.getEntitiesWithMatchedAttr(list.getEntityGroup("AVAIL"), "AVAILTYPE",availtype); 
			abr.addDebug("QSMANNABR.getRowItems from list availtype:"+availtype+" availvct.size: "+availVct.size());
		}
		if (diffTbl!=null){
			// get the proper AVAILs for this ANN
			Vector availDiffVct = (Vector)diffTbl.get("AVAIL");
			availVct = new Vector();
			// get the avail entityitems for the current dts
			for (int i=0; i<availDiffVct.size(); i++){
				DiffEntity diff = (DiffEntity)availDiffVct.elementAt(i);
				if (!diff.isDeleted()){
					availVct.addElement(diff.getCurrentEntityItem());
				}
			}
			Vector annVct = (Vector)diffTbl.get("ROOT");
			annItem = ((DiffEntity)annVct.firstElement()).getCurrentEntityItem();
			abr.addDebug("QSMANNABR.getRowItems from diff availtype:"+availtype+" availvct.size: "+availVct.size());
		}
		
		// get AVAILs matching the specified geo, look for WW first now
		// first look for WW CQ22265
		Vector wwavailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "GENAREASELECTION",QSMRPTABRSTATUS.GENAREA_WW);
		abr.addDebug("QSMANNABR.getRowItems GENAREASELECTION:"+QSMRPTABRSTATUS.GENAREA_WW+" has wwavailvct.size: "+
				wwavailVct.size());		
		availVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "GENAREASELECTION",geo);
		abr.addDebug("QSMANNABR.getRowItems GENAREASELECTION:"+geo+" availvct.size: "+availVct.size());
		if (isWW && wwavailVct.size()>0){// add any WW avails to the geo avails
			for (int wa=0; wa<wwavailVct.size(); wa++){
				EntityItem avail = (EntityItem)wwavailVct.elementAt(wa);
				if (availVct.contains(avail)){
					abr.addDebug("QSMANNABR.getRowItems WW "+avail.getKey()+" was also found for "+geo);
				}else{
					abr.addDebug("QSMANNABR.getRowItems adding WW "+avail.getKey()+" to avails found for "+geo);
					availVct.insertElementAt(avail, 0); // put WW infront
				}
			}
		}
		wwavailVct.clear();
		Hashtable completedOfTbl = new Hashtable(); // keep track of offerings already output, will have dupes
		//if there is a WW avail and a geo avail to the same offering
	
		for(int a=0; a<availVct.size(); a++){
			// this needs LSEO and LSEOBUNDLE thru the AVAIL where GENAREASELECTION  In ('6204'| '1999' | '6197')
			EntityItem availitem = (EntityItem)availVct.elementAt(a);
			String availgenarea = availitem.getKey()+":GENAREA:"+
				PokUtils.getAttributeFlagValue(availitem, "GENAREASELECTION");
			// get LSEOs
			Vector lseoVct = PokUtils.getAllLinkedEntities(availitem, "LSEOAVAIL", "LSEO");
			abr.addDebug("QSMANNABR.getRowItems "+availitem.getKey()+" lseoVct.size: "+lseoVct.size());
			// the LSEO must have a MODEL where COFCAT in ('100', '101') 
			for (int i=0; i< lseoVct.size(); i++){
				EntityItem lseoitem = (EntityItem)lseoVct.elementAt(i);
				if (completedOfTbl.containsKey(lseoitem.getKey())){
					abr.addDebug("QSMANNABR.getRowItems already completed "+lseoitem.getKey()+
							" for "+completedOfTbl.get(lseoitem.getKey())+" skipping "+availgenarea);
					continue;
				}
				completedOfTbl.put(lseoitem.getKey(),availgenarea);
				abr.addDebug("QSMANNABR.getRowItems checking "+lseoitem.getKey()+" for "+availgenarea);
				Vector wwseoVct = PokUtils.getAllLinkedEntities(lseoitem, "WWSEOLSEO", "WWSEO");
				for (int w=0; w<wwseoVct.size(); w++){
					EntityItem wwseoitem = (EntityItem)wwseoVct.elementAt(w);
					if(isNewAnn()){
						// get its model					
						Vector projVct = PokUtils.getAllLinkedEntities(wwseoitem, "WWSEOPROJA", "PROJ");							
						Vector mdlVct = PokUtils.getAllLinkedEntities(wwseoitem, "MODELWWSEO", "MODEL");
						for (int m=0; m<mdlVct.size(); m++){
							EntityItem mdlitem = (EntityItem)mdlVct.elementAt(m);
							String cofcat = PokUtils.getAttributeFlagValue(mdlitem, "COFCAT");
							abr.addDebug("QSMANNABR.getRowItems "+wwseoitem.getKey()+" "+lseoitem.getKey()+
									" "+mdlitem.getKey()+" cofcat:"+cofcat);
							if (COFCAT_HW.equals(cofcat)||COFCAT_SW.equals(cofcat)){
								XLRow xlrow = new XLRow(diffTbl==null?lseoitem:
									diffTbl.get(lseoitem.getKey()));
								rowVct.addElement(xlrow);
								xlrow.addRowItem(diffTbl==null?availitem:
									diffTbl.get(availitem.getKey()));
								xlrow.addRowItem(diffTbl==null?wwseoitem:
									diffTbl.get(wwseoitem.getKey()));
								xlrow.addRowItem(diffTbl==null?mdlitem:
									diffTbl.get(mdlitem.getKey()));
								xlrow.addRowItem(diffTbl==null?annItem:
									diffTbl.get(annItem.getKey())); // ANNOUNCEMENT
								Vector geomodVct = PokUtils.getAllLinkedEntities(mdlitem, "MODELGEOMOD", "GEOMOD");
								//CQ22265
								Vector wwgeomodVct = PokUtils.getEntitiesWithMatchedAttr(geomodVct, "GENAREASELECTION",QSMRPTABRSTATUS.GENAREA_WW);
								abr.addDebug("QSMANNABR.getRowItems GEOMOD GENAREASELECTION:"+QSMRPTABRSTATUS.GENAREA_WW+" has wwgeomodVct.size: "+
										wwgeomodVct.size());		
								if (wwgeomodVct.size()==0){
									// none found for WW look for geo geomod, first one found is used so dont need to combine
									geomodVct = PokUtils.getEntitiesWithMatchedAttr(geomodVct, "GENAREASELECTION",geo);
									abr.addDebug("QSMANNABR.getRowItems GEOMOD GENAREASELECTION:"+geo+" has geomodVct.size: "+
											geomodVct.size());
								}else{
									geomodVct = wwgeomodVct;
								}						
								
								if (geomodVct.size()>0){ // if more than one found, use first one
									if (geomodVct.size()>1){
										for (int g=0; g<geomodVct.size(); g++){
											abr.addDebug("QSMANNABR.getRowItems WARNING: Found more than one GEOMOD for "+
												mdlitem.getKey()+" "+((EntityItem)geomodVct.elementAt(g)).getKey());
										}
									}
									EntityItem ei = (EntityItem)geomodVct.firstElement();
									xlrow.addRowItem(diffTbl==null?ei:
										diffTbl.get(ei.getKey()));
									geomodVct.clear();
								}
								if (projVct.size()>0){
									if (projVct.size()>1){
										for (int g=0; g<projVct.size(); g++){
											abr.addDebug("QSMANNABR.getRowItems WARNING: Found more than one PROJ for "+
													wwseoitem.getKey()+" "+((EntityItem)projVct.elementAt(g)).getKey());
										}
									}
									EntityItem ei = (EntityItem)projVct.firstElement();
									abr.addDebug("QSMANNABR.getRowItems using "+ei.getKey());
									xlrow.addRowItem(diffTbl==null?ei:
										diffTbl.get(ei.getKey()));
									// use Association PROJSGMNTACRNYMA to obtain SGMNTACRNYM.ACRNYM 
									Vector sgVct = PokUtils.getAllLinkedEntities(ei, "PROJSGMNTACRNYMA", "SGMNTACRNYM");
									if (sgVct.size()>0){
										if (sgVct.size()>1){
											for (int g=0; g<sgVct.size(); g++){
												abr.addDebug("QSMANNABR.getRowItems WARNING: Found more than one SGMNTACRNYM for "+
														ei.getKey()+" "+((EntityItem)sgVct.elementAt(g)).getKey());
											}
										}
										EntityItem sgei = (EntityItem)sgVct.firstElement();
										abr.addDebug("QSMANNABR.getRowItems using "+sgei.getKey());
										xlrow.addRowItem(diffTbl==null?sgei:
											diffTbl.get(sgei.getKey()));
										sgVct.clear();
									}
								}
								abr.addDebug("QSMANNABR.getRowItems NEWANN Adding "+xlrow);
							} // model is correct
						} // model loop
					} else{ // is EOL announcement
						XLRow xlrow = new XLRow(diffTbl==null?lseoitem:
							diffTbl.get(lseoitem.getKey()));
						rowVct.addElement(xlrow);
						xlrow.addRowItem(diffTbl==null?availitem:
							diffTbl.get(availitem.getKey()));
						xlrow.addRowItem(diffTbl==null?wwseoitem:
							diffTbl.get(wwseoitem.getKey()));
						xlrow.addRowItem(diffTbl==null?annItem:
							diffTbl.get(annItem.getKey())); // ANNOUNCEMENT
						abr.addDebug("QSMANNABR.getRowItems EOLANN Adding "+xlrow);
					}
				} // end wwseo loop
			} // end lseo loop
			// get LSEOBUNDLEs
			Vector lseobdlVct = PokUtils.getAllLinkedEntities(availitem, "LSEOBUNDLEAVAIL", "LSEOBUNDLE");
			abr.addDebug("QSMANNABR.getRowItems "+availitem.getKey()+" lseobdlVct.size: "+lseobdlVct.size());
			for (int i=0; i< lseobdlVct.size(); i++){
				EntityItem lseobdlitem = (EntityItem)lseobdlVct.elementAt(i);
				if (completedOfTbl.containsKey(lseobdlitem.getKey())){
					abr.addDebug("QSMANNABR.getRowItems already completed "+lseobdlitem.getKey()+
							" for "+completedOfTbl.get(lseobdlitem.getKey())+" skipping "+availgenarea);
					continue;
				}
				completedOfTbl.put(lseobdlitem.getKey(),availgenarea);
				abr.addDebug("QSMANNABR.getRowItems checking "+lseobdlitem.getKey()+" for "+availgenarea);
				XLRow xlrow = new XLRow(diffTbl==null?lseobdlitem:
					diffTbl.get(lseobdlitem.getKey()));
				rowVct.addElement(xlrow);
				xlrow.addRowItem(diffTbl==null?availitem:
					diffTbl.get(availitem.getKey()));
				xlrow.addRowItem(diffTbl==null?annItem:
					diffTbl.get(annItem.getKey())); // ANNOUNCEMENT
				if (this.isNewAnn()){
					Vector projVct = PokUtils.getAllLinkedEntities(lseobdlitem, "LSEOBUNDLEPROJA", "PROJ");
					if (projVct.size()>0){
						if (projVct.size()>1){
							for (int g=0; g<projVct.size(); g++){
								abr.addDebug("QSMANNABR.getRowItems WARNING: Found more than one PROJ for "+
										lseobdlitem.getKey()+" "+((EntityItem)projVct.elementAt(g)).getKey());
							}
						}
						EntityItem ei = (EntityItem)projVct.firstElement();
						abr.addDebug("QSMANNABR.getRowItems using "+ei.getKey());
						xlrow.addRowItem(diffTbl==null?ei:
							diffTbl.get(ei.getKey()));
						// use Association PROJSGMNTACRNYMA to obtain SGMNTACRNYM.ACRNYM 
						Vector sgVct = PokUtils.getAllLinkedEntities(ei, "PROJSGMNTACRNYMA", "SGMNTACRNYM");
						if (sgVct.size()>0){
							if (sgVct.size()>1){
								for (int g=0; g<sgVct.size(); g++){
									abr.addDebug("QSMANNABR.getRowItems WARNING: Found more than one SGMNTACRNYM for "+
											ei.getKey()+" "+((EntityItem)sgVct.elementAt(g)).getKey());
								}
							}
							EntityItem sgei = (EntityItem)sgVct.firstElement();
							abr.addDebug("QSMANNABR.getRowItems using "+sgei.getKey());
							xlrow.addRowItem(diffTbl==null?sgei:
								diffTbl.get(sgei.getKey()));
							sgVct.clear();
						}
						projVct.clear();
					}	
					Vector geomodVct = PokUtils.getAllLinkedEntities(lseobdlitem, "LSEOBUNDLEGEOMOD", "GEOMOD");
					//CQ22265
					Vector wwgeomodVct = PokUtils.getEntitiesWithMatchedAttr(geomodVct, "GENAREASELECTION",QSMRPTABRSTATUS.GENAREA_WW);
					abr.addDebug("QSMANNABR.getRowItems GEOMOD GENAREASELECTION:"+QSMRPTABRSTATUS.GENAREA_WW+" has wwgeomodVct.size: "+
							wwgeomodVct.size());	
					if (wwgeomodVct.size()==0){
						// none found for WW look for geo geomod, first one found is used so dont need to combine
						geomodVct = PokUtils.getEntitiesWithMatchedAttr(geomodVct, "GENAREASELECTION",geo);
						abr.addDebug("QSMANNABR.getRowItems GEOMOD GENAREASELECTION:"+geo+" has geomodVct.size: "+
								geomodVct.size());
					}else{
						geomodVct = wwgeomodVct;
					}					

					if (geomodVct.size()>0){
						if (geomodVct.size()>1){
							for (int g=0; g<geomodVct.size(); g++){
								abr.addDebug("QSMANNABR.getRowItems WARNING: Found more than one GEOMOD for "+
										lseobdlitem.getKey()+" "+((EntityItem)geomodVct.elementAt(g)).getKey());
							}
						}
						EntityItem ei = (EntityItem)geomodVct.firstElement();
						xlrow.addRowItem(diffTbl==null?ei:
							diffTbl.get(ei.getKey()));
						geomodVct.clear();
					}
				}
				abr.addDebug("QSMANNABR.getRowItems Adding "+xlrow);
			}
		} // end availvct loop
		
		completedOfTbl.clear();
		
		return rowVct;
	}
	/***************************************************************
	 * If (Now() + 30 days >= ANNDATE >= Now())then output rpt
	 * @see COM.ibm.eannounce.abr.sg.QSMABRInterface#withinDateRange(COM.ibm.eannounce.objects.EntityItem, java.lang.String, COM.ibm.eannounce.abr.sg.QSMRPTABRSTATUS)
	 */
	public boolean withinDateRange(EntityItem rootitem, String now, QSMRPTABRSTATUS abr) {
		boolean ok = false;
		Object args[] = new Object[5];
		String anndate = PokUtils.getAttributeValue(rootitem, "ANNDATE", "", "", false);
		PDGUtility pdgutil = new PDGUtility();
		String nowPlus30 = pdgutil.getDate(now, 30);
		args[0] = PokUtils.getAttributeDescription(rootitem.getEntityGroup(), "ANNDATE", "ANNDATE");
		args[1] = anndate;
		args[2] = "";
		args[3] = now;
		args[4] = nowPlus30;
		
		abr.addDebug("QSMANNABR.withinDateRange now "+now+" anndate "+anndate+" nowplus30 "+nowPlus30);
		if ((nowPlus30.compareTo(anndate)>=0) && (anndate.compareTo(now)>=0)){
			ok = true;
		}else{
			args[2] = "not";
		}
		// DATE_RANGE_MSG = &quot;{0}&quot; value of &quot;{1}&quot; was {2} within range of {3} and {4}
		abr.addOutput("DATE_RANGE_MSG",args);
		
		return ok;
	}
	
	/***********************************************
	 *  get row 1 - flat file only CQ00016165
	 *  1 2009-01-13 11:33:42.123456  43635 NEW
	 *  'New' for a new announcement
	 *  'End' for a withdrawal announcement
	 */
	public String getRowOne(EntityItem rootitem) { 
		String rfanum = PokUtils.getAttributeValue(rootitem, "ANNNUMBER", "", "", false);
		if (ANNTYPE_NEW.equals(annType)){ 
			return XLColumn.formatToWidth(rfanum,6,XLColumn.RIGHT)+" New";
		}else{
			return XLColumn.formatToWidth(rfanum,6,XLColumn.RIGHT)+" End";
		}
	}	
	/***********************************************
	 *  get row 1 - flat file only CQ00016165
	 *  text		start	end width justification
	 * Date______		3	12	10	Left
	 * Time___________	14	28	15	Left
	 * 
	 * or
	 * IFCDate___		3	12	10	Left
	 * IFCTime________	14	28	15	Left
	 * 
	 */
	public String getRowTwoPrefix(){
		// first two columns are not part of the ss output and not in the 'template'
		if (ANNTYPE_NEW.equals(annType)){ 
			return XLColumn.formatToWidth("Date______", 10)+" "+
				XLColumn.formatToWidth("Time___________", 15);
		}else{
			return XLColumn.formatToWidth("IFCDate___", 10)+" "+
			XLColumn.formatToWidth("IFCTime________", 15);
			
		}
	}
	
}
