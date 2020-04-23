//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2013  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
package COM.ibm.eannounce.abr.sg.psu;
  
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Hashtable;

import com.ibm.transform.oim.eacm.util.PokUtils;

import COM.ibm.eannounce.abr.util.PokBaseABR;
import COM.ibm.eannounce.objects.EANBusinessRuleException;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.WorkflowException;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.LockException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.transactions.OPICMList;
  
/**
 * PSUCRITERIA=List, get all PDHUPDATEACT and sort them and process any not previously handled
 * 	LIST – the children specify everything required to define the update. There may be one more children 
 * of entity type “PDH Update Action” (PDHUPDATEACT). 
 * The list will support PSUCLASS IN {Update | Reference}.
 */
//$Log: PSUList.java,v $
//Revision 1.1  2013/04/19 19:28:43  wendy
//Add PSUABRSTATUS
//
class PSUList {
	private static final String ID_PAD="000000000000";
	private PSUABRSTATUS abr;
	private EntityItem rootEntity;
	private Object[] args = new String[3];
 
	/**
	 * @param psu
	 * @param ei
	 */
	PSUList(PSUABRSTATUS psu,EntityItem ei){
		abr = psu;
		rootEntity = ei;
	}

	/**
	 * execute -
	 * 
	 * @param psuUpdateActGrp
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws WorkflowException 
	 * @throws LockException 
	 * @throws EANBusinessRuleException 
	 * @throws MiddlewareShutdownInProgressException 
	 * @throws RemoteException 
	 */
	void execute(EntityGroup psuUpdateActGrp) throws SQLException, MiddlewareException, 
	RemoteException, MiddlewareShutdownInProgressException, EANBusinessRuleException, 
	LockException, WorkflowException 
	{
		EntityItem[] children = psuUpdateActGrp.getEntityItemsAsArray();

		if(children.length==0){
			//LIST_NO_CHILDREN_ERR = {0} Does not have any {1}.
			args[0] = abr.getLD_Value(rootEntity, "PSUCRITERIA");
			args[1] = psuUpdateActGrp.getLongDescription();
			abr.addError("LIST_NO_CHILDREN_ERR",args);
			return;
		}

		//The LIST will be sorted (ordered by) in ascending order by Entity Type and Entity ID.
		Arrays.sort(children, new java.util.Comparator(){
			public int compare(Object o1, Object o2) {
				EntityItem ei1 = (EntityItem)o1;
				EntityItem ei2 = (EntityItem)o2;
				String psu1 = getSortKey(ei1);
				String psu2 = getSortKey(ei2);
				return psu1.compareToIgnoreCase(psu2);
			}
		});

		processList(children);
	}
	
	/**
	 * get key for sorting
	 * @param ei
	 * @return
	 */
	private String getSortKey(EntityItem ei){
		// root type and id are main sort key because they are used for psuhighentitytype and highentityid
		// sort key is roottype+rootid+attraction - update or reference will have a value in attraction
		StringBuffer sb = new StringBuffer(PokUtils.getAttributeValue(ei, "PSUENTITYTYPE", "", "", false));
		// id 1267883 ends up before 83468 - must pad id
		String id = PokUtils.getAttributeValue(ei, "PSUENTITYID", "", "", false);
		if(id.length()<ID_PAD.length()){
			id=ID_PAD.substring(0, ID_PAD.length()-id.length())+id;
		}
		sb.append(id);
		sb.append(PokUtils.getAttributeValue(ei, "PSUCLASS", "", "", false)); // update or reference
		sb.append(PokUtils.getAttributeValue(ei, "PSUATTRACTION", "", "", false)); // new or delete
		sb.append(PokUtils.getAttributeValue(ei, "PSUATTRIBUTE", "", "", false)); // may not have a value
		
		return sb.toString();
	}

