//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2010  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package COM.ibm.eannounce.abr.sg.bh;

import java.sql.SQLException;
import java.util.*;

import com.ibm.transform.oim.eacm.util.PokUtils;

import COM.ibm.eannounce.abr.util.AttrComparator;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.MiddlewareException;

/**********************************************************************************
* SVCSEOABRSTATUS class
*
*From "BH FS ABR Data Quality 20101103.xls"
*need meta for 
*	48.00				ParentFrom	SVCSEOPRCPT	EFFECTIVEDATE				
	49.00				ParentTo	SVCSEOPRCPT	ENDDATE				
	50.00				ChildFrom	"SVCSEOPRCPT-d:PRCPTCNTRYEFF-d:CNTRYEFF"	EFFECTIVEDATE				
	51.00				ChildTo	"SVCSEOPRCPT-d:PRCPTCNTRYEFF-d:CNTRYEFF"	ENDDATE
* From "BH FS ABR Data Quality 20100923.doc"
*
*need new ve - DQVESVCSEO
*This does not have an ABR attribute 
*
SVCSEOABRSTATUS_class=COM.ibm.eannounce.abr.sg.bh.SVCSEOABRSTATUS
SVCSEOABRSTATUS_enabled=true
SVCSEOABRSTATUS_idler_class=A
SVCSEOABRSTATUS_keepfile=true
SVCSEOABRSTATUS_report_type=DGTYPE01
SVCSEOABRSTATUS_vename=DQVESVCSEO
SVCSEOABRSTATUS_CAT1=RPTCLASS.SVCSEOABRSTATUS
SVCSEOABRSTATUS_CAT2=
SVCSEOABRSTATUS_CAT3=RPTSTATUS
*/
//$Log: SVCSEOABRSTATUS.java,v $
//Revision 1.4  2013/11/04 14:27:55  liuweimi
//Change based on document: BH FS ABR Data Quality 20130904b.doc to fix Defect:  BH 185136
//
//Revision 1.3  2012/11/05 13:37:32  liuweimi
// IN3010935 & RCQ00225128-RQ based on BH FS ABR Data Quality Checkks 20121030.xls &  BH FS ABR Data Quality Sets 20121030.xls
//
//Revision 1.2  2011/01/28 19:11:56  wendy
//correct info msg for enddate and avail
//
//Revision 1.1  2011/01/06 13:52:06  wendy
//BH FS ABR Data Quality 20110105 updates
//
public class SVCSEOABRSTATUS extends DQABRSTATUS
{
	private String SVCSEOFO = null;
	private String SVCSEOPA = null;
	private String SVCSEOAD = null;
	private String SVCMODFO = null;
	private String SVCMODPA = null;
	private String SVCMODAD = null;
    private Vector plaAvailVctY = null;
    private Vector loAvailVctY = null;
    
