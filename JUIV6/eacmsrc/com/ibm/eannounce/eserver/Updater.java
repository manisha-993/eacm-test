//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eannounce.eserver;

import java.io.*;
import java.net.*;
import java.util.zip.*;

/**
 * it is part of eaServer.jar and executes on the client
 *	updates need to be contained in a zip file.
 *	if eaServer.jar is to be replaced it should be name eaServer.tmp when EACM is
 *	run it will move it to the proper location.
 *
 */
// $Log: Updater.java,v $
// Revision 1.2  2012/11/12 22:48:07  wendy
// AutoUpdate changes
//
// Revision 1.1  2012/09/27 19:39:24  wendy
// Initial code
//
public class Updater {
	static final long serialVersionUID = 1L;

	private static final String INDENT3 = "                ";
	private static final String INDENT2 = "            ";
	private static final String INDENT1 = "        ";
	private static final String INDENT  = "    ";

	private String sUpdate = null;
	private UpdateStatus status = null;
	private String sHome = null;
	private String sRestartPath = null;

	/**
	 * Constructor
	 * @param upFile
	 * @param home
	 */
	private Updater(String upFile, String home) {
		sUpdate = upFile;
		sHome = home;
		status = new UpdateStatus(this);
	}

    /**
     * update is complete, restart the application
     * called when user presses ok button on updatestatus frame
     */
    protected void restartApplication(){
		if (sRestartPath != null) {
			File exeFile = new File(sRestartPath);
			if (exeFile.exists()) {
				try {
					Runtime.getRuntime().exec(sRestartPath);
					pause(2500);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.exit(0);
    }

    /**
     * put error message in status dialog and prevent application restart
     * @param errmsg
     */
    private void setError(String errmsg){
		status.setMessage("Error: "+errmsg);
		status.setComplete();
		sRestartPath = null; // dont allow a restart
    }

    /**
	 * update
	 */
	private void update() {
		status.setVisible(true);

		File f = new File(sUpdate);
		if (f.exists()) {
			unpack(f);
		}else{
			setError("Update file \""+sUpdate+"\" not found");
		}

		// save log
		String file = sUpdate + ".log";
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		BufferedWriter out = null;
		try {
			fos = new FileOutputStream(file);
			osw = new OutputStreamWriter(fos, "utf8");
			out = new BufferedWriter(osw);
			out.write(status.getUpdateInformation());
		} catch (IOException ioe) {
			ioe.printStackTrace();
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
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

	/**
	 * unpack
	 * @param f
	 */
	private void unpack(File f) {
		ZipFile zip = null;
		FileInputStream fin = null;
		InputStream in = null;
		ZipInputStream zin = null;
		ZipEntry ze = null;

		try {
			zip = new ZipFile(f);
			status.setMessage(INDENT+"initializing update process");

			status.setMax(zip.size());
			status.setMessage(INDENT+"processing update " + f.toString() + "...");

			fin = new FileInputStream(f);
			in = new BufferedInputStream(fin);
			zin = new ZipInputStream(in);
			ze = null;
			while ((ze = zin.getNextEntry()) != null) {
				status.setIncrement();
				unzip(zin, ze);
			}

			status.setMessage(INDENT+"removing temporary files...");

			if (!f.delete()) {
				f.deleteOnExit();
				status.setMessage(INDENT1+"scheduled removal of " + f.toString());
			} else {
				status.setMessage(INDENT1+"removed " + f.toString());
			}

			if (sRestartPath != null) {
				status.setMessage("Will attempt restart of: " + sRestartPath + " upon completion.");
			}

			status.setMessage("Update complete");

			status.setIncrement();
			status.setComplete();
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
			setError(fnfe.getMessage());
		} catch (IOException ioe) {
			ioe.printStackTrace();
			setError(ioe.getMessage());
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
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

	/**
	 * unzip
	 * @param zin
	 * @param ze
	 */
	private void unzip(ZipInputStream zin, ZipEntry ze) {
		int len = 0;
		byte[] b = null;
		FileOutputStream out = null;
		File pref = null;
		if (!ze.isDirectory()) {
			status.setMessage(INDENT1+"processing " + ze.getName() + "...");
			try {
				String fName = ze.getName();
				if (fName.endsWith("eannc.pref")) {
					status.setMessage(INDENT2+"resetting preferences...");
					pref = new File(sHome + fName);
					if (!pref.delete()) {
						pref.deleteOnExit();
					}
				} else if (fName.endsWith(".delete")) {
					int iLen = fName.length() - 7;
					String tName = fName.substring(0,iLen);
					status.setMessage(INDENT3+"detecting " + tName + "...");
					pref = new File(sHome + tName);
					if (pref.exists()) {
						status.setMessage(INDENT3+"resetting " + tName + "...");
						if (!pref.delete()) {
							pref.deleteOnExit();
						}
					}
				} else {
					backup(fName);
					createPathToFile(sHome + fName);
					out = new FileOutputStream(sHome + fName);
					b = new byte[512];
					status.setMessage(INDENT2+"inflating " + ze.getName() + "...");
					while ((len = zin.read(b)) != -1) {
						out.write(b, 0, len);
					}
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} finally {
				try {
					if (out != null) {
						out.close();
					}
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}
	}

	/**
	 * backup the specified file by finding the next number and create .bak#
	 * @param path
	 */
	private void backup(String path) {
		String sFileName = sHome + path.replace('/', System.getProperty("file.separator").charAt(0));
		String sBackup = sFileName + ".bak";
		File existing = new File(sFileName);
		if (existing.exists()) {
			int i = 0;
			File tmp = new File(sBackup + i);
			while (tmp.exists()) {
				++i;
				tmp = new File(sBackup + i);
			}
			if (copy(existing, tmp)) {
				status.setMessage(INDENT2+"copying: " + sFileName + " to " + tmp.getName());
			} else {
				status.setMessage(INDENT2+"copy of " + sFileName + " deferred.");
			}
		}
	}

	/**
	 * copy file from one name to another to back it up
	 * @param from
	 * @param to
	 * @return
	 */
	private boolean copy(File from, File to) {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		byte[] buf = null;

		try {
			fis = new FileInputStream(from);
			fos = new FileOutputStream(to);
			buf = new byte[1024];
			try {
				int i = 0;
				while ((i = fis.read(buf)) != -1) {
					fos.write(buf, 0, i);
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
				return false;
			}
		} catch (FileNotFoundException fnf) {
			fnf.printStackTrace();
			return false;
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
				if (fos != null) {
					fos.close();
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		return true;
	}

	/**
	 * createPathToFile
	 * @param path
	 */
	private void createPathToFile(String path2) {
		String fileSep = System.getProperty("file.separator");
		String path = path2.replace('/', fileSep.charAt(0));
		int index = path.lastIndexOf(fileSep);
		if (index > 0) {
			String sDir = path.substring(0, index);
			File dir = new File(sDir);
			if (!dir.exists()) {
				status.setMessage(INDENT3+"creating directory: " + sDir);
				dir.mkdirs();
			}
		}
	}

	/**
	 * set executable name
	 * @param exe
	 */
	private void setRestartApplication(String exe) {
		sRestartPath = sHome + exe;
	}

	/**
	 * pause the current thread
	 * @param millis
	 */
	private void pause(int millis) {
		if (millis > 0) {
			try {
				Thread.sleep(millis);
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
		}
	}

	/**
	 * main
	 * @param s
	 * com.ibm.eannounce.eserver.Updater C%3A%5CEACM_V6%5C201201140718.optional C%3A%5CEACM_V6%5C EACM.exe
	 * arg[0] is zip file name with updated files
	 * arg[1] is the home directory
	 * arg[2] is the exe to restart
	 */
	public static void main(String[] s) {
		if (s != null && s.length>1) {
			int ii = s.length;
			String[] parm = new String[s.length];
			try {
				for (int i = 0; i < ii; ++i) {
					parm[i] = URLDecoder.decode(s[i], "utf8");
				}

				if (parm[0] != null && parm[1] != null) {
					Updater me = new Updater(parm[0], parm[1]);
					if (ii > 2 && parm[2] != null) {
						me.setRestartApplication(parm[2]);
					}

					me.update();
				}
			
			} catch (Exception uee) {
				uee.printStackTrace();
			}
		}
	}
}
