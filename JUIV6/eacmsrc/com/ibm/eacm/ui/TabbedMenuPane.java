//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.ui;

import COM.ibm.opicmpdh.middleware.Profile;
import java.awt.*;
import java.awt.event.*;
import java.util.Enumeration;

import javax.swing.*;
import javax.swing.plaf.TabbedPaneUI;
import javax.accessibility.*;

import com.ibm.eacm.EACM;
import com.ibm.eacm.actions.*;

import com.ibm.eacm.objects.*;
import com.ibm.eacm.preference.*;
import com.ibm.eacm.tabs.CloseTabComponent;
import com.ibm.eacm.tabs.NavController;
import com.ibm.eacm.tabs.TabPanel;
/**
 * this is the main tabbed pane for EACM
 */
// $Log: TabbedMenuPane.java,v $
// Revision 1.4  2014/05/01 19:35:15  wendy
// Add tablayoutpolicy to support scroll of action tabbed pane
//
// Revision 1.3  2013/03/14 17:34:19  wendy
// dont use session when checking for existing navigate profile
//
// Revision 1.2  2012/10/26 21:08:27  wendy
// use profile.equals for getNavigateIndex check
//
// Revision 1.1  2012/09/27 19:39:11  wendy
// Initial code
//
public class TabbedMenuPane extends JTabbedPane implements EACMGlobals {
	private static final long serialVersionUID = 1L;
	private JMenu windowMenu = null;
	private ButtonGroup btnGroup = null;
	private int index = -1;

	/**
     *
     */
    public TabbedMenuPane() {
    	windowMenu = new JMenu(Utils.getResource(WINDOW_MENU));
    	windowMenu.setMnemonic(Utils.getMnemonic(WINDOW_MENU));
		
    	btnGroup = new ButtonGroup();
		setOpaque(false);
		setTabPlacement(BehaviorPref.getTabPlacement());
		setTabLayoutPolicy(BehaviorPref.getNavTabLayoutPolicy()); 

		initAccessibility("accessible.tabPane");
	}

    /**
     * updateTabLayoutPolicy
     */
    public void updateTabLayoutPolicy(){
		setTabLayoutPolicy(BehaviorPref.getNavTabLayoutPolicy()); 

		revalidate();
		// make the selected tab visible after switching policy
		if(this.getTabLayoutPolicy() == JTabbedPane.SCROLL_TAB_LAYOUT && getSelectedIndex()!=-1){
			SwingUtilities.invokeLater(new Runnable(){
				public void run() {
					CloseTabComponent ctb = (CloseTabComponent)getTabComponentAt(getSelectedIndex());
					ctb.scrollRectToVisible(ctb.getBounds());
				}
			});
		}
    }
	/**
     * updateTabPlacement
     */
    public void updateTabPlacement() {
        setTabPlacement(BehaviorPref.getTabPlacement());
	
		for (int i=0;i<getTabCount();++i) {
			if (super.getComponentAt(i) instanceof NavController){
				((NavController)super.getComponentAt(i)).updateTabPlacement();
			}
		}
	
		revalidate();
	}

	/**
     * dereference 
     */
    public void dereference() {
		initAccessibility(null);
		EACM.closeMenu(windowMenu);
		windowMenu = null;

		for (Enumeration<AbstractButton> btnEnum = btnGroup.getElements(); btnEnum.hasMoreElements();){
			btnGroup.remove(btnEnum.nextElement()); 
		}
		btnGroup = null;

		removeAll();
		setUI(null);
	}

	/**
     * @see javax.swing.JTabbedPane#removeTabAt(int)
     */
    public void removeTabAt(int i) {
		if (index == i) {
			index = -1;
		}
		super.removeTabAt(i); // this calls removeNotify() on the Tab item
			// remove the menuitem for this tab
	   	JMenuItem item = windowMenu.getItem(i);
	   	windowMenu.remove(item);
	   	btnGroup.remove(item);
	   	
    	EACM.closeMenuItem(item);
	}

	/**
     * getSelectedTab
     * @return
     */
    public TabPanel getSelectedTab() {
		return getTabPanel(getSelectedIndex());
	}

	/**
     * get the TabPanel at the specified index
     * @param i
     * @return
     */
    public TabPanel getTabPanel(int i) {
		if (i < 0 || i >= getTabCount()) {
			return null;
		}
		return (TabPanel)super.getComponentAt(i);
	}

