//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//

package COM.ibm.eannounce.objects;

import java.util.*;

import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;
import COM.ibm.opicmpdh.middleware.Profile;

// Exceptions
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.LockException;

import java.sql.SQLException;
import java.rmi.RemoteException;

public class HWUpgradeList extends EANMetaEntity implements EANTableWrapper {

	/**
	* @serial
	*/
	static final long serialVersionUID = 1L;
	private EANList m_updateList = new EANList();
	/**
	* Main method which performs a simple test of this class
	*/
	public static void main(String arg[]) {
	}

	public HWUpgradeList (Profile _prof, String _strKey) throws MiddlewareRequestException {
    	super(null , _prof, _strKey);
    }

  	public String dump(boolean _bBrief) {
	    StringBuffer strbResult = new StringBuffer();
      	strbResult.append("HWUpgradeList: " + getKey());
      	if (!_bBrief) {
			for (int i = 0; i < getHWUpgradeItemCount(); i++) {
				HWUpgradeItem hwui = getHWUpgradeItem(i);
				strbResult.append(hwui.dump(_bBrief));
			}
		}
	    return new String(strbResult);
	}


	/*
	* return the column information here
	*/
	public EANList getColumnList() {
		EANList colList = new EANList();
		for (int i = 0; i < getHWUpgradeItemCount(); i++) {
			HWUpgradeItem hwui = getHWUpgradeItem(i);
			HWUpgradeMG mg = hwui.getToUpgradeMG();
			try {
				colList.put(new Implicator(mg, getProfile(), mg.getKey()));
			}	catch (Exception x) {
				x.printStackTrace();
			}
		}

		return colList;
	}

	/*
	*	Return only visible rows
	*/
	public EANList getRowList() {
		EANList rowList = new EANList();
		for (int i = 0; i < getHWUpgradeItemCount(); i++) {
			HWUpgradeItem hwui = getHWUpgradeItem(i);
			HWUpgradeMG mg = hwui.getFromUpgradeMG();
			try {
				rowList.put(new Implicator(mg, getProfile(), mg.getKey()));
			}	catch (Exception x) {
				x.printStackTrace();
			}
		}

		return rowList;
	}

	/*
	* Returns a basic table adaptor for client rendering of EntityLists
	*/
	public RowSelectableTable getTable() {
	    return new RowSelectableTable(this, "HW Upgrade From\\To");
	}

  public void commit(Database _db, RemoteDatabaseInterface _rdi) throws EANBusinessRuleException, MiddlewareBusinessRuleException, RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, SQLException {
 	}

	public boolean canEdit() {
		return true;
	}

	public boolean canCreate() {
		return false;
	}

	public boolean addRow() {
		return false;
	}

	public boolean addRow(String _strKey) {
		return false;
	}

	public void removeRow(String _strKey) {
	}

 	public boolean hasChanges() {
		return false;
 	}

	public int getHWUpgradeItemCount() {
		return getDataCount();
	}

 	protected void putHWUpgradeItem(HWUpgradeItem _hwui) {
		putData(_hwui);
	}

	public HWUpgradeItem getHWUpgradeItem(int _i) {
		return (HWUpgradeItem)getData(_i);
	}

	public HWUpgradeItem getHWUpgradeItem(String _s) {
		return (HWUpgradeItem)getData(_s);
	}

	public void removeHWUpgradeItem() {
		resetData();
		m_updateList = new EANList();
	}

	public void removeHWUpgradeItem(String _str) {
		getData().remove(_str);
	}

	public void removeHWUpgradeItem(int _i) {
		getData().remove(_i);
	}

 	public boolean equivalent(EntityItem[] _eia, EANActionItem _ai) {
		return false;
	}

	public Object getMatrixValue(String _str) {
		StringTokenizer st = new StringTokenizer(_str, ":");
		String strFromMG = st.nextToken();
	    String strToMG = st.nextToken();

	    HWUpgradeItem hwui = getHWUpgradeItem(strFromMG + strToMG);
		if (hwui != null) {
			return hwui.getSelected();
		} else {
			return null;
		}
	}

	public void putMatrixValue(String _str, Object _o) {
		if (!(_o instanceof Boolean)) return;
		StringTokenizer st = new StringTokenizer(_str, ":");
		String strFromMG = st.nextToken();
	    String strToMG = st.nextToken();

	    HWUpgradeItem hwui = getHWUpgradeItem(strFromMG + strToMG);
	  	if (hwui != null) {
			if (hwui.isEditable()) {
				boolean b = ((Boolean)_o).booleanValue();
				hwui.setSelected(b);
				if (b) {
					m_updateList.put(hwui);
				} else {
					m_updateList.remove(hwui);
				}
			}
		} else {
			System.out.println("HWUpgradeList putMatrixValue hwui is null ");
		}
	}

	public EANList getUpdateList() {
		return m_updateList;
	}

	public boolean isMatrixEditable(String _str) {
		StringTokenizer st = new StringTokenizer(_str, ":");
		String strFromMG = st.nextToken();
		String strToMG = st.nextToken();

		HWUpgradeItem hwui = getHWUpgradeItem(strFromMG + strToMG);
		if (hwui != null) {
			return hwui.isEditable();
		} else {
			return false;
		}
	}

	public void rollbackMatrix() {}
	public void addColumn(EANFoundation _ean) { }
	public EntityList generatePickList(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRelatorType) {
		return null;
	}

	public boolean removeLink(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRowKey)	throws EANBusinessRuleException {
		return false;
	}

	public EANFoundation[] link(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRowKey, EntityItem[] _aeiChild, String _strLinkOption) throws EANBusinessRuleException {
		return null;
	}

	public EntityList create(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRelatorType) {
		return null;
	}

  public EntityList edit(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String[] _astrKey) throws SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException,RemoteException {
		return null;
	}

	public WhereUsedList getWhereUsedList(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRelatorType) {
		return null;
	}

	public Object[] getActionItemsAsArray(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strKey) {
		return null;
	}

	public boolean isDynaTable() {
		return false;
	}
 	public boolean duplicate(String _strKey, int _iDup) {
 		return false;
 	}
	public Object linkAndRefresh(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, LinkActionItem _lai) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, EANBusinessRuleException, LockException, WorkflowException, RemoteException {
		return null;
	}
}
