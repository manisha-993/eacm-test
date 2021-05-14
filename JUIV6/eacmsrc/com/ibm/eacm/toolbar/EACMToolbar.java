//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.toolbar;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
import java.util.Hashtable;

import javax.swing.*;

import com.ibm.eacm.*;
import com.ibm.eacm.actions.*;
import com.ibm.eacm.edit.RecordToggle;
import com.ibm.eacm.objects.EACMGlobals;

/**
 * toolbar used in EACM
 * @author Wendy Stimpson
 */
//$Log: EACMToolbar.java,v $
//Revision 1.2  2015/03/13 18:30:55  stimpsow
//allow alt+f4 to get to the frame, toolbar was grabbing it
//
//Revision 1.1  2012/09/27 19:39:25  wendy
//Initial code
//
public class EACMToolbar extends JToolBar implements EACMGlobals
{
	private static final long serialVersionUID = 1L;

	private KeyListener keyListener = null;
	private String alignment = null;
    private int recordtoggleId=-1;

	private RecordToggle recToggle = null;

	/**
	 * used for matrix, navigate, lock, query, whereused and restore
	 * @param bar
	 * @param actionTbl
	 */
	public EACMToolbar(ToolbarLayout bar, Hashtable<String, EACMAction> actionTbl) {
		super(bar.getStringKey());

		setRollover(true);

		// some LNF dont support enter key press to activate, enforce it
		keyListener = new KeyAdapter(){
		    /**
		     * Invoked when a key has been pressed.
		     */
		    public void keyPressed(KeyEvent e) {
		    	if(e.getKeyCode()==KeyEvent.VK_ENTER){
		    		if(e.getSource() instanceof JButton){
		    			JButton jb = (JButton)e.getSource();
		    			jb.doClick();
		    			e.consume();
		    		}
		    	}
		    }
		};

		add(bar.getToolbarItems(),actionTbl);
		setFloatable(bar.isFloatable());
		alignment = bar.getAlignmentString();
		setOrientation(bar.getOrientationInt());
	}

    /**
     * used for editors
     * @param bar
     * @param obj
     * @param actionTbl
     */
    public EACMToolbar(ToolbarLayout bar, RecordToggle obj,
    		Hashtable<String, EACMAction> actionTbl)
    {
		super(bar.getStringKey());
		recToggle = obj;
		setRollover(true);

		// some LNF dont support enter key press to activate, enforce it
		keyListener = new KeyAdapter(){
		    /**
		     * Invoked when a key has been pressed.
		     */
		    public void keyPressed(KeyEvent e) {
		    	if(e.getKeyCode()==KeyEvent.VK_ENTER){
		    		if(e.getSource() instanceof JButton){
		    			JButton jb = (JButton)e.getSource();
		    			jb.doClick();
		    			e.consume();
		    		}
		    	}
		    }
		};
		add(bar.getToolbarItems(),actionTbl);
		setFloatable(bar.isFloatable());
		alignment = bar.getAlignmentString();

		if(recToggle!=null){
			addPropertyChangeListener("orientation",recToggle);
			recToggle.setOrientation(bar.getOrientationInt());
		}
		setOrientation(bar.getOrientationInt());

	}

