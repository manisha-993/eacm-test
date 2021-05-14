/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/12/05
 * @author Anthony C. Liberto
 *
 * $Log: SearchPanel.java,v $
 * Revision 1.2  2008/01/30 16:26:53  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:43:25  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:11  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:44  tony
 * This is the initial load of OPICM
 *
 * Revision 1.11  2005/09/08 17:59:00  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.10  2005/02/11 17:49:35  tony
 * USRO-R-JSTT-697QCV
 *
 * Revision 1.9  2005/02/04 15:22:08  tony
 * JTest Format Third Pass
 *
 * Revision 1.8  2005/02/02 17:30:20  tony
 * JTest Second Pass
 *
 * Revision 1.7  2005/01/27 23:18:20  tony
 * JTest Formatting
 *
 * Revision 1.6  2005/01/18 19:03:12  tony
 * USRO-R-JSTT-68QPBP
 *
 * Revision 1.5  2004/11/24 19:01:18  tony
 * gb_20041124 -- added entitydata in testmode to search
 * and picklist functions.
 *
 * Revision 1.4  2004/11/15 23:00:03  tony
 * 66N7MJ
 *
 * Revision 1.3  2004/11/05 22:03:34  tony
 * searchable picklist
 *
 * Revision 1.2  2004/02/26 21:53:16  tony
 * remoteDatabaseInterface centralization.
 *
 * Revision 1.1.1.1  2004/02/10 16:59:29  tony
 * This is the initial load of OPICM
 *
 * Revision 1.28  2003/12/18 16:08:34  tony
 * added activate me method.
 *
 * Revision 1.27  2003/12/01 20:10:08  tony
 * accessibility.
 *
 * Revision 1.26  2003/12/01 19:46:27  tony
 * accessibility
 *
 * Revision 1.25  2003/11/06 22:36:07  tony
 * added print statement
 *
 * Revision 1.24  2003/10/29 00:21:16  tony
 * removed System.out. statements.
 *
 * Revision 1.23  2003/10/13 21:22:31  tony
 * 52522
 *
 * Revision 1.22  2003/09/12 16:11:53  tony
 * 52188
 *
 * Revision 1.21  2003/09/10 15:48:21  tony
 * 52128
 *
 * Revision 1.20  2003/09/02 14:39:21  tony
 * 52005
 *
 * Revision 1.19  2003/08/27 19:08:48  tony
 * updated generic search to improve functionality.
 *
 * Revision 1.18  2003/08/26 21:17:18  tony
 * mw_update_20030825
 *
 * Revision 1.17  2003/08/22 16:38:50  tony
 * general search
 *
 * Revision 1.16  2003/08/21 19:43:10  tony
 * 51391
 *
 * Revision 1.15  2003/06/25 18:18:48  tony
 * 51359
 *
 * Revision 1.14  2003/06/09 15:51:20  tony
 * 50908
 *
 * Revision 1.13  2003/06/02 21:12:24  tony
 * updated messaging logic to improve performance.
 *
 * Revision 1.12  2003/05/30 21:09:20  tony
 * updated messaging logic to popup on top
 * of the currently active Component.
 *
 * Revision 1.11  2003/05/28 14:40:47  tony
 * 50913
 *
 * Revision 1.10  2003/05/27 21:20:45  tony
 * 50919
 *
 * Revision 1.9  2003/05/15 16:23:38  tony
 * updated messaging logic on search.
 *
 * Revision 1.8  2003/04/03 18:51:48  tony
 * adjusted logic to display individualized icon
 * for each frameDialog/tab.
 *
 * Revision 1.7  2003/04/03 16:55:42  tony
 * cleaned up code.
 *
 * Revision 1.6  2003/03/29 00:06:27  tony
 * added remove Menu Logic
 *
 * Revision 1.5  2003/03/28 17:00:55  tony
 * adjusted logic to use eAccessConstants values.
 *
 * Revision 1.4  2003/03/27 16:24:13  tony
 * adjusted logic for reuse of the editController component.
 *
 * Revision 1.3  2003/03/26 17:18:21  tony
 * removed menu from search panel.
 *
 * Revision 1.2  2003/03/13 18:38:44  tony
 * accessibility and column Order.
 *
 * Revision 1.1.1.1  2003/03/03 18:03:44  tony
 * This is the initial load of OPICM
 *
 *
 */
