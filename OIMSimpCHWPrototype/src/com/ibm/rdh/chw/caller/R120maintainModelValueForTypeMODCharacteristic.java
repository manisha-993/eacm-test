package com.ibm.rdh.chw.caller;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.rdh.chw.entity.CHWAnnouncement;
import com.ibm.rdh.chw.entity.TypeModel;
import com.ibm.rdh.rfc.Char_descrTable;
import com.ibm.rdh.rfc.Char_descrTableRow;
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
import com.ibm.rdh.rfc.Z_DM_SAP_CHAR_MAINTAIN;
import com.ibm.rdh.rfc.Zdm_geo_to_classTable;
import com.ibm.rdh.rfc.Zdm_geo_to_classTableRow;

public class R120maintainModelValueForTypeMODCharacteristic extends Rfc {

	private com.ibm.rdh.rfc.Z_DM_SAP_CHAR_MAINTAIN rfc;

	public R120maintainModelValueForTypeMODCharacteristic(TypeModel typeModel,
			CHWAnnouncement chwA, String pimsIdentity) throws Exception {
		reInitialize();

		rfcName = "Z_DM_SAP_CHAR_MAINTAIN";
		rfc = new com.ibm.rdh.rfc.Z_DM_SAP_CHAR_MAINTAIN();
		// Set up the RFC fields
		// CHARACTS
		CharactsTable c0Table = new CharactsTable();
		CharactsTableRow c0Row = c0Table.createEmptyRow();
		String charac = "MK_" + typeModel.getType() + "_MOD";
		c0Row.setCharact(charac);
		c0Row.setDatatype("CHAR");
		c0Row.setCharnumberString("3");
		c0Row.setStatus("1");
		c0Row.setValassignm("S");

		// This will be optional, but we need it now.
		c0Row.setAdditVals("X");
		c0Row.setNegVals("X");
		// end

		c0Table.appendRow(c0Row);
		rfc.setICharacts(c0Table);

		rfcInfo.append("CHARACTS \n");
		rfcInfo.append(Tab + "ADDITVALS" + c0Row.getAdditVals() + "NEGVALS"
				+ c0Row.getNegVals() + "\n");
		rfcInfo.append(Tab + "CHARACT>>" + c0Row.getCharact() + ", DATATYPE>>"
				+ c0Row.getDatatype() + ", CHARNUMBER>>"
				+ c0Row.getCharnumberString() + ", STATUS>>"
				+ c0Row.getStatus() + ", VALASSIGNM>>" + c0Row.getValassignm()
				+ "\n");

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
		c4Row.setValue(typeModel.getModel());

		c4Table.appendRow(c4Row);
		rfc.setICharVals(c4Table);
		rfcInfo.append("CHAR_VALS  \n");
		rfcInfo.append(Tab + "CHARACT>>" + c4Row.getCharact() + ", VALUE>>"
				+ c4Row.getValue() + "\n");

		// CHV_DESCR - C5
		Chv_descrTable c5Table = new Chv_descrTable();
		Chv_descrTableRow c5Row = c5Table.createEmptyRow();

		c5Row.setCharact(charac);
		c5Row.setValue(typeModel.getModel());
		c5Row.setLanguage("E");
		c5Row.setValdescr(padWithBlanks(typeModel.getDescription(), 26) + " "
				+ typeModel.getModel());

		c5Table.appendRow(c5Row);
		rfc.setIChvDescr(c5Table);

		rfcInfo.append("CHVDESCR \n");
		rfcInfo.append(Tab + "CHARACT>>" + c5Row.getCharact() + ", VALUE>>"
				+ c5Row.getValue() + ", LANGUAGE>>" + c5Row.getLanguage()
				+ ", VALDECSR>>" + c5Row.getValdescr() + "\n");

		// E1CAWNM STRUCTURE
		E1cawnmTable e1Table = new E1cawnmTable();
		E1cawnmTableRow e1Row = e1Table.createEmptyRow();

		e1Row.setAtnam(charac);
		e1Row.setAtwrt(typeModel.getModel());

		e1Table.appendRow(e1Row);
		rfc.setIE1cawnm(e1Table);
		rfcInfo.append("E1CAWNM \n");
		rfcInfo.append(Tab + "ATNAM>>" + e1Row.getAtnam() + ", ATWRT>>"
				+ e1Row.getAtwrt() + "\n");

		// E1CUTXM STRUCTURE
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

		// PIMS_IDENTITY
		rfc.setPimsIdentity(pimsIdentity);
		rfcInfo.append("PIMSIdentity \n");
		rfcInfo.append(Tab + "PIMSIdentity>>" + pimsIdentity + "\n");

		// RFANUMBER
		rfc.setRfaNum(chwA.getAnnDocNo());
		rfcInfo.append("RFANUM \n");
		rfcInfo.append(Tab + "RFANumber>>" + chwA.getAnnDocNo() + "\n");

	}

	// This is an new add function
	private String padWithBlanks(String inString, int totalSize) {
		// TODO Auto-generated method stub
		String outString;

		outString = inString;

		if (inString.length() < totalSize) {
			while (outString.length() < totalSize) {
				outString = outString + " ";
			}
		} else {
			outString = outString.substring(0, totalSize);

		}

		return outString;
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

	public Z_DM_SAP_CHAR_MAINTAIN getRfc() {
		return rfc;
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
		return "Maintain Model value for type MOD characteristic";
	}

	@Override
	public String getRfcName() {
		return ClassUtil.getSimpleClassName(getRfc());
	}

	public void evaluate() throws Exception {
		execute();
	}

}
