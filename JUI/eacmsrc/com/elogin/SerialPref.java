/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: SerialPref.java,v $
 * Revision 1.1  2007/04/18 19:42:18  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:37:40  tony
 * This is the initial load of OPICM
 *
 * Revision 1.8  2005/09/08 17:58:57  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.7  2005/02/08 16:33:58  tony
 * JTest Formatting Fourth pass
 *
 * Revision 1.6  2005/02/04 16:57:41  tony
 * JTest Formatter Third Pass
 *
 * Revision 1.5  2005/02/03 19:42:21  tony
 * JTest Third pass
 *
 * Revision 1.4  2005/02/02 17:30:22  tony
 * JTest Second Pass
 *
 * Revision 1.3  2005/01/27 23:18:18  tony
 * JTest Formatting
 *
 * Revision 1.2  2004/06/03 14:50:56  tony
 * added abiltity to reset preferences with the presence of
 * a reset.pref file.
 *
 * Revision 1.1.1.1  2004/02/10 16:59:26  tony
 * This is the initial load of OPICM
 *
 * Revision 1.11  2004/01/20 21:30:59  tony
 * added comments
 *
 * Revision 1.10  2004/01/20 19:25:10  tony
 * added a peek to the logic so i can see values for serial
 * preferences.  I can also overwrite them.
 *
 * Revision 1.9  2004/01/20 17:30:35  tony
 * acl_20040120
 * enhanced code, added convenience methods.
 *
 * Revision 1.8  2004/01/20 00:29:38  tony
 * 53562
 *
 * Revision 1.7  2004/01/20 00:20:50  tony
 * 53562
 *
 * Revision 1.6  2004/01/14 18:47:57  tony
 * acl_20040114
 *   1)  updated logic to allow for manual load of serial pref.
 *   2)  trigger a middleware selection if no default middleware is defined.
 *   3)  prevent a put of parent when non new row is selected.
 *
 * Revision 1.5  2003/10/29 00:22:22  tony
 * removed System.out. statements.
 *
 * Revision 1.4  2003/06/25 23:49:13  tony
 * added eCipher which will encrypt Strings.  This will allow
 * for safe replaying of passwords.
 *
 * Revision 1.3  2003/03/12 23:51:09  tony
 * accessibility and column order
 *
 * Revision 1.2  2003/03/07 21:40:47  tony
 * Accessibility update
 *
 * Revision 1.1.1.1  2003/03/03 18:03:40  tony
 * This is the initial load of OPICM
 *
 * Revision 1.8  2002/11/26 22:16:53  tony
 * adjusted logic because serialPref needed to be static.
 *
 * Revision 1.7  2002/11/12 23:37:19  tony
 * System.outs removed
 *
 * Revision 1.6  2002/11/07 16:58:09  tony
 * added/adjusted copyright statement
 *
 */
