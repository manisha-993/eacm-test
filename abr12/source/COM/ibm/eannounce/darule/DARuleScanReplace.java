//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2011  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.darule;

import java.util.Vector;

import COM.ibm.eannounce.abr.util.StringUtils;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.Profile;

import com.ibm.transform.oim.eacm.util.PokUtils;

/*********************************
 *DARULETYPE  30  ScanReplace
 *3.	ScanReplace
 * This scans a derived attribute for a text sting and replaces that string with a different string. 
 * The text string being looked for is specified in “Pass Results” (RULEPASS). The replacement text 
 * string is specified in “Fail Results” (RULEFAIL). RULETEST is ignored.
 *  
 */
// $Log: DARuleScanReplace.java,v $
// Revision 1.8  2011/07/06 14:04:33  lucasrg
// Now the rule bypasses when the input from the previous rule in the sequence is null, not generating derived data for that entity.
//
// Revision 1.7  2011/04/28 13:04:30  lucasrg
// Throwing InvalidDARuleException instead of Exception now
//
// Revision 1.6  2011/04/20 16:03:22  lucasrg
// Fixed the RULEPASS/RULEFAIL validation
//
// Revision 1.5  2011/04/20 13:53:55  lucasrg
// Added DARule attributes validation
//
// Revision 1.4  2011/04/07 21:29:23  lucasrg
// Rule now throws an exception when the input value is null
//
// Revision 1.3  2011/03/25 14:46:32  lucasrg
// Java 1.3 compatibility
//
// Revision 1.2  2011/03/23 13:54:12  lucasrg
// Rule implemented
//
// Revision 1.1  2011/03/15 21:12:10  wendy
// Init for BH FS ABR Catalog Attr Derivation 20110221b.doc
//
public class DARuleScanReplace extends DARuleItem {
	private static final long serialVersionUID = 1L;

	/**
	 * constructor
	 * @param ei
	 */
	protected DARuleScanReplace(EntityItem ei) {
		super(ei);
	}

	/* (non-Javadoc)
	 *  used for offering DQ and Workflow 
	 * @see COM.ibm.eannounce.darule.DARuleItem#getDerivedValue(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.Profile, java.lang.String, java.lang.StringBuffer)
	 */
	protected String getDerivedValue(Database db, Profile prof,
			EntityItem rootItem, String interimValue, StringBuffer debugSb)
			throws Exception {
		String result = interimValue;
		//The root entity type PDHDOMAIN is in DARULE.PDHDOMAIN
		if (isApplicable(rootItem, debugSb)) {
			// process this rule
			String rulePass = PokUtils.getAttributeValue(getDARULEEntity(),
					"RULEPASS",null,null,false);
			String ruleFail = PokUtils.getAttributeValue(getDARULEEntity(),
					"RULEFAIL",null,null,false);
			if (interimValue != null) {
				/* Only derive if it is not null because
				 * the previous rule could result null and
				 * the derivation should not be created in this case */
				result = derive(rootItem, rulePass, ruleFail, interimValue, debugSb);
			} else {
				result = null;
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * used for IDL to improve performance
	 * @see COM.ibm.eannounce.darule.DARuleItem#getDerivedValues(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.Profile, COM.ibm.eannounce.objects.EntityItem[], java.lang.String[], java.lang.StringBuffer)
	 */
	protected String[] getDerivedValues(Database db, Profile prof,
			EntityItem[] rootItemArray, String[] interimValues,
			StringBuffer debugSb) throws Exception {
		String results[] = new String[rootItemArray.length];
		String rulePass = PokUtils.getAttributeValue(getDARULEEntity(),
				"RULEPASS",null,null,false);
		String ruleFail = PokUtils.getAttributeValue(getDARULEEntity(),
				"RULEFAIL",null,null,false);
		for (int i = 0; i < rootItemArray.length; i++) {
			EntityItem rootItem = rootItemArray[i];
			//The root entity type PDHDOMAIN is in DARULE.PDHDOMAIN
			if (isApplicable(rootItem, debugSb)) {
				// process this rule
				if (interimValues[i] != null) {
					/* Only derive if it is not null because
					 * the previous rule could result null and
					 * the derivation should not be created in this case */
					results[i] = derive(rootItem, rulePass, ruleFail, interimValues[i], debugSb);
				} else {
					results[i] = null;	
				}
			} else {
				if (interimValues != null) {
					results[i] = interimValues[i];
				} else {
					results[i] = null;
				}
			}
		}
		return results;
	}

	/**
	 * Simple derivation, using Java String.replaceAll
	 * @param rulePass String to match
	 * @param ruleFail Replacement value
	 * @param input Input value
	 * @return Derived value
	 * @throws Exception 
	 */
	private String derive(EntityItem rootItem, String rulePass,
			String ruleFail, String input, StringBuffer debugSb) throws InvalidDARuleException {
		String errors = "DARuleScanReplace errors:";
		boolean hasErrors = false;
		if (rulePass == null) {
			errors += "<br>\nRule pass (RULEPASS) cannot be null for Scan Replace Rule";
			hasErrors = true;
		}
		if (ruleFail == null) {
			errors += "<br>\nRule fail (RULEFAIL) cannot be null for Scan Replace Rule";
			hasErrors = true;
		}
		if (hasErrors) {
			Vector vct = new Vector();
			vct.add(getDARULEEntity());
			throw new InvalidDARuleException(errors, vct);
		}
		return StringUtils.replace(input, rulePass, ruleFail);
	}

	/***********************************************
	 *  Get the version
	 *
	 *@return java.lang.String
	 */
	public static String getVersion() {
		return "$Revision: 1.8 $";
	}
}
