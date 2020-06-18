//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: LocalRuleException.java,v $
// Revision 1.5  2005/09/08 18:12:25  joan
// add testing rule for meta maintenance
//
// Revision 1.4  2005/03/04 18:26:23  dave
// Jtest actions for the day
//
// Revision 1.3  2005/02/18 23:07:42  gregg
// somemore fixin
//
// Revision 1.2  2005/02/18 23:00:41  gregg
// more fixesa
//
// Revision 1.1  2005/02/18 22:43:51  gregg
// initial load
//
//
//

package COM.ibm.eannounce.objects;

/**
 * LocalRuleException
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class LocalRuleException extends EANBusinessRuleException {

    /**
     * LocalRuleException
     *
     *  @author David Bigelow
     */
    public LocalRuleException() {
        super();
    }

    /**
     * Constructs a <code>LocalRuleException</code> with the specified detail message
     *
     * @param s
     */
    public LocalRuleException(String s) {
        super(s);
    }

    /**
     * validate
     *
     * @param _ei
     * @param _lrg
     *  @author David Bigelow
     */
    public void validate(EntityItem _ei, LocalRuleGroup _lrg) {
        for (int i = 0; i < _lrg.getItemCount(); i++) {
            if (!_lrg.getItem(i).evaluate(_ei)) {
                add(_ei, _lrg.getItem(i).getExceptionMessage());
            }
        }
    }

    /**
     * (non-Javadoc)
     * validate
     *
     * @see COM.ibm.eannounce.objects.EANBusinessRuleException#validate(COM.ibm.eannounce.objects.EANAttribute, java.lang.String)
     */
    public void validate(EANAttribute _att, String _s) {
        return; //Nothing!!!
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
        return "$Id: LocalRuleException.java,v 1.5 2005/09/08 18:12:25 joan Exp $";
    }

}
