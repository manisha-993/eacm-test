//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.nav;
import COM.ibm.eannounce.objects.*;

import com.ibm.eacm.objects.OutOfRangeException;
import com.ibm.eacm.table.EntityGroupTable;


/**
 * interface shared between dual nav and single nav
 * @author Wendy Stimpson
 */
//$Log: NavData.java,v $
//Revision 1.3  2013/09/19 22:12:24  wendy
//control sort when a row is updated
//
//Revision 1.2  2013/05/01 18:35:12  wendy
//perf updates for large amt of data
//
//Revision 1.1  2012/09/27 19:39:15  wendy
//Initial code
//
public interface NavData {
	/**
     * load
     * @param eList
     */
    EntityGroup loadNav(EntityList eList);

    /**
     * getSelectedEntityItems
     *
     * @param bnew
     * @param bEx
     * @throws com.ibm.eacm.objects.OutOfRangeException
     * @return
     */
    EntityItem[] getSelectedEntityItems(boolean bnew, boolean bEx) throws OutOfRangeException;
	
    /**
     * getAllEntityItems
     *
     * @param bnew
     * @param bEx
     * @throws com.ibm.eacm.objects.OutOfRangeException
     * @return
     */
    EntityItem[] getAllEntityItems(boolean bnew, boolean bEx) throws OutOfRangeException;

	/**
     * setSelected
     * @param key
     * @param keys
     */
    void setSelected(String key, String[] keys);
    void setSelectedIndex(int index);
    
	/**
     * getTable
     * @return
     */
    EntityGroupTable getTable();
	/**
     * update table
     * @return
     */
    void updateTable();
    
	/**
     * update table
     * @return
     */
    void updateTableWithSelectedRows();
    
	/**
     * getTable
     * @param i
     * @return
     */
    EntityGroupTable getTable(int i);
	/**
     * getEntityGroup
     * @return
     */
    EntityGroup getEntityGroup();

	/**
     * getEntityGroupKey
     * @return
     */
    String getEntityGroupKey();

	/**
     * getCurrentEntityItem - gets it based on row
     * @param bnew
     * @return
     */
    EntityItem getCurrentEntityItem(boolean bnew);
	/**
     * getCurrentEntityItem - gets it based on row and column
     */
    EntityItem getCurrentEntityItem();    

	/**
     * export
     * @return
     */
    String export();

	/**
     * gotoTab
     * @param s
     * @return
     */
    boolean gotoTab(String s);
	/**
     * dereference
     * 
     */
    void dereference();

	/**
     * getEntityGroupCount
     * @return
     */
    int getTabCount();

	/**
     * updateTabPlacement
     */
    void updateTabPlacement();

}
