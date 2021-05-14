//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.nav;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


import COM.ibm.eannounce.objects.DomainException;
import COM.ibm.eannounce.objects.EANBusinessRuleException;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EntityList;
import COM.ibm.eannounce.objects.PDGActionItem;
import COM.ibm.eannounce.objects.PDGCollectInfoList;
import COM.ibm.eannounce.objects.PDGTemplateActionItem;
import COM.ibm.eannounce.objects.SBRException;

import com.ibm.eacm.*;
import com.ibm.eacm.actions.*;

import com.ibm.eacm.mw.DBSwingWorker;
import com.ibm.eacm.mw.RMIMgr;

import com.ibm.eacm.objects.*;
import com.ibm.eacm.table.PDGEditTable;
import com.ibm.eacm.ui.EACMFrame;

import com.ibm.eacm.nav.PDGEditor;

/******************************************************************************
 * This is used to display the edit pdg frame.
 * @author Wendy Stimpson
 */
//$Log: EditPDGFrame.java,v $
//Revision 1.1  2012/09/27 19:39:14  wendy
//Initial code
//

public class EditPDGFrame extends EACMFrame implements ListSelectionListener
{
	private static final long serialVersionUID = 1L;
	/** object copyright statement */
	public static final String COPYRIGHT="(C) Copyright IBM Corp. 2012  All Rights Reserved.";
	/** cvs version */
	public static final String VERSION = "$Revision: 1.1 $";
	private static final String GENDATA_ACTION = "genDat";
	private static final String VIEWDATA_ACTION ="viewMD";
	private static final String STEP_ACTION = "step";

	private JPanel pnlMain = new JPanel(null);
	private JPanel m_pBtn = new JPanel(new GridLayout(1, 4));
	private JButton btnSave = null;
	private JButton btnCancel = null;
	private JButton btnGenData = null;
	private JButton btnViewData = null;
	private JButton btnStep = null;

	private PDGTemplateActionItem pai = null;
	private EntityList list = null;

	private PDGEditor pdgEditor = null;
	private EntityItem pdgItem;

	/**
	 * get unique id for this frame
	 * getUID()
	 * @return
	 */
	public String getUID() {
		String actionkey = pai.getActionItemKey();
		return actionkey;
	}

	/**
	 * @param ai
	 * @param el
	 */
	public EditPDGFrame(PDGTemplateActionItem ai, EntityList el, EntityItem item)  {
		super("pdg.panel", ai.toString());
		pai = ai;
		pdgItem = item;

		closeAction = new CloseAction(this);

		list = el;

		init();

		enableActions();

		finishSetup(EACM.getEACM());
		setResizable(true);
	}

