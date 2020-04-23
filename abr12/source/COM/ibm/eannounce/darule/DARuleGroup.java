//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2011  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.darule;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Hashtable;

import java.util.Vector;

import COM.ibm.eannounce.abr.util.ABRUtil;

import COM.ibm.eannounce.objects.DeleteActionItem;
import COM.ibm.eannounce.objects.EANBusinessRuleException;
import COM.ibm.eannounce.objects.EANMetaAttribute;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EntityList;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.LockException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.Profile;

import com.ibm.transform.oim.eacm.util.PokUtils;


/*********************************
 * 
 * One for each DARULE DAATTRIBUTECODE
 * 
 * E.	Sequence
 * A derived Entity Type Attribute Code may have multiple Attribute Derivation Rules. This is an integer value 
 * that indicates the sequence that the Rules should be applied
 * 
Knows how to build/update the CATDATA for its rule.
Does a create/link or update CATDATA ->assumption that EntityItem passed in is part of an extract with a CATDATA group
(create/link must add the new CATDATA to the EntityGroup so it can be dereferenced)
Multiple DARULEs will be ordered by sequence and executed in that order.
It must know the create action name.

DARULE  Entity  Attribute Derivation Rule

DARULE  ATTRDERIVEABRSTATUS A   Attribute Derivation ABR Status
DARULE  COMMENTS    L   Comments
DARULE  DAATTRIBUTECODE U   Attribute
DARULE  DAENTITYTYPE    U   Entity Type
DARULE  DALIFECYCLE S   Life Cycle
DARULE  DARULETYPE  U   Rule Type
DARULE  PDHDOMAIN   F   Domains
DARULE  RULEFAIL    L   Fail Results
DARULE  RULEMULTIPLE    T   Rule Concatenation String
DARULE  RULEPASS    L   Pass Results
DARULE  RULESEQ T   Sequence
DARULE  RULETEST    T   Test

 */
// $Log: DARuleGroup.java,v $
// Revision 1.9  2011/07/13 19:44:03  wendy
// added to error msg for idl
//
// Revision 1.8  2011/06/06 16:53:59  wendy
// Added information to error message
//
// Revision 1.7  2011/04/27 12:10:46  wendy
// add root info to truncation warning for idl
//
// Revision 1.6  2011/04/25 20:11:50  wendy
// Expire cache if DARULE that was part of cache was modified
//
// Revision 1.5  2011/04/14 01:15:45  wendy
// Added delete CATDATA if it existed and newly derived value was empty
// also truncate if value> maxlen
//
// Revision 1.4  2011/04/08 17:13:58  wendy
// Check for more than 1 Ready in IDL
//
// Revision 1.3  2011/03/23 14:44:27  wendy
// remove asis ruletype
//
// Revision 1.2  2011/03/21 18:47:57  wendy
// Deref createlist
//
// Revision 1.1  2011/03/15 21:12:10  wendy
// Init for BH FS ABR Catalog Attr Derivation 20110221b.doc
//
public class DARuleGroup implements Serializable {
	private static final long serialVersionUID = 1L;
	private String relatorType = null;
	private String createActionName = null;
	private String attributeCode = null;
	private Vector daRuleVct = null;  // must allow for multiple DARULE entitys for a single attrcode

