//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.editor;


import java.awt.*;

import java.awt.event.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.EventObject;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;

import javax.swing.JPanel;
import javax.swing.JTable;

import COM.ibm.eannounce.objects.BlobAttribute;
import COM.ibm.eannounce.objects.EANAttribute;
import COM.ibm.eannounce.objects.EANBlobAttribute;
import COM.ibm.eannounce.objects.EntityItem;

import COM.ibm.opicmpdh.transactions.OPICMBlobValue;


import com.ibm.eacm.EACM;
import com.ibm.eacm.edit.EditController;
import com.ibm.eacm.edit.form.FormCellPanel;
import com.ibm.eacm.edit.form.FormTable;
import com.ibm.eacm.mw.DBSwingWorker;
import com.ibm.eacm.mw.DBUtils;
import com.ibm.eacm.mw.RMIMgr;
import com.ibm.eacm.objects.Routines;
import com.ibm.eacm.objects.ShellControl;
import com.ibm.eacm.objects.Utils;

/**
 * Blob editor
 * @author Wendy Stimpson
 */
//$Log: BlobCellEditor.java,v $
//Revision 1.2  2014/10/20 19:56:08  wendy
//Add worker id to timing log output
//
//Revision 1.1  2012/09/27 19:39:21  wendy
//Initial code
//
public class BlobCellEditor extends AttrCellEditor  {
	private static final long serialVersionUID = 1L;

	public static final String NOBLOB_UPDATE="NOBLOB_UPDATE";

	private BlobEditor blobEditor = null;
	private AttachAction attachAction = null;
	private DetachAction detachAction = null;
	private LaunchAction launchAction = null;
	private OPICMBlobValue m_newBlob = null;
	private Location blobLoc = null;
	private boolean editorOpening = false; // if editor opened with mouseclick, dont activate button immediately
	private static int blobCounter = 0;
	private EditController editController = null;
	private ActionListener actListener = null;
	private JComponent comp=null;
	
	//==========added for formeditor
	public FormCellPanel getFormCellPanel(){// added for formeditor
		if(blobEditor !=null){
			return blobEditor.getFormCellPanel();
		}
		return null;
	}
	public Color getBackground() { 
		return blobEditor.getBackground(); 
	}
	public void setForeground(Color fg) {
		blobEditor.setForeground(fg);
	}
	public void setFont(Font font) {
		blobEditor.setFont(font);
	}
    public void setOpaque(boolean isOpaque) {
    	blobEditor.setOpaque(isOpaque);
    }
    public void updateAttachAction(){
    	//needed when switching from renderer mode to editor mode
    	attachAction.setEnabled(isCellEditable());
    }
	/**
	 * constructor
	 * @param f
	 * @param ec
	 */
	public BlobCellEditor(FormCellPanel f ,EditController ec){
		super(false);

		init();
		blobEditor.setFormCellPanel(f);
		editController = ec;
		comp = f; // used for messages
	}
	//========== end added for formeditor
	/**
	 * constructor
	 * @param ec
	 * @param msgcomp
	 */
	public BlobCellEditor(EditController ec, JComponent msgcomp){
		super(false);

		init();
		editController = ec;
		comp = msgcomp;
	}
	private void init(){
		attachAction = new AttachAction();
		attachAction.setEnabled(isCellEditable());
		detachAction = new DetachAction();
		launchAction = new LaunchAction();

		blobEditor = new BlobEditor();
	}
    /**
     * hang onto the action listener, add it after attribute is set
     * @param dl
     */
    public void addActionListener(ActionListener dl){
    	actListener = dl;
    }
    
	/**
	 * use editor rules when pasting
	 * @param pasteObj  
	 * @return
	 */
	public boolean paste(Object pasteObj,boolean editOpen){
		return false;
	}

