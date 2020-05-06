/**
 * (c) Copyright International Business Machines Corporation, 2006
 * All Rights Reserved.
 */
package com.ibm.transform.oim.eacm.xalan;

import java.util.Comparator;

import org.w3c.dom.Element;

/**
 * This can sort the Nodes
 * @author cstolpe
 */
public class DOMAttributeComparator implements Comparator {
	final static String TAGNAME = "attribute";
	final static String ORDER = "order";
	final static String CODE = "code";

	/**
	 * Implements Comparator interface
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Object o1, Object o2) {
		int comparison = 0;
		Integer order1 = new Integer(0);
		Integer order2 = new Integer(0);
		if (o1 != null && o2 != null && o1 instanceof Element && o2 instanceof Element) {
			Element e1 = (Element) o1;
			Element e2 = (Element) o2;
			if (e1.getTagName().equals(TAGNAME) && 
				e2.getTagName().equals(TAGNAME) &&
				e1.hasAttribute(ORDER)  &&
				e2.hasAttribute(ORDER)) 
			{
				order1 = new Integer(e1.getAttribute(ORDER));
				order2 = new Integer(e2.getAttribute(ORDER));
				comparison = order1.compareTo(order2);
				// If they have the same order sort by attribute code
				if (comparison == 0) {
					comparison = e1.getAttribute(CODE).compareTo(e2.getAttribute(CODE));
				}
			}
		}
		return comparison;
	}

}
