//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: MetaHelpValue.java,v $
// Revision 1.17  2009/05/04 17:44:43  wendy
// check for rs!=null before close()
//
// Revision 1.16  2005/03/08 23:15:46  dave
// Jtest checkins from today and last ngith
//
// Revision 1.15  2005/01/18 21:46:50  dave
// more parm debug cleanup
//
// Revision 1.14  2004/01/14 22:56:03  dave
// more trimming.. targeting reverse lookup function
//
// Revision 1.13  2003/04/08 02:28:08  dave
// better tracing
//
// Revision 1.12  2003/04/08 02:25:55  dave
// close and commit it
//
// Revision 1.11  2003/03/12 18:24:18  dave
// simplifying and preparing for the Tagging requirement
//
// Revision 1.10  2003/01/09 22:15:30  gregg
// in updatePdhMeta: dont insert empty Strings fix
//
// Revision 1.9  2002/12/16 18:05:39  gregg
// return a boolean in updatePdhMeta method indicating wheter any database updates were performed.
//
// Revision 1.8  2002/10/31 22:26:00  gregg
// new constructor to create an "empty" MetaHelpValue
//
// Revision 1.7  2002/05/17 17:27:57  joan
// working on ReportActionItem
//
// Revision 1.6  2002/04/08 18:20:13  gregg
// valOn/effOn -> use db.getdates().getNow()
//
// Revision 1.5  2002/03/14 22:36:24  gregg
// updatePdh()/expirePdh()
//
// Revision 1.4  2002/03/14 20:14:14  gregg
// made non-abstract
//
// Revision 1.3  2002/03/14 20:12:03  gregg
// some cleanup
//
// Revision 1.2  2002/03/14 20:00:48  gregg
// protected isNewValue()
//
// Revision 1.1  2002/03/14 19:39:37  gregg
// initial load
//
//

package COM.ibm.eannounce.objects;

import java.sql.ResultSet;
import java.sql.SQLException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.ReturnStatus;

/**
* Manages a MetaAttribute's help text
*
*/
public class MetaHelpValue extends EANMetaFoundation {

    /**
    * @serial
    */
    static final long serialVersionUID = 1L;
    private boolean m_bNewValue = true;

    /**
     * construct an empty MetaHelpValue
     *
     * @param _oEma
     * @param _prof
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public MetaHelpValue(EANMetaFoundation _oEma, Profile _prof) throws MiddlewareRequestException {
        super(_oEma, _prof, _oEma.getKey());
    }

    /**
     * Construct using database
     *
     * @param _oEma
     * @param _db
     * @param _prof
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public MetaHelpValue(EANMetaFoundation _oEma, Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareRequestException {
        super(_oEma, _prof, _oEma.getKey());
        
        try {
            ResultSet rs = null;
            ReturnDataResultSet rdrs = null;
            int iNLSID = getProfile().getReadLanguage().getNLSID();
            String strEnterprise = getProfile().getEnterprise();
            String strAttCode = getAttributeCode();
            String strValOn = getProfile().getValOn();
            String strEffOn = getProfile().getEffOn();
    
            try {
                rs = _db.callGBL1092(new ReturnStatus(-1), strEnterprise, strAttCode, iNLSID, strValOn, strEffOn);
                rdrs = new ReturnDataResultSet(rs);
            } finally {
            	if (rs!=null){
            		rs.close();
            		rs = null;
            	}
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }
            if (rdrs.getRowCount() > 0) {
                String strHelpValue = rdrs.getColumn(0, 1).trim();
                setNewValue(false);
                setHelpValueText(strHelpValue);
            } else {
                setNewValue(true);
                setHelpValueText("");
            }
            return;
        } finally {      
        }
    }

    /**
     * Get the attributeCode associated w/ this MetaHelpValue's parent
     *
     * @return String
     */
    public String getAttributeCode() {
        EANMetaFoundation oMa = (EANMetaFoundation) getParent();
        return oMa.getKey();
    }

    /**
     * Store the helpValue text. Note: this is really just storing longDescription.
     *
     * @param _s 
     */
    public void setHelpValueText(String _s) {
        putLongDescription(_s);
        return;
    }

