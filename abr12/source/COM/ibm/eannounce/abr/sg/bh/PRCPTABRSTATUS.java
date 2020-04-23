//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2010  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package COM.ibm.eannounce.abr.sg.bh;

import java.util.*;

import com.ibm.transform.oim.eacm.util.PokUtils;
import COM.ibm.eannounce.objects.*;
import COM.ibm.eannounce.abr.util.AttrComparator;

/**********************************************************************************
* PRCPTABRSTATUS class
*
* From "BH FS ABR Data Quality 20101220.doc"
* need CNTRYEFF and PRCPTCNTRYEFF and actions to create them
*
*need updated ve - DQVEPRCPT
*
PRCPTABRSTATUS_class=COM.ibm.eannounce.abr.sg.bh.PRCPTABRSTATUS
PRCPTABRSTATUS_enabled=true
PRCPTABRSTATUS_idler_class=A
PRCPTABRSTATUS_keepfile=true
PRCPTABRSTATUS_report_type=DGTYPE01
PRCPTABRSTATUS_vename=DQVEPRCPT
PRCPTABRSTATUS_CAT1=RPTCLASS.PRCPTABRSTATUS
PRCPTABRSTATUS_CAT2=
PRCPTABRSTATUS_CAT3=RPTSTATUS
*/
//$Log: PRCPTABRSTATUS.java,v $
//Revision 1.5  2013/11/04 14:27:55  liuweimi
//Change based on document: BH FS ABR Data Quality 20130904b.doc to fix Defect:  BH 185136
//
//Revision 1.4  2011/01/05 21:53:03  wendy
//Add more info to output for onevalidovertime
//
//Revision 1.3  2011/01/05 17:10:01  wendy
//pick up countrylist description from cntryeff grp
//
//Revision 1.2  2011/01/05 13:57:06  wendy
//Added CNTRYEFF and onevalidovertime
//
//Revision 1.1  2010/10/20 16:01:43  wendy
//Init for spec chg BH FS ABR Data Quality 20101012.doc
//
public class PRCPTABRSTATUS extends DQABRSTATUS
{

