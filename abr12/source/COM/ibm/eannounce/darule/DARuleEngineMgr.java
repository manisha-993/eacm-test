//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2011  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.darule;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Vector;

import com.ibm.transform.oim.eacm.util.PokUtils;

import COM.ibm.eannounce.objects.EANList;
import COM.ibm.eannounce.objects.EANMetaAttribute;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.DatePackage;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.objects.Blob;

/*********************************
 * There are four separate processes that need to be supported:
 * 1.	IDL - whenever an Attribute Derivation Rule is created or changed, the corresponding CATDATA 
 * attributes must be created or updated. The user will trigger this process by setting Life Cycle to 
 * Ready which will set the ABR to Queued. Once complete, the ABR sets Life Cycle to Production.
 * 2.	Offering Data - there will be a work flow action for an authorized user to request the generation / 
 * updating of the CATADATA attributes for the offering data. The ABR only processes data for the selected 
 * offering which has the ABR attribute set to Queued and only utilizes CATADATA rules that have a Life Cycle 
 * of Production.
 * 3.	Data Quality - whenever offering data moves to “Ready for Review”, the Data Quality ABR needs to 
 * generate / update the CATADATA attributes for the offering. If the generation fails, the DQ ABR will fail. 
 * When STATUS is change to “Ready for Review”, the Data Quality ABR will be queued. If all of the Data 
 * Quality Checks pass, then the DQ ABR will process data for the selected offering and only utilizes CATADATA 
 * rules that have a Life Cycle of Production.
 * 4.	Retire - whenever an Attribute Derivation Rule is no longer being used, the user will set Life Cycle 
 * to Retire which will set the ABR to Queued. The ABR will then delete (deactivate) all values generated based 
 * on this rule. Once complete, the ABR sets Life Cycle to Obsolete.
 * 
 * 
 * The Offering Data and Data Quality ABRs need to be shut down via Task Master Idlers whenever a change is 
 * made to a DARULE which needs an IDL.
 * 
 * Whenever there are multiple DARULEs for a single Entity Attribute controlled by Sequence (RULESEQ), 
 * the following process must be followed by the user:
 * •	Making a change to one or more of the rules
 * 1.	the user will change Life Cycle to “Change” for the entire set of DARULEs.
 * 2.	Make the change
 * 3.	Set only one of the DARULEs to Ready.
 * •	Making the set Obsolete.
 * 1.	the user will change Life Cycle to “Change” for the entire set of DARULEs.
 * 2.	the other DARULE will be changed to “Retire”
 * •	Initialize a new DARULE set
 * 1.	Leave all but one in Draft
 * 2.	Change one to “Ready”
 * 
 * 1. This class is used to support the IDL by finding all DARULEs for a given entitytype and attributecode
 * and returning them as an DARuleGroup.  This group can execute the DARULEs on each offering found by the 
 * ATTRDERIVEABRSTATUS ABR.  Any DARuleEngine cached for the given entitytype is expired.
 * 2 and 3. This class supports Offering data and DQ ABRs by looking in cache for a DARuleEngine for that 
 * entitytype.  If not in cache it will create a DARuleEngine and cache it for future use.
 * Creates and caches a DARuleEngine for each entity type when running Production DARULEs on instances
 * of offering data.
 * 4.  This class needs to be notified to expire any DARuleEngine for an entitytype that is having a DARULE retired
 *
 */
// $Log: DARuleEngineMgr.java,v $
// Revision 1.4  2011/04/25 20:11:50  wendy
// Expire cache if DARULE that was part of cache was modified
//
// Revision 1.3  2011/03/23 15:40:38  wendy
// make addDebugComment public
//
// Revision 1.2  2011/03/17 20:15:53  wendy
// support bypass cache for dev work
//
// Revision 1.1  2011/03/15 21:12:10  wendy
// Init for BH FS ABR Catalog Attr Derivation 20110221b.doc
//
public class DARuleEngineMgr {
	private static final String RULE_KEY = "DARULES"; 
    private static Hashtable metaTbl = new Hashtable();
	//DARULE_debugLevel=4
	private static int DEBUG_LVL = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getABRDebugLevel("DARULE");
	//DARULE_useCache=false turn off during dev
	private static boolean USE_CACHE = Boolean.valueOf(COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue("DARULE",
			"_useCache","true")).booleanValue();

	private DARuleEngineMgr() {} // do not instantiate this class
	
