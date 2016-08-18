package com.ibm.rdh.chw.caller;

import java.text.SimpleDateFormat;
import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.rdh.chw.entity.AUOMaterial;
import com.ibm.rdh.rfc.Bmm00Table;
import com.ibm.rdh.rfc.Bmm00TableRow;
import com.ibm.rdh.rfc.Bmmh1Table;
import com.ibm.rdh.rfc.Bmmh1TableRow;
import com.ibm.rdh.rfc.Zdm_geo_to_classTable;
import com.ibm.rdh.rfc.Zdm_geo_to_classTableRow;

public class R005CreatePlantViewForMaterial extends Rfc {

	private com.ibm.rdh.rfc.Z_DM_SAP_MATM_CREATE rfc;

	public R005CreatePlantViewForMaterial(AUOMaterial auoMaterial,
			String sapPlant, String pimsIdentity) throws Exception {
		reInitialize();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
		rfcName = "Z_DM_SAP_MATM_CREATE";
		rfc = new com.ibm.rdh.rfc.Z_DM_SAP_MATM_CREATE();
		// Set Up the RFC Fields
		// Bmm00 - B0 Structure
		Bmm00Table b0Table = new Bmm00Table();
		Bmm00TableRow b0Row = b0Table.createEmptyRow();

		b0Row.setTcode("MM01");
		b0Row.setMatnr(auoMaterial.getMaterial());
		b0Row.setMbrsh("M");
		b0Row.setMtart("ZIMG");
		b0Row.setXeiv4("X");
		if (sapPlant.equals("1999")) {
			b0Row.setLgort("CHW1");
		}
		b0Row.setWerks(sapPlant);
		b0Row.setXeid1("X");
		b0Row.setXeid2("X");

		b0Table.appendRow(b0Row);
		rfc.setIBmm00(b0Table);

		rfcInfo.append("BMM00 \n");
		rfcInfo.append(Tab + "TCODE>>" + b0Row.getTcode() + ", MATNR>>"
				+ b0Row.getMatnr() + ", MBRSH>>" + b0Row.getMbrsh()
				+ ", MTART>>" + b0Row.getMtart() + ", XEIV4>>"
				+ b0Row.getXeiv4() + ", LGORT>>" + b0Row.getLgort() + ", WERKS>>"
				+ b0Row.getWerks() + ", XEID1>>" + b0Row.getXeid1() + ", XEID2>>"
				+ b0Row.getXeid2() + "\n");

		// Bmmh1 - B1
		Bmmh1Table b1Table = new Bmmh1Table();
		Bmmh1TableRow b1Row = b1Table.createEmptyRow();

		b1Row.setMeins("EA");
		b1Row.setTragr("STD");
		b1Row.setGewei("KG");
		b1Row.setMtpos("Z002");
		b1Row.setLadgr("B001");
		b1Row.setXmcng("X");
		b1Row.setSernp("ZCHW");
		b1Row.setSbdkz("1");
		if (sapPlant.equals("1999")) {
			b1Row.setMtvfp("NC");

		} else {
			b1Row.setMtvfp("ZE");

		}
		b1Row.setDismm("PD");
		b1Row.setDispo("000");
		b1Row.setFhori("000");
		b1Row.setDisls("EX");
		b1Row.setMaabc("A");
		b1Row.setDisgr("Z025");
		b1Row.setPerkz("M");
		// Prctr need to be confirmed
		String prctr = auoMaterial.getDiv();
		String temp = "0000000000";// temp str for format
		b1Row.setPrctr(temp.substring(0, 10 - prctr.length()) + prctr);

		b1Row.setBeskz("X");
		// Add 5 set value not set in the previous epimshw code
		b1Row.setZeinr(auoMaterial.getMaterial());
		b1Row.setMatkl("000");
		b1Row.setSpart(auoMaterial.getDiv());
		b1Row.setZeiar("New");
		b1Row.setAeszn(sdf.format(auoMaterial.getEffectiveDate()));
		// end

		b1Table.appendRow(b1Row);
		rfc.setIBmmh1(b1Table);
		rfcInfo.append("BMMH1 \n");
		rfcInfo.append(Tab + "ZEINR>>" + b1Row.getZeinr() + ", MATKL>>"
				+ b1Row.getMatkl() + ", SPART>>" + b1Row.getSpart()
				+ ", ZEIAR>>" + b1Row.getZeiar() + ", AESZN>>"
				+ b1Row.getAeszn() + "\n");
		rfcInfo.append(Tab + "MEINS>>" + b1Row.getMeins() + ", TRAGR>>"
				+ b1Row.getTragr() + ", GEWEI>>" + b1Row.getGewei()
				+ ", MTPOS>>" + b1Row.getMtpos() + ", LADGR>>"
				+ b1Row.getLadgr() + ", XMCNG>>" + b1Row.getXmcng()
				+ ", SERNP>>" + b1Row.getSernp() + ", SBDKZ>>"
				+ b1Row.getSbdkz() + ", MTVFP>>" + b1Row.getMtvfp()
				+ ", DISMM>>" + b1Row.getDismm() + ", DISPO>>"
				+ b1Row.getDispo() + ", FHORI>>" + b1Row.getFhori()
				+ ", DISLS>>" + b1Row.getDisls() + ", MAABC>>"
				+ b1Row.getMaabc() + ", DISGR>>" + b1Row.getDisgr()
				+ ", PERKZ>>" + b1Row.getPerkz() + ", PRCTR>>"
				+ b1Row.getPrctr() + ", BESKZ>>" + b1Row.getBeskz() + "\n");

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
		rfc.setRfaNum(auoMaterial.getMaterial());
		rfcInfo.append("RFANUM \n");
		rfcInfo.append(Tab + "RFANumber>>" + auoMaterial.getMaterial() + "\n");

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
		return "Create Plant View For Material";
	}

	public void evaluate() throws Exception {
		execute();
	}
}