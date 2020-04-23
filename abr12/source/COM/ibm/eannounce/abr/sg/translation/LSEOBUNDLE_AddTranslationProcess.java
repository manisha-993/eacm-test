package COM.ibm.eannounce.abr.sg.translation;

import java.util.Date;
import java.util.Map;


public class LSEOBUNDLE_AddTranslationProcess extends AddTranslationProcess {

	public boolean isValid(EntityHandler entityHandler, Date targetDate) throws Exception {
		Map attributes = entityHandler.getAttributes(
				new String[] { "STATUS" , "BUNDLPUBDATEMTRGT", "BUNDLUNPUBDATEMTRGT" });
		
		if (!isStatusFinal(attributes, "STATUS")) {
			return false;
		}
		if (!isAfter(attributes, "BUNDLPUBDATEMTRGT", targetDate))
			return false;

		if (!isBefore(attributes, "BUNDLUNPUBDATEMTRGT", targetDate))
			return false;
		
		return true;
	}

}
