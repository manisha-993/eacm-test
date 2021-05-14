//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.nav;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;

import COM.ibm.eannounce.objects.*;

import com.ibm.eacm.*;
import com.ibm.eacm.actions.*;
import com.ibm.eacm.mw.DBUtils;
import com.ibm.eacm.mw.HeavyWorker;
import com.ibm.eacm.mw.RMIMgr;
import com.ibm.eacm.objects.Findable;
import com.ibm.eacm.objects.FindableComp;
import com.ibm.eacm.objects.OutOfRangeException;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.preference.BehaviorPref;
import com.ibm.eacm.table.RSTTable;
import com.ibm.eacm.table.RSTTableModel;
import com.ibm.eacm.ui.EACMFrame;
import com.ibm.eacm.ui.UI;



/******************************************************************************
* This is used to display the link wizard frame.
* @author Wendy Stimpson
*/
//$Log: LinkWizard.java,v $
//Revision 1.6  2013/10/08 13:48:10  wendy
//scroll to row in next action
//
//Revision 1.5  2013/05/22 15:59:30  wendy
//use viewrow for LinkTable.highlight
//
//Revision 1.4  2013/03/14 18:28:32  wendy
//exec closeaction in finally block
//
//Revision 1.3  2013/03/14 17:31:42  wendy
//disable navigate while link is running
//
//Revision 1.2  2013/03/12 18:12:11  wendy
//put link in worker
//
//Revision 1.1  2012/09/27 19:39:14  wendy
//Initial code
//
public class LinkWizard extends EACMFrame implements FindableComp
{
	private static final long serialVersionUID = 1L;
	/** object copyright statement */
	public static final String COPYRIGHT="(C) Copyright IBM Corp. 2012  All Rights Reserved.";
	/** cvs version */
	public static final String VERSION = "$Revision: 1.6 $";

	private static final String NEXT = "next";
	private static final String PREV = "prev";
	private static final String FINISH ="fin";

	private JPanel pnlMain = new JPanel(null);
	private JPanel pnlBtn = new JPanel(new GridLayout(1, 4, 2, 2));
	private JPanel pNorth = new JPanel(new BorderLayout());
	private JLabel lblFilter = new JLabel(Utils.getImageIcon("fltr.gif"));
	private JLabel lblDirection = new JLabel();
	private JButton btnCancel, btnNext, btnPrev, btnFinish;
	private int curIndex = -1;
	private LinkActionItem linkAction = null;
	private Navigate nav = null;
	private String msgPrefix="";
	private Component[] view = null;
	private JScrollPane jsp = null;

	/**
	 */
	public LinkWizard()  {
		super("wizard.panel");

		closeAction = new CloseAction(this);

		init();

		finishSetup(EACM.getEACM());

		setResizable(true);
	}


