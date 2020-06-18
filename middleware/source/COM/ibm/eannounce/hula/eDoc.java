//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: eDoc.java,v $
// Revision 1.34  2012/11/28 17:57:59  wendy
// RCQ00210066-WI Ref CQ00014746 cache vector of entitytypes, not list of entitygroups
//
// Revision 1.33  2009/05/20 01:14:00  wendy
// Use gbl8199x for UI perf
//
// Revision 1.32  2009/05/14 18:24:01  wendy
// UI perf improvement
//
// Revision 1.31  2009/05/14 18:01:02  wendy
// UI perf improvement
//
// Revision 1.30  2006/10/05 19:27:55  roger
// Fix
//
// Revision 1.29  2006/10/05 16:00:29  roger
// Fix
//
// Revision 1.28  2006/10/05 15:44:43  roger
// Log putBlob calls for caching into TCOUNT table
//
// Revision 1.27  2006/02/01 22:27:18  joan
// add condition to see whether filter is needed in sp
//
// Revision 1.26  2006/01/30 22:33:06  joan
// try to add Pass=4 to gbl8104
//
// Revision 1.25  2005/05/13 13:50:21  dave
// changed more to GBL8104
//
// Revision 1.24  2005/05/11 15:41:32  dave
// changing to gbl8104
//
// Revision 1.23  2005/03/03 21:25:15  dave
// NEW_LINE on EAN Foundation
//
// Revision 1.22  2005/02/09 22:13:42  dave
// more JTest Cleanup
//
// Revision 1.21  2005/01/18 21:33:08  dave
// removing the debug parms from code (sp internalized them)
//
// Revision 1.20  2004/10/07 04:19:59  dave
// cleaning up
//
// Revision 1.19  2004/10/01 17:02:01  dave
// found an GBL8102 bug
//
// Revision 1.18  2004/09/30 05:08:46  dave
// reduces spew
//
// Revision 1.17  2004/09/23 02:39:51  dave
// spelling error
//
// Revision 1.16  2004/09/23 02:33:54  dave
// fixing cache item
//
// Revision 1.15  2004/09/23 02:17:35  dave
// more spew
//
// Revision 1.14  2004/09/10 21:03:46  gregg
// change key for etype in putBlob to ai.getKey()+egRoot.getKey()
//
// Revision 1.13  2004/09/10 20:56:52  gregg
// save off EntityGroups
//
// Revision 1.12  2004/08/31 22:50:34  dave
// syntax
//
// Revision 1.11  2004/08/31 22:23:51  dave
// new mass idl based upon static session id
//
// Revision 1.10  2004/08/31 04:20:07  dave
// more refinement
//
// Revision 1.9  2004/08/31 03:50:53  dave
// need to freestatement
//
// Revision 1.8  2004/08/30 22:40:02  dave
// no result set needed
//
// Revision 1.7  2004/08/30 22:19:12  dave
// cinn tax
//
// Revision 1.6  2004/08/30 21:19:51  dave
// new stuff of getting complete image
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
// Revision 1.13  2004/08/09 14:54:44  dave
// 1 should have been 11
//
// Revision 1.12  2004/08/09 14:47:14  dave
// syntax and software only (again) for ECCM
//
// Revision 1.11  2004/08/09 14:41:36  dave
// adding relchildtype and genareacode
//
// Revision 1.10  2004/08/06 20:57:45  dave
// null point catch
//
// Revision 1.9  2004/08/06 20:40:04  dave
// cache key change
//
// Revision 1.8  2004/08/06 20:21:29  dave
// syntax
//
// Revision 1.7  2004/08/06 19:45:23  dave
// expose m_rdrs
//
// Revision 1.6  2004/08/06 18:20:09  dave
// eDoc dump
//
// Revision 1.5  2004/08/06 17:35:02  dave
// more eDoc Syntax
//
// Revision 1.4  2004/08/06 17:27:15  dave
// eDoc Adjustments
//
// Revision 1.3  2004/08/06 17:14:57  dave
// more syntax removal
//
// Revision 1.2  2004/08/06 16:58:45  dave
// eDoc Fixes
//
// Revision 1.1  2004/08/06 16:45:14  dave
// new eDoc class for ECCM EXCELLERATOR
//
//
package COM.ibm.eannounce.hula;

import java.sql.ResultSet;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import COM.ibm.opicmpdh.objects.Blob;
import COM.ibm.opicmpdh.middleware.*;

import COM.ibm.eannounce.objects.EANActionItem;
import COM.ibm.eannounce.objects.EANList;
import COM.ibm.eannounce.objects.EANMetaEntity;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.ExtractActionItem;

