/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/12/05
 * @author Anthony C. Liberto
 *
 * $Log: SortPanel.java,v $
 * Revision 1.5  2012/04/05 17:35:51  wendy
 * jre142 and win7 changes
 *
 * Revision 1.4  2009/04/15 20:21:19  wendy
 * improve sort performance
 *
 * Revision 1.3  2009/04/08 20:01:21  wendy
 * Sort needs more than column name to find the attribute
 *
 * Revision 1.2  2008/01/30 16:26:53  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:43:25  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:11  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:44  tony
 * This is the initial load of OPICM
 *
 * Revision 1.9  2005/09/08 17:59:00  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.8  2005/04/05 21:33:17  tony
 * fixed typo
 *
 * Revision 1.7  2005/04/05 17:29:50  tony
 * MN_23318121
 *
 * Revision 1.6  2005/02/04 15:22:08  tony
 * JTest Format Third Pass
 *
 * Revision 1.5  2005/02/02 17:30:21  tony
 * JTest Second Pass
 *
 * Revision 1.4  2005/01/27 23:18:20  tony
 * JTest Formatting
 *
 * Revision 1.3  2005/01/10 23:47:48  tony
 * //USRO-R-JSTT-68HRZL
 *
 * Revision 1.2  2004/12/09 22:07:02  tony
 * MN_21913928
 *
 * Revision 1.1.1.1  2004/02/10 16:59:29  tony
 * This is the initial load of OPICM
 *
 * Revision 1.12  2004/01/16 16:06:34  tony
 * 20040116
 * enhanced logic to prevent the ascending
 * and descending radio buttons from resetting
 * after every sort.
 *
 * Revision 1.11  2003/11/13 23:16:16  tony
 * accessibility
 *
 * Revision 1.10  2003/11/07 17:05:51  tony
 * 52927
 *
 * Revision 1.9  2003/10/29 00:21:16  tony
 * removed System.out. statements.
 *
 * Revision 1.8  2003/07/14 17:39:45  tony
 * 51445
 *
 * Revision 1.7  2003/07/10 18:00:55  tony
 * 51434
 *
 * Revision 1.6  2003/05/28 16:27:40  tony
 * 50924
 *
 * Revision 1.5  2003/05/22 19:38:25  tony
 * 50882
 *
 * Revision 1.4  2003/05/22 16:23:12  tony
 * 50874 -- filter, find, and sort adjust the object they
 * function on dynamically.
 *
 * Revision 1.3  2003/03/13 21:17:02  tony
 * accessibility enhancements.
 *
 * Revision 1.2  2003/03/13 18:38:44  tony
 * accessibility and column Order.
 *
 * Revision 1.1.1.1  2003/03/03 18:03:44  tony
 * This is the initial load of OPICM
 *
 *
 */

package com.ibm.eannounce.dialogpanels;
import com.elogin.*;
import com.ibm.eannounce.eforms.table.*;
import com.ibm.eannounce.eobjects.*;
import java.awt.*;
import java.util.Arrays;
import javax.swing.*;
import COM.ibm.eannounce.objects.rsTableComparator;

