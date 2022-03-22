package COM.ibm.eannounce.abr.sg.rfc;

import COM.ibm.opicmpdh.middleware.D;

public class RfcCallerBase {
	public StringBuffer rptSb = new StringBuffer();
	private static final char[] FOOL_JTEST = { '\n' };
	private int abr_debuglvl = D.EBUG_ERR;
	static final String NEWLINE = new String(FOOL_JTEST);
	private boolean isdebug = true;
	public StringBuffer getRptSb() {
		return rptSb;
	}	
	
	protected void addDebug(String msg) {
		if (D.EBUG_DETAIL <= abr_debuglvl || isdebug) {
		//if (D.EBUG_DETAIL <= abr_debuglvl) {
			rptSb.append("<!-- " + msg + " -->" + NEWLINE);
		}
	}
	
	protected void addOutput(String msg) { 
		rptSb.append("<p>"+msg+"</p>"+NEWLINE);
	}
	
	protected void addMsg(StringBuffer msg) { 
		rptSb.append(msg.toString()+NEWLINE);
	}
	
	
	protected void addRfcName(RdhBase rdhBase){
		this.addDebug("Calling " + rdhBase.getRFCName());
	}
	
	protected void addRfcResult(RdhBase rdhBase) {
		this.addDebug(rdhBase.createLogEntry());
		if (rdhBase.getRfcrc() == 0) {
			this.addOutput(rdhBase.getRFCName() + " called successfully!");
		} else {
			this.addOutput(rdhBase.getRFCName() + " called  faild!");
			this.addOutput("return code is " + rdhBase.getRfcrc());
			this.addOutput(rdhBase.getError_text());
		}
	}
	

}
