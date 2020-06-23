//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.nav;


import java.awt.Component;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;

import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.Profile;

import com.ibm.eacm.actions.*;
import com.ibm.eacm.edit.EditController;
import com.ibm.eacm.mw.DBSwingWorker;
import com.ibm.eacm.mw.DBUtils;
import com.ibm.eacm.mw.LoginMgr;
import com.ibm.eacm.mw.RMIMgr;
import com.ibm.eacm.objects.EANActionMenu;
import com.ibm.eacm.objects.OutOfRangeException;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.table.EntityGroupTable;
import com.ibm.eacm.ui.PickFrame;


/******************************************************************************
 * This is used to display the link picklist for navigate
 * @author Wendy Stimpson
 */
//$Log: NavPickFrame.java,v $
//Revision 1.2  2012/11/09 20:47:58  wendy
//check for null profile from getNewProfileInstance()
//
//Revision 1.1  2012/09/27 19:39:14  wendy
//Initial code
//
public class NavPickFrame extends PickFrame
{
	private static final long serialVersionUID = 1L;
	/** object copyright statement */
	public static final String COPYRIGHT="(C) Copyright IBM Corp. 2012  All Rights Reserved.";
	/** cvs version */
	public static final String VERSION = "$Revision: 1.2 $";
	private Navigate nav = null;


