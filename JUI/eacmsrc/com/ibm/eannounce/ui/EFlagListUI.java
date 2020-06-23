/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: EFlagListUI.java,v $
 * Revision 1.1  2007/04/18 19:47:20  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:56  tony
 * This is the initial load of OPICM
 *
 * Revision 1.5  2005/09/08 17:58:59  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.4  2005/02/03 21:26:12  tony
 * JTest Format Third Pass
 *
 * Revision 1.3  2005/01/28 00:10:28  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/25 22:55:34  tony
 * Jtest Modifications
 *
 * Revision 1.1.1.1  2004/02/10 17:00:28  tony
 * This is the initial load of OPICM
 *
 * Revision 1.1.1.1  2003/03/03 18:04:02  tony
 * This is the initial load of OPICM
 *
 * Revision 1.2  2002/11/07 16:58:11  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.ui;
import com.ibm.eannounce.eobjects.EFlagList;
import javax.swing.event.*;
import java.awt.event.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.BasicListUI;
import javax.swing.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class EFlagListUI extends BasicListUI {
    KeyListener keyListen = null;

    /**
     * Constructor
     *
     * @author Anthony C. Liberto
     */
    public EFlagListUI() {
        super();
        return;
    }

    /**
     * Constructor
     *
     * @param _fl
     * @author Anthony C. Liberto
     */
 //   public eFlagListUI(eFlagList _fl) {
 //       this();
 //       return;
 //   }

    /**
     * create Component UI
     *
     * @param list
     * @return
     * @author Anthony C. Liberto
     */
    public static ComponentUI createUI(JComponent list) {
        return new EFlagListUI();
    }

    /**
     * @see javax.swing.plaf.basic.BasicListUI#createMouseInputListener()
     * @author Anthony C. Liberto
     */
    protected MouseInputListener createMouseInputListener() {
        return new MyMouseHandler();
    }

    /**
     * To change the template for this generated type comment go to
     * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
     *
     * @author Anthony C. Liberto
     */
    protected class MyMouseHandler implements MouseInputListener {
        /**
         * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
         * @author Anthony C. Liberto
         */
        public void mousePressed(MouseEvent _me) {
            int row = -1;
            if (SwingUtilities.isLeftMouseButton(_me)) {
                if (!list.isEnabled()) {
                    return;
                }
                if (!list.hasFocus()) {
                    list.requestFocus();
                }
                list.setValueIsAdjusting(true);
                row = convertYToRow(_me.getY());
                if (row != -1) {
                    ((EFlagList) list).toggle(row);
                }
            }
            return;
        }
        /**
         * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
         * @author Anthony C. Liberto
         */
        public void mouseReleased(MouseEvent _me) {
            if (SwingUtilities.isLeftMouseButton(_me)) {
                list.setValueIsAdjusting(false);
            }
            return;
        }
        /**
         * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
         * @author Anthony C. Liberto
         */
        public void mouseDragged(MouseEvent e) {
        }
        /**
         * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
         * @author Anthony C. Liberto
         */
        public void mouseClicked(MouseEvent _me) {
        }
        /**
         * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
         * @author Anthony C. Liberto
         */
        public void mouseEntered(MouseEvent e) {
        }
        /**
         * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
         * @author Anthony C. Liberto
         */
        public void mouseExited(MouseEvent e) {
        }
        /**
         * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
         * @author Anthony C. Liberto
         */
        public void mouseMoved(MouseEvent e) {
        }
    }

}
