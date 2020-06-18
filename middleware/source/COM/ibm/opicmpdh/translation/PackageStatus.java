//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: PackageStatus.java,v $
// Revision 1.11  2008/01/31 21:32:03  wendy
// Cleanup RSA warnings
//
// Revision 1.10  2005/01/27 04:02:37  dave
// removing automated readObject from Jtest
//
// Revision 1.9  2005/01/27 00:34:55  dave
// Jtest on Translation area (formatting and clean up)
//
// Revision 1.8  2003/08/13 22:31:17  dave
// making changes for set status in TranslationII
//
// Revision 1.7  2003/07/07 20:39:06  dave
// translation for 1.2 retrofit I
//
// Revision 1.6  2001/08/22 16:53:56  roger
// Removed author RM
//
// Revision 1.5  2001/03/26 16:35:46  roger
// Misc formatting clean up
//
// Revision 1.4  2001/03/21 00:01:17  roger
// Implement java class file branding in getVersion method
//
// Revision 1.3  2001/03/16 15:52:28  roger
// Added Log keyword
//


package COM.ibm.opicmpdh.translation;


import java.io.Serializable;


/**
 * A class which defines the PackageStatus for Translation
 * @version @date
 */
public final class PackageStatus implements Serializable {

    // Class constants

    /**
     * @serial
     */
    static final long serialVersionUID = 1L;

    // Class variables
    // Instance variable
    /**
     * TBD
     */
    protected String m_strAttributeValue = null;
    /**
     * TBD
     */
    protected String m_strLongDescription = null;


    /**
     * Main method which performs a simple test of this class
     *
     * @param _args
     */
    public static void main(String _args[]) {
    }

    /**
     * Construct a <code>PackageStatus</code> object
     *
     * @param _strAttributeValue
     * @param _strLongDescription
     */
    protected PackageStatus(
        String _strAttributeValue,
        String _strLongDescription) {
        this.m_strAttributeValue = _strAttributeValue;
        this.m_strLongDescription = _strLongDescription;
    }

    /**
     * Return the status description of the <code>PackageStatus</code>
     *
     * @return String
     */
    public String getStatusDescription() {
        return m_strLongDescription;
    }

    /**
     * getFlag Code
     * @return
     * @author Dave
     */
    public final String getFlagCode() {
        return m_strAttributeValue;
    }

    /**
     * Compare <code>PackageStatus</code> values
     *
     * @param _obj
     * @return boolean
     */
    public boolean equals(Object _obj) {
        if ((_obj != null) && (_obj instanceof PackageStatus)) {
            return m_strAttributeValue.equals(
                ((PackageStatus) _obj).m_strAttributeValue);
        }

        return false;
    }

    /**
     * Return the <code>PackageStatus</code> as a <code>String</code>
     *
     * @return String
     */
    public String toString() {
        return m_strAttributeValue + "-" + m_strLongDescription;
    }

    /**
     * Return the date/time this class was generated
     * @return the date/time this class was generated
     */
    public final static String getVersion() {
        return "$Id: PackageStatus.java,v 1.11 2008/01/31 21:32:03 wendy Exp $";
    }
}
