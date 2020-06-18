//
//$Log: MetaRole.java,v $
//Revision 1.32  2005/03/08 23:15:46  dave
//Jtest checkins from today and last ngith
//
//Revision 1.31  2005/01/18 21:46:51  dave
//more parm debug cleanup
//
//Revision 1.30  2003/01/03 22:43:26  gregg
//returning a boolean on entity, attribute meta update methods indicating whether or not any pdh updates were actually made.
//
//Revision 1.29  2002/10/16 23:13:46  gregg
//in updatePdhCapForAttribute: setCapability of at as well
//
//Revision 1.28  2002/10/15 23:34:26  gregg
//reset all EntityGroup (for one entityType) caches when update roles for entities...
//
//Revision 1.27  2002/09/06 17:38:47  gregg
//SortFilterInfo now uses String sort/filter key constants (were ints)
//
//Revision 1.26  2002/08/08 22:17:03  gregg
//getCompareField(), setCompareField(String) methods required by EANComparable inteface
//
//Revision 1.25  2002/07/27 00:17:21  gregg
//some Role/Attribute updates
//
//Revision 1.24  2002/07/24 23:39:36  gregg
//some cache management for entitygroups
//
//Revision 1.23  2002/07/24 22:40:17  gregg
//syntax
//
//Revision 1.22  2002/07/24 22:31:37  gregg
//set capability on attribute when updating to DB
//
//Revision 1.21  2002/07/24 17:58:33  gregg
//refresh all caches in updatePdhCapForAttribute method
//
//Revision 1.20  2002/07/18 16:39:00  gregg
//isDisplayable param change
//
//Revision 1.19  2002/07/18 00:07:17  gregg
//made isDisplayable public
//
//Revision 1.18  2002/07/17 20:42:21  gregg
//displayable attributes
//
//Revision 1.17  2002/06/18 22:29:27  gregg
//fix for updatePdhCapForEntities
//
//Revision 1.16  2002/06/18 21:55:58  gregg
//some debugging
//
//Revision 1.15  2002/06/18 00:01:41  gregg
//fix for updatePdhCapForEntities
//
//Revision 1.14  2002/06/17 21:22:15  gregg
//updatePdhCapForEntities method
//
//Revision 1.13  2002/06/17 18:06:33  gregg
//getAllCapabilities method
//
//Revision 1.12  2002/06/14 21:57:21  gregg
//implement EANComparable for toCompareString
//
//Revision 1.11  2002/03/27 00:10:11  gregg
//updatePdhCapForEntity(), updatePdhCapForAttribute() updates
//
//Revision 1.10  2002/03/26 00:03:12  gregg
//updatePdhCapForEntity(), updatePdhCapForAttribute()
//
//Revision 1.9  2002/03/25 21:05:33  gregg
//update stuff
//
//Revision 1.8  2002/03/22 19:21:54  gregg
//updatePdhCapForEntity(), updatePdhCapForAttribute()
//
//Revision 1.7  2002/03/22 19:15:16  gregg
//updatePdhMetaCopyRole()
//
//Revision 1.6  2002/03/22 02:01:04  gregg
//updatePdh stuff
//
//Revision 1.5  2002/03/22 00:16:07  gregg
//syntax
//
//Revision 1.4  2002/03/22 00:08:33  gregg
//getMaintainers()
//
//Revision 1.3  2002/03/20 23:18:42  gregg
//updatePdhMeta(), expirePdhMeta()
//
//Revision 1.2  2002/03/20 22:28:22  gregg
//updatePdhDescription()
//
//Revision 1.1  2002/03/13 17:52:47  gregg
//initial load
//
//

package COM.ibm.eannounce.objects;

import java.util.Vector;
import java.sql.ResultSet;
import java.sql.SQLException;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;

/**
 * Manages one MetaRole
 */
public class MetaRole extends EANMetaFoundation implements EANComparable {

    /**
     * FIELD
     */
    public static final String SORT_BY_ROLECODE = "Role Code";
    /**
     * FIELD
     */
    public static final String SORT_BY_ROLEDESC = "Role Description";
    private String m_strSortType = SORT_BY_ROLEDESC;
    private boolean m_bDisplayable = true;

