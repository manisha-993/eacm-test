//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: eConcatRule.java,v $
// Revision 1.7  2005/02/09 22:13:42  dave
// more JTest Cleanup
//
// Revision 1.6  2004/10/15 19:40:19  gregg
// switch'n it up w/ class type checks
//
// Revision 1.5  2004/08/30 18:40:55  dave
// adding rollup rule
//
// Revision 1.4  2004/08/27 20:59:00  dave
// adding Valfrom to all objects
//
// Revision 1.3  2004/08/23 19:50:59  dave
// super
//
// Revision 1.2  2004/08/23 18:02:38  dave
// syntax issues
//
// Revision 1.1  2004/08/23 17:50:00  dave
// new Concat basic rule
//

package COM.ibm.eannounce.hula;

import java.sql.SQLException;

import COM.ibm.eannounce.objects.EANFoundation;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;


/**
 * eConcatRule
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class eConcatRule extends eBasicRule {

    static final long serialVersionUID = 20011106L;

    /**
     * Version info
     *
     * @return String
     */
    public String getVersion() {
        return "$Id: eConcatRule.java,v 1.7 2005/02/09 22:13:42 dave Exp $";
    }

    /**
     * eConcatRule
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
     * @param _strTriggerFC
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     *  @author David Bigelow
     */
    protected eConcatRule(
        EANFoundation _ef,
        String _strEntityType,
        String _strHeritage,
        String _strAttributeCode,
        String _strDesc,
        String _strTest,
        String _strPass,
        String _strFail,
        String _strValFrom,
        String _strTriggerFC)
        throws SQLException, MiddlewareRequestException, MiddlewareException {
        super(_ef, _strEntityType, _strHeritage, _strAttributeCode, _strDesc, _strTest, _strPass, _strFail, _strValFrom, _strTriggerFC);
        m_iClassType = CONCATRULE_TYPE;
    }

}
