//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: eBasicRule.java,v $
// Revision 1.16  2005/02/09 22:13:42  dave
// more JTest Cleanup
//
// Revision 1.15  2004/10/15 19:40:19  gregg
// switch'n it up w/ class type checks
//
// Revision 1.14  2004/08/30 23:02:05  dave
// null pointer
//
// Revision 1.13  2004/08/30 19:13:01  dave
// bugs
//
// Revision 1.12  2004/08/30 19:04:29  dave
// rollup rule
//
// Revision 1.11  2004/08/30 18:55:10  dave
// make sure it compiles
//
// Revision 1.10  2004/08/30 18:40:54  dave
// adding rollup rule
//
// Revision 1.9  2004/08/27 20:59:00  dave
// adding Valfrom to all objects
//
// Revision 1.8  2004/08/26 19:54:16  dave
// new Required Concat
//
// Revision 1.7  2004/08/23 18:02:38  dave
// syntax issues
//
// Revision 1.6  2004/08/23 17:50:00  dave
// new Concat basic rule
//
// Revision 1.5  2004/08/23 16:47:35  dave
// more import fixing
//
// Revision 1.4  2004/08/23 16:40:41  dave
// fixing imports
//
// Revision 1.3  2004/08/23 16:20:40  dave
// new import statements
//
// Revision 1.2  2004/08/23 16:18:14  dave
// Move to new package
//
// Revision 1.1  2004/08/23 16:15:20  dave
// moving eObjects to hula subdirectory
//
// Revision 1.6  2004/08/18 00:06:50  dave
// member variable fix
//
// Revision 1.5  2004/08/16 22:54:23  dave
// trapping null pointer
//
// Revision 1.4  2004/08/16 22:39:58  dave
// sytax fixes
//
// Revision 1.3  2004/08/16 22:13:14  dave
// more test stuff
//
// Revision 1.2  2004/08/16 22:07:30  dave
// syntax errs
//
// Revision 1.1  2004/08/16 21:59:53  dave
// new eBasicRule
//
// Revision 1.7  2004/08/16 21:19:33  dave
// alligning variables
//
// Revision 1.6  2004/08/16 21:06:12  dave
// 0 needs to be -1
//
// Revision 1.5  2004/08/16 20:26:59  dave
// more change
//
// Revision 1.4  2004/08/16 20:19:53  dave
// alittle more syntax
//
// Revision 1.3  2004/08/16 20:13:04  dave
// adding more columns to 9992
//
// Revision 1.2  2004/08/16 20:08:08  dave
// syntax
//
// Revision 1.1  2004/08/16 19:59:38  dave
// new eRulesCollection off from ODS
//

package COM.ibm.eannounce.hula;

import java.sql.SQLException;

import COM.ibm.eannounce.objects.EANFoundation;
import COM.ibm.eannounce.objects.EANMetaFoundation;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;

