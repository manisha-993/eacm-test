//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
//{{{ Log
//$Log: DataDrivenWorkflowItem.java,v $
//Revision 1.28  2005/02/14 17:18:33  dave
//more jtest fixing
//
//Revision 1.27  2003/03/27 23:07:20  dave
//adding some timely commits to free up result sets
//
//Revision 1.26  2003/02/03 20:17:09  gregg
//null ptr fix in getChildItems method
//
//Revision 1.25  2003/02/03 18:42:10  gregg
//s'more field checks in updatePdhMeta
//
//Revision 1.24  2003/01/17 21:09:04  gregg
//allFieldsExist method
//
//Revision 1.23  2003/01/02 19:51:33  gregg
//return a boolean in updatePdhMeta indicating whether any PHD updates were performed.
//
//Revision 1.22  2002/12/11 22:22:43  gregg
//ooops - fixed params to gbl7508 in getPossibleActions method
//
//Revision 1.21  2002/12/11 22:04:02  gregg
//getAllPossibleAction method -> changed getPossibleActions method to include only those belonging to parent group
//
//Revision 1.20  2002/12/10 23:33:35  gregg
//closing off some db connections after use
//
//Revision 1.19  2002/11/27 23:01:20  gregg
//fix in getChildItems method
//
//Revision 1.18  2002/11/27 20:16:30  gregg
//parent/child/level/tree logic + methods
//
//Revision 1.17  2002/11/27 19:27:54  gregg
//use RoleCode for input into 7532, not opwgid
//
//Revision 1.16  2002/11/26 23:18:25  gregg
//s;more fine-tuning getPossibleGroups
//
//Revision 1.15  2002/11/26 22:58:58  gregg
//now using gbl7526 in getPossibleGroups method (i.e. pull all groups for this entity
//keying off of Role/Action/EntityGroup record regardless of role or action)
//
//Revision 1.14  2002/11/26 22:27:56  gregg
//use gbl7532 for getPossibleEntities
//
//Revision 1.13  2002/11/26 20:35:06  gregg
//setAction, setEntity, setGroup methods: only take action if anything has changed
//
//Revision 1.12  2002/11/26 19:15:37  gregg
//oh, yeah - we must set the m_saPossibleXXX arrays at the end of their corresponding get methods
//
//Revision 1.11  2002/11/26 18:35:27  gregg
//store possible Actions, Entities, Groups until relevant data has changed.
//also: resetPossibleActions, resetPossibleEntities, resetPossibleGroups
//
//Revision 1.10  2002/11/26 01:14:10  gregg
//in getPossibleActions methos -> only include Navigate, Extract classes
//
//Revision 1.9  2002/11/26 01:08:11  gregg
//prevent null ptrs in dump
//
//Revision 1.8  2002/11/26 00:39:21  gregg
//- new constructor
//- getPossibleActions, getPossibleEntities, getPossibleGroups methods
//
//Revision 1.7  2002/10/14 17:20:25  gregg
//updatePdhMeta/expirePdhMeta methods
//
//Revision 1.6  2002/10/11 23:16:46  gregg
//setParent() again in rehash()
//
//Revision 1.5  2002/10/11 23:09:59  gregg
// serialVersionUID
//
//Revision 1.4  2002/10/11 22:40:56  gregg
//fix in rehash() - parent reference is lost on removeMeta
//
//Revision 1.3  2002/10/11 22:06:38  gregg
//rehash() on setAction, setGroup, setRole, setEntity
//
//Revision 1.2  2002/10/11 20:12:36  gregg
//getVersion method
//
//Revision 1.1  2002/10/11 17:30:51  gregg
//initial load
//
//}}}

package COM.ibm.eannounce.objects;

import java.util.Hashtable;
import java.util.Vector;
import java.sql.ResultSet;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.ReturnStatus;

/**
 * Manages one Role/Action/Entity/Group relationship
 */
public class DataDrivenWorkflowItem extends EANMetaEntity implements EANComparable {

