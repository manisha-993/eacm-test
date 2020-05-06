/*
 * Created on Mar 1, 2005
 * Licensed Materials -- Property of IBM
 *
 * (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
 * The source code for this program is not published or otherwise divested of
 * its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
 */


package com.ibm.transform.oim.eacm.xalan.table;


import java.sql.SQLException;

import COM.ibm.eannounce.objects.MetaFlag;
import COM.ibm.eannounce.objects.MetaFlagAttribute;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;

import com.ibm.transform.oim.eacm.xalan.Init;
import com.ibm.transform.oim.eacm.xalan.Table;


/**
 * Creates a table for a meta flag attribute
 * <pre>
 * $Log: MetaFlagTable.java,v $
 * Revision 1.2  2006/01/26 15:24:30  wendy
 * AHE copyright
 *
 * Revision 1.1  2005/09/08 19:13:55  wendy
 * New pkg
 *
 * Revision 1.2  2005/03/02 19:11:08  chris
 * Added CVS log to class javadoc
 *
 * </pre>
 * @author cstolpe
 */
public class MetaFlagTable implements Table, Init {
    private MetaFlag[] mfa = null;
    private String attributeCode = null;
    private Database db = null;
    private Profile prof = null;

    /**
     * Gets a cell in the table. Column 0 is the flag code. Column 1 is the short description. And column 2 is the long description.
     * @see com.ibm.transform.oim.eacm.xalan.Table#get(int, int)
     */
    public Object get(int row, int column) {
        if (mfa == null) {
            return null;
        }
        switch (column) {
        case 0 :
            return mfa[row].getFlagCode();
        case 1 :
            return mfa[row].getShortDescription();
        case 2 :
            return mfa[row].getLongDescription();
        default :
            break;
        }
        return null;
    }

    /**
     * Column 0 is the "Flag Code". Column 1 is the "Short Description". And column 2 is "Long Description".
     * @see com.ibm.transform.oim.eacm.xalan.Table#getColumnHeader(int)
     */
    public String getColumnHeader(int column) {
        switch (column) {
        case 0 :
            return "Flag Code";  //$NON-NLS-1$
        case 1 :
            return "Short Description";  //$NON-NLS-1$
        case 2 :
            return "Long Description";  //$NON-NLS-1$
        default :
            break;
        }
        return null;
    }

    /**
     * Always 3. Column 0 is the flag code. Column 1 is the short description. And column 2 is the long description.
     * @see com.ibm.transform.oim.eacm.xalan.Table#getColumnCount()
     */
    public int getColumnCount() {
        return 3;
    }

    /**
     * If attribute exists there should be one or more rows.
     * @see com.ibm.transform.oim.eacm.xalan.Table#getRowCount()
     */
    public int getRowCount() {
        if (mfa == null) {
            return 0;
        }
        return mfa.length;
    }

    /**
     * Set the database.
     *
     * @param database
     * @return boolean
     */
    public boolean setDatabase(Database database) {
        db = database;
        return true;
    }

    /**
     * Set the profile.
     *
     * @param profile
     * @return boolean
     */
    public boolean setProfile(Profile profile) {
        prof = profile;
        return true;
    }

    /**
     * Create a meta flag attribute for the specified attribute code.
     * @see com.ibm.transform.oim.eacm.xalan.Init#initialize()
     */
    public boolean initialize() {
        try {
            MetaFlagAttribute matt = new MetaFlagAttribute(null, db, prof, attributeCode);
            int n = matt.getMetaFlagCount();
            mfa = new MetaFlag[n];
            for (int i = 0; i < n; i++) {
                mfa[i] = matt.getMetaFlag(i);
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
     * Set MetaFlag[] to null;
     * @see com.ibm.transform.oim.eacm.xalan.Init#dereference()
     */
    public boolean dereference() {
        mfa = null;
        return true;
    }

    /**
     * Set the attribute code of the meta flag
     * @param code
     * @return
     */
    public String setAttributeCode(String code) {
        attributeCode = code;
        return code;
    }

}
