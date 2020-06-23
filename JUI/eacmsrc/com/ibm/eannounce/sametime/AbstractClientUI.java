/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/12/05
 * @author Anthony C. Liberto
 *
 */
package com.ibm.eannounce.sametime;
import javax.swing.JFrame;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public abstract class AbstractClientUI extends JFrame {
    /**
     * Constructor
     * @param _title
     * @author Anthony C. Liberto
     */
    public AbstractClientUI(String _title) {
        super(_title);
        return;
    }
    /**
     * start
     * @param _host
     * @param _user
     * @param _pass
     * @param _place
     * @author Anthony C. Liberto
     */
    public void start(String _host, String _user, String _pass, String _place) {
    }
    /**
     * stop
     * @author Anthony C. Liberto
     */
    public void stop() {
    }
    /**
     * loggedIn
     * @return
     * @author Anthony C. Liberto
     */
    public boolean loggedIn() {
        return false;
    }
}