    /**
    * @serial
    */
    static final long serialVersionUID = 1L;

    /**
     * FIELD
     */
    public static final String SORT_BY_ROLE = "Role Code";
    /**
     * FIELD
     */
    public static final String SORT_BY_ACTION = "Action Item Key";
    /**
     * FIELD
     */
    public static final String SORT_BY_ENTITY = "Entity Type";
    /**
     * FIELD
     */
    public static final String SORT_BY_GROUP = "Action Group";
    private String m_strCompField = SORT_BY_ACTION;

    private String m_strRole = null;
    private String m_strAction = null;
    private String m_strEntity = null;
    private String m_strGroup = null;
    private String m_strParentGroup = null;
    private int m_iLevel = -1;

    private String[] m_saAllPossibleActions = null;
    private String[] m_saPossibleActions = null;
    private String[] m_saPossibleEntities = null;
    private String[] m_saPossibleGroups = null;

    private boolean m_bFiltered = false;

    /**
     * FIELD
     */
    public static final String HTML_NEW_LINE = "<BR>";

    /**
     * Create a new Item w/ all fields
     *
     * @param _emf
     * @param _prof
     * @param _strRole
     * @param _strAction
     * @param _strEntity
     * @param _strGroup
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public DataDrivenWorkflowItem(EANMetaFoundation _emf, Profile _prof, String _strRole, String _strAction, String _strEntity, String _strGroup) throws MiddlewareRequestException {
        super(_emf, _prof, _strRole + _strAction + _strEntity + _strGroup);
        m_strRole = _strRole; //setRole(_strRole);
        m_strAction = _strAction; //setAction(_strAction);
        m_strEntity = _strEntity; //setEntity(_strEntity);
        m_strGroup = _strGroup; //setGroup(_strGroup);
    }

    /**
     * Only the Role is known:
     *  -- first setAction(),
     *  -- then setEntity(),
     *  -- then setGroup()
     *
     * @param _emf
     * @param _prof
     * @param _strRole
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public DataDrivenWorkflowItem(EANMetaFoundation _emf, Profile _prof, String _strRole) throws MiddlewareRequestException {
        super(_emf, _prof, _strRole);
        m_strRole = _strRole;
    }

    //// EANComparable
    /**
     * (non-Javadoc)
     * setCompareField
     *
     * @see COM.ibm.eannounce.objects.EANComparable#setCompareField(java.lang.String)
     */
    public void setCompareField(String _s) {
        m_strCompField = _s;
    }

    /**
     * (non-Javadoc)
     * getCompareField
     *
     * @see COM.ibm.eannounce.objects.EANComparable#getCompareField()
     */
    public String getCompareField() {
        return m_strCompField;
    }

    /**
     * (non-Javadoc)
     * toCompareString
     *
     * @see COM.ibm.eannounce.objects.EANComparable#toCompareString()
     */
    public String toCompareString() {
        if (getCompareField().equals(SORT_BY_ROLE)) {
            return getRole();

        } else if (getCompareField().equals(SORT_BY_ENTITY)) {
            return getEntity();

        } else if (getCompareField().equals(SORT_BY_GROUP)) {
            return getGroup();
        }
        return getAction();
    }

    private DataDrivenWorkflowGroup getParentGroup() {
        return (DataDrivenWorkflowGroup) getParent();
    }

    // if any part of the key has changed, we need to rehash things
    private void rehash() {
        DataDrivenWorkflowGroup parent = getParentGroup();
        parent.removeMeta(this);
        setKey(getRole() + getAction() + getEntity() + getGroup());
        setParent(parent);
        parent.putMeta(this);
        return;
    }

    /// MUTATORS
    /**
     * setRole
     *
     * @param _s
     *  @author David Bigelow
     */
    public void setRole(String _s) {
        //only anything has changed...
        if (m_strRole == null || !_s.equals(m_strRole)) {
            m_strRole = _s;
            rehash();
        }
    }