// Exceptions
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import java.sql.SQLException;
import java.util.Vector;

/**
* This Object it the top level container for all VE NetChangePackages
*/
public class eDoc extends EANMetaEntity {

    /**
    * @serial
    */
    final static long serialVersionUID = 1L;

    // Here are the things tracked at this level for Parent Information
    private EntityGroup m_egRoot = null;
    private ExtractActionItem m_ai = null;
    private ReturnDataResultSet m_rdrs = null;
    private String m_strStartDate = null;
    private String m_strEndDate = null;
    private int m_iSessionID = 0;

    public void dereference(){
    	if (m_ai!=null){
    		//restore later m_ai.dereference();
    		m_ai = null;
    	}
    	m_rdrs = null;
    	m_strStartDate = null;
    	m_strEndDate = null;
    }
    /**
    * Main method which performs a simple test of this class
    *
    * @param  arg  Description of the Parameter
    */
    public static void main(String arg[]) {
    }

    /**
     * This  will generate fulls for all items in the sessionid
     * It it for one entity only
     *
     * @param _db
     * @param _prof
     * @param _iSessionID
     * @param _ai
     * @param _strEntityType
     * @param _strEndDate
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     */
    public eDoc(Database _db, Profile _prof, int _iSessionID, ExtractActionItem _ai, String _strEntityType, String _strEndDate) throws SQLException, MiddlewareException, MiddlewareRequestException {

        super(null, _prof, _ai.getKey() + _strEntityType);

        try {

            ReturnStatus returnStatus = new ReturnStatus(-1);
            ResultSet rs = null;
            ReturnDataResultSet rdrs = null;
            EntityGroup egRoot = null;
            EANList eltmp = null;

            int iSessionID = _iSessionID;

            String strEntityType = _strEntityType;
            String strEnterprise = getProfile().getEnterprise();
            String strValOn = getProfile().getValOn();
            String strEffOn = getProfile().getEffOn();
            String strRoleCode = getProfile().getRoleCode();
            String strActionItemKey = _ai.getActionItemKey();

            int iNLSID = getProfile().getReadLanguage().getNLSID();

            setRootEntityGroup(new EntityGroup(this, _db, null, _strEntityType, _ai.getPurpose(), false));

            //m_strStartDate = "1980-01-01-00.00.00.000000";
            m_strEndDate = _strEndDate;
            m_strStartDate = _strEndDate;

            // Lets get the parent back for future use
            egRoot = getRootEntityGroup();

            // first.. get a new session id for the Extract transaction...

            setExtractSessionID(iSessionID);

            //
            //  Checking for a Cached EntityGroup list so we do not have to load each one individually
            //
            eltmp = getCachedEntityGroupList(_db, _ai, iNLSID);
            if (eltmp != null) {
                setEntityGroup(eltmp);
            } else {
                // Retrieve the Action Class and the Action Description.
                try {
                    rs = _db.callGBL7004(returnStatus, strEnterprise, strRoleCode, strActionItemKey, strValOn, strEffOn);
                    rdrs = new ReturnDataResultSet(rs);
                } finally {
                	if (rs !=null){
                		rs.close();
                		rs = null;
                	}
                    _db.commit();
                    _db.freeStatement();
                    _db.isPending();
                }
                setSourceActionItem(_ai);
                for (int i = 0; i < rdrs.size(); i++) {
                    String strMyEntityType = rdrs.getColumn(i, 0);
                    EntityGroup eg = getEntityGroup(strMyEntityType);
                    _db.debug(D.EBUG_SPEW, "gbl7004:answer:" + strMyEntityType + ":");
                    if (eg == null) {
                        eg = new EntityGroup(this, _db, getProfile(), strMyEntityType, _ai.getPurpose(), false);
                        putEntityGroup(eg);
                        if (eg.getKey().equals(egRoot.getKey())) {
                            eg.setParentLike(true);
                        }
                    }
                }
                setSourceActionItem(null);
                putCachedEntityGroupList(_db, _ai, iNLSID);
            }

            setSourceActionItem(_ai);
            getSourceActionItem();

            // Now.. pass bypass 1
            try {
                rs = _db.callGBL8104(returnStatus, iSessionID, strEnterprise, strEntityType, strActionItemKey, strRoleCode, m_strStartDate, m_strEndDate, 1, "", -1, 0);
                // Lets load them into a return data result set that contains all our transactions
                m_rdrs = new ReturnDataResultSet(rs);
            } finally {
            	if (rs !=null){
            		rs.close();
            		rs = null;
            	}
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }
            _db.debug(D.EBUG_DETAIL, "gbl8104:recordcount:" + m_rdrs.size());

            for (int i = 0; i < m_rdrs.size(); i++) {
                int y = 0;
                String strGenArea = m_rdrs.getColumn(i, y++);
                String strRootType = m_rdrs.getColumn(i, y++);
                int iRootID = m_rdrs.getColumnInt(i, y++);
                String strRootTran = m_rdrs.getColumn(i, y++);
                String strChildType = m_rdrs.getColumn(i, y++);
                int iChildID = m_rdrs.getColumnInt(i, y++);
                String strChildTran = m_rdrs.getColumn(i, y++);
                int iChildLevel = m_rdrs.getColumnInt(i, y++);
                String strChildClass = m_rdrs.getColumn(i, y++);
                String strChildPath = m_rdrs.getColumn(i, y++);
                String strRelChildType = m_rdrs.getColumn(i, y++);
                int iRelChildID = m_rdrs.getColumnInt(i, y++);
                _db.debug(
                    D.EBUG_SPEW,
                    "gbl8104:answer:"
                        + strGenArea
                        + ":"
                        + strRootType
                        + ":"
                        + iRootID
                        + ":"
                        + strRootTran
                        + ":"
                        + strChildType
                        + ":"
                        + iChildID
                        + ":"
                        + strChildTran
                        + ":"
                        + iChildLevel
                        + ":"
                        + strChildClass
                        + ":"
                        + strChildPath
                        + ":"
                        + strRelChildType
                        + ":"
                        + iRelChildID);
            }

        } finally {
            _db.freeStatement();
            _db.isPending();

        }

    }

