//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: SetItem.java,v $
// Revision 1.6  2008/02/01 22:10:08  wendy
// Cleanup RSA warnings
//
// Revision 1.5  2002/09/13 17:04:25  gregg
// updatePdhMeta, expirePdhMeta methods
//
// Revision 1.4  2002/04/10 16:58:16  dave
// better logging during a dump for StateTransitions stuff
//
// Revision 1.3  2002/03/12 20:53:42  dave
// fixing Set Check to conform to abstract super class
//
// Revision 1.2  2002/02/27 20:06:26  dave
// syntax
//
// Revision 1.1  2002/02/27 19:54:23  dave
// new objects to manage state transition
//
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;



/**
*/
public class SetItem extends EANFoundation {

  // Instance variables
  /**
  * @serial
  */
  static final long serialVersionUID = 1L;
	private String m_strValue = null;
	
  public String getVersion() {
    return new String("$Id: SetItem.java,v 1.6 2008/02/01 22:10:08 wendy Exp $");
  }         

  /**
  * Main method which performs a simple test of this class
  */
  public static void main(String arg[]) {
  }

	/**
	* Creates the Check Item, based upon the value of _s1 and its associated MetaAttribute definition
	*/
	public SetItem(EANMetaAttribute _ema, String _strValue) throws MiddlewareRequestException {
		super(_ema, null, _ema.getKey() + _strValue);
		m_strValue = _strValue;
  }

	//
	// ACCESSORS
	//
	public EANMetaAttribute getMetaAttribute() {
		return (EANMetaAttribute)getParent();
	}
	
	public String getValue() {
		return m_strValue;
	}
	
  /** Display the object and show values
  */
  public String dump (boolean _bBrief) {
  	return "SetItem:" + getKey() + ":" + getValue() + ":"  + getMetaAttribute().dump(_bBrief);
  }
  
  public String getLongDescription() {
  	return dump(false);
  }
  
  public String  getShortDescription() {
  	return dump(true);
  }
  
	public String toString() {
		return dump(false);
	}
	
	////
	//Update Pdh Meta 
	////
	
/** 
 * @param _db Database connection
 * @param _bExpire expire or update
 * @param _strTransCode the transition code to use in update
 */
	public void updatePdhMeta(Database _db, String _strTransCode) throws MiddlewareException {
	    updatePdhMeta(_db,_strTransCode,false);
	}

/** 
 * @param _db Database connection
 * @param _bExpire expire or update
 * @param _strTransCode the transition code to use in update
 */	
	public void expirePdhMeta(Database _db, String _strTransCode) throws MiddlewareException {
	    updatePdhMeta(_db,_strTransCode,true);
	}
	
	private void updatePdhMeta(Database _db, String _strTransCode, boolean _bExpire) throws MiddlewareException {
        String strNow = _db.getDates().getNow();
		String strForever = _db.getDates().getForever();
		String strValEffFrom = strNow;
		String strValEffTo = strForever;
		if(_bExpire)
		    strValEffTo = strNow;
        new MetaLinkAttrRow(getMetaAttribute().getProfile()
		                   ,"Trans/Attribute"
						   ,_strTransCode
						   ,getMetaAttribute().getAttributeCode()
						   ,"Set"
						   ,getValue()
						   ,strValEffFrom
						   ,strValEffTo
						   ,strValEffFrom
						   ,strValEffTo
						   ,2).updatePdh(_db);	    
	}


}
