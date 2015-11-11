package com.ibm.rdh.chw.caller;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.PropertyKeys;
import com.ibm.pprds.epimshw.util.ConfigManager;
import com.ibm.pprds.epimshw.util.ProfitCenterPlantSelector;
import com.ibm.rdh.chw.entity.CHWAnnouncement;
import com.ibm.rdh.chw.entity.TypeModel;
import com.ibm.rdh.chw.entity.TypeModelUPGGeo;
import com.ibm.rdh.rfc.Bmm00Table;
import com.ibm.rdh.rfc.Bmm00TableRow;
import com.ibm.rdh.rfc.Bmmh1Table;
import com.ibm.rdh.rfc.Bmmh1TableRow;
import com.ibm.rdh.rfc.Zdm_geo_to_classTable;
import com.ibm.rdh.rfc.Zdm_geo_to_classTableRow;

public class R189createCFIPlantViewForType extends Rfc {
	private com.ibm.rdh.rfc.Z_DM_SAP_MATM_CREATE rfc;

	public R189createCFIPlantViewForType(CHWAnnouncement chwA,
			TypeModel typeModel, String sapPlant, String newFlag,
			TypeModelUPGGeo tmUPGObj, String FromToType, String pimsIdentity)
			throws Exception {

		reInitialize();

		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");

		rfc = new com.ibm.rdh.rfc.Z_DM_SAP_MATM_CREATE();

		Bmm00Table b0Table = new Bmm00Table();
		Bmm00TableRow b0Row = b0Table.createEmptyRow();
		b0Row.setTcode("MM01");

		if ("New".equals(newFlag)) {
			b0Row.setMatnr(typeModel.getType() + "New");
		} else if ("UPG".equals(newFlag)) {
			b0Row.setMatnr(typeModel.getType() + "UPG");
		} else if ("MTC".equals(newFlag) && "MTCTOTYPE".equals(FromToType)) {
			b0Row.setMatnr(tmUPGObj.getType() + "MTC");
		} else if ("MTC".equals(newFlag) && "MTCFROMTYPE".equals(FromToType)) {

			b0Row.setMatnr(tmUPGObj.getFromType() + "MTC");
		}

		b0Row.setXeiv4("X");
		b0Row.setWerks(sapPlant);
		b0Row.setMbrsh("M");
		b0Row.setMtart("ZPRT");

		b0Table.appendRow(b0Row);
		rfc.setIBmm00(b0Table);
		rfcInfo.append("IBmm00 \n");
		rfcInfo.append(Tab  + "TCODE" + b0Row.getTcode() + 
				", MATNR>>" + b0Row.getMatnr() + 
				", XEIV4>>" + b0Row.getXeiv4() + 
				", WERKS>>" + b0Row.getWerks() + 
				", MBRSH>>" + b0Row.getMbrsh() + 
				", MTART>>" + b0Row.getMtart() + "\n");

		Bmmh1Table b1Table = new Bmmh1Table();
		Bmmh1TableRow b1Row = b1Table.createEmptyRow();

		b1Row.setGewei("KG");
		b1Row.setSpart(typeModel.getDiv());

		b1Row.setLadgr("B001");

		if ("1999".equals(sapPlant))
			b1Row.setMtvfp("NC");
		else
			b1Row.setMtvfp("ZE");

		// SAP Ledger

		if ("Y".equals(ConfigManager.getConfigManager().getString(PropertyKeys.KEY_SAP_LEDGER))) {
			boolean existsPro = ProfitCenterPlantSelector.checkProfitCenterPlants(sapPlant);
			if (existsPro) {
				if (typeModel.getProfitCenter() != null
						|| (!typeModel.getProfitCenter().equals(""))) {
					b1Row.setPrctr(typeModel.getProfitCenter());
				}
			}
		}

		b1Row.setMatkl("000");
		b1Row.setMeins("EA");
		b1Row.setZeinr(chwA.getAnnDocNo());
		b1Row.setZeiar(chwA.getAnnouncementType());
		b1Row.setAeszn(sdf.format(new Date()));

		b1Table.appendRow(b1Row);
		rfc.setIBmmh1(b1Table);

		rfcInfo.append("BMMH1 \n");
		rfcInfo.append(Tab + "LADGR>>" + b1Row.getLadgr() + ", MTVFP>>"
				+ b1Row.getMtvfp() + ", PRCTR>>" + b1Row.getPrctr()
				+ ", MATKL>>" + b1Row.getMatkl() + ", MEINS>>"
				+ b1Row.getMeins() + ", ZEINR>>" + b1Row.getZeinr()
				+ ", ZEIAR>>" + b1Row.getZeiar() + ", AESZN>>"
				+ b1Row.getAeszn() + "\n");

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

		rfc.setRfaNum(chwA.getAnnDocNo());
		rfcInfo.append("RFANUM \n");
		rfcInfo.append(Tab + ",RFANUM>>" + chwA.getAnnDocNo() + "\n");
	}

	@Override
	public void execute() throws Exception {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRfcName() {
		return ClassUtil.getSimpleClassName(getRfc());
	}

	public void evaluate() throws Exception {

		execute();
	}

}
