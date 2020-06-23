/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 *  Description of the Class
 *
 * $Log: ExtensionFilter.java,v $
 * Revision 1.1  2007/04/18 19:42:17  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:37:39  tony
 * This is the initial load of OPICM
 *
 * Revision 1.6  2005/09/08 17:58:50  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.5  2005/02/04 16:57:40  tony
 * JTest Formatter Third Pass
 *
 * Revision 1.4  2005/02/02 21:30:08  tony
 * JTest Second Pass
 *
 * Revision 1.3  2005/01/27 23:18:18  tony
 * JTest Formatting
 *
 * Revision 1.2  2004/02/19 21:34:52  tony
 * e-announce1.3
 *
 * Revision 1.1.1.1  2004/02/10 16:59:25  tony
 * This is the initial load of OPICM
 *
 * Revision 1.1.1.1  2003/03/03 18:03:40  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2002/11/07 16:58:09  tony
 * added/adjusted copyright statement
 *
 */
package com.elogin;
import java.io.File;
import java.io.FilenameFilter;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class ExtensionFilter implements FilenameFilter {
	private String extension;

	/**
	 *  Create an ExtensionFilter with the geven exctension
	 *
	 * @param  ext  the extension to limit on
	 */
	public ExtensionFilter(String ext) {
		extension = ext.toLowerCase();                                                          //010168
	}

	/**
	 *  Description of the Method
	 *
	 * @return    Description of the Return Value
	 */
	public String toString() {
		return extension;
	}

	/**
	 *  given the file does the name fit the extension criteria
	 *
	 * @param  dir   the file location
	 * @param  name  the name of the file
	 * @return       does the filename fit the criteria
	 */
	public boolean accept(File dir, String name) {
		if (extension.startsWith(".")) {
			return (name.toLowerCase().endsWith(extension));
		}                                                                                       //010168
		return doesMatch(name);
	}

	/**
	 *  does the extenstion match
	 *
	 * @param  _name  the fileName to be validated
	 * @return        true if the extension matches
	 */
	private boolean doesMatch(String _name) {
		String name = new String(_name.toLowerCase());
		StringTokenizer st = new StringTokenizer(extension, "*");
		Vector v = new Vector();
		int ii = -1;
        if (extension.startsWith("*")) {
			v.add("");
		}
		while (st.hasMoreTokens()) {
			v.add(st.nextToken().toLowerCase());
		}
		if (extension.endsWith("*")) {
			v.add("");
		}
		ii = v.size();

		if (ii == 1 || v.isEmpty()) {
			return extension.equals(name);
		} else if (have((String)v.get(0)) && !name.startsWith(((String)v.get(0)))) {
			return false;
		} else if (have((String)v.get(ii - 1)) && !name.endsWith(((String)v.get(ii - 1)))) {
			return false;
		} else {
			int index = 0;
			for (int i = 1; i < (ii - 1); ++i) {
				String strMatch = (String)v.get(i);
				index = name.indexOf(strMatch, index);
				if (index == -1) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean have(String s) {
		String test = null;
        try {
			if (s == null) {
				return false;
			}
			test = s.trim();
			if (test == null || test.length() == 0 || test.equals("")) {
				return false;
            } else {
				return true;
			}
		} catch (Exception ex) {
            EAccess.report(ex,false);
        }
		return false;
	}
}

