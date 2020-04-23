//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2011  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.darule;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Vector;

import com.ibm.transform.oim.eacm.util.PokUtils;

import COM.ibm.eannounce.abr.util.ABRUtil;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EntityList;
import COM.ibm.eannounce.objects.ExtractActionItem;

import COM.ibm.opicmpdh.middleware.Database;

import COM.ibm.opicmpdh.middleware.MiddlewareException;

import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.Profile;

/*********************************
 *  
 * One for each offering type (like FEATURE)
 * Reads DARules for its entity type (DAENTITYTYPE)
 * Creates a DARuleGroup for each DAATTRIBUTECODE with DARuleItem for each DARULE - may be multiple DARULEs
 *
 */
// $Log: DARuleEngine.java,v $
// Revision 1.6  2011/06/06 16:11:25  wendy
// make sure DAENTITYTYPE is a supported type
//
// Revision 1.5  2011/04/29 12:28:14  wendy
// Check for any existing catdata that do not have a production DARULE
//
// Revision 1.4  2011/04/25 20:11:50  wendy
// Expire cache if DARULE that was part of cache was modified
//
// Revision 1.3  2011/04/07 18:40:19  wendy
// pull extract to get all DARULE attributes
//
// Revision 1.2  2011/03/23 14:45:44  wendy
// remove asis ruletype
//
// Revision 1.1  2011/03/15 21:12:11  wendy
// Init for BH FS ABR Catalog Attr Derivation 20110221b.doc
//
public class DARuleEngine implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String DARULE_SRCHACTION_NAME = "SRDDARULE";
	private Vector daRuleVct = new Vector();
	private String entityType = null;
	private String createDTS = null;

	/*
DALIFECYCLE	10	Draft
DALIFECYCLE	20	Ready
DALIFECYCLE	30	Production
DALIFECYCLE	40	Obsolete
DALIFECYCLE	50	Change
DALIFECYCLE	60	Retire
	 */
	protected static final String DALIFECYCLE_Draft = "10";
	protected static final String DALIFECYCLE_Ready = "20";
	protected static final String DALIFECYCLE_Production = "30";
	protected static final String DALIFECYCLE_Change = "50";
	protected static final String DALIFECYCLE_Retire = "60";
		
