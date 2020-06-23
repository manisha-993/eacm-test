/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: NavPick.java,v $
 * Revision 1.4  2009/05/28 14:18:09  wendy
 * Performance cleanup
 *
 * Revision 1.3  2008/01/30 16:26:55  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.2  2007/08/01 18:46:12  wendy
 * prevent hang
 *
 * Revision 1.1  2007/04/18 19:44:49  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:15  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:38:02  tony
 * This is the initial load of OPICM
 *
 * Revision 1.32  2005/09/08 17:59:05  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.31  2005/02/03 21:26:13  tony
 * JTest Format Third Pass
 *
 * Revision 1.30  2005/02/03 16:38:53  tony
 * JTest Second Pass Wrapup
 *
 * Revision 1.29  2005/02/01 00:27:55  tony
 * JTest Second Pass
 *
 * Revision 1.28  2005/01/31 20:47:46  tony
 * JTest Second Pass
 *
 * Revision 1.27  2005/01/26 23:42:27  tony
 * JTest Formatting
 *
 * Revision 1.26  2005/01/14 19:53:00  tony
 * adjusted button logic to troubleshoot
 * Button disappearing issue.
 *
 * Revision 1.25  2004/11/24 19:05:15  tony
 * added passthrough for eData function.
 *
 * Revision 1.24  2004/11/24 19:01:18  tony
 * gb_20041124 -- added entitydata in testmode to search
 * and picklist functions.
 *
 * Revision 1.23  2004/11/08 19:01:22  tony
 * TIR USRO-R-CRES-66FQY6
 * Improved sort logic to no longer sort multiple times on a
 * navigation reload.
 *
 * Revision 1.22  2004/11/05 22:04:38  tony
 * searchable picklist
 *
 * Revision 1.21  2004/10/22 22:14:06  tony
 * TIR_65Y3M
 *
 * Revision 1.20  2004/08/26 16:26:36  tony
 * accessibility enhancement.  Adjusted menu and toolbar
 * appearance for the accessibility impaired.  Improved
 * accessibility performance by enabling preference toggling.
 *
 * Revision 1.19  2004/06/22 23:27:56  tony
 * CR0618043756 (opposing selected link)
 *
 * Revision 1.18  2004/06/22 18:05:09  tony
 * cr_2115
 *
 * Revision 1.17  2004/06/17 20:55:05  tony
 * cr_2115 prestub
 *
 * Revision 1.16  2004/06/08 20:41:51  tony
 * 5ZPTCX.2
 *
 * Revision 1.15  2004/06/08 15:47:19  tony
 * 5ZRKHB
 * 5ZPTCX.2
 *
 * Revision 1.14  2004/06/07 19:43:33  tony
 * 5ZPTCX
 * 5ZPQXN
 *
 * Revision 1.13  2004/05/26 23:09:43  tony
 * cr_ActChain
 * updated logic for picklist chained actions.
 *
 * Revision 1.12  2004/05/26 15:54:24  tony
 * 5ZBTCQ.2
 *
 * Revision 1.11  2004/05/26 15:54:01  tony
 * 5ZBTCQ
 *
 * Revision 1.10  2004/05/25 22:48:17  tony
 * 5ZBTCQ
 *
 * Revision 1.9  2004/05/24 22:11:10  tony
 * cr_ActChain behavior mods
 *
 * Revision 1.8  2004/05/24 21:48:54  tony
 * cr_ActChain
 *
 * Revision 1.7  2004/05/20 14:47:55  tony
 * cr_ActChain
 *
 * Revision 1.6  2004/04/20 20:34:42  tony
 * updated logic
 *
 * Revision 1.5  2004/03/30 23:26:55  tony
 * CR_0813025214
 *
 * Revision 1.4  2004/03/30 21:34:51  tony
 * CR_0813025214
 *
 * Revision 1.3  2004/03/25 23:37:19  tony
 * cr_216041310
 *
 * Revision 1.2  2004/02/27 18:55:01  tony
 * display statements
 *
 * Revision 1.1.1.1  2004/02/10 16:59:44  tony
 * This is the initial load of OPICM
 *
 * Revision 1.43  2003/10/31 17:30:49  tony
 * 52783
 *
 * Revision 1.42  2003/10/29 00:16:42  tony
 * removed System.out. statements.
 *
 * Revision 1.41  2003/10/22 17:11:22  tony
 * 52558
 *
 * Revision 1.40  2003/10/15 15:56:53  tony
 * added logic to trap null pointer
 *
 * Revision 1.39  2003/10/15 15:30:48  tony
 * 52555
 *
 * Revision 1.38  2003/10/14 23:25:26  tony
 * kc_20031014
 * application was not picking up link actions.
 *
 * Revision 1.37  2003/10/14 20:56:40  tony
 * 52558
 *
 * Revision 1.36  2003/10/13 22:52:02  tony
 * 51753
 *
 * Revision 1.35  2003/09/24 15:44:34  tony
 * 52364
 *
 * Revision 1.34  2003/09/22 22:29:18  tony
 * 52347
 *
 * Revision 1.33  2003/09/19 18:10:24  tony
 * 52321
 *
 * Revision 1.32  2003/09/04 21:37:37  tony
 * 52068
 *
 * Revision 1.31  2003/08/28 22:54:01  tony
 * 51876
 *
 * Revision 1.30  2003/08/21 22:20:06  tony
 * 51866
 * 51876
 *
 * Revision 1.29  2003/08/15 15:38:08  tony
 * cr_0805036452
 *
 * Revision 1.28  2003/07/22 15:44:14  tony
 * 51492
 *
 * Revision 1.27  2003/07/17 17:24:17  tony
 * 51477
 *
 * Revision 1.26  2003/07/09 15:09:00  tony
 * 51417 -- (did not tag) updated menu creation logic
 * to use the properties file instead of constant values.
 *
 * Revision 1.25  2003/07/03 16:38:04  tony
 * improved scripting logic.
 *
 * Revision 1.24  2003/06/10 16:46:48  tony
 * 51260
 *
 * Revision 1.23  2003/06/05 20:15:22  tony
 * 51169
 *
 * Revision 1.22  2003/06/03 20:28:59  tony
 * 51113
 *
 * Revision 1.21  2003/06/02 16:45:30  tony
 * 51024 added outOfRangeException, thus preventing multiple
 * messages.
 *
 * Revision 1.20  2003/05/30 21:09:23  tony
 * updated messaging logic to popup on top
 * of the currently active Component.
 *
 * Revision 1.19  2003/05/29 21:20:45  tony
 * adjusted setParm logic for single parm string.
 *
 * Revision 1.18  2003/05/28 16:27:04  tony
 * 50924
 *
 * Revision 1.17  2003/05/16 19:54:34  tony
 * adjusted getNumber to properly display
 *
 * Revision 1.16  2003/05/08 19:19:27  tony
 * 50445
 *
 * Revision 1.15  2003/05/07 15:55:49  tony
 * 24306
 *
 * Revision 1.14  2003/05/06 22:07:22  tony
 * 24351
 *
 * Revision 1.13  2003/04/21 20:02:35  tony
 * updated logic to correlate to mw changes.
 *
 * Revision 1.12  2003/04/16 17:39:31  tony
 * added setResizable to displayComponent.
 *
 * Revision 1.11  2003/04/11 20:02:30  tony
 * added copyright statements.
 *
 */
