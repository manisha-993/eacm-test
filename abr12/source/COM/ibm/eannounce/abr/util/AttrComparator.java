// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2010  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.util;

import java.util.Comparator;
import COM.ibm.eannounce.objects.EntityItem;
import com.ibm.transform.oim.eacm.util.PokUtils;

/**********************************************************************************
* Utility to sort collections of entityitems based on a specific attribute code
*/
//$Log: AttrComparator.java,v $
//Revision 1.1  2010/12/14 18:58:49  wendy
//Attribute comparator
//
public class AttrComparator implements Comparator {
	private String attrCode= null;
	
	public AttrComparator(String att){
		attrCode = att;
	}
	public int compare(Object _s1, Object _s2) {
		EntityItem ei1 = (EntityItem)_s1;
		EntityItem ei2 = (EntityItem)_s2;

		String val1 = PokUtils.getAttributeValue(ei1, attrCode, "", "", false);
		String val2 = PokUtils.getAttributeValue(ei2, attrCode, "", "", false);
		return val1.compareTo(val2);
	}
}