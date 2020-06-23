//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
package com.ibm.eacm.tabs;

import java.awt.*;
import java.awt.event.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;

import COM.ibm.eannounce.objects.InactiveGroup;
import COM.ibm.eannounce.objects.InactiveItem;
import COM.ibm.eannounce.objects.RowSelectableTable;
import COM.ibm.opicmpdh.middleware.Profile;

import com.ibm.eacm.EACM;
import com.ibm.eacm.actions.*;

import com.ibm.eacm.editor.DateEditor;
import com.ibm.eacm.editor.DatePopup;
import com.ibm.eacm.editor.DateSelector;
import com.ibm.eacm.editor.TimeEditor;
import com.ibm.eacm.mw.DBSwingWorker;
import com.ibm.eacm.mw.RMIMgr;
import com.ibm.eacm.objects.Findable;
import com.ibm.eacm.objects.Utils;

import com.ibm.eacm.toolbar.ComboItem;
import com.ibm.eacm.toolbar.DefaultToolbarLayout;

import com.ibm.eacm.table.*;

/**
 * tab for RestoreAction
 *
 * The 'Entity Name' column does not have a valid value any more because the sps use NAME for that column
 * gbl2935 and gbl2936
 *
 * These are the only entities with a NAME attr
DGENTITY	NAME
METAXLATEGRP	NAME
NLS	NAME
OPWG	NAME
RPTEXCEL	NAME
SUB	NAME
WG	NAME
* @author Wendy Stimpson
 */