	/**
	 * does this cell have the lock - need to override lock check for editors that can be view only
	 * @return
	 */
	protected boolean isCellLocked(){
		return true;
	}
	/**
	 * called if the user cancels the edit, should hide the dialog here
	 * @see javax.swing.AbstractCellEditor#cancelCellEditing()
	 */
	public void cancelCellEditing() { 
		m_newBlob = null;
		if(blobLoc!=null){
			blobLoc.dereference();
			blobLoc = null;
		}

		super.cancelCellEditing(); 
	}
	/**
	 * called if the user clicks on a different cell, should hide the dialog here
	 * returning true tells the table to use the partially edited value, that it is valid
	 * @see javax.swing.AbstractCellEditor#stopCellEditing()
	 */
	public boolean stopCellEditing() { 
		if (blobLoc != null) { //user attached a new blob
			try {
				m_newBlob.loadFromFile(blobLoc.getLocation()); // put bytes into the blob object
				blobLoc.dereference();
				blobLoc = null;
			} catch (IOException x) {
				x.printStackTrace();
				// "msg3015b"=File {0} does not exist, please update your file reference.
				com.ibm.eacm.ui.UI.showErrorMessage(null,Utils.getResource("msg3015b", blobLoc.getLocation()));
				return false;						
			}
		}else{
			m_newBlob = null; // reset this, it is not new if user detached or launched a blob
		}

		return super.stopCellEditing(); 
	}

	/**
	 * called after the editor component is installed, initiate edit here, like show a dialog
	 * not sure if this is still called, from JTable.editCellAt()
	 * 					note that as of Java 2 platform v1.2, the call to
	 *                  <code>shouldSelectCell</code> is no longer made
	 * use it to pass the keybd event to the editor..
	 * at com.ibm.eacm.editor.FlagCellEditor.shouldSelectCell(FlagCellEditor.java:71)
	at javax.swing.plaf.basic.BasicTableUI$Handler.adjustSelection(BasicTableUI.java:1101)
	at javax.swing.plaf.basic.BasicTableUI$Handler.mousePressed(BasicTableUI.java:1025)
	 * @see javax.swing.AbstractCellEditor#shouldSelectCell(java.util.EventObject)
	 */
	public boolean shouldSelectCell(EventObject anEvent) { 
		attachAction.setEnabled(isCellEditable()); // may be view only
		if(attachAction.isEnabled()){ // if this is disabled, the event is not forwarded to it
			editorOpening = true;
		}
		return true; 
	}

	/**
	 * determine background color based on meta requirements
	 * @param _s
	 * @return
	 */
	protected Color getBackground(String _s) {
		if(!this.isCellEditable()){
			return Color.WHITE;
		}
		return super.getBackground(_s);
	}
	/**
	 * return the edited value
	 * @see javax.swing.CellEditor#getCellEditorValue()
	 */
	public Object getCellEditorValue() {
		if (m_newBlob==null){ // returning null deactivates the blob attribute
			return NOBLOB_UPDATE;
		}

		return m_newBlob;
	}
	/* (non-Javadoc)
	 * return the component for use in joptionpane
	 * @see com.ibm.eacm.editor.AttrCellEditor#getComponent()
	 */
	public Component getComponent() {
		return blobEditor;
	}

