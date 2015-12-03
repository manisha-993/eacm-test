package com.ibm.rdh.chw.caller;

import java.text.SimpleDateFormat;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.rdh.chw.entity.CHWAnnouncement;
import com.ibm.rdh.chw.entity.CHWGeoAnn;
import com.ibm.rdh.chw.entity.TypeModel;
import com.ibm.rdh.chw.entity.TypeModelUPGGeo;
import com.ibm.rdh.rfc.Bmm00Table;
import com.ibm.rdh.rfc.Bmm00TableRow;
import com.ibm.rdh.rfc.Bmmh1Table;
import com.ibm.rdh.rfc.Bmmh1TableRow;
import com.ibm.rdh.rfc.Bmmh5Table;
import com.ibm.rdh.rfc.Bmmh5TableRow;
import com.ibm.rdh.rfc.Zdm_geo_to_classTable;
import com.ibm.rdh.rfc.Zdm_geo_to_classTableRow;


public class R100createTypeMaterialBasicView extends Rfc {
	private com.ibm.rdh.rfc.Z_DM_SAP_MATM_CREATE rfc;

	public R100createTypeMaterialBasicView(CHWAnnouncement chwA,
			TypeModel typeModel, CHWGeoAnn chwAg, String newFlag,
			TypeModelUPGGeo tmUPGObj, String FromToType, String pimsIdentity)
			throws Exception {
		reInitialize();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");

		rfc = new com.ibm.rdh.rfc.Z_DM_SAP_MATM_CREATE();

		// Set Up the RFC Fields
		// Bmm00 - B0 Structure

		Bmm00Table b0Table = new Bmm00Table();
		Bmm00TableRow b0Row = b0Table.createEmptyRow();

		b0Row.setTcode("MM01");
		if (newFlag.equals("NEW")) {
			b0Row.setMatnr(typeModel.getType() + "NEW");
		} else if (newFlag.equals("UPG")) {
			b0Row.setMatnr(typeModel.getType() + "UPG");
		} else if (newFlag.equals("MTC") && FromToType.equals("MTCTOTYPE")) {
			b0Row.setMatnr(tmUPGObj.getType() + "MTC");
		} else if (newFlag.equals("MTC") && FromToType.equals("MTCFROMTYPE")) {
			b0Row.setMatnr(tmUPGObj.getFromType() + "MTC");
		}

		b0Row.setMbrsh("M");
		b0Row.setMtart("ZMAT");
		// b0Row.setXeiv4("X");

		b0Row.setXeik1("X");

		b0Table.appendRow(b0Row);
		rfc.setIBmm00(b0Table);

		rfcInfo.append("BMM00 \n");
		rfcInfo.append(Tab + "TCODE>>" + b0Row.getTcode() + ", MATNR>>"
				+ b0Row.getMatnr() + ", MBRSH>>" + b0Row.getMbrsh()
				+ ", MTART>>" + b0Row.getMtart() + ", XEIK1>>"
				+ b0Row.getXeik1() +
				// ", XEIV4>>"+b0Row.getXeiv4()+
				"\n");

		Bmmh1Table b1Table = new Bmmh1Table();
		Bmmh1TableRow b1Row = b1Table.createEmptyRow();

		b1Row.setMeins("EA");
		b1Row.setZeinr(chwA.getAnnDocNo());
		b1Row.setMatkl("000");

		b1Row.setSpart(typeModel.getDiv()); // RQ0724066720 changes
		b1Row.setZeiar(chwA.getAnnouncementType());
		b1Row.setAeszn(sdf.format(chwAg.getAnnouncementDate()));
		b1Row.setGewei("KG");
		if (newFlag.equals("MTC")) {
			if (tmUPGObj.getProductHierarchy() != null
					|| (tmUPGObj.getProductHierarchy() != "")) {
				b1Row.setPrdha(tmUPGObj.getProductHierarchy());
			}
		} else {
			if (typeModel.getProductHierarchy() != null
					|| (typeModel.getProductHierarchy() != "")) {
				b1Row.setPrdha(typeModel.getProductHierarchy());
			}
		}

		b1Table.appendRow(b1Row);
		rfc.setIBmmh1(b1Table);

		rfcInfo.append("BMMH1 \n");
		rfcInfo.append(Tab + "MEINS>>" + b1Row.getMeins() + ", ZEINR>>"
				+ b1Row.getZeinr() + ", MATKL>>" + b1Row.getMatkl()
				+ ", SPART>>" + b1Row.getSpart() + ", ZEIAR>>"
				+ b1Row.getZeiar() + ", AESZN>>" + b1Row.getAeszn()
				+ ", GEWEI>>" + b1Row.getGewei() + "\n");
		if (newFlag.equals("MTC")) {
			if (tmUPGObj.getProductHierarchy() != null
					|| (tmUPGObj.getProductHierarchy() != "")) {
				rfcInfo.append(Tab + "PRDHA>>" + b1Row.getPrdha() + "\n");
			}
		} else {
			if (typeModel.getProductHierarchy() != null
					|| (typeModel.getProductHierarchy() != "")) {
				rfcInfo.append(Tab + "PRDHA>>" + b1Row.getPrdha() + "\n");
			}
		}

		// Bmmh5 - B5 Structure

		Bmmh5Table b5Table = new Bmmh5Table();
		Bmmh5TableRow b5Row = b5Table.createEmptyRow();

		b5Row.setSpras("E");

		if (newFlag.equals("NEW")) {
			b5Row.setMaktx("Machine Type " + typeModel.getType() + " - NEW");
		} else if (newFlag.equals("UPG")) {
			b5Row.setMaktx("Machine Type " + typeModel.getType() + " - UPG");
		} else if (newFlag.equals("MTC") && FromToType.equals("MTCTOTYPE")) {
			b5Row.setMaktx("Machine Type " + tmUPGObj.getType() + " - MTC");
		} else if (newFlag.equals("MTC") && FromToType.equals("MTCFROMTYPE")) {
			b5Row.setMaktx("Machine Type " + tmUPGObj.getFromType() + " - MTC");
		}

		b5Table.appendRow(b5Row);
		rfc.setIBmmh5(b5Table);

		rfcInfo.append("BMMH5 \n");
		rfcInfo.append(Tab + "SPRAS>>" + b5Row.getSpras() + ", MAKTX>>"
				+ b5Row.getMaktx() + "\n");

		Zdm_geo_to_classTable zdmTable = new Zdm_geo_to_classTable();
		Zdm_geo_to_classTableRow zdmRow = zdmTable.createEmptyRow();

		zdmRow.setZGeo("US");

		zdmTable.appendRow(zdmRow);

		rfc.setGeoData(zdmTable);

		rfcInfo.append("ZDM_GEO_TO_CLASS \n");
		rfcInfo.append(Tab + "GEO>>" + zdmRow.getZGeo() + "\n");

		//if (includeHWPIMSIndicator()) {
			// rfc.setPimsIdentity("C");
		rfc.setPimsIdentity(pimsIdentity);
		rfcInfo.append("PIMSIdentity \n");
		rfcInfo.append(Tab + "PIMSIdentity>>" + pimsIdentity + "\n");
		//}

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
		return "Create type material basic view";
	}
	public void evaluate() throws Exception { 
		execute() ; 
	}
}