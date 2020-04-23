package COM.ibm.eannounce.abr.sg.translation;

import java.util.Date;
import java.util.Map;

public class LSEO_AddTranslationProcess extends AddTranslationProcess {

	public boolean isValid(EntityHandler entityHandler, Date targetDate)
			throws Exception {
		Map attributes = entityHandler.getAttributes(
				new String[] { "STATUS" , "LSEOPUBDATEMTRGT", "LSEOUNPUBDATEMTRGT" });
		
		
		if (!isStatusFinal(attributes, "STATUS")) {
			return false;
		}
		if (!isAfter(attributes, "LSEOPUBDATEMTRGT", targetDate))
			return false;

		if (!isBefore(attributes, "LSEOUNPUBDATEMTRGT", targetDate))
			return false;

		return true;
	}

}
