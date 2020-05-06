/*
 * Created on Jan 17, 2005
 *
 * Licensed Materials -- Property of IBM
 *
 * (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
 * The source code for this program is not published or otherwise divested of
 * its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
 */
package com.ibm.transform.oim.eacm.xalan.table;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Vector;

import COM.ibm.eannounce.objects.EANAttribute;
import COM.ibm.eannounce.objects.EANBusinessRuleException;
import COM.ibm.eannounce.objects.EANMetaAttribute;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;

import com.ibm.transform.oim.eacm.xalan.EntityParam;
import com.ibm.transform.oim.eacm.xalan.Init;
import com.ibm.transform.oim.eacm.xalan.PDHAccess;
import com.ibm.transform.oim.eacm.xalan.Table;

/**
 * An entity item attributes as a single row with the specified attributed codes as the columns
 *
 * <pre>
 * $Log: EntityTable.java,v $
 * Revision 1.3  2011/04/28 22:27:40  wendy
 * access pdh
 *
 * Revision 1.2  2006/01/26 15:24:30  wendy
 * AHE copyright
 *
 * Revision 1.1  2005/09/08 19:13:55  wendy
 * New pkg 
 *
 * Revision 1.1  2005/02/23 21:13:02  chris
 * Initial XSL Report ABR Code
 *
 * </pre>
 *
 * @author cstolpe
 */
public class EntityTable implements Table, Init, EntityParam, PDHAccess {
    private Vector columns = new Vector();
    private int nColumns = 0;
    private int nRows = 0;
    private boolean initialized = false;
    private String entityType = null;
    private int entityID = -1;
    private Database db = null;
    private Profile prof = null;
    private EntityItem ei = null;

    /**
     * Set the attribute codes for the column headers
     * @param codes
     */
    public void setColumnAttributeCodes(String[] codes) {
        columns.addAll(Arrays.asList(codes));
        nColumns = columns.size();
    }

    /**
     * Set the database if the parameter is not null.
     * @see com.ibm.transform.oim.eacm.xalan.Init#setDatabase(COM.ibm.opicmpdh.middleware.Database)
     * @return true if set
     */
    public boolean setDatabase(Database database) {
        if (database == null) {
            System.err.println("EntityTable.setDatabase(Database): database is null");  //$NON-NLS-1$
            return false;
        }
        db = database;
        return true;
    }

    /**
     * Set the profile if the parameter is not null
     * @see com.ibm.transform.oim.eacm.xalan.Init#setProfile(COM.ibm.opicmpdh.middleware.Profile)
     * @return true if set
     */
    public boolean setProfile(Profile profile) {
        if (profile == null) {
            System.err.println("EntityTable.setProfile(Profile): profile is null");  //$NON-NLS-1$
            return false;
        }
        prof = profile;
        return true;
    }

    /**
     * Checks whether the table is initialized
     * @return boolean
     */
    public boolean isInitialized() {
        return initialized;
    }

    /**
     * Initialize an instance of an EntityItem for the given entity type an ID.
     * @see com.ibm.transform.oim.eacm.xalan.Init#initialize()
     * @return true if successful
     */
    public boolean initialize() {
        if (db == null) {
            System.err.print("EntityTable.initialize(): database is null ");  //$NON-NLS-1$
            initialized = false;
            return initialized;
        }
        if (prof == null) {
            System.err.print("EntityTable.initialize(): profile is null ");  //$NON-NLS-1$
            initialized = false;
            return initialized;
        }
        try {
            EntityGroup eg = new EntityGroup(null, db, prof, entityType, "Edit");  //$NON-NLS-1$
            ei = new EntityItem(eg, prof, db, entityType, entityID);
            eg.put(ei.getKey(), ei);
            initialized = true;
            nRows = 1;
        } catch (MiddlewareRequestException e) {
            System.err.print("EntityTable.initialize(): MiddlewareRequestException ");  //$NON-NLS-1$
            System.err.println(e.getMessage());
            initialized = false;
        } catch (SQLException e) {
            System.err.print("EntityTable.initialize(): SQLException ");  //$NON-NLS-1$
            System.err.println(e.getMessage());
            initialized = false;
        } catch (MiddlewareException e) {
            System.err.print("EntityTable.initialize(): MiddlewareException ");  //$NON-NLS-1$
            System.err.println(e.getMessage());
            initialized = false;
        } catch (EANBusinessRuleException e) {
            System.err.print("EntityTable.initialize(): EANBusinessRuleException ");  //$NON-NLS-1$
            System.err.println(e.getMessage());
            initialized = false;
        }
        return initialized;
    }