/**
 * eBasicRule
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class eBasicRule extends EANMetaFoundation {

    static final long serialVersionUID = 20011106L;

    /**
     * CONCATRULE_TYPE
     */
    public static final int CONCATRULE_TYPE = 0;
    /**
     * FIELD
     */
    public static final int ROLLUPRULE_TYPE = 1;
    /**
     * FIELD
     */
    public static final int REQUIREDRULE_TYPE = 2;
    /**
     * FIELD
     */
    protected int m_iClassType = -1;
    /**
     * FIELD
     */
    protected String m_strEntityType = null;
    /**
     * FIELD
     */
    protected String m_strHeritage = null;
    /**
     * FIELD
     */
    protected String m_strAttributeCode = null;
    /**
     * FIELD
     */
    protected String m_strDesc = null;
    /**
     * FIELD
     */
    protected String m_strTest = null;
    /**
     * FIELD
     */
    protected String m_strPass = null;
    /**
     * FIELD
     */
    protected String m_strFail = null;
    /**
     * FIELD
     */
    protected String m_strValFrom = null;
    /**
     * FIELD
     */
    protected boolean m_bTriggerIn = false;
    /**
     * FIELD
     */
    protected boolean m_bTriggerOut = false;
    /**
     * FIELD
     */
    protected boolean m_bTriggerBoth = false;
    /**
     * FIELD
     */
    public static final String TRIGGERIN = "RTINB";
    /**
     * FIELD
     */
    public static final String TRIGGEROUT = "RTOTB";
    /**
     * TRIGGERBOTH
     */
    public static final String TRIGGERBOTH = "RTBOTH";

    /**
     * get Version
     * @return String
     */
    public String getVersion() {
        return "$Id: eBasicRule.java,v 1.16 2005/02/09 22:13:42 dave Exp $";
    }

    /**
     * eBasicRule
     *
     * @param _ef
     * @param _strEntityType
     * @param _strHeritage
     * @param _strAttributeCode
     * @param _strDesc
     * @param _strTest
     * @param _strPass
     * @param _strFail
     * @param _strValFrom
     * @param _strTrigger
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     *  @author David Bigelow
     */
    protected eBasicRule(
        EANFoundation _ef,
        String _strEntityType,
        String _strHeritage,
        String _strAttributeCode,
        String _strDesc,
        String _strTest,
        String _strPass,
        String _strFail,
        String _strValFrom,
        String _strTrigger)
        throws SQLException, MiddlewareRequestException, MiddlewareException {
        super(_ef, null, _strHeritage + "." + _strEntityType + "." + _strAttributeCode);

        m_strEntityType = _strEntityType;
        m_strHeritage = _strHeritage;
        m_strAttributeCode = _strAttributeCode;
        m_strDesc = _strDesc;
        m_strTest = _strTest;
        m_strPass = _strPass;
        m_strFail = _strFail;
        if (_strTrigger == null) {
            _strTrigger = "NOOP";
        }
        m_bTriggerIn = _strTrigger.equals(TRIGGERIN);
        m_bTriggerOut = _strTrigger.equals(TRIGGEROUT);
        m_bTriggerBoth = _strTrigger.equals(TRIGGERBOTH);

        m_strValFrom = _strValFrom;

    }

    /**
     * toString
     *
     * @return String
     * @author David Bigelow
     */
    public String toString() {
        return m_strHeritage + "." + m_strEntityType + "." + m_strAttributeCode + "." + m_strDesc + ".Test." + m_strTest + ".Pass." + m_strPass + "." + m_strFail;
    }

    /**
     * dump
     * @param _bBrief
     * @return String
     * @author David Bigelow
     */
    public String dump(boolean _bBrief) {
        StringBuffer strbResult = new StringBuffer();
        strbResult.append(this.toString());
        return new String(strbResult);

    }

    /**
     * getEntityType
     *
     * @return String
     *  @author David Bigelow
     */
    public String getEntityType() {
        return m_strEntityType;
    }

    /**
     * getHeritage
     *
     * @return
     *  @author David Bigelow
     */
    public String getHeritage() {
        return m_strHeritage;
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
     * getDescription
     *
     * @return
     *  @author David Bigelow
     */
    public String getDescription() {
        return m_strDesc;
    }

    /**
     * getTest
     *
     * @return
     *  @author David Bigelow
     */
    public String getTest() {
        return m_strTest;
    }
    /**
     * getPass
     *
     * @return
     *  @author David Bigelow
     */
    public String getPass() {
        return m_strPass;
    }
    /**
     * getFail
     *
     * @return
     *  @author David Bigelow
     */
    public String getFail() {
        return m_strFail;
    }

    /**
     * isInBoundTrigger
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isInBoundTrigger() {
        return m_bTriggerBoth || m_bTriggerIn;
    }
    /**
     * isOutBoundTrigger
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isOutBoundTrigger() {
        return m_bTriggerBoth || m_bTriggerOut;
    }

    /**
     * getType
     *
     * @return
     *  @author David Bigelow
     */
    protected final int getType() {
        return m_iClassType;
    }

}
