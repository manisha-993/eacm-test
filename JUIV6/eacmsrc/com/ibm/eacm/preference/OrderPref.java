//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.preference;


import com.ibm.eacm.*;
import com.ibm.eacm.mw.DBSwingWorker;
import com.ibm.eacm.mw.RMIMgr;

import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;

import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.objects.*;
import com.ibm.eacm.tabs.TabPanel;

import java.awt.*;
import java.rmi.RemoteException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.table.JTableHeader;

import java.awt.event.*;

/**
 * This is used to set column order preferences for entitygroups
 * @author Wendy Stimpson
 */
// $Log: OrderPref.java,v $
// Revision 1.5  2014/01/24 12:47:13  wendy
// getro() throw remoteexception to allow reconnect
//
// Revision 1.4  2013/08/16 16:24:27  wendy
// prevent null ptr if deref ran
//
// Revision 1.3  2013/07/18 18:26:16  wendy
// fix compiler warnings
//
// Revision 1.2  2013/02/05 18:23:23  wendy
// throw/handle exception if ro is null
//
// Revision 1.1  2012/09/27 19:39:15  wendy
// Initial code
//
public class OrderPref extends JPanel implements Preferencable, ActionListener, EACMGlobals {
	private static final long serialVersionUID = 1L;
    private static final String COLON = ":";

    private JPanel btnPnl = null;
    private JButton bSave = new JButton(new SaveAction());
    private JButton bDefSave= new JButton(new SaveDefAction());
    private JButton bDefReset = new JButton(new ResetDefAction());
    private JButton bReset = new JButton(new ResetAction());

	private JComboBox profBox = new JComboBox();
	private JLabel profLbl = new JLabel(Utils.getResource("prof.pref") + COLON);
	private JComboBox entBox = new JComboBox();
	private JLabel entLbl = new JLabel(Utils.getResource("entity.pref") + COLON);

	private MetaEntityList mel = null;
    private Hashtable<String, MetaEntityList> metaEntityListTbl = new Hashtable<String, MetaEntityList>();
	private OrderTable orderTbl = new OrderTable();
	private JScrollPane scroll = new JScrollPane(orderTbl);

	private MetaColumnOrderGroup mcog = null;
    private boolean selectingEntboxItem = false;

    private SaveWorker saveWorker = null;
    private MetaColGrpWorker mclgWorker = null;
    private ResetDefWorker resetDefWorker = null;
    private ResetWorker resetWorker = null;
    private MetaListWorker mlWorker = null;

	/**
     * constructor for column order preferences
     */
    protected OrderPref() {
		scroll.setFocusable(false);
		init(); // must be done before listeners are added
		createButtonPanel();
		loadOrderDefault(); // must be done before listeners are added

		profBox.addActionListener(this);
		entBox.addActionListener(this);

	    setBorder(BorderFactory.createTitledBorder(Utils.getResource("colorder.border")));// meet accessiblity
	}
    private void createButtonPanel() {
    	btnPnl = new JPanel(new GridLayout(1, 4, 5, 5));

//  	this is needed or the mnemonic doesnt activate
    	bSave.setMnemonic((char)((Integer)bSave.getAction().getValue(Action.MNEMONIC_KEY)).intValue());
    	bDefSave.setMnemonic((char)((Integer)bDefSave.getAction().getValue(Action.MNEMONIC_KEY)).intValue());
    	bDefReset.setMnemonic((char)((Integer)bDefReset.getAction().getValue(Action.MNEMONIC_KEY)).intValue());
    	bReset.setMnemonic((char)((Integer)bReset.getAction().getValue(Action.MNEMONIC_KEY)).intValue());

        btnPnl.add(bSave);
        btnPnl.add(bDefSave);
        btnPnl.add(bDefReset);
        btnPnl.add(bReset);

        bDefSave.setVisible(false);
    }

