package com.ibm.rdh.chw.caller;

import java.text.SimpleDateFormat;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.PropertyKeys;
import com.ibm.pprds.epimshw.util.ConfigManager;
import com.ibm.pprds.epimshw.util.ProfitCenterPlantSelector;
import com.ibm.rdh.chw.entity.CHWAnnouncement;
import com.ibm.rdh.chw.entity.CHWGeoAnn;
import com.ibm.rdh.chw.entity.TypeModel;
import com.ibm.rdh.chw.entity.TypeModelUPGGeo;
import com.ibm.rdh.rfc.Bmm00Table;
import com.ibm.rdh.rfc.Bmm00TableRow;
import com.ibm.rdh.rfc.Bmmh1Table;
import com.ibm.rdh.rfc.Bmmh1TableRow;
import com.ibm.rdh.rfc.Zdm_geo_to_classTable;
import com.ibm.rdh.rfc.Zdm_geo_to_classTableRow;

public class R101createGenericPlantViewforMaterial extends Rfc {
	private com.ibm.rdh.rfc.Z_DM_SAP_MATM_CREATE rfc;

	public R101createGenericPlantViewforMaterial(CHWAnnouncement chwA,
			TypeModel typeModel, CHWGeoAnn chwAg, String newFlag,
			String loadingGrp, TypeModelUPGGeo tmUPGObj, String FromToType,
			String pimsIdentity, String plantValue) throws Exception {
		reInitialize();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
		rfcName = "Z_DM_SAP_MATM_CREATE";
		rfc = new com.ibm.rdh.rfc.Z_DM_SAP_MATM_CREATE();

		// Set Up the RFC Fields
		// Bmm00 - B0 Structure
		Bmm00Table b0Table = new Bmm00Table();
		Bmm00TableRow b0Row = b0Table.createEmptyRow();

		b0Row.setTcode("MM01");
		if (("1999".equals(plantValue))
				&& (("NEW".equals(newFlag)) || ("UPG".equals(newFlag)) || ("MTC"
						.equals(newFlag))))
			b0Row.setLgort("CHW1");

		if ("NEW".equals(newFlag)) {
			b0Row.setMatnr(typeModel.getType() + "NEW");
		} else if ("UPG".equals(newFlag)) {
			b0Row.setMatnr(typeModel.getType() + "UPG");
		} else if ("MTC".equals(newFlag) && "MTCTOTYPE".equals(FromToType)) {
			b0Row.setMatnr(tmUPGObj.getType() + "MTC");
		} else if ("MTC".equals(newFlag) && "MTCFROMTYPE".equals(FromToType)) {
			b0Row.setMatnr(tmUPGObj.getFromType() + "MTC");
		}
		b0Row.setMbrsh("M");
		b0Row.setMtart("ZMAT");
		b0Row.setXeiv4("X");
		b0Row.setWerks(plantValue);
		b0Row.setXeid1("X");
		b0Row.setXeid2("X");
		// SAP Ledger
		if ("Y".equals(ConfigManager.getConfigManager().getString(
				PropertyKeys.KEY_SAP_LEDGER))) {
			System.out.println("Type Model's Vendor ID in rfc R101"
					+ typeModel.getVendorID());
			if (typeModel.getVendorID() != null
					&& (!"".equals(typeModel.getVendorID()))) {
				b0Row.setXeie1("X");
			}
		}

		b0Table.appendRow(b0Row);
		rfc.setIBmm00(b0Table);

		rfcInfo.append("BMM00 \n");
		rfcInfo.append(Tab + "TCODE>>" + b0Row.getTcode() + ", MATNR>>"
				+ b0Row.getMatnr() + ", MBRSH>>" + b0Row.getMbrsh()
				+ ", MTART>>" + b0Row.getMtart() + ", XEIV4>>"
				+ b0Row.getXeiv4() + ", WERKS>>" + b0Row.getWerks()
				+ ", LGORT>>" + b0Row.getLgort() + ", XEID1>>"
				+ b0Row.getXeid1() + ", XEID2>>" + b0Row.getXeid2()
				+ ", XEIE1>>" + b0Row.getXeie1() + "\n");

		// Bmmh1 - B1
		Bmmh1Table b1Table = new Bmmh1Table();
		Bmmh1TableRow b1Row = b1Table.createEmptyRow();

		b1Row.setMeins("EA");
		b1Row.setTragr("STD");

		// add 3 set value, In the previous epimshw code
		b1Row.setZeinr(chwA.getAnnDocNo());
		b1Row.setMatkl("000");
		b1Row.setSpart(typeModel.getDiv());
		// end

		if ("MTC".equals(newFlag)) {
			b1Row.setLadgr(tmUPGObj.getLoadingGroup());
		} else {
			b1Row.setLadgr(loadingGrp);
		}
		b1Row.setMtpos("Z002");
		if ("NEW".equals(newFlag)) {
			b1Row.setKtgrm("01");
		} else if ("UPG".equals(newFlag) || "MTC".equals(newFlag)) {
			b1Row.setKtgrm("06");
		}

		b1Row.setZeiar(chwA.getAnnouncementType());
		b1Row.setAeszn(sdf.format(chwAg.getAnnouncementDate()));
		b1Row.setGewei("KG");
		b1Row.setXmcng("X");
		b1Row.setSernp("ZCHW");
		b1Row.setSbdkz("1");
		if (("1999").equals(plantValue))
			b1Row.setMtvfp("NC");
		else
			b1Row.setMtvfp("ZE");
		b1Row.setDismm("PD");
		b1Row.setDispo("000");
		b1Row.setFhori("000");
		b1Row.setDisls("EX");
		b1Row.setMaabc("A");
		b1Row.setDisgr("Z025");
		b1Row.setPerkz("M");
		// SAP Ledger
		// boolean existsPro = false;

		boolean existsPro = ProfitCenterPlantSelector
				.checkProfitCenterPlants(plantValue);
		System.out
				.println("Printing the value of boolean exists in the rfc R101 "
						+ existsPro);
		if (existsPro) {
			if (typeModel.getProfitCenter() != null
					&& (!"".equals(typeModel.getProfitCenter()))) {
				b1Row.setPrctr(typeModel.getProfitCenter());
			}
		}
		if (typeModel.getVendorID() != null
				&& (!"".equals(typeModel.getVendorID()))) {
			b1Row.setEkgrp("001");
		}
		if (!typeModel.getVendorID().equals("")) {
			b1Row.setMfrnr(typeModel.getVendorID());
		} else {
			b1Row.setMfrnr("?");
		}

		b1Table.appendRow(b1Row);
		rfc.setIBmmh1(b1Table);

		rfcInfo.append("BMMH1 \n");
		rfcInfo.append(Tab + "ZEINR>>" + b1Row.getZeinr() + ", MATKL>>"
				+ b1Row.getMatkl() + ", SPART>>" + b1Row.getSpart() + "\n");

		rfcInfo.append(Tab + "MEINS>>" + b1Row.getMeins() + ", TRAGR>>"
				+ b1Row.getTragr() + ", LADGR>>" + b1Row.getLadgr()
				+ ", MTPOS>>" + b1Row.getMtpos() + ", KTGRM>>"
				+ b1Row.getKtgrm() + ", ZEIAR>>" + b1Row.getZeiar()
				+ ", AESZN>>" + b1Row.getAeszn() + ", GEWEI>>"
				+ b1Row.getGewei() + ", XMCNG>>" + b1Row.getXmcng()
				+ ", SERNP>>" + b1Row.getSernp() + ", SBDKZ>>"
				+ b1Row.getSbdkz() + ", MTVFP>>" + b1Row.getMtvfp()
				+ ", DISMM>>" + b1Row.getDismm() + ", DISPO>>"
				+ b1Row.getDispo() + ", FHORI>>" + b1Row.getFhori()
				+ ", DISLS>>" + b1Row.getDisls() + ", MAABC>>"
				+ b1Row.getMaabc() + ", DISGR>>" + b1Row.getDisgr()
				+ ", PERKZ>>" + b1Row.getPerkz() + ", PRCTR>>"
				+ b1Row.getPrctr() + ", EKGRP>>" + b1Row.getEkgrp()
				+ ", MFRNR>>" + b1Row.getMfrnr() + "\n");

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
		return "Create Generic Plant View for material";
	}

	public void evaluate() throws Exception {
		execute();
	}
}
