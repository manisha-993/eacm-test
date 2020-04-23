package COM.ibm.eannounce.darule.parser;

import java.util.ArrayList;

import COM.ibm.eannounce.objects.EANEntity;
import COM.ibm.eannounce.objects.EANMetaAttribute;
import COM.ibm.eannounce.objects.EntityItem;

import com.ibm.transform.oim.eacm.util.PokUtils;

//$Log: EntityAttributeRule.java,v $
//Revision 1.7  2011/05/19 16:20:14  lucasrg
//Added getters to EntityType and AttributeCode
//
//Revision 1.6  2011/04/28 19:51:27  lucasrg
//Fixed rule path logic
//Added better logging
//
//Revision 1.5  2011/04/28 12:59:13  lucasrg
//Added multi flag delimiter
//
//Revision 1.4  2011/04/25 21:45:29  lucasrg
//Removed html conversion in PokUtils.getAttributeValue calls
//
//Revision 1.3  2011/04/07 21:27:57  lucasrg
//Applied fixes after testing
//
//Revision 1.2  2011/03/29 17:27:41  lucasrg
//Traverse to find entity in context's "currentEntity"
//
//Revision 1.1  2011/03/23 13:53:51  lucasrg
//Initial commit
//

/**
 * This rule represents the part: EntityType.AttributeCode of the rule syntax; 
 * 
 * @author lucasrg
 *
 */
public class EntityAttributeRule extends AttributeRule {

	private static final long serialVersionUID = -8665974578750568958L;

	private String entityType;

	private String attributeCode;

	public EntityAttributeRule(String entityType, String attributeCode) {
		this.entityType = entityType;
		this.attributeCode = attributeCode;
	}
	
	public String getEntityType() {
		return entityType;
	}
	
	public String getAttributeCode() {
		return attributeCode;
	}

	public void executeRule(AttributeRuleContext context) throws Exception {
		EntityItem entity = context.getCurrentEntity();
		ArrayList resultEntities = new ArrayList();
		if (entity.getEntityType().equals(entityType)) {
			resultEntities.add(entity);
		}
		

			//Coming from a relator, use it's direction (-u or -d)
			char currentDirection = context.getCurrentDirection();
			
			if (currentDirection == AttributeRuleContext.DOWN 
					|| currentDirection == AttributeRuleContext.BOTH) {
				//Look for downlinks
				for (int i = 0; i < entity.getDownLinkCount(); i++) {
					EANEntity item = entity.getDownLink(i);
					if (item.getEntityType().equals(entityType)) {
						resultEntities.add(item);
					}
				}
			}

				if (currentDirection == AttributeRuleContext.DOWN
						|| currentDirection == AttributeRuleContext.BOTH) {
					//Look for uplinks
					for (int i = 0; i < entity.getUpLinkCount(); i++) {
						EANEntity item = entity.getUpLink(i);
						if (item.getEntityType().equals(entityType)) {
							resultEntities.add(item);
						}
					}
				}

		if (resultEntities.size() > 0) {
			for (int i = 0; i < resultEntities.size(); i++) {
				EntityItem resultEntity = (EntityItem) resultEntities.get(i);
				EANMetaAttribute metaAttr = resultEntity.getEntityGroup().getMetaAttribute(attributeCode);
				if (metaAttr==null) {
					throw new Exception("Attribute "+attributeCode+" NOT found in '"+
							resultEntity.getEntityType()+"' META data.");
				}
				context.addResult(PokUtils.getAttributeValue(resultEntity,
						attributeCode, ", ", "", false));
			}
		} else {
			String errorMsg = "EntityAttributeRule: No entity found from "
				+ entity.getKey()
				+ " [" + currentDirection+ "] to " 
				+ "'" + entityType	+ "'";
			context.log(errorMsg);
		}

	}

}
