//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: TranslationAttribute.java,v $
// Revision 1.15  2005/02/08 21:56:51  dave
// more cvs cleanup
//
// Revision 1.14  2005/01/27 04:02:37  dave
// removing automated readObject from Jtest
//
// Revision 1.13  2005/01/27 00:34:55  dave
// Jtest on Translation area (formatting and clean up)
//
// Revision 1.12  2003/09/14 02:24:34  dave
// Translation fixes so no length attributes will also not be
// considered overrides
//
// Revision 1.11  2003/08/11 19:08:36  dave
// added override att per IBM 1.2h
//
// Revision 1.10  2003/07/08 01:54:43  dave
// minor fix
//
// Revision 1.9  2003/07/08 01:45:27  dave
// closing in on it
//
// Revision 1.8  2003/07/08 01:23:04  dave
// seperation for 1.2 Action retrofit
//
// Revision 1.7  2003/07/08 00:55:49  dave
// more revisions to fit into 1.2
//
// Revision 1.6  2001/08/22 16:53:59  roger
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
 * A class which defines the TranslationAttribute used in DATA TranslationPackages
 * @version @date
 */
public final class TranslationAttribute implements Serializable {

    // Class constants

    /**
     * @serial
     */
    static final long serialVersionUID = 1L;

    /**
     * m_strAttributeType
     */
    protected String m_strAttributeType = null;
    /**
     * m_strAttributeCode
     */
    protected String m_strAttributeCode = null;
    /**
     * m_strAttributeDescription
     */
    protected String m_strAttributeDescription = null;
    /**
     * m_strAttributeValue
     */
    protected String m_strAttributeValue = null;
    /**
     * m_iNLSID
     */
    protected int m_iNLSID = 0;
    /**
     * m_strTranslatedDescription
     */
    protected String m_strTranslatedDescription = null;
    /**
     * m_strTransDescOverride
     */
    protected String m_strTransDescOverride = null;

    /**
     * m_iTranslatedNLSID
     */
    protected int m_iTranslatedNLSID = 0;
    /**
     * m_bMemoryUsed
     */
    protected boolean m_bMemoryUsed = false;
    /**
     * m_bTranslatable
     */
    protected boolean m_bTranslatable = false;
    /**
     * m_strEnterprise
     */
    protected String m_strEnterprise = null;
    /**
     * m_strEntityType
     */
    protected String m_strEntityType = null;
    /**
     * m_iEntityID
     */
    protected int m_iEntityID = 0;
    /**
     * m_baAttributeValue
     */
    protected byte[] m_baAttributeValue = null;
    /**
     * m_baTranslatedDescription
     */
    protected byte[] m_baTranslatedDescription = null;
    /**
     * m_baTransDescOverride
     */
    protected byte[] m_baTransDescOverride = null;


    /**
     * Main method which performs a simple test of this class
     *
     * @param _args
     */
    public static void main(String _args[]) {
    }

    /**
     * TranslationAttribute
     *
     * @param _strEnterprise
     * @param _strEntityType
     * @param _iEntityID
     * @param _strAttributeType
     * @param _strAttributeCode
     * @param _strAttributeDescription
     * @param _strAttributeValue
     * @param _iNLSID
     * @param _iTranslatedNLSID
     * @param _bTranslatable
     * @author Dave
     */
    public TranslationAttribute(
        String _strEnterprise,
        String _strEntityType,
        int _iEntityID,
        String _strAttributeType,
        String _strAttributeCode,
        String _strAttributeDescription,
        String _strAttributeValue,
        int _iNLSID,
        int _iTranslatedNLSID,
        boolean _bTranslatable) {
        m_strAttributeType = _strAttributeType;
        m_strAttributeCode = _strAttributeCode;
        m_strAttributeDescription = _strAttributeDescription;
        m_strAttributeValue = _strAttributeValue;
        m_iNLSID = _iNLSID;
        m_iTranslatedNLSID = _iTranslatedNLSID;
        m_bMemoryUsed = false;
        m_bTranslatable = _bTranslatable;
        m_strEnterprise = _strEnterprise;
        m_strEntityType = _strEntityType;
        m_iEntityID = _iEntityID;
    }

    /**
     * TranslationAttribute
     *
     * @param _strEnterprise
     * @param _strEntityType
     * @param _iEntityID
     * @param _strAttributeType
     * @param _strAttributeCode
     * @param _strAttributeDescription
     * @param _baAttributeValue
     * @param _iNLSID
     * @param _iTranslatedNLSID
     * @param _bTranslatable
     * @author Dave
     */
    public TranslationAttribute(
        String _strEnterprise,
        String _strEntityType,
        int _iEntityID,
        String _strAttributeType,
        String _strAttributeCode,
        String _strAttributeDescription,
        byte[] _baAttributeValue,
        int _iNLSID,
        int _iTranslatedNLSID,
        boolean _bTranslatable) {
        m_strAttributeType = _strAttributeType;
        m_strAttributeCode = _strAttributeCode;
        m_strAttributeDescription = _strAttributeDescription;
        m_baAttributeValue = _baAttributeValue;
        m_iNLSID = _iNLSID;
        m_iTranslatedNLSID = _iTranslatedNLSID;
        m_bMemoryUsed = false;
        m_bTranslatable = _bTranslatable;
        m_strEnterprise = _strEnterprise;
        m_strEntityType = _strEntityType;
        m_iEntityID = _iEntityID;
        m_baAttributeValue = _baAttributeValue;
        m_strAttributeValue = null;
    }

