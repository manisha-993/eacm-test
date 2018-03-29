package com.ibm.rdh.chw.caller;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.PropertyKeys;
import com.ibm.pprds.epimshw.util.ConfigManager;
import com.ibm.rdh.chw.entity.TypeModel;
import com.ibm.rdh.chw.entity.TypeModelUPGGeo;
import com.ibm.rdh.rfc.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class R205ClassificationForBTProductsTypeMaterials extends Rfc {

	private com.ibm.rdh.rfc.Z_DM_SAP_CLASSIFICATION_MAINT rfc;

	public R205ClassificationForBTProductsTypeMaterials(TypeModel typeModel,
			TypeModelUPGGeo tmupg, String newFlag, String fromtotype,
			String typeProfRefresh, String type, String profile,
			String pimsIdentity,String rfaNumber) throws Exception {

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
		if (typeProfRefresh != null) {
			if ("TYPENEW".equals(typeProfRefresh)) {
				r0Row.setKparaValu(type + "NEW");
			} else if ("TYPEUPG".equals(typeProfRefresh)) {
				r0Row.setKparaValu(type + "UPG");
			} else if ("TYPEMTC".equals(typeProfRefresh)) {
				r0Row.setKparaValu(type + "MTC");
			}
		} else {
			if ("NEW".equals(newFlag)) {
				r0Row.setKparaValu(typeModel.getType() + "NEW");
			} else if ("UPG".equals(newFlag)) {
				r0Row.setKparaValu(typeModel.getType() + "UPG");
			} else if ("MTC".equals(newFlag) && "FROMTYPE".equals(fromtotype)) {
				r0Row.setKparaValu(tmupg.getFromType() + "MTC");
			} else if ("MTC".equals(newFlag) && "TOTYPE".equals(fromtotype)) {
				r0Row.setKparaValu(tmupg.getType() + "MTC");
			}
			//add type+model at 20180328
			else if ("MOD".equals(newFlag)) {
				r0Row.setKparaValu(typeModel.getType() + typeModel.getModel());
			}
			//add end
		}

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
		r5Row.setErsda(curDate);

		r5Table.appendRow(r5Row);
		rfc.setIMara(r5Table);

		rfcInfo.append("MARA  \n");
		rfcInfo.append(Tab + "ERSDA>>" + r5Row.getErsdaString() + "\n");

		// API_AUSP - R6
		Api_auspTable r6Table = new Api_auspTable();
		Api_auspTableRow r6Row = r6Table.createEmptyRow();

		r6Row.setCharact("MM_BTPRODUCTS");
		if (typeProfRefresh != null) {
			if ("TYPENEW".equals(typeProfRefresh)) {
				if (profile != null) {
					r6Row.setValue("PROCESS4");

				} else {
					r6Row.setValue("");
				}
			}

			else if ("TYPEUPG".equals(typeProfRefresh)) {
				if (profile != null) {
					r6Row.setValue("PROCESS4");
				} else {
					r6Row.setValue("");
				}
			}

			else if ("TYPEMTC".equals(typeProfRefresh)) {
				if (profile != null) {
					r6Row.setValue("PROCESS4");
				} else {
					r6Row.setValue("");
				}
			}

		} else {

			if (newFlag.equals("NEW")) {
				if (typeModel.isHasRevProfile()) {

					r6Row.setValue("PROCESS4");
				} else {
					r6Row.setValue("");
				}
			} else if (newFlag.equals("UPG")) {
				if (typeModel.isHasRevProfile()) {

					r6Row.setValue("PROCESS4");
				} else {
					r6Row.setValue("");
				}
			}

			else if (newFlag.equals("MTC") && fromtotype.equals("FROMTYPE")) {
				if (tmupg.isFromTMRevProfileExist()) {

					r6Row.setValue("PROCESS4");
				} else {
					r6Row.setValue("");
				}
			}

			else if (newFlag.equals("MTC") && fromtotype.equals("TOTYPE")) {
				if (tmupg.isToTMRevProfileExist()) {

					r6Row.setValue("PROCESS4");
				} else {
					r6Row.setValue("");
				}
			}
			//add type+model at 20180328
			else if (newFlag.equals("MOD")) {
				if (typeModel.isHasRevProfile()) {

					r6Row.setValue("PROCESS4");
				} else {
					r6Row.setValue("");
				}
			}
			//add end
		}
		r6Table.appendRow(r6Row);
		rfc.setIApiAusp(r6Table);

		rfcInfo.append("CHARACT  \n");
		rfcInfo.append(Tab + "CHARACT>>" + r6Row.getCharact() + "\n");
		rfcInfo.append("VALUE  \n");
		rfcInfo.append(Tab + "VALUE>>" + r6Row.getValue() + "\n");

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
//		if (typeProfRefresh != null) {
//			if (typeProfRefresh.equals("TYPENEW") && (profile != null)) {
//				rfc.setRfaNum(profile + "_REV");
//			} else if (typeProfRefresh.equals("TYPEUPG") && (profile != null)) {
//				rfc.setRfaNum(profile + "_REV");
//			} else if (typeProfRefresh.equals("TYPEMTC") && (profile != null)) {
//				rfc.setRfaNum(profile + "_REV");
//			}
//		} else {
//			if (newFlag.equals("NEW")) {
//				rfc.setRfaNum(typeModel.getRevProfile().getRevenueProfile()
//						+ "_REV");
//			} else if (newFlag.equals("UPG")) {
//				rfc.setRfaNum(typeModel.getRevProfile().getRevenueProfile()
//						+ "_REV");
//			} else if (newFlag.equals("MTC") && fromtotype.equals("FROMTYPE")) {
//				System.out.println("From Type RFA Number ***** "
//						+ tmupg.getFromTMRevProfile().getRevenueProfile());
//
//				rfc.setRfaNum(tmupg.getFromTMRevProfile().getRevenueProfile()
//						+ "_REV");
//			} else if (newFlag.equals("MTC") && fromtotype.equals("TOTYPE")) {
//				System.out.println("To Type RFA Number ***** "
//						+ tmupg.getToTMRevProfile().getRevenueProfile());
//				rfc.setRfaNum(tmupg.getToTMRevProfile().getRevenueProfile()
//						+ "_REV");
//			}
//		}
		rfc.setRfaNum(rfaNumber);
		rfcInfo.append("RFANUM \n");
		rfcInfo.append(Tab + "RFANumber>>" + rfc.getRfaNum() + "\n");

		// Story 1796482: Question about Z_DM_SAP_CLASSIFICATION_MAINT - do some update for RFCABR of MODEL.
		// Z_DM_SAP_CLASSIFICATION_MAINT: If the <charval_refresh> input parameter/variable == "1" (TRUE): it will delete all characters first then create as input.
		// We will call r175 first for MM_FILEDS, the call r205 but we don't want to delete characters where created by r175, so set it to 0
		rfc.setCharvalRefresh("0");
		rfcInfo.append("CHARVAL_REFRESH \n");
		rfcInfo.append(Tab + "CHARVAL_REFRESH>>" + rfc.getCharvalRefresh()
				+ "\n");
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

	public com.ibm.rdh.rfc.Z_DM_SAP_CLASSIFICATION_MAINT getRfc() {
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
		return "Maintain BTProducts Classification for Type Materials";
	}

	@Override
	public String getRfcName() {
		return ClassUtil.getSimpleClassName(getRfc());
	}

	public void evaluate() throws Exception {

		execute();
	}
}
