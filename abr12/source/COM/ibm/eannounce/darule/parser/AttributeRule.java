package COM.ibm.eannounce.darule.parser;

import java.io.Serializable;

//$Log: AttributeRule.java,v $
//Revision 1.5  2011/05/19 16:21:26  lucasrg
//added getLastRule method
//
//Revision 1.4  2011/04/07 21:27:57  lucasrg
//Applied fixes after testing
//
//Revision 1.3  2011/03/29 17:24:01  lucasrg
//AttributeRule extensions now are responsible for calling next rule in chain
//
//Revision 1.2  2011/03/25 14:46:50  lucasrg
//Java 1.3 compatibility
//
//Revision 1.1  2011/03/23 13:53:51  lucasrg
//Initial commit
//

/**
 * Rule to be chained and executed.<br>
 * Is created by the {@link AttributeRuleParser} to process rules like:<br>
 * WWSEODERIVEDDATA-d:DERIVEDDATA.MEMRYRAMSTDUNIT  
 * 
 * @author lucasrg
 *
 */
public abstract class AttributeRule implements Serializable {
	
	private static final long serialVersionUID = -4496111977016801915L;

	private String key;

	private AttributeRule nextRule;
	
	protected void executeNextRule(AttributeRuleContext context) throws Exception {
		if (nextRule != null) {
			nextRule.executeRule(context);
		}
	}
	
	public void setNextRule(AttributeRule nextRule) {
		this.nextRule = nextRule;
	}
	
	public abstract void executeRule(AttributeRuleContext context) throws Exception;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public AttributeRule getLastRule() {
		if (nextRule != null) {
			return nextRule.getLastRule();
		} else {
			return this;
		}
	}
	
}
