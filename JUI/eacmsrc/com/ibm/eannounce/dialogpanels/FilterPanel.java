/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/12/05
 * @author Anthony C. Liberto
 *
 * $Log: FilterPanel.java,v $
 * Revision 1.3  2012/04/05 17:35:13  wendy
 * jre142 and win7 changes
 *
 * Revision 1.2  2008/01/30 16:26:53  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:43:24  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:10  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:43  tony
 * This is the initial load of OPICM
 *
 * Revision 1.12  2005/09/08 17:59:00  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.11  2005/02/18 23:10:09  tony
 * USRO-R-TMAY-69QQSU
 *
 * Revision 1.10  2005/02/04 15:22:08  tony
 * JTest Format Third Pass
 *
 * Revision 1.9  2005/02/03 22:52:29  tony
 * collumn filter
 *
 * Revision 1.8  2005/02/03 16:22:33  tony
 * column filtering
 *
 * Revision 1.7  2005/02/02 17:30:20  tony
 * JTest Second Pass
 *
 * Revision 1.6  2005/02/01 22:26:40  tony
 * ColumnFilter
 *
 * Revision 1.5  2005/01/27 23:18:20  tony
 * JTest Formatting
 *
 * Revision 1.4  2004/10/22 22:12:33  tony
 * TIR_65Y3M
 *
 * Revision 1.3  2004/08/11 21:24:24  tony
 * 5ZKL3K
 *
 * Revision 1.2  2004/07/29 22:37:14  tony
 * MN_20200715
 *
 * Revision 1.1.1.1  2004/02/10 16:59:28  tony
 * This is the initial load of OPICM
 *
 * Revision 1.17  2003/12/30 21:39:10  tony
 * 53482
 *
 * Revision 1.16  2003/11/13 23:16:16  tony
 * accessibility
 *
 * Revision 1.15  2003/10/29 00:21:16  tony
 * removed System.out. statements.
 *
 * Revision 1.14  2003/08/14 18:05:18  tony
 * 51761
 *
 * Revision 1.13  2003/08/13 21:01:34  tony
 * 51760
 *
 * Revision 1.12  2003/07/22 15:42:27  tony
 * removed menu
 *
 * Revision 1.11  2003/05/28 18:15:24  tony
 * 50903
 *
 * Revision 1.10  2003/05/28 16:27:40  tony
 * 50924
 *
 * Revision 1.9  2003/05/28 14:41:08  tony
 * 50945
 *
 * Revision 1.8  2003/05/23 18:25:46  tony
 * 50874b
 *
 * Revision 1.7  2003/05/22 18:10:34  tony
 * 50873
 *
 * Revision 1.6  2003/05/22 16:23:12  tony
 * 50874 -- filter, find, and sort adjust the object they
 * function on dynamically.
 *
 * Revision 1.5  2003/04/21 20:03:12  tony
 * enhanced scroll logic to improve flag editor.
 *
 * Revision 1.4  2003/04/03 18:51:47  tony
 * adjusted logic to display individualized icon
 * for each frameDialog/tab.
 *
 * Revision 1.3  2003/03/13 21:17:01  tony
 * accessibility enhancements.
 *
 * Revision 1.2  2003/03/13 18:38:43  tony
 * accessibility and column Order.
 *
 * Revision 1.1.1.1  2003/03/03 18:03:43  tony
 * This is the initial load of OPICM
 *
 *
 */
