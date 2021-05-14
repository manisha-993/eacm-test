// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.ui;

import java.awt.Color;
import java.awt.Component;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;

import COM.ibm.eannounce.objects.*;

import com.ibm.eacm.objects.EACMGlobals;
import com.ibm.eacm.objects.OutOfRangeException;
import com.ibm.eacm.objects.Routines;
import com.ibm.eacm.preference.BehaviorPref;
import com.ibm.eacm.table.EntityGroupTable;
import com.ibm.eacm.table.RSTTableModel;
import com.ibm.eacm.tabs.CloseTabComponent;



/**
 * this represents an entitylist as a tabbedpane, one entitygroup per tab
 *
 * @author Wendy Stimpson
 */
//$Log: EntityListTabbedPane.java,v $
//Revision 1.4  2014/05/01 19:35:15  wendy
//Add tablayoutpolicy to support scroll of action tabbed pane
//
//Revision 1.3  2013/09/19 22:12:24  wendy
//control sort when a row is updated
//
//Revision 1.2  2013/05/01 18:35:13  wendy
//perf updates for large amt of data
//
//Revision 1.1  2012/09/27 19:39:11  wendy
//Initial code
//
public class EntityListTabbedPane extends JTabbedPane implements RowSorterListener, EACMGlobals
{
	private static final long serialVersionUID = 1L;
	private EntityList entityList = null;
	private EntityGroup peg = null; // parent entitygroup
	private MouseListener mouseListener = null;
	private ListSelectionListener selectListener = null;
	private ActionListener closeTabListener = null;

	/**
	 * used by navdatasingle and pickframes
	 * @param mouse
	 * @param sl
	 */
	public EntityListTabbedPane(MouseListener mouse,ListSelectionListener sl)
	{
		mouseListener = mouse;
		selectListener = sl;
		setTabPlacement(BehaviorPref.getTabPlacement());
	}
	/**
	 * used by the navcartframe
	 * @param mouse
	 * @param sl
	 * @param ctl
	 */
	public EntityListTabbedPane(MouseListener mouse,ListSelectionListener sl,
			ActionListener ctl)
	{
		mouseListener = mouse;
		selectListener = sl;
		closeTabListener = ctl;
		setTabPlacement(BehaviorPref.getTabPlacement());
	}
	/**
	 * do this in a separate step so listeners are ready when tabs are created
	 * @param eList
	 */
	public void load(EntityList eList){
		entityList = eList; // do not deref previous list, if used in navigate, then the NSO will deref, otherwise caller must handle deref
		if(entityList.isABRStatus()){
			loadABRView();
		}else{
			load();
		}
	}

	/**
	 * @return
	 */
	public EntityList getEntityList(){
		return entityList;
	}

	/**
	 * remove selected items from the current tab
	 * @return count of items left in the group
	 */
	public int removeSelectedItems(){
		// find the selected table
		EntityGroupTable egt = getTable(getSelectedIndex());
		int cnt = egt.removeSelectedItems();
		if(closeTabListener !=null){
			CloseTabComponent tabcomp = (CloseTabComponent)getTabComponentAt(getSelectedIndex());
			tabcomp.setTitle(getTitle(egt.getEntityGroup(),egt)); // show new count
		}else{
			setTitleAt(getSelectedIndex(),getTitle(egt.getEntityGroup(),egt)); // show new count
		}
		setToolTipTextAt(getSelectedIndex(),getTip(egt.getEntityGroup(),egt));
		return cnt;
	}
	/**
	 * remove items from the specified group
	 * @param eg
	 * @param ei
	 * @return count of items left in the group
	 */
	public int removeItems(EntityGroup eg, EntityItem[] ei){
		int numleft = 0;
		// find the table for this group
		for (int i=0; i< getTabCount(); i++){
			EntityGroupTable egt = getTable(i);
			if(egt.getEntityGroup().equals(eg)){
				FilterGroup fg = egt.getFilterGroup();
				egt.resetFilter(); // show all
				for (int e=0; e<egt.getModel().getRowCount(); e++){
					for (int x=0; x<ei.length; x++){
						int row = ((RSTTableModel)egt.getModel()).getRowIndex(ei[x].getKey());
						egt.setRowSelectionInterval(row,row);
					}
				}
				numleft= egt.removeSelectedItems();
				egt.setFilterGroup(fg); // restore filtergroup
				egt.filter();
				if(closeTabListener !=null){
					CloseTabComponent tabcomp = (CloseTabComponent)getTabComponentAt(i);
					tabcomp.setTitle(getTitle(egt.getEntityGroup(),egt)); // show new count
				}else{
					setTitleAt(i,getTitle(egt.getEntityGroup(),egt)); // show new count
				}
				setToolTipTextAt(i,getTip(egt.getEntityGroup(),egt));
				break;
			}
		}
		return numleft;
	}

