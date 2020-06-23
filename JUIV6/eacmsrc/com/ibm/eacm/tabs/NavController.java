//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.tabs;


import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.transactions.NLSItem;

import java.awt.*;
import java.awt.event.KeyEvent;

import java.beans.PropertyChangeEvent;
import java.util.Hashtable;

import javax.swing.*;
import javax.swing.border.Border;

import com.ibm.eacm.*;
import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.actions.ResetDateAction;

import com.ibm.eacm.cart.*;

import com.ibm.eacm.mw.DBUtils;
import com.ibm.eacm.mw.LoginMgr;
import com.ibm.eacm.nav.NavDataDouble;
import com.ibm.eacm.nav.NavHistBox;
import com.ibm.eacm.nav.Navigate;

import com.ibm.eacm.navform.Generator;
import com.ibm.eacm.objects.*;

import com.ibm.eacm.preference.*;
import com.ibm.eacm.table.BaseTable;
import com.ibm.eacm.table.EntityGroupTable;
import com.ibm.eacm.toolbar.*;


/**
 * this class manages the navigate(s), could be single or dual
 * @author Wendy Stimpson
 */
//$Log: NavController.java,v $
//Revision 1.8  2015/01/05 19:15:34  stimpsow
//use Theme for background colors
//
//Revision 1.7  2014/10/03 11:08:07  wendy
//IN5515352 remove F8 keyboard mapping
//
//Revision 1.6  2013/11/08 20:49:42  wendy
//prevent null ptr
//
//Revision 1.5  2013/10/15 17:19:19  wendy
//check current navigate for iswaiting
//
//Revision 1.4  2013/07/29 18:23:15  wendy
//splitpane divider was creeping
//
//Revision 1.3  2013/02/07 13:37:38  wendy
//log close tab
//
//Revision 1.2  2012/11/09 20:47:59  wendy
//check for null profile from getNewProfileInstance()
//
//Revision 1.1  2012/09/27 19:39:23  wendy
//Initial code
//
public class NavController extends TabPanel implements Cartable
{
	private static final long serialVersionUID = 1L;

	private JPanel pnlMain = new JPanel(new BorderLayout());
	private JPanel pnlNorth = new JPanel(new BorderLayout());
	private JLabel sesLbl = new JLabel();
	private JLabel cgLbl = new JLabel();

	private Navigate[] nav = null;
	private Navigate curNav = null;
	private JScrollPane jsp = null;

	private boolean bPin = false;

	private JSplitPane split = null;

	private boolean bMark = false;
	private String strCG = "";

	private CartList cart = null; //only one needed for both navs
	private NavCartFrame cartFrame = null;

