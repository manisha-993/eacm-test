/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: EPropertyField.java,v $
 * Revision 1.2  2008/01/30 16:26:56  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:45:47  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:17  tony
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
 * Revision 1.6  2003/08/27 19:08:49  tony
 * updated generic search to improve functionality.
 *
 * Revision 1.5  2003/04/30 21:43:52  tony
 * updated color selction capabilities on textComponents.
 *
 * Revision 1.4  2003/04/11 20:02:32  tony
 * added copyright statements.
 *
 */
package com.ibm.eannounce.eobjects;
import com.elogin.*;
import java.awt.*;
import java.beans.*;
import javax.swing.text.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class EPropertyField extends ETextField implements EAccessConstants {
	private static final long serialVersionUID = 1L;
	private String status = null;
	private int type = -1;
	private int maxL = -1;							//50353
	private String parm = null;

	private boolean notifyAlways = false;			//19939

	/**
     * ePropertyField
     * @param col
     * @param _pcl
     * @param _type
     * @param _parm
     * @param _initStatus
     * @author Anthony C. Liberto
     */
    public EPropertyField(int col, PropertyChangeListener _pcl, int _type, String _parm, String _initStatus) {
		super(col);
		setType(_type);
		setParameter(_parm);
		status = _initStatus;
		addPropertyChangeListener(_pcl);
		return;
	}

	/**
     * setNotifyAlways
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setNotifyAlways(boolean _b) {		//19939
		notifyAlways = _b;							//19939
	}												//19939

	/**
     * setType
     * @param _i
     * @author Anthony C. Liberto
     */
    public void setType(int _i) {
		type = _i;
	}

	/**
     * getType
     * @return
     * @author Anthony C. Liberto
     */
    public int getType() {
		return type;
	}

	/**
     * setParameter
     * @param _s
     * @author Anthony C. Liberto
     */
    public void setParameter(String _s) {
		parm = new String(_s);
	}

	/**
     * getParameter
     * @return
     * @author Anthony C. Liberto
     */
    public String getParameter() {
		return parm;
	}

	/**
     * getParameterInt
     * @return
     * @author Anthony C. Liberto
     */
    public int getParameterInt() {
		try {
			return Integer.valueOf(parm).intValue();
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		return -1;
	}

	private boolean statusEquals(String _s) {
		if (status == null) {
            return false;
		}
		return status.equals(_s);
	}

	private void setStatus(String _s) {
		if (statusEquals(_s)) {
			if (notifyAlways) {
				firePropertyChange(_s,null,null);
			}
			return;
		}
		firePropertyChange(_s,null,null);
		status = _s;
		return;
	}

	/**
     * getStatus
     * @return
     * @author Anthony C. Liberto
     */
    public String getStatus() {
		return status;
	}

	/**
     * isActive
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isActive() {
		return statusEquals(ACTIVATE);
	}

	private void evaluate() {
		evaluate(getText());
	}

	private void evaluate(String _s) {
		int length = _s.length();
		if (type == MINIMUM_TYPE) {
			int i = getParameterInt();
			if (i < 0) {
                return;
			}
			if (length <= i) {
				setStatus(DEACTIVATE);
			} else if (length > i ) {
				setStatus(ACTIVATE);
			}
		} else if (type == MINIMUM_EQUAL_TYPE) {
			int i = getParameterInt();
			if (i < 0) {
                return;
			}
			if (length < i) {
				setStatus(DEACTIVATE);
			} else if (length >= i ) {
				setStatus(ACTIVATE);
			}
		} else if (type == MAXIMUM_TYPE) {
			int i = getParameterInt();
			if (i < 0) {
                return;
			}
			if (length < i) {
				setStatus(ACTIVATE);
			} else if (length >= i ) {
				setStatus(DEACTIVATE);
			}
		} else if (type == MAXIMUM_EQUAL_TYPE) {
			int i = getParameterInt();
			if (i < 0) {
                return;
			}
			if (length <= i) {
				setStatus(ACTIVATE);
			} else if (length > i) {
				setStatus(DEACTIVATE);
			}
		}
	}

	/**
     * @see javax.swing.JTextField#createDefaultModel()
     * @author Anthony C. Liberto
     */
    protected Document createDefaultModel() {
		return new MaxDoc();
	}

	private class MaxDoc extends PlainDocument {
		private static final long serialVersionUID = 1L;
		public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
			if (str == null) {
                return;
			}
			if (maxL < 0 || (getLength() + str.length()) <= maxL) {					//50353
				super.insertString(offs, str, a);
				evaluate();
			} else {																//50353
				reportMessage("");													//50353
			}																		//50353
			return;
		}

		public void remove(int offs, int len) throws BadLocationException {
			super.remove(offs,len);
			evaluate();
			return;
		}
	}
/*
 50353
*/
	/**
     * setMaximumLength
     * @param _i
     * @author Anthony C. Liberto
     */
    public void setMaximumLength(int _i) {
		maxL = _i;
		return;
	}

	/**
     * getMaximumLength
     * @return
     * @author Anthony C. Liberto
     */
    public int getMaximumLength() {
		return maxL;
	}

	/**
     * reportMessage
     * @param _s
     * @author Anthony C. Liberto
     */
    public void reportMessage(String _s) {
		Toolkit.getDefaultToolkit().beep();
		return;
	}
}

