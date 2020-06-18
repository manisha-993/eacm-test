//
// Copyright (c) 2002, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: MetaEntityList.java,v $
// Revision 1.131  2012/11/08 21:59:12  wendy
// rollback if error instead of commit
//
// Revision 1.130  2009/11/03 18:48:37  wendy
// enhance deref
//
// Revision 1.129  2008/11/11 23:10:13  wendy
// EACM3 CTO-LA test defect, allow view meta to be turned off
//
// Revision 1.128  2008/08/08 21:42:18  wendy
// CQ00006067-WI : LA CTO - More support for QueryAction
//
// Revision 1.127  2008/08/05 01:32:17  wendy
// Check for null before rs.close()
//
// Revision 1.126  2005/03/07 21:23:22  dave
// Jtest Syntax
//
// Revision 1.125  2005/03/07 21:10:26  dave
// Jtest cleanup
//
// Revision 1.124  2005/01/18 21:46:50  dave
// more parm debug cleanup
//
// Revision 1.123  2004/04/07 17:30:54  gregg
// putEntityGroup made public
//
// Revision 1.122  2003/09/29 03:47:00  dave
// ??? removed dupped method
//
// Revision 1.121  2003/09/29 03:37:56  dave
// fixing ECCMODS nls on flag
//
// Revision 1.120  2003/08/21 21:46:29  gregg
// more NLS in constructor
//
// Revision 1.119  2003/08/21 21:33:28  gregg
// debug stmt fix
//
// Revision 1.118  2003/08/21 21:14:50  gregg
// return all nlsid's on 7501
//
// Revision 1.117  2003/05/09 20:41:20  gregg
// removed some debugs
//
// Revision 1.116  2003/04/14 18:26:37  gregg
// SORT_BY_ENTITY1TYPE, SORT_BY_ENTITY2TYPE
//
// Revision 1.115  2003/03/28 19:31:01  gregg
// some _db.commit(); 's
//
// Revision 1.114  2003/03/20 00:50:02  gregg
// remote getEntityGroup method
//
// Revision 1.113  2003/02/14 01:04:23  gregg
// continuing the legacy of expireActionsForEntity
//
// Revision 1.112  2003/02/03 18:08:11  gregg
// more expireActionsForEntity
//
// Revision 1.111  2003/02/01 00:48:54  gregg
// use getColumnDate method in expireActionsForEntity method
//
// Revision 1.110  2003/02/01 00:28:53  gregg
// expireActionsForEntity method
//
// Revision 1.109  2003/01/30 00:13:51  gregg
// setDependentFlagList method
//
// Revision 1.108  2003/01/29 17:52:03  gregg
// hasAssociatedData method
//
// Revision 1.107  2003/01/14 00:23:03  gregg
// removed entityclass param in gbl7506 -- we want to ensure entitytype is unique across ALL entityclasses
//
// Revision 1.106  2003/01/13 22:27:56  gregg
// extra EntityClass param passed into GBL7506
//
// Revision 1.105  2003/01/10 18:17:18  gregg
// debug statement added
//
// Revision 1.104  2003/01/07 00:38:33  gregg
// include version in JavaDoc
//
// Revision 1.103  2002/12/16 20:53:04  gregg
// return boolean in updateListToPdhMet method indicating any updates made to pdh
//
// Revision 1.102  2002/12/10 18:55:14  gregg
// isNewEntityTYpe -- back to using strNow from profile
//
// Revision 1.101  2002/12/06 21:14:43  gregg
// in isNewEntityType method: get current time from db, not profile
//
// Revision 1.100  2002/12/05 18:02:57  gregg
// setRelator in constructor if applicable
//
// Revision 1.99  2002/11/21 23:02:21  gregg
// setcapability of eg's in constructor
//
// Revision 1.98  2002/11/21 22:03:52  gregg
// null ptr fix
//
// Revision 1.97  2002/11/21 21:49:04  gregg
// abondoning stub in its current incarnation -- going with empty EntityGroup instead, for consistency and easier maintaing
//
// Revision 1.96  2002/11/21 21:28:20  gregg
// ~try~ and keep eg+stub desc's sync'd
//
// Revision 1.95  2002/11/21 20:38:55  gregg
// revising setActive, isActive for EntityGroups to use stub
//
// Revision 1.94  2002/11/21 19:22:12  gregg
// fix to filtered logic using MetaEntityStub
//
// Revision 1.93  2002/11/21 18:45:39  gregg
// fix in groupToStub method
//
// Revision 1.92  2002/11/21 17:49:55  gregg
// use MetaEntityStub
//
// Revision 1.91  2002/11/20 23:32:30  gregg
// first pass at using MetaEntityStubs as placeholders for EntityGroups
//
// Revision 1.90  2002/11/18 21:29:23  gregg
// syntax - wrong # of params
//
// Revision 1.89  2002/11/18 21:20:24  gregg
// ignore case logic on performFilter
//
// Revision 1.88  2002/11/18 18:41:30  gregg
// more wildcard for filter
//
// Revision 1.87  2002/11/18 18:28:09  gregg
// allow use of wildcards on performFilter()
//
// Revision 1.86  2002/11/06 22:41:12  gregg
// removing display statements
//
// Revision 1.85  2002/10/15 23:29:46  gregg
// when updatePdhMeta->expire ALL cache for entityType
//
// Revision 1.84  2002/10/15 17:08:57  gregg
// replaceEntityGroup
//
// Revision 1.83  2002/10/15 00:35:40  gregg
// Additional "NoAtts" type purpose for use by MetaEntityList -- should solve the isNavigate() cache inconsitencies
//
// Revision 1.82  2002/10/14 23:48:27  gregg
// pass default purpose of Navigate
//
// Revision 1.81  2002/09/30 18:40:55  gregg
// getNewMetaAttribute method
//
// Revision 1.80  2002/09/27 20:46:06  gregg
// add in XML,Blob types in getMetaAttributeForRole method
//
// Revision 1.79  2002/09/27 18:52:56  gregg
// some static sort/filter types arrays mods
//
// Revision 1.78  2002/09/26 17:35:12  gregg
// redfine some sort type constants
//
// Revision 1.77  2002/09/25 16:56:22  gregg
// removed resetAllCaches method - now use MetaCacheManager for this
//
// Revision 1.76  2002/09/06 20:15:53  gregg
// some more sort/filter
//
// Revision 1.75  2002/09/06 17:51:44  gregg
// more sort/filter
//
// Revision 1.74  2002/09/06 17:38:47  gregg
// SortFilterInfo now uses String sort/filter key constants (were ints)
//
// Revision 1.73  2002/07/27 00:17:21  gregg
// some Role/Attribute updates
//
// Revision 1.72  2002/07/26 23:16:02  gregg
// more att caps
//
// Revision 1.71  2002/07/26 21:16:57  gregg
// more in getAttributeCapabilityForRole
//
// Revision 1.70  2002/07/26 18:33:59  gregg
// fix in getAttributeCapabilityForRole
//
// Revision 1.69  2002/07/26 16:28:04  gregg
// some debug stmts.
//
// Revision 1.68  2002/07/24 23:39:36  gregg
// some cache management for entitygroups
//
// Revision 1.67  2002/07/18 23:32:27  gregg
// isEntityGroupDisplayableForFilter method
//
// Revision 1.66  2002/07/18 18:10:46  gregg
// SortFilterInfo stuff
//
// Revision 1.65  2002/06/17 17:29:05  gregg
// getCapability methods added
//
// Revision 1.64  2002/06/17 17:21:27  gregg
// getEntityClass methods
//
// Revision 1.63  2002/06/14 23:38:49  gregg
// sortList method
//
// Revision 1.62  2002/06/05 21:02:08  gregg
// expireAllAttributeInstances method
//
// Revision 1.61  2002/06/05 20:52:20  gregg
// some rearranging
//
// Revision 1.60  2002/06/05 20:44:31  gregg
// put EG back in the list for refreshAllAttributeInstances
//
// Revision 1.59  2002/06/05 20:40:07  gregg
// refreshEntityGroupCache, refreshAllAttributeInstances methods added
//
// Revision 1.58  2002/06/05 17:19:25  gregg
// more getEntityGroupWithAtts
//
// Revision 1.57  2002/06/05 16:46:17  gregg
// getEntityGroupWithAtts method synchronized
//
// Revision 1.56  2002/06/05 16:38:03  gregg
// getEntityGroupsWithAtts method: replace EG in proper index
//
// Revision 1.55  2002/06/05 00:46:19  gregg
// debugging
//
// Revision 1.54  2002/06/04 21:13:19  gregg
// getActionGroupByEntityType, setActionGroupForEntityType methods
//
// Revision 1.53  2002/06/04 17:36:16  gregg
// in getEntityGroupWithAtts -> no longer removeData
//
// Revision 1.52  2002/06/03 18:13:49  gregg
// stub for getActionGroupByEntityType added (still needs to be written)
//
// Revision 1.51  2002/06/03 17:56:43  gregg
// expireAttribute for orphans
//
// Revision 1.50  2002/06/03 17:35:21  gregg
// some cleanup
//
// Revision 1.49  2002/05/31 23:45:27  gregg
// getOwnedAttributes, getOrphanedAttributes
//
// Revision 1.48  2002/05/31 20:45:15  gregg
// fix for expireAttribute
//
// Revision 1.47  2002/05/31 18:03:51  gregg
// expireAttribute method added
//
// Revision 1.46  2002/05/31 17:43:24  gregg
// getEntityGroupsContainingAttribute method added
//
// Revision 1.45  2002/04/10 21:06:56  dave
// massive .close() effort on classes and methods
//
// Revision 1.44  2002/04/09 20:15:03  gregg
// remove MetaEntityList cache methods (EntityGroup reset cache methods remain)
//
// Revision 1.43  2002/04/09 00:15:02  gregg
// made MetaEntityList cache methods protected
//
// Revision 1.42  2002/04/08 18:21:46  gregg
// valOn/effOn -> use db.getDates().getNow()
//
// Revision 1.41  2002/04/04 00:17:02  gregg
// removed valFrom, valTo...
//
// Revision 1.40  2002/04/03 23:52:44  gregg
// getAllAttributes method now returns attCode,attDesc,attType
//
// Revision 1.39  2002/04/02 23:40:23  gregg
// setValOn, setValTo for EANMetaAttribute in getMetaAttributeForRole method
//
// Revision 1.38  2002/03/22 21:38:05  gregg
// getAllAttributes(_db)
//
// Revision 1.37  2002/03/22 19:32:48  gregg
// shell for getAllAttributes()()
//
// Revision 1.36  2002/03/22 02:00:50  gregg
// changed some protecteds to publics
//
// Revision 1.35  2002/03/20 00:28:53  gregg
// ...getMetaAttributeForRole...
//
// Revision 1.34  2002/03/19 23:57:39  gregg
// ...getMetaAttributeForRole()
//
// Revision 1.33  2002/03/19 23:50:45  gregg
// signature change for method getMetaAttributeForRole()
//
// Revision 1.32  2002/03/19 23:38:30  gregg
// getMetaAttributeForRole(), + cache stuff that may or may not be left in...
//
// Revision 1.31  2002/03/16 01:03:54  gregg
// resetEntityGroupCache()
//
// Revision 1.30  2002/03/15 22:24:34  gregg
// fixes for cache update, etc
//
// Revision 1.29  2002/03/13 21:47:54  gregg
// removeEntityGroup(String _strEntityType) added
//
// Revision 1.28  2002/03/13 21:24:22  gregg
// setEntityGroupCapability()
//
// Revision 1.27  2002/03/13 19:05:14  gregg
// removeData(),putData() added in getEntityGroupWithAtts() to ensure proper reference to EntityGroups are maintained in the list
//
// Revision 1.26  2002/03/12 23:24:29  gregg
// no longer throw an exception when building a list from an invalid entityType (instead build empty list)
//
// Revision 1.25  2002/03/08 19:42:59  gregg
// bunch of new update stuff
//
// Revision 1.24  2002/03/06 22:00:21  gregg
// getPossibleAttributeTypes
//
// Revision 1.23  2002/03/02 00:12:51  gregg
// type-o fix
//
// Revision 1.22  2002/03/02 00:00:07  gregg
// isNewAttributeCode() method added
//
// Revision 1.21  2002/03/01 22:38:45  gregg
// putNewEntityGroup - if an expired entityType is added -> remove from expired list
//
// Revision 1.20  2002/03/01 20:49:53  gregg
// provide accessors to retreive entityType, descriptions from entityGroup
//
// Revision 1.19  2002/03/01 18:40:45  gregg
// dropped in new changes - updateListToPdh(), expire methods, changes to put/remove EntityGroups,
// isNewEntityType() method...
//
// Revision 1.18  2002/02/28 22:21:48  gregg
// removed setupMetaAttributes() - instead create EntityGroup w/ purpose='Edit'
//
// Revision 1.17  2002/02/28 21:53:52  gregg
// no need to pass Profile into setupMetaAttributes
//
// Revision 1.16  2002/02/28 18:30:16  gregg
// getColumnInt used for 7502
//
// Revision 1.15  2002/02/28 18:01:34  gregg
// changed input params for 7501 (roleCode replaces openId)
//
// Revision 1.14  2002/02/28 01:16:23  gregg
// reordering of columns for gbl7502
//
// Revision 1.13  2002/02/27 01:16:31  gregg
// throw MiddlewareException if entityType is not valid
//
// Revision 1.12  2002/02/26 23:09:59  gregg
// reversed order of a freeStatement(), isPEnding() combo to avoid debug error message
//
// Revision 1.11  2002/02/26 21:53:49  gregg
// make sure entityType is valid b4 adding EntityGroup (con't)
//
// Revision 1.10  2002/02/26 21:44:00  dave
// ensuring I am setting the rs = null after a close
//
// Revision 1.9  2002/02/26 19:21:12  gregg
// if the passed entityType in the constructor is not valid, do not add entityGroup
//
// Revision 1.8  2002/02/26 17:52:52  gregg
// getAttributeCapabilityForRole() method added
//
// Revision 1.7  2002/02/26 01:29:42  gregg
// check for rowCount > o on rdrs in getEntityCapabilityforRoles
//
// Revision 1.6  2002/02/26 00:42:35  gregg
// minor fix to getEntityCapabilityForRole
//
// Revision 1.5  2002/02/25 23:33:12  gregg
// getEntityCapabilityForRole() added
//
// Revision 1.4  2002/02/22 17:08:43  gregg
// made setMetaAttributes() protected to avoid public use
//
// Revision 1.3  2002/02/22 00:03:38  gregg
// getEntitiesLike() added
//
// Revision 1.2  2002/02/21 23:30:40  gregg
// some cleanup
//
// Revision 1.1  2002/02/21 22:59:48  gregg
// initial load
//
//
//