	/*
FEATURECATDATA	FEATURE	CATDATA
LSEOBUNDLECATDATA	LSEOBUNDLE	CATDATA
MODELCATDATA	MODEL	CATDATA
SVCMODCATDATA	SVCMOD	CATDATA
WWSEOCATDATA	WWSEO	CATDATA
SWFEATURECATDATA	SWFEATURE	CATDATA
LSEOCATDATA	LSEO	CATDATA
	 */
	protected static final Hashtable CATREL_TBL;
	static {
		CATREL_TBL = new Hashtable(); 
		CATREL_TBL.put("FEATURE", "FEATURECATDATA");
		CATREL_TBL.put("MODEL", "MODELCATDATA");
		CATREL_TBL.put("WWSEO", "WWSEOCATDATA");
		CATREL_TBL.put("SVCMOD", "SVCMODCATDATA");
		CATREL_TBL.put("LSEO", "LSEOCATDATA");
		CATREL_TBL.put("LSEOBUNDLE", "LSEOBUNDLECATDATA");
		CATREL_TBL.put("SWFEATURE", "SWFEATURECATDATA");
	}
	private static final Hashtable CATACTION_TBL;
    static {
    	CATACTION_TBL = new Hashtable(); 
    	CATACTION_TBL.put("FEATURE", "CRFEATCATDATA");
    	CATACTION_TBL.put("MODEL", "CRMDLCATDATA");
    	CATACTION_TBL.put("WWSEO", "CRWWSEOCATDATA");
    	CATACTION_TBL.put("SVCMOD", "CRSVCMCATDATA");
    	CATACTION_TBL.put("LSEO", "CRLSEOCATDATA");
    	CATACTION_TBL.put("LSEOBUNDLE", "CRLSEOBCATDATA");
    	CATACTION_TBL.put("SWFEATURE", "CRSWFCATDATA");
    }
    
	private static final String DELETEACTION_NAME="DELCATDATA"; 
  