	/**
	 * init frame components
	 */
	private void init() {
		Dimension d = new Dimension(300, 400);

		createActions();

		createMenuBar();

		btnCancel = new JButton(closeAction);
		btnNext = new JButton(getAction(NEXT));
		btnPrev = new JButton(getAction(PREV));
		btnFinish = new JButton(getAction(FINISH));

		btnCancel.setMnemonic((char)((Integer)btnCancel.getAction().getValue(Action.MNEMONIC_KEY)).intValue());
		btnNext.setMnemonic((char)((Integer)btnNext.getAction().getValue(Action.MNEMONIC_KEY)).intValue());
		btnPrev.setMnemonic((char)((Integer)btnPrev.getAction().getValue(Action.MNEMONIC_KEY)).intValue());
		btnFinish.setMnemonic((char)((Integer)btnFinish.getAction().getValue(Action.MNEMONIC_KEY)).intValue());


		lblFilter.setEnabled(false);
		lblFilter.setToolTipText(Utils.getToolTip("linkFilterLbl"));
		lblFilter.setHorizontalAlignment(SwingConstants.CENTER);
		lblFilter.setVerticalAlignment(SwingConstants.CENTER);

		jsp = new JScrollPane();
		jsp.setFocusable(false);
		jsp.setPreferredSize(d);
		jsp.setSize(d);

		pnlBtn.add(btnPrev);
		pnlBtn.add(btnNext);
		pnlBtn.add(btnCancel);
		pnlBtn.add(btnFinish);

		pNorth.add(lblFilter,BorderLayout.NORTH);
		pNorth.add(lblDirection,BorderLayout.SOUTH);

		GroupLayout layout = new GroupLayout(pnlMain);
		pnlMain.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		GroupLayout.ParallelGroup leftToRight = layout.createParallelGroup();
		leftToRight.addComponent(pNorth, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);// this centers it
		leftToRight.addComponent(jsp);
		leftToRight.addComponent(pnlBtn);

		GroupLayout.SequentialGroup topToBottom = layout.createSequentialGroup();
		topToBottom.addComponent(pNorth);
		topToBottom.addComponent(jsp);
		topToBottom.addComponent(pnlBtn);

		layout.setHorizontalGroup(leftToRight);
		layout.setVerticalGroup(topToBottom);
		getContentPane().add(pnlMain);
	}
	/**
	 * @param ai
	 * @param ei
	 * @param eg1
	 * @param eg2
	 * @param n
	 * @return
	 */
	public boolean updateWizard(LinkActionItem ai, EntityItem[] ei, EntityGroup eg1,EntityGroup eg2,Navigate n)  {
		nav = n;
		linkAction = ai;
		curIndex = 0;

		EntityItem[] ei2 = ei;
		String strEntity1 = null;
		String strEntity2 = null;

		MetaLink mLink = linkAction.getMetaLink();
		if (linkAction.useCart() && nav.isMultipleNavigate()) {
			ei2 = getOpposingEntityItems();
		}
		linkAction.setCheckExistence(true);
		if (linkAction.isOppSelect()) {
			msgPrefix="msg11007wp.";
			strEntity1 = mLink.getEntity2Type();
			strEntity2 = mLink.getEntity1Type();
		} else {
			msgPrefix ="msg11007w.";
			strEntity1 = mLink.getEntity1Type();
			strEntity2 = mLink.getEntity2Type();
		}

		if(view !=null){
			for(int i=0;i<view.length; i++){
				if (view[i] instanceof LinkTable){
					((LinkTable)view[i]).dereference();
				}else if(view[i] instanceof LinkTypeChooser){
					((LinkTypeChooser)view[i]).dereference();
				}
				view[i]=null;
			}
			view = null;
		}

		if (linkAction.canLink() && linkAction.canLinkCopy()) {
			view = new Component[3];
			view[2] = new LinkTypeChooser();
		} else {
			view = new Component[2];
		}
		view[0] = new LinkTable(eg1);

		((LinkTable) view[0]).highlight(ei, strEntity1);
		view[1] = new LinkTable(eg2);
		if (ei2 != null) {
			((LinkTable) view[1]).highlight(ei2, strEntity2);
		}

		if (linkAction.useParents()) {
			curIndex=0;
		} else {
			curIndex=1;
		}
		setView(view[curIndex]);

		if (Utils.isArmed(AUTOLINK_ARM_FILE)) {
			Logger.getLogger(NAV_PKG_NAME).log(Level.FINER,"autoLinks: true");
			if (linkAction.isOppSelect()) {
				if (shouldAutoLink(ei2, ei)) {
					nav.disableActionsAndWait();
					if (linkAction.useCart()) {
						((LinkTable) view[0]).selectAll();
					} else if (linkAction.useParents()) {
						((LinkTable) view[1]).selectAll();
					}
					if (getLinkType() == LinkActionItem.TYPE_COPY) {
						finish(com.ibm.eacm.ui.UI.showIntSpinner(null,Utils.getResource("msg3013"),1, 1, 99, 1));
					} else {
						finish(0);
					}
					return false;
				}
			} else {
				if (shouldAutoLink(ei, ei2)) {
					nav.disableActionsAndWait();
					if (linkAction.useCart()) {
						((LinkTable) view[1]).selectAll();
					} else if (linkAction.useParents()) {
						((LinkTable) view[0]).selectAll();
					}
					if (getLinkType() == LinkActionItem.TYPE_COPY) {
						finish(com.ibm.eacm.ui.UI.showIntSpinner(null,Utils.getResource("msg3013"),1, 1, 99, 1));
					} else {
						finish(0);
					}
					return false;
				}
			}
		}

		updateDirectionLabel();
		return true;
	}
	
