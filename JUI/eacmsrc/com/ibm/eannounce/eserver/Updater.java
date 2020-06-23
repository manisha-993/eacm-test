/**
 *
 * Copyright (c) 2003-2004 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 * e-announce update functionality
 *
 *	updates need to be contained in a zip file.
 *	ea-server.properties last.update should be the update filename and should be in each update.
 *	if eaServer.jar is to be replaces it should be name eaServer.tmp when e-announce is
 *	run it will move it to the proper location.
 *
 * @version 1.3  2004/02/10
 * @author Anthony C. Liberto
 *
 * $Log: Updater.java,v $
 * Revision 1.2  2008/01/30 16:27:10  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:46:48  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:55  tony
 * This is the initial load of OPICM
 *
 * Revision 1.16  2005/09/08 17:59:12  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.15  2005/05/17 17:48:32  tony
 * updated logic to address update of e-announce application.
 * added madatory update functionality as well.
 * improved pref logic for mandatory updates.
 *
 * Revision 1.14  2005/02/03 21:26:15  tony
 * JTest Format Third Pass
 *
 * Revision 1.13  2005/01/28 17:54:21  tony
 * JTest Fromat step 2
 *
 * Revision 1.12  2005/01/26 17:18:50  tony
 * JTest Formatting modifications
 *
 * Revision 1.11  2004/10/27 20:15:36  tony
 * improved updater by adding the ability to automatically
 * restart the e-announce application that was running.
 *
 * Revision 1.10  2004/10/21 21:27:30  tony
 * deprecated an existing method
 *
 * Revision 1.9  2004/10/18 21:02:11  tony
 * fixed flaw in updater logic.
 *
 * Revision 1.8  2004/10/14 21:27:07  tony
 * added ability to copy backups of existing updated items.
 *
 * Revision 1.7  2004/10/13 17:56:18  tony
 * added on-line update functionality.
 *
 * Revision 1.6  2004/05/03 21:43:20  tony
 * updated eServer logic.
 *
 * Revision 1.5  2004/04/28 15:03:13  tony
 * adjusted to delete update automatically
 *
 * Revision 1.4  2004/03/04 18:38:30  tony
 * updater enhancement
 *
 * Revision 1.3  2004/03/03 00:01:42  tony
 * added to functionality, moved firewall to preference.
 *
 * Revision 1.2  2004/02/24 17:08:29  tony
 * firewall enhancement
 *
 * Revision 1.1  2004/02/19 21:36:58  tony
 * e-announce1.3
 *
 *
 */