    /**
     * setAction
     *
     * @param _s
     *  @author David Bigelow
     */
    public void setAction(String _s) {
        //only anything has changed...
        if (m_strAction == null || !_s.equals(m_strAction)) {
            m_strAction = _s;
            //possible entities AND groups will change when the action is changed
            resetPossibleEntities();
            resetPossibleGroups();
            //
            rehash();
        }
    }

    /**
     * setEntity
     *
     * @param _s
     *  @author David Bigelow
     */
    public void setEntity(String _s) {
        //only anything has changed...
        if (m_strEntity == null || !_s.equals(m_strEntity)) {
            m_strEntity = _s;
            //possible groups will change when the action is changed
            resetPossibleGroups();
            rehash();
        }
    }

    /**
     * setGroup
     *
     * @param _s
     *  @author David Bigelow
     */
    public void setGroup(String _s) {
        m_strGroup = _s;
        rehash();
    }

    /**
     * setParentActionGroup
     *
     * @param _s
     *  @author David Bigelow
     */
    public void setParentActionGroup(String _s) {
        m_strParentGroup = _s;
    }

    /**
     * setLevel
     *
     * @param _i
     *  @author David Bigelow
     */
    public void setLevel(int _i) {
        m_iLevel = _i;
    }

    /**
     * resetPossibleActions
     *
     *  @author David Bigelow
     */
    protected void resetPossibleActions() {
        m_saPossibleActions = null;
    }

    /**
     * resetPossibleEntities
     *
     *  @author David Bigelow
     */
    protected void resetPossibleEntities() {
        m_saPossibleEntities = null;
    }

    /**
     * resetPossibleGroups
     *
     *  @author David Bigelow
     */
    protected void resetPossibleGroups() {
        m_saPossibleGroups = null;
    }
    /**
     * Given an enterprise and a ~parent~ Action Group, list the possible actions w/ in this group
     *
     * @param _db
     * @param _strParentActionGroup
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @return String[]
     */
    public String[] getPossibleActions(Database _db, String _strParentActionGroup) throws MiddlewareException {
        String strEnterprise = null;
        String[] sa = null;
        if (getProfile() == null) {
            throw new MiddlewareException("No Profile, so we cannot figure out possible actions!");
        }
        //if we already have these, then no reason to rebuild them, eh?
        if (m_saPossibleActions != null) {
            return m_saPossibleActions;
        }
        //
        strEnterprise = getProfile().getEnterprise();
        try {
            String strNow = _db.getDates().getNow();
            ResultSet rs = null;
            ReturnDataResultSet rdrs = null;
            try {
                rs = _db.callGBL7508(new ReturnStatus(-1), strEnterprise, "Group/Action", _strParentActionGroup, "Link", strNow, strNow);
                rdrs = new ReturnDataResultSet(rs);
            } finally {
                rs.close();
                rs = null;
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }
            sa = new String[rdrs.getRowCount()];
            for (int i = 0; i < rdrs.getRowCount(); i++) {
                sa[i] = rdrs.getColumn(i, 0);
            }
            m_saPossibleActions = sa;
        } catch (Exception exc) {
            _db.debug(D.EBUG_ERR, exc.toString() + " in DataDrivenWorkflowItem.getPossibleactions");
        } finally {
            _db.freeStatement();
            _db.isPending();
        }
        return sa;
    }