	/**
	 * update or create and link CATDATA for this root entity item
	 * called on specific instance data like from DQ ABR or from workflow ABR
	 * NOTE: not used for IDL
	 * 
	 * @param db
	 * @param prof
	 * @param root
	 * @param msgSb
	 * @return
	 * @throws Exception
	 */
	public static boolean updateCatData(Database db, Profile prof, EntityItem root, StringBuffer msgSb)
	throws Exception
	{
		boolean chgsmade = false;
		//find DARuleEngine for the root type 
		DARuleEngine darEngine = getEngine(db, prof, root.getEntityType(), msgSb);
		if(darEngine!=null){
			DARuleEngineMgr.addDebugComment(D.EBUG_DETAIL, msgSb,"updateCatData: "+darEngine);

			// execute 
			try{
				chgsmade = darEngine.updateCatData(db, prof, root, msgSb);
			}finally{
				darEngine.dereference();
			}
		}else{
			throw new DARuleException("Unable to find DARules for "+root.getEntityType());
		}
		return chgsmade;
	}
	
	/**
	 * 
	 * this will find the DARULE group for the specified entitytype and attribute code
	 * it will cause all rules for the entitytype to be expired in cache
	 * 
	 * NOTE: used from IDL abr
	 * 
	 * @param db
	 * @param prof
	 * @param entitytype - the flag description, use FEATURE instead of 10
	 * @param attrcode - flag value
	 * @param debugSb 
	 * @return
	 */
	public static DARuleGroup getDARuleGroup(Database db, Profile prof, String entitytype, String attrcode, 
			StringBuffer debugSb) throws Exception
	{
		// clear any cached engine for this entitytype - it must be reloaded after DARULEs are moved to production state
		expireCache(db, prof, entitytype, debugSb);
		
		//get a new DARuleGroup for the root type and attributecode- this is an idl
		return new DARuleGroup(db, prof,entitytype, attrcode, debugSb);
	}
	
	/**
	 * called from the ATTRDERIVEABRSTATUS ABR when a DARULE is retired.  
	 * clear the current DARULEs from cache for this entitytype
	 * NOTE: used from IDL ABR 
	 * 
	 * @param db
	 * @param prof
	 * @param entitytype
	 * @param debugSb
	 */
	public static void clearEntityType(Database db, Profile prof, String entitytype, StringBuffer debugSb) 
	{
		// clear any cached engine for this entitytype - it must be reloaded for DARULEs in production state
		expireCache(db, prof, entitytype, debugSb);
	}

	/**
	 * add msg as an html comment if meets debuglevel set in abr.server.properties
	 * @param debuglvl
	 * @param sb
	 * @param msg
	 */
	public static void addDebugComment(int debuglvl, StringBuffer sb, String msg){
		if (debuglvl <= DEBUG_LVL) {
			sb.append("<!-- "+msg+" -->\n");
		}
	}
	/**
	 * add msg as an html paragraph
	 * @param sb
	 * @param msg
	 */
	protected static void addInformation(StringBuffer sb, String msg){
		sb.append("<p>"+msg+"</p>\n");
	}
    /**
     *  Get Name based on navigation attributes for specified entity
     * @param db
     * @param prof
     * @param theItem
     * @return
     * @throws java.sql.SQLException
     * @throws MiddlewareException
     */
    protected static String getNavigationName(Database db, Profile prof, EntityItem theItem) throws java.sql.SQLException, MiddlewareException
     {
         StringBuffer navName = new StringBuffer();
         // NAME is navigate attributes
         // check hashtable to see if we already got this meta
         EANList metaList = (EANList)metaTbl.get(theItem.getEntityType());
         if (metaList==null) {
             EntityGroup eg = new EntityGroup(null, db, prof, theItem.getEntityType(), "Navigate");
             metaList = eg.getMetaAttribute();  // iterator does not maintain navigate order
             metaTbl.put(theItem.getEntityType(), metaList);
         }
         navName.append(theItem.getEntityGroup().getLongDescription()+": ");
         for (int ii=0; ii<metaList.size(); ii++) {
             EANMetaAttribute ma = (EANMetaAttribute)metaList.getAt(ii);
             navName.append(PokUtils.getAttributeValue(theItem, ma.getAttributeCode(),", ", "", false));
             if (ii+1<metaList.size()){
 	        	navName.append(" ");
 			}
         }

         return navName.toString().trim();
     }

	/**
	 * get the ruleengine for this entity type - will be cached, not used for IDL
	 * @param db
	 * @param prof
	 * @param rootType
	 * @param debugSb
	 * @return
	 * @throws MiddlewareException
	 * @throws SQLException
	 * @throws MiddlewareShutdownInProgressException
	 */
	private static DARuleEngine getEngine(Database db, Profile prof, String rootType, StringBuffer debugSb) 
	throws Exception
	{
	   	//look at cache
		DARuleEngine darEng = getCache(db,prof,rootType, debugSb);

		// if not in cache, pull from pdh and put into cache
		if(darEng==null){
			darEng = new DARuleEngine(db, prof,rootType, debugSb);
			//put into cache
			putCache(db, prof, darEng, rootType, debugSb);
		}
		return darEng;
	}
	
