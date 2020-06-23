//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.ui;

import com.ibm.eacm.*;
import com.ibm.eacm.objects.EACMGlobals;

import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;

import javax.swing.event.MenuKeyEvent;
import javax.swing.event.MenuKeyListener;
import javax.swing.event.PopupMenuListener;
import javax.swing.plaf.basic.*;

/**
 * this is used to display EANActionItems in menus and on buttons like Navigate
 * the menuitems have be dereferenced when the tab is closed.
 * @author Wendy Stimpson
 */
// $Log: ScrollPopupMenu.java,v $
// Revision 1.2  2013/07/18 18:39:29  wendy
// fix compiler warnings
//
// Revision 1.1  2012/09/27 19:39:11  wendy
// Initial code
//
public class ScrollPopupMenu extends JPopupMenu  implements EACMGlobals {
    private static final long serialVersionUID = 1L;

    /**
     * max visible menu items for scroll
     */
    private static final int MAX_VISIBLE_MENU_ITEMS = 10;
    private int visIndex = 0; // starting visible index
    private int iWidth = 0;
    private boolean bScroll = false;
    private int maxVisibleItems=MAX_VISIBLE_MENU_ITEMS;

    private ArrowButton btnUp = new ArrowButton(BasicArrowButton.NORTH);
    private ArrowButton btnDown = new ArrowButton(BasicArrowButton.SOUTH);
    private MenuKeys menuKeys = new MenuKeys();

    /**
     * construct scrollable popup menu
     */
    public ScrollPopupMenu() {
        add(btnUp);
        add(btnDown);
        reset();
    }

    /**
     * @see javax.swing.JPopupMenu#show(java.awt.Component, int, int)
     */
    public void show(Component c, int x, int y) {
    	if (getMenuItemCount()>0) {
    		reset();
    		super.show(c,x,y);
    	}
    }

    /**
     * remove all menuitems and their actions
     */
    public void clearActions(){
    	while (getMenuItemCount() > 0) {
			Component c = getMenuItem(0);
			remove(c);
		}
        iWidth = 0;
    }

    public void remove(Component comp) {
    	if (comp instanceof JMenuItem){
    		JMenuItem jmi = (JMenuItem)comp;
    		EACM.closeMenuItem(jmi);
    	}

    	super.remove(comp);
    }

    /**
     * reset scroll to item 0
     */
    private void reset() {
        visIndex = 0;
        adjustBtnIndex(0);
    }

    /**
     * @see javax.swing.JPopupMenu#add(javax.swing.JMenuItem)
     */
    public JMenuItem add(JMenuItem item) {
        int i = getMenuItemCount();
        JMenuItem out = (JMenuItem)super.add(item,(i+1));//put it just before the up button
        iWidth = Math.max(iWidth, (getWidth(out) + 20));

        out.addMenuKeyListener(menuKeys);
        return out;
    }

  
    /**
     * set the maximum number of visible menu items
     * @param mvi
     */
    public void setMaxVisibleItems(int mvi){
    	maxVisibleItems = mvi;
    }
    /**
     * getMenuItem
     * @param _i
     * @return
     */
    public Component getMenuItem(int i) {
      return getComponent(i+1);// get past first button
    }

    /**
     * getMenuItemCount
     * @return
     *
     */
    public int getMenuItemCount() {
        return getComponentCount()-2; // allow for buttons
    }

    /**
     * this is for the button and mouse control
     * @param i
     */
    private void adjustBtnIndex(int i) {
        visIndex += i;
        visIndex = Math.min(visIndex, (getMenuItemCount() - maxVisibleItems));
        visIndex = Math.max(0,visIndex);

        adjustIndex();
    }

    /**
     * this is used for the arrow key, it must support wrapping
     * @param i
     */
    private void adjustKeyIndex(int i) {
        visIndex += i;
        if (visIndex>(getMenuItemCount() - maxVisibleItems)){
        	// roll forward to first item
        	visIndex=0;
        }
        if (visIndex<0){
        	//rollback to last item
        	visIndex =getMenuItemCount() - maxVisibleItems;
        }

        adjustIndex();
    }

    private void adjustIndex() {
        int maxVis = visIndex + maxVisibleItems;
        for (int i=0;i<getMenuItemCount();++i) {
        	JMenuItem item = (JMenuItem)getComponent(i+1);// get past first button
            item.setVisible(!(i < visIndex || i >= maxVis));
        }
        btnDown.setEnabled(maxVis != getMenuItemCount());
        btnUp.setEnabled(visIndex != 0);
    }

