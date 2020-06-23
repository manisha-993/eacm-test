/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: EPopupMenu.java,v $
 * Revision 1.2  2008/01/30 16:27:00  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:42:17  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:09  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:38  tony
 * This is the initial load of OPICM
 *
 * Revision 1.5  2005/09/08 17:58:54  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.4  2005/07/20 18:23:59  tony
 * added canShowPopup to improve functionality based on
 * where i sit.
 *
 * Revision 1.3  2005/02/02 21:30:08  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 23:18:16  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:25  tony
 * This is the initial load of OPICM
 *
 * Revision 1.10  2003/10/07 21:36:58  tony
 * added setlookandfeel to assist in accessibility
 * high contrast functionality.
 *
 * Revision 1.9  2003/04/11 20:02:26  tony
 * added copyright statements.
 *
 */
package com.elogin;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import javax.swing.*;
import javax.accessibility.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class EPopupMenu extends JPopupMenu implements Accessible, MouseListener, EAccessConstants, EDisplayable {
	private static final long serialVersionUID = 1L;
	private HashMap hash = new HashMap();
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
     * ePopupMenu
     * @author Anthony C. Liberto
     */
    public EPopupMenu() {
		super();
		return;
	}

	/**
     * ePopupMenu
     * @param _title
     * @author Anthony C. Liberto
     */
    public EPopupMenu(String _title) {
		super(_title);
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

	/**
     * @see java.awt.Container#removeAll()
     * @author Anthony C. Liberto
     */
    public void removeAll() {
		super.removeAll();
		if (hash != null) {
            hash.clear();
		}
	}

	/**
     * @see java.awt.Component#setFont(java.awt.Font)
     * @author Anthony C. Liberto
     */
    public void setFont(Font _f) {
		super.setFont(_f);
	}

	/**
     * addPopupMenu
     * @param _s
     * @param _enabled
     * @param _al
     * @author Anthony C. Liberto
     */
    public void addPopupMenu(String _s, boolean _enabled, ActionListener _al) {
		add(generateMenuItem(_s,_s,_enabled,_al));
	}

	/**
     * addPopupMenu
     * @param _s
     * @param _al
     * @author Anthony C. Liberto
     */
    public void addPopupMenu(String _s, ActionListener _al) {
		add(generateMenuItem(_s,_s,true,_al));
	}

	/**
     * addPopupMenu
     * @param _name
     * @param _s
     * @param _al
     * @author Anthony C. Liberto
     */
    public void addPopupMenu(String _name, String _s, ActionListener _al) {
		add(generateMenuItem(_name,_s, true,_al));
	}

	private JMenuItem generateMenuItem(String _name, String _s, boolean _enabled, ActionListener _al) {
		JMenuItem menu = new JMenuItem(getString(_s));
		menu.setActionCommand(_s);
		menu.addActionListener(_al);
//memory		menu.setFont(getFont());
		menu.setName(_name);
		menu.setEnabled(_enabled);
		hash.put(_name, menu);
		return menu;
	}

	/**
     * removeMenu
     * @param _s
     * @param _al
     * @author Anthony C. Liberto
     */
    public void removeMenu(String _s, ActionListener _al) {		//acl_Mem_20020130
		if (hash.containsKey(_s)) {								//acl_Mem_20020130
			Object o = hash.remove(_s);							//acl_Mem_20020130
			if (o instanceof JMenuItem) {						//acl_Mem_20020130
				JMenuItem item = (JMenuItem)o;					//acl_Mem_20020130
				remove(item);									//acl_Mem_20020130
				item.removeActionListener(_al);					//acl_Mem_20020130
				item.getUI().uninstallUI(item);					//acl_Mem_20020130
				item.removeAll();								//acl_Mem_20020130
				item.removeNotify();							//acl_Mem_20020131
			}													//acl_Mem_20020130
		}														//acl_Mem_20020130
		return;													//acl_Mem_20020130
	}															//acl_Mem_20020130
/*
	private void setMnemonic(JMenuItem _menu, String _s) {
		_menu.setMnemonic(getChar(_s));
		return;
	}
*/
	/**
     * setEnabled
     * @param _name
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setEnabled(String _name, boolean _b) {
		if (hash.containsKey(_name)) {
			Object o = hash.get(_name);
			if (o instanceof JMenuItem) {
				((JMenuItem)o).setEnabled(_b);
			}
		}
		return;
	}

	/**
     * setVisible
     * @param _name
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setVisible(String _name, boolean _b) {
		if (hash.containsKey(_name)) {
			Object o = hash.get(_name);
			if (o instanceof JMenuItem) {
				((JMenuItem)o).setVisible(_b);
			}
		}
		return;
	}

	/**
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseExited(MouseEvent e) {}
	/**
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseEntered(MouseEvent e) {}
	/**
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseClicked(MouseEvent e) {}
	/**
     * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseReleased(MouseEvent e) {}
	/**
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mousePressed(MouseEvent e) {
		maybeShowPopup(e);
	}

	private void maybeShowPopup(MouseEvent e) {
		if (canShowPopup(e)) {
			if (SwingUtilities.isRightMouseButton(e)) {
				show(e.getComponent(),e.getX(),e.getY());
			}
		}
		return;
	}
	/**
	 * can show popup
	 * @param _e
	 * @return boolean
	 * @author tony
	 */
	public boolean canShowPopup(MouseEvent _e) {
		return true;
	}

	/**
     * @see javax.swing.JPopupMenu#show(java.awt.Component, int, int)
     * @author Anthony C. Liberto
     */
    public void show(Component _c, int _x, int _y) {									//22916
		JFrame frame = eaccess().getLogin();
		Dimension scr = frame.getSize();										//22916
		Dimension pSize = getPreferredSize();											//22916
		Point ptO = _c.getLocationOnScreen();											//22916
		Point ptS = frame.getLocationOnScreen();								//22916
		Point pt = new Point(ptO.x - ptS.x, ptO.y - ptS.y);								//22916
		super.show(_c,getXLocation(_x,scr,pSize,pt),getYLocation(_y,scr,pSize,pt));		//22916
		return;																			//22916
	}																					//22916

	private int getXLocation(int _x, Dimension _d, Dimension _popSize, Point _pt) {		//22916
		int x = _x;																		//22916
		if ((_d.width - (_popSize.width + _pt.x + _x)) < 0) {							//22916
			x = x - _popSize.width;														//22916
		}																				//22916
		return x;																		//22916
	}																					//22916

	private int getYLocation(int _y, Dimension _d, Dimension _popSize, Point _pt) {		//22916
		int y = _y;																		//22916
		if ((_d.height - (_popSize.height + _pt.y + _y)) < 0) {							//22916
			y = y - _popSize.height;													//22916
		}																				//22916
		return y;																		//22916
	}																					//22916

	/**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
		getUI().uninstallUI(this);								//acl_Mem_20020130
		removeAll();
		removeNotify();											//acl_Mem_20020130
		hash = null;
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
}

