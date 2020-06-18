//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: StateTransitionException.java,v $
// Revision 1.10  2014/12/04 22:45:31  stimpsow
// add attr code to trace msg
//
// Revision 1.9  2005/09/08 18:12:25  joan
// add testing rule for meta maintenance
//
// Revision 1.8  2003/04/24 18:32:19  dave
// getting rid of traces and system out printlns
//
// Revision 1.7  2002/11/12 17:18:29  dave
// System.out.println clean up
//
// Revision 1.6  2002/09/26 23:39:34  joan
// remove System.out
//
// Revision 1.5  2002/04/17 18:39:52  dave
// whoop.. needed to get the set call for the statetransition into
// the put for the EAN flag Attribute
//
// Revision 1.4  2002/04/17 18:08:37  dave
// put the set call in the EANUtlity
//
// Revision 1.3  2002/04/17 17:54:09  dave
// changes to test set login on state transition
//
// Revision 1.2  2002/04/17 17:21:45  dave
// commented out no state transition clause
//
// Revision 1.1  2002/04/15 22:51:11  joan
// remove EAN prefix
//
//

package COM.ibm.eannounce.objects;

public class StateTransitionException extends EANBusinessRuleException {

  	public StateTransitionException() {
  		super();
  	}

  	/**
  	* Constructs a <code>StateTransitionException</code> with the specified detail message
  	*/
  	public StateTransitionException(String s) {
  		super(s);
  	}

  public void validate(EANAttribute _eanAttr, String _strValue) {
		clearLists();
		if (_eanAttr instanceof StatusAttribute) {
			StatusAttribute fa = (StatusAttribute)_eanAttr;
			String strFlagValue = _strValue;
			String strFlagFrom = fa.getFirstActiveFlagCode();

			if (! strFlagFrom.equals(strFlagValue)) {
		 		EANMetaFlagAttribute mfa = (EANMetaFlagAttribute)fa.getMetaAttribute();
		 		if (mfa instanceof MetaStatusAttribute) {
					MetaStatusAttribute msa = (MetaStatusAttribute)mfa;
					// System.out.println("msa.dump: " + msa.dump(false));
					StateTransition st = msa.getStateTransition(strFlagFrom, strFlagValue);
					if (st != null) {
						boolean b = st.evaluate((EntityItem)_eanAttr.getParent());
						if (!b) {
							add(_eanAttr, strFlagValue + ": Failed State Transition Evaluation");
						}
					} else {

						// This is OK not to have a state transition for now may turn it on after testing DWB
						//add(_eanAttr, strFlagValue + ": No State Transition");

						System.out.println(_eanAttr.getAttributeCode()+" "+ strFlagValue + ":No State Transition Found:");

					}
				}
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
    return "$Id: StateTransitionException.java,v 1.10 2014/12/04 22:45:31 stimpsow Exp $";
  }

}
