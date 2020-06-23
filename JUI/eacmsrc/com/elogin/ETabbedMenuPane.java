/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: ETabbedMenuPane.java,v $
 * Revision 1.4  2012/04/05 17:33:37  wendy
 * jre142 and win7 changes
 *
 * Revision 1.3  2009/05/28 13:57:50  wendy
 * Performance cleanup
 *
 * Revision 1.2  2008/01/30 16:26:59  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:42:17  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:09  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:39  tony
 * This is the initial load of OPICM
 *
 * Revision 1.12  2005/09/08 17:58:55  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.11  2005/02/09 19:29:50  tony
 * JTest After Scout
 *
 * Revision 1.10  2005/02/09 18:55:23  tony
 * Scout Accessibility
 *
 * Revision 1.9  2005/02/04 16:57:41  tony
 * JTest Formatter Third Pass
 *
 * Revision 1.8  2005/02/03 16:38:51  tony
 * JTest Second Pass Wrapup
 *
 * Revision 1.7  2005/02/02 21:30:08  tony
 * JTest Second Pass
 *
 * Revision 1.6  2005/01/27 23:18:17  tony
 * JTest Formatting
 *
 * Revision 1.5  2004/11/19 18:01:50  tony
 * imporoved accessibilitiy and replayable log file functionatlity.
 *
 * Revision 1.4  2004/11/16 17:28:35  tony
 * USRO-R-CRES-66ERD3
 *
 * Revision 1.3  2004/03/22 21:52:21  tony
 * fixed null pointer
 *
 * Revision 1.2  2004/02/24 18:01:31  tony
 * e-announce13 send tab
 *
 * Revision 1.1.1.1  2004/02/10 16:59:25  tony
 * This is the initial load of OPICM
 *
 * Revision 1.33  2004/01/16 23:25:22  tony
 * 53562
 *
 * Revision 1.32  2004/01/16 18:40:10  tony
 * 53516
 * updated tab logic to alter tab color when necessary.
 *
 * Revision 1.31  2004/01/08 17:40:55  tony
 * 53516
 *
 * Revision 1.30  2003/12/19 18:42:18  tony
 * acl_20031219
 * updated logic to prevent error and null pointers when
 * painting or validating components.
 *
 * Revision 1.29  2003/11/14 17:31:33  tony
 * accessible
 *
 * Revision 1.28  2003/10/29 19:10:41  tony
 * acl_20031029
 *
 * Revision 1.27  2003/10/29 00:22:23  tony
 * removed System.out. statements.
 *
 * Revision 1.26  2003/10/27 22:18:20  tony
 * updated logic to make ACCESSIBLE_ENABLED a boolean
 * that was not computed each time but instead a constant...
 * That will allow for the app to run a bit faster.
 * Parameterized accessibility on the table with the new
 * Parm.
 *
 * Revision 1.25  2003/10/17 22:46:59  tony
 * parameterized setLookAndFeel to prevent memory leak for testing.
 *
 * Revision 1.24  2003/10/17 18:01:52  tony
 * 52614
 *
 * Revision 1.23  2003/10/07 21:36:21  tony
 * added setlookandfeel to assist in accessibility
 * high contrast functionality.
 *
 * Revision 1.22  2003/09/30 16:53:55  tony
 * kehrli_20030929
 *
 * Revision 1.21  2003/09/30 16:33:57  tony
 * kehrli_20030929 --
 * logic enhancement to improve modification
 * ability of tab icon, title, tip, and menu items for
 * future modifications.
 *
 * Revision 1.20  2003/09/17 16:13:30  tony
 * loop prevention
 *
 * Revision 1.19  2003/09/12 16:15:25  tony
 * 52189
 *
 * Revision 1.18  2003/09/05 20:20:18  tony
 * 51975
 *
 * Revision 1.17  2003/08/28 18:36:01  tony
 * 51975
 *
 * Revision 1.16  2003/05/15 23:13:26  tony
 * updated functionality improved logic for next and
 * previous tab.
 *
 * Revision 1.15  2003/04/18 20:10:30  tony
 * added tab placement to preferences.
 *
 * Revision 1.14  2003/04/11 20:47:17  tony
 * improved Windows logic.
 *
 * Revision 1.13  2003/04/03 16:19:06  tony
 * changed refreshLookAndFeel to refreshAppearance()
 * updated logic to take into account displayed dialogs
 * as well.
 *
 * Revision 1.12  2003/04/02 19:53:37  tony
 * adjusted logic.  Everytime a new tab is launched the
 * system must grab a new instance of the profile.
 * This will aid in session tagging.
 *
 * Revision 1.11  2003/03/27 16:23:33  tony
 * added session id as an identification component
 * of the profile, needed because of pinning aspect.
 *
 * Revision 1.10  2003/03/25 23:29:05  tony
 * added eDisplayable to provide an
 * easier interface into components.  This will
 * assist in ease of programming in the future.
 *
 * Revision 1.9  2003/03/25 21:44:48  tony
 * adjusted logic to integrate in the xmlEditor.
 *
 * Revision 1.8  2003/03/24 21:52:23  tony
 * added modalCursor logic.
 *
 * Revision 1.7  2003/03/21 20:54:31  tony
 * updated getBackground, getForeground, and getFont logic.
 * added logic so that each can be set for the component.
 *
 * Revision 1.6  2003/03/18 22:39:10  tony
 * more accessibility updates.
 *
 * Revision 1.5  2003/03/12 23:51:09  tony
 * accessibility and column order
 *
 * Revision 1.4  2003/03/11 00:33:23  tony
 * accessibility changes
 *
 * Revision 1.3  2003/03/07 21:40:46  tony
 * Accessibility update
 *
 * Revision 1.2  2003/03/04 22:34:49  tony
 * *** empty log message ***
 *
 * Revision 1.1.1.1  2003/03/03 18:03:40  tony
 * This is the initial load of OPICM
 *
 * Revision 1.18  2002/11/07 16:58:17  tony
 * added/adjusted copyright statement
 *
 */
