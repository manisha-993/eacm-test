//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.nav;


import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ibm.eacm.EACM;
import com.ibm.eacm.actions.*;
import com.ibm.eacm.mw.DBUtils;
import com.ibm.eacm.mw.HeavyWorker;
import com.ibm.eacm.mw.RMIMgr;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.ui.UI;
import COM.ibm.eannounce.objects.*;


/**
 * This is used for link request from NavPickFrame
 * moved to navigate because if pickframe is closed during link it gets all messed up
 * @author Wendy Stimpson
 */
//$Log: PLLinkAction.java,v $
//Revision 1.1  2012/09/27 19:39:14  wendy
//Initial code
//
public class PLLinkAction extends EACMAction {
	private static final long serialVersionUID = 1L;
	private Navigate navigateTab=null;
	private NavPickFrame pickFrame=null;
	public PLLinkAction(Navigate n) {
		super(LINK_ACTION);
		navigateTab = n;
	}
	public void dereference(){
		super.dereference();
		navigateTab=null;
		pickFrame=null;
	}
	public void actionPerformed(ActionEvent e) {}

	protected void link(LinkActionItem lai,  EntityItem[] navItems, EntityItem[] dataItems){
		worker = new LinkWorker(lai, navItems,dataItems);
		RMIMgr.getRmiMgr().execute(worker);
	}
	protected void setPickFrame(NavPickFrame pf){
		pickFrame = pf;
	}

	private class LinkWorker extends HeavyWorker<Object, Void> {
		private EntityItem[] childItems = null; //these are the items to link from the pickframe
		private EntityItem[] parentItems = null; //these are the items to link from the navigate tbl
		private LinkActionItem lai = null;
		private long t11 = 0L;
		private String msg = null;
		private EntityGroup childGrp = null;

		LinkWorker(LinkActionItem l,EntityItem[] n, EntityItem[] d){
			// make a clone of these dataitems incase user closes the pickframe
			childItems = new EntityItem[d.length];

			try {
				// clone the child, pickframe entitylist may be dereferenced if pickframe is closed
				childGrp = new EntityGroup(null, ro(), navigateTab.getProfile(), d[0].getEntityType(), "Navigate");
				for (int i = 0; i < d.length; ++i) {
					childItems[i] = new EntityItem(childGrp,d[i]);
					childGrp.putEntityItem(childItems[i]);
				}
			} catch (Exception _mre) {
				_mre.printStackTrace();
			}
			parentItems = n;
			lai=l;
			MetaLink mLink = lai.getMetaLink();

			if (lai.isOppSelect()) {
				lai.setParentEntityItems(childItems);
				lai.setChildEntityItems(parentItems);
				msg = reportLink(childItems, parentItems, mLink);
			} else {
				lai.setParentEntityItems(parentItems);
				lai.setChildEntityItems(childItems);
				msg = reportLink(parentItems, childItems, mLink);
			}
		}

		@Override
		public Object doInBackground() {
			Object o = null;
			if(derefCaller){ // user closed the tab before this could run, dont run at all
				return null;
			}
			try{
				t11 = System.currentTimeMillis();
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting");
				o = DBUtils.doLinkAction(lai, navigateTab.getProfile(), navigateTab);
			}catch(Exception ex){ // prevent hang
				Logger.getLogger(NAV_PKG_NAME).log(Level.SEVERE,"Exception "+ex,ex);
			}finally{
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"bg ended "+Utils.getDuration(t11));
				RMIMgr.getRmiMgr().complete(this);
				if(isCancelled() && derefCaller){
					navigateTab.derefWorkerData();
				}
				worker = null;
				parentItems=null;
				childItems = null;
				if(childGrp!=null){
					childGrp.dereference();
					childGrp = null;
				}
			}
			return o;
		}