	/**
	 *  build updates - there may be multiple PDHUPDATEACT for one entity
	 * any previously handled types and ids are dropped
	 * 
	 * @param children
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws RemoteException
	 * @throws MiddlewareShutdownInProgressException
	 * @throws EANBusinessRuleException
	 * @throws LockException
	 * @throws WorkflowException
	 */
	private void processList(EntityItem[] children) throws SQLException, MiddlewareException, 
	RemoteException, MiddlewareShutdownInProgressException, EANBusinessRuleException, 
	LockException, WorkflowException 
	{
		int totalProcessed = 0;
		int deletedRelCnt = 0;
		String prevType = null;
		int prevId = -1;
		PSUUpdateData currPSUdata=null;
		boolean allProcessed = true;

		// this is the total number to process in one execution of this abr
		int psuMax = Integer.parseInt(PokUtils.getAttributeValue(rootEntity, "PSUMAX", "", ""+children.length, false));

		abr.addDebug(D.EBUG_DETAIL,"PSUList.processList: psuMax: "+psuMax);

		// build all list actions - there may be multiple PDHUPDATEACT for one entity
		OPICMList needValueList = new OPICMList();
		OPICMList psuUpdateList = new OPICMList();
		
		Hashtable delRelTbl = new Hashtable(); // entitytype is key, value is PSUUnLinkData
		boolean allSkipped = true;

		// children are in sorted order
		for(int i=0; i<children.length && abr.getReturnCode()==PokBaseABR.PASS; i++){
			EntityItem item = children[i];
			String psuEntityType = PokUtils.getAttributeValue(item, "PSUENTITYTYPE", "", null, false);
			String psuEntityIdStr = PokUtils.getAttributeValue(item, "PSUENTITYID", "", null, false);
			abr.addDebug(D.EBUG_SPEW,"PSUList.processList["+i+"]: "+item.getKey()+" psuEntityType: "+psuEntityType+" psuEntityId: "+psuEntityIdStr);
			if(psuEntityType==null){
				//REQ_NOTPOPULATED_ERR = {0} {1} is required and does not have a value.
				args[0] = item.getEntityGroup().getLongDescription()+": "+abr.getNavigationName(item);
				args[1] = PokUtils.getAttributeDescription(item.getEntityGroup(), "PSUENTITYTYPE", "PSUENTITYTYPE");
				abr.addError("REQ_NOTPOPULATED_ERR",args);
				break;
			}
			
			int psuEntityId = 0;
			
			if(psuEntityIdStr==null){
				//REQ_NOTPOPULATED_ERR = {0} {1} is required and does not have a value.
				args[0] = item.getEntityGroup().getLongDescription()+": "+abr.getNavigationName(item);
				args[1] = PokUtils.getAttributeDescription(item.getEntityGroup(), "PSUENTITYID", "PSUENTITYID");
				abr.addError("REQ_NOTPOPULATED_ERR",args);
				break;
			}else{
				// make sure it is all digits
				try{
					psuEntityId = Integer.parseInt(psuEntityIdStr);
				}catch(NumberFormatException nfe){
					//INVALID_FORMAT_ERR = {0} {1} is invalid.
					args[0] = item.getEntityGroup().getLongDescription()+": "+abr.getNavigationName(item);
					args[1] = abr.getLD_Value(item, "PSUENTITYID");
					abr.addError("INVALID_FORMAT_ERR",args);
					break;
				}
			}

			// check to see if these types and ids were already processed in a previous execution of the abr
			if(abr.wasPreviouslyProcessed(psuEntityType, psuEntityId)){
				continue;
			}

			allSkipped=false;
			if(prevType==null){
				prevType = psuEntityType; // init this so neededvalues will accumulate more than 1 first time thru
			}
			
			// type or id has changed, check max
			if(!psuEntityType.equals(prevType) || prevId!=psuEntityId){
				// have the max items to process been reached
				if((psuUpdateList.size()+totalProcessed)>=psuMax){
					abr.addDebug(D.EBUG_WARN,"PSUList.processList:  psuMax: "+psuMax+
					" updates has been reached, stopping processing");
					allProcessed = false;
					break;
				}
				if(psuUpdateList.size()>=PSUABRSTATUS.UPDATE_SIZE){
					totalProcessed += psuUpdateList.size()+deletedRelCnt;
					if(needValueList.size()>0){
						abr.getCurrentValues(needValueList);
						needValueList.removeAll();
					}
					delRelTbl.clear();
					deletedRelCnt = 0;
					abr.doUpdates(rootEntity,psuUpdateList,false);
				}
			}
			// verify values needed are filled in
			String psuClass = PokUtils.getAttributeFlagValue(item, "PSUCLASS");
			abr.addDebug(D.EBUG_SPEW,"PSUList.processList: "+item.getKey()+" psuClass: "+psuClass);
			if(PSUABRSTATUS.PSUCLASS_Update.equalsIgnoreCase(psuClass)){
				if(!psuEntityType.equals(prevType) || prevId!=psuEntityId){
					// create a PSUUpdateData to hold info
					currPSUdata = new PSUUpdateData(abr,psuEntityType,psuEntityId);
					psuUpdateList.put(currPSUdata);
				}

				// this is an update action for an attribute
				if(!buildListUpdate(item,currPSUdata,needValueList)){
					break;
				}
			}else if(PSUABRSTATUS.PSUCLASS_Reference.equalsIgnoreCase(psuClass)){
				String psuAttrAct = PokUtils.getAttributeValue(item, "PSUATTRACTION", "", null, false);
				String psuRelAction = PokUtils.getAttributeValue(item, "PSURELATORACTION", "", null, false);
				String psuRelType = PokUtils.getAttributeValue(item, "PSURELATORTYPE", "", null, false);
				abr.addDebug(D.EBUG_SPEW,"PSUList.processList: reference "+item.getKey()+
						" psuAttrAct: "+psuAttrAct+" psuRelAction: "+psuRelAction+" psuRelType: "+psuRelType);
				
				if(psuRelAction==null){
					//REQ_NOTPOPULATED_ERR = {0} {1} is required and does not have a value.
					args[0] = item.getEntityGroup().getLongDescription()+": "+abr.getNavigationName(item);
					args[1] = PokUtils.getAttributeDescription(item.getEntityGroup(), "PSURELATORACTION", "PSURELATORACTION");
					abr.addError("REQ_NOTPOPULATED_ERR",args);
					break;
				}

				// create relator needs type1, id1, type2, id2 and linkaction
				if(PSUABRSTATUS.PSUATTRACTION_N.equalsIgnoreCase(psuAttrAct)){
					if(psuRelType==null){ // needed to validate meta on the relatot
						//REQ_NOTPOPULATED_ERR = {0} {1} is required and does not have a value.
						args[0] = item.getEntityGroup().getLongDescription()+": "+abr.getNavigationName(item);
						args[1] = PokUtils.getAttributeDescription(item.getEntityGroup(), "PSURELATORTYPE", "PSURELATORTYPE");
						abr.addError("REQ_NOTPOPULATED_ERR",args);
						break;
					}
					//if parent changed
					if(!psuEntityType.equals(prevType) || prevId!=psuEntityId){
						// create a PSULinkData to hold info - this is the parent or root
						currPSUdata = new PSULinkData(abr,psuEntityType,psuEntityId);
						psuUpdateList.put(currPSUdata);
					}

					// this is a reference action
					if(!buildListReference(item,(PSULinkData)currPSUdata)){
						break;
					}
				} else if(PSUABRSTATUS.PSUATTRACTION_D.equalsIgnoreCase(psuAttrAct)){
					//must get deleterelator action, relator type and relator id
					// group all of the same entitytype
					currPSUdata = (PSUDeleteData)delRelTbl.get(psuEntityType);
					if(currPSUdata==null){
						currPSUdata = new PSUDeleteData(abr,psuEntityType);
						((PSUDeleteData)currPSUdata).addDeleteId(psuEntityId);
						((PSUDeleteData)currPSUdata).setActionName(psuRelAction);
						delRelTbl.put(psuEntityType,currPSUdata);
						psuUpdateList.put(currPSUdata);
					}else{
						((PSUDeleteData)currPSUdata).addDeleteId(psuEntityId);
						deletedRelCnt++;
					}
				}else{
					//REQ_NOTPOPULATED_ERR = {0} {1} is required and does not have a value.
					//NOTSUPPORTEDLIST_ERR = {0} {1} is not supported. Only {2} are supported when Criteria is List
					args[0] = item.getEntityGroup().getLongDescription()+": "+abr.getNavigationName(item);
					if(psuAttrAct==null){
						args[1] = PokUtils.getAttributeDescription(item.getEntityGroup(), "PSUATTRACTION", "PSUATTRACTION");
						abr.addError("REQ_NOTPOPULATED_ERR",args);
					}else{
						args[1] = abr.getLD_Value(item, "PSUATTRACTION");
						args[2] = PSUABRSTATUS.PSUATTRACTION_D+", "+PSUABRSTATUS.PSUATTRACTION_N;
						abr.addError("NOTSUPPORTEDLIST_ERR",args);
					}
					break;
				}
			}else{
				//REQ_NOTPOPULATED_ERR = {0} {1} is required and does not have a value.
				//NOTSUPPORTED_ERR = {0} {1} is not supported.
				args[0] = item.getEntityGroup().getLongDescription()+": "+abr.getNavigationName(item);
				if(psuClass==null){
					args[1] = PokUtils.getAttributeDescription(item.getEntityGroup(), "PSUCLASS", "PSUCLASS");
					abr.addError("REQ_NOTPOPULATED_ERR",args);
				}else{
					args[1] = abr.getLD_Value(item, "PSUCLASS");
					abr.addError("NOTSUPPORTED_ERR",args);
				}
				break;
			}

			if(!psuEntityType.equals(prevType)){
				//get any current attribute values before switching types, pull all at once
				if(needValueList.size()>0){
					abr.getCurrentValues(needValueList);
					needValueList.removeAll();
				}

				// entity type changed
				prevType = psuEntityType;
				prevId=psuEntityId;
			}else if(prevId!=psuEntityId){
				prevId=psuEntityId;
				// entity id changed
			}
		}

		// if there were errors, clear the update vct
		if(abr.getReturnCode()!= PokBaseABR.PASS){
			while(psuUpdateList.size()>0){
				PSUUpdateData psu = (PSUUpdateData)psuUpdateList.remove(0);
				psu.dereference();
			}

			needValueList.removeAll();
			delRelTbl.clear();
		}else{
			if(allSkipped){
				// tell user nothing was done
				//ALLSKIPPED = No data found to process.
				abr.addMessage("","ALLSKIPPED",null);
				allProcessed = true;
			}
			
			if(psuUpdateList.size()>0){
				// last type needs to be handled, pull all at once
				if(needValueList.size()>0){
					abr.getCurrentValues(needValueList);
					needValueList.removeAll();
				}
				delRelTbl.clear();
			}
			
			abr.doUpdates(rootEntity,psuUpdateList,allProcessed);
		}

		needValueList = null;
	}
	/**
	 * this is a list update request, make sure all attributes have a value and fill in the psudata info
	 * @param item
	 * @param currPSUdata
	 * @param needValueList - must get the current attr value for these
	 * @return true if no errors
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private boolean buildListUpdate(EntityItem item,PSUUpdateData currPSUdata,
			OPICMList needValueList) throws SQLException, MiddlewareException{
		boolean isok = true;
		
		String psuAttr = PokUtils.getAttributeValue(item, "PSUATTRIBUTE", "", null, false);
		String psuAttrAction = PokUtils.getAttributeValue(item, "PSUATTRACTION", "", null, false);
		String psuAttrType = PokUtils.getAttributeValue(item, "PSUATTRTYPE", "", null, false);
		String psuAttrValue = PokUtils.getAttributeValue(item, "PSUATTRVALUE", "", null, false);
		abr.addDebug(D.EBUG_SPEW,"PSUList.buildListUpdate: "+item.getKey()+
				" psuAttrAction: "+psuAttrAction+" psuAttr: "+psuAttr+" psuAttrType: "+psuAttrType+
				" psuAttrValue: "+psuAttrValue);

		if(psuAttr==null){
			//REQ_NOTPOPULATED_ERR = {0} {1} is required and does not have a value.
			args[0] = item.getEntityGroup().getLongDescription()+": "+abr.getNavigationName(item);
			args[1] = PokUtils.getAttributeDescription(item.getEntityGroup(), "PSUATTRIBUTE", "PSUATTRIBUTE");
			abr.addError("REQ_NOTPOPULATED_ERR",args);
			isok = false;
		}
		if(psuAttrType==null){
			//REQ_NOTPOPULATED_ERR = {0} {1} is required and does not have a value.
			args[0] = item.getEntityGroup().getLongDescription()+": "+abr.getNavigationName(item);
			args[1] = PokUtils.getAttributeDescription(item.getEntityGroup(), "PSUATTRTYPE", "PSUATTRTYPE");
			abr.addError("REQ_NOTPOPULATED_ERR",args);
			isok = false;
		}else {
			if(psuAttrType.equalsIgnoreCase("U") ||
					psuAttrType.equalsIgnoreCase("A") ||
					psuAttrType.equalsIgnoreCase("S") ||
					psuAttrType.equalsIgnoreCase("F") ||
					psuAttrType.equalsIgnoreCase("T")){}
			else{
				//NOTSUPPORTEDLIST_ERR = {0} {1} is not supported. Only {2} are supported when Criteria is List
				args[0] = item.getEntityGroup().getLongDescription()+": "+abr.getNavigationName(item);
				args[1] = abr.getLD_Value(item, "PSUATTRTYPE");
				args[2] = "U, A, S, F, T";
				abr.addError("NOTSUPPORTEDLIST_ERR",args);
				isok=false;
			}
		}

		// this is set action
		if(PSUABRSTATUS.PSUATTRACTION_N.equalsIgnoreCase(psuAttrAction)){
			if(psuAttrValue==null){
				//REQ_NOTPOPULATED_ERR = {0} {1} is required and does not have a value.
				args[0] = item.getEntityGroup().getLongDescription()+": "+abr.getNavigationName(item);
				args[1] = PokUtils.getAttributeDescription(item.getEntityGroup(), "PSUATTRVALUE", "PSUATTRVALUE");
				abr.addError("REQ_NOTPOPULATED_ERR",args);
				isok = false;
			} 

			if (isok){
				//add this attribute to the PSUUpdateData - must get currentvalue for a deactivate, get all at once later
				abr.setAttribute(psuAttrType,currPSUdata,psuAttr,psuAttrValue);
			}
		}else if(PSUABRSTATUS.PSUATTRACTION_D.equalsIgnoreCase(psuAttrAction)){
			// this is a deactivate
			if(isok){
				if(psuAttrValue==null){
					psuAttrValue="temp";
				}
				//add this attribute to the PSUUpdateData - must get currentvalue for a deactivate, get all at once later
				abr.deactivateAttribute(psuAttrType,currPSUdata,psuAttr,psuAttrValue);	
				needValueList.put(currPSUdata);
			}
		}else{
			//REQ_NOTPOPULATED_ERR = {0} {1} is required and does not have a value.
			//NOTSUPPORTEDLIST_ERR = {0} {1} is not supported. Only {2} are supported when Criteria is List
			args[0] = item.getEntityGroup().getLongDescription()+": "+abr.getNavigationName(item);
			if(psuAttrAction==null){
				args[1] = PokUtils.getAttributeDescription(item.getEntityGroup(), "PSUATTRACTION", "PSUATTRACTION");
				abr.addError("REQ_NOTPOPULATED_ERR",args);
			}else{
				args[1] = abr.getLD_Value(item, "PSUATTRACTION");
				args[2] = PSUABRSTATUS.PSUATTRACTION_D+", "+PSUABRSTATUS.PSUATTRACTION_N;
				abr.addError("NOTSUPPORTEDLIST_ERR",args);
			}

			isok = false;
		}

		return isok;
	}

	/**
	 * this is a list reference request, make sure all attributes have a value
	 * @param item
	 * @param psuData
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private boolean buildListReference(EntityItem item,PSULinkData psuData) throws SQLException, MiddlewareException{
		boolean isok = true;

		String psuEntityTypeRef = PokUtils.getAttributeValue(item, "PSUENTITYTYPEREF", "", null, false);
		String psuEntityIdRef = PokUtils.getAttributeValue(item, "PSUENTITYIDREF", "", null, false);
		String psuRelatorAct = PokUtils.getAttributeValue(item, "PSURELATORACTION", "", null, false);
		String psuAttrAct = PokUtils.getAttributeValue(item, "PSUATTRACTION", "", null, false);
		abr.addDebug(D.EBUG_SPEW,"PSUList.buildListReference: "+item.getKey()+" psuEntityTypeRef: "+psuEntityTypeRef+
				" psuEntityIdRef: "+psuEntityIdRef+" psuRelatorAct: "+psuRelatorAct+
				" psuAttrAct: "+psuAttrAct);

		// create relator needs type1, id1, type2, id2 and linkaction
		if(PSUABRSTATUS.PSUATTRACTION_N.equalsIgnoreCase(psuAttrAct)){
			// this is a new relator request
			if(psuEntityTypeRef==null){
				//REQ_NOTPOPULATED_ERR = {0} {1} is required and does not have a value.
				args[0] = item.getEntityGroup().getLongDescription()+": "+abr.getNavigationName(item);
				args[1] = PokUtils.getAttributeDescription(item.getEntityGroup(), "PSUENTITYTYPEREF", "PSUENTITYTYPEREF");
				abr.addError("REQ_NOTPOPULATED_ERR",args);
				isok = false;
			}
			int psuEntityIdRefint = 0;
			if(psuEntityIdRef==null){
				//REQ_NOTPOPULATED_ERR = {0} {1} is required and does not have a value.
				args[0] = item.getEntityGroup().getLongDescription()+": "+abr.getNavigationName(item);
				args[1] = PokUtils.getAttributeDescription(item.getEntityGroup(), "PSUENTITYIDREF", "PSUENTITYIDREF");
				abr.addError("REQ_NOTPOPULATED_ERR",args);
				isok = false;
			}else{
				// make sure it is all digits
				try{
					psuEntityIdRefint = Integer.parseInt(psuEntityIdRef);
				}catch(NumberFormatException nfe){
					//INVALID_FORMAT_ERR = {0} {1} is invalid.
					args[0] = item.getEntityGroup().getLongDescription()+": "+abr.getNavigationName(item);
					args[1] = abr.getLD_Value(item, "PSUENTITYID");
					abr.addError("INVALID_FORMAT_ERR",args);
					isok=false;
				}
			}

			if(isok){
				PSUUpdateData childData = psuData.addChild(abr,psuEntityTypeRef, psuEntityIdRefint,psuRelatorAct);

				// are these on the relator only - if so they are optional, but if attribute is specified, rest must be
				String psuAttr = PokUtils.getAttributeValue(item, "PSUATTRIBUTE", "", null, false);
				String psuAttrType = PokUtils.getAttributeValue(item, "PSUATTRTYPE", "", null, false);
				String psuAttrValue = PokUtils.getAttributeValue(item, "PSUATTRVALUE", "", null, false);
				String psuAttrAction = PokUtils.getAttributeValue(item, "PSUATTRACTION", "", null, false);

				abr.addDebug(D.EBUG_SPEW,"PSUList.buildListReference: "+item.getKey()+
						" psuAttrAction: "+psuAttrAction+" psuAttr: "+psuAttr+" psuAttrType: "+psuAttrType+
						" psuAttrValue: "+psuAttrValue);

				if(psuAttr!=null){
					// then the rest must have a value
					if(psuAttrType==null){
						//REQ_NOTPOPULATED_ERR = {0} {1} is required and does not have a value.
						args[0] = item.getEntityGroup().getLongDescription()+": "+abr.getNavigationName(item);
						args[1] = PokUtils.getAttributeDescription(item.getEntityGroup(), "PSUATTRTYPE", "PSUATTRTYPE");
						abr.addError("REQ_NOTPOPULATED_ERR",args);
						isok = false;
					}else{
						if(psuAttrType.equalsIgnoreCase("U") ||
								psuAttrType.equalsIgnoreCase("A") ||
								psuAttrType.equalsIgnoreCase("S") ||
								psuAttrType.equalsIgnoreCase("F") ||
								psuAttrType.equalsIgnoreCase("T")){}
						else{
							//NOTSUPPORTEDLIST_ERR = {0} {1} is not supported. Only {2} are supported when Criteria is List
							args[0] = item.getEntityGroup().getLongDescription()+": "+abr.getNavigationName(item);
							args[1] = abr.getLD_Value(item, "PSUATTRTYPE");
							args[2] = "U, A, S, F, T";
							abr.addError("NOTSUPPORTEDLIST_ERR",args);
							isok=false;
						}
					}
					if(psuAttrValue==null){
						//REQ_NOTPOPULATED_ERR = {0} {1} is required and does not have a value.
						args[0] = item.getEntityGroup().getLongDescription()+": "+abr.getNavigationName(item);
						args[1] = PokUtils.getAttributeDescription(item.getEntityGroup(), "PSUATTRVALUE", "PSUATTRVALUE");
						abr.addError("REQ_NOTPOPULATED_ERR",args);
						isok = false;
					} 
	
					if(isok){
						abr.setAttribute(psuAttrType,childData,psuAttr,psuAttrValue);
					}
				}
			}
		}//end new relator
		else if(PSUABRSTATUS.PSUATTRACTION_D.equalsIgnoreCase(psuAttrAct)){
			//get deleterelator action, relator type and relator id

		}

		return isok;
	}

	/**
	 * release memory
	 */
	void dereference(){
		abr = null;
		rootEntity  = null;
		args = null;
	}
}
