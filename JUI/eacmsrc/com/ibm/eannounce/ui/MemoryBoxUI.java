/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: MemoryBoxUI.java,v $
 * Revision 1.2  2008/01/30 16:27:04  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:47:21  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:56  tony
 * This is the initial load of OPICM
 *
 * Revision 1.6  2005/09/08 17:58:59  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.5  2005/02/08 16:33:58  tony
 * JTest Formatting Fourth pass
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
 * Revision 1.5  2003/05/21 21:29:13  tony
 * 50587
 *
 * Revision 1.4  2003/04/11 20:02:27  tony
 * added copyright statements.
 *
 */
package com.ibm.eannounce.ui;
import com.ibm.eannounce.eobjects.*;
import java.awt.*;
import java.awt.event.*; //15060
import javax.swing.plaf.basic.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class MemoryBoxUI extends EComboBoxUI { //WindowsComboBoxUI {
    /**
     * overridevisible
     *
     */
    public boolean overRideVisible = false; //013080
    private static final String LIGHTWEIGHT_KEYBOARD_NAVIGATION = "JComboBox.lightweightKeyboardNavigation"; //15060  //$NON-NLS-1$
    private static final String LIGHTWEIGHT_KEYBOARD_NAVIGATION_ON = "Lightweight"; //15060  //$NON-NLS-1$
    private static final String LIGHTWEIGHT_KEYBOARD_NAVIGATION_OFF = "Heavyweight"; //15060  //$NON-NLS-1$
    private int defBtnSize = 20; //staticButtonSize
    private Color focusColor = UIManager.getColor("ComboBox.selectionBackground"); //$NON-NLS-1$

    private EditKeyListener editListen = null;

    /**
     * Constructor
     *
     * @param _box
     * @author Anthony C. Liberto
     */
    public MemoryBoxUI(EComboBox _box) {
        super(_box);
        return;
    }

    /**
     * @see javax.swing.plaf.basic.BasicComboBoxUI#createPopup()
     * @author Anthony C. Liberto
     */
    protected ComboPopup createPopup() { //012561
        BasicComboPopup popup = new BasicComboPopup(comboBox) {//012561
        	private static final long serialVersionUID = 1L;
        	public void show() {//012561
                Dimension popupSize = comboBox.getSize(); //012561
                Dimension d = comboBox.getPreferredSize(); //012561
                int listIndex = 0;
                Rectangle popupBounds = null;
                popupSize.setSize(popupSize.width, getPopupHeightForRowCount(comboBox.getMaximumRowCount())); //012561
                popupBounds = computePopup(0, comboBox.getBounds().height, d.width + 15, popupSize.height); //012710
                scroller.setMaximumSize(popupBounds.getSize()); //012561
                scroller.setPreferredSize(popupBounds.getSize()); //012561
                scroller.setMinimumSize(popupBounds.getSize()); //012561
                list.invalidate(); //012561
                //acl_20021204				list.ensureIndexIsVisible(list.getSelectedIndex());																//012561
                listIndex = list.getSelectedIndex(); //acl_20021204
                list.ensureIndexIsVisible(listIndex); //acl_20021204
                comboBox.setSelectedIndex(listIndex); //acl_20021204
                setLightWeightPopupEnabled(comboBox.isLightWeightPopupEnabled()); //012561
                show(comboBox, popupBounds.x, popupBounds.y); //012561
            } //012561

            protected KeyListener createKeyListener() { //15060
                return new MyInvocationKeyHandler(); //15060
            } //15060

            protected ListSelectionListener createListSelectionListener() {
                return new MyListSelectionHandler();
            }
            //15060
            class MyInvocationKeyHandler extends KeyAdapter { //15060
                public void keyReleased(KeyEvent e) { //15060
                    boolean isLight = false; //15060
                    Object navType = comboBox.getClientProperty(LIGHTWEIGHT_KEYBOARD_NAVIGATION); //15060
                    if (navType != null) { //15060
                        if (navType.equals(LIGHTWEIGHT_KEYBOARD_NAVIGATION_ON)) { //15060
                            isLight = true; //15060

                        } else if (navType.equals(LIGHTWEIGHT_KEYBOARD_NAVIGATION_OFF)) { //15060
                            isLight = false;
                        } //15060
                    } //15060
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) { //15060
                        comboBox.setSelectedIndex(list.getSelectedIndex()); //15060
                    } //15060
                } //15060
            } //15060

            class MyListSelectionHandler implements ListSelectionListener {
                public void valueChanged(ListSelectionEvent _lse) {
                    ((MemoryBox) comboBox).setText(list.getSelectedIndex());
                    list.ensureIndexIsVisible(list.getSelectedIndex());
                }
            }

            public boolean isVisible() { //013080
                if (overRideVisible) { //013080
                    return false;
                } //013080
                return super.isVisible(); //013080
            }

        }; //012561
        return popup; //012561
    } //012561

    /**
     * isVisible
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isVisible() { //013080
        return popup.isVisible(); //013080
    } //013080

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

    private Rectangle computePopup(int x, int y, int w, int h) { //012710
        Rectangle screen = null;
        int myX = 0;
        int myY = 0;
        Rectangle r = new Rectangle(x, y, w, h); //012710
        Point p = new Point(0, 0); //012710
        Dimension scr = Toolkit.getDefaultToolkit().getScreenSize(); //012710
        SwingUtilities.convertPointFromScreen(p, comboBox); //012710
        screen = new Rectangle(p.x, p.y, scr.width, scr.height - 100); //16526
        myX = getOffHorz(screen, r); //012710
        myY = getOffVert(screen, r, y); //012710
        return new Rectangle(myX, myY, r.width, r.height); //012710
    } //012710

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
     * get the vertical offset
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

    /*
     50857
     */
    /**
     * @see javax.swing.plaf.ComponentUI#uninstallUI(javax.swing.JComponent)
     * @author Anthony C. Liberto
     */
    public void uninstallUI(JComponent _c) {
        JTextField txt = (JTextField) ((ComboBoxEditor) editor).getEditorComponent();
        txt.removeKeyListener(editListen);
        super.uninstallUI(_c);
        return;
    }

    /**
     * @see javax.swing.plaf.basic.BasicComboBoxUI#createEditor()
     * @author Anthony C. Liberto
     */
    protected ComboBoxEditor createEditor() {
        ComboBoxEditor myEditor = new BasicComboBoxEditor.UIResource();
        JTextField txt = (JTextField) myEditor.getEditorComponent();
        if (editListen == null) {
            editListen = new EditKeyListener();
        }
        txt.addKeyListener(editListen);
        return myEditor;
    }

    class EditKeyListener extends KeyAdapter {
        /**
         * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
         * @author Anthony C. Liberto
         */
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                ((MemoryBox) comboBox).processEnter();
            }
        }
    }

}
