//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;

public class HWPermutationItem extends EANDataFoundation {
	private Boolean m_bSelected = null;
	private String m_strProcessor = null;
	private String m_strInteractive = null;

	public HWPermutationItem(Profile _prof, boolean _b, String _strProcessor, String _strInteractive) throws MiddlewareRequestException {
		super(null, _prof, _strProcessor + _strInteractive);
		m_bSelected = new Boolean(_b);
		m_strProcessor = _strProcessor;
		m_strInteractive = _strInteractive;
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
	public void setProcessor(String _s) {
		m_strProcessor = _s;
	}

	public String getProcessor() {
		return m_strProcessor;
	}

	public void setInteractive(String _s) {
		m_strInteractive = _s;
	}

	public String getInteractive() {
		return m_strInteractive;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(m_strProcessor);
		if (m_strInteractive != null && m_strInteractive.length() > 0) {
			sb.append("/" + m_strInteractive);
		}
		return sb.toString();
	}

	public String dump(boolean _bBrief) {
    	StringBuffer strbResult = new StringBuffer();
    	strbResult.append("\n	HWPermutationItem:" + getKey() + ":");
    	if (!_bBrief) {
  			strbResult.append("\n		m_bSelected: " + m_bSelected);
  			strbResult.append("\n		m_strProcessor: " + m_strProcessor);
  			strbResult.append("\n		m_strInteractive: " + m_strInteractive);
		}

    	return new String(strbResult);

	}

}
