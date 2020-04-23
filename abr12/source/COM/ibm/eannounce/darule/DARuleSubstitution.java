//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2011  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.darule;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import COM.ibm.eannounce.abr.util.StringUtils;
import COM.ibm.eannounce.darule.parser.AttributeRule;
import COM.ibm.eannounce.darule.parser.AttributeRuleContext;
import COM.ibm.eannounce.darule.parser.AttributeRuleParser;
import COM.ibm.eannounce.darule.parser.EntityAttributeRule;
import COM.ibm.eannounce.darule.parser.RelatorAttributeRule;
import COM.ibm.eannounce.objects.EANMetaAttribute;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EntityList;
import COM.ibm.eannounce.objects.ExtractActionItem;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.Profile;

import com.ibm.transform.oim.eacm.util.PokUtils;

/*********************************
 *DARULETYPE  20  Substitution
 *
 *2.	Substitution
 *This takes a text string and substitutes values from identified attributes. The text string is 
 *“Pass Results” (RULEPASS) if the RULETEST is true. If RULETEST is false, then the text string is 
 *“Fail Results” (RULEFAIL).
 * 
 */
// $Log: DARuleSubstitution.java,v $
// Revision 1.18  2011/06/21 17:45:07  lucasrg
// NullPointerException fix to handle invalid VEs, output the error message in the report so it's easier to spot the problem
//
// Revision 1.17  2011/06/09 21:06:37  lucasrg
// Rule now returns null if all attributes are empty
// Less debug logging
// No more info showing the user when no results are found
//
// Revision 1.16  2011/05/20 18:15:25  lucasrg
// Better 'attribute not found' message
//
// Revision 1.15  2011/05/20 15:24:14  lucasrg
// Fixed the way it validates if an entity have an attribute
//
// Revision 1.14  2011/05/19 16:22:46  lucasrg
// Validate the rule before navigating the path
// Throw an exception to stop the IDL immediately if it fails
//
// Revision 1.13  2011/04/28 19:52:49  lucasrg
// Fixed rule path logic, added error info in the report for missing links
//
// Revision 1.12  2011/04/28 13:11:12  lucasrg
// Added debug info for IDL
//
// Revision 1.11  2011/04/28 13:05:25  lucasrg
// Throwing InvalidDARuleException instead of Exception now
// Added more debug info (PokUtils.outputList(entitylist))
//
// Revision 1.10  2011/04/25 21:45:29  lucasrg
// Removed html conversion in PokUtils.getAttributeValue calls
//
// Revision 1.9  2011/04/20 19:09:59  lucasrg
// Allow null concatenation strings
//
// Revision 1.8  2011/04/20 13:55:18  lucasrg
// Added DARule attributes validation
//
// Revision 1.7  2011/04/07 21:29:57  lucasrg
// Applied fixes after testing
//
// Revision 1.6  2011/03/29 17:30:25  lucasrg
// Added support for RULEMULTIPLE
// Update to use the new AttributeRuleParser
//
// Revision 1.5  2011/03/25 14:46:50  lucasrg
// Java 1.3 compatibility
//
// Revision 1.4  2011/03/23 15:02:00  lucasrg
// Fixed the use of entityList.dereference()
//
// Revision 1.3  2011/03/23 14:51:23  lucasrg
// Fixed rootEntity error (changed EANEntity to EntityItem)
//
// Revision 1.2  2011/03/23 13:54:12  lucasrg
// Rule implemented
//
// Revision 1.1  2011/03/15 21:12:10  wendy
// Init for BH FS ABR Catalog Attr Derivation 20110221b.doc
//
public class DARuleSubstitution extends DARuleItem {
	private static final long serialVersionUID = 1L;

	private static transient AttributeRuleParser ruleParser = new AttributeRuleParser();

	private static transient AttributeRuleContext ruleContext = new AttributeRuleContext();