	private void disableActionsAndWait(){
    	disableActions();
    	setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
      	fixTableHeaderCursorBug(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    }
	private void disableActions(){
	 	enableAll(false);
	}
	private void enableActionsAndRestore(){
		enableActions();

		setCursor(Cursor.getDefaultCursor());
		fixTableHeaderCursorBug(Cursor.getDefaultCursor());
	}
	private void enableActions(){
		enableAll(true);
	}
	private void fixTableHeaderCursorBug(Cursor cursor){
		//tableheaders do not consistently display correct cursor
		JTableHeader header = orderTbl.getTableHeader();
		if (header != null) {
			header.setCursor(cursor);
		}
	}
    /**
     * refreshAppearance - used when font is changed
     */
    public void refreshAppearance() {
    	orderTbl.resizeCells();
    }

    /* (non-Javadoc)
     * @see com.ibm.eacm.preference.Preferencable#getButtonPanel()
     */
    public JPanel getButtonPanel() {
       	return btnPnl;
    }
	/**
     * layout panel
     *
     */
    private void init() {
        Dimension d = new Dimension(600,400);

    	profLbl.setLabelFor(profBox);
		profLbl.setDisplayedMnemonic(Utils.getMnemonic("prof.pref"));

		entLbl.setLabelFor(entBox);
		entLbl.setDisplayedMnemonic(Utils.getMnemonic("entity.pref"));

	    loadProfileBox(EACM.getEACM().getProfileSet());

		scroll.setPreferredSize(d);
		scroll.setSize(d);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        GroupLayout.ParallelGroup leftToRight = layout.createParallelGroup();

        GroupLayout.SequentialGroup top = layout.createSequentialGroup();
        GroupLayout.ParallelGroup col1 = layout.createParallelGroup();
        col1.addComponent(profLbl);
        col1.addComponent(entLbl);
        top.addGroup(col1);
        GroupLayout.ParallelGroup col2 = layout.createParallelGroup();
        col2.addComponent(profBox);
        col2.addComponent(entBox);
        top.addGroup(col2);
        leftToRight.addGroup(top);
        leftToRight.addComponent(scroll);

        GroupLayout.SequentialGroup topToBottom = layout.createSequentialGroup();
        GroupLayout.ParallelGroup profRow = layout.createParallelGroup();
        profRow.addComponent(profLbl);
        profRow.addComponent(profBox);
        topToBottom.addGroup(profRow);
        GroupLayout.ParallelGroup entityRow = layout.createParallelGroup();
        entityRow.addComponent(entLbl);
        entityRow.addComponent(entBox);
        topToBottom.addGroup(entityRow);
        topToBottom.addComponent(scroll);

        layout.setHorizontalGroup(leftToRight);
        layout.setVerticalGroup(topToBottom);
	}

    /**
     * look for selected tab and get entitygroup from that
     * use active profile, if one has been selected
     */
    private void loadOrderDefault() {
    	TabPanel eTab = EACM.getEACM().getCurrentTab();
        String egType = null;
        if (eTab != null) { // try to display that entitygroup
        	egType = eTab.getEntityType(2);
            if (egType==null){
            	egType = eTab.getEntityType(0);
            }
            if (egType==null){
            	egType = eTab.getEntityType(1);
            }
        }

        Profile prof = null;
        if(EACM.getEACM().getCurrentTab()!=null){
        	prof = EACM.getEACM().getCurrentTab().getProfile();
        }

        loadProfileAndGroup(prof, egType);
    }
	/**
     * release memory
     */
    public void dereference() {
      	profLbl.setLabelFor(null);
		entLbl.setLabelFor(null);

		profBox.removeActionListener(this);
		for (int i=0;i<profBox.getItemCount();++i) {
			ProfileDisplay pfd = (ProfileDisplay)profBox.getItemAt(i);
			pfd.dereference();
		}
		profBox.removeAllItems();
		profBox.removeAll();

		entBox.removeActionListener(this);
		entBox.removeAllItems();
		entBox.removeAll();

    	scroll.removeAll();
    	scroll.setUI(null);
    	scroll= null;

		for (Enumeration<MetaEntityList> e = metaEntityListTbl.elements(); e.hasMoreElements();){
			MetaEntityList ml = (MetaEntityList)e.nextElement();
			ml.dereference();
		}
		metaEntityListTbl.clear();
		metaEntityListTbl=null;
		mel = null;

    	orderTbl.dereference();
    	orderTbl = null;

    	// deref the buttons/actions
    	SaveAction sa  = (SaveAction)bSave.getAction();
    	sa.dereference();
    	bSave.setAction(null);
    	bSave.setUI(null);

      	SaveDefAction sda  = (SaveDefAction)bDefSave.getAction();
    	sda.dereference();
    	bDefSave.setAction(null);
    	bDefSave.setUI(null);

    	ResetAction ra  = (ResetAction)bReset.getAction();
    	ra.dereference();
    	bReset.setAction(null);
    	bReset.setUI(null);

      	ResetDefAction rda  = (ResetDefAction)bDefReset.getAction();
    	rda.dereference();
    	bDefReset.setAction(null);
    	bDefReset.setUI(null);

    	removeAll(); // does removenotify on all components
    	setUI(null);

    	btnPnl.removeAll();
    	btnPnl.setUI(null);
        btnPnl = null;
        bSave = null;
        bDefSave= null;
        bDefReset = null;
        bReset = null;

    	profBox = null;
    	profLbl = null;
    	entBox = null;
    	entLbl = null;

    	mcog = null;

        saveWorker = null;
        mclgWorker = null;
        resetDefWorker = null;
        resetWorker = null;
        mlWorker = null;
	}

	/**
     * fill in profile combo box
     * @param pSet
     */
    private void loadProfileBox(ProfileSet pSet) {
        if (pSet != null) {
			Profile[] prof = pSet.toArray();
			Arrays.sort(prof, new ProfComparator());

			DefaultComboBoxModel dcm = new DefaultComboBoxModel();// better performance than adding directly to combobox
			for (int i=0;i<prof.length;++i) {
				dcm.addElement(new ProfileDisplay(prof[i]));
			}
			profBox.setModel(dcm);
			profBox.setSelectedItem(null);
		}
	}

    private RemoteDatabaseInterface ro() throws RemoteException  {
    	return RMIMgr.getRmiMgr().getRemoteDatabaseInterface();
    }
    /**
     * get the list of entitygroups for this profile
     * @param prof
     * @return
     *
     */
    private MetaEntityList getMetaEntityList(Profile prof) {
        MetaEntityList mel = null;
        long t1 =System.currentTimeMillis();
        Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting "+prof);
        if (prof != null) {
        	String profkey = prof.getEnterprise() + ":" + prof.getOPWGID();
        	try {
        		mel = (MetaEntityList)metaEntityListTbl.get(profkey);
        		if (mel==null){
        			mel = ro().getMetaEntityList(prof);
        			//if eacm closed while this is running, deref may have released the table
        			if(metaEntityListTbl !=null){
        				metaEntityListTbl.put(profkey, mel);
        			}
        		}
        	} catch (Exception exc) {
        		if(RMIMgr.shouldTryReconnect(exc)){	// try to reconnect
        			if (RMIMgr.getRmiMgr().reconnectMain()) {
        				try{
        					mel = ro().getMetaEntityList(prof);
        					metaEntityListTbl.put(profkey, mel);
        				}catch(Exception e){
        					if(RMIMgr.shouldPromptUser(e)){// show user msg and ask what to do
        						if (com.ibm.eacm.ui.UI.showMWExcPrompt(this, e) == RETRY) {
        							mel= getMetaEntityList(prof);
        						}
        					}else{
        						com.ibm.eacm.ui.UI.showException(this,e, "mw.err-title");
        					}
        				}
        			}else{	// reconnect failed
        				com.ibm.eacm.ui.UI.showException(this,exc, "mw.err-title");
        			}
        		}else{ // show user msg and ask what to do
        			if(RMIMgr.shouldPromptUser(exc)){
        				if (com.ibm.eacm.ui.UI.showMWExcPrompt(this, exc) == RETRY) {
        					mel= getMetaEntityList(prof);
        				}// else user decide to ignore or exit
        			}else{
        				com.ibm.eacm.ui.UI.showException(this,exc, "mw.err-title");
        			}
        		}
        	}
        }

        Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"ended "+Utils.getDuration(t1));

        return mel;
    }

