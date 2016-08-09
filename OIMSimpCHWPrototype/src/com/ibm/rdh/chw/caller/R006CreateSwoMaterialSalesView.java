package com.ibm.rdh.chw.caller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.rdh.chw.entity.AUOMaterial;
import com.ibm.rdh.chw.entity.CntryTax;
import com.ibm.rdh.rfc.Bmm00Table;
import com.ibm.rdh.rfc.Bmm00TableRow;
import com.ibm.rdh.rfc.Bmmh1Table;
import com.ibm.rdh.rfc.Bmmh1TableRow;
import com.ibm.rdh.rfc.Bmmh2Table;
import com.ibm.rdh.rfc.Bmmh2TableRow;
import com.ibm.rdh.rfc.Zdm_geo_to_classTable;
import com.ibm.rdh.rfc.Zdm_geo_to_classTableRow;

public class R006CreateSwoMaterialSalesView extends Rfc {

	private com.ibm.rdh.rfc.Z_DM_SAP_MATM_CREATE rfc;

	public R006CreateSwoMaterialSalesView(AUOMaterial auoMaterial,
			String salesOrg, String currentSapSalesStatus,
			Date currentEffectiveDate, String pimsIdentity) throws Exception {
		reInitialize();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdff = new SimpleDateFormat("ddMMyy");
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
		b0Row.setXeiv1("X");
		b0Row.setXeiv2("X");
		b0Row.setVkorg(salesOrg);
		b0Row.setVtweg("00");
		b0Table.appendRow(b0Row);
		rfc.setIBmm00(b0Table);

		rfcInfo.append("BMM00 \n");
		rfcInfo.append(Tab + "TCODE>>" + b0Row.getTcode() + ", MATNR>>"
				+ b0Row.getMatnr() + ", MBRSH>>" + b0Row.getMbrsh()
				+ ", MTART>>" + b0Row.getMtart() + ", XEIV1>>"
				+ b0Row.getXeiv1() + ", XEIV2>>" + b0Row.getXeiv2() + ", VKORG"
				+ b0Row.getVkorg() + ", VTWEG" + b0Row.getVtweg() + "\n");

		// Bmmh1 - B1
		Bmmh1Table b1Table = new Bmmh1Table();
		Bmmh1TableRow b1Row = b1Table.createEmptyRow();

		b1Row.setMeins("EA");
		b1Row.setDwerk("1222");
		b1Row.setMtpos("Z002");
		String ktgrm = auoMaterial.getAcctAsgnGrp();
		if (null == ktgrm || ktgrm.trim().equals("")) {
			b1Row.setKtgrm("01");
		} else {
			b1Row.setKtgrm(ktgrm);
		}

		b1Row.setSktof("X");
		b1Row.setMvgr1(auoMaterial.getMaterialGroup1());
		b1Row.setMvgr2("ZIP");
		b1Row.setVersg("1");
		if (null != currentSapSalesStatus) {
			b1Row.setVmsta(currentSapSalesStatus);
		}

		// check the status first. if there is no status, then don't set this
		// one
		if (null != currentSapSalesStatus) {
			String vmstd = sdf.format(currentEffectiveDate);
			if (null != vmstd) {
				b1Row.setVmstd(vmstd);
			}
		}
		b1Row.setAumng("1");
		b1Row.setProdh(auoMaterial.getCHWProdHierarchy());

		// Add 6 set value not set in the previous epimshw code
		b1Row.setZeinr(auoMaterial.getMaterial());
		b1Row.setMatkl("000");
		b1Row.setSpart(auoMaterial.getDiv());
		b1Row.setZeiar("New");
		b1Row.setGewei("KG");
		b1Row.setAeszn(sdff.format(auoMaterial.getEffectiveDate()));
		// end

		b1Table.appendRow(b1Row);
		rfc.setIBmmh1(b1Table);
		rfcInfo.append("BMMH1 \n");
		rfcInfo.append(Tab + "ZEINR>>" + b1Row.getZeinr() + ", MATKL>>"
				+ b1Row.getMatkl() + ", SPART>>" + b1Row.getSpart()
				+ ", ZEIAR>>" + b1Row.getZeiar() + ", GEWEI>>"
				+ b1Row.getGewei() + ", AESZN>>" + b1Row.getAeszn() + "\n");

		rfcInfo.append(Tab + "MEINS>>" + b1Row.getMeins() + ", DWERK"
				+ b1Row.getDwerk() + ", MTPOS" + b1Row.getMtpos() + ", KTGRM"
				+ b1Row.getKtgrm() + ", SKTOF" + b1Row.getSktof() + ", MVGR1"
				+ b1Row.getMvgr1() + ", MVGR2" + b1Row.getMvgr2() + ",VERSG"
				+ b1Row.getVersg() + ",VMSTA" + b1Row.getVmsta() + ", VMSTD"
				+ b1Row.getVmstd() + ", AUMNG" + b1Row.getAumng() + ", PRODH"
				+ b1Row.getProdh() + "\n");

		// Bmmh2 -B2
		Bmmh2Table b2Table = new Bmmh2Table();
		Enumeration e = auoMaterial.getCountryList().elements();
		while (e.hasMoreElements()) {
			Bmmh2TableRow b2Row = b2Table.createEmptyRow();
			CntryTax cntry = (CntryTax) e.nextElement();
			b2Row.setAland(cntry.getCountry());
			if ("US".equals(cntry.getCountry())) {
				b2Row.setTaty1("M");
			} else {
				b2Row.setTaty1("1");
			}
			b2Table.appendRow(b2Row);
			rfcInfo.append("BMMH2 \n");
			rfcInfo.append(Tab + "ALAND>>" + b2Row.getAland() + ", TATY1>>"
					+ b2Row.getTaty1() + "\n");
		}

		rfc.setIBmmh2(b2Table);
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
		return "Create Swo Material Sales View";
	}

	public void evaluate() throws Exception {
		execute();
	}
}