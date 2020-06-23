/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: EInformationPanel.java,v $
 * Revision 1.2  2008/01/30 16:27:00  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:42:17  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:09  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:37  tony
 * This is the initial load of OPICM
 *
 * Revision 1.5  2005/09/08 17:58:52  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.4  2005/02/03 16:38:51  tony
 * JTest Second Pass Wrapup
 *
 * Revision 1.3  2005/02/02 21:30:07  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 23:18:16  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:24  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2003/10/08 20:09:19  tony
 * 52476
 *
 * Revision 1.3  2003/04/11 20:02:25  tony
 * added copyright statements.
 *
 */
package com.elogin;
import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.UIManager;
import javax.accessibility.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class EInformationPanel extends EPanel implements Accessible {
	private static final long serialVersionUID = 1L;
	/**
     * FILTER
     */
    public static final int FILTER		= 0;
	/**
     * WAY_BACK
     */
    public static final int WAY_BACK	= 2;
	/**
     * HIDDEN
     */
    public static final int HIDDEN		= 1;
	private EStatusbar status = null;

	private ELabel filter = new ELabel(eaccess().getImageIcon("fltr.gif")) {				//acl_20021119
		private static final long serialVersionUID = 1L;
		public String getToolTipText() {													//acl_20021119
			if (isEnabled()) {																//acl_20021119
				return super.getToolTipText() + " on";										//acl_20021119
			} else {																		//acl_20021119
				return super.getToolTipText() + " off";										//acl_20021119
			}																				//acl_20021119
		}																					//acl_20021119
	};																						//acl_20021119

	private ELabel wayBack = new ELabel(eaccess().getImageIcon("wayBack.gif")) {			//acl_20021119
		private static final long serialVersionUID = 1L;
		public String getToolTipText() {													//acl_20021119
			if (isEnabled()) {																//acl_20021119
				return super.getToolTipText() + " past/future";								//acl_20021119
			} else {																		//acl_20021119
				return super.getToolTipText() + " present";									//acl_20021119
			}																				//acl_20021119
		}																					//acl_20021119
	};																						//acl_20021119

	private ELabel lblHidden = new ELabel(eaccess().getImageIcon("hideCol.gif")) {			//52476
		private static final long serialVersionUID = 1L;
		public String getToolTipText() {													//52476
			if (isEnabled()) {																//52476
				return super.getToolTipText() + " exist";									//52476
			} else {																		//52476
				return "no " + super.getToolTipText();										//52476
			}																				//52476
		}																					//52476
	};																						//52476

	/**
     * eInformationPanel
     * @param _status
     * @author Anthony C. Liberto
     */
    public EInformationPanel(EStatusbar _status) {
		super(new GridLayout(1,3,3,3));
		status = _status;
		init();
		return;
	}

	private void init() {
		filter.setToolTipText(getString("fltrd"));
		wayBack.setToolTipText(getString("wayB"));
		lblHidden.setToolTipText(getString("hidden"));										//52476

		setEnabled(false,0);
		setEnabled(false,1);
		setEnabled(false,2);																//52476
		add(filter);
		add(lblHidden);																		//52476
		add(wayBack);

		setBorder(UIManager.getBorder("eannounce.etchedBorder"));
		return;
	}

	/**
     * setEnabled
     * @param b
     * @param i
     * @author Anthony C. Liberto
     */
    public void setEnabled(boolean b, int i) {
		if (i == FILTER) {								//asst
			filter.setEnabled(b);
		} else if (i == WAY_BACK) {
			wayBack.setEnabled(b);
		} else if (i == HIDDEN) {															//52476
			lblHidden.setEnabled(b);														//52476
		}
		return;
	}

	/**
     * isEnabled
     * @param i
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isEnabled(int i) {
		if (i == FILTER) {								//asst
			return filter.isEnabled();
		} else if (i == WAY_BACK) {
			return wayBack.isEnabled();
		} else if (i == HIDDEN) {															//52476
			return lblHidden.isEnabled();													//52476
		}
		return false;
	}

	/**
     * @see java.awt.Container#getComponent(int)
     * @author Anthony C. Liberto
     */
    public Component getComponent(int i) {
		if (i == FILTER) {
			return filter;
		} else if (i == WAY_BACK) {
			return wayBack;
		} else if (i == HIDDEN) {															//52476
			return lblHidden;																//52476
		}
		return null;
	}

	/**
     * getString
     * @author Anthony C. Liberto
     * @return String
     * @param _code
     */
    public String getString(String _code) {
		return eaccess().getString(_code);
	}

	/**
     * getPanelType
     * @author Anthony C. Liberto
     * @return String
     */
    public String getPanelType() {
		return TYPE_EINFORMATIONPANEL;
	}
}
