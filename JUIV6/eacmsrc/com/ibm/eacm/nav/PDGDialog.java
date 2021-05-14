//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.nav;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.ibm.eacm.*;
import com.ibm.eacm.ui.EACMDialog;
import com.ibm.eacm.ui.EACMFrame;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.Profile;

import com.ibm.eacm.actions.*;
import com.ibm.eacm.mw.DBSwingWorker;
import com.ibm.eacm.mw.DBUtils;
import com.ibm.eacm.mw.RMIMgr;
import com.ibm.eacm.objects.EACMGlobals;
import com.ibm.eacm.objects.OutOfRangeException;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.table.RSTTable;


/******************************************************************************
* This is used to display the pdg dialog.
* @author Wendy Stimpson
*/
//$Log: PDGDialog.java,v $
//Revision 1.1  2012/09/27 19:39:14  wendy
//Initial code
//
public class PDGDialog extends EACMDialog implements EACMGlobals, ListSelectionListener
{
	private static final long serialVersionUID = 1L;
	/** object copyright statement */
	public static final String COPYRIGHT="(C) Copyright IBM Corp. 2012  All Rights Reserved.";
	/** cvs version */
	public static final String VERSION = "$Revision: 1.1 $";


	private JPanel pnlMain = new JPanel(null);
	private JPanel pnlBtn = null;
	private JButton btnEdit, btnCreate, btnCancel, btnDelete;
	private PDGTable table = null;
	private JScrollPane jsp = null;
	private EntityList list;

	private PDGTemplateActionItem pdgAction = null; // could be PDGActionItem or SPDGActionItem
	private DeleteActionItem dai = null;
	/**
	 */
	public PDGDialog(EntityList el, PDGTemplateActionItem pdg)  {
		//pdg.panel=EACM {0}
		super(EACM.getEACM(),"pdg.panel",JDialog.ModalityType.APPLICATION_MODAL);

		pdgAction = pdg;

		if(pdgAction instanceof PDGActionItem){
			dai = ((PDGActionItem)pdgAction).getPDGDeleteAction();
		}else{
			dai = ((SPDGActionItem)pdgAction).getPDGDeleteAction();
		}

		list = el;

		init();

		finishSetup(EACM.getEACM());

		setResizable(true);
	}

	/**
	 * init frame components
	 */
	private void init() {
		EntityGroup eg = null;
		for (int i=0; i<list.getEntityGroupCount(); ++i) {
			eg = list.getEntityGroup(i);
			if (eg.isDisplayable()) {
				break;
			}
		}
		setTitle(Utils.getResource("pdg.panel",eg.getLongDescription()));

		createActions();

		Dimension d = new Dimension(400,300);
		table = new PDGTable(eg.getEntityGroupTable(), list.getProfile());

		table.getSelectionModel().addListSelectionListener(this);

		btnCancel = new JButton(closeAction);
		btnEdit = new JButton(getAction(EDIT_ACTION));
		btnCreate = new JButton(getAction(CREATE_ACTION));

		btnCancel.setMnemonic((char)((Integer)btnCancel.getAction().getValue(Action.MNEMONIC_KEY)).intValue());
		btnCreate.setMnemonic((char)((Integer)btnCreate.getAction().getValue(Action.MNEMONIC_KEY)).intValue());
		btnEdit.setMnemonic((char)((Integer)btnEdit.getAction().getValue(Action.MNEMONIC_KEY)).intValue());

		jsp = new JScrollPane(table);
		jsp.setFocusable(false);
		jsp.setPreferredSize(d);
		jsp.setSize(d);

		table.resizeCells();

		pnlBtn = new JPanel(new GridLayout(1,0,5,5));
		pnlBtn.add(btnEdit);
		pnlBtn.add(btnCreate);
		pnlBtn.add(btnCancel);

		if(dai != null) {
			btnDelete = new JButton(getAction(DELETE_ACTION));
			pnlBtn.add(btnDelete);
			btnDelete.setMnemonic((char)((Integer)btnDelete.getAction().getValue(Action.MNEMONIC_KEY)).intValue());
		}

		GroupLayout layout = new GroupLayout(pnlMain);
		pnlMain.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		GroupLayout.ParallelGroup leftToRight = layout.createParallelGroup();
		leftToRight.addComponent(jsp);
		leftToRight.addComponent(pnlBtn, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);// this centers it

		GroupLayout.SequentialGroup topToBottom = layout.createSequentialGroup();
		topToBottom.addComponent(jsp);
		topToBottom.addComponent(pnlBtn);

		layout.setHorizontalGroup(leftToRight);
		layout.setVerticalGroup(topToBottom);
		getContentPane().add(pnlMain);
	}

