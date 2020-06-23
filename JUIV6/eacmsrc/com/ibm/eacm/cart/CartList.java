// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.cart;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;

import com.ibm.eacm.mw.DBSwingWorker;
import com.ibm.eacm.mw.RMIMgr;
import com.ibm.eacm.objects.AlwaysLevel;
import com.ibm.eacm.objects.EACMGlobals;
import com.ibm.eacm.objects.Utils;

import COM.ibm.eannounce.objects.*;

import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;


/**
 * allow for single cart implementation across tabs.
 * idea is that navigate or whereused should have one of these, only create frame when user wants to view it
 * one cart per profile
 * 
 * @author Wendy Stimpson
 */
//$Log: CartList.java,v $
//Revision 1.5  2014/06/20 18:17:53  wendy
//log when workfolder is cleared or added to
//
//Revision 1.4  2014/01/24 12:47:12  wendy
//getro() throw remoteexception to allow reconnect
//
//Revision 1.3  2013/07/18 18:34:39  wendy
//fix compiler warnings
//
//Revision 1.2  2013/02/05 18:23:22  wendy
//throw/handle exception if ro is null
//
//Revision 1.1  2012/09/27 19:39:18  wendy
//Initial code
//
public class CartList implements EACMGlobals {
	// this is used for persistence
	private static HashMap<String, CartList> cartListMap = new HashMap<String, CartList>();
	
	private EntityList list = null;
	private EntityGroup cutGroup = null;
    private AddGroupWorker addGrpWorker = null;
    private CloneWorker cloneWorker = null;
	private PropertyChangeSupport changeSupport = null; // notify
	private Cartable cartable = null;
	private NavCartFrame cartFrame = null;

	/**
	 * called when a cartframe is instantiated or dereferenced
	 * @param ncf
	 */
	protected void setCartFrame(NavCartFrame ncf){
		cartFrame=ncf;
	}

	/**
	 * must set this when same profile is used across several tabs
	 * @param dataSrc
	 */
	public void setCartable(Cartable dataSrc){
		cartable = dataSrc;
	}
	/**
	 * enable add selected to cart action if cartable has data
	 */
	public void updateActions(){
		boolean hasCartableData = cartable.hasCartableData(); // is there any cartable data at all
		boolean hasdata = hasCartableData && cartableHasSelection(); // is any of it selected
    	firePropertyChange(ADD2CART_ACTION,!hasdata, hasdata); // enable? any add selected action	
    	
		hasdata = cartable.supportsGetAll() && hasCartableData; // is addall supported and does any data exist
    	firePropertyChange(ADDALL2CART_ACTION,!hasdata, hasdata);
	}
	/**
	 * @return
	 */
	protected Cartable getCartable(){
		return cartable;
	}

	/**
     * get CartList for this profile, it persists after the frame is closed/disposed
     * @param dataSrc
     * @param prof
     * @return
     */
    public static CartList getCartList(Cartable dataSrc,Profile prof) {
    	CartList cList = null;
    	if (prof != null) {
    		String key = getCartKey(prof);
    		if (cartListMap.containsKey(key)) {
    			cList = cartListMap.get(key);
    			cList.setCartable(dataSrc);
    		}else{
    			cList = new CartList(prof);
    			cartListMap.put(key,cList);
    			cList.setCartable(dataSrc);
    		}	
    	}
		return cList;
	}
	
    /**
     * get the key for the specified profile
     * @param _prof
     * @return
     */
    private static String getCartKey(Profile _prof){
    	return _prof.getEnterprise() + ":" + _prof.getOPWGID();
    }

    /**
     * get the key for the current cart
     * @return
     */
    protected String getCartKey(){
    	return getCartKey(getEntityList().getProfile());
    }
	/**
     * cartList
     * @param prof
     */
    private CartList(Profile prof) {
		try {
			//  This is used to create a local cart for any client.
		    //  It is basically an empty Shell
			list = new EntityList(prof);
		} catch (MiddlewareRequestException mre) {
			NavCartFrame.logger.log(Level.SEVERE,"Error getting local cart entitylist",mre);
		}
	}
 
	/**
     * getEntityList
     * @return
     */
    protected EntityList getEntityList() {
		return list;
	}

	/**
     * getCutGroup
     * @return
     */
    protected EntityGroup getCutGroup() {
		return cutGroup;
	}
    
