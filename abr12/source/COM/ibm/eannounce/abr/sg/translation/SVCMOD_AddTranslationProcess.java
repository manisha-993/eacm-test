package COM.ibm.eannounce.abr.sg.translation;

import java.util.Date;
import java.util.Map;


public class SVCMOD_AddTranslationProcess extends AddTranslationProcess {

	public boolean isValid(EntityHandler entityHandler, Date targetDate) throws Exception {
		Map attributes = entityHandler.getAttributes(
				new String[] { "STATUS" , "ANNDATE", "WTHDRWEFFCTVDATE" });
		
		if (!isStatusFinal(attributes, "STATUS")) {
			return false;
		}

		if (!isAfter(attributes, "ANNDATE", targetDate))
			return false;

		if (!isBefore(attributes, "WTHDRWEFFCTVDATE", targetDate))
			return false;		
		
		return true;
	}

}