	public void dereference(){
    	SVCSEOFO = null;
    	SVCSEOPA = null;
    	SVCSEOAD = null;
    	SVCMODFO = null;
    	SVCMODPA = null;
    	SVCMODAD = null;
    	if(plaAvailVctY!=null){
    		plaAvailVctY.clear();
    		plaAvailVctY = null;
    	}
    	if(loAvailVctY!=null){
    		loAvailVctY.clear();
    		loAvailVctY = null;
    	}
    	super.dereference();
	}
    /**********************************
	* Note the ABR is only called when
	* DATAQUALITY transitions from 'Draft to Ready for Review',
	*	'Change Request to Ready for Review' and from 'Ready for Review to Final'
	*
1.00	SVCSEO		Root									
2.00		X	SVCSEOPRCPT-d		PRCPT							
3.00			CountOf	=>	1			RE	RE	RE		must have at least one {LD: PRCPT}
4.00			DATAQUALITY	<=	PRCPT	STATUS		E	E	E		{LD: STATUS} can not be higher than the {LD: PRCPT} {NDN: PRCPT} {LD: STATUS} {STATUS}
5.00	SVCSEO		Root									
6.00		Y	SVCSEOAVAIL-d		AVAIL							
7.00	WHEN		"First Order"	=	Y: AVAIL	AVAILTYPE						
8.00			SVCSEOFO	SetTo	Y: AVAIL	MIN(EFFECTIVEDATE)					SVCSEO AVAIL "First Order" EFFECTIVEDATE	
9.00	END	7.00										
10.00												
11.00	WHEN		"Planned Availability" (146)	=	Y: AVAIL	AVAILTYPE						
12.00			CountOf	=>	1			RW	RW	RE		must have at least one "Planned Availability"
12.80			A SVCSEOPRCPT-d: PRCPTCNTRYEFF-d									
13.00			Y: COUNTRYLIST	"IN aggregate G"	CNTRYEFF	COUNTRYLIST		E	E	E	SVCSEO AVAIL COUNTRYLIST must be a subset of PRCPT COUNTRYLIST	{LD: AVAIL} {NDN: Y: AVAIL} must not include a country that is not in the {LD: PRCPT} {NDN: X: PRCPT} {LD: COUNTRYLIST}
14.00			SVCSEOPA	SetTo	Y: AVAIL	MIN(EFFECTIVEDATE)					SVCSEO AVAIL "Planned Availability" EFFECTIVEDATE - Choose the earliest date if there are multiple	
15.00	ANNOUNCEMENT		Y: + AVAILANNA-d 		W: ANNOUNCEMENT						SVCSEO ANNOUNCEMENT Planned Availability	
16.00	WHEN		"RFA" (RFA)	<>	Y: AVAIL	AVAILANNTYPE						
17.00			CountOf	=	0			E	E	E	Only AVAIL.AVAILANNTYPE = "RFA" can be in an ANNOUNCEMENT	{LD: AVAIL} {NDN: Y:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
18.00	END	16.00										
19.00	IF		ANNTYPE	<>	New (19)							
20.00			CountOf	=	0			E	E	E		{LD: AVAIL} {NDN: Y:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
21.00	END	19.00										
22.00	IF		ANNTYPE	=	New (19)							
23.00			SVCSEOAD	SetTo	W: ANNOUNCEMENT	MIN(ANNDATE)					SVCSEO ANNOUNCEMENT NEW ANNDATE	
24.00	END	22.00										
25.00	END	11.00										
26.00	SVCSEO		Root									
27.00			SVCMODSVCSEO-u		SVCMOD							
28.00		Z	SVCMODAVAIL-d		AVAIL							
29.00	WHEN		"First Order"	=	Z: AVAIL	AVAILTYPE						
30.00			SVCMODFO	SetTo	Z: AVAIL	MIN(EFFECTIVEDATE)					SVCMOD AVAIL "First Order" EFFECTIVEDATE	
31.00	END	29.00										
32.00	WHEN		"Planned Availability" (146)	=	Z: AVAIL	AVAILTYPE						
33.00			CountOf	=>	1			RW	RW	RE		must have at least one "Planned Availability"
34.00			Z: COUNTRYLIST	Contains	A: CNTRYEFF	COUNTRYLIST					PRCPT COUNTRYLIST must be a subset of SVCMOD AVAIL COUNTRYLIST	{LD: PRCPT} {NDN: X: PRCPT} {LD: COUNTRYLIST}  must not include a country that is not in the {LD: AVAIL} {NDN: Z: AVAIL}
35.00			SVCMODPA	SetTo	Z: AVAIL	MIN(EFFECTIVEDATE)	E E E		SVCMOD AVAIL "Planned Availability" EFFECTIVEDATE	
36.00	ANNOUNCEMENT		Z: + AVAILANNA-d 								SVCMOD ANNOUNCEMENT Planned Availability	
37.00			SVCMODAD	SetTo	Z: ANNOUNCEMENT	MIN(ANNDATE)					SVCMOD ANNOUNCEMENT NEW ANNDATE	
38.00	END	32.00										
39.00			SVCSEOFIRSTORDER	FirstValue		SVCSEOFO					Derives SVCSEO First Order	
40.00						SVCSEOAD						
41.00						SVCSEOPA						
42.00			SVCMODFIRSTORDER	FirstValue		SVCMODFO					Derves SVCMOD First Order	
43.00						SVCMODAD						
44.00						SVCMODPA						
44.20	IF		NotNull(SVCSEOFIRSTORDER)									
44.40	AND		NotNull(SVCMODFIRSTORDER)									
45.00			SVCMODFIRSTORDER	<=	SVCSEOFIRSTORDER			RE	RE	RE	First Order SVCMOD can not be later than SVCSEO First Order	{LD: SVCMOD} First Order date must not be later than the {LD: SVCSEO} {NDN: SVCSEO)
45.20	END	44.40										

46.00	SVCSEO		Root									
47.00				OneValidOverTime			Yes	E	E	E	For the SVCSEO, each instance of the child AVAIL where AVAILTYPE = "Planned Availability" check the PRCPT'S child CNTRYEFF EFFECTIVEDATE by Country. The AVAILTYPE = "Last Order" EFFECTIVEDATE is compared to ENDDATE.	must have at lease one valid {LD: PRCPT) {LD: CNTRYEFF} during the time that the {LD: SVCSEO} is valid by Country for {LD: AVAIL} Planned Availability and Last Order.
48.00				ParentFrom	SVCSEO-d. AVAIL where AVAILTYPE = "Planned Availability"	EFFECTIVEDATE	Yes					
49.00				ParentTo	SVCSEO-d. AVAIL where AVAILTYPE = "Last Order"	EFFECTIVEDATE	Yes					
50.00				ChildFrom	"SVCSEOPRCPT-d:PRCPTCNTRYEFF-d:CNTRYEFF"	EFFECTIVEDATE	Yes					
51.00				ChildTo	"SVCSEOPRCPT-d:PRCPTCNTRYEFF-d:CNTRYEFF"	ENDDATE	Yes					

	*/
    protected void doDQChecking(EntityItem rootEntity, String statusFlag) throws Exception
    {
    	addHeading(3,m_elist.getEntityGroup("PRCPT").getLongDescription()+" checks:");
    	//2.00		X	SVCSEOPRCPT-d		PRCPT							
    	//3.00			CountOf	=>	1			RE	RE	RE		must have at least one {LD: PRCPT}
	   	int cnt = getCount("SVCSEOPRCPT");
		EntityGroup prcptGrp = m_elist.getEntityGroup("PRCPT");
		if(cnt==0){
			//MINIMUM_ERR =  must have at least one {0}
			args[0] = prcptGrp.getLongDescription();
			createMessage(CHECKLEVEL_RE,"MINIMUM_ERR",args);
		}

		//4.00			DATAQUALITY	<=	PRCPT	STATUS		E	E	E		{LD: STATUS} can not be higher than the {LD: PRCPT} {NDN: PRCPT} {LD: STATUS} {STATUS}
		for (int i=0; i<prcptGrp.getEntityItemCount(); i++){
			EntityItem prcptitem = prcptGrp.getEntityItem(i);
			checkStatusVsDQ(prcptitem, "STATUS", rootEntity,CHECKLEVEL_E);
		}
		// get all prcpt countrys
		ArrayList prcptctryList = new ArrayList();
		EntityGroup cntryEffGrp = m_elist.getEntityGroup("CNTRYEFF");
		for (int i=0; i<cntryEffGrp.getEntityItemCount(); i++){
			EntityItem cntryeff = cntryEffGrp.getEntityItem(i);
			getCountriesAsList(cntryeff ,prcptctryList, CHECKLEVEL_NOOP);
		}
    	addDebug("all prcpt-cntryeffCtrys "+prcptctryList);
    	
    	//5.00-25.00
		checkSVCSEOAvails(rootEntity, prcptctryList, statusFlag);

    	// 26.00 - 38.00
		checkSVCMODAvails(rootEntity, prcptctryList, statusFlag);
    	
		// 39.00 - 45.00
		checkFODates(rootEntity);
	
		//46.00	SVCSEO		Root									
		//47.00				OneValidOverTime			Yes	E	E	E	For the SVCSEO, each instance of the child AVAIL where AVAILTYPE = "Planned Availability" check the PRCPT'S child CNTRYEFF EFFECTIVEDATE by Country. The AVAILTYPE = "Last Order" EFFECTIVEDATE is compared to ENDDATE.	must have at lease one valid {LD: PRCPT) {LD: CNTRYEFF} during the time that the {LD: SVCSEO} is valid by Country for {LD: AVAIL} Planned Availability and Last Order.
		//48.00				ParentFrom	SVCSEO-d. AVAIL where AVAILTYPE = "Planned Availability"	EFFECTIVEDATE	Yes					
		//49.00				ParentTo	SVCSEO-d. AVAIL where AVAILTYPE = "Last Order"	EFFECTIVEDATE	Yes					
		//50.00				ChildFrom	"SVCSEOPRCPT-d:PRCPTCNTRYEFF-d:CNTRYEFF"	EFFECTIVEDATE	Yes					
		//51.00				ChildTo	"SVCSEOPRCPT-d:PRCPTCNTRYEFF-d:CNTRYEFF"	ENDDATE	Yes					
		oneValidOverTime(rootEntity); 
		
		prcptctryList.clear();
    }