    /**
     * This guy just gets a complete image w/o any net change worries
     * It it for one entity only
     *
     * @param _db
     * @param _prof
     * @param _ai
     * @param _strEntityType
     * @param _iEntityID
     * @param _strEndDate
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     */

    public eDoc(Database _db, Profile _prof, ExtractActionItem _ai, String _strEntityType, int _iEntityID, String _strEndDate) throws SQLException, MiddlewareException, MiddlewareRequestException {
        this(_db, _prof, _ai, _strEntityType, _iEntityID, _strEndDate, null, null);
    }

    /**
     * This guy just gets a complete imaage w/o any net change worries
     * It it for one entity only -- tuned for performance
     *
     * @param _db
     * @param _prof
     * @param _ai
     * @param _strEntityType
     * @param _iEntityID
     * @param _strEndDate
     * @param _egRoot
     * @param _elEGLCache
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     */

    protected eDoc(Database _db, Profile _prof, ExtractActionItem _ai, String _strEntityType, int _iEntityID, String _strEndDate, EntityGroup _egRoot, EANList _elEGLCache)
        throws SQLException, MiddlewareException, MiddlewareRequestException {

        super(null, _prof, _ai.getKey() + _strEntityType + _iEntityID);

        try {

            int iSessionID = 1;
            EntityGroup egRoot = null;

            ReturnStatus returnStatus = new ReturnStatus(-1);
            ResultSet rs = null;
            ReturnDataResultSet rdrs = null;

            String strEntityType = _strEntityType;
            String strEnterprise = getProfile().getEnterprise();
            String strValOn = getProfile().getValOn();
            String strEffOn = getProfile().getEffOn();
            String strRoleCode = getProfile().getRoleCode();
            String strActionItemKey = _ai.getActionItemKey();

            int iNLSID = getProfile().getReadLanguage().getNLSID();
            int iEntityID = _iEntityID;

            if (_egRoot == null) {
                _egRoot = new EntityGroup(this, _db, null, _strEntityType, _ai.getPurpose(), false);
            }
            setRootEntityGroup(_egRoot);
            egRoot = getRootEntityGroup();

            //m_strStartDate = "1980-01-01-00.00.00.000000";
            m_strEndDate = _strEndDate;
            m_strStartDate = _strEndDate;

            // first.. get a new session id for the Extract transaction...
            iSessionID = _db.getNewSessionID();
            setExtractSessionID(iSessionID);

            //
            //  Checking for a Cached EntityGroup list so we do not have to load each one individually
            //
            if (_elEGLCache == null) {
                _elEGLCache = getCachedEntityGroupList(_db, _ai, iNLSID);
            }

            if (_elEGLCache != null) {
                setEntityGroup(_elEGLCache);
            } else {
                // Retrieve the Action Class and the Action Description.
                try {
                    rs = _db.callGBL7004(returnStatus, strEnterprise, strRoleCode, strActionItemKey, strValOn, strEffOn);
                    rdrs = new ReturnDataResultSet(rs);
                } finally {
                	if (rs !=null){
                		rs.close();
                		rs = null;
                	}
                    _db.commit();
                    _db.freeStatement();
                    _db.isPending();
                }
                setSourceActionItem(_ai);
                for (int i = 0; i < rdrs.size(); i++) {
                    String strMyEntityType = rdrs.getColumn(i, 0);
                    EntityGroup eg = getEntityGroup(strMyEntityType);
                    _db.debug(D.EBUG_SPEW, "gbl7004:answer:" + strMyEntityType + ":");
                    if (eg == null) {
                        eg = new EntityGroup(this, _db, getProfile(), strMyEntityType, _ai.getPurpose(), false);
                        putEntityGroup(eg);
                        if (eg.getKey().equals(egRoot.getKey())) {
                            eg.setParentLike(true);
                        }
                    }
                }
                setSourceActionItem(null);
                putCachedEntityGroupList(_db, _ai, iNLSID);
            }

            setSourceActionItem(_ai);
            getSourceActionItem();

            _db.test(getRootEntityGroup() != null, "SourceEntityGroup is null");

            // Here we insert record into the trsNetterPass2 table
            //
            _db.callGBL8101(returnStatus, iSessionID, strEnterprise, strEntityType, iEntityID, strActionItemKey, m_strEndDate);

            _db.commit();
            _db.freeStatement();
            _db.isPending();
            //
            // Now.. pass bypass 1
            try {
                rs = _db.callGBL8104(returnStatus, iSessionID, strEnterprise, strEntityType, strActionItemKey, strRoleCode, m_strStartDate, m_strEndDate, 1, "", -1, 0);
                // Lets load them into a return data result set that contains all our transactions
                m_rdrs = new ReturnDataResultSet(rs);
            } finally {
            	if (rs !=null){
            		rs.close();
            		rs = null;
            	}
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }
            _db.debug(D.EBUG_DETAIL, "gbl8104:recordcount:" + m_rdrs.size());

            for (int i = 0; i < m_rdrs.size(); i++) {
                String strGenArea = m_rdrs.getColumn(i, 0);
                String strRootType = m_rdrs.getColumn(i, 1);
                int iRootID = m_rdrs.getColumnInt(i, 2);
                String strRootTran = m_rdrs.getColumn(i, 3);
                String strChildType = m_rdrs.getColumn(i, 4);
                int iChildID = m_rdrs.getColumnInt(i, 5);
                String strChildTran = m_rdrs.getColumn(i, 6);
                int iChildLevel = m_rdrs.getColumnInt(i, 7);
                String strChildClass = m_rdrs.getColumn(i, 8);
                String strChildPath = m_rdrs.getColumn(i, 9);
                String strRelChildType = m_rdrs.getColumn(i, 10);
                int iRelChildID = m_rdrs.getColumnInt(i, 11);
                _db.debug(
                    D.EBUG_SPEW,
                    "gbl8104:answer:"
                        + strGenArea
                        + ":"
                        + strRootType
                        + ":"
                        + iRootID
                        + ":"
                        + strRootTran
                        + ":"
                        + strChildType
                        + ":"
                        + iChildID
                        + ":"
                        + strChildTran
                        + ":"
                        + iChildLevel
                        + ":"
                        + strChildClass
                        + ":"
                        + strChildPath
                        + ":"
                        + strRelChildType
                        + ":"
                        + iRelChildID);
            }

        } finally {

            _db.freeStatement();
            _db.isPending();

        }

    }

