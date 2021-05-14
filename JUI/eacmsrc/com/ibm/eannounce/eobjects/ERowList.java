/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: ERowList.java,v $
 * Revision 1.2  2008/01/30 16:26:56  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:45:47  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:17  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:38:17  tony
 * This is the initial load of OPICM
 *
 * Revision 1.8  2005/09/08 17:59:09  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.7  2005/02/09 19:29:51  tony
 * JTest After Scout
 *
 * Revision 1.6  2005/02/09 18:55:25  tony
 * Scout Accessibility
 *
 * Revision 1.5  2005/02/08 21:38:39  tony
 * JTest Formatting
 *
 * Revision 1.4  2005/02/03 21:26:14  tony
 * JTest Format Third Pass
 *
 * Revision 1.3  2005/01/28 19:36:59  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/26 18:20:08  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:49  tony
 * This is the initial load of OPICM
 *
 * Revision 1.9  2003/12/17 16:46:11  tony
 * 52910
 *
 * Revision 1.8  2003/09/17 15:35:28  tony
 * 52284
 *
 * Revision 1.7  2003/08/29 16:55:43  tony
 * 51990
 *
 * Revision 1.6  2003/08/18 17:28:48  tony
 * 51780
 *
 * Revision 1.5  2003/06/04 18:46:27  tony
 * 51112
 *
 * Revision 1.4  2003/04/24 15:33:12  tony
 * updated logic to include preference for selection fore and
 * back ground.
 *
 * Revision 1.3  2003/04/11 20:02:32  tony
 * added copyright statements.
 *
 */
