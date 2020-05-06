// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2002, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
// $Log: ANNDATEComparator.java,v $
// Revision 1.2  2006/01/25 18:42:28  wendy
// AHE copyright
//
// Revision 1.1  2005/09/13 17:09:16  couto
// Init OIM3.0b
//
// Revision 1.1.1.1  2004/01/26 17:40:02  chris
// Latest East Coast Source
//
// Revision 1.1  2002/11/19 16:52:38  cstolpe
// ANNOUNCEMENT:ANNDATE EntityItem Comparator used in AnnProjRpt.jsp
//
package com.ibm.transform.oim.eacm.util;
import COM.ibm.eannounce.objects.EntityItem;

/**
 *
 * ANNOUNCEMENT:ANNDATE EntityItem Comparator used in AnnProjRpt.jsp
 */
public class ANNDATEComparator extends java.lang.Object implements java.util.Comparator {
    /** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2002, 2006  All Rights Reserved.";

    /**
     * Compare two ANNDATE
     *
     * @param o1 - First Obejct to be compared
     * @param o2 - Second Ojbect to be compared
     * @return int
     */
    public int compare(Object o1, Object o2) {
        EntityItem ei1 = (EntityItem) o1;
        EntityItem ei2 = (EntityItem) o2;
        String s1 = PokUtils.getAttributeValue(ei1, "ANNDATE", "","");
        String s2 = PokUtils.getAttributeValue(ei2, "ANNDATE", "","");
        return s1.compareTo(s2);
    }
} // -- end class ANNDATEComparator