	/**
	 * constructor for IDL, will not be cached
	 * @param db
	 * @param prof
	 * @param entityType
	 * @param attrCode
	 * @param debugSb
	 * @throws Exception
	 */
	protected DARuleGroup(Database db, Profile prof,String entityType, String attrCode, 
			StringBuffer debugSb) 
	throws Exception
	{
		this(attrCode,entityType);
		
		// search for DARules for this entitytype
		EntityItem eia[] = DARuleEngine.searchForDARules(db, prof,entityType,attrCode,debugSb);
		if(eia !=null){
			// look for any invalid lifecycle or sequence
			verifyDARules(eia, debugSb);
			
			//find executable rules for this attributecode
			for(int i=0; i<eia.length; i++){
				EntityItem daruleItem = eia[i];
				String ruletype = PokUtils.getAttributeFlagValue(daruleItem, "DARULETYPE");
				String lifecycle = PokUtils.getAttributeFlagValue(daruleItem, "DALIFECYCLE");
				DARuleEngineMgr.addDebugComment(D.EBUG_DETAIL,debugSb,"DARuleGroup:["+i+"] "+daruleItem.getKey()+" ruletype "+
						ruletype+" lifecycle "+lifecycle);

				if(DARuleEngine.DALIFECYCLE_Ready.equals(lifecycle) ||
						DARuleEngine.DALIFECYCLE_Draft.equals(lifecycle)||
						DARuleEngine.DALIFECYCLE_Change.equals(lifecycle)){
					DARuleItem darItem  = null;
					if(DARuleEngine.DARULETYPE_Substitution.equals(ruletype)){	//DARULETYPE  20  Substitution
						darItem = new DARuleSubstitution(daruleItem);
					}else if(DARuleEngine.DARULETYPE_ScanReplace.equals(ruletype)){ 	//DARULETYPE  30  ScanReplace
						darItem = new DARuleScanReplace(daruleItem);
					}else if(DARuleEngine.DARULETYPE_Equation.equals(ruletype)){	//DARULETYPE  40  Equation
						darItem = new DARuleEquation(daruleItem);
					}
					if(darItem!=null){
						addRuleItem(darItem);
					}else{
						DARuleEngineMgr.addDebugComment(D.EBUG_ERR,debugSb,"DARuleGroup: "+daruleItem.getKey()+" ruletype "+ruletype+" is not supported");
					}
				}else{
					DARuleEngineMgr.addDebugComment(D.EBUG_WARN,debugSb,"DARuleGroup: "+daruleItem.getKey()+" lifecycle "+lifecycle+" is not used for IDL");
				}
			}
		}
	}
    /**
     * NOTE: only used for IDL 
     * 
     * 	A derived Entity Type Attribute Code may have multiple Attribute Derivation Rules that are applied in a sequence;
     * however, there is a single definition. For example, there cannot be two DARULEs with a Sequence = 0 or empty
     * for the same Entity Attribute. Sequence is an integer value that indicates the sequence that the Rules should
     * be applied.
     * 
     *  2.	If any of the DARULEs have DALIFECYCLE of Production, then this is an error
     * @param eia
     * @param debugSb 
     * @throws InvalidDARuleException 
     */
    private void verifyDARules(EntityItem eia[], StringBuffer debugSb) throws InvalidDARuleException
    {
    	Vector errvct = null;
    	EntityItem daruleSeq0=null;
    	EntityItem daruleReady=null;
    	StringBuffer sb = new StringBuffer("Invalid DARULE found:");
		for(int i=0; i<eia.length; i++){
			EntityItem daruleItem = eia[i];
			String sequence = PokUtils.getAttributeValue(daruleItem, "RULESEQ", "", "0", false);
			String lifecycle = PokUtils.getAttributeFlagValue(daruleItem, "DALIFECYCLE");
			DARuleEngineMgr.addDebugComment(D.EBUG_DETAIL,debugSb,"verifyDARules: "+daruleItem.getKey()+
					" sequence "+sequence+" lifecycle "+lifecycle);
			
			if(DARuleEngine.DALIFECYCLE_Production.equals(lifecycle)){
				if(errvct==null){
					errvct = new Vector();
				}
				sb.append(" "+daruleItem.getKey()+" DALIFECYCLE is Production.");
				errvct.add(daruleItem);
				continue;
			}
			if(DARuleEngine.DALIFECYCLE_Retire.equals(lifecycle)){
				if(errvct==null){
					errvct = new Vector();
				}
				sb.append(" "+daruleItem.getKey()+" DALIFECYCLE is Retire.");
				errvct.add(daruleItem);
				continue;
			}
			// only one in the set can be 'Ready'
			if(DARuleEngine.DALIFECYCLE_Ready.equals(lifecycle)){
				if(daruleReady!=null){
					if(errvct==null){
						errvct = new Vector();
					}
					errvct.add(daruleItem);
					sb.append(" "+daruleItem.getKey()+" DALIFECYCLE is Ready.");
					if(!errvct.contains(daruleReady)){
						errvct.add(daruleReady);
						sb.append(" "+daruleReady.getKey()+" DALIFECYCLE is Ready.");
					}
					continue;
				}
				daruleReady = daruleItem;
			}
			
			//ignore Obsolete
			if(DARuleEngine.DALIFECYCLE_Ready.equals(lifecycle) ||
					DARuleEngine.DALIFECYCLE_Draft.equals(lifecycle)||
					DARuleEngine.DALIFECYCLE_Change.equals(lifecycle)){
				if("0".equals(sequence)){
					if(daruleSeq0!=null){
						if(errvct==null){
							errvct = new Vector();
						}
						errvct.add(daruleItem);
						sb.append(" "+daruleReady.getKey()+" duplicate Sequence 0 or empty.");
						if(!errvct.contains(daruleSeq0)){
							errvct.add(daruleSeq0);
							sb.append(" "+daruleSeq0.getKey()+" duplicate Sequence 0 or empty.");
						}
					}
					daruleSeq0 = daruleItem;
				}
			}
		}
		
		if(errvct!=null){
			throw new InvalidDARuleException(sb.toString(),errvct);
		}
    }
	/**
	 * constructor 
	 * 
	 * @param attrCode - flag value
	 * @param entityType - description like FEATURE, not flag code
	 */
	protected DARuleGroup(String attrCode, String entityType){
		attributeCode = attrCode;
		relatorType = CATREL_TBL.get(entityType).toString();	
		createActionName = CATACTION_TBL.get(entityType).toString();
	}
	 
	/**
	 * some attribute codes have multiple rule items
	 * @param dar
	 */
	protected void addRuleItem(DARuleItem dar){
		if(daRuleVct==null){
			daRuleVct = new Vector();
		}
		daRuleVct.add(dar);
		if(daRuleVct.size()>1){
			//sort on sequence number 
			Collections.sort(daRuleVct);
		}
	}
	