	private void createActions(){
		closeAction = new CloseAction(this);
		addAction(new EditAction());
		addAction(new CreateAction());
		if(dai != null) {
			addAction(new DeleteAction());
		}
	}

	/**
	 * release memory
	 */
	public void dereference() {
		super.dereference();

		table.getSelectionModel().removeListSelectionListener(this);

		dai = null;
		pdgAction = null;

		btnCancel.setAction(null);
		btnCancel.removeAll();
		btnCancel.setUI(null);
		btnCancel = null;

		btnEdit.setAction(null);
		btnEdit.removeAll();
		btnEdit.setUI(null);
		btnEdit = null;

		btnCreate.setAction(null);
		btnCreate.removeAll();
		btnCreate.setUI(null);
		btnCreate = null;

		if(btnDelete !=null){
			btnDelete.setAction(null);
			btnDelete.removeAll();
			btnDelete.setUI(null);
			btnDelete = null;
		}

		table.dereference();
		table = null;

		pnlMain.removeAll();
		pnlMain.setUI(null);
		pnlMain = null;

		pnlBtn.removeAll();
		pnlBtn.setUI(null);
		pnlBtn = null;

		jsp.removeAll();
		jsp.setUI(null);
		jsp = null;

		list.dereference();
		list = null;
	}

	private class CloseAction extends CloseDialogAction
	{
		private static final long serialVersionUID = 1L;
		CloseAction(EACMDialog f) {
			super(f,CANCEL_ACTION);
		}
	}
	private class EditAction extends EACMAction
	{
		private static final long serialVersionUID = 1L;
		EditAction() {
			super(EDIT_ACTION);
		}
		/**
		 * derived classes should override for conditional checks needed before enabling
		 * @return
		 */
		protected boolean canEnable(){
			return pdgAction.getPDGEditAction()!=null && table.getSelectedRowCount()>0;
		}
		public void actionPerformed(ActionEvent e) {
			EANFoundation o = table.getSelectedItem();

	        if (o instanceof EntityItem) {
	        	disableActionsAndWait();
				EntityItem ei = (EntityItem)o;
				EntityItem[] aei = {ei};
				worker = new EditWorker(aei);
				RMIMgr.getRmiMgr().execute(worker);
	        }
		}
		private class EditWorker extends DBSwingWorker<EntityList, Void> {
			private EntityItem[] eia = null;
			EditWorker(EntityItem[] ei){
				eia = ei;
			}
			@Override
			public EntityList doInBackground() {
				EntityList el = null;
				try{
		        	EditActionItem cai = pdgAction.getPDGEditAction();
					el = DBUtils.getEdit(cai, eia,list.getProfile(), PDGDialog.this);
				}catch(Exception ex){ // prevent hang
					Logger.getLogger(NAV_PKG_NAME).log(Level.SEVERE,"Exception "+ex,ex);
				}finally{
					RMIMgr.getRmiMgr().complete(this);
					worker = null;
					if(isCancelled()){
						enableActionsAndRestore();
						eia = null;
					}
				}

				return el;
			}

