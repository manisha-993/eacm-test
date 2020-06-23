/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: OrderPanel.java,v $
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
 * Revision 1.6  2005/09/08 17:59:00  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.5  2005/02/02 17:30:21  tony
 * JTest Second Pass
 *
 * Revision 1.4  2005/01/27 23:18:20  tony
 * JTest Formatting
 *
 * Revision 1.3  2004/05/18 21:28:42  tony
 * tbd_tr
 *
 * Revision 1.2  2004/02/26 21:53:16  tony
 * remoteDatabaseInterface centralization.
 *
 * Revision 1.1.1.1  2004/02/10 16:59:28  tony
 * This is the initial load of OPICM
 *
 * Revision 1.35  2003/12/01 17:45:12  tony
 * accessibility
 *
 * Revision 1.34  2003/11/14 21:51:00  tony
 * fixed null pointer
 *
 * Revision 1.33  2003/10/29 18:17:56  tony
 * 52727
 *
 * Revision 1.32  2003/10/29 17:38:07  tony
 * 52727
 *
 * Revision 1.31  2003/10/29 00:21:16  tony
 * removed System.out. statements.
 *
 * Revision 1.30  2003/08/27 18:00:04  tony
 * 51964
 *
 * Revision 1.29  2003/07/30 21:35:25  tony
 * 51587
 *
 * Revision 1.28  2003/07/11 17:04:06  tony
 * removed character
 *
 * Revision 1.27  2003/07/11 17:00:16  tony
 * updated logic to perform default functions for
 * the preference panel.
 *
 * Revision 1.26  2003/05/30 21:09:19  tony
 * updated messaging logic to popup on top
 * of the currently active Component.
 *
 * Revision 1.25  2003/05/06 14:24:14  tony
 * 50525
 *
 * Revision 1.24  2003/05/06 14:20:48  tony
 * 50525
 *
 * Revision 1.23  2003/05/06 00:07:19  tony
 * 50468
 *
 * Revision 1.22  2003/04/21 20:03:12  tony
 * enhanced scroll logic to improve flag editor.
 *
 * Revision 1.21  2003/04/11 20:02:28  tony
 * added copyright statements.
 *
 */