    /**
     * release memory
     */
    public void dereference() {
        btnUp.dereference();
        btnUp = null;
        btnDown.dereference();
        btnDown = null;
        clearActions();

       	PopupMenuListener[] pml = getPopupMenuListeners();
       	for (int ii=0; ii<pml.length; ii++){
       		removePopupMenuListener(pml[ii]);
       	}

        removeAll();

        setUI(null);
        menuKeys = null;
    }

    /**
     * @see java.awt.Component#getPreferredSize()
     */
    public Dimension getPreferredSize() {
        Dimension out = super.getPreferredSize();
        out.setSize(Math.min(iWidth,500),out.height);
        return out;
    }

    /**
     * get Width needed for this item
     * @param item
     * @return
     */
    private int getWidth(JMenuItem item) {
        if (item != null) {
            FontMetrics fm = getFontMetrics(getFont());
            return fm.stringWidth(item.getText()) + fm.getAscent() + fm.getDescent();
        }
        return 10;
    }

    private class ArrowButton extends BasicArrowButton implements MouseListener {
        private static final long serialVersionUID = 1L;
    	ArrowButton(int dir){
    		super(dir);
    	  	addMouseListener(this);
    	  	setRequestFocusEnabled(false);
    	}

        public boolean isVisible() {
            return getMenuItemCount() >= maxVisibleItems;
        }
        void dereference(){
        	removeMouseListener(this);
        	removeAll();
        	setUI(null);
        }

        public void mouseEntered(MouseEvent _me) {}
        public void mouseExited(MouseEvent _me) {}
        public void mouseClicked(MouseEvent _me) {}

        /**
         * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
         */
        public void mousePressed(MouseEvent _me) {
            Object source = _me.getSource();
            bScroll = true;
            SwingWorker<?, ?> worker = null;
            if (source == btnUp) {
                worker = new ScrollWorker(btnUp,-1);
            } else if (source == btnDown) {
                worker = new ScrollWorker(btnDown,1);
            }
            worker.execute();
        }

        /**
         * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
         */
        public void mouseReleased(MouseEvent _me) {
            bScroll = false;
        }
    }

	private class ScrollWorker extends SwingWorker<Void, Void> {
		private JButton btn = null;
		private int inc=0;
		ScrollWorker(JButton _btn, int _i){
			btn=_btn;
			inc = _i;
		}
		@Override
		public Void doInBackground() {
			try{
				while(btn.isEnabled() && bScroll) {
					adjustBtnIndex(inc);
					Thread.sleep(200);
				}
				return null;

			}catch(Exception ex){ // prevent hang
    			Logger.getLogger(APP_PKG_NAME).log(Level.SEVERE,"Exception ",ex);
				bScroll = false;
				btn = null;
			}
			return null;
		}
		@Override
		public void done() {
			//this will be on the dispatch thread
			bScroll = false;
			btn = null;
		}
	}

	private class MenuKeys implements MenuKeyListener{
		public void menuKeyReleased(MenuKeyEvent e) {}
		public void menuKeyTyped(MenuKeyEvent e) {}

		public void menuKeyPressed(MenuKeyEvent e) {
			MenuElement[] path = e.getPath();
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				pathloop:for(int i=0; i<path.length ;i++){
					MenuElement me = (MenuElement) path[i];
					if(me instanceof JMenuItem) {
						JMenuItem jmi = (JMenuItem)me;
						if(jmi.isArmed()){
							for (int x=0; x<getComponentCount(); x++){
								if (getComponent(x) instanceof JMenuItem){
									JMenuItem comp = (JMenuItem)getComponent(x);
									if (jmi ==comp){
										if (x==visIndex+1){
											adjustKeyIndex(-1);
											break pathloop;
										}
									}
								}
							}
						}
					}
				}
			}else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				pathloop:for(int i=0; i<path.length ;i++){
					MenuElement me = (MenuElement) path[i];
					if(me instanceof JMenuItem) {
						JMenuItem jmi = (JMenuItem)me;
						if(jmi.isArmed()){
							for (int x=0; x<ScrollPopupMenu.this.getComponentCount(); x++){
								if (getComponent(x) instanceof JMenuItem){
									JMenuItem comp = (JMenuItem)getComponent(x);
									if (jmi ==comp){
										if (x==(visIndex+maxVisibleItems)){
											adjustKeyIndex(1);
											break pathloop;
										}
									}
								}
							}
						}
					}
				}
			}

		}
	}
}
