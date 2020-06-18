// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package COM.ibm.eannounce.objects;

import java.sql.SQLException;
import java.util.*;
import java.text.*;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.middleware.taskmaster.AbortedTaskWriter;
import COM.ibm.opicmpdh.objects.*;
/**********************************************************************************
* This class generates a sales manual for the selected MODEL.
* The user enters a date and the report returns all Features that have a
* Global withdraw date effective earlier or = to the date entered. The output format is based on the autogen
* Sales Manual Section.
* CR2440
*
*/
// $Log: GENSALESMANPDG.java,v $
// Revision 1.9  2014/01/13 13:37:47  wendy
// migration to V17
//
// Revision 1.8  2007/10/04 19:03:21  wendy
// RQ0927061652  SG: EACM: change Attributes Provided and Attributes Required to Long Text
//
// Revision 1.7  2007/06/26 12:15:58  wendy
// Removed links to deprecated v4 css
//
// Revision 1.6  2007/04/21 15:18:31  wendy
// RQ0410076321 updates
//
// Revision 1.5  2007/03/15 17:52:30  wendy
// Added serializable to comparator
//
// Revision 1.4  2007/02/08 18:35:17  wendy
// Use db server timestamp instead of profile.getNow()
//
// Revision 1.3  2007/02/07 20:43:04  wendy
// Spec chg for RQ1129065855/CR0323066233 TIR6Y6UX6
//
// Revision 1.2  2007/01/17 19:17:30  wendy
// Added model conversions, feature conversions and supported devices for CR0425066856/RQ1103063724
//
// Revision 1.1  2006/10/02 17:38:52  wendy
// Init for CR2440, convert sales manual rpt to PDG
//

public class GENSALESMANPDG extends PDGActionItem
{
	static final long serialVersionUID = 20011106L;
    /** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2006  All Rights Reserved.";

    private static final String ERROR_MODELINVALID =
    	"You have selected a Model that does not meet the selection criteria."+NEW_LINE+
    	"The Category must be Hardware, the Subcategory must be System and the Group must be Base.";

	private static final String COFCAT_HW = "100";      // Hardware
	private static final String COFSUBCAT_SYS = "126";  // System
	private static final String COFGRP_BASE = "150";    // Base

	private static final Hashtable ORDERCODE_CELL_TBL = new Hashtable(5);
	private static final String ORDERCODE_MES  = "5956";        // MES
	private static final String ORDERCODE_INITIAL = "5957";     // Initial
	private static final String ORDERCODE_BOTH = "5955";        // Both
	private static final String ORDERCODE_SUPPORTED = "5958";   // Supported
	private static final String ORDERCODE_MUSTREMOVE = "5959";  // Must Remove

	static {
		ORDERCODE_CELL_TBL.put(ORDERCODE_INITIAL,"I");
		ORDERCODE_CELL_TBL.put(ORDERCODE_MES,"M");
		ORDERCODE_CELL_TBL.put(ORDERCODE_BOTH,"B");
		ORDERCODE_CELL_TBL.put(ORDERCODE_SUPPORTED,"S");
	//    ORDERCODE_CELL_TBL.put(ORDERCODE_MUSTREMOVE,"N");  N not wanted
		ORDERCODE_CELL_TBL.put(ORDERCODE_MUSTREMOVE," ");
	}

	private static final String INSTALL_CIF = "5671"; // CIF
	private static final String INSTALL_NA = "5673";  // N/A
	// 3.0a description  is this also inserted for 3.0b? also not in the spec
	private static final String NO_DESCRIPTION =
		".*   DESCRIPTION FILE NOT FOUND"+NEW_LINE+
		".*"+NEW_LINE+
		".* BEGIN FEATURE TEMPLATE"+NEW_LINE+
		".sk 1"+NEW_LINE+
		":ul c."+NEW_LINE+
		".kp on"+NEW_LINE;
	private static final int MAX_DESC_LEN = 28;     // max description len
	private static final int MAX_XMP_LEN = 69;      // matrix section max row len
	private static final int MAX_MATRIX_COLS = 10;  // max number of cols(models) in matrix section
	private static final int MAX_STR_LEN = 79;
	private static final int I20 = 20;
	private static final int I14 = 14;
	private static final int I15 = 15;
	private static final int I11 = 11;
	private static final int I16 = 16;
	private static final int I28 = 28;
	private static final int I42 = 42;
	private static final int I51 = 51;

    private static final char[] FOOL_JTEST_CR = {'\r'};
    private static final String CARRIAGE_RETURN = new String(FOOL_JTEST_CR);

	private String filterDate = null;  // date specfied on PDG entity
    private EntityItem theMdlItem = null;
    private StringBuffer salesManSb = new StringBuffer();
    private SMComparator smc = new SMComparator();
    private String strNow = null;

  	/******************************************
  	* Version
  	* @return String
  	*/
  	public String getVersion() {
  		return "$Revision: 1.9 $";
  	}

  	/******************************************
  	* Constructor
  	* @param mf  EANMetaFoundation
  	* @param ai  GENSALESMANPDG
  	* @throws MiddlewareRequestException
  	*/
	public GENSALESMANPDG(EANMetaFoundation  mf, GENSALESMANPDG ai)
		throws MiddlewareRequestException
	{
		super(mf, ai);
	}

  	/******************************************
  	* Constructor
  	* @param emf  EANMetaFoundation
  	* @param db  Database
  	* @param prof  Profile
  	* @param strActionItemKey String
  	* @throws SQLException
  	* @throws MiddlewareException
  	* @throws MiddlewareRequestException
  	*/
	public GENSALESMANPDG(EANMetaFoundation emf, Database db, Profile prof, String strActionItemKey)
		throws SQLException, MiddlewareException, MiddlewareRequestException
	{
	  	super(emf, db,  prof, strActionItemKey);
	}

  	/******************************************
  	* Debug dump info
  	* @param bBrief boolean
  	* @return String
  	*/
  	public String dump(boolean bBrief) {
   		StringBuffer strbResult = new StringBuffer();
   		strbResult.append("GENSALESMANPDG:" + getKey() + ":desc:" + getLongDescription());
  		strbResult.append(":purpose:" + getPurpose());
  		strbResult.append(":entitytype:" + getEntityType() + NEW_LINE);
   		return strbResult.toString();
  	}

  	/******************************************
  	* Purpose
  	* @return String
  	*/
  	public String getPurpose() {
  		return "GENSALESMANPDG";
  	}

  	/******************************************
  	* This actually does the PDG action if genData = true
  	* @param dbCurrent 	Database
	* @param prof  Profile
	* @param genData  boolean
	* @throws SQLException
	* @throws MiddlewareException
	* @throws MiddlewareShutdownInProgressException
	* @throws SBRException
  	* @return StringBuffer
  	*/
	protected StringBuffer checkMissingData(Database dbCurrent, Profile prof, boolean genData)
		throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException
	{
		StringBuffer sbReturn = new StringBuffer();
		String strTraceBase = "GENSALESMANPDG checkMissingData: ";
		GeneralAreaList gal;
        Vector mtVct = new Vector(1);
        Vector mdlVct = new Vector(1);
        String prevMt="";
        String currMt="";

		dbCurrent.debug(D.EBUG_INFO, strTraceBase+genData);

		// generate the salesmanual, genData state doesn't matter here
		// this code came from salesmanual jsp which supported multiple model roots
		// keep it just in case they decide to support multiple roots here
        gal = dbCurrent.getGeneralAreaList(prof);
        mdlVct.add(theMdlItem);
        for (int i=0; i<mdlVct.size(); i++) {
            // do all models of same machtype at the same time, sort grouped all MachType together in order
            EntityItem mdl2Item = (EntityItem)mdlVct.elementAt(i);
            currMt = m_utility.getAttrValueDesc(mdl2Item,"MACHTYPEATR");
            if (currMt.equals(prevMt)){ // same machtype
                mtVct.addElement(mdl2Item);
            }else {  // machtype has changed
                if (mtVct.size()>0) { // some models with the prev mt
                    // process all at once
                    sbReturn.append(processMachtype(prof, dbCurrent, gal, prevMt,mtVct));
                }
                mtVct.clear();
                prevMt=currMt;
                mtVct.addElement(mdl2Item);
            }
        }  // end each model

        if (mtVct.size()>0) { // some models with this mt
            // process all at once
            sbReturn.append(processMachtype(prof, dbCurrent, gal, currMt, mtVct));
        }

        // release memory
        mdlVct.clear();
        mtVct.clear();

		return sbReturn;
	}

  	/******************************************
  	* Get the announce filter date from the PDG entity
  	* @param dbCurrent 	Database
	* @param prof  Profile
	* @param pdgsmItem  EntityItem
	* @throws SQLException
	* @throws MiddlewareException
	* @throws MiddlewareRequestException
	* @throws MiddlewareShutdownInProgressException
	* @throws SBRException
  	*/
	protected void checkPDGAttribute(Database dbCurrent, Profile prof, EntityItem pdgsmItem)
		throws SQLException, MiddlewareRequestException, MiddlewareException,
		SBRException, MiddlewareShutdownInProgressException
	{
		EANAttribute att = pdgsmItem.getAttribute("SMANNDATE");
		if (att !=null){
			filterDate = ((String)att.get()).trim();
		}

		if (filterDate == null) {  // this shouldn't really happen, attribute is required
			m_SBREx.add("An Announce Date is required.");
		}
	}

  	/******************************************
  	* clear variables
  	*/
  	protected void resetVariables() {
     	theMdlItem = null;
     	filterDate = null;
	}

  	/******************************************
  	* What does this really do?
  	* @param dbCurrent 	Database
	* @param prof  Profile
	* @return byte[]
	* @throws SQLException
	* @throws MiddlewareException
	* @throws MiddlewareShutdownInProgressException
	* @throws SBRException
  	*/
  	public byte[] viewMissing(Database dbCurrent, Profile prof)
		throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException
	{
		return null;
	}

  	/******************************************
  	* This executes the action
  	*
  	* @param dbCurrent 	Database
	* @param prof  Profile
	* @throws SQLException
	* @throws MiddlewareException
	* @throws MiddlewareShutdownInProgressException
	* @throws SBRException
  	*/
	public void executeAction(Database dbCurrent, Profile prof)
		throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException
	{
		String strTraceBase = "GENSALESMANPDG executeAction: ";
    	// Set up current time
    	DatePackage dp = dbCurrent.getDates();
   		strNow = dp.getNow();

		m_SBREx = new SBRException(); // clear out any old info in this exception

		if (m_eiPDG == null || m_eiPDG.getEntityID()<0) {
			if (m_eiPDG == null) {
				D.ebug(D.EBUG_ERR,strTraceBase+"PDG entity is null");
				m_SBREx.add("PDG entity is null");
			}else{
				D.ebug(D.EBUG_ERR,strTraceBase+"PDG entity ID is negative "+m_eiPDG.getKey());
				m_SBREx.add("PDG entity id is a negative value. Please save before generating data.");
			}
			throw m_SBREx;
		}else{
			ExtractActionItem eaItem;
			EntityItem[] eiParm = {m_eiPDG};
			dbCurrent.debug(D.EBUG_INFO, strTraceBase +" entered for "+ m_eiPDG.getKey());

			prof = m_utility.setProfValOnEffOn(dbCurrent, prof);

			eaItem = new ExtractActionItem(null, dbCurrent, prof, "EXTSMRPT");

			// pull extract to get entities for sales manual
			m_ABReList = EntityList.getEntityList(dbCurrent, prof, eaItem, eiParm);

			dbCurrent.debug(D.EBUG_INFO, strTraceBase+" EXTSMRPT entitylist: "+NEW_LINE+outputList(m_ABReList));

			m_eiPDG = m_ABReList.getParentEntityGroup().getEntityItem(0); // use one from extract action

			// check for ann date
			checkPDGAttribute(dbCurrent, prof, m_eiPDG);
			// validate model is hardware, system, base
			checkDataAvailability(dbCurrent, prof, m_eiPDG);
			// if anndate was null and/or model wasn't hardware, system, base this will have an error count
			if (m_SBREx.getErrorCount() > 0) {
				savePDGResults(dbCurrent, prof, PDGUtility.STATUS_ERROR, m_SBREx.toString());
				throw m_SBREx;
			}
			// generate the sales manual
			salesManSb = checkMissingData(dbCurrent, prof, true);

			// save the sales man to the blob table and clear any error msgs
			savePDGResults(dbCurrent, prof, PDGUtility.STATUS_COMPLETE, salesManSb.toString());

			m_ABReList.dereference();
		}
	}

  	/******************************************
  	* Strange hiccup in getting blob attr when run from ABR occasionally, allow access here
  	*
	* @param prof  Profile
	* @return String
  	*/
	public String getSalesManualRpt(Profile prof)
	{
		return getHtmlStart(prof, strNow)+salesManSb.toString();
	}
  	/******************************************
  	* This checks for valid entity items to run on
  	* The m_ABReList entityList will be generated in code that calls this
  	* Make sure the MODEL is Hardware, System, Base
  	* The navigate action has filters for Hardware and System, but couldn't add Base
  	*
  	* @param dbCurrent 	Database
	* @param prof  Profile
	* @param pdgsmItem  EntityItem
	* @throws SQLException
	* @throws MiddlewareException
	* @throws MiddlewareShutdownInProgressException
	* @throws SBRException
  	*/
	protected void checkDataAvailability(Database dbCurrent, Profile prof, EntityItem pdgsmItem)
		throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException
	{
		String strTraceBase = "GENSALESMANPDG checkDataAvailability: ";
    	EntityGroup egMODEL = m_ABReList.getEntityGroup("MODEL");

    	dbCurrent.test(egMODEL != null,  strTraceBase + "MODEL EntityGroup is null");
    	dbCurrent.test(egMODEL.getEntityItemCount() > 0, strTraceBase + "No MODEL linked to this PDG");

    	theMdlItem = egMODEL.getEntityItem(0);

		//COFCAT = Hardware COFSUBCAT = System COFGRP = Base
		if(isSelected(theMdlItem, "COFCAT", COFCAT_HW) &&
		   isSelected(theMdlItem, "COFSUBCAT", COFSUBCAT_SYS) &&
		   isSelected(theMdlItem, "COFGRP", COFGRP_BASE)) {
		}
		else {
			m_SBREx.add(ERROR_MODELINVALID);
		}
	}

