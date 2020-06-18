//
// Copyright (c) 2004, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: Rule51Group.java,v $
// Revision 1.25  2009/05/11 16:45:40  wendy
// Support dereference for memory release
//
// Revision 1.24  2009/01/18 22:07:48  wendy
// Check for rs != null before rs.close()
//
// Revision 1.23  2005/03/10 22:12:07  dave
// Jtest results
//
// Revision 1.22  2005/03/03 19:07:05  gregg
// rule51 addDomainEntityID fix duplicate adding logic
//
// Revision 1.21  2005/03/03 17:56:28  gregg
// debugging rule51
//
// Revision 1.20  2005/01/20 22:57:00  gregg
// cleaning up after the scourge of Rule51
//
// Revision 1.19  2005/01/20 22:05:27  gregg
// derp
//
// Revision 1.18  2005/01/20 21:58:01  gregg
// da da da
//
// Revision 1.17  2005/01/20 21:51:46  gregg
// fix
//
// Revision 1.16  2005/01/20 21:40:37  gregg
// fix
//
// Revision 1.15  2005/01/20 21:31:26  gregg
// fix
//
// Revision 1.14  2005/01/20 21:17:40  gregg
// fixes
//
// Revision 1.13  2005/01/20 21:08:25  gregg
// sum more rule51 for link
//
// Revision 1.12  2005/01/18 21:46:51  dave
// more parm debug cleanup
//
// Revision 1.11  2005/01/18 21:22:44  gregg
// more Rule51 link
//
// Revision 1.10  2005/01/17 21:20:06  gregg
// count rdrs correctly fer 0002
//
// Revision 1.9  2005/01/17 21:15:46  gregg
// close db resources
//
// Revision 1.8  2005/01/17 19:41:39  gregg
// change gbl0002 parms
//
// Revision 1.7  2005/01/17 19:40:39  gregg
// addDomainEntities()
//
// Revision 1.6  2005/01/17 19:32:32  gregg
// ok second pass at Rule51
//
// Revision 1.5  2005/01/17 18:04:35  gregg
// getExceptionMEssage
//
// Revision 1.4  2005/01/17 17:59:49  gregg
// various
//
// Revision 1.3  2005/01/17 17:45:40  gregg
// validate
//
// Revision 1.2  2005/01/16 23:28:31  gregg
// more accessors
//
// Revision 1.1  2005/01/16 23:21:37  gregg
// Rule51
//
//
//

package COM.ibm.eannounce.objects;

import java.util.Vector;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSetGroup;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.rmi.RemoteException;