    /**
     * eDoc
     *
     * @param _db
     * @param _prof
     * @param _ai
     * @param _strTargetEntityType
     * @param _strStartDate
     * @param _strEndDate
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public eDoc(Database _db, Profile _prof, ExtractActionItem _ai, String _strTargetEntityType, String _strStartDate, String _strEndDate)
        throws SQLException, MiddlewareException, MiddlewareRequestException {

        super(null, _prof, _ai.getKey() + _strTargetEntityType);

        try {

            EntityGroup egRoot = null;
            EANList eltmp = null;

            ReturnStatus returnStatus = new ReturnStatus(-1);
            ResultSet rs = null;
            ReturnDataResultSet rdrs = null;
            String strTargetEntityType = _strTargetEntityType;
            String strEnterprise = getProfile().getEnterprise();
            String strValOn = getProfile().getValOn();
            String strEffOn = getProfile().getEffOn();
            String strRoleCode = getProfile().getRoleCode();
            String strActionItemKey = _ai.getActionItemKey();

            int iNLSID = getProfile().getReadLanguage().getNLSID();
            int iSessionID = _db.getNewSessionID();

            setRootEntityGroup(new EntityGroup(this, _db, null, _strTargetEntityType, _ai.getPurpose(), false));

            m_strStartDate = _strStartDate;
            m_strEndDate = _strEndDate;


            // Lets get the parent back for future use
            egRoot = getRootEntityGroup();

            // first.. get a new session id for the Extract transaction...
            setExtractSessionID(iSessionID);

            //
            //  Checking for a Cached EntityGroup list so we do not have to load each one individually
            //
            eltmp = getCachedEntityGroupList(_db, _ai, iNLSID);
            if (eltmp != null) {
                setEntityGroup(eltmp);
            } else {

                try {
                    rs = _db.callGBL7004(returnStatus, strEnterprise, strRoleCode, strActionItemKey, strValOn, strEffOn);
                    rdrs = new ReturnDataResultSet(rs);
                } finally {
                	if (rs !=null){
                		rs.close();
                		rs = null;
                	}
                    _db.commit();
                    _db.freeStatement();
                    _db.isPending();
                }
                setSourceActionItem(_ai);
                for (int i = 0; i < rdrs.size(); i++) {
                    String strEntityType = rdrs.getColumn(i, 0);
                    EntityGroup eg = getEntityGroup(strEntityType);
                    _db.debug(D.EBUG_SPEW, "gbl7004:answer:" + strEntityType + ":");
                    if (eg == null) {
                        eg = new EntityGroup(this, _db, getProfile(), strEntityType, _ai.getPurpose(), false);
                        putEntityGroup(eg);
                        if (eg.getKey().equals(egRoot.getKey())) {
                            eg.setParentLike(true);
                        }
                    }
                }
                setSourceActionItem(null);
                putCachedEntityGroupList(_db, _ai, iNLSID);
            }

            setSourceActionItem(_ai);
            getSourceActionItem();

            _db.test(getRootEntityGroup() != null, "SourceEntityGroup is null");

            try {
                rs = _db.callGBL8104(returnStatus, iSessionID, strEnterprise, strTargetEntityType, strActionItemKey, strRoleCode, m_strStartDate, m_strEndDate, 0, "", -1, 0);
                // Lets load them into a return data result set that contains all our transactions
                m_rdrs = new ReturnDataResultSet(rs);
            } finally {
            	if (rs !=null){
            		rs.close();
            		rs = null;
            	}
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }
            _db.debug(D.EBUG_DETAIL, "gbl8104:recordcount:" + m_rdrs.size());

            for (int i = 0; i < m_rdrs.size(); i++) {
                String strGenArea = m_rdrs.getColumn(i, 0);
                String strRootType = m_rdrs.getColumn(i, 1);
                int iRootID = m_rdrs.getColumnInt(i, 2);
                String strRootTran = m_rdrs.getColumn(i, 3);
                String strChildType = m_rdrs.getColumn(i, 4);
                int iChildID = m_rdrs.getColumnInt(i, 5);
                String strChildTran = m_rdrs.getColumn(i, 6);
                int iChildLevel = m_rdrs.getColumnInt(i, 7);
                String strChildClass = m_rdrs.getColumn(i, 8);
                String strChildPath = m_rdrs.getColumn(i, 9);
                String strRelChildType = m_rdrs.getColumn(i, 10);
                int iRelChildID = m_rdrs.getColumnInt(i, 11);
                _db.debug(
                    D.EBUG_SPEW,
                    "gbl8104:answer:"
                        + strGenArea
                        + ":"
                        + strRootType
                        + ":"
                        + iRootID
                        + ":"
                        + strRootTran
                        + ":"
                        + strChildType
                        + ":"
                        + iChildID
                        + ":"
                        + strChildTran
                        + ":"
                        + iChildLevel
                        + ":"
                        + strChildClass
                        + ":"
                        + strChildPath
                        + ":"
                        + strRelChildType
                        + ":"
                        + iRelChildID);
            }

        } finally {
            _db.freeStatement();
            _db.isPending();

        }
    }
 
    /**
     * eDoc - used for UI perf improvement
     *
     * @param _db
     * @param _prof
     * @param actionKey
     * @param _strTargetEntityType
     * @param _strStartDate
     * @param _strEndDate
     * @throws SQLException
     * @throws MiddlewareException
     * @throws MiddlewareRequestException
     */
    public eDoc(Database _db, Profile _prof, String strActionItemKey, String _strTargetEntityType, String _strStartDate, String _strEndDate)
        throws SQLException, MiddlewareException, MiddlewareRequestException {

        super(null, _prof, strActionItemKey + _strTargetEntityType);

        try {
            ReturnStatus returnStatus = new ReturnStatus(-1);
            ResultSet rs = null;
            String strEnterprise = getProfile().getEnterprise();
            String strRoleCode = getProfile().getRoleCode();

            int iSessionID = _db.getNewSessionID();
            m_strStartDate = _strStartDate;
            m_strEndDate = _strEndDate;

            // first.. get a new session id for the Extract transaction...
            setExtractSessionID(iSessionID);

            try {
                rs = _db.callGBL8199X(returnStatus, iSessionID, strEnterprise, _strTargetEntityType, strActionItemKey, strRoleCode, m_strStartDate, m_strEndDate, 0, "", -1, 0);
                // Lets load them into a return data result set that contains all our transactions
                m_rdrs = new ReturnDataResultSet(rs);
            } finally {
            	if (rs !=null){
            		rs.close();
            		rs = null;
            	}
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }
            _db.debug(D.EBUG_DETAIL, "gbl8199X:recordcount:" + m_rdrs.size());

            for (int i = 0; i < m_rdrs.size(); i++) {
                String strGenArea = m_rdrs.getColumn(i, 0);
                String strRootType = m_rdrs.getColumn(i, 1);
                int iRootID = m_rdrs.getColumnInt(i, 2);
                String strRootTran = m_rdrs.getColumn(i, 3);
                String strChildType = m_rdrs.getColumn(i, 4);
                int iChildID = m_rdrs.getColumnInt(i, 5);
                String strChildTran = m_rdrs.getColumn(i, 6);
                int iChildLevel = m_rdrs.getColumnInt(i, 7);
                String strChildClass = m_rdrs.getColumn(i, 8);
                String strChildPath = m_rdrs.getColumn(i, 9);
                String strRelChildType = m_rdrs.getColumn(i, 10);
                int iRelChildID = m_rdrs.getColumnInt(i, 11);
                _db.debug(
                    D.EBUG_SPEW,
                    "gbl8199X:answer:"
                        + strGenArea
                        + ":"
                        + strRootType
                        + ":"
                        + iRootID
                        + ":"
                        + strRootTran
                        + ":"
                        + strChildType
                        + ":"
                        + iChildID
                        + ":"
                        + strChildTran
                        + ":"
                        + iChildLevel
                        + ":"
                        + strChildClass
                        + ":"
                        + strChildPath
                        + ":"
                        + strRelChildType
                        + ":"
                        + iRelChildID);
            }

        } finally {
            _db.freeStatement();
            _db.isPending();

        }
    }

