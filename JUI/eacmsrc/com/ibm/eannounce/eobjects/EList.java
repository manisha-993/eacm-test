/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: EList.java,v $
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
 * Revision 1.5  2004/09/15 22:45:49  tony
 * updated blue pages add logic
 *
 * Revision 1.4  2004/06/30 17:05:31  tony
 * 5ZKMGH
 *
 * Revision 1.3  2004/04/26 21:29:18  tony
 * 53825
 *
 * Revision 1.2  2004/03/12 23:07:47  tony
 * cr_6303
 * send bookmark to a friend.
 *
 * Revision 1.1.1.1  2004/02/10 16:59:49  tony
 * This is the initial load of OPICM
 *
 * Revision 1.10  2003/08/05 22:31:40  joan
 * fix null pointer
 *
 * Revision 1.9  2003/06/19 16:09:44  tony
 * 51298 -- limited capability when working in the past.
 * updated past date logic.
 *
 * Revision 1.8  2003/03/20 23:59:37  tony
 * column order moved to preferences.
 * preferences refined.
 * Change History updated.
 * Default Column Order Stubs added
 *
 * Revision 1.7  2003/03/20 18:12:31  tony
 * stickpin and bookmarking.
 *
 * Revision 1.6  2003/03/19 20:35:02  tony
 * addition of bookmarking logic
 *
 * Revision 1.5  2003/03/17 23:32:31  tony
 * accessibility update.
 *
 * Revision 1.4  2003/03/12 23:51:18  tony
 * accessibility and column order
 *
 * Revision 1.3  2003/03/11 00:33:27  tony
 * accessibility changes
 *
 * Revision 1.2  2003/03/07 21:40:49  tony
 * Accessibility update
 *
 * Revision 1.1.1.1  2003/03/03 18:03:52  tony
 * This is the initial load of OPICM
 *
 * Revision 1.12  2002/11/20 21:24:43  tony
 * fixed index out of range
 *
 * Revision 1.11  2002/11/07 16:58:16  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eobjects;
