/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: EDatePanel.java,v $
 * Revision 1.3  2008/02/04 14:22:47  wendy
 * Cleanup more RSA warnings
 *
 * Revision 1.2  2008/01/30 16:27:00  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:42:17  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:37:37  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:58:52  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/02/02 21:30:07  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 23:18:18  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:24  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2003/09/05 17:31:42  tony
 * 2003-09-05 memory enhancements
 *
 * Revision 1.3  2003/04/11 20:02:25  tony
 * added copyright statements.
 *
 */
package com.elogin;
import java.awt.GridLayout;
import java.util.Date;
import javax.swing.*;
import javax.accessibility.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class EDatePanel extends EPanel implements Accessible {
	private static final long serialVersionUID = 1L;
	private Date d = null;
	private ETimeLabel time = null;
	private ELabel lbl = new ELabel();

	/**
     * eDatePanel
     * @author Anthony C. Liberto
     */
    public EDatePanel() {
		super(new GridLayout(1,1));
		setBorder(UIManager.getBorder("eannounce.etchedBorder"));
		add(lbl);
	}

	/**
     * setDate
     * @param in
     * @author Anthony C. Liberto
     */
    public void setDate(Date in) {
		d = in;
		lbl.setText("  " + toDate() + "  ");
		lbl.setToolTipText(toFullDate());									//acl_20021115
		repaint();
		return;
	}

	/**
     * setTimeLabel
     * @param _lbl
     * @author Anthony C. Liberto
     */
    public void setTimeLabel(ETimeLabel _lbl) {
		time = _lbl;
	}
    /**
     * fix rsa warning
     * @return
     */
    public ETimeLabel getTimeLabel() {
		return time;
	}
	/**
     * toDate
     * @return
     * @author Anthony C. Liberto
     */
    public String toDate() {
		if (d != null) {
			return eaccess().formatDate(FORMATTED_DATE,d);
		}
//			return sdf.format(d);
		return ("No Date is set");
	}

	/**
     * toFullDate
     * @return
     * @author Anthony C. Liberto
     */
    public String toFullDate() {											//acl_20021115
		if (d != null) {														//acl_20021115
			eaccess().formatDate(DISPLAY_DATE,d);
		}
//			return full.format(d);											//acl_20021115

		return null;														//acl_20021115
	}																		//acl_20021115
}

