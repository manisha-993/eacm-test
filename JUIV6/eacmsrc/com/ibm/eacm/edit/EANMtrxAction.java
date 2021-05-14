//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package com.ibm.eacm.edit;

import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ibm.eacm.EACM;
import com.ibm.eacm.actions.*;
import com.ibm.eacm.mtrx.MatrixActionBase;
import com.ibm.eacm.mtrx.MatrixActionTab;
import com.ibm.eacm.mtrx.RelAttrMatrixActionTab;
import com.ibm.eacm.mw.DBSwingWorker;
import com.ibm.eacm.mw.DBUtils;
import com.ibm.eacm.mw.LoginMgr;
import com.ibm.eacm.mw.RMIMgr;
import com.ibm.eacm.objects.Utils;


import COM.ibm.eannounce.objects.EANActionItem;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.MatrixActionItem;
import COM.ibm.eannounce.objects.MatrixList;
import COM.ibm.opicmpdh.middleware.Profile;
/**
 * this is used for matrix from edit
 * completely untested 
 *
 * @author Wendy Stimpson
 */
// $Log: EANMtrxAction.java,v $
// Revision 1.3  2014/10/20 19:56:06  wendy
// Add worker id to timing log output
//
// Revision 1.2  2012/11/09 20:48:00  wendy
// check for null profile from getNewProfileInstance()
//
// Revision 1.1  2012/09/27 19:39:19  wendy
// Initial code
//
public class EANMtrxAction extends EANAction{
	private static final long serialVersionUID = 1L;
	private EditController curEditor=null;
	public EANMtrxAction(EANActionItem act,EditController n,EANActionSet p) {
		super(act,ACTION_PURPOSE_MATRIX,p);
		curEditor = n;
	}

	public void dereference(){
		super.dereference();
		curEditor = null;
	}

	public void actionPerformed(ActionEvent e) {
        EntityItem[] ei = curEditor.getSelectedEntityItems();

		if (ei!=null){
			//check for existing mtrx for same profile, action and ei
			if (!EACM.getEACM().tabExists(curEditor.getProfile(), ei, getEANActionItem())) {
				curEditor.disableActionsAndWait(); //disable all other actions and also set wait cursor
				worker = new MtrxWorker(ei);
				RMIMgr.getRmiMgr().execute(worker);
			}
		}else{
		      com.ibm.eacm.ui.UI.showFYI(null, Utils.getResource("msg11014.1",Utils.getResource("mtrx")));
		}
	}
	private class MtrxWorker extends DBSwingWorker<MatrixList, Void> {
		private EntityItem[] eia = null;
		private long t11 = 0L;
		MtrxWorker(EntityItem[] ei){
			eia = ei;
		}
		@Override
		public MatrixList doInBackground() {
			MatrixList mlist = null;
			try{
				t11 = System.currentTimeMillis();
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting "+getIdStr());
				MatrixActionItem mai = (MatrixActionItem)getEANActionItem();
				mai.setEntityItems(eia);
				Profile prof = LoginMgr.getNewProfileInstance(curEditor.getProfile());
				if(prof!=null){
					mlist = DBUtils.getMatrixList(mai, prof, curEditor);
				}
			}catch(Exception ex){ // prevent hang
				Logger.getLogger(EDIT_PKG_NAME).log(Level.SEVERE,"Exception "+ex,ex);
			}finally{
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,getIdStr()+" bg ended "+Utils.getDuration(t11));
				RMIMgr.getRmiMgr().complete(this);
				worker = null;
			}

			return mlist;
		}

		@Override
		public void done() {
			//this will be on the dispatch thread
			try {
				if(!isCancelled()){
					MatrixList mlist = get();
					if (mlist !=null){
			            MatrixActionBase mat = null;
			            if(mlist.isFeatureMatrix()){  // terrible name but if there are RELATTR defined this will be true
			            	mat = new RelAttrMatrixActionTab(mlist, curEditor.getParentKey());
			            }else{
			            	mat = new MatrixActionTab(mlist, curEditor.getParentKey(), eia);
			            }

			            EACM.getEACM().addTab(curEditor, mat);
						mat.requestFocusInWindow();
					}
				}
			} catch (InterruptedException ignore) {}
			catch (java.util.concurrent.ExecutionException e) {
				listErr(e,"getting matrix list");
			}finally{
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,getIdStr()+" dispatch ended "+Utils.getDuration(t11));
				eia = null;
				curEditor.enableActionsAndRestore();
			}
		}
		public void notExecuted(){
			Logger.getLogger(EDIT_PKG_NAME).log(Level.WARNING,getIdStr()+" not executed");
			curEditor.enableActionsAndRestore();
			worker = null;
			eia = null;
		}
	}

}
