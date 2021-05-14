/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: NavData.java,v $
 * Revision 1.3  2009/05/28 14:18:09  wendy
 * Performance cleanup
 *
 * Revision 1.2  2008/02/21 19:18:51  wendy
 * Add access to change history for relators
 *
 * Revision 1.1  2007/04/18 19:44:49  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:14  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:38:00  tony
 * This is the initial load of OPICM
 *
 * Revision 1.6  2005/09/08 17:59:04  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.5  2005/02/01 00:27:55  tony
 * JTest Second Pass
 *
 * Revision 1.4  2005/01/26 23:42:27  tony
 * JTest Formatting
 *
 * Revision 1.3  2004/11/08 19:01:40  tony
 * Improved sort logic to no longer sort multiple times on a
 * navigation reload.
 *
 * Revision 1.2  2004/03/25 23:37:20  tony
 * cr_216041310
 *
 * Revision 1.1.1.1  2004/02/10 16:59:43  tony
 * This is the initial load of OPICM
 *
 * Revision 1.10  2003/12/22 21:38:16  tony
 * 53451
 *
 * Revision 1.9  2003/08/27 17:19:26  tony
 * 51932
 *
 * Revision 1.8  2003/06/12 22:23:41  tony
 * 1.2h modification enhancements.
 *
 * Revision 1.7  2003/06/02 16:45:29  tony
 * 51024 added outOfRangeException, thus preventing multiple
 * messages.
 *
 * Revision 1.6  2003/04/18 20:10:29  tony
 * added tab placement to preferences.
 *
 * Revision 1.5  2003/04/11 20:02:30  tony
 * added copyright statements.
 *
 */
