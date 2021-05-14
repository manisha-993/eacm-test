//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.edit;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.event.ListSelectionEvent;

import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.transactions.NLSItem;

import com.ibm.eacm.EACM;
import com.ibm.eacm.actions.CopyAction;
import com.ibm.eacm.actions.CutAction;
import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.actions.EANActionSet;
import com.ibm.eacm.actions.FindNextAction;
import com.ibm.eacm.actions.FindRepAction;
import com.ibm.eacm.actions.PasteAction;
import com.ibm.eacm.actions.ResetDateAction;

import com.ibm.eacm.mtrx.MatrixActionBase;
import com.ibm.eacm.mw.RMIMgr;
import com.ibm.eacm.nav.Navigate;
import com.ibm.eacm.objects.Findable;
import com.ibm.eacm.objects.ODSImport;
import com.ibm.eacm.objects.Routines;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.objects.XLSImport;
import com.ibm.eacm.preference.PrefMgr;
import com.ibm.eacm.table.BaseTable;
import com.ibm.eacm.table.RSTTable;
import com.ibm.eacm.tabs.ActionTreeTabPanel;
import com.ibm.eacm.toolbar.ComboItem;
import com.ibm.eacm.toolbar.DefaultToolbarLayout;
import com.ibm.eacm.tree.NLSTreeScroll;
import com.ibm.eacm.tree.NLSNode;
import com.ibm.eacm.ui.UI;
import com.ibm.eacm.wused.WUsedActionTab;

import com.ibm.eacm.edit.Importable;


/**
 * this is the panel that is added as a tab, it has the actions
 * it manages the editors
 * @author Wendy Stimpson
 */
//$Log: EditController.java,v $
//Revision 1.25  2015/01/05 19:15:34  stimpsow
//use Theme for background colors
//
//Revision 1.24  2014/01/24 18:46:36  wendy
//show x of y in record toggle label
//
//Revision 1.23  2014/01/22 20:39:36  wendy
//RCQ 288700 Apache OpenDocument support
//
//Revision 1.22  2013/11/15 17:39:30  wendy
//dont refreshmodel after adding row - user wants rows at bottom
//
//Revision 1.21  2013/11/13 00:11:50  wendy
//override F10 menubar shortcut for fillcopyentity action
//
//Revision 1.20  2013/11/07 18:09:40  wendy
//Add FillCopyEntity action
//
//Revision 1.19  2013/09/23 21:26:45  wendy
//force nav refresh if flag meta was added
//
//Revision 1.18  2013/09/09 21:38:19  wendy
//readonly profile is not able to edit
//
//Revision 1.17  2013/08/21 00:23:56  wendy
//prevent null ptr if deref ran
//
//Revision 1.16  2013/08/19 15:14:02  wendy
//control display of BRE stacktrace with logging level
//
//Revision 1.15  2013/07/31 17:01:37  wendy
//xsl import - advance the editor counter, only set one value for Unique flag
//
//Revision 1.14  2013/07/31 00:47:11  wendy
//correct UAT refresh error
//
//Revision 1.13  2013/07/29 19:38:34  wendy
//xslimport match flag on desc or flagcode
//
//Revision 1.12  2013/07/26 17:29:21  wendy
//support duplication of multiple rows
//
//Revision 1.11  2013/07/23 17:58:19  wendy
//Add importxls logging
//
//Revision 1.10  2013/05/16 22:05:02  wendy
//duplicate entity and relator after edit
//
//Revision 1.9  2013/05/07 18:25:26  wendy
//another perf update for large amt of data
//
//Revision 1.8  2013/05/01 18:35:14  wendy
//perf updates for large amt of data
//
//Revision 1.7  2013/03/11 14:34:25  wendy
//Check for valid server time
//
//Revision 1.6  2013/02/15 11:54:42  wendy
//refresh after create a root item
//
//Revision 1.5  2013/02/11 22:45:53  wendy
//Only check existance of other editactions
//
//Revision 1.4  2013/02/07 13:37:39  wendy
//log close tab
//
//Revision 1.3  2013/02/05 18:23:21  wendy
//throw/handle exception if ro is null
//
//Revision 1.2  2012/11/09 20:46:27  wendy
//check target in tabExistsWithAll()
//
//Revision 1.1  2012/09/27 19:39:19  wendy
//Initial code
//
public class EditController extends ActionTreeTabPanel implements Importable
{
	private static final long serialVersionUID = 1L;
	protected static final Logger logger;
	static {
		logger = Logger.getLogger(EDIT_PKG_NAME);
		logger.setLevel(PrefMgr.getLoggerLevel(EDIT_PKG_NAME, Level.INFO));
	}

	//these actions need the current editor
	private static final String EDITOR_ACTIONS[] = {
		GRIDEDITOR_ACTION,VERTEDITOR_ACTION,FORMEDITOR_ACTION,
		FREEZE_ACTION,LOCK_ACTION,UNLOCK_ACTION,PREVEDIT_ACTION,NEXTEDIT_ACTION,IMPORTODS_ACTION,
		CREATE_ACTION,IMPORTXLS_ACTION,DUPLICATE_ACTION,REFRESHFORM_ACTION,FLAGMAINT_ACTION,
		REMOVENEW_ACTION,RESETALLATTR_ACTION,HIDECOL_ACTION,MOVECOL_LEFT_ACTION,MOVECOL_RIGHT_ACTION,
		FILLCOPY_ACTION,FILLCOPYENTITY_ACTION,FILLAPPEND_ACTION,FILLPASTE_ACTION
	};

	// these actions are toggled when user moves from one record to another in the recordtoggle ctrl
	private static final String TOGGLED_ACTIONS[] = {
		LOCK_ACTION,UNLOCK_ACTION,SAVERECORD_ACTION,SAVEALL_ACTION,RESETONEATTR_ACTION,
		RESETRECORD_ACTION,RESETALLATTR_ACTION,REMOVENEW_ACTION,DEACTIVATEATTR_ACTION,
		FLAGMAINT_ACTION,SPELLCHK_ACTION,SAVEDEFAULT_ACTION, CANCELDEFAULT_ACTION,FILLPASTE_ACTION
	};

	// actions that rely on row or column selections
	private static final String[] ROWCOL_SELECTION_ACTIONS = {
		FREEZE_ACTION,LOCK_ACTION,UNLOCK_ACTION,SAVERECORD_ACTION,RESETONEATTR_ACTION,RESETALLATTR_ACTION,
		RESETRECORD_ACTION,REMOVENEW_ACTION,DUPLICATE_ACTION,DEACTIVATEATTR_ACTION,
		FLAGMAINT_ACTION,SPELLCHK_ACTION,SAVEDEFAULT_ACTION,CANCELDEFAULT_ACTION,SAVEALL_ACTION,
		HIDECOL_ACTION,MOVECOL_LEFT_ACTION,MOVECOL_RIGHT_ACTION,ENTITYDATA_ACTION,
		COPY_ACTION,PASTE_ACTION,CUT_ACTION,FILLCOPY_ACTION,FILLCOPYENTITY_ACTION,FILLAPPEND_ACTION,FILLPASTE_ACTION
	};

	protected static final int GRID_EDITOR = 0;
	protected static final int VERT_EDITOR = 1;
	protected static final int FORM_EDITOR = 2;
	private static final int MAX_EDITOR = 3;

	private EditorPanel[] edit = new EditorPanel[MAX_EDITOR];
	private int currentEditorType = -1;
	private RecordToggle recordToggle = null;
	private EntityList entityList = null;
	private RowSelectableTable egRstTable = null; // table for the entitygroup
	private EntityGroup editEntityGroup = null; // entitygroup under edit

	private EANActionItem actionItem = null;
	private int navOPWGID = -1;

	private ParentComboBox parSelCombo = null;
	private NLSTreeScroll nlsTree = null;
	private Navigate nav = null;
	private EntityItem sourceItemArray[]; // do not deref these items, they are from the caller
	private Vector<String> changedKeysVct; // keys of items that are committed

	/* (non-Javadoc)
	 * @see java.awt.Component#toString()
	 */
	public String toString(){
		if(entityList!=null){
			return "Edit: "+entityList.getParentActionItem().getActionItemKey();
		}else{
			return super.toString();
		}
	}
	
	/**
	 * attempt to improve perf by moving edit updates into the source items instead of going back to the pdh
	 * for the updates
	 * @param eia
	 */
	public void setSourceEntityItems(EntityItem eia[]){
		sourceItemArray = eia;
	}
	/**
	 * editController
	 * @param eList for edit or create action
	 * @param nav2 - could be from nav, wu or mtrx
	 */
	public EditController(EntityList eList, Navigate nav2) {
		super(nav2!=null?nav2.getKey():null);

		long t1 = System.currentTimeMillis();
		Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"editController"+getUID()+".construct():00 ");
		this.nav = nav2;
		entityList = eList;

		// do this here so the maint action can be loaded into it
		addAction(new FlagMaintAction(this));

		init();

