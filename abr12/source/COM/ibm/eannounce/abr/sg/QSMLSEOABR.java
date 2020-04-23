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
* QSM LSEO ABR 
* 
* QSMRPTABRSTATUS will launch the correct class
* From "SG FS SysFeed QSM Load  20090709.doc" 
*
*C.	Change Report: LSEO
*
*If GENAREASELECTION  Not In ('6204'| '1999' | '6197') then  (no report now)
*	Set QSMFEEDRESEND = "No" (No)
*	Set Return Code = "0030" (Passed)
*	Exit
*End if
*
*If WWSEOLSEO-d: WWSEO.SPECBID = 11457 (N) then
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
*Case "New" (New) or Empty
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
*	ErrorMessage LD(LSEO) NDN(LSEO) "Invalid data condition; the report was not generated."
*	Set QSMFEEDRESEND = "No" (No)
*	Set Return Code = "0040" (Failed)
*	Exit
*End Select Case
*Set QSMFEEDRESEND = "No" (No)
*Exit
*
*/
//QSMLSEOABR.java,v
//Revision 1.8  2013/05/22 14:35:30  wendy
//Updates for RCQ00132040-WI
//
//Revision 1.7  2009/07/13 15:28:44  wendy
//RCQ00055678 PRODID is now found on the parent WWSEO instead of the grandparent MODEL. PRODID changed from type "T" to type "U"
//
//Revision 1.6  2009/06/24 13:00:47  wendy
//Make 'IBMPartno' Left justified instead of Right CQ00016165
//
//Revision 1.5  2009/03/13 14:32:45  wendy
//CQ22294-LA CTO (XCC) - Update QSM Reports to Accept WW GenArea (WI CQ22265)
//
//Revision 1.4  2009/02/04 21:26:44  wendy
//CQ00016165 - Automated QSM feed from ePIMS HW to support the late change request from BIDS
//
//Revision 1.3  2008/12/10 16:39:39  wendy
//Spec change to reduce number of error rpts
//
//Revision 1.2  2008/11/19 21:18:56  wendy
//Add check for null specbid or null cofcat and add msg for model
//
//Revision 1.1  2008/09/30 12:50:12  wendy
//CQ00006066-WI LA CTO - EACM - Support GFS data Load of QSM (report)
//

public class QSMLSEOABR implements QSMABRInterface
{	
	//COFCAT	100	Hardware
	//COFCAT	101	Software
	//COFCAT	102	Service
	private static final String COFCAT_HW="100";
	private static final String COFCAT_SW="101";
  
	private static final Vector COLUMN_VCT;

