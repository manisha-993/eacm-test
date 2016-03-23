package com.ibm.rdh.chw.caller;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.PropertyKeys;
import com.ibm.pprds.epimshw.util.ConfigManager;
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

public class R182deleteModelSelectionDependency extends Rfc {
	private com.ibm.rdh.rfc.Z_DM_SAP_DEPD_MAINTAIN rfc;

	public R182deleteModelSelectionDependency(TypeModel typeModel,
			CHWAnnouncement chwA, String pimsIdentity) throws Exception {
		reInitialize();
		Date curDate = new Date();
		String sDateFormat = ConfigManager.getConfigManager().getString(
				PropertyKeys.KEY_DATE_FORMAT, true);
		SimpleDateFormat sdf = new SimpleDateFormat(sDateFormat);
		rfcName = "Z_DM_SAP_DEPD_MAINTAIN";
		rfc = new com.ibm.rdh.rfc.Z_DM_SAP_DEPD_MAINTAIN();
		
		// Set up the RFC fields
		// D0- Structure

		String type = typeModel.getType();
		String model = typeModel.getModel();

		DepdescrTable l1Table = new DepdescrTable();
		DepdescrTableRow l1Row = l1Table.createEmptyRow();

		l1Row.setLanguage("E");
		l1Row.setDescript("SC_MK_" + type + "_MODEL_" + model);

		l1Table.appendRow(l1Row);

		rfc.setIDepdescr(l1Table);

		rfcInfo.append(Tab + "LANGUAGE>>" + l1Row.getLanguage()
				+ ", DESCRIPT>>" + l1Row.getDescript() + "\n");

		// D2- Structure

		DepsourceTable l2Table = new DepsourceTable();
		DepsourceTableRow l2Row = l2Table.createEmptyRow();

		l2Row.setLine("");

		l2Table.appendRow(l2Row);
		rfc.setIDepsource(l2Table);

		rfcInfo.append(Tab + "LINE>>" + l2Row.getLine() + "\n");

		// D3- Structure

		Dep_identStructure struct = new Dep_identStructure();
		struct.setDepExtern("SC_MK_" + type + "_MODEL_" + model);

		rfc.setIDepIdent(struct);
		rfcInfo.append(Tab + "DEP_EXTERN>>" + struct.getDepExtern() + "\n");

		// D4- Structure

		DepdatStructure depdat = new DepdatStructure();
		depdat.setDepType("5");
		depdat.setStatus("3");

		rfc.setIDepdat(depdat);
		rfcInfo.append(Tab + "DEP_TYPE>>" + depdat.getDepType() + ", STATUS>>"
				+ depdat.getStatus() + "\n");
		Zdm_geo_to_classTable zdmTable = new Zdm_geo_to_classTable();
		Zdm_geo_to_classTableRow zdmRow = zdmTable.createEmptyRow();

		zdmRow.setZGeo("US");

		zdmTable.appendRow(zdmRow);

		rfc.setGeoData(zdmTable);

		rfcInfo.append("ZDM_GEO_TO_CLASS \n");
		rfcInfo.append(Tab + "GEO>>" + zdmRow.getZGeo() + "\n");

		// rfc.setPimsIdentity("C");
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
		getRfc().execute(null);// has not been finished.
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
	public String getRfcName() {
		return ClassUtil.getSimpleClassName(getRfc());
	}

	public com.ibm.rdh.rfc.Z_DM_SAP_DEPD_MAINTAIN getRfc() {
		return rfc;
	}

	@Override
	protected String getMaterialName() {
		// TODO Auto-generated method stub
		return "Delete Model Selection Dependency";
	}

	public void evaluate() throws Exception {
		execute();
	}
}