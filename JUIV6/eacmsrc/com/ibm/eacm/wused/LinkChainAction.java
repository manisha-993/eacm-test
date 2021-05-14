//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.wused;

import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EntityList;
import COM.ibm.eannounce.objects.LinkActionItem;
import COM.ibm.eannounce.objects.MetaLink;

import com.ibm.eacm.EACM;
import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.mw.*;
import com.ibm.eacm.objects.*;
import com.ibm.eacm.ui.UI;


/*********************************************************************
 * This is used for linkchain request from WUPickFrame
 * @author Wendy Stimpson
 */
//$Log: LinkChainAction.java,v $
//Revision 1.2  2012/12/03 19:40:56  wendy
//fix typo
//
//Revision 1.1  2012/09/27 19:39:25  wendy
//Initial code
//
public class LinkChainAction extends EACMAction
{
	private static final long serialVersionUID = 1L;
	private WUsedActionTab wusedTab=null;
	public LinkChainAction(WUsedActionTab n) {
		super(LINK_ACTION);
		wusedTab = n;
	}
	public void dereference(){
		super.dereference();
		wusedTab=null;
	}
	public void actionPerformed(ActionEvent e) {}


	protected void linkChain(EntityItem[] selItems,LinkActionItem lai){
		worker = new LinkChainWorker(selItems,lai);
		RMIMgr.getRmiMgr().execute(worker);
	}

	private class LinkChainWorker extends HeavyWorker<Object, Void> {
		private EntityItem[] selItems = null;
		private EntityItem[] selectedOriginals = null;
		private LinkActionItem lai = null;
		private long t11 = 0;
		LinkChainWorker(EntityItem[] d, LinkActionItem l){
			selItems = d;
			lai = l;
		}
		@Override
		public Object doInBackground() {
			Object obj = null;
			try{
				t11 = System.currentTimeMillis();
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting");
				//Original item array is based on selection, save it for msg
				selectedOriginals = wusedTab.getOriginalEntityItemAsArray();
				obj = wusedTab.linkChain(lai, selectedOriginals, selItems);
			}catch(Exception ex){ // prevent hang
				Logger.getLogger(WU_PKG_NAME).log(Level.SEVERE,"Exception "+ex,ex);
			}finally{
				RMIMgr.getRmiMgr().complete(this);
				worker = null;
			}
			return obj;
		}

		@Override
		public void done() {
			//this will be on the dispatch thread
			try {
				if(!isCancelled()){
					Object obj = get();
					if (obj != null) {
						if (obj instanceof Boolean) {
							if (!((Boolean) obj).booleanValue()) {
								Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"actperf ended "+Utils.getDuration(t11));
								return; // how will user know it failed?
							}
						}
					} else { //  how will user know it failed?
						Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"actperf obj=null ended "+Utils.getDuration(t11));
						return;
					}
					String out = reportLink(selectedOriginals, selItems,null);
					if (out != null) {
						UI.showLinkResults(wusedTab, out);
					}
					if (obj instanceof EntityList && lai.hasChainAction()) {
						wusedTab.updateTable();
						if (!lai.requireInput()) {
							if (lai.isChainEditAction()) {
								 EACM.getEACM().loadFromLink(wusedTab, (EntityList)obj);
							} else if (lai.isChainWhereUsedAction()) {
								 EACM.getEACM().loadFromLink(wusedTab, (EntityList)obj);
							} else if (lai.isChainMatrixAction()) {
								 EACM.getEACM().loadFromLink(wusedTab, (EntityList)obj);
							}
						}
					}
				}
			} catch (InterruptedException ignore) {}
			catch (java.util.concurrent.ExecutionException e) {
				listErr(e,"getting entitylist");
			}finally{
				wusedTab.enableActionsAndRestore();
				selItems = null;
				selectedOriginals = null;
			}
		}
		public void notExecuted(){
			Logger.getLogger(WU_PKG_NAME).log(Level.WARNING,"not executed");
			wusedTab.enableActionsAndRestore();
			worker = null;
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
				Logger.getLogger(WU_PKG_NAME).log(Level.FINER,"       to "+_eiChild[c].getKey());
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

		if (_ei == null) {// can this happen?
			Logger.getLogger(WU_PKG_NAME).log(Level.FINER,"Linked parent");
			return "parent";
		}
		EntityGroup eg = _ei.getEntityGroup();
		if (_rel == null) {
			Logger.getLogger(WU_PKG_NAME).log(Level.FINER,"Linked "+_ei.getKey());
			return eg.getLongDescription()+" "+_ei.toString();
		}
		e1Type = _rel.getEntity1Type();
		if (e1Type != null) {
			String eType_ei = _ei.getEntityType();
			if (eType_ei.equals(e1Type)) {
				Logger.getLogger(WU_PKG_NAME).log(Level.FINER,"Linked "+_ei.getKey());
				return eg.getLongDescription()+" "+_ei.toString();
			}

			ent = (EntityItem)_ei.getDownLink(0);
			if (ent != null) {
				eg = ent.getEntityGroup();
				String eType_ent = ent.getEntityType();
				if (eType_ent.equals(e1Type)) {
					Logger.getLogger(WU_PKG_NAME).log(Level.FINER,"Linked "+ent.getKey());
					return eg.getLongDescription()+" "+ent.toString();
				}
			}
		}

		Logger.getLogger(WU_PKG_NAME).log(Level.FINER,"Linked "+_ei.getKey());
		return eg.getLongDescription()+" "+_ei.toString();
	}
}