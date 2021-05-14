/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2003/05/21
 * @author Anthony C. Liberto
 *
 * $Log: EScrollPopup.java,v $
 * Revision 1.2  2008/01/30 16:26:56  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:45:47  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:17  tony
 * This is the initial load of OPICM
 *
 * Revision 1.8  2005/09/08 17:59:09  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.7  2005/01/28 19:36:59  tony
 * JTest Second Pass
 *
 * Revision 1.6  2005/01/26 18:20:08  tony
 * JTest Formatting
 *
 * Revision 1.5  2004/06/22 18:08:15  tony
 * accessible
 *
 * Revision 1.4  2004/04/08 19:52:50  tony
 * enhanced menu scrolling
 *
 * Revision 1.3  2004/04/01 17:12:45  tony
 * updated scroll delay
 *
 * Revision 1.2  2004/03/25 23:36:07  tony
 * cr_209046022
 *
 * Revision 1.1.1.1  2004/02/10 16:59:50  tony
 * This is the initial load of OPICM
 *
 * Revision 1.9  2003/10/07 21:33:41  tony
 * added setlookandfeel to assist in accessibility
 * high contrast functionality.
 *
 * Revision 1.8  2003/06/05 17:07:38  tony
 * added refreshAppearance logic to the toolbar for the container.
 * This will flow thru to the scrollable popup.
 * Adjusted the sizing logic on the scrollable popup.
 *
 * Revision 1.7  2003/05/23 16:01:36  tony
 * 50890
 *
 * Revision 1.6  2003/05/21 22:36:05  tony
 * added visible menu size to eAccessConstants.
 *
 * Revision 1.5  2003/05/21 22:31:57  tony
 * improved functionality.
 *
 * Revision 1.4  2003/05/21 22:15:11  tony
 * updated logic for adding to popup.
 *
 * Revision 1.3  2003/05/21 21:57:47  tony
 * adjusted visibility of up and down buttons.
 * adjusted when the popup gets displayed.
 *
 * Revision 1.2  2003/05/21 20:44:18  tony
 * added log file
 *
 *
 */