	/**
	 * update or create and link CATDATA for this rule
	 * used by RuleEngine from RuleEngineMgr for DQ ABR or workflow ABR on a specific offering entity instance
	 * not used for IDL
	 * 
	 * It derives DAATTRIBUTEVALUE for DAATTRIBUTECODE.
	 *  If the root entity has a child CATDATA for this attribute code (CATDATA.DAATTRIBUTECODE),
	 *  then
	 *  update the value if the derived value does not exactly match DAATTRIBUTEVALUE.
	 *  else
	 *  create a new instance of CATDATA for DAATRIBUTECODE and DAATTRIBUTEVALUE.
	 *  
	 * @param db
	 * @param prof
	 * @param root
	 * @param msgSb
	 * @return
	 * @throws Exception
	 */
	protected boolean updateCatData(Database db, Profile prof, EntityItem root, StringBuffer msgSb) 
	throws Exception
	{
		// get the derived value
		String derivedValue = getDerivedValue(db, prof, root,msgSb);
		//if null returned - no rules applied
		  
		//update the CATDATA	
		boolean chgsmade = doCatdataUpdate(db, prof, root, derivedValue, msgSb);

		return chgsmade;
	}

	/**
	 * update or create and link CATDATA for this DAATTRIBUTECODE
	 * called directly from IDL abr for subsets of entityitems it finds that meet the criteria
	 * 
	 * It derives DAATTRIBUTEVALUE for DAATTRIBUTECODE.
	 *  If the root entity has a child CATDATA for this attribute code (CATDATA.DAATTRIBUTECODE),
	 *  then
	 *  update the value if the derived value does not exactly match DAATTRIBUTEVALUE.
	 *  else
	 *  create a new instance of CATDATA for DAATRIBUTECODE and DAATTRIBUTEVALUE.
	 *  
	 * @param db
	 * @param prof 
	 * @param rootArray
	 * @param msgSb
	 * @param debugSb - separate buffer to stream out
	 * @return
	 * @throws Exception
	 */
	public boolean[] idlCatData(Database db, Profile prof, EntityItem rootArray[], StringBuffer msgSb,
			StringBuffer debugSb) 
	throws Exception
	{		
		// get the derived value
		String derivedValues[] = getDerivedValues(db, prof, rootArray, debugSb);
		//if null returned - no rules applied

		//update the CATDATA	
		boolean[] chgsmade = doCatdataUpdates(db, prof, rootArray, derivedValues, msgSb,debugSb);

		return chgsmade;
	}
	
	/**
	 * expose all DARULE entityItems for this entitytype and attributecode
	 * ATTRDERIVEABRSTATUS ABR must update DALIFECYCLE for all DARULEs in a set
	 * @return
	 */
	public Vector getDARULEEntitys(){
		if(daRuleVct==null){
			return null;
		}
		Vector eiVct = new Vector(daRuleVct.size());
		for(int i=0; i<daRuleVct.size(); i++){
			DARuleItem darItem = (DARuleItem)daRuleVct.elementAt(i);
			eiVct.add(darItem.getDARULEEntity());
		}
		return eiVct;
	}

