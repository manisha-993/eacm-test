/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: EDateLabel.java,v $
 * Revision 1.3  2009/06/09 19:39:06  wendy
 * BH SR-14 date warnings
 *
 * Revision 1.2  2008/01/30 16:26:56  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:45:47  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:17  tony
 * JTest Modifications
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
 * Revision 1.4  2004/01/14 21:55:42  tony
 * acl_20040114
 * added hand cursor logic.
 *
 * Revision 1.3  2003/09/10 00:09:40  tony
 * updated to save memory.
 *
 * Revision 1.2  2003/03/07 21:40:49  tony
 * Accessibility update
 *
 * Revision 1.1.1.1  2003/03/03 18:03:52  tony
 * This is the initial load of OPICM
 *
 * Revision 1.2  2002/11/07 16:58:15  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eobjects;
import com.elogin.*;
import javax.swing.border.*;
import java.awt.*;

import javax.swing.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class EDateLabel extends ELabel {
	private static final long serialVersionUID = 1L;
	private boolean blnCurrent = false;
	private String action = null;

	private static final Border NORMAL =  new EmptyBorder(2,2,2,2);
	private static final Border LOWERED = UIManager.getBorder("eannounce.loweredBorder");

	/**
     * eDateLabel
     * @author Anthony C. Liberto
     */
    public EDateLabel() {
		init();
	}

	/**
     * eDateLabel
     * @param _string
     * @author Anthony C. Liberto
     */
    public EDateLabel(String _string) {
		super(_string);
		init();
	}

	/**
     * setActionCommand
     * @param _s
     * @author Anthony C. Liberto
     */
    public void setActionCommand(String _s) {
		action = _s;
	}

	/**
     * getActionCommand
     * @return
     * @author Anthony C. Liberto
     */
    public String getActionCommand() {
		return action;
	}

	/**
     * isCurrent
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isCurrent() {
		return blnCurrent;
	}

	/**
     * setCurrent
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setCurrent(boolean _b) {
		blnCurrent = _b;
		if (_b) {
			setForeground(Color.blue);
			setBorder(LOWERED);
		} else {
			setForeground(Color.black);
			setBorder(NORMAL);
		}
	}
    /**
     * BH SR-14
     * Draw warning days in red
     */
    public void setWarning() {
    	setForeground(Color.red);
    	setBorder(NORMAL);
	}

	private void init() {
		setForeground(Color.black);
	}

    /**
     * dereference
     *
     * @author Anthony C. Liberto
     */
    public void dereference() {
    	action=null;
		removeAll();
		removeNotify();
	}

/*
 acl_20040113
 */
    /**
     * getCursor
     *
     * @return
     * @author Anthony C. Liberto
     */
    public Cursor getCursor() {
		if (eaccess().isBusy() || eaccess().isModalBusy()) {
			return eaccess().getModalCursor();
		}
		return Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
	}
}
