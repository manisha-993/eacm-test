/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: ETabable.java,v $
 * Revision 1.2  2009/05/28 13:57:50  wendy
 * Performance cleanup
 *
 * Revision 1.1  2007/04/18 19:42:17  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:37:38  tony
 * This is the initial load of OPICM
 *
 * Revision 1.6  2005/09/08 17:58:55  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.5  2005/03/28 17:56:37  tony
 * MN_button_disappear
 * adjust to preven java bug 4664818
 *
 * Revision 1.4  2005/02/02 21:30:07  tony
 * JTest Second Pass
 *
 * Revision 1.3  2005/01/27 23:18:19  tony
 * JTest Formatting
 *
 * Revision 1.2  2004/02/20 22:17:58  tony
 * chatAction
 *
 * Revision 1.1.1.1  2004/02/10 16:59:25  tony
 * This is the initial load of OPICM
 *
 * Revision 1.19  2003/10/17 18:01:52  tony
 * 52614
 *
 * Revision 1.18  2003/09/30 16:33:57  tony
 * kehrli_20030929 --
 * logic enhancement to improve modification
 * ability of tab icon, title, tip, and menu items for
 * future modifications.
 *
 * Revision 1.17  2003/09/12 16:15:25  tony
 * 52189
 *
 * Revision 1.16  2003/08/28 18:36:01  tony
 * 51975
 *
 * Revision 1.15  2003/07/29 16:58:29  tony
 * 51555
 *
 * Revision 1.14  2003/07/18 18:59:08  tony
 * acl_20030718 added selectKeys logic as guest
 * Entity  = current entity
 * Entity1 = parent Entity
 * Entity2 = Child Entity
 * then ',' then keys separated by ','.
 *
 * Revision 1.13  2003/07/11 17:00:15  tony
 * updated logic to perform default functions for
 * the preference panel.
 *
 * Revision 1.12  2003/07/03 16:38:03  tony
 * improved scripting logic.
 *
 * Revision 1.11  2003/07/02 21:42:29  tony
 * updated logging to allow parsing of log file to
 * auto navigate development to the proper location
 * via the proper path.
 *
 * Revision 1.10  2003/07/02 16:43:23  tony
 * updated logging capabilities to allow for play back of
 * log files.
 *
 * Revision 1.9  2003/04/18 20:10:30  tony
 * added tab placement to preferences.
 *
 * Revision 1.8  2003/04/11 20:02:26  tony
 * added copyright statements.
 *
 */