	/* (non-Javadoc)
	 * @see java.awt.Component#toString()
	 */
	public String toString(){
		if(nav !=null){
			StringBuilder sb = new StringBuilder();
			for(int i=0;i<nav.length; i++){
				if(nav[i]!=null){
					if(sb.length()>0){
						sb.append(NEWLINE);
					}
					sb.append(nav[i]);
				}
			}
			return sb.toString();
		}else{
			return super.toString();
		}
	}
	/**
	 * @param p
	 */
	public NavController(Profile p) {
		setDoubleBuffered(true);
		add(pnlMain,BorderLayout.CENTER);
		add(pnlNorth,BorderLayout.NORTH);
		pnlNorth.add(sesLbl,BorderLayout.WEST);
		pnlNorth.add(cgLbl,BorderLayout.EAST);

		setChangeGroupTagText(null);
		cart = CartList.getCartList(this,p);

		// track focus so current navigate can be set, needed for dual nav
		if (NavLayoutPref.isDualNavLayout()){
			KeyboardFocusManager focusManager =
				KeyboardFocusManager.getCurrentKeyboardFocusManager();
			focusManager.addPropertyChangeListener("focusOwner",this);
		}

		generateForm();
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.tabs.DataActionPanel#getJTable()
	 */
	protected BaseTable getJTable() {
		return getNavigate().getJTable();
	}
	/**
	 * getCart
	 * @return
	 */
	public CartList getCart() {
		return cart;
	}
	/**
	 * updateTabPlacement
	 *
	 */
	public void updateTabPlacement() {
		if (nav[0] != null) {
			nav[0].updateTabPlacement();
		}
		if (nav[1] != null) {
			nav[1].updateTabPlacement();
		}
	}

	/**
	 * setSessionTagText
	 * @param s
	 */
	public void setSessionTagText(String s) {
		if (s != null) {
			//sesLbl= You are currently working in {0}.
			String msg= Utils.getResource("sesLbl",s);

			sesLbl.setText(msg);
			sesLbl.setToolTipText(msg);
		}
		sesLbl.setVisible(s != null);
	}

	/**
	 * setPin
	 * @param b
	 */
	public void setPin(boolean b) {
		bPin = b;
	}

	/**
	 * isPin
	 * @return
	 */
	public boolean isPin() {
		return bPin;
	}

	/**
	 * create navigate(s)
	 */
	private void generateForm() {
		nav = new Navigate[2];
		nav[0] = new Navigate(this, 0);
		// reduce memory use and improve perf by not creating double navs if not needed
		if (NavLayoutPref.isDualNavLayout()){
			nav[1] = new Navigate(this, 1);
		}else{
			nav[1] =null;
		}
	}

	/**
	 * load from navigation
	 * @param parTab
	 * @param history
	 * @param list
	 * @param prof
	 * @return
	 */
	public static NavController loadFromNav(TabPanel parTab, NavHistBox history, EntityList list, Profile prof) {
		Profile newprof = LoginMgr.getNewProfileInstance(list.getProfile());
		if(newprof!=null){
			NavController out = new NavController(newprof);
			out.setParentProfile(prof);
			out.init(list,history);

			EACM.getEACM().addTab(parTab, out);
			return out;
		}
		return null;
	}

	/**
	 * initialize navigate panels with this entitylist, called when a new wg role tab is opened
	 * or from a link search or nav action
	 * @param list
	 */
	public void init(EntityList list) {
		init(list, null,null);
	}

	/**
	 * used from search and loadfromnav
	 * @param list
	 * @param hist
	 */
	public void init(EntityList list,NavHistBox hist) {
		init(list, null,hist);
	}
	/**
	 * this is called for all new nav tabs
	 * @param prev
	 * @param item
	 * @param hist
	 * @return
	 */
	private void init(EntityList prev, BookmarkItem item, NavHistBox hist) {
		curNav = nav[0]; // init of tables needs curnav
		Generator navgen = new Generator(this);

		// both navigates are sharing the same entitylist, clone for second navigate
		nav[0].setSeed(prev);
		if (nav[1] != null) {
			// if this is a bookmark, then get a naventry
			EntityList other = null;
			if(item!=null){
				other = DBUtils.getNavigateEntry(EACM.getEACM().getActiveProfile());
			}else{
				other = prev.cloneStructure();
			}
			nav[1].setSeed(other);
		}
		navgen.generateForm();

		jsp = new JScrollPane(navgen.getMainPanel());

		pnlMain.add(jsp,BorderLayout.CENTER);
		if(item != null){
			nav[0].loadBookmarkHistory(item);
		}
		if (hist != null) {
			nav[0].loadHistory(hist);
			if (nav[1] != null) {
				nav[1].loadHistory(hist);
			}
		}

		curNav = null; // allow nav[0] to setup properly
		setCurrentNavigate(nav[0]);

		navgen.dereference();
		navgen = null;

		if (split!=null){
			//do this to after generate is done
			split.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, this);

			//IN5515352 remove F8 keyboard mapping
			KeyStroke keyToRemove = KeyStroke.getKeyStroke(KeyEvent.VK_F8, 0);
			Utils.removeKeyBoardMapping(split, keyToRemove);
		}
	}

