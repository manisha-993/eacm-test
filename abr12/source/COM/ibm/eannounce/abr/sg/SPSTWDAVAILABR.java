/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *   Module Name: SPSTWDAVAIL.java
 *
 *   Copyright  : COPYRIGHT IBM CORPORATION, 2013
 *                LICENSED MATERIAL - PROGRAM PROPERTY OF IBM
 *                REFER TO COPYRIGHT INSTRUCTION FORM#G120-2083
 *                RESTRICTED MATERIALS OF IBM
 *                IBM CONFIDENTIAL
 *
 *   Version: 1.0
 *
 *   Functional Description: 
 *
 *   Component : 
 *   Author(s) Name(s): Will
 *   Date of Creation: Nov 22, 2013
 *   Languages/APIs Used: Java
 *   Compiler/JDK Used: JDK 1.3, 1.4
 *   Production Operating System: AIX 4.x, Windows
 *   Production Dependencies: JDK 1.3 or greater
 *
 *   Change History:
 *   Author(s)     Date	        Change #    Description
 *   -----------   ----------   ---------   ---------------------------------------------
 *   Will   Nov 22, 2013     RQ          Initial code 
 *   
 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

package COM.ibm.eannounce.abr.sg;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Vector;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import COM.ibm.eannounce.objects.EANBusinessRuleException;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.LinkActionItem;
import COM.ibm.eannounce.objects.WorkflowException;
import COM.ibm.opicmpdh.middleware.LockException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;

//$Log: SPSTWDAVAILABR.java,v $
//Revision 1.5  2014/07/09 03:05:14  liuweimi
//Fix out of memory issue
//
//Revision 1.4  2014/02/18 07:48:59  liuweimi
//change based on BH FS Inbound Feed SPST20140120.doc.
//Mapping updates for a few items and default values.
//Add TAXCATG relator to service pacs.Check mapping for more details.
//Create new AVAIL existing SEOs/MODELs for the different set of countries.
//Check if a LSEO exists for the incoming bundle SEOID. If it does, fail the xml for that particular SEOID.
//Set LSEOQTY attribute on LSEOBUNDLELSEO relator. When creating LSEOBUNDLELSEO relator, set LSEOQTY attribute to 1
//
//Revision 1.3  2014/01/07 14:55:15  liuweimi
//3 Open issues - 1. If the first avail fails, continue to process other avails in the xml. This doesn't refer to invalid flag codes or invalid xml format
//2. Check if a LSEO exists for the incoming bundle SEOID. If it does, fail the xml for that particular SEOID
//3. Set LSEOQTY attribute on LSEOBUNDLELSEO relator. When creating LSEOBUNDLELSEO relator, set LSEOQTY attribute to 1
//

/**
 * Steps 1 to 3 below are for a model. Repeat these steps for LSEOs and LSEOBUNDLEs if they exist in the XML.

	1.Search for MODEL using:
	•	<MODEL.MACHTYPEATR> and <MODEL.MODELATR>
	•	Update MODEL. WTHDRWEFFCTVDATE to the Withdraw Date provided by SPST based on XML mapping SS.
	
	2.Create AVAIL
	The parent is the MODEL in step 1.Create AVAIL entity as follows.The attributes of the AVAIL  including default values are supplied via the XML shown in the SS.
	Use the derive COMNAME value based in the SS.
	
	3.Link AVAIL to MODEL
	Link the MODEL in step 1 to the newly created AVAIL in step 2. 
	Create Report
	
	4. A report is created and submitted to Subscription/Notification as follows:
	
	Last Order Avail is created and the MODELs, LSEOs & LSEOBUNDLEs are linked to this Avail.
	 
	Userid:
	Role:
	Workgroup:
	Date:
	Description: Last Order Avail Created - AVAIL
	Return code:
	
	<COMNAME> created for
	<COUNTRY>
	
	And linked to 
	
	<MT> <MODEL>, 
	<SEOID>, <BDLSEOID> as referenced



 * @author Will
 *
 */
public class SPSTWDAVAILABR extends SPSTABR {
	
	public void validateData(SPSTABRSTATUS theAbr, Element rootElem)
			throws SQLException, MiddlewareException,
			MiddlewareShutdownInProgressException {
		super.validateData(theAbr, rootElem);
	}

	public String getVersion() {
		return "1.0";
	}

