package COM.ibm.eannounce.abr.sg.rfc;

import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;

import COM.ibm.eannounce.objects.EANBusinessRuleException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;


public class RfcAbrFactory {
	
	private RFCABRSTATUS rfcAbrStatus;
	
	public RfcAbr getRfcTypeAbr(String entityType) throws RfcAbrException, MiddlewareRequestException, SQLException, MiddlewareException, RemoteException, EANBusinessRuleException, IOException, MiddlewareShutdownInProgressException {
		if (rfcAbrStatus == null) {
			throw new RfcAbrException("Did not set RFCABRSTATUS instance to RfcAbrFactory");
		} else if("MODEL".equals(entityType)) {
			return new RFCMODELABR(rfcAbrStatus);
		} else if("PRODSTRUCT".equals(entityType)) {
			return new RFCPRODSTRUCTABR(rfcAbrStatus);
		} else if("FCTRANSACTION".equals(entityType)) {
			return new RFCFCTRANSACTIONABR(rfcAbrStatus);
		} else if("MODELCONVERT".equals(entityType)) {
			return new RFCMODELCONVERTABR(rfcAbrStatus);
		} else if("AUOMTRL".equals(entityType)) {
			return new RFCAUOMTRLABR(rfcAbrStatus);
		} else if ("SWPRODSTRUCT".equals(entityType)) {
			return new RFCSWPRODSTRUCTABR(rfcAbrStatus);
		} else {
			throw new RfcAbrException("Can not get instance for entity type:" + entityType);
		}
	}

	public void setRfcAbrStatus(RFCABRSTATUS rfcAbrStatus) {
		this.rfcAbrStatus = rfcAbrStatus;
	}
	
}