	static {	
/*
5.	WWSEOLSEO-u
6.	MODELWWSEO-u
7.	Include row MODEL where COFCAT in ('100', '101') 
8.	MODELGEOMOD-d
9.	Include data GEOMOD where GENAREASELECTION  In ('6204'| '1999' | '6197') since GEOMOD may not exist
10.	Association WWSEOPROJA

*/

/*
	 SS 		SS		Flat File							Special Bids	
	 Column		Report	Column						Col		Col				LSEO	LSEO
Col# Heading	Passed	Heading						Width	Justify	EACM Entity	EACM Attribute
1						"2"							1		Left		"5"
2						Date______					10		Left		
3						Time___________				15		Left		
4	RFAnumber	Always	RFANUM						6		Right	LSEO	QSMRFANUMBER
5	AnnDate				ANNDATE___					10		Left	LSEO	LSEOPUBDATEMTRGT
6	GADate				GADATE____					10		Left	LSEO	LSEOPUBDATEMTRGT
7	WDDate				WDDATE____					10				LSEO	LSEOUNPUBDATEMTRGT
8	IBMPartno	Always	IBMPART_____				12		Left	LSEO	SEOID
9	Description			DESCRIPTION___________________	30	Left	WWSEO	PRCFILENAM
10	Div					DV							2		Left	PROJ	DIV
11	SegA				SGA							3		Left	SGMNTACRNYM	ACRNYM
12	ProdID				P							1		Left	WWSEO	PRODID RCQ00055678 was MODEL
13	SysOrOpt			I							1		Left	WWSEO	SEOORDERCODE
14	PlntOfMfg			PLT							3		Left	GEOMOD	PLNTOFMFR
15	IndDefCat			IDC							3		Left	LSEO	INDDEFNCATG
16	FtnClass			CLAS						4		Left	MODEL	FUNCCLS
17	Brand				B							1		Left	GEOMOD	EMEABRANDCD
18	USPartNo			USPN___						7		Left	LSEO	SEOID
19	SPECBID				S							1		Left	WWSEO	SPECBID
20	SEOType		Always	SEOTYPE___					10		Left		"LSEO"
21	EntityId	Always	ENTITYID__					10		Right	LSEO	Entityid	 
*/		
		
		COLUMN_VCT = new Vector();
		 		
		//4	RFAnumber	Always	RFANUM						6		Right	LSEO	QSMRFANUMBER
		XLColumn xlcCol = new XLColumn("RFAnumber","LSEO","QSMRFANUMBER");
		xlcCol.setAlwaysShow();
		xlcCol.setFFColumnLabel("RFANUM");
		xlcCol.setColumnWidth(6);
		xlcCol.setJustified(XLColumn.RIGHT);
		COLUMN_VCT.addElement(xlcCol);
		//5	AnnDate				ANNDATE___					10		Left	LSEO	LSEOPUBDATEMTRGT
		xlcCol = new XLColumn("AnnDate","LSEO","LSEOPUBDATEMTRGT");
		xlcCol.setFFColumnLabel("ANNDATE___");
		xlcCol.setColumnWidth(10);
		COLUMN_VCT.addElement(xlcCol);
		//6	GADate				GADATE____					10		Left	LSEO	LSEOPUBDATEMTRGT
		xlcCol = new XLColumn("GADate","LSEO","LSEOPUBDATEMTRGT");
		xlcCol.setFFColumnLabel("GADATE____");
		xlcCol.setColumnWidth(10);
		COLUMN_VCT.addElement(xlcCol);
		//7	WDDate				WDDATE____					10				LSEO	LSEOUNPUBDATEMTRGT
		xlcCol = new XLColumn("WDDate","LSEO","LSEOUNPUBDATEMTRGT");
		xlcCol.setFFColumnLabel("WDDATE____");
		xlcCol.setColumnWidth(10);
		COLUMN_VCT.addElement(xlcCol);
		//8	IBMPartno	Always	IBMPART_____				12		Left	LSEO	SEOID
		xlcCol = new XLColumn("IBMPartno","LSEO","SEOID");
		xlcCol.setFFColumnLabel("IBMPART_____");
		xlcCol.setColumnWidth(12);
		xlcCol.setAlwaysShow();
		xlcCol.setJustified(XLColumn.LEFT);
		COLUMN_VCT.addElement(xlcCol);
		//9	Description			DESCRIPTION___________________	30	Left	WWSEO	PRCFILENAM
		xlcCol = new XLColumn("Description","WWSEO","PRCFILENAM");
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
		//12	ProdID				P							1		Left	WWSEO	PRODID
		xlcCol = new XLColumn("ProdID","WWSEO","PRODID"); //RCQ00055678
		xlcCol.setFFColumnLabel("P");
		xlcCol.setColumnWidth(1);
		COLUMN_VCT.addElement(xlcCol);
		//13	SysOrOpt			I							1		Left	WWSEO	SEOORDERCODE
		xlcCol = new XLColumn("SysOrOpt","WWSEO","SEOORDERCODE");
		xlcCol.setFFColumnLabel("I");
		xlcCol.setColumnWidth(1);
		COLUMN_VCT.addElement(xlcCol);	
		//14	PlntOfMfg			PLT							3		Left	GEOMOD	PLNTOFMFR
		xlcCol = new XLColumn("PlntOfMfg","GEOMOD","PLNTOFMFR");
		xlcCol.setFFColumnLabel("PLT");
		xlcCol.setColumnWidth(3);
		COLUMN_VCT.addElement(xlcCol);	
		//15	IndDefCat			IDC							3		Left	LSEO	INDDEFNCATG
		xlcCol = new XLColumn("IndDefCat","LSEO","INDDEFNCATG");
		xlcCol.setFFColumnLabel("IDC");
		xlcCol.setColumnWidth(3);
		COLUMN_VCT.addElement(xlcCol);
		//16	FtnClass			CLAS						4		Left	MODEL	FUNCCLS
		xlcCol = new XLColumn("FtnClass","MODEL","FUNCCLS");
		xlcCol.setFFColumnLabel("CLAS");
		xlcCol.setColumnWidth(4);
		COLUMN_VCT.addElement(xlcCol);	
		//17	Brand				B							1		Left	GEOMOD	EMEABRANDCD
		xlcCol = new XLColumn("Brand","GEOMOD","EMEABRANDCD");
		xlcCol.setFFColumnLabel("B");
		xlcCol.setColumnWidth(1);
		COLUMN_VCT.addElement(xlcCol);
		//18	USPartNo			USPN___						7		Left	LSEO	SEOID
		xlcCol = new XLColumn("USPartNo","LSEO","SEOID");
		xlcCol.setFFColumnLabel("USPN___");
		xlcCol.setColumnWidth(7);
		COLUMN_VCT.addElement(xlcCol);	
		//19	SPECBID				S							1		Left	WWSEO	SPECBID
		xlcCol = new XLColumn("SPECBID","WWSEO","SPECBID");
		xlcCol.setFFColumnLabel("S");
		xlcCol.setColumnWidth(1);
		COLUMN_VCT.addElement(xlcCol);	
		//20	SEOType		Always	SEOTYPE___					10		Left		"LSEO"
		xlcCol = new XLFixedColumn("SEOType","LSEO");
		xlcCol.setFFColumnLabel("SEOTYPE___");
		xlcCol.setColumnWidth(10);
		xlcCol.setAlwaysShow();
		COLUMN_VCT.addElement(xlcCol);	
		//21	EntityId	Always	ENTITYID__					10		Right	LSEO	Entityid
		xlcCol = new XLIdColumn("EntityId","LSEO");
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
	 * If WWSEOLSEO-d: WWSEO.SPECBID = 11457 (N) then
	 * 	Do not send Spreadsheet; instead send HTML Report
	 * 	Set QSMFEEDRESEND = "No" (No)
	 * 	Set Return Code = "0030" (Passed)
	 * 	Exit
	 * End if
	 * 
	 * If RootEntity.STATUS <> "Final" (0020) then
	 * 	ErrorMessage LD(LSEO) NDN(LSEO) "was queued to send data; however, it is not Final" 
	 * 	Set QSMFEEDRESEND = "No" (No)
	 * 	Set Return Code = "0040" (Failed)
	 * 	Exit
	 * End if
	 * 
	 * If GENAREASELECTION  Not In ('6204'| '1999' | '6197') then (no report now)
	 * 	Set QSMFEEDRESEND = "No" (No)
	 * 	Set Return Code = "0030" (Passed)
	 * 	Exit
	 * End if
	 * 
	 * @return boolean
	 * @throws MiddlewareException 
	 * @throws SQLException 
	 */
	public boolean canGenerateReport(EntityItem rootItem, QSMRPTABRSTATUS abr) {
		boolean isOk = true;
		String statusFlag = PokUtils.getAttributeFlagValue(rootItem, "STATUS");
		abr.addDebug("QSMLSEOABR.generateReport: "+rootItem.getKey()+" STATUS: "+
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
			abr.addOutput("LSEO does not have required 'General Area Selection' value.");
			abr.addDebug("GENAREASELECTION did not include "+testSet+", ["+
					PokUtils.getAttributeValue(rootItem, "GENAREASELECTION",", ", "", false)+"]");
			abr.setNoReport();
		}
		testSet.clear();
		
		return isOk;
	}
	/***********************************************
	 * Should the report be generated for this extract
	 * Check for specified structure and attributes
	 *5.	WWSEOLSEO-u
	 *6.	MODELWWSEO-u
	 *7.	Include row MODEL where COFCAT in ('100', '101')
	 *8.	MODELGEOMOD-d
	 *9.	Include data GEOMOD where GENAREASELECTION  In ('6204'| '1999' | '6197') since GEOMOD may not exist
	 *10.	Association WWSEOPROJA
	 *The path used for the ChangeSpecBid is similar to the preceding except the first three items are not applicable.
	 *@return boolean
	 */ 
	public boolean canGenerateReport(EntityList list, QSMRPTABRSTATUS abr){
		boolean isok = false;
		EntityItem lseoitem = list.getParentEntityGroup().getEntityItem(0);

		// LSEO must have a MODEL where COFCAT in ('100', '101') 		
		// get its model
		Vector wwseoVct = PokUtils.getAllLinkedEntities(lseoitem, "WWSEOLSEO", "WWSEO");
		Vector mdlVct = PokUtils.getAllLinkedEntities(wwseoVct, "MODELWWSEO", "MODEL");
		for (int m=0; m<mdlVct.size(); m++){
			EntityItem mdlitem = (EntityItem)mdlVct.elementAt(m);
			String cofcat = PokUtils.getAttributeFlagValue(mdlitem, "COFCAT");
			abr.addDebug("QSMLSEOABR.canGenerateReport "+lseoitem.getKey()+
					" "+mdlitem.getKey()+" cofcat:"+cofcat);
			if (COFCAT_HW.equals(cofcat)||COFCAT_SW.equals(cofcat)){
				isok = true;
				break;
			}
		}
		
		if (!isok){
			abr.addOutput("Hardware or Software Model was not found.");
		}

		mdlVct.clear();

		for (int i=0; i<wwseoVct.size(); i++){
			EntityItem wwseoItem = (EntityItem)wwseoVct.elementAt(i);
			String specbid = PokUtils.getAttributeFlagValue(wwseoItem, "SPECBID");
			abr.addDebug(wwseoItem.getKey()+" SPECBID: "+specbid);
			if ("11457".equals(specbid) || specbid==null){  // is No
				abr.addOutput("WWSEO is not a special bid.");
				isok=false;
				break;
			}
		}
		
		wwseoVct.clear();

		return isok;
	}
	
	/**********************************
	 * get the name of the VE to use 
	 * @see COM.ibm.eannounce.abr.sg.QSMABRInterface#getVeName()
	 */
	public String getVeName() { 
		return "QSMLSEO";
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
	 * CQ00016165 
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
	 *5.	WWSEOLSEO-u
	 *6.	MODELWWSEO-u
	 *7.	Include row MODEL where COFCAT in ('100', '101')
	 *8.	MODELGEOMOD-d
	 *9.	Include data GEOMOD where GENAREASELECTION  In ('6204'| '1999' | '6197') since GEOMOD may not exist
	 *10.	Association WWSEOPROJA
	 *@return Vector
	 */
	public Vector getRowItems(EntityList list,Hashtable diffTbl, String geo, QSMRPTABRSTATUS abr){
		Vector rowVct = new Vector();

		EntityItem lseoitem = null;
		if (diffTbl!=null){
			Vector lseoVct = (Vector)diffTbl.get("ROOT");
			lseoitem = ((DiffEntity)lseoVct.firstElement()).getCurrentEntityItem();			
		}else{
			lseoitem = list.getParentEntityGroup().getEntityItem(0);
		}

		abr.addDebug("QSMLSEOABR.getRowItems "+lseoitem.getKey());
		// the LSEO must have a MODEL where COFCAT in ('100', '101') 
		Vector wwseoVct = PokUtils.getAllLinkedEntities(lseoitem, "WWSEOLSEO", "WWSEO");
		for (int w=0; w<wwseoVct.size(); w++){
			EntityItem wwseoitem = (EntityItem)wwseoVct.elementAt(w);

			// get its model					
			Vector projVct = PokUtils.getAllLinkedEntities(wwseoitem, "WWSEOPROJA", "PROJ");							
			Vector mdlVct = PokUtils.getAllLinkedEntities(wwseoitem, "MODELWWSEO", "MODEL");
			for (int m=0; m<mdlVct.size(); m++){
				EntityItem mdlitem = (EntityItem)mdlVct.elementAt(m);
				String cofcat = PokUtils.getAttributeFlagValue(mdlitem, "COFCAT");
				abr.addDebug("QSMLSEOABR.getRowItems "+lseoitem.getKey()+
						" "+mdlitem.getKey()+" cofcat:"+cofcat);
				if (COFCAT_HW.equals(cofcat)||COFCAT_SW.equals(cofcat)){
					XLRow xlrow = new XLRow(diffTbl==null?lseoitem:
						diffTbl.get(lseoitem.getKey()));
					rowVct.addElement(xlrow);

					xlrow.addRowItem(diffTbl==null?wwseoitem:
						diffTbl.get(wwseoitem.getKey()));
					xlrow.addRowItem(diffTbl==null?mdlitem:
						diffTbl.get(mdlitem.getKey()));

					Vector geomodVct = PokUtils.getAllLinkedEntities(mdlitem, "MODELGEOMOD", "GEOMOD");
					//CQ22265
					Vector wwgeomodVct = PokUtils.getEntitiesWithMatchedAttr(geomodVct, "GENAREASELECTION",QSMRPTABRSTATUS.GENAREA_WW);
					abr.addDebug("QSMLSEOABR.getRowItems GENAREASELECTION:"+QSMRPTABRSTATUS.GENAREA_WW+" has wwgeomodVct.size: "+
							wwgeomodVct.size());
					
					if (wwgeomodVct.size()==0){
						// none found for WW look for geo geomod, first one found is used so dont need to combine
						geomodVct = PokUtils.getEntitiesWithMatchedAttr(geomodVct, "GENAREASELECTION",geo);
						abr.addDebug("QSMLSEOABR.getRowItems GEOMOD GENAREASELECTION:"+geo+" has geomodVct.size: "+
								geomodVct.size());
					}else{
						geomodVct = wwgeomodVct;
					}
					if (geomodVct.size()>0){
						if (geomodVct.size()>1){// if more than one found, use first one
							for (int g=0; g<geomodVct.size(); g++){
								abr.addDebug("QSMLSEOABR.getRowItems WARNING: Found more than one GEOMOD for "+
									mdlitem.getKey()+" "+((EntityItem)geomodVct.elementAt(g)).getKey());
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
					abr.addDebug("QSMLSEOABR.getRowItems Adding "+xlrow);
				} // model is correct
			} // model loop					
		} // end wwseo loop
			
		return rowVct;
	}
	/***************************************************************
	 * If (Now() + 30 days >= LSEOPUBDATEMTRGT >= Now()) OR 
	 * (Now() + 30 days >= LSEOUNPUBDATEMTRGT >= Now()) 
	 * then output rpt
	 * @see COM.ibm.eannounce.abr.sg.QSMABRInterface#withinDateRange(COM.ibm.eannounce.objects.EntityItem, java.lang.String, COM.ibm.eannounce.abr.sg.QSMRPTABRSTATUS)
	 */
	public boolean withinDateRange(EntityItem rootitem, String now, QSMRPTABRSTATUS abr) {
		boolean ok = false;
		Object args[] = new Object[5];
		String pubdate = PokUtils.getAttributeValue(rootitem, "LSEOPUBDATEMTRGT", "", DEFAULT_PUBFROM, false);
		String unpubdate = PokUtils.getAttributeValue(rootitem, "LSEOUNPUBDATEMTRGT", "", DEFAULT_PUBTO, false);
		PDGUtility pdgutil = new PDGUtility();
		String nowPlus30 = pdgutil.getDate(now, 30);
		
		args[0] = PokUtils.getAttributeDescription(rootitem.getEntityGroup(), "LSEOPUBDATEMTRGT", "LSEOPUBDATEMTRGT");
		args[1] = pubdate;
		args[2] = "";
		args[3] = now;
		args[4] = nowPlus30;
		
		abr.addDebug("QSMLSEOABR.withinDateRange now "+now+" pubdate "+pubdate+" unpubdate "+unpubdate+" nowPlus30 "+nowPlus30);
		if ((nowPlus30.compareTo(pubdate)>=0) && (pubdate.compareTo(now)>=0)){
			ok = true;
		}else{
			args[2] = "not";
		}
		// DATE_RANGE_MSG = &quot;{0}&quot; value of &quot;{1}&quot; was {2} within range of {3} and {4}
		abr.addOutput("DATE_RANGE_MSG",args);
		args[0] = PokUtils.getAttributeDescription(rootitem.getEntityGroup(), "LSEOUNPUBDATEMTRGT", "LSEOUNPUBDATEMTRGT");
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
