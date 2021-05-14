/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: EGraphics.java,v $
 * Revision 1.1  2007/04/18 19:42:17  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:37:37  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:58:52  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/02/02 21:30:06  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 23:18:18  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:24  tony
 * This is the initial load of OPICM
 *
 * Revision 1.3  2003/04/11 20:02:25  tony
 * added copyright statements.
 *
 */
package com.elogin;
import java.awt.Image;
import java.awt.image.ImageProducer;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class EGraphics extends Object{
	private Class redirectClass = null;

	/**
     * eGraphics
     * @author Anthony C. Liberto
     */
    public EGraphics() {}

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
     * getImage
     * @param _file
     * @return
     * @author Anthony C. Liberto
     */
    public Image getImage(String _file) {
		return getImage(_file,getRedirect());
	}

	/**
     * getImage
     * @param _file
     * @param _class
     * @return
     * @author Anthony C. Liberto
     */
    public Image getImage(String _file, Class _class) {
		URL url = null;
		ImageProducer ip = null;
		if (_file == null || _file.equalsIgnoreCase("null")) {
			return null;
		}
		if (_class == null) {
			url = getClass().getResource(_file);
		} else {
			url = _class.getResource(_file);
		}
		if (url != null) {
			try {
				ip = (ImageProducer)url.getContent();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return (url == null) ? Toolkit.getDefaultToolkit().createImage(_file) : Toolkit.getDefaultToolkit().createImage(ip);
	}

	/**
     * getImageIcon
     * @param _file
     * @return
     * @author Anthony C. Liberto
     */
    public ImageIcon getImageIcon(String _file) {
		return getImageIcon(_file,getRedirect());
	}

	/**
     * getImageIcon
     * @param _file
     * @param _class
     * @return
     * @author Anthony C. Liberto
     */
    public ImageIcon getImageIcon(String _file, Class _class) {
		URL url = null;
		if (_file == null || _file.equalsIgnoreCase("null")) {
			return null;
		}
		if (_class == null) {
			url = getClass().getResource(_file);
		} else {
			url = _class.getResource(_file);
		}
		return (url == null) ? new ImageIcon(_file) : new ImageIcon(url);
	}
}
