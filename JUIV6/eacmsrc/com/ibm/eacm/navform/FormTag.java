//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
package com.ibm.eacm.navform;

/****
 * Base class for tags in the navigate layout xml
 * @author Wendy Stimpson
 */
//$Log: FormTag.java,v $
//Revision 1.2  2013/07/18 18:28:40  wendy
//fix compiler warnings
//
//Revision 1.1  2012/09/27 19:39:25  wendy
//Initial code
//
class FormTag {
    private String name = null;
    
    /**
     * setName
     * @param s
     */
    protected void setName(String s) {
        name = s;
    }

    /**
     * getName
     * @return
     */
    protected String getName() {
    	return name;
    }

    /**
     * release memory
     */
    protected void dereference(){
    	name = null;
    }
    /**
     * get Class for specified name
     * @param className
     * @return
     */
    protected static Class<?> getClass(String className) {
     	if (className.equals("int")) {
            return int.class;
        } 
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
        return null;
    }

	/**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "FormTag name:" + getName();
    }
}