	private boolean closeWhenDone = false;
	/**
	 * need a way to delay closing when finish() is using a worker
	 */
	protected void closeWhenLinkIsDone(){
		closeWhenDone = true;
	}
	private boolean shouldAutoLink(EntityItem[] _parent, EntityItem[] _child) {
		if (_parent != null && _child != null) {
			return (_parent.length == 1);
		}
		return false;
	}
	private int getLinkType() {
		if (view.length == 3) {
			return ((LinkTypeChooser) view[2]).getLinkType();
		} else {
			if (linkAction.canLink()) {
				return LinkActionItem.TYPE_DEFAULT;
			} else {
				return LinkActionItem.TYPE_COPY;
			}
		}
	}
	private void finish(int _copies) {
		Object[] o1 = null;
		Object[] o2 = null;

		try {
			o1 = ((LinkTable) view[0]).getSelectedEntityItems(false, true);
			o2 = ((LinkTable) view[1]).getSelectedEntityItems(false, true);
		} catch (OutOfRangeException _range) {
			_range.printStackTrace();
			com.ibm.eacm.ui.UI.showErrorMessage(this,_range.toString());
			return;
		}
		int iLinkType = getLinkType();
		linkAction.setLinkType(iLinkType);
		if (iLinkType == LinkActionItem.TYPE_COPY) {
			if (_copies <= 0) {
				return;
			}
			linkAction.setCopyCount(_copies);
		}
		if (o1 instanceof EntityItem[] && o2 instanceof EntityItem[]) {
			EntityItem[] eiParent = (EntityItem[]) o1;
			EntityItem[] eiChild = (EntityItem[]) o2;
			MetaLink mLink = null;
			//Object o = null;
			String out = null;
			setOptions(BehaviorPref.getLinkTypeKey());
			mLink = linkAction.getMetaLink();
			if (linkAction.isOppSelect()) {
				out = reportLink(eiChild, eiParent, mLink);
			} else {
				out = reportLink(eiParent, eiChild, mLink);
			}
			if (linkAction.isOppSelect()) {
				linkAction.setParentEntityItems(eiChild);
				linkAction.setChildEntityItems(eiParent);
			} else {
				linkAction.setParentEntityItems(eiParent);
				linkAction.setChildEntityItems(eiChild);
			}
			
			worker = new LinkWorker(out);
			RMIMgr.getRmiMgr().execute(worker);
			/* this should really be in a DBSwingWorker, but ran out of time to do it
			o = DBUtils.doLinkAction(linkAction, nav.getProfile(), this);
			if (o != null) {
				if (o instanceof Boolean) {
					if (!((Boolean) o).booleanValue()) {
						return;
					}
				}
			} else {
				return;
			}

			if (isShowing()) {
				UI.showLinkResults(this,out);
			} else {
				UI.showLinkResults(null, out);
			}
			nav.getNavController().setShouldRefresh(true);

			if (o instanceof EntityList && linkAction.hasChainAction()) {
				if (!linkAction.requireInput()) {
					if (linkAction.isChainEditAction()) {
						EACM.getEACM().loadFromLink(nav.getNavController(), (EntityList)o);
					} else if (linkAction.isChainWhereUsedAction()) {
						EACM.getEACM().loadFromLink(nav.getNavController(), (EntityList)o);
					} else if (linkAction.isChainMatrixAction()) {
						EACM.getEACM().loadFromLink(nav.getNavController(), (EntityList)o);
					}
				}
			}
			if (isShowing()) {
				closeAction.actionPerformed(null);
			}
			*/
		} else {
			com.ibm.eacm.ui.UI.showErrorMessage(this,Utils.getResource("msg12010.0"));
		}

	}
	private LinkWorker worker = null;
	