	/**
	 * release memory
	 */
	public void dereference() {
		pdgEditor.getTable().unregisterEACMAction(getAction(COPY_ACTION),KeyEvent.VK_V, Event.CTRL_MASK);
		pdgEditor.getTable().unregisterEACMAction(getAction(PASTE_ACTION),KeyEvent.VK_C, Event.CTRL_MASK);

		pdgEditor.getTable().getSelectionModel().removeListSelectionListener(this);
		pdgEditor.getTable().getColumnModel().getSelectionModel().removeListSelectionListener(this);

		super.dereference(); // this clears all actions

		pdgEditor.dereference();
		pdgEditor = null;

		pai = null;

		m_pBtn.removeAll();
		m_pBtn.setUI(null);
		m_pBtn = null;

		btnSave.setAction(null);
		btnSave.setUI(null);
		btnSave = null;

		btnViewData.setAction(null);
		btnViewData.setUI(null);
		btnViewData = null;

		btnGenData.setAction(null);
		btnGenData.setUI(null);
		btnGenData = null;

		list.dereference();
		list = null;

		btnCancel.setAction(null);
		btnCancel.setUI(null);
		btnCancel = null;

		if (btnStep!=null){
			btnStep.setAction(null);
			btnStep.setUI(null);
			btnStep = null;
		}

		pnlMain.removeAll();
		pnlMain.setUI(null);
		pnlMain = null;

		pdgItem = null;
	}
	/**
	 * init frame components
	 */
	private void init() {
		pdgEditor = new PDGEditor(list.getProfile(),pdgItem);

		createActions();

		createMenuBar();

		pdgEditor.getTable().getSelectionModel().addListSelectionListener(this);
		pdgEditor.getTable().getColumnModel().getSelectionModel().addListSelectionListener(this);

		btnSave = new JButton(getAction(SAVE_ACTION));
		btnSave.setMnemonic((char)((Integer)btnSave.getAction().getValue(Action.MNEMONIC_KEY)).intValue());

		btnGenData = new JButton(getAction(GENDATA_ACTION));
		btnGenData.setMnemonic((char)((Integer)btnGenData.getAction().getValue(Action.MNEMONIC_KEY)).intValue());

		btnViewData = new JButton(getAction(VIEWDATA_ACTION));
		btnViewData.setMnemonic((char)((Integer)btnViewData.getAction().getValue(Action.MNEMONIC_KEY)).intValue());

		btnCancel = new JButton(closeAction);
		btnCancel.setMnemonic((char)((Integer)btnCancel.getAction().getValue(Action.MNEMONIC_KEY)).intValue());

		// must override JComponent bindings or EACM copy and paste action are not invoked
		pdgEditor.getTable().registerEACMAction(getAction(COPY_ACTION), KeyEvent.VK_C, Event.CTRL_MASK);
		pdgEditor.getTable().registerEACMAction(getAction(PASTE_ACTION), KeyEvent.VK_V, Event.CTRL_MASK);


		((EntityDataAction)getAction(ENTITYDATA_ACTION)).setTable(pdgEditor.getTable());
		//COPY_ACTION
		((CopyAction)getAction(COPY_ACTION)).setTable(pdgEditor.getTable());
		//PASTE_ACTION
		((PasteAction)getAction(PASTE_ACTION)).setTable(pdgEditor.getTable());

		m_pBtn.add(btnSave);
		m_pBtn.add(btnGenData);
		m_pBtn.add(btnViewData);

		if(pai instanceof PDGActionItem){
			PDGActionItem pdg = (PDGActionItem)pai;
			if (pdg.isCollectInfo()) {
				btnStep = new JButton(getAction(STEP_ACTION));
				String txt = pdg.getStepDescription(pdg.getCollectStep());
				if (txt != null) {
					btnStep.setText(txt);
					setMnemonic(txt.toCharArray());
				}
				m_pBtn.add(btnStep);
			}

			pdg.resetPDGCollectInfo();
		}

		m_pBtn.add(btnCancel);

		GroupLayout layout = new GroupLayout(pnlMain);
		pnlMain.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		GroupLayout.ParallelGroup leftToRight = layout.createParallelGroup();
		leftToRight.addComponent(pdgEditor);
		leftToRight.addComponent(m_pBtn);

		GroupLayout.SequentialGroup topToBottom = layout.createSequentialGroup();
		topToBottom.addComponent(pdgEditor);
		topToBottom.addComponent(m_pBtn,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
				GroupLayout.PREFERRED_SIZE); // prevent vertical growth

		layout.setHorizontalGroup(leftToRight);
		layout.setVerticalGroup(topToBottom);

//		getRootPane().setDefaultButton(btnFind); this interferes with ctrl-enter to open editor

		getContentPane().add(pnlMain);
	}
	private void setMnemonic(char[] _c) {
		char char0 = Utils.getMnemonic(CANCEL_ACTION);
		char char1 = Utils.getMnemonic(GENDATA_ACTION);
		char char2 = Utils.getMnemonic(VIEWDATA_ACTION);
		for (int i = 0; i < _c.length; ++i) {
			if (_c[i] != char0) {
				if (_c[i] != char1) {
					if (_c[i] != char2) {
						btnStep.setMnemonic(_c[i]);
						return;
					}
				}
			}
		}
	}
	/**
	 * create all of the actions
	 */
	private void createActions() {
		//VIEWDATA_ACTION
		addAction(new ViewDataAction());
		//GENDATA_ACTION
		addAction(new GenDataAction());

		addAction(new StepAction());

		//ATTRHELP_ACTION
		addAction(new HelpAction());

		addAction(new RefreshAction());

		addAction(new DeactivateAttrAction());

		//SAVE_ACTION
		addAction(new SaveAction());

		//ENTITYDATA_ACTION
		addAction(new EntityDataAction(null));

		//COPY_ACTION
		addAction(new CopyAction(null));
		//PASTE_ACTION
		addAction(new PasteAction(null));
	}
	/**
	 * createMenuBar
	 */
	private void createMenuBar() {
		menubar = new JMenuBar();
		JMenu mnuFile = new JMenu(Utils.getResource(FILE_MENU));
		mnuFile.setMnemonic(Utils.getMnemonic(FILE_MENU));

		JMenuItem mi = mnuFile.add(closeAction);
		mi.setVerifyInputWhenFocusTarget(false);

		addGlobalActionMenuItem(mnuFile, BREAK_ACTION);

		menubar.add(mnuFile);


		JMenu mnuTbl = new JMenu(Utils.getResource(HELP_MENU));
		mnuTbl.setMnemonic(Utils.getMnemonic(HELP_MENU));

		addLocalActionMenuItem(mnuTbl, ATTRHELP_ACTION);
		addLocalActionMenuItem(mnuTbl, ENTITYDATA_ACTION);
		menubar.add(mnuTbl);

		mnuTbl = new JMenu(Utils.getResource(EDIT_MENU));
		mnuTbl.setMnemonic(Utils.getMnemonic(EDIT_MENU));
		addLocalActionMenuItem(mnuTbl, COPY_ACTION);
		addLocalActionMenuItem(mnuTbl, PASTE_ACTION);
		addLocalActionMenuItem(mnuTbl, DEACTIVATEATTR_ACTION);
		mnuTbl.addSeparator();
		addLocalActionMenuItem(mnuTbl, REFRESH_ACTION);
		menubar.add(mnuTbl);

		setJMenuBar(menubar);
	}

