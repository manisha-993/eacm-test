package COM.ibm.eannounce.abr.sg.translation;

import java.util.Date;
import java.util.Map;


public class MM_AddTranslationProcess extends AddTranslationProcess {

	public boolean isValid(EntityHandler entityHandler, Date targetDate) throws Exception {
		Map attributes = entityHandler.getAttributes(
				new String[] { "MMSTATUS" , "PUBFROM", "PUBTO" });
		
		if (!isStatusFinal(attributes, "MMSTATUS")) {
			return false;
		}
		
		if (!isAfter(attributes, "PUBFROM", targetDate))
			return false;

		if (!isBefore(attributes, "PUBTO", targetDate))
			return false;
		
		return true;
	}

}
