//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.


package com.ibm.eacm.nav;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ibm.eacm.mw.DBUtils;
import com.ibm.eacm.objects.EACMGlobals;
import com.ibm.eacm.objects.Utils;

import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.Profile;
//import java.util.*;


/**
 * This class instantiates NavSerialObject and writes them to disk for a particular profile
 * It may read NavSerialObjects for a different sessionid for the same profile type if a 
 * navigate was pinned.
 * There is one per Navigation.  It hangs onto the current NavSerialObject to improve performance.
 * 
 * @author Wendy Stimpson
 */
//$Log: SerialObjectController.java,v $
//Revision 1.4  2014/01/14 15:36:55  wendy
//add more info to trace msgs
//
//Revision 1.3  2013/09/25 11:08:15  wendy
//do not display read errors
//
//Revision 1.2  2013/05/01 18:35:13  wendy
//perf updates for large amt of data
//
//Revision 1.1  2012/09/27 19:39:15  wendy
//Initial code
//
public class SerialObjectController implements EACMGlobals {
	private static int fileKey = 0;
	private static final String NAVLIST_EXT = ".navlist";

	//private Profile prof = null;
	private NavSerialObject currentNso = null;

	/**
	 * serialObjectController
	 *  
	 */
	public SerialObjectController() {}

	/**
	 * generateHist - called when loading entitylists from navigate or search
	 * this will always be the currentNso
	 * @param eList
	 * @param iSide
	 * @return
	 */
	public NavSerialObject generateHist(EntityList eList, int iSide) {
		long t1 = System.currentTimeMillis();
		Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting "+eList.getParentActionItem().getActionItemKey());

		String fileName = getFileName(iSide,eList.getProfile());
		NavSerialObject nso = new NavSerialObject(fileName, eList);		
		nso.setController(this);

		write(nso,true,false); 

		if (currentNso != null){
			// race with write currentNso.dereference();
			writeAndDeref(currentNso);
		}			
		// make this the current one
		currentNso = nso;
		Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"generatehist ended "+Utils.getDuration(t1));
		return nso;
	}

	/**
	 * navigate to the next page saving current cursor location
	 * currentNso will be set by generateHist()
	 * @param prof
	 * @param action
	 * @param ei
	 * @param key
	 * @return
	 */
	public EntityList navigate(Profile prof, NavActionItem action, EntityItem[] ei, String key){
		long t1 =System.currentTimeMillis();
		Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting "+action.getActionItemKey());

		if (currentNso!=null){
			currentNso.setCursorKeys(ei,key);  // set the history in the current action
			// do this in generatehist to prevent race write(currentNso);
		}

		EntityList list = DBUtils.navigate(action, ei, prof, null);
		Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"navigate ended "+Utils.getDuration(t1));
		return list;
	}
	/**
	 * Used in copy action
	 * @return
	 */
	protected String getSelectionKey() {
		String key = null;
		if (currentNso!=null){
			key = currentNso.getKey();
		}
		return key;
	}

	/**
	 * Used in renavigate from history list, probably not needed now but leave it
	 * @param filename
	 * @return
	 */
	public boolean isRefresh(String filename){
		boolean iscurrent = false;
		if (currentNso!=null){
			iscurrent = currentNso.getFileName().equals(filename);
		}
		return iscurrent;
	}

	public NavSerialObject getCurrentNso() {
		return currentNso;
	}

	/**
	 * @param nso
	 */
	public void setCurrentNso(NavSerialObject nso){
		if (currentNso != null){
			currentNso.dereference();	
		}
		nso.setController(this);
		currentNso = nso;
	}
	/**
	 * generate - called when loading bookmarks
	 * a new navigate will be created using current profile, but loaded bookmarks may use
	 * a different profile
	 * this is called after the bookmarked page is loaded
	 * @param item
	 * @return
	 *  
	 */
	protected NavSerialObject generate(NavActionItem item) {  	
		String fileName = getFileName(-1,item.getProfile());
		NavSerialObject tmpNso = new NavSerialObject(fileName, item, item.getEntityItems());
		tmpNso.setController(this);

		write(tmpNso, true, false);

		return tmpNso;
	}    

	/**
	 * write - does things like save history
	 * @param nso
	 *  
	 */
	protected void write(NavSerialObject nso) {
		write(nso,false,false);
	}
	private void writeAndDeref(NavSerialObject nso) {
		write(nso,false,true);
	}
	
