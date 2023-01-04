//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2005, 2011  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package COM.ibm.eannounce.abr.sg.bh;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.abr.sg.bh.DQABRSTATUS;
import COM.ibm.eannounce.objects.*;

import com.ibm.transform.oim.eacm.util.*;

import java.sql.SQLException;
import java.util.*;
import java.util.regex.Pattern;

/**********************************************************************************
 * MODELABRSTATUS class
 * 
 * BH FS ABR Data Quality 20120306.doc - OSN updates
 * 
 * BH FS ABR Data Quality 20111020e.xls BH Defect 67890 (support for old data)
 * sets changes BH FS ABR Data Quality 20110517.doc support already final or rfr
 * - gen catdata and queue ads abr need new VE EXRPT3MODEL2
 * 
 * BH FS ABR Data Qualtity Checks 20110413.xls mdl pla avail chk chg
 * 
 * BH FS ABR Data Qualtity Checks 20110407.xls swps chk chg
 * 
 * BH FS ABR Data Quality Checks 20110322.xls Delete 4.02 and 4.06
 * 
 * from BH FS ABR Catalog Attr Derivation 20110221b.doc need updated
 * EXRPT3MODEL1
 *
 * BH FS ABR Data Quality 20101012.doc need updated EXRPT3MODEL1
 *
 * BH FS ABR Data Quality 20100615.doc need actions for lifecycle workflow needs
 * LIFECYCLE attribute and transitions need meta - MODEL.WARRSVCCOVR missing
 * flag values need action to edit MODELWARR attributes (COUNTRYLIST,
 * EFFECTIVEDATE, ENDDATE) MODEL.WARRSVCCOVR needs EXISTS rule
 * 
 * From "SG FS ABR Data Quality 20090729.doc"
 *
 * The Model (MODEL) is part of a complex data structure and hence a Virtual
 * Entity (VE) is used to describe the applicable structure for the Data Quality
 * checks and another VE for the SAPL feed.
 *
 * Process There may be a SAPL XML Feed to OIDH (LEDGER) of this data which is
 * determined after the data quality checks (if any) are complete.
 *
 * The ABR produces a Report
 * 
 * A. Checking
 * 
 * A MODEL always has Availability Dates (AVAIL EFFECTIVEDATE) and an
 * Announcement Date (ANNOUNCEMENT ANNDATE) which are checked against the
 * planning dates. If it is a 'New' announcement and the MODEL is not a
 * 'Service', then the Announcement must include at least one Feature (FEATURE
 * or SWFEATURE).
 * 
 * If the Model is Hardware System and in the XCC_List of PDHDOMAINs, then it
 * must have an Image (Key 86) and a Warranty (Key 91). The Image and Warranty
 * must have UniqueCoverage which is explained in the section on Rules.
 * 
 * The dates on the product structure (e.g. PRODSTRUCT) and referenced by the
 * product structure (i.e. AVAIL) are checked to ensure that they are consistent
 * with the Model dates (either MODEL or AVAIL) and the Feature dates (e.g.
 * FEATURE).
 * 
 * 
 * B. STATUS changed to Ready for Review
 * 
 * 1. Set COMPATGENABR = 0020 (Queued) 2. PropagateOStoWWSEO 3. Set ADSABRSTATUS
 * = 0020 (Queued) C. STATUS changed to Final
 * 
 * 1. IF FirstTime(MODEL.STATUS = 0020 (Final)) THEN ELSE obtain the value for
 * SAPASSORTMODULE that was in effect at the ï¿½last DTS that STATUS was
 * Finalï¿½ and set SAPASSORTMODULEPRIOR equal to that value. 2.
 * PropagateOStoWWSEO 3. QueueSAPL 4. Set COMPATGENABR = 0020 (Queued) 5. Set
 * WWPRTABRSTATUS = &ABRWAITODS2 6. Set EPIMSABRSTATUS = &ABRWAITODS 7. Set
 * ADSABRSTATUS = 0020 (Queued) 8. IF (MODEL: MODELAVAIL-d: AVAILANNA:
 * ANNOUNCEMENT.STATUS = 0020 (Final) and (MODEL: MODELAVAIL: AVAILANNA:
 * ANNOUNCEMENT.ANNTYPE) = 19 (New) then Set (MODEL: MODELAVAIL: AVAILANNA:
 * ANNOUNCEMENT.WWPRTABRSTATUS) = &ABRWAITODS2 D. Status = Final
 * 
 * Data Quality checking is not required; however, the data may need to be sent
 * via XML to SAPL. This occurs when data is sent to SAPL but there is a
 * downstream failure which requires the XML to be sent again. The UI allows the
 * setting of SAPL to Send which will queue this ABR.
 * 
 * 1. Set SAPLABRSTATUS = 0020 (Queued) 2. Set ADSABRSTATUS = 0020 (Queued)
 *
 * 
 * The ABR sets its unique attribute (MODELABRSTATUS)
 * MODELABRSTATUS_class=COM.ibm.eannounce.abr.sg.MODELABRSTATUS
 * MODELABRSTATUS_enabled=true MODELABRSTATUS_idler_class=A
 * MODELABRSTATUS_keepfile=true MODELABRSTATUS_report_type=DGTYPE01
 * MODELABRSTATUS_vename=EXRPT3MODEL1
 * MODELABRSTATUS_CAT1=RPTCLASS.MODELABRSTATUS MODELABRSTATUS_CAT2=
 * MODELABRSTATUS_CAT3=RPTSTATUS
 * MODELABRSTATUS_domains=0050,0090,0150,0190,0210,0230,0240,0310,0330,0340,0360,0390
 */
// MODELABRSTATUS.java,v
// Revision 1.35 2011/06/10 17:40:40 wendy
// BH FS ABR Data Quality 20110517.doc : support already final or rfr - gen
// catdata and queue ads abr

// Revision 1.31 2011/03/24 15:19:51 wendy
// Add CATDATA support and FEATINDC

// Revision 1.17 2010/03/18 12:14:18 wendy
// BH FS ABR Data Quality 20100313.doc updates

// Revision 1.16 2010/02/22 16:45:33 wendy
// fix cvs revision and remove all references to svcprodstruct

// Revision 1.9 2009/12/07 20:00:59 wendy
// Updated for spec chg BH FS ABR Data Qualtity 20091207.xls

// Revision 1.8 2009/11/04 15:08:07 wendy
// BH Configurable Services - spec chgs

// Revision 1.7 2009/09/09 16:45:08 wendy
// Check avails by (xx)prodstruct instead of as a group

// Revision 1.6 2009/09/03 22:59:58 wendy
// add more debug output

// Revision 1.5 2009/09/01 16:34:27 wendy
// fix SVCPRODSTRUCT typo for HW model

// Revision 1.4 2009/08/17 15:20:36 wendy
// Added headings

// Revision 1.3 2009/08/15 01:41:50 wendy
// SR10, 11, 12, 15, 17 BH updates phase 4

// Revision 1.2 2009/08/06 22:24:31 wendy
// SR10, 11, 12, 15, 17 BH updates phase 3

// Revision 1.1 2009/08/02 19:08:10 wendy
// SR10, 11, 12, 15, 17 BH updates phase 2

public class MODELABRSTATUS extends DQABRSTATUS {
	private Vector mdlPlaAvailVctA = null; // AVAIL A MODELAVAIL-d AVAILTYPE =
											// "Planned Availability"
	// private Vector mdlMesPlaAvailVctA = null; //AVAIL A MODELAVAIL-d
	// AVAILTYPE = "MES Planned Availability"
	private Hashtable mdlPlaAvailCtryTblA = null;
	// private Hashtable mdlMesPlaAvailCtryTblA = null;
	private Hashtable mdlLOAvailCtryTblC = null;
	// private Hashtable mdlMesLOAvailCtryTblC = null;
	private Vector mdlFOAvailVctB = null; // AVAIL B MODELAVAIL-d AVAILTYPE =
											// "First Order"
	private Vector mdlLOAvailVctC = null; // AVAIL C MODELAVAIL-d AVAILTYPE =
											// "Last Order"
	// private Vector mdlMesLOAvailVctC = null; //AVAIL C MODELAVAIL-d AVAILTYPE
	// = "MES Last Order"
	private Vector mdlEOMAvailVctP = null; // AVAIL P MODELAVAIL-d AVAILTYPE =
											// "End of Marketing" (200)
	private Vector mdlEOSAvailVctR = null; // AVAIL R MODELAVAIL-d AVAILTYPE =
											// "End of Service" (151)
	private Vector psPlaAvailVctD = null; // AVAIL D PRODSTRUCT: OOFAVAIL-d:
											// AVAIL AVAIL AVAILTYPE = "Planned
											// Availability"
	private Vector psMesPlaAvailVctD = null; // AVAIL D PRODSTRUCT: OOFAVAIL-d:
												// AVAIL AVAIL AVAILTYPE = "MES
												// Planned Availability"
	private Vector psLOAvailVctE = null;// AVAIL E PRODSTRUCT: OOFAVAIL-d: AVAIL
										// AVAILTYPE = "Last Order"
	private Vector psMesLOAvailVctE = null;// AVAIL E PRODSTRUCT: OOFAVAIL-d:
											// AVAIL AVAILTYPE = "MES Last
											// Order"

	private Vector swpsPlaAvailVctH = null;// AVAIL H SWPRODSTRUCT:
											// SWPRODSTRUCTAVAIL-d: AVAIL
											// AVAILTYPE = "Planned
											// Availability"
	private Vector swpsMesPlaAvailVctH = null;// AVAIL H SWPRODSTRUCT:
												// SWPRODSTRUCTAVAIL-d: AVAIL
												// AVAILTYPE = "MES Planned
												// Availability"
	private Vector swpsLOAvailVctK = null;// AVAIL K SWPRODSTRUCT:
											// SWPRODSTRUCTAVAIL-d: AVAIL
											// AVAILTYPE = "Last Order"
	private Vector swpsMesLOAvailVctK = null;// AVAIL K SWPRODSTRUCT:
												// SWPRODSTRUCTAVAIL-d: AVAIL
												// AVAILTYPE = "MES Last Order"

	// FEATINDC FEATN No
	// FEATINDC FEATY Yes
	private static final String FEATINDC_No = "FEATN";

	private static final String ORDERCODE_INITIAL = "5957"; // ORDERCODE 5957 I
															// Initial
	// WARRSVCCOVR WSVC01 No Warranty
	// WARRSVCCOVR WSVC02 Warranty

	private static final String WARRSVCCOVR_NOWARR = "WSVC01";
	private static final Hashtable ATTR_OF_INTEREST_TBL;
	private static final String reg = "^\\d+$";
	private static Pattern pattern = Pattern.compile(reg);
	private static Set set = new HashSet();

	static {
		set.add("MSWH");
		set.add("MAIN");
		set.add("CABL");
		set.add("PMG");
		set.add("MANL");
		set.add("MATM");
		set.add("PLA");
		set.add("ALP");
		set.add("CSW");
		set.add("EDUC");
		set.add("MNPM");
		set.add("***");
	}
	static {
		ATTR_OF_INTEREST_TBL = new Hashtable();
		ATTR_OF_INTEREST_TBL.put("MODELWARR", new String[] { "EFFECTIVEDATE", "ENDDATE", "COUNTRYLIST", "DEFWARR" });
	}

	private boolean IMGUniqueCoverageChkDone = false;
	private EntityList lseoList = null;

	public void dereference() {
		super.dereference();
		if (lseoList != null) {
			lseoList.dereference();
			lseoList = null;
		}

		if (mdlPlaAvailVctA != null) {
			mdlPlaAvailVctA.clear();
			mdlPlaAvailVctA = null;
		}
		if (mdlPlaAvailCtryTblA != null) {
			mdlPlaAvailCtryTblA.clear();
			mdlPlaAvailCtryTblA = null;
		}
		if (mdlFOAvailVctB != null) {
			mdlFOAvailVctB.clear();
			mdlFOAvailVctB = null;
		}
		if (mdlLOAvailVctC != null) {
			mdlLOAvailVctC.clear();
			mdlLOAvailVctC = null;
		}

		if (mdlEOMAvailVctP != null) {
			mdlEOMAvailVctP.clear();
			mdlEOMAvailVctP = null;
		}
		if (mdlEOSAvailVctR != null) {
			mdlEOSAvailVctR.clear();
			mdlEOSAvailVctR = null;
		}

		if (mdlLOAvailCtryTblC != null) {
			mdlLOAvailCtryTblC.clear();
			mdlLOAvailCtryTblC = null;
		}
		if (swpsPlaAvailVctH != null) {
			swpsPlaAvailVctH.clear();
			swpsPlaAvailVctH = null;
		}
		if (swpsLOAvailVctK != null) {
			swpsLOAvailVctK.clear();
			swpsLOAvailVctK = null;
		}

		if (psPlaAvailVctD != null) {
			psPlaAvailVctD.clear();
			psPlaAvailVctD = null;
		}
		if (psLOAvailVctE != null) {
			psLOAvailVctE.clear();
			psLOAvailVctE = null;
		}

	}

	/**********************************
	 * needs VE for all except already final
	 */
	protected boolean isVEneeded(String statusFlag) {
		return true;
	}

	/**
	 * allow derived classes a way to override the VE used when status and dq
	 * are the same and checks are not needed, but some structure may be needed
	 * 
	 * @param statusFlag
	 * @param dataQualityFlag
	 * @return
	 */
	protected String getVEName(String statusFlag, String dataQualityFlag) {
		if (statusFlag.equals(STATUS_FINAL)) {
			addDebug("Status already final, use diff ve");
			return "EXRPT3MODEL2";
		} else if (statusFlag.equals(STATUS_R4REVIEW) && dataQualityFlag.equals(DQ_R4REVIEW)) {
			addDebug("Status already rfr, use diff ve");
			return "EXRPT3MODEL2";
		}
		return m_abri.getVEName();
	}

	/**********************************
	 * complete abr processing when status is already final; (dq was final too)
	 * E. Status is Final
	 * 
	 * When this ABR is invoked and STATUS = DATAQUALITY = ï¿½Finalï¿½,
	 * then checking is not required. CATDATA derivation is required. If the
	 * generation fails, the DQ ABR will fail. The DQ ABR will process data for
	 * the selected offering and only utilizes CATADATA rules that have a Life
	 * Cycle of Production. If the generation of CATDATA is successful, then
	 * only the setting of ADSABRSTATUS is processed. This includes the
	 * necessary conditions and only column N (Final) applies.
	 * 
	 */
	protected void doAlreadyFinalProcessing(EntityItem rootEntity) throws Exception {
		// update darules and if there are changes queue ADSABRSTATUS
		if (doDARULEs()) {
			boolean chgsMade = updateDerivedData(rootEntity);
			addDebug("doAlreadyFinalProcessing: " + rootEntity.getKey() + " chgsMade " + chgsMade);
			if (chgsMade) {
				boolean oldData = this.isOldData(rootEntity, "ANNDATE");
				boolean inxcclist = domainInRuleList(rootEntity, "XCC_LIST");
				addDebug("doAlreadyFinalProcessing: " + rootEntity.getKey() + " domain in XCCLIST " + inxcclist
						+ " olddata " + oldData);
				// 135.40 IF MODEL ANNDATE > "2010-03-01"
				// Delete 2011-10-20 135.42 OR MODEL PDHDOMAIN IN XCC_LIST
				if (!oldData)// || inxcclist)
				{
					// 136.21 IF MODEL STATUS = "Final" (0020)
					// 136.22 AND MODELAVAIL-d: AVAIL STATUS = "Final" (0020)
					// 136.24 IF AVAIL AVAILANNTYPE = "RFA" (RFA)
					// 137.00 IF MODELAVAIL-d: AVAILANNA ANNOUNCEMENT STATUS =
					// "Final" (0020)
					// 139.04 SET MODEL ADSABRSTATUS &ADSFEED
					// 139.05 END 137.00
					// 139.06 ELSE 136.24
					// 139.08 SET MODEL ADSABRSTATUS &ADSFEED
					// 140.00 END 136.24
					// 140.01 END 136.21
					doAlreadyFinalADSProcessing(rootEntity);
				} else {
					// 140.02 ELSE 135.40
					// 140.04 SET MODEL ADSABRSTATUS &ADSFEEDRFR &ADSFEED
					setFlagValue(m_elist.getProfile(), "ADSABRSTATUS",
							getQueuedValueForItem(rootEntity, "ADSABRSTATUS"), rootEntity);
					// 140.06 END 135.40

					setFlagValue(m_elist.getProfile(), "RFCABRSTATUS",
							getQueuedValueForItem(rootEntity, "RFCABRSTATUS"), rootEntity);
				}
			} else {
				// NO_CHGSFOUND = No {0} changes found..
				args[0] = m_elist.getEntityGroup("CATDATA").getLongDescription();
				addResourceMsg("NO_CHGSFOUND", args);
			}
		}
	}

	/**
	 * 
	 * 136.21 IF MODEL STATUS = "Final" (0020) 136.22 AND MODELAVAIL-d: AVAIL
	 * STATUS = "Final" (0020) 136.24 IF AVAIL AVAILANNTYPE = "RFA" (RFA) 137.00
	 * IF MODELAVAIL-d: AVAILANNA ANNOUNCEMENT STATUS = "Final" (0020) 139.04
	 * SET MODEL ADSABRSTATUS &ADSFEED 139.05 END 137.00 139.06 ELSE 136.24
	 * 139.08 SET MODEL ADSABRSTATUS &ADSFEED 140.00 END 136.24 140.01 END
	 * 136.21
	 * 
	 * @param mdlItem
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareRequestException
	 */
	private void doAlreadyFinalADSProcessing(EntityItem mdlItem)
			throws MiddlewareRequestException, MiddlewareException, SQLException {
		Vector availVct = PokUtils.getAllLinkedEntities(mdlItem, "MODELAVAIL", "AVAIL");
		availloop: for (int ai = 0; ai < availVct.size(); ai++) {
			EntityItem avail = (EntityItem) availVct.elementAt(ai);
			String availAnntypeFlag = PokUtils.getAttributeFlagValue(avail, "AVAILANNTYPE");
			if (availAnntypeFlag == null) {
				availAnntypeFlag = AVAILANNTYPE_RFA; // if not set, default to
														// RFA
			}

			addDebug("doAlreadyFinalADSProcessing: " + avail.getKey() + " availAnntypeFlag " + availAnntypeFlag);
			// 136.22 AND MODELAVAIL-d: AVAIL STATUS = "Final" (0020)
			if (statusIsFinal(avail)) {
				// 136.24 IF AVAIL AVAILANNTYPE = "RFA" (RFA)
				if (AVAILANNTYPE_RFA.equals(availAnntypeFlag)) {
					Vector annVct = PokUtils.getAllLinkedEntities(avail, "AVAILANNA", "ANNOUNCEMENT");
					for (int i = 0; i < annVct.size(); i++) {
						EntityItem annItem = (EntityItem) annVct.elementAt(i);
						String anntype = getAttributeFlagEnabledValue(annItem, "ANNTYPE");
						addDebug("doAlreadyFinalADSProcessing: " + annItem.getKey() + " type " + anntype);
						// 137.00 IF MODELAVAIL-d: AVAILANNA ANNOUNCEMENT STATUS
						// = "Final" (0020)
						if (statusIsFinal(annItem, "ANNSTATUS")) {
							// 139.04 SET MODEL ADSABRSTATUS &ADSFEED
							setSinceFirstFinal(mdlItem, "ADSABRSTATUS");
							// setFlagValue(m_elist.getProfile(),"ADSABRSTATUS",
							// getQueuedValueForItem(mdlItem,"ADSABRSTATUS"),mdlItem);
							break availloop;
						}
					} // end ann loop
					annVct.clear();
					// 139.05 END 137.00
				}
				// else{ // not RFA
				// 139.06 ELSE 136.24
				// 139.08 SET MODEL ADSABRSTATUS &ADSFEED
				setSinceFirstFinal(mdlItem, "ADSABRSTATUS");
				// setFlagValue(m_elist.getProfile(),"ADSABRSTATUS",
				// getQueuedValueForItem(mdlItem,"ADSABRSTATUS"),mdlItem);
				break availloop;
				// 140.00 END 136.24
				// } // end not RFA
			} // end avail.status=final
		} // end avail loop
		availVct.clear();
		// 140.01 END 136.21
	}

	/**********************************
	 * complete abr processing when status is already rfr; (dq was rfr too) D.
	 * Status is Ready for Review
	 * 
	 * When this ABR is invoked and STATUS = DATAQUALITY = ï¿½Ready for
	 * Reviewï¿½, then checking is not required. CATDATA derivation is
	 * required. If the generation fails, the DQ ABR will fail. The DQ ABR will
	 * process data for the selected offering and only utilizes CATADATA rules
	 * that have a Life Cycle of Production. If the generation of CATDATA is
	 * successful, then only the setting of ADSABRSTATUS is processed. This
	 * includes the necessary conditions and only column M (Ready for Review)
	 * applies.
	 * 
	 */
	protected void doAlreadyRFRProcessing(EntityItem rootEntity) throws Exception {
		// update darules and if there are changes queue ADSABRSTATUS
		if (doDARULEs()) {
			String lifecycle = PokUtils.getAttributeFlagValue(rootEntity, "LIFECYCLE");
			boolean oldData = this.isOldData(rootEntity, "ANNDATE");
			boolean inxcclist = domainInRuleList(rootEntity, "XCC_LIST");
			addDebug("doAlreadyRFRProcessing: " + rootEntity.getKey() + " domain in XCCLIST " + inxcclist + " olddata "
					+ oldData + " lifecycle " + lifecycle);

			if (lifecycle == null || lifecycle.length() == 0) {
				lifecycle = LIFECYCLE_Plan;
			}
			boolean chgsMade = false;
			if (oldData || !inxcclist) { // olddata or !inxcclist do not have
											// lifecycle check
				chgsMade = updateDerivedData(rootEntity);
			} else if (LIFECYCLE_Plan.equals(lifecycle) || // first time moving
															// to RFR
					LIFECYCLE_Develop.equals(lifecycle)) // been RFR before
			{
				chgsMade = updateDerivedData(rootEntity);
			}

			addDebug("doAlreadyRFRProcessing: " + rootEntity.getKey() + " chgsMade " + chgsMade);
			if (chgsMade) {
				// 135.40 IF MODEL ANNDATE > "2010-03-01"
				// Delete 2011-10-20 135.42 OR MODEL PDHDOMAIN IN XCC_LIST
				if (!oldData)// || inxcclist)
				{
					// 136.02 IF MODEL STATUS = "Ready for Review" (0040)
					// 136.04 AND MODEL LIFECYCLE = "Develop" (LF02) | "Plan"
					// (LF01)
					// 136.06 AND MODELAVAIL-d: AVAILANNA AVAIL STATUS = "Final"
					// (0020) | "Ready for Review" (0040)
					// 136.08 IF AVAIL AVAILANNTYPE = "RFA" (RFA)
					// 136.10 IF ANNOUNCEMENT STATUS = "Final" (0020) | "Ready
					// for Review" (0040)
					// 136.12 SET MODEL ADSABRSTATUS &ADSFEEDRFR
					// 136.13 END 136.10
					// 136.14 ELSE 136.08
					// 136.16 SET MODEL ADSABRSTATUS &ADSFEEDRFR
					// 136.18 END 136.08
					// 136.20 END 136.02
					doAlreadyRFRADSProcessing(rootEntity);
				} else {
					// 140.02 R1.0 ELSE 135.40
					// 140.04 R1.0 SET MODEL ADSABRSTATUS &ADSFEEDRFR &ADSFEED
					setFlagValue(m_elist.getProfile(), "ADSABRSTATUS",
							getRFRQueuedValueForItem(rootEntity, "ADSABRSTATUS"), rootEntity);
				}
				// 140.06 R1.0 END 135.40
				// 141.00 END 132.00 MODEL
			} else {
				// NO_CHGSFOUND = No {0} changes found..
				args[0] = m_elist.getEntityGroup("CATDATA").getLongDescription();
				addResourceMsg("NO_CHGSFOUND", args);
			}
		}
	}

	/**
	 * 
	 * 136.02 IF MODEL STATUS = "Ready for Review" (0040) 136.04 AND MODEL
	 * LIFECYCLE = "Develop" (LF02) | "Plan" (LF01) 136.06 AND MODELAVAIL-d:
	 * AVAILANNA AVAIL STATUS = "Final" (0020) | "Ready for Review" (0040)
	 * 136.08 IF AVAIL AVAILANNTYPE = "RFA" (RFA) 136.10 IF ANNOUNCEMENT STATUS
	 * = "Final" (0020) | "Ready for Review" (0040) 136.12 SET MODEL
	 * ADSABRSTATUS &ADSFEEDRFR 136.13 END 136.10 136.14 ELSE 136.08 136.16 SET
	 * MODEL ADSABRSTATUS &ADSFEEDRFR 136.18 END 136.08 136.20 END 136.02
	 * 
	 * @param rootItem
	 */
	private void doAlreadyRFRADSProcessing(EntityItem rootItem) {
		// lifecycle already checked before calling this
		Vector availVct = PokUtils.getAllLinkedEntities(rootItem, "MODELAVAIL", "AVAIL");
		availloop: for (int ai = 0; ai < availVct.size(); ai++) {
			EntityItem avail = (EntityItem) availVct.elementAt(ai);
			String availAnntypeFlag = PokUtils.getAttributeFlagValue(avail, "AVAILANNTYPE");
			if (availAnntypeFlag == null) {
				availAnntypeFlag = AVAILANNTYPE_RFA; // if not set, default to
														// RFA
			}
			addDebug("doAlreadyRFRADSProcessing: " + avail.getKey() + " availAnntypeFlag " + availAnntypeFlag);
			// 136.06 AND MODELAVAIL-d: AVAIL STATUS = "Final" (0020) | "Ready
			// for Review" (0040)
			if (statusIsRFRorFinal(avail)) {
				// 136.08 IF AVAIL AVAILANNTYPE = "RFA" (RFA)
				// if (AVAILANNTYPE_RFA.equals(availAnntypeFlag)){
				// Vector annVct= PokUtils.getAllLinkedEntities(avail,
				// "AVAILANNA", "ANNOUNCEMENT");
				// for (int i=0; i<annVct.size(); i++){
				// EntityItem annItem = (EntityItem)annVct.elementAt(i);
				// addDebug("doAlreadyRFRADSProcessing: "+annItem.getKey());
				// // 136.10 IF ANNOUNCEMENT STATUS = "Final" (0020) | "Ready
				// for Review" (0040)
				// if(statusIsRFRorFinal(annItem, "ANNSTATUS")){
				// // 136.12 SET MODEL ADSABRSTATUS &ADSFEEDRFR
				// setFlagValue(m_elist.getProfile(),"ADSABRSTATUS",
				// getRFRQueuedValueForItem(rootItem,"ADSABRSTATUS"),rootItem);
				// addDebug("doAlreadyRFRADSProcessing: RFA done RFR or Final
				// ann "+rootItem.getKey()+" ADSABRSTATUS is queued");
				// annVct.clear();
				// break availloop;
				// }
				// }// end ann loop
				// annVct.clear();
				// //136.13 END 136.10
				// }else{
				// avail was not RFA
				// 136.14 ELSE 136.08
				// 136.16 SET MODEL ADSABRSTATUS &ADSFEEDRFR
				doRFR_MODADSXML(rootItem);
				// 136.18 END 136.08
				addDebug("doAlreadyRFRADSProcessing: not RFA " + rootItem.getKey() + " ADSABRSTATUS is queued");
				break availloop;
				// 136.20 END 136.02
				// }
			}
		} // end avail loop
		availVct.clear();
	}

	/*
	 * 
	 * from sets ss 132.000 MODEL Root Entity 132.200 R1.0 MODEL COMPATGENABR
	 * &COMPATGENRFR &COMPATGEN 132.400 IF MODEL PDHDOMAIN IN XCC_LIST delete
	 * 133.000 R1.0 MODEL COMPATGENABR &COMPATGENRFR &COMPATGEN Delete
	 * 2011-10-20 134.00 SET MODEL EPIMSABRSTATUS &ABRWAITODS 135.00 Perform
	 * MODEL SAPASSORTMODULEPRIOR SetPriorAssortModule 135.20 Perform WWSEO OS
	 * PropagateOStoWWSEO PropagateOStoWWSEO
	 * 
	 * 135.21 IF MODELWWSEO-d WWSEO STATUS = "Final" (0020) 135.22 AND
	 * WWSEOLSEO-d LSEO STATUS = "Final" (0020) 135.23 Perform LSEO
	 * PropagateModelWarr 135.24 ELSE 135.21 135.25 IF LSEO STATUS =
	 * "Ready for Review" (0040) 135.26 LSEO LIFECYCLE = "Develop" (LF02) |
	 * "Plan" (LF01) 135.27 AND WWSEO STATUS = "Final" (0020) |
	 * "Ready for Review" (0040) 135.28 Perform LSEO PropagateModelWarr 135.29
	 * END 135.21
	 * 
	 * 135.30 END 132.40 135.40 IF MODEL ANNDATE > "2010-03-01" Delete
	 * 2011-10-20 135.42 OR MODEL PDHDOMAIN IN XCC_LIST 136.02 IF MODEL STATUS =
	 * "Ready for Review" (0040) 136.04 AND MODEL LIFECYCLE = "Develop" (LF02) |
	 * "Plan" (LF01) 136.06 AND MODELAVAIL-d: AVAILANNA AVAIL STATUS = "Final"
	 * (0020) | "Ready for Review" (0040) 136.08 IF AVAIL AVAILANNTYPE = "RFA"
	 * (RFA) 136.10 IF ANNOUNCEMENT STATUS = "Final" (0020) | "Ready for Review"
	 * (0040) 136.12 SET MODEL ADSABRSTATUS &ADSFEEDRFR 136.13 END 136.10 136.14
	 * ELSE 136.08 136.16 SET MODEL ADSABRSTATUS &ADSFEEDRFR 136.18 END 136.08
	 * 136.20 END 136.02 136.21 IF MODEL STATUS = "Final" (0020) 136.22 AND
	 * MODELAVAIL-d: AVAIL STATUS = "Final" (0020) 136.24 IF AVAIL AVAILANNTYPE
	 * = "RFA" (RFA) 137.00 IF MODELAVAIL-d: AVAILANNA ANNOUNCEMENT STATUS =
	 * "Final" (0020) 138.00 IF ANNOUNCEMENT ANNTYPE = "New" (19) Add 2011-10-20
	 * 138.200 R1.0 AND ANNOUNCEMENT PDHDOMAIN IN XCC_LIST 139.00 SET
	 * ANNOUNCEMENT WWPRTABRSTATUS &ABRWAITODS2 139.02 END 138.00 139.04 SET
	 * MODEL ADSABRSTATUS &ADSFEED 139.05 END 137.00 139.06 ELSE 136.24 139.08
	 * SET MODEL ADSABRSTATUS &ADSFEED 140.00 END 136.24 140.01 END 136.21
	 * 140.02 ELSE 135.40 140.04 SET MODEL ADSABRSTATUS &ADSFEEDRFR &ADSFEED
	 * 140.06 END 135.40 141.00 END 132.00 MODEL
	 */
	/**********************************
	 * complete abr processing after status moved to readyForReview; (status was
	 * chgreq) D. STATUS changed to Ready for Review 132.000 MODEL Root Entity
	 * 132.200 R1.0 MODEL COMPATGENABR &COMPATGENRFR &COMPATGEN 132.400 IF MODEL
	 * PDHDOMAIN IN XCC_LIST delete 133.000 R1.0 MODEL COMPATGENABR
	 * &COMPATGENRFR &COMPATGEN 135.20 Perform WWSEO OS PropagateOStoWWSEO
	 * PropagateOStoWWSEO
	 * 
	 * 135.21 IF MODELWWSEO-d WWSEO STATUS = "Final" (0020) 135.22 AND
	 * WWSEOLSEO-d LSEO STATUS = "Final" (0020) 135.23 Perform LSEO
	 * PropagateModelWarr 135.24 ELSE 135.21 135.25 IF LSEO STATUS = "Ready for
	 * Review" (0040) 135.26 LSEO LIFECYCLE = "Develop" (LF02) | "Plan" (LF01)
	 * 135.27 AND WWSEO STATUS = "Final" (0020) | "Ready for Review" (0040)
	 * 135.28 Perform LSEO PropagateModelWarr 135.29 END 135.21
	 * 
	 * 135.30 END 132.40 135.40 IF MODEL ANNDATE > "2010-03-01" Delete
	 * 2011-10-20 135.42 OR MODEL PDHDOMAIN IN XCC_LIST 136.02 IF MODEL STATUS =
	 * "Ready for Review" (0040) 136.04 AND MODEL LIFECYCLE = "Develop" (LF02) |
	 * "Plan" (LF01) 136.06 AND MODELAVAIL-d: AVAILANNA AVAIL STATUS = "Final"
	 * (0020) | "Ready for Review" (0040) 136.08 IF AVAIL AVAILANNTYPE = "RFA"
	 * (RFA) 136.10 IF ANNOUNCEMENT STATUS = "Final" (0020) | "Ready for Review"
	 * (0040) 136.12 SET MODEL ADSABRSTATUS &ADSFEEDRFR 136.13 END 136.10 136.14
	 * ELSE 136.08 136.16 SET MODEL ADSABRSTATUS &ADSFEEDRFR 136.18 END 136.08
	 * 136.20 END 136.02 Final processing... 140.02 ELSE 135.40 140.04 SET MODEL
	 * ADSABRSTATUS &ADSFEEDRFR &ADSFEED 140.06 END 135.40 141.00 END 132.00
	 * MODEL
	 * 
	 */
	protected void completeNowR4RProcessing() throws java.sql.SQLException,
			COM.ibm.opicmpdh.middleware.MiddlewareException, COM.ibm.opicmpdh.middleware.MiddlewareRequestException {
		EntityItem mdlItem = m_elist.getParentEntityGroup().getEntityItem(0);

		boolean oldData = this.isOldData(mdlItem, "ANNDATE");
		boolean inxcclist = domainInRuleList(mdlItem, "XCC_LIST");
		addDebug("nowRFR: " + mdlItem.getKey() + " domain in XCCLIST " + inxcclist + " olddata " + oldData);
		// 132.00 MODEL Root Entity
		// 132.20 R1.0 SET MODEL COMPATGENABR &COMPATGENRFR &COMPATGEN
		setFlagValue(m_elist.getProfile(), "COMPATGENABR", getRFRQueuedValue("COMPATGENABR"));

		// 132.40 IF MODEL PDHDOMAIN IN XCC_LIST
		if (inxcclist) {
			// 135.20 Perform WWSEO OS PropagateOStoWWSEO PropagateOStoWWSEO
			// 2013-04-26 Delete based on SETs
			// propagateOStoWWSEO(mdlItem,m_elist.getEntityGroup("WWSEO"));

			// 135.24 ELSE 135.21
			// 135.25 IF LSEO STATUS = "Ready for Review" (0040)
			// 135.26 LSEO LIFECYCLE = "Develop" (LF02) | "Plan" (LF01)
			// 135.27 AND WWSEO STATUS = "Final" (0020) | "Ready for Review"
			// (0040)
			// 135.28 Perform LSEO PropagateModelWarr
			EntityGroup wwseoGrp = m_elist.getEntityGroup("WWSEO");
			if (wwseoGrp.getEntityItemCount() > 0) {
				Vector validWWSEOVct = new Vector();
				for (int i = 0; i < wwseoGrp.getEntityItemCount(); i++) {
					EntityItem wwseo = wwseoGrp.getEntityItem(i);
					if (statusIsRFRorFinal(wwseo)) {
						validWWSEOVct.add(wwseo);
					}
				}
				if (validWWSEOVct.size() > 0) {
					propagateModelWarr(mdlItem, validWWSEOVct, false);
					validWWSEOVct.clear();
				}
			}
			// 135.29 END 135.21
		}
		// 135.30 END 132.40

		// 135.40 IF MODEL ANNDATE > "2010-03-01"
		// Delete 2011-10-20 135.42 OR MODEL PDHDOMAIN IN XCC_LIST
		if (!oldData)// || inxcclist)
		{
			// 136.020 IF MODEL STATUS = "Ready for Review" (0040)
			// 20130904 Change Column E & J & O 136.040 IF MODEL LIFECYCLE <>
			// "Develop" (LF02) | "Plan" (LF01) if true, then was once Final
			// 136.060 AND MODELAVAIL-d: AVAILANNA AVAIL STATUS = "Final" (0020)
			// | "Ready for Review" (0040)
			// 20130904 Add 136.062 SET MODEL ADSABRSTATUS &ADSFEEDRFR Was once
			// Final
			// 20130904 Add 136.064 ELSE 136.040
			// 20130904 Change Columns I, J, K 136.160 SET MODEL ADSABRSTATUS =
			// "Passed" (0030) | "Passed Resend RFR" (XMLRFR) ADSABRSTATUS
			// &ADSFEEDRFR XML was sent
			// 20130904 Add 136.162 SET MODEL ADSABRSTATUS <> "Passed" (0030) |
			// "Passed Resend RFR" (XMLRFR) ADSABRSTATUS &ADSFEEDRFRFIRST XML
			// was never sent
			// 20130904 Add 136.164 END 136.040
			// 136.200 END 136.020

			doR4R_RFAProcessing("MODELAVAIL");
		} else {
			// 140.02 R1.0 ELSE 135.40

			// setFlagValue(m_elist.getProfile(),"ADSABRSTATUS",
			// getRFRQueuedValueForItem(mdlItem,"ADSABRSTATUS"),mdlItem);
			// 20130904b Add 140.030 IF MODEL LIFECYCLE = "Develop" (LF02) |
			// "Plan" (LF01) if true, then was never Final
			// "20130904b Change
			// 20130904 Change" "Columns E, I, J, K, M, N
			// Columns M & O" 140.040 SET MODEL ADSABRSTATUS = "Passed" (0030) |
			// "Passed Resend RFR" (XMLRFR) ADSABRSTATUS &ADSFEEDRFR &ADSFEED
			// RFR - ability to Queue 1st time and then Delay the rest of the
			// times
			// 20130904b Add 140.042 SET MODEL ADSABRSTATUS <> "Passed" (0030) |
			// "Passed Resend RFR" (XMLRFR) ADSABRSTATUS &ADSFEEDRFRFIRST XML
			// was never sent
			// 20130904b Add 140.044 ELSE 140.030 Was Final at least once
			// 20130904b Add 140.046 SetSinceFinal MODEL ADSABRSTATUS = "Passed"
			// (0030) | "Passed Resend RFR" (XMLRFR) ADSABRSTATUS &ADSFEED XML
			// was sent since first Final
			// 20130904b Add 140.048 SetSinceFinal MODEL ADSABRSTATUS <>
			// "Passed" (0030) | "Passed Resend RFR" (XMLRFR) ADSABRSTATUS
			// &ADSFEEDFINALFIRST XML was never sent since first Final
			// 20130904b Add 140.050 END 139.020
			setQueueforOldData(mdlItem);
		}
		// 140.06 R1.0 END 135.40
		// 141.00 END 132.00 MODEL
	}

	/**********************************
	 * complete abr processing after status moved to final; (status was r4r) C.
	 * STATUS changed to Final 132.000 MODEL Root Entity 132.200 R1.0 MODEL
	 * COMPATGENABR &COMPATGENRFR &COMPATGEN 132.400 IF MODEL PDHDOMAIN IN
	 * XCC_LIST delete 133.000 R1.0 MODEL COMPATGENABR &COMPATGENRFR &COMPATGEN
	 * 
	 * Delete 2011-10-20 134.00 SET MODEL EPIMSABRSTATUS &ABRWAITODS 135.00
	 * Perform MODEL SAPASSORTMODULEPRIOR SetPriorAssortModule 135.20 Perform
	 * WWSEO OS PropagateOStoWWSEO PropagateOStoWWSEO 135.21 IF MODELWWSEO-d
	 * WWSEO STATUS = "Final" (0020) 135.22 AND WWSEOLSEO-d LSEO STATUS =
	 * "Final" (0020) 135.23 Perform LSEO PropagateModelWarr 135.30 END 132.40
	 * 135.40 IF MODEL ANNDATE > "2010-03-01" Delete 2011-10-20 135.42 OR MODEL
	 * PDHDOMAIN IN XCC_LIST RFR processing... 136.21 IF MODEL STATUS = "Final"
	 * (0020) 136.22 AND MODELAVAIL-d: AVAIL STATUS = "Final" (0020) 136.24 IF
	 * AVAIL AVAILANNTYPE = "RFA" (RFA) 137.00 IF MODELAVAIL-d: AVAILANNA
	 * ANNOUNCEMENT STATUS = "Final" (0020) 138.00 IF ANNOUNCEMENT ANNTYPE =
	 * "New" (19) Add 2011-10-20 138.200 R1.0 AND ANNOUNCEMENT PDHDOMAIN IN
	 * XCC_LIST 139.00 SET ANNOUNCEMENT WWPRTABRSTATUS &ABRWAITODS2 139.02 END
	 * 138.00 139.04 SET MODEL ADSABRSTATUS &ADSFEED 139.05 END 137.00 139.06
	 * ELSE 136.24 139.08 SET MODEL ADSABRSTATUS &ADSFEED 140.00 END 136.24
	 * 140.01 END 136.21 140.02 ELSE 135.40 140.04 SET MODEL ADSABRSTATUS
	 * &ADSFEEDRFR &ADSFEED 140.06 END 135.40 141.00 END 132.00 MODEL
	 * 
	 */
	protected void completeNowFinalProcessing() throws java.sql.SQLException,
			COM.ibm.opicmpdh.middleware.MiddlewareException, COM.ibm.opicmpdh.middleware.MiddlewareRequestException {
		EntityItem mdlItem = m_elist.getParentEntityGroup().getEntityItem(0);

		boolean oldData = this.isOldData(mdlItem, "ANNDATE");
		boolean inxcclist = domainInRuleList(mdlItem, "XCC_LIST");
		addDebug("nowFinal: " + mdlItem.getKey() + " domain in XCCLIST " + inxcclist + " olddata " + oldData);

		// 132.00 MODEL Root Entity
		// R1.0 132.20 SET MODEL COMPATGENABR &COMPATGENRFR &COMPATGEN
		setFlagValue(m_elist.getProfile(), "COMPATGENABR", getQueuedValue("COMPATGENABR"));
		// 132.40 IF MODEL PDHDOMAIN IN XCC_LIST
		if (inxcclist) {
			// Delete 2011-10-20 134.00 SET MODEL EPIMSABRSTATUS &ABRWAITODS
			// setFlagValue(m_elist.getProfile(),"EPIMSABRSTATUS",
			// getQueuedValue("EPIMSABRSTATUS"));

			// 135.00 Perform MODEL SAPASSORTMODULEPRIOR SetPriorAssortModule
			checkAssortModule();

			// 135.20 Perform WWSEO OS PropagateOStoWWSEO PropagateOStoWWSEO
			// 2013-04-26 Delete based on SETs
			// propagateOStoWWSEO(mdlItem,m_elist.getEntityGroup("WWSEO"));

			// 135.21 IF MODELWWSEO-d WWSEO STATUS = "Final" (0020)
			// 135.22 AND WWSEOLSEO-d LSEO STATUS = "Final" (0020)
			// 135.23 Perform LSEO PropagateModelWarr
			EntityGroup wwseoGrp = m_elist.getEntityGroup("WWSEO");
			if (wwseoGrp.getEntityItemCount() > 0) {
				Vector validWWSEOVct = new Vector();
				for (int i = 0; i < wwseoGrp.getEntityItemCount(); i++) {
					EntityItem wwseo = wwseoGrp.getEntityItem(i);
					if (statusIsFinal(wwseo)) {
						validWWSEOVct.add(wwseo);
					}
				}
				if (validWWSEOVct.size() > 0) {
					propagateModelWarr(mdlItem, validWWSEOVct, true);
					validWWSEOVct.clear();
				}
			}
			// 135.29 END 135.21
		}
		// 135.30 END

		// 135.40 IF MODEL ANNDATE > "2010-03-01"
		// Delete 2011-10-20 135.42 OR MODEL PDHDOMAIN IN XCC_LIST
		if (!oldData)// || inxcclist)
		{
			// 136.21 IF MODEL STATUS = "Final" (0020)
			// 136.22 AND MODELAVAIL-d: AVAIL STATUS = "Final" (0020)
			// 136.24 IF AVAIL AVAILANNTYPE = "RFA" (RFA)
			// 137.00 IF MODELAVAIL-d: AVAILANNA ANNOUNCEMENT STATUS = "Final"
			// (0020)
			// 138.00 IF ANNOUNCEMENT ANNTYPE = "New" (19)
			// Add 2011-10-20 138.200 R1.0 AND ANNOUNCEMENT PDHDOMAIN IN
			// XCC_LIST
			// 139.00 SET ANNOUNCEMENT WWPRTABRSTATUS &ABRWAITODS2
			// 139.02 END 138.00
			// 139.04 SET MODEL ADSABRSTATUS &ADSFEED
			// 139.05 END 137.00
			// 139.06 ELSE 136.24
			// 139.08 SET MODEL ADSABRSTATUS &ADSFEED
			// 140.00 END 136.24
			// 140.01 END 136.21
			doFinalRFAProcessing("MODELAVAIL");
		} else {// 140.020

			// setFlagValue(m_elist.getProfile(),"ADSABRSTATUS",
			// getQueuedValueForItem(mdlItem,"ADSABRSTATUS"),mdlItem);

			// 20130904b Add 140.030 IF MODEL LIFECYCLE = "Develop" (LF02) |
			// "Plan" (LF01) if true, then was never Final
			// "20130904b Change
			// 20130904 Change" "Columns E, I, J, K, M, N
			// Columns M & O" 140.040 SET MODEL ADSABRSTATUS = "Passed" (0030) |
			// "Passed Resend RFR" (XMLRFR) ADSABRSTATUS &ADSFEEDRFR &ADSFEED
			// RFR - ability to Queue 1st time and then Delay the rest of the
			// times
			// 20130904b Add 140.042 SET MODEL ADSABRSTATUS <> "Passed" (0030) |
			// "Passed Resend RFR" (XMLRFR) ADSABRSTATUS &ADSFEEDRFRFIRST XML
			// was never sent
			// 20130904b Add 140.044 ELSE 140.030 Was Final at least once
			// 20130904b Add 140.046 SetSinceFinal MODEL ADSABRSTATUS = "Passed"
			// (0030) | "Passed Resend RFR" (XMLRFR) ADSABRSTATUS &ADSFEED XML
			// was sent since first Final
			// 20130904b Add 140.048 SetSinceFinal MODEL ADSABRSTATUS <>
			// "Passed" (0030) | "Passed Resend RFR" (XMLRFR) ADSABRSTATUS
			// &ADSFEEDFINALFIRST XML was never sent since first Final
			// 20130904b Add 140.050 END 139.020
			setQueueforOldData(mdlItem);

			// 140.046 SetSinceFinal MODEL RFCABRSTATUS = "Passed" (0030)
			// RFCABRSTATUS &OIMSFEED XML was sent since first Final
			// 140.048 SetSinceFinal MODEL RFCABRSTATUS <> "Passed" (0030)
			// RFCABRSTATUS &OIMSFEED XML was never sent since first Final
			// MODEL COFCAT = "Hardware(100)
			// OR
			// MODEL MACHTYPEATR+MODELATR IN 5313-HPO and 5372-IS5
			if (isHardwareOrHIPOModel(mdlItem)) {
				setFlagValue(m_elist.getProfile(), "RFCABRSTATUS", getQueuedValueForItem(mdlItem, "RFCABRSTATUS"),
						mdlItem);
			}
		}
		// 141.00 END 132.00 MODEL
	}

	// 20130904b Add 140.030 IF MODEL LIFECYCLE = "Develop" (LF02) | "Plan"
	// (LF01) if true, then was never Final
	// "20130904b Change
	// 20130904 Change" "Columns E, I, J, K, M, N
	// Columns M & O" 140.040 SET MODEL ADSABRSTATUS = "Passed" (0030) | "Passed
	// Resend RFR" (XMLRFR) ADSABRSTATUS &ADSFEEDRFR &ADSFEED RFR - ability to
	// Queue 1st time and then Delay the rest of the times
	// 20130904b Add 140.042 SET MODEL ADSABRSTATUS <> "Passed" (0030) | "Passed
	// Resend RFR" (XMLRFR) ADSABRSTATUS &ADSFEEDRFRFIRST XML was never sent
	// 20130904b Add 140.044 ELSE 140.030 Was Final at least once
	// 20130904b Add 140.046 SetSinceFinal MODEL ADSABRSTATUS = "Passed" (0030)
	// | "Passed Resend RFR" (XMLRFR) ADSABRSTATUS &ADSFEED XML was sent since
	// first Final
	// 20130904b Add 140.048 SetSinceFinal MODEL ADSABRSTATUS <> "Passed" (0030)
	// | "Passed Resend RFR" (XMLRFR) ADSABRSTATUS &ADSFEEDFINALFIRST XML was
	// never sent since first Final
	// 20130904b Add 140.050 END 139.020

	private void setQueueforOldData(EntityItem item)
			throws MiddlewareRequestException, MiddlewareException, SQLException {
		String lifecycle = PokUtils.getAttributeFlagValue(item, "LIFECYCLE");
		addDebug("setQueueforOldData: " + item.getKey() + " lifecycle " + lifecycle);
		if (lifecycle == null || lifecycle.length() == 0) {
			lifecycle = LIFECYCLE_Plan;
		}

		// IF LIFECYCLE = "Develop" (LF02) | "Plan" (LF01)
		if (LIFECYCLE_Plan.equals(lifecycle) || // first time moving to RFR
				LIFECYCLE_Develop.equals(lifecycle)) {
			setRFRSinceFirstRFR(item);
		} else {
			setSinceFirstFinal(item, "ADSABRSTATUS");
		}
	}

	/**
	 * used when entities are going to final and must check RFA
	 * 
	 * 136.21 IF MODEL STATUS = "Final" (0020) 136.22 AND MODELAVAIL-d: AVAIL
	 * STATUS = "Final" (0020) 136.24 IF AVAIL AVAILANNTYPE = "RFA" (RFA) 137.00
	 * IF MODELAVAIL-d: AVAILANNA ANNOUNCEMENT STATUS = "Final" (0020) 138.00 IF
	 * ANNOUNCEMENT ANNTYPE = "New" (19) Add 2011-10-20 138.200 R1.0 AND
	 * ANNOUNCEMENT PDHDOMAIN IN XCC_LIST 139.00 SET ANNOUNCEMENT WWPRTABRSTATUS
	 * &ABRWAITODS2 139.02 END 138.00 139.04 SET MODEL ADSABRSTATUS &ADSFEED
	 * 139.05 END 137.00 139.06 ELSE 136.24 139.08 SET MODEL ADSABRSTATUS
	 * &ADSFEED 140.00 END 136.24 140.01 END 136.21
	 * 
	 * @param availRel
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareRequestException
	 */
	private void doFinalRFAProcessing(String availRel)
			throws MiddlewareRequestException, MiddlewareException, SQLException {
		EntityItem mdlItem = m_elist.getParentEntityGroup().getEntityItem(0);
		// 136.21 IF MODEL STATUS = "Final" (0020)
		Vector availVct = PokUtils.getAllLinkedEntities(mdlItem, availRel, "AVAIL");

		for (int ai = 0; ai < availVct.size(); ai++) {
			EntityItem avail = (EntityItem) availVct.elementAt(ai);
			String availAnntypeFlag = PokUtils.getAttributeFlagValue(avail, "AVAILANNTYPE");
			if (availAnntypeFlag == null) {
				availAnntypeFlag = AVAILANNTYPE_RFA; // if not set, default to
														// RFA
			}

			addDebug("doFinal_RFAProcessing: " + avail.getKey() + " availAnntypeFlag " + availAnntypeFlag);
			// 136.22 AND MODELAVAIL-d: AVAIL STATUS = "Final" (0020)
			if (statusIsFinal(avail)) {
				boolean mdlPosted = false;
				// 136.24 IF AVAIL AVAILANNTYPE = "RFA" (RFA)
				if (AVAILANNTYPE_RFA.equals(availAnntypeFlag)) {
					Vector annVct = PokUtils.getAllLinkedEntities(avail, "AVAILANNA", "ANNOUNCEMENT");
					for (int i = 0; i < annVct.size(); i++) {
						EntityItem annItem = (EntityItem) annVct.elementAt(i);
						String anntype = getAttributeFlagEnabledValue(annItem, "ANNTYPE");
						addDebug("doFinal_RFAProcessing: " + annItem.getKey() + " type " + anntype);
						// 137.00 IF MODELAVAIL-d: AVAILANNA ANNOUNCEMENT STATUS
						// = "Final" (0020)
						if (statusIsFinal(annItem, "ANNSTATUS")) {
							// 138.00 IF ANNOUNCEMENT ANNTYPE = "New" (19)
							// Add 2011-10-20 138.200 R1.0 AND ANNOUNCEMENT
							// PDHDOMAIN IN XCC_LIST
							if (ANNTYPE_NEW.equals(anntype) && domainInRuleList(annItem, "XCC_LIST")) {
								addDebug(annItem.getKey() + " is Final and New and domain in xcclist");
								// 139.00 SET ANNOUNCEMENT WWPRTABRSTATUS
								// &ABRWAITODS2
								setFlagValue(m_elist.getProfile(), "WWPRTABRSTATUS",
										getQueuedValueForItem(annItem, "WWPRTABRSTATUS"), annItem);
							}
							if (!mdlPosted) {
								mdlPosted = true;
								// 20130904b Change Columns E, I, J, K 139.040
								// SetSinceFinal MODEL ADSABRSTATUS = "Passed"
								// (0030) | "Passed Resend RFR" (XMLRFR)
								// ADSABRSTATUS &ADSFEED
								// 20130904b Add 139.042 SetSinceFinal MODEL
								// ADSABRSTATUS <> "Passed" (0030) | "Passed
								// Resend RFR" (XMLRFR) ADSABRSTATUS
								// &ADSFEEDFINALFIRST
								setSinceFirstFinal(mdlItem, "ADSABRSTATUS");
								// 139.040 SetSinceFinal MODEL RFCABRSTATUS =
								// "Passed" (0030) RFCABRSTATUS &OIMSFEED
								// 139.042 SetSinceFinal MODEL RFCABRSTATUS <>
								// "Passed" (0030) RFCABRSTATUS &OIMSFEED
								// MODEL COFCAT = "Hardware(100)
								// OR
								// MODEL MACHTYPEATR+MODELATR IN 5313-HPO and
								// 5372-IS5
								if (isHardwareOrHIPOModel(mdlItem)) {
									setRFCSinceFirstFinal(mdlItem, "RFCABRSTATUS");
								}
								// setFlagValue(m_elist.getProfile(),"ADSABRSTATUS",
								// getQueuedValueForItem(mdlItem,"ADSABRSTATUS"),mdlItem);

							}
							// 139.05 END 137.00
							// TODO
							if (isQsmANNTYPE(anntype) && isHardwareOrHIPOModel(mdlItem)) {
								setFlagValue(m_elist.getProfile(), "QSMCREFABRSTATUS",
										getQueuedValueForItem(annItem, "QSMCREFABRSTATUS"), annItem);
								setFlagValue(m_elist.getProfile(), "QSMFULLABRSTATUS",
										getQueuedValueForItem(annItem, "QSMFULLABRSTATUS"), annItem);
							}
						}
					} // end ann loop
					annVct.clear();
					// 139.02 END 138.00
				}
				// else{ // bvnot RFA
				// 139.06 ELSE 136.24
				if (!mdlPosted) {
					mdlPosted = true;
					// 20130904b Change Columns E, I, J, K 139.080 SetSinceFinal
					// MODEL ADSABRSTATUS = "Passed" (0030) | "Passed Resend
					// RFR" (XMLRFR) ADSABRSTATUS &ADSFEED
					// 20130904b Add 139.082 SetSinceFinal MODEL ADSABRSTATUS <>
					// "Passed" (0030) | "Passed Resend RFR" (XMLRFR)
					// ADSABRSTATUS &ADSFEEDFINALFIRST
					// setFlagValue(m_elist.getProfile(),"ADSABRSTATUS",
					// getQueuedValueForItem(mdlItem,"ADSABRSTATUS"),mdlItem);
					setSinceFirstFinal(mdlItem, "ADSABRSTATUS");
					// 139.080 SetSinceFinal MODEL RFCABRSTATUS = "Passed"
					// (0030) RFCABRSTATUS &OIMSFEED
					// 139.082 SetSinceFinal MODEL RFCABRSTATUS <> "Passed"
					// (0030) RFCABRSTATUS &OIMSFEED
					// MODEL COFCAT = "Hardware(100)
					// OR
					// MODEL MACHTYPEATR+MODELATR IN 5313-HPO and 5372-IS5
					if (isHardwareOrHIPOModel(mdlItem)) {
						setRFCSinceFirstFinal(mdlItem, "RFCABRSTATUS");
					}
				}

				// 140.00 END 136.24
				// } // end not RFA
			} // end avail.status=final
		} // end avail loop
		availVct.clear();
		// 140.01 END 136.21
	}

	/***************
	 * 
	 * DD. PropagateModelWarr
	 * 
	 * The relator MODELWARR is locked if MODEL STATUS = {Ready for Review |
	 * Final}. Therefore a change to MODELWARR will require MODEL to undergo a
	 * change in STATUS to Change Request and return to Ready for Review and
	 * then Final. Therefore the DQ ABR for MODEL will be queued.
	 * 
	 * The SETS spreadsheet will use this function if the MODEL passes all of
	 * the data quality checks.
	 * 
	 * A second EXTRACT for a small VE will be performed for the prior time that
	 * the DQ ABR Passed. The VE is:
	 * 
	 * MODELWARR:D:0 A change of interest may be any one or more of the
	 * following: ï¿½ MODELWARR (i.e. add or remove a relator of this type)
	 * ï¿½ EFFECTIVEDATE (attribute on MODELWARR) ï¿½ ENDDATE (attribute
	 * on MODELWARR) ï¿½ COUNTRYLIST (attribute on MODELWARR) ï¿½ DEFWARR
	 * (ATTRIBUTE on MODELWARR)
	 * 
	 * If there is a change of interest, then Queue grandchildren LSEOs. They
	 * are all of the LSEOs in the path MODELWWSEO-d: WWSEOLSEO-d starting with
	 * the root MODEL. Only LSEOs where LSEO Unpublish Date - Target
	 * (LSEOUNPUBDATEMTRGT) > NOW() are considered. There will be logic in the
	 * SETS SS that considers the STATUS of the WWSEO and the LSEO.
	 * 
	 * @param mdlitem
	 * @param wwseoitem
	 * @param modelFinal
	 * @throws MiddlewareRequestException
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private void propagateModelWarr(EntityItem mdlitem, Vector validWWSEOVct, boolean modelFinal)
			throws MiddlewareRequestException, SQLException, MiddlewareException {
		EntityItem eai[] = new EntityItem[validWWSEOVct.size()];
		for (int i = 0; i < validWWSEOVct.size(); i++) {
			EntityItem wwseo = (EntityItem) validWWSEOVct.elementAt(i);
			eai[i] = new EntityItem(null, m_prof, wwseo.getEntityType(), wwseo.getEntityID());
		}
		// pull VE to get lseo
		String VeName = "DQVEWWSEOLSEO";
		lseoList = m_db.getEntityList(m_elist.getProfile(),
				new ExtractActionItem(null, m_db, m_elist.getProfile(), VeName), eai);
		addDebug("propagateModelWarr: strnow: " + getCurrentDate() + " Extract " + VeName + NEWLINE
				+ PokUtils.outputList(lseoList));
		// EntityGroup wwseoGrp = lseoList.getParentEntityGroup();

		Vector lseoVct = new Vector(); // hang onto lseo needed propagation
		// for (int i2=0; i2<wwseoGrp.getEntityItemCount(); i2++){ wwseo were
		// already filtered and not needed here
		// EntityItem wwseoitem = wwseoGrp.getEntityItem(i2);
		// addDebug("propagateModelWarr: "+wwseoitem.getKey());
		EntityGroup lseoGrp = lseoList.getEntityGroup("LSEO");
		if (modelFinal) {
			// 135.21 IF MODELWWSEO-d WWSEO STATUS = "Final" (0020)
			// 135.22 AND WWSEOLSEO-d LSEO STATUS = "Final" (0020)
			for (int i = 0; i < lseoGrp.getEntityItemCount(); i++) {
				EntityItem lseo = lseoGrp.getEntityItem(i);
				String unpubdate = PokUtils.getAttributeValue(lseo, "LSEOUNPUBDATEMTRGT", "", FOREVER_DATE, false);
				addDebug("propagateModelWarr: " + lseo.getKey() + " unpubdate " + unpubdate);
				// Only LSEOs where LSEO Unpublish Date - Target
				// (LSEOUNPUBDATEMTRGT) > NOW() or Empty are considered.
				if (statusIsFinal(lseo) && unpubdate.compareTo(getCurrentDate()) > 0) {
					// 135.23 Perform LSEO PropagateModelWarr
					lseoVct.add(lseo);
				}
			}
		} else {
			// 135.24 ELSE 135.21
			// 135.25 IF LSEO STATUS = "Ready for Review" (0040)
			// 20130904 Delete 135.26 LSEO LIFECYCLE = "Develop" (LF02) | "Plan"
			// (LF01)
			// 135.27 AND WWSEO STATUS = "Final" (0020) | "Ready for Review"
			// (0040)
			for (int i = 0; i < lseoGrp.getEntityItemCount(); i++) {
				EntityItem lseo = lseoGrp.getEntityItem(i);
				String unpubdate = PokUtils.getAttributeValue(lseo, "LSEOUNPUBDATEMTRGT", "", FOREVER_DATE, false);
				addDebug("propagateModelWarr: " + lseo.getKey() + " unpubdate " + unpubdate);
				// Only LSEOs where LSEO Unpublish Date - Target
				// (LSEOUNPUBDATEMTRGT) > NOW() or Empty are considered.
				if (statusIsRFR(lseo) && unpubdate.compareTo(getCurrentDate()) > 0) {
					// String lifecycle = PokUtils.getAttributeFlagValue(lseo,
					// "LIFECYCLE");
					// addDebug("propagateModelWarr: "+lseo.getKey()+" lifecycle
					// "+lifecycle);
					// if (lifecycle==null || lifecycle.length()==0){
					// lifecycle = LIFECYCLE_Plan;
					// }
					// // IF LIFECYCLE = "Develop" (LF02) | "Plan" (LF01)
					// if (LIFECYCLE_Plan.equals(lifecycle) || // first time
					// moving to RFR
					// LIFECYCLE_Develop.equals(lifecycle)){ // been RFR before
					// 135.28 Perform LSEO PropagateModelWarr
					lseoVct.add(lseo);
					// }
				}
			}
		}
		// }
		// 135.29 END 135.21
		if (lseoVct.size() > 0) {
			propagateModelWarrLseo(mdlitem, lseoVct, modelFinal);
			lseoVct.clear();
		}
	}

	/**
	 *
	 * A second EXTRACT for a small VE will be performed for the prior time that
	 * the DQ ABR Passed. The VE is:
	 * 
	 * MODELWARR:D:0 A change of interest may be any one or more of the
	 * following: ï¿½ MODELWARR (i.e. add or remove a relator of this type)
	 * ï¿½ EFFECTIVEDATE (attribute on MODELWARR) ï¿½ ENDDATE (attribute
	 * on MODELWARR) ï¿½ COUNTRYLIST (attribute on MODELWARR) ï¿½ DEFWARR
	 * (ATTRIBUTE on MODELWARR)
	 * 
	 * If there is a change of interest, then Queue grandchildren LSEOs.
	 * 
	 * @param mdlitem
	 * @param lseoVct
	 * @param queueFinal
	 * @throws MiddlewareRequestException
	 * @throws MiddlewareException
	 * @throws SQLException
	 */
	private void propagateModelWarrLseo(EntityItem mdlitem, Vector lseoVct, boolean queueFinal)
			throws MiddlewareRequestException, MiddlewareException, SQLException {
		boolean hadchgs = false;
		String passedDTS = getPrevPassedDTS(mdlitem, "MODELABRSTATUS");
		if (passedDTS != null) {
			// only changes since moving to rfr or final are considered.. so
			// first time is ignored
			hadchgs = changeOfInterest(mdlitem, passedDTS, m_elist.getProfile().getValOn(), "DQVEMODELWARR",
					ATTR_OF_INTEREST_TBL);
		}
		if (hadchgs) {
			for (int i = 0; i < lseoVct.size(); i++) {
				EntityItem lseoItem = (EntityItem) lseoVct.elementAt(i);
				if (queueFinal) {
					setFlagValue(m_elist.getProfile(), "ADSABRSTATUS", getQueuedValueForItem(lseoItem, "ADSABRSTATUS"),
							lseoItem);
				} else {
					setFlagValue(m_elist.getProfile(), "ADSABRSTATUS",
							getRFRQueuedValueForItem(lseoItem, "ADSABRSTATUS"), lseoItem);
				}
			}
		}
	}

	/**********************************
	 * generate string representation of attributes in the list for this entity
	 * 
	 * @param theItem
	 * @param attrlist
	 * @return
	 */
	protected String generateString(EntityItem theItem, String[] attrlist) {
		if (theItem.getEntityType().equals("MODELWARR")) {
			// check if this is DEFWARR
			String defwarr = PokUtils.getAttributeFlagValue(theItem, "DEFWARR");
			addDebug("generateString: " + theItem.getKey() + " defwarr " + defwarr);
			if (DEFWARR_Yes.equals(defwarr) || defwarr == null) {
				StringBuffer sb = new StringBuffer(theItem.getKey());
				for (int a = 0; a < attrlist.length; a++) {
					String value = PokUtils.getAttributeValue(theItem, attrlist[a], ", ", "", false);
					if (DEFWARR_Yes.equals(defwarr) && attrlist[a].equals("COUNTRYLIST")) { // dont
																							// look
																							// at
																							// country
																							// changes
																							// if
																							// this
																							// is
																							// a
																							// ww
																							// default
						value = "DEFWARR";
					}
					if (attrlist[a].equals("DEFWARR") && defwarr == null) { // dont
																			// look
																			// at
																			// defwarr
																			// null
																			// to
																			// no
																			// chgs
						value = DEFWARR_No_Desc;
					}
					sb.append(":" + value);
				}
				return sb.toString();
			}
		}

		return super.generateString(theItem, attrlist);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see COM.ibm.eannounce.abr.sg.DQABRSTATUS#doDQChecking(COM.ibm.eannounce.
	 * objects.EntityItem, java.lang.String) checks from ss: 1.00 MODEL Root
	 * MODEL 2.00 ANNDATE 3.00 WITHDRAWDATE => MODEL ANNDATE E E E {LD:
	 * WITHDRAWDATE} {WITHDRAWDATE} must not be earlier than {LD: ANNDATE}
	 * {ANNDATE} 4.00 WTHDRWEFFCTVDATE => MODEL ANNDATE E E E
	 * {LD:WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE} must not be earlier than {LD:
	 * ANNDATE} {ANNDATE} Delete 20110322 4.02 IF "Service" (102) <> MODEL
	 * COFCAT 4.04 "00 - No Product Hierarchy Code" (BHPH0000) <> MODEL
	 * BHPRODHIERCD E E E must not have {LD: BHPRODHIERCD} {BHPRODHIERCD} Delete
	 * 20110322 4.06 END 4.02
	 * 
	 * 5.00 AVAIL A MODELAVAIL-d MODEL AVAIL 2016-01-26 Change Column G 6.00
	 * WHEN AVAILTYPE = "Planned Availability" or "MES Planned Availability"
	 * 2016-01-26 New 6.01 IF AVAILTYPE = "Planned Availability" 7.00 CountOf =>
	 * 1 RE*2 RE*2 RE*2 must have at least one "Planned Availability" 2016-01-26
	 * New 7.01 END 6.01 8.00 EFFECTIVEDATE => MODEL ANNDATE W E E {LD: AVAIL}
	 * {NDN: AVAIL} must not be earlier than the {LD: MODEL} {LD: ANNDATE}
	 * {ANNDATE} 9.00 COUNTRYLIST Not Checked since MODEL does not have
	 * COUNTRYLIST 10.20 WHEN "RFA" (RFA) = A: AVAIL AVAILANNTYPE 11.00 IF
	 * COFCAT <> "Service" (102) 11.10 AND "No" (FEATN) <> MODEL FEATINDC 12.00
	 * THEN ANNCODENAME IN "D: AVAIL | H: AVAIL" ANNCODENAME W RW RE MODEL
	 * Announcement must announce Feature or SWFeature the {LD: MODEL} {LD:
	 * ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must include one or more features.
	 * 12.10 END 12.00 13.00 ANNOUNCEMENT M A: + AVAILANNA 14.00 WHEN ANNTYPE =
	 * "New" (19) 15.00 ANNDATE => MODEL ANNDATE W W E {LD: ANNOUNCEMENT} {LD:
	 * ANNDATE} {ANNDATE} must not be earlier than the {LD: MODEL} {LD: ANNDATE}
	 * {ANNDATE} 16.00 END 14.00 16.20 END 10.20 17.00 END 6.00 18.00 AVAIL B
	 * MODELAVAIL-d MODEL AVAIL 19.00 WHEN AVAILTYPE = "First Order" 20.00
	 * EFFECTIVEDATE => MODEL ANNDATE W W E {LD: AVAIL} {NDN: AVAIL} must not be
	 * earlier than the {LD: MODEL} {LD: ANNDATE} {ANNDATE} xx21.00 COUNTRYLIST
	 * "IN aggregate G" A: AVAIL COUNTRYLIST W W E {LD: AVAIL} {NDN: AVAIL} {LD:
	 * COUNTRYLIST} includes a Country that does not have a
	 * "Planned Availability" Change 20111216 21.00 COUNTRYLIST "IN aggregate G"
	 * A: AVAIL COUNTRYLIST OSN:XCC_LIST W W E {LD: AVAIL} {NDN: AVAIL} {LD:
	 * COUNTRYLIST} includes a Country that does not have a
	 * "Planned Availability" 21.20 WHEN "RFA" (RFA) = B: AVAIL AVAILANNTYPE
	 * 22.00 ANNOUNCEMENT G B: + AVAILANNA MODEL ANNOUNCEMENT 23.00 WHEN ANNTYPE
	 * = "New" (19) 24.00 ANNDATE => MODEL ANNDATE W W E {LD: ANNOUNCEMENT} {LD:
	 * ANNDATE} {ANNDATE} must not be earlier than the {LD: MODEL} {LD: ANNDATE}
	 * {ANNDATE} 24.10 ANNDATE <= B: AVAIL EFFECTIVEDATE W E E {LD: AVAIL} {NDN:
	 * B: AVAIL} must not be earlier than the {LD: ANNOUNCEMENT} {NDN: G:
	 * ANNOUNCEMENT} 25.00 END 23.00 25.20 END 21.20 26.00 END 19.00 27.00 AVAIL
	 * C MODELAVAIL-d MODEL AVAIL 28.00 WHEN AVAILTYPE = "Last Order" 29.00
	 * EFFECTIVEDATE <= MODEL WTHDRWEFFCTVDATE W W E {LD: AVAIL} {NDN: AVAIL}
	 * must not be later than the {LD: MODEL} {LD: WTHDRWEFFCTVDATE}
	 * {WTHDRWEFFCTVDATE} xx30.00 COUNTRYLIST "IN aggregate G" A: AVAIL
	 * COUNTRYLIST W W E*2 {LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a
	 * Country that does not have a "Planned Availability" xx32.00 IF
	 * COUNTRYLIST Match D: AVAIL COUNTRYLIST has PRODSTRUCT in this Country
	 * 30.00 COUNTRYLIST "IN aggregate G" A: AVAIL COUNTRYLIST OSN:XCC_LIST W W
	 * E*2 A: MODEL Planned Avail {LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST}
	 * includes a Country that does not have a "Planned Availability" 32.00 IF
	 * COUNTRYLIST Match D: AVAIL COUNTRYLIST OSN:XCC_LIST
	 * "D: PRODSTRUCT Planned Availhas PRODSTRUCT in this Country" 33.00 AND
	 * "Initial" = PRODSTRUCT ORDERCODE 33.20 AND FCTYPE =
	 * "Primary FC (100) |""Secondary FC"" (110)" RPQ logic 34.00 THEN TheMatch
	 * IN E: AVAIL COUNTRYLIST OSN:XCC_LIST W RW RE
	 * "E: PRODSTRUCT Last order ALL Features that are Initial must be withdrawn in all countries that the MODEL is being withdrawn"
	 * {LD: FEATURE} (NDN: E: FEATURE} must have a "Last Order" for all
	 * countries in the {LD: MODEL} {LD: AVAIL} {C: AVAIL} AND PRODSTRUCT
	 * SameParent D: AVAIL E: AVAIL Note: the AVAILs must have the same parent
	 * PRODSTRUCT 34.10 EFFECTIVEDATE => E: AVAIL EFFECTIVEDATE OSN:XCC_LIST W
	 * RW RE
	 * "E: PRODSTRUCT Last order Features must be withdrawn on or before MODEL is withdrawn"
	 * {LD: PRODSTRUCT} (NDN: E: PRODSTRUCT} {LD: AVAIL} {E: AVAIL} must be no
	 * later than the {LD: MODEL} {LD:AVAIL} 34.20 END 34.00 35.00 IF
	 * COUNTRYLIST Match H: AVAIL COUNTRYLIST OSN:XCC_LIST
	 * "S: SWPRODSTRUCT Planned Avail has SWPRODSTRUCT in this Country" 36.00
	 * THEN TheMatch IN K: AVAIL COUNTRYLIST OSN:XCC_LIST W RW RE ALL SW
	 * Features must be withdrawn in all countries that the MODEL is being
	 * withdrawn {LD: SWFEATURE} (NDN: K: SWFEATURE} must have a "Last Order"
	 * for all countries in the {LD: MODEL} {LD: AVAIL} {C: AVAIL} 36.02 AND
	 * SWPRODSTRUCT SameParent H: AVAIL K: AVAIL Note: the AVAILs must have the
	 * same parent SWPRODSTRUCT 36.10 EFFECTIVEDATE => K: AVAIL EFFECTIVEDATE
	 * OSN:XCC_LIST W RW RE SW Features must be withdrawn on or before MODEL is
	 * withdrawn {LD: SWPRODSTRUCT} (NDN: K: SWPRODSTRUCT} {LD: AVAIL} {E:
	 * AVAIL} must be no later than the {LD: MODEL} {LD:AVAIL} 36.20 END 36.00
	 * 
	 * 38.20 WHEN "RFA" (RFA) = C: AVAIL AVAILANNTYPE 39.00 ANNOUNCEMENT N C: +
	 * AVAILANNA 40.00 WHEN ANNTYPE = "End Of Life - Withdrawal from mktg" (14)
	 * 41.00 ANNDATE <= MODEL WITHDRAWDATE W W E {LD: ANNOUNCEMENT} {LD:
	 * ANNDATE} {ANNDATE} must not be later than the {LD: MODEL} {LD:
	 * WITHDRAWDATE} {WITHDRAWDATE} 41.20 ANNDATE <= C: AVAIL EFFECTIVEDATE W W
	 * E {LD: ANNOUNCEMENT} {LD: ANNDATE} {ANNDATE} must not be later than the
	 * {LD: MODEL} {LD: AVAIL {NDN: C: AVAIL} 42.00 END 40.00 42.20 END 38.20
	 * 43.00 END 28.00 200.00 AVAIL P MODELAVAIL-d MODEL AVAIL 201.00 WHEN
	 * AVAILTYPE = "End of Marketing" (200) 202.00 EFFECTIVEDATE <= MODEL
	 * WITHDRAWDATE W W E {LD: AVAIL} {NDN: AVAIL} must not be later than the
	 * {LD: MODEL} {LD: WITHDRAWDATE} {WITHDRAWDATE} xx203.00 COUNTRYLIST
	 * "IN aggregate G" A:AVAIL COUNTRYLIST W W E*2 {LD: AVAIL} {NDN: AVAIL}
	 * {LD: COUNTRYLIST} includes a country that does not have a
	 * "Planned Availability" Change 20111216 203.00 COUNTRYLIST
	 * "IN aggregate G" A:AVAIL COUNTRYLIST OSN:XCC_LIST W W E*2 {LD: AVAIL}
	 * {NDN: AVAIL} {LD: COUNTRYLIST} includes a country that does not have a
	 * "Planned Availability" 204.00 WHEN "RFA" (RFA) = P: AVAIL AVAILANNTYPE
	 * 205.00 ANNOUNCEMENT Q P: + AVAILANNA-d MODEL ANNOUNCEMENT 206.00 IF
	 * ANNTYPE <> "End Of Life - Withdrawal from mktg" (14) 207.00 CountOf = 0 E
	 * E E {LD: AVAIL} {NDN: P:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN:
	 * ANNOUNCEMENT} 208.00 ELSE ANNDATE <= MODEL WITHDRAWDATE W W E {LD:
	 * ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must be earlier than the {LD: MODEL}
	 * {LD: WITHDRAWDATE} {WITHDRAWDATE} Add 20111213 208.10 ANNDATE <= P: AVAIL
	 * EFFECTIVEDATE W W E {LD: ANNOUNCEMENT} {LD: ANNDATE} {ANNDATE} must not
	 * be later than the {LD: MODEL} {LD: AVAIL {NDN: P: AVAIL} 209.00 END
	 * 206.00 210.00 END 204.00 211.00 END 201.00 212.00 AVAIL R MODELAVAIL-d
	 * MODEL AVAIL 213.00 WHEN AVAILTYPE = "End of Service" (151) 214.00
	 * EFFECTIVEDATE => MODEL WTHDRWEFFCTVDATE W W E {LD: AVAIL} {NDN: AVAIL}
	 * must not be earlier than {LD: MODEL} {LD: WTHDRWEFFCTVDATE}
	 * {WTHDRWEFFCTVDATE} xx215.00 COUNTRYLIST IN C:AVAIL COUNTRYLIST W W E*2
	 * {LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a country that does
	 * not have a "Last Order" Change 20111216 215.00 COUNTRYLIST IN C:AVAIL
	 * COUNTRYLIST OSN:XCC_LIST W W E*2 {LD: AVAIL} {NDN: AVAIL} {LD:
	 * COUNTRYLIST} includes a country that does not have a "Last Order" 216.00
	 * IF COUNTRYLIST Match D: AVAIL COUNTRYLIST MODEL has End of Service ==>
	 * TMF available in that Country will not have an End of Service ---- i.e.
	 * MODEL stops all 217.00 THEN TheMatch IN L: AVAIL COUNTRYLIST W RW RE {LD:
	 * IPSCFEAT} (NDN: L: IPSCFEAT} must have a "End of Service" for all
	 * countries in the {LD: MODEL} {LD: AVAIL} {E: AVAIL} 218.00 WHEN "RFA"
	 * (RFA) = R: AVAIL AVAILANNTYPE 219.00 ANNOUNCEMENT S R: + AVAILANNA-d
	 * MODEL ANNOUNCEMENT 220.00 IF ANNTYPE <>
	 * "End Of Life - Discontinuance of service" (13) 221.00 CountOf = 0 E E E
	 * {LD: AVAIL} {NDN: R:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN:
	 * ANNOUNCEMENT} xx222.00 ELSE ANNDATE <= MODEL WITHDRAWDATE W W E {LD:
	 * ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must be earlier than the {LD: MODEL}
	 * {LD: WITHDRAWDATE} {WITHDRAWDATE} Change 20111212 222.00 ELSE ANNDATE =>
	 * MODEL WITHDRAWDATE W W E {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not
	 * be earlier than the {LD: MODEL} {LD: WITHDRAWDATE} {WITHDRAWDATE} Add
	 * 20111213 222.10 ANNDATE <= R: AVAIL EFFECTIVEDATE W W E {LD:
	 * ANNOUNCEMENT} {LD: ANNDATE} {ANNDATE} must not be later than the {LD:
	 * MODEL} {LD: AVAIL {NDN: R: AVAIL} 223.00 END 220.00 224.00 END 218.00
	 * 225.00 END 213.00 44.00 MODEL Root Hardware MODEL 45.00 WHEN COFCAT =
	 * "Hardware" (100) 46.00 FEATURE PRODSTRUCT-u 46.80 IF "No" (FEATN) <>
	 * MODEL.FEATINDC 47.00 CountOf => 1 RE RE RE must have at least one {LD:
	 * FEATURE} 47.20 END 48.00 SWFEATURE SWPRODSTRUCT-u 49.00 CountOf = 0 RE RE
	 * RE must not have any {LD: SWFEATURE) {NDN: SWFEATURE} 52.00 AVAIL D
	 * PRODSTRUCT: OOFAVAIL-d: AVAIL TMF AVAIL 53.00 WHEN AVAILTYPE =
	 * "Planned Availability" 54.00 ANNCODENAME xx55.00 COUNTRYLIST
	 * "IN aggregate G" A: AVAIL COUNTRYLIST W W E*2 TMF can only be available
	 * where the MODEL is available {LD: PRODSTRUCT} {NDN: PRODSTRUCT}{LD:
	 * AVAIL} {NDN: A: AVAIL} {LD: COUNTRYLIST} includes a Country that the {LD:
	 * MODEL} is not available in. xx56.00 EFFECTIVEDATE => A: AVAIL
	 * EFFECTIVEDATE Yes W W E*3 can not be available before the MODEL {LD:
	 * PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} must not be
	 * earlier than the {LD: MODEL} {LD:AVAIL} {NDN: A: AVAIL} Change 20111216
	 * 55.00 COUNTRYLIST "IN aggregate G" A: AVAIL COUNTRYLIST OSN:XCC_LIST W W
	 * E*2 TMF can only be available where the MODEL is available {LD:
	 * PRODSTRUCT} {NDN: PRODSTRUCT}{LD: AVAIL} {NDN: A: AVAIL} {LD:
	 * COUNTRYLIST} includes a Country that the {LD: MODEL} is not available in.
	 * Change 20111216 56.00 EFFECTIVEDATE => A: AVAIL EFFECTIVEDATE Ctry
	 * OSN:XCC_LIST W W E*3 can not be available before the MODEL {LD:
	 * PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} must not be
	 * earlier than the {LD: MODEL} {LD:AVAIL} {NDN: A: AVAIL} 57.00 END 53.00
	 * 58.00 AVAIL PRODSTRUCT: OOFAVAIL-d: AVAIL TMF AVAIL 59.00 WHEN AVAILTYPE
	 * = "First Order" 60.00 ANNCODENAME xx61.00 COUNTRYLIST "IN aggregate G" D:
	 * AVAIL COUNTRYLIST W W E*2 TMF can only be available where the MODEL is
	 * available {LD: PRODSTRUCT} {NDN: PRODSTRUCT}{LD: AVAIL} {NDN: AVAIL} {LD:
	 * COUNTRYLIST} includes a Country that does not have a
	 * "Planned Availability" xx62.00 EFFECTIVEDATE => A: AVAIL EFFECTIVEDATE
	 * Yes W W E*3 can not be available before the MODEL {LD: PRODSTRUCT} {NDN:
	 * PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} must not be earlier than the
	 * {LD:MODEL} {LD:AVAIL} {NDN: A: AVAIL} Change 20111216 61.00 COUNTRYLIST
	 * "IN aggregate G" D: AVAIL COUNTRYLIST OSN W W E*2 TMF can only be
	 * available where the MODEL is available {LD: PRODSTRUCT} {NDN:
	 * PRODSTRUCT}{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a Country
	 * that does not have a "Planned Availability" Change 20111216 62.00
	 * EFFECTIVEDATE => A: AVAIL EFFECTIVEDATE Both W W E*3 can not be available
	 * before the MODEL {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN:
	 * AVAIL} must not be earlier than the {LD:MODEL} {LD:AVAIL} {NDN: A: AVAIL}
	 * 63.00 END 59.00 64.00 AVAIL E PRODSTRUCT: OOFAVAIL-d: AVAIL TMF AVAIL
	 * 65.00 WHEN AVAILTYPE = "Last Order" 66.00 ANNCODENAME xx67.00 COUNTRYLIST
	 * IN D: AVAIL COUNTRYLIST W W E*2 TMF can only be withdrawn where it has
	 * been announced {LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a
	 * country that does not have a "Planned Availability" Change 20111216 67.00
	 * COUNTRYLIST IN D: AVAIL COUNTRYLIST OSN W W E*2 TMF can only be withdrawn
	 * where it has been announced {LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST}
	 * includes a country that does not have a "Planned Availability" 68.00 IF
	 * "Initial" = PRODSTRUCT ORDERCODE xx69.00 THEN EFFECTIVEDATE <= C: AVAIL
	 * EFFECTIVEDATE Yes W W E*3 TMF must be withdrawn on or before the MODEL
	 * {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} must not be
	 * later than the {LD:MODEL} {LD:AVAIL} {NDN: C: AVAIL} Change 20111216
	 * 69.00 THEN EFFECTIVEDATE <= C: AVAIL EFFECTIVEDATE Both W W E*3 TMF must
	 * be withdrawn on or before the MODEL {LD: PRODSTRUCT} {NDN: PRODSTRUCT}
	 * {LD: AVAIL} {NDN: AVAIL} must not be later than the {LD:MODEL} {LD:AVAIL}
	 * {NDN: C: AVAIL} 70.00 END 65.00 70.20 "End of Marketing End of Service on
	 * the MODEL applies to TMF hence no checking here" 250.00 AVAIL T
	 * PRODSTRUCT: OOFAVAIL-d: AVAIL TMF AVAIL 251.00 WHEN AVAILTYPE =
	 * "End of Service" (151) 252.00 ANNCODENAME 253.00 COUNTRYLIST IN D: AVAIL
	 * COUNTRYLIST W W E*2 {LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a
	 * country that does not have a "Planned Availability" 254.00 EFFECTIVEDATE
	 * <= C: AVAIL EFFECTIVEDATE Yes W W E*3 {LD: IPSCSTRUCT} {NDN: IPSCSTRUCT}
	 * {LD: AVAIL} {NDN: AVAIL} can not be later than the {LD:AVAIL} {NDN: C:
	 * AVAIL} 255.00 END 251.00 71.00 FEATURE F PRODSTRUCT-u RPQ FEATURE 72.00
	 * WHEN FCTYPE <> "Primary FC (100) |""Secondary FC"" (110)" 73.00
	 * PRODSTRUCT F: 74.00 ANNDATE => M: ANNOUNCEMENT ANNDATE W W E {LD:
	 * PRODSTRUCT} {NDN: PRODSTRUCT} {LD: ANNDATE} {ANNDATE} can not be earlier
	 * than the {LD: ANNOUNCEMENT} {NDN: M: ANNOUNCEMENT} {LD: ANNDATE} {M:
	 * ANNDATE} 75.00 GENAVAILDATE => A: AVAIL EFFECTIVEDATE W W E {LD:
	 * PRODSTRUCT} {NDN: PRODSTRUCT} {LD: GENAVAILDATE} {GENAVAILDATE} can not
	 * be earlier than the {LD: AVAIL} {NDN: A: AVAIL} #76.00 WITHDRAWDATE <= N:
	 * ANNOUNCEMENT ANNDATE W W E {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD:
	 * WITHDRAWDATE} {WITHDRAWDATE} can not be later than the {LD: ANNOUNCEMENT}
	 * {NDN: N: ANNOUNCEMENT} {LD: ANNDATE} {N: ANNDATE} 76.00 WITHDRAWDATE =>
	 * N: ANNOUNCEMENT ANNDATE W W E {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD:
	 * WITHDRAWDATE} {WITHDRAWDATE} can not be earlier than the {LD:
	 * ANNOUNCEMENT} {NDN: N: ANNOUNCEMENT} {LD: ANNDATE} {N: ANNDATE} 77.00
	 * WTHDRWEFFCTVDATE <= C: AVAIL EFFECTIVEDATE W W E {LD: PRODSTRUCT} {NDN:
	 * PRODSTRUCT} {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE} can not be later
	 * than the {LD: AVAIL} {NDN: C: AVAIL} 78.00 END 72.00
	 * 
	 * 91.00 WARR MODELWARR-d WARR 91.10 IF "No Warranty" <> MODEL WARRSVCCOVR
	 * 91.18 CountOf => 1 RW RW RE Required on all Hardware must have at least
	 * one Warranty 92.00 WARRCoverage EntityType Yes RW RE RE Required if XCC
	 * {LD: WARR} {NDN: WARR} have gaps in the date range. 92.10 COUNTRYLIST
	 * "Contains aggregate E" A: AVAIL COUNTRYLIST E E E Column E - attributes
	 * being moved to MODELWARR must have a {LD: WARR} for every country in the
	 * {LD: MODEL} {LD: AVAIL} {NDN: A: AVAIL} 92.20 MIN(EFFECTIVEDATE) <= A:
	 * AVAIL EFFECTIVEDATE Yes E E E Column E - attributes being moved to
	 * MODELWARR must have a {LD: WARR} with an EFFECTIVE DATE as early as or
	 * earlier than the {LD: MODEL} {LD: AVAIL} {NDN: A: AVAIL} 92.30
	 * MAX(ENDDATE) => C: AVAIL EFFECTIVEDATE Yes E E E Column E - attributes
	 * being moved to MODELWARR must have a {LD: WARR} with a ENDATE as late as
	 * or later than the {LD: MODEL} {LD: AVAIL} {NDN: C: AVAIL} 95.02 ELSE
	 * 95.04 CountOf = 0 E E E must not have any {LD: WARR} {NDN: WARR} 95.06
	 * END 91.10
	 * 
	 * 
	 * 79.00 END 45.00 Hardware Model - End 80.00 MODEL Root Hardware System
	 * MODEL 81.00 WHEN COFCAT = "Hardware" (100) delete 82.00 AND COFSUBCAT =
	 * "System" (126) 83.00 IF PDHDOMAIN IN ABR_PROPERTITIES XCC_LIST XCC Unique
	 * Rules 84.00 MODELCG MDLCGMDL-u MODELCG 85.00 CountOf => 1 RW RW RE must
	 * be in a {LD MODELCG} 86.00 IMG MODELIMG-d IMG 86.90 CountOf => 1 RW RW RE
	 * Required if XCC must have at least one Image 87.00 UniqueCoverage IMG Yes
	 * RW RW RE Required if XCC {LD: IMG} {NDN: IMG} have gaps in the date range
	 * or they overlap. 88.00 COUNTRYLIST "Contains aggregate E" A: AVAIL
	 * COUNTRYLIST RW RW RE must have a {LD: IMG} for every country in the {LD:
	 * MODEL} {LD: AVAIL} {NDN: A: AVAIL} 89.00 MIN(PUBFROM) <= A: AVAIL
	 * EFFECTIVEDATE Yes RW RW RE must have a {LD: IMG} with a PUBFROM as early
	 * as or earlier than the {LD: MODEL} {LD: AVAIL} {NDN: A: AVAIL} 90.00
	 * MAX(PUBTO) => C: AVAIL EFFECTIVEDATE Yes RW RW RE must have a {LD: IMG}
	 * with a PUBTO as late as or later than the {LD: MODEL} {LD: AVAIL} {NDN:
	 * C: AVAIL} 90.02 END 83.00 96.00 END 81.00 97.00 MODEL Root Software MODEL
	 * 98.00 WHEN COFCAT = "Software" (101) 99.00 FEATURE PRODSTRUCT-u 100.00
	 * CountOf = 0 RE RE E must not have any {LD: FEATURE) {NDN: FEATURE} 101.00
	 * SWFEATURE SWPRODSTRUCT-u 101.20 IF "No" (FEATN) <> MODEL FEATINDC 102.00
	 * CountOf => 1 RE RE RE must have at least one {LD: SWFEATURE} 101.10 END
	 * 105.00 AVAIL H SWPRODSTRUCT: SWPRODSTRUCTAVAIL-d: AVAIL TM SW AVAIL
	 * 106.00 WHEN AVAILTYPE = "Planned Availability" 107.00 ANNCODENAME 108.00
	 * COUNTRYLIST "IN aggregate G" A: AVAIL COUNTRYLIST W W E*2 {LD:
	 * SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} includes a
	 * Country that the {LD: MODEL} is not available in 109.00 EFFECTIVEDATE =>
	 * A: AVAIL EFFECTIVEDATE Yes W W E {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT}
	 * {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: MODEL}
	 * {LD:AVAIL} {NDN: A: AVAIL} 110.00 END 106.00 111.00 AVAIL SWPRODSTRUCT:
	 * SWPRODSTRUCTAVAIL-d: AVAIL TM SW AVAIL 112.00 WHEN AVAILTYPE =
	 * "First Order" 113.00 ANNCODENAME 114.00 COUNTRYLIST "IN aggregate G" H:
	 * AVAIL COUNTRYLIST W W E*2 {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD:
	 * AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a Country that does not
	 * have a "Planned Availability" 115.00 EFFECTIVEDATE => A: AVAIL
	 * EFFECTIVEDATE Yes W W E {LD: SWPRODSTRUCT} {NDN:SWPRODSTRUCT} {LD: AVAIL}
	 * {NDN: AVAIL} must not be earlier than the {LD: MODEL} {LD:AVAIL} {NDN: A:
	 * AVAIL} 116.00 END 112.00 117.00 AVAIL K SWPRODSTRUCT:
	 * SWPRODSTRUCTAVAIL-d: AVAIL TM SW AVAIL 118.00 WHEN AVAILTYPE =
	 * "Last Order" 119.00 ANNCODENAME 120.00 COUNTRYLIST "IN aggregate G" H:
	 * AVAIL COUNTRYLIST W W E*2 {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD:
	 * AVAIL} {NDN: AVAIL} includes a Country that does not have a
	 * "Planned Availability" 121.00 EFFECTIVEDATE <= C: AVAIL EFFECTIVEDATE Yes
	 * W W E {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
	 * must not be later than the {LD:MODEL} {LD:AVAIL} {NDN: C: AVAIL} 122.00
	 * END 118.00 123.00 END 98.00 124.00 MODEL Root Service MODEL 125.00 WHEN
	 * COFCAT = "Service" (102) 126.00 MODELCG MDLCGMDL-u 127.00 CountOf = 0 RE
	 * RE E must not be in a {LD MODELCG} {NDN: MODELCG} 128.00 SEOCG SEOCGSEO-u
	 * 129.00 CountOf = 0 RE RE E must not be in a {LD SEOCG} {NDN: SEOCG}
	 * 130.00 FEATURE SWPRODSTRUCT-u 131.00 CountOf = 0 RE RE E must not have
	 * any {LD: FEATURE) {NDN: FEATURE} 132.00 SWFEATURE SWPRODSTRUCT-u 133.00
	 * CountOf = 0 RE RE E must not have any {LD: SWFEATURE) {NDN: SWFEATURE}
	 * 154.00 END 125.00 200.00 all MODELs: Hardware, Software, Services 90.04
	 * IMG MODELIMG-d IMG - optional 90.06 UniqueCoverage IMG Yes W E {LD: IMG}
	 * {NDN: IMG} have gaps in the date range or they overlap. 90.08 COUNTRYLIST
	 * "Contains aggregate E" A: AVAIL COUNTRYLIST W E must have a {LD: IMG} for
	 * every country in the {LD: MODEL} {LD: AVAIL} {NDN: A: AVAIL} 90.10
	 * MIN(PUBFROM) <= A: AVAIL EFFECTIVEDATE Yes W E must have a {LD: IMG} with
	 * a PUBFROM as early as or earlier than the {LD: MODEL} {LD: AVAIL} {NDN:
	 * A: AVAIL} 90.12 MAX(PUBTO) => C: AVAIL EFFECTIVEDATE Yes W E must have a
	 * {LD: IMG} with a PUBTO as late as or later than the {LD: MODEL} {LD:
	 * AVAIL} {NDN: C: AVAIL} 90.18 END 90.04 IMG 90.20 MM MODELMM-d MM -
	 * optional 90.22 UniqueCoverage MM Yes W E {LD: MM} {NDN: MM} have gaps in
	 * the date range or they overlap. 90.24 COUNTRYLIST "Contains aggregate E"
	 * A: AVAIL COUNTRYLIST W E must have a {LD: MM} for every country in the
	 * {LD: MODEL} {LD: AVAIL} {NDN: A: AVAIL} 90.26 MIN(PUBFROM) <= A: AVAIL
	 * EFFECTIVEDATE Yes W E must have a {LD: MM} with a PUBFROM as early as or
	 * earlier than the {LD: MODEL} {LD: AVAIL} {NDN: A: AVAIL} 90.28 MAX(PUBTO)
	 * => C: AVAIL EFFECTIVEDATE Yes W E must have a {LD: MM} with a PUBTO as
	 * late as or later than the {LD: MODEL} {LD: AVAIL} {NDN: C: AVAIL} 90.38
	 * END 90.20 MM 90.40 FB MODELFB-d FB - optional 90.42 UniqueCoverage FB Yes
	 * W E {LD: FB} {NDN: FB} have gaps in the date range or they overlap. 90.44
	 * COUNTRYLIST "Contains aggregate E" A: AVAIL COUNTRYLIST W E must have a
	 * {LD: FB} for every country in the {LD: MODEL} {LD: AVAIL} {NDN: A: AVAIL}
	 * 90.46 MIN(PUBFROM) <= A: AVAIL EFFECTIVEDATE Yes W E must have a {LD: FB}
	 * with a PUBFROM as early as or earlier than the {LD: MODEL} {LD: AVAIL}
	 * {NDN: A: AVAIL} 90.48 MAX(PUBTO) => C: AVAIL EFFECTIVEDATE Yes W E must
	 * have a {LD: FB} with a PUBTO as late as or later than the {LD: MODEL}
	 * {LD: AVAIL} {NDN: C: AVAIL} 90.50 END 90.40 FB
	 */
	protected void doDQChecking(EntityItem rootEntity, String statusFlag) throws Exception {
		addHeading(2, rootEntity.getEntityGroup().getLongDescription() + " Checks:");

		String modelCOFCAT = getAttributeFlagEnabledValue(rootEntity, "COFCAT");
		if (modelCOFCAT == null) {
			modelCOFCAT = "";
		}
		boolean isSvcModel = SERVICE.equals(modelCOFCAT);
		String prodhierFlag = PokUtils.getAttributeFlagValue(rootEntity, "BHPRODHIERCD");
		//If SYSIDUNIT = "SIU-CPU", system type should be set to machine type.
		//Else no check on system type, which means system type can be blank.
		doModelSysteamTypeChecks(rootEntity, statusFlag);
		addDebug(rootEntity.getKey() + " COFCAT: " + modelCOFCAT + " isSvcModel " + isSvcModel + " prodhierFlag "
				+ prodhierFlag);
		doWarrantyWTY0000(rootEntity);
		// 2.00 ANNDATE
		// 3.00 WITHDRAWDATE => MODEL ANNDATE E E E {LD: WITHDRAWDATE}
		// {WITHDRAWDATE} can not be earlier than {LD: ANNDATE} {ANNDATE}
		checkCanNotBeEarlier(rootEntity, "WITHDRAWDATE", "ANNDATE", CHECKLEVEL_E);

		// 4.00 WTHDRWEFFCTVDATE => MODEL ANNDATE E E E {LD: WITHDRAWDATE}
		// {WITHDRAWDATE} can not be earlier than {LD: ANNDATE} {ANNDATE}
		checkCanNotBeEarlier(rootEntity, "WTHDRWEFFCTVDATE", "ANNDATE", CHECKLEVEL_E);

		// Delete 20110322 4.02 IF "Service" (102) <> MODEL COFCAT
		// 4.04 "00 - No Product Hierarchy Code" (BHPH0000) <> MODEL
		// BHPRODHIERCD
		// if(!isSvcModel &&
		// Remove - RCQ00337764 for lenovo
		// if(BHPRODHIERCD_No_ProdHCode.equals(prodhierFlag)){
		// //4.04 "00 - No Product Hierarchy Code" (BHPH0000) <> MODEL
		// BHPRODHIERCD E E E
		// //must not have {LD: BHPRODHIERCD} {BHPRODHIERCD}
		// //MUST_NOT_HAVE_ERR1= must not have {0}
		// args[0] = this.getLD_Value(rootEntity, "BHPRODHIERCD");
		// createMessage(CHECKLEVEL_E,"MUST_NOT_HAVE_ERR1",args);
		// }
		// Delete 20110322 4.06 END 4.02

		int cnt;
		// 4.10 TAXCATG MODTAXRELEVANCE-d
		cnt = getCount("MODTAXRELEVANCE");
		if (cnt == 0) {
			// 4.12 CountOf => 1 W W E must have at least one "Tax Category"
			// MINIMUM_ERR = must have at least one {0}
			args[0] = m_elist.getEntityGroup("TAXCATG").getLongDescription();
			createMessage(getCheck_W_W_E(statusFlag), "MINIMUM_ERR", args);
		}

		// 4.40 TAXGRP MODELTAXGRP-d
		cnt = getCount("MODELTAXGRP");
		if (cnt == 0) {
			// 4.42 CountOf => 1 W W E must have at least one "Tax Group"
			// MINIMUM_ERR = must have at least one {0}
			args[0] = m_elist.getEntityGroup("TAXGRP").getLongDescription();
			createMessage(getCheck_W_W_E(statusFlag), "MINIMUM_ERR", args);
		}

		//
		getAvails(rootEntity, statusFlag, modelCOFCAT);
		doSoftwareDataChecks(rootEntity, statusFlag, modelCOFCAT);
		// doModelPLAAvailChecks(rootEntity, statusFlag, modelCOFCAT);
		doModelPLAndMesPLAvailChecks("Model Planned Avail Checks:", mdlPlaAvailVctA, PLANNEDAVAIL, rootEntity,
				statusFlag, modelCOFCAT);
		// doModelPLAndMesPLAvailChecks("Model MES Planned Avail Checks:",
		// mdlMesPlaAvailVctA, MESPLANNEDAVAIL, rootEntity, statusFlag,
		// modelCOFCAT);

		doModelFOAvailChecks(rootEntity, statusFlag, modelCOFCAT);

		// doModelLOAvailChecks(rootEntity, statusFlag, modelCOFCAT);
		doModelLOAndMesLOAvailChecks("Model Last Order Avail Checks:", mdlLOAvailVctC, LASTORDERAVAIL, rootEntity,
				statusFlag, modelCOFCAT);
		// doModelLOAndMesLOAvailChecks("Model MES Last Order Avail Checks:",
		// mdlMesLOAvailVctC, MESLASTORDERAVAIL, rootEntity, statusFlag,
		// modelCOFCAT);

		doModelEOMAvailChecks(rootEntity, statusFlag, modelCOFCAT);

		doModelEOSAvailChecks(rootEntity, statusFlag, modelCOFCAT);

		if (HARDWARE.equals(modelCOFCAT)) {
			doHardwareChecks(rootEntity, statusFlag);
		} else if (SOFTWARE.equals(modelCOFCAT)) {
			doSoftwareChecks(rootEntity, statusFlag);
		} else if (SERVICE.equals(modelCOFCAT)) {
			doServiceChecks(rootEntity, statusFlag);
		}
		int checklvl = doCheck_N_W_E(statusFlag);// no more checks if Draft

		if (CHECKLEVEL_NOOP == checklvl) {
			addDebug("other uniquecoverage checks are bypassed because status is " + statusFlag);
		} else {
			addHeading(3, rootEntity.getEntityGroup().getLongDescription() + " Unique Coverage Checks:");
			// New 200.00 all MODELs: Hardware, Software, Services
			// Move 90.04 IMG MODELIMG-d IMG - optional
			// Move 90.06 UniqueCoverage IMG Yes W E {LD: IMG} {NDN: IMG} have
			// gaps in the date range or they overlap.
			// Move 90.08 COUNTRYLIST "Contains aggregate E" A: AVAIL
			// COUNTRYLIST W E must have a {LD: IMG} for every country in the
			// {LD: MODEL} {LD: AVAIL} {NDN: A: AVAIL}
			// Move 90.10 MIN(PUBFROM) <= A: AVAIL EFFECTIVEDATE Yes W E must
			// have a {LD: IMG} with a PUBFROM as early as or earlier than the
			// {LD: MODEL} {LD: AVAIL} {NDN: A: AVAIL}
			// Move 90.12 MAX(PUBTO) => C: AVAIL EFFECTIVEDATE Yes W E must have
			// a {LD: IMG} with a PUBTO as late as or later than the {LD: MODEL}
			// {LD: AVAIL} {NDN: C: AVAIL}
			if (IMGUniqueCoverageChkDone) { // xcc hw does img check
				checkUniqueCoverage(rootEntity, "MODELIMG", "IMG", mdlPlaAvailVctA, mdlLOAvailVctC, checklvl, false);
			}
			// Move & Change 90.20 MM MODELMM-d MM - optional
			// Move 90.22 UniqueCoverage MM Yes W E {LD: MM} {NDN: MM} have gaps
			// in the date range or they overlap.
			// Move 90.24 COUNTRYLIST "Contains aggregate E" A: AVAIL
			// COUNTRYLIST W E must have a {LD: MM} for every country in the
			// {LD: MODEL} {LD: AVAIL} {NDN: A: AVAIL}
			// Move 90.26 MIN(PUBFROM) <= A: AVAIL EFFECTIVEDATE Yes W E must
			// have a {LD: MM} with a PUBFROM as early as or earlier than the
			// {LD: MODEL} {LD: AVAIL} {NDN: A: AVAIL}
			// Move 90.28 MAX(PUBTO) => C: AVAIL EFFECTIVEDATE Yes W E must have
			// a {LD: MM} with a PUBTO as late as or later than the {LD: MODEL}
			// {LD: AVAIL} {NDN: C: AVAIL}
			checkUniqueCoverage(rootEntity, "MODELMM", "MM", mdlPlaAvailVctA, mdlLOAvailVctC, checklvl, false);
			// checkUniqueCoverage(rootEntity, "MODELMM","MM",
			// mdlMesPlaAvailVctA, mdlMesLOAvailVctC, checklvl, false);
			// Move & Change 90.40 FB MODELFB-d FB - optional
			// Move 90.42 UniqueCoverage FB Yes W E {LD: FB} {NDN: FB} have gaps
			// in the date range or they overlap.
			// Move 90.44 COUNTRYLIST "Contains aggregate E" A: AVAIL
			// COUNTRYLIST W E must have a {LD: FB} for every country in the
			// {LD: MODEL} {LD: AVAIL} {NDN: A: AVAIL}
			// Move 90.46 MIN(PUBFROM) <= A: AVAIL EFFECTIVEDATE Yes W E must
			// have a {LD: FB} with a PUBFROM as early as or earlier than the
			// {LD: MODEL} {LD: AVAIL} {NDN: A: AVAIL}
			// Move 90.48 MAX(PUBTO) => C: AVAIL EFFECTIVEDATE Yes W E must have
			// a {LD: FB} with a PUBTO as late as or later than the {LD: MODEL}
			// {LD: AVAIL} {NDN: C: AVAIL}
			checkUniqueCoverage(rootEntity, "MODELFB", "FB", mdlPlaAvailVctA, mdlLOAvailVctC, checklvl, false);
			// checkUniqueCoverage(rootEntity, "MODELFB","FB",
			// mdlMesPlaAvailVctA, mdlMesLOAvailVctC, checklvl, false);
		}
	}

	private void doModelSysteamTypeChecks(EntityItem rootEntity, String statusFlag) {
		String sysidunit= PokUtils.getAttributeValue(rootEntity, "SYSIDUNIT", "", "");
		String systemtype= PokUtils.getAttributeValue(rootEntity, "SYSTEMTYPE", "", "");
		String machtype= PokUtils.getAttributeValue(rootEntity, "MACHTYPEATR", "", "");
		String cofcat= PokUtils.getAttributeValue(rootEntity, "COFCAT", "", "");
		addHeading(3,"Model SysteamType Checks:");
		addDebug("sysidunit=" + sysidunit + " systemtype=" + systemtype + "cofcat" + cofcat);
		if("Hardware".equals(cofcat)) {
			if ("SIU-CPU".equals(sysidunit)) {
				if (machtype == null || machtype.equals("") || !machtype.equals(systemtype)) {
					args[0] = "If SYSIDUNIT = \"SIU-CPU\", system type should be set to machine type :" + machtype;
					createMessage(getCheck_W_W_E(statusFlag), "INVALID_VALUES_ERR", args);
				}
			}
		}
	}

	private void doWarrantyWTY0000(EntityItem rootEntity) throws SQLException, MiddlewareException {

		addHeading(3, rootEntity.getEntityGroup().getLongDescription() + " Warranty Checks:");
		// String warrID = PokUtils.getAttributeFlagValue(rootEntity, "WARRID");


		// if ("WTY0000".equals(warrID)) {

		EntityGroup warrGrp = m_elist.getEntityGroup("WARR");
		if (warrGrp != null && warrGrp.getEntityItemCount() > 0) {
			for (int i = 0; i < warrGrp.getEntityItemCount(); i++) {
				EntityItem wItem = warrGrp.getEntityItem(i);
				String warrID = PokUtils.getAttributeValue(wItem, "WARRID", null, null);
				if ("WTY0000".equals(warrID)) {
					EntityItem mwItem = wItem.getUpLink().size() > 0 ? (EntityItem) wItem.getUpLink().get(0) : null;
					if (mwItem != null) {
						//PokUtils.getAttributeValue(item, attCode, delim, defValue)
						String defWarr = PokUtils.getAttributeValue(mwItem, "DEFWARR",null,null,true);
						if (defWarr == null || !defWarr.equals("No")) {
							args[0] = PokUtils.getAttributeDescription(mwItem.getEntityGroup(), "DEFWARR", "") + ":"
									+ defWarr + " on MODELWARR ";
							// args[1]=PokUtils.getAttributeDescription(entityItem.getEntityGroup(),
							// "TAXCLS","");
							args[1] = "Warranty ID WTY0000,"+PokUtils.getAttributeDescription(mwItem.getEntityGroup(), "DEFWARR", "") + ":"+"No is expected";
							addError("INVALID_VALUES_ERR", args);
						}
					}
				}
			}
		}

		/*
		 * Vector modelv = PokUtils.getAllLinkedEntities(rootEntity,
		 * "MODELWARR", "MODEL"); if(modelv!=null&&modelv.size()>0){ for(int
		 * i=0;i<modelv.size();i++){ EntityItem tItem =
		 * (EntityItem)modelv.get(i); EntityItem dItem =(EntityItem)
		 * tItem.getDownLink().get(0); //EntityItem uItem =(EntityItem)
		 * tItem.getUpLink().get(0);
		 * addDebug("tItem:"+tItem.getEntityType()+":"+tItem.getEntityID());
		 * addDebug("tItem downlink:"+tItem.getDownLinkCount()+":"+tItem.
		 * getDownLink());
		 * addDebug("tItem uItem:"+dItem.getEntityID()+":"+dItem.getEntityType()
		 * ); }
		 * 
		 * }
		 * 
		 * if (modelwarrGrp != null && modelwarrGrp.getEntityItemCount()>0) {
		 * for (int i = 0; i < modelwarrGrp.getEntityItemCount(); i++) {
		 * EntityItem tItem = modelwarrGrp.getEntityItem(i);
		 * addDebug("modelwarrGrp tItem uItem:"+tItem.getEntityID()+":"+tItem.
		 * getEntityType()); String defWarr =
		 * PokUtils.getAttributeFlagValue(tItem, "DEFWARR");
		 * 
		 * //,", ", "", false addDebug("defWarr:"+defWarr);
		 * if(defWarr==null||!defWarr.equals("N1")){
		 * args[0]="DEFWARR:"+PokUtils.getAttributeDescription(modelwarrGrp,
		 * "DEFWARR", "") +":"+defWarr+" on MODELWARR:"+tItem.getEntityID();
		 * //args[1]=PokUtils.getAttributeDescription(entityItem.getEntityGroup(
		 * ), "TAXCLS",""); args[1] = "Warranty ID WTY0000";
		 * addError("INVALID_VALUES_ERR", args); }
		 * 
		 * } // } }
		 */

		// check model avails

	}

	/**
	 * from BH FS ABR Catalog Attr Derivation 20110221.doc C. Data Quality As
	 * part of the normal process for offering information, a user first creates
	 * data in ï¿½Draftï¿½. The user then asserts the Data Quality
	 * (DATAQUALITY) as being ï¿½Ready for Reviewï¿½ which queues the Data
	 * Quality ABR. The DQ ABR checks to ensure that the data is ï¿½Ready for
	 * Reviewï¿½ and then advances Status (STATUS) to ï¿½Ready for
	 * Reviewï¿½.
	 * 
	 * The Data Quality ABR will be enhanced such that if the checks pass, then
	 * the DQ ABR will process the corresponding DARULE. If DARULE is processed
	 * successfully, then the DQ ABR will set STATUS = ï¿½Ready for
	 * Reviewï¿½. If DARULE is not processed successfully, then the DQ ABR
	 * will ï¿½Failï¿½ and return Data Quality to the prior state (Draft
	 * or Change Request).
	 */
	protected boolean updateDerivedData(EntityItem rootEntity) throws Exception {
		boolean chgsMade = false;
		// NOW() <= Withdrawal Effective Date (WTHDRWEFFCTVDATE)
		String wdDate = PokUtils.getAttributeValue(rootEntity, "WTHDRWEFFCTVDATE", "", FOREVER_DATE, false);
		addDebug("updateDerivedData wdDate: " + wdDate + " now: " + getCurrentDate());
		if (getCurrentDate().compareTo(wdDate) <= 0) {
			chgsMade = execDerivedData(rootEntity);
		}
		return chgsMade;
	}

	/*
	 * (non-Javadoc) update LIFECYCLE value when STATUS is updated
	 * 
	 * @see COM.ibm.eannounce.abr.sg.bh.DQABRSTATUS#doPostProcessing(COM.ibm.
	 * eannounce.objects.EntityItem, java.lang.String)
	 */
	protected String getLCRFRWFName() {
		return "WFLCMODELRFR";
	}

	protected String getLCFinalWFName() {
		return "WFLCMODELFINAL";
	}

	/***************
	 * 
	 * @param mdlItem
	 * @param statusFlag
	 * @param modelCOFCAT
	 * @throws SQLException
	 * @throws MiddlewareException
	 * 
	 *             212.00 AVAIL R MODELAVAIL-d MODEL AVAIL 213.00 WHEN AVAILTYPE
	 *             = "End of Service" (151) 214.00 EFFECTIVEDATE => MODEL
	 *             WTHDRWEFFCTVDATE W W E {LD: AVAIL} {NDN: AVAIL} must not be
	 *             earlier than {LD: MODEL} {LD: WTHDRWEFFCTVDATE}
	 *             {WTHDRWEFFCTVDATE} xx215.00 COUNTRYLIST IN C:AVAIL
	 *             COUNTRYLIST W W E*2 {LD: AVAIL} {NDN: AVAIL} {LD:
	 *             COUNTRYLIST} includes a country that does not have a "Last
	 *             Order" Change 20111216 215.00 COUNTRYLIST IN C:AVAIL
	 *             COUNTRYLIST OSN:XCC_LIST W W E*2 {LD: AVAIL} {NDN: AVAIL}
	 *             {LD: COUNTRYLIST} includes a country that does not have a
	 *             "Last Order" Do NOT Implement 216.00 IF COUNTRYLIST Match D:
	 *             AVAIL COUNTRYLIST MODEL has End of Service ==> TMF available
	 *             in that Country will not have an End of Service ---- i.e.
	 *             MODEL stops all Do NOT Implement 217.00 THEN TheMatch IN L:
	 *             AVAIL COUNTRYLIST W RW RE {LD: IPSCFEAT} (NDN: L: IPSCFEAT}
	 *             must have a "End of Service" for all countries in the {LD:
	 *             MODEL} {LD: AVAIL} {E: AVAIL} 218.00 WHEN "RFA" (RFA) = R:
	 *             AVAIL AVAILANNTYPE 219.00 ANNOUNCEMENT S R: + AVAILANNA-d
	 *             MODEL ANNOUNCEMENT 220.00 IF ANNTYPE <> "End Of Life -
	 *             Discontinuance of service" (13) 221.00 CountOf = 0 E E E {LD:
	 *             AVAIL} {NDN: R:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN:
	 *             ANNOUNCEMENT} xx222.00 ELSE ANNDATE <= MODEL WITHDRAWDATE W W
	 *             E {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must be earlier than
	 *             the {LD: MODEL} {LD: WITHDRAWDATE} {WITHDRAWDATE} Change
	 *             20111212 222.00 ELSE ANNDATE => MODEL WITHDRAWDATE W W E {LD:
	 *             ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be earlier than
	 *             the {LD: MODEL} {LD: WITHDRAWDATE} {WITHDRAWDATE} Add
	 *             20111213 222.10 ANNDATE <= R: AVAIL EFFECTIVEDATE W W E {LD:
	 *             ANNOUNCEMENT} {LD: ANNDATE} {ANNDATE} must not be later than
	 *             the {LD: MODEL} {LD: AVAIL {NDN: R: AVAIL} 223.00 END 220
	 *             224.00 END 218 225.00 END 213
	 * 
	 */
	private void doModelEOSAvailChecks(EntityItem mdlItem, String statusFlag, String modelCOFCAT)
			throws SQLException, MiddlewareException {
		int checklvl = getCheck_W_W_E(statusFlag);

		addHeading(3, "Model End of Service Avail Checks:");
		// 212.00 AVAIL R MODELAVAIL-d MODEL AVAIL
		// 213.00 WHEN AVAILTYPE = "End of Service" (151)
		if (mdlEOSAvailVctR.size() > 0) {
			for (int i = 0; i < mdlEOSAvailVctR.size(); i++) {
				EntityItem avail = (EntityItem) mdlEOSAvailVctR.elementAt(i);
				// 214.00 EFFECTIVEDATE => MODEL WTHDRWEFFCTVDATE W W E {LD:
				// AVAIL} {NDN: AVAIL} must not be earlier than the {LD: MODEL}
				// {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
				checkCanNotBeEarlier(avail, "EFFECTIVEDATE", mdlItem, "WTHDRWEFFCTVDATE", checklvl);

				// old215.00 COUNTRYLIST IN C:AVAIL COUNTRYLIST W W E*2 {LD:
				// AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a country that
				// does not have a "Last Order"
				// checkLOAvailForCtryExists(avail, mdlLOAvailCtryTblC.keySet(),
				// getCheckLevel(checklvl,mdlItem,"ANNDATE"));
			}
			Hashtable loAvailOSNTbl = new Hashtable();
			boolean loOsnErrors = getAvailByOSN(loAvailOSNTbl, this.mdlLOAvailVctC, true, CHECKLEVEL_RE);
			// MES
			// Hashtable mesLoAvailOSNTbl = new Hashtable();
			// boolean mesLoOsnErrors =
			// getAvailByOSN(mesLoAvailOSNTbl,this.mdlMesLOAvailVctC,true,CHECKLEVEL_RE);
			Hashtable eosAvailOSNTbl = new Hashtable();
			boolean eosOsnErrors = getAvailByOSN(eosAvailOSNTbl, mdlEOSAvailVctR, true, CHECKLEVEL_RE);
			addDebug("mdlEOSAvailChecks eosOsnErrors " + eosOsnErrors + " eosAvailOSNTbl.keys "
					+ eosAvailOSNTbl.keySet() + " loOsnErrors " + loOsnErrors + " loAvailOSNTbl.keys "
					+ loAvailOSNTbl.keySet()/*
											 * +" mesloOsnErrors "+
											 * mesLoOsnErrors+" mesloAvailOSNTbl.keys "
											 * +mesLoAvailOSNTbl.keySet()
											 */);
			// Change 20111216 215.00 COUNTRYLIST IN C:AVAIL COUNTRYLIST
			// OSN:XCC_LIST W W E*2 {LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST}
			// includes a country that does not have a "Last Order"
			if (!loOsnErrors && !eosOsnErrors) {
				// only do this check if no errors were found building the OSN
				// buckets
				checkAvailCtryByOSN(eosAvailOSNTbl, loAvailOSNTbl, "MISSING_LO_OSNCTRY_ERR", null, true,
						getCheckLevel(checklvl, mdlItem, "ANNDATE"));
			}
			// if(mdlMesLOAvailVctC != null && mdlMesLOAvailVctC.size() > 0 &&
			// !mesLoOsnErrors && !eosOsnErrors){
			// // only do this check if no errors were found building the OSN
			// buckets
			// checkAvailCtryByOSN(eosAvailOSNTbl,mesLoAvailOSNTbl,
			// "MISSING_LO_OSNCTRY_ERR", null, true,
			// getCheckLevel(checklvl,mdlItem,"ANNDATE"));
			// }
			loAvailOSNTbl.clear();
			// mesLoAvailOSNTbl.clear();
			eosAvailOSNTbl.clear();
		}

		// 218.00 WHEN "RFA" (RFA) = R: AVAIL AVAILANNTYPE
		Vector tmpMdlEOSVctR = new Vector(mdlEOSAvailVctR);
		// remove any that are not AVAILANNTYPE=RFA
		removeNonRFAAVAIL(tmpMdlEOSVctR);

		for (int i = 0; i < tmpMdlEOSVctR.size(); i++) {
			EntityItem avail = (EntityItem) tmpMdlEOSVctR.elementAt(i);
			// 219.00 ANNOUNCEMENT S R: + AVAILANNA-d MODEL ANNOUNCEMENT
			Vector annVct = PokUtils.getAllLinkedEntities(avail, "AVAILANNA", "ANNOUNCEMENT");

			for (int ie = 0; ie < annVct.size(); ie++) {
				EntityItem annItem = (EntityItem) annVct.elementAt(ie);

				String anntypeFlag = PokUtils.getAttributeFlagValue(annItem, "ANNTYPE");
				addDebug("mdlEOSAvailChecks " + annItem.getKey() + " anntypeFlag " + anntypeFlag);
				// 220.00 IF ANNTYPE <> "End Of Life - Discontinuance of
				// service" (13)
				// 221.00 CountOf = 0 E E E {LD: AVAIL} {NDN: R:AVAIL} must not
				// be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
				if (!ANNTYPE_EOLDS.equals(anntypeFlag)) {
					// MUST_NOT_BE_IN_ERR2= {0} must not be in {1}
					args[0] = getLD_NDN(avail);
					args[1] = getLD_NDN(annItem);
					createMessage(CHECKLEVEL_E, "MUST_NOT_BE_IN_ERR2", args);
					continue;
				}
				// old222.00 ELSE ANNDATE <= MODEL WITHDRAWDATE W W E {LD:
				// ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must be earlier than the
				// {LD: MODEL} {LD: WITHDRAWDATE} {WITHDRAWDATE}
				// checkCanNotBeLater(annItem, "ANNDATE", mdlItem,
				// "WITHDRAWDATE", checklvl);
				// Change 20111212 222.00 ELSE ANNDATE => MODEL WITHDRAWDATE W W
				// E
				// {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must not be earlier
				// than the {LD: MODEL} {LD: WITHDRAWDATE} {WITHDRAWDATE}
				checkCanNotBeEarlier(annItem, "ANNDATE", mdlItem, "WITHDRAWDATE", checklvl);
				// Add 20111213 222.10 ANNDATE <= R: AVAIL EFFECTIVEDATE W W E
				// {LD: ANNOUNCEMENT} {LD: ANNDATE} {ANNDATE} must not be later
				// than the {LD: MODEL} {LD: AVAIL {NDN: R: AVAIL}
				checkCanNotBeLater4(annItem, "ANNDATE", avail, "EFFECTIVEDATE", checklvl);
			}

			annVct.clear();
		}
		// 223.00 END 220
		// 224.00 END 218
		// 225.00 END 213
		tmpMdlEOSVctR.clear();
	}

	/************************************
	 * Get all avails needed for the tests
	 * 
	 * @param mdlitem
	 * @param statusFlag
	 * @param modelCOFCAT
	 */
	private void getAvails(EntityItem mdlitem, String statusFlag, String modelCOFCAT) {
		// boolean isSvcModel = SERVICE.equals(modelCOFCAT);
		boolean isSwModel = SOFTWARE.equals(modelCOFCAT);
		boolean isHwModel = HARDWARE.equals(modelCOFCAT);
		Vector availVct = PokUtils.getAllLinkedEntities(mdlitem, "MODELAVAIL", "AVAIL");
		// 5.00 AVAIL A MODELAVAIL-d MODEL AVAIL
		// 2016-01-26 Change Column G 6.00 WHEN AVAILTYPE = "Planned
		// Availability" or "MES Planned Availability"
		mdlPlaAvailVctA = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", PLANNEDAVAIL);
		// mdlMesPlaAvailVctA = PokUtils.getEntitiesWithMatchedAttr(availVct,
		// "AVAILTYPE", MESPLANNEDAVAIL);

		// 18.00 AVAIL B MODELAVAIL-d MODEL AVAIL
		// 19.00 WHEN AVAILTYPE = "First Order"
		mdlFOAvailVctB = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", FIRSTORDERAVAIL);

		// 27.00 AVAIL C MODELAVAIL-d MODEL AVAIL
		// 2016-01-26 Change Column G 28.00 WHEN AVAILTYPE = "Last Order" or
		// "MES Last Order"
		mdlLOAvailVctC = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", LASTORDERAVAIL);
		// mdlMesLOAvailVctC = PokUtils.getEntitiesWithMatchedAttr(availVct,
		// "AVAILTYPE", MESLASTORDERAVAIL);
		// 200.00 AVAIL P MODELAVAIL-d MODEL AVAIL
		// 201.00 WHEN AVAILTYPE = "End of Marketing" (200)
		mdlEOMAvailVctP = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", EOMAVAIL);

		// 212.00 AVAIL R MODELAVAIL-d AVAILTYPE = "End of Service" (151)
		mdlEOSAvailVctR = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", EOSAVAIL);

		addDebug("getAvails MODELAVAIL all availVct: " + availVct.size() + " plannedavail: " + mdlPlaAvailVctA.size() +
		// " mesplannedavail: "+mdlMesPlaAvailVctA.size()+
				" firstorder: " + mdlFOAvailVctB.size() + " lastorder: " + mdlLOAvailVctC.size() +
				// " meslastorder: "+mdlMesLOAvailVctC.size()+
				" eom: " + mdlEOMAvailVctP.size() + " eos: " + mdlEOSAvailVctR.size());

		mdlPlaAvailCtryTblA = getAvailByCountry(mdlPlaAvailVctA, getCheck_W_W_E(statusFlag));
		addDebug("getAvails MODELAVAIL mdlPlaAvailCtryTblA: " + mdlPlaAvailCtryTblA.keySet());
		// mdlMesPlaAvailCtryTblA = getAvailByCountry(mdlMesPlaAvailVctA,
		// getCheck_W_W_E(statusFlag));
		// addDebug("getAvails MODELAVAIL mdlMesPlaAvailCtryTblA: "
		// +mdlMesPlaAvailCtryTblA.keySet());

		mdlLOAvailCtryTblC = getAvailByCountry(mdlLOAvailVctC, getCheck_W_W_E(statusFlag));
		addDebug("getAvails MODELAVAIL mdlLOAvailCtryTblC: " + mdlLOAvailCtryTblC.keySet());
		// mdlMesLOAvailCtryTblC = getAvailByCountry(mdlMesLOAvailVctC,
		// getCheck_W_W_E(statusFlag));
		// addDebug("getAvails MODELAVAIL mdlMesLOAvailCtryTblC: "
		// +mdlMesLOAvailCtryTblC.keySet());

		if (isSwModel) {
			// 105.00 AVAIL H SWPRODSTRUCT: SWPRODSTRUCTAVAIL-d: AVAIL TM SW
			// AVAIL
			// 2016-01-26 Change Column G 106.00 WHEN AVAILTYPE = "Planned
			// Availability" or "MES Planned Availability"
			EntityGroup swpsGrp = m_elist.getEntityGroup("SWPRODSTRUCT");
			availVct = PokUtils.getAllLinkedEntities(swpsGrp, "SWPRODSTRUCTAVAIL", "AVAIL");
			swpsPlaAvailVctH = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", PLANNEDAVAIL);
			swpsMesPlaAvailVctH = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", MESPLANNEDAVAIL);

			// 117.00 AVAIL K SWPRODSTRUCT: SWPRODSTRUCTAVAIL-d: AVAIL TM SW
			// AVAIL
			// 2016-01-26 Change Column G 118.00 WHEN AVAILTYPE = "Last Order"
			// or "MES Last Order"
			swpsLOAvailVctK = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", LASTORDERAVAIL);
			swpsMesLOAvailVctK = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", MESLASTORDERAVAIL);

			addDebug("getAvails SWPRODSTRUCT: SWPRODSTRUCTAVAIL-d: AVAIL all avails:" + availVct.size()
					+ " plannedavail: " + swpsPlaAvailVctH.size() + " mesplannedavail: " + swpsMesPlaAvailVctH.size()
					+ " lastorder: " + swpsLOAvailVctK.size() + " meslastorder: " + swpsMesLOAvailVctK.size());
		}

		if (isHwModel) {
			// 52.00 AVAIL D PRODSTRUCT: OOFAVAIL-d: AVAIL TMF AVAIL
			// 2016-01-26 Change Column G 53.00 WHEN AVAILTYPE = "Planned
			// Availability" or "MES Planned Availability"
			EntityGroup psGrp = m_elist.getEntityGroup("PRODSTRUCT");
			availVct = PokUtils.getAllLinkedEntities(psGrp, "OOFAVAIL", "AVAIL");
			psPlaAvailVctD = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", PLANNEDAVAIL);
			psMesPlaAvailVctD = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", MESPLANNEDAVAIL);

			// 64.00 AVAIL E PRODSTRUCT: OOFAVAIL-d: AVAIL TMF AVAIL
			// 65.00 WHEN AVAILTYPE = "Last Order"
			psLOAvailVctE = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", LASTORDERAVAIL);
			psMesLOAvailVctE = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", MESLASTORDERAVAIL);

			addDebug("getAvails PRODSTRUCT: OOFAVAIL-d: AVAIL all avails:" + availVct.size() + " plannedavail: "
					+ psPlaAvailVctD.size() + " mesplannedavail: " + psMesPlaAvailVctD.size() + " lastorder: "
					+ psLOAvailVctE.size() + " meslastorder: " + psMesLOAvailVctE.size());
		}

		availVct.clear();
	}

	/*****************
	 * 
	 * @param mdlItem
	 * @param statusFlag
	 * @param modelCOFCAT
	 * @throws SQLException
	 * @throws MiddlewareException
	 * 
	 *             200.00 AVAIL P MODELAVAIL-d MODEL AVAIL 201.00 WHEN AVAILTYPE
	 *             = "End of Marketing" (200) 202.00 EFFECTIVEDATE <= MODEL
	 *             WITHDRAWDATE W W E {LD: AVAIL} {NDN: AVAIL} must not be later
	 *             than the {LD: MODEL} {LD: WITHDRAWDATE} {WITHDRAWDATE}
	 *             xx203.00 COUNTRYLIST "in aggregate G" A:AVAIL COUNTRYLIST W W
	 *             E*2 {LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a
	 *             country that does not have a "Planned Availability" Change
	 *             20111216 203.00 COUNTRYLIST "IN aggregate G" A:AVAIL
	 *             COUNTRYLIST OSN:XCC_LIST W W E*2 {LD: AVAIL} {NDN: AVAIL}
	 *             {LD: COUNTRYLIST} includes a country that does not have a
	 *             "Planned Availability" 204.00 WHEN "RFA" (RFA) = P: AVAIL
	 *             AVAILANNTYPE 205.00 ANNOUNCEMENT Q P: + AVAILANNA-d MODEL
	 *             ANNOUNCEMENT 206.00 IF ANNTYPE <> "End Of Life - Withdrawal
	 *             from mktg" (14) 207.00 CountOf = 0 E E E {LD: AVAIL} {NDN:
	 *             P:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN:
	 *             ANNOUNCEMENT} 208.00 ELSE ANNDATE <= MODEL WITHDRAWDATE W W E
	 *             {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must be earlier than
	 *             the {LD: MODEL} {LD: WITHDRAWDATE} {WITHDRAWDATE} Add
	 *             20111213 208.10 ANNDATE <= P: AVAIL EFFECTIVEDATE W W E {LD:
	 *             ANNOUNCEMENT} {LD: ANNDATE} {ANNDATE} must not be later than
	 *             the {LD: MODEL} {LD: AVAIL {NDN: P: AVAIL} 209.00 END 206
	 *             210.00 END 204 211.00 END 201
	 */
	private void doModelEOMAvailChecks(EntityItem mdlItem, String statusFlag, String modelCOFCAT)
			throws SQLException, MiddlewareException {
		int checklvl = getCheck_W_W_E(statusFlag);

		addHeading(3, "Model End of Marketing Avail Checks:");
		// 200.00 AVAIL P MODELAVAIL-d MODEL AVAIL
		// 201.00 WHEN AVAILTYPE = "End of Marketing" (200)
		if (mdlEOMAvailVctP.size() > 0) {
			for (int i = 0; i < mdlEOMAvailVctP.size(); i++) {
				EntityItem avail = (EntityItem) mdlEOMAvailVctP.elementAt(i);
				// 202.00 EFFECTIVEDATE <= MODEL WITHDRAWDATE W W E {LD: AVAIL}
				// {NDN: AVAIL} must not be later than the {LD: MODEL} {LD:
				// WITHDRAWDATE} {WITHDRAWDATE}
				checkCanNotBeLater(avail, "EFFECTIVEDATE", mdlItem, "WITHDRAWDATE", checklvl);

				// old203.00 COUNTRYLIST "in aggregate G" A:AVAIL COUNTRYLIST W
				// W E*2 {LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a
				// country that does not have a "Planned Availability"
				// checkPlannedAvailForCtryExists(avail,
				// mdlPlaAvailCtryTblA.keySet(),
				// getCheckLevel(checklvl,mdlItem,"ANNDATE"));
			}

			Hashtable plaAvailOSNTbl = new Hashtable();
			boolean plaOsnErrors = getAvailByOSN(plaAvailOSNTbl, mdlPlaAvailVctA, true, CHECKLEVEL_RE);
			// MES
			// Hashtable mesPlaAvailOSNTbl = new Hashtable();
			// boolean mesPlaOsnErrors =
			// getAvailByOSN(mesPlaAvailOSNTbl,mdlMesPlaAvailVctA,true,CHECKLEVEL_RE);
			Hashtable eomAvailOSNTbl = new Hashtable();
			boolean eomOsnErrors = getAvailByOSN(eomAvailOSNTbl, mdlEOMAvailVctP, true, CHECKLEVEL_RE);
			addDebug("mdlEOMAvailChecks eomOsnErrors " + eomOsnErrors + " eomAvailOSNTbl.keys "
					+ eomAvailOSNTbl.keySet() + " plaOsnErrors " + plaOsnErrors + " plaAvailOSNTbl.keys "
					+ plaAvailOSNTbl.keySet()/*
												 * +" mesplaOsnErrors "+
												 * mesPlaOsnErrors+" mesplaAvailOSNTbl.keys "
												 * +mesPlaAvailOSNTbl.keySet()
												 */);
			// Change 20111216 203.00 COUNTRYLIST "IN aggregate G" A:AVAIL
			// COUNTRYLIST OSN:XCC_LIST W W E*2
			// {LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a country
			// that does not have a "Planned Availability"
			if (!plaOsnErrors && !eomOsnErrors) {
				// only do this check if no errors were found building the OSN
				// buckets
				checkAvailCtryByOSN(eomAvailOSNTbl, plaAvailOSNTbl, "MISSING_PLA_OSNCTRY_ERR", null, true,
						getCheckLevel(checklvl, mdlItem, "ANNDATE"));
			}
			// if(mdlMesPlaAvailVctA != null && mdlMesPlaAvailVctA.size() > 0 &&
			// !mesPlaOsnErrors && !eomOsnErrors){
			// // only do this check if no errors were found building the OSN
			// buckets
			// checkAvailCtryByOSN(eomAvailOSNTbl,mesPlaAvailOSNTbl,
			// "MISSING_PLA_OSNCTRY_ERR", null, true,
			// getCheckLevel(checklvl,mdlItem,"ANNDATE"));
			// }
			plaAvailOSNTbl.clear();
			// mesPlaAvailOSNTbl.clear();
			eomAvailOSNTbl.clear();
		}

		// 204.00 WHEN "RFA" (RFA) = P: AVAIL AVAILANNTYPE
		Vector tmpMdlEOMVctP = new Vector(mdlEOMAvailVctP);
		// remove any that are not AVAILANNTYPE=RFA
		removeNonRFAAVAIL(tmpMdlEOMVctP);

		for (int i = 0; i < tmpMdlEOMVctP.size(); i++) {
			EntityItem avail = (EntityItem) tmpMdlEOMVctP.elementAt(i);
			// 205.00 ANNOUNCEMENT Q P: + AVAILANNA-d MODEL ANNOUNCEMENT
			Vector annVct = PokUtils.getAllLinkedEntities(avail, "AVAILANNA", "ANNOUNCEMENT");

			for (int ie = 0; ie < annVct.size(); ie++) {
				EntityItem annItem = (EntityItem) annVct.elementAt(ie);

				String anntypeFlag = PokUtils.getAttributeFlagValue(annItem, "ANNTYPE");
				addDebug("checkAvails " + annItem.getKey() + " anntypeFlag " + anntypeFlag);
				// 206.00 IF ANNTYPE <> "End Of Life - Withdrawal from mktg"
				// (14)
				// 207.00 CountOf = 0 E E E {LD: AVAIL} {NDN: P:AVAIL} must not
				// be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
				if (!ANNTYPE_EOL.equals(anntypeFlag)) {
					// MUST_NOT_BE_IN_ERR2= {0} must not be in {1}
					args[0] = getLD_NDN(avail);
					args[1] = getLD_NDN(annItem);
					createMessage(CHECKLEVEL_E, "MUST_NOT_BE_IN_ERR2", args);
					continue;
				}
				// 208.00 ELSE ANNDATE <= MODEL WITHDRAWDATE W W E
				// {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must be earlier than
				// the {LD: MODEL} {LD: WITHDRAWDATE} {WITHDRAWDATE}
				checkCanNotBeLater(annItem, "ANNDATE", mdlItem, "WITHDRAWDATE", checklvl);

				// Add 20111213 208.10 ANNDATE <= P: AVAIL EFFECTIVEDATE W W E
				// {LD: ANNOUNCEMENT} {LD: ANNDATE} {ANNDATE} must not be later
				// than the {LD: MODEL} {LD: AVAIL {NDN: P: AVAIL}
				checkCanNotBeLater4(annItem, "ANNDATE", avail, "EFFECTIVEDATE", checklvl);
			}

			annVct.clear();
		}
		// 209.00 END 206
		// 210.00 END 204
		// 211.00 END 201
		tmpMdlEOMVctP.clear();
	}

	/***************
	 * 
	 * @param mdlItem
	 * @param statusFlag
	 * @param modelCOFCAT
	 * @throws SQLException
	 * @throws MiddlewareException
	 *             27 AVAIL C MODELAVAIL-d MODEL AVAIL 28 WHEN AVAILTYPE = "Last
	 *             Order" 29 EFFECTIVEDATE <= MODEL WTHDRWEFFCTVDATE W W E {LD:
	 *             AVAIL} {NDN: AVAIL} can not be later than the {LD: MODEL}
	 *             {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE} 30 COUNTRYLIST IN
	 *             A: AVAIL COUNTRYLIST W W E*2 {LD: AVAIL} {NDN: AVAIL} {LD:
	 *             COUNTRYLIST} includes a country that this is not available in
	 *             delete 31 ANNCODENAME IN "E: AVAIL | K: AVAIL" ANNCODENAME W
	 *             W E MODEL Withdrawal must withdraw at least one Feature or
	 *             SWFeature {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must include
	 *             one or more features. 32 IF COUNTRYLIST IN D: AVAIL
	 *             COUNTRYLIST 33 AND "Initial" = PRODSTRUCT ORDERCODE 34 THEN
	 *             COUNTRYLIST IN E: AVAIL COUNTRYLIST W RW RE ALL Features must
	 *             be withdrawn in all countries that the MODEL is being
	 *             withdrawn {LD: FEATURE} (NDN: E: FEATURE} must have a "Last
	 *             Order" for all countries in the {LD: MODEL} {LD: AVAIL} {C:
	 *             AVAIL} 35 IF COUNTRYLIST IN H: AVAIL COUNTRYLIST 36 THEN
	 *             COUNTRYLIST IN K: AVAIL COUNTRYLIST W RW RE ALL SW Features
	 *             must be withdrawn in all countries that the MODEL is being
	 *             withdrawn {LD: SWFEATURE} (NDN: K: SWFEATURE} must have a
	 *             "Last Order" for all countries in the {LD: MODEL} {LD: AVAIL}
	 *             {C: AVAIL} delete 37 IF COUNTRYLIST IN J: AVAIL COUNTRYLIST
	 *             delete 38 THEN COUNTRYLIST IN L: AVAIL COUNTRYLIST W RW RE
	 *             ALL SVC Features must be withdrawn in all countries that the
	 *             MODEL is being withdrawn {LD: SVCFEATURE} (NDN: L:
	 *             SVCFEATURE} must have a "Last Order" for all countries in the
	 *             {LD: MODEL} {LD: AVAIL} {C: AVAIL} Add 38.2 WHEN "RFA" (RFA)
	 *             = C: AVAIL AVAILANNTYPE 39 ANNOUNCEMENT C: + AVAILANNA 40
	 *             WHEN ANNTYPE = "End Of Life - Withdrawal from mktg" (14) 41
	 *             ANNDATE <= MODEL WITHDRAWDATE W W E {LD: ANNOUNCEMENT} {LD:
	 *             ANNDATE} {ANNDATE} can not be later than the {LD: MODEL} {LD:
	 *             WITHDRAWDATE} {WITHDRAWDATE} 42 END 40 Add 42.2 END 38.2 43
	 *             END 28
	 * 
	 *             27.00 AVAIL C MODELAVAIL-d MODEL AVAIL 28.00 WHEN AVAILTYPE =
	 *             "Last Order" 29.00 EFFECTIVEDATE <= MODEL WTHDRWEFFCTVDATE W
	 *             W E {LD: AVAIL} {NDN: AVAIL} must not be later than the {LD:
	 *             MODEL} {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE} xx30.00
	 *             COUNTRYLIST "in aggregate G" A: AVAIL COUNTRYLIST W W E*2
	 *             {LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a Country
	 *             that does not have a "Planned Availability" xx32.00 IF
	 *             COUNTRYLIST Match D: AVAIL COUNTRYLIST has PRODSTRUCT in this
	 *             Country 30.00 COUNTRYLIST "IN aggregate G" A: AVAIL
	 *             COUNTRYLIST OSN:XCC_LIST W W E*2 A: MODEL Planned Avail {LD:
	 *             AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a Country that
	 *             does not have a "Planned Availability" 32.00 IF COUNTRYLIST
	 *             Match D: AVAIL COUNTRYLIST OSN:XCC_LIST "D: PRODSTRUCT
	 *             Planned Availhas PRODSTRUCT in this Country" 33.00 AND
	 *             "Initial" = PRODSTRUCT ORDERCODE 33.20 AND FCTYPE = "Primary
	 *             FC (100) |""Secondary FC"" (110)" RPQ logic 34.00 THEN
	 *             TheMatch IN E: AVAIL COUNTRYLIST OSN:XCC_LIST W RW RE "E:
	 *             PRODSTRUCT Last order ALL Features that are Initial must be
	 *             withdrawn in all countries that the MODEL is being withdrawn"
	 *             {LD: FEATURE} (NDN: E: FEATURE} must have a "Last Order" for
	 *             all countries in the {LD: MODEL} {LD: AVAIL} {C: AVAIL} AND
	 *             PRODSTRUCT SameParent D: AVAIL E: AVAIL Note: the AVAILs must
	 *             have the same parent PRODSTRUCT 34.10 EFFECTIVEDATE => E:
	 *             AVAIL EFFECTIVEDATE OSN:XCC_LIST W RW RE "E: PRODSTRUCT Last
	 *             order Features must be withdrawn on or before MODEL is
	 *             withdrawn" {LD: PRODSTRUCT} (NDN: E: PRODSTRUCT} {LD: AVAIL}
	 *             {E: AVAIL} must be no later than the {LD: MODEL} {LD:AVAIL}
	 *             34.20 END 34.00 35.00 IF COUNTRYLIST Match H: AVAIL
	 *             COUNTRYLIST OSN:XCC_LIST "S: SWPRODSTRUCT Planned Avail has
	 *             SWPRODSTRUCT in this Country" 36.00 THEN TheMatch IN K: AVAIL
	 *             COUNTRYLIST OSN:XCC_LIST W RW RE ALL SW Features must be
	 *             withdrawn in all countries that the MODEL is being withdrawn
	 *             {LD: SWFEATURE} (NDN: K: SWFEATURE} must have a "Last Order"
	 *             for all countries in the {LD: MODEL} {LD: AVAIL} {C: AVAIL}
	 *             36.02 AND SWPRODSTRUCT SameParent H: AVAIL K: AVAIL Note: the
	 *             AVAILs must have the same parent SWPRODSTRUCT 36.10
	 *             EFFECTIVEDATE => K: AVAIL EFFECTIVEDATE OSN:XCC_LIST W RW RE
	 *             SW Features must be withdrawn on or before MODEL is withdrawn
	 *             {LD: SWPRODSTRUCT} (NDN: K: SWPRODSTRUCT} {LD: AVAIL} {E:
	 *             AVAIL} must be no later than the {LD: MODEL} {LD:AVAIL} 36.20
	 *             END 36.00
	 * 
	 *             38.2 WHEN "RFA" (RFA) = C: AVAIL AVAILANNTYPE 39.00
	 *             ANNOUNCEMENT N C: + AVAILANNA 40.00 WHEN ANNTYPE = "End Of
	 *             Life - Withdrawal from mktg" (14) 41.00 ANNDATE <= MODEL
	 *             WITHDRAWDATE W W E {LD: ANNOUNCEMENT} {LD: ANNDATE} {ANNDATE}
	 *             must not be later than the {LD: MODEL} {LD: WITHDRAWDATE}
	 *             {WITHDRAWDATE} 41.20 ANNDATE <= C: AVAIL EFFECTIVEDATE W W E
	 *             {LD: ANNOUNCEMENT} {LD: ANNDATE} {ANNDATE} must not be later
	 *             than the {LD: MODEL} {LD: AVAIL {NDN: C: AVAIL} 42.00 END 40
	 *             42.2 END 38.2 43.00 END 28
	 * 
	 */
	private void doModelLOAvailChecks(EntityItem mdlItem, String statusFlag, String modelCOFCAT)
			throws SQLException, MiddlewareException {
		int checklvl = getCheck_W_W_E(statusFlag);

		// boolean isSvcModel = SERVICE.equals(modelCOFCAT);
		boolean isSwModel = SOFTWARE.equals(modelCOFCAT);
		boolean isHwModel = HARDWARE.equals(modelCOFCAT);

		ArrayList loCodeNameList = null;
		if (isHwModel) {
			loCodeNameList = getAttributeAsList(psLOAvailVctE, "ANNCODENAME", checklvl); // E:AVAIL
		} else if (isSwModel) {
			loCodeNameList = getAttributeAsList(swpsLOAvailVctK, "ANNCODENAME", checklvl); // K:
																							// AVAIL
		}

		addHeading(3, "Model Last Order Avail Checks:");
		// 27.00 AVAIL C MODELAVAIL-d MODEL AVAIL
		// 28.00 WHEN AVAILTYPE = "Last Order"
		if (mdlLOAvailVctC.size() > 0) {
			for (int i = 0; i < mdlLOAvailVctC.size(); i++) {
				EntityItem avail = (EntityItem) mdlLOAvailVctC.elementAt(i);
				// 29.00 EFFECTIVEDATE <= MODEL WTHDRWEFFCTVDATE W W E {LD:
				// AVAIL} {NDN: AVAIL} can not be later than the {LD: MODEL}
				// {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
				checkCanNotBeLater(avail, "EFFECTIVEDATE", mdlItem, "WTHDRWEFFCTVDATE", checklvl);

				// old30.00 COUNTRYLIST "in aggregate G" A: AVAIL COUNTRYLIST W
				// W E*2
				// checkPlannedAvailForCtryExists(avail,
				// mdlPlaAvailCtryTblA.keySet(),
				// getCheckLevel(checklvl,mdlItem,"ANNDATE"));
			}
			Hashtable plaAvailOSNTbl = new Hashtable();
			boolean plaOsnErrors = getAvailByOSN(plaAvailOSNTbl, mdlPlaAvailVctA, true, CHECKLEVEL_RE);
			addDebug("doModelLOAvailChecks  plaOsnErrors " + plaOsnErrors + " plaAvailOSNTbl.keys "
					+ plaAvailOSNTbl.keySet());

			Hashtable loAvailOSNTbl = new Hashtable();
			boolean loOsnErrors = getAvailByOSN(loAvailOSNTbl, this.mdlLOAvailVctC, true, CHECKLEVEL_RE);
			addDebug("doModelLOAvailChecks  loOsnErrors " + loOsnErrors + " loAvailOSNTbl.keys "
					+ loAvailOSNTbl.keySet());
			// 30.00 COUNTRYLIST "IN aggregate G" A: AVAIL COUNTRYLIST
			// OSN:XCC_LIST W W E*2
			// {LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a Country
			// that does not have a "Planned Availability"
			if (!plaOsnErrors && !loOsnErrors) {
				// only do this check if no errors were found building the OSN
				// buckets
				checkAvailCtryByOSN(loAvailOSNTbl, plaAvailOSNTbl, "MISSING_PLA_OSNCTRY_ERR", null, true,
						getCheckLevel(checklvl, mdlItem, "ANNDATE"));
			}
			plaAvailOSNTbl.clear();
			loAvailOSNTbl.clear();
		}
		if (loCodeNameList != null) {
			loCodeNameList.clear();
		}

		if (isHwModel) {
			// 32.00 IF COUNTRYLIST Match D: AVAIL COUNTRYLIST OSN:XCC_LIST "D:
			// PRODSTRUCT Planned Avail has PRODSTRUCT in this Country"
			// 33.00 AND "Initial" = PRODSTRUCT ORDERCODE
			// 33.20 AND FCTYPE = "Primary FC (100) | ""Secondary FC"" (110)"
			// 34.00 THEN TheMatch IN E: AVAIL COUNTRYLIST OSN:XCC_LIST W RW RE
			// "E: PRODSTRUCT Last order ALL Features that are Initial must be
			// withdrawn in all countries that the MODEL is being withdrawn"
			// {LD: FEATURE} (NDN: E: FEATURE} must have a "Last Order" for all
			// countries in the {LD: MODEL} {LD: AVAIL} {C: AVAIL}
			// AND PRODSTRUCT SameParent D: AVAIL E: AVAIL Note: the AVAILs must
			// have the same parent PRODSTRUCT
			// 34.10 EFFECTIVEDATE => E:AVAIL EFFECTIVEDATE OSN:XCC_LIST W RW RE
			// "E: PRODSTRUCT Last order Features must be withdrawn on or before
			// MODEL is withdrawn" {LD: PRODSTRUCT} (NDN: E: PRODSTRUCT} {LD:
			// AVAIL} {E: AVAIL} must be no later than the {LD: MODEL}
			// {LD:AVAIL}
			matchPsModelLastOrderAvail(statusFlag, m_elist.getEntityGroup("PRODSTRUCT"), "FEATURE", "OOFAVAIL");
			// 34.20 END 34.00
		}

		if (isSwModel) {
			// 35.00 IF COUNTRYLIST Match H: AVAIL COUNTRYLIST OSN:XCC_LIST "S:
			// SWPRODSTRUCT Planned Avail has SWPRODSTRUCT in this Country"
			// 36.00 THEN TheMatch IN K: AVAIL COUNTRYLIST OSN:XCC_LIST W RW RE
			// ALL SW Features must be withdrawn in all countries that the MODEL
			// is being withdrawn {LD: SWFEATURE} (NDN: K: SWFEATURE} must have
			// a "Last Order" for all countries in the {LD: MODEL} {LD: AVAIL}
			// {C: AVAIL}
			// 36.02 AND SWPRODSTRUCT SameParent H: AVAIL K: AVAIL
			// 36.10 EFFECTIVEDATE => K:AVAIL EFFECTIVEDATE OSN:XCC_LIST W RW RE
			// SW Features must be withdrawn on or before MODEL is withdrawn
			// {LD: SWPRODSTRUCT} (NDN: K: SWPRODSTRUCT} {LD: AVAIL} {E: AVAIL}
			// must be no later than the {LD: MODEL} {LD:AVAIL}
			matchPsModelLastOrderAvail(statusFlag, m_elist.getEntityGroup("SWPRODSTRUCT"), "SWFEATURE",
					"SWPRODSTRUCTAVAIL");
			// 36.20 END 36.00
		}

		// 38.2 WHEN "RFA" (RFA) = C: AVAIL AVAILANNTYPE
		Vector tmpMdlLOVctC = new Vector(mdlLOAvailVctC);
		// remove any that are not AVAILANNTYPE=RFA
		removeNonRFAAVAIL(tmpMdlLOVctC);

		// 39.00 ANNOUNCEMENT C: + AVAILANNA
		// 40.00 WHEN ANNTYPE = "End Of Life - Withdrawal from mktg" (14)
		Vector annVct = PokUtils.getAllLinkedEntities(tmpMdlLOVctC, "AVAILANNA", "ANNOUNCEMENT");
		addDebug("doModelLOAvailChecks mdlLOAvailVctC " + mdlLOAvailVctC.size() + " tmpMdlLOVctC " + tmpMdlLOVctC.size()
				+ " annVct: " + annVct.size());
		annVct = PokUtils.getEntitiesWithMatchedAttr(annVct, "ANNTYPE", ANNTYPE_EOL);
		addDebug("doModelLOAvailChecks EOL annVct: " + annVct.size());
		for (int i = 0; i < annVct.size(); i++) {
			EntityItem annItem = (EntityItem) annVct.elementAt(i);
			// 41.00 ANNDATE <= MODEL WITHDRAWDATE W W E {LD: ANNOUNCEMENT} {LD:
			// ANNDATE} {ANNDATE} can not be later than the {LD: MODEL} {LD:
			// WITHDRAWDATE} {WITHDRAWDATE}
			checkCanNotBeLater(annItem, "ANNDATE", mdlItem, "WITHDRAWDATE", getCheck_W_W_E(statusFlag));
			// Add 20111213 41.20 ANNDATE <= C: AVAIL EFFECTIVEDATE W W E
			// {LD: ANNOUNCEMENT} {LD: ANNDATE} {ANNDATE} must not be later than
			// the {LD: MODEL} {LD: AVAIL {NDN: C: AVAIL}
			String annCodeName = PokUtils.getAttributeFlagValue(annItem, "ANNCODENAME");
			ArrayList AnnCntryList = new ArrayList();
			this.getCountriesAsList(annItem, AnnCntryList, CHECKLEVEL_E);
			for (int c = 0; c < tmpMdlLOVctC.size(); c++) {
				EntityItem loavail = (EntityItem) tmpMdlLOVctC.elementAt(c);
				String avail_annCodeName = PokUtils.getAttributeFlagValue(loavail, "ANNCODENAME");
				if (avail_annCodeName != null && avail_annCodeName.equals(annCodeName)) {// match
																							// anncodename
																							// -
																							// 2013-01-21
					ArrayList loavailCntryList = new ArrayList();
					this.getCountriesAsList(loavail, loavailCntryList, CHECKLEVEL_E);
					loavailCntryList.retainAll(AnnCntryList);
					addDebug("doModelLOAvailChecks: Loavail cntry intersect Ann cntry: " + loavailCntryList);
					if (loavailCntryList.size() > 0)
						checkCanNotBeLater4(annItem, "ANNDATE", loavail, "EFFECTIVEDATE", getCheck_W_W_E(statusFlag));
				}
			}
		}
		annVct.clear();
		// 42.00 END 40
		// 42.2 END 38.2
		// 43.00 END 28
		tmpMdlLOVctC.clear();
	}

	private void doModelLOAndMesLOAvailChecks(String checkTitle, Vector mdlLOOrMesLOAvailVctC, String availType,
			EntityItem mdlItem, String statusFlag, String modelCOFCAT) throws SQLException, MiddlewareException {
		int checklvl = getCheck_W_W_E(statusFlag);

		// boolean isSvcModel = SERVICE.equals(modelCOFCAT);
		boolean isSwModel = SOFTWARE.equals(modelCOFCAT);
		boolean isHwModel = HARDWARE.equals(modelCOFCAT);

		ArrayList loCodeNameList = null;
		ArrayList mesloCodeNameList = null;
		if (isHwModel) {
			loCodeNameList = getAttributeAsList(psLOAvailVctE, "ANNCODENAME", checklvl); // E:AVAIL
			mesloCodeNameList = getAttributeAsList(psMesLOAvailVctE, "ANNCODENAME", checklvl); // E:AVAIL
		} else if (isSwModel) {
			loCodeNameList = getAttributeAsList(swpsLOAvailVctK, "ANNCODENAME", checklvl); // K:
																							// AVAIL
			mesloCodeNameList = getAttributeAsList(swpsMesLOAvailVctK, "ANNCODENAME", checklvl); // K:
																									// AVAIL
		}

		addHeading(3, checkTitle);
		// 27.00 AVAIL C MODELAVAIL-d MODEL AVAIL
		// 28.00 WHEN AVAILTYPE = "Last Order"
		if (mdlLOOrMesLOAvailVctC.size() > 0) {
			for (int i = 0; i < mdlLOOrMesLOAvailVctC.size(); i++) {
				EntityItem avail = (EntityItem) mdlLOOrMesLOAvailVctC.elementAt(i);
				// 29.00 EFFECTIVEDATE <= MODEL WTHDRWEFFCTVDATE W W E {LD:
				// AVAIL} {NDN: AVAIL} can not be later than the {LD: MODEL}
				// {LD: WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE}
				checkCanNotBeLater(avail, "EFFECTIVEDATE", mdlItem, "WTHDRWEFFCTVDATE", checklvl);

				// old30.00 COUNTRYLIST "in aggregate G" A: AVAIL COUNTRYLIST W
				// W E*2
				// checkPlannedAvailForCtryExists(avail,
				// mdlPlaAvailCtryTblA.keySet(),
				// getCheckLevel(checklvl,mdlItem,"ANNDATE"));
			}
			Hashtable plaAvailOSNTbl = new Hashtable();
			boolean plaOsnErrors = getAvailByOSN(plaAvailOSNTbl, mdlPlaAvailVctA, true, CHECKLEVEL_RE);
			addDebug("doModelLOAvailChecks  plaOsnErrors " + plaOsnErrors + " plaAvailOSNTbl.keys "
					+ plaAvailOSNTbl.keySet());
			// Hashtable mesPlaAvailOSNTbl = new Hashtable();
			// boolean mesPlaOsnErrors =
			// getAvailByOSN(mesPlaAvailOSNTbl,mdlMesPlaAvailVctA,true,CHECKLEVEL_RE);
			// addDebug("doModelLOAvailChecks mesPlaOsnErrors "+
			// mesPlaOsnErrors+" mesPlaAvailOSNTbl.keys
			// "+mesPlaAvailOSNTbl.keySet());

			Hashtable loAvailOSNTbl = new Hashtable();
			boolean loOsnErrors = getAvailByOSN(loAvailOSNTbl, mdlLOOrMesLOAvailVctC, true, CHECKLEVEL_RE);
			addDebug("doModelLOAvailChecks  loOsnErrors " + loOsnErrors + " loAvailOSNTbl.keys "
					+ loAvailOSNTbl.keySet());
			// 30.00 COUNTRYLIST "IN aggregate G" A: AVAIL COUNTRYLIST
			// OSN:XCC_LIST W W E*2
			// {LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a Country
			// that does not have a "Planned Availability"
			if (!plaOsnErrors && !loOsnErrors) {
				// only do this check if no errors were found building the OSN
				// buckets
				checkAvailCtryByOSN(loAvailOSNTbl, plaAvailOSNTbl, "MISSING_PLA_OSNCTRY_ERR", null, true,
						getCheckLevel(checklvl, mdlItem, "ANNDATE"));
			}
			// if(!mesPlaOsnErrors && !loOsnErrors &&
			// MESLASTORDERAVAIL.equals(availType)){
			// // only do this check if no errors were found building the OSN
			// buckets
			// checkAvailCtryByOSN(loAvailOSNTbl,mesPlaAvailOSNTbl,
			// "MISSING_PLA_OSNCTRY_ERR", null, true,
			// getCheckLevel(checklvl,mdlItem,"ANNDATE"));
			// }
			plaAvailOSNTbl.clear();
			// mesPlaAvailOSNTbl.clear();
			loAvailOSNTbl.clear();
		}
		if (loCodeNameList != null) {
			loCodeNameList.clear();
		}
		if (mesloCodeNameList != null) {
			mesloCodeNameList.clear();
		}
		if (isHwModel) {
			// 32.00 IF COUNTRYLIST Match D: AVAIL COUNTRYLIST OSN:XCC_LIST "D:
			// PRODSTRUCT Planned Avail has PRODSTRUCT in this Country"
			// 33.00 AND "Initial" = PRODSTRUCT ORDERCODE
			// 33.20 AND FCTYPE = "Primary FC (100) | ""Secondary FC"" (110)"
			// 34.00 THEN TheMatch IN E: AVAIL COUNTRYLIST OSN:XCC_LIST W RW RE
			// "E: PRODSTRUCT Last order ALL Features that are Initial must be
			// withdrawn in all countries that the MODEL is being withdrawn"
			// {LD: FEATURE} (NDN: E: FEATURE} must have a "Last Order" for all
			// countries in the {LD: MODEL} {LD: AVAIL} {C: AVAIL}
			// AND PRODSTRUCT SameParent D: AVAIL E: AVAIL Note: the AVAILs must
			// have the same parent PRODSTRUCT
			// 34.10 EFFECTIVEDATE => E:AVAIL EFFECTIVEDATE OSN:XCC_LIST W RW RE
			// "E: PRODSTRUCT Last order Features must be withdrawn on or before
			// MODEL is withdrawn" {LD: PRODSTRUCT} (NDN: E: PRODSTRUCT} {LD:
			// AVAIL} {E: AVAIL} must be no later than the {LD: MODEL}
			// {LD:AVAIL}
			// matchPsModelLastOrderAvail(statusFlag,
			// m_elist.getEntityGroup("PRODSTRUCT"),"FEATURE","OOFAVAIL");
			matchPsModelLastOrderAndMesLastOrderAvail(availType, mdlLOOrMesLOAvailVctC, statusFlag,
					m_elist.getEntityGroup("PRODSTRUCT"), "FEATURE", "OOFAVAIL");
			// 34.20 END 34.00
		}

		if (isSwModel) {
			// 35.00 IF COUNTRYLIST Match H: AVAIL COUNTRYLIST OSN:XCC_LIST "S:
			// SWPRODSTRUCT Planned Avail has SWPRODSTRUCT in this Country"
			// 36.00 THEN TheMatch IN K: AVAIL COUNTRYLIST OSN:XCC_LIST W RW RE
			// ALL SW Features must be withdrawn in all countries that the MODEL
			// is being withdrawn {LD: SWFEATURE} (NDN: K: SWFEATURE} must have
			// a "Last Order" for all countries in the {LD: MODEL} {LD: AVAIL}
			// {C: AVAIL}
			// 36.02 AND SWPRODSTRUCT SameParent H: AVAIL K: AVAIL
			// 36.10 EFFECTIVEDATE => K:AVAIL EFFECTIVEDATE OSN:XCC_LIST W RW RE
			// SW Features must be withdrawn on or before MODEL is withdrawn
			// {LD: SWPRODSTRUCT} (NDN: K: SWPRODSTRUCT} {LD: AVAIL} {E: AVAIL}
			// must be no later than the {LD: MODEL} {LD:AVAIL}
			// matchPsModelLastOrderAvail(statusFlag,
			// m_elist.getEntityGroup("SWPRODSTRUCT"),"SWFEATURE","SWPRODSTRUCTAVAIL");
			matchPsModelLastOrderAndMesLastOrderAvail(availType, mdlLOOrMesLOAvailVctC, statusFlag,
					m_elist.getEntityGroup("SWPRODSTRUCT"), "SWFEATURE", "SWPRODSTRUCTAVAIL");
			// 36.20 END 36.00
		}

		// 38.2 WHEN "RFA" (RFA) = C: AVAIL AVAILANNTYPE
		Vector tmpMdlLOVctC = new Vector(mdlLOOrMesLOAvailVctC);
		// remove any that are not AVAILANNTYPE=RFA
		removeNonRFAAVAIL(tmpMdlLOVctC);

		// 39.00 ANNOUNCEMENT C: + AVAILANNA
		// 40.00 WHEN ANNTYPE = "End Of Life - Withdrawal from mktg" (14)
		Vector annVct = PokUtils.getAllLinkedEntities(tmpMdlLOVctC, "AVAILANNA", "ANNOUNCEMENT");
		addDebug("doModelLOAvailChecks mdlLOAvailVctC " + mdlLOOrMesLOAvailVctC.size() + " tmpMdlLOVctC "
				+ tmpMdlLOVctC.size() + " annVct: " + annVct.size() + " availTYpe:" + availType);
		annVct = PokUtils.getEntitiesWithMatchedAttr(annVct, "ANNTYPE", ANNTYPE_EOL);
		addDebug("doModelLOAvailChecks EOL annVct: " + annVct.size());
		for (int i = 0; i < annVct.size(); i++) {
			EntityItem annItem = (EntityItem) annVct.elementAt(i);
			// 41.00 ANNDATE <= MODEL WITHDRAWDATE W W E {LD: ANNOUNCEMENT} {LD:
			// ANNDATE} {ANNDATE} can not be later than the {LD: MODEL} {LD:
			// WITHDRAWDATE} {WITHDRAWDATE}
			checkCanNotBeLater(annItem, "ANNDATE", mdlItem, "WITHDRAWDATE", getCheck_W_W_E(statusFlag));
			// Add 20111213 41.20 ANNDATE <= C: AVAIL EFFECTIVEDATE W W E
			// {LD: ANNOUNCEMENT} {LD: ANNDATE} {ANNDATE} must not be later than
			// the {LD: MODEL} {LD: AVAIL {NDN: C: AVAIL}
			String annCodeName = PokUtils.getAttributeFlagValue(annItem, "ANNCODENAME");
			ArrayList AnnCntryList = new ArrayList();
			this.getCountriesAsList(annItem, AnnCntryList, CHECKLEVEL_E);
			for (int c = 0; c < tmpMdlLOVctC.size(); c++) {
				EntityItem loavail = (EntityItem) tmpMdlLOVctC.elementAt(c);
				String avail_annCodeName = PokUtils.getAttributeFlagValue(loavail, "ANNCODENAME");
				if (avail_annCodeName != null && avail_annCodeName.equals(annCodeName)) {// match
																							// anncodename
																							// -
																							// 2013-01-21
					ArrayList loavailCntryList = new ArrayList();
					this.getCountriesAsList(loavail, loavailCntryList, CHECKLEVEL_E);
					loavailCntryList.retainAll(AnnCntryList);
					addDebug("doModelLOAvailChecks: Loavail cntry intersect Ann cntry: " + loavailCntryList);
					if (loavailCntryList.size() > 0)
						checkCanNotBeLater4(annItem, "ANNDATE", loavail, "EFFECTIVEDATE", getCheck_W_W_E(statusFlag));
				}
			}
		}
		annVct.clear();
		// 42.00 END 40
		// 42.2 END 38.2
		// 43.00 END 28
		tmpMdlLOVctC.clear();
	}

	/**
	 * must have MODEL in the message
	 * 
	 * @param item1
	 * @param attrCode1
	 * @param item2
	 * @param attrCode2
	 * @param checkLvl
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private void checkCanNotBeLater4(EntityItem item1, String attrCode1, EntityItem item2, String attrCode2,
			int checkLvl) throws SQLException, MiddlewareException {
		checkCanNotBeLater4(null, item1, attrCode1, item2, attrCode2, checkLvl);
	}

	private void checkCanNotBeLater4(EntityItem psitem, EntityItem item1, String attrCode1, EntityItem item2,
			String attrCode2, int checkLvl) throws SQLException, MiddlewareException {
		String psinfo = "";
		if (psitem != null) {
			psinfo = this.getLD_NDN(psitem) + " ";
		}
		String date1 = getAttrValueAndCheckLvl(item1, attrCode1, checkLvl);
		String date2 = getAttrValueAndCheckLvl(item2, attrCode2, checkLvl);
		addDebug("checkCanNotBeLater4 " + item1.getKey() + " " + attrCode1 + ":" + date1 + " " + item2.getKey() + " "
				+ attrCode2 + ":" + date2);
		boolean isok = checkDates(date1, date2, DATE_LT_EQ); // date1<=date2
		if (isok) { // look for missing meta
			if (date1.length() > 0 && !Character.isDigit(date1.charAt(0))) {
				isok = false;
			}
			if (date2.length() > 0 && !Character.isDigit(date2.charAt(0))) {
				isok = false;
			}
		}
		if (!isok) {
			// CANNOT_BE_LATER_ERR = {0} {1} must not be later than the {2} {3}
			// {LD: ANNOUNCEMENT} {LD: ANNDATE} {ANNDATE} must not be later than
			// the {LD: MODEL} {LD: AVAIL {NDN: C: AVAIL}
			args[0] = psinfo + getLD_NDN(item1);
			args[1] = this.getLD_Value(item1, attrCode1);
			args[2] = m_elist.getParentEntityGroup().getLongDescription() + " " + this.getLD_NDN(item2);
			args[3] = this.getLD_Value(item2, attrCode2);
			createMessage(checkLvl, "CANNOT_BE_LATER_ERR", args);
		}
	}

	/**
	 * Do not move data quality status when triggered from draft to rfr or from
	 * rfr to final and send error message SW Models with Machine type = 56, 57*
	 * (Parent_Entity_Type: MODEL Child_Attribute_Code: COFCAT = Software &
	 * Child_Attribute_Code: MACHTYPEATR starts with 56xx or 57xx)*** If
	 * Parent_Entity_Type: TAXCATG is present on sw model for
	 * Child_Attribute_Code: TAXCNTRY = * US then Parent_Entity_Type:
	 * MODTAXRELEVANCE Child_Attribute_Code: TAXCLS should be set to alpha code
	 * (never 1) If value Child_Attribute_Code: TAXCLS is set to other than
	 * alpha code for Child_Attribute_Code: TAXCNTRY =US - do not change status
	 * of Model and send error message.
	 * 
	 * @param mdlItem
	 * @param statusFlag
	 * @param modelCOFCAT
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private void doSoftwareDataChecks(EntityItem mdlItem, String statusFlag, String modelCOFCAT)
			throws SQLException, MiddlewareException {
		addHeading(3, mdlItem.getLongDescription() + " SofWare data Checks:");
		// MACHTYPEATR
		String machtype = getAttributeFlagEnabledValue(mdlItem, "MACHTYPEATR");
		if (machtype != null && (machtype.startsWith("56") || machtype.startsWith("57")) && "101".equals(modelCOFCAT)) {
			// MODTAXRELEVANCE

			Vector taxcat = PokUtils.getAllLinkedEntities(mdlItem, "MODTAXRELEVANCE", "TAXCATG");
			if (taxcat != null && !taxcat.isEmpty()) {
				for (int i = 0; i < taxcat.size(); i++) {
					EntityItem tItem = (EntityItem) taxcat.get(i);
					String tax = PokUtils.getAttributeFlagValue(tItem, "TAXCNTRY");
					String grp = PokUtils.getAttributeFlagValue(tItem, "SLEORGGRP");
					addDebug("tax" + tax);
					addDebug("grp" + grp);
					if ("1652".equals(tax)&&"SGSUS".equals(grp)) {
						EntityItem entityItem = (EntityItem) tItem.getUpLink().get(0);

						String v2 = PokUtils.getAttributeValue(entityItem, "TAXCLS", "", "");
						if ("".equals(v2) || pattern.matcher(v2).find()) {

							args[0] = getLD_NDN(entityItem);
							// args[1] =
							// PokUtils.getAttributeDescription(entityItem.getEntityGroup(),
							// "TAXCLS", "");
							args[1] = "Tax Country US";
							addError("INVALID_VALUES_ERR", args);

						}

					}

				}
			}

		}

	}

	/*****************
	 * 
	 * @param mdlItem
	 * @param statusFlag
	 * @param modelCOFCAT
	 * @throws SQLException
	 * @throws MiddlewareException
	 *             5.00 AVAIL A MODELAVAIL-d MODEL AVAIL 2016-01-26 Change
	 *             Column G 6.00 WHEN AVAILTYPE = "Planned Availability" or "MES
	 *             Planned Availability" 7.00 CountOf => 1 RE*2 RE*2 RE*2 must
	 *             have at least one "Planned Availability" 8.00 EFFECTIVEDATE
	 *             => MODEL ANNDATE W E E {LD: AVAIL} {NDN: AVAIL} must not be
	 *             earlier than the {LD: MODEL} {LD: ANNDATE} {ANNDATE} 9.00
	 *             COUNTRYLIST Not Checked since MODEL does not have COUNTRYLIST
	 *             10.2 WHEN "RFA" (RFA) = A: AVAIL AVAILANNTYPE 11.00 IF COFCAT
	 *             <> "Service" (102) 11.10 AND "No" (FEATN) <> MODEL FEATINDC
	 *             12.00 THEN ANNCODENAME IN "D: AVAIL | H: AVAIL" ANNCODENAME W
	 *             RW RE MODEL Announcement must announce Feature or SWFeature
	 *             the {LD: MODEL} {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must
	 *             include one or more features. 12.10 END 12.00 13.00
	 *             ANNOUNCEMENT M A: + AVAILANNA 14.00 WHEN ANNTYPE = "New" (19)
	 *             15.00 ANNDATE => MODEL ANNDATE W W E {LD: ANNOUNCEMENT} {LD:
	 *             ANNDATE} {ANNDATE} must not be earlier than the {LD: MODEL}
	 *             {LD: ANNDATE} {ANNDATE} 16.00 END 14 16.2 END 10.2 17.00 END
	 *             6
	 * 
	 */
	private void doModelPLAAvailChecks(EntityItem mdlItem, String statusFlag, String modelCOFCAT)
			throws SQLException, MiddlewareException {
		boolean isSvcModel = SERVICE.equals(modelCOFCAT);
		boolean isSwModel = SOFTWARE.equals(modelCOFCAT);
		boolean isHwModel = HARDWARE.equals(modelCOFCAT);

		String modelFEATINDC = getAttributeFlagEnabledValue(mdlItem, "FEATINDC");
		addDebug("doModelPLAAvailChecks " + mdlItem.getKey() + " FEATINDC: " + modelFEATINDC);

		addHeading(3, "Model Planned Avail Checks:");

		// 5.00 AVAIL A MODELAVAIL-d MODEL AVAIL
		// 6.01 WHEN AVAILTYPE = "Planned Availability"
		// 7.00 Count of => 1 RE*2 RE*2 RE*2 must have at least one "Planned
		// Availability"
		checkPlannedAvailsExist(mdlPlaAvailVctA, getCheckLevel(CHECKLEVEL_RE, mdlItem, "ANNDATE"));

		int checklvl = getCheck_W_W_E(statusFlag);

		ArrayList plaCodeNameList = null;
		if (isHwModel) {
			plaCodeNameList = getAttributeAsList(psPlaAvailVctD, "ANNCODENAME", checklvl); // D:AVAIL
		} else if (isSwModel) {
			plaCodeNameList = getAttributeAsList(swpsPlaAvailVctH, "ANNCODENAME", checklvl); // H:
																								// AVAIL
		}

		for (int i = 0; i < mdlPlaAvailVctA.size(); i++) {
			EntityItem avail = (EntityItem) mdlPlaAvailVctA.elementAt(i);
			// 8.00 EFFECTIVEDATE => MODEL ANNDATE W E E {LD: AVAIL} {NDN:
			// AVAIL} can not be earlier than the {LD: MODEL} {LD: ANNDATE}
			// {ANNDATE}
			checkCanNotBeEarlier(avail, "EFFECTIVEDATE", mdlItem, "ANNDATE", getCheck_W_E_E(statusFlag));
		}
		// 20121030 Add 8.20 WHEN "Final" (FINAL) = MODEL DATAQUALITY
		// 20121030 Add 8.22 IF STATUS = "Ready for Review" (0040)
		// 20121030 Add 8.24 OR STATUS = "Final" (0020)
		// 20121030 Add 8.26 CountOf => 1 RE*2 must have at least one "Planned
		// Availability" that is either "Ready for Review" or "Final" in order
		// to be "Final"
		// 20121030 Add 8.28 END 8.20
		checkPlannedAvailsStatus(mdlPlaAvailVctA, mdlItem, getCheckLevel(CHECKLEVEL_RE, mdlItem, "ANNDATE"));
		// 10.2 WHEN "RFA" (RFA) = A: AVAIL AVAILANNTYPE
		Vector tmpMdlPlaVctA = new Vector(mdlPlaAvailVctA);
		// remove any that are not AVAILANNTYPE=RFA
		removeNonRFAAVAIL(tmpMdlPlaVctA);

		for (int i = 0; i < tmpMdlPlaVctA.size(); i++) {
			EntityItem avail = (EntityItem) tmpMdlPlaVctA.elementAt(i);
			// 11.00 IF COFCAT <> "Service" (102)
			// 11.10 AND "No" (FEATN) <> MODEL FEATINDC

			// 12.00 THEN ANNCODENAME IN "D: AVAIL | H: AVAIL" ANNCODENAME W RW
			// RE MODEL Announcement must announce Feature or SWFeature
			// the {LD: MODEL} {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must
			// include one or more features.
			if (!isSvcModel && !FEATINDC_No.equals(modelFEATINDC)) {
				String annCodeName = PokUtils.getAttributeFlagValue(avail, "ANNCODENAME");
				if (annCodeName != null) {
					if (plaCodeNameList.contains(annCodeName)) {
						continue;
					} else {
						addDebug("doModelPLAAvailChecks psplannedavails plaCodeNameList: " + plaCodeNameList
								+ " did not have mdlplannedavail:" + avail.getKey() + " annCodeName: " + annCodeName);
						Vector annVct = PokUtils.getAllLinkedEntities(avail, "AVAILANNA", "ANNOUNCEMENT");
						// MUST_HAVE_FEATURES_ERR2 = the {0} {1} must include
						// one or more features.
						// the {LD: MODEL} {LD: ANNOUNCEMENT} {NDN:
						// ANNOUNCEMENT} must include one or more features.
						args[0] = mdlItem.getEntityGroup().getLongDescription();
						if (annVct.size() > 0) {
							args[1] = getLD_NDN(avail) + " " + getLD_NDN((EntityItem) annVct.firstElement());
						} else {
							args[1] = m_elist.getEntityGroup("ANNOUNCEMENT").getLongDescription();
						}

						createMessage(checklvl, "MUST_HAVE_FEATURES_ERR2", args);
						annVct.clear();
					}
				}
			}
			// 12.10 END 12.00
		}

		if (plaCodeNameList != null) {
			plaCodeNameList.clear();
		}

		// 13.00 ANNOUNCEMENT A: + AVAILANNA
		// 14.00 WHEN ANNTYPE = "New" (19)
		// 15.00 ANNDATE => MODEL ANNDATE W W E {LD: ANNOUNCEMENT} {LD: ANNDATE}
		// {ANNDATE} can not be earlier than the {LD: MODEL} {LD: ANNDATE}
		// {ANNDATE}
		// 16.00 END 14

		Vector annVct = PokUtils.getAllLinkedEntities(tmpMdlPlaVctA, "AVAILANNA", "ANNOUNCEMENT");
		addDebug("doModelPLAAvailChecks annVct: " + annVct.size());
		annVct = PokUtils.getEntitiesWithMatchedAttr(annVct, "ANNTYPE", ANNTYPE_NEW);
		addDebug("doModelPLAAvailChecks NEW annVct: " + annVct.size());
		for (int i = 0; i < annVct.size(); i++) {
			EntityItem annItem = (EntityItem) annVct.elementAt(i);
			// {LD: ANNOUNCEMENT} {LD: ANNDATE} {ANNDATE} can not be earlier
			// than the {LD: MODEL} {LD: ANNDATE} {ANNDATE}
			checkCanNotBeEarlier(annItem, "ANNDATE", mdlItem, "ANNDATE", getCheck_W_W_E(statusFlag));
		}
		annVct.clear();
		tmpMdlPlaVctA.clear();
		// 16.2 END 10.2
		// 17.00 END 6
	}

	private void doModelPLAndMesPLAvailChecks(String checkTitle, Vector mdlPlaOrMesPlaAvailVctA, String availType,
			EntityItem mdlItem, String statusFlag, String modelCOFCAT) throws SQLException, MiddlewareException {
		boolean isSvcModel = SERVICE.equals(modelCOFCAT);
		boolean isSwModel = SOFTWARE.equals(modelCOFCAT);
		boolean isHwModel = HARDWARE.equals(modelCOFCAT);

		String modelFEATINDC = getAttributeFlagEnabledValue(mdlItem, "FEATINDC");
		addDebug("doModelPLAAvailChecks " + mdlItem.getKey() + " FEATINDC: " + modelFEATINDC + " AVAILTYPE:"
				+ availType);

		addHeading(3, checkTitle);

		// 5.00 AVAIL A MODELAVAIL-d MODEL AVAIL
		// 6.01 WHEN AVAILTYPE = "Planned Availability"
		// 7.00 Count of => 1 RE*2 RE*2 RE*2 must have at least one "Planned
		// Availability"
		if (PLANNEDAVAIL.equals(availType)) {
			checkPlannedAvailsExist(mdlPlaOrMesPlaAvailVctA, getCheckLevel(CHECKLEVEL_RE, mdlItem, "ANNDATE"));
		}
		// 7.01 END 6.01

		int checklvl = getCheck_W_W_E(statusFlag);

		ArrayList plaCodeNameList = null;
		ArrayList mesPlaCodeNameList = null;
		if (isHwModel) {
			plaCodeNameList = getAttributeAsList(psPlaAvailVctD, "ANNCODENAME", checklvl); // D:AVAIL
			mesPlaCodeNameList = getAttributeAsList(psMesPlaAvailVctD, "ANNCODENAME", checklvl);
		} else if (isSwModel) {
			plaCodeNameList = getAttributeAsList(swpsPlaAvailVctH, "ANNCODENAME", checklvl); // H:
																								// AVAIL
			mesPlaCodeNameList = getAttributeAsList(swpsMesPlaAvailVctH, "ANNCODENAME", checklvl);
		}

		for (int i = 0; i < mdlPlaOrMesPlaAvailVctA.size(); i++) {
			EntityItem avail = (EntityItem) mdlPlaOrMesPlaAvailVctA.elementAt(i);
			// 8.00 EFFECTIVEDATE => MODEL ANNDATE W E E {LD: AVAIL} {NDN:
			// AVAIL} can not be earlier than the {LD: MODEL} {LD: ANNDATE}
			// {ANNDATE}
			checkCanNotBeEarlier(avail, "EFFECTIVEDATE", mdlItem, "ANNDATE", getCheck_W_E_E(statusFlag));
		}
		// 20121030 Add 8.20 WHEN "Final" (FINAL) = MODEL DATAQUALITY
		// 20121030 Add 8.22 IF STATUS = "Ready for Review" (0040)
		// 20121030 Add 8.24 OR STATUS = "Final" (0020)
		// 2016-01-26 New 8.25 IF AVAILTYPE = "Planned Availability"
		// 8.26 CountOf => 1 RE*2 must have at least one "Planned Availability"
		// that is either "Ready for Review" or "Final" in order to be "Final"
		// 2016-01-26 New 8.27 END 8.25
		// 20121030 Add 8.28 END 8.20
		if (PLANNEDAVAIL.equals(availType)) {
			checkPlannedAvailsStatus(mdlPlaOrMesPlaAvailVctA, mdlItem,
					getCheckLevel(CHECKLEVEL_RE, mdlItem, "ANNDATE"));
		}

		// 10.2 WHEN "RFA" (RFA) = A: AVAIL AVAILANNTYPE
		Vector tmpMdlPlaVctA = new Vector(mdlPlaOrMesPlaAvailVctA);
		// remove any that are not AVAILANNTYPE=RFA
		removeNonRFAAVAIL(tmpMdlPlaVctA);

		for (int i = 0; i < tmpMdlPlaVctA.size(); i++) {
			EntityItem avail = (EntityItem) tmpMdlPlaVctA.elementAt(i);
			// 11.00 IF COFCAT <> "Service" (102)
			// 11.10 AND "No" (FEATN) <> MODEL FEATINDC

			// 12.00 THEN ANNCODENAME IN "D: AVAIL | H: AVAIL" ANNCODENAME W RW
			// RE MODEL Announcement must announce Feature or SWFeature
			// the {LD: MODEL} {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} must
			// include one or more features.
			if (!isSvcModel && !FEATINDC_No.equals(modelFEATINDC)) {
				String annCodeName = PokUtils.getAttributeFlagValue(avail, "ANNCODENAME");
				if (annCodeName != null) {
					if (plaCodeNameList.contains(annCodeName) || mesPlaCodeNameList.contains(annCodeName)) {
						continue;
					} else {
						addDebug("doModelPLAAvailChecks psplannedavails plaCodeNameList: " + plaCodeNameList
								+ " and psMesPlannedavails mesPlaCodeNameList: " + mesPlaCodeNameList
								+ " did not have mdlplannedavail:" + avail.getKey() + " annCodeName: " + annCodeName);
						Vector annVct = PokUtils.getAllLinkedEntities(avail, "AVAILANNA", "ANNOUNCEMENT");
						// MUST_HAVE_FEATURES_ERR2 = the {0} {1} must include
						// one or more features.
						// the {LD: MODEL} {LD: ANNOUNCEMENT} {NDN:
						// ANNOUNCEMENT} must include one or more features.
						args[0] = mdlItem.getEntityGroup().getLongDescription();
						if (annVct.size() > 0) {
							args[1] = getLD_NDN(avail) + " " + getLD_NDN((EntityItem) annVct.firstElement());
						} else {
							args[1] = m_elist.getEntityGroup("ANNOUNCEMENT").getLongDescription();
						}
						createMessage(checklvl, "MUST_HAVE_FEATURES_ERR2", args);
						annVct.clear();
					}
				}
			}
			// 12.10 END 12.00
		}

		if (plaCodeNameList != null) {
			plaCodeNameList.clear();
		}
		if (mesPlaCodeNameList != null) {
			mesPlaCodeNameList.clear();
		}

		// 13.00 ANNOUNCEMENT A: + AVAILANNA
		// 14.00 WHEN ANNTYPE = "New" (19)
		// 15.00 ANNDATE => MODEL ANNDATE W W E {LD: ANNOUNCEMENT} {LD: ANNDATE}
		// {ANNDATE} can not be earlier than the {LD: MODEL} {LD: ANNDATE}
		// {ANNDATE}
		// 16.00 END 14

		Vector annVct = PokUtils.getAllLinkedEntities(tmpMdlPlaVctA, "AVAILANNA", "ANNOUNCEMENT");
		addDebug("doModelPLAAvailChecks annVct: " + annVct.size());
		annVct = PokUtils.getEntitiesWithMatchedAttr(annVct, "ANNTYPE", ANNTYPE_NEW);
		addDebug("doModelPLAAvailChecks NEW annVct: " + annVct.size());
		for (int i = 0; i < annVct.size(); i++) {
			EntityItem annItem = (EntityItem) annVct.elementAt(i);
			// {LD: ANNOUNCEMENT} {LD: ANNDATE} {ANNDATE} can not be earlier
			// than the {LD: MODEL} {LD: ANNDATE} {ANNDATE}
			checkCanNotBeEarlier(annItem, "ANNDATE", mdlItem, "ANNDATE", getCheck_W_W_E(statusFlag));
		}
		annVct.clear();
		tmpMdlPlaVctA.clear();
		// 16.2 END 10.2
		// 17.00 END 6
	}

	/****************
	 * 
	 * @param mdlItem
	 * @param statusFlag
	 * @param modelCOFCAT
	 * @throws SQLException
	 * @throws MiddlewareException
	 * 
	 *             18.00 AVAIL B MODELAVAIL-d MODEL AVAIL 19.00 WHEN AVAILTYPE =
	 *             "First Order" 20.00 EFFECTIVEDATE => MODEL ANNDATE W W E {LD:
	 *             AVAIL} {NDN: AVAIL} must not be earlier than the {LD: MODEL}
	 *             {LD: ANNDATE} {ANNDATE} xx21.00 COUNTRYLIST "in aggregate G"
	 *             A: AVAIL COUNTRYLIST W W E {LD: AVAIL} {NDN: AVAIL} {LD:
	 *             COUNTRYLIST} includes a Country that does not have a "Planned
	 *             Availability" Change 20111216 21.00 COUNTRYLIST "IN aggregate
	 *             G" A: AVAIL COUNTRYLIST OSN:XCC_LIST W W E {LD: AVAIL} {NDN:
	 *             AVAIL} {LD: COUNTRYLIST} includes a Country that does not
	 *             have a "Planned Availability" 21.2 WHEN "RFA" (RFA) = B:
	 *             AVAIL AVAILANNTYPE 22.00 ANNOUNCEMENT G B: + AVAILANNA MODEL
	 *             ANNOUNCEMENT 23.00 WHEN ANNTYPE = "New" (19) 24.00 ANNDATE =>
	 *             MODEL ANNDATE W W E {LD: ANNOUNCEMENT} {LD: ANNDATE}
	 *             {ANNDATE} must not be earlier than the {LD: MODEL} {LD:
	 *             ANNDATE} {ANNDATE} 24.10 ANNDATE <= B: AVAIL EFFECTIVEDATE W
	 *             E E {LD: AVAIL} {NDN: B: AVAIL} must not be earlier than the
	 *             {LD: ANNOUNCEMENT} {NDN: G: ANNOUNCEMENT} 25.00 END 23 25.2
	 *             END 21.2 26.00 END 19
	 * 
	 */
	private void doModelFOAvailChecks(EntityItem mdlItem, String statusFlag, String modelCOFCAT)
			throws SQLException, MiddlewareException {
		int checklvl = getCheck_W_W_E(statusFlag);
		addHeading(3, "Model First Order Avail Checks:");
		// 18.00 AVAIL B MODELAVAIL-d MODEL AVAIL
		// 19.00 WHEN AVAILTYPE = "First Order"
		if (mdlFOAvailVctB.size() > 0) {
			for (int i = 0; i < mdlFOAvailVctB.size(); i++) {
				EntityItem avail = (EntityItem) mdlFOAvailVctB.elementAt(i);
				// 20.00 EFFECTIVEDATE => MODEL ANNDATE W W E {LD: AVAIL} {NDN:
				// AVAIL} can not be earlier than the {LD: MODEL} {LD: ANNDATE}
				// {ANNDATE}
				checkCanNotBeEarlier(avail, "EFFECTIVEDATE", mdlItem, "ANNDATE", checklvl);
				// old21.00 COUNTRYLIST "in aggregate G" A: AVAIL COUNTRYLIST W
				// W E
				// checkPlannedAvailForCtryExists(avail,
				// mdlPlaAvailCtryTblA.keySet(), checklvl);
			}
			Hashtable plaAvailOSNTbl = new Hashtable();
			boolean plaOsnErrors = getAvailByOSN(plaAvailOSNTbl, mdlPlaAvailVctA, true, CHECKLEVEL_RE);
			// MES
			// Hashtable mesPlaAvailOSNTbl = new Hashtable();
			// boolean mesPlaOsnErrors =
			// getAvailByOSN(mesPlaAvailOSNTbl,mdlMesPlaAvailVctA,true,CHECKLEVEL_RE);
			Hashtable foAvailOSNTbl = new Hashtable();
			boolean foOsnErrors = getAvailByOSN(foAvailOSNTbl, mdlFOAvailVctB, true, CHECKLEVEL_RE);
			addDebug("doModelFOAvailChecks foOsnErrors " + foOsnErrors + " foAvailOSNTbl.keys " + foAvailOSNTbl.keySet()
					+ " plaOsnErrors " + plaOsnErrors + " plaAvailOSNTbl.keys "
					+ plaAvailOSNTbl.keySet()/*
												 * +" mesPlaOsnErrors "+
												 * mesPlaOsnErrors+" mesPlaAvailOSNTbl.keys "
												 * +mesPlaAvailOSNTbl.keySet()
												 */);
			// Change 20111216 21.00 COUNTRYLIST "IN aggregate G" A: AVAIL
			// COUNTRYLIST OSN:XCC_LIST W W E {LD: AVAIL} {NDN: AVAIL} {LD:
			// COUNTRYLIST} includes a Country that does not have a "Planned
			// Availability"
			if (!plaOsnErrors && !foOsnErrors) {
				// only do this check if no errors were found building the OSN
				// buckets
				checkAvailCtryByOSN(foAvailOSNTbl, plaAvailOSNTbl, "MISSING_PLA_OSNCTRY_ERR", null, true, checklvl);
			}
			// if(mdlMesPlaAvailVctA.size() > 0 && !mesPlaOsnErrors &&
			// !foOsnErrors){
			// // only do this check if no errors were found building the OSN
			// buckets
			// checkAvailCtryByOSN(foAvailOSNTbl,mesPlaAvailOSNTbl,
			// "MISSING_PLA_OSNCTRY_ERR", null, true, checklvl);
			// }
			plaAvailOSNTbl.clear();
			// mesPlaAvailOSNTbl.clear();
			foAvailOSNTbl.clear();
		}
		// 21.2 WHEN "RFA" (RFA) = B: AVAIL AVAILANNTYPE
		Vector tmpMdlFOVctB = new Vector(mdlFOAvailVctB);
		// remove any that are not AVAILANNTYPE=RFA
		removeNonRFAAVAIL(tmpMdlFOVctB);

		// 22.00 ANNOUNCEMENT G B: + AVAILANNA MODEL ANNOUNCEMENT
		// 23.00 WHEN ANNTYPE="New" (19)
		// 24.00 ANNDATE => MODEL ANNDATE W W E {LD: ANNOUNCEMENT} {LD: ANNDATE}
		// {ANNDATE} can not be earlier than the {LD: MODEL} {LD: ANNDATE}
		// {ANNDATE}
		Vector annVct = PokUtils.getAllLinkedEntities(tmpMdlFOVctB, "AVAILANNA", "ANNOUNCEMENT");
		addDebug("doModelFOAvailChecks mdlFOAvailVctB " + mdlFOAvailVctB.size() + " RFA mdlfoavails "
				+ tmpMdlFOVctB.size() + " annVct: " + annVct.size());
		annVct = PokUtils.getEntitiesWithMatchedAttr(annVct, "ANNTYPE", ANNTYPE_NEW);
		addDebug("doModelFOAvailChecks NEW annVct: " + annVct.size());

		for (int i = 0; i < annVct.size(); i++) {
			EntityItem annItem = (EntityItem) annVct.elementAt(i);
			// {LD: ANNOUNCEMENT} {LD: ANNDATE} {ANNDATE} can not be earlier
			// than the {LD: MODEL} {LD: ANNDATE} {ANNDATE}
			checkCanNotBeEarlier(annItem, "ANNDATE", mdlItem, "ANNDATE", getCheck_W_W_E(statusFlag));
			// 24.10 ANNDATE <= B: AVAIL EFFECTIVEDATE W E E
			// {LD: AVAIL} {NDN: B: AVAIL} must not be earlier than the {LD:
			// ANNOUNCEMENT} {NDN: G: ANNOUNCEMENT}
			String annCodeName = PokUtils.getAttributeFlagValue(annItem, "ANNCODENAME");
			for (int b = 0; b < tmpMdlFOVctB.size(); b++) {
				EntityItem foavail = (EntityItem) tmpMdlFOVctB.elementAt(b);
				String avail_annCodeName = PokUtils.getAttributeFlagValue(foavail, "ANNCODENAME");
				if (avail_annCodeName != null && avail_annCodeName.equals(annCodeName))// match
																						// anncodename
																						// -
																						// 2013-01-21
					checkCanNotBeEarlier(foavail, "EFFECTIVEDATE", annItem, "ANNDATE", getCheck_W_E_E(statusFlag));
			}
		}
		annVct.clear();
		tmpMdlFOVctB.clear();
		// 25.00 END 23
		// 26.00 END 19
		// END 21.2
	}

	/***************************************
	 * 
	 * For each PS->plannedAvail.ctry that matches the
	 * MODEL->LastOrderAvail.ctry there must be a PS->LastOrderAvail.ctry PER
	 * PRODSTRUCT
	 * 
	 * @param statusFlag
	 * @param psGrp
	 * @param featType
	 * @param psRelType
	 * @throws MiddlewareException
	 * @throws SQLException
	 * 
	 *             27.00 AVAIL C MODELAVAIL-d MODEL AVAIL 28.00 WHEN AVAILTYPE =
	 *             "Last Order"
	 * 
	 *             --- D:AVAIL are PRODSTRUCT PLA, E:AVAIL are PRODSTRUCT LO
	 *             32.00 IF COUNTRYLIST Match D: AVAIL COUNTRYLIST OSN:XCC_LIST
	 *             "D: PRODSTRUCT Planned Avail has PRODSTRUCT in this Country"
	 *             33.00 AND "Initial" = PRODSTRUCT ORDERCODE 33.20 AND FCTYPE =
	 *             "Primary FC (100) | ""Secondary FC"" (110)" RPQ logic 34.00
	 *             THEN TheMatch IN E: AVAIL COUNTRYLIST OSN:XCC_LIST W RW RE
	 *             "E: PRODSTRUCT Last order ALL Features that are Initial must
	 *             be withdrawn in all countries that the MODEL is being
	 *             withdrawn" {LD: FEATURE} (NDN: E: FEATURE} must have a "Last
	 *             Order" for all countries in the {LD: MODEL} {LD: AVAIL} {C:
	 *             AVAIL} AND PRODSTRUCT SameParent D: AVAIL E: AVAIL Note: the
	 *             AVAILs must have the same parent PRODSTRUCT 34.10
	 *             EFFECTIVEDATE => E:AVAIL EFFECTIVEDATE OSN:XCC_LIST W RW RE
	 *             "E: PRODSTRUCT Last order Features must be withdrawn on or
	 *             before MODEL is withdrawn" {LD: PRODSTRUCT} (NDN: E:
	 *             PRODSTRUCT} {LD: AVAIL} {E: AVAIL} must be no later than the
	 *             {LD: MODEL} {LD:AVAIL} 34.20 END 34.00
	 * 
	 *             --- H:AVAIL are SWPRODSTRUCT PLA, K:AVAIL are SWPRODSTRUCT LO
	 *             35.00 IF COUNTRYLIST Match H: AVAIL COUNTRYLIST OSN:XCC_LIST
	 *             "S: SWPRODSTRUCT Planned Avail has SWPRODSTRUCT in this
	 *             Country" 36.00 THEN TheMatch IN K: AVAIL COUNTRYLIST
	 *             OSN:XCC_LIST W RW RE ALL SW Features must be withdrawn in all
	 *             countries that the MODEL is being withdrawn {LD: SWFEATURE}
	 *             (NDN: K: SWFEATURE} must have a "Last Order" for all
	 *             countries in the {LD: MODEL} {LD: AVAIL} {C: AVAIL} 36.02 AND
	 *             SWPRODSTRUCT SameParent H: AVAIL K: AVAIL 36.10 EFFECTIVEDATE
	 *             => K:AVAIL EFFECTIVEDATE OSN:XCC_LIST W RW RE SW Features
	 *             must be withdrawn on or before MODEL is withdrawn {LD:
	 *             SWPRODSTRUCT} (NDN: K: SWPRODSTRUCT} {LD: AVAIL} {E: AVAIL}
	 *             must be no later than the {LD: MODEL} {LD:AVAIL} 36.20 END
	 *             36.00
	 *
	 */
	private void matchPsModelLastOrderAvail(String statusFlag, EntityGroup psGrp, String featType, String psRelType)
			throws MiddlewareException, SQLException {
		int checklvl = getCheck_W_RW_RE(statusFlag);

		if (mdlLOAvailVctC.size() > 0) {
			Hashtable mdlloAvailOSNTbl = new Hashtable();
			boolean mdlloOsnErrors = getAvailByOSN(mdlloAvailOSNTbl, mdlLOAvailVctC, true, CHECKLEVEL_RE);
			addDebug("matchPsModelLastOrderAvail  mdlloOsnErrors " + mdlloOsnErrors + " mdlloAvailOSNTbl.keys "
					+ mdlloAvailOSNTbl.keySet());
			if (mdlloOsnErrors) {
				return;
			}

			for (int p = 0; p < psGrp.getEntityItemCount(); p++) {
				EntityItem psitem = psGrp.getEntityItem(p);
				boolean dochecks = true;
				if (psitem.getEntityType().equals("PRODSTRUCT")) {
					// 33.00 ORDERCODE 5957 I Initial
					String ordercode = PokUtils.getAttributeFlagValue(psitem, "ORDERCODE");
					addDebug("matchPsModelLastOrderAvail " + psitem.getKey() + " ordercode: " + ordercode);
					if (!ORDERCODE_INITIAL.equals(ordercode)) {
						dochecks = false;
						addDebug("     ordercode was not initial, skipping checks");
					} else {
						// 33.20 AND FCTYPE = "Primary FC (100) |""Secondary
						// FC"" (110)" RPQ logic
						EntityItem featitem = getUpLinkEntityItem(psitem, "FEATURE");
						addDebug("matchPsModelLastOrderAvail  " + psitem.getKey() + " " + featitem.getKey());
						if (isRPQ(featitem)) {
							addDebug(featitem.getKey() + " was an RPQ FCTYPE: "
									+ getAttributeFlagEnabledValue(featitem, "FCTYPE") + " skipping checks");
							dochecks = false;
						}
					}
				}
				if (dochecks) {
					/*
					 * 32.00 IF COUNTRYLIST Match D: AVAIL COUNTRYLIST
					 * OSN:XCC_LIST
					 * "D: PRODSTRUCT Planned Avail has PRODSTRUCT in this Country"
					 * 34.00 THEN TheMatch IN E: AVAIL COUNTRYLIST OSN:XCC_LIST
					 * W RW RE
					 * "E: PRODSTRUCT Last order ALL Features that are Initial must be withdrawn in all countries that the MODEL is being withdrawn"
					 * {LD: FEATURE} (NDN: E: FEATURE} must have a "Last Order"
					 * for all countries in the {LD: MODEL} {LD: AVAIL} {C:
					 * AVAIL} AND PRODSTRUCT SameParent D: AVAIL E: AVAIL Note:
					 * the AVAILs must have the same parent PRODSTRUCT 34.10
					 * EFFECTIVEDATE => E:AVAIL EFFECTIVEDATE OSN:XCC_LIST W RW
					 * RE
					 * "E: PRODSTRUCT Last order Features must be withdrawn on or before MODEL is withdrawn"
					 * {LD: PRODSTRUCT} (NDN: E: PRODSTRUCT} {LD: AVAIL} {E:
					 * AVAIL} must be no later than the {LD: MODEL} {LD:AVAIL}
					 * 34.20 END 34.00
					 * 
					 * 35.00 IF COUNTRYLIST Match H: AVAIL COUNTRYLIST
					 * OSN:XCC_LIST
					 * "S: SWPRODSTRUCT Planned Avail has SWPRODSTRUCT in this Country"
					 * 36.00 THEN TheMatch IN K: AVAIL COUNTRYLIST OSN:XCC_LIST
					 * W RW RE ALL SW Features must be withdrawn in all
					 * countries that the MODEL is being withdrawn {LD:
					 * SWFEATURE} (NDN: K: SWFEATURE} must have a "Last Order"
					 * for all countries in the {LD: MODEL} {LD: AVAIL} {C:
					 * AVAIL} 36.02 AND SWPRODSTRUCT SameParent H: AVAIL K:
					 * AVAIL 36.10 EFFECTIVEDATE => K:AVAIL EFFECTIVEDATE
					 * OSN:XCC_LIST W RW RE SW Features must be withdrawn on or
					 * before MODEL is withdrawn {LD: SWPRODSTRUCT} (NDN: K:
					 * SWPRODSTRUCT} {LD: AVAIL} {E: AVAIL} must be no later
					 * than the {LD: MODEL} {LD:AVAIL} 36.20 END 36.00
					 */
					Vector availVct = PokUtils.getAllLinkedEntities(psitem, psRelType, "AVAIL");
					Vector psplannedAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", PLANNEDAVAIL);
					Vector pslastOrderAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE",
							LASTORDERAVAIL);
					addDebug("matchPsModelLastOrderAvail " + psitem.getKey() + " all avail: " + availVct.size()
							+ " plaAvail: " + psplannedAvailVct.size() + " loAvail: " + pslastOrderAvailVct.size());

					Hashtable loAvailOSNTbl = new Hashtable();
					boolean loOsnErrors = getAvailByOSN(loAvailOSNTbl, pslastOrderAvailVct, true, CHECKLEVEL_RE);

					Hashtable plaAvailOSNTbl = new Hashtable();
					boolean plaOsnErrors = getAvailByOSN(plaAvailOSNTbl, psplannedAvailVct, true, CHECKLEVEL_RE);
					addDebug("matchPsModelLastOrderAvail " + psitem.getKey() + " loOsnErrors " + loOsnErrors
							+ " loAvailOSNTbl.keys " + loAvailOSNTbl.keySet() + " plaOsnErrors " + plaOsnErrors
							+ " plaAvailOSNTbl.keys " + plaAvailOSNTbl.keySet());

					if (plaOsnErrors || loOsnErrors) {
						continue;
					}
					// IF MODEL-LastOrderAVAIL.COUNTRYLIST Match PS-PlannedAVAIL
					// COUNTRYLIST BY OSN
					// THEN MODELAVAIL.COUNTRYLIST TheMatch PS-LastOrderAVAIL
					// COUNTRYLIST W RW RE
					// must have a "Last Order" {LD: AVAIL} corresponding to
					// {LD: MODEL} {LD: AVAIL} {NDN: AVAIL}
					// get the set of countries for PS lastorder avails

					Vector outputMsgsForAvailsVct = new Vector();
					Vector datechkForAvailsVct = new Vector();

					Set plaKeys = plaAvailOSNTbl.keySet();
					Iterator itr = plaKeys.iterator();
					while (itr.hasNext()) { // look at each set of planned
											// avails for an osn
						String osn = (String) itr.next();
						// get avails from each table for this osn
						Vector plaOsnVct = (Vector) plaAvailOSNTbl.get(osn);
						Vector mdlloOsnVct = (Vector) mdlloAvailOSNTbl.get(osn);
						if (mdlloOsnVct == null) {
							addDebug("matchPsModelLastOrderAvail no mdl loavails to check for osn " + osn);
							continue;
						}
						Hashtable mdllastOrderAvlCtryTbl = getAvailByCountry(mdlloOsnVct, checklvl);
						addDebug("matchPsModelLastOrderAvail osn " + osn + " mdllastOrderAvlCtryTbl: "
								+ mdllastOrderAvlCtryTbl.keySet());
						Vector loOsnVct = (Vector) loAvailOSNTbl.get(osn);
						Hashtable lastOrderAvlCtryTbl = null;
						if (loOsnVct != null) {
							lastOrderAvlCtryTbl = getAvailByCountry(loOsnVct, checklvl);
						} else {
							lastOrderAvlCtryTbl = new Hashtable();
						}
						addDebug("matchPsModelLastOrderAvail loOsnVct: "
								+ (loOsnVct == null ? "null" : "" + loOsnVct.size()) + " PS-lastOrderAvlCtry: "
								+ lastOrderAvlCtryTbl.keySet());

						// For each PS->plannedAvail.ctry that matches the
						// MODEL->LastOrderAvail.ctry BY OSN
						// there must be a PS->LastOrderAvail.ctry by OSN
						for (int i = 0; i < plaOsnVct.size(); i++) {
							EntityItem avail = (EntityItem) plaOsnVct.elementAt(i);

							EANFlagAttribute ctrylist = (EANFlagAttribute) getAttrAndCheckLvl(avail, "COUNTRYLIST",
									checklvl);
							if (ctrylist != null && ctrylist.toString().length() > 0) {
								// Get the selected Flag codes.
								MetaFlag[] mfArray = (MetaFlag[]) ctrylist.get();
								ArrayList unmatchedFlags = new ArrayList();

								Vector mdlloVct = new Vector(); // hold onto
																// model
																// lastorderavail
																// in case mult
																// ctrys match
								for (int im = 0; im < mfArray.length; im++) {
									if (mfArray[im].isSelected()) {
										// get the MODEL-lastorderavail for this
										// ctry and osn
										EntityItem mdlloAvail = (EntityItem) mdllastOrderAvlCtryTbl
												.get(mfArray[im].getFlagCode());
										if (!lastOrderAvlCtryTbl.containsKey(mfArray[im].getFlagCode())) {
											addDebug("matchPsModelLastOrderAvail PS-plannedavail:" + avail.getKey()
													+ " No PS lastorderavail for osn " + osn + " ctry "
													+ mfArray[im].getFlagCode());
											if (mdlloAvail != null) {
												addDebug("matchPsModelLastOrderAvail PS-plannedavail:" + avail.getKey()
														+ " MODEL-lastorderavail " + mdlloAvail.getKey()
														+ " match for ctry " + mfArray[im].getFlagCode());
												if (outputMsgsForAvailsVct.contains(mdlloAvail)) {
													addDebug("already output msg for MODEL-lastorderavail "
															+ mdlloAvail.getKey());
													continue;
												}
												if (!mdlloVct.contains(mdlloAvail)) {
													mdlloVct.add(mdlloAvail);
													unmatchedFlags.add(mfArray[im].getFlagCode());
												}
											}
										} else {
											if (mdlloAvail != null) {
												addDebug("matchPsModelLastOrderAvail PS-plannedavail:" + avail.getKey()
														+ " PS lastorder match ctry and osn " + osn
														+ " MODEL-lastorderavail " + mdlloAvail.getKey()
														+ " match for ctry " + mfArray[im].getFlagCode());

												// get the ps-lastorderavail for
												// this ctry and osn
												EntityItem loAvail = (EntityItem) lastOrderAvlCtryTbl
														.get(mfArray[im].getFlagCode());

												if (datechkForAvailsVct.contains(loAvail)) {
													addDebug("already chk dates for ps-lastorderavail "
															+ loAvail.getKey());
													continue;
												}
												datechkForAvailsVct.add(loAvail);

												// 34.10 EFFECTIVEDATE =>
												// E:AVAIL EFFECTIVEDATE
												// OSN:XCC_LIST W RW RE "E:
												// PRODSTRUCT Last order
												// Features must be withdrawn on
												// or before MODEL is withdrawn"
												// {LD: PRODSTRUCT} (NDN: E:
												// PRODSTRUCT} {LD: AVAIL} {E:
												// AVAIL} must be no later than
												// the {LD: MODEL} {LD:AVAIL}
												// 36.10 EFFECTIVEDATE =>
												// K:AVAIL EFFECTIVEDATE
												// OSN:XCC_LIST W RW RE SW
												// Features must be withdrawn on
												// or before MODEL is withdrawn
												// {LD: SWPRODSTRUCT} (NDN: K:
												// SWPRODSTRUCT} {LD: AVAIL} {E:
												// AVAIL} must be no later than
												// the {LD: MODEL} {LD:AVAIL}
												checkCanNotBeLater4(psitem, loAvail, "EFFECTIVEDATE", mdlloAvail,
														"EFFECTIVEDATE", checklvl);
											}
										}
									} // end ctry is selected
								}

								// output msg for all mdl lastorder that didnt
								// have an ps lastorder
								for (int m = 0; m < mdlloVct.size(); m++) {
									EntityItem mdlloAvail = (EntityItem) mdlloVct.elementAt(m);
									// PS_LAST_ORDER_ERR2 = {0} must have a
									// &quot;Last Order&quot; for all countries
									// in the {1} {2}. Missing countries: {3}
									// {LD: FEATURE} (NDN: E: FEATURE} must have
									// a "Last Order" for all countries in the
									// {LD: MODEL} {LD: AVAIL} {C: AVAIL}
									args[0] = getLD_NDN(getUpLinkEntityItem(psitem, featType));
									args[1] = m_elist.getParentEntityGroup().getLongDescription();
									if (osn.equals(DOMAIN_NOT_IN_LIST)) {
										args[2] = getLD_NDN(mdlloAvail);
									} else {
										args[2] = getLD_NDN(mdlloAvail) + " for "
												+ getLD_Value(mdlloAvail, "ORDERSYSNAME");
									}
									args[3] = getUnmatchedDescriptions(mdlloAvail, "COUNTRYLIST", unmatchedFlags);
									createMessage(checklvl, "PS_LAST_ORDER_ERR2", args);
								}
								outputMsgsForAvailsVct.addAll(mdlloVct);
								mdlloVct.clear();
								unmatchedFlags.clear();
							}
							// lastOrderAvlCtryTbl.clear();
						} // end plannedavail loop
						lastOrderAvlCtryTbl.clear();
					} // end osn loop

					// release memory
					availVct.clear();
					psplannedAvailVct.clear();
					pslastOrderAvailVct.clear();

					outputMsgsForAvailsVct.clear();
				} // end dochecks
			} // end (xx)prodstruct group loop
		} // end model LO avails exist
	}

	private void matchPsModelLastOrderAndMesLastOrderAvail(String availType, Vector mdlLOAvailOrMesLOAvailVctC,
			String statusFlag, EntityGroup psGrp, String featType, String psRelType)
			throws MiddlewareException, SQLException {
		int checklvl = getCheck_W_RW_RE(statusFlag);

		if (mdlLOAvailOrMesLOAvailVctC.size() > 0) {
			Hashtable mdlloAvailOSNTbl = new Hashtable();
			boolean mdlloOsnErrors = getAvailByOSN(mdlloAvailOSNTbl, mdlLOAvailOrMesLOAvailVctC, true, CHECKLEVEL_RE);
			addDebug("matchPsModelLastOrderAvail  mdlloOsnErrors " + mdlloOsnErrors + " mdlloAvailOSNTbl.keys "
					+ mdlloAvailOSNTbl.keySet());
			if (mdlloOsnErrors) {
				return;
			}

			for (int p = 0; p < psGrp.getEntityItemCount(); p++) {
				EntityItem psitem = psGrp.getEntityItem(p);
				boolean dochecks = true;
				if (psitem.getEntityType().equals("PRODSTRUCT")) {
					// 33.00 ORDERCODE 5957 I Initial
					String ordercode = PokUtils.getAttributeFlagValue(psitem, "ORDERCODE");
					addDebug("matchPsModelLastOrderAvail " + psitem.getKey() + " ordercode: " + ordercode);
					if (!ORDERCODE_INITIAL.equals(ordercode)) {
						dochecks = false;
						addDebug("     ordercode was not initial, skipping checks");
					} else {
						// 33.20 AND FCTYPE = "Primary FC (100) |""Secondary
						// FC"" (110)" RPQ logic
						EntityItem featitem = getUpLinkEntityItem(psitem, "FEATURE");
						addDebug("matchPsModelLastOrderAvail  " + psitem.getKey() + " " + featitem.getKey());
						if (isRPQ(featitem)) {
							addDebug(featitem.getKey() + " was an RPQ FCTYPE: "
									+ getAttributeFlagEnabledValue(featitem, "FCTYPE") + " skipping checks");
							dochecks = false;
						}
					}
				}
				if (dochecks) {
					/*
					 * 32.00 IF COUNTRYLIST Match D: AVAIL COUNTRYLIST
					 * OSN:XCC_LIST
					 * "D: PRODSTRUCT Planned Avail has PRODSTRUCT in this Country"
					 * 34.00 THEN TheMatch IN E: AVAIL COUNTRYLIST OSN:XCC_LIST
					 * W RW RE
					 * "E: PRODSTRUCT Last order ALL Features that are Initial must be withdrawn in all countries that the MODEL is being withdrawn"
					 * {LD: FEATURE} (NDN: E: FEATURE} must have a "Last Order"
					 * for all countries in the {LD: MODEL} {LD: AVAIL} {C:
					 * AVAIL} AND PRODSTRUCT SameParent D: AVAIL E: AVAIL Note:
					 * the AVAILs must have the same parent PRODSTRUCT 34.10
					 * EFFECTIVEDATE => E:AVAIL EFFECTIVEDATE OSN:XCC_LIST W RW
					 * RE
					 * "E: PRODSTRUCT Last order Features must be withdrawn on or before MODEL is withdrawn"
					 * {LD: PRODSTRUCT} (NDN: E: PRODSTRUCT} {LD: AVAIL} {E:
					 * AVAIL} must be no later than the {LD: MODEL} {LD:AVAIL}
					 * 34.20 END 34.00
					 * 
					 * 35.00 IF COUNTRYLIST Match H: AVAIL COUNTRYLIST
					 * OSN:XCC_LIST
					 * "S: SWPRODSTRUCT Planned Avail has SWPRODSTRUCT in this Country"
					 * 36.00 THEN TheMatch IN K: AVAIL COUNTRYLIST OSN:XCC_LIST
					 * W RW RE ALL SW Features must be withdrawn in all
					 * countries that the MODEL is being withdrawn {LD:
					 * SWFEATURE} (NDN: K: SWFEATURE} must have a "Last Order"
					 * for all countries in the {LD: MODEL} {LD: AVAIL} {C:
					 * AVAIL} 36.02 AND SWPRODSTRUCT SameParent H: AVAIL K:
					 * AVAIL 36.10 EFFECTIVEDATE => K:AVAIL EFFECTIVEDATE
					 * OSN:XCC_LIST W RW RE SW Features must be withdrawn on or
					 * before MODEL is withdrawn {LD: SWPRODSTRUCT} (NDN: K:
					 * SWPRODSTRUCT} {LD: AVAIL} {E: AVAIL} must be no later
					 * than the {LD: MODEL} {LD:AVAIL} 36.20 END 36.00
					 */
					Vector availVct = PokUtils.getAllLinkedEntities(psitem, psRelType, "AVAIL");
					Vector psplannedAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", PLANNEDAVAIL);
					Vector pslastOrderAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE",
							LASTORDERAVAIL);
					addDebug("matchPsModelLastOrderAvail " + psitem.getKey() + " all avail: " + availVct.size()
							+ " plaAvail: " + psplannedAvailVct.size() + " loAvail: " + pslastOrderAvailVct.size());

					if (LASTORDERAVAIL.endsWith(availType) && checkMdlLoAvailWithPsAvailDAndAvailE(pslastOrderAvailVct,
							psplannedAvailVct, psitem, mdlloAvailOSNTbl, featType, checklvl)) {
						continue;
					}
					// MES
					Vector psMesplannedAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE",
							MESPLANNEDAVAIL);
					Vector psMeslastOrderAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE",
							MESLASTORDERAVAIL);
					addDebug("matchPsModelLastOrderAvail " + psitem.getKey() + " all avail: " + availVct.size()
							+ " mesplaAvail: " + psMesplannedAvailVct.size() + " mesloAvail: "
							+ psMeslastOrderAvailVct.size());

					if (checkMdlLoAvailWithPsAvailDAndAvailE(psMeslastOrderAvailVct, psMesplannedAvailVct, psitem,
							mdlloAvailOSNTbl, featType, checklvl)) {
						continue;
					}
					availVct.clear();
				} // end dochecks
			} // end (xx)prodstruct group loop
		} // end model LO avails exist
	}

	private boolean checkMdlLoAvailWithPsAvailDAndAvailE(Vector pslastOrderAvailVct, Vector psplannedAvailVct,
			EntityItem psitem, Hashtable mdlloAvailOSNTbl, String featType, int checklvl)
			throws SQLException, MiddlewareException {
		Hashtable loAvailOSNTbl = new Hashtable();
		boolean loOsnErrors = getAvailByOSN(loAvailOSNTbl, pslastOrderAvailVct, true, CHECKLEVEL_RE);

		Hashtable plaAvailOSNTbl = new Hashtable();
		boolean plaOsnErrors = getAvailByOSN(plaAvailOSNTbl, psplannedAvailVct, true, CHECKLEVEL_RE);
		addDebug("matchPsModelLastOrderAvail " + psitem.getKey() + " loOsnErrors " + loOsnErrors
				+ " loAvailOSNTbl.keys " + loAvailOSNTbl.keySet() + " plaOsnErrors " + plaOsnErrors
				+ " plaAvailOSNTbl.keys " + plaAvailOSNTbl.keySet());

		if (plaOsnErrors || loOsnErrors) {
			return true;
		}
		// IF MODEL-LastOrderAVAIL.COUNTRYLIST Match PS-PlannedAVAIL COUNTRYLIST
		// BY OSN
		// THEN MODELAVAIL.COUNTRYLIST TheMatch PS-LastOrderAVAIL COUNTRYLIST W
		// RW RE
		// must have a "Last Order" {LD: AVAIL} corresponding to {LD: MODEL}
		// {LD: AVAIL} {NDN: AVAIL}
		// get the set of countries for PS lastorder avails

		Vector outputMsgsForAvailsVct = new Vector();
		Vector datechkForAvailsVct = new Vector();

		Set plaKeys = plaAvailOSNTbl.keySet();
		Iterator itr = plaKeys.iterator();
		while (itr.hasNext()) { // look at each set of planned avails for an osn
			String osn = (String) itr.next();
			// get avails from each table for this osn
			Vector plaOsnVct = (Vector) plaAvailOSNTbl.get(osn);
			Vector mdlloOsnVct = (Vector) mdlloAvailOSNTbl.get(osn);
			if (mdlloOsnVct == null) {
				addDebug("matchPsModelLastOrderAvail no mdl loavails to check for osn " + osn);
				continue;
			}
			Hashtable mdllastOrderAvlCtryTbl = getAvailByCountry(mdlloOsnVct, checklvl);
			addDebug("matchPsModelLastOrderAvail osn " + osn + " mdllastOrderAvlCtryTbl: "
					+ mdllastOrderAvlCtryTbl.keySet());
			Vector loOsnVct = (Vector) loAvailOSNTbl.get(osn);
			Hashtable lastOrderAvlCtryTbl = null;
			if (loOsnVct != null) {
				lastOrderAvlCtryTbl = getAvailByCountry(loOsnVct, checklvl);
			} else {
				lastOrderAvlCtryTbl = new Hashtable();
			}
			addDebug("matchPsModelLastOrderAvail loOsnVct: " + (loOsnVct == null ? "null" : "" + loOsnVct.size())
					+ " PS-lastOrderAvlCtry: " + lastOrderAvlCtryTbl.keySet());

			// For each PS->plannedAvail.ctry that matches the
			// MODEL->LastOrderAvail.ctry BY OSN
			// there must be a PS->LastOrderAvail.ctry by OSN
			for (int i = 0; i < plaOsnVct.size(); i++) {
				EntityItem avail = (EntityItem) plaOsnVct.elementAt(i);

				EANFlagAttribute ctrylist = (EANFlagAttribute) getAttrAndCheckLvl(avail, "COUNTRYLIST", checklvl);
				if (ctrylist != null && ctrylist.toString().length() > 0) {
					// Get the selected Flag codes.
					MetaFlag[] mfArray = (MetaFlag[]) ctrylist.get();
					ArrayList unmatchedFlags = new ArrayList();

					Vector mdlloVct = new Vector(); // hold onto model
													// lastorderavail in case
													// mult ctrys match
					for (int im = 0; im < mfArray.length; im++) {
						if (mfArray[im].isSelected()) {
							// get the MODEL-lastorderavail for this ctry and
							// osn
							EntityItem mdlloAvail = (EntityItem) mdllastOrderAvlCtryTbl.get(mfArray[im].getFlagCode());
							if (!lastOrderAvlCtryTbl.containsKey(mfArray[im].getFlagCode())) {
								addDebug("matchPsModelLastOrderAvail PS-plannedavail:" + avail.getKey()
										+ " No PS lastorderavail for osn " + osn + " ctry "
										+ mfArray[im].getFlagCode());
								if (mdlloAvail != null) {
									addDebug("matchPsModelLastOrderAvail PS-plannedavail:" + avail.getKey()
											+ " MODEL-lastorderavail " + mdlloAvail.getKey() + " match for ctry "
											+ mfArray[im].getFlagCode());
									if (outputMsgsForAvailsVct.contains(mdlloAvail)) {
										addDebug("already output msg for MODEL-lastorderavail " + mdlloAvail.getKey());
										continue;
									}
									if (!mdlloVct.contains(mdlloAvail)) {
										mdlloVct.add(mdlloAvail);
										unmatchedFlags.add(mfArray[im].getFlagCode());
									}
								}
							} else {
								if (mdlloAvail != null) {
									addDebug("matchPsModelLastOrderAvail PS-plannedavail:" + avail.getKey()
											+ " PS lastorder match ctry and osn " + osn + " MODEL-lastorderavail "
											+ mdlloAvail.getKey() + " match for ctry " + mfArray[im].getFlagCode());

									// get the ps-lastorderavail for this ctry
									// and osn
									EntityItem loAvail = (EntityItem) lastOrderAvlCtryTbl
											.get(mfArray[im].getFlagCode());

									if (datechkForAvailsVct.contains(loAvail)) {
										addDebug("already chk dates for ps-lastorderavail " + loAvail.getKey());
										continue;
									}
									datechkForAvailsVct.add(loAvail);

									// 34.10 EFFECTIVEDATE => E:AVAIL
									// EFFECTIVEDATE OSN:XCC_LIST W RW RE "E:
									// PRODSTRUCT Last order Features must be
									// withdrawn on or before MODEL is
									// withdrawn"
									// {LD: PRODSTRUCT} (NDN: E: PRODSTRUCT}
									// {LD: AVAIL} {E: AVAIL} must be no later
									// than the {LD: MODEL} {LD:AVAIL}
									// 36.10 EFFECTIVEDATE => K:AVAIL
									// EFFECTIVEDATE OSN:XCC_LIST W RW RE SW
									// Features must be withdrawn on or before
									// MODEL is withdrawn
									// {LD: SWPRODSTRUCT} (NDN: K: SWPRODSTRUCT}
									// {LD: AVAIL} {E: AVAIL} must be no later
									// than the {LD: MODEL} {LD:AVAIL}
									checkCanNotBeLater4(psitem, loAvail, "EFFECTIVEDATE", mdlloAvail, "EFFECTIVEDATE",
											checklvl);
								}
							}
						} // end ctry is selected
					}

					// output msg for all mdl lastorder that didnt have an ps
					// lastorder
					for (int m = 0; m < mdlloVct.size(); m++) {
						EntityItem mdlloAvail = (EntityItem) mdlloVct.elementAt(m);
						// PS_LAST_ORDER_ERR2 = {0} must have a &quot;Last
						// Order&quot; for all countries in the {1} {2}. Missing
						// countries: {3}
						// {LD: FEATURE} (NDN: E: FEATURE} must have a "Last
						// Order" for all countries in the {LD: MODEL} {LD:
						// AVAIL} {C: AVAIL}
						args[0] = getLD_NDN(getUpLinkEntityItem(psitem, featType));
						args[1] = m_elist.getParentEntityGroup().getLongDescription();
						if (osn.equals(DOMAIN_NOT_IN_LIST)) {
							args[2] = getLD_NDN(mdlloAvail);
						} else {
							args[2] = getLD_NDN(mdlloAvail) + " for " + getLD_Value(mdlloAvail, "ORDERSYSNAME");
						}
						args[3] = getUnmatchedDescriptions(mdlloAvail, "COUNTRYLIST", unmatchedFlags);
						createMessage(checklvl, "PS_LAST_ORDER_ERR2", args);
					}
					outputMsgsForAvailsVct.addAll(mdlloVct);
					mdlloVct.clear();
					unmatchedFlags.clear();
				}
				// lastOrderAvlCtryTbl.clear();
			} // end plannedavail loop
			lastOrderAvlCtryTbl.clear();
		} // end osn loop

		// release memory
		outputMsgsForAvailsVct.clear();

		return false;
	}

	private void matchPsModelLastOrderAvail(Vector mdlLOOrMesLOAvailVctC, String statusFlag, EntityGroup psGrp,
			String featType, String psRelType) throws MiddlewareException, SQLException {
		int checklvl = getCheck_W_RW_RE(statusFlag);

		if (mdlLOOrMesLOAvailVctC.size() > 0) {
			Hashtable mdlloAvailOSNTbl = new Hashtable();
			boolean mdlloOsnErrors = getAvailByOSN(mdlloAvailOSNTbl, mdlLOOrMesLOAvailVctC, true, CHECKLEVEL_RE);
			addDebug("matchPsModelLastOrderAvail  mdlloOrMesloOsnErrors " + mdlloOsnErrors
					+ " mdlloOrMesloAvailOSNTbl.keys " + mdlloAvailOSNTbl.keySet());
			if (mdlloOsnErrors) {
				return;
			}

			for (int p = 0; p < psGrp.getEntityItemCount(); p++) {
				EntityItem psitem = psGrp.getEntityItem(p);
				boolean dochecks = true;
				if (psitem.getEntityType().equals("PRODSTRUCT")) {
					// 33.00 ORDERCODE 5957 I Initial
					String ordercode = PokUtils.getAttributeFlagValue(psitem, "ORDERCODE");
					addDebug(
							"matchPsModelLastOrderOrMesLastOrderAvail " + psitem.getKey() + " ordercode: " + ordercode);
					if (!ORDERCODE_INITIAL.equals(ordercode)) {
						dochecks = false;
						addDebug("     ordercode was not initial, skipping checks");
					} else {
						// 33.20 AND FCTYPE = "Primary FC (100) |""Secondary
						// FC"" (110)" RPQ logic
						EntityItem featitem = getUpLinkEntityItem(psitem, "FEATURE");
						addDebug("matchPsModelLastOrderOrMesLastOrderAvail  " + psitem.getKey() + " "
								+ featitem.getKey());
						if (isRPQ(featitem)) {
							addDebug(featitem.getKey() + " was an RPQ FCTYPE: "
									+ getAttributeFlagEnabledValue(featitem, "FCTYPE") + " skipping checks");
							dochecks = false;
						}
					}
				}
				if (dochecks) {
					/*
					 * 32.00 IF COUNTRYLIST Match D: AVAIL COUNTRYLIST
					 * OSN:XCC_LIST
					 * "D: PRODSTRUCT Planned Avail has PRODSTRUCT in this Country"
					 * 34.00 THEN TheMatch IN E: AVAIL COUNTRYLIST OSN:XCC_LIST
					 * W RW RE
					 * "E: PRODSTRUCT Last order ALL Features that are Initial must be withdrawn in all countries that the MODEL is being withdrawn"
					 * {LD: FEATURE} (NDN: E: FEATURE} must have a "Last Order"
					 * for all countries in the {LD: MODEL} {LD: AVAIL} {C:
					 * AVAIL} AND PRODSTRUCT SameParent D: AVAIL E: AVAIL Note:
					 * the AVAILs must have the same parent PRODSTRUCT 34.10
					 * EFFECTIVEDATE => E:AVAIL EFFECTIVEDATE OSN:XCC_LIST W RW
					 * RE
					 * "E: PRODSTRUCT Last order Features must be withdrawn on or before MODEL is withdrawn"
					 * {LD: PRODSTRUCT} (NDN: E: PRODSTRUCT} {LD: AVAIL} {E:
					 * AVAIL} must be no later than the {LD: MODEL} {LD:AVAIL}
					 * 34.20 END 34.00
					 * 
					 * 35.00 IF COUNTRYLIST Match H: AVAIL COUNTRYLIST
					 * OSN:XCC_LIST
					 * "S: SWPRODSTRUCT Planned Avail has SWPRODSTRUCT in this Country"
					 * 36.00 THEN TheMatch IN K: AVAIL COUNTRYLIST OSN:XCC_LIST
					 * W RW RE ALL SW Features must be withdrawn in all
					 * countries that the MODEL is being withdrawn {LD:
					 * SWFEATURE} (NDN: K: SWFEATURE} must have a "Last Order"
					 * for all countries in the {LD: MODEL} {LD: AVAIL} {C:
					 * AVAIL} 36.02 AND SWPRODSTRUCT SameParent H: AVAIL K:
					 * AVAIL 36.10 EFFECTIVEDATE => K:AVAIL EFFECTIVEDATE
					 * OSN:XCC_LIST W RW RE SW Features must be withdrawn on or
					 * before MODEL is withdrawn {LD: SWPRODSTRUCT} (NDN: K:
					 * SWPRODSTRUCT} {LD: AVAIL} {E: AVAIL} must be no later
					 * than the {LD: MODEL} {LD:AVAIL} 36.20 END 36.00
					 */
					Vector availVct = PokUtils.getAllLinkedEntities(psitem, psRelType, "AVAIL");
					Vector psplannedAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", PLANNEDAVAIL);
					Vector psMesPlannedAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE",
							MESPLANNEDAVAIL);
					Vector pslastOrderAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE",
							LASTORDERAVAIL);
					Vector psMeslastOrderAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE",
							MESLASTORDERAVAIL);
					addDebug("matchPsModelLastOrderAvail " + psitem.getKey() + " all avail: " + availVct.size()
							+ " plaAvail: " + psplannedAvailVct.size() + " loAvail: " + pslastOrderAvailVct.size()
							+ " mesPlaAvail: " + psMesPlannedAvailVct.size() + " mesloAvail: "
							+ psMeslastOrderAvailVct.size());

					Hashtable loAvailOSNTbl = new Hashtable();
					boolean loOsnErrors = getAvailByOSN(loAvailOSNTbl, pslastOrderAvailVct, true, CHECKLEVEL_RE);
					Hashtable mesloAvailOSNTbl = new Hashtable();
					boolean mesloOsnErrors = getAvailByOSN(mesloAvailOSNTbl, psMeslastOrderAvailVct, true,
							CHECKLEVEL_RE);

					Hashtable plaAvailOSNTbl = new Hashtable();
					boolean plaOsnErrors = getAvailByOSN(plaAvailOSNTbl, psplannedAvailVct, true, CHECKLEVEL_RE);
					Hashtable mesPlaAvailOSNTbl = new Hashtable();
					boolean mesPlaOsnErrors = getAvailByOSN(mesPlaAvailOSNTbl, psMesPlannedAvailVct, true,
							CHECKLEVEL_RE);

					addDebug("matchPsModelLastOrderAvail " + psitem.getKey() + " loOsnErrors " + loOsnErrors
							+ " loAvailOSNTbl.keys " + loAvailOSNTbl.keySet() + " plaOsnErrors " + plaOsnErrors
							+ " plaAvailOSNTbl.keys " + plaAvailOSNTbl.keySet() + " mesplaOsnErrors " + mesPlaOsnErrors
							+ " mesplaAvailOSNTbl.keys " + mesPlaAvailOSNTbl.keySet() + " mesloOsnErrors "
							+ mesloOsnErrors + " mesloAvailOSNTbl.keys " + mesloAvailOSNTbl.keySet());
					// ==================================================================================================================================
					if (plaOsnErrors || loOsnErrors) {
						continue;
					}
					// IF MODEL-LastOrderAVAIL.COUNTRYLIST Match PS-PlannedAVAIL
					// COUNTRYLIST BY OSN
					// THEN MODELAVAIL.COUNTRYLIST TheMatch PS-LastOrderAVAIL
					// COUNTRYLIST W RW RE
					// must have a "Last Order" {LD: AVAIL} corresponding to
					// {LD: MODEL} {LD: AVAIL} {NDN: AVAIL}
					// get the set of countries for PS lastorder avails

					Vector outputMsgsForAvailsVct = new Vector();
					Vector datechkForAvailsVct = new Vector();

					Set plaKeys = plaAvailOSNTbl.keySet();
					Iterator itr = plaKeys.iterator();
					while (itr.hasNext()) { // look at each set of planned
											// avails for an osn
						String osn = (String) itr.next();
						// get avails from each table for this osn
						Vector plaOsnVct = (Vector) plaAvailOSNTbl.get(osn);
						Vector mdlloOsnVct = (Vector) mdlloAvailOSNTbl.get(osn);
						if (mdlloOsnVct == null) {
							addDebug(
									"matchPsModelLastOrderOrMesLastOrderAvail no mdl loavails to check for osn " + osn);
							continue;
						}
						Hashtable mdllastOrderAvlCtryTbl = getAvailByCountry(mdlloOsnVct, checklvl);
						addDebug("matchPsModelLastOrderAvail osn " + osn + " mdllastOrderAvlCtryTbl: "
								+ mdllastOrderAvlCtryTbl.keySet());
						Vector loOsnVct = (Vector) loAvailOSNTbl.get(osn);
						Hashtable lastOrderAvlCtryTbl = null;
						if (loOsnVct != null) {
							lastOrderAvlCtryTbl = getAvailByCountry(loOsnVct, checklvl);
						} else {
							lastOrderAvlCtryTbl = new Hashtable();
						}
						addDebug("matchPsModelLastOrderAvail loOsnVct: "
								+ (loOsnVct == null ? "null" : "" + loOsnVct.size()) + " PS-lastOrderAvlCtry: "
								+ lastOrderAvlCtryTbl.keySet());

						// For each PS->plannedAvail.ctry that matches the
						// MODEL->LastOrderAvail.ctry BY OSN
						// there must be a PS->LastOrderAvail.ctry by OSN
						for (int i = 0; i < plaOsnVct.size(); i++) {
							EntityItem avail = (EntityItem) plaOsnVct.elementAt(i);

							EANFlagAttribute ctrylist = (EANFlagAttribute) getAttrAndCheckLvl(avail, "COUNTRYLIST",
									checklvl);
							if (ctrylist != null && ctrylist.toString().length() > 0) {
								// Get the selected Flag codes.
								MetaFlag[] mfArray = (MetaFlag[]) ctrylist.get();
								ArrayList unmatchedFlags = new ArrayList();

								Vector mdlloVct = new Vector(); // hold onto
																// model
																// lastorderavail
																// in case mult
																// ctrys match
								for (int im = 0; im < mfArray.length; im++) {
									if (mfArray[im].isSelected()) {
										// get the MODEL-lastorderavail for this
										// ctry and osn
										EntityItem mdlloAvail = (EntityItem) mdllastOrderAvlCtryTbl
												.get(mfArray[im].getFlagCode());
										if (!lastOrderAvlCtryTbl.containsKey(mfArray[im].getFlagCode())) {
											addDebug("matchPsModelLastOrderAvail PS-plannedavail:" + avail.getKey()
													+ " No PS lastorderavail for osn " + osn + " ctry "
													+ mfArray[im].getFlagCode());
											if (mdlloAvail != null) {
												addDebug("matchPsModelLastOrderAvail PS-plannedavail:" + avail.getKey()
														+ " MODEL-lastorderavail " + mdlloAvail.getKey()
														+ " match for ctry " + mfArray[im].getFlagCode());
												if (outputMsgsForAvailsVct.contains(mdlloAvail)) {
													addDebug("already output msg for MODEL-lastorderavail "
															+ mdlloAvail.getKey());
													continue;
												}
												if (!mdlloVct.contains(mdlloAvail)) {
													mdlloVct.add(mdlloAvail);
													unmatchedFlags.add(mfArray[im].getFlagCode());
												}
											}
										} else {
											if (mdlloAvail != null) {
												addDebug("matchPsModelLastOrderAvail PS-plannedavail:" + avail.getKey()
														+ " PS lastorder match ctry and osn " + osn
														+ " MODEL-lastorderavail " + mdlloAvail.getKey()
														+ " match for ctry " + mfArray[im].getFlagCode());

												// get the ps-lastorderavail for
												// this ctry and osn
												EntityItem loAvail = (EntityItem) lastOrderAvlCtryTbl
														.get(mfArray[im].getFlagCode());

												if (datechkForAvailsVct.contains(loAvail)) {
													addDebug("already chk dates for ps-lastorderavail "
															+ loAvail.getKey());
													continue;
												}
												datechkForAvailsVct.add(loAvail);

												// 34.10 EFFECTIVEDATE =>
												// E:AVAIL EFFECTIVEDATE
												// OSN:XCC_LIST W RW RE "E:
												// PRODSTRUCT Last order
												// Features must be withdrawn on
												// or before MODEL is withdrawn"
												// {LD: PRODSTRUCT} (NDN: E:
												// PRODSTRUCT} {LD: AVAIL} {E:
												// AVAIL} must be no later than
												// the {LD: MODEL} {LD:AVAIL}
												// 36.10 EFFECTIVEDATE =>
												// K:AVAIL EFFECTIVEDATE
												// OSN:XCC_LIST W RW RE SW
												// Features must be withdrawn on
												// or before MODEL is withdrawn
												// {LD: SWPRODSTRUCT} (NDN: K:
												// SWPRODSTRUCT} {LD: AVAIL} {E:
												// AVAIL} must be no later than
												// the {LD: MODEL} {LD:AVAIL}
												checkCanNotBeLater4(psitem, loAvail, "EFFECTIVEDATE", mdlloAvail,
														"EFFECTIVEDATE", checklvl);
											}
										}
									} // end ctry is selected
								}

								// output msg for all mdl lastorder that didnt
								// have an ps lastorder
								for (int m = 0; m < mdlloVct.size(); m++) {
									EntityItem mdlloAvail = (EntityItem) mdlloVct.elementAt(m);
									// PS_LAST_ORDER_ERR2 = {0} must have a
									// &quot;Last Order&quot; for all countries
									// in the {1} {2}. Missing countries: {3}
									// {LD: FEATURE} (NDN: E: FEATURE} must have
									// a "Last Order" for all countries in the
									// {LD: MODEL} {LD: AVAIL} {C: AVAIL}
									args[0] = getLD_NDN(getUpLinkEntityItem(psitem, featType));
									args[1] = m_elist.getParentEntityGroup().getLongDescription();
									if (osn.equals(DOMAIN_NOT_IN_LIST)) {
										args[2] = getLD_NDN(mdlloAvail);
									} else {
										args[2] = getLD_NDN(mdlloAvail) + " for "
												+ getLD_Value(mdlloAvail, "ORDERSYSNAME");
									}
									args[3] = getUnmatchedDescriptions(mdlloAvail, "COUNTRYLIST", unmatchedFlags);
									createMessage(checklvl, "PS_LAST_ORDER_ERR2", args);
								}
								outputMsgsForAvailsVct.addAll(mdlloVct);
								mdlloVct.clear();
								unmatchedFlags.clear();
							}
							// lastOrderAvlCtryTbl.clear();
						} // end plannedavail loop
						lastOrderAvlCtryTbl.clear();
					} // end osn loop

					// release memory
					availVct.clear();
					psplannedAvailVct.clear();
					pslastOrderAvailVct.clear();

					outputMsgsForAvailsVct.clear();
				} // end dochecks
			} // end (xx)prodstruct group loop
		} // end model LO avails exist
	}

	/***************************************
	 * 
	 * For each PS->plannedAvail.ctry that matches the
	 * MODEL->LastOrderAvail.ctry there must be a PS->LastOrderAvail.ctry PER
	 * PRODSTRUCT
	 * 
	 * @param statusFlag
	 * @param psGrp
	 * @param featType
	 * @param psRelType
	 * @throws MiddlewareException
	 * @throws SQLException
	 * 
	 *             27.00 AVAIL C MODELAVAIL-d MODEL AVAIL 28.00 WHEN AVAILTYPE =
	 *             "Last Order"
	 * 
	 *             old32.00 IF COUNTRYLIST Match D: AVAIL COUNTRYLIST has
	 *             PRODSTRUCT in this Country old33.00 AND "Initial" =
	 *             PRODSTRUCT ORDERCODE old33.20 AND FCTYPE = "Primary FC (100)
	 *             |""Secondary FC"" (110)" RPQ logic old34.00 THEN TheMatch IN
	 *             E: AVAIL COUNTRYLIST W RW RE ALL Features that are Initial
	 *             must be withdrawn in all countries that the MODEL is being
	 *             withdrawn {LD: FEATURE} (NDN: E: FEATURE} must have a "Last
	 *             Order" for all countries in the {LD: MODEL} {LD: AVAIL} {C:
	 *             AVAIL} old35.00 IF COUNTRYLIST Match H: AVAIL COUNTRYLIST has
	 *             SWPRODSTRUCT in this Country old36.00 THEN TheMatch IN K:
	 *             AVAIL COUNTRYLIST W RW RE ALL SW Features must be withdrawn
	 *             in all countries that the MODEL is being withdrawn {LD:
	 *             SWFEATURE} (NDN: K: SWFEATURE} must have a "Last Order" for
	 *             all countries in the {LD: MODEL} {LD: AVAIL} {C: AVAIL}
	 *
	 *
	 *             private void matchPsModelLastOrderAvail(String statusFlag,
	 *             EntityGroup psGrp, String featType, String psRelType) throws
	 *             MiddlewareException, SQLException { int checklvl =
	 *             getCheck_W_RW_RE(statusFlag);
	 * 
	 *             if (mdlLOAvailCtryTblC.size()>0){ for(int p=0;
	 *             p<psGrp.getEntityItemCount(); p++){ EntityItem psitem =
	 *             psGrp.getEntityItem(p); boolean dochecks = true; if
	 *             (psitem.getEntityType().equals("PRODSTRUCT")){ //33.00
	 *             ORDERCODE 5957 I Initial String ordercode =
	 *             PokUtils.getAttributeFlagValue(psitem, "ORDERCODE");
	 *             addDebug("matchPsModelLastOrderAvail "+psitem.getKey()+"
	 *             ordercode: "+ordercode); if
	 *             (!ORDERCODE_INITIAL.equals(ordercode)){ dochecks = false;
	 *             addDebug(" ordercode was not initial, skipping checks");
	 *             }else{ //33.20 AND FCTYPE = "Primary FC (100) |""Secondary
	 *             FC"" (110)" RPQ logic EntityItem featitem =
	 *             getUpLinkEntityItem(psitem, "FEATURE");
	 *             addDebug("matchPsModelLastOrderAvail "+psitem.getKey()+"
	 *             "+featitem.getKey()); if (isRPQ(featitem)){
	 *             addDebug(featitem.getKey()+" was an RPQ FCTYPE: "+
	 *             getAttributeFlagEnabledValue(featitem, "FCTYPE")+" skipping
	 *             checks"); dochecks = false; } } } if (dochecks){ Vector
	 *             availVct = PokUtils.getAllLinkedEntities(psitem, psRelType,
	 *             "AVAIL"); Vector psplannedAvailVct =
	 *             PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE",
	 *             PLANNEDAVAIL); Vector pslastOrderAvailVct =
	 *             PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE",
	 *             LASTORDERAVAIL); addDebug("matchPsModelLastOrderAvail
	 *             "+psitem.getKey()+" all avail: "+availVct.size()+ " plaAvail:
	 *             "+psplannedAvailVct.size()+" loAvail:
	 *             "+pslastOrderAvailVct.size());
	 * 
	 *             // IF MODEL-LastOrderAVAIL.COUNTRYLIST Match PS-PlannedAVAIL
	 *             COUNTRYLIST // THEN MODELAVAIL.COUNTRYLIST TheMatch
	 *             PS-LastOrderAVAIL COUNTRYLIST W RW RE //must have a "Last
	 *             Order" {LD: AVAIL} corresponding to {LD: MODEL} {LD: AVAIL}
	 *             {NDN: AVAIL} // get the set of countries for PS lastorder
	 *             avails ArrayList lastOrderAvlCtry =
	 *             getCountriesAsList(pslastOrderAvailVct, checklvl);
	 *             addDebug("matchPsModelLastOrderAvail PS-lastOrderAvlCtry:
	 *             "+lastOrderAvlCtry);
	 * 
	 *             Vector outputMsgsForAvailsVct = new Vector(); // For each
	 *             PS->plannedAvail.ctry that matches the
	 *             MODEL->LastOrderAvail.ctry // there must be a
	 *             PS->LastOrderAvail.ctry for (int i=0;
	 *             i<psplannedAvailVct.size(); i++){ EntityItem avail =
	 *             (EntityItem)psplannedAvailVct.elementAt(i);
	 * 
	 *             EANFlagAttribute ctrylist =
	 *             (EANFlagAttribute)getAttrAndCheckLvl(avail, "COUNTRYLIST",
	 *             checklvl); if (ctrylist != null &&
	 *             ctrylist.toString().length()>0) { // Get the selected Flag
	 *             codes. MetaFlag[] mfArray = (MetaFlag[]) ctrylist.get();
	 *             ArrayList unmatchedFlags = new ArrayList();
	 * 
	 *             Vector mdlloVct = new Vector(); // hold onto model
	 *             lastorderavail in case mult ctrys match for (int im = 0; im <
	 *             mfArray.length; im++) { if (mfArray[im].isSelected() &&
	 *             !lastOrderAvlCtry.contains(mfArray[im].getFlagCode())){
	 *             addDebug("matchPsModelLastOrderAvail
	 *             PS-plannedavail:"+avail.getKey()+ " No PS lastorderavail for
	 *             ctry "+mfArray[im].getFlagCode()); // get the
	 *             MODEL-lastorderavail for this ctry EntityItem mdlloAvail =
	 *             (EntityItem)mdlLOAvailCtryTblC.get(mfArray[im].getFlagCode());
	 *             if (mdlloAvail!=null){ addDebug("matchPsModelLastOrderAvail
	 *             PS-plannedavail:"+avail.getKey()+ " MODEL-lastorderavail
	 *             "+mdlloAvail.getKey()+" match for ctry "+
	 *             mfArray[im].getFlagCode());
	 *             if(outputMsgsForAvailsVct.contains(mdlloAvail)){
	 *             addDebug("already output msg for MODEL-lastorderavail
	 *             "+mdlloAvail.getKey()); continue; } if
	 *             (!mdlloVct.contains(mdlloAvail)){ mdlloVct.add(mdlloAvail);
	 *             unmatchedFlags.add(mfArray[im].getFlagCode()); } } } }
	 * 
	 *             // output msg for all mdl lastorder that didnt have an ps
	 *             lastorder for (int m=0; m<mdlloVct.size(); m++){ EntityItem
	 *             mdlloAvail = (EntityItem)mdlloVct.elementAt(m);
	 *             //PS_LAST_ORDER_ERR2 = {0} must have a &quot;Last Order&quot;
	 *             for all countries in the {1} {2}. Missing countries: {3}
	 *             //{LD: FEATURE} (NDN: E: FEATURE} must have a "Last Order"
	 *             for all countries in the {LD: MODEL} {LD: AVAIL} {C: AVAIL}
	 *             args[0]=getLD_NDN(getUpLinkEntityItem(psitem, featType));
	 *             args[1]=m_elist.getParentEntityGroup().getLongDescription();
	 *             args[2]=getLD_NDN(mdlloAvail);
	 *             args[3]=getUnmatchedDescriptions(mdlloAvail,
	 *             "COUNTRYLIST",unmatchedFlags);
	 *             createMessage(checklvl,"PS_LAST_ORDER_ERR2",args); }
	 *             outputMsgsForAvailsVct.addAll(mdlloVct); mdlloVct.clear();
	 *             unmatchedFlags.clear(); } } // end plannedavail loop
	 * 
	 *             // release memory availVct.clear();
	 *             psplannedAvailVct.clear(); pslastOrderAvailVct.clear();
	 *             lastOrderAvlCtry.clear(); outputMsgsForAvailsVct.clear(); }
	 *             // end dochecks } // end (xx)prodstruct group loop } // end
	 *             model LO avails exist }
	 */
	/****************************
	 * For HW models - there must be at least one FEATURE - there cant be any
	 * SVCFEATURE or SWFEATURE - All PRODSTRUCT-OOFAVAIL-AVAIL plannedavail
	 * countries must be a subset of all MODELAVAIL-plannedavail ctrys - By
	 * country PRODSTRUCT-OOFAVAIL-AVAIL plannedavail effdate
	 * =>MODELAVAIL-plannedavail effdate for same ctry
	 * 
	 * - All PRODSTRUCT-OOFAVAIL-AVAIL firstorder countries must be a subset of
	 * all OOFAVAIL-plannedavail ctrys - By country PRODSTRUCT-OOFAVAIL-AVAIL
	 * firstorder effdate =>MODELAVAIL-plannedavail effdate for same ctry
	 * 
	 * - All PRODSTRUCT-OOFAVAIL-AVAIL lastorder countries must be a subset of
	 * all OOFAVAIL-plannedavail ctrys - If PRODSTRUCT with a lastorder avail
	 * has "Initial" = PRODSTRUCT ORDERCODE then the PRODSTRUCT-OOFAVAIL-AVAIL
	 * lastorder effdate must be <= MODELAVAIL-lastorder effdate for same ctry
	 * 
	 * @param modelItem
	 * @param statusFlag
	 * @throws MiddlewareException
	 * @throws SQLException
	 * 
	 *             checks from ss 44.00 MODEL Root Hardware MODEL 45.00 WHEN
	 *             COFCAT = "Hardware" (100) 46.00 FEATURE PRODSTRUCT-u 46.80 IF
	 *             "No" (FEATN) <> MODEL.FEATINDC 47.00 CountOf => 1 RE RE RE
	 *             must have at least one {LD: FEATURE} 47.20 END 48.00
	 *             SWFEATURE SWPRODSTRUCT-u 49.00 CountOf = 0 RE RE RE must not
	 *             have any {LD: SWFEATURE) {NDN: SWFEATURE} 52.00 AVAIL D
	 *             PRODSTRUCT: OOFAVAIL-d: AVAIL TMF AVAIL 53.00 WHEN AVAILTYPE
	 *             = "Planned Availability" 54.00 ANNCODENAME xx55.00
	 *             COUNTRYLIST "in aggregate G" A: AVAIL COUNTRYLIST W W E*2 TMF
	 *             can only be available where the MODEL is available {LD:
	 *             PRODSTRUCT} {NDN: PRODSTRUCT}{LD: AVAIL} {NDN: A: AVAIL} {LD:
	 *             COUNTRYLIST} includes a Country that the {LD: MODEL} is not
	 *             available in. xx56.00 EFFECTIVEDATE => A: AVAIL EFFECTIVEDATE
	 *             Yes W W E*3 can not be available before the MODEL {LD:
	 *             PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} must
	 *             not be earlier than the {LD: MODEL} {LD:AVAIL} {NDN: A:
	 *             AVAIL} Change 20111216 55.00 COUNTRYLIST "IN aggregate G" A:
	 *             AVAIL COUNTRYLIST OSN:XCC_LIST W W E*2 TMF can only be
	 *             available where the MODEL is available {LD: PRODSTRUCT} {NDN:
	 *             PRODSTRUCT}{LD: AVAIL} {NDN: A: AVAIL} {LD: COUNTRYLIST}
	 *             includes a Country that the {LD: MODEL} is not available in.
	 *             Change 20111216 56.00 EFFECTIVEDATE => A: AVAIL EFFECTIVEDATE
	 *             Ctry OSN:XCC_LIST W W E*3 can not be available before the
	 *             MODEL {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN:
	 *             AVAIL} must not be earlier than the {LD: MODEL} {LD:AVAIL}
	 *             {NDN: A: AVAIL} 57.00 END 53 58.00 AVAIL PRODSTRUCT:
	 *             OOFAVAIL-d: AVAIL TMF AVAIL 59.00 WHEN AVAILTYPE = "First
	 *             Order" 60.00 ANNCODENAME xx61.00 COUNTRYLIST "in aggregate G"
	 *             D: AVAIL COUNTRYLIST W W E*2 TMF can only be available where
	 *             the MODEL is available {LD: PRODSTRUCT} {NDN: PRODSTRUCT}{LD:
	 *             AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a Country that
	 *             does not have a "Planned Availability" xx62.00 EFFECTIVEDATE
	 *             => A: AVAIL EFFECTIVEDATE Yes W W E*3 can not be available
	 *             before the MODEL {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD:
	 *             AVAIL} {NDN: AVAIL} must not be earlier than the {LD:MODEL}
	 *             {LD:AVAIL} {NDN: A: AVAIL} Change 20111216 61.00 COUNTRYLIST
	 *             "IN aggregate G" D: AVAIL COUNTRYLIST OSN W W E*2 TMF can
	 *             only be available where the MODEL is available {LD:
	 *             PRODSTRUCT} {NDN: PRODSTRUCT}{LD: AVAIL} {NDN: AVAIL} {LD:
	 *             COUNTRYLIST} includes a Country that does not have a "Planned
	 *             Availability" Change 20111216 62.00 EFFECTIVEDATE => A: AVAIL
	 *             EFFECTIVEDATE Both W W E*3 can not be available before the
	 *             MODEL {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN:
	 *             AVAIL} must not be earlier than the {LD:MODEL} {LD:AVAIL}
	 *             {NDN: A: AVAIL} 63.00 END 59 64.00 AVAIL E PRODSTRUCT:
	 *             OOFAVAIL-d: AVAIL TMF AVAIL 65.00 WHEN AVAILTYPE = "Last
	 *             Order" 66.00 ANNCODENAME xx67.00 COUNTRYLIST IN D: AVAIL
	 *             COUNTRYLIST W W E*2 TMF can only be withdrawn where it has
	 *             been announced {LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST}
	 *             includes a country that does not have a "Planned
	 *             Availability" Change 20111216 67.00 COUNTRYLIST IN D: AVAIL
	 *             COUNTRYLIST OSN W W E*2 TMF can only be withdrawn where it
	 *             has been announced {LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST}
	 *             includes a country that does not have a "Planned
	 *             Availability" 68.00 IF "Initial" = PRODSTRUCT ORDERCODE
	 *             xx69.00 THEN EFFECTIVEDATE <= C: AVAIL EFFECTIVEDATE Yes W W
	 *             E*3 TMF must be withdrawn on or before the MODEL {LD:
	 *             PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} must
	 *             not be later than the {LD:MODEL} {LD:AVAIL} {NDN: C: AVAIL}
	 *             Change 20111216 69.00 THEN EFFECTIVEDATE <= C: AVAIL
	 *             EFFECTIVEDATE Both W W E*3 TMF must be withdrawn on or before
	 *             the MODEL {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL}
	 *             {NDN: AVAIL} must not be later than the {LD:MODEL} {LD:AVAIL}
	 *             {NDN: C: AVAIL} 70.00 END 65 71.00 FEATURE F PRODSTRUCT-u RPQ
	 *             FEATURE 72.00 WHEN FCTYPE <> "Primary FC (100) |""Secondary
	 *             FC"" (110)" 73.00 PRODSTRUCT F: 74.00 ANNDATE => M:
	 *             ANNOUNCEMENT ANNDATE W W E {LD: PRODSTRUCT} {NDN: PRODSTRUCT}
	 *             {LD: ANNDATE} {ANNDATE} can not be earlier than the {LD:
	 *             ANNOUNCEMENT} {NDN: M: ANNOUNCEMENT} {LD: ANNDATE} {M:
	 *             ANNDATE} 75.00 GENAVAILDATE => A: AVAIL EFFECTIVEDATE W W E
	 *             {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: GENAVAILDATE}
	 *             {GENAVAILDATE} can not be earlier than the {LD: AVAIL} {NDN:
	 *             A: AVAIL} xx76.00 WITHDRAWDATE <= N: ANNOUNCEMENT ANNDATE W W
	 *             E {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: WITHDRAWDATE}
	 *             {WITHDRAWDATE} can not be later than the {LD: ANNOUNCEMENT}
	 *             {NDN: N: ANNOUNCEMENT} {LD: ANNDATE} {N: ANNDATE} Change
	 *             20120426 76.00 WITHDRAWDATE => N: ANNOUNCEMENT ANNDATE W W E
	 *             {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: WITHDRAWDATE}
	 *             {WITHDRAWDATE} can not be earlier than the {LD: ANNOUNCEMENT}
	 *             {NDN: N: ANNOUNCEMENT} {LD: ANNDATE} {N: ANNDATE}
	 * 
	 *             77.00 WTHDRWEFFCTVDATE <= C: AVAIL EFFECTIVEDATE W W E {LD:
	 *             PRODSTRUCT} {NDN: PRODSTRUCT} {LD: WTHDRWEFFCTVDATE}
	 *             {WTHDRWEFFCTVDATE} can not be later than the {LD: AVAIL}
	 *             {NDN: C: AVAIL} 78.00 END 72 91.00 WARR MODELWARR-d WARR
	 *             91.10 IF "No Warranty" <> MODEL WARRSVCCOVR 91.18 CountOf =>
	 *             1 RW RW RE Required on all Hardware must have at least one
	 *             Warranty 92.00 WARRCoverage EntityType Yes RW RE RE Required
	 *             if XCC {LD: WARR} {NDN: WARR} have gaps in the date range.
	 *             92.10 COUNTRYLIST "Contains aggregate E" A: AVAIL COUNTRYLIST
	 *             E E E Column E - attributes being moved to MODELWARR must
	 *             have a {LD: WARR} for every country in the {LD: MODEL} {LD:
	 *             AVAIL} {NDN: A: AVAIL} 92.20 MIN(EFFECTIVEDATE) <= A: AVAIL
	 *             EFFECTIVEDATE Yes E E E Column E - attributes being moved to
	 *             MODELWARR must have a {LD: WARR} with an EFFECTIVE DATE as
	 *             early as or earlier than the {LD: MODEL} {LD: AVAIL} {NDN: A:
	 *             AVAIL} 92.30 MAX(ENDDATE) => C: AVAIL EFFECTIVEDATE Yes E E E
	 *             Column E - attributes being moved to MODELWARR must have a
	 *             {LD: WARR} with a ENDATE as late as or later than the {LD:
	 *             MODEL} {LD: AVAIL} {NDN: C: AVAIL} 95.02 ELSE 95.04 CountOf =
	 *             0 E E E must not have any {LD: WARR} {NDN: WARR} 95.06 END
	 *             91.10
	 * 
	 *             79.00 END 45 Hardware Model - End
	 * 
	 */
	private void doHardwareChecks(EntityItem mdlItem, String statusFlag) throws MiddlewareException, SQLException {
		int checklvl = getCheck_W_W_E(statusFlag);
		String modelCOFSUBCAT = getAttributeFlagEnabledValue(mdlItem, "COFSUBCAT");
		String modelFEATINDC = getAttributeFlagEnabledValue(mdlItem, "FEATINDC");
		addDebug("doHardwareChecks " + mdlItem.getKey() + " COFSUBCAT: " + modelCOFSUBCAT + " FEATINDC: "
				+ modelFEATINDC);

		addHeading(3, "Hardware Model Checks:");

		int cnt = 0;

		// 46.00 FEATURE PRODSTRUCT-u
		// 46.80 IF "No" (FEATN) <> MODEL.FEATINDC
		if (!FEATINDC_No.equals(modelFEATINDC)) {
			cnt = getCount("PRODSTRUCT");
			if (cnt == 0) {
				// 47.00 CountOf => 1 RE RE RE must have at least one {LD:
				// FEATURE}
				// MINIMUM_ERR = must have at least one {0}
				args[0] = m_elist.getEntityGroup("FEATURE").getLongDescription();
				createMessage(CHECKLEVEL_RE, "MINIMUM_ERR", args);
			}
		}
		// 47.20 END

		// 48.00 SWFEATURE SWPRODSTRUCT-u
		// 49.00 Count of = 0 RE RE RE must not have any {LD: SWFEATURE) {NDN:
		// SWFEATURE}
		cnt = getCount("SWPRODSTRUCT");
		if (cnt != 0) {
			// PSLINK_ERR = a {0} {1} can not have a {2}
			EntityGroup eGrp = m_elist.getEntityGroup("SWFEATURE");
			args[0] = "Hardware";
			args[1] = mdlItem.getEntityGroup().getLongDescription();
			args[2] = eGrp.getLongDescription();
			createMessage(CHECKLEVEL_RE, "PSLINK_ERR", args);
		}

		// 91.00 WARR MODELWARR-d WARR
		String warrsvccovr = PokUtils.getAttributeFlagValue(mdlItem, "WARRSVCCOVR");
		addDebug("doHardwareChecks  warrsvccovr: " + warrsvccovr);
		cnt = getCount("MODELWARR");
		// 91.10 IF "No Warranty" <> MODEL WARRSVCCOVR
		if (!WARRSVCCOVR_NOWARR.equals(warrsvccovr)) {
			addHeading(3, m_elist.getEntityGroup("MODELWARR").getLongDescription() + " Coverage Checks:");
			// 91.18 CountOf => 1 RW RW RE must have at least one Warranty
			if (cnt == 0) {
				// MINIMUM_ERR = must have at least one {0}
				args[0] = m_elist.getEntityGroup("WARR").getLongDescription();
				createMessage(getCheck_RW_RW_RE(statusFlag), "MINIMUM_ERR", args);
			} else {
				// 92.00 WARRCoverage EntityType Yes RW RE RE Required if XCC
				// {LD: WARR} {NDN: WARR} have gaps in the date range.
				// 92.10 COUNTRYLIST "Contains aggregate E" A: AVAIL COUNTRYLIST
				// E E E Column E - attributes being moved to MODELWARR must
				// have a {LD: WARR} for every country in the {LD: MODEL} {LD:
				// AVAIL} {NDN: A: AVAIL}
				// 92.20 MIN(EFFECTIVEDATE) <= A: AVAIL EFFECTIVEDATE Yes E E E
				// Column E - attributes being moved to MODELWARR must have a
				// {LD: WARR} with an EFFECTIVE DATE as early as or earlier than
				// the {LD: MODEL} {LD: AVAIL} {NDN: A: AVAIL}
				// 92.30 MAX(ENDDATE) => C: AVAIL EFFECTIVEDATE Yes E E E Column
				// E - attributes being moved to MODELWARR must have a {LD:
				// WARR} with a ENDATE as late as or later than the {LD: MODEL}
				// {LD: AVAIL} {NDN: C: AVAIL}
				checkWarrCoverage(mdlItem, mdlItem, "MODELWARR", "MODELAVAIL", CHECKLEVEL_E,
						getCheck_RW_RE_RE(statusFlag));// Defect 526751 was
														// getCheck_RW_RW_RE()
			}
		} else {
			// 95.02 ELSE
			// 95.04 CountOf = 0 E E E must not have any {LD: WARR} {NDN: WARR}
			if (cnt != 0) {
				// MUST_NOT_HAVE_ERR= must not have any {0}
				args[0] = m_elist.getEntityGroup("WARR").getLongDescription();
				createMessage(CHECKLEVEL_E, "MUST_NOT_HAVE_ERR", args);
			}
		}
		// 95.06 END 91.10

		checkProdstructAvails(mdlItem, checklvl);

		// 71.00 FEATURE F PRODSTRUCT-u RPQ FEATURE
		// 72.00 WHEN FCTYPE <> "Primary FC (100) |""Secondary FC"" (110)"
		checkRPQFeatures(mdlItem, statusFlag);

		// these checks are specific to domain
		// Delete 82.00 AND COFSUBCAT = "System" (126)
		// 83.00 IF PDHDOMAIN IN ABR_PROPERTITIES XCC_LIST
		if (// SYSTEM.equals(modelCOFSUBCAT) &&
		domainInRuleList(mdlItem, "XCC_LIST")) {
			doHwXCCDomainChecks(mdlItem, statusFlag);
		}
	}

	/*
	 * old private void checkProdstructAvails(EntityItem mdlItem,int checklvl){
	 * 
	 * EntityGroup psGrp = m_elist.getEntityGroup("PRODSTRUCT");
	 * 
	 * addHeading(3,psGrp.getLongDescription()+" Planned Avail Checks:");
	 * //52.00 AVAIL D PRODSTRUCT: OOFAVAIL-d: AVAIL TMF AVAIL //2016-01-26
	 * Change Column G 53.00 WHEN AVAILTYPE = "Planned Availability" or
	 * "MES Planned Availability" //55.00 COUNTRYLIST "in aggregate G" A: AVAIL
	 * COUNTRYLIST W W E*2 TMF can only be available where the MODEL is
	 * available //{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a country
	 * that does not have a "Planned Availability" //56.00 EFFECTIVEDATE => A:
	 * AVAIL EFFECTIVEDATE Yes W W E*3 can not be available before the MODEL
	 * //{LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} can not be
	 * earlier than the {LD:AVAIL} {NDN: A: AVAIL}
	 * checkPsAndModelAvails(mdlItem, checklvl,
	 * getCheckLevel(checklvl,mdlItem,"ANNDATE"), mdlPlaAvailCtryTblA,
	 * psPlaAvailVctD, "OOFAVAIL"); //57.00 END 53 PRODSTRUCT.OOFAVAIL.AVAIL as
	 * set is ok here
	 * 
	 * //58.00 AVAIL PRODSTRUCT: OOFAVAIL-d: AVAIL TMF AVAIL //59.00 WHEN
	 * AVAILTYPE = "First Order"
	 * 
	 * addHeading(3,psGrp.getLongDescription()+" First Order Avail Checks:");
	 * checkFirstOrderAvails(mdlItem, statusFlag,psGrp, "OOFAVAIL",checklvl); //
	 * PER PRODSTRUCT
	 * 
	 * //64.00 AVAIL E PRODSTRUCT: OOFAVAIL-d: AVAIL TMF AVAIL //65.00 WHEN
	 * AVAILTYPE = "Last Order" //67.00 COUNTRYLIST IN D: AVAIL COUNTRYLIST W W
	 * E*2 TMF can only be withdrawn where it has been announced //{LD: AVAIL}
	 * {NDN: AVAIL} {LD: COUNTRYLIST} includes a country that does not have a
	 * "Planned Availability"
	 * 
	 * //68.00 IF "Initial" = PRODSTRUCT ORDERCODE //69.00 THEN EFFECTIVEDATE <=
	 * C: AVAIL EFFECTIVEDATE Yes W W E*3 TMF must be withdrawn on or before the
	 * MODEL //{LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} can
	 * not be later than the {LD:AVAIL} {NDN: C: AVAIL}
	 * 
	 * //70.00 END 65
	 * addHeading(3,psGrp.getLongDescription()+" Last Order Avail Checks:");
	 * checkLastOrderAvails(statusFlag, psGrp, "OOFAVAIL",checklvl); // PER
	 * PRODSTRUCT }
	 */
	private void checkProdstructAvails(EntityItem mdlItem, int checklvl) throws SQLException, MiddlewareException {
		EntityGroup psGrp = m_elist.getEntityGroup("PRODSTRUCT");
		int oldDataChklvl = getCheckLevel(checklvl, mdlItem, "ANNDATE"); // E*2
																			// override

		addHeading(3, psGrp.getLongDescription() + " Planned Avail Checks:");
		// 52.00 AVAIL D PRODSTRUCT: OOFAVAIL-d: AVAIL TMF AVAIL
		// 2016-01-26 Change Column G 53.00 WHEN AVAILTYPE = "Planned
		// Availability" or "MES Planned Availability"
		// 54.00 ANNCODENAME

		addDebug("\ncheckProdstructAvails checking plannedavail ");
		Hashtable mdlLoAvailOSNTbl = new Hashtable();
		boolean mdlLoOsnErrors = getAvailByOSN(mdlLoAvailOSNTbl, this.mdlLOAvailVctC, true, CHECKLEVEL_RE);
		Hashtable mdlPlaAvailOSNTbl = new Hashtable();
		boolean mdlPlaOsnErrors = getAvailByOSN(mdlPlaAvailOSNTbl, mdlPlaAvailVctA, true, CHECKLEVEL_RE);
		addDebug("checkProdstructAvails  mdlPlaOsnErrors " + mdlPlaOsnErrors + " mdlPlaAvailOSNTbl.keys "
				+ mdlPlaAvailOSNTbl.keySet() + " mdlLoOsnErrors " + mdlLoOsnErrors + " mdlLoAvailOSNTbl.keys "
				+ mdlLoAvailOSNTbl.keySet());
		// MES
		// Hashtable mdlMesLoAvailOSNTbl = new Hashtable();
		// boolean mdlMesLoOsnErrors =
		// getAvailByOSN(mdlMesLoAvailOSNTbl,this.mdlMesLOAvailVctC,true,CHECKLEVEL_RE);
		// Hashtable mdlMesPlaAvailOSNTbl = new Hashtable();
		// boolean mdlMesPlaOsnErrors =
		// getAvailByOSN(mdlMesPlaAvailOSNTbl,mdlMesPlaAvailVctA,true,CHECKLEVEL_RE);
		// addDebug("checkProdstructAvails mdlMesPlaOsnErrors "+
		// mdlMesPlaOsnErrors+" mdlMesPlaAvailOSNTbl.keys
		// "+mdlMesPlaAvailOSNTbl.keySet()+" mdlMesLoOsnErrors "+
		// mdlMesLoOsnErrors+" mdlMesLoAvailOSNTbl.keys
		// "+mdlMesLoAvailOSNTbl.keySet());
		checkPsPlaAvailWithAvailA(PLANNEDAVAIL, psGrp, mdlPlaAvailOSNTbl, mdlPlaOsnErrors, checklvl, oldDataChklvl);
		// checkPsPlaAvailWithAvailA(PLANNEDAVAIL, psGrp,mdlMesPlaAvailOSNTbl,
		// mdlMesPlaOsnErrors, checklvl, oldDataChklvl);

		// MES
		addHeading(3, psGrp.getLongDescription() + " MES Planned Avail Checks:");
		addDebug("\ncheckProdstructAvails checking mesplannedavail ");
		checkPsPlaAvailWithAvailA(MESPLANNEDAVAIL, psGrp, mdlPlaAvailOSNTbl, mdlPlaOsnErrors, checklvl, oldDataChklvl);
		// checkPsPlaAvailWithAvailA(MESPLANNEDAVAIL,
		// psGrp,mdlMesPlaAvailOSNTbl, mdlMesPlaOsnErrors, checklvl,
		// oldDataChklvl);

		addHeading(3, psGrp.getLongDescription() + " First Order Avail Checks:");
		// 58.00 AVAIL PRODSTRUCT: OOFAVAIL-d: AVAIL TMF AVAIL
		// 59.00 WHEN AVAILTYPE = "First Order"
		// 60.00 ANNCODENAME
		checkPsFoAvailWithAvailAAndAvailD(psGrp, mdlPlaAvailOSNTbl, mdlPlaOsnErrors, mdlItem,
				/* mdlMesPlaAvailOSNTbl, mdlMesPlaOsnErrors, */ checklvl, oldDataChklvl);

		mdlPlaAvailOSNTbl.clear();

		addHeading(3, psGrp.getLongDescription() + " Last Order Avail Checks:");
		checkPsLoAvailWithAvailDAndAvailC(LASTORDERAVAIL, psGrp, mdlLoAvailOSNTbl, mdlLoOsnErrors,
				/* mdlMesLoAvailOSNTbl, mdlMesLoOsnErrors, */ checklvl, oldDataChklvl);
		addHeading(3, psGrp.getLongDescription() + " MES Last Order Avail Checks:");
		checkPsLoAvailWithAvailDAndAvailC(MESLASTORDERAVAIL, psGrp, mdlLoAvailOSNTbl, mdlLoOsnErrors,
				/* mdlMesLoAvailOSNTbl, mdlMesLoOsnErrors, */ checklvl, oldDataChklvl);
	}

	private void checkPsLoAvailWithAvailDAndAvailC(String lastOrderAvailType, EntityGroup psGrp,
			Hashtable mdlLoAvailOSNTbl, boolean mdlLoOsnErrors,
			/* Hashtable mdlMesLoAvailOSNTbl, boolean mdlMesLoOsnErrors, */ int checklvl, int oldDataChklvl)
			throws SQLException, MiddlewareException {
		// 64.00 AVAIL E PRODSTRUCT: OOFAVAIL-d: AVAIL TMF AVAIL
		// 65.00 WHEN AVAILTYPE = "Last Order"
		// 66.00 ANNCODENAME
		// get all PRODSTRUCT - must compare each PS-PLA against PS-FO
		for (int p = 0; p < psGrp.getEntityItemCount(); p++) {
			EntityItem psitem = psGrp.getEntityItem(p);
			Vector availVct = PokUtils.getAllLinkedEntities(psitem, "OOFAVAIL", "AVAIL");
			Vector psloAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", lastOrderAvailType);
			Vector psplannedAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", PLANNEDAVAIL);
			Vector psMesplannedAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", MESPLANNEDAVAIL);
			addDebug("checkProdstructAvails for " + psitem.getKey() + " psloAvailVct " + psloAvailVct.size()
					+ " psplannedAvailVct " + psplannedAvailVct.size() + " psMesplannedAvailVct "
					+ psMesplannedAvailVct.size());
			if (psloAvailVct.size() > 0) {
				Hashtable loAvailOSNTbl = new Hashtable();
				boolean loOsnErrors = getAvailByOSN(loAvailOSNTbl, psloAvailVct, true, CHECKLEVEL_RE);
				Hashtable plaAvailOSNTbl = new Hashtable();
				boolean plaOsnErrors = getAvailByOSN(plaAvailOSNTbl, psplannedAvailVct, true, CHECKLEVEL_RE);
				Hashtable mesPlaAvailOSNTbl = new Hashtable();
				boolean mesPlaOsnErrors = getAvailByOSN(mesPlaAvailOSNTbl, psMesplannedAvailVct, true, CHECKLEVEL_RE);
				addDebug("checkProdstructAvails " + psitem.getKey() + " plaOsnErrors " + plaOsnErrors
						+ " plaAvailOSNTbl.keys " + plaAvailOSNTbl.keySet() + " mesPlaOsnErrors " + mesPlaOsnErrors
						+ " mesPlaAvailOSNTbl.keys " + mesPlaAvailOSNTbl.keySet() + " loOsnErrors " + loOsnErrors
						+ " loAvailOSNTbl.keys " + loAvailOSNTbl.keySet());
				if (!loOsnErrors && !plaOsnErrors) {
					// only do this check if no errors were found building the
					// OSN buckets
					// 67.00 COUNTRYLIST IN D: AVAIL COUNTRYLIST OSN:XCC_LIST W
					// W E*2 TMF can only be withdrawn where it has been
					// announced {LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST}
					// includes a country that does not have a "Planned
					// Availability"
					checkAvailCtryByOSN(loAvailOSNTbl, plaAvailOSNTbl, "MISSING_PLA_OSNCTRY_ERR", psitem, true,
							oldDataChklvl);
				}
				if (!loOsnErrors && !mesPlaOsnErrors && MESLASTORDERAVAIL.equals(lastOrderAvailType)) {
					// only do this check if no errors were found building the
					// OSN buckets
					// 67.00 COUNTRYLIST IN D: AVAIL COUNTRYLIST OSN:XCC_LIST W
					// W E*2 TMF can only be withdrawn where it has been
					// announced {LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST}
					// includes a country that does not have a "Planned
					// Availability"
					checkAvailCtryByOSN(loAvailOSNTbl, mesPlaAvailOSNTbl, "MISSING_PLA_OSNCTRY_ERR", psitem, true,
							oldDataChklvl);
				}
				if (!loOsnErrors && !mdlLoOsnErrors) {
					// only do this check if no errors were found building the
					// OSN buckets
					// 68.00 IF "Initial" = PRODSTRUCT ORDERCODE
					// ORDERCODE 5957 I Initial
					String ordercode = PokUtils.getAttributeFlagValue(psitem, "ORDERCODE");
					addDebug("checkProdstructAvails " + psitem.getKey() + " ordercode " + ordercode);
					if (ORDERCODE_INITIAL.equals(ordercode)) {
						// 69.00 THEN EFFECTIVEDATE <= C: AVAIL EFFECTIVEDATE
						// "Cty OSN:XCC_LIST" W W E*3 TMF must be withdrawn on
						// or before the MODEL {LD: PRODSTRUCT} {NDN:
						// PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} must not be
						// later than the {LD:MODEL} {LD:AVAIL} {NDN: C: AVAIL}
						checkAvailDatesByCtryByOSN(loAvailOSNTbl, mdlLoAvailOSNTbl, psitem, DATE_LT_EQ, checklvl,
								"Model", true);
					}
				}
				// if(!loOsnErrors && !mdlMesLoOsnErrors &&
				// MESLASTORDERAVAIL.equals(lastOrderAvailType)){
				// // only do this check if no errors were found building the
				// OSN buckets
				// //68.00 IF "Initial" = PRODSTRUCT ORDERCODE
				// //ORDERCODE 5957 I Initial
				// String ordercode = PokUtils.getAttributeFlagValue(psitem,
				// "ORDERCODE");
				// addDebug("checkProdstructAvails "+psitem.getKey()+" ordercode
				// "+ordercode);
				// if(ORDERCODE_INITIAL.equals(ordercode)){
				// //69.00 THEN EFFECTIVEDATE <= C: AVAIL EFFECTIVEDATE "Cty
				// OSN:XCC_LIST" W W E*3 TMF must be withdrawn on or before the
				// MODEL {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN:
				// AVAIL} must not be later than the {LD:MODEL} {LD:AVAIL} {NDN:
				// C: AVAIL}
				// checkAvailDatesByCtryByOSN(loAvailOSNTbl,mdlMesLoAvailOSNTbl,
				// psitem, DATE_LT_EQ, checklvl,
				// "Model",true);
				// }
				// }
				loAvailOSNTbl.clear();
				plaAvailOSNTbl.clear();
				mesPlaAvailOSNTbl.clear();
			}
			availVct.clear();
			psloAvailVct.clear();
			psplannedAvailVct.clear();
			psMesplannedAvailVct.clear();
			// 70.00 END 65.00
		}
	}

	/**
	 * Check PRODSTRUCT First Order avail with A:AVAIL and D:AVAIL
	 * 
	 * @param psGrp
	 * @param mdlPlaAvailOSNTbl
	 * @param mdlPlaOsnErrors
	 * @param mdlMesPlaAvailOSNTbl
	 * @param mdlMesPlaOsnErrors
	 * @param checklvl
	 * @param oldDataChklvl
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private void checkPsFoAvailWithAvailAAndAvailD(EntityGroup psGrp, Hashtable mdlPlaAvailOSNTbl,
			boolean mdlPlaOsnErrors,
			EntityItem mdlItem/*
								 * , Hashtable mdlMesPlaAvailOSNTbl, boolean
								 * mdlMesPlaOsnErrors
								 */, int checklvl, int oldDataChklvl) throws SQLException, MiddlewareException {
		// get all PRODSTRUCT - must compare each PS-PLA against PS-FO
		for (int p = 0; p < psGrp.getEntityItemCount(); p++) {
			EntityItem psitem = psGrp.getEntityItem(p);
			Vector availVct = PokUtils.getAllLinkedEntities(psitem, "OOFAVAIL", "AVAIL");
			Vector psfoAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", FIRSTORDERAVAIL);
			Vector psplannedAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", PLANNEDAVAIL);
			Vector psMesplannedAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", MESPLANNEDAVAIL);
			addDebug("checkProdstructAvails for " + psitem.getKey() + " psfoAvailVct " + psfoAvailVct.size()
					+ " psplannedAvailVct " + psplannedAvailVct.size() + " psMesplannedAvailVct "
					+ psMesplannedAvailVct.size());

			if (psfoAvailVct.size() > 0) {
				Hashtable foAvailOSNTbl = new Hashtable();
				boolean foOsnErrors = getAvailByOSN(foAvailOSNTbl, psfoAvailVct, true, CHECKLEVEL_RE);
				Hashtable plaAvailOSNTbl = new Hashtable();
				boolean plaOsnErrors = getAvailByOSN(plaAvailOSNTbl, psplannedAvailVct, true, CHECKLEVEL_RE);
				Hashtable mesPlaAvailOSNTbl = new Hashtable();
				boolean mesPlaOsnErrors = getAvailByOSN(plaAvailOSNTbl, psMesplannedAvailVct, true, CHECKLEVEL_RE);
				addDebug("checkProdstructAvails " + psitem.getKey() + " plaOsnErrors " + plaOsnErrors
						+ " plaAvailOSNTbl.keys " + plaAvailOSNTbl.keySet() + " mesPlaOsnErrors " + mesPlaOsnErrors
						+ " mesPlaAvailOSNTbl.keys " + mesPlaAvailOSNTbl.keySet() + " foOsnErrors " + foOsnErrors
						+ " foAvailOSNTbl.keys " + foAvailOSNTbl.keySet());
				if (!foOsnErrors && !plaOsnErrors) {
					// only do this check if no errors were found building the
					// OSN buckets
					// 61.00 COUNTRYLIST "IN aggregate G" D: AVAIL COUNTRYLIST
					// OSN:XCC_LIST W W E*2 TMF can only be available where the
					// MODEL is available {LD: PRODSTRUCT} {NDN: PRODSTRUCT}{LD:
					// AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a Country
					// that does not have a "Planned Availability"
					checkAvailCtryByOSN(foAvailOSNTbl, plaAvailOSNTbl, "MISSING_PLA_OSNCTRY_ERR", psitem, true,
							oldDataChklvl);
				}
				if (psMesplannedAvailVct != null && psMesplannedAvailVct.size() > 0 && !foOsnErrors
						&& !mesPlaOsnErrors) {
					// only do this check if no errors were found building the
					// OSN buckets
					// 61.00 COUNTRYLIST "IN aggregate G" D: AVAIL COUNTRYLIST
					// OSN:XCC_LIST W W E*2 TMF can only be available where the
					// MODEL is available {LD: PRODSTRUCT} {NDN: PRODSTRUCT}{LD:
					// AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a Country
					// that does not have a "Planned Availability"
					checkAvailCtryByOSN(foAvailOSNTbl, mesPlaAvailOSNTbl, "MISSING_PLA_OSNCTRY_ERR", psitem, true,
							oldDataChklvl);
				}

				for (int i = 0; i < psfoAvailVct.size(); i++) {
					EntityItem avail = (EntityItem) psfoAvailVct.elementAt(i);
					// 62.00 EFFECTIVEDATE => MODEL ANNDATE "Cty OSN:XCC_LIST" W
					// W E*3 can not be available before the MODEL {LD:
					// PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
					// must not be earlier than the {LD:MODEL}
					checkCanNotBeLater(avail, "EFFECTIVEDATE", mdlItem, "ANNDATE", checklvl);
				}
				// if(!foOsnErrors && !mdlPlaOsnErrors){
				// // only do this check if no errors were found building the
				// OSN buckets
				// //62.00 EFFECTIVEDATE => A: AVAIL EFFECTIVEDATE "Cty
				// OSN:XCC_LIST" W W E*3 can not be available before the MODEL
				// {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
				// must not be earlier than the {LD:MODEL} {LD:AVAIL} {NDN: A:
				// AVAIL}
				// checkAvailDatesByCtryByOSN(foAvailOSNTbl,mdlPlaAvailOSNTbl,
				// psitem, DATE_GR_EQ, checklvl,
				// "Model",true);
				// }
				// if(mdlMesPlaAvailOSNTbl.size() > 0 && !foOsnErrors &&
				// !mdlMesPlaOsnErrors){
				// // only do this check if no errors were found building the
				// OSN buckets
				// //62.00 EFFECTIVEDATE => A: AVAIL EFFECTIVEDATE "Cty
				// OSN:XCC_LIST" W W E*3 can not be available before the MODEL
				// {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
				// must not be earlier than the {LD:MODEL} {LD:AVAIL} {NDN: A:
				// AVAIL}
				// checkAvailDatesByCtryByOSN(foAvailOSNTbl,mdlMesPlaAvailOSNTbl,
				// psitem, DATE_GR_EQ, checklvl,
				// "Model",true);
				// }
				foAvailOSNTbl.clear();
				plaAvailOSNTbl.clear();
				mesPlaAvailOSNTbl.clear();
			}
			availVct.clear();
			psfoAvailVct.clear();
			psplannedAvailVct.clear();
			psMesplannedAvailVct.clear();
			// 63.00 END 59.00
		}
	}

	/**
	 * Check PRODSTRUCT Plan avail and MES Plan avail with MODEL plan avail and
	 * MES plan avail
	 * 
	 * @param availType
	 * @param psGrp
	 * @param mdlPlaAvailOSNTbl
	 * @param mdlPlaOsnErrors
	 * @param checklvl
	 * @param oldDataChklvl
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private void checkPsPlaAvailWithAvailA(String availType, EntityGroup psGrp, Hashtable mdlPlaAvailOSNTbl,
			boolean mdlPlaOsnErrors, int checklvl, int oldDataChklvl) throws SQLException, MiddlewareException {
		// get all PRODSTRUCT - must compare each PS-PLA
		for (int p = 0; p < psGrp.getEntityItemCount(); p++) {
			EntityItem psitem = psGrp.getEntityItem(p);
			Vector availVct = PokUtils.getAllLinkedEntities(psitem, "OOFAVAIL", "AVAIL");
			Vector psplannedAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", availType);
			addDebug("checkProdstructAvails for " + psitem.getKey() + " psplannedAvailVct " + psplannedAvailVct.size());
			if (psplannedAvailVct.size() > 0) {
				Hashtable plaAvailOSNTbl = new Hashtable();
				boolean plaOsnErrors = getAvailByOSN(plaAvailOSNTbl, psplannedAvailVct, true, CHECKLEVEL_RE);
				addDebug("checkProdstructAvails " + psitem.getKey() + " plaOsnErrors " + plaOsnErrors
						+ " plaAvailOSNTbl.keys " + plaAvailOSNTbl.keySet());
				if (!plaOsnErrors && !mdlPlaOsnErrors) {
					// only do this check if no errors were found building the
					// OSN buckets
					// 55.00 COUNTRYLIST "IN aggregate G" A: AVAIL COUNTRYLIST
					// OSN:XCC_LIST W W E*2 TMF can only be available where the
					// MODEL is available
					// {LD: PRODSTRUCT} {NDN: PRODSTRUCT}{LD: AVAIL} {NDN: A:
					// AVAIL} {LD: COUNTRYLIST} includes a Country that the {LD:
					// MODEL} is not available in.
					// MODELROOT_AVAIL_OSNCTRY_ERR = {0} {1} {2} includes a
					// Country that the Model is not available in {3} Extra
					// countries: {4}
					checkAvailCtryByOSN(plaAvailOSNTbl, mdlPlaAvailOSNTbl, "MODELROOT_AVAIL_OSNCTRY_ERR", psitem, true,
							oldDataChklvl);

					// 56.00 EFFECTIVEDATE => A: AVAIL EFFECTIVEDATE "Cty
					// OSN:XCC_LIST" W W E*3 can not be available before the
					// MODEL {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL}
					// {NDN: AVAIL} must not be earlier than the {LD: MODEL}
					// {LD:AVAIL} {NDN: A: AVAIL}
					checkAvailDatesByCtryByOSN(plaAvailOSNTbl, mdlPlaAvailOSNTbl, psitem, DATE_GR_EQ, checklvl, "Model",
							true);
				}
				plaAvailOSNTbl.clear();
			}
			availVct.clear();
			psplannedAvailVct.clear();
			// 57.00 END 53.00
		}
	}

	/***************
	 * Check each Feature, if it is an RPQ then Check each PRODSTRUCT
	 * PRODSTRUCT.ANNDATE must => all MODELAVAIL plannedavails NEW ann anndate
	 * PRODSTRUCT.GENAVAILDATE must => all MODELAVAIL plannedavails effdate
	 * PRODSTRUCT.WITHDRAWDATE must <= all MODELAVAIL lastorder EOL ann anndate
	 * PRODSTRUCT.WTHDRWEFFCTVDATE must <= all MODELAVAIL lastorder effdate
	 * 
	 * @param statusFlag
	 * 
	 *            71.00 FEATURE F PRODSTRUCT-u RPQ FEATURE 72.00 WHEN FCTYPE <>
	 *            "Primary FC (100) |""Secondary FC"" (110)" 73.00 PRODSTRUCT F:
	 *            74.00 ANNDATE => M: ANNOUNCEMENT ANNDATE W W E {LD:
	 *            PRODSTRUCT} {NDN: PRODSTRUCT} {LD: ANNDATE} {ANNDATE} can not
	 *            be earlier than the {LD: ANNOUNCEMENT} {NDN: M: ANNOUNCEMENT}
	 *            {LD: ANNDATE} {M: ANNDATE} 75.00 GENAVAILDATE => A: AVAIL
	 *            EFFECTIVEDATE W W E {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD:
	 *            GENAVAILDATE} {GENAVAILDATE} can not be earlier than the {LD:
	 *            AVAIL} {NDN: A: AVAIL} xx76.00 WITHDRAWDATE <= N: ANNOUNCEMENT
	 *            ANNDATE W W E {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD:
	 *            WITHDRAWDATE} {WITHDRAWDATE} can not be later than the {LD:
	 *            ANNOUNCEMENT} {NDN: N: ANNOUNCEMENT} {LD: ANNDATE} {N:
	 *            ANNDATE} 76.00 WITHDRAWDATE => N: ANNOUNCEMENT ANNDATE W W E
	 *            {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: WITHDRAWDATE}
	 *            {WITHDRAWDATE} can not be earlier than the {LD: ANNOUNCEMENT}
	 *            {NDN: N: ANNOUNCEMENT} {LD: ANNDATE} {N: ANNDATE} 77.00
	 *            WTHDRWEFFCTVDATE <= C: AVAIL EFFECTIVEDATE W W E {LD:
	 *            PRODSTRUCT} {NDN: PRODSTRUCT} {LD: WTHDRWEFFCTVDATE}
	 *            {WTHDRWEFFCTVDATE} can not be later than the {LD: AVAIL} {NDN:
	 *            C: AVAIL} 78.00 END 72
	 * 
	 * @throws MiddlewareException
	 * @throws SQLException
	 */
	private void checkRPQFeatures(EntityItem mdl, String statusFlag) throws SQLException, MiddlewareException {
		int checklvl = getCheck_W_W_E(statusFlag);

		Vector newannVct = PokUtils.getAllLinkedEntities(mdlPlaAvailVctA, "AVAILANNA", "ANNOUNCEMENT");
		addDebug("checkRPQFeatures mdlplannedavail annVct: " + newannVct.size());
		newannVct = PokUtils.getEntitiesWithMatchedAttr(newannVct, "ANNTYPE", ANNTYPE_NEW);
		addDebug("checkRPQFeatures NEW annVct: " + newannVct.size());

		Vector eolannVct = PokUtils.getAllLinkedEntities(mdlLOAvailVctC, "AVAILANNA", "ANNOUNCEMENT");
		addDebug("checkRPQFeatures mdllastorder annVct: " + eolannVct.size());
		eolannVct = PokUtils.getEntitiesWithMatchedAttr(eolannVct, "ANNTYPE", ANNTYPE_EOL);
		addDebug("checkRPQFeatures EOL annVct: " + eolannVct.size());

		// 71.00 FEATURE F PRODSTRUCT-u RPQ FEATURE
		EntityGroup featGrp = m_elist.getEntityGroup("FEATURE");
		addHeading(3, featGrp.getLongDescription() + " RPQ Checks:");
		for (int i = 0; i < featGrp.getEntityItemCount(); i++) {
			EntityItem featitem = featGrp.getEntityItem(i);
			// 72.00 WHEN FCTYPE <> "Primary FC (100) |""Secondary FC"" (110)"
			if (isRPQ(featitem)) {
				addDebug("checkRPQFeatures " + featitem.getKey() + " was an RPQ FCTYPE: "
						+ getAttributeFlagEnabledValue(featitem, "FCTYPE"));
				// look at the prodstructs
				for (int d = 0; d < featitem.getDownLinkCount(); d++) {
					EntityItem psitem = (EntityItem) featitem.getDownLink(d);
					// remove 74.00 and 75.00 based on update document - BH FS
					// ABR Data Quality Checks 20120814.xls - Aug 29
					// addDebug("checkRPQFeatures testing
					// PRODSTRUCT.GENAVAILDATE cannot be earlier than
					// mdlplannedavail effdate");
					// //75.00 GENAVAILDATE => A: AVAIL EFFECTIVEDATE W W E
					// //{LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: GENAVAILDATE}
					// {GENAVAILDATE} can not be earlier than the {LD: AVAIL}
					// {NDN: AVAIL}
					// for (int a=0; a<mdlPlaAvailVctA.size(); a++){
					// EntityItem mdlPlaAvail =
					// (EntityItem)mdlPlaAvailVctA.elementAt(a);
					// checkCanNotBeEarlier(psitem, "GENAVAILDATE",
					// mdlPlaAvail,"EFFECTIVEDATE", checklvl);
					// }
					// addDebug("checkRPQFeatures testing
					// PRODSTRUCT.WTHDRWEFFCTVDATE cannot be later than
					// mdllastorder effdate");

					// addDebug("checkRPQFeatures testing PRODSTRUCT.ANNDATE
					// cannot be earlier than mdlpla new ANN.ANNDATE");
					// 74.00 ANNDATE => M: ANNOUNCEMENT ANNDATE W W E (from
					// MODELAVAIL plannedavail NEW anntype)
					// for (int n=0; n<newannVct.size(); n++){
					// EntityItem annItem = (EntityItem)newannVct.elementAt(n);
					// //{LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: ANNDATE}
					// {ANNDATE} can not be earlier than the {LD: ANNOUNCEMENT}
					// {LD: ANNDATE} {ANNDATE}
					// String date1 = getAttrValueAndCheckLvl(psitem, "ANNDATE",
					// checklvl);
					// String date2 = getAttrValueAndCheckLvl(annItem,
					// "ANNDATE", checklvl);
					// addDebug("checkRPQFeatures "+psitem.getKey()+"
					// ANNDATE:"+date1+
					// " can not be earlier than "+annItem.getKey()+"
					// ANNDATE:"+date2);
					// boolean isok = checkDates(date1, date2, DATE_GR_EQ);
					// //date1=>date2
					// if (!isok){
					// //CANNOT_BE_EARLIER_ERR2 = {0} {1} must not be earlier
					// than the {2} {3}
					// args[0]=getLD_NDN(psitem);
					// args[1]=getLD_Value(psitem, "ANNDATE");
					// args[2]=getLD_NDN(annItem);
					// args[3]=getLD_Value(annItem, "ANNDATE");
					// createMessage(checklvl,"CANNOT_BE_EARLIER_ERR2",args);
					// }
					//
					// }
					// 20120713 Add 75.60 IF "Initial" = PRODSTRUCT ORDERCODE
					String ordercode = PokUtils.getAttributeFlagValue(psitem, "ORDERCODE");
					addDebug("checkRPQFeatures " + psitem.getKey() + " ordercode " + ordercode);
					if (ORDERCODE_INITIAL.equals(ordercode)) {
						// check if model has withdrawdate and more ps will be
						// got based on feature.
						addDebug(
								"checkRPQFeatures testing PRODSTRUCT.WITHDRAWDATE cannot be later than MODEL.WITHDRAWDATE");
						// 20120830 Change 76.00 WITHDRAWDATE <= MODEL
						// WITHDRAWDATE W W E {LD: PRODSTRUCT} {NDN: PRODSTRUCT}
						// {LD: WITHDRAWDATE} {WITHDRAWDATE} can not be later
						// than the {LD: MODEL} {NDN: MODEL} {LD: WITHDRAWDATE}
						// {N: WITHDRAWDATE}
						checkCanNotBeLater(psitem, "WITHDRAWDATE", mdl, "WITHDRAWDATE", checklvl);
						// for (int n=0; n<eolannVct.size(); n++){
						// EntityItem annItem =
						// (EntityItem)eolannVct.elementAt(n);
						// //xx{LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD:
						// WITHDRAWDATE} {WITHDRAWDATE} can not be later than
						// the {LD: ANNOUNCEMENT} {NDN:ANNOUNCEMENT} {LD:
						// ANNDATE} {ANNDATE}
						// //Change 20120426
						// //{LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD:
						// WITHDRAWDATE} {WITHDRAWDATE} can not be earlier than
						// the {LD: ANNOUNCEMENT} {NDN: N: ANNOUNCEMENT} {LD:
						// ANNDATE} {N: ANNDATE}
						// String date1 = getAttrValueAndCheckLvl(psitem,
						// "WITHDRAWDATE", checklvl);
						// String date2 = getAttrValueAndCheckLvl(annItem,
						// "ANNDATE", checklvl);
						// addDebug("checkRPQFeatures "+psitem.getKey()+"
						// WITHDRAWDATE:"+date1+
						// " can not be earlier than "+annItem.getKey()+"
						// ANNDATE:"+date2);
						// boolean isok = checkDates(date1, date2, DATE_GR_EQ);
						// //date1>=date2
						// if (!isok){
						// //CANNOT_BE_EARLIER_ERR2 = {0} {1} must not be earier
						// than the {2} {3}
						// args[0]=getLD_NDN(psitem);
						// args[1]=getLD_Value(psitem, "WITHDRAWDATE");
						// args[2]=getLD_NDN(annItem);
						// args[3]=getLD_Value(annItem, "ANNDATE");
						// //
						// createMessage(checklvl,"CANNOT_BE_LATER_ERR",args);
						// createMessage(checklvl,"CANNOT_BE_EARLIER_ERR2",args);
						// }
						// }
						// 77.00 WTHDRWEFFCTVDATE <= C: AVAIL EFFECTIVEDATE W W
						// E
						// {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD:
						// WTHDRWEFFCTVDATE} {WTHDRWEFFCTVDATE} can not be later
						// than the {LD: AVAIL} {NDN: AVAIL}
						for (int a = 0; a < mdlLOAvailVctC.size(); a++) {
							EntityItem mdlloAvail = (EntityItem) mdlLOAvailVctC.elementAt(a);
							checkCanNotBeLater(psitem, "WTHDRWEFFCTVDATE", mdlloAvail, "EFFECTIVEDATE", checklvl);
						}
						// for (int a=0; a<mdlMesLOAvailVctC.size(); a++){
						// EntityItem mdlloAvail =
						// (EntityItem)mdlMesLOAvailVctC.elementAt(a);
						// checkCanNotBeLater(psitem, "WTHDRWEFFCTVDATE",
						// mdlloAvail,"EFFECTIVEDATE", checklvl);
						// }
					}
				}
			} else {
				addDebug("checkRPQFeatures " + featitem.getKey() + " was NOT an RPQ FCTYPE: "
						+ getAttributeFlagEnabledValue(featitem, "FCTYPE"));
			}
		}

		newannVct.clear();
		eolannVct.clear();
	}

	/***************************************
	 * For HW system models and domain in XCC_LIST - Must have MDLCGMDL relator
	 * - Must have an IMG for each MODELAVAIL plannedavail ctry - By Country the
	 * MIN(PUBFROM) for IMG must be <= MODELAVAIL plannedavail effdate - By
	 * Country the MAX(PUBTO) for IMG must be => MODELAVAIL lastorder effdate -
	 * By Country there can not be any gaps or overlap in dates from min pubfrom
	 * to max pubto
	 * 
	 * - Must have an WARR for each MODELAVAIL plannedavail ctry - By Country
	 * the MIN(PUBFROM) for WARR must be <= MODELAVAIL plannedavail effdate - By
	 * Country the MAX(PUBTO) for WARR must be => MODELAVAIL lastorder effdate -
	 * By Country there can not be any gaps or overlap in dates from min pubfrom
	 * to max pubto
	 * 
	 * @param mdlItem
	 * @param statusFlag
	 * @throws MiddlewareException
	 * @throws SQLException
	 * 
	 *             checks from ss 80.00 MODEL Root Hardware System MODEL 81.00
	 *             WHEN COFCAT = "Hardware" (100) Delete 82.00 AND COFSUBCAT =
	 *             "System" (126) 83.00 AND PDHDOMAIN IN ABR_PROPERTITIES
	 *             XCC_LIST XCC Unique Rules 84.00 MODELCG MDLCGMDL-u MODELCG
	 *             85.00 CountOf => 1 RW RW RE must be in a {LD MODELCG} 86.00
	 *             IMG MODELIMG-d IMG 86.90 CountOf => 1 RW RW RE Required if
	 *             XCC must have at least one Image 87.00 UniqueCoverage IMG Yes
	 *             RW RW RE Required if XCC {LD: IMG} {NDN: IMG} have gaps in
	 *             the date range or they overlap. 88.00 COUNTRYLIST "Contains
	 *             aggregate E" A: AVAIL COUNTRYLIST RW RW RE must have a {LD:
	 *             IMG} for every country in the {LD: MODEL} {LD: AVAIL} {NDN:
	 *             A: AVAIL} 89.00 MIN(PUBFROM) <= A: AVAIL EFFECTIVEDATE Yes RW
	 *             RW RE must have a {LD: IMG} with a PUBFROM as early as or
	 *             earlier than the {LD: MODEL} {LD: AVAIL} {NDN: A: AVAIL}
	 *             90.00 MAX(PUBTO) => C: AVAIL EFFECTIVEDATE Yes RW RW RE must
	 *             have a {LD: IMG} with a PUBTO as late as or later than the
	 *             {LD: MODEL} {LD: AVAIL} {NDN: C: AVAIL} Add 90.02 END 83.00
	 *             Change 96.00 END 81.00
	 * 
	 *             96.00 END 81
	 */
	private void doHwXCCDomainChecks(EntityItem mdlItem, String statusFlag) throws MiddlewareException, SQLException {
		addDebug("doHwXCCDomainChecks: entered");
		addHeading(3, "Hardware Model XCC Checks:");

		int checklvl = getCheck_RW_RW_RE(statusFlag);
		// 84.00 MODELCG MDLCGMDL-u MODELCG
		// 85.00 CountOf => 1 RW RW RE
		int cnt = getCount("MDLCGMDL");
		if (cnt == 0) {
			EntityGroup eGrp = m_elist.getEntityGroup("MODELCG");
			// must be in a {LD MODELCG}
			// MUST_BE_IN_ATLEAST_ONE_ERR = must be in at least one {0}
			args[0] = eGrp.getLongDescription();
			createMessage(checklvl, "MUST_BE_IN_ATLEAST_ONE_ERR", args);
		}

		// 86.00 IMG MODELIMG-d IMG
		// 86.90 CountOf => 1 RW RW RE Required if XCC must have at least one
		// Image
		// 87.00 UniqueCoverage IMG Yes RW RW RE Required if XCC {LD: IMG} {NDN:
		// IMG} have gaps in the date range or they overlap.
		// 88.00 COUNTRYLIST "Contains aggregate E" A: AVAIL COUNTRYLIST RW RW
		// RE must have a {LD: IMG} for every country in the {LD: MODEL} {LD:
		// AVAIL} {NDN: A: AVAIL}
		// 89.00 MIN(PUBFROM) <= A: AVAIL EFFECTIVEDATE Yes RW RW RE must have a
		// {LD: IMG} with a PUBFROM as early as or earlier than the {LD: MODEL}
		// {LD: AVAIL} {NDN: A: AVAIL}
		// 90.00 MAX(PUBTO) => C: AVAIL EFFECTIVEDATE Yes RW RW RE must have a
		// {LD: IMG} with a PUBTO as late as or later than the {LD: MODEL} {LD:
		// AVAIL} {NDN: C: AVAIL}
		checkUniqueCoverage(mdlItem, "MODELIMG", "IMG", mdlPlaAvailVctA, mdlLOAvailVctC, checklvl, true);
		IMGUniqueCoverageChkDone = true;

		// 90.02 END 83.00
		// 96.00 END 81.00
	}

	/*************
	 * Check the WARR - All MODELAVAIL-plannedavail countries must be a subset
	 * of MODELWARR countries. - BY country the MIN(MODELWARR.EFFECTIVEDATE)
	 * must be <= then all MODELAVAIL-plannedavail effdates - BY country the
	 * MAX(MODELWARR.ENDDATE) must be => then all MODELAVAIL-lastorderavail
	 * effdates
	 * 
	 * @param mdlItem
	 * @param checklvl
	 * @throws SQLException
	 * @throws MiddlewareException
	 * 
	 *             93.00 MODELWARR.COUNTRYLIST "Contains aggregate E" A: AVAIL
	 *             COUNTRYLIST N RW RE must have a {LD: WARR} for every country
	 *             in the {LD: MODEL} {LD: AVAIL} {NDN: A: AVAIL} 94.00
	 *             MIN(MODELWARR.EFFECTIVEDATE) <= A: AVAIL EFFECTIVEDATE Yes N
	 *             RW RE must have a {LD: WARR} with an EFFECTIVE DATE as early
	 *             as or earlier than the {LD: MODEL} {LD: AVAIL} {NDN: A:
	 *             AVAIL} 95.00 MAX(MODELWARR.ENDDATE) => C: AVAIL EFFECTIVEDATE
	 *             Yes N RW RE must have a {LD: WARR} with a ENDATE as late as
	 *             or later than the {LD: MODEL} {LD: AVAIL} {NDN: C: AVAIL} /
	 *             private void checkMODELWARR(EntityItem mdlItem,int checklvl)
	 *             throws SQLException, MiddlewareException { EntityGroup eGrp =
	 *             m_elist.getEntityGroup("MODELWARR"); addDebug("checkMODELWARR
	 *             entered MODELWARR.cnt "+eGrp.getEntityItemCount());
	 * 
	 *             ArrayList warrCtryList = new ArrayList(); // get all
	 *             countries for all warrs, only use valid ones Hashtable ucTbl
	 *             = new Hashtable(); //sort by MODELWARR.EFFECTIVEDATE for (int
	 *             i=0; i<eGrp.getEntityItemCount(); i++){ EntityItem ei =
	 *             eGrp.getEntityItem(i); String effdate =
	 *             PokUtils.getAttributeValue(ei, "EFFECTIVEDATE", "", null,
	 *             false); boolean isok = true; if (effdate ==null ||
	 *             effdate.startsWith("<span ")){ //"<span means not in meta
	 *             addDebug("checkMODELWARR "+ei.getKey()+" EFFECTIVEDATE
	 *             "+effdate); //REQ_NOTPOPULATED_ERR = {0} {1} is required and
	 *             does not have a value. args[0]=getLD_NDN(ei);
	 *             args[1]=PokUtils.getAttributeDescription(eGrp,
	 *             "EFFECTIVEDATE", "EFFECTIVEDATE");
	 *             createMessage(checklvl,"REQ_NOTPOPULATED_ERR",args); isok =
	 *             false; } String tst = PokUtils.getAttributeFlagValue(ei,
	 *             "COUNTRYLIST"); if (tst ==null){ addDebug("checkMODELWARR
	 *             "+ei.getKey()+" COUNTRYLIST "+tst); //REQ_NOTPOPULATED_ERR =
	 *             {0} {1} is required and does not have a value.
	 *             args[0]=getLD_NDN(ei);
	 *             args[1]=PokUtils.getAttributeDescription(eGrp, "COUNTRYLIST",
	 *             "COUNTRYLIST");
	 *             createMessage(checklvl,"REQ_NOTPOPULATED_ERR",args); isok =
	 *             false; } tst = PokUtils.getAttributeValue(ei, "ENDDATE", "",
	 *             null, false); if (tst ==null || tst.startsWith("<span ")){
	 *             //"<span means not in meta addDebug("checkMODELWARR
	 *             "+ei.getKey()+" ENDDATE "+tst); //REQ_NOTPOPULATED_ERR = {0}
	 *             {1} is required and does not have a value.
	 *             args[0]=getLD_NDN(ei);
	 *             args[1]=PokUtils.getAttributeDescription(eGrp, "ENDDATE",
	 *             "ENDDATE");
	 *             createMessage(checklvl,"REQ_NOTPOPULATED_ERR",args); isok =
	 *             false; } if (!isok){ continue; }
	 * 
	 *             UniqueCoverage uc = new UniqueCoverage(ei,checklvl); Iterator
	 *             itr = uc.ctryList.iterator(); while (itr.hasNext()) { String
	 *             ctryflag = (String) itr.next(); Vector tmp =
	 *             (Vector)ucTbl.get(ctryflag); if (tmp==null){ tmp = new
	 *             Vector(); ucTbl.put(ctryflag,tmp); } tmp.add(uc); }
	 *             getCountriesAsList(ei, warrCtryList,checklvl); // accumulate
	 *             all the different countries }
	 * 
	 *             if(ucTbl.size()==0){ return; }
	 * 
	 *             // sort each countries vectors using the
	 *             MODELWARR.EFFECTIVEDATE attr for (Enumeration e =
	 *             ucTbl.keys(); e.hasMoreElements();) {
	 *             Collections.sort((Vector)ucTbl.get(e.nextElement())); }
	 * 
	 *             addDebug("checkMODELWARR mdlPlaAvailVctA.size()
	 *             "+mdlPlaAvailVctA.size()+ " mdlLOAvailVctC.size()
	 *             "+mdlLOAvailVctC.size()+" warrCtryList "+warrCtryList);
	 * 
	 *             //93.00 MODELWARR.COUNTRYLIST "Contains aggregate E" A: AVAIL
	 *             COUNTRYLIST N RW RE must have a {LD: WARR} for every country
	 *             in the {LD: MODEL} {LD: AVAIL} {NDN: A: AVAIL} for (int i=0;
	 *             i<mdlPlaAvailVctA.size(); i++){ EntityItem mdlplaAvail =
	 *             (EntityItem)mdlPlaAvailVctA.elementAt(i); ArrayList ctryList
	 *             = new ArrayList(); getCountriesAsList(mdlplaAvail,
	 *             ctryList,checklvl);
	 * 
	 *             //check by country String effdate =
	 *             getAttrValueAndCheckLvl(mdlplaAvail, "EFFECTIVEDATE",
	 *             checklvl); addDebug("checkMODELWARR mdlplannedavail["+i+"]
	 *             "+mdlplaAvail.getKey()+" EFFECTIVEDATE "+effdate+" ctryList
	 *             "+ctryList);
	 * 
	 *             if(!warrCtryList.containsAll(ctryList)){ //MISSING_CTRY_ERR =
	 *             must have a {0} for every country in the {1} {2}
	 *             args[0]=eGrp.getLongDescription();
	 *             args[1]=mdlItem.getEntityGroup().getLongDescription();
	 *             args[2]=getLD_NDN(mdlplaAvail);
	 *             createMessage(checklvl,"MISSING_CTRY_ERR",args); }
	 * 
	 *             boolean isok = true; Iterator itr = ctryList.iterator();
	 *             while (itr.hasNext() && isok) { String ctryflag = (String)
	 *             itr.next(); Vector tmpVct = (Vector)ucTbl.get(ctryflag); //
	 *             get the MODELWARR that have this country if (tmpVct!=null){
	 *             UniqueCoverage uc = (UniqueCoverage)tmpVct.firstElement();
	 *             String minEffDate =uc.pubfrom; addDebug("checkMODELWARR
	 *             "+uc.item.getKey()+" had minEffDate "+minEffDate+" for
	 *             country "+ctryflag); //94.00 MIN(MODELWARR.EFFECTIVEDATE) <=
	 *             A: AVAIL EFFECTIVEDATE Yes N RW RE must have a {LD: WARR}
	 *             with an EFFECTIVE DATE as early as or earlier than the {LD:
	 *             MODEL} {LD: AVAIL} {NDN: A: AVAIL} isok =
	 *             checkDates(minEffDate, effdate, DATE_LT_EQ); //date1<=date2
	 *             if(!isok){ //PUBFROM_ERR = must have a {0} with a {1} as
	 *             early as or earlier than the {2} {3}
	 *             args[0]=getLD_NDN(uc.item);//eGrp.getLongDescription();
	 *             args[1]=PokUtils.getAttributeDescription(eGrp,
	 *             "EFFECTIVEDATE", "EFFECTIVEDATE");
	 *             args[2]=mdlItem.getEntityGroup().getLongDescription();
	 *             args[3]=getLD_NDN(mdlplaAvail);
	 *             createMessage(checklvl,"PUBFROM_ERR",args); } }else{
	 *             addDebug("checkMODELWARR: No MODELWARR found for mdlpla
	 *             country "+ctryflag); } }
	 * 
	 *             ctryList.clear(); }
	 * 
	 *             // sort each countries vectors using PUBTO now for
	 *             (Enumeration e = ucTbl.keys(); e.hasMoreElements();){ String
	 *             flagCode = (String)e.nextElement(); Vector ctryUcVct =
	 *             (Vector)ucTbl.get(flagCode); for (int u=0;
	 *             u<ctryUcVct.size(); u++){ UniqueCoverage uc =
	 *             (UniqueCoverage)ctryUcVct.elementAt(u);
	 *             uc.setPubFromSort(false); // sort by pubto now }
	 *             Collections.sort(ctryUcVct); }
	 * 
	 *             //check by country //95.00 MAX(MODELWARR.ENDDATE) => C: AVAIL
	 *             EFFECTIVEDATE Yes N RW RE must have a {LD: WARR} with a
	 *             ENDATE as late as or later than the {LD: MODEL} {LD: AVAIL}
	 *             {NDN: C: AVAIL} for (int i=0; i<mdlLOAvailVctC.size(); i++){
	 *             EntityItem mdlloAvail =
	 *             (EntityItem)mdlLOAvailVctC.elementAt(i); String effdate =
	 *             getAttrValueAndCheckLvl(mdlloAvail, "EFFECTIVEDATE",
	 *             checklvl);
	 * 
	 *             ArrayList ctryList = new ArrayList();
	 *             getCountriesAsList(mdlloAvail, ctryList,checklvl);
	 *             addDebug("checkMODELWARR mdllastorder["+i+"]
	 *             "+mdlloAvail.getKey()+" EFFECTIVEDATE "+effdate+" ctryList
	 *             "+ctryList);
	 * 
	 *             boolean isok = true; Iterator itr = ctryList.iterator();
	 *             while (itr.hasNext() && isok) { String ctryflag = (String)
	 *             itr.next(); Vector tmpVct = (Vector)ucTbl.get(ctryflag); if
	 *             (tmpVct!=null){ UniqueCoverage uc =
	 *             (UniqueCoverage)tmpVct.lastElement(); String maxEndDate
	 *             =uc.pubto; addDebug("checkMODELWARR "+uc.item.getKey()+" had
	 *             maxEndDate "+maxEndDate+" for country "+ctryflag); //95.00
	 *             MAX(MODELWARR.ENDDATE) => C: AVAIL EFFECTIVEDATE Yes N RW RE
	 *             must have a {LD: WARR} with a ENDATE as late as or later than
	 *             the {LD: MODEL} {LD: AVAIL} {NDN: C: AVAIL} isok =
	 *             checkDates(maxEndDate, effdate, DATE_GR_EQ); //date1=>date2
	 *             if(!isok){ //PUBTO_ERR = must have a {0} with a {1} as late
	 *             as or later than the {2} {3} //must have a {LD: IMG} with a
	 *             PUBTO as late as or later than the {LD: MODEL} {LD: AVAIL}
	 *             {NDN: C: AVAIL}
	 *             args[0]=getLD_NDN(uc.item);//eGrp.getLongDescription();
	 *             args[1]=PokUtils.getAttributeDescription(eGrp, "ENDDATE",
	 *             "ENDDATE");
	 *             args[2]=mdlItem.getEntityGroup().getLongDescription();
	 *             args[3]=getLD_NDN(mdlloAvail);
	 *             createMessage(checklvl,"PUBTO_ERR",args); } }else{
	 *             addDebug("checkMODELWARR: No MODELWARR found for mdllo
	 *             country "+ctryflag); } }
	 * 
	 *             ctryList.clear(); }
	 * 
	 *             // release memory for (Enumeration e = ucTbl.keys();
	 *             e.hasMoreElements();) { String flagCode =
	 *             (String)e.nextElement(); Vector ctryUcVct =
	 *             (Vector)ucTbl.get(flagCode); for (int u=0;
	 *             u<ctryUcVct.size(); u++){ UniqueCoverage uc =
	 *             (UniqueCoverage)ctryUcVct.elementAt(u); uc.dereference(); }
	 *             ctryUcVct.clear(); } ucTbl.clear(); }
	 * 
	 *             /***************************** When MODEL is Software - must
	 *             not have any FEATURE - must not have any SVCFEATURE - must
	 *             have at least one SWFEATURE
	 * 
	 *             - All SWPRODSTRUCTAVAIL plannedavail ctry must be a subset of
	 *             MODELAVAIL plannedavail ctrys - By ctry SWPRODSTRUCTAVAIL
	 *             plannedavail effdate must be => MODELAVAIL plannedavail
	 *             effdate for same ctry
	 * 
	 *             - All SWPRODSTRUCTAVAIL firstorder ctry must be a subset of
	 *             SWPRODSTRUCTAVAIL plannedavail ctrys - By ctry
	 *             SWPRODSTRUCTAVAIL firstorder effdate must be => MODELAVAIL
	 *             plannedavail effdate for same ctry
	 * 
	 *             - All SWPRODSTRUCTAVAIL lastorder ctry must be a subset of
	 *             SWPRODSTRUCTAVAIL plannedavail ctrys - By ctry
	 *             SWPRODSTRUCTAVAIL lastorder effdate must be => MODELAVAIL
	 *             lastorder effdate for same ctry
	 * 
	 * @param mdlItem
	 * @param statusFlag
	 * @throws MiddlewareException
	 * @throws SQLException
	 * 
	 *             checks from ss: 97.00 MODEL Root Software MODEL 98.00 WHEN
	 *             COFCAT = "Software" (101) 99.00 FEATURE PRODSTRUCT-u 100.00
	 *             CountOf = 0 RE RE E must not have any {LD: FEATURE) {NDN:
	 *             FEATURE} 101.00 SWFEATURE SWPRODSTRUCT-u 101.20 IF "No"
	 *             (FEATN) <> MODEL FEATINDC 102.00 CountOf => 1 RE RE RE must
	 *             have at least one {LD: SWFEATURE} 101.10 END 105.00 AVAIL H
	 *             SWPRODSTRUCT: SWPRODSTRUCTAVAIL-d: AVAIL TM SW AVAIL 106.00
	 *             WHEN AVAILTYPE = "Planned Availability" 107.00 ANNCODENAME
	 *             108.00 COUNTRYLIST "in aggregate G" A: AVAIL COUNTRYLIST W W
	 *             E*2 {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN:
	 *             AVAIL} includes a Country that the {LD: MODEL} is not
	 *             available in 109.00 EFFECTIVEDATE => A: AVAIL EFFECTIVEDATE
	 *             Yes W W E {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL}
	 *             {NDN: AVAIL} must not be earlier than the {LD: MODEL}
	 *             {LD:AVAIL} {NDN: A: AVAIL} 110.00 END 106 111.00 AVAIL
	 *             SWPRODSTRUCT: SWPRODSTRUCTAVAIL-d: AVAIL TM SW AVAIL 112.00
	 *             WHEN AVAILTYPE = "First Order" 113.00 ANNCODENAME 114.00
	 *             COUNTRYLIST "in aggregate G" H: AVAIL COUNTRYLIST W W E*2
	 *             {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN:
	 *             AVAIL} {LD: COUNTRYLIST} includes a Country that does not
	 *             have a "Planned Availability" 115.00 EFFECTIVEDATE => A:
	 *             AVAIL EFFECTIVEDATE Yes W W E {LD: SWPRODSTRUCT}
	 *             {NDN:SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} must not be
	 *             earlier than the {LD: MODEL} {LD:AVAIL} {NDN: A: AVAIL}
	 *             116.00 END 112 117.00 AVAIL K SWPRODSTRUCT:
	 *             SWPRODSTRUCTAVAIL-d: AVAIL TM SW AVAIL 118.00 WHEN AVAILTYPE
	 *             = "Last Order" 119.00 ANNCODENAME 120.00 COUNTRYLIST "in
	 *             aggregate G" H: AVAIL COUNTRYLIST W W E*2 {LD: SWPRODSTRUCT}
	 *             {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} includes a
	 *             Country that does not have a "Planned Availability" 121.00
	 *             EFFECTIVEDATE <= C: AVAIL EFFECTIVEDATE Yes W W E {LD:
	 *             SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
	 *             must not be later than the {LD:MODEL} {LD:AVAIL} {NDN: C:
	 *             AVAIL} 122.00 END 118 123.00 END 98
	 * 
	 */
	private void doSoftwareChecks(EntityItem mdlItem, String statusFlag) throws MiddlewareException, SQLException {
		String modelFEATINDC = getAttributeFlagEnabledValue(mdlItem, "FEATINDC");
		addDebug("doSoftwareChecks " + mdlItem.getKey() + " FEATINDC: " + modelFEATINDC);

		addHeading(3, " Software Model Checks:");
		int checklvl = getCheck_W_W_E(statusFlag);
		// 99.00 FEATURE PRODSTRUCT-u
		// 100.00 Count of = 0 RE RE E must not have any {LD: FEATURE) {NDN:
		// FEATURE}
		// a Software {LD: MODEL} can not have a {LD: FEATURE}
		// PSLINK_ERR = a {0} {1} can not have a {2}
		int cnt = getCount("PRODSTRUCT");
		if (cnt != 0) {
			EntityGroup eGrp = m_elist.getEntityGroup("FEATURE");
			args[0] = "Software";
			args[1] = mdlItem.getEntityGroup().getLongDescription();
			args[2] = eGrp.getLongDescription();
			createMessage(getCheck_RE_RE_E(statusFlag), "PSLINK_ERR", args);
		}

		// 101.00 SWFEATURE SWPRODSTRUCT-u
		// 101.20 IF "No" (FEATN) <> MODEL FEATINDC
		if (!FEATINDC_No.equals(modelFEATINDC)) {
			cnt = getCount("SWPRODSTRUCT");
			if (cnt == 0) {
				// 102.00 Count of => 1 RE RE RE must have at least one {LD:
				// SWFEATURE}
				// MINIMUM_ERR = must have at least one {0}
				args[0] = m_elist.getEntityGroup("SWFEATURE").getLongDescription();
				createMessage(CHECKLEVEL_RE, "MINIMUM_ERR", args);
			}
		}
		// 101.10 END

		// 111.00 AVAIL SWPRODSTRUCT: SWPRODSTRUCTAVAIL-d: AVAIL TM SW AVAIL
		// 112.00 WHEN AVAILTYPE = "First Order"
		EntityGroup swpsGrp = m_elist.getEntityGroup("SWPRODSTRUCT");
		addHeading(3, swpsGrp.getLongDescription() + " Planned Avail Checks:");

		// 105.00 AVAIL H SWPRODSTRUCT: SWPRODSTRUCTAVAIL-d: AVAIL TM SW AVAIL
		// 2016-01-26 Change Column G 106.00 WHEN AVAILTYPE = "Planned
		// Availability" or "MES Planned Availability"
		// 108.00 COUNTRYLIST "in aggregate G" A: AVAIL COUNTRYLIST W W E*2
		// {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
		// includes a Country that the {LD: MODEL} is not available in
		// 109.00 EFFECTIVEDATE => A: AVAIL EFFECTIVEDATE Yes W W E
		// {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} can
		// not be earlier than the {LD:AVAIL} {NDN: A: AVAIL}
		checkPsAndModelAvails(mdlItem, checklvl, getCheckLevel(checklvl, mdlItem, "ANNDATE"), mdlPlaAvailCtryTblA,
				swpsPlaAvailVctH, "SWPRODSTRUCTAVAIL", true);
		// checkPsAndModelAvails(mdlItem, checklvl,
		// getCheckLevel(checklvl,mdlItem,"ANNDATE"),
		// mdlMesPlaAvailCtryTblA, swpsPlaAvailVctH, "SWPRODSTRUCTAVAIL", true);

		// MES
		addHeading(3, swpsGrp.getLongDescription() + " MES Planned Avail Checks:");
		checkPsAndModelAvails(mdlItem, checklvl, getCheckLevel(checklvl, mdlItem, "ANNDATE"), mdlPlaAvailCtryTblA,
				swpsMesPlaAvailVctH, "SWPRODSTRUCTAVAIL", true);
		// checkPsAndModelAvails(mdlItem, checklvl,
		// getCheckLevel(checklvl,mdlItem,"ANNDATE"),
		// mdlMesPlaAvailCtryTblA, swpsMesPlaAvailVctH, "SWPRODSTRUCTAVAIL",
		// true);
		// 110.00 END 106 SWPRODSTRUCT.SWPRODSTRUCTAVAIL.AVAIL as set is ok here

		addHeading(3, swpsGrp.getLongDescription() + " First Order Avail Checks:");
		// 111.00 AVAIL SWPRODSTRUCT: SWPRODSTRUCTAVAIL-d: AVAIL TM SW AVAIL
		// 112.00 WHEN AVAILTYPE = "First Order"
		checkFirstOrderAvails(mdlItem, statusFlag, swpsGrp, "SWPRODSTRUCTAVAIL", checklvl); // PER
																							// SWPRODSTRUCT

		addHeading(3, swpsGrp.getLongDescription() + " Last Order Avail Checks:");
		// 117.00 AVAIL K SWPRODSTRUCT: SWPRODSTRUCTAVAIL-d: AVAIL TM SW AVAIL
		// 2016-01-26 Change Column G 118.00 WHEN AVAILTYPE = "Last Order" or
		// "MES Last Order"
		checkLastOrderAvails(LASTORDERAVAIL, statusFlag, swpsGrp, "SWPRODSTRUCTAVAIL", checklvl); // PER
																									// SWPRODSTRUCT
		// MES
		addHeading(3, swpsGrp.getLongDescription() + " MES Last Order Avail Checks:");
		checkLastOrderAvails(MESLASTORDERAVAIL, statusFlag, swpsGrp, "SWPRODSTRUCTAVAIL", checklvl); // PER
																										// SWPRODSTRUCT
	}

	/************************
	 * When MODEL is Service - must not have any MDLCGMDL relators - must not
	 * have any SEOCGSEO relators - must not have any FEATURE - must not have
	 * any SWFEATURE
	 * 
	 * @param mdlItem
	 * @param statusFlag
	 * @throws MiddlewareException
	 * @throws SQLException
	 * 
	 *             checks from ss 124.00 MODEL Root Service MODEL 125.00 WHEN
	 *             COFCAT = "Service" (102) 126.00 MODELCG MDLCGMDL-u 127.00
	 *             CountOf = 0 RE RE E must not be in a {LD MODELCG} {NDN:
	 *             MODELCG} ##128.00 SEOCG SEOCGSEO-u ##129.00 CountOf = 0 RE RE
	 *             E must not be in a {LD SEOCG} {NDN: SEOCG} 20120508 Change
	 *             128.00 SEOCG MODELWWSEO-d: SEOCGSEO-u 20120508 Change 129.00
	 *             CountOf = 0 RE RE E {LD: WWSEO} {NDN: WWSEO} must not be in a
	 *             {LD SEOCG} {NDN: SEOCG} 20120508 Add 129.20 SEOCG SEOCGMDL-u
	 *             20120508 Add 129.22 CountOf = 0 RE RE E must not be in a {LD
	 *             SEOCG} {NDN: SEOCG} 20120508 Add 129.24 END 129.20 130.00
	 *             FEATURE SWPRODSTRUCT-u 131.00 CountOf = 0 RE RE E must not
	 *             have any {LD: FEATURE) {NDN: FEATURE} 132.00 SWFEATURE
	 *             SWPRODSTRUCT-u 133.00 CountOf = 0 RE RE E must not have any
	 *             {LD: SWFEATURE) {NDN: SWFEATURE} 154.00 END 125
	 * 
	 */
	private void doServiceChecks(EntityItem mdlItem, String statusFlag) throws MiddlewareException, SQLException {
		addHeading(3, " Service Model Checks:");

		// 126.00 MODELCG MDLCGMDL-u
		// 127.00 CountOf = 0 RE RE E must not be in a {LD MODELCG} {NDN:
		// MODELCG}
		int cnt = getCount("MDLCGMDL");
		if (cnt != 0) {
			EntityGroup eGrp = m_elist.getEntityGroup("MODELCG");
			// MUST_NOT_BE_IN_ERR= must not be in a {0}
			for (int i = 0; i < eGrp.getEntityItemCount(); i++) {
				EntityItem item = eGrp.getEntityItem(i);
				args[0] = getLD_NDN(item);
				createMessage(getCheck_RE_RE_E(statusFlag), "MUST_NOT_BE_IN_ERR", args);
			}
		}
		// 20120508 Change 128.00 SEOCG MODELWWSEO-d: SEOCGSEO-u
		// 20120508 Change 129.00 CountOf = 0 RE RE E {LD: WWSEO} {NDN: WWSEO}
		// must not be in a {LD SEOCG} {NDN: SEOCG}
		cnt = getCount("MODELWWSEO");
		if (cnt != 0) {
			EntityGroup eGrp = m_elist.getEntityGroup("WWSEO");
			for (int i = 0; i < eGrp.getEntityItemCount(); i++) {
				EntityItem item = eGrp.getEntityItem(i);
				Vector SEOCGVct = PokUtils.getAllLinkedEntities(item, "SEOCGSEO", "SEOCG");
				if (SEOCGVct != null && SEOCGVct.size() > 0) {
					for (Iterator it = SEOCGVct.iterator(); it.hasNext();) {
						EntityItem seocg = (EntityItem) it.next();
						// MUST_NOT_BE_IN_ERR2= {0} must not be in {1}
						args[0] = getLD_NDN(item);
						args[1] = getLD_NDN(seocg);
						createMessage(getCheck_RE_RE_E(statusFlag), "MUST_NOT_BE_IN_ERR2", args);
					}
				}
			}
		}

		// 20120508 Add 129.20 SEOCG SEOCGMDL-u
		// 20120508 Add 129.22 CountOf = 0 RE RE E must not be in a {LD SEOCG}
		// {NDN: SEOCG}
		// 20120508 Add 129.24 END 129.20
		cnt = getCount("SEOCGMDL");
		if (cnt != 0) {
			EntityGroup eGrp = m_elist.getEntityGroup("SEOCG");
			// MUST_NOT_BE_IN_ERR= must not be in a {0}
			for (int i = 0; i < eGrp.getEntityItemCount(); i++) {
				EntityItem item = eGrp.getEntityItem(i);
				args[0] = getLD_NDN(item);
				createMessage(getCheck_RE_RE_E(statusFlag), "MUST_NOT_BE_IN_ERR", args);
			}
		}

		// 130.00 FEATURE PRODSTRUCT-u
		// 131.00 Count of = 0 RE RE E must not have any {LD: FEATURE) {NDN:
		// FEATURE}
		// a Service {LD: MODEL} can not have a {LD: FEATURE}
		// PSLINK_ERR = a {0} {1} can not have a {2}
		cnt = getCount("PRODSTRUCT");
		if (cnt != 0) {
			EntityGroup eGrp = m_elist.getEntityGroup("FEATURE");
			args[0] = "Service";
			args[1] = mdlItem.getEntityGroup().getLongDescription();
			args[2] = eGrp.getLongDescription();
			createMessage(getCheck_RE_RE_E(statusFlag), "PSLINK_ERR", args);
		}

		// 132.00 SWFEATURE SWPRODSTRUCT-u
		// 133.00 Count of = 0 RE RE E must not have any {LD: SWFEATURE) {NDN:
		// SWFEATURE}
		// a Service {LD: MODEL} can not have a {LD: SWFEATURE}
		// PSLINK_ERR = a {0} {1} can not have a {2}
		cnt = getCount("SWPRODSTRUCT");
		if (cnt != 0) {
			EntityGroup eGrp = m_elist.getEntityGroup("SWFEATURE");
			args[0] = "Service";
			args[1] = mdlItem.getEntityGroup().getLongDescription();
			args[2] = eGrp.getLongDescription();
			createMessage(getCheck_RE_RE_E(statusFlag), "PSLINK_ERR", args);
		}
	}

	/**
	 * Compare prodstruct firstorder avails Each ps firstorder avail must have a
	 * corresponding country in the ps plannedavail countries PER PRODSTRUCT The
	 * ps firstorder avail effdate can not be earlier than the MODELAVAIL
	 * plannedavail effdate
	 * 
	 * @param statusFlag
	 * @param psGrp
	 * @param psRelType
	 * @param checklvl
	 * @throws SQLException
	 * @throws MiddlewareException
	 * 
	 *             ------------------------- 111.00 AVAIL SWPRODSTRUCT:
	 *             SWPRODSTRUCTAVAIL-d: AVAIL TM SW AVAIL 112.00 WHEN AVAILTYPE
	 *             = "First Order" 113.00 ANNCODENAME 114.00 COUNTRYLIST "in
	 *             aggregate G" H: AVAIL COUNTRYLIST W W E*2 {LD: SWPRODSTRUCT}
	 *             {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} {LD:
	 *             COUNTRYLIST} includes a Country that does not have a "Planned
	 *             Availability" 115.00 EFFECTIVEDATE => A: AVAIL EFFECTIVEDATE
	 *             Yes W W E {LD: SWPRODSTRUCT} {NDN:SWPRODSTRUCT} {LD: AVAIL}
	 *             {NDN: AVAIL} must not be earlier than the {LD: MODEL}
	 *             {LD:AVAIL} {NDN: A: AVAIL} 116.00 END 112
	 */
	private void checkFirstOrderAvails(EntityItem mdlItem, String statusFlag, EntityGroup psGrp, String psRelType,
			int checklvl) throws SQLException, MiddlewareException {
		int oldDataChklvl = getCheckLevel(checklvl, mdlItem, "ANNDATE");

		for (int p = 0; p < psGrp.getEntityItemCount(); p++) {
			EntityItem psitem = psGrp.getEntityItem(p);

			Vector availVct = PokUtils.getAllLinkedEntities(psitem, psRelType, "AVAIL");
			Vector psPlaAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", PLANNEDAVAIL);
			Vector psMesPlaAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", MESPLANNEDAVAIL);
			Vector psFOAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", FIRSTORDERAVAIL);
			Hashtable psPlaAvailCtryTbl = getAvailByCountry(psPlaAvailVct, getCheck_W_W_E(statusFlag));
			Hashtable psMesPlaAvailCtryTbl = getAvailByCountry(psMesPlaAvailVct, getCheck_W_W_E(statusFlag));
			addDebug("checkFirstOrderAvails " + psitem.getKey() + ": " + psRelType + "-d: AVAIL all avail:"
					+ availVct.size() + " psPla:" + psPlaAvailVct.size() + " psMesPla:" + psMesPlaAvailVct.size()
					+ " psFO: " + psFOAvailVct.size() + " psPlaAvailCtryTbl " + psPlaAvailCtryTbl.keySet()
					+ " psMesPlaAvailCtryTbl " + psMesPlaAvailCtryTbl.keySet());

			// 111.00 AVAIL SWPRODSTRUCT: SWPRODSTRUCTAVAIL-d: AVAIL TM SW AVAIL
			// 115.00 EFFECTIVEDATE => A: AVAIL EFFECTIVEDATE Yes W W E can not
			// be available before the MODEL
			for (int i = 0; i < psFOAvailVct.size(); i++) {
				EntityItem avail = (EntityItem) psFOAvailVct.elementAt(i);
				Vector mdlPlaVct = new Vector(); // hold onto model plannedavail
													// for date checks incase
													// same avail for mult ctrys
				checkCtryMismatch(avail, mdlPlaAvailCtryTblA, mdlPlaVct, checklvl);
				// Vector mdlMesPlaVct = new Vector();
				// checkCtryMismatch(avail, mdlMesPlaAvailCtryTblA,
				// mdlMesPlaVct, checklvl);

				String date1 = getAttrValueAndCheckLvl(avail, "EFFECTIVEDATE", checklvl);
				checkSwpsFoAvailWithAvailA(mdlPlaVct, date1, psitem, avail, checklvl);
				// checkSwpsFoAvailWithAvailA(mdlMesPlaVct, date1, psitem,
				// avail, checklvl);
				mdlPlaVct.clear();
				// mdlMesPlaVct.clear();

				// 114.00 COUNTRYLIST "IN aggregate G" H: AVAIL COUNTRYLIST W W
				// E*2 {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN:
				// AVAIL} {LD: COUNTRYLIST} includes a Country that does not
				// have a "Planned Availability"
				checkSwpsFoAvailWithAvailH(psitem, avail, psPlaAvailCtryTbl, oldDataChklvl);
				if (psMesPlaAvailVct.size() > 0)
					checkSwpsFoAvailWithAvailH(psitem, avail, psMesPlaAvailCtryTbl, oldDataChklvl);
			} // end firstorder avail loop
				// release memory
			availVct.clear();
			psPlaAvailVct.clear();
			psMesPlaAvailVct.clear();
			psFOAvailVct.clear();
			psPlaAvailCtryTbl.clear();
			psMesPlaAvailCtryTbl.clear();
		} // end ps loop
	}

	/**
	 * Check SWPRODSTRUCT First Order Avail with A:AVAIL
	 * 
	 * @param mdlPlaOrMesPlaVec
	 * @param date1
	 * @param psitem
	 * @param psFoAvail
	 * @param checklvl
	 * @throws SQLException
	 * @throws MiddlewareException
	 * 
	 *             115.00 EFFECTIVEDATE => A: AVAIL EFFECTIVEDATE Yes W W E {LD:
	 *             SWPRODSTRUCT} {NDN:SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
	 *             must not be earlier than the {LD: MODEL} {LD:AVAIL} {NDN: A:
	 *             AVAIL}
	 */
	private void checkSwpsFoAvailWithAvailA(Vector mdlPlaOrMesPlaVec, String date1, EntityItem psitem,
			EntityItem psFoAvail, int checklvl) throws SQLException, MiddlewareException {
		// do the date checks now
		for (int m = 0; m < mdlPlaOrMesPlaVec.size(); m++) {
			EntityItem mdlplaAvail = (EntityItem) mdlPlaOrMesPlaVec.elementAt(m);
			// 115.00 EFFECTIVEDATE => A: AVAIL EFFECTIVEDATE Yes W W E can not
			// be available before the MODEL
			String mdldate2 = getAttrValueAndCheckLvl(mdlplaAvail, "EFFECTIVEDATE", checklvl);
			addDebug("checkFirstOrderAvails  " + psitem.getEntityType() + "-firstorder:" + psFoAvail.getKey()
					+ " EFFECTIVEDATE:" + date1 + " can not be earlier than mdlplannedavail: " + mdlplaAvail.getKey()
					+ " EFFECTIVEDATE:" + mdldate2);
			boolean isok = checkDates(date1, mdldate2, DATE_GR_EQ); // date1=>date2
			if (!isok) {
				// CANNOT_BE_EARLIER_ERR2 = {0} {1} must not be earlier than the
				// {2} {3}
				// {LD: PS} {NDN: PS} {LD: AVAIL} {NDN: AVAIL} can not be
				// earlier than the {LD:MODEL} {LD:AVAIL} {NDN: A: AVAIL}
				args[0] = getLD_NDN(psitem);
				args[1] = getLD_NDN(psFoAvail);
				args[2] = m_elist.getParentEntityGroup().getLongDescription();
				args[3] = getLD_NDN(mdlplaAvail);
				createMessage(checklvl, "CANNOT_BE_EARLIER_ERR2", args);
			}
		}
	}

	/**
	 * Check SWPRODSTRUCT First Order Avail with H:AVAIl
	 * 
	 * @param psitem
	 * @param psFoAvail
	 * @param psPlaOrMesPlaAvailCtryTbl
	 * @param oldDataChklvl
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private void checkSwpsFoAvailWithAvailH(EntityItem psitem, EntityItem psFoAvail,
			Hashtable psPlaOrMesPlaAvailCtryTbl, int oldDataChklvl) throws SQLException, MiddlewareException {
		// 114.00 COUNTRYLIST "in aggregate G" H: AVAIL COUNTRYLIST W W E*2 {LD:
		// SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} includes a
		// Country that the {LD: SWPRODSTRUCT} is not available in
		String missingCtry = checkCtryMismatch(psFoAvail, psPlaOrMesPlaAvailCtryTbl, oldDataChklvl);
		if (missingCtry.length() > 0) {
			addDebug("checkFirstOrderAvails " + psitem.getKey() + " firstorder:" + psFoAvail.getKey()
					+ " COUNTRYLIST had ctry [" + missingCtry + "] that were not in the ps plannedavail");
			// MISSING_PLA_CTRY_ERR = {0} {1} includes a Country that does not
			// have a &quot;Planned Availability&quot; Extra countries are: {2}
			// {LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a country
			// that does not have a "Planned Availability"
			args[0] = getLD_NDN(psitem) + " " + getLD_NDN(psFoAvail);
			args[1] = PokUtils.getAttributeDescription(psFoAvail.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
			args[2] = missingCtry;
			createMessage(oldDataChklvl, "MISSING_PLA_CTRY_ERR", args);
		}
	}

	/***************
	 * Each ps lastorder avail ctry must be a subset of all ps plannedavail
	 * countries PER PRODSTRUCT Each ps lastorder avail effdate can not be later
	 * than the MODELAVAIL lastorder effdate
	 * 
	 * @param statusFlag
	 * @param psGrp
	 * @param psRelType
	 * @param checklvl
	 * @throws SQLException
	 * @throws MiddlewareException
	 *             ----------------------------- 117.00 AVAIL K SWPRODSTRUCT:
	 *             SWPRODSTRUCTAVAIL-d: AVAIL TM SW AVAIL 118.00 WHEN AVAILTYPE
	 *             = "Last Order" 119.00 ANNCODENAME 120.00 COUNTRYLIST "in
	 *             aggregate G" H: AVAIL COUNTRYLIST W W E*2 {LD: SWPRODSTRUCT}
	 *             {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} includes a
	 *             Country that does not have a "Planned Availability" 121.00
	 *             EFFECTIVEDATE <= C: AVAIL EFFECTIVEDATE Yes W W E {LD:
	 *             SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
	 *             must not be later than the {LD:MODEL} {LD:AVAIL} {NDN: C:
	 *             AVAIL} 122.00 END 118
	 */
	private void checkLastOrderAvails(String lastOrderAvailType, String statusFlag, EntityGroup psGrp, String psRelType,
			int checklvl) throws SQLException, MiddlewareException {
		EntityItem mdlItem = m_elist.getParentEntityGroup().getEntityItem(0);
		int oldDatachklvl = getCheckLevel(checklvl, mdlItem, "ANNDATE");

		for (int p = 0; p < psGrp.getEntityItemCount(); p++) {
			EntityItem psitem = psGrp.getEntityItem(p);

			Vector availVct = PokUtils.getAllLinkedEntities(psitem, psRelType, "AVAIL");
			Vector psPlaAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", PLANNEDAVAIL);
			Vector psMesPlaAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", MESPLANNEDAVAIL);
			Vector psLOAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", lastOrderAvailType);
			Hashtable psPlaCtryTbl = getAvailByCountry(psPlaAvailVct, getCheck_W_W_E(statusFlag));
			Hashtable psMesPlaCtryTbl = getAvailByCountry(psMesPlaAvailVct, getCheck_W_W_E(statusFlag));
			addDebug("checkLastOrderAvails " + psitem.getKey() + ": " + psRelType + "-d: AVAIL all avail:"
					+ availVct.size() + " psPla:" + psPlaAvailVct.size() + " psMesPla:" + psMesPlaAvailVct.size()
					+ " psLO: " + psLOAvailVct.size() + " psPlaCtryTbl " + psPlaCtryTbl.keySet() + " psMesPlaCtryTbl "
					+ psMesPlaCtryTbl.keySet());

			// 117.00 AVAIL K SWPRODSTRUCT: SWPRODSTRUCTAVAIL-d: AVAIL TM SW
			// AVAIL
			// 118.00 WHEN AVAILTYPE = "Last Order"
			for (int i = 0; i < psLOAvailVct.size(); i++) {
				EntityItem avail = (EntityItem) psLOAvailVct.elementAt(i);
				Vector mdlLoVct = new Vector(); // hold onto mdl lastorder for
												// date checks incase same avail
												// for mult ctrys
				checkCtryMismatch(avail, mdlLOAvailCtryTblC, mdlLoVct, checklvl);
				// Vector mdlMesLoVct = new Vector(); // hold onto mdl lastorder
				// for date checks incase same avail for mult ctrys
				// if(MESLASTORDERAVAIL.equals(lastOrderAvailType))
				// checkCtryMismatch(avail, mdlMesLOAvailCtryTblC, mdlMesLoVct,
				// checklvl);
				String date1 = getAttrValueAndCheckLvl(avail, "EFFECTIVEDATE", checklvl);
				checkSwpsLoAvailWithAvailA(mdlLoVct, psitem, date1, avail, checklvl);
				// if(MESLASTORDERAVAIL.equals(lastOrderAvailType))
				// checkSwpsLoAvailWithAvailA(mdlMesLoVct, psitem, date1, avail,
				// checklvl);
				mdlLoVct.clear();

				// 120.00 COUNTRYLIST "IN aggregate G" H: AVAIL COUNTRYLIST W W
				// E*2 {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN:
				// AVAIL} includes a Country that does not have a "Planned
				// Availability"
				checkSwpsLoAvailWithAvailH(avail, psPlaCtryTbl, oldDatachklvl, psitem);
				if (MESLASTORDERAVAIL.equals(lastOrderAvailType))
					checkSwpsLoAvailWithAvailH(avail, psMesPlaCtryTbl, oldDatachklvl, psitem);
			} // end lastorder avail loop
				// release memory
			availVct.clear();
			psPlaAvailVct.clear();
			psLOAvailVct.clear();
			psPlaCtryTbl.clear();
			psMesPlaCtryTbl.clear();
		} // end ps group loop
	}

	private void checkSwpsLoAvailWithAvailA(Vector mdlLoVct, EntityItem psitem, String date1, EntityItem avail,
			int checklvl) throws SQLException, MiddlewareException {
		// do the date checks now
		for (int m = 0; m < mdlLoVct.size(); m++) {
			EntityItem loAvail = (EntityItem) mdlLoVct.elementAt(m);
			// 121.00 EFFECTIVEDATE <= C: AVAIL EFFECTIVEDATE Yes W W E
			// {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
			// must not be later than the {LD:MODEL} {LD:AVAIL} {NDN: C: AVAIL}
			String date2 = getAttrValueAndCheckLvl(loAvail, "EFFECTIVEDATE", checklvl);
			addDebug("checkLastOrderAvails " + psitem.getEntityType() + "-lastorder:" + avail.getKey()
					+ " EFFECTIVEDATE:" + date1 + " must not be later than mdllastorder: " + loAvail.getKey()
					+ " EFFECTIVEDATE:" + date2);
			boolean isok = checkDates(date1, date2, DATE_LT_EQ); // date1<=date2
			if (!isok) {
				// CANNOT_BE_LATER_ERR = {0} {1} must not be later than the {2}
				// {3}
				args[0] = getLD_NDN(psitem);
				args[1] = getLD_NDN(avail);
				args[2] = m_elist.getParentEntityGroup().getLongDescription();
				args[3] = getLD_NDN(loAvail);
				createMessage(checklvl, "CANNOT_BE_LATER_ERR", args);
			}
		}
	}

	/**
	 * Check SWPRODSTRUCT AVAIL WITH H:AVAIL
	 * 
	 * @param avail
	 * @param psPlaCtryTbl
	 * @param oldDatachklvl
	 * @param psitem
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private void checkSwpsLoAvailWithAvailH(EntityItem avail, Hashtable psPlaCtryTbl, int oldDatachklvl,
			EntityItem psitem) throws SQLException, MiddlewareException {
		// 120.00 COUNTRYLIST "in aggregate G" H: AVAIL COUNTRYLIST W W E*2
		// {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
		// includes a Country that does not have a "Planned Availability"
		String missingCtry = checkCtryMismatch(avail, psPlaCtryTbl, oldDatachklvl);
		if (missingCtry.length() > 0) {
			addDebug("checkLastOrderAvails  " + psitem.getKey() + " lastorder:" + avail.getKey()
					+ " COUNTRYLIST had ctry [" + missingCtry + "] that were not in the ps plannedavail");
			// MISSING_PLA_CTRY_ERR = {0} {1} includes a Country that does not
			// have a &quot;Planned Availability&quot; Extra countries are: {2}
			// {LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a country
			// that does not have a "Planned Availability"
			args[0] = getLD_NDN(psitem) + " " + getLD_NDN(avail);
			args[1] = PokUtils.getAttributeDescription(avail.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
			args[2] = missingCtry;
			createMessage(oldDatachklvl, "MISSING_PLA_CTRY_ERR", args);
		}
	}

	/***********************************************
	 * Get ABR description
	 *
	 * @return java.lang.String
	 */
	public String getDescription() {
		String desc = "MODEL ABR";
		return desc;
	}

	/***********************************************
	 * Get the version
	 *
	 * @return java.lang.String
	 */
	public String getABRVersion() {
		return "1.35";
	}
}