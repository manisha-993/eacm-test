/*
 * Created on Jan 17, 2005
 *
 * Licensed Materials -- Property of IBM
 *
 * (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
 * The source code for this program is not published or otherwise divested of
 * its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
 */
package com.ibm.transform.oim.eacm.xalan.table.ls;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import COM.ibm.eannounce.objects.AttributeChangeHistoryGroup;
import COM.ibm.eannounce.objects.AttributeChangeHistoryItem;
import COM.ibm.eannounce.objects.ChangeHistoryItem;
import COM.ibm.eannounce.objects.EANAttribute;
import COM.ibm.eannounce.objects.EANList;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EntityList;
import COM.ibm.eannounce.objects.ExtractActionItem;
import COM.ibm.eannounce.objects.NavActionItem;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.Profile;

import com.ibm.transform.oim.eacm.xalan.EntityParam;
import com.ibm.transform.oim.eacm.xalan.Init;
import com.ibm.transform.oim.eacm.xalan.Table;
import com.ibm.transform.oim.eacm.xalan.table.EntityGroupTable;
import com.ibm.transform.oim.eacm.util.LSConstants;

/**
 * This table contains the following 9 columns:<br/>
 * 0 In-Country Course Code<br/>
 * 1 Global Reporting English Title<br/>
 * 2 Pricing Status<br/>
 * 3 Delivery Method<br/>
 * 4 Sub Delivery Method<br/>
 * 5 Active Course Languages <br/>
 * 6 Awaiting Approvals Timestamp<br/>
 * 7 Worldwide Course Title (LSWWID)<br/>
 * 8 Worldwide Course Title (LSWWTITLE)<br/>
 *
 * They are not sorted by the awaiting approvals timestamp.
 * The LSCC rows come after the LSWW rows.
 *
 * <pre>
 * $Log: RequiresOrUnderEditTable.java,v $
 * Revision 1.3  2006/01/25 19:51:43  wendy
 * AHE copyright
 *
 * Revision 1.2  2005/09/09 13:33:26  wendy
 * remove eannounce11 ref
 *
 * Revision 1.1  2005/09/08 19:14:46  wendy
 * New pkg
 *
 * Revision 1.2  2005/03/03 15:06:31  chris
 * Changed Mapping of WWCC to WW table to eliminate duplicates
 *
 * Revision 1.1  2005/02/23 21:13:02  chris
 * Initial XSL Report ABR Code
 *
 * </pre>
 *
 * @author cstolpe
 */
public class RequiresOrUnderEditTable implements Table, Init, EntityParam {
    // class name, method name, parameter
    private final MessageFormat PARAM1NULL = new MessageFormat("{0}.{1}({2}): {2} is null.");  //$NON-NLS-1$
    private Object[] mfParams = {
        "RequiresEditTable",  //$NON-NLS-1$
        "",  //$NON-NLS-1$
        ""  //$NON-NLS-1$
    };
    private Map parentWW = new HashMap();
    private EntityGroupTable wwcc = new EntityGroupTable();
    private EntityGroupTable cc = new EntityGroupTable();
    private EntityGroupTable ww = new EntityGroupTable();
    private EntityGroup egWWCC = null;
    private Vector timestamp = new Vector();
    private Database db = null;
    private Profile prof = null;
    private String entityType = null;
    private int entityID = -1;
    private String navActionWWCC = "NAVLSCTWWCCRE";
    private String navActionCC = "NAVLSCTCCFCCRE";

