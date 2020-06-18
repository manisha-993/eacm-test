//
//Copyright (c) 2001, International Business Machines Corp., Ltd.
//All Rights Reserved.
//Licensed for use in connection with IBM business only.
//
//$Log: EntityItemException.java,v $
//Revision 1.3  2005/09/08 18:12:25  joan
//add testing rule for meta maintenance
//
//Revision 1.2  2005/02/28 23:31:02  dave
//more Jtest
//

package COM.ibm.eannounce.objects;

/**
 * EntityItemException
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EntityItemException extends EANBusinessRuleException {

    /**
     * EntityItemException
     *
     *  @author David Bigelow
     */
    public EntityItemException() {
        super();
    }

    /**
     * Constructs a <code>EntityItemException</code> with the specified detail message
     *
     * @param s
     */
    public EntityItemException(String s) {
        super(s);
    }

    /**
     * (non-Javadoc)
     * validate
     *
     * @see COM.ibm.eannounce.objects.EANBusinessRuleException#validate(COM.ibm.eannounce.objects.EANAttribute, java.lang.String)
     */
    public void validate(EANAttribute _eanAttr, String _strValue) {
    }

    /**
     * (non-Javadoc)
     * validate
     *
     * @see COM.ibm.eannounce.objects.EANBusinessRuleException#validate(COM.ibm.eannounce.objects.EANMetaAttribute, MetaFlagMaintItem _mi, java.lang.String)
     */

    public void validate(EANMetaAttribute _eanMA, MetaFlagMaintItem _mi, String _strValue) {
	}

    /**
    * Return the date/time this class was generated
    * @return the date/time this class was generated
    */
    public String getVersion() {
        return "$Id: EntityItemException.java,v 1.3 2005/09/08 18:12:25 joan Exp $";
    }
}
