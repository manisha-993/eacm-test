//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.objects;


import java.util.List;

import javax.swing.RowSorter;
import javax.swing.RowSorter.SortKey;

/*********************************************************************
 * This is used for sort requests
 * @author Wendy Stimpson
 */
//$Log: Sortable.java,v $
//Revision 1.2  2013/07/29 18:25:17  wendy
//allow sort dialog to show current keys
//
//Revision 1.1  2012/09/27 19:39:13  wendy
//Initial code
//
public interface Sortable {

	SortColumn[] getVisibleColumnNames(); 
	void sort(List <RowSorter.SortKey> list);
	List<? extends SortKey> getSortKeys();
	String getTableTitle();
}