package com.ibm.eannounce.dialogpanels;
import com.elogin.*;
import com.ibm.eannounce.eforms.table.*;
import com.ibm.eannounce.eobjects.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.Profile;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class FilterPanel extends AccessibleDialogPanel {
	private static final long serialVersionUID = 1L;
	//50874	private table m_table = null;

    //string values

    //checkbox
    private ECheckBox m_chkCase = new ECheckBox(getString("case"));

    private EButton m_btnAdd = new EButton(getString("addF"));
    private EButton m_btnR = new EButton(getString("rmvS"));
    private EButton m_btnRA = new EButton(getString("rmvA"));

    // buttons
    private EButton m_btnReset = new EButton(getString("rstg"));
    private EButton m_btnRun = new EButton(getString("runf"));
    private EButton m_btnCancel = new EButton(getString("clse"));

    private FilterTable criTable = new FilterTable();
    private EScrollPane jsp = new EScrollPane(criTable);
    //	private Vector m_fiVector = new Vector();

    //	private EntityGroup m_eg = null;
    //50873	private serialPref m_oPref = null;			//23370
    //50945	private static final String m_strKey = "MFILTER";
    //50874	private String m_strTableKey = null;

    /**
     * filterPanel
     * @author Anthony C. Liberto
     */
    public FilterPanel() {
        super(new BorderLayout());
        init();
        return;
    }

    /**
     * init
     * @author Anthony C. Liberto
     */
    public void init() {
        GridBagLayout gridbag = new GridBagLayout();
        //		BorderLayout ba = new BorderLayout();
        GridBagConstraints c = new GridBagConstraints();
        EPanel pNorth = new EPanel();
        EPanel pSouth = new EPanel();
        int vspd = -1;
        int hspd = -1;

        pNorth.setLayout(gridbag);
        pSouth.setLayout(gridbag);
        c.fill = GridBagConstraints.BOTH;

        //add check case
        c.weightx = 0.0;
        c.gridwidth = GridBagConstraints.REMAINDER; //end row
        gridbag.setConstraints(m_chkCase, c);
        pNorth.add(m_chkCase);

        //add add, remove, removeAll
        c.weightx = 0.0;
        c.gridwidth = 3;
        gridbag.setConstraints(m_btnAdd, c);
        pNorth.add(m_btnAdd);

        c.gridwidth = GridBagConstraints.RELATIVE; //next-to-last in row
        gridbag.setConstraints(m_btnR, c);
        pNorth.add(m_btnR);

        c.gridwidth = GridBagConstraints.REMAINDER; //end row
        gridbag.setConstraints(m_btnRA, c);
        pNorth.add(m_btnRA);

        //add m_criteria
        vspd = Integer.valueOf(getString("colv")).intValue();
        hspd = Integer.valueOf(getString("colh")).intValue();
        jsp.getVerticalScrollBar().setUnitIncrement(vspd);
        jsp.getHorizontalScrollBar().setUnitIncrement(hspd);
        jsp.setSize(new Dimension(400, 300));
        jsp.setPreferredSize(new Dimension(400, 300));

        //add reset, run, cancel
        c.weightx = 0.0;
        c.gridwidth = 3;
        gridbag.setConstraints(m_btnReset, c);
        pSouth.add(m_btnReset);

        c.gridwidth = GridBagConstraints.RELATIVE; //next-to-last in row
        gridbag.setConstraints(m_btnRun, c);
        pSouth.add(m_btnRun);

        c.gridwidth = GridBagConstraints.REMAINDER; //end row
        gridbag.setConstraints(m_btnCancel, c);
        pSouth.add(m_btnCancel);

        // add listeners
        m_btnAdd.addActionListener(this);
        m_btnR.addActionListener(this);
        m_btnRA.addActionListener(this);
        m_btnCancel.addActionListener(this);
        m_btnReset.addActionListener(this);
        m_btnRun.addActionListener(this);
        m_chkCase.addActionListener(this);

        m_btnAdd.setActionCommand("add");
        m_btnR.setActionCommand("remove");
        m_btnRA.setActionCommand("removeAll");
        m_btnCancel.setActionCommand("exit");
        m_btnReset.setActionCommand("reset");
        m_btnRun.setActionCommand("run");
        m_chkCase.setActionCommand("case");

        add("North", pNorth);
        add("Center", jsp);
        add("South", pSouth);

        setMnemonicsAndTips();
        m_chkCase.setOpaque(false); //XP
        accessibleize();
        return;
    }

    private void filter() {
        if(saveEdit()){
        ETable tbl = getTable(); //50874
        if (tbl != null) { //50874
            tbl.filter(); //50874
            saveFilterGroup(tbl);

        } //50874
        //50874		m_table.filter();
        }
    }

    private boolean saveEdit() {
        return criTable.saveCurrentEdit();
    }

    private void showHiddenRows() {
        ETable tbl = getTable(); //50874
        if (tbl != null) { //50874
            tbl.resetFilter(); //50874
        } //50874
        //50874		m_table.resetFilter();
        return;
    }

    /*
    50874
    	public void setObject(Object _o) {										//22377
    		if (_o == null) {													//22377
    			m_table = null;													//22377
    			setRemoveEnabled(false);
    		} else if (_o instanceof table) {
    			m_table = (table)_o;													//22377
    			setFilterGroup(getFilterGroup());
    			setRemoveEnabled();
    			if (m_table instanceof usedTable) {								//22377
    				m_strTableKey = ((usedTable)m_table).getUIPrefKey();		//22377
    			} else if (m_table instanceof lockTable) {						//22377
    				m_strTableKey = ((lockTable)m_table).getUIPrefKey();		//22377
    			} else {														//22377
    				m_strTableKey = m_table.getUIPrefKey();						//22377
    			}																//22377

    		}														//22377
    		return;
    	}
    */
    private void setFilterGroup(FilterGroup _fGroup) {
        if (criTable != null) {
            criTable.refreshTable(_fGroup);
        }
        return;
    }

    private void setColFilterGroup(FilterGroup _fGroup) { //filter_col
        setFilterGroup(_fGroup); //filter_col
        return; //filter_col
    } //filter_col
    /*
     50874
    	public table getTable() {
    		return m_table;
    	}
    */
    /**
     * getFilterGroup
     * @return
     * @author Anthony C. Liberto
     */
    public FilterGroup getFilterGroup() {
        ETable tbl = getTable(); //50874
        if (tbl != null) { //50874
            if (tbl.isPivot()) { //filter_col
                return tbl.getColFilterGroup(); //filter_col
            } else { //filter_col
                return tbl.getFilterGroup(); //50903
            } //filter_col
        } //50874
        return null; //50874
        //50874		return m_table.getFilterGroup();
    }

    /**
     * getCriteriaFilterGroup
     * @return
     * @author Anthony C. Liberto
     */
    public FilterGroup getCriteriaFilterGroup() {
        return criTable.getFilterGroup();
    }

    /**
     * setMnemonicsAndTips
     * @author Anthony C. Liberto
     */
    public void setMnemonicsAndTips() {
        m_btnReset.setMnemonic(getChar("rstg-s"));
        m_btnRun.setMnemonic(getChar("runf-s"));
        m_btnCancel.setMnemonic(getChar("clse-s"));
        m_chkCase.setMnemonic(getChar("case-s"));
        m_btnAdd.setMnemonic(getChar("addF-s"));
        m_btnR.setMnemonic(getChar("rmvS-s"));
        m_btnRA.setMnemonic(getChar("rmvA-s"));

        m_btnReset.setToolTipText(getString("rstg-t"));
        m_btnRun.setToolTipText(getString("runf-t"));
        m_btnCancel.setToolTipText(getString("clse-t"));
        m_chkCase.setToolTipText(getString("case-t"));

        //		String sc2f = getString("sc2f");
        //		String sfm = getString("sfm");
        //		String ev2m = getString("ev2m");
        return;
    }

    /**
     * Listener
     *
     * @param _action
     */
    public void actionPerformed(String _action) {
        appendLog("filterPanel.actionPerformed(" + _action + ")");
        setModalBusy(true); //TIR_65Y3M
        if (_action.equals("exit")) {
            disposeDialog();
        } else if (_action.equals("reset")) {
            showHiddenRows();
        } else if (_action.equals("run")) {
            filter();
        } else if (_action.equals("add")) {
            addToCriteria();
            setRemoveEnabled();
        } else if (_action.equals("remove")) {
            removeCriteria(false);
            setRemoveEnabled();
        } else if (_action.equals("removeAll")) {
            removeCriteria(true);
            setRemoveEnabled();
        } else if (_action.equals("case")) {
            FilterGroup fg = getFilterGroup(); //50903
            if (fg != null) { //50903
                fg.setCaseSensitive(m_chkCase.isSelected()); //50903
            } //50903
            //50903			getFilterGroup().setCaseSensitive(m_chkCase.isSelected());
        } //19939
        //TIR_65Y3M		setBusy(false);
        setModalBusy(false); //TIR_65Y3M
        return;
    }

    private void setRemoveEnabled() {
        setRemoveEnabled(criTable.getRowCount());
        return;
    }

    private void setRemoveEnabled(int _rows) {
        setRemoveEnabled(_rows > 0);
        return;
    }

    private void setRemoveEnabled(boolean _b) {
        m_btnR.setEnabled(_b);
        m_btnRA.setEnabled(_b);
        return;
    }

    /**
     * toArray
     *
     * @author Anthony C. Liberto
     * @param _v
     * @return
     */
    protected FilterItem[] toArray(Vector _v) {
        int l = _v.size();
        FilterItem[] fiArray = new FilterItem[l];

        for (int i = 0; i < l; i++) {
            Object o = _v.elementAt(i);
            if (o instanceof FilterItem) {
                fiArray[i] = (FilterItem) o;
            }
        }
        return fiArray;
    }

    private void addToCriteria() {
        if(saveEdit()){
        criTable.addRow();
        }else{
        	Toolkit.getDefaultToolkit().beep();
        }
        return;
    }

    /**
     * removeSelected
     *
     * @author Anthony C. Liberto
     * @param _v
     */
    protected void removeSelected(Vector _v) {
        for (int i = 0; i < _v.size(); i++) {
            FilterItem fi = (FilterItem) _v.elementAt(i);
            if (fi.isSelected()) {
                _v.removeElementAt(i);
                removeSelected(_v);
                break;
            }
        }
    }

    private void removeCriteria(boolean _all) {
        if (_all) {
            criTable.removeAll();
        } else {
            criTable.remove();
        }
 
        saveFilterGroup(getTable()); // update filtergroup with any removals
    }

    /**
     * disposeDialog
     * @author Anthony C. Liberto
     */
    public void disposeDialog() {
        m_chkCase.setSelected(false);
        //50874		removeCriteria(true);
        super.disposeDialog();
        return;
    }

    /**
     * windowClosing
     * @param e
     * @author Anthony C. Liberto
     */
    public void windowClosing(WindowEvent e) {
        disposeDialog();
        return;
    }

    /*
     50873
    	public void setOPICMUIPreferences(serialPref _sP) {	//23370
    		m_oPref = _sP;
    	}
    */
    /**
     * @author Anthony C. Liberto
     */
    public void dereference() {
        m_btnAdd.removeActionListener(this);
        m_btnR.removeActionListener(this);
        m_btnRA.removeActionListener(this);
        m_chkCase.removeActionListener(this); //19939
        if (criTable != null) {
            criTable.dereference();
            criTable = null;
        }
        if (jsp != null) {
            jsp.dereference();
            jsp = null;
        }
        return;
    }

    /**
     * setTitle
     * @author Anthony C. Liberto
     * @param _title
     */
    public void setTitle(String _title) {
    }
    /**
     * getTitle
     * @author Anthony C. Liberto
     * @return String
     */
    public String getTitle() {
        return getString("filter.panel");
    }

    /**
     * getIconName
     * @author Anthony C. Liberto
     * @return String
     */
    public String getIconName() {
        return "fltr.gif";
    }

    /*
     50874
    */
    /**
     * initTable
     * @param _tbl
     * @author Anthony C. Liberto
     */
    public void initTable(ETable _tbl) {
        if (_tbl == null) {
            setRemoveEnabled(false);
            return;
        }
        setRemoveEnabled();
        return;
    }

    /**
     * getUIPrefKey
     *
     * @author Anthony C. Liberto
     * @return
     */
    protected String getUIPrefKey() {
        ETable tbl = getTable();
        if (tbl != null) {
            return tbl.getUIPrefKey();
        }
        return null;
    }

    /**
     * getTable
     * @return
     * @author Anthony C. Liberto
     */
    public ETable getTable() {
        Object o = null; //50924
        if (id != null) { //50924
            o = id.getSearchObject(); //50924
        } else { //50924
            o = eaccess().getSearchObject();
        } //50924
        if (o instanceof ETable) {
            initTable((ETable) o);
            return (ETable) o;
        }
        initTable(null);
        return null;
    }

    //53482	private void setFilterGroup(table _tbl) {
    private void setFilterGroup(ETable _tbl, boolean _filter) { //53482
        if (_tbl != null) {
            FilterGroup fGroup = getSavedFilterGroup(_tbl); //50873
            if (fGroup != null) { //50873
                if (_tbl instanceof BookmarkTable) {
                    fGroup.refresh(((BookmarkTable) _tbl).getBookmarkTable());
                } else if (!_tbl.isPivot()) {
                    fGroup.refresh(_tbl.getRowSelectableTable()); //51761
                }
                //53482				_tbl.setFilterGroup(fGroup);					//50874b
                if (_tbl.isPivot()) { //filter_col
                    _tbl.setColFilterGroup(fGroup, _filter); //filter_col
                    setColFilterGroup(fGroup); //filter_col
                } else { //filter_col
                    _tbl.setFilterGroup(fGroup, _filter); //53482
                    setFilterGroup(fGroup); //50873
                } //filter_col
            } else { //50873
                if (_tbl.isPivot()) { //filter_col
                    setColFilterGroup(_tbl.getColFilterGroup()); //filter_col
                } else { //filter_col
                    setFilterGroup(_tbl.getFilterGroup());
                } //filter_col
            } //50873
        }
        return;
    }

    /**
     * activateMe
     * @author Anthony C. Liberto
     */
    public void activateMe() {
        //53482		setFilterGroup(getTable());
        setFilterGroup(getTable(), false); //53482
        setRemoveEnabled(); //51760
        return;
    }

    /*
     50873
    */
    private void saveFilterGroup(ETable _tbl) {
        if (_tbl != null) {
            String key = getTableKey(_tbl); //50945
            FilterGroup fg = null;
/*
 if we are a pivot table don't remember
 me.  The options are not static
 so i get confused
 USRO-R-TMAY-69QQSU
 */
            if (_tbl.isPivot()) {
				return;				//USRO-R-TMAY-69QQSU
//USRO-R-TMAY-69QQSU                fg = _tbl.getColFilterGroup();
            } else {
                fg = _tbl.getFilterGroup();
            }
            if (fg != null) {
                eaccess().setPrefObject(key, fg);
            }
        }
        return;
    }

    private FilterGroup getSavedFilterGroup(ETable _tbl) {
        String key = null;
        Object o = null;
        if (_tbl != null) {
/*
 if we are a pivot table don't remember
 me.  The options are not static
 so i get confused by presenting irrelevent saved information
 USRO-R-TMAY-69QQSU
 */
            if (_tbl.isPivot()) {			//USRO-R-TMAY-69QQSU
            	return null;				//USRO-R-TMAY-69QQSU
			}								//USRO-R-TMAY-69QQSU
            key = getTableKey(_tbl); //50945
            o = eaccess().getPrefObject(key);
            if (o instanceof FilterGroup) {
                FilterGroup tmp = (FilterGroup) o; //MN_20200715
                if (tmp != null) { //MN_20200715
                    FilterGroup out = null; //MN_20200715
                    try { //MN_20200715
                        out = tmp.copy(); //MN_20200715
                    } catch (Exception _ex) { //MN_20200715
                        _ex.printStackTrace(); //MN_20200715
                    } //MN_20200715
                    if (out != null) { //MN_20200715
                        return out; //MN_20200715
                    } //MN_20200715
                } //MN_20200715
                //MN_20200715				return (FilterGroup)o;
            }
        }
        return null;
    }
    /*
     50945
    */
    private String getTableKey(ETable _tbl) {
        if (_tbl != null) {
            if (_tbl.isPivot()) {
                return getConstant() + _tbl.getUIPrefKey() + "_pivot";
            } else {
                return getConstant() + _tbl.getUIPrefKey();
            }
        }
        return getConstant();
    }

    private String getConstant() {
        Profile prof = getActiveProfile();
        if (prof != null) {
            return prof.getEnterprise() + "_" + prof.getOPWGID() + "_MFILTER_";
        }
        return "MFILTER_";
    }

    /*
     acl_20030722
     */
    /**
     * getMenuBar
     * @author Anthony C. Liberto
     * @return JMenuBar
     */
    public JMenuBar getMenuBar() {
        return null;
    }
    /**
     * createMenu
     * @author Anthony C. Liberto
     */
    protected void createMenu() {
    }
    /**
     * removeMenu
     * @author Anthony C. Liberto
     */
    protected void removeMenu() {
    }

    /*
     accessibility
     */
    private void accessibleize() {
        m_chkCase.getAccessibleContext().setAccessibleDescription(getString("case"));
        m_btnAdd.getAccessibleContext().setAccessibleDescription(getString("addF"));
        m_btnR.getAccessibleContext().setAccessibleDescription(getString("rmvS"));
        m_btnRA.getAccessibleContext().setAccessibleDescription(getString("rmvA"));
        m_btnReset.getAccessibleContext().setAccessibleDescription(getString("rstg"));
        m_btnRun.getAccessibleContext().setAccessibleDescription(getString("runf"));
        m_btnCancel.getAccessibleContext().setAccessibleDescription(getString("clse"));
        return;
    }
}