	/**
	 * update or create CATDATA with the derived value
	 * @param db
	 * @param prof
	 * @param root
	 * @param derivedValue
	 * @param msgSb
	 * @return
	 * @throws Exception
	 */
	private boolean doCatdataUpdate(Database db, Profile prof, EntityItem root, String derivedValue, 
			StringBuffer msgSb) throws Exception 
	{
		boolean chgsmade = false;
		// make sure it was in the extract
		EntityList list = root.getEntityGroup().getEntityList();
		if(list==null){
			throw new DARuleException("CATDATA EntityGroup not found.  No EntityList for "+root.getKey());
		}
		EntityGroup eGrp = list.getEntityGroup("CATDATA");
		if(eGrp==null){
			throw new DARuleException("CATDATA EntityGroup not found in EntityList for "+root.getKey());
		}
		
		// truncate if exceeds maxlen
	   	int maxLen = EANMetaAttribute.TEXT_MAX_LEN;
		EANMetaAttribute metaAttr = eGrp.getMetaAttribute("DAATTRIBUTEVALUE");
		if(metaAttr!=null){
			maxLen = metaAttr.getMaxLen();
		}
		
		Vector catdataVct = PokUtils.getAllLinkedEntities(root, relatorType, "CATDATA");
		DARuleEngineMgr.addDebugComment(D.EBUG_DETAIL,msgSb,"doCatdataUpdate: "+relatorType+" catdataVct.size "+
				catdataVct.size()+" darGrpAttrcode "+attributeCode);
		
		// is there a CATADATA for this rule
		EntityItem catdata = null;
		for(int i=0; i<catdataVct.size(); i++){
			EntityItem ei = (EntityItem)catdataVct.elementAt(i);
			String attrcode = PokUtils.getAttributeFlagValue(ei, "DAATTRIBUTECODE");
			DARuleEngineMgr.addDebugComment(D.EBUG_DETAIL,msgSb,"doCatdataUpdate: "+ei.getKey()+" catDataAttrcode "+attrcode);
			if(attributeCode.equals(attrcode)){
				catdata = ei;
				break;
			}
		}
		
		if(derivedValue!=null && derivedValue.trim().length()>0){
			if(derivedValue.length()>maxLen){
				//notify user 
				DARuleEngineMgr.addInformation(msgSb, "Warning: Derived value exceeded "+maxLen+
						" characters.  It was truncated.");
				// truncate
				derivedValue = derivedValue.substring(0,maxLen-1);
			}
		}
		
		if(catdata==null){
			if(derivedValue!=null && derivedValue.trim().length()>0){
				//create and link catdata
				chgsmade = createCATDATA(db, prof, list, root, derivedValue, msgSb, msgSb);
			}
		}else{
			if(derivedValue!=null && derivedValue.trim().length()>0){
				//update catdata
				String curvalue = PokUtils.getAttributeValue(catdata, "DAATTRIBUTEVALUE", "", null, false);
				if(!derivedValue.equals(curvalue)){
					chgsmade = true;
					updateAttribute(db, catdata, derivedValue, msgSb);
					//Updated {0} for {1}
					DARuleEngineMgr.addInformation(msgSb,"Updated "+
							DARuleEngineMgr.getNavigationName(db, prof, catdata)+" for "+
							DARuleEngineMgr.getNavigationName(db, prof, root));
				}else{
					DARuleEngineMgr.addDebugComment(D.EBUG_DETAIL,msgSb," "+catdata.getKey()+" value did not change "+curvalue);
				}
			}else{
				//delete the CATDATA
				chgsmade = true;
				//Deleted {0} for {1}
				DARuleEngineMgr.addInformation(msgSb,"Deleted "+
						DARuleEngineMgr.getNavigationName(db, prof, catdata)+" for "+
						DARuleEngineMgr.getNavigationName(db, prof, root));
				deleteCatdata(db, prof, new EntityItem[]{catdata});
			}
		} 
		
		return chgsmade;
	}
	
	/**
	 * create the CATDATA and its relator, move those items to the root's entitylist
	 * @param db
	 * @param prof
	 * @param list
	 * @param root
	 * @param derivedValue
	 * @param msgSb
	 * @param debugSb
	 * @return
	 * @throws Exception
	 */
	private boolean createCATDATA(Database db, Profile prof, EntityList list, EntityItem root, String derivedValue, 
			StringBuffer msgSb, StringBuffer debugSb) throws Exception 
	{
		boolean chgsmade = false;
		//create and link catdata
		EntityItem catdata = createCATDATA(db, prof, root, derivedValue, debugSb);
		if(catdata!=null){
			chgsmade = true;
			EntityGroup eGrp = list.getEntityGroup("CATDATA");
			EntityList createList = catdata.getEntityGroup().getEntityList();
			EntityGroup createGrp = createList.getEntityGroup("CATDATA");
			eGrp.putEntityItem(catdata);
			catdata.reassign(eGrp);
			// remove it from create list
			createGrp.removeEntityItem(catdata);
			
			EntityItem catRel = (EntityItem)catdata.getUpLink(0);
			eGrp = list.getEntityGroup(relatorType);
			createGrp = createList.getEntityGroup(relatorType);
			eGrp.putEntityItem(catRel);
			catRel.reassign(eGrp);
			// remove it from create list
			createGrp.removeEntityItem(catRel);
			
			//release memory
			createList.dereference();

			//Created and referenced {0} for {1}
			DARuleEngineMgr.addInformation(msgSb,"Created and referenced "+
					DARuleEngineMgr.getNavigationName(db, prof, catdata)+" for "+
					DARuleEngineMgr.getNavigationName(db, prof, root));
		}else{
			throw new DARuleException("Unable to create CATDATA for "+
					DARuleEngineMgr.getNavigationName(db, prof, root));
		}
		
		return chgsmade;
	}
	
