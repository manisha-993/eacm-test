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
* QSM LSEOBUNDLE ABR
*
* QSMRPTABRSTATUS will launch the correct class
* From "SG FS SysFeed QSM Load  20090220.doc" 
*
*D.	Change Report: LSEOBUNDLE
*
*If GENAREASELECTION  Not In ('6204'| '1999' | '6197') then (no report now)
*	Set QSMFEEDRESEND = "No" (No)
*	Set Return Code = "0030" (Passed)
*	Exit
*End if
*
*If LSEOBUNDLE.SPECBID = 11457 (N) then
*	Do not send Spreadsheet; instead send HTML Report
*	Set QSMFEEDRESEND = "No" (No)
*	Set Return Code = "0030" (Passed)
*	Exit
*End if
*
*If RootEntity.STATUS <> "Final" (0020) then
*	ErrorMessage LD(LSEO) NDN(LSEO) "was queued to send data; however, it is not Final"
*	Set QSMFEEDRESEND = "No" (No)
*	Set Return Code = "0040" (Failed)
*	Exit
*End if
*
*Select Case QSMFEEDRESEND
*Case "New" (New)
*	GenerateRFAnumber
*	GenerateOutputAll
*Case "Yes" (Yes)
*	GenerateOutputAll
*Case "No" (No)
*	If (Now() + 30 days >= LSEOPUBDATEMTRGT => Now()) OR (Now() + 30 days >= LSEOUNPUBDATEMTRGT => Now()) then
*		GenerateOutputDelta
*	Else
*		Do not send Spreadsheet; instead send HTML Report
*		Set QSMFEEDRESEND = "No" (No)
*		Set Return Code = "0030" (Passed)
*		Exit
*	End if
*Case Else
*	ErrorMessage LD(LSEOBUNDLE) NDN(LSEOBUNDLE) "Invalid data condition; the report was not generated."
*	Set QSMFEEDRESEND = "No" (No)
*	Set Return Code = "0040" (Failed)
*	Exit
*	End Select Case
*
*Set QSMFEEDRESEND = "No" (No)
*Exit
*
*/
//QSMLSEOBDLABR.java,v
//Revision 1.8  2013/05/22 14:35:29  wendy
//Updates for RCQ00132040-WI
//
//Revision 1.7  2009/06/24 13:00:47  wendy
//Make 'IBMPartno' Left justified instead of Right CQ00016165
//
//Revision 1.6  2009/03/13 14:32:45  wendy
//CQ22294-LA CTO (XCC) - Update QSM Reports to Accept WW GenArea (WI CQ22265)
//
//Revision 1.5  2009/02/04 21:26:43  wendy
//CQ00016165 - Automated QSM feed from ePIMS HW to support the late change request from BIDS
//
//Revision 1.4  2008/12/10 16:39:39  wendy
//Spec change to reduce number of error rpts
//
//Revision 1.3  2008/11/19 21:18:21  wendy
//Add check for null specbid
//
//Revision 1.2  2008/10/08 12:59:23  wendy
//Added more debug
//
//Revision 1.1  2008/09/30 12:50:12  wendy
//CQ00006066-WI LA CTO - EACM - Support GFS data Load of QSM (report)
//

public class QSMLSEOBDLABR implements QSMABRInterface
{
	private static final Vector COLUMN_VCT;

