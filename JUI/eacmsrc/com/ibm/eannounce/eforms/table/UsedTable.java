/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: UsedTable.java,v $
 * Revision 1.4  2009/05/26 13:45:46  wendy
 * Performance cleanup
 *
 * Revision 1.3  2008/02/21 19:18:51  wendy
 * Add access to change history for relators
 *
 * Revision 1.2  2008/01/30 16:26:58  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:45:12  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:16  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:38:09  tony
 * This is the initial load of OPICM
 *
 * Revision 1.12  2005/09/08 17:59:08  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.11  2005/02/09 18:55:25  tony
 * Scout Accessibility
 *
 * Revision 1.10  2005/01/31 20:47:48  tony
 * JTest Second Pass
 *
 * Revision 1.9  2005/01/26 22:17:57  tony
 * JTest Modifications
 *
 * Revision 1.8  2005/01/10 22:30:29  tony
 * multiple edit from whereUsed.
 *
 * Revision 1.7  2005/01/07 18:21:42  tony
 * 6554
 * improved logic to adjust action list
 * in addition to table on create and edit or
 * table refresh.
 *
 * Revision 1.6  2004/11/16 22:25:02  tony
 * improved sorting and resizing logic to improve table
 * performance.
 *
 * Revision 1.5  2004/11/15 23:01:18  tony
 * improved table logic to sort only a single time on
 * navigation type table, instead of multiple times.
 *
 * Revision 1.4  2004/10/22 22:14:44  tony
 * auto_sort/size
 *
 * Revision 1.3  2004/06/08 20:42:20  tony
 * 5ZPTCX.2
 *
 * Revision 1.2  2004/04/12 22:22:19  tony
 * MN_18712534
 *
 * Revision 1.1.1.1  2004/02/10 16:59:47  tony
 * This is the initial load of OPICM
 *
 * Revision 1.25  2004/01/06 23:00:29  joan
 * 53496
 *
 * Revision 1.24  2003/12/09 22:32:05  tony
 * 53362
 *
 * Revision 1.23  2003/12/05 22:08:19  tony
 * cleaned-up code
 *
 * Revision 1.22  2003/12/04 21:02:38  tony
 * improved functionality to enhance performance
 *
 * Revision 1.21  2003/11/11 00:42:22  tony
 * accessibility update, added convenience method to table.
 *
 * Revision 1.20  2003/11/10 22:57:27  tony
 * accessibility
 *
 * Revision 1.19  2003/10/31 17:30:48  tony
 * 52783
 *
 * Revision 1.18  2003/10/29 16:47:40  tony
 * 52728
 *
 * Revision 1.17  2003/10/03 16:39:12  tony
 * updated accessibility.
 *
 * Revision 1.16  2003/10/02 21:36:57  tony
 * added accessibility logic
 *
 * Revision 1.15  2003/09/30 18:13:46  tony
 * acl_20030930 --
 * improved where used logic to properly eliminate invalid
 * action items.
 *
 * Revision 1.14  2003/09/22 21:36:26  tony
 * cchu_20030922
 * picklist actions were being removed from action list if the
 * relator did not exist.
 *
 * Revision 1.13  2003/09/15 20:48:52  tony
 * 52264
 *
 * Revision 1.12  2003/09/05 20:19:02  tony
 * 52076
 *
 * Revision 1.11  2003/09/03 17:09:29  tony
 * 52008
 *
 * Revision 1.10  2003/08/26 21:17:09  tony
 * cr_TBD_3
 * update whereused-matrix & matrix-whereused functionality.
 *
 * Revision 1.9  2003/05/29 19:03:38  tony
 * 50944
 *
 * Revision 1.8  2003/04/22 21:05:15  tony
 * fixed history information bug.
 *
 * Revision 1.7  2003/04/21 23:21:48  tony
 * added association renderer for used table.
 *
 * Revision 1.6  2003/04/21 22:14:47  tony
 * updated default logic to allow for parent and child
 * color defaults.  Updated the sort on the whereUsed table.
 *
 * Revision 1.5  2003/04/21 20:03:13  tony
 * enhanced scroll logic to improve flag editor.
 *
 * Revision 1.4  2003/03/12 23:51:16  tony
 * accessibility and column order
 *
 * Revision 1.3  2003/03/05 18:54:26  tony
 * accessibility updates.
 *
 * Revision 1.2  2003/03/04 16:52:56  tony
 * added logic for EntityHistoryGroup
 *
 * Revision 1.1.1.1  2003/03/03 18:03:51  tony
 * This is the initial load of OPICM
 *
 * Revision 1.24  2002/11/07 16:58:35  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.table;
