/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: EannounceToolbar.java,v $
 * Revision 1.4  2012/04/05 17:38:26  wendy
 * jre142 and win7 changes
 *
 * Revision 1.3  2009/05/26 13:07:16  wendy
 * Performance cleanup
 *
 * Revision 1.2  2008/01/30 16:27:04  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:45:24  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:16  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:38:15  tony
 * This is the initial load of OPICM
 *
 * Revision 1.10  2005/09/08 17:59:08  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.9  2005/06/02 16:46:56  tony
 * PKUR-6CWGY6 commented out
 *
 * Revision 1.8  2005/03/31 17:42:08  tony
 * fixed out of bounds exception
 *
 * Revision 1.7  2005/03/28 17:56:38  tony
 * MN_button_disappear
 * adjust to preven java bug 4664818
 *
 * Revision 1.6  2005/02/03 21:26:14  tony
 * JTest Format Third Pass
 *
 * Revision 1.5  2005/01/31 20:47:49  tony
 * JTest Second Pass
 *
 * Revision 1.4  2005/01/26 22:17:58  tony
 * JTest Modifications
 *
 * Revision 1.3  2005/01/14 19:53:56  tony
 * adjusted button logic to troubleshoot
 * Button disappearing issue.
 *
 * Revision 1.2  2004/11/19 18:01:51  tony
 * imporoved accessibilitiy and replayable log file functionatlity.
 *
 * Revision 1.1.1.1  2004/02/10 16:59:48  tony
 * This is the initial load of OPICM
 *
 * Revision 1.17  2003/10/29 19:10:43  tony
 * acl_20031029
 *
 * Revision 1.16  2003/10/27 22:18:20  tony
 * updated logic to make ACCESSIBLE_ENABLED a boolean
 * that was not computed each time but instead a constant...
 * That will allow for the app to run a bit faster.
 * Parameterized accessibility on the table with the new
 * Parm.
 *
 * Revision 1.15  2003/10/17 22:46:59  tony
 * parameterized setLookAndFeel to prevent memory leak for testing.
 *
 * Revision 1.14  2003/10/07 21:35:16  tony
 * added setlookandfeel to assist in accessibility
 * high contrast functionality.
 *
 * Revision 1.13  2003/06/19 16:06:29  tony
 * toolbar functionality update 1.2h
 *
 * Revision 1.12  2003/06/05 17:07:38  tony
 * added refreshAppearance logic to the toolbar for the container.
 * This will flow thru to the scrollable popup.
 * Adjusted the sizing logic on the scrollable popup.
 *
 * Revision 1.11  2003/05/21 17:05:01  tony
 * 50827 -- separated out picklist from navigate
 *
 * Revision 1.10  2003/05/14 19:02:01  tony
 * cleabed-up code
 *
 * Revision 1.9  2003/03/29 00:06:58  tony
 * added new constructor
 *
 * Revision 1.8  2003/03/25 23:29:06  tony
 * added eDisplayable to provide an
 * easier interface into components.  This will
 * assist in ease of programming in the future.
 *
 * Revision 1.7  2003/03/24 21:52:24  tony
 * added modalCursor logic.
 *
 * Revision 1.6  2003/03/21 20:54:33  tony
 * updated getBackground, getForeground, and getFont logic.
 * added logic so that each can be set for the component.
 *
 * Revision 1.5  2003/03/18 22:39:11  tony
 * more accessibility updates.
 *
 * Revision 1.4  2003/03/12 23:51:17  tony
 * accessibility and column order
 *
 * Revision 1.3  2003/03/11 00:33:26  tony
 * accessibility changes
 *
 * Revision 1.2  2003/03/07 21:40:48  tony
 * Accessibility update
 *
 * Revision 1.1.1.1  2003/03/03 18:03:51  tony
 * This is the initial load of OPICM
 *
 * Revision 1.7  2002/11/11 22:55:41  tony
 * adjusted classification on the toggle
 *
 * Revision 1.6  2002/11/07 16:58:36  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.toolbar;
