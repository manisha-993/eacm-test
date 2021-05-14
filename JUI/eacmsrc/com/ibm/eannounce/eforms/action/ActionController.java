/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: ActionController.java,v $
 * Revision 1.5  2009/05/26 13:00:13  wendy
 * Performance cleanup
 *
 * Revision 1.4  2008/08/08 21:52:12  wendy
 * CQ00006067-WI : LA CTO - More support for QueryAction
 *
 * Revision 1.3  2008/08/04 14:12:10  wendy
 * CQ00006067-WI : LA CTO - Added support for QueryAction
 *
 * Revision 1.2  2008/01/30 16:27:03  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:43:53  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:11  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:51  tony
 * This is the initial load of OPICM
 *
 * Revision 1.12  2005/09/08 17:59:01  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.11  2005/03/28 17:56:36  tony
 * MN_button_disappear
 * adjust to preven java bug 4664818
 *
 * Revision 1.10  2005/02/04 15:22:09  tony
 * JTest Format Third Pass
 *
 * Revision 1.9  2005/02/03 16:38:52  tony
 * JTest Second Pass Wrapup
 *
 * Revision 1.8  2005/02/01 20:48:33  tony
 * JTest Second Pass
 *
 * Revision 1.7  2005/01/27 19:15:15  tony
 * JTest Format
 *
 * Revision 1.6  2005/01/05 23:47:16  tony
 * 6554
 * added edit capability to whereused action.
 *
 * Revision 1.5  2004/11/19 18:01:50  tony
 * imporoved accessibilitiy and replayable log file functionatlity.
 *
 * Revision 1.4  2004/10/28 22:55:59  tony
 * updated logic for the memory enhanced middleware
 *
 * Revision 1.3  2004/03/30 16:30:54  tony
 * 6951 refined.
 *
 * Revision 1.2  2004/02/20 22:17:59  tony
 * chatAction
 *
 * Revision 1.1.1.1  2004/02/10 16:59:36  tony
 * This is the initial load of OPICM
 *
 * Revision 1.41  2003/12/30 20:42:39  tony
 * 53476
 *
 * Revision 1.40  2003/12/17 19:14:18  tony
 * acl_20031217
 * updated hidden logic and added comment for previous token
 *
 * Revision 1.39  2003/12/16 20:23:22  tony
 * 53412
 *
 * Revision 1.38  2003/10/29 19:10:42  tony
 * acl_20031029
 *
 * Revision 1.37  2003/10/29 00:20:24  tony
 * REPLAYABLE_LOGFILE
 *
 * Revision 1.36  2003/10/20 16:37:13  tony
 * memory update.  Updated dereference logic to close memory gaps
 *
 * Revision 1.35  2003/10/17 18:02:16  tony
 * 52614
 *
 * Revision 1.34  2003/10/08 20:10:00  tony
 * 52476
 *
 * Revision 1.33  2003/09/30 21:29:14  tony
 * acl_20030930
 * repaired logic to allow for invocation of whereused from
 * matrix when matrix launched from whereused and the
 * whereused parent is closed.
 *
 * Revision 1.32  2003/09/30 16:34:49  tony
 * kehrli_20030929 --
 * logic enhancement to improve modification
 * ability of tab icon, title, tip, and menu items for
 * future modifications.
 *
 * Revision 1.31  2003/09/12 16:14:54  tony
 * 52189
 *
 * Revision 1.30  2003/08/28 18:36:02  tony
 * 51975
 *
 * Revision 1.29  2003/08/26 21:17:09  tony
 * cr_TBD_3
 * update whereused-matrix & matrix-whereused functionality.
 *
 * Revision 1.28  2003/07/29 16:58:29  tony
 * 51555
 *
 * Revision 1.27  2003/07/23 20:41:57  tony
 * added and enhanced restore logic
 *
 * Revision 1.26  2003/07/18 18:59:08  tony
 * acl_20030718 added selectKeys logic as guest
 * Entity  = current entity
 * Entity1 = parent Entity
 * Entity2 = Child Entity
 * then ',' then keys separated by ','.
 *
 * Revision 1.25  2003/07/11 17:00:16  tony
 * updated logic to perform default functions for
 * the preference panel.
 *
 * Revision 1.24  2003/07/07 14:55:26  tony
 * added process methods
 *
 * Revision 1.23  2003/07/03 17:26:55  tony
 * updated logic to improve scripting
 *
 * Revision 1.22  2003/07/03 16:38:04  tony
 * improved scripting logic.
 *
 * Revision 1.21  2003/07/02 21:42:30  tony
 * updated logging to allow parsing of log file to
 * auto navigate development to the proper location
 * via the proper path.
 *
 * Revision 1.20  2003/07/02 16:43:24  tony
 * updated logging capabilities to allow for play back of
 * log files.
 *
 * Revision 1.19  2003/06/13 17:32:51  tony
 * 51255 resize on refresh issue.
 *
 * Revision 1.18  2003/06/05 22:11:48  tony
 * 51160
 *
 * Revision 1.17  2003/05/29 21:20:44  tony
 * adjusted setParm logic for single parm string.
 *
 * Revision 1.16  2003/05/23 17:07:39  tony
 * 50892
 *
 * Revision 1.15  2003/05/09 16:51:27  tony
 * updated filter icon enabling for filter.
 *
 * Revision 1.14  2003/05/02 17:52:23  tony
 * 50495
 *
 * Revision 1.13  2003/05/01 17:13:37  tony
 * 50471
 *
 * Revision 1.12  2003/04/18 20:10:29  tony
 * added tab placement to preferences.
 *
 * Revision 1.11  2003/04/11 17:32:57  tony
 * added tooltip to session label.
 *
 * Revision 1.10  2003/04/03 18:51:48  tony
 * adjusted logic to display individualized icon
 * for each frameDialog/tab.
 *
 * Revision 1.9  2003/04/03 16:19:07  tony
 * changed refreshLookAndFeel to refreshAppearance()
 * updated logic to take into account displayed dialogs
 * as well.
 *
 * Revision 1.8  2003/04/02 19:53:38  tony
 * adjusted logic.  Everytime a new tab is launched the
 * system must grab a new instance of the profile.
 * This will aid in session tagging.
 *
 * Revision 1.7  2003/04/02 17:55:52  tony
 * adjusted so that session label is always present.
 *
 * Revision 1.6  2003/04/02 00:52:37  tony
 * session tagging update.
 *
 * Revision 1.5  2003/04/01 21:18:40  tony
 * SessionTagging added to the e-announce
 * application.  Cleaned-up actionController.
 *
 * Revision 1.4  2003/03/27 16:24:44  tony
 * added session id as an identification component
 * of the profile, needed because of pinning aspect.
 *
 * Revision 1.3  2003/03/17 23:44:36  tony
 * adjust eStatus.component placement logic.
 *
 * Revision 1.2  2003/03/11 00:33:23  tony
 * accessibility changes
 *
 * Revision 1.1.1.1  2003/03/03 18:03:46  tony
 * This is the initial load of OPICM
 *
 * Revision 1.37  2002/11/21 16:52:04  tony
 * 23317
 *
 * Revision 1.36  2002/11/07 16:58:24  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.action;
