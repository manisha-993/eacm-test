package COM.ibm.eannounce.catalog;

import java.util.Collection;

// Interface to define what methods needed for operating on a CatList of CatItems

public interface Listable {
  void put(CatItem _ci);
  CatItem get(CatId _cid);
  Collection values();
  
//  CatItem get(index _i);  // DWB.. we will not need until we proved we need ordered list capability.

//indexOf(Object)
//indexOf(Id)
//remove(int)
//removeAll()
//remove(Object)
//remove(Id)
//resetId(OldId)
//copyto(ObjectArray)
//replace(Object)
//clone()
//copyList(List)
}

/*
 * $Log: Listable.java,v $
 * Revision 1.2  2011/05/05 11:21:33  wendy
 * src from IBMCHINA
 *
 * Revision 1.1.1.1  2007/06/05 02:09:21  jingb
 * no message
 *
 * Revision 1.1.1.1  2006/03/30 17:36:29  gregg
 * Moving catalog module from middleware to
 * its own module.
 *
 * Revision 1.6  2005/05/19 21:48:13  dave
 * more list trickery
 *
 * Revision 1.5  2005/05/13 20:39:49  roger
 * Turn on logging in source
 *
 */