  	/******************************************
	* Is this flag selected
	*
	* @param entityItem EntityItem object
	* @param attrCode   String
	* @param flag       String
	* @return boolean
	*/
	private static boolean isSelected(EntityItem entityItem, String attrCode, String flag)
	{
		boolean isSet = false;
		EANFlagAttribute attr = (EANFlagAttribute)entityItem.getAttribute(attrCode);
		if (attr != null) {
			isSet = attr.isSelected(flag);
		}
		return isSet;
	}

	/**************************************************************************************
	* Output this machine type
	*
	* @param profile Profile object
	* @param dbCurrent Database object
	* @param gal GeneralAreaList
	* @param machtype String machine type
	* @param mdlVct  Vector of MODEL entityitems
	*/
	private String processMachtype(Profile profile, Database dbCurrent, GeneralAreaList gal,
		 String machtype, Vector mdlVct)
	  throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
		 COM.ibm.opicmpdh.middleware.MiddlewareException,
		 COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
		 SBRException,
		 java.sql.SQLException
	{
		String strTraceBase = "GENSALESMANPDG processMachtype: ";
		StringBuffer sb = new StringBuffer();
		String extractName = "MODELSMRPT";
		Hashtable featTbl = new Hashtable();  // prevent duplicates
		Vector fcDetailsVct = new Vector(1);
		boolean is30a;
		EntityList list;
		EANList metaList;
		EntityGroup prodGrp;
		EntityItem[] eiArray = new EntityItem[mdlVct.size()];
		mdlVct.copyInto(eiArray);

		list = EntityList.getEntityList(dbCurrent, profile,
			new ExtractActionItem(null, dbCurrent, profile, extractName),
			eiArray);

		if (list.getEntityGroupCount()==0){
			list.dereference();
			m_SBREx.add("No EntityGroups found for extract "+extractName+".  It may be missing from meta.");
			throw m_SBREx;
		}

		sb.append("<!-- "+extractName+" entitylist: "+NEW_LINE+outputList(list)+" -->"+NEW_LINE);
		dbCurrent.debug(D.EBUG_INFO, strTraceBase+" "+extractName+" entitylist: "+NEW_LINE+outputList(list));

		metaList = list.getEntityGroup("FEATURE").getMetaAttribute();

		is30a = (metaList.get("DESCRIPTION")!=null); // DESCRIPTION was removed from 3.0b
		sb.append("<!-- Is it 30a? "+is30a+" -->"+NEW_LINE);
		dbCurrent.debug(D.EBUG_INFO, strTraceBase+"Is it 30a? "+is30a);

		/*For the selected Model(s), choose all Features where PRODSTRUCT.ANNDATE is less than
		or equal to the Announce Date from the input screen.  If PRODSTRUCT.ANNDATE does not
		exist check FEATURE.FIRSTANNDATE.*/
		prodGrp = list.getEntityGroup("PRODSTRUCT"); // all prodstructs for these models
		for (int f=0; f<prodGrp.getEntityItemCount();f++) {
			EntityItem prodItem = prodGrp.getEntityItem(f);
			EntityItem featItem;
			String geo;
			String annDate = m_utility.getAttrValueDesc(prodItem,"ANNDATE");
			if (annDate.length()>0 && annDate.compareTo(filterDate)>0){
				// stop processing this feature
				sb.append("<!-- Skipping "+prodItem.getUpLink(0).getKey()+" "+prodItem.getKey()+" ann date: "+
					annDate+" is after "+filterDate+" -->"+NEW_LINE);
				dbCurrent.debug(D.EBUG_INFO, strTraceBase+
					"Skipping "+prodItem.getUpLink(0).getKey()+" "+prodItem.getKey()+" ann date: "+
					annDate+" is after "+filterDate);
				continue; // feature has been announced
			}
			// get the feature
			featItem = (EntityItem)prodItem.getUpLink(0);// must exist and be 1:1 here
			annDate = m_utility.getAttrValueDesc(featItem,"FIRSTANNDATE");
			if (annDate.length()>0 && annDate.compareTo(filterDate)>0){
				// stop processing this feature
				sb.append("<!-- Skipping "+featItem.getKey()+" ann date: "+annDate+" is after "+filterDate+" -->"+NEW_LINE);
				dbCurrent.debug(D.EBUG_INFO, strTraceBase+"Skipping "+featItem.getKey()+" ann date: "+annDate+" is after "+filterDate);
				continue; // feature has been announced
			}

			if (featTbl.get(featItem.getKey())!=null) {  // already found this feature, don't repeat it
				continue;
			}
			featTbl.put(featItem.getKey(), featItem.getKey());

			geo = getFeatureGeo(featItem, gal); // get geo for this feature
			fcDetailsVct.addElement(new SortedFeature(featItem, geo, machtype));
		}  // end each prodstruct

		Collections.sort(fcDetailsVct);  // sort on feature code

		// 02/16/06 put matrix first
		// output (Feature Matrix) section
		sb.append("<pre>"+NEW_LINE);
		getFeatureMatrix(fcDetailsVct,sb);
		sb.append("</pre>"+NEW_LINE);

		// output (Feature Code Details) section
		sb.append("<pre>"+NEW_LINE);
		getFeatureCodeDetails(machtype,fcDetailsVct,sb,is30a);
		sb.append("</pre>"+NEW_LINE);

		for (int f=0; f<fcDetailsVct.size(); f++) {
			SortedFeature sf = (SortedFeature)fcDetailsVct.elementAt(f);
			sf.dereference();  // done with this feature now
		}
		fcDetailsVct.clear();

		// new for CR0425066856/RQ1103063724
	    // model conversions
		sb.append("<pre>"+NEW_LINE);
	    getModelConversions(dbCurrent,profile,list.getParentEntityGroup(),sb);
		sb.append("</pre>"+NEW_LINE);

		// feature conversions
		sb.append("<pre>"+NEW_LINE);
		getFCConversions(dbCurrent, profile, list.getParentEntityGroup(),sb);
		sb.append("</pre>"+NEW_LINE);

		// External Machine Type (Support Device)
		sb.append("<pre>"+NEW_LINE);
		getSupportedDevices(list.getParentEntityGroup(), list.getEntityGroup("MDLCGOSMDL"), sb);
		sb.append("</pre>"+NEW_LINE);

		list.dereference();
		featTbl.clear();
		for (int f=0; f<eiArray.length;f++)  {
			eiArray[f] = null;
		}

		sb.append("<br />"+NEW_LINE);

		return sb.toString();
	}
	/********************************************************************************
	*new for CR0425066856/RQ1103063724
	* generate Model Conversions section
	* For the selected Model, choose all Model Conversions where MODELCONVERT.ANNDATE is less
	* than or equal to the Announce Date from the input screen. If MODELCONVERTANNDATE is null,
	* then the conversion should NOT be printed in the report.
	*
	*	.sk 2
	*	:h3.Model Conversions
	*	:p.
	*	:xmp.
	*	.kp off
	*		  From   	 To
	*	Type  Model  Type  Model
	*	----  -----  ----  -----
	*	9402   740   9402	 890
	*	9402   830   9402	 890
	*@param  dbCurrent  Database
	*@param  profile    Profile
	*@param  mdlGrp     EntityGroup root MODELs
	*@param  sb    		StringBuffer used for output
	*/
	private void getModelConversions(Database dbCurrent,Profile profile,
		EntityGroup mdlGrp,	StringBuffer sb)
		throws java.sql.SQLException, COM.ibm.opicmpdh.middleware.MiddlewareException,
		COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
		COM.ibm.eannounce.objects.SBRException
	{
		Vector mdlcvtVct = new Vector(1);
    	FixedFormatTable ffTbl = new FixedFormatTable(MAX_XMP_LEN);
    	// check each root model
    	for (int i=0; i<mdlGrp.getEntityItemCount(); i++){
			EntityItem mdlItem = mdlGrp.getEntityItem(i);
			Vector tmp = findMODELCONVERT(dbCurrent, profile, mdlItem,sb);
			mdlcvtVct.addAll(tmp);
			tmp.clear();
		}

		sb.append(".sk 2"+NEW_LINE);
		sb.append(":h3.Model Conversions"+NEW_LINE);
		sb.append(":p."+NEW_LINE);
		sb.append(":xmp."+NEW_LINE);
		sb.append(".kp off"+NEW_LINE);

		ffTbl.setRowContent(1, 7, "From");  // indexes are 1 based
		ffTbl.setRowContent(1, I20, "To");
		ffTbl.setRowContent(2, 1, "Type");
		ffTbl.setRowContent(2, 7, "Model");
		ffTbl.setRowContent(2, I14, "Type");
		ffTbl.setRowContent(2, I20, "Model");
		ffTbl.setRowContent(3, 1, "----");
		ffTbl.setRowContent(3, 7, "-----");
		ffTbl.setRowContent(3, I14, "----");
		ffTbl.setRowContent(3, I20, "-----");

		Collections.sort(mdlcvtVct,smc);
		if (mdlcvtVct.size()==0){
			sb.append("<!-- No MODELCONVERT found.-->"+NEW_LINE);
		}
		for (int m=0; m<mdlcvtVct.size();m++){
			EntityItem mdlcvt = (EntityItem)mdlcvtVct.elementAt(m);
			// check the date
			String cnvdate = m_utility.getAttrValueDesc(mdlcvt,"ANNDATE");
			if (cnvdate.length()>0 &&
				cnvdate.compareTo(filterDate)<=0){
				// output this modelconvert
				ffTbl.setRowContent(4+m, 1, m_utility.getAttrValueDesc(mdlcvt,"FROMMACHTYPE"));
				ffTbl.setRowContent(4+m, 7, m_utility.getAttrValueDesc(mdlcvt,"FROMMODEL"));
				ffTbl.setRowContent(4+m, I14, m_utility.getAttrValueDesc(mdlcvt,"TOMACHTYPE"));
				ffTbl.setRowContent(4+m, I20, m_utility.getAttrValueDesc(mdlcvt,"TOMODEL"));
			}else{
				sb.append("<!-- Skipping "+mdlcvt.getKey()+" failed date chk ANNDATE:"+cnvdate+"-->"+NEW_LINE);
			}
		}

		sb.append(ffTbl.getTable()); // add model cnvt table
		sb.append(":exmp."+NEW_LINE);

		ffTbl.clear();
		mdlcvtVct.clear();
	}