import com.elogin.*;
//import com.ibm.eannounce.eserver.ChatAction;
import COM.ibm.eannounce.objects.EntityItem;
import com.ibm.eannounce.eforms.navigate.*;
import com.ibm.eannounce.epanels.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.Profile;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import javax.swing.*;
import java.util.*;
import org.xml.sax.Attributes;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class ActionController extends EParsePanel implements ActionListener, PropertyChangeListener, ETabable {
	private static final long serialVersionUID = 1L;

    /**
     * PARENT
     */
    protected static final int PARENT = 0;
    /**
     * CHILDREN
     */
    private static final int CHILDREN = 1;

    private String navKey = null;
    private int id = -1;
    private Action act = null;
    private Object o = null;

    private String splitType = "Vertical";
    private String mtrxPosition = "Top";
    private String crossPosition = "Bottom";
    //private boolean couple = true;
    private boolean oneTouch = true;
    private double splitPosition = .5D;

    //private String XML = null;
    private NavActionTree tree = null;

//    private ePanel pnlMain = new ePanel(new BorderLayout());
    private ELabel sesLbl = new ELabel();

    private EntityItem[] eiArray = null;
//    private String fileName = new String("matrix.html");
    private Profile parentProfile = null;
    private String sGif = null;
    private ETabable parTab = null; //51975
    private Vector vChildTab = new Vector(); //51975

    private boolean bRefresh = false; //6554

    /**
     * actionController
     * @param _o
     * @param _key
     * @author Anthony C. Liberto
     */
    public ActionController(Object _o, String _key) {
        super(new BorderLayout());
        if (_key != null) {
            navKey = new String(_key);
        }
        tree = new NavActionTree(this, 1);
        tree.setMinimumSize(UIManager.getDimension("eannounce.minimum"));
        init(_o);
    }

    /**
     * updateTabPlacement
     * @author Anthony C. Liberto
     * @param _revalidate 
     */
    public void updateTabPlacement(boolean _revalidate) {
    }

    /**
     * loadFromFile
     * @param _o
     * @return
     * @author Anthony C. Liberto
     */
    public static ActionController loadFromFile(Object _o) {
        return new ActionController(_o, "");
    }

    /**
     * setGif
     * @author Anthony C. Liberto
     * @param _s
     */
    public void setGif(String _s) {
        if (_s != null) {
            sGif = new String(_s);
        } else {
            sGif = null;
        }
    }

    /**
     * getGif
     * @author Anthony C. Liberto
     * @return String
     */
    public String getGif() {
        return sGif;
    }

    /**
     * setSessionTagText
     * @param _s
     * @author Anthony C. Liberto
     */
    private void setSessionTagText(String _s) {
        String msg = null;
        if (_s != null) {
            setCode("sesLbl");
            setParm(_s);
            msg = getMessage();
            sesLbl.setText(msg);
            sesLbl.setToolTipText(msg);
        }
        sesLbl.setVisible(_s != null);
    }

    /**
     * setCart
     * @param _cart
     * @author Anthony C. Liberto
     */
    public void setCart(NavCartDialog _cart) {
        act.setCart(_cart);
    }

    /**
     * getCart
     * @return
     * @author Anthony C. Liberto
     */
    protected NavCartDialog getCart() {
        return act.getCart();
    }

    /**
     * setTreeEnabled
     * @param _b
     * @author Anthony C. Liberto
     */
    protected void setTreeEnabled(boolean _b) {
        tree.setEnabled(_b);
    }

    /**
     * getProfile
     * @author Anthony C. Liberto
     * @return Profile
     */
    public Profile getProfile() {
        return act.getProfile();
    }

    /**
     * refreshMenu
     * @author Anthony C. Liberto
     */
    public void refreshMenu() {
        if (act != null) {
            act.refreshMenu();
        }
    }

    private void init(Object _o) {
        add("North", sesLbl);
        setObject(_o);
        buildAction(_o);
    }

    private void buildAction(Object _o) {
        if (_o instanceof MatrixList) {
            act = new MatrixAction(this, _o);
            setSessionTagText(((MatrixList) _o).getEntityList().getTagDisplay());
        } else if (_o instanceof WhereUsedList) {
            act = new UsedAction(this, _o);
            setSessionTagText(((WhereUsedList) _o).getEntityList().getTagDisplay());
        } else if (_o instanceof LockList) {
            act = new LockAction(this, _o);
            setSessionTagText("N/A");
        } else if (_o instanceof QueryList) {
            act = new QueryAction(this, _o);
            setSessionTagText("N/A");
        }else if (_o instanceof String) {
            if (((String) _o).equals(TYPE_RESTOREACTION)) {
                act = new RestoreAction(this, _o);
                setSessionTagText("N/A");
            }
        }
        add("Center", (JComponent) act);
    }

    /**
     * requestFocus
     * @param _i
     * @author Anthony C. Liberto
     */
    public void requestFocus(int _i) {
        if (act != null) {
            act.requestFocus(_i);
        }
    }

    /**
     * setObject
     * @author Anthony C. Liberto
     * @param _o
     */
    public void setObject(Object _o) {
        o = _o;
    }

    /**
     * setEntityItemArray
     * @param _ei
     * @author Anthony C. Liberto
     */
    public void setEntityItemArray(EntityItem[] _ei) {
        eiArray = _ei;
    }

    /**
     * close
     * @author Anthony C. Liberto
     * @return boolean
     */
    public boolean close() {
        if (act != null) {
            if (act.okToClose(false)) {
                act.dereference();
                act = null;
                return true;
            }
        }
        return false;
    }

    //52614	public void select() {
    /**
     * select
     * @author Anthony C. Liberto
     * @param _bCloseAll 
     */
    public void select(boolean _bCloseAll) { //52614
        if (act != null) {
            if (!_bCloseAll && shouldRefresh()) { //6554
                if (eaccess().alwaysRefresh()) { //6554
                    refresh(); //6554
                } else if (eaccess().neverRefresh()) { //6554
                } else { //6554
                    setCode("msg11012"); //6554
                    if (showConfirm(YES_NO, true) == YES) { //6554
                        refresh(); //6554
                    } //6554
                } //6554
            } //6554
            act.select(); //52189
            eaccess().setEMenuBar(act.getMenubar());
            eaccess().setHidden(act.hasHiddenAttributes()); //acl_20031217
        } else { //acl_20031217
            eaccess().setHidden(false); //52476
        } //acl_20031217
        eaccess().setFilter(isFiltered());
        eaccess().setActiveProfile(getProfile());
        eaccess().setMenuEnabled(SYSTEM_MENU, "resetDate", false); //50495
        //52703		appendLog("<tabSelect tab=\"" + getPanelType() + "\" parentkey=\"" + getParentKey() + "\" opwgid=\"" + getOPWGID() + "\"/>");
        if (EAccess.isArmed(REPLAYABLE_ARM_FILE)) { //52703
            appendLog("<tabSelect tab=\"" + getPanelType() + "\" parentkey=\"" + new String(getParentKey()) + "\" opwgid=\"" + getOPWGID() + "\"/>"); //52703
        } //52703
        requestFocus(); //50471
    }

    /**
     * @see java.awt.Component#requestFocus()
     * @author Anthony C. Liberto
     */
    public void requestFocus() {
        if (act != null) { //50471
            act.requestFocus(); //50471
        } //50471
    }

    /**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
        setBusy(true);
        //removeAll(); done in super()
        //removeNotify();
        eiArray = null;

        if (act != null) {
            act.dereference();
            act = null; //acl_Mem_20020201
        }
        o = null; //acl_Mem_20020201

        splitType = null; //acl_Mem_20020201
        mtrxPosition = null; //acl_Mem_20020201
        crossPosition = null; //acl_Mem_20020201
        splitPosition = 0D; //acl_Mem_20020201
        //setBusy(false); done in super()
        sGif = null;
        dereferenceRelated(); //51975
        if (tree != null) {
            tree.dereference();
            tree = null;
        }
        super.dereference();
    }

    /**
     * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
     * @author Anthony C. Liberto
     */
    public void propertyChange(PropertyChangeEvent _pce) {
        appendLog("PropertyChanged: " + _pce.getPropertyName());
    }

    /**
     * actionPerformed
     * @author Anthony C. Liberto
     * @param _ae
     */
    public void actionPerformed(ActionEvent _ae) {
    }

    /*
     * form stuff
     */
    private void processSplit(Attributes atts) {
        for (int i = 0; i < atts.getLength(); ++i) {
            if (atts.getType(i).equals("CDATA")) {
                processSplit(atts.getLocalName(i), atts.getValue(i));
            }
        }
    }

    private void setSplitType(String _s) {
        splitType = new String(_s);
    }

    /**
     * getSplitType
     * @return
     * @author Anthony C. Liberto
     */
    protected int getSplitType() {
        if (splitType.equalsIgnoreCase("Horizontal")) {
            return 1;
        }
        return 0;
    }

    /**
     * getMatrixPosition
     * @return
     * @author Anthony C. Liberto
     */
    protected String getMatrixPosition() {
        return mtrxPosition;
    }

    /**
     * getCrossPosition
     * @return
     * @author Anthony C. Liberto
     */
    protected String getCrossPosition() {
        return crossPosition;
    }

    private void setSplitPostion(String _s) {
        System.out.println("actionController.setSplitPosition(" + _s + ")");
        try {
            splitPosition = Double.valueOf(_s).doubleValue();
        } catch (NumberFormatException _nfe) {
            _nfe.printStackTrace();
        }
    }

    /**
     * getSplitPostion
     * @return
     * @author Anthony C. Liberto
     */
    protected double getSplitPostion() {
        return splitPosition;
    }

    /**
     * isOneTouch
     * @return
     * @author Anthony C. Liberto
     */
    protected boolean isOneTouch() {
        return oneTouch;
    }

    private void processSplit(String _att, String _val) {
        System.out.println("processSplit(" + _att + ", " + _val + ")");
        if (_att.equalsIgnoreCase("Type")) {
            setSplitType(_val);
        } else if (_att.equalsIgnoreCase("Position")) {
            setSplitPostion(_val);
        } else if (_att.equalsIgnoreCase("Matrix")) {
        	mtrxPosition = _val;
        } else if (_att.equalsIgnoreCase("Cross")) {
        	crossPosition = _val;
        } else if (_att.equalsIgnoreCase("OneTouch")) {
            if (_val.equalsIgnoreCase("true")) {
            	oneTouch = true;
            } else if (_val.equalsIgnoreCase("false")) {
            	oneTouch = false;
            } else {
                appendLog("invalid Floatable value: " + _val);
            }
        } /*else if (_att.equalsIgnoreCase("Couple")) {
            if (_val.equalsIgnoreCase("true")) {
            	couple = true;
            } else if (_val.equalsIgnoreCase("false")) {
            	couple = false;
            } else {
                appendLog("invalid Floatable value: " + _val);
            }
        }*/
    }

    /**
     * processElement
     *
     * @author Anthony C. Liberto
     * @param tagName
     * @param atts
     */
    protected void processElement(String tagName, Attributes atts) {
        if (tagName.equalsIgnoreCase("SPLIT")) {
            processSplit(atts);
        }
    }

    /**
     * processEndElement
     *
     * @author Anthony C. Liberto
     * @param tagName
     */
    protected void processEndElement(String tagName) {
    }

    /**
     * isHidden
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean isHidden() {
        if (act != null) { //53412
            return act.isHidden();
        } //53412
        return false; //53412
    }*/

    /**
     * isFiltered
     * @return
     * @author Anthony C. Liberto
     */
    private boolean isFiltered() {
        if (act != null) { //53412
            return act.isFiltered();
        } //53412
        return false; //53412
    }
    /*
     *Tabable
     */
    /**
     * setParentKey
     * @author Anthony C. Liberto
     * @param _navKey
     */
    public void setParentKey(String _navKey) {
        navKey = _navKey;
    }

    /**
     * getParentKey
     * @author Anthony C. Liberto
     * @return String
     */
    public String getParentKey() {
        return navKey;
    }

    /**
     * setOPWGID
     * @author Anthony C. Liberto
     * @param _id 
     */
    public void setOPWGID(int _id) {
        id = _id;
    }

    /**
     * getOPWGID
     * @author Anthony C. Liberto
     * @return int
     */
    public int getOPWGID() {
        return id;
    }

    /**
     * setShouldRefresh
     * @author Anthony C. Liberto
     * @param _code
     * @param _eType
     * @param _opwg
     */
    public void setShouldRefresh(String _eType, int _opwg, int _code) {
    } //acl_20021022

    /**
     * setShouldRefresh
     * @author Anthony C. Liberto
     * @param _b 
     */
    public void setShouldRefresh(boolean _b) {
        System.out.println("setShouldRefresh(" + _b + ")");
        bRefresh = _b; //6554
    }

    /**
     * shouldRefresh
     * @author Anthony C. Liberto
     * @return boolean
     */
    public boolean shouldRefresh() { //acl_20021022
       // System.out.println("shouldRefresh()");
        return bRefresh; //6554
    } //acl_20021022

    /**
     * getEntityType
     * @author Anthony C. Liberto
     * @return String
     * @param _i 
     */
    public String getEntityType(int _i) {
    	if (act instanceof QueryAction){
    		return ((QueryAction)act).getViewName();
    	}
        return null;
    }

    /**
     * refresh
     * @author Anthony C. Liberto
     * @param _breakable 
     */
    public void refresh(boolean _breakable) {
        System.out.println("I am trying to refresh"); //6554
    }

    /**
     * contains
     * @author Anthony C. Liberto
     * @return boolean
     * @param _eai
     * @param _ei
     */
    public boolean contains(EntityItem[] _ei, EANActionItem _eai) {
        return act.contains(_ei, _eai);
    }

    /**
     * refreshUpdate
     * @author Anthony C. Liberto
     */
    public void refreshUpdate() {
        if (act instanceof MatrixAction) {
            ((MatrixAction) act).refreshTable(0, true);
        }
    }

    /**
     * refreshUpdate
     * @param _r
     * @param _c
     * @author Anthony C. Liberto
     */
    public void refreshUpdate(int _r, int _c) { //23317
        if (act instanceof MatrixAction) { //23317
            ((MatrixAction) act).refreshTable(0, _r, _c, true); //23317
        } //23317
    } //23317

    /**
     * getTree
     * @return
     * @author Anthony C. Liberto
     */
    public NavActionTree getTree() {
        return tree;
    }
    /*
    	action processing.
     */
    /**
     * getTableTitle
     * @author Anthony C. Liberto
     * @return String
     */
    public String getTableTitle() {
        return act.getTableTitle();
    }

    /**
     * performAction
     * @param _ai
     * @param _navType
     * @author Anthony C. Liberto
     */
    public void performAction(EANActionItem _ai, int _navType) {
        act.performAction(_ai, _navType);
    }

    /**
     * actionExists
     * @param _sAction
     * @return
     * @author Anthony C. Liberto
     */
    protected boolean actionExists(String _sAction) {
        if (tree == null) {
            return false;
        }
        return tree.actionExists(_sAction);
    }

    /**
     * getRelatedEntityItems
     * @param _type
     * @param _eiRelatives
     * @return
     * @author Anthony C. Liberto
     */
    protected EntityItem[] getRelatedEntityItems(int _type, EntityItem[] _eiRelatives) {
        int ii = -1;
        int xx = -1;
        Vector v = null;
        int jj = -1;
        EntityItem[] out = null;
        if (eiArray == null) {
            return _eiRelatives;
        }
        ii = _eiRelatives.length;
        xx = eiArray.length;
        if (ii == xx) {
            return eiArray;
        }
        v = new Vector();
        for (int i = 0; i < ii; ++i) {
            String key = _eiRelatives[i].getKey();
            for (int x = 0; x < xx; ++x) {
                if (_type == PARENT) {
                    if (eiArray[x].getDownLink(key) != null) {
                        v.add(eiArray[x]);
                        break;
                    }
                } else if (_type == CHILDREN) {
                    if (eiArray[x].getUpLink(key) != null) {
                        v.add(eiArray[x]);
                        break;
                    }
                }
            }
        }
        if (v.isEmpty()) {
            return null;
        }
        jj = v.size();
        out = new EntityItem[jj];
        for (int j = 0; j < jj; ++j) {
            out[j] = (EntityItem) v.get(j);
        }
        return out;
    }

    /**
     * getSearchableObject
     * @author Anthony C. Liberto
     * @return Object
     */
    public Object getSearchableObject() { //22377
        return act.getSearchableObject(); //22377
    } //22377

    /**
     * getHelpText
     * @author Anthony C. Liberto
     * @return String
     */
    public String getHelpText() {
        if (act != null) {
            return act.getHelpText();
        }
        return getString("nia");
    }

    /**
     * refreshAppearance
     * @author Anthony C. Liberto
     */
    public void refreshAppearance() {
        if (act != null) {
            act.refreshAppearance();
        }
    }

    /**
     * setParentProfile
     * @author Anthony C. Liberto
     * @param _prof
     */
    public void setParentProfile(Profile _prof) {
        parentProfile = _prof;
    }

    /**
     * getParentProfile
     * @author Anthony C. Liberto
     * @return Profile
     */
    public Profile getParentProfile() {
        return parentProfile;
    }
    /*
     50892
    */
    /**
     * setLockListAll
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setLockListAll(boolean _b) {
        if (act instanceof LockAction) {
            ((LockAction) act).setLockListAll(_b);
        }
    }

    /*
     51160
     */
    /**
     * viewLockExist
     * @param _b
     * @return
     * @author Anthony C. Liberto
     */
    public boolean viewLockExist(boolean _b) {
        if (act instanceof LockAction) {
            return ((LockAction) act).viewLockExist(_b);
        }
        return false;
    }

    /**
     * getPanelType
     * @author Anthony C. Liberto
     * @return String
     */
    public String getPanelType() {
        return TYPE_ACTIONCONTROLLER;
    }

    /**
     * getVisiblePanelType
     * @author Anthony C. Liberto
     * @return String
     */
    public String getVisiblePanelType() {
        if (act != null) {
            return act.getPanelType();
        }
        return getPanelType();
    }

    /**
     * process
     * @author Anthony C. Liberto
     * @param _action
     * @param _child
     * @param _method
     * @param _parent
     */
    public void process(String _method, String _action, String[] _parent, String[] _child) {
        if (act != null) {
            act.process(_method, _action, _parent, _child);
        }
    }

    /*
     acl_20030718
     */
    /**
     * selectKeys
     * @author Anthony C. Liberto
     * @param _keys
     */
    public void selectKeys(String[] _keys) {
        if (act != null) {
            act.selectKeys(_keys);
        }
    }

    /**
     * viewRestoreExist
     * @return
     * @author Anthony C. Liberto
     */
    public boolean viewRestoreExist() {
        if (act instanceof RestoreAction) {
            return ((RestoreAction) act).viewRestoreExist();
        }
        return false;
    }

    /*
     cr_TBD_3
     */

    /**
     * getAllActions
     * @param _ean
     * @return
     * @author Anthony C. Liberto
     */
    public EANActionItem[] getAllActions(EANActionItem[] _ean) {
        EANActionItem[] parent = null;
        if (o instanceof WhereUsedList) {
            parent = getParentActionItems((WhereUsedList) o);
        } else if (o instanceof MatrixList) {
            parent = getParentActionItems((MatrixList) o);
        }
        return combineActions(parent, _ean);
    }

    private EANActionItem[] combineActions(EANActionItem[] _parent, EANActionItem[] _child) {
        if (_parent != null && _child != null) {
            int pp = _parent.length;
            int cc = _child.length;
            int aa = pp + cc;
            EANActionItem[] out = new EANActionItem[aa];
            System.arraycopy(_parent, 0, out, 0, pp);
            System.arraycopy(_child, 0, out, pp, cc);
            return out;
        } else if (_parent != null) {
            return _parent;
        } else if (_child != null) {
            return _child;
        }
        return null;
    }

    private EANActionItem[] getParentActionItems(WhereUsedList _wList) {
        EANActionItem[] out = null;
        if (_wList != null) {
            EntityGroup peg = _wList.getParentEntityGroup();
            if (peg != null) {
                ActionGroup pag = peg.getActionGroup();
                if (pag != null) {
                    int ii = pag.getActionItemCount();
                    out = new EANActionItem[ii];
                    for (int i = 0; i < ii; ++i) {
                        out[i] = pag.getActionItem(i);
                    }
                }
            }
        }
        return out;
    }

    /**
     * getParentActionItems
     * @param _mList
     * @return
     * @author Anthony C. Liberto
     */
    private EANActionItem[] getParentActionItems(MatrixList _mList) {
        EANActionItem[] out = null;
        if (_mList != null) {
            EntityGroup peg = _mList.getParentEntityGroup();
            if (peg != null) {
                ActionGroup pag = peg.getActionGroup();
                if (pag != null) {
                    int ii = pag.getActionItemCount();
                    out = new EANActionItem[ii];
                    for (int i = 0; i < ii; ++i) {
                        out[i] = pag.getActionItem(i);
                    }
                }
            }
        }
        return out;
    }

    /*
     51975
     */
    /**
     * setParentTab
     * @author Anthony C. Liberto
     * @param _tab
     */
    public void setParentTab(ETabable _tab) {
        parTab = _tab;
        if (parTab != null) {
            parTab.addChildTab(this);
        }
    }

    /**
     * getParentTab
     * @author Anthony C. Liberto
     * @return ETabable
     */
    public ETabable getParentTab() {
        return parTab;
    }

    /**
     * addChildTab
     * @author Anthony C. Liberto
     * @param _tab
     */
    public void addChildTab(ETabable _tab) {
        vChildTab.add(_tab);
    }

    /**
     * removeChildTab
     * @author Anthony C. Liberto
     * @return eTabable
     * @param _tab
     */
    public ETabable removeChildTab(ETabable _tab) {
        if (vChildTab.remove(_tab)) {
            return _tab;
        }
        return null;
    }

    /**
     * dereference Related
     * @author Anthony C. Liberto
     */
    private void dereferenceRelated() {
    	if (vChildTab!=null){
    		while (!vChildTab.isEmpty()) {
    			ETabable tab = (ETabable) vChildTab.remove(0);
    			tab.setParentTab(getParentTab());
    		}
    		vChildTab = null;
    	}
        if (parTab != null) {
            parTab.removeChildTab(this);
            parTab = null;
        }
    }

    /*
     52189
     */
    /**
     * deselect
     * @author Anthony C. Liberto
     */
    public void deselect() {
        if (act != null) {
            act.deselect();
        }
    }

    /*
     kehrli_20030929
     */
    /**
     * getTabToolTipText
     * @author Anthony C. Liberto
     * @return String
     */
    public String getTabToolTipText() {
        if (act != null) {
            return act.getTabToolTipText();
        }
        return null;
    }

    /**
     * getTabIcon
     * @author Anthony C. Liberto
     * @return Icon
     */
    public Icon getTabIcon() {
        if (act != null) {
            return act.getTabIcon();
        }
        return null;
    }

    /**
     * getTabTitle
     * @author Anthony C. Liberto
     * @return String
     */
    public String getTabTitle() {
        if (act != null) {
            return act.getTabTitle();
        }
        return null;
    }

    /**
     * getTabMenuTitle
     * @author Anthony C. Liberto
     * @return String
     */
    public String getTabMenuTitle() {
        if (act != null) {
            return act.getTabMenuTitle();
        }
        return null;
    }
    /*
     53476
     */
    /**
     * refresh
     * @param _list
     * @author Anthony C. Liberto
     * /
    public void refresh(EntityList _list) {
        if (act instanceof UsedAction) {
            ((UsedAction) act).refresh(_list);
        }
    }*/

    /*
     6554
     */
    private void refresh() {
        System.out.println("refresh the action");
        setShouldRefresh(false);
        if (act != null) {
            act.refreshTable();
        }
    }

    /**
     * refresh toolbar
     * MN_button_disappear
     * @author Anthony C. Liberto
     */
    public void refreshToolbar() {
		if (act != null) {
			act.refreshToolbar();
		}
	}
    //unused or old
    //private static final int RESTORE = 0;
    //private static final int MATRIX = 1;
    //private static final int USED = 2;
	//private static final int LOCK = 3;

    /**
     * getChatAction
     * @author Anthony C. Liberto
     * @return ChatAction
     * /
    public ChatAction getChatAction() {
        if (act != null) {
            return act.getChatAction();
        }
        return null;
    }*/
    /**
     * bCommit
     */
    //protected boolean bCommit = false;

    /**
     * isTreeEnabled
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean isTreeEnabled() {
        return tree.isEnabled();
    }*/
    /**
     * getObject
     * @return
     * @author Anthony C. Liberto
     * /
    public Object getObject() {
        return o;
    }*/
    /**
     * getEntityItemArray
     * @return
     * @author Anthony C. Liberto
     * /
    public EntityItem[] getEntityItemArray() {
        return eiArray;
    }*/

    /**
     * getMenubar
     * @return
     * @author Anthony C. Liberto
     * /
    public EMenubar getMenubar() {
        if (act == null) {
            return null;
        }
        return act.getMenubar();
    }*/

    /**
     * isCoupled
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean isCoupled() {
        return couple;
    }*/
}