	/**
     * getEntityGroup 
     * this is where link (WizardPanel) gets the workfolder item from
     * @param _key
     * @return
     */
    public EntityGroup getEntityGroup(String _key) {
    	EntityGroup eg = list.getEntityGroup(_key);
    	if(eg ==null){
    		NavCartFrame.logger.log(Level.INFO,this.toString());
    	}
    	return eg;
	}
    
	/**
     * list the contents of the cart - used for debug
     */
    public String toString(){
    	StringBuilder sb = new StringBuilder();
    	if(list==null){
    		sb.append("List: null"+NEWLINE);
    	}else{
            EntityGroup peg =list.getParentEntityGroup();
            if (peg!=null)  {
                sb.append("List Parent EG: ");
        		listGroup(peg, sb);
            }
            sb.append("List EG count: "+list.getEntityGroupCount()+NEWLINE);
            for (int i=0; i<list.getEntityGroupCount(); i++)  {
                EntityGroup eg =list.getEntityGroup(i);
                sb.append("EG: ");
        		listGroup(eg, sb);
            }
    	}
    	if(cutGroup==null){
    		sb.append("CutGroup: null");    		
    	}else{
    		sb.append("CutGroup: ");
    		listGroup(cutGroup, sb);
    	}
    	return sb.toString();
    }
    
    private void listGroup(EntityGroup eg, StringBuilder sb){
		sb.append(eg.getEntityType()+" : "+eg.getEntityItemCount()+" items. "+NEWLINE);
        if (eg.getEntityItemCount()>0)
        {
        	StringBuilder tmpsb = new StringBuilder();  // prevent more than 2048 chars in a line
            tmpsb.append("IDs(");
            for (int e=0; e<eg.getEntityItemCount(); e++)
            {
                tmpsb.append(" "+eg.getEntityItem(e).getEntityID());
                if (tmpsb.length()>256)
                {
                    sb.append(tmpsb.toString()+NEWLINE);
                    tmpsb.setLength(0);
                }
            }
            tmpsb.append(")");
            sb.append(tmpsb.toString());
            sb.append(NEWLINE);		
        }	
    }
    /**
     * called by AddAll2CartAction
     * @return
     */
    protected boolean hasAnyToCart(){ 	
    	return cartable.supportsGetAll() && cartable.hasCartableData(); // is there any cartable data at all
    }
    /**
     * add all items to the cart
     */
    protected void addAllToCart(){
    	EntityItem ei[] = cartable.getAllEntityItems();
    	if(ei!=null){
    		if (cutGroup!=null){
    			for (int i = 0; i < ei.length; i++) {
    				EntityItem item = ei[i];
    				EntityGroup eg = (EntityGroup) item.getParent();

    				// We must first hunt for the entity guy .. It needs to have a parent group
    				if (eg != null) {
    					// cutgroup is actually the association or relator
    					// We now have an ei that is workable. (True entity2type)
    					if(cutGroup.getEntityType().equals(item.getEntityType())){
    						// already exists, dont let user overwrite without removing first
    						//msg6001.1 = Cut Group already exists on WorkFolder.  Please remove it before executing {0}.
    						com.ibm.eacm.ui.UI.showWarning(null, Utils.getResource("msg6001.1","Add All"));
    						return;
    					}
    				}// end eg is valid
    			}// end item loop
    		}

    		addToCart(ei);
    	}
    }
    
    /**
     * check for selected items to add to cart, hasCartableData() also checks for ispast
     * @return
     */
    protected boolean cartableHasSelection(){
    	EntityItem ei[] = cartable.getSelectedEntityItems(false);
    	return (ei!=null && ei.length>0) && cartable.hasCartableData();
    }

    /**
     * called by AddSelected2CartAction
     */
    protected void addSelectedToCart(){
    	EntityItem ei[] = cartable.getSelectedEntityItems(true);
    	if(ei!=null){
    		if (cutGroup!=null){
    			for (int i = 0; i < ei.length; i++) {
    				EntityItem item = ei[i];
    				EntityGroup eg = (EntityGroup) item.getParent();

    				// We must first hunt for the entity guy .. It needs to have a parent group
    				if (eg != null) {
    					// cutgroup is actually the association or relator
    					if(cutGroup.getEntityType().equals(item.getEntityType())){
    						// already exists, dont let user overwrite without removing first
    						//msg6001.1 = Cut Group already exists on WorkFolder.  Please remove it before executing {0}.
    						com.ibm.eacm.ui.UI.showWarning(null,Utils.getResource("msg6001.1","Add Selected"));
    						return;
    					}
    				}// end eg is valid
    			}// end item loop
    		}

    		addToCart(ei);
    	}
    }
    
