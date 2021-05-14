/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: NavDataSelector.java,v $
 * Revision 1.2  2008/01/30 16:26:54  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:44:49  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:01  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:59:05  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/02/09 18:55:24  tony
 * Scout Accessibility
 *
 * Revision 1.2  2005/02/03 16:38:53  tony
 * JTest Second Pass Wrapup
 *
 * Revision 1.1.1.1  2004/02/10 16:59:43  tony
 * This is the initial load of OPICM
 *
 * Revision 1.20  2003/12/22 21:38:16  tony
 * 53451
 *
 * Revision 1.19  2003/12/19 23:14:01  tony
 * acl20031219
 * updated logic so that on 'x' close click on longeditor
 * editing is completed.
 *
 * Revision 1.18  2003/12/01 19:46:27  tony
 * accessibility
 *
 * Revision 1.17  2003/12/01 17:44:14  tony
 * accessibility
 *
 * Revision 1.16  2003/08/28 22:54:32  tony
 * memory update
 *
 * Revision 1.15  2003/07/22 15:44:01  tony
 * 51494
 *
 * Revision 1.14  2003/07/10 20:11:29  tony
 * added shouldResetBusy Logic to assist in providing more
 * control of cursor busy.
 *
 * Revision 1.13  2003/06/05 20:15:22  tony
 * 51169
 *
 * Revision 1.12  2003/05/28 16:27:05  tony
 * 50924
 *
 * Revision 1.11  2003/04/18 20:10:28  tony
 * added tab placement to preferences.
 *
 * Revision 1.10  2003/04/16 17:39:30  tony
 * added setResizable to displayComponent.
 *
 * Revision 1.9  2003/04/11 19:27:33  tony
 * added toggletab logic.
 *
 * Revision 1.8  2003/04/03 18:51:48  tony
 * adjusted logic to display individualized icon
 * for each frameDialog/tab.
 *
 * Revision 1.7  2003/04/03 16:19:09  tony
 * changed refreshLookAndFeel to refreshAppearance()
 * updated logic to take into account displayed dialogs
 * as well.
 *
 * Revision 1.6  2003/03/25 21:44:48  tony
 * adjusted logic to integrate in the xmlEditor.
 *
 * Revision 1.5  2003/03/21 20:54:32  tony
 * updated getBackground, getForeground, and getFont logic.
 * added logic so that each can be set for the component.
 *
 * Revision 1.4  2003/03/13 21:17:39  tony
 * accessibility enhancements.
 *
 * Revision 1.3  2003/03/13 18:38:45  tony
 * accessibility and column Order.
 *
 * Revision 1.2  2003/03/11 00:33:25  tony
 * accessibility changes
 *
 * Revision 1.1.1.1  2003/03/03 18:03:49  tony
 * This is the initial load of OPICM
 *
 * Revision 1.10  2002/11/07 16:58:30  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.navigate;
import com.elogin.*;
import com.ibm.eannounce.eobjects.*;
import java.awt.*;
import java.awt.event.*;
import COM.ibm.eannounce.objects.*;
import javax.swing.*;
import javax.accessibility.AccessibleContext;

public class NavDataSelector extends EScrollPane implements MouseListener, KeyListener, DisplayableComponent {
	private static final long serialVersionUID = 1L;
	private NavData parent = null;
	private EList list = new EList();
	private int clicks = -1;
	private Dimension dSize = new Dimension(200,400);
	private InterfaceDialog id = null;
	private boolean bResize = false;

	public NavDataSelector(NavData _parent, int _clicks) {
		super();
		setViewportView(list);
		parent = _parent;
		clicks = _clicks;
		list.setCellRenderer(new listRender());											//51494
		list.addMouseListener(this);
		list.addKeyListener(this);
		setSize(dSize);
		setPreferredSize(dSize);
		setMinimumSize(UIManager.getDimension("eannounce.minimum"));
		initAccessibility("accessible.navData");
		return;
	}

	public void updateTabPlacement(boolean _revalidate) {}

	public Navigate getNavigate() {
		return parent.getNavigate();
	}

	public void clear() {
		list.clear();
	}

	public JMenuBar getMenuBar() {
		return null;
	}

//rst_update	public void load(EntityList _eList) {
//rst_update		list.load(_eList.getTable(),true,true);
	public void load(RowSelectableTable _tbl) {			//rst_update
		list.load(_tbl,true,true);						//rst_update
//		list.loadObject(_eg);
	}