    /**
     *  (non-Javadoc)
     * @see com.ibm.transform.oim.eacm.xalan.Table#get(int, int)
     */
    public Object get(int row, int column) {
        // 0 "LSCRSID" In-Country Course Code
        // 1 "LSCRGLOBALREPTITLE" Global Reporting English Title
        // 2 "LSCCPRICINGSTATUS" "LSWWCCPRICINGSTATUS" Pricing Status
        // 3 "LSCRSDELIVERY" Delivery Method
        // 4 "LSCRSSUBDELIVERY" Sub Delivery Method
        // 5 "LSLANGINDICATOR" Active Course Languages
        if (column < 6) {
            if (row < wwcc.getRowCount()) {
                return wwcc.get(row, column);
            }
            else {
                row -= wwcc.getRowCount();
                return cc.get(row, column);
            }
        }
        else if (column > 6){
            // 7 "LSWWID"
            // 8 "LSWWTITLE" Worldwide Course
            column -= 7; // normalize to ww table;
            if (row < wwcc.getRowCount()) {
                // Which WW does this WWCC row refer to?
                EntityItem eiWWCC = egWWCC.getEntityItem(row);
                Integer rowWW = (Integer) parentWW.get(eiWWCC.getKey());
                if (rowWW != null){
                    return ww.get(rowWW.intValue(), column);
                }
                else {
                    System.err.print("RequiresEditTable.get(int,int): No WW parent for row ");  //$NON-NLS-1$
                    System.err.println(row);
                }
            }
        }
        else {
            // 6 Timestamp of "LSCCLIFECYCLE" "LSWWCCLIFECYCLE" Awaiting Approvals Timestamp
            return timestamp.elementAt(row);
        }
        return null; //$NON-NLS-1$
    }

    /**
     *  (non-Javadoc)
     * @see com.ibm.transform.oim.eacm.xalan.Table#getColumnHeader(int)
     */
    public String getColumnHeader(int column) {
        if (column < 6) {
            return wwcc.getColumnHeader(column);
        }
        if (column > 6) {
            column -= 7;
            return ww.getColumnHeader(column);
        }
        else {
            return "Awaiting Approvals Timestamp";  //$NON-NLS-1$
        }
    }

    /**
     *  (non-Javadoc)
     * @see com.ibm.transform.oim.eacm.xalan.Table#getColumnCount()
     */
    public int getColumnCount() {
        return wwcc.getColumnCount() + ww.getColumnCount() + 1;
    }

    /**
     * Sum of WWCC and CC rows
     * @see com.ibm.transform.oim.eacm.xalan.Table#getRowCount()
     */
    public int getRowCount() {
        return wwcc.getRowCount() + cc.getRowCount();
    }

    /**
     *  (non-Javadoc)
     * @see com.ibm.transform.oim.eacm.xalan.Init#setDatabase(COM.ibm.opicmpdh.middleware.Database)
     */
    public boolean setDatabase(Database database) {
        if (database == null) {
            mfParams[1] = "setDatabase";  //$NON-NLS-1$
            mfParams[2] = "Database";  //$NON-NLS-1$
            System.err.println(PARAM1NULL.format(mfParams));
            return false;
        }
        db = database;
        return true;
    }

    /**
     *  (non-Javadoc)
     * @see com.ibm.transform.oim.eacm.xalan.Init#setProfile(COM.ibm.opicmpdh.middleware.Profile)
     */
    public boolean setProfile(Profile profile) {
        if (profile == null) {
            mfParams[1] = "setProfile";  //$NON-NLS-1$
            mfParams[2] = "Profile";  //$NON-NLS-1$
            System.err.println(PARAM1NULL.format(mfParams));
            return false;
        }
        prof = profile;
        return true;
    }

