/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: ThinBase.java,v $
 * Revision 1.6  2011/09/09 17:25:29  wendy
 * use securelogin now
 *
 * Revision 1.5  2009/05/28 13:54:44  wendy
 * Performance cleanup
 *
 * Revision 1.4  2008/01/30 16:27:00  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.3  2007/05/31 18:45:20  wendy
 * Allow exit if user selects ABORT on login failure
 *
 * Revision 1.2  2007/05/31 18:16:57  wendy
 * prevent NPE
 *
 * Revision 1.1  2007/04/18 19:42:18  wendy
 * Reorganized JUI module
 *
 * Revision 1.3  2006/11/09 15:51:06  tony
 * more monitor logic
 *
 * Revision 1.2  2006/05/15 17:44:13  tony
 * nb 20060515
 * updated logic to display middleware connection
 * information to eliminate confustion relating to the
 * server that is being connected to.
 *
 * Revision 1.1.1.1  2005/09/09 20:37:41  tony
 * This is the initial load of OPICM
 *
 * Revision 1.31  2005/09/08 17:58:58  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.30  2005/05/25 18:15:42  tony
 * silverBulletReload
 *
 * Revision 1.29  2005/05/24 21:49:17  tony
 * wooden stake
 *
 * Revision 1.28  2005/05/24 21:27:57  tony
 * silverBullet
 *
 * Revision 1.27  2005/05/09 18:23:35  tony
 * improved version logging.
 *
 * Revision 1.26  2005/03/15 17:03:31  tony
 * updated versioning logic
 *
 * Revision 1.25  2005/03/14 23:40:40  tony
 * added Middleware.getMiddlewareVersion call
 *
 * Revision 1.24  2005/03/11 23:30:39  tony
 * adjusted middleware versioning logic
 *
 * Revision 1.23  2005/03/11 17:45:19  tony
 * adjusted mw comparizon logic to no longer require DB2
 *
 * Revision 1.22  2005/03/10 18:59:26  tony
 * added middleware versioning dump.
 * This will assist in troubleshooting incompatibility issues.
 *
 * Revision 1.21  2005/02/04 16:57:41  tony
 * JTest Formatter Third Pass
 *
 * Revision 1.20  2005/02/02 17:30:22  tony
 * JTest Second Pass
 *
 * Revision 1.19  2005/01/27 23:18:17  tony
 * JTest Formatting
 *
 * Revision 1.18  2005/01/14 21:25:30  tony
 * added error reporting for bad token update.
 *
 * Revision 1.17  2005/01/14 19:49:03  tony
 * improved upgrade logic.
 *
 * Revision 1.16  2004/10/13 19:31:35  tony
 * added capability for autoDetectUpdate preference and
 * corresponding logic to support the function.
 *
 * Revision 1.15  2004/10/13 17:56:18  tony
 * added on-line update functionality.
 *
 * Revision 1.14  2004/08/23 21:38:09  tony
 * TIR USRO-R-RLON-645P76
 *
 * Revision 1.13  2004/08/04 17:49:14  tony
 * adjusted logic to break function parameterization into
 * arm files and a new function directory.  This way
 * we will eliminate the possibility of translation to
 * accidentally change functionality.
 *
 * Revision 1.12  2004/06/10 20:53:17  tony
 * improved location chooser functionality.
 *
 * Revision 1.11  2004/06/09 16:01:47  tony
 * updated logic for loaction chooser.
 *
 * Revision 1.10  2004/06/09 15:48:52  tony
 * location chooser added to application.  It is controlled by
 * a boolean parameter (LOCATION_CHOOSER_ENABLED)
 * in eAccessConstants.
 *
 * Revision 1.9  2004/05/18 14:40:36  tony
 * improved malformed database connection error.
 *
 * Revision 1.8  2004/03/12 18:57:45  tony
 * added inavlidTokenUpdate
 *
 * Revision 1.7  2004/03/03 00:01:42  tony
 * added to functionality, moved firewall to preference.
 *
 * Revision 1.6  2004/03/02 21:01:12  tony
 * updated getNow logic.  should now call only once.
 *
 * Revision 1.5  2004/02/27 18:57:11  tony
 * added reconnectDatabase functionality
 *
 * Revision 1.4  2004/02/26 21:53:16  tony
 * remoteDatabaseInterface centralization.
 *
 * Revision 1.3  2004/02/25 18:14:58  tony
 * //dwb_20040225_2time
 *
 * Revision 1.2  2004/02/24 18:18:09  tony
 * dwb_20040224
 *
 * Revision 1.1.1.1  2004/02/10 16:59:27  tony
 * This is the initial load of OPICM
 *
 * Revision 1.23  2004/01/20 19:25:51  tony
 * added password logic to the log file.
 *
 * Revision 1.22  2004/01/14 18:47:57  tony
 * acl_20040114
 *   1)  updated logic to allow for manual load of serial pref.
 *   2)  trigger a middleware selection if no default middleware is defined.
 *   3)  prevent a put of parent when non new row is selected.
 *
 * Revision 1.21  2003/12/04 22:23:10  tony
 * added test functionality for literal modification.
 *
 * Revision 1.20  2003/10/29 00:22:22  tony
 * removed System.out. statements.
 *
 * Revision 1.19  2003/09/18 17:29:55  tony
 * 52308
 *
 * Revision 1.18  2003/09/03 17:10:02  tony
 * acl_20030903 sleep update
 *
 * Revision 1.17  2003/08/21 22:19:14  tony
 * dwb_20030821
 *
 * Revision 1.16  2003/08/21 19:43:10  tony
 * 51391
 *
 * Revision 1.15  2003/07/03 00:41:40  tony
 * updated event logging so log parser can directly parse
 * the log file.
 *
 * Revision 1.14  2003/06/30 18:07:21  tony
 * improved functionality for testing by adding functionality
 * to MWObject that includes the default of the userName and
 * overwrite.properties file to use.  This will give the test
 * team more flexibility in defining properties for the application.
 *
 * Revision 1.13  2003/06/25 16:18:59  tony
 * added errorhandling to getNow
 *
 * Revision 1.12  2003/06/12 22:23:40  tony
 * 1.2h modification enhancements.
 *
 * Revision 1.11  2003/06/10 16:46:47  tony
 * 51260
 *
 * Revision 1.10  2003/05/30 21:09:17  tony
 * updated messaging logic to popup on top
 * of the currently active Component.
 *
 * Revision 1.9  2003/05/28 14:42:03  tony
 * updated date logic for testing purposes
 *
 * Revision 1.8  2003/05/23 17:08:37  tony
 * updated reporting logic for middleware connection.
 *
 * Revision 1.7  2003/05/15 16:26:33  joan
 * work on pop up dialog when editing PDG
 *
 * Revision 1.6  2003/05/05 18:04:38  tony
 * 50515
 *
 * Revision 1.5  2003/05/02 20:05:54  tony
 * 50504 -- adjusted messaging and this seems to have
 * resolved the problem in local test.
 *
 * Revision 1.4  2003/04/22 16:37:04  tony
 * created MWChooser to update handling of default
 * middlewareLocation.
 *
 * Revision 1.3  2003/04/11 20:02:27  tony
 * added copyright statements.
 *
 */
