/**
 * Copyright (c) 2003-2004 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 * This is the main cart panel.  Most of the functionality is maintained
 * here.  There are custom methods in navCartTab and navCartProfile.
 *
 * for ease of integration this can be renamed navCart.  This will allow
 * for the ability to toggle between the navCartTab and navCartProfile.
 *
 *  -------------
 * | cr903035214 |
 *  -------------
 *
 * @version 1.2  2003/11/03
 * @author Anthony C. Liberto
 *
 * $Log: NavCartDialog.java,v $
 * Revision 1.6  2012/09/05 14:40:28  wendy
 * RCQ00213801  Capability enhancement
 *
 * Revision 1.5  2010/03/11 15:26:55  wendy
 * prevent clearing persistent cart when derefed
 *
 * Revision 1.4  2009/05/28 14:18:09  wendy
 * Performance cleanup
 *
 * Revision 1.3  2009/04/16 17:54:42  wendy
 * Cleanup history
 *
 * Revision 1.2  2008/01/30 16:26:54  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:44:49  wendy
 * Reorganized JUI module
 *
 * Revision 1.3  2006/10/26 20:03:07  tony
 * MN29626005
 *
 * Revision 1.2  2005/09/12 19:03:14  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:38:00  tony
 * This is the initial load of OPICM
 *
 * Revision 1.14  2005/09/08 17:59:04  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.13  2005/05/03 20:54:20  tony
 * MN23855544
 *
 * Revision 1.12  2005/02/03 21:26:12  tony
 * JTest Format Third Pass
 *
 * Revision 1.11  2005/02/03 16:38:53  tony
 * JTest Second Pass Wrapup
 *
 * Revision 1.10  2005/02/01 00:27:55  tony
 * JTest Second Pass
 *
 * Revision 1.9  2005/01/26 23:42:27  tony
 * JTest Formatting
 *
 * Revision 1.8  2005/01/14 19:52:25  tony
 * adjusted button logic to troubleshoot
 * Button disappearing issue.
 *
 * Revision 1.7  2004/11/08 19:01:40  tony
 * Improved sort logic to no longer sort multiple times on a
 * navigation reload.
 *
 * Revision 1.6  2004/08/26 16:26:36  tony
 * accessibility enhancement.  Adjusted menu and toolbar
 * appearance for the accessibility impaired.  Improved
 * accessibility performance by enabling preference toggling.
 *
 * Revision 1.5  2004/08/09 21:22:12  tony
 * improved logging
 *
 * Revision 1.4  2004/06/17 18:58:57  tony
 * cr_4215 cr0313024215
 *
 * Revision 1.3  2004/03/25 23:37:20  tony
 * cr_216041310
 *
 * Revision 1.2  2004/02/26 21:53:17  tony
 * remoteDatabaseInterface centralization.
 *
 * Revision 1.1.1.1  2004/02/10 16:59:43  tony
 * This is the initial load of OPICM
 *
 * Revision 1.8  2004/01/16 16:49:48  tony
 * 53548
 * added find/replace and filter menu to NavcartDialog
 * this will allow for unfilter when everything is filtered
 * out and the right click functionality is not available.
 *
 * Revision 1.7  2004/01/16 15:36:09  tony
 * 53548
 *
 * Revision 1.6  2004/01/08 17:40:03  tony
 * 53511
 *
 * Revision 1.5  2003/12/12 16:58:27  tony
 *  5214_B reporting
 *
 * Revision 1.4  2003/12/11 22:29:19  tony
 * cr_5214_2
 *
 * Revision 1.3  2003/11/07 19:18:52  tony
 * CR_903035214
 *
 * Revision 1.2  2003/11/06 16:39:17  tony
 * added synchronization logic
 *
 * Revision 1.1  2003/11/05 17:12:02  tony
 * cr_persistant_cart
 * Change Request to make cart persistant across tabs.
 * This is the first pass at addressing the issue.  It is not
 * currently implemented, but all of the source code is in
 * place and ready for test.
 * To implement this change navigate needs to use the new
 * navCartDialog instead of the existing navCart.  There are
 * two flavors... navCartTab, is similar to the current
 * implementation and navCartProfile is a cross tab
 * implementation.
 *
 */
package com.ibm.eannounce.eforms.navigate;
import com.elogin.*;
import com.ibm.eannounce.eobjects.*;
import COM.ibm.eannounce.objects.*;

import com.ibm.eannounce.exception.*;
import COM.ibm.opicmpdh.transactions.OPICMList;
import java.awt.event.*;
import java.awt.*;
import javax.swing.event.*;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.*;

