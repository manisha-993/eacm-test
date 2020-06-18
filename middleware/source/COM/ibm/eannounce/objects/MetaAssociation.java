//
// Copyright (c) 2002, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: MetaAssociation.java,v $
// Revision 1.21  2005/03/04 22:04:04  dave
// Jtest
//
// Revision 1.20  2005/03/04 21:53:09  dave
// Jtest
//
// Revision 1.19  2002/12/17 21:59:39  gregg
// getAttributeCode1, getAttributeCode2 methods
//
// Revision 1.18  2002/12/16 19:39:43  gregg
// more bChanged logic in updatePdhMeta method
//
// Revision 1.17  2002/12/16 18:36:52  gregg
// return a boolean in updatePdhMeta method indicating whether any database updates were performed.
//
// Revision 1.16  2002/12/09 18:33:58  gregg
// more updatePdhMEta
//
// Revision 1.15  2002/12/09 18:12:13  gregg
// in updatePdhMeta method: close out old records after att1 || att2 has changed
//
// Revision 1.14  2002/12/07 00:03:20  gregg
// s'more de-buggin'
//
// Revision 1.13  2002/12/06 22:10:33  gregg
// fix in getMetaFlagAttribute2 method
//
// Revision 1.12  2002/12/06 21:50:20  gregg
// compile fix
//
// Revision 1.11  2002/12/06 21:38:25  gregg
// punting exception in updatePdhMeta method
//
// Revision 1.10  2002/12/06 00:54:38  gregg
// refresh cache in updatePdhMeta
//
// Revision 1.9  2002/12/06 00:33:18  gregg
// updatePdhMeta Role Capability stuff....
//
// Revision 1.8  2002/12/05 23:27:19  gregg
// fix in updatePdhMeta
//
// Revision 1.7  2002/12/05 22:45:54  gregg
// some debugging + error checking
//
// Revision 1.6  2002/12/05 22:09:23  gregg
// updatePdhMeta methods
//
// Revision 1.5  2002/11/23 01:21:33  gregg
// some fundamental changes to the object + validate method
//
// Revision 1.4  2002/11/22 21:59:08  gregg
// initially build EG1, EG2 w/out database
//
// Revision 1.3  2002/11/22 21:57:46  gregg
// getEntityGroup1Edit, getEntityGroup2Edit methods.
// also initial build EG1, EG2 w/ NoAtts
//
// Revision 1.2  2002/11/07 23:00:44  gregg
// serialVersionUID
//
// Revision 1.1  2002/11/07 22:09:32  gregg
// initial load
//
//
//

package COM.ibm.eannounce.objects;

import java.util.Vector;
import java.sql.ResultSet;
import java.sql.SQLException;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.ReturnStatus;

/**
 *  This is basically an EntityGroup with a bit of extra functionality specific to Associations.
 *  To facilitate managing Association-related MetaData.
 */
public class MetaAssociation extends EntityGroup {

    static final long serialVersionUID = 1L;

    private EntityGroup m_eg1 = null;
    private EntityGroup m_eg2 = null;
    private EANMetaFlagAttribute m_mfAtt1 = null;
    private EANMetaFlagAttribute m_mfAtt2 = null;
    //these must be sync'd w/ above atts
    private String m_strAtt1 = null;
    private String m_strAtt2 = null;

