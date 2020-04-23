package COM.ibm.eannounce.darule.parser;

import java.util.ArrayList;
import java.util.List;

import COM.ibm.eannounce.objects.EntityItem;

//$Log: AttributeRuleContext.java,v $
//Revision 1.4  2011/04/28 19:51:27  lucasrg
//Fixed rule path logic
//Added better logging
//
//Revision 1.3  2011/04/07 21:27:57  lucasrg
//Applied fixes after testing
//
//Revision 1.2  2011/03/29 17:24:51  lucasrg
//Support for multiple results, for "RULEMULTIPLE" concatenation
//
//Revision 1.1  2011/03/23 13:53:51  lucasrg
//Initial commit
//

/**
 * Stores the context that will be processed during the
 * execution of the chain of AttributeRules
 * 
 * Not serializable because it must be transient 
 * 
 * @author lucasrg
 *
 */
public class AttributeRuleContext {

	public static final char BOTH = 'b';
	
	public static final char DOWN = 'd';

	public static final char UP = 'u';

	private EntityItem daRuleItem;
	
	private EntityItem currentEntity;
	
	private List results = new ArrayList();
	
	private char currentDirection = BOTH;
	
	/**
	 * Store log data for debug 
	 */
	private StringBuffer log = new StringBuffer();;

	public EntityItem getCurrentEntity() {
		return currentEntity;
	}

	public void setCurrentEntity(EntityItem entity) {
		currentEntity = entity;
	}
	
	/**
	 * AttributeRuleContext.BOTH, AttributeRuleContext.DOWN or AttributeRuleContext.UP
	 */
	public void setCurrentDirection(char currentDirection) {
		this.currentDirection = currentDirection;
	}
	
	public char getCurrentDirection() {
		return currentDirection;
	}
	
	public void reset(EntityItem currentEntity) {
		log = new StringBuffer();
		currentDirection = BOTH;
		results.clear();
		setCurrentEntity(currentEntity);
	}

	public EntityItem getDaRuleItem() {
		return daRuleItem;
	}

	public void setDaRuleItem(EntityItem daRuleItem) {
		this.daRuleItem = daRuleItem;
	}

	public List getResults() {
		return results;
	}

	public void addResult(String result) {
		this.results.add(result);
	}

	public void dereference() {
		daRuleItem = null;
		currentEntity = null;
		this.results.clear();
	}

	public StringBuffer getLog() {
		return log;
	}
	
	public void log(String message) {
		if (log != null) {
			log.append(message+"\n");
		}
	}

}