package COM.ibm.eannounce.objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.rmi.RemoteException;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;

/**
* This Object manages A list of MetaEntities - intended for use with the MetaAdmin UI
*/
public class MetaEntityList extends EANMetaEntity implements EANSortableList {
 
    /**
    * @serial
    */
    static final long serialVersionUID = 1L;
    static final String CACHE_ENTTYPE = "META_ENTITY_LIST_CACHE";

    //determine which constructor created this thing..
    private boolean m_bSingleEntity = false;
    //hold onto expired Entity Groups
    private EANList m_oElExpired = null;
    private String m_strPurpose = null;
    //store ActionGroups by their entitytype
    private Hashtable m_hashAGs = null;
    private SortFilterInfo m_SFInfo = null;

    private static final int ALL_ATTRIBUTES = 1000;
    private static final int OWNED_ATTRIBUTES = 1001;
    private static final int ORPHAN_ATTRIBUTES = 1002;
    
    private static final char READ = 'R';
    private static final char WRITE = 'W';
    private static final char CREATE= 'C';
    

    /**
     * FIELD
     */
    protected static final String[] SA_FILTER_TYPES = { EntityGroup.SORT_BY_LONGDESC, EntityGroup.SORT_BY_SHORTDESC, EntityGroup.SORT_BY_ENTITYTYPE, EntityGroup.SORT_BY_ENTITYCLASS, EntityGroup.SORT_BY_ENTITY1TYPE, EntityGroup.SORT_BY_ENTITY2TYPE };
    /**
     * FIELD
     */
    public static final String FILTER_BY_LONG_DESCRIPTION = SA_FILTER_TYPES[0];
    /**
     * FIELD
     */
    public static final String FILTER_BY_SHORT_DESCRIPTION = SA_FILTER_TYPES[1];
    /**
     * FIELD
     */
    public static final String FILTER_BY_ENTITY_TYPE = SA_FILTER_TYPES[2];
    /**
     * FIELD
     */
    public static final String FILTER_BY_ENTITY_CLASS = SA_FILTER_TYPES[3];
    /**
     * FIELD
     */
    public static final String FILTER_BY_ENTITY1TYPE = SA_FILTER_TYPES[4];
    /**
     * FIELD
     */
    public static final String FILTER_BY_ENTITY2TYPE = SA_FILTER_TYPES[5];
    /**
     * FIELD
     */
    protected static final String[] SA_SORT_TYPES = SA_FILTER_TYPES;
    /**
     * FIELD
     */
    public static final String SORT_BY_LONG_DESCRIPTION = SA_SORT_TYPES[0];
    /**
     * FIELD
     */
    public static final String SORT_BY_SHORT_DESCRIPTION = SA_SORT_TYPES[1];
    /**
     * FIELD
     */
    public static final String SORT_BY_ENTITY_TYPE = SA_SORT_TYPES[2];
    /**
     * FIELD
     */
    public static final String SORT_BY_ENTITY_CLASS = SA_SORT_TYPES[3];
    /**
     * FIELD
     */
    public static final String SORT_BY_ENTITY1TYPE = SA_SORT_TYPES[4];
    /**
     * FIELD
     */
    public static final String SORT_BY_ENTITY2TYPE = SA_SORT_TYPES[5];

    /**
     * Main method which performs a simple test of this class
     *
     * @param arg 
     */
    public static void main(String arg[]) {
    }