/**
 * Rule51Group
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Rule51Group extends EANMetaFoundation {

    /**
     *@serial
     */
    final static long serialVersionUID = 1L;

    private String m_strRelType = null; // e.g. a Feature
    private String m_strDomainEntityType = null; // e.g. a Model
    private String m_strAttributeCode = null;
    private String m_strAttributeValue = null;
    private Vector m_vctDomainEIDs = null;
    private int m_iParentEntityID = -1;
    private String m_strParentEntityType = null;
    
    public void dereference(){
    	m_strRelType = null; // e.g. a Feature
        m_strDomainEntityType = null; // e.g. a Model
        m_strAttributeCode = null;
        m_strAttributeValue = null;
        if (m_vctDomainEIDs != null){
        	m_vctDomainEIDs.clear();
        	m_vctDomainEIDs = null;
        }
        m_strParentEntityType = null;
        super.dereference();
    }

    /**
     * Rule51Group
     *
     * @param _prof
     * @param _strParentEntityType
     * @param _strRelType
     * @param _strDomainEntityType
     * @param _strAttributeCode
     * @param _strGroupKey
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    protected Rule51Group(Profile _prof, String _strParentEntityType, String _strRelType, String _strDomainEntityType, String _strAttributeCode, String _strGroupKey) throws MiddlewareRequestException {
        super(null, _prof, _strGroupKey);
        m_strRelType = _strRelType;
        m_strDomainEntityType = _strDomainEntityType;
        m_strAttributeCode = _strAttributeCode;
        m_strParentEntityType = _strParentEntityType;
        m_vctDomainEIDs = new Vector();
    }

    private Rule51Group(Rule51Group _rg) throws MiddlewareRequestException {
        this(_rg.getProfile(), _rg.m_strParentEntityType, _rg.m_strRelType, _rg.m_strDomainEntityType, _rg.m_strAttributeCode, _rg.getKey());
        this.setAttributeValue(_rg.m_strAttributeValue);
        this.m_iParentEntityID = _rg.m_iParentEntityID;
        for (int i = 0; i < _rg.m_vctDomainEIDs.size(); i++) {
            this.addDomainEntityID((Integer) _rg.m_vctDomainEIDs.elementAt(i));
            //this.m_vctDomainEIDs.addElement(_rg.m_vctDomainEIDs.elementAt(i));
        }
    }

    /**
     * setAttributeValue
     *
     * @param _strAttributeValue
     *  @author David Bigelow
     */
    public final void setAttributeValue(String _strAttributeValue) {
        m_strAttributeValue = _strAttributeValue;
    }

    /**
     * getAttributeValue
     *
     * @return
     *  @author David Bigelow
     */
    public final String getAttributeValue() {
        return m_strAttributeValue;
    }

    /**
     * addDomainEntityID
     *
     * @param _iDomainEntityID
     *  @author David Bigelow
     */
    public final void addDomainEntityID(int _iDomainEntityID) {
        addDomainEntityID(new Integer(_iDomainEntityID));
        //m_vctDomainEIDs.addElement(new Integer(_iDomainEntityID));
    }

    /**
     * getDomainEntityIDCount
     *
     * @return
     *  @author David Bigelow
     */
    public final int getDomainEntityIDCount() {
        return m_vctDomainEIDs.size();
    }

    /**
     * getDomainEntityID
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public final int getDomainEntityID(int _i) {
        return ((Integer) m_vctDomainEIDs.elementAt(_i)).intValue();
    }

    /**
     * getRelatorType
     *
     * @return
     *  @author David Bigelow
     */
    public final String getRelatorType() {
        return m_strRelType;
    }

    /**
     * getDomainEntityType
     *
     * @return
     *  @author David Bigelow
     */
    public final String getDomainEntityType() {
        return m_strDomainEntityType;
    }

    /**
     * getAttributeCode
     *
     * @return
     *  @author David Bigelow
     */
    public final String getAttributeCode() {
        return m_strAttributeCode;
    }

    /**
     * setParentEntityID
     *
     * @param _i
     *  @author David Bigelow
     */
    public final void setParentEntityID(int _i) {
        m_iParentEntityID = _i;
    }

    /**
     * getParentEntityID
     *
     * @return
     *  @author David Bigelow
     */
    public final int getParentEntityID() {
        return m_iParentEntityID;
    }

    /**
     * getParentEntityType
     *
     * @return
     *  @author David Bigelow
     */
    public final String getParentEntityType() {
        return m_strParentEntityType;
    }

    /**
     * getExceptionMessage
     *
     * @return
     *  @author David Bigelow
     */
    public final String getExceptionMessage() {
        StringBuffer sb = new StringBuffer("Unique Attribute Rule failed for: ");
        sb.append(dump(false));
        return sb.toString();
    }

    /**
     * Get a unique PartNo String based on all attributecodes, vals.
     *
     * @param _iDomainEID
     * @return String
     */
    public final String getPartNo(int _iDomainEID) {
        StringBuffer sbPartNo = new StringBuffer();
        sbPartNo.append(m_strDomainEntityType + ":");
        sbPartNo.append(_iDomainEID + ":");
        sbPartNo.append(m_strAttributeCode + ":");
        sbPartNo.append(m_strAttributeValue + ":");
        return sbPartNo.toString();
    }

    /**
     * getDomainEntityIDs - get all child entity ids thru specified relator type for specified parent
     * type and id
     *
     * @param _db
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @return
     *  @author David Bigelow
     */
    public Vector getDomainEntityIDs(Database _db) throws SQLException, MiddlewareException {
        ReturnStatus returnStatus = new ReturnStatus(-1);
        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;
        String strEnterprise = getProfile().getEnterprise();
        Vector v = new Vector();
 
        try {
            rs = _db.callGBL0001(returnStatus, strEnterprise, getParentEntityType(), getParentEntityID(), getDomainEntityType(), getRelatorType());
            rdrs = new ReturnDataResultSet(rs);
        } finally {
        	if (rs != null){
        		rs.close();
        		rs = null;
        	}
            _db.commit();
            _db.freeStatement();
            _db.isPending();
        }

        for (int i = 0; i < rdrs.size(); i++) {
            int iEID = rdrs.getColumnInt(i, 0);
            v.addElement(new Integer(iEID));
        }
        return v;
    }

    /**
     * validate
     *
     * @param _db
     * @param _strTag
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @return
     *  @author David Bigelow
     */
    public final boolean validate(Database _db, String _strTag) throws SQLException, MiddlewareException {

        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;
        
        boolean bValid = true;
        int iCount = -1;

        ReturnStatus returnStatus = new ReturnStatus(-1);
        String strEnterprise = getProfile().getEnterprise();
        Vector vctEIDs = getDomainEntityIDs(_db);

        // _strTag is for debugging only
        _db.debug(D.EBUG_DETAIL, "Rule51Group.validate from " + _strTag);

        // first lets grab any other models to check for...
        for (int i = 0; i < vctEIDs.size(); i++) {
            addDomainEntityID((Integer) vctEIDs.elementAt(i));
        }

        for (int i = 0; i < getDomainEntityIDCount(); i++) {
            int iEID = getDomainEntityID(i);
            int iNLSID = getProfile().getReadLanguage().getNLSID();
            try {
                rs = _db.callGBL0002(returnStatus, strEnterprise, getParentEntityID(), getDomainEntityType(), iEID, getRelatorType(), getAttributeCode(), getAttributeValue(), iNLSID);
                rdrs = new ReturnDataResultSet(rs);
            } finally {
            	if (rs != null){
            		rs.close();
            		rs = null;
            	}
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }
            iCount = rdrs.getColumnInt(0, 0);

            _db.debug(D.EBUG_DETAIL, "Rule51Group.validate:0002 count for EID (" + iEID + "):" + iCount);

            if (iCount > 0) {
                // THEN WE FOUND ANOTHER-->FAIL!!!
                bValid = false;
            }
        }

        return bValid;
    }

    /**
     * getAttributeValue
     *
     * @param _db
     * @param _strParentEntityType
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @return
     *  @author David Bigelow
     */
    public final String getAttributeValue(Database _db, String _strParentEntityType) throws SQLException, MiddlewareException {
        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;
        
        ReturnStatus returnStatus = new ReturnStatus(-1);
        String strEnterprise = getProfile().getEnterprise();
        String s = null;
        int iNLSID = getProfile().getReadLanguage().getNLSID();
        
        try {
            rs = _db.callGBL0003(returnStatus, strEnterprise, _strParentEntityType, getParentEntityID(), getAttributeCode(), iNLSID);
            rdrs = new ReturnDataResultSet(rs);
        } finally {
        	if (rs != null){
        		rs.close();
        		rs = null;
        	}
            _db.commit();
            _db.freeStatement();
            _db.isPending();
        }
        if (rdrs.size() > 0) {
            s = rdrs.getColumn(0, 0);
        }
        return s;
    }

    /**
     * getAttributeValue
     *
     * @param _ro
     * @param _strParentEntityType
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException
     * @throws java.rmi.RemoteException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws java.sql.SQLException
     * @return
     *  @author David Bigelow
     */
    public final String getAttributeValue(RemoteDatabaseInterface _ro, String _strParentEntityType) throws MiddlewareBusinessRuleException, RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, SQLException {
        String strEnterprise = getProfile().getEnterprise();
        int iNLSID = getProfile().getReadLanguage().getNLSID();
        String s = null;
        ReturnDataResultSetGroup rdrsg = _ro.remoteGBL0003(strEnterprise, _strParentEntityType, getParentEntityID(), getAttributeCode(), iNLSID);
        if (rdrsg.getResultSetCount() > 0) {
            ReturnDataResultSet rdrs = rdrsg.getResultSet(0);
            if (rdrs.size() > 0) {
                s = rdrs.getColumn(0, 0);
            }
        }
        return s;
    }

    /**
     * Add any Domain Entity IDs into our structure which can be downlinked to. (our Create case).
     *
     * @param _ei 
     */
    protected final void addDomainEntities(EntityItem _ei) {
        for (int i = 0; i < _ei.getDownLinkCount(); i++) {
            EntityItem eiDownRel = (EntityItem) _ei.getDownLink(i);
            if (eiDownRel.getEntityType().equals(getRelatorType())) {
                for (int j = 0; j < eiDownRel.getDownLinkCount(); j++) {
                    EntityItem eiDomain = (EntityItem) eiDownRel.getDownLink(j);
                    if (eiDomain.getEntityType().equals(getDomainEntityType())) {
                        addDomainEntity(eiDomain);
                        //m_vctDomainEIDs.addElement(new Integer(eiDomain.getEntityID()));
                    }
                }
            }
        }
    }

    /**
     * addDomainEntity
     *
     * @param _ei
     *  @author David Bigelow
     */
    protected final void addDomainEntity(EntityItem _ei) {
        Integer oInt = new Integer(_ei.getEntityID());
        addDomainEntityID(oInt);
    }

    private final boolean addDomainEntityID(Integer _oInt) {
        if (m_vctDomainEIDs.contains(_oInt)) {
            System.out.println("Rule51.addDomainEntityID already contains " + _oInt.toString() + ". Doing nothing.");
            return false;
        }
        m_vctDomainEIDs.addElement(_oInt);
        return true;
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _b) {
        StringBuffer sb = new StringBuffer(getKey() + NEW_LINE);
        sb.append(m_strParentEntityType + ":");
        sb.append(m_iParentEntityID + ":");
        sb.append(m_strDomainEntityType + ":");
        for (int i = 0; i < m_vctDomainEIDs.size(); i++) {
            sb.append(m_vctDomainEIDs.elementAt(i) + ",");
        }
        sb.append(m_strAttributeCode + ":");
        sb.append(m_strAttributeValue + ":");
        return sb.toString();
    }

    /**
     * getCopy
     *
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @return
     *  @author David Bigelow
     */
    public Rule51Group getCopy() throws MiddlewareRequestException {
        return new Rule51Group(this);
    }

}
