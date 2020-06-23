/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: ActionTreeModel.java,v $
 * Revision 1.3  2008/08/08 21:52:13  wendy
 * CQ00006067-WI : LA CTO - More support for QueryAction
 *
 * Revision 1.2  2008/01/30 16:27:06  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:44:58  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:03  tony
 * This is the initial load of OPICM
 *
 * Revision 1.6  2005/09/08 17:59:05  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.5  2005/01/31 20:47:46  tony
 * JTest Second Pass
 *
 * Revision 1.4  2005/01/26 23:42:28  tony
 * JTest Formatting
 *
 * Revision 1.3  2005/01/18 21:38:49  tony
 * USRO-R-JSTT-68RKKP
 *
 * Revision 1.2  2005/01/07 18:05:47  tony
 * rm_20050107
 * adjusted logic to allow for menu items and toolbar containers
 * to be populated by an array of keys instead of just a single one.
 *
 * Revision 1.1.1.1  2004/02/10 16:59:44  tony
 * This is the initial load of OPICM
 *
 * Revision 1.3  2003/10/20 16:37:15  tony
 * memory update.  Updated dereference logic to close memory gaps
 *
 * Revision 1.2  2003/09/22 21:36:26  tony
 * cchu_20030922
 * picklist actions were being removed from action list if the
 * relator did not exist.
 *
 * Revision 1.1.1.1  2003/03/03 18:03:49  tony
 * This is the initial load of OPICM
 *
 * Revision 1.11  2002/11/20 21:23:52  tony
 * removed println statement
 *
 * Revision 1.10  2002/11/20 16:30:49  tony
 * acl_20021120 -- added navigate and search functions to
 * menu and toolbar
 *
 * Revision 1.9  2002/11/07 16:58:32  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.navigate.tree;