	private class LinkWorker extends HeavyWorker<Object, Void> {
		private String out = null;
		LinkWorker(String s){
			out = s;
		}
		@Override
		public Object doInBackground() {
			Object o = null;
			try{
				o = DBUtils.doLinkAction(linkAction, nav.getProfile(),LinkWizard.this);
			}catch(Exception ex){ // prevent hang
				Logger.getLogger(WU_PKG_NAME).log(Level.SEVERE,"Exception "+ex,ex);
			}finally{
				RMIMgr.getRmiMgr().complete(this);
			 	if(isCancelled()){
					LinkWizard.this.enableActionsAndRestore();
					nav.enableActionsAndRestore();
					if(closeWhenDone){
						closeWhenDone = false;
						closeAction.actionPerformed(null);
					}
			 	}
			
				worker = null;
			}
			return o;
		}

		@Override
		public void done() {
			//this will be on the dispatch thread if not cancelled before
			try {
				if(!isCancelled()){
					Object o = get();
					if (o != null) {
						if (o instanceof Boolean) {
							if (!((Boolean) o).booleanValue()) {
								return;
							}
						}
					} else {
						return;
					}
					if (isShowing()) {
						UI.showLinkResults(LinkWizard.this,out);
					} else {
						UI.showLinkResults(null, out);
					}
					nav.getNavController().setShouldRefresh(true);

					if (o instanceof EntityList && linkAction.hasChainAction()) {
						if (!linkAction.requireInput()) {
							if (linkAction.isChainEditAction()) {
								EACM.getEACM().loadFromLink(nav.getNavController(), (EntityList)o);
							} else if (linkAction.isChainWhereUsedAction()) {
								EACM.getEACM().loadFromLink(nav.getNavController(), (EntityList)o);
							} else if (linkAction.isChainMatrixAction()) {
								EACM.getEACM().loadFromLink(nav.getNavController(), (EntityList)o);
							}
						}
					}
					if (isShowing()) {
						closeWhenDone = true;
					}
				}
			} catch (InterruptedException ignore) {}
			catch (java.util.concurrent.ExecutionException e) {
				listErr(e,"linking items");
			}finally{
				LinkWizard.this.enableActionsAndRestore();
				nav.enableActionsAndRestore();
				out = null;
				if(closeWhenDone){
					closeWhenDone = false;
					closeAction.actionPerformed(null);
				}
			}
		}
		public void notExecuted(){
			Logger.getLogger(NAV_PKG_NAME).log(Level.WARNING,"not executed");
			LinkWizard.this.enableActionsAndRestore();
			nav.enableActionsAndRestore();
			worker = null;
		}
	}

	private void setOptions(String _s) {
		if (_s.equals("NODUPES")) {
			linkAction.setOption(LinkActionItem.OPT_NODUPES);
		} else if (_s.equals("REPLACEALL")) {
			linkAction.setOption(LinkActionItem.OPT_REPLACEALL);
		} else {
			linkAction.setOption(LinkActionItem.OPT_DEFAULT);
		}
	}
	/**
	 * create all of the actions
	 */
	private void createActions() {
		addAction(new NextAction());

		addAction(new PrevAction());
		addAction(new FinishAction());

		addAction(new FilterAction(this,null));
		addAction(new SortAction(this,null));

		//SELECTALL_ACTION
		addAction(new SelectAllAction(null));
		//SELECTINV_ACTION
		addAction(new SelectInvertAction(null));
		addAction(new FindRepAction());
		addAction(new FindNextAction());
	}
	/**
	 * createMenuBar
	 */
	private void createMenuBar() {
		menubar = new JMenuBar();
		createFileMenu();
		createEditMenu();
		createTableMenu();

		setJMenuBar(menubar);
	}

