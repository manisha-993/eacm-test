//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: CGActionItem.java,v $
// Revision 1.15  2011/01/12 15:05:24  wendy
// action is always single input
//
// Revision 1.14  2009/05/11 15:50:35  wendy
// Support dereference for memory release
//
// Revision 1.13  2006/02/20 21:39:45  joan
// clean up System.out.println
//
// Revision 1.12  2005/08/10 16:14:23  tony
// improved catalog viewer functionality.
//
// Revision 1.11  2005/08/03 17:09:43  tony
// added datasource logic for catalog mod
//
// Revision 1.10  2005/02/15 21:41:29  joan
// work on CR5246
//
// Revision 1.9  2005/02/15 00:23:12  dave
// more CR work CR1124045246
//
// Revision 1.8  2005/02/15 00:02:20  dave
// CR1124045246 - making a place for any
// clear Change Group function to be triggered
//
// Revision 1.7  2005/02/10 01:22:25  dave
// JTest fixes
//
// Revision 1.6  2005/01/18 21:33:09  dave
// removing the debug parms from code (sp internalized them)
//
// Revision 1.5  2004/08/03 16:42:49  joan
// adjust
//
// Revision 1.4  2004/08/02 21:45:10  joan
// fix bug
//
// Revision 1.3  2004/08/02 20:09:50  joan
// add purpose
//
// Revision 1.2  2004/08/02 19:59:26  joan
// adjust method
//
// Revision 1.1  2004/08/02 19:34:43  joan
// add CGActionItem
//

package COM.ibm.eannounce.objects;

import java.sql.ResultSet;
import java.sql.SQLException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;

// Temp made this non abstract because we need to compile in general before we whip up
// how actions are suppose to differentiate
/**
 *  Description of the Class
 *
 * @author     davidbig
 * @created    April 11, 2003
 */
public class CGActionItem extends EANActionItem {

    final static long serialVersionUID = 20011106L;

    /**
     * FIELD
     */
    public static final String CHANGE_GROUP_ENTITY_TYPE = "CHANGEGROUP";

