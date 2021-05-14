/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: EFlagBoxUI.java,v $
 * Revision 1.2  2008/01/30 16:27:04  wendy
 * Cleanup RSA warnings
 *
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
 * Revision 1.4  2005/02/10 18:59:34  tony
 * JTest Formatting
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
 * Revision 1.4  2003/04/11 20:02:27  tony
 * added copyright statements.
 *
 */
package com.ibm.eannounce.ui;
import com.ibm.eannounce.eobjects.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.basic.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class EFlagBoxUI extends EComboBoxUI { //WindowsComboBoxUI
    /**
     * overRideVisible
     *
     */
    public boolean overRideVisible = false; //013080
    private static final String LIGHTWEIGHT_KEYBOARD_NAVIGATION = "JComboBox.lightweightKeyboardNavigation"; //15060  //$NON-NLS-1$
    private static final String LIGHTWEIGHT_KEYBOARD_NAVIGATION_ON = "Lightweight"; //15060  //$NON-NLS-1$
    private static final String LIGHTWEIGHT_KEYBOARD_NAVIGATION_OFF = "Heavyweight"; //15060  //$NON-NLS-1$
    private int defBtnSize = 20; //staticButtonSize
    private Color focusColor = UIManager.getColor("ComboBox.selectionBackground"); //$NON-NLS-1$

    /**
     * Constructor
     *
     * @param _box
     * @author Anthony C. Liberto
     */
    public EFlagBoxUI(EComboBox _box) {
        super(_box);
        return;
    }

    /**
     * @see javax.swing.plaf.basic.BasicComboBoxUI#createPopup()
     * @author Anthony C. Liberto
     */
    protected ComboPopup createPopup() {
        BasicComboPopup popup = new BasicComboPopup(comboBox) {
        	private static final long serialVersionUID = 1L;
        	public void show() {
                Dimension popupSize = comboBox.getSize();
                Dimension d = ((EFlagBox) comboBox).getPreferredSize(false);
                Rectangle popupBounds = null;
                popupSize.setSize(popupSize.width, getPopupHeightForRowCount(comboBox.getMaximumRowCount()));
                popupBounds = computePopup(0, comboBox.getBounds().height, d.width + 15, popupSize.height);
                scroller.setMaximumSize(popupBounds.getSize());
                scroller.setPreferredSize(popupBounds.getSize());
                scroller.setMinimumSize(popupBounds.getSize());
                list.invalidate();
                list.ensureIndexIsVisible(list.getSelectedIndex());
                setLightWeightPopupEnabled(comboBox.isLightWeightPopupEnabled());
                show(comboBox, popupBounds.x, popupBounds.y);
                return;
            }

            protected KeyListener createKeyListener() {
                return new MyInvocationKeyHandler();
            }

            class MyInvocationKeyHandler extends KeyAdapter {
                public void keyReleased(KeyEvent e) {
                    boolean isLight = false;
                    Object navType = comboBox.getClientProperty(LIGHTWEIGHT_KEYBOARD_NAVIGATION);
                    if (navType != null) {
                        if (navType.equals(LIGHTWEIGHT_KEYBOARD_NAVIGATION_ON)) {
                            isLight = true;
                        } else if (navType.equals(LIGHTWEIGHT_KEYBOARD_NAVIGATION_OFF)) {
                            isLight = false;
                        }
                    }
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        if (isVisible()) {
                            if (isLight) {
                                comboBox.setSelectedIndex(list.getSelectedIndex());
                            } else {
                                togglePopup();
                            }
                        }
                    }
                }
            }

            public boolean isVisible() {
                if (overRideVisible) {
                    return false;
                }
                return super.isVisible();
            }
        };
        return popup;
    }

    /**
     * isVisible
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isVisible() {
        return popup.isVisible();
    }

    /**
     * popupRepaint
     *
     * @author Anthony C. Liberto
     */
    public void popupRepaint() {
        popup.getList().repaint();
    }

    /**
     * getListSelection
     *
     * @return
     * @author Anthony C. Liberto
     */
    public int getListSelection() {
        return popup.getList().getSelectedIndex();
    }

    /**
     * getList
     *
     * @return
     * @author Anthony C. Liberto
     */
    public JList getList() {
        return popup.getList();
    }

    private Rectangle computePopup(int x, int y, int w, int h) {
        Rectangle r = new Rectangle(x, y, w, h);
        Point p = new Point(0, 0);
        Dimension scr = Toolkit.getDefaultToolkit().getScreenSize();
        int myX = -1;
        int myY = -1;
        Rectangle screen = null;
        SwingUtilities.convertPointFromScreen(p, comboBox);
        screen = new Rectangle(p.x, p.y, scr.width, scr.height - 100);
        myX = getOffHorz(screen, r);
        myY = getOffVert(screen, r, y);
        return new Rectangle(myX, myY, r.width, r.height);
    }

    /**
     * getPreferredSize
     *
     * @return
     * @author Anthony C. Liberto
     */
    public Dimension getPreferredSize() {
        Dimension d = comboBox.getPreferredSize();
        d.width = d.width + 16;
        return d;
    }

    /**
     * get Vertical Offset
     *
     * @param scr
     * @param r
     * @param y
     * @return
     * @author Anthony C. Liberto
     */
    public int getOffVert(Rectangle scr, Rectangle r, int y) {
        int h = (scr.y + scr.height) - 5;
        int H = r.y + r.height;
        if (H >= h) {
            return -r.height;
        }
        return y;
    }

    /**
     * get Horizontal Offset
     *
     * @param scr
     * @param r
     * @return
     * @author Anthony C. Liberto
     */
    public int getOffHorz(Rectangle scr, Rectangle r) {
        int w = (scr.x + scr.width) - 5;
        int W = r.x + r.width;
        if (W >= w) {
            return w - W;
        }
        return 0;
    }

    /**
     * @see javax.swing.plaf.basic.BasicComboBoxUI#createLayoutManager()
     * @author Anthony C. Liberto
     */
    protected LayoutManager createLayoutManager() { //staticButtonSize
        return new ComboBoxLayoutManager();
    }

    /**
     * To change the template for this generated type comment go to
     * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
     *
     * @author Anthony C. Liberto
     */
    public class ComboBoxLayoutManager implements LayoutManager { //staticButtonSize
        /**
         * @see java.awt.LayoutManager#addLayoutComponent(java.lang.String, java.awt.Component)
         * @author Anthony C. Liberto
         */
        public void addLayoutComponent(String name, Component comp) {
        }
        /**
         * @see java.awt.LayoutManager#removeLayoutComponent(java.awt.Component)
         * @author Anthony C. Liberto
         */
        public void removeLayoutComponent(Component comp) {
        }

        /**
         * @see java.awt.LayoutManager#preferredLayoutSize(java.awt.Container)
         * @author Anthony C. Liberto
         */
        public Dimension preferredLayoutSize(Container parent) { //staticButtonSize
            return parent.getPreferredSize();
        }

        /**
         * @see java.awt.LayoutManager#minimumLayoutSize(java.awt.Container)
         * @author Anthony C. Liberto
         */
        public Dimension minimumLayoutSize(Container parent) { //staticButtonSize
            return parent.getMinimumSize();
        }

        /**
         * @see java.awt.LayoutManager#layoutContainer(java.awt.Container)
         * @author Anthony C. Liberto
         */
        public void layoutContainer(Container parent) { //staticButtonSize
            JComboBox cb = (JComboBox) parent;
            int width = cb.getWidth();
            int height = cb.getHeight();
            Insets insets = getInsets();
            int buttonSize = height - (insets.top + insets.bottom);
            Rectangle cvb;
            arrowButton.setBounds(width - (insets.right + defBtnSize), insets.top, defBtnSize, buttonSize); //staticButtonSize
            if (editor != null) {
                cvb = rectangleForCurrentValue();
                editor.setBounds(cvb);
            }
        }
    } //staticButtonSize

    /**
     * @see javax.swing.plaf.basic.BasicComboBoxUI#paintCurrentValue(java.awt.Graphics, java.awt.Rectangle, boolean)
     * @author Anthony C. Liberto
     */
    public void paintCurrentValue(Graphics g, Rectangle bounds, boolean hasFocus) {
        if (comboBox.getSelectedIndex() == -1) {
            ListCellRenderer renderer = comboBox.getRenderer();
            Component c = null;
            if (hasFocus && !isPopupVisible(comboBox)) {
                c = renderer.getListCellRendererComponent(listBox, "", -1, true, false); //$NON-NLS-1$
            } else {
                c = renderer.getListCellRendererComponent(listBox, "", -1, false, false); //$NON-NLS-1$
            }
            if (hasFocus) {
                c.setBackground(focusColor);
            } else {
                c.setBackground(comboBox.getBackground());
            }
            c.setFont(eBox.getFont());
            currentValuePane.paintComponent(g, c, comboBox, bounds.x, bounds.y, bounds.width, bounds.height);
            return;
        }
        super.paintCurrentValue(g, bounds, hasFocus);
        return;
    }

}