    /**********************************
	* Note the ABR is only called when
	* DATAQUALITY transitions from 'Draft to Ready for Review',
	*	'Change Request to Ready for Review' and from 'Ready for Review to Final'
	*
	1.00	PRCPT		Root									
Add			C	PRCPTCNTRYEFF-d		CNTRYEFF							
	2.00			CHRGCOMPPRCPT-u		CHRGCOMP							
	3.00			SVCMODCHRGCOMP-u		SVCMOD							
	4.00		A	SVCMODAVAIL-d		AVAIL							
	5.00	WHEN		"Planned Availability" (146)	=	A: AVAIL	AVAILTYPE						
Change	6.00			C: COUNTRYLIST	"IN aggregate G"	A: AVAIL	COUNTRYLIST		E	E	E	PRCPT CNTRYEFF COUNTRYLIST must be a subset of SVCMOD AVAIL COUNTRYLIST	{LD: PRCPT} {NDN: PRCPT} {LD: CNTRYEFF} {NDN: CNTRYEFF} must not include a country that is not in the {LD: SVCMOD} {LD: AVAIL} {LD: COUNTRYLIST}
	7.00	END	5.00										
	8.00	PRCPT		Root									
	9.00			PRCPTCVMSPEC-d		CVMSPEC							
	10.00			STATUS	<=	CVMSPEC	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than the {LD: CVMSPEC} {NDN: CVMSPEC} {LD: STATUS} {STATUS}
	11.00			CountOf	=>	1			E	E	E	PRCPT must have at least one CVMSPEC	{LD: PRCPT} {NDN: PRCPT} must reference at least one {LD: CVMSPEC}
	12.00	PRCPT		Root									
	13.00			SVCSEOPRCPT-u		SVCSEO							
	14.00		B	SVCSEOAVAIL-d		AVAIL							
15.00	WHEN		"Planned Availability" (146)	=	B: AVAIL	AVAILTYPE						
16.00			C: COUNTRYLIST	"IN aggregate G"	B: AVAIL	COUNTRYLIST		E	E	E	PRCPT CNTRYEFF COUNTRYLIST must be a subset of SVCSEO AVAIL COUNTRYLIST	{LD: PRCPT} {NDN: PRCPT}  {LD: CNTRYEFF} {NDN: CNTRYEFF} must not include a country that is not in the {LD: SVCSEO} {LD: AVAIL} {LD: COUNTRYLIST}
17.00			B: COUNTRYLIST	"IN aggregate G"	C: CNTRYEFF	COUNTRYLIST		E	E	E	SVCSEO AVAIL COUNTRYLIST must exist in PRCPT CNTRYEFF COUNTRYLIST	{LD: AVAIL} {NDN: B: AVAIL} must not include a country that is not in the {LD: PRCPT}  {LD: CNTRYEFF} {NDN: CNTRYEFF}  {LD: COUNTRYLIST}
18.00	END	15.00										
								
19.00	PRCPT		Root									
20.00				OneValidOverTime				E	E	E	From PRCPT, find the children CTRYEFF and get min{EFFECTIVEDATE) and max(ENDDATE). Then look at all children of PRCPT of type CVMSPEC. The relator PRCPTCVMSPEC has EFFECTIVEDATE and ENDATE. This date range must cover the CTRYEFF min/max dates	must have at least one valid {LD: PRCPTCVMSPEC) during the time that the {LD: PRCPT} {LD: CNTRYEFF) is valid.
21.00				ParentFrom	"PRCPTCNTRYEFF-d: CTRYEFF"	EFFECTIVEDATE						
22.00				ParentTo	"PRCPTCNTRYEFF-d: CTRYEFF"	ENDDATE						
23.00				ChildFrom	PRCPTCVMSPEC	EFFECTIVEDATE						
24.00				ChildTo	PRCPTCVMSPEC	ENDDATE						
30.00	PRCPT		Root									
31.00			PRCPTCVMSPEC								This relator has the date attributes that follow in column E and gets you to the enitytype in column G	
32.00			EFFECTIVEDATE	=>	CVMSPEC	EFFECTIVEDATE		W	E	E		the {LD: PRCPTCVMSPEC} {NDN: PRCPTCVMSPEC} {LD: EFFECTIVEDATE} {PRCPTCVMSPEC.EFFECTIVEDATE} must not be earlier than the {LD: CVMSPEC} {NDN: CVMSPEC} {LD: EFFECTIVEDATE} {CVMSPEC.EFFECTiVEDATE}
33.00			ENDDATE	<=	CVMSPEC	ENDDATE		W	E	E		the {LD: PRCPTCVMSPEC} {NDN: PRCPTCVMSPEC} {LD: ENDDATE} {PRCPTCVMSPEC.ENDDATE} must not be later than the {LD: CVMSPEC} {NDN: CVMSPEC} {LD: ENDDATE} {CVMSPEC.ENDDATE}
	*/
    protected void doDQChecking(EntityItem rootEntity, String statusFlag) throws Exception
    {
    	//1.00	PRCPT		Root									
    	//Add			C	PRCPTCNTRYEFF-d		CNTRYEFF							

      	//2.00			CHRGCOMPPRCPT-u		CHRGCOMP							
    	//3.00			SVCMODCHRGCOMP-u		SVCMOD							
    	Vector chrgcompVct = PokUtils.getAllLinkedEntities(rootEntity, "CHRGCOMPPRCPT", "CHRGCOMP");
    	Vector svcmodVct = PokUtils.getAllLinkedEntities(chrgcompVct, "SVCMODCHRGCOMP", "SVCMOD");
    	Vector svcmodAvailVct = PokUtils.getAllLinkedEntities(svcmodVct, "SVCMODAVAIL", "AVAIL");
    	Vector svcmodPlaAvailVctA = PokUtils.getEntitiesWithMatchedAttr(svcmodAvailVct, "AVAILTYPE", PLANNEDAVAIL);
       	//4.00		A	SVCMODAVAIL-d		AVAIL							
    	//5.00	WHEN		"Planned Availability" (146)	=	A: AVAIL	AVAILTYPE						
    	ArrayList plaAvailCtry = getCountriesAsList(svcmodPlaAvailVctA, CHECKLEVEL_E);
  	
    	addDebug(rootEntity.getKey()+" chrgcompVct "+chrgcompVct.size()+
    			" svcmodVct "+svcmodVct.size()+" svcmodAvailVct "+svcmodAvailVct.size()+
    			" svcmodPlaAvailVctA "+svcmodPlaAvailVctA.size()+" all svcmod plaAvailCtry "+plaAvailCtry);
    	
		//12.00	PRCPT		Root									
		//13.00			SVCSEOPRCPT-u		SVCSEO							
		//14.00		B	SVCSEOAVAIL-d		AVAIL		
		Vector svcseoVct = PokUtils.getAllLinkedEntities(rootEntity, "SVCSEOPRCPT", "SVCSEO");
    	Vector svcseoAvailVct = PokUtils.getAllLinkedEntities(svcseoVct, "SVCSEOAVAIL", "AVAIL");
    	Vector svcseoPlaAvailVctB = PokUtils.getEntitiesWithMatchedAttr(svcseoAvailVct, "AVAILTYPE", PLANNEDAVAIL);
    	ArrayList svcseoplaAvailCtry = getCountriesAsList(svcseoPlaAvailVctB, CHECKLEVEL_E);
    	addDebug(" svcseoVct "+svcseoVct.size()+
    			" svcseoAvailVct "+svcseoAvailVct.size()+
    			" svcseoPlaAvailVctB "+svcseoPlaAvailVctB.size());
    	addDebug("all svcseoPlaAvailVctB countrys "+svcseoplaAvailCtry); 
    	
		EntityGroup egCNTRYEFF = m_elist.getEntityGroup("CNTRYEFF");
     	addHeading(3,egCNTRYEFF.getLongDescription()+" and Planned Availability checks:");
     	
		ArrayList allCntryeffCtry = new ArrayList();
		
    	// check each CNTRYEFF countries
		for(int i=0; i<egCNTRYEFF.getEntityItemCount(); i++){
			ArrayList cntryeffCtry = new ArrayList();
			EntityItem cntryeffItem = egCNTRYEFF.getEntityItem(i);
			getCountriesAsList(cntryeffItem, cntryeffCtry,CHECKLEVEL_E);
			addDebug(cntryeffItem.getKey()+" cntryeffCtry "+cntryeffCtry);
			allCntryeffCtry.addAll(cntryeffCtry); // accumulate all cntryeff countrys
			
	    	String missingCtry = null;
			//5.00	WHEN		"Planned Availability" (146)	=	A: AVAIL	AVAILTYPE						
	    	if(svcmodPlaAvailVctA.size()>0){
	    		missingCtry = checkCtryMismatch(cntryeffItem, plaAvailCtry, CHECKLEVEL_E);
	    		if (missingCtry.length()>0){
	    			addDebug(cntryeffItem.getKey()+" COUNTRYLIST had extra ["+missingCtry+"] from svcmod A:AVAIL");
	    	    	//6.00			C: COUNTRYLIST	"IN	aggregate G"	A: AVAIL	COUNTRYLIST		E	E	E	
	    	    	//PRCPT CNTRYEFF COUNTRYLIST must be a subset of SVCMOD AVAIL COUNTRYLIST	
	    	    	//{LD: PRCPT} {NDN: PRCPT} {LD: CNTRYEFF} {NDN: CNTRYEFF} must not include a country that is not in the {LD: SVCMOD} {LD: AVAIL} {LD: COUNTRYLIST}
	    			//INCLUDE_ERR2 = {0} {1} must not include a Country that is not in the {2} {3}. Extra countries are: {4}
	    			args[0]="";
	    			args[1]=getLD_NDN(cntryeffItem);
	    			args[2]=m_elist.getEntityGroup("SVCMOD").getLongDescription()+" "+
	    				m_elist.getEntityGroup("AVAIL").getLongDescription();
	    			args[3]=PokUtils.getAttributeDescription(egCNTRYEFF, "COUNTRYLIST", "COUNTRYLIST");
	    			args[4]=missingCtry;
	    			createMessage(CHECKLEVEL_E,"INCLUDE_ERR2",args);
	    		}
	    	}
	    	//7.00	END	5.00
	    	//15.00	WHEN		"Planned Availability" (146)	=	B: AVAIL	AVAILTYPE						
	    	if(svcseoPlaAvailVctB.size()>0){
	    		missingCtry = checkCtryMismatch(cntryeffItem, svcseoplaAvailCtry, CHECKLEVEL_E);
	    		if (missingCtry.length()>0){
	    			addDebug(cntryeffItem.getKey()+" COUNTRYLIST had extra ["+missingCtry+"] from svcseo B:AVAIL");
	    	    	//16.00			C: COUNTRYLIST	"IN aggregate G"	B: AVAIL	COUNTRYLIST		E	E	E	PRCPT CNTRYEFF COUNTRYLIST must be a subset of SVCSEO AVAIL COUNTRYLIST	{LD: PRCPT} {NDN: PRCPT}  {LD: CNTRYEFF} {NDN: CNTRYEFF} must not include a country that is not in the {LD: SVCSEO} {LD: AVAIL} {LD: COUNTRYLIST}
	    			//INCLUDE_ERR2 = {0} {1} must not include a Country that is not in the {2} {3}. Extra countries are: {4}
	    			args[0]="";
	    			args[1]=getLD_NDN(cntryeffItem);
	    			args[2]=m_elist.getEntityGroup("SVCSEO").getLongDescription()+" "+
	    				m_elist.getEntityGroup("AVAIL").getLongDescription();
	    			args[3]=PokUtils.getAttributeDescription(egCNTRYEFF, "COUNTRYLIST", "COUNTRYLIST");
	    			args[4]=missingCtry;
	    			createMessage(CHECKLEVEL_E,"INCLUDE_ERR2",args);
	    		}
	    	}
		}
    	
    	addDebug(rootEntity.getKey()+" allCntryeffCtry "+allCntryeffCtry);
    
	 	addHeading(3,m_elist.getEntityGroup("CVMSPEC").getLongDescription()+" checks:");
		//8.00	PRCPT		Root									
		//9.00			PRCPTCVMSPEC-d		CVMSPEC							
	   	int cnt = getCount("PRCPTCVMSPEC");
		EntityGroup cvmspecGrp = m_elist.getEntityGroup("CVMSPEC");
		//11.00			CountOf	=>	1	E E E	PRCPT must have at least one CVMSPEC	{LD: PRCPT} {NDN: PRCPT} must reference at least one {LD: CVMSPEC}
		if(cnt==0){
			//MINIMUM_ERR =  must have at least one {0}
			args[0] = cvmspecGrp.getLongDescription();
			createMessage(CHECKLEVEL_E,"MINIMUM_ERR",args);
		}
    	
		for (int i=0; i<cvmspecGrp.getEntityItemCount(); i++){
			EntityItem cvmspecitem = cvmspecGrp.getEntityItem(i);
			addDebug("doDQChecking: "+cvmspecitem.getKey());
			//10.00			STATUS	<=	CVMSPEC	DATAQUALITY		E	E	E		{LD: STATUS} can not be higher than the {LD: CVMSPEC} {NDN: CVMSPEC} {LD: STATUS} {STATUS}
			checkStatusVsDQ(cvmspecitem, "STATUS", rootEntity,CHECKLEVEL_E);
		}
		
	 	addHeading(3,m_elist.getEntityGroup("SVCSEO").getLongDescription()+" Planned Availability checks:");
		
   		//12.00	PRCPT		Root									
    	//13.00			SVCSEOPRCPT-u		SVCSEO							
    	//14.00		B	SVCSEOAVAIL-d		AVAIL							
		for (int i=0; i<svcseoVct.size(); i++){
			EntityItem svcseoitem = (EntityItem)svcseoVct.elementAt(i);
	    	Vector availVct = PokUtils.getAllLinkedEntities(svcseoitem, "SVCSEOAVAIL", "AVAIL");
	    	Vector plaAvailVctB = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", PLANNEDAVAIL);
	    	addDebug(" svcseoitem "+svcseoitem.getKey()+" availVct "+availVct.size()+" plaAvailVctB "+plaAvailVctB.size());
	    	for (int i2=0; i2<plaAvailVctB.size(); i2++){
	    		EntityItem availitem = (EntityItem)plaAvailVctB.elementAt(i2);
	    		String missingCtry = checkCtryMismatch(availitem, allCntryeffCtry, CHECKLEVEL_E);
	    		if (missingCtry.length()>0){
	    			addDebug(" "+availitem.getKey()+" COUNTRYLIST had extra ["+missingCtry+"] not in PRCPT CNTRYEFF COUNTRYLIST");
	    	    	//17.00			B: COUNTRYLIST	"IN aggregate G"	C: CNTRYEFF	COUNTRYLIST		E	E	E	SVCSEO AVAIL COUNTRYLIST must exist in PRCPT CNTRYEFF COUNTRYLIST	{LD: AVAIL} {NDN: B: AVAIL} must not include a country that is not in the {LD: PRCPT}  {LD: CNTRYEFF} {NDN: CNTRYEFF}  {LD: COUNTRYLIST}

	    			//INCLUDE_ERR2 = {0} {1} must not include a Country that is not in the {2} {3}. Extra countries are: {4}
	    			args[0]=getLD_NDN(svcseoitem)+" "+getLD_NDN(availitem);
	    			args[1]=PokUtils.getAttributeDescription(availitem.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
	    			args[2]=rootEntity.getEntityGroup().getLongDescription()+" "+egCNTRYEFF.getLongDescription();
	    			args[3]=args[1];
	    			args[4]=missingCtry;
	    			createMessage(CHECKLEVEL_E,"INCLUDE_ERR2",args);
	    		}
			}
			availVct.clear();
			plaAvailVctB.clear();
		}
    	//18.00	END	15.00
    	
		//19.00	PRCPT		Root									
		//20.00				OneValidOverTime				E	E	E	
		oneValidOverTime(rootEntity);
		
		int checklvl = getCheck_W_E_E(statusFlag);
		//30.00	PRCPT		Root									
		//31.00			PRCPTCVMSPEC								This relator has the date attributes that follow in column E and gets you to the enitytype in column G	
		EntityGroup prcptcvmspecGrp = m_elist.getEntityGroup("PRCPTCVMSPEC");
		
	 	addHeading(3,prcptcvmspecGrp.getLongDescription()+" and "+
	 			m_elist.getEntityGroup("CVMSPEC").getLongDescription()+" checks:");
	 	
		for (int i=0; i<prcptcvmspecGrp.getEntityItemCount(); i++){
			EntityItem prcptcvmspec = prcptcvmspecGrp.getEntityItem(i);
			EntityItem  cvmspecitem= getDownLinkEntityItem(prcptcvmspec, "CVMSPEC");
			String prcptcvmspecEffdate = PokUtils.getAttributeValue(prcptcvmspec,  "EFFECTIVEDATE",", ", null, false);
			String cvmspecEffdate = PokUtils.getAttributeValue(cvmspecitem,  "EFFECTIVEDATE",", ", null, false);
			String prcptcvmspecEnddate = PokUtils.getAttributeValue(prcptcvmspec,  "ENDDATE",", ", FOREVER_DATE, false);
			String cvmspecEnddate = PokUtils.getAttributeValue(cvmspecitem,  "ENDDATE",", ", FOREVER_DATE, false);
			addDebug("doDQChecking: "+prcptcvmspec.getKey()+" prcptcvmspecEffdate "+prcptcvmspecEffdate+
					" prcptcvmspecEnddate "+prcptcvmspecEnddate+" "+cvmspecitem.getKey()+" cvmspecEffdate "+
					cvmspecEffdate+" cvmspecEnddate "+cvmspecEnddate);
			if(prcptcvmspecEffdate!=null && cvmspecEffdate!=null){ //bad data, should have exist rule!
				//32.00			EFFECTIVEDATE	=>	CVMSPEC	EFFECTIVEDATE		W	E	E		
				boolean isok = checkDates(prcptcvmspecEffdate, cvmspecEffdate, DATE_GR_EQ);	//date1=>date2	
				if(!isok){
					//CANNOT_BE_EARLIER_ERR2 = {0} {1} must not be earlier than the {2} {3}
					//the {LD: PRCPTCVMSPEC} {NDN: PRCPTCVMSPEC} {LD: EFFECTIVEDATE} {PRCPTCVMSPEC.EFFECTIVEDATE} must not be earlier than the {LD: CVMSPEC} {NDN: CVMSPEC} {LD: EFFECTIVEDATE} {CVMSPEC.EFFECTiVEDATE}
					args[0]=getLD_NDN(prcptcvmspec);
					args[1]=this.getLD_Value(prcptcvmspec, "EFFECTIVEDATE");
					args[2]=getLD_NDN(cvmspecitem);
					args[3]=getLD_Value(cvmspecitem, "EFFECTIVEDATE");
					createMessage(checklvl,"CANNOT_BE_EARLIER_ERR2",args);
				}
			}

			//33.00			ENDDATE	<=	CVMSPEC	ENDDATE		W	E	E		
			boolean isok = checkDates(prcptcvmspecEnddate, cvmspecEnddate, DATE_LT_EQ);	//date1<=date2
			if(!isok){
				//CANNOT_BE_LATER_ERR = {0} {1} must not be later than the {2} {3}
				//the {LD: PRCPTCVMSPEC} {NDN: PRCPTCVMSPEC} {LD: ENDDATE} {PRCPTCVMSPEC.ENDDATE} must not be later than the {LD: CVMSPEC} {NDN: CVMSPEC} {LD: ENDDATE} {CVMSPEC.ENDDATE}
				args[0]=getLD_NDN(prcptcvmspec);
				args[1]=this.getLD_Value(prcptcvmspec, "ENDDATE");
				args[2]=getLD_NDN(cvmspecitem);
				args[3]=getLD_Value(cvmspecitem, "ENDDATE");
				createMessage(checklvl,"CANNOT_BE_LATER_ERR",args);
			}
		}
	
		// release memory
		allCntryeffCtry.clear();
		chrgcompVct.clear();
    	svcmodVct.clear();
    	svcmodAvailVct.clear();
    	svcmodPlaAvailVctA.clear();
    	svcseoVct.clear();
    	svcseoAvailVct.clear();
    	svcseoPlaAvailVctB.clear();
    	plaAvailCtry.clear();
    	svcseoplaAvailCtry.clear();
    }
    
	/**
	 * 
		20.00				OneValidOverTime				E	E	E	
		From PRCPT, find the children CNTRYEFF and get min{EFFECTIVEDATE) and max(ENDDATE). 
		Then look at all children of PRCPT of type CVMSPEC. The relator PRCPTCVMSPEC has EFFECTIVEDATE and ENDATE. 
		This date range must cover the CNTRYEFF min/max dates	
		must have at least one valid {LD: PRCPTCVMSPEC) during the time that the {LD: PRCPT} {LD: CNTRYEFF) is valid.
		
		21.00				ParentFrom	"PRCPTCNTRYEFF-d: CNTRYEFF"	EFFECTIVEDATE						
		22.00				ParentTo	"PRCPTCNTRYEFF-d: CNTRYEFF"	ENDDATE						
		23.00				ChildFrom	PRCPTCVMSPEC	EFFECTIVEDATE						
		24.00				ChildTo	PRCPTCVMSPEC	ENDDATE	
	 * @param root
	 */
    private void oneValidOverTime(EntityItem root){
    	StringBuffer chksSb = new StringBuffer(root.getEntityGroup().getLongDescription());
    	chksSb.append(" "+m_elist.getEntityGroup("CNTRYEFF").getLongDescription());
    	chksSb.append(" and "+m_elist.getEntityGroup("PRCPTCVMSPEC").getLongDescription());
    	//get entities with from dates
    	Vector childVct = this.getDownLinkEntityItems(root, "PRCPTCVMSPEC");
    	Vector parentVct = PokUtils.getAllLinkedEntities(root, "PRCPTCNTRYEFF", "CNTRYEFF");

    	addHeading(3,chksSb.toString()+" validity checks:");
    	
    	addDebug("oneValidOverTime entered for "+root.getKey()+" PRCPTCVMSPEC-childVct.cnt "+childVct.size()+
    			" CNTRYEFF-parentVct.cnt "+parentVct.size());
    	
    	if (parentVct.size()==0){// no CNTRYEFF, nothing to check
    		childVct.clear();
    		return;
    	}

    	AttrComparator attrcomp = new AttrComparator("EFFECTIVEDATE");
    	// sort children by efffrom
    	Collections.sort(childVct, attrcomp);
    	// sort parent by efffrom
    	Collections.sort(parentVct, attrcomp);

    	EntityItem parent = (EntityItem)parentVct.firstElement();
    	String parentEffFrom = PokUtils.getAttributeValue(parent, "EFFECTIVEDATE", "", EPOCH_DATE, false);
    	addDebug("oneValidOverTime first parent "+parent.getKey()+" parentEffFrom "+parentEffFrom);
    	if (childVct.size()==0){// there is no RE so no error for now.
    		//	must have at least one valid {LD: PRCPTCVMSPEC) during the time that the {LD: PRCPT} {LD: CNTRYEFF) is valid.
    		//INVALID_CHILD_ERR= must have at least one valid {0} during the time that the {1} is valid.
    //		args[0]= m_elist.getEntityGroup("PRCPTCVMSPEC").getLongDescription();
    //		args[1]= root.getEntityGroup().getLongDescription()+" "+parent.getEntityGroup().getLongDescription();
    //		createMessage(CHECKLEVEL_E,"INVALID_CHILD_ERR",args);
    		parentVct.clear();
    		return;
    	}

    	// look at first one
    	EntityItem child = (EntityItem)childVct.firstElement();
    	String childEffFrom = PokUtils.getAttributeValue(child, "EFFECTIVEDATE", "", EPOCH_DATE, false);// has EXIST rule
    	String prevEffTo = PokUtils.getAttributeValue(child, "ENDDATE", "", FOREVER_DATE, false);
    	addDebug("oneValidOverTime first child "+child.getKey()+" childEffFrom "+childEffFrom+" prevEffTo "+prevEffTo);
    	if(childEffFrom.compareTo(parentEffFrom)>0){
    		//	must have at least one valid {LD: PRCPTCVMSPEC) during the time that the {LD: PRCPT} {LD: CNTRYEFF) is valid.
    		//INVALID_CHILD_ERR2= must have at least one valid {0} during the time that the {1} {2} is valid ({3}-{4}).
    		args[0]= child.getEntityGroup().getLongDescription();
    		args[1]= root.getEntityGroup().getLongDescription();
    		args[2]=parent.getEntityGroup().getLongDescription();
    		args[3]=parentEffFrom;
    		args[4]=PokUtils.getAttributeValue((EntityItem)parentVct.lastElement(), "ENDDATE", "", FOREVER_DATE, false);
    		createMessage(CHECKLEVEL_E,"INVALID_CHILD_ERR2",args);
    		
    		//CANNOT_BE_LATER_ERR = {0} {1} must not be later than the {2} {3}
    		args[0]= child.getEntityGroup().getLongDescription();
    		args[1] =PokUtils.getAttributeDescription(child.getEntityGroup(),"EFFECTIVEDATE", "EFFECTIVEDATE")+
    			" ("+childEffFrom+")";
    		args[2]= root.getEntityGroup().getLongDescription()+" "+parent.getEntityGroup().getLongDescription();
    		args[3] = PokUtils.getAttributeDescription(parent.getEntityGroup(), 
    				"EFFECTIVEDATE", "EFFECTIVEDATE")+" ("+parentEffFrom+")";
    		addResourceMsg("CANNOT_BE_LATER_ERR",args);
    		childVct.clear();
    		parentVct.clear();
    		return;
    	}
    	
    	AttrComparator endattrcomp = new AttrComparator("ENDDATE");
    	// sort children by effto
    	Collections.sort(childVct, endattrcomp);
    	// sort parent by effto
    	Collections.sort(parentVct, endattrcomp);
    	// look at last one
    	child = (EntityItem)childVct.lastElement();
    	parent = (EntityItem)parentVct.lastElement();
    	String childEffTo = PokUtils.getAttributeValue(child, "ENDDATE", "", FOREVER_DATE, false);
    	String parentEffTo = PokUtils.getAttributeValue(parent, "ENDDATE", "", FOREVER_DATE, false); 
    	addDebug("oneValidOverTime last child "+child.getKey()+" childEffTo "+childEffTo);
    	addDebug("oneValidOverTime last parent "+parent.getKey()+" parentEffTo "+parentEffTo);
    	if(childEffTo.compareTo(parentEffTo)<0){
    		//INVALID_CHILD_ERR2= must have at least one valid {0} during the time that the {1} {2} is valid ({3}-{4}).
    		args[0]= child.getEntityGroup().getLongDescription();
    		args[1]= root.getEntityGroup().getLongDescription();
    		args[2]=parent.getEntityGroup().getLongDescription();
    		args[3]=PokUtils.getAttributeValue((EntityItem)parentVct.firstElement(), "EFFECTIVEDATE", "", EPOCH_DATE, false);
    		args[4]=parentEffTo;
    		createMessage(CHECKLEVEL_E,"INVALID_CHILD_ERR2",args);
    		
    		//CANNOT_BE_EARLIER_ERR2 = {0} {1} must not be earlier than the {2} {3} 
       		args[0]= child.getEntityGroup().getLongDescription();
    		args[1] = PokUtils.getAttributeDescription(child.getEntityGroup(), 
    				"ENDDATE", "ENDDATE")+" ("+childEffTo+")";
    		args[2]= root.getEntityGroup().getLongDescription()+" "+parent.getEntityGroup().getLongDescription();
    		args[3] = PokUtils.getAttributeDescription(parent.getEntityGroup(), 
    				"EFFECTIVEDATE", "EFFECTIVEDATE")+" ("+parentEffTo+")";
    		addResourceMsg("CANNOT_BE_EARLIER_ERR2",args);
    		childVct.clear();
    		parentVct.clear();
    		return;
    	}

    	// sort children by efffrom again
    	Collections.sort(childVct, attrcomp);
    	// sort parent by efffrom
    	Collections.sort(parentVct, attrcomp);
    	
    	// look for gaps
    	for (int i=1; i<childVct.size(); i++){
    		child = (EntityItem)childVct.elementAt(i);
    		String nextEffFrom = PokUtils.getAttributeValue(child, "EFFECTIVEDATE", "", EPOCH_DATE, false); // has EXIST rule
    		String nextEffTo = PokUtils.getAttributeValue(child, "ENDDATE", "", FOREVER_DATE, false);
    		addDebug("oneValidOverTime gaps "+child.getKey()+" prevEffTo "+prevEffTo+" nextEffFrom "+
    				nextEffFrom+" nextEffTo "+nextEffTo);
    		if(nextEffFrom.compareTo(prevEffTo)>0){
    			//INVALID_CHILD_ERR= must have at least one valid {0} during the time that the {1} is valid.
    			args[0]= child.getEntityGroup().getLongDescription();
    			args[1]= root.getEntityGroup().getLongDescription()+" "+parent.getEntityGroup().getLongDescription();
    			createMessage(CHECKLEVEL_E,"INVALID_CHILD_ERR",args);
        		//GAPS_ERR = {0} has gaps in time between {1} and {2}
        		args[0]= child.getEntityGroup().getLongDescription();
        		args[1]= prevEffTo;
        		args[2]= nextEffFrom;

        		addResourceMsg("GAPS_ERR",args);
    			break;
    		}
    		prevEffTo= nextEffTo;
    	}

    	childVct.clear();
    	parentVct.clear();
    }

    /**********************************
     * complete abr processing after status moved to final; (status was r4r)
150.02	R1.0	PRCPT									
150.04	R1.0	IF		"CHRGCOMPPRCPT-u: SVCMODCHRGCOMP-u"	CHRGCOMP	STATUS	=	"Ready for Review" (0040) | "Final" (0020)			
150.06	R1.0	AND			SVCMOD	STATUS	=	"Ready for Review" (0040)			
150.08	R1.0	AND			SVCMOD	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
150.10	R1.0	SET			SVCMOD				ADSABRSTATUS	&ADSFEEDRFR	
150.12	R1.0	ELSE	150.04								
150.14	R1.0	IF			SVCMOD	STATUS	=	"Final" (0020)			
150.16	R1.0	AND			CHRGCOMP	STATUS	=	"Final" (0020)			
150.18	R1.0	AND			PRCPT	STATUS	=	"Final" (0020)			
150.20	R1.0	SET			SVCMOD				ADSABRSTATUS		&ADSFEED
150.22	R1.0	END	150.14								
150.24	R1.0	END	150.04								
						
     */
    protected void completeNowFinalProcessing() throws
    java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException,
    COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    {
    	EntityGroup chrgcompGrp = m_elist.getEntityGroup("CHRGCOMP");
    	for (int i=0; i<chrgcompGrp.getEntityItemCount(); i++){
    		EntityItem chrgcomp = chrgcompGrp.getEntityItem(i);
    		addDebug("completeNowFinalProcessing: "+chrgcomp.getKey());
    		//150.04	R1.0	IF		"CHRGCOMPPRCPT-u: CHRGCOMPSVCMOD-u"	CHRGCOMP	STATUS	=	"Ready for Review" (0040) | "Final" (0020)			
    		if (this.statusIsRFRorFinal(chrgcomp)){
    			Vector svcmodVct = PokUtils.getAllLinkedEntities(chrgcomp, "SVCMODCHRGCOMP", "SVCMOD");
    			for (int s=0; s<svcmodVct.size(); s++)	{
    				EntityItem svcmod = (EntityItem)svcmodVct.elementAt(s);
//    	        	String lifecycle = PokUtils.getAttributeFlagValue(svcmod, "LIFECYCLE");
//    	    	  	addDebug("completeNowFinalProcessing: "+svcmod.getKey()+" lifecycle "+lifecycle);
//    	    		if (lifecycle==null || lifecycle.length()==0){ 
//    	        		lifecycle = LIFECYCLE_Plan;
//    	        	}
    	    		//150.14	R1.0	IF			SVCMOD	STATUS	=	"Final" (0020)			
    	    		if (statusIsFinal(svcmod)){
    	    			//150.16	R1.0	AND			CHRGCOMP	STATUS	=	"Final" (0020)		
    	    			if(statusIsFinal(chrgcomp)){
    	    				//150.18	R1.0	AND			PRCPT	STATUS	=	"Final" (0020)			
    	    	    		//150.20	R1.0	SET			SVCMOD				ADSABRSTATUS		&ADSFEED
    	    				setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getQueuedValueForItem(svcmod,"ADSABRSTATUS"),svcmod);
    	    			}
    	    			//150.22	R1.0	END	150.14	
    				}else{
    			   		//150.06	R1.0	AND			SVCMOD	STATUS	=	"Ready for Review" (0040)			
    		    		//20130904 Delete 150.08	R1.0	AND			SVCMOD	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)	//delete
    					doRFR_ADSXML(svcmod);
//        		    	if (this.statusIsRFR(svcmod) && (LIFECYCLE_Plan.equals(lifecycle) ||  // first time moving to RFR
//        		    			LIFECYCLE_Develop.equals(lifecycle))){ // been RFR before
//            	    		//150.10	R1.0	SET			SVCMOD				ADSABRSTATUS	&ADSFEEDRFR	        		    			
//        					setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getRFRQueuedValueForItem(svcmod,"ADSABRSTATUS"),svcmod);
//        				}
    				}
    			}
    			svcmodVct.clear();
    		}
    	}
    }
    /**********************************
     * complete abr processing after status moved to readyForReview; (status was chgreq)
 	* B.	Status changed to Ready for Review	
150.02	R1.0	PRCPT									
150.04	R1.0	IF		"CHRGCOMPPRCPT-u: SVCMODCHRGCOMP-u"	CHRGCOMP	STATUS	=	"Ready for Review" (0040) | "Final" (0020)			
150.06	R1.0	AND			SVCMOD	STATUS	=	"Ready for Review" (0040)			
150.08	R1.0	AND			SVCMOD	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
150.10	R1.0	SET			SVCMOD				ADSABRSTATUS	&ADSFEEDRFR	
150.12	R1.0	ELSE	150.04								
150.14	R1.0	IF			SVCMOD	STATUS	=	"Final" (0020)			
150.16	R1.0	AND			CHRGCOMP	STATUS	=	"Final" (0020)			
150.18	R1.0	AND			PRCPT	STATUS	=	"Final" (0020)			
150.20	R1.0	SET			SVCMOD				ADSABRSTATUS		&ADSFEED
150.22	R1.0	END	150.14								
150.24	R1.0	END	150.04								
     */
    protected void completeNowR4RProcessing() throws
    java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException,
    COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    {
    	EntityGroup chrgcompGrp = m_elist.getEntityGroup("CHRGCOMP");
    	for (int i=0; i<chrgcompGrp.getEntityItemCount(); i++){
    		EntityItem chrgcomp = chrgcompGrp.getEntityItem(i);
    		addDebug("completeNowR4RProcessing: "+chrgcompGrp.getKey());
    		//150.04	R1.0	IF		"CHRGCOMPPRCPT-u:CHRGCOMPSVCMOD-u"	CHRGCOMP	STATUS	=	"Ready for Review" (0040) | "Final" (0020)			
    		if (this.statusIsRFRorFinal(chrgcomp)){
    			Vector svcmodVct = PokUtils.getAllLinkedEntities(chrgcomp, "SVCMODCHRGCOMP", "SVCMOD");
    			for (int s=0; s<svcmodVct.size(); s++)	{
    				EntityItem svcmod = (EntityItem)svcmodVct.elementAt(s);
//    		    	String lifecycle = PokUtils.getAttributeFlagValue(svcmod, "LIFECYCLE");
//    		    	addDebug("completeNowR4RProcessing: "+svcmod.getKey()+" lifecycle "+lifecycle);
//    		    	if (lifecycle==null || lifecycle.length()==0){ 
//    		    		lifecycle = LIFECYCLE_Plan;
//    		    	}
    	    		//150.06	R1.0	AND			SVCMOD	STATUS	=	"Ready for Review" (0040)			
    	    		//150.08	R1.0	AND			SVCMOD	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
    		    	if (this.statusIsRFR(svcmod)){
//    		    			&& (LIFECYCLE_Plan.equals(lifecycle) ||  // first time moving to RFR
//    		    			LIFECYCLE_Develop.equals(lifecycle))){ // been RFR before
        	    		//150.10	R1.0	SET			SVCMOD				ADSABRSTATUS	&ADSFEEDRFR	
    					setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getRFRQueuedValueForItem(svcmod,"ADSABRSTATUS"),svcmod);
    				}
    			}
    			svcmodVct.clear();
    		}
    	}
    }
	
    /***********************************************
    *  Get ABR description
    *
    *@return java.lang.String
    */
    public String getDescription()
    {
        String desc =  "PRCPT ABR.";

        return desc;
    }

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getABRVersion()
    {
        return "$Revision: 1.5 $";
    }
}
