//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//

package COM.ibm.eannounce.hula;

import java.sql.ResultSet;
import java.sql.SQLException;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.eannounce.objects.EANList;
import COM.ibm.eannounce.objects.EANMetaFoundation;

/**
 * ePricingGroup
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ePricingGroup extends EANMetaFoundation {

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
    protected EANList m_elPricingItem = new EANList();

    /**
     * getVersion
     *
     * @return String
     */
    public String getVersion() {
        return "$Id: ePricingGroup.java,v 1.7 2005/02/09 22:13:42 dave Exp $";
    }

    /**
     * ePricingGroup
     *
     * @param _db
     * @param _prof
     * @param _strPartNumber
     * @param _strCountryCode
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     *  @author David Bigelow
     */
    public ePricingGroup(Database _db, Profile _prof, String _strPartNumber, String _strCountryCode) throws SQLException, MiddlewareRequestException, MiddlewareException {

        super(null, _prof, _strPartNumber + '.' + _strCountryCode);

        try { 

            // Lets get all the rule LayoutItems here...
            ReturnStatus returnStatus = new ReturnStatus(-1);
            ResultSet rs = null;
            ReturnDataResultSet rdrs = null;

            m_strPartNumber = _strPartNumber;
            m_strCountryCode = _strCountryCode;

            try {
                rs = _db.callGBL9993(returnStatus, _strPartNumber, _strCountryCode);
                rdrs = new ReturnDataResultSet(rs);
            } finally {
                rs.close();
                rs = null;
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }

            // We need to get the description of the link group here...
            for (int i = 0; i < rdrs.size(); i++) {
                int y = 0;
                String strCurrencyCode = rdrs.getColumn(i, y++);
                String strPriceValidFromDate = rdrs.getColumn(i, y++);
                String strPriceValidToDate = rdrs.getColumn(i, y++);
                String strPriceAmount = rdrs.getColumn(i, y++);
                ePricingItem pi = new ePricingItem(this, strCurrencyCode, strPriceValidFromDate, strPriceValidToDate, strPriceAmount);
                m_elPricingItem.put(pi);
            }
        }   finally {
            _db.isPending();
        }
    }

    /**
     * getPrice
     *
     * @return
     *  @author David Bigelow
     */
    public String getPrice() {
        ePricingItem pi = getPricingItem(0);
        if (getPricingItemCount() == 0) {
            return "ZERO PRICE WARNING !!!";
        }
        return pi.toString();
    }

    /**
     * getPricingItemCount
     *
     * @return
     *  @author David Bigelow
     */
    public int getPricingItemCount() {
        return m_elPricingItem.size();
    }

    /**
     * getPricingItem
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public ePricingItem getPricingItem(int _i) {
        return (ePricingItem) m_elPricingItem.getAt(_i);
    }


    /**
     * toString()
     *
     * @return String
     * @author David Bigelow
     */
    public String toString() {
        return m_strPartNumber + "." + m_strCountryCode;
    }

    /**
     * dump
     *
     * @param _bBrief
     * @return
     *  @author David Bigelow
     */
    public String dump(boolean _bBrief) {
        StringBuffer strbResult = new StringBuffer();
        strbResult.append(this.toString());
        return new String(strbResult);
    }

}
