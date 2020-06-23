/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: NavPDGPanel.java,v $
 * Revision 1.2  2008/01/30 16:26:53  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:43:24  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:10  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:43  tony
 * This is the initial load of OPICM
 *
 * Revision 1.8  2005/09/08 17:58:59  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.7  2005/02/15 17:17:30  joan
 * add buttons
 *
 * Revision 1.6  2005/02/11 20:01:31  joan
 * add delete button
 *
 * Revision 1.5  2005/02/04 15:22:08  tony
 * JTest Format Third Pass
 *
 * Revision 1.4  2005/02/02 17:30:21  tony
 * JTest Second Pass
 *
 * Revision 1.3  2005/01/27 23:18:20  tony
 * JTest Formatting
 *
 * Revision 1.2  2005/01/07 17:02:06  tony
 * USRO-R-RLON-68EBE2
 *
 * Revision 1.1.1.1  2004/02/10 16:59:28  tony
 * This is the initial load of OPICM
 *
 * Revision 1.12  2003/10/29 00:21:16  tony
 * removed System.out. statements.
 *
 * Revision 1.11  2003/09/17 20:53:51  joan
 * fb fixes
 *
 * Revision 1.10  2003/07/10 15:20:54  joan
 * 51317
 *
 * Revision 1.9  2003/06/17 19:47:43  joan
 * work on SPDGActionItem
 *
 * Revision 1.8  2003/05/30 21:09:18  tony
 * updated messaging logic to popup on top
 * of the currently active Component.
 *
 * Revision 1.7  2003/05/28 16:27:40  tony
 * 50924
 *
 * Revision 1.6  2003/05/06 18:59:22  joan
 * 50530
 *
 * Revision 1.5  2003/04/21 20:03:12  tony
 * enhanced scroll logic to improve flag editor.
 *
 * Revision 1.4  2003/04/10 19:50:32  joan
 * fix feedback
 *
 * Revision 1.3  2003/04/03 22:44:18  joan
 * fixes
 *
 * Revision 1.2  2003/03/27 21:29:05  joan
 * fix bug
 *
 * Revision 1.1  2003/03/20 01:05:06  joan
 * initial load
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
import com.ibm.eannounce.exception.OutOfRangeException;
import COM.ibm.eannounce.objects.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class NavPDGPanel extends AccessibleDialogPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	//	private boolean displayOn = false;

//	private int ReturnStatus = 0;

	private NavTable table = null;

	//buttons
	private EButton btnClose = new EButton(getString("cncl"));