    /**
     * MetaAssociation
     *
     * @param _emf
     * @param _db
     * @param _prof
     * @param _strEntityType
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public MetaAssociation(EANMetaFoundation _emf, Database _db, Profile _prof, String _strEntityType) throws SQLException, MiddlewareException, MiddlewareRequestException {
        super(_emf, _db, _prof, _strEntityType, "Navigate");
        
        if (!isAssoc()) {
            throw new MiddlewareRequestException(_strEntityType + " : This EntityGroup is not an Association.");
        }
        //get Entity1, Entity2
        setEntityGroup1(new EntityGroup(getProfile(), getEntity1Type(), "NoAtts"));
        setEntityGroup2(new EntityGroup(getProfile(), getEntity2Type(), "NoAtts"));
        try {
        
            String strNow = _db.getDates().getNow();
            
            ResultSet rs = null;
            ReturnDataResultSet rdrs = null;
        
            try {
                rs = _db.callGBL7508(new ReturnStatus(-1), getProfile().getEnterprise(), "Assoc/Attribute", getEntityType(), "Transform", strNow, strNow);
                rdrs = new ReturnDataResultSet(rs);
            } finally {
                rs.close();
                rs = null;
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }
            
            if (rdrs.getRowCount() > 0) {
                String strAttCode1 = rdrs.getColumn(0, 0);
                String strAttCode2 = rdrs.getColumn(0, 1);
                m_strAtt1 = strAttCode1;
                m_strAtt2 = strAttCode2;
            } else {
                _db.debug(D.EBUG_WARN, "MetaAssociation: in constructor: No rows found for Assoc/Attribute (" + getEntityType() + ")!");
            }
        } catch (Exception exc) {
            _db.debug(D.EBUG_ERR, "MetaAssociation: exception in constructor:" + exc.toString());
        } finally {
            _db.freeStatement();
            _db.isPending();
        }
    }

    /**
     * MetaAssociation
     *
     * @param _prof
     * @param _strEntityType
     * @param _eg1
     * @param _eg2
     * @param _mfAtt1
     * @param _mfAtt2
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public MetaAssociation(Profile _prof, String _strEntityType, EntityGroup _eg1, EntityGroup _eg2, EANMetaFlagAttribute _mfAtt1, EANMetaFlagAttribute _mfAtt2) throws MiddlewareRequestException {
        super(_prof, _strEntityType, "Navigate");
        setAssoc(true, _eg1.getEntityType(), _eg2.getEntityType());
        setEntityGroup1(_eg1);
        setEntityGroup2(_eg2);
        setMetaFlagAttribute1(_mfAtt1);
        setMetaFlagAttribute2(_mfAtt2);
    }

    /**
     * setEntityGroup1
     *
     * @param _eg
     *  @author David Bigelow
     */
    public void setEntityGroup1(EntityGroup _eg) {
        m_eg1 = _eg;
        //keep m_strEntity1Type in sync
        if (_eg != null) {
            setAssoc(true, _eg.getEntityType(), getEntity2Type());
        }
    }

    /**
     * setEntityGroup2
     *
     * @param _eg
     *  @author David Bigelow
     */
    public void setEntityGroup2(EntityGroup _eg) {
        m_eg2 = _eg;
        //keep m_strEntity2Type in sync
        if (_eg != null) {
            setAssoc(true, getEntity1Type(), _eg.getEntityType());
        }
    }

    /**
     * pulled this out of constructor
     */
    private void setupMetaFlagAttributes(Database _db, String _strAttCode1, String _strAttCode2) throws SQLException, MiddlewareException {
        //Set up Att1 w/ some nice error checking
        //reasons this could fail: cant find att in eg1, not a flag att, no eg1 built
        if (getEntityGroup1Edit(_db) != null) {
            EANMetaFlagAttribute mfAtt1 = null;
            try {
                mfAtt1 = (EANMetaFlagAttribute) getEntityGroup1().getMetaAttribute(_strAttCode1);
            } catch (ClassCastException cce) {
                _db.debug(D.EBUG_WARN, "MetaAssociation: in constructor: att1 (" + _strAttCode1 + ") is not a flag attribute!" + cce.getMessage());
            }
            if (mfAtt1 != null) {
                setMetaFlagAttribute1(mfAtt1);
            
            } else {
                _db.debug(D.EBUG_WARN, "MetaAssociation: in constructor: could not find att1 (" + _strAttCode1 + ")!");
            }
        } else {
            _db.debug(D.EBUG_WARN, "MetaAssociation: in constructor: cannot retreive MetaAttribute1 b/c EntiyGroup1 is null!");
        }
        //Now set up Att2 the same way
        if (getEntityGroup2Edit(_db) != null) {
            EANMetaFlagAttribute mfAtt2 = null;
            try {
                mfAtt2 = (EANMetaFlagAttribute) getEntityGroup2().getMetaAttribute(_strAttCode2);
            } catch (ClassCastException cce) {
                _db.debug(D.EBUG_WARN, "MetaAssociation: in constructor: att2 (" + _strAttCode2 + ") is not a flag attribute!" + cce.getMessage());
            }
            if (mfAtt2 != null) {
                setMetaFlagAttribute2(mfAtt2);
            
            } else {
                _db.debug(D.EBUG_WARN, "MetaAssociation: in constructor: could not find att2 (" + _strAttCode2 + ")!");
                _db.debug(D.EBUG_WARN, "    MetaAssociation: eg2 att count:" + getEntityGroup2().getMetaAttributeCount());
                for (int i = 0; i < getEntityGroup2().getMetaAttributeCount(); i++) {
                    _db.debug(D.EBUG_WARN, "   --- MetaAssociation:eg2.getMEtaAttribute(" + i + ") = " + getEntityGroup2().getMetaAttribute(i));
                }
            }
        } else {
            _db.debug(D.EBUG_WARN, "MetaAssociation: in constructor: cannot retreive MetaAttribute2 b/c EntiyGroup2 is null!");
        }
    }