	/**
	 * load into a new tab
	 * @param list
	 * @param book
	 */
	private static void loadBookmarkInNewTab(EntityList list, BookmarkItem book) {
		NavController navCtrl = new NavController(list.getProfile());
		navCtrl.setParentProfile(list.getProfile());
		navCtrl.init(list, book, null);
		navCtrl.setBookmark(true);
		EACM.getEACM().addTab(null, navCtrl);
	}
	/**
	 * called from evt dispatch thread in bookmarkframe.loadworker
	 * @param list
	 * @param book
	 */
	public static void loadBookmark(EntityList list, BookmarkItem book) {
		if (BehaviorPref.loadBookmark()) { //load into a new tab
			loadBookmarkInNewTab(list, book);
		} else {
			TabPanel tab = EACM.getEACM().getCurrentTab();
			if (tab instanceof NavController) {
				NavController navCtrl = (NavController) tab;
				if(navCtrl.isPin()){
					loadBookmarkInNewTab(list, book);
				}else{
					// replace the current contents
					navCtrl.setBookmark(true);
					Navigate tmpNav = navCtrl.getNavigate();

					tmpNav.resetHistory();
					tmpNav.load(list, book, Navigate.NAVIGATE_INIT_LOAD);

					EACM.getEACM().setTitleAt(tab, tmpNav.getTabTitle());
					EACM.getEACM().setIconAt(tab, tab.getTabIcon());
				}
			}
		}
	}
	/**
	 * getTableTitle
	 *
	 * @return
	 */
	public String getTableTitle() {
		return Utils.getTabTitle("navTab", getProfile());
	}

	/**
	 * getProfile
	 *
	 * @return
	 */
	public Profile getProfile() {
		if(curNav != null){
			return curNav.getProfile();
		}
		return null;
	}
	   /**
     * change the read language to this nlsitem
     * @param nls
     */
    public void setReadLanguage(NLSItem nls){
    	if (nav != null) {
    		for(int i=0;i<nav.length;i++){
    			if(nav[i]!=null){
    				nav[i].setReadLanguage(nls);
    			}
    		}
    	}	
    }
	/**
	 * getNavigate - used by generator
	 * @param i
	 * @return
	 */
	public Navigate getNavigate(int i) {
		return nav[i];
	}

	/**
	 * get current Navigate
	 * @return
	 */
	public Navigate getNavigate() {
		return curNav;
	}