    /**
     * 
46.00	SVCSEO		Root									
47.00				OneValidOverTime			Yes	E	E	E	For the SVCSEO, each instance of the child AVAIL 
												where AVAILTYPE = "Planned Availability" check the PRCPT'S child 
												CNTRYEFF EFFECTIVEDATE by Country. 
												The AVAILTYPE = "Last Order" EFFECTIVEDATE is compared to ENDDATE.	
												must have at lease one valid {LD: PRCPT) {LD: CNTRYEFF} during the time that the {LD: SVCSEO} is valid by Country for {LD: AVAIL} Planned Availability and Last Order.
48.00				ParentFrom	SVCSEO-d. AVAIL where AVAILTYPE = "Planned Availability"	EFFECTIVEDATE	Yes					
49.00				ParentTo	SVCSEO-d. AVAIL where AVAILTYPE = "Last Order"	EFFECTIVEDATE	Yes					
50.00				ChildFrom	"SVCSEOPRCPT-d:PRCPTCNTRYEFF-d:CNTRYEFF"	EFFECTIVEDATE	Yes					
51.00				ChildTo	"SVCSEOPRCPT-d:PRCPTCNTRYEFF-d:CNTRYEFF"	ENDDATE	Yes					

     * @param root
     * @throws MiddlewareException 
     * @throws SQLException 
     */
    private void oneValidOverTime(EntityItem root) throws SQLException, MiddlewareException
    {
    	StringBuffer chksSb = new StringBuffer(root.getEntityGroup().getLongDescription());
    	chksSb.append(" "+m_elist.getEntityGroup("AVAIL").getLongDescription());
    	chksSb.append(" and "+m_elist.getEntityGroup("PRCPTCNTRYEFF").getLongDescription());
    	
    	addHeading(3,chksSb.toString()+" validity checks:");
    	
    	if(plaAvailVctY.size()==0){ // loavail not required
    		return;
    	}

       	//sort avails find earliest date by ctry and latest date by ctry
    	AttrComparator attrComp = new AttrComparator("EFFECTIVEDATE");
    	//48.00				ParentFrom	SVCSEO-d. AVAIL where AVAILTYPE = "Planned Availability"	EFFECTIVEDATE	Yes					
    	//49.00				ParentTo	SVCSEO-d. AVAIL where AVAILTYPE = "Last Order"	EFFECTIVEDATE	Yes		
		Collections.sort(plaAvailVctY, attrComp);
		Collections.sort(loAvailVctY, attrComp);
		
		Hashtable availByCtryTbl = new Hashtable();
    	for (int i=plaAvailVctY.size()-1; i>=0; i--){ // go in reverse, getting earliest date last
    		ArrayList ctryList = new ArrayList();
    		EntityItem plaAvail = (EntityItem)plaAvailVctY.elementAt(i);
			String fromdate = PokUtils.getAttributeValue(plaAvail, "EFFECTIVEDATE", "", EPOCH_DATE, false);
    		getCountriesAsList(plaAvail, ctryList,CHECKLEVEL_E);
    		
     		Iterator itr = ctryList.iterator();
    		while (itr.hasNext()) {
    			String ctryflag = (String) itr.next();
    			TPIC tpic = (TPIC)availByCtryTbl.get(ctryflag);
    			if(tpic!=null){
    				tpic.fromDate=fromdate;
    			}else{
    				availByCtryTbl.put(ctryflag,new TPIC(ctryflag,fromdate));
    			}
    		}
    		ctryList.clear();
    	}
    	for (int i=0; i<loAvailVctY.size(); i++){ // go forward, getting latest date last
    		ArrayList ctryList = new ArrayList();
    		EntityItem loAvail = (EntityItem)loAvailVctY.elementAt(i);
			String todate = PokUtils.getAttributeValue(loAvail, "EFFECTIVEDATE", "", FOREVER_DATE, false);
    		getCountriesAsList(loAvail, ctryList,CHECKLEVEL_E);
    		
     		Iterator itr = ctryList.iterator();
    		while (itr.hasNext()) {
    			String ctryflag = (String) itr.next();
     			TPIC tpic = (TPIC)availByCtryTbl.get(ctryflag);
    			if(tpic!=null){
    				tpic.toDate=todate;
    			}
    		}
    		ctryList.clear();
    	}
    	
    	addDebug("oneValidOverTime "+root.getKey()+" availByCtryTbl "+availByCtryTbl);
    
    	//get child entities dates
    	//SVCSEOPRCPT-d:PRCPTCNTRYEFF-d:CNTRYEFF
    	Vector prcptVct = PokUtils.getAllLinkedEntities(root, "SVCSEOPRCPT", "PRCPT");
    	Vector childVct =PokUtils.getAllLinkedEntities(prcptVct, "PRCPTCNTRYEFF", "CNTRYEFF");
    	addDebug("oneValidOverTime prcptVct "+prcptVct.size()+" CNTRYEFF childVct "+childVct.size());

    	AttrComparator attrcomp = new AttrComparator("EFFECTIVEDATE");
    	// sort children by efffrom
    	Collections.sort(childVct, attrcomp);
    	
		Hashtable childByCtryTbl = new Hashtable();
    	for (int i=childVct.size()-1; i>=0; i--){ // go in reverse, getting earliest date last
    		ArrayList ctryList = new ArrayList();
    		EntityItem cntryeff = (EntityItem)childVct.elementAt(i);
			String fromdate = PokUtils.getAttributeValue(cntryeff, "EFFECTIVEDATE", "", EPOCH_DATE, false);
			String todate = PokUtils.getAttributeValue(cntryeff, "ENDDATE", "", FOREVER_DATE, false);
    		getCountriesAsList(cntryeff, ctryList,CHECKLEVEL_E);
    		
     		Iterator itr = ctryList.iterator();
    		while (itr.hasNext()) {
    			String ctryflag = (String) itr.next();
    			Vector tpicVct = (Vector)childByCtryTbl.get(ctryflag);
    			if(tpicVct==null){
    				tpicVct = new Vector();
    				childByCtryTbl.put(ctryflag,tpicVct);
    			}
    			tpicVct.add(new TPIC(ctryflag,fromdate,todate));
    		}
    		ctryList.clear();
    	}
    	addDebug("oneValidOverTime CNTRYEFF childByCtryTbl "+childByCtryTbl);
    	
    	Iterator tpicitr = 	availByCtryTbl.keySet().iterator();
		tpicloop:while (tpicitr.hasNext()) {
			String ctryflag = (String) tpicitr.next();
			String ctryDesc = getUnmatchedDescriptions(m_elist.getEntityGroup("AVAIL"), "COUNTRYLIST", ctryflag);
    		TPIC tpic = (TPIC)availByCtryTbl.get(ctryflag);
        	addDebug("oneValidOverTime parent "+tpic);
    		Vector childtpicVct = (Vector)childByCtryTbl.get(ctryflag);
    		if(childtpicVct==null){
        		//must have at least one valid {LD: PRCPT) {LD: CNTRYEFF} during the time that the {LD: SVCSEO} is valid by Country for {LD: AVAIL} Planned Availability and Last Order.
        		//ONE_VALID_ERR = must have at least one valid {0} {1} during the time that the {2} is valid by Country for {3} Planned Availability and Last Order.
        		args[0]= m_elist.getEntityGroup("PRCPT").getLongDescription();
        		args[1]= m_elist.getEntityGroup("CNTRYEFF").getLongDescription();
        		args[2]= root.getEntityGroup().getLongDescription();
        		args[3]= m_elist.getEntityGroup("AVAIL").getLongDescription();
        		createMessage(CHECKLEVEL_E,"ONE_VALID_ERR",args);
        		//VALUE_FND = {0} found for {1} {2}
        		args[0]= "No "+m_elist.getEntityGroup("CNTRYEFF").getLongDescription();
        		args[1] = "Planned Availability";
        		args[2] ="Country: "+ctryDesc;
        		addResourceMsg("VALUE_FND",args);
    			continue;
    		}
    		
    		// check dates
    		// sort the vector by effective date
    		Collections.sort(childtpicVct);
    		
    		// look at first one 
    		TPIC childtpic = (TPIC)childtpicVct.firstElement();
        	String prevEffTo=childtpic.toDate;
        	addDebug("oneValidOverTime first child "+childtpic);
        	if(childtpic.fromDate.compareTo(tpic.fromDate)>0){
        		//must have at least one valid {LD: PRCPT) {LD: CNTRYEFF} during the time that the {LD: SVCSEO} is valid by Country for {LD: AVAIL} Planned Availability and Last Order.
        		//ONE_VALID_ERR = must have at least one valid {0} {1} during the time that the {2} is valid by Country for {3} Planned Availability and Last Order.
        		args[0]= m_elist.getEntityGroup("PRCPT").getLongDescription();
        		args[1]= m_elist.getEntityGroup("CNTRYEFF").getLongDescription();
        		args[2]= root.getEntityGroup().getLongDescription();
        		args[3]= m_elist.getEntityGroup("AVAIL").getLongDescription();
        		createMessage(CHECKLEVEL_E,"ONE_VALID_ERR",args);
        		
        		//CANNOT_BE_LATER_ERR2 = {0} {1} must not be later than the {2} {3} {4}
        		args[0]= m_elist.getEntityGroup("CNTRYEFF").getLongDescription();
        		args[1] =PokUtils.getAttributeDescription(m_elist.getEntityGroup("CNTRYEFF"),"EFFECTIVEDATE", "EFFECTIVEDATE")+
        			" ("+childtpic.fromDate+")";
        		args[2]= m_elist.getEntityGroup("AVAIL").getLongDescription()+" Planned Availability";
        		args[3] = PokUtils.getAttributeDescription(m_elist.getEntityGroup("AVAIL"), 
        				"EFFECTIVEDATE", "EFFECTIVEDATE")+" ("+tpic.fromDate+")";
        		args[4] = " for Country "+ctryDesc;
        		addResourceMsg("CANNOT_BE_LATER_ERR2",args);
        		continue;
        	}
        	// look at last one
        	// sort by enddate
        	for(int u=0; u<childtpicVct.size(); u++){
        		TPIC t = (TPIC)childtpicVct.elementAt(u);
        		t.setFromSort(false);
        	}
        	Collections.sort(childtpicVct);
        			
        	childtpic = (TPIC)childtpicVct.lastElement();
        	addDebug("oneValidOverTime last child "+childtpic);
        	if(childtpic.toDate.compareTo(tpic.toDate)<0){
        		//must have at least one valid {LD: PRCPT) {LD: CNTRYEFF} during the time that the {LD: SVCSEO} is valid by Country for {LD: AVAIL} Planned Availability and Last Order.
        		//ONE_VALID_ERR = must have at least one valid {0} {1} during the time that the {2} is valid by Country for {3} Planned Availability and Last Order.
        		args[0]= m_elist.getEntityGroup("PRCPT").getLongDescription();
        		args[1]= m_elist.getEntityGroup("CNTRYEFF").getLongDescription();
        		args[2]= root.getEntityGroup().getLongDescription();
        		args[3]= m_elist.getEntityGroup("AVAIL").getLongDescription();
        		createMessage(CHECKLEVEL_E,"ONE_VALID_ERR",args);
        
        		//CANNOT_BE_EARLIER_ERR3 = {0} {1} must not be earlier than the {2} {3} {4}  
           		args[0]= m_elist.getEntityGroup("CNTRYEFF").getLongDescription();
        		args[1] = PokUtils.getAttributeDescription(m_elist.getEntityGroup("CNTRYEFF"), 
        				"ENDDATE", "ENDDATE")+" ("+childtpic.toDate+")";
        		args[2]= m_elist.getEntityGroup("AVAIL").getLongDescription()+" Last Order Availability ";
        		args[3] = PokUtils.getAttributeDescription(m_elist.getEntityGroup("AVAIL"), 
        				"EFFECTIVEDATE", "EFFECTIVEDATE")+" ("+tpic.toDate+")";
         		args[4] = " for Country "+ctryDesc;
        		addResourceMsg("CANNOT_BE_EARLIER_ERR3",args);
        		continue;
        	}
    		// sort the vector by effective date
        	for(int u=0; u<childtpicVct.size(); u++){
        		TPIC t = (TPIC)childtpicVct.elementAt(u);
        		t.setFromSort(true);
        	}
        	Collections.sort(childtpicVct);
    		
        	// look for gaps
        	for (int i=1; i<childtpicVct.size(); i++){
        		childtpic = (TPIC)childtpicVct.elementAt(i);
        		String nextEffFrom = childtpic.fromDate;
        		String nextEffTo = childtpic.toDate;
        		addDebug("oneValidOverTime gaps "+childtpic);
        		if(nextEffFrom.compareTo(prevEffTo)>0){
            		//must have at least one valid {LD: PRCPT) {LD: CNTRYEFF} during the time that the {LD: SVCSEO} is valid by Country for {LD: AVAIL} Planned Availability and Last Order.
            		//ONE_VALID_ERR = must have at least one valid {0} {1} during the time that the {2} is valid by Country for {3} Planned Availability and Last Order.
            		args[0]= m_elist.getEntityGroup("PRCPT").getLongDescription();
            		args[1]= m_elist.getEntityGroup("CNTRYEFF").getLongDescription();
            		args[2]= root.getEntityGroup().getLongDescription();
            		args[3]= m_elist.getEntityGroup("AVAIL").getLongDescription();
            		createMessage(CHECKLEVEL_E,"ONE_VALID_ERR",args);
     
            		//GAPS_ERR = {0} has gaps in time between {1} and {2}
            		args[0]= m_elist.getEntityGroup("CNTRYEFF").getLongDescription();
            		args[1]= prevEffTo;
            		args[2]= nextEffFrom+" for Country "+ctryDesc;

            		addResourceMsg("GAPS_ERR",args);
            		continue tpicloop;
        		}
        		prevEffTo= nextEffTo;
        	}
		}

		Iterator itr = availByCtryTbl.keySet().iterator();
		while (itr.hasNext()) {
			String ctryflag = (String) itr.next();
			TPIC tpic = (TPIC)availByCtryTbl.get(ctryflag);
			tpic.dereference();
		}
		availByCtryTbl.clear();
		
		itr = childByCtryTbl.keySet().iterator();
		while (itr.hasNext()) {
			String ctryflag = (String) itr.next();
			Vector vct = (Vector)childByCtryTbl.get(ctryflag);
			for (int i=0; i<vct.size(); i++){
				TPIC tpic = (TPIC)vct.elementAt(i);
				tpic.dereference();
			}
		}
		childByCtryTbl.clear();
		
    	childVct.clear();
    	prcptVct.clear();
    }
    	
