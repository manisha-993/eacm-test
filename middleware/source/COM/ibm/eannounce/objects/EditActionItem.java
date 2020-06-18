//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: EditActionItem.java,v $
// Revision 1.55  2013/05/07 18:12:17  wendy
// another perf update for large amt of data
//
// Revision 1.54  2009/05/18 23:06:22  wendy
// Support dereference for memory release
//
// Revision 1.53  2009/05/11 13:52:12  wendy
// Support turning off domain check for all actions
//
// Revision 1.52  2009/03/12 14:59:13  wendy
// Add methods for metaui access
//
// Revision 1.51  2008/02/07 02:29:40  wendy
// Check for rs != null before clear
//
// Revision 1.50  2007/08/03 11:25:44  wendy
// RQ0713072645-1 Make actions sensitive to the PROFILE's PDHDOMAINs
//
// Revision 1.49  2006/02/20 21:39:46  joan
// clean up System.out.println
//
// Revision 1.48  2005/08/22 19:21:49  tony
// Added getTargetType Logic to improve keying of the
// records
//
// Revision 1.47  2005/08/10 16:14:23  tony
// improved catalog viewer functionality.
//
// Revision 1.46  2005/08/03 17:09:44  tony
// added datasource logic for catalog mod
//
// Revision 1.45  2005/02/16 21:10:08  gregg
// allNLS
//
// Revision 1.44  2005/02/15 18:42:24  dave
// More JTest cleanup
//
// Revision 1.43  2005/01/18 21:33:10  dave
// removing the debug parms from code (sp internalized them)
//
// Revision 1.42  2004/11/03 23:12:43  gregg
// MetaColumnOrderGroup for NavActionItems
// MetaLinkAttr switch on ActionItem ColumnOrderControl
// Apply ColumnOrderControl for EntityItems
// Cleanup Debugs
//
// Revision 1.41  2004/07/15 17:28:59  joan
// add Single input
//
// Revision 1.40  2004/06/18 17:11:17  joan
// work on edit relator
//
// Revision 1.39  2004/05/24 19:05:34  joan
// fix bugs
//
// Revision 1.38  2003/10/30 00:43:31  dave
// fixing all the profile references
//
// Revision 1.37  2003/05/09 22:40:12  dave
// minor syntax errors
//
// Revision 1.36  2003/05/09 22:33:03  dave
// more cleanup and trace for controlling ui/non ui needs
//
// Revision 1.35  2003/05/01 18:23:29  dave
// Create StandAlone I
//
// Revision 1.34  2003/05/01 17:49:13  dave
// Allowing relatorless create
//
// Revision 1.33  2003/05/01 17:38:22  dave
// first pass at EditActionItem controlling the create
//
// Revision 1.32  2003/04/24 18:32:16  dave
// getting rid of traces and system out printlns
//
// Revision 1.31  2003/03/27 23:07:21  dave
// adding some timely commits to free up result sets
//
// Revision 1.30  2003/03/10 17:17:59  dave
// attempting to remove GBL7030 from the abstract Action Item
//
// Revision 1.29  2002/11/15 19:32:02  gregg
// more update form
//
// Revision 1.28  2002/11/15 18:53:52  gregg
// in updatePdhMeta -> correctly expire getFormKey record if it is blank (i.e. getformKey().equals(""))
//
// Revision 1.27  2002/11/12 17:18:27  dave
// System.out.println clean up
//
// Revision 1.26  2002/11/07 22:31:50  dave
// cleaning logic for modify
//
// Revision 1.25  2002/11/07 22:28:45  dave
// minor logic change && to ||
//
// Revision 1.24  2002/11/07 21:41:17  dave
// syntax fix
//
// Revision 1.23  2002/11/07 21:31:49  dave
// trying to work on edit kludge
//
// Revision 1.22  2002/10/31 23:05:55  dave
// fix for null put, and deactivate
//
// Revision 1.21  2002/10/16 22:02:45  gregg
// In constructor: added test for linkvalue == 'Y' when for 'Action/Attribute', linkcode = 'View'.
// This way, linkvalues of 'N' will not enable View functionality.
//
// Revision 1.20  2002/10/14 21:16:54  gregg
// in updatePdhMeta method: do not update 'Form' record if it is empty ("").
//
// Revision 1.19  2002/09/16 23:18:02  dave
// null pointer fix
//
// Revision 1.18  2002/09/16 20:18:04  dave
// Entity Only Edit
//
// Revision 1.17  2002/09/13 18:11:43  dave
// adding entityonly logic to edit in attempts to allow
// for cases where the user has no interest
// in any relator .. or association
//
// Revision 1.16  2002/09/10 22:32:18  gregg
// updatePdhMetaLogic
//
// Revision 1.15  2002/09/09 16:52:36  dave
// Null Pointer Trap
//
// Revision 1.14  2002/09/09 15:59:24  dave
// removing the requirement for needing a meta link during edit
//
// Revision 1.13  2002/08/23 21:59:55  gregg
// updatePdhMeta method throws MiddlewareException
//
// Revision 1.12  2002/08/23 21:29:45  gregg
// updatePdhMeta(Database,boolean) method
//
// Revision 1.11  2002/07/16 22:25:13  joan
// work on action item
//
// Revision 1.10  2002/05/24 21:44:36  dave
// fix to 8002 (missing sessionID on all nav)
//
// Revision 1.9  2002/05/20 22:49:06  dave
// syntax fix
//
// Revision 1.8  2002/05/20 22:31:12  dave
// new edit/create defaults in action item
//
// Revision 1.7  2002/04/24 16:03:54  dave
// minor clean up for ActionGroupBinder
//
// Revision 1.6  2002/04/04 00:29:05  dave
// fix isFormsCapable
//
// Revision 1.5  2002/03/28 19:18:18  dave
// removed the canCreate function from EditActionItem
//
// Revision 1.4  2002/03/27 23:51:56  dave
// syntax for addRow, etc
//
// Revision 1.3  2002/03/27 22:34:20  dave
// Row Selectable Table row Add logic. First attempt
//
// Revision 1.2  2002/03/20 19:18:40  dave
// syntax and constructor clean up on strFlagKey
//
// Revision 1.1  2002/03/06 23:53:09  dave
// introduction of the edit action item
//
//