  	/*********************
	*new for CR0425066856/RQ1103063724
   	* find MODELCONVERT for this model
   	* Query all the MODELCONVERT’s entities where the FROMMACHTYPE/FROMMODEL equals the parent MODEL in the PDG.
	*@param  dbCurrent  Database
	*@param  profile    Profile
   	*@param  mdlItem EntityItem MODEL to find MODELCONVERT for using it as 'FROM MODEL'
	*@param  sbout   	StringBuffer for debug and output
   	*/
	private Vector findMODELCONVERT(Database dbCurrent, Profile profile, EntityItem mdlItem,
		StringBuffer sbout)
		throws java.sql.SQLException, COM.ibm.opicmpdh.middleware.MiddlewareException,
		COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
		COM.ibm.eannounce.objects.SBRException
	{
		Vector mdlconvVct = new Vector();
		StringBuffer sb = new StringBuffer();
		String machtype = m_utility.getAttrValueDesc(mdlItem,"MACHTYPEATR");
		String modelAtr = m_utility.getAttrValueDesc(mdlItem,"MODELATR");
		String strSai = "SRDMODELCONVERT";
		EntityItem[] aei = null;

		//sb.append("map_FROMMACHTYPE=" + machtype + ";");
		//sb.append("map_FROMMODEL=" + modelAtr);
		//RQ0410076321 use TO model instead of FROM model
		sb.append("map_TOMACHTYPE=" + machtype + ";");
		sb.append("map_TOMODEL=" + modelAtr);
		sbout.append("<!-- findMODELCONVERT searching for "+sb+" -->"+NEW_LINE);

		// search for MODELCONVERT
		aei = m_utility.dynaSearch(dbCurrent, profile, null, strSai, "MODELCONVERT", sb.toString());
		if (aei != null && aei.length > 0) {
			for (int j = 0; j < aei.length; j++) {
				EntityItem ei = aei[j];
				mdlconvVct.addElement(ei);
				aei[j]=null;
			}
		}

		return mdlconvVct;
	}
  	/*********************
	*new for CR0425066856/RQ1103063724
   	* find FCTRANSACTION for this model
	*:h5.Feature Conversions
	*:xmp.
	*.kp off
	*
	*               Parts       Continuous    Machine
	*From:   To:    Returned    Maintenance   Type     Model
	*----    ---   ---------    -----------   -------  -----
	*
	*:exmp.
	*
	*@param  dbCurrent  Database
	*@param  profile    Profile
	*@param  mdlGrp     EntityGroup root MODELs
	*@param  sb     	StringBuffer for output
   	*/
	private void getFCConversions(Database dbCurrent, Profile profile,
		EntityGroup mdlGrp,StringBuffer sb)
		throws java.sql.SQLException, COM.ibm.opicmpdh.middleware.MiddlewareException,
		COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
		COM.ibm.eannounce.objects.SBRException
	{
		Vector fctransVct = new Vector(1);
		EntityList list=null;
		FixedFormatTable ffTbl = new FixedFormatTable(MAX_XMP_LEN);

    	// check each root model
    	for (int i=0; i<mdlGrp.getEntityItemCount(); i++){
			EntityItem mdlItem = mdlGrp.getEntityItem(i);
			Vector tmp = findFCTRANS(dbCurrent, profile, mdlItem,sb);
			// this will only have nav attributes from searchaction
			fctransVct.addAll(tmp);
			tmp.clear();
		}

		if (fctransVct.size()>0){
			// pull an extract to get all attributes
			EntityItem[] eiArray = new EntityItem[fctransVct.size()];
    		fctransVct.copyInto(eiArray);
			list = dbCurrent.getEntityList(profile,
				new ExtractActionItem(null, dbCurrent, profile, "DUMMY"),
				eiArray);

			fctransVct.clear();
			for (int i=0; i<list.getParentEntityGroup().getEntityItemCount(); i++){
				//RQ0410076321 add date filter
				EntityItem fcitem = list.getParentEntityGroup().getEntityItem(i);
				// check the date
				String cnvdate = m_utility.getAttrValueDesc(fcitem,"ANNDATE");
				if (cnvdate.length()>0 &&
					cnvdate.compareTo(filterDate)<=0){
					fctransVct.addElement(fcitem);
				}else{
					sb.append("<!-- Skipping "+fcitem.getKey()+" failed date chk ANNDATE:"+cnvdate+"-->"+NEW_LINE);
				}
			}

			Collections.sort(fctransVct, smc);
		}else {
			sb.append("<!-- No FCTRANSACTION found.-->"+NEW_LINE);
		}

		sb.append(":h5.Feature Conversions"+NEW_LINE);
		sb.append(":xmp."+NEW_LINE);
		sb.append(".kp off"+NEW_LINE);

		getFCTRANSHeader(ffTbl);

		/*
Wendy	the possible values for FCTRANSACTION.RETURNEDPARTS exceed the column width.. how should i handle it?
Alan Crudo	RETURNEDPARTS should only be a Yes or NO
Wendy	it can also be "Does not apply" or "Feature conversion only"
Alan Crudo	does not apply is a NO
Alan Crudo	lets do this, map "Does Not Apply" = No and "Feature Conversion Only" = FCO
RETURNEDPARTS	5100	Yes
RETURNEDPARTS	5101	No
RETURNEDPARTS	5102	Does not apply
RETURNEDPARTS	5103	Feature conversion only
		*/
		for (int i=0; i<fctransVct.size(); i++){
			EntityItem fcItem = (EntityItem)fctransVct.elementAt(i);
			String returnedPartsFlag = m_utility.getAttrValue(fcItem,"RETURNEDPARTS");
			String returnedPartsDesc = m_utility.getAttrValueDesc(fcItem,"RETURNEDPARTS");
			if (returnedPartsFlag.equals("5102")){ // "Does not apply"
				returnedPartsDesc = "No";
			}else if (returnedPartsFlag.equals("5103")){ //"Feature conversion only"
				returnedPartsDesc = "FCO";
			}
			ffTbl.setRowContent(i+4, 1, m_utility.getAttrValueDesc(fcItem,"FROMFEATURECODE"));
			ffTbl.setRowContent(i+4, 9, m_utility.getAttrValueDesc(fcItem,"TOFEATURECODE"));
			ffTbl.setRowContent(i+4, I15, returnedPartsDesc);
			ffTbl.setRowContent(i+4, I28, "              ");
			ffTbl.setRowContent(i+4, I42, m_utility.getAttrValueDesc(fcItem,"TOMACHTYPE"));
			ffTbl.setRowContent(i+4, I51, m_utility.getAttrValueDesc(fcItem,"TOMODEL"));
		}

		fctransVct.clear();

		sb.append(ffTbl.getTable());

		ffTbl.clear();
		if (list!=null){
			list.dereference();
		}

		// skipping EDITORNOTE for now
		sb.append(":exmp."+NEW_LINE);
	}
  	/*********************
	*new for CR0425066856/RQ1103063724
   	* find FCTRANSACTION for this model
   	* Query all the FCTRANSACTION’s entities where the FROMMACHTYPE/FROMMODEL equals
   	* the parent MODEL in the PDG.
	*@param  dbCurrent  Database
	*@param  profile    Profile
   	*@param  mdlItem EntityItem MODEL to find FCTRANSACTION for using it as 'FROM MODEL'
	*@param  sbout   	StringBuffer for debug and output
   	*/
	private Vector findFCTRANS(Database dbCurrent, Profile profile, EntityItem mdlItem,
		StringBuffer sbout)
		throws java.sql.SQLException, COM.ibm.opicmpdh.middleware.MiddlewareException,
		COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
		COM.ibm.eannounce.objects.SBRException
	{
		Vector fctransVct = new Vector();
		StringBuffer sb = new StringBuffer();
		String machtype = m_utility.getAttrValueDesc(mdlItem,"MACHTYPEATR");
		String modelAtr = m_utility.getAttrValueDesc(mdlItem,"MODELATR");
		String strSai = "SRDFCTRANSACTION";
		EntityItem[] aei = null;

		//sb.append("map_FROMMACHTYPE=" + machtype + ";");
		//sb.append("map_FROMMODEL=" + modelAtr);
		//RQ0410076321 use TO model instead of FROM model
		sb.append("map_TOMACHTYPE=" + machtype + ";");
		sb.append("map_TOMODEL=" + modelAtr);
		// search for FCTRANSACTION
		sbout.append("<!-- findFCTRANS searching for "+sb+" -->"+NEW_LINE);

		aei = m_utility.dynaSearch(dbCurrent, profile, null, strSai, "FCTRANSACTION", sb.toString());
		if (aei != null && aei.length > 0) {
			for (int j = 0; j < aei.length; j++) {
				EntityItem ei = aei[j];
				fctransVct.addElement(ei);
				aei[j]=null;
			}
		}

		return fctransVct;
	}
	/********************************************************************************
	*new for CR0425066856/RQ1103063724
	* get feature conversions table header
	*
	*               Parts       Continuous    Machine
	*From:   To:    Returned    Maintenance   Type     Model
	*----    ---   ---------    -----------   -------  -----
	*
	*:hp3.NOTE TO EDITORS - (insert EDITORNOTE):ehp2. skipping this now
	*:exmp.
	*
	* @param ffTbl FixedFormatTable
	*/
	private void getFCTRANSHeader(FixedFormatTable ffTbl) throws SBRException
	{
		ffTbl.setRowContent(1, I16, "Parts");  // indexes are 1 based
		ffTbl.setRowContent(1, I28, "Continuous");
		ffTbl.setRowContent(1, I42, "Machine");
		ffTbl.setRowContent(2, 1, "From:");
		ffTbl.setRowContent(2, 9, "To:");
		ffTbl.setRowContent(2, I16, "Returned");
		ffTbl.setRowContent(2, I28, "Maintenance");
		ffTbl.setRowContent(2, I42, "Type");
		ffTbl.setRowContent(2, I51, "Model");
		ffTbl.setRowContent(3, 1, "----");
		ffTbl.setRowContent(3, 9, "---");
		ffTbl.setRowContent(3, I15, "---------");
		ffTbl.setRowContent(3, I28, "-----------");
		ffTbl.setRowContent(3, I42, "-------");
		ffTbl.setRowContent(3, I51, "-----");
	}