	/* (non-Javadoc)
	 * when column selection changes this is notified
	 * also called when tablemodel is changed
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	public void valueChanged(ListSelectionEvent lse) {
		if (!lse.getValueIsAdjusting()) {
			enableActions();
		}
	}
	public void disableActionsAndWait(){
		super.disableActionsAndWait();
		if (pdgEditor !=null){
			pdgEditor.setEnabled(false);
		}
	}
	public void enableActionsAndRestore(){
		super.enableActionsAndRestore();
		if (pdgEditor !=null){
			pdgEditor.setEnabled(true);
		}
	}


	//================================================================
	//SAVE_ACTION
	private class SaveAction extends EACMAction {
		private static final long serialVersionUID = 1L;
		SaveAction() {
			super(SAVE_ACTION);
			setEnabled(true);
		}

		public void actionPerformed(ActionEvent e) {
			// save any edit
			pdgEditor.getTable().stopCellEditing();
			if(pdgEditor.getTable().hasChanges()){
				disableActionsAndWait();

				worker = new SaveWorker();
				RMIMgr.getRmiMgr().execute(worker);
			}
		}
		private class SaveWorker extends DBSwingWorker<Exception, Void> {
			@Override
			public Exception doInBackground() {
				try{
					if (pdgItem.getEntityID() < 0) {
						EntityGroup eg = list.getParentEntityGroup();
						pdgItem.setParentEntityItem(eg.getEntityItem(0));
					}
					pdgEditor.getTable().commit();
				}catch(Exception x){
					return x;
				}finally{
					RMIMgr.getRmiMgr().complete(this);
					worker = null;
				}
				return null;

			}

			@Override
			public void done() {
				RefreshAction refresh = null;
				//this will be on the dispatch thread
				try {
					if(!isCancelled()){
						Exception ex = get();
						if (ex !=null){
							if (ex instanceof EANBusinessRuleException) {
								EANBusinessRuleException bre = (EANBusinessRuleException) ex;
								pdgEditor.moveToError(bre);
							}

							com.ibm.eacm.ui.UI.showErrorMessage(pdgEditor,ex.toString());
						}else{
							// refresh the entity
							refresh = (RefreshAction)getAction(REFRESH_ACTION);
							refresh.actionPerformed(null);
						}
					}
				} catch (InterruptedException ignore) {}
				catch (java.util.concurrent.ExecutionException e) {
					listErr(e,"saving data");
				}finally{
					if(refresh==null){
						enableActionsAndRestore();
					}
				}
			}
			public void notExecuted(){
				Logger.getLogger(NAV_PKG_NAME).log(Level.WARNING,"not executed");
				enableActionsAndRestore();
				worker = null;
			}
		}

	}
	//VIEWDATA_ACTION
	private class ViewDataAction extends EACMAction {
		private static final long serialVersionUID = 1L;
		ViewDataAction() {
			super(VIEWDATA_ACTION);
			setEnabled(true);
		}

		public void actionPerformed(ActionEvent e) {
			// save any edit
			pdgEditor.getTable().stopCellEditing();
			disableActionsAndWait();

			worker = new ViewDataWorker();
			RMIMgr.getRmiMgr().execute(worker);
		}
		private class ViewDataWorker extends DBSwingWorker<Exception, Void> {
			private byte[] ba=null;
			@Override
			public Exception doInBackground() {
				try{
					if(pdgEditor.getTable().hasChanges()){
						if (pdgItem.getEntityID() < 0) {
							EntityGroup eg = list.getParentEntityGroup();
							pdgItem.setParentEntityItem(eg.getEntityItem(0));
						}
						pdgEditor.getTable().commit();
					}

					ba = viewMissingData();
				}catch(Exception x){
					return x;
				}finally{
					RMIMgr.getRmiMgr().complete(this);
					worker = null;
				}
				return null;

			}

			@Override
			public void done() {
				RefreshAction refresh = null;
				//this will be on the dispatch thread
				try {
					if(!isCancelled()){
						Exception ex = get();
						if (ex !=null){
							if (ex instanceof EANBusinessRuleException) {
								EANBusinessRuleException bre = (EANBusinessRuleException) ex;
								pdgEditor.moveToError(bre);
							}

							com.ibm.eacm.ui.UI.showErrorMessage(pdgEditor,ex.toString());
						}else{
							// refresh the entity
							refresh = (RefreshAction)getAction(REFRESH_ACTION);
							refresh.actionPerformed(null);
							if (ba != null&& ba.length>0) {
								com.ibm.eacm.ui.UI.showMessage(pdgEditor,"information-title",
										JOptionPane.INFORMATION_MESSAGE, "information-acc", new String(ba));
							}
							ba=null;
						}
					}
				} catch (InterruptedException ignore) {}
				catch (java.util.concurrent.ExecutionException e) {
					listErr(e,"generating data");
				}finally{
					if(refresh==null){
						enableActionsAndRestore();
					}
				}
			}
			public void notExecuted(){
				Logger.getLogger(NAV_PKG_NAME).log(Level.WARNING,"not executed");
				enableActionsAndRestore();
				worker = null;
			}
			private byte[] viewMissingData() {
				byte[] baReturn = null;
		        try {
		    		pai.setEntityItem(pdgItem);
		    		baReturn = pai.rviewMissingData(ro(), list.getProfile());
		        } catch (SBRException _e) {
		           	if(pai instanceof PDGActionItem){
		        		if (_e.resetPDGCollectInfo()) {
		        			((PDGActionItem)pai).resetPDGCollectInfo();
		        		}
		        	}
		            com.ibm.eacm.ui.UI.showErrorMessage(pdgEditor,_e.toString());
		        } catch (Exception exc) {
					if(RMIMgr.shouldTryReconnect(exc)){	// try to reconnect
						if (RMIMgr.getRmiMgr().reconnectMain()) {
							try{
								baReturn = pai.rviewMissingData(ro(), list.getProfile());
							}catch (SBRException _e) {
						       	if(pai instanceof PDGActionItem){
					        		if (_e.resetPDGCollectInfo()) {
					        			((PDGActionItem)pai).resetPDGCollectInfo();
					        		}
					        	}
					            com.ibm.eacm.ui.UI.showErrorMessage(pdgEditor,_e.toString());
					        }catch(Exception e){
								if(RMIMgr.shouldPromptUser(e)){// show user msg and ask what to do
									if (com.ibm.eacm.ui.UI.showMWExcPrompt(pdgEditor, e) == RETRY) {
										baReturn = viewMissingData();
									}
								}else{
									com.ibm.eacm.ui.UI.showException(pdgEditor,e, "mw.err-title");
								}
							}
						}else{	// reconnect failed
							com.ibm.eacm.ui.UI.showException(pdgEditor,exc, "mw.err-title");
						}
					}else{ // show user msg and ask what to do
						if(RMIMgr.shouldPromptUser(exc)){
							if (com.ibm.eacm.ui.UI.showMWExcPrompt(pdgEditor, exc) == RETRY) {
								baReturn = viewMissingData();
							}// else user decide to ignore or exit
						}else{
							com.ibm.eacm.ui.UI.showException(pdgEditor,exc, "mw.err-title");
						}
					}
				}

		        return baReturn;
		    }

		}

	}
	//GENDATA_ACTION
	private class GenDataAction extends EACMAction {
		private static final long serialVersionUID = 1L;
		GenDataAction() {
			super(GENDATA_ACTION);
			setEnabled(true);
		}

		public void actionPerformed(ActionEvent e) {
			// save any edit
			pdgEditor.getTable().stopCellEditing();
			disableActionsAndWait();

			worker = new GenDataWorker();
			RMIMgr.getRmiMgr().execute(worker);
		}
		private class GenDataWorker extends DBSwingWorker<Exception, Void> {
			@Override
			public Exception doInBackground() {
				try{
					if(pdgEditor.getTable().hasChanges()){
						if (pdgItem.getEntityID() < 0) {
							EntityGroup eg = list.getParentEntityGroup();
							pdgItem.setParentEntityItem(eg.getEntityItem(0));
						}
						pdgEditor.getTable().commit();
					}

					genPDGData();
				}catch(Exception x){
					return x;
				}finally{
					RMIMgr.getRmiMgr().complete(this);
					worker = null;
				}
				return null;

			}
			private void genPDGData() {
		        try {
		    		pai.setEntityItem(pdgItem);
		            pai.rexec(ro(), list.getProfile());
		        }catch(DomainException de) {
		        	com.ibm.eacm.ui.UI.showException(pdgEditor,de);
		        	de.dereference();
		        }catch (SBRException _e) {
		        	if(pai instanceof PDGActionItem){
		        		if (_e.resetPDGCollectInfo()) {
		        			((PDGActionItem)pai).resetPDGCollectInfo();
		        		}
		        	}
		            com.ibm.eacm.ui.UI.showErrorMessage(pdgEditor,_e.toString());
		        } catch (Exception _ex) {
		        	com.ibm.eacm.ui.UI.showException(pdgEditor, _ex);
		        }
		    }

			@Override
			public void done() {
				RefreshAction refresh = null;
				//this will be on the dispatch thread
				try {
					if(!isCancelled()){
						Exception ex = get();
						if (ex !=null){
							if (ex instanceof EANBusinessRuleException) {
								EANBusinessRuleException bre = (EANBusinessRuleException) ex;
								pdgEditor.moveToError(bre);
							}

							com.ibm.eacm.ui.UI.showErrorMessage(pdgEditor,ex.toString());
						}else{
							// refresh the entity
							refresh = (RefreshAction)getAction(REFRESH_ACTION);
							refresh.actionPerformed(null);
						}
					}
				} catch (InterruptedException ignore) {}
				catch (java.util.concurrent.ExecutionException e) {
					listErr(e,"generating data");
				}finally{
					if(refresh==null){
						enableActionsAndRestore();
					}
				}
			}
			public void notExecuted(){
				Logger.getLogger(NAV_PKG_NAME).log(Level.WARNING,"not executed");
				enableActionsAndRestore();
				worker = null;
			}
		}
	}
	//================================================================
	//STEP_ACTION
	private class StepAction extends EACMAction {
		private static final long serialVersionUID = 1L;
		StepAction() {
			super(STEP_ACTION);
			setEnabled(pai instanceof PDGActionItem);
		}

		public void actionPerformed(ActionEvent e) {
			// save any edit
			pdgEditor.getTable().stopCellEditing();
			disableActionsAndWait();

			worker = new StepWorker();
			RMIMgr.getRmiMgr().execute(worker);
		}
		private class StepWorker extends DBSwingWorker<PDGCollectInfoList, Void> {
			private Exception commitExc;
			@Override
			public PDGCollectInfoList doInBackground() {
				PDGCollectInfoList cl = null;
				try{
					if(pdgEditor.getTable().hasChanges()){
						if (pdgItem.getEntityID() < 0) {
							EntityGroup eg = list.getParentEntityGroup();
							pdgItem.setParentEntityItem(eg.getEntityItem(0));
						}
						pdgEditor.getTable().commit();
					}


					if (((PDGActionItem)pai).isCollectParentNavInfo()) {
						try {
							Object[] ao = PDGEditTable.getParentInformationAtLevel(((PDGActionItem)pai).getParentNavInfoLevel());
							if (ao.length == 2) {
								String strEntityType = (String)ao[0];
								int[] aiEntityID = (int[])ao[1];
								EntityItem[] aei = new EntityItem[aiEntityID.length];
								for (int i=0; i < aiEntityID.length; i++) {
									int iEntityID = aiEntityID[i];
									EntityItem ei = new EntityItem(null, list.getProfile(), strEntityType, iEntityID);
									aei[i] = ei;
								}
								((PDGActionItem)pai).setParentNavInfo(aei);
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}

					pai.setEntityItem(pdgItem);
					cl = ((PDGActionItem)pai).collectInfo(((PDGActionItem)pai).getCollectStep());
					if (cl == null) {
						try {
							cl = ((PDGActionItem)pai).collectInfo(null, RMIMgr.getRmiMgr().getRemoteDatabaseInterface(), list.getProfile(),
									((PDGActionItem)pai).getCollectStep());
						} catch (Exception ex) {
							ex.printStackTrace();
							com.ibm.eacm.ui.UI.showErrorMessage(pdgEditor,ex.toString());
						}
					}
				}catch(Exception x){
					commitExc=x;
					Logger.getLogger(NAV_PKG_NAME).log(Level.SEVERE,"Exception "+x,x);
				}finally{
					RMIMgr.getRmiMgr().complete(this);
					worker = null;
				}
				return cl;

			}

			@Override
			public void done() {
				RefreshAction refresh = null;
				//this will be on the dispatch thread
				try {
					if(!isCancelled()){
						PDGCollectInfoList cl = get();
						if (cl ==null){
							if(commitExc!=null){
								if (commitExc instanceof EANBusinessRuleException) {
									EANBusinessRuleException bre = (EANBusinessRuleException) commitExc;
									pdgEditor.moveToError(bre);
								}

								com.ibm.eacm.ui.UI.showErrorMessage(pdgEditor,commitExc.toString());
								commitExc = null;
							}
						}else{
							PDGInfoDialog pid = new PDGInfoDialog(cl, (PDGActionItem)pai,
									pdgItem.getEntityItemTable(),null);

							pid.setVisible(true);

							// refresh the entity
							refresh = (RefreshAction)getAction(REFRESH_ACTION);
							refresh.actionPerformed(null);
						}
					}
				} catch (InterruptedException ignore) {}
				catch (java.util.concurrent.ExecutionException e) {
					listErr(e,"step data");
				}finally{
					if(refresh==null){
						enableActionsAndRestore();
					}
				}
			}
			public void notExecuted(){
				Logger.getLogger(NAV_PKG_NAME).log(Level.WARNING,"not executed");
				enableActionsAndRestore();
				worker = null;
			}
		}
	}

	//================================================================
	private class RefreshAction extends EACMAction
	{
		private static final long serialVersionUID = 1L;
		RefreshAction() {
			super(REFRESH_ACTION,KeyEvent.VK_F5, 0);
		}
		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			pdgEditor.getTable().cancelCurrentEdit();
			disableActionsAndWait();
			worker = new RefreshWorker();
			RMIMgr.getRmiMgr().execute(worker);
		}
		private class RefreshWorker extends DBSwingWorker<EntityItem, Void> {
			@Override
			public EntityItem doInBackground() {
				EntityItem ei=null;
				try{
					EntityGroup eg = list.getEntityGroup(pdgItem.getEntityType());
					if (eg.isRelator() || eg.isAssoc()) {
						EntityItem eiD = (EntityItem) pdgItem.getDownLink(0);
						eg = list.getEntityGroup(eiD.getEntityType());
						pdgItem.removeDownLink(eiD);
						ei = ro().refreshEntityItem(list.getProfile(), eg, eiD);
					} else {
						ei = ro().refreshEntityItem(list.getProfile(), eg, pdgItem);
					}
				}catch(Exception ex){ // prevent hang
					Logger.getLogger(NAV_PKG_NAME).log(Level.SEVERE,"Exception "+ex,ex);
				}finally{
					RMIMgr.getRmiMgr().complete(this);
					worker = null;
				}
				return ei;
			}

			@Override
			public void done() {
				//this will be on the dispatch thread
				try {
					if(!isCancelled()){
						EntityItem ei = get();
						if (ei !=null){
							EntityGroup eg = list.getEntityGroup(pdgItem.getEntityType());
							if (eg.isRelator() || eg.isAssoc()) {
								pdgItem.putDownLink(ei);
							} else {
								pdgItem = ei;
							}
							//update the table model
							pdgEditor.getTable().updateModel(pdgItem);
							pdgEditor.getTable().resizeCells();
						}
					}
				} catch (InterruptedException ignore) {}
				catch (java.util.concurrent.ExecutionException e) {
					listErr(e,"getting refreshed entity");
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
	//================================================================
	private class HelpAction extends EACMAction
	{
		private static final long serialVersionUID = 1L;
		HelpAction() {
			super(ATTRHELP_ACTION,KeyEvent.VK_F1, 0);
		}
		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			// save any edit
			pdgEditor.getTable().stopCellEditing();

			String txt = pdgEditor.getTable().getHelpText();
			// split it at 80 char boundaries
			txt = Routines.addLineWraps(txt, 80);

			com.ibm.eacm.ui.UI.showFYI(pdgEditor,(txt != null && txt.trim().length()>0) ? txt : Utils.getResource("nia"));
		}
	}
	//================================================================
	private class CloseAction extends CloseFrameAction
	{
		private static final long serialVersionUID = 1L;
		CloseAction(EACMFrame f) {
			super(f,CANCEL_ACTION);
		}

		public void actionPerformed(ActionEvent e) {
			// user wants to deactivate.. cancel any edit
			pdgEditor.getTable().cancelCurrentEdit();
	        if (pdgEditor.getTable().hasChanges()) {
	        	pdgEditor.getTable().getRSTable().rollback();
            }
			pdgEditor.getTable().unlockTable();
			super.actionPerformed(e);
		}
	}
	private class DeactivateAttrAction extends EACMAction //implements PropertyChangeListener
	{
		private static final long serialVersionUID = 1L;

		/**
		 * constructor
		 */
		public DeactivateAttrAction() {
			super(DEACTIVATEATTR_ACTION,KeyEvent.VK_DELETE, 0);
			//	pdgEditor.getTable().addPropertyChangeListener(DATACHANGE_PROPERTY,this);
			super.setEnabled(false);
		}
		public void dereference(){
			super.dereference();
			//pdgEditor.getTable().removePropertyChangeListener(DATACHANGE_PROPERTY,this);
		}

		/**
		 * derived classes should override for conditional checks needed before enabling
		 * @return
		 */
		protected boolean canEnable(){
			return pdgEditor.getTable().hasEditableAttrSelected();
		}
		/* (non-Javadoc)
		 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
		 * /
		public void propertyChange(PropertyChangeEvent event) {
			if(event.getPropertyName().equals(DATACHANGE_PROPERTY)) {
				setEnabled(true);
			}
		}
		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			// user wants to deactivate.. cancel any edit
			pdgEditor.getTable().cancelCurrentEdit();

			//deactivate the attribute
			pdgEditor.getTable().deactivateAttribute();
		}
	}
}