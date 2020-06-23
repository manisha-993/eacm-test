/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: FlagEditorPopup.java,v $
 * Revision 1.3  2012/04/05 18:10:24  wendy
 * jre142 and win7 changes
 *
 * Revision 1.2  2008/01/30 16:27:05  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:44:34  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:37:57  tony
 * This is the initial load of OPICM
 *
 * Revision 1.6  2005/09/08 17:59:03  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.5  2005/05/19 20:41:49  tony
 * singleFlag UI Enhancement.
 *
 * Revision 1.4  2005/02/04 18:16:49  tony
 * JTest Formatter Fourth Pass
 *
 * Revision 1.3  2005/02/01 20:48:32  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 19:15:16  tony
 * JTest Format
 *
 * Revision 1.1.1.1  2004/02/10 16:59:41  tony
 * This is the initial load of OPICM
 *
 * Revision 1.9  2003/12/01 23:02:36  tony
 * 53235
 *
 * Revision 1.8  2003/11/14 20:45:09  tony
 * 52862
 *
 * Revision 1.7  2003/09/08 19:55:12  tony
 * 51732
 *
 * Revision 1.6  2003/07/15 18:24:14  joan
 * 51336
 *
 * Revision 1.5  2003/06/25 16:17:40  tony
 * 51346
 *
 * Revision 1.4  2003/04/30 00:11:38  tony
 * updated form editing capability per KC.
 *
 * Revision 1.3  2003/04/17 23:13:26  tony
 * played around with editing Colors
 *
 * Revision 1.2  2003/03/19 20:36:31  tony
 * comboBox accessibility enhancements.
 *
 * Revision 1.1.1.1  2003/03/03 18:03:47  tony
 * This is the initial load of OPICM
 *
 * Revision 1.6  2002/11/07 16:58:28  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.editor;