    /**
     * Given an enterprise, list the possible actions
     *
     * @param _db
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @return String[]
     */
    public String[] getAllPossibleActions(Database _db) throws MiddlewareException {
        String strEnterprise = null;
        Vector v = new Vector();
        Hashtable hashKeys = null;
        String[] sa = null;
           
        if (getProfile() == null) {
            throw new MiddlewareException("No Profile, so we cannot figure out possible actions!");
        }
        //if we already have these, then no reason to rebuild them, eh?
        if (m_saAllPossibleActions != null) {
            return m_saAllPossibleActions;
        }
        //
        strEnterprise = getProfile().getEnterprise();
        try {
            ResultSet rs = null;
            ReturnDataResultSet rdrs = null;
            try {
                rs = _db.callGBL7524(new ReturnStatus(-1), strEnterprise);
                rdrs = new ReturnDataResultSet(rs);
            } finally {
                rs.close();
                rs = null;
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }
            //keep track of the keys that have been added, b/c we will potentially get multiple nls'd
            hashKeys = new Hashtable(rdrs.getRowCount());
            v = new Vector(rdrs.getRowCount());
            for (int row = 0; row < rdrs.getRowCount(); row++) {
                String strActionName = rdrs.getColumn(row, 0);
                String strActionClass = rdrs.getColumn(row, 1);
                boolean bValidClass = (strActionClass.equalsIgnoreCase("Navigate") || strActionClass.equalsIgnoreCase("Extract"));
                if (hashKeys.get(strActionName) == null && bValidClass) {
                    hashKeys.put(strActionName, "ratsliveonnoevilstar");
                    //_db.debug(D.EBUG_SPEW,"gbl7524 answer:" + strActionName + " --> possible action for " + strEnterprise);
                    v.addElement(strActionName);
                } else { //its already been stored
                    continue;
                }
            }
        } catch (Exception exc) {
            _db.debug(D.EBUG_ERR, exc.toString() + " in DataDrivenWorkflowItem.getPossibleactions");
        } finally {
            _db.freeStatement();
            _db.isPending();
        }
        sa = new String[v.size()];
        for (int i = 0; i < v.size(); i++) {
            sa[i] = (String) v.elementAt(i);
        }
        m_saAllPossibleActions = sa;
        return sa;
    }

    /**
     * Given an action, list the possible Entities (and relators, Assoc's)
     *
     * @param _db
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @return String[]
     */
    public String[] getPossibleEntities(Database _db) throws MiddlewareException {
        
        String strEnterprise = null;
        Vector v = new Vector();
        String[] sa = null;

        if (getProfile() == null) {
            throw new MiddlewareException("No Profile defined, so we cannot figure out possible Entities!");
        }
        if (getAction() == null) {
            throw new MiddlewareException("No Action defined, so we cannot figure out possible Entities!");
        }
        //if we already have these, then no reason to rebuild them, eh?
        if (m_saPossibleEntities != null) {
            return m_saPossibleEntities;
        }
        
        strEnterprise = getProfile().getEnterprise();
        try {
            String strNow = _db.getDates().getNow();
            ResultSet rs = null;
            ReturnDataResultSet rdrs = null;
            try {
                rs = _db.callGBL7532(new ReturnStatus(-1), strEnterprise, getRole(), getAction(), strNow, strNow);
                rdrs = new ReturnDataResultSet(rs);
            } finally {
                rs.close();
                rs = null;
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }
            v = new Vector(rdrs.getRowCount());
            for (int row = 0; row < rdrs.getRowCount(); row++) {
                String strEntType = rdrs.getColumn(row, 0);
                v.addElement(strEntType);
            }
        } catch (Exception exc) {
            _db.debug(D.EBUG_ERR, exc.toString() + " in DataDrivenWorkflowItem.getPossibleEntities");
        } finally {
            _db.freeStatement();
            _db.isPending();
        }
        sa = new String[v.size()];
        for (int i = 0; i < v.size(); i++) {
            sa[i] = (String) v.elementAt(i);
        }
        m_saPossibleEntities = sa;
        return sa;
    }