	public void setBackground(Color _c) {
		super.setBackground(_c);
		if (list != null) list.setBackground(_c);
	}

	public void setForeground(Color _c) {
		super.setForeground(_c);
		if (list != null) list.setForeground(_c);
	}

	public void setFont(Font _f) {
		super.setFont(_f);
		if (list != null) list.setFont(_f);
	}

	private EntityGroup getSelected() {
		int index = list.getSelectedIndex();
		Object o = list.getItemAt(index);
		if (o instanceof EntityGroup)
			return (EntityGroup)o;
		return null;
	}

	public void mouseClicked(MouseEvent me) {
		if (me.getClickCount() == clicks) {								//22214
			EntityGroup eg = getSelected();								//22214
			if (eg != null) {											//22214
				toggleTab(parent.getIndexOf(eg.getKey()));		//22214
			}															//22214
		}																//22214
		return;
	}
	public void keyReleased(KeyEvent ke) {
		if (ke.getKeyCode() == KeyEvent.VK_ENTER) {						//22214
			EntityGroup eg = getSelected();								//22214
			if (eg != null) {											//22214
				toggleTab(parent.getIndexOf(eg.getKey()));		//22214
			}															//22214
		}																//22214
	}

	private void toggleTab(int _i) {
		parent.toggleTab(_i);
		if (id != null) {
			if (id instanceof FrameDialog) {
				((FrameDialog)id).toFront();
			}
		}
		return;
	}

	public void mouseEntered(MouseEvent me) {}
	public void mouseExited(MouseEvent me) {}
	public void mousePressed(MouseEvent me) {}
	public void mouseReleased(MouseEvent me) {}
	public void keyPressed(KeyEvent ke) {}
	public void keyTyped(KeyEvent ke) {}

	public void setParentDialog(InterfaceDialog _id) {
		id = _id;
		return;
	}

	public InterfaceDialog getParentDialog() {
		return id;
	}

	public void disposeDialog() {
		eaccess().dispose(getParentDialog());
		return;
	}

	public void showMe() {}
	public void hideMe() {}
	public void activateMe() {}

	public void setResizable(boolean _b) {
		bResize = _b;
		return;
	}

	public boolean isResizable() {
		return bResize;
	}
	public void setTitle(String _title) {}
	public String getTitle() {
		return getString("selector.panel");
	}

	public void refreshAppearance() {
		return;
	}

	public void dereference() {
		initAccessibility(null);
		parent = null;
		id = null;
		clear();
		list.removeMouseListener(this);
		list.removeKeyListener(this);
		list = null;
		removeAll();
		return;
	}

	public String getPanelType() {
		return TYPE_NAVDATASELECTOR;
	}

	public String getIconName() {
		return DEFAULT_ICON;
	}

	public boolean isPanelType(String _s) {
		return _s.equals(getPanelType());
	}
/*
 50924
 */
 	public Object getSearchableObject() {
		return null;
	}

/*
 51169
 */
 	public void toFront() {
		if (id != null) {
			id.toFront();
		}
		return;
	}

	public boolean shouldResetBusy() {
		return true;
	}

/*
 51494
 */
 	class listRender extends ELabel implements ListCellRenderer {
 		private static final long serialVersionUID = 1L;
 		public listRender() {
			super();
			return;
		}

		public Component getListCellRendererComponent(JList _list, Object _val, int _index, boolean _select, boolean _focus) {
			if (_val instanceof EntityGroup) {
				EntityGroup eg = (EntityGroup)_val;
				setText(eg.toString() + " (" + eg.getEntityItemCount() + ")");
			} else {
				setText(_val.toString());
			}

			if (_select) {
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
			} else {
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}
			if (_focus) {
				setBorder(UIManager.getBorder("eannounce.focusborder"));
			} else {
				setBorder(UIManager.getBorder("eannounce.emptyBorder"));
			}
			return this;
		}

		public void dereference() {
			removeAll();
			removeNotify();
			return;
		}
	}

/*
 access
 */
	public String getAccessibleDescription() {
		return "";
	}

	public boolean hasCustomFocusPolicy() {
		return false;
	}

/*
 acl20031219
 */
	public boolean canWindowClose() {
		return true;
	}

/*
 53451
 */
	public FilterGroup getFilterGroup() {
		return null;
	}

	public void setFilterGroup(FilterGroup _fg) {}
/*
 accessibility
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

