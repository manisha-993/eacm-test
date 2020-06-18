//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;


public class HWUpgradeItem extends EANDataFoundation {
	private HWUpgradeMG m_fromMG = null;
	private HWUpgradeMG m_toMG = null;
	Boolean m_bSelected = null;
	boolean m_bEditable = true;

	/**
	* @serial
	*/
	static final long serialVersionUID = 1L;

	public String getVersion() {
		return new String("$Id: HWUpgradeItem.java,v 1.5 2008/02/01 22:10:07 wendy Exp $");
	}

	/**
	* Main method which performs a simple test of this class
	*/
	public static void main(String arg[]) {
	}

	public HWUpgradeItem(HWUpgradeItem _hwui) throws MiddlewareRequestException {
		super(null,_hwui.getProfile(), _hwui.getKey());
		setFromUpgradeMG(_hwui.getFromUpgradeMG());
		setToUpgradeMG(_hwui.getToUpgradeMG());

  	}

	public HWUpgradeItem(EANFoundation _f, Profile _prof, HWUpgradeMG _fromMG, HWUpgradeMG _toMG) throws MiddlewareRequestException {
		super(_f, _prof, _fromMG.getKey() + _toMG.getKey());
		setFromUpgradeMG(_fromMG);
		setToUpgradeMG(_toMG);
		m_bSelected = new Boolean(false);
  	}

	public String dump(boolean _bBrief) {
    	StringBuffer strbResult = new StringBuffer();
    	strbResult.append("\nHWUpgradeItem: " + getKey() + ":");
    	strbResult.append("isSelected: " + m_bSelected + ", isEditable: " + m_bEditable);
    	if (!_bBrief) {
			strbResult.append(m_fromMG.dump(_bBrief));
			strbResult.append(m_toMG.dump(_bBrief));
		}
    	return new String(strbResult);

	}

	protected void setFromUpgradeMG(HWUpgradeMG _mg) {
		m_fromMG = _mg;
	}

	public HWUpgradeMG getFromUpgradeMG() {
		return m_fromMG;
	}

	protected void setToUpgradeMG(HWUpgradeMG _mg) {
		m_toMG = _mg;
	}

	public HWUpgradeMG getToUpgradeMG() {
		return m_toMG;
	}

	public void setSelected(boolean _b) {
		m_bSelected = new Boolean(_b);
	}

	public boolean isSelected() {
		return m_bSelected.booleanValue();
	}

	public Boolean getSelected() {
		return m_bSelected;
	}

	protected void setEditable(boolean _b) {
		m_bEditable = _b;
	}

	public boolean isEditable() {
		return m_bEditable;
	}

	public String toString() {
		return getKey();
	}
}
