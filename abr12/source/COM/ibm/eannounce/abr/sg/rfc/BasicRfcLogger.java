package COM.ibm.eannounce.abr.sg.rfc;

import com.ibm.pprds.epimshw.util.RfcLogger;

public class BasicRfcLogger implements RfcLogger {

	private RFCABRSTATUS abr;

	public BasicRfcLogger(RFCABRSTATUS abr) {
		this.abr = abr;
	}

	@Override
	public void info(String msg) {
		abr.addDebug(msg);
	}

	@Override
	public void warn(String msg) {
		abr.addDebug(msg);
	}

	@Override
	public void error(String msg) {
		abr.addDebug(msg);
	}
	
}