package com.ibm.eannounce.dialogpanels;
import com.elogin.*;
import com.ibm.eannounce.eforms.action.MatrixAction;
import com.ibm.eannounce.eforms.action.UsedAction;
import com.ibm.eannounce.eforms.navigate.*;
import com.ibm.eannounce.eforms.edit.*;
import com.ibm.eannounce.eforms.table.BooleanTable;
import com.ibm.eannounce.eforms.table.RSTable;
import com.ibm.eannounce.eobjects.*;
import COM.ibm.eannounce.objects.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import javax.swing.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class SearchPanel extends AccessibleDialogPanel implements PropertyChangeListener {
	private static final long serialVersionUID = 1L;
    private EButton btnOK = new EButton(getString("fnd"));
    //51359	private eButton	btnCancel = new eButton(getString("cncl"));
    private EButton btnCancel = new EButton(getString("clse")); //51359
    private EButton btnInfo = new EButton();

    private ELabel lblSearch = new ELabel(getString("fnd-l"));

    private EPropertyField txtSearch = null; //17010

    private SearchActionItem sai = null;

    private EditController ec = new EditController(); //dyna
    //gen_search	private JScrollPane scroll = new JScrollPane(ec);			//dyna
    private BooleanTable srchTbl = new BooleanTable() {
    	private static final long serialVersionUID = 1L;
    	public void valueChanged() {
            validateSelection();
            return;
        }
    }; //gen_search
    private JScrollPane scroll = new JScrollPane(srchTbl); //gen_search

    private EPanel pNorth = new EPanel(new BorderLayout(5, 5));

    private Navigate nav = null;
    private MatrixAction mtrx = null; //searchable picklist
    private UsedAction used = null; //searchable picklist
    private int navType = 0;

//    private boolean bProcess = false;
    private MyFocusTraversalPolicy policy = new MyFocusTraversalPolicy();

    /**
     * searchPanel
     * @author Anthony C. Liberto
     */
    public SearchPanel() {
        super(new BorderLayout(5, 5));
        return;
    }

    /*
     gb_20041124
    	public JMenuBar getMenuBar() {
    		return null;
    	}

    	protected void createMenu() {}
    	protected void removeMenu() {}
    */
    /**
     * init
     * @author Anthony C. Liberto
     */
    public void init() {
        EPanel pSouth = new EPanel(new GridLayout(1, 3));
        txtSearch = new EPropertyField(25, this, MINIMUM_EQUAL_TYPE, "3", DEACTIVATE); //17010
        lblSearch.setLabelFor(txtSearch); //access
        pNorth.add("West", lblSearch);
        add("North", pNorth);
        pSouth.add(btnOK);
        pSouth.add(btnInfo);
        btnInfo.setText(getString("rstA")); //52128
        btnInfo.setMnemonic(getChar("rstA-s")); //52128
        pSouth.add(btnCancel);

        //TIR USRO-R-JSTT-68QPBP		btnOK.setMnemonic(getChar("fnd-s"));		//22554
		if (EAccess.isTestMode()) {									//USRO-R-JSTT-697QCV
			btnOK.setMnemonic(getChar("fnd-s2")); 					//TIR USRO-R-JSTT-68QPBP
		} else {													//USRO-R-JSTT-697QCV
			btnOK.setMnemonic(getChar("fnd-s"));					//USRO-R-JSTT-697QCV
		}															//USRO-R-JSTT-697QCV
        //51359		btnCancel.setMnemonic(getChar("cncl-s"));	//22554
        btnCancel.setMnemonic(getChar("clse-s")); //51359
        btnOK.addActionListener(this);
        btnOK.setActionCommand("process");
        btnInfo.addActionListener(this);
        btnInfo.setActionCommand("misc");
        btnCancel.addActionListener(this);
        btnCancel.setActionCommand("exit");
        setFindEnabled(false); //17010
        add("South", pSouth);
        return;
    }

    /**
     * setFindEnabled
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setFindEnabled(boolean _b) { //17010
        btnOK.setEnabled(_b); //17010
        return; //17010
    } //17010

    /**
     * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
     * @author Anthony C. Liberto
     */
    public void propertyChange(PropertyChangeEvent _pce) { //17010
        String property = _pce.getPropertyName(); //17010
        if (property.equalsIgnoreCase("Activate")) { //17010
            if (srchTbl.hasSelection()) {
                setFindEnabled(true); //17010
            } else {
                setFindEnabled(false);
            }
        } else if (property.equalsIgnoreCase("Deactivate")) { //17010
            setFindEnabled(false); //17010
        }
        return;
    } //17010

    /**
     * disposeDialog
     * @author Anthony C. Liberto
     */
    public void disposeDialog() {
        txtSearch.setText("");
        nav = null; //22541
        ec.gc(); //dyna
        if (isShowing()) { //51069
            super.disposeDialog();
        } //51069
        setBusy(false);
        return;
    }

    /**
     * processRequest
     * @author Anthony C. Liberto
     */
    public void processRequest() {
        if (nav == null) {
            if (ec.canContinue()) { //66N7MJ
                processRequestOther(sai);
            } //66N7MJ
            setBusy(false); //66N7MJ
            return;
        }

        final ESwingWorker myWorker = new ESwingWorker() {
           // boolean bLockList = false;
            public Object construct() {
                if (nav != null) { //22541
                    if (!sai.isDynaSearchEnabled()) {
                        dBase().setSearchString(sai, txtSearch.getText());
                    } else if (!ec.canContinue()) { //22624
                        return null;
                    } //22624
                    if (!processSearch(sai, navType)) { //22541
                        return null;
                    }
                } else if (mtrx != null || used != null) {
                    if (!processSearch(sai, navType)) {
                        return null;
                    }
                }
                return "pass";
            }

            public void finished() {
                Object o = getValue();
                if (o != null) {
                    disposeDialog();
                }
                setWorker(null);
                setBusy(false);
                return;
            }
        };
        setWorker(myWorker);
        return;
    }

    private boolean processSearch(SearchActionItem _sai, int _navType) { //22541
        EntityList eList = dBase().getEntityList(_sai, null, this); //22541
        //50908		if (nav != null) {																		//50919
        if (eList != null) { //50908
            if (nav != null) {
                return nav.processSearchAction(getParentDialog(), eList, _navType); //22541
            }
        } //50919
        return false; //50919
    } //22541

    /**
     * actionPerformed
     * @author Anthony C. Liberto
     * @param _action
     */
    public void actionPerformed(String _action) {
        appendLog("searchPanel.action: " + _action);
        if (isBusy()) {
            return;
        }
        setBusy(true);
        if (_action.equals("exit")) {
            disposeDialog();
        } else if (_action.equals("process")) {
            processRequest();
            return;
        } else if (_action.equals("misc")) {
            if (isDynaSearch()) {
                ec.rollback();
            } else {
                showInformation();
            }
        } else if (_action.equals("eData")) { //gb_20041124
            getInformation(); //gb_20041124
        } //gb_20041124
        setBusy(false);
        return;
    }

    private void showInformation() {
        return;
    }

    /**
     * showSearch
     * @param _sai
     * @param _navType
     * @param _o
     * @author Anthony C. Liberto
     */
    public void showSearch(SearchActionItem _sai, int _navType, Object _o) {
        setSearchActionItem(_sai);
        nav = null; //searchable picklist
        mtrx = null; //searchable picklist
        used = null; //searchable picklist
        if (_o instanceof Navigate) {
            nav = (Navigate) _o;
        } else if (_o instanceof MatrixAction) { //searchable picklist
            mtrx = (MatrixAction) _o; //searchable picklist
        } else if (_o instanceof UsedAction) { //searchable picklist
            used = (UsedAction) _o; //searchable picklist
        }
        navType = _navType;
        setBusy(false);
        showDialog(this, this);
        return;
    }

    /**
     * setSearchActionItem
     * @param _sai
     * @author Anthony C. Liberto
     */
    public void setSearchActionItem(SearchActionItem _sai) {
        sai = _sai;
        setTitle(_sai.toString());
        if (sai.isDynaSearchEnabled()) { //dyna
            removeScroll();
            constructTable(sai); //dyna
            pNorth.remove(txtSearch); //dyna

            //52128			btnInfo.setText(getString("rstA"));
            //52128			btnInfo.setMnemonic(getChar("rstA-s"));		//22554
            btnInfo.setEnabled(true); //52128
            btnInfo.setVisible(true); //52128

            setFindEnabled(true); //dyna
            lblSearch.setVisible(false); //22544
            scroll.setVisible(false); //gen_search
            setModal(false); //acl_20021108
        } else { //dyna
            txtSearch.setParameter(Routines.toString(_sai.getSearchStringMin())); //mw_update_20030825
            removeTable(); //dyna
            constructScroll(_sai);
            pNorth.add("Center", txtSearch); //dyna
            //52128			btnInfo.setText(getString("info"));
            //52128			btnInfo.setMnemonic(getChar("info-s"));		//22554
            btnInfo.setEnabled(false); //52128
            btnInfo.setVisible(false); //52128

            setFindEnabled(false); //dyna
            lblSearch.setVisible(true); //22544
            scroll.setVisible(true); //gen_search
            setModal(false); //acl_20021108
        } //dyna
        packDialog(); //dyna
        return;
    }

    /**
     * constructTable
     * @param _sai
     * @author Anthony C. Liberto
     */
    public void constructTable(SearchActionItem _sai) { //dyna
        ec.build(_sai); //dyna
        //52005		pNorth.add("North", (JComponent)ec);				//dyna
        add("Center", (JComponent) ec); //52005
        return; //dyna
    } //dyna

    /**
     * removeTable
     * @author Anthony C. Liberto
     */
    public void removeTable() { //dyna
        remove(ec); //dyna
        return; //dyna
    } //dyna

    /**
     * constructScroll
     * @param _sai
     * @author Anthony C. Liberto
     */
    public void constructScroll(SearchActionItem _sai) { //gen_search
        SearchRequest sr = _sai.getSearchRequest(); //gen_search
        if (sr != null) {
            srchTbl.updateModel(sr.getTable());
            srchTbl.sort(1, true); //52522
        }
        //52188		pNorth.add("South",scroll);							//gen_search
        add("Center", scroll); //52188
        return; //gen_search
    } //gen_search

    /**
     * removeScroll
     * @author Anthony C. Liberto
     */
    public void removeScroll() { //gen_search
        //52188		pNorth.remove(scroll);								//gen_search
        remove(scroll); //52188
        return; //gen_search
    } //gen_search

    /**
     * isDynaSearch
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isDynaSearch() {
        if (sai != null) {
            return sai.isDynaSearchEnabled();
        }
        return false;
    }

    /**
     * getSearchActionItem
     * @return
     * @author Anthony C. Liberto
     */
    public SearchActionItem getSearchActionItem() {
        return sai;
    }

    /**
     * getIconName
     * @author Anthony C. Liberto
     * @return String
     */
    public String getIconName() {
        return "search.gif";
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
     * @author Anthony C. Liberto
     * @return String
     */
    public String getTitle() {
        return getString("search.panel");
    }

    /*
     50913
     */
    /**
     * setParentDialog
     * @author Anthony C. Liberto
     * @param _id
     */
    public void setParentDialog(InterfaceDialog _id) {
        id = _id;
        //		if (ec != null) {
        //			metaValidator valid = ec.getValidator();
        //			if (valid != null) {
        //				valid.setInterfaceDialog(id);
        //			}
        //		}
        return;
    }

    /**
     * isResizable
     * @author Anthony C. Liberto
     * @return boolean
     */
    public boolean isResizable() {
        return true;
    }

    private void validateSelection() {
        if (txtSearch != null && txtSearch.isActive()) {
            if (srchTbl.hasSelection()) {
                setFindEnabled(true);
                return;
            }
        }
        setFindEnabled(false);
        return;
    }

    /**
     * getDynaTable
     * @return
     * @author Anthony C. Liberto
     */
    public Component getDynaTable() {
        return ec.getDisplayableTableComponent();
    }

    /*
     accessibility
     */
    /**
     * hasCustomFocusPolicy
     * @author Anthony C. Liberto
     * @return boolean
     */
    public boolean hasCustomFocusPolicy() {
        return true;
    }

    /**
     * getFocusTraversalPolicy
     * @author Anthony C. Liberto
     * @return FocusTraversalPolicy
     */
    public FocusTraversalPolicy getFocusTraversalPolicy() {
        return policy;
    }

    private class MyFocusTraversalPolicy extends FocusTraversalPolicy {
        /**
         * @see java.awt.FocusTraversalPolicy#getComponentAfter(java.awt.Container, java.awt.Component)
         * @author Anthony C. Liberto
         */
        public Component getComponentAfter(Container _cont, Component _comp) {
            if (_comp == getDynaTable() || _comp == srchTbl) {
                if (btnOK.isEnabled()) {
                    return btnOK;
                } else if (btnInfo.isShowing()) {
                    return btnInfo;
                } else {
                    return btnCancel;
                }
            } else if (_comp == txtSearch) {
                return srchTbl;
            } else if (_comp == btnOK) {
                if (btnInfo.isShowing()) {
                    return btnInfo;
                } else {
                    return btnCancel;
                }
            } else if (_comp == btnInfo) {
                return btnCancel;
            } else if (_comp == btnCancel) {
                return getFirstComponent(_cont);
            }
            return null;
        }

        /**
         * @see java.awt.FocusTraversalPolicy#getComponentBefore(java.awt.Container, java.awt.Component)
         * @author Anthony C. Liberto
         */
        public Component getComponentBefore(Container _cont, Component _comp) {
            if (_comp == getDynaTable() || _comp == txtSearch) {
                return btnCancel;
            } else if (_comp == srchTbl) {
                return txtSearch;
            } else if (_comp == btnOK) {
                if (isDynaSearch()) {
                    return getDynaTable();
                } else {
                    return srchTbl;
                }
            } else if (_comp == btnInfo) {
                return btnOK;
            } else if (_comp == btnCancel) {
                if (btnInfo.isShowing()) {
                    return btnInfo;
                } else if (btnOK.isEnabled()) {
                    return btnOK;
                } else {
                    if (isDynaSearch()) {
                        return getDynaTable();
                    } else {
                        return srchTbl;
                    }
                }

            }
            return null;
        }

        /**
         * @see java.awt.FocusTraversalPolicy#getDefaultComponent(java.awt.Container)
         * @author Anthony C. Liberto
         */
        public Component getDefaultComponent(Container _cont) {
            return getFirstComponent(_cont);
        }

        /**
         * @see java.awt.FocusTraversalPolicy#getFirstComponent(java.awt.Container)
         * @author Anthony C. Liberto
         */
        public Component getFirstComponent(Container _cont) {
            if (isDynaSearch()) {
                return getDynaTable();
            } else {
                return txtSearch;
            }
        }

        /**
         * @see java.awt.FocusTraversalPolicy#getInitialComponent(java.awt.Window)
         * @author Anthony C. Liberto
         */
        public Component getInitialComponent(Window _win) {
            return getDefaultComponent(null);
        }

        /**
         * @see java.awt.FocusTraversalPolicy#getLastComponent(java.awt.Container)
         * @author Anthony C. Liberto
         */
        public Component getLastComponent(Container _cont) {
            return btnCancel;
        }
    }

    /*
     acl_20031217
     */
    /**
     * activateMe
     * @author Anthony C. Liberto
     */
    public void activateMe() {
        if (isDynaSearch()) {
            Component tbl = getDynaTable();
            if (tbl != null) {
                tbl.requestFocus();
            }
        } else {
            if (txtSearch != null) {
                txtSearch.requestFocus();
            }
        }
        return;
    }

    /*
     searchable picklist
     */
    private void processRequestOther(SearchActionItem _sai) {
        EntityList eList = dBase().getEntityList(_sai, null, this);
        if (eList != null && hasData(eList)) {
            if (mtrx != null) {
                disposeDialog();
                mtrx.showPicklist(eList);
                return;
            } else if (used != null) {
                disposeDialog();
                used.showPicklist(eList);
                return;
            }
        }
        showError("msg24005");
        return;
    }

    private boolean hasData(EntityList _eList) {
        if (_eList != null) {
            int ii = _eList.getEntityGroupCount();
            for (int i = 0; i < ii; ++i) {
                EntityGroup eg = _eList.getEntityGroup(i);
                if (eg != null) {
                    if (eg.isDisplayable() && eg.getEntityItemCount() > 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /*
     gb_20041124
     */
    /**
     * getInformation
     * @author Anthony C. Liberto
     */
    protected void getInformation() {
        if (isDynaSearch()) {
            Component c = getDynaTable();
            if (c != null && c instanceof RSTable) {
                ((RSTable) c).showInformation();
            }
        }
        return;
    }
    /**
     * getMenuBar
     * @author Anthony C. Liberto
     * @return JMenuBar
     */
    public JMenuBar getMenuBar() {
        if (EAccess.isTestMode()) {
            return super.getMenuBar();
        }
        return null;
    }

    /**
     * createMenu
     * @author Anthony C. Liberto
     */
    protected void createMenu() {
        String strKey = null;
        if (menubar != null) {
            menubar.removeAll();
            menubar.addMenu("File", "exit", this, KeyEvent.VK_F4, Event.ALT_MASK, true);
            menubar.setMenuMnemonic("File", 'F');
            strKey = getString("tbl");
            menubar.addMenu(strKey, "eData", this, KeyEvent.VK_F12, Event.CTRL_MASK, true);
            menubar.setMenuMnemonic(strKey, getChar("tbl-s"));
        }
        return;
    }

    /**
     * removeMenu
     * @author Anthony C. Liberto
     */
    protected void removeMenu() {
        if (menubar != null) {
            menubar.removeMenuItem("exit", this);
            menubar.removeMenuItem("eData", this);
            menubar.removeAll();
        }
        return;
    }

}