		@Override
		public void done() {
			//this will be on the dispatch thread if not cancelled before
			try {
				if(!isCancelled()){
					Object o = get();
					if(o!=null){
						if (o instanceof Boolean) {
							if (((Boolean) o).booleanValue()) {
								UI.showLinkResults(pickFrame,msg);
							}
						} else{
							UI.showLinkResults(pickFrame,msg);
							if (lai.hasChainAction()) {
								launchLink( o);
							}
						}
					}
				}
			} catch (InterruptedException ignore) {}
			catch (java.util.concurrent.ExecutionException e) {
				listErr(e,"linking items");
			}finally{
				if (pickFrame!=null){ // pick frame is still open
					pickFrame.enableActionsAndRestore();
				}else {
					if(derefCaller){ // user closed the tab
						if(navigateTab!=null){
							navigateTab.derefWorkerData();
						}
					}else{
						navigateTab.enableActionsAndRestore();//pickframe was closed
					}
				}
			}
		}
		public void notExecuted(){
			Logger.getLogger(NAV_PKG_NAME).log(Level.WARNING,"not executed");
			if (pickFrame!=null){
				pickFrame.enableActionsAndRestore();
			}else{
				navigateTab.enableActionsAndRestore();//pickframe was closed
			}
			worker = null;
		}

		private void launchLink(Object _o) {
			if (_o instanceof EntityList){
				if (!lai.requireInput()) {
					if (lai.isChainEditAction()) {
						EACM.getEACM().loadFromLink(navigateTab.getNavController(), (EntityList)_o);
					} else if (lai.isChainWhereUsedAction()) {
						EACM.getEACM().loadFromLink(navigateTab.getNavController(), (EntityList)_o);
					} else if (lai.isChainMatrixAction()) {
						EACM.getEACM().loadFromLink(navigateTab.getNavController(), (EntityList)_o);
					}
				}
			}
		}
	}
	private String reportLink(EntityItem[] _eiParent, EntityItem[] _eiChild, MetaLink _rel) {
		StringBuffer sb = new StringBuffer();
		//linked2 = linked to:
		String msg = " " + Utils.getResource("linked2") + " ";
		for (int p = 0; p < _eiParent.length; ++p) {
			for (int c = 0; c < _eiChild.length; ++c) {
				sb.append(getParentString(_eiParent[p], _rel) + msg +
						_eiChild[c].getEntityGroup().getLongDescription()+":"+_eiChild[c].toString() + RETURN);
				Logger.getLogger(NAV_PKG_NAME).log(Level.FINER,"       to "+_eiChild[c].getKey());
			}
		}
		return sb.toString();
	}
	private String getParentString(EntityItem _ei, MetaLink _rel) {
		String e1Type = null;
		EntityItem ent = null;
		if (_ei == null) {
			Logger.getLogger(NAV_PKG_NAME).log(Level.FINER,"Attempt to link parent");
			return "parent";
		}
		if (_rel == null) {
			Logger.getLogger(NAV_PKG_NAME).log(Level.FINER,"Attempt to link "+_ei.getKey());
			return _ei.getEntityGroup().getLongDescription()+":"+_ei.toString();
		}
		e1Type = _rel.getEntity1Type();
		if (e1Type != null) {
			String eType_ei = _ei.getEntityType();
			if (eType_ei != null && eType_ei.equals(e1Type)) {
				Logger.getLogger(NAV_PKG_NAME).log(Level.FINER,"Attempt to link "+_ei.getKey());
				return _ei.getEntityGroup().getLongDescription()+":"+_ei.toString();
			}
			ent = (EntityItem)_ei.getDownLink(0);
			if (ent != null) {
				String eType_ent = ent.getEntityType();
				if (eType_ent != null && eType_ent.equals(e1Type)) {
					Logger.getLogger(NAV_PKG_NAME).log(Level.FINER,"Attempt to link "+ent.getKey());
					return ent.getEntityGroup().getLongDescription()+":"+ent.toString();
				}
			}
		}
		Logger.getLogger(NAV_PKG_NAME).log(Level.FINER,"Attempt to link "+_ei.getKey());
		return _ei.getEntityGroup().getLongDescription()+":"+_ei.toString();
	}
}
