//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: DescriptionChangeItem.java,v $
// Revision 1.9  2005/02/14 17:18:33  dave
// more jtest fixing
//
// Revision 1.8  2002/09/11 16:41:39  dave
// dump message clarification
//
// Revision 1.7  2002/09/10 19:57:32  dave
// more fixes
//
// Revision 1.6  2002/09/10 19:46:52  dave
// more fixes to my stuff
//
// Revision 1.5  2002/09/10 19:34:12  dave
// more changes for Description Change List
//
// Revision 1.4  2002/09/10 05:16:53  dave
// more syntax
//
// Revision 1.3  2002/09/10 05:08:47  dave
// another syntax error
//
// Revision 1.2  2002/09/10 05:01:46  dave
// syntax errors
//
// Revision 1.1  2002/09/10 04:53:22  dave
// new base item object to track metadescription changes
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;

// Temp made this non abstract because we need to compile in general before we whip up
// how actions are suppose to differentiate
/**
 * DescriptionChangeItem
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DescriptionChangeItem extends EANMetaFoundation {

    /**
    * @serial
    */
    static final long serialVersionUID = 1L;

    /**
     */
    public static final int FLAGCODE_DESC = 0;
    /**
     */
    public static final int ATTRIBUTECODE_DESC = 1;
    /**
     */
    public static final int ENTITY_DESC = 2;

    private int m_iNLSID = 1;
    private String m_strOldSDescription;
    private String m_strNewSDescription;
    private String m_strOldLDescription;
    private String m_strNewLDescription;
    private String m_strFlagCode;
    private String m_strAttributeCode;
    private String m_strEntityType;
    private int m_iType;
    private int m_iCount;
    private String m_strTrans;
    private String m_strTransDate;

    /**
     * Main method which performs a simple test of this class
     *
     * @param arg 
     */
    public static void main(String arg[]) {
    }

    //
    // Abstract Method Section
    //

    /**
     * (non-Javadoc)
     * getVersion
     *
     * @see COM.ibm.eannounce.objects.EANFoundation#getVersion()
     */
    public String getVersion() {
        return "$Id: DescriptionChangeItem.java,v 1.9 2005/02/14 17:18:33 dave Exp $";
    }

    /**
     * DescriptionChangeItem
     *
     * @param _me
     * @param _iCount
     * @param _iType
     * @param _strTransDate
     * @param _strTrans
     * @param _iNLSID
     * @param _strEntityType
     * @param _strAttributeCode
     * @param _strFlagCode
     * @param _strOldSDescription
     * @param _strNewSDescription
     * @param _strOldLDescription
     * @param _strNewLDescription
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    protected DescriptionChangeItem(
        EANMetaEntity _me,
        int _iCount,
        int _iType,
        String _strTransDate,
        String _strTrans,
        int _iNLSID,
        String _strEntityType,
        String _strAttributeCode,
        String _strFlagCode,
        String _strOldSDescription,
        String _strNewSDescription,
        String _strOldLDescription,
        String _strNewLDescription)
        throws MiddlewareRequestException {

        super(_me, null, _iCount + "");
        m_iCount = _iCount;
        m_iType = _iType;
        m_iNLSID = _iNLSID;
        m_strTransDate = _strTransDate;
        m_strTrans = _strTrans;
        m_strEntityType = _strEntityType;
        m_strAttributeCode = _strAttributeCode;
        m_strFlagCode = _strFlagCode;
        m_strOldLDescription = _strOldLDescription;
        m_strNewLDescription = _strNewLDescription;
        m_strOldSDescription = _strOldSDescription;
        m_strNewSDescription = _strNewSDescription;
    }

    // Accessors

    /**
     * getCount
     *
     * @return
     *  @author David Bigelow
     */
    public int getCount() {
        return m_iCount;
    }

    /**
     * getType
     *
     * @return
     *  @author David Bigelow
     */
    public int getType() {
        return m_iType;
    }

    /**
     * (non-Javadoc)
     * getNLSID
     *
     * @see COM.ibm.eannounce.objects.EANObject#getNLSID()
     */
    public int getNLSID() {
        return m_iNLSID;
    }

    /**
     * getTransDate
     *
     * @return
     *  @author David Bigelow
     */
    public String getTransDate() {
        return m_strTransDate;
    }

    /**
     * getTrans
     *
     * @return
     *  @author David Bigelow
     */
    public String getTrans() {
        return m_strTrans;
    }

    /**
     * getEntityType
     *
     * @return
     *  @author David Bigelow
     */
    public String getEntityType() {
        return m_strEntityType;
    }

    /**
     * getAttributeCode
     *
     * @return
     *  @author David Bigelow
     */
    public String getAttributeCode() {
        return m_strAttributeCode;
    }

    /**
     * getFlagCode
     *
     * @return
     *  @author David Bigelow
     */
    public String getFlagCode() {
        return m_strFlagCode;
    }

    /**
     * getOldLDescription
     *
     * @return
     *  @author David Bigelow
     */
    public String getOldLDescription() {
        return m_strOldLDescription;
    }

    /**
     * getNewLDescription
     *
     * @return
     *  @author David Bigelow
     */
    public String getNewLDescription() {
        return m_strNewLDescription;
    }

    /**
     * getOldSDescription
     *
     * @return
     *  @author David Bigelow
     */
    public String getOldSDescription() {
        return m_strOldSDescription;
    }

    /**
     * getNewSDescription
     *
     * @return
     *  @author David Bigelow
     */
    public String getNewSDescription() {
        return m_strNewSDescription;
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {

        StringBuffer strbResult = new StringBuffer();

        if (_bBrief) {
            strbResult.append(getKey());
        } else {
            strbResult.append("Key:" + getKey());
            strbResult.append(":Count:" + getCount());
            strbResult.append(":Type:" + getType());
            strbResult.append(":NLS:" + getNLSID());
            strbResult.append(":TransDate:" + getTransDate());
            strbResult.append(":Trans:" + getTrans());
            strbResult.append(":ET:" + getEntityType());
            strbResult.append(":AC:" + getAttributeCode());
            strbResult.append(":FC:" + getFlagCode());
            strbResult.append(":OSD:" + getOldSDescription());
            strbResult.append(":NSD:" + getNewSDescription());
            strbResult.append(":OLD:" + getOldLDescription());
            strbResult.append(":NLD:" + getNewLDescription());
        }

        return new String(strbResult);

    }
}
