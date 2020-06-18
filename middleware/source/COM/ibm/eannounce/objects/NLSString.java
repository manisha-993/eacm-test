//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: NLSString.java,v $
// Revision 1.10  2005/03/10 20:42:47  dave
// JTest daily ritual
//
// Revision 1.9  2005/03/10 00:25:31  dave
// Jtest readObject removal
//
// Revision 1.8  2005/03/10 00:17:47  dave
// more Jtest work
//
// Revision 1.7  2005/03/08 23:15:47  dave
// Jtest checkins from today and last ngith
//
// Revision 1.6  2002/01/31 21:32:22  dave
// more mass abstract changes
//
// Revision 1.5  2002/01/31 17:58:54  dave
// Rearranging Abrstract levels for more consistiency
//
// Revision 1.4  2001/08/22 16:52:50  roger
// Removed author RM
//
// Revision 1.3  2001/08/01 21:13:28  dave
// more foundation building for the search API
//
// Revision 1.2  2001/08/01 17:44:59  dave
// big syntax fixes
//
//

package COM.ibm.eannounce.objects;

import java.io.Serializable;

/**
* This object holds a NLS enabled piece of text
* @author  David Bigelow
* @version @date
*/
public class NLSString extends Object implements Serializable, Cloneable {

    /**
    * @serial
    */
    static final long serialVersionUID = 1L;

    /**
     * FIELD
     */
    protected int m_iNLS = 0;
    /**
     * FIELD
     */
    protected boolean m_bSelected = false;

    private String m_str = null;

     
    /**
     * Main method which performs a simple test of this class
     *
     * @param arg 
     */
    public static void main(String arg[]) {
    }

    /**
     * NLSString
     *
     * @param _i
     * @param _s
     *  @author David Bigelow
     */
    public NLSString(int _i, String _s) {
        m_str = _s;
        m_iNLS = _i;
    }

    /**
     * (non-Javadoc)
     * toString
     *
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return m_str;
    }

    /**
     * getVersion
     *
     * @return
     *  @author David Bigelow
     */
    public String getVersion() {
        return "$Id: NLSString.java,v 1.10 2005/03/10 20:42:47 dave Exp $";
    }

}