package com.ibm.eannounce.eforms.navigate;
import com.elogin.*;
import com.ibm.eannounce.eforms.action.*;
import com.ibm.eannounce.eforms.edit.*; //cr_0813025224
import com.ibm.eannounce.eforms.table.NavTable;
import com.ibm.eannounce.eobjects.*;
import com.ibm.eannounce.exception.*;
import COM.ibm.eannounce.objects.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.TreePath;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class NavPick extends AccessibleDialogPanel implements ActionListener, DisplayableComponent {
	private static final long serialVersionUID = 1L;
	/**
     * MATRIX
     */
    public static final int MATRIX = 0;
    /**
     * NAVIGATE
     */
    public static final int NAVIGATE = 1;
    /**
     * WHERE_USED
     */
    public static final int WHERE_USED = 2;

    private ERolloverButton btnColumn = new ERolloverButton(getImageIcon("addcol.gif"), "addcol.gif");
    private ERolloverButton btnLink = new ERolloverButton(getImageIcon("link.gif"), "link.gif");
    private ERolloverButton btnCopy = new ERolloverButton(getImageIcon("copyLink.gif"), "copyLink.gif");
    private ERolloverButton btnView = null;

    private EPanel pnlNorth = new EPanel(new BorderLayout());
    private EPanel pnlCenter = new EPanel(new BorderLayout());
    private NavDataPick data = null;

    //private MetaLink[] metaLink = null;

    private JToolBar toolbar = null;

    private UsedAction used = null;
    private MatrixAction matrix = null;
    private Navigate nav = null;
    private int type = 0;

    private String[] keys = null;
    private TreePath path = null;
//    private interfaceDialog id = null;

    private EComboBox eChoose = new EComboBox();
    //51753	private boolean bResize = false;
    private boolean bResize = true; //51753
    //52364	private eLabel lblFilter = new eLabel(eaccess().getImageIcon("fltr.gif"));
    private ELabel lblFilter = new ELabel(getString("fltrOn")); //52364

    private JTabbedPane tabPane = new JTabbedPane() {//cr_0813025224
    	private static final long serialVersionUID = 1L;
    	public void setSelectedIndex(int _i) {//cr_0813025224
            super.setSelectedIndex(_i); //cr_0813025224
            setHighlight(_i); //cr_0813025224
            toggleMenu(_i); //5ZBTCQ
        } //cr_0813025224

    }; //cr_0813025224
    private EditActionItem viewAction = null; //cr_0813025224

    /**
     * navPick
     * @param _used
     * @param _type
     * @author Anthony C. Liberto
     */
    public NavPick(UsedAction _used, int _type) {
        super(new BorderLayout());
        used = _used;
        init(_type);
    }

    /**
     * navPick
     * @param _navigate
     * @param _type
     * @author Anthony C. Liberto
     */
    public NavPick(Navigate _navigate, int _type) {
        super(new BorderLayout());
        nav = _navigate;
        init(_type);
    }

    /**
     * navPick
     * @param _matrix
     * @param _type
     * @author Anthony C. Liberto
     */
    public NavPick(MatrixAction _matrix, int _type) {
        super(new BorderLayout());
        matrix = _matrix;
        init(_type);
        setModalCursor(true); //TIR_65Y3M
    }

    /**
     * getLinkActionItem
     * @return
     * @author Anthony C. Liberto
     */
    public LinkActionItem getLinkActionItem() {
        if (type == WHERE_USED && data != null) { //cr_ActChain
            EntityGroup eg = data.getEntityGroup(); //cr_ActChain
            if (eg != null) { //cr_ActChain
                ActionGroup ag = eg.getActionGroup(); //cr_ActChain
                if (ag != null) { //cr_ActChain
                    int ii = ag.getActionItemCount(); //cr_ActChain
                    if (ii > 0) { //cr_ActChain
                        for (int i = 0; i < ii; ++i) { //cr_ActChain
                            EANActionItem item = ag.getActionItem(i); //cr_ActChain
                            if (item instanceof LinkActionItem) { //cr_ActChain
                                return (LinkActionItem) item; //cr_ActChain
                            } //cr_ActChain
                        } //cr_ActChain
                    } //cr_ActChain
                } //cr_ActChain
            } //cr_ActChain
            return null; //cr_ActChain
        } //cr_ActChain
        if (eChoose.getItemCount() == 1) {
            return (LinkActionItem) eChoose.getItemAt(0);
        }
        return (LinkActionItem) eChoose.getSelectedItem();
    }

    /**
     * setKeys
     * @param _keys
     * @author Anthony C. Liberto
     */
    public void setKeys(String[] _keys) {
        keys = _keys;
    }

    /**
     * getKeys
     * @return
     * @author Anthony C. Liberto
     */
    public String[] getKeys() {
        return keys;
    }

    /**
     * setPath
     * @param _path
     * @author Anthony C. Liberto
     */
    public void setPath(TreePath _path) {
        path = _path;
    }

    /**
     * getPath
     * @return
     * @author Anthony C. Liberto
     */
    public TreePath getPath() {
        return path;
    }

    /**
     * getNavigate
     * @return
     * @author Anthony C. Liberto
     */
    public Navigate getNavigate() {
        return nav;
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @author Anthony C. Liberto
     */
    public void actionPerformed(ActionEvent _ae) {
        String action = null;
        if (!isBusy()) { //23336
            setBusy(true);
            action = _ae.getActionCommand();
            appendLog("navPick.action: " + action);
            if (data == null) {
                return;
            }
            Long t11 = EAccess.eaccess().timestamp("NavPick actionPerformed "+action+" dialog started");
            if (action.equals("addC")) {
                if (matrix != null) {
                    EntityItem[] dataItems = null;
                    try {
                        dataItems = data.getSelectedEntityItems(false, true);
                        matrix.addColumn(dataItems);
                    } catch (OutOfRangeException _range) {
                        _range.printStackTrace();
                        //51260						setMessage(_range.toString());
                        //51260						showError();
                        showException(_range, FYI_MESSAGE, OK); //51260
                    }
                    dataItems = null;
                }
            } else if (action.equals("link")) {
                if (used != null) {
                    LinkActionItem lai = getLinkActionItem();

                    if (lai != null && lai.hasChainAction()) { //cr_ActChain
                        EntityItem[] selItems = null; //cr_ActChain//5ZPTCX
                        EntityItem[] parItems = null;
                        Object o = null;
                        String out = null;
                        try { //cr_ActChain
                            selItems = data.getSelectedEntityItems(false, true); //cr_ActChain
                            parItems = used.getParentEntityItemAsArray(); //cr_ActChain

                            System.err.println("NavPick.selCount: " + selItems.length);
                            System.err.println("NavPick.parCount: " + parItems.length);

                            o = used.link(lai, parItems, selItems, this); //5ZPTCX.2
                            if (o != null) { //cr_ActChain
                                if (o instanceof Boolean) { //cr_ActChain
                                    if (!((Boolean) o).booleanValue()) { //cr_ActChain
                                    	EAccess.eaccess().timestamp("NavPick actionPerformed "+action+" dialog ended",t11);
                                        return; //cr_ActChain
                                    } //cr_ActChain
                                } //cr_ActChain
                            } else { //cr_ActChain
                            	EAccess.eaccess().timestamp("NavPick actionPerformed "+action+" dialog ended",t11);
                                return; //cr_ActChain
                            } //cr_ActChain
                            out = reportLink(parItems, selItems); //cr_ActChain
                            if (out != null) { //cr_ActChain
                                eaccess().showLinkDialog((Window) id, getString("msg3011.0"), out); //cr_ActChain
                            } //cr_ActChain																											//cr_ActChain
                            if (lai.hasChainAction()) { //cr_ActChain
                                if (!lai.requireInput()) { //cr_ActChain
                                    if (lai.isChainEditAction()) { //cr_ActChain
                                        eaccess().load(used.getActionController(), o, "edit.gif"); //cr_ActChain
                                    } else if (lai.isChainWhereUsedAction()) { //cr_ActChain
                                        eaccess().load(used.getActionController(), o, "used.gif"); //cr_ActChain
                                    } else if (lai.isChainMatrixAction()) { //cr_ActChain
                                        eaccess().load(used.getActionController(), o, "mtrx.gif"); //cr_ActChain
                                    } //cr_ActChain
                                } //cr_ActChain
                            } //cr_ActChain
                        } catch (OutOfRangeException _range) { //cr_ActChain
                            _range.printStackTrace(); //cr_ActChain
                            showException(_range, FYI_MESSAGE, OK); //cr_ActChain
                        } //cr_ActChain
                        selItems = null; //cr_ActChain
                        parItems = null; //cr_ActChain
                        disposeDialog(); //cr_ActChain
                    } else { //cr_ActChain
                        EntityItem[] dataItems = null;
                        boolean b = false;
                        try {
                            dataItems = data.getSelectedEntityItems(false, true);
                            //parent item array is based on selection, save it for msg
                            EntityItem[] selectedParents = used.getParentEntityItemAsArray();
                            b = used.link(getKeys(), dataItems, eaccess().getLinkType()); //51876
                            if (b) { //52555
                            	//String out = reportLink(used.getParentEntityItemAsArray(), dataItems); 
                                String out = reportLink(selectedParents, dataItems); 
                                if (out != null) { //52555
                                    eaccess().showLinkDialog((Window) id, getString("msg3011.0"), out); //52555
                                } //52555
                            } //52555
                        } catch (OutOfRangeException _range) {
                            _range.printStackTrace();
                            showException(_range, FYI_MESSAGE, OK); //51260
                        }
                        dataItems = null;
                    }
                } else {
                    Object[] linkIn = link(); //cr_ActChain
                    if (linkIn != null) { //TIR USRO-R-CRES-66FQY6
                        if (linkIn.length == 2) { //cr_ActChain
                            if (linkIn[0] != null && linkIn[0] instanceof String) { //cr_ActChain
                                eaccess().showLinkDialog((Window) id, getString("msg3011.0"), (String) linkIn[0]); //cr_ActChain
                            } //cr_ActChain
                            if (linkIn[1] != null && !(linkIn[1] instanceof Boolean)) { //cr_ActChain5ZPQXN
                                launchLink(getLinkActionItem(), linkIn[1]); //cr_ActChain
                                disposeDialog(); //cr_ActChain
                            } //cr_ActChain
                        } //cr_ActChain
                    } //TIR USRO-R-CRES-66FQY6
                }
            } else if (action.equals("view")) { //cr_0813025224
                processViewAction(); //cr_0813025224
            } else if (action.equals("rfrsh")) { //cr_2115
            	refresh(); //cr_2115
            } else if (action.equals("copylink")) {
                Object[] linkIn = copyLink(); //cr_ActChain
                if (linkIn.length == 2) { //cr_ActChain
                    if (linkIn[0] != null && linkIn[0] instanceof String) { //cr_ActChain
                        eaccess().showLinkDialog((Window) id, getString("msg3011.0"), (String) linkIn[0]); //cr_ActChain
                    } //cr_ActChain
                    if (linkIn[1] != null && !(linkIn[1] instanceof Boolean)) { //cr_ActChain5ZPQXN
                        launchLink(getLinkActionItem(), linkIn[1]); //cr_ActChain
                        disposeDialog(); //cr_ActChain
                    } //cr_ActChain
                } //cr_ActChain
            } else if (action.equals("choose")) {
                setButtonsVisible(getLinkActionItem());
            } else if (action.equals("exit")) {
                hideDialog();
            } else if (action.equals("selA")) {
                data.actionPerformed("selA");
            } else if (action.equals("iSel")) {
                data.actionPerformed("iSel");
            } else if (action.equals("f/r")) {
                data.actionPerformed("f/r");
            } else if (action.equals("srt")) {
                data.actionPerformed("srt");
            } else if (action.equals("fltr")) {
                data.actionPerformed("fltr");
            } else if (action.equals("eData")) { //gb_20041124
                data.actionPerformed("eData"); //gb_20041124
            } //gb_20041124
            setBusy(false);
            
            EAccess.eaccess().timestamp("NavPick actionPerformed "+action+" dialog ended",t11);
        } //23336
    }

    /**
     * setType
     * @param _i
     * @author Anthony C. Liberto
     */
    public void setType(int _i) {
        type = _i;
    }

    /**
     * setEntityList
     * @param _eList
     * @author Anthony C. Liberto
     */
    public void setEntityList(EntityList _eList) {
        NavTable table = null;
        boolean bTableEmpty = false;
        data.load(_eList, false);
        table = data.getTable(0);
        bTableEmpty = (table != null) ? table.isEmpty() : true; //52783
        menubar.setEnabled("rfrsh", false); //cr_2115
        if (type == MATRIX) { //acl_20021024
            //			btnColumn.setEnabled(true);										//acl_20021024
            //52783			menubar.setEnabled("addC",true);								//51113
            menubar.setEnabled("addC", !bTableEmpty); //52783
            btnColumn.setEnabled(!bTableEmpty); //52783
            menubar.setEnabled("link", false); //51866
            eChoose.setVisible(false); //52555
        } else if (type == WHERE_USED) { //51866
            menubar.setEnabled("addC", false); //51866
            //52783			menubar.setEnabled("link",true);								//51866
            menubar.setEnabled("link", !bTableEmpty); //52783
            btnLink.setEnabled(!bTableEmpty); //52783
            eChoose.setVisible(false); //52555
        } else if (type == NAVIGATE) { //acl_20021024
            boolean bStatus = isStatusView(_eList); //cr_2115
            menubar.setEnabled("addC", false); //51113
            menubar.setEnabled("link", false); //51866
            menubar.setEnabled("view", isDetailViewable()); //cr_0813025224
            menubar.setEnabled("rfrsh", bStatus); //cr_2115
            if (bStatus) { //cr_2115
                eChoose.setVisible(false); //cr_2115
                eChoose.setEnabled(false); //cr_2115
                setButtonsVisible(null); //cr_2115
            } else { //cr_2115
                EntityGroup eg = data.getEntityGroup(); //acl_20021024
                if (eg != null) { //acl_20021024
                    RowSelectableTable rst = eg.getActionGroupTable(); //kc_20031014
                    EANActionItem[] ean = eaccess().getExecutableActionItems(eg, rst); //kc_20031014
                    loadActionItems(ean); //cr_0813025224
                } //acl_20021024
            } //cr_2115
        } //acl_20021024
        setPickFilter(false); //51477
    }

    private void setButtonsVisible(LinkActionItem _lai) { //acl_20021024
        boolean bTableEmpty = data.isEmpty(); //52783
        if (_lai != null) { //acl_20021024
            btnLink.setVisible(_lai.canLink()); //52783
            btnCopy.setVisible(_lai.canLinkCopy()); //52783
            btnLink.setEnabled(!bTableEmpty); //52783
            btnCopy.setEnabled(!bTableEmpty); //52783
            menubar.setEnabled("copylink", _lai.canLinkCopy() && !bTableEmpty); //52783
            menubar.setEnabled("link", _lai.canLink() && !bTableEmpty); //52783
        } else { //acl_20021024
            btnCopy.setVisible(false); //acl_20021024
            btnLink.setVisible(false); //acl_20021024
            btnCopy.setEnabled(false); //52783
            btnLink.setEnabled(false); //52783
            menubar.setEnabled("copylink", false); //24351
            menubar.setEnabled("link", false); //24351
        } //acl_20021024
        if (btnView != null) { //cr_0813025224
            btnView.setEnabled(isDetailViewable()); //cr_0813025224
        } //cr_0813025224
        menubar.setEnabled("view", isDetailViewable()); //cr_0813025224
    } //acl_20021024

    /**
     * getEntityList
     * @return
     * @author Anthony C. Liberto
     * /
    public EntityList getEntityList() {
        return data.getEntityList();
    }

    /**
     * @see java.awt.Component#setBackground(java.awt.Color)
     * @author Anthony C. Liberto
     */
    public void setBackground(Color _c) {
        super.setBackground(_c);
    }

    /**
     * @see java.awt.Component#setForeground(java.awt.Color)
     * @author Anthony C. Liberto
     */
    public void setForeground(Color _c) {
        super.setForeground(_c);
    }

    /**
     * @see java.awt.Component#setFont(java.awt.Font)
     * @author Anthony C. Liberto
     */
    public void setFont(Font _f) {
        super.setFont(_f);
    }

    private void init(int _type) {
        Dimension d = null;
        //Font f = null;
        createMenu(); //24351
        setType(_type);
        //f = getFont();
        if (!EAccess.isArmed(ACCESSIBLE_ARM_FILE)) {
            toolbar = new JToolBar();
            toolbar.setFloatable(false);
        }
        if (type == MATRIX) {
            data = new NavDataPick(matrix) {
            	private static final long serialVersionUID = 1L;
            	public void updateFilterIcon(boolean _b) {
                    setPickFilter(_b);
                }
            };
            data.getAccessibleContext().setAccessibleDescription(getString("accessible.mtrxPick"));
            if (toolbar != null) {
                toolbar.add(btnColumn);
                btnColumn.setActionCommand("addC");
                toolbar.add(btnColumn);
                btnColumn.addActionListener(this);
                btnColumn.setToolTipText(getString("addC")); //52347
            }
        } else if (type == WHERE_USED) {
            data = new NavDataPick(used) {
            	private static final long serialVersionUID = 1L;
            	public void updateFilterIcon(boolean _b) {
                    setPickFilter(_b);
                }
            };
            data.getAccessibleContext().setAccessibleDescription(getString("accessible.usedPick"));
            if (toolbar != null) {
                toolbar.add(btnLink);
                btnLink.addActionListener(this);
                btnLink.setActionCommand("link");
            }
        } else if (type == NAVIGATE) {
            data = new NavDataPick(nav) {
            	private static final long serialVersionUID = 1L;
            	public void updateFilterIcon(boolean _b) {
                    setPickFilter(_b);
                }
            };
            data.getAccessibleContext().setAccessibleDescription(getString("accessible.navPick"));
            if (toolbar != null) {
                toolbar.add(btnLink);
                btnLink.addActionListener(this);
                toolbar.add(btnCopy);
                btnCopy.addActionListener(this);
                btnLink.setActionCommand("link");
                btnCopy.setActionCommand("copylink");

                btnView = new ERolloverButton(getImageIcon("view.gif"), "view.gif"); //cr_0813025224
                toolbar.add(btnView); //cr_0813025224
                btnView.setToolTipText(getString("view")); //cr_0813025224
                btnView.addActionListener(this); //cr_0813025224
                btnView.setActionCommand("view"); //cr_0813025224
            }
        }
        data.setInterfaceDialog(id); //24306
        lblFilter.setEnabled(false); //24306
        eChoose.addActionListener(this);
        eChoose.setActionCommand("choose");
        btnLink.setToolTipText(getString("link")); //19913
        btnCopy.setToolTipText(getString("copylink")); //22776
        //22776		btnCopy.setToolTipText(getString("copy"));					//19913

        if (toolbar != null) {
            add("North", toolbar); //20011112
        }
        //52364		pnlNorth.add("North",lblFilter);
        pnlNorth.add("East", lblFilter); //52364
        pnlNorth.add("South", eChoose);
        pnlCenter.add("North", pnlNorth);
        pnlCenter.add("Center", (Component) data); //20011112

        //cr_0813025224		add("Center",pnlCenter);
        tabPane.addTab(getTitle(), pnlCenter); //cr_0813025224
        add("Center", tabPane); //cr_0813025224
        d = new Dimension(400, 400);
        setPreferredSize(d);
        setSize(d);
    }

    //cr_ActChain	private String link() {
    private Object[] link() { //cr_ActChain
        EntityItem[] navItems = null;
        EntityItem[] dataItems = null;
        try {
            navItems = nav.getSelectedEntityItems(false, true);
            dataItems = data.getSelectedEntityItems(false, true);
            return link(navItems, dataItems, 1, LinkActionItem.TYPE_DEFAULT); //cc_20030303
        } catch (OutOfRangeException _range) {
            _range.printStackTrace();
            //51260			setMessage(_range.toString());
            //51260			showError();
            showException(_range, FYI_MESSAGE, OK); //51260
        }
        navItems = null;
        dataItems = null;
        return null;
    }

    //cr_ActChain	private String copyLink() {
    private Object[] copyLink() { //cr_ActChain
        int copies = pasteCopies();
        EntityItem[] navItems = null;
        EntityItem[] dataItems = null;
        try {
            navItems = nav.getSelectedEntityItems(false, true);
            dataItems = data.getSelectedEntityItems(false, true);
            return link(navItems, dataItems, copies, LinkActionItem.TYPE_COPY); //cc_20030303
        } catch (OutOfRangeException _range) {
            _range.printStackTrace();
            //51260			setMessage(_range.toString());
            //51260			showError();
            showException(_range, FYI_MESSAGE, OK); //51260
        }
        navItems = null;
        dataItems = null;
        return null;
    }

    private int pasteCopies() {
        return eaccess().getNumber((Window) getParentDialog(), "msg3013");
    }

    private void setOptions(LinkActionItem _lai, String _s) { //acl_20021024
        if (_lai != null) { //acl_20021024
            if (_s.equals("NODUPES")) { //acl_20021024
                _lai.setOption(LinkActionItem.OPT_NODUPES); //acl_20021024
            } else if (_s.equals("REPLACEALL")) { //acl_20021024
                _lai.setOption(LinkActionItem.OPT_REPLACEALL); //acl_20021024
            } else { //acl_20021024
                _lai.setOption(LinkActionItem.OPT_DEFAULT); //acl_20021024
            } //acl_20021024
        } //acl_20021024
    } //acl_20021024

    //cc_20030303	private String link(EntityItem[] _eiParent, EntityItem[] _eiChild, int _linkCount) {
    //cr_ActChain	private String link(EntityItem[] _eiParent, EntityItem[] _eiChild, int _linkCount ,int _linkType) {	//cc_20030303
    private Object[] link(EntityItem[] _eiParent, EntityItem[] _eiChild, int _linkCount, int _linkType) { //cr_ActChain
        LinkActionItem lai = null;
        MetaLink mLink = null;
        Object o = null; //CR0618043756
        Object[] out = new Object[2]; //cr_ActChain
        if (_linkCount <= 0) { //22828
            return null;
        } //22828
        if (_eiParent == null) { //22076
            setCode("msg11021.0"); //22076
            setParm(getString("parent")); //22076
            showError();
            return null; //22076
        } else if (_eiChild == null) { //22076
            setCode("msg11021.0"); //22076
            setParm(getString("child")); //22076
            showError();
            return null; //22076
        }

        lai = getLinkActionItem();
        if (lai == null) {
            return null;
        }

        mLink = lai.getMetaLink(); //51492
        if (mLink == null) { //51492
            return null; //51492
        } //51492
        //CR0618043756		String sOut = reportLink(_eiParent,_eiChild,mLink);						//51492
        //51492		String out = reportLink(_eiParent, _eiChild,lai.getMetaLink());

        if (lai != null) { //acl_20021024
            String sOut = null; //CR0618043756
            if (lai.isOppSelect()) { //CR0618043756
                sOut = reportLink(_eiChild, _eiParent, mLink); //CR0618043756
            } else { //CR0618043756
                sOut = reportLink(_eiParent, _eiChild, mLink); //CR0618043756
            } //CR0618043756
            lai.setLinkType(_linkType); //cc_20030303
            lai.setCopyCount(_linkCount); //22874
            setOptions(lai, nav.getCart().getLinkType()); //acl_20021024
            /*cr_ActChain
            			if (dBase().rexec(lai,_eiParent,_eiChild,this)) {										//51004
            				return out;													//51004
            			}																//51004
            */
            //CR0618043756			Object o = dBase().rexec(lai,_eiParent,_eiChild,this);					//cr_ActChain
            if (lai.isOppSelect()) { //CR0618043756
                o = dBase().rexec(lai, _eiChild, _eiParent, this); //CR0618043756
            } else { //CR0618043756
                o = dBase().rexec(lai, _eiParent, _eiChild, this); //CR0618043756
            } //CR0618043756
            if (o != null) { //cr_ActChain
                if (o instanceof Boolean) { //cr_ActChain
                    if (!((Boolean) o).booleanValue()) { //cr_ActChain
                        return null; //cr_ActChain
                    } //cr_ActChain
                } //cr_ActChain
            } else { //cr_ActChain
                return null; //cr_ActChain
            } //cr_ActChain
            //cr_ActChain
            out[0] = sOut; //cr_ActChain
            out[1] = o; //cr_ActChain
            //cr_ActChain
        } //51004
        return out; //cr_ActChain
        //cr_ActChain		return null;														//51004
    }

    private String reportLink(EntityItem[] _eiParent, EntityItem[] _eiChild, MetaLink _rel) {
        StringBuffer sb = null;
        String msg = null;
        int pp = -1;
        int cc = -1;

        if (_eiParent == null || _eiChild == null || _rel == null) { //52558
            return null; //52558
        } //52558
        sb = new StringBuffer();
        msg = " " + getString("l2") + " ";
        pp = _eiParent.length;
        cc = _eiChild.length;
        for (int p = 0; p < pp; ++p) {
            for (int c = 0; c < cc; ++c) {
                sb.append(getParentString(_eiParent[p], _rel) + msg + _eiChild[c].toString() + RETURN);
                appendLog("       to "+_eiChild[c].getKey());
            }
        }
        return sb.toString();
    }

    private String getParentString(EntityItem _ei, MetaLink _rel) {
        String e1Type = null;
        EANEntity ent = null;
        if (_ei == null) {
        	appendLog("Linked "+"parent");
            return "parent";
        }
        if (_rel == null) {
        	appendLog("Linked "+_ei.getKey());
            return _ei.toString();
        }
        e1Type = _rel.getEntity1Type();
        if (e1Type != null) { //52558
            String eType_ei = _ei.getEntityType(); //52558
            if (eType_ei != null && eType_ei.equals(e1Type)) { //52558
            	appendLog("Linked "+_ei.getKey());
                return _ei.toString(); //52558
            } //52558

            //52558		if (e1Type.equals(_ei.getEntityType()))
            //52558			return _ei.toString();
            ent = _ei.getDownLink(0);
            if (ent != null) {
                String eType_ent = ent.getEntityType(); //52558
                if (eType_ent != null && eType_ent.equals(e1Type)) { //52558
                	appendLog("Linked "+ent.getKey());
                    return ent.toString(); //52558
                } //52558
            } //52558
        } //52558
        //52558		if (ent != null && e1Type.equals(ent.getEntityType()))
        //52558			return ent.toString();
        appendLog("Linked "+_ei.getKey());
        return _ei.toString();
    }

    /*private String getLinkType() {
        return eaccess().getLinkType(); //51876
    }*/

    /**
     * dereference
     *
     * @author Anthony C. Liberto
     */
    public void dereference() {
        removeMenu(); //24351
        if (id != null) { //52321
            if (id.isShowing()) { //52321
                id.dispose(); //52321
            } //52321
            id = null;
        } //52321
        if (toolbar != null) {
            toolbar.removeAll();
            toolbar.removeNotify();
            toolbar = null;
        }
        if (btnColumn != null) {
            btnColumn.removeActionListener(this);
            btnColumn.removeAll();
            btnColumn.removeNotify();
            btnColumn = null;
        }

        if (btnLink != null) {
            btnLink.removeActionListener(this);
            btnLink.removeAll();
            btnLink.removeNotify();
            btnLink = null;
        }

        if (btnCopy != null) {
            btnCopy.removeActionListener(this);
            btnCopy.removeAll();
            btnCopy.removeNotify();
            btnCopy = null;
        }

        if (btnView != null) { //cr_0813025224
            btnView.removeActionListener(this); //cr_0813025224
            btnView.removeAll(); //cr_0813025224
            btnView.removeNotify(); //cr_0813025224
            btnView = null; //cr_0813025224
        } //cr_0813025224

        if (eChoose != null) {
            eChoose.removeActionListener(this);
            eChoose.removeAllItems();
            eChoose.removeAll();
            eChoose.removeNotify();
            eChoose = null;
        }

        dereferenceViewer(); //cr_0813025224

        pnlNorth.removeAll();
        pnlNorth.removeNotify();
        pnlNorth = null;

        pnlCenter.removeAll();
        pnlCenter.removeNotify();
        pnlCenter = null;

        if (data != null) {
            data.dereference();
            data = null;
        }

        //metaLink = null;
        removeAll();
        removeNotify();
    }

    /**
     * setParentDialog
     * @author Anthony C. Liberto
     * @param _id
     */
    public void setParentDialog(InterfaceDialog _id) {
        id = _id;
        if (data != null) { //24306
            data.setInterfaceDialog(_id); //24306
        } //24306
    }

    /**
     * getParentDialog
     *
     * @return
     * @author Anthony C. Liberto
     */
    public InterfaceDialog getParentDialog() {
        return id;
    }

    /**
     * disposeDialog
     *
     * @author Anthony C. Liberto
     */
    public void disposeDialog() {
        eaccess().dispose(getParentDialog());
        setBusy(false); //TIR_65Y3M
        dereferenceViewer();
    }

    /**
     * showMe
     *
     * @author Anthony C. Liberto
     */
    public void showMe() {
    }

    /**
     * hideMe
     *
     * @author Anthony C. Liberto
     */
    public void hideMe() {
    }

    /**
     * setTitle
     * @author Anthony C. Liberto
     * @param _title
     */
    public void setTitle(String _title) {
    }

    /**
     * getTitle
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getTitle() {
        return getString("pick.panel");
    }

    /**
     * activateMe
     *
     * @author Anthony C. Liberto
     */
    public void activateMe() {
        if (data != null) {
            data.requestFocus();
        }
    }

    /**
     * setResizable
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setResizable(boolean _b) {
        bResize = _b;
    }

    /**
     * isResizable
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isResizable() {
        return bResize;
    }

    /**
     * refreshAppearance
     *
     * @author Anthony C. Liberto
     */
    public void refreshAppearance() {
        data.refreshAppearance();
    }

    /**
     * getIconName
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getIconName() {
        return DEFAULT_ICON;
    }

    /**
     * getPanelType
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getPanelType() {
        return TYPE_NAVPICK;
    }

    /*
     24351
    */
    /**
     * createMenu
     *
     * @author Anthony C. Liberto
     */
    protected void createMenu() {
        String strKey = getString("edit");
        super.createMenu();
        menubar.addMenu(strKey, "view", this, KeyEvent.VK_V, Event.CTRL_MASK, true); //cr_0813025224
        menubar.addSeparator(strKey); //cr_0813025224
        menubar.addMenu(strKey, "selA", this, KeyEvent.VK_A, Event.CTRL_MASK, true);
        menubar.addMenu(strKey, "iSel", this, KeyEvent.VK_I, Event.CTRL_MASK, true);
        menubar.addSeparator(strKey);
        menubar.addMenu(strKey, "f/r", this, KeyEvent.VK_F, Event.CTRL_MASK, true);
        menubar.setMenuMnemonic(strKey, getChar("edit-s"));

        strKey = getString("act");
        menubar.addMenu(strKey, "link", this, KeyEvent.VK_L, Event.CTRL_MASK, false);
        menubar.addMenu(strKey, "copylink", this, KeyEvent.VK_C, Event.CTRL_MASK, false);
        menubar.addMenu(strKey, "addC", this, KeyEvent.VK_N, Event.CTRL_MASK, false);
        menubar.addMenu(strKey, "rfrsh", this, KeyEvent.VK_F5, 0, false); //cr_2115
        menubar.setMenuMnemonic(strKey, getChar("act-s"));

        strKey = getString("tbl");
        menubar.addMenu(strKey, "srt", this, 0, 0, true);
        //cr_0805036452		menubar.addMenu(strKey,"fltr",this, KeyEvent.VK_F8, Event.CTRL_MASK, true);
        menubar.addMenu(strKey, "fltr", this, KeyEvent.VK_F8, 0, true); //cr_0805036452
        if (EAccess.isTestMode()) { //gb_20041124
            menubar.addMenu(strKey, "eData", this, KeyEvent.VK_F12, Event.CTRL_MASK, true); //gb_20041124
        } //gb_20041124
        menubar.setMenuMnemonic(strKey, getChar("tbl-s"));
    }

    /**
     * removeMenu
     *
     * @author Anthony C. Liberto
     */
    protected void removeMenu() {
        menubar.removeMenuItem("selA", this);
        menubar.removeMenuItem("iSel", this);
        menubar.removeMenuItem("f/r", this);
        menubar.removeMenuItem("view", this); //cr_0813025224
        menubar.removeMenuItem("link", this);
        menubar.removeMenuItem("copylink", this);
        menubar.removeMenuItem("addC", this);
        menubar.removeMenuItem("rfrsh", this); //cr_2115
        menubar.removeMenuItem("srt", this);
        menubar.removeMenuItem("fltr", this);
        menubar.removeMenuItem("eData", this); //gb_20041124
        super.removeMenu();
    }

    /*
     24306
    */
    private void setPickFilter(boolean _b) {
        lblFilter.setEnabled(_b);
    }

    /*
     50924
     */
    /**
     * getSearchableObject
     *
     * @return
     * @author Anthony C. Liberto
     */
    public Object getSearchableObject() {
        if (tabPane != null) { //5ZBTCQ
            Component c = tabPane.getComponentAt(tabPane.getSelectedIndex()); //5ZBTCQ
            if (c instanceof EditController) { //5ZRKHB
                return ((EditController) c).getSearchableObject(); //5ZBTCQ
            } //5ZBTCQ
        } //5ZBTCQ
        return data.getTable(); //5ZRKHB
    }

    /*
     51169
     */
    /**
     * toFront
     *
     * @author Anthony C. Liberto
     */
    public void toFront() {
        if (id != null) {
            id.toFront();
        }
    }
    /*
     kc_20031014
     */

    /*
     52555
     */
    private String reportLink(EntityItem[] _eiParent, EntityItem[] _eiChild) {
        StringBuffer sb = new StringBuffer();
        String msg = null;
        int pp = -1;
        int cc = -1;

        if (_eiParent == null || _eiChild == null) {
            return null;
        }
        msg = " " + getString("l2") + " ";
        pp = _eiParent.length;
        cc = _eiChild.length;
        for (int p = 0; p < pp; ++p) {
            for (int c = 0; c < cc; ++c) {
                sb.append(getParentString(_eiParent[p], null) + msg + _eiChild[c].toString() + RETURN);
                appendLog("       to "+_eiChild[c].getKey());
            }
        }
        return sb.toString();
    }

    /*
     cr_0813025224
     */
    private void loadActionItems(EANActionItem[] _ean) {
        eChoose.removeAllItems();
        viewAction = null;
        if (_ean != null) {
            int ii = _ean.length;
            for (int i = 0; i < ii; ++i) {
                if (_ean[i] != null) {
                    if (_ean[i] instanceof LinkActionItem) {
                        eChoose.addItem((LinkActionItem) _ean[i]);
                    } else if (_ean[i] instanceof EditActionItem) {
                        viewAction = (EditActionItem) _ean[i];
                    }
                }
            }
            if (eChoose.getItemCount() > 1) {
                eChoose.setSelectedIndex(0);
                eChoose.setVisible(true);
                eChoose.setEnabled(true);
            } else {
                eChoose.setVisible(false);
                eChoose.setEnabled(false);
            }
            setButtonsVisible(getLinkActionItem());
        } else {
            eChoose.setVisible(false);
            eChoose.setEnabled(false);
            setButtonsVisible(null);
        }
    }

    /**
     * isDetailViewable
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isDetailViewable() {
        return viewAction != null;
    }

    private Component getDisplayComponent() {
        return this;
    }

    private void processViewAction() {
        if (viewAction != null) {
            final ESwingWorker myWorker = new ESwingWorker() {
                public Object construct() {
                    try {
                        EntityItem[] ei = data.getSelectedEntityItems(false, true);
                        if (ei != null) {
                            return dBase().getEntityList(viewAction, ei, getDisplayComponent());
                        }
                    } catch (OutOfRangeException _range) {
                        _range.printStackTrace();
                        showException(_range, FYI_MESSAGE, OK);
                    }
					catch(Exception ex){ // prevent hang
						appendLog("Exception in NavPick.processViewAction.ESwingWorker.construct() "+ex);
						ex.printStackTrace();
						setBusy(false);
					}

                    return null;
                }

                public void finished() {
                    Object o = getValue();
                    if (o != null && o instanceof EntityList) {
                        processEdit((EntityList) o);

                    }
                    setWorker(null);
                    setBusy(false);
                }
            };
            setWorker(myWorker);
        }
    }

    private void processEdit(EntityList _eList) {
        EditController ec = null;
        dereferenceViewer();
        ec = new EditController(_eList, nav);
        tabPane.addTab(getString("detView"), ec);
        tabPane.setSelectedIndex(1);
    }

    private void dereferenceViewer() {
        while (tabPane.getTabCount() > 1) {
            Component comp = tabPane.getComponentAt(1);
            if (comp != null && comp instanceof EditController) {
                ((EditController) comp).dereference();
            }
            tabPane.removeTabAt(1);
        }
    }

    /**
     * getSelectedViewItems
     * @return
     * @author Anthony C. Liberto
     */
    public String[] getSelectedViewItems() {
        EditController ec = getEditController();
        if (ec != null) {
            return ec.getSelectedKeys();
        }
        return null;
    }

    private EditController getEditController() {
        if (tabPane.getTabCount() > 1) {
            Component c = tabPane.getComponentAt(1);
            if (c instanceof EditController) {
                return (EditController) c;
            }
        }
        return null;
    }

    private void setHighlight(int _i) {
        if (_i == 0) {
            highlight();
        }
    }

    private void highlight() {
        String[] strKeys = getSelectedViewItems();
        if (strKeys != null) {
            NavTable nt = data.getTable();
            if (nt != null) {
                nt.highlight(strKeys);
            }
        }
    }

    /**
     * hideDialog
     *
     * @author Anthony C. Liberto
     */
    public void hideDialog() {
        super.hideDialog();
        dereferenceViewer();
        return;
    }
    /*
     5ZBTCQ
     */
    private void toggleMenu(int _i) {
        if (menubar != null) {
            boolean b = (_i == 0);
            menubar.setMenuEnabled(getString("edit"), b);
            menubar.setMenuEnabled(getString("act"), b);
            menubar.setMenuEnabled(getString("tbl"), b);
            System.out.println("NavPick.toggleMenu enabled is: " + b);
        }
    }
    /*
     cr_ActChain
     */
    private void launchLink(LinkActionItem _lai, Object _o) {
        if (_lai != null && _lai.hasChainAction()) {
            if (!_lai.requireInput()) {
                if (_lai.isChainEditAction()) {
                    eaccess().load(nav.getENavForm(), _o, "edit.gif");
                } else if (_lai.isChainWhereUsedAction()) {
                    eaccess().load(nav.getENavForm(), _o, "used.gif");
                } else if (_lai.isChainMatrixAction()) {
                    eaccess().load(nav.getENavForm(), _o, "mtrx.gif");
                } else {
                    System.out.println("NavPick.launchLink 01");
                }
            } else {
                System.out.println("NavPick.launchLink 02");
            }
        } else {
            System.out.println("NavPick.launchLink 03");
        }
    }

    private void refresh() {
    	EntityList _list = data.getEntityList();
        if (_list != null) {
            EntityList refreshList = dBase().getEntityList(_list.getParentActionItem(), getParentEntityItems(_list), this);
            setEntityList(refreshList);
        }
    }

    private EntityItem[] getParentEntityItems(EntityList _list) {
        if (_list != null) {
            EntityGroup parent = _list.getParentEntityGroup();
            if (parent != null) {
                return parent.getEntityItemsAsArray();
            }
        }
        return null;
    }

    private boolean isStatusView(EntityList _list) {
        if (_list != null) {
            return _list.isABRStatus();
        }
        return false;
    }

    /*
     searchable picklist
     */
    /**
     * showPicklist
     * @param _list
     * @author Anthony C. Liberto
     */
    public void showPicklist(EntityList _list) {
        setEntityList(_list);
        showDialog(this, this);
        toFront();
    }
}
