// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
/** CR1008045141
LSWWABR1
 * This table contains the following 4 columns:
 * 0 LSCUR."LSCURID",
 * 1 "LSCRSID",
 * 2 "LSCRGLOBALREPTITLE",
 * 3 "LSCRSEXPDATE"
 *
 * It finds All Available Classroom In-Country (LSWWCC) courses for a LSWW
 * It is invoked after navigating to a particular LSWW
 *
 * @author Wendy Stimpson
 * Created on Feb 25, 2005
 *
 * $Log: AvailClassroomWWTable.java,v $
 * Revision 1.6  2011/04/28 22:28:12  wendy
 * access pdh
 *
 * Revision 1.5  2006/01/25 19:51:43  wendy
 * AHE copyright
 *
 * Revision 1.4  2005/10/03 19:32:26  wendy
 * Conform to new jtest config
 *
 * Revision 1.3  2005/09/30 19:14:50  wendy
 * jtest complained about see tags, removed them
 *
 * Revision 1.2  2005/09/09 13:33:26  wendy
 * remove eannounce11 ref
 *
 * Revision 1.1  2005/09/08 19:14:46  wendy
 * New pkg
 *
 * Revision 1.4  2005/04/14 01:20:14  wendy
 * Removed EntityGroupTable
 *
 * Revision 1.3  2005/04/13 13:02:16  wendy
 * Add output revision number
 *
 * Revision 1.2  2005/04/06 14:16:37  wendy
 * Use VE with filters
 *
 * Revision 1.1  2005/02/28 16:59:58  wendy
 * Init for CR1008045141
 *
 *
 */
package com.ibm.transform.oim.eacm.xalan.table.ls;

import java.text.MessageFormat;

import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;

import com.ibm.transform.oim.eacm.xalan.*;
import com.ibm.transform.oim.eacm.util.PokUtils;
 

public class AvailClassroomWWTable implements Table, Init, EntityParam, PDHAccess {
    // class name, method name, parameter
    private final MessageFormat PARAM1NULL = new MessageFormat("{0}.{1}({2}): {2} is null.");  //$NON-NLS-1$
    private Object[] mfParams = {
        "AvailClassroomWWTable",  //$NON-NLS-1$
        "",  //$NON-NLS-1$
        ""  //$NON-NLS-1$
    };
    private EntityGroup wwccEGrp = null;
    private EANAttribute lscurAttr = null;
    private String lscurAttrDesc="Curriculum ID"; //$NON-NLS-1$
    private Database db = null;
    private Profile prof = null;
    private String entityType = null;
    private EntityList list=null;
    private int entityID = -1;
    private final static  String[] ATTR_CODES = {
            "LSCRSID",                  //$NON-NLS-1$
            "LSCRGLOBALREPTITLE",       //$NON-NLS-1$
            "LSCRSEXPDATE"              //$NON-NLS-1$
        };


    /**
     *  (non-Javadoc)
     * @see com.ibm.transform.oim.eacm.xalan.Table#get(int, int)
     */
    public Object get(int row, int column)
    {
        Object value=null;
        /* These are the columns
         * 0 LSCUR."LSCURID",
         * 1 "LSCRSID",
         * 2 "LSCRGLOBALREPTITLE",
         * 3 "LSCRSEXPDATE"
         */
        if (wwccEGrp!=null) {
            if (row < wwccEGrp.getEntityItemCount()) {
                if (column==0)  {
                    value = lscurAttr;  // same for all rows
                }
                else {
                    value = wwccEGrp.getEntityItem(row).getAttribute(ATTR_CODES[column-1]);
                }
            }
        }
        return value;
    }

    /**
     *  (non-Javadoc)
     * @see com.ibm.transform.oim.eacm.xalan.Table#getColumnHeader(int)
     */
    public String getColumnHeader(int column) {
        String header=null;
        String attributeCode;
        EANMetaAttribute ma;
        if (wwccEGrp==null) {
            header= "NULL LSWWCC";  //$NON-NLS-1$
        }
        else{
            if (column < 0 || column >= getColumnCount()) {
                header= "BAD colid "+column;  //$NON-NLS-1$
            }
            else {
                if (column==0)  {
                    header= lscurAttrDesc;
                }
                else {
                    attributeCode = ATTR_CODES[column-1];
                    ma = wwccEGrp.getMetaAttribute(attributeCode);
                    if (ma == null) {
                        header= "BAD Attr "+attributeCode;  //$NON-NLS-1$
                    }
                    else{
                        header= ma.getActualLongDescription();
                    }
                }
            }
        }
        return header;
    }