	/**
	 * delete the CATDATA and its relator
	 * @param db
	 * @param prof
	 * @param catdata
	 * @throws MiddlewareRequestException
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws LockException
	 * @throws MiddlewareShutdownInProgressException
	 * @throws EANBusinessRuleException
	 */
	protected static void deleteCatdata(Database db, Profile prof, EntityItem[] catdata)
	throws MiddlewareRequestException, SQLException, MiddlewareException, LockException, 
	MiddlewareShutdownInProgressException, EANBusinessRuleException
	{
    	DeleteActionItem dai = new DeleteActionItem(null, db,prof,DELETEACTION_NAME);

    	// do the delete
    	dai.setEntityItems(catdata);
    	db.executeAction(prof, dai);
	}
	/**
	 * NOTE: used for IDL
	 * @param db
	 * @param prof
	 * @param rootArray
	 * @param derivedValues
	 * @param msgSb
	 * @param debugSb
	 * @return
	 * @throws Exception
	 */
	private boolean[] doCatdataUpdates(Database db, Profile prof, EntityItem[] rootArray, String[] derivedValues, 
			StringBuffer msgSb, StringBuffer debugSb) throws Exception 
	{
		boolean[] chgsmade = new boolean[rootArray.length];
		boolean commitGrp = false;
		// make sure it was in the extract
		EntityList list = rootArray[0].getEntityGroup().getEntityList();
		if(list==null){
			throw new DARuleException("CATDATA EntityGroup not found.  No EntityList for "+
					rootArray[0].getEntityType()+".  Execution terminated.");
		}
		EntityGroup eGrp = list.getEntityGroup("CATDATA");
		if(eGrp==null){
			throw new DARuleException("CATDATA EntityGroup not found in EntityList for "+
					rootArray[0].getEntityType()+".  Execution terminated.");
		}
		
		// truncate if exceeds maxlen
	   	int maxLen = EANMetaAttribute.TEXT_MAX_LEN;
		EANMetaAttribute metaAttr = eGrp.getMetaAttribute("DAATTRIBUTEVALUE");
		if(metaAttr!=null){
			maxLen = metaAttr.getMaxLen();
		}
		
		Vector catdata2deleteVct = new Vector();

		for(int x=0; x<rootArray.length; x++){
			EntityItem root = rootArray[x];
			chgsmade[x] = false;
			String derivedValue = null;
			if(derivedValues != null){
				derivedValue = derivedValues[x];
			}
			DARuleEngineMgr.addDebugComment(D.EBUG_DETAIL,debugSb,"doCatdataUpdates: "+root.getKey()
					+" darattrcode "+attributeCode);

			if(derivedValue!=null && derivedValue.trim().length()>0){
				if(derivedValue.length()>maxLen){
					//notify user 
					DARuleEngineMgr.addInformation(msgSb, "Warning: Derived value exceeded "+maxLen+
							" characters.  It was truncated for "+DARuleEngineMgr.getNavigationName(db, prof, root)+".");
					// truncate
					derivedValue = derivedValue.substring(0,maxLen-1);
				}
			}
			
			Vector catdataVct = PokUtils.getAllLinkedEntities(root, relatorType, "CATDATA");
			DARuleEngineMgr.addDebugComment(D.EBUG_DETAIL,debugSb,"doCatdataUpdates: "+root.getKey()+" "+relatorType+" catdataVct.size "+
					catdataVct.size());

			// is there a CATADATA for this rule
			EntityItem catdata = null;
			for(int i=0; i<catdataVct.size(); i++){
				EntityItem ei = (EntityItem)catdataVct.elementAt(i);
				String attrcode = PokUtils.getAttributeFlagValue(ei, "DAATTRIBUTECODE");
				DARuleEngineMgr.addDebugComment(D.EBUG_DETAIL,debugSb,"doCatdataUpdates: "+ei.getKey()+" attrcode "+attrcode);
				if(attributeCode.equals(attrcode)){
					catdata = ei;
					break;
				}
			}

			if(catdata==null){
				if(derivedValue!=null && derivedValue.trim().length()>0){
					//create and link catdata
					chgsmade[x] = createCATDATA(db, prof, list, root, derivedValue, msgSb, debugSb);
				}
			}else{
				if(derivedValue!=null && derivedValue.trim().length()>0){
					//update existing catdata
					String curvalue = PokUtils.getAttributeValue(catdata, "DAATTRIBUTEVALUE", "", null, false);
					if(!derivedValue.equals(curvalue)){
						chgsmade[x] = true;
						commitGrp = true;
						StringBuffer debugSb2 = new StringBuffer();
						// save the attribute
						ABRUtil.setText(catdata,"DAATTRIBUTEVALUE", derivedValue, debugSb2); 
						if (debugSb2.length()>0){
							DARuleEngineMgr.addDebugComment(D.EBUG_DETAIL,debugSb,"updateAttribute "+catdata.getKey()+" value "+derivedValue);
							DARuleEngineMgr.addDebugComment(D.EBUG_DETAIL,debugSb,debugSb2.toString());
						}
						//Updated {0} for {1}
						DARuleEngineMgr.addInformation(msgSb,"Updated "+
								DARuleEngineMgr.getNavigationName(db, prof, catdata)+" for "+
								DARuleEngineMgr.getNavigationName(db, prof, root));
					}
				}else{
					//delete the CATDATA
					chgsmade[x] = true;
					//Deleted {0} for {1}
					DARuleEngineMgr.addInformation(msgSb,"Deleted "+
							DARuleEngineMgr.getNavigationName(db, prof, catdata)+" for "+
							DARuleEngineMgr.getNavigationName(db, prof, root));
					catdata2deleteVct.add(catdata);
				}
			}
		}
		
		if(commitGrp){
			// one or more existing CATDATA was updated, commit entitygroup
			eGrp.commit(db, null);
		}
		
		if(catdata2deleteVct.size()>0){
			EntityItem[] eia = new EntityItem[catdata2deleteVct.size()];
			catdata2deleteVct.copyInto(eia);
			deleteCatdata(db, prof, eia);
			catdata2deleteVct.clear();
			eia = null;
		}
		
		return chgsmade;
	}
	/**
	 * apply a subset of Attribute Derivation Rules (DARULE) based on the following criteria:
	 * 	The root entity type = DARULE.DAENTITYTYPE
	 * 	DARULE.LIFECYCLE = “Production” (???) - filtered when group was built
	 * 	The root entity type PDHDOMAIN is in DARULE.PDHDOMAIN
	 * 
	 * @param db
	 * @param prof
	 * @param rootItem
	 * @param debugSb
	 * @return
	 * @throws Exception
	 */
	private String getDerivedValue(Database db, Profile prof, EntityItem rootItem,StringBuffer debugSb) 
	throws Exception
	{
		String results = null;
		if(daRuleVct!=null){
			for(int i=0; i<daRuleVct.size(); i++){
				DARuleItem darItem = (DARuleItem)daRuleVct.elementAt(i);
				results = darItem.getDerivedValue(db, prof, rootItem, results, debugSb);
				DARuleEngineMgr.addDebugComment(D.EBUG_SPEW,debugSb,"getDerivedValue["+i+"]: "+darItem.getKey()+" results "+results);
			}
		}
		return results;
	}
	/**
	 * used for IDL to improve performance
	 * @param db
	 * @param prof
	 * @param rootItemArray
	 * @param debugSb
	 * @return
	 * @throws Exception
	 */
	private String[] getDerivedValues(Database db, Profile prof, EntityItem rootItemArray[],StringBuffer debugSb) 
	throws Exception
	{
		String results[] = null;
		if(daRuleVct!=null && rootItemArray !=null && rootItemArray.length>0){
			for(int i=0; i<daRuleVct.size(); i++){
				DARuleItem darItem = (DARuleItem)daRuleVct.elementAt(i);
				results = darItem.getDerivedValues(db, prof, rootItemArray, results, debugSb);
			}
		}
		return results;
	}
	/**
	 * release memory
	 */
	public void dereference()
	{
		if(daRuleVct!=null){
			for(int i=0; i<daRuleVct.size(); i++){
				DARuleItem darObj = (DARuleItem)daRuleVct.elementAt(i);
				darObj.dereference();
			}
			daRuleVct.clear();
			daRuleVct = null;
		}
		
		attributeCode = null;
		relatorType = null;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		StringBuffer sb = new StringBuffer("DARuleGroup: ");
		sb.append("DAATTRIBUTECODE: "+attributeCode+" \n");
		if(daRuleVct!=null){
			for(int i=0; i<daRuleVct.size(); i++){
				DARuleItem darItem = (DARuleItem)daRuleVct.elementAt(i);
				sb.append(darItem.toString()+"\n");
			}
		}else{
			sb.append("No DARULEs");
		}
		
		return sb.toString();
	}
	