    /**
     * Given an Role+Action+Entity, list the possible Groups
     *
     * @param _db
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @return String[]
     */
    public String[] getPossibleGroups(Database _db) throws MiddlewareException {
        
        Vector v = new Vector();
        Hashtable hashKeys = null;
        String[] sa = null;
        String strEnterprise = null;
        
        if (getProfile() == null) {
            throw new MiddlewareException("No Profile defined, so we cannot figure out possible Groups!");
        }
        if (getAction() == null) {
            throw new MiddlewareException("No Action defined, so we cannot figure out possible Groups!");
        }
        if (getEntity() == null) {
            throw new MiddlewareException("No Entity defined, so we cannot figure out possible Groups!");
        }
        //if we already have these, then no reason to rebuild them, eh?
        if (m_saPossibleGroups != null) {
            return m_saPossibleGroups;
        }
        
        strEnterprise = getProfile().getEnterprise();
        
        try {
            ResultSet rs = null;
            ReturnDataResultSet rdrs = null;
            
            String strNow = _db.getDates().getNow();
            try {
                rs = _db.callGBL7526(new ReturnStatus(-1), strEnterprise, "Role/Action/Entity/Group", getEntity(), strNow, strNow);
                rdrs = new ReturnDataResultSet(rs);
            } finally {
                rs.close();
                rs = null;
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }
            
            v = new Vector(rdrs.getRowCount());
            hashKeys = new Hashtable(rdrs.getRowCount());
            for (int row = 0; row < rdrs.getRowCount(); row++) {
                String strGroup = rdrs.getColumn(row, 2); //we want LINKVALUE
                if (hashKeys.get(strGroup) == null) {
                    v.addElement(strGroup);
                    hashKeys.put(strGroup, "dogeeseseegod");
                } else {
                    continue;
                }
            }
        } catch (Exception exc) {
            _db.debug(D.EBUG_ERR, exc.toString() + " in DataDrivenWorkflowItem.getPossibleGroups");
        } finally {
            _db.freeStatement();
            _db.isPending();
        }
        
        sa = new String[v.size()];
        for (int i = 0; i < v.size(); i++) {
            sa[i] = (String) v.elementAt(i);
        }
        m_saPossibleGroups = sa;
        return sa;
    }

    /**
     * setFiltered
     *
     * @param _b
     *  @author David Bigelow
     */
    public void setFiltered(boolean _b) {
        m_bFiltered = _b;
    }

    //////
    /**
     * Are all fields complete?
     * Look at getRole(),getAction(),getEntity(),getGroup()
     *
     * @return boolean
     */
    public boolean allFieldsExist() {
        return (!(getRole() == null || getRole().equals("") || getAction() == null || getAction().equals("") || getEntity() == null || getEntity().equals("") || getGroup() == null || getGroup().equals("")));
    }

    /// ACCESSORS

    /**
     * getRole
     *
     * @return
     *  @author David Bigelow
     */
    public String getRole() {
        return m_strRole;
    }

    /**
     * getAction
     *
     * @return
     *  @author David Bigelow
     */
    public String getAction() {
        return m_strAction;
    }

    /**
     * getEntity
     *
     * @return
     *  @author David Bigelow
     */
    public String getEntity() {
        return m_strEntity;
    }

    /**
     * getGroup
     *
     * @return
     *  @author David Bigelow
     */
    public String getGroup() {
        return m_strGroup;
    }

    /**
     * isFiltered
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isFiltered() {
        return m_bFiltered;
    }

    /**
     * getParentActionGroup
     *
     * @return
     *  @author David Bigelow
     */
    public String getParentActionGroup() {
        return m_strParentGroup;
    }

