/*
 * Created on Mar 2, 2005
 * Licensed Materials -- Property of IBM
 *
 * (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
 * The source code for this program is not published or otherwise divested of
 * its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
 */


package com.ibm.transform.oim.eacm.xalan.table.ls;


import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import COM.ibm.eannounce.objects.EANAttribute;
import COM.ibm.eannounce.objects.EANFlagAttribute;
import COM.ibm.eannounce.objects.EANList;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EntityList;
import COM.ibm.eannounce.objects.ExtractActionItem;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;

import com.ibm.transform.oim.eacm.xalan.EntityParam;
import com.ibm.transform.oim.eacm.xalan.Init;
import com.ibm.transform.oim.eacm.xalan.Table;
import com.ibm.transform.oim.eacm.xalan.table.EntityGroupTable;


/**
 * Creates a table of WWCC and parent Available WW that are found under a CUR
 * <pre>
 * $Log: CURtoWWCCTable.java,v $
 * Revision 1.2  2006/01/25 19:51:43  wendy
 * AHE copyright
 *
 * Revision 1.1  2005/09/08 19:14:46  wendy
 * New pkg
 *
 * Revision 1.1  2005/03/02 19:29:39  chris
 * Report - Worldwide In-Country Courses Sorted by Available Worldwide Course
 *
 * </pre>
 * @author cstolpe
 */
public class CURtoWWCCTable implements Table, Init, EntityParam {
    private EntityGroupTable ww = new EntityGroupTable();
    private EntityGroupTable wwcc = new EntityGroupTable();
    private Database db = null;
    private Profile prof = null;
    private EntityGroup egWWCC = null;
    private EntityList list = null;
    private String entityType = null;
    private int entityID = -1;
    private String extractName;
    private Map wwccToWW = new HashMap();

    /**
     * Columns 0 & 1 are from the WW table. Column 2 is Lifecycle Flag Code.
     * And columns 3 through 12 are from the WWCC table
     * @see com.ibm.transform.oim.eacm.xalan.Table#get(int, int)
     */
    public Object get(int row, int column) {
        if (column < 2) {
            // get row by WWCC row to WW row map
            String key = egWWCC.getEntityItem(row).getKey();
            row = ((Integer) wwccToWW.get(key)).intValue();
            return ww.get(row, column);
        }
        else if (column == 2) {
            EntityItem ei = egWWCC.getEntityItem(row);
            EANAttribute att = ei.getAttribute("LSWWCCLIFECYCLE");  //$NON-NLS-1$
            if (att != null && att instanceof EANFlagAttribute) {
                EANFlagAttribute fa = (EANFlagAttribute) att;
                return fa.getFirstActiveFlagCode();
            }
        }
        else {
            return wwcc.get(row, column - 3);
        }
        return null;
    }

    /**
     * Columns 0 & 1 are from the WW table. Column 2 is Lifecycle Flag Code.
     * And columns 3 through 12 are from the WWCC table
     * @see com.ibm.transform.oim.eacm.xalan.Table#getColumnHeader(int)
     */
    public String getColumnHeader(int column) {
        if (column < 2) {
            return ww.getColumnHeader(column);
        }
        else if (column == 2) {
            return "Lifecycle Flag Code";  //$NON-NLS-1$
        }
        else {
            return wwcc.getColumnHeader(column - 3);
        }
    }

    /**
     * There should be 12 columns
     * @see com.ibm.transform.oim.eacm.xalan.Table#getColumnCount()
     */
    public int getColumnCount() {
        return ww.getColumnCount() + wwcc.getColumnCount() + 1;
    }

    /**
     * Count of WWCC rows
     * @see com.ibm.transform.oim.eacm.xalan.Table#getRowCount()
     */
    public int getRowCount() {
        return wwcc.getRowCount();
    }

    /**
     * Set the Database.
     * @see com.ibm.transform.oim.eacm.xalan.Init#setDatabase(Database)
     */
    public boolean setDatabase(Database database) {
        db = database;
        return true;
    }