	/**
     * addMenu
     * @param s
     */
    private void addMenu(String s) {
		WindowAction act = new WindowAction(this, s,getTabCount());
		JMenuItem jmi = new JRadioButtonMenuItem(act)
       	{
       	 	private static final long serialVersionUID = 1L;
       	 	// this is the only way to get selected to show up.. setting icon didnt do it
       	 	public Icon getIcon() {
       	 		if (isSelected()) {
       	 			return UIManager.getIcon("eannounce.selectedIcon");
       	 		}
       	 		return UIManager.getIcon("eannounce.unselectedIcon");
       	 	}
       	};
       	jmi.setSelected(true);
       	btnGroup.add(jmi); // this makes only one icon visible
       	windowMenu.add(jmi);
	}

	/**
     * getMenu
     * @return
     */
    public JMenu getTabMenu() {
		return windowMenu;
	}

	/**
	 * called by actions when using workers
     */
    public void disableActionsAndWait(){
    	setEnabled(false);
     	for(int i=0;i<getTabCount(); i++){
    		TabPanel tab = getTabPanel(i);
    		tab.disableActionsAndWait();
    	}
    }
    public void enableActionsAndRestore(){ 
    	setEnabled(true);
    	for(int i=0;i<getTabCount(); i++){
    		TabPanel tab = getTabPanel(i);
    		tab.enableActionsAndRestore();
    	}
    }
	/**
     * @see javax.swing.JTabbedPane#setSelectedIndex(int)
     * 
     */
    public void setSelectedIndex(int i) {
		int max = getTabCount() - 1;
        if (i > max) {
			i = max;
		} else if (i < 0) {
			i = 0;
		}

        if (index != i) {
        	index = i;
        	TabPanel eTab = getSelectedTab();		
        	if (eTab != null) {						
        		eTab.deselect();					
        	}										
        	super.setSelectedIndex(i);
        	if (i >= 0) {
        		// index is selected before menu is added when new tab is added
        		if (windowMenu.getItemCount()>i){
        			JMenuItem jmi = windowMenu.getItem(i);
        			jmi.setSelected(true);
        		}
        		
        		getTabPanel(i).select();	
      
        		if (EACM.getEACM() != null) {
        			EACM.getEACM().adjustPrevNext(i, max);
        		}
        	}
        }
	}

	/**
     * getNavigateIndex
     * @param prof
     * @return
     */
    public int getNavigateIndex(Profile prof) {
    	for (int i=0;i<getTabCount();++i) {
    		TabPanel eTab = getTabPanel(i);
    		if(eTab instanceof NavController){
        		Profile tabprof = eTab.getProfile();
        		String tabEnt = tabprof.getEnterprise();
        		int tabopwgid = tabprof.getOPWGID();
    			//if (eTab.getProfile().equals(prof)) { this uses session also
    			if(tabEnt.equals(prof.getEnterprise()) && tabopwgid == prof.getOPWGID()) {
    				return i;
    			}
    		}
    	}
    	return -1;
	}
/*
  public final boolean equals(Object _o) {
    return ((_o != null) && (_o instanceof Profile) && (m_strEnterprise.equals((((Profile) _o).m_strEnterprise))) && (m_iOPWGID == (((Profile) _o).m_iOPWGID)) && (m_iSessionID == (((Profile) _o).m_iSessionID)));
  }
 */

	/**
     * setIconAt
     * @param tab
     * @param icon
     */
    public void setIconAt(TabPanel tab, Icon icon) {
		if (tab != null) {
			int index = indexOfComponent((Component)tab);
			CloseTabComponent tabcomp = (CloseTabComponent)getTabComponentAt(index);
			tabcomp.setIcon(icon); // show new icon
		}
	}

	/**
     * selectParent
     * @param _tab
     */
    public void selectParent(TabPanel tab) {
    	TabPanel parTab = tab.getParentTab();
        Profile parentProfile = null;
        if (parTab != null) {
			int tmpIndex = indexOfComponent((Component)parTab);
			if (tmpIndex >= 0) {
				setSelectedIndex(tmpIndex);
				return;
			}
		}

		parentProfile = tab.getParentProfile();
		if (parentProfile != null) {
			TabPanel tab2 = null;
			for (int i=0;i<getTabCount();++i) {
				tab2 = getTabPanel(i);
				if (tab2 instanceof NavController) {
					if (parentProfile.equals(tab2.getProfile())) {
						setSelectedIndex(i);
						return;
					}
				}
			}
		}
		if (getTabCount() > 0) {
			setSelectedIndex(0);
		}
	}

