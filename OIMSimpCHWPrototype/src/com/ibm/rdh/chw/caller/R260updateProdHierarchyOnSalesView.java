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

public class R260updateProdHierarchyOnSalesView extends Rfc {

	private com.ibm.rdh.rfc.Z_DM_SAP_MATM_CREATE rfc;

	public R260updateProdHierarchyOnSalesView(CHWAnnouncement chwA,
			Object material, String pimsIdentity, String salesOrg,
			String productHierarchy, CHWGeoAnn chwAg, String acctAsgnGrp)
			throws Exception {
		reInitialize();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
		rfcName = "Z_DM_SAP_MATM_CREATE";

		rfc = new com.ibm.rdh.rfc.Z_DM_SAP_MATM_CREATE();

		// Set Up the RFC Fields
		// Bmm00 - B0 Structure

		Bmm00Table b0Table = new Bmm00Table();
		Bmm00TableRow b0Row = b0Table.createEmptyRow();

		b0Row.setTcode("MM01"); // For NEW
		if (material instanceof TypeModel) {
			b0Row.setMatnr(((TypeModel) material).getType()
					+ ((TypeModel) material).getModel());
		}
		// comment at 20160427
		// else if (material instanceof ServicePac)
		// {
		// b0Row.setMatnr(((ServicePac)material).getType()+((ServicePac)material).getModel());
		// }
		// else if (material instanceof Lseo)
		// {
		// b0Row.setMatnr(((Lseo)material).getSeoid());
		// }
		// else if (material instanceof LseoBundle)
		// {
		// b0Row.setMatnr(((LseoBundle)material).getSeoid());
		// }

		b0Row.setXeiv1("X");
		b0Row.setVkorg(salesOrg);
		b0Row.setVtweg("00");
		// add 20160427
		b0Row.setMbrsh("M");
		b0Row.setMtart("ZMAT");
		// end
		b0Table.appendRow(b0Row);
		rfc.setIBmm00(b0Table);

		rfcInfo.append("BMM00 \n");
		rfcInfo.append(Tab + "TCODE>>" + b0Row.getTcode() + ", MATNR>>"
				+ b0Row.getMatnr() + ", XEIV1>>" + b0Row.getXeiv1()
				+ ", VKORG>>" + b0Row.getVkorg() + ", VTWEG>>"
				+ b0Row.getVtweg() + "\n");
		rfcInfo.append(Tab + "MBRSH>>" + b0Row.getMbrsh() + ", MTART>>"
				+ b0Row.getMtart() + "\n");

		// Bmmh1
		Bmmh1Table b1Table = new Bmmh1Table();
		Bmmh1TableRow b1Row = b1Table.createEmptyRow();

		b1Row.setProdh(productHierarchy);
		if (null == acctAsgnGrp || acctAsgnGrp.equals("")) {
			b1Row.setKtgrm("01");
		} else {
			b1Row.setKtgrm(acctAsgnGrp);
		}
		// add 20160427
		b1Row.setMatkl("000");
		b1Row.setMeins("EA");
		b1Row.setZeinr(chwA.getAnnDocNo());
		b1Row.setZeiar(chwA.getAnnouncementType());
		b1Row.setAeszn(sdf.format(chwAg.getAnnouncementDate()));
		b1Row.setSpart((chwA.getDiv()));
		b1Row.setGewei("KG");
		// end
		b1Table.appendRow(b1Row);
		rfc.setIBmmh1(b1Table);

		rfcInfo.append("BMMH1 \n");
		rfcInfo.append(Tab + "PRODH>>" + b1Row.getProdh() + "\n");
		rfcInfo.append(Tab + ", KTGRM>>" + b1Row.getKtgrm() + "MATKL>>"
				+ b1Row.getMatkl() + ", MEINS>>" + b1Row.getMeins()
				+ ", ZEINR>>" + b1Row.getZeinr() + ", ZEIAR>>"
				+ b1Row.getZeiar() + ", AESZN>>" + b1Row.getAeszn()
				+ ", SPART>>" + b1Row.getSpart() + ", GEWEI>>"
				+ b1Row.getGewei() + "\n");

		// PIMSIdentity
		rfc.setPimsIdentity(pimsIdentity);
		rfcInfo.append("PIMSIdentity \n");
		rfcInfo.append(Tab + "PIMSIdentity>>" + pimsIdentity + "\n");

		// RFANUMBER
		rfc.setRfaNum(chwA.getAnnDocNo());
		rfcInfo.append("RFANUM \n");
		rfcInfo.append(Tab + "RFANumber>>" + chwA.getAnnDocNo() + "\n");

		// ZDM_GEO_TO_CLASS
		Zdm_geo_to_classTable zdmTable = new Zdm_geo_to_classTable();
		Zdm_geo_to_classTableRow zdmRow = zdmTable.createEmptyRow();

		zdmRow.setZGeo("US");

		zdmTable.appendRow(zdmRow);
		rfc.setGeoData(zdmTable);

		rfcInfo.append("ZDM_GEO_TO_CLASS \n");
		rfcInfo.append(Tab + "GEO>>" + zdmRow.getZGeo() + "\n");

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
		return "R260";
	}

	public void evaluate() throws Exception {
		execute();
	}
}
