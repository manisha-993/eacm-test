package COM.ibm.eannounce.abr.sg.rfc;

import java.sql.SQLException;

import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;


public class RfcAbrFactory {

	private static final String MODEL_ENTITYTYPE = "MODEL";
	
	private RFCABRSTATUS rfcAbrStatus;
	
	public RfcAbr getRfcTypeAbr(String entityType) throws RfcAbrException, MiddlewareRequestException, SQLException, MiddlewareException {
		if(rfcAbrStatus == null) {
			throw new RfcAbrException("Did not set RFCABRSTATUS instance to RfcAbrFactory");
		}
		if(MODEL_ENTITYTYPE.equals(entityType)) {
			return new RFCMODELABR(rfcAbrStatus);
		}
		throw new RfcAbrException("Can not get instance for entity type:" + entityType);
	}

	public void setRfcAbrStatus(RFCABRSTATUS rfcAbrStatus) {
		this.rfcAbrStatus = rfcAbrStatus;
	}
	
}
