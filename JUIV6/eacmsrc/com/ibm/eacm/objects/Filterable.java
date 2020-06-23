//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.objects;

import COM.ibm.eannounce.objects.*;

/*********************************************************************
 * This is used for filter requests
 * @author Wendy Stimpson
 */
//$Log: Filterable.java,v $
//Revision 1.1  2012/09/27 19:39:13  wendy
//Initial code
//
public interface Filterable {

	void filter();
	void resetFilter();
	boolean isPivoted(); 
	FilterGroup getFilterGroup();
	FilterGroup getColFilterGroup();
	String getUIPrefKey();
	void setFilterGroup(FilterGroup _group);
	void setColFilterGroup(FilterGroup _group);
	Object getFilterableTable();
	boolean isFiltered();
}