    /**
     * setRootEntityGroup
     *
     * @param _eg
     *  @author David Bigelow
     */
    public void setRootEntityGroup(EntityGroup _eg) {
        m_egRoot = _eg;
    }

    /**
     * getRootEntityGroup
     *
     * @return
     *  @author David Bigelow
     */
    public EntityGroup getRootEntityGroup() {
        return m_egRoot;
    }

    /**
     * setExtractSessionID
     *
     * @param _i
     *  @author David Bigelow
     */
    protected void setExtractSessionID(int _i) {
        m_iSessionID = _i;
    }

    /**
     * getExtractSessionID
     *
     * @return
     *  @author David Bigelow
     */
    public int getExtractSessionID() {
        return m_iSessionID;
    }

    /**
     * setSourceActionItem
     *
     * @param _ai
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public void setSourceActionItem(EANActionItem _ai) throws MiddlewareRequestException {
        if (_ai == null) {
            m_ai = null;
        } else if (_ai instanceof ExtractActionItem) {
            m_ai = new ExtractActionItem(this, (ExtractActionItem) _ai);
        } else {
            m_ai = null;
            System.out.println("Source Action is not of ExtractType:" + _ai);
        }
    }

    /**
     * getSourceActionItem
     *
     * @return
     *  @author David Bigelow
     */
    public ExtractActionItem getSourceActionItem() {
        return m_ai;
    }