    /**
     * check svcseo avails
     * 
5.00	SVCSEO		Root									
6.00		Y	SVCSEOAVAIL-d		AVAIL							
7.00	WHEN		"First Order"	=	Y: AVAIL	AVAILTYPE						
8.00			SVCSEOFO	SetTo	Y: AVAIL	MIN(EFFECTIVEDATE)					SVCSEO AVAIL "First Order" EFFECTIVEDATE	
9.00	END	7.00										
10.00												
11.00	WHEN		"Planned Availability" (146)	=	Y: AVAIL	AVAILTYPE						
12.00			CountOf	=>	1			RW	RW	RE		must have at least one "Planned Availability"
12.80			A SVCSEOPRCPT-d: PRCPTCNTRYEFF-d									
13.00			Y: COUNTRYLIST	"IN aggregate G"	CNTRYEFF	COUNTRYLIST		E	E	E	SVCSEO AVAIL COUNTRYLIST must be a subset of PRCPT COUNTRYLIST	{LD: AVAIL} {NDN: Y: AVAIL} must not include a country that is not in the {LD: PRCPT} {NDN: X: PRCPT} {LD: COUNTRYLIST}
14.00			SVCSEOPA	SetTo	Y: AVAIL	MIN(EFFECTIVEDATE)					SVCSEO AVAIL "Planned Availability" EFFECTIVEDATE - Choose the earliest date if there are multiple	
15.00	ANNOUNCEMENT		Y: + AVAILANNA-d 		W: ANNOUNCEMENT						SVCSEO ANNOUNCEMENT Planned Availability	
16.00	WHEN		"RFA" (RFA)	<>	Y: AVAIL	AVAILANNTYPE						
17.00			CountOf	=	0			E	E	E	Only AVAIL.AVAILANNTYPE = "RFA" can be in an ANNOUNCEMENT	{LD: AVAIL} {NDN: Y:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
18.00	END	16.00										
19.00	IF		ANNTYPE	<>	New (19)							
20.00			CountOf	=	0			E	E	E		{LD: AVAIL} {NDN: Y:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
21.00	END	19.00										
22.00	IF		ANNTYPE	=	New (19)							
23.00			SVCSEOAD	SetTo	W: ANNOUNCEMENT	MIN(ANNDATE)					SVCSEO ANNOUNCEMENT NEW ANNDATE	
24.00	END	22.00										
25.00	END	11.00	
     * @param rootEntity
     * @param prcptctryList
     * @param statusFlag
     * @throws MiddlewareException 
     * @throws SQLException 
     */
    private void checkSVCSEOAvails(EntityItem rootEntity, ArrayList prcptctryList, String statusFlag) throws SQLException, MiddlewareException{ 
		
    	EntityGroup prcptGrp = m_elist.getEntityGroup("PRCPT");
    	EntityGroup cntryEffGrp = m_elist.getEntityGroup("CNTRYEFF");
    	
    	addHeading(3,rootEntity.getEntityGroup().getLongDescription()+" First Order "+
    			m_elist.getEntityGroup("AVAIL").getLongDescription()+" checks:");
    	//5.00	SVCSEO		Root									
    	//6.00		Y	SVCSEOAVAIL-d		AVAIL							
    	//7.00	WHEN		"First Order"	=	Y: AVAIL	AVAILTYPE
      	Vector availVct = PokUtils.getAllLinkedEntities(rootEntity, "SVCSEOAVAIL", "AVAIL");
    	Vector foAvailVctY = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", FIRSTORDERAVAIL);
    	plaAvailVctY = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", PLANNEDAVAIL);
    	loAvailVctY = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", LASTORDERAVAIL);
    	addDebug(rootEntity.getKey()+" availVct "+availVct.size()+" foAvailVctY "+foAvailVctY.size()+
    			" plaAvailVctY "+plaAvailVctY.size());
		for(int i=0; i<foAvailVctY.size(); i++){
			EntityItem availitem = (EntityItem)foAvailVctY.elementAt(i);
			String effDate = PokUtils.getAttributeValue(availitem, "EFFECTIVEDATE", "", null, false);
	    	//8.00			SVCSEOFO	SetTo	Y: AVAIL	MIN(EFFECTIVEDATE)		SVCSEO AVAIL "First Order" EFFECTIVEDATE	
			if (SVCSEOFO==null){
				SVCSEOFO = effDate;
			}else{
				if (SVCSEOFO.compareTo(effDate)>0){ // find earliest date
					SVCSEOFO = effDate;
				}
			}
		   	addDebug("svcseo fo avail "+availitem.getKey()+" effDate "+effDate+" SVCSEOFO "+SVCSEOFO);
		}
		foAvailVctY.clear();
		
		//VALUE_FND = {0} found for {1} {2}
		if(SVCSEOFO==null){
			args[0] = "No Date";
		}else{
			args[0] = SVCSEOFO;
		}
		args[1] = rootEntity.getEntityGroup().getLongDescription();
		args[2] = "First Order date";
		addResourceMsg("VALUE_FND",args);
		
	  	addHeading(3,rootEntity.getEntityGroup().getLongDescription()+" Planned Availability checks:");
    	//9.00	END	7.00										
    	//10.00												
    	//11.00	WHEN		"Planned Availability" (146)	=	Y: AVAIL	AVAILTYPE						
    	//12.00			CountOf	=>	1			RW	RW	RE		must have at least one "Planned Availability"
		checkPlannedAvailsExist(plaAvailVctY, getCheck_RW_RW_RE(statusFlag)); 
//		20121030 Add	12.20	WHEN		"Final" (FINAL)	=	SVCSEO	DATAQUALITY																																																																																																																																																																																																																																													
//		20121030 Add	12.22	IF		STATUS	=	"Ready for Review" (0040)																																																																																																																																																																																																																																														
//		20121030 Add	12.24	OR		STATUS	=	"Final" (0020)																																																																																																																																																																																																																																														
//		20121030 Add	12.26			CountOf	=>	1					RE		must have at least one "Planned Availability" that is either "Ready for Review" or "Final" in order to be "Final"																																																																																																																																																																																																																																							
//		20121030 Add	12.28	END	12.20			
		checkPlannedAvailsStatus(plaAvailVctY, rootEntity,CHECKLEVEL_RE);

		for(int i=0; i<plaAvailVctY.size(); i++){
			EntityItem availitem = (EntityItem)plaAvailVctY.elementAt(i);
			String effDate = PokUtils.getAttributeValue(availitem, "EFFECTIVEDATE", "", null, false);
	    	//14.00			SVCSEOPA	SetTo	Y: AVAIL	MIN(EFFECTIVEDATE)		SVCSEO AVAIL "Planned Availability" EFFECTIVEDATE	
			if (SVCSEOPA==null){
				SVCSEOPA = effDate;
			}else{
				if (SVCSEOPA.compareTo(effDate)>0){ // find earliest date
					SVCSEOPA = effDate;
				}
			}
		   	
			addDebug("svcseo pla avail "+availitem.getKey()+" effDate "+effDate+" SVCSEOPA "+SVCSEOPA);
			
			if(cntryEffGrp.getEntityItemCount()>0){ 
				String missingCtry = checkCtryMismatch(availitem, prcptctryList, CHECKLEVEL_E);
				if (missingCtry.length()>0){
					ArrayList availctrylist = new ArrayList();
					getCountriesAsList(availitem ,availctrylist, CHECKLEVEL_NOOP);
					addDebug(availitem.getKey()+" COUNTRYLIST had extra ["+missingCtry+"] availctrylist "+availctrylist);
					//12.80			A SVCSEOPRCPT-d: PRCPTCNTRYEFF-d									
					//13.00			Y: COUNTRYLIST	"IN	aggregate G"	CNTRYEFF	COUNTRYLIST		E	E	E	
					//SVCSEO AVAIL COUNTRYLIST must be a subset of PRCPT COUNTRYLIST	
					//{LD: AVAIL} {NDN: Y: AVAIL} must not include a country that is not in the {LD: PRCPT} {NDN: X: PRCPT} {LD: COUNTRYLIST}

					//INCLUDE_ERR2 = {0} {1} must not include a Country that is not in the {2} {3}. Extra countries are: {4}
					args[0]=this.getLD_NDN(availitem);
					args[1]="";
					args[2]=prcptGrp.getLongDescription()+" "+cntryEffGrp.getLongDescription();
					args[3]=PokUtils.getAttributeDescription(cntryEffGrp, "COUNTRYLIST", "COUNTRYLIST");
					args[4]=missingCtry;
					createMessage(CHECKLEVEL_E,"INCLUDE_ERR2",args);

					availctrylist.clear();
				}
			}

	    	//15.00	ANNOUNCEMENT		Y: + AVAILANNA-d 		W: ANNOUNCEMENT		SVCSEO ANNOUNCEMENT Planned Availability	
			String availAnntypeFlag = PokUtils.getAttributeFlagValue(availitem, "AVAILANNTYPE");
			if (availAnntypeFlag==null){
				availAnntypeFlag = AVAILANNTYPE_RFA; // if not set, default to RFA
			}
			Vector annVct= PokUtils.getAllLinkedEntities(availitem, "AVAILANNA", "ANNOUNCEMENT");
			addDebug(availitem.getKey()+" availAnntypeFlag "+availAnntypeFlag+" annVct "+annVct.size());

			if (AVAILANNTYPE_RFA.equals(availAnntypeFlag)){
				for (int i2=0; i2<annVct.size(); i2++){
					EntityItem annItem = (EntityItem)annVct.elementAt(i2);
					String anntype = getAttributeFlagEnabledValue(annItem, "ANNTYPE");
					addDebug(annItem.getKey()+" type "+anntype);

					if(ANNTYPE_NEW.equals(anntype)){
				    	//22.00	IF		ANNTYPE	=	New (19)							
				    	//23.00			SVCSEOAD	SetTo	W: ANNOUNCEMENT		MIN(ANNDATE)	SVCSEO ANNOUNCEMENT NEW ANNDATE	
						String annDate = PokUtils.getAttributeValue(annItem, "ANNDATE", "", null, false);
						if (SVCSEOAD==null){
							SVCSEOAD = annDate;
						}else{
							if (SVCSEOAD.compareTo(annDate)>0){ // find earliest date
								SVCSEOAD = annDate;
							}
						}
					   	addDebug("svcseo pla avail ann "+annItem.getKey()+" annDate "+annDate+" SVCSEOAD "+SVCSEOAD);
				    	//24.00	END	22.00
					}else{
				    	//19.00	IF		ANNTYPE	<>	New (19)							
				    	//20.00			CountOf	=	0			E	E	E		{LD: AVAIL} {NDN: Y:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
						//MUST_NOT_BE_IN_ERR2= {0} must not be in {1}
						args[0] = getLD_NDN(availitem);
						args[1] = getLD_NDN(annItem);
						createMessage(CHECKLEVEL_E,"MUST_NOT_BE_IN_ERR2",args);
				    	//21.00	END	19.00
					}
				}// end ann loop
			}else{ // not RFA
		    	//16.00	WHEN		"RFA" (RFA)	<>	Y: AVAIL	AVAILANNTYPE						
		    	//17.00			CountOf	=	0			E	E	E	Only AVAIL.AVAILANNTYPE = "RFA" can be in an ANNOUNCEMENT	{LD: AVAIL} {NDN: Y:AVAIL} must not be in {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
				for (int i2=0; i2<annVct.size(); i2++){
					//MUST_NOT_BE_IN_THIS_ERR= {0} must not be in this {1} {2}
					args[0] = this.getLD_NDN(availitem);
					args[1] = this.getLD_Value((EntityItem)annVct.elementAt(i2),"ANNTYPE");
					args[2] = getLD_NDN((EntityItem)annVct.elementAt(i2));
					createMessage(CHECKLEVEL_E,"MUST_NOT_BE_IN_THIS_ERR",args);
				}
				//18.00	END	16.00	
			} // end not RFA
	    	
	    	annVct.clear();
		}
		//25.00	END	11.00
		//VALUE_FND = {0} found for {1} {2}
		if(SVCSEOPA==null){
			args[0] = "No Date";
		}else{
			args[0] = SVCSEOPA;
		}
		args[1] = rootEntity.getEntityGroup().getLongDescription();
		args[2] = "Planned Availability date";
		addResourceMsg("VALUE_FND",args);
		
		//VALUE_FND = {0} found for {1} {2}
		if(SVCSEOAD==null){
			args[0] = "No Date";
		}else{
			args[0] = SVCSEOAD;
		}
		args[1] = rootEntity.getEntityGroup().getLongDescription();
		args[2] = "New Announcement date";
		addResourceMsg("VALUE_FND",args);
		
		availVct.clear();
    }
    