package com.ibm.eannounce.eobjects;
import com.elogin.*;
import com.ibm.eannounce.eforms.table.*;
import javax.swing.*;
import java.awt.*;
import java.util.Vector;
import javax.accessibility.AccessibleContext;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class ERowList extends EList2  {
	private static final long serialVersionUID = 1L;
	private Vector data = new Vector();
	private Dimension listSize = new Dimension();
	private RowHeaderLabel lblHeader = new RowHeaderLabel();
	private RSTable table = null;
	private GTable m_gtable = null;
	private HeadRend header = null;

	/**
     * eRowList
     * @author Anthony C. Liberto
     */
    public ERowList() {
		super();
		setListData(data);
		header = new HeadRend(this);
		setCellRenderer(header);
		setOpaque(false);
		lblHeader.setFont(getFont());
		initAccessibility("accessible.rowList");
		return;
	}

	/**
     * getListSize
     * @return
     * @author Anthony C. Liberto
     */
    public Dimension getListSize() {
		return listSize;
	}

	/**
     * clear
     * @author Anthony C. Liberto
     */
    public void clear() {
		data.clear();
	}

	/**
     * setTable
     * @param _table
     * @author Anthony C. Liberto
     */
    public void setTable(RSTable _table) {
		table = _table;
		lblHeader.setTable(_table);
		return;
	}

	/**
     * setGTable
     * @param _gtable
     * @author Anthony C. Liberto
     */
    public void setGTable(GTable _gtable) {
		m_gtable = _gtable;
		//lblHeader.setTable(_table);
		return;
	}

	/**
     * getTable
     * @return
     * @author Anthony C. Liberto
     */
    public RSTable getTable() {
		return table;
	}

	/**
     * getGTable
     * @return
     * @author Anthony C. Liberto
     */
    public GTable getGTable() {
		return m_gtable;
	}

	/**
     * setHeaderValue
     * @param _s
     * @author Anthony C. Liberto
     */
    public void setHeaderValue(String _s) {
		lblHeader.setText(_s);
	}

	/**
     * getHeaderValue
     * @return
     * @author Anthony C. Liberto
     */
    public String getHeaderValue() {
		return lblHeader.getText();
	}

	/**
     * getHeader
     * @return
     * @author Anthony C. Liberto
     */
    public RowHeaderLabel getHeader() {
		return lblHeader;
	}

	/**
     * toggleIcon
     * @param _b
     * @author Anthony C. Liberto
     */
    public void toggleIcon(boolean _b) {
		lblHeader.toggleIcon(_b);
		return;
	}

	/**
     * getDirection
     * @return
     * @author Anthony C. Liberto
     */
    public boolean getDirection() {
		return lblHeader.getDirection();
	}

	/**
     * getItemAt
     * @param _i
     * @return
     * @author Anthony C. Liberto
     */
    public Object getItemAt(int _i) {
		return data.get(_i);
	}

	/**
     * removeAt
     * @param _i
     * @return
     * @author Anthony C. Liberto
     */
    public Object removeAt(int _i) {
		return data.remove(_i);
	}

	/**
     * refresh
     * @param _o
     * @author Anthony C. Liberto
     */
    public void refresh(Object[] _o) {
		int ii = 0;
        int w = 0;
        clear();
		if (_o == null) {
			return;
		}
		ii = _o.length;
//21773		int w = widthOf(getHeaderValue()) + 5;
		w = widthOf(getHeaderValue()) + 15;			//21773
		for (int i=0;i<ii;++i) {
			addItem(_o[i]);
			w = Math.max(w,widthOf(_o[i]));
		}
		setFixedCellWidth(w);
		if (table != null) {
			listSize.setSize(w,table.getPreferredHeight());
		} else if (m_gtable != null) {
			listSize.setSize(w,m_gtable.getPreferredSize().height);
		} else {
			listSize.setSize(w,(getFixedCellHeight() * ii));
		}
		setListData(data);
		setSize(listSize);
		setPreferredSize(listSize);
		return;
	}

	/**
     * @see java.awt.Component#getSize()
     * @author Anthony C. Liberto
     */
    public Dimension getSize() {
		if (table != null) {
			listSize.setSize(listSize.width, table.getPreferredHeight());
			return listSize;
		} if (m_gtable != null) {
			listSize.setSize(listSize.width, m_gtable.getPreferredSize().height);
			return listSize;
		}
		return super.getSize();
	}

	private int widthOf(Object _o) {
		if (_o != null) {
			FontMetrics fm = getFontMetrics(getFont());
			return fm.stringWidth(_o.toString()) + 7;
		}
		return 27;
	}

	/**
     * removeItem
     * @param _o
     * @author Anthony C. Liberto
     */
    public void removeItem(Object _o) {
		data.remove(_o);
		return;
	}

	/**
     * addItem
     * @param _o
     * @author Anthony C. Liberto
     */
    public void addItem(Object _o) {
		data.add(_o);
		return;
	}

	/**
     * addItem
     * @param _i
     * @param _o
     * @author Anthony C. Liberto
     */
    public void addItem(int _i, Object _o) {
		data.add(_i,_o);
		return;
	}

	/**
     * getDataSize
     * @return
     * @author Anthony C. Liberto
     */
    public int getDataSize() {
		return data.size();
	}

	/**
     * getData
     * @return
     * @author Anthony C. Liberto
     */
    public Vector getData() {
		return data;
	}

	/**
     * setData
     * @param _v
     * @author Anthony C. Liberto
     */
    public void setData(Vector _v) {
		data = _v;
		setListData(data);
	}

	/**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
		initAccessibility(null);
		data.clear();
		data = null;
		listSize = null;

		lblHeader.dereference();
		lblHeader = null;

		header.dereference();
		header = null;

		table = null;
		m_gtable = null;
		return;
	}

	private int getHeight(int _row) {
		if (table != null) {
			return table.getRowHeight(_row);
			//return Math.max(table.getRowHeight(_row), table.getRowHeight());
		} else if (m_gtable != null) {
			return m_gtable.getRowHeight(_row);
		}
		return getFixedCellHeight();
	}

	private boolean isVariableHeight() {
		return (getFixedCellHeight() == -1);
	}

/*
 * end scroll
 */
	private class HeadRend extends ELabel implements ListCellRenderer {
		private static final long serialVersionUID = 1L;
		private ERowList rList = null;

		/**
         * headRend
         * @param _rList
         * @author Anthony C. Liberto
         */
        private HeadRend(ERowList _rList) {
			rList = _rList;
			setOpaque(true);
			setBorder(UIManager.getBorder("eannounce.raisedBorder"));
			return;
		}

		/**
         * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
         * @author Anthony C. Liberto
         */
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			setText(value.toString());
			setFont(list.getFont());

			if (isVariableHeight()) {
				this.setPreferredSize(new Dimension(rList.widthOf(value),rList.getHeight(index)));
			}

			return this;
		}

		/**
         * dereference
         * @author Anthony C. Liberto
         */
        public void dereference() {
			rList = null;
			removeAll();
			removeNotify();
			return;
		}
	}

/*
 51112
 */
	/**
     * sort
     * @author Anthony C. Liberto
     */
    public void sort() {
		lblHeader.doClick();
		return;
	}

/*
 51780
 */
	/**
     * @see javax.swing.JList#getFixedCellHeight()
     * @author Anthony C. Liberto
     */
    public int getFixedCellHeight() {
		if (table instanceof HorzTable) {
			return table.getRowHeight();			//51990
//51990			return table.getPreferredHeight();
		} else if (table instanceof CrossTable) {	//52284
			return table.getRowHeight();			//52284
		}
		return super.getFixedCellHeight();
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
					strAccessible = eaccess().getString(_s);
					ac.setAccessibleName(strAccessible);
					ac.setAccessibleDescription(strAccessible);
				}
			}
		}
		return;
	}
}
