/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: FlagEditorUI.java,v $
 * Revision 1.3  2012/04/05 17:50:33  wendy
 * jre142 and win7 changes
 *
 * Revision 1.2  2008/02/18 16:24:39  wendy
 * Cleanup unused methods
 *
 * Revision 1.1  2007/04/18 19:47:21  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:56  tony
 * This is the initial load of OPICM
 *
 * Revision 1.7  2005/09/08 17:58:59  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.6  2005/05/19 20:41:49  tony
 * singleFlag UI Enhancement.
 *
 * Revision 1.5  2005/02/03 21:26:12  tony
 * JTest Format Third Pass
 *
 * Revision 1.4  2005/02/03 19:42:22  tony
 * JTest Third pass
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
 * Revision 1.5  2003/11/20 19:58:05  tony
 * 52316
 *
 * Revision 1.4  2003/04/17 23:13:51  tony
 * adjusted editing colors.
 *
 * Revision 1.3  2003/03/21 20:54:31  tony
 * updated getBackground, getForeground, and getFont logic.
 * added logic so that each can be set for the component.
 *
 * Revision 1.2  2003/03/19 20:36:31  tony
 * comboBox accessibility enhancements.
 *
 * Revision 1.1.1.1  2003/03/03 18:04:02  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2002/11/07 16:58:11  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.ui;
import com.elogin.EAccess;
import com.ibm.eannounce.eforms.editor.*;
import java.awt.*;

