//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
/*
rundll32 url.dll,FileProtocolHandler http:\\www.ibm.com
RunDLL32.EXE shell32.dll,ShellExec_RunDLL iexplore -k www.ibm.com
<vb.script>
set objExplorer=CreateObject("InternetExplorer.Application")
objExplorer.navigate "http://www.ibm.com"
objExplorer.ToolBar=0
objExplorer.StatusBar=0
objExplorer.width=800
objExplorer.height=600
objExplorer.Left=0
objExplorer.Top=0
objExplorer.Visible=1
</vbsScript>
 *
 */
package com.ibm.eacm.objects;
import java.awt.Desktop;
import java.io.*;
import java.net.URI;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ibm.eacm.ui.UI;


/**
 *
 */
// $Log: ShellControl.java,v $
// Revision 1.1  2012/09/27 19:39:12  wendy
// Initial code
//
public class ShellControl implements EACMGlobals {
    private static final String WIN_PATH = "rundll32";
    private static final String WIN_FLAG = "url.dll,FileProtocolHandler";
    private static final String UNIX_PATH = "netscape";
    private static final String UNIX_FLAG = "-remote openURL";

    /**
     * shellControl
     */
    private ShellControl() {}

    /**
     * getOS
     *
     * @return
     */
    private static int getOS() {
        String os = System.getProperty("os.name");
        if (os != null) {
            if (os.equals(WINDOWS_2000)) {
                return OS_WINDOWS_2000;
            } else if (os.equals(WINDOWS_XP)) {
                return OS_WINDOWS_XP;
            } else if (os.startsWith(WINDOWS)) {
                return OS_WINDOWS;
            }
        }
        return OS_OTHER;
    }

    /**
     * isStandardClient
     *
     * @return
     */
    public static boolean isStandardClient() {
        int ios = getOS();
        return ios >= OS_MINIMUM_CLIENT;
    }

    /**
     * isWindows
     * @return
     */
    public static boolean isWindows() {
        return getOS() >= OS_WINDOWS;
    }
    /**
     * autolaunch a program based on the extension of the specified file
     *
     * @param command
     */
    public static void launch(String command) {
        String strCommand = null;
        try {
            if (isWindows()) {
                strCommand = WIN_PATH + " " + WIN_FLAG + " " + command;
                Runtime.getRuntime().exec(strCommand);
            } else {
    			Logger.getLogger(APP_PKG_NAME).log(Level.SEVERE,"autoLaunch not supported on " + System.getProperty("os.name") + " platform.");
            }
        } catch (IOException ioe) {
			Logger.getLogger(APP_PKG_NAME).log(Level.SEVERE,"Exception ",ioe);
        }
    }

    /**
     * launch a browser to the specified URL
     *
     * @param url
     */
    public static void launchURL(String url) {
        String strCommand = null;
        Process p = null;
        if (!Routines.have(url)) {
            return;
        }
        // this will launch the system default browser
      	if (Desktop.isDesktopSupported()) {   
    	    Desktop dt = Desktop.getDesktop();   
    	    if (dt.isSupported(Desktop.Action.BROWSE)) {   
    	        try {
					dt.browse(new URI(url));
					return;
				} catch (Exception e) {
					Logger.getLogger(APP_PKG_NAME).log(Level.SEVERE,"Unable to launch browser ",e);
					UI.showException(null, e);
				}   
    	    }
    	}
        try {
            if (isStandardClient()) { 
                String strScriptFile = generateScript(url); 
                strCommand = WIN_PATH + " " + WIN_FLAG + " " + strScriptFile; 
                p = Runtime.getRuntime().exec(strCommand); 
            } else if (isWindows()) {
                strCommand = WIN_PATH + " " + WIN_FLAG + " " + url;
                p = Runtime.getRuntime().exec(strCommand);
            } else {
                strCommand = UNIX_PATH + " " + UNIX_FLAG + "(" + url + ")";
                p = Runtime.getRuntime().exec(strCommand);

                try {
                    int exitCode = p.waitFor();

                    if (exitCode != 0) {
                        strCommand = UNIX_PATH + " " + url;
                        p = Runtime.getRuntime().exec(strCommand);
                    }
                } catch (InterruptedException ie) {
        			Logger.getLogger(APP_PKG_NAME).log(Level.SEVERE,"Exception ",ie);
                }
            }
        } catch (IOException ioe) {
			Logger.getLogger(APP_PKG_NAME).log(Level.SEVERE,"Exception ",ioe);
        }
    }
    private static String generateScript(String url) {
        String strFileName = null;
        String strMsg = null;
  
        strFileName = TEMP_DIRECTORY + Routines.getRandomString() + VB_SCRIPT_EXTENSION; 
        strMsg =  MessageFormat.format(EACMProperties.getProperty("vb.script"),url);
		Logger.getLogger(APP_PKG_NAME).log(Level.CONFIG,"script fn:" + strFileName + " msg:" + strMsg);
        Utils.writeString(strFileName, strMsg,EACM_FILE_ENCODE);
        Utils.deleteOnExit(strFileName);
        return strFileName;
    }
}