	/**
     * addTab
     * @param parentTab
     * @param tab
     */
    public void addTab(TabPanel parentTab, TabPanel tab) {
		int i = getTabCount();
		super.addTab(tab.getTabTitle(),tab.getTabIcon(),(Component)tab,tab.getTabToolTipText());
		// title is displayed by the tabcomponent
		CloseTabComponent ctb = new CloseTabComponent(tab.getTabTitle(),
				tab.getTabIcon(),EACM.getEACM().getGlobalAction(CLOSETAB_ACTION));
		setTabComponentAt(i,ctb);

		if (parentTab != tab) {
			tab.setParentTab(parentTab);
		}
		addMenu(tab.getTabMenuTitle());
		if (i >= 0) {
			setSelectedIndex(i);
		}
		
		if(i>0 && this.getTabLayoutPolicy() == JTabbedPane.SCROLL_TAB_LAYOUT){
			SwingUtilities.invokeLater(new Runnable(){
				public void run() {
					CloseTabComponent ctb = (CloseTabComponent)getTabComponentAt(getSelectedIndex());
					ctb.scrollRectToVisible(ctb.getBounds());
				}
			});
		}
	}

	/**
     * addTab
     * @param parentTab
     * @param tab
     * @param icon
     * /
    public void addTab(TabPanel parentTab, TabPanel tab, Icon icon) {
		int i = getTabCount();
		super.addTab(tab.getTabTitle(),icon,(Component)tab,tab.getTabToolTipText());

		if (parentTab != tab) {
			tab.setParentTab(parentTab);
		}
		
		// title is displayed by the tabcomponent
		CloseTabComponent ctb = new CloseTabComponent(tab.getTabTitle(),
				icon,EACM.getEACM().getGlobalAction(CLOSETAB_ACTION));
		setTabComponentAt(i,ctb);
		
		addMenu(tab.getTabMenuTitle());
		if (i >= 0) {
			setSelectedIndex(i);
		}
	}

    /**
     * @see javax.swing.JComponent#getToolTipText(java.awt.event.MouseEvent)
     */
    public String getToolTipText(MouseEvent me) {
        if (ui != null && me != null) {
            int indx = ((TabbedPaneUI)ui).tabForCoordinate(this, me.getX(), me.getY());
            if (indx != -1) {
            	TabPanel tab = getTabPanel(indx);
                if (tab instanceof NavController) {
					return tab.getTabToolTipText();
				}
            }
        }
        return super.getToolTipText(me);
    }


	/**
     * @see javax.swing.JTabbedPane#getBackgroundAt(int)
     */
    public Color getBackgroundAt(int index) {
		if (index >= 0 && index < getTabCount()) {
			Color out = super.getBackgroundAt(index);
			if (out != null) {
				if (getSelectedIndex()!=index) {
					out = out.darker();
				}
				return out;
			}
		}
		return ColorPref.DEFAULT_COLOR_ENABLED_BACKGROUND;
	}

	/**
     * setTitleAt
     * @param tab
     * @param title
     */
    public void setTitleAt(TabPanel tab, String title) {
		if (tab != null) {
			int i = indexOfComponent((Component)tab);
			if (i >= 0 && i < getTabCount()) {
				CloseTabComponent tabcomp = (CloseTabComponent)getTabComponentAt(i);
				tabcomp.setTitle(title);
			}
		}
	}

	/**
     * initAccessibility
     *
     * @param _s
     */
    private void initAccessibility(String s) {
    	AccessibleContext ac = getAccessibleContext();
    	if (ac != null) {
    		if (s == null) {
    			ac.setAccessibleName(null);
    			ac.setAccessibleDescription(null);
    		} else {
    			String strAccessible = Utils.getResource(s);
    			ac.setAccessibleName(strAccessible);
    			ac.setAccessibleDescription(strAccessible);
    		}
    	}
	}
}