    /**
     *  (non-Javadoc)
     * @see com.ibm.transform.oim.eacm.xalan.Table#getColumnCount()
     */
    public int getColumnCount() {
        int cnt=0;
        if (wwccEGrp!=null) {
            cnt=ATTR_CODES.length+1;
        }
        return cnt;
    }

    /**
     * Sum of WWCC and CC rows
     * @see com.ibm.transform.oim.eacm.xalan.Table#getRowCount()
     */
    public int getRowCount() {
        int cnt=0;
        if (wwccEGrp!=null) {
            cnt= wwccEGrp.getEntityItemCount();
        }
        return cnt;
    }

    /** (non-Javadoc)
     * @see com.ibm.transform.oim.eacm.xalan.Init#initialize()
     */
    public boolean initialize()
    {
        boolean ok=true;
        String extractName = "EXABR1WWCCF1"; //$NON-NLS-1$
        try {
            EntityGroup curGrp;
            list = db.getEntityList(
                prof,
                new ExtractActionItem(null, db, prof, extractName),
                new EntityItem[] { new EntityItem(null, prof, entityType, entityID) });

            // get LSCUR parent, assumes one and only one
            curGrp = list.getEntityGroup("LSCUR");//$NON-NLS-1$

            // get curriculum info
            if (curGrp!=null && curGrp.getEntityItemCount()>0)
            {
                lscurAttr = curGrp.getEntityItem(0).getAttribute("LSCURID");//$NON-NLS-1$
                lscurAttrDesc = PokUtils.getAttributeDescription(curGrp,"LSCURID", //$NON-NLS-1$
                    "Curriculum ID"); //$NON-NLS-1$
            }

            // LSWWCC are children,  extract filters on 'available' and classroom, just use entity group
            wwccEGrp = list.getEntityGroup("LSWWCC");  //$NON-NLS-1$
        } catch (Exception e) {
            System.err.print("AvailClassroomWWTable.initialize(): Exception ");  //$NON-NLS-1$
            System.err.println(e.getMessage());
            e.printStackTrace();
            ok=false;
        }

        return ok;
    }

    /**
     *  (non-Javadoc)
     * @see com.ibm.transform.oim.eacm.xalan.Init#dereference()
     */
    public boolean dereference() {
        wwccEGrp = null;
        list.dereference();
        list = null;

        for (int i=0; i<mfParams.length; i++) {
            mfParams[i]=null;
        }
        db = null;
        prof = null;
        entityType = null;
        lscurAttr = null;
        lscurAttrDesc=null;
        return true;
    }

    /**
     *  set entity type
     *
     * @return boolean
     * @param eType
     */
    public boolean setEntityType(String eType) {
        entityType = eType;
        return true;
    }

    /**
     *  set entityid
     * @see com.ibm.transform.oim.eacm.xalan.EntityParam#setEntityID(int)
     */
    public boolean setEntityID(int eID) {
        entityID = eID;
        return true;
    }
    /**
     *  set database
     *
     * @return boolean
     * @param database
     */
    public boolean setDatabase(Database database) {
        boolean ok=false;
        if (database == null) {
            mfParams[1] = "setDatabase";  //$NON-NLS-1$
            mfParams[2] = "Database";  //$NON-NLS-1$
            System.err.println(PARAM1NULL.format(mfParams));
            ok=false;
        }
        else {
            db = database;
            ok=true;
        }
        return ok;
    }

    /**
     *  set profile
     *
     * @return boolean
     * @param profile
     */
    public boolean setProfile(Profile profile) {
        boolean ok=false;
        if (profile == null) {
            mfParams[1] = "setProfile";  //$NON-NLS-1$
            mfParams[2] = "Profile";  //$NON-NLS-1$
            System.err.println(PARAM1NULL.format(mfParams));
            ok=false;
        }
        else {
            prof = profile;
            ok= true;
        }
        return ok;
    }
    /**
     * The CVS Revision
     *
     * @return String
     */
    public String getVersion() {
        return "$Revision: 1.6 $";  //$NON-NLS-1$
    }
}