    /**
     * fill in entitygroup combobox
     */
    private void fillEntityBox() {
    	mel.setSortType(MetaEntityList.SORT_BY_LONG_DESCRIPTION);
    	mel.performSort();
    	DefaultComboBoxModel dcm = new DefaultComboBoxModel();// better performance than adding directly to combobox
    	for (int i=0;i<mel.getEntityGroupCount();++i) {
    		dcm.addElement(mel.getLongDescription(i) + " (" + mel.getEntityType(i) + ")");
    	}
    	entBox.setModel(dcm);
    	selectingEntboxItem = true;
    	entBox.setSelectedItem(null);
    	selectingEntboxItem = false;
    	entBox.revalidate();
    }

	/**
	 * get column orders for selected entitygroup
	 * @return
	 * @throws RemoteException
	 * @throws MiddlewareRequestException
	 * @throws MiddlewareShutdownInProgressException
	 * @throws MiddlewareException
	 */
	private MetaColumnOrderGroup getMetaColumnOrderGroup()
	throws RemoteException, MiddlewareRequestException, MiddlewareShutdownInProgressException, MiddlewareException
	{
		MetaColumnOrderGroup mcog=null;
		EntityGroup eg = mel.getEntityGroup(ro(),entBox.getSelectedIndex());
		if (eg != null) {
			mcog = getMetaColumnOrderGroup(eg);
		}
		return mcog;
	}

