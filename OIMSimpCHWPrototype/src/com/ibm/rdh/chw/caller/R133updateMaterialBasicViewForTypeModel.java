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
import com.ibm.rdh.rfc.Bmmh5Table;
import com.ibm.rdh.rfc.Bmmh5TableRow;
import com.ibm.rdh.rfc.Zdm_geo_to_classTable;
import com.ibm.rdh.rfc.Zdm_geo_to_classTableRow;

public class R133updateMaterialBasicViewForTypeModel extends Rfc {
	private com.ibm.rdh.rfc.Z_DM_SAP_MATM_CREATE rfc;

	public R133updateMaterialBasicViewForTypeModel(TypeModel typeModel,
			CHWAnnouncement chwA, String pimsIdentity, CHWGeoAnn chwAg)
			throws Exception {
		reInitialize();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
		rfcName = "Z_DM_SAP_MATM_CREATE";
		rfc = new com.ibm.rdh.rfc.Z_DM_SAP_MATM_CREATE();

		// Set Up the RFC Fields
		// Bmm00 - B0 Structure

		Bmm00Table b0Table = new Bmm00Table();
		Bmm00TableRow b0Row = b0Table.createEmptyRow();
		b0Row.setTcode("MM02");
		b0Row.setMatnr(typeModel.getType() + typeModel.getModel());

		// Two commented out values in the epims code, but we need it.
		b0Row.setMbrsh("M");
		// [Work Item 1681833] New: HIPO materials (5313 HPO and 5372 IS5)are still set to material type of ZPRT in RDH. Should be ZMAT
		if (isHIPOModel(typeModel.getType(), typeModel.getModel())) {
			b0Row.setMtart("ZMAT");
		} else {
			b0Row.setMtart("ZPRT");
		}
		// End

		b0Table.appendRow(b0Row);
		rfc.setIBmm00(b0Table);
		rfcInfo.append("B0 \n");
		rfcInfo.append(Tab + "TCODE>>" + b0Row.getTcode() + ", MATNR>>"
				+ b0Row.getMatnr() + ", MBRSH>>" + b0Row.getMbrsh()
				+ ", MTART>>" + b0Row.getMtart() + "\n");

		// Bmmh1 - B1
		Bmmh1Table b1Table = new Bmmh1Table();
		Bmmh1TableRow b1Row = b1Table.createEmptyRow();

		b1Row.setSpart(typeModel.getDiv());
		if ((typeModel.getEanUPCCode()) != null
				&& !"".equals(typeModel.getEanUPCCode())) {
			String upccd = typeModel.getEanUPCCode();
			if (upccd.length() == 12 && upccd.charAt(0) == '0') {
				b1Row.setEan11(upccd.substring(1, upccd.length()));
			} else {
				b1Row.setEan11(upccd);
			}
			b1Row.setNumtp("UC");
		}

		if ((typeModel.getProductHierarchy()) != null) {
			b1Row.setPrdha(typeModel.getProductHierarchy());
		}
		// New add data start
		b1Row.setZeinr(chwA.getAnnDocNo());
		b1Row.setMatkl("000");
		b1Row.setMeins("EA");
		b1Row.setZeiar(chwA.getAnnouncementType());
		b1Row.setAeszn(sdf.format(chwAg.getAnnouncementDate()));
		b1Row.setGewei("KG");
		// New add data end

		b1Table.appendRow(b1Row);
		rfc.setIBmmh1(b1Table);
		rfcInfo.append("B1 \n");
		rfcInfo.append(Tab + ", SPART>>" + b1Row.getSpart() + ", EAN11>>"
				+ b1Row.getEan11() + ", NUMTP>>" + b1Row.getNumtp()
				+ ", PRDHA>>" + b1Row.getPrdha() + ", ZEINR>>"
				+ b1Row.getZeinr() + ", MATKL>>" + b1Row.getMatkl()
				+ ", MEINS>>" + b1Row.getMeins() + ", ZEIAR>>"
				+ b1Row.getZeiar() + ", AESZN>>" + b1Row.getAeszn()
				+ ", GEWEI>>" + b1Row.getGewei() + "\n");

		// Bmmh5 - B5 Structure
		Bmmh5Table b5Table = new Bmmh5Table();
		Bmmh5TableRow b5Row = b5Table.createEmptyRow();

		b5Row.setSpras("E");
		b5Row.setMaktx(typeModel.getDescription());
		b5Table.appendRow(b5Row);
		rfc.setIBmmh5(b5Table);

		rfcInfo.append("B5 \n");
		rfcInfo.append(Tab + "SPRAS>>" + b5Row.getSpras() + ", MAKTX>>"
				+ b5Row.getMaktx() + "\n");

		// ZDM_GEO_TO_CLASS
		Zdm_geo_to_classTable zdmTable = new Zdm_geo_to_classTable();
		Zdm_geo_to_classTableRow zdmRow = zdmTable.createEmptyRow();

		zdmRow.setZGeo("US");
		zdmTable.appendRow(zdmRow);
		rfc.setGeoData(zdmTable);
		rfcInfo.append("ZDM_GEO_TO_CLASS \n");
		rfcInfo.append(Tab + "GEO>>" + zdmRow.getZGeo() + "\n");

		// PIMS_IDENTITY
		rfc.setPimsIdentity("C");
		rfcInfo.append("PIMSIdentity \n");
		rfcInfo.append(Tab + "PIMSIdentity>>" + "C" + "\n");

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
		return "Update Material basic view for Type/Model";
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
