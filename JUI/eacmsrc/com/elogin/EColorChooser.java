/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: EColorChooser.java,v $
 * Revision 1.2  2008/01/30 16:27:01  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:42:17  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:37:37  tony
 * This is the initial load of OPICM
 *
 * Revision 1.5  2005/09/08 17:58:52  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.4  2005/02/04 16:57:40  tony
 * JTest Formatter Third Pass
 *
 * Revision 1.3  2005/02/02 21:30:08  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 23:18:17  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:24  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2003/04/22 16:37:04  tony
 * created MWChooser to update handling of default
 * middlewareLocation.
 *
 * Revision 1.3  2003/04/21 17:30:18  tony
 * updated Color Logic by adding edit and found color
 * preferences to appearance.
 *
 * Revision 1.2  2003/04/11 20:02:24  tony
 * added copyright statements.
 *
 */
package com.elogin;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class EColorChooser extends JColorChooser {
	private static final long serialVersionUID = 1L;
	private Tracker ok = null;
	private JDialog dialog = null;
	private Component c = null;
	private String title = null;
	private Color initialColor = null;

	/**
     * eColorChooser
     * @param _c
     * @param _title
     * @author Anthony C. Liberto
     */
    public EColorChooser(Component _c,String _title) {
		super();
		c = _c;
		title = new String(_title);
		return;
	}

	/**
     * getColor
     * @param _color
     * @return
     * @author Anthony C. Liberto
     */
    public Color getColor(Color _color) {
		Color color = null;
        initialColor = _color;
		setColor(_color);
		if (ok == null) {
			ok = new Tracker(this);
			dialog = createDialog(c, title, true, this, ok, null);
			dialog.addWindowListener(new Closer());
			dialog.addComponentListener(new DisposeOnClose());
		}
		ok.setColor(_color);
		dialog.show();
		color = ok.getColor();
		return color;
	}

    /**
     * reset
     * @author Anthony C. Liberto
     */
    public void reset() {
        setColor(initialColor);
        return;
    }

	private class Closer extends WindowAdapter {
		/**
         * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
         * @author Anthony C. Liberto
         */
        public void windowClosing(WindowEvent _we) {
			dialog.hide();
			return;
		}
	}

	private class DisposeOnClose extends ComponentAdapter {
		/**
         * @see java.awt.event.ComponentListener#componentHidden(java.awt.event.ComponentEvent)
         * @author Anthony C. Liberto
         */
        public void componentHidden(ComponentEvent _ce) {
			dialog.dispose();
			return;
		}
	}

	private class Tracker implements ActionListener {
		private Color color = null;
		private EColorChooser parent = null;

		/**
         * tracker
         * @param _parent
         * @author Anthony C. Liberto
         */
        private Tracker(EColorChooser _parent) {
			parent = _parent;
			return;
		}

		/**
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         * @author Anthony C. Liberto
         */
        public void actionPerformed(ActionEvent _ae) {
			String action = _ae.getActionCommand();
			if (action.equals("OK")) {
				color = parent.getColor();
			}
			return;
		}

		/**
         * setColor
         * @param _c
         * @author Anthony C. Liberto
         */
        public void setColor(Color _c) {
			color = _c;
			return;
		}

		/**
         * getColor
         * @return
         * @author Anthony C. Liberto
         */
        public Color getColor() {
			return color;
		}
	}

}