	/* (non-Javadoc)
	 * pass it on to the current navigate
	 * @see com.ibm.eacm.tabs.DataActionPanel#enableActionsAndRestore()
	 */
	public void enableActionsAndRestore(){
		getNavigate().enableActionsAndRestore();
	}
	/* (non-Javadoc)
	 * pass it on to the current navigate
	 * @see com.ibm.eacm.tabs.DataActionPanel#disableActionsAndWait()
	 */
	public void disableActionsAndWait(){
		getNavigate().disableActionsAndWait();
	}
	/**
	 * is this waiting for an action to complete
	 * @return
	 */
	public boolean isWaiting() { 
		if(getNavigate()==null){
			return false;
		}
		return getNavigate().isWaiting();
	}
	/**
	 * setCurrentNavigate and update actions to reflect nav
	 * @param diffnav
	 *
	 */
	public void setCurrentNavigate(Navigate diffnav) {
		if(sesLbl == null){
			return; // deref has run
		}
		if (!diffnav.equals(curNav)) {
			if(curNav!=null){
				pnlMain.remove(curNav.getToolbar());
			}
			curNav = diffnav;
			EACM.getEACM().updateMenuBar(curNav.getMenubar());

			setSessionTagText(diffnav.getTagDisplay());

			curNav.updateMenuActions();

			pnlMain.add(curNav.getToolbar().getAlignment(), curNav.getToolbar());
			cart.updateActions(); 
			
			revalidate(); // needed to layout toolbar properly
			curNav.requestFocusInWindow(); // when dualnav is loaded, focus is needed for accelerator keys
			repaint(); // needed to draw border on dualnav properly
		}
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.tabs.TabPanel#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent e) {
		String prop = e.getPropertyName();
		if (("focusOwner".equals(prop))){
			Object obj = e.getNewValue();
			Navigate navToFocus = null;
			if (obj instanceof Component){
				Component comp = (Component)obj;
				while (comp != null){
					if (comp instanceof NavDataDouble){
						navToFocus = ((NavDataDouble)comp).getNavigate();
						break;
					}
					if(comp instanceof NavHistBox){
						navToFocus =((NavHistBox)comp).getNavigate();
						break;
					}
					comp = comp.getParent();
				}
				
				if(navToFocus!=null){
					// only recognize navigates belonging to this navcontroller
					if (NavLayoutPref.isDualNavLayout()){
						if(navToFocus.getUID().equals(nav[0].getUID())||
								navToFocus.getUID().equals(nav[1].getUID())){}
						else{
							navToFocus = null;
						}
					}else{
						if(!navToFocus.getUID().equals(nav[0].getUID())){
							navToFocus = null;
						}
					}
				}
			}
			if (navToFocus !=null){
				final Navigate tmpNav = navToFocus;
				// this causes this to be executed on the event dispatch thread
				// after actionPerformed returns
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						setCurrentNavigate(tmpNav);
					}
				});
			}
		}// end focusOwner property
		else if (JSplitPane.DIVIDER_LOCATION_PROPERTY.equals(prop)) {
			if(split !=null){
				// splitpane only used with dualnav
				if ((split.getDividerLocation() <= (split.getMinimumDividerLocation() + FUDGE_FACTOR))) {//is minimized
					setCurrentNavigate(nav[1]);
				} else if ((split.getDividerLocation() >= (split.getMaximumDividerLocation() - FUDGE_FACTOR))) {//is maximized
					setCurrentNavigate(nav[0]);
				}
			}
			if(e.getSource() instanceof JSplitPane){
				JSplitPane jsp = (JSplitPane)e.getSource();
				if(!NavLayoutPref.isDualNavLayout() && jsp!=split){
					// then this is the navigation split between actions and data, try to remember location
					// div slowly creeps, it seems to be off by the original divider size
					Integer dividerSize = (Integer)UIManager.get("SplitPane.dividerSize");
					BehaviorPref.setNavDividerLocation(jsp.getDividerLocation()+dividerSize);
				}
			}
		}

		super.propertyChange(e);
	}


	/**
	 * EOD has passed, update profiles valon
	 * @param newdate
	 */
	protected void updateValOn(String newdate) {
		// let navigate update its own profile, it is a listener too
	}
    /**
	 * getOPWGID
	 *
	 * @return
	 */
	private int getOPWGID() {
		Profile myProf = getProfile();
		if (myProf != null) {
			return myProf.getOPWGID();
		}
		return -1;
	}

	/**
	 * shouldRefresh
	 *
	 * @return
	 */
	public boolean shouldRefresh() {
		if (curNav != null){
			if(curNav.shouldRefresh()) {
				if (BehaviorPref.alwaysRefresh()) {
					return true;
				} else if (BehaviorPref.neverRefresh()) {
				} else {
					//msg11012 = The information currently displayed has changed, \nwould you like to refresh the display?
					int reply =  com.ibm.eacm.ui.UI.showConfirmYesNo(this, Utils.getResource("msg11012"));
					if (reply == YES_BUTTON) {
						return true;
					}
				}
			}

			curNav.setShouldRefresh(false);
		}
		return false;
	}

	/**
	 * getEntityType
	 *
	 * @param i
	 * @return
	 */
	public String getEntityType(int i) {
		if (curNav != null) {
			return curNav.getEntityType(i);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#requestFocusInWindow()
	 */
	public boolean requestFocusInWindow() {
		boolean rfw = false;
		if (curNav == null) {
			if (nav[0] != null) {
				rfw = nav[0].requestFocusInWindow();
			} else if (nav[1] != null) {
				rfw = nav[1].requestFocusInWindow();
			}
		} else {
			rfw = curNav.requestFocusInWindow();
		}
		if(rfw){
			return rfw;
		}
		return super.requestFocusInWindow();
	}

	/**
	 * refresh
	 *
	 */
	public void refresh() {
		curNav.refresh();
	}

	/**
	 * dereference
	 *
	 */
	public void dereference() {
		super.dereference();

		if (nav!=null){
			for (int i = 0; i < nav.length; ++i) {
				if (nav[i] != null) {
					nav[i].dereference();
					nav[i] = null;
				}
			}
			nav = null;
		}

		if (NavLayoutPref.isDualNavLayout()){
			KeyboardFocusManager focusManager =
				KeyboardFocusManager.getCurrentKeyboardFocusManager();
			focusManager.removePropertyChangeListener("focusOwner",this);
		}

		pnlMain.removeAll();
		pnlMain.setUI(null);
		pnlMain = null;

		pnlNorth.removeAll();
		pnlNorth.setUI(null);
		pnlNorth = null;

		cart = null;

		strCG = null;

		if (split != null) {
			split.removePropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, this);
			split.removeAll();
			split.setUI(null);
			split = null;
		}

		if (cartFrame != null) {
			cartFrame.dereference();
			cartFrame = null;
		}

		sesLbl.removeAll();
		sesLbl.setUI(null);
		sesLbl = null;
		cgLbl.removeAll();
		cgLbl.setUI(null);
		cgLbl = null;

		curNav = null;

		jsp.removeAll();
		jsp.setUI(null);
		jsp = null;
	}

	/* (non-Javadoc)
	 * this is needed to create the toolbar, get actions from nav[0], they will be the same as nav[1]
	 * @see com.ibm.eacm.tabs.TabPanel#getActionTbl()
	 */
	public Hashtable<String, EACMAction> getActionTbl() {
		Hashtable<String, EACMAction> tmp = new Hashtable<String, EACMAction>(super.getActionTbl());
		tmp.putAll(nav[0].getActionTbl());
		return tmp;
	}

	/**
	 * close tabs with workers need to cancel them
	 * @return
	 */
	public boolean canClose() {
		boolean ok = true;
		if(nav[0]!=null){
			ok = nav[0].canClose();
		}
		if(ok){
			if(nav[1]!=null){
				ok = nav[1].canClose();
			}
		}
		if(ok){
			ok = super.canClose();
		}
		return ok;
	}


	/* (non-Javadoc)
	 * @see com.ibm.eacm.tabs.TabPanel#popupCart()
	 */
	public void popupCart() {
		if (cartFrame==null){
			cartFrame = NavCartFrame.getNavCart(cart);
		}

		cart.updateActions();
		cartFrame.setVisible(true);
	}
	/**
	 * user changed background color, update all components
	 */
	public void updateComponentsUI(){
		super.updateComponentsUI();
    	for(int i=0;i<nav.length; i++){
			if(nav[i]!=null){
				nav[i].updateComponentsUI();
			}
		}
	}
	/**
	 * select
	 *
	 */
	public void select() {
		cart.setCartable(this); // tell cart to use this for data selection
		cart.updateActions(); 

		EACM.getEACM().setActiveProfile(getProfile());

		EACM.getEACM().updateMenuBar(curNav.getMenubar());

		EACMAction act = EACM.getEACM().getGlobalAction(RESETDATE_ACTION);
		if(act instanceof ResetDateAction) {
			((ResetDateAction)act).setCurrentTab(this);
		}

		if (shouldRefresh()) {
			if (nav[0] != null && nav[0].shouldRefresh()) {
				nav[0].refresh();
			}
			if (nav[1] != null && nav[1].shouldRefresh()) {
				nav[1].refresh();
			}
			return;
		}

		requestFocusInWindow();
		EACM.getEACM().setFilterStatus(curNav.isFiltered());
		EACM.getEACM().setHiddenStatus(false);
		EACM.getEACM().setPastStatus();
	}

	/**
	 * does this tab contain this data
	 * @param prof
	 * @param ei
	 * @param eai
	 * @return
	 */
	public boolean tabExistsWithAll(Profile prof, EntityItem[] ei, EANActionItem eai){
		if(Utils.isPast(getProfile())){
			return false;
		}
		if(prof.getEnterprise().equals(getProfile().getEnterprise()) &&
				prof.getOPWGID()== getProfile().getOPWGID()){
			return curNav.getCurrentEntityList().equivalent(ei, eai);
		}
		return false;
	}
	/**
	 * does this tab contain some of this data
	 * @param prof
	 * @param ei
	 * @param eai
	 * @return
	 */
	public boolean tabExistsWithSome(Profile prof, EntityItem[] ei, EANActionItem eai){
		if(Utils.isPast(getProfile())){
			return false;
		}
		if(prof.getEnterprise().equals(getProfile().getEnterprise()) &&
				prof.getOPWGID()== getProfile().getOPWGID()){
			return curNav.getCurrentEntityList().subset(ei, eai);
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.objects.FindableComp#getFindable()
	 */
	public Findable getFindable() {
		return curNav.getFindable();
	}
	/**
	 * getHelpText
	 *
	 * @return
	 */
	public String getHelpText() {
		return curNav.getHelpText();
	}

	public ComboItem getDefaultToolbarLayout() {
		if (NavLayoutPref.isDualNavLayout()) {
			return DefaultToolbarLayout.NAV_BAR_DUAL;
		} else {
			return DefaultToolbarLayout.NAV_BAR;
		}
	}

	/**
	 * isMultipleNavigate
	 * @return
	 */
	public boolean isMultipleNavigate() {
		return ((nav[0] != null) && (nav[1] != null));
	}

	/**
	 * getOpposingNavigate
	 * @param nav2
	 * @return
	 */
	public Navigate getOpposingNavigate(Navigate nav2) {
		for (int i = 0; i < nav.length; ++i) {
			if (!nav[i].equals(nav2)) {
				return nav[i];
			}
		}
		return null;
	}

	/**
	 * getSelectedBorder
	 * @param nav
	 * @return
	 */
	public Border getSelectedBorder(Navigate nav) {
		if (isMultipleNavigate() && nav.equals(curNav)) {
			// if dual nav
			return UIManager.getBorder("eannounce.tab.selectedBorder");
		} else {
			return UIManager.getBorder("eannounce.tab.nonSelectedBorder");
		}
	}

	/**
	 * getSplit - used when building dualnav
	 * @return
	 */
	public JSplitPane getSplit() {
		if (split == null) {
			split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		}
		return split;
	}

	/**
	 * set the time for this profile
	 * called when the role is first loaded and when the resetdate action is used
	 * @param time
	 */
	public void setProcessTime(String time) {
		for (int i = 0; i < nav.length; ++i) {
			if (nav[i] !=null){
				nav[i].getProfile().setValOnEffOn(time, time);
				nav[i].refresh();
			}
		}

		EACM.getEACM().setPastStatus();
	}

	/**
	 * setShouldRefresh
	 *
	 * @param code
	 * @param eType
	 * @param opwg
	 */
	public void setShouldRefresh(String eType, int opwg, int code) {
		if (eType != null) {
			if (opwg == getOPWGID()) {
				if (nav[0] != null && nav[0].isType(eType, code)) {
					nav[0].setShouldRefresh(true);
				}
				if (nav[1] != null && nav[1].isType(eType, code)) {
					nav[1].setShouldRefresh(true);
				}
			}
		}
	}

	/**
	 * getTabToolTipText
	 *
	 * @return
	 */
	public String getTabToolTipText() {
		return curNav.getTabToolTipText();
	}

	/**
	 * getTabTitle
	 *
	 * @return
	 */
	public String getTabTitle() {
		return curNav.getTabTitle();
	}

	/**
	 * getTabMenuTitle
	 *
	 * @return
	 */
	public String getTabMenuTitle() {
		return curNav.getTabMenuTitle();
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.tabs.TabPanel#getTabIconKey()
	 */
	protected String getTabIconKey(){
		return curNav.getTabIconKey();
	}

	/**
	 * isBookmark
	 * @return
	 */
	public boolean isBookmark() {
		return bMark;
	}

	/**
	 * called when all history is removed
	 */
	public void resetBookmark(){
		bMark=false;
	}
	/**
	 * setBookmark
	 * @param b
	 */
	private void setBookmark(boolean b) {
		bMark = b;
	}

	/**
	 * setChangeGroupTagText
	 * @param s
	 */
	public void setChangeGroupTagText(String s) {
		String msg = null;
		if (s != null) {
			strCG = s;
			//cgLbl = You are currently in Change Group: {0}.
			msg= Utils.getResource("cgLbl",s);

			cgLbl.setText(msg);
			cgLbl.setToolTipText(msg);
		}
		cgLbl.setVisible(s != null);
	}
	/**
	 * getChangeGroupTagText
	 *
	 * @return String
	 */
	public String getChangeGroupTagText() {
		return strCG;
	}

	/**
	 * getParentInformationAtLevel
	 *
	 * @param i
	 * @return
	 */
	public Object[] getParentInformationAtLevel(int i) {
		return curNav.getParentInformationAtLevel(i);
	}

	/**
	 * @param toolbar
	 */
	public void setToolbar(EACMToolbar toolbar){
		pnlMain.add(toolbar.getAlignment(), toolbar);
	}


	// Cartable interface
	public EntityGroup getSelectedEntityGroup(){
		return getNavigate().getData().getEntityGroup();
	}
	public EntityItem[] getAllEntityItems() {
		try {
			return getNavigate().getData().getAllEntityItems(false,true);
		} catch (OutOfRangeException e) {
			com.ibm.eacm.ui.UI.showFYI(null,e);
		}
		return null;
	}
	public boolean supportsGetAll() { return true;} // enable add all action without having to get all

	/* (non-Javadoc)
	 * @see com.ibm.eacm.cart.Cartable#hasCartableData()
	 */
	public boolean hasCartableData() {
		boolean hasdata = false;
		if(getNavigate() ==null || getNavigate().getData()==null){
			return false;
		}
		EntityGroupTable tbl = getNavigate().getData().getTable();
		if(tbl!=null){
			hasdata = tbl.getRowCount()>0;
		}
		return hasdata && !getNavigate().isPast();
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.cart.Cartable#getSelectedEntityItems()
	 */
	public EntityItem[] getSelectedEntityItems(boolean showException) {
		EntityItem[] eai = null;
		try {
			return getNavigate().getData().getSelectedEntityItems(false,true);
		} catch (OutOfRangeException e) {
			if(showException){
				com.ibm.eacm.ui.UI.showFYI(null,e);
			}
		}
		return eai;
	}

	public void resetChangeGroup() {
		Profile[] pArray = EACM.getEACM().getActiveProfiles();
		Profile pActive = getProfile();
		CGActionItem.clearChangeGroup(pArray, pActive);
		curNav.getAction(CGRP_ACTION).setEnabled(false);
		setChangeGroupTagText(null);
	}
	public void clearChgGrpAction(EANActionItem ean, EntityItem[] ei) {
		if (ean.isClearTargetChangeGroupEnabled()) {
			if (CGActionItem.clearTargetChangeGroupForAll(EACM.getEACM().getActiveProfiles(),ei)) {
				resetChangeGroup();
				//msg3018.0 = Change Group {0} has been deactivated.
				com.ibm.eacm.ui.UI.showFYI(this, Utils.getResource("msg3018.0", getChangeGroupTagText()));
			}
		}
	}
}
