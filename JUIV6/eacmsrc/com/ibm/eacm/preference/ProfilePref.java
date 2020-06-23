//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.preference;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import COM.ibm.opicmpdh.middleware.*;

import com.ibm.eacm.EACM;
import com.ibm.eacm.objects.*;
import com.ibm.eacm.rend.TreeCellRend;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;
import com.ibm.eacm.actions.EACMAction;


/**
 * This class manages preferences for profile
 * @author Wendy Stimpson
 */
//$Log: ProfilePref.java,v $
//Revision 1.2  2013/07/18 18:26:52  wendy
//fix compiler warnings
//
//Revision 1.1  2012/09/27 19:39:15  wendy
//Initial code
//
public class ProfilePref extends JPanel implements Preferencable, EACMGlobals
{
	private static final long serialVersionUID = 1L;

	/**
	 * default profile
	 */
	private static final String DEFAULT_ENTERPRISE = ".default.enterprise";
	private static final String DEFAULT_OPWGID = ".default.opwgid";

	private JButton bSave = null;
	private JButton bReset = null;
	private JPanel btnPnl =null;
	private PrefTreeModel wModel = new PrefTreeModel(new ProfNode(Utils.getResource("dwGroup")));
	private PrefTree wTree = new PrefTree(wModel);
	private JScrollPane wScroll = new JScrollPane(wTree);
	private String userName = null;
	private KeyAdapter keyadapter = null;
	private PrefMgr prefMgr;