package com.ibm.eannounce.eserver;
import java.io.*;
import java.net.*;
import java.util.zip.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class Updater {
    static final long serialVersionUID = 1L;
    private String sUpdate = null;
    /**
     * status
     */
    protected UpdateStatus status = new UpdateStatus() {
    	private static final long serialVersionUID = 1L;
    	public void processOK() {

            restartApplication();
            exit();
            return;
        }
    };
 //   private String sUpPath = null;
    private String sHome = null;
    private String sRestartPath = null;

    /**
     * Constructor
     * @param _upFile
     * @param _home
     * @author Anthony C. Liberto
     */
    public Updater(String _upFile, String _home) {
        setUpdate(_upFile);
        setHome(_home);
        return;
    }

    /**
     * setUpdate
     * @param _s
     * @author Anthony C. Liberto
     */
    protected void setUpdate(String _s) {
        if (_s != null) {
            sUpdate = new String(_s);
        }
        return;
    }

    /**
     * setHome
     * @param _s
     * @author Anthony C. Liberto
     */
    protected void setHome(String _s) {
        if (_s != null) {
            sHome = new String(_s);
        }
        return;
    }

    /**
     * getUpdate
     * @return
     * @author Anthony C. Liberto
     */
    protected String getUpdate() {
        return sUpdate;
    }

    /*
     deprecated
    	protected byte[] getByteArray(String _sHost) {
    		remoteSocket remSock = eChatRemoteServer.getRemoteSocket(_sHost);
    		if (remSock != null) {
    			updatePacket packet = updatePacket.createUpdatePacket();
    			packet.setUpdate(sUpdate);
    			try {
    				status.setMessage("retrieving data from server...", true);
    				return remSock.getRemoteByteArray(packet);
    			} catch (RemoteException _re) {
    				_re.printStackTrace();
    			}
    		}
    		return null;
    	}
    */
    /*
     file to be updated.
     server address
     */
    /**
     * main
     * @param _s
     * @author Anthony C. Liberto
     */
    public static void main(String[] _s) {
        if (_s != null) {
            int ii = _s.length;
            String[] parm = new String[_s.length];
            try {
                for (int i = 0; i < ii; ++i) {
                    parm[i] = URLDecoder.decode(_s[i], "utf8");
                }
            } catch (UnsupportedEncodingException _uee) {
                _uee.printStackTrace();
                return;
            }
            if (ii > 1 && parm[0] != null && parm[1] != null) {
                Updater me = new Updater(parm[0], parm[1]);
                if (ii > 2 && parm[2] != null) {
                    me.setRestartApplication(parm[1], parm[2]);
                }
                me.status.reset();
                me.update(true);
            }
        }
        return;
    }

    /**
     * update
     * @param _delete
     * @author Anthony C. Liberto
     */
    protected void update(boolean _delete) {
        File f = new File(getUpdate());
        if (f.exists()) {
            unpack(f, _delete);
        }
        saveLog(getUpdate());
        return;
    }

    private void saveLog(String _s) {
        String file = new String(_s + ".log");
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter out = null;
        try {
            fos = new FileOutputStream(file);
            osw = new OutputStreamWriter(fos, "utf8");
            out = new BufferedWriter(osw);
            out.write(status.getMessage());
        } catch (IOException _ioe) {
            _ioe.printStackTrace();
        } finally {
            try {
                if (out != null){
                    out.close();
                }
                if (osw != null) {
                    osw.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException _ioe) {
                _ioe.printStackTrace();
            }
        }
        return;
    }

    /**
     * update
     * @param _update
     * @param _delete
     * @author Anthony C. Liberto
     */
    public void update(File _update, boolean _delete) {
        if (_update.exists()) {
            unpack(_update, _delete);
        } else {
            System.out.println("update missing");
        }
    }

    /**
     * unpack
     * @param _f
     * @param _delete
     * @author Anthony C. Liberto
     */
    protected void unpack(File _f, boolean _delete) {
        ZipFile zip = null;
        int ii = -1;
        FileInputStream fin = null;
        InputStream in = null;
        ZipInputStream zin = null;
        ZipEntry ze = null;
        if (_f != null) {
            try {
                zip = new ZipFile(_f);
                ii = zip.size() + 1;
                status("    initializing update process", false);
                if (_delete) {
                    ++ii;
                }
                status("    processing update " + _f.toString() + "...", ii);
                fin = new FileInputStream(_f);
                in = new BufferedInputStream(fin);
                zin = new ZipInputStream(in);
                ze = null;
                while ((ze = zin.getNextEntry()) != null) {
                    unzip(zin, ze);
                }
                if (_delete) {
                    status("    removing temporary files...", false);
                    if (!_f.delete()) {
                        _f.deleteOnExit();
                        status("        scheduled removal of " + _f.toString(), false);
                    } else {
                        status("        removed " + _f.toString(), false);
                    }
                }
                reportRestartInformation();
                status("update complete", true);
            } catch (FileNotFoundException _fnfe) {
                _fnfe.printStackTrace();
            } catch (IOException _ioe) {
                _ioe.printStackTrace();
            } finally {
                try {
                    if (zin != null) {
                        zin.close();
                    }
                    if (in != null) {
                        in.close();
                    }
                    if (fin != null) {
                        fin.close();
                    }
                    if (zip != null) {
                        zip.close();
                    }
                } catch (IOException _ioe) {
                    _ioe.printStackTrace();
                }
            }
        }
        return;
    }

    /**
     * unzip
     * @param _zin
     * @param _ze
     * @author Anthony C. Liberto
     */
    protected void unzip(ZipInputStream _zin, ZipEntry _ze) {
        int len = 0;
        byte[] b = null;
        FileOutputStream out = null;
        File pref = null;
        if (!_ze.isDirectory()) {
            status("        processing " + _ze.getName() + "...", false);
            try {
                String home = getHome();
                String fName = _ze.getName();
                if (fName.endsWith("eannc.pref")) {
                    status("            resetting preferences...", false);
                    pref = new File(home + fName);
                    if (!pref.delete()) {												//acl_20050516
						pref.deleteOnExit();											//acl_20050516
					}																	//acl_20050516
				} else if (fName.endsWith(".delete")) {									//acl_20050516
					int iLen = fName.length() - 7;										//acl_20050516
					String tName = fName.substring(0,iLen);								//acl_20050516
					status("            detecting " + tName + "...", false);			//acl_20050516
					pref = new File(home + tName);										//acl_20050516
					if (pref.exists()) {												//acl_20050516
						status("                resetting " + tName + "...", false);	//acl_20050516
						if (!pref.delete()) {											//acl_20050516
							pref.deleteOnExit();										//acl_20050516
						}																//acl_20050516
					}																	//acl_20050516
                } else {
                    backup(home, fName);
                    createPathToFile(home + fName);
                    out = new FileOutputStream(home + fName);
                    b = new byte[512];
                    status("            inflating " + _ze.getName() + "...", false);
                    while ((len = _zin.read(b)) != -1) {
                        out.write(b, 0, len);
                    }
                }
            } catch (IOException _ioe) {
                _ioe.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException _ioe) {
                    _ioe.printStackTrace();
                }
            }
        }
        return;
    }

    /**
     * backup
     * @param _home
     * @param _path
     * @author Anthony C. Liberto
     */
    protected void backup(String _home, String _path) {
        String sFileName = _home + _path.replace('/', getFileSep().charAt(0));
        String sBackup = new String(sFileName + ".bak");
        File existing = new File(sFileName);
        if (existing.exists()) {
            int i = 0;
            File tmp = new File(sBackup + i);
            while (tmp.exists()) {
                ++i;
                tmp = new File(sBackup + i);
            }
            if (copy(existing, tmp)) {
                status("            copying: " + sFileName + " to " + tmp.getName(), false);
            } else {
                status("            copy of " + sFileName + " deferred.", false);
            }
        }
        return;
    }

    private boolean copy(File _from, File _to) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        byte[] buf = null;
        int i = 0;
        try {
            fis = new FileInputStream(_from);
            fos = new FileOutputStream(_to);
            buf = new byte[1024];
            try {
                while ((i = fis.read(buf)) != -1) {
                    fos.write(buf, 0, i);
                }
            } catch (IOException _ioe) {
                _ioe.printStackTrace();
                return false;
            }
        } catch (FileNotFoundException _fnf) {
            _fnf.printStackTrace();
            return false;
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException _ioe) {
                _ioe.printStackTrace();
            }
        }
        return true;
    }

    /**
     * createPathToFile
     * @param _path
     * @author Anthony C. Liberto
     */
    protected void createPathToFile(String _path) {
        String fileSep = getFileSep();
        String path = new String(_path.replace('/', fileSep.charAt(0)));
        int index = path.lastIndexOf(fileSep);
        if (index > 0) {
            String sDir = path.substring(0, index);
            File dir = new File(sDir);
            if (!dir.exists()) {
                status("                creating directory: " + sDir, false);
                dir.mkdirs();
            }
        }
        return;
    }

    /**
     * status
     * @param _msg
     * @param _steps
     * @author Anthony C. Liberto
     */
    protected void status(String _msg, int _steps) {
        status.setComplete(false);
        status.setMax(_steps);
        status.setMessage(_msg, true);
        return;
    }

    /**
     * status
     * @param _msg
     * @param _complete
     * @author Anthony C. Liberto
     */
    protected void status(String _msg, boolean _complete) {
        status.setMessage(_msg, true);
        status.setComplete(_complete);
        return;
    }

    /**
     * getFileSep
     * @return
     * @author Anthony C. Liberto
     */
    protected String getFileSep() {
        return System.getProperty("file.separator");
    }

    /**
     * getHome
     * @return
     * @author Anthony C. Liberto
     */
    protected String getHome() {
        return sHome;
    }

    /**
     * getUpdateFile
     * @return
     * @author Anthony C. Liberto
     */
    protected String getUpdateFile() {
        return getHome() + "Updates" + getFileSep() + getUpdate();
    }

    /*
     application restart
     */
    /**
     * setRestartApplication
     * @param _dir
     * @param _exe
     * @author Anthony C. Liberto
     */
    protected void setRestartApplication(String _dir, String _exe) {
        if (_dir != null && _exe != null) {
            sRestartPath = new String(_dir + _exe);
        }
        return;
    }

    /**
     * restartApplication
     * @author Anthony C. Liberto
     */
    protected void restartApplication() {
        if (sRestartPath != null) {
            File exeFile = new File(sRestartPath);
            if (exeFile.exists()) {
                RemoteControl remote = new RemoteControl();
                if (remote.command(sRestartPath)) {
                    pause(2500);
                }
            }
        }
        return;
    }

    private void reportRestartInformation() {
        if (sRestartPath != null) {
            status("will attempt restart of: " + sRestartPath + " upon completion.", false);
        }
        return;
    }

    private void pause(int _millis) {
        pause(Thread.currentThread(), _millis);
        return;
    }

    private void pause(Thread _thread, int _millis) {
        if (_millis > 0) {
            try {
                Thread.sleep(_millis);
            } catch (InterruptedException _ie) {
                _ie.printStackTrace();
            }
        }
        return;
    }

    /**
     * exit
     * @author Anthony C. Liberto
     */
    public void exit() {
        System.exit(0);
    }
}
