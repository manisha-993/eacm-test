/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: NavAction.java,v $
 * Revision 1.1  2007/04/18 19:44:49  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:00  tony
 * This is the initial load of OPICM
 *
 * Revision 1.5  2005/09/08 17:59:04  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.4  2005/02/01 00:27:55  tony
 * JTest Second Pass
 *
 * Revision 1.3  2005/01/26 23:42:26  tony
 * JTest Formatting
 *
 * Revision 1.2  2005/01/07 18:05:46  tony
 * rm_20050107
 * adjusted logic to allow for menu items and toolbar containers
 * to be populated by an array of keys instead of just a single one.
 *
 * Revision 1.1.1.1  2004/02/10 16:59:43  tony
 * This is the initial load of OPICM
 *
 * Revision 1.6  2003/12/30 20:41:26  tony
 * cr_3312
 *
 * Revision 1.5  2003/06/19 16:09:41  tony
 * 51298 -- limited capability when working in the past.
 * updated past date logic.
 *
 * Revision 1.4  2003/05/14 21:34:39  tony
 * enhanced logic by adding test information.
 *
 * Revision 1.3  2003/04/03 16:19:08  tony
 * changed refreshLookAndFeel to refreshAppearance()
 * updated logic to take into account displayed dialogs
 * as well.
 *
 * Revision 1.2  2003/03/11 00:33:24  tony
 * accessibility changes
 *
 * Revision 1.1.1.1  2003/03/03 18:03:48  tony
 * This is the initial load of OPICM
 *
 * Revision 1.10  2002/11/07 16:58:29  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.navigate;
import COM.ibm.eannounce.objects.*;
import java.awt.*;
import java.awt.event.FocusListener;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public interface NavAction {
//51298	public abstract void load(EntityGroup _eg);
//51298	public abstract void load(RowSelectableTable _table, String _title);
	/**
     * load
     * @param _ean
     * @param _title
     * @author Anthony C. Liberto
     */
    void load(EANActionItem[] _ean, String _title);				//51298
	/**
     * getSelectedActionItem
     * @return
     * @author Anthony C. Liberto
     */
    EANActionItem getSelectedActionItem();
	/**
     * \getActionItem
     * @param _key
     * @return
     * @author Anthony C. Liberto
     */
    EANActionItem getActionItem(String _key);
	/**
     * getActionItemArray
     * @param _key
     * @return
     * @author Anthony C. Liberto
     */
    EANActionItem[] getActionItemArray(String _key);
	/**
     * \navigate
     * @param _i
     * @author Anthony C. Liberto
     */
    void navigate(int _i);
	/**
     * navigate
     * @author Anthony C. Liberto
     */
    void navigate();
	/**
     * getNavigate
     * @return
     * @author Anthony C. Liberto
     */
    Navigate getNavigate();
	/**
     * setVisible
     * @param _b
     * @author Anthony C. Liberto
     */
    void setVisible(boolean _b);
	/**
     * setEnabled
     * @param _b
     * @author Anthony C. Liberto
     */
    void setEnabled(boolean _b);
	/**
     * setBackground
     * @param _c
     * @author Anthony C. Liberto
     */
    void setBackground(Color _c);
	/**
     * setForeground
     * @param _c
     * @author Anthony C. Liberto
     */
    void setForeground(Color _c);
	/**
     * setFont
     * @param _f
     * @author Anthony C. Liberto
     */
    void setFont(Font _f);
	/**
     * dereference
     * @author Anthony C. Liberto
     */
    void dereference();

	/**
     * setName
     * @param _s
     * @author Anthony C. Liberto
     */
    void setName(String _s);							//access
	/**
     * getName
     * @return
     * @author Anthony C. Liberto
     */
    String getName();									//access

	/**
     * addFocusListener
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
     * clear
     * @author Anthony C. Liberto
     */
    void clear();										//22862
	/**
     * refreshAppearance
     * @author Anthony C. Liberto
     */
    void refreshAppearance();
	/**
     * expandAll
     * @param _b
     * @author Anthony C. Liberto
     */
    void expandAll(boolean _b);							//cr_3312
/*
 rm_20050107
 */
	/**
     * \getActionItemArray
     * @param _key
     * @return
     * @author Anthony C. Liberto
     */
    EANActionItem[] getActionItemArray(String[] _key);
}