package com.elogin;
import COM.ibm.eannounce.objects.MetaEntityList;
import COM.ibm.opicmpdh.transactions.Cipher;
import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.objects.Blob;
import java.rmi.RemoteException;
import java.awt.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class ThinBase extends Object implements EAccessConstants {
    private boolean rmiReady = false;
//    private ReturnStatus returnStatus = new ReturnStatus(-1);
    private RemoteDatabaseInterface ro = null;
    //	private RemoteDatabaseInterface roTime = null;		//dwb_20040224_2time
//    private Middleware mw = null;

    private MWParser parse = null;

    //constants
    /**
     * abort
     */
    public static final int ABORT = 0;
    /**
     * retry
     */
    public static final int RETRY = 1;
    /**
     * ignore
     */
    public static final int IGNORE = 2;
    /**
     * max
     */
    public static final int MAX = 10;

    //private DateRoutines dRoutines = null;
    private MWObject curMWObject = null; //52308

    /**
     * thinBase
     * @param _parse
     * @author Anthony C. Liberto
     */
    public ThinBase(MWParser _parse) {
        parse = _parse;
//        dRoutines = eaccess().getDateRoutines();
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
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
        parse = null;
        //dRoutines = null;
        return;
    }

    /**
     * getMetaEntityList
     * @param _prof
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public MetaEntityList getMetaEntityList(Profile _prof, Component _c) {
        MetaEntityList mel = null;
        eaccess().timestamp("database.getMetaEntityList():00 at: ");
        eaccess().timestamp("database.getMetaEntityList():01 at: ");
        if (_prof != null) {
            eaccess().timestamp("database.getMetaEntityList():02 at: ");
            try {
                eaccess().timestamp("database.getMetaEntityList():03 at: ");
                mel = ro.getMetaEntityList(_prof);
                eaccess().timestamp("database.getMetaEntityList():04 at: ");
            } catch (Exception _x) {
                eaccess().timestamp("database.getMetaEntityList():05 at: ");
                if (mwError(_x, "mw_getMetaEntityList()", _c) == RETRY) {
                    eaccess().timestamp("database.getMetaEntityList():06 at: ");
                    mel = getMetaEntityList(_prof, _c);
                    eaccess().timestamp("database.getMetaEntityList():07 at: ");
                }
                eaccess().timestamp("database.getMetaEntityList():08 at: ");
            }
            eaccess().timestamp("database.getMetaEntityList():09 at: ");
        }
        eaccess().timestamp("database.getMetaEntityList():10 at: ");
        eaccess().write(mel);
        eaccess().timestamp("database.getMetaEntityList():11 at: ");
        eaccess().dump(mel);
        eaccess().timestamp("database.getMetaEntityList():12 at: ");
        return mel;
    }

    private void rmiFailure(String s, boolean force, Component _c) {
        if (Routines.have(s)) {
            showMessage(_c, s);
        }
        if (force) {
            exit("exit force rmifailure");
        }
        return;
    }

    private int mwError(Exception x, String s, Component _c) { //2.2.3_10-17
        return mwError(x, s, false, _c); //2.2.3_10-17
    } //2.2.3_10-17

    private int mwError(Exception x, String str, boolean force, Component _c) { //2.2.3_10-17
        //String ss = null;
        int r = -1;
        x.printStackTrace();
        //ss = 
        	Routines.splitString(str, 100);
        //51260		setMessage(x.toString());
        //51260		int r = showConfirm(_c,ABORT_RETRY_IGNORE,true);
        //51260				logError(x.toString(), r);
        r = showException(x, _c, QUESTION_MESSAGE, ABORT_RETRY_IGNORE); //51260
        if (r == ABORT) {
            rmiFailure("", force, _c);
        } else if (r == IGNORE) { //22767
            return r; //22767
        } else if (x instanceof java.net.NoRouteToHostException || x instanceof java.rmi.ConnectException) {
            try {
                databaseConnect(_c);
            } catch (RemoteException _re) {
                _re.printStackTrace();
            }
        }
        if (r == RETRY) {
            waitFor(2000);
        }
        return r;
    }

    /**
     * logError
     * @param _s
     * @author Anthony C. Liberto
     */
    public void logError(String _s) {
        appendLog("Logged -->> " + _s.trim());
        return;
    }

    /**
     * log
     * @param s
     * @author Anthony C. Liberto
     */
    public void log(String s) {
        appendLog(s.trim());
        return;
    }
/*
    private void logError(String s, int i) {
        appendLog("Error --> " + s.trim() + " {User selected (" + i + ")}");
        return;
    }
*/
    /**********************
     *	RMI -- Routines  **
     *
     * @param _c
     * @throws java.rmi.RemoteException
     **********************/
    public void databaseConnect(Component _c) throws RemoteException {
        MWObject moCur = null;
        rmiReady = false;
        moCur = parse.getCurrent();
        while (moCur == null) { //acl_20040114
            moCur = eaccess().getMWObject(); //acl_20040114
        } //acl_20040114
        rmiControl(moCur, false, _c);
        if (!rmiReady) {
            throw new RemoteException();
        }
        return;
    }

    /**
     * databaseConnect
     * @param _mw
     * @param _c
     * @throws java.rmi.RemoteException
     * @author Anthony C. Liberto
     */
    public void databaseConnect(MWObject _mw, Component _c) throws RemoteException { //loc_choose
        rmiReady = false; //loc_choose
        rmiControl(_mw, false, _c); //loc_choose
        if (!rmiReady) { //loc_choose
            throw new RemoteException();
        } //loc_choose
        return; //loc_choose
    } //loc_choose

    /**
     * isRunning
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isRunning() {
        return rmiReady;
    }

    private void rmiControl(MWObject _mo, boolean _exit, Component _c) {
        int i = 0;
        if (rmiReady) {
            return;
        }
        if (_mo == null) {
            rmiFailure(getString("msg1007.1"), false, _c);
            return;
        }
        for (i = 0; i < MAX; ++i) {
            if (initiateRMI(_mo, _c)) {
                break;
            } else {
                waitFor(1000);
            }
        }
        if (i >= MAX) {
            rmiHardFailure("msg1007", _exit, _c);
            //			rmiHardFailure(getString("msg1007"), _exit, _c);
        }
        return;
    }

    private void rmiHardFailure(String s, boolean _exit, Component _c) {
        if (Routines.have(s)) {
            setCode(s);
            if (EAccess.isArmed(LOCATION_CHOOSER_ARM_FILE)) { //loc_chooser
                showMessage(_c, getString("msg13004.0")); //loc_chooser
            } else { //loc_chooser
                int r = showConfirm(_c, CHOOSE_EXIT, true);
                if (r == 0) {
                    rmiControl(eaccess().getMWObject(), _exit, _c);
                } else if (r == 1) {
                    _exit = true;
                }
            } //loc_chooser
        }
        if (_exit) {
            exit("exit rmiHardFailure");
        }
        return;
    }

    private boolean initiateRMI(MWObject _mo, Component _c) {
        if (rmiReady) {
            return rmiReady;
        }
        try {
            ro = connect(_mo);
            if (ro != null) {
                rmiReady = true;
                System.setProperty(REPORT_PREFIX, _mo.getReportPrefix()); //50515
            }
        } catch (RemoteException re) {
            re.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
            rmiFailure(getString("msg1007"), false, _c);
            //			rmiFailure(getString("msg1007"),false, _c);
        }
        return rmiReady;
    }

    /**
     * connect
     * @param _mo
     * @throws java.rmi.RemoteException
     * @return
     * @author Anthony C. Liberto
     */
    public RemoteDatabaseInterface connect(MWObject _mo) throws RemoteException {
        curMWObject = _mo; //52308
        System.setProperty(MW_VERSION, MiddlewareClientProperties.getVersion());
        if (_mo != null) {
			System.setProperty(MW_DESC,_mo.getDescription());		//nb_20060515
            System.setProperty(MW_OBJECT, _mo.getName());
            System.setProperty(MW_IP, _mo.getIP());
            System.setProperty(MW_PORT, Routines.toString(_mo.getPort()));
            System.setProperty(MW_REPORT, _mo.getReportPrefix());
            System.setProperty(MW_USER, _mo.getUserName()); //acl_20030630
            System.setProperty(MW_PASS, _mo.getPassword()); //acl_20040120
            System.setProperty(MW_PROPERTIES, _mo.getPropertyFile()); //acl_20030630
            return Middleware.connect(_mo.getIP(), _mo.getPort(), _mo.getName());
        }
        System.setProperty(MW_OBJECT, MiddlewareClientProperties.getDatabaseObjectName());
        System.setProperty(MW_IP, MiddlewareClientProperties.getObjectConnectIpAddress());
        System.setProperty(MW_PORT, MiddlewareClientProperties.getObjectConnectPort());
        System.setProperty(MW_REPORT, MiddlewareClientProperties.getVersion());
        return Middleware.connect();
    }

    /**
     * waitFor
     * @param i
     * @author Anthony C. Liberto
     */
    public void waitFor(int i) {
        Thread t = new Thread();
        Routines.sleep(t, i);
        return;
    }

    /**
     * closeRMI
     * @param _c
     * @author Anthony C. Liberto
     */
    public void closeRMI(Component _c) {
        if (!rmiReady) {
            return;
        }
        try {
            Middleware.disconnect(ro);
            rmiReady = false;
        } catch (Exception ex) {
            ex.printStackTrace();
            rmiFailure("msg1010", false, _c);
        }
        return;
    }

    /*********************
     *	Timing Methods  **
     *
     * @return String
     *********************/
    public String getNOW() {
        //		if (roTime == null) {												//dwb_20040225_2time
        //			if (!reconnectTime()) {											//dwb_20040225_2time
        //				return null;												//dwb_20040225_2time
        //			}																//dwb_20040225_2time
        //		}																	//dwb_20040225_2time

        ReturnDataResultSetGroup ld = null;
        try {
            D.ebug("client.getNow.start"); //dwb_20040224
            //dwb_20040224_2time			ld = ro.remoteGBL2028();
            ld = ro.remoteGBL2028(); //dwb_20040224_2time
        } catch (Exception _ce) { //dwb_20040225_2time
            D.ebug("getNow failed");
            _ce.printStackTrace(); //dwb_20040225_2time
            D.ebug("getNow: ConnectException"); //dwb_20040225_2time
            if (reconnectMain()) { //dwb_20040225_2time
                D.ebug("trying getNow again");
                try {
                    ld = ro.remoteGBL2028(); //dwb_20040225_2time
                } catch (Exception _e) {
                    D.ebug("second attempt failed");
                    _e.printStackTrace();
                    return null; // prevent NPE parsing date
                }
            } else { //dwb_20040225_2time
                D.ebug("getNow reconnection failed"); //dwb_20040225_2time
                return null; //dwb_20040225_2time
            } //dwb_20040225_2time													//dwb_20040225_2time
            //		} catch (Exception _ex) {
            //			_ex.printStackTrace();					//dwb_20040224
            //			if (softMWError(_ex,"MW_getNow temporarily lost connection.", null) == retry) {
            //				return getNow();
            //			}
        }
        try {
            String datNow = new String(ld.getColumn(0, 0, 0));
            System.setProperty("mw.now", datNow);
            D.ebug("client.getNow.complete");
            return datNow;//.substring(0, 19);
        } catch (Exception _ex) {
            _ex.printStackTrace();
        }
        return null;
    }
    /*
    	private boolean reconnectTime() {										//dwb_20040225_2time
    		int i = 0;															//dwb_20040225_2time
    		for (i = 0; i < max; ++i) {											//dwb_20040225_2time
    	 		if (initiateTime()) {											//dwb_20040225_2time
    		 		return true;												//dwb_20040225_2time
    			} else {														//dwb_20040225_2time
    				waitFor(1000);												//dwb_20040225_2time
    			}																//dwb_20040225_2time
    		}																	//dwb_20040225_2time
    		return false;														//dwb_20040225_2time
    	}																		//dwb_20040225_2time

    	private boolean initiateTime() {										//dwb_20040225_2time
    		if (roTime != null) {
    			mw.disconnect(roTime);
    		}
    		try {																//dwb_20040225_2time
    			roTime = connect(curMWObject);									//dwb_20040225_2time
    			if (roTime != null) {											//dwb_20040225_2time
    				return true;												//dwb_20040225_2time
    			}																//dwb_20040225_2time
    		} catch (RemoteException re) {										//dwb_20040225_2time
    			re.printStackTrace();											//dwb_20040225_2time
    		} catch (Exception ex) {											//dwb_20040225_2time
    			ex.printStackTrace();											//dwb_20040225_2time
    		}																	//dwb_20040225_2time
    		return false;														//dwb_20040225_2time
    	}																		//dwb_20040225_2time
    */
    /**
     * getProfileString
     * @param _prof
     * @return
     * @author Anthony C. Liberto
     */
    public String getProfileString(Profile _prof) { //eAnnounce1.1
        return _prof.toString() + ":" + _prof.getOPID() + ":" + _prof.getOPWGID() + ":" + _prof.getSessionID() + ":" + _prof.getTranID() + ":" + _prof.getValOn() + ":" + _prof.getEffOn(); //eAnnounce1.1
    } //eAnnounce1.1

    /**
     * getBlob
     * @param _prof
     * @param _eType
     * @param _eId
     * @param _attCode
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public Blob getBlob(Profile _prof, String _eType, int _eId, String _attCode, Component _c) {
        Blob out = null;
        appendLog("getBlob(" + getProfileString(_prof) + "," + _eType + ", " + _eId + ", " + _attCode + ")");
        try {
            out = ro.getBlob(_prof, _eType, _eId, _attCode);
        } catch (Exception x) {
            if (mwError(x, "MW_getBlob", _c) == RETRY) {
                out = getBlob(_prof, _eType, _eId, _attCode, _c);
            }
        }
        return out;
    }

    /**
     * getBlob
     * @param _prof
     * @param _eType
     * @param _eId
     * @param _attCode
     * @param _nls
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public Blob getBlob(Profile _prof, String _eType, int _eId, String _attCode, int _nls, Component _c) {
        Blob out = null;
        appendLog("getBlob(" + getProfileString(_prof) + "," + _eType + ", " + _eId + ", " + _attCode + ", " + _nls + ")");
        try {
            out = ro.getBlob(_prof, _eType, _eId, _attCode, _nls);
        } catch (Exception x) {
            if (mwError(x, "MW_getBlob", _c) == RETRY) {
                out = getBlob(_prof, _eType, _eId, _attCode, _nls, _c);
            }
        }
        return out;
    }

    /*
     * _date is last date code updated YYYYMMDDHHMMSS
     */
    /**
     * getSoftwareImage
     * @param _valChain
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public Blob getSoftwareImage(String _valChain, Component _c) {
        Blob out = null;
        String strAttCode = "IMAGE_UPDATE";
        if (_valChain != null) {
            strAttCode = _valChain;
        }
        try {
            out = ro.getSoftwareImage(getActiveProfile(), "UP", "UPDATE", 1, strAttCode);
        } catch (Exception _ex) {
            if (mwError(_ex, "mw_getUpdate", _c) == RETRY) {
                out = getSoftwareImage(_valChain, _c);
            }
        }
        return out;
    }

    /**
     * getSoftwareImage
     * @param _valChain
     * @param _valOn
     * @param _effOn
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public Blob getSoftwareImage(String _valChain, String _valOn, String _effOn, Component _c) {
        Blob out = null;
        String strAttCode = "IMAGE_UPDATE";
        if (_valChain != null) {
            strAttCode = _valChain;
        }
        try {
            out = ro.getSoftwareImage("UP", "UPDATE", 1, strAttCode, _valOn, _effOn);
        } catch (Exception _ex) {
            if (mwError(_ex, "mw_getUpdate", _c) == RETRY) {
                out = getSoftwareImage(_valChain, _valOn, _effOn, _c);
            }
        }
        return out;
    }

    /**
     * getSoftwareImageVersion
     *
     * @param _valChain
     * @param _c
     * @return
     * @author Anthony C. Liberto
     * @param _curVersion
     */
    public Blob getSoftwareImageVersion(String _valChain, String _curVersion, Component _c) {
        Blob out = null;
        String strAttCode = "IMAGE_UPDATE";
        if (_valChain != null) {
            strAttCode = _valChain;
        }
        try {
            out = ro.getSoftwareImageVersion(getActiveProfile(), "UP", "UPDATE", 1, strAttCode, _curVersion);
        } catch (Exception _ex) {
            if (mwError(_ex, "mw_getUpdate", _c) == RETRY) {
                out = getSoftwareImageVersion(_valChain, _curVersion, _c);
            }
        }
        return out;
    }

    /*
     * date is today's date YYYYMMDDHHMMSS
     */
    /**
     * putSoftwareImage
     * @param _enterprise
     * @param _blob
     * @param _c
     * @author Anthony C. Liberto
     */
    public void putSoftwareImage(String _enterprise, Blob _blob, Component _c) {
        try {
            ro.putSoftwareImage(getActiveProfile(), _enterprise, _blob);
        } catch (Exception _ex) {
            if (mwError(_ex, "mw_putUpdate", _c) == RETRY) {
                putSoftwareImage(_enterprise, _blob, _c);
            }
        }
        return;
    }

    /**
     * login
     * @param u
     * @param p
     * @param t
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public ProfileSet login(String u, String p, String t, Component _c) { //eAnnounce1.1
        ProfileSet ld = null; //eAnnounce1.1
        appendLog("login(" + u + ",**password**,**token**)");
        try {
        	byte[][] encrypted = Cipher.encryptUidPw(u,p);
        	// secure login
        	ld = ro.secureLogin(encrypted, t, "UI");
        	
            //ld = ro.login(u, p, t, "UI"); //17637
        } catch (VersionException ve) {
            EAccess.report(ve,false);
            //msg1004.0 = Client Software is Outdated, Upgrade Process is Initializing.
            showMessage(_c, getString("msg1004.0"));
            //			if (routines.is(getString("eannounce.mode.test"))) {
            //				return login(u,p,JOptionPane.showInputDialog(getString("enter.token")),_c);
            //			} else {
            eaccess().invalidTokenUpdate(); //trigger update- the versionliteral must be updated
            //			}
        } catch (LoginException le) {
            EAccess.report(le,false);
            if (EAccess.isArmed(LOCATION_CHOOSER_ARM_FILE)) { //loc_chooser
                showMessage(_c, getString("msg13003.0")); //loc_chooser
            } else {
                showMessage(_c, getString("msg1003"));
                if (curMWObject != null && curMWObject.showMWSelectOnFailure()) { //52308
                    selectMiddleware(); //52308
                } //loc_chooser
            } //52308
        } catch (Exception x) {
			int r = mwError(x, "MW_Login", _c);
            if (r == RETRY) {
                ld = login(u, p, t, _c);
            }else if (r==ABORT){
            	rmiFailure("", true, _c);
			}
        }
        return ld;
    }

    /**
     * getString
     * @param _code
     * @return
     * @author Anthony C. Liberto
     */
    public String getString(String _code) {
        return eaccess().getString(_code);
    }

    /**
     * showMessage
     * @param _c
     * @param _message
     * @author Anthony C. Liberto
     */
    public void showMessage(Component _c, String _message) {
        setTitle(getString("mw"));
        setMessage(_message);
        eaccess().showError(_c);
        return;
    }

    /**
     * setTitle
     * @param _title
     * @author Anthony C. Liberto
     */
    public void setTitle(String _title) {
        eaccess().setTitle(_title);
        return;
    }

    /**
     * setMessage
     * @param _message
     * @author Anthony C. Liberto
     */
    public void setMessage(String _message) {
        eaccess().setMessage(_message);
        return;
    }

    /**
     * setCode
     * @param _code
     * @author Anthony C. Liberto
     */
    public void setCode(String _code) {
        eaccess().setCode(_code);
        return;
    }

    /**
     * showConfirm
     * @param _c
     * @param _buttons
     * @param _clear
     * @return
     * @author Anthony C. Liberto
     */
    public int showConfirm(Component _c, int _buttons, boolean _clear) {
        setTitle("mw");
        return eaccess().showConfirm(_c, _buttons, _clear);
    }

    /**
     * appendLog
     * @param _message
     * @author Anthony C. Liberto
     */
    public void appendLog(String _message) {
        EAccess.appendLog(_message);
    }

    /**
     * exit
     * @author Anthony C. Liberto
     */
    public void exit(String _s) {
        eaccess().exit(_s);
    }

    /**
     * getActiveProfile
     * @return
     * @author Anthony C. Liberto
     */
    public Profile getActiveProfile() {
        return eaccess().getActiveProfile();
    }

    /*
     51260
     */
    /**
     * showException
     * @param _x
     * @param _c
     * @param _icon
     * @param _buttons
     * @return
     * @author Anthony C. Liberto
     */
    public int showException(Exception _x, Component _c, int _icon, int _buttons) {
        setTitle("mw");
        return eaccess().showException(_x, _c, _icon, _buttons);
    }

    /*
     dwb_20030821
     */
/*
    private int softMWError(Exception _x, String s, Component _c) { //2.2.3_10-17
        return softMWError(_x, s, false, _c); //2.2.3_10-17
    } //2.2.3_10-17
*/
 /*
    private int softMWError(Exception _x, String str, boolean force, Component _c) { //2.2.3_10-17
        String ss = routines.splitString(str, 100);
        int r = showSoftException(ss, _c, QUESTION_MESSAGE, ABORT_RETRY_IGNORE); //51260
        if (r == ABORT) {
            rmiFailure("", force, _c);
        } else if (r == IGNORE) { //22767
            return r; //22767
        } else if (_x instanceof java.net.NoRouteToHostException || _x instanceof java.rmi.ConnectException) {
            try {
                databaseConnect(_c);
            } catch (RemoteException _re) {
                _re.printStackTrace();
            }
        }
        if (r == RETRY) {
            waitFor(2000);
        }
        return r;
    }
*/
    /**
     * showSoftException
     * @param _s
     * @param _c
     * @param _icon
     * @param _buttons
     * @return
     * @author Anthony C. Liberto
     */
    public int showSoftException(String _s, Component _c, int _icon, int _buttons) {
        setTitle("mw");
        return eaccess().showException(_s, _c, _icon, _buttons);
    }

    /*
     52308
     */
    /**
     * selectMiddleware
     * @author Anthony C. Liberto
     */
    public void selectMiddleware() {
        MWObject mwObj = eaccess().getMWObject();
        Component parent = eaccess().getLogin();
        rmiReady = false;
        rmiControl(mwObj, false, parent);
        return;
    }

    /**
     * reconnectMain
     * @return
     * @author Anthony C. Liberto
     */
    public boolean reconnectMain() {
        int i = 0;
        for (i = 0; i < MAX; ++i) {
            if (initiateMain()) {
                return true;
            } else {
                waitFor(1000);
            }
        }
        return false;
    }

    /**
     * initiateMain
     * @return
     * @author Anthony C. Liberto
     */
    public boolean initiateMain() {
        if (ro != null) {
            Middleware.disconnect(ro);
        }
        try {
            ro = connect(curMWObject);
            if (ro != null) {
                return true;
            }
        } catch (RemoteException re) {
            re.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * getRemoteDatabaseInterface
     * @return RemoteDatabaseInterface
     */
    public RemoteDatabaseInterface getRemoteDatabaseInterface() {
        return ro;
    }

    /*
     TIR USRO-R-RLON-645P76
     */
    /**
     * getCurrentMiddlewareObject
     * @return
     * @author Anthony C. Liberto
     */
    public MWObject getCurrentMiddlewareObject() {
        return curMWObject;
    }

    /**
     * getLocalVersion
     * updating call to middleware
     *
     * @author Anthony C. Liberto
     * @return String
     */
    public String getLocalVersion() {
		return Middleware.getMiddlewareVersion();
	}

    /**
     * getCompiledVersion
     * since Optimization is on this will
     * grab the date that the mw.jar was
     * compiled against
     *
     * @author Anthony C. Liberto
     * @return String
     */
    public String getCompiledVersion() {
		return RemoteDatabase.c_strBuildTimeStamp;
	}

    /**
     * getRemoteVersion
     *
     * @author Anthony C. Liberto
     * @return String
     */
	public String getRemoteVersion() {
		String out = "0000-00-00-00.00.00.000000";
		if (ro != null) {
			try {
				out = ro.getVersion();
			} catch (Exception _ex) {
				_ex.printStackTrace();
			}
		}
		return out;
	}

	/**
     * send e-mail
     *
     * @return boolean
     * @author Anthony C. Liberto
     * @param _msg
     * @param _subj
     * @param _to
     */
	public boolean sendMail(String _to, String _subj, String _msg) {
		if (ro != null) {
			try {
				ro.sendEmail(_to,_subj,_msg);
				return true;
			} catch (Exception _ex) {
				if (reconnectMain()) {
					try {
						ro.sendEmail(_to,_subj,_msg);
						return true;
					} catch (Exception _x) {
						_x.printStackTrace();
					}
				} else {
					_ex.printStackTrace();
				}
			}
		}
		return false;
	}

}