    /**
     * This EntityGroup may or may not have attributes
     * - use getEntityGroup1Edit to ensure "Edit" version of the EntityGroup
     *
     * @return EntityGroup
     */
    public EntityGroup getEntityGroup1() {
        return m_eg1;
    }

    /**
     * This EntityGroup may or may not have attributes
     * - use getEntityGroup2Edit to ensure "Edit" version of the EntityGroup
     *
     * @return EntityGroup
     */
    public EntityGroup getEntityGroup2() {
        return m_eg2;
    }

    /**
     * Get the EntityGroup1 containing all attributes, etc (i.e. this is the "Edit" purpose)
     *
     * @param _db
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @return EntityGroup
     */
    public EntityGroup getEntityGroup1Edit(Database _db) throws SQLException, MiddlewareException {
        if (getEntityGroup1().isEditLike()) {
            return getEntityGroup1();
        }
        setEntityGroup1(new EntityGroup(this, _db, getProfile(), getEntity1Type(), "Edit"));
        return getEntityGroup1();
    }

    /**
     * Get the EntityGroup2 containing all attributes, etc (i.e. this is the "Edit" purpose)
     *
     * @param _db
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @return EntityGroup
     */
    public EntityGroup getEntityGroup2Edit(Database _db) throws SQLException, MiddlewareException {
        if (getEntityGroup2().isEditLike()) {
            return getEntityGroup2();
        }
        setEntityGroup2(new EntityGroup(this, _db, getProfile(), getEntity2Type(), "Edit"));
        return getEntityGroup2();
    }

    /**
     * setMetaFlagAttribute1
     *
     * @param _mfAtt
     *  @author David Bigelow
     */
    public void setMetaFlagAttribute1(EANMetaFlagAttribute _mfAtt) {
        m_mfAtt1 = _mfAtt;
        m_strAtt1 = _mfAtt.getAttributeCode();
    }

    /**
     * setMetaFlagAttribute2
     *
     * @param _mfAtt
     *  @author David Bigelow
     */
    public void setMetaFlagAttribute2(EANMetaFlagAttribute _mfAtt) {
        m_mfAtt2 = _mfAtt;
        m_strAtt2 = _mfAtt.getAttributeCode();
    }

    /**
     * getMetaFlagAttribute1
     *
     * @param _db
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @return
     *  @author David Bigelow
     */
    public EANMetaFlagAttribute getMetaFlagAttribute1(Database _db) throws SQLException, MiddlewareException {
        if (m_mfAtt1 == null) {
            setupMetaFlagAttributes(_db, m_strAtt1, m_strAtt2);
        }
        return m_mfAtt1;
    }

    /**
     * getMetaFlagAttribute2
     *
     * @param _db
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @return
     *  @author David Bigelow
     */
    public EANMetaFlagAttribute getMetaFlagAttribute2(Database _db) throws SQLException, MiddlewareException {
        if (m_mfAtt2 == null) {
            setupMetaFlagAttributes(_db, m_strAtt1, m_strAtt2);
        }
        return m_mfAtt2;
    }