//	 must synch writes and reads for very large files - sometimes the write is not complete when a read is attempted
	private Vector<String> writingFilesVct = new Vector<String>(); 
	
	private synchronized void startWrite(String fn){
		writingFilesVct.add(fn);
	}

    private synchronized void writeDone(String fn){
    	writingFilesVct.remove(fn);
    	notifyAll();
    }

    private synchronized void waitForWriteDone(String fn) {
        //This only loops once for each special event, which may not
        //be the event we're waiting for.
        while(writingFilesVct.contains(fn)) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
    }
    

	private void write(final NavSerialObject nso, final boolean markfile, final boolean derefNso) {
		waitForWriteDone(nso.getFileName()+NAVLIST_EXT); // make sure this is free
	
		startWrite(nso.getFileName()+NAVLIST_EXT); // keep reference to this file until write is done
		// put in own thread to improve performance
		Runnable writelater = new Runnable() {
			public void run() {
				EntityList navlist = nso.getNavList();
				String nsofn = nso.getFileName();
				
				Utils.write(nsofn, nso);
				
				if (navlist!=null){ //perftest
					Utils.write(nsofn+NAVLIST_EXT, navlist);
				}
	
				// all will be deleted on exit, so might as well mark it now
				if (markfile){
					Utils.deleteOnExit(nsofn); 
					if (nso.getNavList()!=null){
						Utils.deleteOnExit(nsofn+NAVLIST_EXT);
					}
				}
			
				if (derefNso){
					nso.dereference(); // must do this after write is complete
				}
				
				writeDone(nsofn+NAVLIST_EXT);		
			}
		};

		Thread t = new Thread(writelater);
		t.start();
	}
	//perftest
	protected EntityList readNavList(String fileName) {
		Object o = null;
		EntityList list = null;
		if (fileName != null){ 	
			waitForWriteDone(fileName+NAVLIST_EXT); // make sure this file is not getting written at the same time
			try{
				o = Utils.readWithExceptions(fileName+NAVLIST_EXT); // this file may not be one for this profile.session
			}catch(Exception exc){
        		Logger.getLogger(APP_PKG_NAME).log(Level.SEVERE,"Unable to read navlist ",exc);
        		//com.ibm.eacm.ui.UI.showException(null,exc); just log it, dont put up dialog, the list will be pulled again
			}
			if (o instanceof EntityList) {
				list = (EntityList)o;
			}
		}
		
		return list;
	}

	/***************
	 * allow navhist to delete based on filename - dont delete the files because some are shared
	 * @param nsoFileName
	 */
	protected void delete(String nsoFileName) {
		if (currentNso != null && nsoFileName.equals(currentNso.getFileName())){
			currentNso.dereference();
			currentNso = null;
		}
	}

	/**
	 * getFileName
	 * @param side
	 * @return
	 */
	private String getFileName(int side,Profile prof) {
		return TEMP_DIRECTORY + "hist" + getFilePrefix(side,prof) + "#" + createFileKey() + ".oof";
	}

	/**
	 * getFilePrefix
	 * @param side
	 * @return
	 *  
	 */
	private String getFilePrefix(int side,Profile prof) {		
		String prefix = "_" + prof.getEnterprise() + "_" + prof.getRoleCode() + "_" + prof.getOPWGID() + "_" + prof.getSessionID();
		return prefix + "_" + side;
	}

	private static int createFileKey() {
		int curkey = fileKey;
		fileKey++;
		return curkey;
	}

	/**
	 * getEntityItems used for the navigation based on the navserialobj with the specified filename 
	 * @param fileName
	 */
	public EntityItem[] getEntityItems(String fileName) {
		EntityItem[] eiArray = null;
		if (currentNso!= null && currentNso.getFileName().equals(fileName)){
			eiArray = currentNso.getEntityItems();
		}else{
			Object o = Utils.read(fileName); // this file may not be one for this profile
			if (o instanceof NavSerialObject) {
				// once a history is used, it is no longer valid, cleanup
				NavSerialObject nso = (NavSerialObject)o;
				eiArray = nso.getEntityItems();
				nso.dereference();
				nso=null;
			}
		}
		return eiArray;
	}

	/**********************
	 * Get the NavSerialObject for the specified filename
	 * Called by Navigate.renavigate from history when user goes back
	 * It may end up in a new Navigate if navigate was pinned.
	 * @param fileName
	 * @return
	 */
	public NavSerialObject readNavSerialObject(String fileName) {
		Object o = null;
		NavSerialObject nso = null;
		if (fileName != null){ 			
			o = Utils.read(fileName); // this file may not be one for this profile.session
			if (o instanceof NavSerialObject) {
				nso = (NavSerialObject)o;
				nso.setController(this); // this class is not serializable, so restore it
			}
		}
		return nso;
	}
	/**
	 * dereference 
	 *  
	 */
	protected void dereference() {		
		if (currentNso != null){
			currentNso.dereference();
			currentNso = null;
		}

		System.gc();
	}

}

