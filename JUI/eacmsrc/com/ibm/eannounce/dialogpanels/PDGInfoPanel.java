/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: PDGInfoPanel.java,v $
 * Revision 1.2  2008/01/30 16:26:54  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:43:24  wendy
 * Reorganized JUI module
 *
 * Revision 1.3  2005/10/13 20:11:22  joan
 * adjust for PDG
 *
 * Revision 1.2  2005/09/12 19:03:10  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:43  tony
 * This is the initial load of OPICM
 *
 * Revision 1.8  2005/09/08 17:59:00  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.7  2005/02/28 22:25:34  joan
 * add catch exception
 *
 * Revision 1.6  2005/02/02 17:30:21  tony
 * JTest Second Pass
 *
 * Revision 1.5  2005/01/27 23:18:20  tony
 * JTest Formatting
 *
 * Revision 1.4  2005/01/17 17:40:16  tony
 * USRO-R-SWWE-68ERQ updated
 *
 * Revision 1.3  2005/01/07 20:36:04  tony
 * USRO-R-SWWE-68ERQ
 *
 * Revision 1.2  2004/08/09 20:32:06  joan
 * adjust for PDG
 *
 * Revision 1.1.1.1  2004/02/10 16:59:28  tony
 * This is the initial load of OPICM
 *
 * Revision 1.2  2003/10/29 00:21:16  tony
 * removed System.out. statements.
 *
 * Revision 1.1  2003/06/19 20:01:49  joan
 * initial load
 *
 * Revision 1.4  2003/05/30 21:09:18  tony
 * updated messaging logic to popup on top
 * of the currently active Component.
 *
 * Revision 1.3  2003/04/21 20:03:11  tony
 * enhanced scroll logic to improve flag editor.
 *
 * Revision 1.2  2003/03/13 18:38:43  tony
 * accessibility and column Order.
 *
 * Revision 1.1.1.1  2003/03/03 18:03:43  tony
 * This is the initial load of OPICM
 *
 * Revision 1.3  2002/11/07 16:58:12  tony
 * added/adjusted copyright statement
 *
 */