    /** (non-Javadoc)
     * @see com.ibm.transform.oim.eacm.xalan.Init#initialize()
     */
    public boolean initialize() {
        // These are nav actions to get courses requiring catalog editing
        //"NAVLSCTCCFCCRE"  //$NON-NLS-1$
        //"NAVLSCTWWCCRE"  //$NON-NLS-1$
        // Extract to get LSWW
        // "EXRPT1WWCC4"
        String[] attributeCodes = {
            "LSCRSID", //$NON-NLS-1$
            "LSCRGLOBALREPTITLE", //$NON-NLS-1$
            "LSCCPRICINGSTATUS", //$NON-NLS-1$
            "LSCRSDELIVERY", //$NON-NLS-1$
            "LSCRSSUBDELIVERY", //$NON-NLS-1$
            "LSLANGINDICATOR" //$NON-NLS-1$
        };
        cc.setColumnAttributeCodes(attributeCodes);
        attributeCodes[2] = "LSWWCCPRICINGSTATUS";  //$NON-NLS-1$
        wwcc.setColumnAttributeCodes(attributeCodes);
        attributeCodes = new String[] {
            "LSWWID",  //$NON-NLS-1$
            "LSWWTITLE" //$NON-NLS-1$
        };
        ww.setColumnAttributeCodes(attributeCodes);
        try {
            EntityGroup egCT = new EntityGroup(null, db, prof, entityType, "Edit");  //$NON-NLS-1$
            EntityGroup egCC = new EntityGroup(null, db, prof, "LSCC", "Edit");  //$NON-NLS-2$  //$NON-NLS-1$
            EntityGroup egWW = new EntityGroup(null, db, prof, "LSWW", "Edit");  //$NON-NLS-2$  //$NON-NLS-1$
            NavActionItem nav = new NavActionItem(null, db, prof, navActionWWCC); //$NON-NLS-1$
            EntityItem eiCT = new EntityItem(egCT, prof, db, entityType, entityID);
            EntityItem[] eiaCT = new EntityItem[] {eiCT};
            EntityList list;
            EntityGroup tmp;
            EntityItem[] eia;

            egWWCC = new EntityGroup(null, db, prof, "LSWWCC", "Edit");  //$NON-NLS-2$  //$NON-NLS-1$
            // Get the WWCC entities that are awaiting approvals and require catalog editing
            nav.setEntityItems(eiaCT);
            // TODO right now I am assuming nav actions work, NAVLSCTCCFCCUE is broken
            list = db.executeAction(prof, nav);
            tmp = list.getEntityGroup("LSWWCC");  //$NON-NLS-1$
            eia = tmp.getEntityItemsAsArray();

            // Get the WW entity parent of the WWCC
            // TODO not taking into account limit on selection (Shouldn't be a problem)
            if (eia != null && eia.length > 0) {
                EntityList listWW =
                    db.getEntityList(
                        prof,
                        new ExtractActionItem(null, db, prof, "EXRPT1WWCC4"),  //$NON-NLS-1$
                        eia);
                tmp = listWW.getParentEntityGroup();  //$NON-NLS-1$
                for (int i = 0; i < tmp.getEntityItemCount(); i++) {
                    EntityItem eiWWCC = tmp.getEntityItem(i);
                    // this is the relator
                    EntityItem eiWW = (EntityItem) eiWWCC.getUpLink(0);
                    // make a copy so we can dereference list
                    eiWWCC = new EntityItem(egWWCC, prof, db, eiWWCC.getEntityType(), eiWWCC.getEntityID());
                    egWWCC.putEntityItem(eiWWCC);
                    // get the timestamp of when it was set to awaiting approvals
                    timestamp.add(getTimeStamp(eiWWCC,"LSWWCCLIFECYCLE",LSConstants.XR_LSWWCCLIFECYCLE_0040));  //$NON-NLS-1$
                    if (eiWW != null) {
                        EANList ealWW;
                        // this is the WW
                        eiWW = (EntityItem) eiWW.getUpLink(0);
                        if (eiWW != null) {
                            if (!egWW.containsEntityItem(eiWW.getEntityType(), eiWW.getEntityID())) {
                                // make a copy of the WW
                                eiWW = new EntityItem(egWW, prof, db, eiWW.getEntityType(), eiWW.getEntityID());
                                egWW.putEntityItem(eiWW);
                            }
                            ealWW = egWW.getEntityItem();
                            // map the row of the WWCC to the row of the WW
                            parentWW.put(eiWWCC.getKey(), new Integer(ealWW.indexOf(eiWW.getKey())));
                        }
                    }
                }
                listWW.dereference();
            }
            list.dereference();



            // Get the CC entities that are awaiting approvals and require catalog editing
            nav = new NavActionItem(null, db, prof, navActionCC); //$NON-NLS-1$
            nav.setEntityItems(eiaCT);
            list = db.executeAction(prof, nav);
            tmp = list.getEntityGroup("LSCC");  //$NON-NLS-1$
            for (int i = 0; i < tmp.getEntityItemCount(); i++) {
                EntityItem ei = tmp.getEntityItem(i);
                ei = new EntityItem(egCC, prof, db, ei.getEntityType(), ei.getEntityID());
                egCC.putEntityItem(ei);
                timestamp.add(getTimeStamp(ei,"LSCCLIFECYCLE",LSConstants.XR_LSCCLIFECYCLE_0040));  //$NON-NLS-1$
            }
            list.dereference();
            wwcc.setEntityGroup(egWWCC);
            ww.setEntityGroup(egWW);
            cc.setEntityGroup(egCC);
        } catch (MiddlewareRequestException e) {
            System.err.print("RequiresEditTable.initialize(): Exception ");  //$NON-NLS-1$
            System.err.println(e.getMessage());
            e.printStackTrace();
            return false;
        } catch (SQLException e) {
            System.err.print("RequiresEditTable.initialize(): Exception ");  //$NON-NLS-1$
            System.err.println(e.getMessage());
            e.printStackTrace();
            return false;
        } catch (MiddlewareException e) {
            System.err.print("RequiresEditTable.initialize(): Exception ");  //$NON-NLS-1$
            System.err.println(e.getMessage());
            e.printStackTrace();
            return false;
        } catch (MiddlewareShutdownInProgressException e) {
            System.err.print("RequiresEditTable.initialize(): Exception ");  //$NON-NLS-1$
            System.err.println(e.getMessage());
            e.printStackTrace();
            return false;
        }

        // These are the columns
        // 0 "LSCRSID" In-Country Course Code
        // 1 "LSCRGLOBALREPTITLE" Global Reporting English Title
        // 2 "LSCCPRICINGSTATUS" "LSWWCCPRICINGSTATUS" Pricing Status
        // 3 Timestamp of "LSCCLIFECYCLE" "LSWWCCLIFECYCLE" Awaiting Approvals Timestamp
        // 4 "LSWWID" <br/> "LSWWTITLE" Worldwide Course
        // 5 "LSCRSDELIVERY" Delivery Method
        // 6 "LSCRSSUBDELIVERY" Sub Delivery Method
        // 7 "LSLANGINDICATOR" Active Course Languages

        return true;
    }