    /**
     * getAttributeCode1
     *
     * @return
     *  @author David Bigelow
     */
    public String getAttributeCode1() {
        return m_strAtt1;
    }

    /**
     * getAttributeCode2
     *
     * @return
     *  @author David Bigelow
     */
    public String getAttributeCode2() {
        return m_strAtt2;
    }

    /**
     * MetaAttributeCount should be zero for Assoc's
     *
     * @return int
     */
    public int getMetaAttributeCount() {
        //this will absolutely prevent updatePdhMeta defined in EntityGroup class from doing anything bad
        //even in the case that attributes are somehow added
        return 0;
    }

    /**
     * (non-Javadoc)
     * putMetaAttribute
     *
     * @see COM.ibm.eannounce.objects.EntityGroup#putMetaAttribute(COM.ibm.eannounce.objects.EANMetaAttribute)
     */
    public void putMetaAttribute(EANMetaAttribute _att) {
        //we don't want to add attributes to associations
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {
        StringBuffer sb = new StringBuffer("MetaAssociation" + NEW_LINE + "======================================================");
        sb.append(super.dump(_bBrief));
        sb.append(NEW_LINE + "EntityGroup1:" + (getEntityGroup1() != null ? getEntityGroup1().getEntityType() : "EntityGroup1 is null!!"));
        sb.append(NEW_LINE + "EntityGroup1:" + (getEntityGroup2() != null ? getEntityGroup2().getEntityType() : "EntityGroup2 is null!!"));
        sb.append(NEW_LINE + "MetaFlagAttribute1:" + (m_mfAtt1 != null ? m_mfAtt1.getAttributeCode() : "MetaFlagAttribute1 is null!!"));
        sb.append(NEW_LINE + "MetaFlagAttribute2:" + (m_mfAtt2 != null ? m_mfAtt2.getAttributeCode() : "MetaFlagAttribute2 is null!!"));
        return sb.toString();
    }

    /**
     * Return the date/time this class was generated
     * @return the date/time this class was generated
     */
    public String getVersion() {
        return "$Id: MetaAssociation.java,v 1.21 2005/03/04 22:04:04 dave Exp $";
    }

    /**
     * validate
     * 
     * @return a String Array with flagCodes that are contained in both MetaFlagAttributes.
     * if att1==att2, this will simply return the list of all flagcodes in the att
     * @param _db
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException 
     */
    public String[] validate(Database _db) throws SQLException, MiddlewareException {

        Vector v = new Vector();
        String[] sa = null;

        EANMetaFlagAttribute att1 = getMetaFlagAttribute1(_db);
        EANMetaFlagAttribute att2 = getMetaFlagAttribute2(_db);
        for (int i1 = 0; i1 < att1.getMetaFlagCount(); i1++) {
            String strFlagCode1 = att1.getMetaFlag(i1).getFlagCode();
            for (int i2 = 0; i2 < att2.getMetaFlagCount(); i2++) {
                String strFlagCode2 = att2.getMetaFlag(i2).getFlagCode();
                if (strFlagCode1.equals(strFlagCode2)) {
                    v.addElement(strFlagCode1);
                }
            }
        }
        sa = new String[v.size()];
        for (int i = 0; i < v.size(); i++) {
            sa[i] = (String) v.elementAt(i);
        }
        return sa;
    }

    ////////////
    // UPDATE PDH META METHODS
    ////////////

    /**
     * (non-Javadoc)
     * expirePdhMeta
     *
     * @see COM.ibm.eannounce.objects.EntityGroup#expirePdhMeta(COM.ibm.opicmpdh.middleware.Database)
     */
    public boolean expirePdhMeta(Database _db) throws SQLException, MiddlewareException {
        return updatePdhMeta(_db, true, true);
    }

    /**
     * (non-Javadoc)
     * updatePdhMeta
     *
     * @see COM.ibm.eannounce.objects.EntityGroup#updatePdhMeta(COM.ibm.opicmpdh.middleware.Database)
     */
    public boolean updatePdhMeta(Database _db) throws SQLException, MiddlewareException {
        return updatePdhMeta(_db, false, true);
    }

    /**
     * Expire 2 records in MLA table: 
     *    1) lt == entitytype
     *    2) lt == 'Assoc/Attribute', linkcode == 'Transform'
     */
    private boolean updatePdhMeta(Database _db, boolean _bExpire, boolean _bDeepUpdate) throws SQLException, MiddlewareException {
        boolean bChanged = false;
        // _bDeepUpdate param is irrelevant
        try {
 
            boolean bNewAssoc = MetaEntityList.isNewEntityType(_db, getProfile(), getEntityType());
            MetaAssociation ma_db = null;
            String strNow = _db.getDates().getNow();
            String strForever = _db.getDates().getForever();
            Vector vctRoles = getVctRoles(_db);
 
            if (!bNewAssoc) {
                ma_db = new MetaAssociation((EANMetaFoundation) getParent(), _db, getProfile(), getEntityType());
            }
            //EXPIRE
            if (_bExpire && !bNewAssoc) {
                //1) metaentity
                new MetaEntityRow(getProfile(), getEntityType(), "Assoc", strNow, strNow, strNow, strNow, 2).updatePdh(_db);
                //OK, no more checks needed in this block! 
                bChanged = true;
                //
                //2) metadescription
                ma_db.expirePdhMetaDescriptions(_db, getEntityType(), "Assoc");
                //3) metalinkattr
                //   a) lt = entitytype
                new MetaLinkAttrRow(getProfile(), getEntityType(), ma_db.getEntityGroup1().getEntityType(), ma_db.getEntityGroup2().getEntityType(), "Assoc", ma_db.getMetaFlagAttribute1(_db).getAttributeCode(), strNow, strNow, strNow, strNow, 2).updatePdh(_db);
                //    b) lt = 'Assoc/Attribute', lc = 'Transform'
                new MetaLinkAttrRow(getProfile(), "Assoc/Attribute", getEntityType(), ma_db.getMetaFlagAttribute1(_db).getAttributeCode(), "Transform", ma_db.getMetaFlagAttribute2(_db).getAttributeCode(), strNow, strNow, strNow, strNow, 2).updatePdh(_db);
                //now expire Roles
                for (int i = 0; i < vctRoles.size(); i++) {
                    String[] sa = (String[]) vctRoles.elementAt(i);
                    String strRole = sa[0];
                    String strCap = sa[1];
                    new MetaLinkAttrRow(getProfile(), "Role/Assoc", strRole, getEntityType(), "Capability", strCap, strNow, strNow, strNow, strNow, 2).updatePdh(_db);
                }
            } else if (!_bExpire) {

                //UPDATE

                // 1) metaentity
                if (bNewAssoc) {
                    new MetaEntityRow(getProfile(), getEntityType(), "Assoc", strNow, strForever, strNow, strForever, 2).updatePdh(_db);
                    bChanged = true;
                }
                // 2) metadescription
                bChanged = (updatePdhMetaDescriptions(_db, getEntityType(), "Assoc") ? true : bChanged);
                // 3) metalinkattribute
                //   a) lt = entitytype
                if (bNewAssoc
                    || !ma_db.getMetaFlagAttribute1(_db).getAttributeCode().equals(this.getMetaFlagAttribute1(_db).getAttributeCode())
                    || !ma_db.getEntityGroup1().getEntityType().equals(this.getEntityGroup1().getEntityType())
                    || !ma_db.getEntityGroup2().getEntityType().equals(this.getEntityGroup2().getEntityType())) {
                    new MetaLinkAttrRow(getProfile(), getEntityType(), this.getEntityGroup1().getEntityType(), this.getEntityGroup2().getEntityType(), "Assoc", this.getMetaFlagAttribute1(_db).getAttributeCode(), strNow, strForever, strNow, strForever, 2).updatePdh(_db);
                    bChanged = true;
                }
                //   b) lt = "Assoc/Attribute", lc = "Tranform"
                if (bNewAssoc || !ma_db.getMetaFlagAttribute1(_db).getAttributeCode().equals(this.getMetaFlagAttribute1(_db).getAttributeCode()) || !ma_db.getMetaFlagAttribute2(_db).getAttributeCode().equals(this.getMetaFlagAttribute2(_db).getAttributeCode())) {
                    new MetaLinkAttrRow(getProfile(), "Assoc/Attribute", getEntityType(), this.getMetaFlagAttribute1(_db).getAttributeCode(), "Transform", this.getMetaFlagAttribute2(_db).getAttributeCode(), strNow, strForever, strNow, strForever, 2).updatePdh(_db);
                    bChanged = true;
                    //only close out old record here if att1 has CHANGED. 
                    //this is because att2 is stored in linkvalue
                    if (!bNewAssoc && !ma_db.getMetaFlagAttribute1(_db).getAttributeCode().equals(this.getMetaFlagAttribute1(_db).getAttributeCode())) {
                        new MetaLinkAttrRow(getProfile(), "Assoc/Attribute", getEntityType(), ma_db.getMetaFlagAttribute1(_db).getAttributeCode(), "Transform", ma_db.getMetaFlagAttribute2(_db).getAttributeCode(), strNow, strNow, strNow, strNow, 2).updatePdh(_db);
                        bChanged = true;
                    }
                }
                // c) ROLES!! -- add current and UPDATEAL
                if (bNewAssoc) {
                    new MetaLinkAttrRow(getProfile(), "Role/Assoc", "UPDATEAL", getEntityType(), "Capability", "C", strNow, strForever, strNow, strForever, 2).updatePdh(_db);
                    new MetaLinkAttrRow(getProfile(), "Role/Assoc", getProfile().getRoleCode(), getEntityType(), "Capability", "C", strNow, strForever, strNow, strForever, 2).updatePdh(_db);
                    bChanged = true;
                } else { // check if cap has changed for CURRENT ROLE ONLY!!
                    for (int i = 0; i < vctRoles.size(); i++) {
                        String[] sa = (String[]) vctRoles.elementAt(i);
                        String strRole = sa[0];
                        String strCap = sa[1];
                        if (strRole.equals(getProfile().getRoleCode())) {
                            //only if cap has changed && capability is not null or blank or none or anything
                            // ... reasoning is why would you want to manage an Assoc for which you have no capability?
                            if (getCapability() != null && !getCapability().equals("") && !getCapability().equalsIgnoreCase("N")) {
                                if (!strCap.equals(getCapability())) {
                                    new MetaLinkAttrRow(getProfile(), "Role/Assoc", getProfile().getRoleCode(), getEntityType(), "Capability", getCapability(), strNow, strForever, strNow, strForever, 2).updatePdh(_db);
                                    bChanged = true;
                                }
                            }
                        }
                    }
                }
            }
            //CACHE
            //now update the cache!
            new MetaCacheManager(getProfile()).expireEGCacheAllRolesAllNls(_db, this.getEntityType());
            if (!_bExpire) {
                putCache(_db);
            }
            //} catch(Exception exc) {
            //    _db.debug(D.EBUG_ERR, "in MetaAssociation.updatePdhMeta - (UPDATE) - " + getEntityType() + ":" + exc.toString());		
        } finally {
            _db.freeStatement();
            _db.isPending();
        }
        return bChanged;
    }

    private Vector getVctRoles(Database _db) throws MiddlewareException, SQLException {
        Vector v = new Vector();
        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;
        
        try {
            String strNow = _db.getDates().getNow();
            try {
                rs = _db.callGBL7507(new ReturnStatus(-1), getProfile().getEnterprise(), "Role/Assoc", getEntityType(), "Capability", strNow, strNow);
                rdrs = new ReturnDataResultSet(rs);
            } finally {
                rs.close();
                rs = null;
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }
            for (int row = 0; row < rdrs.getRowCount(); row++) {
                String[] sa = new String[] { rdrs.getColumn(row, 0), rdrs.getColumn(row, 1)};
                v.addElement(sa);
            }
        } finally {
            _db.freeStatement();
            _db.isPending();
        }
        return v;
    }

}
