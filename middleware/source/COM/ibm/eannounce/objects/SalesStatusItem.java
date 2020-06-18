//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: SalesStatusItem.java,v $
// Revision 1.2  2005/10/31 22:25:59  joan
// fixes
//
// Revision 1.1  2005/10/31 22:09:48  joan
// initial load
//

package COM.ibm.eannounce.objects;


import COM.ibm.opicmpdh.middleware.Profile;

import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;


/**
 * SalesStatusItem
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SalesStatusItem extends EANMetaEntity {

	private	String MATNR = null;
	private String VARCOND = null;
	private String VARCONDTYPE = null;
	private String SALESORG = null;
	private String MATERIALSTATUS = null;
	private String MATERIALSTATUSDATE = null;
	private String LASTUPDATED = null;
	private String MARKEDFORDELETION = null;


    /**
     * SalesStatusItem
     *
     * @param _prof
     * @param _ei
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public SalesStatusItem(Profile _prof, String _strKey) throws MiddlewareRequestException {
        super(null, _prof, _strKey);

    }

	public String getMATNR() {
		return MATNR;
	}

	public String getVARCOND() {
		return VARCOND;
	}

	public String getVARCONDTYPE() {
		return VARCONDTYPE;
	}

	public String getSALESORG() {
		return SALESORG;
	}

	public String getMATERIALSTATUS() {
		return MATERIALSTATUS;
	}

	public String getMATERIALSTATUSDATE() {
		return MATERIALSTATUSDATE;
	}

	public String getLASTUPDATED() {
		return LASTUPDATED;
	}

	public String getMARKEDFORDELETION() {
		return MARKEDFORDELETION;
	}

	public void setMATNR(String _s) {
		MATNR = _s;
	}

	public void setVARCOND(String _s) {
		VARCOND = _s;
	}

	public void setVARCONDTYPE(String _s) {
		VARCONDTYPE = _s;
	}

	public void setSALESORG(String _s) {
		SALESORG = _s;
	}

	public void setMATERIALSTATUS(String _s) {
		MATERIALSTATUS = _s;
	}

	public void setMATERIALSTATUSDATE(String _s) {
		MATERIALSTATUSDATE = _s;
	}

	public void setLASTUPDATED(String _s) {
		LASTUPDATED = _s;
	}

	public void setMARKEDFORDELETION(String _s) {
		MARKEDFORDELETION = _s;
	}


    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {
        StringBuffer strbResult = new StringBuffer();
        strbResult.append("SalesStatusItem:" + ":" + getKey() + "\n");
        strbResult.append("MATNR = " + MATNR  + "\n");
		strbResult.append("VARCOND = " + VARCOND  + "\n");
		strbResult.append("VARCONDTYPE = " + VARCONDTYPE  + "\n");
		strbResult.append("SALESORG = " + SALESORG  + "\n");
		strbResult.append("MATERIALSTATUS = " + MATERIALSTATUS  + "\n");
		strbResult.append("MATERIALSTATUSDATE = " + MATERIALSTATUSDATE  + "\n");
		strbResult.append("LASTUPDATED = " + LASTUPDATED  + "\n");
		strbResult.append("MARKEDFORDELETION = " + MARKEDFORDELETION  + "\n");

        return new String(strbResult);
    }
}
