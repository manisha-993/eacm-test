package com.ibm.rdh.chw.caller;

import java.text.SimpleDateFormat;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.rdh.chw.entity.CHWAnnouncement;
import com.ibm.rdh.chw.entity.CHWGeoAnn;
import com.ibm.rdh.chw.entity.TypeModel;
import com.ibm.rdh.rfc.Bmm00Table;
import com.ibm.rdh.rfc.Bmm00TableRow;
import com.ibm.rdh.rfc.Bmmh1Table;
import com.ibm.rdh.rfc.Bmmh1TableRow;
import com.ibm.rdh.rfc.Zdm_geo_to_classTable;
import com.ibm.rdh.rfc.Zdm_geo_to_classTableRow;

public class R057CreateReturnPlantView extends Rfc {

	private com.ibm.rdh.rfc.Z_DM_SAP_MATM_CREATE rfc;

	public R057CreateReturnPlantView(CHWAnnouncement chwA, TypeModel typeModel,
			CHWGeoAnn chwAg, String returnPlant, String pimsIdentity)
			throws Exception {
		reInitialize();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
		rfcName = "Z_DM_SAP_MATM_CREATE";
		rfc = new com.ibm.rdh.rfc.Z_DM_SAP_MATM_CREATE();
		// Set Up the RFC Fields
		// Bmm00 - B0 Structure
		Bmm00Table b0Table = new Bmm00Table();
		Bmm00TableRow b0Row = b0Table.createEmptyRow();
		b0Row.setTcode("MM01");
		// b0Row.setMatnr(typeModel.getRevProfile().getAuoMaterials());
		b0Row.setMatnr(typeModel.getType());
		b0Row.setMbrsh("M");
		b0Row.setMtart("ZIMG");
		b0Row.setXeiv4("X");
		b0Row.setWerks(returnPlant);
		b0Row.setXeib1("X");
		b0Table.appendRow(b0Row);
		rfc.setIBmm00(b0Table);

		rfcInfo.append("BMM00 \n");
		rfcInfo.append(Tab + "TCODE>>" + b0Row.getTcode() + ", MATNR>>"
				+ b0Row.getMatnr() + ", MBRSH>>" + b0Row.getMbrsh()
				+ ", MTART>>" + b0Row.getMtart() + ", XEIV4>>"
				+ b0Row.getXeiv4() + ", WERKS" + b0Row.getWerks() + ", XEIB1"
				+ b0Row.getXeib1() + "\n");

		// Bmmh1 - B1
		Bmmh1Table b1Table = new Bmmh1Table();
		Bmmh1TableRow b1Row = b1Table.createEmptyRow();
		b1Row.setTragr("STD");
		b1Row.setLadgr("RETN");
		b1Row.setSernp("ZXXC");
		b1Row.setSbdkz("1");
		b1Row.setMtvfp("NC");
		b1Row.setVprsv("S");
		b1Row.setPeinh("1");
		b1Row.setStprs("0");
		b1Row.setBklas("7920");

		// Add 7 set value not set in the previous epimshw code
		b1Row.setZeinr(chwA.getAnnDocNo());
		b1Row.setMatkl("000");
		b1Row.setSpart(typeModel.getDiv());
		b1Row.setZeiar(chwA.getAnnouncementType());
		b1Row.setGewei("KG");
		b1Row.setAeszn(sdf.format(chwAg.getAnnouncementDate()));
		b1Row.setMeins("EA");
		// end

		b1Table.appendRow(b1Row);
		rfc.setIBmmh1(b1Table);
		rfcInfo.append("BMMH1 \n");
		rfcInfo.append(Tab + "ZEINR>>" + b1Row.getZeinr() + ", MATKL>>"
				+ b1Row.getMatkl() + ", SPART>>" + b1Row.getSpart()
				+ ", ZEIAR>>" + b1Row.getZeiar() + ", GEWEI>>"
				+ b1Row.getGewei() + ", AESZN>>" + b1Row.getAeszn() + ", MEINS"
				+ b1Row.getMeins() + "\n");

		rfcInfo.append(Tab + "TRAGR>>" + b1Row.getTragr() + ", LADGR>>"
				+ b1Row.getLadgr() + ", SERNP>>" + b1Row.getSernp()
				+ ", SBDKZ>>" + b1Row.getSbdkz() + ", MTVFP>>"
				+ b1Row.getMtvfp() + ", VPRSV>>" + b1Row.getVprsv()
				+ ", PEINH>>" + b1Row.getPeinh() + ", STPRS>>"
				+ b1Row.getStprs() + ", BKLAS>>" + b1Row.getBklas() + "\n");

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
		// TODO Auto-generated method stub
		return "Create Return Plant View";
	}

	public void evaluate() throws Exception {
		execute();
	}

}