    /**
     * what does this really do?
     * @param _ei
     */
    public void addCutItems(EntityItem[] _ei) {
		// need to disable actions while working, frame may be open
		if (_ei != null && cartFrame!=null){
			cartFrame.disableActionsAndWait();
		}
		
    	clearCutGroup();
    	
    	if (_ei != null)  {
    		// this part needs to go into bg task
    		cloneWorker = new CloneWorker(_ei);
    		RMIMgr.getRmiMgr().execute(cloneWorker);
    	}

	}
    /**
     * cut action maybe obsolete, for now prevent user from adding cutitems if the same entitygroup already
     * exists
     * @param aei
     * @return
     */
    public boolean canAddCutItems(EntityItem[] aei) {
    	for (int i = 0; i < aei.length; i++) {
    		EntityItem ei = aei[i];
    		EntityGroup eg = (EntityGroup) ei.getParent();

    		if (eg != null) {
    			// cutgroup hangs onto assoc or relator, but the child entitygroup also exists, it just isnt visible
    			eg = list.getEntityGroup(ei.getEntityType());
    			if(eg !=null && eg == cutGroup){
    				continue;  // cutgroup already exists, it will be replaced
    			}
    			
    			ei = getItemForCart(ei);

    			// We now have an ei that is workable. (True entity2type)
    			eg = list.getEntityGroup(ei.getEntityType());
    			if(eg !=null){
    				return false;  // already exists, dont let user overwrite without removing first
    			}
    		}// end eg is valid
    	}// end item loop
    	return true;
    }
    /**
     * @param ei
     * @return
     */
    private EntityItem getItemForCart(EntityItem ei){
    	boolean bGetDownLink = true;
		EntityGroup eg = (EntityGroup) ei.getParent();

		// We must first hunt for the entity guy .. It needs to have a parent group
		if (eg != null) {
			EntityList el = (EntityList) eg.getParent();
			if (el != null) {
				EANActionItem ai = el.getParentActionItem();
				if (ai instanceof NavActionItem) {
					NavActionItem nai = (NavActionItem) ai;
					if (nai.isNoDownLink(ei.getEntityType())) {
						bGetDownLink = false;
					}
				}
			}

			if (bGetDownLink) {
				if (eg.isRelator() || eg.isAssoc()) {
					String strEntity2Type = eg.getEntity2Type();
					for (int ii = 0; ii < ei.getDownLinkCount(); ii++) {
						EntityItem eiDown = (EntityItem) ei.getDownLink(ii);
						EntityGroup egDown = (EntityGroup) eiDown.getParent();
						if (egDown.isRelator() || egDown.isAssoc()) {
							// this is for 3.0a, relators can have links to entities
							//ei = _aei[i];
							break;
						}
						else {
							if (eiDown.getEntityType().equals(strEntity2Type)) {
								ei = eiDown;
								break;
							}
						}
					}
				}
			}
		}// end eg is valid
		return ei;
    }
    /**
     * @return
     * @throws RemoteException 
     */
    private RemoteDatabaseInterface ro() throws RemoteException  {
    	return RMIMgr.getRmiMgr().getRemoteDatabaseInterface();
    }