	/**
	 * find tab with this key and remove it
	 * @param s
	 */
	public void removeTab(String s) {
		int indx = getIndexOf(s);
		if (indx >= 0) {
			EntityGroupTable egt = getTable(indx);
			egt.getSelectionModel().removeListSelectionListener(selectListener);
			egt.dereference();

			removeTabAt(indx);
		}
		if (getTabCount() > 0) {
			setSelectedIndex(0);
		}
	}

	/**
	 * @param bnew
	 * @param bEx
	 * @return
	 * @throws OutOfRangeException
	 */
	public EntityItem[] getSelectedEntityItems(boolean bnew, boolean bEx) throws OutOfRangeException {
		return getSelectedEntityItems(getSelectedIndex(), bnew, bEx);
	}
	/**
	 * getSelectedEntityItems
	 *
	 * @param i
	 * @param bnew
	 * @param bEx
	 * @throws com.ibm.eacm.objects.OutOfRangeException
	 * @return
	 */
	public EntityItem[] getSelectedEntityItems(int i, boolean bnew, boolean bEx) throws OutOfRangeException {
		if (i != -1) {
			EntityGroupTable egt = getTable(i);
			return egt.getSelectedEntityItems(bnew, bEx);
		}
		return null;
	}
	/**
	 * find the index to insert this new 'title' in sort order
	 * @param title
	 * @return
	 */
	private int getIndexFor(String title) {
		int iLower = 0;

		for (int i=0;i<getTabCount();++i) {
			EntityGroup eg = getEntityGroup(i);
			int iCur = title.compareToIgnoreCase(eg.toString());
			if (iCur == 0) {
				return i;
			} else if (iCur > 0) {
				iLower = i + 1;
			} else if (iCur < 0) {
				return iLower;
			}
		}
		return getTabCount();
	}

	/**
	 * find the tab with this entitygroup, refresh if found
	 * else create a new tab with this group
	 * @param eg
	 */
	public void refreshTab(EntityGroup eg) {
		int index = getIndexOf(eg.getKey());
		if (index >= 0) {
			EntityGroupTable egt = getTable(index);
			egt.refreshModel(eg); // it is the same entitygroup in the cart, but has more entities now
			if(closeTabListener !=null){
				CloseTabComponent tabcomp = (CloseTabComponent)getTabComponentAt(index);
				tabcomp.setTitle(getTitle(eg,egt)); // show new count
			}else{
				setTitleAt(index,getTitle(eg,egt)); // show new count
			}
			setToolTipTextAt(index,getTip(eg,egt));
			setSelectedIndex(index);
		} else {
			int i = getIndexFor(eg.toString());
			addTab(eg,i);
			setSelectedIndex(i);
		}
	}

	/**
	 * check for any selected tab and any selected row on that tab
	 * @return
	 */
	public boolean hasSelection(){
		int index = getSelectedIndex();
		if (index == -1) {
			return false;
		}
		return ((EntityGroupTable)(((JScrollPane)getComponentAt(index)).getViewport().getView())).getSelectedRow()!= -1;
	}

	/**
	 * check for any selected tab and any data
	 * @return
	 */
	public boolean hasData(){
		int index = getSelectedIndex();
		if (index == -1) {
			return false;
		}
		return ((EntityGroupTable)(((JScrollPane)getComponentAt(index)).getViewport().getView())).getRowCount()>0;
	}