			@Override
			public void done() {
				//this will be on the dispatch thread
				try {
					if(!isCancelled()){
						EntityList list2 = get();
						if (list2 !=null){
					        EntityItem theItem = null;
					        for (int i = 0; i < list2.getEntityGroupCount(); ++i) {
					            EntityGroup eg = list2.getEntityGroup(i);
					            if (eg.isDisplayable()) {
					                if (eg.getEntityItemCount() > 0) {
					                    EntityItem ei = eg.getEntityItem(0);
					                    theItem = ei;
					                }
					                break;
					            }
					        }
					        if(theItem==null){
					          	com.ibm.eacm.ui.UI.showErrorMessage(PDGDialog.this,"No displayable entity item found.");
					          	enableActionsAndRestore();
					        }else{
								EditInfo edi = new EditInfo(pdgAction, list2,theItem);
								closeAction.actionPerformed(null);
								// this causes this to be executed on the event dispatch thread
								// after actionPerformed returns
								SwingUtilities.invokeLater(edi);
					        }

						}
					}
				} catch (InterruptedException ignore) {}
				catch (java.util.concurrent.ExecutionException e) {
					listErr(e,"getting entity list");
				}finally{
					eia = null;
				}
			}
			public void notExecuted(){
				Logger.getLogger(NAV_PKG_NAME).log(Level.WARNING,"not executed");
				enableActionsAndRestore();
				worker = null;
			}
		}
	}
	private static class EditInfo implements Runnable{
		private EntityList elist;
		private EntityItem item;
		private PDGTemplateActionItem action;
		EditInfo(PDGTemplateActionItem a, EntityList l, EntityItem ei){
			elist = l;
			action = a;
			item = ei;
		}
		public void run() {
			EACMFrame edf = new EditPDGFrame(action,elist,item);
			edf.setVisible(true);
			elist = null;
			action = null;
		}

	}
	private class CreateAction extends EACMAction
	{
		private static final long serialVersionUID = 1L;
		CreateAction() {
			super(CREATE_ACTION);
		}
		/**
		 * derived classes should override for conditional checks needed before enabling
		 * @return
		 */
		protected boolean canEnable(){
			return pdgAction.getPDGCreateAction()!=null;
		}

		public void actionPerformed(ActionEvent e) {
			EntityGroup eg = list.getParentEntityGroup();
			EntityItem[] aei = eg.getEntityItemsAsArray();

	        if (aei != null && aei.length > 0) {
	    		disableActionsAndWait();
				worker = new CreateWorker(aei);
				RMIMgr.getRmiMgr().execute(worker);
	        }
		}
		private class CreateWorker extends DBSwingWorker<EntityList, Void> {
			private EntityItem[] eia = null;
			CreateWorker(EntityItem[] ei){
				eia = ei;
			}
			@Override
			public EntityList doInBackground() {
				EntityList el = null;
				try{
		        	CreateActionItem cai = pdgAction.getPDGCreateAction();
					el = DBUtils.create(cai, eia,list.getProfile(), PDGDialog.this);
				}catch(Exception ex){ // prevent hang
					Logger.getLogger(NAV_PKG_NAME).log(Level.SEVERE,"Exception "+ex,ex);
				}finally{
					RMIMgr.getRmiMgr().complete(this);
					worker = null;
					if(isCancelled()){
						enableActionsAndRestore();
						eia = null;
					}
				}

				return el;
			}

