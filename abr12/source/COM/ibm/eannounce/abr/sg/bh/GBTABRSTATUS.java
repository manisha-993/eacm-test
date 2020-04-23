//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2011  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package COM.ibm.eannounce.abr.sg.bh;

import COM.ibm.eannounce.abr.util.ABRUtil;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;

import com.ibm.transform.oim.eacm.util.*;

import java.sql.SQLException;
import java.util.*;

/**********************************************************************************
 *
 * From "BH FS ABR Data Quality 20110309.doc"
 * 
 * need the following:
 * 	- WWOCCODE must be searchable and need it to be U - WWOCPARNTS needs to be U with same flag values
 * 	- SIEBELPRODLEV must be searchable
 * 	- need GBT search action -  rupal says it does not need to be domain restricted
 *
XIV.	GBT
A.	Checking
A root entity with a Record Type (RECTYPE) = Delete (RT0030) is not checked and the ABR is set to Passed.

1.	Ensure that WW OC Brand (WWOCCODE) is unique considering Effective Date (EFFECTIVEDATE) and Delete Date (DELDATE).

Note: WW OC PARENTS and WW OC Brand are Unique Flags and will have an EXIST Rule

Consider a SEARCH action using WWOCCODE. If multiple instances of GBT are defined, then perform the following checking:

If EFFECTIVEDATE is not specified, assume 1980-01-01.
If DELDATE is not specified, assume 9999-12-31.

Verify that the date ranges do not overlap. The DELDATE of one may match the EFFECTIVEDATE of another and 
is not considered an overlap.

Error Message: See the Checks Spreadsheet
List all GBTs that have a conflict 

2.	Ensure that WW OC Parents (WWOCPARNTS) references a valid WW OC Brand (WWOCCODE) if Siebel Product Level 
is not “10”.

The Siebel Product Level (SIEBELPRODLEV) of the root determines the Siebel Product Level (SIEBELPRODLEV) of 
the record that is searched for  as follows:
Root 	Parent
35		30
30		20
20 		10
10		n/a 

Use the root entity WWOCPARNTS to search for WWOCCODE where the “Search for” SIEBELPRODLEV is as indicated 
in the preceding table. The “Search for” record must have a Delete Date of 9999-12-31.

Error Message: See the Checks Spreadsheet


GBTABRSTATUS_class=COM.ibm.eannounce.abr.sg.bh.GBTABRSTATUS
GBTABRSTATUS_enabled=true
GBTABRSTATUS_idler_class=A
GBTABRSTATUS_keepfile=true
GBTABRSTATUS_report_type=DGTYPE01
GBTABRSTATUS_CAT1=RPTCLASS.GBTABRSTATUS
GBTABRSTATUS_CAT2=
GBTABRSTATUS_CAT3=RPTSTATUS

 */
//$Log: GBTABRSTATUS.java,v $
//Revision 1.2  2011/03/30 12:10:32  wendy
//correct error levels
//
//Revision 1.1  2011/03/24 11:28:29  wendy
//initial code
//
public class GBTABRSTATUS extends DQABRSTATUS
{

	/*
SIEBELPRODLEV	PL0010	10
SIEBELPRODLEV	PL0020	20
SIEBELPRODLEV	PL0030	30
SIEBELPRODLEV	PL0035	35

Root 	Parent
35		30
30		20
20 		10
10		n/a
	 */
	private static final String SIEBELPRODLEV_10 = "PL0010";
	private static final Hashtable SIEBELPRODLEV_TBL;
	static {
		SIEBELPRODLEV_TBL = new Hashtable(); // needed when looking up values to search on
		SIEBELPRODLEV_TBL.put("PL0035", "PL0030");
		SIEBELPRODLEV_TBL.put("PL0030", "PL0020");
		SIEBELPRODLEV_TBL.put("PL0020", "PL0010");
		//PL0010 is n/a so leave as null
	}
	/*
RECTYPE	RT0010	Active
RECTYPE	RT0020	Add
RECTYPE	RT0030	Delete
RECTYPE	RT0040	Move
RECTYPE	RT0050	Move/Other
RECTYPE	RT0060	Other
RECTYPE	RT0070	Text
RECTYPE	RT0080	Text/Move/Other
RECTYPE	RT0090	Text/Other
	 */
	private static final String DEL_RECTYPE="RT0030";
	private static final String GBT_SRCHACTION_NAME = "SRDOIMDSGBT";//"SRDGBT";this one isnt finding matching WWOCCODE

