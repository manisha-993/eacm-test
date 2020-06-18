//
// Copyright (c) 2002, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//

//
//$Log: MetaCacheManager.java,v $
//Revision 1.19  2012/11/08 21:52:48  wendy
//rollback if error instead of commit
//
//Revision 1.18  2008/02/01 22:10:08  wendy
//Cleanup RSA warnings
//
//Revision 1.17  2005/03/04 22:40:10  dave
//Jtest
//
//Revision 1.16  2005/01/18 21:46:50  dave
//more parm debug cleanup
//
//Revision 1.15  2004/03/17 19:12:01  joan
//add method to rebuild all EG
//
//Revision 1.14  2004/03/16 19:06:29  joan
//add method for rebuilt Cache
//
//Revision 1.13  2004/01/28 19:36:17  gregg
//more expireALLCache
//
//Revision 1.12  2004/01/28 18:34:11  gregg
//expireALLCaches method
//
//Revision 1.11  2002/11/11 19:30:02  gregg
//putCache(db,eg) method for EntityGroup
//
//Revision 1.10  2002/11/06 22:40:40  gregg
//removing display statements
//
//Revision 1.9  2002/10/22 17:20:01  gregg
//expireAllEntityGroupCacheContaining method
//
//Revision 1.8  2002/09/25 17:59:01  gregg
//reset Assoc classes as well
//
//Revision 1.7  2002/09/25 17:39:39  gregg
//in expireALLEntityGroupCaches method: we need to check EntityClass=Relator also (there are still some of these floating around)
//
//Revision 1.6  2002/09/25 17:23:57  gregg
//some rearranging of methods (no longer focus on refreshing by purpose)
//also: expireALLEntityGroupCaches method
//
//Revision 1.5  2002/07/25 17:11:48  gregg
//some debug stmts
//
//Revision 1.4  2002/07/24 23:39:36  gregg
//some cache management for entitygroups
//
//Revision 1.3  2002/05/02 22:27:04  gregg
//fix method signatures
//
//Revision 1.2  2002/05/02 22:18:59  gregg
//cache key stuff for Extract
//
//Revision 1.1  2002/04/16 17:28:13  gregg
//initial load
//
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.DatePackage;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.Stopwatch;
import java.sql.ResultSet;
import java.sql.SQLException;
 
/**
 * Provides methods to reset the meta caches for Cacheable objects (currently implemented only for EntityGroups)
 */
public class MetaCacheManager {

    private Profile m_prof = null;

    /**
     * MetaCacheManager
     *
     * @param _prof
     *  @author David Bigelow
     */
    public MetaCacheManager(Profile _prof) {
        m_prof = _prof;
    }

    ////////////////////////////////////////////////////////////////
    /////  General
    ////////////////////////////////////////////////////////////////

    /**
     * call the Cacheable object's expireCache method
     *
     * @param _db
     * @param _objCache 
     */
    public void expireCache(Database _db, EANCacheable _objCache) {
        _objCache.expireCache(_db);
    }

    /////////////////////////////////////
    ////////   EntityGroup
    /////////////////////////////////////

    /**
     * update EntityGroup's cache for this Role,NLS,Purpose
     *
     * @param _db
     * @param _eg 
     */
    public void putCache(Database _db, EntityGroup _eg) {
        _eg.putCache(_db);
    }

    /**
     * expire an entityGroup's cache
     *
     * @param _db
     * @param _strEntityType
     * @param _strPurpose
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public void expireEGCacheByPurpose(Database _db, String _strEntityType, String _strPurpose) throws SQLException, MiddlewareException, MiddlewareRequestException {
        _db.deactivateBlob(getProfile(), _strEntityType, 0, EntityGroup.getCacheKey(getProfile(), _strPurpose), getProfile().getReadLanguage().getNLSID());
    }

    /**
     * Reset ALL caches by purpose (Role sensitive)
     *
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @param _db
     * @param _strPurpose 
     */
    public void resetAllEGCachesByRolePurpose(Database _db, String _strPurpose) throws SQLException, MiddlewareException, MiddlewareRequestException {

        try {
            String strNow = _db.getDates().getNow();
            //get all entities for role
            ResultSet rs = null;
            ReturnDataResultSet rdrs = null;
            boolean success = false;
            try {
                rs = _db.callGBL7501(new ReturnStatus(-1), getProfile().getEnterprise(), getProfile().getRoleCode(), strNow, strNow);
                rdrs = new ReturnDataResultSet(rs);
                success = true;
            } finally {
              	if(rs != null){
            		try{
            			rs.close();
            		}catch(SQLException x){
            			_db.debug(D.EBUG_DETAIL, "MetaCacheManager.resetAllEGCachesByRolePurpose : ERROR failure closing ResultSet "+x);
            		}
            		rs = null;
            	}
            	
              	if(success){
              		_db.commit();
              	}else{
              		_db.rollback();
              	}
         
                _db.freeStatement();
                _db.isPending();
            }
            for (int row = 0; row < rdrs.getRowCount(); row++) {
                String strEntType = rdrs.getColumn(row, 0);
                expireEGCacheByPurpose(_db, strEntType, _strPurpose);
            }
        } finally {
            _db.freeStatement();
            _db.isPending();
        }
    }

