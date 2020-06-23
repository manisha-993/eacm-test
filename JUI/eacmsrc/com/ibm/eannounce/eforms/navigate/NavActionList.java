/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: NavActionList.java,v $
 * Revision 1.2  2008/01/30 16:26:55  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:44:49  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:14  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:38:00  tony
 * This is the initial load of OPICM
 *
 * Revision 1.6  2005/09/08 17:59:04  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.5  2005/02/03 21:26:12  tony
 * JTest Format Third Pass
 *
 * Revision 1.4  2005/02/01 00:27:55  tony
 * JTest Second Pass
 *
 * Revision 1.3  2005/01/26 23:42:26  tony
 * JTest Formatting
 *
 * Revision 1.2  2005/01/07 18:05:47  tony
 * rm_20050107
 * adjusted logic to allow for menu items and toolbar containers
 * to be populated by an array of keys instead of just a single one.
 *
 * Revision 1.1.1.1  2004/02/10 16:59:43  tony
 * This is the initial load of OPICM
 *
 * Revision 1.10  2003/12/30 20:41:27  tony
 * cr_3312
 *
 * Revision 1.9  2003/08/05 22:31:40  joan
 * fix null pointer
 *
 * Revision 1.8  2003/07/14 14:59:40  tony
 * 51433
 *
 * Revision 1.7  2003/06/19 16:09:41  tony
 * 51298 -- limited capability when working in the past.
 * updated past date logic.
 *
 * Revision 1.6  2003/05/14 21:34:39  tony
 * enhanced logic by adding test information.
 *
 * Revision 1.5  2003/04/28 16:27:20  tony
 * updated logic to adjust action enablement based
 * on if the current location contains data or not.
 *
 * Revision 1.4  2003/04/03 16:19:08  tony
 * changed refreshLookAndFeel to refreshAppearance()
 * updated logic to take into account displayed dialogs
 * as well.
 *
 * Revision 1.3  2003/03/11 00:33:25  tony
 * accessibility changes
 *
 * Revision 1.2  2003/03/05 18:54:24  tony
 * accessibility updates.
 *
 * Revision 1.1.1.1  2003/03/03 18:03:48  tony
 * This is the initial load of OPICM
 *
 * Revision 1.17  2002/11/20 16:30:48  tony
 * acl_20021120 -- added navigate and search functions to
 * menu and toolbar
 *
 * Revision 1.16  2002/11/11 22:55:40  tony
 * adjusted classification on the toggle
 *
 * Revision 1.15  2002/11/07 16:58:29  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.navigate;
import com.ibm.eannounce.eobjects.*;
import COM.ibm.eannounce.objects.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.*;
import javax.accessibility.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class NavActionList extends EScrollPane implements Accessible, NavAction, MouseListener, KeyListener {
	private static final long serialVersionUID = 1L;
	private Navigate parent = null;
//	private Vector data = new Vector();
	private EList list = new EList();
	private int clicks = -1;

	/**
     * navActionList
     * @param _parent
     * @param _clicks
     * @author Anthony C. Liberto
     */
    public NavActionList(Navigate _parent, int _clicks) {
		super();
		setViewportView(list);
		parent = _parent;
		clicks = _clicks;
		list.getAccessibleContext().setAccessibleDescription(getString("accessible.navAction"));
		list.addMouseListener(this);
		list.addKeyListener(this);
		Dimension d = new Dimension(200,200);
		setPreferredSize(d);
		setSize(d);
		setMinimumSize(UIManager.getDimension("eannounce.minimum"));
		return;
	}

	/**
     * @see java.awt.Component#setEnabled(boolean)
     * @author Anthony C. Liberto
     */
    public void setEnabled(boolean _b) {		//2003-04-28
		super.setEnabled(_b);					//2003-04-28
		list.setEnabled(_b);					//2003-04-28
		return;									//2003-04-28
	}											//2003-04-28

	/**
     * @see java.awt.Component#setBackground(java.awt.Color)
     * @author Anthony C. Liberto
     */
    public void setBackground(Color _c) {
		super.setBackground(_c);
		if (list != null) {
            list.setBackground(_c);
		}
	}

	/**
     * @see java.awt.Component#setForeground(java.awt.Color)
     * @author Anthony C. Liberto
     */
    public void setForeground(Color _c) {
		super.setForeground(_c);
		if (list != null) {
            list.setForeground(_c);
		}
	}

	/**
     * @see java.awt.Component#setFont(java.awt.Font)
     * @author Anthony C. Liberto
     */
    public void setFont(Font _f) {
		super.setFont(_f);
		if (list != null) {
            list.setFont(_f);
		}
	}

	/**
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseClicked(MouseEvent me) {
		if (me.getClickCount() == clicks) {
			navigate(list.getSelectedIndex());
		}
		return;
	}
	/**
     * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
     * @author Anthony C. Liberto
     */
    public void keyReleased(KeyEvent ke) {
		if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
			navigate(list.getSelectedIndex());
		}
	}

	/**
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseEntered(MouseEvent me) {}
	/**
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseExited(MouseEvent me) {}
	/**
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mousePressed(MouseEvent me) {}
	/**
     * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseReleased(MouseEvent me) {}
	/**
     * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
     * @author Anthony C. Liberto
     */
    public void keyPressed(KeyEvent ke) {}
	/**
     * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
     * @author Anthony C. Liberto
     */
    public void keyTyped(KeyEvent ke) {}

