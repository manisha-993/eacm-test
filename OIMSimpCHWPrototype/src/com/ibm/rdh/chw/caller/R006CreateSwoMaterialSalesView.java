package com.ibm.rdh.chw.caller;

import java.text.SimpleDateFormat;
import java.util.Date;

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

public class R006CreateSwoMaterialSalesView extends Rfc {

	private com.ibm.rdh.rfc.Z_DM_SAP_MATM_CREATE rfc;

	public R006CreateSwoMaterialSalesView(CHWAnnouncement chwA,
			TypeModel typeModel, CHWGeoAnn chwAg, String salesOrg,
			String currentSapSalesStatus, Date currentEffectiveDate,
			String productHierarchy, String pimsIdentity) throws Exception {
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
		// b0Row.setMatnr(typeModel.getRevProfile().getAuoMaterials());
		b0Row.setMatnr(typeModel.getType());
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
		b1Row.setMtpos("Z002");

		b1Row.setKtgrm("01");
		// String ktgrm =
		// _productSchedule.getMainProduct().getSapAccountAssignmentGroup();
		// if (null == ktgrm || ktgrm.trim().equals(""))
		// {
		// // do not set account assignment group (ktgrm) if we can't make the
		// determination
		// } else
		// {
		// logSetter("ktgrm", ktgrm);
		// b1Row.setKtgrm(ktgrm);
		// }

		b1Row.setSktof("X");

		// String pt = _productSchedule.getMainProduct().getSapMaterialGroup1();
		// if ("I".equals(pt)) {
		// b1Row.setMvgr1("ICS");
		//
		// } else if ("X".equals(pt)) {
		// b1Row.setMvgr1("VRD");
		//
		// } else if ("P".equals(pt)) {
		// b1Row.setMvgr1("PCS");
		//
		// } else {
		// b1Row.setMvgr1("SWC");
		//
		// }

		// The logic of Mvgr2 need to be confirmed.
		// Set mvgr2 based on enablementProcess. If "EPIMS_AAS", then set to
		// "ZIP".
		// If "EPIMS_XSERIES", then set to "XCC". Otherwise error.
		b1Row.setMvgr2("ZIP");

		b1Row.setVersg("1");

		// If the "currentSapSalesStatus" and "currentEffectiveDate" input
		// parameters are set to values,
		// then set vmsta to currentSapSalesStatus and set vmstd to
		// "currentEffectiveDate". (done in setters)
		if (null == currentSapSalesStatus) {
			// if null then do not set the field
			logSetter("vmsta");
		} else {
			logSetter("vmsta", currentSapSalesStatus);
			b1Row.setVmsta(currentSapSalesStatus);
		}

		// check the status first. if there is no status, then don't set this
		// one
		if (null == currentSapSalesStatus) {
			// if null then do not set the field
			logSetter("vmstd");
		} else {
			String vmstd = sdf.format(currentEffectiveDate);
			if (null == vmstd) {
				logSetter("vmstd");
			} else {
				logSetter("vmstd", vmstd);
				b1Row.setVmstd(vmstd);
			}
		}
		b1Row.setAumng("1");
		b1Row.setProdh(productHierarchy);
		b1Row.setBeskz("X");

		// Add 6 set value not set in the previous epimshw code
		b1Row.setZeinr(chwA.getAnnDocNo());
		b1Row.setMatkl("000");
		b1Row.setSpart(typeModel.getDiv());
		b1Row.setZeiar(chwA.getAnnouncementType());
		b1Row.setGewei("KG");
		b1Row.setAeszn(sdff.format(chwAg.getAnnouncementDate()));
		// end

		b1Table.appendRow(b1Row);
		rfc.setIBmmh1(b1Table);
		rfcInfo.append("BMMH1 \n");
		rfcInfo.append(Tab + "ZEINR>>" + b1Row.getZeinr() + ", MATKL>>"
				+ b1Row.getMatkl() + ", SPART>>" + b1Row.getSpart()
				+ ", ZEIAR>>" + b1Row.getZeiar() + ", GEWEI>>"
				+ b1Row.getGewei() + ", AESZN>>" + b1Row.getAeszn() + "\n");

		rfcInfo.append(Tab + "MEINS>>" + b1Row.getMeins() + ", MTPOS"
				+ b1Row.getMtpos() + ", KTGRM" + b1Row.getKtgrm() + ", SKTOF"
				+ b1Row.getSktof() + ", MVGR1" + b1Row.getMvgr1() + ", MVGR2"
				+ b1Row.getMvgr2() + ",VERSG" + b1Row.getVersg() + ",VMSTA"
				+ b1Row.getVmsta() + ", VMSTD" + b1Row.getVmstd() + ", AUMNG"
				+ b1Row.getAumng() + ", PRODH" + b1Row.getProdh() + ", BESKZ"
				+ b1Row.getBeskz() + "\n");

		// Bmmh2 -B2
		// List<SapCountry> countries = sapSalesOrg.getSapCountries();
		// for (SapCountry country : countries) {
		// Bmmh2TableRow bh2row = getBmh2().createEmptyRow();
		// setAland(bh2row, country.getIsoCountry());
		// setTaty1(bh2row, country.getTaxCategory());
		// setTaxm1(bh2row, country.getTaxClass());
		// getBmh2().appendRow(bh2row);
		// }
		// Bmmh2Table b2Table = new Bmmh2Table();
		// if (taxCntryList != null) {
		// Enumeration e = taxCntryList.elements();
		// while (e.hasMoreElements()) {
		// Bmmh2TableRow b2Row = b2Table.createEmptyRow();
		// CntryTax cntry = (CntryTax) e.nextElement();
		// b2Row.setAland(cntry.getCountry());
		// b2Row.setTaty1(cntry.getTaxCategory());
		// b2Row.setTaxm1(cntry.getClassification());
		//
		// b2Table.appendRow(b2Row);
		// rfcInfo.append("BMMH2 \n");
		// rfcInfo.append(Tab + "ALAND>>" + b2Row.getAland() + ", TATY1>>"
		// + b2Row.getTaty1() + ", TAXM1>>" + b2Row.getTaxm1()
		// + "\n");
		// }
		// }
		// rfc.setIBmmh2(b2Table);

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
		return "Create Swo Material Sales View";
	}

	public void evaluate() throws Exception {
		execute();
	}
}