	/**********************************
	 * dont need to pull a VE for this
	 */
	protected boolean isVEneeded(String statusFlag) {
		return false;
	}

	/*
from sets ss:
201.00	GBT		Root Entity							
202.00	SET			GBT				ADSABRSTATUS	norfr	&ADSFEED
203.00	END	201.00	GBT							
	 */
	/**********************************
	 * complete abr processing after status moved to readyForReview; (status was chgreq)
	 * 	Status changed to Ready for Review
	 */
	protected void completeNowR4RProcessing() throws
	java.sql.SQLException,
	COM.ibm.opicmpdh.middleware.MiddlewareException,
	COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	{
		// setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getRFRQueuedValue("ADSABRSTATUS"));
	}

	/**********************************
	 * complete abr processing after status moved to final; (status was r4r)
	 *STATUS changed to Final
	 *
	 *202.00	SET			GBT				ADSABRSTATUS	norfr	&ADSFEED
	 */
	protected void completeNowFinalProcessing() throws
	java.sql.SQLException,
	COM.ibm.opicmpdh.middleware.MiddlewareException,
	COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	{
		setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getQueuedValue("ADSABRSTATUS"));
	}

	/**********************************
	 * Note the ABR is only called when
	 * DATAQUALITY transitions from 'Draft to Ready for Review',
	 *	'Change Request to Ready for Review' and from 'Ready for Review to Final'
	 *
checking from ss:
1.00	GBT		Root									
2.00			WWOCCODE	Unique In	GBT	WWOCCODE		W	W	E	"WWOCCODE is a database key
multiple records for the key must not have overlapping dates
See the document for specifics"	{LD: GBT} {NDN: GBT} is not unique.
3.00			WWOCPARNTS	Verify In	GBT	WWOCCODE		W	W	W	"Parent must exist
See the document for specifics"	LD: GBT} {NDN: GBT} does not have a parent.
	 */
	protected void doDQChecking(EntityItem rootEntity, String statusFlag) throws Exception
	{
		//A root entity with a Record Type (RECTYPE) = Delete (RT0030) is not checked and the ABR is set to Passed.
		String rectype = PokUtils.getAttributeFlagValue(rootEntity, "RECTYPE");
		addDebug(rootEntity.getKey()+" RECTYPE: "+rectype);
		if (DEL_RECTYPE.equals(rectype)){
			//NOT_CHECKED = {0} is not checked.
			args[0] = this.getLD_Value(rootEntity, "RECTYPE");
			addResourceMsg("NOT_CHECKED",args);
			return;
		}

		String wwoccode = PokUtils.getAttributeFlagValue(rootEntity, "WWOCCODE");
		addDebug(rootEntity.getKey()+" wwoccode: "+wwoccode);
		if (wwoccode==null){ // this should exist
			//REQ_NOTPOPULATED_ERR = {0} {1} is required and does not have a value.
			args[0] = "";
			args[1] = PokUtils.getAttributeDescription(rootEntity.getEntityGroup(), "WWOCCODE", "WWOCCODE");
			addError("REQ_NOTPOPULATED_ERR",args);
			return;
		}

		//Verify that the date ranges do not overlap. The DELDATE of one may match the EFFECTIVEDATE of another and is 
		//not considered an overlap.
		verifyUniqueWWOCCODE(wwoccode,statusFlag);

		//Use the root entity WWOCPARNTS to search for WWOCCODE where the “Search for” SIEBELPRODLEV is as indicated in the 
		//preceding table. The “Search for” record must have a Delete Date of 9999-12-31.	
		verifyParent(rootEntity,statusFlag);
	}

	/**
	 * WWOCCODE	T		WW OC Brand
	 * Consider a SEARCH action using WWOCCODE. If multiple instances of GBT are defined, then perform the following 
		checking:

		If EFFECTIVEDATE is not specified, assume 1980-01-01.
		If DELDATE is not specified, assume 9999-12-31.

		Verify that the date ranges do not overlap. The DELDATE of one may match the EFFECTIVEDATE of another and is 
		not considered an overlap.

		Error Message: See the Checks Spreadsheet
		List all GBTs that have a conflict  
	 * @throws MiddlewareShutdownInProgressException 
	 * @throws MiddlewareException 
	 * @throws SQLException 
	 */
	private void verifyUniqueWWOCCODE(String wwoccode, String statusFlag) 
	throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException{
		// search for all GBT with this WWOCCODE
		EntityItem eia[] = searchForGBT(wwoccode,null);

		if (eia!=null && eia.length>1){ // there should always be 1, itself unless search crashes
			checkDateOverlap(eia, statusFlag); // deldate and effectivedate are nav attributes
		}
	}

	/**
	 * @param wwoccode
	 * @param siebelprodlev
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException
	 */
	private EntityItem[] searchForGBT(String wwoccode, String siebelprodlev) 
	throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException
	{
		Vector attrVct = new Vector(1);
		attrVct.addElement("WWOCCODE");
		Vector valVct = new Vector(1);
		valVct.addElement(wwoccode);
		if (siebelprodlev!=null){
			attrVct.addElement("SIEBELPRODLEV"); 
			valVct.addElement(siebelprodlev);
		}

		EntityItem eia[]= null;
		try{
			StringBuffer debugSb = new StringBuffer();
			addDebug("Searching for GBT for attrs: "+attrVct+" values: "+valVct);
			eia= ABRUtil.doSearch(getDatabase(), m_prof, 
					GBT_SRCHACTION_NAME, "GBT", false, attrVct, valVct, debugSb);
			if (debugSb.length()>0){
				addDebug(debugSb.toString());
			}
		}catch(SBRException exc){
			// these exceptions are for missing flagcodes or failed business rules, dont pass back
			java.io.StringWriter exBuf = new java.io.StringWriter();
			exc.printStackTrace(new java.io.PrintWriter(exBuf));
			addDebug("searchForGBT SBRException: "+exBuf.getBuffer().toString());
		}

		if(eia!=null && eia.length>0){ 
			for (int i=0; i<eia.length; i++){
				addDebug("searchForGBT found "+eia[i].getKey());
			}
		}else{
			addDebug("searchForGBT returned 0 ");
		}

		attrVct.clear();
		valVct.clear();
		return eia;
	}	

	/**
	 * If EFFECTIVEDATE is not specified, assume 1980-01-01.
		If DELDATE is not specified, assume 9999-12-31.

		Verify that the date ranges do not overlap. The DELDATE of one may match the EFFECTIVEDATE of another and is 
		not considered an overlap.
2.00			WWOCCODE	Unique In	GBT	WWOCCODE		W	W	E	"WWOCCODE is a database key
multiple records for the key must not have overlapping dates
See the document for specifics"	{LD: GBT} {NDN: GBT} is not unique.
	 * @param eia
	 * @param statusFlag
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private void checkDateOverlap(EntityItem eia[], String statusFlag) throws SQLException, MiddlewareException 
	{
		addDebug("checkDateOverlap entered for GBT eia.length: "+eia.length);
		Vector ucVct = new Vector();
		// make sure deldate is after effdate
		for (int i=0; i<eia.length; i++){
			EntityItem ei = eia[i];
			String deldate = PokUtils.getAttributeValue(ei, "DELDATE", "", FOREVER_DATE, false);
			String effdate = PokUtils.getAttributeValue(ei, "EFFECTIVEDATE", "", EPOCH_DATE, false);
			if(effdate.compareTo(deldate)>0){
				//DATE_ERR=the {0} {1} must be earlier than the {2}
				args[0] = getLD_NDN(ei);
				if(ei.getEntityType().equals(getEntityType())&& ei.getEntityID()==getEntityID()){
					args[0]="";
				}
				args[1] = this.getLD_Value(ei,"EFFECTIVEDATE");
				args[2] = this.getLD_Value(ei, "DELDATE",deldate);
				addError("DATE_ERR",args);
				continue;
			}

			UniqueCoverage uc = new UniqueCoverage(ei);
			ucVct.add(uc);
		}

		//sort by EFFECTIVEDATE  	
		Collections.sort(ucVct);

		UniqueCoverage prevUc = (UniqueCoverage)ucVct.firstElement();
		addDebug("checkDateOverlap prevUc "+prevUc);
		Vector outputVct = new Vector();
		// look for date overlap 
		for (int i=1; i<ucVct.size(); i++)	{
			UniqueCoverage uc = (UniqueCoverage)ucVct.elementAt(i);
			addDebug("   checkDateOverlap uc "+uc);
			if(prevUc.deldate.compareTo(uc.effdate)>0){
				//NOT_UNIQUE_ERR2 = {0} is not unique.
				if(!outputVct.contains(uc.item.getKey())){
					args[0] = getLD_NDN(uc.item);
					if(uc.item.getEntityID()==getEntityID()){
						args[0] =""; // this is root
					}
					//2.00			WWOCCODE	Unique In	GBT	WWOCCODE		W	W	E	"WWOCCODE is a database key
					//multiple records for the key must not have overlapping dates
					//See the document for specifics"	{LD: GBT} {NDN: GBT} is not unique.
					createMessage(getCheck_W_W_E(statusFlag),"NOT_UNIQUE_ERR2",args);
					outputVct.add(uc.item.getKey());
				}
				if(!outputVct.contains(prevUc.item.getKey())){
					args[0] = getLD_NDN(prevUc.item);
					if(prevUc.item.getEntityID()==getEntityID()){
						args[0] =""; // this is root
					}
					//2.00			WWOCCODE	Unique In	GBT	WWOCCODE		W	W	E	"WWOCCODE is a database key
					//multiple records for the key must not have overlapping dates
					//See the document for specifics"	{LD: GBT} {NDN: GBT} is not unique.
					createMessage(getCheck_W_W_E(statusFlag),"NOT_UNIQUE_ERR2",args);
					outputVct.add(prevUc.item.getKey());
				}
				//DATE_RANGE_OVERLAP_ERR={0} {1} and {2} {3} overlap.
				args[0] = getLD_NDN(prevUc.item);
				args[1] = this.getLD_Value(prevUc.item, "DELDATE",prevUc.deldate);
				args[2] = getLD_NDN(uc.item);
				args[3] = this.getLD_Value(uc.item, "EFFECTIVEDATE");
				createMessage(getCheck_W_W_E(statusFlag),"DATE_RANGE_OVERLAP_ERR",args);
			}
			prevUc = uc;
		}

		// release memory
		for (int u=0; u<ucVct.size(); u++){
			UniqueCoverage uc = (UniqueCoverage)ucVct.elementAt(u);
			uc.dereference();
		}  

		ucVct.clear();
	}
	/**
	 * 	2.	Ensure that WW OC Parents (WWOCPARNTS) references a valid WW OC Brand (WWOCCODE) if Siebel Product Level 
	 * is not “10”.
	 *
	 * The Siebel Product Level (SIEBELPRODLEV) of the root determines the Siebel Product Level (SIEBELPRODLEV) of 
		the record that is searched for  as follows:
		Root 	Parent
		35		30
		30		20
		20 		10
		10		n/a  

		Use the root entity WWOCPARNTS to search for WWOCCODE where the “Search for” SIEBELPRODLEV is as indicated in the 
		preceding table. The “Search for” record must have a Delete Date of 9999-12-31.

		3.00		WWOCPARNTS	Verify In	GBT	WWOCCODE		W	W	W	"Parent must exist
			See the document for specifics"	LD: GBT} {NDN: GBT} does not have a parent.
					
	 * @param rootEntity
	 * @param statusFlag
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException
	 */
	private void verifyParent(EntityItem rootEntity, String statusFlag) 
	throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException
	{
		String SIEBELPRODLEV = PokUtils.getAttributeFlagValue(rootEntity, "SIEBELPRODLEV");
		addDebug("verifyParent SIEBELPRODLEV: "+SIEBELPRODLEV);
		if (SIEBELPRODLEV!=null){
			if(!SIEBELPRODLEV_10.equals(SIEBELPRODLEV)){
				//need long description to match on now
				String wwocparnts = PokUtils.getAttributeValue(rootEntity, "WWOCPARNTS", "", null, false);
				addDebug("verifyParent WWOCPARNTS: "+wwocparnts);
				if (wwocparnts==null){ 
					//REQ_NOTPOPULATED_ERR = {0} {1} is required and does not have a value.
					args[0] = "";
					args[1] = PokUtils.getAttributeDescription(rootEntity.getEntityGroup(), "WWOCPARNTS", "WWOCPARNTS");
					addError("REQ_NOTPOPULATED_ERR",args);
					return;
				}
				//We will try to match Long Description if that works for you since Linda will be creating these 
				//two fields via UI add allowed value function and has to make sure long description match.
				//so find matching description and use that flagcode
				wwocparnts = getFlagCodeForDesc(rootEntity, wwocparnts);
				addDebug("verifyParent wwocparnts after mapping: "+wwocparnts);
				if (wwocparnts==null){ //this would be a meta error
					//MISSING_PARENT_ERR = does not have a parent. 
					addError("MISSING_PARENT_ERR",null);
					return;
				}

				String parentvalue = (String)SIEBELPRODLEV_TBL.get(SIEBELPRODLEV);
				addDebug("verifyParent SIEBELPRODLEV parentvalue: "+parentvalue);

				//Use the root entity WWOCPARNTS to search for WWOCCODE where the “Search for” SIEBELPRODLEV 
				//is as indicated in the preceding table. The “Search for” record must have a Delete Date of 9999-12-31.
				EntityItem eia[] = searchForGBT(wwocparnts, parentvalue); 

				if(eia!=null && eia.length>0){
					boolean hasparent = false;
					for (int i=0; i<eia.length; i++){
						EntityItem gbt = eia[i];
						String deldate = PokUtils.getAttributeValue(gbt, "DELDATE", "", FOREVER_DATE, false);
						addDebug("verifyParent "+gbt.getKey()+" deldate: "+deldate);
						//The “Search for” record must have a Delete Date of 9999-12-31.
						if(deldate.equals(FOREVER_DATE)){
							hasparent = true;
							break;
						}
					}
					// dates were wrong
					if(!hasparent){
						//3.00			WWOCPARNTS	Verify In	GBT	WWOCCODE		W	W	W	"Parent must exist
						//See the document for specifics"	LD: GBT} {NDN: GBT} does not have a parent.
						//MISSING_PARENT_DATE_ERR = does not have a parent with {0} {1}. 
						args[0] = PokUtils.getAttributeDescription(rootEntity.getEntityGroup(), "DELDATE", "DELDATE");
						args[1] = FOREVER_DATE;
						createMessage(CHECKLEVEL_W,"MISSING_PARENT_DATE_ERR",args);
					}
				}else{
					//search didnt find one
					//MISSING_PARENT_ERR = does not have a parent. 
					createMessage(CHECKLEVEL_W,"MISSING_PARENT_ERR",null);
				}
			}
		}else{
			//REQ_NOTPOPULATED_ERR = {0} {1} is required and does not have a value.
			args[0] = "";
			args[1] = PokUtils.getAttributeDescription(rootEntity.getEntityGroup(), "SIEBELPRODLEV", "SIEBELPRODLEV");
			addError("REQ_NOTPOPULATED_ERR",args);
		}
	}

	/**
	 * We will try to match Long Description if that works for you since Linda will be creating these 
	 * two fields via UI add allowed value function and has to make sure long description match.
	 * so find matching description and use that flagcode
	 * 
	 * @param rootEntity
	 * @param wwocparntsDesc
	 * @return
	 */
	private String getFlagCodeForDesc(EntityItem rootEntity, String wwocparntsDesc){
		String flagcode=null;
		//get meta for WWOCCODE
		EANMetaFlagAttribute ma = (EANMetaFlagAttribute) rootEntity.getEntityGroup().getMetaAttribute("WWOCCODE");
		for (int im = 0; im < ma.getMetaFlagCount(); im++) {
			MetaFlag mf = ma.getMetaFlag(im);
			if(mf.toString().equals(wwocparntsDesc)){
				flagcode=mf.getFlagCode();
				break;
			}
		} 

		return flagcode;
	}
	/***********************************************
	 *  Get ABR description
	 *
	 *@return java.lang.String
	 */
	public String getDescription()
	{
		String desc =  "GBT ABR.";
		return desc;
	}

	/***********************************************
	 *  Get the version
	 *
	 *@return java.lang.String
	 */
	public String getABRVersion()
	{
		return "$Revision: 1.2 $";
	}
	/**
	 *  If EFFECTIVEDATE is not specified, assume 1980-01-01.
		If DELDATE is not specified, assume 9999-12-31.

		Verify that the date ranges do not overlap. The DELDATE of one may match the EFFECTIVEDATE of another and is 
		not considered an overlap.
	 *
	 */
	private class UniqueCoverage implements Comparable
	{
		EntityItem item = null;
		String deldate = FOREVER_DATE;
		String effdate = EPOCH_DATE;
		UniqueCoverage(EntityItem ei)
		{  
			item = ei;
			deldate = PokUtils.getAttributeValue(item, "DELDATE", "", deldate, false);
			effdate = PokUtils.getAttributeValue(item, "EFFECTIVEDATE", "", effdate, false);
			addDebug("UniqueCoverage "+this);
		}

		void dereference(){
			item = null;
			effdate = null;
			deldate = null;
		}
		public int compareTo(Object o) {
			// order by effdate
			return effdate.compareTo(((UniqueCoverage)o).effdate);
		}
		public String toString(){
			return item.getKey()+" effdate "+effdate+" deldate "+deldate;
		}
	}
}