    /**
     * expire an entityGroup's caches for ALL roles and ALL nls and ALL purposes -- every entity!!
     *
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @param _db
     * @param _strEntityType 
     */
    public void expireEGCacheAllRolesAllNls(Database _db, String _strEntityType) throws SQLException, MiddlewareException, MiddlewareRequestException {
        
        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;
        
        String strNow = _db.getDates().getNow();
        boolean success = false;
        try {
            rs = _db.callGBL7521(new ReturnStatus(-1), getProfile().getEnterprise(), _strEntityType, 0, "CACHE", strNow, strNow);
            rdrs = new ReturnDataResultSet(rs);
            success = true;
        } finally {
        	if(rs != null){
        		try{
        			rs.close();
        		}catch(SQLException x){
        			_db.debug(D.EBUG_DETAIL, "MetaCacheManager.expireEGCacheAllRolesAllNls : ERROR failure closing ResultSet "+x);
        		}
        		rs = null;
        	}
           	if(success){
          		_db.commit();
          	}else{
          		_db.rollback();
          	}
            _db.freeStatement();
            _db.isPending();
        }
        _db.debug(D.EBUG_SPEW, "gbl7521 rowCount:" + rdrs.getRowCount());
        for (int row = 0; row < rdrs.getRowCount(); row++) {
            String strAttributeCode = rdrs.getColumn(row, 0);
            int iNls = rdrs.getColumnInt(row, 1);
            String strEntityType_rs = rdrs.getColumn(row, 2);
            _db.debug(D.EBUG_SPEW, "gbl7521 answers:[" + strAttributeCode + "," + iNls + "," + strEntityType_rs + "]");
            _db.debug(D.EBUG_SPEW, "calling deactivateBlob(" + getProfile().toString() + "," + strEntityType_rs + ",0," + strAttributeCode + "," + iNls);
            _db.deactivateBlob(getProfile(), strEntityType_rs, 0, strAttributeCode, iNls);
        }
    }

    /**
     * expireALLCaches
     *
     * @param _db
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public void expireALLCaches(Database _db) throws SQLException, MiddlewareException, MiddlewareRequestException {
        try {
            // this will go for ALL entitytypes!!
            expireEGCacheAllRolesAllNls(_db, "");
        } finally {
            _db.freeStatement();
            _db.isPending();
        }
    }

    /**
     * This will expire ALL EntityGroup caches for ALL entities and ALL roles and ALL purposes and ALL NLSID's
     * I.E. EVERYTHING
     *
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @param _db 
     */
    public void expireALLEntityGroupCaches(Database _db) throws SQLException, MiddlewareException, MiddlewareRequestException {

        try {
            String strNow = _db.getDates().getNow();
 
            ResultSet rs = null;
            ReturnDataResultSet rdrs = null;
            
            //get all entities - dont worry about role
            //1) 'Entity' class
            boolean success = false;
            try {
                rs = _db.callGBL7529(new ReturnStatus(-1), getProfile().getEnterprise(), "Entity", strNow, strNow);
                rdrs = new ReturnDataResultSet(rs);
                success = true;
            } finally {
             	if(rs != null){
            		try{
            			rs.close();
            		}catch(SQLException x){
            			_db.debug(D.EBUG_DETAIL, "MetaCacheManager.expireALLEntityGroupCaches : ERROR failure closing ResultSet "+x);
            		}
            		rs = null;
            	}
               	if(success){
              		_db.commit();
              	}else{
              		_db.rollback();
              	}
                _db.freeStatement();
                _db.isPending();
            }
            for (int row = 0; row < rdrs.getRowCount(); row++) {
                String strEntType = rdrs.getColumn(row, 0);
                expireEGCacheAllRolesAllNls(_db, strEntType);
            }
            success = false;
            //2) because there are still some 'Relator' class entities in the MetaEntity table..
            try {
                rs = _db.callGBL7529(new ReturnStatus(-1), getProfile().getEnterprise(), "Relator", strNow, strNow);
                rdrs = new ReturnDataResultSet(rs);
                success = true;
            } finally {
             	if(rs != null){
            		try{
            			rs.close();
            		}catch(SQLException x){
            			_db.debug(D.EBUG_DETAIL, "MetaCacheManager.expireALLEntityGroupCaches : ERROR failure closing ResultSet "+x);
            		}
            		rs = null;
            	}
               	if(success){
              		_db.commit();
              	}else{
              		_db.rollback();
              	}
                _db.freeStatement();
                _db.isPending();
            }
            for (int row = 0; row < rdrs.getRowCount(); row++) {
                String strEntType = rdrs.getColumn(row, 0);
                expireEGCacheAllRolesAllNls(_db, strEntType);
            }
            success = false;
            //3) 'Assoc' classes
            try {
                rs = _db.callGBL7529(new ReturnStatus(-1), getProfile().getEnterprise(), "Assoc", strNow, strNow);
                rdrs = new ReturnDataResultSet(rs);
                success = true;
            } finally {
             	if(rs != null){
            		try{
            			rs.close();
            		}catch(SQLException x){
            			_db.debug(D.EBUG_DETAIL, "MetaCacheManager.expireALLEntityGroupCaches : ERROR failure closing ResultSet "+x);
            		}
            		rs = null;
            	}
               	if(success){
              		_db.commit();
              	}else{
              		_db.rollback();
              	}
                _db.freeStatement();
                _db.isPending();
            }
            for (int row = 0; row < rdrs.getRowCount(); row++) {
                String strEntType = rdrs.getColumn(row, 0);
                expireEGCacheAllRolesAllNls(_db, strEntType);
            }
        } finally {
            _db.freeStatement();
            _db.isPending();
        }
    }

