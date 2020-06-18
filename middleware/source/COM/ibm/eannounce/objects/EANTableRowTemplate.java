//
// Copyright (c) 2003, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: EANTableRowTemplate.java,v $
// Revision 1.4  2004/10/21 16:49:54  dave
// trying to share compartor
//
// Revision 1.3  2003/03/10 17:24:49  gregg
// some comments
//
// Revision 1.2  2003/03/10 17:12:46  gregg
// minor changes to match EANAddressable interface
//
// Revision 1.1  2003/03/07 22:13:18  gregg
// initial load
//
//

package COM.ibm.eannounce.objects;

/**
* Enforce that an object can be used as a row in an <CODE>EANSimpleTableTemplate</CODE>.
* <BR>- Note that these are really just glorified <CODE>EANObjects</CODE>.
* <BR>- Column keys need to match columns keys of parent table.
* <BR>- Note that these methods are a subset of interface EANAddressable combined w/ EANObject.
*/
public interface EANTableRowTemplate extends EANObject {

  public static final String CLASS_BRAND = new String("$Id: EANTableRowTemplate.java,v 1.4 2004/10/21 16:49:54 dave Exp $");

  /**
  * Return the <CODE>EANFoundation</CODE> associated w/ this column's cell.
  */
  public EANFoundation getEANObject(String _strColKey);

  /**
  * Put the object in the indicated column - note that in mose cases, we will want _obj to be an EANFoundation -
  * (pass Object to remain consistent w/ EANAddressable).
  */
  public boolean put(String _strColKey, Object _obj) throws EANBusinessRuleException;

  /**
  * Is the cell editable?
  */
  public boolean isEditable(String _s);



}
