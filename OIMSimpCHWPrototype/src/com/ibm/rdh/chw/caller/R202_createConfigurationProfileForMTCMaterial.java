package com.ibm.rdh.chw.caller;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.rdh.chw.entity.CHWAnnouncement;
import com.ibm.rdh.rfc.Cpdep_datTable;
import com.ibm.rdh.rfc.Cpdep_datTableRow;
import com.ibm.rdh.rfc.Cpro_attrTable;
import com.ibm.rdh.rfc.Cpro_attrTableRow;
import com.ibm.rdh.rfc.Object_keyTable;
import com.ibm.rdh.rfc.Object_keyTableRow;
import com.ibm.rdh.rfc.RcucoStructure;
import com.ibm.rdh.rfc.Zdm_geo_to_classTable;
import com.ibm.rdh.rfc.Zdm_geo_to_classTableRow;

public class R202_createConfigurationProfileForMTCMaterial extends Rfc {

	private com.ibm.rdh.rfc.Z_DM_SAP_CONP_MAINTAIN rfc;

	public R202_createConfigurationProfileForMTCMaterial(String typeStr,
			CHWAnnouncement chwA, String pimsIdentity) throws Exception {
		reInitialize();
		String p2_dep_intern;
		rfcName = "Z_DM_SAP_CONP_MAINTAIN";

		rfc = new com.ibm.rdh.rfc.Z_DM_SAP_CONP_MAINTAIN();

		// Set up the RFC fields
		// OBJECT_KEY - P0 STRUCTURE
		Object_keyTable p0Table = new Object_keyTable();
		Object_keyTableRow p0Row = p0Table.createEmptyRow();

		p0Row.setKeyFeld("MATNR");

		p0Row.setKparaValu(typeStr + "MTC");

		p0Table.appendRow(p0Row);
		rfc.setJConObjectKey(p0Table);

		rfcInfo.append("OBJECT_KEY \n");
		rfcInfo.append(Tab + "KEYFELD>>" + p0Row.getKeyFeld() 
				+ ", KPARAVALU>>" + p0Row.getKparaValu() + "\n");

		// CPRO_ATTR - P1 STRUCTURE
		Cpro_attrTable p1Table = new Cpro_attrTable();
		Cpro_attrTableRow p1Row = p1Table.createEmptyRow();

		p1Row.setCProfile("INITIAL");
		p1Row.setClasstype("300");
		p1Row.setStatus("1");
		p1Row.setBomappl("SD01");
		p1Row.setBomexpl("5");
		p1Row.setTasklexpl("1");
		p1Row.setInitscreen("1");
		p1Row.setFlcasonly("");

		p1Table.appendRow(p1Row);
		rfc.setJConProAttributes(p1Table);

		rfcInfo.append("CPRO_ATTR \n");
		rfcInfo.append(Tab + "C_PROFILE>>" + p1Row.getCProfile()
				+ ", CLASSTYPE>>" + p1Row.getClasstype() 
				+ ", STATUS>>" + p1Row.getStatus() 
				+ ", BOMAPPL>>" + p1Row.getBomappl()
				+ ", BOMEXPL>>" + p1Row.getBomexpl() 
				+ ", TASKLEXPL>>" + p1Row.getTasklexpl() 
				+ ", INITSCREEN>>" + p1Row.getInitscreen() 
				+ ", FLCASONLY>>" + p1Row.getFlcasonly() + "\n");

		// CPDET_DAT - P2 STRUCTURE
		Cpdep_datTable p2Table = new Cpdep_datTable();
		Cpdep_datTableRow p2Row = p2Table.createEmptyRow();
		int count;
		for (count = 1; count <= 2; count++) {
			if (count == 1) {

				p2_dep_intern = "MK_HW_PRICING";

				p2Row.setCProfile("INITIAL");
				p2Row.setDepIntern(p2_dep_intern);
				p2Row.setStatus("1");
			} else if (count == 2) {
				p2Row.setCProfile("INITIAL");
				p2_dep_intern = "MK_HW_SUBLINE";
				p2Row.setDepIntern(p2_dep_intern);
				p2Row.setStatus("1");

			}
			p2Table.appendRow(p2Row);
			rfc.setJConProDependencyData(p2Table);

			rfcInfo.append("CPDEP_DAT \n");
			rfcInfo.append(Tab + "C_PROFILE>>" + p2Row.getCProfile()
					+ ", DEP_INTERN>>" + p2Row.getDepIntern() 
					+ ", STATUS>>" + p2Row.getStatus() + "\n");
		}

		// RCUCOSTRUCTURE - P7
		RcucoStructure p7 = new RcucoStructure();

		p7.setObtab("MARA");
		rfc.setJCuco(p7);

		rfcInfo.append("RCUCOSTRUCTURE \n");
		rfcInfo.append(Tab + "OBTAB>>" + p7.getObtab() + "\n");

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
		rfcInfo.append(Tab + "RFANumber>>" + rfc.getRfaNum() + "\n");

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
		return "Create Configuration Profile for MTC material";
	}

	@Override
	public String getRfcName() {
		return ClassUtil.getSimpleClassName(getRfc());
	}

	private com.ibm.rdh.rfc.Z_DM_SAP_CONP_MAINTAIN getRfc() {
		return rfc;
	}

	public void evaluate() throws Exception {
		execute();
	}
}