import com.elogin.Routines;
import com.ibm.eannounce.eforms.action.ActionController;
import com.ibm.eannounce.erend.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import java.awt.*;
import java.util.Vector;
import javax.swing.table.TableCellRenderer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.UIManager;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class UsedTable extends NavTable {
	private static final long serialVersionUID = 1L;
	/**
     * PARENT
     */
	//private static final int PARENT 				= 0;
	/**
     * RELATOR
     */
	//private static final int RELATOR 			= 1;
	/**
     * DIRECTION
     */
	private static final int DIRECTION 			= 2;
	/**
     * ENTITY
     */
	private static final int ENTITY			 	= 3;
	/**
     * ENTITY_DISPLAY_NAME
     */
	private static final int ENTITY_DISPLAY_NAME = 4;

	private ChildRend childRenderer = new ChildRend();
	private ChildFoundRend childFoundRenderer = new ChildFoundRend();
	private ParentRend parentRenderer = new ParentRend();
	private ParentFoundRend parentFoundRenderer = new ParentFoundRend();
	private AssocRend assocRenderer = new AssocRend();
	private AssocFoundRend assocFoundRenderer = new AssocFoundRend();

	private EANActionItem[] actions = null;				//52076
	private String relKey = null;						//52076
	private boolean bRelEnt = false;					//MN_18712534

	/**
     * usedTable
     * @param _o
     * @param _table
     * @param _ac
     * @author Anthony C. Liberto
     */
    public UsedTable(Object _o, RowSelectableTable _table, ActionController _ac) {
		super(_o,_table,_ac,true);
	}

	/**
     * isChild
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    private boolean isChild(String _s) {
		if (_s != null) {
			return _s.equalsIgnoreCase("Child");
		}
		return false;
	}

	/**
     * isAssociation
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    private boolean isAssociation(String _s) {
		if (_s != null) {
			return _s.equalsIgnoreCase("Association");
		}
		return false;
	}

	/**
     * isParent
     * @param _row
     * @return
     * @author Anthony C. Liberto
     */
    private boolean isParent(int _row) {
		String s = getValue(_row, convertColumnIndexToView(DIRECTION));
		if (s != null) {
			return s.equalsIgnoreCase("Parent");
		}
		return false;
	}

	/**
     * isParent
     * @param _s
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean isParent(String _s) {
		if (_s != null) {
			return _s.equalsIgnoreCase("Parent");
		}
		return false;
	}*/

	/**
     * hasEntity
     * @return
     * @author Anthony C. Liberto
     */
    public boolean hasEntity() {
		return hasEntity(getSelectedRow());
	}

	/**
     * hasEntity
     * @param _row
     * @return
     * @author Anthony C. Liberto
     */
    private boolean hasEntity(int _row) {
		String s = getValue(_row,convertColumnIndexToView(ENTITY_DISPLAY_NAME));
		return Routines.have(s);
	}

    /**
     * init
     *
     * @author Anthony C. Liberto
     */
    public void init() {
		setRowMargin(0);
		initAccessibility("accessible.usedTable");
		setColumnSelectionAllowed(false);
		setAutoResizeMode(AUTO_RESIZE_OFF);
		setDefaultRenderer(Object.class, new ERend());
		resizeCells();
		setBorder(UIManager.getBorder("eannounce.focusBorder"));
		replayFilter();
	}

    /**
     * sort
     *
     * @author Anthony C. Liberto
     */
    public void sort() {
		int[] col = new int[4];
		boolean[] direction = new boolean[4];
		col[0] = 0;		//entity
		col[1] = 2;		//direction
		col[2] = 3;		//childEntity
		col[3] = 4;		//child name
		direction[0] = true;
		direction[1] = false;
		direction[2] = true;
		direction[3] = true;
		cgtm.sort(col,direction);
	}

    /**
     * getCellRenderer
     *
     * @param r
     * @param c
     * @return
     * @author Anthony C. Liberto
     */
    public TableCellRenderer getCellRenderer(int r, int c) {
		String type = getValue(r, convertColumnIndexToView(DIRECTION));
		if (isFound(r,c)) {
			if (isChild(type)) {
				return childFoundRenderer;
			} else if (isAssociation(type)) {
				return assocFoundRenderer;
			} else {
				return parentFoundRenderer;
			}
		}
		if (isChild(type)) {
			return childRenderer;
		} else if (isAssociation(type)) {
			return assocRenderer;
		} else {
			return parentRenderer;
		}
	}

	/**
     * getValue
     * @param _row
     * @param _type
     * @return
     * @author Anthony C. Liberto
     */
    private String getValue(int _row, int _type) {
		Object o = getValueAt(_row, _type);
		if (o != null) {
			return o.toString();
		}
		return null;
	}

	/**
     * getWhereUsedGroup
     * @param _s
     * @return
     * @author Anthony C. Liberto
     * /
    public WhereUsedGroup getWhereUsedGroup(String _s) {
		return cgtm.getWhereUsedGroup(_s);
	}*/

    /**
     * getUIPrefKey
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getUIPrefKey() {					//21643
		return "WUSED";
	}

    /**
     * dereference
     *
     * @author Anthony C. Liberto
     */
    public void dereference() {
		if (childRenderer != null) {
			childRenderer.removeAll();
			childRenderer.removeNotify();
			childRenderer = null;
		}

		actions = null;								//52076
		relKey = null;								//52076

		super.dereference();
	}

    /**
     * resizeCells
     *
     * @author Anthony C. Liberto
     */
    public void resizeCells() {
		int rr = getRowCount();
		int h = 0;
        int cc = 0;
        Font fnt = null;
        FontMetrics fmParent = null;
        FontMetrics fmChild = null;
        int Width = 0;
        RSTableColumn tc = null;
        if (!eaccess().canSize(rr)) {								//auto_sort/size
			return;													//auto_sort/size
		}															//auto_sort/size
		cc = getColumnCount();
		fnt = getFont();
		fmParent = getFontMetrics(fnt);
		fmChild = getFontMetrics(fnt.deriveFont(Font.BOLD + Font.ITALIC));
		for (int c=0;c<cc;++c) {
			if (isColumnVisible(c)) {
				Width = getWidth(fmParent, getColumnName(c));
				for (int r=0; r<rr;++r) {
//tableresize					Object o = getValueAt(r,c);
					Object o = getDirectValueAt(r,c);				//tableresize
					if (isParent(r)) {
						Width = Math.max(Width,getWidth(fmParent,o));
					} else {
						Width = Math.max(Width,getWidth(fmChild,o));
					}
				}
			} else {
				Width = 0;
			}
			tc = (RSTableColumn)getColumnModel().getColumn(c);

			tc.setWidth(Width);
			tc.setPreferredWidth(Width);
//52728			tc.setMinWidth(tc.getMinimumPreferredWidth());		//15165
            tc.setMinWidth(tc.getMinimumAllowableWidth());		//52728

		}
		h = getRowHeight();
		for (int r=0;r<rr;++r) {
			if (isRowFiltered(r)) {
				setRowHeight(r,0);

			} else {
				setRowHeight(r,h);
			}
		}
	}

	/**
     * getCurrentRelatedEntityItem
     * @param _new
     * @return
     * @author Anthony C. Liberto
     */
    private EntityItem getCurrentRelatedEntityItem(boolean _new) {
		int row = getSelectedRow();
		WhereUsedItem wi = (WhereUsedItem)cgtm.getRow(row);
		if (wi == null) {
			return null;
		} else if (_new) {
			try {
				return new EntityItem(wi.getRelatedEntityItem());
			} catch (MiddlewareRequestException _mre) {
				_mre.printStackTrace();
				return wi.getRelatedEntityItem();
			}
		} else {
			return wi.getRelatedEntityItem();
		}
	}
    /**
     * relatorhistoryInfo
     *
     */
    public void relatorHistoryInfo() {
		int row = getSelectedRow();
		WhereUsedItem wi = (WhereUsedItem)cgtm.getRow(row);
		if (wi != null) {
			EntityItem curitem = wi.getRelatorEntityItem();
			eaccess().getThisChangeHistory(curitem);
		}else{
			eaccess().showAdminMsg("No Parent Relator found in this action.");			
		}     	
	}
    /**
     * historyInfo
     *
     * @author Anthony C. Liberto
     */
    public void historyInfo() {
		eaccess().getChangeHistory(getCurrentRelatedEntityItem(false));
	}

/*
 *	21814 -- start
 */
    /**
     * getInformation
     *
     * @return
     * @author Anthony C. Liberto
     */
    protected String getInformation() {
		int r = getSelectedRow();
        WhereUsedItem wui = null;
		if (r < 0) {
			return getString("nia");
		}
		wui = (WhereUsedItem)cgtm.getRow(r);
		if (wui == null) {
			return getString("nia");
		}
		return  getInformation("Original", wui.getOriginalEntityItem()) + RETURN + RETURN +
		getInformation("Relator", wui.getRelatorEntityItem()) + RETURN+ RETURN +
        getInformation("Related", wui.getRelatedEntityItem());
	}

	private String getInformation (String _s, EntityItem _ei) {
		if (_ei == null) {
			return "";
		}
		return  _s + "_key: " + _ei.getKey() +
		RETURN + _s + "_EntityType: " + _ei.getEntityType() +
        RETURN + _s + "_EntityID: " + _ei.getEntityID();
//				"RETURN" + _s + "_Value: " + _ei.toString();
	}

/*
 *	21814 -- stop
 */

/*
 50944
*/
	/**
     * hasEntities
     * @return
     * @author Anthony C. Liberto
     */
    public boolean hasEntities() {
		int[] _rows = getSelectedRows();
		int ii = _rows.length;
		for (int i=0;i<ii;++i) {
			if (Routines.have(getValue(_rows[i],convertColumnIndexToView(ENTITY_DISPLAY_NAME)))) {
				return true;
			}
		}
		return false;
	}

/*
 cr_TBD_3
 */
	/**
     * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
     * @author Anthony C. Liberto
     */
    public void valueChanged(ListSelectionEvent _lse) {
		superValueChanged(_lse);
		if (_lse == null || _lse.getValueIsAdjusting()) {									//52076
			return;																			//52076
		}																					//52076
		if (cgtm != null && !isEmpty()) {
			EANFoundation ean = (EANFoundation)cgtm.getRow(getSelectedRow());				//52076
			loadActions(ean);
		}
	}

	/**
     * getWhereUsedItems
     * @param _new
     * @return
     * @author Anthony C. Liberto
     */
    public WhereUsedItem[] getWhereUsedItems(boolean _new) {
		int[] rows = getSelectedRows();
		int rr = rows.length;

		WhereUsedItem[] wi = new WhereUsedItem[rr];

		for (int r=0;r<rr;++r) {
			wi[r] = (WhereUsedItem)cgtm.getRow(rows[r]);
		}
		return wi;
	}
/*
 accessibility
 */
    /**
     * getAccessibleRowNameAt
     *
     * @param _row
     * @return
     * @author Anthony C. Liberto
     */
    public String getAccessibleRowNameAt(int _row) {
		Object o = getAccessibleValueAt(_row,0);
		if (o != null) {
			return o.toString();
		}
		return super.getAccessibleRowNameAt(_row);
	}

	/**
     * @see com.ibm.eannounce.eForms.table.GTable#getAccessibleAttributeType(int, int)
     * @author Anthony C. Liberto
     */
    protected String getAccessibleAttributeType(int _row, int _col) {
		Object o = getAccessibleValueAt(_row,3);
		if (o != null) {
			return o.toString();
		}
		return super.getAccessibleAttributeType(_row,_col);
	}

/*
 53362
 */
	/**
     * defaultSelect
     * @author Anthony C. Liberto
     */
    public void defaultSelect() {
		EANFoundation ean = null;
        if (getColumnCount() > 0) {
			setColumnSelectionInterval(0,0);
		}
		if (getRowCount() > 0) {
			setRowSelectionInterval(0,0);
			ean = (EANFoundation)cgtm.getRow(0);
			loadActions(ean);
		}
	}

/*
 52076
 */
	/**
     * loadActions
     * @param _ean
     * @author Anthony C. Liberto
     */
    private void loadActions(EANFoundation _ean) {
		String curKey = null;
		if (_ean instanceof WhereUsedItem) {
			EntityItem relator = ((WhereUsedItem)_ean).getRelatorEntityItem();
			if (relator != null) {
//53496				curKey = relator.getEntityType();
				curKey = relator.getEntityType() + ((WhereUsedItem)_ean).getDirection();	//53496
			}
		}
		if (_ean != null) {
			String key = _ean.getKey();
			if (tree != null && key != null && ac != null) {
//MN_18712534				if (curKey == null || !curKey.equals(relKey)) {
				boolean bKeysMatch =(curKey == null || !curKey.equals(relKey));		//MN_18712534
				if (bKeysMatch || (!bKeysMatch && (hasEntities() != bRelEnt))) {	//MN_18712534
					actions = ac.getAllActions(getActionItemsAsArray(key));
					relKey = curKey;
					bRelEnt = hasEntities();										//MN_18712534
				}
				if (actions != null && !hasEntities()) {
					Vector v = new Vector();
					int ii = actions.length;
					for (int i=0;i<ii;++i) {
						if (actions[i] instanceof CreateActionItem) {
							v.add(actions[i]);
						} else if (actions[i] instanceof LinkActionItem) {
							v.add(actions[i]);
						} else if (actions[i] instanceof SearchActionItem) {
							v.add(actions[i]);
						} else if (actions[i] instanceof NavActionItem) {
							if (((NavActionItem)actions[i]).isPicklist()) {
								v.add(actions[i]);
							}
						}
					}
					if (v.isEmpty()) {
						actions = null;
					} else {
						actions = (EANActionItem[])v.toArray(new EANActionItem[v.size()]);
					}
				}
				tree.load(actions,getTableTitle());
				ac.refreshMenu();
			}
		}
	}

/*
 5ZPTCX.2
 */
	/**
     * link
     * @param _lai
     * @param _parent
     * @param _child
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public Object link(LinkActionItem _lai, EntityItem[] _parent, EntityItem[] _child, Component _c) {
		if (cgtm != null) {
			return cgtm.link(_lai,_parent,_child,_c);
		}
		return null;
	}

/*
 6554
*/
	/**
     * reselectActions
     * @param _row
     * @author Anthony C. Liberto
     */
    public void reselectActions(int _row) {
    	relKey = null; // reset current key to force reload of actions, some lost after a create
		EANFoundation ean = null;
        if (_row >= 0 && _row < getRowCount()) {
			setRowSelectionInterval(_row,_row);
			ean = (EANFoundation)cgtm.getRow(_row);
			loadActions(ean);
		}
	}

	/**
     * getEntityType
     * @param _row
     * @return
     * @author Anthony C. Liberto
     */
    private String getEntityType(int _row) {
		if (_row >=0 && _row < getRowCount()) {
			return getValue(_row,convertColumnIndexToView(ENTITY));
		}
		return null;
	}

	/**
     * getValidSelectedKeys
     * @param _row
     * @return
     * @author Anthony C. Liberto
     */
    public String[] getValidSelectedKeys(int _row) {
		String[] out = null;
		String key = getEntityType(_row);
		if (key != null) {
			Vector v = new Vector();
			int[] rows = getSelectedRows();
			if (rows != null) {
				int ii = rows.length;
				for (int i=0;i<ii;++i) {
					String str = getEntityType(rows[i]);
					if (str != null && str.equals(key)) {
						if (hasEntity(rows[i])) {
							v.add(cgtm.getRowKey(rows[i]));
						}
					}
				}
			}
			if (!v.isEmpty()) {
				int xx = v.size();
				out = new String[xx];
				for (int x=0;x<xx;++x) {
					out[x] = (String)v.get(x);
				}
			}
		}
		return out;
	}
}
