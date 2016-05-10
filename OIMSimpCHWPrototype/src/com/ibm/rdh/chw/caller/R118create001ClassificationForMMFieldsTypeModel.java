package com.ibm.rdh.chw.caller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.PropertyKeys;
import com.ibm.pprds.epimshw.util.ConfigManager;
import com.ibm.rdh.chw.entity.CHWAnnouncement;
import com.ibm.rdh.chw.entity.TypeModel;
import com.ibm.rdh.rfc.Api_auspTable;
import com.ibm.rdh.rfc.Api_auspTableRow;
import com.ibm.rdh.rfc.KlahTable;
import com.ibm.rdh.rfc.KlahTableRow;
import com.ibm.rdh.rfc.KsskTable;
import com.ibm.rdh.rfc.KsskTableRow;
import com.ibm.rdh.rfc.MaraTable;
import com.ibm.rdh.rfc.MaraTableRow;
import com.ibm.rdh.rfc.Object_keyTable;
import com.ibm.rdh.rfc.Object_keyTableRow;
import com.ibm.rdh.rfc.RcucoTable;
import com.ibm.rdh.rfc.RcucoTableRow;
import com.ibm.rdh.rfc.Zdm_geo_to_classTable;
import com.ibm.rdh.rfc.Zdm_geo_to_classTableRow;

public class R118create001ClassificationForMMFieldsTypeModel extends Rfc {

	private com.ibm.rdh.rfc.Z_DM_SAP_CLASSIFICATION_MAINT rfc;

