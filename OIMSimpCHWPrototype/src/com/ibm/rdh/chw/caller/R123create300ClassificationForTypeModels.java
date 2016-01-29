package com.ibm.rdh.chw.caller;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.PropertyKeys;
import com.ibm.pprds.epimshw.util.ConfigManager;
import com.ibm.rdh.chw.entity.CHWAnnouncement;
import com.ibm.rdh.chw.entity.TypeModelUPGGeo;
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

public class R123create300ClassificationForTypeModels extends Rfc {
	private com.ibm.rdh.rfc.Z_DM_SAP_CLASSIFICATION_MAINT rfc;

	public R123create300ClassificationForTypeModels(String type,
			TypeModelUPGGeo tmUPGObj, String newFlag, CHWAnnouncement chwA,
			String FromToType, String pimsIdentity) throws Exception {
		reInitialize();
		Date curDate = new Date();
		String sDateFormat = ConfigManager.getConfigManager().getString(
				PropertyKeys.KEY_DATE_FORMAT, true);
		SimpleDateFormat sdf = new SimpleDateFormat(sDateFormat);

		rfc = new com.ibm.rdh.rfc.Z_DM_SAP_CLASSIFICATION_MAINT();
		// Set up the RFC fields
		// OBJECT_KEY - R0
		Object_keyTable r0Table = new Object_keyTable();
		Object_keyTableRow r0Row = r0Table.createEmptyRow();

		r0Row.setKeyFeld("MATNR");
		if (("NEW").equals(newFlag)) {
			r0Row.setKparaValu(type + "NEW");
		} else if (newFlag.equals("UPG")) {
			r0Row.setKparaValu(type + "UPG");
		} else if (("MTC").equals(newFlag)) {
			if (("MTCTOTYPE").equals(FromToType)) {
				r0Row.setKparaValu(tmUPGObj.getType() + "MTC");
			}
			if (("MTCFROMTYPE").equals(FromToType)) {
				r0Row.setKparaValu(tmUPGObj.getFromType() + "MTC");
			}
		}
		r0Table.appendRow(r0Row);
		rfc.setIObjectKey(r0Table);

		rfcInfo.append("OBJECTKEY \n");
		rfcInfo.append(Tab + "KEYFELD>>" + r0Row.getKeyFeld() + ", KPARAVALU>>"
				+ r0Row.getKparaValu() + "\n");

		// KLAH - R2
		KlahTable r2Table = new KlahTable();
		KlahTableRow r2Row = r2Table.createEmptyRow();

		if (("MTC").equals(newFlag)) {
			if (("MTCTOTYPE").equals(FromToType)) {
				r2Row.setClass("MK_" + tmUPGObj.getType() + "_MODELS");
			}
			if (("MTCFROMTYPE".equals(FromToType))) {
				r2Row.setClass("MK_" + tmUPGObj.getFromType() + "_MODELS");
			}
		} else {
			r2Row.setClass("MK_" + type + "_MODELS");
		}

		r2Table.appendRow(r2Row);
		rfc.setIKlah(r2Table);

		rfcInfo.append("KLAH \n");
		rfcInfo.append(Tab + "CLASS>>" + r2Row.get_Class() + "\n");

		// KSSK - R3
		KsskTable r3Table = new KsskTable();
		KsskTableRow r3Row = r3Table.createEmptyRow();

		r3Row.setKlart("300");

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
		r5Row.setErsda(curDate);

		r5Table.appendRow(r5Row);
		rfc.setIMara(r5Table);

		rfcInfo.append("MARA  \n");
		rfcInfo.append(Tab + "ERSDA>>" + sdf.format(r5Row.getErsda()) + "\n");

		// API_AUSP - R6
		Api_auspTable r6Table = new Api_auspTable();
		Api_auspTableRow r6Row = r6Table.createEmptyRow();
		//not set
		r6Row.setValue("CH");
		if (("MTC").equals(newFlag)) {
			if (("MTCTOTYPE").equals(FromToType)) {
				r6Row.setCharact("MK_" + tmUPGObj.getType() + "_MOD");
			}
			if (("MTCFROMTYPE").equals(FromToType)) {
				r6Row.setCharact("MK_" + tmUPGObj.getFromType() + "_MOD");
			}
		} else {
			r6Row.setCharact("MK_" + type + "_MOD");
		}
		r6Table.appendRow(r6Row);
		rfc.setIApiAusp(r6Table);

		rfcInfo.append("API_AUSP \n");
		rfcInfo.append(Tab + "CHARACT>> " + r6Row.getCharact() +
		// ", VALUE>> "+r6Row.getValue()+
				"\n");

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
		return "Create 300 Classification for type MODELS";
	}

	@Override
	public String getRfcName() {
		return ClassUtil.getSimpleClassName(getRfc());
	}

	private com.ibm.rdh.rfc.Z_DM_SAP_CLASSIFICATION_MAINT getRfc() {
		return rfc;
	}

	public void evaluate() throws Exception {
		execute();
	}
}
