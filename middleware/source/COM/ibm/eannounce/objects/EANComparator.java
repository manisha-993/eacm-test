//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: EANComparator.java,v $
// Revision 1.12  2005/03/11 22:42:53  dave
// removing some auto genned stuff
//
// Revision 1.11  2005/02/14 17:25:16  dave
// Variables and formatting
//
// Revision 1.10  2004/10/21 16:49:53  dave
// trying to share compartor
//
// Revision 1.9  2003/03/19 18:36:27  gregg
// cleaner integer checking in compare method
//
// Revision 1.8  2003/03/14 22:45:53  gregg
// compareInt in case EANComparable's toCompareString yields an Integer
//
// Revision 1.7  2002/06/14 22:50:36  gregg
// more class casting...
//
// Revision 1.6  2002/06/14 22:39:12  gregg
// syntaxx
//
// Revision 1.5  2002/06/14 22:30:01  gregg
// check instanceof EANComparable in compare(obj,obj) method
//
// Revision 1.4  2002/06/14 21:47:02  gregg
// added compare for EANComparable objects
//
// Revision 1.3  2002/03/12 22:04:44  dave
// more Array , import, cleanup stuff
//
// Revision 1.2  2002/03/12 21:49:32  dave
// syntax
//
// Revision 1.1  2002/03/12 21:39:53  dave
// new Comparitor
//
//

package COM.ibm.eannounce.objects;

import java.util.Comparator;

/**
 * EANComparator
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EANComparator implements Comparator {

    boolean m_bAscending = true;
    /**
     * EANComparator
     *
     * @param _b
     *  @author David Bigelow
     */
    public EANComparator(boolean _b) {
        m_bAscending = _b;
    }

    /**
     * EANComparator
     *
     *  @author David Bigelow
     */
    public EANComparator() {
    }

    /**
     * (non-Javadoc)
     * compare
     *
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(Object o1, Object o2) {
        // EANComparable, so sort by Object's toCompareString().
        // Remember, this is more specific than EANMetaFoundation, so it must be checked first.
        if (o1 instanceof EANComparable) {
            EANComparable ec1 = (EANComparable) o1;
            EANComparable ec2 = (EANComparable) o2;
            //if compare Strings are BOTH int's, we want to sort integer-wise
            if (isInt(ec1.toCompareString()) && isInt(ec2.toCompareString())) {
                try {
                    return compareInt(Integer.parseInt(ec1.toCompareString()), Integer.parseInt(ec2.toCompareString()));
                } catch (NumberFormatException nfExc) { /*do nothing coz we already checked for this!!*/
                }
            } else {
                return compare(ec1.toCompareString(), ec2.toCompareString());
            }
        }
        if (o1 instanceof EANMetaFoundation) {
            return compareString(o1.toString(), o2.toString());
        } else {
            return compareString(o1.toString(), o2.toString());
        }
    }

    /**
     * (non-Javadoc)
     * equals
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object o3) {
        return m_bAscending;
    }

    private int compareInt(int _i1, int _i2) {
        if (_i1 < _i2) {
            return (getResult(-1));
        
        } else if (_i1 > _i2) {
            return (getResult(1));
        }
        return 0;
    }

    private int compareString(String s1, String s2) {
        return getResult(s1.compareToIgnoreCase(s2));
    }

    private int getResult(int result) {
        if (!m_bAscending) {
            return -result;
        }
        return result;
    }

    /**
    * Check if a String is an int
    */
    private boolean isInt(String _s) {
        for (int i = 0; i < _s.length(); i++) {
            if (!Character.isDigit(_s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * getVersion
     *
     * @return
     *  @author David Bigelow
     */
    public String getVersion() {
        return "$Id: EANComparator.java,v 1.12 2005/03/11 22:42:53 dave Exp $";
    }


}