package com.ibm.eannounce.eforms.navigate;
import COM.ibm.eannounce.objects.*;
import com.ibm.eannounce.eforms.table.NavTable;
import com.ibm.eannounce.exception.*;
import java.awt.*;
import java.awt.event.*;
import javax.accessibility.AccessibleContext;
import javax.swing.event.ChangeListener;				//51932

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public interface NavData {
	/**
     * getAccessibleContext
     * @return
     * @author Anthony C. Liberto
     */
    AccessibleContext getAccessibleContext();

	/**
     * load
     * @param _eList
     * @param _reload
     * @return
     * @author Anthony C. Liberto
     */
    EntityGroup load(EntityList _eList, boolean _reload);

    /**
     * getSelectedEntityItems
     *
     * @param _new
     * @param _bEx
     * @throws com.ibm.eannounce.exception.OutOfRangeException
     * @return
     * @author Anthony C. Liberto
     */
    EntityItem[] getSelectedEntityItems(boolean _new, boolean _bEx) throws OutOfRangeException;

    /**
     * getSelectedEntityItems
     *
     * @param _i
     * @param _new
     * @param _bEx
     * @throws com.ibm.eannounce.exception.OutOfRangeException
     * @return
     * @author Anthony C. Liberto
     */
    EntityItem[] getSelectedEntityItems(int _i, boolean _new, boolean _bEx) throws OutOfRangeException;

    /**
     * getAllEntityItems
     *
     * @param _new
     * @param _bEx
     * @throws com.ibm.eannounce.exception.OutOfRangeException
     * @return
     * @author Anthony C. Liberto
     */
    EntityItem[] getAllEntityItems(boolean _new, boolean _bEx) throws OutOfRangeException;

    /**
     * getAllEntityItems
     *
     * @param _i
     * @param _new
     * @param _bEx
     * @throws com.ibm.eannounce.exception.OutOfRangeException
     * @return
     * @author Anthony C. Liberto
     */
    EntityItem[] getAllEntityItems(int _i, boolean _new, boolean _bEx) throws OutOfRangeException;

	/**
     * setMouseListener
     * @param _l
     * @author Anthony C. Liberto
     */
    void setMouseListener(MouseListener _l);
	/**
     * getMouseListener
     * @return
     * @author Anthony C. Liberto
     */
    MouseListener getMouseListener();

	/**
     * getNavigate
     * @return
     * @author Anthony C. Liberto
     */
    Navigate getNavigate();

	/**
     * setSelected
     * @param _key
     * @param _keys
     * @author Anthony C. Liberto
     */
    void setSelected(String _key, String[] _keys);
	/**
     * removeTab
     * @param _key
     * @author Anthony C. Liberto
     */
    void removeTab(String _key);
	/**
     * getTable
     * @param _key
     * @return
     * @author Anthony C. Liberto
     */
    NavTable getTable(String _key);
	/**
     * getTable
     * @return
     * @author Anthony C. Liberto
     */
    NavTable getTable();
	/**
     * getTable
     * @param _i
     * @return
     * @author Anthony C. Liberto
     */
    NavTable getTable(int _i);
	/**
     * getEntityGroup
     * @return
     * @author Anthony C. Liberto
     */
    EntityGroup getEntityGroup();
	/**
     * getEntityGroup
     * @param _i
     * @return
     * @author Anthony C. Liberto
     */
    EntityGroup getEntityGroup(int _i);
	/**
     * getEntityGroup
     * @param _key
     * @return
     * @author Anthony C. Liberto
     */
    EntityGroup getEntityGroup(String _key);
	/**
     * getEntityGroupKey
     * @return
     * @author Anthony C. Liberto
     */
    String getEntityGroupKey();
	/**
     * getMatrixActionItem
     * @return
     * @author Anthony C. Liberto
     */
    EANActionItem getMatrixActionItem();
	/**
     * getMatrixActionItem
     * @param _i
     * @return
     * @author Anthony C. Liberto
     */
    EANActionItem getMatrixActionItem(int _i);
	/**
     * getCurrentEntityItem - gets it based on row
     * @param _new
     * @return
     * @author Anthony C. Liberto
     */
    EntityItem getCurrentEntityItem(boolean _new);
	/**
     * getCurrentEntityItem - gets it based on row and column
     */
    EntityItem getCurrentEntityItem();    
	/**
     * refreshTab
     * @param _eg
     * @param _reload
     * @author Anthony C. Liberto
     */
    void refreshTab(EntityGroup _eg,boolean _reload);
	/**
     * setEnabled
     * @param _b
     * @author Anthony C. Liberto
     */
    void setEnabled(boolean _b);
	/**
     * setVisible
     * @param _b
     * @author Anthony C. Liberto
     */
    void setVisible(boolean _b);
	/**
     * getIndexOf
     * @param _eg
     * @return
     * @author Anthony C. Liberto
     */
    int getIndexOf(EntityGroup _eg);
	/**
     * getTabSelector
     * @return
     * @author Anthony C. Liberto
     */
    NavDataSelector getTabSelector();
	/**
     * refreshTitle
     * @param _eg
     * @author Anthony C. Liberto
     */
    void refreshTitle(EntityGroup _eg);
	/**
     * export
     * @return
     * @author Anthony C. Liberto
     */
    String export();
	/**
     * \setFont
     * @param _f
     * @author Anthony C. Liberto
     */
    void setFont(Font _f);
	/**
     * getFont
     * @return
     * @author Anthony C. Liberto
     */
    Font getFont();
	/**
     * requestFocus
     * @param _eg
     * @author Anthony C. Liberto
     */
    void requestFocus(EntityGroup _eg);
	/**
     * gotoTab
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    boolean gotoTab(String _s);
	/**
     * dereference
     * @author Anthony C. Liberto
     */
    void dereference();
	/**
     * isSelectorOnForm
     * @return
     * @author Anthony C. Liberto
     */
    boolean isSelectorOnForm();
	/**
     * setSelectorOnForm
     * @param _b
     * @author Anthony C. Liberto
     */
    void setSelectorOnForm(boolean _b);

	/**
     * setName
     * @param _s
     * @author Anthony C. Liberto
     */
    void setName(String _s);							//access
	/**
     * \getName
     * @return
     * @author Anthony C. Liberto
     */
    String getName();									//access

	/**
     * toggleTab
     * @param _i
     * @author Anthony C. Liberto
     */
    void toggleTab(int _i);
	/**
     * getIndexOf
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    int getIndexOf(String _s);
	/**
     * getEntityGroupCount
     * @return
     * @author Anthony C. Liberto
     */
    int getEntityGroupCount();

	/**
     * \addFocusListener
     * @param _fl
     * @author Anthony C. Liberto
     */
    void addFocusListener(FocusListener _fl);
	/**
     * removeFocusListener
     * @param _fl
     * @author Anthony C. Liberto
     */
    void removeFocusListener(FocusListener _fl);

	/**
     * addMouseListener
     * @param _ml
     * @author Anthony C. Liberto
     */
    void addMouseListener(MouseListener _ml);			//21893
	/**
     * removeMouseListener
     * @param _ml
     * @author Anthony C. Liberto
     */
    void removeMouseListener(MouseListener _ml);		//21893
	/**
     * refreshAppearance
     * @author Anthony C. Liberto
     */
    void refreshAppearance();
	/**
     * updateTabPlacement
     * @param _revalidate
     * @author Anthony C. Liberto
     */
    void updateTabPlacement(boolean _revalidate);
	/**
     * isShowing
     * @return
     * @author Anthony C. Liberto
     */
    boolean isShowing();

/*
 51932
 */
	/**
     * addChangeListener
     * @param _cl
     * @author Anthony C. Liberto
     */
    void addChangeListener(ChangeListener _cl);
	/**
     * removeChangeListener
     * @param _cl
     * @author Anthony C. Liberto
     */
    void removeChangeListener(ChangeListener _cl);

/*
 53451
 */
	/**
     * getFilterGroup
     * @return
     * @author Anthony C. Liberto
     */
    FilterGroup getFilterGroup();
	/**
     * setFilterGroup
     * @param _fg
     * @author Anthony C. Liberto
     */
    void setFilterGroup(FilterGroup _fg);
	/**
     * refresh
     * @author Anthony C. Liberto
     */
   // void refresh();
	/**
     * sort
     * @author Anthony C. Liberto
     */
    void sort();
}
