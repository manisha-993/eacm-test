package com.ibm.rdh.chw.caller;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.rdh.chw.entity.CHWAnnouncement;
import com.ibm.rdh.chw.entity.TypeModel;
import com.ibm.rdh.rfc.Dep_identStructure;
import com.ibm.rdh.rfc.DepdatStructure;
import com.ibm.rdh.rfc.DepdescrTable;
import com.ibm.rdh.rfc.DepdescrTableRow;
import com.ibm.rdh.rfc.DepsourceTable;
import com.ibm.rdh.rfc.DepsourceTableRow;
import com.ibm.rdh.rfc.Zdm_geo_to_classTable;
import com.ibm.rdh.rfc.Zdm_geo_to_classTableRow;

public class R121createModelSelectionDependency extends Rfc {

	private com.ibm.rdh.rfc.Z_DM_SAP_DEPD_MAINTAIN rfc;

	public R121createModelSelectionDependency(TypeModel typeModel,
			CHWAnnouncement chwA, String pimsIdentity) throws Exception {

		reInitialize();
		// Note that both the dependency name and description use this.
		String depend = "SC_MK_" + typeModel.getType() + "_MODEL_"
				+ typeModel.getModel();
		rfcName = "Z_DM_SAP_DEPD_MAINTAIN";

		rfc = new com.ibm.rdh.rfc.Z_DM_SAP_DEPD_MAINTAIN();

		// DEPDESCR - D0
		DepdescrTable d0Table = new DepdescrTable();
		DepdescrTableRow d0Row = d0Table.createEmptyRow();

		d0Row.setLanguage("E");
		d0Row.setDescript(depend);

		d0Table.appendRow(d0Row);
		rfc.setIDepdescr(d0Table);

		rfcInfo.append("DEPDESCR \n");
		rfcInfo.append(Tab + "LANGUAGE>>" + d0Row.getLanguage()
				+ ", DESCRIPT>>" + d0Row.getDescript() + "\n");

		// DEPSOURCE - D2
		DepsourceTable d2Table = new DepsourceTable();
		DepsourceTableRow d2Row = d2Table.createEmptyRow();

		d2Row.setLine("MK_" + typeModel.getType() + "_MOD='"
				+ typeModel.getModel() + "'");

		d2Table.appendRow(d2Row);
		rfc.setIDepsource(d2Table);

		rfcInfo.append("DEPSOURCE  \n");
		rfcInfo.append(Tab + "LINE>>" + d2Row.getLine() + "\n");

		// DEPIDENT - D3
		Dep_identStructure d3 = new Dep_identStructure();

		d3.setDepExtern(depend);

		rfc.setIDepIdent(d3);

		rfcInfo.append("DEPIDENT \n");
		rfcInfo.append(Tab + "DEPEXTERN>>" + d3.getDepExtern() + "\n");

		// DEPDAT - D4
		DepdatStructure d4 = new DepdatStructure();

		d4.setDepType("5");
		d4.setStatus("1");

		rfc.setIDepdat(d4);

		rfcInfo.append("DEPDAT \n");
		rfcInfo.append(Tab + "DEPTYPE>>" + d4.getDepType() + ", STATUS>>"
				+ d4.getStatus() + "\n");

		Zdm_geo_to_classTable zdmTable = new Zdm_geo_to_classTable();
		Zdm_geo_to_classTableRow zdmRow = zdmTable.createEmptyRow();

		zdmRow.setZGeo("US");

		zdmTable.appendRow(zdmRow);

		rfc.setGeoData(zdmTable);

		rfcInfo.append("ZDM_GEO_TO_CLASS \n");
		rfcInfo.append(Tab + "GEO>>" + zdmRow.getZGeo() + "\n");

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
		getRfc().execute(null);//wait finsih
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
		return "Create 300 Classification for type MODELS";
	}

	@Override
	public String getRfcName() {
		return ClassUtil.getSimpleClassName(getRfc());
	}

	public com.ibm.rdh.rfc.Z_DM_SAP_DEPD_MAINTAIN getRfc() {
		return rfc;
	}

	public void evaluate() throws Exception {
		execute();
	}
}