/*
DARULETYPE  10  As Is - getting removed
DARULETYPE  20  Substitution
DARULETYPE  30  ScanReplace
DARULETYPE  40  Equation
 */	
	protected static final String DARULETYPE_Substitution = "20";
	protected static final String DARULETYPE_ScanReplace = "30";
	protected static final String DARULETYPE_Equation = "40";
	
	/*
DAENTITYTYPE	10	FEATURE
DAENTITYTYPE	20	MODEL
DAENTITYTYPE	30	WWSEO
DAENTITYTYPE	40	LSEO
DAENTITYTYPE	50	LSEOBUNDLE
DAENTITYTYPE	60	SVCMOD
DAENTITYTYPE	70	SWFEATURE
	 */
    private static final Hashtable DAENTITYTYPE_TBL;
    static {
    	DAENTITYTYPE_TBL = new Hashtable(); 
    	DAENTITYTYPE_TBL.put("FEATURE", "10");
    	DAENTITYTYPE_TBL.put("MODEL", "20");
    	DAENTITYTYPE_TBL.put("WWSEO", "30");
    	DAENTITYTYPE_TBL.put("LSEO", "40");
    	DAENTITYTYPE_TBL.put("LSEOBUNDLE", "50");
    	DAENTITYTYPE_TBL.put("SVCMOD", "60");
    	DAENTITYTYPE_TBL.put("SWFEATURE", "70");
    }
    
	/**
	 * constructor - will be cached, not used for IDL
	 * 
	 * @param db
	 * @param prof
	 * @param rootType
	 * @param msgSb
	 * @throws MiddlewareException
	 * @throws SQLException
	 * @throws MiddlewareShutdownInProgressException
	 */
	protected DARuleEngine(Database db, Profile prof, String rootType,StringBuffer msgSb) 
	throws Exception
	{
		//get all DARules for this entity type
		entityType = rootType;
		
		if(DAENTITYTYPE_TBL.get(rootType)==null){
			throw new MiddlewareException(rootType+" is not supported");
		}
		
		// set create time
	    createDTS = db.getNow(0);

		// search for DARules for this entitytype
		loadRules(db, prof, msgSb);
	}
	
	/**
	 * process each rule for this entity, all attributes
	 * NOTE: not used for IDL
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
		boolean chgsmade = false;
		String relatorType = DARuleGroup.CATREL_TBL.get(entityType).toString();
		Vector catdataVct = PokUtils.getAllLinkedEntities(root, relatorType, "CATDATA");
		if(daRuleVct.size()==0){
			DARuleEngineMgr.addDebugComment(D.EBUG_ERR,msgSb,"updateCatData: No Production DARULEs found for "+root.getEntityType());
			// did any CATDATA exist? they must be removed
			// this should never happen, the retire process should delete these CATDATA
			if(catdataVct.size()>0){
				//delete the CATDATA
				chgsmade = true;
				for(int i=0; i<catdataVct.size(); i++){
					EntityItem catdata = (EntityItem)catdataVct.elementAt(i);
					//Deleted {0} for {1}
					DARuleEngineMgr.addInformation(msgSb,"Deleted "+
							DARuleEngineMgr.getNavigationName(db, prof, catdata)+" for "+
							DARuleEngineMgr.getNavigationName(db, prof, root));
				}
				EntityItem[] catdataArray = new EntityItem[catdataVct.size()];
				catdataVct.copyInto(catdataArray);
				DARuleGroup.deleteCatdata(db, prof, catdataArray);
				
				catdataArray = null;
			}
		}else{
			Vector daattrCodesVct = new Vector();
			//process each DARuleGroup - one for each attribute
			for(int i=0; i<daRuleVct.size(); i++){
				DARuleGroup darGrp = (DARuleGroup)daRuleVct.elementAt(i);
				chgsmade = darGrp.updateCatData(db, prof, root, msgSb) || chgsmade;
				Vector daruleVct = darGrp.getDARULEEntitys();
				for (int x=0; x<daruleVct.size(); x++){
					EntityItem darule = (EntityItem)daruleVct.elementAt(x);
					String daattrCode = PokUtils.getAttributeFlagValue(darule, "DAATTRIBUTECODE");
					if(!daattrCodesVct.contains(daattrCode)){
						daattrCodesVct.add(daattrCode);
					}
				}
				daruleVct.clear();
			}
			// do any previous catdata exist for DARULEs that are no longer 'Production'
			if(catdataVct.size()>0){
				Vector deleteVct = new Vector();
				for(int i=0; i<catdataVct.size(); i++){
					EntityItem ei = (EntityItem)catdataVct.elementAt(i);
					String attrcode = PokUtils.getAttributeFlagValue(ei, "DAATTRIBUTECODE");
					if(daattrCodesVct.contains(attrcode)){
						continue;
					}
					DARuleEngineMgr.addDebugComment(D.EBUG_DETAIL,msgSb,"updateCatData: "+ei.getKey()+
							" attrcode "+attrcode+" does not have a Production DARULE");
					//delete the CATDATA
					chgsmade = true;
					deleteVct.add(ei);
				}
				//delete the CATDATA
				if(deleteVct.size()>0){
					for(int i=0; i<deleteVct.size(); i++){
						EntityItem catdata = (EntityItem)deleteVct.elementAt(i);
						//Deleted {0} for {1}
						DARuleEngineMgr.addInformation(msgSb,"Deleted "+
								DARuleEngineMgr.getNavigationName(db, prof, catdata)+" for "+
								DARuleEngineMgr.getNavigationName(db, prof, root));
					}
					EntityItem[] catdataArray = new EntityItem[deleteVct.size()];
					deleteVct.copyInto(catdataArray);
					DARuleGroup.deleteCatdata(db, prof, catdataArray);

					catdataArray = null;
					deleteVct.clear();
				}
			}
		}
		
		catdataVct.clear();
		
		return chgsmade;
	}
	
	/**
	 * get all DARULE entity ids used for this entitytype
	 * @return
	 */
	protected Vector getAllDARULEids(){
		Vector vct = new Vector();
		for(int i=0; i<daRuleVct.size(); i++){
			DARuleGroup darGrp = (DARuleGroup)daRuleVct.elementAt(i);
			Vector rulesVct = darGrp.getDARULEEntitys();
			for (int x=0; x<rulesVct.size(); x++){
				EntityItem item = (EntityItem)rulesVct.elementAt(x);
				vct.add(new Integer(item.getEntityID()));
			}
		}
		return vct;
	}
	
	/**
	 * release memory
	 */
	protected void dereference(){
		for(int i=0; i<daRuleVct.size(); i++){
			DARuleGroup darGrp = (DARuleGroup)daRuleVct.elementAt(i);
			darGrp.dereference();
		}
		daRuleVct.clear();
		daRuleVct = null;
		entityType = null;
		createDTS = null;
	}
	
	/**
	 * get the time this engine was created
	 * @return
	 */
	protected String getCreateDTS(){
		return createDTS;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		StringBuffer sb = new StringBuffer("DARuleEngine: ");
		sb.append("DAENTITYTYPE: "+entityType+" \n");
		if(daRuleVct!=null){
			for(int i=0; i<daRuleVct.size(); i++){
				DARuleGroup darGrp = (DARuleGroup)daRuleVct.elementAt(i);
				sb.append(darGrp.toString()+"\n");
			}
		}else{
			sb.append("No DARuleGroups");
		}
		
		return sb.toString();
	}
	/**
	 * load all DARULEs for this entity type - will be cached, 
	 * NOTE: not used for IDL
	 * @param db
	 * @param prof
	 * @param msgSb
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException
	 */
	private void loadRules(Database db, Profile prof, StringBuffer msgSb) 
	throws Exception
	{
		for(int i=0; i<daRuleVct.size(); i++){
			DARuleGroup darObj = (DARuleGroup)daRuleVct.elementAt(i);
			darObj.dereference();
		}
		daRuleVct.clear();
		Hashtable daRuleTbl = new Hashtable();
		// search for DARules for this entitytype
		EntityItem eia[] = searchForDARules(db, prof,entityType,null,msgSb);
		if(eia !=null){
			for(int i=0; i<eia.length; i++){
				EntityItem daruleItem = eia[i];
				String ruletype = PokUtils.getAttributeFlagValue(daruleItem, "DARULETYPE");
				String attrcode = PokUtils.getAttributeFlagValue(daruleItem, "DAATTRIBUTECODE");
				String lifecycle = PokUtils.getAttributeFlagValue(daruleItem, "DALIFECYCLE");
				DARuleEngineMgr.addDebugComment(D.EBUG_DETAIL,msgSb,"loadRules: "+daruleItem.getKey()+" ruletype "+
						ruletype+" attrcode "+attrcode+" lifecycle "+lifecycle);
	
				if(DALIFECYCLE_Production.equals(lifecycle)){
					DARuleItem darItem  = null;
					if(DARULETYPE_Substitution.equals(ruletype)){	//DARULETYPE  20  Substitution
						darItem = new DARuleSubstitution(daruleItem);
					}else if(DARULETYPE_ScanReplace.equals(ruletype)){ 	//DARULETYPE  30  ScanReplace
						darItem = new DARuleScanReplace(daruleItem);
					}else if(DARULETYPE_Equation.equals(ruletype)){	//DARULETYPE  40  Equation
						darItem = new DARuleEquation(daruleItem);
					}
					if(darItem!=null){
						DARuleGroup darGrp  = (DARuleGroup)daRuleTbl.get(attrcode);
						if(darGrp==null){
							darGrp = new DARuleGroup(attrcode,entityType);
							//E.	Sequence
							// A derived Entity Type Attribute Code may have multiple Attribute Derivation Rules. 
							//This is an integer value that indicates the sequence that the Rules should be applied
							daRuleVct.add(darGrp);
							daRuleTbl.put(attrcode, darGrp);
						}
						darGrp.addRuleItem(darItem);
					}else{
						DARuleEngineMgr.addDebugComment(D.EBUG_ERR,msgSb,"loadRules: "+daruleItem.getKey()+" ruletype "+ruletype+" is not supported");
					}
				}else{
					DARuleEngineMgr.addDebugComment(D.EBUG_DETAIL,msgSb,"loadRules: "+daruleItem.getKey()+" lifecycle "+lifecycle+" is not used for offerings");
				}
			}
		}
	}
	
	/*****************************************
	 * Search for DARULEs using:
	 * -	<DAENTITYTYPE> and possibly <DAATTRIBUTECODE>
	 *
	 * @param db
	 * @param prof
	 * @param entityType
	 * @param attrCode
	 * @param debugSb
	 * @return
	 * @throws Exception
	 */
	public static EntityItem[] searchForDARules(Database db, Profile prof,String entityType,
			String attrCode, StringBuffer debugSb)
	throws Exception
	{
		Vector attrVct = new Vector(1);
		Vector valVct = new Vector(1);
		
		if(!DAENTITYTYPE_TBL.containsKey(entityType)){
			throw new IllegalArgumentException("Unsupported DAENTITYTYPE: "+entityType);
		}
		attrVct.addElement("DAENTITYTYPE");
		valVct.addElement(DAENTITYTYPE_TBL.get(entityType));
		
		if(attrCode!=null){ // IDL groups only need DARULEs for this attributecode
			attrVct.addElement("DAATTRIBUTECODE");
			valVct.addElement(attrCode);
		}

		EntityItem eia[]= null;
		StringBuffer debugSb2 = new StringBuffer();
		try{
			eia= ABRUtil.doSearch(db, prof,
					DARULE_SRCHACTION_NAME, "DARULE", false, attrVct, valVct, debugSb2);
			if (debugSb2.length()>0){
				DARuleEngineMgr.addDebugComment(D.EBUG_DETAIL,debugSb,debugSb2.toString());
			}
		}catch(Exception exc){
			if (debugSb2.length()>0){
				DARuleEngineMgr.addDebugComment(D.EBUG_DETAIL,debugSb,debugSb2.toString());
			}
			throw exc;
		}
		
		if (eia!=null && eia.length >0){
			// pull an extract to get all attributes
			EntityList rulelist = db.getEntityList(prof, new ExtractActionItem(null, db,prof, "dummy"),	eia);
			eia = rulelist.getParentEntityGroup().getEntityItemsAsArray();
			for (int i=0; i<eia.length; i++){
				DARuleEngineMgr.addDebugComment(D.EBUG_SPEW,debugSb,"searchForDARules found "+eia[i].getKey());
			}

		}
		attrVct.clear();
		valVct.clear();
		return eia;
	}
    /***********************************************
     *  Get the version
     *
     *@return java.lang.String
     */
     public static String getVersion()
     {
     	return "$Revision: 1.6 $";
     }
}
