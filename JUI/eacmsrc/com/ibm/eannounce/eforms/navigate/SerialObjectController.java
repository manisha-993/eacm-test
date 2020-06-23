// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005,2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package com.ibm.eannounce.eforms.navigate;
import com.elogin.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.Profile;
//import java.util.*;


/**
 * This class instantiates NavSerialObject and writes them to disk for a particular profile
 * It may read NavSerialObjects for a different sessionid for the same profile type if a 
 * navigate was pinned.
 * There is one per Navigation.  It hangs onto the current NavSerialObject to improve performance.
 * 
 * $Log: SerialObjectController.java,v $
 * Revision 1.5  2009/08/25 12:27:06  wendy
 * Avoid race condition in write and deref
 *
 * Revision 1.4  2009/05/28 14:18:09  wendy
 * Performance cleanup
 *
 * Revision 1.3  2009/04/16 17:54:42  wendy
 * Cleanup history
 *
 * Revision 1.2  2008/01/30 16:26:54  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:44:49  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:02  tony
 * This is the initial load of OPICM
 *
 */
public class SerialObjectController implements EAccessConstants {
	private Profile prof = null;
	private NavSerialObject currentNso = null;
	private static int fileKey = 0;
	private static final String NAVLIST_EXT = ".navlist";

	/**
     * serialObjectController
     * @param _prof
     *  
     */
    protected SerialObjectController(Profile _prof) {
		prof = _prof;
		if (prof == null) {
			prof = EAccess.eaccess().getActiveProfile();
		}		
	}

	/**
     * generateHist - called when loading entitylists from navigate or search
     * this will always be the currentNso
     * @param _eList
     * @param _iSide
     * @return
     */
    protected NavSerialObject generateHist(EntityList _eList, int _iSide) {
    	Long t1 = EAccess.eaccess().timestamp("        SerialobjController.generateHist started");
    	if (!_eList.getProfile().equals(prof)){
    		if (!prof.getEnterprise().equals(_eList.getProfile().getEnterprise()) ||
        			prof.getOPWGID()!=_eList.getProfile().getOPWGID()){
        			prof = _eList.getProfile();
        		}
    	}
  		String fileName = getFileName(_iSide);
		NavSerialObject nso = new NavSerialObject(fileName, _eList);		
		nso.setController(this);
    	
		write(nso,true,false); 
		
		if (currentNso != null){
			// race with write currentNso.dereference();
			writeAndDeref(currentNso);
		}			
		// make this the current one
		currentNso = nso;
		EAccess.eaccess().timestamp("        SerialobjController.generateHist ended",t1);
		return nso;
	}
    
