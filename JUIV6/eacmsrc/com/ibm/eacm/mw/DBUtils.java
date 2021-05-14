//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.mw;

import java.awt.Component;
import java.net.URLEncoder;
import java.rmi.RemoteException;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import COM.ibm.eannounce.objects.*;

import COM.ibm.opicmpdh.middleware.LockException;
import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;
import COM.ibm.opicmpdh.middleware.SearchExceedsLimitException;

import com.ibm.eacm.objects.EACMProperties;
import com.ibm.eacm.objects.EACMGlobals;
import com.ibm.eacm.objects.Routines;
import com.ibm.eacm.objects.Utils;


/**
 * Database Utilities
 * @author Wendy Stimpson
 */
//$Log: DBUtils.java,v $
//Revision 1.6  2014/02/24 15:09:33  wendy
//RCQ285768 - view cached XML in JUI
//
//Revision 1.5  2014/01/24 12:47:12  wendy
//getro() throw remoteexception to allow reconnect
//
//Revision 1.4  2013/03/14 17:29:27  wendy
//log entityitemexceptions for link
//
//Revision 1.3  2013/02/05 18:23:21  wendy
//throw/handle exception if ro is null
//
//Revision 1.2  2012/11/12 22:47:48  wendy
//AutoUpdate changes
//
//Revision 1.1  2012/09/27 19:39:21  wendy
//Initial code
//
public class DBUtils implements EACMGlobals {
	private DBUtils(){};

	/**
	 * get RemoteDatabaseInterface for db access
	 * @return
	 * @throws RemoteException 
	 */
	protected static RemoteDatabaseInterface ro() throws RemoteException  {
		return RMIMgr.getRmiMgr().getRemoteDatabaseInterface();
	}