package com.elogin;
import java.awt.*;
import java.io.*;
import java.util.HashMap;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class SerialPref implements Serializable, EAccessConstants {
//	public static final String EDITOR_PATH = "EDITOR_PATH";
//	public static final String PASS_WORD = "aslkj";

//	private static final String fileName = RESOURCE_DIRECTORY + "eannc.pref";	//update because made static
//	private File f = new File(fileName);
	private File f = null;

	private HashMap map = new HashMap();

	static final long serialVersionUID = 20020227L;

    /**
     * create a new empyt serial string
     */
	public SerialPref() {
		return;
	}

	/**
     * exists
     * @return
     * @author Anthony C. Liberto
     */
    public boolean exists() {
		if (f == null) {
			f = new File(PREFERENCE_FILE);
		}
		return f.exists();
	}

	/**
     * putPrefString
     * @param _key
     * @param _val
     * @author Anthony C. Liberto
     */
    public void putPrefString(String _key, String _val) {
		put(_key,_val);
		return;
	}

	/**
     * putPrefInteger
     * @param _key
     * @param _i
     * @author Anthony C. Liberto
     */
    public void putPrefInteger(String _key, int _i) {
		put(_key, new Integer(_i));
		return;
	}

	/**
     * putPrefDouble
     * @param _key
     * @param _dbl
     * @author Anthony C. Liberto
     */
    public void putPrefDouble(String _key, double _dbl) {
		put(_key,new Double(_dbl));
		return;
	}

	/**
     * putPrefPoint
     * @param _key
     * @param _pt
     * @author Anthony C. Liberto
     */
    public void putPrefPoint(String _key, Point _pt) {
		put(_key, _pt);
		return;
	}

	/**
     * putPrefDimension
     * @param _key
     * @param _d
     * @author Anthony C. Liberto
     */
    public void putPrefDimension(String _key, Dimension _d) {
		put(_key, _d);
		return;
	}

	/**
     * putPrefColor
     * @param _key
     * @param _c
     * @author Anthony C. Liberto
     */
    public void putPrefColor(String _key, Color _c) {
		put(_key, _c);
		return;
	}

	/**
     * putPrefFont
     * @param _key
     * @param _f
     * @author Anthony C. Liberto
     */
    public void putPrefFont(String _key, Font _f) {
		put(_key, _f);
		return;
	}

	/**
     * putPrefBoolean
     * @param _key
     * @param _b
     * @author Anthony C. Liberto
     */
    public void putPrefBoolean(String _key, boolean _b) {
		put(_key,new Boolean(_b));
		return;
	}

	/**
     * putPrefBoolean
     * @param _key
     * @param _b
     * @author Anthony C. Liberto
     */
    public void putPrefBoolean(String _key, Boolean _b) {
		put(_key, _b);
		return;
	}

	/**
     * put
     * @param _key
     * @param _value
     * @author Anthony C. Liberto
     */
    public void put(Object _key, Object _value) {
		map.put(_key,_value);
		writeFile();
		return;
	}

	/**
     * getPrefString
     * @param _key
     * @param _def
     * @return
     * @author Anthony C. Liberto
     */
    public String getPrefString(String _key, String _def) {
		if (containsKey(_key)) {
			Object o = map.get(_key);
			if (o instanceof String) {
				return (String)o;
			}
		}
		return _def;
	}

	/**
     * getPrefDouble
     * @param _key
     * @return
     * @author Anthony C. Liberto
     */
    public Double getPrefDouble(String _key) {
		if (containsKey(_key)) {
			Object o = map.get(_key);
			if (o instanceof Double) {
				return (Double)o;
			}
		}
		return null;
	}

	/**
     * getPrefDouble
     * @param _key
     * @param _def
     * @return
     * @author Anthony C. Liberto
     */
    public double getPrefDouble(String _key, double _def) {
		Double dbl = getPrefDouble(_key);
		if (dbl != null) {
			return dbl.doubleValue();
		}
		return _def;
	}

	/**
     * getPrefInteger
     * @param _key
     * @return
     * @author Anthony C. Liberto
     */
    public Integer getPrefInteger(String _key) {
		if (containsKey(_key)) {
			Object o = map.get(_key);
			if (o instanceof Integer) {
				return (Integer)o;
			}
		}
		return null;
	}

	/**
     * getPrefInt
     * @param _key
     * @param _def
     * @return
     * @author Anthony C. Liberto
     */
    public int getPrefInt(String _key, int _def) {
		Integer i = getPrefInteger(_key);
		if (i != null) {
			return i.intValue();
		}
		return _def;
	}

	/**
     * getPrefBoolean
     * @param _key
     * @return
     * @author Anthony C. Liberto
     */
    public Boolean getPrefBoolean(String _key) {
		if (containsKey(_key)) {
			Object o = map.get(_key);
			if (o instanceof Boolean) {
				return (Boolean)o;
			}
		}
		return null;
	}

	/**
     * getPrefBoolean
     * @param _key
     * @param _def
     * @return
     * @author Anthony C. Liberto
     */
    public boolean getPrefBoolean(String _key, boolean _def) {
		Boolean bln = getPrefBoolean(_key);
		if (bln != null) {
			return bln.booleanValue();
		}
		return _def;
	}

	/**
     * getPrefPoint
     * @param _key
     * @return
     * @author Anthony C. Liberto
     */
    public Point getPrefPoint(String _key) {
		if (containsKey(_key)) {
			Object o = map.get(_key);
			if (o instanceof Point) {
				return (Point)o;
			}
		}
		return null;
	}

	/**
     * getPrefDimension
     * @param _key
     * @return
     * @author Anthony C. Liberto
     */
    public Dimension getPrefDimension(String _key) {
		if (containsKey(_key)) {
			Object o = map.get(_key);
			if (o instanceof Dimension) {
				return (Dimension)o;
			}
		}
		return null;
	}

	/**
     * getPrefColor
     * @param _key
     * @param _def
     * @return
     * @author Anthony C. Liberto
     */
    public Color getPrefColor(String _key, Color _def) {
		if (containsKey(_key)) {
			Object o = map.get(_key);
			if (o instanceof Color) {
				return (Color)o;
			}
		}
		return _def;
	}

	/**
     * getPrefFont
     * @param _key
     * @param _def
     * @return
     * @author Anthony C. Liberto
     */
    public Font getPrefFont(String _key, Font _def) {
		if (containsKey(_key)) {
			Object o = map.get(_key);
			if (o instanceof Font) {
				return (Font)o;
			}
		}
		return _def;
	}

	/**
     * get
     * @param _key
     * @return
     * @author Anthony C. Liberto
     */
    public Object get(Object _key) {
		if (containsKey(_key)) {
			return map.get(_key);
		}
		return null;
	}

	/**
     * removePref
     * @param _key
     * @param _write
     * @author Anthony C. Liberto
     */
    public void removePref(String _key, boolean _write) {
		if (containsKey(_key)) {
			map.remove(_key);
			if (_write) {
				writeFile();
			}
		}
		return;
	}

	/**
     * get
     * @param _key
     * @return
     * @author Anthony C. Liberto
     */
    public Object get(String _key) {
		if (containsKey(_key)) {
			return map.get(_key);
		}
		return null;
	}

	/**
     * containsKey
     * @param _key
     * @return
     * @author Anthony C. Liberto
     */
    public boolean containsKey(Object _key) {
		return map.containsKey(_key);
	}

	/**
     * writeFile
     * @author Anthony C. Liberto
     */
    public void writeFile() {
		FileOutputStream fout = null;
        ObjectOutputStream outStream = null;
        try {
			fout = new FileOutputStream(PREFERENCE_FILE);
			outStream = new ObjectOutputStream(fout);
			outStream.writeObject(this);
			outStream.reset();			//JTest
			outStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (outStream != null) {
					outStream.close();
				}
				if (fout != null) {
					fout.close();				//s80
				}
			} catch (IOException _ioe) {
				_ioe.printStackTrace();
			}
		}
		return;
	}

	/**
     * reset
     * @author Anthony C. Liberto
     */
    public void reset() {
		map.clear();
		writeFile();
		return;
	}

	/**
     * readFile
     * @return
     * @author Anthony C. Liberto
     */
    public static SerialPref readFile() {
		SerialPref sp = null;
		FileInputStream fin = null;
        ObjectInputStream inStream = null;
        if (!resetPreferences()) {
			try {
				fin = new FileInputStream(PREFERENCE_FILE);
				inStream = new ObjectInputStream(fin);
				sp = (SerialPref)inStream.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (inStream != null) {
						inStream.close();
					}
					if (fin != null) {
						fin.close();			//s80
					}
				} catch (IOException _ioe) {
					_ioe.printStackTrace();
				}
			}

			if (sp != null) {											//53562
				String key = sp.getPrefString(PREFERENCE_KEY, "");		//53562
				if (!key.equals(PREFERENCE_TOKEN)) {					//53562
					sp.reset();											//53562
					sp.putPrefString(PREFERENCE_KEY,PREFERENCE_TOKEN);	//53562
				}														//53562
			}															//53562
		}
		if (sp == null) {
			sp = new SerialPref();
			sp.putPrefString(PREFERENCE_KEY,PREFERENCE_TOKEN);		//53562
		}
		return sp;
	}