	/**
     * navigate to the next page saving current cursor location
     * currentNso will be set by generateHist()
     * @param _action
     * @param _ei
     * @param _key
     * @return
     * 
     */
    protected EntityList navigate(NavActionItem _action, EntityItem[] _ei, String _key){
    	//Long t1 = EAccess.eaccess().timestamp("        SerialobjController navigate started");
    	
    	if (currentNso!=null){
    		currentNso.setCursorKeys(_ei,_key);  // set the history in the current action
    		// do this in generatehist to prevent race write(currentNso);
    	//	EAccess.eaccess().timestamp("        SerialobjController navigate after setkeys",t1);
    	}
    	
		EntityList list = EAccess.eaccess().dBase().getEntityList(_action, _ei,null);
	//EAccess.eaccess().timestamp("        SerialobjController navigate ended",t1);
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
    protected boolean isRefresh(String filename){
    	boolean iscurrent = false;
    	if (currentNso!=null){
    		iscurrent = currentNso.getFileName().equals(filename);
    	}
    	return iscurrent;
    }
    
    protected NavSerialObject getCurrentNso() {
    	return currentNso;
    }
    
    /**
     * @param nso
     */
    protected void setCurrentNso(NavSerialObject nso, boolean isPin){
    	if (currentNso != null){
    		if (isPin){
    			// list is shared so dont dereference the list
    			currentNso.clearList();
    		}
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
     * @param _item
     * @return
     *  
     */
    protected NavSerialObject generate(NavActionItem _item) {
    	if (!_item.getProfile().equals(prof)){
    		if (!prof.getEnterprise().equals(_item.getProfile().getEnterprise()) ||
    			prof.getOPWGID()!=_item.getProfile().getOPWGID()){
    			prof = _item.getProfile();
    		}
    	}    	
    	String fileName = getFileName(-1);
    	NavSerialObject tmpNso = new NavSerialObject(fileName, _item, _item.getEntityItems());
    	tmpNso.setController(this);

    	write(tmpNso, true, false);
		
    	return tmpNso;
	}    
	/*private String getProfileKey(Profile _prof) {
		if (_prof != null) {
			return _prof.getEnterprise() + ":" + _prof.getOPWGID()+":"+_prof.getSessionID();
		}
		return null;
	}*/
	/**
     * write - does things like save history
     * @param _nso
     *  
     */
	protected void write(NavSerialObject _nso) {
		write(_nso,false,false);
	}
	private void writeAndDeref(NavSerialObject _nso) {
		write(_nso,false,true);
	}
	private void write(final NavSerialObject _nso, final boolean markfile, final boolean derefNso) {
		// put in own thread to improve performance
        Runnable writelater = new Runnable() {
            public void run() {
               //Long t1 = EAccess.eaccess().timestamp("        SerialobjController.write own thrd started");
				EAccess.eaccess().gio.write(_nso.getFileName(), _nso);
				if (_nso.getNavList()!=null){ //perftest
					EAccess.eaccess().gio.write(_nso.getFileName()+NAVLIST_EXT, _nso.getNavList());
				}
				// all will be deleted on exit, so might as well mark it now
				if (markfile){
					EAccess.eaccess().gio.deleteOnExit(_nso.getFileName()); 
					if (_nso.getNavList()!=null){
						EAccess.eaccess().gio.deleteOnExit(_nso.getFileName()+NAVLIST_EXT);
					}
				}
				if (derefNso){
					_nso.dereference(); // must do this after write is complete
				}
				//EAccess.eaccess().timestamp("        SerialobjController.write ownthrd ended",t1);
            }
        };

        Thread t = new Thread(writelater);
        t.start();
	}
	//perftest
	protected EntityList readNavList(String _fileName) {
		//	Long t1=EAccess.eaccess().timestamp("        SerialobjController.readNavList started");
			Object o = null;
			EntityList list = null;
			if (_fileName != null){ // gio.read() does this check-> || (!EAccess.eaccess().gio().exists(_fileName))) {			
				o = EAccess.eaccess().gio().read(_fileName+NAVLIST_EXT); // this file may not be one for this profile.session
				if (o instanceof EntityList) {
					list = (EntityList)o;
				}
	        }
		//	EAccess.eaccess().timestamp("        SerialobjController.readNavList ended",t1);
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
     * @param _side
     * @return
     */
    private String getFileName(int _side) {
		return TEMP_DIRECTORY + "hist" + getFilePrefix(_side) + "#" + createFileKey() + ".oof";
	}

	/**
     * getFilePrefix
     * @param _side
     * @return
     *  
     */
    private String getFilePrefix(int _side) {		
    	String prefix = "_" + prof.getEnterprise() + "_" + prof.getRoleCode() + "_" + prof.getOPWGID() + "_" + prof.getSessionID();
		return prefix + "_" + _side;
	}

	private static int createFileKey() {
		int curkey = fileKey;
		fileKey++;
		return curkey;
	}

	/**
     * getEntityItems used for the navigation based on the navserialobj with the specified filename 
     * @param _fileName
     */
	protected EntityItem[] getEntityItems(String _fileName) {
		EntityItem[] eiArray = null;
		if (currentNso!= null && currentNso.getFileName().equals(_fileName)){
			eiArray = currentNso.getEntityItems();
		}else{
			Object o = EAccess.eaccess().gio().read(_fileName); // this file may not be one for this profile
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

	/**
	 * called to refresh 
	 * @return
	 */
	protected EntityList replay(){
		Long t1=EAccess.eaccess().timestamp("        SerialobjController.replay started");
		EntityList list = null;

		if (currentNso != null){
			list = currentNso.replay();
		}
		EAccess.eaccess().timestamp("        SerialobjController.replay ended",t1);
		return list;
	}

	/**********************
	 * Get the NavSerialObject for the specified filename
	 * Called by Navigate.renavigate from history when user goes back
	 * It may end up in a new Navigate if navigate was pinned.
	 * @param _fileName
	 * @return
	 */
	protected NavSerialObject readNavSerialObject(String _fileName) {
	//	Long t1=EAccess.eaccess().timestamp("        SerialobjController.readNavSerialObject started");
		Object o = null;
		NavSerialObject nso = null;
		if (_fileName != null){ // gio.read() does this check-> || (!EAccess.eaccess().gio().exists(_fileName))) {			
			o = EAccess.eaccess().gio().read(_fileName); // this file may not be one for this profile.session
			if (o instanceof NavSerialObject) {
				nso = (NavSerialObject)o;
				nso.setController(this); // this class is not serializable, so restore it
			}
        }
	//	EAccess.eaccess().timestamp("        SerialobjController.readNavSerialObject ended",t1);
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
		prof = null;
		System.gc();
	}

}