	/**
     * clearAll 
     */
    protected void clearAll() {
		if (cartFrame!=null){
			cartFrame.disableActionsAndWait();
		}
		clearCutGroup();
		
		while (list.getEntityGroupCount() > 0) {
			EntityGroup eg = list.getEntityGroup(0);
			if (cartFrame!=null){
				cartFrame.removeTab(eg.getKey());
			}
			while (eg.getEntityItemCount() > 0) {
				EntityItem ei = eg.getEntityItem(0);
				eg.removeEntityItem(ei);
			}
			list.removeEntityGroup(eg);
		}
		
		if (cartFrame!=null){
			cartFrame.enableActionsAndRestore();
		}
		NavCartFrame.logger.log(AlwaysLevel.ALWAYS,"Cleared workfolder");
	} 
	/**
     * clearCutGroup and its hidden child entitygroup
     */
    protected void clearCutGroup() {
    	if (cutGroup != null) {
    		if (cutGroup.getEntityItemCount() > 0){
    			EntityItem cutitem = cutGroup.getEntityItem(0); // this could be an assoc or relator
    			// find group for the child, it will not be a displayed tab
    			cutitem = getItemForCart(cutitem);
    			if (cutitem !=null){
    				EntityGroup eg = list.getEntityGroup(cutitem.getEntityType());
    				if (eg!=null){
    					while (eg.getEntityItemCount() > 0) {
    	    				EntityItem ei = eg.getEntityItem(0);
    	    				eg.removeEntityItem(ei);
    	    			}
    					list.removeEntityGroup(eg);
    				}
    			}
    			// remove all cut items
    			while (cutGroup.getEntityItemCount() > 0) {
    				EntityItem ei = cutGroup.getEntityItem(0);
    				cutGroup.removeEntityItem(ei);
    			}
    		}
    		if (cartFrame!=null){
    			cartFrame.removeTab(cutGroup.getKey());
    		}
    		list.removeEntityGroup(cutGroup);
    		
    		cutGroup =null;
    	}
	}
    
    /**
     * add entityitems to the cart
     * @param _ei
     */
    private void addToCart(EntityItem[] _ei) {
    	HashMap<String, Vector<EntityItem>> map = new HashMap<String, Vector<EntityItem>>();
    	for (int i=0; i < _ei.length; i++) {
    		if (!_ei[i].isNew()) {
    			// must allow for different types of items, whereused can do this
    			String type = _ei[i].getEntityType();
    			Vector<EntityItem> v = map.get(type);
    			if (v==null){
    				v = new Vector<EntityItem>();
    				map.put(type, v);
    			}
    			v.add(_ei[i]);
    		}
    	}
    	// need to disable actions while working, even if called from navigate, frame may be open
    	if (cartFrame!=null){
    		cartFrame.disableActionsAndWait();
    	}

    	// this part needs to go into bg task
    	addGrpWorker = new AddGroupWorker(map);
    	RMIMgr.getRmiMgr().execute(addGrpWorker);
    }
    /**
     * check to see if any of these items are currently in the cart, they were deleted
     * @param _ei
     */
    public void updateDeletedCartItems(EntityItem[] _ei) {
    	if (_ei != null && _ei.length>0) {
    		Vector<EntityItem> delItemVct = new Vector<EntityItem>(_ei.length);
    		// look thru the items and see if cart has any, all items are the same type???
    	  	for (int i=0; i<_ei.length; i++){
    	  		EntityItem item = getItemForCart(_ei[i]);
    	  		delItemVct.add(item);
    	  	}
    		EntityItem delitem = delItemVct.firstElement();
    		EntityGroup eg = list.getEntityGroup(delitem.getEntityType());
    	  
    		if(eg!=null){
    			Vector<EntityItem> tmp = new Vector<EntityItem>();
    			for (int i=0; i<delItemVct.size(); i++){
    				EntityItem item =delItemVct.elementAt(i);
    				EntityItem cartitem = eg.getEntityItem(item.getKey());
    				if (cartitem !=null){
    					tmp.add(cartitem);
    				}
    			}
    			if (tmp.size()>0){
    				// need to disable actions while working, even if called from navigate, frame may be open
    				if (cartFrame!=null){
    					cartFrame.disableActionsAndWait();
    					EntityItem[] eia = new EntityItem[tmp.size()];
    					tmp.copyInto(eia);
    					cartFrame.removeDeletedItems(eg,eia);
    					cartFrame.enableActionsAndRestore();
    				}else{
    					// remove the selected item(s) from the group
    					for (int i=0;i<tmp.size();++i) {
    						eg.removeEntityItem(tmp.elementAt(i));
    					}
    					if (eg.getEntityItemCount() == 0) { // if all items were removed, remove the group too
    						list.removeEntityGroup(eg);
    					}
    				}
    				tmp.clear();
    			}
    		}
    		// cutgroup hangs onto the assoc or relator
    		if (cutGroup !=null && cutGroup.getEntityType().equals(_ei[0].getEntityType())){
    			Vector<EntityItem> tmp = new Vector<EntityItem>();
    			for (int i=0; i<_ei.length; i++){
    				EntityItem item = _ei[i];
    				EntityItem cartitem = cutGroup.getEntityItem(item.getKey());
    				if (cartitem !=null){
    					tmp.add(cartitem);
    				}
    			}
    			if (tmp.size()>0){
    				// need to disable actions while working, even if called from navigate, frame may be open
    				if (cartFrame!=null){
    					cartFrame.disableActionsAndWait();
    					EntityItem[] eia = new EntityItem[tmp.size()];
    					tmp.copyInto(eia);
    					cartFrame.removeDeletedItems(cutGroup, eia);
    					cartFrame.enableActionsAndRestore();
    				}else{
    					// remove the selected item(s) from the group
    					for (int i=0;i<tmp.size();++i) {
    						cutGroup.removeEntityItem(tmp.elementAt(i));
    					}
    					if (cutGroup.getEntityItemCount() == 0) { // if all items were removed, remove the group too
    						clearCutGroup();
    					}
    				}
    				tmp.clear();
    			}
    		}
    		delItemVct.clear();
    	}
    }
    

