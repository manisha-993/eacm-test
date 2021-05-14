/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: HTMLGrabber.java,v $
 * Revision 1.1  2007/04/18 19:42:17  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:37:39  tony
 * This is the initial load of OPICM
 *
 * Revision 1.5  2005/09/08 17:58:56  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.4  2005/02/04 16:57:41  tony
 * JTest Formatter Third Pass
 *
 * Revision 1.3  2005/02/02 21:30:08  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 23:18:17  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:25  tony
 * This is the initial load of OPICM
 *
 * Revision 1.2  2003/10/29 00:22:23  tony
 * removed System.out. statements.
 *
 * Revision 1.1  2003/07/29 21:34:32  tony
 * updated form functionality.
 *
 * Revision 1.2  2003/04/11 17:31:34  tony
 * updated logic so that eResource will
 * compile with the rest of the application.
 *
 * Revision 1.1.1.1  2003/03/03 18:03:43  tony
 * This is the initial load of OPICM
 *
 * Revision 1.7  2002/11/07 16:58:09  tony
 * added/adjusted copyright statement
 *
 */
package com.elogin;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class HTMLGrabber extends Object implements EAccessConstants {
	private String encoding = EANNOUNCE_FILE_ENCODE;
	private String delim = RETURN;
	private Class redirectClass = null;

	/**
     * htmlGrabber
     * @author Anthony C. Liberto
     */
    public HTMLGrabber() {
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
     * getRedirect
     * @return
     * @author Anthony C. Liberto
     */
    public Class getRedirect() {
		return redirectClass;
	}

	/**
     * setRedirect
     * @param _class
     * @author Anthony C. Liberto
     */
    public void setRedirect(String _class) {
		if (_class == null || _class.equalsIgnoreCase("null")) {
			redirectClass = null;
			return;
		}
		try {
			setRedirect(Class.forName(_class));
		} catch (ClassNotFoundException _cnfe) {
			_cnfe.printStackTrace();
		}
		return;
	}

	/**
     * setRedirect
     * @param _class
     * @author Anthony C. Liberto
     */
    public void setRedirect(Class _class) {
		redirectClass = _class;
		return;
	}

	/**
     * getHTML
     * @param _file
     * @return
     * @author Anthony C. Liberto
     */
    public String getHTML(String _file) {
		return getHTML(_file,getRedirect());
	}

	/**
     * getHTML
     * @param _file
     * @param _class
     * @return
     * @author Anthony C. Liberto
     */
    public String getHTML(String _file, Class _class) {
		URL url = null;
        if (_file == null || _file.equals("null")) {
			return null;
		}
		url = getURL(_file,_class);

		if (url != null) {
			return getHTMLString(url,false);
		}
		return null;
	}

	/**
     * getURL
     * @param _file
     * @param _class
     * @return
     * @author Anthony C. Liberto
     */
    public URL getURL(String _file, Class _class) {
		if (_class == null) {
			return getClass().getResource(_file);
		} else {
			return _class.getResource(_file);
		}
	}

	/**
     * XMLExists
     * @param _fileName
     * @return
     * @author Anthony C. Liberto
     */
    public boolean xmlExists(String _fileName) {
		if (eaccess().fileExist(RESOURCE_DIRECTORY + _fileName)) {
			return true;
		}
		try {
			URL url = getURL(_fileName,getRedirect());
			if (url != null) {				//21862
				url.getContent();
				return true;
			}								//21862
		} catch (IOException _ioe) {
			_ioe.printStackTrace();
		}
		return false;
	}

	private String getHTMLString(URL _url, boolean delimit) {
		StringBuffer out = new StringBuffer();
		String temp = null;
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader inStream = null;

		try {
			is = _url.openStream();
			isr = new InputStreamReader(is,encoding);
			inStream = new BufferedReader(isr);
			while ((temp = inStream.readLine()) != null) {
				if (delimit) {
					out.append(temp + delim);

				} else {
					out.append(temp);
				}
			}
		} catch (UnsupportedEncodingException uee) {
			uee.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
            try {
                if (inStream != null) {
                    inStream.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException _ioe) {
                _ioe.printStackTrace();
            }
		}
		return out.toString();
	}
}