package com.ibm.eannounce.dialogpanels;
import com.elogin.*;
import com.ibm.eannounce.eforms.table.*;
import com.ibm.eannounce.eobjects.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import COM.ibm.eannounce.objects.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class PDGInfoPanel extends AccessibleDialogPanel implements KeyListener{
	private static final long serialVersionUID = 1L;
	//	private boolean displayOn = false;

//  private int ReturnStatus = 0;

	private PDGInfoTable table = null;
//	private RowSelectableTable m_rst = null;
	private PDGCollectInfoList m_cl = null;
	//buttons
	private EButton btnClose = new EButton(getString("cncl"));
	private EButton btnSave = new EButton(getString("ok"));

	//Panels
//USRO-R-SWWE-68ERQ	private ePanel pNorth = new ePanel();
	private EPanel pNorth = new EPanel(new BorderLayout());		//USRO-R-SWWE-68ERQ
	private EPanel pSouth = new EPanel();
	//private GridBagConstraints gbca = new GridBagConstraints();
	private GridLayout ga = new GridLayout(1,2,5,5);
	private EScrollPane sPane = new EScrollPane();
	private PDGActionItem m_pdgai = null;
	private RowSelectableTable m_rst = null;
	private EANMetaAttribute m_meta = null;

	private boolean m_bButtons = false;

	/**
     * PDGInfoPanel
     * @author Anthony C. Liberto
     */
    public PDGInfoPanel() {
		super(new BorderLayout());
		setResizable(true);			//USRO-R-SWWE-68ERQ
		generateDialog();
		return;
	}

	/**
     * getMenuBar
     * @author Anthony C. Liberto
     * @return JMenuBar
     */
    public JMenuBar getMenuBar() {
		return null;
	}

	/**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
		if (table != null) {
			table.dereference();
		}
		table = null;
		btnClose = null;
		btnSave = null;
		pNorth = null;
		pSouth = null;
		//gbca = null;
		ga = null;
		if (sPane != null) {
			sPane.dereference();
			sPane = null;
		}
		return;
	}

	private void generateDialog() {
        Dimension d = new Dimension(400,300);
        int vspd = -1;
        int hspd = -1;

		addKeyListener(this);
		btnClose.setMnemonic(getChar("cncl-s"));
		btnClose.setActionCommand("exit");
		btnClose.setToolTipText(getString("cncl-t"));

		btnSave.setMnemonic(getChar("ok-s"));
		btnSave.setActionCommand("ok");
		btnSave.setToolTipText(getString("save-t"));

		vspd = Integer.valueOf(getString("colv")).intValue();
		hspd = Integer.valueOf(getString("colh")).intValue();
		sPane.getVerticalScrollBar().setUnitIncrement(vspd);
		sPane.getHorizontalScrollBar().setUnitIncrement(hspd);
		sPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		//add to Panel
//USRO-R-SWWE-68ERQ		pNorth.add(sPane);
		pNorth.add("Center",sPane);		//USRO-R-SWWE-68ERQ

		//add to Panel
		pSouth.setLayout(ga);
		pSouth.add(btnSave);
		pSouth.add(btnClose);
//USRO-R-SWWE-68ERQ		add("North", pNorth);
		add("Center",pNorth);			//USRO-R-SWWE-68ERQ
		add("South", pSouth);

		btnClose.addActionListener(this);
		btnSave.addActionListener(this);

		sPane.setSize(d);
		sPane.setPreferredSize(d);
		setModal(true);
		return;
	}

	/**
     * showPDGInfo
     * @param _win
     * @param _cl
     * @return
     * @author Anthony C. Liberto
     */
    public PDGCollectInfoList showPDGInfo(Window _win, PDGCollectInfoList _cl) {
        RowSelectableTable rst = null;
        JViewport cView = null;
        Dimension d = null;

        m_bButtons = false;
		m_cl = _cl;
		rst = m_cl.getTable();
		rst.setLongDescription(true);
		table = new PDGInfoTable(rst);
		sPane.setViewportView(table);
		if (_cl.isMatrix()) {
			ERowList cList = table.refreshList();
			if (cList != null) {
				sPane.setRowHeaderView(cList);
				cView = sPane.getRowHeader();
				d = cList.getSize();
				cView.setViewSize(d);
				cView.setSize(d);
				cView.setPreferredSize(d);
				sPane.setCorner(JScrollPane.UPPER_LEFT_CORNER, new JLabel(table.getTableTitle()));
			}
		}
		showDialog(_win,this);
		return m_cl;
	}

	/**
     * isButtonSelected
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isButtonSelected() {
		return m_bButtons;
	}

	/**
     * actionPerformed
     *
     * @author Anthony C. Liberto
     * @param _action
     */
    public void actionPerformed(String _action) {
		if (_action.equals("exit")) {
			cancel();
		} else if (_action.equals("ok")) {
			save();
		}
		return;
	}

	private void cancel() {
		m_cl = null;
		m_bButtons = true;
		disposeDialog();
		return;
	}

	private void save() {
		m_bButtons = true;
		try {
			if (m_pdgai != null && m_rst != null) {
				if (m_meta != null) {
					m_pdgai.setPDGCollectInfo(m_cl, m_meta, m_rst);
				} else {
					m_pdgai.setPDGCollectInfo(m_cl, m_pdgai.getCollectStep(), m_rst);
				}
			}
			disposeDialog();
		} catch (Exception e) {
			setMessage(e.toString());
			showError(getParentDialog()); //50621
		}

		return;
	}

	/**
     * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
     * @author Anthony C. Liberto
     */
    public void keyReleased(KeyEvent kea) {}
	/**
     * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
     * @author Anthony C. Liberto
     */
    public void keyTyped(KeyEvent kea) {}

	/**
     * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
     * @author Anthony C. Liberto
     */
    public void keyPressed(KeyEvent kea) {
//		char key = kea.getKeyChar();
		if (kea.getKeyCode() == KeyEvent.VK_ENTER) {
			save();

		} else if (kea.getKeyCode() == KeyEvent.VK_ESCAPE) {
			cancel();
		}
		return;
	}

	/**
     * setPDGActionItem
     *
     * @author Anthony C. Liberto
     * @param _pdgai
     */
    public void setPDGActionItem(PDGActionItem _pdgai) {
		m_pdgai = _pdgai;
	}

	/**
     * setRequestTable
     *
     * @author Anthony C. Liberto
     * @param _rst
     */
    public void setRequestTable(RowSelectableTable _rst) {
		m_rst = _rst;
	}

	public void setRequestMeta(EANMetaAttribute _meta) {
		m_meta = _meta;
	}
}