    /**
     * get rule engine from blob table if it exists
     * @param db
     * @param prof
     * @param rootType
     * @param debugSb
     * @return
     */
    private static DARuleEngine getCache(Database db,Profile prof,String rootType, 
    	StringBuffer debugSb) 
    {
        byte[] ba = null;
        ByteArrayInputStream baisObject = null;
        ObjectInputStream oisObject = null;
        DARuleEngine dareng = null;
        //The Attribute Derivation Rules are applied for US English (NLSID = 1) only. 
        //The translation function is used to produce translations of the US English data.
        int nlsid=1;

        if(!USE_CACHE){
            addDebugComment(D.EBUG_ERR,debugSb,"DARuleEngineMgr.getCache() is bypassed");
        	return dareng;
        }
        try {
            // go get the blob
            Blob b = db.getBlobNow(prof, rootType, 0, RULE_KEY, nlsid);

            db.commit();
            db.freeStatement();
            db.isPending();
            ba = b.getBAAttributeValue();
            if (ba == null || ba.length == 0) {
                db.debug(D.EBUG_WARN, 
                		"DARuleEngineMgr.getCache()  ** No Cache Object Found for "+rootType+":"+RULE_KEY+" **");
                addDebugComment(D.EBUG_INFO,debugSb,"DARuleEngineMgr.getCache()  ** No Cache Object Found for "+rootType+":"+RULE_KEY+" **");
                return dareng;
            }

            try {
                baisObject = new ByteArrayInputStream(ba);
                oisObject = new ObjectInputStream(baisObject);
                dareng = (DARuleEngine) oisObject.readObject();
            }finally {
            	if(oisObject!=null){
            		oisObject.close();
            		oisObject = null;
            	}
            	if(baisObject!=null){
            		baisObject.close();
            		baisObject = null;
            	}
            }

            ba = null;
            
            // check for any changes in DARULEs used in this engine since it was cached
            // looking for any moved from Production to Change - and forcing reload for this entitytype
            if(updatesFound(db, prof,dareng,debugSb)){
            	// expire this one
            	expireCache(db, prof, rootType, debugSb);
            	dareng.dereference();
            	//force a new one
            	dareng = null;
            }
            return dareng;
        }catch (Exception x) {
            db.debug(D.EBUG_ERR, "DARuleEngineMgr.getCache() "+x.getMessage());
            addDebugComment(D.EBUG_ERR,debugSb,"DARuleEngineMgr.getCache() Error: "+x.getMessage());
            x.printStackTrace();
            if (x instanceof java.io.InvalidClassException){
            	// clear the cache, something changed
            	expireCache(db, prof, rootType, debugSb);
            }
        }finally {
            db.freeStatement();
            db.isPending();
        }

        return dareng;
    }
    