	/********************************************************************************
	*new for CR0425066856/RQ1103063724
	* generate Supported Devices section
	* For the selected Model, choose all Support Devices where MODEL.ANNDATE is less
	* than or equal to the Announce Date from the input screen. If  MODEL.ANNDATE is null,
	* then the Supported Devices should NOT be printed in the report.
	*
	* this is listed as PDG MODEL filter.. it is already hw, system, base!!!
	* COFCAT = Hardware COFSUBCAT = % COFGRP = Base COFSUBGRP = %
	*
	* pdg root model is filtered on:
	* COFCAT = Hardware COFSUBCAT = System COFGRP = Base COFSUBGRP = %
	*@param  mdlGrp    EntityGroup root MODEL
	*@param  mdlcgosmdlGrp    EntityGroup get all downlinks (MODELs) from this relator (MDLCGOSMDL)
	*@param  sb  StringBuffer for output
	*/
	private void getSupportedDevices(EntityGroup mdlGrp,
		EntityGroup mdlcgosmdlGrp, StringBuffer sb)
		throws SBRException
	{
		Vector suppDevVct = new Vector();
		EntityItem mdlItem = mdlGrp.getEntityItem(0);
		String modelAtr = m_utility.getAttrValueDesc(mdlItem,"MODELATR");
		FixedFormatTable mdlFfTbl = new FixedFormatTable(MAX_XMP_LEN);
		Vector mdlColVct = new Vector(1);
		Hashtable cellTbl = new Hashtable();

		sb.append(":p.The following external machine types are supported on the following"+NEW_LINE);
		sb.append("model - ("+modelAtr+")."+NEW_LINE);
		sb.append(":p.This list is not all inclusive.  Many devices are supported through"+NEW_LINE);
		sb.append("standard ports.  Please refer to the sales manual"+NEW_LINE);
		sb.append("of the external machine type."+NEW_LINE);

		sb.append(":xmp."+NEW_LINE+".kp off"+NEW_LINE);  // fixme .kp off not in spec

    	// get each root modelatr for the matrix
    	for (int i=0; i<mdlGrp.getEntityItemCount(); i++){
			EntityItem modelaItem = mdlGrp.getEntityItem(i);
			String mdlatr = m_utility.getAttrValueDesc(modelaItem,"MODELATR");
			if (mdlatr.length()==0){
				mdlatr = "   ";
			}
			if (!mdlColVct.contains(mdlatr)){
				mdlColVct.addElement(mdlatr); // collection of all possible models
			}
		}

		//-- Entity:MODELa<----Relator:MDLCGMDL<---Entity:MODELCG  (MODELa is from PDG)
		//-- Entity:MODELCG---->Relator:MDLCGMDLCGOS--->Entity:MODELCGOS
		//-- Entity:MODELCGOS---->Relator:MDLCGOSMDL--->Entity:MODELc
		for (int i=0; i<mdlcgosmdlGrp.getEntityItemCount(); i++){
			EntityItem relator = mdlcgosmdlGrp.getEntityItem(i);
			for (int di=0; di<relator.getDownLinkCount(); di++)
			{
				EntityItem suppDevItem = (EntityItem)relator.getDownLink(di); // this is MODELc
				if (suppDevItem.getEntityType().equals("MODEL") && !suppDevVct.contains(suppDevItem)) {
					// check the date
					String suppdate = m_utility.getAttrValueDesc(suppDevItem,"ANNDATE");
					if (suppdate.length()>0 &&
						suppdate.compareTo(filterDate)<=0){
							String sdmachtype = m_utility.getAttrValueDesc(suppDevItem,"MACHTYPEATR");
							String sdmodelAtr = m_utility.getAttrValueDesc(suppDevItem,"MODELATR");

							// find all of its MODELa for the matrix
							Vector modelcgosVct = getAllLinkedEntities(suppDevItem, "MDLCGOSMDL","MODELCGOS");
							Vector modelcgVct = getAllLinkedEntities(modelcgosVct, "MDLCGMDLCGOS","MODELCG");
							Vector modelaVct = getAllLinkedEntities(modelcgVct, "MDLCGMDL","MODEL");
							for (int a=0; a<modelaVct.size(); a++){
								EntityItem modelaItem = (EntityItem)modelaVct.elementAt(a);
								String mdlatr = m_utility.getAttrValueDesc(modelaItem,"MODELATR");
								if (mdlatr.length()==0){
									mdlatr = "   ";
								}
								cellTbl.put(sdmachtype+sdmodelAtr+mdlatr, "X"); // info for cell at suppdev/model intersection
							}

							// output this suppdev
							suppDevVct.addElement(suppDevItem);
					}else{
						sb.append("<!-- Skipping "+suppDevItem.getKey()+" failed date chk ANNDATE:"+suppdate+"-->"+NEW_LINE);
					}
				}
			}
		}

		// sort output
		Collections.sort(suppDevVct, smc);

		Collections.sort(mdlColVct);  // these are just strings, make sure the modelatr are still sorted
		// output each suppdev model for this model
		if (suppDevVct.size()==0){
			// get model header for a empty set of models
			getSuppDevHeader(mdlFfTbl, mdlColVct,0);
			sb.append(mdlFfTbl.getTable());
			sb.append(":exmp."+NEW_LINE);
		}else {
			// output a max of 10 model columns per table
			int cnt = mdlColVct.size()/MAX_MATRIX_COLS;  // number of groups of 10
			int rem = mdlColVct.size()%MAX_MATRIX_COLS;  // leftover amt
			for (int i=0; i<cnt; i++) {
				// get model header for a subset of models
				getSuppDevHeader(mdlFfTbl, mdlColVct,i*MAX_MATRIX_COLS);
				getSuppDevMatrixTbl(mdlFfTbl, suppDevVct,mdlColVct, cellTbl, i*MAX_MATRIX_COLS);
				sb.append(mdlFfTbl.getTable());
				// reset the table
				mdlFfTbl.setTableWidth(MAX_XMP_LEN);
			}
			if (rem != 0) {
				// get model header for remaining models
				getSuppDevHeader(mdlFfTbl, mdlColVct,cnt*MAX_MATRIX_COLS);
				getSuppDevMatrixTbl(mdlFfTbl, suppDevVct,mdlColVct, cellTbl, cnt*MAX_MATRIX_COLS);
				sb.append(mdlFfTbl.getTable());
			}
			sb.append(":exmp."+NEW_LINE);
		}

		mdlFfTbl.clear();
	}
	/********************************************************************************
	*new for CR0425066856/RQ1103063724
	* Build supported device matrix header
	*
	*	      |D|  X = SUPPORTED DEVICE
	*         |1|
	*         |1|
	*         | |
	*  MT-MOD | |        DESCRIPTION
	*---------|-|-----------------------------------------
	*         | |
	*
	* @param ffTbl FixedFormatTable
	* @param mdlVct Vector of String (MODELATR)
	* @param startMdlId int starting index into mdlVct
	*/
	private void getSuppDevHeader(FixedFormatTable ffTbl, Vector mdlVct, int startMdlId)
	throws SBRException
	{
		int startPos = 10;
		StringBuffer sb = new StringBuffer();
		int filler = 0;

		ffTbl.setRowContent(1, startPos, "|");  // indexes are 1 based
		ffTbl.setRowContent(2, startPos, "|");
		ffTbl.setRowContent(3, startPos, "|");
		ffTbl.setRowContent(4, startPos, "|");
		ffTbl.setRowContent(5, 3, "MT-MOD |");
		ffTbl.setRowContent(6, 1, "---------|");
		ffTbl.setRowContent(7, startPos, "|");
		startPos++;

		// only do 10 max models at a time
		for (int i=startMdlId; i<mdlVct.size() && i<startMdlId+MAX_MATRIX_COLS; i++){
			String mdlatr = mdlVct.elementAt(i).toString(); // must be 3 chars long
			char data0[] = {mdlatr.charAt(0),'|'};
			char data1[] = {mdlatr.charAt(1),'|'};
			char data2[] = {mdlatr.charAt(2),'|'};
			ffTbl.setRowContent(1, startPos, new String(data0)); // first char of model atr
			ffTbl.setRowContent(2, startPos, new String(data1)); // second char of model atr
			ffTbl.setRowContent(3, startPos, new String(data2)); // third char of model atr
			ffTbl.setRowContent(4, startPos, " |");
			ffTbl.setRowContent(5, startPos, " |");
			ffTbl.setRowContent(6, startPos, "-|");
			ffTbl.setRowContent(7, startPos, " |");
			startPos+=2;
		}

		ffTbl.setRowContent(1, startPos, "  X = SUPPORTED DEVICE");
		ffTbl.setRowContent(5, startPos, "        DESCRIPTION");

		filler = MAX_XMP_LEN-startPos;
        // build delimiter row
		for (int i=0; i<filler; i++){
			sb.append("-");
		}

		ffTbl.setRowContent(6, startPos, sb.toString());
	}
	/********************************************************************************
	*new for CR0425066856/RQ1103063724
	* Build supported device matrix table for this subset of modelatr
	*
	* @param mdlFfTbl FixedFormatTable
	* @param suppDevVct Vector of EntityItem supported device (MODEL)
	* @param mdlVct Vector of String (MODELATR)
	* @param cellTbl Hashtable of String
	* @param startMdlId int starting index into mdlVct
	*/
	private void getSuppDevMatrixTbl(FixedFormatTable mdlFfTbl, Vector suppDevVct,
		Vector mdlVct, Hashtable cellTbl, int startMdlId) throws SBRException
	{
		Vector sdInMdlSetVct = new Vector();
		String curdvccat="";
		int row = mdlFfTbl.getRowCount()+1;

		// get each suppdev and see if it has one of the models
		for (int i=0; i<suppDevVct.size(); i++) {
			EntityItem suppDevItem = (EntityItem)suppDevVct.elementAt(i);
			String sdmachtype = m_utility.getAttrValueDesc(suppDevItem,"MACHTYPEATR");
			String sdmodelAtr = m_utility.getAttrValueDesc(suppDevItem,"MODELATR");

			// only output suppdev that have one of this set of MODELs
			// can tell by entry in cellTbl
			// if there is an entry for at least one of this set of models, then display this suppdev
			// only do 10 max models at a time
			for (int m=startMdlId; m<mdlVct.size() && m<startMdlId+MAX_MATRIX_COLS; m++){
				String mdlatr = mdlVct.elementAt(m).toString(); // must be 3 chars long
				String cell = (String)cellTbl.get(sdmachtype+sdmodelAtr+mdlatr);
				if (cell!=null){
					sdInMdlSetVct.addElement(suppDevItem);
					break;
				}
			}
		}

		for (int i=0; i<sdInMdlSetVct.size(); i++){
			int increment=0;
			EntityItem suppDevItem = (EntityItem)sdInMdlSetVct.elementAt(i);
			String dvccat = m_utility.getAttrValueDesc(suppDevItem,"COMPATDVCCAT");
			if (!curdvccat.equals(dvccat)){
				curdvccat = dvccat;
				// output category header
				setSuppDevTypeRow(mdlFfTbl,row+i, dvccat);
				row+=2; // acct for separators
			}
			// output each suppdev
			increment = setSuppDevRow(mdlFfTbl,row+i, suppDevItem, mdlVct, cellTbl, startMdlId);
			row=row+increment; // account for more than row for this suppdev
		}

		sdInMdlSetVct.clear();
	}
	/********************************************************************************
	*new for CR0425066856/RQ1103063724
	* Add the 'Compatible Device Category' row and its delimiter row
	*
	* ---------+-+--------External NetFinity Server--------
	* - - - - -|-|- - - - - - - - - - - - - - - - - - - - -
	*
	* @param ffTbl FixedFormatTable
	* @param row int with row number
	* @param dvccat String with value of COMPATDVCCAT for this set of SuppDev
	*/
	private void setSuppDevTypeRow(FixedFormatTable ffTbl,int row, String dvccat)
	throws SBRException
	{
		int startPos=0;
		int num = 0;
		int filler =0;
		String catPrefix="--------";
		StringBuffer sb = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();

        // get length of modelatr row
        int len = ffTbl.getRowLength(2); // row 1 has comment on it
		ffTbl.setRowContent(row, 1, "---------+"); // COMPATDVCCAT goes on this line
		// this is the separator line between each COMPATDVCCAT
		ffTbl.setRowContent(row+1, 1, "- - - - -|");

		// build category row
		startPos = ffTbl.getRowLength(row);
		num = (len - startPos)/2; // this is the number of modelatr for this table
		startPos++;
		for (int i=0; i<num; i++){
			ffTbl.setRowContent(row, startPos, "-+");
			ffTbl.setRowContent(row+1, startPos, "-|");
			startPos+=2;
		}

		// find number of dashes needed to complete the row
		filler = MAX_XMP_LEN-(catPrefix.length()+dvccat.length()+startPos);

		ffTbl.setRowContent(row, startPos, catPrefix+dvccat); // COMPATDVCCAT goes on this line
		// 'External NetFinity Server' is longest one.. 25 chars
		for (int i=0; i<filler; i++){
			sb.append("-");
		}
		filler = MAX_XMP_LEN-startPos;
		for (int i=0; i<filler; i+=2){
			sb2.append("- ");
		}

		// COMPATDVCCAT goes on this line
		ffTbl.setRowContent(row, catPrefix.length()+dvccat.length()+startPos, sb.toString());

		// this is the separator line between each COMPATDVCCAT
		ffTbl.setRowContent(row+1, startPos, sb2.toString());
	}
	/********************************************************************************
	*new for CR0425066856/RQ1103063724
	* Build row for this supp dev
	* 7210-030 |X|  IBM 7210-030  External DVD-RAM Drive
	*
	* @param ffTbl FixedFormatTable
	* @param row int
	* @param suppDevItem EntityItem
	* @param mdlVct Vector of String (MODELATR)
	* @param cellTbl Hashtable with cell value
	* @param startMdlId int starting index into mdlVct
	*/
	private int setSuppDevRow(FixedFormatTable ffTbl,int row, EntityItem suppDevItem,
	Vector mdlVct, Hashtable cellTbl, int startMdlId)
	throws SBRException
	{
		int len =0;
		int increment=0;
        int startPos = I11;

		String machtype = m_utility.getAttrValueDesc(suppDevItem,"MACHTYPEATR");
		String modelAtr = m_utility.getAttrValueDesc(suppDevItem,"MODELATR");
		String mktg = m_utility.getAttrValueDesc(suppDevItem,"MKTGNAME");
		ffTbl.setRowContent(row, 1, machtype);
		ffTbl.setRowContent(row, 5, "-"+modelAtr);
		ffTbl.setRowContent(row, 10, "|");

		// only do 10 max models at a time
		for (int m=startMdlId; m<mdlVct.size() && m<startMdlId+MAX_MATRIX_COLS; m++){
			String mdlatr = mdlVct.elementAt(m).toString(); // must be 3 chars long
			String cell = (String)cellTbl.get(machtype+modelAtr+mdlatr);
			if (cell==null){
				cell=" ";
			}
			ffTbl.setRowContent(row, startPos, cell+"|");
			startPos+=2;
		}

        // add the description
        if(!mktg.startsWith(" ")) {  // add space after vertical bar
            mktg = " "+mktg;
        }
        // get length of current suppdev row
        len = ffTbl.getRowLength(row);
        if (len+mktg.length()<=MAX_XMP_LEN) {  //
            ffTbl.setRowContent(row, startPos, mktg);
        }else{
            Vector strVct = split(mktg, MAX_XMP_LEN-len, MAX_XMP_LEN);
            for (int v=0; v<strVct.size(); v++){
                ffTbl.setRowContent(v+row, startPos, strVct.elementAt(v).toString());
                startPos=1;
            }
            increment=strVct.size()-1;
            strVct.clear();
        }
        return increment;
	}

	/********************************************************************************
	* Get geos around each fc, unless it is WW,then don't display
	*@param  featItem   EntityItem
	*@param  gal    GeneralAreaList
	*@return String
	*/
	private static String getFeatureGeo(EntityItem featItem, GeneralAreaList gal)
	{
		StringBuffer sb = new StringBuffer();
		// get the GEO
		boolean emea = gal.isRfaGeoEMEA(featItem);
		boolean can = gal.isRfaGeoCAN(featItem);
		boolean la = gal.isRfaGeoLA(featItem);
		boolean ap = gal.isRfaGeoAP(featItem);
		boolean us = gal.isRfaGeoUS(featItem);

		if(ap && can && emea && la && us)
		{
			//geo=""; // is "WW"
		}else {
			if (us){
				sb.append("US");
			}
			if(ap) {
				if (sb.length()>0){
					sb.append(", ");
				}
				sb.append("AP");
			}
			if(la) {
				if (sb.length()>0){
					sb.append(", ");
				}
				sb.append("LA");
			}
			if(can) {
				if (sb.length()>0){
					sb.append(", ");
				}
				sb.append("CAN");
			}
			if(emea) {
				if (sb.length()>0){
					sb.append(", ");
				}
				sb.append("EMEA");
			}
		}// end not WW
		return sb.toString();
	}