    /**
     * Create us a MetaRole w/ RoleCode and Description
     *
     * @param _em
     * @param _oProfile
     * @param _strRoleCode
     * @param _strDescription
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public MetaRole(EANMetaEntity _em, Profile _oProfile, String _strRoleCode, String _strDescription) throws SQLException, MiddlewareException, MiddlewareRequestException {
        super(_em, _oProfile, _strRoleCode);
        //set these here for convenience...if we want separate short/long descrips, we'll need to set these explicitely..
        putLongDescription(_strDescription);
        putShortDescription(_strDescription);
        return;
    }

    /**
     * Perform a simple test of this class
     *
     * @param args 
     */
    public static void main(String[] args) {
    }

    /**
     * Get a list of Maintainers for this Role
     *
     * @return Vector
     * @param _db
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public Vector getMaintainers(Database _db) throws SQLException, MiddlewareException, MiddlewareRequestException {

        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;
        String strEnterprise = getProfile().getEnterprise();
        int iNLSID = getProfile().getReadLanguage().getNLSID();
        int iOPWGID = getProfile().getOPWGID();
        String strValOn = getProfile().getValOn();
        Vector v = new Vector();

        try {
            rs = _db.callGBL7515(new ReturnStatus(-1), strEnterprise, getRoleCode(), iNLSID, iOPWGID, strValOn);
            rdrs = new ReturnDataResultSet(rs);
        } finally {
            rs.close();
            rs = null;
            _db.commit();
            _db.freeStatement();
            _db.isPending();
        }

        for (int row = 0; row < rdrs.getRowCount(); row++) {
            //col 0 is desc
            String s1 = rdrs.getColumn(row, 1); //roleCode of maintainer
            String s2 = rdrs.getColumn(row, 2); //nlsId
            int i2 = 1;
            try {
                i2 = Integer.parseInt(s2);
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }
            if (i2 == iNLSID) {
                v.addElement(s1);
            }
        }
        return v;
    }

    /**
     * set the maintainers & update Pdh
     *
     * @param _db
     * @param _vctMaintainers
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public void updatePdhMaintainers(Database _db, Vector _vctMaintainers) throws SQLException, MiddlewareException, MiddlewareRequestException {

        String strNow = _db.getDates().getNow();
        String strForever = _db.getDates().getForever();

        Vector vctMaintainers_OG = getMaintainers(_db);

        System.err.println("OG:" + vctMaintainers_OG.toString());
        System.err.println("new:" + _vctMaintainers.toString());

        //expire all rows that are in OG, !in new...
        for (int i = 0; i < vctMaintainers_OG.size(); i++) {
            String strMaint_OG = (String) vctMaintainers_OG.elementAt(i);
            boolean bFound = false;
            for (int j = 0; j < _vctMaintainers.size(); j++) {
                String strMaint = (String) _vctMaintainers.elementAt(j);
                if (strMaint.equals(strMaint_OG)) {
                    bFound = true;
                    j = _vctMaintainers.size();
                }
            }
            if (!bFound) {
                //expire strMaint_OG
                MetaLinkAttrRow oMlaRow = new MetaLinkAttrRow(getProfile(), "Role/Maintainer", getRoleCode(), strMaint_OG, "Capability", "L", strNow, strNow, strNow, strNow, 2);
                oMlaRow.updatePdh(_db);
            }
        }

        //update all rows that are in new, !in OG...
        for (int i = 0; i < _vctMaintainers.size(); i++) {
            String strMaint = (String) _vctMaintainers.elementAt(i);
            boolean bFound = false;
            for (int j = 0; j < vctMaintainers_OG.size(); j++) {
                String strMaint_OG = (String) vctMaintainers_OG.elementAt(j);
                if (strMaint.equals(strMaint_OG)) {
                    bFound = true;
                    j = vctMaintainers_OG.size();
                }
            }
            if (!bFound) {
                //update strMaint
                MetaLinkAttrRow oMlaRow = new MetaLinkAttrRow(getProfile(), "Role/Maintainer", getRoleCode(), strMaint, "Capability", "L", strNow, strForever, strNow, strForever, 2);
                oMlaRow.updatePdh(_db);
            }
        }

        //update this so that we will see changes...
        strNow = _db.getDates().getNow();
        getProfile().setValOn(strNow);
        getProfile().setEffOn(strNow);
        return;
    }

    /**
     * set displayable
     *
     * @param _b 
     */
    protected void setDisplayable(boolean _b) {
        m_bDisplayable = _b;
        return;
    }

    /**
     * is this MetaRole displayable?
     *
     * @return boolean
     */
    public boolean isDisplayable() {
        return m_bDisplayable;
    }

    /**
     * get the RoleCode
     *
     * @return String
     */
    public String getRoleCode() {
        return getKey();
    }

