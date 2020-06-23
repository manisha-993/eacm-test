//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.mtrx;


import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import COM.ibm.eannounce.objects.EANBusinessRuleException;
import COM.ibm.eannounce.objects.MetaColumnOrderGroup;
import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;

import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.mw.DBSwingWorker;
import com.ibm.eacm.mw.RMIMgr;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.table.MtrxTable;


/*********************************************************************
 * save the current column order of matrixgroups, also save any hidden columns
 * @author Wendy Stimpson
 */
//$Log: SaveMgOrderAction.java,v $
//Revision 1.2  2012/12/03 19:41:15  wendy
//fix typo
//
//Revision 1.1  2012/09/27 19:39:22  wendy
//Initial code
//
public class SaveMgOrderAction extends EACMAction
{
	private static final long serialVersionUID = 1L;

	private MtrxTable mTable = null;
	private MatrixActionBase actionTab = null;

	public SaveMgOrderAction(MtrxTable _tab,MatrixActionBase mab) {
		super(MTRX_SAVEORDER);
		setTable(_tab);
		actionTab = mab;
	}

	public void setTable(MtrxTable nt){
		mTable = nt;
	    setEnabled(true);
	}

	public void setEnabled(boolean newValue) {
		super.setEnabled(newValue && mTable!=null && !Utils.isPast(mTable.getProfile()));
	}

	public void dereference(){
		super.dereference();
		mTable = null;
		actionTab = null;
	}
	public void actionPerformed(ActionEvent e) {
		mTable.getCrossTable().cancelCurrentEdit();

		actionTab.disableActionsAndWait();

		worker = new SaveMgWorker();
		RMIMgr.getRmiMgr().execute(worker);
	}

	/**
	 * update metacolumnordergroup with current ui order and commit
	 *
	 */
    private class SaveMgWorker extends DBSwingWorker<Void, Void> {
    	@Override
    	public Void doInBackground() {
    		try{
       			MetaColumnOrderGroup mcog = mTable.getMetaColumnOrderGroup();
    			if (mcog != null) {
    				mTable.adjustOrder(mcog);
    				commit(mcog);
    			}
    		}catch(Exception ex){ // prevent hang
    			Logger.getLogger(MTRX_PKG_NAME).log(Level.SEVERE,"Exception "+ex,ex);
    		}finally{
            	RMIMgr.getRmiMgr().complete(worker);
            	worker = null;
    		}
    		return null;
    	}

        @Override
        public void done() {
            //this will be on the dispatch thread
        	actionTab.enableActionsAndRestore();
        	worker = null;
        }
     	public void notExecuted(){
     		Logger.getLogger(MTRX_PKG_NAME).log(Level.WARNING,"not executed");
     		actionTab.enableActionsAndRestore();
     		worker = null;
    	}
    }

	private boolean commit(MetaColumnOrderGroup _mcog)  {
		boolean ok = true;
		try {
			_mcog.commit(null, ro());
		} catch (MiddlewareBusinessRuleException _mbre) {
			com.ibm.eacm.ui.UI.showException(mTable,_mbre);
			ok = false;
		}catch (EANBusinessRuleException _bre) {
    		com.ibm.eacm.ui.UI.showException(mTable,_bre);
			ok = false;
        } catch (Exception exc) {
			if(RMIMgr.shouldTryReconnect(exc)){	// try to reconnect
				if (RMIMgr.getRmiMgr().reconnectMain()) {
					try{
						_mcog.commit(null, ro());
					}catch (MiddlewareBusinessRuleException _mbre) {
						com.ibm.eacm.ui.UI.showException(mTable,_mbre);
						ok = false;
					}catch (EANBusinessRuleException _bre) {
			    		com.ibm.eacm.ui.UI.showException(mTable,_bre);
						ok = false;
			        }catch(Exception e){
						if(RMIMgr.shouldPromptUser(e)){// show user msg and ask what to do
							if (com.ibm.eacm.ui.UI.showMWExcPrompt(mTable, e) == RETRY) {
								ok = commit(_mcog);
							}
						}else{
							com.ibm.eacm.ui.UI.showException(mTable,e);
							ok = false;
						}
					}
				}else{	// reconnect failed
					com.ibm.eacm.ui.UI.showException(mTable,exc, "mw.err-title");
					ok = false;
				}
			}else{ // show user msg and ask what to do
				if(RMIMgr.shouldPromptUser(exc)){
					if (com.ibm.eacm.ui.UI.showMWExcPrompt(mTable, exc) == RETRY) {
						ok = commit(_mcog);
					}// else user decide to ignore or exit
				}else{
					com.ibm.eacm.ui.UI.showException(mTable,exc);
					ok = false;
				}
			}
		}
		return ok;
	}
}