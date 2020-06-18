//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: TranslationPackage.java,v $
// Revision 1.33  2005/03/03 21:32:11  dave
// NEW_LINE cleanup
//
// Revision 1.32  2005/03/03 21:25:17  dave
// NEW_LINE on EAN Foundation
//
// Revision 1.31  2005/02/08 21:56:51  dave
// more cvs cleanup
//
// Revision 1.30  2005/01/27 04:02:37  dave
// removing automated readObject from Jtest
//
// Revision 1.29  2005/01/27 03:40:37  dave
// More JTest cleanup
//
// Revision 1.28  2005/01/27 00:34:55  dave
// Jtest on Translation area (formatting and clean up)
//
// Revision 1.27  2004/11/04 18:57:36  dave
// OK Billingcode is now an attribute on the translation entity
// itself and is now part of the PackageID constructor
//
// Revision 1.26  2004/09/15 17:24:06  roger
// BillingCode for META package s/b ""
//
// Revision 1.25  2004/09/01 16:54:02  roger
// Clean up for final test
//
// Revision 1.24  2004/08/31 17:15:32  roger
// BillingCode support
//
// Revision 1.23  2004/08/31 16:39:41  roger
// Need BillingCode value
//
// Revision 1.22  2004/08/30 20:21:35  roger
// Try to find value
//
// Revision 1.21  2004/08/30 20:15:34  roger
// Force call to getBillingCode
//
// Revision 1.20  2004/08/30 20:07:57  roger
// New support for billing code
//
// Revision 1.19  2003/09/11 19:51:13  joan
// fix compile
//
// Revision 1.18  2003/09/11 19:35:27  dave
// add lock logic to Translation Package
//
// Revision 1.17  2003/08/28 22:43:31  dave
// More trace
//
// Revision 1.16  2003/08/14 19:28:54  dave
// damn syntax
//
// Revision 1.15  2003/08/14 19:25:18  dave
// comment out cleanup
//
// Revision 1.14  2003/08/14 19:14:51  dave
// forgot the ')'
//
// Revision 1.13  2003/08/14 18:40:09  dave
// more dump information
//
// Revision 1.12  2003/07/15 20:41:30  yang
// chinging TranslationPackage to public
//
// Revision 1.11  2003/07/08 01:45:27  dave
// closing in on it
//
// Revision 1.10  2003/07/07 23:56:39  dave
// my syntax cleaned up
//
// Revision 1.9  2003/07/07 23:38:01  dave
// more syntax
//
// Revision 1.8  2003/07/07 23:01:19  dave
// more res
//
// Revision 1.7  2003/07/07 22:36:32  dave
// more var arranging
//
// Revision 1.6  2001/08/22 16:54:04  roger
// Removed author RM
//
// Revision 1.5  2001/03/26 16:35:47  roger
// Misc formatting clean up
//
// Revision 1.4  2001/03/21 00:01:18  roger
// Implement java class file branding in getVersion method
//
// Revision 1.3  2001/03/16 15:52:28  roger
// Added Log keyword
//


package COM.ibm.opicmpdh.translation;


import COM.ibm.opicmpdh.middleware.Database;


import COM.ibm.opicmpdh.middleware.LockException;
import COM.ibm.opicmpdh.middleware.Profile;

import java.io.Serializable;
import java.util.Enumeration;







/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Dave
 */


public final class TranslationPackage implements Serializable {

    // Class constants
    static final long serialVersionUID = 1L;

    /**
     * FIELD
     */
    public static final String NEW_LINE = "\n";
    /**
    * FIELD
    */
    public static final String TAB = "\t";
    
    /**
     * FIELD
     */
    protected PackageID m_pkID = null;
    /**
     * FIELD
     */
    protected String m_strMemoryHint = null;
    /**
     * FIELD
     */
    protected TranslationDataRequest m_tdr = null;
    /**
     * FIELD
     */
    protected TranslationMetaRequest m_tmr = null;

    /**
     * Main method which performs a simple test of this class
     *
     * @param _args
     */
    public static void main(String _args[]) {
    }

    /**
     * Construct a <code>TranslationPackage</code> object
     *
     * @param _pkID
     * @param _strMemoryHint
     */
    public TranslationPackage(PackageID _pkID, String _strMemoryHint) {
        m_pkID = _pkID;

        if (isDataType()) {
            m_tdr = new TranslationDataRequest();
        } else {
            m_tmr = new TranslationMetaRequest();
        }

        m_strMemoryHint = _strMemoryHint;
    }

    /**
     * isMetaType
     * @return
     * @author Dave
     */
    public final boolean isMetaType() {
        return m_pkID.isMetaType();
    }

    /**
     * isDataType
     * @return
     * @author Dave
     */
    public final boolean isDataType() {
        return m_pkID.isDataType();
    }

    /**
     * getMetaRequest
     * @return
     * @author Dave
     */
    public final TranslationMetaRequest getMetaRequest() {
        return m_tmr;
    }

    /**
     * getDataRequest
     * @return
     * @author Dave
     */
    public final TranslationDataRequest getDataRequest() {
        return m_tdr;
    }

    /**
     * setDataRequest
     * @param _tdr
     * @author Dave
     */
    protected void setDataRequest(TranslationDataRequest _tdr) {
        m_tdr = _tdr;
    }

    /**
     * setMetaRequest
     * @param _tmr
     * @author Dave
     */
    protected void setMetaRequest(TranslationMetaRequest _tmr) {
        m_tmr = _tmr;
    }