import com.elogin.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;
import javax.swing.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class EList extends EList2 {
	private static final long serialVersionUID = 1L;
	/**
     * data
     */
    private Vector data = new Vector();

	/**
     * eList
     * @author Anthony C. Liberto
     */
    public EList() {
		super();
		setListData(false);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		return;
	}

	/**
     * load
     * @param _table
     * @param _sort
     * @param _direction
     * @author Anthony C. Liberto
     */
    public void load(RowSelectableTable _table, boolean _sort, boolean _direction) {
        int ii = -1;
        EANFoundation[] rows = null;
        data.clear();
		ii = _table.getRowCount();

		rows = _table.getTableRowsAsArray();

		if (_sort) {
			Arrays.sort(rows,new EComparator(_direction));
		}

		for (int i=0;i<ii;++i) {
			data.add(_table.getRow(rows[i].getKey()));
		}
//sort		for (int i=0;i<ii;++i) {
//sort			data.add(_table.getRow(i));
//sort		}
		setListData(true);
		return;
	}

	/**
     * load
     * @param _shg
     * @author Anthony C. Liberto
     */
    public void load(SerialHistoryGroup _shg) {
		int ii = -1;
        int index = -1;
        SerialHistory[] sHistArray = null;
        data.clear();
		sHistArray = _shg.toArray();
		if (sHistArray == null) {
            return;
		}
		Arrays.sort(sHistArray,new EComparator(true));
		ii = sHistArray.length;
		for (int i=0;i<ii;++i) {
			if (!sHistArray[i].getAttributeCode().endsWith("CURRENT")) {
				data.add(sHistArray[i]);
			}
		}
		setListData(true);
		index = getFirstVisibleIndex();
		if (index >= 0) {
			setSelectionInterval(index,index);
		}
		return;
	}

	/**
     * setListData
     * @param _clear
     * @author Anthony C. Liberto
     */
    public void setListData(boolean _clear) {
		setListData(data);
		if (_clear) {
			clearSelection();
		}
		return;
	}

	/**
     * load
     * @param _flag
     * @author Anthony C. Liberto
     */
    public void load(MetaFlag[] _flag) {
		int ii = -1;
        int index = -1;
        data.clear();
//		Arrays.sort(_flag,new SerialHistoryComparator(true));
		ii = _flag.length;
		for (int i=0;i<ii;++i) {
			data.add(_flag[i]);
		}
		setListData(true);
		index = getFirstVisibleIndex();
		if (index >= 0) {
			setSelectionInterval(index,index);
		}
		return;
	}

	/**
     * clear
     * @author Anthony C. Liberto
     */
    public void clear() {
		data.clear();
		setListData(false);
		return;
	}

	/**
     * loadObject
     * @param _o
     * @param _i
     * @author Anthony C. Liberto
     */
    public void loadObject(Object _o, int _i) {
		data.insertElementAt(_o,_i);
		setListData(true);
		return;
	}

	/**
     * loadObject
     * @param _o
     * @author Anthony C. Liberto
     */
    public void loadObject(Object _o) {
		data.add(_o);
		setListData(true);
		return;
	}

	/**
     * getItemAt
     * @param _i
     * @return
     * @author Anthony C. Liberto
     */
    public Object getItemAt(int _i) {
		if (_i < 0 || _i >= getDataSize()) {
			return null;
		}
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
		return;
	}

	/**
     * contains
     * @param _o
     * @return
     * @author Anthony C. Liberto
     */
    public boolean contains(Object _o) {
		return data.contains(_o);
	}

	/**
     * @see javax.swing.JComponent#createToolTip()
     * @author Anthony C. Liberto
     */
    public JToolTip createToolTip() {
		EMToolTip tip = new EMToolTip();
		tip.setComponent(this);
		return tip;
	}

	/**
     * @see javax.swing.JComponent#getToolTipText(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public String getToolTipText(MouseEvent _me) {
		int row = locationToIndex(_me.getPoint());
		if (row > 0 && row < data.size()) {
			return data.get(row).toString();
		}
		return null;
	}

	/**
     * @see javax.swing.JComponent#getToolTipLocation(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public Point getToolTipLocation(MouseEvent _me) {
		Point pt = _me.getPoint();
		String s= (getToolTipText(_me));
		if(s == null || s.equals("")) {
            return null;
		}
		pt.translate(-1,-2);
		return pt;
	}

/*
 51298
 */
	/**
     * load
     * @param _ean
     * @param _title
     * @author Anthony C. Liberto
     */
    public void load(EANActionItem[] _ean, String _title) {
		int ii = -1;
        //boolean bPast = false;
        if (_ean == null) {
            return;
		}		//51639
		data.clear();
		ii = _ean.length;
		//bPast = eaccess().isPast();
		for (int i=0;i<ii;++i) {
			data.add(_ean[i]);
		}
		setListData(true);
		return;
	}

/*
 cr_6303
 */
	/**
     * load
     * @param _ps
     * @author Anthony C. Liberto
     */
    public void load(ProfileSet _ps) {
		int ii = 0;
        String curOp = null;
        data.clear();
		if (_ps != null) {
			Profile[] prof = _ps.toArray();
			EComparator eComp = new EComparator(true) {					//53825
				public Object getObject(Object _o, int _index) {		//53825
					if (_o instanceof Profile) {						//53825
						return ((Profile)_o).getOPName();				//53825
					} else {											//53825
						return _o;										//53825
					}													//53825
				}														//53825
			};															//53825
			Arrays.sort(prof,eComp);									//53825
			ii = prof.length;
			curOp = null;										//5ZKMGH
			for (int i=0;i<ii;++i) {
				if (curOp == null) {									//5ZKMGH
					data.add(new ProfileDisplay(prof[i]));
				} else if (!curOp.equals(prof[i].getOPName())) {		//5ZKMGH
					data.add(new ProfileDisplay(prof[i]));				//5ZKMGH
				}														//5ZKMGH
				curOp = prof[i].getOPName();							//5ZKMGH
			}
		}
		setListData(true);
		return;
	}

/*
 blue page Create

 */
	/**
     * load
     * @param _o
     * @author Anthony C. Liberto
     */
    public void load(Object[] _o) {
		int ii = 0;
        data.clear();
		if (_o != null) {
			EComparator eComp = new EComparator(true);
			Arrays.sort(_o,eComp);
			ii = _o.length;
			for (int i=0;i<ii;++i) {
				if (_o != null) {
					data.add(_o[i]);
				}
			}
		}
		setListData(true);
		return;
	}
}
