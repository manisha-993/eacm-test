package com.ibm.rdh.chw.caller;

import java.text.SimpleDateFormat;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.util.ProfitCenterPlantSelector;
import com.ibm.rdh.chw.entity.CHWAnnouncement;
import com.ibm.rdh.chw.entity.CHWGeoAnn;
import com.ibm.rdh.rfc.Bmm00Table;
import com.ibm.rdh.rfc.Bmm00TableRow;
import com.ibm.rdh.rfc.Bmmh1Table;
import com.ibm.rdh.rfc.Bmmh1TableRow;
import com.ibm.rdh.rfc.Zdm_geo_to_classTable;
import com.ibm.rdh.rfc.Zdm_geo_to_classTableRow;

public class R262createPlantViewProfitCenterForMaterial extends Rfc {
	private com.ibm.rdh.rfc.Z_DM_SAP_MATM_CREATE rfc;

	public R262createPlantViewProfitCenterForMaterial(CHWAnnouncement chwA,
			String material, String sapPlant, String pimsIdentity,
			String profitCenter, CHWGeoAnn chwAg) throws Exception {

		reInitialize();
		rfcName = "Z_DM_SAP_MATM_CREATE";
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
		rfc = new com.ibm.rdh.rfc.Z_DM_SAP_MATM_CREATE();
		// Set Up the RFC Fields
		// Bmm00 - B0 Structure

		Bmm00Table b0Table = new Bmm00Table();
		Bmm00TableRow b0Row = b0Table.createEmptyRow();

		b0Row.setTcode("MM01");
		b0Row.setMatnr(material);

		// new add data start
		b0Row.setMbrsh("M");
		b0Row.setMtart("ZPRT");
		// new add data end

		b0Row.setWerks(sapPlant);
		b0Row.setXeiv4("X");

		b0Table.appendRow(b0Row);
		rfc.setIBmm00(b0Table);

		rfcInfo.append("BMM00 \n");
		rfcInfo.append(Tab + "TCODE>>" + b0Row.getTcode()
				+ ", MATNR>>" + b0Row.getMatnr() 
				+ ", XEIV4>>" + b0Row.getXeiv4()
				+ ", WERKS>>" + b0Row.getWerks() + "\n");
		rfcInfo.append(Tab + "MBRSH>>" + b0Row.getMbrsh() 
				+ ", MTART>>" + b0Row.getMtart() + "\n");

		// Bmmh1
		Bmmh1Table b1Table = new Bmmh1Table();
		Bmmh1TableRow b1Row = b1Table.createEmptyRow();

		// SAP Ledger
		if (profitCenter != null) {
			boolean existsPro = ProfitCenterPlantSelector
					.checkProfitCenterPlants(sapPlant);
			if (existsPro) {
				b1Row.setPrctr(profitCenter);
			}
		}

		// new add data start
		b1Row.setGewei("KG");
		b1Row.setSpart("B1");// There got no type.model
		b1Row.setMatkl("000");
		b1Row.setMeins("EA");
		b1Row.setZeinr(chwA.getAnnDocNo());
		b1Row.setZeiar(chwA.getAnnouncementType());
		b1Row.setAeszn(sdf.format(chwAg.getAnnouncementDate()));
		// new add data end

		b1Table.appendRow(b1Row);
		rfc.setIBmmh1(b1Table);

		rfcInfo.append("BMMH1 \n");
		rfcInfo.append(Tab + "PRCTR>>"+ b1Row.getPrctr() + "\n");
		rfcInfo.append(Tab + "GEWEI>>" + b1Row.getGewei() 
				+ ", SPART>>" + b1Row.getSpart() 
				+ ", MATKL>>" + b1Row.getMatkl()
				+ ", MEINS>>" + b1Row.getMeins() 
				+ ", ZEINR>>" + b1Row.getZeinr() 
				+ ", ZEIAR>>" + b1Row.getZeiar()
				+ ", AESZN>>" + b1Row.getAeszn() + "\n");

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
	public String getRfcName() {
		return ClassUtil.getSimpleClassName(getRfc());
	}

	public com.ibm.rdh.rfc.Z_DM_SAP_MATM_CREATE getRfc() {
		return rfc;
	}

	@Override
	protected String getMaterialName() {
		return "R262";
	}

	public void evaluate() throws Exception {
		execute();
	}
}