	/**
	 * @param n
	 * @param _list
	 */
	public NavPickFrame(Navigate n, EntityList _list)  {
		super(n.getNavController(),_list);
		nav = n;

		boolean bStatus = _list.isABRStatus();
		getAction(LINK_ACTION).setEnabled(false);
		getAction(COPYLINK_ACTION).setEnabled(false);
		getAction(REFRESH_ACTION).setEnabled(bStatus);

		if (bStatus) {
			setButtonsVisible(false);
		} else {
			updateActions();
		}

	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.ui.PickFrame#dereference()
	 */
	public void dereference(){
		super.dereference();
		nav = null;
	}

	/**
	 * actions need reference to navigate
	 * @return
	 */
	protected Navigate getNavigate() {return nav;}

	/**
	 * action needs selected items to link
	 * @return
	 * @throws OutOfRangeException
	 */
	protected EntityItem[] getSelectedEntityItems() throws OutOfRangeException {
		return tabbedPane.getSelectedEntityItems(false, true);
	}

	/**
	 * get actions for this entitygroup and update any link or linkcopy actions
	 */
	private void updateActions(){
		// add EANActionItems to the linkaction
		EntityGroup eg = tabbedPane.getEntityGroup();
		RowSelectableTable rst = eg.getActionGroupTable();
		EANActionItem[] ean = Utils.getExecutableActionItems(eg, rst);

		EANActionItem[] eanLink = null;
		EANActionItem[] eanLinkCopy = null;
		Vector<LinkActionItem> linkVct = new Vector<LinkActionItem>();
		Vector<LinkActionItem> linkcopyVct = new Vector<LinkActionItem>();
		ViewAction viewAction = (ViewAction)getAction(VIEW_ACTION);
		if(ean!=null){
			for (int i = 0; i < ean.length; ++i) {
				if (ean[i] instanceof LinkActionItem) {
					LinkActionItem lai = (LinkActionItem) ean[i];
					if(lai.canLink()){
						linkVct.add(lai);
					}
					if(lai.canLinkCopy()){
						linkcopyVct.add(lai);
					}
				} else if (ean[i] instanceof EditActionItem) {
					viewAction.setViewAction((EditActionItem) ean[i]);
				}
			}
		}

		if(linkVct.size()>0){
			eanLink = new EANActionItem[linkVct.size()];
			linkVct.copyInto(eanLink);
			linkVct.clear();
		}
		if(linkcopyVct.size()>0){
			eanLinkCopy = new EANActionItem[linkcopyVct.size()];
			linkcopyVct.copyInto(eanLinkCopy);
			linkcopyVct.clear();
		}

		//LINK_ACTION
		EANActionSet linkAction = (EANLinkActionPLSet)getAction(LINK_ACTION);
		linkAction.updateEANActions(eanLink);
		//linkcopy action
		linkAction = (EANCopyLinkActionPLSet)getAction(COPYLINK_ACTION);
		linkAction.updateEANActions(eanLinkCopy);

		enableActions();

		setButtonsVisible(true);
	}

	/* (non-Javadoc)
	 * called when disabling actions and wait cursor
	 * @see com.ibm.eacm.ui.EACMFrame#disableActions()
	 */
	protected void disableActions(){
		super.disableActions();

		tabbedPane.setEnabled(false);
		tabbedPane.getTable().setEnabled(false);
	}

	/* (non-Javadoc)
	 * called after actions are enabledAndRestored or when current selection changes
	 * @see com.ibm.eacm.ui.EACMFrame#refreshActions()
	 */
	protected void refreshActions() {
		tabbedPane.setEnabled(true);
		tabbedPane.getTable().setEnabled(true);

		//LINK_ACTION
		getAction(LINK_ACTION).setEnabled(tabbedPane.hasSelection());
		getAction(COPYLINK_ACTION).setEnabled(tabbedPane.hasSelection());

		getAction(REFRESH_ACTION).setEnabled(getEntityList().isABRStatus());

		//VIEW_ACTION
		getAction(VIEW_ACTION).setEnabled(true); //it will look at values before enabling
	}
	/**
	 * createToolbar using same actions as menus
	 */
	protected void createToolbar() {
		super.createToolbar();

		tBar.add(getAction(LINK_ACTION));
		tBar.add(getAction(COPYLINK_ACTION));
		tBar.add(getAction(VIEW_ACTION));
		tBar.addSeparator();
		tBar.add(getAction(FINDREP_ACTION));
      	tBar.add(getAction(FILTER_ACTION));
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.ui.PickFrame#createActionMenu()
	 */
	protected void createActionMenu() {
		JMenu actMnu = new JMenu(Utils.getResource(ACTIONS_MENU));
		actMnu.setMnemonic(Utils.getMnemonic(ACTIONS_MENU));

		actMnu.add(new EANActionMenu((EANActionSet)getAction(LINK_ACTION)));
		actMnu.add(new EANActionMenu((EANActionSet)getAction(COPYLINK_ACTION)));

		addLocalActionMenuItem(actMnu, REFRESH_ACTION);

		menubar.add(actMnu);
	}
	/**
	 * create all of the actions, they are shared between toolbar and menu
	 */
	protected void createActions() {
		super.createActions();

		//LINK_ACTION
		EACMAction act = new EANLinkActionPLSet(this); // there may be multiple linkactions, put them into a set
		addAction(act);

		addAction(new EANCopyLinkActionPLSet(this));
		addAction(new ViewAction());
		addAction(new RefreshAction());
	}

	private void setButtonsVisible(boolean b) {
		//LINK_ACTION
		EANActionSet linkAction = (EANLinkActionPLSet)getAction(LINK_ACTION);
		int linkcnt = linkAction.getActionCount();
		//linkcopy action
		linkAction = (EANCopyLinkActionPLSet)getAction(COPYLINK_ACTION);
		int linkcopycnt = linkAction.getActionCount();
		boolean bTableEmpty = !tabbedPane.hasSelection();
		if (b) {
			getAction(LINK_ACTION).setEnabled(linkcnt>0 && !bTableEmpty);
			setVisible(LINK_ACTION,linkcnt>0);

			getAction(COPYLINK_ACTION).setEnabled(linkcopycnt>0 && !bTableEmpty);
			setVisible(COPYLINK_ACTION,linkcopycnt>0);
		} else {
			getAction(LINK_ACTION).setEnabled(false);
			setVisible(LINK_ACTION,false);

			getAction(COPYLINK_ACTION).setEnabled(false);
			setVisible(COPYLINK_ACTION,false);
		}
	}


	public void valueChanged(ListSelectionEvent _lse) {
		super.valueChanged(_lse);
		if (!_lse.getValueIsAdjusting()) {
			int i = tabbedPane.getSelectedIndex();
			if(i==0){
				String[] strKeys = getSelectedViewItems();
				if (strKeys != null) {
					EntityGroupTable nt = tabbedPane.getTable();
					nt.highlight(strKeys);
				}
			}
			toggleMenu(i);
		}
	}
	private void toggleMenu(int _i) {
		if (menubar != null) {
			boolean b = (_i == 0);
			String editMenu = Utils.getResource(EDIT_MENU);
			String actionsMenu = Utils.getResource(ACTIONS_MENU);
			String tableMenu = Utils.getResource(TABLE_MENU);
			for (int i=0; i<menubar.getMenuCount(); i++) {
				JMenu amenu = menubar.getMenu(i);
				if(amenu.getText().equals(editMenu)||
						amenu.getText().equals(actionsMenu) ||
						amenu.getText().equals(tableMenu)){
					amenu.setEnabled(b);
				}
			}
		}
	}
	private String[] getSelectedViewItems() {
		if (tabbedPane.getTabCount() > 1) {
			Component c = tabbedPane.getComponentAt(1);
			if (c instanceof EditController) {
				return ((EditController) c).getSelectedKeys();
			}
		}
		return null;
	}

	private class ViewAction extends EACMAction {
		private static final long serialVersionUID = 1L;
		private EditActionItem viewAction = null;
		ViewAction() {
			super(VIEW_ACTION,KeyEvent.VK_V, Event.CTRL_MASK);
			putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("view.gif"));
		}

		void setViewAction(EditActionItem v){
			viewAction = v;
		}

		/* (non-Javadoc)
		 * @see com.ibm.eacm.actions.EACMAction#canEnable()
		 */
		 protected boolean canEnable(){
			return viewAction !=null && tabbedPane.hasSelection();
		 }
		 public void dereference(){
			 super.dereference();
			 viewAction=null;
		 }
		 public void actionPerformed(ActionEvent e) {
			 try{
				 worker = new EditWorker(tabbedPane.getSelectedEntityItems(false, true));
				 disableActionsAndWait();
				 RMIMgr.getRmiMgr().execute(worker);
			 } catch (OutOfRangeException _range) {
				 com.ibm.eacm.ui.UI.showFYI(NavPickFrame.this,_range);
			 }
		 }
		 class EditWorker extends DBSwingWorker<EntityList, Void> {
			 private  EntityItem [] eia = null;

			 EditWorker(EntityItem[] vk){
				 eia = vk;
			 }
			 @Override
			 public EntityList doInBackground() {
				 EntityList list = null;
				 try{
					 Profile prof = LoginMgr.getNewProfileInstance(getNavigate().getProfile());
					 if(prof!=null){
						 list = DBUtils.getEdit(viewAction, eia,
								 prof, getNavigate());
					 }
				 }catch(Exception ex){ // prevent hang
					Logger.getLogger(NAV_PKG_NAME).log(Level.SEVERE,"Exception "+ex,ex);
				 }finally{
					 RMIMgr.getRmiMgr().complete(this);
					 worker = null;
					 eia = null;
				 }

				 return list;
			 }

			 @Override
			 public void done() {
				 //this will be on the dispatch thread
				 try {
					 if(!isCancelled()){
						 EntityList list = get();
						 if(list!=null){
							 EditController ec = new EditController(list, nav);
							 dereferenceViewer();
							 tabbedPane.addTab(Utils.getResource("detView"), ec);
							 tabbedPane.setSelectedIndex(1);
						 }
					 }
				 } catch (InterruptedException ignore) {}
				 catch (java.util.concurrent.ExecutionException e) {
					 listErr(e,"getting entitylist");
				 }finally{
					 enableActionsAndRestore();
				 }
			 }
			 public void notExecuted(){
				 Logger.getLogger(NAV_PKG_NAME).log(Level.WARNING,"not executed");
				 enableActionsAndRestore();
				 worker = null;
			 }
		 }
		 void dereferenceViewer() {
			 while (tabbedPane.getTabCount() > 1) {
				 Component comp = tabbedPane.getComponentAt(1);
				 if (comp instanceof EditController) {
					 ((EditController) comp).dereference();
				 }
				 tabbedPane.removeTabAt(1);
			 }
		 }
	}

	private class RefreshAction extends EACMAction {
		private static final long serialVersionUID = 1L;
		RefreshAction() {
			super(REFRESH_ACTION,KeyEvent.VK_F5, 0);
		}

		/* (non-Javadoc)
		 * @see com.ibm.eacm.actions.EACMAction#canEnable()
		 */
		protected boolean canEnable(){
			return getEntityList() !=null && getEntityList().isABRStatus();
		}

		public void actionPerformed(ActionEvent e) {
			worker = new RefreshWorker();
			disableActionsAndWait();
			RMIMgr.getRmiMgr().execute(worker);
		}
		class RefreshWorker extends DBSwingWorker<EntityList, Void> {
			@Override
			public EntityList doInBackground() {
				EntityList list = null;
				try{
					EntityGroup parent = getEntityList().getParentEntityGroup();
					EntityItem[] aeiP = parent.getEntityItemsAsArray();
					list = DBUtils.navigate(((NavActionItem)getEntityList().getParentActionItem()), aeiP, getEntityList().getProfile(), NavPickFrame.this);
				}catch(Exception ex){ // prevent hang
					Logger.getLogger(NAV_PKG_NAME).log(Level.SEVERE,"Exception "+ex,ex);
				}finally{
					RMIMgr.getRmiMgr().complete(this);
					worker = null;
				}

				return list;
			}

			@Override
			public void done() {
				//this will be on the dispatch thread
				try {
					if(!isCancelled()){
						EntityList list = get();
						if(list!=null){
							EntityList prev = tabbedPane.getEntityList();
							if(prev!=null){
								prev.dereference();
							}
				            tabbedPane.load(list);
						}
					}
				} catch (InterruptedException ignore) {}
				catch (java.util.concurrent.ExecutionException e) {
					listErr(e,"getting entitylist");
				}finally{
					enableActionsAndRestore();
				}
			}
			public void notExecuted(){
				Logger.getLogger(NAV_PKG_NAME).log(Level.WARNING,"not executed");
				enableActionsAndRestore();
				worker = null;
			}
		}

	}
}