import COM.ibm.opicmpdh.middleware.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class NavCartDialog extends AccessibleDialogPanel implements ChangeListener, ActionListener, EAccessConstants {
	private static final long serialVersionUID = 1L;
	/**
     * DATA_SINGLE
     */
    public static final int DATA_SINGLE  = 0;
	/**
     * DATA_DOUBLE
     */
    public static final int DATA_DOUBLE  = 1;
	/**
     * DATA_FORM
     */
    public static final int DATA_FORM	 = 2;
    
    private Navigate parent = null;
    private ERolloverButton btnPaste = null;
    private MultiButton btnClear = null;
    private MultiButton btnAdd = null;
    private EPanel pnlNorth = new EPanel(new BorderLayout());
    private EPanel pnlCntrl = new EPanel(new BorderLayout());
    private EPanel pnlCenter = new EPanel(new BorderLayout());
    private NavData data = null;
    private String[] raClear = {"minus.gif","rmvS", "rmvT", "rmvA"};
    private String[] raAdd = {"plus.gif","add2cart", "add2cartA"};
    private MetaLink[] metaLink = null;
    private EToolBar toolbar = null;
    private boolean onForm = false;
    private EPopupMenu popup = new EPopupMenu("popup");
    private boolean bResize = true;
    private CartList cList = null;

	/**
     * nsoController
     */
    //protected SerialObjectController nsoController = null;
	/**
     * nso
     */
    //protected NavSerialObject nso = null;

	/**
     * navCartDialog
     * @param _parent
     * @author Anthony C. Liberto
     */
    protected NavCartDialog(Navigate _parent) {
		super(new BorderLayout());
		if (!EAccess.isArmed(ACCESSIBLE_ARM_FILE)) {
			toolbar = new EToolBar("Cart");
		}

		createPopupMenu();
		parent = _parent;
		btnClear = createButton(raClear);
		btnPaste = new ERolloverButton(getImageIcon("paste.gif"),"paste.gif");
		btnPaste.setActionCommand("paste");
		btnPaste.setToolTipText(getString("cpste"));
		btnPaste.addActionListener(this);
		btnPaste.setEnabled(false);
		btnAdd = createButton(raAdd);
		Dimension d = new Dimension(500,600);
		setPreferredSize(d);
		setSize(d);
		setMinimumSize(d);
		setMinimumSize(UIManager.getDimension("eannounce.minimum"));
		build();
	}

    private void build() {
		createEntityList();
		data = new NavDataSingle(getEntityList(),false);//createData(DATA_SINGLE);
		data.getAccessibleContext().setAccessibleDescription(getString("accessible.navCart"));
		data.setMouseListener(popup);
		data.addChangeListener(this);
		cList.addSynchListener(data);
		init();
		//nsoController = new SerialObjectController(parent.getProfile());
		//nso = nsoController.generate(getEntityList(), "serialCart", true, 0);
	}
    /*private NavData createData(int _type) {
		if (_type == DATA_SINGLE) {
			return new NavDataSingle(getEntityList(),false);
		} else if (_type == DATA_DOUBLE) {
			return new NavDataDouble(getEntityList(),false);
		} else if (_type == DATA_FORM) {
			return new NavDataForm(getEntityList(),false);
		}
		return null;
	}*/
	/**
     * createPopupMenu
     * @author Anthony C. Liberto
     */
    private void createPopupMenu() {
		popup.addPopupMenu("srtA","srtA",this);
		popup.addPopupMenu("srtD","srtD",this);
		popup.addSeparator();
		popup.addPopupMenu("f/r","f/r",this);
		popup.addPopupMenu("fltr","fltr",this);
		popup.addSeparator();
		popup.addPopupMenu("selA","selA",this);
		popup.addPopupMenu("iSel","iSel",this);
	}

    /**
     * createMenu
     *
     * @author Anthony C. Liberto
     */
    protected void createMenu() {
		if (menubar != null) {									//53548
			menubar.removeAll();								//53548
			createActionMenu();									//53548
			createTableMenu();									//53548
		}														//53548
	}

	/**
     * createActionMenu
     * @author Anthony C. Liberto
     */
    private void createActionMenu() {							//53548
		String strKey = getString("act");
//53548		menubar.removeAll();
		menubar.addMenu(strKey,"add2cart",this,KeyEvent.VK_A, Event.CTRL_MASK + Event.SHIFT_MASK,true);
		menubar.addMenu(strKey,"add2cartA",this,0,0,true);
		menubar.addSeparator(strKey);
		menubar.addMenu(strKey,"rmvS",this,0,0,true);
		menubar.addMenu(strKey,"rmvT",this,0,0,true);
		menubar.addMenu(strKey,"rmvA",this,0,0,true);
		menubar.addSeparator(strKey);
		menubar.addMenu(strKey,"lnkA",this,0,0,true);			//cr_5214_2
		menubar.setMenuMnemonic(strKey, getChar("act-s"));
	}

	/**
     * createTableMenu
     * @author Anthony C. Liberto
     */
    private void createTableMenu() {												//53548
		String strKey = getString("tbl");											//53548
		menubar.addMenu(strKey,"f/r",this,KeyEvent.VK_F, Event.CTRL_MASK,true);		//53548
		menubar.addMenu(strKey,"fltr",this,KeyEvent.VK_F8,0,true);					//53548
		menubar.setMenuMnemonic(strKey, getChar("tbl-s"));							//53548
	}																				//53548

	/**
     * removePopupMenu
     * @author Anthony C. Liberto
     */
    private void removePopupMenu() {
		popup.removeMenu("srtA",this);
		popup.removeMenu("srtD",this);
		popup.removeMenu("f/r",this);
		popup.removeMenu("fltr",this);
		popup.removeMenu("selA",this);
		popup.removeMenu("iSel",this);
	}

    /**
     * removeMenu
     *
     * @author Anthony C. Liberto
     */
    protected void removeMenu() {								//53548
		removeActionMenu();										//53548
		removeTableMenu();										//53548
		menubar.removeAll();									//53548
	}															//53548

	/**
     * removeActionMenu
     * @author Anthony C. Liberto
     */
    private void removeActionMenu() {							//53548
		menubar.removeMenuItem("add2cart",this);
		menubar.removeMenuItem("add2cartA",this);
		menubar.removeMenuItem("rmvS",this);
		menubar.removeMenuItem("rmvT",this);
		menubar.removeMenuItem("rmvA",this);
		menubar.removeMenuItem("lnkA",this);					//cr_5214_2
	}

	/**
     * removeTableMenu
     * @author Anthony C. Liberto
     */
    private void removeTableMenu() {							//53548
		menubar.removeMenuItem("f/r",this);						//53548
		menubar.removeMenuItem("fltr",this);					//53548
	}															//53548

	/**
     * getLinkType
     * @return
     * @author Anthony C. Liberto
     */
    public String getLinkType() {
		return eaccess().getLinkType();
	}

	/**
     * isOnForm
     * @return
     * @author Anthony C. Liberto
     */
    protected boolean isOnForm() {
		return onForm;
	}

	/**
     * setOnForm
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setOnForm(boolean _b) {
		onForm = _b;
	}

	/**
     * @see java.awt.Component#setFont(java.awt.Font)
     * @author Anthony C. Liberto
     */
    public void setFont(Font _f) {
		super.setFont(_f);
		if (btnClear != null) {
            btnClear.setFont(_f);
		}
		if (btnAdd != null) {
            btnAdd.setFont(_f);
		}
	}


	/**
     * getSingleRelator
     * @param _egParent
     * @param _egChild
     * @return
     * @author Anthony C. Liberto
     *
    protected MetaLink getSingleRelator(EntityGroup _egParent, EntityGroup _egChild) {
		MetaLink[] relator = _egParent.getMetaLinkList(_egChild);
		if (relator.length == 1) {
			return relator[0];
		}
		return null;
	}*/

	/**
     * reportLink
     * @param _eiParent
     * @param _eiChild
     * @param _rel
     * @return
     * @author Anthony C. Liberto
     */
    private String reportLink(EntityItem[] _eiParent, EntityItem[] _eiChild, MetaLink _rel) {
        StringBuffer sb = null;
        String msg = null;
        int pp = -1;
        int cc = -1;

        if (_eiParent == null || _eiChild == null) {
			return null;
		}
		sb = new StringBuffer();
		msg = " " + getString("l2") + " ";
		pp = _eiParent.length;
		cc = _eiChild.length;
		for (int p=0;p<pp;++p) {
			for (int c=0;c<cc;++c) {
				sb.append(getParentString(_eiParent[p], _rel) + msg + _eiChild[c].toString() + RETURN);
			}
		}
		return sb.toString();
	}

	/**
     * getParentString
     * @param _ei
     * @param _rel
     * @return
     * @author Anthony C. Liberto
     */
    private String getParentString(EntityItem _ei, MetaLink _rel) {
		String e1Type = null;
        EANEntity ent = null;
        if (_rel == null) {
			return _ei.toString();
		}
		e1Type = _rel.getEntity1Type();
		if (e1Type.equals(_ei.getEntityType())) {
			return _ei.toString();
		}
		ent = _ei.getDownLink(0);
		if (ent != null && e1Type.equals(ent.getEntityType())) {
			return ent.toString();
		}
		return _ei.toString();
	}

	/**
     * pasteCopies
     * @return
     * @author Anthony C. Liberto
     */
    private int pasteCopies() {
		return eaccess().getNumber((Window)getParentDialog(),"msg3013");
	}


	/**
     * getRelatorType
     * @param _link
     * @return
     * @author Anthony C. Liberto
     */
    private MetaLink getRelatorType(MetaLink[] _link) {
		if (_link == null) {
			return null;
		} else if (_link.length == 0) {
			showError("msg23043");
			return null;
		} else if (_link.length == 1) {
			return _link[0];
		}
		return eaccess().getRelator(_link);
	}

	private MultiButton createButton(String[] _s) {
		MultiButton btn = new MultiButton(_s, this);
		btn.setActionCommand(_s[1]);
		btn.setToolTipText(getString(_s[1]));
		return btn;
	}

    /**
     * init
     *
     * @author Anthony C. Liberto
     */
    public void init() {
		if (toolbar != null) {
			toolbar.add(btnPaste);
			toolbar.add(btnAdd);
			toolbar.add(btnClear);
			add("North", toolbar);
		}
		add("Center", (Component)data);
	}

	/**
     * rebuildCart
     * @param _eList
     * @author Anthony C. Liberto
     */
    private void rebuildCart(EntityList _eList) {
		int ii = _eList.getEntityGroupCount();
		for (int i=0;i<ii;++i) {
			refreshTab(_eList.getEntityGroup(i),false);
		}
		setEntityList(_eList);
	}

	/**
     * getNavigate
     * @return
     * @author Anthony C. Liberto
     *
    public Navigate getNavigate() {
		return parent;
	}*/

	/**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @author Anthony C. Liberto
     */
    public void actionPerformed(ActionEvent _ae) {
		String action = _ae.getActionCommand();
		appendLog("navCart.action: " + action);
		if (isBusy()) {
			appendLog("    I am busy");
			return;
		}
		setBusy(true);
		if (action.equals("add2cart")) {
			addToCart(getActiveNavigate());
		} else if (action.equals("add2cartA")) {
			addToCartAll(getActiveNavigate());
		} else if (action.equals("f/r")) {
//53548			data.getTable().showFind();
			data.getTable().showFind(id);			//53548
		} else if (action.equals("fltr")) {
//53548			data.getTable().showFilter();
			data.getTable().showFilter(id);			//53548
		} else if (action.equals("selA")) {
			data.getTable().selectAll();
		} else if (action.equals("iSel")) {
			data.getTable().invertSelection();
		} else if (action.equals("srtD")) {
			data.getTable().sort(false);
		} else if (action.equals("srtA")) {
			data.getTable().sort(true);
		} else if (action.equals("rmvA")) {
			clearAll(true);
		} else if (action.equals("rmvT")) {
			clear(data.getEntityGroup());
		} else if (action.equals("rmvS")) {
			removeSelected();
		} else if (action.equals("link")) {
			link();
		} else if (action.equals("lnkA")) {
			linkAll();
		} else if (action.equals("copyC")) {
			copyCreate();
		} else if (action.equals("copyA")) {
			copyAll();
		} else if (action.equals("paste")) {
			pasteCut();
		}
		setBusy(false);
	}

	/**
     * getSelectedEntityGroup
     * @return
     * @author Anthony C. Liberto
     */
    private EntityGroup getSelectedEntityGroup() {
		return data.getEntityGroup();
	}

	/**
     * getIndexOf
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    private int getIndexOf(String _s) {
		return data.getIndexOf(_s);
	}

	/**
     * getEntityGroup
     * @param _key
     * @return
     * @author Anthony C. Liberto
     */
    public EntityGroup getEntityGroup(String _key) {
		return data.getEntityGroup(getIndexOf(_key));
	}

    /**
     * dereference
     *
     * @author Anthony C. Liberto
     */
    public void dereference() {
      	super.dereference();
    	
		parent = null;
		removePopupMenu();

		if (btnClear != null) {
			btnClear.dereference();
			btnClear = null;
		}

		if (btnAdd != null) {
			btnAdd.dereference();
			btnAdd = null;
		}

		if (btnPaste != null) {
			btnPaste.removeActionListener(this);
			btnPaste.removeAll();
			btnPaste = null;
		}

		//this empties persistent too clearAll(false);
		pnlNorth.removeAll();
		pnlNorth = null;
		pnlCntrl.removeAll();
		pnlCntrl = null;
		pnlCenter.removeAll();
		pnlCenter = null;
		if (data != null) {
			((NavDataSingle)data).eList=null;  // dont allow it to deref this because list is shared
			data.removeChangeListener(this);
			data.dereference();
			data = null;
		}
		raClear = null;
		//raLink = null;
		//raCopy = null;
		raAdd = null;

		metaLink = null;
		if (toolbar != null) {
			toolbar.removeAll();
			toolbar = null;
		}
		//linkType = null;
		/*if (nso != null) {
			nso.dereference();
			nso = null;
		}
		nsoController = null;*/
		if (cList != null) {
			cList.removeSynchListener(data);
			cList = null;
		}
	}

    /**
     * showMe
     *
     * @author Anthony C. Liberto
     */
    public void showMe() {}

    /**
     * hideMe
     *
     * @author Anthony C. Liberto
     */
    public void hideMe() {}

    /**
     * activateMe
     *
     * @author Anthony C. Liberto
     */
    public void activateMe() {}
	/**
     * setTitle
     * @author Anthony C. Liberto
     * @param _title
     */
    public void setTitle(String _title) {}

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
     * getTitle
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getTitle() {
		return getString("cart.panel");
	}

    /**
     * refreshAppearance
     *
     * @author Anthony C. Liberto
     */
    public void refreshAppearance() {
		data.refreshAppearance();
	}
    public void refresh() {
		rebuildCart(getEntityList());
	}

    /**
     * getIconName
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getIconName() {
		return "histLoad.gif";
	}

    /**
     * getPanelType
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getPanelType() {
		return TYPE_NAVCART;
	}

    /**
     * getSearchableObject
     *
     * @return
     * @author Anthony C. Liberto
     */
    public Object getSearchableObject() {
		if (data != null) {
			return data.getTable();
		}
		return null;
	}

    /**
     * getActiveNavigate
     * @return
     * @author Anthony C. Liberto
     */
    private Navigate getActiveNavigate() {
		if (parent != null) {
			return parent.getActiveNavigate();
		}
        return parent;
	}

	/**
     * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
     * @author Anthony C. Liberto
     */
    public void stateChanged(ChangeEvent _ce) {
    	if (parent==null){ // derefenced
    		return;
    	}
		EntityGroup egParent = getSelectedEntityGroup();
		if (egParent != null) {
			btnPaste.setEnabled(egParent == getCutGroup());
		} else {
			btnPaste.setEnabled(false);
		}
	}

	/**
     * removeTab
     * @param _s
     * @author Anthony C. Liberto
     */
    private void removeTab(String _s) {
		if (cList != null) {
			cList.removeTab(data,_s);
		}
	}

	/**
     * refreshTab
     * @param _eg
     * @param _reload
     * @author Anthony C. Liberto
     */
    private void refreshTab(EntityGroup _eg,boolean _reload) {
		if (_eg != null) {
			data.refreshTab(_eg,_reload);
		}
	}

	/**
     * removeSelected
     * @author Anthony C. Liberto
     */
    private void removeSelected() {
		EntityItem[] dataItems = null;
		try {
			dataItems = data.getSelectedEntityItems(true,true);
			clear(data.getEntityGroup(), dataItems);
		} catch (OutOfRangeException _range) {
			_range.printStackTrace();
			showException(_range, FYI_MESSAGE,OK);
		}
		dataItems = null;
	}

	/**
     * link
     * @author Anthony C. Liberto
     */
    private void link() {
		NavData nd = getActiveNavigate().getData();
		EntityItem[] ndItems = null;
		EntityItem[] dataItems = null;
        String out = null;
		try {

			ndItems = nd.getSelectedEntityItems(false,true);
			dataItems = data.getSelectedEntityItems(false,true);

			out = link(ndItems,dataItems, getRelatorType(metaLink), EANUtility.LINK_DEFAULT, 1);
			if (out != null) {
				eaccess().showLinkDialog((Window)id,getString("msg3011.0"),out);
			}
		} catch (OutOfRangeException _range) {
			_range.printStackTrace();
			showException(_range, FYI_MESSAGE, OK);
		}
		ndItems = null;
		dataItems = null;
		nd = null;
	}

	/**
     * copyCreate
     * @author Anthony C. Liberto
     */
    private void copyCreate() {
		EntityItem[] ndItems = null;
		EntityItem[] dataItems = null;
	    NavData nd = getActiveNavigate().getData();
        MetaLink relator = null;
		try {
			ndItems = nd.getSelectedEntityItems(false,true);
			dataItems = data.getSelectedEntityItems(false,true);
			relator = getRelatorType(metaLink);
			if (relator != null) {
				int copies = pasteCopies();
				if (copies > 0) {
					String out = link(ndItems,dataItems,relator,EANUtility.LINK_COPY,copies);
					if (out != null) {
						eaccess().showLinkDialog((Window)id,getString("msg3011.0"),out);
					}
				}
			}
		} catch (OutOfRangeException _range) {
			_range.printStackTrace();
			showException(_range,FYI_MESSAGE,OK);
		}
		ndItems = null;
		dataItems = null;
		nd = null;
	}

	/**
     * copyAll
     * @author Anthony C. Liberto
     */
    private void copyAll() {
		EntityItem[] ndItems = null;
		EntityItem[] dataItems = null;
		NavData nd = getActiveNavigate().getData();
		MetaLink relator = null;
        try {
			ndItems = nd.getSelectedEntityItems(false,true);
			dataItems = data.getSelectedEntityItems(false,true);
			relator = getRelatorType(metaLink);
			if (relator != null) {
				int copies = pasteCopies();
				if (copies > 0) {
					String out = link(ndItems,dataItems,relator,EANUtility.LINK_COPY,copies);
					if (out != null) {
						eaccess().showLinkDialog((Window)id,getString("msg3011.0"),out);
					}
				}
			}
		} catch (OutOfRangeException _range) {
			_range.printStackTrace();
			showException(_range,FYI_MESSAGE,OK);
		}
		ndItems = null;
		dataItems = null;
		nd = null;
	}

	/**
     * link
     * @param _eiParent
     * @param _eiChild
     * @param _rel
     * @param _linkType
     * @param _linkCount
     * @return
     * @author Anthony C. Liberto
     */
    private String link(EntityItem[] _eiParent, EntityItem[] _eiChild, MetaLink _rel, int _linkType, int _linkCount) {
		String out = null;
        if (_rel == null) {
            return null;
		}
		if (_eiParent == null) {
            return null;
		}
		if (_eiChild == null) {
            return null;
		}

		out = reportLink(_eiParent, _eiChild, _rel);

		if (dBase().link(getLinkType(), _eiParent, _eiChild, _rel, _linkType, _linkCount)) {
			return out;
		}
		return null;
	}

	/**
     * addToCart
     * @param _ei
     * @author Anthony C. Liberto
     */
    public void addToCart(EntityItem[] _ei) {
        EntityGroup eg = null;
		if (getEntityList() == null || _ei == null) {
			return;
		}
		for (int i=0; i < _ei.length; i++) {
			if (!_ei[i].isNew()) {
				EntityItem[] aei = {_ei[i]};
				eg = dBase().addToCart(getEntityList(),aei);
				if (eg != null) {
					refreshTab(eg,false);
				}
			}
		}
		/*if (eg != null) {
			nso.setEntityList(getEntityList());
			nsoController.write(nso);
		}*/
	}

	/**
     * clearAll
     * @param _save
     * @author Anthony C. Liberto
     */
    protected void clearAll(boolean _save) {
		while (getEntityList().getEntityGroupCount() > 0) {
			EntityGroup eg = getEntityList().getEntityGroup(0);
			removeTab(eg.getKey());
			getEntityList().removeEntityGroup(eg);
		}
		clearCutGroup();
		if (_save) {
			save();
		}
	}

	/**
     * clearCutGroup
     * @author Anthony C. Liberto
     */
    private void clearCutGroup() {
		if (getCutGroup() == null) {
			return;
		}
		while (getCutGroup().getEntityItemCount() > 0) {
			EntityItem ei = getCutGroup().getEntityItem(0);
			getCutGroup().removeEntityItem(ei);
		}
		removeTab(getCutGroup().getKey());
		btnPaste.setEnabled(false);
		setCutGroup(null);
	}

	/**
     * save
     * @author Anthony C. Liberto
     */
    private void save() {
		/*if (nso != null && getEntityList() != null) {
			nso.setEntityList(getEntityList());
			nsoController.write(nso);
		}*/
	}

	/**
     * clear
     * @param _eg
     * @author Anthony C. Liberto
     */
    private void clear(EntityGroup _eg) {
		if (_eg == null) {
            return;
		}
		removeTab(_eg.getKey());
		getEntityList().removeEntityGroup(_eg);
		if (_eg == getCutGroup()) {
			clearCutGroup();
		} else {
			_eg = null;
		}
		//nso.setEntityList(getEntityList());
		//nsoController.write(nso);
	}

	/**
     * clear
     * @param _eg
     * @param _ei
     * @author Anthony C. Liberto
     */
    private void clear(EntityGroup _eg, EntityItem[] _ei) {
	    int ii = -1;
        int reply = -1;
        if (_eg == null || _ei == null) {
            return;
	    }
		ii = _ei.length;
		for (int i=0;i<ii;++i) {
			_eg.removeEntityItem(_ei[i]);
		}
		if (_eg.getEntityItemCount() == 0) {
			if (_eg == getCutGroup()) {
				removeTab(_eg.getKey());
				getEntityList().removeEntityGroup(_eg);
				//nso.setEntityList(getEntityList());
			//	nsoController.write(nso);
				btnPaste.setEnabled(false);
				return;
			} else {
				setCode("msg11003");
				reply = showConfirm(OK_CANCEL,true);
				if (reply == OK) {
					removeTab(_eg.getKey());
					getEntityList().removeEntityGroup(_eg);
				//	nso.setEntityList(getEntityList());
					//nsoController.write(nso);
					return;
				}
			}
		}
		///nso.setEntityList(getEntityList());
		//nsoController.write(nso);
		refreshTab(_eg,false);
	}

	/**
     * addCutItems
     * @param _ei
     * @author Anthony C. Liberto
     */
    protected void addCutItems(EntityItem[] _ei) {
		EntityGroup grp = null;
        if (getCutGroup() != null) {
			clearCutGroup();
		}
		if (_ei == null)  {
			return;
		}
		grp = dBase().cloneEntityItem(getEntityList(),_ei);
		if (grp != null) {
			setCutGroup(grp);
			if (getCutGroup() != null) {
				getCutGroup().putLongDescription("Move");
				refreshTab(getCutGroup(),false);
				btnPaste.setEnabled(true);
			}
		}
	}

	/**
     * addToCart
     * @param _parent
     * @author Anthony C. Liberto
     */
    protected void addToCart(Navigate _parent) {
		NavData nd = _parent.getData();
		EntityItem[] ndItems = null;
		try {
			ndItems = nd.getSelectedEntityItems(false,true);
			addToCart(ndItems);
		} catch (OutOfRangeException _range) {
			_range.printStackTrace();
			showException(_range, FYI_MESSAGE,OK);
		}
		nd = null;
		ndItems = null;
	}

	/**
     * addToCartAll
     * @param _parent
     * @author Anthony C. Liberto
     */
    protected void addToCartAll(Navigate _parent) {
		NavData nd = _parent.getData();
		EntityItem[] ndItems = null;
		try {
			ndItems = nd.getAllEntityItems(false,true) ;
			addToCart(ndItems);
		} catch (OutOfRangeException _range) {
			_range.printStackTrace();
			showException(_range, FYI_MESSAGE,OK);
		}
		ndItems = null;
		nd = null;
	}

	/**
     * getCutRelator
     * @return
     * @author Anthony C. Liberto
     */
    private MetaLink getCutRelator() {
		if (getCutGroup() != null) {
			NavData nd = parent.getData();
			if (nd != null) {
				EntityGroup egParent = nd.getEntityGroup();
				if (egParent != null) {
					MetaLink[] links = egParent.getMetaLinkList(getCutGroup());
					return getRelatorType(links);
				}
			}
		}
		return null;
	}

	/**
     * pasteCut
     * @author Anthony C. Liberto
     */
    private void pasteCut() {
		EntityItem[] ndItems = null;
		EntityItem[] dataItems = null;
		NavData nd = parent.getData();
		MetaLink relator = null;
        try {
			ndItems = nd.getSelectedEntityItems(false,true);
			dataItems = data.getSelectedEntityItems(false,true);
		} catch (OutOfRangeException _range) {
			_range.printStackTrace();
			showException(_range,FYI_MESSAGE,OK);
			return;
		}
		relator = getCutRelator();
		if (relator != null) {
			String out = link(ndItems,
				dataItems,
                relator, EANUtility.LINK_MOVE,1);
			if (out != null) {
				eaccess().showLinkDialog((Window)id,getString("msg3011.0"),out);
				clear(getCutGroup(), dataItems);
			}
		}
	}

	/**
     * setEntityList
     * @param _list
     * @author Anthony C. Liberto
     */
    private void setEntityList(EntityList _list) {
		if (cList == null) {
			cList = getCartList(parent.getProfile(),true);
		}

		cList.setEntityList(_list);
	}
	/**
     * getEntityList
     * @return
     * @author Anthony C. Liberto
     */
    private EntityList getEntityList() {
		if (cList == null) {
			cList = getCartList(parent.getProfile(),true);
		}
		return cList.getEntityList();
	}
	/**
     * getCutGroup
     * @return
     * @author Anthony C. Liberto
     */
    private EntityGroup getCutGroup(){
		if (cList == null) {
			cList = getCartList(parent.getProfile(),true);
		}
		return cList.getCutGroup();
	}

	/**
     * setCutGroup
     * @param _eg
     * @author Anthony C. Liberto
     */
    private void setCutGroup(EntityGroup _group) {
		if (cList == null) {
			cList = getCartList(parent.getProfile(),true);
		}
		cList.setCutGroup(_group);
	}
    
    private EntityList createEntityList() {
		if (cList == null) {
			cList = getCartList(eaccess().getActiveProfile(),true);
		}
		return cList.getEntityList();
	}

	private CartList getCartList(Profile _prof, boolean _create) {
		return eaccess().getCartList(_prof,_create);
	}

/*
 5214_B
 */
	/**
     * linkAll
     * @author Anthony C. Liberto
     */
    private void linkAll() {
		EntityItem[] ndItems = null;
		NavData nd = getActiveNavigate().getData();
		try {
			ndItems = nd.getSelectedEntityItems(false,true);
			linkAll(ndItems);
		} catch (OutOfRangeException _range) {
			_range.printStackTrace();
			showException(_range,FYI_MESSAGE,OK);
		}
		ndItems = null;
		nd = null;
	}

	/**
     * linkAll
     * @param _eiParent
     * @author Anthony C. Liberto
     */
    private void linkAll(EntityItem[] _eiParent) {
		OPICMList oList = null;
        if (_eiParent != null) {
			EntityItem[] eiChild = getAllChildren();
			if (eiChild == null) {			//53511
				return;						//53511
			}								//53511
//MN23855544			oList = dBase().linkAll(getLinkType(),_eiParent,eiChild,EANUtility.LINK_DEFAULT,1,true);
			if(!canLinkAll(_eiParent, eiChild)){
				return;
			}
			oList = dBase().linkAll(getLinkType(),_eiParent,eiChild,EANUtility.LINK_DEFAULT,1,true,this);		//MN23855544
			if (oList != null) {
				report(oList,_eiParent,eiChild);
			} else {																							//MN29626005
				eaccess().showFYI(this,"msg3011.1");															//MN29626005
				appendLog("no possible links found");															//MN29626005
				if (_eiParent != null) {																		//MN29626005
					int pp = _eiParent.length;																	//MN29626005
					for (int p=0;p<pp;++p) {																	//MN29626005
						appendLog("    parent: " + p + " of " + pp + " " +  _eiParent[p].getEntityType());		//MN29626005
					}																							//MN29626005
				} else {																						//MN29626005
					appendLog("    parent is null");															//MN29626005
				}																								//MN29626005
				if (eiChild != null) {																			//MN29626005
					int ii = eiChild.length;																	//MN29626005
					for (int i=0;i<ii;++i) {																	//MN29626005
						appendLog("        child " + i + " of " + ii + " " + eiChild[i].getEntityType());		//MN29626005
					}																							//MN29626005
				} else {																						//MN29626005
					appendLog("    children null");																//MN29626005
				}																								//MN29626005
			}																									//MN29626005
		}
	}

	/**
	 * RCQ00213801 - turn off link all capability on the JUI workfolder
	 * check to see if a link is allowed between these parents and childre
	 * @param eiParent
	 * @param eiChild
	 * @return
	 */
	private boolean canLinkAll(EntityItem[] eiParent, EntityItem[] eiChild){
		Hashtable parentTypeTbl = new Hashtable();
		Hashtable childTypeTbl = new Hashtable();
		for (int ii = 0; ii < eiParent.length; ii++) {
			EntityItem eiP = eiParent[ii];
			EntityGroup egP = eiP.getEntityGroup();
			appendLog(" parent["+ii+"] "+eiP.getKey());
			if (egP == null) {
				appendLog("NavCartDialog EntityGroup is null for parent "+eiP.getKey());
				continue;
			}

			if (egP.isRelator() || egP.isAssoc()) {
				eiP = (EntityItem) eiP.getDownLink(0);
				egP = eiP.getEntityGroup();
			}
			if(!parentTypeTbl.containsKey(eiP.getEntityType())){
				parentTypeTbl.put(eiP.getEntityType(),eiP.getEntityGroup());
			}
		}
		for (int ii = 0; ii < eiChild.length; ii++) {
			EntityItem eiC = eiChild[ii];
			EntityGroup egC = eiC.getEntityGroup();
			if (egC == null) {
				appendLog("NavCartDialog Entitygroup is null for eic "+eiC.getKey());
				continue;
			}

			if(!childTypeTbl.containsKey(eiC.getEntityType())){
				childTypeTbl.put(eiC.getEntityType(),eiC.getEntityGroup());
			}
		}
		boolean bLinkChild = false;
		for (Enumeration e = parentTypeTbl.elements(); e.hasMoreElements();){
			EntityGroup egP = (EntityGroup)e.nextElement();
			MetaLinkGroup mlgP = egP.getMetaLinkGroup();

			//link to children
			for (int p = 0; p < mlgP.getMetaLinkCount(MetaLinkGroup.DOWN); p++) {
				MetaLink ml = mlgP.getMetaLink(MetaLinkGroup.DOWN, p);
				String strEntityType = ml.getEntityType();
				String strEntity1Type = ml.getEntity1Type();
				String strEntity2Type = ml.getEntity2Type();

				if (!egP.getEntityType().equals(strEntity1Type)) {
					appendLog("NavCartDialog Parent entity type " + egP.getEntityType() + " not equal metalink " + strEntity1Type);
					appendLog("metalink: " + ml.dump(false));
					continue;
				}

				int cnt=0;
				for (Enumeration ec = childTypeTbl.elements(); ec.hasMoreElements();){
					EntityGroup egC = (EntityGroup)ec.nextElement();

					appendLog(" child["+cnt+"] "+egC.getKey());
					cnt++;
					if (egC.getEntityType().equals(strEntity2Type)) {
						// check to see if this is a valid linkall
						if(!ml.isLinkAllAble()){
							appendLog("NavCartDialog "+strEntityType+" can not linkall "+ml.dump(false));
							UIManager.getLookAndFeel().provideErrorFeedback(null);
							//msg3011.2 = No {0} Link(s) can be created.
							eaccess().showFYI("msg3011.2", new String[]{strEntityType},this);
							childTypeTbl.clear();
							parentTypeTbl.clear();
							return false;
						}
						bLinkChild = true;
					} else { // look down the child entity to get the right match
						EntityItem eiC = egC.getEntityItem(0);
						for (int iz = 0; iz < eiC.getDownLinkCount(); iz++) {
							EntityItem eiCDown = (EntityItem) eiC.getDownLink(iz);
							if (eiCDown.getEntityType().equals(strEntity2Type)) {
								// check to see if this is a valid linkall
								if(!ml.isLinkAllAble()){ 
									appendLog("NavCartDialog "+strEntityType+" can not linkall "+ml.dump(false));
									UIManager.getLookAndFeel().provideErrorFeedback(null);
									//msg3011.2 = No {0} Link(s) can be created.
									eaccess().showFYI("msg3011.2", new String[]{strEntityType},this);									childTypeTbl.clear();
									parentTypeTbl.clear();
									return false;
								}
								bLinkChild = true;
							}
						}
					}
				}
			}
		}

		if (!bLinkChild) {
			Hashtable childTypeTbl2 = new Hashtable();
			for (Enumeration ec = childTypeTbl.elements(); ec.hasMoreElements();){
				EntityGroup egC = (EntityGroup)ec.nextElement();
				if (egC.isRelator() || egC.isAssoc()) {
					EntityItem eic = egC.getEntityItem(0);
					eic = (EntityItem) eic.getDownLink(0);
					egC = eic.getEntityGroup();
				}
				if(!childTypeTbl2.containsKey(egC.getEntityType())){
					childTypeTbl2.put(egC.getEntityType(),egC);
				}
			}
			childTypeTbl.clear();
			for (Enumeration ec = childTypeTbl2.elements(); ec.hasMoreElements();){
				EntityGroup egC = (EntityGroup)ec.nextElement();
				MetaLinkGroup mlgP = egC.getMetaLinkGroup();

				for (int p = 0; p < mlgP.getMetaLinkCount(MetaLinkGroup.DOWN); p++) {
					MetaLink ml = mlgP.getMetaLink(MetaLinkGroup.DOWN, p);
					String strEntityType = ml.getEntityType();
					String strEntity1Type = ml.getEntity1Type();
					String strEntity2Type = ml.getEntity2Type();

					if (!egC.getEntityType().equals(strEntity1Type)) {
						appendLog("NavCartDialog child entity type " + egC.getEntityType() + " not equal " + strEntity1Type);
						appendLog("ml " + ml.dump(false));
						continue;
					}

					for (Enumeration e = parentTypeTbl.elements(); e.hasMoreElements();){
						EntityGroup egP = (EntityGroup)e.nextElement();
						if (egP.getEntityType().equals(strEntity2Type)) {
							// check to see if this is a valid linkall
							if(!ml.isLinkAllAble()){
								appendLog("NavCartDialog "+strEntityType+" can not linkall "+ml.dump(false));
								UIManager.getLookAndFeel().provideErrorFeedback(null);
								//msg3011.2 = No {0} Link(s) can be created.
								eaccess().showFYI("msg3011.2", new String[]{strEntityType},this);
								childTypeTbl2.clear();
								parentTypeTbl.clear();
								return false;
							}
						} else { // look down the child entity to get the right match
							EntityItem eiC = egP.getEntityItem(0);;
							for (int iz = 0; iz < eiC.getDownLinkCount(); iz++) {
								EntityItem eiCDown = (EntityItem) eiC.getDownLink(iz);
								if (eiCDown.getEntityType().equals(strEntity2Type)) {
									// check to see if this is a valid linkall
									if(!ml.isLinkAllAble()){ 
										appendLog("NavCartDialog "+strEntityType+" can not linkall "+ml.dump(false));
										UIManager.getLookAndFeel().provideErrorFeedback(null);
										//msg3011.2 = No {0} Link(s) can be created.
										eaccess().showFYI("msg3011.2", new String[]{strEntityType},this);
										childTypeTbl2.clear();
										parentTypeTbl.clear();
										return false;
									}
								}
							}
						}
					}
				}
			}
			childTypeTbl2.clear();
		}
		childTypeTbl.clear();
		parentTypeTbl.clear();
		return true;
	}

	private void report(OPICMList _oList, EntityItem[] _eiParent, EntityItem[] _eiChild) {
		if (_oList != null) {
			int ii = _oList.size();
			if (ii > 0) {
				StringBuffer sb = new StringBuffer();
				for (int i=0;i<ii;++i) {
					Object o = _oList.getAt(i);
					if (o != null) {
						if (o instanceof ReturnRelatorKey) {
							sb = getRelatorInfo(sb, (ReturnRelatorKey)o, _eiParent, _eiChild);
							sb.append(RETURN);
						}
					}
				}
				eaccess().showLinkDialog((Window)id,getString("msg3011.0"),sb.toString());
			}
		}
	}

	private StringBuffer getRelatorInfo(StringBuffer _sb, ReturnRelatorKey _rrk, EntityItem[] _eiParent, EntityItem[] _eiChild) {
		if (_sb != null && _rrk != null && _eiParent != null && _eiChild != null) {
			EntityItem par = getEntityItem(_rrk.getEntity1Type() + _rrk.getEntity1ID(),_eiParent,true);
			EntityItem kid = getEntityItem(_rrk.getEntity2Type() + _rrk.getEntity2ID(),_eiChild,false);
			if (par != null && kid != null) {
				_sb.append(par.toString());
				_sb.append(" " + getString("l2") + " ");
				_sb.append(kid.toString());
				appendLog("NavCartDialog linked parent "+par.getKey()+" to child "+kid.getKey());
			}
		}
		return _sb;
	}

	private EntityItem getEntityItem(String _key, EntityItem[] _ei,	boolean _down) {
		if (_key != null && _ei != null) {
			int ii = _ei.length;
			for (int i=0;i<ii;++i) {
				if (_ei[i] != null) {
					if (_key.equals(_ei[i].getKey())) {
						return _ei[i];
					} else if (_down) {
						EANEntity ean = _ei[i].getDownLink(0);
						if (ean != null && ean instanceof EntityItem) {
							if (_key.equals(ean.getKey())) {
								return (EntityItem)ean;
							}
						}
					}
				}
			}
		}
		return null;
	}

	private EntityItem[] getAllChildren() {
		EntityList eList = getEntityList();
		if (eList != null) {
			int ii = eList.getEntityGroupCount();
			Vector v = new Vector();
			for (int i=0;i<ii;++i) {
				EntityGroup eg = eList.getEntityGroup(i);
				if (eg != null) {
					int xx = eg.getEntityItemCount();
					for (int x=0;x<xx;++x) {
						EntityItem ei = eg.getEntityItem(x);
						if (ei != null) {
							v.add(ei);
						}
					}
				}
			}
			if (!v.isEmpty()) {
				return (EntityItem[])v.toArray(new EntityItem[v.size()]);
			}
		}
		return null;
	}
/*
 cr_4215
 */
    /**
     * disposeDialog
     *
     * @author Anthony C. Liberto
     */
    public void disposeDialog() {
		if (hasData()) {
			if (eaccess().always("clrWFType",PREF_WF_CLEAR_TYPE,DEFAULT_WF_CLEAR_TYPE)) {
				clearAll(true);
			} else if (eaccess().prompt("clrWFType",PREF_WF_CLEAR_TYPE,DEFAULT_WF_CLEAR_TYPE)) {
				int[] i = {eaccess().getInt("clrWFType.always"),eaccess().getInt("clrWFType.never")};
				int iReply = eaccess().showConfirm(this,YES_NO_SHOW,"msg24016",PREF_WF_CLEAR_TYPE,i);
				if (iReply == 0) {
					clearAll(true);
				}
			}
		}
		eaccess().dispose(getParentDialog());
	}

	/**
     * hasData
     * @return
     * @author Anthony C. Liberto
     */
    private boolean hasData() {
		EntityList eList = getEntityList();
		if (eList != null) {
			return (eList.getEntityGroupCount() != 0);
		}
		return false;
	}
}
