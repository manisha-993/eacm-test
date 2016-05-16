package com.ibm.rdh.chw.caller;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.rdh.chw.entity.CHWAnnouncement;
import com.ibm.rdh.chw.entity.TypeModel;
import com.ibm.rdh.rfc.RmclmTable;
import com.ibm.rdh.rfc.RmclmTableRow;
import com.ibm.rdh.rfc.Zdm_geo_to_classTable;
import com.ibm.rdh.rfc.Zdm_geo_to_classTableRow;

public class R160assignChartoClassFEAT_0000 extends Rfc {
	private com.ibm.rdh.rfc.Z_DM_SAP_ASSIGN_CHAR_TO_CLASS rfc;

	public R160assignChartoClassFEAT_0000(TypeModel typeModel,
			CHWAnnouncement chwA, String pimsIdentity) throws Exception {

		reInitialize();
		rfc = new com.ibm.rdh.rfc.Z_DM_SAP_ASSIGN_CHAR_TO_CLASS();

		// Set up the RFC fields
		// C0
		rfc.setJClass("MK_" + typeModel.getType() + "_FEAT_0000");
		rfc.setJKlart("300");

		rfcInfo.append("Direct fields \n");
		rfcInfo.append(Tab + "CLASS>>" + rfc.getJClass() 
				+ ", KLART>>" + rfc.getJKlart() + "\n");

		// RMCLM - C1
		RmclmTable c1Table = new RmclmTable();
		RmclmTableRow c1Row = c1Table.createEmptyRow();
		int count;
		for (count = 1; count <= 2; count++) {
			if (count == 1) {
				c1Row.setMerkma("MK_SUBLINE");
			} else if (count == 2) {
				c1Row.setMerkma("MK_RPQ_APPROVAL");
			}

			c1Table.appendRow(c1Row);
			rfc.setJMultichar(c1Table);

			rfcInfo.append("RMCLM \n");
			rfcInfo.append(Tab + "MERKMA>>" + c1Row.getMerkma() + "\n");
		}
		
		// ZDM_GEO_TO_CLASS
		Zdm_geo_to_classTable zdmTable = new Zdm_geo_to_classTable();
		Zdm_geo_to_classTableRow zdmRow = zdmTable.createEmptyRow();

		zdmRow.setZGeo("US");
		zdmTable.appendRow(zdmRow);
		rfc.setGeoData(zdmTable);

		rfcInfo.append("ZDM_GEO_TO_CLASS \n");
		rfcInfo.append(Tab + "GEO>>" + zdmRow.getZGeo() + "\n");
	
		// PIMSIdentity
		rfc.setPimsIdentity(pimsIdentity);
		rfcInfo.append("PIMSIdentity \n");
		rfcInfo.append(Tab + "PIMSIdentity>>" + pimsIdentity + "\n");
		
		// RFANUMBER
		rfc.setRfaNum(chwA.getAnnDocNo());
		rfcInfo.append("RFANUM \n");
		rfcInfo.append(Tab + ",RFANUM>>" + chwA.getAnnDocNo() + "\n");

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

	public com.ibm.rdh.rfc.Z_DM_SAP_ASSIGN_CHAR_TO_CLASS getRfc() {
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
		return "Assign Char to Class FEAT_0000";
	}

	@Override
	public String getRfcName() {
		return ClassUtil.getSimpleClassName(getRfc());
	}

	public void evaluate() throws Exception {
		execute();
	}

}