/*
 USRO-R-RLON-68EBE2
	private eButton btnEdit = new eButton("Edit");
	private eButton btnCreate = new eButton("Create");
*/
	private EButton btnEdit = new EButton(getString("edit"));		//USRO-R-RLON-68EBE2
	private EButton btnCreate = new EButton(getString("crte"));		//USRO-R-RLON-68EBE2
	private EButton btnDelete = new EButton(getString("del"));

	//Panels
	private EPanel pNorth = new EPanel();
	private EPanel pSouth = new EPanel();
	//private GridBagConstraints gbca = new GridBagConstraints();
	private GridLayout ga = new GridLayout(1,2,5,5);
	private EScrollPane sPane = new EScrollPane();

	private EntityList m_el = null;
	private PDGActionItem m_pdgai = null;
	private SPDGActionItem m_spdgai = null;
	private boolean m_bButtons = false;
	private boolean m_bSPDG = false;

	/**
     * NavPDGPanel
     * @author Anthony C. Liberto
     */
    public NavPDGPanel() {
		super(new BorderLayout());
		generateDialog();
		return;
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
		btnEdit = null;
		btnCreate = null;
		btnDelete = null;
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
		btnClose.setMnemonic(getChar("cncl-s"));
		btnClose.setToolTipText(getString("cncl-t"));

		btnEdit.setMnemonic(getChar("edit-s"));		//USRO-R-RLON-68EBE2
		btnCreate.setMnemonic('r');					//USRO-R-RLON-68EBE2
		btnDelete.setMnemonic('d');
		vspd = Integer.valueOf(getString("colv")).intValue();
		hspd = Integer.valueOf(getString("colh")).intValue();
		sPane.getVerticalScrollBar().setUnitIncrement(vspd);
		sPane.getHorizontalScrollBar().setUnitIncrement(hspd);
		sPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		//add to Panel
		pNorth.add(sPane);

		//add to Panel
		pSouth.setLayout(ga);
		pSouth.add(btnEdit);
		pSouth.add(btnCreate);
		pSouth.add(btnClose);
		add("North", pNorth);
		add("South", pSouth);

		btnClose.addActionListener(this);
		btnEdit.addActionListener(this);
		btnCreate.addActionListener(this);
		btnDelete.addActionListener(this);

		sPane.setSize(d);
		sPane.setPreferredSize(d);
		setModal(true);
		return;
	}

	/**
     * setSPDG
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setSPDG(boolean _b) {
		m_bSPDG = _b;
	}

	/**
     * isSPDG
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isSPDG() {
		return m_bSPDG;
	}

	/**
     * showNavPDG
     * @param _pdgai
     * @param _eList
     * @author Anthony C. Liberto
     */
    public void showNavPDG(PDGActionItem _pdgai, EntityList _eList) {
        EntityGroup eg = null;
		m_bButtons = false;
		m_el = _eList;
		m_pdgai = _pdgai;

		for (int i=0; i<_eList.getEntityGroupCount(); ++i) {
			eg = _eList.getEntityGroup(i);
			if (eg.isDisplayable()) {
				break;
			}
		}

		table = new NavTable(eg, eg.getEntityGroupTable());
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		if (table.isValidCell(0,0)) {
			table.setRowSelectionInterval(0,0);
			table.setColumnSelectionInterval(0,0);
		}
		sPane.setViewportView(table);
		setTitle(eg.getLongDescription());
		if (_pdgai.getPDGDeleteAction() != null) {
			pSouth.add(btnDelete);
		} else {
			pSouth.remove(btnDelete);
		}

		setBusy(false);
		showDialog(this,this);
	}

	/**
     * showNavSPDG
     * @param _spdgai
     * @param _eList
     * @author Anthony C. Liberto
     */
    public void showNavSPDG(SPDGActionItem _spdgai, EntityList _eList) {
        EntityGroup eg = null;
		m_bButtons = false;
		m_el = _eList;
		m_spdgai = _spdgai;

		for (int i=0; i<_eList.getEntityGroupCount(); ++i) {
			eg = _eList.getEntityGroup(i);
			if (eg.isDisplayable()) {
				break;
			}
		}

		table = new NavTable(eg, eg.getEntityGroupTable());
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		if (table.isValidCell(0,0)) {
			table.setRowSelectionInterval(0,0);
			table.setColumnSelectionInterval(0,0);
		}
		sPane.setViewportView(table);
		setTitle(eg.getLongDescription());
		if (_spdgai.getPDGDeleteAction() != null) {
			pSouth.add(btnDelete);
		} else {
			pSouth.remove(btnDelete);
		}

		setBusy(false);
		showDialog(this,this);
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
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @author Anthony C. Liberto
     */
    public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (isBusy()) {
			return;
		}
		setBusy(true);

		if (source == btnClose) {
			cancel();
		} else if (source == btnEdit) {
			if (m_bSPDG) {
				processEditSPDG();
			} else {
				processEdit();
			}
			return;
		} else if (source == btnCreate) {
			if (m_bSPDG) {
				processCreateSPDG();
			} else {
				processCreate();
			}
			return;
		} else if (source == btnDelete) {
			if (m_bSPDG) {
				processSPDGDelete();
			} else {
				processDelete();
			}
			return;
		}
		setBusy(false);
		return;
	}

	private EntityList cancel() {
		m_el = null;
		m_bButtons = true;
		disposeDialog();
		return null;
	}

	private void processEdit() {
		Object o = table.getSelectedObject();
        EditPDGPanel editPnl = null;
        if (o != null && o instanceof EntityItem) {
			EntityItem ei = (EntityItem)o;
			EntityItem[] aei = {ei};
			EditActionItem eai = m_pdgai.getPDGEditAction();
			if (eai != null) {
				EntityList el = dBase().getEntityList(eai, aei,this);
				disposeDialog();
				editPnl = EAccess.getEditPDGPnl();
				eaccess().setPDGOn(true);			//50530
				editPnl.setSPDG(false);
				editPnl.showEditPDG(m_pdgai, el);
			}
		} else {
			setBusy(false);
		}
	}

	private void processCreate() {
		EntityGroup eg = m_el.getParentEntityGroup();
		EntityItem[] aei = new EntityItem[eg.getEntityItemCount()];
        EditPDGPanel editPnl = null;
        for (int i=0; i < eg.getEntityItemCount(); i++) {
			aei[i] = eg.getEntityItem(i);
		}

		if (aei != null && aei.length > 0) {
			CreateActionItem cai = m_pdgai.getPDGCreateAction();
			if (cai != null) {
				EntityList el = dBase().getEntityList(cai, aei,this);
				disposeDialog();
				editPnl = EAccess.getEditPDGPnl();
				eaccess().setPDGOn(true);			//50530
				editPnl.setSPDG(false);
				editPnl.showEditPDG(m_pdgai, el);
			}
		}
	}

	private void processDelete() {
		System.out.println("NavPDGPanel processDelete");
        try {
			EntityItem[] aei = (EntityItem[]) table.getSelectedObjects(false, true);
			DeleteActionItem dai = m_pdgai.getPDGDeleteAction();
			if (aei != null && dai != null) {
				EntityGroup eg = null;
                EntityItem[] aeiP = null;
                dBase().rexec(dai, aei, (Window) getParentDialog());
				eg = m_el.getParentEntityGroup();
				aeiP = new EntityItem[eg.getEntityItemCount()];

		        for (int i=0; i < eg.getEntityItemCount(); i++) {
					aeiP[i] = eg.getEntityItem(i);
				}

				refreshTable(dBase().getEntityList(m_pdgai.getPDGNavAction(), aeiP, this));

			}
		} catch (OutOfRangeException _range) {
            _range.printStackTrace();
            setBusy(false);
            showException(_range, FYI_MESSAGE, OK); //51260
	        return;
        }

		setBusy(false);

	}

	private void processSPDGDelete() {
		System.out.println("NavPDGPanel processSPDGDelete");
        try {
			EntityItem[] aei = (EntityItem[]) table.getSelectedObjects(false, true);
			DeleteActionItem dai = m_spdgai.getPDGDeleteAction();
			if (aei != null && dai != null) {
				EntityGroup eg = null;
                EntityItem[] aeiP = null;
                dBase().rexec(dai, aei, (Window) getParentDialog());
				eg = m_el.getParentEntityGroup();
				aeiP = new EntityItem[eg.getEntityItemCount()];

		        for (int i=0; i < eg.getEntityItemCount(); i++) {
					aeiP[i] = eg.getEntityItem(i);
				}

				refreshTable(dBase().getEntityList(m_pdgai.getPDGNavAction(), aeiP, this));

			}
		} catch (OutOfRangeException _range) {
            _range.printStackTrace();
            setBusy(false);
            showException(_range, FYI_MESSAGE, OK); //51260
	        return;
        }

		setBusy(false);

	}

	private void refreshTable(EntityList _el) {
        EntityGroup eg = null;
        m_el = _el;
		for (int i=0; i<_el.getEntityGroupCount(); ++i) {
			eg = _el.getEntityGroup(i);
			if (eg.isDisplayable()) {
				break;
			}
		}
		if (eg != null) {
			table.updateModel(eg.getEntityGroupTable());
		}
	}

	private void processEditSPDG() {
        EditPDGPanel editPnl = null;
        Object o = table.getSelectedObject();
		if (o != null && o instanceof EntityItem) {
			EntityItem ei = (EntityItem)o;
			EntityItem[] aei = {ei};
			EditActionItem eai = m_spdgai.getPDGEditAction();
			if (eai != null) {
				EntityList el = dBase().getEntityList(eai, aei,this);
				disposeDialog();
				editPnl = EAccess.getEditPDGPnl();
				eaccess().setPDGOn(true);			//50530
				editPnl.setSPDG(true);
				editPnl.showEditSPDG(m_spdgai, el);
			}
		} else {
			setBusy(false);
		}
	}

	private void processCreateSPDG() {
		EntityGroup eg = m_el.getParentEntityGroup();
		EntityItem[] aei = new EntityItem[eg.getEntityItemCount()];
        EditPDGPanel editPnl = null;
        for (int i=0; i < eg.getEntityItemCount(); i++) {
			aei[i] = eg.getEntityItem(i);
		}

		if (aei != null && aei.length > 0) {
			CreateActionItem cai = m_spdgai.getPDGCreateAction();
			if (cai != null) {
				EntityList el = dBase().getEntityList(cai, aei,this);
				disposeDialog();
				editPnl = EAccess.getEditPDGPnl();
				eaccess().setPDGOn(true);			//50530
				editPnl.setSPDG(true);
				editPnl.showEditSPDG(m_spdgai, el);
			}
		}
	}

	/**
     * getEntityList
     * @return
     * @author Anthony C. Liberto
     */
    public EntityList getEntityList() {
		return m_el;
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
     * createMenu
     * @author Anthony C. Liberto
     */
    protected void createMenu() {}
	/**
     * removeMenu
     * @author Anthony C. Liberto
     */
    protected void removeMenu() {}

/*
 50924
 */
    /**
     * getSearchableObject
     * @author Anthony C. Liberto
     * @return Object
     */
    public Object getSearchableObject() {
		return table;
	}

}

