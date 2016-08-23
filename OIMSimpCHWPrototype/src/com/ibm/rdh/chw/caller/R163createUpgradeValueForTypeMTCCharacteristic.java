package com.ibm.rdh.chw.caller;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.rdh.chw.entity.CHWAnnouncement;
import com.ibm.rdh.chw.entity.TypeModelUPGGeo;
import com.ibm.rdh.rfc.Char_valsTable;
import com.ibm.rdh.rfc.Char_valsTableRow;
import com.ibm.rdh.rfc.CharactsTable;
import com.ibm.rdh.rfc.CharactsTableRow;
import com.ibm.rdh.rfc.Chv_descrTable;
import com.ibm.rdh.rfc.Chv_descrTableRow;
import com.ibm.rdh.rfc.E1cawnmTable;
import com.ibm.rdh.rfc.E1cawnmTableRow;
import com.ibm.rdh.rfc.E1cutxmTable;
import com.ibm.rdh.rfc.E1cutxmTableRow;
import com.ibm.rdh.rfc.Zdm_geo_to_classTable;
import com.ibm.rdh.rfc.Zdm_geo_to_classTableRow;

public class R163createUpgradeValueForTypeMTCCharacteristic extends Rfc {

	private com.ibm.rdh.rfc.Z_DM_SAP_CHAR_MAINTAIN rfc;

