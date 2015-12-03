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

public class R166createSTPPlantViewForMaterial extends Rfc {

	private com.ibm.rdh.rfc.Z_DM_SAP_MATM_CREATE rfc;

	public R166createSTPPlantViewForMaterial(CHWAnnouncement chwA,
			TypeModel typeModel, CHWGeoAnn chwAg, String storageLocation,
			String newFlag) throws Exception {

		reInitialize();
		String storageLocationValue;
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
		rfcName = "Z_DM_SAP_MATM_CREATE";
		rfc = new com.ibm.rdh.rfc.Z_DM_SAP_MATM_CREATE();
		
		//Set Up the RFC Fields
		//Bmm00 - B0 Structure

		Bmm00Table  b0Table = new Bmm00Table();
		Bmm00TableRow b0Row = b0Table.createEmptyRow();

		b0Row.setTcode("MM01");

		if ("NEW".equals(newFlag)) {
		b0Row.setMatnr(typeModel.getType()+"NEW");
		} else if ("UPG".equals(newFlag)){
			b0Row.setMatnr(typeModel.getType()+"UPG");
		}
		  else if ("MTC".equals(newFlag)){
			b0Row.setMatnr(typeModel.getType()+"MTC");
		}

		b0Row.setMbrsh("M");
		b0Row.setMtart("ZMAT");
		b0Row.setXeiv4("X");

		b0Row.setWerks("1001");

		//b0Row.setLgort(storageLocationValue);

		b0Row.setLgort(storageLocation);

		b0Row.setXeid1("X");
		b0Row.setXeid2("X");

		b0Table.appendRow(b0Row);
		rfc.setIBmm00(b0Table);

		rfcInfo.append("BMM00 \n");
		rfcInfo.append(Tab+"TCODE>>"+b0Row.getTcode()+
			", MATNR>>"+b0Row.getMatnr()+
			", MBRSH>>"+b0Row.getMbrsh()+
			", MTART>>"+b0Row.getMtart()+
			", XEIV4>>"+b0Row.getXeiv4()+
			", WERKS>>"+b0Row.getWerks()+
			", LGORT>>"+b0Row.getLgort()+
			", XEID1>>"+b0Row.getXeid1()+
			", XEID2>>"+b0Row.getXeid2()+
			"\n");


		Bmmh1Table  b1Table = new Bmmh1Table();
		Bmmh1TableRow b1Row = b1Table.createEmptyRow();

		b1Row.setMeins("EA");
		b1Row.setTragr("STD");
		b1Row.setLadgr("RSTP");
	    b1Row.setMtpos("Z002");
		b1Row.setKtgrm("01");
		b1Row.setZeiar(chwA.getAnnouncementType());
		b1Row.setAeszn(sdf.format(chwAg.getAnnouncementDate()));
		b1Row.setGewei("KG");
		b1Row.setXmcng("X");
		b1Row.setSernp("ZCHW");
//
			if ("1".equals(storageLocation.substring(1, 4))) {
				b1Row.setDiskz("");
			} else {
				b1Row.setDiskz("1");
			}
		b1Row.setSbdkz("1");
		b1Row.setMtvfp("ZE");
		b1Row.setDismm("PD");
		b1Row.setDispo("000");
		b1Row.setFhori("000");
		b1Row.setDisls("EX");
		b1Row.setMaabc("A");
		b1Row.setDisgr("Z025");
		b1Row.setPerkz("M");
		
		//add 3 set value, In the previous epimshw code
		b1Row.setMatkl("000");
		b1Row.setZeinr(chwA.getAnnDocNo());
		b1Row.setSpart(typeModel.getDiv());
		//end
		
		b1Table.appendRow(b1Row);
		rfc.setIBmmh1(b1Table);

		rfcInfo.append("BMMH1 \n");
		rfcInfo.append(Tab+"MEINS>>"+b1Row.getMeins()+
			", TRAGR>>"+b1Row.getTragr()+
			", LADGR>>"+b1Row.getLadgr ()+
			", MTPOS>>"+b1Row.getMtpos()+
			", KTGRM>>"+b1Row.getKtgrm()+
			", ZEIAR>>"+b1Row.getZeiar()+
			", AESZN>>"+b1Row.getAeszn()+
			", GEWEI>>"+b1Row.getGewei()+
			", XMCNG>>"+b1Row.getXmcng()+
			", SERNP>>"+b1Row.getSernp()+
			", DISKZ>>"+b1Row.getDiskz()+
			", SBDKZ>>"+b1Row.getSbdkz()+
			", MTVFP>>"+b1Row.getMtvfp()+
			", DISMM>>"+b1Row.getDismm()+
			", DISPO>>"+b1Row.getDispo()+
			", FHORI>>"+b1Row.getFhori()+
			", DISLS>>"+b1Row.getDisls()+
			", MAABC>>"+b1Row.getMaabc()+
			", DISGR>>"+b1Row.getDisgr()+
			", PERKZ>>"+b1Row.getPerkz()+
			"\n");

			Zdm_geo_to_classTable zdmTable = new Zdm_geo_to_classTable();
			Zdm_geo_to_classTableRow zdmRow = zdmTable.createEmptyRow();

			zdmRow.setZGeo("US");

			zdmTable.appendRow(zdmRow);

			rfc.setGeoData(zdmTable);

			rfcInfo.append("ZDM_GEO_TO_CLASS \n");
			rfcInfo.append(Tab+"GEO>>"+zdmRow.getZGeo()+
				"\n");


			
			rfc.setPimsIdentity("C");
			rfcInfo.append("PIMSIdentity \n");
			rfcInfo.append(Tab+"PIMSIdentity>>"+"C"+
				"\n");
		

			//RFANUMBER
			rfc.setRfaNum(chwA.getAnnDocNo());
			rfcInfo.append("RFANUM \n");
			rfcInfo.append(Tab+"RFANumber>>"+chwA.getAnnDocNo()+
				"\n");

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
		return "Create STP Plant 1001 View for material";
	}

	@Override
	public String getRfcName() {
		return ClassUtil.getSimpleClassName(getRfc());
	}

	private com.ibm.rdh.rfc.Z_DM_SAP_MATM_CREATE getRfc() {
		return rfc;
	}

	public void evaluate() throws Exception { 
		execute() ; 
	}
}