    /**
     * getAttributeType
     * @return
     * @author Dave
     */
    public final String getAttributeType() {
        return m_strAttributeType;
    }

    /**
     * getAttributeCode
     * @return
     * @author Dave
     */
    public final String getAttributeCode() {
        return m_strAttributeCode;
    }

    /**
     * getAttributeDescription
     * @return
     * @author Dave
     */
    public final String getAttributeDescription() {
        return m_strAttributeDescription;
    }

    /**
     * getAttributeValue
     * @return
     * @author Dave
     */
    public final String getAttributeValue() {
        return m_strAttributeValue;
    }

    /**
     * getNLSID
     * @return
     * @author Dave
     */
    public final int getNLSID() {
        return m_iNLSID;
    }

    /**
     * getTranslatedDescription
     * @return
     * @author Dave
     */
    public final String getTranslatedDescription() {
        return m_strTranslatedDescription;
    }
    /**
     * getTranslatedNLSID
     * @return
     * @author Dave
     */
    public final int getTranslatedNLSID() {
        return m_iTranslatedNLSID;
    }

    /**
     * getMemoryUsed
     * @return
     * @author Dave
     */
    public final boolean getMemoryUsed() {
        return m_bMemoryUsed;
    }
    /**
     * getTranslatable
     * @return
     * @author Dave
     */
    public final boolean getTranslatable() {
        return m_bTranslatable;
    }

    /**
     * setTranslatedDescription
     * @param _strTranslatedDescription
     * @author Dave
     */
    public final void setTranslatedDescription(String _strTranslatedDescription) {
        this.m_strTranslatedDescription = _strTranslatedDescription;
    }
    /**
     * setMemoryUsed
     * @param _bMemoryUsed
     * @author Dave
     */
    public final void setMemoryUsed(boolean _bMemoryUsed) {
        this.m_bMemoryUsed = _bMemoryUsed;
    }

    /**
     * getTransDescOverride
     * @return
     * @author Dave
     */
    public final String getTransDescOverride() {
        return m_strTransDescOverride;
    }

    /**
     * setTransDescOverride
     * @param _str
     * @author Dave
     */
    public final void setTransDescOverride(String _str) {
        m_strTransDescOverride = _str;
    }

    /**
     * hasTransDescOverride
     * @return
     * @author Dave
     */
    public boolean hasTransDescOverride() {
        return m_strTransDescOverride != null && m_strTransDescOverride.trim().length() > 0;
    }

    /**
     * hasTransDescOverrideAsByteArray
     * @return
     * @author Dave
     */
    public boolean hasTransDescOverrideAsByteArray() {
        return m_baTransDescOverride != null;
    }

    /**
     * getEnterprise
     * @return
     * @author Dave
     */
    public final String getEnterprise() {
        return m_strEnterprise;
    }

    /**
     * getEntityType
     * @return
     * @author Dave
     */
    public final String getEntityType() {
        return m_strEntityType;
    }

    /**
     * getEntityID
     * @return
     * @author Dave
     */
    public final int getEntityID() {
        return m_iEntityID;
    }

    /**
     * getAttributeValueAsByteArray
     * @return
     * @author Dave
     */
    public final byte[] getAttributeValueAsByteArray() {
        return m_baAttributeValue;
    }

    /**
     * getTranslatedDescriptionAsByteArray
     * @return
     * @author Dave
     */
    public final byte[] getTranslatedDescriptionAsByteArray() {
        return m_baTranslatedDescription;
    }
    /**
     * getTransDescOverrideAsByteArray
     * @return
     * @author Dave
     */
    public final byte[] getTransDescOverrideAsByteArray() {
        return m_baTransDescOverride;
    }

    /**
     * setTranslatedDescriptionAsByteArray
     * @param _baTranslatedDescription
     * @author Dave
     */
    public final void setTranslatedDescriptionAsByteArray(byte[] _baTranslatedDescription) {
        this.m_baTranslatedDescription = _baTranslatedDescription;
    }

    /**
     * setTransDescOverrideAsByteArray
     * @param _baTranslatedDescription
     * @author Dave
     */
    public final void setTransDescOverrideAsByteArray(byte[] _baTranslatedDescription) {
        this.m_baTransDescOverride = _baTranslatedDescription;
    }

    /**
     * getKey
     * @return
     * @author Dave
     */
    public final String getKey() {
        return this.m_strEnterprise
        + ":"
        + this.m_strEntityType
        + ":"
        + this.m_iEntityID
        + ":"
        + this.m_strAttributeType
        + ":"
        + this.m_strAttributeCode;
    }

    /**
     * @see java.lang.Object#toString()
     * @author Dave
     */
    public final String toString() {
        return "Enterprise: "
        + m_strEnterprise
        + " EntityType: "
        + m_strEntityType
        + " EntityID: "
        + m_iEntityID
        + " "
        + super.toString();
    }

    /**
     * getVersion
     * @return
     * @author Dave
     */
    public final static String getVersion() {
        return "$Id: TranslationAttribute.java,v 1.15 2005/02/08 21:56:51 dave Exp $";
    }
}
