//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.actions;


import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import COM.ibm.opicmpdh.middleware.Middleware;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.RemoteDatabase;
import COM.ibm.opicmpdh.objects.Blob;


import com.ibm.eacm.*;
import com.ibm.eacm.mw.DBUtils;
import com.ibm.eacm.mw.RMIMgr;
import com.ibm.eacm.objects.AlwaysLevel;
import com.ibm.eacm.objects.DateRoutines;
import com.ibm.eacm.objects.EACMProperties;
import com.ibm.eacm.objects.Routines;
import com.ibm.eacm.objects.SerialPref;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.ui.SWProgressDialog;
import com.ibm.eannounce.version.Version;


/*********************************************************************
 * This is used to check for software updates and also install them
 * @author Wendy Stimpson
 */
//$Log: SWUpdateMgr.java,v $
//Revision 1.3  2013/07/31 20:00:18  wendy
//can not use a separate thread when invoked from autoupdate
//
//Revision 1.2  2013/07/18 18:36:10  wendy
//fix compiler warnings
//
//Revision 1.1  2012/09/27 19:39:16  wendy
//Initial code
//
public class SWUpdateMgr extends EACMAction
{
	private static final long serialVersionUID = 1L;

	private boolean bCheckForUpdate = true; // only do this once when user selects a profile
	private SwingWorker<?, ?> updateWorker = null;
	
	public SWUpdateMgr() {
		super(CHKUP_ACTION);
		setEnabled(false);
	}

