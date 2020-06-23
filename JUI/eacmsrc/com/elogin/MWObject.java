/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: MWObject.java,v $
 * Revision 1.1  2007/04/18 19:42:17  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:37:40  tony
 * This is the initial load of OPICM
 *
 * Revision 1.6  2005/09/08 17:58:50  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.5  2005/02/02 17:30:22  tony
 * JTest Second Pass
 *
 * Revision 1.4  2005/01/27 23:18:19  tony
 * JTest Formatting
 *
 * Revision 1.3  2004/08/25 16:24:39  tony
 * updated location chooser logic to enhance functionality.
 *
 * Revision 1.2  2004/02/19 21:34:52  tony
 * e-announce1.3
 *
 * Revision 1.1.1.1  2004/02/10 16:59:26  tony
 * This is the initial load of OPICM
 *
 * Revision 1.9  2004/01/20 20:50:40  tony
 * updated logic
 *
 * Revision 1.8  2004/01/20 19:25:51  tony
 * added password logic to the log file.
 *
 * Revision 1.7  2003/12/01 17:46:08  tony
 * accessibility
 *
 * Revision 1.6  2003/09/18 17:30:22  tony
 * 52308
 *
 * Revision 1.5  2003/06/30 18:07:21  tony
 * improved functionality for testing by adding functionality
 * to MWObject that includes the default of the userName and
 * overwrite.properties file to use.  This will give the test
 * team more flexibility in defining properties for the application.
 *
 * Revision 1.4  2003/05/06 22:10:36  tony
 * 50515
 *
 * Revision 1.3  2003/05/05 18:04:38  tony
 * 50515
 *
 * Revision 1.2  2003/04/11 20:02:24  tony
 * added copyright statements.
 *
 */
