//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.ui;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.JTableHeader;

import COM.ibm.eannounce.objects.*;

import com.ibm.eacm.EACM;
import com.ibm.eacm.actions.*;

import com.ibm.eacm.objects.Findable;
import com.ibm.eacm.objects.FindableComp;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.table.BaseTable;
import com.ibm.eacm.table.RSTTable;
import com.ibm.eacm.tabs.TabPanel;
import com.ibm.eacm.ui.EACMFrame;

/******************************************************************************
* This is used to display the picklist for matrix and whereused and navigate
* @author Wendy Stimpson
*/
// $Log: PickFrame.java,v $
// Revision 1.4  2015/03/13 18:30:55  stimpsow
// allow alt+f4 to get to the frame, toolbar was grabbing it
//
// Revision 1.3  2013/09/25 11:04:55  wendy
// expose init to derived classes
//
// Revision 1.2  2013/09/13 18:33:40  wendy
// select correct entity in showdata
//
// Revision 1.1  2012/09/27 19:39:11  wendy
// Initial code
//
public abstract class PickFrame extends EACMFrame implements ListSelectionListener, ChangeListener,FindableComp
{
	private static final long serialVersionUID = 1L;
	/** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2012  All Rights Reserved.";
    /** cvs version */
    public static final String VERSION = "$Revision: 1.4 $";

    private JPanel navpickPanel = new JPanel(new BorderLayout());
    private JPanel pnlMain = new JPanel(null);

	protected EntityListTabbedPane tabbedPane = null;
    protected TabPanel actionTab = null;
    private JLabel lblFilter = new JLabel(Utils.getImageIcon("fltr.gif"));
    private JTabbedPane tabPane = new JTabbedPane();

	/* (non-Javadoc)
	 * @see com.ibm.eacm.objects.FindableComp#getFindable()
	 */
	public Findable getFindable() {
		return tabbedPane.getTable();
	}

    /**
     * get unique id for this frame
     * @return
     */
    public String getUID() {
    	return actionTab.getUID()+":"+tabbedPane.getEntityList().getParentActionItem().getActionItemKey();
    }

    /**
     * enable label
     * @param _filter
     */
    public void setFilter(boolean _filter) {
    	lblFilter.setEnabled(_filter);
    }
	/* (non-Javadoc)
	 * @see com.ibm.eacm.ui.EACMFrame#fixTableHeaderCursorBug(java.awt.Cursor)
	 */
	protected void fixTableHeaderCursorBug(Cursor cursor){
		//tableheaders do not consistently display correct cursor
		for (int i=0; i< tabbedPane.getTabCount(); i++){
			if(tabbedPane.getTable(i)!=null){ //do all tables
				JTableHeader header = tabbedPane.getTable(i).getTableHeader();
				if (header != null) {
					header.setCursor(cursor);
				}
			}
		}
	}

	/**
	 * @param tab
	 * @param list
	 */
	public PickFrame(TabPanel tab, EntityList list)  {
		super("pick.panel");

		//display the entitylist in a tabbedpane
		tabbedPane = new EntityListTabbedPane(new MouseAdapter() {
			public void mouseReleased(MouseEvent evt) {
				if (evt.isPopupTrigger()) {
					popup.show(evt.getComponent(), evt.getX(), evt.getY());
				}
			}
		},this);
		tabbedPane.load(list);
		if(tabbedPane.getTabCount()>0){
			tabbedPane.setSelectedIndex(0);
		}
		// use this for added tabs or selected tab chgs
		tabbedPane.addChangeListener(this);

    	setIconImage(Utils.getImage(DEFAULT_ICON));

    	actionTab = tab;

        init();

		addComponentListener(new ComponentAdapter(){
			//Ensure the tabbed pane gets focus
			public void componentShown(ComponentEvent ce) {
				tabbedPane.getTable(0).requestFocusInWindow();
			}
		});
        setPreferredSize(new Dimension(400, 400));

        finishSetup(EACM.getEACM());
        setResizable(true);

        refreshActions();
    }

	protected BaseTable getJTable() {
		return tabbedPane.getTable();
	}