	/** 
	 * update an existing CATDATA
	 * CATDATA DAATTRIBUTEVALUE    T   Attribute Value
	 * @param db
	 * @param catdataItem
	 * @param value
	 * @param debugSb
	 * @throws Exception
	 */
	private void updateAttribute(Database db,EntityItem catdataItem, String value,StringBuffer debugSb) 
	throws Exception
	{
		StringBuffer debugSb2 = new StringBuffer();
		// save the attribute
		ABRUtil.setText(catdataItem,"DAATTRIBUTEVALUE", value, debugSb2); 
		DARuleEngineMgr.addDebugComment(D.EBUG_DETAIL,debugSb,"updateAttribute "+catdataItem.getKey()+" to value "+value);
		if (debugSb2.length()>0){
			DARuleEngineMgr.addDebugComment(D.EBUG_WARN,debugSb,debugSb2.toString());
		}
		// must commit changed entity to the PDH 
		catdataItem.commit(db, null);	
	}
	
	/**
	 * create CATDATA with specified value and link the CATADATA entity to the root item
	 * @param db
	 * @param prof
	 * @param rootitem
	 * @param derivedValue
	 * @param debugSb
	 * @return
	 * @throws Exception
	 */
	private EntityItem createCATDATA(Database db, Profile prof,EntityItem rootitem, 
			String derivedValue, StringBuffer debugSb) 
	throws Exception
	{ 
		EntityItem catdata = null;
		DARuleEngineMgr.addDebugComment(D.EBUG_SPEW,debugSb,"createCATDATA  darattrcode "+attributeCode+" derivedvalue "+derivedValue);
		
		// create the var entity with project parent
		Vector attrCodeVct = new Vector();
		Hashtable attrValTbl = new Hashtable();
		attrCodeVct.addElement("DAATTRIBUTECODE");
		attrValTbl.put("DAATTRIBUTECODE", attributeCode); 
		attrCodeVct.addElement("DAATTRIBUTEVALUE");
		attrValTbl.put("DAATTRIBUTEVALUE", derivedValue); 
		
		StringBuffer debugSb2 = new StringBuffer();
		catdata = ABRUtil.createEntity(db, prof, createActionName, rootitem,  
				"CATDATA", attrCodeVct, attrValTbl, debugSb2); 
		if (debugSb2.length()>0){
			DARuleEngineMgr.addDebugComment(D.EBUG_DETAIL,debugSb,"createCATDATA "+debugSb2.toString());
		}

		attrCodeVct.clear();
		attrValTbl.clear();
		
		return catdata;
	}
    /***********************************************
     *  Get the version
     *
     *@return java.lang.String
     */
     public static String getVersion()
     {
     	return "$Revision: 1.9 $";
     }
}