			@Override
			public void done() {
				//this will be on the dispatch thread
				try {
					if(!isCancelled()){
						EntityList list2 = get();
						if (list2 !=null){
					        EntityItem theItem = null;
					        for (int i = 0; i < list2.getEntityGroupCount(); ++i) {
					            EntityGroup eg = list2.getEntityGroup(i);
					            if (eg.isDisplayable()) {
					                if (eg.getEntityItemCount() > 0) {
					                    EntityItem ei = eg.getEntityItem(0);
					                    theItem = ei;
					                }
					                break;
					            }
					        }
					        if(theItem==null){
					          	com.ibm.eacm.ui.UI.showErrorMessage(PDGDialog.this,"No displayable entity item found.");
					          	enableActionsAndRestore();
					        }else{
								EditInfo edi = new EditInfo(pdgAction, list2,theItem);
								closeAction.actionPerformed(null);
								// this causes this to be executed on the event dispatch thread
								// after actionPerformed returns
								SwingUtilities.invokeLater(edi);
					        }

						}
					}
				} catch (InterruptedException ignore) {}
				catch (java.util.concurrent.ExecutionException e) {
					listErr(e,"getting entity list");
				}finally{
					eia = null;
				}
			}
			public void notExecuted(){
				Logger.getLogger(NAV_PKG_NAME).log(Level.WARNING,"not executed");
				enableActionsAndRestore();
				worker = null;
			}
		}
	}
	private class DeleteAction extends EACMAction
	{
		private static final long serialVersionUID = 1L;
		DeleteAction() {
			super(DELETE_ACTION);
		}
		public void actionPerformed(ActionEvent e) {
	        try {
				EntityItem[] aei = (EntityItem[]) table.getSelectedEntityItems(false, true);

				if (aei != null) {
					disableActionsAndWait();
					worker = new DeleteWorker(aei);
					RMIMgr.getRmiMgr().execute(worker);
				}
			} catch (OutOfRangeException _range) {
	            com.ibm.eacm.ui.UI.showFYI(PDGDialog.this,_range);
	        }
		}
		private void refreshTable(EntityList _el) {
	        EntityGroup eg = null;
	        if(list !=null){
	        	list.dereference();
	        }
	        list = _el;
			for (int i=0; i<_el.getEntityGroupCount(); ++i) {
				eg = _el.getEntityGroup(i);
				if (eg.isDisplayable()) {
					break;
				}
			}
			if (eg != null) {
				table.updateModel(eg.getEntityGroupTable(),list.getProfile());
			}
		}
		private class DeleteWorker extends DBSwingWorker<EntityList, Void> {
			private EntityItem[] eia = null;
			DeleteWorker(EntityItem[] ei){
				eia = ei;
			}
			@Override
			public EntityList doInBackground() {
				EntityList dellist = null;
				try{
					dai.setEntityItems(eia);
					if(DBUtils.doDelete(dai, list.getProfile(), PDGDialog.this)){
						EntityGroup eg = list.getParentEntityGroup();
						EntityItem[] aeiP = eg.getEntityItemsAsArray();
						NavActionItem navAction = pdgAction.getPDGNavAction();
						if(navAction ==null){
							dellist = DBUtils.getNavigateEntry(list.getProfile());
						}else{
							dellist = DBUtils.navigate(navAction, aeiP, list.getProfile(), PDGDialog.this);
						}
					}
				}catch(Exception ex){ // prevent hang
					Logger.getLogger(NAV_PKG_NAME).log(Level.SEVERE,"Exception "+ex,ex);
				}finally{
					RMIMgr.getRmiMgr().complete(this);
					worker = null;
				}

				return dellist;
			}

			@Override
			public void done() {
				//this will be on the dispatch thread
				try {
					if(!isCancelled()){
						EntityList list2 = get();
						if (list2 !=null){
							refreshTable(list2);
						}
					}
				} catch (InterruptedException ignore) {}
				catch (java.util.concurrent.ExecutionException e) {
					listErr(e,"getting entity list");
				}finally{
					eia = null;
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

	private class PDGTable extends RSTTable {
		private static final long serialVersionUID = 1L;

		PDGTable(RowSelectableTable rst, Profile prof) {
			super(rst,prof);

			init();

			if (isValidCell(0,0)) {
				setRowSelectionInterval(0,0);
				setColumnSelectionInterval(0,0);
			}
		}

		/**
		 * init
		 *
		 */
		protected void init() {
			super.init();
			setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		/**
		 * get the accessibility resource key
		 * @return
		 */
		protected String getAccessibilityKey() {
			return "accessible.pdgTable";
		}
	}

	public void valueChanged(ListSelectionEvent e) {
		enableActions();
	}
}