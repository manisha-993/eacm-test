/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: FormVariable.java,v $
 * Revision 1.2  2008/01/30 16:27:06  wendy
 * Cleanup RSA warnings
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
 * Revision 1.5  2002/11/07 16:58:23  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms;
import com.ibm.eannounce.eobjects.*;
import java.util.Vector;
import java.lang.reflect.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class FormVariable extends EObject {
    private static Class cls = null;
    private Object o = null;
    private Class[] parms = null;
    private Object[] args = null;
    private String name = null;
    private String value = null;

    /**
     * formVariable
     * @author Anthony C. Liberto
     */
    public FormVariable() {
        return;
    }

   /* private FormVariable(String _name, String _className) {
        setName(_name);
        o = createObject(_className);
    }*/

    /**
     * createObject
     * @param _className
     * @return
     * @author Anthony C. Liberto
     */
    public Object createObject(String _className) {
        cls = getClass(_className);
        return createObject();
    }

    /**
     * createObject
     * @return
     * @author Anthony C. Liberto
     */
    public Object createObject() {
        try {
            if (cls.isPrimitive() && value == null) {
                return null;

            } else if (cls.isPrimitive() && value != null) {
                return generateWrapper();
            }
            if (parms == null) {
                return cls.newInstance();

            } else {
                return createWithConstructor();
            }
        } catch (InstantiationException ie) {
            ie.printStackTrace();
        } catch (IllegalAccessException iae) {
            iae.printStackTrace();
        }
        return null;
    }

    private Object createWithConstructor() {
        try {
            Constructor construct = cls.getConstructor(parms);
            return construct.newInstance(getComputedArgs());
        } catch (NoSuchMethodException nsme) {
            nsme.printStackTrace();
        } catch (SecurityException se) {
            se.printStackTrace();
        } catch (InstantiationException ie) {
            ie.printStackTrace();
        } catch (IllegalAccessException iae) {
            iae.printStackTrace();
        } catch (IllegalArgumentException iae) {
            iae.printStackTrace();
        } catch (InvocationTargetException ite) {
            ite.printStackTrace();
        }
        return null;
    }

    /**
     * setValue
     * @param _s
     * @author Anthony C. Liberto
     */
    public void setValue(String _s) {
        value = new String(_s);
        if (cls != null && cls.isPrimitive()) {
            o = generateWrapper();
        }
    }

    private Object generateWrapper() {
        if (cls.getName().equals("boolean")) {
            return new Boolean(value);

        } else if (cls.getName().equals("byte")) {
            return new Byte(value);

        } else if (cls.getName().equals("char")) {
            return new Character(value.charAt(0));

        } else if (cls.getName().equals(" short")) {
            return new Short(value);

        } else if (cls.getName().equals("int")) {
            return new Integer(value);

        } else if (cls.getName().equals("long")) {
            return new Long(value);

        } else if (cls.getName().equals("float")) {
            return new Float(value);

        } else if (cls.getName().equals("double")) {
            return new Double(value);
        }
        return null;
    }

    /**
     * setClass
     * @param _className
     * @author Anthony C. Liberto
     */
    public void setClass(String _className) {
        cls = getClass(_className);
    }

    /**
     * getClass
     * @param _className
     * @return
     * @author Anthony C. Liberto
     */
    public Class getClass(String _className) {
        //try this if below doesn't work boolean.TYPE;
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
     * setName
     * @param _s
     * @author Anthony C. Liberto
     */
    public void setName(String _s) {
        name = new String(_s);
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
     * setObject
     * @param _s
     * @author Anthony C. Liberto
     */
    public void setObject(String _s) {
        o = createObject(_s);
    }

    /**
     * getObject
     * @return
     * @author Anthony C. Liberto
     */
    public Object getObject() {
        if (o == null) {
            o = createObject();
        }
        return o;
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
     *
     * @author Anthony C. Liberto
     * @param _s
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
     * getClassParms
     * @return
     * @author Anthony C. Liberto
     */
    public Class[] getClassParms() {
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
     * set
     *
     * @param attribute
     * @author Anthony C. Liberto
     * @param _value
     */
    public void set(String attribute, Object[] _value) {
        if (o == null) {
            return;
        }
        if (o instanceof String) {
            if (attribute.equalsIgnoreCase("TEXT")) {
                o = new String((String) _value[0]);
            } else {
                appendLog("formVariable.set(" + attribute + ") is undefined for String.");
            }
        } else if (o instanceof Vector) {
            if (attribute.equalsIgnoreCase("ADD")) {
                int ii = _value.length;
                for (int i = 0; i < ii; ++i) {
                    ((Vector) o).add(_value[i]);
                }
            } else {
                appendLog("formVariable.set(" + attribute + ") is undefined for Vector.");
            }
        } else {
            appendLog("undefined class: " + o.getClass().getName() + ") formVariable.set(" + attribute + ").");
        }
        return;
    }
}
