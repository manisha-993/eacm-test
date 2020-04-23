/**
 * <pre>
 * (c) Copyright International Business Machines Corporation, 2003
 * All Rights Reserved.
 *
 * This class compares AttributeChangeHistoryItem by date descending and value ascending.
 *
 * $Log: ACHIComparator.java,v $
 * Revision 1.3  2005/01/31 16:30:05  joan
 * make changes for Jtest
 *
 * Revision 1.2  2003/08/01 00:38:27  bala
 * EC Team droip
 *
 * Revision 1.1.1.1  2003/06/19 14:05:01  cstolpe
 * Initial Import
 *
 * </pre>
 *
 *@author     cstolpe
 *@created    June 16, 2003
 */
package COM.ibm.eannounce.abr.psg;
import COM.ibm.eannounce.objects.AttributeChangeHistoryItem;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Administrator
 */
public class ACHIComparator implements java.util.Comparator {
	/**
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     * @author Administrator
     */
    public int compare(Object o1, Object o2) {
		AttributeChangeHistoryItem achi1 = (AttributeChangeHistoryItem) o1;
		AttributeChangeHistoryItem achi2 = (AttributeChangeHistoryItem) o2;
		int compare = achi1.getChangeDate().compareTo(achi2.getChangeDate());
		if (compare == 0) {
			// If same date compare values
			achi1.get("ATTVAL", true).toString().compareTo(
				achi2.get("ATTVAL", true).toString());
		}
		else {
			compare *= -1; // sort date in descending order
		}
		return compare;
	}
	/**
     * @see java.lang.Object#equals(java.lang.Object)
     * @author Administrator
     */
    public boolean equals(Object obj) {
		return obj instanceof ACHIComparator;
	}
}
