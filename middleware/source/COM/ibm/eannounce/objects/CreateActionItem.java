//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: CreateActionItem.java,v $
// Revision 1.54  2009/08/26 17:56:18  wendy
// SR5 updates
//
// Revision 1.53  2009/05/18 23:12:49  wendy
// Support dereference for memory release
//
// Revision 1.52  2009/05/11 13:50:50  wendy
// Support turning off domain check for all actions
//
// Revision 1.51  2009/03/12 14:59:14  wendy
// Add methods for metaui access
//
// Revision 1.50  2007/08/03 11:25:44  wendy
// RQ0713072645-1 Make actions sensitive to the PROFILE's PDHDOMAINs
//
// Revision 1.49  2006/02/20 21:39:45  joan
// clean up System.out.println
//
// Revision 1.48  2005/08/22 19:21:49  tony
// Added getTargetType Logic to improve keying of the
// records
//
// Revision 1.47  2005/08/10 16:14:23  tony
// improved catalog viewer functionality.
//
// Revision 1.46  2005/08/03 17:09:43  tony
// added datasource logic for catalog mod
//
// Revision 1.45  2005/02/16 21:10:07  gregg
// allNLS
//
// Revision 1.44  2005/02/14 17:18:33  dave
// more jtest fixing
//
// Revision 1.43  2005/01/18 21:33:09  dave
// removing the debug parms from code (sp internalized them)
//
// Revision 1.42  2004/10/25 17:23:00  joan
// work on create parent
//
// Revision 1.41  2004/09/15 18:04:33  joan
// work on BluePage
//
// Revision 1.40  2004/07/15 17:28:59  joan
// add Single input
//
// Revision 1.39  2004/06/25 17:27:40  joan
// work on BluePage
//
// Revision 1.38  2004/06/25 16:18:30  joan
// work on BluePage
//
// Revision 1.37  2004/01/08 19:58:12  dave
// testing out AL local cache technique
//
// Revision 1.36  2003/10/30 00:43:30  dave
// fixing all the profile references
//
// Revision 1.35  2003/07/17 22:44:16  dave
// adding   m_bPeer = _ai.m_bPeer;
//
// Revision 1.34  2003/05/10 08:45:38  dave
// de-UIing the Create for back end processing
//
// Revision 1.33  2003/05/02 18:41:57  dave
// adding trace
//
// Revision 1.32  2003/05/02 18:22:58  dave
// Fix to retain StandAlonEntityItem when
// a create action item is derived from an existing one
//
// Revision 1.31  2003/05/01 19:48:49  dave
// syntax
//
// Revision 1.30  2003/05/01 19:13:21  dave
// Create Stand Alone II
//
// Revision 1.29  2003/05/01 18:23:29  dave
// Create StandAlone I
//
// Revision 1.28  2003/04/14 20:57:28  dave
// fixing the updateUSEnglishOnly meta rule
//
// Revision 1.27  2003/03/27 23:07:20  dave
// adding some timely commits to free up result sets
//
// Revision 1.26  2003/03/10 17:17:59  dave
// attempting to remove GBL7030 from the abstract Action Item
//
// Revision 1.25  2002/10/08 22:16:58  gregg
// only update form if != ""
//
// Revision 1.24  2002/09/10 00:06:45  gregg
// fix for relator update in updatePdhMeta method
//
// Revision 1.23  2002/09/06 22:05:12  gregg
// some null checking in updatePdhMeta method
//
// Revision 1.22  2002/09/05 23:19:59  gregg
// first pass at updatePdhMeta logic
//
// Revision 1.21  2002/08/23 21:59:54  gregg
// updatePdhMeta method throws MiddlewareException
//
// Revision 1.20  2002/08/23 21:29:45  gregg
// updatePdhMeta(Database,boolean) method
//
// Revision 1.19  2002/07/12 22:54:08  joan
// call setFormKey in constructors
//
// Revision 1.18  2002/05/24 21:44:36  dave
// fix to 8002 (missing sessionID on all nav)
//
// Revision 1.17  2002/05/20 22:49:06  dave
// syntax fix
//
// Revision 1.16  2002/05/20 22:31:11  dave
// new edit/create defaults in action item
//
// Revision 1.15  2002/05/20 18:35:03  dave
// syntax fix
//
// Revision 1.14  2002/05/20 18:14:11  dave
// adding type to represent peer create to Create Action Item
//
// Revision 1.13  2002/04/25 17:40:10  dave
// syntax
//
// Revision 1.12  2002/04/04 00:29:04  dave
// fix isFormsCapable
//
// Revision 1.11  2002/03/27 22:34:20  dave
// Row Selectable Table row Add logic. First attempt
//
// Revision 1.10  2002/03/05 19:25:57  dave
// adding MetaLink to NavActionItem in case of the Picklist
// is used so we single out the sole relator that can be used
// when pulling things off the picklist
//
// Revision 1.9  2002/03/05 04:35:49  dave
// syntax
//
// Revision 1.8  2002/03/05 04:17:43  dave
// trace and debug fixes
//
// Revision 1.7  2002/03/05 02:47:30  dave
// missing ;
//
// Revision 1.6  2002/03/05 02:41:04  dave
// null pointer fix
//
// Revision 1.5  2002/03/05 01:43:45  dave
// fixing more throws logic
//
// Revision 1.4  2002/03/04 23:35:01  dave
// numerous fixes
//
// Revision 1.3  2002/03/04 23:19:12  dave
// working on the createActionItem and entitylist constructor
//
// Revision 1.2  2002/03/04 19:42:30  dave
// adding CreateActionItem to eannounce
//
// Revision 1.1  2002/03/04 19:28:41  dave
// added CreateActionItem Object
//
// Revision 1.12  2002/03/01 23:23:15  dave
// column number wrong for pulling 7030 in ActionItem objects
//
// Revision 1.11  2002/03/01 22:50:56  dave
// fix to ensure characteristics on getNavItem constructur for clone
// is pulling all the other information
//
// Revision 1.10  2002/03/01 22:03:34  dave
// more NAVActionItem fixing
//
// Revision 1.9  2002/03/01 19:21:58  dave
// syntax for action item changes
//
// Revision 1.8  2002/03/01 19:00:53  dave
// merged conflicts
//
// Revision 1.7  2002/02/12 23:35:37  dave
// added purpose to the NavActionItem
//
// Revision 1.6  2002/02/12 19:18:38  dave
// extra debugging to isolate Class problem for action item
//
// Revision 1.5  2002/02/12 18:51:44  dave
// more changes to dump
//
// Revision 1.4  2002/02/12 18:10:23  dave
// more changes
//
// Revision 1.3  2002/02/11 18:55:04  dave
// more dump rearranges
//
// Revision 1.2  2002/02/09 21:50:41  dave
// more syntax
//
// Revision 1.1  2002/02/09 20:48:25  dave
// re-arrannging the ActionItem to abstract a common base
//
//

