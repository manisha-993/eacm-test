package com.ibm.rdh.chw.caller;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.rdh.chw.entity.CHWAnnouncement;
import com.ibm.rdh.chw.entity.TypeModelUPGGeo;
import com.ibm.rdh.rfc.Char_descrTable;
import com.ibm.rdh.rfc.Char_descrTableRow;
import com.ibm.rdh.rfc.Char_valsTable;
import com.ibm.rdh.rfc.Char_valsTableRow;
import com.ibm.rdh.rfc.CharactsTable;
import com.ibm.rdh.rfc.CharactsTableRow;
import com.ibm.rdh.rfc.Chv_descrTable;
import com.ibm.rdh.rfc.Chv_descrTableRow;
import com.ibm.rdh.rfc.Zdm_geo_to_classTable;
import com.ibm.rdh.rfc.Zdm_geo_to_classTableRow;

public class R201createUpgradeValueForTypeMCCharacteristic extends Rfc {

	private com.ibm.rdh.rfc.Z_DM_SAP_CHAR_MAINTAIN rfc;

	public R201createUpgradeValueForTypeMCCharacteristic(
			TypeModelUPGGeo typeModelUpg, CHWAnnouncement chwA,
			String FROMTOTYPE, String pimsIdentity) throws Exception {
		reInitialize();
		String charac = null;
		if ("FROMTYPE".equalsIgnoreCase(FROMTOTYPE)) {
			charac = "MK_" + typeModelUpg.getFromType() + "_MTC";
		}
		if ("TOTYPE".equalsIgnoreCase(FROMTOTYPE)) {
			charac = "MK_" + typeModelUpg.getType() + "_MTC";
		}
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
		// add 20160504
		c0Row.setNegVals("X");
		c0Row.setAdditVals("X");
		// add end

		c0Table.appendRow(c0Row);
		rfc.setICharacts(c0Table);

		rfcInfo.append("CHARACTS \n");
		rfcInfo.append(Tab + "CHARACT>>" + c0Row.getCharact() + ", DATATYPE>>"
				+ c0Row.getDatatype() + ", CHARNUMBER>>"
				+ c0Row.getCharnumberString() + ", STATUS>>"
				+ c0Row.getStatus() + ", VALASSIGNM>>" + c0Row.getValassignm()
				+ "\n");

		rfcInfo.append(Tab + "NEGVALS>>" + c0Row.getNegVals() + ", ADDITVALS>>"
				+ c0Row.getAdditVals() + "\n");

		// This part will be optional,but now is required.
		// CHAR_DESCR - C1
		Char_descrTable c1Table = new Char_descrTable();
		Char_descrTableRow c1Row = c1Table.createEmptyRow();

		c1Row.setCharact(charac);
		c1Row.setLanguage("E");
		c1Row.setChdescr("Models");

		c1Table.appendRow(c1Row);
		rfc.setICharDescr(c1Table);

		rfcInfo.append("CHAR_DESCR  \n");
		rfcInfo.append(Tab + "CHARACT>>" + c1Row.getCharact() + ", LANGUAGE>>"
				+ c1Row.getLanguage() + ", CHDESCR>>" + c1Row.getChdescr()
				+ "\n");
		// end

		// CHAR_VALS - C4
		Char_valsTable c4Table = new Char_valsTable();
		Char_valsTableRow c4Row = c4Table.createEmptyRow();

		c4Row.setCharact(charac);
		c4Row.setValue(typeModelUpg.getFromType() + typeModelUpg.getFromModel()
				+ "_" + typeModelUpg.getType() + typeModelUpg.getModel());
		c4Row.setFldelete("X");

		c4Table.appendRow(c4Row);
		rfc.setICharVals(c4Table);

		rfcInfo.append("CHAR_VALS  \n");
		rfcInfo.append(Tab + "CHARACT>>" + c4Row.getCharact() + ", VALUE>>"
				+ c4Row.getValue() + ", FLDELETE>>" + c4Row.getFldelete()
				+ "\n");

		// CHV_DESCR - C5
		Chv_descrTable c5Table = new Chv_descrTable();
		Chv_descrTableRow c5Row = c5Table.createEmptyRow();

		c5Row.setCharact(charac);
		c5Row.setValue(typeModelUpg.getFromType() + typeModelUpg.getFromModel()
				+ "_" + typeModelUpg.getType() + typeModelUpg.getModel());
		c5Row.setLanguage("E");
		c5Row.setValdescr("From" + typeModelUpg.getFromType() + "Model"
				+ typeModelUpg.getFromModel() + "to" + typeModelUpg.getType()
				+ "Model" + typeModelUpg.getModel());

		c5Table.appendRow(c5Row);
		rfc.setIChvDescr(c5Table);

		rfcInfo.append("CHVDESCR \n");
		rfcInfo.append(Tab + "CHARACT>>" + c5Row.getCharact() + ", VALUE>>"
				+ c5Row.getValue() + ", LANGUAGE>>" + c5Row.getLanguage()
				+ ", VALDECSR>>" + c5Row.getValdescr() + "\n");

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
		rfcInfo.append(Tab + "PIMSIdentity>>" + pimsIdentity + "\n");

		// RFANUMBER
		rfc.setRfaNum(chwA.getAnnDocNo());
		rfcInfo.append("RFANUM \n");
		rfcInfo.append(Tab + "RFANumber>>" + chwA.getAnnDocNo() + "\n");

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
		return "Delete Upgrade value for type MTC characteristic";
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
