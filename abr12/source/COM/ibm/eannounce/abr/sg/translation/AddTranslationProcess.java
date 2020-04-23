// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2007, 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg.translation;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import COM.ibm.opicmpdh.middleware.MiddlewareException;

/**
 * Base class for processing added translation languages
 * 
 * @author lucasrg
 * 
 */
public abstract class AddTranslationProcess {

	static final String STATUS_FINAL = "0020";

	private static final DateFormat DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd");

	public abstract boolean isValid(EntityHandler entityHandler, Date targetDate)
			throws Exception;

	public void createRelators(final RelatorHandler relatorHandler,
			EntityHandler currentEntity) throws Exception {
		relatorHandler.addChild(currentEntity.getEntityType(),
				currentEntity.getEntityID());
	}

	private Date parseDate(Object value) {
		try {
			return DATE_FORMAT.parse((String) value);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Unable to parse date: " + value);
		}
	}

	protected boolean isStatusFinal(Map attributes, String attributeCode)
			throws MiddlewareException, SQLException {
		return STATUS_FINAL.equals(attributes.get(attributeCode));
	}

	protected boolean isAfter(Map attributes, String attributeCode,
			Date targetDate) throws MiddlewareException, SQLException {
		String value = (String) attributes.get(attributeCode);
		if (value == null)
			throw new IllegalArgumentException(attributeCode+" cannot be null");
		Date date = parseDate(value);
		return targetDate.after(date);
	}

	protected boolean isBefore(Map attributes, String attributeCode,
			Date targetDate) throws MiddlewareException, SQLException {
		String value = (String) attributes.get(attributeCode);
		if (value == null)
			return true; //If there is no "end date", it is valid
		Date date = parseDate(value);
		return targetDate.before(date);
	}

}