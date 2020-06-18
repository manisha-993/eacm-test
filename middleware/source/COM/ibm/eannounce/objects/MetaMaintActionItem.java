// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
// $Log: MetaMaintActionItem.java,v $
// Revision 1.15  2009/05/11 15:46:05  wendy
// Support dereference for memory release
//
// Revision 1.14  2009/01/12 13:14:55  wendy
// Check for rs != null before rs.close()
//
// Revision 1.13  2008/02/01 22:10:05  wendy
// Cleanup RSA warnings
//
// Revision 1.12  2007/06/04 18:32:30  wendy
// RQ110306297 support max length rule on long description for flags
//
// Revision 1.11  2005/09/08 18:12:25  joan
// add testing rule for meta maintenance
//
// Revision 1.10  2005/03/24 18:48:54  joan
// fixes
//
// Revision 1.9  2005/03/24 18:47:18  joan
// fix compile
//
// Revision 1.8  2005/03/24 18:30:54  joan
// work on flag maintenance
//
// Revision 1.7  2005/03/24 00:02:40  joan
// work on flag
//
// Revision 1.6  2005/03/08 23:15:46  dave
// Jtest checkins from today and last ngith
//
// Revision 1.5  2005/03/07 23:09:52  joan
// work on flag maintenance
//
// Revision 1.4  2005/03/02 20:46:22  joan
// fixes
//
// Revision 1.3  2005/03/01 23:51:30  joan
// fixes
//
// Revision 1.2  2005/03/01 23:46:20  joan
// fix compile
//
// Revision 1.1  2005/03/01 23:39:20  joan
// work on Meta Maintenance
//
//

package COM.ibm.eannounce.objects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.rmi.RemoteException;

import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.T;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.LockException;
import COM.ibm.opicmpdh.transactions.OPICMList;


/**
 *  Description of the Class
 *
 * @author
 * @created    April 18, 2003
 */
public class MetaMaintActionItem extends EANActionItem {

    final static long serialVersionUID = 20011106L;

    private String m_strEntityType = null;
    private OPICMList m_attrCodeList = new OPICMList();
    private OPICMList m_attrMaxList = new OPICMList(); //RQ110306297
    private OPICMList m_autoFillList = new OPICMList();

    protected void dereference(){
    	super.dereference();
    	m_strEntityType = null;
    	if (m_attrCodeList != null){
    		m_attrCodeList.clear();
    		m_attrCodeList = null;
    	}
    	if (m_attrMaxList != null){
    		m_attrMaxList.clear();
    		m_attrMaxList = null;
    	}
    	if (m_autoFillList != null){
    		m_autoFillList.clear();
    		m_autoFillList = null;
    	} 
    }
    /**
     * Main method which performs a simple test of this class
     *
     * @param  arg  Description of the Parameter
     */
    public static void main(String arg[]) {
    }

    /*
     *  Version info
     */
    /**
     *  Gets the version attribute of the MetaMaintActionItem object
     *
     * @return    The version value
     */
    public String getVersion() {
        return "$Id: MetaMaintActionItem.java,v 1.15 2009/05/11 15:46:05 wendy Exp $";
    }

    /**
       *Constructor for the MetaMaintActionItem object
       *
       * @param  _ai                             Description of the Parameter
       * @exception  MiddlewareRequestException  Description of the Exception
       */
    public MetaMaintActionItem(MetaMaintActionItem _ai) throws MiddlewareRequestException {
        super(_ai);
        setEntityType(_ai.getEntityType());
        setAttrCodeList(_ai.getAttrCodeList());
        setAutoFillList(_ai.getAutoFillList());
        setAttrMaxList(_ai.getAttrMaxList()); // RQ110306297
    }

    /**
     *Constructor for the MetaMaintActionItem object
     *
     * @param  _mf                             Description of the Parameter
     * @param  _ai                             Description of the Parameter
     * @exception  MiddlewareRequestException  Description of the Exception
     */
    public MetaMaintActionItem(EANMetaFoundation _mf, MetaMaintActionItem _ai) throws MiddlewareRequestException {
        super(_mf, _ai);
        setEntityType(_ai.getEntityType());
        setAutoFillList(_ai.getAutoFillList());
    }

