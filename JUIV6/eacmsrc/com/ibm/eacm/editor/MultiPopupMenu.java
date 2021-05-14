//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.editor;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.IllegalComponentStateException;
import java.awt.Point;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;

import com.ibm.eacm.EACM;

import COM.ibm.eannounce.objects.MetaFlag;
/**
 * this class is used to display a multiflag editor in a popupmenu
 * @author Wendy Stimpson
 */
//$Log: MultiPopupMenu.java,v $
//Revision 1.3  2014/02/10 21:36:51  wendy
//prevent hang if editor is not yet showing when popup is launched
//
//Revision 1.2  2014/01/14 19:22:08  wendy
//RCQ00288888 - accept value on loss of focus
//
//Revision 1.1  2012/09/27 19:39:21  wendy
//Initial code
//
public class MultiPopupMenu extends JPopupMenu implements MultiPopup, KeyListener, FocusListener {
	private static final long serialVersionUID = 1L;

	private MultiFlagCBList list = null;
	private JScrollPane scroll = null;
	private Dimension scrollSize = new Dimension(300,200);
	private MultiColHeader colHeader = null;
	
    private Action cancelAction = null;
    private Action acceptAction = null;
	/**
     * constructor
     */
    protected MultiPopupMenu(MultiEditor me, Action cnclAct, Action acceptAct) {
	    // allow key events to come to this component
	    enableEvents(AWTEvent.MOUSE_EVENT_MASK|AWTEvent.KEY_EVENT_MASK);
	    
	    list = new MultiFlagCBList(me,this);
		scroll = new JScrollPane(list);
		cancelAction = cnclAct;
		acceptAction = acceptAct;
		
		colHeader = new MultiColHeader(cnclAct,acceptAct);
		scroll.setColumnHeaderView(colHeader);
		
	    init();
	    
		addKeyListener(this);
		addFocusListener(this); 
	}

    /* (non-Javadoc)
     * @see com.ibm.eacm.editor.MultiPopup#setLabelText(java.lang.String)
     */
    public void setLabelText(String _s){
    	colHeader.setLabelText(_s);
    }
    public String getLabelText(){
    	return colHeader.getLabelText();
    }
    /* (non-Javadoc)
     * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
     */
    public void keyPressed(KeyEvent _ke) {
    	int keyCode = _ke.getKeyCode();
    	if (keyCode == KeyEvent.VK_ENTER) {
    		acceptAction.actionPerformed(null);
    		return;
    	} 
    	if (keyCode == KeyEvent.VK_ESCAPE) {
    		cancelAction.actionPerformed(null);
    		return;
    	}
    	
    	list.keyPressed(_ke);
    }
    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {
    	list.keyTyped(e);
    }
	public void focusGained(FocusEvent e) {}

	/* (non-Javadoc)
	 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
	 */
	public void focusLost(FocusEvent e) {
	  	// call cancelaction to stop edit and close menu
	  	//cancelAction.actionPerformed(null); 
		//RCQ00288888 accept on loss of focus
	  	acceptAction.actionPerformed(null); 
	}


	private void init() {
	//	setFocusable(false);
		//setLightWeightPopupEnabled(true);
		setLayout(new BorderLayout());
		setBorderPainted(true);
		setBorder(BorderFactory.createLineBorder(Color.black));
		setOpaque(false);
		add(scroll,BorderLayout.NORTH);
		setDoubleBuffered(true);

		//setRequestFocusEnabled(false);
		scroll.setRequestFocusEnabled(false);
		scroll.getHorizontalScrollBar().setRequestFocusEnabled(false);
		scroll.getVerticalScrollBar().setRequestFocusEnabled(false);

		scroll.setSize(scrollSize);
		scroll.setPreferredSize(scrollSize);
		scroll.getViewport().setScrollMode(JViewport.BLIT_SCROLL_MODE);		
	}

    /* (non-Javadoc)
     * @see com.ibm.eacm.editor.MultiPopup#setDescription(java.lang.String)
     */
    public void setDescription(String desc) {
    	 colHeader.setDescription(desc);
	}

    /* (non-Javadoc)
     * @see com.ibm.eacm.editor.MultiPopup#loadMetaFlags(COM.ibm.eannounce.objects.MetaFlag[])
     */
    public void loadMetaFlags(MetaFlag[] mf) {
		list.loadFlags(mf);
	}
    /* (non-Javadoc)
     * return the flags, selection is held in the flag
     * to the editor so that it can update the RowSelectableTable
     * @see com.ibm.eacm.editor.MultiPopup#getMetaFlags()
     */
    public MetaFlag[] getMetaFlags() {
		return list.getMetaFlags();
	}

    /* (non-Javadoc)
     * @see com.ibm.eacm.editor.MultiPopup#dereference()
     */
    public void dereference() {
    	removeFocusListener(this);
    	removeKeyListener(this);
    	
		scroll.removeAll();
		scroll.setUI(null);
		scroll = null;

		scrollSize = null;
		
		colHeader.dereference();
		colHeader = null;

		cancelAction = null;
		acceptAction = null;
		
		list.dereference();
		list=null;
		
		removeAll();
		setUI(null);
	}

    /* (non-Javadoc)
     * @see com.ibm.eacm.editor.MultiPopup#hidePopup()
     */
    public void hidePopup() {
		super.setVisible(false);
	}

    /* (non-Javadoc)
     * @see com.ibm.eacm.editor.MultiPopup#processPopupKey(java.awt.event.KeyEvent)
     */
    public void processPopupKey(KeyEvent _ke) {
		if (_ke != null) {
			list.scrollToCharacter(_ke.getKeyChar());
		}
	}
    
    /* (non-Javadoc)
     * @see com.ibm.eacm.editor.MultiPopup#showPopup(java.awt.Component)
     */
    public void showPopup(Component editor){
    	if(this.isShowing()){
    		return;
    	}
    	Point pt = getPopupPosition(editor);
    	super.show(editor, pt.x, pt.y);
		requestFocus();// this is needed or popup doesnt get focus requestfocusinwindow doesnt do it (esc closes searchframe)
    }
    
    /**
     * @param editor
     * @return
     */
    private Point getPopupPosition(Component editor) {
        Dimension pSize = scrollSize;
        Dimension sSize = EACM.getEACM().getSize();
        Dimension mSize = editor.getSize();

        Point pt = null;
        try {
        	//if editor is not showing on the screen yet, it will get this exception
            pt = editor.getLocationOnScreen();
        } catch (IllegalComponentStateException icse) {
        	pt = new Point(editor.getX(),editor.getY());
        	Logger.getLogger(EACM.APP_PKG_NAME).log(Level.SEVERE, "Unable to get editor location, using "+pt);
        }
        Point relPt = EACM.getEACM().getLocation();
        pt.setLocation(getX(pt.x, mSize.width, pSize.width, sSize.width, relPt.x), 
        		getY(pt.y, mSize.height, pSize.height, sSize.height, relPt.y));
        SwingUtilities.convertPointFromScreen(pt, editor);
        return pt;
    }

    private int getX(int _x, int _myWidth, int _popupWidth, int _frameWidth, int _relX) {
        if ((_x + _popupWidth) >= (_frameWidth + _relX)) {
            return _x - (_popupWidth - _myWidth);
        } else {
            return _x;
        }
    }

    private int getY(int _y, int _myHeight, int _popupHeight, int _frameHeight, int _relY) {
        if ((_y + _popupHeight + _myHeight) > (_frameHeight + _relY - 20)) {
            return _y - (_popupHeight - _myHeight);
        } else {
            return _y;
        }
    }

}