    /**
     * check SVCMOD avails
     * 
26.00	SVCSEO		Root									
27.00			SVCMODSVCSEO-u		SVCMOD							
28.00		Z	SVCMODAVAIL-d		AVAIL							
29.00	WHEN		"First Order"	=	Z: AVAIL	AVAILTYPE						
30.00			SVCMODFO	SetTo	Z: AVAIL	MIN(EFFECTIVEDATE)		SVCMOD AVAIL "First Order" EFFECTIVEDATE	
31.00	END	29.00										
32.00	WHEN		"Planned Availability" (146)	=	Z: AVAIL	AVAILTYPE						
33.00			CountOf	=>	1			RW	RW	RE		must have at least one "Planned Availability"
34.00			Z: COUNTRYLIST	Contains	A: CNTRYEFF	COUNTRYLIST					PRCPT COUNTRYLIST must be a subset of SVCMOD AVAIL COUNTRYLIST	{LD: PRCPT} {NDN: X: PRCPT} {LD: COUNTRYLIST}  must not include a country that is not in the {LD: AVAIL} {NDN: Z: AVAIL}
35.00			SVCMODPA	SetTo	Z: AVAIL	MIN(EFFECTIVEDATE)		SVCMOD AVAIL "Planned Availability" EFFECTIVEDATE	
36.00	ANNOUNCEMENT		Z: + AVAILANNA-d 								SVCMOD ANNOUNCEMENT Planned Availability	
37.00			SVCMODAD	SetTo	Z: ANNOUNCEMENT	MIN(ANNDATE)		SVCMOD ANNOUNCEMENT NEW ANNDATE	
38.00	END	32.00	
    	
     * @param rootEntity
     * @param prcptctryList
     * @throws MiddlewareException 
     * @throws SQLException 
     */
    private void checkSVCMODAvails(EntityItem rootEntity, ArrayList prcptctryList, String statusFlag) 
    throws SQLException, MiddlewareException
    {
    	//26.00	SVCSEO		Root									
    	//27.00			SVCMODSVCSEO-u		SVCMOD							
    	//28.00		Z	SVCMODAVAIL-d		AVAIL							
		EntityGroup prcptGrp = m_elist.getEntityGroup("PRCPT");
		EntityGroup cntryEffGrp = m_elist.getEntityGroup("CNTRYEFF");
		
      	Vector svcmodVct = PokUtils.getAllLinkedEntities(rootEntity, "SVCMODSVCSEO", "SVCMOD");
    	
      	if(svcmodVct.size()==0){ // this is not flagged as an error in the spec
        	addHeading(3,"No "+m_elist.getEntityGroup("SVCMODSVCSEO").getLongDescription()+" found");
      		return;
      	}
      	
    	addHeading(3,m_elist.getEntityGroup("SVCMOD").getLongDescription()+" First Order "+
    			m_elist.getEntityGroup("AVAIL").getLongDescription()+" checks:");

      	Vector availVctZ = PokUtils.getAllLinkedEntities(svcmodVct, "SVCMODAVAIL", "AVAIL");
    	Vector foAvailVctZ = PokUtils.getEntitiesWithMatchedAttr(availVctZ, "AVAILTYPE", FIRSTORDERAVAIL);
    	addDebug("svcmodVct "+svcmodVct.size()+" availVctZ "+availVctZ.size()+" foAvailVctZ "+foAvailVctZ.size());
    	
    	//29.00	WHEN		"First Order"	=	Z: AVAIL	AVAILTYPE						
		for(int i=0; i<foAvailVctZ.size(); i++){
			EntityItem availitem = (EntityItem)foAvailVctZ.elementAt(i);
			String effDate = PokUtils.getAttributeValue(availitem, "EFFECTIVEDATE", "", null, false);
	    	//30.00			SVCMODFO	SetTo	Z: AVAIL	MIN(EFFECTIVEDATE)			SVCMOD AVAIL "First Order" EFFECTIVEDATE	
			if (SVCMODFO==null){
				SVCMODFO = effDate;
			}else{
				if (SVCMODFO.compareTo(effDate)>0){ // find earliest date
					SVCMODFO = effDate;
				}
			}
		   	addDebug("svcmod fo avail "+availitem.getKey()+" effDate "+effDate+" SVCMODFO "+SVCMODFO);
		}
       	//31.00	END	29.00
		//VALUE_FND = {0} found for {1} {2}
		if(SVCMODFO==null){
			args[0] = "No Date";
		}else{
			args[0] = SVCMODFO;
		}
		args[1] = m_elist.getEntityGroup("SVCMOD").getLongDescription();
		args[2] = "First Order date";
		addResourceMsg("VALUE_FND",args);
       	
       	addHeading(3,m_elist.getEntityGroup("SVCMOD").getLongDescription()+" Planned Availability checks:");
    	//32.00	WHEN		"Planned Availability" (146)	=	Z: AVAIL	AVAILTYPE						
    	//33.00			CountOf	=>	1			RW	RW	RE		must have at least one "Planned Availability"	
       	// do this per SVCMOD
       	for (int x2=0; x2<svcmodVct.size(); x2++){
       		EntityItem svcmoditem = (EntityItem)svcmodVct.elementAt(x2);
       		availVctZ = PokUtils.getAllLinkedEntities(svcmoditem, "SVCMODAVAIL", "AVAIL");
       		Vector plaAvailVctZ = PokUtils.getEntitiesWithMatchedAttr(availVctZ, "AVAILTYPE", PLANNEDAVAIL);
       		addDebug(svcmoditem.getKey()+" availVctZ "+availVctZ.size()+
       				" plaAvailVctZ "+plaAvailVctZ.size());
       		if (plaAvailVctZ.size()==0){
       			//MINIMUM2_ERR = {0} must have at least one {1}
       			args[0] = getLD_NDN(svcmoditem);
       			args[1] = "Planned Availability";
       			createMessage(getCheck_RW_RW_RE(statusFlag),"MINIMUM2_ERR",args);
       		}
       		ArrayList availCtrys = new ArrayList(); 
       		for(int i=0; i<plaAvailVctZ.size(); i++){
       			EntityItem availitem = (EntityItem)plaAvailVctZ.elementAt(i);
       			String effDate = PokUtils.getAttributeValue(availitem, "EFFECTIVEDATE", "", null, false);
       			//35.00			SVCMODPA	SetTo	Z: AVAIL	MIN(EFFECTIVEDATE)			SVCMOD AVAIL "Planned Availability" EFFECTIVEDATE	
       			if (SVCMODPA==null){
       				SVCMODPA = effDate;
       			}else{
       				if (SVCMODPA.compareTo(effDate)>0){ // find earliest date
       					SVCMODPA = effDate;
       				}
       			}
       			// get all avail countries
				getCountriesAsList(availitem ,availCtrys, CHECKLEVEL_E);
				
       			Vector annVct= PokUtils.getAllLinkedEntities(availitem, "AVAILANNA", "ANNOUNCEMENT");
       			addDebug(availitem.getKey()+" annVct "+annVct.size());
       			for (int i2=0; i2<annVct.size(); i2++){
       				EntityItem annItem = (EntityItem)annVct.elementAt(i2);
       				//36.00	ANNOUNCEMENT		Z: + AVAILANNA-d 								SVCMOD ANNOUNCEMENT Planned Availability	
       				//37.00			SVCMODAD	SetTo	Z: ANNOUNCEMENT	MIN(ANNDATE)			SVCMOD ANNOUNCEMENT NEW ANNDATE	
       				String annDate = PokUtils.getAttributeValue(annItem, "ANNDATE", "", null, false);
       				if (SVCMODAD==null){
       					SVCMODAD = annDate;
       				}else{
       					if (SVCMODAD.compareTo(annDate)>0){ // find earliest date
       						SVCMODAD = annDate;
       					}
       				}
       				addDebug(svcmoditem.getKey()+" pla avail ann "+annItem.getKey()+" annDate "+annDate+" SVCMODAD "+SVCMODAD);
       				//38.00	END	32.00
       			}// end ann loop
       			annVct.clear();
       		}// end planned avail loop

			addDebug(svcmoditem.getKey()+" all pla availCtrys "+availCtrys);
			
			if(cntryEffGrp.getEntityItemCount()>0 && plaAvailVctZ.size()>0){   
				if(!availCtrys.containsAll(prcptctryList)){
					for (int ii=0; ii<prcptGrp.getEntityItemCount(); ii++){
						EntityItem prcptitem = prcptGrp.getEntityItem(ii);
						Vector ctryeffVct = PokUtils.getAllLinkedEntities(prcptitem, "PRCPTCNTRYEFF", "CNTRYEFF");
						for(int x=0; x<ctryeffVct.size(); x++){
							EntityItem cntryitem = (EntityItem)ctryeffVct.elementAt(x);
							String missingCtry = checkCtryMismatch(cntryitem, availCtrys, CHECKLEVEL_E);
							//34.00			Z: COUNTRYLIST	Contains	A: CNTRYEFF	COUNTRYLIST					
							//PRCPT COUNTRYLIST must be a subset of SVCMOD AVAIL COUNTRYLIST	
							//{LD: PRCPT} {NDN: X: PRCPT} {LD: COUNTRYLIST}  must not include a country that is not in the {LD: AVAIL} {NDN: Z: AVAIL}
							if (missingCtry.length()>0){
								args[0]=getLD_NDN(prcptitem);
								args[1]=getLD_NDN(cntryitem);
				    			//INCLUDE_ERR2 = {0} {1} must not include a Country that is not in the {2} {3}. Extra countries are: {4}
				    			args[2]=this.getLD_NDN(svcmoditem)+" "+
				    				m_elist.getEntityGroup("AVAIL").getLongDescription();
				    			args[3]=PokUtils.getAttributeDescription(cntryEffGrp, "COUNTRYLIST", "COUNTRYLIST");
				    			args[4]=missingCtry;
				    			createMessage(CHECKLEVEL_E,"INCLUDE_ERR2",args);
							}
						}// end cntryeff loop per prcpt
					} // end prcpt loop
				}// cntryeff were not a subset of avail ctrys
			}//end cntryeff exist
       		
    		availVctZ.clear();
    		plaAvailVctZ.clear();
    		availCtrys.clear();
       	}// end svcmod loop
       	//38.00	END	32.00
		//VALUE_FND = {0} found for {1} {2}
		if(SVCMODPA==null){
			args[0] = "No Date";
		}else{
			args[0] = SVCMODPA;
		}
		args[1] = m_elist.getEntityGroup("SVCMOD").getLongDescription();
		args[2] = "Planned Availability date";
		addResourceMsg("VALUE_FND",args);
		
		//VALUE_FND = {0} found for {1} {2}
		if(SVCMODAD==null){
			args[0] = "No Date";
		}else{
			args[0] = SVCMODAD;
		}
		args[1] = m_elist.getEntityGroup("SVCMOD").getLongDescription();
		args[2] = "New Announcement date";
		addResourceMsg("VALUE_FND",args);
		
		availVctZ.clear();
    	foAvailVctZ.clear();
    }

