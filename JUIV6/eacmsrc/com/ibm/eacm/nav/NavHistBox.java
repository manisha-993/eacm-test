//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.nav;


import java.awt.Color;

import com.ibm.eacm.objects.Routines;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.rend.HistRend;

import COM.ibm.eannounce.objects.*; 
import javax.accessibility.*;
import javax.swing.*;


/**
 * This class gives a dropdown representation for history.  It is created
 * by Navigate.build() from a predefined form.  
 * Manipulate strings instead of the NavSerialObjects, this frees up memory.  They will be reloaded
 * when needed.
 * @author Wendy Stimpson
 */
//$Log: NavHistBox.java,v $
//Revision 1.4  2015/01/05 19:15:34  stimpsow
//use Theme for background colors
//
//Revision 1.3  2013/07/31 16:58:04  wendy
//correct gotoprev with pinned nav
//
//Revision 1.2  2013/02/05 18:23:21  wendy
//throw/handle exception if ro is null
//
//Revision 1.1  2012/09/27 19:39:15  wendy
//Initial code
//
public class NavHistBox extends JComboBox
{
	private static final int MAXIMUM_NAVIGATE_HISTORY = 50;
	
	private static final long serialVersionUID = 1L;
	private Navigate parent = null;
	private SerialObjectController nsoController = null;
	
	/**
	 * navHistBox - called by Navigate.createHistory()
	 * @param par
	 * 
	 */
	protected NavHistBox(Navigate par) {
		parent = par;
		nsoController = parent.getNavSerialObjectController();
		setRenderer(new HistRend(this));
		initAccessibility("accessible.navHist");
	}   

	/* (non-Javadoc)
	 * needed because metal lnf and using themes change the color - make it look like it did
	 * @see java.awt.Component#getBackground()
	 */
	public Color getBackground() {
		return Color.white;
	}
	
	/*
	 * Used in cell renderer
	 */
	public boolean isPickList(Object value){
		boolean ispicklist = false;
		if (value != null){
			for(int i=0; i<getItemCount(); i++){
				HistItem hi = (HistItem)getItemAt(i);
				if(hi.equals(value)){
					EANActionItem action = hi.getAction();
					if (action instanceof NavActionItem){
						ispicklist = ((NavActionItem)action).isPicklist();
						break;
					}
				}
			}
		}

		return ispicklist;
	}
	/**
	 * reset - called by Navigate.resetHistory()
	 *
	 */
	protected void reset() {
		while (getHistoryCount() > 0) {
			HistItem histitem = (HistItem)getItemAt(0);
			nsoController.delete(histitem.getFileName());// get filename 
			removeItemAt(0);
			histitem.dereference();
		}
	}
	
	/**
	 * this should be the current selection
	 * @param nso
	 */
	protected void update(EANActionItem act){
		int selid = getSelectedIndex();
		HistItem histitem = (HistItem)getItemAt(selid);
		histitem.setAction(act);
	}

	/**
	 * load
	 * 
	 * @param nso
	 * @param eList
	 * @param remove
	 */
	protected void load(NavSerialObject nso, EntityList eList, boolean remove) {
		int iCurrent = getSelectedIndex();
		if (remove && iCurrent < getHistoryCount() -1) {
			removeHistory(iCurrent, true);
		}

		HistItem hi = new HistItem(nso.getAction(),nso.getFileName());
		addItem(hi);

		if (eList != null) {
			setSelectedItem(hi);
		}

		if (getHistoryCount() > MAXIMUM_NAVIGATE_HISTORY) {
			removeHistory(0,false);
		}
	}

