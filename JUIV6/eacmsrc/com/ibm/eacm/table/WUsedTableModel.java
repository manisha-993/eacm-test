//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.table;


import com.ibm.eacm.mw.RMIMgr;
import com.ibm.eacm.objects.EACMGlobals;
import com.ibm.eacm.objects.Utils;

import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.Profile;

import java.awt.Component;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *  this represents a RowSelectableTable as a java tablemodel supporting WhereUsed action
 * @author Wendy Stimpson
 */
// $Log: WUsedTableModel.java,v $
// Revision 1.2  2013/09/13 18:31:05  wendy
// add more debug msgs
//
// Revision 1.1  2012/09/27 19:39:12  wendy
// Initial code
//
public class WUsedTableModel extends RSTTableModel implements EACMGlobals
{
	private static final long serialVersionUID = 1L;

	/**
	 * @param table
	 */
	public WUsedTableModel(RSTTable table){
		super(table);
	}

    /**
     * @param _row
     * @return
     */
    public WhereUsedItem getWhereUsedItemForRow(int _row) {
        return (WhereUsedItem)super.getRowEAN(_row);
    }

//  ====================================================================
//  removelink support
    /**
     * called from the action tab worker
     * @param _s
     * this is called on the bg worker thread
     */
    protected boolean removeLink(Profile prof, String[] _s) {
    	int[] iArray = new int[_s.length];
    	for (int i = 0; i < _s.length; ++i) {
    		iArray[i] = getRSTable().getRowIndex(_s[i]);
    	}

    	FilterGroup fg = getFilterGroup(prof);
    	getRSTable().setFilterGroup(null);  // dont let rstable do the filtering after the link

    	boolean ok = false;
    	long t11 =System.currentTimeMillis();
		Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting");
        try {
        	ok = getRSTable().removeLink(iArray, null, ro(), prof);
        } catch (EANBusinessRuleException _bre) {
        	com.ibm.eacm.ui.UI.showException(null,_bre);
        	_bre.dereference();
        } catch (Exception exc) {
        	if(RMIMgr.shouldTryReconnect(exc) &&	// try to reconnect
        			RMIMgr.getRmiMgr().reconnectMain()) {
        		try{
        			ok = getRSTable().removeLink(iArray, null, ro(), prof);
        		}catch (EANBusinessRuleException _bre) {
        			com.ibm.eacm.ui.UI.showException(null,_bre);
        			_bre.dereference();
        		}catch(Exception e){
        			com.ibm.eacm.ui.UI.showException(null,e, "mw.err-title");
        		}
        	}else{
        		com.ibm.eacm.ui.UI.showException(null,exc, "mw.err-title");
        	}
        }

		Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"removelink ended "+Utils.getDuration(t11));

       // refresh();
       	getRSTable().setFilterGroup(fg);
        return ok;
    }