    protected void cancelWorkers(){
		if (addGrpWorker!=null){
			//addGrpWorker.cancel(true);
			RMIMgr.getRmiMgr().cancelWorker(addGrpWorker,true);
		}
		if (cloneWorker!=null){
			//cloneWorker.cancel(true);
			RMIMgr.getRmiMgr().cancelWorker(cloneWorker,true);
		}
    }
    private class AddGroupWorker extends DBSwingWorker<Vector<EntityGroup>, Void> { 
    	private HashMap<String, Vector<EntityItem>> map =null;
    	AddGroupWorker(HashMap<String, Vector<EntityItem>> m){
    		map = m;
    	}

    	@Override
    	public Vector<EntityGroup> doInBackground() {
    		Vector<EntityGroup> vct = new Vector<EntityGroup>();
    		try{
    	  		Iterator<Vector<EntityItem>> itr = map.values().iterator();
        		while (itr.hasNext()) {
        			Vector<?> tmp  = (Vector<?>)itr.next();
        			EntityItem eia[] = new EntityItem[tmp.size()];
        			tmp.copyInto(eia);
        			EntityGroup eg = addGroupToCart(eia);
        			if (eg != null) {
        				vct.add(eg);
        			}
        			tmp.clear();
        		}
    		}catch(Exception ex){ // prevent hang
    			NavCartFrame.logger.log(Level.SEVERE,"CartList.AddGroupWorker.doInBackground()",ex);
    		}finally{
    			map.clear();
    			RMIMgr.getRmiMgr().complete(this); // done accessing pdh, allow another to run
    			addGrpWorker = null;  // nothing to cancel now
    		}
			return vct;
    	}

        @Override
        public void done() {
        	//this will be on the dispatch thread
        	try {
        		if(!isCancelled()){
        			Vector<EntityGroup> vct = get();
        			if (vct != null) { 
        				for (int i=0; i<vct.size(); i++){
        					// add this entitygroup as a new tab
        					if (cartFrame!=null){
        						cartFrame.refreshTab(vct.elementAt(i));
        					}
        				}
        				vct.clear();
        	    		NavCartFrame.logger.log(Level.INFO,this.toString());
        	    	} 
        		}
            } catch (InterruptedException ignore) {}
            catch (java.util.concurrent.ExecutionException e) {
            	listErr(e,"adding groups to cart");
            }finally{
        		if (cartFrame!=null){
        			cartFrame.enableActionsAndRestore();
        		}
            }
        }
    	public void notExecuted(){
    		NavCartFrame.logger.log(Level.WARNING,"CartList.AddGroupWorker not executed");
    		if (cartFrame!=null){
    			cartFrame.enableActionsAndRestore();
    		}
    		addGrpWorker = null; 
    	}
    }   
    /**
     * this creates a new entitygroup and adds it to the cart entitylist
     * done in a background thread
     */
    private EntityGroup addGroupToCart(EntityItem[] _ei) {
    	EntityGroup out = null;
    	try {
    		out = getEntityList().addToCart(ro(), _ei, true);
    	} catch (Exception _ex) {
    		if(RMIMgr.shouldTryReconnect(_ex) && // try to reconnect
    				RMIMgr.getRmiMgr().reconnectMain()){
    			try {
    				out = getEntityList().addToCart(ro(), _ei, true);
    			}catch (Exception _x) {
    				com.ibm.eacm.ui.UI.showException(cartFrame,_x, "mw.err-title");
    			}
    		}else{
    			com.ibm.eacm.ui.UI.showException(cartFrame,_ex, "mw.err-title");
    		}
    	}
        return out;
    }
    //=====================================================================================
    // clone worker
    private class CloneWorker extends DBSwingWorker<EntityGroup, Void> { 
    	private EntityItem[] eiArray =null;
    	CloneWorker(EntityItem[] ei){
    		eiArray = ei;
    	}

