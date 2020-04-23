package COM.ibm.eannounce.abr.sg.translation;

import java.util.Date;
import java.util.Map;


public class SWFEATURE_AddTranslationProcess extends AddTranslationProcess {

	public boolean isValid(EntityHandler entityHandler, Date targetDate) throws Exception {
		Map attributes = entityHandler.getAttributes(
				new String[] { "STATUS" , "WITHDRAWDATEEFF_T" });
		
		if (!isStatusFinal(attributes, "STATUS")) {
			return false;
		}

		if (!isBefore(attributes, "WITHDRAWDATEEFF_T", targetDate))
			return false;		
		
		return true;
	}

}
