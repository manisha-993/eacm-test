//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: TranslationEntity.java,v $
// Revision 1.12  2005/03/03 21:35:19  dave
// get the NEW_LINE Stragler
//
// Revision 1.11  2005/03/03 21:25:17  dave
// NEW_LINE on EAN Foundation
//
// Revision 1.10  2005/02/08 21:56:51  dave
// more cvs cleanup
//
// Revision 1.9  2005/01/27 04:02:37  dave
// removing automated readObject from Jtest
//
// Revision 1.8  2005/01/27 03:40:37  dave
// More JTest cleanup
//
// Revision 1.7  2005/01/27 00:34:55  dave
// Jtest on Translation area (formatting and clean up)
//
// Revision 1.6  2001/08/22 16:54:01  roger
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

import java.io.Serializable;
import java.util.Vector;




/**
 * A class which defines the TranslationEntity used in DATA TranslationPackages
 * @version @date
 */
public final class TranslationEntity implements Serializable {

    // Class constants

    /**
     * @serial
     */
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
    protected String m_strEnterprise = null;
    /**
     * FIELD
     */
    protected String m_strEntityType = null;
    /**
     * FIELD
     */
    protected int m_iEntityID = 0;
    /**
     * FIELD
     */
    protected String m_strEntityDescription = null;
    /**
     * FIELD
     */
    protected Vector m_vctAttributes = new Vector();
    /**
     * FIELD
     */
    protected Vector m_vctChildren = new Vector();


    /**
     * Main method which performs a simple test of this class
     *
     * @param _args
     */
    public static void main(String _args[]) {
    }

    /**
     * Construct a <code>TranslationAttribute</code> object
     */
    public TranslationEntity() {
    }

    /**
     * Construct a <code>TranslationAttribute</code> object
     *
     * @param _strEnterprise
     * @param _strEntityType
     * @param _iEntityID
     * @param _strEntityDescription
     */
    public TranslationEntity(String _strEnterprise, String _strEntityType, int _iEntityID, String _strEntityDescription) {
        this.m_strEnterprise = _strEnterprise;
        this.m_strEntityType = _strEntityType;
        this.m_iEntityID = _iEntityID;
        this.m_strEntityDescription = _strEntityDescription;
    }

    /**
     * Accessor
     *
     * @return String
     */
    public String getEnterprise() {
        return m_strEnterprise;
    }

    /**
     * Accessor
     *
     * @return String
     */
    public String getEntityType() {
        return m_strEntityType;
    }

    /**
     * Accessor
     *
     * @return int
     */
    public int getEntityID() {
        return m_iEntityID;
    }

    /**
     * Accessor
     *
     * @return String
     */
    public String getEntityDescription() {
        return m_strEntityDescription;
    }

    /**
     * Accessor
     *
     * @return Vector
     */
    public Vector getAttributesAsVector() {
        return m_vctAttributes;
    }

    /**
     * Accessor
     *
     * @return Vector
     */
    public Vector getChildrenAsVector() {
        return m_vctChildren;
    }

    /**
     * getKey
     * @return String
     */
    public String getKey() {
        return m_strEnterprise + ":" + m_strEntityType + ":" + m_iEntityID;
    }

    /**
     * Attach an attribute
     *
     * @param _taCurrent
     */
    public final void addAttribute(TranslationAttribute _taCurrent) {
        m_vctAttributes.addElement(_taCurrent);
    }

    /**
     * Attach a child reference
     *
     * @param _strChildKey
     */
    public final void addChild(String _strChildKey) {
        m_vctChildren.addElement(_strChildKey);
    }

    /**
     * Return the TranslationEntity as a <code>String</code>
     *
     * @return String
     */
    public final String toString() {

        StringBuffer strbResult = new StringBuffer();

        strbResult.append(
            "Enterprise: "
                + m_strEnterprise
                + " EntityType: "
                + m_strEntityType
                + " EntityID: "
                + m_iEntityID
                + " Description: "
                + m_strEntityDescription);

        for (int i = 0; i < m_vctAttributes.size(); i++) {
            strbResult.append(NEW_LINE + "   attribute: " + (TranslationAttribute) m_vctAttributes.elementAt(i));
        }

        for (int i = 0; i < m_vctChildren.size(); i++) {
            strbResult.append(NEW_LINE + "      child: " + (String) m_vctChildren.elementAt(i));
        }

        return new String(strbResult);
    }

    /**
     * Return the date/time this class was generated
     * @return the date/time this class was generated
     */
    public final static String getVersion() {
        return "$Id: TranslationEntity.java,v 1.12 2005/03/03 21:35:19 dave Exp $";
    }
}