	/**
	 * removeHistory
	 *
	 * @param i
	 * @param below = true, remove everything after this item
	 * 
	 */
	protected void removeHistory(int ii, boolean below) {
		if (below) {
			int i = ii + 1;
			while (getHistoryCount() > i) {
				removeHistory(i, false);
			}
		} else {
			HistItem histitem = (HistItem)getItemAt(ii);
			nsoController.delete(histitem.getFileName());// get filename 
			removeItemAt(ii);
			histitem.dereference();
		}
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComboBox#setSelectedIndex(int)
	 */
	public void setSelectedIndex(int anIndex) {
		if (!parent.isPin()) {
			// only change it if the same navcontroller tab will be used
			super.setSelectedIndex(anIndex);
		}

		if (anIndex >= 0) {
			HistItem histitem = (HistItem)getItemAt(anIndex);
			String nsoFn = histitem.getFileName();//hashFilename.get(histitem.getHistKey());
			if (nsoFn != null) {
				parent.loadFromHistory(nsoFn);
			}			
		}	
	}

	/**
	 * get the history filename for this index
	 * @param i
	 * @return
	 */
	protected String getHistoryFilename(int i){
		String fn = "";
		if(i>=0 && i<this.getItemCount()){
			HistItem histitem = (HistItem)getItemAt(i);
			fn=histitem.getFileName();
		}
	
		return fn;
	}

	/**
	 * dereference
	 *
	 */
	public void dereference() {
		initAccessibility(null);

		parent = null;
		nsoController = null;
		HistRend hr = (HistRend)getRenderer();
		if (hr != null){
			hr.dereference();
		}	
		for(int i=0; i<getItemCount(); i++){
			HistItem hi = (HistItem)getItemAt(i);
			hi.dereference();
		}
		removeAll();
		setUI(null);
	}

	/**
	 * backup
	 * called by prev arrow action
	 */
	protected void backup() {
		int i = getSelectedIndex() -1;
		int count = getHistoryCount();
		if (i < 0) {
			setSelectedIndex(count -1);
		} else if (i >= count) {
			setSelectedIndex(0);
		} else if (i >= 0 && i < count) {
			setSelectedIndex(i);
		}
	}

	/**
	 * load from another NavHist- done when a tab is pinned
	 */
	protected void load(NavHistBox hist){
		HistItem hi = (HistItem)getSelectedItem();
  	
		// load this history from the passed in history
		for (int i=0;i<hist.getItemCount();++i) {
			HistItem other = (HistItem)hist.getItemAt(i);
			if (other.equals(hi)){
				return;
			}
		
			boolean exists = false;
			for (int i2=0;i2<getItemCount();++i2) {
				HistItem curhistitem = (HistItem)getItemAt(i2);
				if(curhistitem.getFileName().equals(other.getFileName())) {
					exists = true;
					break;
				}
			}
			if (!exists) {	
				HistItem hi2 = new HistItem(other.getAction(),other.getFileName());
				insertItemAt(hi2,i);
			}		
			
		}  
		this.fireActionEvent();
	}

    /**
	 * getHistoryCount
	 *
	 * @return
	 * 
	 */
	protected int getHistoryCount() {
		return getItemCount();
	}

	/**
	 * getNavigationHistory
	 * 
	 * @return EANActionItem[]
	 */
	protected EANActionItem[] getNavigationHistory() {
		EANActionItem[] items = null;
		int ii = getHistoryCount();
		if (ii>0){
			ii--; // skip last one
			items = new EANActionItem[ii];	

			for (int i=0;i<ii;++i) {
				HistItem histitem = (HistItem)getItemAt(i);
				items[i] = histitem.getAction();
			}			
		}
	
		return items;
	}

	/**
	 * loadBookmarkHistory -
	 * 
	 * @param bmi
	 */
	protected void loadBookmarkHistory(BookmarkItem bmi) {
		if (bmi != null && bmi.hasActionHistory()) {
			EANActionItem[] hist = bmi.getActionHistory();
			for (int i=0;i<hist.length;++i) {
				if (hist[i] instanceof NavActionItem) {
					NavSerialObject tmp = nsoController.generate((NavActionItem)hist[i]);
					HistItem hi = new HistItem(tmp.getAction(),tmp.getFileName());
					insertItemAt(hi,i);
				}
			}
			//notify action listeners that data has changed
			fireActionEvent();
		}
	}

	/**
	 * reselectIndex
	 *
	 * @param i
	 * 
	 */
	protected void reselectIndex(int i) {
		if (i >= 0 && i < getItemCount()) {
			super.setSelectedIndex(i);
		}
	}

	/**********
	 * NavController.FocusManager uses this
	 * used to set current navigate when used in dual nav
	 * 
	 */
	public Navigate getNavigate() {
		return parent;
	}

	/**
	 * initAccessibility
	 *
	 * 
	 * @param s
	 */
	private void initAccessibility(String s) {
		AccessibleContext ac = getAccessibleContext();
		if (ac != null) {
			if (s == null) {
				ac.setAccessibleName(null);
				ac.setAccessibleDescription(null);
			} else {
				String strAccessible = Utils.getResource(s);
				ac.setAccessibleName(strAccessible);
				ac.setAccessibleDescription(strAccessible);
			}
		}
	}
	//private static int cnt;
	public class HistItem {
		private EANActionItem actionItem;
		private String filename;
		private String key;
		HistItem(EANActionItem act, String fn){
		//	cnt++;
			actionItem = act;
			filename = fn;
			key = actionItem.getActionItemKey();// this caused pin and gotoprev to load wrong history +cnt;
		}
		void dereference(){
			actionItem = null;
			filename=null;
			key = null;
		}
		/**
		 * needed to change display when language is changed, the action is not shared with the navserialobject
		 * @param act
		 */
		void setAction(EANActionItem act){
			actionItem = act;
		}
		String getFileName(){
			return filename;
		}
		String getHistKey(){
			return key;
		}
		EANActionItem getAction(){
			return actionItem;
		}
		public String toString(){		
			String tmp = actionItem.getDisplayName();	
			if (tmp == null) {							
				tmp = actionItem.toString();			
			}											
			return Routines.truncate(tmp,150); // limit length for history list
		}
		public boolean equals(Object o) {
			if(o instanceof HistItem){
				if(this.getHistKey()!=null){ // incase deref has run
					return this.getHistKey().equals(((HistItem)o).getHistKey());
				}
			}
			return super.equals(o);
		}
	}
}