    //
    // CACHING SECIONT
    //
    private EANList getCachedEntityGroupList(Database _db, EANActionItem _ai, int _iNLSID) {

        String strKey = "ELD." + getProfile().getRoleCode() + _ai.getKey();
        byte[] ba = null;
        ByteArrayInputStream baisObject = null;
        BufferedInputStream bis = null;
        ObjectInputStream oisObject = null;
        EANList el = null;

        _db.debug(D.EBUG_SPEW, "(TESTING) getCachedEntityGroupList() for " + getKey() + " strKey = " + strKey);

        try {

            // go get the blob.. It is NLS Independent right now
            Blob b = _db.getBlobNow(getProfile(), (_ai.getKey() + m_egRoot.getEntityType()), 0, strKey, _iNLSID);
            _db.commit();
            _db.freeStatement();
            _db.isPending();
            ba = b.getBAAttributeValue();
            if (ba == null || ba.length==0) {
                _db.debug(D.EBUG_SPEW, "getCachedEntityGroupList complete");
                return null;
            }

        	//RCQ00210066-WI cant hide the entitygroups in this list any more, get them now
            Vector entityTypeVct = null;
            try {
                baisObject = new ByteArrayInputStream(ba);
                bis = new BufferedInputStream(baisObject, 1000000);
                oisObject = new ObjectInputStream(bis);
                Object obj = oisObject.readObject();
                if(obj instanceof EANList){
                	el = (EANList) obj;
                }else if(obj instanceof Vector){
                	entityTypeVct = (Vector)obj;
                }
            } finally {
            	if (oisObject !=null){
            		try{
            			oisObject.close();
            		} catch (java.io.IOException x) {
            			_db.debug(D.EBUG_DETAIL, "getCachedEntityGroupList: "+strKey+" ERROR failure closing ObjectInputStream "+x);
            		} 
            		oisObject=null;
            	}
            	if(bis!=null){
            		try{
            			bis.close();
            		} catch (java.io.IOException x) {
            			_db.debug(D.EBUG_DETAIL, "getCachedEntityGroupList: "+strKey+" ERROR failure closing BufferedInputStream "+x);
            		} 
            		bis=null;
            	}
            	if (baisObject!=null){
            		try{
            			baisObject.close();
            		} catch (java.io.IOException x) {
            			_db.debug(D.EBUG_DETAIL, "getCachedEntityGroupList: "+strKey+" ERROR failure closing ByteArrayInputStream "+x);
            		} 
            		baisObject=null;
            	}
                ba = null;
            }
            if(el==null && entityTypeVct !=null){
            	el= new EANList();

            	//RCQ00210066-WI cant hide the entitygroups in this list any more, get them now
            	for (int i = 0; i < entityTypeVct.size(); i++) {
            		String strEntityType = entityTypeVct.elementAt(i).toString();
            		_db.debug(D.EBUG_DETAIL, "getCachedEntityGroupList: "+strKey+" loading "+strEntityType);
            		EntityGroup eg = getEntityGroup(strEntityType);
            		if (eg == null) {
            		    eg = new EntityGroup(this, _db, null, strEntityType, _ai.getPurpose(), false);
            		}
            		el.put(eg);
            	}
            	entityTypeVct.clear();
            	entityTypeVct=null;
            }
            return el;

        } catch (Exception x) {
            _db.debug(D.EBUG_ERR, x.getMessage());
            x.printStackTrace();
        } finally {
            _db.freeStatement();
            _db.isPending();
        }

        _db.debug(D.EBUG_SPEW, "getCachedEntityGroupList complete");
        return null;
    }

