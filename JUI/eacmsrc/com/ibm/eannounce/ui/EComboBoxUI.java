/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: EComboBoxUI.java,v $
 * Revision 1.3  2012/04/05 17:50:03  wendy
 * jre142 and win7 changes
 *
 * Revision 1.2  2008/01/30 16:27:04  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:47:20  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:56  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:58:58  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
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
 * Revision 1.9  2003/05/07 22:11:46  tony
 * 50567
 *
 * Revision 1.8  2003/04/11 20:02:27  tony
 * added copyright statements.
 *
 */
package com.ibm.eannounce.ui;
import com.elogin.*;
import com.ibm.eannounce.eobjects.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.plaf.basic.*;
import javax.swing.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class EComboBoxUI extends javax.swing.plaf.metal.MetalComboBoxUI
//WindowsComboBoxUI win7 cant use windowsui, arrow is lost
implements EAccessConstants {
    /** Automatically generated javadoc for: TOOLBAR_HEIGHT */
    private static final int TOOLBAR_HEIGHT = 100;
    /**
     * pointer
     *
     */
    protected EComboBox eBox = null;


    /**
     * Constructor
     *
     * @param _eBox
     * @author Anthony C. Liberto
     */
    public EComboBoxUI(EComboBox _eBox) {
        super();
        eBox = _eBox;
        return;
    }

    /**
     * eaccess
     *
     * @return
     * @author Anthony C. Liberto
     */
    public static EAccess eaccess() {
        return EAccess.eaccess();
    }

    /**
     * refreshAppearance
     *
     * @author Anthony C. Liberto
     */
    public void refreshAppearance() {
        ((EList2) listBox).setFixedCellHeight();
        return;
    }

    /**
     * @see javax.swing.plaf.basic.BasicComboBoxUI#createPopup()
     * @author Anthony C. Liberto
     */
    protected ComboPopup createPopup() {
        BasicComboPopup popup = new BasicComboPopup(comboBox) {
        	private static final long serialVersionUID = 1L;
        	protected int getPopupHeightForRowCount(int maxRowCount) {
                int minRowCount = Math.min(maxRowCount, eBox.getItemCount());
                FontMetrics fm = eBox.getFontMetrics(eBox.getFont());
                int height = (minRowCount * (fm.getHeight() + 4));
                return height;
            }

            public Cursor getCursor() {
                return eBox.getCursor();
            }

            protected JList createList() {
                return new EList2(comboBox.getModel()) {
                	private static final long serialVersionUID = 1L;
                	public void processMouseEvent(MouseEvent e) {
                        if (e.isControlDown()) {
                            e = new MouseEvent((Component) e.getSource(), e.getID(), e.getWhen(), e.getModifiers() ^ InputEvent.CTRL_MASK, e.getX(), e.getY(), e.getClickCount(), e.isPopupTrigger());
                        }
                        super.processMouseEvent(e);
                    }

                    public Cursor getCursor() {
                        return eBox.getCursor();
                    }
                };
            }
            /*
            	50567
            */
            public void show() {
                Dimension popupSize = comboBox.getSize();
                Rectangle popupBounds = null;
                popupSize.setSize(popupSize.width, getPopupHeightForRowCount(comboBox.getMaximumRowCount()));
                popupBounds = computePopup(0, comboBox.getBounds().height, ((EComboBox) comboBox).getPopupWidth(), popupSize.height);
                scroller.setMaximumSize(popupBounds.getSize());
                scroller.setPreferredSize(popupBounds.getSize());
                scroller.setMinimumSize(popupBounds.getSize());
                list.invalidate();
                list.ensureIndexIsVisible(list.getSelectedIndex());
                setLightWeightPopupEnabled(comboBox.isLightWeightPopupEnabled());
                show(comboBox, popupBounds.x, popupBounds.y);
                return;
            }

            private Rectangle computePopup(int x, int y, int w, int h) {
                Rectangle r = new Rectangle(x, y, w, h);
                Point p = new Point(0, 0);
                Dimension scr = Toolkit.getDefaultToolkit().getScreenSize();
                Rectangle screen = null;
                int myX = -1;
                int myY = -1;
                SwingUtilities.convertPointFromScreen(p, comboBox);
                screen = new Rectangle(p.x, p.y, scr.width, scr.height - TOOLBAR_HEIGHT);
                myX = getOffHorz(screen, r);
                myY = getOffVert(screen, r, y);
                return new Rectangle(myX, myY, r.width, r.height);
            }

            private int getOffVert(Rectangle scr, Rectangle r, int y) {
                int h = (scr.y + scr.height) - 5;
                int H = r.y + r.height;
                if (H >= h) {
                    return -r.height;
                }
                return y;
            }

            private int getOffHorz(Rectangle scr, Rectangle r) {
                int w = (scr.x + scr.width) - 5;
                int W = r.x + r.width;
                if (W >= w) {
                    return w - W;
                }
                return 0;
            }

            protected JScrollPane createScroller() {
                return new JScrollPane(list, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            }
        };
        return popup;
    }

    /**
     * @see javax.swing.plaf.basic.BasicComboBoxUI#paintCurrentValue(java.awt.Graphics, java.awt.Rectangle, boolean)
     * @author Anthony C. Liberto
     */
    public void paintCurrentValue(Graphics g, Rectangle bounds, boolean hasFocus) {
        ListCellRenderer renderer = eBox.getRenderer();
        Component c = null;
        if (comboBox.getSelectedIndex() == -1) {
            if (hasFocus && !isPopupVisible(eBox)) {
                c = renderer.getListCellRendererComponent(listBox, "", -1, true, false); //$NON-NLS-1$
            } else {
                c = renderer.getListCellRendererComponent(listBox, "", -1, false, false); //$NON-NLS-1$
            }
            if (hasFocus) {
                c.setBackground(UIManager.getColor("ComboBox.selectionBackground")); //$NON-NLS-1$
            } else {
                c.setBackground(eBox.getBackground());
            }
            c.setFont(eBox.getFont());
            currentValuePane.paintComponent(g, c, eBox, bounds.x, bounds.y, bounds.width, bounds.height);
            return;
        }
        if (hasFocus && !isPopupVisible(comboBox)) {
            c = renderer.getListCellRendererComponent(listBox, eBox.getSelectedItem(), -1, true, false);
        } else {
            c = renderer.getListCellRendererComponent(listBox, eBox.getSelectedItem(), -1, false, false);
        }
        c.setBackground(eBox.getBackground());
        c.setForeground(eBox.getForeground());
        c.setFont(eBox.getFont());
        c.setEnabled(eBox.isEnabled());
        currentValuePane.paintComponent(g, c, eBox, bounds.x, bounds.y, bounds.width, bounds.height);
        return;
    }
}