    /**
     *  (non-Javadoc)
     * @see com.ibm.transform.oim.eacm.xalan.Init#dereference()
     */
    public boolean dereference() {
        ww = new EntityGroupTable();
        wwcc = new EntityGroupTable();
        cc = new EntityGroupTable();
        timestamp.clear();
        parentWW.clear();
        return true;
    }

    /**
     *  (non-Javadoc)
     * @see com.ibm.transform.oim.eacm.xalan.EntityParam#setEntityType(java.lang.String)
     */
    public boolean setEntityType(String eType) {
        entityType = eType;
        return true;
    }

    /**
     *  (non-Javadoc)
     * @see com.ibm.transform.oim.eacm.xalan.EntityParam#setEntityID(int)
     */
    public boolean setEntityID(int eID) {
        entityID = eID;
        return true;
    }

    /**
     * Get timestamp when course went into awaiting approvals, It will be used to sort the courses
     *
     * @param eid   EntityItem to find timestamp for
     * @param attrCode  String with lifecycle attribute code
     * @param status        String with awaiting approvals flag code
     * @returns String  timestamp
     */
    private String getTimeStamp(EntityItem eid, String attrCode, String status) throws MiddlewareRequestException, MiddlewareException, SQLException {
        String tstamp = null;
        EANAttribute attStatus = null;
        AttributeChangeHistoryGroup histGrp = null;


        attStatus = eid.getAttribute(attrCode);
        histGrp =
            db.getAttributeChangeHistoryGroup(prof, attStatus);

        for (int i = histGrp.getChangeHistoryItemCount() - 1; i >= 0; i--) {
            ChangeHistoryItem chiStatus = histGrp.getChangeHistoryItem(i);
            if (chiStatus instanceof AttributeChangeHistoryItem) {
                Object strFlagCode =
                    ((AttributeChangeHistoryItem) chiStatus).getFlagCode();
                String chgDate = chiStatus.getChangeDate();

                // find awaiting approvals
                if (strFlagCode.equals(status)) // lifecyclestatus
                {
                    // want last one that matches
                    tstamp = chgDate;
                    break;
                }
            }
        }

        return tstamp;
    }

    /**
     * set requires edit
     * @return
     */
    public String setRequiresEdit() {
        navActionWWCC = "NAVLSCTWWCCRE";
        navActionCC = "NAVLSCTCCFCCRE";
        return "Requires Edit"; // TODO get from meta
    }
    /**
     * set under edit
     * @return
     */
    public String setUnderEdit() {
        navActionWWCC = "NAVLSCTWWCCUE";
        navActionCC = "NAVLSCTCCFCCUE";
        return "Under Edit"; // TODO get from meta
    }
}
