package COM.ibm.eannounce.darule.parser;

import COM.ibm.eannounce.objects.EANEntity;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.opicmpdh.middleware.D;

//$Log: RelatorAttributeRule.java,v $
//Revision 1.4  2011/04/28 19:51:27  lucasrg
//Fixed rule path logic
//Added better logging
//
//Revision 1.3  2011/04/07 21:27:57  lucasrg
//Applied fixes after testing
//
//Revision 1.2  2011/03/29 17:28:54  lucasrg
//Added support for multiple links and paths
//
//Revision 1.1  2011/03/23 13:53:51  lucasrg
//Initial commit
//

/**
 * Responsible for the Relator part of the syntax: RelatorType-{u|d}
 *  
 * @author lucasrg
 */
public class RelatorAttributeRule extends AttributeRule {

	private static final long serialVersionUID = 1676051960908659011L;

	private String relatorName;

	private char direction;

	public RelatorAttributeRule(String relatorName, char direction) {
		this.relatorName = relatorName;
		this.direction = direction;
	}

	public void executeRule(AttributeRuleContext context) throws Exception {
		EANEntity entityItem = context.getCurrentEntity();
		//Store the direction we are taking
		char currentDirection = context.getCurrentDirection();
		//Set next direction
		context.setCurrentDirection(direction);
		boolean linkFound = false;
		if (currentDirection == AttributeRuleContext.DOWN 
				|| currentDirection == AttributeRuleContext.BOTH) {
			for (int di = 0; di < entityItem.getDownLinkCount(); di++) {
				EANEntity entityLink = entityItem.getDownLink(di);
				if (entityLink.getEntityType().equals(relatorName)) {
					linkFound = true;
					D.ebug(D.EBUG_SPEW, "RelatorAttributeRule: going from " + entityItem.getKey()
							+ " down to " + entityLink.getKey());
					//Set the current entity for the next rule in chain
					context.setCurrentEntity((EntityItem) entityLink);
					executeNextRule(context);
				}
			}
		} else if (currentDirection == AttributeRuleContext.UP 
				|| currentDirection == AttributeRuleContext.BOTH) {
			for (int ui = 0; ui < entityItem.getUpLinkCount(); ui++) {
				EANEntity entityLink = entityItem.getUpLink(ui);
				if (entityLink.getEntityType().equals(relatorName)) {
					linkFound = true;
					D.ebug(D.EBUG_SPEW, "RelatorAttributeRule: going from " + entityItem.getKey()
							+ " up to " + entityLink.getKey());
					//Set the current entity for the next rule in chain
					context.setCurrentEntity((EntityItem) entityLink);
					executeNextRule(context);
				}
			}
		}
		if (!linkFound) {
			String errorMsg = "RelatorAttributeRule: No link found from "
					+ entityItem.getKey()
					+ " [" + currentDirection+ "] to " 
					+ "'" + relatorName	+ "'";
			context.log(errorMsg);
		}
	}

}
