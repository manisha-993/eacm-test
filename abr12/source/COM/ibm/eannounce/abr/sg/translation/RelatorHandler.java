/**
 * 
 */
package COM.ibm.eannounce.abr.sg.translation;

import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
/**
 * RelatorHandler add relators and creates new parent entities if
 * the child count reaches the parent's limit 
 * @author lucasrg
 *
 */
public abstract class RelatorHandler {

	private String entityFromType;

	private EntityHandler parentEntity;
	
	public RelatorHandler(String entityFromType) {
		this.entityFromType = entityFromType;
	}

	public abstract EntityHandler createParentEntity() throws MiddlewareException;
	
	public ReturnStatus addChild(String entityType, int entityId) throws MiddlewareException {
		ReturnStatus returnStatus = new ReturnStatus();
		try {
			if (parentEntity == null) {
				parentEntity = createParentEntity();
			}
			String relatorType = parentEntity.getEntityType() + entityType;
			D.ebug("RelatorHandler: Creating relator [" + relatorType
					+ "] from " + parentEntity.getEntityType() + " to "
					+ entityType);
			parentEntity.addRelator(relatorType, entityType, entityId);

		} catch (MiddlewareException e) {
			// TODO Check if it is supposed to create a new parent
			/*
			 * //Create a new parent entity parentEntity = null; //Repeat so the
			 * child can be added addChild(relatorType, entityType, entityId);
			 */
			D.ebug("RelatorHandler: [MiddlewareException] " + e.getMessage());

			e.printStackTrace();

			throw e;
		}
		return returnStatus;
	}

	public String getEntityFromType() {
		return entityFromType;
	}

}