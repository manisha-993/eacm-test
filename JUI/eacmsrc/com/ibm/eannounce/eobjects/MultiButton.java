/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: MultiButton.java,v $
 * Revision 1.2  2008/01/30 16:26:56  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:45:48  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:17  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:38:19  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:59:10  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/01/28 19:37:00  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/26 18:20:09  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:50  tony
 * This is the initial load of OPICM
 *
 * Revision 1.2  2003/03/21 20:54:34  tony
 * updated getBackground, getForeground, and getFont logic.
 * added logic so that each can be set for the component.
 *
 * Revision 1.1.1.1  2003/03/03 18:03:52  tony
 * This is the initial load of OPICM
 *
 * Revision 1.7  2002/11/07 16:58:19  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eobjects;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class MultiButton extends ERolloverButton {
	private static final long serialVersionUID = 1L;
	private JPopupMenu popup = new JPopupMenu();
	private ActionListener action = null;

	/**
     * multiButton
     * @param _s
     * @param _al
     * @author Anthony C. Liberto
     */
    public MultiButton(String[] _s, ActionListener _al) {
		super();
		setIcon(eaccess().getImageIcon(_s[0]));			//20011112
		addActionListener(_al);
		add(_s);
	}

	/**
     * @see java.awt.Component#setBackground(java.awt.Color)
     * @author Anthony C. Liberto
     */
    public void setBackground(Color _c) {
		super.setBackground(_c);
		if (popup != null) {
            popup.setBackground(_c);
		}
	}

	/**
     * @see java.awt.Component#setForeground(java.awt.Color)
     * @author Anthony C. Liberto
     */
    public void setForeground(Color _c) {
		super.setForeground(_c);
		if (popup != null) {
            popup.setForeground(_c);
		}
	}

	/**
     * @see java.awt.Component#setFont(java.awt.Font)
     * @author Anthony C. Liberto
     */
    public void setFont(Font _f) {
		super.setFont(_f);
		if (popup != null) {
            popup.setFont(_f);
		}
	}

	/**
     * @see javax.swing.AbstractButton#addActionListener(java.awt.event.ActionListener)
     * @author Anthony C. Liberto
     */
    public void addActionListener(ActionListener _al) {
		action = _al;
	}

	/**
     * removeActionListener
     * @author Anthony C. Liberto
     * @param _al
     */
    public void removeActionListener(ActionListener _al) {
		action = null;
	}

	/**
     * add
     * @param _s
     * @author Anthony C. Liberto
     */
    public void add(String[] _s) {
		int ii = _s.length;
		if (ii <= 1) {
            return;
		}
		for (int i=1;i<ii;++i) {							//20011112
			popup.add(generateMenuItem(_s[i]));
		}
	}

	private JMenuItem generateMenuItem(String in) {
		String s = eaccess().getString(in);
		JMenuItem jmi = new JMenuItem(s);
		setMnemonic(jmi, in + "-s");
		if (action != null) {
            jmi.addActionListener(action);
		}
		jmi.setActionCommand(in);
		jmi.setFont(getFont());
		return jmi;
	}

	private void setMnemonic(JMenuItem _menu, String _s) {
		_menu.setMnemonic(eaccess().getChar(_s));
		return;
	}

	/**
     * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseReleased(MouseEvent _me) {
		super.mouseReleased(_me);
		maybeShowPopup(_me);
	}

	/**
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseEntered(MouseEvent _me) {
		if (!popup.isShowing()) {
			raiseBorder();
		}
		return;
	}

	/**
     * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseMoved(MouseEvent _me) {
		if (!popup.isShowing()) {
			raiseBorder();
		}
		return;
	}

	/**
     * maybeShowPopup
     * @param e
     * @author Anthony C. Liberto
     */
    public void maybeShowPopup(MouseEvent e) {
		if (!isEnabled()) {
            return;
		}
		if (!e.isPopupTrigger() && contains(e.getPoint()) && popup.getComponentCount() > 0) {
			popup.show(e.getComponent(),e.getX(),e.getY());
		}
	}

	/**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
		while (popup.getComponentCount() > 0) {
			Component c = popup.getComponent(0);
			if (c instanceof JMenuItem) {
				((JMenuItem)c).removeActionListener(action);
			}
			popup.remove(c);
		}
		popup.removeAll();
		popup = null;
		action = null;
		super.dereference();
	}

}
