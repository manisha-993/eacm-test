//Licensed Materials -- Property of IBM
//
//(C) Copyright IBM Corp. 2014  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.ui;

import java.awt.*;
import java.awt.event.*;
import java.net.URI;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import COM.ibm.eannounce.objects.EntityItem;

import com.ibm.eacm.actions.*;
import com.ibm.eacm.mw.DBUtils;
import com.ibm.eacm.nav.Navigate;
import com.ibm.eacm.objects.*;

/******************************************************************************
 * This is used to display the view xml dialog - RCQ285768
 * @author Wendy Stimpson
 */
//$Log: ViewXMLDialog.java,v $
//Revision 1.1  2014/02/24 15:12:12  wendy
//RCQ285768 - view cached XML in JUI
//
public class ViewXMLDialog extends EACMDialog implements EACMGlobals, ActionListener, DocumentListener
{
	private static final long serialVersionUID = 1L;
	private static final String TYPES[];
	
	static {
		TYPES = new String[]{
				"MODEL","FEATURE","SWFEATURE","FCTRANSACTION","MODELCONVERT","LSEO","LSEOBUNDLE",
				"SVCMOD","GBT","GENEREALAREA","CATNAV",
				"REVUNBUND","PRODSTRUCT","SWPRODSTRUCT"};
    	Arrays.sort(TYPES);
	}
	
	/** object copyright statement */
	public static final String COPYRIGHT="(C) Copyright IBM Corp. 2014  All Rights Reserved.";
	/** cvs version */
	public static final String VERSION = "$Revision: 1.1 $";

	private Navigate navigate = null;
	private JComboBox cmboType = new JComboBox();
	private IntField idField = new IntField();
	private JLabel typeLbl = null;
	private JLabel idLbl = null;

	private JButton	m_btnRun = null;
	private JButton	m_btnCancel = null;

	private JPanel viewPanel = new JPanel(null);

	/***
	 * 
	 * @param owner
	 * @param nav
	 */
	public ViewXMLDialog(Window owner, Navigate nav)  {
		super(owner,"viewxml.panel",JDialog.ModalityType.DOCUMENT_MODAL);//allow eacm frame to come to front

		closeAction = new CloseDialogAction(this);

		navigate = nav;

		this.setTitle(this.getTitle());

		setIconImage(Utils.getImage(DEFAULT_ICON));

		init();

		finishSetup(owner);

		setSize(getPreferredSize());
	}

	/**
	 * init dialog components
	 */
	private void init() {
		createActions();

		createMenuBar();

    	//viewxml.type = Entity Type:
		typeLbl = new JLabel(Utils.getResource("viewxml.type"));
		typeLbl.setLabelFor(cmboType);
		typeLbl.setDisplayedMnemonic(Utils.getMnemonic("viewxml.type"));
		
		//viewxml.id = Entity ID:
		idLbl = new JLabel(Utils.getResource("viewxml.id"));
		idLbl.setLabelFor(idField);
		idLbl.setDisplayedMnemonic(Utils.getMnemonic("viewxml.id"));
		
		for (int i=0; i<TYPES.length; i++){
			cmboType.addItem(TYPES[i]);
		}
		Dimension curdim = cmboType.getPreferredSize();
		Dimension adjdim = new Dimension(curdim.width+5,curdim.height); // allow for arrow
		cmboType.setSize(adjdim);
		cmboType.setPreferredSize(adjdim);
		
		idField.setSize(adjdim);
		idField.setPreferredSize(adjdim);
		// allow esc to get to the container
		idField.getInputMap().put(KeyStroke.getKeyStroke((char)KeyEvent.VK_ESCAPE), "none");

		cmboType.setSelectedItem(null);

		cmboType.addActionListener(this);
		idField.getDocument().addDocumentListener(this);
		
		if(navigate!=null){
			try {
				EntityItem[] eia = navigate.getSelectedEntityItems(false, false);
				if(eia!=null && eia.length>0){
					EntityItem ei = eia[0];
					String curtype = ei.getEntityType();
					int curid = ei.getEntityID();
					String dwntype = null;
					int dwnid = 0;
					if(ei.hasDownLinks()){
						EntityItem dwnei = (EntityItem)ei.getDownLink(0);
						dwntype = dwnei.getEntityType();
						dwnid = dwnei.getEntityID();
					}

					for(int i=0;i<cmboType.getItemCount(); i++){
						if(curtype.equals(cmboType.getItemAt(i))){
							cmboType.setSelectedIndex(i);
							idField.setText(""+curid);
							break;
						}
						if(dwntype!=null && dwntype.equals(cmboType.getItemAt(i))){
							cmboType.setSelectedIndex(i);
							idField.setText(""+dwnid);
							break;
						}
					}
				}
			} catch (OutOfRangeException e) {
			}
		}

		JPanel m_pBtn = new JPanel(new GridLayout(1,2,10,10));

		m_btnRun = new JButton(getAction(VIEWXMLOK_ACTION));	
		m_btnRun.setMnemonic((char)((Integer)m_btnRun.getAction().getValue(Action.MNEMONIC_KEY)).intValue());
		m_pBtn.add(m_btnRun);	

		m_btnCancel = new JButton(getAction(CANCEL_ACTION));	
		m_btnCancel.setMnemonic((char)((Integer)m_btnCancel.getAction().getValue(Action.MNEMONIC_KEY)).intValue());
		m_pBtn.add(m_btnCancel);

		layoutComponents();

		getContentPane().add(viewPanel);
	}