/*
 acl_20040114
 */
	/*
	 args are defined as...
	 	put || peek for arg[0]
	 	then...
	 	type;key;value1,value2,valueN

	 	type is Object type
	 	key is Object key
	 	value (at least one) is parms of Object
	 */
	/**
     * main
     * @param _args
     * @author Anthony C. Liberto
     */
    public static void main(String[] _args) {
		if (_args != null) {
			SerialPref sp = readFile();
			if (_args[0].equalsIgnoreCase("put")) {
				sp.putRemote(_args);
			} else if (_args[0].equalsIgnoreCase("peek")) {
				sp.peek(_args);
			}
		}
		return;
	}

	private void putRemote(String[] _s) {
		if (_s != null) {
			int ii = _s.length;
			for (int i=1;i<ii;++i) {
				putRemote(_s[i]);
			}
		}
		return;
	}

	private void putRemote(String _s) {
		if (_s != null) {
			String[] tmp = Routines.getStringArray(_s,";");
			int ii = tmp.length;
			if (ii == 3) {
				putRemote(tmp[0],tmp[1],tmp[2]);
			}
		}
		return;
	}

    private void putRemote(String _type, String _key, String _val) {
		if (_type != null && _key != null && _val != null) {
			if (_type.equalsIgnoreCase("string")) {
				putPrefString(_key,_val);
			} else if (_type.equalsIgnoreCase("integer")) {
				try {
					Integer i = new Integer(_val);
					put(_key, i);
				} catch (NumberFormatException _nfe) {
                    EAccess.report(_nfe,false);
				}
			} else if (_type.equalsIgnoreCase("double")) {
				try {
					Double d = new Double(_val);
					put(_key,d);
				} catch (NumberFormatException _nfe) {
                    EAccess.report(_nfe,false);
				}
			} else if (_type.equalsIgnoreCase("point")) {
				Point pt = getPoint(_val);
				if (pt != null) {
					putPrefPoint(_key,pt);
				}
			} else if (_type.equalsIgnoreCase("dimension")) {
				Dimension dim = getDimension(_val);
				if (dim != null) {
					putPrefDimension(_key,dim);
				}
			} else if (_type.equalsIgnoreCase("color")) {
				Color c = Color.decode(_val);
				if (c != null) {
					putPrefColor(_key,c);
				}
			} else if (_type.equalsIgnoreCase("font")) {
				Font tmpFont = getFont(_val);
				if (tmpFont != null) {
					putPrefFont(_key,tmpFont);
				}
			} else if (_type.equalsIgnoreCase("boolean")) {
				Boolean b = new Boolean(_val);
				if (b != null) {
					putPrefBoolean(_key,b);
				}
			}
		}
		return;
	}

	private Point getPoint(String _s) {
		int[] i = getIntArray(_s);
		int ii = i.length;
		if (ii != 2) {
			return new Point(i[0],i[1]);
		}
		return null;
	}

	private int[] getIntArray(String _s) {
		String[] tmp = Routines.getStringArray(_s, ARRAY_DELIMIT);
		int ii = tmp.length;
		int[] out = new int[ii];
		for (int i=0;i<ii;++i) {
			out[i] =  Routines.getInt(tmp[i],-1);
		}
		return out;
	}

	private Dimension getDimension(String _s) {
		int[] i = getIntArray(_s);
		int ii = i.length;
		if (ii != 2) {
			return new Dimension(i[0],i[1]);
		}
		return null;
	}

	private Font getFont(String _s) {
		String[] tmp = Routines.getStringArray(_s,ARRAY_DELIMIT);
		int ii = tmp.length;
		if (ii == 3) {
			return getFont(tmp[0],tmp[1],tmp[2]);
		}
		return null;
	}

	private Font getFont(String _name,String _style, String _size) {
		return new Font(_name,Routines.getInt(_style,0), Routines.getInt(_size,8));
	}