    private void putCachedEntityGroupList(Database _db, EANActionItem _ai, int _iNLSID) {

    	String strNow = null;
    	String strForever = null;
    	String strKey = "ELD." + getProfile().getRoleCode() + _ai.getKey();

    	byte[] ba = null;
    	ByteArrayOutputStream baosObject = null;
    	ObjectOutputStream oosObject = null;

    	boolean success = false;
    	try {
    		DatePackage dp = _db.getDates();

    		baosObject = new ByteArrayOutputStream();
    		oosObject = new ObjectOutputStream(baosObject);

    		strNow = dp.getNow();
    		strForever = dp.getForever();
    		EANList el = getEntityGroup();
    		Vector entityTypeVct = new Vector(el.size());

    		//RCQ00210066-WI cant hide the entitygroups in this list any more, get them now
    		for (int i = 0; i < el.size(); i++) {
    			EntityGroup eg = getEntityGroup(i);
    			entityTypeVct.add(eg.getEntityType());
    			_db.debug(D.EBUG_DETAIL, "putCachedEntityGroupList: "+strKey+" caching "+eg.getEntityType());
    		}

    		//oosObject.writeObject(getEntityGroup());
    		oosObject.writeObject(entityTypeVct);
    		oosObject.flush();
    		oosObject.reset();

    		ba = new byte[baosObject.size()];
    		ba = baosObject.toByteArray();
    		_db.callGBL9974(new ReturnStatus(-1), _db.getInstanceName(), "pcegl-hula");
    		_db.freeStatement();
    		_db.isPending();
    		_db.putBlob(getProfile(), (_ai.getKey() + m_egRoot.getEntityType()), 0, strKey, "CACHE", strNow, strForever, ba, _iNLSID);
    		success=true;
    	} catch (Exception x) {
    		_db.debug(D.EBUG_ERR, "putCachedEntityGroupList: "+strKey+" ERROR "+x.getMessage());
    		x.printStackTrace();

    	} finally {
    		try{
    			if(success){
    				_db.commit();
    			}else{
    				_db.rollback();
    			}
    		}catch(Exception e) {}
    		if(oosObject!=null){
    			try{
    				oosObject.close();
    			} catch (java.io.IOException x) {
    				_db.debug(D.EBUG_DETAIL, "putCachedEntityGroupList: "+strKey+" ERROR failure closing ObjectOutputStream "+x);
    			} 
    			oosObject=null;
    		}
    		if(baosObject!=null){
    			try{
    				baosObject.close();
    			} catch (java.io.IOException x) {
    				_db.debug(D.EBUG_DETAIL, "putCachedEntityGroupList: "+strKey+" ERROR failure closing ByteArrayOutputStream "+x);
    			} 
    			baosObject=null;
    		}
    		_db.freeStatement();
    		_db.isPending();
    	}
    }