	public R118create001ClassificationForMMFieldsTypeModel(TypeModel typeModel,
			CHWAnnouncement chwA, String flfilcd, String warrantyPeriod,
			boolean remarkable, String pimsIdentity) throws Exception {
		reInitialize();
		Date curDate = new Date();
		String sDateFormat = ConfigManager.getConfigManager().getString(
				PropertyKeys.KEY_DATE_FORMAT, true);
		SimpleDateFormat sdf = new SimpleDateFormat(sDateFormat);
		rfcName = "Z_DM_SAP_CLASSIFICATION_MAINT";

		rfc = new com.ibm.rdh.rfc.Z_DM_SAP_CLASSIFICATION_MAINT();

		// Set up the RFC fields
		// OBJECT_KEY - R0
		Object_keyTable r0Table = new Object_keyTable();
		Object_keyTableRow r0Row = r0Table.createEmptyRow();

		r0Row.setKeyFeld("MATNR");
		r0Row.setKparaValu(typeModel.getType() + typeModel.getModel());

		r0Table.appendRow(r0Row);
		rfc.setIObjectKey(r0Table);

		rfcInfo.append("OBJECTKEY \n");
		rfcInfo.append(Tab + "KEYFELD>>" + r0Row.getKeyFeld() + ", KPARAVALU>>"
				+ r0Row.getKparaValu() + "\n");

		// KLAH - R2
		KlahTable r2Table = new KlahTable();
		KlahTableRow r2Row = r2Table.createEmptyRow();

		r2Row.setClass("MM_FIELDS");

		r2Table.appendRow(r2Row);
		rfc.setIKlah(r2Table);

		rfcInfo.append("KLAH \n");
		rfcInfo.append(Tab + "CLASS>>" + r2Row.get_Class() + "\n");

		// KSSK - R3
		KsskTable r3Table = new KsskTable();
		KsskTableRow r3Row = r3Table.createEmptyRow();

		r3Row.setKlart("001");

		r3Table.appendRow(r3Row);
		rfc.setIKssk(r3Table);

		rfcInfo.append("KSSK \n");
		rfcInfo.append(Tab + "KLART>>" + r3Row.getKlart() + "\n");

		// RCUCO - R4
		RcucoTable r4Table = new RcucoTable();
		RcucoTableRow r4Row = r4Table.createEmptyRow();

		r4Row.setObtab("MARA");

		r4Table.appendRow(r4Row);
		rfc.setIRcuco(r4Table);

		rfcInfo.append("RCUCO \n");
		rfcInfo.append(Tab + "OBTAB>>" + r4Row.getObtab() + "\n");

		// MARA - R5
		MaraTable r5Table = new MaraTable();
		MaraTableRow r5Row = r5Table.createEmptyRow();

		// Passing date
		// r5Row.setErsdaString(sdf.format(curDate));
		r5Row.setErsda(curDate);

		r5Table.appendRow(r5Row);
		rfc.setIMara(r5Table);

		rfcInfo.append("MARA  \n");
		rfcInfo.append(Tab + "ERSDA>>" + r5Row.getErsdaString() + "\n");

		// API_AUSP - R6
		Api_auspTable r6Table = new Api_auspTable();
		Vector r6Vector = new Vector(1, 1);

		// Need to Know more about the if and else part in the Design
		// Need to clarify this with Greg .

		Api_auspTableRow r6Row = r6Table.createEmptyRow();
		if (!remarkable) {
			r6Row.setCharact("REMARKETER_REPORTER");
			r6Row.setValue("Yes");
			r6Vector.addElement(r6Row);
		}
		r6Row = r6Table.createEmptyRow();
		r6Row.setCharact("MM_MODEL_PROPERTIES");

		if (typeModel.getCustomerSetup()) {
			r6Row.setValue("CSU");
		} else {
			r6Row.setValue("");
		}

		r6Vector.addElement(r6Row);

		r6Row = r6Table.createEmptyRow();

		r6Row.setCharact("MM_MODEL_PROPERTIES");
		if (typeModel.getLicenseCode()) {
			r6Row.setValue("LIC");
		} else {
			r6Row.setValue("");
		}

		r6Vector.addElement(r6Row);
		
		r6Row = r6Table.createEmptyRow();

		r6Row.setCharact("MM_MODEL_PROPERTIES");
		if (typeModel.getSystemType() == null) {
			r6Row.setValue("");
		} else {
			r6Row.setValue(typeModel.getSystemType());
		}

		r6Vector.addElement(r6Row);

		r6Row = r6Table.createEmptyRow();
		// ("REVENUEPROFILE"+typeModel.getTmRev().getRevProfile().getRevenueProfile());
		r6Row.setCharact("MM_BTPRODUCTS");
		//waiting new entity RFAPROFILE 
//		if (typeModel.getRevProfile() != null) {
//			r6Row.setValue("PROCESS4");
//		} else {
			r6Row.setValue("");
//		}

		r6Vector.addElement(r6Row);

		// GsaLegal changes start
		if ((flfilcd != null && flfilcd.equalsIgnoreCase("XCC"))
				&& (warrantyPeriod != null)) {
			r6Row = r6Table.createEmptyRow();
			r6Row.setCharact("MM_WARRANTY_PERIOD_US");
			r6Row.setValue(warrantyPeriod);
			r6Vector.addElement(r6Row);
		}
		// GsaLegal changes end
		// CR for TIR-6LSREE-SIU indicator needs to be set on all materials - VJ
		r6Row = r6Table.createEmptyRow();

		r6Row.setCharact("MM_SIU");
		if (typeModel.getCPU()) {
			r6Row.setValue("1");
		} else {
			r6Row.setValue("0");
		}

		r6Vector.addElement(r6Row);
		// CR for TIR-6LSREE-SIU indicator needs to be set on all materials - VJ

		// CQ00002610-RQ / CQ00002496 changes start
		r6Row = r6Table.createEmptyRow();

		r6Row.setCharact("MM_TYPE_MOD");
		r6Row.setValue(typeModel.getType() + typeModel.getModel());
		r6Vector.addElement(r6Row);
		// CQ00002610-RQ / CQ00002496 changes end

		// Add the data to rfcInfo
		Enumeration em6 = r6Vector.elements();

		int rowCnt = 0;
		while (em6.hasMoreElements()) {
			r6Row = (Api_auspTableRow) em6.nextElement();
			rowCnt++;
			rfcInfo.append("API_AUSP Row " + rowCnt + "\n");
			rfcInfo.append(Tab + "CHARACT>>" + r6Row.getCharact()
					+ ", VALUE>> " + r6Row.getValue() + "\n");

			// Add the row to the table
			r6Table.appendRow(r6Row);
		}
		rfc.setIApiAusp(r6Table);

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
		rfc.setCharvalRefresh("1");
		rfcInfo.append("RFANUM \n");
		rfcInfo.append(Tab + "RFANumber>>" + chwA.getAnnDocNo() + "\n");
		rfcInfo.append("CharvalRefresh\n");
		rfcInfo.append(Tab + "CharvalRefresh>>" + "1" + "\n");

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
	public String getRfcName() {
		return ClassUtil.getSimpleClassName(getRfc());
	}

	public com.ibm.rdh.rfc.Z_DM_SAP_CLASSIFICATION_MAINT getRfc() {
		return rfc;
	}

	@Override
	protected String getMaterialName() {
		return "Create 001 Classification for MM_FIELDS type/model";
	}
	public void evaluate() throws Exception { 
		execute() ; 
	}
}