/*
	private void load(EntityGroup _eg) {
		if (_eg == null) {
            return;
		}
		load(_eg.getActionGroupTable(),null);
		return;
	}
*/
	/**
     * load
     *
     * @author Anthony C. Liberto
     * @param _table
     * @param _title
     */
    protected void load(RowSelectableTable _table, String _title) {
		list.load(_table,false,false);		//sort_remove
//sort_remove		list.load(_table,true,true);
		return;
	}

	/**
     * getActionItem
     * @author Anthony C. Liberto
     * @return EANActionItem
     * @param _key
     */
    public EANActionItem getActionItem(String _key) {
		int ii = list.getDataSize();
		for (int i=0;i<ii;++i) {
			Object o = list.getItemAt(i);
			if (o != null && o instanceof EANActionItem) {
				EANActionItem eai = (EANActionItem)o;
				if (_key.equals(eai.getKey())) {
					return eai;
				}
			}
		}
		return null;
	}

	/**
     * getActionItemArray
     * @author Anthony C. Liberto
     * @return EANActionItem[]
     * @param _key
     */
    public EANActionItem[] getActionItemArray(String _key) {
		Vector v = new Vector();
		int ii = list.getDataSize();
        int xx = -1;
		EANActionItem[] out = null;
		for (int i=0;i<ii;++i) {
			Object o = list.getItemAt(i);
			if (o != null && o instanceof EANActionItem) {
				EANActionItem eai = (EANActionItem)o;
//acl_20021120				if (eai.getPurpose().equals(_key)) {
				if (eai.getActionClass().equals(_key)) {		//acl_20021120
					v.add(eai);
				}
			}
		}
		if (v.isEmpty()) {
            return out;
		}
		xx = v.size();
		out = new EANActionItem[xx];
		for (int x=0;x<xx;++x) {
			out[x] = (EANActionItem)v.get(x);
		}
		return out;
	}

    /**
     * getSelectedActionItem
     *
     * @return
     * @author Anthony C. Liberto
     */
    public EANActionItem getSelectedActionItem() {
		Object o = list.getSelectedValue();
		if (o != null && o instanceof EANActionItem) {
			return (EANActionItem)o;
		}
		return null;
	}

    /**
     * navigate
     *
     * @param _i
     * @author Anthony C. Liberto
     */
    public void navigate(int _i) {
		if (_i >= 0) {
			navigate();
		}
		return;
	}

	private void navigate(EANActionItem _nai) {
		if (_nai != null) {
			parent.performAction(_nai,NAVIGATE_LOAD);
		}
		return;
	}

    /**
     * navigate
     *
     * @author Anthony C. Liberto
     */
    public void navigate() {
		navigate(getSelectedActionItem());
	}

    /**
     * getNavigate
     *
     * @return
     * @author Anthony C. Liberto
     */
    public Navigate getNavigate() {
		return parent;
	}

    /**
     * clear
     *
     * @author Anthony C. Liberto
     */
    public void clear() {				//22862
		list.clear();					//22862
		return;							//22862
	}									//22862

    /**
     * refreshAppearance
     *
     * @author Anthony C. Liberto
     */
    public void refreshAppearance() {
		revalidate();
		repaint();
		return;
	}

    /**
     * dereference
     *
     * @author Anthony C. Liberto
     */
    public void dereference() {
		clear();						//22862
		parent = null;
		list.removeMouseListener(this);
		list.removeKeyListener(this);
		list.clear();
		list = null;
		removeAll();
		return;
	}
/*
 51298
*/
	/**
     * load
     * @author Anthony C. Liberto
     * @param _ean
     * @param _title
     */
    public void load(EANActionItem[] _ean, String _title) {
		if (list == null) {
            return;
		}					//51639
		list.load(_ean,_title);
		setViewPosition(0,0);						//51443
		return;
	}

/*
 cr_3312
 */
    /**
     * expandAll
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void expandAll(boolean _b) {}

/*
 rm_20050107
 */
	/**
     * getActionItemArray
     * @author Anthony C. Liberto
     * @return EANActionItem[]
     * @param _key
     */
    public EANActionItem[] getActionItemArray(String[] _key) {
		EANActionItem[] out = null;
		if (_key != null) {
			int kk = _key.length;
			Vector v = new Vector();
			int ii = list.getDataSize();
			for (int i=0;i<ii;++i) {
				Object o = list.getItemAt(i);
				if (o != null && o instanceof EANActionItem) {
					EANActionItem eai = (EANActionItem)o;
					for (int k=0;k<kk;++k) {
						if (_key[k] != null && eai.getActionClass().equals(_key[k])) {
							v.add(eai);
						}
					}
				}
			}
			if (!v.isEmpty()) {
				int xx = v.size();
				out = new EANActionItem[xx];
				for (int x=0;x<xx;++x) {
					out[x] = (EANActionItem)v.get(x);
				}
			}
		}
		return out;
	}

}