	/**
	 * get the entitygroup of the currently selected tab
	 * @return
	 */
	public EntityGroup getEntityGroup() {
		return getEntityGroup(getSelectedIndex());
	}
	/**
	 * find the index of the tab with this entitygroup key
	 * @param s
	 * @return
	 */
	private int getIndexOf(String s) {
		for (int i=0;i<getTabCount();++i) {
			EntityGroup eg = getEntityGroup(i);
			if (eg.getKey().equals(s)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * get the table for the selected tab
	 * @return
	 */
	public EntityGroupTable getTable() {
		return getTable(getSelectedIndex());
	}
	/**
	 * get the table at this tab index
	 * @param i
	 * @return
	 */
	public EntityGroupTable getTable(int i) {
		if(i>=0 && i<getTabCount()){
			Component c = getComponentAt(i);
			if (c instanceof JScrollPane) {
				return (EntityGroupTable)(((JScrollPane)c).getViewport().getView());
			}
		}
		return null;
	}

	/**
	 * entityitems were updated after an edit, so refresh the table
	 */
	public void updateTable(){
		for (int i=0; i<getTabCount(); i++){
			Component c = getComponentAt(i);
			if (c instanceof JScrollPane) {
				boolean wasFiltered = ((EntityGroupTable)(((JScrollPane)c).getViewport().getView())).isFiltered();
				((EntityGroupTable)(((JScrollPane)c).getViewport().getView())).updateTable();
				if(wasFiltered){
					 ((EntityGroupTable)(((JScrollPane)c).getViewport().getView())).filter();
				}
			}
		}
	}
	/**
	 * entityitems were updated after an edit, so refresh the table
	 */
	public void updateTableWithSelectedRows(){
		for (int i=0; i<getTabCount(); i++){
			Component c = getComponentAt(i);
			if (c instanceof JScrollPane) {
				boolean wasFiltered = ((EntityGroupTable)(((JScrollPane)c).getViewport().getView())).isFiltered();
				((EntityGroupTable)(((JScrollPane)c).getViewport().getView())).updateTableWithSelectedRows();
				if(wasFiltered){
					 ((EntityGroupTable)(((JScrollPane)c).getViewport().getView())).filter();
				}
			}
		}
	}
	
	/**
	 * get the entitygroup from the table at the specified tab
	 * @param i
	 * @return
	 */
	public EntityGroup getEntityGroup(int i) {
		if(getTabCount()>i && i!=-1){
			return getTable(i).getEntityGroup();
		}
		return null;
	}

	public void dereference() {
		removeAll();
		setUI(null);

		selectListener = null;
		mouseListener = null;
		entityList = null;
		peg = null;
		tabLoadThreadVct = null;
	}

	/* (non-Javadoc)
	 * @see javax.swing.JTabbedPane#removeAll()
	 */
	public void removeAll() {
		// dereference all tables
		for (int i=0; i<getTabCount(); i++){
			Component c = getComponentAt(i);
			if (c instanceof JScrollPane) {
				EntityGroupTable egt = (EntityGroupTable)(((JScrollPane)c).getViewport().getView());
				egt.getSelectionModel().removeListSelectionListener(selectListener);
				if(egt.getRowSorter()!=null){
					egt.getRowSorter().removeRowSorterListener(this);
				}
				egt.dereference();
			}
			if(closeTabListener!=null){
				CloseTabComponent ctb = (CloseTabComponent)getTabComponentAt(i);
				ctb.dereference(closeTabListener);
				//setTabComponentAt(i, null);
			}
		}
		super.removeAll(); // remove all tabs
	}

	private void loadABRView(){
		int x = 0;
		removeAll();
		RowSelectableTable rst = entityList.getTable();

		EANFoundation[] ean = rst.getTableRowsAsArray();

		Arrays.sort(ean, new java.util.Comparator<Object>(){
			 public int compare(Object o1, Object o2) {
				 return o1.toString().compareToIgnoreCase(o2.toString());
			 }
		});
	    for (int i = 0; i < ean.length; ++i) {
            EntityGroup eg = entityList.getEntityGroup(ean[i].getKey());
            if (eg.isDisplayable()) {
                addStatusTab(eg, x++);
            }
        }

		setSelectedIndex(-1); // deselect all or notification may not be sent when (0) is selected
	}
    private void addStatusTab(EntityGroup eg, int index) {
        RowSelectableTable rst = eg.getEntityGroupTable();
        EntityGroupTable nt = null;
        JScrollPane scroll = null;

        /*
         if single display like vertical
         */
        if (rst.getRowCount() == 1) {
            EANFoundation ean = rst.getRow(0);
            if (ean != null) {
                rst = ((EntityItem) ean).getEntityItemTable();
            }
        }

        nt = new EntityGroupTable(eg, rst);

        nt.addMouseListener(mouseListener);
		if(nt.getRowSorter()!=null){
			nt.getRowSorter().addRowSorterListener(this);
		}

        scroll = new JScrollPane(nt);
        add(scroll, index);
		if(closeTabListener !=null){
			// title is displayed by the tabcomponent
			CloseTabComponent ctb = new CloseTabComponent(getTitle(eg,nt),closeTabListener);
			setTabComponentAt(index,ctb);
		}else{
			setTitleAt(index,getTitle(eg,nt));
		}

		setToolTipTextAt(index,getTip(eg,nt));
    }

	private void load() {
		int x = 0;
		// reset thread counts
		tabLoadedCnt=0;
		tabLoadThreadVct.clear();

		removeAll();

		RowSelectableTable rst = entityList.getTable();

		EANFoundation[] ean = rst.getTableRowsAsArray();

		Arrays.sort(ean, new java.util.Comparator<Object>(){
			 public int compare(Object o1, Object o2) {
				 return o1.toString().compareToIgnoreCase(o2.toString());
			 }
		});

		// create a thread for each entitygroup if there are more than one
		peg = entityList.getParentEntityGroup();
		int tabcnt = 0;
		/**/if (peg != null && entityList.isParentDisplayable()) {
			tabcnt++;
		}

		for (int i=0;i<ean.length;++i) {
			EntityGroup eg = entityList.getEntityGroup(ean[i].getKey());
			if (eg.isDisplayable()) {
				tabcnt++;
			}
		}/**/
		//tabcnt=1;// remove this and above to use threads
		if (peg != null && entityList.isParentDisplayable()) {
			if (tabcnt==1){
				addTab(peg,x++);
			}else{
				//thread it
				tabLoadThreadVct.add(new TabLoader(peg,x++));
			}
		}

		for (int i=0;i<ean.length;++i) {
			EntityGroup eg = entityList.getEntityGroup(ean[i].getKey());
			if (eg.isDisplayable()) {
				if (tabcnt==1){
					addTab(eg,x++);
				}else{
					//thread it
					tabLoadThreadVct.add(new TabLoader(eg,x++));
				}
			}
		}
		if (tabcnt>1){ // use threads
			// create array to hold info needed to add the tab
			tabArray = new ScrollEntityGroup[tabLoadThreadVct.size()];
			for (int i=0; i<tabLoadThreadVct.size(); i++){
				Thread thd = (Thread)tabLoadThreadVct.elementAt(i);
				thd.start();
			}

			waitForTabSetup();
			// actually add the tab using the index now
			for (int i=0; i<tabArray.length; i++){
				addTab(tabArray[i], i);
				tabArray[i].dereference();
				tabArray[i] = null;
			}
		}

		setSelectedIndex(-1); // deselect all or notification may not be sent when (0) is selected


		// reset thread counts
		tabLoadedCnt=0;
		tabLoadThreadVct.clear();
		tabArray = null;

	}

	// no threads used here
	private void addTab(EntityGroup eg, int index) {
		EntityGroupTable nt = new EntityGroupTable(eg);
		JScrollPane scroll = null;

		nt.addMouseListener(mouseListener);
		if(nt.getRowSorter()!=null){
			nt.getRowSorter().addRowSorterListener(this);
		}

		if (selectListener!=null){
			nt.getSelectionModel().addListSelectionListener(selectListener);
		}

		scroll = new JScrollPane(nt);
		add(scroll,index);

		if(closeTabListener !=null){
			// title is displayed by the tabcomponent
			CloseTabComponent ctb = new CloseTabComponent(getTitle(eg,nt),closeTabListener);
			setTabComponentAt(index,ctb);
		}else{
			setTitleAt(index,getTitle(eg,nt));
		}

		setToolTipTextAt(index,getTip(eg,nt));

		nt.filter(); // apply any filters if they exist
	}
	//some threads load faster than others, index is meaningless when loaded from thread so sort is lost
	private void addTab(ScrollEntityGroup seg, int index) {
		add(seg.scroll,index);

		if(closeTabListener !=null){
			// title is displayed by the tabcomponent
			CloseTabComponent ctb = new CloseTabComponent(getTitle(seg.eg,null),closeTabListener);
			setTabComponentAt(index,ctb);
		}else{
			setTitleAt(index,getTitle(seg.eg,null));
		}

		setToolTipTextAt(index,getTip(seg.eg,null));
	}
	// called by the thread to do everything but add the tab at the index
	private void setupTab(EntityGroup eg, int index) {
		EntityGroupTable nt = new EntityGroupTable(eg);

		nt.addMouseListener(mouseListener);

		if(nt.getRowSorter()!=null){
			nt.getRowSorter().addRowSorterListener(this);
		}
		if (selectListener!=null){
			nt.getSelectionModel().addListSelectionListener(selectListener);
		}

		JScrollPane scroll = new JScrollPane(nt);
		tabArray[index]= new ScrollEntityGroup(eg,scroll); // save for later add()

		nt.filter(); // apply any filters if they exist
	}
	private String getTitle(EntityGroup eg, EntityGroupTable egt) {
		String s = null;
		if (eg == peg) {
			s = INDICATE_PARENT + eg.toString();
		} else {
			s = eg.toString();
		}
		int i = eg.getEntityItemCount();
		String title = Routines.truncate(s,20);
		if (i>0){
			int visibleCnt = i;
			if(egt !=null){
				visibleCnt = egt.getRowCount();
			}
			title+=" (" + visibleCnt + (visibleCnt!=i?" of "+i:"")+")";
		}
		return title;
	}

	/**
	 * get the tooltip for this group, basically is the title but not truncated
	 * @param eg
	 * @param egt
	 * @return
	 */
	private String getTip(EntityGroup eg,EntityGroupTable egt ) {
		int i = eg.getEntityItemCount();
		String tip  = eg.toString();
		if (i > 0) {
			tip+=" (" + i + ")";
		}
		return tip;
	}

	/*
	 * make selected tab lighter than the rest
	 * @see javax.swing.JTabbedPane#getBackgroundAt(int)
	 */
	public Color getBackgroundAt(int index) {
		Color out = super.getBackgroundAt(index);
		if (out != null) {
			if (getSelectedIndex()!=index) {
				out = out.darker();
			}
		}
		return out;
	}

	private int tabLoadedCnt=0;
	private Vector<TabLoader> tabLoadThreadVct =new Vector<TabLoader>();
	private ScrollEntityGroup[] tabArray = null;
	private synchronized void waitForTabSetup() {
		//This only loops once for each special event, which may not
		//be the event we're waiting for.
		while(tabLoadThreadVct.size()!=tabLoadedCnt) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		//System.out.println("Joy and efficiency have been achieved!");
	}

	private synchronized void tabLoaded(){
		tabLoadedCnt++;
		notifyAll();
	}

	/******************************************************************************
	 * Thread to load a tab - used to improve performance
	 */
	private class TabLoader extends Thread
	{
		private EntityGroup eg;
		private int index;
		protected TabLoader(EntityGroup g, int id){
			eg = g;
			index=id;
		}
		/**
		 * Try to load the tab.
		 */
		public void run()
		{
			//System.err.println("@@@@starting thread "+index);
			setupTab(eg, index);
			tabLoaded();
			//System.err.println("@@@@ending thread "+index);
			eg=null; // release memory
		}
	}
	private class ScrollEntityGroup{
		EntityGroup eg;
		JScrollPane scroll;
		ScrollEntityGroup(EntityGroup eg2,JScrollPane scroll2){
			this.eg = eg2;
			this.scroll = scroll2;
		}
		void dereference(){
			eg = null;
			scroll = null;
		}
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.RowSorterListener#sorterChanged(javax.swing.event.RowSorterEvent)
	 */
	public void sorterChanged(RowSorterEvent e) {
		int index = this.getSelectedIndex();
		if(index != -1){
			EntityGroupTable egt = getTable(index);
			if(closeTabListener !=null){
				CloseTabComponent tabcomp = (CloseTabComponent)getTabComponentAt(index);
				tabcomp.setTitle(getTitle(egt.getEntityGroup(),egt)); // show new count
			}else{
				setTitleAt(index,getTitle(egt.getEntityGroup(),egt)); // show new count
			}
			setToolTipTextAt(index,getTip(egt.getEntityGroup(),egt));
		}
	}
}
