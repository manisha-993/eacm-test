/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: EVersionPanel.java,v $
 * Revision 1.2  2008/01/30 16:26:59  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:42:17  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:37:39  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:58:55  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/02/02 21:30:07  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 23:18:17  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:25  tony
 * This is the initial load of OPICM
 *
 * Revision 1.3  2003/04/11 20:02:27  tony
 * added copyright statements.
 *
 */
package com.elogin;
import java.awt.BorderLayout;
import javax.swing.border.Border;
import javax.swing.*;
import javax.accessibility.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class EVersionPanel extends EPanel implements Accessible {
	private static final long serialVersionUID = 1L;
	private BorderLayout layout = new BorderLayout(0,0);				//013108
	private EMLabel defLabel = new EMLabel();
	private JComponent dispComp = null;

	/**
     * eVersionPanel
     * @param _location
     * @author Anthony C. Liberto
     */
    public EVersionPanel(String _location) {
		super();
		Border bord = null;
        setLayout(layout);
		bord = UIManager.getBorder("eannounce.etchedBorder");
		setBorder(bord);
		add(_location, defLabel);
		return;
	}

	/**
     * addComponent
     * @param _s
     * @param _c
     * @author Anthony C. Liberto
     */
    public void addComponent(String _s, JComponent _c) {
		add(_s, _c);
		revalidate();
		return;
	}

	/**
     * setText
     * @param _ver
     * @author Anthony C. Liberto
     */
    public void setText(String _ver) {
		defLabel.setText(_ver);
		defLabel.revalidate();
		return;
	}

	/**
     * @see javax.swing.JComponent#setToolTipText(java.lang.String)
     * @author Anthony C. Liberto
     */
    public void setToolTipText(String _tip) {
		defLabel.setToolTipText(_tip);
		return;
	}

	/**
     * setComponent
     * @param _location
     * @param _jc
     * @author Anthony C. Liberto
     */
    public void setComponent(String _location, JComponent _jc) {
		if (_jc != null) {
			add(_location,_jc);
			dispComp = _jc;
		} else {
			if (dispComp != null) {
				remove(dispComp);
			}
		}
		return;
	}

	/**
     * remove
     * @author Anthony C. Liberto
     */
    public void remove() {
		dispComp = null;
		removeAll();
		revalidate();
		defLabel.removeAll();
		defLabel.removeNotify();
		defLabel = null;
		return;
	}
}