//52927 public class sortPanel extends accessibleDialogPanel implements FocusListener {
/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class SortPanel extends AccessibleDialogPanel  {				//52927
	private static final long serialVersionUID = 1L;
	//50874	private table m_table = null;
	private String[] m_tObject = null;
	private Object m_srchObject = null;										//51434
	private final int maxSort = 4;											//var_sort_criteria


	//label
	private ELabel[] m_lblHeader = new ELabel[maxSort];					//var_sort_criteria

	//combo box
	private EFlagBox[] m_cmbSort = new EFlagBox[maxSort];				//var_sort_criteria

	// buttons

	private EButton	m_btnRun = new EButton(getString("ok"));			//var_sort_criteria
	private EButton	m_btnCancel = new EButton(getString("cncl"));		//var_sort_criteria
	private EButton m_btnReset = new EButton(getString("clr"));			//51445

	private ERadioButton[] m_rbAsc = new ERadioButton[maxSort];			//var_sort_criteria
	private ERadioButton[] m_rbDes = new ERadioButton[maxSort];			//var_sort_criteria
	private ButtonGroup[] m_btnGroup = new ButtonGroup[maxSort];		//var_sort_criteria

	private EPanel m_pSel = new EPanel(new GridLayout(maxSort,1,5,5));	//var_sort_criteria
//51445	private ePanel m_pBtn = new ePanel(new GridLayout(1,2,5,5));
	private EPanel m_pBtn = new EPanel(new GridLayout(1,3,5,5));		//51445
	private EPanel[] m_pVis = new EPanel[maxSort];						//var_sort_criteria
	private EPanel[] m_pRdo = new EPanel[maxSort];						//var_sort_criteria

	private Object[] name = null;										//52927
	private boolean bProcess = false;									//52927

	/**
     * sortPanel
     * @author Anthony C. Liberto
     */
    public SortPanel() {
		super(new BorderLayout());
		init();
	}

	/**
     * init
     * @author Anthony C. Liberto
     */
    public void init() {
		String sAsc = getString("srtA");
		String sDes = getString("srtD");
		String sPrimary = getString("sortBy");
		String sSecondary = getString("thenBy");
		Dimension d = new Dimension(150,25);
		for (int i=0;i<maxSort;++i) {
			if (i == 0) {
				m_lblHeader[i] = new ELabel(sPrimary);
			} else {
				m_lblHeader[i] = new ELabel(sSecondary);
			}
			m_cmbSort[i] = new EFlagBox();
			m_cmbSort[i].setFont(this.getFont());
			m_lblHeader[i].setLabelFor(m_cmbSort[i]);							//accessible
			m_cmbSort[i].setSize(d);
			m_cmbSort[i].setPreferredSize(d);
			if (i < (maxSort -1)) {
				m_cmbSort[i].addActionListener(this);
				m_cmbSort[i].setActionCommand(Integer.toString(i));
//52927				m_cmbSort[i].addFocusListener(this);
			}
			m_btnGroup[i] = new ButtonGroup();
			m_rbAsc[i] = new ERadioButton(sAsc,true);
			m_rbDes[i] = new ERadioButton(sDes,false);
			m_rbAsc[i].getAccessibleContext().setAccessibleDescription(sAsc);	//accessible
			m_rbDes[i].getAccessibleContext().setAccessibleDescription(sDes);	//accessible
			m_btnGroup[i].add(m_rbAsc[i]);
			m_btnGroup[i].add(m_rbDes[i]);
			m_pVis[i] = new EPanel(new BorderLayout());
			m_pRdo[i] = new EPanel(new BorderLayout());
			m_pVis[i].add("West", m_cmbSort[i]);
			m_pVis[i].add("North", m_lblHeader[i]);
			m_pVis[i].add("East", m_pRdo[i]);
			m_pRdo[i].add("West", m_rbAsc[i]);
			m_pRdo[i].add("East", m_rbDes[i]);
			m_pSel.add(m_pVis[i]);
		}
		m_pBtn.add(m_btnRun);
		m_pBtn.add(m_btnReset);							//51445
		m_pBtn.add(m_btnCancel);
		m_btnRun.setActionCommand("run");
		m_btnReset.setActionCommand("reset");			//51445
		m_btnCancel.setActionCommand("exit");
		m_btnRun.addActionListener(this);
		m_btnReset.addActionListener(this);				//51445
		m_btnCancel.addActionListener(this);

		add("North", m_pSel);
		add("South", m_pBtn);
        setMnemonicsAndTips();
		setSize(getPreferredSize());
		accessibleize();														//accessible
	}

	private void setEnabled(int _i, boolean _b) {
		boolean b = _b;
		if (_b) {
			b = (m_cmbSort[_i].getItemCount() > 0);
		}
		if (_i > 0 && _i < maxSort) {
			m_lblHeader[_i].setEnabled(b);
			m_cmbSort[_i].setEnabled(b);
			m_rbAsc[_i].setEnabled(b);
			m_rbAsc[_i].setSelected(true);				//51445
			m_rbDes[_i].setEnabled(b);
		}
	}

	private void removeAllItems() {
		for (int i=0;i<maxSort;++i) {
			if (m_cmbSort[i] != null) {
				m_cmbSort[i].removeAllItems();
			}
		}
		for (int i=0;i<maxSort;++i) {				//acl_20040116
			if (m_rbAsc[i] != null) {				//acl_20040116
				m_rbAsc[i].setSelected(true);		//acl_20040116
			}										//acl_20040116
		}											//acl_20040116
	}

	private void setSelectedItem(Object _o) {
		for (int i=0;i<maxSort;++i) {
			m_cmbSort[i].setSelectedItem(_o);
		}
	}

	/*
	 MN_23318121
	 */
	private int[] getSortColumns(ETable _tbl) {
		int[] out = new int[maxSort];
		if (_tbl != null) {
			for (int i=0;i<maxSort;++i) {
				Object o = m_cmbSort[i].getSelectedItem();
				if (o != null) {
					out[i] = ((SortColumn)o).getColumnIndex();//_tbl.getIndexFromHeader(o.toString());
				}else{
					// allow first sort column to be valid, no-op the rest for performance 
					if (i>0){
						out[i]=rsTableComparator.NO_OP_ID;
					}
				}
			}
		}
		return out;
	}

	private void sort() {
		ETable tbl = getTable();
		int[] iArray = getSortColumns(tbl);
		boolean[] dArray = new boolean[maxSort];

        for (int i=0;i<maxSort;++i) {
			dArray[i] = m_rbAsc[i].isSelected();
		}
//50874		m_table.sort(iArray, dArray);
		if (tbl != null) {					//50874
			tbl.sort(iArray,dArray);		//50874
		}									//50874
		disposeDialog();
	}

	/**
     * actionPerformed
     * @author Anthony C. Liberto
     * @param _action
     */
    public void actionPerformed(String _action) {
		if (_action.equals("exit")) {
			disposeDialog();
		} else if (_action.equals("run")) {
			sort();
		} else if (_action.equals("reset")) {		//51445
			reset();								//51445
		} else {
			process(_action);						
		}
		setBusy(false);
	}

/*
 52927
	public void focusLost(FocusEvent _fe) {
		Object source = _fe.getSource();
		int i = (getIndex((eComboBox)source));
		if (i >= 0 && i < maxSort) {
			Object o = m_cmbSort[i].getSelectedItem();
			if (o != null) {
				int next = i + 1;
				if (next < maxSort) {
					removeItem(next,o);
				}
			}
		}
		return;
	}
	public void focusGained(FocusEvent _fe) {}
*/

	private int getIndex(String _s) {
		return Routines.toInt(_s);
	}

	/**
     * getIndex
     *
     * @author Anthony C. Liberto
     * @param _box
     * @return
     */
    protected int getIndex(EComboBox _box) {
		for (int i=0;i<maxSort;++i) {
			if (m_cmbSort[i] == _box) {
				return i;
			}
		}
		return -1;
	}

	/**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
		for (int i=0;i<maxSort;++i) {
			m_lblHeader[i].removeAll();
			m_cmbSort[i].removeAllItems();
			if (i < (maxSort -1)) {
				m_cmbSort[i].removeActionListener(this);
//52927				m_cmbSort[i].removeFocusListener(this);
			}
			m_pVis[i].removeAll();
			m_pRdo[i].removeAll();
			m_btnGroup[i].remove(m_rbAsc[i]);
			m_btnGroup[i].remove(m_rbDes[i]);
			m_btnGroup[i] = null;
			m_rbAsc[i] = null;
			m_rbDes[i] = null;

			m_pVis[i] = null;
			m_pRdo[i] = null;

			m_cmbSort[i] = null;
			m_lblHeader[i] = null;
		}

		m_btnCancel.removeActionListener(this);
		m_btnCancel.removeAll();
		m_btnCancel = null;

		m_btnReset.removeActionListener(this);					//51445
		m_btnReset.removeAll();									//51445
		m_btnReset = null;										//51445

		m_btnRun.removeActionListener(this);
		m_btnRun.removeAll();
		m_btnRun = null;

		m_pSel.removeAll();
		m_pSel = null;
		m_pBtn.removeAll();
		m_pBtn = null;
		m_srchObject = null;										//51434

		name = null;											//52927

		removeAll();
		removeNotify();
	}
/*
 50874
	public table getTable() {
		return m_table;
	}
*/
	/**
     * setMnemonicsAndTips
     * @author Anthony C. Liberto
     */
    public void setMnemonicsAndTips() {
		m_btnRun.setMnemonic(getChar("ok-s"));
		m_btnCancel.setMnemonic(getChar("cncl-s"));
		m_btnReset.setMnemonic(getChar("clr-s"));				//51445
	}

	/*private int getObjectIndex(Object _o) {
		if (_o != null) {
			String s = _o.toString();
			for (int i=0; i < m_tObject.length; i++) {
				if (m_tObject[i].toString().equals(s)) {
                    System.out.println(s + " is column: " + i);
					return i;
				}
			}
		}
		return -1;
	}*/

	/**
     * setTitle
     * @author Anthony C. Liberto
     * @param _title
     */
    public void setTitle(String _title) {}
	/**
     * getTitle
     * @author Anthony C. Liberto
     * @return String
     */
    public String getTitle() {
		return getString("sort.panel");
	}
/*
 50874
*/
	/**
     * initTable
     * @param _tbl
     * @author Anthony C. Liberto
     */
    private void initTable(ETable _tbl) {
		removeAllItems();

		if (_tbl == null) {
			packDialog();
			return;
		}

		name = _tbl.getVisibleColumnNames();
		if (name != null) {														//USRO-R-JSTT-68HRZL
			int ii = name.length;												//USRO-R-JSTT-68HRZL
			m_tObject = new String[ii];											//USRO-R-JSTT-68HRZL
			for (int i=0;i<ii;++i) {											//USRO-R-JSTT-68HRZL
				m_tObject[i] = name[i].toString();					//USRO-R-JSTT-68HRZL
			}																	//USRO-R-JSTT-68HRZL
		}																		//USRO-R-JSTT-68HRZL

		Arrays.sort(name,new EComparator(true));

//52927		addToComboBox(name);
		addToComboBox(0,name);		//52927

//52927		setSelectedItem(null);

		for (int i=1;i<maxSort;++i) {
			setEnabled(i,false);
		}

		packDialog();
	}

	/**
     * getTable
     * @return
     * @author Anthony C. Liberto
     */
    private ETable getTable() {
		Object o = null;						//50924
		if (id != null) {						//50924
			o = id.getSearchObject();			//50924
		} else {								//50924
			o = eaccess().getSearchObject();
		}										//50924

		if (m_srchObject == o) {				//51434
			return (ETable)o;					//51434
		}										//51434
		m_srchObject = o;						//51434

		if (o instanceof ETable) {
			initTable((ETable)o);
			return (ETable)o;
		}
		initTable(null);
		return null;
	}

	/**
     * activateMe
     * @author Anthony C. Liberto
     */
    public void activateMe() {
		getTable();
	}
/*
 50882
*/
	/**
     * showMe
     * @author Anthony C. Liberto
     */
    public void showMe() {
		if (m_cmbSort[0] != null) {
			m_cmbSort[0].requestFocus();
		}
	}

/*
 51445
 */
	private void reset() {
		setSelectedItem(null);
		for (int i=1;i<maxSort;++i) {
			setEnabled(i,false);
		}
	}

/*
 52927
 */
	private synchronized void addToComboBox(int _i, Object[] _o) {
		Object[] oSel = null;
        int ii = -1;
        if (bProcess) {
			return;
		}
		bProcess = true;
		if (_i > 0) {
			oSel = getSelectedItems(_i);
		}
		if (_i >= 0 && _i < maxSort) {
			m_cmbSort[_i].removeAllItems();
			ii = _o.length;
			for (int i=0;i<ii;++i) {
				m_cmbSort[_i].addItem(_o[i]);
			}
			if (oSel != null) {
				for (int i=0;i<oSel.length;++i) {
					m_cmbSort[_i].removeItem(oSel[i]);
				}
			}
			for (int i=_i+1;i<maxSort;++i) {
				m_cmbSort[i].setSelectedItem(null);
				setEnabled(i,false);
			}
			m_cmbSort[_i].setSelectedItem(null);
			setEnabled(_i,true);
		}
		bProcess = false;
		return;
	}

	private void process(String _action) {
		int i = getIndex(_action);
		if (i >= 0 && i < maxSort) {
			int next = i + 1;
			addToComboBox(next,name);
		}
		return;
	}
	private Object[] getSelectedItems(int _ii) {
		if (_ii >= 0 && _ii < m_cmbSort.length) {
			Object[] out = new Object[_ii];
			for (int i=0;i<_ii;++i) {
				out[i] = getSelectedItem(i);
			}
			return out;
		}
		return null;
	}

	private Object getSelectedItem(int _i) {
		if (_i >= 0 && _i < m_cmbSort.length) {
			return m_cmbSort[_i].getSelectedItem();
		}
		return null;
	}

/*
 accessibility
 */
	private void accessibleize() {
		m_btnRun.getAccessibleContext().setAccessibleDescription(getString("ok"));
		m_btnCancel.getAccessibleContext().setAccessibleDescription(getString("cncl"));
		m_btnReset.getAccessibleContext().setAccessibleDescription(getString("clr"));
	}
}