	ProfilePref(PrefMgr pm){
		super(new BorderLayout());
		prefMgr = pm;
		initProfileTree();

		createButtonPanel();

		add(wScroll,BorderLayout.NORTH);
		wTree.addTreeSelectionListener((SaveAction)bSave.getAction());
		keyadapter = new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar()==KeyEvent.VK_ENTER)	{
					if (bSave.isEnabled()){
						e.consume();
						javax.swing.SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								bSave.doClick();
							}
						});
					}
				}
			}
		};
		wTree.addKeyListener(keyadapter);

		setBorder(BorderFactory.createTitledBorder(Utils.getResource("profile.border")));// meet accessiblity 
	}

	private void createButtonPanel() {
		btnPnl = new JPanel(new BorderLayout(5, 5));

		bSave = new JButton(new SaveAction());
//		this is needed or the mnemonic doesnt activate
		bSave.setMnemonic((char)((Integer)bSave.getAction().getValue(Action.MNEMONIC_KEY)).intValue());
		bSave.addKeyListener(prefMgr.getKeyListener());

		bReset = new JButton(new ResetAction());
		bReset.setMnemonic((char)((Integer)bReset.getAction().getValue(Action.MNEMONIC_KEY)).intValue());
		bReset.addKeyListener(prefMgr.getKeyListener());

		btnPnl.add(bReset,BorderLayout.EAST);
		btnPnl.add(bSave,BorderLayout.WEST);	
	}
	private void initProfileTree() {
		Profile[] prof = generateProfileArray();
		if (prof != null) {
			ProfNode dmtn = null;
			ProfNode root = (ProfNode) wModel.getRoot();

			Arrays.sort(prof, new ProfComparator()); 

			for (int i = 0; i < prof.length; ++i) {
				if (dmtn == null || !dmtn.toString().equals(prof[i].getWGName())) {
					if (dmtn != null) {
						wTree.expandPath(new TreePath(dmtn.getPath()));
					}
					dmtn = new ProfNode(prof[i].getWGName());
					wModel.addNode(dmtn, root, root.getChildCount());
				}
				ProfNode tmp = new ProfNode(prof[i]);
				wModel.addNode(tmp, dmtn, dmtn.getChildCount());
			}
			userName = prof[0].getOPName(); 
			wTree.setSelectionModel(new PrefTreeSelectionModel());
			wTree.expandPath(new TreePath(dmtn.getPath()));
			wTree.treeDidChange();
			wTree.setDefaultPath();
		}
	}

	private Profile[] generateProfileArray() {
		ProfileSet ps = EACM.getEACM().getProfileSet();
		Profile[] prof = null;
		if (ps != null) {
			prof = ps.toArray();
		}

		return prof;
	}

	public JPanel getButtonPanel() {
		return btnPnl;
	}
	/**
	 * dereference 
	 * 
	 */
	public void dereference() {

		wModel.dereference();
		wScroll.removeAll();
		wScroll.setUI(null);

		// release buttons
		btnPnl.removeAll();
		btnPnl.setUI(null);
		SaveAction sa  = (SaveAction)bSave.getAction();
		sa.dereference();
		bSave.removeKeyListener(prefMgr.getKeyListener());
		bSave.setAction(null);
		bSave.setUI(null);

		ResetAction ra  = (ResetAction)bReset.getAction();
		ra.dereference();
		bReset.removeKeyListener(prefMgr.getKeyListener());
		bReset.setAction(null);
		bReset.setUI(null);

		removeAll();
		setUI(null);
		bSave = null;
		bReset = null;
		btnPnl =null;
		wModel = null;
		wTree.removeKeyListener(keyadapter);
		wTree.dereference();
		wTree = null;
		keyadapter = null;
		wScroll = null;
		userName = null;
		prefMgr = null;
	}

	private class ResetAction extends EACMAction {
		private static final long serialVersionUID = 1L;
		private static final String CMD = "reset.pref";
		ResetAction() {
			super(CMD,"Profile");
		}
		public void actionPerformed(ActionEvent e) {
			wTree.clearSelection(); 

			String enterprise = getDefaultProfileEnterprise(userName);
			if(enterprise!=null){
				SerialPref.removePref(userName + DEFAULT_ENTERPRISE); 
				SerialPref.removePref(userName + DEFAULT_OPWGID); 
				//Preferences.userNodeForPackage(PrefMgr.class).remove(userName + DEFAULT_ENTERPRISE); 
				//Preferences.userNodeForPackage(PrefMgr.class).remove(userName + DEFAULT_OPWGID); 
				//msg11017.0 = The Changes you made may not take effect until the next time you log in.
				com.ibm.eacm.ui.UI.showFYI(ProfilePref.this,Utils.getResource("msg11017.0"));
			}
		} 	
	}
	public static String getDefaultProfileEnterprise(String opname){
		//return Preferences.userNodeForPackage(PrefMgr.class).get(opname + DEFAULT_ENTERPRISE,null);
		return SerialPref.getPref(opname + DEFAULT_ENTERPRISE,null);
	}
	public static int getDefaultProfileOPWGID(String opname){
		//return Preferences.userNodeForPackage(PrefMgr.class).getInt(opname + DEFAULT_OPWGID,0);
		return SerialPref.getPref(opname + DEFAULT_OPWGID,0);
	}

	private class SaveAction extends EACMAction implements TreeSelectionListener{
		private static final long serialVersionUID = 1L;
		private static final String CMD = "save.pref";
		SaveAction() {
			super(CMD,"Profile");
			setEnabled(false);
		}
		public void dereference(){
			super.dereference();
			wTree.removeTreeSelectionListener(this);
		}
		public void actionPerformed(ActionEvent e) {
			Profile prof = wTree.getSelectedProfile(); 

			if (prof != null) { 
				String defEnterprise = getDefaultProfileEnterprise(userName);
				int defOpwgid = getDefaultProfileOPWGID(userName);
	
				boolean saveit = true;
				if (defEnterprise!=null){
					if (defOpwgid == prof.getOPWGID() &&
							defEnterprise.equals(prof.getEnterprise())) {
						saveit = false;
					}
				}
				if (saveit){
					SerialPref.putPref(userName+DEFAULT_ENTERPRISE, prof.getEnterprise());
					SerialPref.putPref(userName+DEFAULT_OPWGID, prof.getOPWGID());
					//prefMgr.getPrefNode().put(userName+DEFAULT_ENTERPRISE, prof.getEnterprise());
					//prefMgr.getPrefNode().putInt(userName+DEFAULT_OPWGID, prof.getOPWGID());
	
					bSave.setEnabled(false);
					//msg11017.0 = The Changes you made may not take effect until the next time you log in.
					com.ibm.eacm.ui.UI.showFYI(ProfilePref.this,Utils.getResource("msg11017.0"));
				}
			}
		}
		public void valueChanged(TreeSelectionEvent e) {
			bSave.setEnabled(wTree.getSelectedProfile()!=null);	
		} 	
	}

	private class PrefTree extends JTree implements ActionListener { 
		private static final long serialVersionUID = 1L;
		private JPopupMenu popup = null; 
		/**
		 * prefTree
		 * @param model
		 */
		private PrefTree(TreeModel model) {
			super(model);
			createPopupMenu();
			setShowsRootHandles(false);
			setCellRenderer(new TreeCellRend());// prevent folder icons
		}

		void dereference(){
			removePopupMenu();
			popup = null;
			removeAll();
			setUI(null);
		}
		/**
		 * createPopupMenu
		 */
		private void createPopupMenu() {
			if (popup == null) {
				popup = new JPopupMenu();
				setComponentPopupMenu(popup);
			}

			JMenuItem mi = new JMenuItem(Utils.getResource(EXPANDALL_ACTION));  
			mi.setActionCommand(EXPANDALL_ACTION);
			mi.addActionListener(this);
			popup.add(mi);
			mi = new JMenuItem(Utils.getResource(COLLAPSEALL_ACTION));  
			mi.setActionCommand(COLLAPSEALL_ACTION);
			mi.addActionListener(this);
			popup.add(mi);
		}
		/**
		 * @see javax.swing.JTree#setExpandedState(javax.swing.tree.TreePath, boolean)
		 * this prevents collapsing a node!!!/
        protected void setExpandedState(TreePath path, boolean state) {
            if (!state) {
                return;
            }
            super.setExpandedState(path, state);
        }

        /**
		 * setDefaultPath
		 */
		private void setDefaultPath() {
			Profile defProf = EACM.getEACM().getDefaultProfile();
			if (defProf != null) {
				ProfNode defNode = wModel.getNodeOPWGID(defProf.getOPWGID(), defProf.getEnterprise());
				if (defNode != null) {
					TreePath defPath = new TreePath(defNode.getPath());
					setSelectionPath(defPath);
					scrollPathToVisible(defPath);
				}
			}
		}

		/**
		 * getSelectedProfile
		 * @return
		 */
		private Profile getSelectedProfile() {
			Object o = getLastSelectedPathComponent();
			if (o instanceof ProfNode) {
				return ((ProfNode) o).getProfile();
			}
			return null;
		}

		/**
		 * remove the popop menu
		 */
		private void removePopupMenu() {
			for (int ii=0; ii<popup.getComponentCount(); ii++) {
				Component comp = popup.getComponent(ii);
				if (comp instanceof JMenuItem) {// separators are null
					JMenuItem mi = (JMenuItem)comp;
					mi.removeActionListener(this);
					EACM.closeMenuItem(mi);
				}  
			}
			popup.setUI(null);
			popup.removeAll();
		}
		/**
		 * actionPerformed
		 *
		 * @param ae
		 */
		public void actionPerformed(ActionEvent ae) {
			String str = ae.getActionCommand();
			if (str.equals(EXPANDALL_ACTION)) {
				expandAll(true);
			} else if (str.equals(COLLAPSEALL_ACTION)) {
				expandAll(false);
			}
		}
//		If expand is true, expands all nodes in the tree.
//		Otherwise, collapses all nodes in the tree.
		private void expandAll(boolean expand) {
			TreeNode root = (TreeNode)getModel().getRoot();

			TreePath parent = new TreePath(root);
			// Traverse tree from root
			//expandAll(new TreePath(root), expand); leave first level children visible
			// Traverse children
			TreeNode node = (TreeNode)parent.getLastPathComponent();
			if (node.getChildCount() >= 0) {
				for (Enumeration<?> e=node.children(); e.hasMoreElements(); ) {
					TreeNode n = (TreeNode)e.nextElement();
					TreePath path = parent.pathByAddingChild(n);
					expandAll(path, expand);
				}
			}
		}
		private void expandAll(TreePath parent, boolean expand) {
			// Traverse children
			TreeNode node = (TreeNode)parent.getLastPathComponent();
			if (node.getChildCount() >= 0) {
				for (Enumeration<?> e=node.children(); e.hasMoreElements(); ) {
					TreeNode n = (TreeNode)e.nextElement();
					TreePath path = parent.pathByAddingChild(n);
					expandAll(path, expand);
				}
			}

			// Expansion or collapse must be done bottom-up
			if (expand) {
				expandPath(parent);
			} else {
				collapsePath(parent);
			}
		}

	}

	private class PrefTreeSelectionModel extends DefaultTreeSelectionModel {
		private static final long serialVersionUID = 1L;

		/**
		 * @see javax.swing.tree.TreeSelectionModel#getSelectionMode()
		 */
		public int getSelectionMode() {
			return DefaultTreeSelectionModel.SINGLE_TREE_SELECTION;
		}

		/**
		 * @see javax.swing.tree.TreeSelectionModel#addSelectionPaths(javax.swing.tree.TreePath[])
		 */
		public void addSelectionPaths(TreePath[] paths) {}

		/**
		 * @see javax.swing.tree.TreeSelectionModel#setSelectionPath(javax.swing.tree.TreePath)
		 */
		public void setSelectionPath(TreePath path) {
			Object o = path.getLastPathComponent();
			if (o instanceof ProfNode) {
				if (!((ProfNode) o).isSelectable()) {
					return;
				}
			}
			super.setSelectionPath(path);
		}
	}

	private class PrefTreeModel extends DefaultTreeModel {
		private static final long serialVersionUID = 1L;
		private Hashtable<String,ProfNode> profLookUpTbl = new Hashtable<String,ProfNode>();
		/**
		 * prefTreeModel
		 * @param o
		 */
		private PrefTreeModel(TreeNode o) {
			super(o);
		}

		/**
		 * clear
		 */
		private void clear() {
			ProfNode root = (ProfNode) getRoot();
			if (root != null) {
				while (root.getChildCount() > 0) {
					removeNodeFromParent((MutableTreeNode) root.getFirstChild());
				}
			}
			profLookUpTbl.clear();
		}

		/**
		 * addNode
		 * @param child
		 * @param parent
		 * @param i
		 */
		private void addNode(ProfNode child, ProfNode parent, int i) {
			Object obj = child.getUserObject();
			if (obj instanceof Profile){
				String key = ((Profile)obj).getEnterprise()+((Profile)obj).getOPWGID();
				profLookUpTbl.put(key,child);
			}
			insertNodeInto(child, parent, i);
		}

		/**
		 * @param opwgid - are not unique across enterprises
		 * @param enterprise
		 * @return
		 */
		private ProfNode getNodeOPWGID(int opwgid, String enterprise) {
			return (ProfNode)profLookUpTbl.get(enterprise+opwgid);
		}

		/**
		 * dereference
		 */
		private void dereference() {
			clear();
			profLookUpTbl = null;
		}
	}

	private class ProfNode extends DefaultMutableTreeNode {
		private static final long serialVersionUID = 1L;

		/**
		 * profNode
		 * @param o
		 */
		private ProfNode(Object o){
			super(o);
		}

		/**
		 * isSelectable
		 * @return
		 */
		private boolean isSelectable() {
			return getUserObject() instanceof Profile;
		}

		/**
		 * getProfile
		 * @return
		 */
		private Profile getProfile() {
			if (isSelectable()) {
				return (Profile) getUserObject();
			}
			return null;
		}
	}

	/* (non-Javadoc)
	 * called when dialog is closing
	 * @see com.ibm.eacm.preference.Preferencable#isClosing()
	 */
	public void isClosing() {} 
	/* (non-Javadoc)
	 * notify user that changes have not been saved
	 * @see com.ibm.eacm.preference.Preferencable#hasChanges()
	 */
	public boolean hasChanges() { 
		boolean chgs = false;
		if (bSave!=null){
			chgs = bSave.isEnabled();
		}
		return chgs;
	}

	public void updateFromPrefs() {
	}
}
