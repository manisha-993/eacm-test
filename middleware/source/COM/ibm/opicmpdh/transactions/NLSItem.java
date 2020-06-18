//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: NLSItem.java,v $
// Revision 1.14  2008/01/31 21:29:04  wendy
// Cleanup RSA warnings
//
// Revision 1.13  2005/03/11 22:21:40  dave
// removing those nasty auto method generations
//
// Revision 1.12  2005/01/27 04:02:36  dave
// removing automated readObject from Jtest
//
// Revision 1.11  2005/01/26 20:45:36  dave
// Jtest cleanup effort
//
// Revision 1.10  2001/09/11 21:57:48  roger
// Convert ConnectionItem + SessionObject + NLSID to Profile
//
// Revision 1.9  2001/08/22 16:53:10  roger
// Removed author RM
//
// Revision 1.8  2001/08/14 16:11:09  dave
// corrected object type name issues
//
// Revision 1.7  2001/08/13 22:32:53  roger
// Equals method normally compares an Object
//
// Revision 1.6  2001/05/31 21:16:48  dave
// sundry toString methods
//
// Revision 1.5  2001/05/21 22:23:29  dave
// equals method work
//
// Revision 1.4  2001/04/03 19:03:22  dave
// changes to doc tags and a put change to the OPICBlobValue
//
// Revision 1.3  2001/03/21 00:01:12  roger
// Implement java class file branding in getVersion method
//
// Revision 1.2  2001/03/16 16:08:57  roger
// Added Log keyword, and standard copyright
//


package COM.ibm.opicmpdh.transactions;


import java.io.Serializable;

/**
* This holds a basic PDH NLSID and its textual description
* @version @date
*/
public class NLSItem extends Object implements Serializable, Cloneable {
    /**
    * @serial
    */
    static final long serialVersionUID = 1L;

    /**
     * NLSID
     */
    public static final int NLSID = 0;
    /**
     * NLSIDDESC
     */
    public static final int NLSDESC = 1;
    private int m_iNLSID = 0;
    private String m_strNLSDescription = null;


    /**
     * Main method which performs a simple test of this class
     *
     * @param arg
     */
    public static void main(String arg[]) {
    }

    /**
     * Creates an NLSItem Object
     *
     * @param _i1
     * @param _s1
     */
    public NLSItem(int _i1, String _s1) {

        super();
        this.m_iNLSID = _i1;
        this.m_strNLSDescription = _s1;
    }

    /** Return the NLSID
    * @return the NLSID*/
    public int getNLSID() {
        return m_iNLSID;
    }

    /** Return the NLS description
    * @return the description
    */
    public String getNLSDescription() {
        return m_strNLSDescription;
    }

    /**
     * return any property based upon the passed constant
     *
     * @return Object at the given index
     * @param _i
     */
    public Object get(int _i) {
        switch (_i) {
        case NLSID :
            return new Integer(m_iNLSID);
        case NLSDESC :
            return m_strNLSDescription;
        default :
            return null;
        }
    }

    /**
     * Display the NLSItem Properties
     *
     * @param out
     */
    public void display(java.io.PrintStream out) {
        out.println(
            "NLSItem:" + this.m_iNLSID + ":" + this.m_strNLSDescription);
    }

    /** Return the date/time this class was generated
    * @return the date/time this class was generated
    */
    public String getVersion() {
        return "$Id: NLSItem.java,v 1.14 2008/01/31 21:29:04 wendy Exp $";
    }

    /**
     * equals method
     *
     * @param _nlsi
     * @return
     * @author Dave
     */
    public boolean equals(NLSItem _nlsi) {
        return getNLSID() == _nlsi.getNLSID();
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     * @author Dave
     */
    public boolean equals(Object _obj) {
        return (
            (_obj instanceof NLSItem)
            && (getNLSID() == ((NLSItem) _obj).getNLSID()));
    }

    /**
     * @see java.lang.Object#toString()
     * @author Dave
     */
    public String toString() {
        return m_strNLSDescription;
    }
}
