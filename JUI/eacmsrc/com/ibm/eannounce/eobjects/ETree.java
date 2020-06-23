/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: ETree.java,v $
 * Revision 1.3  2012/04/05 17:48:34  wendy
 * jre142 and win7 changes
 *
 * Revision 1.2  2008/01/30 16:26:57  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:45:48  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:17  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:38:18  tony
 * This is the initial load of OPICM
 *
 * Revision 1.9  2005/09/08 17:59:10  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.8  2005/07/20 19:36:22  tony
 * TIR 6E9S4E
 *
 * Revision 1.7  2005/07/20 18:23:59  tony
 * added canShowPopup to improve functionality based on
 * where i sit.
 *
 * Revision 1.6  2005/06/02 20:41:59  tony
 * dwb3_20050602
 *
 * Revision 1.5  2005/02/08 21:38:39  tony
 * JTest Formatting
 *
 * Revision 1.4  2005/02/03 21:26:14  tony
 * JTest Format Third Pass
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
 * Revision 1.19  2003/12/30 20:41:27  tony
 * cr_3312
 *
 * Revision 1.18  2003/10/07 21:31:48  tony
 * added setlookandfeel to assist in accessibility
 * high contrast functionality.
 *
 * Revision 1.17  2003/05/30 21:09:24  tony
 * updated messaging logic to popup on top
 * of the currently active Component.
 *
 * Revision 1.16  2003/05/29 21:20:44  tony
 * adjusted setParm logic for single parm string.
 *
 * Revision 1.15  2003/05/22 16:23:14  tony
 * 50874 -- filter, find, and sort adjust the object they
 * function on dynamically.
 *
 * Revision 1.14  2003/05/16 18:22:28  tony
 * updated convenience methods
 *
 * Revision 1.13  2003/05/16 17:42:02  tony
 * added convenience methods to the default Tree.  This
 * flows up to other tree classes.
 *
 * Revision 1.12  2003/05/02 20:05:56  tony
 * 50504 -- adjusted messaging and this seems to have
 * resolved the problem in local test.
 *
 * Revision 1.11  2003/04/24 15:33:12  tony
 * updated logic to include preference for selection fore and
 * back ground.
 *
 * Revision 1.10  2003/04/11 20:02:33  tony
 * added copyright statements.
 *
 */