    /**
     * Set the Profile.
     * @see com.ibm.transform.oim.eacm.xalan.Init#setProfile(Profile)
     */
    public boolean setProfile(Profile profile) {
        prof = profile;
        return true;
    }

    /**
     * Execute the specified extract and set up the table.
     * @see com.ibm.transform.oim.eacm.xalan.Init#initialize()
     */
    public boolean initialize() {
        try {
            EntityGroup egCUR = new EntityGroup(null, db, prof, entityType, "Navigate");  //$NON-NLS-1$
            EntityGroup egWW;
            EntityItem eiCUR = new EntityItem(egCUR, prof, db, entityType, entityID);
            ExtractActionItem ex = new ExtractActionItem(null, db, prof, extractName);
            EntityItem[] eia = new EntityItem[] { eiCUR };
            String[] codes = null;
            EANList listWW;
            int nWWCC;

            // Execute the extract
            list = db.getEntityList(prof, ex, eia);

            // Set up the WW Table
            codes = new String[] {
                "LSWWID",  //$NON-NLS-1$
                "LSWWEXPDATE"  //$NON-NLS-1$
            };
            egWW = list.getEntityGroup("LSWW");  //$NON-NLS-1$
            ww.setColumnAttributeCodes(codes);
            ww.setEntityGroup(egWW);

            // Set up the WWCC Table
            egWWCC = list.getEntityGroup("LSWWCC");  //$NON-NLS-1$
            codes = new String[] {
                "LSCRSID",  //$NON-NLS-1$
                "LSCRGLOBALREPTITLE",  //$NON-NLS-1$
                "LSCRSEXPDATE",  //$NON-NLS-1$
                "LSWWCCCOUNTRY",  //$NON-NLS-1$
                "LSCRSDELIVERY",  //$NON-NLS-1$
                "LSCRSSUBDELIVERY",  //$NON-NLS-1$
                "LSCRSMEDIA",  //$NON-NLS-1$
                "LSCRSDURATION",  //$NON-NLS-1$
                "LSCRSDURATIONUNITS"  //$NON-NLS-1$
            };
            wwcc.setColumnAttributeCodes(codes);
            wwcc.setEntityGroup(egWWCC);

            // Map rows of WWCC to rows of WW
            listWW = egWW.getEntityItem();
            nWWCC = egWWCC.getEntityItemCount();
            for (int i = 0; i < nWWCC; i++) {
                EntityItem eiWWCC = egWWCC.getEntityItem(i);
                String key = eiWWCC.getKey();
                EntityItem eiWWWWCC = (EntityItem) eiWWCC.getUpLink(0);
                EntityItem eiWW = (EntityItem) eiWWWWCC.getUpLink(0);
                wwccToWW.put(key, new Integer(listWW.indexOf(eiWW.getKey())));
            }
        } catch (MiddlewareRequestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (MiddlewareException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Dereference the EntityList.
     * @see com.ibm.transform.oim.eacm.xalan.Init#dereference()
     */
    public boolean dereference() {
        if (list != null) {
            list.dereference();
            return true;
        }
        return false;
    }

    /**
     * Set the entity type. LSCUR is expected
     * @see com.ibm.transform.oim.eacm.xalan.EntityParam#setEntityType(String)
     */
    public boolean setEntityType(String aType) {
        entityType = aType;
        return true;
    }

    /**
     * Set the entity ID.
     * @see com.ibm.transform.oim.eacm.xalan.EntityParam#setEntityID(int)
     */
    public boolean setEntityID(int aID) {
        entityID = aID;
        return true;
    }

    /**
     * Set the name of the extract to run. EXCURWWCCALL does not filter on LSWWCCLIFECYCLE.
     * EXCURWWCC0010 to 0070 filter on the specified status.
     * @param extract
     * @return
     */
    public String setExtract(String extract) {
        extractName = extract;
        return extract;
    }
}
