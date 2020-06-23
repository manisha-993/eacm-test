/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: FormEnabler.java,v $
 * Revision 1.1  2007/04/18 19:43:40  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:37:59  tony
 * This is the initial load of OPICM
 *
 * Revision 1.5  2005/09/08 17:59:01  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.4  2005/02/04 15:22:08  tony
 * JTest Format Third Pass
 *
 * Revision 1.3  2005/02/01 22:06:31  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 19:15:14  tony
 * JTest Format
 *
 * Revision 1.1.1.1  2004/02/10 16:59:42  tony
 * This is the initial load of OPICM
 *
 * Revision 1.2  2003/10/29 00:21:17  tony
 * removed System.out. statements.
 *
 * Revision 1.1.1.1  2003/03/03 18:03:48  tony
 * This is the initial load of OPICM
 *
 * Revision 1.9  2002/11/11 22:55:37  tony
 * adjusted classification on the toggle
 *
 * Revision 1.8  2002/11/07 16:58:20  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms;
import javax.swing.JComponent;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class FormEnabler {
    private String name = null;
    private String equation = null;
    private String equationType = null;
    private String type = null;
    private Object[] components = null;
//    private Evaluator evaluator = new Evaluator();
    private FormMethod method = null;

    /**
     * formEnabler
     * @author Anthony C. Liberto
     */
    public FormEnabler() {
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
     * setEquation
     * @param _s
     * @author Anthony C. Liberto
     */
    public void setEquation(String _s) {
        equation = new String(_s);
    }

    /**
     * getEquation
     * @return
     * @author Anthony C. Liberto
     */
    public String getEquation() {
        return equation;
    }

    /**
     * setEquationType
     * @param _s
     * @author Anthony C. Liberto
     */
    public void setEquationType(String _s) {
        equationType = new String(_s);
    }

    /**
     * getEquationType
     * @return
     * @author Anthony C. Liberto
     */
    public String getEquationType() {
        return equationType;
    }

    /**
     * setType
     * @param _s
     * @author Anthony C. Liberto
     */
    public void setType(String _s) {
        type = new String(_s);
    }

    /**
     * getType
     * @return
     * @author Anthony C. Liberto
     */
    public String getType() {
        return type;
    }

    /**
     * setComponents
     * @param _o
     * @author Anthony C. Liberto
     */
    public void setComponents(Object[] _o) {
        components = _o;
    }

    /**
     * getComponents
     * @return
     * @author Anthony C. Liberto
     */
    public Object[] getComponents() {
        return components;
    }

    /**
     * setMethod
     * @param _method
     * @author Anthony C. Liberto
     */
    public void setMethod(FormMethod _method) {
        method = _method;
    }

    /**
     * getMethod
     * @return
     * @author Anthony C. Liberto
     */
    public FormMethod getMethod() {
        return method;
    }
    /* deprecated
    	public void invoke(PDHItem item) {
    /*		if (equationType.equalsIgnoreCase("method")) {
    			if (method != null) invoke(method.invoke());
    		} else if (equationType.equalsIgnoreCase("evalutator")) {
    			if (item != null) {
    				try {
    					invoke(evaluator.process(item, getEquation()));
    				} catch (evaluatorException _ee) {
    					_ee.printStackTrace();
    				}
    			}
    		}
    		return;
    	}
    */

    /**
     * invoke
     *
     * @author Anthony C. Liberto
     * @param _o
     */
    protected void invoke(Object _o) {
        if (_o instanceof Boolean) {
            invoke(((Boolean) _o).booleanValue());
        }
        return;
    }

    private void invoke(boolean _b) {
        if (type == null) {
            return;
        }
        if (type.equalsIgnoreCase("show")) {
            processVisible(_b);
        } else if (type.equalsIgnoreCase("hide")) {
            processVisible(!_b);
        } else if (type.equalsIgnoreCase("enable")) {
            processEnable(_b);
        } else if (type.equalsIgnoreCase("disable")) {
            processEnable(!_b);
        }
        return;
    }

    private JComponent getComponent(FormItem fi) {
        Object o = fi.getObject();
        if (o instanceof JComponent) {
            return (JComponent) o;
        }
        return null;
    }

    private void processVisible(boolean _b) {
        int ii = -1;
        if (components == null) {
            return;
        }
        ii = components.length;
        for (int i = 0; i < ii; ++i) {
            if (components[i] instanceof FormItem) {
                JComponent comp = getComponent((FormItem) components[i]);
                if (comp != null) {
                    comp.setVisible(_b);
                }
            }
        }
        return;
    }

    private void processEnable(boolean _b) {
        int ii = -1;
        if (components == null) {
            return;
        }
        ii = components.length;
        for (int i = 0; i < ii; ++i) {
            if (components[i] instanceof FormItem) {
                JComponent comp = getComponent((FormItem) components[i]);
                if (comp != null) {
                    comp.setEnabled(_b);
                }
            }
        }
        return;
    }
}