	private void createEditMenu(){
		JMenu mnuTbl = new JMenu(Utils.getResource(EDIT_MENU));
		mnuTbl.setMnemonic(Utils.getMnemonic(EDIT_MENU));

		addLocalActionMenuItem(mnuTbl, SELECTALL_ACTION);
		addLocalActionMenuItem(mnuTbl, SELECTINV_ACTION);
		mnuTbl.addSeparator();

		addLocalActionMenuItem(mnuTbl, FINDREP_ACTION);
		addLocalActionMenuItem(mnuTbl, FINDNEXT_ACTION);

		menubar.add(mnuTbl);
	}
	private void createTableMenu(){
		JMenu mnuTbl = new JMenu(Utils.getResource(TABLE_MENU));
		mnuTbl.setMnemonic(Utils.getMnemonic(TABLE_MENU));

		addLocalActionMenuItem(mnuTbl, SORT_ACTION);
		addLocalActionMenuItem(mnuTbl, FILTER_ACTION);

		menubar.add(mnuTbl);
	}
	private void createFileMenu() {
		JMenu mnuFile = new JMenu(Utils.getResource(FILE_MENU));
		mnuFile.setMnemonic(Utils.getMnemonic(FILE_MENU));

		JMenuItem mi = mnuFile.add(closeAction);
		mi.setVerifyInputWhenFocusTarget(false);

		menubar.add(mnuFile);
	}
	private EntityItem[] getOpposingEntityItems() {
		EntityItem[] ei = null;
		Navigate nav2 = nav.getOpposingNavigate();
		if (nav2 != null) {
			try {
				ei = nav2.getSelectedEntityItems(false, false);
			} catch (OutOfRangeException e) {
				com.ibm.eacm.ui.UI.showFYI(this,e);
			}
		}
		return ei;
	}
	/**
	 * release memory
	 */
	public void dereference() {
		super.dereference();

		btnCancel.setAction(null);
		btnCancel.removeAll();
		btnCancel.setUI(null);
		btnCancel = null;

		btnNext.setAction(null);
		btnNext.removeAll();
		btnNext.setUI(null);
		btnNext = null;

		btnFinish.setAction(null);
		btnFinish.removeAll();
		btnFinish.setUI(null);
		btnFinish = null;

		btnPrev.setAction(null);
		btnPrev.removeAll();
		btnPrev.setUI(null);
		btnPrev = null;

		linkAction = null;
		lblFilter = null;
		msgPrefix = null;

		lblDirection.setLabelFor(null);
		lblDirection = null;

		if(view !=null){
			for(int i=0;i<view.length; i++){
				if (view[i] instanceof LinkTable){
					((LinkTable)view[i]).dereference();
				}else if(view[i] instanceof LinkTypeChooser){
					((LinkTypeChooser)view[i]).dereference();
				}

				view[i]=null;
			}
			view = null;
		}

		pNorth.removeAll();
		pNorth.setUI(null);
		pNorth = null;

		pnlMain.removeAll();
		pnlMain.setUI(null);
		pnlMain = null;

		pnlBtn.removeAll();
		pnlBtn.setUI(null);
		pnlBtn = null;

		jsp.removeAll();
		jsp.setUI(null);
		jsp = null;

		nav = null;
	}
    /**
     * enable label
     * @param _filter
     */
    public void setFilter(boolean _filter) {
    	lblFilter.setEnabled(_filter);
    }

