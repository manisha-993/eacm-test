//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
package com.ibm.eacm.navform;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * this has data for the method tag in the navigate layout xml
 * 
<method name="navigateBuild" method="build" object="navigate0"/>
<method name="getData" method="getData" Object="navigate0"/>
<method name="getHist" method="getHistory" Object="navigate0"/>
<method name="getActionTree" method="getActionTree" Object="navigate0"/>
<method name="getSelector" method ="getSelector" Object="navigate0"/>
<method name="navigateBuild0" parms ="int,int,int,int" args="int0,int1,int0,int1" method="build" object="navigate0"/>
<method name="navigateBuild1" parms ="int,int,int,int" args="int0,int1,int0,int1" method="build" object="navigate1"/>
<method name= "getParentSplit" method="getSplit" parms = "int" args="int1" object="parent"/>
 
 * @author Wendy Stimpson
 */
//$Log: FormMethod.java,v $
//Revision 1.2  2013/07/18 18:28:40  wendy
//fix compiler warnings
//
//Revision 1.1  2012/09/27 19:39:25  wendy
//Initial code
//
public class FormMethod extends FormTag
{
    private String method = null;
    private Class<?>[] parms = null; //parm type
    private Object[] args = null; //actual passed in parms
    private Object object = null; //object that method is to be invoked on
    
	/* (non-Javadoc)
	 * @see com.ibm.eacm.navform.FormTag#dereference()
	 */
	protected void dereference(){
		super.dereference();
		method = null;
		object = null;
		parms = null;
		args = null;
	}

    /**
     * setMethod
     * @param s
     */
    protected void setMethod(String s) {
        method = s;
    }

    /**
     * getMethod
     * @return
     */
    private Method getMethod() {
        Class<?> cls = null;
        if (method == null) {
            return null;
        }
        if (object != null) {
            cls = object.getClass();
        }
        try {
            if (cls != null) {
                return cls.getMethod(method, parms); //will return superClass methods.
                //return cls.getDeclaredMethod(method,parms);			//will not return superClass methods.
            }
        } catch (NoSuchMethodException nsme) {
        	com.ibm.eacm.ui.UI.showException(null,nsme);
        }
        return null;
    }


    /**
     * setParms
     * @param s
     */
    protected void setParms(String[] s) {
        parms = new Class[s.length];
        for (int i = 0; i < s.length; ++i) {
            parms[i] = getClass(s[i]);
        }
    }
    
    /**
     * setArgs
     * @param o
     */
    protected void setArgs(Object[] o) {
        args = o;
    }

    private Object[] getComputedArgs() {
        if (args == null) {
            return new Object[0];
        }
      
        Object[] out = new Object[args.length];
        for (int i = 0; i < args.length; ++i) {
            if (args[i] instanceof FormMethod) {
                out[i] = ((FormMethod) args[i]).invoke();
            } else {
                out[i] = args[i];
            }
        }
        return out;
    }

    /**
     * setObject
     * @param o
     */
    protected void setObject(Object o) {
        if (o instanceof String) {
            object = createObject((String) o);
        } else if (o instanceof FormItem) {
            object = ((FormItem) o).getObject();
        } else {
            object = o;
        }
    }

    /**
     * createObject
     * @param s
     * @return
     */
    private Object createObject(String s) {
        Class<?> cls = getClass(s);
     
        try {
        	if (cls!=null){
        		return cls.newInstance();
        	}
        } catch (Exception ie) {
        	com.ibm.eacm.ui.UI.showException(null,ie);
        } 
        return null;
    }

    /**
     * invoke
     * @return
     */
    protected Object invoke() {
        try {
        	Method mth = getMethod();
        	if (mth!=null){
        	    return mth.invoke(object, getComputedArgs());
        	}
        } catch(InvocationTargetException ite){
            Throwable t = ite.getCause();
            if (t!=null){
                com.ibm.eacm.ui.UI.showException(null,t);
            }else{
            	com.ibm.eacm.ui.UI.showException(null,ite);
            }
        } catch (Exception iae) {
            com.ibm.eacm.ui.UI.showException(null,iae);
        } 
        return null;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return super.toString()+" formMethod method: " + method;
    }
}
