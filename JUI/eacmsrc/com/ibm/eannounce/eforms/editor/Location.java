/**
 * Copyright (c) 2001 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 * @version 2.3
 * @date 2001-04-01
 * @author Anthony C. Liberto
 *
 * $Log: Location.java,v $
 * Revision 1.1  2007/04/18 19:44:34  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:37:57  tony
 * This is the initial load of OPICM
 *
 * Revision 1.6  2005/09/08 17:59:03  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.5  2005/02/03 21:26:12  tony
 * JTest Format Third Pass
 *
 * Revision 1.4  2005/02/03 19:42:21  tony
 * JTest Third pass
 *
 * Revision 1.3  2005/02/01 20:48:33  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 19:15:16  tony
 * JTest Format
 *
 * Revision 1.1.1.1  2004/02/10 16:59:41  tony
 * This is the initial load of OPICM
 *
 * Revision 1.1.1.1  2003/03/03 18:03:47  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2002/08/16 20:42:00  tony
 * preferences
 *
 * Revision 1.3  2002/02/13 15:55:36  tony
 * adjusted trace statements.
 *
 * Revision 1.2  2002/02/13 01:58:06  tony
 * update to test system.
 *
 * Revision 1.1.1.1  2001/11/29 19:00:19  tony
 * This is the initial load of OPICM
 *
 * Revision 1.2  2001/11/13 21:25:46  tony
 * added serialization to location for future expansion.
 *
 * Revision 1.1.1.1  2001/08/06 21:39:18  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2001/06/03 20:53:56  tony
 * added log statement and copyright statment to the code.
 *
 */
package com.ibm.eannounce.eforms.editor;
import com.elogin.Routines;
import java.io.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class Location implements Serializable {
	static final long serialVersionUID = 19721222L;
	private String dir = null;
	private String file = null;
	private String sep = System.getProperty("file.separator");

	/**
     * Location
     * @param d
     * @author Anthony C. Liberto
     */
    public Location(String d) {
		setDirectory(d);
		return;
	}

	/**
     * Location
     * @param d
     * @param f
     * @author Anthony C. Liberto
     */
    public Location(String d, String f) {
		setDirectory(d);
		setFileName(f);
		return;
	}

	/**
     * setDirectory
     * @param s
     * @author Anthony C. Liberto
     */
    public void setDirectory(String s) {
		dir = new String(s);
		return;
	}

	/**
     * getDirectory
     * @return
     * @author Anthony C. Liberto
     */
    public String getDirectory() {
		return dir;
	}

	/**
     * setFileName
     * @param s
     * @author Anthony C. Liberto
     */
    public void setFileName(String s) {
		file = new String(s);
		return;
	}

	/**
     * getFileName
     * @return
     * @author Anthony C. Liberto
     */
    public String getFileName() {
		return file;
	}

	/**
     * getFileSep
     * @return
     * @author Anthony C. Liberto
     */
    public String getFileSep() {
		return sep;
	}

	/**
     * getLocation
     * @return
     * @author Anthony C. Liberto
     */
    public String getLocation() {
		return dir + file;
	}

	/**
     * copyToFile
     * @param s
     * @author Anthony C. Liberto
     */
    public void copyToFile(String s) {											//013244
		File inFile = null;
		File outFile = null;
		FileInputStream fis = null;
		FileOutputStream fos = null;
        byte [] b = new byte[512];
        int len = 0;
		try {
			inFile = new File(getLocation());
			outFile = new File(s);
			fis = new FileInputStream(inFile);
			fos = new FileOutputStream(outFile);
			while ( (len=fis.read(b))!= -1 ) {
				fos.write(b,0,len);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
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
		return;																	//013244
	}																			//013244

	/**
     * appendDirectory
     * @param s
     * @author Anthony C. Liberto
     */
    public void appendDirectory(String s) {
		setDirectory(dir + s + sep);
		return;
	}

	/**
     * isValid
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isValid() {
		if (dir == null || file == null) {
			return false;
		}
		if (!Routines.have(dir) || !Routines.have(file)) {
			return false;
		}
		return true;
	}
}