    //
    //  Entity Group Methods
    //
    /**
     * getEntityGroup
     *
     * @param _str
     * @return
     *  @author David Bigelow
     */
    public EntityGroup getEntityGroup(String _str) {
        return (EntityGroup) getData(_str);
    }
    /**
     * getEntityGroup
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public EntityGroup getEntityGroup(int _i) {
        return (EntityGroup) getData(_i);
    }
    /**
     * setEntityGroup
     *
     * @param _eanList
     *  @author David Bigelow
     */
    protected void setEntityGroup(EANList _eanList) {
        setData(_eanList);
        for (int i = 0; i < getEntityGroupCount(); i++) {
            EntityGroup eg = getEntityGroup(i);
            eg.setParent(this);
        }
    }
    /**
     * removeEntityGroup
     *
     * @param _eg
     * @return
     *  @author David Bigelow
     */
    public EntityGroup removeEntityGroup(EntityGroup _eg) {
        return (EntityGroup) removeData(_eg);
    }
    /**
     * putEntityGroup
     *
     * @param _eg
     *  @author David Bigelow
     */
    public void putEntityGroup(EntityGroup _eg) {
        putData(_eg);
        _eg.setParent(this);
    }
    /**
     * getEntityGroupCount
     *
     * @return
     *  @author David Bigelow
     */
    public int getEntityGroupCount() {
        return getDataCount();
    }

    /**
     * getEntityGroup
     *
     * @return
     *  @author David Bigelow
     */
    public EANList getEntityGroup() {
        return getData();
    }

    /**
     * getTransactions
     *
     * @return
     *  @author David Bigelow
     */
    public ReturnDataResultSet getTransactions() {
        return m_rdrs;
    }

    /**
     * getVersion
     *
     * @return String
     *  @author David Bigelow
     */
    public String getVersion() {
        return "$Id: eDoc.java,v 1.34 2012/11/28 17:57:59 wendy Exp $";
    }

    /**
     * dump
     * @param _bBrief
     * @return String
     *  @author David Bigelow
     */
    public String dump(boolean _bBrief) {
        StringBuffer strbResult = new StringBuffer();
        for (int i = 0; i < m_rdrs.size(); i++) {
            String strRootType = m_rdrs.getColumn(i, 0);
            int iRootID = m_rdrs.getColumnInt(i, 1);
            String strRootTran = m_rdrs.getColumn(i, 2);
            String strChildType = m_rdrs.getColumn(i, 3);
            int iChildID = m_rdrs.getColumnInt(i, 4);
            String strChildTran = m_rdrs.getColumn(i, 5);
            int iChildLevel = m_rdrs.getColumnInt(i, 6);
            String strChildClass = m_rdrs.getColumn(i, 7);
            String strChildPath = m_rdrs.getColumn(i, 8);
            strbResult.append(
                "Row:"
                    + i
                    + ":"
                    + strRootType
                    + ":"
                    + iRootID
                    + ":"
                    + strRootTran
                    + ":"
                    + strChildType
                    + ":"
                    + iChildID
                    + ":"
                    + strChildTran
                    + ":"
                    + iChildLevel
                    + ":"
                    + strChildClass
                    + ":"
                    + strChildPath
                    + NEW_LINE);
        }
        return strbResult.toString();
    }

}