	/********************************************************************************
	* generate (Feature Code Details) section
	*@param  machtype    String
	*@param  featVct     Vector of SortedFeature
	*/
	private void getFeatureCodeDetails(String machtype, Vector featVct, StringBuffer sb,
		boolean is30a)
	{
		sb.append(":h3.Feature descriptions"+NEW_LINE);
		sb.append(":p.Following are the descriptions, in numeric order, for all features"+NEW_LINE);
		sb.append("on this model."+NEW_LINE);
		sb.append(":p.Attributes, as defined in the descriptions, state the interaction"+NEW_LINE);
		sb.append("of requirements among features."+NEW_LINE);
		sb.append(":p.Minimums and maximums are the absolute limits for a single"+NEW_LINE);
		sb.append("feature without regard to interaction with other features.  The"+NEW_LINE);
		sb.append("maximum valid quantity for MES orders may be different than for"+NEW_LINE);
		sb.append("initial orders.  The maximums listed below refer to the largest"+NEW_LINE);
		sb.append("quantity of these two possibilities."+NEW_LINE);
		sb.append(":p.The order type defines if a feature is orderable only on initial"+NEW_LINE);
		sb.append("orders, only on MES orders, on both initial and MES orders, or if a"+NEW_LINE);
		sb.append("feature is supported on a model as a result of a model conversion."+NEW_LINE);
		sb.append(":p."+NEW_LINE);
		sb.append(""+NEW_LINE);

		// output each feature for this machtype in fcode order
		// output each model for this feature in modelatr order
		if (featVct.size()>0){
			for (int f=0; f<featVct.size(); f++) {
				SortedFeature sf = (SortedFeature)featVct.elementAt(f);
	            String wdEffDate = sf.getWDEffDate(); // RQ1129065855/CR0323066233 TIR6Y6UX6
				String geo = sf.getGEO();
				String note = sf.getEditorNote();
				Vector mdlVct = sf.getModelProdstructs();// these have been sorted based on ps->model values
				if(geo.length()>0)  { // not WW
					sb.append(":p.:hp2."+geo+"---&gt;:ehp2."+NEW_LINE+".br"+NEW_LINE);
				}

				if (note.length()>0){
					// :hp2.(EDITORNOTE) already wrapped in hp2 tags
					sb.append(note);
				}

				sb.append(split(":h5. (#"+sf.getFcode()+") - "+sf.getDescription(),""+NEW_LINE,MAX_STR_LEN));
				sb.append(sf.getDetailDescriptionSection(is30a));
				sb.append(".kp off"+NEW_LINE);
				sb.append(".* MODEL SPECIFIC INFO SHOULD BE ADDED BELOW THIS LINE"+NEW_LINE);

				for (int m=0; m<mdlVct.size(); m++) {
					EntityItem prodItem = (EntityItem)mdlVct.elementAt(m);
					EntityItem mdl2Item = (EntityItem)prodItem.getDownLink(0);

					String model = m_utility.getAttrValueDesc(mdl2Item,"MODELATR");
					String min = m_utility.getAttrValueDesc(prodItem,"SYSTEMMIN");
					String max = m_utility.getAttrValueDesc(prodItem,"SYSTEMMAX");
					String intorder = m_utility.getAttrValueDesc(prodItem,"INITORDERMAX");
					String oslvl = m_utility.getAttrValueDesc(prodItem,"OSLEVELCOMPLEMENT");
					String order = m_utility.getAttrValueDesc(prodItem,"ORDERCODE");
					String csu = "No";
					String retparts = m_utility.getAttrValueDesc(prodItem,"RETURNEDPARTS");

					sb.append(".kp on"+NEW_LINE);
					sb.append(":li.For "+machtype+" - "+model+" ("+sf.getFcode()+")"+NEW_LINE);
					sb.append(":ul c."+NEW_LINE);
					if (min.length()>0){
						sb.append(":li.Minimum required: "+min+""+NEW_LINE);
					}
					if (max.length()>0){
						sb.append(":li.Maximum allowed: "+max+"  ");
						if (intorder.length()>0){
							sb.append("(Initial order maximum: "+intorder+" )"+NEW_LINE);
						}else{
							sb.append(""+NEW_LINE);
						}
					}else{
						if (intorder.length()>0){
							sb.append(":li.(Initial order maximum: "+intorder+" )"+NEW_LINE);
						}
					}

					sb.append(split(":li.OS level required: "+oslvl,""+NEW_LINE,MAX_STR_LEN));
					sb.append(":li.Initial Order/MES/Both/Supported: "+order+""+NEW_LINE);
					if(isSelected(prodItem, "INSTALL", INSTALL_CIF)) {
						csu="Yes";
					}
					if(isSelected(prodItem, "INSTALL", INSTALL_NA)) {
						csu="N/A";
					}
					sb.append(":li.CSU: "+csu+""+NEW_LINE);
					if (retparts.length()>0) {
						sb.append(":li.Return parts MES: "+retparts+""+NEW_LINE);
					}
					sb.append(":eul."+NEW_LINE);
					note = m_utility.getAttrValueDesc(prodItem, "EDITORNOTE");
					if (note.length()>0){
						// :NOTE. PRODSTRUCT(EDITORNOTE)- only insert if exists
						sb.append(split(":NOTE. "+note,""+NEW_LINE,MAX_STR_LEN));
					}

					/*
					RQ1129065855/CR0323066233 TIR6Y6UX6
					2.	For the selected Model, if the FEATURE.WITHDRAWDATEEFF_T or the PRODSTRUCT.WITHDRAWDATE
					is less than or equal to the Announce Date specified on the PDG, then print at the bottom of
					the technical data "This feature has been withdrawn".

					Always check the FEATURE.WITHDRAWDATEEFF_T, first, if it does not exist,
					then check the PRODSTRUCT.WITHDRAWDATE.
					*/
					if(wdEffDate.length()==0){
						wdEffDate = m_utility.getAttrValueDesc(prodItem,"WITHDRAWDATE");
					}

					if (wdEffDate.length()>0 && wdEffDate.compareTo(filterDate)<=0){
						sb.append(":p.This feature has been withdrawn"+NEW_LINE);
					}
					// end RQ1129065855/CR0323066233 TIR6Y6UX6
					sb.append(".kp off"+NEW_LINE);
				}  // end of model loop

				sb.append(":eul."+NEW_LINE);
				if(geo.length()>0) { //not WW, so close the geo tag
					sb.append(getCloseGeoTag(geo)+""+NEW_LINE);
				}

				sb.append(".*"+NEW_LINE);
				sb.append(".******** END OF MODEL SPECIFIC INFO ************************"+NEW_LINE);
			}
		} // features exist
	}
	/********************************************************************************
	* generate (Feature Matrix)  section
	*
	*    Hard code the report header, section header and column header text as shown in the example.
	*    (Allow for up to 10 model columns, with the DESCRIPTION of the SUPPDEVICE (using text wrapping)
	*    is not exceed 69 characters in any given text row)
	*
	*    :p.The following feature availability matrix for MT MODEL(MACHTYPEATR)
	*    :xmp.
	*                       MODEL(MODELATR)
	*                            |
	*                            \/
	*                           | | I = Initial
	*                           | | M = MES
	*                           | | B = Both (Initial and MES)
	*                           | | W = Withdrawn
	*                           | |
	*    FEAT/PN                | |        DESCRIPTION
	*    -----------------------|-|-----------------------------------------
	*    FEATURE(FEATURECODE)   |I|  FEATURE(MGKTNAME)
	*    :exmp.                 /\
	*                           |
	*                    PRODSTRUCT(ORDERCODE)
	*
	*    NOTE: For this report only print 50 lines of text, then repeat header (as many times as required)
	*    above with :xmp. and :exmp.  (The 50 lines is each single line including the xmp and exmp lines.)
	*
	* From Mark Gennrich's note:
	.**************************************************
	:h3.Feature availability matrix
	:p.
	Order codes:
	:ul.
	:li.I - Available on initial orders from the plant only
	:li.M - Available on field upgrade (MES) orders only
	:li.B - Available on both initial and field upgrade orders
	:li.S - Supported for migration only and cannot be ordered
	:eul.
	:p.As additional features are announced, supported, or withdrawn, this
	list will be updated.  Contact your IBM representative for additional
	information.
	.sp
	:xmp.
	.kp off
			|5|
			|7|
			|0|
			| |
	Feature | |        Description
	--------|-|-----------------------------------------
	0005    |B| Bulk Order Indicator

	:exmp.

	.**************************************

	Mike Slocum	The .kp on (for keep) tag tells the script interpreter to keep a section of text
	as a single block and not to allow a page break within the block.  So, for text within :xmp
	tags you could set .kp on and script will generate a failure if a page break occurs within
	the xmp/exmp tags when the document is printed or formatted.   And .kp on is the default.
	In general, I set .kp off following an xmp tag to allow page breaks within that text.  If you
	set .kp on then you should set it to off after the block of text that you want to stay together
	without a page break.
	Wendy	if you set .kp off following an xmp tag, do you need to set kp on before the exmp tag?
	Mike Slocum	No, the exmp resets the .kp back to its default value of on

	*
	*@param  featVct    Vector of SortedFeature
	*@param  sb        StringBuffer for output
	*/
	private void getFeatureMatrix(Vector featVct, StringBuffer sb)
		throws SBRException
	{
		// build the matrix table
		FixedFormatTable mdlFfTbl = new FixedFormatTable(MAX_XMP_LEN); // model portion of the matrix

		sb.append(":h3.Feature availability matrix"+NEW_LINE);
		sb.append(":p."+NEW_LINE);
		sb.append("Order codes:"+NEW_LINE);
		sb.append(":ul."+NEW_LINE);
		sb.append(":li.I - Available on initial orders from the plant only"+NEW_LINE);
		sb.append(":li.M - Available on field upgrade (MES) orders only"+NEW_LINE);
		sb.append(":li.B - Available on both initial and field upgrade orders"+NEW_LINE);
		sb.append(":li.S - Supported for migration only and cannot be ordered"+NEW_LINE);
		sb.append(":eul."+NEW_LINE);
		sb.append(":p.As additional features are announced, supported, or withdrawn, this"+NEW_LINE);
		sb.append("list will be updated.  Contact your IBM representative for additional"+NEW_LINE);
		sb.append("information."+NEW_LINE);
		sb.append(".sp "+NEW_LINE);

		// output each feature for this machtype in fcode order one per row
		// output each model for this feature in modelatr order one per column
		if (featVct.size()==0){
			// get model header for a empty set of models
			getMatrixHeader(mdlFfTbl, null,0);
			sb.append(":xmp."+NEW_LINE+".kp off"+NEW_LINE+mdlFfTbl.getTable());  // start a new matrix   .kp off not in spec
			sb.append(":exmp."+NEW_LINE);
		}else {
			Vector mdlColVct = new Vector(1);
			Hashtable cellTbl = new Hashtable();
			int cnt, rem;
			// get all possible model types
			for (int f=0; f<featVct.size(); f++) {
				SortedFeature sf = (SortedFeature)featVct.elementAt(f);
				Vector prodVct = sf.getModelProdstructs();
				for (int m=0; m<prodVct.size(); m++) {
					EntityItem prodItem = (EntityItem)prodVct.elementAt(m);
					String effDate = sf.getWDEffDate();
					String orderCode = "X";
					String orderFlag = m_utility.getAttrValue(prodItem,"ORDERCODE"); // need flag code here

					EntityItem mdl2Item = (EntityItem)prodItem.getDownLink(0);  // and only be 1 here based on VE def
					String mdlatr = m_utility.getAttrValueDesc(mdl2Item,"MODELATR");

					if (orderFlag.length()>0){
						orderCode = (String)ORDERCODE_CELL_TBL.get(orderFlag);
						if (orderCode==null){
							orderCode = "X";  // skip this one, don't care about these
						}
					}

					// check to see if FEATURE.WITHDRAWDATEEFF_T is less than or equal to the Announce Date
					// from the input screen. Then print ‘W’ in the model column
					if (effDate.length()>0 && effDate.compareTo(filterDate)<=0){
						orderCode = "S";//"W"; 02/16/06 don't use W now
					}

					if (mdlatr.length()==0){
						mdlatr = "   ";
					}
					if (!mdlColVct.contains(mdlatr)){
						mdlColVct.addElement(mdlatr); // collection of all possible models
					}
					cellTbl.put(sf.getFcode()+mdlatr, orderCode); // info for cell at feature/model intersection
				}
			}
			Collections.sort(mdlColVct);  // these are just strings, make sure the modelatr are still sorted

			// output a max of 10 model columns per table
			cnt = mdlColVct.size()/MAX_MATRIX_COLS;  // number of groups of 10
			rem = mdlColVct.size()%MAX_MATRIX_COLS;  // leftover amt
			for (int i=0; i<cnt; i++) {
				// get model header for a subset of models
				getMatrixHeader(mdlFfTbl, mdlColVct,i*MAX_MATRIX_COLS);
				// output the features, this is limited by the number of rows
				// the number of rows will vary based on geo tags or length of description
				getFeatureMatrixTbl(sb,mdlFfTbl, featVct,mdlColVct, cellTbl, i*MAX_MATRIX_COLS);
				// reset the table
				mdlFfTbl.setTableWidth(MAX_XMP_LEN);
			}
			if (rem != 0) {
				// get model header for remaining models
				getMatrixHeader(mdlFfTbl, mdlColVct,cnt*MAX_MATRIX_COLS);
				getFeatureMatrixTbl(sb,mdlFfTbl, featVct,mdlColVct, cellTbl, cnt*MAX_MATRIX_COLS);
			}

		} // features exist

		mdlFfTbl.clear();
	}

	/********************************************************************************
	* There is a limit of 69 chars per row inside xmp tags!!
	* Build header for this subset of MODELs using this template
	* one MODELATR per column, max of 10
	*
	*        |5| I = Initial
	*        |2| M = MES
	*        |0| B = Both (Initial and MES)
	*        | | W = Withdrawn
	*        | |
	*FEAT/PN | |        DESCRIPTION
	*--------|-|-----------------------------------------
	* From Mark Gennrich's note:
			|5|
			|7|
			|0|
			| |
	Feature | |        Description
	--------|-|-----------------------------------------
	0005    |B| Bulk Order Indicator

	* @param ffTbl FixedFormatTable
	* @param mdlVct Vector of String (MODELATR)
	* @param startMdlId int index into the Vector to start with
	*/
	private static void getMatrixHeader(FixedFormatTable ffTbl, Vector mdlVct, int startMdlId) throws SBRException
	{
		int startPos = 9;
		ffTbl.setRowContent(1, startPos, "|");  // indexes are 1 based
		ffTbl.setRowContent(2, startPos, "|");
		ffTbl.setRowContent(3, startPos, "|");
	//02/16/06    ffTbl.setRowContent(4, startPos, "|");
		ffTbl.setRowContent(4, startPos, "|");
		ffTbl.setRowContent(5, 1, "Feature |");
		ffTbl.setRowContent(6, 1, "--------|");
		startPos++;

		if (mdlVct==null){  // build an empty template
			ffTbl.setRowContent(1, startPos, " |");
			ffTbl.setRowContent(2, startPos, " |");
			ffTbl.setRowContent(3, startPos, " |");
	//02/16/06        ffTbl.setRowContent(4, startPos, " |");
			ffTbl.setRowContent(4, startPos, " |");
			ffTbl.setRowContent(5, startPos, " |");
			ffTbl.setRowContent(6, startPos, "-|");
			startPos+=2;
		}else{
			// only do 10 max models at a time
			for (int i=startMdlId; i<mdlVct.size() && i<startMdlId+MAX_MATRIX_COLS; i++){
				String mdlatr = mdlVct.elementAt(i).toString(); // must be 3 chars long
				char data0[] = {mdlatr.charAt(0),'|'};
				char data1[] = {mdlatr.charAt(1),'|'};
				char data2[] = {mdlatr.charAt(2),'|'};
				ffTbl.setRowContent(1, startPos, new String(data0)); // first char of model atr
				ffTbl.setRowContent(2, startPos, new String(data1)); // second char of model atr
				ffTbl.setRowContent(3, startPos, new String(data2)); // third char of model atr
	//02/16/06            ffTbl.setRowContent(4, startPos, " |");
				ffTbl.setRowContent(4, startPos, " |");
				ffTbl.setRowContent(5, startPos, " |");
				ffTbl.setRowContent(6, startPos, "-|");
				startPos+=2;
			}
		}

		ffTbl.setRowContent(1, startPos, " ");//I = Initial");
		ffTbl.setRowContent(2, startPos, " ");//M = MES");
		ffTbl.setRowContent(3, startPos, " ");//B = Both (Initial and MES)");
	//02/16/06    ffTbl.setRowContent(4, startPos, " S = Supported");
		ffTbl.setRowContent(4, startPos, " ");//W = Withdrawn");
		ffTbl.setRowContent(5, startPos, "        Description");
		ffTbl.setRowContent(6, startPos, "-----------------------------------------");
	}