//  ====================================================================
// link support
    /**
     * this is called on the worker thread
     * @param selectedWUItemkeys
     * @param itemsToLink
     * @param _linkType
     * @param _c
     * @return
     */
    protected EANFoundation[] link(String[] selectedWUItemkeys, EntityItem[] itemsToLink, String _linkType,
    		Profile prof, Component _c) {
    	int[] iArray = null;
    	// prevent cross link, only first type is valid, user may have selected other types
    	String type = null;
    	Vector<Integer> ivct = new Vector<Integer>(); // only link similar types
    	for (int i = 0; i < selectedWUItemkeys.length; ++i) {
    		int mdlrowid = getRSTable().getRowIndex(selectedWUItemkeys[i]);
    		WhereUsedItem wui = getWhereUsedItemForRow(mdlrowid);
    		EntityItem origEI = wui.getRelatedEntityItem();
    		if(type==null){ // first one is valid, that is used to enable the link action
    			type = origEI.getEntityType();
    			ivct.add(new Integer(mdlrowid));
    			Logger.getLogger(APP_PKG_NAME).log(Level.FINE,"linking row from "+selectedWUItemkeys[i]);
    		}else if(type.equals(origEI.getEntityType())){
    			ivct.add(new Integer(mdlrowid));    			
    			Logger.getLogger(APP_PKG_NAME).log(Level.FINE,"linking row from "+selectedWUItemkeys[i]);
    		}else{
    			Logger.getLogger(APP_PKG_NAME).log(Level.WARNING,"link for "+type+" so BYPASSING selectedWUItemkeys["+i+"] "+selectedWUItemkeys[i]);
    		}
    	}
    	for(int ie=0;ie<itemsToLink.length; ie++){
    		Logger.getLogger(APP_PKG_NAME).log(Level.FINE,"linking to "+itemsToLink[ie].getKey());
    	}


    	iArray = new int[ivct.size()];
    	for (int i=0; i<ivct.size(); i++){
    		iArray[i]=ivct.elementAt(i);
    	}
    	ivct.clear();

    	FilterGroup fg = getFilterGroup(prof);
    	getRSTable().setFilterGroup(null);  // dont let rstable do the filtering after the link
    	long t11 =System.currentTimeMillis();
		Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"link starting");
        EANFoundation[] added = null;
        try {
        	added = getRSTable().link(iArray, null, ro(), prof, itemsToLink, _linkType);
        } catch (EANBusinessRuleException _bre) {
        	com.ibm.eacm.ui.UI.showException(_c,_bre);
            _bre.dereference();
        } catch (Exception exc) {
        	if(RMIMgr.shouldTryReconnect(exc) &&	// try to reconnect
        			RMIMgr.getRmiMgr().reconnectMain()) {
                try {
                	added = getRSTable().link(iArray, null, ro(), prof, itemsToLink, _linkType);
                } catch (EANBusinessRuleException _bre) {
                	com.ibm.eacm.ui.UI.showException(_c,_bre);
                    _bre.dereference();
                } catch(Exception ex){
            		com.ibm.eacm.ui.UI.showException(_c,ex, "mw.err-title");
                }
            } else {
        		com.ibm.eacm.ui.UI.showException(_c,exc, "mw.err-title");
            }
        }

		Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"link1 ended "+Utils.getDuration(t11));

        if ( getRSTable()!=null){ // can happen if user closed the tab while link was executing
        	//refresh(); // update the model information
        	getRSTable().setFilterGroup(fg);  // restore any filtergroup
        }
        
        if(added !=null){
        	for(int ie=0;ie<added.length; ie++){
        		Logger.getLogger(APP_PKG_NAME).log(Level.FINE,"added "+added[ie].getKey());
        	}
        }

        return added;
    }

//  ====================================================================
//  chainlink support     not tested at all  -didnt find any meta for it
    protected Object linkChain(LinkActionItem _lai, EntityItem[] _parent, EntityItem[] _child, Profile prof, Component _c) {
        Object obj = null;
        long t1 =System.currentTimeMillis();
		Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting");
        if (_parent != null && _child != null) {
            _lai.setParentEntityItems(_parent);
            _lai.setChildEntityItems(_child);
            obj = link(_lai, prof, _c);
        }

		Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"linkchain ended "+Utils.getDuration(t1));
        updatedModel();// TODO this must not be on bg worker thread
        return obj;
    }

    private Object link(LinkActionItem _lai, Profile prof, Component _c) {
    	Object o = null;

    	try {
    		o = getRSTable().link(null, ro(), prof, _lai);
    	} catch(DomainException de) {
    		com.ibm.eacm.ui.UI.showException(_c,de);
    		de.dereference();
    	} catch(EntityItemException eie){
    		com.ibm.eacm.ui.UI.showException(_c,eie);
    		eie.dereference();
    	} catch (Exception exc) {
        	if(RMIMgr.shouldTryReconnect(exc) &&	// try to reconnect
        			RMIMgr.getRmiMgr().reconnectMain()) {
        		try{
        			o = getRSTable().link(null, ro(), prof, _lai);
        		}catch(Exception e){
        			if(RMIMgr.shouldPromptUser(exc)){
        				if (com.ibm.eacm.ui.UI.showMWExcPrompt(_c, exc) == RETRY) {
        					o= link( _lai, prof, _c);
        				}// else user decide to ignore or exit
        			}else{
        				com.ibm.eacm.ui.UI.showException(_c,exc, "mw.err-title");
        			}
        		}
        	} else{ // show user msg and ask what to do
    			if(RMIMgr.shouldPromptUser(exc)){
    				if (com.ibm.eacm.ui.UI.showMWExcPrompt(_c, exc) == RETRY) {
    					o= link( _lai, prof, _c);
    				}// else user decide to ignore or exit
    			}else{
    				com.ibm.eacm.ui.UI.showException(_c,exc, "mw.err-title");
    			}
    		}
        }
        return o;
    }
}
