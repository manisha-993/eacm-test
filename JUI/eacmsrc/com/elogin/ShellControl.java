/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
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
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: ShellControl.java,v $
 * Revision 1.1  2007/04/18 19:42:18  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:37:41  tony
 * This is the initial load of OPICM
 *
 * Revision 1.6  2005/09/08 17:58:57  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.5  2005/02/02 17:30:21  tony
 * JTest Second Pass
 *
 * Revision 1.4  2005/01/27 23:18:16  tony
 * JTest Formatting
 *
 * Revision 1.3  2004/04/08 19:53:23  tony
 * enhanced notes on runtime control
 *
 * Revision 1.2  2004/04/07 21:11:08  tony
 * enhanced shellControl functionality.
 *
 * Revision 1.1.1.1  2004/02/10 16:59:26  tony
 * This is the initial load of OPICM
 *
 * Revision 1.5  2003/10/29 00:22:23  tony
 * removed System.out. statements.
 *
 * Revision 1.4  2003/07/15 18:52:18  tony
 * improved reporting logic to use vbscript to eliminate browser
 * elements that were deemed unnecessary.
 *
 * Revision 1.3  2003/07/14 17:40:06  tony
 * added comments
 *
 * Revision 1.2  2003/04/11 20:02:27  tony
 * added copyright statements.
 *
 */
package com.elogin;
import java.io.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class ShellControl {
    private static final String WIN_PATH = "rundll32";
    private static final String WIN_FLAG = "url.dll,FileProtocolHandler";
    private static final String UNIX_PATH = "netscape";
    private static final String UNIX_FLAG = "-remote openURL";

    /**
     * shellControl
     * @author Anthony C. Liberto
     */
    public ShellControl() {
        return;
    }

    /**
     * eaccess
     * @return
     * @author Anthony C. Liberto
     */
    public static EAccess eaccess() {
        return EAccess.eaccess();
    }

    /**
     * autolaunch a program based on the extension of the specified file
     *
     * @param _command
     */
    public void launch(String _command) {
        String strCommand = null;
        try {
            if (eaccess().isWindows()) {
                strCommand = WIN_PATH + " " + WIN_FLAG + " " + _command;
                Runtime.getRuntime().exec(strCommand);
            } else {
                appendLog("autoLaunch not supported on " + System.getProperty("os.name") + " platform.");
            }
        } catch (IOException _ioe) {
            _ioe.printStackTrace();
        }
        return;
    }

    /**
     * command
     * @param _command
     * @author Anthony C. Liberto
     */
    public void command(String _command) {
        try {
            Runtime.getRuntime().exec(_command);
        } catch (IOException _ioe) {
            _ioe.printStackTrace();
        }
        return;
    }

    /**
     * launch a browser to the specified URL
     *
     * @param _url
     */
    public void launchURL(String _url) {
        String strCommand = null;
        Process p = null;
        if (!Routines.have(_url)) {
            return;
        }
        try {
            if (eaccess().isStandardClient()) { //acl_20030715
                String strScriptFile = eaccess().generateScript(_url); //acl_20030715
                strCommand = WIN_PATH + " " + WIN_FLAG + " " + strScriptFile; //acl_20030715
                p = Runtime.getRuntime().exec(strCommand); //acl_20030715
            } else if (eaccess().isWindows()) {
                strCommand = WIN_PATH + " " + WIN_FLAG + " " + _url;
                p = Runtime.getRuntime().exec(strCommand);
            } else {
                strCommand = UNIX_PATH + " " + UNIX_FLAG + "(" + _url + ")";
                p = Runtime.getRuntime().exec(strCommand);

                try {
                    int exitCode = p.waitFor();

                    if (exitCode != 0) {
                        strCommand = UNIX_PATH + " " + _url;
                        p = Runtime.getRuntime().exec(strCommand);
                    }
                } catch (InterruptedException _ie) {
                    _ie.printStackTrace();
                }
            }
        } catch (IOException _ioe) {
            _ioe.printStackTrace();
        }
        return;
    }

    private void appendLog(String _message) {
        EAccess.appendLog(_message);
        return;
    }
    /*
     shell out a command to DOS
       arp -a
       ipconfig.exe /all
       mem
       msinfo32 /report c:\test.txt
       netstat
       nbstat -1 127.0.0.1
       vol
     */
    /**
     * shellCommand
     * @param _s
     * @param _dump
     * @return
     * @author Anthony C. Liberto
     */
    public BufferedReader shellCommand(String _s, boolean _dump) {
        BufferedReader br = null;
        if (_s != null) {
            if (_dump) {
                System.out.println("command: " + _s + "...");
            }
            try {
                Process process = Runtime.getRuntime().exec(_s);
                br = new BufferedReader(new InputStreamReader(process.getInputStream()));
                if (_dump) {
                    dump(br);
                }
            } catch (IOException _ioe) {
                _ioe.printStackTrace();
            }
        }
        return br;
    }

    /**
     * dump
     * @param _br
     * @author Anthony C. Liberto
     */
    public void dump(BufferedReader _br) {
        String sLine = null;
        try {
            while ((sLine = _br.readLine()) != null) {
                System.out.println("    " + sLine);
            }
        } catch (IOException _ioe) {
            _ioe.printStackTrace();
        }
        return;
    }
}