	/**
	 * called by gridtable
	 * @param table
	 * @param row
	 * @param ll
	 * @param prof
	 * @param owner
	 * @param lockType
	 * 
	 */
	public static void unlockEntityItems(RowSelectableTable table, int[] row, LockList ll, 
			Profile prof, EntityItem owner, int lockType) 
	{
		long t1 =System.currentTimeMillis();
		Logger.getLogger(TIMING_LOGGER).log(Level.INFO," starting");
		try {
			table.unlockEntityItems(row, ro(), null, ll, prof, owner, lockType);
		} catch (Exception exc) {
			if(RMIMgr.shouldTryReconnect(exc)){	// try to reconnect
				if (RMIMgr.getRmiMgr().reconnectMain()) {
					try {
						table.unlockEntityItems(row, ro(), null, ll, prof, owner, lockType);
					} catch (Exception e) {
						com.ibm.eacm.ui.UI.showException(null,e, "mw.err-title");
					}
				} else{	// reconnect failed
					com.ibm.eacm.ui.UI.showException(null,exc, "mw.err-title");
				}
			}else{
				com.ibm.eacm.ui.UI.showException(null,exc, "mw.err-title");
			}
		}
		Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"unlock ended "+Utils.getDuration(t1));
		Utils.monitor("unlockEntityItems", ll);
	}

	/**
	 * called by gridtable
	 * @param table
	 * @param row
	 * @param ll
	 * @param prof
	 * @param owner
	 * @param lockType
	 */
	public static void lockEntityItems(RowSelectableTable table, int[] row, LockList ll, Profile prof, 
			EntityItem owner, int lockType) 
	{
		long t1 =System.currentTimeMillis();
		Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting");
		try {
			table.lockEntityItems(row, ro(), null, ll, prof, owner, lockType, true);
		} catch (Exception exc) {
			if(RMIMgr.shouldTryReconnect(exc)){	// try to reconnect
				if (RMIMgr.getRmiMgr().reconnectMain()) {
					try {
						table.lockEntityItems(row, ro(), null, ll, prof, owner, lockType, true);
					} catch (Exception e) {
						com.ibm.eacm.ui.UI.showException(null,e, "mw.err-title");
					}
				} else{	// reconnect failed
					com.ibm.eacm.ui.UI.showException(null,exc, "mw.err-title");
				}
			}else{
				com.ibm.eacm.ui.UI.showException(null,exc, "mw.err-title");
			}
		}
		Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"lock ended "+Utils.getDuration(t1));
		Utils.monitor("lockEntityItems", ll);
	}

	/**
	 * called by verttable and editform
	 * @param table
	 * @param ll
	 * @param prof
	 * @param owner
	 * @param lockType
	 * 
	 */
	public static void unlock(RowSelectableTable table, LockList ll, Profile prof, EntityItem owner, 
			int lockType) 
	{
		long t1 = System.currentTimeMillis();
		Logger.getLogger(TIMING_LOGGER).log(Level.INFO," starting");
		try {
			table.unlock(ro(), null, ll, prof, owner, lockType);
		} catch (Exception exc) {
			if(RMIMgr.shouldTryReconnect(exc)){	// try to reconnect
				if (RMIMgr.getRmiMgr().reconnectMain()) {
					try {
						table.unlock(ro(), null, ll, prof, owner, lockType);
					} catch (Exception e) {
						com.ibm.eacm.ui.UI.showException(null,e, "mw.err-title");
					}
				} else{	// reconnect failed
					com.ibm.eacm.ui.UI.showException(null,exc, "mw.err-title");
				}
			}else{
				com.ibm.eacm.ui.UI.showException(null,exc, "mw.err-title");
			}
		}
		Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"unlock ended "+Utils.getDuration(t1));
		Utils.monitor("unlock", ll);
	}

	/**
	 * called by verttable and editform
	 * @param table
	 * @param ll
	 * @param prof
	 * @param owner
	 * @param lockType
	 */
	public static void lock(RowSelectableTable table, LockList ll, Profile prof, EntityItem owner, 
			int lockType) 
	{
		long t1 =System.currentTimeMillis();
		Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting");
		try {
			table.lock(ro(), null, ll, prof, owner, lockType);
		}catch (Exception exc) {
			if(RMIMgr.shouldTryReconnect(exc)){	// try to reconnect
				if (RMIMgr.getRmiMgr().reconnectMain()) {
					try {
						table.lock(ro(), null, ll, prof, owner, lockType);
					} catch (Exception e) {
						com.ibm.eacm.ui.UI.showException(null,e, "mw.err-title");
					}
				} else{	// reconnect failed
					com.ibm.eacm.ui.UI.showException(null,exc, "mw.err-title");
				}
			}else{
				com.ibm.eacm.ui.UI.showException(null,exc, "mw.err-title");
			}
		}
		Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"lock ended "+Utils.getDuration(t1));
		if (owner != null) {
			Utils.monitor("lock", ll);
		}
	}

	/**
	 * commit this entity item
	 * @param ei
	 * @throws Exception
	 */
	public static void commit(EntityItem ei) throws Exception {
		try {
			ei.commit(null, ro());
		} catch (MiddlewareBusinessRuleException mbre) {
			mbre.printStackTrace();
			throw mbre;
		} catch (EANBusinessRuleException bre) {
			bre.printStackTrace();
			throw bre;
		} catch (Exception exc) {
			if(RMIMgr.shouldTryReconnect(exc)){	// try to reconnect
				if (RMIMgr.getRmiMgr().reconnectMain()) {
					try {
						ei.commit(null, ro());
					} catch (MiddlewareBusinessRuleException mbre) {
						mbre.printStackTrace();
						throw mbre;
					} catch (EANBusinessRuleException bre) {
						bre.printStackTrace();
						throw bre;
					}catch (Exception e) {
						if(RMIMgr.shouldPromptUser(e)){// show user msg and ask what to do
							if (com.ibm.eacm.ui.UI.showMWExcPrompt(null, e) == RETRY) {
								commit(ei);
							}
						}else{ 
							com.ibm.eacm.ui.UI.showException(null,e, "mw.err-title");
						}
					}
				} else{	// reconnect failed
					com.ibm.eacm.ui.UI.showException(null,exc, "mw.err-title");
				}
			}else{ // show user msg and ask what to do
				if(RMIMgr.shouldPromptUser(exc)){
					if (com.ibm.eacm.ui.UI.showMWExcPrompt(null, exc) == RETRY) {
						commit(ei);
					}// else user decide to ignore or exit	
				}else{
					com.ibm.eacm.ui.UI.showException(null,exc, "mw.err-title");
				}
			}
		}
	}

	public static byte[] getBlobValue(EANBlobAttribute att, Component comp) {
		byte[] out = null;
		try {
			out = att.getBlobValue(ro(), null);
		} catch (Exception exc) {
			if(RMIMgr.shouldTryReconnect(exc)){	// try to reconnect
				if (RMIMgr.getRmiMgr().reconnectMain()) {
					try {
						out = att.getBlobValue(ro(), null);
					} catch (Exception e) {
						com.ibm.eacm.ui.UI.showException(comp,e, "mw.err-title");
					}
				} else{	// reconnect failed
					com.ibm.eacm.ui.UI.showException(comp,exc, "mw.err-title");
				}
			}else{
				com.ibm.eacm.ui.UI.showException(comp,exc, "mw.err-title");
			}
		}
		return out;
	}

	public static boolean execWorkflow(WorkflowActionItem flow, Profile prof, Component comp) {
		try {
			flow.rexec(ro(), prof);
			return true;
		}catch(DomainException de) { // RQ0713072645
			com.ibm.eacm.ui.UI.showException(comp,de);
			de.dereference();
		}catch (LockException le) {
			com.ibm.eacm.ui.UI.showErrorMessage(comp,le.toString());
		} catch (Exception exc) {
			if(RMIMgr.shouldTryReconnect(exc)){	// try to reconnect
				if (RMIMgr.getRmiMgr().reconnectMain()) {
					try {
						flow.rexec(ro(), prof);
						return true;
					} catch (LockException le) {
						com.ibm.eacm.ui.UI.showErrorMessage(comp,le.toString());
					}catch (Exception e) {
						if(RMIMgr.shouldPromptUser(e)){// show user msg and ask what to do
							if (com.ibm.eacm.ui.UI.showMWExcPrompt(comp, e) == RETRY) {
								return execWorkflow(flow, prof, comp);
							}
						}else{ 
							com.ibm.eacm.ui.UI.showException(comp,e, "mw.err-title");
						}
					}
				} else{	// reconnect failed
					com.ibm.eacm.ui.UI.showException(comp,exc, "mw.err-title");
				}
			}else{ // show user msg and ask what to do
				if(RMIMgr.shouldPromptUser(exc)){
					if (com.ibm.eacm.ui.UI.showMWExcPrompt(comp, exc) == RETRY) {
						return execWorkflow(flow,prof, comp);
					}// else user decide to ignore or exit	
				}else{
					com.ibm.eacm.ui.UI.showException(comp,exc, "mw.err-title");
				}
			}
		}
		return false;
	}

	/**
	 * @param eai
	 * @param ei
	 * @param prof
	 * @param comp
	 * @return
	 */
	public static EntityList getEdit(EditActionItem eai, EntityItem[] ei,Profile prof, Component comp) {
		EntityList el = null;
		try {
			el = EntityList.getEntityList(ro(), prof, eai, ei);
		} catch (Exception exc) {
			if(RMIMgr.shouldTryReconnect(exc)){	// try to reconnect
				if (RMIMgr.getRmiMgr().reconnectMain()) {
					try {
						el = EntityList.getEntityList(ro(), prof, eai, ei);
					} catch (Exception e) {
						com.ibm.eacm.ui.UI.showException(comp,e, "mw.err-title");
					}
				} else{	// reconnect failed
					com.ibm.eacm.ui.UI.showException(comp,exc, "mw.err-title");
				}
			}else{
				com.ibm.eacm.ui.UI.showException(comp,exc, "mw.err-title");
			}
		}
		return el;
	}
	/**
	 * @param exai
	 * @param ei
	 * @param prof
	 * @param comp
	 * @return
	 */
	public static EntityList getVEEdit(ExtractActionItem exai, EntityItem[] ei,Profile prof, Component comp) {
		EntityList el = null;
		try {
			el = EntityList.getEntityList(ro(), prof, exai, ei);
		} catch (Exception exc) {
			if(RMIMgr.shouldTryReconnect(exc)){	// try to reconnect
				if (RMIMgr.getRmiMgr().reconnectMain()) {
					try {
						el = EntityList.getEntityList(ro(),prof, exai, ei);
					} catch (Exception e) {
						com.ibm.eacm.ui.UI.showException(comp,e, "mw.err-title");
					}
				} else{	// reconnect failed
					com.ibm.eacm.ui.UI.showException(comp,exc, "mw.err-title");
				}
			}else{
				com.ibm.eacm.ui.UI.showException(comp,exc, "mw.err-title");
			}
		}
		return el;
	}
	/**
	 * called from NavPickFrame.EANLinkActionPL and LinkWizard
	 * @param lai
	 * @param prof
	 * @param c
	 * @return
	 */
	public static Object doLinkAction(LinkActionItem lai, Profile prof, Component c) {
		long t1 = System.currentTimeMillis();
		Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting");
		Object o = null;
		try {
			o = lai.rexec(ro(), prof);
		}catch(DomainException de) { // RQ0713072645
			com.ibm.eacm.ui.UI.showException(c,de);
			de.dereference();
		}catch(EntityItemException mbre){
			Logger.getLogger(APP_PKG_NAME).log(Level.SEVERE, "error",mbre);
			//SR11, SR15 and SR17. dont execute again
			com.ibm.eacm.ui.UI.showException(c,mbre);
		}catch (LockException le) {
			com.ibm.eacm.ui.UI.showErrorMessage(c,le.toString());
		}catch (Exception exc) {
			if(RMIMgr.shouldTryReconnect(exc)){	// try to reconnect
				if (RMIMgr.getRmiMgr().reconnectMain()) {
					try{
						o = lai.rexec(ro(), prof);
					}catch(DomainException de) { // RQ0713072645
						com.ibm.eacm.ui.UI.showException(c,de);
						de.dereference();
					}catch(EntityItemException mbre){
						Logger.getLogger(APP_PKG_NAME).log(Level.SEVERE, "error",mbre);
						//SR11, SR15 and SR17. dont execute again
						com.ibm.eacm.ui.UI.showException(c,mbre);
					}catch (LockException le) {
						com.ibm.eacm.ui.UI.showErrorMessage(c,le.toString());
					}catch(Exception e){
						if(RMIMgr.shouldPromptUser(e)){// show user msg and ask what to do
							if (com.ibm.eacm.ui.UI.showMWExcPrompt(c, e) == RETRY) {
								o = doLinkAction(lai, prof, c);
							}
						}else{ 
							com.ibm.eacm.ui.UI.showException(c,e, "mw.err-title");
						}
					}
				}else{	// reconnect failed
					com.ibm.eacm.ui.UI.showException(c,exc, "mw.err-title");
				}
			}else{ // show user msg and ask what to do
				if(RMIMgr.shouldPromptUser(exc)){
					if (com.ibm.eacm.ui.UI.showMWExcPrompt(c, exc) == RETRY) {
						o = doLinkAction(lai, prof, c);
					}// else user decide to ignore or exit	
				}else{
					com.ibm.eacm.ui.UI.showException(c,exc, "mw.err-title");
				}
			} 
		}

		Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"linkaction ended "+Utils.getDuration(t1));
		return o;
	}

	/**
	 * get MatrixList
	 * @param mai
	 * @param prof
	 * @param c
	 * @return
	 */
	public static MatrixList getMatrixList(MatrixActionItem mai, Profile prof, Component c) {
		MatrixList ml = null;
		try {
			ml = mai.rexec(ro(), prof);
		}catch(DomainException de) { // RQ0713072645
			com.ibm.eacm.ui.UI.showException(c,de);
			de.dereference();
		} catch (Exception exc) {
			if(RMIMgr.shouldTryReconnect(exc)){	// try to reconnect
				if (RMIMgr.getRmiMgr().reconnectMain()) {
					try{
						ml = mai.rexec(ro(), prof);
					}catch(Exception e){
						if(RMIMgr.shouldPromptUser(e)){// show user msg and ask what to do
							if (com.ibm.eacm.ui.UI.showMWExcPrompt(c, e) == RETRY) {
								ml = getMatrixList(mai, prof, c);
							}
						}else{ 
							com.ibm.eacm.ui.UI.showException(c,e, "mw.err-title");
						}
					}
				}else{	// reconnect failed
					com.ibm.eacm.ui.UI.showException(c,exc, "mw.err-title");
				}
			}else{ // show user msg and ask what to do
				if(RMIMgr.shouldPromptUser(exc)){
					if (com.ibm.eacm.ui.UI.showMWExcPrompt(c, exc) == RETRY) {
						ml = getMatrixList(mai, prof, c);
					}// else user decide to ignore or exit	
				}else{
					com.ibm.eacm.ui.UI.showException(c,exc, "mw.err-title");
				}
			} 
		}

		return ml;
	}

	/**
	 * @param del
	 * @param prof
	 * @param c
	 * @return
	 */
	public static boolean doDelete(DeleteActionItem del, Profile prof, Component c) {
		try {
			del.rexec(ro(), prof);
			return true;
		} catch(DomainException de) { // RQ0713072645
			com.ibm.eacm.ui.UI.showException(c,de);
			de.dereference();
		} catch (LockException le) {
			com.ibm.eacm.ui.UI.showErrorMessage(c,le.toString());
		} catch (EANBusinessRuleException ean) { //53761
			com.ibm.eacm.ui.UI.showErrorMessage(c,ean.toString());
		} catch (MiddlewareRequestException mre) { //53832
			com.ibm.eacm.ui.UI.showErrorMessage(c,mre.toString());
		} catch (Exception ex) {
			if(RMIMgr.shouldTryReconnect(ex)){	// try to reconnect
				if (RMIMgr.getRmiMgr().reconnectMain()) {
					try {
						del.rexec(ro(), prof);
						return true;
					} catch (LockException le) {
						com.ibm.eacm.ui.UI.showErrorMessage(c,le.toString());
					} catch (EANBusinessRuleException ean) { //53761
						com.ibm.eacm.ui.UI.showErrorMessage(c,ean.toString());
					} catch (MiddlewareRequestException mre) { //53832
						com.ibm.eacm.ui.UI.showErrorMessage(c,mre.toString());
					} catch (Exception x) {
						//show user msg and ask what to do
						if(RMIMgr.shouldPromptUser(ex)){
							if (com.ibm.eacm.ui.UI.showMWExcPrompt(c, ex) == RETRY) {
								return doDelete(del, prof, c);
							}// else user decide to ignore or exit	
						}else{
							com.ibm.eacm.ui.UI.showException(c,ex, "mw.err-title");
						}
					}
				} else{	// reconnect failed
					com.ibm.eacm.ui.UI.showException(c,ex, "mw.err-title");
				}
			}else{ // show user msg and ask what to do
				if(RMIMgr.shouldPromptUser(ex)){
					if (com.ibm.eacm.ui.UI.showMWExcPrompt(c, ex) == RETRY) {
						return doDelete(del, prof, c);
					}// else user decide to ignore or exit	
				}else{
					com.ibm.eacm.ui.UI.showException(c,ex, "mw.err-title");
				}
			} 
		}
		return false;
	}

	/**
	 * link
	 * called by navcart.
	 * 
	 * @param str
	 * @param eiParent
	 * @param eiChild
	 * @param rel
	 * @param linkType
	 * @param linkCount
	 * @return
	 */
	public static boolean link(String str, Profile prof, EntityItem[] eiParent, EntityItem[] eiChild, MetaLink rel, int linkType, int linkCount) {
		long t1 =System.currentTimeMillis();
		Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting");
		
		boolean ok = false;
		try { 
			EANUtility.linkEntityItems(ro(), prof, str, eiParent, eiChild, rel, linkType, linkCount);
			ok = true;
		} catch (Exception ex) {
			if(RMIMgr.shouldTryReconnect(ex) && // try to reconnect
					RMIMgr.getRmiMgr().reconnectMain()){
				try {
					EANUtility.linkEntityItems(ro(), prof, str, eiParent, eiChild, rel, linkType, linkCount);
					ok = true;
				}catch (Exception x) {
					com.ibm.eacm.ui.UI.showException(null,x, "mw.err-title");
				}
			}else{
				com.ibm.eacm.ui.UI.showException(null,ex, "mw.err-title");
			}
		}

		Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"link3 ended "+Utils.getDuration(t1));
		return ok;
	}
	/**
	 * get whereusedlist
	 * used by WUsedActionTab.refresh, EANWuAction and MatrixAction
	 * @param wai
	 * @param prof
	 * @param c
	 * @return
	 */
	public static WhereUsedList getWUList(WhereUsedActionItem wai,  Profile prof, Component c) {
		WhereUsedList wl = null;
		try {
			wl = wai.rexec(ro(), prof);
		} catch (Exception exc) {
			if(RMIMgr.shouldTryReconnect(exc)){	// try to reconnect
				if (RMIMgr.getRmiMgr().reconnectMain()) {
					try{
						wl = wai.rexec(ro(), prof);
					}catch(Exception e){
						if(RMIMgr.shouldPromptUser(e)){// show user msg and ask what to do
							if (com.ibm.eacm.ui.UI.showMWExcPrompt(c, e) == RETRY) {
								wl = getWUList(wai, prof, c);
							}
						}else{ 
							com.ibm.eacm.ui.UI.showException(c,e, "mw.err-title");
						}
					}
				}else{	// reconnect failed
					com.ibm.eacm.ui.UI.showException(c,exc, "mw.err-title");
				}
			}else{ // show user msg and ask what to do
				if(RMIMgr.shouldPromptUser(exc)){
					if (com.ibm.eacm.ui.UI.showMWExcPrompt(c, exc) == RETRY) {
						wl = getWUList(wai, prof, c);
					}// else user decide to ignore or exit	
				}else{
					com.ibm.eacm.ui.UI.showException(c,exc, "mw.err-title");
				}
			} 
		}

		return wl;
	}

	/**
	 * get entitylist for entry point for profile
	 * @param prof
	 * @return
	 */
	public static EntityList getNavigateEntry(Profile prof) {
		EntityList el = null;
		long t1 = System.currentTimeMillis();
		Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting "+prof.getReadLanguage());
		
		try {
			el = ro().getEntityList(prof);
		} catch (Exception exc) {
			if(RMIMgr.shouldTryReconnect(exc)){	// try to reconnect
				if (RMIMgr.getRmiMgr().reconnectMain()) {
					try {
						el = ro().getEntityList(prof);
					} catch (Exception e) {
						if(RMIMgr.shouldPromptUser(e)){// show user msg and ask what to do
							if (com.ibm.eacm.ui.UI.showMWExcPrompt(null, e) == RETRY) {
								el = getNavigateEntry(prof);
							}
						}else{ 
							com.ibm.eacm.ui.UI.showException(null,e, "mw.err-title");
						}
					}
				} else{	// reconnect failed
					com.ibm.eacm.ui.UI.showException(null,exc, "mw.err-title");
				}
			}else{ // show user msg and ask what to do
				if(RMIMgr.shouldPromptUser(exc)){
					if (com.ibm.eacm.ui.UI.showMWExcPrompt(null, exc) == RETRY) {
						el = getNavigateEntry(prof);
					}// else user decide to ignore or exit	
				}else{
					com.ibm.eacm.ui.UI.showException(null,exc, "mw.err-title");
				}
			}
		}
		Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"getnaventry ended "+Utils.getDuration(t1));
		RMIMgr.logger.log(Level.FINER,"list:");
		// loggers need this put into separate lines
		String listArray[] = Routines.splitString(Utils.outputList(el), NEWLINE);
		for(int i=0;i<listArray.length; i++){
			RMIMgr.logger.log(Level.FINER, listArray[i]);
		}
		return el;
	}
	
    /**
     * @param ai
     * @param ei
     * @param prof
     * @param comp
     * @return
     */
    public static EntityList navigate(NavActionItem ai, EntityItem[] ei, Profile prof,Component comp) {
        EntityList el = null;
        long t1=Long.MIN_VALUE;
        try {
        	t1 = System.currentTimeMillis();
        	Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting");
            el = EntityList.getEntityList(ro(), prof, ai, ei);
        } catch(DomainException de) { 
        	com.ibm.eacm.ui.UI.showException(comp,de);
            de.dereference();
		} catch (Exception exc) {
			if(RMIMgr.shouldTryReconnect(exc)){	// try to reconnect
				if (RMIMgr.getRmiMgr().reconnectMain()) {
					try {
						el = EntityList.getEntityList(ro(), prof, ai, ei);
					} catch (Exception e) {
						if(RMIMgr.shouldPromptUser(e)){// show user msg and ask what to do
							if (com.ibm.eacm.ui.UI.showMWExcPrompt(comp, e) == RETRY) {
								el = navigate(ai,ei,prof,comp);
							}
						}else{ 
							com.ibm.eacm.ui.UI.showException(comp,e, "mw.err-title");
						}
					}
				} else{	// reconnect failed
					com.ibm.eacm.ui.UI.showException(comp,exc, "mw.err-title");
				}
			}else{ // show user msg and ask what to do
				if(RMIMgr.shouldPromptUser(exc)){
					if (com.ibm.eacm.ui.UI.showMWExcPrompt(comp, exc) == RETRY) {
						el = navigate(ai,ei,prof,comp);
					}// else user decide to ignore or exit	
				}else{
					com.ibm.eacm.ui.UI.showException(comp,exc, "mw.err-title");
				}
			}
		}
		Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"nav ended "+Utils.getDuration(t1));
        return el;
    }

    /**
     * @param cai
     * @param ei
     * @param prof
     * @param comp
     * @return
     */
    public static EntityList create(CreateActionItem cai, EntityItem[] ei,Profile prof, Component comp) {
        EntityList el = null;
        try {
            el = EntityList.getEntityList(ro(),prof, cai, ei);
        }catch (Exception exc) {
        	if(RMIMgr.shouldTryReconnect(exc)){	// try to reconnect
        		if (RMIMgr.getRmiMgr().reconnectMain()) {
        			try {
        			    el = EntityList.getEntityList(ro(),prof, cai, ei);
        			} catch (Exception e) {
        				if(RMIMgr.shouldPromptUser(e)){// show user msg and ask what to do
        					if (com.ibm.eacm.ui.UI.showMWExcPrompt(comp, e) == RETRY) {
        					    el = create(cai,ei,prof,comp);
        					}
        				}else{ 
        					com.ibm.eacm.ui.UI.showException(comp,e, "mw.err-title");
        				}
        			}
        		} else{	// reconnect failed
        			com.ibm.eacm.ui.UI.showException(comp,exc, "mw.err-title");
        		}
        	}else{ // show user msg and ask what to do
        		if(RMIMgr.shouldPromptUser(exc)){
        			if (com.ibm.eacm.ui.UI.showMWExcPrompt(comp, exc) == RETRY) {
        				el = create(cai,ei,prof,comp);
        			}// else user decide to ignore or exit	
        		}else{
        			com.ibm.eacm.ui.UI.showException(comp,exc, "mw.err-title");
        		}
        	}
        }
        return el;
    }
	/**
	 * Execute search action
	 * @param sai
	 * @param c
	 * @return
	 */
	public static EntityList doSearch(SearchActionItem sai,Profile prof, Component c) {
		EntityList el = null;

		long t1 = System.currentTimeMillis();
		Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting");
		try {
			el = sai.rexec(ro(), prof);
		} catch (Exception exc) {
			if (exc instanceof SearchExceedsLimitException){ 
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"dosearch ended "+Utils.getDuration(t1));
				com.ibm.eacm.ui.UI.showException(c,exc);
				return el;
			}

			if(RMIMgr.shouldTryReconnect(exc)){	// try to reconnect
				if (RMIMgr.getRmiMgr().reconnectMain()) {
					try{
						el = sai.rexec(ro(), prof);
					}catch(Exception e){
						if(RMIMgr.shouldPromptUser(e)){// show user msg and ask what to do
							if (com.ibm.eacm.ui.UI.showMWExcPrompt(c, e) == RETRY) {
								el = doSearch(sai,prof, c);
							}
						}else{ 
							com.ibm.eacm.ui.UI.showException(c,e, "mw.err-title");
						}
					}
				}else{	// reconnect failed
					com.ibm.eacm.ui.UI.showException(c,exc, "mw.err-title");
				}
			}else{ // show user msg and ask what to do
				if(RMIMgr.shouldPromptUser(exc)){
					if (com.ibm.eacm.ui.UI.showMWExcPrompt(c, exc) == RETRY) {
						el = doSearch(sai,prof,c);
					}// else user decide to ignore or exit	
				}else{
					com.ibm.eacm.ui.UI.showException(c,exc, "mw.err-title");
				}
			} 
		}
		Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"dosearch ended "+Utils.getDuration(t1));
		return el;
	}

	/**
	 * get the xml - RCQ285768
	 * @param entitytype
	 * @param entityid
	 * @param comp
	 * @return
	 */
	public static String getCachedXML(String entitytype, int entityid, Component comp) {
		String out = null;
		try {
			out = ro().getCachedXML(entitytype, entityid); 
		} catch (Exception exc) {
			if(RMIMgr.shouldTryReconnect(exc)){	// try to reconnect
				if (RMIMgr.getRmiMgr().reconnectMain()) {
					try {
						out = ro().getCachedXML(entitytype, entityid);  
					} catch (Exception e) {
						com.ibm.eacm.ui.UI.showException(comp,e, "mw.err-title");
					}
				} else{	// reconnect failed
					com.ibm.eacm.ui.UI.showException(comp,exc, "mw.err-title");
				}
			}else{
				com.ibm.eacm.ui.UI.showException(comp,exc, "mw.err-title");
			}
		}
		return out;
	}
	/**
	 * install this zip file, it contains updated sw
	 * 
	 * DBUtils.updateSoftwareImage cmd "C:\EACMV6\jre\bin\java.exe" -Djava.compiler=NONE 
	 * -classpath .;"C:\EACMV6\Updates\eaServer.jar" com.ibm.eannounce.eserver.Updater 
	 * C%3A%5CEACMV6%5C201201140718.optional C%3A%5CEACMV6%5C EACM.exe

	 * @param fileName
	 * @return
	 */
	public static boolean updateSoftwareImage(String fileName) {
		RMIMgr.logger.log(Level.INFO,"loading update");
		String[] parms = getUpdateParms(fileName);
		if (parms != null) {
			String cmd = MessageFormat.format(EACMProperties.getProperty("update.launch.command"),(Object[])parms);
			RMIMgr.logger.log(Level.INFO,"cmd: "+cmd);  
			try {
				Runtime.getRuntime().exec(cmd);
			} catch (Exception e) {
				RMIMgr.logger.log(Level.SEVERE,"unable to load update",e);
				return false;
			}
		}else{
			return false;
		}

		return true;
	}
	/**
	 * get parameters needed to fill in this cmd:
	 * "{0}\bin\java.exe" -Djava.compiler=NONE -classpath .;"{1}\eaServer.jar" 
	 * 				 com.ibm.eannounce.eserver.Updater {2} {3} {4}
	 * @param file
	 * @return
	 */
	private static String[] getUpdateParms(String file) {
		try {
			String[] parms = { System.getProperty("java.home"), //location of jre
					UPDATE_FOLDER, //location of ea-server.jar
					URLEncoder.encode(file, "utf8"), //location of updated files (in zip)
					URLEncoder.encode(HOME_DIRECTORY, "utf8"), //directory location of Home
					URLEncoder.encode(System.getProperty("lax.application.name"), "utf8") //restart the application located here
			};
			return parms;
		} catch (java.io.UnsupportedEncodingException uee) {
			uee.printStackTrace();
		}
		return null;
	}
}