import com.elogin.*;
import javax.swing.tree.*;
import COM.ibm.eannounce.objects.*;
import java.util.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class ActionTreeModel extends DefaultTreeModel implements EAccessConstants {
	private static final long serialVersionUID = 1L;
	private HashMap map = new HashMap();
	private HashMap mapAction = new HashMap();

	/**
     * ActionTreeModel
     * @param _an
     * @author Anthony C. Liberto
     */
    public ActionTreeModel(AbstractNode _an) {
		super(_an);
	}

	/**
     * ActionTreeModel
     * @author Anthony C. Liberto
     */
    public ActionTreeModel() {
		this(new CategoryNode("ROOT","navActionTree -- ROOT"));
	}

	/**
     * clear
     * @author Anthony C. Liberto
     */
    public void clear() {
		AbstractNode an = getRootNode();
		if (map != null) {
			map.clear();
		}
		if (mapAction != null) {
			mapAction.clear();
		}
		if (an != null) {
			while(an.getChildCount() > 0) {
				removeNodeFromParent((AbstractNode)an.getFirstChild());
			}
		}
	}

	/**
     * setRootName
     * @param _s
     * @author Anthony C. Liberto
     */
    public void setRootName(String _s) {
		AbstractNode root = getRootNode();
		if (root != null) {
			root.setUserObject(_s);
		}
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
        clear();
		ii = _table.getRowCount();

		rows = _table.getTableRowsAsArray();

		if (_sort) {
			Arrays.sort(rows,new EComparator(_direction));
		}

		for (int i=0;i<ii;++i) {
			addNode((EANActionItem)_table.getRow(rows[i].getKey()));
		}
	}

	/**
     * addNode
     * @param _ean
     * @author Anthony C. Liberto
     */
    public void addNode(EANActionItem _ean) {
		AbstractNode parent = null;
        AbstractNode child = null;
        String key = null;
        if (_ean == null) {			//cchu_20030922
			return;					//cchu_20030922
		}							//cchu_20030922
		child = new ActionNode(_ean);
		parent = getParentNode(_ean.getCategoryCode(), _ean.getCategoryShortDescription(),true);
		insertNodeInto(child, parent, parent.getChildCount());
		map.put(_ean.getKey(),_ean);
		key = getPurpose(_ean);
		if (mapAction.containsKey(key)) {
			Vector v = (Vector)mapAction.get(key);
			v.add(_ean);
		} else {
			Vector v = new Vector();
			v.add(_ean);
			mapAction.put(key,v);							//USRO-R-JSTT-68RKKP
////USRO-R-JSTT-68RKKP			mapAction.put(_ean.getActionClass(),v);			//acl_20021120
//acl_20021120			mapAction.put(_ean.getPurpose(),v);
		}
	}

	private String getPurpose(EANActionItem _ean) {
		if (_ean instanceof CreateActionItem) {
			return ACTION_PURPOSE_CREATE;

		} else if (_ean instanceof DeleteActionItem) {
			return ACTION_PURPOSE_DELETE;

		} else if (_ean instanceof EditActionItem) {
			return ACTION_PURPOSE_EDIT;

		} else if (_ean instanceof ExtractActionItem) {
			return ACTION_PURPOSE_EXTRACT;

		} else if (_ean instanceof LinkActionItem) {
			return ACTION_PURPOSE_LINK;

		} else if (_ean instanceof LockActionItem) {
			return ACTION_PURPOSE_LOCK;

		} else if (_ean instanceof MatrixActionItem) {
			return ACTION_PURPOSE_MATRIX;

		} else if (_ean instanceof NavActionItem) {
			return ACTION_PURPOSE_NAVIGATE;

		} else if (_ean instanceof ReportActionItem) {
			return ACTION_PURPOSE_REPORT;

		} else if (_ean instanceof SearchActionItem) {
			return ACTION_PURPOSE_SEARCH;

		} else if (_ean instanceof WhereUsedActionItem) {
			return ACTION_PURPOSE_WHERE_USED;

		} else if (_ean instanceof WorkflowActionItem) {
			return ACTION_PURPOSE_WORK_FLOW;

		} else if (_ean instanceof PDGActionItem) { //USRO-R-JSTT-68RKKP
			return ACTION_PURPOSE_PDG;
		}			//USRO-R-JSTT-68RKKP
		else if (_ean instanceof QueryActionItem) {
			return ACTION_PURPOSE_QUERY;

		}
//		else if (_ean instanceof ActionItem)
//			return ACTION_PURPOSE_;
//USRO-R-JSTT-68RKKP		return _ean.getClass().getName();
		return _ean.getActionClass();			//USRO-R-JSTT-68RKKP
	}

	/**
     * getActionItem
     * @param _key
     * @return
     * @author Anthony C. Liberto
     */
    public EANActionItem getActionItem(String _key) {
		if (map.containsKey(_key)) {
			Object o = map.get(_key);
			if (o instanceof EANActionItem) {
				return (EANActionItem)o;
			}
		}
		return null;
	}

	/**
     * actionExists
     * @param _sAction
     * @return
     * @author Anthony C. Liberto
     */
    public boolean actionExists(String _sAction) {
		return mapAction.containsKey(_sAction);
	}

	/**
     * getActionItemArray
     * @param _key
     * @return
     * @author Anthony C. Liberto
     */
    public EANActionItem[] getActionItemArray(String _key) {
		if (mapAction.containsKey(_key)) {
			Vector v = (Vector)mapAction.get(_key);
			int ii = v.size();
			EANActionItem[] out = new EANActionItem[ii];
			for (int i=0;i<ii;++i) {
				out[i] = (EANActionItem)v.get(i);
			}
			return out;
		} else {
			return null;
		}
//		AbstractNode parent = getParentNode(_key,false);
//		if (parent != null) {
//			int ii = parent.getChildCount();
//			EANActionItem[] out = new EANActionItem[ii];
//			for(int i=0;i<ii;++i) {
//				out[i] = ((AbstractNode)parent.getChildAt(i)).getActionItem();
//			}
//			return out;
//		}
//		return null;
	}

	/**
     * getParentNode
     * @param _code
     * @param _desc
     * @param _create
     * @return
     * @author Anthony C. Liberto
     */
    public AbstractNode getParentNode(String _code, String _desc, boolean _create) {
		AbstractNode root = getRootNode();
        AbstractNode child = null;
        AbstractNode child2 = null;
		if (root != null) {
			int ii = root.getChildCount();
			for (int i=0;i<ii;++i) {
				child = (AbstractNode)root.getChildAt(i);
				if (child.equals(_code)) {
					return child;
				}
			}
		}
		if (!_create) {
			return null;
		}
		child2 = new CategoryNode(_code,_desc);
		insertNodeInto(child2,root,root.getChildCount());
		return child2;
	}

	/**
     * getPath
     * @param _an
     * @return
     * @author Anthony C. Liberto
     */
    public TreePath getPath(AbstractNode _an) {
		return new TreePath(_an.getPath());
	}

	/**
     * getRootNode
     * @return
     * @author Anthony C. Liberto
     */
    public AbstractNode getRootNode() {
		Object o = getRoot();
		if (o instanceof AbstractNode) {
			return (AbstractNode)o;
		}
		return null;
	}

	/**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
		clear();
		mapAction = null;
		map = null;
		setRoot(null);
	}

/*
 rm_20050107
 */
	/**
     * getActionItemArray
     * @param _key
     * @return
     * @author Anthony C. Liberto
     */
    public EANActionItem[] getActionItemArray(String[] _key) {
		Vector vctAll = new Vector();
		if (_key != null) {
			int ii = _key.length;
			for (int i=0;i<ii;++i) {
				if (mapAction.containsKey(_key[i])) {
					vctAll.addAll((Vector)mapAction.get(_key[i]));
				}
			}
			if (!vctAll.isEmpty()) {
				int xx = vctAll.size();
				EANActionItem[] out = new EANActionItem[xx];
				for (int x=0;x<xx;++x) {
					out[x] = (EANActionItem)vctAll.get(x);
				}
				return out;
			}
		}
		return null;
	}
}