package com.ibm.eannounce.dialogpanels;
import com.elogin.*;
import com.ibm.eannounce.eobjects.*;
import com.ibm.eannounce.eforms.table.OrderTable;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;
import java.awt.*;
import java.util.Arrays;
import javax.swing.JMenuBar;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class OrderPanel extends AccessibleDialogPanel {
	private static final long serialVersionUID = 1L;
	private EPanel pnlNorth = new EPanel(new BorderLayout());
	private EPanel pnlLbl = new EPanel(new GridLayout(2,1,5,5));
	private EPanel pnlBox = new EPanel(new GridLayout(2,1,5,5));

	private EComboBox profBox = new EComboBox();
	private ELabel profLbl = new ELabel(getString("prof.pref") + COLON);
	private EComboBox entBox = new EComboBox();
	private ELabel entLbl = new ELabel(getString("entity") + COLON);

	private MetaEntityList mel = null;
	private OrderTable ot = new OrderTable();
	private EScrollPane scroll = new EScrollPane(ot);
//	private boolean bUpdateDefaults = false;

	private ProfileSet pSet = null;
	private MetaColumnOrderGroup mcog = null;									//51964

	/**
     * orderPanel
     * @author Anthony C. Liberto
     */
    public OrderPanel() {
		super(new BorderLayout());
		scroll.setFocusable(false);
		init();
		return;
	}

	/**
     * init
     * @author Anthony C. Liberto
     */
    public void init() {
        Dimension d = new Dimension(600,400);

        initComponents();			//50468
		pnlLbl.add(profLbl);
		pnlLbl.add(entLbl);

		pnlBox.add(profBox);
		pnlBox.add(entBox);

		profBox.setActionCommand("profile_selected");
		profBox.addActionListener(this);
		entBox.setActionCommand("entity_selected");
		entBox.addActionListener(this);

		pnlNorth.add("West", pnlLbl);
		pnlNorth.add("Center", pnlBox);

		add("North",pnlNorth);
		add("Center",scroll);
		setModalCursor(true);
		scroll.setPreferredSize(d);
		scroll.setSize(d);
		return;
	}

	private void dereferenceEntityBox() {
		if (entBox != null) {
			entBox.removeActionListener(this);
			entBox.removeAllItems();
			entBox.removeAll();
			entBox.removeNotify();
		}
		return;
	}

	private void dereferenceProfileBox() {
		if (profBox != null) {
			profBox.removeActionListener(this);
			profBox.removeAllItems();
			profBox.removeAll();
			profBox.removeNotify();
		}
		return;
	}

	/**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
		dereferenceProfileBox();
		dereferenceEntityBox();
		if (scroll != null) {
			scroll.dereference();
			scroll = null;
		}
		removeAll();
		removeNotify();
		return;
	}

	/**
     * loadProfileBox
     * @param _pSet
     * @author Anthony C. Liberto
     */
    public void loadProfileBox(ProfileSet _pSet) {
        Profile[] prof = null;
        int ii = -1;
        ProfileDisplay[] disp = null;
        if (_pSet != null && pSet != _pSet) {
			pSet = _pSet;
			profBox.removeAllItems();

			prof = pSet.toArray();
			ii = prof.length;
			disp = new ProfileDisplay[ii];
			for (int i=0;i<ii;++i) {
				disp[i] = new ProfileDisplay(prof[i]);
			}

			Arrays.sort(disp, new EComparator());

			for (int i=0;i<ii;++i) {
				profBox.addItem(disp[i]);
			}
			profBox.setSelectedItem(null);
//52727			adjustDefaultButton();
		}
		return;
	}

	private void loadEntityBox() {
		int ii = -1;
        entBox.removeAllItems();
		mel = eaccess().getMetaEntityList(getSelectedProfile());
		if (mel != null) {
			mel.setSortType(MetaEntityList.SORT_BY_LONG_DESCRIPTION);
			mel.performSort();
			ii = mel.getEntityGroupCount();
			for (int i=0;i<ii;++i) {
				entBox.addItem(mel.getLongDescription(i) + " (" + mel.getEntityType(i) + ")");
			}
			entBox.setSelectedItem(null);
			updateOrderModel(null);
			entBox.revalidate();
		}
		adjustDefaultButton();
		return;
	}

	/**
     * getEntityGroup
     * @param _i
     * @return
     * @author Anthony C. Liberto
     */
    public EntityGroup getEntityGroup(int _i) {
		return  dBase().getEntityGroup(mel,_i);
	}

	private void updateTable() {
		EntityGroup eg = getEntityGroup(entBox.getSelectedIndex());
		if (eg != null) {
			mcog = dBase().getMetaColumnOrderGroup(eg,this);
			if (mcog != null) {
				updateOrderModel(mcog.getTable("Order Table"));
			}
		}
		return;
	}

	private Profile getSelectedProfile() {
		Object o = profBox.getSelectedItem();
//50340		if (o != null && o instanceof ProfileDisplay) {
//50340			Profile out = (Profile)o;
		if (o != null && o instanceof ProfileDisplay) {				//50340
			Profile out = ((ProfileDisplay)o).getProfile();			//50340
			eaccess().setProcessTime(out,eaccess().getNow(END_OF_DAY));
			return out;
		}
		return null;
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

	/**
     * refreshMenu
     * @param _changed
     * @author Anthony C. Liberto
     */
    public void refreshMenu(boolean _changed) {
		return;
	}

	/**
     * refreshAppearance
     * @author Anthony C. Liberto
     */
    public void refreshAppearance() {
		entBox.refreshAppearance();
		ot.refreshAppearance();
		return;
	}

	/**
     * clear
     * @author Anthony C. Liberto
     */
    public void clear() {
		updateOrderModel(null);
		resetMetaEntityList();
		return;
	}

	/**
     * resetMetaEntityList
     * @author Anthony C. Liberto
     */
    public void resetMetaEntityList() {
		dereferenceEntityBox();
		mel = null;
		return;
	}

	/**
     * commit
     * @param _default
     * @author Anthony C. Liberto
     */
    public void commit(boolean _default) {
		setUpdateDefaults(_default);
		ot.commit();
		return;
	}

	/**
     * rollback
     * @author Anthony C. Liberto
     */
    public void rollback() {
		if (!isDefaultCapable()) {													//tbd_tr
			if (mcog != null) {														//51964
				try {																//51964
					mcog.resetToDefaults(null, getRemoteDatabaseInterface());		//51964
				} catch (Exception _x) {											//51964
					_x.printStackTrace();											//51964
				}																	//51964
			}																		//51964
		}																			//tbd_tr
		ot.rollback();
		return;
	}

	/**
     * rollbackDefault
     * @author Anthony C. Liberto
     */
    public void rollbackDefault() {
		ot.rollbackDefault();
		return;
	}

	/**
     * \setUpdateDefaults
     * @param _default
     * @author Anthony C. Liberto
     */
    public void setUpdateDefaults(boolean _default) {
		ot.setUpdateDefaults(_default);
		return;
	}


	private void updateOrderModel(MetaColumnOrderTable _ctm) {
		ot.updateOrderModel(_ctm);
		return;
	}

	/**
     * actionPerformed
     * @author Anthony C. Liberto
     * @param _action
     */
    public void actionPerformed(String _action) {
		if (isModalBusy()) {
			return;
		}
		setModalBusy(true);
		if (_action.equals("exit")) {
			disposeDialog();
		} else if (_action.equals("entity_selected")) {
			updateTable();
			adjustButtonEnabled();									//50525
		} else if (_action.equals("profile_selected")) {
			loadEntityBox();
			adjustButtonEnabled();									//50525
//		} else if (_action.equals("save")) {
//			commit();
//		} else if (_action.equals("reset")) {
//			rollback();
		}
		setModalBusy(false);
		return;
	}

	/**
     * isDefaultCapable
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isDefaultCapable() {
		Profile prof = getSelectedProfile();
		if (prof != null) {
			return prof.hasRoleFunction(Profile.ROLE_FUNCTION_ATTR_ORDER_DEFAULT);
		}
		return false;
	}

	/**
     * canSave
     * @return
     * @author Anthony C. Liberto
     */
    public boolean canSave() {					//50525
		if (ot != null && ot.hasRows()) {		//50525
			return true;						//50525
		}										//50525
		return false;							//50525
	}											//50525

	/**
     * adjustDefaultButton
     * @author Anthony C. Liberto
     */
    public void adjustDefaultButton() {}
	/**
     * adjustButtonEnabled
     * @author Anthony C. Liberto
     */
    public void adjustButtonEnabled() {} 		//50525

	/**
     * disposeDialog
     * @author Anthony C. Liberto
     */
    public void disposeDialog() {
		super.disposeDialog();
		return;
	}

	/**
     * isResizable
     * @author Anthony C. Liberto
     * @return boolean
     */
    public boolean isResizable() {
		return true;
	}

/*
added per 50340
*/
	private class ProfileDisplay {
		private Profile prof = null;

		/**
         * ProfileDisplay
         * @param _prof
         * @author Anthony C. Liberto
         */
        protected ProfileDisplay(Profile _prof) {
			prof = _prof;
			return;
		}

		/**
         * @see java.lang.Object#toString()
         * @author Anthony C. Liberto
         */
        public String toString() {
			if (prof != null) {
				return prof.getWGName() + ":  " + prof.toString();
			}
			return null;
		}

		/**
         * equals
         * @param _prof
         * @return
         * @author Anthony C. Liberto
         */
        public boolean equals(Profile _prof) {
			if (prof != null) {													//52727
				if (prof.getEnterprise().equals(_prof.getEnterprise())) {		//52727
					if (prof.getOPWGID() == _prof.getOPWGID()) {				//52727
						return true;											//52727
					}															//52727
				}																//52727
			}																	//52727
			return false;														//52727
//52727			return (_prof == getProfile());
		}

		/**
         * getProfile
         * @return
         * @author Anthony C. Liberto
         */
        public Profile getProfile() {
			return prof;
		}

		/**
         * dereference
         * @author Anthony C. Liberto
         */
        public void dereference() {
			prof = null;
			return;
		}
	}
/*
 50468
*/
	private void initComponents() {
		profBox.setUseDefined(false);
		profLbl.setUseDefined(false);
		profLbl.setLabelFor(profBox);
		entBox.setUseDefined(false);
		entLbl.setUseDefined(false);
		entLbl.setLabelFor(entBox);
		ot.setUseDefined(false);
		return;
	}

	/**
     * loadProfile
     * @param _prof
     * @param _eType
     * @author Anthony C. Liberto
     */
    public void loadProfile(Profile _prof, String _eType) {
		if (_prof != null) {
			profBox.setSelectedItem(getProfileDisplay(_prof));
			loadEntityBox();
//52727			adjustButtonEnabled();
			if (_eType != null) {
				EntityGroup eg = getEntityGroup(_eType);
				if (eg != null) {
					mcog = dBase().getMetaColumnOrderGroup(eg,this);
					if (mcog != null) {
						updateOrderModel(mcog.getTable("Order Table"));
					}
				}
			}
		}
		adjustButtonEnabled();				//52727
		return;
	}

	private EntityGroup getEntityGroup(String _eType) {
		if (mel != null) {											//51587
			int ii = mel.getEntityGroupCount();
			for (int i=0;i<ii;++i) {
				if (_eType.equals(mel.getEntityType(i))) {
					entBox.setSelectedIndex(i);
					return getEntityGroup(i);
				}
			}
		}															//51587
		return null;
	}

	private ProfileDisplay getProfileDisplay(Profile _prof) {
		if (_prof != null) {
			int ii = profBox.getItemCount();
			for (int i=0;i<ii;++i) {
				Object o = profBox.getItemAt(i);
				if (o != null && o instanceof ProfileDisplay) {
					if (((ProfileDisplay)o).equals(_prof)) {
						return (ProfileDisplay)o;
					}
				}
			}
		}
		return null;
	}
}