    /**
     * Set the entity item to null.
     * @see com.ibm.transform.oim.eacm.xalan.Init#dereference()
     * @return true
     */
    public boolean dereference() {
        ei = null;
        return true;
    }
    /**
     * Set the entity ID of the entity item
     * @see com.ibm.transform.oim.eacm.xalan.EntityParam#setEntityID(int)
     * @param eID
     * @return true if the parameter is not less than 0
     */
    public boolean setEntityID(int eID) {
        if (eID < 0) {
            System.err.println("EntityTable.setEntityID(int): parameter is less than zero");  //$NON-NLS-1$
            return false;
        }
        this.entityID = eID;
        return true;
    }
    /**
     * Set the entity type of the entity item
     * @see com.ibm.transform.oim.eacm.xalan.EntityParam#setEntityType(java.lang.String)
     * @param eType
     * @return true if the parameter is not null or empty string
     */
    public boolean setEntityType(String eType) {
        if (eType == null) {
            System.err.println("EntityTable.setEntityType(String): parameter is null");  //$NON-NLS-1$
            return false;
        }
        if (eType.equals("")) {  //$NON-NLS-1$
            System.err.println("EntityTable.setEntityType(String): parameter is empty string");  //$NON-NLS-1$
            return false;
        }
        this.entityType = eType;
        return true;
    }

    /**
     * Get the attribute row
     * @see com.ibm.transform.oim.eacm.xalan.Table#get(int, int)
     *
     */
    public Object get(int row, int column) {
        EANAttribute attribute;
        if (column < 0 || column >= nColumns) {
            System.err.println("EntityTable.get(int, int): column is out of bounds.");  //$NON-NLS-1$
            return null;  //$NON-NLS-1$
        }
        if (row < 0 || row >= nRows) {
            System.err.println("EntityTable.get(int, int): row is out of bounds.");  //$NON-NLS-1$
            return null;  //$NON-NLS-1$
        }
        attribute = ei.getAttribute((String)columns.elementAt(column));
        return attribute;
    }

    /**
     * Get the column header for the specified column
     * @see com.ibm.transform.oim.eacm.xalan.Table#getColumnHeader(int)
     * @param column number
     * @return column description
     */
    public String getColumnHeader(int column) {
        if (column < 0 || column >= nColumns) {
            System.err.println("EntityTable.getColumnHeader(int): column is out of bounds.");  //$NON-NLS-1$
            return "";  //$NON-NLS-1$
        }
        else {
            EntityGroup eg = ei.getEntityGroup();
            if (eg != null) {
                EANMetaAttribute ma = eg.getMetaAttribute((String) columns.elementAt(column));
                if (ma != null) {
                    return ma.getActualLongDescription();
                }
            }
        }
        return "";  //$NON-NLS-1$
    }

    /**
     * The number of columns in this table is two. Attribute Description and Attribute value
     * @see com.ibm.transform.oim.eacm.xalan.Table#getColumnCount()
     * @return two
     */
    public int getColumnCount() {
        return nColumns;
    }

    /**
     * Get the number of Attributes this entity item has
     * @see com.ibm.transform.oim.eacm.xalan.Table#getRowCount()
     * @return int
     */
    public int getRowCount() {
        return nRows;
    }
}