import javax.swing.plaf.basic.*;
import javax.swing.plaf.metal.MetalComboBoxButton;
import javax.swing.plaf.metal.MetalComboBoxIcon;
import javax.swing.*;
import javax.swing.plaf.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class FlagEditorUI extends EComboBoxUI { 
    private final int defBtnSize = 20;
    private FlagEditorPopup flagPop = null;
    private JButton arrow = null;//    	new BasicArrowButton(BasicArrowButton.SOUTH);win7 cant render windows lnf, must use metal
    /**
     * currentValuePane
     */
    protected CellRendererPane currentValuePane = new CellRendererPane();
    private String defValue = ""; //$NON-NLS-1$

    /**
     * Constructor
     *
     * @param _edit
     * @author Anthony C. Liberto
     */
    public FlagEditorUI(FlagEditor _edit) {
        super(_edit);
    }

    /**
     * setArrowVisible
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setArrowVisible(boolean _b) {
        arrow.setVisible(_b);
    }

    /**
     * isArrowVisible
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isArrowVisible() {
        return arrow.isVisible();
    }

    /**
     * @see javax.swing.plaf.basic.BasicComboBoxUI#createArrowButton()
     */
    protected JButton createArrowButton() {
    	//must use metal on win7
    	arrow = new MetalComboBoxButton( comboBox,
                new MetalComboBoxIcon(),
                comboBox.isEditable(),
                currentValuePane,
                listBox )
    	{
			private static final long serialVersionUID = 1L;

			// arrow ends up with green background, turn it off
    	    public void setEnabled(boolean enabled) {
    	    	super.setEnabled(enabled);

    	    	// Set the background and foreground to the combobox colors.
    	    	if (enabled) {
     	    	    setBackground(UIManager.getColor("ComboBox.disabledBackground"));
    	    	    setForeground(comboBox.getForeground());
    	    	} else {
    	    	    setBackground(UIManager.getColor("ComboBox.disabledBackground"));
    	    	    setForeground(UIManager.getColor("ComboBox.disabledForeground"));
    	    	}	    
    	    }
    	};
    	arrow.setMargin( new Insets( 0, 1, 1, 3 ) );
 
        return arrow;
    }

    /**
     * create The Component UI - method required by JRE
     *
     * @param _fe
     * @return
     */
    public static ComponentUI createUI(FlagEditor _fe) {
        return new FlagEditorUI(_fe);
    }

    /**
     * @see javax.swing.plaf.basic.BasicComboBoxUI#createPopup()
     * @author Anthony C. Liberto
     */
    protected ComboPopup createPopup() {
        if (flagPop == null) {
            flagPop = new FlagEditorPopup((FlagEditor) eBox);
        }
        return flagPop;
    }

    /**
     * getFlagPopup
     *
     * @return
     * @author Anthony C. Liberto
     */
    public ComboPopup getFlagPopup() {
        return createPopup();
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
     * setSelectedIndex
     *
     * @param _i
     * @author Anthony C. Liberto
     */
    public void setSelectedIndex(int _i) {
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
     * @see javax.swing.plaf.basic.BasicComboBoxUI#createLayoutManager()
     * @author Anthony C. Liberto
     */
    protected LayoutManager createLayoutManager() {
        return new ComboBoxLayoutManager();
    }

    /**
     * To change the template for this generated type comment go to
     * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
     *
     * @author Anthony C. Liberto
     */
    public class ComboBoxLayoutManager implements LayoutManager {
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
        public Dimension preferredLayoutSize(Container parent) {
            return parent.getPreferredSize();
        }

        /**
         * @see java.awt.LayoutManager#minimumLayoutSize(java.awt.Container)
         * @author Anthony C. Liberto
         */
        public Dimension minimumLayoutSize(Container parent) {
            return parent.getMinimumSize();
        }

        /**
         * @see java.awt.LayoutManager#layoutContainer(java.awt.Container)
         * @author Anthony C. Liberto
         */
        public void layoutContainer(Container parent) {
            JComboBox cb = (JComboBox) parent;
            int width = cb.getWidth();
            int height = cb.getHeight();
            Insets insets = getInsets();
            int buttonSize = height - (insets.top + insets.bottom);
            Rectangle cvb;
            arrowButton.setBounds(width - (insets.right + defBtnSize), insets.top, defBtnSize, buttonSize);
            if (editor != null) {
                cvb = rectangleForCurrentValue();
                editor.setBounds(cvb);
            }
        }
    }

    /**
     * @see javax.swing.plaf.basic.BasicComboBoxUI#paintCurrentValue(java.awt.Graphics, java.awt.Rectangle, boolean)
     * @author Anthony C. Liberto
     */
    public void paintCurrentValue(Graphics _g, Rectangle _rect, boolean _hasFocus) {
        ListCellRenderer renderer = eBox.getRenderer();
        Component c = null;
        if (eBox.getSelectedIndex() == -1) {
            if (hasFocus && !isPopupVisible(comboBox)) {
                c = renderer.getListCellRendererComponent(listBox, defValue, -1, true, false);
            } else {
                c = renderer.getListCellRendererComponent(listBox, defValue, -1, false, false);
            }
            if (hasFocus) { //52316
                c.setBackground(UIManager.getColor("Table.selectionBackground")); //52316  //$NON-NLS-1$
            } else { //52316
                c.setBackground(eBox.getBackground());
            } //52316

            c.setForeground(eBox.getForeground());
            c.setFont(eBox.getFont());
            currentValuePane.paintComponent(_g, c, eBox, _rect.x, _rect.y, _rect.width, _rect.height);
        } else {
            if (hasFocus && !isPopupVisible(comboBox)) {
                c = renderer.getListCellRendererComponent(listBox, eBox.getSelectedItem(), -1, true, false);
            } else {
                c = renderer.getListCellRendererComponent(listBox, eBox.getSelectedItem(), -1, false, false);
            }
            //			if ( hasFocus && !isPopupVisible(comboBox) ) {
            //				c.setForeground(listBox.getSelectionForeground());
            //				c.setBackground(listBox.getSelectionBackground());
            //			} else {
            c.setForeground(eBox.getForeground());
            if (hasFocus) { //52316
                c.setBackground(UIManager.getColor("Table.selectionBackground")); //52316  //$NON-NLS-1$
            } else { //52316
                c.setBackground(eBox.getBackground());
            } //52316
            //			}

            c.setFont(eBox.getFont());
            currentValuePane.paintComponent(_g, c, eBox, _rect.x, _rect.y, _rect.width, _rect.height);
            //	         super.paintCurrentValue(_g,_rect,_hasFocus);
        }
    }

/*
 Enhanced
 */
	/**
	 * setPopupVisible
	 * enhanced
	 * @param _box
	 * @param _vis
	 */
	public void setPopupVisible(JComboBox _box, boolean _vis) {
		if (_box instanceof FlagEditor) {
			if (_vis) {
				if (!((FlagEditor)_box).isDisplayablePopup()) {
					_vis = false;
				}
			}
		}
		super.setPopupVisible(_box,_vis);
	}

	/**
	 * create the editor - called by JRE installui()
	 * enhanced
	 * @return editor
	 * @author Anthony C. Liberto
	 */
    protected ComboBoxEditor createEditor() {
		if (EAccess.isArmed(ENHANCED_FLAG_EDIT)) {
	        return new FlagComboEditor(comboBox);
		} else {
			return super.createEditor();
		}
    }

}