package com.elogin;
import COM.ibm.opicmpdh.middleware.Profile;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.plaf.TabbedPaneUI;
import javax.accessibility.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class ETabbedMenuPane extends JTabbedPane implements Accessible, ActionListener, EAccessConstants, EDisplayable {
	private static final long serialVersionUID = 1L;
	private EMenu menu = null;
	private Vector v = null;
	private ButtonGroup group = null;
	private int index = -1;
	/**
     * bModalCursor
     */
    private boolean bModalCursor = false;
	/**
     * bUseBack
     */
    private boolean bUseBack = true;
	/**
     * bUseFont
     */
    private boolean bUseFont = true;
	/**
     * bUseFore
     */
    private boolean bUseFore = true;

	/**
     * eTabbedMenuPane
     * @author Anthony C. Liberto
     */
    public ETabbedMenuPane() {
		super();
		init();
		setLookAndFeel();
		initAccessibility("accessible.tabPane");
		return;
	}

	/**
     * eaccess
     * @return
     * @author Anthony C. Liberto
     */
    public static EAccess eaccess() {
		return EAccess.eaccess();
	}

	private void init() {
		menu = new EMenu("wins");
		if (EAccess.isAccessible()) {
			menu.getAccessibleContext().setAccessibleDescription(menu.getText());
		} else {
			menu.setMnemonic(getChar("wins-s"));
		}
		v = new Vector();
		group = new ButtonGroup();
		setOpaque(false);
		updateTabPlacement(false);
		return;
	}

	/**
     * updateTabPlacement
     * @param _revalidate
     * @author Anthony C. Liberto
     */
    public void updateTabPlacement(boolean _revalidate) {
		int ii = -1;
        setTabPlacement(eaccess().getPrefInt(PREF_TAB_LAYOUT, SwingConstants.TOP));
		ii = getTabCount();
		for (int i=0;i<ii;++i) {
			ETabable tab = getTabable(i);
			if (tab != null) {
				tab.updateTabPlacement(_revalidate);
			}
		}
		if (_revalidate) {
			revalidate();
		}
		return;
	}

	/**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
		initAccessibility(null);
		removeAll();
		menu = null;
		v = null;
		return;
	}

	/**
     * getDisplayable
     * @author Anthony C. Liberto
     * @return EDisplayable
     */
    public EDisplayable getDisplayable() {
		Container par = getParent();
		if (par instanceof EDisplayable) {
			return (EDisplayable)par;
		}
		return null;
	}

	/**
     * @see java.awt.Component#getCursor()
     * @author Anthony C. Liberto
     */
    public Cursor getCursor() {
		if (isModalCursor()) {
			return eaccess().getModalCursor();
		} else {
			EDisplayable disp = getDisplayable();
			if (disp != null) {
				return disp.getCursor();
			}
		}
		return eaccess().getCursor();
	}

	/**
     * setModalCursor
     * @author Anthony C. Liberto
     * @param _b 
     */
    public void setModalCursor(boolean _b) {
		bModalCursor = _b;
		return;
	}

	/**
     * isModalCursor
     * @author Anthony C. Liberto
     * @return boolean
     */
    public boolean isModalCursor() {
		return bModalCursor;
	}

	/**
     * setUseDefined
     * @author Anthony C. Liberto
     * @param _b 
     */
    public void setUseDefined(boolean _b) {
		setUseBack(_b);
		setUseFore(_b);
		setUseFont(_b);
		return;
	}

	/**
     * setUseBack
     * @author Anthony C. Liberto
     * @param _b 
     */
    public void setUseBack(boolean _b) {
		bUseBack = _b;
		return;
	}

	/**
     * setUseFore
     * @author Anthony C. Liberto
     * @param _b 
     */
    public void setUseFore(boolean _b) {
		bUseFore = _b;
		return;
	}

	/**
     * setUseFont
     * @author Anthony C. Liberto
     * @param _b 
     */
    public void setUseFont(boolean _b) {
		bUseFont = _b;
		return;
	}

	/**
     * @see java.awt.Component#getBackground()
     * @author Anthony C. Liberto
     */
    public Color getBackground() {
		if (eaccess().canOverrideColor() && bUseBack) {
			if (isEnabled()) {
				return eaccess().getBackground();
			} else {
				return eaccess().getDisabledBackground();
			}
		}
		return super.getBackground();
	}

	/**
     * @see java.awt.Component#getForeground()
     * @author Anthony C. Liberto
     */
    public Color getForeground() {
		if (eaccess().canOverrideColor() && bUseFore) {
			if (isEnabled()) {
				return eaccess().getForeground();
			} else {
				return eaccess().getDisabledForeground();
			}
		}
		return super.getForeground();
	}

	/**
     * @see java.awt.MenuContainer#getFont()
     * @author Anthony C. Liberto
     */
    public Font getFont() {
		if (EANNOUNCE_UPDATE_FONT && bUseFont) {
			return eaccess().getFont();
		}
		return super.getFont();
	}

/*
 51975
	public void addTab(String _menuTitle, String _tabTitle, Icon _icon, eTabable _c, String _tip) {
		int i = getTabCount();
		super.addTab(_tabTitle, _icon, (Component)_c, _tip);
		addMenu(_menuTitle);
		if (i >= 0) {
			setSelectedIndex(i);
		}
		return;
	}
*/
	/**
     * @see javax.swing.JTabbedPane#removeTabAt(int)
     * @author Anthony C. Liberto
     */
    public void removeTabAt(int _i) {
		if (index == _i) {
			index = -1;
		}
		super.removeTabAt(_i); // this calls removeNotify() on the Tab item
		removeMenu(_i);
	}

	/**
     * @see java.awt.Container#removeAll()
     * @author Anthony C. Liberto
     */
    public void removeAll() {
		index = -1;
		super.removeAll();
		menu.removeAll();
		v.clear();
		return;
	}

	/**
     * @see javax.swing.JTabbedPane#getComponentAt(int)
     * @author Anthony C. Liberto
     */
    public Component getComponentAt(int _i) {
		if (_i < 0 || _i >= getTabCount()) {
			return null;
		}
		return super.getComponentAt(_i);
	}

	/**
     * getSelectedTab
     * @return
     * @author Anthony C. Liberto
     */
    public ETabable getSelectedTab() {
		return getTabable(getSelectedIndex());
	}

	/**
     * getTabable
     * @param _i
     * @return
     * @author Anthony C. Liberto
     */
    public ETabable getTabable(int _i) {
		if (_i < 0 || _i >= getTabCount()) {
			return null;
		}
		return (ETabable)super.getComponentAt(_i);
	}

	/**
     * addMenu
     * @param _s
     * @author Anthony C. Liberto
     */
    public void addMenu(String _s) {
        ERadioButtonMenuItem item = null;
        getTabCount();
		item = new ERadioButtonMenuItem(_s, true);			//20020710
		item.addActionListener(this);
		group.add(item);
		menu.add(item);
		v.add(item);
		return;
	}

	/**
     * removeMenu
     * @param _i
     * @author Anthony C. Liberto
     */
    public void removeMenu(int _i) {
		Object o = v.remove(_i);
		if (o instanceof JMenuItem) {
			JMenuItem item = (JMenuItem)o;
			item.removeActionListener(this);
			menu.remove(item);
			v.remove(item);
			if (item instanceof ERadioButtonMenuItem) {
				group.remove(item);
				((ERadioButtonMenuItem)item).dereference();
			} else {
				item.removeAll();
				item.removeNotify();
			}
			item = null;
		}
		return;
	}

	/**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @author Anthony C. Liberto
     */
    public void actionPerformed(ActionEvent _ae) {
		int i = v.indexOf(_ae.getSource());
		setSelectedIndex(i);
		return;
	}

	/**
     * getMenu
     * @return
     * @author Anthony C. Liberto
     */
    public EMenu getMenu() {
		return menu;
	}

	/**
     * @see javax.swing.JTabbedPane#setSelectedIndex(int)
     * @author Anthony C. Liberto
     */
    public void setSelectedIndex(int i) {
		int max = getTabCount() - 1;
		ETabable eTab = null;
        if (i > max) {
			i = max;
		} else if (i < 0) {
			i = 0;
		}

		if (index == i) {
			return;
		}
		index = i;
		eTab = getSelectedTab();		//52189
		if (eTab != null) {						//52189
			eTab.deselect();					//52189
		}										//52189
		super.setSelectedIndex(i);
		if (i >= 0) {
			setSelectedMenuItem(i);
//52614			getTabable(i).select();
			getTabable(i).select(false);		//52614
			eaccess().adjustPrevNext(i,max);
		}
	}


	/**
     * isWindows
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean isWindows() {
		return eaccess().isWindows();
	}

	/**
     * isEmpty
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isEmpty() {
		return getTabCount() <= 0;
	}

	/**
     * setSelectedMenuItem
     * @param _i
     * @author Anthony C. Liberto
     */
    public void setSelectedMenuItem(int _i) {
		JMenuItem jmi = null;
        if (_i >= menu.getItemCount()) {
			return;
		}
		jmi = menu.getItem(_i);
		if (jmi != null) {
			jmi.setSelected(true);
		}
		return;
	}

/*
 51975
	public void selectParent(eTabable _tab) {
		int ii = getTabCount();
		Profile parentProfile = _tab.getParentProfile();
		if (parentProfile != null) {
			eTabable tab = null;
			for (int i=0;i<ii;++i) {
				tab = getTabable(i);
				if (tab.isPanelType(TYPE_eNavForm)) {
					if (parentProfile.equals(tab.getProfile())) {
						setSelectedIndex(i);
						return;
					}
				}
			}
		}
		if (ii > 0)
			setSelectedIndex(0);
		return;
	}
*/
	/**
     * getNavigateIndex
     * @param _prof
     * @return
     * @author Anthony C. Liberto
     */
    public int getNavigateIndex(Profile _prof) {
		if (!isEmpty()) {
			int ii = getTabCount();
			ETabable eTab = null;
			for (int i=0;i<ii;++i) {
				eTab = getTabable(i);
				if (eTab.getProfile() == _prof) {
					if (eTab.isPanelType(TYPE_ENAVFORM)) {
						return i;
					}
				}
			}
		}
		return -1;
	}

	/**
     * getETabable
     * @param _prof
     * @param _panelType
     * @return
     * @author Anthony C. Liberto
     */
    public ETabable getETabable(Profile _prof, String _panelType) {
		if (!isEmpty()) {
			int ii = getTabCount();
			ETabable eTab = null;
			for (int i=0;i<ii;++i) {
				eTab = getTabable(i);
				if (eTab.getProfile().getOPWGID() == _prof.getOPWGID()) {
					if (eTab.isPanelType(_panelType)) {
						return eTab;
					}
				}
			}
		}
		return null;
	}

	/**
     * setIconAt
     * @param _tab
     * @param _icon
     * @author Anthony C. Liberto
     */
    public void setIconAt(ETabable _tab, Icon _icon) {
		if (_tab != null) {
			setIconAt(indexOfComponent((Component)_tab),_icon);
		}
		return;
	}

	/**
     * getString
     * @param _code
     * @return
     * @author Anthony C. Liberto
     */
    public String getString(String _code) {
		return eaccess().getString(_code);
	}

	/**
     * getChar
     * @param _code
     * @return
     * @author Anthony C. Liberto
     */
    public char getChar(String _code) {
		return eaccess().getChar(_code);
	}

	/**
     * refreshAppearance
     * @author Anthony C. Liberto
     */
    public void refreshAppearance() {
		for (int i=0;i<getTabCount();++i) {
			ETabable tab = getTabable(i);
			if (tab != null) {
				if (tab instanceof JComponent) {
					((JComponent)tab).revalidate();
				}
				tab.refreshAppearance();
			}
		}
		revalidate();
		repaint();
		return;
	}
/*
 51975
 */
	/**
     * selectParent
     * @param _tab
     * @author Anthony C. Liberto
     */
    public void selectParent(ETabable _tab) {
		ETabable parTab = _tab.getParentTab();
		int ii = -1;
        Profile parentProfile = null;
        if (parTab != null) {
			int tmpIndex = indexOfComponent((Component)parTab);
			if (tmpIndex >= 0) {
				setSelectedIndex(tmpIndex);
				return;
			}
		}
		ii = getTabCount();
		parentProfile = _tab.getParentProfile();
		if (parentProfile != null) {
			ETabable tab = null;
			for (int i=0;i<ii;++i) {
				tab = getTabable(i);
				if (tab.isPanelType(TYPE_ENAVFORM)) {
					if (parentProfile.equals(tab.getProfile())) {
						setSelectedIndex(i);
						return;
					}
				}
			}
		}
		if (ii > 0) {
			setSelectedIndex(0);
		}
	}

/*
 kehrli_20030929
 */
	/**
     * addTab
     * @param _parentTab
     * @param _tab
     * @author Anthony C. Liberto
     */
    public void addTab(ETabable _parentTab, ETabable _tab) {
		int i = getTabCount();
		super.addTab(_tab.getTabTitle(),_tab.getTabIcon(),(Component)_tab,_tab.getTabToolTipText());
		if (_parentTab != _tab) {
			_tab.setParentTab(_parentTab);
		}
		addMenu(_tab.getTabMenuTitle());
		if (i >= 0) {
			setSelectedIndex(i);
		}

		return;
	}

	/**
     * addTab
     * @param _parentTab
     * @param _tab
     * @param _icon
     * @author Anthony C. Liberto
     */
    public void addTab(ETabable _parentTab, ETabable _tab, Icon _icon) {
		int i = getTabCount();
		super.addTab(_tab.getTabTitle(),_icon,(Component)_tab,_tab.getTabToolTipText());
		if (_parentTab != _tab) {
			_tab.setParentTab(_parentTab);
		}
		addMenu(_tab.getTabMenuTitle());
		if (i >= 0) {
			setSelectedIndex(i);
		}
		return;
	}

	/**
     * addTab
     * @param _parentTab
     * @param _tab
     * @param _icon
     * @param _title
     * @author Anthony C. Liberto
     */
    public void addTab(ETabable _parentTab, ETabable _tab, Icon _icon, String _title) {
		int i = getTabCount();
		super.addTab(_title,_icon,(Component)_tab,_tab.getTabToolTipText());
		if (_parentTab != _tab) {
			_tab.setParentTab(_parentTab);
		}
		addMenu(_tab.getTabMenuTitle());
		if (i >= 0) {
			setSelectedIndex(i);
		}
		return;
	}

    /**
     * @see javax.swing.JComponent#getToolTipText(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public String getToolTipText(MouseEvent _me) {
        if (ui != null && _me != null) {
            int indx = ((TabbedPaneUI)ui).tabForCoordinate(this, _me.getX(), _me.getY());
            if (indx != -1) {
                ETabable tab = getTabable(indx);
                if (tab != null && tab.isPanelType(TYPE_ENAVFORM)) {
					return tab.getTabToolTipText();
				}
            }
        }
        return super.getToolTipText(_me);
    }

/*
 acl_20031007
 */
	/**
     * setLookAndFeel
     * @author Anthony C. Liberto
     */
    public void setLookAndFeel() {
		if (EAccess.isAccessible()) {
			LookAndFeel lnf = UIManager.getLookAndFeel();
			try {
				UIManager.setLookAndFeel(WINDOWS_LNF);
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			SwingUtilities.updateComponentTreeUI(this);

			try {
				UIManager.setLookAndFeel(lnf);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return;
	}

/*
 acl_20031219
 */
	/**
     * @see java.awt.Component#validate()
     * @author Anthony C. Liberto
     */
    public void validate() {
		try {
			super.validate();
		} catch (Exception _x) {
			_x.printStackTrace();
		}
		return;
	}

    /**
     * @see javax.swing.JComponent#paintImmediately(int, int, int, int)
     * @author Anthony C. Liberto
     */
    public void paintImmediately(int _x, int _y, int _w, int _h) {
		try {
			paintImmediately2(_x,_y,_w,_h);
		} catch (Exception _e) {
			_e.printStackTrace();
		}
		return;
	}

	private void paintImmediately2(int _x, int _y, int _w, int _h) throws Exception {
		super.paintImmediately(_x,_y,_w,_h);
		return;
	}
/*
 53516
 ********
 * NOTE *
 *      ***********************************************************************
 * comment in when 53516 is approved                                          *
 * code in eLogin.java tagged with comment out for 53516 needs to be removed. *
 ******************************************************************************
 */
	/**
     * @see javax.swing.JTabbedPane#getBackgroundAt(int)
     * @author Anthony C. Liberto
     */
    public Color getBackgroundAt(int _index) {
		if (_index >= 0 && _index < getTabCount()) {
			Color out = super.getBackgroundAt(_index);
			if (out != null) {
				if (!isSelectedIndex(_index)) {
					out = out.darker();
				}
				return out;
			}
		}
		return DEFAULT_COLOR_ENABLED_BACKGROUND;
	}

	/**
     * isSelectedIndex
     * @param _i
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isSelectedIndex(int _i) {
		return getSelectedIndex() == _i;
	}

/*
 53562
 */
	/**
     * setTitleAt
     * @param _tab
     * @param _title
     * @author Anthony C. Liberto
     */
    public void setTitleAt(ETabable _tab, String _title) {
		if (_tab != null) {
			int i = indexOfComponent((Component)_tab);
			if (i >= 0 && i < getTabCount()) {
				setTitleAt(i,_title);
			}
		}
		return;
	}

/*
 accessibility
 */
	/**
     * initAccessibility
     *
     * @author Anthony C. Liberto
     * @param _s
     */
    public void initAccessibility(String _s) {
		if (EAccess.isAccessible()) {
			AccessibleContext ac = getAccessibleContext();
			String strAccessible = null;
			if (ac != null) {
				if (_s == null) {
					ac.setAccessibleName(null);
					ac.setAccessibleDescription(null);
				} else {
					strAccessible = getString(_s);
					ac.setAccessibleName(strAccessible);
					ac.setAccessibleDescription(strAccessible);
				}
			}
		}
		return;
	}
}
