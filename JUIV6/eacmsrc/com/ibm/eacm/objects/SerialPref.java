//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.objects;

import java.io.*;
import java.util.HashMap;


/**
 * This class maintains user object preferences, they are serialized and written to disk
 * most other strings, ints, doubles and booleans are held in java preferences and can be imported and exported
 * @author Wendy Stimpson
 */
//$Log: SerialPref.java,v $
//Revision 1.1  2012/09/27 19:39:13  wendy
//Initial code
//
public class SerialPref implements Serializable, EACMGlobals {
	private HashMap<String, Object> map = new HashMap<String, Object>();
	private boolean hasChanges = false;

    private static final String PREFERENCE_FILE = RESOURCE_DIRECTORY + "eannc.pref";
    private static final String PREFERENCE_RESET = RESOURCE_DIRECTORY + "reset.pref";
    private static final String PREFERENCE_KEY = "eannounce.preference.key"; 
    private static final String PREFERENCE_TOKEN = "Test"; //will cause eannc.pref to be reset when changed.

	static final long serialVersionUID = 20020227L;
	private static SerialPref sPref = SerialPref.readFile(); // serialized user preferences
	
	private SerialPref() {}

	/**
     * putPref
     * @param key
     * @param i
     *  
     */
    public static void putPref(String key, int i) {
    	sPref.put(key, new Integer(i));
	}

	/**
     * putPref
     * @param key
     * @param dbl
     *  
     * /
    public static void putPref(String key, double dbl) {
    	sPref.put(key,new Double(dbl));
	}

	/**
     * putPref
     * @param key
     * @param b
     *  
     */
    public static void putPref(String key, boolean b) {
    	sPref.put(key,new Boolean(b));
	}

	/**
     * put
     * @param key
     * @param value
     *  
     */
    public static void putPref(String key, Object value) {
    	sPref.put(key, value);
	}
    private void put(String key, Object value) {
		map.put(key,value);
		hasChanges = true;
	}

	/**
     * getPref
     * @param key
     * @param def
     * @return
     *  
     */
    public static String getPref(String key, String def) {
		return sPref.getThisPref(key, def);
	}
    private String getThisPref(String key, String def) {
		if (map.containsKey(key)) {
			Object o = map.get(key);
			if (o instanceof String) {
				return (String)o;
			}
		}
		return def;
	}

	/**
     * getPrefDouble
     * @param key
     * @param def
     * @return
     *  
     * /
    public static double getPref(String key, double def) {
		return sPref.getThisPref(key, def);
	}
    private double getThisPref(String key, double def) {
    	if (map.containsKey(key)) {
			Object o = map.get(key);
			if (o instanceof Double) {
				return ((Double)o).doubleValue();
			}
		}
		return def;
	}*/

	/**
     * getPrefInt
     * @param key
     * @param def
     * @return
     *  
     */
    public static int getPref(String key, int def) {
		return sPref.getThisPref(key, def);
	}
    private int getThisPref(String key, int def) {
		if (map.containsKey(key)) {
			Object o = map.get(key);
			if (o instanceof Integer) {
				return ((Integer)o).intValue();
			}
		}
		return def;
	}

	/**
     * getPrefBoolean
     * @param key
     * @param def
     * @return
     *  
     */
    public static boolean getPref(String key, boolean def) {
		return sPref.getThisPref(key, def);
	}
    private boolean getThisPref(String key, boolean def) {
    	if (map.containsKey(key)) {
			Object o = map.get(key);
			if (o instanceof Boolean) {
				return ((Boolean)o).booleanValue();
			}
		}

		return def;
	}
	/**
     * getPrefColor
     * @param key
     * @param def
     * @return
     *  
     * /
    public static Color getPref(String key, Color def) {
		return sPref.getThisPref(key, def);
	}
    private Color getThisPref(String key, Color def) {
		if (map.containsKey(key)) {
			Object o = map.get(key);
			if (o instanceof Color) {
				return (Color)o;
			}
		}
		return def;
	}*/

	/**
     * getPrefFont
     * @param key
     * @param def
     * @return
     *  
     * /
    public static Font getPref(String key, Font def) {
		return sPref.getThisPref(key, def);
	}
    private Font getThisPref(String key, Font def) {
		if (map.containsKey(key)) {
			Object o = map.get(key);
			if (o instanceof Font) {
				return (Font)o;
			}
		}
		return def;
	}

	/**
     * removePref
     * @param key
     * @param write
     *  
     */
    public static void removePref(String key) {
    	sPref.removeThisPref(key);
	}
    private void removeThisPref(String key) {
		if (key!=null && map.containsKey(key)) {
			map.remove(key);
			hasChanges = true;
		}
	}

	/**
     * get
     * @param key
     * @return
     *  
     */
    public static Object getPref(String key) {
		return sPref.getThisPref(key);
	}
    private Object getThisPref(String key) {
		if (map.containsKey(key)) {
			return map.get(key);
		}
		return null;
	}

	/**
     * writeFile
     *  
     */
    private void writeFile() {
    	if(hasChanges){
    		Utils.write(PREFERENCE_FILE, this);
    		hasChanges = false;
    	}
	}

    public static void writePreferences() {
    	sPref.writeFile();
	}
	/**
     * reset all saved preferences
     *  
     */
    private void reset() {
		map.clear();
		hasChanges = true;
		writeFile();
	}

	/**
     * readFile
     * @return
     */
    private static SerialPref readFile() {
		SerialPref sp = null;

        if (!resetPreferences()) {
        	sp = (SerialPref)Utils.read(PREFERENCE_FILE);

			if (sp != null) {											
				String key = sp.getThisPref(PREFERENCE_KEY, "");		
				if (!key.equals(PREFERENCE_TOKEN)) {					
					sp.reset();											
					sp.put(PREFERENCE_KEY,PREFERENCE_TOKEN);	
				}														
			}															
		}
		if (sp == null) {
			sp = new SerialPref();
			sp.put(PREFERENCE_KEY,PREFERENCE_TOKEN);		
		}
		return sp;
	}

	private static boolean resetPreferences() {
		File tmpFile = new File(PREFERENCE_RESET);
		File prefFile = null;
        if (tmpFile.exists()) {
			tmpFile.delete();
			prefFile = new File(PREFERENCE_FILE);
			if (prefFile.exists()) {
				prefFile.delete();
			}
			return true;
		}
		return false;
	}
}