    /////////////////////////////////////
    /// EANMetaAttributes
    /////////////////////////////////////

    /**
     * This will expire all of the EntityGroup caches contianing this meta attribute.
     * It is necessary because when an attribute is changed, it must be carried out in ALL Entities owning this att.
     *
     * @param _db
     * @param _strAttCode 
     */
    public void expireAllEntityGroupCacheContaining(Database _db, String _strAttCode) {
        Stopwatch sw = new Stopwatch();
        sw.start();
        // 1. get all entities containing this att
        ReturnDataResultSet rdrs = getAllEntityTypesContaining(_db, _strAttCode);
        // 2. now expire all entity group caches containing this att
        for (int row = 0; row < rdrs.getRowCount(); row++) {
            String strEntityType = rdrs.getColumn(row, 0);
            try {
                expireEGCacheAllRolesAllNls(_db, strEntityType);
            } catch (Exception exc) {
                _db.debug(D.EBUG_ERR, "MetaCacheManager.expireAllEntityGroupCacheContaining:" + exc.toString());
            }
        }
        //_db.debug(D.EBUG_SPEW,"(TESTING) expireAllEntityGroupCacheContaining took :" + sw.finish());
    }

    private ReturnDataResultSet getAllEntityTypesContaining(Database _db, String _strAttCode) {
        ReturnDataResultSet rdrs = null;
        ResultSet rs = null;
        try {

            String strNow = _db.getDates().getNow();
            boolean success = false;
            try {
                rs = _db.callGBL7507(new ReturnStatus(-1), getProfile().getEnterprise(), "Entity/Attribute", _strAttCode, "EntityAttribute", strNow, strNow);
                rdrs = new ReturnDataResultSet(rs);
                success = true;
            } finally {
             	if(rs != null){
            		try{
            			rs.close();
            		}catch(SQLException x){
            			_db.debug(D.EBUG_DETAIL, "MetaCacheManager.getAllEntityTypesContaining : ERROR failure closing ResultSet "+x);
            		}
            		rs = null;
            	}
               	if(success){
              		_db.commit();
              	}else{
              		_db.rollback();
              	}
                _db.freeStatement();
                _db.isPending();
            }            
        } catch (Exception exc) {
            _db.debug(D.EBUG_ERR, "MetaCacheManager.getAllEntityTypesContaining:" + exc.toString());
        } finally {
            _db.freeStatement();
            _db.isPending();
        }
        return rdrs;

    }