	private class CloseAction extends CloseFrameAction
	{
		private static final long serialVersionUID = 1L;
		CloseAction(EACMFrame f) {
			super(f,CANCEL_ACTION);
		}
	}
	private void setView(Component _c) {
		jsp.setViewportView(_c);
		jsp.revalidate();
		lblDirection.setLabelFor(_c);
		LinkTable table = null;
		if(_c instanceof LinkTable){
			table = (LinkTable)_c;
			int selectedRows[] = table.getSelectedRows();
			if(selectedRows.length>0){
				table.scrollToRow(selectedRows[0]);
			}
		}
		updateTableActions(table);
	}
	private void updateTableActions(LinkTable table) {
		EACMAction act = getAction(SELECTALL_ACTION);
		if (act!=null){
			((SelectAllAction)act).setTable(table);
		}
		act = getAction(SELECTINV_ACTION);
		if (act!=null){
			((SelectInvertAction)act).setTable(table);
		}
		//SORT_ACTION
		act = getAction(SORT_ACTION);
		if (act!=null){
			((SortAction)act).setSortable(table);
		}
		//FILTER_ACTION
		act = getAction(FILTER_ACTION);
		if (act!=null){
			((FilterAction)act).setFilterable(table);
		}
	}
	private void updateDirectionLabel(){
		String parm = "";

		if (linkAction.canLink()) {
			parm= Utils.getResource("lnk");
		} else if (linkAction.canLinkCopy()) {
			parm= Utils.getResource("copylink");
		}

		String msg= Utils.getResource(msgPrefix + curIndex,parm);
		lblDirection.setText(msg);

		enableActions();
	}
	private class NextAction extends EACMAction
	{
		private static final long serialVersionUID = 1L;
		NextAction() {
			super(NEXT);
		}
		/**
		 * derived classes should override for conditional checks needed before enabling
		 * @return
		 */
		protected boolean canEnable(){
			return view != null && curIndex<view.length-1;
		}

		public void actionPerformed(ActionEvent e) {
			setView(view[++curIndex]);
			updateDirectionLabel();
		}
	}
	private class PrevAction extends EACMAction
	{
		private static final long serialVersionUID = 1L;
		PrevAction() {
			super(PREV);
		}
		/**
		 * derived classes should override for conditional checks needed before enabling
		 * @return
		 */
		protected boolean canEnable(){
			return view != null && curIndex>0;
		}

		public void actionPerformed(ActionEvent e) {
			setView(view[--curIndex]);
			updateDirectionLabel();
		}
	}
	private class FinishAction extends EACMAction
	{
		private static final long serialVersionUID = 1L;
		FinishAction() {
			super(FINISH);
		}
		/**
		 * derived classes should override for conditional checks needed before enabling
		 * @return
		 */
		protected boolean canEnable(){
			return view != null && curIndex==view.length-1;
		}

