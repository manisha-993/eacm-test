//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
package com.ibm.eacm.mtrx;


import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import COM.ibm.eannounce.objects.*;

import COM.ibm.opicmpdh.middleware.Profile;

import com.ibm.eacm.actions.*;
import com.ibm.eacm.mw.RMIMgr;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.preference.PrefMgr;
import com.ibm.eacm.table.CrossTable;

import com.ibm.eacm.tabs.ActionTreeTabPanel;
import com.ibm.eacm.toolbar.*;


/**
* base class for tab for MatrixAction
* column for each matrixgroup in MatrixTable
* cross table for selected column where number of relators can be specified in the cell (no RELATTR)
* SG	Action/Attribute	MTRMODEL	TYPE	EntityType	MODEL
* SG	Action/Attribute	MTRMODEL	ENTITYTYPE	Link	MODEL
* SG	Action/Attribute	MTRMODEL	TYPE	NavAction	NAVMTRMODEL
*
* @author Wendy Stimpson
*/
//$Log: MatrixActionBase.java,v $
//Revision 1.3  2013/05/01 18:35:13  wendy
//perf updates for large amt of data
//
//Revision 1.2  2013/02/07 13:37:38  wendy
//log close tab
//
//Revision 1.1  2012/09/27 19:39:22  wendy
//Initial code
//
public abstract class MatrixActionBase extends ActionTreeTabPanel
{
	private static final long serialVersionUID = 1L;
	protected static final Logger logger;
	static {
		logger = Logger.getLogger(MTRX_PKG_NAME);
		logger.setLevel(PrefMgr.getLoggerLevel(MTRX_PKG_NAME, Level.INFO));
	}
	private MatrixList mList = null;
	private EACMToolbar cTool = null;
	private MtrxPickFrame pickFrame = null;
	private WindowAdapter pickFrameListener = null;
	
