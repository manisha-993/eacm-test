/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: FormMethod.java,v $
 * Revision 1.2  2010/08/10 19:43:01  wendy
 * more debug info
 *
 * Revision 1.1  2007/04/18 19:43:40  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:37:59  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:59:01  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/02/01 22:06:31  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 19:15:15  tony
 * JTest Format
 *
 * Revision 1.1.1.1  2004/02/10 16:59:42  tony
 * This is the initial load of OPICM
 *
 * Revision 1.1.1.1  2003/03/03 18:03:48  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2002/11/07 16:58:22  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Array;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class FormMethod {
    private String name = null; //ref name
    private String method = null;
    private Class[] parms = null; //parm type
    private Object[] args = null; //actual passed in parms
    private Object object = null; //object that method is to be invoked on
    private Object output = null; //output from the method

    /**
     * formMethod
     * @author Anthony C. Liberto
     */
    public FormMethod() {
        return;
    }

    /**
     * isEmpty
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isEmpty() {
        if (name == null || object == null) {
            return true;
        }
        return false;
    }

    /**
     * setName
     * @param s
     * @author Anthony C. Liberto
     */
    public void setName(String s) {
        name = new String(s);
        return;
    }

    /**
     * getName
     * @return
     * @author Anthony C. Liberto
     */
    public String getName() {
        return name;
    }

    /**
     * setMethod
     * @param _s
     * @author Anthony C. Liberto
     */
    public void setMethod(String _s) {
        method = new String(_s);
    }

    /**
     * getMethod
     * @return
     * @author Anthony C. Liberto
     */
    public Method getMethod() {
        Class cls = null;
        if (method == null) {
            return null;
        }
        if (object != null) {
            cls = object.getClass();
        }
        try {
            if (cls != null) {
                return cls.getMethod(method, parms); //will return superClass methods.
                //				return cls.getDeclaredMethod(method,parms);			//will not return superClass methods.
            }
        } catch (NoSuchMethodException nsme) {
            nsme.printStackTrace();
        }
        return null;
    }

    /**
     * setParms
     * @param c
     * @author Anthony C. Liberto
     */
    public void setParms(Class[] c) {
        parms = c;
    }

    /**
     * setParms
     * @param _s
     * @author Anthony C. Liberto
     */
    public void setParms(String[] _s) {
        int ii = _s.length;
        parms = new Class[ii];
        for (int i = 0; i < ii; ++i) {
            parms[i] = getClass(_s[i]);
        }
        return;
    }

    /**
     * getClass
     * @param _className
     * @return
     * @author Anthony C. Liberto
     */
    public static Class getClass(String _className) {
        if (_className.endsWith("[]")) {
            return getArray(_className);
        }
        if (_className.equals("boolean")) {
            return boolean.class;

        } else if (_className.equals("byte")) {
            return byte.class;

        } else if (_className.equals("char")) {
            return char.class;

        } else if (_className.equals("short")) {
            return short.class;

        } else if (_className.equals("int")) {
            return int.class;

        } else if (_className.equals("long")) {
            return long.class;

        } else if (_className.equals("float")) {
            return float.class;

        } else if (_className.equals("double")) {
            return double.class;
        }
        try {
            return Class.forName(_className);
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
        return null;
    }

    /**
     * getArray
     * @param _className
     * @return
     * @author Anthony C. Liberto
     */
    public static Class getArray(String _className) {
        int i = _className.length() - 2;
        String className = _className.substring(0, i);
        if (className.equals("boolean")) {
            return boolean[].class;

        } else if (className.equals("byte")) {
            return byte[].class;

        } else if (className.equals("char")) {
            return char[].class;

        } else if (className.equals("short")) {
            return short[].class;

        } else if (className.equals("int")) {
            return int[].class;

        } else if (className.equals("long")) {
            return long[].class;

        } else if (className.equals("float")) {
            return float[].class;

        } else if (className.equals("double")) {
            return double[].class;
        }
        try {
            Class cls = Class.forName(className);
            Object ra = Array.newInstance(cls, 0);
            return ra.getClass();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        } catch (NegativeArraySizeException nase) {
            nase.printStackTrace();
        }
        return null;
    }

    /**
     * getParms
     * @return
     * @author Anthony C. Liberto
     */
    public Class[] getParms() {
        return parms;
    }

    /**
     * setArgs
     * @param _o
     * @author Anthony C. Liberto
     */
    public void setArgs(Object[] _o) {
        args = _o;
    }

    /**
     * getArgs
     * @return
     * @author Anthony C. Liberto
     */
    public Object[] getArgs() {
        return args;
    }

    private Object[] getComputedArgs() {
        int ii = -1;
        Object[] out = null;
        if (args == null) {
            return new Object[0];
        }
        ii = args.length;
        out = new Object[ii];
        for (int i = 0; i < ii; ++i) {
            if (args[i] != null && args[i] instanceof FormMethod) {
                out[i] = ((FormMethod) args[i]).invoke();
            } else {
                out[i] = args[i];
            }
        }
        return out;
    }

    /**
     * setObject
     * @param _o
     * @author Anthony C. Liberto
     */
    public void setObject(Object _o) {
        if (_o instanceof String) {
            object = createObject((String) _o);

        } else if (_o instanceof FormItem) {
            object = ((FormItem) _o).getObject();

        } else {
            object = _o;
        }
        return;
    }

    /**
     * createObject
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    public Object createObject(String _s) {
        Class cls = getClass(_s);
        if (cls == null) {
            return null;
        }
        try {
            return cls.newInstance();
        } catch (InstantiationException ie) {
            ie.printStackTrace();
        } catch (IllegalAccessException iae) {
            iae.printStackTrace();
        }
        return null;
    }

    /**
     * getOutput
     * @return
     * @author Anthony C. Liberto
     */
    public Object getOutput() {
        return output;
    }

    /**
     * getObject
     * @return
     * @author Anthony C. Liberto
     */
    public Object getObject() {
        return object;
    }

    /**
     * invoke
     * @return
     * @author Anthony C. Liberto
     */
    public Object invoke() {
        output = masterInvoke();
        return output;
    }

    private Object masterInvoke() {
        Method mth = getMethod();
        if (mth == null) {
            return null;
        }
        try {
            return getMethod().invoke(object, getComputedArgs());
            //			return getMethod().invoke(object,args);
        } catch (IllegalAccessException iae) {
            iae.printStackTrace();
        } catch (IllegalArgumentException iae) {
            iae.printStackTrace();
        } catch (InvocationTargetException ite) {
            ite.printStackTrace();
            Throwable t = ite.getCause();
            if (t!=null){
            	t.printStackTrace();
            }
        }
        return null;
    }

    /**
     * isValid
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isValid() {
        if (name == null || parms == null || object == null || args == null) {
            return false;
        }
        return true;
    }

    /**
     * @see java.lang.Object#toString()
     * @author Anthony C. Liberto
     */
    public String toString() {
        return "formMethod name:" + name + ", method: " + method + ", output: " + output;
    }
}