    /**
     * This represents a MetaMaintActionItem.  It can only be constructed via a database connection, a Profile, and an Action Item Key
     *
     * @param  _emf                            Description of the Parameter
     * @param  _db                             Description of the Parameter
     * @param  _prof                           Description of the Parameter
     * @param  _strActionItemKey               Description of the Parameter
     * @exception  SQLException                Description of the Exception
     * @exception  MiddlewareException         Description of the Exception
     * @exception  MiddlewareRequestException  Description of the Exception
     */
    public MetaMaintActionItem(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {

        super(_emf, _db, _prof, _strActionItemKey);

        // Lets go get the information pertinent to the WhereUsed Action Item

        try {
            ReturnStatus returnStatus = new ReturnStatus(-1);
            ResultSet rs = null;
            ReturnDataResultSet rdrs;
            Profile prof = getProfile();

            rs = _db.callGBL7030(returnStatus, prof.getEnterprise(), _strActionItemKey, prof.getValOn(), prof.getEffOn());
            rdrs = new ReturnDataResultSet(rs);
            rs.close();
            rs = null;
            _db.commit();
            _db.freeStatement();
            _db.isPending();

            // Set the class and description...
            for (int ii = 0; ii < rdrs.size(); ii++) {
                String strType = rdrs.getColumn(ii, 0);
                String strCode = rdrs.getColumn(ii, 1);
                String strValue = rdrs.getColumn(ii, 2);

                _db.debug(D.EBUG_SPEW, "gbl7030 answer is:" + strType + ":" + strCode + ":" + strValue + ":");

                // Collect the attributes
                if (strType.equals("TYPE") && strCode.equals("EntityType")) {
                    setEntityType(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("Attribute")) {
                    m_attrCodeList.put(strValue, strValue);
                } else if (strType.equals("TYPE") && strCode.equals("HideDefault")) {
                    m_autoFillList.put(strValue, strValue);
                } else {
                    _db.debug(D.EBUG_ERR, "*** ACTION ITEM ATTRIBUTE *** No home for this Action/Attribute" + strType + ":" + strCode + ":" + strValue);
                }
            }

			//RQ110306297 find any max rule for long description
			for (int i=0; i<m_attrCodeList.size(); i++){
				String attrCode = m_attrCodeList.getAt(i).toString();
                try {
                    // Get the rules
                    rs = _db.callGBL8611(returnStatus, prof.getEnterprise().trim(), attrCode, prof.getValOn(), prof.getEffOn());
                    rdrs = new ReturnDataResultSet(rs);
                    for (int ii = 0; ii < rdrs.size(); ii++) {
                        String s1 = rdrs.getColumn(ii, 0);
                        String s2 = rdrs.getColumn(ii, 1);
                        String s3 = rdrs.getColumn(ii, 2);

                        _db.debug(D.EBUG_SPEW, "gbl8611:answer:" + s1 + ":" + s2 + ":" + s3);

                        // Get all the rules....
                        if (s1.equals("MAX")) {
							m_attrMaxList.put(attrCode,s3);
                        }
                    }
                } finally {
                	if (rs != null){
                		rs.close();
                    	rs = null;
                	}
                    _db.commit();
                    _db.freeStatement();
                    _db.isPending();
                }
			}
        } finally {
            _db.freeStatement();
            _db.isPending();
        }
    }

    /**
     *  Description of the Method
     *
     * @param  _bBrief  Description of the Parameter
     * @return          Description of the Return Value
     */
    public String dump(boolean _bBrief) {
        StringBuffer strbResult = new StringBuffer();
        strbResult.append("MetaMaintActionItem:" + getKey() + ":desc:" + getLongDescription());
        strbResult.append(":purpose:" + getPurpose());
        strbResult.append(":entitytype:" + getEntityType() + "\n");
        return strbResult.toString();
    }

    /**
     *  Gets the purpose attribute of the MetaMaintActionItem object
     *
     * @return    The purpose value
     */
    public String getPurpose() {
        return "WhereUsed";
    }

    /**
     *  Gets the entityType attribute of the MetaMaintActionItem object
     *
     * @return    The entityType value
     */
    public String getEntityType() {
        return m_strEntityType;
    }

    /**
     *  Sets the entityType attribute of the MetaMaintActionItem object
     *
     * @param  _str  The new entityType value
     */
    protected void setEntityType(String _str) {
        m_strEntityType = _str;
    }

    /**
     *  Gets the attribute code list attribute of the MetaMaintActionItem object
     *
     * @return    The OPICMList value
     */
    public OPICMList getAttrCodeList() {
        return m_attrCodeList;
    }

    /**
     *  Sets the attribute code list attribute of the MetaMaintActionItem object
     *
     * @param  _str  The new OPICMList value
     */
    protected void setAttrCodeList(OPICMList _list) {
        m_attrCodeList = _list;
    }

    /** RQ110306297
     *  Gets the max length attribute list of the MetaMaintActionItem object
     *
     * @return    The OPICMList value
     */
    public OPICMList getAttrMaxList() {
        return m_attrMaxList;
    }

    /** RQ110306297
     *  Sets the max length attribute list of the MetaMaintActionItem object
     *
     * @param  _str  The new OPICMList value
     */
    protected void setAttrMaxList(OPICMList _list) {
        m_attrMaxList = _list;
    }

    /** RQ110306297
     * Get the max length for the long description for this attribute
     * @return int
     */
    public int getMaxLength(EANMetaAttribute _ma) {
		int maxlen = 0;
        if (_ma != null && m_attrMaxList.get(_ma.getKey()) != null) {
			maxlen = Integer.parseInt(m_attrMaxList.get(_ma.getKey()).toString());
        }
        return maxlen;
    }

    /**
     *  Gets the attribute code list attribute of the MetaMaintActionItem object
     *
     * @return    The OPICMList value
     */
    public OPICMList getAutoFillList() {
        return m_autoFillList;
    }

    /**
     *  Sets the attribute code list attribute of the MetaMaintActionItem object
     *
     * @param  _str  The new OPICMList value
     */
    protected void setAutoFillList(OPICMList _list) {
        m_autoFillList = _list;
    }

    /**
     *
     * @return    The boolean value
     */
    public boolean canMaintenance(EANMetaAttribute _ma) {
        if (_ma != null && m_attrCodeList.get(_ma.getKey()) != null) {
            return true;
        }
        return false;
    }


    /**
     *
     * @return    The boolean value
     */
    public boolean canAutoFill(String _strAttributeCode) {
        if (_strAttributeCode != null && m_autoFillList.get(_strAttributeCode) != null) {
            return true;
        }
        return false;
    }

    /**
     *  Description of the Method
     *
     * @param  _db                                        Description of the Parameter
     * @param  _prof                                      Description of the Parameter
     * @return                                            Description of the Return Value
     * @exception  SQLException                           Description of the Exception
     * @exception  MiddlewareException                    Description of the Exception
     * @exception  MiddlewareRequestException             Description of the Exception
     * @exception  LockException                          Description of the Exception
     * @exception  MiddlewareShutdownInProgressException  Description of the Exception
     * @exception  RemoteException                        Description of the Exception
     */
    public MetaFlagMaintList exec(Database _db, Profile _prof, EANMetaAttribute _ma) throws SQLException, MiddlewareException, MiddlewareRequestException, LockException, MiddlewareShutdownInProgressException, RemoteException {
        //String strTraceBase = "MetaMaintActionItem exec method";
        T.est(_ma != null, "Meta attribute is null.");

        T.est(m_attrCodeList.get(_ma.getKey()) != null, "Not in the flag maintenance list: " + _ma.getKey());

        MetaFlagMaintList ml = new MetaFlagMaintList(this, _db, _prof, _ma.getKey());
        ml.putLongDescription(_ma.getActualLongDescription());
        ml.setMetaFlag(_ma);
        return ml;
    }

    /**
     *  Description of the Method
     *
     * @param  _rdi                                       Description of the Parameter
     * @param  _prof                                      Description of the Parameter
     * @return                                            Description of the Return Value
     * @exception  SQLException                           Description of the Exception
     * @exception  MiddlewareRequestException             Description of the Exception
     * @exception  MiddlewareShutdownInProgressException  Description of the Exception
     * @exception  MiddlewareException                    Description of the Exception
     * @exception  RemoteException                        Description of the Exception
     */
    public MetaFlagMaintList rexec(RemoteDatabaseInterface _rdi, Profile _prof, EANMetaAttribute _ma) throws SQLException, MiddlewareRequestException, MiddlewareShutdownInProgressException, MiddlewareException, RemoteException {
       // String strTraceBase = "MetaMaintActionItem rexec method";
        T.est(_ma != null, "Meta attribute is null.");
        T.est(m_attrCodeList.get(_ma.getKey()) != null, "Not in the flag maintenance list: " + _ma.getKey());
        MetaFlagMaintList ml = _rdi.getMetaFlagMaintList(this, _prof, _ma.getKey());
        ml.putLongDescription(_ma.getActualLongDescription());
        ml.setMetaFlag(_ma);
        return ml;
    }

    /**
     *  Description of the Method
     *
     * @param  _db                                        Description of the Parameter
     * @param  _prof                                      Description of the Parameter
     * @return                                            Description of the Return Value
     * @exception  SQLException                           Description of the Exception
     * @exception  MiddlewareException                    Description of the Exception
     * @exception  MiddlewareShutdownInProgressException  Description of the Exception
     */
    public MetaFlagMaintList executeAction(Database _db, Profile _prof, String _strAttributeCode) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
        _db.debug(D.EBUG_DETAIL, "MetaMaintActionItem.executeAction method");
        return new MetaFlagMaintList(this, _db, _prof, _strAttributeCode);
    }

    /**
     * @param  _db                      Description of the Parameter
     * @param  _bExpire                 Description of the Parameter
     * @return                          true if successful, false if nothing to update or unsuccessful
     * @exception  MiddlewareException  Description of the Exception
     */
    protected boolean updatePdhMeta(Database _db, boolean _bExpire) throws MiddlewareException {
        return false;
    }

    /**
     * to simplify things a bit
     */
    /*private void updateActionAttribute(Database _db, boolean _bExpire, String _strLinkType2, String _strLinkCode, String _strLinkValue) throws MiddlewareException {
    }*/

}
