//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.navform;


/**
 * this holds data for the variable tag in navigate layout xml
<variable name="int0" class = "int" value="0"/>
<variable name="int1" class = "int" value="1"/>
@author Wendy Stimpson
 */
// $Log: FormVariable.java,v $
// Revision 1.1  2012/09/27 19:39:25  wendy
// Initial code
//
public class FormVariable extends FormTag
{
    private Class<?> cls = null;
    private Object varObject = null;;
    private String value = null;

	/* (non-Javadoc)
	 * release memory
	 * @see com.ibm.eacm.navform.FormTag#dereference()
	 */
	protected void dereference(){
		super.dereference();
		value = null;
		cls = null;
		varObject = null;
	}

    /**
     * setValue
     * @param s
     */
    protected void setValue(String s) {
        value = s;
    }

    private Object generateWrapper() {
        if (cls.getName().equals("int")) {
            return new Integer(value);
        } 
        return null;
    }

    /**
     * setClass
     * @param className
     */
    protected void setClass(String className) {
        cls = getClass(className);
    }
    
    /**
     * getObject
     * @return
     */
    protected Object getObject() {
        if (varObject == null) {
        	varObject = createObject();
        }
        return varObject;
    }
    /**
     * create Object for the class
     * @return
     */
	private Object createObject() {
		try {
			if (cls != null){
				if (cls.isPrimitive()){
					if (value == null) {
						return null;
					}else{
						return generateWrapper();
					}
				} 

				return cls.newInstance();
			}
		} catch (Exception ie) {
            ie.printStackTrace();
        } 
        return null;
    }
    
    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return super.toString()+" formVariable value: " + value;
    }
}
