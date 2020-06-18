//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;


public class HWUpgradeMG extends EANDataFoundation {
	private EntityItem m_eiProc = null;
	private EntityItem m_eiICard = null;
	private String m_strMachType = null;
	private String m_strModel = null;
	private String m_strProcFC = null;
	private String m_strICardFC = null;

	/**
	* @serial
	*/
	static final long serialVersionUID = 1L;

	public String getVersion() {
		return new String("$Id: HWUpgradeMG.java,v 1.5 2008/02/01 22:10:08 wendy Exp $");
	}

	/**
	* Main method which performs a simple test of this class
	*/
	public static void main(String arg[]) {
	}

	public HWUpgradeMG(HWUpgradeMG _mg) throws MiddlewareRequestException {
		super(null,_mg.getProfile(), _mg.getKey());
		setProcEntityItem(_mg.getProcEntityItem());
		setICardEntityItem(_mg.getICardEntityItem());
		setMachType(_mg.getMachType());
		setModel(_mg.getModel());
		setProcFC(_mg.getProcFC());
		setICardFC(_mg.getICardFC());
  	}

	public HWUpgradeMG(EANFoundation _f, Profile _prof, EntityItem _eiMG, EntityItem _eiProc, EntityItem _eiICard, String _strMachType, String _strModel, String _strProcFC, String _strICardFC) throws MiddlewareRequestException {
		super(_f, _prof, _eiProc.getKey() +(_eiICard != null? _eiICard.getKey() : "") );
		setProcEntityItem(_eiProc);
		setICardEntityItem(_eiICard);
		setMachType(_strMachType);
		setModel(_strModel);
		setProcFC(_strProcFC);
		setICardFC(_strICardFC);
  	}

	public String dump(boolean _bBrief) {
    	StringBuffer strbResult = new StringBuffer();
    	strbResult.append("\nHWUpgradeMG:" + getKey() + ":");
    	if (!_bBrief) {
			strbResult.append("Processor EntityItem: " + m_eiProc.getKey());
			if (m_eiICard != null) {
				strbResult.append("ICard EntityItem: " + m_eiICard.getKey());
			}
			strbResult.append(toString());
		}

    	return new String(strbResult);

	}

	protected void setProcEntityItem(EntityItem _ei) {
		m_eiProc = _ei;
	}

	public EntityItem getProcEntityItem() {
		return m_eiProc;
	}

	protected void setICardEntityItem(EntityItem _ei) {
		m_eiICard = _ei;
	}

	public EntityItem getICardEntityItem() {
		return m_eiICard;
	}

	protected void setMachType(String _s) {
		m_strMachType = _s;
	}

	public String getMachType() {
		return m_strMachType;
	}

	protected void setModel(String _s) {
		m_strModel = _s;
	}

	public String getModel() {
		return m_strModel;
	}

	protected void setProcFC(String _s) {
		m_strProcFC = _s;
	}

	public String getProcFC() {
		return m_strProcFC;
	}

	protected void setICardFC(String _s) {
		m_strICardFC = _s;
	}

	public String getICardFC() {
		return m_strICardFC;
	}

	public String getLongDescription() {
		return toString();
	}

	public String getShortDescription() {
		return toString();
	}

	public String toString() {
		StringBuffer strbResult = new StringBuffer();
	 	strbResult.append(m_strMachType + "-" + m_strModel + " " + m_strProcFC);
	 	if (m_strICardFC != null && m_strICardFC.length() > 0) {
			strbResult.append("/" + m_strICardFC);
		}
    	return new String(strbResult);
	}
}