	private void layoutComponents() {

		GroupLayout layout = new GroupLayout(viewPanel);
		viewPanel.setLayout(layout);
		
		layout.setHorizontalGroup(
				layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
										.addGroup(layout.createParallelGroup(Alignment.LEADING,false)
								.addComponent(idLbl,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(typeLbl,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addComponent(idField,GroupLayout.DEFAULT_SIZE,122, Short.MAX_VALUE)
								.addComponent(cmboType,0,122, Short.MAX_VALUE)))		
					.addGroup(Alignment.TRAILING,
							layout.createSequentialGroup()
							.addComponent(m_btnRun)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(m_btnCancel)))
							.addContainerGap())						
		);
		
		layout.setVerticalGroup(
				layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(typeLbl)
								.addComponent(cmboType,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(idLbl)
								.addComponent(idField,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(m_btnRun)
						.addComponent(m_btnCancel))
						.addContainerGap())						
		);
	}
	/**
	 * actionPerformed
	 * 
	 * @param _action
	 */
	public void actionPerformed(ActionEvent e) {
		getAction(VIEWXMLOK_ACTION).setEnabled(true);
	}
	/**
	 * createMenuBar
	 */
	private void createMenuBar() {
		menubar = new JMenuBar();

		createFileMenu();

		setJMenuBar(menubar);
	}
	private void createFileMenu() {
		JMenu mnuFile = new JMenu(Utils.getResource(FILE_MENU));
		mnuFile.setMnemonic(Utils.getMnemonic(FILE_MENU));

		JMenuItem mi = mnuFile.add(closeAction);
		mi.setVerifyInputWhenFocusTarget(false);

		menubar.add(mnuFile);
	}
	/**
	 * create all of the actions
	 */
	private void createActions() {
		EACMAction act = new OkAction();
		addAction( act);
		act = new CancelAction();
		addAction( act);
	}

	public void dereference(){
		super.dereference();

		navigate = null;
		cmboType.removeActionListener(this);
		idField.getDocument().removeDocumentListener(this);
		
		idField.dereference();
		idField = null;
		
		idLbl.setLabelFor(null);
		idLbl.removeAll();
		idLbl.removeNotify();
		idLbl.setUI(null);
		idLbl = null;
		
		cmboType.removeAllItems();
		cmboType.removeAll();
		cmboType.removeNotify();
		cmboType.setUI(null);
		cmboType = null;
		
		typeLbl.setLabelFor(null);
		typeLbl.removeAll();
		typeLbl.removeNotify();
		typeLbl.setUI(null);
		typeLbl = null;

		m_btnRun.setAction(null);
		m_btnRun.setUI(null);
		m_btnRun = null;
		m_btnCancel.setAction(null);
		m_btnCancel.setUI(null);
		m_btnCancel = null;

		removeAll();
		viewPanel.setLayout(null); 
		viewPanel.setUI(null);
	}

	private class OkAction extends EACMAction 
	{
		private static final long serialVersionUID = 1L;
		OkAction() {
			super(VIEWXMLOK_ACTION);
			setEnabled(false);
		}
		protected boolean canEnable(){ 
			return idField !=null && idField.getDocument().getLength()>0 && 
					cmboType.getSelectedIndex()!=-1; 
		}
		
		public void actionPerformed(ActionEvent e) {
			disableActionsAndWait();
			
			// pass the entity type and id to the mw, get xml
			String type = cmboType.getSelectedItem().toString();
			int id = Integer.parseInt(idField.getText().trim());
			Logger.getLogger(APP_PKG_NAME).log(Level.INFO,"Getting XML for type: "+type+" id: "+id);
			
			String XML = DBUtils.getCachedXML(type, id, ViewXMLDialog.this);

	        if(XML !=null){
	        	if(!XML.trim().startsWith("<")){
	        		// display the error message from the server
	        		com.ibm.eacm.ui.UI.showErrorMessage(null, XML);
	        		enableActionsAndRestore();
	        		return;
	        	}
	        	// write the file to the TEMP dir
	        	String strFileName = TEMP_DIRECTORY +"viewxml_"+ type +id+ ".xml"; 
	        	Utils.writeString(strFileName, XML,EACM_FILE_ENCODE);
	        	Utils.deleteOnExit(strFileName);

	        	String url="file:///"+strFileName.replace('\\', '/');

	        	// this will launch the system default editor for xml - usually a browser 
	        	Desktop dt = Desktop.getDesktop();   
	        	try {
	        		dt.browse(new URI(url));
	        	} catch (Exception exc) {
	        		Logger.getLogger(APP_PKG_NAME).log(Level.SEVERE,"Unable to launch browser ",exc);
	        		UI.showException(null, exc);
	        	}   
	        }
	    
			// close the dialog
			closeAction.actionPerformed(null);
		} 	
	} 
	
	private class CancelAction extends EACMAction 
	{
		private static final long serialVersionUID = 1L;
		CancelAction() {
			super(CANCEL_ACTION);
		}

		public void actionPerformed(ActionEvent e) {
			closeAction.actionPerformed(e); 
		} 	
	}
	@Override
	public void insertUpdate(DocumentEvent e) {
		getAction(VIEWXMLOK_ACTION).setEnabled(true);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		getAction(VIEWXMLOK_ACTION).setEnabled(true);
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		getAction(VIEWXMLOK_ACTION).setEnabled(true);
	} 
}