import com.elogin.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import COM.ibm.eannounce.objects.EANActionItem;
import com.ibm.eannounce.eobjects.*;
import javax.swing.plaf.basic.BasicToolBarUI;				//21539

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class EannounceToolbar extends JToolBar implements ActionListener, EAccessConstants, EDisplayable {
	private static final long serialVersionUID = 1L;
	private ActionListener al = null;
	private String sAlign = null;
	private EVector hash = new EVector();
	private EToolbar bar = null;
	private Object obj = null;
//	private boolean bSerial = false;
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
     * eannounceToolbar
     * @param _al
     * @author Anthony C. Liberto
     */
    public EannounceToolbar(ActionListener _al) {
		super();
		setLookAndFeel();
		setRemoteActionListener(_al);
	}

	/**
     * eannounceToolbar
     * @param _bar
     * @param _al
     * @param _obj
     * @author Anthony C. Liberto
     */
    protected EannounceToolbar(EToolbar _bar, ActionListener _al, Object _obj) {
		super(_bar.getStringKey());
		setLookAndFeel();
		setRemoteActionListener(_al);
		obj = _obj;
		rebuild(_bar);
	}

    /**
     * getDisplayable
     *
     * @return
     * @author Anthony C. Liberto
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
			return EAccess.eaccess().getModalCursor();
		} else {
			EDisplayable disp = getDisplayable();
			if (disp != null) {
				return disp.getCursor();
			}
		}
		return EAccess.eaccess().getCursor();
	}

    /**
     * setModalCursor
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setModalCursor(boolean _b) {
		bModalCursor = _b;
	}

    /**
     * isModalCursor
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isModalCursor() {
		return bModalCursor;
	}

    /**
     * setUseDefined
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setUseDefined(boolean _b) {
		setUseBack(_b);
		setUseFore(_b);
		setUseFont(_b);
	}

    /**
     * setUseBack
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setUseBack(boolean _b) {
		bUseBack = _b;
	}

    /**
     * setUseFore
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setUseFore(boolean _b) {
		bUseFore = _b;
	}

    /**
     * setUseFont
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setUseFont(boolean _b) {
		bUseFont = _b;
	}

	/**
     * @see java.awt.Component#getBackground()
     * @author Anthony C. Liberto
     */
    public Color getBackground() {
		if (EAccess.eaccess().canOverrideColor() && bUseBack) {
			if (isEnabled()) {
				return EAccess.eaccess().getBackground();
			} else {
				return EAccess.eaccess().getDisabledBackground();
			}
		}
		return super.getBackground();
	}

	/**
     * @see java.awt.Component#getForeground()
     * @author Anthony C. Liberto
     */
    public Color getForeground() {
		if (EAccess.eaccess().canOverrideColor() && bUseFore) {
			if (isEnabled()) {
				return EAccess.eaccess().getForeground();
			} else {
				return EAccess.eaccess().getDisabledForeground();
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
			return EAccess.eaccess().getFont();
		}
		return super.getFont();
	}

	/**
     * rebuild
     * @param _bar
     * @author Anthony C. Liberto
     */
    private void rebuild(EToolbar _bar) {
		bar = _bar;
		removeAllButtons();
		add(_bar.getToolbarItems());
		setFloatable(_bar.isFloatable());
		sAlign = _bar.getAlignmentString();
		setOrientation(_bar.getOrientationInt());
	}

	/**
     * getStringKey
     * @return
     * @author Anthony C. Liberto
     */
    protected String getStringKey() {
		return bar.getStringKey();
	}

	/**
     * add
     * @param _item
     * @author Anthony C. Liberto
     */
    private void add(ToolbarItem[] _item) {
		int ii = _item.length;
		for (int i=0;i<ii;++i) {
			add(_item[i]);
		}
	}

	/**
     * add
     * @param _item
     * @author Anthony C. Liberto
     */
    private void add(ToolbarItem _item) {
		if (_item.getType() == ToolbarItem.BUTTON_TYPE) {
			if (!has(_item.getKey())) {
				addButton(_item.getGif(), _item.getKey(), _item.getTipDisplay(), _item.getEnabled());
			}
		} else if (_item.getType() == ToolbarItem.CONTAINER_TYPE) {
			if (!has(_item.getKey())) {
				addSubButton(_item.getGif(), _item.getKey(), _item.getTipDisplay());
			}
		} else if (_item.getType() == ToolbarItem.SEPARATOR_TYPE) {
			addSeparator();
		} else if (_item.getType() == ToolbarItem.COMPONENT_TYPE) {
			addComponent(_item);
		}
	}

	private void addComponent(ToolbarItem _item) {
		if (obj == null) {
            return;
		}
		if (obj instanceof Component) {
			add((Component)obj);
		} else if (obj instanceof Component[]) {
		}
	}

	/**
     * setRemoteActionListener
     * @param _al
     * @author Anthony C. Liberto
     */
    private void setRemoteActionListener(ActionListener _al) {
		al = _al;
	}

	/**
     * getRemoteActionListener
     * @return
     * @author Anthony C. Liberto
     * /
    public ActionListener getRemoteActionListener() {
		return al;
	}*/

	/**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @author Anthony C. Liberto
     */
    public void actionPerformed(ActionEvent _ae) {
    	if (al != null) {
    		try{
			al.actionPerformed(_ae);
    		}catch(Exception x){
    			EAccess.eaccess().setBusy(false);
    			EAccess.eaccess().showException(x, this, ERROR_MESSAGE, OK);
    		}
		}
	}

	/**
     * addButton
     * @param _image
     * @param _key
     * @param _tip
     * @param _enabled
     * @author Anthony C. Liberto
     */
    public void addButton(String _image, String _key, String _tip, boolean _enabled) {
		addButton(_image,_key,_tip,_enabled,true);
	}

	private void addButton(String _image, String _key, String _tip, boolean _enabled, boolean _popup) {
		ERolloverButton btn = new ERolloverButton(getImageIcon(_image),_image);
		btn.setRolloverEnabled(true);
		btn.setActionCommand(_key);
		btn.setName(_key);
		if (_tip != null) {
			btn.setToolTipText(EAccess.eaccess().getString(_tip));
		}
		btn.addActionListener(this);
		btn.setEnabled(_enabled);
		btn.setAutoPopup(_popup);
		put(_key, btn);
		add(btn);
	}

	private void addSubButton(String _image, String _key, String _tip) {
		ERolloverContainer btn = new ERolloverContainer(getImageIcon(_image),_image);
		btn.setRolloverEnabled(true);
		btn.setActionCommand(_key);
		btn.setName(_key);
		if (_tip != null) {
			btn.setToolTipText(EAccess.eaccess().getString(_tip));
		}
		btn.setActionListener(this);
		btn.setAutoPopup(true);
		put(_key, btn);
		add(btn);
	}

	/**
     * remove
     * @param _items
     * @author Anthony C. Liberto
     * /
    public void remove(ToolbarItem[] _items) {
		int ii = _items.length;
		for (int i=0;i<ii;++i) {
			remove(_items[i].getKey());
		}
	}*/

	/**
     * remove
     * @param _key
     * @author Anthony C. Liberto
     */
    public void remove(String _key) {
		if (hash.containsKey(_key)) {
			Object o = hash.remove(_key);
			if (o instanceof EannounceButton) {
				EannounceButton item = (EannounceButton)o;
				remove((Component)item);
				item.removeActionListener(this);
				item.getUI().uninstallUI((JComponent)item);
				item.dereference();
				item.removeAll();
				item.removeNotify();
			}
		}
	}

	/**
     * @see javax.swing.JToolBar#addSeparator()
     * @author Anthony C. Liberto
     */
    public void addSeparator() {
		EmptySeparator sep = new EmptySeparator();
        add(sep);
	}

//50827	public void adjustSubAction(String _key, EANActionItem[] _eai, boolean _hasData) {
	/**
     * adjustSubAction
     * @param _key
     * @param _eai
     * @param _hasData
     * @param _type
     * @author Anthony C. Liberto
     */
    public void adjustSubAction(String _key, EANActionItem[] _eai, boolean _hasData, int _type) {			//50827
		Object o = get(_key);
		if (o instanceof ERolloverContainer) {
//50827			((eRolloverContainer)o).adjustSubItems(_eai, _hasData);
			((ERolloverContainer)o).adjustSubItems(_eai, _hasData,_type);									//50827
		}
	}

	/**
     * setOrientation
     * @param _item
     * @author Anthony C. Liberto
     * /
    public void setOrientation(ComboItem _item) {
		setOrientation(_item.getIntKey());
	}*/

	/**
     * setAlignment
     * @param _item
     * @author Anthony C. Liberto
     * /
    public void setAlignment(ComboItem _item) {
		setAlignment(_item.toString());
	}*/

	/**
     * setAlignment
     * @param _s
     * @author Anthony C. Liberto
     *
    private void setAlignment(String _s) {
		sAlign = _s;
	}*/

	/**
     * getAlignment
     * @return
     * @author Anthony C. Liberto
     */
    public String getAlignment() {
		return sAlign;
	}

	private void put(String _key, Object _o){
		hash.put(_key,_o);
	}

	/**
     * has
     * @param _key
     * @return
     * @author Anthony C. Liberto
     */
    private boolean has(String _key){
		if (hash == null) {
			return false;
		}
		return hash.containsKey(_key);
	}

	/**
     * get
     * @param _key
     * @return
     * @author Anthony C. Liberto
     */
    private Object get(String _key){
		if (has(_key)) {
			return hash.get(_key);
		}
		return null;
	}

	/**
     * getButton
     * @param _key
     * @return
     * @author Anthony C. Liberto
     */
    private EannounceButton getButton(String _key) {
		Object o = get(_key);
		if (o instanceof EannounceButton) {
			return (EannounceButton)o;
		}
		return null;
	}

	/**
     * isVisible
     * @param _key
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setVisible(String _key, boolean _b){
		EannounceButton btn = getButton(_key);
		if (btn != null) {
			btn.setVisible(_b);
		}
	}

	/**
     * isVisible
     * @param _key
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean isVisible(String _key){
		EannounceButton btn = getButton(_key);
		if (btn != null) {
			return btn.isVisible();
		}
		return false;
	}*/

	/**
     * setEnabled
     * @param _key
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setEnabled(String _key, boolean _b){
		EannounceButton btn = getButton(_key);
		if (btn != null) {
			btn.setEnabled(_b);
		}
	}

	/**
     * isEnabled
     * @param _key
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean isEnabled(String _key) {
		EannounceButton btn = getButton(_key);
		if (btn != null) {
			return btn.isEnabled();
		}
		return false;
	}*/

	/**
     * isShowing
     * @param _key
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean isShowing(String _key) {
		EannounceButton btn = getButton(_key);
		if (btn != null) {
			return btn.isShowing();
		}
		return false;
	}*/


	/**
     * removeAllButtons
     * @author Anthony C. Liberto
     */
    private void removeAllButtons() {
		while (!hash.isEmpty()) {
			Object o = hash.remove(0);
			if (o instanceof EannounceButton) {
				remove(((EannounceButton)o).getName());
			} else if (o instanceof JComponent){
				remove((JComponent)o);
			}
		}
	}

	/**
     * getImageIcon
     * @param _image
     * @return
     * @author Anthony C. Liberto
     */
    private ImageIcon getImageIcon(String _image) {
		return EAccess.eaccess().getImageIcon(_image);
	}

	/**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
		((BasicToolBarUI)getUI()).uninstallUI(this);		//21539
		removeAllButtons();
		obj = null;
		hash = null;
		al = null;
		sAlign = null;
		removeAll();
		removeNotify();
	}

/*
 acl_20030505
*/
	/**
     * refreshAppearance
     * @author Anthony C. Liberto
     */
    public void refreshAppearance() {
		int ii = getComponentCount();
		for (int i=0;i<ii;++i) {
			Component c = getComponentAtIndex(i);
			if (c instanceof ERolloverContainer) {
				((ERolloverContainer)c).refreshAppearance();
			}
		}
	}
/*
 acl_20031007
 */
	/**
     * setLookAndFeel
     * @author Anthony C. Liberto
     */
    private void setLookAndFeel() {
    	 /*breaks win7if (EAccess.isArmed(ACCESSIBLE_ARM_FILE)) {
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
		}*/
	}

    /**
     * refresh toolbar
     * MN_button_disappear
     * @author Anthony C. Liberto
     */
    public void refreshToolbar() {
		for (int i=0;i<getComponentCount();++i) {
			Component c = getComponentAtIndex(i);
			if (c instanceof ERolloverButton) {
				((ERolloverButton)c).refreshButton();
			}
		}
	}
}