	static {
/*
5.	LSEOBUNDLEGEOMOD-d
6.	Include data GEOMOD where GENAREASELECTION  In ('6204'| '1999' | '6197') since GEOMOD may not exist
7.	Association LSEOBUNDLEPROJA -d
*/
/*
Column Names		Passed	EACM Entity	EACM Attribute
-----------------	-------	-----------	--------------
EntityId			Always	LSEOBUNDLE	Entityid
RFAnumber			Always	LSEOBUNDLE	QSMRFANUMBER
GADate						LSEOBUNDLE	BUNDLPUBDATEMTRGT
Withdrawal					LSEOBUNDLE	BUNDLUNPUBDATEMTRGT
IBMPartno			Always	LSEOBUNDLE	SEOID
Description					LSEOBUNDLE	PRCFILENAM
SEOType				Always				LSEOBUNDLE
Div							PROJ		DIV
ProdID						LSEOBUNDLE	PRODID
SysOrOpt					LSEOBUNDLE	BUNDLETYPE If LSEOBUNDLE.BUNDLETYPE = Hardware then "Initial" else "Both"
PlntOfMfg					GEOMOD		PLNTOFMFR
IndDefCat					LSEOBUNDLE	INDDEFNCATG
FtnClass					LSEOBUNDLE	FUNCCLS
Brand						GEOMOD		EMEABRANDCD
USPartNo								""

		COLUMN_VCT = new Vector();
		XLColumn xlcId = new XLIdColumn("EntityId","LSEOBUNDLE");
		xlcId.setAlwaysShow();
		XLColumn xlcAnnNum = new XLColumn("RFAnumber","LSEOBUNDLE","QSMRFANUMBER");
		xlcAnnNum.setAlwaysShow();
		XLColumn xlcLseoSeoid = new XLColumn("IBMPartno","LSEOBUNDLE","SEOID");
		xlcLseoSeoid.setAlwaysShow();
		XLColumn xlcLseoSeotype = new XLFixedColumn("SEOType","LSEOBUNDLE");
		xlcLseoSeotype.setAlwaysShow();
		COLUMN_VCT.addElement(xlcId);
		COLUMN_VCT.addElement(xlcAnnNum);
		COLUMN_VCT.addElement(new XLColumn("GADate","LSEOBUNDLE","BUNDLPUBDATEMTRGT"));
		COLUMN_VCT.addElement(new XLColumn("Withdrawal","LSEOBUNDLE","BUNDLUNPUBDATEMTRGT"));
		COLUMN_VCT.addElement(xlcLseoSeoid);
		COLUMN_VCT.addElement(new XLColumn("Description","LSEOBUNDLE","PRCFILENAM"));
		COLUMN_VCT.addElement(xlcLseoSeotype);
		COLUMN_VCT.addElement(new XLColumn("Div","PROJ","DIV"));
		COLUMN_VCT.addElement(new XLColumn("ProdID","LSEOBUNDLE","PRODID"));
		COLUMN_VCT.addElement(new XLSysOrOptColumn());
		COLUMN_VCT.addElement(new XLColumn("PlntOfMfg","GEOMOD","PLNTOFMFR"));
		COLUMN_VCT.addElement(new XLColumn("IndDefCat","LSEOBUNDLE","INDDEFNCATG"));
		COLUMN_VCT.addElement(new XLColumn("FtnClass","LSEOBUNDLE","FUNCCLS"));
		COLUMN_VCT.addElement(new XLColumn("Brand","GEOMOD","EMEABRANDCD"));
		COLUMN_VCT.addElement(new XLFixedColumn("USPartNo",""));
*/

/*
	 SS 		SS		Flat File									Special Bids	
	 Column		Report	Column						Col		Col		LSEOBUNDLE	LSEOBUNDLE
Col# Heading	Passed	Heading						Width	Justify	EACM Entity	EACM Attribute
1						"2"							1		Left		"5"
2						Date______					10		Left		
3						Time___________				15		Left		
4	RFAnumber	Always	RFANUM						6		Right	LSEOBUNDLE	QSMRFANUMBER
5	AnnDate				ANNDATE___					10		Left	LSEOBUNDLE	BUNDLPUBDATEMTRGT
6	GADate				GADATE____					10		Left	LSEOBUNDLE	BUNDLPUBDATEMTRGT
7	WDDate				WDDATE____					10				LSEOBUNDLE	BUNDLUNPUBDATEMTRGT
8	IBMPartno	Always	IBMPART_____				12		Left	LSEOBUNDLE	SEOID
9	Description			DESCRIPTION___________________	30	Left	LSEOBUNDLE	PRCFILENAM
10	Div					DV							2		Left	PROJ	DIV
11	SegA				SGA							3		Left	SGMNTACRNYM	ACRNYM
12	ProdID				P							1		Left	LSEOBUNDLE	PRODID
13	SysOrOpt			I							1		Left		If LSEOBUNDLE.BUNDLETYPE = Hardware then "Initial" else "Both"
14	PlntOfMfg			PLT							3		Left	GEOMOD	PLNTOFMFR
15	IndDefCat			IDC							3		Left	LSEOBUNDLE	INDDEFNCATG
16	FtnClass			CLAS						4		Left	LSEOBUNDLE	FUNCCLS
17	Brand				B							1		Left	GEOMOD	EMEABRANDCD
18	USPartNo			USPN___						7		Left		Null
19	SPECBID				S							1		Left	LSEOBUNDLE	SPECBID
20	SEOType		Always	SEOTYPE___					10		Left		"LSEOBUNDLE"
21	EntityId	Always	ENTITYID__					10		Right	LSEOBUNDLE	Entityid
		 
*/		

		COLUMN_VCT = new Vector();

		//4	RFAnumber	Always	RFANUM						6		Right	LSEOBUNDLE	QSMRFANUMBER
		XLColumn xlcCol = new XLColumn("RFAnumber","LSEOBUNDLE","QSMRFANUMBER");
		xlcCol.setAlwaysShow();
		xlcCol.setFFColumnLabel("RFANUM");
		xlcCol.setColumnWidth(6);
		xlcCol.setJustified(XLColumn.RIGHT);
		COLUMN_VCT.addElement(xlcCol);
		//5	AnnDate				ANNDATE___					10		Left	LSEOBUNDLE	BUNDLPUBDATEMTRGT
		xlcCol = new XLColumn("AnnDate","LSEOBUNDLE","BUNDLPUBDATEMTRGT");
		xlcCol.setFFColumnLabel("ANNDATE___");
		xlcCol.setColumnWidth(10);
		COLUMN_VCT.addElement(xlcCol);
		//6	GADate				GADATE____					10		Left	LSEOBUNDLE	BUNDLPUBDATEMTRGT
		xlcCol = new XLColumn("GADate","LSEOBUNDLE","BUNDLPUBDATEMTRGT");
		xlcCol.setFFColumnLabel("GADATE____");
		xlcCol.setColumnWidth(10);
		COLUMN_VCT.addElement(xlcCol);
		//7	WDDate				WDDATE____					10				LSEOBUNDLE	BUNDLUNPUBDATEMTRGT
		xlcCol = new XLColumn("WDDate","LSEOBUNDLE","BUNDLUNPUBDATEMTRGT");
		xlcCol.setFFColumnLabel("WDDATE____");
		xlcCol.setColumnWidth(10);
		COLUMN_VCT.addElement(xlcCol);
		//8	IBMPartno	Always	IBMPART_____				12		Left	LSEOBUNDLE	SEOID
		xlcCol = new XLColumn("IBMPartno","LSEOBUNDLE","SEOID");
		xlcCol.setFFColumnLabel("IBMPART_____");
		xlcCol.setColumnWidth(12);
		xlcCol.setAlwaysShow();
		xlcCol.setJustified(XLColumn.LEFT);
		COLUMN_VCT.addElement(xlcCol);
		//9	Description			DESCRIPTION___________________	30	Left	LSEOBUNDLE	PRCFILENAM
		xlcCol = new XLColumn("Description","LSEOBUNDLE","PRCFILENAM");
		xlcCol.setFFColumnLabel("DESCRIPTION___________________");
		xlcCol.setColumnWidth(30);
		COLUMN_VCT.addElement(xlcCol);
		//10	Div					DV							2		Left	PROJ	DIV
		xlcCol = new XLColumn("Div","PROJ","DIV");
		xlcCol.setFFColumnLabel("DV");
		xlcCol.setColumnWidth(2);
		COLUMN_VCT.addElement(xlcCol);
		//11	SegA				SGA							3		Left	SGMNTACRNYM	ACRNYM
		xlcCol = new XLColumn("SegA","SGMNTACRNYM","ACRNYM");
		xlcCol.setFFColumnLabel("SGA");
		xlcCol.setColumnWidth(3);
		COLUMN_VCT.addElement(xlcCol);
		//12	ProdID				P							1		Left	LSEOBUNDLE	PRODID
		xlcCol = new XLColumn("ProdID","LSEOBUNDLE","PRODID");
		xlcCol.setFFColumnLabel("P");
		xlcCol.setColumnWidth(1);
		COLUMN_VCT.addElement(xlcCol);
		//13	SysOrOpt			I							1		Left		If LSEOBUNDLE.BUNDLETYPE = Hardware then "Initial" else "Both"
		xlcCol = new XLSysOrOptColumn();
		xlcCol.setFFColumnLabel("I");
		xlcCol.setColumnWidth(1);
		COLUMN_VCT.addElement(xlcCol);	
		//14	PlntOfMfg			PLT							3		Left	GEOMOD	PLNTOFMFR
		xlcCol = new XLColumn("PlntOfMfg","GEOMOD","PLNTOFMFR");
		xlcCol.setFFColumnLabel("PLT");
		xlcCol.setColumnWidth(3);
		COLUMN_VCT.addElement(xlcCol);	
		//15	IndDefCat			IDC							3		Left	LSEOBUNDLE	INDDEFNCATG
		xlcCol = new XLColumn("IndDefCat","LSEOBUNDLE","INDDEFNCATG");
		xlcCol.setFFColumnLabel("IDC");
		xlcCol.setColumnWidth(3);
		COLUMN_VCT.addElement(xlcCol);
		//16	FtnClass			CLAS						4		Left	LSEOBUNDLE	FUNCCLS
		xlcCol = new XLColumn("FtnClass","LSEOBUNDLE","FUNCCLS");
		xlcCol.setFFColumnLabel("CLAS");
		xlcCol.setColumnWidth(4);
		COLUMN_VCT.addElement(xlcCol);
		//17	Brand				B							1		Left	GEOMOD	EMEABRANDCD
		xlcCol = new XLColumn("Brand","GEOMOD","EMEABRANDCD");
		xlcCol.setFFColumnLabel("B");
		xlcCol.setColumnWidth(1);
		COLUMN_VCT.addElement(xlcCol);	
		//18	USPartNo			USPN___						7		Left		Null
		xlcCol = new XLFixedColumn("USPartNo","");
		xlcCol.setFFColumnLabel("USPN___");
		xlcCol.setColumnWidth(7);
		COLUMN_VCT.addElement(xlcCol);	
		//19	SPECBID				S							1		Left	LSEOBUNDLE	SPECBID
		xlcCol = new XLColumn("SPECBID","LSEOBUNDLE","SPECBID");
		xlcCol.setFFColumnLabel("S");
		xlcCol.setColumnWidth(1);
		COLUMN_VCT.addElement(xlcCol);	
		//20	SEOType		Always	SEOTYPE___					10		Left		"LSEOBUNDLE"
		xlcCol = new XLFixedColumn("SEOType","LSEOBUNDLE");
		xlcCol.setFFColumnLabel("SEOTYPE___");
		xlcCol.setColumnWidth(10);
		xlcCol.setAlwaysShow();
		COLUMN_VCT.addElement(xlcCol);	
		//21	EntityId	Always	ENTITYID__					10		Right	LSEOBUNDLE	Entityid
		xlcCol = new XLIdColumn("EntityId","LSEOBUNDLE");
		xlcCol.setFFColumnLabel("ENTITYID__");
		xlcCol.setAlwaysShow();
		xlcCol.setJustified(XLColumn.RIGHT);
		xlcCol.setColumnWidth(10);
		COLUMN_VCT.addElement(xlcCol);			
	}