	public R163createUpgradeValueForTypeMTCCharacteristic(String type,
			TypeModelUPGGeo typeModelUpg, CHWAnnouncement chwA,
			String pimsIdentity) throws Exception {
		reInitialize();
		String charac = "MK_" + type + "_MTC";
		String characupg = "MK_" + type + "_MTC";
		rfcName = "Z_DM_SAP_CHAR_MAINTAIN";

		rfc = new com.ibm.rdh.rfc.Z_DM_SAP_CHAR_MAINTAIN();

		// Set up the RFC fields
		// CHARACTS
		CharactsTable c0Table = new CharactsTable();
		CharactsTableRow c0Row = c0Table.createEmptyRow();

		c0Row.setCharact(charac);
		c0Row.setDatatype("CHAR");
		c0Row.setCharnumberString("15");
		c0Row.setStatus("1");
		c0Row.setValassignm("S");

		c0Table.appendRow(c0Row);
		rfc.setICharacts(c0Table);

		rfcInfo.append("CHARACTS \n");
		rfcInfo.append(Tab + "CHARACT>>" + c0Row.getCharact() + ", DATATYPE>>"
				+ c0Row.getDatatype() + ", CHARNUMBER>>"
				+ c0Row.getCharnumberString() + ", STATUS>>"
				+ c0Row.getStatus() + ", VALASSIGNM>>" + c0Row.getValassignm()
				+ "\n");

		// CHAR_VALS - C4
		Char_valsTable c4Table = new Char_valsTable();
		Char_valsTableRow c4Row = c4Table.createEmptyRow();

		c4Row.setCharact(charac);
		c4Row.setValue(typeModelUpg.getFromType() + typeModelUpg.getFromModel()
				+ "_" + typeModelUpg.getType() + typeModelUpg.getModel());

		c4Table.appendRow(c4Row);
		rfc.setICharVals(c4Table);

		rfcInfo.append("CHAR_VALS  \n");
		rfcInfo.append(Tab + "CHARACT>>" + c4Row.getCharact() + ", VALUE>>"
				+ c4Row.getValue() + "\n");

		// CHV_DESCR - C5
		Chv_descrTable c5Table = new Chv_descrTable();
		Chv_descrTableRow c5Row = c5Table.createEmptyRow();

		c5Row.setCharact(characupg);
		c5Row.setValue(typeModelUpg.getFromType() + typeModelUpg.getFromModel()
				+ "_" + typeModelUpg.getType() + typeModelUpg.getModel());
		c5Row.setLanguage("E");
		c5Row.setValdescr("From " + typeModelUpg.getFromType()
				+ typeModelUpg.getFromModel() + " to " + typeModelUpg.getType()
				+ typeModelUpg.getModel());

		c5Table.appendRow(c5Row);
		rfc.setIChvDescr(c5Table);

		rfcInfo.append("CHVDESCR \n");
		rfcInfo.append(Tab + "CHARACT>>" + c5Row.getCharact() + ", VALUE>>"
				+ c5Row.getValue() + ", LANGUAGE>>" + c5Row.getLanguage()
				+ ", VALDECSR>>" + c5Row.getValdescr() + "\n");

		// E1CAWNM STRUCTURE
		E1cawnmTable e1Table = new E1cawnmTable();
		E1cawnmTableRow e1Row = e1Table.createEmptyRow();

		e1Row.setAtnam(characupg);
		e1Row.setAtwrt(typeModelUpg.getFromType() + typeModelUpg.getFromModel()
				+ "_" + typeModelUpg.getType() + typeModelUpg.getModel());

		e1Table.appendRow(e1Row);
		rfc.setIE1cawnm(e1Table);

		rfcInfo.append("E1CAWNM \n");
		rfcInfo.append(Tab + "ATNAM>>" + e1Row.getAtnam() + ", ATWRT>>"
				+ e1Row.getAtwrt() + "\n");

		// E1CUTXM
		E1cutxmTable e1CutTable = new E1cutxmTable();
		E1cutxmTableRow e1CutRow = e1CutTable.createEmptyRow();

		e1CutRow.setTxtLine(chwA.getAnnDocNo());

		e1CutTable.appendRow(e1CutRow);
		rfc.setIE1cutxm(e1CutTable);

		rfcInfo.append("E1CUTXM \n");
		rfcInfo.append(Tab + "TDLINE>>" + e1CutRow.getTxtLine() + "\n");

		// ZDM_GEO_TO_CLASS
		Zdm_geo_to_classTable zdmTable = new Zdm_geo_to_classTable();
		Zdm_geo_to_classTableRow zdmRow = zdmTable.createEmptyRow();

		zdmRow.setZGeo("US");

		zdmTable.appendRow(zdmRow);
		rfc.setGeoData(zdmTable);

		rfcInfo.append("ZDM_GEO_TO_CLASS \n");
		rfcInfo.append(Tab + "GEO>>" + zdmRow.getZGeo() + "\n");

		// PIMSIdentity
		rfc.setPimsIdentity(pimsIdentity);
		rfcInfo.append("PIMSIdentity \n");
		rfcInfo.append(Tab + "PIMSIdentity>>" + rfc.getPimsIdentity() + "\n");

		// RFANUMBER
		rfc.setRfaNum(chwA.getAnnDocNo());
		rfcInfo.append("RFANUM \n");
		rfcInfo.append(Tab + "RFANumber>>" + chwA.getAnnDocNo() + "\n");

		// REFRESH_VALS
		rfc.setRefreshVals("X");
		rfcInfo.append("REFRESH_VALS \n");
		rfcInfo.append(Tab + "REFRESH_VALS>>" + rfc.getRefreshVals() + "\n");

	}

	@Override
	public void execute() throws Exception {
		logExecution();
		getRfc().execute();
		getLog().debug(getErrorInformation());
		if (getSeverity() == ERROR) {
			throw new HWPIMSAbnormalException(getErrorInformation());
		}
	}

	@Override
	public String getTaskDescription() {
		StringBuffer sb = new StringBuffer();
		sb.append(" " + getMaterialName());
		return sb.toString();
	}

	@Override
	protected boolean isSuccessful() {
		boolean ans = false;
		int rc = getRfc().getRfcrc();
		if (0 == rc) {
			ans = true;
		}
		return ans;
	}

	@Override
	protected String getErrorInformation() {
		String ans;
		if (isSuccessful()) {
			ans = RFC_OK_MESSAGE;
		} else {
			ans = formatRfcErrorMessage(getRfc().getRfcrc(), getRfc()
					.getErrorText());
		}
		return ans;
	}

	@Override
	protected String getMaterialName() {
		return "Create Upgrade value for type MTC characteristic";
	}

	@Override
	public String getRfcName() {
		return ClassUtil.getSimpleClassName(getRfc());
	}

	private com.ibm.rdh.rfc.Z_DM_SAP_CHAR_MAINTAIN getRfc() {
		return rfc;
	}

	public void evaluate() throws Exception {
		execute();
	}
}