package com.elogin;
import java.io.Serializable;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class MWObject implements Serializable {
	static final long serialVersionUID = 19721222L;
	private String sDesc = null;
	private String sIP = null;
	private int iPort = 0;
	private String sName = null;
	private String sReport = "";																//50515
	private String sUser = "";																	//acl_20030630
	private String sPass = "";																	//acl_20040120
	private String sPropertyFile = "";															//acl_20030630
	private boolean mwSelOnFail = true;															//52308
	private String chatIP = null;
	private int iChatPort = 0;

	/**
     * MWObject
     * @param _desc
     * @param _name
     * @param _ip
     * @param _port
     * @param _report
     * @param _user
     * @param _pass
     * @param _prop
     * @param _showSel
     * @param _chatIP
     * @param _chatPort
     * @author Anthony C. Liberto
     */
    public MWObject(String _desc, String _name, String _ip, int _port, String _report, String _user, String _pass, String _prop, boolean _showSel, String _chatIP, int _chatPort) {		//acl_20040120
		setDescription(_desc);
		setIP(_ip);
		setPort(_port);
		setName(_name);
		setReportPrefix(_report);																//50515
		setUserName(_user);																		//acl_20030630
		setPassword(_pass);																		//acl_20040120
		setPropertyFile(_prop);																	//acl_20030630
		setMWSelectOnFailure(_showSel);															//52308
		if (_chatIP != null) {
			setChatIP(_chatIP);
		} else {
			setChatIP(_ip);
		}
		setChatPort(_chatPort);
		return;
	}

	/**
     * setDescription
     * @param _s
     * @author Anthony C. Liberto
     */
    public void setDescription(String _s) {
		sDesc = new String(_s);
	}

	/**
     * getDescription
     * @return
     * @author Anthony C. Liberto
     */
    public String getDescription() {
		return sDesc;
	}

	/**
     * setName
     * @param _s
     * @author Anthony C. Liberto
     */
    public void setName(String _s) {
		sName = new String(_s);
	}

	/**
     * getName
     * @return
     * @author Anthony C. Liberto
     */
    public String getName() {
		return sName;
	}

	/**
     * setIP
     * @param _s
     * @author Anthony C. Liberto
     */
    public void setIP(String _s) {
		sIP = new String(_s);
		return;
	}

	/**
     * getIP
     * @return
     * @author Anthony C. Liberto
     */
    public String getIP() {
		return sIP;
	}

	/**
     * setPort
     * @param _i
     * @author Anthony C. Liberto
     */
    public void setPort(int _i) {
		iPort = _i;
		return;
	}

	/**
     * getPort
     * @return
     * @author Anthony C. Liberto
     */
    public int getPort() {
		return iPort;
	}

	/**
     * @see java.lang.Object#toString()
     * @author Anthony C. Liberto
     */
    public String toString() {
		return sDesc;
	}

	/**
     * key
     * @return
     * @author Anthony C. Liberto
     */
    public String key() {
		return sDesc + ":" + sName + ":" + sIP + ":" + iPort + ":" + sReport;
	}
/*
 50515
*/
	/**
     * setReportPrefix
     * @param _s
     * @author Anthony C. Liberto
     */
    public void setReportPrefix(String _s) {
		if (_s != null) {															//50515
			sReport = new String(_s);
		}																			//50515
		return;
	}

	/**
     * getReportPrefix
     * @return
     * @author Anthony C. Liberto
     */
    public String getReportPrefix() {
		return sReport;
	}

/*
 acl_20030630
 */
    /**
     * setUserName
     * @param _s
     * @author Anthony C. Liberto
     */
    public void setUserName(String _s) {
		if (_s != null) {
			sUser = new String(_s);
		}
		return;
	}

	/**
     * getUserName
     * @return
     * @author Anthony C. Liberto
     */
    public String getUserName() {
		return sUser;
	}

	/**
     * setPassword
     * @param _s
     * @author Anthony C. Liberto
     */
    public void setPassword(String _s) {											//acl_20040120
		if (_s != null) {															//acl_20040120
			sPass = new String(_s);													//acl_20040120
		}																			//acl_20040120
		return;																		//acl_20040120
	}

	/**
     * getPassword
     * @return
     * @author Anthony C. Liberto
     */
    public String getPassword() {													//acl_20040120
		return sPass;																//acl_20040120
	}																				//acl_20040120

	/**
     * setPropertyFile
     * @param _s
     * @author Anthony C. Liberto
     */
    public void setPropertyFile(String _s) {
		if (_s != null) {
			sPropertyFile = new String(_s);
		}
		return;
	}

	/**
     * getPropertyFile
     * @return
     * @author Anthony C. Liberto
     */
    public String getPropertyFile() {
		return sPropertyFile;
	}

/*
 52308
 */
    /**
     * showMWSelectOnFailure
     * @return
     * @author Anthony C. Liberto
     */
    public boolean showMWSelectOnFailure() {
		return mwSelOnFail;
	}

	/**
     * setMWSelectOnFailure
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setMWSelectOnFailure(boolean _b) {
		mwSelOnFail = _b;
		return;
	}

	/**
     * setChatIP
     * @param _s
     * @author Anthony C. Liberto
     */
    public void setChatIP(String _s) {
		chatIP = new String(_s);
		return;
	}

	/**
     * getChatIP
     * @return
     * @author Anthony C. Liberto
     */
    public String getChatIP() {
		return chatIP;
	}

	/**
     * setChatPort
     * @param _i
     * @author Anthony C. Liberto
     */
    public void setChatPort(int _i) {
		iChatPort = _i;
		return;
	}

	/**
     * getChatPort
     * @return
     * @author Anthony C. Liberto
     */
    public int getChatPort() {
		return iChatPort;
	}

/*
 accessible
 */
	/**
     * eaccess
     * @return
     * @author Anthony C. Liberto
     */
    public static EAccess eaccess() {
		return EAccess.eaccess();
	}

	/**
     * getAccessibleString
     * @return
     * @author Anthony C. Liberto
     */
    public String getAccessibleString() {
		String[] parm = new String[10];
		parm[0] = sDesc;
		parm[1] = sIP;
		parm[2] = Routines.toString(iPort);
		parm[3] = (chatIP == null) ? "undefined" : chatIP;
		parm[4] = Routines.toString(iChatPort);
		parm[5] = sName;
		parm[6] = sReport;
		parm[7] = sUser;
		parm[8] = sPropertyFile;
		parm[9] = Routines.toString(mwSelOnFail);
		return eaccess().getMessage("mwobject.accessible",parm);
	}

/*
 loc_chooser

 */
	/**
     * isValid
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isValid() {
		return Routines.have(sIP);
	}
}