	/***********************************************
	 * Should the report be generated for this root
	 * Check for specified attributes on root - no structure available at this point in time
	 *
	 * If LSEOBUNDLE.SPECBID = 11457 (N) then
	 * Do not send Spreadsheet; instead send HTML Report
	 * Set QSMFEEDRESEND = "No" (No)
	 * Set Return Code = "0030" (Passed)
	 * Exit
	 * End if
	 *
	 * If RootEntity.STATUS <> "Final" (0020) then
	 * ErrorMessage LD(LSEOBUNDLE) NDN(LSEOBUNDLE) "was queued to send data; however, it is not Final"
	 * Set QSMFEEDRESEND = "No" (No)
	 * Set Return Code = "0040" (Failed)
	 * Exit
	 * End if
	 *
	 * If GENAREASELECTION  Not In ('6204'| '1999' | '6197') then (no report now)
	 * Set QSMFEEDRESEND = "No" (No)
	 * Set Return Code = "0030" (Passed)
	 * Exit
	 * End if
	 *
	 * @return boolean
	 * @throws MiddlewareException
	 * @throws SQLException
	 */
	public boolean canGenerateReport(EntityItem rootItem, QSMRPTABRSTATUS abr) {
		boolean isOk = true;
		String statusFlag = PokUtils.getAttributeFlagValue(rootItem, "STATUS");
		abr.addDebug("QSMLSEOBDLABR.generateReport: "+rootItem.getKey()+" STATUS: "+
			PokUtils.getAttributeValue(rootItem, "STATUS",", ", "", false)+" ["+statusFlag+"]");
		if (!STATUS_FINAL.equals(statusFlag)){
			// NOT_FINAL_ERR = was queued to send data; however, it is not Final
			abr.addError("NOT_FINAL_ERR",null);
			isOk = false;
		}

		Set testSet = new HashSet();
		for (int i=0; i<QSMRPTABRSTATUS.GEOS.length; i++){
			testSet.add(QSMRPTABRSTATUS.GEOS[i]);
		}
		testSet.add(QSMRPTABRSTATUS.GENAREA_WW); // allow WW now CQ22265
		if (!PokUtils.contains(rootItem, "GENAREASELECTION", testSet)){
			isOk = false;
			abr.addOutput("LSEOBUNDLE does not have required 'General Area Selection' value.");
			abr.addDebug("GENAREASELECTION did not include "+testSet+", ["+
					PokUtils.getAttributeValue(rootItem, "GENAREASELECTION",", ", "", false)+"]");
			abr.setNoReport();
		}
		testSet.clear();

		String specbid = PokUtils.getAttributeFlagValue(rootItem, "SPECBID");
		abr.addDebug(rootItem.getKey()+" SPECBID: "+specbid);
		if ("11457".equals(specbid) || specbid==null){  // is No
			abr.addOutput("LSEOBUNDLE is not a special bid.");
			isOk=false;
		}

		return isOk;
	}
	/***********************************************
	 * Should the report be generated for this extract
	 * Check for specified structure and attributes
	 * 5.	LSEOBUNDLEGEOMOD-d
	 * 6.	Include data GEOMOD where GENAREASELECTION  In ('6204'| '1999' | '6197') since GEOMOD may not exist
	 * 7.	Association LSEOBUNDLEPROJA -d
	 * The path used for the ChangeSpecBid is similar to the preceding except the first three items are not applicable.
	 *@return boolean 
	 */
	public boolean canGenerateReport(EntityList list, QSMRPTABRSTATUS abr){
		return true;
	}