    /**
     * FIELD
     */
    private String m_strEntityType = null;
    private String m_strPurpose = "CG";
    protected void dereference(){
    	super.dereference();
    	m_strEntityType = null;
    	m_strPurpose = null;
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
     *  Gets the version attribute of the CGActionItem object
     *
     * @return    The version value
     */
    public String getVersion() {
        return "$Id: CGActionItem.java,v 1.15 2011/01/12 15:05:24 wendy Exp $";
    }

    /*
     *  Instantiate a new ActionItem based upon a dereferenced version of the Existing One
     */
    /**
     *Constructor for the CGActionItem object
     *
     * @param  _ai                             Description of the Parameter
     * @exception  MiddlewareRequestException  Description of the Exception
     */
    public CGActionItem(CGActionItem _ai) throws MiddlewareRequestException {
        super(_ai);
        m_strEntityType = _ai.m_strEntityType;
    }

    /**
     *Constructor for the CGActionItem object
     *
     * @param  _mf                             Description of the Parameter
     * @param  _ai                             Description of the Parameter
     * @exception  MiddlewareRequestException  Description of the Exception
     */
    public CGActionItem(EANMetaFoundation _mf, CGActionItem _ai) throws MiddlewareRequestException {
        super(_mf, _ai);
        m_strEntityType = _ai.m_strEntityType;
    }

    /**
     * This represents a Search Action Item.  It can only be constructed via a database connection, a Profile, and an Action Item Key*
     *
     * @param  _emf                            Description of the Parameter
     * @param  _db                             Description of the Parameter
     * @param  _prof                           Description of the Parameter
     * @param  _strActionItemKey               Description of the Parameter
     * @exception  SQLException                Description of the Exception
     * @exception  MiddlewareException         Description of the Exception
     * @exception  MiddlewareRequestException  Description of the Exception
     */
    public CGActionItem(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {
        this(_emf, _db, _prof, _strActionItemKey, true);
    }
    /**
     * This represents a Search Action Item.  It can only be constructed via a database connection, a Profile, and an Action Item Key*
     *
     * @param  _emf                            Description of the Parameter
     * @param  _db                             Description of the Parameter
     * @param  _prof                           Description of the Parameter
     * @param  _strActionItemKey               Description of the Parameter
     * @exception  SQLException                Description of the Exception
     * @exception  MiddlewareException         Description of the Exception
     * @exception  MiddlewareRequestException  Description of the Exception
     * @param _bUI
     */
    public CGActionItem(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey, boolean _bUI) throws SQLException, MiddlewareException, MiddlewareRequestException {

        super(_emf, _db, _prof, _strActionItemKey);

        try {
            Profile prof = getProfile();
            ReturnStatus returnStatus = new ReturnStatus(-1);
            ResultSet rs = null;
            ReturnDataResultSet rdrs;

            try {
                rs = _db.callGBL7030(returnStatus, prof.getEnterprise(), _strActionItemKey, prof.getValOn(), prof.getEffOn());
                rdrs = new ReturnDataResultSet(rs);
            } finally {
            	if(rs!=null){
            		rs.close();
            		rs = null;
            	}
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }

            // Set the class and description...
            for (int ii = 0; ii < rdrs.size(); ii++) {
                String strType = rdrs.getColumn(ii, 0);
                String strCode = rdrs.getColumn(ii, 1);
                String strValue = rdrs.getColumn(ii, 2);
                _db.debug(D.EBUG_SPEW, "gbl7030 answer is:" + strType + ":" + strCode + ":" + strValue + ":");
                if (strType.equals("TYPE") && strCode.equals("EntityType")) {
                    setEntityType(strValue);
				} else if (strType.equals("TYPE") && strCode.equals("DataSource")) {	//catalog enhancement
					setDataSource(strValue);											//catalog enhancement
				} else if (strType.equals("TYPE") && strCode.equals("DataSourceParms")) {
					setAdditionalParms(strValue);
                } else {
                    _db.debug(D.EBUG_ERR, "*** ACTION ITEM ATTRIBUTE *** No home for this Action/Attribute" + strType + ":" + strCode + ":" + strValue);
                }
            }
        } finally {
            _db.freeStatement();
            _db.isPending();
        }
    }

    /**
     * isSingleInput
     * this is always single input
     * @return
     */
    public boolean isSingleInput() {
        return true;
    }
    /**
     *  Description of the Method
     *
     * @param  _bBrief  Description of the Parameter
     * @return          Description of the Return Value
     */
    public String dump(boolean _bBrief) {
        StringBuffer strbResult = new StringBuffer();
        strbResult.append("CGActionItem:" + super.dump(_bBrief));
        return strbResult.toString();
    }

    /**
    *  Gets the purpose attribute of the CGActionItem object
    *
    * @return    The purpose value
    */
    public String getPurpose() {
        return m_strPurpose;
    }

    /**
     * setEntityType
     *
     * @param _str
     *  @author David Bigelow
     */
    protected void setEntityType(String _str) {
        m_strEntityType = _str;
    }

    /**
     * getEntityType
     *
     * @return
     *  @author David Bigelow
     */
    public String getEntityType() {
        return m_strEntityType;
    }

    /**
     * setChangeGroup
     *
     * @param _ei
     * @param _aProf
     * @param _activeProf
     *  @author David Bigelow
     */
    public void setChangeGroup(EntityItem _ei, Profile[] _aProf, Profile _activeProf) {
        boolean bFound = false;
        if (!_ei.getEntityType().equals(getEntityType())) {
            EntityGroup eg = (EntityGroup) _ei.getParent();

            if (eg != null) {
                if (eg.isRelator() || eg.isAssoc()) {
                    //check the child entity item
                    EntityItem eic = (EntityItem) _ei.getDownLink(0);
                    if (!eic.getEntityType().equals(getEntityType())) {
                        //check the parent entity item
                        EntityItem eip = (EntityItem) _ei.getUpLink(0);
                        if (eip.getEntityType().equals(getEntityType())) {
                            _ei = eip;
                            bFound = true;
                        }
                    } else {
                        _ei = eic;
                        bFound = true;
                    }
                }
            }
        } else {
            bFound = true;
        }

        if (_aProf == null) {
            D.ebug(D.EBUG_SPEW,"CGActionItem setChangeGroup array of profs is null");
            return;
        }

        if (bFound == true) {
            for (int i = 0; i < _aProf.length; i++) {
                if (_aProf[i].getEnterprise().equals(_activeProf.getEnterprise()) && _aProf[i].getOPWGID() == _activeProf.getOPWGID()) {
                    D.ebug(D.EBUG_SPEW,"CGActionItem setChangeGroup for prof: " + _aProf[i].dump(true));
                    _aProf[i].setTranID(_ei.getEntityID());
                }
            }
        } else {
            D.ebug(D.EBUG_SPEW,"CGActionItem setChangeGroup can't find matching entity: " + _ei.getKey());
        }
    }

    /**
     * clearChangeGroup
     *
     * @param _aProf
     * @param _activeProf
     *  @author David Bigelow
     */
    public static void clearChangeGroup(Profile[] _aProf, Profile _activeProf) {
        for (int i = 0; i < _aProf.length; i++) {
            if (_aProf[i].getEnterprise().equals(_activeProf.getEnterprise()) && _aProf[i].getOPWGID() == _activeProf.getOPWGID()) {
                _aProf[i].resetTranID();
            }
        }
    }

    /**
     * clearTargetChangeGroups out of the active profile who's tranid equals that of the EntityItem's entityid
     * The passed EntityItem must have an entity type of the Change Group
     *
     * @return boolean
     * @param _aProf
     * @param _activeProf
     *  @author David Bigelow
     * @param _aei
     */
    public static boolean clearTargetChangeGroup(Profile[] _aProf, Profile _activeProf, EntityItem _aei[]) {
        boolean bFound = false;
        for (int x = 0; x < _aei.length;x++) {
            EntityItem ei = _aei[x];
            if (!ei.getEntityType().equals(CHANGE_GROUP_ENTITY_TYPE)) {
                continue;
            }
            for (int i = 0; i < _aProf.length; i++) {
                if (_aProf[i].getEnterprise().equals(_activeProf.getEnterprise()) && _aProf[i].getOPWGID() == _activeProf.getOPWGID() && ei.getEntityID() == _aProf[i].getTranID()) {
                    bFound = true;
                    _aProf[i].resetTranID();
                }
            }
        }
        return bFound;
    }

    /**
     * clearTargetChangeGroups out of *all* profiles who's tranid equals that of the EntityItem's entityid
     * The passed EntityItem must have an entity type of the Change Group
     *
     * @param _aProf
     * @param _aei
     *  @author David Bigelow
     */
    public static boolean clearTargetChangeGroupForAll(Profile[] _aProf,  EntityItem _aei[]) {
        String strTraceBase = "CGActionItem clearTargetChangeGroupForAll";
        boolean bFound = false;
        for (int x = 0; x < _aei.length;x++) {
            EntityItem ei = _aei[x];
            if (!ei.getEntityType().equals(CHANGE_GROUP_ENTITY_TYPE)) {
				EntityGroup eg = ei.getEntityGroup();
				if (eg.isRelator()) {
					ei = (EntityItem) ei.getDownLink(0);
					if (!ei.getEntityType().equals(CHANGE_GROUP_ENTITY_TYPE)) {
						continue;
					}
				} else {
					continue;
				}
            }
            for (int i = 0; i < _aProf.length; i++) {
                if (ei.getEntityID() == _aProf[i].getTranID()) {
                    bFound = true;
                    _aProf[i].resetTranID();
                } else {
					D.ebug(D.EBUG_SPEW,strTraceBase + " entityid: " + ei.getEntityID() + " not equal profile tranid: " + _aProf[i].getTranID());
				}
            }
        }
        return bFound;
    }

    /**
     * setChangeGroupForAll
     *
     * @param _ei
     * @param _aProf
     *  @author David Bigelow
     */
    public void setChangeGroupForAll(EntityItem _ei, Profile[] _aProf) {
        boolean bFound = false;
        D.ebug(D.EBUG_SPEW,"CGActionItem setChangeGroupForAll " + _ei.getKey());
        if (!_ei.getEntityType().equals(getEntityType())) {
            EntityGroup eg = (EntityGroup) _ei.getParent();

            if (eg != null) {
                if (eg.isRelator() || eg.isAssoc()) {
                    //check the child entity item
                    EntityItem eic = (EntityItem) _ei.getDownLink(0);
                    if (!eic.getEntityType().equals(getEntityType())) {
                        //check the parent entity item
                        EntityItem eip = (EntityItem) _ei.getUpLink(0);
                        if (eip.getEntityType().equals(getEntityType())) {
                            _ei = eip;
                            bFound = true;
                        }
                    } else {
                        _ei = eic;
                        bFound = true;
                    }
                }
            }
        } else {
            bFound = true;
        }

        if (bFound) {
            for (int i = 0; i < _aProf.length; i++) {
                D.ebug(D.EBUG_SPEW,"CGActionItem setChangeGroupForAll for prof: " + _aProf[i].dump(true));
                _aProf[i].setTranID(_ei.getEntityID());
            }
        } else {
            D.ebug(D.EBUG_SPEW,"CGActionItem setChangeGroupForAll can't find matching entity: " + _ei.getKey());
        }
    }

    /**
     * clearChangeGroupForAll
     *
     * @param _aProf
     *  @author David Bigelow
     */
    public static void clearChangeGroupForAll(Profile[] _aProf) {
        for (int i = 0; i < _aProf.length; i++) {
            _aProf[i].resetTranID();
        }
    }

    /**
     * (non-Javadoc)
     * updatePdhMeta
     *
     * @see COM.ibm.eannounce.objects.EANActionItem#updatePdhMeta(COM.ibm.opicmpdh.middleware.Database, boolean)
     */
    protected boolean updatePdhMeta(Database _db, boolean _bExpire) throws MiddlewareException {
        return false;
    }

}
