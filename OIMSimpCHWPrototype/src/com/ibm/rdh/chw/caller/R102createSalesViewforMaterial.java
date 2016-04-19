package com.ibm.rdh.chw.caller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.PropertyKeys;
import com.ibm.pprds.epimshw.util.ConfigManager;
import com.ibm.rdh.chw.entity.CHWAnnouncement;
import com.ibm.rdh.chw.entity.CntryTax;
import com.ibm.rdh.chw.entity.TypeModel;
import com.ibm.rdh.chw.entity.TypeModelUPGGeo;
import com.ibm.rdh.rfc.Bmm00Table;
import com.ibm.rdh.rfc.Bmm00TableRow;
import com.ibm.rdh.rfc.Bmmh1Table;
import com.ibm.rdh.rfc.Bmmh1TableRow;
import com.ibm.rdh.rfc.Bmmh2Table;
import com.ibm.rdh.rfc.Bmmh2TableRow;
import com.ibm.rdh.rfc.Zdm_geo_to_classTable;
import com.ibm.rdh.rfc.Zdm_geo_to_classTableRow;

public class R102createSalesViewforMaterial extends Rfc {

	private com.ibm.rdh.rfc.Z_DM_SAP_MATM_CREATE rfc;

	public R102createSalesViewforMaterial(CHWAnnouncement chwA,
			TypeModel typeModel, String sapPlant, String newFlag,
			TypeModelUPGGeo tmUPGObj, String FromToType, String pimsIdentity,
			String flfilcd, String salesOrg, Vector taxCntryList)
			throws Exception {

		reInitialize();
		Date curDate = new Date();
		// SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
		String sDateFormat = ConfigManager.getConfigManager().getString(
				PropertyKeys.KEY_DATE_FORMAT, true);
		SimpleDateFormat sdff = new SimpleDateFormat("ddMMyy");
		SimpleDateFormat sdf = new SimpleDateFormat(sDateFormat);
		rfcName = "Z_DM_SAP_MATM_CREATE";

		rfc = new com.ibm.rdh.rfc.Z_DM_SAP_MATM_CREATE();

		// Set Up the RFC Fields
		// Bmm00 - B0 Structure

		Bmm00Table b0Table = new Bmm00Table();
		Bmm00TableRow b0Row = b0Table.createEmptyRow();

		b0Row.setTcode("MM01");

		if (("NEW").equals(newFlag)) {
			b0Row.setMatnr(typeModel.getType() + "NEW");
		} else if (("UPG").equals(newFlag)) {
			b0Row.setMatnr(typeModel.getType() + "UPG");
		} else if (("MTC").equals(newFlag) && ("MTCTOTYPE").equals(FromToType)) {
			b0Row.setMatnr(tmUPGObj.getType() + "MTC");
		} else if (("MTC").equals(newFlag)
				&& ("MTCFROMTYPE").equals(FromToType)) {
			b0Row.setMatnr(tmUPGObj.getFromType() + "MTC");
		}
		b0Row.setMbrsh("M");
		b0Row.setMtart("ZMAT");
		b0Row.setXeiv1("X");
		b0Row.setXeiv2("X");
		b0Row.setVkorg(salesOrg);
		b0Row.setVtweg("00");

		b0Table.appendRow(b0Row);
		rfc.setIBmm00(b0Table);

		rfcInfo.append("BMM00 \n");
		rfcInfo.append(Tab + "TCODE>>" + b0Row.getTcode() + ", MATNR>>"
				+ b0Row.getMatnr() + ", MBRSH>>" + b0Row.getMbrsh()
				+ ", MTART>>" + b0Row.getMtart() + ", XEIV4>>"
				+ b0Row.getXeiv1() + ", XEIV2>>" + b0Row.getXeiv2()
				+ ", VKORG>>" + b0Row.getVkorg() + ", VTWEG>>"
				+ b0Row.getVtweg() + "\n");

		Bmmh1Table b1Table = new Bmmh1Table();
		Bmmh1TableRow b1Row = b1Table.createEmptyRow();

		b1Row.setMeins("EA");
		b1Row.setDwerk(sapPlant);
		b1Row.setMtpos("Z002");

		if ("NEW".equals(newFlag)) {
			b1Row.setKtgrm("01");
		} else if ("UPG".equals(newFlag) || "MTC".equals(newFlag)) {
			b1Row.setKtgrm("06");
		}

		b1Row.setSktof("X");
		b1Row.setScmng("1");

		if ("RSS".equals(chwA.getSegmentAcronym())) {
			b1Row.setMvgr1("POS");
		} else if ("AS4".equals(chwA.getSegmentAcronym())) {
			b1Row.setMvgr1("ICH");
		} else if ("RS6".equals(chwA.getSegmentAcronym())) {
			b1Row.setMvgr1("PCH");
		} else if ("LSC".equals(chwA.getSegmentAcronym())) {
			b1Row.setMvgr1("ZCH");
		} else if (chwA.isXccOnlyDiv(typeModel.getDiv())) {
			b1Row.setMvgr1("VRD");
		}

		else {
			b1Row.setMvgr1("HWC");
		}

		if ("EMPTY".equalsIgnoreCase(flfilcd)) {
		} else {
			b1Row.setMvgr2(flfilcd);
		}

		b1Row.setMvgr3("ST");
		b1Row.setVersg("1");
		b1Row.setVmsta("Z0");
		b1Row.setVmstd(sdf.format(curDate));

		b1Row.setAumng("1");

		// add 6 set value, In the previous epimshw code 
		b1Row.setZeinr(chwA.getAnnDocNo());
		b1Row.setMatkl("000");
		b1Row.setSpart(typeModel.getDiv()); // RQ0724066720 changes
		b1Row.setZeiar(chwA.getAnnouncementType());
		// b1Row.setAeszn(sdf.format(chwAg.getAnnouncementDate()));
		b1Row.setAeszn(sdff.format(curDate));
		b1Row.setGewei("KG");
		//end
		
		b1Table.appendRow(b1Row);
		rfc.setIBmmh1(b1Table);

		rfcInfo.append("BMMH1 \n");
		rfcInfo.append(Tab + "MEINS>>" + b1Row.getMeins() + ", DWERK>>"
				+ b1Row.getDwerk() + ", KTGRM>>" + b1Row.getKtgrm()
				+ ", MTPOS>>" + b1Row.getMtpos() + ", SKTOF>>"
				+ b1Row.getSktof() + ", SCMNG>>" + b1Row.getScmng()
				+ ", MVGR1>>" + b1Row.getMvgr1() + ", MVGR2>>"
				+ b1Row.getMvgr2() + ", MVGR3>>" + b1Row.getMvgr3()
				+ ", VERSG>>" + b1Row.getVersg() + ", VMSTA>>"
				+ b1Row.getVmsta() + ", VMSTD>>" + b1Row.getVmstd()
				+ ", AUMNG>>" + b1Row.getAumng() + "\n");

		Bmmh2Table b2Table = new Bmmh2Table();
		if (taxCntryList != null) {
			Enumeration e = taxCntryList.elements();
			while (e.hasMoreElements()) {
				Bmmh2TableRow b2Row = b2Table.createEmptyRow();
				CntryTax cntry = (CntryTax) e.nextElement();
				b2Row.setAland(cntry.getCountry());
				b2Row.setTaty1(cntry.getTaxCategory());
				b2Row.setTaxm1(cntry.getClassification());

				b2Table.appendRow(b2Row);

				rfcInfo.append("BMMH2 \n");
				rfcInfo.append(Tab + "ALAND>>" + b2Row.getAland() + ", TATY1>>"
						+ b2Row.getTaty1() + ", TAXM1>>" + b2Row.getTaxm1()
						+ "\n" + "\n");
			}
		}
		rfc.setIBmmh2(b2Table);

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
		// TODO Auto-generated method stub
		return "Create Sales View for material";
	}

	@Override
	public String getRfcName() {
		return ClassUtil.getSimpleClassName(getRfc());
	}

	public com.ibm.rdh.rfc.Z_DM_SAP_MATM_CREATE getRfc() {
		return rfc;
	}

	public void evaluate() throws Exception {
		execute();
	}

}