	/**********************************
	 * get the name of the VE to use
	 * @see COM.ibm.eannounce.abr.sg.QSMABRInterface#getVeName()
	 */
	public String getVeName() {
		return "QSMLSEOBDL";
	}

	/***********************************************
	 *  Get the version
	 *
	 *@return java.lang.String
	 */
	public String getVersion(){
		return "1.8";
	}

	/*********************************************************************************
	 * @see COM.ibm.eannounce.abr.sg.QSMABRInterface#getColumnCount()
	 */
	public int getColumnCount() {
		return COLUMN_VCT.size();
	}

	/*********************************************************************************
	 * @see COM.ibm.eannounce.abr.sg.QSMABRInterface#getColumnLabel(int)
	 */
	public String getColumnLabel(int i) {
		return ((XLColumn)COLUMN_VCT.elementAt(i)).getColumnLabel();
	}
	/***********************************************
	 *  Get the label for the specified column index for the flatfile
	 *
	 *@return String
	 */
	public String getFFColumnLabel(int i){
		return ((XLColumn)COLUMN_VCT.elementAt(i)).getFFColumnLabel();
	}
	/***********************************************
	 *  Get the width for the specified column (used for flat files)  CQ00016165
	 *
	 *@return int
	 */
	public int getColumnWidth(int i){
		return ((XLColumn)COLUMN_VCT.elementAt(i)).getColumnWidth();
	}
	/* (non-Javadoc)
	 * @see COM.ibm.eannounce.abr.sg.QSMABRInterface#setColumnValue(org.apache.poi.hssf.usermodel.HSSFCell, java.lang.String, java.util.Hashtable, int)
	 */
	public void setColumnValue(HSSFCell cell,String rowtype, Hashtable itemTbl, int i) {
		((XLColumn)COLUMN_VCT.elementAt(i)).setColumnValue(cell,itemTbl);
	}
	/***********************************************
	 *  get the value for the specified column index - flat file only
	 *
	 */
	public String getColumnValue(String rowtype, Hashtable itemTbl, int i){
		return ((XLColumn)COLUMN_VCT.elementAt(i)).getColumnValue(itemTbl);
	}
	
