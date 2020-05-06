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

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Vector;

import COM.ibm.eannounce.objects.EANAttribute;
import COM.ibm.eannounce.objects.EANMetaAttribute;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;

import com.ibm.transform.oim.eacm.xalan.Table;

/**
 * Makes an EntityGroup look like a Table
 *
 * <pre>
 * $Log: EntityGroupTable.java,v $
 * Revision 1.2  2006/01/26 15:24:30  wendy
 * AHE copyright
 *
 * Revision 1.1  2005/09/08 19:13:55  wendy
 * New pkg
 *
 * Revision 1.2  2005/02/28 14:50:30  wendy
 * Added dereference()
 *
 * Revision 1.1  2005/02/23 21:13:02  chris
 * Initial XSL Report ABR Code
 *
 * </pre>
 *
 * @author cstolpe
 */
public class EntityGroupTable implements Table {
    private Vector columns = new Vector();
    private int nColumns = 0;
    private int nRows = 0;
    private EntityGroup eg = null;

    /**
     * Set the EntityGroup this table will represent.
     * @param entityGroup
     */
    public void setEntityGroup(EntityGroup entityGroup) {
        eg = entityGroup;
        nRows = eg.getEntityItemCount();
    }

    /**
     * Set the attribute codes for the column headers
     * @param codes
     */
    public void setColumnAttributeCodes(String[] codes) {
        columns.addAll(Arrays.asList(codes));
        nColumns = columns.size();
    }

    /**
     *  (non-Javadoc)
     * @see com.ibm.transform.oim.eacm.xalan.Table#get(int, int)
     */
    public Object get(int row, int column) {
        EntityItem ei;
        if (eg == null) {
            System.err.println(eg.getEntityType() + " EntityGroupTable.get(int, int): entity group is null.");  //$NON-NLS-1$
            return null;  //$NON-NLS-1$
        }
        if (column < 0 || column >= nColumns) {
            System.err.println(eg.getEntityType() + " EntityGroupTable.get(int, int): column "+column+" is out of bounds.");  //$NON-NLS-1$
            return null;  //$NON-NLS-1$
        }
        if (row < 0 || row >= nRows) {
            System.err.println(eg.getEntityType() + " EntityGroupTable.get(int, int): row "+row+" is out of bounds.");  //$NON-NLS-1$
            return null;  //$NON-NLS-1$
        }
        ei = eg.getEntityItem(row);
        return ei.getAttribute((String) columns.elementAt(column));
    }

    /**
     * Get the long description from the meta attribute for the column
     * @see com.ibm.transform.oim.eacm.xalan.Table#getColumnHeader(int)
     */
    public String getColumnHeader(int column) {
        String attributeCode;
        EANMetaAttribute ma;
        if (eg == null) {
            System.err.println(eg.getEntityType() + " EntityGroupTable.getColumnHeader(int): entity group is null.");  //$NON-NLS-1$
            return "";  //$NON-NLS-1$
        }
        if (column < 0 || column >= nColumns) {
            System.err.println(eg.getEntityType() + " EntityGroupTable.getColumnHeader(int): column "+column+" is out of bounds.");  //$NON-NLS-1$
            return "";  //$NON-NLS-1$
        }
        attributeCode = (String) columns.elementAt(column);
        ma = eg.getMetaAttribute(attributeCode);
        if (ma == null) {
            MessageFormat mf = new MessageFormat(eg.getEntityType() + "EntityGroupTable.getColumnHeader(int): attribute code {0}  not found in meta for {1}."); //$NON-NLS-1$
            String[] mfArgs = new String[2];
            mfArgs[0] = attributeCode;
            mfArgs[1] = eg.getEntityType();
            System.err.println(mf.format(mfArgs));
            return "";  //$NON-NLS-1$
        }
        return ma.getActualLongDescription();
    }

    /**
     *  (non-Javadoc)
     * @see com.ibm.transform.oim.eacm.xalan.Table#getColumnCount()
     */
    public int getColumnCount() {
        return nColumns;
    }

    /**
     *  (non-Javadoc)
     * @see com.ibm.transform.oim.eacm.xalan.Table#getRowCount()
     */
    public int getRowCount() {
        return nRows;
    }

    /**
     *  Release memory
     */
    public void dereference()
    {
        EntityItem eiArray[] = eg.getEntityItemsAsArray();
        for (int i=0;i<eiArray.length; i++)
        {
            EntityItem eitem = eiArray[i];
            eg.removeEntityItem(eitem);
            for (int iy = 0; iy < eitem.getAttributeCount();iy++) {
                EANAttribute att = eitem.getAttribute(iy);
                att.setParent(null);
            }
            eitem.setParent(null);
            eiArray[i]=null;
        }
        eg=null;
        columns.clear();
        columns=null;
    }
}
