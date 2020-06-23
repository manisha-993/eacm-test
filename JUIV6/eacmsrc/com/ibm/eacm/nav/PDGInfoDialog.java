//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.nav;

import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.*;

import com.ibm.eacm.*;
import com.ibm.eacm.ui.EACMDialog;
import COM.ibm.eannounce.objects.*;

import com.ibm.eacm.actions.*;

import com.ibm.eacm.objects.EACMGlobals;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.table.PDGInfoTable;


/******************************************************************************
* This is used to display the pdg info dialog.  
* @author Wendy Stimpson
*/
//$Log: PDGInfoDialog.java,v $
//Revision 1.1  2012/09/27 19:39:15  wendy
//Initial code
//
public class PDGInfoDialog extends EACMDialog implements EACMGlobals
{
	private static final long serialVersionUID = 1L;
	/** object copyright statement */
	public static final String COPYRIGHT="(C) Copyright IBM Corp. 2012  All Rights Reserved.";
	/** cvs version */
	public static final String VERSION = "$Revision: 1.1 $";


	private JPanel pnlMain = new JPanel(null); 
	private JPanel pnlBtn = null;
	private JButton btnSave, btnCancel;

	private JScrollPane jsp = null;
	private PDGInfoTable table = null;
	private PDGCollectInfoList m_cl = null;
	private RowSelectableTable m_rst = null;
	private EANMetaAttribute m_meta = null;

	private PDGActionItem pdgAction = null; 

	/**
	 */
	public PDGInfoDialog(PDGCollectInfoList cl, PDGActionItem pdg,	
			RowSelectableTable pdgrst,EANMetaAttribute m)  
	{
		//pdg.panel=EACM {0}
		super(EACM.getEACM(),"pdg.panel",JDialog.ModalityType.APPLICATION_MODAL);
		
		m_cl = cl;
		pdgAction = pdg;
		m_rst = pdgrst;
		m_meta = m; // may be null
		
		init();

		finishSetup(EACM.getEACM());

		setResizable(true); 
	}

	/**
	 * init frame components
	 */
	private void init() {
		if(m_meta != null){
			setTitle(Utils.getResource("pdg.panel",m_meta.getLongDescription()));
		}else{
			setTitle(Utils.getResource("pdg.panel",pdgAction.getStepDescription(pdgAction.getCollectStep())));
		}
		
		createActions();
		
		Dimension d = new Dimension(400,300);
		table = new PDGInfoTable(m_cl.getTable(), m_cl.getProfile());

        RowSelectableTable rst = null;

		rst = m_cl.getTable();
		rst.setLongDescription(true);

		btnCancel = new JButton(closeAction);
		btnSave = new JButton(getAction(OK_ACTION));
		btnSave.setToolTipText(Utils.getToolTip("save"));
	
		btnCancel.setMnemonic((char)((Integer)btnCancel.getAction().getValue(Action.MNEMONIC_KEY)).intValue());
		btnSave.setMnemonic((char)((Integer)btnSave.getAction().getValue(Action.MNEMONIC_KEY)).intValue());
		
		jsp = new JScrollPane(table); 
		jsp.setFocusable(false);

		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		jsp.setPreferredSize(d);
		jsp.setSize(d);

		if (m_cl.isMatrix()) {
			JViewport cView = jsp.getRowHeader();
			Dimension dd = table.getRowHeader(0).getSize();
			cView.setViewSize(dd);
			cView.setSize(dd);
			cView.setPreferredSize(dd);
			jsp.setCorner(JScrollPane.UPPER_LEFT_CORNER, new JLabel(table.getTableTitle()));
			jsp.setRowHeaderView(table.getRowHeader(0));
			jsp.setCorner(JScrollPane.UPPER_LEFT_CORNER, table.getHeader());
		}
		
		table.resizeCells();

		pnlBtn = new JPanel(new GridLayout(1,2,5,5));
		pnlBtn.add(btnSave);
		pnlBtn.add(btnCancel);

		GroupLayout layout = new GroupLayout(pnlMain);
		pnlMain.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		GroupLayout.ParallelGroup leftToRight = layout.createParallelGroup();
		leftToRight.addComponent(jsp);
		leftToRight.addComponent(pnlBtn, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);// this centers it

		GroupLayout.SequentialGroup topToBottom = layout.createSequentialGroup();
		topToBottom.addComponent(jsp);
		topToBottom.addComponent(pnlBtn);
		
		layout.setHorizontalGroup(leftToRight);
		layout.setVerticalGroup(topToBottom);
		getContentPane().add(pnlMain);
	}
	
	private void createActions(){
		closeAction = new CloseAction(this);
		addAction(new SaveAction());
	}

	/**
	 * release memory
	 */
	public void dereference() {
		super.dereference();

		btnCancel.setAction(null);
		btnCancel.removeAll();
		btnCancel.setUI(null);
		btnCancel = null;
		
		btnSave.setAction(null);
		btnSave.removeAll();
		btnSave.setUI(null);
		btnSave = null;

		table.dereference();
		table = null;

		pnlMain.removeAll();
		pnlMain.setUI(null);
		pnlMain = null;
		
		pnlBtn.removeAll();
		pnlBtn.setUI(null);
		pnlBtn = null;

		jsp.removeAll();
		jsp.setUI(null);
		jsp = null;
		
		m_cl = null;
		m_rst = null;
		m_meta = null;

		pdgAction = null; 
	}

	private class CloseAction extends CloseDialogAction
	{
		private static final long serialVersionUID = 1L;
		CloseAction(EACMDialog f) {
			super(f,CANCEL_ACTION);
		}
	}

	private class SaveAction extends EACMAction
	{
		private static final long serialVersionUID = 1L;
		SaveAction() {
			super(OK_ACTION);
		}

		public void actionPerformed(ActionEvent e) {
			try{
				if (m_meta != null) {
					pdgAction.setPDGCollectInfo(m_cl, m_meta, m_rst);
				} else {
					pdgAction.setPDGCollectInfo(m_cl, pdgAction.getCollectStep(), m_rst);
				}
				closeAction.actionPerformed(null);
			} catch (Exception ex) {
				com.ibm.eacm.ui.UI.showErrorMessage(PDGInfoDialog.this,ex.toString());
			}
		}
	}
}