    /**
     * RecordToggle is shared across Vertical and Form toolbars, it must get transferred
     */
    public void restoreRecordToggle(){
    	if(recToggle!=null){
    		for (int i=0; i<getComponentCount(); i++)	{
    			Component comp = getComponent(i);
    			if(comp == recToggle){
    				return;
    			}
    		}
    		recToggle.setOrientation(this.getOrientation());
    		add(recToggle, recordtoggleId);
    	}
    }
	/**
     * add
     * @param item
     */
    private void add(ToolbarItem[] itemArray, Hashtable<String, EACMAction> actionTbl) {
    	for (int i=0;i<itemArray.length;++i) {
    		ToolbarItem item = itemArray[i];
    		if (item.equals(ToolbarItem.SEPARATOR)) {
    			addSeparator();
    		}else if (item.getType()==ToolbarItem.ACTION_TYPE){
    			EACMAction act = actionTbl.get(item.getActionName());
    			if (act==null){//look in global table
    				 act = EACM.getEACM().getGlobalAction(item.getActionName());
    			}

    			JButton jb = add(act);
    			jb.addKeyListener(keyListener);
    		}
    		else if (item.getType() == ToolbarItem.COMPONENT_TYPE) {
    			if (recToggle !=null) {
    				recordtoggleId = i;
    				add(recToggle);
    			}
    		}
    	}

    	EACMAction breakAction = EACM.getEACM().getGlobalAction(BREAK_ACTION);
    	addSeparator();
    	JButton jb = add(breakAction);
    	jb.addKeyListener(keyListener);

    	Dimension forcedDim = UIManager.getDimension(TOOLBAR_BUTTON_DIM);
    	for (int i=0; i<getComponentCount(); i++)	{
    		Component comp = getComponent(i);
    		// everytime a button is pressed, focus will be returned to the dialog
    		if (comp instanceof JButton) {
    			((JButton)comp).setRequestFocusEnabled(false);
    			// preferred sizes and max sizes are different if this control is
    			// part of a dialog.. force them to be the same
    			((JButton)comp).setPreferredSize(forcedDim);
    			((JButton)comp).setMaximumSize(forcedDim);
    		}
    	}

    	forcedDim = null;
    }

    /* (non-Javadoc)
     * need to copy this method to override the button keybinding
     * @see javax.swing.JToolBar#createActionComponent(javax.swing.Action)
     */
    protected JButton createActionComponent(Action a) {
        JButton b = new JButton() {
			private static final long serialVersionUID = 1L;
			protected PropertyChangeListener createActionPropertyChangeListener(Action a) {
                PropertyChangeListener pcl = createActionChangeListener(this);
                if (pcl==null) {
                    pcl = super.createActionPropertyChangeListener(a);
                }
                return pcl;
            }
    		/* (non-Javadoc)
    		 * keystrokes are going to the toolbar buttons, ALT-F4 was not going to the frame
    		 * @see javax.swing.JComponent#processKeyBinding(javax.swing.KeyStroke, java.awt.event.KeyEvent, int, boolean)
    		 */
    		protected boolean processKeyBinding(KeyStroke ks, KeyEvent e, int condition, boolean pressed) {
              	if(e.getKeyCode()==KeyEvent.VK_ALT || e.getKeyCode()==KeyEvent.VK_F4){
                	return false;
            	}
    			return super.processKeyBinding(ks, e, condition, pressed); 
    		}
        };
        if (a != null && (a.getValue(Action.SMALL_ICON) != null ||
                          a.getValue(Action.LARGE_ICON_KEY) != null)) {
            b.setHideActionText(true);
        }
        b.setHorizontalTextPosition(JButton.CENTER);
        b.setVerticalTextPosition(JButton.BOTTOM);
        return b;
    }
    
	/**
     * getAlignment
     * @return
     */
    public String getAlignment() {
		return alignment;
	}


    public void updateActions(Hashtable<String, EACMAction> actionTbl){
		for (int i=0; i<getComponentCount(); i++){
			Component comp = getComponent(i);
			if (comp instanceof JButton){
				JButton button = ((JButton)comp);
				EACMAction act = (EACMAction)button.getAction();
				if (act==null){
					continue;
				}
				String actionkey = act.getActionKey();
				act = actionTbl.get(actionkey);
				if(act !=null){
					button.setAction(act);
				}
			}
		}
	}

	/**
     * setVisible
     * @param key
     * @param b
     */
    public void setVisible(String key, boolean b){
		for (int i=0; i<getComponentCount(); i++){
			Component comp = getComponent(i);
			if (comp instanceof JButton){
				EACMAction act = (EACMAction)((JButton)comp).getAction();
				if (act==null){
					continue;
				}
				if(act.getActionKey().equals(key)){
					comp.setVisible(b);
					break;
				}
			}
		}
	}

	/**
     * dereference
     */
    public void dereference() {
    	setUI(null);

		for (int i=0; i<getComponentCount(); i++){
			Component comp = getComponent(i);
			if (comp instanceof JButton){
				((JButton)comp).setAction(null);
				((JButton)comp).removeKeyListener(keyListener);
			}
		}

    	keyListener = null;

		if(recToggle!=null){
			removePropertyChangeListener("orientation",recToggle);
			recToggle = null;
		}

		alignment = null;
		removeAll();
	}

}