package com.ibm.eannounce.eobjects;
import com.elogin.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.accessibility.*;
import javax.swing.*;
import javax.swing.plaf.basic.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class EScrollPopup extends JPopupMenu implements MouseListener, EAccessConstants, Accessible {
	private static final long serialVersionUID = 1L;
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

	private JButton btnUp = new BasicArrowButton(BasicArrowButton.NORTH) {
		private static final long serialVersionUID = 1L;
		public boolean isVisible() {
			return isButtonVisible();
		}
	};
	private JButton btnDown = new BasicArrowButton(BasicArrowButton.SOUTH) {
		private static final long serialVersionUID = 1L;
		public boolean isVisible() {
			return isButtonVisible();
		}
	};

	private int visIndex = 0;
	private int iWidth = 0;					//50890
	private Vector v = new Vector();
	private boolean bScroll = false;		//cr_6022

	/**
     * eScrollPopup
     * @author Anthony C. Liberto
     */
    public EScrollPopup() {
		super();
		init();
		return;
	}

	private void init() {
		add(btnUp);
//cr_6022		btnUp.addActionListener(this);
//cr_6022		btnUp.setActionCommand("up");
		btnUp.addMouseListener(this);				//cr_6022
		add(btnDown);
//cr_6022		btnDown.addActionListener(this);
//cr_6022		btnDown.setActionCommand("down");
		btnDown.addMouseListener(this);				//cr_6022
		reset();
		return;
	}

	/**
     * isButtonVisible
     * @return
     * @author Anthony C. Liberto
     */
    protected boolean isButtonVisible() {
		if (v != null) {
			return v.size() >= MAX_VISIBLE_MENU_ITEMS;
		}
		return false;
	}

	/**
     * @see javax.swing.JPopupMenu#show(java.awt.Component, int, int)
     * @author Anthony C. Liberto
     */
    public void show(Component _c, int _x, int _y) {
		if (v.isEmpty()) {
			return;
		}
		reset();
		super.show(_c,_x,_y);
		return;
	}

	/**
     * reset
     * @author Anthony C. Liberto
     */
    public void reset() {
		visIndex = 0;
		adjustIndex(0);
		btnUp.setEnabled(false);
		return;
	}

	/**
     * @see javax.swing.JPopupMenu#add(javax.swing.JMenuItem)
     * @author Anthony C. Liberto
     */
    public JMenuItem add(JMenuItem _item) {
		int i = v.size();
		JMenuItem out = (JMenuItem)super.add(_item,(i+1));
		iWidth = Math.max(iWidth, (getWidth(out) + 20));			//50890
		v.add(out);
		return out;
	}

	/**
     * getMenuItem
     * @param _i
     * @return
     * @author Anthony C. Liberto
     */
    public Component getMenuItem(int _i) {
		if (_i >= 0 && _i < getMenuItemCount()) {
			return (Component)v.get(_i);
		}
		return null;
	}

	/**
     * getMenuItemCount
     * @return
     * @author Anthony C. Liberto
     */
    public int getMenuItemCount() {
		return v.size();
	}

	/**
     * removeMenuItem
     * @param _c
     * @author Anthony C. Liberto
     */
    public void removeMenuItem(Component _c) {
		v.remove(_c);
		if (v.isEmpty()) {
			iWidth = 0;
		}
		remove(_c);
		return;
	}

	/**
     * toggle
     * @param _item
     * @param _b
     * @author Anthony C. Liberto
     */
    public void toggle(JMenuItem _item, boolean _b) {
		_item.setVisible(_b);
		_item.setEnabled(_b);
		return;
	}

/*
 cr_6022
	public void actionPerformed(ActionEvent _ae) {
		String action = _ae.getActionCommand();
		if (action.equals("up")) {
			adjustIndex(-1);
		} else if (action.equals("down")) {
			adjustIndex(1);
		}
		return;
	}
*/

	private void adjustIndex(int _i) {
        int ii = v.size();
        int maxVis = -1;
		visIndex += _i;
		visIndex = Math.min(visIndex, (ii - MAX_VISIBLE_MENU_ITEMS));
		visIndex = Math.max(0,visIndex);
		maxVis = visIndex + MAX_VISIBLE_MENU_ITEMS;
		for (int i=0;i<ii;++i) {
			JMenuItem item = (JMenuItem)v.get(i);
			if (i < visIndex) {
				toggle(item,false);
			} else if (i >= maxVis) {
				toggle(item,false);
			} else {
				toggle(item,true);
			}
		}
		btnDown.setEnabled(maxVis != ii);
		btnUp.setEnabled(visIndex != 0);
		return;
	}

	/**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
//cr_6022		btnUp.removeActionListener(this);
//cr_6022		btnDown.removeActionListener(this);
		btnUp.removeMouseListener(this);			//cr_6022
		btnDown.removeMouseListener(this);			//cr_6022
		v.clear();
		iWidth = 0;
		removeAll();
		removeNotify();
		return;
	}
/*
 50890
*/
	/**
     * @see java.awt.Component#getPreferredSize()
     * @author Anthony C. Liberto
     */
    public Dimension getPreferredSize() {
		Dimension out = super.getPreferredSize();
		out.setSize(Math.min(iWidth,500),out.height);
		return out;
	}

	/**
     * getWidth
     * @param _item
     * @return
     * @author Anthony C. Liberto
     */
    public int getWidth(JMenuItem _item) {
		if (_item != null) {
			FontMetrics fm = getFontMetrics(getFont());
			return fm.stringWidth(_item.getText()) + fm.getAscent() + fm.getDescent();
		}
		return 10;
	}
/*
 acl_20030505
*/
	/**
     * refreshAppearance
     * @author Anthony C. Liberto
     */
    public void refreshAppearance() {
        int ii = v.size();
        iWidth = 0;
		for (int i=0;i<ii;++i) {
			iWidth = Math.max(iWidth, getWidth((JMenuItem)v.get(i)));
		}
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
     * setUseDefined
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setUseDefined(boolean _b) {
		setUseBack(_b);
		setUseFore(_b);
		setUseFont(_b);
		return;
	}

	/**
     * setUseBack
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setUseBack(boolean _b) {
		bUseBack = _b;
		return;
	}

	/**
     * setUseFore
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setUseFore(boolean _b) {
		bUseFore = _b;
		return;
	}

	/**
     * setUseFont
     * @param _b
     * @author Anthony C. Liberto
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
 cr_6022

 */
	/**
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseEntered(MouseEvent _me) {}
	/**
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseExited(MouseEvent _me) {}
	/**
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseClicked(MouseEvent _me) {}


	/**
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mousePressed(MouseEvent _me) {
		Object source = _me.getSource();
		if (source == btnUp) {
			bScroll = true;
			adjustIndexLoop(btnUp,-1);
		} else if (source == btnDown) {
			bScroll = true;
			adjustIndexLoop(btnDown,1);
		}
		return;
	}

	/**
     * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseReleased(MouseEvent _me) {
		bScroll = false;
		return;
	}

	/**
     * adjustIndexLoop
     * @param _btn
     * @param _i
     * @author Anthony C. Liberto
     */
    public void adjustIndexLoop(final JButton _btn, final int _i) {
		final ESwingWorker myWorker = new ESwingWorker() {
			public Object construct() {
				while(_btn.isEnabled() && bScroll) {
					adjustIndex(_i);
					pause(100);
				}
				return null;
			}

			public void finished() {
				bScroll = false;
				eaccess().setWorker(null);
				eaccess().setBusy(false);
				return;
			}
		};
		myWorker.start();
		return;
	}
}