	/********************************************************************************
	* There is a limit of 69 chars per row inside xmp tags!!
	* Build entire matrix table(s) for all features that are linked to this subset of
	* models
	*
	*         |5| I = Initial
	*         |2| M = MES
	*         |0| B = Both (Initial and MES)
	*         | | W = Withdrawn
	*         | |
	* FEAT/PN | |        DESCRIPTION
	* --------|-|-----------------------------------------
	* 0356    |B| V.36 20-FT PCI CABLE
	* 0359    |B| X.21 20-FT PCI CABLE
	* 0360    |W| X.21 50-FT PCI CABLE
	*
	* @param  sb StringBuffer
	* @param mdlFfTbl FixedFormatTable with MODEL part of matrix
	* @param featVct Vector of SortedFeature
	* @param mdlVct Vector of String (MODELATR)
	* @param cellTbl Hashtable of ordercodes for model/feature cell
	* @param startMdlId int index into the model Vector to start with
	*/
	private void getFeatureMatrixTbl(StringBuffer sb,
		FixedFormatTable mdlFfTbl, Vector featVct,
		Vector mdlVct, Hashtable cellTbl, int startMdlId) throws SBRException
	{
		FixedFormatTable tmpFfTbl = new FixedFormatTable(MAX_XMP_LEN);
		String prevGeo="";
		String nextGeo="";
		String currentGeo="";
		Vector featInMdlSetVct = new Vector();

		sb.append(":xmp."+NEW_LINE+".kp off"+NEW_LINE+mdlFfTbl.getTable()); //02/16/06 only do models once

		// get each feature and see if it has one of the models
		for (int i=0; i<featVct.size(); i++) {
			SortedFeature sf = (SortedFeature)featVct.elementAt(i);

			//also only output features that have one of this set of MODELs
			// can tell by entry in cellTbl, this is the ORDERCODE from PRODSTRUCT
			// if there is an entry for at least one of this set of models, then display this feature
			String fcode = sf.getFcode();
			// only do 10 max models at a time
			for (int m=startMdlId; m<mdlVct.size() && m<startMdlId+MAX_MATRIX_COLS; m++){
				String mdlatr = mdlVct.elementAt(m).toString(); // must be 3 chars long
				String orderCode = (String)cellTbl.get(fcode+mdlatr);
				if (orderCode!=null && !orderCode.equals("X")){  // X values are to be dropped
					featInMdlSetVct.addElement(sf);
					break;
				}
			}
		}

		for (int i=0; i<featInMdlSetVct.size(); i++) {
			SortedFeature sf = (SortedFeature)featInMdlSetVct.elementAt(i);
			String fcode = sf.getFcode();
			int tmpRow = 1;
			Vector geoVct;
			int startPos = 10;
			int len;
			String desc = sf.getMktgName();

			// get geo, it must be a row in the table.. also need to group features with same geo tag
			currentGeo = sf.getGEO();
			// does the next feature have a different geo?
			if((i+1)<featInMdlSetVct.size()) {  // not last feature
				SortedFeature sfNext = (SortedFeature)featInMdlSetVct.elementAt(i+1);
				nextGeo = sfNext.getGEO();
			}else {   // is last feature
				nextGeo = "";
			}

			geoVct = getGeoTagVct(prevGeo, currentGeo);
			if (geoVct.size()>0) {
				for (int g=0; g<geoVct.size(); g++){
					tmpFfTbl.setRowContent(tmpRow++, 1, geoVct.elementAt(g).toString());
				}
				geoVct.clear();
			}

			// put this feature info into a tmp table, can't exceed 50 rows per matrix
			tmpFfTbl.setRowContent(tmpRow, 1, fcode+"    |");
			// only do 10 max models at a time
			for (int m=startMdlId; m<mdlVct.size() && m<startMdlId+MAX_MATRIX_COLS; m++){
				String mdlatr = mdlVct.elementAt(m).toString(); // must be 3 chars long
				String orderCode = (String)cellTbl.get(fcode+mdlatr);
				if (orderCode==null){
					orderCode=" ";
				}
				tmpFfTbl.setRowContent(tmpRow, startPos, orderCode+"|");
				startPos+=2;
			}
			// add the description
			if(!desc.startsWith(" ")) {  // add space after vertical bar
				desc = " "+desc;
			}
			// get length of current feature row
			len = tmpFfTbl.getRowLength(tmpRow);
			if (len+desc.length()<=MAX_XMP_LEN) {  //
				tmpFfTbl.setRowContent(tmpRow++, startPos, desc);
			}else{
				Vector strVct = split(desc, MAX_XMP_LEN-len, MAX_XMP_LEN);
				for (int v=0; v<strVct.size(); v++){
					tmpFfTbl.setRowContent(v+tmpRow, startPos, strVct.elementAt(v).toString());
					startPos=1;
				}
				tmpRow+=strVct.size();
				strVct.clear();
			}
			if(!currentGeo.equals(nextGeo)){  // close the geo with this feature
				if(!currentGeo.equals("")) {
					tmpFfTbl.setRowContent(tmpRow, 1, getCloseGeoTag(currentGeo));
					currentGeo=""; // reset it
				}
			}

			sb.append(tmpFfTbl.getTable()); // not splitting any more

			tmpFfTbl.setTableWidth(MAX_XMP_LEN); // reinit
			prevGeo = currentGeo;
		}

		sb.append(":exmp."+NEW_LINE);

		featInMdlSetVct.clear();
		tmpFfTbl.clear();
		sb.append(""+NEW_LINE);
	}
	/********************************************************************************
	* Replace all occurences of pattern in str with replace...
	* @param str String
	* @param pattern String
	* @param replace String
	* @return String
	*/
	private static String replace(String str, String pattern, String replace)
	{
		int s = 0;
		int e = 0;
		StringBuffer result = new StringBuffer(str.length());

		while ((e = str.indexOf(pattern, s)) >= 0) {
			result.append(str.substring(s, e));
			result.append(replace);
			s = e+pattern.length();
		}
		result.append(str.substring(s));
		return result.toString();
	}
	/********************************************************************************
	* Split on word boundaries if text exceeds max chars
	* if text starts with a comment, repeat it on each wrapped line
	* if text starts with ":hp2.", repeat on each line also end line with ":ehp2."
	* caller will allow for length of appended ":ehp2."
	*@param  txt    String to split
	*@param  firstlen int max length of first line
	*@param  maxlen int max length of all other lines
	*@return Vector
	*/
	private static Vector split(String txt, int firstlen, int maxlen)
	{
		Vector strVct = new Vector();
		boolean isheader = txt.startsWith(":hp2.");
		if (txt.length()>firstlen)
		{
			StringBuffer tmp = null;
			Vector lineVct = new Vector();
			int id = 0;
			BreakIterator boundary = BreakIterator.getLineInstance();
			StringBuffer sb = new StringBuffer();
			int curlen = firstlen;
			boolean firstWord = true;

			// make sure no carriage returns are in the txt
			txt = replace(txt, CARRIAGE_RETURN+NEW_LINE, NEW_LINE); //Editing in BUI adds carriage returns
			// remove new lines between content that can be merged by putting each complete line
			// in a stringbuffer, hold these sb in a vector

			while(id != -1)
			{
				String line = txt;
				id = txt.indexOf(NEW_LINE);
				if (id != -1)// found a newline
				{
					line = txt.substring(0,id);
					txt = txt.substring(id+1,txt.length());
				}
	// this preserves separate new lines, but it seems they don't want new lines in the data at all
	// THEY WILL NEVER BE ABLE TO HAVE A SEPARATE NEW LINE in the output of the data if this is done
	//          if (line.startsWith(".")||line.startsWith(":")||line.length()==0) {  // can't merge to prev
				if (line.startsWith(".")||line.startsWith(":")){// can't merge to prev
					tmp = new StringBuffer();
					lineVct.addElement(tmp);
				}else{
					if (tmp==null) { // first character wasn't a gml tag
						tmp = new StringBuffer();
						lineVct.addElement(tmp);
					}
					else { // not first character of the first line
						if (!tmp.toString().endsWith(" ")) {
							tmp.append(" "); // insert a space between merged text
						}
					}
				}
				tmp.append(line);
				if(txt.length()==0) { // if last line was terminated by a newline, dont add empty line
					break;
				}
			}

			for (int i=0; i<lineVct.size(); i++){
				int start,end,lineLength;
				boolean isComment;
				boolean firstText=true;
				String part = lineVct.elementAt(i).toString();
				if (part.length()<curlen) {
					strVct.add(part);
					if (firstWord){
						firstWord=false;
						curlen = maxlen;
					}
					continue;
				}

				isComment = part.startsWith(".*");
				boundary.setText(part);
				start = boundary.first();
				end = boundary.next();
				lineLength = 0;
				while (end != BreakIterator.DONE)
				{
					String word = part.substring(start,end);
					lineLength = lineLength + word.length();
					if (lineLength > curlen)
					{
						lineLength = word.length();
						if (isheader) {
							sb.append(":ehp2.");
						}
						strVct.add(sb.toString());
						sb.setLength(0);
						if (firstWord){
							firstWord=false;
							curlen = maxlen;
						}
					}

					// first word will be the '.' of the'.*' comment line, BreakIterator splits it
					if((isheader || isComment) && sb.length()==0 && !firstText){
						if (isComment) { // splitting a comment, so insert comment tag
							sb.append(".* ");
						}else {  // it is a header
							sb.append(":hp2.");
						}
					}

					sb.append(word);
					firstText=false;

					start = end;
					end = boundary.next();
				}
				if (sb.length()>0)
				{
					if (isheader) {
						sb.append(":ehp2.");
					}

					strVct.add(sb.toString());
					sb.setLength(0);
					if (firstWord){
						firstWord=false;
						curlen = maxlen;
					}
				}
			}
		}else {
			if (isheader) {
				txt=txt+":ehp2.";
			}
			strVct.add(txt);
		}
		return strVct;
	}

	/********************************************************************************
	* Split on word boundaries if text exceeds max chars
	* if text starts with a comment, repeat it on each wrapped line
	* if text starts with ":hp2.", repeat on each line also end line with ":ehp2."
	* caller will allow for length of appended ":ehp2."
	*@param  txt    String to split
	*@param  delimiter String delimiter on max len boundary
	*@param  maxlen int max length of a line
	*@return String
	*/
	private static String split(String txt, String delimiter, int maxlen)
	{
		StringBuffer outputSb = new StringBuffer();
		Vector strVct = split(txt, maxlen, maxlen);
		for (int v=0; v<strVct.size(); v++){
			outputSb.append(strVct.elementAt(v).toString()+delimiter);
		}
		return outputSb.toString();
	}

	/********************************************************************************
	* Get cloase GEO tag
	*@param  geo     String
	*@return String
	*/
	private static String getCloseGeoTag(String geo)
	{
		return ".br;:hp2.&lt;---"+geo+":ehp2.";
	}
	/********************************************************************************
	* Get open GEO tag
	*@param  geo     String
	*@return String
	*/
	private static String getOpenGeoTag(String geo)
	{
		return ":p.:hp2."+geo+"---&gt;:ehp2.";
	}