package COM.ibm.eannounce.objects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.rmi.RemoteException;

import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.BluePageException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.transactions.BluePageEntry;
import COM.ibm.opicmpdh.transactions.BluePageEntryGroup;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;

// Temp made this non abstract because we need to compile in general before we whip up
// how actions are suppose to differentiate
// Actually we should be storing the metalink used to do this right
/**
 *  Description of the Class
 *
 * @author     davidbig
 * @created    April 14, 2003
 */
public class CreateActionItem extends EANActionItem {

    final static long serialVersionUID = 20011106L;

    private MetaLink m_ml = null;
    private String m_strFormKey = null;
    private String m_strStandAloneEntityType = null;
    private boolean m_bParent = true;
    /**
     * FIELD
     */
    protected boolean m_bPeer = false;
    private boolean m_bStandAlone = true;
    private boolean m_bBypassCommitChecks = false; // SR5

    private int m_iDefaultIndex = 0;
    private boolean m_bDefaultIndex = false;
    private int m_iNumberItems = 1; // SR5
    /**
     * FIELD
     */
    protected boolean m_bUI = true;
    /**
     * FIELD
     */
    protected boolean m_bWorkflow = true;
    /**
     * FIELD
     */
    protected boolean m_bBluePage = false;
    private String m_strEmailAddress = null;
    private String m_strBPFirstName = null;
    private String m_strBPLastName = null;
    /**
     * FIELD
     */
    protected BluePageEntry[] m_abpe = null;
    /**
     * FIELD
     */
    protected boolean m_bCreateParent = false;
    
    public void setNumberOfItems(int num){
    	if (num>1){
    		m_iNumberItems = num; // SR5
    	}
    }
    protected int getNumberOfItems() { return m_iNumberItems;}// SR5
    public void dereference(){
    	super.dereference();
    	if (m_ml != null){
    		// this may be shared m_ml.dereference();
    		m_ml = null;
    	}
        m_strFormKey = null;
        m_strStandAloneEntityType = null;
        m_strEmailAddress = null;
        m_strBPFirstName = null;
        m_strBPLastName = null;
        m_abpe = null;
    }