    /**
     * 
39.00			SVCSEOFIRSTORDER	FirstValue		SVCSEOFO					Derives SVCSEO First Order	
40.00						SVCSEOAD						
41.00						SVCSEOPA						
42.00			SVCMODFIRSTORDER	FirstValue		SVCMODFO					Derves SVCMOD First Order	
43.00						SVCMODAD						
44.00						SVCMODPA						
44.20	IF		NotNull(SVCSEOFIRSTORDER)									
44.40	AND		NotNull(SVCMODFIRSTORDER)									
45.00			SVCMODFIRSTORDER	<=	SVCSEOFIRSTORDER			RE	RE	RE	First Order SVCMOD can not be later than SVCSEO First Order	{LD: SVCMOD} First Order date must not be later than the {LD: SVCSEO} {NDN: SVCSEO)
45.20	END	44.40										

     * @param rootEntity
     * @throws MiddlewareException 
     * @throws SQLException 
     */
    private void checkFODates(EntityItem rootEntity) throws SQLException, MiddlewareException{
    	String seofodate=null;
    	String modfodate=null;
    	//39.00			SVCSEOFIRSTORDER	FirstValue		SVCSEOFO					Derives SVCSEO First Order	
    	//40.00						SVCSEOAD						
    	//41.00						SVCSEOPA	
    	if (SVCSEOFO!=null){
    		seofodate = SVCSEOFO;
    	}else if (SVCSEOAD!=null){
    		seofodate = SVCSEOAD;
    	}else if (SVCSEOPA!=null){
    		seofodate = SVCSEOPA;
    	}
    	
    	//42.00			SVCMODFIRSTORDER	FirstValue		SVCMODFO					Derives SVCMOD First Order	
    	//43.00						SVCMODAD						
    	//44.00						SVCMODPA	
    	if (SVCMODFO!=null){
    		modfodate = SVCMODFO;
    	}else if (SVCMODAD!=null){
    		modfodate = SVCMODAD;
    	}else if (SVCMODPA!=null){
    		modfodate = SVCMODPA;
    	}
    	//44.20	IF		NotNull(SVCSEOFIRSTORDER)									
    	//44.40	AND		NotNull(SVCMODFIRSTORDER)									
    	//45.00			SVCMODFIRSTORDER	<=	SVCSEOFIRSTORDER	RE RE RE			First Order SVCMOD can not be later than SVCSEO First Order	
    	//{LD: SVCMOD} First Order date must not be later than the {LD: SVCSEO} {NDN: SVCSEO)
    	//FODATE_ERR= {0} First Order date {1} must not be later than the {2} date {3}
    	if(modfodate!=null && seofodate!=null){
    		if(modfodate.compareTo(seofodate)>0){
				args[0]=m_elist.getEntityGroup("SVCMOD").getLongDescription();
    			args[1]=modfodate;
    			args[2]=this.getLD_NDN(rootEntity);
    			args[3]=seofodate;
				createMessage(CHECKLEVEL_RE,"FODATE_ERR",args); 
    		}
    	}
    	//45.20	END	44.40
    }
    /**********************************
     * complete abr processing after status moved to final; (status was r4r)
     *C. STATUS changed to Final
176.02	R1.0	SVCSEO		Root Entity							
176.04	R1.0	IF		SVCMODSVCSEO-u	SVCMOD	STATUS	=	"Ready for Review" (0040)			
176.06	R1.0	AND			SVCMOD	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
176.08	R1.0	SET			SVCMOD				ADSABRSTATUS	&ADSFEEDRFR	
176.10	R1.0	ELSE	176.04								
176.12	R1.0	IF			SVCMOD	STATUS	=	"Final" (0020)			
176.14	R1.0	AND			SVCSEO	STATUS	=	"Final" (0020)			
176.16	R1.0	SET			SVCMOD				ADSABRSTATUS		&ADSFEED
176.18	R1.0	END	176.12								
176.20	R1.0	END	176.04								
176.22	R1.0	IF		SVCMODSVCSEOREF-u	SVCMOD	STATUS	=	"Ready for Review" (0040)			
176.24	R1.0	AND			SVCMOD	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
176.26	R1.0	SET			SVCMOD				ADSABRSTATUS	&ADSFEEDRFR	
176.28	R1.0	ELSE	176.22								
176.30	R1.0	IF			SVCMOD	STATUS	=	"Final" (0020)			
176.32	R1.0	AND			SVCSEO	STATUS	=	"Final" (0020)			
176.34	R1.0	SET			SVCMOD				ADSABRSTATUS		&ADSFEED
176.36	R1.0	END	176.30								
176.38	R1.0	END	176.22																				
     */
    protected void completeNowFinalProcessing() throws
    java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException,
    COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    {
    	//176.02	R1.0	SVCSEO		Root Entity							
    	EntityGroup svcmodGrp = m_elist.getEntityGroup("SVCMOD");
    	for (int i=0; i<svcmodGrp.getEntityItemCount(); i++){
    		EntityItem svcmod = svcmodGrp.getEntityItem(i);
//        	String lifecycle = PokUtils.getAttributeFlagValue(svcmod, "LIFECYCLE");
//    	  	addDebug("completeNowFinalProcessing: "+svcmod.getKey()+" lifecycle "+lifecycle);
//    	  	if (lifecycle==null || lifecycle.length()==0){ 
//    	  		lifecycle = LIFECYCLE_Plan;
//    	  	}

    	  	if (statusIsFinal(svcmod)){
    	  		//176.12	R1.0	IF			SVCMOD	STATUS	=	"Final" (0020)			
    	    	//176.14	R1.0	AND			SVCSEO	STATUS	=	"Final" (0020)			
    	    	//176.16	R1.0	SET			SVCMOD				ADSABRSTATUS		&ADSFEED
						
    	  		//176.30	R1.0	IF			SVCMOD	STATUS	=	"Final" (0020)			
    	  		//176.32	R1.0	AND			SVCSEO	STATUS	=	"Final" (0020)			
    	  		//176.34	R1.0	SET			SVCMOD				ADSABRSTATUS		&ADSFEED
    	  											
    	  		setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getQueuedValueForItem(svcmod,"ADSABRSTATUS"),svcmod);
    	  		//176.18	R1.0	END	176.12	
       	  		//176.36	R1.0	END	176.30
    	  	}else{
    	  		//176.04	R1.0	IF		SVCMODSVCSEO-u	SVCMOD	STATUS	=	"Ready for Review" (0040)			
    	  		//20130904 Delete 176.06	R1.0	AND			SVCMOD	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
    	  		//176.22	R1.0	IF		SVCMODSVCSEOREF-u	SVCMOD	STATUS	=	"Ready for Review" (0040)			
    	  		//176.24	R1.0	AND			SVCMOD	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
    	  		doRFR_ADSXML(svcmod);//TODO check if need to use the same way with model to queue abr
//    	  		if (this.statusIsRFR(svcmod) && (LIFECYCLE_Plan.equals(lifecycle) ||  // first time moving to RFR
//    	  				LIFECYCLE_Develop.equals(lifecycle))){ // been RFR before
//    	  			//176.08	R1.0	SET			SVCMOD				ADSABRSTATUS	&ADSFEEDRFR	
//        	  		//176.26	R1.0	SET			SVCMOD				ADSABRSTATUS	&ADSFEEDRFR	
//    	  			setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getRFRQueuedValueForItem(svcmod,"ADSABRSTATUS"),svcmod);
//    	  		}
 
    	  	}
    	  	//176.20	R1.0	END	176.04
    	  	//176.38	R1.0	END	176.22	
    	}
    }
    /**********************************
     * complete abr processing after status moved to readyForReview; (status was chgreq)
 	* B.	Status changed to Ready for Review	
176.02	R1.0	SVCSEO		Root Entity							
176.04	R1.0	IF		SVCMODSVCSEO-u	SVCMOD	STATUS	=	"Ready for Review" (0040)			
176.06	R1.0	AND			SVCMOD	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
176.08	R1.0	SET			SVCMOD				ADSABRSTATUS	&ADSFEEDRFR	
176.10	R1.0	ELSE	176.04								
176.12	R1.0	IF			SVCMOD	STATUS	=	"Final" (0020)			
176.14	R1.0	AND			SVCSEO	STATUS	=	"Final" (0020)			
176.16	R1.0	SET			SVCMOD				ADSABRSTATUS		&ADSFEED
176.18	R1.0	END	176.12								
176.20	R1.0	END	176.04								
176.22	R1.0	IF		SVCMODSVCSEOREF-u	SVCMOD	STATUS	=	"Ready for Review" (0040)			
176.24	R1.0	AND			SVCMOD	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
176.26	R1.0	SET			SVCMOD				ADSABRSTATUS	&ADSFEEDRFR	
176.28	R1.0	ELSE	176.22								
176.30	R1.0	IF			SVCMOD	STATUS	=	"Final" (0020)			
176.32	R1.0	AND			SVCSEO	STATUS	=	"Final" (0020)			
176.34	R1.0	SET			SVCMOD				ADSABRSTATUS		&ADSFEED
176.36	R1.0	END	176.30								
176.38	R1.0	END	176.22								

     */
    protected void completeNowR4RProcessing() throws
    java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException,
    COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    {
    	//176.02	R1.0	SVCSEO		Root Entity							
    	EntityGroup svcmodGrp = m_elist.getEntityGroup("SVCMOD");
    	for (int i=0; i<svcmodGrp.getEntityItemCount(); i++){
    		EntityItem svcmod = svcmodGrp.getEntityItem(i);
//        	String lifecycle = PokUtils.getAttributeFlagValue(svcmod, "LIFECYCLE");
//    	  	addDebug("completeNowFinalProcessing: "+svcmod.getKey()+" lifecycle "+lifecycle);
//    	  	if (lifecycle==null || lifecycle.length()==0){ 
//    	  		lifecycle = LIFECYCLE_Plan;
//    	  	}

    	  	//176.04	R1.0	IF		SVCMODSVCSEO-u	SVCMOD	STATUS	=	"Ready for Review" (0040)			
    	  	//20130904 Delete 176.06	R1.0	AND			SVCMOD	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
    	  	//176.22	R1.0	IF		SVCMODSVCSEOREF-u	SVCMOD	STATUS	=	"Ready for Review" (0040)			
    	  	//20130904 Delete 176.24	R1.0	AND			SVCMOD	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			

    	  	if (this.statusIsRFR(svcmod)){
//    	  			&& (LIFECYCLE_Plan.equals(lifecycle) ||  // first time moving to RFR
//    	  			LIFECYCLE_Develop.equals(lifecycle))){ // been RFR before
    	  		//176.08	R1.0	SET			SVCMOD				ADSABRSTATUS	&ADSFEEDRFR	
    	  		//176.26	R1.0	SET			SVCMOD				ADSABRSTATUS	&ADSFEEDRFR	
    	  		setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getRFRQueuedValueForItem(svcmod,"ADSABRSTATUS"),svcmod);
    	  	}

    	  	//176.20	R1.0	END	176.04
    	  	//176.38	R1.0	END	176.22	
    	}
    }
	
    /***********************************************
    *  Get ABR description
    *
    *@return java.lang.String
    */
    public String getDescription()
    {
        String desc =  "SVCSEO ABR.";

        return desc;
    }

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getABRVersion()
    {
        return "$Revision: 1.4 $";
    }
}