    	@Override
    	public EntityGroup doInBackground() {
    		EntityGroup grp=null;
    		try{
    			grp = cloneEntityItem(eiArray);
    		}catch(Exception ex){ // prevent hang
    			NavCartFrame.logger.log(Level.SEVERE,"CartList.CloneWorker.doInBackground()",ex);
    		}finally{
    			eiArray =null;
    			RMIMgr.getRmiMgr().complete(this); // done accessing pdh, allow another to run
    			cloneWorker = null;  // nothing to cancel now
    		}
			return grp;
    	}

        @Override
        public void done() {
        	//this will be on the dispatch thread
        	try {
        		if(!isCancelled()){
        			EntityGroup grp= get();
        			if (grp != null) { 
        				cutGroup =grp;
        				cutGroup.putLongDescription("Move");
            			if (cartFrame!=null){
            				cartFrame.refreshTab(cutGroup);
            			}
        	    		NavCartFrame.logger.log(Level.INFO,this.toString());
        			} 
        		}
            } catch (InterruptedException ignore) {}
            catch (java.util.concurrent.ExecutionException e) {
            	listErr(e,"adding cutgroup");
            }finally{
        		if (cartFrame!=null){
        			cartFrame.enableActionsAndRestore();
        		}
            }
        }
    	/* (non-Javadoc)
    	 * @see com.ibm.eacm.mw.DBSwingWorker#notExecuted()
    	 */
    	public void notExecuted(){
    		NavCartFrame.logger.log(Level.WARNING,"CartList.CloneWorker not executed");
    		if (cartFrame!=null){
    			cartFrame.enableActionsAndRestore();
    		}
    		cloneWorker = null; 
    	}
    }  
    /**
     * cloneEntityItem
     * done in a background thread
     * throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException
     */
    private EntityGroup cloneEntityItem(EntityItem[] _ei) {
        EntityGroup out = null;
        try {
        	out = EANUtility.cloneEntityItems(getEntityList(), getEntityList().getProfile(), ro(), _ei);
        } catch (Exception _ex) {
        	if(RMIMgr.shouldTryReconnect(_ex) && // try to reconnect
        			RMIMgr.getRmiMgr().reconnectMain()){
        		try {
        			out = EANUtility.cloneEntityItems(getEntityList(), getEntityList().getProfile(), ro(), _ei);
        		}catch (Exception _x) {
        			com.ibm.eacm.ui.UI.showException(cartFrame,_x, "mw.err-title");
        		}
        	}else{
        		com.ibm.eacm.ui.UI.showException(cartFrame,_ex, "mw.err-title");
        	}
    	}

        return out;
    }
    //=====================================================================================
    // must notify actions when changing
    protected synchronized void addPropertyChangeListener(String propertyName,PropertyChangeListener listener) {
		if (listener == null) {
			return;
		}
		if (changeSupport == null) {
			changeSupport = new PropertyChangeSupport(this);
		}
		// add listener to this cart - may need to add getCartKey() if conflicts with other tabs and actions
		changeSupport.addPropertyChangeListener(propertyName,listener);
	}
	protected synchronized void removePropertyChangeListener(String propertyName,PropertyChangeListener listener) {
		if (listener == null || changeSupport == null) {
			return;
		}
		changeSupport.removePropertyChangeListener(propertyName,listener);
	}

	protected void firePropertyChange(String propertyName,
			boolean oldValue, boolean newValue) {
		if (changeSupport == null || oldValue == newValue) {
			return;
		}
		changeSupport.firePropertyChange(propertyName, oldValue, newValue);
	}
}