    /**
     * check for any updates made to DARULE(s) used for this engine
     * @param db
     * @param prof
     * @param dareng
     * @param debugSb
     * @return
     */
    private static boolean updatesFound(Database db, Profile prof, DARuleEngine dareng,
    		StringBuffer debugSb)
    {
    	boolean chgs = false;
    	
        // check DTS in DARuleEngine
    	Vector idvct = getChangedDARULEids(db, prof,  dareng.getCreateDTS(), debugSb);
    	if(idvct.size()>0){
    		// check for any changes in DARULEs used in this engine
    		Vector daruleids = dareng.getAllDARULEids();
    		for (int x=0; x<daruleids.size(); x++){
    			Integer id = (Integer)daruleids.elementAt(x);
    			if(idvct.contains(id)){
    				chgs = true;
    				break;
    			}
    		}
    		daruleids.clear();
    		idvct.clear();
    	}
        
        return chgs;
    }
	private static final String DTS_SQL = 
	"select entityid from opicm.entity where enterprise=? and entitytype=? and valto>current timestamp "+ 
	"and effto>current timestamp "+
	"and valfrom >? with ur";
	/**
	 * fixme this should really be an SP
	 * @param db
	 * @param prof
	 * @param dts
	 * @param debugSb
	 * @return
	 */
	private static Vector getChangedDARULEids(Database db, Profile prof, String dts, StringBuffer debugSb)
	{
   	 	PreparedStatement ps = null;
		ResultSet result=null;
		Vector vct = new Vector();
		String enterprise = prof.getEnterprise();

		addDebugComment(D.EBUG_WARN,debugSb,"getChangedDARULEids look for DARULE chgs after "+dts);
		try{
	 	 	ps = db.getPDHConnection().prepareStatement(DTS_SQL);
			ps.clearParameters();
			ps.setString(1, enterprise);
			ps.setString(2, "DARULE");
			ps.setString(3, dts);

			result = ps.executeQuery();
			while(result.next()) {					
				int eid = result.getInt(1);
				vct.add(new Integer(eid));	
				addDebugComment(D.EBUG_WARN,debugSb,"getChangedDARULEids found DARULE"+eid);
			}
		}
		catch(Exception t) {
			addDebugComment(D.EBUG_ERR,debugSb,"getChangedDARULEids exception: "+t);
		}
		finally{
			if (result!=null){
				try {
					result.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				result=null;
			}
			if(ps!=null){
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			try {
				db.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		    db.freeStatement();
		    db.isPending();
		}
		return vct;
	}	
    /**
     * put this rule engine in the blob table
     * @param _db
     * @param prof
     * @param dareng
     * @param rootType
     * @param debugSb
     */
    private static void putCache(Database _db, Profile prof, DARuleEngine dareng, String rootType, 
    		StringBuffer debugSb) 
    {
        ByteArrayOutputStream baosObject = null;
        ObjectOutputStream oosObject = null;

        if(!USE_CACHE){
            addDebugComment(D.EBUG_ERR,debugSb,"DARuleEngineMgr.putCache() is bypassed");
        	return;
        }
        try {
        	DatePackage dp = _db.getDates();
        	String strNow = dp.getNow();
        	String strForever = dp.getForever();
        	//The Attribute Derivation Rules are applied for US English (NLSID = 1) only. 
        	//The translation function is used to produce translations of the US English data.
        	int nlsid=1;

        	try {
        		//update 
        		byte[] byteArray = null;
        		ByteArrayOutputStream BAout = null;
        		ObjectOutputStream Oout = null;
        		try {
        			//put object into stream
        			try {
        				BAout = new ByteArrayOutputStream();
        				Oout = new ObjectOutputStream(BAout);
        				Oout.writeObject(dareng);
        				Oout.flush();
        				Oout.reset();
        			}
        			finally {
        				if(Oout!=null){
        					Oout.close();
        				}
        				if(BAout!=null){
        					BAout.close();
        				}
        			}
        			byteArray = BAout.toByteArray();
        		}
        		catch (Exception exc) {
        			exc.printStackTrace();
        		}

        		_db.callGBL9974(new ReturnStatus( -1), _db.getInstanceName(), "pc");
        		_db.freeStatement();
        		_db.isPending();
        		_db.putBlob(prof, rootType, 0, RULE_KEY, "CACHE", strNow, strForever, byteArray, nlsid);
        	}
            finally {
                _db.commit();

                if(oosObject!=null){
                	oosObject.close();
                }
                if(baosObject!=null){
                	baosObject.close();
                }
            }
        }
        catch (Exception x) {
            _db.debug(D.EBUG_ERR,"DARuleEngineMgr.putCache() "+ x.getMessage());
            addDebugComment(D.EBUG_ERR,debugSb,"DARuleEngineMgr.putCache() "+x.getMessage());
            x.printStackTrace();
            _db.freeStatement();
            _db.isPending();

        }
    }
    /**
     * expire this rule engine in the blob table
     * @param _db
     * @param prof
     * @param rootType
     * @param debugSb
     */
    private static void expireCache(Database db, Profile prof, String rootType, 
    		StringBuffer debugSb) 
    {
        try {
            //The Attribute Derivation Rules are applied for US English (NLSID = 1) only. 
            //The translation function is used to produce translations of the US English data.
            int nlsid=1;
            addDebugComment(D.EBUG_SPEW,debugSb,"DARuleEngineMgr: Expire rules for "+rootType);
            try {
                //expire
                db.deactivateBlob(prof, rootType, 0, RULE_KEY, nlsid);
            }
            finally {
                db.commit();
            }
        }
        catch (Exception x) {
            db.debug(D.EBUG_ERR,"DARuleEngineMgr.expireCache() "+ x.getMessage());
            addDebugComment(D.EBUG_ERR,debugSb,"DARuleEngineMgr.expireCache() "+x.getMessage());
            x.printStackTrace();
            db.freeStatement();
            db.isPending();
        }
    }
    
    /***********************************************
     *  Get the version
     *
     *@return java.lang.String
     */
     public static String getVersion()
     {
     	return "$Revision: 1.4 $";
     }
}