    /**
     * getPackageID
     * @return
     * @author Dave
     */
    public final PackageID getPackageID() {
        return m_pkID;
    }

    /**
     * Accessor
     *
     * @return String
     */
    public final String getMemoryHint() {
        return m_strMemoryHint;
    }

    /**
     * Mutator
     *
     * @param _strMemoryHint
     */
    public final void setMemoryHint(String _strMemoryHint) {
        m_strMemoryHint = _strMemoryHint;
    }

    /**
     * Primitive method to dump contents of a <code>TranslationPackage</code>
     *
     * @return String
     */
    public final String dump() {

        StringBuffer strbResult = new StringBuffer();

        if (isDataType()) {

            TranslationDataRequest tdrThis = this.getDataRequest();
            Enumeration eTest1 = tdrThis.getAttributeKeys();

            strbResult.append(NEW_LINE + "PACKAGE: " + m_pkID.toString() + " This is DATA type to language:" + m_pkID.getLanguage());
            strbResult.append(NEW_LINE + "Memory Hint: " + getMemoryHint());
            strbResult.append(NEW_LINE + "==== Translation Entities ====" + tdrThis.m_vctEntities.size());

            for (int i = 0; i < tdrThis.m_vctEntities.size(); i++) {
                TranslationEntity te = (TranslationEntity) tdrThis.m_vctEntities.elementAt(i);
                String strEntityType = te.getEntityType();
                int iEntityID = te.getEntityID();
                strbResult.append(NEW_LINE + "ET:EID:" + strEntityType + ":" + iEntityID + ":" + te.m_vctAttributes.size());
            }

            while (eTest1.hasMoreElements()) {
                String strKey = (String) eTest1.nextElement();
                TranslationAttribute taTest = tdrThis.getAttributeElement(strKey);
                String strAttributeType = taTest.getAttributeType();

                strbResult.append(NEW_LINE + "====");
                strbResult.append(NEW_LINE + "Key: " + strKey);
                strbResult.append(NEW_LINE + "Type: " + taTest.getAttributeType());
                strbResult.append(NEW_LINE + "Translatable: " + taTest.getTranslatable());
                if (!strAttributeType.equals("P")) {
                    strbResult.append(NEW_LINE + "English: " + taTest.getAttributeValue());
                } else {
                    byte[] baTest = taTest.getAttributeValueAsByteArray();
                    strbResult.append(NEW_LINE + "English: byte[] ");
                    for (int i = 0; i < baTest.length; i++) {
                        char chTest = (char) baTest[i];
                        strbResult.append(chTest);
                    }
                }
                strbResult.append(NEW_LINE + "Translated: " + taTest.getTranslatedDescription());
                strbResult.append(NEW_LINE + "TransOverride: " + taTest.getTransDescOverride());
                strbResult.append(NEW_LINE + "Memory: " + taTest.getMemoryUsed());
            }

            strbResult.append(NEW_LINE);
        } else {
            TranslationMetaRequest tmrThis = this.getMetaRequest();
            Enumeration eTest1 = tmrThis.getAttributeKeys();

            strbResult.append(NEW_LINE + "PACKAGE: " + m_pkID.toString() + " This is META type to language:" + m_pkID.getLanguage());
            strbResult.append(NEW_LINE + "Memory Hint: " + getMemoryHint());

            while (eTest1.hasMoreElements()) {
                String strKey = (String) eTest1.nextElement();
                TranslationMetaAttribute tmaTest = tmrThis.getAttributeElement(strKey);
                strbResult.append(NEW_LINE + "====");
                strbResult.append(NEW_LINE + "Key: " + strKey);
                strbResult.append(NEW_LINE + "Type: " + tmaTest.getAttributeType());
                strbResult.append(NEW_LINE + "Translatable: " + tmaTest.getTranslatable());
                strbResult.append(NEW_LINE + "English: " + tmaTest.getAttributeDescription());
                strbResult.append(NEW_LINE + "Translated: " + tmaTest.getTranslatedDescription());
                strbResult.append(NEW_LINE + "TransOverride: " + tmaTest.getTransDescOverride());
                strbResult.append(NEW_LINE + "Memory: " + tmaTest.getMemoryUsed());
            }
            strbResult.append(NEW_LINE);
        }

        return new String(strbResult);
    }

    /**
     * lockForVerification
     *
     * @param _db
     * @param _prof
     * @throws COM.ibm.opicmpdh.middleware.LockException
     * @return
     * @author Dave
     */
    public final boolean lockForVerification(Database _db, Profile _prof) throws LockException {
        if (1 == 0  && _db != null && _prof != null) {
            throw new LockException("Lock Test");
        }

        return true;
    }

    /**
     * unlock
     *
     * @param _db
     * @param _prof
     * @author Dave
     */
    public void unlock(Database _db, Profile _prof) {
        if (_db != null || _prof != null) {
            return;
        }
    }

    /**
     * getBillingCode
     * @return
     * @author Dave
     */
    protected String getBillingCode() {
        String strResult = "";
        if (isDataType()) {
            strResult = m_tdr.getBillingCode();
        } else {
            //       strResult = m_tmr.getBillingCode();
            // Don't attempt to get BillingCode for a META package, just return empty string
            strResult = "";
        }
        //D.isplay("XXX " + strResult);
        return strResult;
    }

    /**
     * Return the date/time this class was generated
     * @return the date/time this class was generated
     */
    public final static String getVersion() {
        return "$Id: TranslationPackage.java,v 1.33 2005/03/03 21:32:11 dave Exp $";
    }
}
