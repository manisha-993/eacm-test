// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2007, 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg.translation;

import java.util.Date;
import java.util.Map;

public class CATNAV_AddTranslationProcess extends AddTranslationProcess {

	public boolean isValid(EntityHandler entityHandler, Date targetDate)
			throws Exception {
		Map attributes = entityHandler
				.getAttributes(new String[] { "CATPUBLISH" });
		if ("Yes".equals(attributes.get("CATPUBLISH"))) {
			return true;
		}
		return false;
	}

}
