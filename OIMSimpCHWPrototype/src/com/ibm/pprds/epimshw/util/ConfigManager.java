package com.ibm.pprds.epimshw.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.Vector;

import org.apache.log4j.Logger;
//import versata.common.DataConst;
//import versata.vls.ServerEnvironment;
//import versata.vls.VSORBSessionImpl;



import com.ibm.pprds.epimshw.PropertyKeys;




public class ConfigManager
{
	//public static final String CONFIG_FOLDER = "/WEB-INF/config/";   // WEB-INF/config/
	public static final String CONFIG_FOLDER = "";   // WEB-INF/config/
	public static final String BUSINESS_OBJECT_PACKAGE_NAME = "EPIMSSW";
	public static final String LOCAL_REAL_PATH = "C:/development/epimssw/epimssw/properties/test";
	private static Logger logger = LogManager.getLogManager().getPromoteLogger();
	protected static Hashtable map = new Hashtable();
	static
	{
		map.put(PropertyKeys.KEY_CONFIG_FOLDER, CONFIG_FOLDER);
		//map.put(PropertyKeys.KEY_SYSTEM_REAL_PATH, LOCAL_REAL_PATH);
	}
	protected static Vector files = new Vector();
	protected static ConfigManager myself= null;
	private static boolean configured = false;

	private ConfigManager()
	{
	}

	public static synchronized ConfigManager getConfigManager()
	{
		if (myself == null)
		{
    		myself = new ConfigManager();
			if(!configured)
			{
				//Logfactory.configureLog4J();
				configured = true;
			}
		}
		return myself;
	}

/*	public static synchronized ConfigManager getConfigManager(String file)
	{
		if (myself == null)
		{
			myself = new ConfigManager();
		}
		myself.add(file);
		return myself;
	}*/

	public void startDeamons()
	{
		//start other threads here
//		new FileCleanup();
//		new IdocCleanup();
		//new EplMailer().start();
	}

	public void add(String newFile)
	{
		newFile = newFile + ".properties";
		if (!files.contains(newFile))
		{
			logger.info("Loading configuration file " + newFile);
			files.addElement(newFile);
			load(newFile);
		}
	}

	public void add(File newFile)
	{
		if (!files.contains(newFile.getName()))
		{
			logger.info("Loading configuration file " + newFile.getName());
			files.addElement(newFile.getName());
			load(newFile);
		}
	}

	public void addAllConfigFiles()
	{
		addAll(getString(PropertyKeys.KEY_SYSTEM_REAL_PATH)
			+ getString(PropertyKeys.KEY_CONFIG_FOLDER));
	}

	public void addAll(String dir)
	{
		File file = new File(dir);
		logger.debug("Absolute path: " + file.getAbsolutePath());
		File[] files = file.listFiles();
		if (files != null)
		{
			for(int i=0;i<files.length;i++)
			{
				if (files[i].getName().endsWith(".properties"))
				{
					add(files[i]);
				}
			}
		}
		else
		{
			logger.error("Config file path " + dir 
				+ " does not denote a directory."
				+ " Properties will not be loaded by ConfigManager");
		}

	}

	public boolean load(String filename)
	{
		String path = getString(PropertyKeys.KEY_SYSTEM_REAL_PATH) +
					  getString(PropertyKeys.KEY_CONFIG_FOLDER) +
					  filename;
		return load(new File(path));
	}

	public boolean load(File file)
	{
		try
		{
			Properties p = new Properties();
			p.load(new FileInputStream(file));
			add(p);
			return true;
		}
		catch(Exception e)
		{
			logger.error("Configuration file could not be loaded from path " +
							file.getAbsolutePath() + ". Error was " + e);
			return false;
		}
	}

	public void add(Properties newProp)
	{
		Enumeration keys = newProp.keys();
		String key;
		while (keys.hasMoreElements())
		{
			key = (String) keys.nextElement();
			Object value = newProp.getProperty(key);
			Object ptr = map.put(key,value);
			if ((ptr != null) && (!ptr.equals(value)))
				logger.debug("Resetting config setting " + key + " from " + ptr + " to " + value);
		}
	}

	public Object put(String key, String parm)
	{
		Object last =  map.put(key,parm);
		if ((last != null) && (!last.equals(parm)))
				logger.debug("Resetting config setting " + key + " from " + last + " to " + parm);
		return last;
	}

	public String getString(String key)
	{
		String result = (String) map.get(key);
		if (null == result)
		{
			logger.warn("Property key " + key + " was not found");
		}
		else if (result.trim().equalsIgnoreCase("null"))
		{
			result = null;
		}
		return result;
	}


	public String getString(String key, boolean noNull)
	{
		String result = getString(key);
		if (noNull && null == result)
		{
			result = "";
		}
		return result;
	}
	
			
	public int getInt(String key)
	{
		String value = getString(key);
		try
		{
			return Integer.parseInt(value.trim());
		}
		catch(NumberFormatException nfe)
		{
			if (value == null)
				logger.error("Property " + key + " was not loaded");
			else
				logger.error("Property " + key + " has value " + value + " which could not be parsed as an int");
			throw nfe;
		}
	}
	
	public int getInt(String key, int defaultValue)
	{
		String value = getString(key);
		if (null == value)
		{
			return defaultValue;
		}
		int result = defaultValue;
		try
		{
			result = Integer.parseInt(value.trim());
		}
		catch(Exception nfe)
		{
			if (value == null)
			{
				logger.warn("Integer property " + key + " was not defined."
					+ " Using default value: " + defaultValue);
				return defaultValue;
			}
			else
			{
				logger.warn("Integer property " + key + " was specified but is not an integer.  value: "
					 + value + " Using default value: " + defaultValue);
				return defaultValue;
			}
		}
		return result;
	}
		
	public boolean getBoolean(String key)
	{
		return "true".equalsIgnoreCase(getString(key));
	}

	public void cacheFlush()
	{
		//don't actually empty as all contained object can't be reloaded.
		for(int i=0;i < files.size(); i++)
		{
			try
			{
				reload((String) files.elementAt(i));
			}
			catch (MissingResourceException mre)
			{
			}
		}
		logger.debug("ConfigManager flushed and reloaded");
	}

	public void reload(String fileName)
	{
		logger.debug("Reloading " + fileName);
		load(fileName);
	}
	
//	public void configureLog4J()
//	{
//		Logfactory.configureLog4J();
//	}

	public static void main(String[] args)
	{
		ConfigManager config = getConfigManager();
		String realPath = LOCAL_REAL_PATH;
		System.out.println("Setting config property: " + PropertyKeys.KEY_SYSTEM_REAL_PATH);
		config.put(PropertyKeys.KEY_SYSTEM_REAL_PATH, realPath);
		//config.configureLog4J();
		config.addAllConfigFiles();
		//LogManager.getLogManager().getImportLogger().info("Test write to database");
		System.out.println("DONE");
	}

	/**
	 * Get a String array containing all config parmaters.
	 */	
	public String[][] getConfigParameters()
	{
		
		String[][] values = new String[map.size()][2];
		int i = 0;
		for (Enumeration enumValues = map.keys(); enumValues.hasMoreElements(); i++)
		{
			String key = (String) enumValues.nextElement();	
			Object oValue = map.get(key);
			values[i][0] = key;
			values[i][1] = getString(key);
		}
		return values;
	}
}
