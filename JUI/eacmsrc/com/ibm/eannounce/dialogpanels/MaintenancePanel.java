/**
 * Copyright (c) 2004-2005 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 * for cr_FlagUpdate
 *
 * @version 3.0  2005/03/02
 * @author Anthony C. Liberto
 *
 * $Log: MaintenancePanel.java,v $
 * Revision 1.2  2008/01/30 16:26:53  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:43:24  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:10  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:43  tony
 * This is the initial load of OPICM
 *
 * Revision 1.7  2005/09/08 17:58:59  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.6  2005/09/01 21:58:04  tony
 * MN25073760
 *
 * Revision 1.5  2005/03/24 20:51:04  tony
 * jt_20050324
 * added ability to unretire flags
 *
 * Revision 1.4  2005/03/24 15:44:14  tony
 * adjusted flag maintenance functionality based on
 * demo feedback.
 *
 * Revision 1.3  2005/03/08 20:33:53  tony
 * added updateFlagCodes logic
 *
 * Revision 1.2  2005/03/03 22:19:04  tony
 * cr_FlagUpdate
 * improved functionality
 *
 * Revision 1.1  2005/03/03 21:47:38  tony
 * cr_FlagUpdate
 * Added Flag Modification Capability.
 *
 */
package com.ibm.eannounce.dialogpanels;
import com.elogin.*;
import com.ibm.eannounce.eforms.table.*;
import com.ibm.eannounce.eobjects.*;
import COM.ibm.eannounce.objects.*;
import java.awt.*;
import java.awt.event.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class MaintenancePanel extends AccessibleDialogPanel {
	private static final long serialVersionUID = 1L;
	private final String ADD = "addMF";
	private final String RETIRE = "rtrSF";
	private final String COMMIT = "cmtF";
	private final String CLOSE = "clse";

    private ELabel lblFilter = new ELabel(eaccess().getImageIcon("fltr.gif"));
	private MaintenanceTable maintTable = new MaintenanceTable() {
		    private static final long serialVersionUID = 1L;
		    public void setFilter(boolean _filter) {
				lblFilter.setEnabled(_filter);
				return;
			}

			public void toggleCommit(boolean _b) {
				menubar.setEnabled(COMMIT, _b);
				return;
			}

			public EntityList getEntityList() {
				return getParentEntityList();
			}
	};
    private EScrollPane jsp = new EScrollPane(maintTable);
	private EntityList parList = null;
	private MetaFlagMaintList mfmList = null;

    /**
     * MaintenancePanel
     *
     * @author Anthony C. Liberto
     */
    public MaintenancePanel() {
        super(new BorderLayout());
        init();
        return;
    }

    /**
     * init
     * @author Anthony C. Liberto
     */
    public void init() {
		setModalCursor(true);
		add("North", lblFilter);
		add("Center",jsp);
		setResizable(true);
        return;
    }

    /**
     * actionPerformed
     *
     * @author Anthony C. Liberto
     * @param _action
     */
    public void actionPerformed(String _action) {
        appendLog("maintenancePanel.actionPerformed(" + _action + ")");
        setModalBusy(true);
        if (_action.equalsIgnoreCase(ADD)) {
			maintTable.addRow();
		} else if (_action.equalsIgnoreCase(RETIRE)) {
			maintTable.remove();
		} else if (_action.equalsIgnoreCase("unRetire")) {	//jt_20050324
			maintTable.unexpireFlags();						//jt_20050324
		} else if (_action.equalsIgnoreCase(COMMIT)) {
			maintTable.commit();
		} else if (_action.equalsIgnoreCase("clse")) {
            disposeDialog();
		} else if (_action.equalsIgnoreCase("f/r")) {
			maintTable.showFind(id);
		} else if (_action.equalsIgnoreCase("fltr")) {
			maintTable.showFilter(id);
		} else if (_action.equalsIgnoreCase("selA")) {
			maintTable.selectAll();
		} else if (_action.equalsIgnoreCase("iSel")) {
			maintTable.invertSelection();
		} else if (_action.equalsIgnoreCase("rstA")) {		//dwb_20050324
			maintTable.rollback();							//dwb_20050324
		} else if (_action.equalsIgnoreCase("rstS")) {		//dwb_20050324
			maintTable.rollbackSingle();					//dwb_20050324
		}													//dwb_20050324
        setModalBusy(false);
        return;
    }

    /**
     * windowClosing
     *
     * @author Anthony C. Liberto
     * @param e
     */
    public void windowClosing(WindowEvent e) {
        disposeDialog();
        return;
    }

    /**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
        if (jsp != null) {
            jsp.dereference();
            jsp = null;
        }
        parList = null;
        return;
    }

    /**
     * getTitle
     * @author Anthony C. Liberto
     * @return String
     */
    public String getTitle() {
        String strDefOut = getString("maint.panel");					//demo_update
        if (mfmList != null) {											//demo_update
            System.out.println("longDesc: " + mfmList.getLongDescription());
			return mfmList.getLongDescription() + " " + strDefOut;		//demo_update
		}																//demo_update
        return getString("name") + " " + strDefOut;						//demo_update
//demo_update        return getString("maint.panel");
    }

    /**
     * getIconName
     * @author Anthony C. Liberto
     * @return String
     */
    public String getIconName() {
        return "maint.gif";
    }

    /**
     * setTitle
     * @author Anthony C. Liberto
     * @param _title
     */
    public void setTitle(String _title) {}

    /**
     * createMenu
     * @author Anthony C. Liberto
     */
    protected void createMenu() {
		if (menubar != null) {
			menubar.removeAll();
			createFileMenu();
			createTableMenu();
		}
		return;
	}

    private void createTableMenu() {
		String strKey = getString("tbl");
		menubar.addMenu(strKey,"f/r",this,KeyEvent.VK_F, Event.CTRL_MASK,true);
		menubar.addMenu(strKey,"fltr",this,KeyEvent.VK_F8,0,true);
		menubar.addSeparator(strKey);
		menubar.addMenu(strKey,"selA",this,KeyEvent.VK_A,Event.CTRL_MASK,true);
		menubar.addMenu(strKey,"iSel",this,KeyEvent.VK_I,Event.CTRL_MASK,true);
		menubar.addSeparator(strKey);
		menubar.addMenu(strKey,"rstA",this,KeyEvent.VK_Z, Event.CTRL_MASK + Event.SHIFT_MASK,true);	//dwb_20050324
		menubar.addMenu(strKey,"rstS",this,KeyEvent.VK_Z, Event.CTRL_MASK,true);					//dwb_20050324
		menubar.addSeparator(strKey);
		menubar.addMenu(strKey,ADD,this,KeyEvent.VK_N,Event.CTRL_MASK,true);
		menubar.addMenu(strKey,RETIRE,this,KeyEvent.VK_R,Event.CTRL_MASK,true);
		menubar.addMenu(strKey,"unRetire",this,KeyEvent.VK_U,Event.CTRL_MASK,true);					//jt_20050324
		menubar.addMenu(strKey,COMMIT,this,KeyEvent.VK_S,Event.CTRL_MASK,false);
		menubar.setMenuMnemonic(strKey, getChar("tbl-s"));
		return;
	}

    private void createFileMenu() {
        String strKey = getString("file");
        menubar.addMenu(strKey, CLOSE, this, KeyEvent.VK_W, Event.CTRL_MASK, true);
        menubar.setMenuMnemonic(strKey, getChar("file-s"));
        return;
    }

    /**
     * removeMenu
     * @author Anthony C. Liberto
     */
    protected void removeMenu() {
		removeFileMenu();
		removeTableMenu();
		return;
	}

    private void removeTableMenu() {
		menubar.removeMenuItem("f/r",this);
		menubar.removeMenuItem("fnd",this);
		menubar.removeMenuItem("selA",this);
		menubar.removeMenuItem("iSel",this);
		menubar.removeMenuItem("rstA",this);		//dwb_20050324
		menubar.removeMenuItem("rstS",this);		//dwb_20050324
		menubar.removeMenuItem(ADD,this);
		menubar.removeMenuItem(RETIRE,this);
		menubar.removeMenuItem("unRetire",this);	//jt_20050324
		menubar.removeMenuItem(COMMIT,this);
		return;
	}

	private void removeFileMenu() {
		menubar.removeMenuItem(CLOSE,this);
		return;
	}

    /**
     * refresh
     *
     * @author Anthony C. Liberto
     * @param _list
     */
    public void refresh(MetaFlagMaintList _list) {
		mfmList = _list;
		if (maintTable != null) {
			maintTable.refreshTable(_list);
		}
		lblFilter.setEnabled(false);
		return;
	}

	/**
	 * getSearchableObject
	 * get the component to search
	 *
	 * @author Anthony C. Liberto
	 * @return
	 */
	public Object getSearchableObject() {
		return maintTable;
	}

	/**
     * okToClose
     * @author Anthony C. Liberto
     * @return boolean
     */
    public boolean okToClose() {
		if (maintTable != null) {
			if (maintTable.isEditing()) {			//MN25073760
				maintTable.saveCurrentEdit();		//MN25073760
			}										//MN25073760
			if (maintTable.hasChanges()) {
				int action = showConfirm(YES_NO_CANCEL,"updtMsg2",true);
				if (action == 0) {
					return maintTable.commit();
				} else if (action == 1) {
					return true;
				} else if (action == 2) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * getEntityList
	 *
	 * @author Anthony C. Liberto
	 * @return EntityList
	 */
	public EntityList getParentEntityList() {
		return parList;
	}

	/**
	 * setParentEntityList
	 *
	 * @author Anthony C. Liberto
	 * @param _par
	 */
	public void setParentEntityList(EntityList _par) {
		parList = _par;
		return;
	}
}