	public String getTitle() {
		return "Last Order Avail is created and the MODELs, LSEOs & LSEOBUNDLEs are linked to this Avail.";
	}

	public String getDescription() {
		return "Last Order Avail Created - AVAIL";
	}

	
	protected void checkRelatedEntities(Element availElem) throws SQLException, MiddlewareException,
			MiddlewareShutdownInProgressException {
		return;//no checking for this
	}

	/**
	 * Steps 1 to 3 below are for a model. Repeat these steps for LSEOs and LSEOBUNDLEs if they exist in the XML.

		1.Search for MODEL using:
		•	<MODEL.MACHTYPEATR> and <MODEL.MODELATR>
		•	Update MODEL. WTHDRWEFFCTVDATE to the Withdraw Date provided by SPST based on XML mapping SS.
		
		
		2.Create AVAIL
		The parent is the MODEL in step 1.Create AVAIL entity as follows.The attributes of the AVAIL  including default values are supplied via the XML shown in the SS.
		Use the derive COMNAME value based in the SS.
		
		
		3.Link AVAIL to MODEL
		Link the MODEL in step 1 to the newly created AVAIL in step 2. 
 
	 */
	protected void generateEntities(Vector availattcodeVct,
			Hashtable availattValtab, Element availElem, StringBuffer availCntySb)
			throws MiddlewareRequestException, SQLException,
			MiddlewareException, EANBusinessRuleException, RemoteException,
			MiddlewareShutdownInProgressException, LockException,
			WorkflowException {
		EntityItem availitem = null;
		String effdate = (String)availattValtab.get("EFFECTIVEDATE");//get it for setting withdraw date for model, lseo and bundle
		NodeList modelList =availElem.getElementsByTagName("MODELLIST");//MODELIST
		//					<1..N>	<MODELELEMENT>		5	
		StringBuffer modelmsg = new StringBuffer();
		StringBuffer seomsg = new StringBuffer();
		StringBuffer bundlemsg = new StringBuffer();
		for (int f=0; f<modelList.getLength(); f++){
			Node modelNode = modelList.item(f);
			if (modelNode.getNodeType()!=Node.ELEMENT_NODE){							
				continue;
			}						
			NodeList modelElems = ((Element)modelNode).getElementsByTagName("MODELELEMENT");//MODEL
			for(int g=0;g<modelElems.getLength();g++){
				Node modelelemNode = modelElems.item(g);
				if(modelelemNode.getNodeType()!=Node.ELEMENT_NODE){
					continue;
				}
				Element modelElem = (Element)modelelemNode;
				String machtype = spstAbr.getNodeValue(modelElem, "MACHTYPE", false);
				String modelatr = spstAbr.getNodeValue(modelElem,"MODELATR", false);
				
				EntityItem modelitem = this.searchForModel(machtype, modelatr);

				if(modelitem !=null){
					if(isValidNewAvail(modelitem,(Vector) availattValtab.get("COUNTRYLIST"), "149")){
						LinkActionItem modelavaillink = new LinkActionItem(null, spstAbr.getDatabase(),spstAbr.getProfile(),MODELAVAIL_LINK_ACTION);
						//create avail based on model, if the avail has been created, just do the reference
						availitem = createOrRefAvail(modelitem, availitem, MODELAVAIL_CREATE_ACTION, availattcodeVct, availattValtab, 
								modelavaillink);
						spstAbr.setTextValue(spstAbr.getProfile(), "WTHDRWEFFCTVDATE", effdate,modelitem);
						spstAbr.setTextValue(spstAbr.getProfile(), "WITHDRAWDATE", effdate,modelitem);					
						modelmsg.append(" " + machtype + modelatr + " ");
					}else{
						spstAbr.addError("ERROR_EXIST_AVAIL",new String[]{"MODEL-"+machtype+modelatr});//TODO Fail the ABR
					}
				}else{
					spstAbr.addError("ERROR_MODEL_NOT_FOUND",new String[]{machtype,modelatr});//Fail the ABR
				}
				modelitem = null;
				String seoid = spstAbr.getNodeValue((Element)modelElem, "SEOID", false);
				EntityItem lseo = this.searchForLSEO(seoid);
				if(lseo != null){							
					if(isValidNewAvail(lseo,(Vector) availattValtab.get("COUNTRYLIST"), "149")){
						LinkActionItem lseolavaillink = new LinkActionItem(null, spstAbr.getDatabase(),spstAbr.getProfile(),LSEOAVAIL_LINK_ACTION);
						availitem = createOrRefAvail(lseo, availitem, LSEOAVAIL_CREATE_ACTION, availattcodeVct, availattValtab, lseolavaillink);
						spstAbr.setTextValue(spstAbr.getProfile(), "LSEOUNPUBDATEMTRGT", effdate,lseo);//TODO LSEOUNPUBDATEMTRGT?
						seomsg.append(" " + seoid + " ");
					}else{
						spstAbr.addError("ERROR_EXIST_AVAIL",new String[]{"LSEO-" + seoid});//TODO Fail the ABR
					}
				}else{					
					spstAbr.addError("ERROR_LSEO_NOT_FOUND2",new String[]{seoid});//Fail the ABR
				}
				lseo = null;
			}
		}
//		1	<BUNDLELIST>		4	
		NodeList bundleList =availElem.getElementsByTagName("BUNDLELIST");//BUNDLELIST
		for (int f=0; f<bundleList.getLength(); f++){
			Node bundlelNode = bundleList.item(f);
			if (bundlelNode.getNodeType()!=Node.ELEMENT_NODE){							
				continue;
			}						
			NodeList bundleElems = ((Element)bundlelNode).getChildNodes();
			for(int g=0;g<bundleElems.getLength();g++){
				Node bundleelemNode = bundleElems.item(g);
				if(bundleelemNode.getNodeType()!=Node.ELEMENT_NODE){
					continue;
				}
				Element bundleElem = (Element)bundleelemNode;
				String seoid = spstAbr.getNodeValue(bundleElem, "BDLSEOID", false);
				EntityItem bundle = this.searchForLSEOBUNDLE(seoid);
				if(bundle != null){
					if(isValidNewAvail(bundle,(Vector) availattValtab.get("COUNTRYLIST"), "149")){
						LinkActionItem lai = new LinkActionItem(null,spstAbr.getDatabase(),spstAbr.getProfile(),LSEOBUNDLEAVAIL_LINK_ACTION);
						availitem = this.createOrRefAvail(bundle, availitem, LSEOBUNDLEAVAIL_CREATE_ACTION, availattcodeVct, availattValtab, lai);
						spstAbr.setTextValue(spstAbr.getProfile(), "BUNDLUNPUBDATEMTRGT", effdate,bundle);//TODO BUNDLUNPUBDATEMTRGT?
						bundlemsg.append(" " + seoid + " ");
					}else{
						spstAbr.addError("ERROR_EXIST_AVAIL",new String[]{"LSEOBUNDLE-"+seoid});//TODO Fail the ABR
					}
				}else{
					spstAbr.addError("ERROR_LSEOBUNDLE_NOT_FOUND",new String[]{seoid});//Fail the ABR
				}
				bundle = null;
			}
		}
//		CREATED_WD_AVAIL = AVAIL - {0} created for <br />  &nbsp;&nbsp;&nbsp;country:{1}<br />&nbsp;&nbsp;&nbsp;
//		And linked to <br />&nbsp;&nbsp;&nbsp;
//		MODEL - {2} <br />&nbsp;&nbsp;&nbsp;
//		LSEO - {3}<br />&nbsp;&nbsp;&nbsp;
//		LSEOBUNDLE - {4} as referenced
		String modelstr = modelmsg.toString();
		if(modelstr.length()>0){
			modelstr = "<br />&nbsp;&nbsp;&nbsp;MODEL - " + modelstr;
		}
		String seostr = seomsg.toString();
		if(seostr.length()>0){
			seostr = "<br />&nbsp;&nbsp;&nbsp;LSEO - " + seostr;
		}
		String bundlestr = bundlemsg.toString();
		if(bundlestr.length() > 0){//sometimes bundle will not exiest in the xml
			bundlestr = "<br />&nbsp;&nbsp;&nbsp;LSEOBUNDLE -" + bundlestr;
		}
		if(availitem!=null)
			spstAbr.addOutput("CREATED_WD_AVAIL", new String[]{
					(String)availattValtab.get("COMNAME"),availCntySb.toString(),modelstr,
					seostr,bundlestr});
		availitem=null;
	}
}
