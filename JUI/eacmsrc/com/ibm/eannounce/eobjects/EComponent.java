/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: EComponent.java,v $
 * Revision 1.1  2007/04/18 19:45:47  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:16  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:59:09  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/01/28 19:36:59  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/26 18:20:08  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:49  tony
 * This is the initial load of OPICM
 *
 * Revision 1.2  2003/04/11 20:02:32  tony
 * added copyright statements.
 *
 */
package com.ibm.eannounce.eobjects;
import java.awt.*;
import java.lang.reflect.*;
//import java.util.*;
import javax.swing.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class EComponent extends EObject {
//	private Vector m_vctMethod = null;
	private Class m_class = null;
	private Object m_component = null;
	private String m_strKey = null;

	/**
     * eComponent
     * @param _key
     * @author Anthony C. Liberto
     */
    public EComponent(String _key) {
		super();
		setKey(_key);
		return;
	}

	private void setKey(String _key) {
		if (_key != null) {
			m_strKey = new String(_key);
		}
		return;
	}

	/**
     * getKey
     * @return
     * @author Anthony C. Liberto
     */
    public String getKey() {
		return m_strKey;
	}

	/**
     * construct
     * @param _class
     * @author Anthony C. Liberto
     */
    public void construct(String _class) {
		try {
			m_class = Class.forName(_class);
		} catch (ClassNotFoundException _cnf) {
			_cnf.printStackTrace();
		}
		if (m_class != null) {
			try {
				m_component = m_class.newInstance();
			} catch (InstantiationException _ie) {
				_ie.printStackTrace();
			} catch (IllegalAccessException _iae) {
				_iae.printStackTrace();
			}
		}
		return;
	}

	/**
     * construct
     * @param _class
     * @param _parms
     * @param _args
     * @author Anthony C. Liberto
     */
    public void construct(String _class, Class[] _parms, Object[] _args) {
		try {
			m_class = Class.forName(_class);
		} catch (ClassNotFoundException _cnf) {
			_cnf.printStackTrace();
		}
		if (m_class != null) {
			try {
				Constructor construct = m_class.getConstructor(_parms);
				if (construct !=  null) {
					try {
						m_component = construct.newInstance(_args);
					} catch (InstantiationException _ie) {
						_ie.printStackTrace();
					} catch (IllegalAccessException _iae) {
						_iae.printStackTrace();
					} catch (IllegalArgumentException _iae) {
						_iae.printStackTrace();
					} catch (InvocationTargetException _ite) {
						_ite.printStackTrace();
					}
				}
			} catch (NoSuchMethodException _nsm) {
				_nsm.printStackTrace();
			} catch (SecurityException _se) {
				_se.printStackTrace();
			}
		}
		return;
	}

	/**
     * isInstanceOf
     * @param _class
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isInstanceOf(Class _class) {
		return (java.beans.Beans.isInstanceOf(m_component, _class));
	}

	/**
     * getMethod
     * @param _name
     * @param _parms
     * @return
     * @author Anthony C. Liberto
     */
    public Method getMethod(String _name, Class[] _parms) {
		if (m_class != null) {
			try {
				return m_class.getMethod(_name,_parms);
			} catch (NoSuchMethodException _nsm) {
				_nsm.printStackTrace();
			} catch (SecurityException _se) {
				_se.printStackTrace();
			}
		}
		return null;
	}

	/**
     * invoke
     * @param _name
     * @param _parms
     * @param _args
     * @return
     * @author Anthony C. Liberto
     */
    public Object invoke(String _name, Class[] _parms, Object[] _args) {
		Method mthd = getMethod(_name,_parms);
		if (mthd != null) {
			try {
				return mthd.invoke(this,_args);
			} catch (IllegalAccessException _iae) {
				_iae.printStackTrace();
			} catch (IllegalArgumentException _iae) {
				_iae.printStackTrace();
			} catch (InvocationTargetException _ite) {
				_ite.printStackTrace();
			}
		}
		return null;
	}

	/**
     * getComponent
     * @return
     * @author Anthony C. Liberto
     */
    public Component getComponent() {
		if (m_component instanceof Component) {
			return (Component)m_component;
		}
		return null;
	}

	/**
     * getJComponent
     * @return
     * @author Anthony C. Liberto
     */
    public JComponent getJComponent() {
		if (m_component instanceof JComponent) {
			return (JComponent)m_component;
		}
		return null;
	}

	/**
     * getComponentClass
     * @return
     * @author Anthony C. Liberto
     */
    public Class getComponentClass() {
		return m_class;
	}

}
