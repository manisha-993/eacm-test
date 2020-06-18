//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: TranslationMetaAttribute.java,v $
// Revision 1.18  2005/02/08 21:56:51  dave
// more cvs cleanup
//
// Revision 1.17  2005/01/27 04:02:37  dave
// removing automated readObject from Jtest
//
// Revision 1.16  2005/01/27 00:34:55  dave
// Jtest on Translation area (formatting and clean up)
//
// Revision 1.15  2003/09/14 02:24:34  dave
// Translation fixes so no length attributes will also not be
// considered overrides
//
// Revision 1.14  2003/08/26 19:55:15  dave
// some syntax
//
// Revision 1.13  2003/08/26 19:42:29  dave
// providing getKey on AttributeMetaTranslation to
// match key of MetaTranslationItem
//
// Revision 1.12  2003/08/26 19:37:16  dave
// adding Attribute Code Description to MTA
//
// Revision 1.11  2003/08/11 19:08:36  dave
// added override att per IBM 1.2h
//
// Revision 1.10  2003/08/08 18:55:20  dave
// syntax changes
//
// Revision 1.9  2003/08/08 18:04:25  dave
// o.k. Meta API Conv II
//
// Revision 1.8  2003/07/08 01:23:04  dave
// seperation for 1.2 Action retrofit
//
// Revision 1.7  2003/07/08 00:55:49  dave
// more revisions to fit into 1.2
//
// Revision 1.6  2001/08/22 16:54:03  roger
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


import COM.ibm.eannounce.objects.MetaTranslationItem;

import java.io.Serializable;





/**
 * A class which defines the TranslationMetaAttribute used in META TranslationPackages
 * @version @date
 */
public class TranslationMetaAttribute implements Serializable {

    // Class constants

    /**
     * @serial
     */
    static final long serialVersionUID = 1L;

    // Class variables
    // Instance variable
    /**
     * m_strAttributeType
     */
    protected String m_strAttributeType = null;
    /**
     * m_strAttributeCode
     */
    protected String m_strAttributeCode = null;
    /**
     * Field
     */
    protected String m_strAttributeCodeDesc = null;
    /**
     * Field
     */
    protected String m_strAttributeDescription = null;
    /**
     * Field
     */
    protected String m_strAttributeValue = null;
    /**
     * Field
     */
    protected int m_iNLSID = 0;
    /**
     * Field
     */
    protected String m_strTranslatedDescription = null;
    /**
     * Field
     */
    protected String m_strTransDescOverride = null;
    /**
     * Field
     */
    protected int m_iTranslatedNLSID = 0;
    /**
     * Field
     */
    protected boolean m_bMemoryUsed = false;
    /**
     * Field
     */
    protected boolean m_bTranslatable = false;


    /**
     * Main method which performs a simple test of this class
     *
     * @param _args
     */
    public static void main(String _args[]) {
    }

    /**
     * TranslationMetaAttribute
     * @param _mti
     * @param _iNLSID
     * @param _iTargetNLSID
     * @author Dave
     */
    public TranslationMetaAttribute(MetaTranslationItem _mti, int _iNLSID, int _iTargetNLSID) {
        m_strAttributeType = _mti.getType();
        m_strAttributeCode = _mti.getCode();
        m_strAttributeCodeDesc = _mti.getCodeDesc();
        m_strAttributeValue = _mti.getValue();
        m_strAttributeDescription = _mti.getLongDescription();
        m_iNLSID = _iNLSID;
        m_strTranslatedDescription = "";
        m_iTranslatedNLSID = _iTargetNLSID;
        m_bMemoryUsed = false;
        m_bTranslatable = true;

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
     * getAttributeCodeDesc
     * @return
     * @author Dave
     */
    public final String getAttributeCodeDesc() {
        return m_strAttributeCodeDesc;
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
     * getTransDescOverride
     * @return
     * @author Dave
     */
    public final String getTransDescOverride() {
        return m_strTransDescOverride;
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
     * Mutator
     *
     * @param _strTranslatedDescription
     */
    public final void setTranslatedDescription(String _strTranslatedDescription) {
        this.m_strTranslatedDescription = _strTranslatedDescription;
    }

    /**
     * Mutator
     *
     * @param _bMemoryUsed
     */
    public final void setMemoryUsed(boolean _bMemoryUsed) {
        this.m_bMemoryUsed = _bMemoryUsed;
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
     * AttributeType + ":" + AttributeCode + ":" + AttributeValue
     *
     * @return String
     */
    public String getKey() {
        return m_strAttributeType + m_strAttributeCode + m_strAttributeValue;
    }

    /**
     * Return the packageInfo as a <code>String</code>
     *
     * @return String
     */
    public String toString() {
        return "AttributeType: "
        + m_strAttributeType
        + " Code: "
        + m_strAttributeCode
        + " Desc: "
        + m_strAttributeDescription
        + " Value: "
        + m_strAttributeValue
        + " NLSID: "
        + m_iNLSID
        + " Tran'd Desc: "
        + m_strTranslatedDescription
        + " Tran'd NLSID: "
        + m_iTranslatedNLSID
        + " Memory Used: "
        + m_bMemoryUsed;
    }

    /**
    * Return the date/time this class was generated
    * @return the date/time this class was generated
    */
    public static String getVersion() {
        return "$Id: TranslationMetaAttribute.java,v 1.18 2005/02/08 21:56:51 dave Exp $";
    }

}
