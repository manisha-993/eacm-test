package COM.ibm.eannounce.darule.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import COM.ibm.eannounce.abr.util.StringUtils;

//$Log: AttributeRuleParser.java,v $
//Revision 1.7  2011/06/21 17:47:20  lucasrg
//Always store the rule definition in 'nextRule.setKey()' to use in reports too
//
//Revision 1.6  2011/05/19 16:21:11  lucasrg
//Better exception message
//
//Revision 1.5  2011/04/28 19:51:27  lucasrg
//Fixed rule path logic
//Added better logging
//
//Revision 1.4  2011/04/07 21:27:57  lucasrg
//Applied fixes after testing
//
//Revision 1.3  2011/03/29 17:26:02  lucasrg
//Added trim() to bypass errors in rules with white space
//
//Revision 1.2  2011/03/25 14:46:50  lucasrg
//Java 1.3 compatibility
//
//Revision 1.1  2011/03/23 13:53:51  lucasrg
//Initial commit
//

/**
 * Parse rule attributes. Syntax:<br>
 * Syntax: [RelatorType-{u|d}: [RelatorType-{u|d}: ] ... ]EntityType.AttributeCode<br>
 * Example: WWSEOPRODSTRUCT-d:PRODSTRUCT-u:FEATUREPLANAR-d:PLANAR.MEMRYRAMMAX
 * 
 * The result is a map of [~RULE~ , {@link AttributeRule}]
 * 
 * Not serializable because it must be transient
 * 
 * @author lucasrg
 */
public class AttributeRuleParser {

	private static final int STATE_READING = 0;
	private static final int STATE_OPEN = 1;
	private static final char DELIMITER = '~';

	/**
	 * Create a list of AttributeRules
	 * @throws Exception 
	 */
	public List parse(String value) throws Exception {
		List attributes = new ArrayList();

		int state = STATE_READING;
		String attributeValue = "";
		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);
			if (c == DELIMITER) {
				if (state == STATE_READING) {
					attributeValue = "";
					state = STATE_OPEN;
				} else if (state == STATE_OPEN) {
					attributes
							.add(parseAttributeRule(DELIMITER + attributeValue
									+ DELIMITER, attributeValue.trim()));
					state = STATE_READING;
				}
			} else if (state == STATE_OPEN) {
				attributeValue += c;
			}
		}

		return attributes;
	}

	private AttributeRule parseAttributeRule(String keyValue, String input) throws Exception {
		//Syntax: [RelatorType-{u|d}: [RelatorType-{u|d}: ] ... ]EntityType.AttributeCode
		//Example: WWSEOPRODSTRUCT-d:PRODSTRUCT-u:FEATUREPLANAR-d:PLANAR.MEMRYRAMMAX
		AttributeRule firstRule = null;
		AttributeRule rule = null;
		StringTokenizer stringTokenizer = new StringTokenizer(input, ":");
		while (stringTokenizer.hasMoreTokens()) {
			String step = stringTokenizer.nextToken().trim();
			AttributeRule nextRule = null;
			if (step.indexOf("-d") > 0) {
				//Is down relator
				String relatorName = StringUtils.replace(step, "-d", "");
				nextRule = new RelatorAttributeRule(relatorName,
						AttributeRuleContext.DOWN);
			} else if (step.indexOf("-u") > 0) {
				//Is up relator
				String relatorName = StringUtils.replace(step, "-u", "");
				nextRule = new RelatorAttributeRule(relatorName,
						AttributeRuleContext.UP);
			} else if (step.indexOf(".") > 0) {
				//Is ENTITY.ATTRIBUTE
				StringTokenizer st = new StringTokenizer(step, ".");
				nextRule = new EntityAttributeRule(st.nextToken().trim(), st
						.nextToken().trim());
			}

			if (nextRule == null) {
				throw new Exception("Inavlid step: "+step);
			}
			
			//Store the rule definition as key to replace later
			nextRule.setKey(keyValue);

			if (firstRule == null) {
				firstRule = nextRule;
			}
			if (rule != null) {
				rule.setNextRule(nextRule);
			}
			rule = nextRule;

		}
		return firstRule;
	}

}
