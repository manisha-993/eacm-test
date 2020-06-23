//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.tabs;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import COM.ibm.eannounce.objects.QueryActionItem;
import COM.ibm.eannounce.objects.QueryList;
import COM.ibm.opicmpdh.middleware.Profile;

import com.ibm.eacm.actions.FindNextAction;
import com.ibm.eacm.actions.FindRepAction;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.objects.Findable;
import com.ibm.eacm.toolbar.ComboItem;
import com.ibm.eacm.toolbar.DefaultToolbarLayout;


import com.ibm.eacm.table.BaseTable;
import com.ibm.eacm.table.QueryTable;
/**
 * tab for QueryAction
 * @author Wendy Stimpson
 */
//$Log: QueryActionTab.java,v $
//Revision 1.2  2013/02/07 13:37:38  wendy
//log close tab
//
//Revision 1.1  2012/09/27 19:39:23  wendy
//Initial code
//
public class QueryActionTab extends ActionTabPanel
{
	private static final long serialVersionUID = 1L;
	private QueryList qList = null;
    private JScrollPane tScroll = null;
	private QueryTable qTable = null;

    protected BaseTable getJTable() { return qTable;}

	/* (non-Javadoc)
	 * @see java.awt.Component#toString()
	 */
	public String toString(){
		if(qList!=null){
			return "Query: "+qList.getParentActionItem().getActionItemKey();
		}else{
			return super.toString();
		}
	}
	
	public Findable getFindable() {
		return qTable;
	}

	public QueryActionTab(QueryList ql){
		qList = ql;
		init();
	}
	public void dereference(){
		super.dereference();
		qList.dereference();
		qList = null;

        qTable.getSelectionModel().removeListSelectionListener(this);
        qTable.getColumnModel().removeColumnModelListener(this);
    	qTable.dereference();
    	qTable = null;

    	tScroll.removeAll();
    	tScroll.setUI(null);
    	tScroll = null;
	}
	/**
     * init
     *
     */
	private void init() {
    	setSessionTagText("N/A");
    	qTable = new QueryTable(qList.getTable(), getProfile());
    	qTable.addMouseListener(new MouseAdapter() { // base class deref will remove this
            public void mouseReleased(MouseEvent evt) {
                if (evt.isPopupTrigger()) {
                    popup.show(evt.getComponent(), evt.getX(), evt.getY());
                }
            }
		});

        qTable.getColumnModel().addColumnModelListener(this); // base class has listener methods

        createActions();//this needs qTable
        createMenus();
    	createPopupMenu();
        createToolbar();

        tScroll = new JScrollPane(qTable);

        JPanel tPnl = new JPanel(new BorderLayout());
        tPnl.add(tScroll,BorderLayout.CENTER);

        tPnl.add(getToolbar().getAlignment(), getToolbar());

        getAction(FINDREP_ACTION).setEnabled(qTable.getRowCount() > 0);
        getAction(FINDNEXT_ACTION).setEnabled(qTable.getRowCount() > 0);

        add(tPnl,BorderLayout.CENTER);

        qTable.getSelectionModel().addListSelectionListener(this);

		if (qTable.getRowCount() > 0) {
			qTable.setColumnSelectionInterval(0,0);
			qTable.setRowSelectionInterval(0,0);
		}
		//enable actions based on selection
		enableTableActions();
    }
    /**
     * createMenus
     */
    private void createMenus() {
    	createFileMenu();
        createEditMenu();
        createTableMenu();
    }
    /**
     * create the edit menu
     */
    private void createEditMenu() {
      	JMenu editMenu = new JMenu(Utils.getResource(EDIT_MENU));
      	editMenu.setMnemonic(Utils.getMnemonic(EDIT_MENU));

      	addLocalActionMenuItem(editMenu, SELECTALL_ACTION);
      	addLocalActionMenuItem(editMenu, SELECTINV_ACTION);

      	getMenubar().add(editMenu);
    }
	/**
	 * createTableMenu
	 */
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

        tblMenu.setEnabled(qTable.getRowCount() > 0);
	}

	/**
	 * create all of the actions, they are shared between toolbar and menu
	 */
    //protected
    public void createActions() {
    	super.createActions();
		addAction(new FindRepAction());
		addAction(new FindNextAction());
    	createTableActions(qTable);
	}


	public String getHelpText() {
		return qTable.getHelpText();
	}

	protected String getTabIconKey() {
		return "query.icon";
	}

	public ComboItem getDefaultToolbarLayout() {
		return DefaultToolbarLayout.QUERY_BAR;
	}

	public Profile getProfile() {
        return qList.getProfile();
	}

	protected String getTabMenuTitleKey() { return "query.title";}


	public String getTableTitle() {
		return qTable.getTableTitle();
	}
	protected boolean isFiltered() {
        return qTable.isFiltered();
	}
	public String getEntityType(int _i) {
		return ((QueryActionItem)qList.getParentActionItem()).getViewName();
	}

    /* (non-Javadoc)
     * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
     *
    public void valueChanged(ListSelectionEvent _lse) {
		if (!_lse.getValueIsAdjusting()) {
			//enable actions based on selection
			enableTableActions(qTable.getSelectedRow()!=-1);
		}
	}*/

    public void refreshActions(){
    	enableTableActions();
    }
}
