package COM.ibm.eannounce.abr.util;

import java.util.List;

//$Log: StringUtils.java,v $
//Revision 1.2  2011/03/29 17:21:41  lucasrg
//Added concatenate function
//
//Revision 1.1  2011/03/23 13:52:29  lucasrg
//Initial commit
//

/**
 * Utility class to add replace functionality to old java version.
 * @author lucasrg
 *
 */
public class StringUtils {

	private StringUtils() {
	}

	/**
	 * Replace every match of oldPattern with newPattern in the input String
	 * @param input
	 * @param oldPattern
	 * @param newPattern
	 * @return replaced string
	 */
	public static String replace(final String input,
			final String oldPattern, final String newPattern) {
		if (oldPattern.equals("")) {
			throw new IllegalArgumentException("Old pattern must have content.");
		}
		final StringBuffer result = new StringBuffer();
		int startIndex = 0;
		int indexOld = 0;
		while ((indexOld = input.indexOf(oldPattern, startIndex)) >= 0) {
			result.append(input.substring(startIndex, indexOld));
			result.append(newPattern);
			startIndex = indexOld + oldPattern.length();
		}
		result.append(input.substring(startIndex));
		return result.toString();
	}
	
	/**
	 * Concatenate a list of strings using the separator
	 * 
	 * @param strings List of String
	 * @param separator
	 * @return Concatenated string
	 */
	public static String concatenate(List strings, String separator) {
		StringBuffer result = new StringBuffer();
		boolean first = true;
		for (int i = 0; i < strings.size(); i++) {
			if (first) {
				first = false;
			} else {
				result.append(separator);
			}
			result.append(strings.get(i));
		}
		return result.toString();
	}
}