	/**
	 * constructor
	 * @param ei
	 */
	protected DARuleSubstitution(EntityItem ei) {
		super(ei);
	}

	/* (non-Javadoc)
	 * used for offering DQ and Workflow 
	 * @see COM.ibm.eannounce.darule.DARuleItem#getDerivedValue(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.Profile, java.lang.String, java.lang.StringBuffer)
	 */
	protected String getDerivedValue(Database db, Profile prof, EntityItem rootItem,
			String interimValue, StringBuffer debugSb) throws Exception {
		String result = interimValue;
		//The root entity type PDHDOMAIN is in DARULE.PDHDOMAIN
		if (isApplicable(rootItem, debugSb)) {
			// process this rule
			String veName = getDAVEName();
			String rule = getRule();
			String concatString = getRuleConcatenationString();
			DARuleEngineMgr.addDebugComment(D.EBUG_DETAIL, debugSb,
					"DASubstitutionRule - RootItem " + rootItem.getKey() + "\n" + "DAVENAME: "
							+ veName + "\n" + "RULE: " + rule + "\n" + "CONCATSTRING: "
							+ concatString);

			EntityGroup peg;
			EntityItem rootEntity;
			EntityList entityList = null;

			if (veName == null) {
				//No VE, get attribute from root item
				peg = rootItem.getEntityGroup();
				rootEntity = rootItem;
			} else {
				entityList = db.getEntityList(prof, new ExtractActionItem(null, db, prof, veName),
						new EntityItem[] { new EntityItem(rootItem) });
				DARuleEngineMgr.addDebugComment(D.EBUG_DETAIL, debugSb, "DASubstitutionRule VE\n"
						+ PokUtils.outputList(entityList));
				peg = entityList.getParentEntityGroup();
				rootEntity = peg.getEntityItem(rootItem.getKey());
			}

			List ruleAttributes = parseAndValidate(rule, rootEntity, entityList, debugSb);
			//Process the rule and replace ~ENTITY.ATTRIBUTE~ with it's values
			result = derive(ruleAttributes, rootEntity, rule, concatString, veName == null, debugSb);

			//Only dereference after use 
			if (entityList != null) {
				entityList.dereference();
			}

		}
		return result;
	}

	/* (non-Javadoc)
	 * used for IDL to improve performance
	 * @see COM.ibm.eannounce.darule.DARuleItem#getDerivedValues(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.Profile, COM.ibm.eannounce.objects.EntityItem[], java.lang.String[], java.lang.StringBuffer)
	 */
	protected String[] getDerivedValues(Database db, Profile prof, EntityItem[] rootItemArray,
			String[] interimValues, StringBuffer debugSb) throws Exception {
		String[] results = new String[rootItemArray.length];
		String veName = getDAVEName();
		String rule = getRule();
		String concatString = getRuleConcatenationString();
		EntityList veEntityList = null;
		EntityGroup peg = null;
		DARuleEngineMgr.addDebugComment(D.EBUG_DETAIL, debugSb, "DASubstitutionRule - IDL\n"
				+ "DAVENAME: " + veName + "\n" + "RULE: " + rule + "\n" + "CONCATSTRING: "
				+ concatString);
		List ruleAttributes = null;
		for (int i = 0; i < rootItemArray.length; i++) {
			EntityItem rootItem = rootItemArray[i];
			//The root entity type PDHDOMAIN is in DARULE.PDHDOMAIN
			if (isApplicable(rootItem, debugSb)) {
				EntityItem rootEntity;
				if (veName == null) {
					//No VE, get attribute from root item
					rootEntity = rootItem;
					peg = rootEntity.getEntityGroup();
					DARuleEngineMgr.addDebugComment(D.EBUG_DETAIL, debugSb, 
							"DASubstitutionRule: No VE defined");
				} else {
					//Lazy init veEntityList
					if (peg == null) {
						EntityItem[] items = new EntityItem[rootItemArray.length];
						for (int ri = 0; ri < rootItemArray.length; ri++) {
							items[ri] = new EntityItem(rootItemArray[ri]);
						}
						veEntityList = db.getEntityList(prof, new ExtractActionItem(null, db, prof,
								veName), items);
						DARuleEngineMgr.addDebugComment(D.EBUG_DETAIL, debugSb,
								"DASubstitutionRule VE\n" + PokUtils.outputList(veEntityList));
						peg = veEntityList.getParentEntityGroup();
					}
					rootEntity = peg.getEntityItem(rootItem.getKey());
				}

				// parse and validate the rule once
				if (ruleAttributes == null) {
					ruleAttributes = parseAndValidate(rule, rootEntity, veEntityList, debugSb);
				}

				// process this rule
				results[i] = derive(ruleAttributes, rootEntity, rule, concatString, veName == null,
						debugSb);
			} else {
				if (interimValues != null) {
					results[i] = interimValues[i];
				} else {
					results[i] = null;
				}
			}
		}
		if (veEntityList != null) {
			veEntityList.dereference();
		}

		return results;
	}