    /**
     * getLevel
     *
     * @return
     *  @author David Bigelow
     */
    public int getLevel() {
        return m_iLevel;
    }
    //////////////

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _b) {
        StringBuffer sb = new StringBuffer();
        sb.append(HTML_NEW_LINE + "----- DataDrivenWorkflowItem -----");
        sb.append(HTML_NEW_LINE + "Role:" + getRole());
        if (getAction() != null) {
            sb.append(HTML_NEW_LINE + "Action:" + getAction());
        }
        if (getEntity() != null) {
            sb.append(HTML_NEW_LINE + "Entity:" + getEntity());
        }
        if (getGroup() != null) {
            sb.append(HTML_NEW_LINE + "Group:" + getGroup());
        }
        return sb.toString();
    }

    //////// UPDATE Methods ////////////
    //Ideally, these will only be called from DataDrivenWorkflowGroup
    /**
     * insertPdhMeta
     *
     * @param _db
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @return
     *  @author David Bigelow
     */
    protected boolean insertPdhMeta(Database _db) throws MiddlewareException {
        return updatePdhMeta(_db, false);
    }

    /**
     * expirePdhMeta
     *
     * @param _db
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @return
     *  @author David Bigelow
     */
    protected boolean expirePdhMeta(Database _db) throws MiddlewareException {
        return updatePdhMeta(_db, true);
    }

    private boolean updatePdhMeta(Database _db, boolean _bExpire) throws MiddlewareException {
        String strNow = _db.getDates().getNow();
        String strForever = _db.getDates().getForever();
        String strValEffTo = strForever;
        if (_bExpire) {
            strValEffTo = strNow;
        }

        //check validity of data
        if (getRole() == null) {
            throw new MiddlewareException("Role is null! Update not performed.");
        }
        if (getAction() == null) {
            throw new MiddlewareException("Action is null! Update not performed.");
        }
        if (getEntity() == null) {
            throw new MiddlewareException("Entity is null! Update not performed.");
        }
        if (getGroup() == null) {
            throw new MiddlewareException("Group is null! Update not performed.");
        }

        if (getRole().equals("")) {
            throw new MiddlewareException("Role is empty! Update not performed.");
        }
        if (getAction().equals("")) {
            throw new MiddlewareException("Action is empty! Update not performed.");
        }
        if (getEntity().equals("")) {
            throw new MiddlewareException("Entity is empty! Update not performed.");
        }
        if (getGroup().equals("")) {
            throw new MiddlewareException("Group is empty! Update not performed.");
        }

        new MetaLinkAttrRow(getProfile(), "Role/Action/Entity/Group", getRole(), getAction(), getEntity(), getGroup(), strNow, strValEffTo, strNow, strValEffTo, 2).updatePdh(_db);
        return true;
    }
    /////////////////////

    /**
     * Return the date/time this class was generated
     * @return the date/time this class was generated
     */
    public String getVersion() {
        return "$Id: DataDrivenWorkflowItem.java,v 1.28 2005/02/14 17:18:33 dave Exp $";
    }

    /////////
    //  parent, children methods
    /////////

    /**
     * All DataDrivenWorkflowItems in this item's DataDrivenWorkflowGroup whose ActionGroup contains this item's Action
     *
     * @return EANList
     */
    public EANList getParentItems() {
        EANList eListParents = new EANList();
        if (getParent() == null || !(getParent() instanceof DataDrivenWorkflowGroup) || getParentActionGroup() == null) {
            return eListParents;
        } //return emptiness
        for (int i = 0; i < getParentGroup().getObjectCount(); i++) {
            if (getParentActionGroup().equals(getParentGroup().getItem(i).getGroup())) {
                eListParents.put(getParentGroup().getItem(i));
            }
        }
        return eListParents;
    }

    /**
     * hasParentItems
     *
     * @return
     *  @author David Bigelow
     */
    public boolean hasParentItems() {
        return (getParentItems().size() > 0);
    }

    /**
     * All DataDrivenWorkflowItems in this item's DataDrivenWorkflowGroup whose Action is contained in this item's ActionGroup
     *
     * @return EANList
     */
    public EANList getChildItems() {
        EANList eListChildren = new EANList();
        //if(getParent() == null || !(getParent() instanceof DataDrivenWorkflowGroup) || getGroup() == null)
        //    return eListChildren; //return emptiness
        for (int i = 0; i < getParentGroup().getObjectCount(); i++) {
            if (getGroup() != null && getGroup().equals(getParentGroup().getItem(i).getParentActionGroup())) {
                eListChildren.put(getParentGroup().getItem(i));
            }
        }
        return eListChildren;
    }

    /**
     * hasChildItems
     *
     * @return
     *  @author David Bigelow
     */
    public boolean hasChildItems() {
        return (getChildItems().size() > 0);
    }

}
