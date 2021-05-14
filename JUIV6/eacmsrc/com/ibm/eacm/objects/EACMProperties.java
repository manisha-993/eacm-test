//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.objects;

import java.io.*;
import java.util.*;

/**********************************************************************************
 * Retrieve configuration properties for <code>EACM</code>
 * @author Wendy Stimpson
 */
// $Log: EACMProperties.java,v $
// Revision 1.2  2013/07/18 18:23:22  wendy
// fix compiler warnings
//
// Revision 1.1  2012/09/27 19:39:13  wendy
// Initial code
//
public final class EACMProperties implements EACMGlobals
{
    /** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2012  All Rights Reserved.";
    /** cvs revision number */
    public static final String VERSION = "$Revision: 1.2 $";
    private static final String PROPERTIES_FNAME = "EACM.properties";
    private static Properties eacmProperties = null;

    static
    {
        // Load the properties from file so they are available for each accessor method
        loadProperties();
    }

    /**
    * Don't let anyone instantiate this class.
    */
    private EACMProperties() {}

    /**
    * Load the properties
    */
    private static final void loadProperties()
    {
        if (eacmProperties == null)
        {
            FileInputStream inProperties = null;
            try {
                inProperties = new FileInputStream(RESOURCE_DIRECTORY + PROPERTIES_FNAME);
                eacmProperties = new Properties(System.getProperties());
                eacmProperties.load(inProperties);
            } catch (Exception x) {
                System.err.println("EACMProperties Unable to loadProperties " + x);
            }
            finally {
                if (inProperties != null) {
                    try {
                        inProperties.close();
                    } catch (java.io.IOException e) {
                        System.out.println("I/O Exception "+e.getMessage());
                    }
                }
            }
        }
    }

    /**
     * access any property
     * @param prop
     * @param def
     * @return
     */
    public static final String getProperty(String prop, String def) {
        return eacmProperties.getProperty(prop, def);
    }
    public static final void setProperty(String key, String value){
    	eacmProperties.setProperty(key, value);
    }
    public static final void remove(String key){
    	eacmProperties.remove(key);
    }
    /**
     * get the property for this key
     * @param key
     * @return
     */
    public static final String getProperty(String key){
    	return eacmProperties.getProperty(key);
    }
    /**
    * Return mandatory update or not
    * @return boolean
    */
    public static final boolean isUpdateMandatory() {
        return eacmProperties.getProperty("mandatory_update", "false").equalsIgnoreCase("true");
    }

    /**
     * Get the maximum number of times to try to get RMI object
     * @return
     */
    public static final int rmiMaxTries() {
    	return Integer.parseInt(eacmProperties.getProperty("rmi.max.count", "10"));
    }

    /**
     * Get the maximum number of workers allowed to run at one time
     * @return
     */
    public static final int rmiMaxWorkers() {
    	return Integer.parseInt(eacmProperties.getProperty("rmi.max.workers.count", "4"));
    }

    /**
     * get the number of days to age the logs
     * @return
     */
    public static final int getLogAge(){
    	return Integer.parseInt(eacmProperties.getProperty("log.age", "7"));
    }
    /**
     * Get default form
     * @return String
     */
     public static final String getDefaultForm() {
         return eacmProperties.getProperty("eacm.default.form", "nav.xml");
     }

     /**
      * Get available forms
      * @return String[]
      */
     public static final String[] getAllForms() {
    	 return Routines.getStringArray(
    			 eacmProperties.getProperty("nav.form.available", "nav.xml,nav_dual.xml,nav_rev.xml"), ",");
     }

     /**
      * Get link types
      * @return String[]
      */
     public static final String[] getLinkTypes() {
    	 return Routines.getStringArray(
    			 eacmProperties.getProperty("eacm.link.type", "noDup,adtnl,lnkRplAll"), ",");
     }

    /**************************
     * list current properties
     * @return
     */
    public static final String listProperties(){
    	StringBuffer sb = new StringBuffer();
		for (Enumeration<?> e = eacmProperties.propertyNames(); e.hasMoreElements();){
			String propname = (String)e.nextElement();
			String prop = (String)eacmProperties.get(propname);
			sb.append(propname+":"+prop+RETURN);
		}
    	return sb.toString();
    }
}
