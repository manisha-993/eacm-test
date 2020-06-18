////
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: TranslationDataRequest.java,v $
// Revision 1.23  2008/01/31 21:32:04  wendy
// Cleanup RSA warnings
//
// Revision 1.22  2005/01/27 04:02:37  dave
// removing automated readObject from Jtest
//
// Revision 1.21  2005/01/27 00:34:55  dave
// Jtest on Translation area (formatting and clean up)
//
// Revision 1.20  2004/11/04 18:57:36  dave
// OK Billingcode is now an attribute on the translation entity
// itself and is now part of the PackageID constructor
//
// Revision 1.19  2004/09/01 19:30:28  roger
// Clean up
//
// Revision 1.18  2004/09/01 18:59:51  roger
// Show a bit more for debugging
//
// Revision 1.17  2004/09/01 17:37:42  roger
// Clean up
//
// Revision 1.16  2004/09/01 17:13:48  roger
// And again ...
//
// Revision 1.15  2004/09/01 17:11:17  roger
// Try again
//
// Revision 1.14  2004/09/01 16:54:03  roger
// Clean up for final test
//
// Revision 1.13  2004/09/01 16:29:38  roger
// Want the hash key , not the element
//
// Revision 1.12  2004/08/31 20:25:02  roger
// Syntax
//
// Revision 1.11  2004/08/31 20:18:02  roger
// Syntax
//
// Revision 1.10  2004/08/31 20:01:01  roger
// Find the value ...
//
// Revision 1.9  2004/08/31 16:39:41  roger
// Need BillingCode value
//
// Revision 1.8  2003/08/27 16:51:32  dave
// postETSTranslation VE pre compare
//
// Revision 1.7  2003/08/07 19:37:12  dave
// removing the abstract thing
//
// Revision 1.6  2001/08/22 16:54:00  roger
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
 * A class which defines the TranslationDataRequest used in DATA TranslationPackages
 * @version @date
 */
public final class TranslationDataRequest implements Serializable {
    // Class constants
    /**
     * @serial
     */
    static final long serialVersionUID = 1L;
    // Class variables
    // Instance variable
    /**
     * m_vctEntities
     */
    protected Vector m_vctEntities = new Vector();
    /**
     * m_htEntities
     */
    protected Hashtable m_htEntities = new Hashtable();
    /**
     * m_vctAttributes
     */
    protected Vector m_vctAttributes = new Vector();
    /**
     * m_htAttributes
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
     * Return an enumeration of TranslationEntity key items
     *
     * @return Enumeration
     */
    public Enumeration getEntityKeys() {
        return m_htEntities.keys();
    }
    /**
     * Return an enumeration of TranslationEntity items
     *
     * @return Enumeration
     */
    public Enumeration getEntityElements() {
        return m_htEntities.elements();
    }
    /**
     * Return TranslationEntity with specified key
     *
     * @return TranslationEntity
     * @param _strEntityKey
     */
    public TranslationEntity getEntityElement(String _strEntityKey) {
        return (TranslationEntity) m_htEntities.get(_strEntityKey);
    }
    /**
     * getEntitiesAsVector
     *
     * @return Vector
     */
    public Vector getEntitiesAsVector() {
        return m_vctEntities;
    }
    /**
     * Attach an entity
     *
     * @param _teCurrent
     */
    public final void addEntity(TranslationEntity _teCurrent) {
        String strKey = _teCurrent.getKey();
        m_vctEntities.addElement(_teCurrent);
        m_htEntities.put(strKey, _teCurrent);
    }
    /**
     * Return an enumeration of TranslationAttribute key items
     *
     * @return Enumeration
     */
    public Enumeration getAttributeKeys() {
        return m_htAttributes.keys();
    }
    /**
     * Return an enumeration of TranslationAttribute items
     *
     * @return Enumeration
     */
    public Enumeration getAttributeElements() {
        return m_htAttributes.elements();
    }
    /**
     * Return TranslationAttribute with specified key
     *
     * @return TranslationAttribute
     * @param _strAttributeKey
     */
    public TranslationAttribute getAttributeElement(String _strAttributeKey) {
        return (TranslationAttribute) m_htAttributes.get(_strAttributeKey);
    }
    /**
     * Return TranslationAttributes as a Vector
     *
     * @return Vector
     */
    public Vector getAttributesAsVector() {
        return m_vctAttributes;
    }
    /**
     * Attach a meta attribute
     *
     * @param _teCurrent
     * @param _taCurrent
     */
    public final void addAttribute(TranslationEntity _teCurrent, TranslationAttribute _taCurrent) {
        String strKey = _taCurrent.getKey();
        m_vctAttributes.addElement(_taCurrent);
        m_htAttributes.put(strKey, _taCurrent);
        _teCurrent.addAttribute(_taCurrent);
    }
    /**
     * getBillingCode
     *
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
                TranslationAttribute ta = null;
                D.isplay("XXX found BRANDCODE " + strKey);
                ta = this.getAttributeElement(strKey);
                D.isplay("XXX BRANDCODE AV = " + ta.m_strAttributeValue);

                strResult = ta.m_strAttributeValue;
            }
        }

        return strResult;
    }
    /**
     * Return the date/time this class was generated
     * @return the date/time this class was generated
     */
    public final static String getVersion() {
        return "$Id: TranslationDataRequest.java,v 1.23 2008/01/31 21:32:04 wendy Exp $";
    }
}