		public void actionPerformed(ActionEvent e) {
			LinkWizard.this.disableActionsAndWait();
			nav.disableActionsAndWait();
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					int iCopy = 0;

					if (getLinkType()==LinkActionItem.TYPE_COPY) {
						iCopy = com.ibm.eacm.ui.UI.showIntSpinner(null,Utils.getResource("msg3013"),1, 1, 99, 1);
					}

					finish(iCopy);
				//	LinkWizard.this.enableActionsAndRestore();
				}
			});

		}
	}
	private class LinkTable extends RSTTable {
		private static final long serialVersionUID = 1L;

		LinkTable(EntityGroup group) {
			super(group.getEntityGroupTable(),group.getProfile());
			init();
		}

		/**
		 * init
		 *
		 */
		protected void init() {
			super.init();
			setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		}
		/**
		 * get the accessibility resource key
		 * @return
		 */
		protected String getAccessibilityKey() {
			return "accessible.linkTable";
		}
		void highlight(EntityItem[] ei, String strEntity) {
			boolean bClear = true;
			for (int i = 0; i < ei.length; ++i) {
				String key = getKey(ei[i], strEntity);
				int r = ((RSTTableModel)getModel()).getModelRowIndex(key);
				if (r >= 0) {
					int viewrow = this.convertRowIndexToView(r);
					if (bClear) {
						bClear = false;
						clearSelection();
						scrollToRow(viewrow);
					}
	
					addRowSelectionInterval(viewrow, viewrow);
				}
			}
			if(bClear){
				// nothing found, so just select first row
				clearSelection();
				scrollToRow(0);
				addRowSelectionInterval(0, 0);
			}
		}
	    private String getKey(EntityItem _ei, String _strEntity) {
	        if (_strEntity != null) {
	            if (_ei.hasDownLinks()) {
	                EANEntity eanDown = _ei.getDownLink(0);
	                String strDown = eanDown.getEntityType();
	                if (strDown != null) {
	                    if (strDown.equals(_strEntity)) {
	                        return eanDown.getKey();
	                    }
	                }
	            }
	            if (_ei.hasUpLinks()) {
	                EANEntity eanUp = _ei.getUpLink(0);
	                String strUp = eanUp.getEntityType();
	                if (strUp != null) {
	                    if (strUp.equals(_strEntity)) {
	                        return eanUp.getKey();
	                    }
	                }
	            }
	        }
	        return _ei.getKey();
	    }
	}
	private String reportLink(EntityItem[] _eiParent, EntityItem[] _eiChild, MetaLink _rel) {
		if (_eiParent == null || _eiChild == null) {
			return null;
		}

		StringBuffer sb = new StringBuffer();
		String msg = " " + Utils.getResource("linked2") + " ";

		for (int p = 0; p < _eiParent.length; ++p) {
			for (int c = 0; c < _eiChild.length; ++c) {
				EntityGroup eg =_eiChild[c].getEntityGroup();
				sb.append(getParentString(_eiParent[p], _rel) + msg +
						eg.getLongDescription()+" "+_eiChild[c].toString() + NEWLINE);
				Logger.getLogger(NAV_PKG_NAME).log(Level.FINER,"       to "+_eiChild[c].getKey());
			}
		}
		return sb.toString();
	}
	/**
	 * build parent information for the link message
	 * @param _ei
	 * @param _rel
	 * @return
	 */
	private String getParentString(EntityItem _ei, MetaLink _rel) {
		String e1Type = null;
		EntityItem ent = null;

		if (_ei == null) {
			Logger.getLogger(NAV_PKG_NAME).log(Level.FINER,"Linked parent");
			return "parent";
		}
		EntityGroup eg = _ei.getEntityGroup();
		if (_rel == null) {
			Logger.getLogger(NAV_PKG_NAME).log(Level.FINER,"Linked "+_ei.getKey());
			return eg.getLongDescription()+" "+_ei.toString();
		}
		e1Type = _rel.getEntity1Type();
		if (e1Type != null) {
			String eType_ei = _ei.getEntityType();
			if (eType_ei.equals(e1Type)) {
				Logger.getLogger(NAV_PKG_NAME).log(Level.FINER,"Linked "+_ei.getKey());
				return eg.getLongDescription()+" "+_ei.toString();
			}

			ent = (EntityItem)_ei.getDownLink(0);
			if (ent != null) {
				eg = ent.getEntityGroup();
				String eType_ent = ent.getEntityType();
				if (eType_ent.equals(e1Type)) {
					Logger.getLogger(NAV_PKG_NAME).log(Level.FINER,"Linked "+ent.getKey());
					return eg.getLongDescription()+" "+ent.toString();
				}
			}
		}

		Logger.getLogger(NAV_PKG_NAME).log(Level.FINER,"Linked "+_ei.getKey());
		return eg.getLongDescription()+" "+_ei.toString();
	}
	private class LinkTypeChooser extends JPanel {
		private static final long serialVersionUID = 1L;
		private JComboBox eBox = new JComboBox();
		// chooseLink =Choose link Type:
		private JLabel eLbl = new JLabel(Utils.getResource("chooseLink"));
		private int[] out = new int[]{LinkActionItem.TYPE_DEFAULT,LinkActionItem.TYPE_COPY};
		//copylink = Copy & Link
		private String[] str = { Utils.getResource("link"), Utils.getResource("copylink") };

		LinkTypeChooser() {
			for (int i = 0; i < out.length; ++i) {
				eBox.addItem(str[i]);
			}
			eLbl.setLabelFor(eBox);
			add(eLbl);
			add(eBox);
		}

		void dereference(){
			eLbl.setLabelFor(null);
			eLbl = null;
			eBox.removeAllItems();
			str = null;
			out = null;
			removeAll();
		}

		/**
		 * getLinkType
		 * @return
		 */
		int getLinkType() {
			int indx = eBox.getSelectedIndex();
			if (indx >= 0 && indx < out.length) {
				return out[indx];
			}
			return indx;
		}
	}
	public Findable getFindable() {
		if(	jsp.getViewport().getView() instanceof LinkTable){
			return (LinkTable)jsp.getViewport().getView();
		}
		return null;
	}
}