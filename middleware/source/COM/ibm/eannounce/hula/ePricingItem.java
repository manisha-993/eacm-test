//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: ePricingItem.java,v $
// Revision 1.6  2005/02/09 22:13:42  dave
// more JTest Cleanup
//
// Revision 1.5  2004/08/23 16:47:35  dave
// more import fixing
//
// Revision 1.4  2004/08/23 16:40:41  dave
// fixing imports
//
// Revision 1.3  2004/08/23 16:20:41  dave
// new import statements
//
// Revision 1.2  2004/08/23 16:18:14  dave
// Move to new package
//
// Revision 1.1  2004/08/23 16:15:20  dave
// moving eObjects to hula subdirectory
//
// Revision 1.3  2004/08/19 19:02:49  dave
// more cyentex
//
// Revision 1.2  2004/08/19 18:53:34  dave
// typo in Ammount
//
// Revision 1.1  2004/08/19 18:30:47  dave
// commit new Pricing Stuff from ODS
//

package COM.ibm.eannounce.hula;

import java.sql.SQLException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.eannounce.objects.EANFoundation;
import COM.ibm.eannounce.objects.EANMetaFoundation;

/**
 * ePricingItem
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ePricingItem extends EANMetaFoundation {

    static final long serialVersionUID = 20011106L;

    /**
     * FIELD
     */
    protected String m_strPartNumber = null;
    /**
     * FIELD
     */
    protected String m_strCountryCode = null;
    /**
     * FIELD
     */
    protected String m_strDistChannel = null;
    /**
     * FIELD
     */
    protected String m_strPriceValidToDate = null;
    /**
     * FIELD
     */
    protected String m_strPriceType = null;
    /**
     * FIELD
     */
    protected String m_strMaterialStatus = null;
    /**
     * FIELD
     */
    protected String m_strMaterialStatusDate = null;
    /**
     * FIELD
     */
    protected String m_strPriceAmount = null;
    /**
     * FIELD
     */
    protected String m_strCurrencyCode = null;
    /**
     * FIELD
     */
    protected String m_strPriceValidFromDate = null;
    /**
     * FIELD
     */
    protected String m_strCallForQuote = null;
    /**
     * FIELD
     */
    protected String m_strPrecedence = null;
    /**
     * FIELD
     */
    protected String m_strSourceSystem = null;
    /**
     * FIELD
     */
    protected String m_strLastUpdateDate = null;

    /**
     *  (non-Javadoc)
     * getVersion
     * @see COM.ibm.eannounce.objects.EANFoundation#getVersion()
     */
    public String getVersion() {
        return "$Id: ePricingItem.java,v 1.6 2005/02/09 22:13:42 dave Exp $";
    }

    /**
     * ePricingItem
     *
     * @param _ef
     * @param _strCurrencyCode
     * @param _strPriceValidFromDate
     * @param _strPriceValidToDate
     * @param _strPriceAmount
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     *  @author David Bigelow
     */
    protected ePricingItem(EANFoundation _ef, String _strCurrencyCode, String _strPriceValidFromDate, String _strPriceValidToDate, String _strPriceAmount) throws SQLException, MiddlewareRequestException, MiddlewareException {

        super(_ef, null, _strCurrencyCode + "." + _strPriceValidFromDate + "." + _strPriceValidToDate);
        m_strCurrencyCode = _strCurrencyCode;
        m_strPriceValidFromDate = _strPriceValidFromDate;
        m_strPriceValidToDate = _strPriceValidToDate;
        m_strPriceAmount = _strPriceAmount;

    }
    /**
     *  (non-Javadoc)
     * toString
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return m_strPriceAmount + " " + m_strCurrencyCode;
    }
    /**
     * (non-Javadoc)
     * dump
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {
        StringBuffer strbResult = new StringBuffer();
        strbResult.append(m_strCurrencyCode + "." + m_strPriceValidFromDate + "." + m_strPriceValidFromDate + "." + m_strPriceAmount);
        return new String(strbResult);
    }
}
