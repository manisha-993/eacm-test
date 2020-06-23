//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.mtrx;

import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;

import COM.ibm.eannounce.objects.*;

import com.ibm.eacm.actions.*;

import com.ibm.eacm.mw.DBSwingWorker;
import com.ibm.eacm.mw.RMIMgr;
import com.ibm.eacm.objects.Findable;
import com.ibm.eacm.objects.OutOfRangeException;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.table.CrossTable;

import com.ibm.eacm.ui.PickFrame;


/******************************************************************************
* This is used to display the picklist for matrix
* @author Wendy Stimpson
*/
// $Log: MtrxPickFrame.java,v $
// Revision 1.2  2012/12/03 19:41:15  wendy
// fix typo
//
// Revision 1.1  2012/09/27 19:39:22  wendy
// Initial code
//
public class MtrxPickFrame extends PickFrame
{
	private static final long serialVersionUID = 1L;
	/** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2012  All Rights Reserved.";
    /** cvs version */
    public static final String VERSION = "$Revision: 1.2 $";
    private CrossTable crssTable = null;

	/* (non-Javadoc)
	 * @see com.ibm.eacm.ui.PickFrame#getFindable()
	 */
	public Findable getFindable() {
		return tabbedPane.getTable();
	}
    /**
     * @param mtrx
     * @param _list
     * @param tbl
     */
    public MtrxPickFrame(MatrixActionBase mtrx, EntityList _list, CrossTable tbl)  {
        super(mtrx,_list);
        crssTable = tbl;
    }
	protected void refreshActions() {
		super.refreshActions();

		//ADDCOL_ACTION
		getAction(ADDCOL_ACTION).setEnabled(tabbedPane.hasSelection());
    }
    /**
     * createToolbar using same actions as menus
     */
    protected void createToolbar() {
    	super.createToolbar();

    	tBar.add(getAction(ADDCOL_ACTION));
    	tBar.addSeparator();
       	tBar.add(getAction(FINDREP_ACTION));
      	tBar.add(getAction(FILTER_ACTION));
     }
     protected void createActionMenu() {
    	JMenu actMnu = new JMenu(Utils.getResource(ACTIONS_MENU));
    	actMnu.setMnemonic(Utils.getMnemonic(ACTIONS_MENU));

    	addLocalActionMenuItem(actMnu, ADDCOL_ACTION);

    	menubar.add(actMnu);
    }
    /**
     * create all of the actions, they are shared between toolbar and menu
     */
    protected void createActions() {
    	super.createActions();

        //ADDCOL_ACTION
        EACMAction act = new AddColAction();
        addAction(act);
    }

    /**
     * dereference
     *
     */
    public void dereference() {
		crssTable = null;

        super.dereference();
    }

    //================================================================

    private class AddColAction extends EACMAction {
    	private static final long serialVersionUID = 1L;
    	AddColAction() {
    		super(ADDCOL_ACTION, KeyEvent.VK_N, Event.CTRL_MASK);
    		putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("addcol.gif"));
    		setEnabled(false);
    	}

    	public void actionPerformed(ActionEvent e) {
    		disableActionsAndWait();

    		worker = new AddColWorker();
    		RMIMgr.getRmiMgr().execute(worker);
    	}

    	/**
    	 * get clones in the bg thread
    	 *
    	 */
        private class AddColWorker extends DBSwingWorker<EntityItem[], Void> {
        	@Override
        	public EntityItem[] doInBackground() {
        		EntityItem[] cloneArray=null;
        		try{
        			EntityItem[] selItems = tabbedPane.getSelectedEntityItems(false, true);
        			// clone the items, list will be dereferenced
        			cloneArray = new EntityItem[selItems.length];
        			EntityGroup egReturn = new EntityGroup(null, RMIMgr.getRmiMgr().getRemoteDatabaseInterface(),
        					crssTable.getProfile(),	selItems[0].getEntityType(), "Navigate");
        			for (int i=0; i<selItems.length; i++){
        				cloneArray[i] = new EntityItem(egReturn,selItems[i]);
        				egReturn.putEntityItem(cloneArray[i]);
        			}
        			selItems = null;
        		}catch(OutOfRangeException _range) {
        			com.ibm.eacm.ui.UI.showFYI(MtrxPickFrame.this,_range);
        		}catch(Exception ex){ // prevent hang
        			Logger.getLogger(MTRX_PKG_NAME).log(Level.SEVERE,"Exception "+ex,ex);
        		}finally{
        			RMIMgr.getRmiMgr().complete(worker);
        			worker = null;
        		}
    			return cloneArray;
        	}

            @Override
            public void done() {
                //this will be on the dispatch thread
            	try {
            		if(!isCancelled()){
            			EntityItem[] cloneArray = get();
             			((MatrixActionBase)actionTab).addColumn(crssTable,cloneArray);
            		}
                } catch (InterruptedException ignore) {}
                catch (java.util.concurrent.ExecutionException e) {
                	listErr(e,"adding Column");
                }finally{
                	enableActionsAndRestore();
            		worker = null;
                }
            }
         	public void notExecuted(){
         		Logger.getLogger(MTRX_PKG_NAME).log(Level.WARNING,"not executed");
         		enableActionsAndRestore();
         		worker = null;
        	}
        }
    }
}