package COM.ibm.eannounce.objects;

import java.sql.ResultSet;
import java.sql.SQLException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import java.util.Vector;

// Temp made this non abstract because we need to compile in general before we whip up
// how actions are suppose to differentiate
// Actually we should be storing the metalink used to do this right
/**
 * EditActionItem
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EditActionItem extends EANActionItem {

    static final long serialVersionUID = 20011106L;

    private MetaLink m_ml = null;
    private String m_strFormKey = null;
    private boolean m_bCreate = false;
    private boolean m_bEdit = false;
    private int m_iDefaultIndex = 0;
    private boolean m_bDefaultIndex = false;
    private MatrixActionItem m_mai = null;
    private String m_strTargetEntity = null;
    private boolean m_bEntityOnly = false;
    /**
     * FIELD
     */
    protected boolean m_bUI = true;
    private boolean m_bEditRelatorOnly = false;
 
    public void dereference(){
    	super.dereference();
    	if (m_ml !=null){
    		m_ml.dereference();
    		m_ml = null;
    	}
        m_strFormKey = null;
        if (m_mai!=null){
        	m_mai.dereference();
        	m_mai = null;
        }
        m_strTargetEntity = null;
    }
    /**
     * Main method which performs a simple test of this class
     *
     * @param arg
     */
    public static void main(String arg[]) {
    }

    /**
     * (non-Javadoc)
     * getVersion
     *
     * @see COM.ibm.eannounce.objects.EANFoundation#getVersion()
     */
    public String getVersion() {
        return "$Id: EditActionItem.java,v 1.55 2013/05/07 18:12:17 wendy Exp $";
    }

    /*
    * Instantiate a new ActionItem based upon a dereferenced version of the Existing One
    */
    /**
     * EditActionItem
     *
     * @param _ai
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public EditActionItem(EditActionItem _ai) throws MiddlewareRequestException {
        super(_ai);
        setMetaLink(_ai.getMetaLink());
        setFormKey(_ai.getFormKey());
        setEdit(_ai.canEdit());
        setDefaultIndex(_ai.getDefaultIndex());
        setHasDefaultIndex(_ai.hasDefaultIndex());
        setEntityOnly(_ai.isEntityOnly());
        setCreate(_ai.canCreate());
        setTargetEntity(_ai.getTargetEntity());
        m_bUI = _ai.m_bUI;
        setEditRelatorOnly(_ai.isEditRelatorOnly());
    }

    /**
     * EditActionItem
     *
     * @param _mf
     * @param _ai
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public EditActionItem(EANMetaFoundation _mf, EditActionItem _ai) throws MiddlewareRequestException {
        super(_mf, _ai);
        setMetaLink(_ai.getMetaLink());
        setFormKey(_ai.getFormKey());
        setEdit(_ai.canEdit());
        setDefaultIndex(_ai.getDefaultIndex());
        setHasDefaultIndex(_ai.hasDefaultIndex());
        setEntityOnly(_ai.isEntityOnly());
        setTargetEntity(_ai.getTargetEntity());
        setCreate(_ai.canCreate());
        m_bUI = _ai.m_bUI;
        setEditRelatorOnly(_ai.isEditRelatorOnly());
    }

    /**
     * This represents an Action Item.  It can only be constructed via a database connection, a Profile, and an Action Item Key*
     *
     * @param _emf
     * @param _db
     * @param _prof
     * @param _strActionItemKey
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     */
    public EditActionItem(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {

        super(_emf, _db, _prof, _strActionItemKey);

        // Lets go get the information pertinent to the Create Action Item

        try {

            ReturnStatus returnStatus = new ReturnStatus(-1);
            ResultSet rs = null;
            ReturnDataResultSet rdrs;
            Profile prof = getProfile();

            try {
                rs = _db.callGBL7030(returnStatus, prof.getEnterprise(), _strActionItemKey, prof.getValOn(), prof.getEffOn());
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
            // Assume they can edit
            // Assume they work w/ relators

            setEdit(true);
            setEntityOnly(false);
            setCreate(false);
            setEditRelatorOnly(false);
            setDomainCheck(true);
            // Set the class and description...
            for (int ii = 0; ii < rdrs.size(); ii++) {

                String strType = rdrs.getColumn(ii, 0);
                String strCode = rdrs.getColumn(ii, 1);
                String strValue = rdrs.getColumn(ii, 2);

                _db.debug(D.EBUG_SPEW, "gbl7030 answer is:" + strType + ":" + strCode + ":" + strValue + ":");

                // Collect the attributes
                if (strType.equals("TYPE") && strCode.equals("Relator")) {
                    setMetaLink(new MetaLink(this, _db, _prof, strValue));
                } else if (strType.equals("TYPE") && strCode.equals("Form")) {
                    setFormKey(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("Entity")) {
                    setEntityOnly(true);
                    setTargetEntity(strValue);
                    setTargetType(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("EditRelator")) {
                    setEditRelatorOnly(true);
                    setTargetEntity(strValue);
                    setTargetType(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("View") && strValue.equals("Y")) {
                    setEdit(false);
                } else if (strType.equals("TYPE") && strCode.equals("Create") && strValue.equals("Y")) {
                    setCreate(true);
                } else if (strType.equals("TYPE") && strCode.equals("DefaultValueIndex")) {
                    setDefaultIndex(Integer.valueOf(strValue).intValue());
                    setHasDefaultIndex(true);
                } else if (strType.equals("TYPE") && strCode.equals("MatrixAction")) {
                    setMatrixActionItem(new MatrixActionItem(_emf, _db, _prof, _strActionItemKey));
                } else if (strType.equals("ENTITYTYPE") && strCode.equals("Link")) {
                    super.setAssociatedEntityType(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("SingleInput")) {
                    setSingleInput(true);
                } else if (strType.equals("TYPE") && strCode.equals("ColumnOrderControl")) {
                    setMetaColumnOrderControl(true);
                } else if (strType.equals("TYPE") && strCode.equals("AllNLS")) {
                    setAllNLS(true);
				} else if (strType.equals("TYPE") && strCode.equals("DataSource")) {	//catalog enhancement
					setDataSource(strValue);											//catalog enhancement
				} else if (strType.equals("TYPE") && strCode.equals("DataSourceParms")) {
					setAdditionalParms(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("DomainCheck")) { // default is on, allow off
				    setDomainCheck(!strValue.equals("N")); //RQ0713072645
                }else {
                    _db.debug(D.EBUG_ERR, "*** ACTION ITEM ATTRIBUTE *** No home for this Action/Attribute" + strType + ":" + strCode + ":" + strValue);
                }
            }

        } finally {
            _db.freeStatement();
            _db.isPending();
        }
    }

    /**
     * modifyActionItem
     *
     * @param _ei
     *  @author David Bigelow
     */
    public void modifyActionItem(EntityItem _ei) {
        EntityGroup eg = _ei.getEntityGroup();
        if (eg == null) {
            setEntityOnly(true);
            setTargetEntity(_ei.getEntityType());
            return;
        }
        // Would be a good idea to check for if it is a true entity here
        if (!eg.isRelator()) {
            setEntityOnly(true);
            if (eg.isAssoc()) {
                // we need to get the child as the target..
                // if there is one
                setTargetEntity(eg.getEntity2Type());
            } else {
                setTargetEntity(eg.getEntityType());
            }
        }
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {

        StringBuffer strbResult = new StringBuffer();
        strbResult.append("EditActionItem:" + super.dump(_bBrief));
        strbResult.append("\nMetaLink:");
        if (getMetaLink() == null) {
            strbResult.append(":**null**:auto create when user selects obect selected");
        } else {
            strbResult.append(getMetaLink().getKey());
        }
        strbResult.append(":isFormCapable:" + isFormCapable());
        strbResult.append(":FormKey:" + getFormKey());
        strbResult.append(":canEdit:" + canEdit());
        strbResult.append(":DefaultIndex:" + hasDefaultIndex());
        strbResult.append(":" + getDefaultIndex());

        return strbResult.toString();

    }

    /**
     * (non-Javadoc)
     * getPurpose
     *
     * @see COM.ibm.eannounce.objects.EANActionItem#getPurpose()
     */
    public String getPurpose() {
        return "Edit";
    }

    /**
     * getMetaLink
     *
     * @return
     *  @author David Bigelow
     */
    public MetaLink getMetaLink() {
        return m_ml;
    }

    /**
     * getFormKey
     *
     * @return
     *  @author David Bigelow
     */
    public String getFormKey() {
        return m_strFormKey;
    }

    /**
     * setMetaLink
     *
     * @param _ml
     *  @author David Bigelow
     */
    public void setMetaLink(MetaLink _ml) {
        m_ml = _ml;
        if (m_ml != null) {
            m_ml.setParent(this);
        }
    }
    /*********
     * meta ui must update action
     * @throws MiddlewareException 
     * @throws SQLException 
     * @throws MiddlewareRequestException 
     */
    public void updateMetaLink(EANMetaFoundation _emf, Database _db, Profile _prof, String _strEntityType) 
    throws MiddlewareRequestException, SQLException, MiddlewareException
    {
    	setMetaLink(new MetaLink(_emf, _db, _prof, _strEntityType));
    }
    public void setActionClass(){
    	setActionClass("Edit");
    }
    public void updateAction(String strForm, boolean bEdit){
    	setFormKey(strForm);
    	setEdit(bEdit);
    }    
    /**
     * setFormKey
     *
     * @param _str
     *  @author David Bigelow
     */
    protected void setFormKey(String _str) {
        m_strFormKey = _str;
    }

    /**
     * isFormCapable
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isFormCapable() {
        return m_strFormKey != null;
    }

    /**
     * canEdit
     *
     * @return
     *  @author David Bigelow
     */
    public boolean canEdit() {
        return m_bEdit;
    }

    /**
     * setEdit
     *
     * @param _b
     *  @author David Bigelow
     */
    protected void setEdit(boolean _b) {
        m_bEdit = _b;
    }

    /**
     * canCreate
     *
     * @return
     *  @author David Bigelow
     */
    public boolean canCreate() {
        return m_bCreate;
    }

    /**
     * setCreate
     *
     * @param _b
     *  @author David Bigelow
     */
    protected void setCreate(boolean _b) {
        m_bCreate = _b;
    }
    /**
     * hasDefaultIndex
     *
     * @return
     *  @author David Bigelow
     */
    public boolean hasDefaultIndex() {
        return m_bDefaultIndex;
    }

    /**
     * setHasDefaultIndex
     *
     * @param _b
     *  @author David Bigelow
     */
    protected void setHasDefaultIndex(boolean _b) {
        m_bDefaultIndex = _b;
    }

    /**
     * getDefaultIndex
     *
     * @return
     *  @author David Bigelow
     */
    public int getDefaultIndex() {
        return m_iDefaultIndex;
    }

    /**
     * setDefaultIndex
     *
     * @param _i
     *  @author David Bigelow
     */
    protected void setDefaultIndex(int _i) {
        m_iDefaultIndex = _i;
    }

    /**
     * setEntityOnly
     *
     * @param _b
     *  @author David Bigelow
     */
    protected void setEntityOnly(boolean _b) {
    	D.ebug(D.EBUG_SPEW,"EditActionItem.setEntityOnly() "+getActionItemKey()+" being set to:" + _b);
        m_bEntityOnly = _b;
    }
    /**
     * isEntityOnly
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isEntityOnly() {
        return m_bEntityOnly;
    }

    /**
     * setEditRelatorOnly
     *
     * @param _b
     *  @author David Bigelow
     */
    protected void setEditRelatorOnly(boolean _b) {
        D.ebug(D.EBUG_SPEW,"EditActionItem.setEditRelatorOnly() "+getActionItemKey()+" being set to:" + _b);
        m_bEditRelatorOnly = _b;
    }
    /**
     * isEditRelatorOnly
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isEditRelatorOnly() {
        return m_bEditRelatorOnly;
    }

    /**
     * setTargetEntity
     *
     * @param _str
     *  @author David Bigelow
     */
    protected void setTargetEntity(String _str) {
        D.ebug(D.EBUG_SPEW,"EditActionItem.setTargetEntity(): "+getActionItemKey()+" setting to:" + _str);
        m_strTargetEntity = _str;
    }

    /**
     * getTargetEntity
     *
     * @return
     *  @author David Bigelow
     */
    public String getTargetEntity() {
        if (isEntityOnly()) {
            return m_strTargetEntity;
        } else {
            return null;
        }
    }

    /**
     * getMatrixActionItem
     *
     * @return
     *  @author David Bigelow
     */
    public MatrixActionItem getMatrixActionItem() {
        return m_mai;
    }

    /**
     * setMatrixActionItem
     *
     * @param _mai
     *  @author David Bigelow
     */
    protected void setMatrixActionItem(MatrixActionItem _mai) {
        m_mai = _mai;
        if (m_mai != null) {
            m_mai.setParent(this);
        }
    }

    /**
     * getActionItemArray
     *
     * @return
     *  @author David Bigelow
     */
    public Object[] getActionItemArray() {
        Vector aiVector = new Vector();

        //add MatrixActionItem
        if (m_mai != null) {
            aiVector.addElement(m_mai);
        }

        if (aiVector.size() > 0) {
            return aiVector.toArray();

        } else {
            return null;
        }
    }

    /**
     * (non-Javadoc)
     * updatePdhMeta
     *
     * @see COM.ibm.eannounce.objects.EANActionItem#updatePdhMeta(COM.ibm.opicmpdh.middleware.Database, boolean)
     */
    protected boolean updatePdhMeta(Database _db, boolean _bExpire) throws MiddlewareException {
        try {
            //lets get a fresh object from the database
            EditActionItem ed_db = new EditActionItem(null, _db, getProfile(), getActionItemKey());
            boolean bNewAction = false;
            //check for new
            if (ed_db.getActionClass() == null) {
                bNewAction = true;
            }

            //EXPIRES
            if (_bExpire && !bNewAction) {
                //Relator
                if (ed_db.getMetaLink() != null) {
                    updateActionAttribute(_db, true, "TYPE", "Relator", ed_db.getMetaLink().getEntityType());

                } else {
                    _db.debug(D.EBUG_ERR, "EditActionItem.updatePdhMeta:MetaLink is null for \"" + ed_db.getActionItemKey() + "\"");
                }
                //Form
                if (ed_db.getFormKey() != null && !ed_db.getFormKey().equals("")) {
                    updateActionAttribute(_db, true, "TYPE", "Form", ed_db.getFormKey());
                }
                //canEdit
                updateActionAttribute(_db, true, "TYPE", "View", (ed_db.canEdit() ? "N" : "Y"));

            } else {
                //Relator
                if (ed_db.getMetaLink() != null && this.getMetaLink() != null) {
                    String strRelator_db = ed_db.getMetaLink().getEntityType();
                    String strRelator_this = this.getMetaLink().getEntityType();
                    //only need to insert since this will vary only by linkvalue
                    if (bNewAction || !strRelator_db.equals(strRelator_this)) {
                        updateActionAttribute(_db, false, "TYPE", "Relator", strRelator_this);
                    }
                } else if (ed_db.getMetaLink() == null && this.getMetaLink() != null) {
                    updateActionAttribute(_db, false, "TYPE", "Relator", getMetaLink().getEntityType());
                } else {
                    _db.debug(D.EBUG_ERR, "EditActionItem.updatePdhMeta:MetaLink is null for \"" + this.getActionItemKey() + "\"");
                }

                //Form
                if (getFormKey() != null) {
                    if (bNewAction || ed_db == null || (!this.getFormKey().equals(ed_db.getFormKey()))) {
                        //only need to insert since this will vary only by linkvalue
                        if (!this.getFormKey().equals("")) {
                            updateActionAttribute(_db, false, "TYPE", "Form", this.getFormKey());

                        } else if (this.getFormKey().equals("") && ed_db.getFormKey() != null && !bNewAction) {
                            updateActionAttribute(_db, true, "TYPE", "Form", ed_db.getFormKey());
                        }
                    }
                } else { //expire
                    if (ed_db.getFormKey() != null) {
                        updateActionAttribute(_db, true, "TYPE", "Form", ed_db.getFormKey());
                    }
                }

                //Peer
                // 2) Peer create
                if (bNewAction || ed_db.canEdit() != this.canEdit()) {
                    updateActionAttribute(_db, false, "TYPE", "View", (this.canEdit() ? "N" : "Y"));
                }
            }

        } catch (SQLException sqlExc) {
            _db.debug(D.EBUG_ERR, "EditActionItem 361 " + sqlExc.toString());
            return false;
        } finally {
            _db.freeStatement();
            _db.isPending();
        }
        return true;
    }

    /**
     * disableUIAfterCache
     *
     *  @author David Bigelow
     */
    protected void disableUIAfterCache() {
        m_bUI = false;
    }

    /**
     * enableUIAfterCache
     *
     *  @author David Bigelow
     */
    protected void enableUIAfterCache() {
        m_bUI = true;
    }

    /**
     * isUIAfterCacheEnabled
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isUIAfterCacheEnabled() {
        return m_bUI;
    }

    /**
     * to simplify things a bit
     */
    private void updateActionAttribute(Database _db, boolean _bExpire, String _strLinkType2, String _strLinkCode, String _strLinkValue) throws MiddlewareException {
        String strValFrom = _db.getDates().getNow();
        String strValTo = (_bExpire ? strValFrom : _db.getDates().getForever());
        new MetaLinkAttrRow(getProfile(), "Action/Attribute", getActionItemKey(), _strLinkType2, _strLinkCode, _strLinkValue, strValFrom, strValTo, strValFrom, strValTo, 2).updatePdh(_db);
    }
}