import com.ibm.eannounce.eobjects.*;
import COM.ibm.eannounce.objects.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicComboPopup;
import java.awt.event.*;
import java.awt.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class FlagEditorPopup extends BasicComboPopup {
	private static final long serialVersionUID = 1L;
	private static final String LIGHTWEIGHT_KEYBOARD_NAVIGATION = "JComboBox.lightweightKeyboardNavigation";
    private static final String LIGHTWEIGHT_KEYBOARD_NAVIGATION_ON = "Lightweight";
    private static final String LIGHTWEIGHT_KEYBOARD_NAVIGATION_OFF = "Heavyweight";
    private KeyListener listKeyListener = null;

    /**
     * flagEditorPopup
     * @param _fe
     * @author Anthony C. Liberto
     */
    public FlagEditorPopup(FlagEditor _fe) {
        super(_fe);
        return;
    }

    /**
     * @see javax.swing.plaf.basic.BasicComboPopup#createScroller()
     * @author Anthony C. Liberto
     */
    protected JScrollPane createScroller() {
        return new JScrollPane(list, 
        		ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED){
			private static final long serialVersionUID = 1L;

			public JScrollBar createVerticalScrollBar() {
        	        return new ScrollBar(JScrollBar.VERTICAL){
						private static final long serialVersionUID = 1L;

						protected void processMouseMotionEvent(MouseEvent e) {
        	            	 this.setVisible(false);//jre142 win7 cant scroll to values outside of viewport, hide
        	            	 super.processMouseMotionEvent(e);
        	            	 this.setVisible(true);
        	           }
        	           
        	           protected void processMouseEvent(MouseEvent e) {
        	             	 this.setVisible(false);//jre142 win7 cant scroll to values outside of viewport, hide
        	             	 super.processMouseEvent(e);
        	             	 this.setVisible(true);
        	           }
        	        };
        	    }
        };
    }

    /**
     * @see javax.swing.plaf.basic.ComboPopup#show()
     * @author Anthony C. Liberto
     */
    public void show() {	
        Dimension popupSize = comboBox.getSize();
        Rectangle popupBounds = null;
        popupSize.setSize(popupSize.width, getPopupHeightForRowCount(comboBox.getMaximumRowCount()));
        popupBounds = computePopup(0, comboBox.getBounds().height, ((FlagEditor) comboBox).getPopupWidth(), popupSize.height);
        scroller.setMaximumSize(popupBounds.getSize());
        scroller.setPreferredSize(popupBounds.getSize());
        scroller.setMinimumSize(popupBounds.getSize());
        list.invalidate();
        list.ensureIndexIsVisible(list.getSelectedIndex());
        setLightWeightPopupEnabled(comboBox.isLightWeightPopupEnabled());
        show(comboBox, popupBounds.x, popupBounds.y);
        return;
    }
    
    /**
     * @see javax.swing.JPopupMenu#show(java.awt.Component, int, int)
     * @author Anthony C. Liberto
     */
    public void show(Component _c, int _x, int _y) {
        if (!_c.isEnabled()) { //53235
            return; //53235
        } //53235
        super.show(_c, _x, _y);
    }

    /**
     * @see javax.swing.plaf.basic.BasicComboPopup#getPopupHeightForRowCount(int)
     * @author Anthony C. Liberto
     */
    protected int getPopupHeightForRowCount(int maxRowCount) { //22349
        int iRowCount = comboBox.getModel().getSize(); //22349
        int rowCount = Math.min(maxRowCount, iRowCount); //22349
        int height = 0; //22349
        ListCellRenderer rend = list.getCellRenderer(); //22349

        if (iRowCount >= 1) { //22349
            Object o = list.getModel().getElementAt(0); //22349
            if (o != null) { //22349
                Component c = rend.getListCellRendererComponent(list, o, 0, false, false); //22349
                if (c != null) { //22349
                    height = (c.getPreferredSize().height * Math.max(5, rowCount)); //22349
                } //22349
            } //22349
        } //22349

        return height == 0 ? 100 : height; //22349
    } //22349
    /*
    	protected JList createList() {
    		listKeyListener = createListKeyListener();
    		return new JList(comboBox.getModel());
    	}

    	protected int getPopupHeightForRowCount(int maxRowCount) {
    		int minRowCount = Math.min(maxRowCount,comboBox.getItemCount());
    		FontMetrics fm = comboBox.getFontMetrics(comboBox.getFont());
    		int height = (minRowCount * (fm.getHeight() + 4));
    		return height;
    	}
    */
    /**
     * @see javax.swing.plaf.basic.BasicComboPopup#createList()
     * @author Anthony C. Liberto
     */
    protected JList createList() {
        EList2 list = new EList2(comboBox.getModel()) {
        	private static final long serialVersionUID = 1L;
        	public void processMouseEvent(MouseEvent e) {
                if (e.isControlDown()) {
                    e = new MouseEvent((Component) e.getSource(), e.getID(), e.getWhen(), e.getModifiers() ^ InputEvent.CTRL_MASK, e.getX(), e.getY(), e.getClickCount(), e.isPopupTrigger());
                }
                super.processMouseEvent(e);
            }
        };
        list.setModalCursor(true); //51336
        return list;
        /*51336		return new eList2(comboBox.getModel()) {
        			public void processMouseEvent(MouseEvent e)  {
        				if (e.isControlDown())  {
        					e = new MouseEvent((Component)e.getSource(), e.getID(), e.getWhen(),
        								   e.getModifiers() ^ InputEvent.CTRL_MASK,
        								   e.getX(), e.getY(), e.getClickCount(), e.isPopupTrigger());
        				}
        				super.processMouseEvent(e);
        			}
        		};
        */
    }

    private Rectangle computePopup(int x, int y, int w, int h) {
        Rectangle r = new Rectangle(x, y, w, h);
        Point p = new Point(0, 0);
        Dimension scr = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle screen = null;
        int myX = -1;
        int myY = -1;
        SwingUtilities.convertPointFromScreen(p, comboBox);
        screen = new Rectangle(p.x, p.y, scr.width, scr.height - 100);
        myX = getOffHorz(screen, r);
        myY = getOffVert(screen, r, y);
        return new Rectangle(myX, myY, r.width, r.height);
    }

    /**
     * getOffVert
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
     * getOffHorz
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
     * @see javax.swing.plaf.basic.BasicComboPopup#createListMouseListener()
     * @author Anthony C. Liberto
     */
    protected MouseListener createListMouseListener() {
        return new MyListMouseHandler();
    }

    /**
     * To change the template for this generated type comment go to
     * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
     *
     * @author Anthony C. Liberto
     */
    private class MyListMouseHandler extends MouseAdapter {
        /**
         * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
         * @author Anthony C. Liberto
         */
        public void mouseClicked(MouseEvent _me) {
            Component c = _me.getComponent();
            if (c instanceof JList) {
                JList list = (JList) c;
                Object o = list.getSelectedValue();
                if (o instanceof MetaFlag) {
                    MetaFlag flag = (MetaFlag) o;
                    int i = -1;
                    if (flag.isSelected()) {
                        flag.setSelected(false);
                    } else {
                        flag.setSelected(true);
                    }
                    i = list.getSelectedIndex();
                    list.paintImmediately(list.getCellBounds(i, i));
                }
            }
        }

        /**
         * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
         * @author Anthony C. Liberto
         */
        public void mouseReleased(MouseEvent _me) {
            //			if (((flagEditor)comboBox).isSingleSelection()) {
            //kc_20030429				comboBox.setSelectedIndex(list.getSelectedIndex());
            hide(); //kc_20030429
            repaint(); //kc_20030429
            comboBox.setSelectedIndex(list.locationToIndex(_me.getPoint())); //kc_20030429
            ((FlagEditor) comboBox).stopCellEditing(); //51346

            //			}
            return;
        }
    }

    /**
     * @see javax.swing.plaf.basic.BasicComboPopup#configureList()
     * @author Anthony C. Liberto
     */
    protected void configureList() {
        super.configureList();
        list.addKeyListener(listKeyListener);
    }

    /**
     * @see javax.swing.plaf.basic.ComboPopup#uninstallingUI()
     * @author Anthony C. Liberto
     */
    public void uninstallingUI() {
        super.uninstallingUI();
        list.removeKeyListener(listKeyListener);
    }

    /**
     * createListKeyListener
     * @return
     * @author Anthony C. Liberto
     */
    protected KeyListener createListKeyListener() {
        return new MyListKeyHandler();
    }

    /**
     * To change the template for this generated type comment go to
     * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
     *
     * @author Anthony C. Liberto
     */
    private class MyListKeyHandler extends KeyAdapter {
        /**
         * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
         * @author Anthony C. Liberto
         */
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_ENTER) {
                if (isVisible()) {
                    togglePopup();
                }
            } else if (keyCode == KeyEvent.VK_SPACE) {
                Component c = e.getComponent();
                if (c instanceof JList) {
                    JList list = (JList) c;
                    Object o = list.getSelectedValue();
                    if (o instanceof MetaFlag) {
                        MetaFlag flag = (MetaFlag) o;
                        int i = -1;
                        if (flag.isSelected()) {
                            flag.setSelected(false);
                        } else {
                            flag.setSelected(true);
                        }
                        i = list.getSelectedIndex();
                        list.paintImmediately(list.getCellBounds(i, i));
                    }
                }
            }
        }
    }

    /**
     * @see javax.swing.plaf.basic.BasicComboPopup#createKeyListener()
     * @author Anthony C. Liberto
     */
    protected KeyListener createKeyListener() {
        return new MyInvocationKeyHandler();
    }

    /**
     * To change the template for this generated type comment go to
     * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
     *
     * @author Anthony C. Liberto
     */
    private class MyInvocationKeyHandler extends KeyAdapter {
        /**
         * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
         * @author Anthony C. Liberto
         */
        public void keyReleased(KeyEvent e) {
            boolean isLight = false;
            Object navType = comboBox.getClientProperty(LIGHTWEIGHT_KEYBOARD_NAVIGATION);
            int keyCode = -1;
            if (navType != null) {
                if (navType.equals(LIGHTWEIGHT_KEYBOARD_NAVIGATION_ON)) {
                    isLight = true;

                } else if (navType.equals(LIGHTWEIGHT_KEYBOARD_NAVIGATION_OFF)) {
                    isLight = false;
                }
            }
            keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_ENTER) {
                if (isVisible()) {
                    if (isLight) {
                        comboBox.setSelectedIndex(list.getSelectedIndex());
                    } else {
                        togglePopup();
                    }
                }
            } else if (keyCode == KeyEvent.VK_SPACE) {
                Component c = e.getComponent();
                if (c instanceof FlagEditor) {
                    FlagEditor edit = (FlagEditor) c;
                    Object o = edit.getSelectedItem();
                    int i = -1;
                    if (o != null && o instanceof MetaFlag) { //22054
                        MetaFlag flag = (MetaFlag) o;
                        if (flag.isSelected()) {
                            flag.setSelected(false);
                        } else {
                            flag.setSelected(true);
                        }
                        i = edit.getSelectedIndex();
                        list.paintImmediately(list.getCellBounds(i, i));
                    }
                }
            } else { //51732
                comboBox.setSelectedIndex(list.getSelectedIndex()); //51732
            }
        }
    }

}