	/*********************************************************************************
	 * @see COM.ibm.eannounce.abr.sg.QSMABRInterface#isChanged(com.ibm.transform.oim.eacm.diff.DiffEntity, int)
	 */
	public boolean isChanged(String rowtype, Hashtable itemTbl, int i)
	{
		return ((XLColumn)COLUMN_VCT.elementAt(i)).isChanged(itemTbl);
	}

	/***********************************************
	 *  Get the entity items to put in the ss for this geo
	 *  5.	LSEOBUNDLEGEOMOD-d
	 *  6.	Include data GEOMOD where GENAREASELECTION  In ('6204'| '1999' | '6197') since GEOMOD may not exist
	 *  7.	Association LSEOBUNDLEPROJA -d
	 *
	 * @see COM.ibm.eannounce.abr.sg.QSMABRInterface#getRowItems(COM.ibm.eannounce.objects.EntityList, java.util.Hashtable, java.lang.String, COM.ibm.eannounce.abr.sg.QSMRPTABRSTATUS)
	 * @return Vector
	 */
	public Vector getRowItems(EntityList list,Hashtable diffTbl, String geo, QSMRPTABRSTATUS abr){
		Vector rowVct = new Vector();

		EntityItem lseobdlitem = null;
		if (diffTbl!=null){
			Vector lseoVct = (Vector)diffTbl.get("ROOT");
			lseobdlitem = ((DiffEntity)lseoVct.firstElement()).getCurrentEntityItem();
		}else{
			lseobdlitem = list.getParentEntityGroup().getEntityItem(0);
		}

		abr.addDebug("QSMLSEOBDLABR.getRowItems "+lseobdlitem.getKey());

		Vector projVct = PokUtils.getAllLinkedEntities(lseobdlitem, "LSEOBUNDLEPROJA", "PROJ");

		XLRow xlrow = new XLRow(diffTbl==null?lseobdlitem:diffTbl.get(lseobdlitem.getKey()));
		rowVct.addElement(xlrow);

		Vector geomodVct = PokUtils.getAllLinkedEntities(lseobdlitem, "LSEOBUNDLEGEOMOD", "GEOMOD");
		//CQ22265
		Vector wwgeomodVct = PokUtils.getEntitiesWithMatchedAttr(geomodVct, "GENAREASELECTION",QSMRPTABRSTATUS.GENAREA_WW);
		abr.addDebug("QSMLSEOBDLABR.getRowItems GENAREASELECTION:"+QSMRPTABRSTATUS.GENAREA_WW+" has wwgeomodVct.size: "+
				wwgeomodVct.size());		
		if (wwgeomodVct.size()==0){
			// none found for WW look for geo geomod, first one found is used so dont need to combine
			geomodVct = PokUtils.getEntitiesWithMatchedAttr(geomodVct, "GENAREASELECTION",geo);
			abr.addDebug("QSMLSEOBDLABR.getRowItems GEOMOD GENAREASELECTION:"+geo+" has geomodVct.size: "+
					geomodVct.size());
		}else{
			geomodVct = wwgeomodVct;
		}		
		if (geomodVct.size()>0){
			if (geomodVct.size()>1){
				for (int g=0; g<geomodVct.size(); g++){
					abr.addDebug("QSMLSEOBDLABR.getRowItems WARNING: Found more than one GEOMOD "+
						((EntityItem)geomodVct.elementAt(g)).getKey());
				}
			}
			EntityItem ei = (EntityItem)geomodVct.firstElement();
			xlrow.addRowItem(diffTbl==null?ei:
				diffTbl.get(ei.getKey()));
			geomodVct.clear();
		}
	
		if (projVct.size()>0){
			EntityItem ei = (EntityItem)projVct.firstElement();
			xlrow.addRowItem(diffTbl==null?ei:
				diffTbl.get(ei.getKey()));
			// use Association PROJSGMNTACRNYMA to obtain SGMNTACRNYM.ACRNYM 
			Vector sgVct = PokUtils.getAllLinkedEntities(ei, "PROJSGMNTACRNYMA", "SGMNTACRNYM");
			if (sgVct.size()>0){
				EntityItem sgei = (EntityItem)sgVct.firstElement();
				xlrow.addRowItem(diffTbl==null?sgei:
					diffTbl.get(sgei.getKey()));
				sgVct.clear();
			}
		}
		abr.addDebug("QSMLSEOBDLABR.getRowItems Adding "+xlrow);

		return rowVct;
	}
	/***************************************************************
	 * If (Now() + 30 days >= BUNDLPUBDATEMTRGT >= Now()) OR
	 * (Now() + 30 days >= BUNDLUNPUBDATEMTRGT >= Now())
	 * then output rpt
	 * @see COM.ibm.eannounce.abr.sg.QSMABRInterface#withinDateRange(COM.ibm.eannounce.objects.EntityItem, java.lang.String, COM.ibm.eannounce.abr.sg.QSMRPTABRSTATUS)
	 */
	public boolean withinDateRange(EntityItem rootitem, String now, QSMRPTABRSTATUS abr) {
		boolean ok = false;
		Object args[] = new Object[5];
		String pubdate = PokUtils.getAttributeValue(rootitem, "BUNDLPUBDATEMTRGT", "",DEFAULT_PUBFROM, false);
		String unpubdate = PokUtils.getAttributeValue(rootitem, "BUNDLUNPUBDATEMTRGT", "", DEFAULT_PUBTO, false);
		PDGUtility pdgutil = new PDGUtility();
		String nowPlus30 = pdgutil.getDate(now, 30);
		args[0] = PokUtils.getAttributeDescription(rootitem.getEntityGroup(), "BUNDLPUBDATEMTRGT", "BUNDLPUBDATEMTRGT");
		args[1] = pubdate;
		args[2] = "";
		args[3] = now;
		args[4] = nowPlus30;

		abr.addDebug("QSMLSEOBDLABR.withinDateRange now "+now+" pubdate "+pubdate+" unpubdate "+unpubdate+" nowPlus30 "+nowPlus30);
		if ((nowPlus30.compareTo(pubdate)>=0) && (pubdate.compareTo(now)>=0)){
			ok = true;
		}else{
			args[2] = "not";
		}
		// DATE_RANGE_MSG = &quot;{0}&quot; value of &quot;{1}&quot; was {2} within range of {3} and {4}
		abr.addOutput("DATE_RANGE_MSG",args);
		args[0] = PokUtils.getAttributeDescription(rootitem.getEntityGroup(), "BUNDLUNPUBDATEMTRGT", "BUNDLUNPUBDATEMTRGT");
		args[1] = unpubdate;
		args[2] = "";
		if ((nowPlus30.compareTo(unpubdate)>=0) && (unpubdate.compareTo(now)>=0)){
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
	 *  'SBD' for a special bid
	 */
	public String getRowOne(EntityItem rootitem) { 
		String rfanum = PokUtils.getAttributeValue(rootitem, "QSMRFANUMBER", "", "", false);
		return XLColumn.formatToWidth(rfanum,6,XLColumn.RIGHT)+" SBD";
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
		return XLColumn.formatToWidth("Date______", 10)+" "+
			XLColumn.formatToWidth("Time___________", 15);
	}
	
}
