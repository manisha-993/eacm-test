// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eannounce.eforms.edit;

import COM.ibm.eannounce.objects.EANMetaAttribute;
import COM.ibm.eannounce.objects.RowSelectableTable;

/**********************************************************************************
 * Used for interacting with component controlling edit
 */
// $Log: Editable.java,v $
// Revision 1.1  2009/05/22 14:16:05  wendy
// Performance cleanup
//
public interface Editable {
	boolean commitDefault();
	void historyInfo();
	void cancelDefault();
	void updateModel(RowSelectableTable _t);
	void prepareToEdit();
	Object getSelectedObject();
	boolean saveCurrentEdit();
	void selectKeys(String[] _s);
	void highlight(String[] _s);
	void refreshTable(boolean _b);
	void select();
	void deselect();
	boolean hasHiddenAttributes();
	void showHide(boolean _b);
	boolean isPasteable();
	boolean isEditing();
	void cancelEdit();
	boolean canStopEditing();
	EANMetaAttribute getSelectedEANMetaAttribute();
	boolean isFiltered();
	void deactivateAttribute();
	void stopEditing();
	void selectAll();
	void invertSelection();
	String getSelectionKey();
	boolean hasFocus();
	void showFilter();
	void showEffectivity();
	void sort(boolean _ascending);
	void fillClear();
	void fillPaste();
	void fillCopy(boolean _row);
	void spellCheck();
}