	/**
	 * return a component to edit the cell, the editor completely replaces the renderer when editing is in progress
	 * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
	 */
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		//set the value in the editor here
		if (value instanceof BlobAttribute) {
			setAttribute((BlobAttribute) value);
		}
		return blobEditor;
	} 
	/**
	 * set the attribute to use for this editor execution
	 * @param _ean
	 */
	public void setAttribute(EANAttribute _ean) {
		super.setAttribute(_ean);
		m_newBlob = null; 
		if(blobLoc!=null){
			blobLoc.dereference();
			blobLoc = null;
		}
	}
	/**
	 * release memory
	 */
	public void dereference(){
		m_newBlob = null;
		if(blobLoc!=null){
			blobLoc.dereference();
			blobLoc = null;
		}
		
		comp = null;

		actListener = null;
		
		attachAction.dereference();
		attachAction = null;

		detachAction.dereference();
		detachAction = null;

		launchAction.dereference();
		detachAction = null;

		blobEditor.dereference();
		blobEditor = null;
		editController = null;

		super.dereference();
	}

	/**
	 * this is the blob editor, it is a panel of 3 buttons to attach, detach or launch a blob
	 */
	private class BlobEditor extends JPanel implements KeyListener,EditorComponent 
	{
		private static final long serialVersionUID = 1L;
		private JButton attachButton = null;
		private JButton detachButton = null;
		private JButton launchButton = null;
		private FocusListener fl = null;
		
		//==========added for formeditor
		private FormCellPanel fcp = null; // added for formeditor
		/**
		 * @param f
		 */
		public void setFormCellPanel(FormCellPanel f) {
			fcp=f;
		}
		/* (non-Javadoc)
		 * @see com.ibm.eacm.editor.EditorComponent#getFormCellPanel()
		 */
		public FormCellPanel getFormCellPanel() { 
			return fcp;
		}
	    /* (non-Javadoc)
	     * this is needed so a double click on button when disabled can get the lock
	     * @see java.awt.Component#addMouseListener(java.awt.event.MouseListener)
	     */
	    public synchronized void addMouseListener(MouseListener l) {
	    	detachButton.addMouseListener(l);
	    	attachButton.addMouseListener(l);
	    	launchButton.addMouseListener(l);
	    }
	    /* (non-Javadoc)
	     * @see java.awt.Component#removeMouseListener(java.awt.event.MouseListener)
	     */
	    public synchronized void removeMouseListener(MouseListener l) {
	    	detachButton.removeMouseListener(l);
	    	attachButton.removeMouseListener(l);
	    	launchButton.removeMouseListener(l);
	    }
	    /* (non-Javadoc)
	     * needed to recognize keystroke traversal
	     * @see java.awt.Component#addFocusListener(java.awt.event.FocusListener)
	     */
	    public synchronized void addFocusListener(FocusListener l) {
	    	attachButton.addFocusListener(l);
	    	detachButton.addFocusListener(l);
	    	launchButton.addFocusListener(l);
	    }
	    public synchronized void removeFocusListener(FocusListener l) {
	    	attachButton.removeFocusListener(l);
	    	detachButton.removeFocusListener(l);
	    	launchButton.removeFocusListener(l);
	    }
		//==========end added for formeditor
		
		BlobEditor(){
			super(new GridLayout(1, 3, 1, 0));
			attachButton = new JButton(attachAction){
				private static final long serialVersionUID = 1L;

				/* (non-Javadoc)
				 * use meta to define color
				 * @see java.awt.Component#getBackground()
				 */
				public Color getBackground() {
					return BlobCellEditor.this.getBackground("");
				} 		
			};
					
			add(attachButton);
			// this is needed or the mnemonic doesnt activate
			attachButton.setMnemonic((char)((Integer)attachAction.getValue(Action.MNEMONIC_KEY)).intValue());

			detachButton= new JButton(detachAction){
				private static final long serialVersionUID = 1L;

				/* (non-Javadoc)
				 * use meta to define color
				 * @see java.awt.Component#getBackground()
				 */
				public Color getBackground() {
					return BlobCellEditor.this.getBackground("");
				} 		
			};
			add(detachButton);
			// this is needed or the mnemonic doesnt activate
			detachButton.setMnemonic((char)((Integer)detachAction.getValue(Action.MNEMONIC_KEY)).intValue());

			launchButton = new JButton(launchAction){
				private static final long serialVersionUID = 1L;

				/* (non-Javadoc)
				 * use meta to define color
				 * @see java.awt.Component#getBackground()
				 */
				public Color getBackground() {
					return BlobCellEditor.this.getBackground("");
				} 		
			};
			add(launchButton);
			// this is needed or the mnemonic doesnt activate
			launchButton.setMnemonic((char)((Integer)launchAction.getValue(Action.MNEMONIC_KEY)).intValue());

			//add focuslistener to give first button focus
			fl = new FocusAdapter(){
			    public void focusGained(FocusEvent e) {
			    	if(!attachButton.hasFocus()){
			    		attachButton.requestFocusInWindow();
			    	}
			    }
			};

			super.addFocusListener(fl);
			attachButton.addKeyListener(this);
			detachButton.addKeyListener(this);
			launchButton.addKeyListener(this);
		}

		/**
		 * release memory
		 */
		void dereference(){
			fcp = null;

			super.removeFocusListener(fl);
			fl = null;
			
			attachButton.removeKeyListener(this);
			detachButton.removeKeyListener(this);
			launchButton.removeKeyListener(this);

			attachButton.setAction(null);
			attachButton.setUI(null);
			attachButton = null;

			detachButton.setAction(null);
			detachButton.setUI(null);
			detachButton = null;

			launchButton.setAction(null);
			launchButton.setUI(null);
			launchButton = null;
		}

		/* (non-Javadoc)
		 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
		 */
		public void keyPressed(KeyEvent e) {
			editorOpening = false;
			int keyCode = e.getKeyCode();
			// allow escape to close the editor
			if (keyCode == KeyEvent.VK_ESCAPE) {
				cancelCellEditing();
				e.consume();
			}
			// allow enter to activate the button
			if (keyCode == KeyEvent.VK_ENTER) {
				if(attachButton.hasFocus()){
					attachButton.doClick();
				}
				if(detachButton.hasFocus()){
					detachButton.doClick();
				}
				if(launchButton.hasFocus()){
					launchButton.doClick();
				}
				e.consume();
			} 
			// allow user to tab between the buttons
			if (keyCode == KeyEvent.VK_TAB) {
				if(attachButton.hasFocus()){
					if(e.isShiftDown()){
						launchButton.requestFocusInWindow();
					}else{
						detachButton.requestFocusInWindow();
					}
				}
				if(detachButton.hasFocus()){
					if(e.isShiftDown()){
						attachButton.requestFocusInWindow();
					}else{
						launchButton.requestFocusInWindow();
					}
				}
				if(launchButton.hasFocus()){
					if(e.isShiftDown()){
						detachButton.requestFocusInWindow();
					}else{
						attachButton.requestFocusInWindow();
					}
				}
		
				// notify the formtable that this has focus
				if(getFormCellPanel()!=null){
					getFormCellPanel().getFormTable().keyPressed(e);
				}					
				e.consume();
			} 
		}

		/* (non-Javadoc)
		 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
		 */
		public void keyReleased(KeyEvent e) {
			int keyCode = e.getKeyCode();
			if (keyCode == KeyEvent.VK_ENTER || keyCode == KeyEvent.VK_TAB || keyCode == KeyEvent.VK_ESCAPE) {
				e.consume();
			}	
		}

		/* (non-Javadoc)
		 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
		 */
		public void keyTyped(KeyEvent e) {}
	}

	/**
	 * this action uses a file dialog to specify the file to use for the blob
	 */
	private class AttachAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private static final String NAME="atch";
		private static final int MAX_BLOB_SIZE = 10000000; // 10MB
		AttachAction(){
			super(Utils.getResource(NAME));
			String value = Utils.getToolTip(NAME);
			if (value!=null){
				putValue(Action.SHORT_DESCRIPTION, value);
			}
			putValue(Action.MNEMONIC_KEY, new Integer(Utils.getMnemonic(NAME)));
		}
		
		/* (non-Javadoc)
		 * attach the blob 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			if(editorOpening){
				editorOpening = false;
				return;
			}

			// this is needed if user was editing a different control and left a invalid value
			if(getFormCellPanel()!=null){
				FormTable ft = getFormCellPanel().getFormTable();
				// make sure it is not stopping this editor
				if(ft.getCurrentEditor()!=getFormCellPanel()){
					if(!ft.canStopEditing()){
						return; 
					}
				}
			}
			// open filedialog to get filename and directory to load blob from
			FileDialog fd = new FileDialog(EACM.getEACM(), Utils.getResource("atch-l"), FileDialog.LOAD);
			fd.setVisible(true);

			String dir = fd.getDirectory();
			String file = fd.getFile();
			fd.dispose();

			if (dir != null && file != null) { //user did not cancel
				File f = new File(dir+file);	
				if (f.exists()) {				
					if (f.length() < MAX_BLOB_SIZE) {
						// create a new blob attr but do not load the bytes yet
						m_newBlob = new OPICMBlobValue(attr.getNLSID(), null, null);
						blobLoc = new Location(dir, file);
						m_newBlob.setBlobExtension(Routines.truncateFilename(30, file));
						if(actListener != null){
							// notify table listener that values have changed
							actListener.actionPerformed(null);
						}
					} else {
						//"msg3015"=Blobs must be under 10MB to be stored in the PDH.
						com.ibm.eacm.ui.UI.showErrorMessage(null,Utils.getResource("msg3015"));
					}
				} else {				
					// "msg3015a" = Selected Blob File {0} does not exist.
					com.ibm.eacm.ui.UI.showErrorMessage(null,Utils.getResource("msg3015a",dir+file));
				}
			}
		}
		/**
		 * release memory
		 */
		void dereference(){
			putValue(Action.NAME, null);
			putValue(Action.SHORT_DESCRIPTION, null);
			putValue(Action.SMALL_ICON,null);
			putValue(Action.MNEMONIC_KEY,null);
		}
	}

	/**
	 * this class uses a file dialog to determine the location to store the blob bytes from the pdh
	 * it download the bytes and writes the file
	 */
	private class DetachAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private static final String NAME="dtch";
		DetachAction(){
			super(Utils.getResource(NAME));
			String value = Utils.getToolTip(NAME);
			if (value!=null){
				putValue(Action.SHORT_DESCRIPTION, value);
			}
			putValue(Action.MNEMONIC_KEY, new Integer(Utils.getMnemonic(NAME)));
		}
		public void actionPerformed(ActionEvent e) {
			if(editorOpening){
				editorOpening = false;
				return;
			}
			// this is needed if user was editing a different control and left a invalid value
			if(getFormCellPanel()!=null){
				FormTable ft = getFormCellPanel().getFormTable();
				// make sure it is not stopping this editor
				if(ft.getCurrentEditor()!=getFormCellPanel()){
					if(!ft.canStopEditing()){
						return; 
					}
				}
			}
			
			if (attr.isActive()) {
				String file = getBlobAttr().getBlobExtension(); // get existing file name
				if (!Routines.have(file)) { // if it doesnt have one yet, get it from the blob created in this session
					if (m_newBlob != null) { // user has attached blob data in this session
						file = m_newBlob.getBlobExtension();
					} 
					if (!Routines.have(file)){
						// "msg23041"= Unable to detach Blob at this time. Please try again later.
						com.ibm.eacm.ui.UI.showErrorMessage(null,Utils.getResource("msg23041"));
						return;
					}
				}

				FileDialog  fd = new FileDialog(((Frame)comp.getTopLevelAncestor()), Utils.getResource("dtch-l"), FileDialog.SAVE);
				fd.setFile(file);
				fd.setVisible(true);
				String dir = fd.getDirectory();
				file = fd.getFile();
				fd.dispose();
				if (dir != null && file != null) { //user did not cancel dialog
					disableActionsAndWait(); //disable all other actions and also set wait cursor
					detachWorker = new DetachWorker(dir + file);
					RMIMgr.getRmiMgr().execute(detachWorker);
				}
			} else {
				// "msg2004" = Blob does not exist for current attribute.
				com.ibm.eacm.ui.UI.showErrorMessage(null,Utils.getResource("msg2004"));
			}
		}
		/**
		 * release memory
		 */
		void dereference(){
			putValue(Action.NAME, null);
			putValue(Action.SHORT_DESCRIPTION, null);
			putValue(Action.SMALL_ICON,null);
			putValue(Action.MNEMONIC_KEY,null);
		}
	}
	private EANBlobAttribute getBlobAttr(){
		return (EANBlobAttribute) attr;
	}

	private DetachWorker detachWorker = null;
	private class DetachWorker extends DBSwingWorker<Void, Void> { 
		private long t11 = 0L;
		private String path = null;
		DetachWorker(String path2){
			path = path2;
		}
		@Override
		public Void doInBackground() {
			try{		
				t11 = System.currentTimeMillis();
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting "+getIdStr());
				OPICMBlobValue opbv = getBlobFromDatabase();
				if (opbv != null) {
					if (opbv.size() > 0) { // blob was in pdh
						opbv.saveToFile(path);
					} else if (blobLoc != null) { // blob was attached in this edit session
						blobLoc.copyToFile(path);
					} else {
						// "msg23041" =Unable to detach Blob at this time. Please try again later.
						com.ibm.eacm.ui.UI.showErrorMessage(null,Utils.getResource("msg23041"));
					}
				}
			}catch (IOException x) {
				com.ibm.eacm.ui.UI.showException(null, x);
			}catch(Exception ex){ // prevent hang
				Logger.getLogger(EDIT_PKG_NAME).log(Level.SEVERE,"Exception",ex);
			}finally{
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,getIdStr()+" bg ended "+Utils.getDuration(t11));
				RMIMgr.getRmiMgr().complete(this);
				detachWorker = null;
			}

			return null;
		}

		@Override
		public void done() {
			//this will be on the dispatch thread
			Logger.getLogger(TIMING_LOGGER).log(Level.INFO,getIdStr()+" dispatch ended "+Utils.getDuration(t11));
			enableActionsAndRestore();	
		}
		
		public void notExecuted(){
			Logger.getLogger(EDIT_PKG_NAME).log(Level.WARNING,getIdStr()+" not executed");
			enableActionsAndRestore();
			detachWorker = null; 
		}
	}

	private OPICMBlobValue getBlobFromDatabase() {
		if (!attr.isActive()) {
			attr.setActive(true);
			// create a new empty blob
			m_newBlob = new OPICMBlobValue(attr.getNLSID(), null, null);
		}
		if (m_newBlob == null) {
			m_newBlob = getBlobAttr().getOPICMBlobValue();

			if (blobLoc != null) { // use local blob
				try {
					m_newBlob.loadFromFile(blobLoc.getLocation());
				} catch (IOException x) {
					x.printStackTrace();
				}
			} else {
				String ext = getBlobAttr().getBlobExtension();
				EntityItem ei = (EntityItem) attr.getParent();
				if (Routines.have(ext) && ei.getEntityID() > 0 && m_newBlob.size() == 0) {
					byte[] baAttributeValue = DBUtils.getBlobValue(getBlobAttr(), editController);
					m_newBlob.setBlobValue(baAttributeValue);
					m_newBlob.setBlobExtension(ext);
				}
			}
		}

		return m_newBlob;
	}

	private class LaunchAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private static final String NAME="lnch";
		LaunchAction(){
			super(Utils.getResource(NAME));
			String value = Utils.getToolTip(NAME);
			if (value!=null){
				putValue(Action.SHORT_DESCRIPTION, value);
			}
			putValue(Action.MNEMONIC_KEY, new Integer(Utils.getMnemonic(NAME)));
		}
		public void actionPerformed(ActionEvent e) {
			if(editorOpening){
				editorOpening = false;
				return;
			}
			// this is needed if user was editing a different control and left a invalid value
			if(getFormCellPanel()!=null){
				FormTable ft = getFormCellPanel().getFormTable();
				// make sure it is not stopping this editor
				if(ft.getCurrentEditor()!=getFormCellPanel()){
					if(!ft.canStopEditing()){
						return; 
					}
				}
			}
			if (attr.isActive()) {
				disableActionsAndWait(); //disable all other actions and also set wait cursor
				launchWorker = new LaunchWorker();
				RMIMgr.getRmiMgr().execute(launchWorker);
			} else {
				// "msg2004" = Blob does not exist for current attribute.
				com.ibm.eacm.ui.UI.showErrorMessage(null,Utils.getResource("msg2004"));
			}
		}
		void dereference(){
			putValue(Action.NAME, null);
			putValue(Action.SHORT_DESCRIPTION, null);
			putValue(Action.SMALL_ICON,null);
			putValue(Action.MNEMONIC_KEY,null);
		}
	}
	private LaunchWorker launchWorker = null;
	private class LaunchWorker extends DBSwingWorker<String, Void> { 
		private long t11 = 0L;
		private boolean blobExists = false;

		@Override
		public String doInBackground() {
			String path = null;
			try{		
				t11 = System.currentTimeMillis();
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,"starting "+getIdStr());
				OPICMBlobValue opbv = getBlobFromDatabase();
				if (opbv == null) {
					// "msg23022" = Please attach, before launching.
					com.ibm.eacm.ui.UI.showErrorMessage(null,Utils.getResource("msg23022"));
				}else{
					blobExists = true;
					++blobCounter;
					String blobCount = "_" + blobCounter;
					String tmpFile = attr.getParent().getKey() + attr.getAttributeCode() + blobCount + getBlobAttr().getBlobExtension();

					String dir = System.getProperty("java.io.tmpdir");
					String file = Routines.replaceChar(tmpFile,' ', '_');		

					File f = new File(dir + file);

					Logger.getLogger(EDIT_PKG_NAME).log(Level.FINE,"launching blob: " + file);					

					f.deleteOnExit();

					int size = opbv.size();
					try {
						if (size > 0) { // blob was in pdh
							opbv.saveToFile(dir + file);
						}
					} catch (IOException x) {
						size = 0;
						com.ibm.eacm.ui.UI.showException(null, x);
					}
					if (size > 0) { // blob was newly attached and not in pdh yet
						path =dir + file;
					} else if (blobLoc != null) {  // blob was newly attached
						path = blobLoc.getLocation(); 
					} else { 
						//"msg23040" = Unable to launch Blob at this time. Please try again later.
						com.ibm.eacm.ui.UI.showErrorMessage(null,Utils.getResource("msg23040"));
					}
				}
			}catch(Exception ex){ // prevent hang
				Logger.getLogger(EDIT_PKG_NAME).log(Level.SEVERE,"Exception",ex);
			}finally{
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,getIdStr()+" bg ended "+Utils.getDuration(t11));
				RMIMgr.getRmiMgr().complete(this);
				launchWorker = null;
			}

			return path;
		}

		@Override
		public void done() {
			//this will be on the dispatch thread
			try {
				if(!isCancelled()){
					String path = get();
					if (path == null) {
						if(blobExists){
							//"msg23040" = Unable to launch Blob at this time. Please try again later.
							com.ibm.eacm.ui.UI.showErrorMessage(null,Utils.getResource("msg23040"));
						}
						return;
					}

					Logger.getLogger(EDIT_PKG_NAME).log(Level.INFO,"launching blob: " + path);	
					ShellControl.launch(path); 
				}
			} catch (InterruptedException ignore) {}
			catch (java.util.concurrent.ExecutionException e) {
				listErr(e,"launching blob");
			}finally{  
				Logger.getLogger(TIMING_LOGGER).log(Level.INFO,getIdStr()+" dispatch ended "+Utils.getDuration(t11));
				enableActionsAndRestore();	
			}
		}
		public void notExecuted(){
			Logger.getLogger(EDIT_PKG_NAME).log(Level.WARNING,getIdStr()+" not executed");
			enableActionsAndRestore();
			launchWorker = null; 
		}
	}

	private void enableActionsAndRestore(){
		if(editController!=null){ // will be null if used in pdg
			editController.enableActionsAndRestore();
		}
		attachAction.setEnabled(isCellEditable());
		detachAction.setEnabled(true);
		launchAction.setEnabled(true);
	}
	
	private void disableActionsAndWait() {
		if(editController!=null){ // will be null if used in pdg
			editController.disableActionsAndWait();
		}
		attachAction.setEnabled(false);
		detachAction.setEnabled(false);
		launchAction.setEnabled(false);
	}
	private class Location {
		private String dir = null;
		private String file = null;

		/**
		 * Location of newly loaded blob file
		 */
		Location(String d, String f) {
			dir = d;
			file = f;
		}
		String getLocation() {
			return dir + file;
		}
		void dereference(){
			dir = null;
			file = null;
		}
		/**
		 * copy blob file to new file 
		 * called when user attaches a new blob, then does a detach to a different file location.
		 * @param s
		 */
		void copyToFile(String s) {						
			FileInputStream fis = null;
			FileOutputStream fos = null;
			try {
				File inFile = new File(getLocation());
				File outFile = new File(s);
				byte [] b = new byte[512];
				int len = 0;
				fis = new FileInputStream(inFile);
				fos = new FileOutputStream(outFile);
				while ( (len=fis.read(b))!= -1 ) {
					fos.write(b,0,len);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				try {
					if (fis != null) {
						fis.close();
					}
					if (fos != null) {
						fos.close();
					}
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}
	}
}
