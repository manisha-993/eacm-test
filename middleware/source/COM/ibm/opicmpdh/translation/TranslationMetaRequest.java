//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: TranslationMetaRequest.java,v $
// Revision 1.20  2005/02/08 21:56:51  dave
// more cvs cleanup
//
// Revision 1.19  2005/01/27 04:02:37  dave
// removing automated readObject from Jtest
//
// Revision 1.18  2005/01/27 00:34:55  dave
// Jtest on Translation area (formatting and clean up)
//
// Revision 1.17  2004/11/04 18:57:36  dave
// OK Billingcode is now an attribute on the translation entity
// itself and is now part of the PackageID constructor
//
// Revision 1.16  2004/09/01 19:30:28  roger
// Clean up
//
// Revision 1.15  2004/09/01 18:59:51  roger
// Show a bit more for debugging
//
// Revision 1.14  2004/09/01 17:37:42  roger
// Clean up
//
// Revision 1.13  2004/09/01 17:13:48  roger
// And again ...
//
// Revision 1.12  2004/09/01 17:11:17  roger
// Try again
//
// Revision 1.11  2004/09/01 16:54:03  roger
// Clean up for final test
//
// Revision 1.10  2004/09/01 16:29:38  roger
// Want the hash key , not the element
//
// Revision 1.9  2004/08/31 17:23:50  roger
// Syntax
//
// Revision 1.8  2004/08/31 17:15:32  roger
// BillingCode support
//
// Revision 1.7  2004/08/31 16:39:41  roger
// Need BillingCode value
//
// Revision 1.6  2001/08/22 16:54:04  roger
// Removed author RM
//
// Revision 1.5  2001/03/26 16:35:47  roger
// Misc formatting clean up
//
// Revision 1.4  2001/03/21 00:01:17  roger
// Implement java class file branding in getVersion method
//
// Revision 1.3  2001/03/16 15:52:28  roger
// Added Log keyword
//


package COM.ibm.opicmpdh.translation;


import COM.ibm.opicmpdh.middleware.D;


import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;





/**
 * A class which defines the TranslationMetaRequest
 * @version @date
 */
public class TranslationMetaRequest implements Serializable {
    // Class constants
    /**
     * @serial
     */
    static final long serialVersionUID = 1L;
    // Class variables
    // Instance variable
    /**
     * FIELD
     */
    protected Vector m_vctAttributes = new Vector();
    /**
     * FIELD
     */
    protected Hashtable m_htAttributes = new Hashtable();


    /**
     * Main method which performs a simple test of this class
     *
     * @param _args
     */
    public static void main(String _args[]) {
    }
    /**
     * Return an enumeration of TranslationMetaAttribute key items
     *
     * @return Enumeration
     */
    public Enumeration getAttributeKeys() {
        return m_htAttributes.keys();
    }
    /**
     * Return an enumeration of TranslationMetaAttribute items
     *
     * @return Enumeration
     */
    public Enumeration getAttributeElements() {
        return m_htAttributes.elements();
    }
    /**
     * Return TranslationAttribute with specified key
     *
     * @return TranslationMetaAttribute
     * @param _strAttributeKey
     */
    public TranslationMetaAttribute getAttributeElement(String _strAttributeKey) {
        return (TranslationMetaAttribute) m_htAttributes.get(_strAttributeKey);
    }
    /**
     * Return TranslationMetaAttributes as a Vector
     *
     * @return Vector
     */
    public Vector getAttributesAsVector() {
        return m_vctAttributes;
    }
    /**
     * Attach a meta attribute
     *
     * @param _tmaCurrent
     */
    public final void addAttribute(TranslationMetaAttribute _tmaCurrent) {
        String strKey = _tmaCurrent.getKey();
        m_vctAttributes.addElement(_tmaCurrent);
        m_htAttributes.put(strKey, _tmaCurrent);
    }
    /**
     * getBillingCode
     * @return
     * @author Dave
     */
    protected String getBillingCode() {

        String strResult = "";
        Enumeration eAE = this.getAttributeKeys();

        while (eAE.hasMoreElements()) {
            String strKey = (String) eAE.nextElement();

            D.isplay("XXX AttributeKey " + strKey);

            // Find a key with ET=BR, AC=BRANDCODE, AT=U and you've got it ...
            if ((strKey.indexOf(":BR:") >= 0) && (strKey.indexOf("BRANDCODE") >= 0) && (strKey.indexOf(":U:") >= 0)) {

                TranslationMetaAttribute tma = null;

                D.isplay("XXX found BRANDCODE " + strKey);
                tma = this.getAttributeElement(strKey);
                D.isplay("XXX BRANDCODE AV = " + tma.m_strAttributeValue);

                strResult = tma.m_strAttributeValue;
            }
        }

        return strResult;
    }
    /**
     * Return the date/time this class was generated
     * @return the date/time this class was generated
     */
    public static String getVersion() {
        return "$Id: TranslationMetaRequest.java,v 1.20 2005/02/08 21:56:51 dave Exp $";
    }
}