package com.elogin;
//import com.ibm.eannounce.eserver.ChatAction;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.Profile;
import java.awt.event.ActionEvent;
import javax.swing.Icon; //kerhli_20030929

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public abstract interface ETabable {
    /**
     * getParentKey
     * @return
     * @author Anthony C. Liberto
     */
    String getParentKey();
    /**
     * setParentKey
     * @param _key
     * @author Anthony C. Liberto
     */
    void setParentKey(String _key);
    /**
     * shouldRefresh
     * @return
     * @author Anthony C. Liberto
     */
    boolean shouldRefresh(); //acl_20021022
    /**
     * setShouldRefresh
     * @param _b
     * @author Anthony C. Liberto
     */
    void setShouldRefresh(boolean _b); //acl_20021022
    /**
     * setShouldRefresh
     * @param _eType
     * @param _opwg
     * @param _code
     * @author Anthony C. Liberto
     */
    void setShouldRefresh(String _eType, int _opwg, int _code); //acl_20021022
    /**
     * setOPWGID
     * @param _id
     * @author Anthony C. Liberto
     */
    void setOPWGID(int _id);
    /**
     * getOPWGID
     * @return
     * @author Anthony C. Liberto
     */
    int getOPWGID();
    /**
     * refresh
     * @param _breakable
     * @author Anthony C. Liberto
     */
    void refresh(boolean _breakable);
    /**
     * actionPerformed
     * @param _ae
     * @author Anthony C. Liberto
     */
    void actionPerformed(ActionEvent _ae);
    /**
     * getEntityType
     * @param _i
     * @return
     * @author Anthony C. Liberto
     */
    String getEntityType(int _i);
    /**
     * contains
     * @param _ei
     * @param _eai
     * @return
     * @author Anthony C. Liberto
     */
    boolean contains(EntityItem[] _ei, EANActionItem _eai); //20059
    /**
     * getSearchableObject
     * @return
     * @author Anthony C. Liberto
     */
    Object getSearchableObject(); //22377
    /**
     * getHelpText
     * @return
     * @author Anthony C. Liberto
     */
    String getHelpText();
    /**
     * requestFocus
     * @author Anthony C. Liberto
     */
    void requestFocus();
    /**
     * getPanelType
     * @return
     * @author Anthony C. Liberto
     */
    String getPanelType();
    /**
     * getVisiblePanelType
     * @return
     * @author Anthony C. Liberto
     */
    String getVisiblePanelType();
    /**
     * isPanelType
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    boolean isPanelType(String _s);
    /**
     * getTableTitle
     * @return
     * @author Anthony C. Liberto
     */
    String getTableTitle();
    /**
     * close
     * @return
     * @author Anthony C. Liberto
     */
    boolean close();
    //52614	public abstract void select();
    /**
     * select
     * @param _closeAll
     * @author Anthony C. Liberto
     */
    void select(boolean _closeAll); //52614
    /**
     * deselect
     * @author Anthony C. Liberto
     */
    void deselect(); //52189
    /**
     * dereference
     * @author Anthony C. Liberto
     */
    void dereference();
    /**
     * getProfile
     * @return
     * @author Anthony C. Liberto
     */
    Profile getProfile();
    /**
     * getParentProfile
     * @return
     * @author Anthony C. Liberto
     */
    Profile getParentProfile();
    /**
     * setParentProfile
     * @param _p
     * @author Anthony C. Liberto
     */
    void setParentProfile(Profile _p);
    /**
     * refreshAppearance
     * @author Anthony C. Liberto
     */
    void refreshAppearance();

    /**
     * setGif
     * @param _s
     * @author Anthony C. Liberto
     */
    void setGif(String _s);
    /**
     * getGif
     * @return
     * @author Anthony C. Liberto
     */
    String getGif();
    /**
     * updateTabPlacement
     * @param _revalidate
     * @author Anthony C. Liberto
     */
    void updateTabPlacement(boolean _revalidate);

    /**
     * process
     * @param _method
     * @param _action
     * @param _parent
     * @param _child
     * @author Anthony C. Liberto
     */
    void process(String _method, String _action, String[] _parent, String[] _child);
    /**
     * selectKeys
     * @param _keys
     * @author Anthony C. Liberto
     */
    void selectKeys(String[] _keys); //acl_20030718
    /*
     51975
     */
    /**
     * getParentTab
     * @return
     * @author Anthony C. Liberto
     */
    ETabable getParentTab();
    /**
     * setParentTab
     * @param _tab
     * @author Anthony C. Liberto
     */
    void setParentTab(ETabable _tab);
    /**
     * addChildTab
     * @param _tab
     * @author Anthony C. Liberto
     */
    void addChildTab(ETabable _tab);
    /**
     * removeChildTab
     * @param _tab
     * @return
     * @author Anthony C. Liberto
     */
    ETabable removeChildTab(ETabable _tab);
    /**
     * dereferenceRelated
     * @author Anthony C. Liberto
     */
    //void dereferenceRelated();
    /*
     kehrli_20030929
     */
    /**
     * getTabToolTipText
     * @return
     * @author Anthony C. Liberto
     */
    String getTabToolTipText();
    /**
     * getTabTitle
     * @return
     * @author Anthony C. Liberto
     */
    String getTabTitle();
    /**
     * getTabMenuTitle
     * @return
     * @author Anthony C. Liberto
     */
    String getTabMenuTitle();
    /**
     * getTabIcon
     * @return
     * @author Anthony C. Liberto
     */
    Icon getTabIcon();

    /**
     * getChatAction
     * @return
     * @author Anthony C. Liberto
     */
    //ChatAction getChatAction();

    /**
     * refresh toolbar
     * MN_button_disappear
     * @author Anthony C. Liberto
     */
    void refreshToolbar();
}
