//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2008  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
package com.ibm.eannounce.eforms.action;

import java.awt.*;
import java.awt.event.*;

//import javax.swing.Icon;
//import javax.swing.JTable;
import javax.swing.UIManager;
import com.ibm.eannounce.eforms.toolbar.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.Profile;

import com.elogin.*;
import com.ibm.eannounce.eforms.table.QueryTable;
import com.ibm.eannounce.eforms.toolbar.EannounceToolbar;
import com.ibm.eannounce.eobjects.EScrollPane;
//import com.ibm.eannounce.eserver.ChatAction;
/**********************************************************************************
 * This class is used to display a view
 *
 */
//$Log: QueryAction.java,v $
//Revision 1.3  2009/05/26 13:00:14  wendy
//Performance cleanup
//
//Revision 1.2  2008/08/08 21:52:12  wendy
//CQ00006067-WI : LA CTO - More support for QueryAction
//
//Revision 1.1  2008/08/04 14:12:10  wendy
//CQ00006067-WI : LA CTO - Added support for QueryAction
//
public class QueryAction extends Action implements FocusListener,
		EAccessConstants 
{
	private static final long serialVersionUID = 1L;
	private QueryList qList = null;
	private EPanel tPnl = new EPanel(new BorderLayout());
    private EScrollPane tScroll = null;
	private QueryTable qTable = null; 
	private EannounceToolbar tTool = null;
	
	/**
	 * @param _parent
	 * @param _o
	 */
	protected QueryAction(ActionController _parent, Object _o) {
		super(_parent);//, _o);
        qList = (QueryList) _o;
        createMenus();
        init();
	}
    /**
     * createMenus
     */
    protected void createMenus() {
        createFileMenu();
        createEditMenu();
        createTableMenu();
    }

    /**
     * removeMenus
     */
    protected void removeMenus() {
        if (getMenubar() != null) { 
            removeFileMenu();
            removeEditMenu();
			//getMenubar().removeMenuItem("rfrsh", this);
            removeTableMenu();
            getMenubar().removeAll();
        } 
    }	
	/**
	 * createTableMenu without entity history actions
	 */  
	protected void createTableMenu() {
		String strKey = getString("tbl");
		getMenubar().addMenu(strKey, "left", this, KeyEvent.VK_LEFT, Event.CTRL_MASK, true);
		getMenubar().addMenu(strKey, "right", this, KeyEvent.VK_RIGHT, Event.CTRL_MASK, true); 
		getMenubar().addSeparator(strKey);
		getMenubar().addMenu(strKey, "srt", this, 0, 0, true);
		getMenubar().addSeparator(strKey); 
		getMenubar().addMenu(strKey, "hide", this, 0, 0, true); 
		getMenubar().addMenu(strKey, "unhide", this, 0, 0, true); 
		getMenubar().addSeparator(strKey);
		getMenubar().addMenu(strKey, "f/r", this, KeyEvent.VK_F, Event.CTRL_MASK, true);
		getMenubar().addMenu(strKey, "fltr", this, KeyEvent.VK_F8, 0, true); 
//		getMenubar().addSeparator(strKey);
//		getMenubar().addMenu(strKey, "rfrsh", this, KeyEvent.VK_F5, 0, true);
		getMenubar().setMenuMnemonic(strKey, getChar("tbl-s"));
	}	
    private void createEditMenu() {
        String strKey = getString("edit");
        getMenubar().addMenu(strKey, "selA", this, KeyEvent.VK_A, Event.CTRL_MASK, true);
        getMenubar().addMenu(strKey, "iSel", this, KeyEvent.VK_I, Event.CTRL_MASK, true);
    }

    private void removeEditMenu() {
    	getMenubar().removeMenuItem("selA", this);
    	getMenubar().removeMenuItem("iSel", this);
    }
	/**
     * init
     *
     */
    protected void init() {
        super.init();
        Dimension min = null;
        if (!EAccess.isArmed(ACCESSIBLE_ARM_FILE)) {
            tTool = ToolbarController.generateToolbar(DefaultToolbarLayout.QUERY_BAR, this, null);
        }
    	qTable = new QueryTable(qList, qList.getTable(), getActionController());
    	qTable.addFocusListener(this);
    	qTable.setParentObject(this);
    	qTable.sort(); 
    	tScroll = new EScrollPane(qTable);

    	min = UIManager.getDimension("eannounce.minimum");
    	tScroll.setMinimumSize(min);
    	tPnl.add("Center", tScroll);
        if (tTool != null) {
            tTool.setMinimumSize(min);
            tPnl.add(tTool.getAlignment(), tTool);
        }
    	qTable.defaultSelect(); 
    	
        getSplitPane().setLeftComponent(getActionController().getTree());
        getSplitPane().setRightComponent(tPnl);
    }
    
    /**
     * Get the view name used for this action
     * @return String
     */
    protected String getViewName(){
    	return ((QueryActionItem)qList.getParentActionItem()).getViewName();
    }
    
    /* (non-Javadoc)
     * @see com.ibm.eannounce.eforms.action.Action#getSearchableObject()
     */
    protected Object getSearchableObject() { 
        return qTable; 
    }
    /* (non-Javadoc)
     * @see com.ibm.eannounce.eforms.action.Action#dereference()
     */
    public void dereference(){
        if (tPnl != null) {
            tPnl.removeAll();
            tPnl.removeNotify();
            tPnl = null;
        }

        if (qList != null){
        	qList.dereference();
        	qList = null;
        }

        if (qTable != null) {
            qTable.removeFocusListener(this);
            qTable.dereference();
            //qTable.removeAll();
            //qTable.removeNotify();
            qTable = null;
        }

        if (tScroll != null) {
            tScroll.dereference();
            tScroll = null;
        }

        if (tTool != null) {
            tTool.dereference();
            tTool = null;
        }

        super.dereference();
    }
    
	/* (non-Javadoc)
	 * @see com.ibm.eannounce.eforms.action.Action#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent _ae) {
        String action = _ae.getActionCommand();
        appendLog("QueryAction.actionPerformed(" + action + ")");
        if (isBusy()) {
            appendLog("    I am busy");
            return;
        }

        try{
        	setBusy(true);
        	if (action.equals("clsT")) {
        		eaccess().close(getActionController());
        		return;
        	} else if (action.equals("clsA")) { 
        		eaccess().closeAll(); 
        		return;
        	} else if (action.equals("exit")) {
        		eaccess().exit("exit query");
        		return;
        	} 
        	Long t11 = EAccess.eaccess().timestamp("QueryAction performAction "+action+" started");
        	if (action.equals("saveT")) {
        		eaccess().save(qList);
        	} else if (action.equals("f/r")) {
        		find();
        	} else if (action.equals("fltr")) {
        		qTable.showFilter();
        	} else if (action.equals("srt")) {
        		qTable.showSort();
        	} else if (action.equals("hide")) { 
        		showHide(true); 
        	} else if (action.equals("unhide")) { 
        		showHide(false); 
        	} else if (action.equals("left")) {
        		qTable.moveColumn(true);
        	} else if (action.equals("right")) {
        		qTable.moveColumn(false);
        	} else if (action.equals("fmi")) {
        		qTable.showInformation();
        	} else if (action.equals("capture")) {
        		if (qList != null) {
        			eaccess().capture(qList.dump(false));
        		}
        	} else if (action.equals("selA")) {
        		qTable.selectAll();
        	} else if (action.equals("iSel")) {
        		qTable.invertSelection();
        	} /*else if (action.equals("rfrsh")) {
        		refresh();
        	}*/
        	EAccess.eaccess().timestamp("QueryAction performAction "+action+" ended",t11);
        }catch(Exception exc){
        	eaccess().showException(exc, null,ERROR_MESSAGE,OK);
        }        
        setBusy(false);
        
	}
 		
    /**
     * showHide
     * @param _b
     */
    private void showHide(boolean _b) {
        if (qTable != null) {
            qTable.showHide(_b);
            eaccess().setHidden(_b); 
        }
    }
    
	/* (non-Javadoc)
	 * @see com.ibm.eannounce.eforms.action.Action#find()
	 */
	private void find() {
		qTable.showFind();
	}

	/* (non-Javadoc)
	 * @see com.ibm.eannounce.eforms.action.Action#getPanelType()
	 */
	public String getPanelType() {
		return TYPE_QUERYACTION;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
	 */
	public void focusGained(FocusEvent _fe) {
		Component c = _fe.getComponent();
		if (tTool != null) {
			tTool.setEnabled("f/r", c == qTable);
			tTool.setEnabled("fltr", c == qTable);
		}
		getMenubar().setMenuEnabled("Table", c == qTable);
	}
	public void focusLost(FocusEvent e) {}
	/* (non-Javadoc)
	 * @see com.ibm.eannounce.eforms.action.Action#process(java.lang.String, java.lang.String, java.lang.String[], java.lang.String[])
	 */
	protected void process(String _method, String _action, String[] _parent, String[] _child) {
		qTable.highlight(_parent);
	}
	
	/* (non-Javadoc)
	 * @see com.ibm.eannounce.eforms.action.Action#isFiltered()
	 */
	protected boolean isFiltered() {
        if (qTable != null) { 
            return qTable.isFiltered();
        } 
        return false; 
	}
	/* (non-Javadoc)
	 * @see com.ibm.eannounce.eforms.action.Action#isHidden()
	 */
	protected boolean isHidden() {
		return qTable.isHidden();
	}
	/* (non-Javadoc)
	 * @see com.ibm.eannounce.eforms.action.Action#getHelpText()
	 */
	protected String getHelpText() {
		return qTable.getHelpText();
	}

	/* (non-Javadoc)
	 * @see com.ibm.eannounce.eforms.action.Action#getProfile()
	 */
	protected Profile getProfile() {
        if (qList == null) {
            return null;
        }
        return qList.getProfile();		
	}	

	/* (non-Javadoc)
	 * @see com.ibm.eannounce.eforms.action.Action#getTabMenuTitle()
	 */
	protected String getTabMenuTitleKey() { return "query.title";}
	
	/* (non-Javadoc)
	 * @see com.ibm.eannounce.eforms.action.Action#getTabIcon()
	 */
	protected String getTabIconKey() {
		return "query.icon";
	}	
	/* (non-Javadoc)
	 * @see com.ibm.eannounce.eforms.action.Action#okToClose(boolean)
	 * /
	public boolean okToClose(boolean _b) {
		return true;
	}*/
	/* (non-Javadoc)
	 * @see com.ibm.eannounce.eforms.action.Action#requestFocus(int)
	 */
	protected void requestFocus(int _i) {
		if (qTable != null) {
			qTable.requestFocus();
		}
	}
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#requestFocus()
	 */
	public void requestFocus() {
		if (qTable != null) {
			qTable.requestFocus();
		}
	}
	/* (non-Javadoc)
	 * @see com.ibm.eannounce.eforms.action.Action#getTableTitle()
	 */
	protected String getTableTitle() {
		if (qTable != null) {
			return qTable.getTableTitle();
		}
		return "TBD View";		
	}

	/* (non-Javadoc)
	 * @see com.ibm.eannounce.eforms.action.Action#selectKeys(java.lang.String[])
	 */
	protected void selectKeys(String[] _keys) {
        if (qTable != null) {
            qTable.selectKeys(_keys);
        }
	}

	
	//======================================================================================
	// unused or obsolete
	//public void copy() {}
	//public ChatAction getChatAction() {
		//return null;
	//}
	//public boolean update() {
//		return false;
	//}

	/* (non-Javadoc)
	 * @see com.ibm.eannounce.eforms.action.Action#refresh()
	 * /
	public void refresh() {
		
	}*/
	//public void paste() {}
	//public void closeLocalMenus() {	}
	//public void refreshMenu() {}
	//public void refreshToolbar() {}
	//public void sort(boolean _ascending) {}
	//public boolean contains(EntityItem[] _ei, EANActionItem _eai) {
		//return false;
	//}
	//public void moveColumn(boolean _left) {}
	
	//public void performAction(EANActionItem _ai, int _navType) {}
	/* (non-Javadoc)
	 * @see com.ibm.eannounce.eforms.action.Action#getTable()
	 * /
	public JTable getTable() {
		return qTable;
	}	*/
	/* (non-Javadoc)
	 * @see com.ibm.eannounce.eforms.action.Action#getTabToolTipText()
	 * /
	public String getTabToolTipText() {
		return getTabMenuTitle();
	}	*/
	/* (non-Javadoc)
	 * @see com.ibm.eannounce.eforms.action.Action#getTabTitle()
	 * /
	public String getTabTitle() {
		String name = null;
		setCode("tab.title");
		setParmCount(2);
		setParm(0, getActionController().getTableTitle());
		setParm(1, getActionController().getProfile().toString());
		name = getMessage();
		eaccess().clear();
		return name;
	}	*/
}