    /**
     * Store the helpValue text. Note: this is really just storing longDescription.
     *
     * @param _i
     * @param _s 
     */
    public void setHelpValueText(int _i, String _s) {
        putLongDescription(_i, _s);
        return;
    }

    /**
     * Get the helpValue text. Note: this is really just grabbing longDescription.
     *
     * @return String
     */
    public String getHelpValueText() {
        return getLongDescription();
    }

    /**
     * is this a new HelpValue (! in database)?
     *
     * @return boolean
     */
    protected boolean isNewValue() {
        return m_bNewValue;
    }

    /**
     * set whether or not this is a new value
     */
    private void setNewValue(boolean _b) {
        m_bNewValue = _b;
        return;
    }

    /**
     * Get the version Id - generated by CVS
     *
     * @return String
     */
    public String getVersion() {
        return "$Id: MetaHelpValue.java,v 1.17 2009/05/04 17:44:43 wendy Exp $";
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {
        StringBuffer strBuf = new StringBuffer();
        strBuf.append("AttCode:  " + getAttributeCode());
        strBuf.append("NLSID:    " + getNLSID());
        strBuf.append("HelpValue:" + getHelpValueText());
        return strBuf.toString();
    }

    /**
     * override EANMetaFoundation's toString() method
     *
     * @return String
     */
    public String toString() {
        return dump(false);
    }

    /**
     * update this row into the PDH
     *
     * @return boolean
     * @param _db
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public boolean updatePdh(Database _db) throws SQLException, MiddlewareException, MiddlewareRequestException {
        return updatePdh(_db, false);
    }

    /**
     * expire this row in the PDH
     *
     * @return boolean
     * @param _db
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public boolean expirePdh(Database _db) throws SQLException, MiddlewareException, MiddlewareRequestException {
        return updatePdh(_db, true);
    }

    /**
     * update/expire
     */
    private boolean updatePdh(Database _db, boolean _bExpire) throws SQLException, MiddlewareException, MiddlewareRequestException {
        boolean bChanged = false;
        EANList eList = new EANList();
        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;
        String strNow = _db.getDates().getNow();
        String strForever = _db.getDates().getForever();
        String strEnterprise = getProfile().getEnterprise();
 
        if (_bExpire && !isNewValue()) {
            //expire ALL nlsid's!!!
            try {
                rs = _db.callGBL7513(new ReturnStatus(-1), strEnterprise, getAttributeCode());
                rdrs = new ReturnDataResultSet(rs);
            } finally {
               	if (rs!=null){
            		rs.close();
            		rs = null;
            	}
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }
            for (int row = 0; row < rdrs.getRowCount(); row++) {
                int iNLSID_exp = rdrs.getColumnInt(row, 0);
                String strHelpVal_exp = rdrs.getColumn(row, 1);
                eList.put(new MetaHelpRow(getProfile(), getAttributeCode(), iNLSID_exp, strNow, strNow, strNow, strNow, 2, strHelpVal_exp));
            }

        } else if (!_bExpire) {
            if (!isNewValue()) {
                //for comp
                MetaHelpValue oMhv_db = new MetaHelpValue((EANMetaAttribute) getParent(), _db, getProfile());
                String strHelpVal_exp = oMhv_db.getHelpValueText();
                String strHelpVal = getHelpValueText();
                //if this has changed -> expire old, update new
                if (!strHelpVal.equals(strHelpVal_exp)) {
                    eList.put(new MetaHelpRow(getProfile(), getAttributeCode(), getNLSID(), strNow, strNow, strNow, strNow, 2, strHelpVal_exp));
                    eList.put(new MetaHelpRow(getProfile(), getAttributeCode(), getNLSID(), strNow, strForever, strNow, strForever, 2, strHelpVal));
                }
            } else {
                if (!getHelpValueText().equals("")) {
                    eList.put(new MetaHelpRow(getProfile(), getAttributeCode(), getNLSID(), strNow, strForever, strNow, strForever, 2, getHelpValueText()));
                }
            }
        }

        //now go through list and perform updates
        for (int i = 0; i < eList.size(); i++) {
            MetaHelpRow oMhRow = (MetaHelpRow) eList.getAt(i);
            oMhRow.updatePdh(_db);
            bChanged = true;
        }
        //it is no longer a new value.
        setNewValue(false);
        return bChanged;

    }

}