	/**
	 * @return
	 */
	public EntityList getEntityList(){
		return tabbedPane.getEntityList();
	}
    /**
     * enable remove selected action based on current selection
     * listener is added to each tab in the tabbedpane when the tab is created
     * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
     */
    public void valueChanged(ListSelectionEvent _lse) {
        if (!_lse.getValueIsAdjusting()) {
        	refreshActions();
        }
    }
	/**
	 * called when user selects a tab or tab is added or removed
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	public void stateChanged(ChangeEvent _ce) {
		boolean hasTabs = tabbedPane.getTabCount()!=0;

		// these depend on current tab
		setFilter(hasTabs && tabbedPane.getTable().isFiltered());

		if (hasTabs){
			//SELECTALL_ACTION
			EACMAction act = getAction(SELECTALL_ACTION);
			BaseTable tbl = tabbedPane.getTable();
			if (act!=null){
				((SelectAllAction)act).setTable(tbl);
			}
			//SELECTINV_ACTION
			act = getAction(SELECTINV_ACTION);
			if (act!=null){
				((SelectInvertAction)act).setTable(tbl);
			}
			//FILTER_ACTION
			act = getAction(FILTER_ACTION);
			if (act!=null){
				((FilterAction)act).setFilterable(tbl);
				act.setEnabled(hasTabs);
			}
			//SORT_ACTION
			act = getAction(SORT_ACTION);
			if (act!=null){
				((SortAction)act).setSortable(tbl);
				act.setEnabled(hasTabs);
			}
			//ENTITYDATA_ACTION
			act = getAction(ENTITYDATA_ACTION);
			if (act!=null){
				((EntityDataAction)act).setTable(tabbedPane.getTable());
			}
		}else{
			//SELECTALL_ACTION
			getAction(SELECTALL_ACTION).setEnabled(false);
			getAction(SELECTINV_ACTION).setEnabled(false);
			getAction(FILTER_ACTION).setEnabled(false);
			getAction(SORT_ACTION).setEnabled(false);
		}
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.ui.EACMFrame#refreshActions()
	 */
	protected void refreshActions() {
		//ENTITYDATA_ACTION
		EACMAction act = getAction(ENTITYDATA_ACTION);
		if (act!=null){
			act.setEnabled(true);
		}
		
		super.refreshActions();
    }
    /**
     * init frame components
     */
    protected void init() {
    	createActions();

    	createMenuBar();
    	createPopupMenu();

    	createToolbar();
    	adjustToolBarButtons();

    	lblFilter.setEnabled(false);
    	lblFilter.setToolTipText(Utils.getToolTip("fltrOn"));
    	lblFilter.setHorizontalAlignment(SwingConstants.CENTER);
    	lblFilter.setVerticalAlignment(SwingConstants.CENTER);

    	GroupLayout layout = new GroupLayout(pnlMain);
    	pnlMain.setLayout(layout);
    	layout.setAutoCreateGaps(true);
    	layout.setAutoCreateContainerGaps(true);

    	GroupLayout.ParallelGroup leftToRight = layout.createParallelGroup();

    	leftToRight.addComponent(lblFilter, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);// this centers it
    	leftToRight.addComponent(tabbedPane);

    	GroupLayout.SequentialGroup topToBottom = layout.createSequentialGroup();

    	topToBottom.addComponent(lblFilter);
    	topToBottom.addComponent(tabbedPane);

    	layout.setHorizontalGroup(leftToRight);
    	layout.setVerticalGroup(topToBottom);

    	navpickPanel.add(tBar,BorderLayout.NORTH);
    	navpickPanel.add(tabPane,BorderLayout.CENTER);

        tabPane.addTab(getTitle(), pnlMain);

    	getContentPane().add(navpickPanel);
    }
    /**
     * createToolbar using same actions as menus
     */
    protected void createToolbar() {
    	tBar = new JToolBar(){
    		private static final long serialVersionUID = 1L;
    	    /* (non-Javadoc)
    	     * need to copy this method to override the button keybinding
    	     * @see javax.swing.JToolBar#createActionComponent(javax.swing.Action)
    	     */
    	    protected JButton createActionComponent(Action a) {
    	        JButton b = new JButton() {
    				private static final long serialVersionUID = 1L;
    				protected PropertyChangeListener createActionPropertyChangeListener(Action a) {
    	                PropertyChangeListener pcl = createActionChangeListener(this);
    	                if (pcl==null) {
    	                    pcl = super.createActionPropertyChangeListener(a);
    	                }
    	                return pcl;
    	            }
    	    		/* (non-Javadoc)
    	    		 * keystrokes are going to the toolbar buttons, ALT-F4 was not going to the frame
    	    		 * @see javax.swing.JComponent#processKeyBinding(javax.swing.KeyStroke, java.awt.event.KeyEvent, int, boolean)
    	    		 */
    	    		protected boolean processKeyBinding(KeyStroke ks, KeyEvent e, int condition, boolean pressed) {
    	              	if(e.getKeyCode()==KeyEvent.VK_ALT || e.getKeyCode()==KeyEvent.VK_F4){
    	                	return false;
    	            	}
    	    			return super.processKeyBinding(ks, e, condition, pressed);
    	    		}
    	        };
    	        if (a != null && (a.getValue(Action.SMALL_ICON) != null ||
    	                          a.getValue(Action.LARGE_ICON_KEY) != null)) {
    	            b.setHideActionText(true);
    	        }
    	        b.setHorizontalTextPosition(JButton.CENTER);
    	        b.setVerticalTextPosition(JButton.BOTTOM);
    	        return b;
    	    }
    	};
    	tBar.setFloatable(false);
    //dont support break, get into limbo	tBar.add(ELogin.getEACM().getGlobalAction(BREAK_ACTION));
    }
    /**
     * createMenuBar
     */
    private void createMenuBar() {
        menubar = new JMenuBar();
    	createFileMenu();
    	createEditMenu();
    	createActionMenu();
    	createTableMenu();

    	setJMenuBar(menubar);
    }

