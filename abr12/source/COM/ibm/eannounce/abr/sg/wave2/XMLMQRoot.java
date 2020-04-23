// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg.wave2;


import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.*;

import java.util.*;
import java.io.*;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import com.ibm.transform.oim.eacm.diff.*;

/**********************************************************************************
* Used for checking entities and structure queued by DQ abrs
*
*/
// XMLMQRoot.java,v
// XMLMQRoot.java,v
// Revision 1.7  2010/01/08 12:43:18  wendy
// cvs failure again
//
// Revision 1.5  2008/05/27 14:28:58  wendy
// Clean up RSA warnings
//
// Revision 1.4  2008/05/03 23:29:32  wendy
// Changed to support generation of large XML files
//
// Revision 1.3  2008/05/02 19:05:19  wendy
// Check for null xml before notify()
//
// Revision 1.2  2008/05/01 12:07:29  wendy
// Allow control of notification
//
// Revision 1.1  2008/04/29 14:30:47  wendy
// Init for
//  -   CQ00003539-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC
//  -   CQ00005096-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Add Category MM and Images
//  -   CQ00005046-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Support CRAD in BHC
//  -   CQ00005045-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Upgrade/Conversion Support
//  -   CQ00006862-WI  - BHC 3.0 Support - Support for Services Data UI
//
//
public abstract class XMLMQRoot extends XMLMQAdapter
{
    /**********************************
    * create xml and write to queue
    */
    public void processThis(ADSABRSTATUS abr, Profile profileT1, Profile profileT2, EntityItem rootEntity)
    throws
    java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException,
    ParserConfigurationException,
    java.rmi.RemoteException,
    COM.ibm.eannounce.objects.EANBusinessRuleException,
    COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
    IOException,
    javax.xml.transform.TransformerException,
	MissingResourceException
    {
		processThis(abr, profileT1, profileT2, rootEntity, true);
    }