    /**
     * Main method which performs a simple test of this class
     *
     * @param  arg  Description of the Parameter
     */
    public static void main(String arg[]) {
    }

    /**
     *  Gets the version attribute of the CreateActionItem object
     *
     * @return    The version value
     */
    public String getVersion() {
        return "$Id: CreateActionItem.java,v 1.54 2009/08/26 17:56:18 wendy Exp $";
    }

    /*
     *  Instantiate a new ActionItem based upon a dereferenced version of the Existing One
     */
    /**
     *Constructor for the CreateActionItem object
     *
     * @param  _ai                             Description of the Parameter
     * @exception  MiddlewareRequestException  Description of the Exception
     */
    public CreateActionItem(CreateActionItem _ai) throws MiddlewareRequestException {
        super(_ai);
        setMetaLink(_ai.getMetaLink());
        setFormKey(_ai.getFormKey());
        setDefaultIndex(_ai.getDefaultIndex());
        setHasDefaultIndex(_ai.hasDefaultIndex());
        setStandAloneEntityType(_ai.getStandAloneEntityType());
        m_bUI = _ai.m_bUI;
        m_bWorkflow = _ai.m_bWorkflow;
        m_bBluePage = _ai.m_bBluePage;
        m_bPeer = _ai.m_bPeer;
        setEmailAddress(_ai.getEmailAddress());
        setBPFirstName(_ai.getBPFirstName());
        setBPLastName(_ai.getBPLastName());
        m_abpe = _ai.m_abpe;
        m_bCreateParent = _ai.m_bCreateParent;
        m_bBypassCommitChecks = _ai.m_bBypassCommitChecks;
    }

    /**
     *Constructor for the CreateActionItem object
     *
     * @param  _mf                             Description of the Parameter
     * @param  _ai                             Description of the Parameter
     * @exception  MiddlewareRequestException  Description of the Exception
     */
    public CreateActionItem(EANMetaFoundation _mf, CreateActionItem _ai) throws MiddlewareRequestException {
        super(_mf, _ai);
        setMetaLink(_ai.getMetaLink());
        setFormKey(_ai.getFormKey());
        setDefaultIndex(_ai.getDefaultIndex());
        setHasDefaultIndex(_ai.hasDefaultIndex());
        setStandAloneEntityType(_ai.getStandAloneEntityType());
        m_bUI = _ai.m_bUI;
        m_bWorkflow = _ai.m_bWorkflow;
        m_bBluePage = _ai.m_bBluePage;
        m_bPeer = _ai.m_bPeer;
        setEmailAddress(_ai.getEmailAddress());
        setBPFirstName(_ai.getBPFirstName());
        setBPLastName(_ai.getBPLastName());
        m_abpe = _ai.m_abpe;
        m_bCreateParent = _ai.m_bCreateParent;
        m_bBypassCommitChecks = _ai.m_bBypassCommitChecks;
    }