    private void createFileMenu() {
        JMenu mnuFile = new JMenu(Utils.getResource(FILE_MENU));
        mnuFile.setMnemonic(Utils.getMnemonic(FILE_MENU));

    	JMenuItem mi = mnuFile.add(closeAction);
    	mi.setVerifyInputWhenFocusTarget(false);

		menubar.add(mnuFile);
    }

    private void createEditMenu() {
        JMenu editMenu = new JMenu(Utils.getResource(EDIT_MENU));
        editMenu.setMnemonic(Utils.getMnemonic(EDIT_MENU));

        EACMAction act = getAction(VIEW_ACTION);
        if(act!=null){
            addLocalActionMenuItem(editMenu, VIEW_ACTION);
            editMenu.addSeparator();
        }

        addLocalActionMenuItem(editMenu, SELECTALL_ACTION);
        addLocalActionMenuItem(editMenu, SELECTINV_ACTION);

        editMenu.addSeparator();
		addLocalActionMenuItem(editMenu, FINDREP_ACTION);
		addLocalActionMenuItem(editMenu, FINDNEXT_ACTION);

		menubar.add(editMenu);
    }
    private void createTableMenu(){
        JMenu mnuTbl = new JMenu(Utils.getResource(TABLE_MENU));
        mnuTbl.setMnemonic(Utils.getMnemonic(TABLE_MENU));

        addLocalActionMenuItem(mnuTbl, SORT_ACTION);
        addLocalActionMenuItem(mnuTbl, FILTER_ACTION);

        if (Utils.isTestMode()) {
            addLocalActionMenuItem(mnuTbl, ENTITYDATA_ACTION);
        }

		menubar.add(mnuTbl);
    }
    protected abstract void createActionMenu();

    /**
     * create all of the actions, they are shared between toolbar and menu
     */
    protected void createActions() {
    	closeAction = new CloseFrameAction(this);

        RSTTable table = tabbedPane.getTable(0);

		//FILTER_ACTION
		addAction(new com.ibm.eacm.actions.FilterAction(this,table));
		//SORT_ACTION
		addAction(new com.ibm.eacm.actions.SortAction(this,table));

    	//SELECTALL_ACTION
		addAction(new SelectAllAction(table));

		//SELECTINV_ACTION
		addAction(new SelectInvertAction(table));
		 //ENTITYDATA_ACTION
		addAction(new EntityDataAction(table));

		addAction(new FindRepAction());
		addAction(new FindNextAction());
    }

    public void setVisible(boolean b) {
    	if(!b && !isWaiting()) {// enable caller actions if our work is done, if user closed before done, dont enable caller
    		actionTab.enableActionsAndRestore();
    	}
        super.setVisible(b);
    }

    /**
     * dereference
     *
     */
    public void dereference() {
	    ComponentListener[] l = getComponentListeners();
	    for (int i = 0; i < l.length; ++i) {
	    	removeComponentListener(l[i]);
	    }

	    EntityList list = tabbedPane.getEntityList();
	    if(list!=null){
	    	list.dereference();
	    	list = null;
	    }

        tabPane.removeAll();
        tabPane.setUI(null);
        tabPane = null;

        navpickPanel.removeAll();
        navpickPanel.setUI(null);
        navpickPanel = null;

        pnlMain.removeAll();
        pnlMain.setUI(null);
        pnlMain = null;

        actionTab = null;

    	tabbedPane.removeChangeListener(this);
		tabbedPane.dereference();
		tabbedPane = null;

        lblFilter.removeAll();
        lblFilter.setUI(null);
        lblFilter = null;

        super.dereference();
    }
}