    /**********************************
    * create xml and write to queue if notifynow=true
    */
    protected void processThis(ADSABRSTATUS abr, Profile profileT1, Profile profileT2, EntityItem rootEntity,
    	boolean notifyNow)
    throws
    java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException,
    ParserConfigurationException,
    java.rmi.RemoteException,
    COM.ibm.eannounce.objects.EANBusinessRuleException,
    COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
    IOException,
    javax.xml.transform.TransformerException,
	MissingResourceException
    {
		long lastTime =System.currentTimeMillis();
		long runTime = 0;
	    String m_strEpoch = "1980-01-01-00.00.00.000000";
		// do pull at current time so proper meta gets cached!!!
		// some meta is post 1980 so VE gets bad info
		// do pull at time2 (toDate)
		EntityList listT2 = abr.getEntityListForDiff(profileT2, getVeName(),rootEntity);

		// do pull at time1 (fromDate)
		EntityList listT1 = abr.getEntityListForDiff(profileT1, getVeName(),rootEntity);

		runTime = System.currentTimeMillis();
		abr.addDebug("Time for both pulls: "+Stopwatch.format(runTime-lastTime));
		lastTime=runTime;

		// get VE steps for later flattening the VE
		Hashtable hshMap =
			((ExtractActionItem)listT2.getParentActionItem()).generateVESteps(abr.getDB(), profileT2,
				rootEntity.getEntityType());

		// flatten both VEs using the VE steps
		DiffVE diff = new DiffVE(listT1,listT2, hshMap);
		diff.setCheckAllNLS(true);
		abr.addDebug("hshMap: "+hshMap);
		abr.addDebug("time1 flattened VE: "+diff.getPriorDiffVE());
		abr.addDebug("time2 flattened VE: "+diff.getCurrentDiffVE());

		// merge time1 and time2 flattened VEs into one with adds and deletes marked
		Vector diffVct = diff.diffVE();
		abr.addDebug(" diffVE info:\n"+diff.getDebug());
		abr.addDebug(" diffVE flattened VE: "+diffVct);

		runTime = System.currentTimeMillis();
		abr.addDebug(" Time for diff: "+Stopwatch.format(runTime-lastTime));
		lastTime=runTime;

		// group all changes by entitytype, except for root entity
		Hashtable diffTbl = new Hashtable();
		boolean chgsFnd = false;
		for (int x=0; x<diffVct.size(); x++){
			DiffEntity de = (DiffEntity)diffVct.elementAt(x);
			// keep track of any changes
			chgsFnd = chgsFnd || de.isChanged();
		}
		if (chgsFnd && abr.fullMode() && ! abr.isPeriodicABR()){
			
			profileT1.setValOnEffOn(m_strEpoch, m_strEpoch);
			EntityList listT1full = abr.getEntityListForDiff(profileT1, getVeName(),rootEntity);
		    DiffVE diffvefull = new DiffVE( listT1full,listT2, hshMap);
		    diffvefull.setCheckAllNLS(true);

			abr.addDebug("hshMapfull: "+hshMap);
			abr.addDebug("time1full flattened VE: "+diffvefull.getPriorDiffVE());
			abr.addDebug("time2full flattened VE: "+diffvefull.getCurrentDiffVE());

			// merge time1 and time2 flattened VEs into one with adds and deletes marked
			Vector diffVctfull = diffvefull.diffVE();
			abr.addDebug(" diffVctfull info:\n"+diffvefull.getDebug());
			abr.addDebug(" diffVctfull flattened VE: "+diffVctfull);

			// group all changes by entitytype, except for root entity
			for (int x=0; x<diffVctfull.size(); x++){
				DiffEntity de = (DiffEntity)diffVctfull.elementAt(x);
			    //abr.addDebug(" de.isChanged(): "+de.isChanged()+" "+de.toString());

				// must be able to find up and down links from a diffentity
				diffTbl.put(de.getKey(), de);

				String type = de.getEntityType();
				if (de.isRoot()){
					type = "ROOT";
				}
				Vector vct = (Vector)diffTbl.get(type);
				if (vct==null){
					vct = new Vector();
					diffTbl.put(type, vct);
				}
				vct.add(de);
			}
			for (int i=0; i< listT2.getEntityGroupCount(); i++){
				String type = listT2.getEntityGroup(i).getEntityType();
				Vector vct = (Vector)diffTbl.get(type);
				if (vct==null){
					vct = new Vector();
					diffTbl.put(type, vct);
				}
			}

			Vector mqVct = getMQPropertiesFN();
			if (mqVct==null){
				abr.addDebug("No MQ properties files, nothing will be generated.");
				//NOT_REQUIRED = Not Required for {0}.
				abr.addXMLGenMsg("NOT_REQUIRED", rootEntity.getKey());
			}else{
				String xml = generateXML(abr, diffTbl);
				if (notifyNow && xml != null){
					abr.notify(this, rootEntity.getKey(), xml);
				}
			}
			
			listT1full.dereference();
			diffVctfull.clear();	
			diffvefull.dereference();
			
		} else if (chgsFnd){
			for (int x=0; x<diffVct.size(); x++){
				DiffEntity de = (DiffEntity)diffVct.elementAt(x);
				// keep track of any changes
			    //abr.addDebug(" de.isChanged(): "+de.isChanged()+" "+de.toString());

				// must be able to find up and down links from a diffentity
				diffTbl.put(de.getKey(), de);

				String type = de.getEntityType();
				if (de.isRoot()){
					type = "ROOT";
				}
				Vector vct = (Vector)diffTbl.get(type);
				if (vct==null){
					vct = new Vector();
					diffTbl.put(type, vct);
				}
				vct.add(de);
			}
			// make sure there is a vector for each entitygroup in the extract
			for (int i=0; i< listT2.getEntityGroupCount(); i++){
				String type = listT2.getEntityGroup(i).getEntityType();
				Vector vct = (Vector)diffTbl.get(type);
				if (vct==null){
					vct = new Vector();
					diffTbl.put(type, vct);
				}
			}

			Vector mqVct = getMQPropertiesFN();
			if (mqVct==null){
				abr.addDebug("No MQ properties files, nothing will be generated.");
				//NOT_REQUIRED = Not Required for {0}.
				abr.addXMLGenMsg("NOT_REQUIRED", rootEntity.getKey());
			}else{
				String xml = generateXML(abr, diffTbl);
				if (notifyNow && xml != null){
					abr.notify(this, rootEntity.getKey(), xml);
				}
			}
		}else{
			//NO_CHANGES_FND=No Changes found for {0}
			abr.addXMLGenMsg("NO_CHANGES_FND",rootEntity.getKey());
		}

		// release memory
		listT1.dereference();
		listT2.dereference();
		hshMap.clear();
		diffVct.clear();
		diff.dereference();
		
		for (Enumeration eNum = diffTbl.elements(); eNum.hasMoreElements();)  {
			Object obj = eNum.nextElement();
			if(obj instanceof Vector){
				((Vector)obj).clear();
			}
		}
		diffTbl.clear();
    }

    /***********************************************
    *  Get the xml
    *
    *@return java.lang.String
    */
    protected String generateXML(ADSABRSTATUS abr, Hashtable diffTbl)
    throws
    java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException,
    ParserConfigurationException,
    java.rmi.RemoteException,
    COM.ibm.eannounce.objects.EANBusinessRuleException,
    COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
    IOException,
    javax.xml.transform.TransformerException
    {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.newDocument();  // Create
		XMLElem xmlMap = getXMLMap(); // get list of xml elements

		Element root = null;

		StringBuffer debugSb = new StringBuffer();
		xmlMap.addElements(abr.getDB(),diffTbl, document, root,null,debugSb);

		abr.addDebug("GenXML debug: "+ADSABRSTATUS.NEWLINE+debugSb.toString());
		String xml = abr.transformXML(this, document);
		abr.addDebug("Generated MQ xml:"+ADSABRSTATUS.NEWLINE+xml+ADSABRSTATUS.NEWLINE);

		return xml;
	}
    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getVersion()    {
        return "1.7";
    }
}