		if(isAllNLS()){
			nlsTree = new NLSTreeScroll(this);
		}

		Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"05 at "+Utils.getDuration(t1));
		if (nav != null) {
			nav.addEditCtrl(this);
			navOPWGID =nav.getOPWGID();
		}

		createActions();
		createMenu();

		recordToggle = new RecordToggle(egRstTable, getAction(PREVEDIT_ACTION),getAction(NEXTEDIT_ACTION));

		if (isFormCapable()) {
			setEditor(FORM_EDITOR,null);
			Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"after create "+Utils.getDuration(t1));
		} else {
			if (egRstTable.getRowCount() == 1) {
				setEditor(VERT_EDITOR,null);
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"after createvert "+Utils.getDuration(t1));
			} else {
				setEditor(GRID_EDITOR,null);
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"after creategrid "+Utils.getDuration(t1));
			}
		}

		getSplitPane().setRightComponent(getCurrentEditor());
		getSplitPane().setLeftComponent(getActionTree());

		refreshMenu();

		enableActionsAndRestore(); // make sure all actions are in proper state

		Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"construct ended "+Utils.getDuration(t1));
	}
	private void init() {
		setSessionTagText(entityList.getTagDisplay());
		actionItem = entityList.getParentActionItem();
		setTableAndActions();
		//this is used when edit can create
		if (egRstTable.canCreate()) {
			parSelCombo = new ParentComboBox(this,entityList.getParentEntityGroup());
			add(parSelCombo,BorderLayout.SOUTH);
		}
	}

	/**
	 * get the EntityGroup and actions
	 */
	private void setTableAndActions() {
		if (entityList.isParentDisplayable()) {							//VEEdit
			EntityGroup teg = entityList.getParentEntityGroup();			//VEEdit
			loadActions(teg);
		} else {
			for (int i = 0; i < entityList.getEntityGroupCount(); ++i) {
				EntityGroup teg = entityList.getEntityGroup(i);
				if (teg.isDisplayable()) {
					loadActions(teg);
					break;
				}
			}
		}
	}

	/**
	 * setup this edit session for this entitygroup
	 * @param teg
	 */
	private void loadActions(EntityGroup teg){
		egRstTable = teg.getEntityGroupTable();
		editEntityGroup = teg;

		RowSelectableTable actionrst = editEntityGroup.getActionGroupTable();
		EANActionItem[] ean = Utils.getExecutableActionItems(editEntityGroup, actionrst);
		retrieveMetaMaintAction(ean);
		getActionTree().load(ean, Utils.getActionTitle(editEntityGroup, actionrst));
	}

	/* (non-Javadoc)
	 * needed to fix header bug
	 *
	 * @see com.ibm.eacm.tabs.DataActionPanel#getJTable()
	 */
	protected BaseTable getJTable() {
		if(getCurrentEditor()!=null){
			return getCurrentEditor().getJTable();
		}
		return null;
	}

    /* (non-Javadoc)
     * @see com.ibm.eacm.tabs.ActionTreeTabPanel#disableActionsAndWait()
     */
    public void disableActionsAndWait(){
		for (int i = 0; i < MAX_EDITOR; ++i) {
			if (edit[i] != null) {
				edit[i].setEnabled(false);
			}
		}
		if(nlsTree !=null){
			nlsTree.setEnabled(false);
		}
    	super.disableActionsAndWait();
    }
    /* (non-Javadoc)
     * @see com.ibm.eacm.tabs.ActionTreeTabPanel#enableActionsAndRestore()
     */
    public void enableActionsAndRestore(){
    	super.enableActionsAndRestore();
    	if(isWaiting()){ // all workers have not completed
    		return;
    	}
    	for (int i = 0; i < MAX_EDITOR; ++i) {
    		if (edit[i] != null) {
    			edit[i].setEnabled(true);
    		}
    	}
    	if(nlsTree !=null){
    		nlsTree.setEnabled(true);
    	}
    	if(this.getCurrentEditor()!=null){
    		this.getCurrentEditor().requestFocusInWindow(); // put focus back in the table - editor will do it
    	}
    }
	/**
	 * does this tab contain this data
	 * @param prof
	 * @param ei
	 * @param eai
	 * @return
	 */
	public boolean tabExistsWithAll(Profile prof, EntityItem[] ei, EANActionItem eai){
		boolean exists=false;
		if(prof.getEnterprise().equals(getProfile().getEnterprise()) &&
				prof.getOPWGID()== getProfile().getOPWGID()){
			exists= entityList.equivalent(ei, eai);
			if(!exists){
				// equivalent check misses edit on same items if one edit is a form and the other is not
				if (eai instanceof EditActionItem && (getEntityList().getParentActionItem() instanceof EditActionItem)) {
					EditActionItem myAction = (EditActionItem)getEntityList().getParentActionItem();
					EditActionItem otherAction = (EditActionItem)eai;
					// look for different targets
					if(myAction.isEditRelatorOnly()!=otherAction.isEditRelatorOnly()){
						return exists;
					}
					if(myAction.isEntityOnly()!=otherAction.isEntityOnly()){
						return exists;
					}
					String myTarget = myAction.getTargetEntity();
					String otherTarget = otherAction.getTargetEntity();
					if(myTarget !=null && otherTarget!=null){
						// diff target
						if(!myTarget.equals(otherTarget)){
							return exists;
						}
					}
					
		            boolean bdown = false;
		            EntityGroup eg = entityList.getEntityGroup(ei[0].getEntityType());

		            // If we cannot find an entitygroup directly.. we need
		            // to look downward from what was passed
		            if (eg == null) {
		                EntityItem eic = null;
		                if (ei[0].getDownLink(0) == null) {
		                    return exists;
		                }
		                eic = (EntityItem) ei[0].getDownLink(0);
		                eg = entityList.getEntityGroup(eic.getEntityType());
		                bdown = true;
		                if (eg == null) {
		                    return exists;
		                }
		            }

		            // If we made it this far.. we found an entitygroup that has
		            // either a direct reference. or a dowlnink reference
		            for (int y = 0; y < ei.length; y++) {
		                if (eg.getEntityItem( (bdown ? ei[y].getDownLink(0).getKey() : ei[y].getKey())) == null) {
		                    return exists;
		                }
		            }
		        	//msg7002.0 = Entity Item are already under Edit another tab for {0} Action.
		    		com.ibm.eacm.ui.UI.showWarning(null,Utils.getResource("msg7002.0",
		    				entityList.getParentActionItem().getLongDescription()));
		            exists= true;
				}
			}
		}
		return exists;
	}
	/**
	 * does this tab contain some of this data
	 * @param prof
	 * @param ei
	 * @param eai
	 * @return
	 */
	public boolean tabExistsWithSome(Profile prof, EntityItem[] ei, EANActionItem eai){
		if(prof.getEnterprise().equals(getProfile().getEnterprise()) &&
				prof.getOPWGID()== getProfile().getOPWGID()){
			return entityList.subset(ei, eai);
		}
		return false;
	}


	/**
	 * get entitygroup rst under edit
	 * @return
	 */
	public RowSelectableTable getEgRstTable() {
		return egRstTable;
	}

	/**
	 * getTableTitle
	 */
	public String getTableTitle() {
		return egRstTable.toString();
	}

	/**
	 * getCurrentEditor
	 * @return
	 */
	protected EditorPanel getCurrentEditor() {
		if (currentEditorType == -1) {
			return null;
		} else {
			return edit[currentEditorType];
		}
	}

	/**
	 * user changed background color, update all components
	 */
	public void updateComponentsUI(){
		super.updateComponentsUI();
		for(int i=0;i<edit.length; i++){
			if(edit[i]!=null){
				edit[i].updateComponentsUI();
			}
		}
	}
	/**
	 * if this is editing a relator, show parent and child indicators on attribute description
	 * @return
	 */
	public boolean isIndicateRelations() {
		if (actionItem instanceof EditActionItem) {
			return ((EditActionItem) actionItem).isEditRelatorOnly();
		}
		return false;
	}

	/**
	 * called on event dispatch thread..
	 */
	protected void commitCompleted(){
		if(getEgRstTable()==null){
			return; //deref must have run
		}
		getEgRstTable().refresh();
	
		if(changedKeysVct != null && sourceItemArray!=null && sourceItemArray.length>0){
			if(sourceItemArray[0].getKey()!=null){ //if this is null, the caller tab was closed/derefd
				Long t1 = System.currentTimeMillis();
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting");
				if (nav != null) {
					nav.disableActionsAndWait();
				}else {
					if (getParentTab() instanceof WUsedActionTab) {
						((WUsedActionTab) getParentTab()).disableActionsAndWait();
					}
					if (getParentTab() instanceof MatrixActionBase) {
						((MatrixActionBase) getParentTab()).disableActionsAndWait();
					}
				}
			
				boolean dupeDone = false;
				for(int i=0;i<sourceItemArray.length; i++){
					EntityItem srcItem = sourceItemArray[i];
					if(changedKeysVct.contains(srcItem.getKey())){
						EntityItem eaf = (EntityItem)getEgRstTable().getRow(sourceItemArray[i].getKey());
						if(eaf!=null){
							if(!doesEditMetaMatch(srcItem, eaf)){
								dupeDone = false;//force a refresh
								break; 
							}
							srcItem.duplicateUpdates(eaf);
							if(srcItem.getEntityGroup().isRelator()){
								EntityItem dnitem = (EntityItem)srcItem.getDownLink(0);
								if(dnitem !=null){
									EntityItem dnedititem = (EntityItem)eaf.getDownLink(dnitem.getKey());
									if(dnedititem !=null){
										if(!doesEditMetaMatch(dnitem, dnedititem)){
											dupeDone = false;//force a refresh
											break; 
										}
										dnitem.duplicateUpdates(dnedititem);
									}
								}
							}
							dupeDone = true; //just in case nothing matches
						}
					}
				}

				if(dupeDone){
					// must notify caller to update its model
					if (nav != null) {
						nav.editComplete();
					} else {
						if (getParentTab() instanceof WUsedActionTab) {
							((WUsedActionTab) getParentTab()).editComplete();
						}
						if (getParentTab() instanceof MatrixActionBase) {
							((MatrixActionBase) getParentTab()).editComplete();
						}
					}
				}else{
					//force entire refresh - couldnt find matching edited item
					setShouldRefresh();
					// when tab gets focus it will do refresh and disable the actions then
					if (nav != null) {
						nav.enableActionsAndRestore();
					}else {
						if (getParentTab() instanceof WUsedActionTab) {
							((WUsedActionTab) getParentTab()).enableActionsAndRestore();
						}
						if (getParentTab() instanceof MatrixActionBase) {
							((MatrixActionBase) getParentTab()).enableActionsAndRestore();
						}
					}
				}
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"update ended "+Utils.getDuration(t1));
			}
		}else{
			setShouldRefresh();
		}

		refreshMenu();

		reloadNLSTree();
		changedKeysVct = null;
	}
	
	/**
	 * must check if user added flag meta, meta from src entity will not have it
	 * this is only a problem if the navigate attribute was updated, it will not show
	 * @param srcItem
	 * @param editItem
	 * @return
	 */
	private boolean doesEditMetaMatch(EntityItem srcItem, EntityItem editItem){
		FlagMaintAction fma = (FlagMaintAction)getAction(FLAGMAINT_ACTION);
		if(fma.isMaintenanceable()){ // only need to check if user has this rolefunction
			EntityGroup srceg = srcItem.getEntityGroup();
			for (int j = 0; j < srceg.getMetaAttributeCount(); j++) {
				//use meta attr from source entity
				EANMetaAttribute ma = srceg.getMetaAttribute(j);
				if(ma instanceof EANMetaFlagAttribute){
					if(!fma.canMaintenance(ma)){
						continue;
					}
					EANMetaFlagAttribute srcmfa = (EANMetaFlagAttribute)ma;
					EANFlagAttribute flagattr = (EANFlagAttribute)editItem.getAttribute(srcmfa.getAttributeCode());
					//does the edited meta have any extra flags?
					MetaFlag[] flags = (MetaFlag[])flagattr.get();
					for(int f=0;f<flags.length;f++){
						if (flags[f].isSelected() && !srcmfa.containsMetaFlag(flags[f].getFlagCode())) {
				        	logger.log(Level.INFO, "Flag Meta was added, a parent refresh is required");
							return false;
						}
					}
				}
			}
		}

		return true;
	}

	private JMenuItem findMenuItem(String s,JMenu amenu){
		if (amenu.getText().equals(s)){
			return amenu;
		}
		for (int ii=0; ii<amenu.getItemCount(); ii++) {
			JMenuItem item = amenu.getItem(ii);
			if (item==null) {// separators are null
				continue;
			}

			if (item.getText().equals(s)){
				return item;
			}
			if (item instanceof JMenu) {
				item = findMenuItem(s,(JMenu)item);
				if (item!=null) return item;
			}
		}
		return null;
	}
	private JMenuItem findMenuItem(String s) {
		for (int i=0; i<getMenubar().getMenuCount(); i++) {
			JMenu amenu = getMenubar().getMenu(i);
			JMenuItem item = findMenuItem(s,amenu);
			if (item!=null) {
				return item;
			}
		}
		return null;
	}


	//everything below here has not been gone thru
	//--------------------------------------------------
	//--------------------------------------------------


	/**
	 * called by ToggleAction - show or hide action panel
	 */
	protected void toggleSplit() {
		toggleSplit(getActionTree(), getActionTree().getPreferredWidth());
	}
	/**
	 * called by the ToggleNlsTreeAction
	 */
	protected void toggleTreeView() {
		toggleSplit(nlsTree, nlsTree.getPreferredWidth());
	}
	/**
	 * show or hide the specified tree
	 * @param tree
	 * @param prefWidth
	 */
	private void toggleSplit(JComponent tree, int prefWidth){
		int divLoc = getSplitPane().getDividerLocation();
		Component leftComponent = getSplitPane().getLeftComponent();
		if (divLoc < 10) {
			//nothing showing, but it isnt the requested tree
			if(leftComponent!=tree){
				getSplitPane().setLeftComponent(tree);
				tree.revalidate();
			}
			getSplitPane().setDividerLocation(prefWidth);
		} else {
			//is showing, but it isnt the requested tree
			if(leftComponent!=tree){
				getSplitPane().setLeftComponent(tree);
				tree.revalidate();
				getSplitPane().setDividerLocation(prefWidth);
			}else{
				getSplitPane().setDividerLocation(0);
			}
		}
		repaint();
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.tabs.DataActionPanel#getProfile()
	 */
	public Profile getProfile() {
		return entityList.getProfile();
	}

	/**
	 * close the editor tab
	 *
	 * @return
	 */
	public boolean canClose() {
		// user wants to close the tab, were there any changes?
		EditorPanel ed = getCurrentEditor();
		if (ed !=null) {
			// try to end the current edit, if not, then just cancel it
			if (!ed.canStopEditing()) {
				ed.cancelCurrentEdit();
			}

			if (ed.okToClose()) {
				LockList ll = EACM.getEACM().getLockMgr().getLockList(getProfile(),true);
				if (ll != null) {
					// if mw is down, this loops thru each row and column trying to unlock each cell
					// app appears hung, so end now if cant verify connection to mw
					try {
						String time = RMIMgr.getRmiMgr().testGetServerTime();
						if(time==null){
							UI.showErrorMessage(this, "Unable to release locks. Can not contact server");
							return true;
						}
					} catch (Exception e) {
						e.printStackTrace();
						UI.showErrorMessage(this, "Unable to release locks: "+e.getMessage());
						return true;
					} 
					EntityItem lockOwnerEI = EACM.getEACM().getLockMgr().getLockOwner(getProfile());
					if (lockOwnerEI!=null){
						try {
							egRstTable.unlock(RMIMgr.getRmiMgr().getRemoteDatabaseInterface(), null, ll,
									getProfile(), lockOwnerEI, LockGroup.LOCK_NORMAL);

							Utils.monitor("unlock", ll);
						} catch (Exception e) {
							e.printStackTrace();
							return true;
						} 
					}
				}
			} else {
				return false;
			}
		}
		return super.canClose();
	}

	/**
	 * select
	 *
	 */
	public void select() {

		EACM.getEACM().updateMenuBar(getMenubar());
		revalidate();

		EACMAction act = EACM.getEACM().getGlobalAction(RESETDATE_ACTION);
		if(act instanceof ResetDateAction) {
			((ResetDateAction)act).setCurrentTab(this);
			act.setEnabled(false);
		}

		EACM.getEACM().setFilterStatus(hasFiltered());
		EACM.getEACM().setHiddenStatus(hasHiddenAttributes());
		EACM.getEACM().setActiveProfile(entityList.getProfile());
		EACM.getEACM().setPastStatus();

		requestFocusInWindow();

		getCurrentEditor().select();

		// must reflect current lock state, can get out of sync if the user unlocks in the unlocktab
		act = getAction(LOCK_ACTION);
		if(act !=null) {
			act.setEnabled(true);
		}
		act = getAction(UNLOCK_ACTION);
		if(act !=null) {
			act.setEnabled(true);
		}
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#requestFocusInWindow()
	 */
	public boolean requestFocusInWindow() {
		EditorPanel ed = getCurrentEditor();
		if (ed != null) {
			return ed.requestFocusInWindow();
		}
		return super.requestFocusInWindow();
	}

	/**
	 * dereference
	 */
	public void dereference() {
		if(nav!=null){
			// tell navigate edit is closing
			nav.removeEditCtrl(this);
		}
		sourceItemArray = null;
		recordToggle.dereference();
		recordToggle = null;

		getSplitPane().setRightComponent(null);

		for (int i = 0; i < MAX_EDITOR; ++i) {
			if (edit[i] != null) {
				edit[i].dereference();
				edit[i] = null;
			}
		}

		edit = null;

		if (entityList != null) {
			entityList.dereference();
			entityList = null;
		}

		egRstTable = null;
		editEntityGroup = null;

		if (parSelCombo != null) {
			parSelCombo.dereference();
			parSelCombo = null;
		}

		nav = null;
		actionItem = null;

		if (nlsTree != null) {
			nlsTree.dereference();
			nlsTree = null;
		}

		super.dereference();
	}

	/**
	 * get the toggle control, used in vertical and form editors
	 * @return
	 */
	protected RecordToggle getRecordToggle() {return recordToggle;}

	/**
	 * create the editor if it doesnt already exist
	 * @param type
	 * @param key - current record key or null
	 * @return
	 */
	private EditorPanel getEditorForType(int type, String key) {
		if (edit[type] == null) {
			EANFoundation ean = null;
	        if (key != null) {
	            ean = getEgRstTable().getRow(key);
	        } else {
	            ean = getEgRstTable().getRow(0);
	        }
			switch (type) {
			case GRID_EDITOR :
				edit[type] = new GridEditor(this);
				recordToggle.setGridTable(edit[type].getJTable());
				break;
			case VERT_EDITOR :
				edit[type] = new VertEditor((EntityItem)ean, this);
				break;
			case FORM_EDITOR :
				edit[type] = new FormEditor((EntityItem)ean, this);
				break;
			default :
				break;
			}
		}

		return edit[type];
	}


	/**
	 * get the entitylist for this edit
	 * called by editors
	 * @return
	 */
	public EntityList getEntityList(){
		return entityList;
	}

	/**
	 * used by setEditorAction
	 * @return
	 */
	protected int getCurrentEditorType(){
		return currentEditorType;
	}

	/**
	 * called when instantiating an edit session or from setEditorAction
	 * @param type
	 * @param attrkey - has currently selected attr when switching editors
	 */
	protected void setEditor(int type,String attrkey) {
		if (currentEditorType !=-1) {
			if (!getCurrentEditor().canStopEditing()) { // end any current edits
				javax.swing.SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						//display problem to user
						getCurrentEditor().stopCellEditing();
					}
				});
				return;
			}
		}
		if (currentEditorType == type) { // already using the specified editor
			return;
		}
		EditorPanel curEdit = getCurrentEditor();
		String curRecKey = null;
		String curSelKey = attrkey;
		if (curEdit != null) {
			//get current selection
			curRecKey = curEdit.getRecordKey(); // basically entity
			//curSelKey = curEdit.getSelectionKey(); // and attribute
		}

		curEdit = addEditor(type, curRecKey);
		// select this entity and attribute
		curEdit.setSelection(curRecKey, curSelKey);

		FlagMaintAction fma = (FlagMaintAction)getAction(FLAGMAINT_ACTION);
		// show or hide the flag maint button
		curEdit.setToolBarButtonVisible(FLAGMAINT_ACTION,fma.isMaintenanceable());

		// show or hide the nlstree button
		curEdit.setToolBarButtonVisible(TOGGLENLSTREE_ACTION,this.isAllNLS());

		// load current key into toggle
		recordToggle.setCurrentKey(curEdit.getRecordKey());

		EACM.getEACM().setFilterStatus(curEdit.hasFiltered());
		EACM.getEACM().setHiddenStatus(curEdit.hasHiddenAttributes());

		// enable or disable actions that need editor
		updateEditorActions();

		EANFoundation ean = null;
        if (curRecKey != null) {
            ean = getEgRstTable().getRow(curRecKey);
        } else {
            ean = getEgRstTable().getRow(0);
        }

		loadNLSTree((EntityItem)ean); // using selected entityitem
	}


	/**
	 * @param type
	 * @param key - current record key or null
	 */
	private EditorPanel addEditor(int type, String key) {
		// get the editor for this type
		EditorPanel ed = getEditorForType(type, key);
		currentEditorType = type;

		// make sure recordtoggle is on the current toolbar
		ed.restoreRecordToggle();

		setVisible(Utils.getResource(FILL_MENU), currentEditorType!=FORM_EDITOR);

		refreshMenu();

		//load the editor into the splitpane
		getSplitPane().setRightComponent(ed);
		getSplitPane().setDividerLocation(0);

		ed.requestFocusInWindow();

		return ed;
	}

	/**
	 * commit - called when tab is closing and user has not saved - from okToClose()
	 * @return
	 */
	protected boolean commit() {
		if (!getCurrentEditor().canStopEditing()) { // end any current edits
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					//display problem to user
					getCurrentEditor().stopCellEditing();
				}
			});
			return false;
		}

		try{
			getCurrentEditor().commit();
			commitCompleted();
			return true;
		}catch (EANBusinessRuleException bre) {
			getCurrentEditor().moveToError(bre);
			com.ibm.eacm.ui.UI.showException(this,bre);
		} catch (Exception re) {
			if(edit!=null){ //if null, this was derefd - save must have hung and user closed tab
				com.ibm.eacm.ui.UI.showException(this,re);
			}
		}

		return false;
	}

	/**
	 * used by attribute history action
	 * @return
	 */
	protected EANAttribute getSelectedAttribute(){
		EANAttribute attr = null;
		EditorPanel ed = getCurrentEditor();
		if (ed != null) {
			attr = ed.getSelectedAttribute();
		}
		return attr;
	}

	/**
	 * canEditDefault
	 * @return
	 */
	public boolean canEditDefault() {
		return getProfile().hasRoleFunction(Profile.ROLE_FUNCTION_DEFAULT_VALUES);
	}

	/**
	 * get the grideditor if one was instantiated
	 * @return
	 */
	protected GridEditor getGridEditor() {
		return (GridEditor) edit[GRID_EDITOR];
	}

	/**
	 * commitAll
	 * @return
	 */
	protected boolean commitAll() {
		try {
			// get the set of entities that will be commited
			changedKeysVct = getCurrentEditor(). getChangedKeys();
			getEgRstTable().commit(RMIMgr.getRmiMgr().getRemoteDatabaseInterface());
			return true;
		} catch (EANBusinessRuleException bre) {
			getCurrentEditor().moveToError(bre);
			com.ibm.eacm.ui.UI.showException(this,bre);
		} catch (Exception re) {
			if(egRstTable!=null){ //if null, this was derefd - save must have hung and user closed tab
				com.ibm.eacm.ui.UI.showException(this,re);
			}
		}
		return false;
	}
	
	/**
	 * commitAll on background thread - called from saveall action
	 * @return
	 */
	protected void commitAllBg() throws Exception {
		// get the set of entities that will be commited
		changedKeysVct = getCurrentEditor(). getChangedKeys();
		getEgRstTable().commit(RMIMgr.getRmiMgr().getRemoteDatabaseInterface());
	}
	/**
	 * commitRecord - commit selected records
	 * @return
	 * @throws Exception 
	 */
	protected void commitRecord() throws Exception {
		// get the set of entities that will be commited
		changedKeysVct = getCurrentEditor(). getSelectedChangedKeys();
		getCurrentEditor().commit(); 
	}

	/**
	 * called by removenew action - remove any selected row that is new
	 */
	protected void removeNewRows(){
	   	//grid editor has the editctrl table rendered in a jtable
		if(getCurrentEditorType()==GRID_EDITOR){
			getGridEditor().removeNew();
		}else{
			EntityItem curItem = getCurrentEditor().getCurrentEntityItem();
			int mdlid = getEgRstTable().getRowIndex(curItem.getKey());

			// item in this editor will be removed, get another from the recordtoggle
			// because the editctrl.rst may be filtered
			String nextkey = null;
			if(getRecordToggle().hasNext()){
				nextkey = getRecordToggle().getNextRowKey();
			}else if(getRecordToggle().hasPrev()){
				nextkey = getRecordToggle().getPrevRowKey();
			}

			getEgRstTable().removeRow(mdlid);

			// update the grideditor's jtable
			GridEditor ged = getGridEditor();
			if(ged != null){
				ged.refreshModel();
			}

			// load another entity
			loadSingleEditor(nextkey);
		}
	}
	/**
	 * called by createaction - add row directly to the edit controller RST
	 * update the grideditor jtable and
	 */
	protected void addNewRow(){
		boolean success=false;
		RowSelectableTable rst = getEgRstTable();
		if (entityList.isVEEdit()) {
			success = rst.addRow(getCurrentEditor().getRecordKey());
		} else {
			success = rst.addRow();
		}
		if (success){
			int mdlrowid = rst.getRowCount()-1;
			//grid editor has the editctrl table rendered in a jtable, refresh it
			if(getCurrentEditorType()==GRID_EDITOR){
				//getGridEditor().refreshModel(); // this sorts - user wants them all at the end
				getGridEditor().rowsAdded(mdlrowid); // put new rows at bottom
				//select the newly added row - done above now
				//getGridEditor().selectModelRow(mdlrowid);
			}else{
				// update the grideditor's jtable
				GridEditor ged = getGridEditor();
				if(ged != null){
					ged.refreshModel();
					//ged.refreshModel(); // this sorts - user wants them all at the end
					ged.rowsAdded(mdlrowid); // put new rows at bottom
				}

				//load the editor with the new row
				String newkey = egRstTable.getRowKey(mdlrowid);
				loadSingleEditor(newkey);
			}
		}else{
			com.ibm.eacm.ui.UI.showErrorMessage(this,"Error adding row, see log for details");
		}
	}
    /**
     * called by duplicateaction - duplicate selected rows
     * @param copies
     */
    protected void duplicate(int copies) {
    	//support multiple rows
		getCurrentEditor().duplicate(copies);
		
		//grid editor has the editctrl table rendered in a jtable, refresh it
		if(getCurrentEditorType()!=GRID_EDITOR){
			// update the grideditor's jtable
			GridEditor ged = getGridEditor();
			if(ged != null){
				ged.refreshModel();
			}

			//load the single editor with the last new row
			int row = egRstTable.getRowCount() - 1;
			String newkey = egRstTable.getRowKey(row);
			loadSingleEditor(newkey);
		}
    }

    /**
     * called by ResetAllAction
     */
    protected void resetAll() {
    	//grid editor has the editctrl table rendered in a jtable
		if(getCurrentEditorType()==GRID_EDITOR){
			getGridEditor().rollback();
		}else{
			EntityItem curItem = getCurrentEditor().getCurrentEntityItem();
			int mdlid = getEgRstTable().getRowIndex(curItem.getKey());

			if(curItem.isNew()){
				// item in this editor will be removed, get another from the recordtoggle
				// because the editctrl.rst may be filtered
				String nextkey = null;
				if(getRecordToggle().hasNext()){
					nextkey = getRecordToggle().getNextRowKey();
				}else if(getRecordToggle().hasPrev()){
					nextkey = getRecordToggle().getPrevRowKey();
				}

				if(nextkey==null){
					// cant delete the last entity in the editor
					getCurrentEditor().rollback();
					return;
				}

				getEgRstTable().removeRow(mdlid);

				// update the grideditor's jtable
				GridEditor ged = getGridEditor();
				if(ged != null){
					ged.refreshModel();
				}

				// load another entity
				loadSingleEditor(nextkey);
			}else{
				getCurrentEditor().rollback();
			}
		}
    }

	public EntityItem[] getSelectedEntityItems(){
		return getCurrentEditor().getSelectedEntityItems();
	}

	/**
	 * enable actions based on current selection
	 */
	public void refreshActions() {
		Action action = getAction(EDITMTRX_ACTIONSET);
		if(action!=null){
			action.setEnabled(getActionTree().actionExists(ACTION_PURPOSE_MATRIX));
		}

		action = getAction(EDITWF_ACTIONSET);
		if(action!=null){
			action.setEnabled(getActionTree().actionExists(ACTION_PURPOSE_WORK_FLOW));
		}
	}

	/**
	 * hasMasterChanges
	 * @return
	 */
	protected boolean hasMasterChanges() {
		if(getProfile()==null || getProfile().isReadOnly()){
			return false;
		}
		// some changes are not rolled back when unlocked, like the default values
		// make sure user still has lock(s)
		boolean chgs = egRstTable.hasChanges();
		if(chgs){
			// this checks for existing locks and changes
			if (getEntityItemCount() == 1) { // vertical edit may be used on multiple rows - dont ignore other rows
				chgs = this.getCurrentEditor().hasAnyChanges() || this.getCurrentEditor().isNew();
			}
		}
		return chgs;
	}

	/**
	 * isEditable
	 * @return
	 */
	protected boolean isEditable() {
		return Utils.isEditable(egRstTable,getProfile());
	}

	/**
	 * isCreatable
	 * @return
	 */
	protected boolean isCreatable() {
		return (!getProfile().isReadOnly()) && egRstTable.canCreate() && !Utils.isPast(getProfile());
	}

    /**
	 * createMenu
	 */
	private void createMenu() {
		//jump to the menubar F10 or Alt is the Windows keyboard shortcut - disable F10
		getMenubar().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_F10, 0,false), "none"); 
		getMenubar().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
					KeyStroke.getKeyStroke(KeyEvent.VK_F10, 0,true), "none"); 
		
		Profile prof = getProfile();
		createFileMenu();
		createEditMenu();
		createViewMenu();

		if (!prof.hasRoleFunction(Profile.ROLE_FUNCTION_READONLY)) {
			createFillMenu();
		}

		createUpdateMenu();
		createTableMenu();
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.tabs.TabPanel#createActions()
	 */
	public void createActions(){
		super.createActions();

		addAction(new SaveRecordAction(this));
		addAction(new SaveAllAction(this));
		addAction(new SaveDefaultAction(this));
		addAction(new CancelDefaultAction(this));
		addAction(new ResetAllAction(this));
		addAction(new ResetSingleAction(this));
		addAction(new ResetRecordAction(this));
		addAction(new DeactivateAttrAction(this));

		addAction(new EANMtrxActionSet(this));

		addAction(new EANWFlowActionSet(this));

		addAction(new SetGridEditorAction(this));
		addAction(new SetVertEditorAction(this));
		addAction(new SetFormEditorAction(this));
		EACMAction act = new ThawAction(this);
		addAction(act);
		addAction(new FreezeAction(this,act));
		addAction(new LockAction(this));
		addAction(new UnlockAction(this));
		addAction(new PrevEditAction(this));
		addAction(new NextEditAction(this));
		addAction(new CreateAction(this));
		addAction(new DuplicateAction(this));
		addAction(new RemoveNewAction(this));
		addAction(new RefreshFormAction(this));
		addAction(new ToggleAction(this));
		addAction(new ToggleNLSTreeAction(this));
		addAction(new SpellCheckAction(this));
		addAction(new ImportXSLAction(this));
		addAction(new ImportODSAction(this));
	    addAction(new FillPasteAction(this));
	    addAction(new PrintAction(this));
	    addAction(new ShowAttrHistoryAction(this));

        //COPY_ACTION
        addAction(new CopyAction(null));
        //PASTE_ACTION
        addAction(new PasteAction(this,null));
        //CUT_ACTION
        addAction(new CutAction(null));
        //FILLCOPY_ACTION
        addAction(new FillCopyAction(null));
        addAction(new FillCopyEntityAction(null));
        addAction(new FillAppendAction(null));

      	addAction(new FindRepAction());
		addAction(new FindNextAction());

		createTableActions(null);
	}

	/**
	 * update actions that need the current editor
	 */
	private void updateEditorActions(){
		for(int i=0; i<EDITOR_ACTIONS.length; i++){
			EACMAction act = getAction(EDITOR_ACTIONS[i]);
			if (act!=null){
				act.setEnabled(true);// action will check other criteria
			}
		}
		if(parSelCombo!=null){
			parSelCombo.setEnabled(true); // this will check the currenteditor
			updateParentSelector();
		}
		updateNLSTree();
	}

	/**
	 * called to select parent of item in the editor
	 */
	private void updateParentSelector() {
		if(getCurrentEditor() !=null) {
			Object obj = getCurrentEditor().getSelectedEAN();
			if (parSelCombo != null && obj instanceof EntityItem) {
				EntityItem ei = (EntityItem) obj;
				EANEntity ean = null;
				if (entityList.isCreateParent()) {
					if (ei.hasDownLinks()) {
						ean = ei.getDownLink(0);
					}
				} else if (ei.hasUpLinks()) {
					ean = ei.getUpLink(0);
				}

				parSelCombo.setSelectedParent((EntityItem)ean);
			}
		}
	}
	/**
	 * update actions that need the current entityitem
	 */
	private void updateToggledActions(){
		for(int i=0; i<TOGGLED_ACTIONS.length; i++){
			EACMAction act = getAction(TOGGLED_ACTIONS[i]);
			if (act!=null){
				act.setEnabled(true);// action will check other criteria
			}
		}
		if(parSelCombo!=null){
			parSelCombo.setEnabled(true); // this will check the currenteditor
			updateParentSelector();
		}
	}

	/**
	 * called from PrevEditAction - will only be enabled if call is valid
	 */
	protected void setPrevEdit(){
		String prevkey = recordToggle.getPrevRowKey();
		loadSingleEditor(prevkey);
	}
	/**
	 * called from NextEditAction - will only be enabled if call is valid
	 */
	protected void setNextEdit(){
		String nextkey = recordToggle.getNextRowKey();
		loadSingleEditor(nextkey);
	}
	/**
	 * load vertical or form editor with specified key
	 * @param key
	 */
	private void loadSingleEditor(String key){
		EANFoundation ean = null;
		if(key!=null){
			ean = egRstTable.getRow(key);
		}

		// update editor
		getCurrentEditor().updateRSTModel((EntityItem)ean);

		// set nextkey in recordtoggle
		recordToggle.setCurrentKey(key);

		updateToggledActions();
	}

	/**
	 * createFileMenu
	 */
	public void createFileMenu() {
		JMenu fileMenu = new JMenu(Utils.getResource(FILE_MENU));
		fileMenu.setMnemonic(Utils.getMnemonic(FILE_MENU));

		addGlobalActionMenuItem(fileMenu,CLOSETAB_ACTION);

		addGlobalActionMenuItem(fileMenu,CLOSEALL_ACTION);

		fileMenu.addSeparator();

		addLocalActionMenuItem(fileMenu, SAVERECORD_ACTION);

        if (canEditDefault()) { 
        	addLocalActionMenuItem(fileMenu, SAVEDEFAULT_ACTION);
        }

 		addLocalActionMenuItem(fileMenu, SAVEALL_ACTION);

		fileMenu.addSeparator();

		addLocalActionMenuItem(fileMenu, PRINT_ACTION);

	    addGlobalActionMenuItem(fileMenu,PAGESETUP_ACTION);

		fileMenu.addSeparator();

		addGlobalActionMenuItem(fileMenu,EXIT_ACTION);

		getMenubar().add(fileMenu);
	}

	/**
	 * createFillMenu
	 */
	private void createFillMenu() {
		JMenu fillMenu = new JMenu(Utils.getResource(FILL_MENU));
		fillMenu.setMnemonic(Utils.getMnemonic(FILL_MENU));

		addLocalActionMenuItem(fillMenu, FILLCOPY_ACTION);
		addLocalActionMenuItem(fillMenu, FILLCOPYENTITY_ACTION);
		addLocalActionMenuItem(fillMenu, FILLAPPEND_ACTION);

		addLocalActionMenuItem(fillMenu, FILLPASTE_ACTION);

		getMenubar().add(fillMenu);
	}

	/**
	 * createEditMenu
	 */
	private void createEditMenu() {
		JMenu editMenu = new JMenu(Utils.getResource(EDIT_MENU));
		editMenu.setMnemonic(Utils.getMnemonic(EDIT_MENU));

		addLocalActionMenuItem(editMenu, CUT_ACTION);

		addLocalActionMenuItem(editMenu, COPY_ACTION);

		addLocalActionMenuItem(editMenu, PASTE_ACTION);

		addLocalActionMenuItem(editMenu, DUPLICATE_ACTION);

		editMenu.addSeparator();

		addLocalActionMenuItem(editMenu, RESETALLATTR_ACTION);
		addLocalActionMenuItem(editMenu, RESETRECORD_ACTION);
		addLocalActionMenuItem(editMenu, RESETONEATTR_ACTION);
		if (canEditDefault()) {
			addLocalActionMenuItem(editMenu, CANCELDEFAULT_ACTION);
		}
		editMenu.addSeparator();

		addLocalActionMenuItem(editMenu, SPELLCHK_ACTION);

		editMenu.addSeparator();
		addLocalActionMenuItem(editMenu, FINDREP_ACTION);
		addLocalActionMenuItem(editMenu, FINDNEXT_ACTION);
		getMenubar().add(editMenu);
	}

	/**
	 * createUpdateMenu
	 */
	private void createUpdateMenu() {
		JMenu updtMenu = new JMenu(Utils.getResource(UPDATE_MENU));
		updtMenu.setMnemonic(Utils.getMnemonic(UPDATE_MENU));

		addLocalActionMenuItem(updtMenu, DEACTIVATEATTR_ACTION);

		updtMenu.addSeparator();
		addLocalActionMenuItem(updtMenu, LOCK_ACTION);
		addLocalActionMenuItem(updtMenu, UNLOCK_ACTION);
		updtMenu.addSeparator();
		addLocalActionMenuItem(updtMenu, CREATE_ACTION);

		FlagMaintAction fma = (FlagMaintAction)getAction(FLAGMAINT_ACTION);
		if (fma.isMaintenanceable()) {
			addLocalActionMenuItem(updtMenu, FLAGMAINT_ACTION);
		}

		addLocalActionMenuItem(updtMenu, IMPORTXLS_ACTION);
		addLocalActionMenuItem(updtMenu, IMPORTODS_ACTION);

		addLocalActionMenuItem(updtMenu, REMOVENEW_ACTION);

		getMenubar().add(updtMenu);
	}

	/**
	 * createTableMenu
	 *
	 */
	private void createTableMenu() {
		JMenu tblMenu = new JMenu(Utils.getResource(TABLE_MENU));
		tblMenu.setMnemonic(Utils.getMnemonic(TABLE_MENU));

		addLocalActionMenuItem(tblMenu, MOVECOL_LEFT_ACTION);
		addLocalActionMenuItem(tblMenu, MOVECOL_RIGHT_ACTION);

		tblMenu.addSeparator();

		addLocalActionMenuItem(tblMenu, FREEZE_ACTION);
		addLocalActionMenuItem(tblMenu, THAW_ACTION);

		tblMenu.addSeparator();
		addLocalActionMenuItem(tblMenu, HIDECOL_ACTION);
		addLocalActionMenuItem(tblMenu, UNHIDECOL_ACTION);

		tblMenu.addSeparator();
		addLocalActionMenuItem(tblMenu, SELECTALL_ACTION);
		addLocalActionMenuItem(tblMenu, SELECTINV_ACTION);

		tblMenu.addSeparator();
		addLocalActionMenuItem(tblMenu, SORT_ACTION);

		tblMenu.addSeparator();
		addLocalActionMenuItem(tblMenu, FILTER_ACTION);

		tblMenu.addSeparator();
		addLocalActionMenuItem(tblMenu, SHOWATTRHIST_ACTION);

		addLocalActionMenuItem(tblMenu, ENTITYDATA_ACTION);

		getMenubar().add(tblMenu);
	}

	/**
	 * create the view menu
	 */
	private void createViewMenu() {
		JMenu viewMenu = new JMenu(Utils.getResource(VIEW_MENU));
		viewMenu.setMnemonic(Utils.getMnemonic(VIEW_MENU));

		addLocalActionMenuItem(viewMenu, GRIDEDITOR_ACTION);
		addLocalActionMenuItem(viewMenu, VERTEDITOR_ACTION);
		addLocalActionMenuItem(viewMenu, FORMEDITOR_ACTION);

		if (getActionTree().actionExists(ACTION_PURPOSE_MATRIX)) {
			addLocalActionMenuItem(viewMenu, EDITMTRX_ACTIONSET);
		}
		if (getActionTree().actionExists(ACTION_PURPOSE_WORK_FLOW)) {
			addLocalActionMenuItem(viewMenu, EDITWF_ACTIONSET);
		}
		viewMenu.addSeparator();
		addLocalActionMenuItem(viewMenu, REFRESHFORM_ACTION);
		viewMenu.addSeparator();
		addLocalActionMenuItem(viewMenu, PREVEDIT_ACTION);
		addLocalActionMenuItem(viewMenu, NEXTEDIT_ACTION);
		viewMenu.addSeparator();
		addLocalActionMenuItem(viewMenu, TOGGLE_ACTION);
		if (isAllNLS()) {
			addLocalActionMenuItem(viewMenu, TOGGLENLSTREE_ACTION);
		}

		getMenubar().add(viewMenu);
	}

	/**
	 * called by the RefreshFormAction - not so sure this is needed
	 */
	protected void formRefresh(){
    	if (getCurrentEditor() instanceof FormEditor) {
    		((FormEditor) getCurrentEditor()).formRefresh();
    		repaint();
    	}
	}

	/**
	 * getFormName
	 * @return
	 */
	public String getFormName() {
		if (actionItem instanceof EditActionItem) {
			return ((EditActionItem) actionItem).getFormKey() + ".html";
		} else if (actionItem instanceof CreateActionItem) {
			return ((CreateActionItem) actionItem).getFormKey() + ".html";
		}

		return null;
	}

	/**
	 * isFormCapable
	 * used by seteditoraction too
	 * @return
	 */
	protected boolean isFormCapable() {
		if (actionItem instanceof EditActionItem) {
			if (((EditActionItem) actionItem).isFormCapable()) {
				return Utils.exists(RESOURCE_DIRECTORY,getFormName());
			}
		} else if (actionItem instanceof CreateActionItem) {
			if (((CreateActionItem) actionItem).isFormCapable()) {
				return Utils.exists(RESOURCE_DIRECTORY,getFormName());
			}
		}

		return false;
	}

	/**
	 * getHelpText
	 *
	 * @return
	 */
	public String getHelpText() {
		String help=null;
		EditorPanel ed = getCurrentEditor();
		if (ed != null) {
			help= ed.getHelpText();
		}
		return help;
	}


	/* this is needed when creating the toolbar, editor is not created yet
	 * @see com.ibm.eacm.tabs.TabPanel#getDefaultToolbarLayout()
	 */
	public ComboItem getDefaultToolbarLayout() {
		if (egRstTable.getRowCount() == 1) {
			if (isFormCapable()) {
				return DefaultToolbarLayout.EDIT_FORM_BAR;
			} else {
				return DefaultToolbarLayout.EDIT_VERT_BAR;
			}
		} else {
			return DefaultToolbarLayout.EDIT_HORZ_BAR;
		}
	}


	/* (non-Javadoc)
	 * @see com.ibm.eacm.tabs.ActionTabPanel#getTabMenuTitleKey()
	 */
	protected String getTabMenuTitleKey() {
		String key = null;
		if (actionItem instanceof CreateActionItem) {
			key ="create.title";
		} else {
			key ="edit.title";
		}
		return key;
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.tabs.TabPanel#getTabIconKey()
	 */
	protected String getTabIconKey(){
		if (actionItem instanceof CreateActionItem) {
			return "create.icon";
		}
		return "edit.icon";
	}

	/**
	 * if there is any MetaMaintActionItem in this set of executable actions, load it into the
	 * FlagMaintAction, if not, clear the FlagMaintAction
	 * @param ean
	 */
	private void retrieveMetaMaintAction(EANActionItem[] ean) {
		MetaMaintActionItem maint = null;
		if (ean != null) {
			for (int i=0;i<ean.length;++i) {
				if (ean[i] instanceof MetaMaintActionItem) {
					maint = (MetaMaintActionItem)ean[i];
					break;
				}
			}
		}
		FlagMaintAction fma = (FlagMaintAction)getAction(FLAGMAINT_ACTION);
		fma.setMaintActionItem(maint);
	}

	/* (non-Javadoc)
	 * when column selection changes this is notified
	 * also called when tablemodel is changed
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	public void valueChanged(ListSelectionEvent lse) {
		if (!lse.getValueIsAdjusting()) {
			updateRowColActions();
			updateNLSTree();
		}
	}

	/**
	 * enable or disable the nls tree based on entityitem selection
	 */
	private void updateNLSTree(){
		// load the selected entity into the nlstree
		if(getCurrentEditor()!=null){
			if(nlsTree !=null){
				EntityItem[]eia = getCurrentEditor().getSelectedEntityItems();
				//must have a single entity selected to show details
				if(eia!=null && eia.length==1){
					loadNLSTree(eia[0]);
				}else{
					loadNLSTree(null);
				}
			}
		}
	}

	/**
	 * update the actions that rely on attribute selection, form will not have a listselection change
	 */
	public void updateRowColActions(){
		// these actions require something to be selected
		for (int i=0; i<ROWCOL_SELECTION_ACTIONS.length; i++){
			EACMAction act = getAction(ROWCOL_SELECTION_ACTIONS[i]);
			if (act!=null){
				act.setEnabled(true);
			}
		}
		if(parSelCombo!=null){
			parSelCombo.setEnabled(true); // this will check the currenteditor
			updateParentSelector();
		}
	}

    /**
     * import Table from Apache OpenDocument ss
     * RCQ 288700
     * @param fn
     */
    protected void importODSTable(String fn) {
    	try	{
    		ODSImport odsImport = new ODSImport(fn);
    		odsImport.process(this);
    		String warning = odsImport.getWarnings();
    		// display warnings
    		if (warning.length()>0){
    			//	com.ibm.eacm.ui.UI.showMessage(null,"warning-title", JOptionPane.WARNING_MESSAGE, "warning-acc",
    			//		warning);
    			com.ibm.eacm.ui.UI.showWarning(null,warning);
    		}
    		odsImport.dereference();
    	}
    	catch (Exception e){
    		com.ibm.eacm.ui.UI.showException(this,e);
    	}
    }
    /**
     * importTable from Excel ss
     */
    protected void importTable() {
    	try{
      		int mdlrowid = egRstTable.getRowCount();
    		XLSImport.importFromFile(this); // use Importable interface to callback based on filetype selected

    		if(egRstTable.getRowCount()==mdlrowid){
    			return;  //user cancelled
    		}
			//grid editor has the editctrl table rendered in a jtable, refresh it
			if(getCurrentEditorType()==GRID_EDITOR){
				getGridEditor().refreshModel();
				//select the first newly added row
				getGridEditor().selectModelRow(mdlrowid);
			}else{
				// update the grideditor's jtable
				GridEditor ged = getGridEditor();
				if(ged != null){
					ged.refreshModel();
				}

				//load the editor with the last new row
				mdlrowid = egRstTable.getRowCount()-1; //record toggle will show count loaded if this is done
				String newkey = egRstTable.getRowKey(mdlrowid);
				loadSingleEditor(newkey);
			}
			getCurrentEditor().resizeAndRepaint();
    	}catch(Throwable exc){
    		  exc.printStackTrace();
    		  com.ibm.eacm.ui.UI.showErrorMessage(this,exc.toString());
    	}
    }

    /**
     * Importable interface
     * processXLSImport import from Excel xls ss or from Apache OpenDocument ods file
     * @param index int counter into number of rows in ss
     * @param attrCodes String[] attribute codes
     * @param attrValue String[], some may be null
     */
    public EANBusinessRuleException processXLSImport(int index, String[] attrCodes, String[] attrValue)
    {
    	// populate a newly created entity with values from xls/ods ss
    	return processImport(index, egRstTable.getRowCount()-1, attrCodes, attrValue);
    }

    /**
     * processImport
     * @param record
     * @param head String[] attribute codes, none should be null
     * @param data String[] attribute values, some may be null
     *
     */
    private EANBusinessRuleException processImport(int index,int record, String[] head, String[] data) {
    	EANBusinessRuleException bre = null;
        if (head != null && data != null) {
            int hh = head.length;
            int dd = data.length;

        	logger.log(Level.FINE, "index= "+ index+ " record=" + record+" head.len "+hh+" data.len "+dd);
        	
            EntityItem ei = (EntityItem)egRstTable.getRow(record); // get current ei
            if (ei !=null && index==0 && ei.getEntityID()<0){
            	// current entity is newly created and is the first one, so fill it in with import values
            }else{
            	egRstTable.addRow(); // create a new row and entityitem
                ei = (EntityItem)egRstTable.getRow(egRstTable.getRowCount()-1);
                if(ei !=null){
                	getRecordToggle().setCountOf(); //update tooltip
                }
            }

            if (ei==null){
                bre = new LocalRuleException("EntityItem can not be found");
                return bre;
            }
                        
            EntityGroup eg = ei.getEntityGroup();
            if (eg.isRelator() || eg.isAssoc()) {
                EntityList elist = eg.getEntityList();
                boolean bCreateParent = false;

                if (elist != null) {
                    bCreateParent = elist.isCreateParent();
                }

                if (bCreateParent) {
                    ei = (EntityItem) ei.getUpLink(0);
                } else {
                    ei = (EntityItem) ei.getDownLink(0);
                }
            }

            for (int h = 0; h < hh && h < dd; ++h) {
            	try {
            		processImportAttribute(ei, head[h], data[h]);
                } catch (EANBusinessRuleException br) {
                    if (bre==null){
						bre = br;
                    }else{
						//only one msg allowed per entityitem, attribute msgs will be accumulated
						// a null ptr will have first msg only
                    	bre.addException(br); // accumulate all msgs
                    }
                }
            }
        }else{
        	logger.log(Level.WARNING,"processImport not executed because head or data was null head: "+head + " data: "+data);
        }
        return bre;
    }
    /**
     * processImportAttribute
     * @param ei   EntityItem to add attributes to
     * @param head String attribute code
     * @param data String attribute value
     */
    private void processImportAttribute(EntityItem ei, String head, String data)
    throws EANBusinessRuleException
    {
    	logger.log(Level.FINER, ei.getKey() +" attrcode "+head+" data "+data);

        if (Routines.have(data)) {
            EANFoundation ean = null;
            try {
            	String key = head;
            	// allow this to work with any old tabbed files that may already have entitytype as key
            	if (!key.startsWith(ei.getEntityType()+":")){
            		key = ei.getEntityType()+":"+head;
            	}

                ean = ei.getEANObject(key);
            } catch (Exception ex) {
            	if(logger.getLevel().equals(Level.SEVERE)){
            		ex.printStackTrace();
            	}
                String msg = ex.getMessage();
                if (msg ==null || msg.length()==0){
                	msg = ex.getClass().getName()+" exception getting "+head;
                }

                EANBusinessRuleException bre = new LocalRuleException();
                bre.add(ei, msg);
                throw bre;
            }
            if (ean != null) {
                if (!((EANAttribute) ean).isEditable()) {
                	logger.log(Level.SEVERE,head + " not editable.");
                    EANBusinessRuleException bre = new LocalRuleException();
                    bre.add(ean, head + " not editable.");
                    throw bre;
                } else if (ean instanceof TextAttribute) {
                    ((TextAttribute) ean).put(data);
                } else if (ean instanceof LongTextAttribute) {
                    ((LongTextAttribute) ean).put(data);
                } else if (ean instanceof XMLAttribute) {
                	// make sure data is enclosed in tags
                	if (!data.trim().startsWith("<")){
                		data = "<pre>"+data+"</pre>";
                	}
                    ((XMLAttribute) ean).put(data);
                } else if ((ean instanceof SingleFlagAttribute) ||
                		(ean instanceof StatusAttribute) ||
                		(ean instanceof TaskAttribute))
                {
                	EANFlagAttribute att = (EANFlagAttribute) ean;
                    MetaFlag[] mFlags = (MetaFlag[]) att.get();
                    boolean foundFlag = false;
                    if (mFlags != null) {
                        int ii = mFlags.length;
                        // turn off all flags
                        for (int i = 0; i < ii; i++) {
                            if (mFlags[i] != null) {
                                mFlags[i].setSelected(false);
                            }
                        }
                        for (int i = 0; i < ii; ++i) {
                            if (mFlags[i] != null) {
                                if (flagEquals(mFlags[i], data)) {
                                	foundFlag = true;
                                    mFlags[i].setSelected(true);
                                    break;
                                }
                            }
                        }
                        att.put(mFlags);
                    }
                    if (!foundFlag){
                    	StringBuffer sb = new StringBuffer();
                    	logger.log(Level.SEVERE,head + " could not find matching flag for "+data);
                        EANBusinessRuleException bre = new LocalRuleException();
                        EANMetaFlagAttribute metaAttr = (EANMetaFlagAttribute)ei.getEntityGroup().getMetaAttribute(head);
                        if (metaAttr !=null){
                        	// find any filtering attributes to warn user, these are needed before this attr
                            for (int x = 0; x < metaAttr.getMetaFlagCount(); x++) {
                                MetaFlag mf = metaAttr.getMetaFlag(x);
                                if (mf.toString().equals(data) || mf.getFlagCode().equals(data)){
                                	for (int m=0; m<mf.getFilterCount();m++){
                                		MetaFlag mff = mf.getFilter(m);
                                		sb.append(mff.getParent().getKey()+" must be set first.");
                                		break;
                                	}
                                }
                            }
                        }

                        bre.add(ean, head + " could not find matching flag for "+data+" "+sb.toString());
                        throw bre;
                    }
                } else if (ean instanceof MultiFlagAttribute) {
                    MultiFlagAttribute att = (MultiFlagAttribute) ean;
                    int foundFlagCnt = 0;
                    String[] flags = Routines.getStringArray(data, FLAG_DELIMIT, true);
                    if (flags != null) {
                        HashMap<String,String> hash = loadKeys(flags);
                        if (hash != null) {
                            MetaFlag[] mFlags = (MetaFlag[]) att.get();
                            if (mFlags != null) {
                                int ii = mFlags.length;
                                // turn off all flags
                                for (int i = 0; i < ii; i++) {
                                    if (mFlags[i] != null) {
                                        mFlags[i].setSelected(false);
                                    }
                                }
                                for (int i = 0; i < ii && !hash.isEmpty(); i++) {
                                    if (mFlags[i] != null) {
                                        if (keyExists(hash, mFlags[i])) {
                                            mFlags[i].setSelected(true);
                                            foundFlagCnt++;
                                        }
                                    }
                                }
                                att.put(mFlags);
                            }
                        }

                        if (foundFlagCnt != flags.length ){
                        	StringBuffer sb = new StringBuffer();
                        	logger.log(Level.SEVERE,head + " could not find matching flags for "+data);
                        	EANBusinessRuleException bre = new LocalRuleException();
                        	EANMetaFlagAttribute metaAttr = (EANMetaFlagAttribute)ei.getEntityGroup().getMetaAttribute(head);
                        	if (metaAttr !=null){
                        		// find any filtering attributes to warn user, these are needed before this attr
                        		for (int x = 0; x < metaAttr.getMetaFlagCount(); x++) {
                        			MetaFlag mf = metaAttr.getMetaFlag(x);
                        			for (int k=0; k<flags.length; k++){
                        				if (mf.toString().equals(flags[k])|| mf.getFlagCode().equals(flags[k])){
                        					for (int m=0; m<mf.getFilterCount();m++){
                        						MetaFlag mff = mf.getFilter(m);
                        						sb.append("It is filtered by "+mff.getParent().getKey());
                        						break;
                        					}
                        				}
                        			}
                        		}
                        	}

                        	bre.add(ean, head + " could not find matching flag for "+data+" "+sb.toString());
                        	throw bre;
                        }
                    }else{   // end flags!=null
                    	logger.log(Level.SEVERE,head + " could not find matching flags for "+data);
                    	EANBusinessRuleException bre = new LocalRuleException();
                    	bre.add(ean, head + " could not find matching flags for "+data);
                    	throw bre;
                    }
                } else if (ean instanceof BlobAttribute) {
                	logger.log(Level.SEVERE," Blob not supported for "+head);
                    EANBusinessRuleException bre = new LocalRuleException();
                    bre.add(ean,head + " Blob attribute is not supported");
                    throw bre;
                } else {
                	logger.log(Level.SEVERE," not supported class: " + ean.getClass().getName());
                    EANBusinessRuleException bre = new LocalRuleException();
                    bre.add(ean,head +" "+ean.getClass().getName()+
                    		" is not supported");
                    throw bre;
                }
            }else{
                // some exceptions are caught when generating an attribute and this fails, so check for null now
            	EANBusinessRuleException bre = new LocalRuleException();
            	bre.add(ei, head+" not found in meta for "+ei.getEntityType());
            	throw bre;
            }
        }else{
        	logger.log(Level.FINER," not updating "+head+" data is null");
        }
    }

    private boolean flagEquals(MetaFlag flag, String data) {
        if (!caseSensitiveImport()) {
            return flag.getLongDescription().equalsIgnoreCase(data);
        }
        boolean matches = flag.getLongDescription().equals(data);
        if(!matches){
        	matches = flag.getFlagCode().equals(data);
        }
        return matches;//flag.getLongDescription().equals(data);
    }
    private boolean caseSensitiveImport() {
        return true;
    }
    private HashMap<String, String> loadKeys(String[] desc) {
        HashMap<String, String> out = null;
        if (desc != null) {
            out = new HashMap<String, String>();
            for (int i = 0; i < desc.length; ++i) {
                if (!caseSensitiveImport()) {
                    out.put(desc[i].toLowerCase(), desc[i]);
                } else {
                    out.put(desc[i], desc[i]);
                }
            }
        }
        return out;
    }
    private boolean keyExists(HashMap<String, String> map, MetaFlag flag) {
        String key = null;
        if (!caseSensitiveImport()) {
            key = flag.getLongDescription().toLowerCase();
        } else {
            key = flag.getLongDescription();
        }
   
        boolean match = map.remove(key) !=null;
        if(!match){
        	// look at flagcode
        	key = flag.getFlagCode();
        	match = map.remove(key) !=null;
        }
        
        return match;
    }
	/**
	 * getEntityGroup under edit
	 * @return
	 */
	public EntityGroup getEntityGroup() {
		return editEntityGroup;
	}
	/**
	 * get another copy of the RST, needed when verttable has entity deleted and needs an empty rst
	 * @return
	 */
	public RowSelectableTable getEntityGroupTable() {
		return editEntityGroup.getEntityGroupTable();
	}

	/**
	 * @return
	 */
	public String[] getSelectedKeys() {
		EditorPanel ed = getCurrentEditor();
		if (ed != null) {
			return ed.getSelectedKeys();
		}
		return null;
	}

	/**
	 * refreshMenu
	 */
	private void refreshMenu() {
		EANActionItem[] eai = getActionTree().getActionItemArray(ACTION_PURPOSE_MATRIX);
		EANActionSet actionSet = ((EANActionSet)getAction(EDITMTRX_ACTIONSET));
		if(actionSet!=null){
			actionSet.updateEANActions(eai);
		}

		eai = getActionTree().getActionItemArray(ACTION_PURPOSE_WORK_FLOW);
		actionSet = ((EANActionSet)getAction(EDITWF_ACTIONSET));
		if(actionSet!=null){
			actionSet.updateEANActions(eai);
		}

		EditorPanel ed = getCurrentEditor();
		if (ed != null) {
			ed.updateTableActions();
		}else{
			updateTableActions((RSTTable)null);
		}
	}

	/**
	 * hasData
	 * @return
	 */
	public boolean hasData() {
		RowSelectableTable rst = getEgRstTable();
		if (rst != null) {
			return (rst.getRowCount() > 0 && rst.getColumnCount() > 0);
		}
		return false;
	}

	/**
	 * set menuitem and toolbar button visibility
	 * @param key
	 * @param visible
	 */
	private void setVisible(String key, boolean visible) {
		JMenuItem m = findMenuItem(key);
		if (m!=null) {
			m.setVisible(visible);
		}
		for (int i = 0; i < MAX_EDITOR; ++i) {
			EditorPanel ed = edit[i];
			if (ed != null) {
				ed.setToolBarButtonVisible(key, visible);
			}
		}
	}

	/**
	 * refresh
	 */
	public void refreshEditor() {
		EditorPanel ed = getCurrentEditor();
		if (ed != null) {
			ed.refreshEditor();
		}
	}
	/**
	 * getEntityItemCount
	 * @return
	 */
	protected int getEntityItemCount() {
		return egRstTable.getRowCount();
	}

	/**
	 * loadNLSTree with this entity, called when editor is changed, or row selection changes in grid
	 * @param ei
	 */
	private void loadNLSTree(EntityItem ei) {
		if (isAllNLS()) {
			nlsTree.load(ei);
		}
	}

	/**
	 * reloadNLSTree using same entity, called after commit where nls may have been updated for the entity
	 */
	private void reloadNLSTree() {
		if (isAllNLS()) {
			nlsTree.reload();
		}
	}

	/**
	 * nls tree was clicked - change the readlanguage
	 * @param node
	 */
	public void onNLSTreeClick(Object treenode) {
		if (treenode instanceof NLSNode) {
			NLSNode node = (NLSNode)treenode;
			NLSItem nls = node.getNLSItem();
			if (nls != null) {
				EditorPanel ed = getCurrentEditor();
				if (ed != null) {
					if (ed.stopCellEditing()) {
						getProfile().setReadLanguage(nls);
						EACM.getEACM().setStatus(getProfile());
						ed.refreshEditor();
					}
				}
			}
		}
	}

	/**
	 * is the parent action allNLS?
	 * @return
	 */
	protected boolean isAllNLS() {
		return actionItem.isAllNLS();
	}

	private boolean isStandAlone(CreateActionItem action) {
		if (action.isStandAlone()) {
			String eType = action.getStandAloneEntityType();
			if (eType != null && nav != null) {
				String navType = nav.getEntityType(2);
				if (navType != null) {
					return eType.equals(navType);
				}else{
					// check group 0 for match
					navType = nav.getEntityType(0);
					if (navType != null) {
						return eType.equals(navType);
					}
				}
			}
		}
		return false;
	}
	/**
	 * hasFiltered
	 * @return
	 */
	private boolean hasFiltered() {
		EditorPanel ed = getCurrentEditor();
		if (ed != null) {
			return ed.hasFiltered();
		}
		return false;
	}

	/**
	 * hasHiddenAttributes
	 * @return
	 */
	private boolean hasHiddenAttributes() {
		EditorPanel ed = getCurrentEditor();
		if (ed != null) {
			return ed.hasHiddenAttributes();
		}
		return false;
	}


	/**
	 * get findable for find/replace
	 *
	 */
	public Findable getFindable() {
		EditorPanel ed = getCurrentEditor();
		if (ed != null) {
			return ed.getFindable();
		}
		return null;
	}

	/**
	 * setShouldRefresh
	 */
	private void setShouldRefresh() {
		if (nav != null) {
			if (isRefreshableAction()) {
				nav.setShouldRefresh(getParentKey(),navOPWGID);
			}
		} else {
			if (getParentTab() instanceof WUsedActionTab) {
				((WUsedActionTab) getParentTab()).setShouldRefresh(true);
			}
			if (getParentTab() instanceof MatrixActionBase) {
				((MatrixActionBase) getParentTab()).setShouldRefresh(true);
			}
		}
	}

	private boolean isRefreshableAction() {
		if (actionItem instanceof CreateActionItem) {
			return (((CreateActionItem) actionItem).isPeerCreate() ||
					isStandAlone((CreateActionItem) actionItem));
		}
		return true;
	}

    /**
	 * shouldRefresh
	 *
	 * @return
	 */
	public boolean shouldRefresh() {
		return false;
	}

	/**
	 * getEntityType
	 *
	 * @param i
	 * @return
	 */
	public String getEntityType(int i) {
		if (editEntityGroup != null) {
			if (i == 0) {
				return editEntityGroup.getEntityType();
			} else if (i == 1) {
				return editEntityGroup.getEntity1Type();
			} else if (i == 2) {
				return editEntityGroup.getEntity2Type();
			}
		}
		return null;
	}

	/**
	 * refresh - called when tab is selected, nls language can not be changed when on an edit tab
	 */
	public void refresh() {
		repaint();
	}
}