	/**
	 * Get the DARule VE Name from "DAVENAME" attribute
	 * @return
	 */
	private String getDAVEName() {
		return PokUtils.getAttributeValue(getDARULEEntity(), "DAVENAME", null, null);
	}

	/**
	 * Get the Rule from RULEPASS or RULEFAIL, depending on RULETEST
	 * @return
	 */
	private String getRule() {
		//For now RULETEST result is always PASS
		return PokUtils.getAttributeValue(getDARULEEntity(), "RULEPASS", null, null, false);
	}

	/**
	 * Get the Multiple Rules concatenation string from RULEMULTIPLE
	 * @return An empty string if no RULEMULTIPLE was found
	 */
	private String getRuleConcatenationString() {
		return PokUtils.getAttributeValue(getDARULEEntity(), "RULEMULTIPLE", null, "", false);
	}

	/**
	 * Validate this rule
	 * @param db 
	 * @param prof 
	 * @throws Exception if the rule is not valid
	 */
	private List parseAndValidate(String rule, EntityItem rootEntity, 
			EntityList veList, StringBuffer debugSb)
			throws InvalidDARuleException {
		if (rule == null) {
			throw createInvalidException("The rule cannot be null");
		}

		List ruleAttributes = null;
		try {
			ruleAttributes = ruleParser.parse(rule);
		} catch (Exception e) {
			throw createInvalidException("Unable to parse the rule: " + e.getMessage());
		}

		if (ruleAttributes == null) {
			throw createInvalidException("Unable to parse the rule (parse result is null)");
		}

		List errors = new ArrayList();
		//Loop thru all ~STEPS:ENTITY.ATTRIBUTE~ in the rule
		for (int i = 0; i < ruleAttributes.size(); i++) {
			AttributeRule attributeRule = ((AttributeRule) ruleAttributes.get(i)).getLastRule();
			if (attributeRule instanceof EntityAttributeRule) {
				EntityAttributeRule entityRule = (EntityAttributeRule) attributeRule;
				//Validate if the entity has the attribute
				EntityGroup entityGroup;
				if (veList != null) {
					DARuleEngineMgr.addDebugComment(D.EBUG_DETAIL, debugSb,
							"DASubstitutionRule EntityGroup from VE for "
							+entityRule.getEntityType());
					entityGroup = veList.getEntityGroup(entityRule.getEntityType());
					if (entityGroup == null) {
						//Null entity group might be a VE problem
						String msg = entityRule.getEntityType() 
							+ " was not found in VE: " + getDAVEName();
						errors.add(msg);
						continue;
					}
					
				} else {
					DARuleEngineMgr.addDebugComment(D.EBUG_DETAIL, debugSb,
							"DASubstitutionRule EntityGroup from RootItem for "
							+entityRule.getEntityType());
					entityGroup = rootEntity.getEntityGroup();
				}
				
				EANMetaAttribute metaAttr = entityGroup.getMetaAttribute(entityRule
						.getAttributeCode());
				if (metaAttr == null) {
					String msg = entityRule.getEntityType() 
						+ " don't have the attribute: "
						+ entityRule.getAttributeCode() 
						+ " (Check if your role have access to that attribute)";
					errors.add(msg);
					StringBuffer sb = new StringBuffer();
		            for (int ii = 0; ii < entityGroup.getMetaAttributeCount(); ii++) {
		                EANMetaAttribute ma = entityGroup.getMetaAttribute(ii);
		                sb.append("\nMetaAttribute:" + ii + ":" + ma.dump(true));
		            }
					DARuleEngineMgr.addDebugComment(D.EBUG_DETAIL, debugSb,
							msg+sb.toString());
				}
			} else {
				errors.add("The rule's last step must be 'ENTITY.ATTRIBUTE'. Check the syntax: "
						+attributeRule.getKey());
			}
		}
		if (errors.size() > 0) {
			throw createInvalidException(StringUtils.concatenate(errors, "<br>\n"));
		}

		return ruleAttributes;
	}

