//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: DescriptionChangeList.java,v $
// Revision 1.7  2005/03/03 21:25:16  dave
// NEW_LINE on EAN Foundation
//
// Revision 1.6  2005/02/14 17:18:33  dave
// more jtest fixing
//
// Revision 1.5  2005/01/18 21:33:09  dave
// removing the debug parms from code (sp internalized them)
//
// Revision 1.4  2002/09/10 23:13:04  dave
// syntax fix
//
// Revision 1.3  2002/09/10 22:58:29  dave
// more syntax changes
//
// Revision 1.2  2002/09/10 22:45:05  dave
// syntax fixes
//
// Revision 1.1  2002/09/10 22:32:56  dave
// adding DescriptionChangeList and Database method
//
//

package COM.ibm.eannounce.objects;

import java.sql.ResultSet;
import java.sql.SQLException;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Profile;


/**
 * DescriptionChangeList
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DescriptionChangeList extends EANMetaEntity {

    /**
     * FIELD
     */
    public static final int FLAG_CHANGES = 0;
  
    private String m_strStartDate;
    private String m_strEndDate;

    /**
     * Main method which performs a simple test of this class
     *
     * @param arg 
     */
    public static void main(String arg[]) {
    }

    /*
    * Version info
    */
    /**
     * (non-Javadoc)
     * getVersion
     *
     * @see COM.ibm.eannounce.objects.EANFoundation#getVersion()
     */
    public String getVersion() {
        return "$Id: DescriptionChangeList.java,v 1.7 2005/03/03 21:25:16 dave Exp $";
    }

    /**
     * This represents an Action Group.  It can only be constructed via a database connection, a Profile, and an Action Item Key*
     *
     * @param _db
     * @param _prof
     * @param i_Type
     * @param _strStartDate
     * @param _strEndDate
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws java.sql.SQLException 
     */
    public DescriptionChangeList(Database _db, Profile _prof, int i_Type, String _strStartDate, String _strEndDate) throws MiddlewareException, SQLException {

        super(null, _prof, _strStartDate + _strEndDate);

        m_strStartDate = _strStartDate;
        m_strEndDate = _strEndDate;

        putLongDescription(FLAG_CHANGES + ":" + m_strStartDate + ":" + m_strEndDate);

        try {

            // Right Now.. everything is of FLAG_CHANGES

            ReturnStatus returnStatus = new ReturnStatus(-1);
            ResultSet rs = null;
            ReturnDataResultSet rdrs = null;

            // Retrieve the Action Class and the Action Description.
            rs = _db.callGBL8008(returnStatus, _prof.getEnterprise(), m_strStartDate, m_strEndDate);
            rdrs = new ReturnDataResultSet(rs);
            rs.close();
            rs = null;
            _db.freeStatement();
            _db.isPending();

            for (int i = 0; i < rdrs.size(); i++) {
                String strTrans = rdrs.getColumn(i, 0);
                String strEntityType = rdrs.getColumn(i, 1);
                String strAttributeCode = rdrs.getColumn(i, 2);
                String strFlagCode = rdrs.getColumn(i, 3);
                int iNLSID = rdrs.getColumnInt(i, 4);
                String strTransDate = rdrs.getColumnDate(i, 5);
                String strNewSDesc = rdrs.getColumn(i, 6);
                String strNewLDesc = rdrs.getColumn(i, 7);
                String strOldSDesc = rdrs.getColumn(i, 8);
                String strOldLDesc = rdrs.getColumn(i, 9);

                _db.debug(D.EBUG_SPEW, "gbl8008:answers" + ":" + strTrans + ":" + strEntityType + ":" + strAttributeCode + ":" + strFlagCode + ":" + iNLSID + ":" + strTransDate + ":" + strNewSDesc + ":" + strNewLDesc + ":" + strOldSDesc + ":" + strOldLDesc);

                putMeta(new DescriptionChangeItem(this, i, FLAG_CHANGES, strTransDate, strTrans, iNLSID, strEntityType, strAttributeCode, strFlagCode, strOldSDesc, strNewSDesc, strOldLDesc, strNewLDesc));

            }

            // We now have everything...

        } catch (Exception x) {
            System.out.println("DescriptionChangeList exception: " + x);
        } finally {
            _db.freeStatement();
            _db.isPending();
        }
    }

    //
    // ACCESSORS
    //

    /**
     * getDescriptionChangeItemCount
     *
     * @return
     *  @author David Bigelow
     */
    public int getDescriptionChangeItemCount() {
        return getMetaCount();
    }

    /**
     * getDescriptionChangeItem
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public DescriptionChangeItem getDescriptionChangeItem(int _i) {
        return (DescriptionChangeItem) getMeta(_i);
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
            strbResult.append("ActionGroup:" + getKey());
            strbResult.append(":ShortDesc:" + getShortDescription());
            strbResult.append(":LongDesc:" + getLongDescription());
            strbResult.append(NEW_LINE + "DescriptionChangeItems:");
            for (int i = 0; i < getDescriptionChangeItemCount(); i++) {
                DescriptionChangeItem dci = getDescriptionChangeItem(i);
                strbResult.append(NEW_LINE + i + ":" + dci.dump(_bBrief));
            }
        }

        return new String(strbResult);

    }

    /**
     * (non-Javadoc)
     * toString
     *
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return getLongDescription();
    }

    //
    // Accessors
    //

    /**
     * getStartDate
     *
     * @return
     *  @author David Bigelow
     */
    public String getStartDate() {
        return m_strStartDate;
    }

    /**
     * getEndDate
     *
     * @return
     *  @author David Bigelow
     */
    public String getEndDate() {
        return m_strEndDate;
    }

}
