//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: SimplePicklistAttribute.java,v $
// Revision 1.7  2008/02/01 22:10:08  wendy
// Cleanup RSA warnings
//
// Revision 1.6  2003/03/25 20:31:42  gregg
// more applyColumnOrders for relator case
//
// Revision 1.5  2003/02/27 19:43:07  gregg
// added multi-logic
//
// Revision 1.4  2003/02/26 23:38:49  gregg
// return getFirstSelectedLongDescripton in toString() method
//
// Revision 1.3  2003/02/26 23:02:46  gregg
// a few more protected methods for convenience
//
// Revision 1.2  2003/02/26 22:12:04  gregg
// accessors to elements in list
//
// Revision 1.1  2003/02/26 21:56:29  gregg
// New SimpleTextAttribute/SimplePicklistAttribute classes + use these in Rendering RowSelectableTable for FilterGroup/FilterItem
//
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;



/**
 * This class can be used to hold Attribute-like information without necessarily being tied directly to any instance
 * of PDH Attribute Data, EntityItems, etc..
 * -- E.g. as a placeholder for RowSelectableTable cell information when we don't need/want a full-blown EANAttribute
 * -- SimplePicklistAtribute holds EANList of NLS-sensitive MetaTag values 
 * -- Currently acts only as single flag - i.e. only one selected option
 */
public class SimplePicklistAttribute extends MetaTag {

  /**
  * @serial
  */
    static final long serialVersionUID = 1L;
    private EANList m_elPicklist = null;
	private boolean m_bMultiple = false;

	/**
	* Creates the SimplePicklistAttribute - set multi
	* @param _ef the Assosicated Creator tied to this Object
	* @param _prof creator's Profile
	* @param _strKey the key for the MetaTag (~attCode)
	* @param _elPicklist an EANList of MetaTags (isSelected() == true on selected MetaTag(s))
	* @param _bMultiple is this a multi-selectable picklist (false acts like a single flag attribute)
	*/
	public SimplePicklistAttribute(EANFoundation _ef, Profile _prof, String _strKey, EANList _elPicklist, boolean _bMultiple) throws MiddlewareRequestException {
	    super(_ef,_prof, _strKey);	
		setPicklist(_elPicklist);
		setMultiple(_bMultiple);
		// enforce only 1 or 0 selected in picklist for non-multi picklists
		if(!isMultiple() && getSelected().size() > 1) {
		    throw new MiddlewareRequestException("You cannot have more than one selected Item in a Non-Multiple SimplePicklistAttribute.");
		}
    }
	
    protected SimplePicklistAttribute getCopy() throws MiddlewareRequestException {
        return new SimplePicklistAttribute(getParent(),getProfile(),getKey(),getPicklist().copyList(getPicklist()),isMultiple());    
    }
    
	protected void setPicklist(EANList _elPicklist) {
	    m_elPicklist = _elPicklist;   
	}
	
/////////////////
//  ACCESSORS  //  
/////////////////
	
/**
 * Get available options for this SimplePicklistAttribute
 * @return EANList of MetaTags representing picklist of options -- isSelected() on MetaTag's represents selected option
 */
	public EANList getPicklist() {
	    return m_elPicklist;
	}
	
/**
 * Get the MetaTag at the specified index in the picklist
 */
    public MetaTag getAt(int _i) {
        return (MetaTag)getPicklist().getAt(_i);
    }
 
/**
 * Number of options in picklist
 */
	public int getCount() {
        return getPicklist().size();
    }
/**
 * Get the first selected MetaTag in the list - this really is only relevant for NON-multiple Picklists.
 * Use getSelected() for multiple picklists.
 * @return first (index-wise) selected MetaTag in the list
 */
    protected MetaTag getFirstSelected() {
	    for(int i = 0; i < getCount(); i++) {
		    if(getAt(i).isSelected()) {
		        return getAt(i);
		    }
	    }
		return null;
    }
	
/**
 * Get a list of selected MetaFlags; size() should eq 1 for NON-multiple picklists
 */
    protected EANList getSelected() {
        EANList elSelected = new EANList();
		for(int i = 0; i < getCount(); i++) {
		    if(getAt(i).isSelected()) {
		        elSelected.put(getAt(i));
		    }
		}
		return elSelected;
    }   
	
/**	
 * Get the selected MetaTag's key --> this really is only relevant if non-multiple
 */
	protected String getFirstSelectedLongDescription() {
	    return (getFirstSelected()!=null?getFirstSelected().getLongDescription():"");
    }
	
/**
 * Get the selected MetaTag's key --> this really is only relevant if non-multiple
 */
	protected String getFirstSelectedKey() {
	    return (getFirstSelected()!=null?getFirstSelected().getKey():"");
    }
	
/**
 * Is this SimplePicklistAttribute acting like a Multi-Flag (ie allow more than one selection)?
 */
    public boolean isMultiple() {
        return m_bMultiple;
    }
	
////////////////
//  MUTATORS  //
////////////////
	
/**
 * Set the option w/ the given key as selected(true).
 * If this is a NON-multiple picklist -> turn all others off: else leave others untouched
 */
    protected void setSelectedKey(String _strKey) {
		for(int i = 0; i < getCount(); i++) {
	        MetaTag mt = getAt(i);
			if(mt.getKey().equals(_strKey)) {
			    mt.setSelected(true);
			} else if(!isMultiple()){ //set all to false if multi
			    mt.setSelected(false);
			}
	    }
    }
	
/**
 * Is this SimplePicklistAttribute acting like a Multi-Flag (ie allow more than one selection)?
 */
    private void setMultiple(boolean _b) {
        m_bMultiple = _b;
    }
	
/////////////////
	
/**
 * @return description of selected option
 */
	public String toString() {
	    return getFirstSelectedLongDescription();
	}
	
    public String getVersion() {
      return new String("$Id: SimplePicklistAttribute.java,v 1.7 2008/02/01 22:10:08 wendy Exp $");
    }

	

}

