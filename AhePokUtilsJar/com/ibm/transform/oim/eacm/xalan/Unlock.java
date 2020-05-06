/**
 * Licensed Materials -- Property of IBM
 * (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
 * The source code for this program is not published or otherwise divested of
 * its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
 *
 * Created on Feb 28, 2005
 *
 */


package com.ibm.transform.oim.eacm.xalan;


import java.sql.SQLException;

import com.ibm.transform.oim.eacm.util.PokUtils;

import COM.ibm.eannounce.objects.EANList;
import COM.ibm.eannounce.objects.EANMetaAttribute;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.LockActionItem;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.Profile;


/**
 * Executes an unlock action
 * <pre>
 * $Log: Unlock.java,v $
 * Revision 1.5  2008/08/26 21:07:54  wendy
 * Updated ReturnCode interface
 *
 * Revision 1.4  2006/10/19 21:29:43  chris
 * Interface changes
 *
 * Revision 1.3  2006/01/26 15:28:14  wendy
 * AHE copyright
 *
 * Revision 1.2  2005/09/09 13:30:54  wendy
 * remove eannouce11 ref
 *
 * Revision 1.1  2005/09/08 19:09:29  wendy
 * New pkg
 *
 * Revision 1.1  2005/03/02 18:39:51  chris
 * Unlock function for CR4220
 *
 * </pre>
 * @author cstolpe
 */
public class Unlock implements Init, EntityParam, ReturnCode {
	private String abrCode;
	/**
	 * Setter method for the ABR attribute code
	 *
	 * @param aCode the attribute code
	 * @return true if non null and length is > 0
	 */
	public boolean setAbrCode(String aCode) {
		abrCode = aCode;
		return abrCode != null && abrCode.length() > 0;
	}
    private String entityType = null;
    private int entityID = -1;
    private boolean passed = false;

    private Database db = null;
    private Profile prof = null;
    private EntityItem ei = null;

    /**
     * Set the database
     *
     * @param database
     * @return boolean true if database is not null
     */
    public boolean setDatabase(Database database) {
        db = database;
        return db != null;
    }

    /**
     * Set the user profile
     *
     * @param profile
     * @return boolean true if profile is not null
     */
    public boolean setProfile(Profile profile) {
        prof = profile;
        return prof != null;
    }

    /**
     *  (non-Javadoc)
     * @see com.ibm.transform.oim.eacm.xalan.Init#initialize()
     */
    public boolean initialize() {
        return true;
    }

    /**
     * Remove references to the entity item
     *
     * @return boolean always true
     */
    public boolean dereference() {
        ei = null;
        return true;
    }

    /**
     * Set the entity type
     * @see com.ibm.transform.oim.eacm.xalan.EntityParam#setEntityType(String)
     */
    public boolean setEntityType(String aType) {
        entityType = aType;
        return true;
    }

    /**
     * Set the entity ID
     * @see com.ibm.transform.oim.eacm.xalan.EntityParam#setEntityID(int)
     */
    public boolean setEntityID(int aID) {
        entityID = aID;
        return true;
    }

    /**
     *  Triggers the specified lock action
     *
     * @param actionName Name of the lock action.
     * @return boolean
     */
    public boolean triggerUnLock(String actionName) {
        EntityGroup eg;
        EntityItem[] aItems;
        LockActionItem lai;
        try {
            System.err.println("actionName="+actionName);
            eg = new EntityGroup(null, db, prof, entityType, "Navigate");  //$NON-NLS-1$
            ei = new EntityItem(eg, prof, db, entityType, entityID);
            aItems = new EntityItem[] { ei };
            lai = new LockActionItem(null, db, prof, actionName);
            lai.setEntityItems(aItems);
            db.executeAction(prof, lai);
            passed = true;
        } catch (MiddlewareRequestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            passed = false;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            passed = false;
        } catch (MiddlewareException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            passed = false;
        } catch (MiddlewareShutdownInProgressException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            passed = false;
        }
        System.err.println("passed="+passed);
        return passed;
    }

    /**
     * Returns true if the unlock action succeeded
     * @return
     */
    public boolean hasPassed() {
        return passed;
    }
	/* (non-Javadoc)
	 * @see com.ibm.transform.oim.eacm.xalan.ReturnCode#getMessage()
	 */
	public String getMessage(){
		return "";
	}
    /**
     * Get Name based on navigation attributes
     *
     * @return    java.lang.String
     */
    public String getNavigationName() {
        StringBuffer navName = new StringBuffer();
        if (ei != null) {
            // NAME is navigate attributes
            EntityGroup eg = ei.getEntityGroup();
            EANList metaList = eg.getMetaAttribute();
            // iterator does not maintain navigate order
            for (int ii = 0; ii < metaList.size(); ii++) {
                EANMetaAttribute ma = (EANMetaAttribute) metaList.getAt(ii);
                navName.append(
                    PokUtils.getAttributeValue(
                        ei,
                        ma.getAttributeCode(), ",", ""));
                navName.append(" "); //$NON-NLS-1$
            }
        }

        return navName.toString();
    }
}