	/* (non-Javadoc)
	 * called by 'check for updates' menu action
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		EACM.getEACM().disableAllActionsAndWait();
		updateWorker = new UpdateWorker(EACM.getEACM().getCurrentTab().getProfile());// get prof from current tab
		updateWorker.execute();
	}
   
    /**
     * getSoftwareImage
     * @param prof
     * @return
     */
    private Blob getSoftwareImage(Profile prof) {
        Blob out = null;
        try {
            out = ro().getSoftwareImage(prof, "UP", "UPDATE", 1, "IMAGE_UPDATE");
        } catch (Exception exc) {
			if(RMIMgr.shouldTryReconnect(exc)){	// try to reconnect
				if (RMIMgr.getRmiMgr().reconnectMain()) {
					try {
			            out = ro().getSoftwareImage(prof, "UP", "UPDATE", 1, "IMAGE_UPDATE");
			        } catch (Exception e) {
						if(RMIMgr.shouldPromptUser(e)){// show user msg and ask what to do
							if (com.ibm.eacm.ui.UI.showMWExcPrompt(null, e) == RETRY) {
						        out = getSoftwareImage(prof);
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
				        out = getSoftwareImage(prof);
					}// else user decide to ignore or exit	
				}else{
					com.ibm.eacm.ui.UI.showException(null,exc, "mw.err-title");
				}
			}
		}
        return out;
    }

    /**
     * getSoftwareImageVersion
     *
     * @param prof
     * @param _curVersion
     * @return
     */
    private String getSoftwareImageVersion(Profile prof, String _curVersion) {
        String sout = null;
        try {
            Blob out = ro().getSoftwareImageVersion(prof, "UP", "UPDATE", 1, 
            		"IMAGE_UPDATE", _curVersion);
            if (out != null) {
    			sout = out.getBlobExtension();
    		}
        } catch (Exception exc) {
			if(RMIMgr.shouldTryReconnect(exc)){	// try to reconnect
				if (RMIMgr.getRmiMgr().reconnectMain()) {
					try {
			            Blob out = ro().getSoftwareImageVersion(prof, "UP", "UPDATE", 1, 
			            		"IMAGE_UPDATE", _curVersion);
			            if (out != null) {
			    			sout = out.getBlobExtension();			        
			    		} 
			        }catch (Exception e) {
						if(RMIMgr.shouldPromptUser(e)){// show user msg and ask what to do
							if (com.ibm.eacm.ui.UI.showMWExcPrompt(null, e) == RETRY) {
							    sout = getSoftwareImageVersion(prof,_curVersion);
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
					    sout = getSoftwareImageVersion(prof,_curVersion);
					}// else user decide to ignore or exit	
				}else{
					com.ibm.eacm.ui.UI.showException(null,exc, "mw.err-title");
				}
			}
		}
        return sout;
    }
	/**
	 * based on the extension and the version determine if this is an update
	 * 
	 * @param strBExtension
	 * @param strCurVersion
	 * @return
	 */
	private boolean isUpdateAvailable(String strBExtension, String strCurVersion) {
		Logger.getLogger(APP_PKG_NAME).log(Level.CONFIG,"    APPLICATION_VERSION_CLIENT  : " + strCurVersion);
		Logger.getLogger(APP_PKG_NAME).log(Level.CONFIG,"    APPLICATION_VERSION_DATABASE: " + strBExtension);
		//<date>.mandatory
        if (strBExtension != null) {
        	// only look at dates, remove optional
        	//2012-01-14-07.27.54.602000      APPLICATION_VERSION_CLIENT  : 201012311205
        	//2012-01-14-07.27.54.602000      APPLICATION_VERSION_DATABASE: 201012311205.optional
        	int dotindex = strBExtension.indexOf('.');
        	if(dotindex!=-1){
        		strBExtension = strBExtension.substring(0,dotindex);
        	}
            if (strBExtension.compareTo(strCurVersion) > 0) {
                return true;
            }
        }
       
        return false;
	}

	/**
	 * based on extension determine if this is mandatory
	 * 
	 * @param sExt
	 * @return
	 */
	private boolean isMandatory(String sExt) {
		return !sExt.toLowerCase().endsWith(OPTIONAL);
	}

    /**
     * called after user selects a profile.  it checks to see if an update is available and if so
     * install it if it is not optional.
	 * @param p
	 */
	public void autoCheckForUpdate(Profile p) {
		setEnabled(isUpdateable()); // it is not updateable if there is a saved update file not yet loaded

		if (Utils.isArmed(AUTO_UPDATE_FILE) && bCheckForUpdate && isUpdateable()) {
			bCheckForUpdate = false;
			//disable all
			EACM.getEACM().disableAllActionsAndWait();

			updateWorker = new AutoUpdateWorker(p);
			updateWorker.execute();
		}
	}

    /**
     * getJarVersion
     * @return "yyyyMMddHHmm"
     */
    public static String getJarVersion() {
        return getJarVersion(UPDATED_DATE);
    }

    /**
     * getJarVersion date in pattern format
     *
     * @param pattern
     * @return
     */
    public static String getJarVersion(String pattern) {
    	Date bldDate = DateRoutines.parseDate(BUILD_DATE, Version.getDate());
    	return DateRoutines.formatDate(pattern, bldDate);
    }

	/**
	 * called by 'check for updates' menu action
	 * checks for existing update and confirms download
	 * after download, confirms install, if not installed now, it will be installed next time the UI is 
	 * started and user logs in
	 */
	private class UpdateWorker extends SwingWorker<Boolean, Void> { 
		private long t11 = 0L;
		private Profile profile;
		UpdateWorker(Profile p){
			profile = p;
		}
		@Override
		public Boolean doInBackground() {
			Boolean bool = new Boolean(false);
			try{		
				boolean isok = false;		
				t11 = System.currentTimeMillis();
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting");
				String strVer = getJarVersion();		
				String strExt = getSoftwareImageVersion(profile,strVer);	
				if (isUpdateAvailable(strExt,strVer)) {
                	//msg11026.4 = EACM has detected software updates, would you like to download them?
            		int reply =  com.ibm.eacm.ui.UI.showConfirmYesNo(null, Utils.getResource("msg11026.4"));
                    if (reply == YES_BUTTON){
        				SWProgressDialog prog = new SWProgressDialog(EACM.getEACM(), "Process Status");
        				prog.setVisible(true);
        				Blob updateBlob = getSoftwareImage(profile);
        				if(updateBlob !=null){
        					String fileName = HOME_DIRECTORY + updateBlob.getBlobExtension();
        					Logger.getLogger(APP_PKG_NAME).log(Level.FINE,"saving file "+fileName);
        					updateBlob.saveToFile(fileName);

        					// msg11026.3 = An Update has been downloaded, would you like to install it now or later? Installing 
        					// now will exit EACM.
        					reply = showConfirmNowLater(Utils.getResource( "msg11026.3"));

        					if (reply == NOW_BUTTON) {
        				 		//installsw=Installing Update File...
        		        		prog.setText(Utils.getResource("installsw"));
        						isok = DBUtils.updateSoftwareImage(fileName);
        					}else {
        						SerialPref.putPref(UPDATE_TO_INSTALL, fileName); // install update later
        						//ELogin.getEACM().setActionEnabled(CHKUP_ACTION, isUpdateable());
        						SWUpdateMgr.this.setEnabled(isUpdateable());
        					}
        				}

        				prog.setVisible(false); 
        				prog.dispose();
                    } else {
                		// loggers need this put into separate lines
        				String listArray[] = Routines.splitString(getMiddlewareVersions(profile), NEWLINE);
        				for(int i=0;i<listArray.length; i++){
        					Logger.getLogger(APP_PKG_NAME).log(AlwaysLevel.ALWAYS, listArray[i]);
        				}
					}
                }else{
                    //up2date = EACM is up to date.
                    com.ibm.eacm.ui.UI.showFYI(null, Utils.getResource("up2date"));
                    //only do this when there is no update.
               		// loggers need this put into separate lines
    				String listArray[] = Routines.splitString(getMiddlewareVersions(profile), NEWLINE);
    				for(int i=0;i<listArray.length; i++){
    					Logger.getLogger(APP_PKG_NAME).log(AlwaysLevel.ALWAYS, listArray[i]);
    				}
                }
				bool = new Boolean(isok);
				if(isok){
					//do it on the bg thread
					Routines.pause(5000);
				}
			}catch(Exception ex){ // prevent hang
				Logger.getLogger(APP_PKG_NAME).log(Level.SEVERE,"Exception",ex);
			}finally{
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"bg ended "+Utils.getDuration(t11));
				updateWorker = null;
				profile = null;
			}

			return bool;
		}

		@Override
		public void done() {
			//this will be on the dispatch thread
			try {
				if(!isCancelled()){
					Boolean ok = get();
					if (ok.booleanValue()) {
						EACM.getEACM().exit();
					}else{
						EACM.getEACM().enableAllActionsAndRestore();
					}
				}else{
					EACM.getEACM().enableAllActionsAndRestore();
				}
			} catch (InterruptedException ignore) {}
			catch (java.util.concurrent.ExecutionException e) {
				String why = null;
				Throwable cause = e.getCause();
				if (cause != null) {
					why = cause.getMessage();
				} else {
					why = e.getMessage();
				}
				Logger.getLogger(APP_PKG_NAME).log(Level.SEVERE,"Error getting blob: " + why);
			}finally{
				//enable all if no update was done
			}
		}
	}

	/**
	 * called after user selects a profile.  it checks to see if an update is available and if so
     * install it if it is not optional.
	 */
	private class AutoUpdateWorker extends SwingWorker<Boolean, Void> { 
		private long t11 = 0L;
		private Profile profile;
		AutoUpdateWorker(Profile p){
			profile = p;
		}
		@Override
		public Boolean doInBackground() {
			Boolean bool = new Boolean(false);
			try{		
				boolean isok = false;		
				t11 = System.currentTimeMillis();
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting");
				String strVer = getJarVersion();									
    			String strExt = getSoftwareImageVersion(profile, strVer);			
    			if (isUpdateAvailable(strExt,strVer)) {		
    				boolean getupdate = false;
    				if (EACMProperties.isUpdateMandatory() || isMandatory(strExt)) {
    					getupdate = true;
    				} else {
    					// msg11026.5= EACM has detected software updates, would you like to download them? Choosing 
    					// 'Yes' will cause EACM to close.
    					int reply =  com.ibm.eacm.ui.UI.showConfirmYesNo(null, Utils.getResource("msg11026.5"));
    					if (reply == YES_BUTTON){
    						getupdate = true;
    					} 
    				}
    				if(getupdate){
        				SWProgressDialog prog = new SWProgressDialog(EACM.getEACM(), "Process Status");
        				prog.setVisible(true);
        				Blob updateBlob = getSoftwareImage(profile);
        				if(updateBlob !=null){
        					String fileName = HOME_DIRECTORY + updateBlob.getBlobExtension();
        					Logger.getLogger(APP_PKG_NAME).log(Level.FINER,"saving file "+fileName);
        					updateBlob.saveToFile(fileName);
        					isok = DBUtils.updateSoftwareImage(fileName);
        				}

        				prog.setVisible(false); 
        				prog.dispose();
    				}else{
    			   		// loggers need this put into separate lines
        				String listArray[] = Routines.splitString(getMiddlewareVersions(profile), NEWLINE);
        				for(int i=0;i<listArray.length; i++){
        					Logger.getLogger(APP_PKG_NAME).log(AlwaysLevel.ALWAYS, listArray[i]);
        				}
    				}
    			}else{
    		   		// loggers need this put into separate lines
    				String listArray[] = Routines.splitString(getMiddlewareVersions(profile), NEWLINE);
    				for(int i=0;i<listArray.length; i++){
    					Logger.getLogger(APP_PKG_NAME).log(AlwaysLevel.ALWAYS, listArray[i]);
    				}
    			}
				
				bool = new Boolean(isok);
				if(isok){
					//do it on the bg thread
					Routines.pause(5000);
				}
			}catch(Exception ex){ // prevent hang
				Logger.getLogger(APP_PKG_NAME).log(Level.SEVERE,"Exception ",ex);
			}finally{
    			Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"bg ended "+Utils.getDuration(t11));

				updateWorker = null;
				profile = null;
			}

			return bool;
		}

		@Override
		public void done() {
			//this will be on the dispatch thread
			try {
				if(!isCancelled()){
					Boolean ok = get();
					if (ok.booleanValue()) {
						EACM.getEACM().exitImmediately();
					}else{
						EACM.getEACM().enableAllActionsAndRestore();
					}
				}else{
					EACM.getEACM().enableAllActionsAndRestore();
				}
			} catch (InterruptedException ignore) {}
			catch (java.util.concurrent.ExecutionException e) {
				String why = null;
				Throwable cause = e.getCause();
				if (cause != null) {
					why = cause.getMessage();
				} else {
					why = e.getMessage();
				}
				Logger.getLogger(APP_PKG_NAME).log(Level.SEVERE,"Error getting blob: " + why);
			}finally{
				//enable all if no update was done
			}
		}
	}
 
    private static int showConfirmNowLater(String userMsg) {
    	String[] options = {"now","later"};
    	String accdesc[] = { "now-acc","later-acc"};
    	int r = com.ibm.eacm.ui.UI.showConfirm(null,userMsg, options, accdesc,JOptionPane.YES_NO_OPTION);
     	options = null;
    	accdesc = null;

    	return r;
    }

    /**
     * does a file with updates exist that has not been loaded yet
     * 
     * @return
     */
    private boolean isUpdateable() {
        return SerialPref.getPref(UPDATE_TO_INSTALL, (String)null)==null;
    }
    
	/**
	 * getMiddlewareVersions
	 *
	 * @return String
	 */
	private String getMiddlewareVersions(Profile profile) {
		Date tmp = DateRoutines.parseDate(BUILD_DATE, Version.BUILD_DATE.substring(4));
		String[] in = new String[5];
		Object[] parms = new String[10];
		String[] vers = null;
		boolean bError = false;

		in[0] = Routines.trim(Middleware.getMiddlewareVersion(),22); // local version
		in[1] = Routines.trim(RMIMgr.getRmiMgr().getRemoteVersion(),22);
		in[2] = Routines.trim(RemoteDatabase.c_strBuildTimeStamp,22);
		in[3] = Routines.trim(getJarVersion(ISO_DATE),22); //"yyyy-MM-dd-HH.mm.ss.SSSSSS"
		in[4] = Routines.trim(DateRoutines.formatDate(ISO_DATE, tmp),22); //"yyyy-MM-dd-HH.mm.ss.SSSSSS"

		parms[0] = in[0];
		parms[2] = in[1];
		parms[4] = in[2];
		parms[6] = in[3];
		parms[8] = in[4];

		parms[1] = "n/a";
		parms[3] = "n/a";
		parms[5] = "n/a";
		parms[7] = "n/a";
		parms[9] = "n/a";

		vers = getMiddlewareVersions(profile, in,END_OF_TIME);

		if (vers != null) {
			for (int i=0;i<in.length && !bError; ++ i) {
				if (!vers[i].equals(END_OF_TIME)) {
					if (!vers[i].equals(in[i])) {
						bError = true;
					}
				}
			}
			parms[1] = vers[0];
			parms[3] = vers[1];
			parms[5] = vers[2];
			parms[7] = vers[3];
			parms[9] = vers[4];
		}

		if (bError) {
			String strOut = Utils.getResource("mw.error.version",parms);
			if (!Utils.isTestMode()) 	{
				//showMessage(getResource("appName"),WARN_MESSAGE,OK,strOut,ELogin.getEACMFrame());
				com.ibm.eacm.ui.UI.showTTFMessage(null,"warning-title", 
							JOptionPane.WARNING_MESSAGE, "warning-acc", strOut);

			}
			return strOut;
		}

		return Utils.getResource("mw.version",parms);
	}
	/**
     * get target version
     *
     */
    private String[] getMiddlewareVersions(Profile prof, String[] _s, String _eod) {
		String[] out = null;
		try {
			out = ro().getTargetVersions(prof,_s,_eod);
		}  catch (Exception exc) {
				if(RMIMgr.shouldTryReconnect(exc)){	// try to reconnect
					if (RMIMgr.getRmiMgr().reconnectMain()) {
						try {
							out = ro().getTargetVersions(prof,_s,_eod);
				        } catch (Exception e) {
							if(RMIMgr.shouldPromptUser(e)){// show user msg and ask what to do
								if (com.ibm.eacm.ui.UI.showMWExcPrompt(null, e) == RETRY) {
									return getMiddlewareVersions(prof,_s,_eod);
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
							return getMiddlewareVersions(prof,_s,_eod);
						}// else user decide to ignore or exit	
					}else{
						com.ibm.eacm.ui.UI.showException(null,exc, "mw.err-title");
					}
				}
			}
		return out;
	}
}