	/* (non-Javadoc)
	 * @see java.awt.Component#toString()
	 */
	public String toString(){
		if(mList!=null){
			return "Matrix: "+mList.getParentActionItem().getActionItemKey();
		}else{
			return super.toString();
		}
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.tabs.ActionTabPanel#getTabMenuTitleKey()
	 */
	public String getTabMenuTitleKey() { return "matrix.title";}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.tabs.TabPanel#getTabIconKey()
	 */
	protected String getTabIconKey() {
		return "matrix.icon";
	}
	
	/**
	 * edit is now complete, items were updated.. refresh the table
	 */
	public abstract void editComplete();
	
	/**
	 * provide access to the MatrixList
	 * @return
	 */
	public MatrixList getMatrixList() { return mList;}

	/* (non-Javadoc)
	 * make sure any pickframe is closed
	 * @see com.ibm.eacm.tabs.DataActionPanel#close()
	 */
	public boolean canClose() {
		boolean ok = super.canClose();
		if(ok){
			if(pickFrame !=null){
				pickFrame.removeWindowListener(pickFrameListener);
				pickFrame.setVisible(false);
				pickFrame.dispose();
				pickFrame.dereference();

				pickFrameListener = null;
				pickFrame = null;
			}
		}
		return ok;
	}

    /**
     * does this tab contain this data
     * @param prof
     * @param _ei
     * @param _eai
     * @return
     */
    public boolean tabExistsWithAll(Profile prof, EntityItem[] _ei, EANActionItem _eai){
    	if(Utils.isPast(getProfile())){
    		return false;
    	}

    	if(prof.getEnterprise().equals(getProfile().getEnterprise()) &&
    			prof.getOPWGID()== getProfile().getOPWGID()){
    	    return mList.equivalent(_ei, _eai);
    	}
    	return false;
    }
    /**
     * does this tab contain some of this data
     * @param prof
     * @param _ei
     * @param _eai
     * @return
     */
    public boolean tabExistsWithSome(Profile prof, EntityItem[] _ei, EANActionItem _eai){
    	if(Utils.isPast(getProfile())){
    		return false;
    	}
    	if(prof.getEnterprise().equals(getProfile().getEnterprise()) &&
    			prof.getOPWGID()== getProfile().getOPWGID()){
    	    return mList.subset(_ei, _eai);
    	}
    	return false;
    }
	/**
	 * provide access to the crosstable toolbar
	 * @return
	 */
	protected EACMToolbar getCrossToolbar() { return cTool;}

	/**
	 * called by derived class, cant do init in constructor because RelAttrMatrixActionTab must instantiate tabbedpane
	 * @param _mList
	 * @param key
	 */
	protected MatrixActionBase(MatrixList _mList, String key) {
		super(key);
		mList = _mList;
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.tabs.DataActionPanel#init()
	 */
	protected void init(){
		// create crosstable actions, they are used in both matrixactiontab and relattrmatrixactiontab
		addAction(new DeleteColAction(null,this)); //MTRX_DELETECOL
		addAction(new DeleteRowAction(null,this)); //MTRX_DELETEROW

		addAction(new AdjustColAction(null,this)); //MTRX_ADJUSTROW
		addAction(new AdjustRowAction(null,this)); //MTRX_ADJUSTCOL

		addAction(new AddColAction(null,this)); //MTRX_ADDCOL

		addAction(new PivotAction(null)); //MTRX_PIVOT
		addAction(new CancelAction(null)); //MTRX_CANCEL
		addAction(new SaveAction(this)); //MTRX_SAVE
		addAction(new ResetSelectedAction(null)); //MTRX_RESETSEL

		createMatrixActions();

		cTool =new EACMToolbar(ToolbarLayout.getToolbarLayout(getDefaultToolbarLayout()), getActionTbl());

		initMatrix();
		loadGroupActions();

		createMenus();
		createPopupMenu();

		refreshActionSet();
	}

	/**
	 * derived classes must implement this
	 */
	protected abstract void initMatrix();
	/**
	 * createMenus
	 */
	protected abstract void createMenus();

	/**
	 * create all of the actions, they are shared between toolbars and menu
	 */
	protected abstract void createMatrixActions();

   	/**
   	 * set current crosstable in its actions
   	 * @param table
   	 */
   	protected void updateCrossTableActions(CrossTable table) {
   		//MTRX_ADDCOL
		((AddColAction)getAction(MTRX_ADDCOL)).setTable(table);

		//MTRX_ADJUSTCOL
		((AdjustColAction)getAction(MTRX_ADJUSTCOL)).setTable(table);
		//MTRX_ADJUSTROW
		((AdjustRowAction)getAction(MTRX_ADJUSTROW)).setTable(table);
		//MTRX_DELETEROW
		((DeleteRowAction)getAction(MTRX_DELETEROW)).setTable(table);
		//MTRX_DELETECOL
		((DeleteColAction)getAction(MTRX_DELETECOL)).setTable(table);
		//MTRX_RESETSEL
		((ResetSelectedAction)getAction(MTRX_RESETSEL)).setTable(table);

   	}

	/**
	 * enable crosstable actions based on selection, actions query the table
	 * called from refreshActions()
	 */
	protected void enableCrossTableActions() {
		super.enableTableActions();
		//MTRX_ADJUSTCOL
		((AdjustColAction)getAction(MTRX_ADJUSTCOL)).setEnabled(true);
		//MTRX_ADJUSTROW
		((AdjustRowAction)getAction(MTRX_ADJUSTROW)).setEnabled(true);
		//MTRX_DELETEROW
		((DeleteRowAction)getAction(MTRX_DELETEROW)).setEnabled(true);
		//MTRX_DELETECOL
		((DeleteColAction)getAction(MTRX_DELETECOL)).setEnabled(true);
		//MTRX_RESETSEL
		((ResetSelectedAction)getAction(MTRX_RESETSEL)).setEnabled(true);

	}
	private void loadGroupActions(){
		EntityGroup eg = mList.getParentEntityGroup();//getEntityList().getParentEntityGroup();
		RowSelectableTable actTbl = eg.getActionGroupTable();
		getActionTree().load(Utils.getExecutableActionItems(eg, actTbl), Utils.getActionTitle(eg, actTbl));
	}

	/**
	 * refreshActions
	 *
	 */
	private void refreshActionSet() {
		EANActionItem[] eai = getActionTree().getActionItemArray(ACTION_PURPOSE_EDIT);

		EANActionSet actionSet = ((EANActionSet)getAction(MTRXEDIT_ACTIONSET));
		if(actionSet!=null){
			actionSet.updateEANActions(eai);
		}
		eai = getActionTree().getActionItemArray(ACTION_PURPOSE_WHERE_USED);
		actionSet = ((EANActionSet)getAction(MTRXWU_ACTIONSET));
		if(actionSet!=null){
			actionSet.updateEANActions(eai);
		}
	}

	/**
	 * getProfile
	 */
	public Profile getProfile() {
		return mList.getProfile();
	}

	/**
	 * release memory
	 */
	public void dereference() {
	    ComponentListener[] l = getComponentListeners();
	    for (int i = 0; i < l.length; ++i) {
	    	removeComponentListener(l[i]);
	    }

	    pickFrame = null;
	    pickFrameListener = null;

		mList.dereference();
		mList = null;

		if (cTool != null) {
			cTool.dereference();
			cTool = null;
		}

		super.dereference();
	}

	/**
	 * get UI preference Key
	 * @return
	 */
	public String getKey() {
		return mList.getParentActionItem().getEntityType();
	}

	/**
	 * called by AddColAction worker on bg thread
	 * @param ctbl
	 * @return
	 */
	protected EANActionItem[] getActionItemsAsArray(CrossTable ctbl) {
		EANActionItem[] out = ctbl.getActionItemsAsArray(0);
		if (out == null || out.length ==0) {
			out = null;
		}
		return out;
	}

	/**
	 * called from the MtrxPickFrame add column action
	 * @param crssTable
	 * @param _ei
	 */
	protected void addColumn(CrossTable crssTable,EntityItem[] _ei) {
		crssTable.addColumn(_ei);
	}

	/* (non-Javadoc)
	 * this is called on the bg thread from the AddColAction worker
	 * @see com.ibm.eacm.mtrx.MatrixActionBase#getPicklistNavigate(com.ibm.eacm.table.CrossTable2)
	 */
	protected EntityList getPicklistNavigate(CrossTable ctbl) {
		EntityList eList = null;
		MatrixGroup mg = ctbl.getMatrixGroup();
		RowSelectableTable mlrst = mg.getMatrixList().getTable();
		int row = mlrst.getRowIndex(mg.getKey());
		int col = mlrst.getColumnIndex(mg.getKey());

		if (row >= 0) {
			eList = generatePicklist(mlrst, row);
		}else if (col >= 0) {
			eList = generatePicklist(mlrst, col);
		}

		return eList;
	}

    /**
     * get the entitylist to use as a picklist
     * @param _rst
     * @param _row
     * @return
     */
    private EntityList generatePicklist(RowSelectableTable _rst, int _row) {
    	EntityList list = null;
        try {
        	list = _rst.generatePickList(_row, null, ro(), getProfile(), true);
        } catch (Exception _ex) {
        	if(RMIMgr.shouldTryReconnect(_ex) && // try to reconnect
    				RMIMgr.getRmiMgr().reconnectMain()){
                try {
                	list = _rst.generatePickList(_row, null, ro(), getProfile(), true);
                } catch (Exception _x) {
            		com.ibm.eacm.ui.UI.showException(this,_x, "mw.err-title");
                }
            } else {
        		com.ibm.eacm.ui.UI.showException(this,_ex, "mw.err-title");
            }
        }

        return list;
    }

	/**
	 * showPicklist for this crosstable
	 * called by AddColAction
	 * @param tbl
	 * @param _list
	 */
	public void showPicklist(CrossTable tbl, EntityList _list) {
		pickFrame = new MtrxPickFrame(this, _list, tbl);
		pickFrameListener = new WindowAdapter(){
			public void windowClosed(WindowEvent e) {
				pickFrame.removeWindowListener(this);
				pickFrame = null;
			}
			public void windowClosing(WindowEvent e) {
				pickFrame.removeWindowListener(this);
				pickFrame = null;
			}
		};
		pickFrame.addWindowListener(pickFrameListener);
		pickFrame.setVisible(true);
	}
}
