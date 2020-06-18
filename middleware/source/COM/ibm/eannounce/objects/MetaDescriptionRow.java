//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: MetaDescriptionRow.java,v $
// Revision 1.16  2005/03/07 21:10:26  dave
// Jtest cleanup
//
// Revision 1.15  2004/01/28 00:05:13  gregg
// ensuring trims b4 inserts
//
// Revision 1.14  2003/03/14 17:54:18  gregg
// remove remote pdh update method from MetaRow class - instead update using db through remote interface
//
// Revision 1.13  2003/03/12 20:52:49  gregg
// add updatePdhRemote method to ALL MetaRows
//
// Revision 1.12  2002/11/06 22:41:57  gregg
// removing display statements
//
// Revision 1.11  2002/10/09 20:31:16  gregg
// remove logic that automatically inserts to nls=1
//
// Revision 1.10  2002/10/09 20:05:21  gregg
// compile error fix
//
// Revision 1.9  2002/10/09 19:55:25  gregg
// ensure that an english record exists (nlsid = 1). if not -> add this record.
//
// Revision 1.8  2002/08/23 22:15:19  gregg
// updatePdhMeta method - only throw MiddlewareException
//
// Revision 1.7  2002/04/11 20:10:36  gregg
// free db stmt
//
// Revision 1.6  2002/03/29 17:30:27  gregg
// made class final
//
// Revision 1.5  2002/03/12 23:23:53  gregg
// we are now updating to database
//
// Revision 1.4  2002/03/08 23:53:07  gregg
// use _NLSID in key
//
// Revision 1.3  2002/03/07 21:30:11  gregg
// nlsID must be explicitely set
//
// Revision 1.2  2002/03/07 18:24:14  gregg
// key = pk for MetaDescription table
//
// Revision 1.1  2002/03/01 18:28:11  gregg
// initial load
//
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.Profile;
import java.sql.SQLException;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.ReturnStatus;

/**
* This is a container that holds all the information representing one Row in the MetaDescription Table
*
*/
public final class MetaDescriptionRow extends MetaRow {

    /**
    * @serial
    */
    static final long serialVersionUID = 1L;

    private String m_strDescriptionClass = null;
    private String m_strDescriptionType = null;
    private int m_iNLSID = 1;

    /**
     * MetaDescriptionRow
     *
     * @param _prof
     * @param _strDescType
     * @param _strDescClass
     * @param _strShortDescription
     * @param _strLongDescription
     * @param _iNLSID
     * @param _strValFrom
     * @param _strValTo
     * @param _strEffFrom
     * @param _strEffTo
     * @param _iTranId
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public MetaDescriptionRow(Profile _prof, String _strDescType, String _strDescClass, String _strShortDescription, String _strLongDescription, int _iNLSID, String _strValFrom, String _strValTo, String _strEffFrom, String _strEffTo, int _iTranId) throws MiddlewareRequestException {
        super(_prof, _strDescType + _strDescClass + _iNLSID + _prof.getEnterprise() + _strValFrom + _strEffFrom //key = pk for MetaDescription table
            , _strValFrom, _strValTo, _strEffFrom, _strEffTo, _iTranId);
        setDescriptionClass(_strDescClass);
        setShortDescription(_strShortDescription);
        setLongDescription(_strLongDescription);
        setDescriptionType(_strDescType);
        setNLSID(_iNLSID);
        return;
    }

    /**
     * Accessor
     *
     * @return String
     */
    public String getDescriptionType() {
        return m_strDescriptionType;
    }

    /**
     * Mutator
     *
     * @param _s 
     */
    public void setDescriptionType(String _s) {
        m_strDescriptionType = _s;
        return;
    }

    /**
     * Accessor
     *
     * @return String
     */
    public String getDescriptionClass() {
        return m_strDescriptionClass;
    }

    /**
     * Mutator
     *
     * @param _s 
     */
    public void setDescriptionClass(String _s) {
        m_strDescriptionClass = _s;
        return;
    }

    /**
     * Mutator
     *
     * @param _s 
     */
    public void setShortDescription(String _s) {
        putShortDescription(getNLSID(), _s);
        return;
    }

    /**
     * Mutator
     *
     * @param _s 
     */
    public void setLongDescription(String _s) {
        putLongDescription(getNLSID(), _s);
        return;
    }

    /**
     * Mutator
     *
     * @param _i 
     */
    public void setNLSID(int _i) {
        m_iNLSID = _i;
    }

    /**
     * Accessor
     *
     * @return int
     */
    public int getNLSID() {
        return m_iNLSID;
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _brief) {
        StringBuffer strBuf = new StringBuffer(" [");
        strBuf.append(getEnterprise() + ",");
        strBuf.append(getDescriptionType() + ",");
        strBuf.append(getDescriptionClass() + ",");
        strBuf.append(getShortDescription() + ",");
        strBuf.append(getLongDescription() + ",");
        strBuf.append(getNLSID() + ",");
        strBuf.append(getValFrom() + ",");
        strBuf.append(getValTo() + ",");
        strBuf.append(getEffFrom() + ",");
        strBuf.append(getEffTo() + ",");
        strBuf.append(getOPWGID() + ",");
        strBuf.append(getTranID() + "]");
        return strBuf.toString();
    }

    /**
     * Update the corresponding row int the PDH
     *
     * @param _db
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException 
     */
    public void updatePdh(Database _db) throws MiddlewareException {
        _db.debug(D.EBUG_SPEW, "(UPDATE) MetaDescriptionRow: " + dump(false));

        try {
            String s1 = getEnterprise().trim();
            String s2 = getDescriptionType().trim();
            String s3 = getDescriptionClass().trim();
            String s4 = getShortDescription().trim();
            String s5 = getLongDescription().trim();
            _db.debug(D.EBUG_SPEW, "(UPDATE) " + s1 + " length == " + s1);
            _db.debug(D.EBUG_SPEW, "(UPDATE) " + s2 + " length == " + s2);
            _db.debug(D.EBUG_SPEW, "(UPDATE) " + s3 + " length == " + s3);
            _db.debug(D.EBUG_SPEW, "(UPDATE) " + s4 + " length == " + s4);
            _db.debug(D.EBUG_SPEW, "(UPDATE) " + s5 + " length == " + s5);
            _db.callGBL2909(new ReturnStatus(-1), getOPWGID(), s1, s2, s3, s4, s5, getNLSID(), getTranID(), getEffFrom(), getEffTo());
            try {
                _db.commit();
            } catch (SQLException ex) {
                _db.debug("MetaDescriptionRow.updatePhd.Trouble in commit." + ex.getMessage());
            }
            _db.freeStatement();
            _db.isPending();

        } finally {
            _db.freeStatement();
            _db.isPending();
        }
        return;
    }

    /**
     * Get the version Id - generated by CVS
     *
     * @return String
     */
    public String getVersion() {
        return "$Id: MetaDescriptionRow.java,v 1.16 2005/03/07 21:10:26 dave Exp $";
    }

}
