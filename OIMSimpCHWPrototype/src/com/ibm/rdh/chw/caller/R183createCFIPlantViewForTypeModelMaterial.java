package com.ibm.rdh.chw.caller;

import java.text.SimpleDateFormat;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.PropertyKeys;
import com.ibm.pprds.epimshw.util.ConfigManager;
import com.ibm.pprds.epimshw.util.ProfitCenterPlantSelector;
import com.ibm.rdh.chw.entity.CHWGeoAnn;
import com.ibm.rdh.rfc.Bmm00Table;
import com.ibm.rdh.rfc.Bmm00TableRow;
import com.ibm.rdh.rfc.Bmmh1Table;
import com.ibm.rdh.rfc.Bmmh1TableRow;
import com.ibm.rdh.rfc.Zdm_geo_to_classTable;
import com.ibm.rdh.rfc.Zdm_geo_to_classTableRow;

public class R183createCFIPlantViewForTypeModelMaterial extends Rfc {

	private com.ibm.rdh.rfc.Z_DM_SAP_MATM_CREATE rfc;

	public R183createCFIPlantViewForTypeModelMaterial(String annDocNo,
			String typemod, String sapPlant, String pimsIdentity,
			String profitCenter, CHWGeoAnn chwAg, String Div) throws Exception {
		reInitialize();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");

		rfc = new com.ibm.rdh.rfc.Z_DM_SAP_MATM_CREATE();
		// Set Up the RFC Fields
		// Bmm00 - B0 Structure

		Bmm00Table b0Table = new Bmm00Table();
		Bmm00TableRow b0Row = b0Table.createEmptyRow();

		b0Row.setTcode("MM01");
		b0Row.setMatnr(typemod);
		b0Row.setXeiv4("X");
		b0Row.setWerks(sapPlant);

		// new add data start
		b0Row.setMbrsh("M");
		b0Row.setMtart("ZMAT");
		// new add data end

		b0Table.appendRow(b0Row);
		rfc.setIBmm00(b0Table);

		rfcInfo.append("BMM00 \n");
		rfcInfo.append(Tab + "TCODE>>" + b0Row.getTcode() + ", MATNR>>"
				+ b0Row.getMatnr() + ", XEIV4>>" + b0Row.getXeiv4()
				+ ", WERKS>>" + b0Row.getWerks() + "\n");
		rfcInfo.append(Tab + "MBRSH>>" + b0Row.getMbrsh() + ", MTART>>"
				+ b0Row.getMtart() + "\n");

		Bmmh1Table b1Table = new Bmmh1Table();
		Bmmh1TableRow b1Row = b1Table.createEmptyRow();

		b1Row.setLadgr("B001");
		b1Row.setMtvfp("ZE");

		// new add data start
		b1Row.setZeinr(annDocNo);
		b1Row.setMatkl("000");
		b1Row.setMeins("EA");
		b1Row.setZeiar("New");
		b1Row.setAeszn(sdf.format(chwAg.getAnnouncementDate()));
		b1Row.setGewei("KG");
		b1Row.setSpart(Div);
		// new add data end

		// SAP Ledger
		if ("Y".equals(ConfigManager.getConfigManager().getString(
				PropertyKeys.KEY_SAP_LEDGER))) {
			boolean existsPro = ProfitCenterPlantSelector
					.checkProfitCenterPlants(sapPlant);
			if (existsPro) {
				b1Row.setPrctr(profitCenter);
			}
		}

		b1Table.appendRow(b1Row);
		rfc.setIBmmh1(b1Table);

		rfcInfo.append("BMMH1 \n");
		rfcInfo.append(Tab + "LADGR>>" + b1Row.getLadgr() + ", MTVFP>>"
				+ b1Row.getMtvfp() + "\n");
		rfcInfo.append(Tab + "ZEINR>>" + b1Row.getZeinr() + ", MATKL>>"
				+ b1Row.getMatkl() + ", MEINS>>" + b1Row.getMeins()
				+ ", ZEIAR>>" + b1Row.getZeiar() + ", AESZN>>"
				+ b1Row.getAeszn() + ", GEWEI>>" + b1Row.getGewei()
				+ ", PRCTR>>" + b1Row.getPrctr() + ", SPART>>"
				+ b1Row.getSpart() + "\n");

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
		rfc.setRfaNum(annDocNo);
		rfcInfo.append("RFANUM \n");
		rfcInfo.append(Tab + "RFANumber>>" + annDocNo + "\n");

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

	public com.ibm.rdh.rfc.Z_DM_SAP_MATM_CREATE getRfc() {
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
		return "Create CFI Plant View for Type/Model material";
	}

	@Override
	public String getRfcName() {
		return ClassUtil.getSimpleClassName(getRfc());
	}

	public void evaluate() throws Exception {
		execute();
	}

}