package com.ibm.eannounce.eobjects;
import com.elogin.*;
import com.ibm.eannounce.erend.TreeCellRend;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;
import java.util.Vector;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class ETree extends JTree implements EAccessConstants, EDisplayable, ActionListener {
	private static final long serialVersionUID = 1L;
	private boolean bModalCursor = false;
	/**
     * bUseBack
     */
    private boolean bUseBack = false;
	/**
     * bUseFont
     */
    private boolean bUseFont = true;
	/**
     * bUseFore
     */
    private boolean bUseFore = true;

	private Vector pVect = null;

	/**
	 * I am the popup menu.
	 */
	protected EPopupMenu popup = null;

	/**
     * eTree
     * @author Anthony C. Liberto
     */
    public ETree() {
		super();
		init();
		return;
	}

	/**
     * eTree
     * @param _model
     * @author Anthony C. Liberto
     */
    public ETree(TreeModel _model) {
		super(_model);
		init();
		return;
	}

	private void init() {
		setCellRenderer(new TreeCellRend());
		ToolTipManager.sharedInstance().registerComponent(this);
		createPopupMenu();		//commenting out will remove dwb3_20050602 popup
		return;
	}

	/**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
		pVect.clear();
		pVect = null;
		removePopupMenu();
		return;
	}

    /**
     * getDisplayable
     *
     * @return
     * @author Anthony C. Liberto
     */
    public EDisplayable getDisplayable() {
		Container par = getParent();
		if (par instanceof EDisplayable) {
			return (EDisplayable)par;
		}
		return null;
	}

    /**
     * setUseDefined
     *
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
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setUseBack(boolean _b) {
		bUseBack = _b;
		return;
	}

    /**
     * setUseFore
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setUseFore(boolean _b) {
		bUseFore = _b;
		return;
	}

    /**
     * setUseFont
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setUseFont(boolean _b) {
		bUseFont = _b;
		return;
	}

	/**
     * getSelectionBackground
     * @return
     * @author Anthony C. Liberto
     */
    public Color getSelectionBackground() {
		return eaccess().getPrefColor(PREF_COLOR_SELECTION_BACKGROUND,DEFAULT_COLOR_SELECTION_BACKGROUND);
	}

	/**
     * getSelectionForeground
     * @return
     * @author Anthony C. Liberto
     */
    public Color getSelectionForeground() {
		return eaccess().getPrefColor(PREF_COLOR_SELECTION_FOREGROUND,DEFAULT_COLOR_SELECTION_FOREGROUND);
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

	/**
     * setRowHeight
     * @param _font
     * @author Anthony C. Liberto
     */
    public void setRowHeight(Font _font) {
		if (_font != null) {
			FontMetrics fm = getFontMetrics(_font);
			setRowHeight(fm.getHeight() + 4);
		}
		return;
	}

	/**
     * @see java.awt.Component#getCursor()
     * @author Anthony C. Liberto
     */
    public Cursor getCursor() {
		if (isModalCursor()) {
			return eaccess().getModalCursor();
		} else {
			EDisplayable disp = getDisplayable();
			if (disp != null) {
				return disp.getCursor();
			}
		}
		return eaccess().getCursor();
	}

    /**
     * setModalCursor
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setModalCursor(boolean _b) {
		bModalCursor = _b;
		return;
	}

    /**
     * isModalCursor
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isModalCursor() {
		return bModalCursor;
	}

	/**
     * @see javax.swing.JComponent#getToolTipText(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public String getToolTipText(MouseEvent _me) {
        TreePath path = null;
        /*
		 turn of the annoying tool tip if not accessible
		 will meet the accessibility and be visually appealing
		 */
		if (!EAccess.isAccessible()) {			//dwb3_20050602
			return null;						//dwb3_20050602
		}										//dwb3_20050602
		path = getPathForLocation(_me.getX(), _me.getY());
		if (path != null) {
			Object node = path.getLastPathComponent();
			if (node != null) {
				return node.toString();
			}
		}
		return null;
	}

	/**
     * getTooltipLocation
     * @param _me
     * @return
     * @author Anthony C. Liberto
     */
    public Point getToolTipLocation(MouseEvent _me) {
		Point pt = _me.getPoint();
		String s = getToolTipText(_me);
		if (s == null || s.equals("")) {
			return null;
		}
		pt.translate(-1,-2);
		return pt;
	}

/*
eaccess pass thru
start
*/
	/**
     * eaccess
     * @return
     * @author Anthony C. Liberto
     */
    public static EAccess eaccess() {
		return EAccess.eaccess();
	}
/*
 50874
	public void setPanelObject(int _i, Object _o) {
		eaccess().setPanelObject(_i,_o);
		return;
	}
*/
	/**
     * show
     * @param _panel
     * @param _modal
     * @author Anthony C. Liberto
     */
    public void show(int _panel, boolean _modal) {
		eaccess().show(_panel, _modal);
		return;
	}

	/**
     * setCode
     * @param _code
     * @author Anthony C. Liberto
     */
    public void setCode(String _code) {
		eaccess().setCode(_code);
		return;
	}

	/**
     * setTitle
     * @param _title
     * @author Anthony C. Liberto
     */
    public void setTitle(String _title) {
		eaccess().setTitle(_title);
		return;
	}

	/**
     * getString
     * @param _code
     * @return
     * @author Anthony C. Liberto
     */
    public String getString(String _code) {
		return eaccess().getString(_code);
	}

	/**
     * getDateRoutines
     * @return
     * @author Anthony C. Liberto
     */
    public DateRoutines getDateRoutines() {
		return eaccess().getDateRoutines();
	}

	/**
     * getChar
     * @param _code
     * @return
     * @author Anthony C. Liberto
     */
    public char getChar(String _code) {
		return eaccess().getChar(_code);
	}

	/**
     * appendLog
     * @param _message
     * @author Anthony C. Liberto
     */
    public void appendLog(String _message) {
		EAccess.appendLog(_message);
		return;
	}

	/**
     * getNow
     * @param _format
     * @return
     * @author Anthony C. Liberto
     */
    public String getNow(String _format) {
		return eaccess().getNow(_format);
	}

	/**
     * getNow
     * @param _format
     * @param _refreshTime
     * @return
     * @author Anthony C. Liberto
     */
    public String getNow(String _format, boolean _refreshTime) {
		return eaccess().getNow(_format,_refreshTime);
	}

	/**
     * setParm
     * @param _s
     * @author Anthony C. Liberto
     */
    public void setParm(String _s) {
		eaccess().setParm(_s);
		return;
	}

	/**
     * setParms
     * @param _s
     * @author Anthony C. Liberto
     */
    public void setParms(String[] _s) {
		eaccess().setParms(_s);
		return;
	}

	/**
     * setParms
     * @param _s
     * @param _delim
     * @author Anthony C. Liberto
     */
    public void setParms(String _s, String _delim) {
		eaccess().setParms(_s,_delim);
		return;
	}
	/**
     * setParmCount
     * @param _i
     * @author Anthony C. Liberto
     */
    public void setParmCount(int _i) {
		eaccess().setParmCount(_i);
		return;
	}

	/**
     * setParm
     * @param _i
     * @param _s
     * @author Anthony C. Liberto
     */
    public void setParm(int _i, String _s) {
		eaccess().setParm(_i,_s);
		return;
	}

	/**
     * clearParms
     * @author Anthony C. Liberto
     */
    public void clearParms() {
		eaccess().clearParms();
		return;
	}

	/**
     * getParms
     * @return
     * @author Anthony C. Liberto
     */
    public String[] getParms() {
		return eaccess().getParms();
	}

	/**
     * getMessage
     * @return
     * @author Anthony C. Liberto
     */
    public String getMessage() {
		return eaccess().getMessage();
	}

	/**
     * setMessage
     * @param _s
     * @author Anthony C. Liberto
     */
    public void setMessage(String _s) {
		eaccess().setMessage(_s);
		return;
	}

	/**
     * showFYI
     * @author Anthony C. Liberto
     */
    public void showFYI() {
		eaccess().showFYI(this);
		return;
	}

	/**
     * showFYI
     * @param _code
     * @author Anthony C. Liberto
     */
    public void showFYI(String _code) {
		eaccess().showFYI(this,_code);
		return;
	}

	/**
     * showError
     * @author Anthony C. Liberto
     */
    public void showError() {
		eaccess().showError(this);
		return;
	}

	/**
     * showError
     * @param _code
     * @author Anthony C. Liberto
     */
    public void showError(String _code) {
		eaccess().showError(this,_code);
		return;
	}

	/**
     * showConfirm
     * @param _buttons
     * @param _clear
     * @return
     * @author Anthony C. Liberto
     */
    public int showConfirm(int _buttons, boolean _clear) {
		return eaccess().showConfirm(this,_buttons,_clear);
	}

	/**
     * showInput
     * @return
     * @author Anthony C. Liberto
     */
    public String showInput() {
		return eaccess().showInput(this);
	}

	/**
     * show
     * @param _dialogType
     * @param _msgType
     * @param _buttons
     * @param _reset
     * @author Anthony C. Liberto
     */
    public void show(int _dialogType, int _msgType, int _buttons, boolean _reset) {
		eaccess().show(this,_dialogType,_msgType,_buttons,_reset);
		return;
	}

	/**
     * show
     * @param _c
     * @param _adp
     * @param _modal
     * @author Anthony C. Liberto
     */
    public void show(Component _c, AccessibleDialogPanel _adp, boolean _modal) {
		eaccess().show(_c, _adp,_modal);
		return;
	}

	/**
     * getImage
     * @param _img
     * @return
     * @author Anthony C. Liberto
     */
    public Image getImage(String _img) {
		return eaccess().getImage(_img);
	}

	/**
     * getImageIcon
     * @param _img
     * @return
     * @author Anthony C. Liberto
     */
    public ImageIcon getImageIcon(String _img) {
		return eaccess().getImageIcon(_img);
	}

	/**
     * setBusy
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setBusy(boolean _b) {
		eaccess().setBusy(_b);
		return;
	}

	/**
     * isBusy
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isBusy() {
		return eaccess().isBusy();
	}

	/**
     * setModalBusy
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setModalBusy(boolean _b) {
		eaccess().setModalBusy(_b);
		return;
	}

	/**
     * isModalBusy
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isModalBusy() {
		return eaccess().isModalBusy();
	}

	/**
     * isWindows
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean isWindows() {
		return eaccess().isWindows();
	}
/*
eaccess pass thru
complete
*/

/*
 convenience methods
*/
	/**
     * collapseAll
     * @author Anthony C. Liberto
     */
    public void collapseAll() {
		collapseAll(null);
		return;
	}

	/**
     * collapseAll
     * @param _selNode
     * @author Anthony C. Liberto
     */
    public void collapseAll(TreeNode _selNode) {
		TreeNode rootNode = getRootNode();
        int ii = 0;
		if (rootNode != null) {
			collapseNode(rootNode);
			ii = rootNode.getChildCount();
			for (int i=0;i<ii;++i) {
				collapseNode(rootNode.getChildAt(i));
			}
			if (_selNode != null) {
				setSelectionPath(getPath(_selNode));
			}
		}
		return;
	}

	/**
     * collapseNode
     * @param _node
     * @author Anthony C. Liberto
     */
    public void collapseNode(TreeNode _node) {
		int ii = 0;
        if (_node != null && !_node.isLeaf()) {		//cr_3312
			collapsePath(getPath(_node));
		}											//cr_3312
		ii = _node.getChildCount();
		if (ii > 0) {
			for (int i=0;i<ii;++i) {
				collapseNode(_node.getChildAt(i));
			}
		}
		return;
	}

	/**
     * expandAll
     * @author Anthony C. Liberto
     */
    public void expandAll() {
		expandAll(null);
		return;
	}

	/**
     * expandAll
     * @param _selNode
     * @author Anthony C. Liberto
     */
    public void expandAll(TreeNode _selNode) {
		TreeNode rootNode = getRootNode();
		int ii = 0;
        if (rootNode != null) {
			expandNode(rootNode);
			ii = rootNode.getChildCount();
			for (int i=0;i<ii;++i) {
				expandNode(rootNode.getChildAt(i));
			}
			if (_selNode != null) {
				setSelectionPath(getPath(_selNode));
			}
		}
		return;
	}

	/**
     * expandNode
     * @param _node
     * @author Anthony C. Liberto
     */
    public void expandNode(TreeNode _node) {
		int ii = 0;
        if (_node != null && !_node.isLeaf()) {		//cr_3312
			expandPath(getPath(_node));
		}											//cr_3312
		ii = _node.getChildCount();
		if (ii > 0) {
			for (int i=0;i<ii;++i) {
				expandNode(_node.getChildAt(i));
			}
		}
		return;
	}

	/**
     * if the node is a DefaultMutableNode use the getPath function
     * otherwise trickle up the tree to find your path to root.
     *
     * @return TreePath
     * @param _node
     */
	public TreePath getPath(TreeNode _node) {
		if (_node != null) {
			if (_node instanceof DefaultMutableTreeNode) {
				return new TreePath(((DefaultMutableTreeNode)_node).getPath());
			} else {
				return getPathToRoot(_node);
			}
		}
		return null;
	}

	/**
     * getPathToRoot
     * @param _node
     * @return
     * @author Anthony C. Liberto
     */
    public TreePath getPathToRoot(TreeNode _node) {
		TreeModel tm = getModel();
		if (tm instanceof DefaultTreeModel) {
			return new TreePath(((DefaultTreeModel)tm).getPathToRoot(_node));
		} else {
			return getPathFromTo(_node,getRootNode());
		}
	}

	/**
     * returns the path from point a to point b if they are on
     * the same path otherwise it is null.
     *
     * @return TreePath
     * @param _from
     * @param _to
     */
	public TreePath getPathFromTo(TreeNode _from, TreeNode _to) {
		TreeNode node = null;
        if (_from == null || _to == null) {
			return null;
		}
		if (pVect == null) {
			pVect = new Vector();
		} else {
			pVect.clear();
		}
		pVect.add(_from);
		node = _from.getParent();
		while (node != null) {
			pVect.insertElementAt(node,0);
			if (node == _to) {
				node = null;
			} else {
				node = node.getParent();
			}
		}

		if (node != null && node != _to) {
			return null;
		}

		if (!pVect.isEmpty()) {
			int ii = pVect.size();
			TreeNode[] nodes = new TreeNode[ii];
			for (int i=0;i<ii;++i) {
				nodes[i] = (TreeNode)pVect.get(i);
			}
			return new TreePath(nodes);
		}
		return null;
	}

	/**
     * getRootNode
     * @return
     * @author Anthony C. Liberto
     */
    public TreeNode getRootNode() {
		TreeModel tm = getModel();
		if (tm != null) {
			Object root = tm.getRoot();
			if (root instanceof TreeNode) {
				return (TreeNode)root;
			}
		}
		return null;
	}

	/**
     * getNodeDepth
     * @param _node
     * @return
     * @author Anthony C. Liberto
     */
    public int getNodeDepth(TreeNode _node) {
		int i = 0;
		TreeNode node = _node.getParent();
		while (node != null) {
			node = node.getParent();
			++i;
		}
		return i;
	}

    /**
     * create the popop menu
     * dwb3_20050602
     * @author tony
     */
    public void createPopupMenu() {
		if (popup == null) {
			popup = new EPopupMenu();
			/*
			 allow for toggle of popup
			 without this we won't popup on
			 the right mouse button click
			 */
			addMouseListener(popup);
		}
    	return;
    }

    /**
     * remove the popop menu
     * dwb3_20050602
     * @author tony
     */
    public void removePopupMenu() {
		if (popup != null) {
			/*
			 remove the popup listener
			 we don't want a memory leak
			 */
			removeMouseListener(popup);
			popup.dereference();
			popup = null;
		}
		return;
    }

	/**
     * actionPerformed
     *
     * @param _ae
     * @author Anthony C. Liberto
     */
    public void actionPerformed(ActionEvent _ae) {}

}
