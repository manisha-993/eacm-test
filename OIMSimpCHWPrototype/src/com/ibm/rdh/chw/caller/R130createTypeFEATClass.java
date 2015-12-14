package com.ibm.rdh.chw.caller;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.PropertyKeys;
import com.ibm.pprds.epimshw.util.ConfigManager;
import com.ibm.rdh.chw.entity.CHWAnnouncement;
import com.ibm.rdh.rfc.Cla_descrTable;
import com.ibm.rdh.rfc.Cla_descrTableRow;
import com.ibm.rdh.rfc.ClclassesTable;
import com.ibm.rdh.rfc.ClclassesTableRow;
import com.ibm.rdh.rfc.Z_DM_SAP_CLASS_MAINTAIN;
import com.ibm.rdh.rfc.Zdm_geo_to_classTable;
import com.ibm.rdh.rfc.Zdm_geo_to_classTableRow;

public class R130createTypeFEATClass extends Rfc {

	private String sDateFormat = null;

	private com.ibm.rdh.rfc.Z_DM_SAP_CLASS_MAINTAIN rfc;

	public R130createTypeFEATClass(String type, String featRanges,
			CHWAnnouncement chwA, String pimsIdentity) throws Exception {

		reInitialize();
		Date curDate = new Date();

		sDateFormat = ConfigManager.getConfigManager().getString(
				PropertyKeys.KEY_DATE_FORMAT, true);
		SimpleDateFormat sdf = new SimpleDateFormat(this.sDateFormat);
		rfc = new com.ibm.rdh.rfc.Z_DM_SAP_CLASS_MAINTAIN();

		// Set up the RFC fields
		// Clclasses - L0
		ClclassesTable l0Table = new ClclassesTable();
		ClclassesTableRow l0Row = l0Table.createEmptyRow();

		String className = "MK_" + type + "_FEAT_" + featRanges;
		l0Row.setClass(className);
		l0Row.setClassType("300");
		l0Row.setStatus("1");
		l0Row.setValFrom(sdf.format(curDate));
		l0Row.setValTo("9999-12-31");
		l0Row.setCheckNo("X");

		l0Table.appendRow(l0Row);

		rfc.setIClclasses(l0Table);

		rfcInfo.append("CLCLASSES  \n");
		rfcInfo.append(Tab + "CLASS>>" + l0Row.get_Class() + ", CLASSTYPE>>"
				+ l0Row.getClassType() + ", STATUS>>" + l0Row.getStatus()
				+ ", VALFROM>>" + l0Row.getValFrom() + ", VALTO>>"
				+ l0Row.getValTo() + ", CHECKNO>>" + l0Row.getCheckNo() + "\n");

		// Cla_descr - L1
		Cla_descrTable l1Table = new Cla_descrTable();
		Cla_descrTableRow l1Row = l1Table.createEmptyRow();

		l1Row.setClass(className);
		l1Row.setClassType("300");
		l1Row.setLanguage("E");
		l1Row.setCatchword("Features for Machine Type " + type);

		l1Table.appendRow(l1Row);

		rfc.setIClaDescr(l1Table);

		rfcInfo.append("CHAR_DESCR \n");
		rfcInfo.append(Tab + "CLASS>>" + l1Row.get_Class() + ", CLASSTYPE>>"
				+ l1Row.getClassType() + ", LANGUAGE>>" + l1Row.getLanguage()
				+ ", CATCHWORD>>" + l1Row.getCatchword() + "\n");

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

		rfc.setRfaNum(chwA.getAnnDocNo());
		rfcInfo.append("RFANUM \n");
		rfcInfo.append(Tab + ",RFANUM>>" + chwA.getAnnDocNo() + "\n");

	}

	@Override
	public void execute() throws Exception {
		// TODO Auto-generated method stub
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

	public Z_DM_SAP_CLASS_MAINTAIN getRfc() {
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
		// TODO Auto-generated method stub
		return "Create type FEAT range class";
	}

	@Override
	public String getRfcName() {
		return ClassUtil.getSimpleClassName(getRfc());
	}

	public void evaluate() throws Exception {

		execute();
	}

}
