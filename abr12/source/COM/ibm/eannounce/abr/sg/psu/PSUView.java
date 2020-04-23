//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2013  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package COM.ibm.eannounce.abr.sg.psu;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import COM.ibm.opicmpdh.middleware.Stopwatch;
import COM.ibm.opicmpdh.transactions.OPICMList;
 
/**  
 * PSUCRITERIA=View, pull sorted view
 * A PDHUPDATE where PSUCRITERIA = "VIEW" provides the name of the DB2 View including the schema (i.e. schema.view) 
 * 
 * via PSUVIEW
 * 	VIEW – there may be one or more children of entity type "PDH Update Action" (PDHUPDATEACT). There will 
 *  be exactly one where PSUCLASS IN {Update | Reference}. In this case, the values are the column names that 
 *  have the values. 
 *  PSUDBTYPE (type = U) = {PDH | ODS} 
 */
//$Log: PSUView.java,v $
//Revision 1.1  2013/04/19 19:28:44  wendy
//Add PSUABRSTATUS
//
class PSUView {
	/*
PSUDBTYPE	DBTYPE1		PDH
PSUDBTYPE	DBTYPE2		ODS
	 */
	private static final String DB_PDH="DBTYPE1"; 
	
	private PSUABRSTATUS abr;
	private EntityItem rootEntity;
	private Object[] args = new String[3]; 
	 
	private String entityTypeCol;
	private String entityIdCol;
	private String attrCol;
	private String attrActionCol;
	private String attrTypeCol;
	private String attrValueCol;
	private String entityTypeRefCol;
	private String entityIdRefCol;
	private String relatorTypeCol;
	private String relatorActCol;
	
	/**
	 * @param psu
	 * @param ei
	 */
	PSUView(PSUABRSTATUS psu,EntityItem ei){
		abr = psu;
		rootEntity = ei;
	}
	