    /**
     * This constructor will get ALL entities based on role
     *
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @param _db
     * @param _prof 
     */
    public MetaEntityList(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareRequestException {
        //this(_db,_prof,null,"Edit");
        this(_db, _prof, null, "NoAtts");
    }
    /**
     * This constructor will get ALL entities based on role
     *
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @param _db
     * @param _prof 
     * @param _bAddViews
     */
    public MetaEntityList(Database _db, Profile _prof, boolean _bAddViews) throws SQLException, MiddlewareException, MiddlewareRequestException {
        //this(_db,_prof,null,"Edit");
        this(_db, _prof, null, "NoAtts", _bAddViews);
    }

    /**
     * This constructor creates a MetaEntityList containing ONE entity
     *
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @param _db
     * @param _prof
     * @param _strEntityType 
     */
    public MetaEntityList(Database _db, Profile _prof, String _strEntityType) throws SQLException, MiddlewareException, MiddlewareRequestException {
        this(_db, _prof, _strEntityType, "Edit");
    }

    /**
     * This constructor creates a MetaEntityList containing ONE entity - w/ purpose
     *
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @param _db
     * @param _prof
     * @param _strEntityType
     * @param _strEGPurpose 
     */
    public MetaEntityList(Database _db, Profile _prof, String _strEntityType, String _strEGPurpose) throws SQLException, MiddlewareException, MiddlewareRequestException {
    	this(_db, _prof, _strEntityType, _strEGPurpose, true);
    }
    /**
     * This constructor creates a MetaEntityList containing ONE entity - w/ purpose
     *
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @param _db
     * @param _prof
     * @param _strEntityType
     * @param _strEGPurpose 
     * @param _bAddViews
     */
    public MetaEntityList(Database _db, Profile _prof, String _strEntityType, String _strEGPurpose, boolean _bAddViews) throws SQLException, MiddlewareException, MiddlewareRequestException {

        super(null, _prof, _prof.toString() + "MetaEntityList" + (_strEntityType != null ? _strEntityType : "ALL"));

        m_SFInfo = new SortFilterInfo(EntityGroup.SORT_BY_LONGDESC, true, MetaEntityList.FILTER_BY_LONG_DESCRIPTION, null);

        setPurpose(_strEGPurpose);
        m_oElExpired = new EANList();
        m_hashAGs = new Hashtable();

        if (_strEntityType != null) {
            m_bSingleEntity = true;
        }

        try {
        	boolean success = false;
            //get the stuff we need
            ReturnStatus returnStatus = new ReturnStatus(-1);
            ReturnDataResultSet rdrs = null;
            ResultSet rs = null;
            
            String strEnterprise = getProfile().getEnterprise();

            String strValOn = _db.getDates().getNow();
            String strEffOn = strValOn;
            String strRoleCode = getProfile().getRoleCode();

            //get the list regardless of constructor used -- we need this to check if an entityType is valid
            try {
                rs = _db.callGBL7501(returnStatus, strEnterprise, strRoleCode, strValOn, strEffOn);
                rdrs = new ReturnDataResultSet(rs);
                success = true;
            } finally {
              	if (rs !=null){
            		try{
            			rs.close();
            		}catch(SQLException x){
            			_db.debug(D.EBUG_DETAIL, "MetaEntityList "+getKey()+": ERROR failure closing ResultSet "+x);
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

            // Building all the EntityGroup if entityType is null...
            if (_strEntityType == null) {
                for (int i = 0; i < rdrs.size(); i++) {
                    String str1 = rdrs.getColumn(i, 0); // This is the Basic Entity Type
                    String strEntClass = rdrs.getColumn(i, 1);
                    String strShortDesc = rdrs.getColumn(i, 2);
                    String strLongDesc = rdrs.getColumn(i, 3);
                    String strCap = rdrs.getColumn(i, 4);
                    String strEnt1 = rdrs.getColumn(i, 5);
                    String strEnt2 = rdrs.getColumn(i, 6);
                    int iNLSID_rs = rdrs.getColumnInt(i, 7);

                    _db.debug(D.EBUG_SPEW, "gbl7501 answer is:" + str1 + ":" + strEntClass + ":" + strShortDesc + ":" + strLongDesc + ":" + iNLSID_rs);

                    //Associations managed in MetaAssociationList!!
                    if (!strEntClass.equals("Assoc")) {
                        EntityGroup eg = getEntityGroup(str1);
                        if (eg == null) {
                            try {
                                eg = new EntityGroup(getProfile(), str1, "NoAtts");
                                eg.putShortDescription(iNLSID_rs, strShortDesc);
                                eg.putLongDescription(iNLSID_rs, strLongDesc);
                                eg.setCapability(strCap);
                                if (strEntClass.equals("Relator")) {
                                    eg.setRelator(true, strEnt1, strEnt2);
                                }
                                putData(eg);
                            } catch (Exception exc) {
                                exc.printStackTrace();
                            }
                        } else { // set descrips for alternate NLSID!!
                            eg.putShortDescription(iNLSID_rs, strShortDesc);
                            eg.putLongDescription(iNLSID_rs, strLongDesc);
                        }
                    }
                }
            } else { //build ONE entityGroup...
                EntityGroup eg = new EntityGroup(this, _db, getProfile(), _strEntityType, getPurpose());
                //we can do this here for one entityGroup...
                //...only if it has a description (i.e. it exists in metadescription table - is valid)
                if (isEntityTypeValid(rdrs, _strEntityType)) {
                    putEntityGroup(eg);
                } else {
                    _db.debug(D.EBUG_SPEW,"MetaEntityList: roleCode '" + strRoleCode + "' does not know anything about entityType '" + _strEntityType + "'. Creating empty MetaEntityList.");
                }
            }
            
            if (_bAddViews){
            	getViewActions(_db);
            }
        } finally {
            _db.freeStatement();
            _db.isPending();
        }
    }
    
    /**
     * Add view actions so column order can be modified
     * @param _db
     * @throws MiddlewareException
     * @throws SQLException
     */
    private void getViewActions(Database _db) throws MiddlewareException, SQLException
    {
		ResultSet rs=null;
		PreparedStatement ps=null;
		// select *	from opicm.metalinkattr where enterprise='SG' and     
		// linkcode='ViewName'  and valto > current timestamp and effto >current timestamp
		StringBuffer querySb = new StringBuffer("select linktype1,linkvalue from opicm.metalinkattr where enterprise='"+
				getProfile().getEnterprise()+"' and linkcode='ViewName' and valto > current timestamp and effto >current timestamp");

		Connection con = _db.getPDHConnection();
		ps = con.prepareStatement(querySb.toString());
		_db.debug(D.EBUG_DETAIL, "MetaEntityList.getViewActions executing SQL:" + querySb);

		rs = ps.executeQuery();

		while (rs.next()) {
			String viewaction = rs.getString(1);
			String viewname = rs.getString(2);
			try {
                EntityGroup eg = new QueryGroup(_db, getProfile(), viewname);
                eg.putShortDescription(1, viewaction);
                eg.putLongDescription(1, "View "+viewname);
                eg.setCapability("R");    
                putData(eg);
            } catch (Exception exc) {
                exc.printStackTrace();
            }
		}
    }

    //
    // MUTATORS
    //

    /**
     * Refresh the attributes for all EntityGroups in the list which contain the indicated MetaAttribute and refresh cache accordingly.
     *
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @param _db
     * @param _att 
     */
    public void refreshAllAttributeInstances(Database _db, EANMetaAttribute _att) throws SQLException, MiddlewareException, MiddlewareRequestException {
        refreshAllAttributeInstances(_db, _att, false);
    }

    /**
     * Expire the attributes for all EntityGroups in the list which contain the indicated MetaAttribute and refresh cache accordingly.
     *
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @param _db
     * @param _att 
     */
    public void expireAllAttributeInstances(Database _db, EANMetaAttribute _att) throws SQLException, MiddlewareException, MiddlewareRequestException {
        refreshAllAttributeInstances(_db, _att, true);
    }

    /**
     * Expire the given attribute, remove it from all EntityGroups it is referenced in, and update Cache accordingly
     *
     * @param _strAttCode the attribute to search for
     * @return EANList of EntityGroups attr is contained in
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @param _db 
     */
    public EANList expireAttribute(Database _db, String _strAttCode) throws SQLException, MiddlewareException, MiddlewareRequestException {
        EANList eList = getEntityGroupsContainingAttribute(_db, _strAttCode);
        EANMetaAttribute oAttExpire = null;

        for (int i = 0; i < eList.size(); i++) {
            EntityGroup oEg = (EntityGroup) eList.getAt(i);
            oAttExpire = oEg.getMetaAttribute(_strAttCode);
            //expiring any of these references ~should~ get it done - only do this once
            if (i == 0) {
                oAttExpire.expirePdhMeta(_db);
            }
            oEg.removeMetaAttribute(oAttExpire);
            expireEntityGroupCache(_db, oEg);
        }
        if (oAttExpire == null) {
            _db.debug(D.EBUG_SPEW, "MetaEntityList.expireAttribute method: no attribute found matching: '" + _strAttCode + "' (ORPHAN) - creating attribute from scratch.");
            oAttExpire = getMetaAttributeForRole(_db, getProfile(), _strAttCode, getProfile().getRoleCode(), getProfile().getReadLanguage().getNLSID());
            if (oAttExpire == null) {
                throw new MiddlewareRequestException("MetaEntityList.expireAttribute method: no attribute found matching: '" + _strAttCode + "' for Role: '" + getProfile().getRoleCode() + "'.");
            }
            oAttExpire.expirePdhMeta(_db);
        }
        return eList;
    }

    /**
     * Update the changed EntityGroup to the PDH
     *
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @return boolean
     * @param _db 
     */
    public boolean updateListToPdh(Database _db) throws SQLException, MiddlewareException, MiddlewareRequestException {
        boolean bChanged = false;
        //Updates
        for (int i = 0; i < getEntityGroupCount(); i++) {
            EntityGroup oEg = getEntityGroup(i);
            //only perform updates on active entityGroups...
            if (oEg.isActive()) {
                bChanged = (oEg.updatePdhMeta(_db) ? true : bChanged);
            }
        }
        //Expires
        for (int i = 0; i < getExpiredEntityGroupCount(); i++) {
            bChanged = (getExpiredEntityGroup(i).expirePdhMeta(_db) ? true : bChanged);
        }
        m_oElExpired = new EANList();
        return bChanged;
    }

    /**
     * Replace the EntityGroup in the list with the indicated EntityGroup - must be the same entityType
     * i.e. perform a refresh of the group -- do nothing if not already in the list.
     *
     * @param _eg 
     */
    public void replaceEntityGroup(EntityGroup _eg) {
        removeData(_eg);
        putData(_eg);
    }

    /**
     * Add a new EntityGroup externally
     *
     * @param _eg 
     */
    public void putNewEntityGroup(EntityGroup _eg) {
        putEntityGroup(_eg);
        _eg.setActive(true);
        //if this has been expired, then take it back out so we dont expire it later!!
        for (int i = 0; i < getExpiredEntityGroupCount(); i++) {
            EntityGroup oEg_exp = getExpiredEntityGroup(i);
            if (oEg_exp.getEntityType().equals(_eg.getEntityType())) {
                m_oElExpired.remove(oEg_exp);
            }
        }
    }

    /**
     * Add a new EntityGroup
     *
     * @param _eg 
     */
    public void putEntityGroup(EntityGroup _eg) {
        _eg.setActive(false);
        putData(_eg);
    }

    /**
     * Removes an EntityGroup and Returns it for further manipulation
     *
     * @return EntityGroup
     * @param _eg 
     */
    public EntityGroup removeEntityGroup(EntityGroup _eg) {
        m_oElExpired.put(_eg);
        removeData(_eg);
        return _eg;
    }

    /**
     * Removes an EntityGroup by entityType and Returns it for further manipulation
     *
     * @return EntityGroup
     * @param _strEntityType 
     */
    public EntityGroup removeEntityGroup(String _strEntityType) {
        m_oElExpired.put(getEntityGroup(_strEntityType));
        return (EntityGroup) removeData(getEntityGroup(_strEntityType));
    }

    /**
     * Set the capability of the indicated EntityGroup with regards to the current active Profile
     * This is the only place to publicly set the capability on an EntityGroup (it cannot be accessed from EntityGroup directly)
     *
     * @param _oEg
     * @param _strCap 
     */
    public void setEntityGroupCapability(EntityGroup _oEg, String _strCap) {
        _oEg.setRead(_strCap.charAt(0) == READ || _strCap.charAt(0) == WRITE || _strCap.charAt(0) == CREATE);
        _oEg.setWrite(_strCap.charAt(0) == WRITE || _strCap.charAt(0) == CREATE);
        _oEg.setCreate(_strCap.charAt(0) == CREATE);
    }
 
    /**
     * (non-Javadoc)
     * getFilterTypesArray
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#getFilterTypesArray()
     */
    public String[] getFilterTypesArray() {
        return SA_FILTER_TYPES;
    }

    /**
     * (non-Javadoc)
     * getObject
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#getObject(int)
     */
    public EANComparable getObject(int _i) {
        return getEntityGroup(_i);
    }

    /**
     * (non-Javadoc)
     * getObjectCount
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#getObjectCount()
     */
    public int getObjectCount() {
        return getEntityGroupCount();
    }

    /**
     * (non-Javadoc)
     * getSFInfo
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#getSFInfo()
     */
    public SortFilterInfo getSFInfo() {
        return m_SFInfo;
    }

    /**
     * (non-Javadoc)
     * getSortTypesArray
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#getSortTypesArray()
     */
    public String[] getSortTypesArray() {
        return SA_SORT_TYPES;
    }

    /**
     * (non-Javadoc)
     * isObjectFiltered
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#isObjectFiltered(int)
     */
    public boolean isObjectFiltered(int _i) {
        return (!isEntityGroupDisplayableForFilter(_i));
    }

    /**
     * (non-Javadoc)
     * performFilter
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#performFilter(boolean)
     */
    public void performFilter(boolean _bUseWildcards) {
        //first go through and set the displayable
        //  - note that it is up to the calling party to check for isDisplayable on MetaRoles
        resetDisplayableObjects();
        if (getSFInfo().getFilter() != null) {
            String strFilter = getSFInfo().getFilter();
            for (int i = 0; i < getObjectCount(); i++) {
                String strToCompare = null;
                if (getSFInfo().getFilterType().equals(FILTER_BY_LONG_DESCRIPTION)) {
                    strToCompare = getLongDescription(i);
                
                } else if (getSFInfo().getFilterType().equals(FILTER_BY_SHORT_DESCRIPTION)) {
                    strToCompare = getShortDescription(i);
                
                } else if (getSFInfo().getFilterType().equals(FILTER_BY_ENTITY_TYPE)) {
                    strToCompare = getEntityType(i);
                
                } else if (getSFInfo().getFilterType().equals(FILTER_BY_ENTITY_CLASS)) {
                    strToCompare = getEntityClass(i);
                
                } else {
                    strToCompare = "";
                }
                //
                if (!_bUseWildcards) {
                    if (strToCompare.length() < strFilter.length()) {
                        setObjectFiltered(i, true);
                    } else if (!strToCompare.substring(0, strFilter.length()).equalsIgnoreCase(strFilter)) {
                        setObjectFiltered(i, true);
                    }
                } else {
                    if (!SortFilterInfo.equalsWithWildcards(strFilter, strToCompare, new char[] { '*', '%' }, true)) {
                        setObjectFiltered(i, true);
                    }
                }
                //

            }
        }
    }
    /**
     * (non-Javadoc)
     * performSort
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#performSort()
     */
    public void performSort() {
        EANComparable[] aC = new EANComparable[getObjectCount()];
        EANComparator ec = new EANComparator(getSFInfo().isAscending());
        for (int i = 0; i < getObjectCount(); i++) {
            EANComparable c = getObject(i);
            c.setCompareField(getSFInfo().getSortType());
            aC[i] = c;
        }
        Arrays.sort(aC, ec);
        resetData();
        for (int i = 0; i < aC.length; i++) {
            EANComparable c = aC[i];
            putObject(c);
        }
    }

    /**
     * (non-Javadoc)
     * putObject
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#putObject(COM.ibm.eannounce.objects.EANComparable)
     */
    public void putObject(EANComparable _ec) {
        putEntityGroup((EntityGroup) _ec);
    }

    /**
     * (non-Javadoc)
     * removeObject
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#removeObject(COM.ibm.eannounce.objects.EANComparable)
     */
    public void removeObject(EANComparable _ec) {
        removeEntityGroup((EntityGroup) _ec);
    }

    /**
     * (non-Javadoc)
     * setObjectFiltered
     *
     * @see COM.ibm.eannounce.objects.EANSortableList#setObjectFiltered(int, boolean)
     */
    public void setObjectFiltered(int _i, boolean _b) {
        setDisplayableForFilter(_i, !_b);
    }

    //non-required for EANSortableList interface
    private void resetDisplayableObjects() {
        for (int i = 0; i < getEntityGroupCount(); i++) {
            setObjectFiltered(i, false);
        }
    }
    //

    ////////
    //}}}

    //
    // ACCESSORS
    //

    //get this stuff from entityGroup w/out having to get an entityGroup...
    //- this will ensure that we only setActive the entityGroups that we are modifying.

    /**
     * get the entityType of the EntityGroup at the given index
     *
     * @param _i index of EntityGroup
     * @return String
     */
    public String getEntityType(int _i) {
        return getEntityGroup(_i).getEntityType();
    }

    /**
     * get the shortDescription of the EntityGroup at the given index
     *
     * @param _i index of EntityGroup
     * @return String
     */
    public String getShortDescription(int _i) {
        return getEntityGroup(_i).getShortDescription();
    }

    /**
     * get the shortDescription of the EntityGroup w/ the specified entityType
     *
     * @param _s entityType of the EntityGroup
     * @return String
     */
    public String getShortDescription(String _s) {
        return getEntityGroup(_s).getShortDescription();
    }

    /**
     * get the longDescription of the EntityGroup at the given index
     *
     * @param _i index of EntityGroup
     * @return String
     */
    public String getLongDescription(int _i) {
        return getEntityGroup(_i).getLongDescription();
    }

    /**
     * get the longDescription of the EntityGroup w/ the specified entityType
     *
     * @param _s entityType of the EntityGroup
     * @return String
     */
    public String getLongDescription(String _s) {
        return getEntityGroup(_s).getLongDescription();
    }

    /**
     * get the EntityClass of the EntityGroup at the given index
     *
     * @param _i index of EntityGroup
     * @return String
     */
    public String getEntityClass(int _i) {
        String strEntityClass = "Entity";
        if (getEntityGroup(_i).isRelator()) {
            strEntityClass = "Relator";
        
        } else if (getEntityGroup(_i).isAssoc()) {
            strEntityClass = "Association";
        }
        return strEntityClass;
    }

    /**
     * get the EntityClass of the EntityGroup at the given index
     *
     * @param _s entityType of the EntityGroup
     * @return String
     */
    public String getEntityClass(String _s) {
        String strEntityClass = "Entity";
        if (getEntityGroup(_s).isRelator()) {
            strEntityClass = "Relator";        
        } else if (getEntityGroup(_s).isAssoc()) {
            strEntityClass = "Association";
        }
        return strEntityClass;
    }

    /**
     * get the Capability of the EntityGroup w/ the specified entityType
     *
     * @param _i index of EntityGroup
     * @return String
     */
    public String getCapability(int _i) {
        return getEntityGroup(_i).getCapability();
    }

    /**
     * get the Capability of the EntityGroup w/ the specified entityType
     *
     * @param _s entityType of the EntityGroup
     * @return String
     */
    public String getCapability(String _s) {
        return getEntityGroup(_s).getCapability();
    }

    /**
     * Denote which constructor built this thing.
     * In case of all attributes, MetaAttributes are not included.
     * For single Entity, MetaAttributes are included.
     *
     * @return boolean
     */
    public boolean isSingleEntity() {
        return m_bSingleEntity;
    }

    /**
     * Get All of the EntityGroups whose entityType matches _strLike%
     * @param _strLike substring to compare to entityTypes
     * @return EANList of EntityGroups
     */
    public EANList getEntitiesLike(String _strLike) {
        int iLen = _strLike.length();
        EANList eanList = new EANList();

        for (int i = 0; i < getEntityGroupCount(); i++) {
            String strEntType = getEntityGroup(i).getEntityType();
            //continue only if this is a possible match..
            if (strEntType.length() >= iLen) {
                if (_strLike.equals(strEntType.substring(0, iLen))) {
                    eanList.put(getEntityGroup(i));
                }
            }
        }
        return eanList;
    }

    /**
     * returns the EntityGroupTest found at key _str
     *
     * @return EntityGroup
     * @param _str 
     */
    public EntityGroup getEntityGroup(String _str) {
        return (EntityGroup) getData(_str);
    }

    /**
     * Returns the EntityGroupTest at Index _i
     *
     * @return EntityGroup
     * @param _i 
     */
    public EntityGroup getEntityGroup(int _i) {
        return (EntityGroup) getData(_i);
    }

    /**
     * returns the EntityGroupTest found at key _str
     */
    private EntityGroup getEntityGroup(Database _db, RemoteDatabaseInterface _rdi, String _str) throws SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, RemoteException {
        EntityGroup oEg = getEntityGroup(_str);
        if (oEg == null) {
            return null;
        }
        //Ensure that ALL EntityGroups we are working with have attributes.
        oEg = getEntityGroupWithAtts(_db, _rdi, oEg);
        //mark this as active now...
        oEg.setActive(true);
        return oEg;
    }

    /**
     * Returns the EntityGroupTest at Index _i
     */
    private EntityGroup getEntityGroup(Database _db, RemoteDatabaseInterface _rdi, int _i) throws SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, RemoteException {
        EntityGroup oEg = getEntityGroup(_i);
        if (oEg == null) {
            return null;
        }
        //Ensure that ALL EntityGroups we are working with have attributes.
        oEg = getEntityGroupWithAtts(_db, _rdi, oEg);
        //mark this as active now...
        oEg.setActive(true);
        return oEg;
    }

    /**
     * getEntityGroup
     *
     * @param _db
     * @param _str
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @return
     *  @author David Bigelow
     */
    public EntityGroup getEntityGroup(Database _db, String _str) throws SQLException, MiddlewareException, MiddlewareRequestException {
        try {
            return getEntityGroup(_db, null, _str);
        } catch (RemoteException re) {
            re.printStackTrace();
        } catch (MiddlewareShutdownInProgressException msipe) {
            msipe.printStackTrace();
        }
        return null;
    }

    /**
     * getEntityGroup
     *
     * @param _db
     * @param _i
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @return
     *  @author David Bigelow
     */
    public EntityGroup getEntityGroup(Database _db, int _i) throws SQLException, MiddlewareException, MiddlewareRequestException {
        try {
            return getEntityGroup(_db, null, _i);
        } catch (RemoteException re) {
            re.printStackTrace();
        } catch (MiddlewareShutdownInProgressException msipe) {
            msipe.printStackTrace();
        }
        return null;
    }

    /**
     * getEntityGroup
     *
     * @param _rdi
     * @param _str
     * @throws java.rmi.RemoteException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @return
     *  @author David Bigelow
     */
    public EntityGroup getEntityGroup(RemoteDatabaseInterface _rdi, String _str) throws RemoteException, MiddlewareShutdownInProgressException, MiddlewareException, MiddlewareRequestException {
        try {
            return getEntityGroup(null, _rdi, _str);
        } catch (SQLException sqlExc) {
            sqlExc.printStackTrace();
        }
        return null;
    }

    /**
     * getEntityGroup
     *
     * @param _rdi
     * @param _i
     * @throws java.rmi.RemoteException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @return
     *  @author David Bigelow
     */
    public EntityGroup getEntityGroup(RemoteDatabaseInterface _rdi, int _i) throws RemoteException, MiddlewareShutdownInProgressException, MiddlewareException, MiddlewareRequestException {
        try {
            return getEntityGroup(null, _rdi, _i);
        } catch (SQLException sqlExc) {
            sqlExc.printStackTrace();
        }
        return null;
    }

    /**
     * get the ~one and only~ Action Group for the Role/Entity
     *
     * @return ActionGroup
     * @param _strEntityType 
     */
    public ActionGroup getActionGroupByEntityType(String _strEntityType) {
        return (ActionGroup) m_hashAGs.get(_strEntityType);
    }

    /**
     * store the Action Group
     *
     * @param _ag 
     */
    public void setActionGroupForEntityType(ActionGroup _ag) {
        m_hashAGs.put(_ag.getEntityType(), _ag);
    }

    /**
     * Get the capability for one EntityGroup in this list based on a given roleCode.
     * Note that this roleCode may not be the same as the profile's roleCode.
     *
     * @return RoleCode's capability for this Entity ('N' if entity is not in list)
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @param _db
     * @param _strEntType
     * @param _strRoleCode 
     */
    public String getEntityCapabilityForRole(Database _db, String _strEntType, String _strRoleCode) throws SQLException, MiddlewareException, MiddlewareRequestException {
        String strCapability = "N";
        String strEntClass = "Entity";
        EntityGroup oEg = getEntityGroup(_strEntType);
        //if this entityType is not in our list then cap = "N"
        if (oEg == null) {
            return strCapability;
        }
        //if(oEg.isAssoc())
        //    strEntClass = "Assoc";
        //else if(oEg.isRelator())
        //    strEntClass = "Relator";
        try {
            strCapability = getCapability(_db, _strEntType, _strRoleCode, strEntClass);
        } catch (MiddlewareRequestException mre) {
            throw mre;
        } catch (MiddlewareException me) {
            throw me;
        } catch (SQLException sqle) {
            throw sqle;
        }
        return strCapability;
    }

    /**
     * Get the capability for one EntityGroup's MetaAttribute in this list based on a given roleCode, attCode.
     * Note that this roleCode may not be the same as the profile's roleCode.
     *
     * @return RoleCode's capability for this Attribute ('N' if attribute is not in list)
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @param _db
     * @param _strAttCode
     * @param _strRoleCode 
     */
    public String getAttributeCapabilityForRole(Database _db, String _strAttCode, String _strRoleCode) throws SQLException, MiddlewareException, MiddlewareRequestException {
        String strCapability = "N";
        String strEntClass = "Attribute";

        /*
               EntityGroup oEg = null;
               Vector vctEntTypes = getEntityTypesContainingAttribute(_db,_strAttCode, true);
               if(vctEntTypes.size() > 0) {
                   String strEntityType = (String)vctEntTypes.elementAt(0);
                   oEg = getEntityGroupWithAtts(_db,getEntityGroup(strEntityType));
               }
               if(oEg == null)
                   return strCapability;
               boolean bAttFound = false;
               for(int i = 0; i < oEg.getMetaAttributeCount(); i++) {
                   EANMetaAttribute oEmAtt = oEg.getMetaAttribute(i);
                   if(oEmAtt.getAttributeCode().equals(_strAttCode)) {
                       bAttFound = true;
                       i = oEg.getMetaAttributeCount(); //break
                   }
               }
               if(!bAttFound)
                   return strCapability;
        */
        try {
            strCapability = getCapability(_db, _strAttCode, _strRoleCode, strEntClass);
            _db.debug(D.EBUG_SPEW, "getAttributeCapabilityForRole.getCapability(_db," + _strAttCode + "," + _strRoleCode + "," + strEntClass + ") = " + strCapability);
        } catch (MiddlewareRequestException mre) {
            throw mre;
        } catch (MiddlewareException me) {
            throw me;
        } catch (SQLException sqle) {
            throw sqle;
        }
        _db.debug(D.EBUG_SPEW, "getAttributeCapabilityForRole method -> returning '" + strCapability + "'");
        return strCapability;
    }

    /**
     * Get all EntityGroups (w/ atts) that contain a given attribute
     *
     * @param _strAttCode the attribute to search for
     * @return EANList of EntityGroups populated w/ attributes
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @param _db 
     */
    public EANList getEntityGroupsContainingAttribute(Database _db, String _strAttCode) throws SQLException, MiddlewareException, MiddlewareRequestException {
        EANList eList = new EANList();
        Vector vctEntityTypes = getEntityTypesContainingAttribute(_db, _strAttCode, true);
        for (int i = 0; i < vctEntityTypes.size(); i++) {
            eList.put(getEntityGroup(_db, (String) vctEntityTypes.elementAt(i)));
        }
        return eList;
    }

    /**
     * Does the list contain this particular EntityType?
     *
     * @return boolean
     * @param _strEntityType 
     */
    public boolean containsEntityType(String _strEntityType) {
        return (getEntityGroup(_strEntityType) != null);
    }
    /**
     * Returns an EANList of the EntityGroups Managed by this Object
     *
     * @return EANList
     */
    protected EANList getEntityGroup() {
        return getData();
    }
    /**
     * Returns the number of EntityGroups managed by this object
     *
     * @return int
     */
    public int getEntityGroupCount() {
        return getDataCount();
    }

    /**
     * getExpiredEntityGroupCount
     *
     * @return
     *  @author David Bigelow
     */
    protected int getExpiredEntityGroupCount() {
        return m_oElExpired.size();
    }

    /**
     * getExpiredEntityGroup
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    protected EntityGroup getExpiredEntityGroup(int _i) {
        return (EntityGroup) m_oElExpired.getAt(_i);
    }

    /**
     * Get attribute codes which belong to an Entity based on Role
     *
     * @return Vector of attributeCodes
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @param _db 
     */
    public Vector getOwnedAttributes(Database _db) throws SQLException, MiddlewareException, MiddlewareRequestException {
        return getAttributes(_db, OWNED_ATTRIBUTES);
    }

    /**
     * Get orphaned attribute codes based on Role
     *
     * @return Vector of attributeCodes
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @param _db 
     */
    public Vector getOrphanedAttributes(Database _db) throws SQLException, MiddlewareException, MiddlewareRequestException {
        return getAttributes(_db, ORPHAN_ATTRIBUTES);
    }

    /**
     * Get ALL attribute codes based on Role
     *
     * @return Vector of attributeCodes
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @param _db 
     */
    public Vector getAllAttributes(Database _db) throws SQLException, MiddlewareException, MiddlewareRequestException {
        return getAttributes(_db, ALL_ATTRIBUTES);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////// PRIVATE ROUTINES /////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Refresh the attributes for all EntityGroups in the list which contain the indicated MetaAttribute and refresh cache accordingly.
     */
    private void refreshAllAttributeInstances(Database _db, EANMetaAttribute _att, boolean _bExpire) throws SQLException, MiddlewareException, MiddlewareRequestException {
       
        Vector vctEntTypes = null;
        EANList eList = getEntityGroupsContainingAttribute(_db, _att.getAttributeCode());
        for (int i = 0; i < eList.size(); i++) {
            EntityGroup eg = (EntityGroup) eList.getAt(i);
            if (!_bExpire) {
                eg.putMetaAttribute(_att);
            } else {
                eg.removeMetaAttribute(_att);
                //_db.debug(D.EBUG_SPEW,"GB - MetaEntityList.refreshAllAttributeInstances:removeMetaAttribute("+_att.getAttributeCode()+")");
            }
            //now update
            putData(eg);
            //now update local copy since we have it.
            refreshEntityGroupCache(_db, eg);
        }
        //this part should NOT be role sensitive!
        vctEntTypes = getEntityTypesContainingAttribute(_db, _att.getAttributeCode(), false);
        for (int i = 0; i < vctEntTypes.size(); i++) {
            String strEntType = (String) vctEntTypes.elementAt(i);
            //First clear out all references to this entity for ALL roles and ALL nls's - yes this is necessary.
            //For example we could have removed an att from an ent - in this case we need to propogate changes.
            //The dilemma is we just dont know where else this att is used - so force all referencing EGs to
            //be rebuilt.
            new MetaCacheManager(getProfile()).expireEGCacheAllRolesAllNls(_db, strEntType);
        }
    }

    /**
     * Retreive all of the entityTypes that contain a given attribute code
     *
     * @param _db
     * @param _strAttCode
     * @param _bRoleSensitive
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @return Vector
     */
    protected Vector getEntityTypesContainingAttribute(Database _db, String _strAttCode, boolean _bRoleSensitive) throws SQLException, MiddlewareException, MiddlewareRequestException {
        Vector vctEntityTypes = null;
        try {
            ReturnDataResultSet rdrs = null;
            ResultSet rs = null;
            String strNow = _db.getDates().getNow();
            boolean success = false;
            try {
                rs = _db.callGBL7507(new ReturnStatus(-1), getProfile().getEnterprise(), "Entity/Attribute", _strAttCode, "EntityAttribute", strNow, strNow);
                rdrs = new ReturnDataResultSet(rs);
                success = true;
            } finally {
            	if(rs!=null){
            		try{
            			rs.close();
            		}catch(SQLException x){
            			_db.debug(D.EBUG_DETAIL, "MetaEntityList "+getKey()+": ERROR failure closing ResultSet "+x);
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
            vctEntityTypes = new Vector();
            for (int row = 0; row < rdrs.getRowCount(); row++) {
                String strEntityType = rdrs.getColumn(row, 0);
                String s2 = rdrs.getColumn(row, 1);
                _db.debug(D.EBUG_SPEW, "gbl7507 answers: " + strEntityType + ":" + s2);
                //this guy might not be visible to this role
                if (containsEntityType(strEntityType) || !_bRoleSensitive) {
                    vctEntityTypes.addElement(strEntityType);
                }
            }
        } finally {
            _db.freeStatement();
            _db.isPending();
        }
        return vctEntityTypes;
    }

    /**
     * if the EntityGroup does not currently contain any attributes -> set these up and replace this E.G. in the list
     * else -> return the E.G. unchanged
     * - this will prevent us from having to hit the database every time we grab an E.G.
     */
    private synchronized EntityGroup getEntityGroupWithAtts(Database _db, RemoteDatabaseInterface _rdi, EntityGroup _oEg) throws RemoteException, SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {
        if (_oEg.getMetaAttributeCount() == 0) {
            if (_db != null) {
                _oEg = new EntityGroup(this, _db, getProfile(), _oEg.getEntityType(), "Edit");
            
            } else {
                _oEg = _rdi.getEntityGroup(getProfile(), _oEg.getEntityType(), "Edit");
            }
        }
        putData(_oEg);
        return _oEg;
    }

    /**
     *
     */
    private Vector getAttributes(Database _db, int _iType) throws SQLException, MiddlewareException, MiddlewareRequestException {
        Vector v = new Vector();
        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;
        String strEnterprise = getProfile().getEnterprise();
        String strRoleCode = getProfile().getRoleCode();
        int iNlsId = getProfile().getReadLanguage().getNLSID();
        boolean success = false;
        try {
            rs = _db.callGBL7514(new ReturnStatus(-1), strEnterprise, strRoleCode, iNlsId);
            rdrs = new ReturnDataResultSet(rs);
            success = true;
        } finally {
        	if(rs != null){
        		try{
        			rs.close();
        		}catch(SQLException x){
        			_db.debug(D.EBUG_DETAIL, "MetaEntityList "+getKey()+": ERROR failure closing ResultSet "+x);
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
            String strAttCode = rdrs.getColumn(row, 0);
            String strAttDesc = rdrs.getColumn(row, 3);
            String strAttType = rdrs.getColumn(row, 1);
            String strIsOrphan = rdrs.getColumn(row, 8);
            String[] info = new String[] { strAttCode, strAttDesc, strAttType };
            if (_iType == ALL_ATTRIBUTES) {
                v.addElement(info);
            
            } else if (_iType == OWNED_ATTRIBUTES) {
                if (strIsOrphan.equals("Owned")) {
                    v.addElement(info);
                }
            } else if (_iType == ORPHAN_ATTRIBUTES) {
                if (strIsOrphan.equals("Orphan")) {
                    v.addElement(info);
                }
            }
        }
        return v;
    }


    /**
     * dump contents - either for html or standard out
     */
    private String dump(String _strNewLine, boolean _bBrief) {
        StringBuffer strBuf = new StringBuffer();
        for (int i = 0; i < getEntityGroupCount(); i++) {
            strBuf.append(getEntityGroup(i).getEntityType());
            strBuf.append(_strNewLine);
        }
        return strBuf.toString();
    }

    /**
     * simple check to see if this entityType is in the rdrs
     */
    private boolean isEntityTypeValid(ReturnDataResultSet _rdrs, String _strEntityType) {
        boolean bIsValid = false;
        for (int row = 0; row < _rdrs.getRowCount(); row++) {
            String s = _rdrs.getColumn(row, 0).trim();
            if (s.equals(_strEntityType)) {
                bIsValid = true;
                break;
            }
        }
        return bIsValid;
    }

    /**
     * Execute GBL7503
     * used for any entityClass + Attributes
     */
    private String getCapability(Database _db, String _strEntType, String _strRoleCode, String _strEntClass) throws SQLException, MiddlewareException, MiddlewareRequestException {
        String strCapability = "N";
        ResultSet rs = null;
        boolean success = false;
        try {
            rs = _db.callGBL7503(new ReturnStatus(-1), getProfile().getEnterprise(), "Role/" + _strEntClass, _strRoleCode, _strEntType, "Capability", getProfile().getValOn(), getProfile().getEffOn());
            if (rs.next()) {
                strCapability = rs.getString(1).trim();
            }
            success = true;
        } finally {
        	if(rs != null){
        		try{
        			rs.close();
        		}catch(SQLException x){
        			_db.debug(D.EBUG_DETAIL, "MetaEntityList "+getKey()+": ERROR failure closing ResultSet "+x);
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
        _db.debug(D.EBUG_SPEW, "gbl7503 answer is:" + strCapability);
        return strCapability;
    }

    /**
     * the purpose
     */
    private String getPurpose() {
        return m_strPurpose;
    }

    /**
     * the purpose
     */
    private void setPurpose(String _s) {
        m_strPurpose = _s;
    }

    ///////////////////////
    ////// Static methods
    ///////////////////////

    /**
     * Build a new EANMetaAttribute
     * String _strAttCode the attribute code
     * String _strAttType the attribute type (e.g. 'T','F'...)
     * String _strCap the initial capability for the passed profile
     *
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @return EANMetaAttribute
     * @param _parent
     * @param _prof
     * @param _strAttCode
     * @param _strAttType
     * @param _strCap 
     */
    public static EANMetaAttribute getNewMetaAttribute(EANMetaFoundation _parent, Profile _prof, String _strAttCode, String _strAttType, String _strCap) throws MiddlewareRequestException {
        EANMetaAttribute oAtt = null;

        switch (_strAttType.charAt(0)) {
        case 'T' :
        case 'I' :
            //NOTE: cpabilities are in relation to the passed RoleCode, NOT the profile
            oAtt = new MetaTextAttribute(_parent, _prof, _strAttCode, _strAttType, _strCap);
            break;
        case 'L' :
            oAtt = new MetaLongTextAttribute(_parent, _prof, _strAttCode, _strAttType, _strCap);
            break;
        case 'F' :
            oAtt = new MetaMultiFlagAttribute(_parent, _prof, _strAttCode, _strAttType, _strCap);
            break;
        case 'U' :
            oAtt = new MetaSingleFlagAttribute(_parent, _prof, _strAttCode, _strAttType, _strCap);
            break;
        case 'S' :
            oAtt = new MetaStatusAttribute(_parent, _prof, _strAttCode, _strAttType, _strCap);
            break;
        case 'A' :
            oAtt = new MetaTaskAttribute(_parent, _prof, _strAttCode, _strAttType, _strCap);
            break;
        case 'X' :
            oAtt = new MetaXMLAttribute(_parent, _prof, _strAttCode, _strAttType, _strCap);
            break;
        case 'B' :
            oAtt = new MetaBlobAttribute(_parent, _prof, _strAttCode, _strAttType, _strCap);
            break;
        default :
            break;
        }
        return oAtt;
    }

    /**
     * Get one attribute if it is visible to a given roleCode, null if not found
     *
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @return EANMetaAttribute
     * @param _db
     * @param _prof
     * @param _strAttCode
     * @param _strRoleCode
     * @param _iNlsId 
     */
    public static EANMetaAttribute getMetaAttributeForRole(Database _db, Profile _prof, String _strAttCode, String _strRoleCode, int _iNlsId) throws SQLException, MiddlewareException, MiddlewareRequestException {
        EANMetaAttribute oAtt = null;
        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;
        String strEnterprise = _prof.getEnterprise();

        boolean success = false;
        try {
            rs = _db.callGBL7514(new ReturnStatus(-1), strEnterprise, _strRoleCode, _iNlsId);
            rdrs = new ReturnDataResultSet(rs);
            success = true;
        } finally {
        	if(rs != null){
        		try{
        			rs.close();
        		}catch(SQLException x){
        			_db.debug(D.EBUG_DETAIL, "MetaEntityList.getMetaAttributeForRole: ERROR failure closing ResultSet "+x);
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
            String strAttCodeComp = rdrs.getColumn(row, 0);
            if (strAttCodeComp.equals(_strAttCode)) {
                String strAttType = rdrs.getColumn(row, 1);
                String strShortDesc = rdrs.getColumn(row, 2);
                String strLongDesc = rdrs.getColumn(row, 3);
                String strCap = rdrs.getColumn(row, 5);

                switch (strAttType.charAt(0)) {
                case 'T' :
                case 'I' :
                    //NOTE: cpabilities are in relation to the passed RoleCode, NOT the profile
                    oAtt = new MetaTextAttribute(null, _prof, _strAttCode, strAttType, strCap);
                    break;
                case 'L' :
                    oAtt = new MetaLongTextAttribute(null, _prof, _strAttCode, strAttType, strCap);
                    break;
                case 'F' :
                    oAtt = new MetaMultiFlagAttribute(null, _prof, _strAttCode, strAttType, strCap);
                    break;
                case 'U' :
                    oAtt = new MetaSingleFlagAttribute(null, _prof, _strAttCode, strAttType, strCap);
                    break;
                case 'S' :
                    oAtt = new MetaStatusAttribute(null, _prof, _strAttCode, strAttType, strCap);
                    break;
                case 'A' :
                    oAtt = new MetaTaskAttribute(null, _prof, _strAttCode, strAttType, strCap);
                    break;
                case 'X' :
                    oAtt = new MetaXMLAttribute(null, _prof, _strAttCode, strAttType, strCap);
                    break;
                case 'B' :
                    oAtt = new MetaBlobAttribute(null, _prof, _strAttCode, strAttType, strCap);
                    break;
                default :
                    break;
                }
                oAtt.putShortDescription(strShortDesc);
                oAtt.putLongDescription(strLongDesc);
                break;
            }
        }
        return oAtt;
    }

    /**
     * Query the Database to determine if this is an existing attributeCode
     * is really the same as isNewEntityType method
     *
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @return boolean
     * @param _db
     * @param _prof
     * @param _strAttributeCode 
     */
    public static boolean isNewAttributeCode(Database _db, Profile _prof, String _strAttributeCode) throws SQLException, MiddlewareException, MiddlewareRequestException {
        return isNewEntityType(_db, _prof, _strAttributeCode);
    }

    /**
     * Query the Database to determine if this is an existing EntityType
     *
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @return boolean
     * @param _db
     * @param _prof
     * @param _strEntityType 
     */
    public static boolean isNewEntityType(Database _db, Profile _prof, String _strEntityType) throws SQLException, MiddlewareException, MiddlewareRequestException {
        String strEnterprise = _prof.getEnterprise();
        String strValOn = _prof.getValOn();
        String strEffOn = _prof.getEffOn();
        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;
        boolean success = false;
        try {
            rs = _db.callGBL7506(new ReturnStatus(-1), strEnterprise, _strEntityType, strValOn, strEffOn);
            rdrs = new ReturnDataResultSet(rs);
            success = true;
            if (rdrs.getRowCount() > 0) {
                return false;
            }
        } finally {
        	if (rs !=null){
        		try{
        			rs.close();
        		}catch(SQLException x){
        			_db.debug(D.EBUG_DETAIL, "MetaEntityList.isNewEntityType(): ERROR failure closing ResultSet "+x);
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

        return true;
    }

    /**
     * Query the Database to determine if this is an existing flagCode
     *
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @return boolean
     * @param _db
     * @param _prof
     * @param _strParentAttCode
     * @param _strFlagCode 
     */
    public static boolean isNewFlagCode(Database _db, Profile _prof, String _strParentAttCode, String _strFlagCode) throws SQLException, MiddlewareException, MiddlewareRequestException {
        String strEnterprise = _prof.getEnterprise();
        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;
        try {
            rs = _db.callGBL7511(new ReturnStatus(-1), strEnterprise, _strParentAttCode, _strFlagCode);
            rdrs = new ReturnDataResultSet(rs);
            if (rdrs.getRowCount() > 0) {
                return false;
            }
        } finally {
          	if (rs !=null){
        		try{
        			rs.close();
        		}catch(SQLException x){
        			_db.debug(D.EBUG_DETAIL, "MetaEntityList.isNewFlagCode(): ERROR failure closing ResultSet "+x);
        		}
        		rs = null;
        	}
            
            _db.freeStatement();
            _db.isPending();
        }

        return true;
    }

    /**
     * Get a list of possible attributeTypes + their descriptions
     *
     * @return rdrs: col[0] = attClass, col[1] = shortDesc, col[2] = longDesc
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @param _db 
     */
    public static ReturnDataResultSet getPossibleAttributeTypes(Database _db) throws SQLException, MiddlewareException, MiddlewareRequestException {

        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;
        boolean success = false;
        
        try {
            rs = _db.callGBL7510(new ReturnStatus(-1));
            rdrs = new ReturnDataResultSet(rs);
            success = true;
        } finally {
          	if (rs !=null){
        		try{
        			rs.close();
        		}catch(SQLException x){
        			_db.debug(D.EBUG_DETAIL, "MetaEntityList.getPossibleAttributeTypes(): ERROR failure closing ResultSet "+x);
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
        return rdrs;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////// CACHE /////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Reset an EntityGroup's cache by effectively expiring it.
     *
     * @param _db
     * @param _oEg 
     */
    public void expireEntityGroupCache(Database _db, EntityGroup _oEg) {
        //_oEg.expireCache(_db);
        try {
            new MetaCacheManager(getProfile()).expireEGCacheAllRolesAllNls(_db, _oEg.getEntityType());
        } catch (Exception exc) {
            _db.debug(D.EBUG_ERR, "MetaEntityList.expireEntityGroupCache for " + _oEg.getEntityType() + ": " + exc.toString());
        }
    }

    /**
     * Reset an EntityGroup's cache by effectively expiring it.
     *
     * @param _db
     * @param _oEg 
     */
    public void refreshEntityGroupCache(Database _db, EntityGroup _oEg) {
        _oEg.putCache(_db);
    }

    /**
     * reset ALL caches - for both this MetaEntityList and EntityGroups contained in the list
     */
    //protected void resetAllCaches(Database _db) {
    //    for(int i = 0; i < getEntityGroupCount(); i++) {
    //        EntityGroup eg = getEntityGroup(i);
    //        expireEntityGroupCache(_db, eg);
    //    }
    // }

    ///////////////////////////////////////////////////////////////////////////////////////////
    //////// MISC
    ///////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Cleans up all internal structures
     */
    public void dereference() {
        for (int ii = getEntityGroupCount() - 1; ii >= 0; ii--) {
            EntityGroup eg = getEntityGroup(ii);
            removeEntityGroup(eg);
            eg.dereference();
        }
        if (m_oElExpired!=null){
        	m_oElExpired.clear();
        	m_oElExpired = null;
        }
        if (m_hashAGs!=null){
        	m_hashAGs.clear();
        	m_hashAGs = null;
        }
        
        m_strPurpose = null;
        if (m_SFInfo!=null){
        	m_SFInfo.dereference();
        	m_SFInfo = null;
        }

        super.dereference();
    }

    /**
     * dump - standard
     *
     * @return String
     * @param _bBrief 
     */
    public String dump(boolean _bBrief) {
        return dump(NEW_LINE, _bBrief);
    }

    /**
     * dump in Html format
     *
     * @return String
     * @param _bBrief 
     */
    public String dumpHtml(boolean _bBrief) {
        return dump("<BR>", _bBrief);
    }

    /**
     * Make a serialized copy of myself
     *
     * @return MetaEntityList
     * @param _b 
     */
    public MetaEntityList clone(boolean _b) {

        //Serialization approach...
        MetaEntityList clone = null;
        byte[] byteArray = null;
        ByteArrayOutputStream BAout = null;
        ByteArrayInputStream BAin = null;
        ObjectOutputStream Oout = null;
        ObjectInputStream Oin = null;

        try {
            //put object into stream
            
            BAout = new ByteArrayOutputStream();
            Oout = new ObjectOutputStream(BAout);
            Oout.writeObject(this);
            Oout.flush();
            Oout.reset();
            
            byteArray = BAout.toByteArray();
            
            //now turn around and pull object out of stream
            BAin = new ByteArrayInputStream(byteArray);
            Oin = new ObjectInputStream(BAin);
            clone = (MetaEntityList) Oin.readObject();
            byteArray = null;
        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            try {
                Oout.close();
                Oin.close();
                BAout.close();
                BAin.close();
                byteArray = null;
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally { 
            }
        }  
        return clone;
    }

    /**
     * Returns true if all entityTypes are the same...order doesn't matter
     *
     * @return boolean
     * @param _el 
     */
    public boolean equivalent(MetaEntityList _el) {
        int iCount = _el.getEntityGroupCount();
        if (iCount != this.getEntityGroupCount()) {
            return false;
        }
        //since sizes are the same, we only need to go through this once...
        for (int i = 0; i < iCount; i++) {
            boolean wasFound = false;
            String s1 = _el.getEntityGroup(i).getEntityType();
            for (int j = 0; j < iCount; j++) {
                String s2 = this.getEntityGroup(j).getEntityType();
                if (s2.equals(s1)) {
                    wasFound = true;
                    j = iCount; //break
                }
            }
            if (!wasFound) {
                return false;
            }
        }
        //if we made it here, then they're equivalent
        return true;
    }

    //////
    // SORT FILTER INFO
    //////

    /**
     * dumpSFInfo
     *
     * @return
     *  @author David Bigelow
     */
    public String dumpSFInfo() {
        return getSFInfo().dump();
    }

    /**
     * set FilterType on EntityGroups
     *
     * @param _strFilterType 
     */
    public void setFilterType(String _strFilterType) {
        getSFInfo().setFilterType(_strFilterType);
    }

    /**
     * getFilterType
     *
     * @return
     *  @author David Bigelow
     */
    public String getFilterType() {
        return getSFInfo().getFilterType();
    }

    /**
     * set Filter on MetaRoles
     *
     * @param _strFilter 
     */
    public void setFilter(String _strFilter) {
        getSFInfo().setFilter(_strFilter);
    }

    /**
     * getFilter
     *
     * @return
     *  @author David Bigelow
     */
    public String getFilter() {
        return getSFInfo().getFilter();
    }

    /**
     * reset Filter on MetaRoles
     */
    public void resetFilter() {
        getSFInfo().resetFilter();
        resetDisplayableEntityGroups();
    }

    /**
     * set the sort type
     *
     * @param _s 
     */
    public void setSortType(String _s) {
        getSFInfo().setSortType(_s);
    }

    /**
     * getSortType
     *
     * @return
     *  @author David Bigelow
     */
    public String getSortType() {
        return getSFInfo().getSortType();
    }

    /**
     * setAscending
     *
     * @param _b
     *  @author David Bigelow
     */
    public void setAscending(boolean _b) {
        getSFInfo().setAscending(_b);
    }

    /**
     * isAscending
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isAscending() {
        return getSFInfo().isAscending();
    }

    private void resetDisplayableEntityGroups() {
        for (int i = 0; i < getEntityGroupCount(); i++) {
            setDisplayableForFilter(i, true);
        }
    }

    private void setDisplayableForFilter(int _i, boolean _b) {
        EntityGroup eg = getEntityGroup(_i);
        eg.setDisplayableForFilter(_b);
    }

    /**
     * isEntityGroupDisplayableForFilter
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public boolean isEntityGroupDisplayableForFilter(int _i) {
        EntityGroup eg = getEntityGroup(_i);
        return eg.isDisplayableForFilter();
    }

    /**
     * the way we want to do this is:
     *   - retain last sort by until explicitely set
     *   - if the last sort was the same type: if asc -> do desc, and visa-versa
     *   - retain last filter/filter type
     *   - if m_strFilter != null -> apply filter
     *
     * @param _bUseWildcardsForfilter
     * @concurrency $none
     */
    public synchronized void performSortFilter(boolean _bUseWildcardsForfilter) {
        //first go through and set the displayable
        //  - note that it is up to the calling party to check for isDisplayable on MetaRoles

        resetDisplayableEntityGroups();
        if (getSFInfo().getFilter() != null) {
            String strFilter = getSFInfo().getFilter();
            for (int i = 0; i < getEntityGroupCount(); i++) {
                String strToCompare = null;
                if (getSFInfo().getFilterType().equals(FILTER_BY_SHORT_DESCRIPTION)) {
                    strToCompare = getShortDescription(i);
                
                } else if (getSFInfo().getFilterType().equals(FILTER_BY_ENTITY_TYPE)) {
                    strToCompare = getEntityType(i);
                
                } else if (getSFInfo().getFilterType().equals(FILTER_BY_ENTITY_CLASS)) {
                    strToCompare = getEntityClass(i);
                
                } else { //default
                    strToCompare = getLongDescription(i);
                }
                if (!_bUseWildcardsForfilter) {
                    if (strToCompare.length() < strFilter.length()) {
                        setDisplayableForFilter(i, false);
                    } else if (!strToCompare.substring(0, strFilter.length()).equalsIgnoreCase(strFilter)) {
                        setDisplayableForFilter(i, false);
                    }
                } else {
                    if (!SortFilterInfo.equalsWithWildcards(strFilter, strToCompare, new char[] { '*', '%' }, true)) {
                        setDisplayableForFilter(i, false);
                    }
                }
            }
        }

        //now do sort
        sortList(getSFInfo().getSortType(), getSFInfo().isAscending());

    }

    /**
     * rearrange the list so that it is sorted alphabetically by the specified type
     *
     * @param _strSortType
     * @param _bAscending
     * @concurrency $none
     */
    public synchronized void sortList(String _strSortType, boolean _bAscending) {

        EntityGroup[] aEg = new EntityGroup[getEntityGroupCount()];
        EANComparator ec = new EANComparator(_bAscending);
        for (int i = 0; i < getEntityGroupCount(); i++) {
            EntityGroup eg = getEntityGroup(i);
            eg.setSortType(_strSortType);
            aEg[i] = eg;
        }
        Arrays.sort(aEg, ec);
        resetData();
        for (int i = 0; i < aEg.length; i++) {
            EntityGroup eg = aEg[i];
            putData(eg);
        }
    }

    /**
     * setDependentFlagList
     *
     * @param _eg
     * @param _dfl
     * @return
     *  @author David Bigelow
     */
    public EntityGroup setDependentFlagList(EntityGroup _eg, DependentFlagList _dfl) {
        _eg.setDependentFlagList(_dfl);
        return _eg;
    }

    /**
     * Expire all Action Item-related MetaData in the MetaLinkAttr table
     *
     * @param _db
     * @param _prof
     * @param _strEntityType
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws java.sql.SQLException
     * @return boolean
     */
    public static boolean expireActionsForEntity(Database _db, Profile _prof, String _strEntityType) throws MiddlewareException, MiddlewareRequestException, SQLException {
 
        boolean bUpdatesPerformed = false;

        ReturnDataResultSet rdrs7511 = null;
        ReturnDataResultSet rdrs7506 = null;
    
        ReturnDataResultSet rdrs = null;

        ResultSet rs = null;

        String strEnterprise = _prof.getEnterprise();
 
        if (!isNewEntityType(_db, _prof, _strEntityType)) {
            throw new MiddlewareRequestException("<I>" + _strEntityType + "</I> is an existing Entity Type!");
        }
        boolean success = false;

        try {
 
            String strNow = _db.getDates().getNow();
            Vector vctKeys = new Vector();
            
            try {
                rs = _db.callGBL7536(new ReturnStatus(-1), strEnterprise, _strEntityType);
                rdrs = new ReturnDataResultSet(rs);
                success = true;
            } finally {
              	if (rs !=null){
            		try{
            			rs.close();
            		}catch(SQLException x){
            			_db.debug(D.EBUG_DETAIL, "MetaEntityList.expireActionsForEntity(): ERROR failure closing ResultSet "+x);
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
                String strLinkType = rdrs.getColumn(row, 0);
                String strLinkType1 = rdrs.getColumn(row, 1);
                String strLinkType2 = rdrs.getColumn(row, 2);
                String strLinkCode = rdrs.getColumn(row, 3);
                String strLinkValue = rdrs.getColumn(row, 4);
                String strValFrom = rdrs.getColumnDate(row, 5);
                String strEffFrom = rdrs.getColumnDate(row, 7);
                String strActionKey = rdrs.getColumn(row, 11);
                if (!vctKeys.contains(strActionKey)) {
                    vctKeys.addElement(strActionKey);
                }
                new MetaLinkAttrRow(_prof, strLinkType, strLinkType1, strLinkType2, strLinkCode, strLinkValue, strValFrom, strNow, strEffFrom, strNow, 2).updatePdh(_db);
                bUpdatesPerformed = true;
            }
            //now do MetaEntity, MetaDescription
            for (int i = 0; i < vctKeys.size(); i++) {
                String strKey = (String) vctKeys.elementAt(i);
                success = false;
                try {
                    rs = _db.callGBL7506(new ReturnStatus(-1), strEnterprise, strKey, strNow, strNow);
                    rdrs7506 = new ReturnDataResultSet(rs);
                    success = true;
                } finally {
                  	if (rs !=null){
                		try{
                			rs.close();
                		}catch(SQLException x){
                			_db.debug(D.EBUG_DETAIL, "MetaEntityList.expireActionsForEntity(): ERROR failure closing ResultSet "+x);
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
                // there really should only be ONE row of these, although db2 unique indexes would allow multiple
                for (int j = 0; j < rdrs7506.getRowCount(); j++) {
                	success = false;
                    String strEntityClass = rdrs7506.getColumn(j, 2);
                    String strValFrom = rdrs7506.getColumn(j, 3);
                    String strEffFrom = rdrs7506.getColumn(j, 5);
                    new MetaEntityRow(_prof, strKey, strEntityClass, strValFrom, strNow, strEffFrom, strNow, 2).updatePdh(_db);
                    try {
                        rs = _db.callGBL7511(new ReturnStatus(-1), strEnterprise, strKey, strEntityClass);
                        rdrs7511 = new ReturnDataResultSet(rs);
                        success =true;
                    } finally {
                      	if (rs !=null){
                    		try{
                    			rs.close();
                    		}catch(SQLException x){
                    			_db.debug(D.EBUG_DETAIL, "MetaEntityList.expireActionsForEntity(): ERROR failure closing ResultSet "+x);
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
                    for (int k = 0; k < rdrs7511.getRowCount(); k++) {
                        int iNLSID = rdrs7511.getColumnInt(k, 0);
                        String strShortDescription = rdrs7506.getColumn(k, 1);
                        String strLongDescription = rdrs7511.getColumn(k, 2);
                        strValFrom = rdrs7511.getColumn(k, 3);
                        strEffFrom = rdrs7511.getColumn(k, 5);
                        new MetaDescriptionRow(_prof, strKey, strEntityClass, strShortDescription, strLongDescription, iNLSID, strValFrom, strNow, strEffFrom, strNow, 2).updatePdh(_db);
                    }
                }
            }
        } finally {
            _db.freeStatement();
            _db.isPending();
        }
        return bUpdatesPerformed;
    }

    /**
     * Does this MetaFlag have any data Associated with it in the PDH?
     * (for ~ANY~ Role, same Enterprise)
     *
     * @param _db
     * @param _prof
     * @param _emfAtt
     * @param _mf
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws java.sql.SQLException
     * @return boolean
     */
    public static boolean hasAssociatedData(Database _db, Profile _prof, EANMetaFlagAttribute _emfAtt, MetaFlag _mf) throws MiddlewareException, SQLException {
        ResultSet rs = null;

        String strEnterprise = _prof.getEnterprise();
        String strNow = _db.getDates().getNow();
        String strAttributeCode = _emfAtt.getAttributeCode();
        String strFlagCode = _mf.getFlagCode();
        boolean bHasData = false;
        boolean success = false;
        try {
            rs = _db.callGBL7535(new ReturnStatus(-1), strEnterprise, strAttributeCode, strFlagCode, strNow, strNow);
            if (rs.next()) { 
                bHasData = true;
            }
            success =true;
        } finally {
          	if (rs !=null){
        		try{
        			rs.close();
        		}catch(SQLException x){
        			_db.debug(D.EBUG_DETAIL, "MetaEntityList.hasAssociatedData(): ERROR failure closing ResultSet "+x);
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
        return bHasData;
    }

    /**
     * dump it for now..
     *
     * @return String
     */
    public String toString() {
        return dump(true);
    }

    /**
    * $Id: MetaEntityList.java,v 1.131 2012/11/08 21:59:12 wendy Exp $
    * @return the date/time this class was generated
    */
    public String getVersion() {
        return "$Id: MetaEntityList.java,v 1.131 2012/11/08 21:59:12 wendy Exp $";
    }
}
