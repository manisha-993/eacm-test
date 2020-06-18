//
// Copyright (c) 2003, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: ActionItemHistory.java,v $
// Revision 1.4  2010/02/15 17:37:24  wendy
// prevent null ptr
//
// Revision 1.3  2009/05/11 15:49:12  wendy
// Support dereference for memory release
//
// Revision 1.2  2004/04/21 23:06:14  gregg
// updateProfile
//
// Revision 1.1  2004/01/09 22:04:04  gregg
// Adding in ActionItemHistory
//
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.*;
import java.io.Serializable;


/**
 * Small structure to hold info about an EANActionItem's History - the 'trail' of Actions/Entities 
 * leading up to it, etc...
 * <!-- Usefull when storing an ActionItem as a bookmark -->
 */
public final class ActionItemHistory implements Serializable {
 
    private EANList m_elActionItems = null;
    private String m_strSelectionKey = null;
    private String[] m_aStrSelectionKeys = null;
    
 /**
  * @serial
  */
    static final long serialVersionUID = 1L;
    
    public void dereference(){
    	if (m_elActionItems !=null){
    		for (int i=0; i<m_elActionItems.size(); i++){
    			EANActionItem actionItem  = (EANActionItem) m_elActionItems.getAt(i);
    			actionItem.dereference();
    		}
    		m_elActionItems.clear();
    		m_elActionItems = null;
    	}
    	m_strSelectionKey = null;
    	m_aStrSelectionKeys = null;
    }
    public ActionItemHistory() {
            
    }
    
/**
 * Store a trail of ActionItems leading up to this one (a 'history').
 * <!-- functionality Added per CR #1210035324.-->
 */
    public void setActionItems(EANList _elActions) throws MiddlewareRequestException {
        if(_elActions == null) {
            return;    
        }
        m_elActionItems = new EANList();
        for(int i = 0; i < _elActions.size(); i++) {
            EANActionItem actionItem = (EANActionItem)_elActions.getAt(i);
            // we are only dealing w/ Search and Nav ActionItems...
            if(actionItem instanceof NavActionItem) {
                m_elActionItems.put(new NavActionItem((NavActionItem)actionItem));        
            } else if(actionItem instanceof SearchActionItem) {
                m_elActionItems.put(new SearchActionItem((SearchActionItem)actionItem));        
            }
        }
    }
    
/**
 * Update Profile in alll Items in history
 */
    protected void updateProfile(Profile _prof) {
    	if (m_elActionItems !=null){
    		for(int i = 0; i < m_elActionItems.size(); i++) {
    			((EANActionItem)m_elActionItems.getAt(i)).setProfile(_prof);   
    		}
    	}
    }
    
    public EANList getActionItems() {
        return m_elActionItems;    
    }
    
    public void setSelectionKey(String _s) {
        m_strSelectionKey = _s;   
    }
    
    public String getSelectionKey() {
        return m_strSelectionKey;   
    }
    
    public void setSelectionKeys(String[] _as) {
        m_aStrSelectionKeys = _as;   
    }
    
    public String[] getSelectionKeys() {
        return m_aStrSelectionKeys;   
    }

}

