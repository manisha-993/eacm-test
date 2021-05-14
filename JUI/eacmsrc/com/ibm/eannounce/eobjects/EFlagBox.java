/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: EFlagBox.java,v $
 * Revision 1.3  2012/04/05 17:39:36  wendy
 * jre142 and win7 changes
 *
 * Revision 1.2  2008/01/30 16:26:57  wendy
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
 * Revision 1.5  2003/12/22 19:03:54  tony
 * 53451
 *
 * Revision 1.4  2003/04/11 20:02:32  tony
 * added copyright statements.
 *
 */
package com.ibm.eannounce.eobjects;
import COM.ibm.eannounce.objects.MetaLink;
import com.ibm.eannounce.ui.EFlagBoxUI;
import COM.ibm.eannounce.objects.EntityItem;
import java.awt.*;
import java.awt.event.*;
//import java.util.Vector;
import javax.swing.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class EFlagBox extends EComboBox implements KeyListener { //cleanup
	private static final long serialVersionUID = 1L;
	/**
     * popWidth
     */
//    private int popWidth = 0;
    private int maxWidth = -1;
    private static final String LIGHTWEIGHT_KEYBOARD_NAVIGATION = "JComboBox.lightweightKeyboardNavigation"; //013080
    private static final String LIGHTWEIGHT_KEYBOARD_NAVIGATION_ON = "Lightweight"; //013080
//    private static final String LIGHTWEIGHT_KEYBOARD_NAVIGATION_OFF = "Heavyweight"; //013080
//    private boolean adjustPrefWidth = true; //013273

    /**
     * eFlagBox
     * @author Anthony C. Liberto
     */
    public EFlagBox() {
        super();
  //win7  super() already calls init    init(); //013208
   //win7 cant see selection     setUI(new EFlagBoxUI(this));
        return;
    }

    /**
     * eFlagBox
     * @param cbm
     * @author Anthony C. Liberto
     * /
    public EFlagBox(ComboBoxModel cbm) {
        super(cbm);
        init(); //013208
        setUI(new EFlagBoxUI(this));
        return;
    }

    /**
     * eFlagBox
     * @param o
     * @author Anthony C. Liberto
     */
    public EFlagBox(Object[] o) {
        super(o);
  //win7      init(); //013208
  //win7      setUI(new EFlagBoxUI(this));
        return;
    }

    /**
     * eFlagBox
     * @param v
     * @author Anthony C. Liberto
     * /
    public EFlagBox(Vector v) {
        super(v);
        init(); //013208
        setUI(new EFlagBoxUI(this));
        return;
    }

    /**
     * getSuperPreferredSize
     * @return
     * @author Anthony C. Liberto
     */
    public Dimension getSuperPreferredSize() {
        return super.getPreferredSize();
    }

    /**
     * @see java.awt.Component#getPreferredSize()
     * @author Anthony C. Liberto
     */
    public Dimension getPreferredSize() {
        return getPreferredSize(true);
    }

    /**
     * getPreferredSize
     * @param _invokeMaxLimit
     * @return
     * @author Anthony C. Liberto
     */
    public Dimension getPreferredSize(boolean _invokeMaxLimit) {
        FontMetrics fm = getFontMetrics(getFont()); //013316
        return new Dimension(getWidth(fm, _invokeMaxLimit), getHeight(fm));
    }

    private int getWidth(FontMetrics fm, boolean _invokeMaxLimit) {
        int w = 100;
        int ii = getItemCount();
        int out = 0;
        for (int i = 0; i < ii; ++i) {
            w = Math.max(w, fm.stringWidth(getItemAt(i).toString()));
        }
        out = w + 35;
        if (_invokeMaxLimit) {
            if (maxWidth > 0 && maxWidth < out) {
                return maxWidth;
            }
        }
        return out;
    }

    /**
     * setMaxWidth
     * @param _i
     * @author Anthony C. Liberto
     */
    public void setMaxWidth(int _i) {
        maxWidth = _i;
        return;
    }

    /**
     * getMaxWidth
     * @return
     * @author Anthony C. Liberto
     */
    public int getMaxWidth() {
        return maxWidth;
    }

    private int getHeight(FontMetrics fm) {
        return fm.getHeight() + 4;
    }

    /**
     * reload
     * @param _o
     * @author Anthony C. Liberto
     */
    public void reload(Object[] _o) {
        removeAllItems();
        if (_o != null) {
            int ii = _o.length;
            for (int i = 0; i < ii; ++i) {
                addItem(_o[i]);
            }
        }
        return;
    }

    /**
     * getSelectedMetaLinkItem
     * @return
     * @author Anthony C. Liberto
     */
    public MetaLink getSelectedMetaLinkItem() {
        int i = getSelectedIndex();
        if (i >= 0) {
            Object o = getItemAt(i);
            if (o != null && o instanceof MetaLink) {
                MetaLink out = (MetaLink) o;
                out.setSelected(true);
                return out;
            }
        }
        return null;
    }

    /**
     * init
     * @author Anthony C. Liberto
     */
    protected void init() {
//    	not needed with metal LNF	    setBorder(UIManager.getBorder("eannounce.loweredBorder"));
        setBackground(Color.white);
        putClientProperty(LIGHTWEIGHT_KEYBOARD_NAVIGATION, LIGHTWEIGHT_KEYBOARD_NAVIGATION_ON);
        addKeyListener(this);
        return;
    }

    /**
     * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
     * @author Anthony C. Liberto
     */
    public void keyPressed(KeyEvent kea) {
    }
    /**
     * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
     * @author Anthony C. Liberto
     */
    public void keyTyped(KeyEvent kea) {
    }
    /**
     * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
     * @author Anthony C. Liberto
     */
    public void keyReleased(KeyEvent kea) {
        if (kea.getKeyCode() == KeyEvent.VK_ESCAPE) {
            cancelCellEditing();
            hidePopup();
        } else if (kea.getKeyCode() == KeyEvent.VK_ENTER) {
            EFlagBoxUI ui = (EFlagBoxUI) getUI();
            int i = ui.getListSelection();
            returnSelected(i);
            stopCellEditing();
            hidePopup();
        } else if (kea.getKeyCode() == KeyEvent.VK_UP || kea.getKeyCode() == KeyEvent.VK_DOWN) {
            EFlagBoxUI ui = (EFlagBoxUI) getUI();
            if (!ui.isVisible()) {
                showPopup();
            }
        }
        return;
    }

    /**
     * @see javax.swing.JComboBox#selectWithKeyChar(char)
     * @author Anthony C. Liberto
     */
    public boolean selectWithKeyChar(char c) {
        EFlagBoxUI ui = (EFlagBoxUI) getUI();
        int i = -1;
        ui.overRideVisible = true;
        if (keySelectionManager == null) {
            keySelectionManager = createDefaultKeySelectionManager();
        }
        i = keySelectionManager.selectionForKey(c, getModel());
        if (i > -1) {
            ComboBoxModel cbm = getModel();
            cbm.setSelectedItem(cbm.getElementAt(i));
        }
        ui.overRideVisible = false;
        if (!ui.isVisible()) {
            showPopup();
        } else if (i > -1) {
            ui.popupRepaint();
        }
        return false;
    }

    /**
     * cancelCellEditing
     * @author Anthony C. Liberto
     */
    public void cancelCellEditing() {
    }

    /**
     * stopCellEditing
     * @return
     * @author Anthony C. Liberto
     */
    public boolean stopCellEditing() {
        return false;
    }

    /**
     * returnSelected
     * @param i
     * @author Anthony C. Liberto
     */
    public void returnSelected(int _i) {
    }

    /**
     * getPopupSize
     * @return
     * @author Anthony C. Liberto
     */
    public Dimension getPopupSize() {
        return getSize();
    }

    /*
     53451
     */
    /**
     * setSelectedParent
     * @param _item
     * @author Anthony C. Liberto
     */
    public void setSelectedParent(EntityItem _item) {
        if (_item == null) {
            setSelectedIndex(0);
        } else {
            String key = _item.getKey();
            int ii = getItemCount();
            for (int i = 0; i < ii; ++i) {
                Object o = getItemAt(i);
                if (o instanceof EntityItem) {
                    if (key.equals(((EntityItem) o).getKey())) {
                        setSelectedItem(o);
                        return;
                    }
                }
            }
        }
        return;
    }
}