    /**
     * dump contents
     *
     * @return String
     * @param _bBrief 
     */
    public String dump(boolean _bBrief) {
        return new String();
    }

    /**
    * Return the date/time this class was generated
    * @return the date/time this class was generated
    */
    public String getVersion() {
        return "$Id: MetaRole.java,v 1.32 2005/03/08 23:15:46 dave Exp $";
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////   ALL SORTS OF UPDATE METHODS /////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * update the description to pdh
     *
     * @return boolean
     * @param _db
     * @param _strShortDesc
     * @param _strLongDesc
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException 
     */
    public boolean updatePdhDescription(Database _db, String _strShortDesc, String _strLongDesc) throws SQLException, MiddlewareRequestException, MiddlewareException {
        boolean bUpdatePerformed = false;
        MetaDescriptionRow oMdRow = null;
        //only if changed..
        if (!_strShortDesc.equals(getShortDescription()) || !_strLongDesc.equals(getLongDescription())) {
            Profile prof = getProfile();
            String strNow = _db.getDates().getNow();
            String strForever = _db.getDates().getForever();
            putLongDescription(_strShortDesc);
            putShortDescription(_strLongDesc);
            
            oMdRow = new MetaDescriptionRow(prof, getRoleCode(), "Role", getShortDescription(), getLongDescription(), prof.getReadLanguage().getNLSID(), strNow, strForever, strNow, strForever, 2);
            
            oMdRow.updatePdh(_db);
            bUpdatePerformed = true;
        }
        return bUpdatePerformed;
    }

    /**
     * update a capability for an entity
     *
     * @return boolean
     * @param _db
     * @param _strEntityType
     * @param _strCap
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException 
     */
    public boolean updatePdhCapForEntity(Database _db, String _strEntityType, String _strCap) throws SQLException, MiddlewareRequestException, MiddlewareException {
        Vector v = new Vector(1);
        String[] pair = new String[] { _strEntityType, _strCap };
        v.addElement(pair);
        return updatePdhCapForEntities(_db, v);
    }

    /**
     * update a capability for a vector of EntType/cap pairs - only enforce changes
     *
     * @return boolean
     * @param _db
     * @param _vctPairs
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException 
     */
    public boolean updatePdhCapForEntities(Database _db, Vector _vctPairs) throws SQLException, MiddlewareRequestException, MiddlewareException {
        boolean bUpdatePerformed = false;

        MetaLinkAttrRow mlaRow = null;
        String strNow = _db.getDates().getNow();
        String strForever = _db.getDates().getForever();
        Vector vctPairs_db = getAllCapabilities(_db, "Entity");

        for (int i = 0; i < _vctPairs.size(); i++) {
            String[] pair = (String[]) _vctPairs.elementAt(i);
            String strEntityType = pair[0];
            String strCap = pair[1];
            String strEntityType_db = "";
            String strCap_db = "N";
            for (int j = 0; j < vctPairs_db.size(); j++) {
                String[] pair_db = (String[]) vctPairs_db.elementAt(j);
                if (pair_db[0].equals(strEntityType)) {
                    strEntityType_db = pair_db[0];
                    strCap_db = pair_db[1];
                    j = vctPairs_db.size(); //break
                    continue;
                }
            }
            //1) if cap not changed -> do nothing
            _db.debug(D.EBUG_DETAIL, strCap + "|" + strEntityType + " : " + strCap_db + "|" + strEntityType_db);
            if (strCap.equals(strCap_db)) {
                continue;
            }
            //2) if cap is 'N' -> expire the record in db
            if (strCap.equals("N")) {
                strForever = strNow;
            } //i.e. expire
            mlaRow = new MetaLinkAttrRow(getProfile(), "Role/Entity", getRoleCode(), strEntityType, "Capability", strCap, strNow, strForever, strNow, strForever, 2);
            mlaRow.updatePdh(_db);
            bUpdatePerformed = true;
            new MetaCacheManager(getProfile()).expireEGCacheAllRolesAllNls(_db, strEntityType);
        }
        //now update profile dates so we see changes...
        strNow = _db.getDates().getNow();
        getProfile().setValOn(strNow);
        getProfile().setEffOn(strNow);
        return bUpdatePerformed;
    }

    /**
     * update a capability for an entity
     *
     * @return boolean
     * @param _db
     * @param _oAtt
     * @param _strCap
     * @param _oMel
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException 
     */
    public boolean updatePdhCapForAttribute(Database _db, EANMetaAttribute _oAtt, String _strCap, MetaEntityList _oMel) throws SQLException, MiddlewareRequestException, MiddlewareException {

        MetaLinkAttrRow mlaRow = null;

        boolean bUpdatePerformed = false;
        String strNow = _db.getDates().getNow();
        String strForever = _db.getDates().getForever();
        String strCap_db = getCapability(_db, _oAtt.getAttributeCode(), getRoleCode(), "Attribute");
        boolean bRemoveAtt = false;
        //1) if cap not changed -> do nothing
        if (_strCap.equals(strCap_db)) {
            return false;
        }
        //2) if cap is 'N' -> expire the record in db
        if (_strCap.equals("N")) {
            strForever = strNow; //i.e. expire
            bRemoveAtt = true;
        }
        mlaRow = new MetaLinkAttrRow(getProfile(), "Role/Attribute", getRoleCode(), _oAtt.getAttributeCode(), "Capability", _strCap, strNow, strForever, strNow, strForever, 2);
        mlaRow.updatePdh(_db);
        bUpdatePerformed = true;
        //now update profile dates so we see changes...
        strNow = _db.getDates().getNow();
        getProfile().setValOn(strNow);
        getProfile().setEffOn(strNow);

        _oAtt.setCapability(_strCap);

        //////////////////
        /// This is key!!
        //////////////////
        //enforce capability change we have to try and do this to propogate changes...
        //note these will expire for ALL rolecodes-- this probably isnt necessarry, but we definitely need to hit all
        //caches for this entitytype (all purposes..). is this overkill?
        //if(_oMel != null) {
        //    if(bRemoveAtt) //i.e. this role can NO LONGER SEE THIS ATTRIBUTE
        //        _oMel.expireAllAttributeInstances(_db,_oAtt);
        //    else {
        //        _oAtt.setCapability(_strCap);
        //        _oMel.refreshAllAttributeInstances(_db,_oAtt);
        //    }
        //}
        if (_oMel != null) {
            Vector vctEntTypes = _oMel.getEntityTypesContainingAttribute(_db, _oAtt.getAttributeCode(), false);
            for (int i = 0; i < vctEntTypes.size(); i++) {
                String strEntType = (String) vctEntTypes.elementAt(i);
                new MetaCacheManager(getProfile()).expireEGCacheAllRolesAllNls(_db, strEntType);
            }
        }
        return bUpdatePerformed;
    }

    /**
     * update the pdh Meta for this NEW role - copy an existing Role
     *
     * @param _db
     * @param _oMrCopy
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException 
     */
    public void updatePdhMetaCopyRole(Database _db, MetaRole _oMrCopy) throws SQLException, MiddlewareRequestException, MiddlewareException {

        MetaDescriptionRow mdRow = null;
        MetaEntityRow meRow = null;
        EANList mlaRows = null;
            
        String strNow = _db.getDates().getNow();
        String strForever = _db.getDates().getForever();

        //we can use the isNewEntityType for RoleCode!!!!
        boolean bNewRoleCode = MetaEntityList.isNewEntityType(_db, getProfile(), getRoleCode());

        if (bNewRoleCode) {
            //1) MetaEntity table
            meRow = new MetaEntityRow(getProfile(), getRoleCode(), "Role", strNow, strForever, strNow, strForever, 2);
            meRow.updatePdh(_db);
            //2)MetaDescription table
            mdRow = new MetaDescriptionRow(getProfile(), getRoleCode(), "Role", getShortDescription(), getLongDescription(), getProfile().getReadLanguage().getNLSID(), strNow, strForever, strNow, strForever, 2);
            mdRow.updatePdh(_db);
            // 3) MLA table
            // get all linktypes == A) 'Role/Action/Entity/Group'
            //                      B) 'Role/Assoc'
            //                      C) 'Role/Attribute'
            //                      D) 'Role/Entity'
            //                      E) 'Role/Maintainer'
            //                      F) 'Role/Search'
            //we can reuse this for copy of Role/% records...
            mlaRows = getMLARows(_db, _oMrCopy.getRoleCode(), "copy");
            for (int i = 0; i < mlaRows.size(); i++) {
                MetaLinkAttrRow mlaRow = (MetaLinkAttrRow) mlaRows.getAt(i);
                mlaRow.updatePdh(_db);
            }
        } else {
            throw new MiddlewareException("MetaRole.UpdatePdhMeta(): " + getRoleCode() + " already exists!");
        }
        return;
    }

    /**
     * update the pdh Meta for this role
     *
     * @param _db
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException 
     */
    public void updatePdhMeta(Database _db) throws SQLException, MiddlewareRequestException, MiddlewareException {
        updatePdhMeta(_db, false);
        return;
    }

    /**
     * expire the pdh Meta for this role
     *
     * @param _db
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException 
     */
    public void expirePdhMeta(Database _db) throws SQLException, MiddlewareRequestException, MiddlewareException {
        updatePdhMeta(_db, true);
        return;
    }

    /**
     *
     */
    private void updatePdhMeta(Database _db, boolean bExpire) throws SQLException, MiddlewareRequestException, MiddlewareException {

        MetaEntityRow meRow = null;
        MetaDescriptionRow mdRow = null;
        EANList mlaRows = null;
        MetaLinkAttrRow mlaRow = null;

        String strNow = _db.getDates().getNow();
        String strForever = _db.getDates().getForever();

        //we can use the isNewEntityType for RoleCode!!!!
        boolean bNewRoleCode = MetaEntityList.isNewEntityType(_db, getProfile(), getRoleCode());

        //EXPIRE
        if (bExpire && !bNewRoleCode) {
            MetaRoleList parentList = (MetaRoleList) getParent();
            parentList.removeMetaRole(this);
            System.err.println("Expiring MetaRole:" + getRoleCode());
            //now go through and do all expires....
            //1) MetaEntity table
            meRow = new MetaEntityRow(getProfile(), getRoleCode(), "Role", strNow, strNow, strNow, strNow, 2);
            meRow.updatePdh(_db);
            //2)MetaDescription table
            mdRow = new MetaDescriptionRow(getProfile(), getRoleCode(), "Role", getShortDescription(), getLongDescription(), getProfile().getReadLanguage().getNLSID(), strNow, strNow, strNow, strNow, 2);
            mdRow.updatePdh(_db);
            // 3) MLA table
            // get all linktypes == A) 'Role/Action/Entity/Group'
            //                      B) 'Role/Assoc'
            //                      C) 'Role/Attribute'
            //                      D) 'Role/Entity'
            //                      E) 'Role/Maintainer'
            //                      F) 'Role/Search'
            //we can reuse this for copy of Role/% records...
            mlaRows = getMLARows(_db, getRoleCode(), "expire");
            for (int i = 0; i < mlaRows.size(); i++) {
                mlaRow = (MetaLinkAttrRow) mlaRows.getAt(i);
                mlaRow.updatePdh(_db);
            }

        }

        //UPDATE
        else {
            //if it is a NEW RoleCode...
            if (bNewRoleCode) {
                //1) MetaEntity table
                meRow = new MetaEntityRow(getProfile(), getRoleCode(), "Role", strNow, strForever, strNow, strForever, 2);
                meRow.updatePdh(_db);
                //2)MetaDescription table
                mdRow = new MetaDescriptionRow(getProfile(), getRoleCode(), "Role", getShortDescription(), getLongDescription(), getProfile().getReadLanguage().getNLSID(), strNow, strForever, strNow, strForever, 2);
                mdRow.updatePdh(_db);
                //now Role/Maintainer for UPDATEAL
                mlaRow = new MetaLinkAttrRow(getProfile(), "Role/Maintainer", getRoleCode(), "UPDATEAL", "Capability", "L", strNow, strForever, strNow, strForever, 2);
                mlaRow.updatePdh(_db);
            } else {
                throw new MiddlewareException("MetaRole.UpdatePdhMeta(): " + getRoleCode() + " already exists!");
            }
        }
        return;
    }

    /**
     * Get MetaLinkAttr Rows for expire OR update w/ copy
     */
    private EANList getMLARows(Database _db, String _strRoleCode, String _strPurpose) throws SQLException, MiddlewareRequestException, MiddlewareException {

        EANList eList = new EANList();
        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;
        String strValEffTo = null;

        String[] strArrayLinkTypes = new String[6];

        String strEnterprise = getProfile().getEnterprise();
        String strLinkType1 = getRoleCode();

        String strValEffFrom = _db.getDates().getNow();
        //for expire, set effTo, valTo to now
        if (_strPurpose.equals("expire")) {
            strValEffTo = strValEffFrom;
        
        } else if (_strPurpose.equals("copy")) {
            strValEffTo = _db.getDates().getForever();
        }

        strArrayLinkTypes[0] = "Role/Action/Entity/Group";
        strArrayLinkTypes[1] = "Role/Assoc";
        strArrayLinkTypes[2] = "Role/Attribute";
        strArrayLinkTypes[3] = "Role/Entity";
        strArrayLinkTypes[4] = "Role/Maintainer";
        strArrayLinkTypes[5] = "Role/Search";

        for (int i = 0; i < strArrayLinkTypes.length; i++) {
            String strLinkType = strArrayLinkTypes[i];
            try {
                rs = _db.callGBL7509(new ReturnStatus(-1), strEnterprise, strLinkType, _strRoleCode, getProfile().getValOn(), getProfile().getEffOn());
                rdrs = new ReturnDataResultSet(rs);
            } finally {
                rs.close();
                rs = null;
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }
            for (int row = 0; row < rdrs.getRowCount(); row++) {
                String strLinkType2 = rdrs.getColumn(row, 0);
                String strLinkCode = rdrs.getColumn(row, 1);
                String strLinkValue = rdrs.getColumn(row, 2);
                //now build our MLA row
                MetaLinkAttrRow mlaRow = new MetaLinkAttrRow(getProfile(), strLinkType, strLinkType1, strLinkType2, strLinkCode, strLinkValue, strValEffFrom, strValEffTo, strValEffFrom, strValEffTo, 2);
                eList.put(mlaRow);
            }
        }

        return eList;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Execute GBL7503
     * used for any entityClass + Attributes
     */
    private String getCapability(Database _db, String _strEntType, String _strRoleCode, String _strEntClass) throws SQLException, MiddlewareException, MiddlewareRequestException {

        String strCapability = "N";
        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;
        try {
            rs = _db.callGBL7503(new ReturnStatus(-1), getProfile().getEnterprise(), "Role/" + _strEntClass, _strRoleCode, _strEntType, "Capability", getProfile().getValOn(), getProfile().getEffOn());
            rdrs = new ReturnDataResultSet(rs);
        } finally {
            rs.close();
            rs = null;
            _db.commit();
            _db.freeStatement();
            _db.isPending();
        }

        if (rdrs.getRowCount() > 0) {
            strCapability = rdrs.getColumn(0, 0);
        }
        _db.debug(D.EBUG_SPEW, "gbl7503 answer is:" + strCapability);

        return strCapability;
    }

    /**
     * Retreives a list of all Capability for this Role
     *
     * @return vector of String[] pairs  containing enttype + capability
     * @param _db
     * @param _strEntClass
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public Vector getAllCapabilities(Database _db, String _strEntClass) throws SQLException, MiddlewareException, MiddlewareRequestException {
        Vector vctPairs = new Vector();
        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;
        try {
            rs = _db.callGBL7508(new ReturnStatus(-1), getProfile().getEnterprise(), "Role/" + _strEntClass, getRoleCode(), "Capability", getProfile().getValOn(), getProfile().getEffOn());
            rdrs = new ReturnDataResultSet(rs);
        } finally {
            rs.close();
            rs = null;
            _db.commit();
            _db.freeStatement();
            _db.isPending();
        }
        for (int row = 0; row < rdrs.getRowCount(); row++) {
            String[] pair = new String[2];
            pair[0] = rdrs.getColumn(row, 0);
            pair[1] = rdrs.getColumn(row, 1);
            vctPairs.addElement(pair);
        }
        return vctPairs;
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

    /**
     * get the sort key
     *
     * @return String
     */
    public String toCompareString() {
        if (getSortType().equals(SORT_BY_ROLEDESC)) {
            return getLongDescription();
        }
        if (getSortType().equals(SORT_BY_ROLECODE)) {
            return getRoleCode();
        }
        //this shouldn't occur
        return toString();
    }

    /**
     * setSortType
     *
     * @param _s
     *  @author David Bigelow
     */
    public void setSortType(String _s) {
        m_strSortType = _s;
        return;
    }

    /**
     * getSortType
     *
     * @return
     *  @author David Bigelow
     */
    public String getSortType() {
        return m_strSortType;
    }

    //{{{ setCompareField(String) method
    //set the String representing the field to compare by
    /**
     * (non-Javadoc)
     * setCompareField
     *
     * @see COM.ibm.eannounce.objects.EANComparable#setCompareField(java.lang.String)
     */
    public void setCompareField(String _s) {
        m_strSortType = _s;
    }
    //}}}

    //{{{ getCompareFiled() method
    //get the String representing the field to compare by
    /**
     * (non-Javadoc)
     * getCompareField
     *
     * @see COM.ibm.eannounce.objects.EANComparable#getCompareField()
     */
    public String getCompareField() {
        return m_strSortType;
    }
    //}}}

}