	/**
	 * execute 
	 *  
	 * @param psuUpdateActGrp
	 * @throws MiddlewareException 
	 * @throws SQLException 
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
		String viewname = PokUtils.getAttributeValue(rootEntity, "PSUVIEW", "", null, false);
		//PSUDBTYPE  (type = U) = {PDH | ODS} 
		String dbtype =  PokUtils.getAttributeFlagValue(rootEntity, "PSUDBTYPE");
		
		abr.addDebug(D.EBUG_DETAIL,"PSUView.execute: "+rootEntity.getKey()+" viewname: "+viewname+
				" dbtype: "+dbtype);
		
		if(viewname==null){ 
			//REQ_NOTPOPULATED_ERR = {0} {1} is required and does not have a value.
			args[0] = rootEntity.getEntityGroup().getLongDescription()+": "+abr.getNavigationName(rootEntity);
			args[1] = PokUtils.getAttributeDescription(rootEntity.getEntityGroup(), "PSUVIEW", "PSUVIEW");
			abr.addError("REQ_NOTPOPULATED_ERR",args);
			return;
		}
		
		if(dbtype==null){ 
			//REQ_NOTPOPULATED_ERR = {0} {1} is required and does not have a value.
			args[0] = rootEntity.getEntityGroup().getLongDescription()+": "+abr.getNavigationName(rootEntity);
			args[1] = PokUtils.getAttributeDescription(rootEntity.getEntityGroup(), "PSUDBTYPE", "PSUDBTYPE");
			abr.addError("REQ_NOTPOPULATED_ERR",args);
			return;
		}
		
		//There will be exactly one where PSUCLASS IN {Update | Reference}. In this case, the values are 
		//the column names that have the values. 
		int psuclassCnt=0;
		EntityItem psuViewItem = null;
		for(int i=0;i<psuUpdateActGrp.getEntityItemCount();i++){
			EntityItem psu = psuUpdateActGrp.getEntityItem(i);
			String psuclass = PokUtils.getAttributeFlagValue(psu, "PSUCLASS");
			abr.addDebug(D.EBUG_DETAIL,"PSUView.execute: "+psu.getKey()+" psuclass: "+psuclass);
			if(PSUABRSTATUS.PSUCLASS_Reference.equalsIgnoreCase(psuclass) || 
					PSUABRSTATUS.PSUCLASS_Update.equalsIgnoreCase(psuclass)){
				psuclassCnt++;
				psuViewItem = psu;
			}
		}
		if(psuclassCnt!=1){
			//VIEW_ERR = {0} does not have exactly one {1} where PSUCLASS IN "Update | Reference".
			args[0] = abr.getLD_Value(rootEntity, "PSUCRITERIA");
			args[1] = psuUpdateActGrp.getLongDescription();
			abr.addError("VIEW_ERR",args);
			return;
		}
		
		// get the column names
		entityTypeCol = PokUtils.getAttributeValue(psuViewItem, "PSUENTITYTYPE", "", null, false);
		entityIdCol = PokUtils.getAttributeValue(psuViewItem, "PSUENTITYID", "", null, false);
		attrCol = PokUtils.getAttributeValue(psuViewItem, "PSUATTRIBUTE", "", null, false);
		attrActionCol = PokUtils.getAttributeValue(psuViewItem, "PSUATTRACTION", "", null, false);
		attrTypeCol = PokUtils.getAttributeValue(psuViewItem, "PSUATTRTYPE", "", null, false);
		attrValueCol = PokUtils.getAttributeValue(psuViewItem, "PSUATTRVALUE", "", null, false);
		entityTypeRefCol = PokUtils.getAttributeValue(psuViewItem, "PSUENTITYTYPEREF", "", null, false);
		entityIdRefCol = PokUtils.getAttributeValue(psuViewItem, "PSUENTITYIDREF", "", null, false);
		relatorTypeCol = PokUtils.getAttributeValue(psuViewItem, "PSURELATORTYPE", "", null, false);
		relatorActCol = PokUtils.getAttributeValue(psuViewItem, "PSURELATORACTION", "", null, false);
		
		// get the class
		String psuClass = PokUtils.getAttributeFlagValue(psuViewItem, "PSUCLASS");
		if(PSUABRSTATUS.PSUCLASS_Update.equalsIgnoreCase(psuClass)){
			abr.addDebug(D.EBUG_INFO,"PSUView.execute: "+psuViewItem.getKey()+" update columns PSUENTITYTYPE: "+entityTypeCol+
					" PSUENTITYID: "+entityIdCol+" PSUATTRIBUTE: "+attrCol+" PSUATTRACTION: "+attrActionCol+
					" PSUATTRTYPE: "+attrTypeCol+" PSUATTRVALUE: "+attrValueCol);
		}else{
			abr.addDebug(D.EBUG_INFO,"PSUView.execute: "+psuViewItem.getKey()+" reference columns PSUENTITYTYPE: "+entityTypeCol+
					" PSUENTITYID: "+entityIdCol+" PSUATTRIBUTE: "+attrCol+" PSUATTRACTION: "+attrActionCol+
					" PSUATTRTYPE: "+attrTypeCol+" PSUATTRVALUE: "+attrValueCol+" PSUENTITYTYPEREF: "+entityTypeRefCol+
					" PSUENTITYIDREF: "+entityIdRefCol+" PSURELATORTYPE: "+relatorTypeCol+" PSURELATORACTION: "+
					relatorActCol);
		}
		
		if(entityTypeCol==null){
			//REQ_NOTPOPULATED_ERR = {0} {1} is required and does not have a value.
			args[0] = psuViewItem.getEntityGroup().getLongDescription()+": "+abr.getNavigationName(psuViewItem);
			args[1] = PokUtils.getAttributeDescription(psuUpdateActGrp, "PSUENTITYTYPE", "PSUENTITYTYPE");
			abr.addError("REQ_NOTPOPULATED_ERR",args);
		}
		if(entityIdCol==null){
			//REQ_NOTPOPULATED_ERR = {0} {1} is required and does not have a value.
			args[0] = psuViewItem.getEntityGroup().getLongDescription()+": "+abr.getNavigationName(psuViewItem);
			args[1] = PokUtils.getAttributeDescription(psuUpdateActGrp, "PSUENTITYID", "PSUENTITYID");
			abr.addError("REQ_NOTPOPULATED_ERR",args);
		}
		if(attrActionCol==null){ // both update and reference look at this now to create or deactivate
			//REQ_NOTPOPULATED_ERR = {0} {1} is required and does not have a value.
			args[0] = psuViewItem.getEntityGroup().getLongDescription()+": "+abr.getNavigationName(psuViewItem);
			args[1] = PokUtils.getAttributeDescription(psuUpdateActGrp, "PSUATTRACTION", "PSUATTRACTION");
			abr.addError("REQ_NOTPOPULATED_ERR",args);
		}

		if(PSUABRSTATUS.PSUCLASS_Update.equalsIgnoreCase(psuClass)){
			checkUpdateColumns(psuViewItem);
		}else if(PSUABRSTATUS.PSUCLASS_Reference.equalsIgnoreCase(psuClass)){
			checkReferenceColumns(psuViewItem);
		}
		
		if(abr.getReturnCode()== PokBaseABR.PASS){
			processView(psuViewItem, viewname,dbtype,psuClass);
		}
	}
	
	/**
	 * check the attribute columns used for update
	 * @param viewitem
	 * @return
	 * @throws MiddlewareException 
	 * @throws SQLException 
	 */
	private void checkUpdateColumns(EntityItem psuViewItem) throws SQLException, MiddlewareException{
		if(attrTypeCol==null){
			//REQ_NOTPOPULATED_ERR = {0} {1} is required and does not have a value.
			args[0] = psuViewItem.getEntityGroup().getLongDescription()+": "+abr.getNavigationName(psuViewItem);
			args[1] = PokUtils.getAttributeDescription(psuViewItem.getEntityGroup(), "PSUATTRTYPE", "PSUATTRTYPE");
			abr.addError("REQ_NOTPOPULATED_ERR",args);
		}

		if(attrCol==null){
			//REQ_NOTPOPULATED_ERR = {0} {1} is required and does not have a value.
			args[0] = psuViewItem.getEntityGroup().getLongDescription()+": "+abr.getNavigationName(psuViewItem);
			args[1] = PokUtils.getAttributeDescription(psuViewItem.getEntityGroup(), "PSUATTRIBUTE", "PSUATTRIBUTE");
			abr.addError("REQ_NOTPOPULATED_ERR",args);
		}
		/*
		 * a deactivate will not need a column for attr value
		 * if(psuAttrValue==null){
			//REQ_NOTPOPULATED_ERR = {0} {1} is required and does not have a value.
			args[0] = psuViewItem.getEntityGroup().getLongDescription()+": "+abr.getNavigationName(viewitem);
			args[1] = PokUtils.getAttributeDescription(viewitem.getEntityGroup(), "PSUATTRVALUE", "PSUATTRVALUE");
			abr.addError("REQ_NOTPOPULATED_ERR",args);
		}
		*/
	}
	/**
	 * check attributes used for reference columns
	 * @param viewitem
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private void checkReferenceColumns(EntityItem viewitem) throws SQLException, MiddlewareException{
		/*
		 * a deactivate will not need columns for these values - wont know deactivate until row is read
		 * if(psuEntityTypeRef==null){
			//REQ_NOTPOPULATED_ERR = {0} {1} is required and does not have a value.
			args[0] = viewitem.getEntityGroup().getLongDescription()+": "+abr.getNavigationName(viewitem);
			args[1] = PokUtils.getAttributeDescription(viewitem.getEntityGroup(), "PSUENTITYTYPEREF", "PSUENTITYTYPEREF");
			abr.addError("REQ_NOTPOPULATED_ERR",args);
		}
		if(psuEntityIdRef==null){
			//REQ_NOTPOPULATED_ERR = {0} {1} is required and does not have a value.
			args[0] = viewitem.getEntityGroup().getLongDescription()+": "+abr.getNavigationName(viewitem);
			args[1] = PokUtils.getAttributeDescription(viewitem.getEntityGroup(), "PSUENTITYIDREF", "PSUENTITYIDREF");
			abr.addError("REQ_NOTPOPULATED_ERR",args);
		}*/
		
