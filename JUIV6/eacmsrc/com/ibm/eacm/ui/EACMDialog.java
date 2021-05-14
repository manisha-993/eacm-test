//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.ui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyEvent;

import com.ibm.eacm.*;
import com.ibm.eacm.actions.*;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.objects.EACMGlobals;

import java.awt.event.WindowListener;
import java.util.Enumeration;
import java.util.Hashtable;


/******************************************************************************
* This is used to display EACM dialogs
* @author Wendy Stimpson
*/
// $Log: EACMDialog.java,v $
// Revision 1.2  2013/07/18 18:39:29  wendy
// fix compiler warnings
//
// Revision 1.1  2012/09/27 19:39:11  wendy
// Initial code
//

public abstract class EACMDialog extends JDialog implements EACMGlobals
{
	private static final long serialVersionUID = 1L;
	/** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2012  All Rights Reserved.";
    /** cvs version */
    public static final String VERSION = "$Revision: 1.2 $";
    protected CloseDialogAction closeAction = null;
    private Hashtable<String, EACMAction> actionTbl = new Hashtable<String, EACMAction>();
    protected JMenuBar menubar = null;
    protected JToolBar tBar = null;

    /*
     * <code>MODELESS</code> dialog doesn't block any top-level windows.
     *
        MODELESS,
     **
     * A <code>DOCUMENT_MODAL</code> dialog blocks input to all top-level windows
     * from the same document except those from its own child hierarchy.
     * A document is a top-level window without an owner. It may contain child
     * windows that, together with the top-level window are treated as a single
     * solid document. Since every top-level window must belong to some
     * document, its root can be found as the top-nearest window without an owner.
     *
        DOCUMENT_MODAL,
     **
     * An <code>APPLICATION_MODAL</code> dialog blocks all top-level windows
     * from the same Java application except those from its own child hierarchy.
     * If there are several applets launched in a browser, they can be
     * treated either as separate applications or a single one. This behavior
     * is implementation-dependent.
     *
        APPLICATION_MODAL, - blocks all
     **
     * A <code>TOOLKIT_MODAL</code> dialog blocks all top-level windows run
     * from the same toolkit except those from its own child hierarchy. If there
     * are several applets launched in a browser, all of them run with the same
     * toolkit; thus, a toolkit-modal dialog displayed by an applet may affect
     * other applets and all windows of the browser instance which embeds the
     * Java runtime environment for this toolkit.
     * Special <code>AWTPermission</code> "toolkitModality" must be granted to use
     * toolkit-modal dialogs. If a <code>TOOLKIT_MODAL</code> dialog is being created
     * and this permission is not granted, a <code>SecurityException</code> will be
     * thrown, and no dialog will be created. If a modality type is being changed
     * to <code>TOOLKIT_MODAL</code> and this permission is not granted, a
     * <code>SecurityException</code> will be thrown, and the modality type will
     * be left unchanged.
     *
        TOOLKIT_MODAL - blocks everything
     * Default modality type for modal dialogs. The default modality type is
     * <code>APPLICATION_MODAL</code>. Calling the oldstyle <code>setModal(true)</code>
     * is equal to <code>setModalityType(DEFAULT_MODALITY_TYPE)</code>.
     */

    /**
     * @param owner
     * @param title
     * @param modalityType
     */
    public EACMDialog(Window owner, String title, JDialog.ModalityType modalityType)  {
        super(owner,Utils.getResource(title),modalityType);
        EACM.getEACM().addUserWindow(this);
    }

    protected void addAction(EACMAction act){
    	actionTbl.put(act.getActionKey(), act);
    }
    protected EACMAction getAction(String key){
    	return actionTbl.get(key);
    }
    protected void enableActions(){
    	for (Enumeration<EACMAction> e = actionTbl.elements(); e.hasMoreElements();){
    		EACMAction action = (EACMAction)e.nextElement();
    		action.setEnabled(true);
    	}
    }

    protected void disableActions(){
    	for (Enumeration<EACMAction> e = actionTbl.elements(); e.hasMoreElements();){
    		EACMAction action = (EACMAction)e.nextElement();
    		action.setEnabled(false);
    	}
    }
	/**
	 * called by actions when using workers
     */
	public void disableActionsAndWait(){
    	disableActions();
    	setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    }
	public void enableActionsAndRestore(){
		if(actionTbl!=null){ // can happen if frame is closed but worker was still running
			enableActions();
		}
		setCursor(Cursor.getDefaultCursor());
	}
    protected void finishSetup(Window owner){
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        if(closeAction==null){
        	closeAction = new CloseDialogAction(this);
        }
    	addWindowListener((WindowListener)closeAction);

       	if(tBar !=null){
    		tBar.setRollover(true);
    	}

        // allow escape to close dialog
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke((char)KeyEvent.VK_ESCAPE), closeAction.getActionKey());
        getRootPane().getActionMap().put(closeAction.getActionKey(), closeAction);

        pack();
        if (owner!=null){
        	setLocationRelativeTo(owner);
        }
        setSize(getPreferredSize());
        setResizable(false);
    }

    /**
     * release memory
     */
    public void dereference() {
     	closeMenuBar();

    	closeToolBar();

    	if (closeAction!=null){
    		removeWindowListener((WindowListener)closeAction);
    		closeAction.dereference();
    		closeAction = null;
    	}

    	if (actionTbl!=null){
    		// deref actions here actionTbl
    		for (Enumeration<EACMAction> e = actionTbl.elements(); e.hasMoreElements();){
    			EACMAction action = (EACMAction)e.nextElement();
    			action.dereference();
    		}
    		actionTbl.clear();
    		actionTbl = null;
    	}

    	removeAll();

        EACM.getEACM().removeUserWindow(this);
    }
    private void closeToolBar() {
    	if(tBar!=null){
    		for (int i=0; i<tBar.getComponentCount(); i++){
    			Component comp = tBar.getComponent(i);
    			if (comp instanceof JButton){
    				((JButton)comp).setAction(null);
    			}
    		}

    		tBar.removeAll();
    		tBar.setUI(null);
    		tBar = null;
    	}
    }

    private void closeMenuBar() {
    	if (menubar!=null){
    		for (int i=0; i<menubar.getMenuCount(); i++) {
    			JMenu amenu = menubar.getMenu(i);
    			EACM.closeMenu(amenu);
    		}
    		menubar.removeAll();
    		menubar.setUI(null);
    		menubar = null;
    	}
    }
}
