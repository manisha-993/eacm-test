//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: RequiredRuleException.java,v $
// Revision 1.17  2007/05/31 20:11:05  wendy
// Improve debug msgs
//
// Revision 1.16  2005/09/08 18:12:25  joan
// add testing rule for meta maintenance
//
// Revision 1.15  2005/03/10 20:42:47  dave
// JTest daily ritual
//
// Revision 1.14  2004/12/08 22:34:01  dave
// untracify
//
// Revision 1.13  2004/12/08 22:14:07  dave
// Tracing
//
// Revision 1.12  2004/08/12 18:02:42  joan
// CR3616
//
// Revision 1.11  2002/11/12 17:18:28  dave
// System.out.println clean up
//
// Revision 1.10  2002/11/04 17:48:10  dave
// fixing 22756 (removing required and default value checks
// if you are isUsedInSearch is true)
//
// Revision 1.9  2002/10/16 21:44:59  dave
// bad patch to say if the entity is deactivated and the string you
// are setting it to must be length zero in order to fail a required
// check.. otherwise it is on its way to being set
//
// Revision 1.8  2002/09/25 17:37:15  joan
// remove System.out
//
// Revision 1.7  2002/09/25 16:24:00  dave
// Deactivate non Classified attributes on update
//
// Revision 1.6  2002/09/06 21:53:59  dave
// DGEntity Changes
//
// Revision 1.5  2002/05/15 23:38:34  dave
// fix for syntax
//
// Revision 1.4  2002/05/15 23:28:44  dave
// fix to allow deactivates to go onto the change stack
//
// Revision 1.3  2002/05/15 18:36:17  dave
// attempted fix at required
//
// Revision 1.2  2002/05/08 19:56:41  dave
// attempting to throw the BusinessRuleException on Commit
//
// Revision 1.1  2002/04/15 22:51:11  joan
// remove EAN prefix
//
//

package COM.ibm.eannounce.objects;

/**
 * RequiredRuleException
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class RequiredRuleException extends EANBusinessRuleException {

    /**
     * RequiredRuleException
     *
     *  @author David Bigelow
     */
    public RequiredRuleException() {
        super();
    }

    /**
     * Constructs a <code>RequiredRuleException</code> with the specified detail message
     *
     * @param s
     */
    public RequiredRuleException(String s) {
        super(s);
    }

    /**
     * (non-Javadoc)
     * validate
     *
     * @see COM.ibm.eannounce.objects.EANBusinessRuleException#validate(COM.ibm.eannounce.objects.EANAttribute, java.lang.String)
     */
    public void validate(EANAttribute _eanAttr, String _strValue) {

        EANMetaAttribute eanMA = _eanAttr.getMetaAttribute();
        EntityItem ei = (EntityItem) _eanAttr.getParent();
        EntityGroup eg = ei.getEntityGroup();
        String strValue = _strValue;

        // If the EntityGroup is Classified and the Attribute is not in the ClassificationList
        // we return w/o checking.. it will be deactivated by the update process, when posted
        // to the database
        if (ei != null && eg != null && eg.isClassified() && !ei.isClassified(eanMA)) {
            return;
        }

        // We do not need to check anything if the parent entity item is used in a Search
        if (ei.isUsedInSearch()) {
            return;
        }

        // This needs to be validated...

        if (_eanAttr.isRequired()) {
            if (strValue == null || strValue.length() == 0) {

                // CR3616 Required Field + Null value + not editable = no need to check when saved
                if (_eanAttr.isEditable()) {
                    System.out.println("RequiredRuleException.validate() *** REQUIRED missing ***" +
                    	_eanAttr.getAttributeCode()+" on "+_eanAttr.getParent().getKey());
                    add(_eanAttr, (strValue == null ? "null value" : "empty value") + ": Required information missing");
                }
            } else if (!_eanAttr.isActive() && strValue.length() == 0) {
                // Not sure we want to do this
                System.out.println("RequiredRuleException.validate() *** REQUIRED attempting to deactivate  ***" +
                	_eanAttr.getAttributeCode()+" on "+_eanAttr.getParent().getKey());
                add(_eanAttr, strValue + ": Cannot Deactivate a Required Attribute");
            }
        }
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
        return "$Id: RequiredRuleException.java,v 1.17 2007/05/31 20:11:05 wendy Exp $";
    }

}