	/**
	 * get selected profile from combobox
	 * @return
	 */
	private Profile getSelectedProfile() {
		ProfileDisplay pfd = ((ProfileDisplay)profBox.getSelectedItem());
		Profile out = null;
		if(pfd!=null){
			out = pfd.prof;
			out.setValOnEffOn(DateRoutines.getEOD(), DateRoutines.getEOD());
		}

		return out;
	}

	/**
     * commit
     * @param def
     */
    private void commit(boolean def) {
    	orderTbl.setUpdateDefaults(def);
    	orderTbl.commit(this);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==entBox){
			if(!selectingEntboxItem){
				disableActionsAndWait();
				mclgWorker = new MetaColGrpWorker();// SwingWorker objects can't be reused
				mclgWorker.execute();
			}
		}else if (e.getSource()==profBox){
			orderTbl.updateOrderModel(null);
	    	selectingEntboxItem = true;  // dont use action listener
	    	entBox.setSelectedItem(null);
	    	selectingEntboxItem = false;

	    	disableActionsAndWait();
			mlWorker = new MetaListWorker();// SwingWorker objects can't be reused
			mlWorker.execute();
		}
	}

	/**
     * check if profile has ability to update system defaults
     * @return
     */
    private boolean isDefaultCapable() {
		Profile prof = getSelectedProfile();
		if (prof != null) {
			return prof.hasRoleFunction(Profile.ROLE_FUNCTION_ATTR_ORDER_DEFAULT);
		}
		return false;
	}

    private void adjustDefaultButton(boolean hasData) {
        boolean b = isDefaultCapable() && hasData;
        bDefSave.setEnabled(b);
        bDefSave.setVisible(b);
    }

    private void adjustButtonEnabled() {
        boolean b = orderTbl.getRowCount() > 0;
        bSave.setEnabled(b);
        bDefReset.setEnabled(b);
        bReset.setEnabled(b);
        adjustDefaultButton(b);
    }

    /***********
     * used to display profile in combobox
     */
	private class ProfileDisplay {
		private Profile prof = null;
		private ProfileDisplay(Profile prof2) {
			this.prof = prof2;
		}
        public String toString() {
			return prof.getWGName() + ":  " + prof.toString();
		}
        private void dereference() {
			prof = null;
		}
	}


	/**
     * load active profile and current entitygroup
     * @param prof
     * @param eType
     */
    private void loadProfileAndGroup(Profile prof, String eType) {
		entBox.setEnabled(false);
		if (prof != null) {
			profBox.setSelectedItem(getProfileDisplay(prof));
			disableActionsAndWait();
			mlWorker = new MetaListWorker(eType);
			mlWorker.execute();
		}else{
			adjustButtonEnabled();
		}
	}

	private ProfileDisplay getProfileDisplay(Profile prof) {
		if (prof != null) {
			for (int i=0;i<profBox.getItemCount();++i) {
				ProfileDisplay pfd = (ProfileDisplay)profBox.getItemAt(i);
				if (pfd.prof.getEnterprise().equals(prof.getEnterprise()) &&
						pfd.prof.getOPWGID()==prof.getOPWGID()) { // session may be different
					return pfd;
				}
			}
		}
		return null;
	}

	private void enableAll(boolean b){
		profBox.setEnabled(b); // allow other selections now
		entBox.setEnabled(b && mel!=null);
        bSave.setEnabled(b);
        bDefReset.setEnabled(b);
        bReset.setEnabled(b);
        adjustDefaultButton(b);
        orderTbl.setEnabled(b);
	}
    /**
     * get column orders for specified entitygroup
     * @param group
     * @return
     */
    private MetaColumnOrderGroup getMetaColumnOrderGroup(EntityGroup group) {
        MetaColumnOrderGroup mcog = null;
        try {
            mcog = group.getMetaColumnOrderGroup(null, ro());
        } catch (Exception exc) {
        	if(RMIMgr.shouldTryReconnect(exc)){	// try to reconnect
    			if (RMIMgr.getRmiMgr().reconnectMain()) {
    				try{
    					 mcog = group.getMetaColumnOrderGroup(null, ro());
    				}catch(Exception e){
    					if(RMIMgr.shouldPromptUser(e)){// show user msg and ask what to do
    						if (com.ibm.eacm.ui.UI.showMWExcPrompt(this, e) == RETRY) {
    							mcog = getMetaColumnOrderGroup(group);
    						}
    					}else{
    						com.ibm.eacm.ui.UI.showException(this,e, "mw.err-title");
    					}
    				}
    			}else{	// reconnect failed
    				com.ibm.eacm.ui.UI.showException(this,exc, "mw.err-title");
    			}
    		}else{ // show user msg and ask what to do
    			if(RMIMgr.shouldPromptUser(exc)){
    				if (com.ibm.eacm.ui.UI.showMWExcPrompt(this, exc) == RETRY) {
    					mcog= getMetaColumnOrderGroup(group);
    				}// else user decide to ignore or exit
    			}else{
    				com.ibm.eacm.ui.UI.showException(this,exc, "mw.err-title");
    			}
    		}
        }

        return mcog;
    }
    /**
     * reset all preferences
     */
    private class ResetWorker extends SwingWorker<Boolean, Void> {
    	@Override
    	public Boolean doInBackground() {
    		Boolean bool = new Boolean(false);
    		try{
                boolean isok = false;
        		if (!isDefaultCapable()) {
        			if (mcog != null) {
        				try {
        					mcog.resetToDefaults(null, ro());
        				} catch (Exception x) {
        					com.ibm.eacm.ui.UI.showException(OrderPref.this,x, "mw.err-title");
        				}
        			}
        		}
        		if(!isCancelled()){
        			isok =  orderTbl.rollback(OrderPref.this);
        		}
                bool = new Boolean(isok);
    		}catch(Exception ex){ // prevent hang
    			Logger.getLogger(APP_PKG_NAME).log(Level.SEVERE,"Exception ",ex);
    		}
			return bool;
    	}

        @Override
        public void done() {
            //this will be on the dispatch thread
            try {
             	if(!isCancelled()){
             		Boolean isok = get();
             		if (isok.booleanValue()){
             			orderTbl.modelChangedUpdate();
             		}
             	}
            } catch (InterruptedException ignore) {}
            catch (java.util.concurrent.ExecutionException e) {
                String why = null;
                Throwable cause = e.getCause();
                if (cause != null) {
                    why = cause.getMessage();
                } else {
                    why = e.getMessage();
                }
    			Logger.getLogger(APP_PKG_NAME).log(Level.SEVERE,"Error getting metalist: " + why,e);
            }finally{
             	enableActionsAndRestore();
            	resetWorker = null;
            }
        }
    }
    /**
     * reset defaults
     */
    private class ResetDefWorker extends SwingWorker<Boolean, Void> {
    	@Override
    	public Boolean doInBackground() {
    		Boolean bool = new Boolean(false);
    		try{
                boolean isok = orderTbl.rollbackDefault(OrderPref.this);
                bool = new Boolean(isok);
    		}catch(Exception ex){ // prevent hang
    			Logger.getLogger(APP_PKG_NAME).log(Level.SEVERE,"Exception ",ex);
    		}
			return bool;
    	}

        @Override
        public void done() {
            //this will be on the dispatch thread
            try {
             	if(!isCancelled()){
             		Boolean isok = get();
             		if (isok.booleanValue()){
             			orderTbl.modelChangedUpdate();
             		}
             	}
            } catch (InterruptedException ignore) {}
            catch (java.util.concurrent.ExecutionException e) {
                String why = null;
                Throwable cause = e.getCause();
                if (cause != null) {
                    why = cause.getMessage();
                } else {
                    why = e.getMessage();
                }
    			Logger.getLogger(APP_PKG_NAME).log(Level.SEVERE,"Error in resetDef: " + why,e);
            }finally{
             	enableActionsAndRestore();
            	resetDefWorker = null;
            }
        }
    }
    /**
     * save all preferences or defaults
     */
    private class SaveWorker extends DBSwingWorker<Void, Void> {
    	boolean isDefault=false;
    	SaveWorker(boolean b){
    		isDefault=b;
    	}
    	@Override
    	public Void doInBackground() {
    		try{
    			commit(isDefault);
    		}catch(Exception ex){ // prevent hang
    			Logger.getLogger(APP_PKG_NAME).log(Level.SEVERE,"Exception ",ex);
    		}
			return null;
    	}

        @Override
        public void done() {
         	enableActionsAndRestore();
        	saveWorker = null;
        }
		@Override
		public void notExecuted() {
			enableActionsAndRestore();
        	saveWorker = null;
		}
    }
    /**
     * get the set of entitygroups for the selected profile on a worker thread
     * then load the list on the event thread.
     * if entitytype is not null, find the column orders for that
     */
    private class MetaListWorker extends SwingWorker<MetaEntityList, Void> {
    	private String entityType=null;

    	MetaListWorker(){}
    	MetaListWorker(String type){
    		entityType = type;
    	}
    	@Override
    	public MetaEntityList doInBackground() {
    		MetaEntityList metalist=null;
    		try{
    			metalist = getMetaEntityList(getSelectedProfile());
    		}catch(Exception ex){ // prevent hang
    			Logger.getLogger(APP_PKG_NAME).log(Level.SEVERE,"Exception ",ex);
    		}
    		return metalist;
    	}

        @Override
        public void done() {
            //this will be on the dispatch thread
            try {
            	if(isCancelled()){
            		return;
            	}
            	mel = get();
            	if (mel!=null){
            		fillEntityBox();
            		if (entityType != null) { // getting loaded after profile was selected and tab loaded
            			javax.swing.SwingUtilities.invokeLater(new Runnable() {
            				public void run() {
            					// select the entitytype and let it run
            					for (int i=0;i<mel.getEntityGroupCount();++i) {
            						if (entityType.equals(mel.getEntityType(i))) {
            							entBox.setSelectedIndex(i);
            							break;
            						}
            					}
            				}
            			});
            		}

            		adjustButtonEnabled();
            	}
            } catch (InterruptedException ignore) {}
            catch (java.util.concurrent.ExecutionException e) {
                String why = null;
                Throwable cause = e.getCause();
                if (cause != null) {
                    why = cause.getMessage();
                } else {
                    why = e.getMessage();
                }
    			Logger.getLogger(APP_PKG_NAME).log(Level.SEVERE,"Error getting metalist: " + why,e);
            }finally{
            	if (entityType == null){ // colgrp worker will run if this is not null
            	 	enableActionsAndRestore();
            		mlWorker = null;
            	}
            }
        }
    }
    /**
     * get column order for selected entitygroup on a worker thread then load the table on the
     * event thread
     */
    private class MetaColGrpWorker extends SwingWorker<MetaColumnOrderGroup, Void> {
    	@Override
    	public MetaColumnOrderGroup doInBackground() {
    		MetaColumnOrderGroup mcog=null;
    		try{
    			mcog = getMetaColumnOrderGroup();
    		}catch(Exception ex){ // prevent hang
    			Logger.getLogger(APP_PKG_NAME).log(Level.SEVERE,"Exception ",ex);
    		}
    		return mcog;
    	}

        @Override
        public void done() {
            //this will be on the dispatch thread
        	try {
        		if(!isCancelled()){
        			mcog = get();
        			if (mcog != null) {
        				orderTbl.updateOrderModel(mcog.getTable("Order Table"));
        			}
             	}
            } catch (InterruptedException ignore) {}
            catch (java.util.concurrent.ExecutionException e) {
                String why = null;
                Throwable cause = e.getCause();
                if (cause != null) {
                    why = cause.getMessage();
                } else {
                    why = e.getMessage();
                }
    			Logger.getLogger(APP_PKG_NAME).log(Level.SEVERE,"Error getting metacolordergrp: " + why,e);
            }finally{
             	enableActionsAndRestore();
            	if(!isCancelled()){
            		adjustButtonEnabled();
            	}

            	mclgWorker = null;
            }
        }
    }

    private class SaveDefAction extends EACMAction {
		private static final long serialVersionUID = 1L;
		private static final String CMD = "save.pref.default";
		SaveDefAction() {
			super(CMD,"System Defaults");
            setEnabled(false);
        }

		public void actionPerformed(ActionEvent e) {
			disableActionsAndWait();
			saveWorker = new SaveWorker(true);
			RMIMgr.getRmiMgr().execute(saveWorker);
		}
    }

    private class ResetDefAction extends EACMAction {
		private static final long serialVersionUID = 1L;
		private static final String CMD = "reset.pref.default";
		ResetDefAction() {
            super(CMD,"using System Defaults");
            setEnabled(false);
        }
		public void actionPerformed(ActionEvent e) {
			orderTbl.editingStopped(null); // clear any editors
			disableActionsAndWait();
			resetDefWorker = new ResetDefWorker();
			resetDefWorker.execute();
		}
    }

    private class ResetAction extends EACMAction {
		private static final long serialVersionUID = 1L;
		private static final String CMD = "reset.pref";
		ResetAction() {
            super(CMD,"Column Orders");
            setEnabled(false);
        }

		public void actionPerformed(ActionEvent e) {
			orderTbl.editingStopped(null); // clear any editors
			disableActionsAndWait();
			resetWorker = new ResetWorker();
			resetWorker.execute();
		}
    }

    private class SaveAction extends EACMAction {
		private static final long serialVersionUID = 1L;
		private static final String CMD = "save.pref";
		SaveAction() {
			super(CMD,"Column Orders");
            setEnabled(false);
        }
		public void actionPerformed(ActionEvent e) {
			orderTbl.editingStopped(null); // clear any editors
			disableActionsAndWait();
			saveWorker = new SaveWorker(false);
			RMIMgr.getRmiMgr().execute(saveWorker);
		}
    }
	/* (non-Javadoc)
	 * called when dialog is closing
	 * @see com.ibm.eacm.preference.Preferencable#isClosing()
	 */
	public void isClosing() {
	    if(saveWorker != null){
	    	RMIMgr.getRmiMgr().cancelWorker(saveWorker,true);
	    }
	    if(mclgWorker != null){
	    	mclgWorker.cancel(true);
	    }
	    if(resetDefWorker != null){
	    	resetDefWorker.cancel(true);
	    }
	    if(resetWorker != null){
	    	resetWorker.cancel(true);
	    }
	    if(mlWorker != null){
	    	mlWorker.cancel(true);
	    }
	}
	/* (non-Javadoc)
	 * notify user that changes have not been saved
	 * @see com.ibm.eacm.preference.Preferencable#hasChanges()
	 */
	public boolean hasChanges() {
		boolean chgs = false;

		if (orderTbl!=null){
			chgs = orderTbl.hasChanges();
		}
		return chgs;
	}
	public void updateFromPrefs() {
		// nothing here is stored in xml prefs
	}
}

