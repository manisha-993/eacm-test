/**
 *
 * Copyright (c) 2003-2004 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 * e-announce chat functionality
 *
 * @version 1.3  2004/02/18
 * @author Anthony C. Liberto
 *
 * $Log: RemoteControl.java,v $
 * Revision 1.1  2007/04/18 19:46:48  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:55  tony
 * This is the initial load of OPICM
 *
 * Revision 1.6  2005/09/08 17:59:12  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.5  2005/01/28 17:54:21  tony
 * JTest Fromat step 2
 *
 * Revision 1.4  2005/01/26 17:18:50  tony
 * JTest Formatting modifications
 *
 * Revision 1.3  2004/10/27 20:15:36  tony
 * improved updater by adding the ability to automatically
 * restart the e-announce application that was running.
 *
 * Revision 1.2  2004/02/20 20:15:34  tony
 * e-announce1.3
 *
 * Revision 1.1  2004/02/19 21:36:58  tony
 * e-announce1.3
 *
 *
 */
package com.ibm.eannounce.eserver;
import java.io.IOException;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class RemoteControl {
    private static final String WIN_PATH = "rundll32 ";
//    private static final String WIN_FLAG = "url.dll,FileProtocolHandler";
//    private static final String UNIX_PATH = "netscape";
//    private static final String UNIX_FLAG = "-remote openURL";

    /**
     * remoteControl
     * @author Anthony C. Liberto
     */
    public RemoteControl() {
        return;
    }

    /**
     * command
     * @param _command
     * @return
     * @author Anthony C. Liberto
     */
    public boolean command(String _command) {
        return command(_command, false);
    }

    /**
     * command
     * @param _command
     * @param _dllCall
     * @return
     * @author Anthony C. Liberto
     */
    public boolean command(String _command, boolean _dllCall) {
        Process p = null;
        try {
            if (_dllCall) {
                System.out.println("command(" + WIN_PATH + _command + ")");
                p = Runtime.getRuntime().exec(WIN_PATH + _command);
            } else {
                System.out.println("command(" + _command + ")");
                p = Runtime.getRuntime().exec(_command);
            }
        } catch (IOException _ioe) {
            _ioe.printStackTrace();
            return false;
        }
        return true;
    }
}