/*
 acl_20040120
 */
    /**
     * resetAllPrefs
     * @author Anthony C. Liberto
     */
    public static void resetAllPrefs() {
		SerialPref tmp = readFile();
		if (tmp != null) {
			tmp.reset();
		}
		return;
	}

	/**
     * resetPrefs
     * @param _s
     * @author Anthony C. Liberto
     */
    public static void resetPrefs(String[] _s) {
		if (_s != null) {
			SerialPref tmp = readFile();
			if (tmp != null) {
				int ii = _s.length;
				for (int i=0;i<ii;++i) {
					tmp.removePref(_s[i],false);
				}
				tmp.writeFile();
			}
		}
		return;
	}

	/**
     * peek
     * @param _s
     * @author Anthony C. Liberto
     */
    public void peek(String[] _s) {
		if (_s != null) {
			int ii = _s.length;
			for (int i=1;i<ii;++i) {
				Object o = get(_s[i]);
				if (o != null) {
					System.out.println("value for " + _s[i] + " is: " + o.toString());
				}
			}
		}
		return;
	}

	private static boolean resetPreferences() {
		File tmpFile = new File(PREFERENCE_RESET);
		File prefFile = null;
        if (tmpFile != null && tmpFile.exists()) {
			tmpFile.delete();
			prefFile = new File(PREFERENCE_FILE);
			if (prefFile != null && prefFile.exists()) {
				prefFile.delete();
			}
			return true;
		}
		return false;
	}
}