    /**
     * This will rebuild ALL EntityGroup caches for ALL entities and ALL roles
     * for Edit, Extract, Navigate
     *
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @param _db
     * @param _prof 
     */
    public void rebuildALLEntityGroupCaches(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareRequestException {

        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;

        String strNow = _db.getDates().getNow();
        //get all entities for the enterprise
        boolean success = false;
        try {
            rs = _db.callGBL7529(new ReturnStatus(-1), _prof.getEnterprise(), "Entity", strNow, strNow);
            rdrs = new ReturnDataResultSet(rs);
            success = true;
        } finally {
          	if(rs != null){
        		try{
        			rs.close();
        		}catch(SQLException x){
        			_db.debug(D.EBUG_DETAIL, "MetaCacheManager.rebuildALLEntityGroupCaches : ERROR failure closing ResultSet "+x);
        		}
        		rs = null;
        	}
           	if(success){
          		_db.commit();
          	}else{
          		_db.rollback();
          	}
            _db.freeStatement();
            _db.isPending();
        }
        for (int row = 0; row < rdrs.getRowCount(); row++) {
            String strEntityType = rdrs.getColumn(row, 0);
            rebuildEntityGroupCacheForAllRoles(_db, _prof, strEntityType);
        }
        success = false;
        //2) get all relator for the enterprise
        try {
        
            rs = _db.callGBL7529(new ReturnStatus(-1), _prof.getEnterprise(), "Relator", strNow, strNow);
            rdrs = new ReturnDataResultSet(rs);
            success = true;
        } finally {
           	if(rs != null){
        		try{
        			rs.close();
        		}catch(SQLException x){
        			_db.debug(D.EBUG_DETAIL, "MetaCacheManager.rebuildALLEntityGroupCaches : ERROR failure closing ResultSet "+x);
        		}
        		rs = null;
        	}
           	if(success){
          		_db.commit();
          	}else{
          		_db.rollback();
          	}
            _db.freeStatement();
            _db.isPending();
        }
        for (int row = 0; row < rdrs.getRowCount(); row++) {
            String strEntityType = rdrs.getColumn(row, 0);
            rebuildEntityGroupCacheForAllRoles(_db, _prof, strEntityType);
        }
        success = false;
        //3) 'Assoc' classes
        try {
            rs = _db.callGBL7529(new ReturnStatus(-1), _prof.getEnterprise(), "Assoc", strNow, strNow);
            rdrs = new ReturnDataResultSet(rs);
            success = true;
        } finally {
           	if(rs != null){
        		try{
        			rs.close();
        		}catch(SQLException x){
        			_db.debug(D.EBUG_DETAIL, "MetaCacheManager.rebuildALLEntityGroupCaches : ERROR failure closing ResultSet "+x);
        		}
        		rs = null;
        	}
           	if(success){
          		_db.commit();
          	}else{
          		_db.rollback();
          	}
            _db.freeStatement();
            _db.isPending();
        }
        for (int row = 0; row < rdrs.getRowCount(); row++) {
            String strEntityType = rdrs.getColumn(row, 0);
            rebuildEntityGroupCacheForAllRoles(_db, _prof, strEntityType);
        }
    }

    /**
     * rebuildEntityGroupCacheForAllRoles
     *
     * @param _db
     * @param _prof
     * @param _strEntityType
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public void rebuildEntityGroupCacheForAllRoles(Database _db, Profile _prof, String _strEntityType) throws SQLException, MiddlewareException, MiddlewareRequestException {
        ReturnStatus returnStatus = new ReturnStatus(-1);
        ReturnDataResultSet rdrs = null;
        ResultSet rs = null;
        DatePackage dp = null;

        String strEnterprise = _prof.getEnterprise();
        int iOPWGID = _prof.getOPWGID();
        int iNLSID = 1;
        String strNow = null;
       // String strForever = null;

        boolean success = false;
        try {
            expireEGCacheAllRolesAllNls(_db, _strEntityType);

            dp = _db.getDates();
            strNow = dp.getNow();
           // strForever = dp.getForever();

            try {
                rs = _db.callGBL9201(returnStatus, strEnterprise, iNLSID, strNow, iOPWGID);
                rdrs = new ReturnDataResultSet(rs);
                success  =true;
            } finally {
               	if(rs != null){
            		try{
            			rs.close();
            		}catch(SQLException x){
            			_db.debug(D.EBUG_DETAIL, "MetaCacheManager.rebuildEntityGroupCacheForAllRoles : ERROR failure closing ResultSet "+x);
            		}
            		rs = null;
            	}
               	if(success){
              		_db.commit();
              	}else{
              		_db.rollback();
              	}
                _db.freeStatement();
                _db.isPending();
            }
            for (int j = 0; j < rdrs.size(); j++) {
                String strRoleCode = rdrs.getColumn(j, 0).trim();
                String strRoleDesc = rdrs.getColumn(j, 1).trim();
                _prof.getProfileForRoleCode(_db, strRoleCode, strRoleDesc, iNLSID);
                // rebuild cache for Navigate

                // rebuild cache for Edit

                // rebuild cache for Extract

            }
        } finally {
            _db.freeStatement();
            _db.isPending();

        }
    }

    /**
     * getProfile
     *
     * @return
     *  @author David Bigelow
     */
    public Profile getProfile() {
        return m_prof;
    }

    /**
     * getVersion
     *
     * @return
     *  @author David Bigelow
     */
    public static final String getVersion() {
        return "$Id: MetaCacheManager.java,v 1.19 2012/11/08 21:52:48 wendy Exp $";
    }

}