//$Log: RestoreActionTab.java,v $
//Revision 1.2  2013/02/07 13:37:37  wendy
//log close tab
//
//Revision 1.1  2012/09/27 19:39:23  wendy
//Initial code
//
public class RestoreActionTab extends ActionTabPanel implements
	PropertyChangeListener, KeyListener
{
	private static final long serialVersionUID = 1L;
	private RestoreTable table = null;

    private JScrollPane jsp = new JScrollPane();
    private JPanel pnlMain = new JPanel(new java.awt.BorderLayout());
    private Profile prof = null;
    private InactiveGroup ig = null;

    private TimeEditor timeEditor = null;
    private DateEditor dateEditor = null;
    private DatePopup datePopup =null;
    private JButton button = null;
    
	/* (non-Javadoc)
	 * @see java.awt.Component#toString()
	 */
	public String toString(){
		return "RestoreActionTab "+prof;
	}

    /* (non-Javadoc)
     * @see com.ibm.eacm.objects.FindableComp#getFindable()
     */
    public Findable getFindable() {
		return table;
	}

    public RestoreActionTab(Profile profile){
    	setSessionTagText("N/A");
        prof = profile;

		dateEditor = new DateEditor(DateEditor.DateType.PAST_DATE, prof);
		dateEditor.addKeyListener(this);// get enter key press

		timeEditor = new TimeEditor(prof);
		timeEditor.getTextField().addKeyListener(this);// get enter key press

		datePopup = new DatePopup(this);
		button = new JButton(new DateAction());
		button.setVerifyInputWhenFocusTarget(false);// dont verify the date field when popup button is pressed

		datePopup.getDateSelector().setEditor(dateEditor);
		datePopup.getDateSelector().addPropertyChangeListener(this);

		dateEditor.setCaretPosition(0);
		button.setOpaque(false);

	    pnlMain.add(setupDatePanel(),BorderLayout.NORTH);
	    pnlMain.add(jsp,BorderLayout.CENTER);

	    init();
    }

    private void init() {
	    createActions();
	    createMenus();
    	createPopupMenu();
	    createToolbar();

        JPanel tPnl = new JPanel(new BorderLayout());
        tPnl.add(pnlMain,BorderLayout.CENTER);

        tPnl.add(getToolbar().getAlignment(), getToolbar());

    	add(tPnl,BorderLayout.CENTER);

	    getAction(REFRESH_ACTION).actionPerformed(null);// get the table
    }

    private JPanel setupDatePanel() {
     	GridBagLayout gbl = new GridBagLayout();
        JPanel dtPanel = new JPanel(gbl);

    	GridBagConstraints gbc = new GridBagConstraints();
    	button.setBorder(null);

		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.ipadx = 2;

		buildConstraints(gbc,0,0,1,1,0,0);
		gbl.setConstraints(dateEditor,gbc);
		dtPanel.add(dateEditor);

		buildConstraints(gbc,1,0,1,1,0,0);
		gbl.setConstraints(button,gbc);
		dtPanel.add(button);

		buildConstraints(gbc,2,0,1,1,0,0);
		gbl.setConstraints(timeEditor,gbc);
		dtPanel.add(timeEditor);
		return dtPanel;
	}

	private void buildConstraints(GridBagConstraints gbc, int gx, int gy, int gw, int gh, int wx, int wy) {
		gbc.gridx = gx;
		gbc.gridy = gy;
		gbc.gridwidth = gw;
		gbc.gridheight = gh;
		gbc.weightx = wx;
		gbc.weighty = wy;
	}
	private boolean hasRows() {
		return (table != null && table.getRowCount() > 0);
	}
    /**
     * createMenus
     *
     */
    private void createMenus() {
    	super.createFileMenu();
        createActionMenu();
        createTableMenu();
    }
	/**
	 * prevent popup from covering editor
	 * @return
	 */
	private int computeY() {
		Point editorPt = dateEditor.getLocationOnScreen();

		// Get toolkit
	    Toolkit toolkit = Toolkit.getDefaultToolkit();

	    // Get screen size
	    Dimension screenDim = toolkit.getScreenSize();

		Dimension pSize = datePopup.getPreferredSize();
		int popupHeight = pSize.height;
		if (popupHeight + editorPt.y+dateEditor.getHeight()>screenDim.height) {
			return -(popupHeight); // put popup over editor
		}

		return dateEditor.getHeight();  // put popup under editor
	}

    private void createTableMenu() {
    	JMenu tblMenu = new JMenu(Utils.getResource(TABLE_MENU));
	   	tblMenu.setMnemonic(Utils.getMnemonic(TABLE_MENU));

	   	addLocalActionMenuItem(tblMenu, MOVECOL_LEFT_ACTION);
	   	addLocalActionMenuItem(tblMenu, MOVECOL_RIGHT_ACTION);
	    tblMenu.addSeparator();

	    addLocalActionMenuItem(tblMenu, SORT_ACTION);
		tblMenu.addSeparator();

		addLocalActionMenuItem(tblMenu, HIDECOL_ACTION);
		addLocalActionMenuItem(tblMenu, UNHIDECOL_ACTION);
		tblMenu.addSeparator();

		addLocalActionMenuItem(tblMenu, FINDREP_ACTION);
		addLocalActionMenuItem(tblMenu, FINDNEXT_ACTION);

		addLocalActionMenuItem(tblMenu, FILTER_ACTION);

		getMenubar().add(tblMenu);

	}
    /* (non-Javadoc)
     * used by base class for enabling column listener
     * @see com.ibm.eacm.tabs.TabPanel#getJTable()
     */
    protected BaseTable getJTable() { return table;}

  //for now use this,need to clean up close tabs action
    public boolean canClose(){
    	dateEditor.giveUpFocus(); // seems to keep closeTab from putting up msg, but not close window
		return true;
	}
	public ComboItem getDefaultToolbarLayout() {
		return DefaultToolbarLayout.RESTORE_BAR;
	}

    public boolean viewRestoreExist() {
    	Profile myProf = getProfile();
    	Profile actProf = EACM.getEACM().getActiveProfile();//.getCurrentTab().getProfile();
    	if (actProf != null) {
    		if (myProf.getEnterprise().equals(actProf.getEnterprise())) {
    			if (myProf.getOPWGID() == actProf.getOPWGID()) {
    				return true;
    			}
    		}
    	}
    	return false;
    }
	/**
	 * create all of the actions, they are shared between toolbar and menu
	 */
    public void createActions() {
    	super.createActions();
		addAction(new FindRepAction());
		addAction(new FindNextAction());

    	createTableActions(table);
    	EACMAction act = new RefreshAction();
    	addAction(act);

    	act = new RestoreItemAction();
    	addAction(act);
	}
    /**
     * create actions menu
     */
    private void createActionMenu() {
    	JMenu actMenu = new JMenu(Utils.getResource(ACTIONS_MENU));
    	actMenu.setMnemonic(Utils.getMnemonic(ACTIONS_MENU));

    	addLocalActionMenuItem(actMenu, RESTORE_ACTION);
    	addLocalActionMenuItem(actMenu, REFRESH_ACTION);

		actMenu.addSeparator();
		addLocalActionMenuItem(actMenu, SELECTALL_ACTION);
		addLocalActionMenuItem(actMenu, SELECTINV_ACTION);

       	getMenubar().add(actMenu);
    }

    /**
     * dereference
     */
    public void dereference() {
        if (table != null) {
        	table.getSelectionModel().removeListSelectionListener(this);
        	table.getColumnModel().removeColumnModelListener(this);
            table.dereference();
            table = null;
        }

        jsp.removeAll();
        jsp.setUI(null);
        jsp = null;

        timeEditor.getTextField().removeKeyListener(this);
        timeEditor.dereference();
        timeEditor = null;

    	dateEditor.removeKeyListener(this);
        dateEditor.dereference();
        dateEditor = null;

		datePopup.getDateSelector().removePropertyChangeListener(this);
        datePopup.dereference();
        datePopup = null;

        ((EACMAction)button.getAction()).dereference();
        button.setAction(null);
        button.removeAll();
        button.setUI(null);
        button = null;

        pnlMain.removeAll();
        pnlMain.setUI(null);
        pnlMain = null;

        prof = null;
        if (ig!=null){
        	ig.dereference();
        	ig = null;
        }
        super.dereference();
    }

    /**
     * enable actions based on current selection
     */
    public void refreshActions() {
    	if(table==null){ // deref may have run before worker was done
    		return;
    	}
    	boolean rowSelected = table.getSelectedRow()!= -1;
    	getAction(RESTORE_ACTION).setEnabled(rowSelected);
    	//enable actions based on selection
		enableTableActions();
    }

    public String getTableTitle() {
        return "RestoreActionTable";
    }

    private void setTableAvailable() {
    	updateTableActions(table);

    	if(hasRows() && table.getSelectedRow()== -1){
    		table.getSelectionModel().setSelectionInterval(0, 0);
    	}

        getAction(RESTORE_ACTION).setEnabled(hasRows() && table.getSelectedRow()!= -1 &&  !getProfile().isReadOnly());

        getAction(FINDREP_ACTION).setEnabled( hasRows());
        getAction(FINDNEXT_ACTION).setEnabled( hasRows());

        EACMAction act = getAction(FILTER_ACTION);
        act.setEnabled(hasRows());
        ((FilterAction)act).setFilterable(table);
        act = getAction(SORT_ACTION);
        act.setEnabled(hasRows());
        ((SortAction)act).setSortable(table);

        // make sure filtered icon is accurate
       	EACM.getEACM().setFilterStatus(isFiltered());
    }

    /**
     * getProfile
     *
     */
    public Profile getProfile() {
        return prof;
    }
    /**
     * getTabMenuTitle
     *
     * @return
     */
    protected String getTabMenuTitleKey() { return "restore.title";}

    /**
     * getTabIcon
     *
     * @return
     */
    protected String getTabIconKey() {
        return "restore.icon";
    }

   	private class DateAction extends EACMAction
	{
		private static final long serialVersionUID = 1L;
		public DateAction() {
		    putValue(javax.swing.Action.LARGE_ICON_KEY, Utils.getImageIcon("cal.gif"));
		}

		public void actionPerformed(ActionEvent e) {
			if (datePopup.isShowing()) {
    			datePopup.setVisible(false);
    		} else {
    			Dimension popupSize = datePopup.getPreferredSize();
    			int x = dateEditor.getWidth() -popupSize.width;
    			datePopup.show(dateEditor, x, computeY());
    		}
		}
	}

	/**
	 * called by dateselector when date has been changed in the calendar control
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent event) {
		if(event.getPropertyName().equals(DateSelector.NEWDATE_PROPERTY))	{
			datePopup.setVisible(false);
		}
	}

    private RowSelectableTable getRestoreTable() {
    	InactiveGroup oldIg = ig;
        ig = getInactiveGroup();
    	if (oldIg!=null){
    		oldIg.dereference();
    	}
        if (ig != null) {
            return ig.getTable();
        }
        return null;
    }

    private InactiveGroup getInactiveGroup() {
        InactiveGroup ig = null;
    	String date = dateEditor.getDate()+"-"+timeEditor.getFullTime();
		boolean viewAll = prof.hasRoleFunction(Profile.ROLE_FUNCTION_SUPERVISOR);
		Logger.getLogger(APP_PKG_NAME).log(Level.FINER,"getInactiveGroup(" + date + ", " + viewAll+")");
        try {
            ig = ro().getInactiveGroup(getProfile(), date, viewAll);
        } catch (Exception exc) {
        	if(RMIMgr.shouldTryReconnect(exc)){	// try to reconnect
    			if (RMIMgr.getRmiMgr().reconnectMain()) {
    				try{
    					ig = ro().getInactiveGroup(getProfile(), date, viewAll);
    				}catch(Exception e){
    					if(RMIMgr.shouldPromptUser(e)){// show user msg and ask what to do
    						if (com.ibm.eacm.ui.UI.showMWExcPrompt(this, e) == RETRY) {
    							ig = getInactiveGroup();
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
    					ig =  getInactiveGroup();
    				}// else user decide to ignore or exit
    			}else{
    				com.ibm.eacm.ui.UI.showException(this,exc, "mw.err-title");
    			}
    		}
        }
        return ig;
    }
    /* (non-Javadoc)
     * @see com.ibm.eacm.tabs.DataActionPanel#disableActionsAndWait()
     */
    public void disableActionsAndWait(){
    	super.disableActionsAndWait();
    	if (table!=null){
    		table.setEnabled(false);
    	}
    	this.dateEditor.setEnabled(false);
    	this.datePopup.setEnabled(false);
    	this.timeEditor.setEnabled(false);
    	button.setEnabled(false);
    }
    /* (non-Javadoc)
     * @see com.ibm.eacm.tabs.DataActionPanel#enableActionsAndRestore()
     */
    public void enableActionsAndRestore(){
    	super.enableActionsAndRestore();
    	if(isWaiting()){ // all workers have not completed
    		return;
    	}
    	this.dateEditor.setEnabled(true);
    	this.datePopup.setEnabled(true);
    	this.timeEditor.setEnabled(true);
    	button.setEnabled(true);
    	if (table!=null){
    		table.setEnabled(true);
    	}
    	this.setTableAvailable();
    }
    //================================================================
    // refresh table
    private RefreshWorker refreshWorker=null;
    private class RefreshAction extends EACMAction
    {
		private static final long serialVersionUID = 1L;
		RefreshAction() {
            super(REFRESH_ACTION,KeyEvent.VK_F5, 0);
            putValue(javax.swing.Action.LARGE_ICON_KEY, Utils.getImageIcon("refresh.gif"));
   		}

		public void actionPerformed(ActionEvent e) {
			if(dateEditor.isValidDate()){
				// need to do this in the tab
				disableActionsAndWait();
				refreshWorker = new RefreshWorker();
				RMIMgr.getRmiMgr().execute(refreshWorker);
			}
		}
    }
    private class RefreshWorker extends DBSwingWorker<RowSelectableTable, Void> {
    	@Override
    	public RowSelectableTable doInBackground() {
    		RowSelectableTable rst=null;
    		try{
    			rst = getRestoreTable();
    		}catch(Exception ex){ // prevent hang
    			Logger.getLogger(APP_PKG_NAME).log(Level.SEVERE,"Exception ",ex);
    		}finally{
            	RMIMgr.getRmiMgr().complete(this);
            	refreshWorker = null;
    		}
			return rst;
    	}

        @Override
        public void done() {
            //this will be on the dispatch thread
        	try {
        		if(!isCancelled()){
        			RowSelectableTable rst = get();
        			if (rst !=null){
        				if (table == null) {
            				table = new RestoreTable(rst, getProfile());
            				jsp.setViewportView(table);
            				table.getSelectionModel().addListSelectionListener(RestoreActionTab.this);
            				table.addMouseListener(new MouseAdapter() { // base class deref will remove this
            		            public void mouseReleased(MouseEvent evt) {
            		                if (evt.isPopupTrigger()) {
            		                    popup.show(evt.getComponent(), evt.getX(), evt.getY());
            		                }
            		            }
            				});
            			    table.getColumnModel().addColumnModelListener(RestoreActionTab.this); // base class has listener methods
            			} else {
            				table.updateModel(rst,RestoreActionTab.this.getProfile());
            			}
        				setTableAvailable();
        			}
        		}
            } catch (InterruptedException ignore) {}
            catch (java.util.concurrent.ExecutionException e) {
            	listErr(e,"getting restore table");
            }finally{
            	enableActionsAndRestore();
            }
        }
     	public void notExecuted(){
     		Logger.getLogger(APP_PKG_NAME).log(Level.WARNING,"not executed");
     		enableActionsAndRestore();
        	refreshWorker = null;
    	}
    }
    // restore items
    private RestoreWorker restoreWorker=null;
    private class RestoreItemAction extends EACMAction
    {
		private static final long serialVersionUID = 1L;
		RestoreItemAction() {
            super(RESTORE_ACTION,KeyEvent.VK_R, Event.CTRL_MASK);
            putValue(javax.swing.Action.LARGE_ICON_KEY, Utils.getImageIcon("restore.gif"));
            setEnabled(false);
   		}

		public void actionPerformed(ActionEvent e) {
			// need to do this in the tab
			disableActionsAndWait();
			restoreWorker = new RestoreWorker();
			RMIMgr.getRmiMgr().execute(restoreWorker);
		}
    }

    private InactiveGroup removeInactiveItem(InactiveGroup group, InactiveItem[] items) {
    	InactiveGroup ig = null;
    	try {
    		ig = group.removeInactiveItem(null, ro(), getProfile(), items);
    	} catch (Exception exc) {
    		if(RMIMgr.shouldTryReconnect(exc)){	// try to reconnect
    			if (RMIMgr.getRmiMgr().reconnectMain()) {
    				try{
    					ig = group.removeInactiveItem(null, ro(), getProfile(), items);
    				}catch(Exception e){
    					if(RMIMgr.shouldPromptUser(e)){// show user msg and ask what to do
    						if (com.ibm.eacm.ui.UI.showMWExcPrompt(this, e) == RETRY) {
    							ig = removeInactiveItem(group, items);
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
    					ig = removeInactiveItem(group, items);
    				}// else user decide to ignore or exit
    			}else{
    				com.ibm.eacm.ui.UI.showException(this,exc, "mw.err-title");
    			}
    		}
    	}
    	return ig;
    }

    private class RestoreWorker extends DBSwingWorker<InactiveGroup, Void> {
    	private InactiveItem[] inAct = null;
    	@Override
    	public InactiveGroup doInBackground() {
    		InactiveGroup igg=null;
    		try{
    			inAct = (InactiveItem[]) table.getInactiveItems();
				igg = removeInactiveItem(ig, inAct);
    		}catch(Exception ex){ // prevent hang
    			Logger.getLogger(APP_PKG_NAME).log(Level.SEVERE,"Exception ",ex);
    		}finally{
            	RMIMgr.getRmiMgr().complete(this);
            	restoreWorker = null;
    		}
			return igg;
    	}

        @Override
        public void done() {
            //this will be on the dispatch thread
        	try {
        		if(!isCancelled()){
        			InactiveGroup oldIg = ig;
        			ig = get();
        			if (ig != null) {
						table.updateModel(ig.getTable(),getProfile());

						EACM.getEACM().refresh(inAct,getProfile());

						// run it again to get relators
				    	refreshWorker = new RefreshWorker();
						RMIMgr.getRmiMgr().execute(refreshWorker);
					}
        			// do it after the restore
        			if (oldIg!=null){
        				oldIg.dereference();
        	    	}
        		}
            } catch (InterruptedException ignore) {}
            catch (java.util.concurrent.ExecutionException e) {
            	listErr(e,"getting restore table");
            }finally{
            	if (refreshWorker==null){
            		enableActionsAndRestore();
            	}
            }
        }
     	public void notExecuted(){
     		Logger.getLogger(APP_PKG_NAME).log(Level.WARNING,"not executed");
     		enableActionsAndRestore();
     		restoreWorker = null;
    	}
    }

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode()==KeyEvent.VK_ENTER){ // error dialog comes in here too
			if(e.getSource()==dateEditor && dateEditor.isValidDate()){
			    getAction(REFRESH_ACTION).actionPerformed(null);// update the table
			}else if(e.getSource()==timeEditor.getTextField()){
			    getAction(REFRESH_ACTION).actionPerformed(null);// update the table
			}else if(e.getSource() instanceof JButton){
    			((JButton)e.getSource()).doClick(); // select a date in the popup
    		}

			e.consume();
		}
	}
}
