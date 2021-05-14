//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.tabs;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;

import com.ibm.eacm.objects.*;

import java.awt.*;
import java.awt.event.*;

/***************
 * 
 * close tab button on the tab itself
 * @author Wendy Stimpson
 */
//$Log: CloseTabComponent.java,v $
//Revision 1.1  2012/09/27 19:39:23  wendy
//Initial code
//
public class CloseTabComponent extends JPanel implements EACMGlobals {
	private static final long serialVersionUID = 1L;
    private TabButton button;
    private JLabel titleLabel = null;
    private JLabel iconLabel = null;
    
    /**
     * @param title
     * @param closeTabListener
     */
    public CloseTabComponent(String title, ActionListener closeTabListener) {
    	this(title, null, closeTabListener);
    }
    /**
     *  constructor
     * @param title
     * @param icon
     * @param closeTabListener
     */
    public CloseTabComponent(String title, Icon icon, ActionListener closeTabListener) {
        //remove default FlowLayout' gaps
        super(new FlowLayout(FlowLayout.LEFT, 0, 0));

        setOpaque(false);
        
        titleLabel = new JLabel(title);
        if(icon!=null){
        	iconLabel = new JLabel(icon);
        	add(iconLabel);
        }
        add(titleLabel);
  
        //add more space between the label and the button
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        //tab button
        button = new TabButton();
        add(button);
        button.addActionListener(closeTabListener);
        //add more space to the top of the component
        setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
    }
    
    
    /**
     * update the title
     * @param title
     */
    public void setTitle(String title){
    	titleLabel.setText(title);
    }

    /**
     * update the icon
     * @param title
     */
    public void setIcon(Icon icon){
    	if(iconLabel!=null){
    		iconLabel.setIcon(icon);
    	}
    }
    
    /**
     * release memory
     * @param closeTabListener
     */
    public void dereference(ActionListener closeTabListener){
        button.removeActionListener(closeTabListener);
    	button.dereference();
    	button = null;
    
    	titleLabel.removeAll();
    	titleLabel.setUI(null);
    	titleLabel = null;
    	
    	if(iconLabel!=null){
    		iconLabel.removeAll();
    		iconLabel.setUI(null);
    		iconLabel = null;
    	}
    	
    	removeAll();
    	setUI(null);
    }
    /**
     *  button used on the tab, has an X image
     *
     */
    public class TabButton extends JButton 
    {
		private static final long serialVersionUID = 1L;

		/**
		 *  constructor
		 */
		TabButton() {
            int size = 17;
            setPreferredSize(new Dimension(size, size));
            setToolTipText(Utils.getResource(CLOSETAB_ACTION)); 
            //Make the button looks the same for all Laf's
            setUI(new BasicButtonUI());
            //Make it transparent
            setContentAreaFilled(false);
            //No need to be focusable
            setFocusable(false);
            setBorder(BorderFactory.createEtchedBorder());
            setBorderPainted(false);
            //Making nice rollover effect
            //we use the same listener for all buttons
            addMouseListener(buttonMouseListener);
            setRolloverEnabled(true);
        }
		
		/**
		 * @return
		 */
		public Component getTabComponent(){
			return CloseTabComponent.this;
		}
		
		/**
		 *  release memory
		 */
		void dereference(){
            // release memory
            removeMouseListener(buttonMouseListener);
            setUI(null);
		}

        /* (non-Javadoc)
         * we don't want to update UI for this button
         * @see javax.swing.JButton#updateUI()
         */
        public void updateUI() {}

        /* (non-Javadoc)
         * paint the cross
         * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
         */
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            //shift the image for pressed buttons
            if (getModel().isPressed()) {
                g2.translate(1, 1);
            }
            g2.setStroke(new BasicStroke(2));
            g2.setColor(Color.BLACK);
            if (getModel().isRollover()) {
                g2.setColor(Color.RED);
            }
            int delta = 6;
            g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
            g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);
            g2.dispose();
        }
    }

    /**
     *  add a border around the X if mouse is in it
     */
    private final static MouseListener buttonMouseListener = new MouseAdapter() {
        /* (non-Javadoc)
         * @see java.awt.event.MouseAdapter#mouseEntered(java.awt.event.MouseEvent)
         */
        public void mouseEntered(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(true);
            }
        }

        /* (non-Javadoc)
         * @see java.awt.event.MouseAdapter#mouseExited(java.awt.event.MouseEvent)
         */
        public void mouseExited(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(false);
            }
        }
    };
}
