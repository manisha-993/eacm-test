package com.ibm.rdh.chw.caller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

import com.ibm.db2.jcc.a.b;
import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.PropertyKeys;
import com.ibm.pprds.epimshw.util.ConfigManager;
import com.ibm.rdh.chw.entity.CHWAnnouncement;
import com.ibm.rdh.chw.entity.CntryTax;
import com.ibm.rdh.chw.entity.PlannedSalesStatus;
import com.ibm.rdh.rfc.Bmm00Table;
import com.ibm.rdh.rfc.Bmm00TableRow;
import com.ibm.rdh.rfc.Bmmh1Table;
import com.ibm.rdh.rfc.Bmmh1TableRow;
import com.ibm.rdh.rfc.Bmmh2Table;
import com.ibm.rdh.rfc.Bmmh2TableRow;
import com.ibm.rdh.rfc.Zdm_geo_to_classTable;
import com.ibm.rdh.rfc.Zdm_geo_to_classTableRow;

public class R117createTypeModelMaterialSalesView extends Rfc {

	private com.ibm.rdh.rfc.Z_DM_SAP_MATM_CREATE rfc;

	public R117createTypeModelMaterialSalesView(CHWAnnouncement chwA,
			String typemod, String div, String acctAsgnGrp,
			PlannedSalesStatus ps, boolean bumpCtr, String pimsIdentity,
			String flfil, String salesOrg1, String productHierarchy,
			Vector VectTaxList, String plantValue) throws Exception {
		reInitialize();
		Date curDate = new Date();
		String sDateFormat = ConfigManager.getConfigManager().getString(
				PropertyKeys.KEY_DATE_FORMAT, true);
		SimpleDateFormat sdf = new SimpleDateFormat(sDateFormat);
		SimpleDateFormat sdff = new SimpleDateFormat("ddMMyy");

		rfcName = "Z_DM_SAP_MATM_CREATE";

		rfc = new com.ibm.rdh.rfc.Z_DM_SAP_MATM_CREATE();

		// Set Up the RFC Fields
		// Bmm00 - B0 Structure

		Bmm00Table b0Table = new Bmm00Table();
		Bmm00TableRow b0Row = b0Table.createEmptyRow();

		b0Row.setTcode("MM01");
		b0Row.setMatnr(typemod); // RQ0724066720 changes

		b0Row.setMbrsh("M");

		b0Row.setMtart("ZPRT");
		b0Row.setXeib1("X");
		b0Row.setXeiv1("X");
		b0Row.setXeiv2("X");
		b0Row.setVkorg(salesOrg1);
		b0Row.setVtweg("00");

		b0Table.appendRow(b0Row);
		rfc.setIBmm00(b0Table);

		rfcInfo.append("B0 \n");
		rfcInfo.append(Tab + "TCODE>>" + b0Row.getTcode() 
				+ ", MATNR>>" + b0Row.getMatnr() 
				+ ", XEIB1>>" + b0Row.getXeib1()
				+ ", MBRSH>>" + b0Row.getMbrsh() 
				+ ", MTART>>" + b0Row.getMtart() 
				+ ", XEIV1>>" + b0Row.getXeiv1()
				+ ", XEIV2>>" + b0Row.getXeiv2() 
				+ ", VKORG>>" + b0Row.getVkorg() 
				+ ", VTWEG>>" + b0Row.getVtweg() + "\n");

		Bmmh1Table b1Table = new Bmmh1Table();
		Bmmh1TableRow b1Row = b1Table.createEmptyRow();

		b1Row.setMeins("EA");

		b1Row.setDwerk(plantValue);
		b1Row.setProdh(productHierarchy); // RQ0615062020 Changes
		b1Row.setMtpos("ZSUP");

		b1Row.setKtgrm(acctAsgnGrp); // RQ0411077139 Changes
		b1Row.setSktof("X");
		b1Row.setScmng("1");
		// add at 20160301
		b1Row.setMatkl("000");
		b1Row.setZeinr(chwA.getAnnDocNo());
		b1Row.setAeszn(sdff.format(curDate));
		b1Row.setGewei("KG");
		b1Row.setSpart(div);
		b1Row.setZeiar(chwA.getAnnouncementType());
		//end of add
		
		if (chwA.getSegmentAcronym().equals("RSS")) {
			b1Row.setMvgr1("POS");
		} else if (chwA.getSegmentAcronym().equals("AS4")) {
			b1Row.setMvgr1("ICH");
		} else if (chwA.getSegmentAcronym().equals("RS6")) {
			b1Row.setMvgr1("PCH");
		}
		// CR0217061532
		else if (chwA.isXccOnlyDiv(div)) { // RQ0724066720 changes
			b1Row.setMvgr1("VRD");

		} else {
			b1Row.setMvgr1("HWC");
		}

		if (flfil.equalsIgnoreCase("BTH")) {
			b1Row.setMvgr2("BTH");
		} else if (flfil.equalsIgnoreCase("XCC")) {
			b1Row.setMvgr2("XCC");
		} else if (flfil.equalsIgnoreCase("ZIP")) {
			b1Row.setMvgr2("ZIP");
		} else if (flfil.equalsIgnoreCase("EMPTY")) {

		} else {
		}
		b1Row.setMvgr3("ST");
		b1Row.setVersg("1");

		if (ps != null) {
			if (flfil.equalsIgnoreCase("BTH")) {

				if (ps.getCurrentSalesStatus() != null) {
					b1Row.setVmsta(ps.getCurrentSalesStatus());
				} else {
					b1Row.setVmsta("Z0");
				}
			} else if (flfil.equalsIgnoreCase("XCC")) {
				if (ps.getCurrentSalesStatus() != null) {
					b1Row.setVmsta(ps.getCurrentSalesStatus());
				} else {
					b1Row.setVmsta("Z0");
				}
			}

			else {
				b1Row.setVmsta("Z0");
			}

			if (flfil.equalsIgnoreCase("BTH")) {
				if ((ps.getCurrentEffectiveDate()) == null) {
					b1Row.setVmstd(sdf.format(curDate));
				} else {
					b1Row.setVmstd(sdf.format(ps.getCurrentEffectiveDate()));
				}
			} else if (flfil.equalsIgnoreCase("XCC")) {
				if ((ps.getCurrentEffectiveDate()) == null) {
					b1Row.setVmstd(sdf.format(curDate));
				} else {
					b1Row.setVmstd(sdf.format(ps.getCurrentEffectiveDate()));
				}
			} else {
				b1Row.setVmstd(sdf.format(curDate));
			}
		} else {
			b1Row.setVmsta("Z0");
			b1Row.setVmstd(sdf.format(curDate));
		}

		b1Row.setAumng("1");
		b1Row.setVprsv("S");
		b1Row.setPeinh("1");
		b1Row.setStprs("1");
		b1Row.setBklas("7900");

		b1Table.appendRow(b1Row);
		rfc.setIBmmh1(b1Table);

		rfcInfo.append("B1 \n");
		rfcInfo.append(Tab
				+ "MEINS>>" + b1Row.getMeins()
				+ ", DWERK>>" + b1Row.getDwerk()
				+ ", PRODH>>" + b1Row.getProdh()
				// RQ0615062020 Changes
				+ ", MTPOS>>" + b1Row.getMtpos() 
				+ ", KTGRM>>" + b1Row.getKtgrm()
				+ ", SKTOF>>" + b1Row.getSktof() 
				+ ", SCMNG>>" + b1Row.getScmng() 
				+ ", MVGR1>>" + b1Row.getMvgr1()
				+ ", MVGR2>>" + b1Row.getMvgr2() 
				+ ", MVGR3>>" + b1Row.getMvgr3() 
				+ ", VERSG>>" + b1Row.getVersg()
				+ ", VMSTA>>" + b1Row.getVmsta() 
				+ ", VMSTD>>" + b1Row.getVmstd() 
				+ ", AUMNG>>" + b1Row.getAumng()
				+ ", VPRSV>>" + b1Row.getVprsv() 
				+ ", PEINH>>" + b1Row.getPeinh() 
				+ ", STPRS>>" + b1Row.getStprs()
				+ ", BKLAS>>" + b1Row.getBklas() + "\n");

		Bmmh2Table b2Table = new Bmmh2Table();
		
		if (VectTaxList != null) {
			Enumeration e = VectTaxList.elements();
			while (e.hasMoreElements()) {
				Bmmh2TableRow b2Row = b2Table.createEmptyRow();
				CntryTax cntry = (CntryTax) e.nextElement();
				b2Row.setAland(cntry.getCountry());
				b2Row.setTaty1(cntry.getTaxCategory());
				if ((cntry.getCountry().equals("US"))
						&& (div.equals("46") || div.equals("48")))
					b2Row.setTaxm1("1");
				else
					b2Row.setTaxm1(cntry.getClassification());
				b2Table.appendRow(b2Row);

				rfcInfo.append("B2 \n");
				rfcInfo.append(Tab + "ALAND>>" + b2Row.getAland() 
						+ ", TATY1>>" + b2Row.getTaty1() 
						+ ", TAXM1>>" + b2Row.getTaxm1()
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
		return "Create Type/Model material sales view";
	}

	@Override
	public String getRfcName() {
		return ClassUtil.getSimpleClassName(getRfc());
	}

	public void evaluate() throws Exception {

		execute();
	}


}
