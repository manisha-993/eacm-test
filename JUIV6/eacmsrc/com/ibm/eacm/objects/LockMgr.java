//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.objects;

import java.util.Hashtable;
import java.util.Vector;

import COM.ibm.eannounce.objects.EANFoundation;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.LockList;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;

/**
 * this manages all locks
 * @author Wendy Stimpson
 */
//$Log: LockMgr.java,v $
//Revision 1.1  2012/09/27 19:39:13  wendy
//Initial code
//
public class LockMgr {
	private Hashtable<String, EntityItem> lockownerTbl = new Hashtable<String, EntityItem>(); // vastly improve performance
	private Hashtable<String, LockList> locklistTbl = new Hashtable<String, LockList>();

	// this is used to rollback values when attributes are locked, but entity is unlocked thru the lockactiontab
    private Hashtable<String, Vector<EANFoundation>>  lockGrpKeyTbl =
    	new Hashtable<String, Vector<EANFoundation>>();// key=LockGroup.key, value=Vector of locked EANAttribute

    /**
     * release memory
     */
    public void dereference(){
    	lockownerTbl.clear();
    	lockownerTbl = null;
    	locklistTbl.clear();
    	locklistTbl = null;
    	lockGrpKeyTbl.clear();
    	lockGrpKeyTbl= null;
    }
    /**
     * @param lockGrpKey
     * @return
     */
    public Vector<EANFoundation> getLockGrpKeyVct(String lockGrpKey) {
    	return lockGrpKeyTbl.get(lockGrpKey);
    }
    /**
     * called when user gets a lock on the ean object. ean reference is used when entity is unlocked thru
     * the lock action tab, the UI needs to reflect this unlocking
     * @param lockGrpKey
     * @param ean
     */
    public void addLockGrpKey(String lockGrpKey, EANFoundation ean) {
    	Vector<EANFoundation> vct = lockGrpKeyTbl.get(lockGrpKey);
    	if (vct==null){
    		vct = new Vector<EANFoundation>();
    		lockGrpKeyTbl.put(lockGrpKey, vct);
    	}
    	if(!vct.contains(ean)){
    		vct.add(ean);
    	}
    }
	/**
	 * used when a table is unlocked, lockaction tab may have this lockitem in the table
	 * @param lockGrpKey
	 * @param ean
	 */
	public void removeLockGrpItem(String lockGrpKey, EANFoundation ean){
    	Vector<EANFoundation> vct = lockGrpKeyTbl.get(lockGrpKey);
    	if (vct!=null && vct.contains(ean)){
    		vct.remove(ean);
    	}
	}
    /**
     * @param lockGrpKey
     */
    public void clearLockGrpKey(String lockGrpKey) {
    	Vector<EANFoundation> vct = lockGrpKeyTbl.remove(lockGrpKey);
    	if (vct!=null){
    		vct.clear();
    	}
    }

    /**
     * getLockList - this holds the user's current locks by profile!
     *
     * @return
     */
    public LockList getLockList(Profile prof, boolean create) {
    	String key = prof.getEnterprise()+prof.getOPWGID();
    	LockList lockList = locklistTbl.get(key);
    	if(lockList==null && create){
    		try {
				lockList = new LockList(prof);
				locklistTbl.put(key, lockList);
			} catch (MiddlewareRequestException e) {
				e.printStackTrace();
			} catch (MiddlewareException e) {
				e.printStackTrace();
			}
    	}
        return lockList;
    }
    /**
     * get existing lock list, dont create a new one
     * @param prof
     * @return
     */
    public LockList getExistingLockList(Profile prof) {
    	return this.getLockList(prof, false);
    }
    /**
     * @param prof
     * @return
     */
    public EntityItem getLockOwner(Profile prof) {
        EntityItem lockOwnerEI = null;
        try {
        	String key = prof.getEnterprise()+prof.getOPWGID();
			lockOwnerEI = (EntityItem)lockownerTbl.get(key);
			if (lockOwnerEI==null){
				lockOwnerEI = new EntityItem(null, prof, Profile.OPWG_TYPE, prof.getOPWGID());
				lockownerTbl.put(key,lockOwnerEI);
			}
        } catch (Exception _x) {
            _x.printStackTrace();
        }
        return lockOwnerEI;
    }
}