	/********************************************************************************
	* Get GEO tag format
	*@param  prevGeo     String
	*@param  currentGeo  String
	*@return Vector
	*/
	private static Vector getGeoTagVct(String prevGeo, String currentGeo)
	{
		Vector geoVct = new Vector();
		if(prevGeo.equals("")) {
			if(!currentGeo.equals("")) {
				geoVct.addElement(getOpenGeoTag(currentGeo));
			}
		}
		else {
			if(!prevGeo.equals(currentGeo))  {
				if(!prevGeo.equals("")) {
					geoVct.addElement(getCloseGeoTag(prevGeo));
				}
				if(!currentGeo.equals("")) {
					geoVct.addElement(getOpenGeoTag(currentGeo));
				}
			}
		}
		return geoVct;
	}
	/********************************************************************************
	* Convert string into valid html.  Special HTML characters are converted.
	*
	* @param txt    String to convert
	* @return String
	*/
	private static String convertToHTML(String txt)
	{
		String retVal=null;
		StringBuffer htmlSB = new StringBuffer();
		if (txt != null) {
			StringCharacterIterator sci = new StringCharacterIterator(txt);
			char ch = sci.first();
			while(ch != CharacterIterator.DONE)
			{
				switch(ch)
				{
				case '<': // could be saved as &lt; also. this will be &#60;
				case '>': // could be saved as &gt; also. this will be &#62;
				case '"': // could be saved as &quot; also. this will be &#34;
				case '&': // ignore entity references such as &lt; if user typed it, user will see it
						  // could be saved as &amp; also. this will be &#38;
					htmlSB.append("&#"+((int)ch)+";");
					break;
				default:
					htmlSB.append(ch);
					break;
				}
				ch = sci.next();
			}
			retVal = htmlSB.toString();
		}

		return retVal;
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
    private static Vector getAllLinkedEntities(Vector srcVct, String linkType, String destType)
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
    private static Vector getAllLinkedEntities(EntityItem entityItem, String linkType, String destType)
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
            }
        }

        // see if this relator is used as a downlink
        for (int ui=0; ui<entityItem.getDownLinkCount(); ui++)
        {
            EANEntity entityLink = entityItem.getDownLink(ui);
            if (entityLink.getEntityType().equals(linkType))
            {
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
     * save the results to the PDG entity
     *
     * @param dbCurrent Databse
     * @param prof		Profile
     * @param status	String
     * @param message	String
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws java.sql.SQLException
     */
    private void savePDGResults(Database dbCurrent, Profile prof,String status, String message)
    	throws MiddlewareException, MiddlewareRequestException, SQLException
    {
		String strTraceBase = "GENSALESMANPDG savePDGResults: ";
        try {
        	EANMetaAttribute ma = null;
        	EANAttribute attEM = null;
        	String origErrorMsg = "";
        	String enterprise = prof.getEnterprise();
        	int iOPWGID = prof.getOPWGID();
        	int iTranID = prof.getTranID();
        	int iNLSID = prof.getReadLanguage().getNLSID();
        	String entityType = m_eiPDG.getEntityType();
        	int iEntityID = m_eiPDG.getEntityID();

        	//String strNow = prof.getNow(); wrong.. this is the time the profile was instantiated!!!!
            ControlBlock cbOn = new ControlBlock(strNow, Profile.FOREVER, strNow, Profile.FOREVER, iOPWGID, iTranID);
            ControlBlock cbOff = new ControlBlock(strNow, strNow, strNow, strNow, iOPWGID, iTranID);

            Vector vctReturnEntityKeys = new Vector();
            Vector vctAtts = new Vector();
            ReturnEntityKey rek = new ReturnEntityKey(entityType, iEntityID, true);
            EntityGroup eg = m_eiPDG.getEntityGroup();
            if (eg == null) {
                // need true to get metacolumnorder
                eg = new EntityGroup(null, dbCurrent, prof, entityType, "Edit", true);
            }

            attEM = m_eiPDG.getAttribute("AFPDGERRORMSG");
            if (attEM != null) {
                origErrorMsg = attEM.toString();
            }

			if (status.equals(PDGUtility.STATUS_COMPLETE)) {
                ma = eg.getMetaAttribute("AFPDGERRORMSG");
                if (ma != null && origErrorMsg.length() > 0) {
					// complete now, so clear errors.. deactivate the attribute
					LongText lt = new LongText(enterprise, entityType, iEntityID, "AFPDGERRORMSG", "no error", iNLSID, cbOff);
					if (lt != null) {
						vctAtts.addElement(lt);
					}
                }

				// save the sales manual as an attachment in the result
                ma = eg.getMetaAttribute("AFPDGRESULT");
                if (ma != null && message.length() > 0) {
        			String strExt = null;
        			byte[] baValue = null;
        			Blob blob = null;
        			StringBuffer htmlSb = new StringBuffer(getHtmlStart(prof, strNow));
        			htmlSb.append(message);
        			htmlSb.append(getHtmlEnd());

                    try {
                        baValue = htmlSb.toString().getBytes("UTF8");
                    } catch (java.io.UnsupportedEncodingException ex) {
						D.ebug(D.EBUG_ERR,strTraceBase+"Error getting bytes: "+ex.getMessage());
                        baValue = htmlSb.toString().getBytes();
                    }

                    strExt = m_eiPDG.getKey() + ".html";
                    blob = new Blob(enterprise, entityType, iEntityID, "AFPDGRESULT", baValue, strExt, iNLSID, cbOn);
                    if (blob != null) {
                        vctAtts.addElement(blob);
                    }
                }
            }else {  // must be an error
                ma = eg.getMetaAttribute("AFPDGERRORMSG");
                if (ma != null) {
					LongText lt=null;
                    if (message.length() > 0) {
                        lt = new LongText(enterprise, entityType, iEntityID, "AFPDGERRORMSG", message, iNLSID, cbOn);
                    } else { // deactivate the attribute.. no msg to add to it
                        lt = new LongText(enterprise, entityType, iEntityID, "AFPDGERRORMSG", "no error", iNLSID, cbOff);
                    }
					if (lt != null) {
						vctAtts.addElement(lt);
					}
                }
            }

            rek.m_vctAttributes = vctAtts;
            vctReturnEntityKeys.addElement(rek);

            //updating entity items
            dbCurrent.update(prof, vctReturnEntityKeys, false, true);

            vctReturnEntityKeys.clear();
            vctAtts.clear();
        } finally {
            dbCurrent.commit();
            dbCurrent.freeStatement();
            dbCurrent.isPending();
        }
    }
	/********************************************************************************
	* Get tags to start HTML, (AHE compliant)
	*@param  	profile 	Profile
	*@param 	curTime String
	*@return String
	*/
	private String getHtmlStart(Profile profile, String curTime) {
		StringBuffer sb = new StringBuffer();
		/*v17 String today = curTime.substring(0,curTime.length()-7);
		sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"+NEW_LINE);
		sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">"+NEW_LINE);
		//<!-- Req from http://w3-03.ibm.com/transform/sas/as-web.nsf/ContentDocsByTitle/HTML+Authoring -->
		sb.append("<head>"+NEW_LINE);
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />"+NEW_LINE);
		//<!-- These are needed to be compliant with WebKing. Meta tags with the name attribute are used by applications,
		// such as search engines, and authors, administrators, and readers. -->
		sb.append("<meta name=\"Description\" content=\"Sales Manual PDG\" />"+NEW_LINE);
		sb.append("<meta name=\"Keywords\" content=\"EACM\" />"+NEW_LINE);
		sb.append("<meta name=\"Owner\" content=\"opicma@us.ibm.com\" />"+NEW_LINE);
		sb.append("<meta name=\"Robots\" content=\"noindex,nofollow\" />"+NEW_LINE);
		sb.append("<meta name=\"Security\" content=\"internal use only\" />"+NEW_LINE);
		sb.append("<meta name=\"Source\" content=\"v8 Template Generator\" />"+NEW_LINE);
		sb.append("<meta name=\"IBM.Country\" content=\"ZZ\" />"+NEW_LINE);
		sb.append("<meta name=\"DC.Date\" scheme=\"iso8601\" content=\""+today+"\" />"+NEW_LINE);
		sb.append("<meta name=\"DC.Language\" scheme=\"rfc1766\" content=\"en-US\" />"+NEW_LINE);
		sb.append("<meta name=\"DC.Rights\" content=\"Copyright (c) 2000,2006 by IBM Corporation\"  />"+NEW_LINE);
		sb.append("<meta name=\"feedback\" content=\"opicma@us.ibm.com\" />"+NEW_LINE);
		//<!-- Req from http://w3-03.ibm.com/transform/sas/as-web.nsf/ContentDocsByTitle/HTML+Authoring -->
		sb.append("<style type=\"text/css\" media=\"all\">"+NEW_LINE);
		sb.append("<!--"+NEW_LINE);
		sb.append("@import url(\"http://w3.ibm.com/ui/v8/css/screen.css\");"+NEW_LINE);
		sb.append("@import url(\"http://w3.ibm.com/ui/v8/css/interior.css\");"+NEW_LINE);
		sb.append("@import url(\"http://w3.ibm.com/ui/v8/css/popup-window.css\");"+NEW_LINE);
		sb.append("@import url(\"http://w3.ibm.com/ui/v8/css/tables.css\");"+NEW_LINE);
		sb.append("-->"+NEW_LINE);
		sb.append("</style>"+NEW_LINE);

		//<!-- print stylesheet MUST follow imported stylesheets -->
		sb.append("<link rel=\"stylesheet\" type=\"text/css\" media=\"print\" href=\"http://w3.ibm.com/ui/v8/css/print.css\" />"+NEW_LINE);
		//<!-- Req from http://w3.ibm.com/standards/intranet/design/v8/101_pg_tmplates/05_css.html -->

		sb.append("<title>e-announce | Model Sales Manual Report</title>"+NEW_LINE);
		sb.append("</head>"+NEW_LINE);
		sb.append("<body id=\"w3-ibm-com\">"+NEW_LINE);
		sb.append("<!-- GENSALESMANPDG VERSION="+getVersion()+" enterprise="+profile.getEnterprise()+" -->"+NEW_LINE);
		sb.append("<!-- PDG invoked for:  "+theMdlItem.getKey()+"-->"+NEW_LINE);

		// these are AHE requirements
		sb.append("<!-- start popup masthead //////////////////////////////////////////// -->"+NEW_LINE);
		sb.append("<div id=\"popup-masthead\">"+NEW_LINE);
		sb.append("    <img id=\"popup-w3-sitemark\" src=\"http://w3.ibm.com/ui/v8/images/id-w3-sitemark-small.gif\" alt=\"\" width=\"182\" height=\"26\" />"+NEW_LINE);
		sb.append("</div>"+NEW_LINE);
		sb.append("<!-- stop popup masthead //////////////////////////////////////////// -->"+NEW_LINE);
		sb.append("<!-- start content //////////////////////////////////////////// -->"+NEW_LINE);
		sb.append("<div id=\"content\">"+NEW_LINE);
		sb.append("    <!-- start main content -->"+NEW_LINE);
		sb.append("    <div id=\"content-main\">"+NEW_LINE);
		//<!-- Req from http://w3.ibm.com/standards/intranet/design/v8/106_second_windows/02_popup.html and
		//  http://w3.ibm.com/standards/intranet/design/v8/101_pg_tmplates/04_pg_style.html#templates -->

		sb.append("<!--START MAIN BODY CONTENT-->"+NEW_LINE);*/

		
		sb.append(AbortedTaskWriter.getHeaderBody("Sales Manual PDG","Model Sales Manual Report"));
		sb.append("<!-- GENSALESMANPDG VERSION="+getVersion()+" enterprise="+profile.getEnterprise()+" -->"+NEW_LINE);

		// output report heading
		sb.append("<p class=\"ibm-intro ibm-alternate-three\"><em>"+profile.getWGName()+" Model Sales Manual Report</em></p>"+NEW_LINE);
		sb.append("<p class=\"ibm-confidential\">IBM Confidential</p>"+NEW_LINE);
		sb.append("<table width=\"560\" summary=\"layout\">"+NEW_LINE);
		sb.append("<tr><td>User Role: </td><td>"+profile.getRoleDescription()+"</td></tr>"+NEW_LINE);
		sb.append("<tr><td>User email:</td><td>"+profile.getEmailAddress()+"</td></tr>"+NEW_LINE);
		sb.append("<tr><td>Current Date: </td><td>"+curTime.substring(0,curTime.length()-7)+"</td></tr>"+NEW_LINE);
		sb.append("<tr><td>Announce Date:</td><td>"+filterDate+"</td></tr>"+NEW_LINE);
		sb.append("</table><br />"+NEW_LINE);

		return sb.toString();
	}
	/********************************************************************************
	* Get tags to close HTML, (AHE compliant)
	*@return String
	*/
    private String getHtmlEnd()
    {
		StringBuffer sb = new StringBuffer();
		// needed to meet AHE, javascript causes IE security warnings
		/* v17 sb.append(getPopupFooter());

		sb.append("<!--END MAIN BODY CONTENT-->"+NEW_LINE);
		// needed to meet AHE, javascript causes IE security warnings
		sb.append(getHtmlTOU());
		// close required div tags
		sb.append("    </div>"+NEW_LINE);
		sb.append("    <!-- stop main content -->"+NEW_LINE);
		sb.append("</div>"+NEW_LINE);
		sb.append("<!-- stop content //////////////////////////////////////////// -->"+NEW_LINE);
		//<!-- Req from http://w3.ibm.com/standards/intranet/design/v8/checklist.html -->

		sb.append("</body></html>"+NEW_LINE);*/
		sb.append(AbortedTaskWriter.getTOUDiv()+NEW_LINE);
		sb.append("</body></html>"+NEW_LINE);
		return sb.toString();
	}
	/********************************************************************************
	* Get tags to support Terms Of Use (AHE requirement)
	*@return String
	* /
    private String getHtmlTOU()
    {
		StringBuffer sb = new StringBuffer();
		// needed to meet AHE, javascript causes IE security warnings
		sb.append("<script language=\"JavaScript\" type=\"text/javaScript\">"+NEW_LINE);
		sb.append("<!--"+NEW_LINE);
		sb.append("    function openTOU() {"+NEW_LINE);
		sb.append("    window.open(\"http://w3.ibm.com/w3/info_terms_of_use.html\", \"TOU\", \"dependent,width=800,height=600,screenX=100,screenY=100,left=100,top=100,titlebar,scrollbars,status,resizable\");"+NEW_LINE);
		sb.append("    }"+NEW_LINE);
		sb.append("//-->"+NEW_LINE);
		sb.append("</script>"+NEW_LINE);
		sb.append("        <p class=\"terms\">"+NEW_LINE);
		sb.append("            <a href=\"javascript:openTOU();\" id=\"tou\">Terms of use</a>"+NEW_LINE);
		sb.append("        </p>"+NEW_LINE);
		return sb.toString();
	}
	/********************************************************************************
	* Get popup footer (AHE requirement)
	*@return String
	* /
    private String getPopupFooter()
    {
		StringBuffer sb = new StringBuffer();
		sb.append("<!-- start popup footer //////////////////////////////////////////// -->"+NEW_LINE);
		sb.append("<div id=\"popup-footer\">"+NEW_LINE);
		sb.append("  <div class=\"hrule-dots\">&nbsp;</div>"+NEW_LINE);
		sb.append("    <div class=\"content\">"+NEW_LINE);
	    sb.append("    <script type=\"text/javascript\" language=\"JavaScript\">function terminate() { top.window.close();}function printReport() { top.window.print();}</script>"+NEW_LINE);
		sb.append("	<div style=\"padding:0 1em 0.4em 34px; float:right;\">"+NEW_LINE);
		sb.append("		<a class=\"popup-print-link\" style=\"float:none;\" href=\"javascript:printReport();\">Print</a>"+NEW_LINE);
		sb.append("		<a style=\"padding:0 1em 0.4em 4px;\" href=\"javascript:terminate();\">Close Window</a>"+NEW_LINE);
		sb.append("	</div>"+NEW_LINE);
	    sb.append("</div>"+NEW_LINE);
	    sb.append("<div style=\"clear:both;\">&nbsp;</div>"+NEW_LINE);
		sb.append("</div>"+NEW_LINE);
		sb.append("<!-- stop popup footer //////////////////////////////////////////// -->"+NEW_LINE);
		//<!-- Req from http://w3.ibm.com/standards/intranet/design/v8/101_pg_tmplates/04_pg_style.html#templates and
		//  http://w3.ibm.com/standards/intranet/design/v8/106_second_windows/02_popup.html -->
		return sb.toString();
	}*/

    private static final int MAX_LIST_LEN=256;
    /********************************************************************************
    * display content of entitylist for debug
    *@param  list    EntityList
    *@return String
    */
    private static String outputList(EntityList list) // debug only
    {
        StringBuffer sb = new StringBuffer();
        EntityGroup peg = null;
        if (list==null) {
            sb.append("Null List");
        }else{
            peg =list.getParentEntityGroup();
            if (peg!=null)
            {
                sb.append(peg.getEntityType()+" : "+peg.getEntityItemCount()+" parent items. ");
                if (peg.getEntityItemCount()>0)
                {
                    StringBuffer tmpsb = new StringBuffer();  // prevent more than 2048 chars in a line
                    tmpsb.append("IDs(");
                    for (int e=0; e<peg.getEntityItemCount(); e++)
                    {
                        tmpsb.append(" "+peg.getEntityItem(e).getEntityID());
                        if (tmpsb.length()>MAX_LIST_LEN)
                        {
                            sb.append(tmpsb.toString()+NEW_LINE);
                            tmpsb.setLength(0);
                        }
                    }
                    tmpsb.append(")");
                    sb.append(tmpsb.toString());
                }
                sb.append(NEW_LINE);
            }

            for (int i=0; i<list.getEntityGroupCount(); i++)
            {
                EntityGroup eg =list.getEntityGroup(i);
                sb.append(eg.getEntityType()+" : "+eg.getEntityItemCount()+" entity items. ");
                if (eg.getEntityItemCount()>0)
                {
                    StringBuffer tmpsb = new StringBuffer();  // prevent more than 2048 chars in a line
                    tmpsb.append("IDs(");
                    for (int e=0; e<eg.getEntityItemCount(); e++)
                    {
                        tmpsb.append(" "+eg.getEntityItem(e).getEntityID());
                        if (tmpsb.length()>MAX_LIST_LEN)
                        {
                            sb.append(tmpsb.toString()+NEW_LINE);
                            tmpsb.setLength(0);
                        }
                    }
                    tmpsb.append(")");
                    sb.append(tmpsb.toString());
                }
                sb.append(NEW_LINE);
            }
        }
        return sb.toString();
    }

	/*******************************************************************************
	* Support classes
	********************************************************************************/
	// sorted feature
	private class SortedFeature implements Comparable
	{
		private EntityItem featItem;
		private String sortKey = null;
		private String hwfccat = null;
		private String fcode = null;
		private String geo = "";
		private String desc = null;
		private Vector mdlVct = new Vector(1);   // ps->models for this feature

		SortedFeature(EntityItem item, String geoStr, String machtype)
		{
			Vector tmpProdVct = item.getDownLink();
			featItem = item;
			fcode = m_utility.getAttrValueDesc(featItem, "FEATURECODE");
			hwfccat = m_utility.getAttrValueDesc(featItem, "HWFCCAT");

			//sortKey=(hwfccat+fcode);   // category is top level sort for first 3 sections -- no longer done
			sortKey=fcode;  // fcode is key for section 4

			// get all models for this feature and sort them
			for (int i=0; i<tmpProdVct.size(); i++) {
				EntityItem prodItem = (EntityItem)tmpProdVct.elementAt(i);
				EntityItem mdl2Item = (EntityItem)prodItem.getDownLink(0);  // 1:1 here
				String mt = m_utility.getAttrValueDesc(mdl2Item,"MACHTYPEATR");

				// verify that this prodstruct has a valid date, it would have been discarded
				// earlier but this feature may have other prodstructs that allowed the feature
				// to get here
				String annDate = m_utility.getAttrValueDesc(prodItem,"ANNDATE");
				if (annDate.length()>0 && annDate.compareTo(filterDate)>0){
					// dont use this prodstruct
					continue; // feature has been withdrawn
				}
				//this is all the prodstructs tied to the feature, make sure a model with a
				// different machtype isn't pulled in
				if(!mt.equals(machtype)){
					continue;
				}

				// add each prodstruct, will sort on model values later 1:1 from ps to model but
				// model can have multiple ps
				mdlVct.addElement(prodItem);
			}

			Collections.sort(mdlVct, smc);  // sort on ps->model.machtypeatr, modelatr

			geo = geoStr;
		}

		Vector getModelProdstructs() { return mdlVct; }
		String getKey() { return featItem.getKey();}
		String getGEO() { return geo;}
		String getHwfccat() { return hwfccat;}
		String getFcode() { return fcode;}
		String getEditorNote() {
			String note = m_utility.getAttrValueDesc(featItem, "EDITORNOTE");
			if(note.length()>0){  // :hp2.(EDITORNOTE):ehp2. on each wrapped row
				note = split(":hp2."+note,""+NEW_LINE,MAX_STR_LEN-":ehp2.".length());
			}
			return note;
		}
		String getMktgName() {
			String name = m_utility.getAttrValueDesc(featItem, "INVNAME");
			if (name.length()==0){
				name = m_utility.getAttrValueDesc(featItem, "COMNAME");
			}
			return name;
		}
		String getWDEffDate() {
			return m_utility.getAttrValueDesc(featItem,"WITHDRAWDATEEFF_T");
		}
		String getDetailDescriptionSection(boolean is30a){
			StringBuffer sb = new StringBuffer();
			if (is30a) {
				String desc = m_utility.getAttrValueDesc(featItem,"DESCRIPTION");
				if (desc.length()==0){
					sb.append(NO_DESCRIPTION);
				}else{
					// wrap if necessary and split at comments
					sb.append(split(convertToHTML(desc),""+NEW_LINE,MAX_STR_LEN));
				}
			}else{
				String attProvided =m_utility.getAttrValueDesc(featItem,"ATTPROVIDED");
				String attRequired = m_utility.getAttrValueDesc(featItem,"ATTREQUIRED");
				String desc = m_utility.getAttrValueDesc(featItem,"FCMKTGDESC");
				if (desc.length()==0){
					sb.append(NO_DESCRIPTION);
				}else{
					// wrap if necessary and split at comments
					sb.append(split(convertToHTML(desc),""+NEW_LINE,MAX_STR_LEN));
					sb.append(".sk 1"+NEW_LINE);
					sb.append(":ul c."+NEW_LINE);
					sb.append(".kp on"+NEW_LINE);
				}
				if (attProvided.length()>0) {
//RQ0927061652		sb.append(":li.Attributes provided: "+attProvided+""+NEW_LINE); //(ATTPROVIDED  surpress if attribute is null)
					sb.append(split(":li.Attributes provided: "+attProvided,""+NEW_LINE,MAX_STR_LEN)); //(ATTPROVIDED  surpress if attribute is null)
				}
				if (attRequired.length()>0){
//RQ0927061652		sb.append(":li.Attributes required: "+attRequired+""+NEW_LINE);// (ATTREQUIRED surpress if attribute is null)
					sb.append(split(":li.Attributes required: "+attRequired,""+NEW_LINE,MAX_STR_LEN));// (ATTREQUIRED surpress if attribute is null)
				}
			}// is 3.0b

			return sb.toString();
		}

		String getDescription() {
			if (desc == null) {
				desc = m_utility.getAttrValueDesc(featItem, "INVNAME");  // will not exceed 28 chars
				if (desc.length()==0) {
					desc = m_utility.getAttrValueDesc(featItem, "COMNAME");
					if (desc.length()>MAX_DESC_LEN) {
						desc = desc.substring(0,MAX_DESC_LEN);
					}
				}
			}
			return desc;
		}

		void dereference() {
			featItem=null;
			sortKey = null;
			hwfccat = null;
			fcode = null;
			geo = null;
			desc=null;
			if (mdlVct!=null) {
				mdlVct.clear();
				mdlVct = null;
			}
		}

		public int compareTo(Object o) // used by Collections.sort()
		{
			SortedFeature se = (SortedFeature)o;
			return sortKey.compareTo(se.sortKey);
		}
	}

	/**********************************************************************************
	 * This class is used to sort output
	 */
	private class SMComparator implements java.util.Comparator,java.io.Serializable
	{
		public int compare(Object o1, Object o2) {
			String ofPN1 = "";
			String ofPN2 = "";
			EntityItem m1 = (EntityItem)o1;
			EntityItem m2 = (EntityItem)o2;
			if (m1.getEntityType().equals("PRODSTRUCT")){
				// entityitem is a prodstruct, so get the model for it
				m1 = (EntityItem)((EntityItem)o1).getDownLink(0);  // 1:1 between ps and model
				m2 = (EntityItem)((EntityItem)o2).getDownLink(0);  // 1:1 between ps and model
				ofPN1 = getModelPSvalue(m1);
				ofPN2 = getModelPSvalue(m2);
			}else if (m1.getEntityType().equals("MODELCONVERT")){
				ofPN1 = getModelConvertValue(m1);
				ofPN2 = getModelConvertValue(m2);
			}else if (m1.getEntityType().equals("MODEL")){
				ofPN1 = getSuppDevValue(m1);
				ofPN2 = getSuppDevValue(m2);
			}else if (m1.getEntityType().equals("FCTRANSACTION")){
				ofPN1 = getFctransValue(m1);
				ofPN2 = getFctransValue(m2);
			}
			return ofPN1.compareTo(ofPN2);
		}
		private String getModelPSvalue(EntityItem item){
			return m_utility.getAttrValueDesc(item,"MACHTYPEATR")+
				m_utility.getAttrValueDesc(item,"MODELATR");
		}
		/**********************************************************************************
		* The model conversion output will be sorted in the following order:
		*1.	To Machine Type
		*2.	To Model
		*3.	From Machine Type
		*4.	From Model
		*/
		private String getModelConvertValue(EntityItem item){
			return m_utility.getAttrValueDesc(item,"TOMACHTYPE")+
				m_utility.getAttrValueDesc(item,"TOMODEL")+
				m_utility.getAttrValueDesc(item,"FROMMACHTYPE")+
				m_utility.getAttrValueDesc(item,"FROMMODEL");
		}
		/**********************************************************************************
		* sort models (supported devices)
	 	*1.	Support Device Type (COMPATDVCCAT Alphabetically)
	 	*2.	Machine (Numerically)
		*3.	Model (Numerically)
	 	*/
		private String getSuppDevValue(EntityItem item){
			return m_utility.getAttrValueDesc(item,"COMPATDVCCAT")+
				m_utility.getAttrValueDesc(item,"MACHTYPEATR")+
				m_utility.getAttrValueDesc(item,"MODELATR");
		}
		/**********************************************************************************
		* The feature conversion output will be sorted in the following order:
		*
		*1.	To Model
		*2.	To Machine Type
		*3.	To Feature Code
		*4.	From Model
		*5.	From Machine Type
		*6.	From Feature Code
		*/
		private String getFctransValue(EntityItem item){
			return m_utility.getAttrValueDesc(item,"TOMODEL")+
				m_utility.getAttrValueDesc(item,"TOMACHTYPE")+
				m_utility.getAttrValueDesc(item,"TOFEATURECODE")+
				m_utility.getAttrValueDesc(item,"FROMMODEL")+
				m_utility.getAttrValueDesc(item,"FROMMACHTYPE")+
				m_utility.getAttrValueDesc(item,"FROMFEATURECODE");
		}
	}

	/**********************************************************************************
	 * This class will build a fixed format table.  It must be enclosed in <pre> tags
	 * to maintain formatting.
	 *
	 */
	private class FixedFormatTable
	{
		private Vector strVct = null;
		private String initStr = "";
		private int tblWidth = 0;

		/********************************************************************************
		 * Constructor
		 * width is max width of the table, table will be padded with blanks to reach that length
		 *
		 *@param  width  maximum width of the table
		 */
		FixedFormatTable(int width)
		{
			strVct = new Vector(1);
			setTableWidth(width);
		}

		/********************************************************************************
		 * Reset the table.  setTableWidth() must be called to init the table again
		 */
		void clear()
		{
			strVct.clear();
			initStr = "";
			tblWidth=0;
		}

		/********************************************************************************
		 * Init the table
		 *@param width int
		 */
		void setTableWidth(int width)
		{
			String blanks =
			"                                                                                "; // 80 chars
			StringBuffer sb= new StringBuffer(blanks);

			clear();

			tblWidth = width;
			// set init string
			while(sb.length()<tblWidth) {
				sb.append(blanks); }
			if(sb.length()>tblWidth) {  // don't exceed table width
				sb.delete(tblWidth, sb.length());
			}
			initStr = sb.toString();
		}

		/********************************************************************************
		 * Get row count
		 *@return int
		 */
		int getRowCount()
		{
			return strVct.size();
		}
		/********************************************************************************
		 * Get row length to determine how much room is left on the row
		 *@param rowNumber int
		 *@return int
		 */
		int getRowLength(int rowNumber)
		{
			int len=0;
			if (strVct.size()+1>rowNumber){
				// only trim trailing blanks, need to know how much room is left on the row
				String rowText = strVct.elementAt(rowNumber-1).toString();
				StringCharacterIterator sci = new StringCharacterIterator(rowText);
				char ch = sci.last();
				int last = 0;
				while(ch != CharacterIterator.DONE)
				{
					if(ch!=' '){
						break;
					}
					last++;
					ch = sci.previous();
				}

				len = rowText.length()-last;
			}
			return len;
		}
		/********************************************************************************
		 * Set content into the table row.
		 *
		 *@param  rowNumber row to insert content into, 1-based
		 *@param  startPos  starting position to insert value, 1-based
		 *@param  value     char to insert
		 *@throws SBRException if start position exceeds table max width
		 */
		void setRowContent(int rowNumber, int startPos, char value) throws SBRException
		{
			char data[] = {value};
			setRowContent(rowNumber, startPos, new String(data));
		}

		/********************************************************************************
		 * Set content into the table row.
		 *
		 *@param  rowNumber row to insert content into, 1-based
		 *@param  startPos  starting position to insert value, 1-based
		 *@param  value     String to insert, could be truncated to fit max width
		 *@throws SBRException if start position exceeds table max width
		 */
		void setRowContent(int rowNumber, int startPos, String value) throws SBRException
		{
			StringBuffer sb;
			// check for startpos exceeding tblWidth
			if(startPos>tblWidth) {
				m_SBREx.add("Start position ("+startPos+") exceeds table width ("+tblWidth+").");
				throw m_SBREx;
			}

			while(strVct.size()<rowNumber)
			{
				strVct.addElement(new StringBuffer(initStr));
			}

			sb = (StringBuffer)strVct.elementAt(rowNumber-1);
			if (value==null) {
				value="";
			}

			// stringbuffer is 0-based
			startPos--;
			sb.replace(startPos, startPos+value.length(), value);
			if(sb.length()>tblWidth) {  // don't exceed table width
				sb.delete(tblWidth, sb.length());
			}
		}

		/********************************************************************************
		 * Set content into the table row.
		 *
		 *@param  rowNumber row to insert content into, 1-based
		 *@param  startPos  starting position to insert value, 1-based
		 *@param  endPos    ending position to insert value, 1-based
		 *@param  value     String to insert, could be truncated to fit end-start length
		 *@throws SBRException if start position exceeds table max width
		 */
		void setRowContent(int rowNumber, int startPos, int endPos, String value) throws SBRException
		{
			StringBuffer sb;
			// check for startpos exceeding tblWidth
			if(startPos>tblWidth) {
				m_SBREx.add("Start position ("+startPos+") exceeds table width ("+tblWidth+").");
				throw m_SBREx;
			}

			while(strVct.size()<rowNumber)
			{
				strVct.addElement(new StringBuffer(initStr));
			}

			sb = (StringBuffer)strVct.elementAt(rowNumber-1);
			if (value==null) {
				value="";
			}

			// if value length exceeds specified length, truncate it
			if (value.length() > (endPos-startPos+1))
			{
				value = value.substring(0,(endPos-startPos+1));
			}

			// stringbuffer is 0-based
			sb.replace(startPos-1, startPos+value.length(), value);
			if(sb.length()>tblWidth) {  // don't exceed table width
				sb.delete(tblWidth, sb.length());
			}
		}

		/********************************************************************************
		 * Append content from one table to this table
		 *
		 *@param  ffTbl FixedFormatTable
		 */
		void append(FixedFormatTable ffTbl)
		{
			// set all new rows to this table's length and append
			for (int i=0; i<ffTbl.strVct.size(); i++){
				StringBuffer sb = (StringBuffer)ffTbl.strVct.elementAt(i);
				if(sb.length()>tblWidth) {  // don't exceed table width
					sb.delete(tblWidth, sb.length());
				}
				strVct.addElement(sb);
			}
		}

		/********************************************************************************
		 * Get the generated table.
		 * trim trailing blanks
		 *@return String table that must be enclosed in <pre> to maintain formatting
		 */
		String getTable()
		{
			StringBuffer sb = new StringBuffer();

			for (int i=0; i<strVct.size(); i++){
				String txt = strVct.elementAt(i).toString();
				int curLen = getRowLength(i+1);  // index is 1 based
				sb.append(txt.substring(0,curLen)+NEW_LINE);
			}

			return sb.toString();
		}
	}
}