    /**
     * This represents an Action Item.  It can only be constructed via a database connection, a Profile, and an Action Item Key*
     *
     * @param  _emf                            Description of the Parameter
     * @param  _db                             Description of the Parameter
     * @param  _prof                           Description of the Parameter
     * @param  _strActionItemKey               Description of the Parameter
     * @exception  SQLException                Description of the Exception
     * @exception  MiddlewareException         Description of the Exception
     * @exception  MiddlewareRequestException  Description of the Exception
     */
    public CreateActionItem(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {

        super(_emf, _db, _prof, _strActionItemKey);

        // Lets go get the information pertinent to the Create Action Item

        try {

        	setDomainCheck(true);
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
                    setStandAloneEntityType(strValue);
                    setTargetType(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("Peer") && strValue.equals("Y")) {
                    setParentCreate(false);
                    setPeerCreate(true);
                } else if (strType.equals("TYPE") && strCode.equals("DefaultValueIndex")) {
                    setDefaultIndex(Integer.valueOf(strValue).intValue());
                    setHasDefaultIndex(true);
                } else if (strType.equals("TYPE") && strCode.equals("BluePage")) {
                    setBluePage(true);
                } else if (strType.equals("ENTITYTYPE") && strCode.equals("Link")) {
                    super.setAssociatedEntityType(strValue);
                    setTargetType(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("SingleInput")) {
                    setSingleInput(true);
                } else if (strType.equals("TYPE") && strCode.equals("CreateParent")) {
                    setCreateParent(true);
                } else if (strType.equals("TYPE") && strCode.equals("AllNLS")) {
                    setAllNLS(true);
				} else if (strType.equals("TYPE") && strCode.equals("DataSource")) {	//catalog enhancement
					setDataSource(strValue);											//catalog enhancement
				} else if (strType.equals("TYPE") && strCode.equals("DataSourceParms")) {
					setAdditionalParms(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("DomainCheck")) { // default is on, allow off
				    setDomainCheck(!strValue.equals("N")); //RQ0713072645
                }else if (strType.equals("TYPE") && strCode.equals("BypassCommitCheck")) { // SR5
					setBypassCommitChecks(strValue.equalsIgnoreCase("Y"));
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
     *  Description of the Method
     *
     * @param  _bBrief  Description of the Parameter
     * @return          Description of the Return Value
     */
    public String dump(boolean _bBrief) {

        StringBuffer strbResult = new StringBuffer();
        strbResult.append("CreateActionItem:" + super.dump(_bBrief));
        strbResult.append("\nMetaLink:");
        if (getMetaLink() == null) {
            strbResult.append(":**null**:");
        } else {
            strbResult.append(getMetaLink().getKey());
        }
        strbResult.append(":isFormCapable:" + isFormCapable());
        strbResult.append(":FormKey:" + getFormKey());
        strbResult.append(":Parent:" + isParentCreate());
        strbResult.append(":Peer:" + isPeerCreate());
        strbResult.append(":DefaultIndex:" + hasDefaultIndex());
        strbResult.append(":" + getDefaultIndex());
        return strbResult.toString();
    }

    /**
     *  Gets the purpose attribute of the CreateActionItem object
     *
     * @return    The purpose value
     */
    public String getPurpose() {
        return "Create";
    }

    /**
     *  Gets the metaLink attribute of the CreateActionItem object
     *
     * @return    The metaLink value
     */
    public MetaLink getMetaLink() {
        return m_ml;
    }

    /**
     *  Gets the formKey attribute of the CreateActionItem object
     *
     * @return    The formKey value
     */
    public String getFormKey() {
        return m_strFormKey;
    }

    /*
     *  Returns true if this create is a create from Parent type (Default)
     */
    /**
     *  Gets the parentCreate attribute of the CreateActionItem object
     *
     * @return    The parentCreate value
     */
    public boolean isParentCreate() {
        return m_bParent;
    }

    /*
     *  Returns true if this create is is a 'create from Peer type'
     */
    /**
     *  Gets the peerCreate attribute of the CreateActionItem object
     *
     * @return    The peerCreate value
     */
    public boolean isPeerCreate() {
        return m_bPeer;
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
    	setActionClass("Create");
    }
    public void updateAction(String strForm, boolean bPeer){
    	setFormKey(strForm);
    	setPeerCreate(bPeer);
    }
    /**
     *  Sets the metaLink attribute of the CreateActionItem object
     *
     * @param  _ml  The new metaLink value
     */
    protected void setMetaLink(MetaLink _ml) {
        m_ml = _ml;
        if (m_ml == null) {
            m_bStandAlone = true;
        } else {
            m_bStandAlone = false;
            m_ml.setParent(this);
        }
    }

    /**
     *  Sets the parentCreate attribute of the CreateActionItem object
     *
     * @param  _b  The new parentCreate value
     */
    protected void setParentCreate(boolean _b) {
        m_bParent = _b;
    }

    /**
     *  Sets the peerCreate attribute of the CreateActionItem object
     *
     * @param  _b  The new peerCreate value
     */
    protected void setPeerCreate(boolean _b) {
        m_bPeer = _b;
    }
    private void setBypassCommitChecks(boolean b){ // SR 5
    	m_bBypassCommitChecks = b;
    }
    public boolean isBypassCommitChecks() { // SR 5
        return m_bBypassCommitChecks;
    }
    /**
     * isStandAlone
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isStandAlone() {
        return m_bStandAlone;
    }

    /**
     *  Description of the Method
     *
     * @return    Description of the Return Value
     */
    public boolean hasDefaultIndex() {
        return m_bDefaultIndex;
    }

    /**
     *  Sets the hasDefaultIndex attribute of the CreateActionItem object
     *
     * @param  _b  The new hasDefaultIndex value
     */
    protected void setHasDefaultIndex(boolean _b) {
        m_bDefaultIndex = _b;
    }

    /**
     * isBluePage
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isBluePage() {
        return m_bBluePage;
    }

    /**
     * setBluePage
     *
     * @param _b
     *  @author David Bigelow
     */
    protected void setBluePage(boolean _b) {
        m_bBluePage = _b;
    }

    /**
     * isCreateParent
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isCreateParent() {
        return m_bCreateParent;
    }

    /**
     * setCreateParent
     *
     * @param _b
     *  @author David Bigelow
     */
    protected void setCreateParent(boolean _b) {
        m_bCreateParent = _b;
    }

    /**
     * setEmailAddress
     *
     * @param _str
     *  @author David Bigelow
     */
    public void setEmailAddress(String _str) {
        m_strEmailAddress = _str;
    }

    /**
     * getEmailAddress
     *
     * @return
     *  @author David Bigelow
     */
    public String getEmailAddress() {
        return m_strEmailAddress;
    }

    /**
     * setBPFirstName
     *
     * @param _str
     *  @author David Bigelow
     */
    public void setBPFirstName(String _str) {
        m_strBPFirstName = _str;
    }

    /**
     * getBPFirstName
     *
     * @return
     *  @author David Bigelow
     */
    public String getBPFirstName() {
        return m_strBPFirstName;
    }

    /**
     * setBPLastName
     *
     * @param _str
     *  @author David Bigelow
     */
    public void setBPLastName(String _str) {
        m_strBPLastName = _str;
    }

    /**
     * getBPLastName
     *
     * @return
     *  @author David Bigelow
     */
    public String getBPLastName() {
        return m_strBPLastName;
    }

    /**
     * setSelectedBluePageEntries
     *
     * @param _abpe
     *  @author David Bigelow
     */
    public void setSelectedBluePageEntries(BluePageEntry[] _abpe) {
        m_abpe = _abpe;
    }

    /**
     * getSelectedBluePageEntries
     *
     * @return
     *  @author David Bigelow
     */
    public BluePageEntry[] getSelectedBluePageEntries() {
        return m_abpe;
    }

    /**
     * getBluePageEntries
     *
     * @param _db
     * @param _rdi
     * @param _strFirstName
     * @param _strLastName
     * @throws java.rmi.RemoteException
     * @throws COM.ibm.opicmpdh.middleware.BluePageException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @return
     *  @author David Bigelow
     */
    public BluePageEntry[] getBluePageEntries(Database _db, RemoteDatabaseInterface _rdi, String _strFirstName, String _strLastName) throws RemoteException, BluePageException, MiddlewareException, MiddlewareShutdownInProgressException {
        BluePageEntryGroup bpeg = null;
        BluePageEntry[] aBPE = null;

        if (_db == null && _rdi == null) {
            return null;
        }
        if (_strFirstName == null || _strLastName == null) {
            D.ebug(D.EBUG_SPEW,"CreateActionItem getBluePageEntries firstname, lastname are null");
            return null;
        }
        m_strBPFirstName = _strFirstName;
        m_strBPLastName = _strLastName;
        if (_db != null) {
            bpeg = _db.getBluePageEntryGroup(_strLastName, _strFirstName);
        } else {
            bpeg = _rdi.getBluePageEntryGroup(_strLastName, _strFirstName);
        }
        aBPE = new BluePageEntry[bpeg.size()];
        bpeg.toArray(aBPE);
        return aBPE;
    }

    /**
     *  Gets the defaultIndex attribute of the CreateActionItem object
     *
     * @return    The defaultIndex value
     */
    public int getDefaultIndex() {
        return m_iDefaultIndex;
    }

    /**
     *  Sets the defaultIndex attribute of the CreateActionItem object
     *
     * @param  _i  The new defaultIndex value
     */
    protected void setDefaultIndex(int _i) {
        m_iDefaultIndex = _i;
    }

    /**
     * setStandAloneEntityType
     *
     * @param _str
     *  @author David Bigelow
     */
    protected void setStandAloneEntityType(String _str) {
        m_strStandAloneEntityType = _str;
    }

    /**
     * getStandAloneEntityType
     *
     * @return
     *  @author David Bigelow
     */
    public String getStandAloneEntityType() {
        return m_strStandAloneEntityType;
    }
    /**
     *  Sets the formKey attribute of the CreateActionItem object
     *
     * @param  _str  The new formKey value
     */
    protected void setFormKey(String _str) {
        m_strFormKey = _str;
    }

    /**
     *  Gets the formCapable attribute of the CreateActionItem object
     *
     * @return    The formCapable value
     */
    public boolean isFormCapable() {
        return m_strFormKey != null;
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
            CreateActionItem cr_db = new CreateActionItem(null, _db, getProfile(), getActionItemKey());
            boolean bNewAction = false;
            //check for new
            if (cr_db.getActionClass() == null) {
                bNewAction = true;
            }

            //EXPIRES
            if (_bExpire && !bNewAction) {
                //Relator
                if (cr_db.getMetaLink() != null) {
                    updateActionAttribute(_db, true, "TYPE", "Relator", cr_db.getMetaLink().getEntityType());
                } else {
                    _db.debug(D.EBUG_ERR, "CreateActionItem.updatePdhMeta:MetaLink is null for \"" + cr_db.getActionItemKey() + "\"");
                }
                //Form
                if (cr_db.getFormKey() != null && !cr_db.getFormKey().equals("")) {
                    updateActionAttribute(_db, true, "TYPE", "Form", cr_db.getFormKey());
                }
                //isPeer
                updateActionAttribute(_db, true, "TYPE", "Peer", (cr_db.isPeerCreate() ? "Y" : "N"));

            } else {
                //Relator
                if (cr_db.getMetaLink() != null && this.getMetaLink() != null) {
                    String strRelator_db = cr_db.getMetaLink().getEntityType();
                    String strRelator_this = this.getMetaLink().getEntityType();
                    //only need to insert since this will vary only by linkvalue
                    if (bNewAction || !strRelator_db.equals(strRelator_this)) {
                        updateActionAttribute(_db, false, "TYPE", "Relator", strRelator_this);
                    }
                } else if (cr_db.getMetaLink() == null && this.getMetaLink() != null) {
                    updateActionAttribute(_db, false, "TYPE", "Relator", getMetaLink().getEntityType());
                } else {
                    _db.debug(D.EBUG_ERR, "CreateActionItem.updatePdhMeta:MetaLink is null for \"" + this.getActionItemKey() + "\"");
                }

                //Form
                if (getFormKey() != null && !getFormKey().equals("")) {
                    if (bNewAction || cr_db == null || (!this.getFormKey().equals(cr_db.getFormKey()))) {
                        //only need to insert since this will vary only by linkvalue
                        updateActionAttribute(_db, false, "TYPE", "Form", this.getFormKey());
                    }
                } else {
                    //expire
                    if (cr_db.getFormKey() != null) {
                        updateActionAttribute(_db, true, "TYPE", "Form", cr_db.getFormKey());
                    }
                }

                //Peer
                // 2) Peer create
                if (bNewAction || cr_db.isPeerCreate() != this.isPeerCreate()) {
                    updateActionAttribute(_db, false, "TYPE", "Peer", (this.isPeerCreate() ? "Y" : "N"));
                }
            }

        } catch (SQLException sqlExc) {
            _db.debug(D.EBUG_ERR, "CreateActionItem 361 " + sqlExc.toString());
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
     * disableWorkflow
     *
     *  @author David Bigelow
     */
    protected void disableWorkflow() {
        m_bWorkflow = false;
    }

    /**
     * enableWorkflow
     *
     *  @author David Bigelow
     */
    protected void enableWorkflow() {
        m_bWorkflow = true;
    }

    /**
     * isWorkflowEnabled
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isWorkflowEnabled() {
        return m_bWorkflow;
    }

    /**
     * to simplify things a bit
     *
     * @param  _db                      Description of the Parameter
     * @param  _bExpire                 Description of the Parameter
     * @param  _strLinkType2            Description of the Parameter
     * @param  _strLinkCode             Description of the Parameter
     * @param  _strLinkValue            Description of the Parameter
     * @exception  MiddlewareException  Description of the Exception
     */
    private void updateActionAttribute(Database _db, boolean _bExpire, String _strLinkType2, String _strLinkCode, String _strLinkValue) throws MiddlewareException {
        String strValFrom = _db.getDates().getNow();
        String strValTo = (_bExpire ? strValFrom : _db.getDates().getForever());
        new MetaLinkAttrRow(getProfile(), "Action/Attribute", getActionItemKey(), _strLinkType2, _strLinkCode, _strLinkValue, strValFrom, strValTo, strValFrom, strValTo, 2).updatePdh(_db);
    }
}
