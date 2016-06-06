package com.ibm.rdh.chw.caller;

//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
//import com.ibm.pprds.epimshw.PropertyKeys;
//import com.ibm.pprds.epimshw.util.ConfigManager;
//import com.ibm.rdh.rfc.Zdm_mat_tabTable;
//import com.ibm.rdh.rfc.Zdm_mat_tabTableRow;
//
//public class R261PlantViewMaterial extends Rfc {
//	private com.ibm.rdh.rfc.Z_DM_SAP_READ_MARC rfc;
//
//	public R261PlantViewMaterial(String material) throws Exception {
//		reInitialize();
//		Date curDate = new Date();
//		rfcName = "Z_DM_SAP_READ_MARC";
//
//		reInitialize();
//		// First check and see if tfc is empty and we do not need to do
//		// anything.
//		String sDateFormat = ConfigManager.getConfigManager().getString(
//				PropertyKeys.KEY_DATE_FORMAT, true);
//		SimpleDateFormat sdf = new SimpleDateFormat(sDateFormat);
//
//		rfc = new com.ibm.rdh.rfc.Z_DM_SAP_READ_MARC();
//
//		Zdm_mat_tabTable r0Table = new Zdm_mat_tabTable();
//		Zdm_mat_tabTableRow r0Row = r0Table.createEmptyRow();
//		r0Row.setMatnr(material);
//		r0Table.appendRow(r0Row);
//		rfc.setZdmMatTab(r0Table);
//		rfcInfo.append("MATNR \n");
//		rfcInfo.append(Tab + "MATNR>>" + r0Row.getMatnr() + "\n");
//
//	}
//
//	@Override
//	public void execute() throws Exception {
//		logExecution();
//		getRfc().execute();
//		getLog().debug(getErrorInformation());
//		if (getSeverity() == ERROR) {
//			throw new HWPIMSAbnormalException(getErrorInformation());
//		}
//	}
//
//	@Override
//	public String getTaskDescription() {
//
//		StringBuffer sb = new StringBuffer();
//		sb.append(" " + getMaterialName());
//		return sb.toString();
//	}
//
//	@Override
//	protected boolean isSuccessful() {
//		boolean ans = false;
//		int rc = getRfc().getRfcrc();
//		if (0 == rc) {
//			ans = true;
//		}
//		return ans;
//	}
//
//	public com.ibm.rdh.rfc.Z_DM_SAP_READ_MARC getRfc() {
//		return rfc;
//	}
//
//	@Override
//	protected String getErrorInformation() {
//		String ans;
//		if (isSuccessful()) {
//			ans = RFC_OK_MESSAGE;
//		} else {
//			ans = formatRfcErrorMessage(getRfc().getRfcrc(), getRfc()
//					.getErrorText());
//		}
//		return ans;
//	}
//
//	@Override
//	protected String getMaterialName() {
//		return "Plant View Material";
//	}
//
//	@Override
//	public String getRfcName() {
//		return ClassUtil.getSimpleClassName(getRfc());
//	}
//
//	public void evaluate() throws Exception {
//		execute();
//	}
//
//}