		if(relatorActCol==null){ // this will hold the column for the name of the meta action
			//REQ_NOTPOPULATED_ERR = {0} {1} is required and does not have a value.
			args[0] = viewitem.getEntityGroup().getLongDescription()+": "+abr.getNavigationName(viewitem);
			args[1] = PokUtils.getAttributeDescription(viewitem.getEntityGroup(), "PSURELATORACTION", "PSURELATORACTION");
			abr.addError("REQ_NOTPOPULATED_ERR",args);
		}
		if(attrCol!=null){
			// must need attribute on the relator, check other attrs
			checkUpdateColumns(viewitem);
		}
	}
	
	private String getOrderBy(){
		// root type and id are main sort key because they are used for psuhighentitytype and highentityid
		// sort key is roottype+rootid+attraction - update or reference will have a value in attraction

		StringBuffer sb = new StringBuffer();
	
		sb.append(entityTypeCol+",");
		sb.append(entityIdCol);
		if(attrActionCol!=null){
			sb.append(","+attrActionCol);	
		}
		if(attrCol!=null){
			sb.append(","+attrCol);	
		}

		return sb.toString();
	}
	/**
	 * execute sql and make updates
	 * @param psuView
	 * @param viewname
	 * @param dbtype
	 * @param psuClass
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws RemoteException
	 * @throws MiddlewareShutdownInProgressException
	 * @throws EANBusinessRuleException
	 * @throws LockException
	 * @throws WorkflowException
	 */
	private void processView(EntityItem psuView, String viewname,String dbtype,String psuClass) throws SQLException, MiddlewareException, 
	RemoteException, MiddlewareShutdownInProgressException, EANBusinessRuleException, LockException, 
	WorkflowException
	{
		boolean allProcessed = true;
		String prevType = null;
		int prevId = -1;
		PSUUpdateData currPSUdata=null;
		
		ResultSet result=null;
		Statement ps = null;
		// this is the total number to process in one execution of this abr
		//5.	PSUMAX – Integer: is the maximum number of PSUENTITYTYPEs updated in a single invocation of this ABR. If empty, then there is not limit (Maximum).
		int psuMax = Integer.parseInt(PokUtils.getAttributeValue(rootEntity, "PSUMAX", "", ""+Integer.MAX_VALUE, false));		
		abr.addDebug(D.EBUG_DETAIL,"PSUView.processView: psuMax: "+psuMax);

		//The Select from the DB2 VIEW will need to be sorted (ordered by) in ascending order by Entity Type and Entity ID. 
		StringBuffer sqlsb = new StringBuffer("select * from "+viewname+" order by ");
		sqlsb.append(getOrderBy());
		sqlsb.append(" with ur");
		
		abr.addDebug(D.EBUG_INFO,"PSUView.processView: sql: "+sqlsb.toString());
	
		long startTime = System.currentTimeMillis();
		int rowCnt =0;
		int totalProcessed = 0;
		int deletedRelCnt = 0;
		
		// there may be multiple rows for one entity
		OPICMList needValueList = new OPICMList();
		OPICMList psuUpdateList = new OPICMList();
		Hashtable delRelTbl = new Hashtable(); // entitytype is key, value is PSUUnLinkData
		boolean allSkipped = true;

		try{
			if(dbtype.equals(DB_PDH)){
				ps = abr.getDatabase().getPDHConnection().createStatement();	
			}else{
				//use ODS
				ps = abr.getDatabase().getODSConnection().createStatement(); 
			}

			result = ps.executeQuery(sqlsb.toString());
			// rows are in sorted order
			while(result.next() && abr.getReturnCode()==PokBaseABR.PASS) {	
				rowCnt++;
				
				String psuEntityType = result.getString(entityTypeCol);
				String psuEntityIdStr = result.getString(entityIdCol);
	
				int psuEntityId = 0;
				// make sure it is all digits
				try{
					psuEntityId = Integer.parseInt(psuEntityIdStr);
				}catch(NumberFormatException nfe){
					abr.addDebug(D.EBUG_ERR,"PSUView.processView["+rowCnt+"]: Error entityTypeCol "+entityTypeCol+
							" "+psuEntityType+": entityIdCol "+entityIdCol+": "+psuEntityIdStr);
					//INVALID_FORMAT_ERR = {0} {1} is invalid.
					args[0] = entityIdCol;
					args[1] = psuEntityIdStr;
					abr.addError("INVALID_FORMAT_ERR",args);
					break;
				}
				// check to see if these types and ids were already processed in a previous execution of the abr
				if(abr.wasPreviouslyProcessed(psuEntityType, psuEntityId)){
					continue;
				}
				
				allSkipped=false;
				if(prevType==null){
					prevType = psuEntityType; // init this so neededvalues will accumulate more than 1 first time thru
				}
				
				String psuAttr = null;
				String psuAttrAction = null;
				String psuAttrType =null;
				String psuAttrValue = null;
				
				String psuEntityTypeRef = null;
				String psuEntityIdRef = null;
				String psuRelatorType = null;
				String psuRelatorAct = null;
				
				if(attrCol!=null){
					psuAttr = result.getString(attrCol);
					psuAttrType = result.getString(attrTypeCol);
					if(psuAttrType.equalsIgnoreCase("U") ||
							psuAttrType.equalsIgnoreCase("A") ||
							psuAttrType.equalsIgnoreCase("S") ||
							psuAttrType.equalsIgnoreCase("F") ||
							psuAttrType.equalsIgnoreCase("T")){}
					else{
						abr.addDebug(D.EBUG_ERR,"PSUView.processView["+rowCnt+"]: Error attrCol "+attrCol+
								" "+psuAttr+": attrTypeCol "+attrTypeCol+": "+psuAttrType);
						//NOTSUPPORTED_ERR = {0} {1} is not supported.
						args[0] = "Attribute Type";
						args[1] = psuAttrType;
						abr.addError("NOTSUPPORTED_ERR",args);
						break;
					}
				}
				if(attrActionCol!=null){
					psuAttrAction = result.getString(attrActionCol);
				}
				
				if(attrValueCol!=null){
					psuAttrValue = result.getString(attrValueCol);
				}
				
				if(PSUABRSTATUS.PSUCLASS_Reference.equalsIgnoreCase(psuClass)){
					if(entityTypeRefCol!=null){
						psuEntityTypeRef = result.getString(entityTypeRefCol);
						psuEntityIdRef = result.getString(entityIdRefCol);
					}

					if(relatorTypeCol!=null){
						psuRelatorType = result.getString(relatorTypeCol);
					}

					if(relatorActCol!=null){
						psuRelatorAct = result.getString(relatorActCol);
					}
				}
				
				if(PSUABRSTATUS.PSUCLASS_Update.equalsIgnoreCase(psuClass)){
					abr.addDebug(D.EBUG_SPEW,"update["+rowCnt+"] PSUENTITYTYPE: "+psuEntityType+
							" PSUENTITYID: "+psuEntityId+" PSUATTRIBUTE: "+psuAttr+" PSUATTRACTION: "+psuAttrAction+
							" PSUATTRTYPE: "+psuAttrType+" PSUATTRVALUE: "+psuAttrValue);
				}else{
					abr.addDebug(D.EBUG_SPEW,"reference["+rowCnt+"] PSUENTITYTYPE: "+psuEntityType+
							" PSUENTITYID: "+psuEntityId+" PSUATTRIBUTE: "+psuAttr+" PSUATTRACTION: "+psuAttrAction+
							" PSUATTRTYPE: "+psuAttrType+" PSUATTRVALUE: "+psuAttrValue+" PSUENTITYTYPEREF: "+psuEntityTypeRef+
							" PSUENTITYIDREF: "+psuEntityIdRef+" PSURELATORTYPE: "+psuRelatorType+" PSURELATORACTION: "+
							psuRelatorAct);
				}
				
				// type or id has changed, check max
				if(!psuEntityType.equals(prevType) || prevId!=psuEntityId){
					// have the max items to process been reached
					if((psuUpdateList.size()+totalProcessed)>=psuMax){
						abr.addDebug(D.EBUG_WARN,"PSUList.execute:  psuMax: "+psuMax+
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
				
				if(PSUABRSTATUS.PSUCLASS_Update.equalsIgnoreCase(psuClass)){
					if(!psuEntityType.equals(prevType) || prevId!=psuEntityId){
						// create a PSUUpdateData to hold info
						currPSUdata = new PSUUpdateData(abr,psuEntityType,psuEntityId);
						psuUpdateList.put(currPSUdata);
					}

					// this is an update action for an attribute
					// this is set action
					if(PSUABRSTATUS.PSUATTRACTION_N.equalsIgnoreCase(psuAttrAction)){
						//add this attribute to the PSUUpdateData - must get currentvalue for a deactivate, get all at once later
						abr.setAttribute(psuAttrType,currPSUdata,psuAttr,psuAttrValue);
					}else if(PSUABRSTATUS.PSUATTRACTION_D.equalsIgnoreCase(psuAttrAction)){
						//add this attribute to the PSUUpdateData - must get currentvalue for a deactivate, get all at once later
						abr.deactivateAttribute(psuAttrType,currPSUdata,psuAttr,psuAttrValue);	
						needValueList.put(currPSUdata);
					}
				}else if(PSUABRSTATUS.PSUCLASS_Reference.equalsIgnoreCase(psuClass)){
					// create relator needs type1, id1, type2, id2 and linkaction
					if(PSUABRSTATUS.PSUATTRACTION_N.equalsIgnoreCase(psuAttrAction)){
						//if parent changed
						if(!psuEntityType.equals(prevType) || prevId!=psuEntityId){
							// create a PSULinkData to hold info
							currPSUdata = new PSULinkData(abr,psuEntityType,psuEntityId);
							psuUpdateList.put(currPSUdata);
						}

						int psuEntityIdRefint = 0;
						// make sure it is all digits
						try{
							psuEntityIdRefint = Integer.parseInt(psuEntityIdRef);
						}catch(NumberFormatException nfe){
							abr.addDebug(D.EBUG_ERR,"PSUView.processView["+rowCnt+"]: Error entityTypeRefCol "+entityTypeRefCol+
									" "+psuEntityTypeRef+": entityIdRefCol "+entityIdRefCol+": "+psuEntityIdRef);
							//INVALID_FORMAT_ERR = {0} {1} is invalid.
							args[0] = entityIdRefCol;
							args[1] = psuEntityIdRef;
							abr.addError("INVALID_FORMAT_ERR",args);
							break;
						}
						
						// this is an reference action
						PSUUpdateData childData = ((PSULinkData)currPSUdata).addChild(abr,psuEntityTypeRef, 
								psuEntityIdRefint,psuRelatorAct);
						
						if(psuAttr!=null){
							abr.setAttribute(psuAttrType,childData,psuAttr,psuAttrValue);
						}
					} else if(PSUABRSTATUS.PSUATTRACTION_D.equalsIgnoreCase(psuAttrAction)){
						// must get deleterelator action, relator type and relator id
						// group all of the same entitytype
						currPSUdata = (PSUDeleteData)delRelTbl.get(psuEntityType);
						if(currPSUdata==null){
							currPSUdata = new PSUDeleteData(abr,psuEntityType);
							((PSUDeleteData)currPSUdata).addDeleteId(psuEntityId);
							((PSUDeleteData)currPSUdata).setActionName(psuRelatorAct);
							delRelTbl.put(psuEntityType,currPSUdata);
							psuUpdateList.put(currPSUdata);
						}else{
							((PSUDeleteData)currPSUdata).addDeleteId(psuEntityId);
							deletedRelCnt++;
						}
					}
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
		}finally{
			if (result!=null){
				try{
					result.close();
				}catch(SQLException sqe){}
				result=null;
			}
			if(ps!=null){
				try{
					ps.close();
				}catch(SQLException sqe){}
				ps = null;
			}
			abr.getDatabase().commit();
		}
		
		// if there were errors, clear the update vct
		if(abr.getReturnCode()!= PokBaseABR.PASS){
			while(psuUpdateList.size()>0){
				PSUUpdateData psu = (PSUUpdateData)psuUpdateList.remove(0);
				psu.dereference();
			}
	
			needValueList.removeAll();
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
				abr.doUpdates(rootEntity,psuUpdateList,allProcessed);
			}
		}

		needValueList = null;
		
		abr.addDebug(D.EBUG_SPEW,"Time to process "+rowCnt+" rows: "+Stopwatch.format(System.currentTimeMillis()-startTime));
	}
	/**
	 * release memory
	 */
	void dereference(){
		abr = null;
		rootEntity  = null;
		args = null;
		entityTypeCol = null;
		entityIdCol = null;
		attrCol = null;
		attrActionCol = null;
		attrTypeCol = null;
		attrValueCol = null;
		entityTypeRefCol = null;
		entityIdRefCol = null;
		relatorTypeCol = null;
		relatorActCol = null;
	}
}