	/**
	 * Takes a text string (interimValue) and substitutes values from identified attributes
	 * @return Replaced string 
	 */
	private String derive(List ruleAttributes, EntityItem rootEntity, String inputValue,
			String concatenationString, boolean emptyDAVE, StringBuffer debugSb)
			throws InvalidDARuleException {

		if (concatenationString == null) {
			concatenationString = "";
		}

		ruleContext.setDaRuleItem(getDARULEEntity());

		String outputValue = inputValue;
		boolean allResultsAreEmpty = true;
		for (int i = 0; i < ruleAttributes.size(); i++) {
			AttributeRule rule = (AttributeRule) ruleAttributes.get(i);

			//If there is no VE for this DARULE
			//Throw an exception if the rule references to any other
			//entity that is not the root item.
			//Reason: without the VE is not possible to traverse
			if (emptyDAVE) {
				if (rule instanceof RelatorAttributeRule) {
					throw createInvalidException("This DARULE have no DAVENAME and has a relator in the rule");
				}
			}

			String key = rule.getKey();

			//Reset context: This is the parent to follow the links
			ruleContext.reset(rootEntity);

			//The AttributeRule is a chain of rules to traverse the links and get the final result 
			try {
				rule.executeRule(ruleContext);
			} catch (Exception e) {
				throw createInvalidException(e.getMessage());
			}

			//The default value when has no result 
			String value = "";

			//The rule should have set the context's result
			if (ruleContext.getResults().size() > 0) {
				//The rule should have set the context's result
				value = StringUtils.concatenate(ruleContext.getResults(), concatenationString);
				DARuleEngineMgr.addDebugComment(D.EBUG_DETAIL, debugSb, rootEntity.getKey() 
						+ " " + key + " - Results: "+ruleContext.getResults().size()+" : "+value);
			} else {
				DARuleEngineMgr.addDebugComment(D.EBUG_DETAIL, debugSb,
						"DA Substitution Rule - No results - " + key+"\n" +
						ruleContext.getLog()
						.toString());
			}
			outputValue = StringUtils.replace(outputValue, key, value);
			
			//Check if found at least one non-empty result
			if (value.length() > 0) {
				allResultsAreEmpty = false;
			}
		}
		ruleContext.dereference();

		return allResultsAreEmpty ? null : outputValue;
	}

	private InvalidDARuleException createInvalidException(String msg) {
		Vector vct = new Vector();
		vct.add(getDARULEEntity());
		return new InvalidDARuleException(msg, vct);
	}

	/***********************************************
	 *  Get the version
	 *
	 *@return java.lang.String
	 */
	public static String getVersion() {
		return "$Revision: 1.18 $";
	}

}
