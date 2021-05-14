// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package com.ibm.eannounce.eforms.navigate;
import java.awt.event.*;
import COM.ibm.eannounce.objects.*;

/**
* This interface is shared by NavHistList and NavHistBox to display history and allow user to 
* go back by selecting a history item.
*
* $Log: NavHist.java,v $
* Revision 1.3  2009/05/28 14:18:09  wendy
* Performance cleanup
*
* Revision 1.2  2009/04/16 17:54:42  wendy
* Cleanup history
*
* Revision 1.1  2007/04/18 19:44:49  wendy
* Reorganized JUI module
*
* Revision 1.2  2005/09/12 19:03:14  tony
* JTest Modifications
*
* Revision 1.1.1.1  2005/09/09 20:38:01  tony
* This is the initial load of OPICM
*
*/
public interface NavHist {
	/**
     * load
     * @param _nso
     * @param _eList
     * @param _remove
     * 
     */
    void load(NavSerialObject _nso, EntityList _eList, boolean _remove);	//20105

	/**
     * setVisible
     * @param _b
     * 
     */
    void setVisible(boolean _b);
	/**
     * setEnabled
     * @param _b
     * 
     */
    void setEnabled(boolean _b);

	/**
     * reset
     * 
     */
    void reset();
	/**
     * dereference
     * 
     */
    void dereference();

	/**
     * backup
     * 
     */
    void backup();

	/**
     * getHistory
     * @param _i
     * @return
     * 
     */
    Object[] getHistory(int _i);
	/**
     * load
     * @param hist
     * @param _navType
     * 
     */
    void load(NavHist hist, int _navType);
	/**
     * remove
     * @param _s
     * 
     */
    void remove(String _s);

	/**
     * setName
     * @param _s
     * 
     */
    void setName(String _s);							//access
	/**
     * getName
     * @return
     * 
     */
    String getName();									//access

	/**
     * getSelectedIndex
     * @return
     * 
     */
    int getSelectedIndex();
	/**
     * getHistoryCount
     * @return
     * 
     */
    int getHistoryCount();
	/**
     * removeHistory
     * @param _i
     * @param _below
     * 
     */
    void removeHistory(int _i, boolean _below);

	/**
     * addFocusListener
     * @param _fl
     * 
     */
    void addFocusListener(FocusListener _fl);
	/**
     * removeFocusListener
     * @param _fl
     * 
     */
    void removeFocusListener(FocusListener _fl);
	/**
     * refreshAppearance
     * 
     */
    void refreshAppearance();

	/**
     * getToolTipText
     * @return
     * 
     */
    String getToolTipText();

	/**
     * getEntityItems
     * 
     * @param _i
     * @return
     */
   // EntityItem[] getEntityItems(int _i); //cr_7342
/*
 cr_1210035324
 */
	/**
     * getNavigationHistory
     * @return
     * 
     */
    EANActionItem[] getNavigationHistory();
	/**
     * loadBookmarkHistory
     * @param _book
     * 
     */
    void loadBookmarkHistory(BookmarkItem _book);

    /**
     * reselect Index
     * PKUR-6CFDY6
     * 
     */
    void reselectIndex(int _i);
    /*
     * Used in cell renderer
     */
    boolean isPickList(Object value);
}

